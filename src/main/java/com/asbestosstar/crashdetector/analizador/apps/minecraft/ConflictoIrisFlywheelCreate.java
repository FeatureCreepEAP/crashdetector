package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
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

	private static final String NO_SUCH_FIELD_TESSELATION = "java.lang.NoSuchFieldError: TESSELATION_SHADERS";
	private static final String TESSELATION_SHADERS = "TESSELATION_SHADERS";
	private static final String IRISFLW = "$irisflw$";

	private static final Set<String> REPORTADOS = Collections.synchronizedSet(new HashSet<String>());

	public static void reiniciarGlobal() {
		REPORTADOS.clear();
	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { TESSELATION_SHADERS, IRISFLW };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		analizarLineas = true;
		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!analizarLineas || linea == null || activado) {
			return;
		}

		if (linea.contains(NO_SUCH_FIELD_TESSELATION)) {
			vioNoSuchField = true;
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains(IRISFLW)) {
			vioIrisFlywheel = true;
		}

		if (vioNoSuchField && vioIrisFlywheel) {
			activar();
		}
	}

	private void activar() {
		if (!REPORTADOS.add(id())) {
			return;
		}

		activado = true;
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
	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}