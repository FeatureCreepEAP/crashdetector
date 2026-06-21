package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class NecesitasSodium implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	private static final String PATRON_SODIUM = "Error loading class: net/caffeinemc/mods/sodium/api/memory/MemoryIntrinsics";

	@Override
	public String[] patronesRapidos() {
		return new String[] { PATRON_SODIUM, "net/caffeinemc/mods/sodium/api/memory/MemoryIntrinsics" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(PATRON_SODIUM)) {
			this.mensaje = MonitorDePID.idioma.necesitasSodiumParaIris() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new NecesitasSodium();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_necesitas_sodium();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "necesitas_sodium";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { PATRON_SODIUM };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}