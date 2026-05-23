package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta un conflicto crítico entre los mods Moonlight e Iceberg durante la
 * recarga de recursos, que provoca un fallo de OpenGL: "No context is current".
 * 
 * Este error ocurre cuando ambos mods intentan registrar listeners de recarga
 * de forma incompatible, especialmente en entornos Forge con mods de Fabric
 * adaptados.
 */
public class ConflictoMoonlightIceberg implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		// No se usa; análisis por línea
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (this.activado) {
			return;
		}

		if (linea.contains("FATAL ERROR in native method")
				&& linea.contains("No context is current or a function that is not available in the current context")) {

			String contenido = consola.contenido_verificar;
			boolean tieneMoonlight = contenido.contains("moonlight") || contenido.contains("Moonlight");
			boolean tieneIceberg = contenido.contains("iceberg") || contenido.contains("Iceberg");

			if (tieneMoonlight && tieneIceberg) {
				String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				this.mensaje = MonitorDePID.idioma.conflictoMoonlightIceberg() + enlaceHtml;
				this.activado = true;
			}
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		String t = trazo.trace;
		return t.contains("FATAL ERROR in native method") && t.contains("No context is current")
				&& t.contains("moonlight") && t.contains("iceberg");
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoMoonlightIceberg();
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
		return 1100.0f; // Alta: provoca cierre inmediato del juego
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionConflictoMoonlightIceberg())
				.construir();
	}

	@Override
	public String id() {
		return "conflicto_moonlight_iceberg";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreConflictoMoonlightIceberg();
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}