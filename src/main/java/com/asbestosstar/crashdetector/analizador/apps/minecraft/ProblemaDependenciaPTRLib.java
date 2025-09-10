package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta que el mod PTRLib no está instalado.Gracias a Aternos por
 * que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaDependenciaPTRLib implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final String nombreMod = "PTRLib";

	/**
	 * Verifica si el log contiene el error de dependencia faltante de PTRLib.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrón para detectar el error de dependencia faltante de PTRLib
		Pattern patron = Pattern.compile(
				"Encountered an unexpected exception\\s*java\\.lang\\.NoClassDefFoundError: com/mia/craftstudio/IPackReaderCallback",
				Pattern.DOTALL);
		Matcher coincidencia = patron.matcher(contenido);

		if (coincidencia.find()) {
			this.mensaje = MonitorDePID.idioma.mensajeDependenciaModFaltante(nombreMod);
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaDependenciaPTRLib();
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
		return MonitorDePID.idioma.nombreProblemaDependenciaModFaltante();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionInstalarMod(nombreMod)).construir();
	}
}