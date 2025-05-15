package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.anon.AnonimizadorDeRuta;
import com.asbestosstar.crashdetector.anon.AnonimizadordeRegistros;
import com.asbestosstar.crashdetectormc.analyzador.Analizador;
import com.asbestosstar.crashdetectormc.analyzador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetectormc.analyzador.Verificaciones;
import com.asbestosstar.crashdetectormc.api_sito_registro.APIdeSitioDeRegistro;
import com.asbestosstar.crashdetectormc.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetectormc.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetectormc.api_sito_registro.MCLogsAPI;
import com.asbestosstar.crashdetectormc.api_sito_registro.NoAPIdeRegistro;
import com.asbestosstar.crashdetectormc.api_sito_registro.SecureLoggerAPI;
import com.asbestosstar.crashdetectormc.api_sito_registro.StikkedAPI;
import com.asbestosstar.crashdetectormc.gui.NoRegistroDeLauncher;

public class Consola {

	/**
	 * NO USAS DESPUES DE finalizarContento
	 */
	public String contento;

	public Path archivo;
	public String enlance;
	public int linea_original;
	public boolean nueva;
	public String contento_verificar;

	public VerificacionDeStackTrace verificacion_de_stacktrace = new VerificacionDeStackTrace(this);

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

	public void finalizarContento(Instant tiempo) {
		try {

			Instant epoc = Instant.ofEpochMilli(archivo.toFile().lastModified());

			if (epoc.isAfter(tiempo)) {
				nueva = true;
				contento = MonitorDePID.leer_archivo(archivo);

				StringBuilder para_verificar = new StringBuilder();
				String[] lineas = contento.split(File.pathSeparator);
				for (int i = linea_original; i < lineas.length - 1; i++) {
					para_verificar.append(lineas[i]).append(File.pathSeparator);
				}

				contento_verificar = para_verificar.toString();
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
		List<File> resulto = new ArrayList<File>();
		String home = System.getProperty("user.home");
		String applicationSupport = home + "/Library/Application Support/";
		String appdata = System.getenv("APPDATA");

		resulto.add(new File("launcher_log.txt"));
		resulto.add(new File("../../Install/launcher_log.txt"));// CurseForgeApp
		resulto.add(new File(applicationSupport + "minecraft/launcher_log.txt"));// CurseForgeApp

		if (appdata != null) {
			resulto.add(new File(appdata + "/AtLauncher/logs/atlauncher.log"));// ATLauncher DOS
			resulto.add(new File(appdata + "/.minecraft/launcher_log.txt"));// CurseForgeApp
		}

		File carpetaLogs = new File("logs/");
		File carpetaCrashReports = new File("crash-reports/");

		if (carpetaLogs.exists()) {
			for (File archivo : carpetaLogs.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz") && archivo.isFile()) {// TODO recursiv
					resulto.add(archivo);
				}
			}
		}

		if (carpetaCrashReports.exists()) {
			for (File archivo : carpetaCrashReports.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz") && archivo.isFile()) {
					resulto.add(archivo);
				}
			}
		}

		File carpetaTLauncherStarter;
		File carpetaTLauncher;
		if (appdata != null) {// Windows
			carpetaTLauncherStarter = new File(appdata + "/.tlauncher/logs/starter/");
			carpetaTLauncher = new File(appdata + "/.tlauncher/logs/tlauncher/");
		} else if (new File(applicationSupport + "/tlauncher/").exists()) {
			carpetaTLauncherStarter = new File(applicationSupport + "/tlauncher/logs/starter/");
			carpetaTLauncher = new File(applicationSupport + "/tlauncher/logs/tlauncher/");
		} else {
			carpetaTLauncherStarter = new File(home + "/.tlauncher/logs/starter/");
			carpetaTLauncher = new File(home + "/.tlauncher/logs/tlauncher/");
		}

		if (carpetaTLauncher.exists()) {
			for (File archivo : carpetaTLauncher.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz") && archivo.isFile()) {
					resulto.add(archivo);
				}
			}
		}
		if (carpetaTLauncherStarter.exists()) {
			for (File archivo : carpetaTLauncherStarter.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz") && archivo.isFile()) {
					resulto.add(archivo);
				}
			}
		}

		resulto.add(new File(home + ".minecraft/launcher_log.txt"));// CurseForgeApp y TL segundo

		resulto.add(new File("../../logs/ftb-app-electron.log"));// FTB
		resulto.add(new File("../../logs/atlauncher.log"));// ATLauncher UNIX

		resulto.add(new File("../../../logs/PrismLauncher-0.log"));
		resulto.add(new File("../../../logs/PollyMC-0.log"));
		resulto.add(new File("../../../PolyMC-0.log"));
		resulto.add(new File("../../../UltimMC-0.log"));
		resulto.add(new File("../../../MultiMC-0.log"));
//TODO LauncherFenix

		resulto.add(new File("sklauncher/sklauncher_logs.txt"));

		resulto.add(new File("../../../../main.log"));// GDLauncher

		resulto.add(NoRegistroDeLauncher.cd_launcherlog);

		resulto.add(new File("hs_err_pid" + String.valueOf(MonitorDePID.pid) + ".log"));// GDLauncher

		return resulto;

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

}
