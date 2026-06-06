# Documentación de CDLauncher

## Descripción General

CDLauncher es el sistema avanzado de relanzamiento y monitorización de Minecraft de CrashDetector.

Su propósito es relanzar Minecraft bajo la supervisión de CrashDetector mientras proporciona registro avanzado, análisis de fallos, servicios de perfilado y mecanismos de protección de consola.

A diferencia de un lanzador tradicional, CDLauncher no está diseñado para reemplazar lanzadores de Minecraft como el lanzador oficial, CurseForge, Prism Launcher o MultiMC. En su lugar, actúa como un entorno especializado de relanzamiento que inyecta la funcionalidad de CrashDetector en una ejecución de Minecraft ya configurada.

CDLauncher proporciona:

* Funcionalidad de relanzamiento
* Inyección de agentes Java
* Monitorización de consola
* Registro unificado
* Servicios de perfilado
* Servicios de trazado
* Servicios de muestreo
* Protección de Log4j
* Censura de tokens
* Saneamiento de la JVM

Su objetivo principal es maximizar la información de diagnóstico mientras minimiza la interferencia relacionada con el lanzador.

---

# Arquitectura

CDLauncher opera como un entorno de relanzamiento controlado.

```text
Lanzador Original
        │
        ▼
CDLauncher
        │
        ▼
Inyección de Agente Java
        │
        ▼
Proceso de Minecraft
        │
        ▼
Monitorización por CrashDetector
```

El proceso relanzado permanece bajo observación de CrashDetector durante toda su ejecución.

---

# Filosofía de Diseño

CDLauncher sigue varios principios clave.

## Sin Carga Doble

CrashDetector nunca debería existir simultáneamente como:

```text
Classpath
```

y

```text
Agente Java
```

Hacerlo puede crear:

* Conflictos de cargadores de clases
* Conflictos JPMS
* Excepciones de resolución
* Instrumentación duplicada

CDLauncher elimina automáticamente CrashDetector del classpath antes de la inyección.

---

## Monitorización Primero

El lanzador prioriza:

```text
Visibilidad
```

por encima de:

```text
Minimalismo
```

Se realiza todo el esfuerzo posible para capturar información útil de diagnóstico.

---

## Relanzamiento Seguro

El proceso de relanzamiento intenta preservar los argumentos existentes del lanzador mientras inyecta únicamente la funcionalidad requerida por CrashDetector.

---

# Flujo de Relanzamiento

El proceso de relanzamiento sigue una secuencia estructurada.

```text
Construir Comando
       │
Inyectar CrashDetector
       │
Configurar Registro
       │
Proteger Consola
       │
Lanzar Proceso
       │
Monitorizar Proceso
       │
Capturar Salida
```

Cada etapa contribuye al entorno de diagnóstico.

---

# Construcción del Comando

CDLauncher reconstruye el comando de lanzamiento a partir de:

```java
Statics.JVM_ARGS
```

y

```java
Statics.APP_ARGS
```

Posteriormente, modifica el comando resultante antes de ejecutarlo.

---

# Detección de JVM

Antes de iniciar el proceso, CDLauncher verifica que exista un ejecutable válido de Java.

Si es necesario, inserta automáticamente:

```java
MonitorDePID.jvm()
```

al principio del comando.

Esto garantiza que el comando de relanzamiento comience con un entorno Java válido.

---

# Saneamiento del Classpath

Una de las responsabilidades más importantes de CDLauncher es el saneamiento del classpath.

El lanzador elimina:

* JARs de CrashDetector
* Directorios de CrashDetector
* Rutas de instrumentación duplicadas

del classpath antes del lanzamiento.

Ejemplo:

```text
Antes:
minecraft.jar
forge.jar
crashdetector.jar

Después:
minecraft.jar
forge.jar
```

Esto evita problemas de carga duplicada.

---

# Inyección del Agente Java

CrashDetector se inyecta mediante:

```text
-javaagent:
```

en lugar de utilizar el classpath de la aplicación.

Ejemplo:

```text
-javaagent:crashdetector.jar=cdlauncher
```

Esto proporciona:

* Instrumentación en tiempo de ejecución
* Soporte de monitorización
* Compatibilidad JPMS
* Menos conflictos de cargadores de clases

El lanzador solo inyecta el agente si todavía no está presente.

