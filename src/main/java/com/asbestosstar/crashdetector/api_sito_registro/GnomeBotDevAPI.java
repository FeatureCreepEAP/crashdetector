package com.asbestosstar.crashdetector.api_sito_registro;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;

/**
 * Wrapper de MCLogs que reescribe la URL pública para usar gnomebot.dev.
 *
 * NO se recomienda usar este wrapper.
 * Es preferible usar MCLogs directamente siempre que sea posible.
 */
public class GnomeBotDevAPI extends MCLogsAPI {

	@Override
	public String nombre() {
		return "gnomebotdev";
	}

	/**
	 * NO se recomienda usar este wrapper.
	 * La publicación sigue haciéndose contra la API de MCLogs;
	 * solamente se cambia la URL final devuelta al usuario.
	 */
	@Override
	public String publicarRegistro(Consola registro) throws DemasiadoGrande, ErrorConPublicar {
		String urlOriginal = super.publicarRegistro(registro);
		return convertirUrlAMirror(urlOriginal);
	}

	/**
	 * NO se recomienda usar este wrapper.
	 * Este método publica usando MCLogs y luego transforma la URL devuelta
	 * al formato de gnomebot.dev.
	 */
	@Override
	public String publicarTexto(String nombreSugerido, String contenido) throws DemasiadoGrande, ErrorConPublicar {
		String urlOriginal = super.publicarTexto(nombreSugerido, contenido);
		return convertirUrlAMirror(urlOriginal);
	}

	/**
	 * NO se recomienda usar este wrapper.
	 * Cada enlace generado por MCLogs se transforma al mirror de gnomebot.dev.
	 */
	@Override
	public List<String> publicarRegistroEnPartes(Consola registro) throws ErrorConPublicar, DemasiadoGrande {
		List<String> urlsOriginales = super.publicarRegistroEnPartes(registro);
		List<String> urlsConvertidas = new ArrayList<>();

		for (String url : urlsOriginales) {
			urlsConvertidas.add(convertirUrlAMirror(url));
		}

		return urlsConvertidas;
	}

	/**
	 * Se mantiene la API original de MCLogs.
	 * NO se recomienda cambiar esto salvo que gnomebot.dev exponga
	 * una API compatible propia.
	 */
	@Override
	public List<String> sitiosPorDefecto() {
		return super.sitiosPorDefecto();
	}

	/**
	 * Convierte:
	 *   https://mclo.gs/codigo
	 * o
	 *   http://mclo.gs/codigo
	 * o
	 *   mclo.gs/codigo
	 *
	 * en:
	 *   https://gnomebot.dev/paste/mclogs/codigo
	 *
	 * NO se recomienda depender de este comportamiento.
	 */
	private String convertirUrlAMirror(String urlOriginal) {
		if (urlOriginal == null || urlOriginal.isEmpty()) {
			return urlOriginal;
		}

		String codigo = extraerCodigo(urlOriginal);
		if (codigo == null || codigo.isEmpty()) {
			return urlOriginal;
		}

		return "https://gnomebot.dev/paste/mclogs/" + codigo;
	}

	/**
	 * Extrae el identificador final de una URL de MCLogs.
	 * Ejemplo:
	 *   https://mclo.gs/codigo -> codigo
	 */
	private String extraerCodigo(String url) {
		String limpia = url.trim();

		if (limpia.endsWith("/")) {
			limpia = limpia.substring(0, limpia.length() - 1);
		}

		int ultimaBarra = limpia.lastIndexOf('/');
		if (ultimaBarra < 0 || ultimaBarra == limpia.length() - 1) {
			return null;
		}

		return limpia.substring(ultimaBarra + 1);
	}
}