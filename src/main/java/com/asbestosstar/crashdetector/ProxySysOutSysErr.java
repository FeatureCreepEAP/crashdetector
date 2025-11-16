package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador.NoRegistroDeLauncherVShojo;

/**
 * Proxy para System.out y System.err cuando el Launcher no tiene registros.
 * Soluciona problemas de escritura interrumpida y garantiza que TODOS los
 * mensajes.
 */
public class ProxySysOutSysErr {

	public static File archivoLog = NoRegistroDeLauncherVShojo.cd_launcherlog;
	public static FileOutputStream flujoArchivo;
	// FLUJO SINCRONIZADO Y SEGURO QUE SE COMPARTIRÁ CON LOG4J2
	public static OutputStream flujoSincronizadoSeguro;

	static {
		try {
			archivoLog.delete();
			archivoLog.createNewFile();
			flujoArchivo = new FileOutputStream(archivoLog, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		Config config = Config.obtenerInstancia();

		// Depuración: Mostrar condición de inicialización
		boolean condicion = config.obtenerProxySysOutSysErr() || !config.propiedadesConfig.containsKey("0351");
		System.err.println("[ProxySysOutSysErr] Condición de init: " + condicion + " (proxyHabilitado="
				+ config.obtenerProxySysOutSysErr() + ", tiene0351=" + config.propiedadesConfig.containsKey("0351")
				+ ")");

		try {
			// 1. Crear FLUJO SINCRONIZADO que será compartido con Log4j2
			flujoSincronizadoSeguro = new OutputStream() {
				private final Object cerrojo = new Object();

				@Override
				public void write(int b) throws IOException {
					synchronized (cerrojo) {
						try {
							flujoArchivo.write(b);
						} catch (IOException e) {
							manejarErrorRegistro(e, "archivo");
							throw e;
						}
					}
				}

				@Override
				public void write(byte[] b, int off, int len) throws IOException {
					synchronized (cerrojo) {
						try {
							flujoArchivo.write(b, off, len);
						} catch (IOException e) {
							manejarErrorRegistro(e, "archivo");
							throw e;
						}
					}
				}

				@Override
				public void flush() throws IOException {
					synchronized (cerrojo) {
						flujoArchivo.flush();
					}
				}

				@Override
				public void close() {
					// NUNCA CERRAR - mantener el flujo abierto durante toda la ejecución
				}
			};

			// 2. Guardar REFERENCIAS a los flujos originales de consola
			PrintStream salidaOriginal = System.out;
			PrintStream errorOriginal = System.err;

			// 3. Crear FLUJOS COMBINADOS con manejo de errores
			OutputStream flujoSalidaCombinado = crearFlujoCombinado(flujoSincronizadoSeguro, salidaOriginal, "salida");
			OutputStream flujoErrorCombinado = crearFlujoCombinado(flujoSincronizadoSeguro, errorOriginal, "error");

			// 4. CONFIGURAR LOS NUEVOS FLUJOS con auto-vaciado
			System.setOut(new PrintStream(flujoSalidaCombinado, true));
			System.setErr(new PrintStream(flujoErrorCombinado, true));

			// 5. Actualizar configuración DESPUÉS de inicialización exitosa
			config.guardarProxySysOutSysErr(true);
			if (!config.propiedadesConfig.containsKey("0351")) {
				config.propiedadesConfig.put("0351", "true"); // Marcar migración completada
			}

			System.err
					.println("[ProxySysOutSysErr] Proxy inicializado. Escribiendo en: " + archivoLog.getAbsolutePath());

		} catch (Throwable t) {
			System.err.println("[CRÍTICO] Falló inicialización de ProxySysOutSysErr: " + t.getMessage());
			t.printStackTrace();
		}
	}

	private static OutputStream crearFlujoCombinado(OutputStream flujoArchivo, PrintStream flujoConsola,
			String tipoFlujo) {
		return new OutputStream() {
			@Override
			public void write(int b) {
				try {
					flujoArchivo.write(b);
				} catch (IOException e) {
					manejarErrorRegistro(e, tipoFlujo + "-archivo");
				}
				escribirEnConsolaSeguro(flujoConsola, b);
			}

			@Override
			public void write(byte[] b, int off, int len) {
				try {
					flujoArchivo.write(b, off, len);
				} catch (IOException e) {
					manejarErrorRegistro(e, tipoFlujo + "-archivo");
				}
				try {
					flujoConsola.write(b, off, len);
				} catch (Throwable ignorado) {
				}
			}

			@Override
			public void flush() {
				try {
					flujoArchivo.flush();
				} catch (IOException ignorado) {
				}
				flujoConsola.flush();
			}
		};
	}

	private static void escribirEnConsolaSeguro(PrintStream flujo, int b) {
		try {
			flujo.write(b);
		} catch (Throwable ignorado) {
			// Fallo silencioso - no romper el registro por errores de consola
		}
	}

	private static void manejarErrorRegistro(IOException e, String origen) {
		try {
			String mensaje = String.format("[REGISTRO FALLIDO][%s] %s: %s", Thread.currentThread().getName(), origen,
					e.getMessage());
			System.err.println(mensaje);
		} catch (Throwable ignorado) {
			// Manejo de excepciones de error al intentar registrar el error
		}
	}

}