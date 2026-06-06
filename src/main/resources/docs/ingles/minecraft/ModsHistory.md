# Mod History Documentation

## Overview

Mod History is CrashDetector's built-in modpack change tracking system.

Its purpose is to record snapshots of a user's mod collection over time and allow direct comparison between different states of a modpack.

Rather than manually remembering which mods were added or removed between crashes, updates or successful launches, Mod History maintains historical records that can be compared instantly.

The system is particularly useful for:

* Crash investigation
* Modpack maintenance
* Regression analysis
* Update tracking
* Support requests
* Compatibility research
* Historical auditing

By comparing previous states of a modpack, users can quickly determine which modifications may have introduced a problem.

---

# Architecture

Mod History is built around a simple historical snapshot model.

```text
Current Modpack
       │
       ▼
History Snapshot
       │
       ▼
Historical Archive
       │
       ▼
Comparison Engine
       │
       ▼
Difference Report
```

The system stores lists of installed mods and allows comparisons between any two recorded states.

---

# Available Interfaces

Mod History currently includes two graphical implementations.

## Clio's Office

```java
ClioOfficeGUI
```

A highly themed interface inspired by an archive office environment.

Features include:

* Custom artwork
* Proportional layout
* Historical archive theme
* Scrollable comparison lists
* Snapshot management

---

## Legacy Interface

```java
HistoriaModsGUILegacy
```

The original implementation.

Features include:

* Traditional desktop layout
* Lightweight design
* Simplified presentation
* Minimal resource usage

Both interfaces share the same underlying comparison engine.

---

# Historical Files

Historical records are stored in:

```text
history_mods/
```

Files are categorised into three types.

---

## Success Records

```text
000451.success
```

Represents a successful launch state.

---

## Failure Records

```text
000452.failure
```

Represents a crash or failed launch state.

---

## Snapshots

```text
000453.snapshot
```

Represents a manually created snapshot.

---

# Purpose of Historical Analysis

The primary objective is to answer questions such as:

```text
What changed before the crash?
```

```text
Which mods were added?
```

```text
Which mods were removed?
```

```text
When did the problem first appear?
```

Without Mod History, such investigations can become extremely difficult on large modpacks.

---

# Historical File Discovery

When the interface opens, CrashDetector scans the history directory.

Records are automatically discovered and sorted.

Files are ordered by their numerical identifier.

Example:

```text
000450.success
000451.success
000452.failure
000453.snapshot
```

The newest entries appear first.

---

# Dual-Column Selection System

Mod History uses two independent selection columns.

```text
Left Record
        │
        ▼
Comparison Engine
        ▲
        │
Right Record
```

This allows any two historical states to be compared.

---

# Historical Status Types

Each record displays its status.

---

## Success

```text
(Success)
```

Indicates a launch which completed successfully.

---

## Failure

```text
(Failure)
```

Indicates a launch which ended unsuccessfully.

---

## Snapshot

```text
(Snapshot)
```

Indicates a manually preserved state.

---

# Snapshot Creation

One of the most useful features is manual snapshot creation.

A user selects a record and presses:

```text
Snapshot
```

The system creates a preserved copy.

Example:

```text
000452.failure
        │
        ▼
000452.snapshot
```

If a snapshot already exists, an incrementing suffix is added.

Example:

```text
000452_01.snapshot
000452_02.snapshot
```

This prevents accidental overwriting.

---

# Why Snapshots Are Useful

Snapshots allow users to preserve important states.

Examples include:

* Stable modpacks
* Known working configurations
* Pre-update backups
* Investigation checkpoints
* Testing environments

They provide a convenient way to bookmark significant moments in a modpack's history.

---

# Comparison Process

The comparison workflow is straightforward.

```text
Select Record A
         │
Select Record B
         │
         ▼
Compare
         │
         ▼
Difference Report
```

The report displays all detected changes.

---

# Mod Normalisation

Before comparison, mod names are normalised.

The system:

1. Extracts the file name.
2. Removes extensions.
3. Converts names to lower case.
4. Compares normalised identifiers.

Example:

```text
ExampleMod-1.0.jar
```

becomes:

