package com.asbestosstar.crashdetector.config.json5;

import java.io.StringReader;
import java.io.StringWriter;
import com.asbestosstar.crashdetector.config.json.Json;

import de.marhali.json5.Json5Array;
import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Null;
import de.marhali.json5.Json5Object;
import de.marhali.json5.Json5Primitive;
import de.marhali.json5.config.Json5Options;
import de.marhali.json5.stream.Json5Lexer;
import de.marhali.json5.stream.Json5Parser;
import de.marhali.json5.stream.Json5Writer;

public class Json5Motor implements Json5.Motor {

	private final Json5Options options = Json5Options.DEFAULT;

	@Override
	public String nombre() {
		return "json5-java";
	}

	@Override
	public Json.Nodo leer(String texto) {
		try {
			Json5Lexer lexer = new Json5Lexer(new StringReader(texto), options);
			Json5Element element = Json5Parser.parse(lexer);
			if (element == null)
				element = new Json5Object();
			return new Json.Nodo(element, null);
		} catch (Exception e) {
			throw new IllegalStateException("Error leyendo JSON5", e);
		}
	}

	@Override
	public Json.Nodo crearObjeto() {
		return new Json.Nodo(new Json5Object(), null);
	}

	@Override
	public String escribir(Json.Nodo nodo) {
		try {
			StringWriter sw = new StringWriter();
			Json5Writer writer = new Json5Writer(options, sw);
			writer.write((Json5Element) nodo.interno);
			return sw.toString();
		} catch (Exception e) {
			throw new IllegalStateException("Error escribiendo JSON5", e);
		}
	}

	// Optional utility to convert Json5Element to Java types
	public static Object convertirAJava(Json5Element element) {
		if (element == null || element instanceof Json5Null)
			return null;
		if (element.isJson5Primitive()) {
			Json5Primitive p = element.getAsJson5Primitive();
			if (p.isBoolean())
				return p.getAsBoolean();
			if (p.isString())
				return p.getAsString();
			if (p.isNumber())
				return p.getAsNumber();
			return p.getAsString();
		}
		if (element.isJson5Array()) {
			java.util.List<Object> list = new java.util.ArrayList<>();
			for (Json5Element e : element.getAsJson5Array())
				list.add(convertirAJava(e));
			return list;
		}
		if (element.isJson5Object())
			return element.getAsJson5Object().asMap();
		return null;
	}
}