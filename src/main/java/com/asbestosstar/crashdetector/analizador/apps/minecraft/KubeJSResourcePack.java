package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class KubeJSResourcePack implements Verificaciones {

	private boolean activado = false;
	private final Set<String> errores = new HashSet<>();
	private final Map<String, String> enlacesPorError = new HashMap<>();

	private static final String KUBEJS_RESOURCE_PACK = "from pack KubeJS Resource Pack [data]";
	private static final String FAILED_TO_PARSE = "Failed to parse ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { KUBEJS_RESOURCE_PACK, FAILED_TO_PARSE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca líneas que contengan:
	 * <ul>
	 * <li>"from pack KubeJS Resource Pack [data]"</li>
	 * <li>"Failed to parse "</li>
	 * </ul>
	 * e intenta extraer el nombre "lógico" del recurso/mod que aparece justo
	 * después de "Failed to parse ".
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (linea == null || !lineaContieneKubeJSResourcePack(linea)) {
			return;
		}

		String modNombre = extraerModNombre(linea);
		String mensaje = MonitorDePID.idioma.kubeJSResourcePack(modNombre);

		// Solo registrar si es un error nuevo
		if (errores.add(mensaje)) {
			String enlace = consola.agregarErrorALectador(numero_de_linea, this);
			enlacesPorError.put(mensaje, enlace);
		}

		activado = true;
	}

	private boolean lineaContieneKubeJSResourcePack(String linea) {
		return linea.contains(KUBEJS_RESOURCE_PACK) && linea.contains(FAILED_TO_PARSE);
	}

	private String extraerModNombre(String linea) {
		int inicio = linea.indexOf(FAILED_TO_PARSE);
		if (inicio < 0) {
			return "";
		}

		inicio += FAILED_TO_PARSE.length();

		int fin = linea.indexOf(':', inicio);
		if (fin < 0 || fin <= inicio) {
			return "";
		}

		return limpiarEspacios(linea, inicio, fin);
	}

	private String limpiarEspacios(String texto, int inicio, int fin) {
		while (inicio < fin && texto.charAt(inicio) <= ' ') {
			inicio++;
		}

		while (fin > inicio && texto.charAt(fin - 1) <= ' ') {
			fin--;
		}

		if (inicio >= fin) {
			return "";
		}

		return texto.substring(inicio, fin);
	}

	@Override
	public Verificaciones nueva() {
		return new KubeJSResourcePack();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 300.0f; // Prioridad media-alta para errores de recursos críticos [[3]]
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		for (String error : errores) {
			String enlace = enlacesPorError.getOrDefault(error, "");
			html.append("<li>").append(error).append(" ").append(enlace).append("</li>");
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_faltar_de_kubejs_resourcepack();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		return "kubejs_resourcepack";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene las cadenas características del problema de KubeJS
	 * Resource Pack:</li>
	 * <ul>
	 * <li>"from pack KubeJS Resource Pack [data]"</li>
	 * <li>"Failed to parse "</li>
	 * </ul>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo a marcar un
	 * trazo que no corresponda realmente a este error.
	 * </p>
	 */
	@Override
	public String[] ocupaTrazo() {
		return new String[] { KUBEJS_RESOURCE_PACK, FAILED_TO_PARSE };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;// Intalacion corrupta
	}

}