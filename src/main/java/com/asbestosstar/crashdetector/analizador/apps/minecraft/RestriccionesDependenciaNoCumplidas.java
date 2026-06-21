package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Detecta errores de restricciones de dependencias (JarInJar) en Forge.
 * 
 * Agrupa los conflictos por Archivo JAR para mostrar qué mods solicitan qué
 * dependencias internas.
 *
 * Ejemplo: net.minecraftforge.fml.loading.EarlyLoadingException: 3 Dependency
 * restrictions were not met.
 */
public class RestriccionesDependenciaNoCumplidas implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";
	private String cantidad = "varias";

	// Mapa: Nombre del Archivo -> Lista de dependencias solicitadas
	private Map<String, List<String>> conflictosPorMod = new HashMap<>();

	private static final String EARLY_LOADING_EXCEPTION = "EarlyLoadingException";
	private static final String DEPENDENCY_RESTRICTIONS = "Dependency restrictions were not met";
	private static final String FAILED_TO_SELECT_JARS = "Failed to select jars";
	private static final String RESOLUTION_FAILURE = "ResolutionFailureInformation";
	private static final String RESOLUTION_FAILURE_BLOQUE = "ResolutionFailureInformation{";
	private static final String ARTIFACT = "artifact=";
	private static final String MOD_FILE = "Mod File:";

	@Override
	public String[] patronesRapidos() {
		return new String[] { EARLY_LOADING_EXCEPTION, DEPENDENCY_RESTRICTIONS, FAILED_TO_SELECT_JARS,
				RESOLUTION_FAILURE, ARTIFACT, MOD_FILE };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		String linea = evento.linea;

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Línea principal con la cantidad.
		if (linea.contains(EARLY_LOADING_EXCEPTION) && linea.contains(DEPENDENCY_RESTRICTIONS)) {
			extraerCantidadDesdeLinea(linea);

			if (this.enlace == null || this.enlace.isEmpty()) {
				this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			}

			this.activado = true;
			return;
		}

		// Línea detallada donde vienen los ResolutionFailureInformation.
		if (linea.contains(RESOLUTION_FAILURE_BLOQUE) && linea.contains(ARTIFACT) && linea.contains(MOD_FILE)) {
			parsearConflictosAgrupados(linea);
		}
	}

	private void extraerCantidadDesdeLinea(String linea) {
		int dosPuntos = linea.indexOf(": ");
		if (dosPuntos == -1) {
			return;
		}

		int inicio = dosPuntos + 2;
		int fin = linea.indexOf(' ', inicio);

		if (fin == -1) {
			fin = linea.length();
		}

		if (inicio < fin) {
			this.cantidad = linea.substring(inicio, fin).trim();
		}
	}

	private void parsearConflictosAgrupados(String texto) {
		if (texto == null || texto.isEmpty()) {
			return;
		}

		int pos = 0;

		while (true) {
			int inicioBloque = texto.indexOf(RESOLUTION_FAILURE_BLOQUE, pos);
			if (inicioBloque == -1) {
				break;
			}

			int siguienteBloque = texto.indexOf(RESOLUTION_FAILURE_BLOQUE,
					inicioBloque + RESOLUTION_FAILURE_BLOQUE.length());
			int finBloque = siguienteBloque == -1 ? texto.length() : siguienteBloque;

			parsearBloque(texto, inicioBloque, finBloque);

			pos = finBloque;
		}
	}

	private void parsearBloque(String texto, int inicioBloque, int finBloque) {
		int idxArt = texto.indexOf(ARTIFACT, inicioBloque);
		if (idxArt == -1 || idxArt >= finBloque) {
			return;
		}

		String dependencia = extraerValorHastaSeparador(texto, idxArt + ARTIFACT.length(), finBloque);

		if (dependencia == null || dependencia.isEmpty()) {
			dependencia = "Desconocida";
		}

		int pos = inicioBloque;

		while (true) {
			int idxSource = texto.indexOf(MOD_FILE, pos);
			if (idxSource == -1 || idxSource >= finBloque) {
				break;
			}

			String archivo = extraerValorHastaSeparador(texto, idxSource + MOD_FILE.length(), finBloque);

			if (archivo == null || archivo.isEmpty()) {
				archivo = "Desconocido";
			}

			archivo = limpiarNombreArchivo(archivo);

			if (!archivo.isEmpty()) {
				List<String> deps = conflictosPorMod.get(archivo);

				if (deps == null) {
					deps = new ArrayList<>();
					conflictosPorMod.put(archivo, deps);
				}

				if (!deps.contains(dependencia)) {
					deps.add(dependencia);
				}
			}

			pos = idxSource + MOD_FILE.length();
		}
	}

	private String extraerValorHastaSeparador(String texto, int inicio, int limite) {
		if (inicio < 0 || inicio >= texto.length()) {
			return "";
		}

		if (limite > texto.length()) {
			limite = texto.length();
		}

		int finCorchete = texto.indexOf(']', inicio);
		int finComa = texto.indexOf(',', inicio);

		int fin = limite;

		if (finCorchete != -1 && finCorchete < fin) {
			fin = finCorchete;
		}

		if (finComa != -1 && finComa < fin) {
			fin = finComa;
		}

		if (fin <= inicio) {
			return "";
		}

		return texto.substring(inicio, fin).trim();
	}

	private String limpiarNombreArchivo(String archivo) {
		if (archivo == null || archivo.isEmpty()) {
			return "";
		}

		int barraWindows = archivo.lastIndexOf('\\');
		int barraUnix = archivo.lastIndexOf('/');
		int barra = Math.max(barraWindows, barraUnix);

		if (barra != -1 && barra + 1 < archivo.length()) {
			return archivo.substring(barra + 1).trim();
		}

		return archivo.trim();
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new RestriccionesDependenciaNoCumplidas();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1200.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeRestriccionesDependenciaNoCumplidas(cantidad, conflictosPorMod) + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreRestriccionesDependenciaNoCumplidas();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "restricciones_dependencia_no_cumplidas";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}