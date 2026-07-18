package com.asbestosstar.crashdetector.analizador.rapido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Ejecutor multinucleo compartido por una ejecucion de analisis.
 *
 * Runtime.availableProcessors() devuelve los procesadores logicos visibles para
 * la JVM. Por ello funciona de la misma forma con SMT1, SMT2, SMT4, SMT8 y
 * SMT16, sin depender de una arquitectura concreta.
 *
 * No fija afinidad a nucleos fisicos. El sistema operativo decide en que CPU
 * logica ejecuta cada hilo.
 */
final class EjecutorAnalisis implements AutoCloseable {

	private static final String PROPIEDAD_HILOS = "crashdetector.analisis.hilos";

	private final int numeroHilos;
	private final ExecutorService ejecutor;

	EjecutorAnalisis() {
		this.numeroHilos = calcularNumeroHilos();
		this.ejecutor = Executors.newFixedThreadPool(numeroHilos, new FabricaHilos());
	}

	int numeroHilos() {
		return numeroHilos;
	}

	<T> List<T> ejecutarConResultado(List<? extends Callable<T>> tareas) {

		if (tareas == null || tareas.isEmpty()) {
			return Collections.emptyList();
		}

		List<Callable<T>> copia = new ArrayList<Callable<T>>(tareas.size());

		copia.addAll(tareas);

		try {
			List<Future<T>> futuros = ejecutor.invokeAll(copia);
			List<T> resultados = new ArrayList<T>(futuros.size());

			for (Future<T> futuro : futuros) {
				try {
					resultados.add(futuro.get());
				} catch (ExecutionException e) {
					registrarCausa(e);
					resultados.add(null);
				}
			}

			return resultados;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return Collections.emptyList();
		}
	}

	void ejecutar(List<? extends Runnable> tareas) {
		if (tareas == null || tareas.isEmpty()) {
			return;
		}

		List<Callable<Void>> adaptadas = new ArrayList<Callable<Void>>(tareas.size());

		for (final Runnable tarea : tareas) {
			if (tarea == null) {
				continue;
			}

			adaptadas.add(new Callable<Void>() {
				@Override
				public Void call() {
					tarea.run();
					return null;
				}
			});
		}

		ejecutarConResultado(adaptadas);
	}

	@Override
	public void close() {
		ejecutor.shutdown();

		try {
			if (!ejecutor.awaitTermination(1L, TimeUnit.MINUTES)) {

				ejecutor.shutdownNow();

				if (!ejecutor.awaitTermination(10L, TimeUnit.SECONDS)) {

					CrashDetectorLogger.log("No se pudieron detener todos los hilos " + "del analizador.");
				}
			}
		} catch (InterruptedException e) {
			ejecutor.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

	private static int calcularNumeroHilos() {
		int disponibles = Math.max(1, Runtime.getRuntime().availableProcessors());

		int solicitados = Integer.getInteger(PROPIEDAD_HILOS, disponibles).intValue();

		if (solicitados < 1) {
			solicitados = 1;
		}

		/*
		 * No crear mas trabajadores CPU que procesadores logicos visibles. Puede
		 * reducirse con -Dcrashdetector.analisis.hilos=N.
		 */
		return Math.min(disponibles, solicitados);
	}

	private static void registrarCausa(ExecutionException e) {
		Throwable causa = e.getCause();

		if (causa instanceof Exception) {
			CrashDetectorLogger.logException((Exception) causa);
			return;
		}

		CrashDetectorLogger.log("Error grave en una tarea de analisis: " + String.valueOf(causa));
	}

	private static final class FabricaHilos implements ThreadFactory {

		private final AtomicInteger contador = new AtomicInteger();

		@Override
		public Thread newThread(Runnable tarea) {
			Thread hilo = new Thread(tarea, "CD-Analisis-" + contador.incrementAndGet());

			hilo.setDaemon(false);
			hilo.setPriority(Thread.NORM_PRIORITY);

			return hilo;
		}
	}
}
