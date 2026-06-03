package com.asbestosstar.crashdetector.gui.tipos.scriptide;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Lee y expone la configuracion de un lenguaje en formato VSCode/TextMate
 * (language-configuration.json).
 *
 * Incluye: - Comentarios de linea y bloque - Pares de brackets (apertura /
 * cierre) - Pares de cierre automatico - Pares de envolvimiento de seleccion
 */
public class ConfigLenguajeTextMate {

	// -----------------------------------------------------------------------
	// Modelo de datos
	// -----------------------------------------------------------------------

	/** Simbolo de comentario de linea (ej: "//"). */
	public String comentarioLinea = "";

	/** Inicio de comentario de bloque (ej: "/*"). */
	public String comentarioBloqueInicio = "";

	public String comentarioBloqueEnd = "";

	/** Pares de brackets definidos por el lenguaje. */
	public final List<Par> brackets = new ArrayList<>();

	/** Pares que se cierran automaticamente al escribir la apertura. */
	public final List<Par> autoClosingPairs = new ArrayList<>();

	/** Pares que se usan para rodear una seleccion. */
	public final List<Par> surroundingPairs = new ArrayList<>();

	// -----------------------------------------------------------------------
	// Par apertura/cierre
	// -----------------------------------------------------------------------

	public static class Par {
		public final String apertura;
		public final String cierre;

		public Par(String apertura, String cierre) {
			this.apertura = apertura;
			this.cierre = cierre;
		}

		@Override
		public String toString() {
			return apertura + cierre;
		}
	}

	// -----------------------------------------------------------------------
	// Carga desde archivo
	// -----------------------------------------------------------------------

	/**
	 * Lee y parsea un archivo language-configuration.json. Devuelve un
	 * ConfigLenguajeTextMate listo para usar, o una instancia vacia si el archivo
	 * no existe o hay error de parseo.
	 */
	public static ConfigLenguajeTextMate cargar(File archivo) {
		ConfigLenguajeTextMate cfg = new ConfigLenguajeTextMate();

		if (archivo == null || !archivo.isFile()) {
			return cfg;
		}

		try {
			String json = new String(Files.readAllBytes(archivo.toPath()), StandardCharsets.UTF_8);
			parsearJson(json, cfg);
		} catch (Throwable t) {
			CrashDetectorLogger.log("No se pudo cargar configuracion de lenguaje TextMate: " + t.getMessage());
		}

		return cfg;
	}

	// -----------------------------------------------------------------------
	// Parseo JSON
	// -----------------------------------------------------------------------

	private static void parsearJson(String json, ConfigLenguajeTextMate cfg) {
		Json.Nodo raiz = Json.leer(json);
		if (raiz == null) {
			return;
		}

		// comments
		Json.Nodo comments = raiz.obtener("comments");
		if (comments != null) {
			Json.Nodo lc = comments.obtener("lineComment");
			if (lc != null) {
				cfg.comentarioLinea = lc.comoCadena();
			}

			Json.Nodo bc = comments.obtener("blockComment");
			if (bc != null && bc.esArreglo() && bc.tamano() >= 2) {
				cfg.comentarioBloqueInicio = bc.en(0).comoCadena();
				cfg.comentarioBloqueEnd = bc.en(1).comoCadena();
			}
		}

		// brackets
		Json.Nodo brackets = raiz.obtener("brackets");
		if (brackets != null && brackets.esArreglo()) {
			cfg.brackets.addAll(leerListaPares(brackets));
		}

		// autoClosingPairs
		Json.Nodo acp = raiz.obtener("autoClosingPairs");
		if (acp != null && acp.esArreglo()) {
			cfg.autoClosingPairs.addAll(leerListaPares(acp));
		}

		// surroundingPairs
		Json.Nodo sp = raiz.obtener("surroundingPairs");
		if (sp != null && sp.esArreglo()) {
			cfg.surroundingPairs.addAll(leerListaPares(sp));
		}
	}

	/**
	 * Lee una lista JSON de pares. Cada elemento puede ser: - Arreglo de dos
	 * cadenas: ["(", ")"] - Objeto con open/close: {"open": "(", "close": ")"}
	 */
	private static List<Par> leerListaPares(Json.Nodo arreglo) {
		List<Par> lista = new ArrayList<>();
		int tam = arreglo.tamano();

		for (int i = 0; i < tam; i++) {
			Json.Nodo item = arreglo.en(i);
			if (item == null) {
				continue;
			}

			if (item.esArreglo() && item.tamano() >= 2) {
				// formato: ["(", ")"]
				String ap = item.en(0).comoCadena();
				String ci = item.en(1).comoCadena();
				if (ap != null && ci != null) {
					lista.add(new Par(ap, ci));
				}
			} else if (item.esObjeto()) {
				// formato: {"open": "(", "close": ")"}
				Json.Nodo nAp = item.obtener("open");
				Json.Nodo nCi = item.obtener("close");
				if (nAp == null) {
					nAp = item.obtener("apertura");
				}
				if (nCi == null) {
					nCi = item.obtener("cierre");
				}
				if (nAp != null && nCi != null) {
					lista.add(new Par(nAp.comoCadena(), nCi.comoCadena()));
				}
			}
		}

		return lista;
	}

	// -----------------------------------------------------------------------
	// Consultas utiles
	// -----------------------------------------------------------------------

	/**
	 * Devuelve el cierre automatico correspondiente a la cadena de apertura, o null
	 * si no hay cierre definido para ella.
	 */
	public String cierreAutomaticoPara(String apertura) {
		for (Par par : autoClosingPairs) {
			if (par.apertura.equals(apertura)) {
				return par.cierre;
			}
		}
		return null;
	}

	/**
	 * Devuelve true si el caracter dado es un simbolo de apertura de bracket.
	 */
	public boolean esAperturaBracket(char c) {
		String s = String.valueOf(c);
		for (Par par : brackets) {
			if (par.apertura.equals(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Devuelve true si la configuracion tiene datos utiles (no esta vacia).
	 */
	public boolean tieneContenido() {
		return !brackets.isEmpty() || !autoClosingPairs.isEmpty() || !comentarioLinea.isEmpty()
				|| !comentarioBloqueInicio.isEmpty();
	}
}
