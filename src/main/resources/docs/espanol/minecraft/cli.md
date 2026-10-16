# CLI sin interfaz gráfica

CrashDetector permite analizar registros sin abrir una ventana:

```text
java -jar CrashDetectorApp.jar analizar ruta/al/registro.log
java -jar CrashDetectorApp.jar analizar-texto "contenido del registro"
java -jar CrashDetectorApp.jar analizar-markdown documento.md
```

## Comandos y alias

- `analizar`, `analyse`, `analyze` y `logs` reciben una o más rutas de archivos.
- `analizar-texto`, `analizartexto`, `analyse-text`, `analyze-text`, `log-text` y `logs-text` reciben uno o más textos. Cada argumento es un registro independiente; use comillas para conservar espacios y saltos de línea.
- `analizar-markdown` recibe un archivo Markdown y ejecuta `EnlanceMD` dentro del proceso monitor; el documento puede contener muchos enlaces y grupos de registros compatibles.
- `--log`, `--log-temporal` y `--resultado-cli` son argumentos internos del proceso monitor, no comandos públicos para invocar directamente.

Las rutas se validan, se deduplican y se leen con el flujo normal de `Consola`. El texto de `analizar-texto` se materializa como UTF-8 en un archivo temporal controlado por CrashDetector.

`EnlanceMD` extrae todos los enlaces del documento Markdown, elimina URL repetidas y agrupa fragmentos por nombre lógico (`latest.log`, `debug.log`, `crash-*.txt`, etc.). Las líneas de cinco o más `#` separan bloques únicamente en `pruebas_masivas.sh`; cada bloque se entrega completo a una JVM independiente. Para fixtures con enlaces deliberadamente inexistentes puede usarse `-Dcrashdetector.markdown.sin_red=true`, que evita DNS y produce un diagnóstico determinista.

## Resultado

La ejecución headless solicita `-Djava.awt.headless=true` al proceso monitor y no debe crear una interfaz Swing. Cuando el análisis termina, la última línea no vacía de stdout es la ruta absoluta del informe generado. El informe debe existir, ser legible, no estar vacío y contener HTML. Los diagnósticos se escriben en stderr y en el registro del monitor.

Un informe sin hallazgos también es un resultado válido: el archivo se genera aunque contenga el mensaje de que no hubo resultados.

Los códigos públicos son `0` para éxito, `2` para entrada inválida o Markdown sin enlaces compatibles, `3` para fallo del monitor o cuando todas las descargas Markdown fallan y `4` para informe inválido. Una ruta ausente, un archivo inexistente/vacío o HTML inválido nunca se consideran éxito. Un informe sin hallazgos sigue usando código `0` si el HTML es válido. Las fixtures masivas pueden activar `-Dcrashdetector.markdown.sin_red=true` para rechazar enlaces externos sin acceder a la red.

Los temporales de `analizar-texto` se eliminan al preparar una nueva ejecución y mediante `deleteOnExit`; no dependa de su nombre fuera de la ejecución.

## Ejemplo headless

```text
java -Djava.awt.headless=true -jar CrashDetectorApp.jar analizar latest.log
```

El indicador de JVM es una defensa adicional, no sustituye la ruta `analizar`: la aplicación debe activar el transporte `--resultado-cli` y esperar al monitor antes de imprimir la ruta.

Para el transporte interno se usan `--idemode`, `--resultado-cli`, `--log <ruta>` y `--log-temporal <ruta>`. Estos nombres pueden aparecer en los diagnósticos del monitor, pero no deben confundirse con la interfaz pública.