---

# Sistema de Protección de Consola

Los entornos de Minecraft utilizan frecuentemente:

* JLine
* TerminalConsoleAppender
* Terminales interactivas

Estos sistemas pueden ocasionalmente provocar bloqueos durante el cierre.

CDLauncher inyecta automáticamente opciones protectoras para la JVM.

---

## Protección de JLine

Si no está configurado previamente:

```text
-Dterminal.jline=false
```

es inyectado automáticamente.

---

## Terminal No Soportada

Para evitar problemas con terminales interactivas:

```text
-Djline.terminal=jline.UnsupportedTerminal
```

es inyectado automáticamente.

---

## Supresión de Consola de Forge

CDLauncher también inyecta:

```text
-Dforge.logging.console.level=off
```

para reducir ruido innecesario en la consola.

---

# Sistema de Registro Unificado

CDLauncher redirige:

```text
stderr
```

hacia

```text
stdout
```

antes de iniciar la monitorización.

Esto produce:

* Orden consistente de logs
* Análisis simplificado
* Menor complejidad en la gestión de flujos

La salida resultante se procesa como un único flujo.

---

# Bomba de Flujos

Un hilo dedicado en segundo plano vacía continuamente la salida del proceso.

```text
Salida de Minecraft
        │
        ▼
Bomba de Flujos de CDLauncher
        │
        ▼
Archivo de Log
        │
        ▼
Consola de CrashDetector
```

Esto evita que la saturación de búferes congele el proceso objetivo.

---

# Registro de Salida

La salida capturada se escribe en:

```text
cd_launcherlog
```

mediante un escritor con búfer.

Características:

* Soporte UTF-8
* E/S con búfer
* Vaciado periódico
* Manejo automático de EOF

La implementación está optimizada para logs de gran tamaño.

---

# Censura de Tokens

La información sensible de autenticación se elimina automáticamente de los logs.

Ejemplos:

```text
--accessToken
```

y

```text
--clientId
```

Antes:

```text
--accessToken abc123456789
```

Después:

```text
--accessToken null
```

Esto protege las credenciales del usuario al compartir logs.

---

# Protección de Log4j

Muchos entornos de Minecraft incluyen configuraciones personalizadas de Log4j.

Estas pueden:

* Generar salida XML
* Utilizar layouts no soportados
* Crear logs difíciles de leer

CDLauncher sobrescribe estas configuraciones.

---

## Forzado de PatternLayout

Se extrae un archivo de configuración dedicado:

```text
log4j2-cdlauncher.properties
```

y se inyecta mediante:

```text
-Dlog4j.configurationFile=
```

Esto obliga a Log4j a utilizar un PatternLayout basado en texto.

---

## Limpieza de Configuración

Antes de aplicar su propia configuración, CDLauncher elimina argumentos existentes de configuración Log4j.

Ejemplos:

```text
-Dlog4j.configurationFile=
```

```text
-Dlog4j2.configurationFile=
```

```text
-Dlog4j.configuration=
```

```text
-Dlog4j2.configuration=
```

Esto evita que configuraciones suministradas por el lanzador sobrescriban el entorno de registro de CrashDetector.

---

# Monitorización de Procesos

Después de iniciar Minecraft, CDLauncher crea un hilo dedicado de monitorización.

El monitor ejecuta:

```java
MonitorDePID.monitor_cdlauncher(...)
```

Esto permite a CrashDetector:

* Observar el estado del proceso
* Detectar fallos
* Capturar diagnósticos
* Activar flujos de análisis

durante toda la ejecución.

---

# Sistema de Configuración

CDLauncher soporta servicios opcionales mediante:

```java
ConfigCDLauncher
```

Cada opción puede habilitarse o deshabilitarse de forma independiente.

---

# Servicios Disponibles

Los servicios actuales incluyen:

## Tracer

```text
CDTracer
```

Proporciona funcionalidad de trazado de ejecución.

---

## Profiler

```text
CDProfiler
```

Proporciona funcionalidad de perfilado de rendimiento.

---

## Sampler

```text
CDSampler
```

Proporciona funcionalidad de muestreo estadístico.

---

# Inyección de Opciones JVM

Los servicios habilitados generan automáticamente argumentos JVM.

Ejemplo:

```text
-Dcrashdetector.tracer=true
```

