package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ErrorConfiguracionMCForge implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;

	private String mensaje = "";
	private String enlaceHtml = "";
	private String detalleArchivoHtml = "";
	private String listaArchivosVaciosHtml = "";

	private static final String PARSING_EXCEPTION = "ParsingException: Not enough data available";
	private static final String CFG_HANDLER = "ConfigFileTypeHandler$ConfigLoadingException:";
	private static final String CFG_WRAPPER = "ConfigSpecWrapper$ConfigLoadingException:";
	private static final String CFG_FAILED = "Failed loading config file";

	/**
	 * Verificación global barata.
	 * <p>
	 * Si el log completo no contiene señales relacionadas con errores de carga de
	 * configuración, no se analiza línea por línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {

		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(PARSING_EXCEPTION)
				|| ((contenido.contains(CFG_HANDLER) || contenido.contains(CFG_WRAPPER))
						&& contenido.contains(CFG_FAILED))) {

			this.analizarLineas = true;
		}
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Detecta:
	 * <ul>
	 * <li>El caso clásico Forge: "ParsingException: Not enough data available"</li>
	 * <li>El caso alterno Forge/Moonlight:
	 * "<code>ConfigFileTypeHandler$ConfigLoadingException</code>" o
	 * "<code>ConfigSpecWrapper$ConfigLoadingException</code>" con "Failed loading
	 * config file".</li>
	 * </ul>
	 * Además, intenta extraer archivo y modid sin regex.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas) {
			return;
		}

		if (linea == null) {
			return;
		}

		boolean esErrorConfig = false;

		// 1) Detección base del error (si aún no se ha activado).

		if (!activado) {

			// Caso clásico Forge
			if (linea.contains(PARSING_EXCEPTION)) {

				mensaje = MonitorDePID.idioma.errorConfigMCForge() + Verificaciones.nl_html;

				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

				activado = true;
				esErrorConfig = true;

				generarListaArchivosVacios();
			}

			// Caso alterno: excepciones de carga de config (Forge/Moonlight)
			else if ((linea.contains(CFG_HANDLER) || linea.contains(CFG_WRAPPER)) && linea.contains(CFG_FAILED)) {

				mensaje = MonitorDePID.idioma.errorConfigMCForge() + Verificaciones.nl_html;

				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

				activado = true;
				esErrorConfig = true;

				generarListaArchivosVacios();
			}
		}

		// 2) (Opcional) Intentar complementar con detalle de archivo/modid.
		// No exigimos que "activado" sea true aquí: puede aparecer antes o después.
		// Solo se mostrará si al final el verificador está activado.

		if ((detalleArchivoHtml == null || detalleArchivoHtml.isEmpty())
				&& ((linea.contains(CFG_HANDLER) || linea.contains(CFG_WRAPPER)) && linea.contains(CFG_FAILED))) {

			ResultadoConfig resultado = extraerArchivoYModid(linea);

			if (resultado != null) {

				String enlaceDetalle = consola.agregarErrorALectador(numero_de_linea, this);

				detalleArchivoHtml = "<b>" + resultado.archivo + "</b> (modid: <code>" + resultado.modid + "</code>). "
						+ enlaceDetalle;
			}
		}
	}

	/**
	 * Extrae archivo y modid sin regex.
	 */
	private ResultadoConfig extraerArchivoYModid(String linea) {

		int posFailed = linea.indexOf("Failed loading config file");
		if (posFailed < 0) {
			return null;
		}

		int inicioArchivo = posFailed + "Failed loading config file".length();
		inicioArchivo = saltarEspacios(linea, inicioArchivo);

		int posOfType = linea.indexOf(" of type ", inicioArchivo);
		if (posOfType < 0 || posOfType <= inicioArchivo) {
			return null;
		}

		String archivo = linea.substring(inicioArchivo, posOfType).trim();
		if (archivo.isEmpty()) {
			return null;
		}

		int posForModid = linea.indexOf(" for modid ", posOfType);
		if (posForModid < 0) {
			return null;
		}

		int inicioModid = posForModid + " for modid ".length();
		inicioModid = saltarEspacios(linea, inicioModid);

		int finModid = inicioModid;

		while (finModid < linea.length() && !Character.isWhitespace(linea.charAt(finModid))) {

			finModid++;
		}

		if (finModid <= inicioModid) {
			return null;
		}

		String modid = linea.substring(inicioModid, finModid);

		return new ResultadoConfig(archivo, modid);
	}

	private int saltarEspacios(String texto, int inicio) {

		int i = inicio;

		while (i < texto.length() && Character.isWhitespace(texto.charAt(i))) {
			i++;
		}

		return i;
	}

	private static class ResultadoConfig {

		final String archivo;
		final String modid;

		ResultadoConfig(String archivo, String modid) {
			this.archivo = archivo;
			this.modid = modid;
		}
	}

	/**
	 * Genera una lista en HTML de todos los archivos vacíos dentro de la carpeta
	 * 'config' y sus subcarpetas, si existen.
	 */
	private void generarListaArchivosVacios() {

		File carpetaConfig = new File("config");

		if (!carpetaConfig.exists() || !carpetaConfig.isDirectory()) {
			listaArchivosVaciosHtml = "";
			return;
		}

		List<String> archivosVacios = new ArrayList<String>();

		listarArchivosVaciosRecursivo(carpetaConfig, carpetaConfig, archivosVacios);

		if (!archivosVacios.isEmpty()) {

			StringBuilder sb = new StringBuilder();

			sb.append(Verificaciones.nl_html);

			sb.append("<ul>");

			for (String relPath : archivosVacios) {
				sb.append("<li><code>").append(relPath).append("</code></li>");
			}

			sb.append("</ul>");

			listaArchivosVaciosHtml = sb.toString();

		} else {
			listaArchivosVaciosHtml = "";
		}
	}

	/**
	 * Recorre recursivamente la carpeta de configuración y añade a la lista los
	 * archivos cuyo tamaño es 0 bytes.
	 *
	 * @param raiz       Carpeta raíz ('config'), usada para calcular rutas
	 *                   relativas.
	 * @param actual     Archivo o carpeta actual en la recursión.
	 * @param acumulador Lista donde se acumulan las rutas relativas de archivos
	 *                   vacíos.
	 */
	private void listarArchivosVaciosRecursivo(File raiz, File actual, List<String> acumulador) {

		if (actual.isDirectory()) {

			File[] hijos = actual.listFiles();

			if (hijos != null) {

				for (File hijo : hijos) {
					listarArchivosVaciosRecursivo(raiz, hijo, acumulador);
				}
			}
		}

		else if (actual.isFile()) {

			try {

				if (Files.size(actual.toPath()) == 0) {

					String relPath = raiz.toURI().relativize(actual.toURI()).getPath();

					acumulador.add(relPath);
				}

			} catch (IOException e) {
				// Ignoramos archivos inaccesibles; no los incluimos en la lista.
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorConfiguracionMCForge();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1100.0f; // Prioridad alta para errores de configuración críticos
	}

	@Override
	public String mensaje() {

		if (!activado)
			return "";

		// Mensaje base + (opcional) lista de archivos vacíos + enlace del hallazgo +
		// (opcional) detalle del archivo

		StringBuilder sb = new StringBuilder();

		sb.append(mensaje);

		if (!listaArchivosVaciosHtml.isEmpty()) {
			sb.append(listaArchivosVaciosHtml).append(Verificaciones.nl_html);
		}

		sb.append(enlaceHtml);

		if (detalleArchivoHtml != null && !detalleArchivoHtml.isEmpty()) {
			sb.append(Verificaciones.nl_html).append(detalleArchivoHtml);
		}

		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_de_config_mcforge();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		return "config_mcforge";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo marca trazos que contienen las cadenas
	 * características de este fallo:
	 * <ul>
	 * <li>"ParsingException: Not enough data available", o</li>
	 * <li>"ConfigFileTypeHandler$ConfigLoadingException"/
	 * "ConfigSpecWrapper$ConfigLoadingException" junto con "Failed loading config
	 * file".</li>
	 * </ul>
	 * Es intencionadamente conservador (mejor falsos negativos que falsos
	 * positivos).
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (t.contains(PARSING_EXCEPTION)) {
			return true;
		}

		if ((t.contains(CFG_HANDLER) || t.contains(CFG_WRAPPER)) && t.contains(CFG_FAILED)) {

			return true;
		}

		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}