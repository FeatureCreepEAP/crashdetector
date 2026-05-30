package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta la opción de GC inválida "Conflicting collector combinations in
 * option list" en Java. Esto genera errores de arranque en la JVM.
 */
public class OpcionesJavaGCInvalidas implements Verificaciones {

	private boolean activado = false;
	private boolean posibleGCInvalido = false;

	private String mensaje = "";
	private String enlace = "";

	private static final String TEXTO_GC_INVALIDO = "Conflicting collector combinations in option list";

	/**
	 * Verificación global ligera. Marca si el log contiene la cadena GC inválida.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		if (consola.contenido_verificar.contains(TEXTO_GC_INVALIDO)) {
			posibleGCInvalido = true;
		}
	}

	/**
	 * Verificación por línea. Detecta la línea exacta y agrega enlace al lector.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleGCInvalido || activado || linea == null || linea.isEmpty()) {
			return;
		}

		if (linea.contains(TEXTO_GC_INVALIDO)) {
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorOpcionesGCJava() + " " + enlace;
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new OpcionesJavaGCInvalidas();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 500f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_opciones_java_gc_invalidas();
	}

	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.opcionesGCInvalidas()).construir();
	}

	@Override
	public String id() {
		return "opciones_java_gc_invalidas";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.contains(TEXTO_GC_INVALIDO);
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

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}