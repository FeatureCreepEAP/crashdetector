package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el error nativo "StubRoutines::SafeFetch32" relacionado con JDK
 * 17.0.9 en macOS. Este problema se resuelve actualizando a JDK 17.0.10 o
 * superior.
 */
public class ProblemaSafeFetch32JDK17 implements VerificacionRapida {

	private boolean posibleSafeFetch32 = false;
	private boolean activado = false;

	private String mensaje = "";
	private String enlace = "";

	private static final String TEXTO_SAFE_FETCH = "StubRoutines::SafeFetch32";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_SAFE_FETCH };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		posibleSafeFetch32 = true;
		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificacion global ligera.
	 *
	 * Se ejecuta primero. No se limpian campos porque esta verificacion puede
	 * ejecutarse sobre varios archivos de log con la misma instancia.
	 *
	 * No usa regex, toLowerCase ni regionMatches. El simbolo del JVM aparece con
	 * capitalizacion estable.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		if (consola.contenido_verificar.contains(TEXTO_SAFE_FETCH)) {
			posibleSafeFetch32 = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleSafeFetch32 && !activado;
	}

	/**
	 * Verificacion por linea.
	 *
	 * Detecta la linea exacta del error y agrega enlace al lector.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleSafeFetch32 || activado || linea == null || linea.isEmpty()) {
			return;
		}

		if (!linea.contains(TEXTO_SAFE_FETCH)) {
			return;
		}

		if (consola != null) {
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		this.mensaje = MonitorDePID.idioma.problema_safe_fetch32_jdk17() + " " + enlace;
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaSafeFetch32JDK17();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 850.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_problema_safe_fetch32_jdk17();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_actualizar_jdk_macos());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_usar_lanzador_con_jdk_actualizado());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_desactivar_spark_mod());
		return builder.construir();
	}

	@Override
	public String id() {
		return "problema_safe_fetch32_jdk17";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.contains(TEXTO_SAFE_FETCH);
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