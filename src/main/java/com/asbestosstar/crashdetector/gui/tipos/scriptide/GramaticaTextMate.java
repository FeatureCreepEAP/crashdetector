package com.asbestosstar.crashdetector.gui.tipos.scriptide;

import java.awt.Color;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Cargador y aplicador de gramaticas TextMate (tmLanguage JSON).
 *
 * Procesa los patrones match y begin/end del JSON y los aplica como resaltado
 * sobre un StyledDocument de Swing.
 *
 * No implementa el motor completo de TextMate (alcances anidados, inyeccion de
 * gramatica, etc.) sino un subconjunto practico: match + begin/end simples.
 */
public class GramaticaTextMate {

	// -----------------------------------------------------------------------
	// Enumeracion de categorias de tokens para mapear a colores
	// -----------------------------------------------------------------------

	public enum CategoriaToken {
		/** Palabras clave, tipos, almacenamiento. */
		KEYWORD,
		/** Literales de cadena de texto. */
		STRING,
		/** Comentarios. */
		COMMENT,
		/** Numeros literales. */
		NUMBER,
		/** Nombres de funciones / metodos. */
		FUNCTION,
		/** Texto normal sin categoria especial. */
		NORMAL
	}

	// -----------------------------------------------------------------------
	// Reglas de resaltado extraidas del JSON
	// -----------------------------------------------------------------------

	/** Regla basada en un patron `match` simple (una sola linea o token). */
	public static class ReglaMatch {
		public final Pattern patron;
		public final CategoriaToken categoria;

		public ReglaMatch(Pattern patron, CategoriaToken categoria) {
			this.patron = patron;
			this.categoria = categoria;
		}
	}

	/**
	 * Regla basada en un par begin/end (cadenas, comentarios de bloque, etc.). El
	 * texto entre inicio y fin (inclusive) recibe la misma categoria.
	 */
	public static class ReglaRango {
		public final Pattern inicio;
		public final Pattern fin;
		public final CategoriaToken categoria;

		public ReglaRango(Pattern inicio, Pattern fin, CategoriaToken categoria) {
			this.inicio = inicio;
			this.fin = fin;
			this.categoria = categoria;
		}
	}

	// -----------------------------------------------------------------------
	// Estado interno
	// -----------------------------------------------------------------------

	/** Reglas match extraidas del JSON, en orden de prioridad. */
	public final List<ReglaMatch> reglasMatch = new ArrayList<>();

	/** Reglas rango (begin/end) extraidas del JSON. */
	public final List<ReglaRango> reglasRango = new ArrayList<>();

	/** Nombre de la gramatica, tomado del campo "name" del JSON. */
	public String nombre = "";

	// -----------------------------------------------------------------------
	// Carga desde archivo
	// -----------------------------------------------------------------------

	/**
	 * Lee y parsea un archivo tmLanguage JSON. Devuelve una GramaticaTextMate lista
	 * para usar, o null si hubo error.
	 */
	public static GramaticaTextMate cargar(File archivo) {
		if (archivo == null || !archivo.isFile()) {
			return null;
		}

		try {
			String json = new String(Files.readAllBytes(archivo.toPath()), StandardCharsets.UTF_8);
			return parsearJson(json);
		} catch (Throwable t) {
			CrashDetectorLogger.log("No se pudo cargar gramatica TextMate: " + t.getMessage());
			return null;
		}
	}

	/**
	 * Parsea el texto JSON de una gramatica TextMate y extrae las reglas.
	 */
	public static GramaticaTextMate parsearJson(String json) {
		GramaticaTextMate g = new GramaticaTextMate();

		try {
			Json.Nodo raiz = Json.leer(json);
			if (raiz == null) {
				return g;
			}

			// Nombre de la gramatica
			Json.Nodo nNombre = raiz.obtener("name");
			if (nNombre != null) {
				g.nombre = nNombre.comoCadena();
			}

			// Procesar patrones del nivel raiz
			Json.Nodo patterns = raiz.obtener("patterns");
			if (patterns != null && patterns.esArreglo()) {
				procesarListaPatrones(patterns, g);
			}

			// Procesar repositorio
			Json.Nodo repository = raiz.obtener("repository");
			if (repository != null && repository.esObjeto()) {
				for (String clave : repository.claves()) {
					Json.Nodo entrada = repository.obtener(clave);
					if (entrada != null) {
						procesarNodoRegla(entrada, g);
					}
				}
			}

		} catch (Throwable t) {
			CrashDetectorLogger.log("Error parseando gramatica TextMate: " + t.getMessage());
		}

		return g;
	}

	// -----------------------------------------------------------------------
	// Parseo recursivo de nodos de reglas
	// -----------------------------------------------------------------------

