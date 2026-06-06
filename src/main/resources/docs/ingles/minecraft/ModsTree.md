# ModsTree Documentation

## Overview

ModsTree is CrashDetector's mod discovery and analysis subsystem.

Its purpose is to load every mod present in an instance, index their contents, identify which mod loader format they use, extract metadata, discover nested mods, and provide a unified API for searching files, classes, methods, fields, mixins, and bytecode references across an entire modpack.

ModsTree is not limited to Minecraft mods. It also supports a number of other ecosystems such as FeatureCreep modules, Bukkit plugins, Sponge plugins, and several historical mod loaders.

---

# Architecture

The system is built around three primary concepts:

* `Buscador`
* `ArchivoDeMod`
* Concrete implementations such as `ModPKZip` and `ModCarpeta`

## Buscador

`Buscador` is the global entry point.

It is responsible for:

* Loading all mods listed in `ultimo_mods`
* Creating `ArchivoDeMod` objects
* Running parallel indexing
* Maintaining a global set of loaded mods
* Providing search APIs
* Performing bytecode analysis
* Managing class caches

Once loaded, every mod becomes available through the static APIs exposed by `Buscador`.

---

# Supported Container Types

ModsTree can load:

* JAR
* ZIP
* LiteLoader `.litemod`
* WAR
* EAR
* RAR
* FeatureCreep `.fpm`
* Directory based mods

Nested containers are automatically discovered.

Example:

```text
outer.jar
 └── META-INF/jarjar/
     └── dependency.jar
```

Both files become searchable.

Nested mods are represented through:

```java
mods_en_mods()
```

which allows recursive traversal of the entire dependency tree.

---

# Parallel Loading

ModsTree is designed for very large modpacks.

Loading uses a configurable thread pool.

The loader automatically:

1. Calculates an optimal thread count.
2. Creates a ThreadPoolExecutor.
3. Processes each mod in parallel.
4. Indexes metadata.
5. Builds search caches.

This significantly reduces startup time when handling hundreds or thousands of mods.

---

# Metadata Extraction

During indexing, ModsTree extracts metadata from numerous formats.

## MinecraftForge

```text
META-INF/mods.toml
```

Extracts:

* Mod ID
* Version

---

## MinecraftForge Old Versions

```text
mcmod.info
```

Extracts:

* Mod ID
* Version

---

## Fabric

```text
fabric.mod.json
```

Extracts:

* Mod ID
* Version

---

## Quilt

```text
quilt.mod.json
```

Extracts:

* Mod ID
* Version

---

## Rift

```text
riftmod.json
```

Extracts:

* Mod ID
* Version

---

## LiteLoader

```text
litemod.json
```

Extracts:

* Mod ID
* Version

---

## Bukkit / Paper

```text
plugin.yml
paper-plugin.yml
```

Extracts:

* Plugin name
* Version

---

## Sponge

```text
META-INF/sponge_plugins.json
```

Extracts:

* Plugin ID
* Version

---

## NilLoader

```text
*.nilmod.css
```

Extracts:

* Mod ID
* Version

---

## Flint

```text
flintmodule.json
```

Extracts:

* Mod ID
* Version

---

## FeatureCreep

### Flat Mods

```text
fcflat.properties
```

### JBoss Modules

```text
modules.xml
```

### HOI4

```text
*.mod
```

Extracts:

* Module ID
* Version

---

# Loader Detection

After metadata extraction, every mod is tested against registered loaders.

Examples include:

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

A mod may belong to multiple loaders.

Detected loaders are stored in:

```java
cargadores()
```

---

# Mod Names

ModsTree attempts to identify all names associated with a mod.

Sources include:

* Metadata files
* Manifest entries
* Module descriptors
* Loader specific metadata

These names become searchable.

Example:

```java
mod.nombre()
```

---

# Version Detection

Version information is extracted from:

* mods.toml
* fabric.mod.json
* quilt.mod.json
* plugin.yml
* manifest entries
* FeatureCreep metadata
* many other loader formats

Accessible through:

```java
mod.version()
```

---

# Searching

## Find a File

