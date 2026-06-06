# Documentación de ModsTree

## Descripción General

ModsTree es el subsistema de descubrimiento y análisis de mods de CrashDetector.

Su propósito es cargar todos los mods presentes en una instancia, indexar su contenido, identificar qué formato de cargador utilizan, extraer metadatos, descubrir mods anidados y proporcionar una API unificada para buscar archivos, clases, métodos, campos, mixins y referencias de bytecode a través de un modpack completo.

ModsTree no está limitado a mods de Minecraft. También soporta diversos ecosistemas como módulos de FeatureCreep, plugins de Bukkit, plugins de Sponge y varios cargadores históricos de mods.

---

# Arquitectura

El sistema está construido alrededor de tres conceptos principales:

* `Buscador`
* `ArchivoDeMod`
* Implementaciones concretas como `ModPKZip` y `ModCarpeta`

## Buscador

`Buscador` es el punto de entrada global.

Es responsable de:

* Cargar todos los mods listados en `ultimo_mods`
* Crear objetos `ArchivoDeMod`
* Ejecutar indexación paralela
* Mantener un conjunto global de mods cargados
* Proporcionar APIs de búsqueda
* Realizar análisis de bytecode
* Gestionar cachés de clases

Una vez cargado, cada mod queda disponible mediante las APIs estáticas expuestas por `Buscador`.

---

# Tipos de Contenedores Soportados

ModsTree puede cargar:

* JAR
* ZIP
* LiteLoader `.litemod`
* WAR
* EAR
* RAR
* FeatureCreep `.fpm`
* Mods basados en directorios

Los contenedores anidados se descubren automáticamente.

Ejemplo:

```text
outer.jar
 └── META-INF/jarjar/
     └── dependency.jar
```

Ambos archivos se vuelven buscables.

Los mods anidados se representan mediante:

```java
mods_en_mods()
```

lo que permite recorrer recursivamente todo el árbol de dependencias.

---

# Carga Paralela

ModsTree está diseñado para modpacks muy grandes.

La carga utiliza un pool de hilos configurable.

El cargador automáticamente:

1. Calcula un número óptimo de hilos.
2. Crea un ThreadPoolExecutor.
3. Procesa cada mod en paralelo.
4. Indexa metadatos.
5. Construye cachés de búsqueda.

Esto reduce significativamente el tiempo de inicio al manejar cientos o miles de mods.

---

# Extracción de Metadatos

Durante la indexación, ModsTree extrae metadatos de numerosos formatos.

## MinecraftForge

```text
META-INF/mods.toml
```

Extrae:

* Mod ID
* Versión

---

## MinecraftForge Versiones Antiguas

```text
mcmod.info
```

Extrae:

* Mod ID
* Versión

---

## Fabric

```text
fabric.mod.json
```

Extrae:

* Mod ID
* Versión

---

## Quilt

```text
quilt.mod.json
```

Extrae:

* Mod ID
* Versión

---

## Rift

```text
riftmod.json
```

Extrae:

* Mod ID
* Versión

---

## LiteLoader

```text
litemod.json
```

Extrae:

* Mod ID
* Versión

---

## Bukkit / Paper

```text
plugin.yml
paper-plugin.yml
```

Extrae:

* Nombre del plugin
* Versión

---

## Sponge

```text
META-INF/sponge_plugins.json
```

Extrae:

* Plugin ID
* Versión

---

## NilLoader

```text
*.nilmod.css
```

Extrae:

* Mod ID
* Versión

---

## Flint

```text
flintmodule.json
```

Extrae:

* Mod ID
* Versión

---

## FeatureCreep

### Módulos Planos

```text
fcflat.properties
```

### Módulos JBoss

```text
modules.xml
```

### HOI4

```text
*.mod
```

Extrae:

* ID del módulo
* Versión

---

# Detección de Cargadores

Después de extraer metadatos, cada mod es probado contra los cargadores registrados.

Ejemplos incluyen:

* FeatureCreep
* Fabric
* Forge
* NeoForge
* Rift
* LiteLoader
* Bukkit
* Meddle
* NilLoader
* Sponge
* HMod
* Canary
* Risugami ModLoader
* M3L
* LitLaunch
* Flint
* DangerZone
* NXOpen

Un mod puede pertenecer a múltiples cargadores.

Los cargadores detectados se almacenan en:

```java
cargadores()
```

---

# Nombres de Mods

ModsTree intenta identificar todos los nombres asociados a un mod.

Las fuentes incluyen:

* Archivos de metadatos
* Entradas del Manifest
* Descriptores de módulos
* Metadatos específicos de cargadores

Estos nombres se vuelven buscables.

Ejemplo:

```java
mod.nombre()
```

---

# Detección de Versiones

La información de versión se extrae de:

* mods.toml
* fabric.mod.json
* quilt.mod.json
* plugin.yml
* Entradas del Manifest
* Metadatos de FeatureCreep
* Muchos otros formatos de cargadores

Accesible mediante:

```java
mod.version()
```

---

# Búsquedas

## Buscar un Archivo

```java
Buscador.obtenerModsConNombre("fabric.mod.json");
```

Devuelve todos los mods que contienen dicho archivo.

---

## Buscar una Clase

```java
Buscador.buscarModsConTermino(
    "net.minecraft.world.level.Level"
);
```

Devuelve todos los mods que contienen esa clase.

---

## Buscar un Paquete

```java
Buscador.buscarModsConTermino(
    "com.example"
);
```

Devuelve todos los mods que contienen clases dentro de ese paquete.

---

# Descubrimiento de Clases

