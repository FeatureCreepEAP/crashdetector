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
	 * Crea una instancia de ConfigColor para la clave indicada. Si la clave no
	 * existe, se crea automáticamente con el color por defecto.
	 * 
	 * @param clave           Nombre de la clave en el archivo de configuración.
	 * @param valorPorDefecto Color a usar si la clave no existe.
	 * @return Nueva instancia de ConfigColor.
	 * @throws IllegalStateException Si el valor existente no representa un color
	 *                               válido.
	 */
	public static ConfigColor de(String clave, Color valorPorDefecto) throws IllegalStateException {
		Config config = Config.obtenerInstancia();
		String valor = config.propiedadesConfig.getProperty(clave);

		if (valor == null) {
			// Crear con el valor por defecto y guardar
			config.propiedadesConfig.setProperty(clave, Config.colorAHexHtml(valorPorDefecto));
			config.guardar();
			return new ConfigColor(clave);
		}

		// Verificar que el valor existente sea un color válido
		try {
			Config.convertirAColor(valor);
		} catch (Exception e) {
			throw new IllegalStateException("El valor de la clave no representa un color válido: " + valor, e);
		}

		return new ConfigColor(clave);
	}
}
