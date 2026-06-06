# Documentación de GPU Fix

## Descripción General

GPU Fix es la interfaz dedicada de resolución de problemas gráficos de CrashDetector.

Su propósito es ayudar a los usuarios a resolver situaciones en las que Minecraft está utilizando el procesador gráfico incorrecto, especialmente en sistemas que disponen tanto de GPU integrada como de GPU dedicada.

Esto es particularmente común en portátiles que utilizan tecnologías como:

* NVIDIA Optimus
* Gráficos híbridos
* Gráficos integrados Intel o AMD
* Gráficos dedicados NVIDIA o AMD

Cuando Minecraft se ejecuta utilizando la GPU incorrecta, los usuarios pueden experimentar:

* FPS bajos
* Tirones (stuttering)
* Bajo rendimiento con shaders
* Detección incorrecta de la GPU
* Fallos causados por hardware gráfico débil o no compatible

GPU Fix proporciona orientación específica para cada plataforma y, cuando es posible, aplica correcciones a nivel de sistema automáticamente.

---

# Arquitectura

GPU Fix está dividido en dos capas.

```text
GPUFixGUI
    │
    ▼
GPUFixOptimusPrime
```

## GPUFixGUI

La clase base abstracta contiene:

* Configuración de la ventana
* Conexión de botones
* Detección del sistema operativo
* Lógica de aplicación de correcciones
* Lógica para abrir URLs
* Registro en la interfaz de CrashDetector

## GPUFixOptimusPrime

La implementación concreta contiene:

* Apariencia temática de Optimus Prime
* Colores configurables
* Texto explicativo específico para cada plataforma
* Soporte para imágenes decorativas
* Estilo de botones

Esto sigue la separación habitual de CrashDetector entre comportamiento y apariencia.

---

# Interfaz de Usuario

La interfaz contiene:

* Un panel con la imagen de Optimus Prime
* Un área de texto explicativa
* Un botón Aplicar
* Un botón de documentación de Optimus para Linux

El texto cambia automáticamente dependiendo del sistema operativo.

---

# Detección del Sistema Operativo

GPU Fix detecta el sistema actual utilizando:

```java
System.getProperty("os.name")
```

Soporta:

```text
Windows
macOS
Linux
Otros sistemas
```

Cada plataforma recibe instrucciones y comportamientos diferentes.

---

# Corrección de GPU en Windows

En Windows, GPU Fix intenta forzar que Java utilice la GPU de alto rendimiento.

Lo hace escribiendo en la clave de registro de preferencias de GPU de DirectX para el usuario actual.

```text
HKCU\SOFTWARE\Microsoft\DirectX\UserGpuPreferences
```

El valor escrito es:

```text
GpuPreference=2;
```

Esto indica a Windows que debe preferir la GPU de alto rendimiento para el ejecutable de Java seleccionado.

---

# Detección del Ejecutable Java

GPU Fix obtiene el ejecutable Java mediante:

```java
MonitorDePID.jvm()
```

Este valor se utiliza posteriormente como nombre de la entrada del registro.

El objetivo normalmente es el ejecutable Java utilizado para lanzar Minecraft, por ejemplo:

```text
javaw.exe
```

o cualquier otra ruta de Java proporcionada por el lanzador.

---

# Comando del Registro de Windows

La corrección de Windows utiliza el comando integrado `reg`.

```text
reg add HKCU\SOFTWARE\Microsoft\DirectX\UserGpuPreferences /v <javaw> /t REG_SZ /d GpuPreference=2; /f
```

Después de que el comando finaliza, GPU Fix muestra un mensaje de confirmación.

---

# Corrección de GPU en macOS

En macOS, GPU Fix intenta forzar el uso de la GPU dedicada ejecutando:

```text
sudo pmset -a gpuswitch 1
```

Esto solo se aplica a Macs que disponen de gráficos intercambiables.

Los valores son:

```text
1 = GPU dedicada
2 = Cambio automático
```

Debido a que este comando utiliza `sudo`, el sistema puede solicitar credenciales de administrador al usuario.

---

# Corrección de GPU en Linux

En Linux, GPU Fix no aplica modificaciones automáticas al sistema.

En su lugar, muestra instrucciones manuales y proporciona un enlace a la documentación de Optimus.

El botón de documentación incluido abre:

