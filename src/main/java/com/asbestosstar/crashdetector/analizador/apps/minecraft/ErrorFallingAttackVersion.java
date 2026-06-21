package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de compatibilidad con Falling Attack donde el mod está
 * marcado como compatible con 1.19.* pero en realidad es para 1.20.*, causando
 * un ClassMetadataNotFoundException para DamageSources.
 */
public class ErrorFallingAttackVersion implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String FALLING_ATTACK = "falling-attack";
	private static final String CLASS_METADATA_NOT_FOUND = "org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException";
	private static final String DAMAGE_SOURCES = "net.minecraft.world.damagesource.DamageSources";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FALLING_ATTACK, CLASS_METADATA_NOT_FOUND, DAMAGE_SOURCES };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde Falling Attack falla porque
	 * intenta usar una clase que no existe en la versión de Minecraft actual.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la línea que contiene el error de clase no encontrada de Falling
		// Attack
		if (linea.contains(CLASS_METADATA_NOT_FOUND) && linea.contains(DAMAGE_SOURCES)) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de versión de Falling
			// Attack
			mensaje = MonitorDePID.idioma.errorFallingAttackVersion() + VerificacionesLegacy.nl_html;
			activado = true;
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ErrorFallingAttackVersion();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f; // Alta prioridad: rompe la carga del mod
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorFallingAttackVersion();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorFallingAttackVersion())
				.construir();
	}

	@Override
	public String id() {
		return "error_falling_attack_version";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad de versión de Falling Attack.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains(CLASS_METADATA_NOT_FOUND) && t.contains(DAMAGE_SOURCES) && t.contains(FALLING_ATTACK);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}