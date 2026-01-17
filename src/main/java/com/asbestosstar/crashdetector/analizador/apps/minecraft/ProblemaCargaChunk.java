package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores de carga de chunks procesando línea por línea. Evita regex
 * costosos con DOTALL. Basado en Codex de Aternos.
 */
public class ProblemaCargaChunk implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	// Patrones por línea (sin DOTALL, más rápidos)
	private static final Pattern PATRON_LOAD_CHUNK = Pattern.compile("at.*ChunkRegionLoader\\.loadChunk",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern PATRON_LOAD_ENTITIES = Pattern.compile("at.*ChunkRegionLoader\\.loadEntities",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern PATRON_GEN_CHUNK = Pattern.compile("Exception generating new chunk",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern PATRON_CANT_LOAD_CHUNK = Pattern.compile("Couldn't load chunk",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern PATRON_UNEXPECTED_EXCEPTION = Pattern.compile("Encountered an unexpected exception",
			Pattern.CASE_INSENSITIVE);

	private boolean viUnexpectedException = false;

	@Override
	public void verificar(Consola consola) {
		this.activado = false;
		this.enlace = "";
		this.viUnexpectedException = false;
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (linea == null)
			return;

		String l = linea.trim();

		// Reiniciar estado si la línea no está relacionada
		if (!l.contains("exception") && !l.contains("chunk") && !l.contains("load") && !l.contains("generate")) {
			viUnexpectedException = false;
			return;
		}

		// 1. Detectar "Encountered an unexpected exception"
		if (PATRON_UNEXPECTED_EXCEPTION.matcher(l).find()) {
			viUnexpectedException = true;
			return;
		}

		// 2. Si ya vimos "unexpected exception", buscar trazas de ChunkRegionLoader
		if (viUnexpectedException) {
			if (PATRON_LOAD_CHUNK.matcher(l).find() || PATRON_LOAD_ENTITIES.matcher(l).find()) {
				activar(consola, numero_de_linea);
				return;
			}
		}

		// 3. Otros errores directos (no necesitan contexto previo)
		if (PATRON_GEN_CHUNK.matcher(l).find() || PATRON_CANT_LOAD_CHUNK.matcher(l).find()) {
			activar(consola, numero_de_linea);
		}
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
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}