### **Registros del Launcher (Launcher Logs) y Consolas de Desarrollo de Distintos Launchers de Minecraft**

Estas son las ubicaciones de muchos de los Launcher Logs y Consolas de Desarrollo utilizados por distintos launchers de Minecraft. Los Launcher Logs son necesarios para diagnosticar muchos tipos de errores que normalmente no aparecen en `latest.log`, especialmente los mensajes enviados a stdout y otra información del launcher. 

---

### **Launcher Oficial de Minecraft (Java Edition)**

**Windows:**
`%APPDATA%\.minecraft\launcher_log.txt`

**macOS:**
`~/Library/Application Support/minecraft/launcher_log.txt`

**Linux:**
`~/.minecraft/launcher_log.txt`

#### Notas importantes

* Los registros crecen indefinidamente a menos que se eliminen manualmente.
* Contienen mucha más información detallada que otros tipos de registros.
* Es posible habilitar una consola desde la configuración del launcher. Esta consola muestra menos información, pero también omite algunos datos importantes.

![Habilitar Consola](https://pagure.io/CrashDetectorMC/raw/main/f/src/main/resources/imagenes/vainillalanzer.png "Enable Console")

---

### **Aplicación CurseForge**

**Windows:**
`C:\Users\<Usuario>\Documents\CurseForge\Install\launcher_log.txt`
(`../../Install/launcher_log.txt`)

**macOS:**
`~/Library/Application Support/minecraft/launcher_log.txt`

**Linux:**
`~/.minecraft/launcher_log.txt`

#### Notas importantes

* Está basado en el launcher oficial de Minecraft.
* No se genera ningún registro si está activada la opción **"Skip Launcher"**. Desactive dicha opción en:
  `Settings > Minecraft`

![CurseForge Skip Launcher](https://pagure.io/CrashDetectorMC/raw/main/f/src/main/resources/imagenes/cfskiplauncher.png "CurseForge Skip Launcher")

---

### **FTB Launcher**

Ubicación universal:

`../../logs/ftb-app-electron.log`

(Cada `../` sube un nivel de directorio).

---

### **GDLauncher**

Haga clic derecho sobre la instancia, seleccione **Settings** y después **Logs**.

![GD Settings](https://pagure.io/CrashDetectorMC/raw/main/f/src/main/resources/imagenes/gdconfig.png "GD Settings")

![GD Logs](https://pagure.io/CrashDetectorMC/raw/main/f/src/main/resources/imagenes/gdconsola.png "GD Logs")

---

### **MultiMC y Derivados (como PrismLauncher)**

* Haga clic derecho sobre una instancia y seleccione **Edit Instance**.
* En la interfaz aparece como **Minecraft Logs**.

#### Notas importantes

* Es posible subir los registros a Paste.ee (MultiMC) o MCLo.gs (Prism), pero estos servicios suelen tener límites relativamente bajos para el tamaño de los registros.
* Los registros solo son accesibles desde:
  **Edit Instance > Minecraft Log**

![Launcher Logs en PrismLauncher](https://pagure.io/CrashDetectorMC/raw/main/f/src/main/resources/imagenes/registros_de_launcher_prism.png "Launcher Logs Interface")

---

### **SKLauncher**

**Ubicación del registro:**

`sklauncher/sklauncher_logs.txt`

---

### **TLauncher**

**Windows:**
`%APPDATA%\.tlauncher\logs\tlauncher\`

**macOS:**
`~/Library/Application Support/tlauncher/logs/tlauncher/`

**Linux:**
`~/.tlauncher/logs/tlauncher/`

#### Notas importantes

* Los registros se acumulan continuamente hasta que se reinicia el launcher. Cada reinicio genera un nuevo archivo dentro de la carpeta.
* Puede habilitar la Consola de Desarrollo desde la configuración para compartir los registros.
* Al producirse un fallo suele aparecer una ventana emergente que permite subir automáticamente el registro. El límite es de 11 MB comprimidos en formato GZip.

---

### **ATLauncher**

Existe una ventana emergente que muestra los registros.

Incluye:

* Un botón para copiar los registros.
* Un botón para subir los registros.

#### Notas importantes

* Puede subir los registros a `paste.atlauncher.com`, aunque se desconoce el tamaño máximo permitido.
* Parece estar basado en Stikked. Para ver el contenido sin procesar (raw), añada `raw/` después de `view/` en la URL.

![AT Logs](https://pagure.io/CrashDetectorMC/raw/main/f/src/main/resources/imagenes/atlogs.png "AT Logs")

---

Las ubicaciones de los Launcher Logs cambian con frecuencia. Se recomienda revisar nuestro código para conocer todas las ubicaciones soportadas actualmente. 

Puede consultar la clase `Consola.java` dentro del proyecto CrashDetector para encontrar todas las rutas actualizadas.

