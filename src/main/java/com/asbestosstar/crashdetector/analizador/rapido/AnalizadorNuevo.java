package com.asbestosstar.crashdetector.analizador.rapido;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.Analizador;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Motor rapido y multinucleo del analizador.
 *
 * El analisis se divide en dos fases:
 *
 * 1. Todos los logs se leen y se comparan en paralelo. 2. Cuando todos
 * terminaron de leerse, cada log se finaliza en paralelo.
 *
 * Dentro de un mismo log se conserva el orden de las lineas y de las llamadas
 * de cada verificacion. Esto evita carreras en verificaciones legacy y en las
 * colecciones mutables de Consola.
 */
public final class AnalizadorNuevo {

	private final Analizador analizadorBase;

	private final List<Verificaciones> verificaciones = new ArrayList<Verificaciones>();

	private final Set<Verificaciones> verificacionesTardias = Collections
			.newSetFromMap(new IdentityHashMap<Verificaciones, Boolean>());

	public AnalizadorNuevo(Analizador analizadorBase) {
		this.analizadorBase = analizadorBase;

		if (analizadorBase != null) {
			verificacionesTardias.addAll(analizadorBase.verificaciones_tardias_activadas);
		}
	}

	/**
	 * Analiza cada Consola en un trabajador independiente.
	 *
	 * Si hay mas logs que procesadores logicos, los logs restantes esperan en la
	 * cola. Si hay menos, no se crean tareas artificiales.
	 */
	public void analizar(List<Consola> consolas, Set<Verificaciones> todasLasVerificaciones) {

		cargarVerificaciones(todasLasVerificaciones);

		if (consolas == null || consolas.isEmpty()) {
			CrashDetectorLogger.log("Iniciando AnalizadorNuevo con 0 registros");
			return;
		}

		CrashDetectorLogger.log("Iniciando AnalizadorNuevo con " + consolas.size() + " registros");

		CrashDetectorLogger.log("[DEBUG_LOG] Verificaciones cargadas: " + verificaciones.size());

		try (EjecutorAnalisis ejecutor = new EjecutorAnalisis()) {

			CrashDetectorLogger.log("[DEBUG_LOG] Analisis multinucleo con " + ejecutor.numeroHilos()
					+ " procesadores logicos visibles");

			/*
			 * Fase 1: lectura, busqueda de patrones y verificaciones por linea. Una tarea
			 * representa exactamente un log.
			 */
			List<Callable<ContextoConsola>> tareasLectura = new ArrayList<Callable<ContextoConsola>>();

			for (final Consola consola : consolas) {
				tareasLectura.add(new Callable<ContextoConsola>() {
					@Override
					public ContextoConsola call() {
						return procesarConsolaSinFinalizar(consola);
					}
				});
			}

			List<ContextoConsola> contextos = ejecutor.ejecutarConResultado(tareasLectura);

			/*
			 * Fase 2: finalizar las verificaciones normales de cada log. Cada log vuelve a
			 * ser una tarea independiente.
			 */
			ejecutarFinalizaciones(ejecutor, contextos, false);

			/*
			 * Fase 3: las verificaciones tardias se ejecutan solo cuando todas las normales
			 * de todos los logs ya terminaron.
			 */
			ejecutarFinalizaciones(ejecutor, contextos, true);
		}
	}

	/**
	 * El flujo en vivo ya llega desde un hilo dedicado. Se conserva el orden
	 * estricto de sus lineas, pero usa el mismo motor rapido.
	 */
	public void analizarEnVivo(Consola consola, InputStream inputStream, Set<Verificaciones> todasLasVerificaciones) {

		cargarVerificaciones(todasLasVerificaciones);

		ProcesadorVDSTAsync vdstAsync = null;

		try {
			EstadoAnalisisArchivo estado = new EstadoAnalisisArchivo(consola);

			vdstAsync = crearProcesadorVDSTAsync(consola);

			List<Verificaciones> verificacionesLineales = obtenerVerificacionesLineales(consola);

			MotorDeLecturaStreaming motorStreaming = new MotorDeLecturaStreaming();

			CrashDetectorLogger.log("[DEBUG_LOG] Iniciando analisis en vivo");

			motorStreaming.procesarEnVivo(consola, inputStream, verificaciones, verificacionesLineales, estado,
					vdstAsync);

			finalizarVDST(vdstAsync);
			vdstAsync = null;

			finalizarVerificaciones(consola, estado, false);

			finalizarVerificaciones(consola, estado, true);

			CrashDetectorLogger.log("[DEBUG_LOG] Finalizado analisis en vivo");
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		} finally {
			finalizarVDST(vdstAsync);
		}
	}

