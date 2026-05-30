package com.asbestosstar.crashdetector.dto.modpack.importar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;

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

	public static ResultadoImportacion ejecutar(List<TrabajoImportacion> trabajos) {
		ResultadoImportacion total = new ResultadoImportacion();

		if (trabajos == null || trabajos.isEmpty()) {
			return total;
		}

		int hilos = calcularHilos();
		ExecutorService executor = Executors.newFixedThreadPool(hilos);
		List<Future<ResultadoImportacion>> futuros = new ArrayList<Future<ResultadoImportacion>>();

		for (final TrabajoImportacion trabajo : trabajos) {
			if (trabajo == null) {
				continue;
			}

			futuros.add(executor.submit(new java.util.concurrent.Callable<ResultadoImportacion>() {
				@Override
				public ResultadoImportacion call() throws Exception {
					return trabajo.ejecutar();
				}
			}));
		}

		executor.shutdown();

		for (Future<ResultadoImportacion> futuro : futuros) {
			try {
				mezclar(total, futuro.get());
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
				total.errores++;
				total.mensajes.add("Error en trabajo paralelo de importacion: " + t.getMessage());
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
		 * Importar modpacks es principalmente I/O + HTTP, no CPU puro. x2 aprovecha
		 * mejor el tiempo muerto esperando red/disco.
		 */
		return Math.max(2, cpu * 2);
	}

	public static void mezclar(ResultadoImportacion total, ResultadoImportacion r) {
		ImportadorModpack.mezclar(total, r);
	}
}