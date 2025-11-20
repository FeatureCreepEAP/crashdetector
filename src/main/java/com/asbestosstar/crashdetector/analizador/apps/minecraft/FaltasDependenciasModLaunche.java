package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class FaltasDependenciasModLaunche implements Verificaciones {

	private boolean activado = false;
	private final Set<String> errores = new HashSet<>();
	private final Map<String, String> enlacesPorError = new HashMap<>();
	private String[] lineasConsola = null;

	@Override
	public void verificar(Consola consola) {
		// Este método se deja vacío, toda la lógica está en verificar(Consola, String,
		// int)
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {

		if (lineasConsola == null) {
			lineasConsola = consola.contenido_verificar.split(Verificaciones.nl);
		}

		String lineaActual = linea.trim();

		// NUEVO: Detectar el formato especial con versión larga y texto adicional
		if (lineaActual.contains("only supports") && lineaActual.contains("or above")
				&& lineaActual.contains(".Remove ")) {
			procesarFormatoEspecial(consola, lineaActual, numero_de_linea);
			return;
		}

		// NUEVO: Detectar el formato "-- MOD modid --"
		if (lineaActual.startsWith("-- MOD ") && lineaActual.endsWith(" --")) {
			// Preparar para procesar las líneas de detalles del mod
			return;
		}

		// NUEVO: Detectar formato de Failure message para los nuevos estilos
		if (lineaActual.contains("Failure message: Mod ")
				&& (lineaActual.contains(" requires ") || lineaActual.contains(" only supports "))) {
			procesarNuevoFormatoDependencia(consola, lineaActual, numero_de_linea);
			return;
		}

		// Formato antiguo
		if (lineaActual.contains("Missing or unsupported mandatory dependencies:")) {
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

// NUEVO: Método para procesar el nuevo formato de dependencias (modificado para múltiples dependencias)
	private void procesarNuevoFormatoDependencia(Consola consola, String lineaActual, int numero_de_linea) {
		try {

			if (lineaActual.contains("Remove Iris/Oculus & GeckoLib Compat")) {
				procesarFormatoSuperbWarfare(consola, lineaActual, numero_de_linea);
				return;
			}

			// Extraer información básica
			String modId = extraerModIdNuevoFormato(lineaActual);

			// Verificar si hay múltiples dependencias en la misma línea
			if (lineaActual.contains(",") || lineaActual.contains(" and ")) {
				List<String[]> dependencias = extraerMultiplesDependencias(lineaActual);

				for (String[] dependenciaInfo : dependencias) {
					String dependencia = dependenciaInfo[0];
					String versionRequerida = dependenciaInfo[1];

					// Buscar información adicional en las siguientes líneas
					String versionActual = "no encontrada";
					for (int i = 1; i <= 3; i++) {
						if (numero_de_linea + i < lineasConsola.length) {
							String lineaSiguiente = lineasConsola[numero_de_linea + i].trim();
							if (lineaSiguiente.startsWith("Currently,") && lineaSiguiente.contains(dependencia)) {
								versionActual = extraerVersionActualNuevoFormato(lineaSiguiente);
								break;
							}
						}
					}

					// Crear mensaje dependiendo de lo que se encontró
					String mensaje;
					if (versionRequerida.isEmpty() || versionRequerida.equals("any")) {
						// Caso simple: solo requiere un mod sin especificar versión
						mensaje = MonitorDePID.idioma.errorDependenciaSimple(modId, dependencia, versionActual);
					} else if (versionActual.contains("not installed")) {
						// Caso: dependencia no instalada
						mensaje = MonitorDePID.idioma.errorDependenciaNoInstalada(modId, dependencia, versionRequerida);
					} else {
						// Caso: versión incorrecta
						mensaje = MonitorDePID.idioma.errorVersionDependencia(modId, dependencia, versionRequerida,
								versionActual);
					}

					if (errores.add(mensaje)) {
						String enlace = consola.agregarErrorALectador(numero_de_linea, this);
						enlacesPorError.put(mensaje, enlace);
						activado = true;
					}
				}
			} else {
				// Manejar una sola dependencia como antes
				String dependencia = extraerDependenciaNuevoFormato(lineaActual);
				String versionRequerida = extraerVersionRequeridaNuevoFormato(lineaActual);

				// Buscar información adicional en las siguientes líneas
				String versionActual = "no encontrada";
				for (int i = 1; i <= 3; i++) {
					if (numero_de_linea + i < lineasConsola.length) {
						String lineaSiguiente = lineasConsola[numero_de_linea + i].trim();
						if (lineaSiguiente.startsWith("Currently,")) {
							versionActual = extraerVersionActualNuevoFormato(lineaSiguiente);
							break;
						}
					}
				}

				// Crear mensaje dependiendo de lo que se encontró
				String mensaje;
				if (versionRequerida.isEmpty() || versionRequerida.equals("any")) {
					// Caso simple: solo requiere un mod sin especificar versión
					mensaje = MonitorDePID.idioma.errorDependenciaSimple(modId, dependencia, versionActual);
				} else if (versionActual.contains("not installed")) {
					// Caso: dependencia no instalada
					mensaje = MonitorDePID.idioma.errorDependenciaNoInstalada(modId, dependencia, versionRequerida);
				} else {
					// Caso: versión incorrecta
					mensaje = MonitorDePID.idioma.errorVersionDependencia(modId, dependencia, versionRequerida,
							versionActual);
				}

				if (errores.add(mensaje)) {
					String enlace = consola.agregarErrorALectador(numero_de_linea, this);
					enlacesPorError.put(mensaje, enlace);
					activado = true;
				}
			}
		} catch (Exception e) {
			consola.agregarErrorALectador(numero_de_linea, this);
		}
	}

	private void procesarFormatoSuperbWarfare(Consola consola, String lineaActual, int numero_de_linea) {
		try {
			// Extraer mod ID y dependencia
			String modId = "superbwarfare";
			String dependencia = "geckoanimfix";

			// Buscar versión actual en las líneas siguientes
			String versionActual = "desconocida";
			for (int i = 1; i <= 3; i++) {
				if (numero_de_linea + i < lineasConsola.length) {
					String lineaSiguiente = lineasConsola[numero_de_linea + i].trim();
					if (lineaSiguiente.contains("Currently,") && lineaSiguiente.contains("geckoanimfix")) {
						// Extraer solo la versión (1.0.0) sin formato
						if (lineaSiguiente.contains("is §o")) {
							versionActual = lineaSiguiente.substring(lineaSiguiente.indexOf("is §o") + 5).trim();
						} else if (lineaSiguiente.contains("is ")) {
							versionActual = lineaSiguiente.substring(lineaSiguiente.indexOf("is ") + 3).trim();
						}
						break;
					}
				}
			}

			// Crear mensaje especializado
			String mensaje = MonitorDePID.idioma.errorSuperbWarfareIncompatible(modId, dependencia, versionActual);

			if (errores.add(mensaje)) {
				String enlace = consola.agregarErrorALectador(numero_de_linea, this);
				enlacesPorError.put(mensaje, enlace);
				activado = true;
			}
		} catch (Exception e) {
			consola.agregarErrorALectador(numero_de_linea, this);
		}
	}

// NUEVO: Método para extraer múltiples dependencias de una línea
	private List<String[]> extraerMultiplesDependencias(String linea) {
		List<String[]> dependencias = new ArrayList<>();

		// Extraer la parte relevante de la línea
		String parteDependencias = "";
		if (linea.contains(" requires ")) {
			parteDependencias = linea.substring(linea.indexOf(" requires ") + 10);
		} else if (linea.contains(" only supports ")) {
			parteDependencias = linea.substring(linea.indexOf(" only supports ") + 16);
		}

		// Eliminar la parte de "or above", "and below", etc.
		if (parteDependencias.contains(" or above")) {
			parteDependencias = parteDependencias.substring(0, parteDependencias.indexOf(" or above"));
		}
		if (parteDependencias.contains(" and below")) {
			parteDependencias = parteDependencias.substring(0, parteDependencias.indexOf(" and below"));
		}

		// Reemplazar "and" con coma para unificar el formato
		parteDependencias = parteDependencias.replace(" and ", ", ");

		// Dividir en dependencias individuales
		String[] partes = parteDependencias.split(",");

		for (String parte : partes) {
			String dependenciaLimpia = parte.trim();

			// Si la dependencia tiene versión especificada
			if (dependenciaLimpia.contains(" ")) {
				String[] partesDep = dependenciaLimpia.split(" ", 2);
				String nombreDep = partesDep[0].trim();
				String versionDep = partesDep[1].trim();
				dependencias.add(new String[] { nombreDep, versionDep });
			} else {
				// Sin versión específica
				dependencias.add(new String[] { dependenciaLimpia, "" });
			}
		}

		return dependencias;
	}

	// NUEVOS métodos de parseo para el nuevo formato
	private String extraerModIdNuevoFormato(String linea) {
		int inicio = linea.indexOf("Mod ") + 4;
		int fin = linea.indexOf(" ", inicio);
		return limpiarFormato(linea.substring(inicio, fin));
	}

	private String extraerDependenciaNuevoFormato(String linea) {
		int inicio = linea.contains(" requires ") ? linea.indexOf(" requires ") + 10
				: linea.indexOf(" only supports ") + 16;
		int fin = linea.indexOf(" ", inicio);
		if (fin == -1 || fin < inicio)
			fin = linea.length();
		return limpiarFormato(linea.substring(inicio, fin));
	}

	private String extraerVersionRequeridaNuevoFormato(String linea) {
		// Buscar patrón de versión como "20.1.7 or above" o similar
		int inicioVersion = linea.contains(" requires ") ? linea.indexOf(" requires ") + 10
				: linea.indexOf(" only supports ") + 16;
		int finMod = linea.indexOf(" ", inicioVersion);

		if (finMod == -1)
			return "";

		// Buscar la versión después del nombre del mod
		int inicio = linea.indexOf(" ", finMod) + 1;
		int fin = linea.indexOf(" or above");
		if (fin == -1)
			fin = linea.indexOf(" and below");
		if (fin == -1)
			fin = linea.length();

		String resultado = linea.substring(inicio, fin).trim();
		// Si contiene comas o "and", solo tomar la primera parte
		if (resultado.contains(",")) {
			resultado = resultado.substring(0, resultado.indexOf(",")).trim();
		}
		if (resultado.contains(" and ")) {
			resultado = resultado.substring(0, resultado.indexOf(" and ")).trim();
		}
		return resultado;
	}

	private String extraerVersionActualNuevoFormato(String linea) {
		int inicio = linea.indexOf("is ") + 3;
		if (inicio < 3) {
			inicio = linea.indexOf("not installed") > 0 ? linea.indexOf("not installed") : linea.length();
		}
		return inicio < linea.length() ? limpiarFormato(linea.substring(inicio)) : "no especificada";
	}

	// Métodos existentes manteniendo compatibilidad
	private String extraerModId(String linea) {
		int inicio = linea.indexOf("Mod ") + 4;
		int fin = linea.indexOf(" only supports");
		if (fin == -1) {
			fin = linea.indexOf(" requires");
		}
		return limpiarFormato(linea.substring(inicio, fin));
	}

	private String extraerDependencia(String linea) {
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
		String clave;
		if (linea.contains("only supports")) {
			clave = "supports ";
		} else {
			clave = "requires ";
		}
		int inicio = linea.indexOf(clave) + clave.length();
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

	private void procesarFormatoEspecial(Consola consola, String lineaActual, int numero_de_linea) {
		try {
			// Limpiar formato de Minecraft (§e, §r, etc.)
			String lineaLimpia = limpiarFormato(lineaActual);

			// Extraer mod ID
			String modId = lineaLimpia.substring(lineaLimpia.indexOf("Mod ") + 4, lineaLimpia.indexOf(" only supports"))
					.trim();

			// Extraer dependencia
			String dependencia = lineaLimpia.substring(lineaLimpia.indexOf("supports ") + 9,
					lineaLimpia.indexOf(" ", lineaLimpia.indexOf("supports ") + 9)).trim();

			// Extraer versión requerida especial
			String versionRequerida = lineaLimpia.substring(lineaLimpia.indexOf(dependencia) + dependencia.length(),
					lineaLimpia.indexOf(" or above")).trim();

			// Buscar versión actual en las siguientes líneas
			String versionActual = "no encontrada";
			for (int i = 1; i <= 3; i++) {
				if (numero_de_linea + i < lineasConsola.length) {
					String lineaSiguiente = limpiarFormato(lineasConsola[numero_de_linea + i].trim());
					if (lineaSiguiente.startsWith("Currently,") && lineaSiguiente.contains(dependencia)) {
						versionActual = lineaSiguiente.substring(lineaSiguiente.indexOf("is ") + 3).trim();
						break;
					}
				}
			}

			// Crear mensaje de error
			String mensaje = MonitorDePID.idioma.errorVersionDependencia(modId, dependencia, versionRequerida,
					versionActual);

			if (errores.add(mensaje)) {
				String enlace = consola.agregarErrorALectador(numero_de_linea, this);
				enlacesPorError.put(mensaje, enlace);
				activado = true;
			}
		} catch (Exception e) {
			consola.agregarErrorALectador(numero_de_linea, this);
		}
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
			String cleanError = error.trim().replace("\t", " ").replaceAll("\\s+", " ");
			if (!html.toString().contains(cleanError)) {
				String enlace = enlacesPorError.getOrDefault(cleanError, "");
				html.append("<li>").append(cleanError).append(" ").append(enlace).append("</li>");
			}
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_faltas_dependencias_de_modlauncher();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}

	@Override
	public String id() {
		return "faltas_dependencias_modlauncher";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String contenido = trazo.trace;

		for (String error : errores) {
			if (error != null && !error.isEmpty()) {
				String trim = error.trim();
				if (!trim.isEmpty() && contenido.contains(trim)) {
					return true;
				}
			}
		}

		// NUEVO: Añadir detección para los nuevos formatos
		if (contenido.contains("Failure message: Mod ")
				&& (contenido.contains(" requires ") || contenido.contains(" only supports "))
				&& contenido.contains("Currently,")) {
			return true;
		}

		if (contenido.contains("Missing or unsupported mandatory dependencies:")
				|| (contenido.contains("only supports") && contenido.contains("or above"))
				|| (contenido.contains("requires") && contenido.contains("or above")
						&& contenido.contains("Currently,"))) {
			return true;
		}

		return false;
	}
}