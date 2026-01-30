package com.asbestosstar.crashdetector.config;

import java.util.function.Supplier;

public interface ElementoConfig<T> {

	/**
	 * Clave en el archivo de configuración.
	 *
	 * @return clave única del elemento
	 */
	public String clave();

	/**
	 * Obtiene el valor actual del elemento.
	 *
	 * @return valor actual
	 */
	public T obtener();

	/**
	 * Escribe un nuevo valor para el elemento.
	 *
	 * @param valor nuevo valor a guardar
	 */
	public void escribir(T valor);

	/**
	 * Obtiene el nombre que se muestra al público en el editor de configuración. Si
	 * no hay un nombre definido, se debe devolver la clave.
	 *
	 * @return nombre visible para el usuario
	 */
	public String obtenerNombreParaMostrar();

	/**
	 * Establece el nombre visible para el público en el editor de configuración.
	 * Normalmente se usa un valor localizado, por ejemplo: () ->
	 * MonitorDePID.idioma.miNombreParaMostrar()
	 *
	 * @param nombre proveedor del nombre a mostrar
	 */
	public void establecerNombreParaMostrar(Supplier<String> nombre);

	/**
	 * Devuelve el valor por defecto de este elemento de configuración. Este valor
	 * se utiliza cuando el usuario desea restablecer la configuración a su estado
	 * original.
	 *
	 * @return valor por defecto
	 */
	public T obtenerValorPorDefecto();

	/**
	 * Restablece el valor del elemento a su valor por defecto. Implementación por
	 * defecto que reutiliza {@link #obtenerValorPorDefecto()}.
	 */
	public default void resetearAPorDefecto() {
		escribir(obtenerValorPorDefecto());
	}
}
