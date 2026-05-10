package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta "FATAL ERROR in native method … No context is current or a function
 * that is not available…". Si existe un stacktrace cerca, intenta obtener el
 * origen con vdst.
 */
public class ErrorContextoOpenGL implements Verificaciones {

	private boolean posibleContextoOpenGL = false;
	private boolean activado = false;

	private String mensaje = "";
	private String enlaceHtml = "";
	private String origen = "";

	// Estado para detectar el caso donde la cabecera está en una línea y el detalle
	// en la siguiente.
	private boolean cabeceraPendiente = false;
	private int lineaCabeceraPendiente = -1;

	private static final String TEXTO_CABECERA = "FATAL ERROR in native method";
	private static final String TEXTO_DETALLE = "No context is current or a function that is not available in the current context was called";

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

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_CABECERA) && contenido.contains(TEXTO_DETALLE)) {
			posibleContextoOpenGL = true;
		}
	}

	/**
	 * Verificacion por linea.
	 *
	 * Detecta la línea exacta del error y agrega el enlace al lector.
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!posibleContextoOpenGL || activado || linea == null || linea.isEmpty()) {
			return;
		}

		boolean cabeceraAqui = linea.contains(TEXTO_CABECERA);
		boolean detalleAqui = linea.contains(TEXTO_DETALLE);

		// Caso 1: cabecera y detalle en la misma línea.
		if (cabeceraAqui && detalleAqui) {
			activar(consola, numero_de_linea);
			return;
		}

		// Caso 2: cabecera en la línea anterior y detalle en esta línea.
		if (cabeceraPendiente && detalleAqui) {
			activar(consola, lineaCabeceraPendiente >= 0 ? lineaCabeceraPendiente : numero_de_linea);
			cabeceraPendiente = false;
			lineaCabeceraPendiente = -1;
			return;
		}

		// Guardar cabecera por si el detalle está en la siguiente línea.
		if (cabeceraAqui) {
			cabeceraPendiente = true;
			lineaCabeceraPendiente = numero_de_linea;
			return;
		}

		// La cabecera solo debe vivir una línea.
		if (cabeceraPendiente) {
			cabeceraPendiente = false;
			lineaCabeceraPendiente = -1;
		}
	}

	private void activar(Consola consola, int numero_de_linea) {
		mensaje = MonitorDePID.idioma.errorContextoOpenGL();

		origen = detectarOrigenConVDST(consola.verificacion_de_stacktrace, consola.contenido_verificar,
				numero_de_linea);

		if (!origen.isEmpty()) {
			mensaje = mensaje + " <b>(" + origen + ")</b>";
		}

		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		activado = true;
	}

	/**
	 * Busca un trace cercano usando vdst y extrae el primer origen plausible
	 * (jar/modid/paquete) con los métodos utilitarios de VerificacionDeStackTrace.
	 */
	private String detectarOrigenConVDST(VerificacionDeStackTrace vdst, String log, int lineaInicio) {
		VerificacionDeStackTrace.TraceInfo candidato = null;
		int ventanaSuperior = lineaInicio + 60;

		for (VerificacionDeStackTrace.TraceInfo ti : VerificacionDeStackTrace.obtenerTracesFatalConLinea(log)) {
			if (ti.consolaLineaComenzar >= lineaInicio && ti.consolaLineaComenzar <= ventanaSuperior) {
				candidato = ti;
				break;
			}
		}

		if (candidato == null) {
			for (VerificacionDeStackTrace.TraceInfo ti : VerificacionDeStackTrace.obtenerTracesConLinea(log)) {
				if (ti.consolaLineaComenzar >= lineaInicio && ti.consolaLineaComenzar <= ventanaSuperior) {
					candidato = ti;
					break;
				}
			}
		}

		if (candidato == null) {
			return "";
		}

		return origenEnTextoMultilinea(candidato.trace);
	}

	/**
	 * Analiza texto multilinea sin split().
	 */
	private String origenEnTextoMultilinea(String texto) {
		if (texto == null || texto.isEmpty()) {
			return "";
		}

		int inicio = 0;

		while (inicio < texto.length()) {
			int fin = texto.indexOf('\n', inicio);

			if (fin < 0) {
				fin = texto.length();
			}

			int finReal = fin;

			if (finReal > inicio && texto.charAt(finReal - 1) == '\r') {
				finReal--;
			}

			if (finReal > inicio) {
				String linea = texto.substring(inicio, finReal);
				String posible = origenEnLinea(linea);

				if (!posible.isEmpty()) {
					return posible;
				}
			}

			inicio = fin + 1;
		}

		return "";
	}

	/**
	 * Extrae origen de una línea con los utilitarios vdst: - jar primero - luego
	 * modid - luego paquete, evitando prefijos comunes
	 */
	private String origenEnLinea(String linea) {
		for (String jar : VerificacionDeStackTrace.extraerJarsDeLinea(linea)) {
			if (jar.contains(".jar") && !VerificacionDeStackTrace.isJarNoPermite(jar)) {
				return jar;
			}
		}

		String modid = VerificacionDeStackTrace.extraerModidDeLinea(linea);
		if (modid != null && !VerificacionDeStackTrace.esModNoPermite(modid)) {
			return modid;
		}

		String pack = VerificacionDeStackTrace.extraerPaqueteDeLinea(linea);
		if (pack != null && !empiezaConPrefijoNoPermitido(pack)) {
			return pack;
		}

		return "";
	}

	private boolean empiezaConPrefijoNoPermitido(String pack) {
		for (String p : VerificacionDeStackTrace.package_no_permite) {
			if (pack.startsWith(p)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorContextoOpenGL();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1050.0f;
	}

	@Override
	public String mensaje() {
		if (!activado) {
			return "";
		}

		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorContextoOpenGL();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1ErrorContextoOpenGL())
				.construir();
	}

	@Override
	public String id() {
		return "contexto_opengl";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.contains(TEXTO_DETALLE) || trazo.trace.contains(TEXTO_CABECERA);
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