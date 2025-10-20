package com.asbestosstar.crashdetector.parches;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Set;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;

public class ConfigDeParches {
	private static final Path RUTA_ARCHIVO_CONFIG_PARCHES = MonitorDePID.carpeta.resolve("parches.properties");
	public static File archivoConfigParches = RUTA_ARCHIVO_CONFIG_PARCHES.toFile();

	private Properties propiedadesConfig;
	private static ConfigDeParches instancia;

	/**
	 * Constructor privado para cargar o crear el archivo de configuración.
	 * Inicializa todas las propiedades de parches faltantes con sus valores
	 * predeterminados.
	 */
	private ConfigDeParches() {
		propiedadesConfig = new Properties();

		// Cargar configuración existente si el archivo existe
		if (archivoConfigParches.exists()) {
			try (FileReader lector = new FileReader(archivoConfigParches)) {
				propiedadesConfig.load(lector);
			} catch (IOException e) {
				System.err.println("Error al leer el archivo de configuración de parches: " + e.getMessage());
			}
		}

		// Asegurar que todos los parches tengan su propiedad configurada
		inicializarParchesFaltantes();
		guardar(); // Guardar configuración actualizada
	}

	/**
	 * Devuelve la instancia única de la clase ConfigDeParches (patrón Singleton)
	 */
	public static synchronized ConfigDeParches obtenerInstancia() {
		if (instancia == null) {
			instancia = new ConfigDeParches();
		}
		return instancia;
	}

	/**
	 * Inicializa las propiedades faltantes usando los IDs y valores predeterminados
	 * de los parches
	 */
	private void inicializarParchesFaltantes() {
		for (Parche<?> parche : Parche.parches) {
			String id = parche.id();
			if (!propiedadesConfig.containsKey(id)) {
				propiedadesConfig.setProperty(id, String.valueOf(parche.predeterminado()));
			}
		}
	}

	/**
	 * Guarda la configuración actual en el archivo
	 */
	public void guardar() {
		archivoConfigParches.getParentFile().mkdirs();
		try (FileWriter escritor = new FileWriter(archivoConfigParches)) {
			propiedadesConfig.store(escritor, "Configuración de parches - Activación por ID");
		} catch (IOException e) {
			CrashDetectorLogger.log("Error al guardar el archivo de configuración de parches: " + e.getMessage());
		}
	}

	/**
	 * Obtiene el estado de activación de un parche por su ID
	 */
	public boolean obtenerActivacion(String id) {
		String valor = propiedadesConfig.getProperty(id);
		return valor != null ? Boolean.parseBoolean(valor) : false;
	}

	/**
	 * Guarda el estado de activación de un parche por su ID
	 */
	public void guardarActivacion(String id, boolean activo) {
		propiedadesConfig.setProperty(id, String.valueOf(activo));
		guardar();
	}

	/**
	 * Devuelve todos los IDs de parches disponibles en la configuración
	 */
	public Set<String> obtenerIDsParches() {
		return propiedadesConfig.stringPropertyNames();
	}

	/**
	 * Devuelve el estado de activación de un parche por su ID
	 * 
	 * @param id El ID del parche
	 * @return true si está activo, false en caso contrario
	 */
	public boolean estaActivo(String id) {
		return Boolean.parseBoolean(propiedadesConfig.getProperty(id, "false"));
	}

	/**
	 * Activa o desactiva un parche por su ID
	 * 
	 * @param id     El ID del parche
	 * @param activo true para activar, false para desactivar
	 */
	public void establecerActivo(String id, boolean activo) {
		propiedadesConfig.setProperty(id, String.valueOf(activo));
		guardar();
	}

	/**
	 * Asegura que todos los parches tengan su propiedad configurada
	 */
	public void asegurarParches() {
		boolean cambios = false;
		for (Parche<?> parche : Parche.parches) {
			String id = parche.id();
			if (!propiedadesConfig.containsKey(id)) {
				propiedadesConfig.setProperty(id, String.valueOf(parche.predeterminado()));
				cambios = true;
			}
		}
		if (cambios) {
			guardar();
		}
	}

	/**
	 * Devuelve todas las propiedades de configuración
	 */
	public Properties obtenerPropiedades() {
		return propiedadesConfig;
	}
}