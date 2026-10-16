# Headless CLI

CrashDetector can analyze logs without opening a window:

```text
java -jar CrashDetectorApp.jar analizar path/to/log.log
java -jar CrashDetectorApp.jar analizar-texto "log contents"
java -jar CrashDetectorApp.jar analizar-markdown document.md
```

## Commands and aliases

- `analizar`, `analyse`, `analyze`, and `logs` accept one or more file paths.
- `analizar-texto`, `analizartexto`, `analyse-text`, `analyze-text`, `log-text`, and `logs-text` accept one or more text values. Each argument is an independent log; quote arguments to preserve spaces and line breaks.
- `analizar-markdown` accepts one Markdown file and invokes `EnlanceMD` inside the monitor process; the document may contain many compatible log links and groups.
- `--log`, `--log-temporal`, and `--resultado-cli` are internal monitor-process arguments, not public commands.

Paths are validated, de-duplicated, and passed through the normal `Consola` flow. `analizar-texto` materializes each value as UTF-8 in a CrashDetector-controlled temporary file.

`EnlanceMD` extracts every Markdown link, removes duplicate URLs, and groups fragments by logical filename (`latest.log`, `debug.log`, `crash-*.txt`, and similar names). Lines containing five or more `#` characters separate blocks only in `pruebas_masivas.sh`; each block is passed intact to an isolated JVM. Fixtures with deliberately nonexistent links may use `-Dcrashdetector.markdown.sin_red=true` to avoid DNS and produce deterministic diagnostics.

## Result

The headless path requests `-Djava.awt.headless=true` for the monitor process and must not create Swing UI. When analysis finishes, the last non-empty stdout line is the absolute path of the generated report. The report must exist, be readable, non-empty, and contain HTML. Diagnostics are written to stderr and to the monitor log.

A report with no findings is still a successful analysis result: the report is generated with the no-results message.

Public exit codes are `0` for success, `2` for invalid input or Markdown without compatible links, `3` for monitor failure or when all Markdown downloads fail, and `4` for an invalid report. A missing path, missing/empty report, or invalid HTML is never successful. A no-findings report still returns `0` when its HTML is valid. Mass fixtures may enable `-Dcrashdetector.markdown.sin_red=true` to reject external links without accessing the network.

`analizar-texto` temporary files are removed when a new CLI run is prepared and through `deleteOnExit`; do not rely on their names after the run.

## Headless example

```text
java -Djava.awt.headless=true -jar CrashDetectorApp.jar analizar latest.log
```

The JVM flag is an additional safeguard, not a replacement for the `analizar` route: the application must enable `--resultado-cli` transport and wait for the monitor before printing the report path.

The internal transport uses `--idemode`, `--resultado-cli`, `--log <path>`, and `--log-temporal <path>`. These names may appear in monitor diagnostics but are not part of the public command interface.
