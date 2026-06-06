# GPU Fix Documentation

## Overview

GPU Fix is CrashDetector's dedicated graphics troubleshooting interface.

Its purpose is to help users resolve situations where Minecraft is using the wrong graphics processor, especially on systems with both integrated and dedicated GPUs.

This is particularly common on laptops using technologies such as:

* NVIDIA Optimus
* Hybrid graphics
* Integrated Intel or AMD graphics
* Dedicated NVIDIA or AMD graphics

When Minecraft runs on the wrong GPU, users may experience:

* Low FPS
* Stuttering
* Poor shader performance
* Incorrect GPU detection
* Crashes caused by weak or unsupported graphics hardware

GPU Fix provides platform-specific guidance and, where possible, applies system-level fixes automatically.

---

# Architecture

GPU Fix is split into two layers.

```text
GPUFixGUI
    │
    ▼
GPUFixOptimusPrime
```

## GPUFixGUI

The abstract base class contains:

* Window setup
* Button wiring
* Operating system detection
* Fix application logic
* URL opening logic
* CrashDetector GUI registration

## GPUFixOptimusPrime

The concrete implementation contains:

* Optimus Prime themed appearance
* Configurable colours
* Platform-specific explanatory text
* Decorative image support
* Button styling

This follows CrashDetector's usual separation between behaviour and appearance.

---

# User Interface

The interface contains:

* An Optimus Prime image panel
* An explanatory text area
* An Apply button
* A Linux Optimus documentation button

The text changes automatically depending on the operating system.

---

# Operating System Detection

GPU Fix detects the current system using:

```java
System.getProperty("os.name")
```

It supports:

```text
Windows
macOS
Linux
Other systems
```

Each platform receives different instructions and behaviour.

---

# Windows GPU Fix

On Windows, GPU Fix attempts to force Java to use the high-performance GPU.

It does this by writing to the current user's DirectX GPU preference registry key.

```text
HKCU\SOFTWARE\Microsoft\DirectX\UserGpuPreferences
```

The value written is:

```text
GpuPreference=2;
```

This tells Windows to prefer the high-performance GPU for the selected Java executable.

---

# Java Executable Detection

GPU Fix obtains the Java executable using:

```java
MonitorDePID.jvm()
```

This is then used as the registry value name.

The target is normally the Java executable used to launch Minecraft, such as:

```text
javaw.exe
```

or another Java runtime path supplied by the launcher.

---

# Windows Registry Command

The Windows fix uses the built-in `reg` command.

```text
reg add HKCU\SOFTWARE\Microsoft\DirectX\UserGpuPreferences /v <javaw> /t REG_SZ /d GpuPreference=2; /f
```

After the command completes, GPU Fix shows a confirmation message.

---

# macOS GPU Fix

On macOS, GPU Fix attempts to force use of the dedicated GPU by running:

```text
sudo pmset -a gpuswitch 1
```

This applies only to Macs with switchable graphics.

The values are:

```text
1 = dedicated GPU
2 = automatic switching
```

Because this command uses `sudo`, the user may be prompted by the system for administrator credentials.

---

# Linux GPU Fix

On Linux, GPU Fix does not apply an automatic system modification.

Instead, it displays manual guidance and provides a link to Optimus documentation.

The included documentation button opens:

```text
https://rpmfusion.org/Howto/Optimus
```

This is especially relevant for Linux systems using NVIDIA Optimus or PRIME render offload.

---

# Unsupported Systems

If the operating system is not recognised as Windows, macOS, or Linux, GPU Fix displays a system-not-supported message.

No automatic changes are attempted.

---

# Safety Model

GPU Fix makes changes only when the user presses the Apply button.

It does not silently modify GPU preferences.

Platform behaviour:

```text
Windows  → writes a user-level registry preference
macOS    → runs pmset using sudo
Linux    → manual guidance only
Other    → no action
```

The Windows change is applied under `HKCU`, so it affects the current user rather than the entire machine.

---

# Source Links

The class includes support for several source or reference buttons:

```text
TLauncher GPU fix source
VirusTotal behaviour reference
RPM Fusion Optimus guide
```

In the current interface, only the Linux Optimus guide button is added to the visible button bar.

The TLauncher and VirusTotal buttons exist in code but are commented out in the layout.

---

# Integration with Performance Manager

GPU Fix is integrated with the Performance Manager.

When CrashDetector's GPU verification detects a problem:

```java
VerificacionGPU.hayProblema
```

the Performance Manager displays a button which opens GPU Fix directly.

This allows users to move from diagnosis to corrective action quickly.

---

# Theme System

The default implementation is:

```java
GPUFixOptimusPrime
```

The theme uses configurable colours through `ConfigColor`.

Configurable categories include:

```text
Background
Panel
Text
Button
Button Text
```

The image is loaded from:

```text
imagenes/optimus_prime.png
```

If the image cannot be loaded, the label falls back to:

```text
Optimus Prime
```

---

# Localisation

All user-facing text is obtained through:

```java
MonitorDePID.idioma
```

This includes:

* Window title
* Platform-specific instructions
* Button labels
* Error messages
* Success messages
* Unsupported system messages

This keeps the GPU Fix interface compatible with CrashDetector's language system.

---

# Error Handling

If a fix fails, GPU Fix logs the exception through:

```java
CrashDetectorLogger.logException(...)
```

and displays an error message to the user.

This applies to:

* Registry command failures
* `pmset` failures
* URL opening failures
* Java executable detection failures

---

# Typical Uses

## Minecraft Uses Integrated Graphics

Force Java to use the dedicated GPU on Windows.

## Poor FPS on Hybrid Graphics Laptops

Apply or explain GPU preference fixes.

## Shader Performance Problems

Help ensure Minecraft is running on the stronger GPU.

## GPU Verification Follow-Up

Provide corrective action after CrashDetector detects a GPU-related issue.

## Linux Optimus Troubleshooting

Direct users to manual Optimus configuration guidance.

---

# Summary

GPU Fix is CrashDetector's platform-aware graphics troubleshooting tool.

It provides:

* Operating system detection
* Windows high-performance GPU preference application
* macOS dedicated GPU switching
* Linux Optimus guidance
* Performance Manager integration
* Configurable Optimus Prime theme
* Localised user-facing text

making it a focused companion tool for resolving graphics processor selection problems in Minecraft.

