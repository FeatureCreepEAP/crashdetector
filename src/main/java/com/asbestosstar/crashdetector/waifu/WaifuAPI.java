package com.asbestosstar.crashdetector.waifu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu.Arista;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu.Definicion;
import com.google.gson.Gson;

/**
 * No ESTA COMPLETA WIP
 */
public class WaifuAPI {
	// URL del endpoint GraphQL
	private static final String GRAPHQL_URL = "https://api.waifu.neoforged.net/";
	private static final Gson gson = new Gson();

	public static void obtanerModDesdeClase(String clase) {
		try {
			String graphqlQuery = generarConsultaGraphQL(clase);

			// Enviar la solicitud y obtener la respuesta JSON
			String jsonResponse = enviarSolicitudGraphQL(graphqlQuery);

			// Mapear la respuesta JSON a las clases GSON
			RespuestaWaifu respuesta = gson.fromJson(jsonResponse, RespuestaWaifu.class);

			// Verificar si existen datos válidos
			if (respuesta != null && respuesta.data != null && respuesta.data.gameVersion != null
					&& respuesta.data.gameVersion.classes != null
					&& !respuesta.data.gameVersion.classes.edges.isEmpty()) {

				// Recorrer los resultados y mostrar los datos del mod
				for (Arista arista : respuesta.data.gameVersion.classes.edges) {
					if (arista.node != null && arista.node.definitions != null) {
						for (Definicion def : arista.node.definitions) {
							if (def.mod != null) {
								CrashDetectorLogger.log("Nombre del mod: " + def.mod.name);
								if (def.mod.curseforgeProjectId != null) {
									CrashDetectorLogger.log("ID de CurseForge: " + def.mod.curseforgeProjectId);
								} else {
									CrashDetectorLogger.log("ID de CurseForge: No disponible");
								}
								CrashDetectorLogger.log("ID de Modrinth: " + def.mod.modrinthProjectId);
								CrashDetectorLogger.log("---------------------------");
							}
						}
					}
				}

			} else {
				CrashDetectorLogger.log("No se encontraron datos válidos en la respuesta.");
			}

		} catch (Exception e) {
			CrashDetectorLogger.log("Error al realizar la solicitud: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Genera una consulta GraphQL para buscar mods que contengan una clase
	 * específica
	 * 
	 * @param nombreClase El nombre de la clase a buscar (ej:
	 *                    "mezz/jei/api/IModPlugin")
	 * @return Cadena con la consulta GraphQL formateada
	 */
	public static String generarConsultaGraphQL(String nombreClase) {
		// Plantilla de consulta GraphQL con marcador de posición para el nombre de la
		// clase
		return String.format("{\n" + "  gameVersion(loader: NeoForge, version: \"1.21.1\") {\n"
				+ "    classes(where: {name: {equals: \"%s\"}}, first: 1) {\n" + "      edges {\n" + "        node {\n"
				+ "          definitions {\n" + "            mod {\n" + "              name\n"
				+ "              curseforgeProjectId\n" + "              modrinthProjectId\n" + "            }\n"
				+ "          }\n" + "        }\n" + "      }\n" + "    }\n" + "  }\n" + "}", nombreClase // Inserta el
																											// nombre de
																											// la clase
																											// en la
																											// consulta
		);
	}

	/**
	 * Envía una solicitud GraphQL al servidor especificado
	 * 
	 * @param query Consulta GraphQL a enviar
	 * @return Respuesta del servidor en formato JSON
	 * @throws IOException Si hay un error de red o E/S
	 */
	public static String enviarSolicitudGraphQL(String query) throws IOException {
		URL url = new URL(GRAPHQL_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);

		// Preparar cuerpo de la solicitud
		String cuerpoJSON = String.format("{\"query\":\"%s\"}", query.replace("\"", "\\\""));

		// Enviar solicitud
		try (OutputStream os = conn.getOutputStream()) {
			byte[] input = cuerpoJSON.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		int codigoRespuesta = conn.getResponseCode();
		if (codigoRespuesta != HttpURLConnection.HTTP_OK) {
			throw new IOException("Error HTTP: " + codigoRespuesta);
		}

		// Leer respuesta
		StringBuilder respuesta = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				respuesta.append(linea.trim());
			}
		}

		return respuesta.toString();
	}
}