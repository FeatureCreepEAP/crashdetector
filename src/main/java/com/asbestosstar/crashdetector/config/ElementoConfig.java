package com.asbestosstar.crashdetector.config;

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
}
