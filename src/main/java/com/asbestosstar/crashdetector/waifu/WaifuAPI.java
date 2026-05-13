package com.asbestosstar.crashdetector.waifu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.json.Json;

/**
 * API de Waifu/NeoForged para buscar mods por clase.
 *
 * Version con logs detallados para diagnosticar por que no encuentra resultados.
 */
public class WaifuAPI {

	private static final String GRAPHQL_URL = "https://api.waifu.neoforged.net/graphql";

	public static List<RespuestaWaifu.Mod> obtanerModDesdeClase(String clase) {

		return obtanerModDesdeClase(clase, false);
	}

	public static List<RespuestaWaifu.Mod> obtanerModDesdeClase(String clase, boolean usarRegex) {

		CrashDetectorLogger.log("WaifuAPI: inicio obtanerModDesdeClase()");
		CrashDetectorLogger.log("WaifuAPI: clase recibida = " + clase);
		CrashDetectorLogger.log("WaifuAPI: usarRegex = " + usarRegex);

		List<RespuestaWaifu.Mod> modsEncontrados = new ArrayList<RespuestaWaifu.Mod>();

		if (clase == null || clase.trim().isEmpty()) {
			CrashDetectorLogger.log("WaifuAPI: clase nula o vacia, regresando lista vacia");
			return modsEncontrados;
		}

		try {
			if (!usarRegex && clase.indexOf('.') >= 0) {
				CrashDetectorLogger.log("WaifuAPI: clase rechazada porque contiene puntos: " + clase);
				CrashDetectorLogger.log("WaifuAPI: debe usar formato JVM, por ejemplo mezz/jei/api/IModPlugin");
				return modsEncontrados;
			}

			String patron = usarRegex ? clase : escaparRegex(clase);

			CrashDetectorLogger.log("WaifuAPI: patron final = " + patron);

			String consulta = generarConsultaGraphQL();
			CrashDetectorLogger.log("WaifuAPI: consulta GraphQL =\n" + consulta);

			String respuestaJson = enviarSolicitudGraphQL(consulta, patron);

			CrashDetectorLogger.log("WaifuAPI: respuesta JSON cruda longitud = " + respuestaJson.length());
			CrashDetectorLogger.log("WaifuAPI: respuesta JSON cruda = " + limitar(respuestaJson, 12000));

			Json.Nodo raiz = Json.leer(respuestaJson);
			CrashDetectorLogger.log("WaifuAPI: JSON parseado correctamente");

			Json.Nodo errors = raiz.obtener("errors");
			if (!esNulo(errors)) {
				CrashDetectorLogger.log("WaifuAPI: GraphQL devolvio errors = " + limitar(errors.comoCadena(), 8000));
			}

			Json.Nodo data = raiz.obtener("data");
			if (esNulo(data)) {
				CrashDetectorLogger.log("WaifuAPI: data es nulo o no existe");
				return modsEncontrados;
			}

			CrashDetectorLogger.log("WaifuAPI: data existe");

			Json.Nodo gameVersions = data.obtener("gameVersions");
			if (esNulo(gameVersions)) {
				CrashDetectorLogger.log("WaifuAPI: data.gameVersions es nulo o no existe");
				return modsEncontrados;
			}

			if (!gameVersions.esArreglo()) {
				CrashDetectorLogger.log("WaifuAPI: data.gameVersions existe pero no es arreglo");
				CrashDetectorLogger.log("WaifuAPI: gameVersions = " + limitar(gameVersions.comoCadena(), 4000));
				return modsEncontrados;
			}

			CrashDetectorLogger.log("WaifuAPI: cantidad gameVersions = " + gameVersions.tamano());

			for (int i = 0; i < gameVersions.tamano(); i++) {

				Json.Nodo gameVersion = gameVersions.en(i);
				if (esNulo(gameVersion)) {
					CrashDetectorLogger.log("WaifuAPI: gameVersion[" + i + "] es nulo");
					continue;
				}

				String versionDelJuego = texto(gameVersion.obtener("version"));
				String cargador = texto(gameVersion.obtener("loader"));

				CrashDetectorLogger.log("WaifuAPI: revisando gameVersion[" + i + "] loader=" + cargador
						+ " version=" + versionDelJuego);

				Json.Nodo mods = gameVersion.obtener("mods");
				if (esNulo(mods)) {
					CrashDetectorLogger.log("WaifuAPI: mods es nulo para " + cargador + " " + versionDelJuego);
					continue;
				}

				Json.Nodo countNodo = mods.obtener("count");
				CrashDetectorLogger.log("WaifuAPI: mods.count para " + cargador + " " + versionDelJuego
						+ " = " + texto(countNodo));

				Json.Nodo edges = mods.obtener("edges");
				if (esNulo(edges)) {
					CrashDetectorLogger.log("WaifuAPI: mods.edges es nulo para " + cargador + " " + versionDelJuego);
					continue;
				}

				if (!edges.esArreglo()) {
					CrashDetectorLogger.log("WaifuAPI: mods.edges no es arreglo para " + cargador + " " + versionDelJuego);
					CrashDetectorLogger.log("WaifuAPI: edges = " + limitar(edges.comoCadena(), 3000));
					continue;
				}

				CrashDetectorLogger.log("WaifuAPI: edges.tamano para " + cargador + " " + versionDelJuego
						+ " = " + edges.tamano());

				for (int j = 0; j < edges.tamano(); j++) {

					Json.Nodo edge = edges.en(j);
					if (esNulo(edge)) {
						CrashDetectorLogger.log("WaifuAPI: edge[" + j + "] es nulo");
						continue;
					}

					Json.Nodo node = edge.obtener("node");
					if (esNulo(node)) {
						CrashDetectorLogger.log("WaifuAPI: node es nulo en edge[" + j + "]");
						continue;
					}

					RespuestaWaifu.Mod mod = new RespuestaWaifu.Mod();

					mod.name = obtenerPrimerModId(node);
					mod.curseforgeProjectId = enteroONulo(node.obtener("curseforgeProjectId"));
					mod.modrinthProjectId = textoONulo(node.obtener("modrinthProjectId"));
					mod.version_del_juego = versionDelJuego;
					mod.cargador = cargador;
					mod.claseEncontrada = obtenerPrimeraClase(node);
					mod.cantidadClasesEncontradas = contarClases(node);

					CrashDetectorLogger.log("WaifuAPI: mod encontrado:"
							+ " name=" + mod.name
							+ " loader=" + mod.cargador
							+ " version=" + mod.version_del_juego
							+ " cf=" + mod.curseforgeProjectId
							+ " mr=" + mod.modrinthProjectId
							+ " clase=" + mod.claseEncontrada
							+ " coincidencias=" + mod.cantidadClasesEncontradas);

					modsEncontrados.add(mod);
				}
			}

			CrashDetectorLogger.log("WaifuAPI: total mods encontrados = " + modsEncontrados.size());

		} catch (Exception e) {
			CrashDetectorLogger.log("WaifuAPI: excepcion durante busqueda");
			CrashDetectorLogger.logException(e);
		}

		return modsEncontrados;
	}

