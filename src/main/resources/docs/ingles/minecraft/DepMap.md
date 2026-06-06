# Dependency Map Documentation

## Overview

The Dependency Map is an advanced analysis and visualisation tool included with CrashDetector.

Its purpose is to discover, visualise and investigate relationships between mods based upon actual bytecode references rather than declared metadata. Whilst most dependency viewers rely solely upon information found in files such as `mods.toml`, `fabric.mod.json`, or `plugin.yml`, the Dependency Map examines the compiled code itself to determine which mods genuinely depend upon one another.

This allows the tool to reveal hidden dependencies, undocumented relationships, API usage, circular dependencies and version mismatches that would otherwise remain invisible.

The Dependency Map is particularly useful for:

* Missing dependency diagnosis
* Compatibility investigation
* Modpack maintenance
* API usage analysis
* Reverse engineering
* Embedded library inspection
* Mixin research
* FeatureCreep module analysis

---

# Architecture

The Dependency Map is built upon several CrashDetector subsystems.

```text
ModsTree
    │
    ▼
ASM Bytecode Analysis
    │
    ▼
Dependency Analysis Engine
    │
    ▼
Dependency Graph
    │
    ▼
CFR Integration
```

Unlike traditional dependency tools, relationships are derived from actual bytecode references discovered during analysis.

---

# How Dependencies Are Discovered

The Dependency Map does not rely upon declared dependency metadata.

Instead, every class within every mod is analysed.

For example:

```java
SomeOtherModClass.doSomething();
```

or

```java
SomeOtherModClass.someField
```

When a class owned by Mod A references a class owned by Mod B, the system records a dependency.

```text
Mod A → Mod B
```

This indicates that Mod A directly references code provided by Mod B.

---

# Dependency Sources

The analyser inspects:

## Method References

```java
SomeClass.someMethod()
```

## Field References

```java
SomeClass.someField
```

## Cross-Mod References

Only references crossing mod boundaries are counted.

References entirely contained within a single mod are ignored.

```text
Mod A → Mod A
```

Such internal references are not considered dependencies.

---

# Recursive Mod Discovery

Dependency analysis includes:

* Standard mods
* Nested mods
* Jar-in-jar libraries
* Embedded dependencies
* Internal modules

The system recursively traverses all containers discovered by ModsTree.

```java
mods_en_mods()
```

This allows dependencies hidden within nested archives to be analysed alongside ordinary mods.

---

# Parallel Analysis

Large modpacks may contain:

* Hundreds of mods
* Hundreds of thousands of classes
* Millions of references

To improve performance, dependency analysis is performed in parallel.

The process:

1. Builds a global class ownership index.
2. Creates a worker pool.
3. Analyses multiple mods simultaneously.
4. Merges results into the final dependency map.

This significantly reduces analysis time on modern multi-core systems.

---

# Dependency Graph

The primary visualisation is a graph.

Each node represents a mod.

Each connection represents a discovered dependency.

```text
Mod A ─────► Mod B
```

meaning:

```text
Mod A references classes owned by Mod B
```

---

# Node Size

Node size reflects importance within the dependency ecosystem.

Larger nodes generally indicate:

* More dependants
* More dependencies
* Greater influence within the modpack

Frameworks and libraries often appear as the largest nodes.

Examples may include:

```text
Minecraft
Forge
NeoForge
Fabric API
Architectury
FeatureCreep Core
```

---

# Node Labels

Nodes display:

```text
Mod Name (Dependants)
```

Example:

```text
Architectury (37)
```

indicates that thirty-seven other mods depend upon Architectury.

---

# Navigation

The graph supports several navigation features.

## Zoom

Use the mouse wheel to zoom in and out.

## Pan

Click and drag empty space to move around the graph.

## Selection

Clicking a node selects it and displays additional information.

## Double Click

Double-clicking a mod automatically opens its dependency references.

---

# Dependency Tree View

The tree view provides a structured representation of relationships.

Each mod displays:

```text
Dependencies
Dependants
```

Example:

```text
Create
 ├─ Flywheel
 ├─ Forge

Dependants
 ├─ Steam Rails
 ├─ Create Additions
 └─ Additional Create Content
```

---

# Dependency Ranking

Mods are automatically ordered according to:

1. Number of dependants.
2. Number of dependencies.
3. Alphabetical order.

This naturally places important libraries and frameworks near the top.

---

# Reference Inspection

The Dependency Map can explain precisely why a dependency exists.

Example:

```text
com.example.MyClass.method()
    →
net.example.OtherClass.someMethod()
```

For every reference, the system records:

* Source mod
* Destination mod
* Source class
* Source method
* Destination class
* Destination method
* JVM descriptor

This allows direct investigation of dependency relationships.

---

# Cross-Mod Reference Browser

Any dependency link can be expanded to reveal all references responsible for creating the relationship.

```text
Mod A → Mod B
```

The browser can answer questions such as:

```text
Why does this mod depend upon that mod?
```

or

```text
Can this dependency be removed safely?
```

---

# CFR Integration

The Dependency Map integrates directly with the CFR browser.

Selecting a reference allows immediate decompilation of the source class.

Workflow:

```text
Dependency Reference
        ↓
Source Class
        ↓
CFR Browser
        ↓
Java Source
```

Classes are opened through the custom protocol:

```text
cfr://fully.qualified.ClassName
```

allowing rapid investigation without leaving CrashDetector.

---

# Context Menus

Right-clicking entries provides additional functionality.

## View References

Displays all references between two mods.

## Decompile

Opens the selected class in the CFR browser.

---

# Dependency Details

Selecting a dependency displays:

```text
Origin Mod
Destination Mod
Reference Count
```

providing a concise overview of the relationship.

---

# Dependants View

The Dependants view answers an important question:

```text
Which mods depend upon this mod?
```

Example:

```text
Curios
 ├─ Mod A
 ├─ Mod B
 ├─ Mod C
 └─ Mod D
```

This is particularly useful when considering the removal or replacement of a library.

---

# Circular Dependencies

Mutual dependencies are detected automatically.

Example:

```text
Mod A → Mod B
Mod B → Mod A
```

The graph renders these as bidirectional links.

Circular dependencies frequently reveal:

* Tight coupling
* Architectural issues
* Legacy design decisions
* Improper API boundaries

---

# Non-Aligned Dependency Detection

One of the most powerful features of the Dependency Map is Non-Aligned Dependency Detection.

This system searches for references into packages that no longer exist within a dependency.

Example:

Base mod:

```text
com.example.api
```

Dependent mod:

```java
com.example.api.SomeClass
```

If `SomeClass` no longer exists within the base mod, the dependency is considered non-aligned.

---

# What Non-Aligned Dependencies Detect

This feature can identify:

* Removed APIs
* Incorrect versions
* Incomplete ports
* Missing classes
* Broken shaded libraries
* Partial migrations

These issues frequently cause crashes, `ClassNotFoundException`s and compatibility problems.

---

# Non-Aligned Analysis Workflow

Select:

1. A base mod.
2. A package.

Then press:

```text
Check Non-Aligned Dependencies
```

The analyser will inspect every dependant and report invalid references.

---

# Non-Aligned Results

Each result includes:

```text
Dependant Mod
Missing Class
Origin Method
Referenced Package
```

This makes version mismatch diagnosis significantly easier.

---

# Privacy Features

The Dependency Map uses:

```java
ubicacion_para_publicar()
```

rather than raw filesystem paths.

This allows screenshots, reports and support requests to be shared publicly without exposing personal directory structures.

---

# Performance Characteristics

The Dependency Map relies heavily upon:

```java
Buscador.cargarYPrecargarClasesEnCache()
```

which preloads class metadata into memory.

Benefits include:

* Faster graph generation
* Faster reference browsing
* Faster dependency analysis
* Faster CFR navigation
* Reduced archive access

---

# Typical Uses

## Missing Dependency Investigation

Determine precisely which mod requires a missing dependency.

## Mod Removal Planning

Identify all dependants before removing a mod.

## API Analysis

Discover which mods consume a particular API.

## Compatibility Research

Investigate unexpected interactions between mods.

## Mixin Investigation

Trace references related to mixin targets.

## Reverse Engineering

Navigate large codebases without leaving CrashDetector.

## FeatureCreep Development

Analyse relationships between modules in complex FeatureCreep deployments.

---

# Summary

The Dependency Map is CrashDetector's bytecode-level dependency analysis system.

Rather than relying upon declared metadata, it discovers relationships from actual code references, producing a highly accurate representation of mod interactions.

Combined with ModsTree, ASM analysis and CFR integration, the Dependency Map provides:

* Interactive dependency graphs
* Dependency trees
* Reference inspection
* Circular dependency detection
* Non-aligned dependency discovery
* Direct source-code decompilation

making it one of the most powerful diagnostic and investigative tools available within the CrashDetector ecosystem.

