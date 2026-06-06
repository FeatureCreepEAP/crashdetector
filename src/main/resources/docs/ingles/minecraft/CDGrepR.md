# GrepR Documentation

## Overview

GrepR is CrashDetector's advanced file-content search utility.

Inspired by traditional Unix tools such as `grep`, `ripgrep`, and recursive text search utilities, GrepR allows users to search through large collections of files, directories and archives from within CrashDetector.

Unlike ordinary file search tools, GrepR can optionally search inside compressed files and supports both plain-text and regular-expression searches.

The tool is particularly useful for:

* Minecraft modpack investigation
* Configuration auditing
* Crash analysis
* Log analysis
* Mod development
* Reverse engineering
* Dependency research
* Bulk content searches

GrepR provides a graphical interface for performing searches which would otherwise require command-line tools.

---

# Architecture

The GrepR system is divided into several components.

```text
GrepRGUI
    │
    ▼
Search Parameters
    │
    ▼
BusquedaArchivos
    │
    ▼
Result Collection
    │
    ▼
Result Viewer
```

The graphical interface collects user input and passes search requests to the underlying search engine.

---

# User Interface

The default implementation is:

```java
BusquedaGUISaliorMoon
```

The interface contains:

* Directory selection
* Search string field
* Regex option
* Ignore case option
* Search compressed files option
* Search button
* Results area

All controls are presented in a single unified interface.

---

# Search Workflow

A typical search follows this process.

```text
Select Directory
        │
Enter Search Term
        │
Choose Search Options
        │
Press Search
        │
Perform Scan
        │
Display Results
```

The search runs in the background and updates the results area when complete.

---

# Directory Selection

Users may choose a target directory by pressing:

```text
Select Folder
```

This opens a directory chooser.

If no directory is specified, GrepR defaults to:

```text
Current Working Directory
```

which is typically CrashDetector's current execution directory.

---

# Search Terms

The search term field contains the text or pattern being searched for.

Examples:

```text
optifine
```

```text
NullPointerException
```

```text
mods.toml
```

The meaning of the search term depends upon whether regular-expression mode is enabled.

---

# Plain Text Searches

When Regex mode is disabled, GrepR performs a standard text search.

Example:

```text
Mixin
```

This searches for the literal text:

```text
Mixin
```

within files.

---

# Regular Expression Searches

When Regex mode is enabled:

```text
Use Regex
```

the search term is interpreted as a Java regular expression.

Example:

```text
com\.example\..*
```

This allows complex searches spanning many related values.

Regular expressions are particularly useful when investigating:

* Package names
* Class names
* Method signatures
* Version formats
* Log patterns

---

# Case-Insensitive Searches

The option:

```text
Ignore Capitalisation
```

causes searches to ignore differences between upper-case and lower-case characters.

Example:

```text
Mixin
```

would match:

```text
mixin
```

```text
MIXIN
```

```text
Mixin
```

and other capitalisation variants.

This is useful when searching logs and configuration files with inconsistent formatting.

---

# Searching Inside Archives

One of GrepR's most powerful features is:

```text
Search Inside Compressed Files
```

When enabled, the search engine also inspects supported archive formats.

This allows searches inside:

```text
.jar
.zip
```

and other supported compressed containers handled by the underlying search engine.

This is particularly useful for Minecraft environments where much of the relevant content resides within mod JARs.

---

# Background Processing

Searches execute using:

```java
SwingWorker
```

This prevents the graphical interface from freezing during long searches.

Workflow:

```text
Start Search
      │
Background Worker
      │
Collect Results
      │
Update Interface
```

The user may continue interacting with the application whilst the search executes.

---

# Result Collection

Searches are performed through:

```java
BusquedaArchivos.buscar(...)
```

The engine returns a collection of matching entries.

Each result is displayed on a separate line within the results area.

---

# Result Display

Results are presented within a scrollable text area.

Example:

```text
mods/examplemod.jar
config/example.toml
logs/latest.log
```

Each matching entry is displayed independently.

---

# Empty Results

If no matches are found, GrepR displays:

```text
No results found.
```

This allows users to distinguish between:

* An empty result set
* A failed search

---

# Error Handling

If an exception occurs during searching, the interface displays:

```text
Search Error
```

followed by the exception message.

The interface remains operational and additional searches may be performed immediately.

---

# Salior Moon Theme

The default implementation uses the Salior Moon theme.

```java
BusquedaGUISaliorMoon
```

This theme provides:

* Dark blue appearance
* Golden highlights
* Red accent buttons
* Themed artwork
* Configurable colours

---

# Theme Artwork

The interface loads:

```text
imagenes/saliormoongrep.png
```

The image is automatically scaled to fit the interface.

If the image cannot be located, a placeholder label is displayed instead.

---

# Appearance Configuration

The Salior Moon implementation exposes numerous configurable appearance settings.

Categories include:

## Window Colours

```text
Window Background
Panel Background
Primary Text
```

## Controls

```text
Button Background
Button Text
Input Fields
```

## Highlighting

```text
Selection Colour
Selected Text Colour
Highlighted Border
```

All appearance settings are controlled through `ConfigColor`.

No colours are hard-coded into the user experience.

---

# Input Field Styling

Text fields support:

* Custom background colours
* Custom text colours
* Highlight colours
* Selection colours
* Decorative borders

This improves readability during large searches.

---

# Result Viewer

The results area supports:

* Large result sets
* Text selection
* Copy-and-paste operations
* Scrolling
* Theme colours

This makes it suitable for investigative and diagnostic work.

---

# Localisation

All user-facing strings are retrieved through:

```java
MonitorDePID.idioma
```

This includes:

* Labels
* Buttons
* Error messages
* Search status messages
* Result messages

This ensures compatibility with CrashDetector's localisation system.

---

# Typical Uses

## Searching Logs

Locate specific errors within large log collections.

Example:

```text
NullPointerException
```

---

## Finding Mod References

Search for references to a mod ID.

Example:

```text
create
```

---

## Mixin Investigation

Search for mixin-related classes.

Example:

```text
@Mixin
```

---

## Configuration Auditing

Locate specific configuration values.

Example:

```text
renderDistance
```

---

## Reverse Engineering

Search within extracted source trees or development environments.

---

## Archive Investigation

Search directly inside JAR files without manual extraction.

---

## Support Work

Locate recurring issues across large collections of logs and configuration files.

---

# Relationship with Other CrashDetector Tools

GrepR complements several CrashDetector systems.

## ModsTree

Locates classes, metadata and ownership information.

## CFR Browser

Decompiles classes discovered through searches.

## Dependency Map

Investigates relationships identified during searches.

## SpongeMixin Explorer

Examines mixins discovered through search results.

## Mod History

Searches historical snapshots and archived records.

Together these tools provide a comprehensive investigation environment for large Minecraft installations.

---

# Summary

GrepR is CrashDetector's recursive file-content search utility.

By combining plain-text searching, regular-expression support, archive searching, background processing and a configurable graphical interface, it provides a powerful alternative to command-line search tools.

Key features include:

* Recursive directory searching
* Regular expression support
* Case-insensitive searching
* Archive inspection
* Background execution
* Scrollable result viewing
* Salior Moon themed interface
* Localisation support
* Configurable appearance

making it one of the most useful investigative utilities within the CrashDetector ecosystem.

