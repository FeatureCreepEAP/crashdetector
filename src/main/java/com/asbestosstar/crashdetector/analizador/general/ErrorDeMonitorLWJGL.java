package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

public class ErrorDeMonitorLWJGL implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		// Verifica cada línea buscando el error específico
		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains("org.lwjgl.LWJGLException: Failed to set display mode")) {
				mensaje = MonitorDePID.idioma.errorMonitorLWJGL() + Verificaciones.nl_html;
				enlaceHtml = consola.agregarErrorALectador(i, this);
				activado = true;
				break;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorDeMonitorLWJGL();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 800.0f; // Prioridad crítica para errores de renderizado
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
		return MonitorDePID.idioma.nombre_de_error_de_monitor_lwjgl();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "monitor_lwjgl";
	}
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}
	
}