package com.asbestosstar.crashdetector.config;

import java.util.function.Supplier;

import com.asbestosstar.crashdetector.Config;

public class ConfigBoolean implements ElementoConfig<Boolean> {

	private final String clave;
	public Supplier<String> nombre;

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
	 * Crea una instancia de ConfigBoolean para la clave indicada. Si la clave no
	 * existe, se crea con el valor por defecto proporcionado.
	 * 
	 * @param clave           Nombre de la clave en el archivo de configuración.
	 * @param valorPorDefecto Valor booleano a usar si la clave no existe.
	 * @return Nueva instancia de ConfigBoolean.
	 * @throws IllegalStateException Si la clave existe pero el valor no representa
	 *                               un booleano válido.
	 */
	public static ConfigBoolean de(String clave, boolean valorPorDefecto) throws IllegalStateException {
		Config config = Config.obtenerInstancia();

		String valor = config.propiedadesConfig.getProperty(clave);

		if (valor == null) {
			// Crear con el valor por defecto y guardar
			config.propiedadesConfig.setProperty(clave, Boolean.toString(valorPorDefecto));
			config.guardar();
			return new ConfigBoolean(clave);
		}

		// Validar que el valor existente sea un booleano válido
		String trimmed = valor.trim().toLowerCase();
		if (!trimmed.equals("true") && !trimmed.equals("false")) {
			throw new IllegalStateException("El valor de la clave no representa un booleano válido: " + valor);
		}

		return new ConfigBoolean(clave);
	}

	@Override
	public String obtenerNombreParaMostrar() {
		// TODO Auto-generated method stub
		if (nombre != null && nombre.get() != null) {
			return nombre.get();
		}

		return clave;
	}

	@Override
	public void establecerNombreParaMostrar(Supplier<String> nombre) {
		// TODO Auto-generated method stub
		this.nombre = nombre;
	}
}
