package com.asbestosstar.crashdetector.gui.tipos.scriptide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Statics;

/**
 * Descarga y localiza los archivos de gramatica TextMate y configuracion de
 * lenguaje para cada tipo de proyecto del IDE de scripts.
 *
 * Los archivos se guardan en:
 * ~/crash_detector/scriptide/textmate/{tipo}/syntax.tmLanguage.json
 * ~/crash_detector/scriptide/textmate/{tipo}/language-configuration.json
 */
public class DescargadorTextMateScriptIDE {

	/** Carpeta raiz donde se guardan todas las gramaticas. */
	public static final File CARPETA_TEXTMATE = new File(Statics.carpeta_mundial_como_archivo, "scriptide/textmate");

	// -----------------------------------------------------------------------
	// URLs de ZenScript (Jandakast/ZenScript-VSCodeLanguage)
	// -----------------------------------------------------------------------

	public static final String URL_ZENSCRIPT_SINTAXIS = "https://raw.githubusercontent.com/Jandakast/ZenScript-VSCodeLanguage"
			+ "/refs/heads/master/syntaxes/zenscript-syntax.tmLanguage.json";

	public static final String URL_ZENSCRIPT_CONFIG_LENGUAJE = "https://raw.githubusercontent.com/Jandakast/ZenScript-VSCodeLanguage"
			+ "/refs/heads/master/language-configuration.json";

	// -----------------------------------------------------------------------
	// Rutas locales
	// -----------------------------------------------------------------------

	/**
	 * Devuelve la carpeta local donde se guardan los archivos de un tipo de
	 * proyecto.
	 */
	public static File carpetaTipo(TipoProyectoScript tipo) {
		return new File(CARPETA_TEXTMATE, tipo.id);
	}

	/**
	 * Devuelve el archivo local de gramatica tmLanguage (JSON) para el tipo dado.
	 * Puede no existir si aun no se ha descargado.
	 */
	public static File archivoSintaxis(TipoProyectoScript tipo) {
		return new File(carpetaTipo(tipo), "syntax.tmLanguage.json");
	}

	/**
	 * Devuelve el archivo local de configuracion de lenguaje (JSON) para el tipo.
	 * Puede no existir si aun no se ha descargado.
	 */
	public static File archivoConfigLenguaje(TipoProyectoScript tipo) {
		return new File(carpetaTipo(tipo), "language-configuration.json");
	}

	// -----------------------------------------------------------------------
	// Descarga
	// -----------------------------------------------------------------------

	/**
	 * Resultado de una descarga individual: URL y ruta local destino.
	 */
	public static class EntradaDescarga {
		public final String url;
		public final File destino;
		public final String descripcion;

		public EntradaDescarga(String url, File destino, String descripcion) {
			this.url = url;
			this.destino = destino;
			this.descripcion = descripcion;
		}
	}

	/**
	 * Devuelve la lista de archivos que faltan por descargar para el tipo dado. Si
	 * no hay URLs definidas para ese tipo, devuelve lista vacia.
	 */
	public static java.util.List<EntradaDescarga> faltantesPara(TipoProyectoScript tipo) {
		java.util.List<EntradaDescarga> lista = new java.util.ArrayList<>();
		java.util.List<EntradaDescarga> todos = todasLasEntradasPara(tipo);

		for (EntradaDescarga e : todos) {
			if (!e.destino.exists() || e.destino.length() == 0L) {
				lista.add(e);
			}
		}

		return lista;
	}

	/**
	 * Devuelve todas las entradas de descarga definidas para un tipo de proyecto.
	 */
	public static java.util.List<EntradaDescarga> todasLasEntradasPara(TipoProyectoScript tipo) {
		java.util.List<EntradaDescarga> lista = new java.util.ArrayList<>();

		if (tipo == TipoProyectoScript.ZENSCRIPT) {
			lista.add(new EntradaDescarga(URL_ZENSCRIPT_SINTAXIS, archivoSintaxis(tipo),
					"ZenScript - gramatica TextMate"));
			lista.add(new EntradaDescarga(URL_ZENSCRIPT_CONFIG_LENGUAJE, archivoConfigLenguaje(tipo),
					"ZenScript - configuracion de lenguaje"));
		}

		// Agregar otros tipos de proyecto aqui segun se agreguen sus gramaticas
		return lista;
	}

	/**
	 * Resultado global de la descarga.
	 */
	public static class ResultadoDescarga {
		public int exitosas;
		public int fallidas;
		public StringBuilder detalle = new StringBuilder();

		public String mensaje() {
			return "Exitosas: " + exitosas + "  Fallidas: " + fallidas + "\n" + detalle.toString();
		}
	}

	/**
	 * Descarga los archivos de gramatica para el tipo indicado. Omite archivos que
	 * ya existen y tienen contenido. Llamar desde hilo de fondo.
	 */
	public static ResultadoDescarga descargar(TipoProyectoScript tipo) {
		return descargar(faltantesPara(tipo));
	}

	/**
	 * Descarga la lista de entradas dada.
	 */
	public static ResultadoDescarga descargar(java.util.List<EntradaDescarga> entradas) {
		ResultadoDescarga resultado = new ResultadoDescarga();

		for (EntradaDescarga entrada : entradas) {
			try {
				descargarArchivo(entrada.url, entrada.destino);
				resultado.exitosas++;
				resultado.detalle.append("[OK] ").append(entrada.descripcion).append('\n');
			} catch (Throwable t) {
				resultado.fallidas++;
				resultado.detalle.append("[ERROR] ").append(entrada.descripcion).append(": ").append(t.getMessage())
						.append('\n');
				CrashDetectorLogger.logException(t);
			}
		}

		return resultado;
	}

	/**
	 * Descarga un unico URL al archivo destino, creando los directorios necesarios.
	 */
	public static void descargarArchivo(String urlStr, File destino) throws Exception {
		destino.getParentFile().mkdirs();

		URL url = new URL(urlStr);
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		conexion.setConnectTimeout(15_000);
		conexion.setReadTimeout(30_000);
		conexion.setRequestProperty("User-Agent", "CrashDetector-ScriptIDE/1.0");

		int codigo = conexion.getResponseCode();
		if (codigo != HttpURLConnection.HTTP_OK) {
			throw new Exception("HTTP " + codigo + " para " + urlStr);
		}

		try (InputStream in = conexion.getInputStream(); FileOutputStream out = new FileOutputStream(destino)) {
			byte[] buf = new byte[8192];
			int n;
			while ((n = in.read(buf)) >= 0) {
				out.write(buf, 0, n);
			}
		}

		// Verificacion basica: el archivo debe ser JSON valido (empieza con '{')
		byte[] inicio = Files.readAllBytes(destino.toPath());
		String txt = new String(inicio, 0, Math.min(10, inicio.length), StandardCharsets.UTF_8).trim();
		if (!txt.startsWith("{") && !txt.startsWith("[")) {
			destino.delete();
			throw new Exception("Respuesta no es JSON valido en " + urlStr);
		}
	}

	/**
	 * Borra los archivos de gramatica descargados para el tipo dado, forzando que
	 * se descarguen de nuevo la proxima vez.
	 */
	public static void limpiarCache(TipoProyectoScript tipo) {
		File carpeta = carpetaTipo(tipo);
		File[] archivos = carpeta.listFiles();
		if (archivos == null) {
			return;
		}
		for (File f : archivos) {
			if (f != null && f.isFile()) {
				f.delete();
			}
		}
	}
}
