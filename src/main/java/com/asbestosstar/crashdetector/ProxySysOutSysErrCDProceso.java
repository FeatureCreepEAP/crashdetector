package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.gui.tipos.consola.ConsolaDesarrolladorGUI;

/**
 * Proxy robusto de System.out / System.err con soporte para consola TL. Escribe
 * a archivo, consola normal y consola de desarrollador simultáneamente.
 */
public class ProxySysOutSysErrCDProceso {

	// Buffer por línea para consola dev
	private static final StringBuilder lineaActual = new StringBuilder();

	// Buffer mientras la consola aún no existe
	private static final List<String> bufferConsola = new ArrayList<>();

	public static void init() {

		if (!Config.obtenerInstancia().obtenerProxySysOutSysErr()) {
			return;
		}

		File archivoLog = new File(CrashDetectorLogger.LOG_FILE_PATH);

		try {
			// Flujo único al archivo
			FileOutputStream flujoArchivo = new FileOutputStream(archivoLog, false);

			OutputStream flujoArchivoSeguro = new OutputStream() {

				private final Object cerrojo = new Object();

				@Override
				public void write(int b) throws IOException {
					synchronized (cerrojo) {
						flujoArchivo.write(b);
					}
				}

				@Override
				public void flush() throws IOException {
					synchronized (cerrojo) {
						flujoArchivo.flush();
					}
				}

				@Override
				public void close() throws IOException {
					flush();
				}
			};

			PrintStream salidaOriginal = System.out;
			PrintStream errorOriginal = System.err;

			OutputStream flujoSalidaCombinado = new OutputStream() {

				@Override
				public void write(int b) {

					try {
						flujoArchivoSeguro.write(b);
					} catch (IOException e) {
						manejarErrorRegistro(e, "salida");
					}

					escribirEnConsolaSeguro(salidaOriginal, b);

					// Consola TL
					consumirByteParaConsola(b);
				}

				@Override
				public void flush() {
					try {
						flujoArchivoSeguro.flush();
					} catch (IOException e) {
						manejarErrorRegistro(e, "salida");
					}
					salidaOriginal.flush();
				}
			};

			OutputStream flujoErrorCombinado = new OutputStream() {

				@Override
				public void write(int b) {

					try {
						flujoArchivoSeguro.write(b);
					} catch (IOException e) {
						manejarErrorRegistro(e, "error");
					}

					escribirEnConsolaSeguro(errorOriginal, b);

					// Consola TL
					consumirByteParaConsola(b);
				}

				@Override
				public void flush() {
					try {
						flujoArchivoSeguro.flush();
					} catch (IOException e) {
						manejarErrorRegistro(e, "error");
					}
					errorOriginal.flush();
				}
			};

			System.setOut(new PrintStream(flujoSalidaCombinado, true));
			System.setErr(new PrintStream(flujoErrorCombinado, true));

		} catch (FileNotFoundException e) {
			System.err.println("ERROR: No se pudo abrir archivo de registro: " + e.getMessage());
		}
	}

	// ===== Consola TL =====

	private static synchronized void consumirByteParaConsola(int b) {

		if (b == '\n') {
			String linea = lineaActual.toString();
			lineaActual.setLength(0);

			if (!linea.trim().isEmpty()) {
				enviarALaConsola(linea);
			}
			return;
		}

		if (b != '\r') {
			lineaActual.append((char) b);
		}
	}

	private static synchronized void enviarALaConsola(String linea) {

		try {

			if (!ConfigMundial.obtenerInstancia().obtenerConsolaDesarrollo()) {
				return;
			}

			ConsolaDesarrolladorGUI consola = consola();

			if (consola == null) {
				bufferConsola.add(linea);
				return;
			}

			if (!bufferConsola.isEmpty()) {
				for (String l : bufferConsola) {
					consola.agregarLinea(l);
				}
				bufferConsola.clear();
			}

			consola.agregarLinea(linea);

		} catch (Throwable ignored) {
		}
	}

	private static ConsolaDesarrolladorGUI consola() {
		try {
			return (ConsolaDesarrolladorGUI) MonitorDePID.consola_des;
		} catch (Throwable t) {
			return null;
		}
	}

	// ===== Utilidades =====

	private static void escribirEnConsolaSeguro(PrintStream flujo, int dato) {
		try {
			flujo.write(dato);
		} catch (Throwable ignored) {
		}
	}

	private static void manejarErrorRegistro(IOException e, String tipoFlujo) {
		try {
			System.err.println("[CRITICO] Fallo en registro de " + tipoFlujo + ": " + e.getMessage());
		} catch (Throwable t) {
			try {
				new FileOutputStream(FileDescriptor.err)
						.write(("[FALLA TOTAL] Registro " + tipoFlujo + " inaccesible\n").getBytes());
			} catch (IOException ignored) {
			}
		}
	}
}
