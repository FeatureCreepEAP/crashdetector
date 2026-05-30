package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class IndependenteFlywheel implements Verificaciones {

	boolean tiene_uno = false;
	public Set<String> mods = new HashSet<>();
	boolean activado = false;
	String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// TODO Auto-generated method stub
		if (consola.contenido_verificar.contains("flywheel is 1")) {
			tiene_uno = true;

			mods.addAll(Buscador.obtenerModsConNombre("flywheel"));

		}

	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// TODO Auto-generated method stub
		if (tiene_uno) {

			if (linea.contains("requires flywheel") && linea.contains("and below 0.")) {
				this.enlace = consola.agregarErrorALectador(num, this);
				this.activado = true;
			}

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
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;
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

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
