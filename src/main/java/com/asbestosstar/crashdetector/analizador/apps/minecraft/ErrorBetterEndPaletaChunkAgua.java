package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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
	private boolean analizarLineas = false;

	private boolean vioPaletteEntry = false;
	private boolean vioBetterEndWaterColor = false;
	private boolean vioRenderChunkMeshes = false;
	private boolean vioSodiumFluido = false;

	private String enlace = "";

	@Override
	public void verificar(Consola consola) {

		String log = consola.contenido_verificar;

		if (log == null)
			return;

		if (log.contains("Missing Palette entry") && log.contains("betterend$be_getWaterColor")) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!analizarLineas)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (linea.contains("net.minecraft.world.chunk.EntryMissingException: Missing Palette entry")
				|| linea.contains("Missing Palette entry for index")) {
			vioPaletteEntry = true;
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
		}

		if (linea.contains("Description: Encountered exception while building chunk meshes")
				|| linea.contains("ChunkBuilderMeshingTask")) {
			vioRenderChunkMeshes = true;
		}

		if (linea.contains("betterend$be_getWaterColor")) {
			vioBetterEndWaterColor = true;
		}

		if (linea.contains("FluidRendererImpl") || linea.contains("DefaultFluidRenderer")
				|| linea.contains("net.caffeinemc.mods.sodium")) {
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