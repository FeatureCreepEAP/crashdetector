package com.asbestosstar.crashdetector.config;

import java.util.function.Supplier;

public interface ElementoConfig<T> {

	/**
	 * Clave en Archivo Config
	 * 
	 * @return
	 */
	public String clave();

	/**
	 * Valor de Elemento
	 * 
	 * @param por_defecto un valor si no hay un valor
	 * @return
	 */
	public T obtener();

	/**
	 * Escribir un valor nuevo
	 * 
	 * @param valor
	 */
	public void escribir(T valor);
	
	
	/**
	 * el nombre para el publico en la editor de configuraciones. Si no hay un valor devuelve la clave
	 * @return
	 */
	public String obtenerNombreParaMostrar();
	
	/**
	 * el nombre para el publico en la editor de configuraciones. preferemos un valor localizado e.g. () -> MonitorDePID.idioma.miNombreParaMostrar();
	 * @param nombre
	 */
	public void establecerNombreParaMostrar(Supplier<String> nombre);
	
}
