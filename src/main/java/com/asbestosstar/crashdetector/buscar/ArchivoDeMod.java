package com.asbestosstar.crashdetector.buscar;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.anon.AnonimizadorDeRuta;
import com.asbestosstar.crashdetector.cargador.Cargador;

/**
 * Interfaz que representa un archivo de mod (JAR, ZIP, directorio) y sus
 * contenidos. Proporciona métodos para explorar y analizar mods sin depender
 * directamente de ASM o Javassist.
 * 
 * <p>
 * Para análisis de mixins, delega toda la lógica de parsing a
 * {@link ParserMixerLogger}.
 * </p>
 */
public interface ArchivoDeMod {

	public static ArchivoDeMod origin = new Origin();

	/**
	 * Obtiene la fuente original de este archivo de mod.
	 * 
	 * @return Referencia al origen del mod
	 */
	public ArchivoDeMod obtenerDesde();

	/**
	 * Obtiene la lista de mods embebidos dentro de este mod.
	 * 
	 * @return Lista de mods contenidos
	 */
	public List<ArchivoDeMod> mods_en_mods();

	/**
	 * Obtiene los nombres del mod en diferentes formatos.
	 * 
	 * @return Lista de nombres del mod
	 */
	public List<String> nombre();

	/**
	 * La version de la mod
	 * 
	 * @return
	 */
	public String version();

	/**
	 * Obtiene la ubicación física del archivo de mod.
	 * 
	 * @return Ruta del archivo
	 */
	public String ubicacion();

	/**
	 * Obtiene la ubicación anonimizada para publicar en informes.
	 * 
	 * @return Ruta anonimizada
	 */
	public default String ubicacion_para_publicar() {
		return AnonimizadorDeRuta.anonimizarNombreDeUsuario(this.ubicacion());
	}

	/**
	 * Obtiene la lista de clases contenidas en el mod.
	 * 
	 * @return Lista de nombres de clases
	 */
	public List<String> clases();

	/**
	 * Verifica si existe un nombre específico de forma recursiva.
	 * 
	 * @param nombre Nombre a buscar
	 * @return true si existe, false en caso contrario
	 */
	public boolean tieneNombreRecursivo(String nombre);

	/**
	 * Obtiene el contenido de un nombre específico de forma recursiva.
	 * 
	 * @param nombre Nombre a buscar
	 * @return Contenido del nombre o null si no existe
	 */
	public String obtenerNombreRecursivo(String nombre);

	/**
	 * Verifica si existe un archivo específico de forma recursiva.
	 * 
	 * @param archivo Archivo a buscar
	 * @return true si existe, false en caso contrario
	 */
	public boolean tieneArchivoRecursivo(String archivo);

	/**
	 * Obtiene el contenido de un archivo específico de forma recursiva.
	 * 
	 * @param archivo Archivo a buscar
	 * @return Contenido del archivo o null si no existe
	 */
	public String obtenerArchivoRecursivo(String archivo);

	/**
	 * Obtiene la lista de todos los archivos contenidos en el mod.
	 * 
	 * @return Lista de nombres de archivos
	 */
	public List<String> archivos();

	/**
	 * Busca recursivamente mods que contengan el archivo, clase o paquete
	 * especificado.
	 * 
	 * @param termino Término a buscar (archivo, clase o paquete)
	 * @return Lista de mods que contienen el elemento buscado
	 */
	public List<ArchivoDeMod> buscarModsCon(String termino);

	// VERIFICACIÓN DE BIBLIOTECAS DE BYTECODE
	public static final boolean ASM_DISPONIBLE = verificarClaseEnClasspath("org.objectweb.asm.ClassReader");
	public static final boolean JAVASSIST_DISPONIBLE = verificarClaseEnClasspath("javassist.bytecode.ClassFile");

