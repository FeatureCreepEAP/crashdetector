package com.asbestosstar.crashdetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.anon.AnonimizadorDeRuta;
import com.asbestosstar.crashdetector.anon.AnonimizadordeRegistros;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro;
import com.asbestosstar.crashdetector.api_sito_registro.CrashDetectorPasteAPI;
import com.asbestosstar.crashdetector.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetector.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sito_registro.GnomeBotDevAPI;
import com.asbestosstar.crashdetector.api_sito_registro.LimteDeTasa;
import com.asbestosstar.crashdetector.api_sito_registro.MCLogsAPI;
import com.asbestosstar.crashdetector.api_sito_registro.NoAPIdeRegistro;
import com.asbestosstar.crashdetector.api_sito_registro.PastesDevAPI;
import com.asbestosstar.crashdetector.api_sito_registro.SecureLoggerAPI;
import com.asbestosstar.crashdetector.api_sito_registro.StikkedAPI;
import com.asbestosstar.crashdetector.divisor.DivisorDeArchivos;
import com.asbestosstar.crashdetector.divisor.TLauncherConsolaDivisor;
import com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasGUI.ErrorDeLectador;
import com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador.NoRegistroDeLauncherVShojo;
import com.asbestosstar.crashdetector.limpiador.LimpiadorDeRegistro;
import com.asbestosstar.crashdetector.limpiador.LimpiadorNingun;
import com.asbestosstar.crashdetector.limpiador.LimpiadorRegistroDeLauncherVainilla;
import com.asbestosstar.crashdetector.limpiador.LimpiadorRegistroLatestLog;
import com.asbestosstar.crashdetector.limpiador.LimpiadorRegistroStderrStreamCrashAssistant;

public class Consola {

	/**
	 * NO USAS DESPUES DE finalizarContento
	 */
	public String contenido;

	public Path archivo;
	public List<String> enlaces = new ArrayList<String>();
	public int linea_original;
	public boolean nueva = false;
	public String contenido_verificar;
	public String[] lineas_verificar;

	public VerificacionDeStackTrace verificacion_de_stacktrace;

	public List<ErrorDeLectador> errores_de_lectadores = new ArrayList<ErrorDeLectador>();

	public LimpiadorDeRegistro limpiador;

	public static ArrayList<String> archivos_en_lista = new ArrayList<String>();

	/**
	 * Si el registro es un registro de lanzer o consola registar aqui en
	 * procesoDeLaMonitorizacionDePID.
	 */
	public static List<String> tipos_de_registros_de_launcher = new ArrayList<String>();// No para registros
																						// con "launcher en
																						// el nombre"

	public static SecureLoggerAPI secure_logger_api = new SecureLoggerAPI();

	/**
	 * Divisores de archivos de registro. Importante cuando tienes archivos de
	 * registro que contienen contenido de la aplicación o el juego que se ejecuta
	 * varias veces.. Registrar en procesoDelApp
	 */
	public static List<DivisorDeArchivos> divisores = new ArrayList<DivisorDeArchivos>();

	/**
	 * Limpiadores de los registros. Registrar en procesoDeLaMonitorizacionDePID
	 */
	public static List<LimpiadorDeRegistro> limpiadores = new ArrayList<LimpiadorDeRegistro>();

	/**
	 * La ubicacion para añadir mas registro archivos. Para los longs que se
	 * necesiten dividir se deben declarar aquí tanto en procesoDelApp como en
	 * procesoDeLaMonitorizacionDePID
	 */
	public static ArrayList<File> archivos_para_mapa = new ArrayList<File>();

	/**
	 * Para Strings de partes de nombres registros inutiles. Si el nombre de log
	 * contente algun de estos strings no incluye cuando buscando en carpetas (solo
	 * para buscando en carpetas)
	 */
	public static ArrayList<String> inutiles_archivo_strs = new ArrayList<>(); // TODO opcion de la config

