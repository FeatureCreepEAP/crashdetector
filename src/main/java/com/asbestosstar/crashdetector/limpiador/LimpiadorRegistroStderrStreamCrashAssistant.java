package com.asbestosstar.crashdetector.limpiador;

import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * Limpiador para archivos stderr_stream.log.
 *
 * Este formato corresponde a la solución de CrashAssistant para capturar stderr
 * en archivo. Es similar con versiones viajas de ProxySysOutSysErr, pero añade
 * una gran cantidad de prefijos.
 *
 * Dichos prefijos (timestamps y bloques [STDERR]) dificultan la lectura directa
 * de stacktraces, por lo que este limpiador los elimina para dejar la traza lo
 * más limpia y legible posible.
 *
 * Formato típico: [16:28:05:286] [STDERR]: mensaje...
 *
 * Reglas: 1) Elimina SIEMPRE el bloque "[STDERR]:". 2) En líneas de traza que
 * comienzan con "at" elimina también el timestamp. 3) Conserva el encabezado
 * inicial del archivo (DateTime, separadores, etc.). 4) Normaliza indentación
 * de stacktrace a " at ...".
 */
public class LimpiadorRegistroStderrStreamCrashAssistant implements LimpiadorDeRegistro {

	/** Detecta timestamp inicial: [16:28:05:286] */
	private static final Pattern TIMESTAMP = Pattern.compile("^\\[\\d{2}:\\d{2}:\\d{2}:\\d{3}\\]\\s*");

	/** Detecta bloque [STDERR]: */
	private static final Pattern BLOQUE_STDERR = Pattern.compile("\\[STDERR\\]:\\s*");

	/** Detecta línea de stacktrace tras limpiar prefijos */
	private static final Pattern LINEA_AT = Pattern.compile("^[\\t ]*at\\s+");

	@Override
	public String limpiarConsola(String contenido) {

		if (contenido == null || contenido.isEmpty())
			return contenido;

		String[] lineas = contenido.split("\n", -1);
		StringBuilder resultado = new StringBuilder(contenido.length());

		for (String linea : lineas) {

			// Si la línea no contiene el prefijo característico, se deja intacta
			if (!linea.contains("[STDERR]")) {
				resultado.append(linea).append("\n");
				continue;
			}

			// 1) Eliminar timestamp inicial
			String sinTimestamp = TIMESTAMP.matcher(linea).replaceFirst("");

			// 2) Eliminar bloque [STDERR]:
			String sinStderr = BLOQUE_STDERR.matcher(sinTimestamp).replaceFirst("");

			// 3) Si es línea de stacktrace, normalizar indentación
			if (LINEA_AT.matcher(sinStderr).find()) {
				String limpio = sinStderr.replaceFirst("^[\\t ]*at\\s+", "");
				resultado.append("  at ").append(limpio).append("\n");
			} else {
				// Línea normal (Exception, Caused by, etc.)
				resultado.append(sinStderr).append("\n");
			}
		}

		return resultado.toString();
	}

	@Override
	public boolean predicado(Path archivo) {
		// Solo aplica a stderr_stream.log
		return archivo != null && archivo.toString().endsWith("stderr_stream.log");
	}
}