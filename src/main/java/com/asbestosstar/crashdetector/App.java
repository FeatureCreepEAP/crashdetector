package com.asbestosstar.crashdetector;

import java.util.ArrayList;
import java.util.List;
@FunctionalInterface
public interface App {

	public static final List<App> APPS = new ArrayList<App>();

	public static final App MINECRAFT = () ->{return "minecraft";};
	public static final App DANGERZONE = () ->{return "dangerzone";};
	//No hay versiones para HOI4/CLAWZWITZ ahora
	public static final App LIVE2D_CUBISM = () ->{return "live2d_cubism";};
	public static final App UNCIV = () ->{return "unciv";};
	public static final App NXOPEN = () ->{return "nxopen";};//Siemens NX / Unigraphics
	public static final App TEAMCENTER = () ->{return "teamcenter";};//Teamcenter Rich Client, un fork de Eclipse IDE
	
	
	
	public String id();



	public static App obtenerApp() {
		// TODO Auto-generated method stub
		App selecto = Statics.APP;
		if(selecto==null) {
			return MINECRAFT;//POR DEFECTO PARA AHORA
		}
		return selecto;
	}
	
}
