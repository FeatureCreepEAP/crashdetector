package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

public class RailwaysCreate6Alfa implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private String claveFaltante = "";

	@Override
	public void verificar(Consola consola) {
		String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);

		int lineaPrincipal = -1;
		for (int i = 0; i < lineas.length; i++) {
			String l = lineas[i];
			if (l.contains("java.lang.NullPointerException: Registry entry not present:")) {
				int idx = l.indexOf("present:");
				if (idx >= 0) {
					claveFaltante = l.substring(idx + "present:".length()).trim();
				}
				lineaPrincipal = i;
				break;
			}
		}
		if (lineaPrincipal < 0 || claveFaltante.isEmpty())
			return;

		// Contexto típico del rastro de Create/Registrate
		String texto = consola.contenido_verificar;
		boolean tieneObjects = texto.contains("at java.util.Objects.requireNonNull(");
		boolean tieneRegistrate = texto.contains("at com.tterrag.registrate.util.entry.RegistryEntry.get(");
		if (!tieneObjects || !tieneRegistrate)
			return;

		mensaje = MonitorDePID.idioma.errorRailwaysCreate6Alfa(claveFaltante);
		enlaceHtml = consola.agregarErrorALectador(lineaPrincipal, this);
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new RailwaysCreate6Alfa();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f;
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeRailwaysCreate6Alfa();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoRailwaysCreate6Alfa())
				.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "railways_6_alfa";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

}
