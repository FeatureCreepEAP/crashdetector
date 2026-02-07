package com.asbestosstar.crashdetector.lanzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;

/**
 * Descarga, limpia y cachea la página de actualizaciones de Minecraft para
 * mostrarla dentro del launcher usando JEditorPane.
 *
 * SIN dependencias externas. Optimizado para velocidad.
 */
public class PaginaDeActualizacionesMinecraft {

	/** Archivo de caché local */
	public static File archivo = Statics.carpeta.resolve("versiones_minecraft_cdlauncher.htm").toFile();

	/**
	 * Obtiene el HTML final. Usa caché si existe, si no descarga y procesa.
	 */
	public static String obtenerHTML() {
		try {
			if (archivo.exists()) {
				return leerArchivo(archivo);
			}

			cachear();
			return archivo.exists() ? leerArchivo(archivo) : "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Descarga la página remota, la limpia y la guarda en caché.
	 */
	public static void cachear() {
		try {
			String html = descargar(obtenerURL());
			html = limpiarHTML(html);

			try (FileOutputStream fos = new FileOutputStream(archivo)) {
				fos.write(html.getBytes(StandardCharsets.UTF_8));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Limpia el HTML: - Elimina TODO antes de “Stay up to date with the latest
	 * news” - Extrae solo news_title + news_desc - Elimina scripts, css, imágenes,
	 * tablas y layouts - Produce HTML compatible con JEditorPane
	 */
	public static String limpiarHTML(String html) {

		// Cortar todo antes del texto clave
		String clave = "stay up to date with the latest news";
		int indice = indiceIgnoreCase(html, clave);
		if (indice >= 0) {
			html = html.substring(indice);
		}

		StringBuilder salida = new StringBuilder(64_000);

		// Fondo blanco explícito para Swing
		salida.append("<html><body style='").append("background:#ffffff;").append("color:#000000;")
				.append("font-family:sans-serif;").append("font-size:12px;").append("margin:10px;'>");

		int posicion = 0;

		while (true) {

			int idxTitulo = indiceIgnoreCase(html, "class=\"news_title\"", posicion);
			if (idxTitulo < 0)
				break;

			int finTagTitulo = html.indexOf('>', idxTitulo);
			if (finTagTitulo < 0)
				break;

			int finTextoTitulo = html.indexOf('<', finTagTitulo + 1);
			if (finTextoTitulo < 0)
				break;

			String titulo = limpiarTexto(html.substring(finTagTitulo + 1, finTextoTitulo));

			int idxDesc = indiceIgnoreCase(html, "class=\"news_desc\"", finTextoTitulo);

			String descripcion = "";
			if (idxDesc >= 0) {
				int finTagDesc = html.indexOf('>', idxDesc);
				int cierreDiv = indiceIgnoreCase(html, "</div>", finTagDesc + 1);

				if (finTagDesc >= 0 && cierreDiv >= 0) {
					String bloque = html.substring(finTagDesc + 1, cierreDiv);
					descripcion = limpiarTexto(eliminarEtiquetasManteniendoSaltos(bloque));
				}
			}

			if (!titulo.isEmpty()) {
				salida.append("<div style='font-size:14px;font-weight:bold;margin-top:12px;'>")
						.append(escaparHTML(titulo)).append("</div>");
			}

			if (!descripcion.isEmpty()) {
				salida.append("<div style='margin-top:4px;margin-bottom:12px;line-height:1.35;'>")
						.append(escaparHTML(descripcion).replace("\n", "<br>")).append("</div>");
			}

			posicion = finTextoTitulo + 1;
		}

		salida.append("</body></html>");

		return postProcesarTexto(salida.toString());
	}

	// =====================================================
	// UTILIDADES INTERNAS (sin dependencias)
	// =====================================================

	private static int indiceIgnoreCase(String texto, String buscar) {
		return indiceIgnoreCase(texto, buscar, 0);
	}

	private static int indiceIgnoreCase(String texto, String buscar, int desde) {
		int tl = texto.length();
		int bl = buscar.length();

		for (int i = Math.max(0, desde); i + bl <= tl; i++) {
			if (texto.regionMatches(true, i, buscar, 0, bl)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Elimina etiquetas HTML pero mantiene saltos de línea.
	 */
	private static String eliminarEtiquetasManteniendoSaltos(String html) {
		StringBuilder sb = new StringBuilder(html.length());
		int len = html.length();

		for (int i = 0; i < len; i++) {
			char c = html.charAt(i);

			if (c == '<') {
				int gt = html.indexOf('>', i + 1);
				if (gt < 0)
					break;

				String tag = html.substring(i + 1, gt).toLowerCase();
				if (tag.startsWith("br") || tag.startsWith("p") || tag.startsWith("/p") || tag.startsWith("div")
						|| tag.startsWith("/div")) {
					sb.append('\n');
				}
				i = gt;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Normaliza espacios y saltos de línea.
	 */
	private static String limpiarTexto(String texto) {
		texto = texto.replace('\r', '\n').replace('\t', ' ');
		while (texto.contains("  "))
			texto = texto.replace("  ", " ");
		return texto.trim();
	}

	/**
	 * Escapa caracteres problemáticos para HTML Swing.
	 */
	private static String escaparHTML(String texto) {
		return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	/**
	 * Limpieza final de restos específicos del HTML original.
	 */
	private static String postProcesarTexto(String html) {
		return html.replace("fb.com/tlauncher", "").replace("tlauncher", "cdlauncher").replace("TLauncher",
				"CDLauncher");
	}

	/**
	 * URL según idioma actual.
	 */
	public static String obtenerURL() {
		return "https://repo.tlauncher.org/update/downloads/configs_v1/pages/index_" + MonitorDePID.idioma.codigo()
				+ ".html";
	}

	/**
	 * Descarga una URL como UTF-8.
	 */
	private static String descargar(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		StringBuilder sb = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {

			String linea;
			while ((linea = br.readLine()) != null) {
				sb.append(linea).append('\n');
			}
		}
		return sb.toString();
	}

	/**
	 * Lee un archivo UTF-8 completo.
	 */
	private static String leerArchivo(File f) throws Exception {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {

			String linea;
			while ((linea = br.readLine()) != null) {
				sb.append(linea).append('\n');
			}
		}
		return sb.toString();
	}
}
