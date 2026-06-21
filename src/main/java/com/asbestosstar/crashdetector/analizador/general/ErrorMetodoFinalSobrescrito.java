package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores IncompatibleClassChangeError donde una clase intenta
 * sobrescribir un método final de Minecraft o de otra librería.
 *
 * Ejemplo: java.lang.IncompatibleClassChangeError: class
 * qouteall.imm_ptl.core.portal.Portal overrides final method
 * net.minecraft.world.entity.Entity.m20191()Lnet/minecraft/world/phys/AABB;
 */
public class ErrorMetodoFinalSobrescrito implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String TEXTO_ERROR = "java.lang.IncompatibleClassChangeError";
	private static final String TEXTO_OVERRIDES_FINAL = "overrides final method";
	private static final String CLASS_PREFIX = "class ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR, TEXTO_OVERRIDES_FINAL };
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

		if (!lineaContieneMetodoFinalSobrescrito(linea)) {
			return;
		}

		String recorte = linea.trim();

		String claseQueSobrescribe = "";
		String metodoFinal = "";

		int indiceClase = recorte.indexOf(CLASS_PREFIX);
		int indiceOverrides = recorte.indexOf(TEXTO_OVERRIDES_FINAL);

		if (indiceClase >= 0 && indiceOverrides > indiceClase) {
			claseQueSobrescribe = recorte.substring(indiceClase + CLASS_PREFIX.length(), indiceOverrides).trim();
			metodoFinal = recorte.substring(indiceOverrides + TEXTO_OVERRIDES_FINAL.length()).trim();
		}

		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		mensaje = MonitorDePID.idioma.errorMetodoFinalSobrescrito(claseQueSobrescribe, metodoFinal)
				+ Verificaciones.nl_html + enlaceHtml;

		activado = true;
	}

	private boolean lineaContieneMetodoFinalSobrescrito(String linea) {
		return linea.contains(TEXTO_ERROR) && linea.contains(TEXTO_OVERRIDES_FINAL);
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.contains(TEXTO_ERROR) && trazo.trace.contains(TEXTO_OVERRIDES_FINAL);
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorMetodoFinalSobrescrito();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public float prioridad() {
		return 1200.0f;
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "metodo_final_sobrescrito";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorMetodoFinalSobrescrito();
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}