package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
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
import com.asbestosstar.crashdetector.gui.NoRegistroDeLauncher;
import com.asbestosstar.crashdetector.limpiador.LimpiadorRegistroDeLauncherVainilla;

public class Consola {

	/**
	 * NO USAS DESPUES DE finalizarContento
	 */
	public String contento;

	public Path archivo;
	public String enlance;
	public int linea_original;
	public boolean nueva = false;
	public String contento_verificar;

	public VerificacionDeStackTrace verificacion_de_stacktrace;

	public static ArrayList<File> archivos_en_lista = new ArrayList<File>();
	public static String[] tipos_de_registros_de_launcher = { "../../logs/ftb-app-electron.log",
			"../../../../main.log" };// No para registros con "launcher en el nombre"

	public static SecureLoggerAPI secure_logger_api = new SecureLoggerAPI();

	static { // APIS Por Defecto
		APIdeSitioDeRegistro.APIS.add(secure_logger_api);
		APIdeSitioDeRegistro.APIS.add(new StikkedAPI());
		APIdeSitioDeRegistro.APIS.add(new MCLogsAPI());
	}

	public Consola(Path archivo) throws IOException {
		super();
		this.archivo = archivo;
		linea_original = 0;
		archivos_en_lista.add(archivo.toFile());

		if (archivo.toString().contains("tlauncher") && !archivo.toString().contains("starter")) {
			String contento_existe = MonitorDePID.leer_archivo(archivo);
			String[] lineas = contento_existe.split(File.pathSeparator);
			for (int i = 0; i < lineas.length - 1; i++) {
				String lin = lineas[i];
				if (lin.contains("[Launcher] Launching Minecraft...")) {
					linea_original = i;
				}

			}

		} else if (archivo.toString().contains("launcher_log")) {
			String contento_existe = MonitorDePID.leer_archivo(archivo);
			String[] lineas = contento_existe.split(File.pathSeparator);
			linea_original = lineas.length;
			System.out.println("DEBUG Linea de launcher_log es " + String.valueOf(linea_original));
		}

	}

