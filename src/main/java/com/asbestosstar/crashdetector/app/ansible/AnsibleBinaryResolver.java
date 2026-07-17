package com.asbestosstar.crashdetector.app.ansible;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.asbestosstar.crashdetector.Statics;

/**
 * Localiza ansible-runner y ansible-playbook como dependencias opcionales.
 */
public final class AnsibleBinaryResolver {

	private AnsibleBinaryResolver() {
	}

	public static String resolverRunner(String explicito) throws AnsibleIntegrationException {
		return resolver("runner", "ansible-runner", explicito);
	}

	public static String resolverPlaybook(String explicito) throws AnsibleIntegrationException {
		return resolver("playbook", "ansible-playbook", explicito);
	}

	public static boolean estaDisponible(String binario) {
		if (binario == null || binario.trim().isEmpty()) {
			return false;
		}
		try {
			AnsibleCommandResult resultado = AnsibleCommandExecutor
					.ejecutar(java.util.Arrays.asList(binario, "--version"), 15L, 1024L * 1024L);
			return resultado.getExitCode() == 0;
		} catch (AnsibleIntegrationException e) {
			return false;
		}
	}

	private static String resolver(String tipo, String nombrePredeterminado, String explicito)
			throws AnsibleIntegrationException {
		String candidato = limpiar(explicito);

		if (candidato == null) {
			candidato = limpiar(System.getProperty("crashdetector.ansible." + tipo + ".binary"));
		}

		if (candidato == null) {
			String variable = "CRASHDETECTOR_ANSIBLE_" + tipo.toUpperCase(Locale.ROOT) + "_BINARY";
			candidato = limpiar(System.getenv(variable));
		}

		if (candidato != null) {
			return validarCandidatoExplicito(candidato);
		}

		for (File carpeta : carpetasDepsBin()) {
			File encontrado = buscarEnCarpeta(carpeta, nombrePredeterminado);
			if (encontrado != null) {
				return rutaCanonica(encontrado);
			}
		}

		return nombrePredeterminado;
	}

	private static String validarCandidatoExplicito(String candidato) throws AnsibleIntegrationException {
		File archivo = new File(candidato);
		boolean pareceRuta = archivo.isAbsolute() || candidato.indexOf('/') >= 0 || candidato.indexOf('\\') >= 0;

		if (!pareceRuta) {
			return candidato;
		}

		try {
			archivo = archivo.getCanonicalFile();
		} catch (IOException e) {
			throw new AnsibleIntegrationException("No se pudo resolver el binario Ansible: " + candidato, e);
		}

		if (!archivo.exists()) {
			throw new AnsibleIntegrationException("El binario Ansible no existe: " + archivo.getAbsolutePath());
		}
		if (!archivo.isFile()) {
			throw new AnsibleIntegrationException("El binario Ansible no es un archivo: " + archivo.getAbsolutePath());
		}
		if (!archivo.canExecute()) {
			throw new AnsibleIntegrationException("El archivo Ansible no es ejecutable: " + archivo.getAbsolutePath());
		}

		return archivo.getAbsolutePath();
	}

	private static List<File> carpetasDepsBin() {
		List<File> carpetas = new ArrayList<File>();

		try {
			if (Statics.carpeta != null) {
				carpetas.add(Statics.carpeta.resolve("deps").resolve("bin").toFile());
			}
		} catch (Throwable ignorado) {
		}

		try {
			if (Statics.carpeta_mundial_como_archivo != null) {
				carpetas.add(new File(new File(Statics.carpeta_mundial_como_archivo, "deps"), "bin"));
			}
		} catch (Throwable ignorado) {
		}

		carpetas.add(new File(new File("deps"), "bin"));
		return carpetas;
	}

	private static File buscarEnCarpeta(File carpeta, String nombre) {
		if (carpeta == null || !carpeta.isDirectory()) {
			return null;
		}

		File normal = new File(carpeta, nombre);
		if (normal.isFile() && normal.canExecute()) {
			return normal;
		}

		if (esWindows()) {
			File exe = new File(carpeta, nombre.toLowerCase(Locale.ROOT).endsWith(".exe") ? nombre : nombre + ".exe");
			if (exe.isFile() && exe.canExecute()) {
				return exe;
			}
		}

		return null;
	}

	private static String rutaCanonica(File archivo) throws AnsibleIntegrationException {
		try {
			return archivo.getCanonicalPath();
		} catch (IOException e) {
			throw new AnsibleIntegrationException("No se pudo resolver el binario Ansible: " + archivo, e);
		}
	}

	private static boolean esWindows() {
		String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
		return os.contains("win");
	}

	private static String limpiar(String valor) {
		if (valor == null) {
			return null;
		}
		String limpio = valor.trim();
		return limpio.isEmpty() ? null : limpio;
	}
}
