package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

public class EliminadorDeMod {

	/**
	 * Elimina un archivo .jar, ya sea físico o anidado dentro de otro .jar
	 */
	public static void eliminarMod(String rutaJar) {
		try {
			if (esArchivoFisico(rutaJar)) {
				File archivo = new File(rutaJar);
				if (!archivo.exists()) {
					logError(MonitorDePID.idioma.archivo_no_encontrado() + ": " + rutaJar);
					return;
				}

				if (archivo.isDirectory()) {
					eliminarDirectorio(archivo);
					logExito(MonitorDePID.idioma.directorio_eliminado() + ": " + rutaJar);
				} else {
					if (archivo.delete()) {
						logExito(MonitorDePID.idioma.jar_eliminado_exitosamente() + ": " + rutaJar);
					} else {
						logError(MonitorDePID.idioma.error_al_eliminar_jar() + ": " + rutaJar);
					}
				}
			} else {
				eliminarJarAnidado(rutaJar);
			}
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			logError(MonitorDePID.idioma.error_al_eliminar_jar_anidado() + ": " + rutaJar);
		}
	}

	/**
	 * Elimina un elemento específico (archivo o carpeta) dentro de un mod
	 * 
	 * @param rutaMod       Ruta al mod (.jar o carpeta)
	 * @param rutaDentroMod Ruta relativa del elemento a eliminar dentro del mod
	 */
	public static void eliminarElementoEnMod(String rutaMod, String rutaDentroMod) {
		try {
			File archivoMod = new File(rutaMod);

			// Modo directorio (carpeta)
			if (archivoMod.isDirectory()) {
				File elemento = new File(archivoMod, rutaDentroMod);
				if (!elemento.exists()) {
					logError(MonitorDePID.idioma.archivo_interno_no_encontrado() + ": " + rutaMod + " -> "
							+ rutaDentroMod);
					return;
				}

				if (elemento.isDirectory()) {
					eliminarDirectorio(elemento);
					logExito(MonitorDePID.idioma.directorio_eliminado() + ": " + rutaDentroMod);
				} else {
					if (elemento.delete()) {
						logExito(MonitorDePID.idioma.archivo_eliminado() + ": " + rutaDentroMod);
					} else {
						logError(MonitorDePID.idioma.error_al_eliminar_archivo() + ": " + rutaDentroMod);
					}
				}

				return;
			}

			// Modo .jar (archivo ZIP empaquetado)
			if (!archivoMod.exists() || !archivoMod.getName().endsWith(".jar")) {
				logError(MonitorDePID.idioma.archivo_externo_no_valido() + ": " + rutaMod);
				return;
			}

			File directorioTemporal = Files.createTempDirectory("crashdetector_mod_temp_").toFile();
			directorioTemporal.deleteOnExit();

			File destinoExtraido = new File(directorioTemporal, "contenido");
			destinoExtraido.mkdirs();

			extraerZip(archivoMod, destinoExtraido);

			File elementoInterno = new File(destinoExtraido, rutaDentroMod);
			if (!elementoInterno.exists()) {
				logError(MonitorDePID.idioma.archivo_interno_no_encontrado() + ": " + rutaMod + " -> " + rutaDentroMod);
				limpiarTemporal(directorioTemporal);
				return;
			}

			if (elementoInterno.isDirectory()) {
				eliminarDirectorio(elementoInterno);
			} else {
				elementoInterno.delete();
			}

			File archivoTemporal = new File(directorioTemporal, "temp.jar");
			empaquetarZip(destinoExtraido, archivoTemporal);

			if (archivoMod.delete() && archivoTemporal.renameTo(archivoMod)) {
				logExito(MonitorDePID.idioma.elemento_mod_eliminado() + ": " + rutaDentroMod);
			} else {
				logError(MonitorDePID.idioma.error_al_reemplazar_jar_externo() + ": " + rutaMod);
			}

			limpiarTemporal(directorioTemporal);
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			logError(MonitorDePID.idioma.error_al_eliminar_elemento_mod() + ": " + rutaDentroMod);
		}
	}

	/**
	 * Elimina recursivamente un directorio y su contenido
	 */
	private static void eliminarDirectorio(File directorio) {
		if (!directorio.exists())
			return;

		File[] archivos = directorio.listFiles();
		if (archivos != null) {
			for (File archivo : archivos) {
				if (archivo.isDirectory()) {
					eliminarDirectorio(archivo);
				} else {
					if (!archivo.delete()) {
						logError(MonitorDePID.idioma.error_al_eliminar_archivo() + ": " + archivo.getAbsolutePath());
					}
				}
			}
		}

		if (!directorio.delete()) {
			logError(MonitorDePID.idioma.error_al_eliminar_directorio() + ": " + directorio.getAbsolutePath());
		}
	}