	private static void procesarListaPatrones(Json.Nodo patrones, GramaticaTextMate g) {
		int tam = patrones.tamano();
		for (int i = 0; i < tam; i++) {
			Json.Nodo item = patrones.en(i);
			if (item != null) {
				procesarNodoRegla(item, g);
			}
		}
	}

	private static void procesarNodoRegla(Json.Nodo nodo, GramaticaTextMate g) {
		if (nodo == null) {
			return;
		}

		// Nodo de tipo "patterns" (agrupa sub-patrones)
		Json.Nodo subPatterns = nodo.obtener("patterns");
		if (subPatterns != null && subPatterns.esArreglo()) {
			procesarListaPatrones(subPatterns, g);
		}

		// Regla match simple
		Json.Nodo matchNodo = nodo.obtener("match");
		Json.Nodo nameNodo = nodo.obtener("name");

		if (matchNodo != null && nameNodo != null) {
			String regexStr = matchNodo.comoCadena();
			String scopeName = nameNodo.comoCadena();
			CategoriaToken cat = scopeACategoria(scopeName);

			if (cat != CategoriaToken.NORMAL && regexStr != null && !regexStr.isEmpty()) {
				try {
					Pattern p = Pattern.compile(regexStr, Pattern.MULTILINE);
					g.reglasMatch.add(new ReglaMatch(p, cat));
				} catch (Throwable ignored) {
					// patron regex invalido — se ignora silenciosamente
				}
			}
		}

		// Regla begin/end
		Json.Nodo beginNodo = nodo.obtener("begin");
		Json.Nodo endNodo = nodo.obtener("end");

		if (beginNodo != null && endNodo != null) {
			String scopeName = nameNodo != null ? nameNodo.comoCadena() : "";
			// Si el nodo no tiene "name" propio, buscar en contentName
			if (scopeName == null || scopeName.isEmpty()) {
				Json.Nodo contentName = nodo.obtener("contentName");
				if (contentName != null) {
					scopeName = contentName.comoCadena();
				}
			}

			CategoriaToken cat = scopeACategoria(scopeName);
			String beginStr = beginNodo.comoCadena();
			String endStr = endNodo.comoCadena();

			if (beginStr != null && !beginStr.isEmpty() && endStr != null && !endStr.isEmpty()) {
				try {
					Pattern pb = Pattern.compile(beginStr, Pattern.MULTILINE);
					Pattern pe = Pattern.compile(endStr, Pattern.MULTILINE);
					g.reglasRango.add(new ReglaRango(pb, pe, cat));
				} catch (Throwable ignored) {
					// patron regex invalido — se ignora
				}
			}

			// Procesar sub-patrones del rango
			if (subPatterns != null && subPatterns.esArreglo()) {
				procesarListaPatrones(subPatterns, g);
			}
		}
	}

	// -----------------------------------------------------------------------
	// Mapeo de scope TextMate a categoria de color
	// -----------------------------------------------------------------------

	/**
	 * Convierte un nombre de scope TextMate ("keyword.control.zenscript", etc.) a
	 * una categoria de token para mapear al color correspondiente del editor.
	 */
	public static CategoriaToken scopeACategoria(String scope) {
		if (scope == null || scope.isEmpty()) {
			return CategoriaToken.NORMAL;
		}

		String s = scope.toLowerCase();

		if (s.startsWith("comment")) {
			return CategoriaToken.COMMENT;
		}
		if (s.startsWith("string")) {
			return CategoriaToken.STRING;
		}
		if (s.startsWith("keyword") || s.startsWith("storage") || s.startsWith("constant.language")
				|| s.startsWith("variable.language")) {
			return CategoriaToken.KEYWORD;
		}
		if (s.startsWith("entity.name.function") || s.startsWith("support.function")) {
			return CategoriaToken.FUNCTION;
		}
		if (s.contains("number") || s.contains("numeric")) {
			return CategoriaToken.NUMBER;
		}

		return CategoriaToken.NORMAL;
	}

	// -----------------------------------------------------------------------
	// Aplicar resaltado sobre un StyledDocument
	// -----------------------------------------------------------------------

