package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores NullPointerException que ocurren dentro del código de
 * Neruina.
 *
 * Ejemplo: at com.bawnorton.neruina.util.TickingEntry.writeStackTraceNbt
 * java.lang.NullPointerException: Cannot invoke "String.isEmpty()" because
 * "p_129298_" is null
 *
 * Esto indica que Neruina está fallando en su intento de manejar un error de
 * otra entidad, ocultando la verdadera causa del problema original.
 */
public class NeruinaOcultaAdvertencia implements VerificacionRapida {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";

	private static final String NULL_POINTER_EXCEPTION = "NullPointerException";
	private static final String NERUINA = "com.bawnorton.neruina";
	private static final String NERUINA_STACK = "at com.bawnorton.neruina";
	private static final String WRITE_STACK_TRACE_NBT = "writeStackTraceNbt";

	@Override
	public String[] patronesRapidos() {
		return new String[] { NULL_POINTER_EXCEPTION, NERUINA, WRITE_STACK_TRACE_NBT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(NULL_POINTER_EXCEPTION) || evento.linea.contains(NERUINA)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String log = consola.contenido_verificar;

		// Pre-check global: Debe haber un NullPointerException y rastros de Neruina
		if (log.contains(NULL_POINTER_EXCEPTION) && log.contains(NERUINA)) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		// Buscamos la línea específica del stack trace de Neruina
		if (linea.contains(NERUINA_STACK) && linea.contains(WRITE_STACK_TRACE_NBT)) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new NeruinaOcultaAdvertencia();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f; // Prioridad alta para advertir del problema de depuración
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeNeruinaOcultaAdvertencia() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreNeruinaOcultaAdvertencia();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "neruina_oculta_error";
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