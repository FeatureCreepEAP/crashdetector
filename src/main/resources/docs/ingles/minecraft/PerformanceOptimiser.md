# Performance Manager Documentation

## Overview

The Performance Manager is CrashDetector's central optimisation and performance analysis environment.

Its purpose is to analyse a Minecraft installation, identify performance bottlenecks and provide practical recommendations for improving game performance, stability and resource utilisation.

Unlike benchmark tools that merely report performance metrics, the Performance Manager attempts to identify actionable improvements by analysing:

* Environmental factors
* Installed mods
* Configuration files
* GPU-related issues

The system combines multiple specialised analysers into a single interface, allowing users to review performance recommendations from several different perspectives.

The Performance Manager is particularly useful for:

* Low FPS diagnosis
* Stuttering investigation
* Modpack optimisation
* Hardware troubleshooting
* Configuration auditing
* Performance tuning
* GPU compatibility issues

---

# Architecture

The Performance Manager acts as a unified front-end for several optimisation analysers.

```text
Environmental Analysis
          │
          ▼
Mod Analysis
          │
          ▼
Configuration Analysis
          │
          ▼
Performance Manager
          │
          ▼
Recommendation Reports
```

Each analyser operates independently and contributes findings to the final report.

---

# User Interface

The default implementation is:

```java
AdministradorDeRendimientoNightcore
```

The interface consists of:

* Description panel
* Analysis controls
* Results tabs
* GPU tools
* Loading overlay

---

# Initial Analysis

When the interface opens, an analysis is automatically started.

```text
Open Window
      │
      ▼
Run Analysis
      │
      ▼
Generate Reports
      │
      ▼
Display Results
```

This allows users to immediately view recommendations without manually starting the process.

---

# Analysis Categories

The Performance Manager organises findings into four categories.

---

## Summary

Provides a high-level overview of all findings.

The summary displays:

* Environmental findings
* Mod findings
* Configuration findings
* GPU warnings
* Compatibility notes

This serves as a quick starting point before investigating individual categories.

---

## Environmental Analysis

Environmental analysis focuses on factors external to Minecraft itself.

Examples may include:

* Operating system configuration
* Available memory
* Storage conditions
* Driver issues
* Runtime environment concerns

Recommendations are generated through:

```java
AnalizadorDeFactoresAmbientales
```

---

## Mod Analysis

Mod analysis examines installed mods and identifies opportunities for optimisation.

Analysis is performed through:

```java
AnalizadorDeModsOptimizacion
```

Potential findings may include:

* Inefficient mods
* Missing optimisation mods
* Known performance bottlenecks
* Redundant functionality
* Alternative implementations

Each recommendation is associated with an estimated impact level and risk level.

---

## Configuration Analysis

Configuration analysis reviews configuration files for settings that may negatively affect performance.

Analysis is performed through:

```java
AnalizadorDeConfigsOptimizacion
```

Examples may include:

* Excessively high render distances
* Inefficient graphical settings
* Expensive simulation options
* Poorly tuned optimisation mod settings

Suggested corrections are displayed alongside each finding.

---

# Analysis Workflow

Performance analysis follows a structured workflow.

```text
Gather Data
      │
      ▼
Environmental Analysis
      │
      ▼
Mod Analysis
      │
      ▼
Configuration Analysis
      │
      ▼
Generate Reports
```

Results from all analysers are merged into the final interface.

---

# Background Processing

Performance analysis is performed using:

```java
SwingWorker
```

This allows analysis to run without freezing the graphical interface.

Whilst analysis is running:

* Controls are disabled.
* A loading overlay is displayed.
* User interaction is restricted.

Once analysis completes:

* Reports are generated.
* Controls are re-enabled.
* The loading overlay is hidden.

---

# Findings Structure

Most recommendations use a common format.

Example:

```text
[High / Low]

Title

Description

Suggestion:
Recommended action
```

Each recommendation contains:

* Impact rating
* Risk rating
* Title
* Description
* Suggested action

This helps users prioritise changes.

---

# Impact Ratings

Each recommendation includes an impact estimate.

Examples may include:

```text
Low
Medium
High
```

Impact estimates indicate the potential performance benefit.

Higher-impact recommendations generally provide larger improvements.

---

# Risk Ratings

Each recommendation also includes a risk estimate.

Examples may include:

```text
Low
Medium
High
```

Risk estimates indicate the likelihood of unintended side effects.

For example:

* Installing a well-established optimisation mod may be low risk.
* Removing a core gameplay mod may be high risk.

---

# Summary View

The Summary tab provides aggregate information.

Example:

```text
Environmental Findings: 3
Mod Findings: 7
Configuration Findings: 4
```