	static { // APIS Por Defecto
		APIdeSitioDeRegistro.APIS.add(secure_logger_api);
		APIdeSitioDeRegistro.APIS.add(new StikkedAPI());
		APIdeSitioDeRegistro.APIS.add(new MCLogsAPI());
		APIdeSitioDeRegistro.APIS.add(new PastesDevAPI());
		APIdeSitioDeRegistro.APIS.add(new CrashDetectorPasteAPI());
		APIdeSitioDeRegistro.APIS.add(new GnomeBotDevAPI());

		divisores.add(new TLauncherConsolaDivisor());
		// divisores.add(new HolaMundoConsolaDivisidor());
		// divisores.add(new VainillaConsolaDivisor());
		tipos_de_registros_de_launcher.add("../../logs/ftb-app-electron.log");
		tipos_de_registros_de_launcher.add(NoRegistroDeLauncherVShojo.cd_launcherlog.getName());
		limpiadores.add(new LimpiadorRegistroDeLauncherVainilla());
		limpiadores.add(new LimpiadorRegistroLatestLog());
		limpiadores.add(new LimpiadorRegistroStderrStreamCrashAssistant());
		archivos_para_mapa.addAll(obtenerArchivosDeConsolas());// TODO crearar una mapa antes del processo de CD
		inutilesArchivoStrsPredetermindados();
	}

	public Consola(Path archivo) throws IOException {
		super();
		this.archivo = archivo;
		linea_original = 0;

		String clave;
		try {
			clave = archivo.toFile().getCanonicalPath().replace('\\', '/');
		} catch (IOException e) {
			clave = archivo.toAbsolutePath().normalize().toString().replace('\\', '/');
		}

		if (!archivos_en_lista.contains(clave)) {
			archivos_en_lista.add(clave); // marcar como visto
		}

		for (DivisorDeArchivos div : divisores) {
			String contento_existe = MonitorDePID.leer_archivo(archivo);
			if (div.predicado(archivo, contento_existe)) {
				int ln = div.obtenerLineaOriginal(contento_existe.split(Verificaciones.nl));
				if (ln > 0) {
					linea_original = ln;
					break;
				}
				;
			}
		}
		// CrashDetectorLogger.log(archivo.toAbsolutePath()+" linea "+
		// String.valueOf(linea_original));

	}

	private static void inutilesArchivoStrsPredetermindados() {
		// TODO Auto-generated method stub
		inutiles_archivo_strs.add("kubejs");// no tiene nada
		inutiles_archivo_strs.add("crafttweaker");// no tiene nada
		inutiles_archivo_strs.add("crash_assistant");// no tiene nada y Aún es demasiado pronto para usarlo
														// correctamente y a veces genera un error de pila y puede tener
														// falsos positivos; probablemente esta sea también una causa
														// conocida del engaño de incompatibilidad que intentan imponer.
		inutiles_archivo_strs.add("telemetry");// no tiene nada y es comun en la lanzer vainilla
		inutiles_archivo_strs.add("launcher");// no quieremos launcher logs, KLauncher y Astralrinth y mas incluye sus
												// registros aqui
		inutiles_archivo_strs.add("latest.log");// no necesitemos latest.log no mas porque tenemos ProxyLog4J2
		inutiles_archivo_strs.add("debug.log");// no necesitemos latest.log no mas porque tenemos ProxyLog4J2
		inutiles_archivo_strs.add("stdout-logs.txt");// no necesitemos y causa problemas

	}

	// escribirMapa: SIN escribir tiempo global al final
	public static void escribirMapa(Instant tiempo) {
		File carpetaDestino = Statics.carpeta_como_archivo;
		if (!carpetaDestino.exists()) {
			carpetaDestino.mkdirs();
		}
		File mapa = new File(carpetaDestino, "mapa_de_registros");

		if (mapa.exists()) {
			mapa.delete();
		}
		String nl = System.lineSeparator();
		StringBuilder sb = new StringBuilder();
		Set<String> vistos = new HashSet<>();

		for (File f : archivos_para_mapa) {
			if (f == null)
				continue;
			try {
				String rutaCanonica = f.getCanonicalPath();
				if (vistos.contains(rutaCanonica))
					continue;
				vistos.add(rutaCanonica);

				if (!f.exists() || !f.isFile())
					continue;

				int linea = 0;
				boolean huboDivisor = false;

				for (DivisorDeArchivos div : divisores) {
					try {
						String contenido = MonitorDePID.leer_archivo(f.toPath());
						if (div.predicado(f.toPath(), contenido)) {
							huboDivisor = true;
							int ln = div.obtenerLineaOriginal(contenido.split(Verificaciones.nl));
							if (ln > 0) {
								linea = ln;
								break;
							}
						}
					} catch (Exception ex) {
						CrashDetectorLogger.logException(ex);
					}
				}

				if (!huboDivisor || linea <= 0) {
					linea = contarLineas(f.toPath());
				}

				if (linea > 0) {
					sb.append(rutaCanonica).append('\t').append(tiempo.toEpochMilli()).append('\t').append(linea)
							.append(nl);
				}
			} catch (IOException e) {
				CrashDetectorLogger.logException(e);
			}
		}

		try (java.io.BufferedWriter w = new java.io.BufferedWriter(new java.io.FileWriter(mapa))) {
			w.write(sb.toString());
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
		}
	}

