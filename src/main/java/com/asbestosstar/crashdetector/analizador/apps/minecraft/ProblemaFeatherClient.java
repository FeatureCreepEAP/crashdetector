package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores internos del Feather Client relacionados con:
 * NoClassDefFoundError: feather/lib/sentry/Sentry
 *
 * Requiere que también esté presente el paquete net.digitalingot.feather
 */
public class ProblemaFeatherClient implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private boolean vioNoClassDef = false;
	private boolean vioPaqueteFeather = false;
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global: deben existir ambas cadenas
		if (log.contains("feather/lib/sentry/Sentry") || log.contains("net.digitalingot.feather")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (linea.contains("java.lang.NoClassDefFoundError: feather/lib/sentry/Sentry")) {
			vioNoClassDef = true;
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains("net.digitalingot.feather")) {
			vioPaqueteFeather = true;
		}

		if (vioNoClassDef || vioPaqueteFeather) {
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

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}