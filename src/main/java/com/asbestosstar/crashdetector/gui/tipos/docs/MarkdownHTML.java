package com.asbestosstar.crashdetector.gui.tipos.docs;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Statics;

public class MarkdownHTML {

	public static List<String> enlaces_de_imagenes = new ArrayList<String>();

	static {
		enlaces_de_imagenes.add(com.asbestosstar.crashdetector.Statics.GIT_RAW + "src/main/resources/");
	}

	/**
	 * Convierte un texto Markdown sencillo a HTML compatible con JEditorPane.
	 *
	 * Soporta: - títulos con #, ##, ###... - párrafos - listas con - o * - citas
	 * con > - líneas horizontales con --- - negrita **texto** - cursiva *texto* -
	 * código inline `codigo` - enlaces [texto](url) - imágenes ![alt](url) -
	 * bloques de código con ```
	 *
	 * El HTML generado es deliberadamente simple para evitar problemas con
	 * JEditorPane.
	 *
	 * @param md markdown de entrada
	 * @return html listo para usar en JEditorPane
	 */
	public String HTMLdesdeMarkdown(String md) {
		if (md == null) {
			md = "";
		}

		String texto = md.replace("\r\n", "\n").replace('\r', '\n');

		String[] lineas = texto.split("\n", -1);

		StringBuilder html = new StringBuilder();
		html.append("<html><body style='font-family:sans-serif;'>");

		boolean enLista = false;
		boolean enCodigo = false;
		boolean enCita = false;

		for (String lineaOriginal : lineas) {
			String linea = lineaOriginal;

			// Inicio o fin de bloque de código
			if (linea.trim().startsWith("```")) {
				if (!enCodigo) {
					cerrarListaSiHaceFalta(html, enLista);
					enLista = false;

					cerrarCitaSiHaceFalta(html, enCita);
					enCita = false;

					html.append("<pre style='background:#f4f4f4; padding:8px; border:1px solid #cccccc;'>");
					enCodigo = true;
				} else {
					html.append("</pre>");
					enCodigo = false;
				}
				continue;
			}

			// Dentro de bloque de código no se interpreta markdown
			if (enCodigo) {
				html.append(escaparHTML(linea)).append("\n");
				continue;
			}

			String trim = linea.trim();

			// Línea vacía
			if (trim.isEmpty()) {
				cerrarListaSiHaceFalta(html, enLista);
				enLista = false;

				cerrarCitaSiHaceFalta(html, enCita);
				enCita = false;

				continue;
			}

			// Línea horizontal
			if (trim.equals("---") || trim.equals("***")) {
				cerrarListaSiHaceFalta(html, enLista);
				enLista = false;

				cerrarCitaSiHaceFalta(html, enCita);
				enCita = false;

				html.append("<hr>");
				continue;
			}

			// Títulos
			if (trim.startsWith("#")) {
				cerrarListaSiHaceFalta(html, enLista);
				enLista = false;

				cerrarCitaSiHaceFalta(html, enCita);
				enCita = false;

				int nivel = contarHashesIniciales(trim);
				if (nivel < 1) {
					nivel = 1;
				}
				if (nivel > 6) {
					nivel = 6;
				}

				String contenido = trim.substring(nivel).trim();
				contenido = procesarInlineMarkdown(contenido);

				html.append("<h").append(nivel).append(">").append(contenido).append("</h").append(nivel).append(">");
				continue;
			}

			// Listas
			if (trim.startsWith("- ") || trim.startsWith("* ")) {
				cerrarCitaSiHaceFalta(html, enCita);
				enCita = false;

				if (!enLista) {
					html.append("<ul>");
					enLista = true;
				}

				String contenido = trim.substring(2).trim();
				contenido = procesarInlineMarkdown(contenido);

				html.append("<li>").append(contenido).append("</li>");
				continue;
			} else {
				cerrarListaSiHaceFalta(html, enLista);
				enLista = false;
			}

			// Citas
			if (trim.startsWith(">")) {
				if (!enCita) {
					html.append("<blockquote style='margin-left:12px; color:#444444;'>");
					enCita = true;
				}

				String contenido = trim.substring(1).trim();
				contenido = procesarInlineMarkdown(contenido);

				html.append("<p>").append(contenido).append("</p>");
				continue;
			} else {
				cerrarCitaSiHaceFalta(html, enCita);
				enCita = false;
			}

			// Párrafo normal
			html.append("<p>").append(procesarInlineMarkdown(trim)).append("</p>");
		}

		cerrarListaSiHaceFalta(html, enLista);
		cerrarCitaSiHaceFalta(html, enCita);

		if (enCodigo) {
			html.append("</pre>");
		}

		html.append("</body></html>");
		return html.toString();
	}

	public void cerrarListaSiHaceFalta(StringBuilder html, boolean enLista) {
		if (enLista) {
			html.append("</ul>");
		}
	}

