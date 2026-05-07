package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class BiomesOPlentyFogDataCustomLiquids implements Verificaciones {

	// Indica si el log contiene indicios globales del error.
	// Esto evita hacer trabajo innecesario línea por línea.
	private boolean posibleProblemaBOP = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a la línea del log donde ocurre el error
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global ligera: el ClassCastException junto con Biomes O' Plenty
		// puede indicar un problema relacionado con líquidos personalizados o niebla.
		if (consola.contenido_verificar.contains("class org.joml.Vector4f cannot be cast to class net.minecraft.client.renderer.fog.FogData")
				&& consola.contenido_verificar.toLowerCase().contains("biomesoplenty")) {
			posibleProblemaBOP = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int num) {
		// Salir temprano si no hay indicios globales
		if (!posibleProblemaBOP) {
			return;
		}

		// Verificación precisa en la línea del error principal.
		if (linea.contains("class org.joml.Vector4f cannot be cast to class net.minecraft.client.renderer.fog.FogData")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
			return;
		}

		// Alternativa: si el lector encuentra primero la referencia a Biomes O' Plenty,
		// también puede enlazar ahí.
		if (linea.toLowerCase().contains("biomesoplenty")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new BiomesOPlentyFogDataCustomLiquids();
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
		return MonitorDePID.idioma.mensajeBiomesOPlentyFogDataLiquidosPersonalizados() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreBiomesOPlentyFogDataLiquidosPersonalizados();
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
		return "biomes_o_plenty_fogdata_liquidos_personalizados";
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