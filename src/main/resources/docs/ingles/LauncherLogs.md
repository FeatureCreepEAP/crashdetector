
These are the locations of many of the LauncherLogs/Dev Consoles for various MinecraftLaunchers. Launcher Logs are needed for many types of errors which do not show up in latest.log most of the time, like stdout.

---

### **Official Minecraft Launcher (Java Edition)**  
- **Log Locations**:  
  - **Windows**:  
    - `%APPDATA%\.minecraft\launcher_log.txt`  
  - **macOS**:  
    - `~/Library/Application Support/minecraft/launcher_log.txt`  
  - **Linux**:  
    - `~/.minecraft/launcher_log.txt`  

**Key Notes**:  
- Logs grow indefinitely unless manually cleared
- These have a lot of verbos compared to other logs.
- You can go to settings to to enable a Console in the GUI which has less verbos but also leaves out information.

---

### **CurseForge App**  
- **Log Locations**:  
  - **Windows**:  
    - `C:\Users\<Username>\Documents\CurseForge\Install\launcher_log.txt` (`../../Install/launcher_log.txt`)  
  - **macOS**:  
    - `~/Library/Application Support/minecraft/launcher_log.txt`  
  - **Linux**:  
    - `~/.minecraft/launcher_log.txt`  

**Key Notes**:  
- This is based on the vanilla launcher.
- No log is generated if the **"Skip Launcher"** option is enabled, please disable Skip Launcher (`Settings > Minecraft`).  
![CurseForge Skip Launcher](https://pagure.io/CrashDetectorMC/raw/main/f/src/main/resources/imagenes/cfskiplauncher.png  "CurseForge Skip Launcher")

---

### **FTB Launcher**  
- **Log Location**:  
  - Universal: `../../logs/ftb-app-electron.log` (each `../` moves up one directory level).  

---

### **GDLauncher**  
- **Log Location**:  
  - Right click the instance and press **Settings** and then **Logs**
![GD Settings](https://pagure.io/CrashDetectorMC/raw/main/f/src/main/resources/imagenes/gdconfig.png  "GD Settings")
![GD Logs](https://pagure.io/CrashDetectorMC/raw/main/f/src/main/resources/imagenes/gdlogs.png  "GD Logs")

---

### **MultiMC & Derivatives (such as PrismLauncher)**  
- **Log Access**:  
  - Called **"Minecraft Logs"** in the UI.  
  - Only accessible via **Edit Instance > Minecraft Log** screen.  
![Launcher Logs in PrismLauncher](https://pagure.io/CrashDetectorMC/raw/main/f/src/main/resources/imagenes/registros_de_launcher_prism.png  "Launcher Logs Interface")
---

### **SKLauncher**  
- **Log Location**:  
 `sklauncher/sklauncher_logs.txt`  

### **TLauncher**  
- **Log Locations**:  
  - **Windows**:  
    - `%APPDATA%\.tlauncher\logs\tlauncher/`  
  - **macOS**:  
    - `~/Library/Application Support/tlauncher/logs/tlauncher/`  
  - **Linux**:  
    - `~/.tlauncher/logs/tlauncher/`  

**Key Notes**:  
- Logs accumulate continuously until the launcher is restarted, each time a new file is made in the folder. 
- You can enable the Dev Console in the Settings to send them. 
---
The locations of the LauncherLogs often change. You should take a look at [our code](https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/Consola.java) to find out all the locations.
