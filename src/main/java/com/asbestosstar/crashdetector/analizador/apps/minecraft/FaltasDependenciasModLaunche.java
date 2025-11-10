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

	// Cache del contenido de la consola dividido por líneas para evitar repetir el
	// split.
	private String[] lineasConsola = null;

	@Override
	public void verificar(Consola consola) {
		// Ahora toda la lógica principal se hace en el método por línea.
		// Este método se deja vacío por rendimiento y claridad.
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó, no seguimos procesando más líneas.
		if (activado) {
			return;
		}

		// Inicializa el array de líneas una sola vez usando el contenido completo.
		if (lineasConsola == null) {
			lineasConsola = consola.contenido_verificar.split(Verificaciones.nl);
		}

		String lineaActual = linea.trim();

		// "Failure message: Mod deeper_oceans requires lithostitched 0 or above"
		// con la siguiente línea:
		// "Currently, lithostitched is not installed"
		if ((lineaActual.contains("only supports") || lineaActual.contains("requires"))
				&& lineaActual.contains("or above")) {
			try {
				String modId = extraerModId(lineaActual);
				String dependencia = extraerDependencia(lineaActual);
				String versionRequerida = extraerVersionRequerida(lineaActual);

				// Busca la línea siguiente en el contenido completo.
				int i = numero_de_linea;
				if (i + 1 < lineasConsola.length) {
					String lineaSiguiente = lineasConsola[i + 1].trim();
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
				consola.agregarErrorALectador(numero_de_linea, this); // Registrar línea como problema
			}
		}

		// Formato antiguo de dependencias faltantes
		else if (lineaActual.contains("Missing or unsupported mandatory dependencies:")) {

			int i = numero_de_linea;
			for (int j = i + 1; j < lineasConsola.length; j++) {
				String lineaDep = lineasConsola[j].trim();
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

	// Métodos auxiliares de parseo (mantenidos igual)
	private String extraerModId(String linea) {
		int inicio = linea.indexOf("Mod ") + 4;

		// Soporta tanto:
		// "Mod X only supports ..."
		// como:
		// "Mod X requires ..."
		int fin = linea.indexOf(" only supports");
		if (fin == -1) {
			fin = linea.indexOf(" requires");
		}

		return limpiarFormato(linea.substring(inicio, fin));
	}

	private String extraerDependencia(String linea) {
		// Decide qué palabra clave usar según el texto
		String clave;
		if (linea.contains("only supports")) {
			clave = "supports ";
		} else {
			clave = "requires ";
		}

		int inicio = linea.indexOf(clave) + clave.length();
		int fin = linea.indexOf(" ", inicio);

		return limpiarFormato(linea.substring(inicio, fin));
	}

	private String extraerVersionRequerida(String linea) {
		// Igual que arriba, soporta "only supports" y "requires"
		String clave;
		if (linea.contains("only supports")) {
			clave = "supports ";
		} else {
			clave = "requires ";
		}

		int inicio = linea.indexOf(clave) + clave.length();
		int fin = linea.indexOf(" or above");

		// Ejemplo:
		// "Mod deeper_oceans requires lithostitched 0 or above"
		// substring(inicio, fin) => "lithostitched 0"
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
		return 1100f;
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
		// Si el verificador nunca se activó o el trazo es nulo, no marcamos nada.
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String contenido = trazo.trace;

		// Primero intentamos ver si el trazo contiene alguno de los mensajes ya
		// construidos (traducciones humanas). Esto es muy preciso si coincide.
		for (String error : errores) {
			if (error != null && !error.isEmpty()) {
				String trim = error.trim();
				if (!trim.isEmpty() && contenido.contains(trim)) {
					return true;
				}
			}
		}

		// Heurística secundaria: patrones típicos de errores de dependencias de Forge.
		// Preferimos falsos negativos antes que marcar cosas que no tocan.
		if (contenido.contains("Missing or unsupported mandatory dependencies:")
				|| (contenido.contains("only supports") && contenido.contains("or above"))
				|| (contenido.contains("requires") && contenido.contains("or above")
						&& contenido.contains("Currently,"))) {
			return true;
		}

		return false;
	}

}
