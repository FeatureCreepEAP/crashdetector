package com.asbestosstar.crashdetector.bajo.hw.cpu.sparc;

import java.io.File;
import java.util.List;

import com.asbestosstar.crashdetector.MonitorDePID;

public class DaxInit {

	private static final String RUTA_JAR_STREAMOFFLOAD = "/usr/lib/streamoffload/comOracleStream.jar";
	private static final String RUTA_SO_STREAMOFFLOAD = "/usr/lib/streamoffload/libstreamoffload.so";

	private static final String RUTA_JAR_STREAMOFFLOAD_VIEJA = "/usr/lib/sparcv9/comOracleStream.jar";
	private static final String RUTA_SO_STREAMOFFLOAD_VIEJA = "/usr/lib/sparcv9/libstreamoffload.so";

	private static final String ARG_ACCEL_LIB_PATH = "-DaccelLibPath=/usr/lib/streamoffload";

	/**
	 * Agrega Oracle DAX Java Stream Offload solo desde rutas confirmadas.
	 */
	public static void agregarJarsOracleStreamOffload(StringBuilder classPath, List<String> nombresYaAgregados) {
		agregarJarSiExiste(classPath, nombresYaAgregados, RUTA_JAR_STREAMOFFLOAD);
		agregarJarSiExiste(classPath, nombresYaAgregados, RUTA_JAR_STREAMOFFLOAD_VIEJA);
	}

	private static void agregarJarSiExiste(StringBuilder classPath, List<String> nombresYaAgregados, String ruta) {
		File archivo = new File(ruta);

		if (!archivo.exists() || !archivo.isFile()) {
			return;
		}

		MonitorDePID.agregarJarAlClasspathSiNoConflicta(classPath, nombresYaAgregados, archivo);
	}

	/**
	 * Devuelve true si existe el JAR de StreamOffload.
	 */
	public static boolean tieneJarOracleStreamOffload() {
		return esArchivo(RUTA_JAR_STREAMOFFLOAD) || esArchivo(RUTA_JAR_STREAMOFFLOAD_VIEJA);
	}

	/**
	 * Devuelve true si existe la biblioteca nativa de StreamOffload.
	 */
	public static boolean tieneBibliotecaNativaOracleStreamOffload() {
		return esArchivo(RUTA_SO_STREAMOFFLOAD) || esArchivo(RUTA_SO_STREAMOFFLOAD_VIEJA);
	}

	/**
	 * Oracle OffloadInfo intenta cargar por defecto:
	 * /usr/lib/sparcv9/libstreamoffload.so
	 *
	 * Pero el paquete real instala: /usr/lib/streamoffload/libstreamoffload.so
	 *
	 * Entonces solo necesitamos arg especial cuando existe la ruta nueva y no
	 * existe la ruta vieja predeterminada.
	 */
	public static boolean necesitaArgEspecialDax() {
		return esArchivo(RUTA_SO_STREAMOFFLOAD) && !esArchivo(RUTA_SO_STREAMOFFLOAD_VIEJA);
	}

	/**
	 * Arg que debe agregarse al nuevo proceso Java si necesitaArgEspecialDax()
	 * devuelve true.
	 */
	public static String obtenerArgEspecialDax() {
		return ARG_ACCEL_LIB_PATH;
	}

	private static boolean esArchivo(String ruta) {
		File archivo = new File(ruta);
		return archivo.exists() && archivo.isFile();
	}
}