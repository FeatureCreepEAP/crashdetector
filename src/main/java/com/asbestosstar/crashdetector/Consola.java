package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.anon.AnonimizadorDeRuta;
import com.asbestosstar.crashdetector.anon.AnonimizadordeRegistros;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro;
import com.asbestosstar.crashdetector.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetector.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sito_registro.MCLogsAPI;
import com.asbestosstar.crashdetector.api_sito_registro.NoAPIdeRegistro;
import com.asbestosstar.crashdetector.api_sito_registro.SecureLoggerAPI;
import com.asbestosstar.crashdetector.api_sito_registro.StikkedAPI;
import com.asbestosstar.crashdetector.divisor.DivisorDeArchivos;
import com.asbestosstar.crashdetector.divisor.TLauncherConsolaDivisor;
import com.asbestosstar.crashdetector.divisor.VainillaConsolaDivisor;
import com.asbestosstar.crashdetector.gui.NoRegistroDeLauncher;
import com.asbestosstar.crashdetector.limpiador.LimipiadorDeRegistro;
import com.asbestosstar.crashdetector.limpiador.LimpiadorRegistroDeLauncherVainilla;
import com.asbestosstar.crashdetector.limpiador.LimpiadorRegistroLatestLog;

public class Consola {

	/**
	 * NO USAS DESPUES DE finalizarContento
	 */
	public String contenido;

	public Path archivo;
	public String enlance;
	public int linea_original;
	public boolean nueva = false;
	public String contenido_verificar;

	public VerificacionDeStackTrace verificacion_de_stacktrace;

	public static ArrayList<File> archivos_en_lista = new ArrayList<File>();
	public static List<String> tipos_de_registros_de_launcher = new ArrayList<String>();// No para registros
																									// con "launcher en
																									// el nombre"

	public static SecureLoggerAPI secure_logger_api = new SecureLoggerAPI();

	public static List<DivisorDeArchivos> divisores = new ArrayList<DivisorDeArchivos>();
	public static List<LimipiadorDeRegistro> limpiadores = new ArrayList<LimipiadorDeRegistro>();

	
	
	
	static { // APIS Por Defecto
		APIdeSitioDeRegistro.APIS.add(secure_logger_api);
		APIdeSitioDeRegistro.APIS.add(new StikkedAPI());
		APIdeSitioDeRegistro.APIS.add(new MCLogsAPI());
		divisores.add(new TLauncherConsolaDivisor());
		divisores.add(new VainillaConsolaDivisor());
		tipos_de_registros_de_launcher.add("../../logs/ftb-app-electron.log");
		limpiadores.add(new LimpiadorRegistroDeLauncherVainilla());
		limpiadores.add(new LimpiadorRegistroLatestLog());
	}

	public Consola(Path archivo) throws IOException {
		super();
		this.archivo = archivo;
		linea_original = 0;
		archivos_en_lista.add(archivo.toFile());

		
		
		for(DivisorDeArchivos div:divisores) {
			if(div.predicado(archivo)) {
				String contento_existe = MonitorDePID.leer_archivo(archivo);
				linea_original=div.obtenerLineaOriginal(contento_existe);
			}
		}

	}

