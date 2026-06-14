package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre MoniLabs y Connector Extras relacionados con
 * modificaciones de KubeJS, específicamente con el handler de KubeJSPlugins.
 */
public class ConflictoMoniLabsConnectorExtras implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoMoniLabs = false;
	private boolean encontradoConnectorExtras = false;
	public boolean analizarLineas = false;

	/**
	 * Método de compatibilidad — busca si MoniLabs y Connector Extras están
	 * presentes en el contenido completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si MoniLabs y Connector Extras están presentes en el contenido
		// del registro
		if (consola.contenido_verificar != null) {
			String contenido = consola.contenido_verificar;
			encontradoMoniLabs = contenido.contains("monilabs");
			encontradoConnectorExtras = contenido.contains("connectorextras");

			if (consola.contenido_verificar.contains("dev.latvian.mods.kubejs.util.KubeJSPlugins.handler")
					&& consola.contenido_verificar.contains("$monilabs$moniLabs$injectBeforeLoad")
					&& (encontradoMoniLabs && encontradoConnectorExtras)) {
				analizarLineas = true;
			}

		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!analizarLineas)
			return false;

		return true;
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
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el conflicto entre MoniLabs y Connector Extras
		// con KubeJS
		if (linea.contains("dev.latvian.mods.kubejs.util.KubeJSPlugins.handler")
				&& linea.contains("$monilabs$moniLabs$injectBeforeLoad")
				&& (encontradoMoniLabs && encontradoConnectorExtras)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al conflicto entre MoniLabs y
			// Connector Extras
			mensaje = MonitorDePID.idioma.errorConflictoMoniLabsConnectorExtras() + Verificaciones.nl_html;
			activado = true;
		}
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

		return t.contains("dev.latvian.mods.kubejs.util.KubeJSPlugins.handler")
				&& t.contains("$monilabs$moniLabs$injectBeforeLoad") && t.toLowerCase().contains("connectorextras");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}