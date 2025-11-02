package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta cuando el archivo de configuración de Sinytra Connector está
 * corrupto, típicamente lleno de caracteres nulos (\u0000), lo que impide que
 * el mod inicie.
 */
public class ErrorConfiguracionConnectorCorrupta implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		// Este método no se usa; el análisis se hace por línea
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (this.activado) {
			return;
		}

		if (linea.contains("org.sinytra.connector.locator.ConnectorConfig")
				&& linea.contains("Error loading Connector configuration")
				&& consola.contenido_verificar.contains("Not a JSON object: \"\\u0000")) {

			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorConfiguracionConnectorCorrupta() + enlaceHtml;
			this.activado = true;
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		String t = trazo.trace;
		return t.contains("org.sinytra.connector.locator.ConnectorConfig") && t.contains("Not a JSON object")
				&& t.contains("\\u0000");
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
}