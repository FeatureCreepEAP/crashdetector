# MCreator Mod Scanner Documentation

## Overview

The MCreator Mod Scanner is a specialised CrashDetector utility designed to identify mods created using MCreator.

Many modpacks contain a mixture of hand-written mods, library mods and mods generated using MCreator. Whilst MCreator itself is a legitimate development tool, generated mods frequently exhibit distinct patterns which can be useful when diagnosing crashes, investigating compatibility issues, or analysing modpack composition.

The MCreator Mod Scanner provides a simple interface for discovering and listing all detected MCreator-generated mods within the currently loaded modpack.

The scanner is particularly useful for:

* Crash investigation
* Compatibility analysis
* Modpack auditing
* Support requests
* Quality assurance
* Development research
* Modpack maintenance

---

# Architecture

The scanner is built upon several CrashDetector subsystems.

```text
ModsTree
    │
    ▼
MCreator Scanner
    │
    ▼
Result Processing
    │
    ▼
Graphical Interface
```

The graphical interface is separated from the scanning logic, allowing multiple visual themes to share the same underlying functionality.

---

# User Interface Framework

The core implementation is:

```java
EscanerMCreatorGUI
```

This abstract class contains:

* Scan logic
* Result handling
* Localisation support
* Theme integration
* Configuration support

Concrete implementations provide the visual appearance whilst reusing the same scanning functionality.

---

# Rosemi LoveLock Theme

The default implementation is:

```java
EscanerMCreatorGUIRosemiLoveLock
```

This theme provides:

* Rosemi LoveLock artwork
* Configurable colours
* Rounded panels
* Styled result views
* Theme-aware controls

All appearance settings are controlled through `ConfigColor`.

No colours are hard-coded into the user experience.

---

# Purpose of the Scanner

The scanner attempts to identify mods that appear to have been generated using MCreator.

This information can be useful because:

* Generated mods often share common structures.
* Certain compatibility issues are more frequently observed in generated mods.
* Support requests may benefit from knowing whether a mod originated from MCreator.
* Large modpacks may contain significant numbers of generated mods that are otherwise difficult to identify.

The scanner is purely informational.

It does not classify MCreator mods as problematic automatically.

---

# Scanning Workflow

The workflow is intentionally simple.

```text
Press Scan
      ↓
Analyse Loaded Mods
      ↓
Identify MCreator Mods
      ↓
Generate Results
      ↓
Display Report
```

The process runs entirely in the background.

---

# Background Processing

Scanning is performed using:

```java
SwingWorker
```

This ensures that the interface remains responsive during analysis.

Whilst scanning:

* The results area displays progress information.
* The scan button is disabled.
* Status messages are updated.

Once the scan completes:

* Results are displayed.
* Controls are re-enabled.
* Completion status is shown.

---

# Result Generation

The scanner obtains results through:

```java
EscanerMCreator.obtainerMCreatorMods()
```

The returned information is then cleaned and formatted before presentation.

The scanner automatically removes duplicate headings and unnecessary formatting before displaying results to the user.

---

# Results Display

Results are displayed within a dedicated report area.

The report typically contains:

```text
MCreator Scan Results

Detected Mod A
Detected Mod B
Detected Mod C
```

followed by a completion message.

If no MCreator mods are detected, the scanner displays an appropriate message instead.

---

# Error Handling

Should an error occur during scanning, the scanner displays:

```text
Error During Scan
```

along with the underlying exception message.

The interface automatically recovers and re-enables the scan button.

This allows users to attempt another scan without restarting CrashDetector.

---

# Status Indicators

The scanner supports three primary states.

## Loading

```text
Loading...
```

Displayed whilst analysis is running.

---

## Completed

```text
Completed
```

Displayed when scanning finishes successfully.

---

## Error

```text
Error
```

Displayed if an exception occurs.

---

# Localisation Support

All user-facing text is retrieved through:

```java
MonitorDePID.idioma
```

This means:

* No user-visible strings are hard-coded.
* Language packs can override text.
* Community translations are supported.

The scanner automatically adapts to the active language.

---

# Theme Configuration

The Rosemi LoveLock implementation exposes numerous configurable appearance settings.

Examples include:

## Window Colours

```text
Window Background
Primary Text
Results Background
Status Colour
```

## Button Colours

```text
Button Background
Button Text
```

## Interface Panels

```text
Description Card
Results Card
Border Colours
```

All settings can be modified through CrashDetector's configuration system.

---

# Decorative Artwork

The Rosemi LoveLock theme supports decorative artwork.

The interface attempts to load:

```text
imagenes/rosemi.png
```

The image is:

* Loaded asynchronously
* Scaled automatically
* Displayed within the description panel

If the image cannot be found, the interface continues operating normally.

---

# Appearance Separation

The scanner follows CrashDetector's standard architectural pattern.

The abstract class contains:

```text
Logic
Scanning
Result Handling
Background Tasks
```

The concrete implementation contains:

```text
Colours
Fonts
Layout
Artwork
Visual Styling
```

This separation allows new themes to be created without modifying scanning behaviour.

---

# Performance Characteristics

The scanner is lightweight compared to tools such as:

* ModsTree
* Dependency Map
* SpongeMixin Explorer

The scanner does not perform deep bytecode analysis itself.

Instead, it relies upon the underlying MCreator detection subsystem to generate results efficiently.

This makes scan times relatively short even on large modpacks.

---

# Typical Uses

## Support Requests

Determine whether MCreator-generated mods are present in a pack.

---

## Modpack Auditing

Identify all generated mods in a distribution.

---

## Crash Investigation

Gather additional information when analysing crashes.

---

## Compatibility Research

Investigate whether generated mods may be involved in conflicts.

---

## Development Research

Study the prevalence of generated mods within a collection.

---

# Relationship with Other CrashDetector Tools

The MCreator Mod Scanner complements several other CrashDetector utilities.

## ModsTree

Provides mod discovery and metadata indexing.

## Dependency Map

Provides dependency relationship analysis.

## SpongeMixin Explorer

Provides mixin investigation tools.

## CFR Browser

Provides source-code decompilation.

The scanner provides high-level identification information which may then be investigated further using these more advanced tools.

---

# Summary

The MCreator Mod Scanner is a lightweight diagnostic utility designed to identify MCreator-generated mods within a modpack.

By combining background analysis, localisation support, configurable themes and integration with the wider CrashDetector ecosystem, it provides a simple and effective way to audit modpacks and gather useful diagnostic information.

Key features include:

* MCreator mod detection
* Background scanning
* Localised interface text
* Theme customisation
* Rosemi LoveLock visual theme
* Lightweight operation
* Integration with CrashDetector's analysis ecosystem

making it a valuable tool for support staff, modpack maintainers and developers alike.

