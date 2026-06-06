# CFR Integration Documentation

## Overview

CrashDetector includes a fully integrated CFR (Class File Reader) decompiler subsystem.

Unlike traditional decompiler frontends which invoke CFR against a JAR file on disk, CrashDetector integrates CFR directly into the ModsTree infrastructure. This allows classes to be decompiled directly from loaded mods, nested JARs, embedded libraries, and other containers without extracting files to disk.

The CFR subsystem consists of four major components:

* CFR Discovery
* CFR GUI Framework
* CFR User Interface
* In-Memory Decompilation Engine

---

# What is CFR?

CFR (Class File Reader) is a Java decompiler.

It converts Java bytecode:

```text
com/example/MyClass.class
```

back into readable Java source code.

CrashDetector uses CFR to:

* Inspect mod internals
* Investigate crashes
* Analyze mixins
* Explore unknown classes
* Examine embedded dependencies
* Aid dependency conflict debugging

---

# Architecture

The CFR implementation is divided into several classes:

```text
BuscarParaCFR
        │
        ▼
     CfrBase
        │
        ▼
 CfrSakuraRiddle
        │
        ▼
  DescompilarCFR
        │
        ▼
     ModsTree
        │
        ▼
      CFR API
```

---

# CFR Installation Discovery

CrashDetector automatically searches for CFR.

The search order is:

## Universal Dependency Folder

```text
~/crash_detector/deps
```

---

## Legacy CFR Folder

```text
~/crash_detector/cfr
```

If found, CrashDetector attempts to migrate CFR automatically into the modern dependency directory.

---

## Instance Dependency Folder

```text
<instance>/deps
```

---

## Common Linux Locations

Examples:

```text
/usr/share/java/CFR/cfr.jar
/usr/share/java/cfr.jar
/opt/cfr/cfr.jar
/usr/local/lib/cfr.jar
```

---

# Installing CFR

If CFR cannot be found, CrashDetector displays a GUI explaining that CFR is required.

The installation dialog provides:

## Download CFR

Downloads:

```text
https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar
```

---

## Open Installation Folder

Opens:

```text
~/crash_detector/deps
```

allowing users to drop CFR into the correct location.

---

# The cfr:// Protocol

CrashDetector introduces a custom protocol:

```text
cfr://com.example.MyClass
```

Selecting one of these links launches the CFR browser.

Example:

```text
cfr://net.minecraft.world.entity.Entity
```

The browser automatically:

1. Opens the CFR GUI.
2. Requests the class.
3. Decompiles it.
4. Displays the resulting Java source.

---

# The Sakura Riddle Browser

The default implementation is:

```java
CfrSakuraRiddle
```

This is a graphical decompilation browser.

Features include:

* Syntax highlighting
* Theme customization
* Class navigation
* Scrollable source view
* Sakura portrait panel

---

# Theme Customization

The CFR browser uses ConfigColor rather than hardcoded colors.

Available categories include:

## General UI

```text
tema.sakura.cfr.fondo
tema.sakura.cfr.texto
tema.sakura.cfr.borde
tema.sakura.cfr.fondo.retrato
```

---

## Syntax Highlighting

```text
tema.sakura.cfr.codigo.palabra.clave
tema.sakura.cfr.codigo.cadena
tema.sakura.cfr.codigo.comentario
tema.sakura.cfr.codigo.numero
tema.sakura.cfr.codigo.tipo
tema.sakura.cfr.codigo.metodo
```

These colors are applied through LectadorDeCodigo.

---

# In-Memory Decompilation

The most important feature of CrashDetector's CFR integration is that it never requires classes to exist as standalone files.

Instead it obtains class bytes directly from ModsTree.

Example:

```java
byte[] bytes =
    Buscador.obtenerBytesDeClase(
        "net.minecraft.world.entity.Entity"
    );
```

The class may originate from:

* A Forge mod
* A Fabric mod
* A nested JAR
* An embedded dependency
* A plugin
* A FeatureCreep module

The user does not need to know where the class physically exists.

---

# Class Name Normalization

Several formats are accepted.

Java format:

```text
com.example.MyClass
```

---

ASM format:

```text
com/example/MyClass
```

---

Class file path:

```text
com/example/MyClass.class
```

---

JVM descriptor:

```text
Lcom/example/MyClass;
```

All formats are normalized automatically.

---

# Integration With ModsTree

CFR never directly scans JAR files.

Instead it asks ModsTree for class bytes.

```java
Buscador.obtenerBytesDeClase(...)
```

This provides several advantages.

---

## Embedded JAR Support

Traditional decompilers frequently struggle with:

```text
mod.jar
 └── libs/
     └── dependency.jar
```

ModsTree already indexes nested archives.

CFR automatically gains support for them.

---

## Anonymous Storage

Classes can originate from:

```text
mod.jar!/library.jar!/another.jar!/Class.class
```

and CFR still receives them as normal byte arrays.

---

## No Temporary Files

Nothing is extracted to disk.

This reduces:

* Disk I/O
* Temporary storage usage
* Cleanup requirements

---

# Dependency Resolution

When CFR encounters references to additional classes:

```java
SomeOtherClass.doSomething();
```

it asks its custom ClassFileSource for those classes.

CrashDetector resolves them through ModsTree.

```java
Buscador.obtenerBytesDeClase(
    "SomeOtherClass"
);
```

This effectively turns ModsTree into a virtual classpath.

---

# Virtual Classpath

One of the most unique aspects of this implementation is that ModsTree behaves as a complete virtual Java classpath.

To CFR, all classes appear available even though they may physically exist in:

* Different mods
* Different archives
* Different nested archives
* Different loaders

This significantly improves decompilation accuracy.

---

# CFR Options

Current options include:

```text
hideutf=true
comments=false
```

These settings reduce output noise and improve readability.

Additional CFR options can easily be added through:

```java
Map<String,String> opciones
```

inside DescompilarCFR.

---

# Relationship With Bytecode Analysis

CrashDetector contains two separate analysis systems.

## ASM Analysis

Provided by:

```java
AnalizadorBytecodeASM
```

Used for:

* Mixins
* Method references
* Field references
* Constants
* Dependency graphs

---

## CFR Analysis

Provided by:

```java
DescompilarCFR
```

Used for:

* Human-readable source viewing
* Manual investigation
* Reverse engineering
* Developer diagnostics

---

# Typical Workflow

A typical diagnostic flow is:

1. CrashDetector identifies a problematic class.
2. ModsTree locates the owning mod.
3. ASM extracts references and metadata.
4. User clicks a cfr:// hyperlink.
5. CFR requests class bytes from ModsTree.
6. Source code is reconstructed.
7. User inspects the implementation.

This allows rapid investigation of issues without opening external tools.

---

# Future Possibilities

Because CFR already receives classes directly from ModsTree, future integrations could include:

* Clickable navigation between classes
* Method call graph visualization
* Mixin target navigation
* Decompiled dependency trees
* Automatic conflict explanations
* Side-by-side bytecode and source views
* Cross-reference search
* Symbol indexing
* Integrated source search

The current architecture already provides most of the underlying infrastructure required for these advanced features.

---

# Summary

CrashDetector's CFR subsystem is more than a simple decompiler launcher.

By combining CFR with ModsTree, the system effectively creates a virtual modpack-wide classpath capable of:

* Decompiling classes directly from memory
* Traversing nested archives
* Resolving dependencies automatically
* Browsing classes through cfr:// hyperlinks
* Integrating with mixin and bytecode analysis

This makes CFR a powerful developer and debugging tool tightly integrated with the CrashDetector ecosystem rather than a standalone external utility.