	public void finalizarContenido(Instant tiempo, boolean ignorar_necesita_estar_despues_de_tiempo) {
		try {

			Instant epoc = Instant.ofEpochMilli(archivo.toFile().lastModified());

			if (epoc.isAfter(tiempo) || ignorar_necesita_estar_despues_de_tiempo || nueva) {
				nueva = true;
				contenido = MonitorDePID.leer_archivo(archivo);

				StringBuilder para_verificar = new StringBuilder();
				String[] lineas = contenido.split(File.pathSeparator);
				for (int i = linea_original; i < lineas.length - 1; i++) {
					para_verificar.append(lineas[i]).append(File.pathSeparator);
				}

				CrashDetectorLogger.log("archivo nombre: "+archivo.toString());
				boolean limpiado=false;
				for(LimipiadorDeRegistro limp:limpiadores) {
					if(limp.predicado(archivo)) {
						contenido_verificar=limp.limpiarConsola(para_verificar.toString());
					}
				}
				if (!limpiado)  {
					contenido_verificar = para_verificar.toString();
				}

				if (contenido_verificar.contains(MonitorDePID.mensaje_de_registro_lanzer_completo)) {
					MonitorDePID.tiene_mensaje_de_registro_launcher_completa = true;
				}

			} else {
				nueva = false;
				contenido = "";
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void finalizarContenidoInyectado(String contento) {
		nueva = true;
		this.contenido = contento;

		CrashDetectorLogger.log("archivo nombre inyectado: "+archivo.toString());
		
		boolean limpiado=false;
		for(LimipiadorDeRegistro limp:limpiadores) {
			if(limp.predicado(archivo)) {
				contenido_verificar=limp.limpiarConsola(contento);
			}
		}
		if (!limpiado)  {
			contenido_verificar = contento.toString();
		}

	
	}

	public static List<Consola> obtenerConsolas() {
		List<Consola> resulto = new ArrayList<Consola>();

		for (File archivo : obtenerArchivosDeConsolas()) {
			if (archivo.exists()) {
				if (!archivos_en_lista.contains(archivo)) {
					try {
						resulto.add(new Consola(archivo.toPath()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		return resulto;

	}

	public static List<File> obtenerArchivosDeConsolas() {
		List<File> resultado = new ArrayList<>();
		Set<String> rutasLauncherLog = new HashSet<>(); // Solo para launcher_log.txt
		String home = System.getProperty("user.home");
		String soporteAplicaciones = home + "/Library/Application Support/";
		String appdata = System.getenv("APPDATA");

		// Agregar logs principales con control de duplicados
		agregarLauncherLog(new File("launcher_log.txt"), resultado, rutasLauncherLog);
		agregarLauncherLog(new File("../../Install/launcher_log.txt"), resultado, rutasLauncherLog); // CurseForgeApp
		agregarLauncherLog(new File(soporteAplicaciones + "minecraft/launcher_log.txt"), resultado, rutasLauncherLog); // CurseForgeApp

		if (appdata != null) {
			agregarLauncherLog(new File(appdata + "/.minecraft/launcher_log.txt"), resultado, rutasLauncherLog); // CurseForgeApp
		}

		agregarDirectorio(resultado, new File("../.hmcl/logs/"));
		agregarDirectorio(resultado, new File(home + "/.hmcl/logs/"));
		if (appdata != null) {
			agregarDirectorio(resultado, new File(appdata + "/.hmcl/logs/"));
		}
		agregarDirectorio(resultado, obtanerHMCLDirMunidial().toFile());
//https://github.com/HMCL-dev/HMCL/issues/2663

		// Agregar otros logs sin control de duplicados
		agregarDirectorio(resultado, new File("logs/"));
		agregarDirectorio(resultado, new File("crash-reports/"));

		// Configuración de TLauncher
		File carpetaTLauncherStarter;
		File carpetaTLauncher;
		File archivoTLLegacy;
		if (appdata != null) {
			carpetaTLauncherStarter = new File(appdata + "/.tlauncher/logs/starter/");
			carpetaTLauncher = new File(appdata + "/.tlauncher/logs/tlauncher/");
			archivoTLLegacy = new File(appdata + "/.tlauncher/logs/launcher.log");
		} else if (new File(soporteAplicaciones + "/tlauncher/").exists()) {
			carpetaTLauncherStarter = new File(soporteAplicaciones + "/tlauncher/logs/starter/");
			carpetaTLauncher = new File(soporteAplicaciones + "/tlauncher/logs/tlauncher/");
			archivoTLLegacy = new File(soporteAplicaciones + "/tlauncher/logs/launcher.log");
		} else {
			carpetaTLauncherStarter = new File(home + "/.tlauncher/logs/starter/");
			carpetaTLauncher = new File(home + "/.tlauncher/logs/tlauncher/");
			archivoTLLegacy = new File(home + "/.tlauncher/logs/launcher.log");
		}

		agregarDirectorio(resultado, carpetaTLauncher);
		agregarDirectorio(resultado, carpetaTLauncherStarter);

		// Agregar otros archivos directamente
		agregarLauncherLog(new File(home + ".minecraft/launcher_log.txt"), resultado, rutasLauncherLog); // CurseForgeApp
																											// y TL
		String str_carpHMCL=Config.obtenerInstancia().obtenerCarpetaHMCL();
		if(!str_carpHMCL.isEmpty()) {
			File carpHMCL = new File(str_carpHMCL);
			agregarDirectorio(resultado, carpHMCL);
		}
		
		resultado.add(archivoTLLegacy);

		
		// segundo
		resultado.add(new File("../../logs/ftb-app-electron.log")); // FTB
		resultado.add(new File("sklauncher/sklauncher_logs.txt"));
		resultado.add(NoRegistroDeLauncher.cd_launcherlog);
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
	private static Path obtanerHMCLDirMunidial() {
		// Primero verificar propiedad del sistema
		String hmclHomeProp = System.getProperty("hmcl.home");
		if (hmclHomeProp != null) {
			return Paths.get(hmclHomeProp).toAbsolutePath().normalize();
		}

		// Lógica específica para Linux/BSD
		if (ES_LINUX_O_BSD) {
			String xdgData = System.getenv("XDG_DATA_HOME");
			if (xdgData != null && !xdgData.isEmpty()) {
				return Paths.get(xdgData, "hmcl").toAbsolutePath().normalize();
			}
			return Paths.get(System.getProperty("user.home"), ".local", "share", "hmcl").toAbsolutePath().normalize();
		}

		// Para Windows y macOS
		return Paths.get(System.getProperty("user.home"), ".hmcl").toAbsolutePath().normalize();
	}

	/**
	 * Agrega archivos de logs evitando duplicados solo para launcher_log.txt
	 */
	private static void agregarLauncherLog(File archivo, List<File> resultado, Set<String> rutas) {
		if (archivo.getName().equals("launcher_log.txt")) {
			try {
				String rutaCanonica = archivo.getCanonicalPath();
				if (!rutas.contains(rutaCanonica)) {
					resultado.add(archivo);
					rutas.add(rutaCanonica);
				}
			} catch (IOException e) {
				resultado.add(archivo);
			}
		} else {
			resultado.add(archivo);
		}
	}

	/**
	 * Agrega recursivamente todos los archivos de un directorio y sus
	 * subdirectorios sin control de duplicados, excluyendo archivos .gz
	 */
	public static void agregarDirectorio(List<File> resultado, File directorio) {
		if (!directorio.exists() || !directorio.isDirectory()) {
			return;
		}

		File[] archivos = directorio.listFiles();
		if (archivos == null)
			return;

		for (File archivo : archivos) {
			if (archivo.isDirectory()) {
				agregarDirectorio(resultado, archivo); // Llamada recursiva
			} else if (!archivo.getName().endsWith(".gz") && !archivo.getName().endsWith(".xz") && archivo.isFile()) {
				resultado.add(archivo);
			}
		}
	}

	public static List<Consola> reorganizar(List<Consola> consolas) {
		List<Consola> priorizadas = new ArrayList<>();
		List<Consola> normales = new ArrayList<>();

		for (Consola consola : consolas) {
			String nombreArchivo = consola.archivo.toString().toLowerCase();
			if (nombreArchivo.contains("crash-report")) {
				priorizadas.add(consola); // Archivos de crash van primero [[3]]
			} else {
				normales.add(consola); // El resto mantiene orden original
			}
		}

		// Combina listas: primero crash reports, luego normales [[1]]
		priorizadas.addAll(normales);
		return priorizadas;
	}

	// Para todos el code aqui,escribir otra vez estar mas simplicado pero hacer la
	// funcion misma
	public String obtainerEnlance() throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro {
		if (enlance != null) {
			CrashDetectorLogger.log("elance no es null");

			return enlance;
		} else {

			this.enlance = APIdeSitioDeRegistro.obtenerAPIdeConfig().publicarRegistro(this);
			return enlance;
		}
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
		boolean anon = Config.obtenerInstancia().esAnonimizarRegistros();
		if (anon) {
			return AnonimizadorDeRuta.anonimizarNombreDeUsuario(archivo.toString());
		}
		return archivo.toString();
	}

	public String obtainerContenidoParaPublicar() {
		boolean anon = Config.obtenerInstancia().esAnonimizarRegistros();
		if (anon) {
			return AnonimizadordeRegistros.anonimizar(this.contenido_verificar);
		}
		return contenido_verificar;
	}

	public String obtenerMensajeUltimaTrace() {
	    List<String> traces = VerificacionDeStackTrace.obtenerTraces(contenido_verificar);
	    if (!traces.isEmpty()) {
	        String ultimaTrace = traces.get(traces.size() - 1);
	        String[] lineas = ultimaTrace.split(VerificacionDeStackTrace.nl);
	        
	        // Buscar la primera línea con un mensaje real de error
	        for (String linea : lineas) {
	            String trimLinea = linea.trim();
	            if (trimLinea.isEmpty()) continue;
	            
	            // Saltar líneas de pila ("at ...")
	            if (trimLinea.startsWith("at ")) continue;
	            
	            // Saltar "Caused by" (se procesa en otro método)
	            if (trimLinea.contains("Caused by")) continue;
	            
	            // Devolver el mensaje limpio
	            return trimLinea;
	        }
	    }
	    return "";
	}
	
	
	
	public String obtainerMensajeFatalUltimaTrace() {
	    List<String> traces = VerificacionDeStackTrace.obtenerTracesFatal(contenido_verificar);
	    if (!traces.isEmpty()) {
	        String ultimaTrace = traces.get(traces.size() - 1);
	        String[] lineas = ultimaTrace.split(VerificacionDeStackTrace.nl);
	        
	        // Buscar el mensaje fatal en las líneas
	        for (String linea : lineas) {
	            String trimLinea = linea.trim();
	            if (trimLinea.isEmpty()) continue;
	            
	            // Saltar líneas de pila
	            if (trimLinea.startsWith("at ")) continue;
	            
	            // Saltar "Caused by"
	            if (trimLinea.contains("Caused by")) continue;
	            
	            // Devolver el mensaje limpio
	            return trimLinea;
	        }
	    }
	    return "";
	}
	
	
	
	
	
	

	
	
	
	

}
