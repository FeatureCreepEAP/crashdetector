# Documentación de GrepR

## Descripción General

GrepR es la utilidad avanzada de búsqueda de contenido en archivos de CrashDetector.

Inspirado en herramientas tradicionales de Unix como `grep`, `ripgrep` y otras utilidades de búsqueda recursiva de texto, GrepR permite a los usuarios buscar a través de grandes colecciones de archivos, directorios y archivos comprimidos desde dentro de CrashDetector.

A diferencia de las herramientas ordinarias de búsqueda de archivos, GrepR puede opcionalmente buscar dentro de archivos comprimidos y soporta tanto búsquedas de texto plano como búsquedas mediante expresiones regulares.

La herramienta es particularmente útil para:

* Investigación de modpacks de Minecraft
* Auditoría de configuraciones
* Análisis de fallos (crashes)
* Análisis de logs
* Desarrollo de mods
* Ingeniería inversa
* Investigación de dependencias
* Búsquedas masivas de contenido

GrepR proporciona una interfaz gráfica para realizar búsquedas que de otro modo requerirían herramientas de línea de comandos.

---

# Arquitectura

El sistema GrepR está dividido en varios componentes.

```text
GrepRGUI
    │
    ▼
Parámetros de Búsqueda
    │
    ▼
BusquedaArchivos
    │
    ▼
Colección de Resultados
    │
    ▼
Visor de Resultados
```

La interfaz gráfica recopila la entrada del usuario y pasa las solicitudes de búsqueda al motor de búsqueda subyacente.

---

# Interfaz de Usuario

La implementación predeterminada es:

```java
BusquedaGUISaliorMoon
```

La interfaz contiene:

* Selección de directorio
* Campo de cadena de búsqueda
* Opción de regex
* Opción de ignorar mayúsculas y minúsculas
* Opción de buscar dentro de archivos comprimidos
* Botón de búsqueda
* Área de resultados

Todos los controles se presentan en una única interfaz unificada.

---

# Flujo de Búsqueda

Una búsqueda típica sigue este proceso.

```text
Seleccionar Directorio
        │
Introducir Término de Búsqueda
        │
Elegir Opciones de Búsqueda
        │
Pulsar Buscar
        │
Realizar Escaneo
        │
Mostrar Resultados
```

La búsqueda se ejecuta en segundo plano y actualiza el área de resultados cuando se completa.

---

# Selección de Directorio

Los usuarios pueden elegir un directorio objetivo pulsando:

```text
Seleccionar Carpeta
```

Esto abre un selector de directorios.

Si no se especifica ningún directorio, GrepR utiliza por defecto:

```text
Directorio de Trabajo Actual
```

que normalmente es el directorio actual de ejecución de CrashDetector.

---

# Términos de Búsqueda

El campo de término de búsqueda contiene el texto o patrón que se desea buscar.

Ejemplos:

```text
optifine
```

```text
NullPointerException
```

```text
mods.toml
```

El significado del término de búsqueda depende de si el modo de expresiones regulares está habilitado.

---

# Búsquedas de Texto Plano

Cuando el modo Regex está deshabilitado, GrepR realiza una búsqueda de texto estándar.

Ejemplo:

```text
Mixin
```

Esto busca el texto literal:

```text
Mixin
```

dentro de los archivos.

---

# Búsquedas con Expresiones Regulares

Cuando el modo Regex está habilitado:

```text
Usar Regex
```

el término de búsqueda se interpreta como una expresión regular de Java.

Ejemplo:

```text
com\.example\..*
```

Esto permite realizar búsquedas complejas que abarcan muchos valores relacionados.

Las expresiones regulares son particularmente útiles para investigar:

* Nombres de paquetes
* Nombres de clases
* Firmas de métodos
* Formatos de versión
* Patrones de logs

---

# Búsquedas sin Sensibilidad a Mayúsculas

La opción:

```text
Ignorar Capitalización
```

hace que las búsquedas ignoren las diferencias entre caracteres en mayúsculas y minúsculas.

Ejemplo:

```text
Mixin
```

coincidirá con:

```text
mixin
```

```text
MIXIN
```

```text
Mixin
```

y otras variantes de capitalización.

Esto es útil al buscar en logs y archivos de configuración con formatos inconsistentes.

---

# Búsqueda Dentro de Archivos Comprimidos

Una de las características más potentes de GrepR es:

```text
Buscar Dentro de Archivos Comprimidos
```

Cuando está habilitada, el motor de búsqueda también inspecciona formatos de archivo comprimido soportados.

Esto permite realizar búsquedas dentro de:

```text
.jar
.zip
```

y otros contenedores comprimidos compatibles manejados por el motor de búsqueda subyacente.

Esto resulta especialmente útil en entornos de Minecraft donde gran parte del contenido relevante reside dentro de archivos JAR de mods.

---

# Procesamiento en Segundo Plano

Las búsquedas se ejecutan utilizando:

```java
SwingWorker
```

Esto evita que la interfaz gráfica se bloquee durante búsquedas largas.

Flujo:

```text
Iniciar Búsqueda
      │
Trabajador en Segundo Plano
      │
Recopilar Resultados
      │
Actualizar Interfaz
```

El usuario puede seguir interactuando con la aplicación mientras la búsqueda está en ejecución.

---

# Recolección de Resultados

