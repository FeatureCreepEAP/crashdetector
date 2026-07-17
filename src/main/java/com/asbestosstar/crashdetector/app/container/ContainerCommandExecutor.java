package com.asbestosstar.crashdetector.app.container;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Ejecuta clientes oficiales sin shell, con captura concurrente, timeout y
 * límites de memoria.
 */
public final class ContainerCommandExecutor {

	private static final long MAX_STDERR_BYTES = 4L * 1024L * 1024L;

	private ContainerCommandExecutor() {
	}

	public static ContainerLogResult ejecutar(List<String> comando, long timeoutSegundos, long maxStdoutBytes)
			throws ContainerIntegrationException {

		if (comando == null || comando.isEmpty()) {
			throw new ContainerIntegrationException("No se recibió ningún comando para ejecutar.");
		}
		if (timeoutSegundos <= 0L) {
			throw new ContainerIntegrationException("El timeout debe ser mayor que cero.");
		}
		if (maxStdoutBytes <= 0L) {
			throw new ContainerIntegrationException("El límite de salida debe ser mayor que cero.");
		}

		final Process proceso;
		try {
			ProcessBuilder builder = new ProcessBuilder(comando);
			builder.redirectErrorStream(false);
			proceso = builder.start();
		} catch (IOException e) {
			throw new ContainerIntegrationException("No se pudo iniciar '" + comando.get(0)
					+ "'. Instale el cliente, colóquelo en deps/bin o use --binary <ruta>.", e);
		}

		final AtomicReference<IOException> errorCaptura = new AtomicReference<IOException>();
		final Capturador stdout = new Capturador(proceso.getInputStream(), maxStdoutBytes, proceso, errorCaptura,
				"stdout");
		final Capturador stderr = new Capturador(proceso.getErrorStream(), Math.min(maxStdoutBytes, MAX_STDERR_BYTES),
				proceso, errorCaptura, "stderr");

		Thread hiloOut = new Thread(stdout, "CrashDetector-Container-stdout");
		Thread hiloErr = new Thread(stderr, "CrashDetector-Container-stderr");
		hiloOut.setDaemon(true);
		hiloErr.setDaemon(true);
		hiloOut.start();
		hiloErr.start();

		boolean termino;
		try {
			termino = proceso.waitFor(timeoutSegundos, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			destruir(proceso);
			throw new ContainerIntegrationException("La obtención de logs fue interrumpida.", e);
		}

		if (!termino) {
			destruir(proceso);
			unir(hiloOut);
			unir(hiloErr);
			throw new ContainerIntegrationException(
					"El cliente de contenedores superó el timeout de " + timeoutSegundos + " segundos.");
		}

		unir(hiloOut);
		unir(hiloErr);

		IOException falloCaptura = errorCaptura.get();
		if (falloCaptura != null) {
			throw new ContainerIntegrationException(falloCaptura.getMessage(), falloCaptura);
		}

		String salida = stdout.comoTexto();
		String errores = stderr.comoTexto();
		int codigo = proceso.exitValue();
		String visible = comandoVisible(comando);

		if (codigo != 0) {
			String detalle = errores.trim().isEmpty() ? salida.trim() : errores.trim();
			if (detalle.length() > 4000) {
				detalle = detalle.substring(0, 4000) + System.lineSeparator() + "[salida truncada]";
			}
			throw new ContainerIntegrationException("El comando terminó con código " + codigo + ": " + visible
					+ (detalle.isEmpty() ? "" : System.lineSeparator() + detalle));
		}

		if (salida.trim().isEmpty()) {
			throw new ContainerIntegrationException(
					"El comando terminó correctamente, pero no devolvió contenido de log: " + visible);
		}

		return new ContainerLogResult(salida, errores, visible);
	}

	private static void destruir(Process proceso) {
		try {
			proceso.destroy();
			if (!proceso.waitFor(2L, TimeUnit.SECONDS)) {
				proceso.destroyForcibly();
			}
		} catch (Throwable ignorado) {
			try {
				proceso.destroyForcibly();
			} catch (Throwable ignoradoTambien) {
			}
		}
	}

	private static void unir(Thread hilo) throws ContainerIntegrationException {
		try {
			hilo.join(5000L);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new ContainerIntegrationException("La captura de logs fue interrumpida.", e);
		}

		if (hilo.isAlive()) {
			throw new ContainerIntegrationException("No se pudo cerrar el flujo del cliente de contenedores.");
		}
	}

	public static String comandoVisible(List<String> comando) {
		StringBuilder salida = new StringBuilder();

		for (String parte : comando) {
			if (salida.length() > 0) {
				salida.append(' ');
			}
			salida.append(citar(parte));
		}

		return salida.toString();
	}

	private static String citar(String valor) {
		if (valor == null) {
			return "''";
		}
		if (valor.matches("[A-Za-z0-9_./:=@,+-]+")) {
			return valor;
		}
		return "'" + valor.replace("'", "'\\''") + "'";
	}

	private static final class Capturador implements Runnable {

		private final InputStream entrada;
		private final long limite;
		private final Process proceso;
		private final AtomicReference<IOException> errorCompartido;
		private final String nombre;
		private final ByteArrayOutputStream salida = new ByteArrayOutputStream();

		private Capturador(InputStream entrada, long limite, Process proceso,
				AtomicReference<IOException> errorCompartido, String nombre) {
			this.entrada = entrada;
			this.limite = limite;
			this.proceso = proceso;
			this.errorCompartido = errorCompartido;
			this.nombre = nombre;
		}

		@Override
		public void run() {
			byte[] buffer = new byte[8192];
			long total = 0L;

			try {
				int leidos;
				while ((leidos = entrada.read(buffer)) != -1) {
					total += leidos;
					if (total > limite) {
						IOException error = new IOException(
								"La salida " + nombre + " superó el límite de " + limite + " bytes.");
						errorCompartido.compareAndSet(null, error);
						destruir(proceso);
						return;
					}
					salida.write(buffer, 0, leidos);
				}
			} catch (IOException e) {
				/*
				 * Cuando otro capturador destruye el proceso por límite o timeout, el cierre
				 * del stream puede producir una IOException secundaria. Conservamos el primer
				 * error útil.
				 */
				if (errorCompartido.get() == null) {
					errorCompartido.compareAndSet(null,
							new IOException("Error leyendo " + nombre + " del cliente de contenedores.", e));
				}
			} finally {
				try {
					entrada.close();
				} catch (IOException ignorado) {
				}
			}
		}

		private String comoTexto() {
			return new String(salida.toByteArray(), StandardCharsets.UTF_8);
		}
	}
}
