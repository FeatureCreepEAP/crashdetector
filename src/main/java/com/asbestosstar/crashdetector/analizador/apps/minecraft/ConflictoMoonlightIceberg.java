package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta un conflicto crítico entre los mods Moonlight e Iceberg durante la
 * recarga de recursos, que provoca un fallo de OpenGL: "No context is current".
 * 
 * Este error ocurre cuando ambos mods intentan registrar listeners de recarga
 * de forma incompatible, especialmente en entornos Forge con mods de Fabric
 * adaptados.
 */
public class ConflictoMoonlightIceberg implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	public boolean analizarLineas = false;

	private boolean tieneMoonlight = false;
	private boolean tieneIceberg = false;

	private static final String FATAL_ERROR = "FATAL ERROR in native method";
	private static final String NO_CONTEXT = "No context is current or a function that is not available in the current context";
	private static final String NO_CONTEXT_CORTO = "No context is current";

	private static final String MOONLIGHT_MINUSCULA = "moonlight";
	private static final String MOONLIGHT_MAYUSCULA = "Moonlight";
	private static final String ICEBERG_MINUSCULA = "iceberg";
	private static final String ICEBERG_MAYUSCULA = "Iceberg";

	private static final Set<String> REPORTADOS = Collections.synchronizedSet(new HashSet<String>());

	public static void reiniciarGlobal() {
		REPORTADOS.clear();
	}

	@Override
	public String[] patronesRapidos() {
		return new String[] { FATAL_ERROR, NO_CONTEXT_CORTO, MOONLIGHT_MINUSCULA, MOONLIGHT_MAYUSCULA,
				ICEBERG_MINUSCULA, ICEBERG_MAYUSCULA };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (linea.contains(MOONLIGHT_MINUSCULA) || linea.contains(MOONLIGHT_MAYUSCULA)) {
			tieneMoonlight = true;
		}

		if (linea.contains(ICEBERG_MINUSCULA) || linea.contains(ICEBERG_MAYUSCULA)) {
			tieneIceberg = true;
		}

		if (linea.contains(FATAL_ERROR) || linea.contains(NO_CONTEXT_CORTO)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		// Modo streaming puro: puede no existir contenido_verificar
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String log = consola.contenido_verificar;

		if (log.contains(MOONLIGHT_MINUSCULA) || log.contains(MOONLIGHT_MAYUSCULA)) {
			tieneMoonlight = true;
		}

		if (log.contains(ICEBERG_MINUSCULA) || log.contains(ICEBERG_MAYUSCULA)) {
			tieneIceberg = true;
		}

		if (log.contains(FATAL_ERROR) && log.contains(NO_CONTEXT)) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (this.activado || linea == null) {
			return;
		}

		if (!analizarLineas || !tieneMoonlight || !tieneIceberg) {
			return;
		}

		if (linea.contains(FATAL_ERROR) && linea.contains(NO_CONTEXT)) {
			activar(consola, numero_de_linea);
		}
	}

	private void activar(Consola consola, int numero_de_linea) {
		if (!REPORTADOS.add(id())) {
			return;
		}

		String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		this.mensaje = MonitorDePID.idioma.conflictoMoonlightIceberg() + enlaceHtml;
		this.activado = true;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;
		return t.contains(FATAL_ERROR) && t.contains(NO_CONTEXT_CORTO) && t.contains(MOONLIGHT_MINUSCULA)
				&& t.contains(ICEBERG_MINUSCULA);
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoMoonlightIceberg();
	}

	@Override
	public boolean activado() {
		return this.activado;
	}

	@Override
	public String mensaje() {
		return this.mensaje;
	}

	@Override
	public float prioridad() {
		return 1100.0f; // Alta: provoca cierre inmediato del juego
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionConflictoMoonlightIceberg())
				.construir();
	}

	@Override
	public String id() {
		return "conflicto_moonlight_iceberg";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreConflictoMoonlightIceberg();
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}