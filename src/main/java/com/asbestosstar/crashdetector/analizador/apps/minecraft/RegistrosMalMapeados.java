package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class RegistrosMalMapeados implements Verificaciones {

	private boolean posibleRegistrosMalMapeados = false;
	private boolean activado = false;

	private final Set<String> entradas = new HashSet<>();
	private final Map<String, String> enlacesPorEntrada = new HashMap<>();

	private static final String TEXTO_MISSING_ID = "Found a missing id from the world ";
	private static final String TEXTO_UNIDENTIFIED = "Unidentified mapping from registry ";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(TEXTO_MISSING_ID) || contenido.contains(TEXTO_UNIDENTIFIED)) {
			posibleRegistrosMalMapeados = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!posibleRegistrosMalMapeados)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleRegistrosMalMapeados || linea == null || linea.isEmpty()) {
			return;
		}

		if (linea.contains(TEXTO_MISSING_ID)) {
			String entrada = extraerDespuesDeTexto(linea, TEXTO_MISSING_ID);

			if (esEntradaValida(entrada)) {
				registrarEntrada(consola, entrada, numero_de_linea);
			}
		}

		if (linea.contains(TEXTO_UNIDENTIFIED)) {
			procesarLineaUnidentified(consola, linea, numero_de_linea);
		}
	}

	/**
	 * Procesa:
	 *
	 * Unidentified mapping from registry minecraft:item:kubejs:100k_card: 35229
	 *
	 * y también bloques densos:
	 *
	 * kubejs:ender_pearl_block_1x: 24927kubejs:ender_pearl_block_2x: 24928
	 *
	 * Sin Pattern/Matcher.
	 */
	private void procesarLineaUnidentified(Consola consola, String linea, int numero_de_linea) {
		int inicio = linea.indexOf(TEXTO_UNIDENTIFIED);

		if (inicio < 0) {
			return;
		}

		int inicioDespuesRegistry = inicio + TEXTO_UNIDENTIFIED.length();

		// Saltar el nombre del registry hasta el primer ':'.
		int primerDosPuntos = linea.indexOf(':', inicioDespuesRegistry);

		if (primerDosPuntos < 0 || primerDosPuntos + 1 >= linea.length()) {
			return;
		}

		String bloque = linea.substring(primerDosPuntos + 1).trim();

		extraerEntradasDensas(consola, bloque, numero_de_linea);
	}

	/**
	 * Extrae entradas tipo namespace:path antes de ": numero".
	 *
	 * No usa regex y tolera varias entradas pegadas en una línea.
	 */
	private void extraerEntradasDensas(Consola consola, String texto, int numero_de_linea) {
		int i = 0;

		while (i < texto.length()) {
			int inicio = buscarInicioEntrada(texto, i);

			if (inicio < 0) {
				return;
			}

			int fin = buscarFinEntradaAntesDeNumero(texto, inicio);

			if (fin <= inicio) {
				i = inicio + 1;
				continue;
			}

			String entrada = texto.substring(inicio, fin).trim();

			if (esEntradaValida(entrada)) {
				registrarEntrada(consola, entrada, numero_de_linea);
			}

			i = fin + 1;
		}
	}

	/**
	 * Busca un posible inicio de entrada namespace:path.
	 */
	private int buscarInicioEntrada(String texto, int desde) {
		for (int i = desde; i < texto.length(); i++) {
			char c = texto.charAt(i);

			if (esCaracterEntrada(c)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Busca el final de una entrada antes del patrón ": numero".
	 */
	private int buscarFinEntradaAntesDeNumero(String texto, int inicio) {
		int i = inicio;
		int ultimoSeparadorNamespace = -1;

		while (i < texto.length()) {
			char c = texto.charAt(i);

			if (!esCaracterEntrada(c)) {
				break;
			}

			if (c == ':') {
				ultimoSeparadorNamespace = i;

				int posibleNumero = i + 1;

				while (posibleNumero < texto.length() && Character.isWhitespace(texto.charAt(posibleNumero))) {
					posibleNumero++;
				}

				if (posibleNumero < texto.length() && Character.isDigit(texto.charAt(posibleNumero))) {
					return i;
				}
			}

			i++;
		}

		// Fallback para "Found a missing id..." donde no hay ": numero".
		if (ultimoSeparadorNamespace > inicio) {
			return i;
		}

		return -1;
	}

	/**
	 * Extrae el primer token después de un texto fijo.
	 */
	private String extraerDespuesDeTexto(String linea, String texto) {
		int inicio = linea.indexOf(texto);

		if (inicio < 0) {
			return "";
		}

		inicio += texto.length();

		while (inicio < linea.length() && Character.isWhitespace(linea.charAt(inicio))) {
			inicio++;
		}

		int fin = inicio;

		while (fin < linea.length() && esCaracterEntrada(linea.charAt(fin))) {
			fin++;
		}

		if (fin <= inicio) {
			return "";
		}

		return linea.substring(inicio, fin).trim();
	}

	/**
	 * Acepta caracteres equivalentes al patrón usado antes: [\w\d_:-]
	 *
	 * En la práctica: letras, números, guion bajo, dos puntos y guion.
	 */
	private boolean esCaracterEntrada(char c) {
		return Character.isLetterOrDigit(c) || c == '_' || c == ':' || c == '-';
	}

	/**
	 * Evita timestamps y datos inválidos.
	 */
	private boolean esEntradaValida(String entrada) {
		if (entrada == null || entrada.isEmpty()) {
			return false;
		}

		if (!entrada.contains(":")) {
			return false;
		}

		if (contieneTimestamp(entrada)) {
			return false;
		}

		return true;
	}

	/**
	 * Reemplaza entrada.matches(".*\\d{2}:\\d{2}:\\d{2}.*").
	 */
	private boolean contieneTimestamp(String texto) {
		if (texto == null || texto.length() < 8) {
			return false;
		}

		for (int i = 0; i <= texto.length() - 8; i++) {
			if (Character.isDigit(texto.charAt(i)) && Character.isDigit(texto.charAt(i + 1))
					&& texto.charAt(i + 2) == ':' && Character.isDigit(texto.charAt(i + 3))
					&& Character.isDigit(texto.charAt(i + 4)) && texto.charAt(i + 5) == ':'
					&& Character.isDigit(texto.charAt(i + 6)) && Character.isDigit(texto.charAt(i + 7))) {
				return true;
			}
		}

		return false;
	}

	private void registrarEntrada(Consola consola, String entrada, int numero_de_linea) {
		if (!esEntradaValida(entrada)) {
			return;
		}

		if (entradas.add(entrada)) {
			enlacesPorEntrada.put(entrada, consola.agregarErrorALectador(numero_de_linea, this));
		}

		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new RegistrosMalMapeados();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f; // Alta prioridad
	}

	@Override
	public String mensaje() {
		if (entradas.isEmpty()) {
			return "";
		}

		StringBuilder html = new StringBuilder();

		html.append("<b style='color:#").append(Config.obtenerInstancia().obtenerColorError()).append("'>")
				.append(MonitorDePID.idioma.solucionRegistrosMalMapeados()).append("</b> ");

		html.append("<ul>");

		for (String entrada : entradas) {
			String enlace = enlacesPorEntrada.getOrDefault(entrada, "");

			html.append("<li>").append(entrada);

			if (enlace != null && !enlace.isEmpty()) {
				html.append(" ").append(enlace);
			}

			html.append("</li>");
		}

		html.append("</ul>");

		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_registros_mal_mapeados();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionRegistrosMalMapeados())
				.construir();
	}

	@Override
	public boolean anularNormal() {
		return true; // Normalmente el juego sale normal, pero falla al conectar a mundo/servidor.
	}

	@Override
	public String id() {
		return "registros_mal_mapeados";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		return trazo.trace.contains(TEXTO_MISSING_ID) || trazo.trace.contains(TEXTO_UNIDENTIFIED);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}