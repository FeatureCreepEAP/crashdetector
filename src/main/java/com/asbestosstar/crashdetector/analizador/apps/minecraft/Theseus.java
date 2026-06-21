package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class Theseus implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	String theseus = "com.modrinth.theseus";
	String mr_app = "ModrinthApp";

	@Override
	public String[] patronesRapidos() {
		return new String[] { theseus, mr_app };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// TODO Auto-generated method stub
		if (activado || linea == null) {
			return;
		}

		if (linea.contains(theseus) || linea.contains(mr_app)) {
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new Theseus();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f;
	}

	@Override
	public String mensaje() {
		return mensaje; // Devuelve el mensaje almacenado
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_theseus();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "theseus";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.builder().doc("en", "minecraft/Launchers.md").build();
	}

}