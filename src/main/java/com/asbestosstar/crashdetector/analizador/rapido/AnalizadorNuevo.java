package com.asbestosstar.crashdetector.analizador.rapido;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.Analizador;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Nueva implementación del Analizador optimizada para streaming y escaneo
 * rápido.
 */
public final class AnalizadorNuevo {

	private final List<Verificaciones> verificacionesLegacy = new ArrayList<>();
	private final List<VerificacionRapida> verificacionesRapidas = new ArrayList<>();
	private final MotorDeLecturaStreaming motorStreaming;

	public AnalizadorNuevo(Analizador analizadorBase) {
		this.motorStreaming = new MotorDeLecturaStreaming();
	}

	/**
	 * Ejecuta el análisis.
	 */
	public void analizar(List<Consola> consolas, java.util.Set<Verificaciones> todasLasVerificaciones) {
		verificacionesRapidas.clear();
		verificacionesLegacy.clear();
		for (Verificaciones verificacion : todasLasVerificaciones) {
			if (verificacion instanceof VerificacionRapida) {
				verificacionesRapidas.add((VerificacionRapida) verificacion);
			} else {
				verificacionesLegacy.add(verificacion);
			}
		}

		CrashDetectorLogger.log("Iniciando AnalizadorNuevo con " + consolas.size() + " registros");
		CrashDetectorLogger.log("[DEBUG_LOG] Verificaciones rápidas cargadas: " + verificacionesRapidas.size());
		for (VerificacionRapida v : verificacionesRapidas) {
			CrashDetectorLogger.log("[DEBUG_LOG] - " + v.id() + " (patrones: " + v.patronesRapidos().length + ")");
		}
		CrashDetectorLogger.log("[DEBUG_LOG] Verificaciones legacy cargadas: " + verificacionesLegacy.size());

		for (Consola consola : consolas) {
			analizarConsola(consola);
		}
	}

	private void analizarConsola(Consola consola) {
		try {
			EstadoAnalisisArchivo estado = new EstadoAnalisisArchivo(consola);

			CrashDetectorLogger.log("[DEBUG_LOG] Analizando registro: "
					+ (consola.archivo != null ? consola.archivo.getFileName() : "unknown"));

			// Asegurarse de que el contenido de la consola esté finalizado/cargado si es
			// posible
			// Esto es importante para las verificaciones legacy que usan
			// contenido_verificar.
			if (consola.contenido_verificar == null && consola.archivo != null && consola.archivo.toFile().exists()) {
				try {
					// Intentamos cargar el contenido si es pequeño, para compatibilidad legacy
					long size = consola.archivo.toFile().length();
					if (size < 10 * 1024 * 1024) { // < 10MB
						consola.finalizarContenido(java.time.Instant.now(), true);
						CrashDetectorLogger.log(
								"[DEBUG_LOG] Contenido precargado para compatibilidad legacy (tamaño: " + size + ")");
					}
				} catch (Exception e) {
					CrashDetectorLogger.log("[DEBUG_LOG] No se pudo precargar contenido: " + e.getMessage());
				}
			}

			// Reiniciar el verificador de stacktrace para esta consola
			if (consola.verificacion_de_stacktrace != null) {
				consola.verificacion_de_stacktrace.reiniciar();
			}

			// Primero ejecutamos las verificaciones globales (legacy)
			ejecutarGlobalesLegacy(consola);

			// Ejecutamos el motor de streaming para las verificaciones rápidas y por línea
			if (consola.archivo != null && consola.archivo.toFile().exists()) {
				CrashDetectorLogger.log("[DEBUG_LOG] Iniciando motor streaming para: " + consola.archivo);
				motorStreaming.procesar(consola, verificacionesRapidas, verificacionesLegacy, estado);

				// Una vez terminado el streaming, finalizamos el escaneo incremental de trazas
				if (consola.verificacion_de_stacktrace != null) {
					consola.verificacion_de_stacktrace.finalizarEscaneoIncremental();
				}
			} else {
				// SI el contenido fue inyectado o no tiene archivo físico
				if (consola.lineas_verificar != null) {
					CrashDetectorLogger.log("[DEBUG_LOG] Procesando líneas inyectadas ("
							+ consola.lineas_verificar.length + ") para verificaciones rápidas");
					for (int i = 0; i < consola.lineas_verificar.length; i++) {
						motorStreaming.procesarLinea(consola, consola.lineas_verificar[i], i, verificacionesRapidas,
								verificacionesLegacy, estado);
					}
					// Finalizar extracción de trazas para logs inyectados
					if (consola.verificacion_de_stacktrace != null) {
						consola.verificacion_de_stacktrace.finalizarEscaneoIncremental();
					}
				} else if (consola.contenido_verificar != null) {
					// Fallback si lineas_verificar es null pero contenido_verificar existe
					String contenido = consola.contenido_verificar;
					// Usar un split robusto para diferentes tipos de saltos de línea
					String[] lineas = contenido.split("\\r?\\n", -1);
					CrashDetectorLogger.log("[DEBUG_LOG] Procesando contenido inyectado (" + lineas.length
							+ " líneas) para verificaciones rápidas");
					// Almacenamos las líneas divididas para uso posterior de verificaciones
					consola.lineas_verificar = lineas;
					for (int i = 0; i < lineas.length; i++) {
						motorStreaming.procesarLinea(consola, lineas[i], i, verificacionesRapidas, verificacionesLegacy,
								estado);
					}
					// Finalizar extracción de trazas para logs inyectados
					if (consola.verificacion_de_stacktrace != null) {
						consola.verificacion_de_stacktrace.finalizarEscaneoIncremental();
					}
				}
			}

			// Finalizamos el análisis del archivo para las verificaciones rápidas
			for (VerificacionRapida verificacion : verificacionesRapidas) {
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

	private void ejecutarGlobalesLegacy(Consola consola) {
		for (Verificaciones ver : verificacionesLegacy) {
			try {
				ver.verificar(consola);
			} catch (Exception e) {
				CrashDetectorLogger.logException(e);
			}
		}
	}

	private void ejecutarPorLineaLegacy(Consola consola) {
		if (consola.lineas_verificar != null) {
			for (Verificaciones ver : verificacionesLegacy) {
				if (ver.quiereAnalizarLineas() || ver.activarEscaneoPorLinea(consola)) {
					for (int i = 0; i < consola.lineas_verificar.length; i++) {
						try {
							ver.verificarPorLinea(consola, consola.lineas_verificar[i], i);
						} catch (Exception e) {
							CrashDetectorLogger.logException(e);
						}
					}
				}
			}
		}
	}
}
