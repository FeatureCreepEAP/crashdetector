package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class EarlyWindow implements Verificaciones {

	private static final String APPLE_METAL_OPENGL_RENDERER = "AppleMetalOpenGLRenderer";
	private static final String EARLY_FRAMEBUFFER_DRAW = "net.minecraftforge.fml.earlydisplay.EarlyFramebuffer.draw";

	private static final String MAIN_LINE = "Loading ImmediateWindowProvider fmlearlywindow";
	private static final String FALLBACK_1 = "Failed to initialize the mod loading system and display.";
	private static final String FALLBACK_2 = "Failed to initialize graphics window with current settings.";
	public static final String wayland_26 = "The platform does not provide the window position";

	private boolean activado = false;
	private boolean limitacionOpenGLMacOSDetectada = false;

	private boolean appleMetalDetectado = false;
	private boolean earlyFramebufferDetectado = false;

	private int lineaMainLine = -1;
	private int ultimaLineaSignificativa = -1;

	private String mensaje = "";
	private String enlaceHtml = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { APPLE_METAL_OPENGL_RENDERER, EARLY_FRAMEBUFFER_DRAW, MAIN_LINE, FALLBACK_1, FALLBACK_2,
				wayland_26 };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		procesarLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		procesarLinea(consola, linea, numero_de_linea);
	}

	private void procesarLinea(Consola consola, String linea, int numeroDeLinea) {
		if (linea == null || linea.trim().isEmpty()) {
			return;
		}

		ultimaLineaSignificativa = numeroDeLinea;

		if (linea.contains(APPLE_METAL_OPENGL_RENDERER)) {
			appleMetalDetectado = true;
		}

		if (linea.contains(EARLY_FRAMEBUFFER_DRAW)) {
			earlyFramebufferDetectado = true;
		}

		limitacionOpenGLMacOSDetectada = appleMetalDetectado && earlyFramebufferDetectado;

		if (linea.contains(MAIN_LINE)) {
			lineaMainLine = numeroDeLinea;
		}

		if (activado) {
			return;
		}

		if (lineaContieneFallback(linea)) {
			activar(consola, numeroDeLinea);
			return;
		}

		if (limitacionOpenGLMacOSDetectada) {
			activar(consola, numeroDeLinea);
		}
	}

	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		if (activado) {
			return;
		}

		if (lineaMainLine >= 0 && lineaMainLine == ultimaLineaSignificativa) {
			activar(consola, lineaMainLine);
		}
	}

	private boolean lineaContieneFallback(String linea) {
		return linea.contains(FALLBACK_1) || linea.contains(FALLBACK_2) || linea.contains(wayland_26);
	}

	private void activar(Consola consola, int numeroDeLinea) {
		if (activado) {
			return;
		}

		mensaje = construirMensaje();

		if (consola != null && numeroDeLinea >= 0) {
			enlaceHtml = consola.agregarErrorALectador(numeroDeLinea, this);
		}

		activado = true;
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
	public String[] ocupaTrazo() {
		return new String[] { MAIN_LINE };
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}
}