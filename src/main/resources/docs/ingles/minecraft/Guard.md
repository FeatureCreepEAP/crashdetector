# Guard Documentation

## Overview

Guard is CrashDetector's integrated security analysis system.

Its purpose is to help users identify potentially malicious mods, suspicious classes, and problematic multiplayer servers using a combination of local analysis and external definition databases.

Unlike traditional antivirus software, Guard is specifically designed for Minecraft environments and modded instances. It focuses on threats relevant to Minecraft players, modpack maintainers and support teams.

Guard combines:

* Malware detection
* Suspicious class identification
* Problematic server detection
* Decompiled source inspection
* Definition-based scanning
* Manual investigation tools

It is intended as a diagnostic and investigative tool rather than a replacement for traditional security software.

---

# Architecture

Guard combines several CrashDetector subsystems.

```text
ModsTree
    │
    ▼
Malware Analysis
    │
    ▼
Guard
    │
    ├── Server Scanner
    │
    ├── Malware Scanner
    │
    └── CFR Integration
```

Results are presented through a unified interface which allows users to investigate findings further.

---

# Design Philosophy

Guard operates on a simple principle:

```text
Identify
    ↓
Investigate
    ↓
Verify
```

Guard intentionally avoids automatically deleting files or quarantining mods.

The user remains in control of all actions.

This reduces the risk of false positives causing unintended damage.

---

# User Interface

The default implementation is:

```java
GuardiaSketchyVT
```

The interface consists of three primary sections.

---

## Information Panel

The left-hand panel contains:

* Guard branding
* Usage guidance
* Safety information
* SketchyVT artwork

This section explains the purpose of the scanner and reminds users that false positives may occur.

---

## Server Analysis

The upper table displays problematic servers.

Columns include:

```text
Server
Definition Source
```

---

## Malware Analysis

The lower table displays malware findings.

Columns include:

```text
Message
Location
Class
CFR
```

This allows users to investigate individual detections.

---

# Scan Modes

Guard supports three scanning modes.

---

## Full Scan

```text
Scan Everything
```

Performs:

* Server analysis
* Malware analysis

This is the recommended option for most users.

---

## Server Scan

```text
Scan Servers
```

Only checks server definitions.

No malware analysis is performed.

---

## Malware Scan

```text
Scan Malware
```

Only analyses local mods and classes.

No server checks are performed.

---

# Background Processing

All scans execute using:

```java
SwingWorker
```

This allows Guard to remain responsive whilst analysis is running.

During scanning:

* Controls are disabled.
* A loading overlay is displayed.
* Status information is updated.

Once complete:

* Results are displayed.
* Controls are restored.
* The loading overlay disappears.

---

# Malware Detection

Malware scanning is performed through:

```java
ModsMalware.escanear()
```

The scanner returns a collection of findings.

Each finding contains:

* Detection message
* File location
* Class name
* Metadata

These findings are displayed within the malware table.

---

# Malware Results

Each result represents a potentially suspicious discovery.

Example:

```text
Message:
Known malicious downloader

Location:
mods/example.jar

Class:
com.example.BadClass
```

The exact meaning depends upon the detection rule which triggered the result.

---

# False Positives

Guard may occasionally report false positives.

This can happen because:

* Security signatures are imperfect.
* Certain legitimate mods perform unusual operations.
* Obfuscation is sometimes used by legitimate software.
* Heuristic detections are inherently probabilistic.

For this reason, findings should be investigated rather than accepted blindly.

---

# Server Analysis

Guard can identify problematic multiplayer servers.

Server definitions are obtained through:

```java
ServidoresProblematicos
```

The scanner compares discovered servers against known definitions.

---

# Definition Database

Server analysis relies upon external definitions.

When definitions are unavailable, Guard offers to download them.

When definitions already exist, users may choose between:

```text
Use Local Definitions
```

or

```text
Update Definitions
```

This allows users to balance freshness and speed.

---

# Definition Sources

The current implementation references:

```text
TLauncher Definitions
```

as a source of known problematic server information.

Future versions may support additional providers.

---

# Interactive Definition Policy

Before scanning servers, Guard asks how definitions should be handled.

Possible outcomes include:

