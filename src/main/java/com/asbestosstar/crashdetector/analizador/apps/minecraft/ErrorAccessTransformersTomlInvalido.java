package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Locale;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta InvalidModFileException de Forge causado por una estructura TOML
 * inválida en accessTransformers.
 *
 * Puede llegar en una sola línea o dividido en varias líneas cercanas:
 *
 * InvalidModFileException The configuration path [accessTransformers] is
 * invalid I wasn't expecting a multi-object list - remove one of the [[ ]]
 * (/nombre-del-mod.jar)
 */
public class ErrorAccessTransformersTomlInvalido implements Verificaciones {

	private static final String INVALID_MOD_FILE_EXCEPTION = "InvalidModFileException";
	private static final String RUTA_ACCESS_TRANSFORMERS = "The configuration path [accessTransformers] is invalid";
	private static final String MULTI_OBJECT_LIST = "multi-object list";
	private static final String QUITAR_DOBLE_CORCHETE = "remove one of the [[ ]]";

	/*
	 * Las partes del mismo error deben aparecer cerca. Así evitamos combinar
	 * mensajes independientes que estén muy separados dentro de un log grande.
	 */
	private static final int MAX_DISTANCIA_LINEAS = 8;

	private boolean activado = false;
	private String nombreJar = "";
	private String enlaceHtml = "";

	private int lineaInvalidModFile = -1;
	private int lineaAccessTransformers = -1;
	private int lineaMultiObject = -1;
	private int lineaJar = -1;

	@Override
	public String[] patronesRapidos() {
		return new String[] { INVALID_MOD_FILE_EXCEPTION, RUTA_ACCESS_TRANSFORMERS, MULTI_OBJECT_LIST,
				QUITAR_DOBLE_CORCHETE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		if (activado || linea == null || linea.isEmpty()) {
			return;
		}

		if (linea.contains(INVALID_MOD_FILE_EXCEPTION)) {
			lineaInvalidModFile = numeroDeLinea;
		}

		if (linea.contains(RUTA_ACCESS_TRANSFORMERS)) {
			lineaAccessTransformers = numeroDeLinea;
		}

		if (linea.contains(MULTI_OBJECT_LIST) || linea.contains(QUITAR_DOBLE_CORCHETE)) {
			lineaMultiObject = numeroDeLinea;
		}

		String jar = extraerNombreJar(linea);
		if (!jar.isEmpty()) {
			nombreJar = jar;
			lineaJar = numeroDeLinea;
		}

		int lineaPrincipal = calcularLineaPrincipalSiCompleto();
		if (lineaPrincipal < 0) {
			return;
		}

		if (nombreJar.isEmpty()) {
			nombreJar = buscarJarCerca(consola, lineaPrincipal);
		}

		if (consola != null) {
			enlaceHtml = consola.agregarErrorALectador(lineaPrincipal, this);
			if (enlaceHtml == null) {
				enlaceHtml = "";
			}
		}

		activado = true;
	}

	private int calcularLineaPrincipalSiCompleto() {
		if (lineaInvalidModFile < 0 || lineaAccessTransformers < 0 || lineaMultiObject < 0) {
			return -1;
		}

		int minimo = Math.min(lineaInvalidModFile, Math.min(lineaAccessTransformers, lineaMultiObject));
		int maximo = Math.max(lineaInvalidModFile, Math.max(lineaAccessTransformers, lineaMultiObject));

		if (maximo - minimo > MAX_DISTANCIA_LINEAS) {
			/*
			 * Descartar estado demasiado viejo y conservar únicamente la señal más reciente
			 * para permitir detectar otro bloque más adelante.
			 */
			int masReciente = maximo;

			if (lineaInvalidModFile != masReciente) {
				lineaInvalidModFile = -1;
			}
			if (lineaAccessTransformers != masReciente) {
				lineaAccessTransformers = -1;
			}
			if (lineaMultiObject != masReciente) {
				lineaMultiObject = -1;
			}

			return -1;
		}

		return lineaAccessTransformers;
	}

	/**
	 * Si el nombre del JAR quedó en una línea separada que no produjo una
	 * coincidencia del autómata, buscarlo alrededor del error usando
	 * lineas_verificar ya disponibles.
	 */
	private String buscarJarCerca(Consola consola, int centro) {
		if (consola == null || consola.lineas_verificar == null || consola.lineas_verificar.length == 0) {
			return "";
		}

		int inicio = Math.max(0, centro - MAX_DISTANCIA_LINEAS);
		int fin = Math.min(consola.lineas_verificar.length - 1, centro + MAX_DISTANCIA_LINEAS);

		for (int i = inicio; i <= fin; i++) {
			String jar = extraerNombreJar(consola.lineas_verificar[i]);
			if (!jar.isEmpty()) {
				lineaJar = i;
				return jar;
			}
		}

		return "";
	}

	private static String extraerNombreJar(String linea) {
		if (linea == null || linea.isEmpty()) {
			return "";
		}

		String minusculas = linea.toLowerCase(Locale.ROOT);
		int finJar = minusculas.lastIndexOf(".jar");

		if (finJar < 0) {
			return "";
		}

		int fin = finJar + 4;
		int inicio = finJar - 1;

		while (inicio >= 0) {
			char c = linea.charAt(inicio);

			if (Character.isWhitespace(c) || c == '(' || c == '[' || c == '{' || c == '\'' || c == '"') {
				break;
			}

			inicio--;
		}

		String candidato = linea.substring(inicio + 1, fin).trim();
		int barra = Math.max(candidato.lastIndexOf('/'), candidato.lastIndexOf('\\'));

		if (barra >= 0 && barra + 1 < candidato.length()) {
			candidato = candidato.substring(barra + 1);
		}

		return candidato.toLowerCase(Locale.ROOT).endsWith(".jar") ? candidato : "";
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorAccessTransformersTomlInvalido();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 909.0f;
	}

	@Override
	public String mensaje() {
		if (!activado) {
			return "";
		}

		return MonitorDePID.idioma.errorAccessTransformersTomlInvalido(nombreJar) + Verificaciones.nl_html + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_access_transformers_toml_invalido();
	}

	@Override
	public QuickFix solucion() {
		if (!activado) {
			return QuickFix.NINGUN;
		}

		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_access_transformers_toml_invalido(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_access_transformers_toml_invalido(nombreJar))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_access_transformers_toml_invalido()).construir();
	}

	@Override
	public String id() {
		return "access_transformers_toml_invalido";
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[] { RUTA_ACCESS_TRANSFORMERS, MULTI_OBJECT_LIST };
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
