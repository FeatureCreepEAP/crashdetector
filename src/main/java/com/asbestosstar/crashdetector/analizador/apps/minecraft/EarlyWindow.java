package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el error EarlyWindow común en Forge, especialmente en macOS con
 * AppleMetalOpenGLRenderer.
 */
public class EarlyWindow implements Verificaciones {

	private static final String APPLE_METAL_OPENGL_RENDERER = "AppleMetalOpenGLRenderer";
	private static final String EARLY_FRAMEBUFFER_DRAW = "net.minecraftforge.fml.earlydisplay.EarlyFramebuffer.draw";

	private static final String MAIN_LINE = "Loading ImmediateWindowProvider fmlearlywindow";
	private static final String FALLBACK_1 = "Failed to initialize the mod loading system and display.";
	private static final String FALLBACK_2 = "Failed to initialize graphics window with current settings.";

	private boolean activado = false;
	private boolean limitacionOpenGLMacOSDetectada = false;
	private String mensaje = "";
	private String enlaceHtml = "";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty())
			return;

		String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);

		limitacionOpenGLMacOSDetectada = contieneFirmaMacOSOpenGL(consola.contenido_verificar);

		// Buscar última línea no vacía
		int indiceUltimaLinea = -1;
		for (int i = lineas.length - 1; i >= 0; i--) {
			if (!lineas[i].trim().isEmpty()) {
				indiceUltimaLinea = i;
				break;
			}
		}

		if (indiceUltimaLinea >= 0 && lineas[indiceUltimaLinea].contains(MAIN_LINE)) {
			mensaje = construirMensaje();
			enlaceHtml = consola.agregarErrorALectador(indiceUltimaLinea, this);
			activado = true;
			return;
		}

		// Activar fallback por contenido general
		if (consola.contenido_verificar.contains(FALLBACK_1) || consola.contenido_verificar.contains(FALLBACK_2)
				|| limitacionOpenGLMacOSDetectada) {
			mensaje = construirMensaje();
			activado = true;
		}
	}

	private boolean contieneFirmaMacOSOpenGL(String contenido) {
		return contenido != null && contenido.contains(APPLE_METAL_OPENGL_RENDERER)
				&& contenido.contains(EARLY_FRAMEBUFFER_DRAW);
	}

	private String construirMensaje() {
		String base = MonitorDePID.idioma.fmlEarlyWindow() + Verificaciones.nl_html;
		if (limitacionOpenGLMacOSDetectada) {
			return base + MonitorDePID.idioma.fmlEarlyWindowMacOSOpenGL();
		}
		return base;
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// No se utiliza; depende de contexto completo de log
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