	private static int contarLineas(java.nio.file.Path ruta) {
		int total = 0;
		try (java.io.BufferedReader br = java.nio.file.Files.newBufferedReader(ruta)) {
			while (br.readLine() != null)
				total++;
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
		}
		return total;
	}

// lector que solo devuelve consolas de archivos cuyo mtime es posterior al del mapa
	public static List<Consola> leerMapaConsolasComoLista() {
		List<Consola> lista = new ArrayList<>();
		File carpeta = Statics.carpeta_como_archivo;
		File mapa = new File(carpeta, "mapa_de_registros");
		if (!mapa.exists() || !mapa.isFile()) {
			return lista;
		}

		Set<String> vistas = new HashSet<>();

		try (BufferedReader br = new BufferedReader(new FileReader(mapa))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String l = linea.trim();
				if (l.isEmpty())
					continue;

				String[] partes = l.split("\t");
				if (partes.length < 3)
					continue;

				String ruta = partes[0].trim();
				long tiempoGuardado = 0L;
				int lineaGuardada = 0;
				try {
					tiempoGuardado = Long.parseLong(partes[1].trim());
				} catch (NumberFormatException ignore) {
				}
				try {
					lineaGuardada = Integer.parseInt(partes[2].trim());
				} catch (NumberFormatException ignore) {
				}

				File f = new File(ruta);
				if (!f.exists() || !f.isFile())
					continue;

				try {
					File fCanonico = f.getCanonicalFile();
					String key = fCanonico.getAbsolutePath();
					if (vistas.contains(key))
						continue;
					vistas.add(key);

					long modAhora = fCanonico.lastModified();

					if (modAhora > tiempoGuardado) {
						Consola c = new Consola(fCanonico.toPath());
						if (lineaGuardada > 0) {
							c.linea_original = lineaGuardada;
						}
						lista.add(c);

					}
				} catch (IOException e) {
					CrashDetectorLogger.logException(e);
				}
			}
		} catch (IOException e) {
			CrashDetectorLogger.logException(e);
		}

