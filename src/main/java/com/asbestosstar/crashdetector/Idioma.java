package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.idioma.Arabe;
import com.asbestosstar.crashdetector.idioma.Chino;
import com.asbestosstar.crashdetector.idioma.Coreano;
import com.asbestosstar.crashdetector.idioma.Espanol;
import com.asbestosstar.crashdetector.idioma.Esperanto;
import com.asbestosstar.crashdetector.idioma.Frances;
import com.asbestosstar.crashdetector.idioma.Indonesia;
import com.asbestosstar.crashdetector.idioma.Ingles;
import com.asbestosstar.crashdetector.idioma.Japones;
import com.asbestosstar.crashdetector.idioma.Khmer;
import com.asbestosstar.crashdetector.idioma.Lao;
import com.asbestosstar.crashdetector.idioma.Malayo;
import com.asbestosstar.crashdetector.idioma.Persa;
import com.asbestosstar.crashdetector.idioma.Portuges;
import com.asbestosstar.crashdetector.idioma.Ruso;
import com.asbestosstar.crashdetector.idioma.Suajili;
import com.asbestosstar.crashdetector.idioma.Tailandes;
import com.asbestosstar.crashdetector.idioma.Ucraniano;
import com.asbestosstar.crashdetector.idioma.Vietnamita;

public interface Idioma {
	Config config = Config.obtenerInstancia();

	@Deprecated
	public static File archivo = new File(System.getProperty("user.home"), "crash_detector/idioma");

	public static ConfigString idioma_respaldo = ConfigString.de("idioma_respaldo", "es");

	/**
	 * Registro dinamico de idiomas.
	 *
	 * La clave es el codigo del idioma (es, en, pt, uk, etc). Si se vuelve a
	 * registrar el mismo codigo, el nuevo objeto sobrescribe al anterior. Esto
	 * permite que la API de extensiones reemplace traducciones completas o
	 * parcialmente mediante wrappers/subclases.
	 */
	public static final Map<String, Idioma> IDIOMAS_REGISTRADOS = new LinkedHashMap<>();

	/**
	 * Registra los idiomas base del programa.
	 *
	 * Se puede llamar varias veces; limpia y reconstruye el registro base. Despues,
	 * extensiones/modpacks pueden volver a registrar el mismo codigo para
	 * sobrescribirlo.
	 */
	public static void registrarIdiomasPredeterminados() {
		IDIOMAS_REGISTRADOS.clear();

		registrarIdioma(new Espanol());
		registrarIdioma(new Ingles());
		registrarIdioma(new Arabe());
		registrarIdioma(new Portuges());
		registrarIdioma(new Persa());
		registrarIdioma(new Ruso());
		registrarIdioma(new Chino());
		registrarIdioma(new Esperanto());
		registrarIdioma(new Japones());
		registrarIdioma(new Coreano());
		registrarIdioma(new Ucraniano());
		registrarIdioma(new Vietnamita());
		registrarIdioma(new Indonesia());
		registrarIdioma(new Malayo());
		registrarIdioma(new Khmer());
		registrarIdioma(new Tailandes());
		registrarIdioma(new Lao());
		registrarIdioma(new Frances());
		registrarIdioma(new Suajili());

	}

	/**
	 * Registra o sobrescribe un idioma por codigo.
	 */
	public static void registrarIdioma(Idioma idioma) {
		if (idioma == null) {
			return;
		}

		String codigo = normalizarCodigo(idioma.codigo());
		if (codigo == null) {
			return;
		}

		IDIOMAS_REGISTRADOS.put(codigo, idioma);
	}

	/**
	 * Registra varios idiomas.
	 */
	public static void registrarIdiomas(Iterable<? extends Idioma> idiomas) {
		if (idiomas == null) {
			return;
		}
		for (Idioma idioma : idiomas) {
			registrarIdioma(idioma);
		}
	}

	/**
	 * Devuelve una copia ordenada de los codigos registrados.
	 */
	public static Set<String> codigosRegistrados() {
		return new LinkedHashSet<>(IDIOMAS_REGISTRADOS.keySet());
	}

	/**
	 * Obtiene el idioma directamente desde el registro.
	 */
	public static Idioma desdeCodigo(String codigo) {
		String codigoNormalizado = normalizarCodigo(codigo);
		if (codigoNormalizado == null) {
			return idiomaPorDefecto();
		}

		Idioma idioma = IDIOMAS_REGISTRADOS.get(codigoNormalizado);
		return idioma != null ? idioma : idiomaPorDefecto();
	}

	/**
	 * Indica si el codigo existe en el registro actual.
	 */
	public static boolean tenemosIdiomaCodigo(String codigo) {
		String codigoNormalizado = normalizarCodigo(codigo);
		return codigoNormalizado != null && IDIOMAS_REGISTRADOS.containsKey(codigoNormalizado);
	}

	/**
	 * Nombre nativo del idioma desde su codigo.
	 */
	public static String nombreDeIdiomaDesdeCodigo(String codigo) {
		Idioma idioma = IDIOMAS_REGISTRADOS.get(normalizarCodigo(codigo));
		return idioma != null ? idioma.nombre_del_idioma() : idiomaPorDefecto().nombre_del_idioma();
	}

	/**
	 * Busca el codigo a partir del nombre visible del idioma.
	 *
	 * Primero intenta coincidencia exacta con el nombre nativo, y luego con el
	 * nombre en espanol ASCII.
	 */
	public static String codigoDesdeNombreVisible(String nombreIdioma) {
		if (nombreIdioma == null) {
			return "es";
		}

		for (Idioma idioma : IDIOMAS_REGISTRADOS.values()) {
			if (nombreIdioma.equals(idioma.nombre_del_idioma())) {
				return idioma.codigo();
			}
		}

		String nombreNormalizado = nombreIdioma.trim().toLowerCase(Locale.ROOT);
		for (Idioma idioma : IDIOMAS_REGISTRADOS.values()) {
			if (nombreNormalizado.equals(idioma.nombre_del_idioma_espanol_minusculas_ascii())) {
				return idioma.codigo();
			}
		}

		return "es";
	}

	/**
	 * Mapa para combos: etiqueta visible -> ruta relativa de bandera.
	 */
	public static LinkedHashMap<String, String> mapaParaComboBoxIdiomas() {
		LinkedHashMap<String, String> mapa = new LinkedHashMap<>();

		for (Idioma idioma : IDIOMAS_REGISTRADOS.values()) {
			Path bandera = idioma.imagen_bandera();
			String rutaRelativa = null;

			if (bandera != null) {
				try {
					Path carpetaBase = Statics.carpeta;
					rutaRelativa = carpetaBase.relativize(bandera).toString().replace('\\', '/');
				} catch (Exception e) {
					// Si algo falla, no rompemos la UI; simplemente dejamos el icono nulo.
					rutaRelativa = null;
				}
			}

			mapa.put(idioma.nombre_del_idioma(), rutaRelativa);
		}

		return mapa;
	}

	/**
	 * Detecta el idioma actual usando el registro dinamico.
	 *
	 * Orden: 1. Configuracion mundial 2. Archivo antiguo 3. Idioma del sistema 4.
	 * Idioma de respaldo 5. Espanol
	 */
	public static Idioma detectar() {
		asegurarIdiomasBaseRegistrados();

		// 1. Configuracion mundial
		String id = ConfigMundial.obtenerInstancia().obtenerIdioma();
		if (tenemosIdiomaCodigo(id)) {
			return desdeCodigo(id);
		}

		// 2. Archivo antiguo
		id = leerIdiomaDesdeArchivo();
		if (tenemosIdiomaCodigo(id)) {
			return desdeCodigo(id);
		}

		// 3. Idioma del sistema
		id = Locale.getDefault().getLanguage().toLowerCase(Locale.ROOT);
		if (tenemosIdiomaCodigo(id)) {
			return desdeCodigo(id);
		}

		// 4. Idioma de respaldo
		id = idioma_respaldo.obtener();
		if (tenemosIdiomaCodigo(id)) {
			return desdeCodigo(id);
		}

		// 5. Espanol como ultimo recurso
		return idiomaPorDefecto();
	}

	/**
	 * Fuerza recalculo del idioma efectivo del proceso actual.
	 */
	public static Idioma recalcularIdiomaActual() {
		return detectar();
	}

	/**
	 * Asegura que el registro base exista antes de usarlo.
	 */
	public static void asegurarIdiomasBaseRegistrados() {
		if (IDIOMAS_REGISTRADOS.isEmpty()) {
			registrarIdiomasPredeterminados();
		}
	}

	/**
	 * Idioma por defecto del sistema.
	 */
	public static Idioma idiomaPorDefecto() {
		Idioma idioma = IDIOMAS_REGISTRADOS.get("es");
		return idioma != null ? idioma : new Espanol();
	}

	/**
	 * Normaliza el codigo.
	 */
	public static String normalizarCodigo(String codigo) {
		if (codigo == null) {
			return null;
		}

		String normalizado = codigo.trim().toLowerCase(Locale.ROOT);
		return normalizado.isEmpty() ? null : normalizado;
	}

	public static String leerIdiomaDesdeArchivo() {
		if (!archivo.exists() || !archivo.canRead()) {
			return null;
		}

		try {
			String texto = leer_archivo(archivo.toPath());
			String codigo = normalizarCodigo(texto);

			if (tenemosIdiomaCodigo(codigo)) {
				// Guardar tambien en config mundial para futuras ejecuciones
				ConfigMundial.obtenerInstancia().guardarIdioma(codigo);
				return codigo;
			}

			return null;
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
			return null;
		}
	}

