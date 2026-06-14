package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre MoniLabs y Connector Extras relacionados con
 * modificaciones de KubeJS, específicamente con el handler de KubeJSPlugins.
 */
public class ConflictoMoniLabsConnectorExtras implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoMoniLabs = false;
	private boolean encontradoConnectorExtras = false;
	public boolean analizarLineas = false;

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

		if (linea.contains(MONILABS)) {
			encontradoMoniLabs = true;
		}

		if (linea.contains(CONNECTOR_EXTRAS)) {
			encontradoConnectorExtras = true;
		}

		if (linea.contains(KUBEJS_HANDLER) && linea.contains(MONILABS_INJECT)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Método de compatibilidad — busca si MoniLabs y Connector Extras están
	 * presentes en el contenido completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Modo streaming puro: puede no existir contenido_verificar
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		// Verificamos si MoniLabs y Connector Extras están presentes en el contenido
		// del registro
		String contenido = consola.contenido_verificar;
		encontradoMoniLabs = contenido.contains(MONILABS);
		encontradoConnectorExtras = contenido.contains(CONNECTOR_EXTRAS);

		if (contenido.contains(KUBEJS_HANDLER) && contenido.contains(MONILABS_INJECT) && encontradoMoniLabs
				&& encontradoConnectorExtras) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
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

		if (!analizarLineas || !encontradoMoniLabs || !encontradoConnectorExtras) {
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
		mensaje = MonitorDePID.idioma.errorConflictoMoniLabsConnectorExtras() + Verificaciones.nl_html;
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
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