	/**
	 * Verifica si una clase está disponible en el classpath.
	 * 
	 * @param nombreClase Nombre completo de la clase a verificar
	 * @return true si la clase existe, false en caso contrario
	 */
	public static boolean verificarClaseEnClasspath(String nombreClase) {
		try {
			Class.forName(nombreClase);
			CrashDetectorLogger.log("tiene clase " + nombreClase);
			return true;
		} catch (ClassNotFoundException e) {
			CrashDetectorLogger.log("NO tiene clase " + nombreClase);
			return false;
		}
	}

	/**
	 * Verifica si alguna biblioteca de análisis de bytecode está disponible.
	 * 
	 * @return true si ASM o Javassist están disponibles, false en caso contrario
	 */
	public static boolean esAnalisisDeBytecodeDisponible() {
		return ASM_DISPONIBLE || JAVASSIST_DISPONIBLE;
	}

	// ========================================================================
	// MÉTODO PRINCIPAL: CARGAR CACHE DESDE MIXERLOGGER (DELEGADO A PARSER)
	// ========================================================================

	/**
	 * Busca y carga el cache desde el log de MixerLogger en las consolas.
	 * 
	 * <p>
	 * Este método delega toda la lógica de parsing a {@link ParserMixerLogger}.
	 * </p>
	 * 
	 * @return true si se encontró y procesó un log, false si no hay log disponible
	 */
	public default boolean cargarCacheDesdeMixerLogger() {
		return ParserMixerLogger.cargarDesdeConsolas();
	}

	// ========================================================================
	// MÉTODOS DE ANÁLISIS DE BYTECODE (CON CONSULTA A CACHE)
	// ========================================================================

	/**
	 * Verifica si una clase existe en el mod.
	 * 
	 * @param nombreClase Nombre completo de la clase (ej: "java/lang/Object")
	 * @return true si la clase existe, false en caso contrario
	 */
	boolean existeClase(String nombreClase);

