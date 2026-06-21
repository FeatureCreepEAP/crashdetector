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

	private boolean vistoParanoia = false;
	private boolean vistoC2ME = false;

	private String enlace = "";

	private static final String TEXTO_PARANOIA = "net.mcreator.paranoia.procedures.ParanoiaProcedure";
	private static final String TEXTO_PARANOIA_CORTO = "ParanoiaProcedure";
	private static final String TEXTO_CHECKED_RANDOM = "CheckedThreadLocalRandom";
	private static final String TEXTO_THREADLOCAL_RANDOM = "ThreadLocalRandom accessed from a different thread";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_PARANOIA, TEXTO_CHECKED_RANDOM, TEXTO_THREADLOCAL_RANDOM };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(TEXTO_PARANOIA)) {
			vistoParanoia = true;
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains(TEXTO_CHECKED_RANDOM) && linea.contains(TEXTO_THREADLOCAL_RANDOM)) {

			vistoC2ME = true;
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (vistoParanoia && vistoC2ME) {
			activado = true;
		}
	}

	private boolean lineaContieneIndicioParanoiaC2ME(String linea) {
		return linea.contains(TEXTO_PARANOIA) || linea.contains(TEXTO_CHECKED_RANDOM)
				|| linea.contains(TEXTO_THREADLOCAL_RANDOM);
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

		return t.contains(TEXTO_PARANOIA_CORTO) && t.contains(TEXTO_CHECKED_RANDOM);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}