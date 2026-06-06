# Documentación de Guard

## Descripción General

Guard es el sistema integrado de análisis de seguridad de CrashDetector.

Su propósito es ayudar a los usuarios a identificar mods potencialmente maliciosos, clases sospechosas y servidores multijugador problemáticos mediante una combinación de análisis local y bases de datos externas de definiciones.

A diferencia del software antivirus tradicional, Guard está diseñado específicamente para entornos de Minecraft y modpacks. Se centra en amenazas relevantes para jugadores de Minecraft, mantenedores de modpacks y equipos de soporte.

Guard combina:

* Detección de malware
* Identificación de clases sospechosas
* Detección de servidores problemáticos
* Inspección de código fuente descompilado
* Escaneo basado en definiciones
* Herramientas de investigación manual

Está diseñado como una herramienta de diagnóstico e investigación y no como un reemplazo de las soluciones de seguridad tradicionales.

---

# Arquitectura

Guard combina varios subsistemas de CrashDetector.

```text
ModsTree
    │
    ▼
Análisis de Malware
    │
    ▼
Guard
    │
    ├── Escáner de Servidores
    │
    ├── Escáner de Malware
    │
    └── Integración CFR
```

Los resultados se presentan mediante una interfaz unificada que permite investigar los hallazgos con mayor profundidad.

---

# Filosofía de Diseño

Guard opera bajo un principio simple:

```text
Identificar
    ↓
Investigar
    ↓
Verificar
```

Guard evita deliberadamente eliminar archivos automáticamente o poner mods en cuarentena.

El usuario mantiene el control de todas las acciones.

Esto reduce el riesgo de que falsos positivos provoquen daños no deseados.

---

# Interfaz de Usuario

La implementación predeterminada es:

```java
GuardiaSketchyVT
```

La interfaz consta de tres secciones principales.

---

## Panel de Información

El panel izquierdo contiene:

* Marca Guard
* Guía de uso
* Información de seguridad
* Arte de SketchyVT

Esta sección explica el propósito del escáner y recuerda a los usuarios que pueden producirse falsos positivos.

---

## Análisis de Servidores

La tabla superior muestra servidores problemáticos.

Las columnas incluyen:

```text
Servidor
Fuente de Definición
```

---

## Análisis de Malware

La tabla inferior muestra hallazgos de malware.

Las columnas incluyen:

```text
Mensaje
Ubicación
Clase
CFR
```

Esto permite investigar detecciones individuales.

---

# Modos de Escaneo

Guard soporta tres modos de escaneo.

---

## Escaneo Completo

```text
Escanear Todo
```

Realiza:

* Análisis de servidores
* Análisis de malware

Es la opción recomendada para la mayoría de los usuarios.

---

## Escaneo de Servidores

```text
Escanear Servidores
```

Solo comprueba definiciones de servidores.

No se realiza análisis de malware.

---

## Escaneo de Malware

```text
Escanear Malware
```

Solo analiza mods y clases locales.

No se realizan comprobaciones de servidores.

---

# Procesamiento en Segundo Plano

Todos los escaneos se ejecutan mediante:

```java
SwingWorker
```

Esto permite que Guard permanezca responsivo mientras el análisis está en ejecución.

Durante el escaneo:

* Los controles se deshabilitan.
* Se muestra una superposición de carga.
* La información de estado se actualiza.

Una vez completado:

* Se muestran los resultados.
* Los controles se restauran.
* La superposición desaparece.

---

# Detección de Malware

El análisis de malware se realiza mediante:

```java
ModsMalware.escanear()
```

El escáner devuelve una colección de hallazgos.

Cada hallazgo contiene:

* Mensaje de detección
* Ubicación del archivo
* Nombre de la clase
* Metadatos

Estos hallazgos se muestran en la tabla de malware.

---

# Resultados de Malware

Cada resultado representa un descubrimiento potencialmente sospechoso.

Ejemplo:

```text
Mensaje:
Descargador malicioso conocido

Ubicación:
mods/example.jar

Clase:
com.example.BadClass
```

El significado exacto depende de la regla de detección que haya activado el resultado.

---

# Falsos Positivos

Guard puede ocasionalmente reportar falsos positivos.

Esto puede ocurrir porque:

* Las firmas de seguridad no son perfectas.
* Algunos mods legítimos realizan operaciones inusuales.
* La ofuscación a veces es utilizada por software legítimo.
* Las detecciones heurísticas son inherentemente probabilísticas.

Por esta razón, los hallazgos deben investigarse en lugar de aceptarse ciegamente.

---

# Análisis de Servidores

Guard puede identificar servidores multijugador problemáticos.

Las definiciones de servidores se obtienen mediante:

```java
ServidoresProblematicos
```

El escáner compara los servidores descubiertos contra definiciones conocidas.

---

# Base de Datos de Definiciones

El análisis de servidores depende de definiciones externas.

Cuando las definiciones no están disponibles, Guard ofrece descargarlas.

Cuando las definiciones ya existen, los usuarios pueden elegir entre:

```text
Usar Definiciones Locales
```

o

```text
Actualizar Definiciones
```

Esto permite equilibrar actualidad y velocidad.

---

# Fuentes de Definiciones

La implementación actual hace referencia a:

```text
Definiciones de TLauncher
```

como fuente de información sobre servidores problemáticos conocidos.

Las versiones futuras podrían soportar proveedores adicionales.

---

# Política Interactiva de Definiciones

Antes de escanear servidores, Guard pregunta cómo deben gestionarse las definiciones.

Los posibles resultados incluyen:

## Descargar Definiciones

Se utiliza cuando no existen definiciones.

