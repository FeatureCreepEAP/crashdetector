package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Analiza errores cuando AzureLib o GeckoLib se inicializan demasiado pronto.
 * Detecta específicamente los errores "AzureLib was initialized too early!" y
 * "GeckoLib was initialized too early!". Identifica la presencia de mods de
 * conexión como Sinytra Connector o specialcompatibilityoperation.
 */
public class AzureGeckoLibInicializoPronto implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private boolean azureLibError = false;
	private boolean geckoLibError = false;
	private boolean connectorPresente = false;
	private String enlaceHtml = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		// Analiza cada línea del registro para detectar los errores específicos
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains("AzureLib was initialized too early!")) {
				azureLibError = true;
				activado = true;
				enlaceHtml = consola.agregarErrorALectador(i, this);
			}
			if (linea.contains("GeckoLib was initialized too early!")) {
				geckoLibError = true;
				activado = true;
				// Solo sobrescribir el enlace si aún no se ha registrado (para mantener el
				// primero)
				if (enlaceHtml.isEmpty()) {
					enlaceHtml = consola.agregarErrorALectador(i, this);
				}
			}
			// Detecta tanto Sinytra Connector como specialcompatibilityoperation como
			// indicadores del mismo problema
			if (linea.contains("SINYTRA CONNECTOR IS PRESENT!") || linea.contains("specialcompatibilityoperation")) {
				connectorPresente = true;
			}
		}

		if (activado) {
			mensaje = MonitorDePID.idioma.errorAzureGeckoLibInicializoPronto(azureLibError, geckoLibError,
					connectorPresente) + Verificaciones.nl_html + enlaceHtml;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new AzureGeckoLibInicializoPronto();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Alta prioridad - error que impide la carga correcta de mods
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_azure_geckolib_inicializo_pronto();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_azure_geckolib_inicializo_pronto())
				.agregarEtiqueta(MonitorDePID.idioma.paso2_azure_geckolib_inicializo_pronto()).construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "azuregeckolibinicialaizo";
	}
	
}