# Unit and mass tests

This guide explains how to run CrashDetector's tests and inspect their results. Run the commands from the repository root.

## Requirements

- A JDK compatible with the project.
- Maven.
- `sh` and `awk` for the mass runner.
- `zip`, `unzip`, and Bash only when creating the reduced CurseForge JAR as well.

Check the available tools before starting:

```bash
java -version
mvn -version
```

## Unit tests

JUnit tests are under `src/test/java`. They cover detector behavior, reciprocal annotation links, Markdown-link extraction, CLI exit-state handling, and test examples.

Run the complete suite with:

```bash
mvn -B -Pdev test
```

Maven compiles production and test classes and runs Surefire. Detailed reports are written to:

```text
target/surefire-reports/
```

To run one test class, use Surefire's selector:

```bash
mvn -B -Pdev -Dtest=PruebaEnlanceMD test
mvn -B -Pdev -Dtest=PruebaEstadoSalidaCLI test
```

A compilation failure, failed assertion, or test error makes Maven finish with a non-zero status.

## Build the JAR

The mass tests need a compiled JAR. Build it with:

```bash
mvn -B -Pdev package
```

The normal artifact is:

```text
target/crashdetector-2.4.1.jar
```

Inspect its contents without launching it:

```bash
jar tf target/crashdetector-2.4.1.jar
```

Test classes are compiled under `target/test-classes`; they are not included in the production JAR.

## Mass tests

Validate the runner's POSIX syntax first:

```bash
sh -n pruebas_masivas.sh
```

Then run:

```bash
CRASHDETECTOR_JAR=target/crashdetector-2.4.1.jar ./pruebas_masivas.sh
```

The script resolves its own root, so it can also be invoked from another directory using a path to the script. `CRASHDETECTOR_JAR` selects the artifact to test and `JAVA` selects the Java executable:

```bash
JAVA=/path/to/java \
CRASHDETECTOR_JAR=/path/to/crashdetector.jar \
/path/to/repository/pruebas_masivas.sh
```

The runner removes `mass_verificaciones/` at startup. Every run therefore begins without reports or logs left by an earlier run.

### Inputs

File tests traverse `mass_archivos/`. These are ordinary Minecraft-shaped logs, not internal runner fixtures, and cover errors, warnings, UTF-8 text, and logs with no findings.

`MASS_ENLACES.MD` contains several Markdown blocks. A complete line with five or more `#` characters separates blocks for the runner only. The script does not interpret URLs or replace the parser: it passes each complete block to `EnlanceMD` in an isolated JVM.

The `mclo.gs` links in the mass document are deliberately nonexistent. The runner enables:

```text
-Dcrashdetector.markdown.sin_red=true
```

This makes downloads fail quickly and deterministically, without DNS or Internet access. These tests do not prove that the external service is available.

### Outputs

The runner creates:

```text
mass_verificaciones/
├── archivos/<name>/
│   ├── informe.html
│   ├── consola_monitor.log
│   ├── salida_estandar.txt
│   ├── salida_de_error.txt
│   └── codigo_de_salida.txt
├── enlaces/<id>/
│   ├── entrada.md
│   ├── informe.html             (when a valid report was generated)
│   ├── consola_monitor.log
│   ├── salida_estandar.txt
│   ├── salida_de_error.txt
│   └── codigo_de_salida.txt
├── resumen.tsv
└── consola_masiva.log
```

`resumen.tsv` has five tab-separated columns:

```text
type    name    report path    exit code    duration in seconds
```

Review `salida_estandar.txt` for the path returned by the monitor and `salida_de_error.txt` or `consola_monitor.log` for diagnostics. Artifacts from failed cases are retained for investigation.

## Exit codes

- `0`: successful analysis and valid HTML report.
- `2`: invalid input, Markdown without compatible links, or no compatible group.
- `3`: monitor failure or all Markdown downloads failed.
- `4`: missing, empty, or invalid generated report.

The runner accepts `0`, `2`, and `3` for deterministic Markdown cases. Any other code, or a report that should have been valid, makes the runner fail.

A Markdown document with a mixture of successful and failed download groups may remain code `0`; the partial-failure diagnostic is written to stderr.

## Troubleshooting and repetition

If the runner fails:

1. inspect the corresponding row in `mass_verificaciones/resumen.tsv`;
2. open `codigo_de_salida.txt`;
3. review `salida_de_error.txt` and `consola_monitor.log`;
4. check that `informe.html` exists, is non-empty, and contains HTML;
5. run the complete command again to confirm that the result did not depend on stale output.

For a complete verification, run:

```bash
mvn -B -Pdev test
mvn -B -Pdev package
sh -n pruebas_masivas.sh
CRASHDETECTOR_JAR=target/crashdetector-2.4.1.jar ./pruebas_masivas.sh
```

Actual availability of external URLs is outside this deterministic suite; test that separately with a controlled document and network policy.
