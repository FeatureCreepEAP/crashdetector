package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

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

	// Cadenas que se buscan en el log para detectar el problema.
	private final String azure = "AzureLib was initialized too early!";
	private final String geck = "GeckoLib was initialized too early!";

	/**
	 * Método de verificación "antiguo" que trabaja con todo el contenido de la
	 * consola. Ahora simplemente delega en la versión por línea para mantener
	 * compatibilidad sin duplicar lógica.
	 */
	@Override
	public void verificar(Consola consola) {

	}

	/**
	 * Verificación por línea. Este es el método que debe usarse en el nuevo flujo.
	 * Marca el error, registra el enlace al lector y actualiza el mensaje cuando
	 * detecta las cadenas relevantes.
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Detecta el error específico de AzureLib inicializada demasiado pronto.
		if (linea.contains(azure)) {
			azureLibError = true;
			activado = true;
			// Mantener la semántica original: AzureLib siempre sobrescribe el enlace.
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		}

		// Detecta el error específico de GeckoLib inicializada demasiado pronto.
		if (linea.contains(geck)) {
			geckoLibError = true;
			activado = true;
			// Solo registrar el enlace si aún no hay uno (mismo comportamiento original:
			// si ya hubo AzureLib, se mantiene ese enlace).
			if (enlaceHtml.isEmpty()) {
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			}
		}

		// Detecta tanto Sinytra Connector como specialcompatibilityoperation como
		// indicadores del mismo problema.
		if (linea.contains("SINYTRA CONNECTOR IS PRESENT!") || linea.contains("specialcompatibilityoperation")) {
			connectorPresente = true;
		}

		// Si ya se activó el error, actualizamos el mensaje que se va a mostrar.
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
		// Alta prioridad - error que impide la carga correcta de mods.
		return 950.0f;
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
		// ID estable para esta verificación.
		return "azuregeckolibinicialaizo";
	}

	/**
	 * Indica si este error está asociado a un trazo concreto de stack trace. En
	 * este caso, cualquier trazo que contenga las cadenas de AzureLib o GeckoLib
	 * inicializadas demasiado pronto se considera ocupado por esta verificación.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return trazo.trace.contains(geck) || trazo.trace.contains(azure);
	}

}