	private static String generarConsultaGraphQL() {

		return "query JIJ($predicate: StringPredicate) {\n"
				+ "  gameVersions {\n"
				+ "    version\n"
				+ "    loader\n"
				+ "    mods(where: {anyClass: {name: $predicate}} first: 10) {\n"
				+ "      count\n"
				+ "      edges {\n"
				+ "        node {\n"
				+ "          curseforgeProjectId\n"
				+ "          modrinthProjectId\n"
				+ "          modIds\n"
				+ "          classes(where: {name: $predicate}) {\n"
				+ "            name\n"
				+ "          }\n"
				+ "        }\n"
				+ "      }\n"
				+ "    }\n"
				+ "  }\n"
				+ "}";
	}

	private static String enviarSolicitudGraphQL(String consulta, String patron) throws IOException {

		CrashDetectorLogger.log("WaifuAPI: enviando solicitud GraphQL a " + GRAPHQL_URL);

		URL url = new URL(GRAPHQL_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("User-Agent", "CrashDetector/WaifuAPI");
		conn.setDoOutput(true);

		String cuerpo = "{"
				+ "\"query\":\"" + escaparJson(consulta) + "\","
				+ "\"operationName\":\"JIJ\","
				+ "\"variables\":{"
				+ "\"predicate\":{\"matches\":\"" + escaparJson(patron) + "\"}"
				+ "}"
				+ "}";

		CrashDetectorLogger.log("WaifuAPI: cuerpo JSON enviado = " + cuerpo);

		try (OutputStream os = conn.getOutputStream()) {
			byte[] bytes = cuerpo.getBytes(StandardCharsets.UTF_8);
			CrashDetectorLogger.log("WaifuAPI: bytes enviados = " + bytes.length);
			os.write(bytes, 0, bytes.length);
		}

		int codigo = conn.getResponseCode();
		CrashDetectorLogger.log("WaifuAPI: codigo HTTP = " + codigo);

		BufferedReader br;
		if (codigo >= 200 && codigo < 300) {
			CrashDetectorLogger.log("WaifuAPI: leyendo inputStream normal");
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
		} else {
			CrashDetectorLogger.log("WaifuAPI: leyendo errorStream");
			br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
		}

		StringBuilder respuesta = new StringBuilder();
		String linea;

		try {
			while ((linea = br.readLine()) != null) {
				respuesta.append(linea);
			}
		} finally {
			br.close();
		}

		CrashDetectorLogger.log("WaifuAPI: respuesta HTTP longitud = " + respuesta.length());
		CrashDetectorLogger.log("WaifuAPI: respuesta HTTP = " + limitar(respuesta.toString(), 12000));

		if (codigo < 200 || codigo >= 300) {
			throw new IOException("Error HTTP " + codigo + ": " + respuesta.toString());
		}

		return respuesta.toString();
	}

	private static String obtenerPrimerModId(Json.Nodo node) {

		Json.Nodo modIds = node.obtener("modIds");

		if (!esNulo(modIds) && modIds.esArreglo() && modIds.tamano() > 0) {
			String ret = texto(modIds.en(0));
			CrashDetectorLogger.log("WaifuAPI: obtenerPrimerModId desde modIds[0] = " + ret);
			return ret;
		}

		String mr = textoONulo(node.obtener("modrinthProjectId"));
		if (mr != null) {
			CrashDetectorLogger.log("WaifuAPI: obtenerPrimerModId desde Modrinth = " + mr);
			return mr;
		}

		Integer cf = enteroONulo(node.obtener("curseforgeProjectId"));
		if (cf != null) {
			CrashDetectorLogger.log("WaifuAPI: obtenerPrimerModId desde CurseForge = " + cf);
			return String.valueOf(cf);
		}

		CrashDetectorLogger.log("WaifuAPI: obtenerPrimerModId no encontro id");
		return "Mod desconocido";
	}

	private static String obtenerPrimeraClase(Json.Nodo node) {

		Json.Nodo classes = node.obtener("classes");

		if (!esNulo(classes) && classes.esArreglo() && classes.tamano() > 0) {
			Json.Nodo primera = classes.en(0);
			String nombre = texto(primera.obtener("name"));
			CrashDetectorLogger.log("WaifuAPI: primera clase encontrada = " + nombre);
			return nombre;
		}

		CrashDetectorLogger.log("WaifuAPI: no hay clases en node.classes");
		return "";
	}

	private static Integer contarClases(Json.Nodo node) {

		Json.Nodo classes = node.obtener("classes");

		if (!esNulo(classes) && classes.esArreglo()) {
			return Integer.valueOf(classes.tamano());
		}

		return Integer.valueOf(0);
	}

	private static boolean esNulo(Json.Nodo nodo) {

		return nodo == null || "null".equals(nodo.comoCadena());
	}

	private static String texto(Json.Nodo nodo) {

		if (esNulo(nodo)) {
			return "";
		}

		String texto = nodo.comoCadena();
		return texto == null || "null".equals(texto) ? "" : texto;
	}

	private static String textoONulo(Json.Nodo nodo) {

		if (esNulo(nodo)) {
			return null;
		}

		String texto = nodo.comoCadena();
		return texto == null || "null".equals(texto) ? null : texto;
	}

	private static Integer enteroONulo(Json.Nodo nodo) {

		if (esNulo(nodo)) {
			return null;
		}

		try {
			return nodo.comoEntero();
		} catch (Exception e) {
			CrashDetectorLogger.log("WaifuAPI: no se pudo convertir entero: " + nodo.comoCadena());
			return null;
		}
	}

	private static String escaparRegex(String texto) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);

			if ("\\.[]{}()*+-?^$|".indexOf(c) >= 0) {
				sb.append('\\');
			}

			sb.append(c);
		}

		return sb.toString();
	}

	private static String escaparJson(String texto) {

		if (texto == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);

			switch (c) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
				break;
			}
		}

		return sb.toString();
	}

	private static String limitar(String texto, int maximo) {

		if (texto == null) {
			return "null";
		}

		if (texto.length() <= maximo) {
			return texto;
		}

		return texto.substring(0, maximo) + "... [recortado, longitud=" + texto.length() + "]";
	}
}