		return lista;
	}

	public void finalizarContenido(Instant tiempo, boolean ignorar_necesita_estar_despues_de_tiempo) {
		try {
			Instant epoc = Instant.ofEpochMilli(archivo.toFile().lastModified());

			if (epoc.isAfter(tiempo) || ignorar_necesita_estar_despues_de_tiempo || nueva) {
				nueva = true;
				contenido = MonitorDePID.leer_archivo(archivo);

				String[] lineas = contenido.split("\\r?\\n", -1); // conserva línea vacía final si existe
				StringBuilder para_verificar = new StringBuilder(contenido.length() + 64);
				int inicio = Math.max(0, linea_original);
				for (int i = inicio; i < lineas.length; i++) {
					para_verificar.append(lineas[i]);
					if (i < lineas.length - 1)
						para_verificar.append('\n');
				}

				// CrashDetectorLogger.log("archivo nombre: " + archivo.toString());
				boolean limpiado = false;
				for (LimpiadorDeRegistro limp : limpiadores) {
					if (limp.predicado(archivo)) {
						contenido_verificar = limp.limpiarConsola(para_verificar.toString());
						lineas_verificar = contenido_verificar.split(Verificaciones.nl);
						this.limpiador = limp;
						limpiado = true;
					}
				}
				if (!limpiado) {
					this.limpiador = new LimpiadorNingun();
					contenido_verificar = para_verificar.toString();
					lineas_verificar = contenido_verificar.split(Verificaciones.nl);
				}

//				if (contenido_verificar.contains(MonitorDePID.mensaje_de_registro_lanzer_completo)) {
//					MonitorDePID.tiene_mensaje_de_registro_launcher_completa = true;
//				}
//			if (
//					contenido_verificar.contains("Minecraft closed with exit code")||//TLauncher
//					contenido_verificar.contains("Process crashed with exit code")||//Vainilla
//					contenido_verificar.contains("Process exited with status")//Astralrinth
//
//					
//					
//					) {
//				MonitorDePID.tiene_mensaje_de_registro_launcher_completa = true;
//			}
			} else {
				nueva = false;
				contenido = "";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void finalizarContenidoInyectado(String contento) {
		nueva = true;
		this.contenido = contento;

		// CrashDetectorLogger.log("archivo nombre inyectado: " + archivo.toString());

		boolean limpiado = false;
		for (LimpiadorDeRegistro limp : limpiadores) {
			if (limp.predicado(archivo)) {
				contenido_verificar = limp.limpiarConsola(contento);
				lineas_verificar = contenido_verificar.split(Verificaciones.nl);
				limpiado = true;
				this.limpiador = limp;
			}
		}
		if (!limpiado) {
			this.limpiador = new LimpiadorNingun();
			contenido_verificar = contento.toString();
			lineas_verificar = contenido_verificar.split(Verificaciones.nl);
		}

	}

	public static List<Consola> obtenerConsolas() {
		List<Consola> resultado = new ArrayList<>();

//	    for (Consola c : leerMapaConsolasComoLista()) {
//	        String k = clave(c.archivo);
//	            resultado.add(c);
//	        
//	    }

		for (File archivo : obtenerArchivosDeConsolas()) {
			if (archivo == null || !archivo.exists() || !archivo.isFile())
				continue;

			String k = clave(archivo);
			if (archivos_en_lista.contains(k)) {
				continue;
			}

			try {
				Consola c = new Consola(archivo.toPath());
				resultado.add(c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultado;
	}

	private static String clave(Path p) {
		try {
			return p.toFile().getCanonicalPath().replace('\\', '/');
		} catch (IOException e) {
			return p.toAbsolutePath().normalize().toString().replace('\\', '/');
		}
	}

	private static String clave(File f) {
		try {
			return f.getCanonicalPath().replace('\\', '/');
		} catch (IOException e) {
			return f.getAbsolutePath().replace('\\', '/');
		}
	}

	public static List<File> obtenerArchivosDeConsolas() {
		List<File> resultado = new ArrayList<>();
//		Set<String> rutasLauncherLog = new HashSet<>(); // Solo para launcher_log.txt
//		String home = System.getProperty("user.home");
//		String soporteAplicaciones = home + "/Library/Application Support/";
//		String appdata = System.getenv("APPDATA");

		// Agregar logs principales con control de duplicados
//		agregarLauncherLog(new File("launcher_log.txt"), resultado, rutasLauncherLog);
//		agregarLauncherLog(new File("../../Install/launcher_log.txt"), resultado, rutasLauncherLog); // CurseForgeApp
//		agregarLauncherLog(new File(soporteAplicaciones + "minecraft/launcher_log.txt"), resultado, rutasLauncherLog); // CurseForgeApp
//		agregarLauncherLog(new File(home + "/.minecraft/launcher_log.txt"), resultado, rutasLauncherLog); // CurseForgeApp
//
//		
//		if (appdata != null) {
//			agregarLauncherLog(new File(appdata + "/.minecraft/launcher_log.txt"), resultado, rutasLauncherLog); // CurseForgeApp
//		}

		// Lexplosion/NightWorld: buscar en %APPDATA%/lexplosion-data los debug y crash
		// del launcher
		// https://discord.com/channels/958036956464422935/1012318418579497061/1417817825908752425
//		if (appdata != null) {
//			File carpetaLexplosion = new File(appdata + "/lexplosion-data/");
//			agregarDirectorio(resultado, carpetaLexplosion);
//		}

//		agregarDirectorio(resultado, new File("../.hmcl/logs/"));
//		agregarDirectorio(resultado, new File(home + "/.hmcl/logs/"));
//		if (appdata != null) {
//			agregarDirectorio(resultado, new File(appdata + "/.hmcl/logs/"));
//		}
//		agregarDirectorio(resultado, obtanerHMCLDirMunidial().toFile());
//https://github.com/HMCL-dev/HMCL/issues/2663

		// Agregar otros logs sin control de duplicados
		agregarDirectorio(resultado, new File("logs/"));// Minecraft Logs
		agregarDirectorio(resultado, new File("crash-reports/"));// Minecraft Crash Reports
		agregarDirectorio(resultado, new File("var/logs/"));// FeatureCreep Logs

		// Configuración de TLauncher
//		File carpetaTLauncherStarter;
//		File carpetaTLauncher;
//		File archivoTLLegacy;
//		if (appdata != null) {
//			carpetaTLauncherStarter = new File(appdata + "/.tlauncher/logs/starter/");
//			carpetaTLauncher = new File(appdata + "/.tlauncher/logs/tlauncher/");
//			archivoTLLegacy = new File(appdata + "/.tlauncher/logs/launcher.log");
//		} else if (new File(soporteAplicaciones + "/tlauncher/").exists()) {
//			carpetaTLauncherStarter = new File(soporteAplicaciones + "/tlauncher/logs/starter/");
//			carpetaTLauncher = new File(soporteAplicaciones + "/tlauncher/logs/tlauncher/");
//			archivoTLLegacy = new File(soporteAplicaciones + "/tlauncher/logs/launcher.log");
//		} else {
//			carpetaTLauncherStarter = new File(home + "/.tlauncher/logs/starter/");
//			carpetaTLauncher = new File(home + "/.tlauncher/logs/tlauncher/");
//			archivoTLLegacy = new File(home + "/.tlauncher/logs/launcher.log");
//		}

		// agregarDirectorio(resultado, carpetaTLauncher);
		// agregarDirectorio(resultado, carpetaTLauncherStarter);

		// Agregar otros archivos directamente
		// agregarLauncherLog(new File(home + ".minecraft/launcher_log.txt"), resultado,
		// rutasLauncherLog); // CurseForgeApp
		// y TL
//		String str_carpHMCL = Config.obtenerInstancia().obtenerCarpetaHMCL();
//		if (!str_carpHMCL.isEmpty()) {
//			File carpHMCL = new File(str_carpHMCL);
//			agregarDirectorio(resultado, carpHMCL);
//		}
//
//		resultado.add(archivoTLLegacy);

		// segundo
		// resultado.add(new File("../../logs/ftb-app-electron.log")); // FTB
		// resultado.add(new File("sklauncher/sklauncher_logs.txt"));no funciona
		resultado.add(NoRegistroDeLauncherVShojo.cd_launcherlog);
		resultado.add(new File("hs_err_pid" + String.valueOf(MonitorDePID.pid) + ".log")); // hs

		return resultado;
	}

	// Verifica si el sistema operativo es Linux o BSD
	private static final boolean ES_LINUX_O_BSD = System.getProperty("os.name").toLowerCase().contains("linux")
			|| System.getProperty("os.name").toLowerCase().contains("bsd");

	/**
	 * Obtiene el directorio principal de HMCL siguiendo sus convenciones
	 * 
	 * @return Ruta al directorio principal de HMCL
	 */
//	private static Path obtanerHMCLDirMunidial() {
//		// Primero verificar propiedad del sistema
//		String hmclHomeProp = System.getProperty("hmcl.home");
//		if (hmclHomeProp != null) {
//			return Paths.get(hmclHomeProp).toAbsolutePath().normalize();
//		}
//
//		// Lógica específica para Linux/BSD
//		if (ES_LINUX_O_BSD) {
//			String xdgData = System.getenv("XDG_DATA_HOME");
//			if (xdgData != null && !xdgData.isEmpty()) {
//				return Paths.get(xdgData, "hmcl").toAbsolutePath().normalize();
//			}
//			return Paths.get(System.getProperty("user.home"), ".local", "share", "hmcl").toAbsolutePath().normalize();
//		}
//
//		// Para Windows y macOS
//		return Paths.get(System.getProperty("user.home"), ".hmcl").toAbsolutePath().normalize();
//	}

	/**
	 * Agrega archivos de logs evitando duplicados solo para launcher_log.txt
	 */
//	private static void agregarLauncherLog(File archivo, List<File> resultado, Set<String> rutas) {
//		if (archivo.getName().equals("launcher_log.txt")) {
//			try {
//				String rutaCanonica = archivo.getCanonicalPath();
//				if (!rutas.contains(rutaCanonica)) {
//					resultado.add(archivo);
//					rutas.add(rutaCanonica);
//				}
//			} catch (IOException e) {
//				resultado.add(archivo);
//			}
//		} else {
//			resultado.add(archivo);
//		}
//	}

	/**
	 * Agrega recursivamente todos los archivos de un directorio y sus
	 * subdirectorios sin control de duplicados, excluyendo archivos .gz y
	 * subdirectorios que coinciden con patrones de inutiles_archivo_strs.
	 */
	public static void agregarDirectorio(List<File> resultado, File directorio) {
		if (!directorio.exists() || !directorio.isDirectory()) {
			return;
		}

		// Preparar conjunto en minúsculas para comparación eficiente a partir de la
		// lista externa
		// (si la lista es null o vacía, se comporta como no haber filtros)
		final Set<String> inutilesLower = (inutiles_archivo_strs == null) ? Collections.emptySet()
				: inutiles_archivo_strs.stream().filter(Objects::nonNull).map(String::toLowerCase)
						.collect(Collectors.toSet());

		// Listar los archivos y subdirectorios del directorio actual
		File[] archivos = directorio.listFiles();
		if (archivos == null)
			return;

		for (File archivo : archivos) {
			if (archivo == null)
				continue;

			String nombre = archivo.getName();
			if (nombre == null)
				continue;

			String lowerNombre = nombre.toLowerCase();

			// Si es un directorio, verificar si su nombre debe ser excluido
			if (archivo.isDirectory()) {
				// Si el nombre del directorio está en inutilesLower, lo excluimos
				if (inutilesLower.stream().anyMatch(lowerNombre::contains)) {
					continue; // Si el directorio está en la lista de inutiles, lo saltamos
				}

				// Si no es un directorio excluido, llamamos recursivamente
				agregarDirectorio(resultado, archivo);
			} else if (archivo.isFile()) {
				// Si el archivo es comprimido, lo excluimos
				boolean esComprimido = lowerNombre.endsWith(".gz") || lowerNombre.endsWith(".xz");
				// Si contiene una cadena no deseada, también lo excluimos
				boolean contieneInutil = !inutilesLower.isEmpty()
						&& inutilesLower.stream().anyMatch(lowerNombre::contains);

				if (!esComprimido && !contieneInutil) {
					resultado.add(archivo);
				}
			}
		}
	}

	public static List<Consola> reorganizar(List<Consola> consolas) {
		List<Consola> crashReports = new ArrayList<>();
		List<Consola> otros = new ArrayList<>();
		List<Consola> latestYDebug = new ArrayList<>();

		for (Consola consola : consolas) {
			String nombreArchivo = consola.archivo.toString().toLowerCase();

			if (nombreArchivo.contains("crash-report")) {
				// Archivos de crash van primero
				crashReports.add(consola);
			} else if (nombreArchivo.endsWith("latest.log") || nombreArchivo.endsWith("debug.log")) {
				// latest.log y debug.log van al final, por debajo de crash reports y después
				// del resto por que a veces registro de lanzer no tiene nombre jars
				latestYDebug.add(consola);
			} else {
				// El resto mantiene su orden original en este bloque intermedio
				otros.add(consola);
			}
		}

		// Orden final: 1) crash reports, 2)latest.log y debug.log , 3) otros logs
		List<Consola> priorizadas = new ArrayList<>(crashReports.size() + otros.size() + latestYDebug.size());
		priorizadas.addAll(crashReports);
		priorizadas.addAll(latestYDebug);
		priorizadas.addAll(otros);
		return priorizadas;
	}

	public static boolean tiene_registro_de_launcher(List<Consola> cons) {
		for (Consola con : cons) {
			File arch = con.archivo.toFile();
			String nombre = arch.getName();
			if (nombre.toLowerCase().contains("launcher") && !nombre.contains("PrismLauncher-0")) {
				return true;
			}
			if (nombre.toLowerCase().contains(".hmcl") && nombre.toLowerCase().contains("logs")) {
				return true;
			}

			if (nombre.toLowerCase().contains("klauncher") && nombre.toLowerCase().contains("logs")) {
				return true;
			}

			for (String regdelauncher : Consola.tipos_de_registros_de_launcher) {
				if (nombre.equals(regdelauncher)) {
					return true;
				}
			}

		}
		return false;
	}

	public String obtenerRutaParaPublicar() {
		boolean anon = ConfigMundial.obtenerInstancia().esAnonimizarRegistros();
		if (anon) {
			return AnonimizadorDeRuta.anonimizarNombreDeUsuario(archivo.toString());
		}
		return archivo.toString();
	}

	public String obtainerContenidoParaPublicar() {
		boolean anon = ConfigMundial.obtenerInstancia().esAnonimizarRegistros();
		if (anon) {
			return AnonimizadordeRegistros.anonimizar(this.contenido_verificar);
		}
		return contenido_verificar;
	}

	public String obtenerMensajeUltimaTrace() {
		List<VerificacionDeStackTrace.TraceInfo> tracesInfo = VerificacionDeStackTrace.obtenerTracesConLinea(this);
		if (!tracesInfo.isEmpty()) {
			// La última traza en el log es la más reciente (está al final de la lista)
			VerificacionDeStackTrace.TraceInfo ultimaTraceInfo = tracesInfo.get(tracesInfo.size() - 1);
			String ultimaTrace = ultimaTraceInfo.trace;
			String[] lineas = ultimaTrace.split(Verificaciones.nl);

			// Buscar la primera línea con un mensaje real de error
			for (String linea : lineas) {
				String trimLinea = linea.trim();
				if (trimLinea.isEmpty())
					continue;

				// Saltar líneas de pila ("at ...")
				if (trimLinea.startsWith("at "))
					continue;

				// Saltar "Caused by" (se procesa en otro método)
				if (trimLinea.contains("Caused by"))
					continue;

				// Saltar "Suppressed" (son errores secundarios)
				if (trimLinea.contains("Suppressed"))
					continue;

				// Devolver el mensaje limpio
				return trimLinea;
			}
		}
		return "";
	}

	public String obtainerMensajeFatalUltimaTrace() {
		List<VerificacionDeStackTrace.TraceInfo> tracesInfo = VerificacionDeStackTrace.obtenerTracesFatalConLinea(this);
		if (!tracesInfo.isEmpty()) {
			// La última traza fatal en el log es la más reciente (está al final de la
			// lista)
			VerificacionDeStackTrace.TraceInfo ultimaTraceInfo = tracesInfo.get(tracesInfo.size() - 1);
			String ultimaTrace = ultimaTraceInfo.trace;
			String[] lineas = ultimaTrace.split(Verificaciones.nl);

			// Buscar el mensaje fatal en las líneas
			for (String linea : lineas) {
				String trimLinea = linea.trim();
				if (trimLinea.isEmpty())
					continue;

				// Saltar líneas de pila
				if (trimLinea.startsWith("at "))
					continue;

				// Saltar "Caused by"
				if (trimLinea.contains("Caused by"))
					continue;

				// Saltar "Suppressed"
				if (trimLinea.contains("Suppressed"))
					continue;

				// Devolver el mensaje limpio
				return trimLinea;
			}
		}
		return "";
	}

	/**
	 * Agregar un error a Lectador De Consolas
	 * 
	 * @param numero_de_linea el numero de linea del error. puedes usar esta metedo
	 *                        mas de una vez si el error es de mas 1 linea
	 * @param verificacion    la verificaion
	 * @param color           Color en la clase LectadorDeConsolas
	 * @return la enlace del error
	 */
	public String agregarErrorALectador(int numero_de_linea, Verificaciones verificacion) {
		ErrorDeLectador letc = new ErrorDeLectador(this, numero_de_linea, verificacion);
		errores_de_lectadores.add(letc);

		String url = letc.toString();
		String texto = MonitorDePID.idioma.verEnConsola();
		String color = Config.obtenerInstancia().obtenerColorEnlace();

		String enlaceHtml = "<a href=\"" + url + "\" style=\"color:" + color + ";\">" + texto + "</a>";

		return enlaceHtml;
	}

	public LimpiadorDeRegistro obtenerLimpiador() {
		return limpiador;
	}

	// En Consola.java (o clase equivalente)
	public List<String> obtainerEnlaces() throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro, LimteDeTasa {
		if (enlaces.isEmpty()) {
			APIdeSitioDeRegistro api = APIdeSitioDeRegistro.obtenerAPIdeConfig();
			List<String> ret = api.publicarRegistroEnPartes(this);
			enlaces.addAll(ret);
		}
		return enlaces;
	}

	// Para todos el code aqui,escribir otra vez estar mas simplicado pero hacer la
	// funcion misma
	@Deprecated
	public String obtainerEnlance() throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro, LimteDeTasa {
		List<String> ls = obtainerEnlaces();
		return (ls != null && !ls.isEmpty()) ? ls.get(0) : null;
	}

}
