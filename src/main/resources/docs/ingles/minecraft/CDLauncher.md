# CDLauncher Documentation

## Overview

CDLauncher is CrashDetector's advanced Minecraft relaunch and monitoring system.

Its purpose is to relaunch Minecraft under CrashDetector supervision whilst providing enhanced logging, crash analysis, profiling services and console protection mechanisms.

Unlike a traditional launcher, CDLauncher is not designed to replace Minecraft launchers such as the official launcher, CurseForge, Prism Launcher or MultiMC. Instead, it acts as a specialised relaunch environment which injects CrashDetector functionality into an already configured Minecraft launch.

CDLauncher provides:

* Relaunch functionality
* Java agent injection
* Console monitoring
* Unified logging
* Profiling services
* Tracing services
* Sampling services
* Log4j protection
* Token censorship
* JVM sanitisation

Its primary goal is to maximise diagnostic information whilst minimising launcher-related interference.

---

# Architecture

CDLauncher operates as a controlled relaunch environment.

```text
Original Launcher
        │
        ▼
CDLauncher
        │
        ▼
Java Agent Injection
        │
        ▼
Minecraft Process
        │
        ▼
CrashDetector Monitoring
```

The relaunched process remains under CrashDetector observation for the duration of its execution.

---

# Design Philosophy

CDLauncher follows several key principles.

## No Double Loading

CrashDetector should never exist simultaneously as:

```text
Classpath
```

and

```text
Java Agent
```

Doing so can create:

* Class loader conflicts
* JPMS conflicts
* Resolution exceptions
* Duplicate instrumentation

CDLauncher automatically removes CrashDetector from the classpath before injection.

---

## Monitoring First

The launcher prioritises:

```text
Visibility
```

over

```text
Minimalism
```

Every effort is made to capture useful diagnostic information.

---

## Safe Relaunching

The relaunch process attempts to preserve existing launcher arguments whilst injecting only the functionality required by CrashDetector.

---

# Relaunch Workflow

The relaunch process follows a structured sequence.

```text
Build Command
       │
Inject CrashDetector
       │
Configure Logging
       │
Protect Console
       │
Launch Process
       │
Monitor Process
       │
Capture Output
```

Each stage contributes to the diagnostic environment.

---

# Command Construction

CDLauncher reconstructs the launch command from:

```java
Statics.JVM_ARGS
```

and

```java
Statics.APP_ARGS
```

The launcher then modifies the resulting command before execution.

---

# JVM Detection

Before launching, CDLauncher verifies that a valid JVM executable exists.

If necessary, it automatically inserts:

```java
MonitorDePID.jvm()
```

at the beginning of the command.

This guarantees that the relaunch command begins with a valid Java runtime.

---

# Classpath Sanitisation

One of CDLauncher's most important responsibilities is classpath sanitisation.

The launcher removes:

* CrashDetector JARs
* CrashDetector directories
* Duplicate instrumentation paths

from the classpath before launching.

Example:

```text
Before:
minecraft.jar
forge.jar
crashdetector.jar

After:
minecraft.jar
forge.jar
```

This prevents duplicate loading issues.

---

# Java Agent Injection

CrashDetector is injected through:

```text
-javaagent:
```

rather than through the application classpath.

Example:

```text
-javaagent:crashdetector.jar=cdlauncher
```

This provides:

* Runtime instrumentation
* Monitoring support
* JPMS compatibility
* Reduced class loader conflicts

The launcher only injects the agent if it is not already present.

---

# Console Protection System

Minecraft environments frequently use:

* JLine
* TerminalConsoleAppender
* Interactive terminals

These systems can occasionally cause hangs during shutdown.

CDLauncher automatically injects protective JVM flags.

---

## JLine Protection

If not already configured:

```text
-Dterminal.jline=false
```

is injected.

---

## Unsupported Terminal

To prevent interactive terminal issues:

```text
-Djline.terminal=jline.UnsupportedTerminal
```

is injected.

---

## Forge Console Suppression

CDLauncher also injects:

```text
-Dforge.logging.console.level=off
```

to reduce unnecessary console noise.

---

# Unified Logging System

CDLauncher redirects:

```text
stderr
```

into

```text
stdout
```

before monitoring begins.

This produces:

* Consistent log ordering
* Simplified analysis
* Reduced stream management complexity

The resulting output is processed as a single stream.

---

# Stream Pump

A dedicated background thread continuously drains process output.

```text
Minecraft Output
        │
        ▼
CDLauncher Stream Pump
        │
        ▼
Log File
        │
        ▼
CrashDetector Console
```

This prevents buffer saturation from freezing the target process.

---

# Output Logging

Captured output is written to:

```text
cd_launcherlog
```

through a buffered writer.

Features include:

* UTF-8 support
* Buffered I/O
* Periodic flushing
* Automatic EOF handling

The implementation is optimised for large logs.

---

# Token Censorship

Sensitive authentication information is automatically removed from logs.

Examples include:

```text
--accessToken
```

and

```text
--clientId
```

Before:

```text
--accessToken abc123456789
```

