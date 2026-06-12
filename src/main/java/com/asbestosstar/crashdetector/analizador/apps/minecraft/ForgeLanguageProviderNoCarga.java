package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ForgeLanguageProviderNoCarga implements Verificaciones {

	// Indica si el log contiene indicios globales del error (optimización de
	// rendimiento)
	private boolean posibleFalloProvider = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	// Nombre del provider que no pudo cargarse
	private String providerFallido = "";

	@Override
	public void verificar(Consola consola) {

		// Detección global ligera:
		// Solo se buscan subcadenas clave para evitar operaciones costosas.
		if (consola.contenido_verificar.contains("java.util.ServiceConfigurationError")
				&& consola.contenido_verificar.contains("net.minecraftforge.forgespi.language.IModLanguageProvider")
				&& consola.contenido_verificar.contains("Unable to load")) {

			posibleFalloProvider = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posibleFalloProvider)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {

		// Salir temprano si no hay indicios globales
		if (!posibleFalloProvider) {
			return;
		}

		// Verificación precisa en la línea exacta
		if (linea.contains("ServiceConfigurationError") && linea.contains("IModLanguageProvider")
				&& linea.contains("Unable to load")) {

			// Extraer el nombre completo del provider que falló
			int indice = linea.indexOf("Unable to load");
			if (indice != -1) {
				String extraido = linea.substring(indice + "Unable to load".length()).trim();
				if (!extraido.isEmpty()) {
					providerFallido = extraido;
				}
			}

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
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