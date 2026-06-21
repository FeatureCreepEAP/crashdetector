package com.asbestosstar.crashdetector.analizador.rapido;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.Analizador;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public final class AnalizadorNuevo {

	private final List<Verificaciones> verificaciones = new ArrayList<>();
	private final MotorDeLecturaStreaming motorStreaming;

	public AnalizadorNuevo(Analizador analizadorBase) {
		this.motorStreaming = new MotorDeLecturaStreaming();
	}

	public void analizar(List<Consola> consolas, Set<Verificaciones> todasLasVerificaciones) {
		cargarVerificaciones(todasLasVerificaciones);

		CrashDetectorLogger.log("Iniciando AnalizadorNuevo con " + consolas.size() + " registros");
		CrashDetectorLogger.log("[DEBUG_LOG] Verificaciones cargadas: " + verificaciones.size());

		for (Consola consola : consolas) {
			analizarConsola(consola);
		}
	}

	public void analizarEnVivo(Consola consola, InputStream inputStream, Set<Verificaciones> todasLasVerificaciones) {
		cargarVerificaciones(todasLasVerificaciones);

		try {
			EstadoAnalisisArchivo estado = new EstadoAnalisisArchivo(consola);

			if (consola.verificacion_de_stacktrace != null) {
				consola.verificacion_de_stacktrace.reiniciar();
			}

			CrashDetectorLogger.log("[DEBUG_LOG] Iniciando análisis en vivo");

			motorStreaming.procesarEnVivo(consola, inputStream, verificaciones, estado);

			finalizarVerificaciones(consola, estado);

			CrashDetectorLogger.log("[DEBUG_LOG] Finalizado análisis en vivo");
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
	}

	private void cargarVerificaciones(Set<Verificaciones> todasLasVerificaciones) {
		verificaciones.clear();

		if (todasLasVerificaciones == null) {
			return;
		}

		for (Verificaciones verificacion : todasLasVerificaciones) {
			if (verificacion != null) {
				verificaciones.add(verificacion);
			}
		}
	}

	private void analizarConsola(Consola consola) {
		try {
			EstadoAnalisisArchivo estado = new EstadoAnalisisArchivo(consola);

			CrashDetectorLogger.log("[DEBUG_LOG] Analizando registro: "
					+ (consola.archivo != null ? consola.archivo.getFileName() : "unknown"));

			if (consola.verificacion_de_stacktrace != null) {
				consola.verificacion_de_stacktrace.reiniciar();
			}

			/*
			 * Prioridad: 1. lineas_verificar 2. contenido_verificar 3. archivo en disco
			 * solo como fallback
			 */
			if (consola.lineas_verificar != null) {
				CrashDetectorLogger
						.log("[DEBUG_LOG] Usando lineas_verificar existentes: " + consola.lineas_verificar.length);

				motorStreaming.procesarLineas(consola, consola.lineas_verificar, verificaciones, estado);

			} else if (consola.contenido_verificar != null) {
				String[] lineas = consola.contenido_verificar.split("\\r?\\n", -1);
				consola.lineas_verificar = lineas;

				CrashDetectorLogger
						.log("[DEBUG_LOG] Usando contenido_verificar existente: " + lineas.length + " líneas");

				motorStreaming.procesarLineas(consola, lineas, verificaciones, estado);

			} else if (consola.archivo != null && consola.archivo.toFile().exists()) {
				CrashDetectorLogger.log(
						"[DEBUG_LOG] No hay contenido precargado; leyendo archivo como fallback: " + consola.archivo);

				motorStreaming.procesarArchivo(consola, verificaciones, estado);

			} else {
				CrashDetectorLogger.log("[DEBUG_LOG] Consola sin contenido, líneas ni archivo válido");
			}

			finalizarVerificaciones(consola, estado);

			CrashDetectorLogger.log("[DEBUG_LOG] Finalizado análisis de consola: "
					+ (consola.archivo != null ? consola.archivo.getFileName() : "unknown"));

		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
	}

	private void finalizarVerificaciones(Consola consola, EstadoAnalisisArchivo estado) {
		for (Verificaciones verificacion : verificaciones) {
			try {
				verificacion.finalizarArchivo(consola, estado);
			} catch (Exception e) {
				CrashDetectorLogger.logException(e);
			}
		}
	}
}