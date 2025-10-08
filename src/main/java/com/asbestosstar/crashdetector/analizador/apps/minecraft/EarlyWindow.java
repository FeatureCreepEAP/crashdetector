package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

/**
 * Este error es común en Minecraft Forge cuando los registros terminan en
 * "Loading ImmediateWindowProvider fmlearlywindow", especialmente debido a
 * controladores (drivers) defectuosos, aunque también puede deberse a
 * conflictos entre mods. Puedes resolverlo editando el valor de
 * earlyWindowProvider a "none" en el archivo config/fml.toml.
 */
public class EarlyWindow implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		if (lineas.length == 0)
			return;

		// Buscar la última línea no vacía
		String ultimaLinea = null;
		int indiceUltimaLinea = -1;

		for (int i = lineas.length - 1; i >= 0; i--) {
			String linea = lineas[i].trim();
			if (!linea.isEmpty()) {
				ultimaLinea = linea;
				indiceUltimaLinea = i;
				break;
			}
		}

		// Solo activar si la última línea no vacía contiene el mensaje
		if (ultimaLinea != null && ultimaLinea.contains("Loading ImmediateWindowProvider fmlearlywindow")) {
			mensaje = MonitorDePID.idioma.fmlEarlyWindow() + Verificaciones.nl_html;
			enlaceHtml = consola.agregarErrorALectador(indiceUltimaLinea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new EarlyWindow();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 800.0f; // Prioridad media para advertencias de inicialización
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
		return MonitorDePID.idioma.nombre_de_early_window();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "earlywindow";
	}
	
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}
}