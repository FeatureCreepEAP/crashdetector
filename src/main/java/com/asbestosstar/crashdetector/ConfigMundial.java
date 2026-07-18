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
public class ConfigMundial {

	private static final boolean VALOR_POR_DEFECTO_TOKEN_DE_ACCESO = false;

	private static final boolean VALOR_POR_DEFECTO_ANONIMIZAR_REGISTROS = true;
	private static final boolean VALOR_POR_DEFECTO_CONSENTIMIENTO_LFPDPPP = false;
	private static final boolean VALOR_POR_DEFECTO_CONSOLA_DESARROLLO = false;
	private static final String VALOR_POR_DEFECTO_CURSEFORGE_CLAVE_API = "";
	private static final String CLAVE_VIRUSTOTAL_API = "virustotal_clave_api";
	private static final String CLAVE_METADEFENDER_API = "metadefender_clave_api";
	private static final String VALOR_POR_DEFECTO_VIRUSTOTAL_CLAVE_API = "";
	private static final String VALOR_POR_DEFECTO_METADEFENDER_CLAVE_API = "";
	private static final String CLAVE_RUTA_EJECUTABLE_JAVA_8 = "ruta_ejecutable_java_8";
	private static final String VALOR_POR_DEFECTO_RUTA_EJECUTABLE_JAVA_8 = "";

	private static final File ARCHIVO_CONFIG_MUNIDIAL = new File(Statics.carpeta_mundial_como_archivo,
			"config_munidial.properties");

	private static ConfigMundial instancia;

	private final Properties propiedades;

	private ConfigMundial() {
		propiedades = new Properties();

		if (ARCHIVO_CONFIG_MUNIDIAL.exists()) {
			try (FileReader lector = new FileReader(ARCHIVO_CONFIG_MUNIDIAL)) {
				propiedades.load(lector);
			} catch (IOException e) {
				System.err.println("Error al leer la configuración mundial: " + e.getMessage());
			}
		} else {
			propiedades.setProperty("habilitar_token_de_acceso_en_la_entrega_del_MonitorDePID",
					String.valueOf(VALOR_POR_DEFECTO_TOKEN_DE_ACCESO));

			propiedades.setProperty("anonimizar_registros", String.valueOf(VALOR_POR_DEFECTO_ANONIMIZAR_REGISTROS));

			propiedades.setProperty("consentimiento_lfpdppp", String.valueOf(VALOR_POR_DEFECTO_CONSENTIMIENTO_LFPDPPP));

			propiedades.setProperty("consola_desarrollo", String.valueOf(VALOR_POR_DEFECTO_CONSOLA_DESARROLLO));
			propiedades.setProperty("curseforge_clave_api", VALOR_POR_DEFECTO_CURSEFORGE_CLAVE_API);
			propiedades.setProperty(CLAVE_VIRUSTOTAL_API, VALOR_POR_DEFECTO_VIRUSTOTAL_CLAVE_API);
			propiedades.setProperty(CLAVE_METADEFENDER_API, VALOR_POR_DEFECTO_METADEFENDER_CLAVE_API);
			propiedades.setProperty(CLAVE_RUTA_EJECUTABLE_JAVA_8, VALOR_POR_DEFECTO_RUTA_EJECUTABLE_JAVA_8);
			guardar();
		}
	}

	public static synchronized ConfigMundial obtenerInstancia() {
		if (instancia == null) {
			instancia = new ConfigMundial();
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

	/**
	 * Devuelve la clave API de CurseForge configurada. Puede devolver una cadena
	 * vacía si no está definida.
	 */
	public String obtenerCurseForgeClaveApi() {
		return propiedades.getProperty("curseforge_clave_api", VALOR_POR_DEFECTO_CURSEFORGE_CLAVE_API).trim();
	}

	/**
	 * Guarda la clave API de CurseForge.
	 */
	public void guardarCurseForgeClaveApi(String claveApi) {
		if (claveApi == null || claveApi.trim().isEmpty()) {
			propiedades.remove("curseforge_clave_api");
		} else {
			propiedades.setProperty("curseforge_clave_api", claveApi.trim());
		}
		guardar();
	}

	/**
	 * Elimina la clave API de CurseForge.
	 */
	public void borrarCurseForgeClaveApi() {
		propiedades.remove("curseforge_clave_api");
		guardar();
	}

	/**
	 * Devuelve la clave API global de VirusTotal.
	 */
	public String obtenerVirusTotalClaveApi() {
		return propiedades.getProperty(CLAVE_VIRUSTOTAL_API, VALOR_POR_DEFECTO_VIRUSTOTAL_CLAVE_API).trim();
	}

	/**
	 * Guarda o elimina la clave API global de VirusTotal.
	 */
	public void guardarVirusTotalClaveApi(String claveApi) {
		if (claveApi == null || claveApi.trim().isEmpty()) {
			propiedades.remove(CLAVE_VIRUSTOTAL_API);
		} else {
			propiedades.setProperty(CLAVE_VIRUSTOTAL_API, claveApi.trim());
		}
		guardar();
	}

	public void borrarVirusTotalClaveApi() {
		propiedades.remove(CLAVE_VIRUSTOTAL_API);
		guardar();
	}

	/**
	 * Devuelve la clave API global de MetaDefender.
	 */
	public String obtenerMetaDefenderClaveApi() {
		return propiedades.getProperty(CLAVE_METADEFENDER_API, VALOR_POR_DEFECTO_METADEFENDER_CLAVE_API).trim();
	}

	/**
	 * Guarda o elimina la clave API global de MetaDefender.
	 */
	public void guardarMetaDefenderClaveApi(String claveApi) {
		if (claveApi == null || claveApi.trim().isEmpty()) {
			propiedades.remove(CLAVE_METADEFENDER_API);
		} else {
			propiedades.setProperty(CLAVE_METADEFENDER_API, claveApi.trim());
		}
		guardar();
	}

	public void borrarMetaDefenderClaveApi() {
		propiedades.remove(CLAVE_METADEFENDER_API);
		guardar();
	}

	/**
	 * Devuelve la ruta guardada del ejecutable java.exe de Java 8.
	 *
	 * Puede devolver cadena vacía si todavía no se ha encontrado.
	 */
	public String obtenerRutaEjecutableJava8() {
		return propiedades.getProperty(CLAVE_RUTA_EJECUTABLE_JAVA_8, VALOR_POR_DEFECTO_RUTA_EJECUTABLE_JAVA_8).trim();
	}

	/**
	 * Guarda la ruta del ejecutable java.exe de Java 8.
	 *
	 * Si recibe null, guarda blanco. Esto mantiene la entrada en la config mundial.
	 */
	public void guardarRutaEjecutableJava8(String ruta) {
		if (ruta == null) {
			propiedades.setProperty(CLAVE_RUTA_EJECUTABLE_JAVA_8, VALOR_POR_DEFECTO_RUTA_EJECUTABLE_JAVA_8);
		} else {
			propiedades.setProperty(CLAVE_RUTA_EJECUTABLE_JAVA_8, ruta.trim());
		}

		guardar();
	}

}