If GPU issues are detected, an additional warning is displayed.

The summary also includes compatibility notes reminding users that optimisation advice should be evaluated carefully before implementation.

---

# Environmental Report

The Environmental tab displays detailed environmental findings.

Example:

```text
[High / Low]
Storage Bottleneck

The game is installed on a slow storage device.

Suggestion:
Move the instance to an SSD.
```

Each finding includes explanatory text and a recommended action.

---

# Mod Report

The Mods tab displays mod-specific recommendations.

Example:

```text
[Medium / Low]
examplemod

This mod is known to reduce performance under certain conditions.
```

Mod reports are intended to help users understand how individual mods affect performance.

---

# Configuration Report

The Configuration tab focuses on configuration settings.

Example:

```text
[High / Low]
options.txt

Render distance is significantly higher than recommended.

Suggestion:
Reduce render distance.
```

Configuration findings are particularly useful because they often provide substantial improvements without requiring any mod changes.

---

# GPU Integration

The Performance Manager integrates directly with CrashDetector's GPU analysis system.

When GPU issues have been detected:

```java
VerificacionGPU.hayProblema
```

an additional button becomes available.

---

# GPU Fix Integration

Selecting:

```text
Open GPU Fix
```

opens the dedicated GPU troubleshooting interface.

The Performance Manager automatically launches:

```java
GPUFixOptimusPrime
```

through the GPU Fix framework.

This allows users to move directly from performance analysis to GPU troubleshooting.

---

# Loading Overlay

The interface includes a dedicated loading overlay.

The overlay:

* Prevents accidental interaction
* Indicates active analysis
* Improves user feedback
* Reduces confusion during long scans

The overlay is displayed automatically whenever an analysis is running.

---

# Theme System

The default implementation uses the Nightcore theme.

```java
AdministradorDeRendimientoNightcore
```

This theme provides:

* Nightcore artwork
* Customisable colours
* Dark appearance
* High-contrast text
* Accent highlighting

---

# Nightcore Artwork

The interface attempts to load:

```text
imagenes/nightcore.png
```

The image is displayed within the description section.

If the artwork cannot be found, the interface falls back to a text label.

---

# Appearance Configuration

The Nightcore implementation exposes extensive customisation options.

Configurable categories include:

## Window Colours

```text
Background
Panel
Text
Secondary Text
```

---

## Controls

```text
Button Background
Button Text
Selection Colour
```

---

## Results Areas

```text
Summary
Environmental
Mods
Configurations
```

All colours are managed through `ConfigColor`.

No user-facing colours are hard-coded.

---

# Localisation Support

All visible text is obtained through:

```java
MonitorDePID.idioma
```

This ensures:

* Full localisation support
* Community translation compatibility
* Consistent language handling

No user-facing strings are embedded directly within the implementation.

---

# Responsive Behaviour

The interface supports:

* Window resizing
* Dynamic content updates
* Theme reloading
* Automatic report regeneration

The appearance can be refreshed without recreating the entire interface.

---

# Error Handling

Analysis failures are handled gracefully.

Exceptions are logged through:

```java
CrashDetectorLogger.logException(...)
```

The loading overlay is always removed, even when an error occurs.

This ensures the interface remains usable after a failure.

---

# Typical Uses

## FPS Optimisation

Identify opportunities for increasing frame rates.

---

## Modpack Tuning

Optimise heavily modded environments.

---

## Configuration Auditing

Review performance-sensitive settings.

---

## Hardware Investigation

Identify environmental factors limiting performance.

---

## GPU Troubleshooting

Detect and investigate graphics-related issues.

---

## Stability Improvements

Reduce performance-related instability and stuttering.

---

# Relationship with Other CrashDetector Tools

The Performance Manager integrates closely with several CrashDetector systems.

## GPU Fix

Provides dedicated GPU troubleshooting.

## VerificacionGPU

Detects graphics-related issues.

## Mod Analysis

Provides optimisation recommendations for installed mods.

## Configuration Analysis

Provides configuration tuning advice.

## Environmental Analysis

Provides system-level recommendations.

Together these systems form CrashDetector's primary performance optimisation ecosystem.

---

# Summary

The Performance Manager is CrashDetector's unified optimisation and performance analysis environment.

By combining environmental analysis, mod analysis, configuration auditing and GPU diagnostics, it provides a practical framework for identifying and resolving performance issues within Minecraft installations.

Key features include:

* Environmental analysis
* Mod optimisation recommendations
* Configuration auditing
* GPU integration
* Impact and risk assessment
* Background analysis
* Nightcore theme
* Localisation support
* Configurable appearance
* Integrated troubleshooting workflows

making it one of the most comprehensive optimisation tools available within the CrashDetector ecosystem.

