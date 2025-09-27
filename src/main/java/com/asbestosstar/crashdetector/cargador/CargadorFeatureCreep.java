package com.asbestosstar.crashdetector.cargador;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

public class CargadorFeatureCreep implements Cargador{

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {
		// TODO Auto-generated method stub
		return true;//FeatureCreep puede leer todos para ahora
	}

	@Override
	public boolean cargadorEsActivado() {
		// TODO Auto-generated method stub
		return Cargador.claseExiste("featurecreep.loader.FCLoaderBasic");
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "featurecreep";
	}

}
