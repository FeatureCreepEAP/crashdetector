package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ForgeLanguageProviderNoCarga implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	// Nombre del provider que no pudo cargarse
	private String providerFallido = "";

	private static final String SERVICE_CONFIGURATION_ERROR = "ServiceConfigurationError";
	private static final String IMOD_LANGUAGE_PROVIDER = "IModLanguageProvider";
	private static final String UNABLE_TO_LOAD = "Unable to load";

	@Override
	public String[] patronesRapidos() {
		return new String[] { SERVICE_CONFIGURATION_ERROR, IMOD_LANGUAGE_PROVIDER, UNABLE_TO_LOAD };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		// Verificación precisa en la línea exacta
		if (lineaContieneFalloProvider(linea)) {

			// Extraer el nombre completo del provider que falló
			int indice = linea.indexOf(UNABLE_TO_LOAD);
			if (indice != -1) {
				String extraido = linea.substring(indice + UNABLE_TO_LOAD.length()).trim();
				if (!extraido.isEmpty()) {
					providerFallido = extraido;
				}
			}

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneFalloProvider(String linea) {
		return linea.contains(SERVICE_CONFIGURATION_ERROR) && linea.contains(IMOD_LANGUAGE_PROVIDER)
				&& linea.contains(UNABLE_TO_LOAD);
	}

	@Override
	public Verificaciones nueva() {
		return new ForgeLanguageProviderNoCarga();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1450;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeForgeLanguageProviderNoCarga(providerFallido) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreForgeLanguageProviderNoCarga();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "forge_language_provider_no_carga";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}