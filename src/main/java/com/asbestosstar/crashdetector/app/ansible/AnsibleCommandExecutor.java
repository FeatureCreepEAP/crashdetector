package com.asbestosstar.crashdetector.app.ansible;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Ejecuta Ansible sin shell, sin entrada interactiva y con límites explícitos.
 */
public final class AnsibleCommandExecutor {

	private static final long MAX_STDERR_BYTES = 8L * 1024L * 1024L;

	private AnsibleCommandExecutor() {
	}

	public static AnsibleCommandResult ejecutar(List<String> comando, File directorioTrabajo,
			Map<String, String> entorno, long timeoutSegundos, long maxStdoutBytes) throws AnsibleIntegrationException {

		if (comando == null || comando.isEmpty()) {
			throw new AnsibleIntegrationException("No se recibió ningún comando Ansible.");
		}
		if (timeoutSegundos <= 0L) {
			throw new AnsibleIntegrationException("El timeout debe ser mayor que cero.");
		}
		if (maxStdoutBytes <= 0L) {
			throw new AnsibleIntegrationException("El límite de salida debe ser mayor que cero.");
		}
		if (directorioTrabajo != null && (!directorioTrabajo.exists() || !directorioTrabajo.isDirectory())) {
			throw new AnsibleIntegrationException(
					"El directorio de trabajo no existe: " + directorioTrabajo.getAbsolutePath());
		}

		final Process proceso;
		try {
			ProcessBuilder builder = new ProcessBuilder(comando);
			builder.redirectErrorStream(false);
			if (directorioTrabajo != null) {
				builder.directory(directorioTrabajo);
			}
			Map<String, String> entornoProceso = builder.environment();
			if (entorno != null) {
				entornoProceso.putAll(entorno);
			}
			proceso = builder.start();
		} catch (IOException e) {
			throw new AnsibleIntegrationException("No se pudo iniciar '" + comando.get(0)
					+ "'. Instale Ansible, colóquelo en deps/bin o configure el binario explícitamente.", e);
		}

		/*
		 * Cerramos stdin inmediatamente: esta integración nunca permite prompts de
		 * contraseña ni confirmaciones interactivas.
		 */
		try {
			OutputStream stdin = proceso.getOutputStream();
			stdin.close();
		} catch (IOException ignorado) {
		}

		final AtomicReference<IOException> errorCaptura = new AtomicReference<IOException>();
		final Capturador stdout = new Capturador(proceso.getInputStream(), maxStdoutBytes, proceso, errorCaptura,
				"stdout");
		final Capturador stderr = new Capturador(proceso.getErrorStream(), Math.min(maxStdoutBytes, MAX_STDERR_BYTES),
				proceso, errorCaptura, "stderr");

		Thread hiloOut = new Thread(stdout, "CrashDetector-Ansible-stdout");
		Thread hiloErr = new Thread(stderr, "CrashDetector-Ansible-stderr");
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
			throw new AnsibleIntegrationException("La ejecución de Ansible fue interrumpida.", e);
		}

		if (!termino) {
			destruir(proceso);
			unir(hiloOut);
			unir(hiloErr);
			throw new AnsibleIntegrationException("Ansible superó el timeout de " + timeoutSegundos + " segundos.");
		}

		unir(hiloOut);
		unir(hiloErr);

		IOException falloCaptura = errorCaptura.get();
		if (falloCaptura != null) {
			throw new AnsibleIntegrationException(falloCaptura.getMessage(), falloCaptura);
		}

		return new AnsibleCommandResult(stdout.comoTexto(), stderr.comoTexto(), proceso.exitValue(),
				comandoVisible(comando));
	}

	public static AnsibleCommandResult ejecutar(List<String> comando, long timeoutSegundos, long maxStdoutBytes)
			throws AnsibleIntegrationException {
		return ejecutar(comando, null, Collections.<String, String>emptyMap(), timeoutSegundos, maxStdoutBytes);
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

	private static void unir(Thread hilo) throws AnsibleIntegrationException {
		try {
			hilo.join(5000L);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new AnsibleIntegrationException("La captura de Ansible fue interrumpida.", e);
		}

		if (hilo.isAlive()) {
			throw new AnsibleIntegrationException("No se pudo cerrar un flujo del proceso Ansible.");
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
								"La salida " + nombre + " de Ansible superó el límite de " + limite + " bytes.");
						errorCompartido.compareAndSet(null, error);
						destruir(proceso);
						return;
					}
					salida.write(buffer, 0, leidos);
				}
			} catch (IOException e) {
				if (errorCompartido.get() == null) {
					errorCompartido.compareAndSet(null,
							new IOException("Error leyendo " + nombre + " del proceso Ansible.", e));
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