Las búsquedas se realizan mediante:

```java
BusquedaArchivos.buscar(...)
```

El motor devuelve una colección de coincidencias.

Cada resultado se muestra en una línea separada dentro del área de resultados.

---

# Visualización de Resultados

Los resultados se presentan dentro de un área de texto desplazable.

Ejemplo:

```text
mods/examplemod.jar
config/example.toml
logs/latest.log
```

Cada entrada coincidente se muestra de forma independiente.

---

# Resultados Vacíos

Si no se encuentran coincidencias, GrepR muestra:

```text
No se encontraron resultados.
```

Esto permite a los usuarios distinguir entre:

* Un conjunto de resultados vacío
* Una búsqueda fallida

---

# Manejo de Errores

Si ocurre una excepción durante la búsqueda, la interfaz muestra:

```text
Error de Búsqueda
```

seguido por el mensaje de la excepción.

La interfaz permanece operativa y pueden realizarse búsquedas adicionales inmediatamente.

---

# Tema Salior Moon

La implementación predeterminada utiliza el tema Salior Moon.

```java
BusquedaGUISaliorMoon
```

Este tema proporciona:

* Apariencia azul oscuro
* Destacados dorados
* Botones de acento rojo
* Arte temático
* Colores configurables

---

# Arte del Tema

La interfaz carga:

```text
imagenes/saliormoongrep.png
```

La imagen se escala automáticamente para ajustarse a la interfaz.

Si no se puede localizar la imagen, se muestra una etiqueta de marcador de posición.

---

# Configuración de Apariencia

La implementación Salior Moon expone numerosas configuraciones de apariencia.

Las categorías incluyen:

## Colores de Ventana

```text
Fondo de Ventana
Fondo de Panel
Texto Principal
```

## Controles

```text
Fondo de Botón
Texto de Botón
Campos de Entrada
```

## Resaltado

```text
Color de Selección
Color de Texto Seleccionado
Borde Resaltado
```

Todas las configuraciones de apariencia están controladas mediante `ConfigColor`.

No existen colores codificados de forma fija dentro de la experiencia de usuario.

---

# Estilo de los Campos de Entrada

Los campos de texto soportan:

* Colores de fondo personalizados
* Colores de texto personalizados
* Colores de resaltado
* Colores de selección
* Bordes decorativos

Esto mejora la legibilidad durante búsquedas extensas.

---

# Visor de Resultados

El área de resultados soporta:

* Grandes conjuntos de resultados
* Selección de texto
* Operaciones de copiar y pegar
* Desplazamiento
* Colores temáticos

Esto la hace adecuada para trabajos de investigación y diagnóstico.

---

# Localización

Todas las cadenas visibles para el usuario se obtienen mediante:

```java
MonitorDePID.idioma
```

Esto incluye:

* Etiquetas
* Botones
* Mensajes de error
* Mensajes de estado de búsqueda
* Mensajes de resultados

Esto garantiza la compatibilidad con el sistema de localización de CrashDetector.

---

# Usos Típicos

## Búsqueda en Logs

Localizar errores específicos dentro de grandes colecciones de logs.

Ejemplo:

```text
NullPointerException
```

---

## Encontrar Referencias a Mods

Buscar referencias a un mod ID.

Ejemplo:

```text
create
```

---

## Investigación de Mixins

Buscar clases relacionadas con mixins.

Ejemplo:

```text
@Mixin
```

---

## Auditoría de Configuración

Localizar valores específicos de configuración.

Ejemplo:

```text
renderDistance
```

---

## Ingeniería Inversa

Buscar dentro de árboles de código fuente extraídos o entornos de desarrollo.

---

## Investigación de Archivos Comprimidos

Buscar directamente dentro de archivos JAR sin necesidad de extraerlos manualmente.

---

## Trabajo de Soporte

Localizar problemas recurrentes a través de grandes colecciones de logs y configuraciones.

---

# Relación con Otras Herramientas de CrashDetector

GrepR complementa varios sistemas de CrashDetector.

## ModsTree

Localiza clases, metadatos e información de propiedad.

## CFR Browser

Descompila clases descubiertas mediante búsquedas.

## Dependency Map

Investiga relaciones identificadas durante las búsquedas.

## SpongeMixin Explorer

Examina mixins descubiertos en los resultados de búsqueda.

## Mod History

Busca en instantáneas históricas y registros archivados.

En conjunto, estas herramientas proporcionan un entorno de investigación integral para grandes instalaciones de Minecraft.

---

# Resumen

GrepR es la utilidad de búsqueda recursiva de contenido en archivos de CrashDetector.

Al combinar búsquedas de texto plano, soporte para expresiones regulares, búsqueda dentro de archivos comprimidos, procesamiento en segundo plano y una interfaz gráfica configurable, proporciona una poderosa alternativa a las herramientas de búsqueda por línea de comandos.

Las características principales incluyen:

* Búsqueda recursiva de directorios
* Soporte para expresiones regulares
* Búsquedas sin sensibilidad a mayúsculas
* Inspección de archivos comprimidos
* Ejecución en segundo plano
* Visualización de resultados desplazable
* Interfaz temática Salior Moon
* Soporte de localización
* Apariencia configurable

lo que la convierte en una de las utilidades de investigación más útiles dentro del ecosistema de CrashDetector.

