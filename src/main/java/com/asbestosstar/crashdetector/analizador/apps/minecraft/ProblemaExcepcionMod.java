package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta excepciones causadas por mods en Forge. Gracias a Aternos
 * por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaExcepcionMod implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final List<String> nombresMods = new ArrayList<>();

	/**
	 * Verifica si el log contiene excepciones de mods en Forge.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrón para detectar excepciones de mods
		Pattern[] patrones = { Pattern.compile("Caught exception from ([^\\(\\n]+)", Pattern.DOTALL), Pattern.compile(
				"net\\.forge\\..*?Loading errors encountered: \\[\\s+([^\\$\\n]+) \\$\\$[^\\)]+\\) encountered an error during",
				Pattern.DOTALL)

		};

		for (Pattern patron : patrones) {
			Matcher coincidencia = patron.matcher(contenido);
			while (coincidencia.find()) {
				String nombreMod = coincidencia.group(1).trim();
				if (!nombreMod.isEmpty() && !nombresMods.contains(nombreMod)) {
					nombresMods.add(nombreMod);
				}
			}
		}

		if (!nombresMods.isEmpty()) {
			if (nombresMods.size() > 1) {
				this.mensaje = MonitorDePID.idioma.mensajeModExcepcionPlural(nombresMods);
			} else {
				this.mensaje = MonitorDePID.idioma.mensajeModExcepcionSingular(nombresMods.get(0));
			}
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaExcepcionMod();
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
		return 1000.0f;
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
		return MonitorDePID.idioma.nombreProblemaModExcepcion();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		for (String mod : nombresMods) {
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionDiferenteMod(mod));
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMod(mod));
		}

		return builder.construir();
	}
}