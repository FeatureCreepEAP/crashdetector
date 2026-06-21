package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
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

	private boolean posibleCoolerAnims = false;

	private String enlace = "";
	private String modelo = "";

	private static final String FAILED_TO_CREATE_MODEL = "Failed to create model for";
	private static final String COOLER_ANIMS = "$cooleranims$";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FAILED_TO_CREATE_MODEL, COOLER_ANIMS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(COOLER_ANIMS)) {
			posibleCoolerAnims = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(COOLER_ANIMS)) {
			posibleCoolerAnims = true;
		}

		if (linea.contains(FAILED_TO_CREATE_MODEL)) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			int inicio = linea.indexOf(FAILED_TO_CREATE_MODEL) + FAILED_TO_CREATE_MODEL.length();

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
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}