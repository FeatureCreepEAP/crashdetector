package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores cuando Minecraft falla al crear o cargar un modelo.
 *
 * Ejemplo típico: java.lang.IllegalArgumentException: Failed to create model
 * for bakery:wandering_baker
 *
 * También detecta si aparece "$cooleranims$" en el log, lo cual puede indicar
 * interferencia del mod Cooler Animations.
 */
public class ErrorCreacionModelo implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;

	private boolean posibleCoolerAnims = false;

	private String enlace = "";
	private String modelo = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global para rendimiento
		if (log.contains("Failed to create model for")) {
			analizarLineas = true;
		}

		// Detectar posible interferencia de Cooler Animations
		if (log.contains("$cooleranims$")) {
			posibleCoolerAnims = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (linea.contains("Failed to create model for")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			int inicio = linea.indexOf("Failed to create model for") + "Failed to create model for".length();

			if (inicio > 0 && inicio < linea.length()) {
				modelo = linea.substring(inicio).trim();
			}

			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCreacionModelo();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f;
	}

	@Override
	public String mensaje() {

		if (!activado)
			return "";

		StringBuilder mensaje = new StringBuilder();

		mensaje.append(MonitorDePID.idioma.mensajeErrorCreacionModelo(modelo)).append(this.enlace);

		// Mensaje adicional si se detectó Cooler Animations
		if (posibleCoolerAnims) {

			mensaje.append(Verificaciones.nl_html).append(MonitorDePID.idioma.posibleConflictoCoolerAnimations());
		}

		return mensaje.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorCreacionModelo();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "error_creacion_modelo";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}