package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflicto entre Iris + Flywheel en Create 6.
 *
 * Error típico: java.lang.NoSuchFieldError: TESSELATION_SHADERS junto con
 * referencias internas $irisflw$
 *
 * Iris Flywheel 2.0 para Create 6 solo es compatible oficialmente con NeoForge.
 */
public class ConflictoIrisFlywheelCreate implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private boolean vioNoSuchField = false;
	private boolean vioIrisFlywheel = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global: deben existir ambas cadenas
		if (log.contains("TESSELATION_SHADERS") && log.contains("$irisflw$")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (linea.contains("java.lang.NoSuchFieldError: TESSELATION_SHADERS")) {
			vioNoSuchField = true;
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains("$irisflw$")) {
			vioIrisFlywheel = true;
		}

		if (vioNoSuchField && vioIrisFlywheel) {
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoIrisFlywheelCreate();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeConflictoIrisFlywheelCreate() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreConflictoIrisFlywheelCreate();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "conflicto_iris_flywheel_create6";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
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