```text
https://rpmfusion.org/Howto/Optimus
```

Esto es especialmente relevante para sistemas Linux que utilizan NVIDIA Optimus o PRIME Render Offload.

---

# Sistemas No Soportados

Si el sistema operativo no es reconocido como Windows, macOS o Linux, GPU Fix muestra un mensaje indicando que el sistema no está soportado.

No se intentan realizar cambios automáticos.

---

# Modelo de Seguridad

GPU Fix realiza cambios únicamente cuando el usuario pulsa el botón Aplicar.

No modifica silenciosamente las preferencias de GPU.

Comportamiento por plataforma:

```text
Windows  → escribe una preferencia de GPU en el registro del usuario
macOS    → ejecuta pmset mediante sudo
Linux    → solo orientación manual
Otros    → ninguna acción
```

La modificación en Windows se realiza bajo `HKCU`, por lo que afecta únicamente al usuario actual y no a toda la máquina.

---

# Enlaces de Referencia

La clase incluye soporte para varios botones de fuentes o referencias:

```text
Fuente de corrección GPU de TLauncher
Referencia de comportamiento en VirusTotal
Guía Optimus de RPM Fusion
```

En la interfaz actual, únicamente el botón de la guía Optimus para Linux se añade a la barra de botones visible.

Los botones de TLauncher y VirusTotal existen en el código pero están comentados en la disposición de la interfaz.

---

# Integración con el Administrador de Rendimiento

GPU Fix está integrado con el Administrador de Rendimiento.

Cuando la verificación de GPU de CrashDetector detecta un problema:

```java
VerificacionGPU.hayProblema
```

el Administrador de Rendimiento muestra un botón que abre directamente GPU Fix.

Esto permite a los usuarios pasar rápidamente del diagnóstico a la acción correctiva.

---

# Sistema de Temas

La implementación predeterminada es:

```java
GPUFixOptimusPrime
```

El tema utiliza colores configurables mediante `ConfigColor`.

Las categorías configurables incluyen:

```text
Fondo
Panel
Texto
Botón
Texto del Botón
```

La imagen se carga desde:

```text
imagenes/optimus_prime.png
```

Si la imagen no puede cargarse, la etiqueta muestra como alternativa:

```text
Optimus Prime
```

---

# Localización

Todo el texto visible para el usuario se obtiene mediante:

```java
MonitorDePID.idioma
```

Esto incluye:

* Título de la ventana
* Instrucciones específicas de cada plataforma
* Etiquetas de botones
* Mensajes de error
* Mensajes de éxito
* Mensajes de sistema no soportado

Esto mantiene la compatibilidad de la interfaz GPU Fix con el sistema de idiomas de CrashDetector.

---

# Manejo de Errores

Si una corrección falla, GPU Fix registra la excepción mediante:

```java
CrashDetectorLogger.logException(...)
```

y muestra un mensaje de error al usuario.

Esto se aplica a:

* Fallos de comandos del registro
* Fallos de `pmset`
* Fallos al abrir URLs
* Fallos al detectar el ejecutable Java

---

# Usos Típicos

## Minecraft Utiliza los Gráficos Integrados

Forzar que Java utilice la GPU dedicada en Windows.

## Bajo Rendimiento en Portátiles con Gráficos Híbridos

Aplicar o explicar correcciones relacionadas con las preferencias de GPU.

## Problemas de Rendimiento con Shaders

Ayudar a garantizar que Minecraft se esté ejecutando sobre la GPU más potente.

## Seguimiento de Verificaciones de GPU

Proporcionar acciones correctivas después de que CrashDetector detecte un problema relacionado con la GPU.

## Solución de Problemas de Optimus en Linux

Dirigir a los usuarios hacia documentación para configurar manualmente Optimus.

---

# Resumen

GPU Fix es la herramienta de resolución de problemas gráficos de CrashDetector adaptada a cada plataforma.

Proporciona:

* Detección del sistema operativo
* Aplicación de preferencias de GPU de alto rendimiento en Windows
* Cambio a GPU dedicada en macOS
* Guías para NVIDIA Optimus en Linux
* Integración con el Administrador de Rendimiento
* Tema configurable de Optimus Prime
* Texto localizado para el usuario

lo que la convierte en una herramienta complementaria especializada para resolver problemas de selección de procesador gráfico en Minecraft.

