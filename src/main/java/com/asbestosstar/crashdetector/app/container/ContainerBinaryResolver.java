package com.asbestosstar.crashdetector.app.container;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.asbestosstar.crashdetector.Statics;

/**
 * Localiza clientes opcionales sin convertirlos en dependencias obligatorias.
 *
 * Orden: 1. --binary 2. propiedad
 * crashdetector.container.&lt;plataforma&gt;.binary 3. variable
 * CRASHDETECTOR_&lt;PLATAFORMA&gt;_BINARY 4. deps/bin de la instancia, deps/bin
 * mundial y ./deps/bin 5. PATH
 */
public final class ContainerBinaryResolver {

	private ContainerBinaryResolver() {
	}

	public static String resolver(String plataforma, String nombrePredeterminado, String explicito)
			throws ContainerIntegrationException {

		String candidato = limpiar(explicito);

		if (candidato == null) {
			candidato = limpiar(System.getProperty("crashdetector.container." + plataforma + ".binary"));
		}

		if (candidato == null) {
			String variable = "CRASHDETECTOR_" + plataforma.toUpperCase(Locale.ROOT).replace('-', '_') + "_BINARY";
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

		/*
		 * Se deja el nombre simple para que ProcessBuilder utilice PATH. Un error de
		 * arranque se convierte después en un mensaje específico.
		 */
		return nombrePredeterminado;
	}

	private static String validarCandidatoExplicito(String candidato) throws ContainerIntegrationException {
		File archivo = new File(candidato);
		boolean pareceRuta = archivo.isAbsolute() || candidato.indexOf('/') >= 0 || candidato.indexOf('\\') >= 0;

		if (!pareceRuta) {
			return candidato;
		}

		try {
			archivo = archivo.getCanonicalFile();
		} catch (IOException e) {
			throw new ContainerIntegrationException("No se pudo resolver el binario: " + candidato, e);
		}

		if (!archivo.exists()) {
			throw new ContainerIntegrationException("El binario no existe: " + archivo.getAbsolutePath());
		}
		if (!archivo.isFile()) {
			throw new ContainerIntegrationException("El binario no es un archivo: " + archivo.getAbsolutePath());
		}
		if (!archivo.canExecute()) {
			throw new ContainerIntegrationException("El archivo no es ejecutable: " + archivo.getAbsolutePath());
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

	private static String rutaCanonica(File archivo) throws ContainerIntegrationException {
		try {
			return archivo.getCanonicalPath();
		} catch (IOException e) {
			throw new ContainerIntegrationException("No se pudo resolver el binario: " + archivo, e);
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
