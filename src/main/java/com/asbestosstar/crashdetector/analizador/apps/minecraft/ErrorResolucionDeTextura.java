package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Verificación especializada para detectar errores de resolución de texturas
 * que ocurren cuando las texturas no caben en la hoja de texturas.
 *
 * - No usa Pattern/Matcher. - Usa verificación global ligera. - Usa
 * verificación por línea para agregar enlace exacto. - También analiza trazos
 * completos mediante VerificacionDeStackTrace.
 */
public class ErrorResolucionDeTextura implements Verificaciones {

	private static final String TEXTO_STITCHER = "net.minecraft.client.renderer.texture.StitcherException:";
	private static final String TEXTO_UNABLE = "Unable to fit:";
	private static final String TEXTO_SIZE = " - size:";
	private static final String TEXTO_RESOURCEPACK = "Maybe try a lower resolution resourcepack?";

	/**
	 * Almacena los mensajes de error únicos detectados.
	 */
	private final Map<String, String> errores = new HashMap<>();

	/**
	 * Almacena el enlace HTML por mensaje base.
	 */
	private final Map<String, String> enlacesPorLinea = new HashMap<>();

	private boolean vioUnable = false;
	private boolean vioResourcePack = false;
	private boolean activado = false;

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_STITCHER, TEXTO_UNABLE, TEXTO_RESOURCEPACK };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		if (linea.contains(TEXTO_UNABLE)) {
			vioUnable = true;
		}

		if (linea.contains(TEXTO_RESOURCEPACK)) {
			vioResourcePack = true;
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación por línea: analiza líneas sueltas sin traza completa que
	 * contengan el mensaje de error, típicamente fuera de un stack trace.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Solo consideramos líneas que parecen mensaje, no frames de stack.
		if (!linea.contains(TEXTO_UNABLE) || !linea.contains(TEXTO_RESOURCEPACK) || linea.contains("at ")) {
			return;
		}

		procesarTextoError(linea, numero_de_linea, consola);
	}

	/**
	 * Procesa texto que contiene:
	 *
	 * net.minecraft.client.renderer.texture.StitcherException: Unable to fit:
	 * recurso - size: 512x512 Maybe try a lower resolution resourcepack?
	 *
	 * Sin Pattern/Matcher.
	 */
	private void procesarTextoError(String texto, int numeroLinea, Consola consola) {
		DatosTextura datos = extraerDatosTextura(texto);

		if (datos == null || datos.recurso.isEmpty() || datos.tamano.isEmpty()) {
			return;
		}

		String mensajeBase = MonitorDePID.idioma.error_resolucion_textura(datos.recurso, datos.tamano);

		if (!errores.containsKey(mensajeBase)) {
			enlacesPorLinea.put(mensajeBase, consola.agregarErrorALectador(numeroLinea, this));
			errores.put(mensajeBase, "");
		}

		activado = true;
	}

	/**
	 * Extrae recurso y tamaño sin regex.
	 */
	private DatosTextura extraerDatosTextura(String texto) {
		if (texto == null || texto.isEmpty()) {
			return null;
		}

		int inicioUnable = texto.indexOf(TEXTO_UNABLE);

		if (inicioUnable < 0) {
			return null;
		}

		int inicioRecurso = inicioUnable + TEXTO_UNABLE.length();

		while (inicioRecurso < texto.length() && Character.isWhitespace(texto.charAt(inicioRecurso))) {
			inicioRecurso++;
		}

		if (inicioRecurso >= texto.length()) {
			return null;
		}

		int finRecurso = buscarFinRecurso(texto, inicioRecurso);

		if (finRecurso <= inicioRecurso) {
			return null;
		}

		String recurso = texto.substring(inicioRecurso, finRecurso).trim();

		int inicioSize = texto.indexOf(TEXTO_SIZE, finRecurso);

		if (inicioSize < 0) {
			return null;
		}

		int inicioTamano = inicioSize + TEXTO_SIZE.length();

		while (inicioTamano < texto.length() && Character.isWhitespace(texto.charAt(inicioTamano))) {
			inicioTamano++;
		}

		int finTamano = leerFinTamano(texto, inicioTamano);

		if (finTamano <= inicioTamano) {
			return null;
		}

		String tamano = texto.substring(inicioTamano, finTamano).trim();

		if (!esTamanoValido(tamano)) {
			return null;
		}

		return new DatosTextura(recurso, tamano);
	}

	/**
	 * El patrón original usaba ([^\s]+), por eso el recurso termina en espacio.
	 * También cortamos antes de " - size:" si aparece inmediatamente.
	 */
	private int buscarFinRecurso(String texto, int inicio) {
		int i = inicio;

		while (i < texto.length()) {
			char c = texto.charAt(i);

			if (Character.isWhitespace(c)) {
				break;
			}

			i++;
		}

		return i;
	}

	/**
	 * Lee tamaños tipo: 16x16 512x512 4096x4096
	 */
	private int leerFinTamano(String texto, int inicio) {
		int i = inicio;

		while (i < texto.length()) {
			char c = texto.charAt(i);

			if (Character.isDigit(c) || c == 'x' || c == 'X') {
				i++;
			} else {
				break;
			}
		}

		return i;
	}

	private boolean esTamanoValido(String tamano) {
		if (tamano == null || tamano.isEmpty()) {
			return false;
		}

		int x = Math.max(tamano.indexOf('x'), tamano.indexOf('X'));

		if (x <= 0 || x >= tamano.length() - 1) {
			return false;
		}

		for (int i = 0; i < tamano.length(); i++) {
			char c = tamano.charAt(i);

			if (i == x) {
				if (c != 'x' && c != 'X') {
					return false;
				}
			} else if (!Character.isDigit(c)) {
				return false;
			}
		}

		return true;
	}

	private static class DatosTextura {
		private final String recurso;
		private final String tamano;

		private DatosTextura(String recurso, String tamano) {
			this.recurso = recurso;
			this.tamano = tamano;
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ErrorResolucionDeTextura();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400f; // Prioridad muy alta: problema crítico que impide el arranque
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder("<ul>");

		for (String mensajeBase : errores.keySet()) {
			String enlace = enlacesPorLinea.getOrDefault(mensajeBase, "");

			if (!enlace.isEmpty()) {
				sb.append("<li>").append(mensajeBase).append(MonitorDePID.idioma.solucion_resolucion_textura())
						.append(" ").append(enlace).append("</li>");
			} else {
				sb.append("<li>").append(mensajeBase).append("</li>");
			}
		}

		sb.append("</ul>");
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_resolucion_textura();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucion_resolucion_textura())
				.construir();
	}

	@Override
	public String id() {
		return "error_resolucion_de_textura";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return extraerDatosTextura(trazo.trace) != null;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true; // Dependente en la tarjeta grafica
	}
}