package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class UraniumLag implements Verificaciones {

	boolean activado = false;

	@Override
	public String[] patronesRapidos() {
		// No necesita activar por línea.
		// Usa vdst.trazos_completos en finalizarArchivo().
		verificar();
		return new String[0];
	}

	public void verificar() {
		// TODO Auto-generated method stub

		activado = Buscador.existeClaseEnAlgunMod("net.yosa.uranium.Uranium");

	}

	public void verificarCoincidencia(EventoDeCoincidencia evento) {
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new UraniumLag();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

	@Override
	public float prioridad() {
		// TODO Auto-generated method stub
		return 1400;
	}

	@Override
	public String mensaje() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.uraniumLag();
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return "UraniumLag";
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}


	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "uraniumlag";
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}

	@Override
	public boolean anularNormal() {
		return true;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}
