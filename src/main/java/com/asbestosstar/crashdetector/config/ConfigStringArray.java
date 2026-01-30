package com.asbestosstar.crashdetector.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.Config;

/**
 * Elemento de configuración que representa un arreglo de Strings. Formato:
 * elementos separados por ';' con escape por '\'. - ';' -> "\;" - '\' -> "\\"
 */
public class ConfigStringArray implements ElementoConfig<List<String>> {

	private final String clave;
	public Supplier<String> nombre;
	public List<String> def;

	private ConfigStringArray(String clave, List<String> def) {
		this.clave = clave;
		this.def = def;
	}

	@Override
	public String clave() {
		return clave;
	}

	@Override
	public List<String> obtener() {
		String valor = Config.obtenerInstancia().propiedadesConfig.getProperty(clave);
		if (valor == null) {
			throw new IllegalArgumentException("La clave de configuración no existe: " + clave);
		}
		// Si existe pero está vacío, devolver lista vacía (no lanzar excepción).
		if (valor.isEmpty()) {
			return new ArrayList<>();
		}
		return decodeLista(valor);
	}

	@Override
	public void escribir(List<String> valores) {
		String joined = encodeLista(valores);
		Config.obtenerInstancia().propiedadesConfig.setProperty(clave, joined);
		Config.obtenerInstancia().guardar();
	}

	/**
	 * Crea un ConfigStringArray para la clave dada. Si la clave NO existe, la
	 * escribe inmediatamente con los valores por defecto (posible lista vacía).
	 */
	public static ConfigStringArray de(String clave, List<String> valoresPorDefecto) {
		Config config = Config.obtenerInstancia();
		String existente = config.propiedadesConfig.getProperty(clave);

		if (existente == null) {
			String joined = encodeLista(valoresPorDefecto);
			config.propiedadesConfig.setProperty(clave, joined);
			config.guardar();
		}
		return new ConfigStringArray(clave, valoresPorDefecto);
	}

	@Override
	public String obtenerNombreParaMostrar() {
		if (nombre != null && nombre.get() != null) {
			return nombre.get();
		}
		return clave;
	}

	@Override
	public void establecerNombreParaMostrar(Supplier<String> nombre) {
		this.nombre = nombre;
	}

	/*
	 * ========================= Helpers de codificación =========================
	 */

	private static String encodeLista(List<String> valores) {
		if (valores == null || valores.isEmpty())
			return "";
		List<String> partes = new ArrayList<>(valores.size());
		for (String s : valores) {
			partes.add(escape(s == null ? "" : s));
		}
		return String.join(";", partes);
	}

	private static List<String> decodeLista(String valor) {
		// Si está vacío, representa lista vacía
		if (valor.isEmpty())
			return new ArrayList<>();

		List<String> resultado = new ArrayList<>();
		StringBuilder actual = new StringBuilder();
		boolean escapando = false;

		for (int i = 0; i < valor.length(); i++) {
			char c = valor.charAt(i);
			if (escapando) {
				actual.append(c);
				escapando = false;
			} else if (c == '\\') {
				escapando = true;
			} else if (c == ';') {
				resultado.add(actual.toString());
				actual.setLength(0);
			} else {
				actual.append(c);
			}
		}
		// último segmento
		resultado.add(actual.toString());
		return resultado;
	}

	private static String escape(String s) {
		StringBuilder out = new StringBuilder(s.length());
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\\') {
				out.append("\\\\");
			} else if (c == ';') {
				out.append("\\;");
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	@Override
	public List<String> obtenerValorPorDefecto() {
		// TODO Auto-generated method stub
		return def;
	}
}
