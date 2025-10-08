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

public class FaltasDependenciasModLaunche implements Verificaciones {

	private boolean activado = false;
	private final Set<String> errores = new HashSet<>();
	private final Map<String, String> enlacesPorError = new HashMap<>();

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);

		for (int i = 0; i < lineas.length; i++) {
			String linea = lineas[i].trim();

			if (linea.contains("only supports") && linea.contains("or above")) {
				try {
					String modId = extraerModId(linea);
					String dependencia = extraerDependencia(linea);
					String versionRequerida = extraerVersionRequerida(linea);

					if (i + 1 < lineas.length) {
						String lineaSiguiente = lineas[i + 1].trim();
						if (lineaSiguiente.startsWith("Currently,")) {
							String versionActual = extraerVersionActual(lineaSiguiente, dependencia);
							String mensaje = MonitorDePID.idioma.errorVersionDependencia(modId, dependencia,
									versionRequerida, versionActual);

							// Solo registrar si es un error nuevo
							if (errores.add(mensaje)) {
								String enlace = consola.agregarErrorALectador(i, this);
								enlacesPorError.put(mensaje, enlace);
							}

							activado = true;
						}
					}
				} catch (Exception e) {
					// Ignora errores de parseo
					consola.agregarErrorALectador(i, this); // Registrar línea como problema
				}
			}

			// Formato antiguo de dependencias faltantes
			else if (linea.contains("Missing or unsupported mandatory dependencies:")) {

				for (int j = i + 1; j < lineas.length; j++) {
					String lineaDep = lineas[j].trim();
					if (lineaDep.startsWith("Currently,") || lineaDep.isEmpty())
						break;
					if (lineaDep.contains("Mod ID") && lineaDep.contains("Requested by")) {
						String mensaje = MonitorDePID.idioma.linea_de_dependencia(lineaDep);
						if (errores.add(mensaje)) {
							String enlace = consola.agregarErrorALectador(j, this);
							enlacesPorError.put(mensaje, enlace);
						}
						activado = true;
					}
				}
			}
		}
	}

	// Métodos auxiliares de parseo (mantenidos igual)
	private String extraerModId(String linea) {
		int inicio = linea.indexOf("Mod ") + 4;
		int fin = linea.indexOf(" only supports");
		return limpiarFormato(linea.substring(inicio, fin));
	}

	private String extraerDependencia(String linea) {
		int inicio = linea.indexOf("supports ") + 9;
		int fin = linea.indexOf(" ", inicio);
		return limpiarFormato(linea.substring(inicio, fin));
	}

	private String extraerVersionRequerida(String linea) {
		int inicio = linea.indexOf("supports ") + 9;
		int fin = linea.indexOf(" or above");
		return limpiarFormato(linea.substring(inicio, fin));
	}

	private String extraerVersionActual(String linea, String dependencia) {
		int inicio = linea.indexOf("is ") + 3;
		return limpiarFormato(linea.substring(inicio));
	}

	private String limpiarFormato(String texto) {
		return texto.replaceAll("§[a-zA-Z0-9]", "");
	}

	@Override
	public Verificaciones nueva() {
		return new FaltasDependenciasModLaunche();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900f;
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		CDStringBuilder html = new CDStringBuilder();
		html.append(MonitorDePID.idioma.no_tienes_las_dependencias_necesarias());
		html.append("<ul>");
		for (String error : errores) {
			String trim = error.trim().replace("\t", "");
			if (!html.toString().contains(trim)) {
				String enlace = enlacesPorError.getOrDefault(trim, "");
				html.append("<li>").append(trim).append(" ").append(enlace).append("</li>");
			}
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_faltas_dependencias_de_modlauncher();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "faltas_dependencias_modlauncher";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}


}