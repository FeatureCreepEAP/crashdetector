package com.asbestosstar.crashdetector.buscar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.anon.AnonimizadorDeRuta;
import com.asbestosstar.crashdetector.cargador.Cargador;
import com.asbestosstar.crashdetector.cargador.CargadorBukkit;
import com.asbestosstar.crashdetector.cargador.CargadorDangerZone;
import com.asbestosstar.crashdetector.cargador.CargadorFabric;
import com.asbestosstar.crashdetector.cargador.CargadorFeatureCreep;
import com.asbestosstar.crashdetector.cargador.CargadorLiteLoader;
import com.asbestosstar.crashdetector.cargador.CargadorMCForge;
import com.asbestosstar.crashdetector.cargador.CargadorNXOpen;
import com.asbestosstar.crashdetector.cargador.CargadorNeoForge;
import com.asbestosstar.crashdetector.cargador.CargadorRift;
import com.asbestosstar.crashdetector.config.ConfigBoolean;

public class Buscardor {

	public static Set<ArchivoDeMod> mods = java.util.Collections
			.newSetFromMap(new java.util.concurrent.ConcurrentHashMap<>());

	public static ConfigBoolean hablicar = ConfigBoolean.de("hablicar_buscardor", true);

	public static boolean cargado = false;

	/** Evita precargar varias veces todas las clases en todos los mods. */
	public static volatile boolean cargadotodos = false;

	public static void cargar() {
		if (!cargado && hablicar.obtener()) {
			try {
				String[] rutasMods = MonitorDePID.leer_archivo(MonitorDePID.ultimo_mods).split(MonitorDePID.nl);

				// Si no hay mods para cargar, marcamos como cargado y salimos
				if (rutasMods.length == 0) {
					cargado = true;
					return;
				}

				// Calcular el número óptimo de hilos
				int numeroHilos = calcularHilosOptimos(rutasMods.length);

				// Crear un ThreadPoolExecutor configurado específicamente
				ThreadPoolExecutor ejecutor = crearThreadPoolExecutor(numeroHilos);

				// Procesar cada mod en un hilo separado
				procesarModsEnParalelo(rutasMods, ejecutor);

				// Cerrar el pool de hilos de manera ordenada
				cerrarThreadPoolExecutor(ejecutor);

				leerMixerLogger();

				cargado = true;
			} catch (IOException e) {
				CrashDetectorLogger.logException(e);
			}
		}
	}

	private static void leerMixerLogger() {
		// 1. Intentar cargar cache desde MixerLogger log en las consolas
		// Esto busca automáticamente en MonitorDePID.consolas
		boolean logCargado = ParserMixerLogger.cargarDesdeConsolas();

		if (logCargado) {
			CrashDetectorLogger.log("[leer] Cache de MixerLogger cargado exitosamente");
		} else {
			CrashDetectorLogger.log("[leer] No se encontró MixerLogger.log, continuando sin cache");
		}

	}

	/**
	 * Carga mods (usando el flujo original) y luego precarga en paralelo los bytes
	 * de TODAS las clases en la caché de cada mod (incluyendo anidados). Es
	 * idempotente gracias a la bandera {@link #cargadotodos}.
	 */
	public static void cargarYPrecargarClasesEnCache() {
		if (cargadotodos) {
			return;
		}
		cargar(); // llama al original
		precargarClasesEnCacheParaModsCargados();
		cargadotodos = true;
	}

	/**
	 * Precarga en paralelo los bytes de clase para TODOS los mods ya cargados. Si
	 * no hay mods, no hace nada. No devuelve conteos (void).
	 */
	public static void precargarClasesEnCacheParaModsCargados() {
		if (obtenerTodosLosModsYSubmodsRecursivos().isEmpty()) {
			return;
		}

		int numeroHilos = calcularHilosOptimos(mods.size());
		ThreadPoolExecutor ejecutor = crearThreadPoolExecutor(numeroHilos);
		AtomicInteger totalPrecargadas = new AtomicInteger(0);

		for (ArchivoDeMod mod : mods) {
			ejecutor.submit(() -> {
				try {
					int count = precargarClasesDeUnMod(mod);
					int total = totalPrecargadas.addAndGet(count);
					if (count > 0) {
						CrashDetectorLogger.log("Precargadas " + count + " clases en " + mod.ubicacion()
								+ " (acumulado=" + total + ")");
					}
				} catch (Throwable t) {
					CrashDetectorLogger.log("Error precargando clases en " + mod.ubicacion() + ": " + t.getMessage());
					CrashDetectorLogger.logException(t);
				}
			});
		}

		cerrarThreadPoolExecutor(ejecutor);
	}

