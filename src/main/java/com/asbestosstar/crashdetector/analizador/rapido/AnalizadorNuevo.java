package com.asbestosstar.crashdetector.analizador.rapido;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.Analizador;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Nueva implementación del Analizador optimizada para streaming y escaneo
 * rápido.
 */
public final class AnalizadorNuevo {

	private final List<Verificaciones> verificaciones = new ArrayList<>();
	private final MotorDeLecturaStreaming motorStreaming;

	public AnalizadorNuevo(Analizador analizadorBase) {
		this.motorStreaming = new MotorDeLecturaStreaming();
	}

	/**
	 * Ejecuta el análisis.
	 */
	public void analizar(List<Consola> consolas, Set<Verificaciones> todasLasVerificaciones) {
		verificaciones.clear();

		for (Verificaciones verificacion : todasLasVerificaciones) {
			if (verificacion != null) {
				verificaciones.add(verificacion);
			}
		}

		CrashDetectorLogger.log("Iniciando AnalizadorNuevo con " + consolas.size() + " registros");
		CrashDetectorLogger.log("[DEBUG_LOG] Verificaciones cargadas: " + verificaciones.size());

		for (Verificaciones v : verificaciones) {
			String[] patrones = v.patronesRapidos();
			int cantidad = patrones == null ? 0 : patrones.length;
			CrashDetectorLogger.log("[DEBUG_LOG] - " + v.id() + " (patrones: " + cantidad + ")");
		}

		for (Consola consola : consolas) {
			analizarConsola(consola);
		}
	}

	private void analizarConsola(Consola consola) {
		try {
			EstadoAnalisisArchivo estado = new EstadoAnalisisArchivo(consola);

			CrashDetectorLogger.log("[DEBUG_LOG] Analizando registro: "
					+ (consola.archivo != null ? consola.archivo.getFileName() : "unknown"));

			if (consola.contenido_verificar == null && consola.archivo != null && consola.archivo.toFile().exists()) {
				try {
					long size = consola.archivo.toFile().length();

					if (size < 10 * 1024 * 1024) {
						consola.finalizarContenido(java.time.Instant.now(), true);
						CrashDetectorLogger.log(
								"[DEBUG_LOG] Contenido precargado para compatibilidad interna (tamaño: " + size + ")");
					}
				} catch (Exception e) {
					CrashDetectorLogger.log("[DEBUG_LOG] No se pudo precargar contenido: " + e.getMessage());
				}
			}

			if (consola.verificacion_de_stacktrace != null) {
				consola.verificacion_de_stacktrace.reiniciar();
			}

			if (consola.archivo != null && consola.archivo.toFile().exists()) {
				CrashDetectorLogger.log("[DEBUG_LOG] Iniciando motor streaming para: " + consola.archivo);
				motorStreaming.procesar(consola, verificaciones, estado);
			} else if (consola.lineas_verificar != null) {
				CrashDetectorLogger
						.log("[DEBUG_LOG] Procesando líneas inyectadas (" + consola.lineas_verificar.length + ")");

				for (int i = 0; i < consola.lineas_verificar.length; i++) {
					motorStreaming.procesarLinea(consola, consola.lineas_verificar[i], i, verificaciones, estado);
				}
			} else if (consola.contenido_verificar != null) {
				String[] lineas = consola.contenido_verificar.split("\\r?\\n", -1);
				consola.lineas_verificar = lineas;

				if (consola.verificacion_de_stacktrace != null) {
					consola.verificacion_de_stacktrace.reiniciar();
				}

				CrashDetectorLogger.log("[DEBUG_LOG] Procesando contenido inyectado (" + lineas.length + " líneas)");

				for (int i = 0; i < lineas.length; i++) {
					motorStreaming.procesarLinea(consola, lineas[i], i, verificaciones, estado);
				}
			}

			for (Verificaciones verificacion : verificaciones) {
				try {
					verificacion.finalizarArchivo(consola, estado);
				} catch (Exception e) {
					CrashDetectorLogger.logException(e);
				}
			}

			CrashDetectorLogger.log("[DEBUG_LOG] Finalizado análisis de consola: "
					+ (consola.archivo != null ? consola.archivo.getFileName() : "unknown"));

		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
	}
}