	/**
	 * Obtiene información de métodos de una clase incluyendo referencias internas.
	 * 
	 * <p>
	 * Primero consulta el cache de ParserMixerLogger si está cargado.
	 * </p>
	 * 
	 * @param nombreClase Nombre completo de la clase
	 * @return Lista con información de métodos y sus referencias
	 */
	default List<InfoMetodo> obtenerMetodosConReferencias(String nombreClase) {
		// Primero intentar obtener desde cache de ParserMixerLogger
		String nombrePunto = nombreClase.replace('/', '.');
		List<InfoMetodo> cacheados = ParserMixerLogger.obtenerMetodosDesdeCache(nombrePunto);
		if (!cacheados.isEmpty()) {
			return cacheados;
		}

		// Fallback a análisis de bytecode
		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.analizarMetodos(this, nombreClase);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.analizarMetodos(this, nombreClase);
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Obtiene campos declarados en una clase.
	 * 
	 * <p>
	 * Primero consulta el cache de ParserMixerLogger si está cargado.
	 * </p>
	 * 
	 * @param nombreClase Nombre completo de la clase
	 * @return Lista con información de campos
	 */
	default List<InfoCampo> obtenerCampos(String nombreClase) {
		String nombrePunto = nombreClase.replace('/', '.');
		List<InfoCampo> cacheados = ParserMixerLogger.obtenerCamposDesdeCache(nombrePunto);
		if (!cacheados.isEmpty()) {
			return cacheados;
		}

		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.analizarCampos(this, nombreClase);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.analizarCampos(this, nombreClase);
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Busca todas las referencias dentro de un método específico.
	 * 
	 * <p>
	 * Primero consulta el cache de ParserMixerLogger si está cargado.
	 * </p>
	 * 
	 * @param nombreClase  Nombre completo de la clase
	 * @param nombreMetodo Nombre del método
	 * @param descriptor   Descriptor del método (ej: "(Ljava/lang/String;)V")
	 * @return Lista de referencias encontradas en el método
	 */
	default List<Referencia> buscarReferenciasEnMetodo(String nombreClase, String nombreMetodo, String descriptor) {
		String nombrePunto = nombreClase.replace('/', '.');
		List<Referencia> cacheadas = ParserMixerLogger.obtenerReferenciasDesdeCache(nombrePunto);
		if (!cacheadas.isEmpty()) {
			// Filtrar por método específico
			List<Referencia> filtradas = new ArrayList<>();
			for (Referencia r : cacheadas) {
				if (r.esMetodo() && r.obtenerNombre().equals(nombreMetodo)
						&& r.obtenerDescriptor().equals(descriptor)) {
					filtradas.add(r);
				}
			}
			if (!filtradas.isEmpty())
				return filtradas;
		}

		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.analizarReferenciasEnMetodo(this, nombreClase, nombreMetodo, descriptor);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.analizarReferenciasEnMetodo(this, nombreClase, nombreMetodo, descriptor);
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Busca todas las referencias a un método específico (llamadas externas).
	 * 
	 * @param claseObjetivo      Clase objetivo del método (ej: "java/lang/String")
	 * @param metodoObjetivo     Nombre del método objetivo
	 * @param descriptorObjetivo Descriptor del método objetivo
	 * @return Lista de referencias que llaman al método objetivo
	 */
	default List<Referencia> buscarReferenciasAMetodo(String claseObjetivo, String metodoObjetivo,
			String descriptorObjetivo) {
		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.analizarReferenciasAMetodo(this, claseObjetivo, metodoObjetivo,
					descriptorObjetivo);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.analizarReferenciasAMetodo(this, claseObjetivo, metodoObjetivo,
					descriptorObjetivo);
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * Obtiene nombres de módulo desde module-info.class.
	 * 
	 * @param moduleinfo Bytes del archivo module-info.class
	 * @return Lista de nombres de módulo encontrados
	 */
	public default List<String> obtenerNombresDeModuleInfo(byte[] moduleinfo) {
		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.obtenerNombreModuloInfo(moduleinfo);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.obtenerNombreModuloInfo(moduleinfo);
		} else {
			return new ArrayList<>();
		}
	}

	// ========================================================================
	// MÉTODOS PARA ANÁLISIS DE MIXINS (DELEGADOS A PARSER)
	// ========================================================================

	/**
	 * Verifica si una clase es un mixin de SpongePowered (tiene @Mixin).
	 * 
	 * <p>
	 * Primero consulta el cache de ParserMixerLogger si está cargado.
	 * </p>
	 * 
	 * @param nombreClase Nombre de la clase en formato interno (ej: "a/b/CMixin")
	 * @return true si la clase tiene la anotación @Mixin, false en caso contrario
	 */
	default boolean esClaseMixin(String nombreClase) {
		String nombrePunto = nombreClase.replace('/', '.');
		if (ParserMixerLogger.esCacheCargado() && ParserMixerLogger.obtenerMixinDesdeCache(nombrePunto) != null) {
			return true;
		}

		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.esClaseMixin(this, nombreClase);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.esClaseMixin(this, nombreClase);
		} else {
			return false;
		}
	}

	/**
	 * Obtiene información detallada de un mixin.
	 * 
	 * <p>
	 * Primero consulta el cache de ParserMixerLogger si está cargado.
	 * </p>
	 * 
	 * @param nombreClase Nombre de la clase mixin en formato interno
	 * @return Objeto MixinInfo con los datos extraídos, o null si no es mixin o hay
	 *         error
	 */
	default MixinInfo obtenerInfoMixin(String nombreClase) {
		String nombrePunto = nombreClase.replace('/', '.');
		MixinInfo cacheado = ParserMixerLogger.obtenerMixinDesdeCache(nombrePunto);
		if (cacheado != null) {
			return cacheado;
		}

		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.obtenerInfoMixin(this, nombreClase);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.obtenerInfoMixin(this, nombreClase);
		} else {
			return null;
		}
	}

	/**
	 * Busca todas las clases mixin en este mod.
	 * 
	 * <p>
	 * Si el cache de ParserMixerLogger está cargado, retorna las clases cacheadas.
	 * </p>
	 * 
	 * @return Lista de nombres de clases que son mixins (formato interno)
	 */
	default List<String> buscarClasesMixin() {
		if (ParserMixerLogger.esCacheCargado()) {
			List<String> resultados = new ArrayList<>();
			// Iterar sobre las claves del cache interno de ParserMixerLogger
			// (usamos reflexión para acceder al mapa privado de forma segura)
			try {
				java.lang.reflect.Field fieldCache = ParserMixerLogger.class.getDeclaredField("Cache");
				fieldCache.setAccessible(true);
				Object cacheInstance = fieldCache.get(null);
				java.lang.reflect.Field fieldMap = cacheInstance.getClass().getDeclaredField("mixinsPorClase");
				fieldMap.setAccessible(true);
				@SuppressWarnings("unchecked")
				java.util.Map<String, ?> cacheMap = (java.util.Map<String, ?>) fieldMap.get(cacheInstance);
				for (String nombrePunto : cacheMap.keySet()) {
					resultados.add(nombrePunto.replace('.', '/'));
				}
			} catch (Exception e) {
				// Si falla la reflexión, fallback a búsqueda manual
				for (String nombreClase : obtenerTodosLosNombresDeClases()) {
					if (esClaseMixin(nombreClase)) {
						resultados.add(nombreClase);
					}
				}
			}
			return resultados;
		}

		// Fallback: búsqueda manual si el cache no está cargado
		List<String> resultados = new ArrayList<>();
		for (String nombreClase : obtenerTodosLosNombresDeClases()) {
			if (esClaseMixin(nombreClase)) {
				resultados.add(nombreClase);
			}
		}
		return resultados;
	}

	/**
	 * Obtiene los bytes de una clase.
	 * 
	 * @param nombreClase Nombre completo de la clase en formato interno (ej:
	 *                    "java/lang/Object")
	 * @return Bytes de la clase o null si no existe
	 */
	byte[] obtenerBytesClase(String nombreClase);

	/**
	 * Obtiene todos los nombres de clases en el mod.
	 * 
	 * @return Lista de nombres de clases en formato interno
	 */
	List<String> obtenerTodosLosNombresDeClases();

	/**
	 * Los cargadors la mod suporte
	 * 
	 * @return
	 */
	public List<Cargador> cargadores();

	public default boolean funcionarConCargadoresActuales() {
		for (Cargador car : cargadores()) {
			if (Cargador.cargadores_activados.contains(car)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Si el archivo (descriptor.mod, modules.xml,fabric.mod.json,mods.toml,etc)
	 * tiene String mcreator.
	 * 
	 * @return
	 */
	public default boolean MetaDataTieneReferenciaDeMCReator() {
		return false;
	}

	// ========================================================================
	// CLASES AUXILIARES PARA ANÁLISIS DE BYTECODE
	// ========================================================================

	/**
	 * Información detallada de un método incluyendo sus referencias internas.
	 */
	public static class InfoMetodo {
		private final String nombre;
		private final String descriptor;
		private final List<Referencia> referenciasAMetodos;
		private final List<Referencia> referenciasACampos;

		public InfoMetodo(String nombre, String descriptor, List<Referencia> referenciasAMetodos,
				List<Referencia> referenciasACampos) {
			this.nombre = nombre;
			this.descriptor = descriptor;
			this.referenciasAMetodos = new ArrayList<>(referenciasAMetodos);
			this.referenciasACampos = new ArrayList<>(referenciasACampos);
		}

		public String obtenerNombre() {
			return nombre;
		}

		public String obtenerDescriptor() {
			return descriptor;
		}

		public List<Referencia> obtenerReferenciasAMetodos() {
			return new ArrayList<>(referenciasAMetodos);
		}

		public List<Referencia> obtenerReferenciasACampos() {
			return new ArrayList<>(referenciasACampos);
		}
	}

	/**
	 * Información básica de un campo declarado.
	 */
	public static class InfoCampo {
		private final String nombre;
		private final String descriptor;

		public InfoCampo(String nombre, String descriptor) {
			this.nombre = nombre;
			this.descriptor = descriptor;
		}

		public String obtenerNombre() {
			return nombre;
		}

		public String obtenerDescriptor() {
			return descriptor;
		}
	}

	/**
	 * Representa una referencia a un método o campo.
	 */
	public static class Referencia {
		private final String clase;
		private final String nombre;
		private final String descriptor;
		private final boolean esMetodo;

		public Referencia(String clase, String nombre, String descriptor, boolean esMetodo) {
			this.clase = clase;
			this.nombre = nombre;
			this.descriptor = descriptor;
			this.esMetodo = esMetodo;
		}

		public String obtenerClase() {
			return clase;
		}

		public String obtenerNombre() {
			return nombre;
		}

		public String obtenerDescriptor() {
			return descriptor;
		}

		public boolean esMetodo() {
			return esMetodo;
		}

		public boolean esCampo() {
			return !esMetodo;
		}
	}

	/**
	 * Contiene toda la información extraída de una clase mixin de SpongePowered.
	 * Incluye targets del @Mixin, métodos con @Inject/@Overwrite, campos @Shadow, y
	 * mapeos de refmap cuando están disponibles.
	 */
	public static class MixinInfo {
		private final String nombreClase;
		private final String jarOrigen;
		private final List<String> targets;
		private final List<MixinMetodoInfo> metodosMixin;
		private final List<MixinCampoInfo> camposMixin;
		private final List<String> targetsInject;
		private final java.util.Map<String, String> refmapMetodos;
		private final java.util.Map<String, String> refmapCampos;

		public MixinInfo(String nombreClase, String jarOrigen, List<String> targets, List<MixinMetodoInfo> metodosMixin,
				List<MixinCampoInfo> camposMixin, List<String> targetsInject,
				java.util.Map<String, String> refmapMetodos, java.util.Map<String, String> refmapCampos) {
			this.nombreClase = nombreClase;
			this.jarOrigen = jarOrigen;
			this.targets = targets != null ? new ArrayList<>(targets) : new ArrayList<>();
			this.metodosMixin = metodosMixin != null ? new ArrayList<>(metodosMixin) : new ArrayList<>();
			this.camposMixin = camposMixin != null ? new ArrayList<>(camposMixin) : new ArrayList<>();
			this.targetsInject = targetsInject != null ? new ArrayList<>(targetsInject) : new ArrayList<>();
			this.refmapMetodos = refmapMetodos != null ? new java.util.HashMap<>(refmapMetodos)
					: new java.util.HashMap<>();
			this.refmapCampos = refmapCampos != null ? new java.util.HashMap<>(refmapCampos)
					: new java.util.HashMap<>();
		}

		public String obtenerNombreClase() {
			return nombreClase;
		}

		public String obtenerJarOrigen() {
			return jarOrigen;
		}

		public List<String> obtenerTargets() {
			return new ArrayList<>(targets);
		}

		public List<MixinMetodoInfo> obtenerMetodosMixin() {
			return new ArrayList<>(metodosMixin);
		}

		public List<MixinCampoInfo> obtenerCamposMixin() {
			return new ArrayList<>(camposMixin);
		}

		public List<String> obtenerTargetsInject() {
			return new ArrayList<>(targetsInject);
		}

		public java.util.Map<String, String> obtenerRefmapMetodos() {
			return new java.util.HashMap<>(refmapMetodos);
		}

		public java.util.Map<String, String> obtenerRefmapCampos() {
			return new java.util.HashMap<>(refmapCampos);
		}

		public String buscarMapeoMetodo(String nombreMetodo, String descriptor) {
			String clave = nombreMetodo + descriptor;
			return refmapMetodos.get(clave);
		}

		public String buscarMapeoCampo(String nombreCampo) {
			return refmapCampos.get(nombreCampo);
		}

		@Override
		public String toString() {
			return "MixinInfo{clase=" + nombreClase + ", jar=" + jarOrigen + ", targets=" + targets.size()
					+ ", metodos=" + metodosMixin.size() + ", campos=" + camposMixin.size() + "}";
		}
	}

	/**
	 * Información de un método mixin con @Inject o @Overwrite.
	 */
	public static class MixinMetodoInfo {
		private final String nombre;
		private final String descriptor;
		private final List<String> targets;
		private final boolean esOverwrite;

		public MixinMetodoInfo(String nombre, String descriptor, List<String> targets, boolean esOverwrite) {
			this.nombre = nombre;
			this.descriptor = descriptor;
			this.targets = targets != null ? new ArrayList<>(targets) : new ArrayList<>();
			this.esOverwrite = esOverwrite;
		}

		public String obtenerNombre() {
			return nombre;
		}

		public String obtenerDescriptor() {
			return descriptor;
		}

		public List<String> obtenerTargets() {
			return new ArrayList<>(targets);
		}

		public boolean esOverwrite() {
			return esOverwrite;
		}

		public boolean esInject() {
			return !esOverwrite;
		}

		@Override
		public String toString() {
			return nombre + descriptor + (esOverwrite ? " [@Overwrite]" : " [@Inject]")
					+ (targets.isEmpty() ? "" : " targets:" + targets);
		}
	}

	/**
	 * Información de un campo mixin con @Shadow.
	 */
	public static class MixinCampoInfo {
		private final String nombre;
		private final String descriptor;

		public MixinCampoInfo(String nombre, String descriptor) {
			this.nombre = nombre;
			this.descriptor = descriptor;
		}

		public String obtenerNombre() {
			return nombre;
		}

		public String obtenerDescriptor() {
			return descriptor;
		}

		@Override
		public String toString() {
			return nombre + ":" + descriptor + " [@Shadow]";
		}
	}

	// ========================================================================
	// MÉTODOS DE UTILIDAD PARA ACCEDER AL CACHE (DELEGADOS A PARSER)
	// ========================================================================

	/**
	 * Obtiene un MixinInfo desde el cache si está disponible.
	 * 
	 * @param nombreClasePunto Nombre de clase en formato punto (ej:
	 *                         "net.example.Mixin")
	 * @return MixinInfo cacheado o null si no está en cache
	 */
	public static MixinInfo obtenerMixinDesdeCache(String nombreClasePunto) {
		return ParserMixerLogger.obtenerMixinDesdeCache(nombreClasePunto);
	}

	/**
	 * Obtiene InfoMetodo cacheados para una clase.
	 * 
	 * @param nombreClasePunto Nombre de clase en formato punto
	 * @return Lista de InfoMetodo o lista vacía si no hay cache
	 */
	public static List<InfoMetodo> obtenerMetodosDesdeCache(String nombreClasePunto) {
		return ParserMixerLogger.obtenerMetodosDesdeCache(nombreClasePunto);
	}

	/**
	 * Obtiene InfoCampo cacheados para una clase.
	 * 
	 * @param nombreClasePunto Nombre de clase en formato punto
	 * @return Lista de InfoCampo o lista vacía si no hay cache
	 */
	public static List<InfoCampo> obtenerCamposDesdeCache(String nombreClasePunto) {
		return ParserMixerLogger.obtenerCamposDesdeCache(nombreClasePunto);
	}

	/**
	 * Obtiene Referencias cacheadas para una clase.
	 * 
	 * @param nombreClasePunto Nombre de clase en formato punto
	 * @return Lista de Referencia o lista vacía si no hay cache
	 */
	public static List<Referencia> obtenerReferenciasDesdeCache(String nombreClasePunto) {
		return ParserMixerLogger.obtenerReferenciasDesdeCache(nombreClasePunto);
	}

	/**
	 * Limpia todo el cache de MixerLogger. Útil para recargar datos si el log
	 * cambia.
	 */
	public static void limpiarCacheMixerLogger() {
		ParserMixerLogger.limpiarCache();
	}

	/**
	 * Agrega nombres evitando duplicados y vacios.
	 */
	public default void agregarNombresSinDuplicados(List<String> nuevos) {
		if (nuevos == null) {
			return;
		}

		for (String nombre : nuevos) {
			if (nombre == null) {
				continue;
			}

			String limpio = nombre.trim();

			if (!limpio.isEmpty() && !this.nombre().contains(limpio)) {
				this.nombre().add(limpio);
			}
		}
	}

	/**
	 * Verifica si el cache de MixerLogger ha sido cargado.
	 * 
	 * @return true si el cache está poblado
	 */
	public static boolean esCacheMixerLoggerCargado() {
		return ParserMixerLogger.esCacheCargado();
	}

	// Implementación vacía para el origen predeterminado.
	static class Origin implements ArchivoDeMod {
		@Override
		public ArchivoDeMod obtenerDesde() {
			return origin;
		}

		@Override
		public List<ArchivoDeMod> mods_en_mods() {
			return new ArrayList<>();
		}

		@Override
		public List<String> nombre() {
			return new ArrayList<>();
		}

		@Override
		public String version() {
			return "";
		}

		@Override
		public String ubicacion() {
			return "";
		}

		@Override
		public List<String> clases() {
			return new ArrayList<>();
		}

		@Override
		public boolean tieneNombreRecursivo(String nombre) {
			return false;
		}

		@Override
		public String obtenerNombreRecursivo(String nombre) {
			return null;
		}

		@Override
		public boolean tieneArchivoRecursivo(String paquete) {
			return false;
		}

		@Override
		public String obtenerArchivoRecursivo(String paquete) {
			return null;
		}

		@Override
		public List<String> archivos() {
			return new ArrayList<>();
		}

		@Override
		public List<ArchivoDeMod> buscarModsCon(String termino) {
			return new ArrayList<>();
		}

		@Override
		public boolean existeClase(String nombreClase) {
			return false;
		}

		@Override
		public byte[] obtenerBytesClase(String nombreClase) {
			return null;
		}

		@Override
		public List<String> obtenerTodosLosNombresDeClases() {
			return new ArrayList<>();
		}

		@Override
		public List<Cargador> cargadores() {
			return new ArrayList<>();
		}

		@Override
		public int precargarTodasLasClasesRecursivo() {
			return 0;
		}

		@Override
		public boolean esClaseMixin(String nombreClase) {
			return false;
		}

		@Override
		public MixinInfo obtenerInfoMixin(String nombreClase) {
			return null;
		}

		@Override
		public boolean cargarCacheDesdeMixerLogger() {
			return false;
		}
	}

	public static class Constante {
		private final String clase;
		private final String metodo;
		private final String descriptorMetodo;
		private final Object valor;
		private final String tipo;

		public Constante(String clase, String metodo, String descriptorMetodo, Object valor, String tipo) {
			this.clase = clase;
			this.metodo = metodo;
			this.descriptorMetodo = descriptorMetodo;
			this.valor = valor;
			this.tipo = tipo;
		}

		public String obtenerClase() {
			return clase;
		}

		public String obtenerMetodo() {
			return metodo;
		}

		public String obtenerDescriptorMetodo() {
			return descriptorMetodo;
		}

		public Object obtenerValor() {
			return valor;
		}

		public String obtenerTipo() {
			return tipo;
		}

		@Override
		public String toString() {
			return "Constante{clase=" + clase + ", metodo=" + metodo + ", desc=" + descriptorMetodo + ", tipo=" + tipo
					+ ", valor=" + String.valueOf(valor) + "}";
		}
	}

	/**
	 * Busca constantes usadas dentro de un método específico.
	 */
	default List<Constante> buscarConstantesEnMetodo(String nombreClase, String nombreMetodo, String descriptorMetodo) {
		if (ASM_DISPONIBLE) {
			return AnalizadorBytecodeASM.analizarConstantesEnMetodo(this, nombreClase, nombreMetodo, descriptorMetodo);
		} else if (JAVASSIST_DISPONIBLE) {
			return AnalizadorBytecodeJavassist.analizarConstantesEnMetodo(this, nombreClase, nombreMetodo,
					descriptorMetodo);
		} else {
			return new ArrayList<>();
		}
	}

	public int precargarTodasLasClasesRecursivo();
}