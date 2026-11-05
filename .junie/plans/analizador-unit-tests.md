---
sessionId: session-261105-194322-xijp
---

# Overview & Scope

## Overview

Write comprehensive unit tests for all 53 **general** analyzers in `analizador/general/`. Each test class will cover:

- **Positive detection**: The analyzer activates on representative crash report lines
- **False negatives**: Lines that should NOT trigger (normal logs, unrelated errors)
- **False positives**: SOS/DLL mentions in library lists, trace lines that are normal, etc.
- **Identity**: `id()`, `nombre()`, `prioridad()`, `nivel_de_criticalidad()`, `nueva()`
- **Edge cases**: null inputs, empty lines, malformed lines, boundary conditions

## Scope

**In scope**: All 53 analyzers in `analizador/general/`. The `analizador/apps/minecraft/` analyzers (~208 files) follow the same pattern and can be tested in a follow-up.

**Out of scope**: Integration tests that require full `Consola` objects with `VerificacionDeStackTrace` (those require the full analysis pipeline). Tests will use `null` for `Consola` where the analyzer supports it, or minimal mock objects.

## Target

~100+ tests total across ~25 test classes (grouping related analyzers). Each test class covers 1-3 analyzers.

# Technical Design

## Test Pattern

Following the existing `PruebaNullPointer` and `PruebaEjemplosDeVerificaciones` patterns:

1. **`@BeforeEach`**: Reset global state (e.g., `NullPointer.reiniciarGlobal()`, `FaltasClases.reiniciarGlobal()`) and set `MonitorDePID.idioma = new Espanol()`
2. **Positive tests**: Feed representative crash lines via `verificarPorLinea(null, linea, lineNum)` and assert `activado() == true`
3. **Negative tests**: Feed normal lines and assert `activado() == false`
4. **False positive tests**: Feed lines that contain keywords but are NOT the actual error (e.g., `sos.dll` in a library list, `NullPointerException` in a trace that is denied)
5. **Identity tests**: Verify `id()`, `nueva()`, `prioridad()`, `nivel_de_criticalidad()`

## Grouping Strategy

Group related analyzers into test classes to keep tests organized:

| Test Class | Analyzers Covered |
|---|---|
| `PruebaNullPointer` (exists) | NullPointer |
| `PruebaJavaVersiones` | JavaVersiones |
| `PruebaFaltasClases` | FaltasClases, AdvertenciaFaltasClases |
| `PruebaContenidoDeTrazos` | ContenidoDeTrazos |
| `PruebaErroresJVM` | ErrorDeEnlaceInsatisfecho, ErrorStackSmashingDetected, ErrorVerificacionBytecode, ErrorContextoOpenGL |
| `PruebaErroresBytecode` | ErrorClaseFinalExtendida, ErrorMetodoFinalSobrescrito, ErrorMetodoAbstractoNoImplementado, ErrorMetodoInexistente, ErrorCampoInexistente |
| `PruebaDriversGPU` | Drivers, VerificacionGPU, ErrorOpenGLMemoriaInsuficiente |
| `PruebaHardwareCPU` | Meltdown, Spectre, RaptorLakeInestable, HardwareFueraDePolitica |
| `PruebaMemoria` | NoTieneMemoria, OpcionesJavaGCInvalidas |
| `PruebaModIncompatible` | ModIncompatibleConCargadorActivo, AccesoIlegalMod, JPMSIllegalAccess, FaltaModuleJPMS |
| `PruebaSpongeMixin` | SpongeMixinClaseMalUbicada, SpongeMixinConfigsProblematicos |
| `PruebaErroresArchivo` | ErrorArchivoBloqueadoPorOtroProceso, ErrorArchivoUsadoPorOtroProceso, ErrorCaracteresInvalidosEnNombre, RutaCaracteresInvalidos |
| `PruebaErroresJar` | ErrorJarCorruptoConNombre, ErrorVersionInvalidaModMaven, VersionInvalidaSemver |
| `PruebaDifDeMods` | DifDeMods |
| `PruebaClassCast` | ClassCastExceptionGeneral |
| `PruebaFabric` | ErrorEntrypointFabric |
| `PruebaLWJGL` | ErrorDeMonitorLWJGL |
| `PruebaLanzadores` | LanzerDesAnimado, LanzerNoAnimado, TienesModDesAnimado, FaltaModAnimado |
| `PruebaAntiManipulacion` | AntiManipulacion |
| `PruebaPreferIPV4` | PreferIPV4Trace |
| `PruebaSafeFetch` | ProblemaSafeFetch32JDK17 |
| `PruebaModulesDuplicados` | ModulesDuplicadosJavaModulePlatform |
| `PruebaAccesoDenegado` | AccesoDenegadoBackupConfig |
| `PruebaFallosTareas` | FallosEjecucionTareas |

