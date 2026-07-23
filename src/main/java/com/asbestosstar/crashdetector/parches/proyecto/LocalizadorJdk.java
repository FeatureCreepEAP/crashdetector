package com.asbestosstar.crashdetector.parches.proyecto;

import java.io.File;

/**
 * Localiza y valida un JDK sin depender de comandos del sistema operativo.
 */
public final class LocalizadorJdk {

	private LocalizadorJdk() {
	}

	/**
	 * Devuelve el JDK usado por el proceso actual cuando java.home contiene javac.
	 * También soporta la disposición antigua JDK/jre de Java 8.
	 */
	public static File localizarJdkActual() {
		String javaHome = System.getProperty("java.home", "").trim();
		if (javaHome.isEmpty()) {
			return null;
		}

		File directo = normalizarJdk(new File(javaHome));
		if (directo != null) {
			return directo;
		}

		File hogar = new File(javaHome);
		File padre = hogar.getParentFile();
		if (padre != null) {
			File antiguo = normalizarJdk(padre);
			if (antiguo != null) {
				return antiguo;
			}
		}

		return null;
	}

	/**
	 * Acepta la raíz del JDK, su carpeta bin o el propio ejecutable javac.
	 */
	public static File normalizarJdk(File seleccion) {
		if (seleccion == null) {
			return null;
		}

		try {
			File canonico = seleccion.getCanonicalFile();

			if (canonico.isFile() && esNombreJavac(canonico.getName())) {
				File bin = canonico.getParentFile();
				File raiz = bin == null ? null : bin.getParentFile();
				return raiz != null && localizarJavac(raiz) != null ? raiz : null;
			}

			if (canonico.isDirectory() && "bin".equalsIgnoreCase(canonico.getName())) {
				File raiz = canonico.getParentFile();
				return raiz != null && localizarJavac(raiz) != null ? raiz : null;
			}

			if (canonico.isDirectory() && localizarJavac(canonico) != null) {
				return canonico;
			}
		} catch (Exception ignorado) {
		}

		return null;
	}

	public static boolean esJdkValido(File seleccion) {
		return normalizarJdk(seleccion) != null;
	}

	public static File localizarJavac(File jdk) {
		if (jdk == null) {
			return null;
		}

		File bin = new File(jdk, "bin");
		File javac = new File(bin, esWindows() ? "javac.exe" : "javac");
		if (javac.isFile()) {
			return javac;
		}

		// Algunas instalaciones exponen ambos nombres aun en entornos compatibles.
		File alternativo = new File(bin, esWindows() ? "javac" : "javac.exe");
		return alternativo.isFile() ? alternativo : null;
	}

	private static boolean esNombreJavac(String nombre) {
		return "javac".equalsIgnoreCase(nombre) || "javac.exe".equalsIgnoreCase(nombre);
	}

	private static boolean esWindows() {
		return System.getProperty("os.name", "").toLowerCase(java.util.Locale.ROOT).contains("win");
	}
}
