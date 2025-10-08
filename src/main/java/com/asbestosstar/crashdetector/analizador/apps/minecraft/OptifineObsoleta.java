package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class OptifineObsoleta implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		String contento_de_consola = consola.contenido_verificar;

		boolean errorOptifine = contento_de_consola.contains("Error loading OptiFine ZIP file");
		boolean uriInvalida = contento_de_consola.contains("URI is not hierarchical");
		boolean incompatibilidad = contento_de_consola
				.contains("cpw.mods.modlauncher.api.IncompatibleEnvironmentException");
		boolean servicioFallido = contento_de_consola.contains("Service failed to load OptiFine");

		if (errorOptifine && uriInvalida && incompatibilidad && servicioFallido) {
			this.mensaje = MonitorDePID.idioma.optifineObsoleta() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new OptifineObsoleta();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_optifine_obsoleta();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "optifine_obsoleta";
	}

}