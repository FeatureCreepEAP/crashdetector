package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta mundos duplicados que no pueden cargarse.Gracias a Aternos
 * por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaMundoDuplicado implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreMundo = "";

	/**
	 * Verifica si el log contiene un problema de mundo duplicado.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrón de error: "World nombre_mundo is a duplicate..."
		Pattern patron = Pattern.compile(
				"World ([\\w_\\-]+) is a duplicate of another world and has been prevented from loading.*?uid\\.dat",
				Pattern.DOTALL);
		Matcher coincidencia = patron.matcher(contenido);

		if (coincidencia.find()) {
			this.nombreMundo = coincidencia.group(1);
			this.mensaje = MonitorDePID.idioma.mensajeMundoDuplicado(nombreMundo) + Verificaciones.nl_html;
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaMundoDuplicado();
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
		return 700.0f;
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
		return MonitorDePID.idioma.nombreProblemaMundoDuplicado();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionEliminarUID(nombreMundo))
				.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMundo(nombreMundo)).construir();
	}
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "mundo_duplicado";
	}
}