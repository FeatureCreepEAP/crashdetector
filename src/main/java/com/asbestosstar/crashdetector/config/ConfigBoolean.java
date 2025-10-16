package com.asbestosstar.crashdetector.config;

import com.asbestosstar.crashdetector.Config;

public class ConfigBoolean implements ElementoConfig<Boolean> {

	private final String clave;

	private ConfigBoolean(String clave) {
		this.clave = clave;
	}

	@Override
	public String clave() {
		return clave;
	}

	@Override
	public Boolean obtener() {
		String valor = Config.obtenerInstancia().propiedadesConfig.getProperty(clave);
		if (valor == null) {
			throw new IllegalArgumentException("La clave de configuración no existe: " + clave);
		}

		String trimmed = valor.trim().toLowerCase();

		if (!trimmed.equals("true") && !trimmed.equals("false")) {
			throw new IllegalStateException("El valor de la clave no es un booleano válido: " + valor);
		}

		return Boolean.parseBoolean(trimmed);
	}

	@Override
	public void escribir(Boolean valor) {
		Config.obtenerInstancia().propiedadesConfig.setProperty(clave, Boolean.toString(valor));
		Config.obtenerInstancia().guardar();
	}

	/**
	 * Crea una instancia de ConfigBoolean para la clave indicada.
	 * 
	 * @param clave Nombre de la clave en el archivo de configuración.
	 * @return Nueva instancia de ConfigBoolean.
	 * @throws IllegalArgumentException Si la clave no existe.
	 * @throws IllegalStateException    Si el valor de la clave no representa un booleano válido.
	 */
	public static ConfigBoolean de(String clave) throws IllegalArgumentException, IllegalStateException {
		String valor = Config.obtenerInstancia().propiedadesConfig.getProperty(clave);

		if (valor == null) {
			throw new IllegalArgumentException("La clave de configuración no existe: " + clave);
		}

		String trimmed = valor.trim().toLowerCase();
		if (!trimmed.equals("true") && !trimmed.equals("false")) {
			throw new IllegalStateException("El valor de la clave no representa un booleano válido: " + valor);
		}

		return new ConfigBoolean(clave);
	}
}