	/**
	 * Extrae un archivo ZIP/JAR a una carpeta temporal
	 */
	private static void extraerZip(File archivoZip, File directorioDestino) throws IOException {
		try (ZipFile zip = new ZipFile(archivoZip)) {
			Enumeration<? extends ZipEntry> entries = zip.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				File archivoDestino = new File(directorioDestino, entry.getName());

				if (entry.isDirectory()) {
					archivoDestino.mkdirs();
				} else {
					archivoDestino.getParentFile().mkdirs();
					try (InputStream is = zip.getInputStream(entry);
							FileOutputStream fos = new FileOutputStream(archivoDestino)) {
						byte[] buffer = new byte[1024];
						int len;
						while ((len = is.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
					}
				}
			}
		}
	}

	/**
	 * Empaqueta un directorio en un nuevo archivo ZIP/JAR
	 */
	private static void empaquetarZip(File directorio, File archivoSalida) throws IOException {
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(archivoSalida))) {
			empaquetarDirectorio(directorio, zos, "");
		}
	}

	/**
	 * Método auxiliar para empaquetar recursivamente
	 */
	private static void empaquetarDirectorio(File directorio, ZipOutputStream zos, String prefijo) throws IOException {
		for (File archivo : directorio.listFiles()) {
			String nombre = prefijo + archivo.getName();
			if (archivo.isDirectory()) {
				zos.putNextEntry(new ZipEntry(nombre + "/"));
				empaquetarDirectorio(archivo, zos, nombre + "/");
			} else {
				zos.putNextEntry(new ZipEntry(nombre));
				try (FileInputStream fis = new FileInputStream(archivo)) {
					byte[] buffer = new byte[1024];
					int len;
					while ((len = fis.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
				}
				zos.closeEntry();
			}
		}
	}

	/**
	 * Elimina un .jar anidado dentro de otro .jar
	 */
	private static void eliminarJarAnidado(String rutaJar) throws IOException {
		int indice = rutaJar.indexOf("!/");
		if (indice == -1) {
			logError(MonitorDePID.idioma.formato_invalido_para_jar_anidado() + ": " + rutaJar);
			return;
		}

		String rutaExterna = rutaJar.substring(0, indice);
		String rutaInterna = rutaJar.substring(indice + 2); // Saltar "!/"

		File archivoExterno = new File(rutaExterna);
		if (!archivoExterno.exists() || !archivoExterno.getName().endsWith(".jar")) {
			logError(MonitorDePID.idioma.archivo_externo_no_valido() + ": " + rutaExterna);
			return;
		}

		File directorioTemporal = Files.createTempDirectory("crashdetector_mod_temp_").toFile();
		directorioTemporal.deleteOnExit();

		File destinoExtraido = new File(directorioTemporal, "contenido");
		extraerZip(archivoExterno, destinoExtraido);

		File archivoInterno = new File(destinoExtraido, rutaInterna);
		if (archivoInterno.exists()) {
			if (!archivoInterno.delete()) {
				logError(MonitorDePID.idioma.error_al_eliminar_jar_anidado() + ": " + rutaJar);
				limpiarTemporal(directorioTemporal);
				return;
			}
		} else {
			logError(MonitorDePID.idioma.archivo_interno_no_encontrado() + ": " + rutaJar);
			limpiarTemporal(directorioTemporal);
			return;
		}

		File archivoTemporal = new File(directorioTemporal, "temp.jar");
		empaquetarZip(destinoExtraido, archivoTemporal);

		if (archivoExterno.delete() && archivoTemporal.renameTo(archivoExterno)) {
			logExito(MonitorDePID.idioma.jar_anidado_eliminado() + ": " + rutaJar);
		} else {
			logError(MonitorDePID.idioma.error_al_reemplazar_jar_externo() + ": " + rutaExterna);
		}

		limpiarTemporal(directorioTemporal);
	}

	/**
	 * Limpia archivos temporales después de la operación
	 */
	private static void limpiarTemporal(File directorio) {
		try {
			Files.walk(Paths.get(directorio.getAbsolutePath())).sorted(java.util.Comparator.reverseOrder())
					.forEach(path -> {
						try {
							Files.delete(path);
						} catch (IOException e) {
							System.err.println("No se pudo eliminar: " + path);
						}
					});
		} catch (IOException e) {
			logError(MonitorDePID.idioma.error_al_limpiar_temporales());
			CrashDetectorLogger.logException(e);
		}
	}

	/**
	 * Verifica si el archivo es físico (no anidado en otro .jar)
	 */
	public static boolean esArchivoFisico(String ruta) {
		return ruta != null && !ruta.contains(".jar!") && !ruta.contains(".zip!");
	}

	/**
	 * Muestra un mensaje de éxito al usuario (solo en modo GUI)
	 */
	private static void logExito(String mensaje) {
		if (!esModoHeadless()) {
			mostrarMensajeExito(mensaje);
		} else {
			System.out.println("[Éxito] " + mensaje);
		}
	}

	/**
	 * Muestra un mensaje de error al usuario (solo en modo GUI)
	 */
	private static void logError(String mensaje) {
		if (!esModoHeadless()) {
			mostrarMensajeError(mensaje);
		} else {
			System.err.println("[Error] " + mensaje);
		}
	}

	/**
	 * Versión GUI: Muestra un mensaje de éxito
	 */
	private static void mostrarMensajeExito(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, MonitorDePID.idioma.exito(), JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Versión GUI: Muestra un mensaje de error
	 */
	private static void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje, MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Verifica si es modo headless
	 */
	public static boolean esModoHeadless() {
		return java.awt.GraphicsEnvironment.isHeadless();
	}
}