	/**
	 * Registro dinamico de idiomas.
	 *
	 * La clave es el codigo del idioma (es, en, pt, uk, etc). Si se vuelve a
	 * registrar el mismo codigo, el nuevo objeto sobrescribe al anterior. Esto
	 * permite que la API de extensiones reemplace traducciones completas o
	 * parcialmente mediante wrappers/subclases.
	 */

	public static String leer_archivo(Path path) throws IOException {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	/**
	 * Codigo de Idioma e.g. es en pt ar ko eo ja fa zh ru
	 * 
	 * @return
	 */
	public String codigo();

	/**
	 * Nombre del idioma en español, en minúsculas, sin acentos ni caracteres
	 * especiales. Ejemplo: "español" -> "espanol"
	 * 
	 * @return
	 */
	public String nombre_del_idioma_espanol_minusculas_ascii();

	/*
	 * Nombre del idioma en su propia forma nativa, con acentos y caracteres
	 * especiales. Ejemplo: "Ingles" -> "English"
	 */
	public String nombre_del_idioma();

	/**
	 * Ubicacion en disco para la bandera del idioma
	 * 
	 * @return
	 */
	public Path imagen_bandera();

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

	public String problema_con_graficas_nouveau();

	public String problema_con_graficas_general();

	public String fmlEarlyWindow();

	public String no_tienes_las_dependencias_necesarias();

	public String linea_de_dependencia(String linea);

	public String local_headless(String archivo);

	public String texto_de_gui();

	public String texto_de_boton_local_enlace();

	public String texto_debajo_de_buton_local_enlace();

	public String texto_de_buton_compartir_enlace();

	public String texto_debajo_de_buton_compartir_enlace();

	public String problematico_jar();

	public String nivel();

	public String posibilidad_fatal();

	public String modids_problematicos();

	public String packages_problematicos();

	public String falta_de_clases_fatales();

	public String corchetes_ondulados();

	public String config_spongemixin_problematico();

	public String module_resolution_exception();

	public String modlauncher_mods_duplicado(String linea);

	public String mcforge_mod_sospechoso();

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

	public String noResultados();

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

	public String nombre_de_contenido_de_stacktrace();

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

	public String inicioApp();

	public String ajustesCrashDetector();

	public String confidencialidad();

	public String tooltip();

	public String config();

	public String abrirCarpeta();

	public String actualizar();

	public String anadirRegistro();

	public String usarIdiomaDelSistema();

	public String volver();

	public String colorFondo();

	public String colorTexto();

	public String colorBoton();

	public String colorCajaTexto();

	public String colorEnlace();

	public String colorTitulosConsolas();

	public String colorError();

	public String colorAdvertencia();

	public String colorInfo();

	public String colorTitulo();

	public String colorEnlaceTexto();

	public String transformacionDeMinecraftCodigo0();

	public String activar_parche();

	public String noHaySolucionDisponible();

	public String error();

	public String error_al_eliminar_jar();

	public String jar_eliminado_exitosamente();

	public String exito();

	public String eliminar();

	public String error_al_eliminar_paquete();

	public String paquete_eliminado_exitosamente();

	public String eliminar_paquete();

	public String no_se_encontraron_mods_con_paquete();

	public String no_se_puede_eliminar_paquete();

	public String eliminar_jar();

	public String no_se_encontraron_mods_duplicados();

	public String archivo_no_encontrado();

	public String directorio_eliminado();

	public String error_al_eliminar_jar_anidado();

	public String archivo_interno_no_encontrado();

	public String archivo_eliminado();

	public String error_al_eliminar_archivo();

	public String archivo_externo_no_valido();

	public String elemento_mod_eliminado();

	public String error_al_reemplazar_jar_externo();

	public String error_al_eliminar_elemento_mod();

	public String error_al_eliminar_directorio();

	public String formato_invalido_para_jar_anidado();

	public String jar_anidado_eliminado();

	public String error_al_limpiar_temporales();

	public String mensaje_de_trace_fatal_ultima_no_traductado();

	public String mensaje_de_trace_ultima_no_traductado();

	public String solucionParaAdvertenciaFaltasClases();

	public String solucionFaltasClases();

	public String solucionParaJavaInstallar();

	public String error_animacion_no_encontrada();

	public String nombre_de_error_animacion_minecraft();

	public String no_se_encontraron_mods_para_eliminar();

	public String opcionesGCInvalidas();

	public String aumentarMemoriaHeap();

	public String aumentarMemoriaPermgen();

	public String utilizarJVM64Bits();

	public String optimizarCodigo();

	public String utilizarRecolectorBasuraEficiente();

	public String modulos();

	public String paquete();

	public String solucionRegistrosMalMapeados();

	public String nombre_de_registros_mal_mapeados();

	public String mensajeCierreAuthMe();

	public String nombreProblemaCierreAuthMe();

	public String solucionCierreAuthMe();

	public String solucionConfigurarPluginAuthMe();

	public String solucionInstalarVersionDiferenteAuthMe();

	public String solucionEliminarPluginAuthMe();

	public String mensajeProblemaCargaMultiverso(String nombreMundo);

	public String nombreProblemaCargaMultiverso();

	public String solucionRepararMundo(String nombreMundo);

	public String solucionEliminarCarpetaMundo(String nombreMundo);

	public String mensajeConfiguracionPermissionsEx();

	public String nombreProblemaConfiguracionPermissionsEx();

	public String solucionConfigurarPermissionsEx();

	public String solucionEliminarPluginPermissionsEx();

	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath);

	public String nombreProblemaNombrePluginAmbiguo();

	public String solucionEliminarPlugin(String segundoPath);

	public String mensajeCargaChunk();

	public String nombreProblemaCargaChunk();