---

## Utilizar Definiciones Existentes

Utiliza datos almacenados localmente.

---

## Actualizar Definiciones

Descarga las definiciones más recientes antes de realizar el análisis.

---

## Cancelar

Cancela el escaneo.

---

# Tabla de Malware

Cada resultado de malware contiene varias columnas.

---

## Mensaje

Explicación legible para humanos de la detección.

Ejemplo:

```text
Patrón sospechoso de ejecución remota de código
```

---

## Ubicación

El mod o archivo asociado con el hallazgo.

Ejemplo:

```text
mods/example.jar
```

---

## Clase

La clase afectada.

Ejemplo:

```text
com.example.Backdoor
```

---

## CFR

Un enlace directo al descompilador de CrashDetector.

---

# Integración CFR

Una de las características más potentes de Guard es su integración con CFR.

Cuando una detección incluye un nombre de clase, se habilita un botón CFR.

Seleccionar:

```text
CFR
```

hace que Guard abra inmediatamente la clase.

Internamente, Guard genera:

```text
cfr://com.example.ClassName
```

y lo pasa al subsistema CFR.

---

# Investigación Mediante Descompilación

Esto permite a los usuarios inspeccionar:

* Actividad de red
* Lógica de descarga
* Manipulación de archivos
* Uso de reflexión
* Cargadores de clases
* Patrones de ofuscación

sin salir de CrashDetector.

---

# Flujo de Investigación

Un flujo típico podría ser:

```text
Detección de Malware
        ↓
Seleccionar Resultado
        ↓
Abrir CFR
        ↓
Inspeccionar Código Fuente
        ↓
Determinar Legitimidad
```

Esto reduce drásticamente el tiempo de investigación.

---

# Sistema de Estado

Guard mantiene mensajes de estado visibles.

Ejemplos:

```text
Listo
Escaneando Servidores
Escaneando Malware
Escaneando Todo
```

Estos mensajes proporcionan retroalimentación durante operaciones prolongadas.

---

# Superposición de Carga

La interfaz incluye una superposición de carga.

La superposición:

* Evita interacciones accidentales.
* Indica progreso.
* Reduce la confusión durante los escaneos.

Todas las operaciones importantes de análisis activan automáticamente esta superposición.

---

# Sistema de Temas

La implementación SketchyVT expone colores configurables.

Las categorías incluyen:

## Apariencia General

```text
Fondo
Paneles
Texto Principal
Texto Secundario
```

---

## Tablas

```text
Fondo de Tabla
Color de Selección
Texto Seleccionado
```

---

## Superposición

```text
Texto de Carga
```

---

## Arte

El tema soporta:

```text
SketchyVT
SketchyVT Original
```

mediante una configuración configurable.

---

# Estilo de Tablas

Ambas tablas de análisis soportan:

* Colores temáticos
* Resaltado de selección
* Encabezados personalizados
* Actualizaciones dinámicas

Esto garantiza legibilidad incluso cuando existen grandes cantidades de hallazgos.

---

# Manejo de Errores

Guard intenta recuperarse elegantemente de errores.

Ejemplos incluyen:

* Fallos al descargar definiciones
* Excepciones durante el análisis
* Fallos al iniciar CFR

Los errores se registran y se presentan al usuario cuando corresponde.

La interfaz sigue siendo utilizable incluso después de un fallo.

---

# Características de Rendimiento

Guard está diseñado para escalar eficientemente.

Características:

* Escaneo en segundo plano
* Ejecución asíncrona
* Actualizaciones incrementales de tablas
* Definiciones almacenadas en caché
* Navegación integrada entre clases

Por ello, incluso modpacks grandes pueden analizarse sin congelar la interfaz.

---

# Usos Típicos

## Investigación de Malware

Identificar mods potencialmente maliciosos.

---

## Auditoría de Seguridad

Revisar la postura de seguridad de un modpack.

---

## Solicitudes de Soporte

Recopilar evidencias para reportes relacionados con malware.

---

## Análisis de Seguridad de Servidores

Identificar servidores problemáticos conocidos.

---

## Ingeniería Inversa

Inspeccionar clases sospechosas mediante CFR.

---

## Mantenimiento de Modpacks

Verificar la legitimidad de los mods incluidos.

---

# Relación con Otras Herramientas de CrashDetector

Guard se integra estrechamente con varios sistemas de CrashDetector.

## ModsTree

Proporciona descubrimiento de clases y propiedad de las mismas.

## Navegador CFR

Proporciona descompilación de código fuente.

## Mapa de Dependencias

Permite investigar dependencias sospechosas con mayor profundidad.

## SpongeMixin Explorer

Puede utilizarse para inspeccionar mixins encontrados dentro de mods sospechosos.

En conjunto, estas herramientas permiten rastrear hallazgos de seguridad desde detecciones de alto nivel hasta líneas individuales de código.

---

# Resumen

Guard es el entorno dedicado de análisis de seguridad de CrashDetector.

Al combinar detección de malware, análisis de servidores, gestión de definiciones e integración con CFR, proporciona un conjunto práctico de herramientas para investigar problemas de seguridad dentro de entornos de Minecraft modificado.

Las características principales incluyen:

* Escaneo de malware
* Detección de servidores problemáticos
* Gestión de definiciones
* Integración CFR
* Flujos de investigación interactivos
* Análisis en segundo plano
* Personalización de temas
* Integración con el ecosistema general de CrashDetector

lo que lo convierte en un recurso valioso para jugadores, equipos de soporte, mantenedores de modpacks y desarrolladores que investigan cuestiones relacionadas con la seguridad.