Estos argumentos se insertan en el comando de lanzamiento antes de la ejecución.

---

# Visibilidad del Log del Lanzador

CDLauncher imprime el comando final de lanzamiento antes de ejecutarlo.

La información sensible es censurada automáticamente.

Ejemplo:

```text
java
-Dcrashdetector.tracer=true
-javaagent:crashdetector.jar=cdlauncher
...
--accessToken null
```

Esto simplifica enormemente la depuración de lanzamientos.

---

# Página de Actualizaciones de la Aplicación

CDLauncher también incluye un sistema de noticias de Minecraft.

Esta funcionalidad está implementada mediante:

```java
PaginaDeActualizacionesMinecraft
```

---

# Obtención de Noticias

Las noticias se descargan desde:

```text
repo.tlauncher.org
```

utilizando el idioma actual de la interfaz.

La URL se genera dinámicamente.

Ejemplo:

```text
index_en.html
index_es.html
```

---

# Caché Local

Las noticias descargadas se almacenan localmente en:

```text
versiones_minecraft_cdlauncher.htm
```

Esto reduce:

* Uso de red
* Tiempos de carga
* Descargas repetidas

---

# Limpieza de HTML

La página descargada pasa por un procesamiento extenso.

El limpiador elimina:

* Scripts
* Elementos de diseño
* Imágenes
* Tablas
* Estilos

Solo se conserva el contenido de los artículos.

---

# Extracción de Noticias

El limpiador extrae los elementos:

```text
news_title
```

y

```text
news_desc
```

de la página original.

La salida resultante se simplifica para mostrarse dentro de componentes Swing.

---

# Compatibilidad con JEditorPane

El HTML final se transforma a un formato adecuado para:

```java
JEditorPane
```

Esto evita problemas de renderizado comunes en el motor HTML heredado de Swing.

---

# Características de Rendimiento

CDLauncher está diseñado para sesiones largas de Minecraft.

Características:

* Búferes de flujo grandes
* Escritura de archivos con búfer
* Monitorización asíncrona
* Bombeo de logs en segundo plano
* Caché de páginas de actualización
* Gestión ligera de configuración

Por lo tanto, el lanzador añade una sobrecarga mínima en comparación con la información diagnóstica obtenida.

---

# Usos Típicos

## Análisis de Fallos

Ejecutar Minecraft bajo supervisión de CrashDetector.

---

## Depuración de Lanzadores

Investigar problemas relacionados con argumentos de lanzamiento.

---

## Recolección de Logs

Generar logs de lanzador limpios y consistentes.

---

## Perfilado de Rendimiento

Habilitar servicios de perfilado.

---

## Trazado en Tiempo de Ejecución

Habilitar servicios de trazado.

---

## Solicitudes de Soporte

Generar logs compartibles con información sensible eliminada automáticamente.

---

# Relación con Otros Sistemas de CrashDetector

CDLauncher sirve como punto principal de entrada para varios subsistemas de CrashDetector.

## MonitorDePID

Proporciona monitorización de procesos.

## Agente de CrashDetector

Proporciona instrumentación en tiempo de ejecución.

## CDTracer

Proporciona trazado de ejecución.

## CDProfiler

Proporciona perfilado.

## CDSampler

Proporciona muestreo estadístico.

## Análisis de Logs del Lanzador

Proporciona análisis de diagnóstico posteriores al lanzamiento.

En conjunto, estos sistemas crean un entorno controlado para diagnosticar problemas de lanzamiento, ejecución y cierre de Minecraft.

---

# Resumen

CDLauncher es el entorno avanzado de relanzamiento de Minecraft de CrashDetector.

Al combinar inyección de agentes Java, saneamiento del classpath, monitorización de procesos, protección de consola, control de Log4j e instrumentación basada en servicios, proporciona una plataforma altamente fiable para recopilar información de diagnóstico.

Las características principales incluyen:

* Relanzamiento seguro
* Inyección de agentes Java
* Saneamiento del classpath
* Protección de consola
* Registro unificado
* Censura de tokens
* Control de configuración de Log4j
* Monitorización en tiempo de ejecución
* Soporte de perfilado
* Soporte de trazado
* Integración con noticias de Minecraft almacenadas en caché

lo que lo convierte en uno de los componentes centrales del ecosistema de diagnóstico de CrashDetector.

