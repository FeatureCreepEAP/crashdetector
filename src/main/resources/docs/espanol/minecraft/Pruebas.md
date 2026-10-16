# Pruebas unitarias y masivas

Esta guía explica cómo ejecutar las pruebas de CrashDetector y revisar sus resultados. Los comandos se ejecutan desde la raíz del repositorio.

## Requisitos

- JDK compatible con el proyecto.
- Maven.
- `sh` y `awk` para el runner masivo.
- `zip`, `unzip` y Bash solo si también se va a generar el JAR reducido de CurseForge.

Compruebe las versiones disponibles antes de comenzar:

```bash
java -version
mvn -version
```

## Pruebas unitarias

Las pruebas JUnit están en `src/test/java`. Incluyen análisis de detectores, simetría de anotaciones, extracción de enlaces Markdown, estados de salida CLI y casos de ejemplo.

Ejecute toda la suite con:

```bash
mvn -B -Pdev test
```

Maven compila las clases de producción y de prueba y ejecuta Surefire. Los informes detallados quedan en:

```text
target/surefire-reports/
```

Para ejecutar una clase concreta, use el selector de Surefire:

```bash
mvn -B -Pdev -Dtest=PruebaEnlanceMD test
mvn -B -Pdev -Dtest=PruebaEstadoSalidaCLI test
```

Un fallo de compilación, una aserción fallida o una prueba con error hace que Maven termine con un código distinto de cero.

## Construir el JAR

Las pruebas masivas necesitan un JAR compilado. Construya el artefacto con:

```bash
mvn -B -Pdev package
```

El resultado normal es:

```text
target/crashdetector-2.4.1.jar
```

Puede comprobar su contenido sin ejecutarlo:

```bash
jar tf target/crashdetector-2.4.1.jar
```

## Pruebas masivas

Antes de ejecutar el runner, valide su sintaxis POSIX:

```bash
sh -n pruebas_masivas.sh
```

Después ejecute:

```bash
CRASHDETECTOR_JAR=target/crashdetector-2.4.1.jar ./pruebas_masivas.sh
```

El script resuelve su propia raíz, por lo que también puede invocarse desde otro directorio usando una ruta al script. `CRASHDETECTOR_JAR` selecciona el JAR a probar y `JAVA` selecciona el ejecutable de Java:

```bash
JAVA=/ruta/a/java \
CRASHDETECTOR_JAR=/ruta/al/crashdetector.jar \
/ruta/al/repositorio/pruebas_masivas.sh
```

El runner borra `mass_verificaciones/` al comenzar. Cada ejecución empieza así sin mezclar informes ni logs de una ejecución anterior.

### Entradas

Las pruebas de archivos recorren `mass_archivos/`. Son registros con formato normal de Minecraft, no contienen marcadores internos del runner y cubren errores, advertencias, texto UTF-8 y registros sin hallazgos.

`MASS_ENLACES.MD` contiene varios bloques Markdown. Una línea completa con cinco o más signos `#` separa bloques únicamente para el runner. El script no interpreta URLs ni reemplaza el parser: entrega cada bloque completo a `EnlanceMD` en una JVM independiente.

Los enlaces `mclo.gs` de la masa son deliberadamente inexistentes. El runner activa:

```text
-Dcrashdetector.markdown.sin_red=true
```

Así las descargas fallan de forma rápida y determinista, sin DNS ni acceso a Internet. Estas pruebas no demuestran que el servicio externo esté disponible.

### Salidas

El runner genera:

```text
mass_verificaciones/
├── archivos/<nombre>/
│   ├── informe.html
│   ├── consola_monitor.log
│   ├── salida_estandar.txt
│   ├── salida_de_error.txt
│   └── codigo_de_salida.txt
├── enlaces/<id>/
│   ├── entrada.md
│   ├── informe.html             (si se generó un informe válido)
│   ├── consola_monitor.log
│   ├── salida_estandar.txt
│   ├── salida_de_error.txt
│   └── codigo_de_salida.txt
├── resumen.tsv
└── consola_masiva.log
```

`resumen.tsv` contiene cinco columnas separadas por tabuladores:

```text
tipo    nombre    ruta del informe    código de salida    duración en segundos
```

Revise `salida_estandar.txt` para la ruta devuelta por el monitor y `salida_de_error.txt` o `consola_monitor.log` para los diagnósticos. Los artefactos de un caso fallido se conservan para poder investigarlo.

## Códigos de salida

- `0`: análisis correcto e informe HTML válido.
- `2`: entrada inválida, Markdown sin enlaces compatibles o ningún grupo compatible.
- `3`: fallo del monitor o fallo de todas las descargas Markdown.
- `4`: informe generado ausente, vacío o inválido.

El runner acepta `0`, `2` y `3` para los casos Markdown deterministas. Cualquier otro código o un informe que debía ser válido hace que el runner termine con fallo.

Un documento Markdown con una mezcla de grupos descargados correctamente y grupos fallidos puede conservar el código `0`; el diagnóstico parcial aparece en stderr.

## Diagnóstico y repetición

Si el runner falla:

1. revise la fila correspondiente en `mass_verificaciones/resumen.tsv`;
2. abra `codigo_de_salida.txt`;
3. revise `salida_de_error.txt` y `consola_monitor.log`;
4. compruebe que `informe.html` existe, no está vacío y contiene HTML;
5. vuelva a ejecutar el comando completo para confirmar que no dependía de resultados antiguos.

Para una comprobación completa, ejecute en este orden:

```bash
mvn -B -Pdev test
mvn -B -Pdev package
sh -n pruebas_masivas.sh
CRASHDETECTOR_JAR=target/crashdetector-2.4.1.jar ./pruebas_masivas.sh
```

La prueba real de disponibilidad de URLs externas queda fuera de esta suite determinista; para ese caso debe usarse un documento y una política de red controlados por separado.
