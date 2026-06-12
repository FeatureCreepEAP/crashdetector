package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class NecesitasSodium implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		String contento_de_consola = consola.contenido_verificar;
		String patron = "Error loading class: net/caffeinemc/mods/sodium/api/memory/MemoryIntrinsics";
		if (contento_de_consola.contains(patron)) {
			this.mensaje = MonitorDePID.idioma.necesitasSodiumParaIris() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return false;

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
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}