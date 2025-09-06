<?php
// Configuration
define('LOGS_DIR', __DIR__ . '/logs/');
define('MAX_GZIP_SIZE', 20 * 1024 * 1024); // 20MB
define('MAX_STORAGE', 20 * 1024 * 1024 * 1024); // 20GB
define('MAX_AGE', 4 * 3600); // 4 hours in seconds
define('SAILOR_MOON_URL', 'https://static.wikia.nocookie.net/rewrite-adventures/images/0/05/Usagi_Tsukino.png');
define('CLEANUP_CHANCE', 0.5); // 50% chance to run cleanup

// Ensure logs directory exists
if (!is_dir(LOGS_DIR)) {
    mkdir(LOGS_DIR, 0755, true);
}

// Optimized cleanup function
function maybeCleanup() {
    // Only run cleanup 50% of the time
    if (mt_rand(1, 100) > (CLEANUP_CHANCE * 100)) {
        return;
    }
    
    $now = time();
    $files = glob(LOGS_DIR . '*.gz');
    
    // Skip if no files
    if (empty($files)) return;
    
    // Delete files older than 4 hours
    foreach ($files as $file) {
        if ($now - filemtime($file) > MAX_AGE) {
            @unlink($file);
        }
    }
    
    // Check if we need to delete by storage size (only if we have more than 5 files)
    if (count($files) > 5) {
        $fileInfo = [];
        $totalSize = 0;
        
        foreach ($files as $file) {
            $size = filesize($file);
            $totalSize += $size;
            $fileInfo[] = ['file' => $file, 'size' => $size, 'mtime' => filemtime($file)];
        }
        
        // Sort by modification time (oldest first)
        usort($fileInfo, function($a, $b) {
            return $a['mtime'] - $b['mtime'];
        });
        
        // Delete oldest files until under 20GB
        if ($totalSize > MAX_STORAGE) {
            $toDelete = [];
            $currentSize = $totalSize;
            
            foreach ($fileInfo as $info) {
                if ($currentSize <= MAX_STORAGE) break;
                $toDelete[] = $info['file'];
                $currentSize -= $info['size'];
            }
            
            foreach ($toDelete as $file) {
                @unlink($file);
            }
        }
    }
}

// Handle log uploads
if ($_SERVER['REQUEST_METHOD'] === 'POST' && 
    strpos($_SERVER['REQUEST_URI'], '/crash_detector/paste/endpoint.php') !== false &&
    isset($_GET['action']) && $_GET['action'] === 'save_log') {
    
    maybeCleanup();
    
    $data = file_get_contents('php://input');
    $size = strlen($data);
    
    if ($size > MAX_GZIP_SIZE) {
        http_response_code(413);
        header('Content-Type: application/json');
        echo json_encode(['error' => 'File too large. Max 20MB gzipped.']);
        exit;
    }
    
    $id = bin2hex(random_bytes(8));
    $file = LOGS_DIR . $id . '.gz';
    
    file_put_contents($file, $data);
    
    // Generate proper links with .gz extension for raw logs
    $baseUrl = 'https://' . $_SERVER['HTTP_HOST'] . '/crash_detector/paste/endpoint.php';
    $decoratedLink = $baseUrl . '?id=' . $id;
    $rawLink = $baseUrl . '/logs/' . $id . '.gz';
    
    header('Content-Type: application/json');
    echo json_encode([
        'link' => $decoratedLink,
        'raw_link' => $rawLink
    ]);
    exit;
}

// Handle raw log requests (with .gz extension)
// Handle raw log requests (with .gz extension)
if (preg_match('!/crash_detector/paste/endpoint\.php/logs/([a-f0-9]+)\.gz$!', $_SERVER['REQUEST_URI'], $matches)) {
    maybeCleanup();
    
    $id = $matches[1];
    $file = LOGS_DIR . $id . '.gz';
    
    if (!file_exists($file)) {
        http_response_code(404);
        exit;
    }

    // Instead of serving as gzip, decompress & send plain text
    $content = gzdecode(file_get_contents($file));
    if ($content === false) {
        http_response_code(500);
        echo "Failed to decode log.";
        exit;
    }
    
    header('Content-Type: text/plain; charset=UTF-8');
    header('Content-Length: ' . strlen($content));
    echo $content;
    exit;
}


