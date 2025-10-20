package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;

public class AuditorTransformer implements Verificaciones {

	private boolean activado = false;
	private final List<EntradaAudit> entradas = new ArrayList<>();
	private final Map<String, String> enlacesPorEntrada = new HashMap<>();
	private static final String[] LISTA_DE_DENEGADOS = { "PLUGIN: runtimedistcleaner", "PLUGIN: accesstransformer",
			"PLUGIN: crashdetector", "REASON: classloading", "TRANSFORMER: crashdetector" };

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null)
			return;

		entradas.clear();
		enlacesPorEntrada.clear();
		Set<String> procesados = new HashSet<>();

		String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);
		if (lineas.length == 0)
			return;

		int auditIndex = 0;

		for (int i = lineas.length - 1; i >= 0; i--) {
			String línea = lineas[i].trim();
			if (línea.isEmpty())
				continue;

			try {
				if (línea.startsWith("Transformer Audit:")) {
					auditIndex++;
					continue;
				}

				if (esEntradaValida(línea) && !estaEnDenylist(línea)) {
					if (procesados.contains(línea))
						continue;
					if (esEntradaExcluida(línea))
						continue;

					procesados.add(línea);

					// Extraer texto base y puntuación
					String textoBase = línea;
					String puntuaciónStr = "";
					float puntuación = auditIndex + (entradas.size() % 100) / 100f;

					if (línea.contains("(") && línea.contains(")")) {
						int inicio = línea.indexOf('(');
						int fin = línea.indexOf(')');
						if (inicio > 0 && fin > inicio) {
							puntuaciónStr = " (" + línea.substring(inicio + 1, fin) + ")";
							textoBase = línea.substring(0, inicio).trim();
						}
					}

					List<String> jars = new ArrayList<>();

					if (línea.contains("mixin:APP:")) {
						Matcher jsonMatcher = Pattern.compile("mixin:APP:([^:\\s]+)").matcher(línea);
						if (jsonMatcher.find()) {
							String jsonFile = jsonMatcher.group(1);
							jars = Buscardor.obtenerUbicaciones(Buscardor.buscarModsConTermino(jsonFile));
						}
					} else if (línea.startsWith("TRANSFORMER: fml:")) {
						Matcher modidMatcher = Pattern.compile("TRANSFORMER: fml:([^:\\s]+)").matcher(línea);
						if (modidMatcher.find()) {
							String modid = modidMatcher.group(1);
							CrashDetectorLogger.log(modid);
							jars = Buscardor.obtenerModsConNombre(modid);
						}
					}

					String jarsStr = "";
					if (!jars.isEmpty()) {
						jarsStr = " <strong>[" + String.join(", ", jars) + "]</strong>";
					}

					// Reconstruir texto final con JARs antes de la puntuación
					String textoParaMostrar = textoBase + jarsStr + puntuaciónStr;

					// Registrar el error en el sistema de lectura
					String enlace = consola.agregarErrorALectador(i, this);
					enlacesPorEntrada.put(textoParaMostrar, enlace);

					entradas.add(new EntradaAudit(textoParaMostrar, puntuación));
				}
			} catch (Exception e) {
				CrashDetectorLogger.log("Error procesando línea: " + línea);
			}
		}

		activado = !entradas.isEmpty();
	}

	/**
	 * Verifica si una línea contiene una entrada válida (REASON, TRANSFORMER,
	 * PLUGIN)
	 *
	 * @param línea Línea a verificar
	 * @return true si la línea es una entrada válida
	 */
	private boolean esEntradaValida(String línea) {
		return línea.startsWith("REASON") || línea.startsWith("TRANSFORMER") || línea.startsWith("PLUGIN");
	}

	/**
	 * Verifica si la línea está en la lista negada
	 *
	 * @param línea Línea a verificar
	 * @return true si la línea debe denegarse
	 */
	private boolean estaEnDenylist(String línea) {
		for (String bloque : LISTA_DE_DENEGADOS) {
			if (línea.startsWith(bloque)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Devuelve true si la línea contiene una entrada excluida: - "mixin" -
	 * "mixin:AFTER" - "computing_frames"
	 *
	 * @param línea Línea a verificar
	 * @return true si debe excluirse
	 */
	private boolean esEntradaExcluida(String línea) {
		if (!línea.contains(": "))
			return false;

		String[] partes = línea.split(": ", 2);
		String valor = partes[1].split(" ")[0];

		return valor.equals("mixin") || valor.equals("mixin:AFTER") || valor.equals("computing_frames");
	}

	@Override
	public String mensaje() {
		if (!activado || entradas.isEmpty())
			return "";

		StringBuilder html = new StringBuilder();
		html.append(MonitorDePID.idioma.auditorias_transformer_detectadas());
		html.append("<ul>");

		for (EntradaAudit entrada : entradas) {
			String enlace = enlacesPorEntrada.getOrDefault(entrada.texto, "");
			html.append("<li>").append(entrada.texto).append(" ").append(enlace).append("</li>");
		}

		html.append("</ul>");
		return html.toString();
	}

	private static class EntradaAudit {
		String texto;
		float score;

		EntradaAudit(String texto, float score) {
			this.texto = texto;
			this.score = score;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new AuditorTransformer();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 2.5f;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.auditorias_transformer();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "auditortransformer";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

}