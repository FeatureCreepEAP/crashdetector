package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.parches.ConfigDeParches;

public class WaterMediaTL implements Verificaciones {
	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		String contento_de_consola = consola.contenido_verificar;
		if (contento_de_consola.contains("TLauncher is NOT supported by WATERMeDIA, please stop using it")) {
			this.mensaje = MonitorDePID.idioma.waterMediaTL() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return false;
	}

	@Override
	public Verificaciones nueva() {
		return new WaterMediaTL();
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
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_watermedia_tl();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(MonitorDePID.idioma.nombre_de_watermedia_tl())
				.agregarBoton(MonitorDePID.idioma.activar_parche(), retener -> {
					ConfigDeParches.obtenerInstancia().establecerActivo("watermedia_tl", true);
				}, true).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "watermedia_tl";
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