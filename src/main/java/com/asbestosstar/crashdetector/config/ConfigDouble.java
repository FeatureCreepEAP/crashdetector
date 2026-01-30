package com.asbestosstar.crashdetector.config;

import java.util.function.Supplier;

import com.asbestosstar.crashdetector.Config;

public class ConfigDouble implements ElementoConfig<Double> {

	private final String clave;
	public Supplier<String> nombre;
	public double def;

	private ConfigDouble(String clave, double def) {
		this.clave = clave;
		this.def = def;
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
	 * Crea una instancia de ConfigDouble para la clave indicada. Si la clave no
	 * existe, se crea automáticamente con el valor por defecto.
	 * 
	 * @param clave           Nombre de la clave en el archivo de configuración.
	 * @param valorPorDefecto Valor numérico a usar si la clave no existe.
	 * @return Nueva instancia de ConfigDouble.
	 * @throws IllegalStateException Si la clave existe pero su valor no representa
	 *                               un número válido.
	 */
	public static ConfigDouble de(String clave, double valorPorDefecto) throws IllegalStateException {
		Config config = Config.obtenerInstancia();
		String valor = config.propiedadesConfig.getProperty(clave);

		if (valor == null) {
			// Crear la clave con valor por defecto
			config.propiedadesConfig.setProperty(clave, Double.toString(valorPorDefecto));
			config.guardar();
			return new ConfigDouble(clave, valorPorDefecto);
		}

		// Verificar que el valor existente sea un número válido
		try {
			Double.parseDouble(valor.trim());
		} catch (NumberFormatException e) {
			throw new IllegalStateException("El valor de la clave no representa un número válido: " + valor, e);
		}

		return new ConfigDouble(clave, valorPorDefecto);
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

	@Override
	public Double obtenerValorPorDefecto() {
		// TODO Auto-generated method stub
		return def;
	}
}
