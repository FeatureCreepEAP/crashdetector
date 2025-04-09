package com.asbestosstar.crashdetectormc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetectormc.analyzador.Analyzador;
import com.asbestosstar.crashdetectormc.analyzador.Verificaciones;

public class Consola {

	public String contento;
	public Path archivo;

	public Consola(Path archivo) throws IOException {
		super();
		this.archivo = archivo;
		this.contento = MonitorDePID.leer_archivo(archivo);
	}

	public static List<Consola> obtainerConsolas(Instant tiempo) {
		List<Consola> resulto = new ArrayList<Consola>();

		for (File archivo : obtainerArchivosDeConsolas()) {
			if (archivo.exists()) {

				Instant epoc = Instant.ofEpochMilli(archivo.lastModified());

				if (epoc.isAfter(tiempo)) {
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

	public static List<File> obtainerArchivosDeConsolas() {
		List<File> resulto = new ArrayList<File>();

		File carpetaLogs = new File("logs/");
		File carpetaCrashReports = new File("crash-reports/");

		if (carpetaLogs.exists()) {
			for (File archivo : carpetaLogs.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz")) {
					resulto.add(archivo);
				}
			}
		}

		if (carpetaCrashReports.exists()) {
			for (File archivo : carpetaCrashReports.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz")) {
					resulto.add(archivo);
				}
			}
		}
		String home = System.getProperty("user.home");
		String applicationSupport = home + "/Library/Application Support/";
		String appdata = System.getenv("APPDATA");

		File carpetaTLauncherStarter;
		File carpetaTLauncher;
		if (appdata != null) {// Windows
			carpetaTLauncherStarter = new File(appdata + "/.tlauncher/logs/starter/");
			carpetaTLauncher = new File(appdata + "/.tlauncher/logs/tlauncher/");
		} else if (new File(applicationSupport + "/tlauncher/").exists()) {
			carpetaTLauncherStarter = new File(applicationSupport + "/tlauncher/logs/starter/");
			carpetaTLauncher = new File(applicationSupport + "/tlauncher/logs/tlauncher/");
		} else {
			carpetaTLauncherStarter = new File(home + "/tlauncher/logs/starter/");
			carpetaTLauncher = new File(home + "/tlauncher/logs/tlauncher/");
		}

		if (carpetaTLauncher.exists()) {
			for (File archivo : carpetaTLauncher.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz")) {
					resulto.add(archivo);
				}
			}
		}
		if (carpetaTLauncherStarter.exists()) {
			for (File archivo : carpetaTLauncherStarter.listFiles()) {
				if (!archivo.getAbsolutePath().endsWith(".gz")) {
					resulto.add(archivo);
				}
			}
		}

		if (appdata != null) {
			resulto.add(new File(appdata + "/AtLauncher/logs/atlauncher.log"));// ATLauncher DOS
			resulto.add(new File(appdata + "/.minecraft/launcher_log.txt"));// CurseForgeApp
		}

		resulto.add(new File("launcher_log.txt"));
		resulto.add(new File("../../Install/launcher_log.txt"));// CurseForgeApp
		resulto.add(new File(applicationSupport + "minecraft/launcher_log.txt"));// CurseForgeApp
		resulto.add(new File(home + ".minecraft/launcher_log.txt"));// CurseForgeApp

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

		return resulto;

	}

	public void analyzar(StringBuilder constructor) {
		constructor.append(archivo.getFileName());
		for(Verificaciones verificacion:Analyzador.verificaciones) {
			verificacion.nueva().verificar(contento, constructor);
		}	
	}

}
