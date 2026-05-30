package com.asbestosstar.crashdetector.gui.tipos.cfr;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.asbestosstar.crashdetector.Statics;

/**
 * Localizador de CFR (Class File Reader). Busca el archivo cfr.jar en
 * ubicaciones comunes, en la carpeta universal de dependencias, en la carpeta
 * de dependencias de la instancia y en la carpeta legacy de CFR.
 */
public class BuscarParaCFR {

	public static final File CARPETA_DEPS = new File(Statics.carpeta_mundial_como_archivo, "deps");

	public static final File CARPETA_DEPS_INSTANCIA = Statics.carpeta.resolve("deps").toFile();

	// Compatibilidad legacy: ubicación vieja.
	public static final File CARPETA_CFR_LEGACY = new File(Statics.carpeta_mundial_como_archivo, "cfr");

	private static final List<String> UBICACIONES_COMUNES = Arrays.asList("/usr/share/java/CFR/cfr.jar",
			"/usr/local/share/java/CFR/cfr.jar", "/opt/cfr/cfr.jar", "/usr/share/java/cfr.jar",
			"/usr/local/lib/cfr.jar");

	public static File encontrarCfr() {
		CARPETA_DEPS.mkdirs();

		// Primero buscar en la carpeta universal nueva.
		File cfrEnDeps = encontrarCfrEnCarpeta(CARPETA_DEPS);
		if (cfrEnDeps != null) {
			return cfrEnDeps;
		}

		// Si existe en legacy, moverlo a la carpeta universal.
		File cfrLegacy = encontrarCfrEnCarpeta(CARPETA_CFR_LEGACY);
		if (cfrLegacy != null) {
			File destino = new File(CARPETA_DEPS, cfrLegacy.getName());

			if (moverArchivo(cfrLegacy, destino)) {
				return destino;
			}

			// Si no se pudo mover, usar legacy como fallback.
			return cfrLegacy;
		}

		// Después buscar en la carpeta deps de la instancia actual.
		File cfrEnDepsInstancia = encontrarCfrEnCarpeta(CARPETA_DEPS_INSTANCIA);
		if (cfrEnDepsInstancia != null) {
			return cfrEnDepsInstancia;
		}

		// Revisar ubicaciones comunes del sistema.
		for (String ruta : UBICACIONES_COMUNES) {
			File archivo = new File(ruta);
			if (archivo.exists() && archivo.isFile()) {
				return archivo;
			}
		}

		return null;
	}

	public static boolean moverArchivo(File origen, File destino) {
		if (origen == null || destino == null || !origen.exists() || !origen.isFile()) {
			return false;
		}

		try {
			File carpetaDestino = destino.getParentFile();

			if (carpetaDestino != null) {
				carpetaDestino.mkdirs();
			}

			if (destino.exists()) {
				return true;
			}

			return origen.renameTo(destino);
		} catch (Throwable t) {
			return false;
		}
	}

	public static File encontrarCfrEnCarpeta(File carpeta) {
		if (carpeta == null || !carpeta.exists() || !carpeta.isDirectory()) {
			return null;
		}

		File[] archivosJar = carpeta.listFiles((dir, name) -> {
			String n = name.toLowerCase();

			return n.endsWith(".jar") && (n.equals("cfr.jar") || n.startsWith("cfr-"));
		});

		if (archivosJar != null && archivosJar.length > 0) {
			return archivosJar[0];
		}

		return null;
	}

	public static boolean estaInstalado() {
		return encontrarCfr() != null;
	}
}