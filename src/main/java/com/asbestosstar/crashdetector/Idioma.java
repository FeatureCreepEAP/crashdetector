package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import com.asbestosstar.crashdetector.idioma.Arabe;
import com.asbestosstar.crashdetector.idioma.Chino;
import com.asbestosstar.crashdetector.idioma.Coreano;
import com.asbestosstar.crashdetector.idioma.Espanol;
import com.asbestosstar.crashdetector.idioma.Esperanto;
import com.asbestosstar.crashdetector.idioma.Ingles;
import com.asbestosstar.crashdetector.idioma.Japones;
import com.asbestosstar.crashdetector.idioma.Persa;
import com.asbestosstar.crashdetector.idioma.Portuges;
import com.asbestosstar.crashdetector.idioma.Ruso;

public interface Idioma {

	Config config = Config.obtenerInstancia();
	public static File archivo = new File(System.getProperty("user.home"), "crash_detector/idioma");

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
		String id = leerIdiomaDesdeArchivo(); // Primero intenta leer del archivo

		if (id == null) {
			id = Locale.getDefault().getLanguage().toLowerCase();
		}

		switch (id) {
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
		case "uk":
			return ruso; // Ucraniano usa configuración rusa
		default:
			return espanol;
		}
	}

	public static String leerIdiomaDesdeArchivo() {
		if (!archivo.exists() || !archivo.canRead()) {
			return null;
		}
		try {
			String texto = MonitorDePID.leer_archivo(archivo.toPath());

			String codigo = texto.trim().toLowerCase();
			Set<String> soportados = new HashSet<>(
					Arrays.asList("es", "en", "ar", "pt", "fa", "ru", "zh", "eo", "ja", "ko"));

			return soportados.contains(codigo) ? codigo : null;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			CrashDetectorLogger.logException(e);
			return null;
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

	public String config_spongemixin_problematico();

	public String module_resolution_exception(String modules, String paquete);

	public String modlauncher_mods_duplicado(String linea);

	public String mcforge_mod_suspechoso();

	public String lithostichctov();

	public String necesitasSodiumParaIris();

	public String kubeJSResourcePack(String mod_nombre);

	public default String obtenerRutaJava() {
		String javaBinary = MonitorDePID.jvm();
		// return javaBinary.orElse("No se pudo determinar la ubicación del ejecutable
		// de Java.");
		return javaBinary;
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

	public String registroDemasiadoGrande();

	public String errorConPublicarRegistro(String error);

	public String apiDeRegistroNoExiste();

	public String errorSSL();

	public String errorJavaFMLVersion(String requerido, String encontrado);

	public String errorProveedorVersion(String jar, String prov, String req, String encontrado2);

	public String errorJavaFML_MCForge();

	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada);

	public String waterMediaTL();

	public String optifineObsoleta();

	public String servicioMLNoPudoCargar(String servicio);

	/**
	 * Genera un mensaje de error para problemas de análisis de archivos JSON de
	 * registros.
	 *
	 * @param archivoJar El nombre del archivo JAR que contiene el recurso
	 *                   problemático.
	 * @param recurso    El recurso problemático que no se pudo analizar
	 *                   correctamente.
	 * @return Un mensaje de error formateado en HTML con detalles sobre el
	 *         problema.
	 */
	public String errorConJSONDeRegistro(String archivoJar, String recurso);

	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual);

	public String gpu_no_compatible();

	public String recomendacionMemoria();

	public String error32BitMemoria();

	public String permGenError();

	public String errorCompatibilidadJava8();

	public String errorJava9NoSoportado();

	public String errorJava8Requerido();

	public String errorDeBloqueTeselado();

	public String errorMonitorLWJGL();

	public String errorOpcionesGCJava();

	public String errorConfigMCForge();

	public String problema_con_graficas_intel();

	public String nombre_de_faltar_de_clases_advertencia();

	public String nombre_de_bloque_teselado();

	public String nombre_de_contento_de_stacktrace();

	public String nombre_de_cursed_consola();

	public String nombre_de_early_window();

	public String nombre_de_drivers();

	public String nombre_de_error_de_config_mcforge();

	public String nombre_de_error_de_monitor_lwjgl();

	public String nombre_de_fabricmc_runtime_error_provided_by();

	public String nombre_de_falta_module_jmps();

	public String nombre_de_faltar_de_clases();

	public String nombre_de_faltas_dependencias_de_modlauncher();

	public String nombre_de_java_versiones();

	public String nombre_de_faltar_de_kubejs_resourcepack();

	public String nombre_de_lenguaje_proveedor_check();

	public String nombre_de_faltar_de_liyhostictchctov();

	public String nombre_de_malware_falso_crash_assistant();

	public String nombre_de_mcforge_mod_sespechoso();

	public String nombre_de_mods_duplicados_modlauncher();

	public String nombre_de_modules_duplicados_jmps();

	public String nombre_de_necesitas_sodium();

	public String nombre_de_no_puede_analizar_json_de_registro();

	public String nombre_de_no_tiene_memoria();

	public String nombre_de_null_pointer();

	public String nombre_de_opciones_java_gc_invalidas();

	public String nombre_de_optifine_obsoleta();

	public String nombre_de_60_segundo_trick();

	public String nombre_de_servicio_de_modlauncher_no_funciona();

	public String nombre_de_spongemixin_configs_problematicos();

	public String nombre_de_theseus();

	public String nombre_de_watermedia_tl();

	public String auditorias_transformer();

	public String auditorias_transformer_detectadas();

	public String descripcionEscanerMCreator();

	public String escanear();

	public String cargando();

	public String codigo();

	// public String advertenciaMalwareFalso();

}
