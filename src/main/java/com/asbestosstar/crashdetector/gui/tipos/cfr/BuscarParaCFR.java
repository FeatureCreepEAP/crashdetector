package com.asbestosstar.crashdetector.gui.tipos.cfr;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Localizador de CFR (Class File Reader). Busca el archivo cfr.jar en
 * ubicaciones comunes en sistemas UNIX/Linux, incluyendo una carpeta
 * personalizada dentro del directorio de usuario.
 */
public class BuscarParaCFR {

	/**
	 * Ubicaciones comunes donde se puede encontrar cfr.jar en sistemas UNIX/Linux.
	 */
	private static final List<String> UBICACIONES_COMUNES = Arrays.asList("/usr/share/java/CFR/cfr.jar",
			"/usr/local/share/java/CFR/cfr.jar", "/opt/cfr/cfr.jar", "/usr/share/java/cfr.jar",
			"/usr/local/lib/cfr.jar");

	/**
	 * Busca y devuelve la primera ruta válida donde se encuentre cfr.jar.
	 *
	 * @return un objeto File apuntando al JAR de CFR, o null si no se encuentra.
	 */
	public static File encontrarCfr() {
		// Revisar las ubicaciones comunes predefinidas
		for (String ruta : UBICACIONES_COMUNES) {
			File archivo = new File(ruta);
			if (archivo.exists() && archivo.isFile()) {
				return archivo;
			}
		}

		// Buscar en la carpeta personalizada del usuario
		File carpetaUsuario = new File(System.getProperty("user.home"), "crash_detector/cfr/");
		if (carpetaUsuario.exists() && carpetaUsuario.isDirectory()) {
			File[] archivosJar = carpetaUsuario.listFiles((dir, name) -> name.toLowerCase().endsWith(".jar"));
			if (archivosJar != null && archivosJar.length > 0) {
				// Devolver el primer JAR encontrado
				return archivosJar[0];
			}
		}

		return null;
	}

	/**
	 * Verifica si CFR está instalado en alguna de las ubicaciones soportadas.
	 *
	 * @return true si se encuentra al menos una copia válida de cfr.jar; false en
	 *         caso contrario.
	 */
	public static boolean estaInstalado() {
		return encontrarCfr() != null;
	}
}