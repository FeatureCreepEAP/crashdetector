package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta mods incompatibles en Fabric.Gracias a Aternos por que esta
 * es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaModIncompatibleFabric implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String primerMod = "";
	private String segundoMod = "";

	/**
	 * Verifica si el log contiene mods incompatibles en Fabric.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrón para detectar incompatibilidad entre dos mods
		Pattern patron = Pattern.compile(
				"\\s*- Mod ([\\w\\-\\._]+) [^\\n]+ (?:is incompatible|conflicts) with [^\\n]+ of (?:mod )?([\\w\\-\\._]+)",
				Pattern.DOTALL);
		Matcher coincidencia = patron.matcher(contenido);

		if (coincidencia.find()) {
			this.primerMod = coincidencia.group(1);
			this.segundoMod = coincidencia.group(2);

			this.mensaje = MonitorDePID.idioma.mensajeModIncompatible(primerMod, segundoMod) + Verificaciones.nl_html;
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaModIncompatibleFabric();
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
		return 900.0f;
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
		return MonitorDePID.idioma.nombreProblemaModIncompatibleFabric();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMod(primerMod))
				.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMod(segundoMod)).construir();
	}
}