package com.asbestosstar.crashdetector.config;

import com.asbestosstar.crashdetector.Config;

public class ConfigDouble implements ElementoConfig<Double> {

	private final String clave;

	private ConfigDouble(String clave) {
		this.clave = clave;
	}

	@Override
	public String clave() {
		return clave;
	}

	@Override
	public Double obtener() {
		String valor = Config.obtenerInstancia().propiedadesConfig.getProperty(clave);
		if (valor == null) {
			throw new IllegalArgumentException("La clave de configuración no existe: " + clave);
		}
		try {
			return Double.parseDouble(valor.trim());
		} catch (NumberFormatException e) {
			throw new IllegalStateException("El valor de la clave no es un número válido: " + valor, e);
		}
	}

	@Override
	public void escribir(Double valor) {
		Config.obtenerInstancia().propiedadesConfig.setProperty(clave, Double.toString(valor));
		Config.obtenerInstancia().guardar();
	}

	/**
	 * Crea una instancia de ConfigDouble para la clave indicada.
	 * 
	 * @param clave Nombre de la clave en el archivo de configuración.
	 * @return Nueva instancia de ConfigDouble.
	 * @throws IllegalArgumentException Si la clave no existe.
	 * @throws IllegalStateException    Si el valor de la clave no puede convertirse a Double.
	 */
	public static ConfigDouble de(String clave) throws IllegalArgumentException, IllegalStateException {
		String valor = Config.obtenerInstancia().propiedadesConfig.getProperty(clave);

		if (valor == null) {
			throw new IllegalArgumentException("La clave de configuración no existe: " + clave);
		}

		// Verificar que el valor sea un número válido antes de crear la instancia
		try {
			Double.parseDouble(valor.trim());
		} catch (NumberFormatException e) {
			throw new IllegalStateException("El valor de la clave no representa un número válido: " + valor, e);
		}

		return new ConfigDouble(clave);
	}
}
