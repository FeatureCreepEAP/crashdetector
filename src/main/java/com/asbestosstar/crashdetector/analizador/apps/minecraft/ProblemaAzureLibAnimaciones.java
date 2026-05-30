package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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
	private boolean analizarLineas = false;

	private boolean vioJsonSyntax = false;
	private boolean vioAzureLib = false;

	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global para rendimiento: ambas cadenas deben existir en el log
		if (log.contains("Expected post to be a JsonArray, was an object")
				&& log.contains("mod.azure.azurelib.loading.json.typeadapter.BakedAnimationsAdapter")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null)
			return;

		if (linea.contains("Expected post to be a JsonArray, was an object")) {
			vioJsonSyntax = true;
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains("mod.azure.azurelib.loading.json.typeadapter.BakedAnimationsAdapter")) {
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
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (trazo == null || trazo.trace == null)
			return false;

		String t = trazo.trace;

		return t.contains("Expected post to be a JsonArray, was an object")
				&& t.contains("mod.azure.azurelib.loading.json.typeadapter.BakedAnimationsAdapter");
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}