ModsTree indexa cada clase descubierta.

Se soportan ambos formatos:

```text
com.example.Class
```

y

```text
com/example/Class
```

Comprobación de existencia:

```java
Buscador.existeClaseEnAlgunMod(
    "com/example/Class"
);
```

---

# Acceso a Bytecode

ModsTree puede recuperar los bytes brutos de una clase.

```java
byte[] bytes =
    Buscador.obtenerBytesDeClase(
        "com.example.Class"
    );
```

Entradas soportadas:

```text
com.example.Class
com/example/Class
com/example/Class.class
Lcom/example/Class;
```

Todas se normalizan automáticamente.

---

# Caché de Clases

Escanear ZIPs repetidamente es costoso.

ModsTree mantiene:

```java
cacheBytesClase
cacheBytesEntrada
```

para evitar descompresiones repetidas.

Las clases se cargan de forma perezosa.

Una clase solo se lee cuando es solicitada.

---

# Precarga Completa de Caché

Para análisis intensivos:

```java
Buscador.cargarYPrecargarClasesEnCache();
```

Esto carga todas las clases de todos los mods en memoria.

Útil para:

* Mapas de dependencias
* Análisis de bytecode
* Inspección de SpongeMixin
* Detección de conflictos

---

# Análisis de Bytecode

Cuando ASM está disponible, ModsTree puede inspeccionar:

* Métodos
* Campos
* Referencias
* Constantes
* Mixins

sin cargar las clases en un ClassLoader de la JVM.

Esto evita efectos secundarios de inicialización de clases.

---

# Análisis de Métodos

Recuperar métodos y referencias:

```java
Buscador.obtenerMetodosConReferenciasDeClase(
    "com/example/MyClass"
);
```

Devuelve:

* Nombre del método
* Descriptor
* Clases referenciadas
* Métodos referenciados
* Campos referenciados

---

# Análisis de Campos

```java
Buscador.obtenerCamposDeClase(
    "com/example/MyClass"
);
```

Devuelve todos los campos declarados.

---

# Análisis de Referencias

Buscar llamadas hacia un método:

```java
Buscador.buscarReferenciasHaciaMetodo(
    owner,
    method,
    descriptor
);
```

Útil para:

* Rastreo de dependencias
* Análisis de migración de APIs
* Investigación de conflictos

---

# Análisis de SpongeMixin

ModsTree incluye inspección de SpongeMixin basada en ASM.

Las características detectadas incluyen:

## Objetivos de Mixins

```java
@Mixin(...)
```

Los objetivos se extraen automáticamente.

---

## Métodos Inject

```java
@Inject
```

Los métodos se recopilan junto con sus objetivos.

---

## Métodos Overwrite

```java
@Overwrite
```

Los métodos overwrite se identifican por separado.

---

## Campos Shadow

```java
@Shadow
```

Los campos shadow se indexan.

---

## Descubrimiento de Objetivos

Soporta:

```java
@Mixin(Target.class)

@Mixin({
    TargetA.class,
    TargetB.class
})

@Mixin(targets = {
    "a.b.C"
})
```

---

# Árboles Recursivos de Mods

Una característica importante de ModsTree es el recorrido recursivo.

Métodos como:

```java
obtenerTodosLosModsYSubmodsRecursivos()
```

permiten analizar:

* Mods principales
* Dependencias jar-in-jar
* Bibliotecas embebidas
* Contenedores de plugins anidados

como un único grafo de búsqueda.

---

# Detección de MCreator

Los metadatos son analizados en busca de referencias a:

```text
mcreator
```

Accesible mediante:

```java
MetaDataTieneReferenciaDeMCReator()
```

Esto puede ser útil para diagnósticos y análisis de compatibilidad.

---

# Soporte de Privacidad

Las rutas pueden anonimizarse antes de su publicación.

```java
Buscador.rutaParaPublicar(...)
```

Esto elimina información específica del usuario al compartir logs o reportes.

---

# Usos Típicos Dentro de CrashDetector

ModsTree impulsa:

* Detección de dependencias faltantes
* Detección de clases faltantes
* Identificación de propietarios de clases
* Mapeo de dependencias
* Análisis de conflictos SpongeMixin
* Inspección de metadatos de mods
* Identificación de cargadores
* Detección de MCreator
* Análisis de referencias de bytecode
* Descubrimiento de bibliotecas embebidas
* Inspección jar-in-jar
* Diagnósticos avanzados de fallos

En la práctica, ModsTree actúa como la base de datos interna de CrashDetector para buscar cualquier mod, clase, archivo, fuente de metadatos y referencia de bytecode presente dentro de un modpack.

---

# Resumen

ModsTree es el subsistema central de descubrimiento, indexación y análisis de mods de CrashDetector.

Al combinar carga paralela, extracción de metadatos, descubrimiento recursivo de contenedores, cachés de clases, análisis ASM e integración con herramientas como Dependency Map, CFR, Guard y SpongeMixin Explorer, proporciona una vista unificada de todo el contenido de un modpack.

Las características principales incluyen:

* Descubrimiento de mods y plugins
* Soporte para múltiples cargadores
* Extracción de metadatos y versiones
* Detección automática de cargadores
* Descubrimiento recursivo de mods anidados
* Acceso directo a bytecode
* Caché de clases
* Análisis ASM
* Inspección de SpongeMixins
* Búsqueda de clases, paquetes y archivos
* Detección de MCreator
* Soporte de privacidad
* APIs avanzadas de búsqueda

convirtiéndolo en uno de los componentes más importantes y fundamentales del ecosistema de análisis de CrashDetector.

