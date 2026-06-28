package com.asbestosstar.crashdetector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.asbestosstar.crashdetector.config.ConfigBoolean;

public class ProxySysOutSysErr {

	public static OutputStream flujoSincronizadoSeguro;

	public static final ConfigBoolean ENVIAR_EN_VIVO = ConfigBoolean.de("livestream_analisis", true);
	public static final ConfigBoolean ENVIAR_A_CONSOLA_DEV = ConfigBoolean.de("livestream_consola_dev", true);

	private static volatile boolean streamingHabilitado = false;
	private static volatile boolean consolaDevHabilitada = false;

	private static final BlockingQueue<String> colaLineas = new LinkedBlockingQueue<>(8192);
	private static Thread hiloEscritor;

	private static final Object cerrojoBuffer = new Object();
	private static ByteArrayOutputStream bufferParcial = new ByteArrayOutputStream(512);

	public static void init() {
		try {
			flujoSincronizadoSeguro = new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					if (streamingHabilitado) {
						byte[] tmp = new byte[] { (byte) b };
						emitirLineas(tmp, 0, 1);
					}
				}

				@Override
				public void write(byte[] b, int off, int len) throws IOException {
					if (streamingHabilitado) {
						emitirLineas(b, off, len);
					}
				}

				@Override
				public void flush() throws IOException {
				}

				@Override
				public void close() {
				}
			};

			PrintStream salidaOriginal = System.out;
			PrintStream errorOriginal = System.err;

			OutputStream flujoSalidaCombinado = crearFlujoCombinado(salidaOriginal, "salida");
			OutputStream flujoErrorCombinado = crearFlujoCombinado(errorOriginal, "error");

			System.setOut(new PrintStream(flujoSalidaCombinado, true));
			System.setErr(new PrintStream(flujoErrorCombinado, true));

			System.err.println("[ProxySysOutSysErr] Proxy inicializado (solo en memoria).");

			streamingHabilitado = ENVIAR_EN_VIVO.obtener();
			consolaDevHabilitada = ENVIAR_A_CONSOLA_DEV.obtener();

			// Iniciamos el hilo de inmediato. Intentará conectar el pipe más adelante.
			if (streamingHabilitado || consolaDevHabilitada) {
				iniciarHiloEscritor();
			}

		} catch (Throwable t) {
			System.err.println("[CRÍTICO] Falló inicialización de ProxySysOutSysErr: " + t.getMessage());
			t.printStackTrace();
		}
	}

	private static void iniciarHiloEscritor() {
		hiloEscritor = new Thread(ProxySysOutSysErr::runEscritor, "ProxySysOutSysErr-Escritor");
		hiloEscritor.setDaemon(true);
		hiloEscritor.start();
		System.err.println("[ProxySysOutSysErr] Hilo de escritura iniciado.");
	}

	private static void runEscritor() {
		OutputStream flujoLocal = null;
		boolean avisoImpreso = false;

		while (true) {
			String linea;
			try {
				linea = colaLineas.poll(500, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}
			if (linea == null) {
				continue;
			}

			// SOLUCIÓN: Revisar MonitorDePID en CADA ciclo para reconectar automáticamente
			if (streamingHabilitado && flujoLocal == null) {
				OutputStream posibleFlujo = MonitorDePID.flujoHaciaMonitor;
				if (posibleFlujo != null) {
					flujoLocal = posibleFlujo;
					if (!avisoImpreso) {
						System.err.println("[ProxySysOutSysErr] ¡Pipe hacia el monitor conectado con éxito!");
						avisoImpreso = true;
					}
				} else if (!avisoImpreso) {
					// Solo imprimir el aviso una vez para no llenar la consola
					System.err.println("[ProxySysOutSysErr] Esperando a que el proceso monitor esté listo...");
					avisoImpreso = true;
				}
			}

			// Si hay pipe, lo mandamos al proceso monitor
			if (flujoLocal != null) {
				try {
					flujoLocal.write(linea.getBytes(StandardCharsets.UTF_8));
					flujoLocal.flush();
				} catch (IOException e) {
					flujoLocal = null; // Se cayó el pipe, lo ponemos nulo para que reintente
					System.err.println("[ProxySysOutSysErr] Pipe en vivo cerrado: " + e.getMessage());
				}
			}

			// Siempre enviamos a la consola de desarrollador si está habilitada
			if (consolaDevHabilitada) {
				try {
					String limpia = linea;
					if (limpia.endsWith("\n"))
						limpia = limpia.substring(0, limpia.length() - 1);
					if (limpia.endsWith("\r"))
						limpia = limpia.substring(0, limpia.length() - 1);
					if (!limpia.isEmpty()) {
						CrashDetectorLogger.enviarALaConsola(limpia);
					}
				} catch (Throwable ignorado) {
				}
			}
		}
	}

	private static void emitirLineas(byte[] b, int off, int len) {
		if (len <= 0)
			return;
		synchronized (cerrojoBuffer) {
			int start = off;
			int end = off + len;
			for (int i = off; i < end; i++) {
				if (b[i] == (byte) '\n') {
					if (bufferParcial.size() > 0) {
						bufferParcial.write(b, start, i - start);
						byte[] datos = bufferParcial.toByteArray();
						bufferParcial.reset();
						emitirLineaCompleta(datos, 0, datos.length);
					} else {
						emitirLineaCompleta(b, start, i - start);
					}
					start = i + 1;
				}
			}
			if (start < end) {
				bufferParcial.write(b, start, end - start);
			}
		}
	}

	private static void emitirLineaCompleta(byte[] datos, int off, int len) {
		if (len > 0 && datos[off + len - 1] == (byte) '\r') {
			len--;
		}
		String texto = new String(datos, off, len, StandardCharsets.UTF_8);
		colaLineas.offer(texto + "\n");
	}

	private static OutputStream crearFlujoCombinado(PrintStream flujoConsola, String tipoFlujo) {
		return new OutputStream() {
			@Override
			public void write(int b) {
				if (streamingHabilitado) {
					byte[] tmp = new byte[] { (byte) b };
					emitirLineas(tmp, 0, 1);
				}
				escribirEnConsolaSeguro(flujoConsola, b);
			}

			@Override
			public void write(byte[] b, int off, int len) {
				if (streamingHabilitado) {
					emitirLineas(b, off, len);
				}
				try {
					flujoConsola.write(b, off, len);
				} catch (Throwable ignorado) {
				}
			}

			@Override
			public void flush() {
				flujoConsola.flush();
			}
		};
	}

	private static void escribirEnConsolaSeguro(PrintStream flujo, int b) {
		try {
			flujo.write(b);
		} catch (Throwable ignorado) {
		}
	}
}