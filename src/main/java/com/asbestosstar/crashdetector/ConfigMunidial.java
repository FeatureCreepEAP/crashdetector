package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuración munidial (global) de CrashDetector. Aplica a todas las
 * instancias del programa.
 */
public class ConfigMunidial {

	private static final boolean VALOR_POR_DEFECTO_TOKEN_DE_ACCESO = false;

	private static final boolean VALOR_POR_DEFECTO_ANONIMIZAR_REGISTROS = true;
	private static final boolean VALOR_POR_DEFECTO_CONSENTIMIENTO_LFPDPPP = false;
	private static final boolean VALOR_POR_DEFECTO_CONSOLA_DESARROLLO = false;

	private static final File ARCHIVO_CONFIG_MUNIDIAL = new File(System.getProperty("user.home"),
			"crash_detector/config_munidial.properties");

	private static ConfigMunidial instancia;

	private final Properties propiedades;

	private ConfigMunidial() {
		propiedades = new Properties();

		if (ARCHIVO_CONFIG_MUNIDIAL.exists()) {
			try (FileReader lector = new FileReader(ARCHIVO_CONFIG_MUNIDIAL)) {
				propiedades.load(lector);
			} catch (IOException e) {
				System.err.println("Error al leer la configuración munidial: " + e.getMessage());
			}
		} else {
			propiedades.setProperty("habilitar_token_de_acceso_en_la_entrega_del_MonitorDePID",
					String.valueOf(VALOR_POR_DEFECTO_TOKEN_DE_ACCESO));

			propiedades.setProperty("anonimizar_registros", String.valueOf(VALOR_POR_DEFECTO_ANONIMIZAR_REGISTROS));

			propiedades.setProperty("consentimiento_lfpdppp", String.valueOf(VALOR_POR_DEFECTO_CONSENTIMIENTO_LFPDPPP));

			propiedades.setProperty("consola_desarrollo", String.valueOf(VALOR_POR_DEFECTO_CONSOLA_DESARROLLO));

			guardar();
		}
	}

	public static synchronized ConfigMunidial obtenerInstancia() {
		if (instancia == null) {
			instancia = new ConfigMunidial();
		}
		return instancia;
	}

	public void guardar() {
		ARCHIVO_CONFIG_MUNIDIAL.getParentFile().mkdirs();
		try (FileWriter escritor = new FileWriter(ARCHIVO_CONFIG_MUNIDIAL)) {
			propiedades.store(escritor, "Configuración munidial de CrashDetector");
		} catch (IOException e) {
			System.err.println("Error al guardar la configuración munidial: " + e.getMessage());
		}
	}

	/**
	 * No es recomendado , solo existe para CDLauncher si necesitas servidores en
	 * modio enlinea pero activado es menos seguro. TODO mover a una config solo
	 * para usarios fin
	 * 
	 * @return
	 */
	public boolean obtenerHabilitarTokenDeAccesoEnLaEntregaDelMonitorDePID() {
		return Boolean.parseBoolean(propiedades.getProperty("habilitar_token_de_acceso_en_la_entrega_del_MonitorDePID",
				String.valueOf(VALOR_POR_DEFECTO_TOKEN_DE_ACCESO)));
	}

	/**
	 * No es recomendado , solo existe para CDLauncher si necesitas servidores en
	 * modio enlinea pero activado es menos seguro. TODO mover a una config solo
	 * para usarios fin
	 * 
	 * @return
	 */
	public void guardarHabilitarTokenDeAccesoEnLaEntregaDelMonitorDePID(boolean valor) {
		propiedades.setProperty("habilitar_token_de_acceso_en_la_entrega_del_MonitorDePID", String.valueOf(valor));
		guardar();
	}

	/**
	 * Devuelve el idioma configurado. Puede devolver null si no está definido.
	 */
	public String obtenerIdioma() {
		String idioma = propiedades.getProperty("idioma");
		return idioma != null ? idioma.trim().toLowerCase() : null;
	}

	public void guardarIdioma(String codigo) {
		if (codigo == null) {
			propiedades.remove("idioma");
		} else {
			propiedades.setProperty("idioma", codigo.toLowerCase());
		}
		guardar();
	}

	/**
	 * Elimina el idioma configurado (vuelve a detección automática).
	 */
	public void borrarIdioma() {
		propiedades.remove("idioma");
		guardar();
	}

	/**
	 * Indica si los registros deben anonimizarse.
	 */
	public boolean esAnonimizarRegistros() {
		return Boolean.parseBoolean(propiedades.getProperty("anonimizar_registros",
				String.valueOf(VALOR_POR_DEFECTO_ANONIMIZAR_REGISTROS)));
	}

	/**
	 * Guarda el estado de anonimización de registros.
	 */
	public void guardarAnonimizarRegistros(boolean valor) {
		propiedades.setProperty("anonimizar_registros", String.valueOf(valor));
		guardar();
	}

	/**
	 * Devuelve si el usuario ha dado consentimiento LFPDPPP.
	 */
	public boolean obtenerConsentimientoLFPDPPP() {
		return Boolean.parseBoolean(propiedades.getProperty("consentimiento_lfpdppp",
				String.valueOf(VALOR_POR_DEFECTO_CONSENTIMIENTO_LFPDPPP)));
	}

	/**
	 * Guarda el consentimiento LFPDPPP.
	 */
	public void guardarConsentimientoLFPDPPP(boolean valor) {
		propiedades.setProperty("consentimiento_lfpdppp", String.valueOf(valor));
		guardar();
	}

	/**
	 * Indica si la consola de desarrollo está habilitada. Por defecto es false.
	 */
	public boolean obtenerConsolaDesarrollo() {
		return Boolean.parseBoolean(
				propiedades.getProperty("consola_desarrollo", String.valueOf(VALOR_POR_DEFECTO_CONSOLA_DESARROLLO)));
	}

	/**
	 * Guarda el estado de la consola de desarrollo.
	 */
	public void guardarConsolaDesarrollo(boolean valor) {
		propiedades.setProperty("consola_desarrollo", String.valueOf(valor));
		guardar();
	}

}