	/**
	 * Aplica el resaltado de la gramatica sobre el documento.
	 *
	 * @param doc     documento del editor
	 * @param texto   texto actual del documento
	 * @param estilos mapa de CategoriaToken a Style de Swing (debe incluir al menos
	 *                NORMAL, KEYWORD, STRING, COMMENT)
	 */
	public void aplicarResaltado(StyledDocument doc, String texto, java.util.Map<CategoriaToken, Style> estilos) {

		if (doc == null || texto == null || texto.isEmpty() || estilos == null) {
			return;
		}

		Style normal = estilos.getOrDefault(CategoriaToken.NORMAL, null);
		if (normal == null) {
			return;
		}

		// Primero limpiar todo a estilo normal
		doc.setCharacterAttributes(0, texto.length(), normal, true);

		// Marcadores de rangos ya coloreados para no sobrescribir con match rules
		boolean[] marcado = new boolean[texto.length()];

		// 1. Aplicar reglas rango (begin/end): cadenas y comentarios de bloque
		for (ReglaRango regla : reglasRango) {
			Style estilo = estilos.getOrDefault(regla.categoria, normal);
			aplicarRango(doc, texto, regla.inicio, regla.fin, estilo, marcado);
		}

		// 2. Aplicar reglas match sobre posiciones no marcadas
		for (ReglaMatch regla : reglasMatch) {
			Style estilo = estilos.getOrDefault(regla.categoria, normal);
			aplicarMatch(doc, texto, regla.patron, estilo, marcado);
		}
	}

	private void aplicarRango(StyledDocument doc, String texto, Pattern inicio, Pattern fin, Style estilo,
			boolean[] marcado) {
		try {
			Matcher mInicio = inicio.matcher(texto);
			int buscarDesde = 0;

			while (mInicio.find(buscarDesde)) {
				int ini = mInicio.start();

				// Buscar el fin desde despues del inicio
				Matcher mFin = fin.matcher(texto);
				boolean encontroFin = mFin.find(mInicio.end());

				int finPos;
				if (encontroFin) {
					finPos = mFin.end();
				} else {
					// Sin cierre: hasta el final de la linea o del texto
					int nl = texto.indexOf('\n', mInicio.end());
					finPos = nl >= 0 ? nl : texto.length();
				}

				int len = finPos - ini;
				if (len > 0) {
					doc.setCharacterAttributes(ini, len, estilo, true);
					for (int i = ini; i < finPos && i < marcado.length; i++) {
						marcado[i] = true;
					}
				}

				buscarDesde = finPos;
				if (buscarDesde >= texto.length()) {
					break;
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.log("Error aplicando rango TextMate: " + t.getMessage());
		}
	}

	private void aplicarMatch(StyledDocument doc, String texto, Pattern patron, Style estilo, boolean[] marcado) {
		try {
			Matcher m = patron.matcher(texto);
			while (m.find()) {
				int ini = m.start();
				int fin = m.end();

				// Solo aplicar si ninguna posicion del rango esta ya marcada
				boolean libre = true;
				for (int i = ini; i < fin && i < marcado.length; i++) {
					if (marcado[i]) {
						libre = false;
						break;
					}
				}

				if (libre && fin - ini > 0) {
					doc.setCharacterAttributes(ini, fin - ini, estilo, true);
				}
			}
		} catch (Throwable t) {
			CrashDetectorLogger.log("Error aplicando match TextMate: " + t.getMessage());
		}
	}

	// -----------------------------------------------------------------------
	// Utilidad: construir mapa de estilos desde colores del editor
	// -----------------------------------------------------------------------

	/**
	 * Construye un mapa de CategoriaToken a Style a partir de los colores actuales
	 * del editor. El parametro {@code editor} se usa solo para crear los Style
	 * (necesita un JTextPane o similar con addStyle).
	 */
	public static java.util.Map<CategoriaToken, Style> construirEstilos(javax.swing.JTextPane editor, Color colorNormal,
			Color colorKeyword, Color colorCadena, Color colorComentario) {

		java.util.Map<CategoriaToken, Style> m = new java.util.EnumMap<>(CategoriaToken.class);

		m.put(CategoriaToken.NORMAL, crearEstilo(editor, "tm_normal", colorNormal, false, false));
		m.put(CategoriaToken.KEYWORD, crearEstilo(editor, "tm_keyword", colorKeyword, true, false));
		m.put(CategoriaToken.STRING, crearEstilo(editor, "tm_cadena", colorCadena, false, false));
		m.put(CategoriaToken.COMMENT, crearEstilo(editor, "tm_comment", colorComentario, false, true));
		m.put(CategoriaToken.NUMBER, crearEstilo(editor, "tm_numero", colorCadena, false, false));
		m.put(CategoriaToken.FUNCTION, crearEstilo(editor, "tm_funcion", colorKeyword, false, false));

		return m;
	}

	private static Style crearEstilo(javax.swing.JTextPane editor, String nombre, Color color, boolean negrita,
			boolean cursiva) {
		Style s = editor.addStyle(nombre, null);
		javax.swing.text.StyleConstants.setForeground(s, color);
		javax.swing.text.StyleConstants.setBold(s, negrita);
		javax.swing.text.StyleConstants.setItalic(s, cursiva);
		return s;
	}
}
