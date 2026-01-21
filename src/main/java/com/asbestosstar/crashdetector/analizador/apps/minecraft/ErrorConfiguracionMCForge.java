package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorConfiguracionMCForge implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private String detalleArchivoHtml = "";
	private String listaArchivosVaciosHtml = "";

	// Patrón más flexible: acepta tanto
	// ConfigFileTypeHandler$ConfigLoadingException
	// como ConfigSpecWrapper$ConfigLoadingException (Moonlight), captura archivo y
	// modid.
	private static final Pattern P_FALLO_CARGA_CONFIG = Pattern.compile(
			"(?:ConfigFileTypeHandler|ConfigSpecWrapper)\\$ConfigLoadingException:\\s*Failed\\s+loading\\s+config\\s+file\\s+([^\\s]+)\\s+of\\s+type\\s+[^\\s]+\\s+for\\s+modid\\s+([^\\s]+)",
			Pattern.CASE_INSENSITIVE);

	private static final String PARSING_EXCEPTION = "ParsingException: Not enough data available";
	private static final String CFG_HANDLER = "ConfigFileTypeHandler$ConfigLoadingException:";
	private static final String CFG_WRAPPER = "ConfigSpecWrapper$ConfigLoadingException:";
	private static final String CFG_FAILED = "Failed loading config file";

	/**
	 * Verificación global no utilizada en este verificador.
	 * <p>
	 * La detección se hace por línea en {@link #verificar(Consola, String, int)},
	 * llamado por el sistema de análisis línea a línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se usa: este verificador funciona en modo por línea.
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
	 * Además, intenta extraer archivo y modid usando {@link #P_FALLO_CARGA_CONFIG}.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// 1) Detección base del error (si aún no se ha activado).
		if (!activado) {
			// Caso clásico Forge
			if (linea.contains(PARSING_EXCEPTION)) {
				mensaje = MonitorDePID.idioma.errorConfigMCForge() + Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;
				generarListaArchivosVacios();
			}
			// Caso alterno: excepciones de carga de config (Forge/Moonlight)
			else if ((linea.contains(CFG_HANDLER) || linea.contains(CFG_WRAPPER)) && linea.contains(CFG_FAILED)) {
				mensaje = MonitorDePID.idioma.errorConfigMCForge() + Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				activado = true;
				generarListaArchivosVacios();
			}
		}

		// 2) (Opcional) Intentar complementar con detalle de archivo/modid.
		// No exigimos que "activado" sea true aquí: puede aparecer antes o después.
		// Solo se mostrará si al final el verificador está activado.
		if ((detalleArchivoHtml == null || detalleArchivoHtml.isEmpty())
				&& (linea.contains(CFG_HANDLER) || linea.contains(CFG_WRAPPER)) && linea.contains(CFG_FAILED)) {

			Matcher m = P_FALLO_CARGA_CONFIG.matcher(linea);
			if (m.find()) {
				String archivo = m.group(1);
				String modid = m.group(2);
				String enlaceDetalle = consola.agregarErrorALectador(numero_de_linea, this);
				detalleArchivoHtml = "<b>" + archivo + "</b> (modid: <code>" + modid + "</code>). " + enlaceDetalle;
			}
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

		List<String> archivosVacios = new ArrayList<>();
		listarArchivosVaciosRecursivo(carpetaConfig, carpetaConfig, archivosVacios);

		if (!archivosVacios.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			// sb.append(MonitorDePID.idioma.archivos_config_vacios_encontrados())
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
		} else if (actual.isFile()) {
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