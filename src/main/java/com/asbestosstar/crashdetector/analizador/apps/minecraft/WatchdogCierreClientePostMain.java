package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el watchdog generado cuando el cliente de Minecraft tarda demasiado
 * en terminar después de iniciar el proceso de cierre.
 *
 * <p>
 * Firma típica:
 * </p>
 *
 * <pre>
 * java.lang.Error: Watchdog (Client shutdown from post-main)
 * </pre>
 *
 * <p>
 * Este informe aparece cuando el juego tarda más de aproximadamente 15 segundos
 * en cerrar. Normalmente no representa un problema grave por sí solo, aunque
 * algún mod podría estar demorando el cierre o podría existir otro fallo
 * anterior en el registro.
 * </p>
 */
public class WatchdogCierreClientePostMain implements Verificaciones {

	private static final String ERROR_WATCHDOG_CIERRE = "Watchdog (Client shutdown from post-main)";

	// Indica si esta verificación fue activada.
	private boolean activado = false;

	// Enlace a la línea donde apareció el watchdog.
	private String enlace = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ERROR_WATCHDOG_CIERRE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (activado || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(ERROR_WATCHDOG_CIERRE)) {
			this.enlace = evento.consola.agregarErrorALectador(evento.numeroDeLinea, this);
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new WatchdogCierreClientePostMain();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		// Es principalmente informativo y no debe ocultar errores más importantes.
		return 1000.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeWatchdogCierreClientePostMain() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreWatchdogCierreClientePostMain();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "watchdog_cierre_cliente_post_main";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}