package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class Segundo60Tick implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	private static final String PATRON = "[Server Watchdog/FATAL] [net.minecraft.server.dedicated.ServerHangWatchdog]: "
			+ "A single server tick took 60.00 seconds (should be max 0.05)";

	@Override
	public String[] patronesRapidos() {
		return new String[] { "A single server tick took 60.00 seconds" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(PATRON)) {
			this.mensaje = MonitorDePID.idioma.segundo60Tick() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new Segundo60Tick();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 100.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_60_segundo_trick();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "segundo_60_tick";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}