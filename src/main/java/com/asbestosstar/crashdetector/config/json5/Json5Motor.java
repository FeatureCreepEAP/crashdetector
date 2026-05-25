package com.asbestosstar.crashdetector.config.json5;

import java.io.StringReader;
import java.util.Map;

import com.asbestosstar.crashdetector.config.json.Json;

import de.marhali.json5.Json5Array;
import de.marhali.json5.Json5Element;
import de.marhali.json5.Json5Null;
import de.marhali.json5.Json5Object;
import de.marhali.json5.Json5Primitive;
import de.marhali.json5.config.Json5Options;
import de.marhali.json5.stream.Json5Lexer;
import de.marhali.json5.stream.Json5Parser;

/**
 * Motor JSON5 basado en json5-java.
 *
 * Importante:
 *
 * No devuelve new Json.Nodo(..., null).
 *
 * Primero parsea JSON5, luego convierte el arbol JSON5 a JSON normal y deja que
 * Json.leer(...) cree el Json.Nodo con su motor correcto.
 */
public class Json5Motor implements Json5.Motor {

	private final Json5Options options = Json5Options.DEFAULT;

	@Override
	public String nombre() {
		return "json5-java-sobre-" + Json.nombreMotor();
	}

	@Override
	public Json.Nodo leer(String texto) {
		try {
			Json5Lexer lexer = new Json5Lexer(new StringReader(texto), options);
			Json5Element element = Json5Parser.parse(lexer);

			if (element == null || element instanceof Json5Null) {
				element = new Json5Object();
			}

			String jsonNormal = convertirAJsonNormal(element);

			return Json.leer(jsonNormal);
		} catch (Exception e) {
			throw new IllegalStateException("Error leyendo JSON5", e);
		}
	}

	@Override
	public Json.Nodo crearObjeto() {
		return Json.crearObjeto();
	}

	@Override
	public String escribir(Json.Nodo nodo) {
		/*
		 * El editor escribe JSON normal.
		 *
		 * Esto pierde estilo JSON5 original, comentarios y trailing commas, pero evita
		 * el problema de motorRef null y mantiene compatibilidad con Json.Nodo.
		 */
		return Json.escribir(nodo);
	}

	private String convertirAJsonNormal(Json5Element element) {
		StringBuilder sb = new StringBuilder();
		escribirJsonNormal(element, sb);
		return sb.toString();
	}

	private void escribirJsonNormal(Json5Element element, StringBuilder sb) {
		if (element == null || element instanceof Json5Null) {
			sb.append("null");
			return;
		}

		if (element.isJson5Object()) {
			escribirObjeto(element.getAsJson5Object(), sb);
			return;
		}

		if (element.isJson5Array()) {
			escribirArreglo(element.getAsJson5Array(), sb);
			return;
		}

		if (element.isJson5Primitive()) {
			escribirPrimitivo(element.getAsJson5Primitive(), sb);
			return;
		}

		sb.append("null");
	}

	private void escribirObjeto(Json5Object obj, StringBuilder sb) {
		sb.append('{');

		boolean primero = true;

		for (Map.Entry<String, Json5Element> e : obj.asMap().entrySet()) {
			if (!primero) {
				sb.append(',');
			}

			primero = false;

			escribirCadena(e.getKey(), sb);
			sb.append(':');
			escribirJsonNormal(e.getValue(), sb);
		}

		sb.append('}');
	}

	private void escribirArreglo(Json5Array arr, StringBuilder sb) {
		sb.append('[');

		boolean primero = true;

		for (Json5Element e : arr) {
			if (!primero) {
				sb.append(',');
			}

			primero = false;
			escribirJsonNormal(e, sb);
		}

		sb.append(']');
	}

	private void escribirPrimitivo(Json5Primitive p, StringBuilder sb) {
		if (p == null) {
			sb.append("null");
			return;
		}

		if (p.isBoolean()) {
			sb.append(p.getAsBoolean() ? "true" : "false");
			return;
		}

		if (p.isNumber()) {
			Number n = p.getAsNumber();

			if (n == null) {
				sb.append("null");
			} else {
				sb.append(String.valueOf(n));
			}

			return;
		}

		escribirCadena(p.getAsString(), sb);
	}

	private void escribirCadena(String s, StringBuilder sb) {
		if (s == null) {
			sb.append("null");
			return;
		}

		sb.append('"');

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			switch (c) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				if (c < 32) {
					String hex = Integer.toHexString(c);
					sb.append("\\u");
					for (int j = hex.length(); j < 4; j++) {
						sb.append('0');
					}
					sb.append(hex);
				} else {
					sb.append(c);
				}
				break;
			}
		}

		sb.append('"');
	}
}