// Handle delete requests
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['delete'])) {
    $id = preg_replace('/[^a-f0-9]/', '', $_POST['delete']);
    $file = LOGS_DIR . $id . '.gz';
    
    if (file_exists($file)) {
        unlink($file);
    }
    
    // Return JSON response for AJAX request
    header('Content-Type: application/json');
    echo json_encode(['status' => 'success']);
    exit;
}

// Handle decorated view
if (isset($_GET['id'])) {
    maybeCleanup();
    
    $id = preg_replace('/[^a-f0-9]/', '', $_GET['id']);
    $file = LOGS_DIR . $id . '.gz';
    
    if (!file_exists($file)) {
        http_response_code(404);
        exit;
    }
    
    $content = gzdecode(file_get_contents($file));
    $lines = explode("\n", $content);
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=5.0, user-scalable=yes">
    <title>Minecraft Log Viewer - <?php echo $id; ?></title>
    <style>
:root {
    --bg-color: #0d0d0d;          /* pure black background */
    --text-color: #eaeaea;        /* bright neutral white */
    --line-num-color: #3aa7ff;    /* vivid cyan/blue */
    --error-color: #ff4d4d;       /* strong red */
    --trace-color: #ffcc00;       /* bold golden yellow */
    --link-color: #00ff99;        /* neon teal/green */
    --highlight-bg: rgba(255, 77, 77, 0.25); /* glowing red highlight */
}


        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            background-color: var(--bg-color);
            color: var(--text-color);
            font-family: 'Ubuntu Mono', 'Fira Code', 'Consolas', 'Courier New', monospace;
            font-size: 11px;
            line-height: 1;
            padding: 0;
            position: relative;
            min-height: 100vh;
            overflow-x: hidden;
        }
        
        .sailor-moon {
            position: fixed;
            bottom: 0;
            right: 0;
            width: 126px;
            height: 184px;
            background-image: url('<?php echo SAILOR_MOON_URL; ?>');
            background-size: contain;
            background-repeat: no-repeat;
            background-position: bottom right;
            z-index: -2;
opacity: 0.15;                /* from 0.05 → 0.15 */
    mix-blend-mode: lighten;      /* “lighten” or “screen” shows better */
    filter: contrast(1.2) brightness(1.2); /* optional boost */
            pointer-events: none;
        }
        
        .controls {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 4px 6px;
            background-color: var(--bg-color);
            border-bottom: 1px solid #222;
            font-size: 10px;
            position: relative;
            z-index: 10;
        }
        
        .controls a {
            color: var(--link-color);
            text-decoration: none;
        }
        
        .controls a:hover {
            text-decoration: underline;
        }
        
        .delete-btn {
            background: #ff5252;
            color: white;
            border: none;
            padding: 2px 5px;
            border-radius: 2px;
            cursor: pointer;
            font-weight: bold;
            font-size: 9px;
            position: relative;
            z-index: 20;
        }
        
        .scroll-to-bottom {
            position: fixed;
            top: 10px;
            right: 10px;
            background: #0078d7;
            color: white;
            border: none;
            width: 30px;
            height: 30px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 16px;
            cursor: pointer;
            box-shadow: 0 1px 5px rgba(0,0,0,0.3);
            z-index: 100;
        }
        
.log-container {
    font-size: 11px;
    line-height: 1.05;
    overflow-x: auto;       /* horizontal scroll if needed */
}

.log-line {
    display: flex;
    margin: 0;
    padding: 0;
}


.line-number,
.line-content {
    line-height: 1.05;   /* match parent */
}


.line-number {
    flex: 0 0 40px;            /* fixed column for numbers */
    text-align: right;
    margin: 0;
    padding: 0 6px 0 0;
    color: var(--line-num-color);
    opacity: 0.6;
    font-variant-numeric: tabular-nums;
    font-size: 11px;
    line-height: 1.2;
    text-decoration: none;
    user-select: none;
}

