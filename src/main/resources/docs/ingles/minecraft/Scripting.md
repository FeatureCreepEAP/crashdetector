# Script IDE

## Overview

Script IDE is an integrated scripting environment built into CrashDetector.

The goal is to provide a lightweight but powerful editor for modding, automation, datapacks, resource packs, configuration formats, scripting languages, and FeatureCreep Datafied Content.

The IDE supports:

* Syntax highlighting
* TextMate grammars
* Language-specific comment and bracket rules
* IntelliSense
* Local language servers
* External LSP servers
* Diagnostics
* Auto-completion
* Automatic dependency downloading
* Multiple project types

The implementation is intentionally modular:

* `ScriptIDEGUI` provides common editor functionality.
* `ScriptIntellisense` provides language intelligence.
* `ScriptIntellisenseLocal` provides fallback completion.
* `ScriptIntellisenseLsp4j` provides LSP client support.
* Internal language servers implement `ScriptIntellisense`.
* TextMate grammars provide syntax coloring independently from IntelliSense.

---

# Required Dependencies

The IDE automatically downloads:

```text
org.eclipse.lsp4j:org.eclipse.lsp4j
org.eclipse.lsp4j:org.eclipse.lsp4j.jsonrpc
org.jboss:jboss-dmr
```

These are downloaded into:

```text
~/crash_detector/deps
```

LSP4J provides the client implementation.

JBoss DMR is required by FeatureCreep Datafied Content projects.

---

# TextMate Support

The IDE supports TextMate grammars.

Downloaded grammars are stored in:

```text
~/crash_detector/scriptide/textmate/
```

Each project type may provide:

```text
syntax.tmLanguage.json
language-configuration.json
```

The IDE parses:

* comments
* brackets
* auto-closing pairs
* surrounding pairs
* keyword scopes
* string scopes
* comment scopes
* function scopes
* number scopes

This system works independently from IntelliSense.

Even without an LSP server the editor still provides:

* syntax coloring
* comment handling
* bracket matching
* automatic bracket insertion

---

# FeatureCreep Datafied Content

## Language

FeatureCreep Datafied Content uses:

```text
JBoss DMR
JSON
```

Extensions:

```text
.dmr
.json
```

## IntelliSense

FeatureCreep uses an internal language server.

No external server is required.

The internal server understands:

* DMR structures
* JSON structures
* FeatureCreep object layouts
* dependency maps
* registries
* content definitions

Because JBoss DMR is expected on the classpath, the IDE can directly parse DMR content.

No additional installation is required.

---

# KubeJS

## Language

KubeJS is JavaScript.

Extensions:

```text
.js
.json
```

## Language Server

Required:

```bash
npm install -g typescript
npm install -g typescript-language-server
```

The IDE uses:

```text
typescript-language-server
```

through LSP4J.

## Benefits

Provides:

* completion
* hover information
* diagnostics
* symbol lookup
* navigation

The IDE can generate TypeScript definition files later to improve mod API support.

---

# Mineflayer

## Language

Mineflayer is JavaScript.

Even though it controls Minecraft bots, it is fundamentally a Node.js ecosystem project.

Extensions:

```text
.js
.json
```

## Required Installation

```bash
npm install -g typescript
npm install -g typescript-language-server
```

Within the project:

```bash
npm install mineflayer
```

Optional:

```bash
npm install mineflayer-pathfinder
npm install prismarine-viewer
npm install vec3
```

## IntelliSense

Uses:

```text
typescript-language-server
```

The TypeScript server automatically understands installed npm packages.

When Mineflayer is installed through npm, the IDE receives:

* completion
* diagnostics
* type information

directly from the package definitions.

---

# ZenScript

## Language

CraftTweaker ZenScript.

Extensions:

```text
.zs
.zenscript
```

## TextMate

The IDE downloads:

```text
ZenScript-VSCodeLanguage
```

automatically.

Provides:

* syntax highlighting
* comments
* bracket rules

## IntelliSense

Currently local.

May later use:

```text
ZenScript Language Server
```

if a stable implementation becomes available.

---

# GroovyScript

## Language

Groovy.

Extensions:

```text
.groovy
```

## Language Server

Recommended:

```text
groovy-language-server
```

Stored as:

```text
~/crash_detector/groovy-language-server-all.jar
```

Configured command:

```bash
java -jar groovy-language-server-all.jar
```

## Notes

The server only understands external APIs if the corresponding jars are present on its classpath.