## Key False Positive Scenarios

- **SOS/DLL in library lists**: `JavaVersiones` must NOT activate on `# C  [libjvm.so+0x...]` unless preceded by `Problematic frame:`
- **NullPointerException in denied traces**: `NullPointer` must NOT activate when `VerificacionDeStackTrace.tracePermite()` returns false
- **ClassNotFoundException in WARN lines**: `FaltasClases` must NOT activate on warning-level class not found
- **Missing classes in Kotlin/gg.essential**: `FaltasClases` must filter these out
- **Driver version lines**: `Drivers` must distinguish between actual driver errors and normal driver info

# Delivery Plan

See the delivery_plan parameter below.

# Delivery Steps

### ✓ Step 1: Stage 1: JVM and bytecode error tests
Tests for JavaVersiones, NullPointer (extend existing), ErrorDeEnlaceInsatisfecho, ErrorStackSmashingDetected, ErrorVerificacionBytecode, ErrorContextoOpenGL, ErrorClaseFinalExtendida, ErrorMetodoFinalSobrescrito, ErrorMetodoAbstractoNoImplementado, ErrorMetodoInexistente, ErrorCampoInexistente, ClassCastExceptionGeneral. 60 tests covering positive detection, false positives (SOS/DLL in library lists without Problematic frame), and edge cases. All passing.

### ✓ Step 2: Stage 2: Missing classes and trace content tests
Tests for FaltasClases, AdvertenciaFaltasClases, ContenidoDeTrazos, DifDeMods. 29 tests covering ClassNotFoundException/NoClassDefFoundError detection, false positives (WARN lines, Kotlin/gg.essential, ignored classes), and trace rendering. All passing.

### ✓ Step 3: Stage 3: GPU, hardware, and memory tests
Tests for Drivers, VerificacionGPU, ErrorOpenGLMemoriaInsuficiente, Meltdown, Spectre, RaptorLakeInestable, HardwareFueraDePolitica, NoTieneMemoria, OpcionesJavaGCInvalidas. 53 tests covering driver detection, CPU vulnerability checks, and OOM/GC error detection. All passing.

### ✓ Step 4: Stage 4: Mod loader and compatibility tests
Tests for ModIncompatibleConCargadorActivo, AccesoIlegalMod, JPMSIllegalAccess, FaltaModuleJPMS, ErrorEntrypointFabric, ErrorDeMonitorLWJGL, ModulesDuplicadosJavaModulePlatform. 29 tests covering mod loader conflicts, JPMS errors, Fabric entrypoint issues (main/client entrypoints, partial pattern match), and module duplication (export package, contains package, Exception in thread, MixinExtras detection, global deduplication). All passing.

### ✓ Step 5: Stage 5: File, JAR, and misc error tests
Tests for ErrorArchivoBloqueadoPorOtroProceso, ErrorArchivoUsadoPorOtroProceso, ErrorCaracteresInvalidosEnNombre, RutaCaracteresInvalidos, ErrorJarCorruptoConNombre, ErrorVersionInvalidaModMaven, VersionInvalidaSemver, AccesoDenegadoBackupConfig, FallosEjecucionTareas, AntiManipulacion, PreferIPV4Trace, ProblemaSafeFetch32JDK17. 36 tests covering file access errors, JAR corruption, and miscellaneous issues. All passing.

### ✓ Step 6: Stage 6: SpongeMixin, launcher, and animation tests
Tests for SpongeMixinClaseMalUbicada, SpongeMixinConfigsProblematicos, LanzerDesAnimado, LanzerNoAnimado, TienesModDesAnimado, FaltaModAnimado. 27 tests covering mixin config issues (MixinApplyError, InvalidMixinException, InvalidInjectionException, MixinTransformerError, FAILED during APPLY, resource invalid, false positive for .mixins.json in frame metadata), launcher animation problems, and mod animation detection. All passing.