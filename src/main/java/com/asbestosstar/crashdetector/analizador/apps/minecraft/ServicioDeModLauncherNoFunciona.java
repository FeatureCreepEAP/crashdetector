package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ServicioDeModLauncherNoFunciona implements Verificaciones {

	private boolean activado = false;
	private final Set<String> serviciosFallidos = new HashSet<>();
	private final Map<String, String> enlacesPorServicio = new HashMap<>();

	// Texto base que indica fallo de carga de un servicio de ModLauncher
	private static final String CARGA_FALLIDA = "Service failed to load";

	@Override
	public String[] patronesRapidos() {
		return new String[] { CARGA_FALLIDA };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscar el patrón "Service failed to load" en la línea actual
		if (linea.contains(CARGA_FALLIDA)) {
			// Extraer el nombre/identificador del servicio que no pudo cargar
			String servicio = extraerServicio(linea);

			String mensaje = MonitorDePID.idioma.servicioMLNoPudoCargar(servicio);

			// Solo registrar si es nuevo
			if (serviciosFallidos.add(mensaje)) {
				String enlace = consola.agregarErrorALectador(numero_de_linea, this);
				enlacesPorServicio.put(mensaje, enlace);
			}
			activado = true;
		}
	}

	private String extraerServicio(String linea) {
		int idx = linea.indexOf(CARGA_FALLIDA);
		if (idx == -1) {
			return "";
		}

		int inicio = idx + CARGA_FALLIDA.length();
		if (inicio >= linea.length()) {
			return "";
		}

		return linea.substring(inicio).trim();
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
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "servicio_de_modlauncher_no_funciona";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { CARGA_FALLIDA };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}