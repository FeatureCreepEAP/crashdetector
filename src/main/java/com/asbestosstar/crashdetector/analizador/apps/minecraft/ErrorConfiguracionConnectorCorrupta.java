package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta cuando el archivo de configuración de Sinytra Connector está
 * corrupto, típicamente lleno de caracteres nulos (\u0000), lo que impide que
 * el mod inicie.
 */
public class ErrorConfiguracionConnectorCorrupta implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	private boolean vioConnectorConfig = false;
	private boolean vioErrorConfig = false;
	private boolean vioJsonNulo = false;

	private static final String CONNECTOR_CONFIG = "org.sinytra.connector.locator.ConnectorConfig";
	private static final String ERROR_LOADING_CONFIG = "Error loading Connector configuration";
	private static final String NOT_JSON_NUL = "Not a JSON object: \"\\u0000";
	private static final String NOT_JSON_OBJECT = "Not a JSON object";
	private static final String U0000 = "\\u0000";

	@Override
	public String[] patronesRapidos() {
		return new String[] { CONNECTOR_CONFIG, ERROR_LOADING_CONFIG, NOT_JSON_NUL };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(CONNECTOR_CONFIG)) {
			vioConnectorConfig = true;
		}

		if (linea.contains(ERROR_LOADING_CONFIG)) {
			vioErrorConfig = true;
		}

		if (linea.contains(NOT_JSON_NUL)) {
			vioJsonNulo = true;
		}

		if (vioConnectorConfig && vioErrorConfig && vioJsonNulo) {
			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorConfiguracionConnectorCorrupta() + enlaceHtml;
			this.activado = true;
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;
		return t.contains(CONNECTOR_CONFIG) && t.contains(NOT_JSON_OBJECT) && t.contains(U0000);
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorConfiguracionConnectorCorrupta();
	}

	@Override
	public boolean activado() {
		return this.activado;
	}

	@Override
	public String mensaje() {
		return this.mensaje;
	}

	@Override
	public float prioridad() {
		return 800.0f; // Alta: impide la carga correcta de mods
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucionConfiguracionConnectorCorrupta()).construir();
	}

	@Override
	public String id() {
		return "configuracion_connector_corrupta";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorConfiguracionConnectorCorrupta();
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}