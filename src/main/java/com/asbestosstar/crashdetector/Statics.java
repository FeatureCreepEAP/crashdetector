package com.asbestosstar.crashdetector;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Statics {

	public static boolean cargador;
	public static Set<Path> carpetas_de_mods = new HashSet<Path>();
	public static App APP;//establar en comenzar, es muy importante, predeterminado es Minecraft pero para FeatureCreep o Fabric necesita cambiar si no es Minecraft y en los cargadores specificalmente para otras applicaciones
	public static String ARGS_DE_APP;//SOLO PARA despues de Entregar en proceso CD
	
	
	
	static {
		App.APPS.add(App.MINECRAFT);
		App.APPS.add(App.DANGERZONE);
		App.APPS.add(App.UNCIV);
		App.APPS.add(App.NXOPEN);
		App.APPS.add(App.TEAMCENTER);
	}
	
	
	
}
