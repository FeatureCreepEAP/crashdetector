package com.asbestosstar.crashdetector.analizador.rapido;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;

final class ProcesadorVDSTAsync {

	private static final int TAMANO_COLA = 256;
	private static final int TAMANO_LOTE = 512;

	private static final LoteVDST FIN = new LoteVDST(null);

	private final VerificacionDeStackTrace vdst;
	private final BlockingQueue<LoteVDST> cola = new ArrayBlockingQueue<LoteVDST>(TAMANO_COLA);
	private final Thread hilo;

	private final List<LineaVDST> loteActual = new ArrayList<LineaVDST>(TAMANO_LOTE);

	private volatile boolean cerrado = false;

	ProcesadorVDSTAsync(VerificacionDeStackTrace vdst) {
		this.vdst = vdst;

		this.vdst.reiniciarSoloEstado();

		this.hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				ejecutar();
			}
		}, "VDST-Async");

		this.hilo.setDaemon(true);
		this.hilo.start();
	}

	void aceptarLinea(String linea, int numeroLinea) {
		if (cerrado || linea == null || linea.isEmpty()) {
			return;
		}

		loteActual.add(new LineaVDST(linea, numeroLinea));

		if (loteActual.size() >= TAMANO_LOTE) {
			enviarLoteActual();
		}
	}

	void finalizarYEsperar() {
		if (cerrado) {
			return;
		}

		cerrado = true;

		enviarLoteActual();

		try {
			cola.put(FIN);
			hilo.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void enviarLoteActual() {
		if (loteActual.isEmpty()) {
			return;
		}

		List<LineaVDST> copia = new ArrayList<LineaVDST>(loteActual);
		loteActual.clear();

		try {
			cola.put(new LoteVDST(copia));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void ejecutar() {
		try {
			while (true) {
				LoteVDST lote = cola.take();

				if (lote == FIN) {
					break;
				}

				if (lote.lineas == null || lote.lineas.isEmpty()) {
					continue;
				}

				for (LineaVDST item : lote.lineas) {
					vdst.procesarLineaIncremental(item.linea, item.numeroLinea);
				}
			}

			vdst.finalizarEscaneoIncremental();

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
	}

	private static final class LoteVDST {
		final List<LineaVDST> lineas;

		LoteVDST(List<LineaVDST> lineas) {
			this.lineas = lineas;
		}
	}

	private static final class LineaVDST {
		final String linea;
		final int numeroLinea;

		LineaVDST(String linea, int numeroLinea) {
			this.linea = linea;
			this.numeroLinea = numeroLinea;
		}
	}
}