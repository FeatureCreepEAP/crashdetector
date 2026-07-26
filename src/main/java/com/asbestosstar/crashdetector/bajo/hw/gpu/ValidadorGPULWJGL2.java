package com.asbestosstar.crashdetector.bajo.hw.gpu;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Backend compatible con LWJGL 2.
 *
 * LWJGL 2 no posee org.lwjgl.opengl.GL ni GLFW. El contexto normalmente es
 * administrado por Display. Si no existe un contexto actual, esta clase intenta
 * crear un Pbuffer de 1x1, que es un contexto OpenGL fuera de pantalla.
 */
public final class ValidadorGPULWJGL2 {

	private static final String CLASE_GL11 = "org.lwjgl.opengl.GL11";
	private static final String CLASE_PBUFFER = "org.lwjgl.opengl.Pbuffer";
	private static final String CLASE_PIXEL_FORMAT = "org.lwjgl.opengl.PixelFormat";

	private ValidadorGPULWJGL2() {
		// Backend estático; no se crean instancias.
	}

	public static void ejecutar() {
		AnalizadorGPUComun.ejecutar("LWJGL 2", new AnalizadorGPUComun.ProveedorRenderer() {
			@Override
			public String obtenerRenderer() throws Throwable {
				return obtenerGPUActivaOpenGL();
			}
		});
	}

	public static void main() {
		ejecutar();
	}

	private static String obtenerGPUActivaOpenGL() throws Throwable {
		String rendererActual = intentarLeerRendererActual();
		if (rendererActual != null) {
			System.out.println("[GPU Check] Usando contexto OpenGL existente de LWJGL 2.");
			return rendererActual;
		}

		System.out.println("[GPU Check] Creando Pbuffer OpenGL temporal con LWJGL 2.");

		int capacidades = numeroInt(ReflexionGPU.invocarEstatico(CLASE_PBUFFER, "getCapabilities"));
		int soportado = ReflexionGPU.campoInt(CLASE_PBUFFER, "PBUFFER_SUPPORTED");

		if ((capacidades & soportado) == 0) {
			CrashDetectorLogger
					.log("LWJGL 2 no informa soporte de Pbuffer; no se creará una ventana visible de prueba.");
			return "Error LWJGL2: Pbuffer no soportado";
		}

		Object pbuffer = null;
		try {
			Object pixelFormat = ReflexionGPU.construir(CLASE_PIXEL_FORMAT);

			/*
			 * Constructor usado: Pbuffer(int ancho, int alto, PixelFormat formato, Drawable
			 * compartido)
			 *
			 * El Drawable compartido se deja en null. LWJGL 2 compartirá con Display si
			 * existe, según sus reglas internas.
			 */
			pbuffer = ReflexionGPU.construir(CLASE_PBUFFER, Integer.valueOf(1), Integer.valueOf(1), pixelFormat, null);

			ReflexionGPU.invocarInstancia(pbuffer, "makeCurrent");
			String renderer = leerRenderer();
			return renderer == null ? "Desconocido" : renderer;

		} finally {
			if (pbuffer != null) {
				try {
					ReflexionGPU.invocarInstancia(pbuffer, "releaseContext");
				} catch (Throwable ignorado) {
					// No propagar un fallo de limpieza.
				}

				try {
					ReflexionGPU.invocarInstancia(pbuffer, "destroy");
				} catch (Throwable ignorado) {
					// No propagar un fallo de limpieza.
				}
			}
		}
	}

	/**
	 * Intenta consultar GL_RENDERER en cualquier contexto LWJGL 2 que ya sea actual
	 * en este hilo. Funciona con Display y también con un Pbuffer ajeno.
	 */
	public static String intentarLeerRendererActual() {
		try {
			String renderer = leerRenderer();
			return renderer != null && !renderer.trim().isEmpty() ? renderer : null;
		} catch (Throwable ignorado) {
			return null;
		}
	}

	private static String leerRenderer() throws Throwable {
		int glRenderer = ReflexionGPU.campoInt(CLASE_GL11, "GL_RENDERER");
		Object renderer = ReflexionGPU.invocarEstatico(CLASE_GL11, "glGetString", Integer.valueOf(glRenderer));
		return renderer == null ? null : renderer.toString();
	}

	private static int numeroInt(Object valor) {
		return valor instanceof Number ? ((Number) valor).intValue() : 0;
	}
}
