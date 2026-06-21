package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EstadoAnalisisArchivo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
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
	public static final String wayland_26 = "The platform does not provide the window position";// Comun en Wayland en
																								// versiones 26+

	private boolean activado = false;
	private boolean limitacionOpenGLMacOSDetectada = false;

	private boolean appleMetalDetectado = false;
	private boolean earlyFramebufferDetectado = false;
	private int lineaMainLine = -1;

	private String mensaje = "";
	private String enlaceHtml = "";

	// Solo buscar MAIN_LINE en las últimas N líneas
	private static final int LOOKBACK_CHARS = 20000;

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

		String linea = evento.linea;

		// Detectar señales globales baratas
		if (linea.contains(APPLE_METAL_OPENGL_RENDERER)) {
			appleMetalDetectado = true;
		}

		if (linea.contains(EARLY_FRAMEBUFFER_DRAW)) {
			earlyFramebufferDetectado = true;
		}

		limitacionOpenGLMacOSDetectada = appleMetalDetectado && earlyFramebufferDetectado;

		// Buscar MAIN_LINE solo en el final del log
		// En modo streaming no tenemos contenido completo; se guarda la última línea
		// encontrada y se confirma al finalizar el archivo.
		if (linea.contains(MAIN_LINE)) {
			lineaMainLine = evento.numeroDeLinea;
		}

		if (lineaContieneFallback(linea) || limitacionOpenGLMacOSDetectada) {
		}

		verificarPorLinea(evento.consola, linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (linea == null || linea.isEmpty() || activado)
			return;

		// Activar detector si línea contiene fallback (MAIN_LINE ya comprobado
		// globalmente)
		if (lineaContieneFallback(linea)) {
			activar(consola, numero_de_linea);
			return;
		}

		if (limitacionOpenGLMacOSDetectada) {
			activar(consola, numero_de_linea);
		}
	}

	@Override
	public void finalizarArchivo(Consola consola, EstadoAnalisisArchivo estado) {
		if (activado) {
			return;
		}

		// MAIN_LINE se confirma al final para conservar la intención original:
		// solo interesa cuando aparece cerca del cierre del log.
		if (lineaMainLine >= 0) {
			activar(consola, lineaMainLine);
		}
	}

	private boolean lineaContieneFallback(String linea) {
		return linea.contains(FALLBACK_1) || linea.contains(FALLBACK_2) || linea.contains(wayland_26);
	}

	private void activar(Consola consola, int numero_de_linea) {
		if (activado) {
			return;
		}

		mensaje = construirMensaje();

		if (consola != null && numero_de_linea >= 0) {
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
		}

		activado = true;
	}

	private String construirMensaje() {
		String base = MonitorDePID.idioma.fmlEarlyWindow() + VerificacionesLegacy.nl_html;
		if (limitacionOpenGLMacOSDetectada) {
			return base + MonitorDePID.idioma.fmlEarlyWindowMacOSOpenGL();
		}
		return base;
	}

	@Override
	public VerificacionesLegacy nueva() {
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

}