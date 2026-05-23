package com.asbestosstar.crashdetector.dto.modpack.minecraftstorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;

public class SolicitudBusquedaModsMinecraftStorage {

	private static final String AGENTE_USUARIO = "Mozilla/5.0";

	public static PaginaMods buscarMods(String idioma, int pagina, String termino) throws IOException {

		String idiomaNormalizado = normalizarIdioma(idioma);

		StringBuilder constructorUrl = new StringBuilder();
		constructorUrl.append(ProveedorModsMinecraftStorage.ENDPOINT.obtener());
		constructorUrl.append("posts/suggest?keyword=");

		if (termino != null && !termino.trim().isEmpty()) {
			constructorUrl.append(java.net.URLEncoder.encode(termino.trim(), "UTF-8"));
		}

		String urlCompleta = constructorUrl.toString();

		URL url = new URL(urlCompleta);
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

		conexion.setRequestMethod("GET");
		conexion.setRequestProperty("Accept", "application/json");
		conexion.setRequestProperty("User-Agent", AGENTE_USUARIO);
		conexion.setRequestProperty("Origin", "https://minecraftstorage.com");
		conexion.setRequestProperty("Referer", "https://minecraftstorage.com/");
		conexion.setRequestProperty("Accept-Language", idiomaNormalizado);

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
			return new RespuestaPaginadaModsMinecraftStorage(raiz, idiomaNormalizado);

		} finally {
			conexion.disconnect();
		}
	}

	private static String normalizarIdioma(String idioma) {

		if (idioma == null) {
			return "en";
		}

		String i = idioma.trim().toLowerCase();

		if (i.startsWith("es")) {
			return "es";
		}

		if (i.startsWith("ru")) {
			return "ru";
		}

		return "en";
	}
}