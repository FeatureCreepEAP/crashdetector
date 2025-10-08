package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

public class ErrorConfiguracionMCForge implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains("ParsingException: Not enough data available")) {
				mensaje = MonitorDePID.idioma.errorConfigMCForge() + Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(i, this);
				activado = true;
				break;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorConfiguracionMCForge();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f; // Prioridad alta para errores de configuración críticos
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_error_de_config_mcforge();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "config_mcforge";
	}
	
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}
	
}