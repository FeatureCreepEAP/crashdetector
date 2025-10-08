package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class lithostictchctov implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		String patron = "Caused by: java.lang.RuntimeException: "
				+ "Unknown registry key in ResourceKey[minecraft:root / minecraft:worldgen/structure_type]: lithostitched:jigsaw";
		String firmaCTOV = "ctov";

		if (contenidoConsola.contains(patron) && contenidoConsola.contains(firmaCTOV)) {
			this.mensaje = MonitorDePID.idioma.lithostichctov() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new lithostictchctov();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 3.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_faltar_de_liyhostictchctov();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "lithostichctov";
	}

}