package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el error nativo "StubRoutines::SafeFetch32" relacionado con JDK
 * 17.0.9 en macOS. Este problema se resuelve actualizando a JDK 17.0.10 o
 * superior.
 */
public class ProblemaSafeFetch32JDK17 implements Verificaciones {

	private boolean posibleSafeFetch32 = false;
	private boolean activado = false;

	private String mensaje = "";
	private String enlace = "";

	private static final String TEXTO_SAFE_FETCH = "StubRoutines::SafeFetch32";

	/**
	 * Verificacion global ligera.
	 *
	 * Se ejecuta primero. No se limpian campos porque esta verificacion puede
	 * ejecutarse sobre varios archivos de log con la misma instancia.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		if (contieneIgnoreCase(consola.contenido_verificar, TEXTO_SAFE_FETCH)) {
			posibleSafeFetch32 = true;
		}
	}

	/**
	 * Verificacion por linea.
	 *
	 * Detecta la linea exacta del error y agrega enlace al lector.
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!posibleSafeFetch32 || activado || linea == null || linea.isEmpty()) {
			return;
		}

		if (!contieneIgnoreCase(linea, TEXTO_SAFE_FETCH)) {
			return;
		}

		this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		this.mensaje = MonitorDePID.idioma.problema_safe_fetch32_jdk17() + " " + enlace;
		this.activado = true;
	}

	/**
	 * Busca un texto ignorando mayusculas/minusculas sin crear copias con
	 * toLowerCase().
	 */
	private boolean contieneIgnoreCase(String texto, String buscar) {
		return indexOfIgnoreCase(texto, buscar) >= 0;
	}

	/**
	 * Busca un texto ignorando mayusculas/minusculas usando regionMatches.
	 */
	private int indexOfIgnoreCase(String texto, String buscar) {
		if (texto == null || buscar == null) {
			return -1;
		}

		int largoTexto = texto.length();
		int largoBuscar = buscar.length();

		if (largoBuscar == 0) {
			return 0;
		}

		if (largoBuscar > largoTexto) {
			return -1;
		}

		int limite = largoTexto - largoBuscar;

		for (int i = 0; i <= limite; i++) {
			if (texto.regionMatches(true, i, buscar, 0, largoBuscar)) {
				return i;
			}
		}

		return -1;
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
		return 850.0f; // Alta prioridad por ser un fallo nativo
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

		return contieneIgnoreCase(trazo.trace, TEXTO_SAFE_FETCH);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}