After:

```text
--accessToken null
```

This protects user credentials when sharing logs.

---

# Log4j Protection

Many Minecraft environments ship custom Log4j configurations.

These may:

* Produce XML output
* Use unsupported layouts
* Generate difficult-to-read logs

CDLauncher overrides these configurations.

---

## PatternLayout Enforcement

A dedicated configuration file is extracted:

```text
log4j2-cdlauncher.properties
```

and injected through:

```text
-Dlog4j.configurationFile=
```

This forces Log4j to use a text-based PatternLayout.

---

## Configuration Cleanup

Before applying its own configuration, CDLauncher removes existing Log4j configuration arguments.

Examples include:

```text
-Dlog4j.configurationFile=
```

```text
-Dlog4j2.configurationFile=
```

```text
-Dlog4j.configuration=
```

```text
-Dlog4j2.configuration=
```

This prevents launcher-supplied configurations from overriding CrashDetector's logging environment.

---

# Process Monitoring

After launching Minecraft, CDLauncher starts a dedicated monitoring thread.

The monitor executes:

```java
MonitorDePID.monitor_cdlauncher(...)
```

This allows CrashDetector to:

* Observe process state
* Detect crashes
* Capture diagnostics
* Trigger analysis workflows

throughout execution.

---

# Configuration System

CDLauncher supports optional services through:

```java
ConfigCDLauncher
```

Each option may be enabled or disabled independently.

---

# Available Services

Current services include:

## Tracer

```text
CDTracer
```

Provides execution tracing functionality.

---

## Profiler

```text
CDProfiler
```

Provides performance profiling functionality.

---

## Sampler

```text
CDSampler
```

Provides statistical sampling functionality.

---

# JVM Option Injection

Enabled services automatically generate JVM arguments.

Example:

```text
-Dcrashdetector.tracer=true
```

These arguments are inserted into the launch command before execution.

---

# Launcher Log Visibility

CDLauncher prints the final launch command before execution.

Sensitive information is censored automatically.

Example:

```text
java
-Dcrashdetector.tracer=true
-javaagent:crashdetector.jar=cdlauncher
...
--accessToken null
```

This greatly simplifies launch debugging.

---

# Application Updates Page

CDLauncher also includes a Minecraft news system.

This functionality is implemented by:

```java
PaginaDeActualizacionesMinecraft
```

---

# News Retrieval

News is downloaded from:

```text
repo.tlauncher.org
```

using the current interface language.

The URL is generated dynamically.

Example:

```text
index_en.html
index_es.html
```

---

# Local Caching

Downloaded news is stored locally.

```text
versiones_minecraft_cdlauncher.htm
```

This reduces:

* Network usage
* Page load times
* Repeated downloads

---

# HTML Cleaning

The downloaded page undergoes extensive processing.

The cleaner removes:

* Scripts
* Layout elements
* Images
* Tables
* Styling

Only article content is retained.

---

# News Extraction

The cleaner extracts:

```text
news_title
```

and

```text
news_desc
```

elements from the original page.

The resulting output is simplified for display inside Swing components.

---

# JEditorPane Compatibility

The final HTML is transformed into a format suitable for:

```java
JEditorPane
```

This avoids rendering issues common in Swing's legacy HTML engine.

---

# Performance Characteristics

CDLauncher is designed for long-running Minecraft sessions.

Performance features include:

* Large stream buffers
* Buffered file output
* Asynchronous monitoring
* Background log pumping
* Cached update pages
* Lightweight configuration handling

The launcher therefore adds minimal overhead compared with the diagnostic information gained.

---

# Typical Uses

## Crash Analysis

Launch Minecraft under CrashDetector supervision.

---

## Launcher Debugging

Investigate launch argument problems.

---

## Log Collection

Generate clean, consistent launcher logs.

---

## Performance Profiling

Enable profiling services.

---

## Runtime Tracing

Enable tracing services.

---

## Support Requests

Produce shareable logs with sensitive information removed automatically.

---

# Relationship with Other CrashDetector Systems

CDLauncher serves as the primary entry point into several CrashDetector subsystems.

## MonitorDePID

Provides process monitoring.

## CrashDetector Agent

Provides runtime instrumentation.

## CDTracer

Provides execution tracing.

## CDProfiler

Provides profiling.

## CDSampler

Provides statistical sampling.

## Launcher Log Analysis

Provides post-launch diagnostic analysis.

Together these systems create a controlled environment for diagnosing Minecraft launch, runtime and shutdown problems.

---

# Summary

CDLauncher is CrashDetector's advanced Minecraft relaunch environment.

By combining Java agent injection, classpath sanitisation, process monitoring, console protection, Log4j control and service-based instrumentation, it provides a highly reliable platform for gathering diagnostic information.

Key features include:

* Safe relaunching
* Java agent injection
* Classpath sanitisation
* Console protection
* Unified logging
* Token censorship
* Log4j configuration control
* Runtime monitoring
* Profiling support
* Tracing support
* Cached Minecraft news integration

making it one of the central components of the CrashDetector diagnostic ecosystem.

