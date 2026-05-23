package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Clase que detecta mods incompatibles en Fabric. Gracias a Aternos porque esta
 * es una implementacion de su codex:
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaModIncompatibleFabric implements Verificaciones {

	private boolean posibleModIncompatibleFabric = false;
	private boolean activado = false;

	private String mensaje = "";
	private String primerMod = "";
	private String segundoMod = "";
	private String enlace = "";

	private static final String TEXTO_MOD = "Mod ";
	private static final String TEXTO_INCOMPATIBLE = " is incompatible";
	private static final String TEXTO_CONFLICTS = " conflicts";
	private static final String TEXTO_WITH = " with ";
	private static final String TEXTO_OF = " of ";
	private static final String TEXTO_OF_MOD = " of mod ";

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

		if (contenido.contains("- Mod ") && (contenido.contains(" is incompatible") || contenido.contains(" conflicts"))
				&& contenido.contains(" with ") && contenido.contains(" of ")) {
			posibleModIncompatibleFabric = true;
		}
	}

	/**
	 * Verificacion por linea.
	 *
	 * Aquí se detectan los mods exactos y se guarda el enlace a la linea.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleModIncompatibleFabric || activado || linea == null || linea.isEmpty()) {
			return;
		}

		DatosModsIncompatibles datos = extraerModsIncompatiblesDeLinea(linea);

		if (datos != null) {
			this.primerMod = datos.primerMod;
			this.segundoMod = datos.segundoMod;
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			this.mensaje = MonitorDePID.idioma.mensajeModIncompatible(primerMod, segundoMod) + Verificaciones.nl_html
					+ enlace;

			activado = true;
		}
	}

	/**
	 * Busca una linea tipo:
	 *
	 * - Mod primer_mod ... is incompatible with ... of segundo_mod - Mod primer_mod
	 * ... conflicts with ... of mod segundo_mod
	 *
	 * Sin Pattern/Matcher.
	 */
	private DatosModsIncompatibles extraerModsIncompatiblesDeLinea(String linea) {
		int inicioMod = indexOfModDespuesDeGuion(linea, 0);

		if (inicioMod < 0) {
			return null;
		}

		int inicioPrimerMod = inicioMod + TEXTO_MOD.length();
		int finPrimerMod = leerFinModId(linea, inicioPrimerMod);

		if (finPrimerMod <= inicioPrimerMod) {
			return null;
		}

		String candidatoPrimerMod = linea.substring(inicioPrimerMod, finPrimerMod).trim();

		int indiceIncompatible = indexOfIgnoreCase(linea, TEXTO_INCOMPATIBLE, finPrimerMod);
		int indiceConflicts = indexOfIgnoreCase(linea, TEXTO_CONFLICTS, finPrimerMod);

		int indiceTipoError = elegirIndiceMasTemprano(indiceIncompatible, indiceConflicts);

		if (indiceTipoError < 0) {
			return null;
		}

		int indiceWith = indexOfIgnoreCase(linea, TEXTO_WITH, indiceTipoError);

		if (indiceWith < 0) {
			return null;
		}

		String candidatoSegundoMod = extraerSegundoMod(linea, indiceWith + TEXTO_WITH.length());

		if (esModIdValido(candidatoPrimerMod) && esModIdValido(candidatoSegundoMod)) {
			return new DatosModsIncompatibles(candidatoPrimerMod, candidatoSegundoMod);
		}

		return null;
	}

	/**
	 * Encuentra el texto "Mod " que aparece después de un guion de lista.
	 *
	 * Acepta: - Mod example ... - Mod example ...
	 */
	private int indexOfModDespuesDeGuion(String texto, int desde) {
		int indice = desde;

		while (indice < texto.length()) {
			int guion = texto.indexOf('-', indice);

			if (guion < 0) {
				return -1;
			}

			int i = guion + 1;

			while (i < texto.length() && Character.isWhitespace(texto.charAt(i))) {
				i++;
			}

			if (empiezaConIgnoreCase(texto, i, TEXTO_MOD)) {
				return i;
			}

			indice = guion + 1;
		}

		return -1;
	}

	/**
	 * Extrae el segundo mod después de "with ... of segundo_mod" o "with ... of mod
	 * segundo_mod".
	 */
	private String extraerSegundoMod(String texto, int desde) {
		int indiceOfMod = indexOfIgnoreCase(texto, TEXTO_OF_MOD, desde);
		int indiceOf = indexOfIgnoreCase(texto, TEXTO_OF, desde);

		int inicioSegundoMod;

		if (indiceOfMod >= 0) {
			inicioSegundoMod = indiceOfMod + TEXTO_OF_MOD.length();
		} else if (indiceOf >= 0) {
			inicioSegundoMod = indiceOf + TEXTO_OF.length();
		} else {
			return null;
		}

		while (inicioSegundoMod < texto.length() && Character.isWhitespace(texto.charAt(inicioSegundoMod))) {
			inicioSegundoMod++;
		}

		int finSegundoMod = leerFinModId(texto, inicioSegundoMod);

		if (finSegundoMod <= inicioSegundoMod) {
			return null;
		}

		return texto.substring(inicioSegundoMod, finSegundoMod).trim();
	}

	/**
	 * Lee el final de un modid.
	 *
	 * El patron original aceptaba: letras, numeros, guion bajo, guion, punto.
	 */
	private int leerFinModId(String texto, int inicio) {
		int i = inicio;

		while (i < texto.length()) {
			char c = texto.charAt(i);

			if (esCaracterModId(c)) {
				i++;
			} else {
				break;
			}
		}

		return i;
	}

	private boolean esModIdValido(String modId) {
		if (modId == null || modId.isEmpty()) {
			return false;
		}

		for (int i = 0; i < modId.length(); i++) {
			if (!esCaracterModId(modId.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	private boolean esCaracterModId(char c) {
		return Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.';
	}

	private int elegirIndiceMasTemprano(int a, int b) {
		if (a < 0) {
			return b;
		}

		if (b < 0) {
			return a;
		}

		return Math.min(a, b);
	}

	private boolean empiezaConIgnoreCase(String texto, int inicio, String buscar) {
		if (texto == null || buscar == null) {
			return false;
		}

		if (inicio < 0 || inicio + buscar.length() > texto.length()) {
			return false;
		}

		return texto.regionMatches(true, inicio, buscar, 0, buscar.length());
	}

	private int indexOfIgnoreCase(String texto, String buscar, int desde) {
		if (texto == null || buscar == null) {
			return -1;
		}

		if (desde < 0) {
			desde = 0;
		}

		int largoTexto = texto.length();
		int largoBuscar = buscar.length();

		if (largoBuscar == 0) {
			return desde;
		}

		if (largoBuscar > largoTexto || desde >= largoTexto) {
			return -1;
		}

		int limite = largoTexto - largoBuscar;

		for (int i = desde; i <= limite; i++) {
			if (texto.regionMatches(true, i, buscar, 0, largoBuscar)) {
				return i;
			}
		}

		return -1;
	}

	private static class DatosModsIncompatibles {
		private final String primerMod;
		private final String segundoMod;

		private DatosModsIncompatibles(String primerMod, String segundoMod) {
			this.primerMod = primerMod;
			this.segundoMod = segundoMod;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaModIncompatibleFabric();
	}

	/**
	 * Indica si el problema fue detectado.
	 */
	@Override
	public boolean activado() {
		return activado;
	}

	/**
	 * Prioridad del problema.
	 */
	@Override
	public float prioridad() {
		return 900.0f;
	}

	/**
	 * Devuelve el mensaje de error almacenado.
	 */
	@Override
	public String mensaje() {
		return mensaje;
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaModIncompatibleFabric();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		return new Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMod(primerMod))
				.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMod(segundoMod)).construir();
	}

	@Override
	public String id() {
		return "mod_incompatible_fabric";
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
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}