package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el error EarlyWindow común en Forge. MAIN_LINE solo activa si aparece
 * cerca del final del log. Los otros patrones se detectan globalmente.
 */
public class EarlyWindow implements Verificaciones {

	private static final String APPLE_METAL_OPENGL_RENDERER = "AppleMetalOpenGLRenderer";
	private static final String EARLY_FRAMEBUFFER_DRAW = "net.minecraftforge.fml.earlydisplay.EarlyFramebuffer.draw";

	private static final String MAIN_LINE = "Loading ImmediateWindowProvider fmlearlywindow";
	private static final String FALLBACK_1 = "Failed to initialize the mod loading system and display.";
	private static final String FALLBACK_2 = "Failed to initialize graphics window with current settings.";

	private boolean activado = false;
	private boolean posibleEarlyWindow = false;
	private boolean limitacionOpenGLMacOSDetectada = false;

	private String mensaje = "";
	private String enlaceHtml = "";

	// Solo buscar MAIN_LINE en las últimas N líneas
	private static final int LOOKBACK_CHARS = 20000;

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty())
			return;

		String contenido = consola.contenido_verificar;

		// Detectar señales globales baratas
		limitacionOpenGLMacOSDetectada = contenido.contains(APPLE_METAL_OPENGL_RENDERER)
				&& contenido.contains(EARLY_FRAMEBUFFER_DRAW);

		// Buscar MAIN_LINE solo en el final del log
		int start = Math.max(0, contenido.length() - LOOKBACK_CHARS);
		String ultimasLineas = contenido.substring(start);
		boolean mainLineFinal = ultimasLineas.contains(MAIN_LINE);

		// Otros patrones globales
		boolean fallbackGlobal = contenido.contains(FALLBACK_1) || contenido.contains(FALLBACK_2);

		// Activar detector si alguno se cumple
		posibleEarlyWindow = mainLineFinal || limitacionOpenGLMacOSDetectada || fallbackGlobal;
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (!posibleEarlyWindow || linea == null || linea.isEmpty() || activado)
			return;

		// Activar detector si línea contiene fallback (MAIN_LINE ya comprobado
		// globalmente)
		if (linea.contains(FALLBACK_1) || linea.contains(FALLBACK_2)) {
			mensaje = construirMensaje();
			activado = true;
		}
	}

	private String construirMensaje() {
		String base = MonitorDePID.idioma.fmlEarlyWindow() + Verificaciones.nl_html;
		if (limitacionOpenGLMacOSDetectada) {
			return base + MonitorDePID.idioma.fmlEarlyWindowMacOSOpenGL();
		}
		return base;
	}

	@Override
	public Verificaciones nueva() {
		return new EarlyWindow();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f;
	}

	@Override
	public String mensaje() {
		return activado ? mensaje + (enlaceHtml != null ? enlaceHtml : "") : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_early_window();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

	@Override
	public String id() {
		return "earlywindow";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null)
			return false;
		String t = trazo.trace;
		return t.contains(MAIN_LINE) || t.contains(FALLBACK_1) || t.contains(FALLBACK_2)
				|| t.contains(APPLE_METAL_OPENGL_RENDERER) || t.contains(EARLY_FRAMEBUFFER_DRAW);
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