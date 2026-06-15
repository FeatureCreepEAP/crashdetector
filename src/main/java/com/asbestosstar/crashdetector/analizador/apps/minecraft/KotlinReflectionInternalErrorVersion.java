package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class KotlinReflectionInternalErrorVersion implements VerificacionRapida {

	// Indica si el log contiene indicios globales del error.
	// Este error suele estar relacionado con versiones problemáticas de Kotlin.
	private boolean posibleProblemaKotlin = false;

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

		if (lineaContieneProblemaKotlin(evento.linea)) {
			posibleProblemaKotlin = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		// Detección global ligera: esta combinación identifica un error común de
		// reflexión interna de Kotlin, frecuentemente visto con Inventory Profiles
		// Next.
		if (consola.contenido_verificar.contains(KOTLIN_REFLECTION_ERROR)
				&& consola.contenido_verificar.contains(PROPERTY_NONE)
				&& consola.contenido_verificar.contains(NOT_RESOLVED)) {
			posibleProblemaKotlin = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleProblemaKotlin && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales
		if (!posibleProblemaKotlin || activado || linea == null) {
			return;
		}

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
	public Verificaciones nueva() {
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