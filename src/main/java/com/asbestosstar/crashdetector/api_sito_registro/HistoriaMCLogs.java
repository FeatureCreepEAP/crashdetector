package com.asbestosstar.crashdetector.api_sito_registro;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;

/**
 * Maneja el historial local de registros publicados en MCLogs.
 * 
 * Archivo: ~/crash_detector/historia_mclogs.txt
 */
public class HistoriaMCLogs {

	public static final Path ARCHIVO = Statics.carpeta_mundial.resolve("historia_mclogs.txt");

	/**
	 * Agrega una entrada nueva al historial.
	 */
	public static synchronized void agregar(EntradaMCLogs entrada) {
		if (entrada == null)
			return;

		try {
			Files.createDirectories(ARCHIVO.getParent());

			Nodo raiz = cargarRaiz();
			Nodo obj = Json.crearObjeto();

			// Formato JSON real:
			// [
			// {
			// "endpoint": "...",
			// "id": "...",
			// "url": "...",
			// "raw": "...",
			// "token": "...",
			// "source": "...",
			// "created": 123,
			// "expires": 123,
			// "size": 123,
			// "lines": 123,
			// "errors": 123,
			// "fecha_local": 123
			// }
			// ]

			obj.obtener("endpoint").poner(nuloSeguro(entrada.endpoint));
			obj.obtener("id").poner(nuloSeguro(entrada.id));
			obj.obtener("url").poner(nuloSeguro(entrada.url));
			obj.obtener("raw").poner(nuloSeguro(entrada.raw));
			obj.obtener("token").poner(nuloSeguro(entrada.token));
			obj.obtener("source").poner(nuloSeguro(entrada.source));

			obj.obtener("created").poner(entrada.created);
			obj.obtener("expires").poner(entrada.expires);
			obj.obtener("size").poner(entrada.size);
			obj.obtener("lines").poner(entrada.lines);
			obj.obtener("errors").poner(entrada.errors);
			obj.obtener("fecha_local").poner(System.currentTimeMillis());

			raiz.agregar(obj);

			Files.write(ARCHIVO, raiz.escribir().getBytes(StandardCharsets.UTF_8));
		} catch (Exception ignored) {
			// El historial no debe romper la publicación del registro.
		}
	}

	/**
	 * Evita guardar null como texto accidentalmente.
	 */
	private static String nuloSeguro(String s) {
		return s == null ? "" : s;
	}

	/**
	 * Carga el historial completo como arreglo JSON.
	 */
	public static synchronized Nodo cargarRaiz() {
		try {
			if (!Files.exists(ARCHIVO) || Files.size(ARCHIVO) <= 0) {
				return Json.leer("[]");
			}

			String txt = new String(Files.readAllBytes(ARCHIVO), StandardCharsets.UTF_8);
			if (txt.trim().isEmpty()) {
				return Json.leer("[]");
			}

			Nodo n = Json.leer(txt);
			if (n != null && n.esArreglo()) {
				return n;
			}
		} catch (Exception ignored) {
		}

		return Json.leer("[]");
	}

	public static class EntradaMCLogs {
		public String endpoint = "";
		public String id = "";
		public String url = "";
		public String raw = "";
		public String token = "";
		public String source = "";
		public long created = 0L;
		public long expires = 0L;
		public long size = 0L;
		public long lines = 0L;
		public long errors = 0L;
	}
}