package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;

public class UraniumLag implements Verificaciones {

	boolean activado = false;

	@Override
	public void verificar(Consola consola) {
		// TODO Auto-generated method stub

		activado = Buscardor.existeClaseEnAlgunMod("net.yosa.uranium.Uranium");

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
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;
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

}
