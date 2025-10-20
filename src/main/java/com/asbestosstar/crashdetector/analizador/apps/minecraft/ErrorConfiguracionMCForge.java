package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorConfiguracionMCForge implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private String detalleArchivoHtml = "";

	// Patrón más flexible: acepta tanto
	// ConfigFileTypeHandler$ConfigLoadingException
	// como ConfigSpecWrapper$ConfigLoadingException (Moonlight), captura archivo y
	// modid.
	private static final Pattern P_FALLO_CARGA_CONFIG = Pattern.compile(
			"(?:ConfigFileTypeHandler|ConfigSpecWrapper)\\$ConfigLoadingException:\\s*Failed\\s+loading\\s+config\\s+file\\s+([^\\s]+)\\s+of\\s+type\\s+[^\\s]+\\s+for\\s+modid\\s+([^\\s]+)",
			Pattern.CASE_INSENSITIVE);

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		// Detectar condición principal: mensaje de ParsingException o cualquier
		// excepción de carga de config.
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];

			// Caso clásico Forge:
			if (linea.contains("ParsingException: Not enough data available")) {
				mensaje = MonitorDePID.idioma.errorConfigMCForge() + Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(i, this);
				activado = true;
				break;
			}

			// Caso alterno: excepciones de carga de config (Forge/Moonlight)
			if ((linea.contains("ConfigFileTypeHandler$ConfigLoadingException:")
					|| linea.contains("ConfigSpecWrapper$ConfigLoadingException:"))
					&& linea.contains("Failed loading config file")) {
				mensaje = MonitorDePID.idioma.errorConfigMCForge() + Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(i, this);
				activado = true;
				break;
			}
		}

		// Si se activó, intentar complementar con el detalle de archivo/modid en
		// cualquier parte del log.
		if (activado) {
			for (int i = 0; i < lineas.length; i++) {
				String l = lineas[i];
				if ((l.contains("ConfigFileTypeHandler$ConfigLoadingException:")
						|| l.contains("ConfigSpecWrapper$ConfigLoadingException:"))
						&& l.contains("Failed loading config file")) {

					Matcher m = P_FALLO_CARGA_CONFIG.matcher(l);
					if (m.find()) {
						String archivo = m.group(1);
						String modid = m.group(2);
						String enlaceDetalle = consola.agregarErrorALectador(i, this);
						detalleArchivoHtml = "<b>" + archivo + "</b> (modid: <code>" + modid + "</code>). "
								+ enlaceDetalle;
						break; // con el primer match es suficiente
					}
				}
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
		// Mensaje base + enlace del hallazgo + (opcional) detalle del archivo al final
		if (detalleArchivoHtml != null && !detalleArchivoHtml.isEmpty()) {
			return mensaje + enlaceHtml + Verificaciones.nl_html + detalleArchivoHtml;
		}
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_de_config_mcforge();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}

	@Override
	public String id() {
		return "config_mcforge";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false; // no consume trazos específicos
	}
}
