package com.asbestosstar.crashdetector.cargador;

import com.asbestosstar.crashdetector.App;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

public class CargadorNXOpen implements Cargador{

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {
		// TODO Auto-generated method stub
		
		
		
	if(App.obtenerApp().equals(App.NXOPEN)) {//TODO verificar si tiene un punto de entrar correcto
		return true;
	}


	
	return false;
	}

	@Override
	public boolean cargadorEsActivado() {
		// TODO Auto-generated method stub
		return App.obtenerApp().equals(App.NXOPEN);//Pero CrashDetector solo es Para FeatureCreep para NXOpen
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "nxopen";
	}

}
