package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta errores al cargar un mundo con Multiverse.Gracias a Aternos
 * por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaCargaMultiverso implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreMundo = "";

	/**
	 * Verifica si el log contiene el error de carga del mundo por Multiverse.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrón de error: "The world 'nombre' could NOT be loaded..."
		Pattern patron = Pattern
				.compile("The world '([^']+)' could NOT be loaded because it contains errors and is probably corrupt!");
		Matcher coincidencia = patron.matcher(contenido);

		if (coincidencia.find()) {
			this.nombreMundo = coincidencia.group(1);
			this.mensaje = MonitorDePID.idioma.mensajeProblemaCargaMultiverso(nombreMundo) + Verificaciones.nl_html;
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaCargaMultiverso();
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
		return MonitorDePID.idioma.nombreProblemaCargaMultiverso();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionRepararMundo(nombreMundo))
				.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarCarpetaMundo(nombreMundo)).construir();
	}

	@Override
	public boolean anularNormal() {
		return true;
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "carga_multiverso";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}