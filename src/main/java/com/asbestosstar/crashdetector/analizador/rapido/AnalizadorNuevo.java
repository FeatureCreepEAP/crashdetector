package com.asbestosstar.crashdetector.analizador.rapido;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.Analizador;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;

/**
 * Nueva implementación del Analizador optimizada para streaming y escaneo
 * rápido.
 */
public final class AnalizadorNuevo {

	private final List<VerificacionesLegacy> verificacionesLegacy = new ArrayList<>();
	private final List<Verificaciones> verificacionesRapidas = new ArrayList<>();
	private final MotorDeLecturaStreaming motorStreaming;

	public AnalizadorNuevo(Analizador analizadorBase) {
		this.motorStreaming = new MotorDeLecturaStreaming();
	}

	/**
	 * Ejecuta el análisis.
	 */
	public void analizar(List<Consola> consolas, java.util.Set<VerificacionesLegacy> todasLasVerificaciones) {
		verificacionesRapidas.clear();
		verificacionesLegacy.clear();
		for (VerificacionesLegacy verificacion : todasLasVerificaciones) {
			if (verificacion instanceof Verificaciones) {
				verificacionesRapidas.add((Verificaciones) verificacion);
			} else {
				CrashDetectorLogger.log("Legacy Verificaciones " + verificacion.id());
				verificacionesLegacy.add(verificacion);
			}
		}

		CrashDetectorLogger.log("Iniciando AnalizadorNuevo con " + consolas.size() + " registros");
		CrashDetectorLogger.log("[DEBUG_LOG] Verificaciones rápidas cargadas: " + verificacionesRapidas.size());
		for (Verificaciones v : verificacionesRapidas) {
			String[] patrones = v.patronesRapidos();
			int cantidad = patrones == null ? 0 : patrones.length;
			CrashDetectorLogger.log("[DEBUG_LOG] - " + v.id() + " (patrones: " + cantidad + ")");
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

			if (consola.contenido_verificar == null && consola.archivo != null && consola.archivo.toFile().exists()) {
				try {
					long size = consola.archivo.toFile().length();
					if (size < 10 * 1024 * 1024) {
						consola.finalizarContenido(java.time.Instant.now(), true);
						CrashDetectorLogger.log(
								"[DEBUG_LOG] Contenido precargado para compatibilidad legacy (tamaño: " + size + ")");
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
				motorStreaming.procesar(consola, verificacionesRapidas, verificacionesLegacy, estado);
			} else {
				List<VerificacionesLegacy> legacyLineales = obtenerLegacyLineales(consola);

				if (consola.lineas_verificar != null) {
					CrashDetectorLogger.log("[DEBUG_LOG] Procesando líneas inyectadas ("
							+ consola.lineas_verificar.length + ") para verificaciones rápidas");

					for (int i = 0; i < consola.lineas_verificar.length; i++) {
						motorStreaming.procesarLinea(consola, consola.lineas_verificar[i], i, legacyLineales, estado);
					}
				} else if (consola.contenido_verificar != null) {
					String[] lineas = consola.contenido_verificar.split("\\r?\\n", -1);
					consola.lineas_verificar = lineas;

					if (consola.verificacion_de_stacktrace != null) {
						consola.verificacion_de_stacktrace.reiniciar();
					}

					CrashDetectorLogger.log("[DEBUG_LOG] Procesando contenido inyectado (" + lineas.length
							+ " líneas) para verificaciones rápidas");

					for (int i = 0; i < lineas.length; i++) {
						motorStreaming.procesarLinea(consola, lineas[i], i, legacyLineales, estado);
					}
				}
			}

			for (Verificaciones verificacion : verificacionesRapidas) {
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

	private List<VerificacionesLegacy> obtenerLegacyLineales(Consola consola) {
		List<VerificacionesLegacy> resultado = new ArrayList<>();

		for (VerificacionesLegacy ver : verificacionesLegacy) {
			try {
				if (ver.verificar(consola)) {
					resultado.add(ver);
				}
			} catch (Exception e) {
				CrashDetectorLogger.logException(e);
			}
		}

		return resultado;
	}

}