	public String solucionEliminarChunk();

	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando);

	public String nombreProblemaExcepcionComandoPlugin();

	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin);

	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias);

	public String mensajeDependenciaPluginUnica(String nombrePlugin, String string);

	public String nombreProblemaDependenciaPlugin();

	public String solucionInstalarPlugin(String dependencia);

	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI);

	public String nombreProblemaVersionAPIIncompatible();

	public String solucionInstalarVersionServidor(String versionAPI);

	public String mensajeMundoDuplicado(String nombreMundo);

	public String nombreProblemaMundoDuplicado();

	public String solucionEliminarUID(String nombreMundo);

	public String solucionEliminarMundo(String nombreMundo);

	public String mensajeTickingEntidadBloque(String nombreEntidad, String tipoEntidad, int[] coordenadas);

	public String nombreProblemaTickingEntidadBloque();

	public String solucionEliminarEntidadBloque(String nombreEntidad, int[] coordenadas);

	public String mensajeModDuplicadoFabric(String nombreMod);

	public String nombreProblemaModDuplicadoFabric();

	public String solucionEliminarModDuplicado(String rutaMod);

	public String mensajeModIncompatible(String primerMod, String segundoMod);

	public String nombreProblemaModIncompatibleFabric();

	public String solucionEliminarMod(String primerMod);

	public String nombreProblemaModFatal();

	public String mensajeModFatal(String nombreMod);

	public String nombreProblemaDependenciaMod();

	public String solucionInstalarModConVersion(String dep, String ver);

	public String solucionInstalarMod(String dep);

	public String mensajeModDependenciaPlural(List<String> dependencias);

	public String mensajeDependenciaModFaltante(String string, String string2, String string3);

	public String mensajePluginTickingRegionalPlural(List<String> nombresPlugins);

	public String mensajePluginTickingRegionalSingular(String string);

	public String nombreProblemaPluginTickingRegional();

	public String solucionInstalarSoftwareSinTickingRegional(String string);

	public String mensajeModFaltanteEnDatapackPlural(List<String> nombresMods);

	public String mensajeModFaltanteEnDatapackSingular(String string);

	public String nombreProblemaModFaltanteEnDatapack();

	public String mensajeModExcepcionPlural(List<String> nombresMods);

	public String mensajeModExcepcionSingular(String string);

	public String nombreProblemaModExcepcion();

	public String solucionInstalarVersionDiferenteMod(String mod);

	public String mensajeModIncompatibleConMinecraftPlural(List<String> nombresMods, List<String> versionesMinecraft);

	public String mensajeModIncompatibleConMinecraftSingular(String string, String string2);

	public String nombreProblemaModIncompatibleConMinecraft();

	public String solucionInstalarVersionForge(String versionMC);

	public String mensajeDependenciaModFaltante(String nombreMod);

	public String nombreProblemaDependenciaModFaltante();

	public String mensajeWorldModFaltantePlural(List<String> nombresMods);

	public String mensajeWorldModFaltanteSingular(String string);

	public String nombreProblemaWorldModFaltante();

	public String nombreProblemaVersionModMundo();

	public String mensajeVersionModMundoPlural(List<String> nombresMods, List<String> versionesEsperadas,
			List<String> versionesActuales);

	public String mensajeVersionModMundoSingular(String string, String string2, String string3);

	public String mensajeVersionDowngrade();

	public String nombreProblemaVersionDowngrade();

	public String solucionVersionDowngrade();

	public String solucionGenerarNuevoMundo();

	public String nombreProblemaDependenciaPluginFaltante();

	public String mensajeDependenciaPluginFaltanteSingular(String string, String string2);

	public String mensajeDependenciaPluginFaltantePlural(List<String> nombresPlugins, List<String> dependencias);

	public String mensajePluginIncompatiblePlural(List<String> nombresPlugins);

	public String mensajePluginIncompatibleSingular(String string);

	public String nombreProblemaPluginIncompatible();

	public String mensajePluginEjecucionPlural(List<String> nombresPlugins);

	public String mensajePluginEjecucionSingular(String string);

	public String nombreProblemaPluginEjecucion();

	public String nombreLegacyRandomSourceMultiHilos();

	public String mensajeLegacyRandomSourceMultiHilos();

	public String desdeUltimoExito();

	public String noHayCambios();

	public String desdeUltimoIntento();

	public String fallo();

	public String diferentesDeLasMods();

	public String historialDeMods();

	public String archivo0();

	public String archivo1();

	public String comparar();

	public String seleccionarDosArchivos();

	public String archivoExito();

	public String archivoFalla();

	public String errorComparandoArchivos();

	public String comparando();

	public String con();

	public String descripcionPanelHistoriaMods();

	public String tieneErrorIPV6();

	public String parcheIPv4();

	public String carpetaHMCL();

	public String noRegistroLauncherTitulo();

	public String descripcionCurseforge();

	public String descripcionPrism();

	public String descripcionHMCL();

	public String descripcionFenix();

	public String descripcionATLauncher();

	public String descripcionGDLauncher();

	public String descripcionLinksMarkdown();

	public String imagenNoEncontrada();

	public String faltar_de_clases_create();

	public String faltar_de_clases_epicfight();

	public String openJ9NoSoportado();

	public String necesitasJDK11();

	public String memoriaExcesiva();

	public String recomendacionMemoriaExcesiva();

	public String disminuirMemoriaHeap();

	public String forgeArchivosFaltantes(String archivo2);

	public String forgeVersionNoEncontrada(String version, String archivo2);

	public String forgeTargetFmlclientNoEncontrado();

	public String forgeClaseMinecraftFaltante();

	public String forgeInstallacionNoCompleta();

	public String nombre_de_forge_instalacion_no_completa();

	public String solucion_para_forge_instalacion_no_completa();

	public String descargar_forge_oficial();

	public String reinstalar_forge_correctamente();

	public String instrucciones_reinstalar_forge();

	public String titulo_instrucciones_reinstaler_mcforge();

	public String error_enlace_insatisfecho(String nombreBiblioteca);

	public String nombre_de_error_enlace_insatisfecho();

	public String solucion_para_error_enlace_insatisfecho();

	public String conflicto_id_colision_especifico(String idConflictivo, String modOrigen, String modDestino);

	public String conflicto_id_maximo();

	public String nombre_de_conflicto_ids();

	public String instalar_justenoughids();

	public String instalar_endlessids();

	public String usar_idfix_minus();

	public String usar_minecraft_id_resolver();

	public String ver_documentacion_jp();

	public String solucion_maximo_rango();

	public String solucion_colision_id();

	public String escanearDeMCreator();

	/**
	 * Obtiene el título de la ventana del árbol de mods.
	 * 
	 * @return Título de la ventana
	 */
	public String tituloArbolDeMods();

	/**
	 * Obtiene el texto para la etiqueta de tipo de búsqueda.
	 * 
	 * @return Texto de la etiqueta
	 */
	String tipoBusqueda();

	/**
	 * Obtiene el texto para el filtro "Todos".
	 * 
	 * @return Texto del filtro
	 */
	String filtroTodos();

	/**
	 * Obtiene el texto para el filtro "Paquetes".
	 * 
	 * @return Texto del filtro
	 */
	String filtroPaquetes();

	/**
	 * Obtiene el texto para el filtro "Clases".
	 * 
	 * @return Texto del filtro
	 */
	String filtroClases();

	/**
	 * Obtiene el texto para el filtro "Métodos".
	 * 
	 * @return Texto del filtro
	 */
	String filtroMetodos();

	/**
	 * Obtiene el texto para el filtro "Campos".
	 * 
	 * @return Texto del filtro
	 */
	String filtroCampos();

	/**
	 * Obtiene el texto para el filtro "Referencias de Campo".
	 * 
	 * @return Texto del filtro
	 */
	String filtroReferenciasCampo();

	/**
	 * Obtiene el texto para el filtro "Referencias de Método".
	 * 
	 * @return Texto del filtro
	 */
	String filtroReferenciasMetodo();

	/**
	 * Obtiene el texto para el filtro "Referencias de Clase".
	 * 
	 * @return Texto del filtro
	 */
	String filtroReferenciasClase();

	/**
	 * Obtiene el texto para el tooltip del campo de búsqueda.
	 * 
	 * @return Texto del tooltip
	 */
	String tipBuscar();

	/**
	 * Obtiene el texto para el botón de búsqueda.
	 * 
	 * @return Texto del botón
	 */
	String botonBuscar();

	/**
	 * Obtiene el texto para el botón de resetear árbol.
	 * 
	 * @return Texto del botón
	 */
	String botonResetearArbol();

	/**
	 * Obtiene el texto para indicar los mods cargados.
	 * 
	 * @return Texto descriptivo
	 */
	String modsCargados();

	/**
	 * Obtiene el texto para la categoría de clases.
	 * 
	 * @return Texto de la categoría
	 */
	String clases();

	/**
	 * Obtiene el texto para la categoría de métodos.
	 * 
	 * @return Texto de la categoría
	 */
	String metodos();

	/**
	 * Obtiene el texto para la categoría de campos.
	 * 
	 * @return Texto de la categoría
	 */
	String campos();

	/**
	 * Obtiene el texto para la categoría de referencias.
	 * 
	 * @return Texto de la categoría
	 */
	String referencias();

	/**
	 * Obtiene el texto para los resultados de búsqueda.
	 * 
	 * @return Texto de resultados
	 */
	String resultadosBusqueda();

	/**
	 * Obtiene el texto para la opción de buscar referencias.
	 * 
	 * @return Texto de la opción
	 */
	String buscarReferencias();

	/**
	 * Obtiene el texto para las referencias de mod.
	 * 
	 * @return Texto descriptivo
	 */
	String referenciasMod();

	/**
	 * Obtiene el texto para las referencias de clase.
	 * 
	 * @return Texto descriptivo
	 */
	String referenciasClase();

	/**
	 * Obtiene el texto para las referencias de método.
	 * 
	 * @return Texto descriptivo
	 */
	String referenciasMetodo();

	/**
	 * Obtiene el texto para las referencias de campo.
	 * 
	 * @return Texto descriptivo
	 */
	String referenciasCampo();

	/**
	 * Obtiene el texto cuando no se encuentran referencias.
	 * 
	 * @return Mensaje de error
	 */
	String noSeEncontraronReferencias();

	/**
	 * Obtiene el texto para el detalle de mod.
	 * 
	 * @return Título de detalle
	 */
	String detalleMod();

	/**
	 * Obtiene el texto para la ubicación.
	 * 
	 * @return Etiqueta de ubicación
	 */
	String ubicacion();

	/**
	 * Obtiene el texto para los nombres.
	 * 
	 * @return Etiqueta de nombres
	 */
	String nombres();

	/**
	 * Obtiene el texto para el módulo.
	 * 
	 * @return Etiqueta de módulo
	 */
	String modulo();

	/**
	 * Obtiene el texto para el detalle de clase.
	 * 
	 * @return Título de detalle
	 */
	String detalleClase();

	/**
	 * Obtiene el texto para el detalle de método.
	 * 
	 * @return Título de detalle
	 */
	String detalleMetodo();

	/**
	 * Obtiene el texto para el detalle de campo.
	 * 
	 * @return Título de detalle
	 */
	String detalleCampo();

	/**
	 * Obtiene el texto para la clase.
	 * 
	 * @return Etiqueta de clase
	 */
	String clase();

	/**
	 * Obtiene el texto para el tipo.
	 * 
	 * @return Etiqueta de tipo
	 */
	String tipo();

	/**
	 * Obtiene el texto para las referencias a métodos.
	 * 
	 * @return Etiqueta de referencias
	 */
	String referenciasAMetodos();

	/**
	 * Obtiene el texto para las referencias a campos.
	 * 
	 * @return Etiqueta de referencias
	 */
	String referenciasACampos();

	/**
	 * Obtiene el texto para el botón de árbol de mods.
	 * 
	 * @return Texto del botón
	 */
	String arbolDeMods();

	/**
	 * Obtiene el texto para método.
	 * 
	 * @return Palabra "Método"
	 */
	String metodo();

	String campo();

	public String descompilar();

	public String exportar();

	public String importar();

	public String errorImportar();

	public String estructuraImportada();

	public String estructuraExportada();

	public String errorExportar();

	public String exportando();

	public String exportacionTardara();

	public String porFavorEspere();

	public String noTienesVLCBin();

	public String descargar_vlc();

	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida);

	public String nombre_de_error_caracteres_invalidos();

	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida);

	public String paso2_caracteres_invalidos(String nombreModulo);

	public String paso3_caracteres_invalidos();

	public String errorDependenciaModFaltante(String nombreJar);

	public String nombre_de_error_dependencia_mod_faltante();

	public String paso1_dependencia_mod_faltante(String nombreJar);

	public String paso2_dependencia_mod_faltante(String nombreJar);

	public String paso3_dependencia_mod_faltante();

	public String errorAccessTransformerInvalido(String nombreJar);

	public String nombre_de_error_access_transformer_invalido();

	public String paso1_access_transformer_invalido(String nombreJar);

	public String paso2_access_transformer_invalido(String nombreJar);

	public String paso3_access_transformer_invalido();

	public String errorDiscrepanciaModID(String nombreMod);

	public String nombre_de_error_discrepancia_mod_id();

	public String paso1_discrepancia_mod_id(String nombreMod);

	public String paso2_discrepancia_mod_id();

	public String paso3_discrepancia_mod_id();

	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido);

	public String nombre_de_error_mod_plataforma_incorrecta();

	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido);

	public String paso2_mod_plataforma_incorrecta(String entornoInvalido);

	public String paso3_mod_plataforma_incorrecta();

	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales);

	public String nombre_de_error_metadata_mods_toml_faltante();

	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales);

	public String paso2_metadata_mods_toml_faltante(String modIdFaltante);

	public String paso3_metadata_mods_toml_faltante(String modIdFaltante);

	public String errorSistemaSonido();

	public String nombre_de_error_sistema_sonido();

	public String paso1_sistema_sonido();

	public String paso2_sistema_sonido();

	public String paso3_sistema_sonido();

	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion);

	public String nombre_de_error_sin_listeners_en_clase();

	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion);

	public String paso2_sin_listeners_en_clase(String nombreClase);

	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion);

	public String errorUnionFileSystemCorrupto(String nombreArchivo);

	public String nombre_de_error_union_filesystem_corrupto();

	public String paso1_union_filesystem_corrupto(String nombreArchivo);

	public String paso2_union_filesystem_corrupto();

	public String paso3_union_filesystem_corrupto();

	public String habilitarProxySysOutSysErrMensaje();

	public String confirmacionTitulo();

	public String proxyHabilitadoMensaje();

	public String informacionTitulo();

	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente);

	public String nombre_de_error_azure_geckolib_inicializo_pronto();

	public String paso1_azure_geckolib_inicializo_pronto();

	public String paso2_azure_geckolib_inicializo_pronto();

	public String errorCompatibilidadC2ME();

	public String nombre_de_error_compatibilidad_c2me();

	public String paso1_compatibilidad_c2me();

	public String paso2_compatibilidad_c2me();

	public String paso3_compatibilidad_c2me();

	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId);

	public String nombre_de_error_jei_plugin_fallido();

	public String paso1_jei_plugin_fallido(String modId);

	public String paso2_jei_plugin_fallido(String modId);

	public String paso3_jei_plugin_fallido(String modId);

	public String tituloLectador();

	public String obtenerTituloLeyenda();

	public String obtenerErrorEnLeyenda();

	public String obtenerStacktraceEnLeyenda();

	public String obtenerErrorAlProcesarLinea();

	public String obtenerTituloError();

	public String obtenerNombreError();

	public String obtenerDescripcionError();

	public String obtenerSeleccionarConsola();

	public String obtenerErrorLecturaArchivo();

	public String obtenerNombreErrorPorDefecto();

	public String obtenerDescripcionErrorPorDefecto();

	public String obtenerEtiquetaBotonLectador();

	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion);

	public String nombre_de_error_registro_suscriptores_automaticos();

	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase);

	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase, List<String> modsUbicacion);

	public String paso3_registro_suscriptores_automaticos(String modId);

	public String paso4_registro_suscriptores_automaticos();

	public String limpiado();

	public String original();

	public String verEnConsola();

	public String advertencia();

	public String fatal();

	public String noRegistroDeBattly();

	public String noRegistroDeNightWorld();

	public String noRegistroDeMCServidor();

	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion);

	public String nombre_de_LexForgeMLTransformerEnNeoForge();

	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante);

	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion);

	public String paso3_LexForgeMLTransformerEnNeoForge();

	public String paso4_LexForgeMLTransformerEnNeoForge();

	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion);

	public String nombreDeWaterMediaXenonIncompatible();

	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId);

	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion);

	public String paso3WaterMediaXenonIncompatible();

	public String nombreDeTaczDeflaterCerrado();

	public String errorTaczDeflaterCerrado(List<String> modsUbicacion);

	public String pasoTaczDeflaterCerrado();

	public String errorFuncionesDeDensidadNoVinculadas(List<String> clavesFaltantes);

	public String nombreDeFuncionesDeDensidadNoVinculadas();

	public String pasoFuncionesDeDensidadNoVinculadas();

	public String nombreDeRailwaysCreate6Alfa();

	public String pasoRailwaysCreate6Alfa();

	public String errorRailwaysCreate6Alfa(String claveFaltante);

	public String errorConflictoMultiworldRendimiento();

	public String nombreDeConflictoMultiworldRendimiento();

	public String pasoConflictoMultiworldRendimiento();

	public String problema_con_graficas_sodium();

	public String errorContextoOpenGL();

	public String nombreErrorContextoOpenGL();

	public String paso1ErrorContextoOpenGL();

	public String copiadoAlPortapapeles();

	public String buscarDentroDeComprimidos();

	public String error_resolucion_textura(String recurso, String tamaño);

	public String nombre_de_error_resolucion_textura();

	public String solucion_resolucion_textura();

	public String error_modlauncher_path();

	public String nombre_error_modlauncher_path();

	public String solucion_modlauncher_path();

	public String tituloEditorCodice();

	public String nuevo();

	public String actualizarSeleccionado();

	public String eliminarSeleccionado();

	public String exportarJSON();

	public String guardarTodo();

	public String general();

	public String id();

	public String paraBuscar();

	public String filtro();

	public String criticalidad();

	public String prioridad();

	public String lista();

	public String colIdioma();

	public String colNombre();

	public String colResultado();

	public String vistaJson();

	public String idiomas();

	public String elegirFiltro();

	public String eligeFiltroMsg();

	public String eligeFiltroTitulo();

	public String faltanCampos();

	public String critInvalida();

	public String filtroNoExiste();

	public String faltanIdiomas();

	public String verificacionInvalida();

	public String guardadoOk();

	public String editorCodiceBoton();

	public String descripcionEditorCodice();

	public String guardarAntesDeSalir();

	public String salirSinGuardar();

	public String descartarCambios();

	public String confirmacion();

	public String nombre_error_configuracion_servicio();

	public String paso1_configuracion_servicio(List<String> modsUbicacion);

	public String paso2_configuracion_servicio();

	public String errorConfiguracionServicio(String clase, List<String> mods);

	public String errorCampoInexistente(String nombreCampo, String lineaCompleta);

	public String nombre_error_campo_inexistente();

	public String paso1_campo_inexistente();

	public String paso2_campo_inexistente();

	public String errorMetodoInexistente(String metodo, String lineaCompleta);

	public String nombre_error_metodo_inexistente();

	public String paso1_metodo_inexistente();

	public String paso2_metodo_inexistente();

	// Helper para escapar HTML en la linea completa
	default String escapeHtml(String s) {
		return s.replace("&", "&amp;").replace("<", "<").replace(">", ">").replace("\"", "&quot;");
	}

	public String mensajeAyudar();

	public String restablecerPlantilla();

	public String restablecer();

	public String restablecerImagenMensjae(String nombreImagen);

	public String restablecerPlantillaMensaje();

	public String faltar_de_clases_azurelib();

	public String errorHealightINT();

	public String solucionHealightINT();

	public String nombreErrorHealightINT();

	public String errorMetodoAbstractoNoImplementadoDetallado(String claseConcreta, String firmaMetodo, String interfaz,
			String origen);

	public String solucionMetodoAbstractoNoImplementado();

	public String nombreErrorMetodoAbstractoNoImplementado();

	public String errorMetadataAnimacionEnServidor();

	public String solucionErrorMetadataAnimacionEnServidor();

	public String nombreErrorMetadataAnimacionEnServidor();

	public String errorConfiguracionConnectorCorrupta();

	public String solucionConfiguracionConnectorCorrupta();

	public String nombreErrorConfiguracionConnectorCorrupta();

	public String errorJarCorruptoConNombre(String nombreJar);

	public String solucionJarCorrupto();

	public String nombreErrorJarCorruptoConNombre();

	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto);

	public String solucionErrorCargaNBTMundoCorrupto();

	public String nombreErrorCargaNBTMundoCorrupto();

	public String problema_con_openAL();

	public String errorArchivoBloqueadoPorOtroProceso();

	public String solucionErrorArchivoBloqueadoPorOtroProceso();

	public String nombreErrorArchivoBloqueadoPorOtroProceso();

	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal);

	public String solucionErrorClaseFinalExtendida();

	public String nombreErrorClaseFinalExtendida();

	public String errorRubidiumObsoletoConIris();

	public String solucionRubidiumObsoletoConIris();

	public String nombreErrorRubidiumObsoletoConIris();

	public String errorVoiceChatPuertoOcupado();

	public String solucionErrorVoiceChatPuertoOcupado();

	public String nombreErrorVoiceChatPuertoOcupado();

	public String errorBlockItemNuloCreate(String nombreBlockItem);

	public String solucionErrorBlockItemNuloCreate();

	public String nombreErrorBlockItemNuloCreate();

	public String modIncompatibleConCargadorActivo(List<String> modsIncompatibles);

	public String solucionModIncompatibleConCargadorActivo();

	public String nombreModIncompatibleConCargadorActivo();

	public String errorCreacionModeloFallida(String modid, String nombreModelo);

	public String solucionErrorCreacionModeloFallida();

	public String nombreErrorCreacionModeloFallida();

	public String solucionConflictoMoonlightIceberg();

	public String nombreConflictoMoonlightIceberg();

	public String conflictoMoonlightIceberg();

	public String instantanea();

	public Object desdeUltimaInstantanea();

	public String seleccionarUnArchivo();

	public String instantaneaCreadaCorrectamente();

	public String errorCreandoInstantanea();

	public String consejo();

	public String resultadoMuestra();

	public String historaDeModsDesc();

	public String texto_de_boton_compartir_markdown();

	public String error_inesperado_al_compartir();

	public String error_inesperado_al_generar_enlaces();

	public String texto_de_boton_compartir_enlace();

	public String columna_url();

	public String titulo_configuracion();

	public String sin_archivo_para_abrir();

	public String archivo_no_existe_prefijo();

	public String no_se_pudo_abrir_se_copia_ruta();

	public String escritorio_no_soportado_se_copia_ruta();

	public String no_se_pudo_editar_se_copia_ruta();

	public String error_inesperado_al_procesar_boton();

	public String limite_de_solicitudes();

	public String infoDeTrazos();

	public String buscador_canario_de_orden_label();

	public String buscador_canario_de_orden_mensaje_proximamente();

	public String buscador_canario_de_orden_titulo_proximamente();

	public String nombre_de_mods_incompatibles_crash_assistant();

	public String nombre_de_modpack_incompatible_crash_assistant();

	public String advertenciaCrashAssistantModpackIncompatibleFalso();

	public String advertenciaCrashAssistantModsIncompatibles();

	public String advertenciaMalwareFalso();

	public String errorDependenciaSimple(String modId, String dependencia, String versionActual);

	public String errorDependenciaNoInstalada(String modId, String dependencia, String versionRequerida);

	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual);

	public String fallo_ejecucion_tarea_descripcion(String clase);

	public String recomendacion_fallos_ejecucion();

	public String info_clase_problematica();

	public String nombre_fallos_ejecucion_tareas();

	public String no_se_encontraron_clases_problema();

	public String errorConflictoOptiFineEMF();

	public String nombreDeConflictoOptiFineEMF();

	public String pasoConflictoOptiFineEMF();

	public String errorConflictoOptiFineFusion();

	public String nombreDeConflictoOptiFineFusion();

	public String pasoConflictoOptiFineFusion();

	public String errorConflictoFlywheelSodium();

	public String nombreDeConflictoFlywheelSodium();

	public String pasoConflictoFlywheelSodium();

	public String errorConflictoOptiFineEpicFight();

	public String nombreDeConflictoOptiFineEpicFight();

	public String pasoConflictoOptiFineEpicFight();

	public String errorConflictoOptiFineRubidium();

	public String nombreDeConflictoOptiFineRubidium();

	public String pasoConflictoOptiFineRubidium();

	public String errorFreeCamServidor();

	public String nombreDeErrorFreeCamServidor();

	public String pasoErrorFreeCamServidor();

	public String errorEntityTextureFeaturesServidor();

	public String nombreDeErrorEntityTextureFeaturesServidor();

	public String pasoErrorEntityTextureFeaturesServidor();

	public String errorEULANoAceptado();

	public String nombreDeErrorEULANoAceptado();

	public String pasoErrorEULANoAceptado();

	public String errorOptiFineServidor();

	public String nombreDeErrorOptiFineServidor();

	public String pasoErrorOptiFineServidor();

	public String errorIronSpellbooksVersion();

	public String nombreDeErrorIronSpellbooksVersion();

	public String pasoErrorIronSpellbooksVersion();

	public String errorConflictoOptiFineEmbeddium();

	public String nombreDeConflictoOptiFineEmbeddium();

	public String pasoConflictoOptiFineEmbeddium();

	public String noPuedeAnalizarJSON();

	public String errorControllableServidor();

	public String nombreDeErrorControllableServidor();

	public String pasoErrorControllableServidor();

	public String errorSupplementariesCargaServidor();

	public String nombreDeErrorSupplementariesCargaServidor();

	public String pasoErrorSupplementariesCargaServidor();

	public String errorGroovyModloaderModuloFaltante();

	public String nombreDeErrorGroovyModloaderModuloFaltante();

	public String pasoErrorGroovyModloaderModuloFaltante();

	public String errorEveryCompatNombreInvalido();

	public String nombreDeErrorEveryCompatNombreInvalido();

	public String pasoErrorEveryCompatNombreInvalido();

	public String nombreDeErrorCodigo1073741819();

	public String pasoErrorCodigo1073741819();

	public String errorCodigo1073741819();

	public String pasoErrorImmersiveTooltipsSinDependencia();

	public String nombreDeErrorImmersiveTooltipsSinDependencia();

	public String errorImmersiveTooltipsSinDependencia();

	public String errorMedievalOriginsCast();

	public String nombreDeErrorMedievalOriginsCast();

	public String pasoErrorMedievalOriginsCast();

	public String errorReignOfNetherMusicManager();

	public String nombreDeErrorReignOfNetherMusicManager();

	public String pasoErrorReignOfNetherMusicManager();

	public String errorYesSteveModelLinux();

	public String nombreDeErrorYesSteveModelLinux();

	public String pasoErrorYesSteveModelLinux();

	public String errorConflictoMovingElevatorsOptiFine();

	public String nombreDeConflictoMovingElevatorsOptiFine();

	public String pasoConflictoMovingElevatorsOptiFine();

	public String errorConflictoFabricAPIOptiFine();

	public String nombreDeConflictoFabricAPIOptiFine();

	public String pasoConflictoFabricAPIOptiFine();

	public String errorModLauncherTransformationService(String claseProveedor);

	public String nombreDeErrorModLauncherTransformationService();

	public String pasoErrorModLauncherTransformationService(String claseProveedor);

	public String errorVersionInvalidaMod(String versionDetectada);

	public String nombreDeErrorVersionInvalidaMod();

	public String pasoErrorVersionInvalidaMod();

	public String errorStackSmashingDetected();

	public String nombreDeErrorStackSmashingDetected();

	public String pasoErrorStackSmashingDetected();

	public String errorVersionClaseGregTechEasyCore();

	public String nombreDeErrorVersionClaseGregTechEasyCore();

	public String pasoErrorVersionClaseGregTechEasyCore();

	public String errorConflictoMoniLabsConnectorExtras();

	public String nombreDeConflictoMoniLabsConnectorExtras();

	public String pasoConflictoMoniLabsConnectorExtras();

	public String pasoErrorCompatibilidadIrisDH();

	public String nombreDeErrorCompatibilidadIrisDH();

	public String errorCompatibilidadIrisDH();

	public String faltar_de_clases_minecraft();

	public String faltar_de_clases_dangerzone();

	public String faltar_de_clases_featurecreep();

	public String faltar_de_clases_modlauncher();

	public String faltar_de_clases_minecraftforge();

	public String faltar_de_clases_neoforged();

	public String faltar_de_clases_fabricloader();

	public String faltar_de_clases_pillowmc();

	public String uraniumLag();

	public String errorFallingAttackVersion();

	public String nombreDeErrorFallingAttackVersion();

	public String pasoErrorFallingAttackVersion();

	public String necesitasInstalarCfr();

	public String cfrNoHayRetrato();

	public String cfrClaseNoEncontrada(String nombreClase);

	public String tituloCfrSakura();

	public String cfrClaseActual();

	public String cfrRetratoDeSakura();

	public String cfrErrorCargarRetrato();

	public String noticiaLegalCFR();

	public String botonDescargarCfr();

	public String botonAbrirCarpetaCfr();

	public String colorFondoPrincipal();

	public String colorTextoBotonReset();

	public String colorTextoCampoBuscar();

	public String colorTextoComboFiltro();

	public String colorTextoRenderer();

	public String colorTextoOverlayCarga();

	public String colorBorde();

	public String colorFondoRetrato();

	public String colorEnlaceCompartir();

	public String colorFondoCampoCompartir();

	public String rosaFondo();

	public String rosaSuave();

	public String moradoAcento();

	public String textoOscuro();

	public String bordeSuave();

	public String fondoCampo();

	public String fondoVistaPrevia();

	public String sintaxisConstructor();

	public String sintaxisMensajeAyudar();

	public String sintaxisEtiquetasHtml();

	public String colorFondoVentana();

	public String colorPanel();

	public String colorBotonTexto();

	public String colorCampo();

	public String colorBordeDestacado();

	public String colorSeleccionTexto();

	public String colorTextoSeleccionado();

	public String colorEstadoExito();

	public String colorEstadoFallo();

	public String colorEstadoInstantanea();

	public String colorResultadoAnadido();

	public String colorResultadoEliminado();

	public String colorBordeScroll();

	public String colorFondoPanel();

	public String colorBeigeListas();

	public String colorTextoListas();

	public String colorBordeListas();

	public String colorBotonFondo();

	public String colorBordeBoton();

	public String colorDoradoTexto();

	public String colorPila();

	public String colorTextoPanel();

	public String colorTextoNegro();

	public String colorTextoPrincipal();

	public String colorEstado();

	public String colorFondoResultados();

	public String colorTextoDescripcion();

	public String colorTextoEstado();

	public String colorTextoExtra();

	public String colorSeparador();

	public String problema_safe_fetch32_jdk17();

	public String nombre_problema_safe_fetch32_jdk17();

	public String solucion_actualizar_jdk_macos();

	public String solucion_usar_lanzador_con_jdk_actualizado();

	public String solucion_desactivar_spark_mod();

	public String problema_mcef_inicializacion_html();

	public String nombre_problema_mcef_inicializacion();

	public String solucion_eliminar_mod_mcef();

	public String solucion_verificar_compatibilidad_mcef();

	public String conflicto_iris_optifine_html();

	public String nombre_conflicto_iris_optifine();

	public String solucion_eliminar_optifine();

	public String solucion_usar_iris_sin_optifine();

	public String conflicto_modernfix_optifine_html();

	public String nombre_conflicto_modernfix_optifine();

	public String solucion_eliminar_optifine_o_modernfix();

	public String solucion_usar_alternativa_modernfix();

	public String error_clave_registro_mayusculas_html(String clave);

	public String nombre_error_clave_registro_mayusculas();

	public String solucion_buscar_clave_en_archivos();

	public String solucion_eliminar_mod_reciente();

	public String error_entrypoint_fabric_html(String modNombre);

	public String nombre_error_entrypoint_fabric();

	public String solucion_eliminar_mod(String modNombre);

	public String solucion_actualizar_mod(String modNombre);

	public String error_en_garde_html();

	public String nombre_error_en_garde();

	public String solucion_actualizar_en_garde();

	public String solucion_eliminar_conflicto_mod_combate();

	public String error_idletweaks_html();

	public String nombre_error_idletweaks();

	public String solucion_actualizar_idletweaks();

	public String solucion_eliminar_idletweaks();

	public String mensagjePirataMC();

	public String infoDeDerechosMiranda();

	public String nombrePirataMC();

	public String desactivarVerificacionPirata();

	public String comprarMC();

	public String lanzer_no_animado_cambiar_a_animado();

	public String nombre_lanzer_no_animado();

	public String lanzer_no_animado_titulo(String lanzadorActual);

	public String lanzer_no_animado_problemas_comunes();

	public String lanzer_no_animado_usar_animados();

	public String lanzer_desanimado_titulo(String lanzadorActual);

	public String lanzer_desanimado_problemas_comunes();

	public String lanzer_desanimado_usar_animados();

	public String nombre_lanzer_desanimado();

	public String lanzer_desanimado_cambiar_lanzer();

	public String falta_mod_animado_titulo();

	public String nombre_falta_mod_animado();

	public String falta_mod_animado_instalar();

	public String tienes_mod_desanimado_titulo();

	public String nombre_tienes_mod_desanimado();

	public String tienes_mod_desanimado_eliminar();

	public String antimanipulacion_titulo();

	public String nombre_antimanipulacion();

	public String antimanipulacion_reinstalar();

	public String configuracionCorporativa();

	public String idiomaRespaldo();

	public String buscardorHabilitado();

	public String nombreHerramienta();

	public String condenarPirateria();

	public String lanzadoresRecomendados();

	public String lanzadoresDesaconsejados();

	public String modsRecomendados();

	public String modsDesaconsejados();

	public String errorCargandoImagen();

	public String proximamente();

	public String informacion();

	public String antiTamper();

	public String configuracionBasica();

	public String derechosMiranda();

	public String funcionalidades();

	public String gestionVerificaciones();

	public String idVerificacion();

	public String nombreVerificacion();

	public String codigoVerificacion();

	public String documentacionVerificacion();

	public String mensajeAmaneKanata();

	public String verificacionesHabilitadas();

	public String verificacionesDeshabilitadas();

	public String deshabilitarNoCorporativas();

	public String verCodigo();

	public String verDocumentacion();

	public String seleccionaVerificacionDeshabilitar();

	public String seleccionaVerificacionHabilitar();

	public String verificacionesNoCorporativasDeshabilitadas();

	public String operacionCompletada();

	public String noVerificacionesNoCorporativas();

	public String colorVerificacionCorporativa();

	public String nombreLanzador();

	public String motivo();

	public String moverADesaconsejados();

	public String moverARecomendados();

	public String guardarCambios();

	public String cancelar();

	public String lanzadoresNoRecomendados();

	public String seleccionaLanzadorMover();

	public String cambiosGuardadosExitosamente();

	public String motivoDesaconsejoPredeterminadoEn(String id);

	public String motivoDesaconsejoPredeterminadoEs(String id);

	public String motivoDesaconsejoPredeterminadoPt(String id);

	public String razones();

	public String agregarLanzador();

	public String quitarLanzador();

	public String editarRazones();

	public String seleccionaLanzadorQuitar();

	public String seleccionaLanzadorEditar();

	public String editarRazonesPara(String idLanzador);

	public String agregarNuevoIdioma();

	public String aceptar();

	public String seleccionaCodigoIdioma();

	public String lanzadoresRecomendadosAviso();

	public String colorResultadoCorrecto();

	public String modsNoRecomendados();

	public String modId();

	public String rutaMod();

	public String agregarMod();

	public String quitarMod();

	public String errorDebeIndicarMod();

	public String modsNoRecomendadosAviso();

	public String anularNormal();

	public String anularNormalDescripcion();

	public String modsRecomendadosAviso();

	public String descripcionDerechosPirateria();

	public String editar();

	public String rutaArchivo();

	public String errorRutaFueraDirectorio();

	public String advertenciaHashLento();

	public String agregarArchivo();

	public String agregarCarpeta();

	public String quitar();

	public String mensajeDeSylentBell();

	public String gmlIPV6();

	public String mensajeIndependenteFlywheel(Set<String> mods);

	public String nombreIndependenteFlywheel();

	public String mensajeFloralEnchantments();

	public String nombreFloralEnchantments();

	public String mixinExtrasDuplicados();

	public String mensajeIrisSombrasTerreno();

	public String nombreIrisSombrasTerreno();

	public String mensajeTickLargoServidor();

	public String nombreTickLargoServidor();

	public String tituloLFPDPPP();

	public String aceptarPermanentemente();

	public String actaProteccionIdiomaCultural();

	public String leerLeyCompleta();

	public String errorAbriendoEnlace();

	public String enlaceDocumentacionIdiomaCoreano();

	public String mensajeAdvertenciaIdiomaCoreano();

	public String canarioTitulo();

	public String canario1984Titulo();

	public String revisar();

	public String cerrar();

	public String canarioTodoSeguro();

	public String canarioComprometido(int inseguros);

	public String colorAlerta();

	public String consolaDesarrollo();

	public String mundial();

	public String ningun();

	public String opcionesMunidiales();

	public String consentimientoLFPDPPP();

	public String habilitarTokenAccesoEnEntregar();

	public String bajar();

	public String logsSoporte();

	public String detenerProceso();

	public String copiarSeleccion();

	public String seleccionarTodo();

	public String copiarTodo();

	public String guardarTodoComoArchivo();

	public String obtenerEnlaceSoporte();

	public String borrarTodo();

	public String consentimientoConfirmadoPendienteImplementacion();

	public String usarSakuraOriginal();

	public String canario1984Descripcion();

	public String gui();

	public String sinOpciones();

	public String confirmacionReEstablarTodos();

	public String seleccionaColor();

	public String botonRestablecerTodo();

	public String botonGuardarTodo();

	public String botonMostrarGUI();

	public String mensajeLuckPermsNoCargado();

	public String nombreLuckPermsNoCargado();

	public String mensajeIrisShaderpackNoEncontrado(String shaderpack);

	public String nombreIrisShaderpackNoEncontrado();

	public String mensajeNightConfigNoSePuedeEscribir(String rutaConfig);

	public String nombreNightConfigNoSePuedeEscribir();

	public String mensajeAccesoDenegadoBackupConfig(String archivoOrigen, String archivoBackup);

	public String nombreAccesoDenegadoBackupConfig();

	public String cdlauncherHabilitarConsola();

	public String cdlauncherDescripcionCompleta();

	public String mensajeSimpleCloudsIncompatibilidadShaders();

	public String nombreSimpleCloudsIncompatibilidadShaders();

	public String colorBotonBaraLateral();

	public String profilerTitulo();

	public String profilerIniciar();

	public String profilerDetener();

	public String profilerLimpiar();

	public String profilerDescripcion();

	public String profilerEstadoIniciado();

	public String profilerEstadoDetenido();

	public String profilerMuestraHilo(String hilo);

	// public String samplerTitulo();

	public String samplerDescripcion();

	public String entrarAlJuego();

	public String mensajeRutaCaracteresInvalidos();

	public String nombreRutaCaracteresInvalidos();

	public String mensajeTwilightForestIntelShaders();

	public String nombreTwilightForestIntelShaders();

	public String mensajeForgeLanguageProviderNoCarga(String providerFallido);

	public String nombreForgeLanguageProviderNoCarga();

	public String mensajeLetsDoCompatInterceptApply();

	public String nombreLetsDoCompatInterceptApply();

	public String mensajeJEIItemGroupCrash(Set<String> pluginsAfectados);

	public String nombreJEIItemGroupCrash();

	public String mensajeVersionInvalida(String versionInvalida, String ubicacionMod);

	public String nombreVersionInvalida();

	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino);

	public String nombreJPMSIllegalAccess();

	public String mensajeMixinClaseMalUbicada(String claseConflictiva, String paqueteMixin, String archivoMixin);

	public String nombreMixinClaseMalUbicada();

	public String problema_con_graficas_matrox();

	public String mensajeVulkanModNoEncuentraGPU();

	public String nombreVulkanModNoEncuentraGPU();

	public String mensajeRenderOutlineRendertypeInvalido(boolean modEnchantOutlineDetectado);

	public String nombreRenderOutlineRendertypeInvalido();

	public String mensajeDivineRPGDimensionalInventoryNPE();

	public String nombreDivineRPGDimensionalInventoryNPE();

	public String mensajeRenderPassNoCerrado();

	public String nombreRenderPassNoCerrado();

	public String mensajeProblemaFeatherClientSodium();

	public String nombreProblemaFeatherClient();

	public String mensajeConflictoIrisFlywheelCreate();

	public String nombreConflictoIrisFlywheelCreate();

	public String nombreModeloGeckoNoEncontrado();

	public String mensajeModeloGeckoNoEncontrado(String modelo);

	public String mensajeAnimacionCobblemon(String animacion, String grupo);

	public String nombreProblemaAnimacionCobblemon();

	public String mensajeProblemaLunarClient();

	public String nombreProblemaLunarClient();

	public String mensajeAccesoIlegalMod(String claseOrigen, String miembroAccedido);

	public String nombreAccesoIlegalMod();

	public String mensajeErrorParseoDataPack(String archivo2, String pack);

	public String nombreErrorParseoDataPack();

	public String mensajeErrorCompilacionShader();

	public String nombreErrorCompilacionShader();

	public String mensajeErrorCreacionModelo(String modelo);

	public String posibleConflictoCoolerAnimations();

	public String nombreErrorCreacionModelo();

	public String nombreProblemaStarlight();

	public String problemaBlockStarlightEngineDetectado();

	public String problemaAAAParticlesEffekseer();

	public String nombreProblemaAAAParticlesEffekseer();

	public String javaProblematica();

	public String problemaParanoiaC2ME();

	public String nombreProblemaParanoiaC2ME();

	public String problemaAssetsDirFaltante();

	public String nombreProblemaAssetsDirFaltante();

	public String dependenciaInstalar(String dependencia, String version);

	public String dependenciaReemplazarRango(String dependencia, String vMin, String vMax);

	public String dependenciaFaltanteMinima(String mod, String dependencia, String version);

	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version);

	public String dependenciaVersionIncorrecta(String mod, String dependencia, String vMin, String vMax, String actual);

	public String problemaCupboardVersion();

	public String nombreProblemaCupboardVersion();

	public String fmlEarlyWindowMacOSOpenGL();

	public String mensajeAnimacionGeckoNoEncontrada(String archivo2);

	public String nombreAnimacionGeckoNoEncontrada();

	public String mensajeAnimacionGeckoInexiste(String archivo2);

	public String nombreAnimacionGeckoInexiste();

	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto);

	public String nombreRegistroDuplicadoObjeto();

	public String mensajeFalloFabricRenderingAPI();

	public String nombreFalloFabricRenderingAPI();

	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos);

	public String nombreRestriccionesDependenciaNoCumplidas();

	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod);

	public String mensajeNeruinaOcultaAdvertencia();

	public String nombreNeruinaOcultaAdvertencia();

	public String mensajeApothicAttributeSinDueño(boolean chestCavityPresente);

	public String nombreApothicAttributeSinDueño();

	public String mensajeErrorPotBlockEntity();

	public String nombreErrorPotBlockEntity();

	public String mensajeErrorPreloadingTricks();

	public String nombreErrorPreloadingTricks();

	public String mensajeErrorSimpleRadioLexiconfig();

	public String nombreErrorSimpleRadioLexiconfig();

	public String nombreErrorMobAITweaks();

	public String mensajeErrorMobAITweaks();

	public String gpu_crash_posible();

	public String gpu_crash_causas();

	public String gpu_crash_recomendaciones();

	public String gpu_nota_precision();

	public String gpu_parche_info();

	public String gpu_no_optima();

	public String gpu_no_optima_detalles();

	public String gpu_consumo_energia();

	public String gpu_recomendaciones_rendimiento();

	public String nombre_verificacion_gpu();

	public String desactivar_parche_gpu();

	public String advertenciaRaptorLakeTitulo();

	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String string);

	public String nombreVerificacionRaptorLake();

	public String verArticuloRaptorLake(String string);

	public String desactivarVerificacionRaptorLake();

	public String tituloMixins();

	public String mixinsModConMixin();

	public String mixinsTooltipCombo();

	public String mixinsRecargar();

	public String mixinsDescompilarSeleccion();

	public String mixinsTodosLosMods();

	public String mixinsRaiz();

	public String mixinsTargets();

	public String mixinsMetodos();

	public String mixinsCampos();

	public String mixinsCantidad();

	public String mixinsDetalleMixin();

	public String mixinsDetalleTarget();

	public String mixinsTarget();

	public String mixinsConflictosPosibles();

	public String mixinsDetalleMetodo();

	public String mixinsTargetsMetodo();

	public String mixinsDetalleCampo();

	public String mixinsDetalleConflicto();

	public String mixinsBuscarConflictos();

	public String mixinsResultadosConflictos();

	public String mixinsErrorDescompilar();

	public String mixinsColorPanel();

	public String mixinsColorTexto();

	public String mixinsColorTextoSuave();

	public String mixinsBotonLateral();

	public String mixinsAyudaUso1();

	public String mixinsAyudaUso2();

	public String mixinsAyudaUso3();

	public String mixinsAyudaUso4();

	public String mixinsAyudaUso5();

	public String mixinsColorComboFondo();

	public String mixinsColorAreaContenidoFondo();

	public String mixinsColorSeleccionTexto();

	public String mixinsColorSeleccionTextoActivo();

	public String mixinsColorAyudaTexto();

	public String mixinsColorArbolFondo();

	public String mixinsColorRendererSeleccionTexto();

	public String mixinsColorRendererSeleccionFondo();

	public String mixinsColorRendererBordeSeleccion();

	public String depmapTitulo();

	public String depmapBotonLateral();

	public String depmapPestanaMapa();

	public String depmapPestanaDependientes();

	public String depmapRecargar();

	public String depmapDescompilarSeleccion();

	public String depmapVerReferencias();

	public String depmapDependencias();

	public String depmapDependientes();

	public String depmapDependiente();

	public String depmapSinDependencias();

	public String depmapSeleccionarMod();

	public String depmapSeleccionarModBase();

	public String depmapSeleccionarDependiente();

	public String depmapSeleccionarPaquete();

	public String depmapComprobarNoAlineadas();

	public String depmapResultadoNoAlineadas();

	public String depmapClaseInexistente();

	public String depmapClaseReferenciada();

	public String depmapOrigen();

	public String depmapDestino();

	public String depmapDependenciaDetalle();

	public String depmapReferenciaDetalle();

	public String depmapMetodoOrigen();

	public String depmapModBase();

	public String depmapTodos();

	public String depmapSeleccioneUnMod();

	public String depmapSeleccioneParametrosNoAlineadas();

	public String depmapSeleccioneClaseParaDescompilar();

	public String depmapErrorDescompilar();

	public String depmapAyuda1();

	public String depmapAyuda2();

	public String depmapAyuda3();

	public String depmapAyuda4();

	public String depmapAyuda5();

	public String depmapColorPanel();

	public String depmapColorTexto();

	public String depmapColorTextoSecundario();

	public String depmapColorAyudaTexto();

	public String depmapColorGrafoFondo();

	public String depmapColorListaFondo();

	public String depmapColorArbolFondo();

	public String depmapColorNodo();

	public String depmapColorEnlace();

	public String depmapColorSeleccion();

	public String depmapColorSeleccionTexto();

	public String mensajeProblemaAzureLibAnimaciones();

	public String nombreProblemaAzureLibAnimaciones();

	public String mensajeProblemaDecocraftNatureEssentialPartnerMod();

	public String nombreProblemaDecocraftNatureEssentialPartnerMod();

	public String mensajeTetraDeserializadorModeloEstatico();

	public String nombreTetraDeserializadorModeloEstatico();

	public String mensajeSimpleEmotesSetupAnimTail();

	public String nombreSimpleEmotesSetupAnimTail();

	public String nombreAdvertenciaSKLauncher();

	public String mensajeAdvertenciaSKLauncher();

	public String guardTitulo();

	public String guardBotonLateral();

	public String guardEscanearTodo();

	public String guardEscanearServidores();

	public String guardEscanearMalware();

	public String guardTablaServidores();

	public String guardTablaMalware();

	public String guardColServidor();

	public String guardColDefinicion();

	public String guardColMensaje();

	public String guardColUbicacion();

	public String guardColClase();

	public String guardColCfr();

	public String guardCfr();

	public String guardCfrTitulo();

	public String guardDescripcion1();

	public String guardDescripcion2();

	public String guardDescripcion3();

	public String guardDescripcion4();

	public String guardDescripcion5();

	public String guardEstadoEscaneandoTodo();

	public String guardEstadoEscaneandoServidores();

	public String guardEstadoEscaneandoMalware();

	public String guardEstadoListo();

	public String guardDefsNoEncontradasTitulo();

	public String guardDefsNoEncontradasMensaje();

	public String guardDefsDescargar();

	public String guardDefsCancelar();

	public String guardDefsActualizarTitulo();

	public String guardDefsActualizarMensaje();

	public String guardDefsUsarLocales();

	public String guardDefsActualizar();

	public String guardFuenteDefinicionesTLauncher();

	public String guardErrorDescompilar();

	public String guardColorPanel();

	public String guardColorTexto();

	public String guardColorTextoSecundario();

	public String guardColorTabla();

	public String guardColorSeleccion();

	public String guardColorSeleccionTexto();

	public String texto_de_boton_compartir_instancia_modpack();

	public String popup_compartir_instancia_modpack();

	public String colorBotonCompartirVerdeOscuro();

	public String colorBotonCompartirVerdeClaro();

	public String colorTextoBotonesCompartir();

	public String compartirInstanciaTitulo();

	public String compartirInstanciaBotonLateral();

	public String compartirInstanciaFormato();

	public String compartirInstanciaServicio();

	public String compartirInstanciaBotonCompartir();

	public String compartirInstanciaBotonRefrescar();

	public String compartirInstanciaEstadoListo();

	public String compartirInstanciaEstadoEmpaquetando();

	public String compartirInstanciaEstadoSubiendo();

	public String compartirInstanciaEstadoError();

	public String compartirInstanciaCodigo();

	public String compartirInstanciaEnlace();

	public String compartirInstanciaMantenerAbierto();

	public String compartirInstanciaSinSeleccion();

	public String compartirInstanciaFormatoNoSoportado();

	public String compartirInstanciaServicioNoDisponible();

	public String compartirInstanciaSubidaCompleta();

	public String compartirInstanciaErrorSubir();

	public String compartirInstanciaColorPanel();

	public String compartirInstanciaColorTexto();

	public String compartirInstanciaPolitica1();

	public String compartirInstanciaPolitica2();

	public String compartirInstanciaPolitica3();

	public String compartirInstanciaPolitica4();

	public String compartirInstanciaPolitica5();

	public String compartirInstanciaPolitica6();

	public String compartirInstanciaPolitica7();

	public String malware_fracturiser_detectado();

	public String malware_evidencia_clase_sospechosa();

	public String malware_evidencia_paquete_sospechoso();

	public String malware_evidencia_archivo_sospechoso();

	public String malware_brightsdk_detectado();

	public String malware_info_stealer_detectado();

	public String docsTituloVentana();

	public String docsCargando();

	public String docsAyudaDeUso();

	public String docsNoHayDocumentos();

	public String docsNoHayMarkdown();

	public String docsDocumentoNoEncontrado();

	public String docsErrorAlAbrirDocumento();

	public String docsImagenNoDisponible();

	public String colorPanelSecundario();

	public String colorTextoSuave();

	public String colorSeleccion();

	public String colorFondoDocumento();

	public String docsArbolTitulo();

	public String docsVisorTitulo();

	public String iaTituloPrincipal();

	public String iaAbrirEnlace();

	public String iaCopiarEnlace();

	public String iaAvisoCuentaBaidu();

	public String iaDescripcionTitulo();

	public String iaDescripcionCuerpo();

	public String iaDescripcionUso();

	public String iaTituloVentana();

	public String iaImagenNoDisponible();

	public String mensajeOculusIrisUnknownShaderVariable();

	public String nombreOculusIrisUnknownShaderVariable();

	public String mensajeItemNoExiste(String itemFaltante, String namespace);

	public String nombreItemNoExiste();

	public String mensajeCobblemonPinkanIslandsRhyhornModelo();

	public String nombreCobblemonPinkanIslandsRhyhornModelo();

	public String mensajeColdSweatInitDynamicTags();

	public String nombreColdSweatInitDynamicTags();

	public String mensajeClassCastExceptionGeneral(String lineaClassCast);

	public String nombreClassCastExceptionGeneral();

	public String mensajeValkyrienSkiesTournamentLithiumPoiInjection();

	public String nombreValkyrienSkiesTournamentLithiumPoiInjection();

	public String mensajeVSTournamentVSConfigClassNoExiste();

	public String nombreVSTournamentVSConfigClassNoExiste();

	public String curseForgeClaveApiMundial();

	public String curseForgeEndpoint();

	public String tlmodsEndpoint();

	public String minecraftStorageEndpoint();

	public String autoBackupActivado();

	public String autoBackupFrecuencia();

	public String autoBackupDiasConservar();

	public String autoBackupTamanoMaximoMB();

	public String actualizadorModsTitulo();

	public String actualizadorModsBotonSidebar();

	public String actualizadorModsDescripcion();

	public String actualizadorModsBotonEscanear();

	public String actualizadorModsBotonActualizarTodo();

	public String actualizadorModsBotonActualizarUno();

	public String actualizadorModsEstadoListo();

	public String actualizadorModsEstadoEscaneando();

	public String actualizadorModsEstadoActualizando();

	public String actualizadorModsEstadoSinActualizaciones();

	public String actualizadorModsEstadoEncontradas(int n);

	public String actualizadorModsEstadoActualizadas(int n);

	public String actualizadorModsEstadoError();

	public String actualizadorModsSinSeleccion();

	public String actualizadorModsColumnaMod();

	public String actualizadorModsColumnaActual();

	public String actualizadorModsColumnaNueva();

	public String actualizadorModsColumnaFuente();

	public String actualizadorModsColumnaLoader();

	public String actualizadorModsColumnaRuta();

	public String actualizadorModsColumnaAccion();

	public String actualizadorModsColorFondo();

	public String actualizadorModsColorPanel();

	public String actualizadorModsColorTexto();

	public String actualizadorModsColorTextoSuave();

	public String actualizadorModsColorBoton();

	public String actualizadorModsColorBotonTexto();

	public String actualizadorModsColorTabla();

	public String actualizadorModsColorSeleccion();

	public String importadorYumeiriTeExtraniamos();

	public String importadorColorFondo();

	public String importadorColorPanel();

	public String importadorColorTexto();

	public String importadorColorTextoSuave();

	public String importadorColorBoton();

	public String importadorColorBotonTexto();

	public String importadorColorBorde();

	public String importadorConflictoTitulo();

	public String importadorConflictoMensaje();

	public String importadorRuta();

	public String importadorArchivoExistente();

	public String importadorArchivoNuevo();

	public String importadorTamano();

	public String importadorFecha();

	public String importadorInfoMod();

	public String importadorModImportadoMasNuevo();

	public String importadorModImportadoMasViejo();

	public String importadorBotonReemplazar();

	public String importadorBotonSaltar();

	public String importadorBotonRenombrar();

	public String importadorModpackTitulo();

	public String importadorModpackBotonSidebar();

	public String importadorModpackDescripcion();

	public String importadorModpackFormato();

	public String importadorModpackArrastraArchivo();

	public String importadorModpackBotonSeleccionar();

	public String importadorModpackBotonImportar();

	public String importadorModpackSeleccionarArchivo();

	public String importadorModpackEstadoListo();

	public String importadorModpackEstadoImportando();

	public String importadorModpackEstadoError();

	public String importadorModpackSinArchivo();

	public String importadorModpackFormatoNoSoportado();

	public String importadorModpackResultado(int copiados, int reemplazados, int saltados, int renombrados,
			int errores);

	public String importadorModpackColorFondo();

	public String importadorModpackColorPanel();

	public String importadorModpackColorTexto();

	public String importadorModpackColorTextoSuave();

	public String importadorModpackColorBoton();

	public String importadorModpackColorBotonTexto();

	public String importadorModpackColorDrop();

	public String importadorModpackColorBorde();

	public String jgitTituloIzzy();

	public String jgitRetratoIzzy();

	public String jgitNoHayRetratoIzzy();

	public String jgitSeccionInstalacion();

	public String jgitAbrirCarpetaInstalacion();

	public String jgitAbrirPaginaDescarga();

	public String jgitSeccionRepositorio();

	public String jgitCrearRepositorioLocal();

	public String jgitCommitManual();

	public String jgitSeccionRemote();

	public String jgitForgeManual();

	public String jgitForgePersonalizado();

	public String jgitEstablecerRemoteManual();

	public String jgitCrearRemoteConAPI();

	public String jgitPushManual();

	public String jgitSeccionAutomaticos();

	public String jgitAutoCommitDespuesBackup();

	public String jgitAutoPushDespuesCommit();

	public String jgitSeccionHerramientas();

	public String jgitAbrirGuiSwing();

	public String jgitEstado();

	public String jgitClasspath();

	public String jgitTodosLosArtefactos();

	public String jgitRepositorio();

	public String jgitRemote();

	public String jgitCarpetaActual();

	public String jgitNoSePudoCrearRepo();

	public String jgitEscribaRemote();

	public String jgitNoSePudoGuardarRemote();

	public String jgitApiForgeAunNoImplementada();

	public String jgitNoHayCambiosOError();

	public String jgitNoSePudoPush();

	public String jgitTituloVentanaSwing();

	public String jgitNoHayRepositorio();

	public String jgitArchivosModificados();

	public String jgitArchivosNuevos();

	public String jgitUltimosCommits();

	public String jgitError();

	public String si();

	public String no();

	public String jgitDescargarDependenciasFaltantes();

	public String jgitNoFaltanDependencias();

	public String jgitConfirmarDescargaDependencias(int size);

	public String jgitDependenciasDescargadas(int totalDescargadas);

	public String jgitErroresDescarga();

	public String jgitReiniciarParaCargarClasspath();

	public String jgitArtefactosFaltantes();

	public String jgitArtefactosFaltantesClasspath();

	public String jgitDependenciasEnCarpeta();

	public String jgitArtefactosFaltantesCarpeta();

	public String jgitForgeNoSeleccionada();

	public String jgitForgeNoRegistrada(String id);

	public String jgitEscribaUrlForge();

	public String jgitEscribaNombreRepositorio();

	public String jgitEscribaDescripcionRepositorio();

	public String jgitEscribaNamespaceOpcional();

	public String jgitEscribaTokenForge();

	public String jgitErrorCrearRemote();

	public String mensajeControlifyRemoveReloadingScreen();

	public String nombreControlifyRemoveReloadingScreen();

	public String mensajeBiomesOPlentyFogDataLiquidosPersonalizados();

	public String nombreBiomesOPlentyFogDataLiquidosPersonalizados();

	public String mensajeKotlinReflectionInternalErrorVersion();

	public String nombreKotlinReflectionInternalErrorVersion();

	public String tituloEscanerMCreator();

	public String escanerMCreatorEscaneandoMods();

	public String escanerMCreatorPorFavorEspera();

	public String escanerMCreatorResultadosAnalisis();

	public String escanerMCreatorNoSeEncontraronMods();

	public String escanerMCreatorEscaneoCompletado();

	public String escanerMCreatorErrorDuranteEscaneo();

	public String escanerMCreatorCargando();

	public String escanerMCreatorCompletado();

	public String escanerMCreatorError();

	public String textoNormal();

	public String noSeEncontroConsolaParaArchivo();

	public String lineaSeleccionadaEnLectador();

	public String mensajeMotionBlurBufferCerrado();

	public String nombreMotionBlurBufferCerrado();

	public String mensajeGeneratorAcceleratorOwoVersion();

	public String nombreGeneratorAcceleratorOwoVersion();

	public String mensajeFabricRenderingApiFaltaIndium();

	public String nombreFabricRenderingApiFaltaIndium();

	public String mensajeEntradaDuplicadaIdModerno();

	public String nombreEntradaDuplicadaIdModerno();

	public String mensajeOpenGLMemoriaInsuficiente();

	public String nombreOpenGLMemoriaInsuficiente();

}
