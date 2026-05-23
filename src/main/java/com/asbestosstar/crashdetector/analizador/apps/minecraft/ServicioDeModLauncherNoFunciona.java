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
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ServicioDeModLauncherNoFunciona implements Verificaciones {

	private boolean activado = false;
	private final Set<String> serviciosFallidos = new HashSet<>();
	private final Map<String, String> enlacesPorServicio = new HashMap<>();

	// Texto base que indica fallo de carga de un servicio de ModLauncher
	private static final String CARGA_FALLIDA = "Service failed to load";

	/**
	 * Verificación global del contenido de la consola.
	 * <p>
	 * En este verificador no es necesario hacer un análisis global pesado: la
	 * detección real se hace por línea en
	 * {@link #verificarPorLinea(Consola, String, int)}, que es llamado para cada
	 * línea del log. Este método existe para mantener el contrato de la interfaz.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		// No se realiza análisis global aquí; todo se hace en el método por línea.
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (linea == null) {
			return;
		}

		// Buscar el patrón "Service failed to load" en la línea actual
		if (linea.contains(CARGA_FALLIDA)) {
			// Extraer el nombre/identificador del servicio que no pudo cargar
			String[] partes = linea.split(CARGA_FALLIDA, 2);
			String servicio = partes.length > 1 ? partes[1].trim() : "";

			String mensaje = MonitorDePID.idioma.servicioMLNoPudoCargar(servicio);

			// Solo registrar si es nuevo
			if (serviciosFallidos.add(mensaje)) {
				String enlace = consola.agregarErrorALectador(numero_de_linea, this);
				enlacesPorServicio.put(mensaje, enlace);
			}
			activado = true;
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
		return QuickFix.NINGUN;// TODO
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

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
