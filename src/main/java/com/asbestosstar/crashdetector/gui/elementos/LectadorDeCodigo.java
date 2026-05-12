package com.asbestosstar.crashdetector.gui.elementos;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class LectadorDeCodigo extends JTextPane {

	private static final long serialVersionUID = 1L;

	private Color colorFondo;
	private Color colorTextoNormal;
	private Color colorPalabraClave;
	private Color colorCadena;
	private Color colorComentario;
	private Color colorNumero;
	private Color colorTipo;
	private Color colorMetodo;

	private final Set<String> palabrasClave = new HashSet<String>();
	private final Set<String> tipos = new HashSet<String>();

	public LectadorDeCodigo(Color colorFondo, Color colorTextoNormal, Color colorPalabraClave, Color colorCadena,
			Color colorComentario, Color colorNumero, Color colorTipo, Color colorMetodo) {

		super(new DefaultStyledDocument());

		this.colorFondo = colorFondo;
		this.colorTextoNormal = colorTextoNormal;
		this.colorPalabraClave = colorPalabraClave;
		this.colorCadena = colorCadena;
		this.colorComentario = colorComentario;
		this.colorNumero = colorNumero;
		this.colorTipo = colorTipo;
		this.colorMetodo = colorMetodo;

		inicializarPalabras();

		setEditable(false);
		setBackground(colorFondo);
		setForeground(colorTextoNormal);
		setCaretColor(colorTextoNormal);
		setFont(new java.awt.Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 13));
	}

	private void inicializarPalabras() {
		String[] claves = new String[] { "abstract", "assert", "break", "case", "catch", "class", "const", "continue",
				"default", "do", "else", "enum", "extends", "final", "finally", "for", "goto", "if", "implements",
				"import", "instanceof", "interface", "native", "new", "package", "private", "protected", "public",
				"return", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
				"transient", "try", "volatile", "while" };

		for (String s : claves) {
			palabrasClave.add(s);
		}

		String[] tiposBase = new String[] { "boolean", "byte", "char", "double", "float", "int", "long", "short",
				"void", "String", "Object", "Integer", "Long", "Double", "Float", "Boolean", "Character", "Byte",
				"Short", "List", "Map", "Set", "Collection", "ArrayList", "HashMap", "HashSet" };

		for (String s : tiposBase) {
			tipos.add(s);
		}
	}

	public void establecerColores(Color colorFondo, Color colorTextoNormal, Color colorPalabraClave, Color colorCadena,
			Color colorComentario, Color colorNumero, Color colorTipo, Color colorMetodo) {

		this.colorFondo = colorFondo;
		this.colorTextoNormal = colorTextoNormal;
		this.colorPalabraClave = colorPalabraClave;
		this.colorCadena = colorCadena;
		this.colorComentario = colorComentario;
		this.colorNumero = colorNumero;
		this.colorTipo = colorTipo;
		this.colorMetodo = colorMetodo;

		setBackground(colorFondo);
		setForeground(colorTextoNormal);
		setCaretColor(colorTextoNormal);

		aplicarResaltado();
	}

	public void establecerCodigo(String codigo) {
		if (codigo == null) {
			codigo = "";
		}

		setText(codigo);
		aplicarResaltado();
		setCaretPosition(0);
	}

	private void aplicarResaltado() {
		StyledDocument doc = getStyledDocument();

		String texto;

		try {
			texto = doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			return;
		}

		aplicarEstilo(doc, 0, texto.length(), colorTextoNormal, false);

		int i = 0;

		while (i < texto.length()) {
			char c = texto.charAt(i);

			if (c == '/' && i + 1 < texto.length() && texto.charAt(i + 1) == '/') {
				int inicio = i;
				i += 2;

				while (i < texto.length() && texto.charAt(i) != '\n') {
					i++;
				}

				aplicarEstilo(doc, inicio, i - inicio, colorComentario, false);
				continue;
			}

			if (c == '/' && i + 1 < texto.length() && texto.charAt(i + 1) == '*') {
				int inicio = i;
				i += 2;

				while (i + 1 < texto.length() && !(texto.charAt(i) == '*' && texto.charAt(i + 1) == '/')) {
					i++;
				}

				if (i + 1 < texto.length()) {
					i += 2;
				}

				aplicarEstilo(doc, inicio, i - inicio, colorComentario, false);
				continue;
			}

			if (c == '"' || c == '\'') {
				char comilla = c;
				int inicio = i;
				i++;

				boolean escape = false;

				while (i < texto.length()) {
					char actual = texto.charAt(i);

					if (escape) {
						escape = false;
					} else if (actual == '\\') {
						escape = true;
					} else if (actual == comilla) {
						i++;
						break;
					}

					i++;
				}

				aplicarEstilo(doc, inicio, i - inicio, colorCadena, false);
				continue;
			}

			if (Character.isDigit(c)) {
				int inicio = i;
				i++;

				while (i < texto.length()) {
					char actual = texto.charAt(i);

					if (Character.isDigit(actual) || actual == '.' || actual == 'x' || actual == 'X' || actual == 'L'
							|| actual == 'l' || actual == 'F' || actual == 'f' || actual == 'D' || actual == 'd'
							|| actual == '_') {
						i++;
					} else {
						break;
					}
				}

				aplicarEstilo(doc, inicio, i - inicio, colorNumero, false);
				continue;
			}

			if (Character.isJavaIdentifierStart(c)) {
				int inicio = i;
				i++;

				while (i < texto.length() && Character.isJavaIdentifierPart(texto.charAt(i))) {
					i++;
				}

				String palabra = texto.substring(inicio, i);

				if (palabrasClave.contains(palabra)) {
					aplicarEstilo(doc, inicio, i - inicio, colorPalabraClave, true);
				} else if (tipos.contains(palabra) || Character.isUpperCase(palabra.charAt(0))) {
					aplicarEstilo(doc, inicio, i - inicio, colorTipo, false);
				} else if (esMetodo(texto, i)) {
					aplicarEstilo(doc, inicio, i - inicio, colorMetodo, false);
				}

				continue;
			}

			i++;
		}
	}

	private boolean esMetodo(String texto, int indiceDespuesPalabra) {
		int i = indiceDespuesPalabra;

		while (i < texto.length() && Character.isWhitespace(texto.charAt(i))) {
			i++;
		}

		return i < texto.length() && texto.charAt(i) == '(';
	}

	private void aplicarEstilo(StyledDocument doc, int inicio, int longitud, Color color, boolean negrita) {
		if (longitud <= 0) {
			return;
		}

		Style estilo = addStyle("estilo_" + color.getRGB() + "_" + negrita, null);
		StyleConstants.setForeground(estilo, color);
		StyleConstants.setBold(estilo, negrita);

		doc.setCharacterAttributes(inicio, longitud, estilo, true);
	}
}