## Download Definitions

Used when no definitions exist.

---

## Use Existing Definitions

Uses locally cached data.

---

## Update Definitions

Downloads the latest available definitions before scanning.

---

## Cancel

Aborts the scan.

---

# Malware Table

Each malware result contains several columns.

---

## Message

Human-readable explanation of the detection.

Example:

```text
Suspicious remote code execution pattern
```

---

## Location

The mod or file associated with the finding.

Example:

```text
mods/example.jar
```

---

## Class

The affected class.

Example:

```text
com.example.Backdoor
```

---

## CFR

A direct link into CrashDetector's decompiler.

---

# CFR Integration

One of Guard's most powerful features is its integration with CFR.

When a detection includes a class name, a CFR button becomes available.

Selecting:

```text
CFR
```

causes Guard to open the class immediately.

Internally, Guard generates:

```text
cfr://com.example.ClassName
```

and passes it to the CFR subsystem.

---

# Decompiled Investigation

This allows users to inspect:

* Network activity
* Download logic
* File manipulation
* Reflection usage
* Class loaders
* Obfuscation patterns

without leaving CrashDetector.

---

# Investigation Workflow

A typical workflow might be:

```text
Malware Detection
        ↓
Select Result
        ↓
Open CFR
        ↓
Inspect Source
        ↓
Determine Legitimacy
```

This dramatically reduces investigation time.

---

# Status System

Guard maintains visible status messages.

Examples include:

```text
Ready
Scanning Servers
Scanning Malware
Scanning Everything
```

These messages provide feedback during long operations.

---

# Loading Overlay

The interface includes a loading overlay.

The overlay:

* Prevents accidental interaction.
* Indicates progress.
* Reduces user confusion during scans.

All major scan operations activate the overlay automatically.

---

# Theme System

The SketchyVT implementation exposes configurable colours.

Categories include:

## General Appearance

```text
Background
Panels
Primary Text
Secondary Text
```

---

## Tables

```text
Table Background
Selection Colour
Selection Text
```

---

## Overlay

```text
Loading Text
```

---

## Artwork

The theme supports:

```text
SketchyVT
SketchyVT Original
```

through a configurable setting.

---

# Table Styling

Both analysis tables support:

* Themed colours
* Selection highlighting
* Custom headers
* Dynamic updates

This ensures readability even when large numbers of findings are present.

---

# Error Handling

Guard attempts to recover gracefully from errors.

Examples include:

* Definition download failures
* Analysis exceptions
* CFR launch failures

Errors are logged and presented to the user where appropriate.

The interface remains usable even after a failure.

---

# Performance Characteristics

Guard is designed to scale efficiently.

Features include:

* Background scanning
* Asynchronous execution
* Incremental table updates
* Cached definitions
* Integrated class navigation

Large modpacks can therefore be analysed without freezing the interface.

---

# Typical Uses

## Malware Investigation

Identify potentially malicious mods.

---

## Security Auditing

Review the security posture of a modpack.

---

## Support Requests

Gather evidence for malware-related reports.

---

## Server Safety Analysis

Identify known problematic servers.

---

## Reverse Engineering

Inspect suspicious classes through CFR.

---

## Modpack Maintenance

Verify the legitimacy of included mods.

---

# Relationship with Other CrashDetector Tools

Guard integrates closely with several CrashDetector systems.

## ModsTree

Provides class ownership and discovery.

## CFR Browser

Provides source-code decompilation.

## Dependency Map

Allows further investigation of suspicious dependencies.

## SpongeMixin Explorer

Can be used to inspect mixins found within suspicious mods.

Together these tools allow security findings to be traced from high-level detection down to individual lines of code.

---

# Summary

Guard is CrashDetector's dedicated security analysis environment.

By combining malware detection, server analysis, definition management and CFR integration, it provides a practical toolkit for investigating security concerns within modded Minecraft environments.

Key features include:

* Malware scanning
* Problematic server detection
* Definition management
* CFR integration
* Interactive investigation workflows
* Background analysis
* Theme customisation
* Integration with the wider CrashDetector ecosystem

making it a valuable resource for players, support teams, modpack maintainers and developers investigating security-related issues.

