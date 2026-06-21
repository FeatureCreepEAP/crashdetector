package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores internos del Feather Client relacionados con:
 * NoClassDefFoundError: feather/lib/sentry/Sentry
 *
 * Requiere que también esté presente el paquete net.digitalingot.feather
 */
public class ProblemaFeatherClient implements Verificaciones {

	private boolean activado = false;
	private boolean vioNoClassDef = false;
	private boolean vioPaqueteFeather = false;
	private String enlace = "";

	private static final String NO_CLASS_DEF = "java.lang.NoClassDefFoundError: feather/lib/sentry/Sentry";
	private static final String SENTRY = "feather/lib/sentry/Sentry";
	private static final String PAQUETE_FEATHER = "net.digitalingot.feather";

	@Override
	public String[] patronesRapidos() {
		return new String[] { NO_CLASS_DEF, SENTRY, PAQUETE_FEATHER };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(NO_CLASS_DEF)) {
			vioNoClassDef = true;
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains(PAQUETE_FEATHER)) {
			vioPaqueteFeather = true;
		}

		if (vioNoClassDef && vioPaqueteFeather) {
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaFeatherClient();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f; // Cliente externo problemático
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";

		return MonitorDePID.idioma.mensajeProblemaFeatherClientSodium() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaFeatherClient();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_feather_client";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.builder().doc("en", "minecraft/Launchers.md").build();
	}
}