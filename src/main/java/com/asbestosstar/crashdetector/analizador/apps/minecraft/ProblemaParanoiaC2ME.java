package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos entre el mod Paranoia (MCreator) y C2ME relacionados con
 * acceso incorrecto a ThreadLocalRandom desde diferentes hilos.
 *
 * Ejemplo de líneas:
 *
 * at net.mcreator.paranoia.procedures.ParanoiaProcedure.
 *
 * com.ishland.c2me.fixes.worldgen.threading_issues.common.CheckedThreadLocalRandom$1:
 * ThreadLocalRandom accessed from a different thread
 */
public class ProblemaParanoiaC2ME implements Verificaciones {

	private boolean activado = false;
	private boolean analizarLineas = false;

	private boolean vistoParanoia = false;
	private boolean vistoC2ME = false;

	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		// Pre-check global para rendimiento
		if (log.contains("net.mcreator.paranoia.procedures.ParanoiaProcedure")
				&& log.contains("CheckedThreadLocalRandom")) {

			analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null)
			return;

		if (linea.contains("net.mcreator.paranoia.procedures.ParanoiaProcedure")) {
			vistoParanoia = true;
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains("CheckedThreadLocalRandom")
				&& linea.contains("ThreadLocalRandom accessed from a different thread")) {

			vistoC2ME = true;
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (vistoParanoia && vistoC2ME) {
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaParanoiaC2ME();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 850f;
	}

	@Override
	public String mensaje() {

		if (!activado)
			return "";

		return MonitorDePID.idioma.problemaParanoiaC2ME() + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaParanoiaC2ME();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_paranoia_c2me";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (trazo == null || trazo.trace == null)
			return false;

		String t = trazo.trace;

		return t.contains("ParanoiaProcedure") && t.contains("CheckedThreadLocalRandom");
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