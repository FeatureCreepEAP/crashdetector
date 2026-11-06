package com.asbestosstar.crashdetector.dto.modpack.importar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

public class ImportadorParalelo {

	public interface TrabajoImportacion {
		ResultadoImportacion ejecutar() throws Exception;
	}

	/*
	 * Evita que varios threads abran dialogos de conflicto al mismo tiempo. Tambien
	 * evita escrituras simultaneas raras sobre el mismo archivo cuando varias
	 * entradas del ZIP apuntan al mismo destino.
	 */
	public static final Object BLOQUEO_CONFLICTOS = new Object();

	/*
	 * Cache compartida para consultas de metadatos (SHA-1 lookups, API calls).
	 * Evita llamadas HTTP duplicadas cuando multiples archivos comparten el mismo SHA-1.
	 */
	public static final java.util.concurrent.ConcurrentHashMap<String, Object> cacheApi =
			new java.util.concurrent.ConcurrentHashMap<String, Object>();

	public static ResultadoImportacion ejecutar(List<TrabajoImportacion> trabajos) {
		ResultadoImportacion total = new ResultadoImportacion();

		if (trabajos == null || trabajos.isEmpty()) {
			return total;
		}

		int hilos = calcularHilos();
		ExecutorService executor = Executors.newFixedThreadPool(hilos);
		List<CompletableFuture<ResultadoImportacion>> futuros = new ArrayList<CompletableFuture<ResultadoImportacion>>();

		for (final TrabajoImportacion trabajo : trabajos) {
			if (trabajo == null) {
				continue;
			}

			final TrabajoImportacion t = trabajo;
			futuros.add(CompletableFuture.supplyAsync(new java.util.function.Supplier<ResultadoImportacion>() {
				@Override
				public ResultadoImportacion get() {
					try {
						return t.ejecutar();
					} catch (Exception e) {
						ResultadoImportacion r = new ResultadoImportacion();
						r.errores++;
						r.mensajes.add("Error en trabajo: " + e.getMessage());
						return r;
					}
				}
			}, executor));
		}

		executor.shutdown();

		/*
		 * Usar joinAll para esperar todos los futures en paralelo en lugar de
		 * secuencialmente con futuro.get(). Esto permite que el thread principal
		 * no sea un cuello de botella.
		 */
		CompletableFuture<Void> todos = CompletableFuture.allOf(
				futuros.toArray(new CompletableFuture[0]));

		try {
			todos.get();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		for (CompletableFuture<ResultadoImportacion> futuro : futuros) {
			try {
				ResultadoImportacion r = futuro.getNow(null);
				if (r != null) {
					mezclar(total, r);
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
				total.errores++;
				total.mensajes.add("Error al recoger resultado: " + t.getMessage());
			}
		}

		return total;
	}

	public static List<TrabajoImportacion> listaSincronizada() {
		return Collections.synchronizedList(new ArrayList<TrabajoImportacion>());
	}

	public static int calcularHilos() {
		int cpu = Runtime.getRuntime().availableProcessors();

		/*
		 * Importar modpacks es principalmente I/O + HTTP, no CPU puro.
		 * x4 aprovecha mejor el tiempo muerto esperando red/disco.
		 * Minimo 8 hilos para modpacks grandes con muchos mods.
		 */
		return Math.max(8, cpu * 4);
	}

	public static void mezclar(ResultadoImportacion total, ResultadoImportacion r) {
		ImportadorModpack.mezclar(total, r);
	}
}