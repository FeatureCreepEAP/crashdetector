package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta "FATAL ERROR in native method … No context is current or a function
 * that is not available…". Si existe un stacktrace cerca, intenta obtener el
 * origen con vdst.
 */
public class ErrorContextoOpenGL implements Verificaciones {

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

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_CABECERA, TEXTO_DETALLE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificacion por linea.
	 *
	 * Detecta la línea exacta del error y agrega el enlace al lector.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

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

		origen = detectarOrigenConVDST(consola.verificacion_de_stacktrace, consola, numero_de_linea);

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
	private String detectarOrigenConVDST(VerificacionDeStackTrace vdst, Consola log, int lineaInicio) {
		if (log == null) {
			return "";
		}

		VerificacionDeStackTrace.TraceInfo candidato = null;
		int ventanaSuperior = lineaInicio + 60;

		for (VerificacionDeStackTrace.TraceInfo ti : VerificacionDeStackTrace.obtenerTracesFatalConLinea(log)) {
			if (ti != null && ti.consolaLineaComenzar >= lineaInicio && ti.consolaLineaComenzar <= ventanaSuperior) {
				candidato = ti;
				break;
			}
		}

		if (candidato == null) {
			for (VerificacionDeStackTrace.TraceInfo ti : VerificacionDeStackTrace.obtenerTracesConLinea(log)) {
				if (ti != null && ti.consolaLineaComenzar >= lineaInicio
						&& ti.consolaLineaComenzar <= ventanaSuperior) {
					candidato = ti;
					break;
				}
			}
		}

		if (candidato == null) {
			return "";
		}

		// Nuevo sistema: TraceInfo.trace puede ser null.
		// Primero usamos las LineaTrazo ya procesadas por VDST.
		if (candidato.lineas != null && !candidato.lineas.isEmpty()) {
			for (VerificacionDeStackTrace.LineaTrazo lt : candidato.lineas) {
				if (lt == null) {
					continue;
				}

				if (lt.origen != null && !lt.origen.isEmpty()) {
					return lt.origen;
				}
			}
		}

		// Fallback viejo: si trace todavía existe, analizar texto multilinea.
		if (candidato.trace != null && !candidato.trace.isEmpty()) {
			String origenDesdeTexto = origenEnTextoMultilinea(candidato.trace);

			if (!origenDesdeTexto.isEmpty()) {
				return origenDesdeTexto;
			}
		}

		// Fallback nuevo: reconstruir desde lineas_verificar sin split del trace.
		return origenEnRangoDeLineas(log.lineas_verificar, candidato.consolaLineaComenzar,
				candidato.consolaLineaTerminar);
	}

	private String origenEnRangoDeLineas(String[] lineas, int inicio, int fin) {
		if (lineas == null || lineas.length == 0) {
			return "";
		}

		int desde = Math.max(0, inicio);
		int hasta = fin >= 0 ? Math.min(fin, lineas.length - 1) : Math.min(desde + 60, lineas.length - 1);

		for (int i = desde; i <= hasta; i++) {
			String linea = lineas[i];

			if (linea == null || linea.isEmpty()) {
				continue;
			}

			String posible = origenEnLinea(linea);

			if (!posible.isEmpty()) {
				return posible;
			}
		}

		return "";
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
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_CABECERA, TEXTO_DETALLE };
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