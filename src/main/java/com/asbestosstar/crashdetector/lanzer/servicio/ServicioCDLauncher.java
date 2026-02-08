package com.asbestosstar.crashdetector.lanzer.servicio;

import java.lang.instrument.Instrumentation;

public interface ServicioCDLauncher {

	/**
	 * 
	 * @param inst
	 */
	public void activar(Instrumentation inst);
	
	/**
	 * Mimo ID de ConfigCDLauncher
	 * @return
	 */
public String id();	
	
	
}