For Minecraft modding this means:

* Minecraft jars
* Forge jars
* NeoForge jars
* Fabric jars
* Mod jars

must be visible to the language server.

---

# ComputerCraft / CC:Tweaked

## Language

Lua.

Extensions:

```text
.lua
```

## Language Server

Recommended:

```text
lua-language-server
```

## Installation

```bash
lua-language-server
```

## Configuration

The IDE generates:

```text
.luarc.json
```

automatically.

The generated configuration registers:

```text
colors
colours
disk
fs
gps
http
keys
multishell
os
paintutils
parallel
peripheral
pocket
rednet
redstone
rs
settings
shell
term
textutils
turtle
vector
window
```

as ComputerCraft globals.

## Optional

The best experience comes from:

```text
lua-ls-cc-tweaked
```

which provides CC:Tweaked API typings.

---

# WorldEdit CraftScripts

## Language

CraftScripts are Rhino JavaScript.

Extensions:

```text
.js
```

## Language Server

Uses:

```text
typescript-language-server
```

## Generated Files

The IDE generates:

```text
jsconfig.json
```

and

```text
.crashdetector/types/worldedit-craftscript/index.d.ts
```

These provide definitions for:

```text
context
player
argv
Packages
importPackage()
importClass()
```

and common WorldEdit APIs.

## Benefits

Provides:

* completion
* diagnostics
* hover information

while still supporting Rhino-specific globals.

---

# Datapacks and Resource Packs

## Extensions

```text
.mcfunction
.json
.mcmeta
```

## Language Server

Recommended:

```text
Spyglass
```

## Features

Provides:

* command completion
* datapack diagnostics
* resource references
* namespace validation
* mcfunction support

The IDE maps:

```text
.mcfunction -> mcfunction
.json -> json
.mcmeta -> json
```

for language identification.

---

# JBoss FEEL

## Language

DMN FEEL.

Extensions:

```text
.feel
.dmn
```

## IntelliSense

Uses an internal language server.

No external installation required.

## Features

Provides:

* FEEL keyword completion
* FEEL function completion
* basic diagnostics

If Drools FEEL libraries exist on the classpath, the IDE can validate expressions through reflection.

Supported completions include:

```text
date
time
date and time
duration
substring
contains
count
min
max
sum
mean
median
stddev
if
then
else
for
some
every
satisfies
```

---

# Jexel3

## Language

Jexel3 scripting.

Extensions:

```text
.jexel
.txt
```

## IntelliSense

Internal language server.

No external dependencies.

Provides:

* completion
* diagnostics
* bracket validation

This implementation exists because no widely adopted Jexel LSP currently exists.

---

# Paradox Games (CWTools)

## Language Server

CWTools.

Supports:

* Stellaris
* Hearts of Iron IV
* Europa Universalis IV
* Crusader Kings II
* Crusader Kings III
* Victoria II
* Victoria III
* Imperator Rome

## Storage

```text
~/crash_detector/cwtools_lsp
```

Configuration folders:

```text
vic2-config
vic3-config
ck2-config
ck3-config
eu4-config
hoi4-config
stellaris-config
ir-config
```

## Features

CWTools provides:

* diagnostics
* completion
* navigation
* symbol lookup
* localization support
* script validation

for Clausewitz engine scripting.

---

# Auto Completion

The IDE provides two completion systems.

## LSP Completion

Provided by:

```text
ScriptIntellisenseLsp4j
```

Uses real language servers.

Provides:

* completion
* diagnostics
* hover
* navigation

## Local Completion

Provided by:

```text
ScriptIntellisenseLocal
```

Used when:

* no LSP exists
* no server is installed
* server startup fails

This guarantees the editor remains usable.

---

# Architecture

```text
ScriptIDEGUI
│
├── TextMate Grammar
│
├── ConfigLenguajeTextMate
│
├── ScriptIntellisense
│   │
│   ├── ScriptIntellisenseLocal
│   ├── ScriptIntellisenseLsp4j
│   ├── ServidorLenguajeDatafiedFC
│   ├── ServidorLenguajeFeel
│   └── ServidorLenguajeJexel3
│
└── LSP Servers
    │
    ├── TypeScript Language Server
    ├── Lua Language Server
    ├── Groovy Language Server
    ├── Spyglass
    └── CWTools
```

The overall design intentionally separates:

* syntax highlighting
* language configuration
* completion
* diagnostics
* project type support

so new languages can be added without modifying the editor core.

