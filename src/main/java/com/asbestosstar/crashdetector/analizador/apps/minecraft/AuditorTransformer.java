package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class AuditorTransformer implements Verificaciones {

	private boolean activado = false;

	private int auditIndex = 0;

	private final List<EntradaAudit> entradas = new ArrayList<>();
	private final Map<String, String> enlacesPorEntrada = new HashMap<>();
	private final Set<String> procesados = new HashSet<>();

	private final Map<String, List<String>> cacheJarsPorMixin = new HashMap<>();
	private final Map<String, List<String>> cacheJarsPorModid = new HashMap<>();

	private static final String[] LISTA_DE_DENEGADOS = { "PLUGIN: runtimedistcleaner", "PLUGIN: accesstransformer",
			"PLUGIN: crashdetector", "REASON: classloading", "TRANSFORMER: crashdetector" };

	@Override
	public String[] patronesRapidos() {
		return new String[] { "Transformer Audit:", "TRANSFORMER:", "PLUGIN:", "REASON:" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null)
			return;

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (linea == null) {
			return;
		}

		int inicio = inicioSinEspacios(linea);
		int fin = finSinEspacios(linea);

		if (fin <= inicio) {
			return;
		}

		try {
			if (empiezaCon(linea, inicio, fin, "Transformer Audit:")) {
				auditIndex++;
				return;
			}

			if (!esEntradaValida(linea, inicio, fin)) {
				return;
			}

			if (estaEnDenylist(linea, inicio, fin)) {
				return;
			}

			String línea = linea.substring(inicio, fin);

			if (procesados.contains(línea)) {
				return;
			}

			if (esEntradaExcluida(línea)) {
				return;
			}

			procesados.add(línea);
			procesarEntrada(consola, línea, numero_de_linea);
		} catch (Exception e) {
			CrashDetectorLogger.log("Error procesando línea: " + linea);
		}
	}

	/**
	 * Procesa una entrada válida del auditor de transformers.
	 */
	private void procesarEntrada(Consola consola, String línea, int numero_de_linea) {
		String textoBase = línea;
		String puntuaciónStr = "";
		float puntuación = auditIndex + (entradas.size() % 100) / 100f;

		int inicioParentesis = línea.indexOf('(');
		int finParentesis = inicioParentesis >= 0 ? línea.indexOf(')', inicioParentesis + 1) : -1;

		if (inicioParentesis > 0 && finParentesis > inicioParentesis) {
			puntuaciónStr = " (" + línea.substring(inicioParentesis + 1, finParentesis) + ")";
			textoBase = línea.substring(0, inicioParentesis).trim();
		}

		List<String> jars = obtenerJarsRelacionados(línea);

		String jarsStr = "";

		if (!jars.isEmpty()) {
			jarsStr = " <strong>[" + String.join(", ", jars) + "]</strong>";
		}

		String textoParaMostrar = textoBase + jarsStr + puntuaciónStr;

		String enlace = consola.agregarErrorALectador(numero_de_linea, this);
		enlacesPorEntrada.put(textoParaMostrar, enlace);

		entradas.add(new EntradaAudit(textoParaMostrar, puntuación));
		activado = true;
	}

	/**
	 * Busca el jar relacionado con una línea del auditor.
	 *
	 * Reemplaza los Pattern/Matcher anteriores: - mixin:APP:([^:\s]+) -
	 * TRANSFORMER: fml:([^:\s]+)
	 */
	private List<String> obtenerJarsRelacionados(String línea) {
		if (línea.indexOf("mixin:APP:") >= 0) {
			String jsonFile = extraerDespuesHastaSeparador(línea, "mixin:APP:");

			if (jsonFile != null && !jsonFile.isEmpty()) {
				List<String> cacheado = cacheJarsPorMixin.get(jsonFile);

				if (cacheado != null) {
					return cacheado;
				}

				List<String> jars = Buscador.obtenerUbicaciones(Buscador.buscarModsConTermino(jsonFile));
				cacheJarsPorMixin.put(jsonFile, jars);
				return jars;
			}
		}

		if (línea.startsWith("TRANSFORMER: fml:")) {
			String modid = extraerDespuesHastaSeparador(línea, "TRANSFORMER: fml:");

			if (modid != null && !modid.isEmpty()) {
				List<String> cacheado = cacheJarsPorModid.get(modid);

				if (cacheado != null) {
					return cacheado;
				}

				CrashDetectorLogger.log(modid);

				List<String> jars = Buscador.obtenerModsConNombre(modid);
				cacheJarsPorModid.put(modid, jars);
				return jars;
			}
		}

		return new ArrayList<>();
	}

	/**
	 * Verifica si una línea contiene una entrada válida.
	 */
	private boolean esEntradaValida(String línea, int inicio, int fin) {
		return empiezaCon(línea, inicio, fin, "REASON") || empiezaCon(línea, inicio, fin, "TRANSFORMER")
				|| empiezaCon(línea, inicio, fin, "PLUGIN");
	}

	/**
	 * Verifica si la línea está en la lista negada.
	 */
	private boolean estaEnDenylist(String línea, int inicio, int fin) {
		for (String bloque : LISTA_DE_DENEGADOS) {
			if (empiezaCon(línea, inicio, fin, bloque)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Devuelve true si la línea contiene una entrada excluida: - mixin -
	 * mixin:AFTER - computing_frames
	 *
	 * Versión sin split().
	 */
	private boolean esEntradaExcluida(String línea) {
		int separador = línea.indexOf(": ");

		if (separador < 0) {
			return false;
		}

		int inicioValor = separador + 2;

		while (inicioValor < línea.length() && Character.isWhitespace(línea.charAt(inicioValor))) {
			inicioValor++;
		}

		int finValor = inicioValor;

		while (finValor < línea.length() && !Character.isWhitespace(línea.charAt(finValor))) {
			finValor++;
		}

		if (finValor <= inicioValor) {
			return false;
		}

		String valor = línea.substring(inicioValor, finValor);

		return valor.equals("mixin") || valor.equals("mixin:AFTER") || valor.equals("computing_frames");
	}

	/**
	 * Extrae texto después de un prefijo hasta ':' o whitespace.
	 */
	private String extraerDespuesHastaSeparador(String línea, String prefijo) {
		int inicio = línea.indexOf(prefijo);

		if (inicio < 0) {
			return null;
		}

		inicio += prefijo.length();

		int fin = inicio;

		while (fin < línea.length()) {
			char c = línea.charAt(fin);

			if (c == ':' || Character.isWhitespace(c)) {
				break;
			}

			fin++;
		}

		if (fin <= inicio) {
			return null;
		}

		return línea.substring(inicio, fin);
	}

	private int inicioSinEspacios(String linea) {
		int i = 0;

		while (i < linea.length() && Character.isWhitespace(linea.charAt(i))) {
			i++;
		}

		return i;
	}

	private int finSinEspacios(String linea) {
		int i = linea.length();

		while (i > 0 && Character.isWhitespace(linea.charAt(i - 1))) {
			i--;
		}

		return i;
	}

	private boolean empiezaCon(String linea, int inicio, int fin, String prefijo) {
		int longitud = prefijo.length();

		if (inicio + longitud > fin) {
			return false;
		}

		return linea.regionMatches(inicio, prefijo, 0, longitud);
	}

	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		// No necesita procesamiento final.
	}

	@Override
	public String mensaje() {
		if (!activado || entradas.isEmpty()) {
			return "";
		}

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
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "auditortransformer";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}