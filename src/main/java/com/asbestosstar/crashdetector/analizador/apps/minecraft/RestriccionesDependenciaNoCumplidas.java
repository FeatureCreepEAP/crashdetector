package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String log = consola.contenido_verificar;

		// Global check barato: solo activa si están las piezas necesarias.
		if (log.contains("EarlyLoadingException") && log.contains("Dependency restrictions were not met")
				&& (log.contains("Failed to select jars") || log.contains("ResolutionFailureInformation"))) {
			activado = true;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!activado || consola == null || linea == null) {
			return;
		}

		// Línea principal con la cantidad.
		if (linea.contains("EarlyLoadingException") && linea.contains("Dependency restrictions were not met")) {
			extraerCantidadDesdeLinea(linea);

			if (this.enlace == null || this.enlace.isEmpty()) {
				this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			}

			return;
		}

		// Línea detallada donde vienen los ResolutionFailureInformation.
		if (linea.contains("ResolutionFailureInformation{") && linea.contains("artifact=")
				&& linea.contains("Mod File:")) {
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
			int inicioBloque = texto.indexOf("ResolutionFailureInformation{", pos);
			if (inicioBloque == -1) {
				break;
			}

			int siguienteBloque = texto.indexOf("ResolutionFailureInformation{", inicioBloque + 29);
			int finBloque = siguienteBloque == -1 ? texto.length() : siguienteBloque;

			parsearBloque(texto, inicioBloque, finBloque);

			pos = finBloque;
		}
	}

	private void parsearBloque(String texto, int inicioBloque, int finBloque) {
		int idxArt = texto.indexOf("artifact=", inicioBloque);
		if (idxArt == -1 || idxArt >= finBloque) {
			return;
		}

		String dependencia = extraerValorHastaSeparador(texto, idxArt + 9, finBloque);

		if (dependencia == null || dependencia.isEmpty()) {
			dependencia = "Desconocida";
		}

		int pos = inicioBloque;

		while (true) {
			int idxSource = texto.indexOf("Mod File:", pos);
			if (idxSource == -1 || idxSource >= finBloque) {
				break;
			}

			String archivo = extraerValorHastaSeparador(texto, idxSource + 9, finBloque);

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

			pos = idxSource + 9;
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
	public Verificaciones nueva() {
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

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}