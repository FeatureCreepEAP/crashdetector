package com.asbestosstar.crashdetector.config.hocon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

public class HoconConfigurateHocon implements com.asbestosstar.crashdetector.config.hocon.Hocon.Motor {

	@Override
	public String nombre() {
		return "configurate-hocon";
	}

	@Override
	public com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo leer(String texto) {
		try {
			BufferedReader reader = new BufferedReader(new StringReader(texto));
			HoconConfigurationLoader loader = HoconConfigurationLoader.builder().source(() -> reader).build();

			CommentedConfigurationNode root = loader.load();
			return new com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo(root, this);
		} catch (Exception e) {
			throw new RuntimeException("No se pudo leer HOCON con configurate-hocon", e);
		}
	}

	@Override
	public com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo crear() {
		CommentedConfigurationNode root = CommentedConfigurationNode.root(ConfigurationOptions.defaults());
		return new com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo(root, this);
	}

	@Override
	public String escribir(com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo nodo) {
		try {
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);

			HoconConfigurationLoader loader = HoconConfigurationLoader.builder().sink(() -> writer).build();

			loader.save((CommentedConfigurationNode) nodo.interno);
			writer.flush();
			return stringWriter.toString();
		} catch (Exception e) {
			throw new RuntimeException("No se pudo escribir HOCON con configurate-hocon", e);
		}
	}

	@Override
	public List<String> claves(com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo nodo) {
		CommentedConfigurationNode node = (CommentedConfigurationNode) nodo.interno;
		List<String> keys = new ArrayList<>();
		node.childrenMap().keySet().forEach(k -> keys.add(String.valueOf(k)));
		return keys;
	}

	@Override
	public com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo obtener(
			com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo nodo, String clave) {

		CommentedConfigurationNode node = (CommentedConfigurationNode) nodo.interno;
		CommentedConfigurationNode child = node.node(clave);
		return new com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo(child, this, node, clave);
	}

	@Override
	public com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo poner(
			com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo nodo, Object valor) {

		CommentedConfigurationNode node = (CommentedConfigurationNode) nodo.interno;
		try {
			node.set(valor);
		} catch (SerializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodo;
	}

	@Override
	public String comoCadena(com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo nodo) {
		CommentedConfigurationNode node = (CommentedConfigurationNode) nodo.interno;
		Object val = node.raw();
		return val == null ? null : String.valueOf(val);
	}
}