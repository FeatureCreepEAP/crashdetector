package com.asbestosstar.crashdetector.cargador;

import com.asbestosstar.crashdetector.App;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

public class CargadorDangerZone implements Cargador{

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {
		// TODO Auto-generated method stub
		
		
		
for(String str : mod.archivos()) {
	if(str.endsWith("ModMain.class")&&App.obtenerApp().equals(App.DANGERZONE)) {//TODO verificar si extendar BaseMod
		return true;
	}
}

	
	return false;
	}

	@Override
	public boolean cargadorEsActivado() {
		// TODO Auto-generated method stub
		return App.obtenerApp().equals(App.DANGERZONE);//Pero CrashDetector solo es Para FeatureCreep para DZ
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "dangerzone";
	}

}
