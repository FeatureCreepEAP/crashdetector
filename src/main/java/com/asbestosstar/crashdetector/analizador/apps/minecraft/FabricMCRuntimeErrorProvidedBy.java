package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

public class FabricMCRuntimeErrorProvidedBy implements Verificaciones {

	private boolean activado = false;
	private final Set<String> modIdsProblematicos = new HashSet<>();
	private final Map<String, String> enlacesPorModId = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i];
			if (linea.contains("Could not execute entrypoint stage") && linea.contains("provided by")) {
				try {
					// Buscamos el mod ID que está entre comillas simples después de "provided by"
					int startIndex = linea.indexOf("provided by '");
					if (startIndex >= 0) {
						// Ajustamos el índice para saltar "provided by '"
						startIndex += "provided by '".length();
						int endIndex = linea.indexOf('\'', startIndex);
						if (endIndex > startIndex) {
							// Extraemos solo el texto entre las comillas simples (el mod ID)
							String modId = linea.substring(startIndex, endIndex);
							// Solo registrar si es nuevo
							if (modIdsProblematicos.add(modId)) {
								String enlace = consola.agregarErrorALectador(i, this);
								enlacesPorModId.put(modId, enlace);
							}
							activado = true;
						}
					}
				} catch (Exception e) {
					// Ignora líneas mal formateadas
					consola.agregarErrorALectador(i, this); // Aún así registra la línea como problema
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new FabricMCRuntimeErrorProvidedBy();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f; // Prioridad media-alta para errores de inicialización
	}

	@Override
	public String mensaje() {
		if (modIdsProblematicos.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		for (String modId : modIdsProblematicos) {
			String enlace = enlacesPorModId.getOrDefault(modId, "");
			html.append("<li>").append(MonitorDePID.idioma.modids_problematicos()).append(" <b>").append(modId)
					.append("</b>").append(" ").append(enlace).append("</li>").append(Verificaciones.nl_html);
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_fabricmc_runtime_error_provided_by();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "fabricmc_runtime_error_providedby";
	}
	
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}
	
	
}