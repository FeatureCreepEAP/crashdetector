package com.asbestosstar.crashdetector.config;

import java.awt.Color;
import com.asbestosstar.crashdetector.Config;

public class ConfigColor implements ElementoConfig<Color> {

	private final String clave;

	private ConfigColor(String clave) {
		this.clave = clave;
	}

	@Override
	public String clave() {
		return clave;
	}

	@Override
	public Color obtener() {
		String valor = Config.obtenerInstancia().propiedadesConfig.getProperty(clave);
		if (valor == null) {
			throw new IllegalArgumentException("La clave de configuración no existe: " + clave);
		}
		try {
			return Config.convertirAColor(valor);
		} catch (Exception e) {
			throw new IllegalStateException("El valor de la clave no es un color válido: " + valor, e);
		}
	}

	@Override
	public void escribir(Color valor) {
		Config.obtenerInstancia().propiedadesConfig.setProperty(clave, Config.colorAHexHtml(valor));
		Config.obtenerInstancia().guardar();
	}

	/**
	 * Crea una instancia de ConfigColor para la clave indicada.
	 * 
	 * @param clave Nombre de la clave en el archivo de configuración.
	 * @return Nueva instancia de ConfigColor.
	 * @throws IllegalArgumentException Si la clave no existe.
	 * @throws IllegalStateException    Si el valor de la clave no representa un color válido.
	 */
	public static ConfigColor de(String clave) throws IllegalArgumentException, IllegalStateException {
		String valor = Config.obtenerInstancia().propiedadesConfig.getProperty(clave);

		if (valor == null) {
			throw new IllegalArgumentException("La clave de configuración no existe: " + clave);
		}

		// Verificar que el valor sea un color válido antes de crear la instancia
		try {
			Config.convertirAColor(valor);
		} catch (Exception e) {
			throw new IllegalStateException("El valor de la clave no representa un color válido: " + valor, e);
		}

		return new ConfigColor(clave);
	}
}