	public void finalizarContento(Instant tiempo, boolean ignorar_necesita_estar_despues_de_tiempo) {
		try {

			Instant epoc = Instant.ofEpochMilli(archivo.toFile().lastModified());

			if (epoc.isAfter(tiempo) || ignorar_necesita_estar_despues_de_tiempo || nueva) {
				nueva = true;
				contento = MonitorDePID.leer_archivo(archivo);

				StringBuilder para_verificar = new StringBuilder();
				String[] lineas = contento.split(File.pathSeparator);
				for (int i = linea_original; i < lineas.length - 1; i++) {
					para_verificar.append(lineas[i]).append(File.pathSeparator);
				}

				if(archivo.toString().endsWith("launcher_log.txt")) {
					contento_verificar = LimpiadorRegistroDeLauncherVainilla.limpiarConsola(para_verificar.toString());
				}else if(archivo.toString().endsWith("latest.log")) {
					contento_verificar = LimpiadorRegistroLatestLog.limpiarConsola(para_verificar.toString());
				}
				else {
				contento_verificar = para_verificar.toString();
				}
				
			if(contento_verificar.contains(MonitorDePID.mensaje_de_registro_launcher_completa)) {
				MonitorDePID.tiene_mensaje_de_registro_launcher_completa=true;
			}	
				
			
			} else {
				nueva = false;
				contento = "";
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void finalizarContentoInyectado(String contento) {
				nueva = true;
				this.contento=contento;
				contento_verificar = contento;
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
	            //resultado.add(new File(appdata + "/AtLauncher/logs/atlauncher.log")); // ATLauncher DOS
	        }

	        // Agregar otros logs sin control de duplicados
	        agregarDirectorio(resultado, new File("logs/"));
	        agregarDirectorio(resultado, new File("crash-reports/"));

	        // Configuración de TLauncher
	        File carpetaTLauncherStarter;
	        File carpetaTLauncher;
	        if (appdata != null) { 
	            carpetaTLauncherStarter = new File(appdata + "/.tlauncher/logs/starter/");
	            carpetaTLauncher = new File(appdata + "/.tlauncher/logs/tlauncher/");
	        } else if (new File(soporteAplicaciones + "/tlauncher/").exists()) {
	            carpetaTLauncherStarter = new File(soporteAplicaciones + "/tlauncher/logs/starter/");
	            carpetaTLauncher = new File(soporteAplicaciones + "/tlauncher/logs/tlauncher/");
	        } else {
	            carpetaTLauncherStarter = new File(home + "/.tlauncher/logs/starter/");
	            carpetaTLauncher = new File(home + "/.tlauncher/logs/tlauncher/");
	        }

	        agregarDirectorio(resultado, carpetaTLauncher);
	        agregarDirectorio(resultado, carpetaTLauncherStarter);

	        // Agregar otros archivos directamente
	        agregarLauncherLog(new File(home + ".minecraft/launcher_log.txt"), resultado, rutasLauncherLog); // CurseForgeApp y TL segundo
	        resultado.add(new File("../../logs/ftb-app-electron.log")); // FTB
	        //resultado.add(new File("../../logs/atlauncher.log")); // ATLauncher UNIX
	        //resultado.add(new File("../../../logs/PrismLauncher-0.log"));
	        //resultado.add(new File("../../../logs/PollyMC-0.log"));
	        //resultado.add(new File("../../../PolyMC-0.log"));
	        //resultado.add(new File("../../../UltimMC-0.log"));
	        //resultado.add(new File("../../../MultiMC-0.log"));
	        resultado.add(new File("sklauncher/sklauncher_logs.txt"));
	        //resultado.add(new File("../../../../main.log")); // GDLauncher
	        resultado.add(NoRegistroDeLauncher.cd_launcherlog);
	        resultado.add(new File("hs_err_pid" + String.valueOf(MonitorDePID.pid) + ".log")); // GDLauncher

	        return resultado;
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
	     * Agrega recursivamente todos los archivos de un directorio y sus subdirectorios
	     * sin control de duplicados, excluyendo archivos .gz
	     */
	    public static void agregarDirectorio(List<File> resultado, File directorio) {
	        if (!directorio.exists() || !directorio.isDirectory()) {
	            return;
	        }

	        File[] archivos = directorio.listFiles();
	        if (archivos == null) return;

	        for (File archivo : archivos) {
	            if (archivo.isDirectory()) {
	                agregarDirectorio(resultado, archivo); // Llamada recursiva
	            } else if (!archivo.getName().endsWith(".gz") && archivo.isFile()) {
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

			this.enlance = APIdeSitioDeRegistro.obtainerAPIdeConfig().publicarRegistro(this);
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
			for (String regdelauncher : Consola.tipos_de_registros_de_launcher) {
				if (nombre.equals(regdelauncher)) {
					return true;
				}
			}

		}
		return false;
	}

	public String obtainerRutaParaPublicar() {
		boolean anon = Config.obtenerInstancia().esAnonimizarRegistros();
		if (anon) {
			return AnonimizadorDeRuta.anonimizarNombreDeUsuario(archivo.toString());
		}
		return archivo.toString();
	}

	public String obtainerContentoParaPublicar() {
		boolean anon = Config.obtenerInstancia().esAnonimizarRegistros();
		if (anon) {
			return AnonimizadordeRegistros.anonimizar(this.contento_verificar);
		}
		return contento_verificar;
	}
	
	public String obtainerMensajeUltimaTrace() {
	    List<String> traces = VerificacionDeStackTrace.obtenerTraces(contento_verificar);
	    if (!traces.isEmpty()) {
	        String ult = traces.get(traces.size() - 1);
	        String[] arr = ult.split(VerificacionDeStackTrace.nl);
	        return arr.length > 0 ? arr[0] : "";
	    }
	    return "";
	}

	public String obtainerMensajeFatalUltimaTrace() {
	    List<String> traces = VerificacionDeStackTrace.obtenerTracesFatal(contento_verificar);
	    if (!traces.isEmpty()) {
	        String ult = traces.get(traces.size() - 1);
	        String[] arr = ult.split(VerificacionDeStackTrace.nl);
	        return arr.length > 0 ? arr[0] : "";
	    }
	    return "";
	}
	

}
