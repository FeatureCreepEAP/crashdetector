package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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
	private boolean posibleError = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String TEXTO_ERROR = "java.lang.IncompatibleClassChangeError";
	private static final String TEXTO_OVERRIDES_FINAL = "overrides final method";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		// Solo activa el análisis por línea si el log contiene los textos clave.
		posibleError = consola.contenido_verificar.contains(TEXTO_ERROR)
				&& consola.contenido_verificar.contains(TEXTO_OVERRIDES_FINAL);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleError || activado || linea == null) {
			return;
		}

		String recorte = linea.trim();

		if (!recorte.contains(TEXTO_ERROR) || !recorte.contains(TEXTO_OVERRIDES_FINAL)) {
			return;
		}

		String claseQueSobrescribe = "";
		String metodoFinal = "";

		int indiceClase = recorte.indexOf("class ");
		int indiceOverrides = recorte.indexOf(TEXTO_OVERRIDES_FINAL);

		if (indiceClase >= 0 && indiceOverrides > indiceClase) {
			claseQueSobrescribe = recorte.substring(indiceClase + "class ".length(), indiceOverrides).trim();
			metodoFinal = recorte.substring(indiceOverrides + TEXTO_OVERRIDES_FINAL.length()).trim();
		}

		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		mensaje = MonitorDePID.idioma.errorMetodoFinalSobrescrito(claseQueSobrescribe, metodoFinal)
				+ Verificaciones.nl_html + enlaceHtml;

		activado = true;
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

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/general/" + this.getClass().getSimpleName()
				+ ".java";
	}
}