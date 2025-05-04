package com.asbestosstar.crashdetectormc;

import java.util.Locale;
import java.util.Optional;

import javax.swing.Icon;

import com.asbestosstar.crashdetectormc.idioma.Arabe;
import com.asbestosstar.crashdetectormc.idioma.Chino;
import com.asbestosstar.crashdetectormc.idioma.Coreano;
import com.asbestosstar.crashdetectormc.idioma.Espanol;
import com.asbestosstar.crashdetectormc.idioma.Esperanto;
import com.asbestosstar.crashdetectormc.idioma.Ingles;
import com.asbestosstar.crashdetectormc.idioma.Japones;
import com.asbestosstar.crashdetectormc.idioma.Persa;
import com.asbestosstar.crashdetectormc.idioma.Portuges;
import com.asbestosstar.crashdetectormc.idioma.Ruso;

public interface Idioma {
	
    Config config = Config.obtenerInstancia();


	public static Idioma espanol = new Espanol();
	public static Idioma ingles = new Ingles();
	public static Idioma arabe = new Arabe();
	public static Idioma portuges = new Portuges();
	public static Idioma persa = new Persa();
	public static Idioma ruso = new Ruso();
	public static Idioma chino = new Chino();
	public static Idioma esperanto = new Esperanto();
	public static Idioma japones = new Japones();
	public static Idioma coreano = new Coreano();

	public static Idioma detectar() {
		String id = Locale.getDefault().getLanguage();
		switch (id.toLowerCase()) {
		case "es":
			return espanol;
		case "en":
			return ingles;
		case "ar":
			return arabe;
		case "pt":
			return portuges;
		case "fa":
			return persa;
		case "ru":
			return ruso;
		case "zh":
			return chino;
		case "eo":
			return esperanto;
		case "ja":
			return japones;
		case "ko":
			return coreano;
		default:
			return espanol;
		}
	}

	/**
	 * no es una carpeta de mods valida
	 * 
	 * @return
	 */
	public String carpeta_de_mods_no_valido();

	/**
	 * No se donde esta el archivo JAR de CrashDetector
	 * 
	 * @return
	 */
	public String no_se_donde_esta_jar();

	/**
	 * "Buscando para PID: " + pid
	 * 
	 * @param pid
	 * @return
	 */
	public String buscando_para_pid(long pid);

	/**
	 * "(PID: " + pid + ") esta muerto!"
	 * 
	 * @param pid
	 * @return
	 */
	public String pid_esta_muerto(long pid);

	/**
	 * No tenemos JVM
	 * 
	 * @return
	 */
	public String no_tenemos_jvm();

	public String problema_con_graficas_ati();

	public String probelma_con_graficas_nouveau();

	public String probelma_con_graficas_general();

	public String fmlEarlyWindow();

	public String no_tienes_las_dependencias_necesitas();

	public String linea_de_dependencia(String linea);

	public String local_headless(String archivo);

	public String texto_de_gui();

	public String texto_de_buton_local_enlance();

	public String texto_debajo_de_buton_local_enlance();

	public String texto_de_buton_compartir_enlance();

	public String texto_debajo_de_buton_compartir_enlance();

	public String problematico_jar();

	public String nivel();

	public String possibladad_fatal();

	public String modids_problematicos();

	public String packages_problematicos();

	public String faltar_de_clases_fatales();

	public String corchetes_ondulados();

	public String config_spongemixin_problematico(String archivo_json);

	public String module_resolution_exception(String modules, String paquete);
	
	public String modlauncher_mods_duplicado(String linea);
	
	public String  mcforge_mod_suspechoso();

	public String lithostichctov();
	
	public String necesitasSodiumParaIris();
	
	public String kubeJSResourcePack(String mod_nombre);

	public default String obtenerRutaJava() {
	    Optional<String> javaBinary = ProcessHandle.current().info().command();
	    return javaBinary.orElse("No se pudo determinar la ubicación del ejecutable de Java.");
	}
	
	
	public String problema_con_graficas_nvidia_windows_viejo();
	
	public String problema_con_graficas_nvidia_windows_nuevo();

	public String segundo60Tick();

	public String noTieneMemoria();
	
	public String theseus();
	
	
	public String noTieneConsolaDeLauncherCursedForge();
	
	public String faltar_de_clases_advertencia();

	public String noResultos();

	public String ubicacionesDeLogs();

	public String infoDeVerificaciones();
	
	public String versionDeJava();

	public String java22();

	public String javaObsoleta();

	public String jpms_modules_faltas(String mod_necesitas, String submod);

	public String null_pointer_error(String metodo, String objeto);

	public String analisisAvanzado();

	public String seleccionarCarpeta();

	public String cadenaBusqueda();

	public String usarRegex();

	public String ignorarMayusculas();

	public String buscar();

	public String busquedaEnProgreso();

	public String noSeEncontraronResultados();

	public String errorBusqueda();

	public String noRegistroDeLauncher();
	
	public String omitirYCerrar();
	
	public String guardarYCerrar();

	public String pegaLosRegistrosAqui();

	public String archivo();

	public String incluir();

	public String abrir();
	
	public String endpointDeInforme();
	
	public String sitoDeLogging();

	public String apiDeLogging();

	public String anonimizarRegistros();

	public String botonDeCompartirInforme();
	
	public String arco();

	public String enlaceDelReporte();

	public String guardarConfigDeCompartir();
	
}
