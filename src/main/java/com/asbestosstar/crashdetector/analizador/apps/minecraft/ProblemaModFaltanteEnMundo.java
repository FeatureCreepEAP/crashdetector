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
 * Clase que detecta mods faltantes en mundos guardados con Forge.Gracias a
 * Aternos por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaModFaltanteEnMundo implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final List<String> nombresMods = new ArrayList<>();

	/**
	 * Verifica si el log contiene mods faltantes en el mundo guardado.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrón para detectar mods faltantes en mundos guardados
		Pattern patron = Pattern.compile(
				"This world was saved with mod (?!forge)(?!minecraft)(\\w+) which appears to be missing",
				Pattern.CASE_INSENSITIVE);
		Matcher coincidencia = patron.matcher(contenido);

		while (coincidencia.find()) {
			String nombreMod = coincidencia.group(1).trim();
			if (!nombreMod.isEmpty() && !nombresMods.contains(nombreMod)) {
				nombresMods.add(nombreMod);
			}
		}

		if (!nombresMods.isEmpty()) {
			if (nombresMods.size() > 1) {
				this.mensaje = MonitorDePID.idioma.mensajeWorldModFaltantePlural(nombresMods);
			} else {
				this.mensaje = MonitorDePID.idioma.mensajeWorldModFaltanteSingular(nombresMods.get(0));
			}
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaModFaltanteEnMundo();
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
		return 850.0f;
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
		return MonitorDePID.idioma.nombreProblemaWorldModFaltante();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		// Agregar solución para cada mod faltante
		for (String mod : nombresMods) {
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod(mod));
		}

		// Agregar solución de no hacer nada
		builder.agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible());
		return builder.construir();
	}

	@Override
	public boolean anularNormal() {
		return true;
	}
}