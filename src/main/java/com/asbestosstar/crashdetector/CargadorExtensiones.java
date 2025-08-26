package com.asbestosstar.crashdetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Cargador de extensiones para CrashDetector compatible con Java 8-21+ Este
 * cargador procesa el archivo de lista de mods generado por el sistema
 */
public class CargadorExtensiones {

	/**
	 * Carga extensiones para el proceso de la aplicación (procesoDelApp) utilizando
	 * el archivo de lista de mods
	 * 
	 * @param archivoListaMods Archivo de texto que contiene la lista de mods (uno
	 *                         por línea)
	 */
	public static void cargarExtensionesProcesoApp(File archivoListaMods) {
		// Validar archivo de lista
		if (archivoListaMods == null || !archivoListaMods.isFile()) {
			return;
		}

		try (BufferedReader lector = new BufferedReader(new FileReader(archivoListaMods))) {
			String rutaMod;
			while ((rutaMod = lector.readLine()) != null) {
				rutaMod = rutaMod.trim();
				if (rutaMod.isEmpty())
					continue;

				File archivoMod = new File(rutaMod);

				// Validar archivo mod
				if (!archivoMod.isFile()) {
					continue;
				}

				String nombreArchivo = archivoMod.getName().toLowerCase();
				if (!(nombreArchivo.endsWith(".jar") || nombreArchivo.endsWith(".fpm")
						|| nombreArchivo.endsWith(".cdext"))) {
					continue;
				}

				procesarModParaExtension(archivoMod, "app");
			}
		} catch (IOException e) {
			CrashDetectorLogger.log("Error leyendo lista de mods para procesoApp: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Carga extensiones para el proceso de monitoreo de PID
	 * (procesoDeLaMonitorizaciónDePID) utilizando el archivo de lista de mods
	 * 
	 * @param archivoListaMods Archivo de texto que contiene la lista de mods (uno
	 *                         por línea)
	 */
	public static void cargarExtensionesProcesoMonitor(File archivoListaMods) {
		// Validar archivo de lista
		if (archivoListaMods == null || !archivoListaMods.isFile()) {
			return;
		}

		try (BufferedReader lector = new BufferedReader(new FileReader(archivoListaMods))) {
			String rutaMod;
			while ((rutaMod = lector.readLine()) != null) {
				rutaMod = rutaMod.trim();
				if (rutaMod.isEmpty())
					continue;

				File archivoMod = new File(rutaMod);

				// Validar archivo mod
				if (!archivoMod.isFile()) {
					continue;
				}

				String nombreArchivo = archivoMod.getName().toLowerCase();
				if (!(nombreArchivo.endsWith(".jar") || nombreArchivo.endsWith(".fpm")
						|| nombreArchivo.endsWith(".cdext"))) {
					continue;
				}

				procesarModParaExtension(archivoMod, "monitor");
			}
		} catch (IOException e) {
			CrashDetectorLogger.log("Error leyendo lista de mods para procesoMonitor: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Procesa un archivo mod específico para cargar su extensión
	 * 
	 * @param archivoMod  Archivo mod a procesar
	 * @param tipoProceso Tipo de proceso ("app" o "monitor")
	 */
	private static void procesarModParaExtension(File archivoMod, String tipoProceso) {
		try (JarFile archivoJar = new JarFile(archivoMod)) {
			Manifest manifiesto = archivoJar.getManifest();
			if (manifiesto == null) {
				return;
			}

			Attributes atributos = manifiesto.getMainAttributes();
			String nombreClase = atributos.getValue("ExtencionCrashDetector");
			if (nombreClase == null || nombreClase.trim().isEmpty()) {
				return;
			}

			// Configurar cargador de clases
			URL urlJar = archivoMod.toURI().toURL();
			URLClassLoader cargadorClases = null;
			try {
				cargadorClases = new URLClassLoader(new URL[] { urlJar }, CargadorExtensiones.class.getClassLoader());

				Class<?> claseExt = Class.forName(nombreClase, true, cargadorClases);
				if (!Extencion.class.isAssignableFrom(claseExt)) {
					return;
				}

				Extencion extension = (Extencion) claseExt.getDeclaredConstructor().newInstance();

				// Ejecutar según el tipo de proceso
				if ("app".equals(tipoProceso)) {
					extension.procesoDelApp();
				} else {
					extension.procesoDeLaMonitorizacionDePID();
				}
			} catch (Exception e) {
				String mensaje = "Error cargando extensión para "
						+ ("app".equals(tipoProceso) ? "aplicación" : "monitoreo") + " en mod: " + archivoMod.getName();
				CrashDetectorLogger.log(mensaje);
				e.printStackTrace();
			} finally {
				if (cargadorClases != null) {
					try {
						cargadorClases.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			CrashDetectorLogger.log("Error procesando mod: " + archivoMod.getName());
			e.printStackTrace();
		}
	}
}