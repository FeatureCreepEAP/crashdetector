package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
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

	private static final Set<String> REPORTADOS_GLOBAL = Collections.synchronizedSet(new HashSet<>());

	private boolean activado = false;
	private String enlace = "";
	private boolean chestCavityPresente = false;

	@Override
	public String[] patronesRapidos() {
		return new String[] { "apoth_attrModifiedEvent", "AttributeMap object was modified without a set owner",
				"net.tigereye.chestcavity" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado)
			return;

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		if (consola == null || linea == null || activado)
			return;

		// Verificamos si Chest Cavity está en el log para añadir la nota
		if (linea.indexOf("net.tigereye.chestcavity") >= 0) {
			chestCavityPresente = true;
			return;
		}

		// Buscamos cualquiera de las dos líneas indicadoras
		if ((linea.indexOf("apoth_attrModifiedEvent") >= 0 && linea.indexOf("@Inject") < 0)
				|| linea.indexOf("AttributeMap object was modified without a set owner") >= 0) {

			if (!REPORTADOS_GLOBAL.add(id()))
				return;

			this.enlace = consola.agregarErrorALectador(numeroDeLinea, this);
			this.activado = true;
		}
	}

	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		// No necesita procesamiento final.
	}

	public static void reiniciarGlobal() {
		REPORTADOS_GLOBAL.clear();
	}

	@Override
	public VerificacionesLegacy nueva() {
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