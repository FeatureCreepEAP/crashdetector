package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores relacionados con el teselado de bloques en Minecraft.
 * Modernized: no regex, no regionMatches, exact-case scan.
 */
public class BloqueTeselado implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlace = "";

	private boolean posibleTesselado = false;

	private static final String TEXTO_TESSELADO = "Tesselating block in world";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		// Global check: inexpensive exact-case contains
		if (consola.contenido_verificar.contains(TEXTO_TESSELADO)) {
			posibleTesselado = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleTesselado || activado || linea == null || linea.isEmpty()) {
			return;
		}

		if (linea.contains(TEXTO_TESSELADO)) {
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
			mensaje = MonitorDePID.idioma.errorDeBloqueTeselado() + " " + enlace;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new BloqueTeselado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 500.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_bloque_teselado();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "bloqueteselado";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}
		return trazo.trace.contains(TEXTO_TESSELADO);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}