```text
examplemod-1.0
```

This improves comparison accuracy.

---

# Relative Path Handling

Mod History attempts to store and display relative paths where possible.

This provides several advantages:

* Cleaner reports
* Easier sharing
* Reduced machine-specific information
* Improved readability

If a relative path cannot be generated, the filename is used instead.

---

# Difference Detection

The comparison engine identifies two primary categories.

---

## Added Mods

Mods present in the newer record but absent from the older one.

Example:

```text
+ mods/new_mod.jar
```

---

## Removed Mods

Mods present in the older record but absent from the newer one.

Example:

```text
- mods/old_mod.jar
```

---

# Difference Report

Results are displayed in an HTML-based report.

Example:

```text
Comparing:
000451.success
with
000452.failure

+ mods/newlibrary.jar
+ mods/newfeature.jar

- mods/removedmod.jar
```

This provides an immediate overview of changes.

---

# Colour Coding

Difference reports use colour-coded results.

---

## Added Mods

Displayed using the configured addition colour.

Typically green.

```text
+ ExampleMod.jar
```

---

## Removed Mods

Displayed using the configured removal colour.

Typically red.

```text
- OldMod.jar
```

---

# No Changes Detected

If two records contain identical mod sets, the report displays:

```text
No changes detected.
```

This can be particularly valuable during crash investigations.

Sometimes a failure occurs even though the mod list has not changed, indicating that another factor may be responsible.

---

# Clio's Office Theme

The Clio's Office implementation presents Mod History as a historical archive.

The interface includes:

* Archive-themed artwork
* Decorative parchment styling
* Historical record lists
* Golden descriptive text
* Scalable layout

The layout is built using proportional coordinates, allowing the interface to scale whilst preserving its appearance.

---

# Responsive Layout System

Clio's Office uses normalised rectangles.

```text
x
y
width
height
```

expressed as proportions rather than fixed coordinates.

This allows interface elements to maintain their intended positions regardless of window size.

---

# Historical Archive Presentation

Records are displayed using simplified labels.

Example:

```text
451 (Success)
452 (Failure)
453 (Snapshot)
```

Leading zeroes and file extensions are removed for readability.

---

# Theme Configuration

Both interfaces support appearance customisation through ConfigColor.

Examples include:

## Status Colours

```text
Success
Failure
Snapshot
```

---

## Result Colours

```text
Added Mods
Removed Mods
```

---

## Interface Colours

```text
Panels
Lists
Buttons
Borders
Text
```

No user-facing colours are hard-coded.

---

# Error Handling

Mod History handles common problems gracefully.

Examples include:

* Missing files
* Corrupted records
* Invalid paths
* Comparison failures
* Snapshot creation failures

Where possible, meaningful feedback is displayed to the user.

---

# Typical Uses

## Crash Investigation

Determine which mods changed before a crash occurred.

---

## Update Analysis

Identify modifications introduced during updates.

---

## Regression Tracking

Find when a previously working modpack became unstable.

---

## Support Requests

Provide historical evidence when seeking assistance.

---

## Modpack Maintenance

Monitor changes across long-term modpack development.

---

## Testing

Create snapshots before experimenting with new mods.

---

# Relationship with Other CrashDetector Tools

Mod History works alongside several CrashDetector systems.

## Dependency Map

Investigate newly added dependencies discovered during comparisons.

## SpongeMixin Explorer

Analyse mixins introduced by newly added mods.

## Guard

Inspect newly added mods for security concerns.

## CFR Browser

Decompile newly introduced classes for further investigation.

Together these tools allow changes discovered by Mod History to be investigated in far greater detail.

---

# Summary

Mod History is CrashDetector's historical modpack tracking and comparison system.

By maintaining records of previous mod states, creating manual snapshots and providing detailed difference reports, it allows users to identify precisely how a modpack has changed over time.

Key features include:

* Historical mod records
* Snapshot creation
* Added mod detection
* Removed mod detection
* Relative-path comparison
* HTML difference reports
* Clio's Office archive interface
* Legacy interface
* Theme customisation

making it an invaluable diagnostic tool for modpack maintainers, developers, support teams and power users.

