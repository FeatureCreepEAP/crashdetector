package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores fatales de Mixin en mods de Fabric sin usar regex costosos.
 * Basado en la implementación de Codex de Aternos.
 */
public class ProblemaSpongeMixinFabric implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreMod = "";
	private boolean viErrorMixin = false;

	@Override
	public void verificar(Consola consola) {
		this.activado = false;
		this.mensaje = "";
		this.nombreMod = "";
		this.viErrorMixin = false;
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (activado)
			return;

		if (linea == null)
			return;

		// Detectar la línea inicial del error fatal de Mixin
		if (linea.contains("MixinTransformerError: An unexpected critical error was encountered")) {
			this.viErrorMixin = true;
			return;
		}

		// Si ya vimos el error, buscar la línea que contiene "from mod"
		if (viErrorMixin && linea.contains("from mod ")) {
			// Extraer el nombre del mod: todo después de "from mod "
			int indice = linea.indexOf("from mod ");
			if (indice != -1) {
				String candidato = linea.substring(indice + "from mod ".length()).trim();
				// El nombre del mod suele estar al inicio y puede terminar en espacio, coma,
				// etc.
				int fin = candidato.indexOf(' ');
				if (fin == -1)
					fin = candidato.length();
				this.nombreMod = candidato.substring(0, fin);
				this.activado = true;
				this.mensaje = MonitorDePID.idioma.mensajeModFatal(nombreMod) + Verificaciones.nl_html;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaSpongeMixinFabric();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 800.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaModFatal();
	}

	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMod(nombreMod)).construir();
	}

	@Override
	public String id() {
		return "problema_spongemixin_fabric";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
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