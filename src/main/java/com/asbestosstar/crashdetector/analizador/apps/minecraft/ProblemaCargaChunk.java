package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.Locale;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

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
	 * Estado temporal:
	 * 
	 * Si se detectó "Encountered an unexpected exception", las siguientes líneas
	 * pueden contener la traza relevante.
	 */
	private boolean viUnexpectedException = false;

	private static final String CHUNK_REGION_LOADER = "ChunkRegionLoader";
	private static final String EXCEPTION_GENERATING_NEW_CHUNK = "Exception generating new chunk";
	private static final String COULDNT_LOAD_CHUNK = "Couldn't load chunk";
	private static final String UNEXPECTED_EXCEPTION = "Encountered an unexpected exception";

	private static final String LOWER_EXCEPTION = "exception";
	private static final String LOWER_CHUNK = "chunk";
	private static final String LOWER_LOAD = "load";
	private static final String LOWER_GENERATE = "generate";
	private static final String LOWER_UNEXPECTED_EXCEPTION = "encountered an unexpected exception";
	private static final String LOWER_EXCEPTION_GENERATING_NEW_CHUNK = "exception generating new chunk";
	private static final String LOWER_COULDNT_LOAD_CHUNK = "couldn't load chunk";
	private static final String LOWER_LOAD_CHUNK = "loadchunk";
	private static final String LOWER_LOAD_ENTITIES = "loadentities";

	@Override
	public String[] patronesRapidos() {
		return new String[] { CHUNK_REGION_LOADER, EXCEPTION_GENERATING_NEW_CHUNK, COULDNT_LOAD_CHUNK,
				UNEXPECTED_EXCEPTION };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		String lower = linea.toLowerCase(Locale.ROOT);

		// Reiniciar estado si la línea no está relacionada
		if (!contieneAlguna(lower, LOWER_EXCEPTION, LOWER_CHUNK, LOWER_LOAD, LOWER_GENERATE)) {
			viUnexpectedException = false;
			return;
		}

		// 1. Detectar "Encountered an unexpected exception"
		if (lower.contains(LOWER_UNEXPECTED_EXCEPTION)) {
			viUnexpectedException = true;
			return;
		}

		// 2. Si ya vimos "unexpected exception", buscar trazas de ChunkRegionLoader
		if (viUnexpectedException) {

			if (esTrazaChunkRegionLoader(lower, LOWER_LOAD_CHUNK)
					|| esTrazaChunkRegionLoader(lower, LOWER_LOAD_ENTITIES)) {

				activar(consola, numero_de_linea);
				return;
			}
		}

		// 3. Otros errores directos (no necesitan contexto previo)
		if (lower.contains(LOWER_EXCEPTION_GENERATING_NEW_CHUNK) || lower.contains(LOWER_COULDNT_LOAD_CHUNK)) {
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
	public boolean recomendadoParaCorperata() {
		return true;
	}
}