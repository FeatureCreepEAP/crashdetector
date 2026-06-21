package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre MoniLabs y Connector Extras relacionados con
 * modificaciones de KubeJS, específicamente con el handler de KubeJSPlugins.
 */
public class ConflictoMoniLabsConnectorExtras implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String MONILABS = "monilabs";
	private static final String CONNECTOR_EXTRAS = "connectorextras";
	private static final String KUBEJS_HANDLER = "dev.latvian.mods.kubejs.util.KubeJSPlugins.handler";
	private static final String MONILABS_INJECT = "$monilabs$moniLabs$injectBeforeLoad";

	private static final Set<String> REPORTADOS = Collections.synchronizedSet(new HashSet<String>());

	public static void reiniciarGlobal() {
		REPORTADOS.clear();
	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { MONILABS, CONNECTOR_EXTRAS, KUBEJS_HANDLER, MONILABS_INJECT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del conflicto entre MoniLabs y Connector
	 * Extras relacionado con modificaciones de KubeJS en KubeJSPlugins.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el conflicto entre MoniLabs y Connector Extras
		// con KubeJS
		if (linea.contains(KUBEJS_HANDLER) && linea.contains(MONILABS_INJECT)) {
			activar(consola, numero_de_linea);
		}
	}

	private void activar(Consola consola, int numero_de_linea) {
		if (!REPORTADOS.add(id())) {
			return;
		}

		// Enlazar a la línea del error en el lector
		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		// Mensaje de error en HTML con referencia al conflicto entre MoniLabs y
		// Connector Extras
		mensaje = MonitorDePID.idioma.errorConflictoMoniLabsConnectorExtras() + VerificacionesLegacy.nl_html;
		activado = true;
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ConflictoMoniLabsConnectorExtras();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 850.0f; // Prioridad media-alta: rompe funcionalidad de KubeJS
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeConflictoMoniLabsConnectorExtras();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.pasoConflictoMoniLabsConnectorExtras()).construir();
	}

	@Override
	public String id() {
		return "conflicto_monilabs_connectorextras";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del conflicto entre
	 * MoniLabs y Connector Extras.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains(KUBEJS_HANDLER) && t.contains(MONILABS_INJECT) && t.contains(CONNECTOR_EXTRAS);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}