.line-number:hover {
    opacity: 0.9;
    text-decoration: underline;
}

.line-content {
    flex: 1 1 auto;
    line-height: 1.2;
}







        
        .error {
            color: var(--error-color);
            font-weight: bold;
        }
        
        .trace {
            color: var(--trace-color);
            padding-left: 15px;
        }
        
        .highlight {
            background-color: var(--highlight-bg);
        }
        
        /* Mobile-specific styling */
        @media (max-width: 768px) {
            body {
                font-size: 10px;
            }
            
            .controls {
                font-size: 9px;
                padding: 3px 5px;
            }
            
            .delete-btn {
                padding: 1px 4px;
                font-size: 8px;
            }
            
            .scroll-to-bottom {
                width: 26px;
                height: 26px;
                font-size: 14px;
                top: 8px;
                right: 8px;
            }
            
            .log-container {
                font-size: 9.5px;
            }
            
            .line-number {
                min-width: 35px;
                padding: 0 4px;
                font-size: 9.5px;
            }
        }
    </style>
</head>
<body>
    <div class="controls">
        <div>
            <button class="delete-btn" onclick="deleteLog('<?php echo $id; ?>')">Delete</button>
        </div>
        <div>
            Raw log: <a href="/crash_detector/paste/endpoint.php/logs/<?php echo $id; ?>.gz" target="_blank">/logs/<?php echo $id; ?>.gz</a>
        </div>
    </div>
    
    <div class="log-container" id="log-container">
        <?php foreach ($lines as $index => $line): 
            $lineNumber = $index + 1;
            $lineId = 'L' . $lineNumber;
            $highlight = '';
            
            // Minecraft error highlighting
            if (preg_match('/\[ERROR\]|java\.lang\.\w+Exception|Caused by:/', $line)) {
                $highlight = 'error';
            } elseif (preg_match('/\[WARN\]|at \w+\.\w+/', $line)) {
                $highlight = 'trace';
            }
            
            // Escape HTML but preserve line breaks
            $safeLine = htmlspecialchars($line);
        ?>
<div class="log-line" id="<?php echo $lineId; ?>">
    <a class="line-number" href="#<?php echo $lineId; ?>"><?php echo $lineNumber; ?></a>
    <span class="line-content <?php echo $highlight; ?>"><?php echo $safeLine; ?></span>
</div>



        <?php endforeach; ?>
    </div>
    
    <button class="scroll-to-bottom" id="scrollToBottom">↓</button>
    <div class="sailor-moon"></div>
    
    <script>
        document.getElementById('scrollToBottom').addEventListener('click', function() {
            window.scrollTo({
                top: document.body.scrollHeight,
                behavior: 'smooth'
            });
        });
        
        function deleteLog(id) {
            if (confirm('Are you sure you want to delete this log?')) {
                fetch(window.location.origin + '/crash_detector/paste/endpoint.php', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: 'delete=' + id
                })
                .then(response => {
                    if (response.ok) {
                        window.location.href = '/';
                    } else {
                        alert('Failed to delete log');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error deleting log: ' + error.message);
                });
            }
        }
        
        // Enhanced mobile handling
        document.addEventListener('DOMContentLoaded', function() {
            if (/Mobi|Android/i.test(navigator.userAgent)) {
                const viewport = document.querySelector('meta[name="viewport"]');
                viewport.setAttribute('content', 'width=device-width, initial-scale=0.5, minimum-scale=0.5, maximum-scale=5.0, user-scalable=yes');
                
                setTimeout(() => {
                    window.scrollTo(0, document.body.scrollHeight);
                }, 300);
            }
        });
    </script>
</body>
</html>
<?php
    exit;
}

// Default response
header('Content-Type: text/plain');
echo "Minecraft Log Service\n";
echo "POST to this script with ?action=save_log to upload logs\n";
echo "GET with ?id=... for decorated view\n";
echo "GET with /logs/...gz for raw logs\n";
?>
