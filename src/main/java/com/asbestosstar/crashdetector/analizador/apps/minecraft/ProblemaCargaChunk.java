package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Locale;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores de carga de chunks procesando línea por línea.
 * 
 * Versión sin regex para evitar costo de Pattern/Matcher. Basado en Codex de
 * Aternos.
 */
public class ProblemaCargaChunk implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	/**
	 * Indica si vale la pena procesar línea por línea.
	 * 
	 * Se activa desde verificar(Consola consola) con un chequeo global barato.
	 */
	private boolean analizarLineas = false;

	/**
	 * Estado temporal:
	 * 
	 * Si se detectó "Encountered an unexpected exception", las siguientes líneas
	 * pueden contener la traza relevante.
	 */
	private boolean viUnexpectedException = false;

	@Override
	public void verificar(Consola consola) {

		// Chequeo global barato:
		// si el log completo no contiene señales básicas de este error,
		// no hace falta procesar línea por línea.
		if (consola == null || consola.contenido_verificar == null) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contieneAlguna(contenido, "ChunkRegionLoader", "Exception generating new chunk", "Couldn't load chunk")) {

			this.analizarLineas = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!analizarLineas) {
			return;
		}

		if (linea == null)
			return;

		String l = linea.trim();
		String lower = l.toLowerCase(Locale.ROOT);

		// Reiniciar estado si la línea no está relacionada
		if (!contieneAlguna(lower, "exception", "chunk", "load", "generate")) {
			viUnexpectedException = false;
			return;
		}

		// 1. Detectar "Encountered an unexpected exception"
		if (lower.contains("encountered an unexpected exception")) {
			viUnexpectedException = true;
			return;
		}

		// 2. Si ya vimos "unexpected exception", buscar trazas de ChunkRegionLoader
		if (viUnexpectedException) {

			if (esTrazaChunkRegionLoader(lower, "loadchunk") || esTrazaChunkRegionLoader(lower, "loadentities")) {

				activar(consola, numero_de_linea);
				return;
			}
		}

		// 3. Otros errores directos (no necesitan contexto previo)
		if (lower.contains("exception generating new chunk") || lower.contains("couldn't load chunk")) {

			activar(consola, numero_de_linea);
		}
	}

	/**
	 * Detecta líneas tipo:
	 * 
	 * at ...ChunkRegionLoader.loadChunk at ...ChunkRegionLoader.loadEntities
	 */
	private boolean esTrazaChunkRegionLoader(String lower, String metodo) {

		int at = lower.indexOf("at");
		if (at < 0) {
			return false;
		}

		int chunkRegionLoader = lower.indexOf("chunkregionloader.", at);
		if (chunkRegionLoader < 0) {
			return false;
		}

		int metodoPos = lower.indexOf(metodo, chunkRegionLoader);
		return metodoPos >= 0;
	}

	/**
	 * Devuelve true si el texto contiene cualquiera de las cadenas dadas.
	 */
	private boolean contieneAlguna(String texto, String... partes) {

		if (texto == null) {
			return false;
		}

		for (String parte : partes) {
			if (texto.contains(parte)) {
				return true;
			}
		}

		return false;
	}

	private void activar(Consola consola, int linea) {
		if (!activado) {
			this.activado = true;
			this.enlace = consola.agregarErrorALectador(linea, this);
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaCargaChunk();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000.0f;
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";

		return MonitorDePID.idioma.mensajeCargaChunk() + (enlace.isEmpty() ? "" : " " + enlace);
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaCargaChunk();
	}

	@Override
	public QuickFix solucion() {

		Builder builder = new Builder(nombre());

		builder.agregarEtiqueta(MonitorDePID.idioma.solucionRepararMundo("world"));

		builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarCarpetaMundo("world"));

		builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarChunk());

		return builder.construir();
	}

	@Override
	public String id() {
		return "carga_chunk";
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

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}