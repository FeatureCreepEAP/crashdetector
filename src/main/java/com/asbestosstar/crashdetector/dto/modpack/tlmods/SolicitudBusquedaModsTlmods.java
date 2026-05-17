package com.asbestosstar.crashdetector.dto.modpack.tlmods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.json.Json;

public class SolicitudBusquedaModsTlmods {

	private static final String URL_BASE = ProveedorModsTlmods.ENDPOINT.obtener() + "api/client/gameentities";
	private static final String AGENTE_USUARIO = "TLauncher/2.9365";

	public static PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {
		StringBuilder constructorUrl = new StringBuilder(URL_BASE);
		constructorUrl.append("?type=MOD").append("&lang=").append(idioma).append("&page=").append(pagina)
				.append("&sort=POPULAR").append("&lv=2.9365");

		if (termino != null && !termino.trim().isEmpty()) {
			// String codificado = java.net.URLEncoder.encode(termino.trim(),
			// StandardCharsets.UTF_8);
			String codificado = java.net.URLEncoder.encode(termino.trim(), "UTF-8");
			constructorUrl.append("&search=").append(codificado);
		}

		String urlCompleta = constructorUrl.toString();
		URL url = new URL(urlCompleta);
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		conexion.setRequestMethod("GET");
		conexion.setRequestProperty("Accept", "application/json");
		conexion.setRequestProperty("User-Agent", AGENTE_USUARIO);

		int codigo = conexion.getResponseCode();
		if (codigo != 200) {
			throw new IOException("HTTP " + codigo + " desde " + urlCompleta);
		}

		try (BufferedReader lector = new BufferedReader(
				new InputStreamReader(conexion.getInputStream(), StandardCharsets.UTF_8))) {
			StringBuilder contenido = new StringBuilder();
			String linea;
			while ((linea = lector.readLine()) != null) {
				contenido.append(linea);
			}
			Json.Nodo raiz = Json.leer(contenido.toString());
			return new RespuestaPaginadaModsTlmods(raiz);
		} finally {
			conexion.disconnect();
		}
	}
}