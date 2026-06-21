package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class IndependenteFlywheel implements Verificaciones {

	public Set<String> mods = new HashSet<>();
	boolean activado = false;
	String enlace = "";

	private static final String FLYWHEEL_IS_1 = "flywheel is 1";
	private static final String REQUIRES_FLYWHEEL = "requires flywheel";
	private static final String AND_BELOW_0 = "and below 0.";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FLYWHEEL_IS_1, REQUIRES_FLYWHEEL, AND_BELOW_0 };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(FLYWHEEL_IS_1)) {
			mods.addAll(Buscador.obtenerModsConNombre("flywheel"));
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// TODO Auto-generated method stub

		if (linea.contains(REQUIRES_FLYWHEEL) && linea.contains(AND_BELOW_0)) {
			this.enlace = consola.agregarErrorALectador(num, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new IndependenteFlywheel();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

	@Override
	public float prioridad() {
		// TODO Auto-generated method stub
		return 1200;
	}

	@Override
	public String mensaje() {
		String mensajeBase = MonitorDePID.idioma.mensajeIndependenteFlywheel(mods);

		return mensajeBase + this.enlace;
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombreIndependenteFlywheel();
	}

	@Override
	public QuickFix solucion() {
		// TODO Auto-generated method stub
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "flywheel_independente";
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}