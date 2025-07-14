package com.asbestosstar.crashdetector.waifu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu.Arista;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu.Definicion;
import com.google.gson.Gson;

/**
 * No ESTA COMPLETA WIP
 */
public class WaifuAPI {
	// URL del endpoint GraphQL
	private static final String GRAPHQL_URL = "https://api.waifu.neoforged.net/graphql";
	private static final Gson gson = new Gson();

	public static void obtanerModDesdeClase(String clase) {
		try {
			String graphqlQuery = generarConsultaGraphQL(clase);

			// Enviar la solicitud y obtener la respuesta JSON
			String jsonResponse = enviarSolicitudGraphQL(graphqlQuery);

			CrashDetectorLogger.log(jsonResponse);
			
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
			//CrashDetectorLogger.log("Error al realizar la solicitud: " + e.getMessage());
			CrashDetectorLogger.logException(e);
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
	    return String.format(
	        "query ModsWithClass {\n" +
	        "  gameVersion(loader: NeoForge, version: \"1.21.1\") {\n" +
	        "    classes(where: {name: {equals: \"%s\"}}, first: 1) {\n" +
	        "      edges {\n" +
	        "        node {\n" +
	        "          definitions {\n" +
	        "            mod {\n" +
	        "              name\n" +
	        "              curseforgeProjectId\n" +
	        "              modrinthProjectId\n" +
	        "            }\n" +
	        "          }\n" +
	        "        }\n" +
	        "      }\n" +
	        "    }\n" +
	        "  }\n" +
	        "}", nombreClase
	    );
	}

	/**
	 * Envía una solicitud GraphQL al servidor especificado
	 * 
	 * @param query Consulta GraphQL a enviar
	 * @return Respuesta del servidor en formato JSON
	 * @throws IOException Si hay un error de red o E/S
	 */
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
	    conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
	    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:128.0) Gecko/20100101 Firefox/128.0");
	    conn.setRequestProperty("Accept", "application/json, multipart/mixed");
	    conn.setRequestProperty("Accept-Language", "es-MX,es;q=0.8,en-US;q=0.5,en;q=0.3");
	    conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
	    conn.setRequestProperty("Referer", "https://api.waifu.neoforged.net/graphql.html");
	    conn.setRequestProperty("Origin", "https://api.waifu.neoforged.net");
	    conn.setRequestProperty("Connection", "keep-alive");
	    conn.setDoOutput(true);

	    // Construye el JSON de forma segura con Gson para que escape correctamente newlines y comillas
	    Map<String, Object> payload = new HashMap<>();
	    payload.put("query", query);
	    payload.put("operationName", "ModsWithClass");
	    String cuerpoJSON = gson.toJson(payload);

	    try (OutputStream os = conn.getOutputStream()) {
	        byte[] input = cuerpoJSON.getBytes(StandardCharsets.UTF_8);
	        os.write(input, 0, input.length);
	    }

	    int codigoRespuesta = conn.getResponseCode();
	    if (codigoRespuesta != HttpURLConnection.HTTP_OK) {
	        throw new IOException("Error HTTP: " + codigoRespuesta);
	    }

	    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
	        StringBuilder respuesta = new StringBuilder();
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            respuesta.append(linea);
	        }
	        return respuesta.toString();
	    }
	}

}