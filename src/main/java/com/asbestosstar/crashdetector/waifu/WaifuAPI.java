package com.asbestosstar.crashdetector.waifu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu.Arista;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu.Definicion;
import com.google.gson.Gson;


//curl 'https://api.waifu.neoforged.net/graphql' -X POST -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:128.0) Gecko/20100101 Firefox/128.0' -H 'Accept: application/json, multipart/mixed' -H 'Accept-Language: es-MX,es;q=0.8,en-US;q=0.5,en;q=0.3' -H 'Accept-Encoding: gzip, deflate, br, zstd' -H 'Referer: https://api.waifu.neoforged.net/graphql.html' -H 'content-type: application/json' -H 'Origin: https://api.waifu.neoforged.net' -H 'DNT: 1' -H 'Sec-GPC: 1' -H 'Connection: keep-alive' -H 'Sec-Fetch-Dest: empty' -H 'Sec-Fetch-Mode: cors' -H 'Sec-Fetch-Site: same-origin' -H 'Priority: u=0' --data-raw '{"query":"query ModsWithClass {\n  gameVersion(loader: NeoForge, version: \"1.21.1\") {\n    classes(where: {name: {equals: \"com/simibubi/create/foundation/utility/Lang\"}}, first: 1) {\n      edges {\n        node {\n          definitions {\n            mod {\n              name\n              curseforgeProjectId\n              modrinthProjectId\n            }\n          }\n        }\n      }\n    }\n  }\n}","operationName":"ModsWithClass"}'

//https://api.waifu.neoforged.net/mod_url/987863
/**
 * No ESTA COMPLETA WIP
 */
public class WaifuAPI {
	// URL del endpoint GraphQL
	private static final String GRAPHQL_URL = "https://api.waifu.neoforged.net/graphql";
	private static final Gson gson = new Gson();

	public static List<VersionWaifu> versiones = new ArrayList<VersionWaifu>();
	
	/**
	 * https://waifu.neoforged.net/a/neoforged-waifuvis-app/mods?var-version=1.21.1-fabric&var-filters= Necesita estar aqui
	 * @param cargador
	 * @param version_del_juego
	 */
	public static VersionWaifu obtainerVersion(String cargador, String version_del_juego) {
		for(VersionWaifu vers:versiones) {
			if(vers.cargador.equals(cargador)&&vers.version_del_juego.equals(version_del_juego)) {return vers;}
		}
		VersionWaifu ret = new VersionWaifu(cargador,version_del_juego);
		versiones.add(ret);
		return ret;
	}
	
	
	
	static {
		versiones.add(new VersionWaifu("NeoForge","1.21.1"));
		versiones.add(new VersionWaifu("NeoForge","1.21.7"));
		versiones.add(new VersionWaifu("NeoForge","1.21.5"));
		versiones.add(new VersionWaifu("NeoForge","1.21.4"));
		versiones.add(new VersionWaifu("Fabric","1.21.1"));
		versiones.add(new VersionWaifu("Forge","1.20.1"));
	}
	
	
	
	public static List<RespuestaWaifu.Mod> obtanerModDesdeClase(String clase, VersionWaifu version) {
	    List<RespuestaWaifu.Mod> modsEncontrados = new ArrayList<>();
	    try {
	        String graphqlQuery = generarConsultaGraphQL(clase, version);
	        String jsonResponse = enviarSolicitudGraphQL(graphqlQuery);
	        
	        RespuestaWaifu respuesta = gson.fromJson(jsonResponse, RespuestaWaifu.class);
	        if (respuesta != null && respuesta.data != null && respuesta.data.gameVersion != null) {
	            for (Arista arista : respuesta.data.gameVersion.classes.edges) {
	                if (arista.node != null && arista.node.definitions != null) {
	                    for (Definicion def : arista.node.definitions) {
	                        if (def.mod != null) {
	                            modsEncontrados.add(def.mod);
	                        }
	                    }
	                }
	            }
	        }
	    } catch (Exception e) {
	        CrashDetectorLogger.logException(e);
	    }
	    return modsEncontrados;
	}

	/**
	 * Genera una consulta GraphQL para buscar mods que contengan una clase
	 * específica
	 * 
	 * @param nombreClase El nombre de la clase a buscar (ej:
	 *                    "mezz/jei/api/IModPlugin")
	 * @return Cadena con la consulta GraphQL formateada
	 */
	public static String generarConsultaGraphQL(String nombreClase, VersionWaifu version) {
	    String cargador = version.cargador;
	    String version_del_juego = version.version_del_juego;

	    return String.format(
	        "query ModsWithClass {\n" +
	        "  gameVersion(loader: %s, version: \"%s\") {\n" +
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
	        "}",
	        cargador, version_del_juego, nombreClase
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