	/**
	 * Precarga todas las clases de un mod concreto, incluyendo anidados si
	 * corresponde.
	 * 
	 * @return número de clases precargadas para logging interno
	 */
	private static int precargarClasesDeUnMod(ArchivoDeMod mod) {
		return mod.precargarTodasLasClasesRecursivo();
	}

	/**
	 * Crea un ThreadPoolExecutor configurado específicamente para operaciones I/O
	 * bound.
	 *
	 * @param numeroHilos Número óptimo de hilos para la operación
	 * @return ThreadPoolExecutor configurado
	 */
	private static ThreadPoolExecutor crearThreadPoolExecutor(int numeroHilos) {
		// Tamaño mínimo de hilos (núcleo del pool) - la mitad del óptimo, mínimo 1
		int tamanoPoolNucleo = Math.max(1, numeroHilos / 2);

		// Tamaño máximo de hilos (tamaño máximo del pool) - el número óptimo calculado
		int tamanoPoolMaximo = numeroHilos;

		// Tiempo que un hilo inactivo permanece antes de ser terminado (en segundos)
		long tiempoVidaHilos = 30;

		// Cola para tareas pendientes - usamos una LinkedBlockingQueue sin límite
		BlockingQueue<Runnable> colaTrabajo = new LinkedBlockingQueue<>();

		// Factoría de hilos con nombres descriptivos para facilitar el debugging
		ThreadFactory fabricaHilos = new ThreadFactory() {
			private final AtomicInteger contador = new AtomicInteger(1);

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName("CargadorMods-" + contador.getAndIncrement());
				t.setDaemon(true);
				return t;
			}
		};

		// Política de rechazo - usamos CallerRunsPolicy para evitar perder tareas
		RejectedExecutionHandler manejadorRechazo = new ThreadPoolExecutor.CallerRunsPolicy();

