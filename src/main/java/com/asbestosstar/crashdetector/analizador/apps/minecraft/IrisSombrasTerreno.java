package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class IrisSombrasTerreno implements Verificaciones {

	private boolean posibleErrorIris = false;
	private boolean activado = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global: el log contiene el método de Iris
		if (consola.contenido_verificar.contains("$iris$renderTerrainShadows")) {
			posibleErrorIris = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		if (!posibleErrorIris)
			return;

		if (linea.contains("$iris$renderTerrainShadows")) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new IrisSombrasTerreno();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeIrisSombrasTerreno() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreIrisSombrasTerreno();
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
		return "iris_sombras_terreno";
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
