package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class ErrorDeEnlaceInsatisfecho implements Verificaciones {

	private boolean activado = false;

	private String nombreBiblioteca = "";
	private String mensaje = "";
	private String enlace = "";

	private static final String TEXTO_ERROR = "java.lang.UnsatisfiedLinkError: Failed to locate library: ";
	private static final String TEXTO_ERROR_SIMPLE = "Failed to locate library: ";
	private static final String VULKAN = "Vulkan";
	private static final String PROBE = "Probe";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR, TEXTO_ERROR_SIMPLE };
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
	 * Detecta el nombre exacto de la biblioteca faltante y agrega enlace a la
	 * linea.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!linea.contains(TEXTO_ERROR)) {
			return;
		}

		// 🔹 Excluir explícitamente las líneas de Vulkan Probe
		if (linea.contains(VULKAN) && linea.contains(PROBE)) {
			return; // ignorar esta línea
		}

		String biblioteca = extraerNombreBiblioteca(linea);

		if (biblioteca == null || biblioteca.isEmpty()) {
			return;
		}

		this.nombreBiblioteca = biblioteca;
		this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		this.mensaje = MonitorDePID.idioma.error_enlace_insatisfecho(nombreBiblioteca) + " " + enlace;
		this.activado = true;
	}

	/**
	 * Extrae el texto después de:
	 *
	 * java.lang.UnsatisfiedLinkError: Failed to locate library:
	 *
	 * Sin Pattern/Matcher.
	 */
	private String extraerNombreBiblioteca(String linea) {
		int inicio = linea.indexOf(TEXTO_ERROR);

		if (inicio < 0) {
			return null;
		}

		inicio += TEXTO_ERROR.length();

		while (inicio < linea.length() && Character.isWhitespace(linea.charAt(inicio))) {
			inicio++;
		}

		if (inicio >= linea.length()) {
			return null;
		}

		int fin = buscarFinNombreBiblioteca(linea, inicio);

		if (fin <= inicio) {
			return null;
		}

		return limpiarNombreBiblioteca(linea.substring(inicio, fin));
	}

	/**
	 * El patron original usaba .* hasta el final de la linea.
	 *
	 * Aquí se corta al final de la linea y se eliminan caracteres comunes de
	 * cierre.
	 */
	private int buscarFinNombreBiblioteca(String linea, int inicio) {
		int fin = linea.length();

		int saltoN = linea.indexOf('\n', inicio);
		if (saltoN >= 0 && saltoN < fin) {
			fin = saltoN;
		}

		int saltoR = linea.indexOf('\r', inicio);
		if (saltoR >= 0 && saltoR < fin) {
			fin = saltoR;
		}

		return fin;
	}

	/**
	 * Limpia espacios y signos que a veces quedan pegados al nombre.
	 */
	private String limpiarNombreBiblioteca(String nombre) {
		if (nombre == null) {
			return "";
		}

		nombre = nombre.trim();

		while (!nombre.isEmpty()) {
			char ultimo = nombre.charAt(nombre.length() - 1);

			if (ultimo == ',' || ultimo == ';') {
				nombre = nombre.substring(0, nombre.length() - 1).trim();
			} else {
				break;
			}
		}

		return nombre;
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorDeEnlaceInsatisfecho();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 700.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_enlace_insatisfecho();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucion_para_error_enlace_insatisfecho()).construir();
	}

	@Override
	public String id() {
		return "enlace_insatisfecho";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
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