		return new ThreadPoolExecutor(tamanoPoolNucleo, tamanoPoolMaximo, tiempoVidaHilos, TimeUnit.SECONDS,
				colaTrabajo, fabricaHilos, manejadorRechazo);
	}

	/**
	 * Calcula el número óptimo de hilos para operaciones I/O bound.
	 *
	 * @param numeroMods Número de mods a cargar
	 * @return Número óptimo de hilos
	 */
	private static int calcularHilosOptimos(int numeroMods) {
		// Usamos ManagementFactory para obtener el número de hilos (heurística ligera)
		int cpus = ManagementFactory.getThreadMXBean().getThreadCount();

		// Para operaciones de disco, usamos un factor de 2
		int hilosOptimos = Math.min(numeroMods, cpus * 4);

		// Asegurar mínimo 1 y máximo razonable
		return Math.max(1, Math.min(hilosOptimos, 512));
	}

	/**
	 * Procesa los mods en paralelo utilizando el ThreadPoolExecutor.
	 *
	 * @param rutasMods Array de rutas de mods a cargar
	 * @param ejecutor  ThreadPoolExecutor para ejecutar las tareas
	 */
	private static void procesarModsEnParalelo(String[] rutasMods, ThreadPoolExecutor ejecutor) {
		for (String modPath : rutasMods) {
			// Creamos variables efectivamente finales para la lambda
			final String rutaActual = modPath;
			final File archivoActual = new File(modPath);

			CrashDetectorLogger.log("buscardor mod " + rutaActual);

			ejecutor.submit(() -> {
				try {
					if (archivoActual.isFile()) {
						if (!archivoActual.getName().endsWith("deactivation")
								&& !archivoActual.getName().endsWith("disabled")
								&& !archivoActual.getName().endsWith("nil.jar")
								&& !archivoActual.getName().endsWith("nil")
								&& !archivoActual.getName().endsWith("rpm")) {

							CrashDetectorLogger.log("prearchivo mod " + archivoActual.getName());
							try (FileInputStream fis = new FileInputStream(archivoActual)) {
								CrashDetectorLogger
										.log("En Stream " + archivoActual.getName() + " cargado correctamente");
								ArchivoDeMod modObj = new ModPKZip(rutaActual, ArchivoDeMod.origin, fis);
								CrashDetectorLogger.log("crear " + archivoActual.getName() + " cargado correctamente");
								mods.add(modObj);
								CrashDetectorLogger
										.log("archivo mod " + archivoActual.getName() + " cargado correctamente");
							} catch (IOException e) {
								CrashDetectorLogger.log(
										"Error al cargar mod ZIP " + archivoActual.getName() + ": " + e.getMessage());
								CrashDetectorLogger.logException(e);
							}
						}
					} else {
						CrashDetectorLogger.log("precarpeta mod " + archivoActual.getName());
						Path rutaRaiz = archivoActual.toPath();
						ArchivoDeMod modObj = new ModCarpeta(rutaActual, ArchivoDeMod.origin, rutaRaiz);
						mods.add(modObj);
						CrashDetectorLogger.log("carpeta mod " + archivoActual.getName() + " cargada correctamente");
					}
				} catch (Exception e) {
					CrashDetectorLogger.log("Error inesperado al procesar mod " + rutaActual + ": " + e.getMessage());
					CrashDetectorLogger.logException(e);
				}
			});
		}
	}

	/**
	 * Cierra el ThreadPoolExecutor de manera ordenada.
	 *
	 * @param ejecutor ThreadPoolExecutor a cerrar
	 */
	private static void cerrarThreadPoolExecutor(ThreadPoolExecutor ejecutor) {
		// Indicar que no se aceptarán más tareas
		ejecutor.shutdown();

		try {
			// Esperar un tiempo razonable para que terminen todas las tareas
			if (!ejecutor.awaitTermination(30, TimeUnit.SECONDS)) {
				// Si no terminan, forzar el cierre
				List<Runnable> tareasPendientes = ejecutor.shutdownNow();
				// Registrar las tareas pendientes si es necesario
				if (!tareasPendientes.isEmpty()) {
					CrashDetectorLogger
							.log("Se cancelaron " + tareasPendientes.size() + " tareas pendientes de carga de mods");
				}

				// Esperar un poco más para que se detengan
				ejecutor.awaitTermination(5, TimeUnit.SECONDS);
			}
		} catch (InterruptedException e) {
			// Si se interrumpe, forzar el cierre
			CrashDetectorLogger.logException(e);
			ejecutor.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

	public static List<ArchivoDeMod> obtenerTodosLosModsYSubmodsRecursivos() {
		List<ArchivoDeMod> modsYSubmods = new ArrayList<>();

		// Recorrer todos los mods cargados
		for (ArchivoDeMod mod : mods) {
			// Añadir el mod principal
			modsYSubmods.add(mod);

			// Si el mod tiene submods, añadirlos de forma recursiva
			agregarSubmodsRecursivos(mod, modsYSubmods);
		}

		return modsYSubmods;
	}

	/**
	 * Método recursivo que añade todos los submods de un mod dado.
	 */
	public static void agregarSubmodsRecursivos(ArchivoDeMod mod, List<ArchivoDeMod> modsYSubmods) {
		// Si el mod tiene submods, los obtenemos y los añadimos recursivamente
		List<ArchivoDeMod> submods = mod.mods_en_mods(); // Asegúrate de que esta función exista o sea creada

		for (ArchivoDeMod submod : submods) {
			modsYSubmods.add(submod);
			// Llamada recursiva para explorar submods del submod
			agregarSubmodsRecursivos(submod, modsYSubmods);
		}
	}

	/**
	 * Prepara una ruta para publicación, anonimizando si es necesario.
	 *
	 * @param ruta Ruta a preparar
	 * @return Ruta anonimizada o original según configuración
	 */
	public static String rutaParaPublicar(String ruta) {
		boolean anonimo = ConfigMundial.obtenerInstancia().esAnonimizarRegistros();
		if (anonimo) {
			return AnonimizadorDeRuta.anonimizarNombreDeUsuario(ruta.toString());
		}
		return ruta.toString();
	}

	/**
	 * Obtiene mods que contienen un archivo con el nombre especificado.
	 *
	 * @param nombre Nombre del archivo a buscar
	 * @return Lista de rutas de mods que contienen el archivo
	 */
	public static List<String> obtenerModsConNombre(String nombre) {
		List<String> modsConNombre = new ArrayList<String>();
		for (ArchivoDeMod mod : obtenerTodosLosModsYSubmodsRecursivos()) {
			if (mod.tieneNombreRecursivo(nombre)) {
				CrashDetectorLogger.log("tiene recursivo nombre " + mod.ubicacion_para_publicar());
				modsConNombre.add(rutaParaPublicar(mod.obtenerNombreRecursivo(nombre)));
			}
		}
		return modsConNombre;
	}

	/**
	 * Busca todos los mods que contienen un archivo, clase o paquete específico.
	 *
	 * @param termino Término a buscar (archivo, clase o paquete)
	 * @return Lista de mods que contienen el término
	 */
	public static List<ArchivoDeMod> buscarModsConTermino(String termino) {
		List<ArchivoDeMod> resultados = new ArrayList<>();
		for (ArchivoDeMod mod : obtenerTodosLosModsYSubmodsRecursivos()) {
			resultados.addAll(mod.buscarModsCon(termino));
		}
		return resultados;
	}

	/**
	 * Obtiene los bytes de una clase desde cualquier mod cargado.
	 * <p>
	 * - Acepta nombres en formato: - com.ejemplo.Clase - com/ejemplo/Clase -
	 * com/ejemplo/Clase.class - Lcom/ejemplo/Clase; - Devuelve SIEMPRE null si la
	 * clase no existe. - Llama automáticamente a cargarYPrecargarClasesEnCache().
	 *
	 * @param nombreClase nombre de la clase
	 * @return byte[] de la clase o null si no existe
	 */
	public static byte[] obtenerBytesDeClase(String nombreClase) {
		if (nombreClase == null || nombreClase.isEmpty()) {
			return null;
		}

		// Normalizar a formato interno ASM: a/b/C
		String claseInterna = normalizarNombreClaseInterno(nombreClase);

		for (ArchivoDeMod mod : obtenerTodosLosModsYSubmodsRecursivos()) {
			try {
				if (mod.existeClase(claseInterna)) {
					CrashDetectorLogger
							.log("tenemos clase " + claseInterna + " en mod " + mod.ubicacion_para_publicar());
					byte[] bytes = mod.obtenerBytesClase(claseInterna);
					if (bytes != null) {
						return bytes;
					}
					CrashDetectorLogger.log("no bytes");
				}
			} catch (Throwable t) {
				CrashDetectorLogger
						.log("Error obteniendo bytes de " + claseInterna + " en " + mod.ubicacion_para_publicar());
				CrashDetectorLogger.logException(t);
			}
		}

		return null;
	}

	/**
	 * Normaliza un nombre de clase al formato interno de ASM: a/b/C Soporta: -
	 * com.ejemplo.Clase - com/ejemplo/Clase - com/ejemplo/Clase.class -
	 * Lcom/ejemplo/Clase;
	 */
	private static String normalizarNombreClaseInterno(String nombre) {
		if (nombre == null) {
			return null;
		}

		String s = nombre.trim();
		if (s.isEmpty()) {
			return s;
		}

		// Descriptor: Lcom/a/B;
		if (s.startsWith("L") && s.endsWith(";")) {
			s = s.substring(1, s.length() - 1);
		}

		// Quitar sufijo .class
		if (s.toLowerCase().endsWith(".class")) {
			s = s.substring(0, s.length() - ".class".length());
		}

		// Convertir puntos a barras
		s = s.replace('.', '/');

		return s;
	}

	/**
	 * Convierte una lista de mods a sus ubicaciones para publicación.
	 *
	 * @param mods Lista de mods
	 * @return Lista de ubicaciones anonimizadas
	 */
	public static List<String> obtenerUbicaciones(List<ArchivoDeMod> mods) {
		List<String> ret = new ArrayList<String>();
		for (ArchivoDeMod mod : mods) {
			ret.add(mod.ubicacion_para_publicar());
		}
		return ret;
	}

	// MÉTODOS DE ANÁLISIS DE BYTECODE (recorren todos los mods)

	/**
	 * Verifica si alguna mod contiene la clase especificada.
	 *
	 * @param nombreClase Nombre completo de la clase (ej: "java/lang/Object")
	 * @return true si al menos un mod contiene la clase, false en caso contrario
	 */
	public static boolean existeClaseEnAlgunMod(String nombreClase) {
		for (ArchivoDeMod mod : obtenerTodosLosModsYSubmodsRecursivos()) {
			if (mod.existeClase(nombreClase)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtiene información detallada de métodos para una clase específica.
	 *
	 * @param nombreClase Nombre completo de la clase
	 * @return Lista de información de métodos con sus referencias
	 */
	public static List<InfoMetodoMod> obtenerMetodosConReferenciasDeClase(String nombreClase) {
		if (!ArchivoDeMod.esAnalisisDeBytecodeDisponible()) {
			return new ArrayList<>();
		}

		List<InfoMetodoMod> resultados = new ArrayList<>();
		for (ArchivoDeMod mod : obtenerTodosLosModsYSubmodsRecursivos()) {
			if (mod.existeClase(nombreClase)) {
				List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(nombreClase);
				for (ArchivoDeMod.InfoMetodo metodo : metodos) {
					resultados.add(new InfoMetodoMod(mod, metodo));
				}
			}
		}
		return resultados;
	}

	/**
	 * Obtiene campos declarados en una clase específica.
	 *
	 * @param nombreClase Nombre completo de la clase
	 * @return Lista de información de campos
	 */
	public static List<InfoCampoMod> obtenerCamposDeClase(String nombreClase) {
		if (!ArchivoDeMod.esAnalisisDeBytecodeDisponible()) {
			return new ArrayList<>();
		}

		List<InfoCampoMod> resultados = new ArrayList<>();
		for (ArchivoDeMod mod : obtenerTodosLosModsYSubmodsRecursivos()) {
			if (mod.existeClase(nombreClase)) {
				List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(nombreClase);
				for (ArchivoDeMod.InfoCampo campo : campos) {
					resultados.add(new InfoCampoMod(mod, campo));
				}
			}
		}
		return resultados;
	}

	/**
	 * Busca todas las referencias dentro de un método específico.
	 *
	 * @param nombreClase  Nombre completo de la clase
	 * @param nombreMetodo Nombre del método
	 * @param descriptor   Descriptor del método
	 * @return Lista de referencias encontradas en el método
	 */
	public static List<ArchivoDeMod.Referencia> buscarReferenciasEnMetodoEnMods(String nombreClase, String nombreMetodo,
			String descriptor) {
		if (!ArchivoDeMod.esAnalisisDeBytecodeDisponible()) {
			return new ArrayList<>();
		}

		List<ArchivoDeMod.Referencia> resultados = new ArrayList<>();
		for (ArchivoDeMod mod : obtenerTodosLosModsYSubmodsRecursivos()) {
			if (mod.existeClase(nombreClase)) {
				resultados.addAll(mod.buscarReferenciasEnMetodo(nombreClase, nombreMetodo, descriptor));
			}
		}
		return resultados;
	}

	/**
	 * Busca todas las referencias a un método específico (llamadas externas).
	 *
	 * @param claseObjetivo      Clase objetivo del método
	 * @param metodoObjetivo     Nombre del método objetivo
	 * @param descriptorObjetivo Descriptor del método objetivo
	 * @return Lista de referencias que llaman al método objetivo
	 */
	public static List<ReferenciaMod> buscarReferenciasDeMetodoEnMods(String claseObjetivo, String metodoObjetivo,
			String descriptorObjetivo) {
		if (!ArchivoDeMod.esAnalisisDeBytecodeDisponible()) {
			return new ArrayList<>();
		}

		List<ReferenciaMod> resultados = new ArrayList<>();
		for (ArchivoDeMod mod : obtenerTodosLosModsYSubmodsRecursivos()) {
			List<ArchivoDeMod.Referencia> referencias = mod.buscarReferenciasAMetodo(claseObjetivo, metodoObjetivo,
					descriptorObjetivo);
			for (ArchivoDeMod.Referencia ref : referencias) {
				resultados.add(new ReferenciaMod(mod, ref));
			}
		}
		return resultados;
	}

	/**
	 * Convierte un nombre de clase del formato de puntos (java.lang.Object) al
	 * formato interno de ASM (java/lang/Object)
	 */
	public static String convertirFormatoClase(String nombreClase) {
		// Si ya tiene '/', asumimos que está en formato interno
		if (nombreClase.contains("/")) {
			return nombreClase;
		}
		// Si tiene '.', convertimos los puntos a barras
		else if (nombreClase.contains(".")) {
			return nombreClase.replace('.', '/');
		}
		// Si no tiene ni '.' ni '/', es una clase en el paquete por defecto
		else {
			return nombreClase;
		}
	}

	/**
	 * Convierte un nombre de clase del formato interno de ASM (java/lang/Object) al
	 * formato de puntos (java.lang.Object)
	 */
	public static String convertirFormatoClasePuntos(String nombreClase) {
		// Si ya tiene '.', asumimos que está en formato de puntos
		if (nombreClase.contains(".")) {
			return nombreClase;
		}
		// Si tiene '/', convertimos las barras a puntos
		else if (nombreClase.contains("/")) {
			return nombreClase.replace('/', '.');
		}
		// Si no tiene ni '.' ni '/', es una clase en el paquete por defecto
		else {
			return nombreClase;
		}
	}

	/**
	 * Busca todas las referencias hacia un método específico en todos los mods.
	 *
	 * @param claseObjetivo      Clase objetivo del método
	 * @param metodoObjetivo     Nombre del método objetivo
	 * @param descriptorObjetivo Descriptor del método objetivo
	 * @return Lista de referencias con información del mod y clase origen
	 */
	public static List<ReferenciaMod> buscarReferenciasHaciaMetodo(String claseObjetivo, String metodoObjetivo,
			String descriptorObjetivo) {
		if (!ArchivoDeMod.esAnalisisDeBytecodeDisponible()) {
			return new ArrayList<>();
		}

		List<ReferenciaMod> resultados = new ArrayList<>();
		for (ArchivoDeMod mod : obtenerTodosLosModsYSubmodsRecursivos()) {
			List<ArchivoDeMod.Referencia> referencias = mod.buscarReferenciasAMetodo(claseObjetivo, metodoObjetivo,
					descriptorObjetivo);
			for (ArchivoDeMod.Referencia ref : referencias) {
				resultados.add(new ReferenciaMod(mod, ref));
			}
		}
		return resultados;
	}

	// CLASES AUXILIARES PARA RESULTADOS

	/**
	 * Combina información de método con su mod de origen.
	 */
	public static class InfoMetodoMod {
		private final ArchivoDeMod mod;
		private final ArchivoDeMod.InfoMetodo infoMetodo;

		public InfoMetodoMod(ArchivoDeMod mod, ArchivoDeMod.InfoMetodo infoMetodo) {
			this.mod = mod;
			this.infoMetodo = infoMetodo;
		}

		public ArchivoDeMod obtenerMod() {
			return mod;
		}

		public ArchivoDeMod.InfoMetodo obtenerInfoMetodo() {
			return infoMetodo;
		}
	}

	/**
	 * Combina información de campo con su mod de origen.
	 */
	public static class InfoCampoMod {
		private final ArchivoDeMod mod;
		private final ArchivoDeMod.InfoCampo infoCampo;

		public InfoCampoMod(ArchivoDeMod mod, ArchivoDeMod.InfoCampo infoCampo) {
			this.mod = mod;
			this.infoCampo = infoCampo;
		}

		public ArchivoDeMod obtenerMod() {
			return mod;
		}

		public ArchivoDeMod.InfoCampo obtenerInfoCampo() {
			return infoCampo;
		}
	}

	/**
	 * Representa una referencia a un método con información del mod origen.
	 */
	public static class ReferenciaMod {
		private final ArchivoDeMod mod;
		private final ArchivoDeMod.Referencia referencia;

		public ReferenciaMod(ArchivoDeMod mod, ArchivoDeMod.Referencia referencia) {
			this.mod = mod;
			this.referencia = referencia;
		}

		public ArchivoDeMod obtenerMod() {
			return mod;
		}

		public ArchivoDeMod.Referencia obtenerReferencia() {
			return referencia;
		}
	}

	public static boolean puedeAnalizarElContentidoDeClase() {
		if (ArchivoDeMod.ASM_DISPONIBLE) {
			return true;
		}
		if (ArchivoDeMod.JAVASSIST_DISPONIBLE) {
			return true;
		}
		return false;
	}

	public static void cargadoresPredetermindado() {
		Cargador.cargadores.add(new CargadorFeatureCreep());
		Cargador.cargadores.add(new CargadorFabric());
		Cargador.cargadores.add(new CargadorMCForge());
		Cargador.cargadores.add(new CargadorNeoForge());
		Cargador.cargadores.add(new CargadorRift());
		Cargador.cargadores.add(new CargadorLiteLoader());
		Cargador.cargadores.add(new CargadorBukkit());


		Cargador.cargadores.add(new CargadorDangerZone());
		Cargador.cargadores.add(new CargadorNXOpen());
	}

}
