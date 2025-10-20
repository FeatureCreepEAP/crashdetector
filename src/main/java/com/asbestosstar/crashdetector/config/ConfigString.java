package com.asbestosstar.crashdetector.config;

import com.asbestosstar.crashdetector.Config;

public class ConfigString implements ElementoConfig<String> {

	private final String clave;

	private ConfigString(String clave) {
		this.clave = clave;
	}

	@Override
	public String clave() {
		return clave;
	}

	@Override
	public String obtener() {
		String valor = Config.obtenerInstancia().propiedadesConfig.getProperty(clave);
		if (valor == null) {
			throw new IllegalArgumentException("La clave de configuración no existe: " + clave);
		}
		return valor;
	}

	@Override
	public void escribir(String valor) {
		Config.obtenerInstancia().propiedadesConfig.setProperty(clave, valor);
		Config.obtenerInstancia().guardar();
	}

	/**
	 * Crea una instancia de ConfigString para la clave indicada. Si la clave no
	 * existe, se crea automáticamente con el valor por defecto.
	 * 
	 * @param clave           Nombre de la clave en el archivo de configuración.
	 * @param valorPorDefecto Valor de texto por defecto que se usará si la clave no
	 *                        existe.
	 * @return Nueva instancia de ConfigString.
	 */
	public static ConfigString de(String clave, String valorPorDefecto) {
		Config config = Config.obtenerInstancia();
		String valor = config.propiedadesConfig.getProperty(clave);

		if (valor == null) {
			// Crear con valor por defecto y guardar
			config.propiedadesConfig.setProperty(clave, valorPorDefecto);
			config.guardar();
			return new ConfigString(clave);
		}

		return new ConfigString(clave);
	}
}
