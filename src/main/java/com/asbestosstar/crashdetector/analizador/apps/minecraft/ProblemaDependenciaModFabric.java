package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que detecta dependencias faltantes en mods de Fabric.Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaDependenciaModFabric implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final List<String> nombresMods = new ArrayList<>();
	private final List<String> dependencias = new ArrayList<>();
	private final List<String> versiones = new ArrayList<>();

	/**
	 * Verifica si el log contiene dependencias faltantes en mods de Fabric.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contento_verificar;

		// Definición de patrones
		Pattern[] patrones = {
				// Ejemplo: "Could not find required mod: examplemod requires {anothermod @
				// [1.0.0]}"
				Pattern.compile("net\\.fabricmc\\.loader\\.discovery\\.ModResolutionException: "
						+ "Could not find required mod: ([\\w\\-\\._]+) "
						+ "requires \\{([\\w\\-\\._]+) @ \\$\\$([^\\$]+)\\$\\$\\}", Pattern.DOTALL),
				// Ejemplo: "requires any version of mod anothermod"
				Pattern.compile(
						"\\s*- Mod ([\\w\\-\\._]+)(?: [^ ]+)? requires any version of (?:mod )?([\\w\\-\\._]+),",
						Pattern.DOTALL),
				// Ejemplo: "requires version 1.2.3 or later of mod anothermod"
				Pattern.compile(
						"\\s*- Mod ([\\w\\-\\._]+)(?: [^ ]+)? requires version ([^ ]+) or later of (?:mod )?([\\w\\-\\._]+),",
						Pattern.DOTALL),
				// Ejemplo: "requires any version after 1.1 of mod brokenmod"
				Pattern.compile(
						"\\s*- Mod ([\\w\\-\\._]+)(?: [^ ]+)? requires any version after ([^ ]+) of (?:mod )?([\\w\\-\\._]+),",
						Pattern.DOTALL),
				// Ejemplo: "requires any version before 1.1 of mod brokenmod"
				Pattern.compile(
						"\\s*- Mod ([\\w\\-\\._]+)(?: [^ ]+)? requires any version before ([^ ]+) of (?:mod )?([\\w\\-\\._]+),",
						Pattern.DOTALL),
				// Ejemplo: "requires version 1.2.3 of mod anothermod"
				Pattern.compile(
						"\\s*- Mod ([\\w\\-\\._]+)(?: [^ ]+)? requires version ([^ ]+) of (?:mod )?([\\w\\-\\._]+),",
						Pattern.DOTALL),
				// Ejemplo: "requires any version between 1.1 (inclusive) and 1.3 (exclusive) of
				// mod brokenmod"
				Pattern.compile(
						"\\s*- Mod ([\\w\\-\\._]+)" + "(?: [^ ]+)? requires any version between " + "([^ ]+) "
								+ "\\$\\$(inclusive|exclusive)\\$\\$ " + "and ([^ ]+) "
								+ "\\$\\$(inclusive|exclusive)\\$\\$ " + "of (?:mod )?([\\w\\-\\._]+),",
						Pattern.DOTALL) };

		String[] tipos = { "short-error", "any", "minimum", "any-after", "any-before", "specific", "between" };

		for (int i = 0; i < patrones.length; i++) {
			Matcher coincidencia = patrones[i].matcher(contenido);
			while (coincidencia.find()) {
				String tipo = tipos[i];
				switch (tipo) {
				case "short-error":
					nombresMods.add(coincidencia.group(1));
					dependencias.add(coincidencia.group(2));
					versiones.add(coincidencia.group(3));
					break;
				case "any":
					nombresMods.add(coincidencia.group(1));
					dependencias.add(coincidencia.group(2));
					versiones.add("");
					break;
				case "minimum":
				case "any-after":
				case "any-before":
				case "specific":
					nombresMods.add(coincidencia.group(1));
					dependencias.add(coincidencia.group(3));
					versiones.add(coincidencia.group(2));
					break;
				case "between":
					String modName = coincidencia.group(1);
					String depMod = coincidencia.group(5);
					String minVersion = coincidencia.group(2);
					String minType = coincidencia.group(3);
					String maxVersion = coincidencia.group(4);
					String maxType = coincidencia.group(5);

					String rango = String.format("%s%s - %s%s", minType.equals("inclusive") ? "≥ " : "> ", minVersion,
							maxType.equals("inclusive") ? "≤ " : "< ", maxVersion);

					nombresMods.add(modName);
					dependencias.add(depMod);
					versiones.add(rango);
					break;
				}
			}
		}

		if (!nombresMods.isEmpty()) {
			if (dependencias.size() > 1) {
				this.mensaje = MonitorDePID.idioma.mensajeModDependenciaPlural(dependencias);
			} else {
				this.mensaje = MonitorDePID.idioma.mensajeDependenciaModFaltante(nombresMods.get(0),
						dependencias.get(0), versiones.get(0));
			}
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaDependenciaModFabric();
	}

	/**
	 * Indica si el problema fue detectado.
	 */
	@Override
	public boolean activado() {
		return activado;
	}

	/**
	 * Prioridad del problema (alta).
	 */
	@Override
	public float prioridad() {
		return 800.0f;
	}

	/**
	 * Devuelve el mensaje de error almacenado.
	 */
	@Override
	public String mensaje() {
		return mensaje;
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaDependenciaMod();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		for (int i = 0; i < dependencias.size(); i++) {
			String dep = dependencias.get(i);
			String ver = versiones.get(i);

			if (ver != null && !ver.isEmpty()) {
				builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarModConVersion(dep, ver));
			} else {
				builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod(dep));
			}
		}

		return builder.construir();
	}
}