	private ContextoConsola procesarConsolaSinFinalizar(Consola consola) {

		if (consola == null || consola.analizadaEnVivo) {
			CrashDetectorLogger.log("[DEBUG_LOG] Consola null o ya analizada " + "en vivo; se omite");
			return null;
		}

		ProcesadorVDSTAsync vdstAsync = null;

		try {
			EstadoAnalisisArchivo estado = new EstadoAnalisisArchivo(consola);

			CrashDetectorLogger.log("[DEBUG_LOG] Analizando registro en " + Thread.currentThread().getName() + ": "
					+ nombreConsola(consola));

			vdstAsync = crearProcesadorVDSTAsync(consola);

			List<Verificaciones> verificacionesLineales = obtenerVerificacionesLineales(consola);

			/*
			 * Es obligatorio crear un motor por log. MotorDeLecturaStreaming contiene
			 * automata y mapas mutables pertenecientes a una sola consola.
			 */
			MotorDeLecturaStreaming motorStreaming = new MotorDeLecturaStreaming();

			if (tieneLineasVerificar(consola)) {
				CrashDetectorLogger
						.log("[DEBUG_LOG] Usando lineas_verificar " + "existentes: " + consola.lineas_verificar.length);

				motorStreaming.procesarLineas(consola, consola.lineas_verificar, verificaciones, verificacionesLineales,
						estado, vdstAsync);

			} else if (tieneContenidoVerificar(consola)) {
				String[] lineas = consola.contenido_verificar.split("\\r?\\n", -1);

				consola.lineas_verificar = lineas;

				CrashDetectorLogger
						.log("[DEBUG_LOG] Usando contenido_verificar " + "existente: " + lineas.length + " lineas");

				motorStreaming.procesarLineas(consola, lineas, verificaciones, verificacionesLineales, estado,
						vdstAsync);

			} else if (consola.archivo != null && consola.archivo.toFile().exists()) {

				CrashDetectorLogger.log("[DEBUG_LOG] Leyendo archivo como streaming: " + consola.archivo);

				motorStreaming.procesarArchivo(consola, verificaciones, verificacionesLineales, estado, vdstAsync);

			} else {
				CrashDetectorLogger.log("[DEBUG_LOG] Consola sin contenido util, " + "lineas utiles ni archivo valido");
			}

			finalizarVDST(vdstAsync);
			vdstAsync = null;

			CrashDetectorLogger.log("[DEBUG_LOG] Lectura y coincidencias terminadas: " + nombreConsola(consola));

			return new ContextoConsola(consola, estado);

		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
			return null;
		} finally {
			finalizarVDST(vdstAsync);
		}
	}

	private void ejecutarFinalizaciones(EjecutorAnalisis ejecutor, List<ContextoConsola> contextos,
			final boolean tardias) {

		if (contextos == null || contextos.isEmpty()) {
			return;
		}

		List<Runnable> tareas = new ArrayList<Runnable>();

		for (final ContextoConsola contexto : contextos) {
			if (contexto == null) {
				continue;
			}

			tareas.add(new Runnable() {
				@Override
				public void run() {
					finalizarVerificaciones(contexto.consola, contexto.estado, tardias);

					if (tardias) {
						CrashDetectorLogger.log(
								"[DEBUG_LOG] Finalizado analisis " + "de consola: " + nombreConsola(contexto.consola));
					}
				}
			});
		}

		ejecutor.ejecutar(tareas);
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

		/*
		 * Esta inicializacion ocurre antes de arrancar trabajadores.
		 */
		VerificacionDeStackTrace.registrarOcupacionDeVerificaciones(verificaciones);
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

		List<Verificaciones> resultado = new ArrayList<Verificaciones>();

		for (Verificaciones ver : verificaciones) {
			try {
				/*
				 * Las mismas instancias de Verificaciones se comparten para conservar la
				 * compatibilidad con Analizador.toString(). El bloqueo evita que dos logs muten
				 * simultaneamente una verificacion legacy.
				 */
				synchronized (ver) {
					if (ver.verificar(consola)) {
						resultado.add(ver);
					}
				}
			} catch (Exception e) {
				CrashDetectorLogger.logException(e);
			}
		}

		return resultado;
	}

	private void finalizarVerificaciones(Consola consola, EstadoAnalisisArchivo estado, boolean tardias) {

		for (Verificaciones verificacion : verificaciones) {

			if (esTardia(verificacion) != tardias) {
				continue;
			}

			try {
				/*
				 * Un objeto Verificaciones puede contener estado mutable. Solo una consola
				 * puede entrar en esa instancia a la vez.
				 */
				synchronized (verificacion) {
					verificacion.finalizarArchivo(consola, estado);
				}
			} catch (Exception e) {
				CrashDetectorLogger.logException(e);
			}
		}
	}

	private boolean esTardia(Verificaciones verificacion) {

		if (verificacion == null) {
			return false;
		}

		if (verificacionesTardias.contains(verificacion)) {
			return true;
		}

		/*
		 * Normalmente las identidades coinciden porque Analizador pasa su union. La
		 * comparacion adicional mantiene compatibilidad con una coleccion equivalente
		 * creada por otro llamador.
		 */
		if (analizadorBase != null) {
			for (Verificaciones tardia : analizadorBase.verificaciones_tardias_activadas) {

				if (tardia == null) {
					continue;
				}

				if (tardia.getClass() == verificacion.getClass()) {
					return true;
				}

				try {
					if (tardia.id().equals(verificacion.id())) {
						return true;
					}
				} catch (RuntimeException e) {
					/* Tratarla como normal si el ID no puede leerse. */
				}
			}
		}

		return false;
	}

	private static String nombreConsola(Consola consola) {

		if (consola == null || consola.archivo == null) {
			return "unknown";
		}

		return String.valueOf(consola.archivo.getFileName());
	}

	private static final class ContextoConsola {
		final Consola consola;
		final EstadoAnalisisArchivo estado;

		ContextoConsola(Consola consola, EstadoAnalisisArchivo estado) {

			this.consola = consola;
			this.estado = estado;
		}
	}
}
