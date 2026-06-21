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
 * Detecta errores relacionados con el teselado de bloques en Minecraft.
 * Modernized: no regex, no regionMatches, exact-case scan.
 */
public class BloqueTeselado implements Verificaciones {

	private static final Set<String> REPORTADOS_GLOBAL = Collections.synchronizedSet(new HashSet<>());

	private boolean activado = false;
	private String mensaje = "";
	private String enlace = "";

	private static final String TEXTO_TESSELADO = "Tesselating block in world";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_TESSELADO };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado)
			return;

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (consola == null || activado || linea == null || linea.isEmpty()) {
			return;
		}

		if (linea.indexOf(TEXTO_TESSELADO) >= 0) {
			if (!REPORTADOS_GLOBAL.add(id()))
				return;

			enlace = consola.agregarErrorALectador(numero_de_linea, this);
			mensaje = MonitorDePID.idioma.errorDeBloqueTeselado() + " " + enlace;
			activado = true;
		}
	}

	private String extraerLinea(String log, int pos) {
		int inicio = log.lastIndexOf('\n', pos);
		int fin = log.indexOf('\n', pos);

		if (inicio < 0)
			inicio = 0;
		else
			inicio++;

		if (fin < 0)
			fin = log.length();

		return log.substring(inicio, fin);
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
		return new BloqueTeselado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 500.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_bloque_teselado();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "bloqueteselado";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}
		return trazo.trace.indexOf(TEXTO_TESSELADO) >= 0;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}