package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

public class ServicioDeModLauncherNoFunciona implements Verificaciones {

	private boolean activado = false;
	private final Set<String> serviciosFallidos = new HashSet<>();
	private final Map<String, String> enlacesPorServicio = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);
		String carga = "Service failed to load";

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains(carga)) {
				String servicio = linea.split(carga)[1].trim();
				String mensaje = MonitorDePID.idioma.servicioMLNoPudoCargar(servicio);

				// Solo registrar si es nuevo
				if (serviciosFallidos.add(mensaje)) {
					String enlace = consola.agregarErrorALectador(i, this);
					enlacesPorServicio.put(mensaje, enlace);
				}
				activado = true;
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ServicioDeModLauncherNoFunciona();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f; // Prioridad media
	}

	@Override
	public String mensaje() {
		if (serviciosFallidos.isEmpty())
			return "";

		CDStringBuilder html = new CDStringBuilder();
		html.append("<ul>");

		for (String servicio : serviciosFallidos) {
			String enlace = enlacesPorServicio.getOrDefault(servicio, "");
			html.append("<li>").append(servicio).append(" ").append(enlace).append("</li>");
		}

		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_servicio_de_modlauncher_no_funciona();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "servicio_de_modlauncher_no_funciona";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

}