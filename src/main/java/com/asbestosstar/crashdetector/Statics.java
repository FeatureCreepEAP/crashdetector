package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.detectorlanzer.LanzerOtra;

public class Statics {

	public static boolean cargador;
	public static Set<Path> carpetas_de_mods = new HashSet<Path>();
	public static App APP;// establar en comenzar, es muy importante, predeterminado es Minecraft pero
							// para FeatureCreep o Fabric necesita cambiar si no es Minecraft y en los
							// cargadores specificalmente para otras applicaciones

	public static List<String> JVM_ARGS = new ArrayList<>();
	public static List<String> APP_ARGS = new ArrayList<>();
	// SOLO PARA despues de Entregar en proceso CD

	public static long INICIO_DE_LA_APP;// Cuando la aplicacion iniciar originalmente

	public static File carpeta_como_archivo = new File("crash_detector/");
	public static Path carpeta = carpeta_como_archivo.toPath();
	public static File carpeta_mundial_como_archivo = new File(System.getProperty("user.home"), "crash_detector/");
	public static Path carpeta_mundial = carpeta_mundial_como_archivo.toPath();
	public static String lanzer_del_app = LanzerOtra.ID;

	public static boolean app_en_cdlauncher = false; // Si la app es en modo relanzer

	public static ConfigString nombre_cd = ConfigString.de("nombre_cd", "CrashDetector");
	public static String GIT = "https://pagure.io/CrashDetectorMC/blob/main/f/";
	public static String GIT_RAW = "https://pagure.io/CrashDetectorMC/raw/main/f/";
public static String ID_CURSEDFORGE="1224146";//TLMods es el mismo
public static String ID_MINECRAFTSTORAGE="1606";
	public static String ID_MCMODCN = "713629";
	
	
	
	
	/**
	 * PARA JPMS
	 */
	public static boolean jpms_forzar_profiler = false;
	public static boolean jpms_forzar_sampler = false;

	static {
		App.APPS.add(App.MINECRAFT);
		App.APPS.add(App.DANGERZONE);
		App.APPS.add(App.UNCIV);
		App.APPS.add(App.NXOPEN);
		App.APPS.add(App.TEAMCENTER);
		App.APPS.add(App.LIVE2D_CUBISM);
	}

}
