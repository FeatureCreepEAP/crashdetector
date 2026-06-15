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
 * Detecta el crash causado por versiones antiguas de Cupboard (ej. 3.2).
 *
 * Ejemplo:
 *
 * Caused by: java.lang.NullPointerException: Cannot invoke
 * "net.minecraftforge.fml.ModList.forEachModInOrder(...)" because the return
 * value of "net.minecraftforge.fml.ModList.get()" is null
 *
 * at com.cupboard.compat.ClientConfigCompat.setupNeoforge(...)
 */
public class ProblemaCupboardVersion implements VerificacionRapida {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";

	private boolean vistoErrorModList = false;
	private boolean vistoCupboard = false;

	private static final String MODLIST_GET = "ModList.get()";
	private static final String IS_NULL = "is null";
	private static final String CLIENT_CONFIG_COMPAT = "ClientConfigCompat";
	private static final String CUPBOARD_SETUP_NEOFORGE = "com.cupboard.compat.ClientConfigCompat.setupNeoforge";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MODLIST_GET, CLIENT_CONFIG_COMPAT, CUPBOARD_SETUP_NEOFORGE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		if (evento.linea.contains(MODLIST_GET) || evento.linea.contains(CLIENT_CONFIG_COMPAT)
				|| evento.linea.contains(CUPBOARD_SETUP_NEOFORGE)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {

		if (consola == null || consola.contenido_verificar == null)
			return;

		String log = consola.contenido_verificar;

		// Pre-check global
		if (log.contains(MODLIST_GET) && log.contains(CLIENT_CONFIG_COMPAT)) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || activado || linea == null)
			return;

		if (linea.contains(MODLIST_GET) && linea.contains(IS_NULL)) {
			vistoErrorModList = true;
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains(CUPBOARD_SETUP_NEOFORGE)) {
			vistoCupboard = true;
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (vistoErrorModList && vistoCupboard) {
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaCupboardVersion();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500f;
	}

	@Override
	public String mensaje() {

		if (!activado)
			return "";

		return MonitorDePID.idioma.problemaCupboardVersion() + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaCupboardVersion();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "problema_cupboard_version";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (trazo == null || trazo.trace == null)
			return false;

		String t = trazo.trace;

		return t.contains(CLIENT_CONFIG_COMPAT) && t.contains(MODLIST_GET);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}