package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.detectorlanzer.LanzerOtra;

public class Statics {

	public static boolean cargador;
	public static Set<Path> carpetas_de_mods = new HashSet<Path>();
	public static App APP;// establar en comenzar, es muy importante, predeterminado es Minecraft pero
							// para FeatureCreep o Fabric necesita cambiar si no es Minecraft y en los
							// cargadores specificalmente para otras applicaciones
	public static String ARGS_DE_APP;// SOLO PARA despues de Entregar en proceso CD

	public static File carpeta_como_archivo = new File("crash_detector/");
	public static Path carpeta = carpeta_como_archivo.toPath();
	public static String lanzer_del_app = LanzerOtra.ID;

	static {
		App.APPS.add(App.MINECRAFT);
		App.APPS.add(App.DANGERZONE);
		App.APPS.add(App.UNCIV);
		App.APPS.add(App.NXOPEN);
		App.APPS.add(App.TEAMCENTER);
		App.APPS.add(App.LIVE2D_CUBISM);
	}

}
