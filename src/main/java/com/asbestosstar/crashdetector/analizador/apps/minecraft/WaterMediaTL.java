package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.parches.ConfigDeParches;
import com.asbestosstar.crashdetector.parches.Parche;

public class WaterMediaTL implements Verificaciones {
	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		String contento_de_consola = consola.contento_verificar;
		if (contento_de_consola.contains("TLauncher is NOT supported by WATERMeDIA, please stop using it")) {
			this.mensaje = MonitorDePID.idioma.waterMediaTL() + Verificaciones.nl_html;
			activado = true;
		}
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
				},true).construir();
	}
}