```java
Buscador.obtenerModsConNombre("fabric.mod.json");
```

Returns all mods containing that file.

---

## Find a Class

```java
Buscador.buscarModsConTermino(
    "net.minecraft.world.level.Level"
);
```

Returns every mod containing that class.

---

## Find a Package

```java
Buscador.buscarModsConTermino(
    "com.example"
);
```

Returns all mods containing classes inside that package.

---

# Class Discovery

ModsTree indexes every discovered class.

Both formats are supported:

```text
com.example.Class
```

and

```text
com/example/Class
```

Class existence checks:

```java
Buscador.existeClaseEnAlgunMod(
    "com/example/Class"
);
```

---

# Bytecode Access

ModsTree can retrieve raw class bytes.

```java
byte[] bytes =
    Buscador.obtenerBytesDeClase(
        "com.example.Class"
    );
```

Supported inputs:

```text
com.example.Class
com/example/Class
com/example/Class.class
Lcom/example/Class;
```

All are normalized automatically.

---

# Class Cache

Repeated ZIP scanning is expensive.

ModsTree maintains:

```java
cacheBytesClase
cacheBytesEntrada
```

to avoid repeated decompression.

Classes are loaded lazily.

A class is only read when requested.

---

# Full Cache Preloading

For intensive analysis:

```java
Buscador.cargarYPrecargarClasesEnCache();
```

This loads every class from every mod into memory.

Useful for:

* Dependency maps
* Bytecode analysis
* SpongeMixin inspection
* Conflict detection

---

# Bytecode Analysis

When ASM is available, ModsTree can inspect:

* Methods
* Fields
* References
* Constants
* Mixins

without loading classes into a JVM ClassLoader.

This prevents class initialization side effects.

---

# Method Analysis

Retrieve methods and references:

```java
Buscador.obtenerMetodosConReferenciasDeClase(
    "com/example/MyClass"
);
```

Returns:

* Method name
* Descriptor
* Referenced classes
* Referenced methods
* Referenced fields

---

# Field Analysis

```java
Buscador.obtenerCamposDeClase(
    "com/example/MyClass"
);
```

Returns all declared fields.

---

# Reference Analysis

Find callers of a method:

```java
Buscador.buscarReferenciasHaciaMetodo(
    owner,
    method,
    descriptor
);
```

Useful for:

* Dependency tracing
* API migration analysis
* Conflict investigation

---

# SpongeMixin Analysis

ModsTree includes ASM-based SpongeMixin inspection.

Detected features include:

## Mixin Targets

```java
@Mixin(...)
```

Targets are extracted automatically.

---

## Inject Methods

```java
@Inject
```

Methods are collected together with their targets.

---

## Overwrite Methods

```java
@Overwrite
```

Overwrite methods are identified separately.

---

## Shadow Fields

```java
@Shadow
```

Shadowed fields are indexed.

---

## Target Discovery

Supports:

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

# Recursive Mod Trees

A major feature of ModsTree is recursive traversal.

Methods such as:

```java
obtenerTodosLosModsYSubmodsRecursivos()
```

allow analysis of:

* Main mods
* Jar-in-jar dependencies
* Embedded libraries
* Nested plugin containers

as a single searchable graph.

---

# MCreator Detection

Metadata is scanned for:

```text
mcreator
```

references.

Accessible through:

```java
MetaDataTieneReferenciaDeMCReator()
```

This can be useful for diagnostics and compatibility analysis.

---

# Privacy Support

Paths can be anonymized before publication.

```java
Buscador.rutaParaPublicar(...)
```

This removes user-specific path information when sharing logs or reports.

---

# Typical Uses Inside CrashDetector

ModsTree powers:

* Missing dependency detection
* Missing class detection
* Class ownership lookup
* Dependency mapping
* SpongeMixin conflict analysis
* Mod metadata inspection
* Loader identification
* MCreator detection
* Bytecode reference analysis
* Embedded library discovery
* Jar-in-jar inspection
* Advanced crash diagnostics

In practice, ModsTree acts as CrashDetector's internal searchable database of every mod, class, file, metadata source, and bytecode reference present inside a modpack.

