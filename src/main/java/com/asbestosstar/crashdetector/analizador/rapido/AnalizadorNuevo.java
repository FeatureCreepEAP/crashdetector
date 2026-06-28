package com.asbestosstar.crashdetector.analizador.rapido;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.Analizador;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public final class AnalizadorNuevo {

	private final List<Verificaciones> verificaciones = new ArrayList<>();
	private final MotorDeLecturaStreaming motorStreaming;

	public AnalizadorNuevo(Analizador analizadorBase) {
		this.motorStreaming = new MotorDeLecturaStreaming();
	}

	public void analizar(List<Consola> consolas, Set<Verificaciones> todasLasVerificaciones) {
		cargarVerificaciones(todasLasVerificaciones);

		if (consolas == null) {
			CrashDetectorLogger.log("Iniciando AnalizadorNuevo con 0 registros");
			return;
		}

		CrashDetectorLogger.log("Iniciando AnalizadorNuevo con " + consolas.size() + " registros");
		CrashDetectorLogger.log("[DEBUG_LOG] Verificaciones cargadas: " + verificaciones.size());

		for (Consola consola : consolas) {
			analizarConsola(consola);
		}
	}

	public void analizarEnVivo(Consola consola, InputStream inputStream, Set<Verificaciones> todasLasVerificaciones) {

		cargarVerificaciones(todasLasVerificaciones);

		ProcesadorVDSTAsync vdstAsync = null;

		try {
			EstadoAnalisisArchivo estado = new EstadoAnalisisArchivo(consola);

			vdstAsync = crearProcesadorVDSTAsync(consola);

			List<Verificaciones> verificacionesLineales = obtenerVerificacionesLineales(consola);

			CrashDetectorLogger.log("[DEBUG_LOG] Iniciando análisis en vivo");

			motorStreaming.procesarEnVivo(consola, inputStream, verificaciones, verificacionesLineales, estado,
					vdstAsync);

			finalizarVDST(vdstAsync);

			finalizarVerificaciones(consola, estado);

			CrashDetectorLogger.log("[DEBUG_LOG] Finalizado análisis en vivo");

		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		} finally {
			finalizarVDST(vdstAsync);
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

		VerificacionDeStackTrace.registrarOcupacionDeVerificaciones(verificaciones);
	}

	private void analizarConsola(Consola consola) {
		if (consola == null || consola.analizadaEnVivo) {
			CrashDetectorLogger.log("[DEBUG_LOG] Consola null o ya analizada en vivo; se omite");
			return;
		}

		ProcesadorVDSTAsync vdstAsync = null;

		try {
			EstadoAnalisisArchivo estado = new EstadoAnalisisArchivo(consola);

			CrashDetectorLogger.log("[DEBUG_LOG] Analizando registro: "
					+ (consola.archivo != null ? consola.archivo.getFileName() : "unknown"));

			vdstAsync = crearProcesadorVDSTAsync(consola);

			List<Verificaciones> verificacionesLineales = obtenerVerificacionesLineales(consola);

			if (tieneLineasVerificar(consola)) {
				CrashDetectorLogger
						.log("[DEBUG_LOG] Usando lineas_verificar existentes: " + consola.lineas_verificar.length);

				motorStreaming.procesarLineas(consola, consola.lineas_verificar, verificaciones, verificacionesLineales,
						estado, vdstAsync);

			} else if (tieneContenidoVerificar(consola)) {
				String[] lineas = consola.contenido_verificar.split("\\r?\\n", -1);
				consola.lineas_verificar = lineas;

				CrashDetectorLogger
						.log("[DEBUG_LOG] Usando contenido_verificar existente: " + lineas.length + " líneas");

				motorStreaming.procesarLineas(consola, lineas, verificaciones, verificacionesLineales, estado,
						vdstAsync);

			} else if (consola.archivo != null && consola.archivo.toFile().exists()) {
				CrashDetectorLogger.log("[DEBUG_LOG] No hay contenido precargado útil; leyendo archivo como streaming: "
						+ consola.archivo);

				motorStreaming.procesarArchivo(consola, verificaciones, verificacionesLineales, estado, vdstAsync);

			} else {
				CrashDetectorLogger.log("[DEBUG_LOG] Consola sin contenido útil, líneas útiles ni archivo válido");
			}

			finalizarVDST(vdstAsync);

			finalizarVerificaciones(consola, estado);

			CrashDetectorLogger.log("[DEBUG_LOG] Finalizado análisis de consola: "
					+ (consola.archivo != null ? consola.archivo.getFileName() : "unknown"));

		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		} finally {
			finalizarVDST(vdstAsync);
		}
	}

	private boolean tieneLineasVerificar(Consola consola) {
		return consola != null && consola.lineas_verificar != null && consola.lineas_verificar.length > 0;
	}

	private boolean tieneContenidoVerificar(Consola consola) {
		return consola != null && consola.contenido_verificar != null && !consola.contenido_verificar.isEmpty();
	}

	private ProcesadorVDSTAsync crearProcesadorVDSTAsync(Consola consola) {
		if (consola == null || consola.verificacion_de_stacktrace == null) {
			return null;
		}

		return new ProcesadorVDSTAsync(consola.verificacion_de_stacktrace);
	}

	private void finalizarVDST(ProcesadorVDSTAsync vdstAsync) {
		if (vdstAsync == null) {
			return;
		}

		try {
			vdstAsync.finalizarYEsperar();
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
	}

	private List<Verificaciones> obtenerVerificacionesLineales(Consola consola) {
		List<Verificaciones> resultado = new ArrayList<>();

		for (Verificaciones ver : verificaciones) {
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