package com.asbestosstar.crashdetector.config.toml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;

/**
 * TOML motor using ElectronWill NightConfig.
 */
public class TomlElectronWill implements Toml.Motor {

	private final TomlParser parser = new TomlParser();
	private final TomlWriter writer = new TomlWriter();

	@Override
	public String nombre() {
		return "electronwill-toml";
	}

	@Override
	public Toml.Nodo leer(String texto) {
		try {
			CommentedConfig config = parser.parse(texto);
			return new Toml.Nodo(config, this);
		} catch (Exception e) {
			throw new IllegalStateException("No se pudo leer TOML", e);
		}
	}

	@Override
	public Toml.Nodo crearObjeto() {
		return new Toml.Nodo(CommentedConfig.inMemory(), this);
	}

	@Override
	public Toml.Nodo crearArreglo() {
		return new Toml.Nodo(new ArrayList<>(), this);
	}

	@Override
	public String escribir(Toml.Nodo nodo) {
		try {
			if (nodo.interno instanceof Config) {
				return writer.writeToString((Config) nodo.interno);
			} else if (nodo.interno instanceof List) {
				// Write list as TOML array
				CommentedConfig tmp = CommentedConfig.inMemory();
				tmp.add("list", nodo.interno);
				return writer.writeToString(tmp);
			} else {
				return nodo.interno.toString();
			}
		} catch (Exception e) {
			throw new IllegalStateException("No se pudo escribir TOML", e);
		}
	}

	@Override
	public boolean esObjeto(Toml.Nodo nodo) {
		return nodo.interno instanceof Config || nodo.interno instanceof CommentedConfig;
	}

	@Override
	public boolean esArreglo(Toml.Nodo nodo) {
		return nodo.interno instanceof List;
	}

	@Override
	public List<String> claves(Toml.Nodo objeto) {
		List<String> salida = new ArrayList<>();
		if (objeto.interno instanceof Config) {
			salida.addAll(((Config) objeto.interno).valueMap().keySet());
		}
		return salida;
	}

	@Override
	public Toml.Nodo obtener(Toml.Nodo actual, String nombre) {
		if (!(actual.interno instanceof Config)) {
			throw new IllegalStateException("No es objeto");
		}

		Config cfg = (Config) actual.interno;
		Object valor = cfg.get(nombre);
		if (valor == null) {
			valor = CommentedConfig.inMemory();
			cfg.add(nombre, valor);
		}
		return new Toml.Nodo(valor, this, cfg, nombre, null);
	}

	@Override
	public boolean eliminar(Toml.Nodo actual, String nombre) {
		if (!(actual.interno instanceof Config)) {
			throw new IllegalStateException("No es objeto");
		}

		Config cfg = (Config) actual.interno;
		boolean existia = cfg.contains(nombre);
		cfg.remove(nombre);
		return existia;
	}

	@Override
	public Toml.Nodo poner(Toml.Nodo actual, Object valor) {
		actual.interno = valor;
		actualizarPadre(actual, valor);
		return actual;
	}

	@Override
	public Toml.Nodo agregar(Toml.Nodo actual, Object valor) {
		if (!(actual.interno instanceof List)) {
			throw new IllegalStateException("No es arreglo");
		}
		List<Object> lista = (List<Object>) actual.interno;
		lista.add(valor);
		return new Toml.Nodo(valor, this, lista, null, lista.size() - 1);
	}

	@Override
	public Toml.Nodo agregarNodo(Toml.Nodo actual, Toml.Nodo valorNodo) {
		return agregar(actual, valorNodo.interno);
	}

	@Override
	public int tamano(Toml.Nodo arreglo) {
		if (arreglo.interno instanceof List)
			return ((List<?>) arreglo.interno).size();
		if (arreglo.interno instanceof Config)
			return ((Config) arreglo.interno).valueMap().size();
		return 0;
	}

	@Override
	public Toml.Nodo en(Toml.Nodo arreglo, int indice) {
		if (!(arreglo.interno instanceof List))
			throw new IllegalStateException("No es arreglo");
		List<?> lista = (List<?>) arreglo.interno;
		return new Toml.Nodo(lista.get(indice), this, lista, null, indice);
	}

	@Override
	public String comoCadena(Toml.Nodo nodo) {
		return String.valueOf(nodo.interno);
	}

	@Override
	public int comoEntero(Toml.Nodo nodo) {
		return Integer.parseInt(nodo.interno.toString());
	}

	@Override
	public long comoLargo(Toml.Nodo nodo) {
		return Long.parseLong(nodo.interno.toString());
	}

	@Override
	public boolean comoBooleano(Toml.Nodo nodo) {
		return Boolean.parseBoolean(nodo.interno.toString());
	}

	@Override
	public double comoDouble(Toml.Nodo nodo) {
		return Double.parseDouble(nodo.interno.toString());
	}

	private void actualizarPadre(Toml.Nodo nodo, Object valor) {
		if (nodo.padre instanceof Config && nodo.claveEnPadre != null) {
			((Config) nodo.padre).add(nodo.claveEnPadre, valor);
		} else if (nodo.padre instanceof List && nodo.indiceEnPadre != null) {
			((List<Object>) nodo.padre).set(nodo.indiceEnPadre, valor);
		}
	}
}