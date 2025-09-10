package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta errores fatales causados por Mixin en mods de Fabric.
 * Gracias a Aternos por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaSpongeMixinFabric implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreMod = "";

	/**
	 * Verifica si el log contiene un error de Mixin en un mod de Fabric.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrón para detectar errores de Mixin relacionados con un mod
		Pattern patron = Pattern.compile(
				"MixinTransformerError: An unexpected critical error was encountered[\\s\\S]*?Caused by: [\\s\\S]*?from mod ([\\w\\-\\._]+)",
				Pattern.DOTALL);
		Matcher coincidencia = patron.matcher(contenido);

		if (coincidencia.find()) {
			this.nombreMod = coincidencia.group(1);
			this.mensaje = MonitorDePID.idioma.mensajeModFatal(nombreMod) + Verificaciones.nl_html;
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaSpongeMixinFabric();
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
		return MonitorDePID.idioma.nombreProblemaModFatal();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMod(nombreMod)).construir();
	}
}