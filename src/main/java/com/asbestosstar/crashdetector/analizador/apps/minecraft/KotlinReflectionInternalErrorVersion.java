package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class KotlinReflectionInternalErrorVersion implements Verificaciones {

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	private static final String KOTLIN_REFLECTION_ERROR = "kotlin.reflect.jvm.internal.KotlinReflectionInternalError";
	private static final String PROPERTY_NONE = "Property 'none'";
	private static final String NOT_RESOLVED = "not resolved in file class";
	private static final String INVENTORY_PROFILES_RULES = "org.anti_ad.mc.ipnext.item.rule.natives.DefinedNativeRulesKt";

	@Override
	public String[] patronesRapidos() {
		return new String[] { KOTLIN_REFLECTION_ERROR, PROPERTY_NONE, NOT_RESOLVED, INVENTORY_PROFILES_RULES };
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

		// Verificación precisa en la línea principal del error.
		if (linea.contains(KOTLIN_REFLECTION_ERROR) && linea.contains(PROPERTY_NONE) && linea.contains(NOT_RESOLVED)) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Alternativa: si la clase de Inventory Profiles Next aparece separada en otra
		// línea, también puede servir como punto de referencia útil.
		if (linea.contains(INVENTORY_PROFILES_RULES)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	private boolean lineaContieneProblemaKotlin(String linea) {
		return (linea.contains(KOTLIN_REFLECTION_ERROR) && linea.contains(PROPERTY_NONE)
				&& linea.contains(NOT_RESOLVED)) || linea.contains(INVENTORY_PROFILES_RULES);
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new KotlinReflectionInternalErrorVersion();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeKotlinReflectionInternalErrorVersion() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreKotlinReflectionInternalErrorVersion();
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
		return "kotlin_reflection_internal_error_version";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}