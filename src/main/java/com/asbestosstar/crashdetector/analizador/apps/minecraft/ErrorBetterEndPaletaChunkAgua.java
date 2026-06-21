package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta crashes de paleta de chunk donde BetterEnd entra en el cálculo del
 * color del agua durante el renderizado de fluidos/chunks.
 *
 * Error típico: net.minecraft.world.chunk.EntryMissingException: Missing
 * Palette entry for index
 *
 * Camino típico: BiMapPalette.get PalettedContainer.get
 * BiomeColors.handler$cak000$betterend$be_getWaterColor Sodium FluidRenderer /
 * ChunkBuilderMeshingTask
 */
public class ErrorBetterEndPaletaChunkAgua implements Verificaciones {

	private boolean activado = false;

	private boolean vioPaletteEntry = false;
	private boolean vioBetterEndWaterColor = false;
	private boolean vioRenderChunkMeshes = false;
	private boolean vioSodiumFluido = false;

	private String enlace = "";

	private static final String MISSING_PALETTE_ENTRY = "Missing Palette entry";
	private static final String MISSING_PALETTE_ENTRY_FULL = "net.minecraft.world.chunk.EntryMissingException: Missing Palette entry";
	private static final String MISSING_PALETTE_ENTRY_INDEX = "Missing Palette entry for index";
	private static final String BETTEREND_WATER_COLOR = "betterend$be_getWaterColor";
	private static final String CHUNK_MESHES = "Description: Encountered exception while building chunk meshes";
	private static final String CHUNK_BUILDER_MESHING_TASK = "ChunkBuilderMeshingTask";
	private static final String FLUID_RENDERER_IMPL = "FluidRendererImpl";
	private static final String DEFAULT_FLUID_RENDERER = "DefaultFluidRenderer";
	private static final String SODIUM = "net.caffeinemc.mods.sodium";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MISSING_PALETTE_ENTRY, BETTEREND_WATER_COLOR, CHUNK_BUILDER_MESHING_TASK,
				FLUID_RENDERER_IMPL, DEFAULT_FLUID_RENDERER, SODIUM };
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

		if (linea == null || activado)
			return;

		if (linea.contains(MISSING_PALETTE_ENTRY_FULL) || linea.contains(MISSING_PALETTE_ENTRY_INDEX)) {
			vioPaletteEntry = true;
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains(CHUNK_MESHES) || linea.contains(CHUNK_BUILDER_MESHING_TASK)) {
			vioRenderChunkMeshes = true;
		}

		if (linea.contains(BETTEREND_WATER_COLOR)) {
			vioBetterEndWaterColor = true;
		}

		if (linea.contains(FLUID_RENDERER_IMPL) || linea.contains(DEFAULT_FLUID_RENDERER) || linea.contains(SODIUM)) {
			vioSodiumFluido = true;
		}

		if (vioPaletteEntry && vioBetterEndWaterColor && vioRenderChunkMeshes && vioSodiumFluido) {
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorBetterEndPaletaChunkAgua();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1450.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorBetterEndPaletaChunkAgua() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorBetterEndPaletaChunkAgua();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "error_betterend_paleta_chunk_agua";
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