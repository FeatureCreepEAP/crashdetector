package com.asbestosstar.crashdetector.config.jsonc;

import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Punto de entrada para JSONC.
 *
 * JSONC = JSON con comentarios. Internamente solo limpia comentarios y luego
 * usa el sistema JSON normal.
 */
public class Jsonc extends Json {

	public Jsonc() {
	}

	public static Json.Nodo leer(String jsonc) {
		return Json.leer(quitarComentarios(jsonc));
	}

	public static Json.Nodo crearObjeto() {
		return Json.crearObjeto();
	}

	public static String escribir(Json.Nodo nodo) {
		return Json.escribir(nodo);
	}

	public static String nombreMotor() {
		return "jsonc-sobre-" + Json.nombreMotor();
	}

	/**
	 * Quita comentarios tipo // y /* ... * / sin tocar texto dentro de cadenas.
	 */
	public static String quitarComentarios(String texto) {
		if (texto == null)
			return null;

		StringBuilder salida = new StringBuilder(texto.length());

		boolean enCadena = false;
		boolean escape = false;
		boolean enComentarioLinea = false;
		boolean enComentarioBloque = false;

		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);
			char sig = (i + 1 < texto.length()) ? texto.charAt(i + 1) : '\0';

			if (enComentarioLinea) {
				if (c == '\n' || c == '\r') {
					enComentarioLinea = false;
					salida.append(c);
				}
				continue;
			}

			if (enComentarioBloque) {
				if (c == '*' && sig == '/') {
					enComentarioBloque = false;
					i++;
				} else if (c == '\n' || c == '\r') {
					// Conserva saltos de linea para que los errores mantengan numeros parecidos
					salida.append(c);
				}
				continue;
			}

			if (enCadena) {
				salida.append(c);

				if (escape) {
					escape = false;
				} else if (c == '\\') {
					escape = true;
				} else if (c == '"') {
					enCadena = false;
				}

				continue;
			}

			if (c == '"') {
				enCadena = true;
				salida.append(c);
				continue;
			}

			if (c == '/' && sig == '/') {
				enComentarioLinea = true;
				i++;
				continue;
			}

			if (c == '/' && sig == '*') {
				enComentarioBloque = true;
				i++;
				continue;
			}

			salida.append(c);
		}

		return salida.toString();
	}
}