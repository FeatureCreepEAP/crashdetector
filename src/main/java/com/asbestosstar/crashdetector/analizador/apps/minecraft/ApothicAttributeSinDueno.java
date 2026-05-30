package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de Apothic Attributes donde un AttributeMap es modificado sin
 * dueño.
 *
 * Ejemplo 1: apoth_attrModifiedEvent Ejemplo 2: An AttributeMap object was
 * modified without a set owner!
 *
 * Causado generalmente por conflictos de mods al registrar atributos de
 * entidades. Especialmente común con el mod Chest Cavity.
 */
public class ApothicAttributeSinDueno implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";
	private boolean chestCavityPresente = false;

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global para activar el análisis línea por línea
		// Buscamos la firma del error específico de Apothic
		if (log.contains("apoth_attrModifiedEvent")
				|| log.contains("AttributeMap object was modified without a set owner")) {
			analizarLineas = true;
		}

		// Verificamos si Chest Cavity está en el log para añadir la nota
		if (log.contains("net.tigereye.chestcavity")) {
			chestCavityPresente = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		// Buscamos cualquiera de las dos líneas indicadoras
		if (linea.contains("apoth_attrModifiedEvent") && !linea.contains("@Inject")
				|| linea.contains("AttributeMap object was modified without a set owner")) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ApothicAttributeSinDueno();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeApothicAttributeSinDueño(chestCavityPresente) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreApothicAttributeSinDueño();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "apothic_attribute_sin_dueño";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}