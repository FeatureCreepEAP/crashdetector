package com.asbestosstar.crashdetector.canario;

import java.util.HashSet;
import java.util.Set;

public interface CanarioDeOrdenJudicial {

	public static final Set<CanarioDeOrdenJudicial> canarios = new HashSet<>();

	/**
	 * true si el servico es seguro y sin un orden judicial
	 * 
	 * @return
	 */
	public boolean seguro();

	/**
	 * Nombre de servicio
	 * 
	 * @return
	 */
	public String nombre_de_servicio();

}