	public void cerrarCitaSiHaceFalta(StringBuilder html, boolean enCita) {
		if (enCita) {
			html.append("</blockquote>");
		}
	}

	public int contarHashesIniciales(String texto) {
		int n = 0;
		while (n < texto.length() && texto.charAt(n) == '#') {
			n++;
		}
		return n;
	}

	/**
	 * Procesa elementos inline de markdown.
	 */
	public String procesarInlineMarkdown(String texto) {
		if (texto == null || texto.isEmpty()) {
			return "";
		}

		String s = escaparHTML(texto);

		// Imágenes: ![alt](url)
		s = reemplazarImagenes(s);

		// Enlaces: [texto](url)
		s = reemplazarEnlaces(s);

		// Código inline: `codigo`
		s = s.replaceAll("`([^`]+)`", "<code>$1</code>");

		// Negrita: **texto**
		s = s.replaceAll("\\*\\*([^*]+)\\*\\*", "<b>$1</b>");

		// Cursiva: *texto*
		s = s.replaceAll("(?<!\\*)\\*([^*]+)\\*(?!\\*)", "<i>$1</i>");

		return s;
	}

	public String reemplazarImagenes(String s) {
		Pattern p = Pattern.compile("!\\[([^\\]]*)\\]\\(([^\\s)]+)(?:\\s+\"([^\"]*)\")?\\)");
		Matcher m = p.matcher(s);
		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			String alt = m.group(1);
			String url = m.group(2).trim();
			String titulo = m.group(3);

			String rutaLocal = convertirEnlaceDeRecursoAURLLocal(url);
			String src = (rutaLocal != null) ? rutaLocal : url;

			StringBuilder reemplazo = new StringBuilder();
			reemplazo.append("<img src=\"").append(escaparHTMLAtributo(src)).append("\" alt=\"")
					.append(escaparHTMLAtributo(alt)).append("\"");

			if (titulo != null && !titulo.isEmpty()) {
				reemplazo.append(" title=\"").append(escaparHTMLAtributo(titulo)).append("\"");
			}

			reemplazo.append(">");

			m.appendReplacement(sb, Matcher.quoteReplacement(reemplazo.toString()));
		}

		m.appendTail(sb);
		return sb.toString();
	}

	public String reemplazarEnlaces(String s) {
		Pattern p = Pattern.compile("\\[([^\\]]+)\\]\\(([^\\s)]+)(?:\\s+\"([^\"]*)\")?\\)");
		Matcher m = p.matcher(s);
		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			String texto = m.group(1);
			String url = m.group(2).trim();
			String titulo = m.group(3);

			String rutaLocal = convertirEnlaceDeRecursoAURLLocal(url);
			String href = (rutaLocal != null) ? rutaLocal : url;

			StringBuilder reemplazo = new StringBuilder();
			reemplazo.append("<a href=\"").append(escaparHTMLAtributo(href)).append("\"");

			if (titulo != null && !titulo.isEmpty()) {
				reemplazo.append(" title=\"").append(escaparHTMLAtributo(titulo)).append("\"");
			}

			reemplazo.append(">").append(texto).append("</a>");

			m.appendReplacement(sb, Matcher.quoteReplacement(reemplazo.toString()));
		}

		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * Convierte un enlace remoto del repositorio a una URL local basada en
	 * Statics.carpeta.
	 *
	 * Ejemplo:
	 * https://pagure.io/CrashDetectorMC/raw/main/f/src/main/resources/imagenes/cfskiplauncher.png
	 *
	 * pasa a: file:/.../Statics.carpeta/imagenes/cfskiplauncher.png
	 *
	 * Esto también funciona para otros recursos dentro de src/main/resources/, por
	 * ejemplo docs/...
	 *
	 * @param url enlace original
	 * @return URL local en formato file: o null si no aplica conversión
	 */
	public String convertirEnlaceDeRecursoAURLLocal(String url) {
		if (url == null || url.isEmpty()) {
			return null;
		}

		for (String prefijo : enlaces_de_imagenes) {
			if (url.startsWith(prefijo)) {
				String rutaRelativa = url.substring(prefijo.length());

				while (rutaRelativa.startsWith("/")) {
					rutaRelativa = rutaRelativa.substring(1);
				}

				Path rutaLocal = Statics.carpeta;
				String[] partes = rutaRelativa.split("/");

				for (String parte : partes) {
					if (!parte.isEmpty()) {
						rutaLocal = rutaLocal.resolve(parte);
					}
				}

				if (java.nio.file.Files.exists(rutaLocal)) {
					return rutaLocal.toUri().toString();
				}
			}
		}

		return null;
	}

	public String escaparHTML(String s) {
		return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	public String escaparHTMLAtributo(String s) {
		return escaparHTML(s).replace("\"", "&quot;");
	}
}