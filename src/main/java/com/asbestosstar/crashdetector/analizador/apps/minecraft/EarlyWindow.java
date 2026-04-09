package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Este error es común en Minecraft Forge cuando los registros terminan en
 * "Loading ImmediateWindowProvider fmlearlywindow", especialmente debido a
 * controladores (drivers) defectuosos, aunque también puede deberse a
 * conflictos entre mods. Puedes resolverlo editando el valor de
 * earlyWindowProvider a "none" en el archivo config/fml.toml.
 */
public class EarlyWindow implements Verificaciones {

	private static final String APPLE_METAL_OPENGL_RENDERER = "AppleMetalOpenGLRenderer";
	private static final String EARLY_FRAMEBUFFER_DRAW = "net.minecraftforge.fml.earlydisplay.EarlyFramebuffer.draw";

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean limitacionOpenGLMacOSDetectada = false;

	// TODO Wayland: The platform does not provide the window position

	/**
	 * Verificación basada en el contenido completo de la consola.
	 * <p>
	 * Se busca la última línea no vacía y se comprueba si contiene "Loading
	 * ImmediateWindowProvider fmlearlywindow". Si no, se usa un patrón alternativo
	 * más genérico: "Failed to initialize the mod loading system and display.".
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;
		String[] lineas = contenidoConsola.split(Verificaciones.nl);
		limitacionOpenGLMacOSDetectada = contieneFirmaMacOSOpenGL(contenidoConsola);

		if (lineas.length == 0)
			return;

		// Buscar la última línea no vacía
		String ultimaLinea = null;
		int indiceUltimaLinea = -1;

		for (int i = lineas.length - 1; i >= 0; i--) {
			String linea = lineas[i].trim();
			if (!linea.isEmpty()) {
				ultimaLinea = linea;
				indiceUltimaLinea = i;
				break;
			}
		}

		// Patrón secundario usado en otros logs similares
		final String falloInicializacion = "Failed to initialize the mod loading system and display.";
		final String falloInicializacionActual = "Failed to initialize graphics window with current settings.";

		// Solo activar si la última línea no vacía contiene el mensaje principal
		if (ultimaLinea != null && ultimaLinea.contains("Loading ImmediateWindowProvider fmlearlywindow")) {
			mensaje = construirMensaje();
			// En este caso sí tenemos una línea concreta que queremos enlazar.
			enlaceHtml = consola.agregarErrorALectador(indiceUltimaLinea, this);
			activado = true;
		}

		else if (contenidoConsola.contains(falloInicializacion) || contenidoConsola.contains(falloInicializacionActual)
				|| limitacionOpenGLMacOSDetectada) {
			mensaje = construirMensaje();
			activado = true;
		}
	}

	private boolean contieneFirmaMacOSOpenGL(String contenidoConsola) {
		return contenidoConsola != null && contenidoConsola.contains(APPLE_METAL_OPENGL_RENDERER)
				&& contenidoConsola.contains(EARLY_FRAMEBUFFER_DRAW);
	}

	private String construirMensaje() {
		String base = MonitorDePID.idioma.fmlEarlyWindow() + Verificaciones.nl_html;
		if (limitacionOpenGLMacOSDetectada) {
			return base + MonitorDePID.idioma.fmlEarlyWindowMacOSOpenGL();
		}
		return base;
	}

	/**
	 * Método de verificación por línea.
	 * <p>
	 * Este verificador depende de conocer la última línea no vacía del log para
	 * determinar si se trata realmente del caso de early window, por lo que la
	 * lógica principal se mantiene en {@link #verificar(Consola)}.
	 * </p>
	 * <p>
	 * Se deja intencionadamente vacío para no duplicar trabajo ni perder la
	 * semántica de "la última línea del registro".
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// No se utiliza en este verificador: requiere contexto completo del log.
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
		return 1500.0f; // Prioridad media para advertencias de inicialización
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_early_window();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

	@Override
	public String id() {
		return "earlywindow";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo se marca como ocupado cuando:
	 * <ul>
	 * <li>El verificador ya se activó en el análisis global, y</li>
	 * <li>El texto del trazo contiene una de las cadenas clave asociadas al
	 * problema de early window.</li>
	 * </ul>
	 * Es intencionadamente conservador: mejor falsos negativos que falsos
	 * positivos.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (t.contains("Loading ImmediateWindowProvider fmlearlywindow")) {
			return true;
		}

		if (t.contains("Failed to initialize the mod loading system and display.")) {
			return true;
		}

		if (t.contains(EARLY_FRAMEBUFFER_DRAW) || t.contains(APPLE_METAL_OPENGL_RENDERER)) {
			return true;
		}

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

}