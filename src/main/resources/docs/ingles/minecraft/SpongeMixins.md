# SpongeMixin Explorer Documentation

## Overview

The SpongeMixin Explorer is CrashDetector's dedicated visualisation and investigation tool for SpongePowered Mixin systems.

Its purpose is to discover, organise, inspect and analyse mixins present throughout a modpack, including mixins located within nested mods and embedded libraries.

Unlike traditional mixin debugging tools, the SpongeMixin Explorer is tightly integrated with ModsTree, CFR and CrashDetector's bytecode analysis framework, allowing users to move seamlessly between mixin metadata, conflict analysis and source-code decompilation.

The tool is particularly useful for:

* Mixin conflict investigation
* Mod compatibility research
* Reverse engineering
* Modpack debugging
* Injection analysis
* Target discovery
* SpongeMixin auditing
* Development and maintenance

---

# Architecture

The SpongeMixin Explorer combines several CrashDetector subsystems.

```text
ModsTree
    │
    ▼
Mixin Discovery
    │
    ▼
Mixin Explorer
    │
    ▼
Conflict Analysis
    │
    ▼
CFR Integration
```

Mixins are discovered directly from bytecode analysis rather than configuration files alone.

---

# User Interface

The default implementation is:

```java
MixinsGUIChiarru
```

The interface is divided into several areas:

## Mod Selection

Displays all mods containing mixins.

Users may:

* View all mods
* Filter to a specific mod
* Refresh the mixin database

---

## Mixin Tree

The tree view displays:

```text
Mod
 └─ Mixin
      ├─ Targets
      ├─ Methods
      └─ Fields
```

This provides a structured overview of every discovered mixin.

---

## Details Panel

Selecting any element displays detailed information.

Depending on the selected node, information may include:

* Mod location
* Mixin targets
* Injected methods
* Shadow fields
* Potential conflicts
* Decompiled source

---

## Character Panel

The default Chiarru theme includes:

* Chiarru artwork
* GoMix artwork
* Theme-aware colouring
* Configurable appearance settings

All colours are controlled through `ConfigColor` rather than hard-coded values.

---

# Loading Process

When the explorer is opened, CrashDetector performs a full preload.

```java
Buscador.cargarYPrecargarClasesEnCache()
```

This allows the explorer to operate efficiently even on large modpacks.

During loading:

1. Mods are discovered.
2. Classes are indexed.
3. Mixins are identified.
4. Targets are analysed.
5. The tree structure is constructed.

A loading overlay is displayed whilst this process is running.

---

# Mixin Discovery

The explorer scans every discovered class.

For each class:

```java
mod.esClaseMixin(...)
```

is used to determine whether the class represents a SpongeMixin.

Only valid mixin classes are added to the interface.

---

# Recursive Discovery

Mixin discovery is recursive.

The explorer searches:

* Standard mods
* Nested mods
* Jar-in-jar libraries
* Embedded dependencies

Using:

```java
mods_en_mods()
```

This ensures that hidden mixins are not overlooked.

---

# Mod View

The highest level of the tree displays mods containing at least one mixin.

Selecting a mod displays:

```text
Location
Mixin Count
```

This provides a quick overview of mixin usage within a particular mod.

---

# Mixin View

Selecting a mixin displays:

```text
Class Name
Owning Mod
Targets
Methods
Fields
```

The explorer retrieves information from the stored `MixinInfo` structure.

Example:

```text
Mixin Class:
com.example.ExampleMixin

Targets:
net.minecraft.world.entity.Entity

Methods:
injectMovement(...)
overwriteTick(...)

Fields:
shadowHealth
shadowPosition
```

---

# Target Analysis

Each mixin target is displayed beneath the mixin node.

Example:

```text
Targets
 ├─ net.minecraft.world.entity.Entity
 ├─ net.minecraft.world.entity.Player
 └─ net.minecraft.server.level.ServerPlayer
```

Selecting a target displays:

* Target name
* Number of conflicts
* Related mixins

---

# Method Analysis

Mixin methods are displayed separately.

Each method includes:

```text
Method Name
Descriptor
Target Methods
```

Example:

```text
injectTick(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V
```

Selecting a method displays all associated targets.

---

# Field Analysis

Mixin fields are grouped beneath the Fields category.

Information includes:

```text
Field Name
Descriptor
```

Example:

```text
shadowHealth I
```

