package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de parseo de animaciones de AzureLib.
 *
 * Requiere que existan ambas cadenas: - JsonSyntaxException: Expected post to
 * be a JsonArray, was an object -
 * mod.azure.azurelib.loading.json.typeadapter.BakedAnimationsAdapter
 *
 * Esto suele indicar que uno de los mods/addons que usan AzureLib tiene
 * animaciones JSON mal formadas o incompatibles. Source FIle #500 en Maven!
 * Gracias PriestessKokomi para esta problema
 */
public class ProblemaAzureLibAnimaciones implements Verificaciones {

	private boolean activado = false;

	private boolean vioJsonSyntax = false;
	private boolean vioAzureLib = false;

	private String enlace = "";

	private static final String EXPECTED_POST_JSON_ARRAY = "Expected post to be a JsonArray, was an object";
	private static final String AZURELIB_BAKED_ANIMATIONS_ADAPTER = "mod.azure.azurelib.loading.json.typeadapter.BakedAnimationsAdapter";

	@Override
	public String[] patronesRapidos() {
		return new String[] { EXPECTED_POST_JSON_ARRAY, AZURELIB_BAKED_ANIMATIONS_ADAPTER };
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

		if (linea.contains(EXPECTED_POST_JSON_ARRAY)) {
			vioJsonSyntax = true;
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains(AZURELIB_BAKED_ANIMATIONS_ADAPTER)) {
			vioAzureLib = true;
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (vioJsonSyntax && vioAzureLib) {
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaAzureLibAnimaciones();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f;
	}

	@Override
	public String mensaje() {

		if (!activado)
			return "";

		return MonitorDePID.idioma.mensajeProblemaAzureLibAnimaciones() + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaAzureLibAnimaciones();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_azurelib_animaciones";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { EXPECTED_POST_JSON_ARRAY, AZURELIB_BAKED_ANIMATIONS_ADAPTER };
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}