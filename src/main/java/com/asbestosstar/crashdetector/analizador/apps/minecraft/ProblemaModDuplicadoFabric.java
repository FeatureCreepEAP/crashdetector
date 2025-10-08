package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta mods duplicados en Fabric. Gracias a Aternos por que esta
 * es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaModDuplicadoFabric implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreMod = "";
	private String rutaMod = "";

	/**
	 * Verifica si el log contiene mods duplicados en Fabric.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrón de error: "Duplicate versions for mod ID 'nombre' [...] at 'ruta'"
		Pattern patron = Pattern.compile(
				"A critical error occurred\\s*net\\.fabricmc\\.loader\\.discovery\\.ModResolutionException: Duplicate versions for mod ID '([^']+)'(?:.*?at ([^\\]]+))?",
				Pattern.DOTALL);
		Matcher coincidencia = patron.matcher(contenido);

		if (coincidencia.find()) {
			this.nombreMod = coincidencia.group(1);
			this.rutaMod = coincidencia.group(2); // Ruta del archivo duplicado

			this.mensaje = MonitorDePID.idioma.mensajeModDuplicadoFabric(nombreMod) + Verificaciones.nl_html;
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaModDuplicadoFabric();
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
		return MonitorDePID.idioma.nombreProblemaModDuplicadoFabric();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionEliminarModDuplicado(rutaMod))
				.construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "mod_duplicado_fabric";
	}
}