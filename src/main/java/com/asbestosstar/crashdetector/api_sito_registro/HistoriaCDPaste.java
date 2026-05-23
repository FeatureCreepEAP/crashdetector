package com.asbestosstar.crashdetector.api_sito_registro;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;

/**
 * Maneja el historial local de registros publicados en CDPaste.
 *
 * Archivo:
 * ~/crash_detector/historia_cdpaste.txt
 */
public class HistoriaCDPaste {

	public static final Path ARCHIVO = Paths.get(System.getProperty("user.home"), "crash_detector",
			"historia_cdpaste.txt");

	/**
	 * Agrega una entrada nueva al historial.
	 */
	public static synchronized void agregar(EntradaCDPaste entrada) {
		if (entrada == null)
			return;

		try {
			Files.createDirectories(ARCHIVO.getParent());

			Nodo raiz = cargarRaiz();
			Nodo obj = Json.crearObjeto();

			obj.obtener("endpoint").poner(nuloSeguro(entrada.endpoint));
			obj.obtener("id").poner(nuloSeguro(entrada.id));
			obj.obtener("url").poner(nuloSeguro(entrada.url));
			obj.obtener("raw").poner(nuloSeguro(entrada.raw));
			obj.obtener("source").poner(nuloSeguro(entrada.source));

			obj.obtener("size").poner(entrada.size);
			obj.obtener("lines").poner(entrada.lines);
			obj.obtener("fecha_local").poner(System.currentTimeMillis());

			raiz.agregar(obj);

			Files.write(ARCHIVO, raiz.escribir().getBytes(StandardCharsets.UTF_8));
		} catch (Exception ignored) {
			// El historial no debe romper la publicación del registro.
		}
	}

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

	public static class EntradaCDPaste {
		public String endpoint = "";
		public String id = "";
		public String url = "";
		public String raw = "";
		public String source = "";
		public long size = 0L;
		public long lines = 0L;
	}
}