This is particularly useful when investigating shadow fields and accessor mixins.

---

# Conflict Analysis

One of the most important features of the SpongeMixin Explorer is conflict detection.

The explorer can identify situations where multiple mixins affect the same target.

Example:

```text
Mixin A
        ┐
        ├─► Entity.tick()
        │
Mixin B
        ┘
```

Such situations frequently contribute to compatibility issues.

---

# Target Conflicts

Target conflict analysis searches for all mixins targeting the same class.

Example:

```text
Target:
net.minecraft.world.entity.Entity
```

Results:

```text
Mod A :: ExampleMixin
Mod B :: AnotherMixin
Mod C :: EntityMixin
```

This helps identify overlapping modifications.

---

# Method Conflicts

The explorer can also search for mixins targeting the same method.

Example:

```text
Entity.tick()
```

Results include:

* Mod
* Mixin class
* Method name
* Descriptor

This allows detailed investigation of injection collisions.

---

# Field Conflicts

Field analysis searches for matching shadow fields.

Example:

```text
health I
```

Results show all mixins interacting with the same field.

Whilst not all matches represent genuine conflicts, they often highlight areas requiring investigation.

---

# Conflict Browser

The context menu provides:

```text
Search for Conflicts
```

Selecting this option generates a dedicated conflict tree.

Results are grouped by:

* Target
* Method
* Field

This makes it easier to investigate large collections of overlapping mixins.

---

# Context Menu

Right-clicking an entry provides several actions.

## Search for Conflicts

Analyses the selected item for potential mixin conflicts.

## Decompile Selection

Opens the corresponding class within the CFR browser.

---

# CFR Integration

The SpongeMixin Explorer integrates directly with CrashDetector's CFR subsystem.

Selecting:

```text
Decompile Selection
```

causes the class to be passed to:

```java
CfrBase.descompilarClase(...)
```

The resulting source code is displayed immediately.

This allows developers to inspect:

* Injection logic
* Target methods
* Accessors
* Overwrites
* Shadow fields

without leaving CrashDetector.

---

# Icons

The explorer uses dedicated icons for different node types.

| Node Type | Meaning                 |
| --------- | ----------------------- |
| Mod       | Mod container           |
| Mixin     | Mixin class             |
| Target    | Target class            |
| Method    | Injection method        |
| Field     | Shadow or related field |
| Conflict  | Potential conflict      |

This improves navigation within large trees.

---

# Theme System

The Chiarru implementation exposes extensive customisation.

Configurable categories include:

## General Interface

```text
Background
Panels
Text
Tree View
Selection Colours
```

## Code Display

```text
Keywords
Strings
Comments
Numbers
Types
Methods
```

All colours are controlled through the configuration system and may be modified without recompiling CrashDetector.

---

# Performance

The explorer is designed for large modpacks.

Performance improvements include:

* Class caching
* Recursive indexing
* Lazy detail generation
* Background loading
* SwingWorker-based processing

This keeps the interface responsive whilst analysing thousands of classes.

---

# Typical Uses

## Mixin Conflict Investigation

Determine which mods are modifying the same game code.

## Compatibility Research

Identify potential incompatibilities before they cause crashes.

## Reverse Engineering

Inspect mixin behaviour without opening an external IDE.

## Mod Development

Review injections and targets during development.

## Modpack Maintenance

Audit mixins present throughout a pack.

## Crash Analysis

Determine whether a crash may be related to conflicting mixins.

---

# Relationship with Other CrashDetector Tools

The SpongeMixin Explorer works closely with:

## ModsTree

Provides class discovery and ownership information.

## Dependency Map

Provides cross-mod dependency analysis.

## CFR Browser

Provides source-code decompilation.

## ASM Analysis

Provides the underlying bytecode inspection used to identify mixins and their targets.

Together these tools form a comprehensive ecosystem for investigating mod interactions.

---

# Summary

The SpongeMixin Explorer is CrashDetector's dedicated SpongePowered Mixin analysis environment.

By combining recursive mod discovery, bytecode analysis, conflict detection and CFR integration, it provides a powerful interface for understanding how mixins interact throughout a modpack.

Key features include:

* Recursive mixin discovery
* Target inspection
* Method analysis
* Field analysis
* Conflict detection
* Source-code decompilation
* Theme customisation
* Integration with ModsTree and CFR

making it one of the most comprehensive mixin investigation tools available within the CrashDetector ecosystem.

