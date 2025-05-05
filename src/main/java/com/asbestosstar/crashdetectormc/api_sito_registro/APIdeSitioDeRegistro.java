package com.asbestosstar.crashdetectormc.api_sito_registro;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;

public interface APIdeSitioDeRegistro {
	
	List<APIdeSitioDeRegistro> APIS = new ArrayList<APIdeSitioDeRegistro>();
	
	

	
	public static APIdeSitioDeRegistro obtainerAPIdeConfig() throws NoAPIdeRegistro {
		
	String nom =	Config.obtenerInstancia().obtenerApiSeleccionada();
		for(APIdeSitioDeRegistro reg:APIS) {
			if(reg.nombre().equals(nom)) {
				return reg;
			}
		}
		
		throw new NoAPIdeRegistro();
	}
	
	
	public String nombre();
	
	/**
	 * 
	 * @return enlance TODO mas de uno
	 */
	public String publicarRegistro(Consola registro) throws DemasiadoGrande, ErrorConPublicar;
	
	public List<String> sitosPorDefecto();
	
	public static String sitioDeConfig() {
		return Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado();
	}

}
