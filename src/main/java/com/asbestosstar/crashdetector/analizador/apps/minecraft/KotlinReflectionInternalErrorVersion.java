package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class KotlinReflectionInternalErrorVersion implements Verificaciones {

	// Indica si el log contiene indicios globales del error.
	// Este error suele estar relacionado con versiones problemáticas de Kotlin.
	private boolean posibleProblemaKotlin = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global ligera: esta combinación identifica un error común de
		// reflexión interna de Kotlin, frecuentemente visto con Inventory Profiles Next.
		if (consola.contenido_verificar.contains("kotlin.reflect.jvm.internal.KotlinReflectionInternalError")
				&& consola.contenido_verificar.contains("Property 'none'")
				&& consola.contenido_verificar.contains("not resolved in file class")) {
			posibleProblemaKotlin = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales
		if (!posibleProblemaKotlin) {
			return;
		}

		// Verificación precisa en la línea principal del error.
		if (linea.contains("kotlin.reflect.jvm.internal.KotlinReflectionInternalError")
				&& linea.contains("Property 'none'")
				&& linea.contains("not resolved in file class")) {

			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Alternativa: si la clase de Inventory Profiles Next aparece separada en otra
		// línea, también puede servir como punto de referencia útil.
		if (linea.contains("org.anti_ad.mc.ipnext.item.rule.natives.DefinedNativeRulesKt")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
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

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}