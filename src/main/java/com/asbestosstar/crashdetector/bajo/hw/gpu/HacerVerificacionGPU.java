package com.asbestosstar.crashdetector.bajo.hw.gpu;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Punto único de entrada para la verificación de GPU.
 *
 * Esta clase no depende directamente de ninguna versión de LWJGL. Detecta el
 * backend gráfico disponible y redirige la ejecución por reflexión a una de
 * estas implementaciones:
 *
 * - ValidadorGPU: LWJGL 3 con GLFW. - ValidadorGPUSDL3: LWJGL 3 con SDL3. -
 * ValidadorGPULWJGL2: LWJGL 2.
 *
 * De este modo sólo existe una clase HacerVerificacionGPU en el proyecto.
 */
public final class HacerVerificacionGPU {

	// ==========================================================
	// CONSTANTES DE LOG (NO CAMBIAR: LAS USA EL ANALIZADOR)
	// ==========================================================
	public static final String LOG_INICIO = "###GPU_VERIFICACION_INICIO###";
	public static final String LOG_FIN = "###GPU_VERIFICACION_FIN###";
	public static final String LOG_ERROR = "###GPU_VERIFICACION_ERROR###";

	public static final String LOG_OPENGL_ERROR = "###GPU_OPENGL_ERROR###";
	public static final String LOG_OPENGL_INICIO = "###GPU_OPENGL_INICIO###";
	public static final String LOG_OPENGL_FIN = "###GPU_OPENGL_FIN###";

	public static final String MSG_ADVERTENCIA = "ADVERTENCIA: No se usa la mejor GPU.";

	public static final String NO_FATAL = "Si puedes leer esto, significa que el verificador de la GPU probablemente no provocó que tu juego fallara; no funcionó según lo previsto, pero no hizo que el juego se cerrara inesperadamente.";

	private static final String MSG_LWJGL_NO_PRESENTE = "LWJGL no detectado, omitiendo verificación de GPU.";
	private static final String MSG_INICIANDO = "Iniciando verificación de GPU...";
	private static final String MSG_ERROR_REFLEXION = "Error cargando el validador de GPU por reflexión.";

	/**
	 * Propiedad opcional para forzar un backend concreto.
	 *
	 * Valores admitidos: auto, glfw, sdl3, lwjgl2 y off.
	 *
	 * Ejemplo: -Dcrashdetector.gpu.backend=sdl3
	 */
	private static final String PROPIEDAD_BACKEND = "crashdetector.gpu.backend";

	private HacerVerificacionGPU() {
		// Clase de entrada estática; no se crean instancias.
	}

	/**
	 * Método usado por CrashDetector para iniciar la comprobación.
	 */
	public static void hacer() {
		imprimirYRegistrar(LOG_INICIO);

		try {
			Backend backend = seleccionarBackend();

			if (backend == Backend.DESACTIVADO) {
				CrashDetectorLogger.log("Verificación de GPU desactivada mediante " + PROPIEDAD_BACKEND + ".");
				return;
			}

			if (backend == null) {
				System.out.println(MSG_LWJGL_NO_PRESENTE);
				CrashDetectorLogger.log(MSG_LWJGL_NO_PRESENTE);
				return;
			}

			System.out.println(MSG_INICIANDO);
			CrashDetectorLogger.log(MSG_INICIANDO);
			CrashDetectorLogger.log("Backend de GPU seleccionado: " + backend.nombreVisible);

			invocarBackend(backend);

		} catch (Throwable error) {
			Throwable causa = ReflexionGPU.causaReal(error);
			imprimirYRegistrar(LOG_ERROR);
			System.err.println(MSG_ERROR_REFLEXION + ": " + ReflexionGPU.mensajeSeguro(causa));
			causa.printStackTrace();
			CrashDetectorLogger.log(LOG_ERROR + ": " + ReflexionGPU.mensajeSeguro(causa));
			System.out.println(NO_FATAL);
			CrashDetectorLogger.log(NO_FATAL);

		} finally {
			// El marcador final se emite una sola vez, sin importar qué backend se usó.
			imprimirYRegistrar(LOG_FIN);
		}
	}

	/**
	 * Entrada convencional para poder ejecutar la clase desde la línea de comandos.
	 */
	public static void main(String[] argumentos) {
		hacer();
	}

	private static void invocarBackend(Backend backend) throws Throwable {
		Class<?> clase = Class.forName(backend.nombreClase, true, HacerVerificacionGPU.class.getClassLoader());

		try {
			clase.getMethod("ejecutar").invoke(null);
		} catch (java.lang.reflect.InvocationTargetException e) {
			throw ReflexionGPU.causaReal(e);
		}
	}

	// ==========================================================
	// SELECCIÓN AUTOMÁTICA DEL BACKEND
	// ==========================================================
	private static Backend seleccionarBackend() {
		String solicitado = System.getProperty(PROPIEDAD_BACKEND, "auto").trim().toLowerCase(java.util.Locale.ENGLISH);

		if ("off".equals(solicitado) || "false".equals(solicitado) || "ninguno".equals(solicitado)) {
			return Backend.DESACTIVADO;
		}

		if (!"auto".equals(solicitado) && !solicitado.isEmpty()) {
			Backend forzado = Backend.desdeNombre(solicitado);
			if (forzado != null && forzado != Backend.DESACTIVADO) {
				if (forzado.estaDisponible()) {
					return forzado;
				}

				CrashDetectorLogger.log("El backend solicitado '" + solicitado
						+ "' no está disponible; se usará detección automática.");
			}
		}

		/*
		 * 1. Primero se busca un contexto ya activo. Ésta es la señal más fiable: puede
		 * haber más de un módulo en el classpath, pero sólo uno controla la ventana
		 * OpenGL actual del hilo de renderizado.
		 */
		if (Backend.SDL3.tieneContextoActivo()) {
			return Backend.SDL3;
		}
		if (Backend.GLFW.tieneContextoActivo()) {
			return Backend.GLFW;
		}
		if (Backend.LWJGL2.tieneContextoActivo()) {
			return Backend.LWJGL2;
		}

		/*
		 * 2. SDL3 puede tener inicializado el subsistema de vídeo antes de crear o
		 * hacer actual el contexto OpenGL.
		 */
		if (Backend.SDL3.tieneSistemaVideoInicializado()) {
			return Backend.SDL3;
		}

		/*
		 * 3. LWJGL 2 y LWJGL 3 no suelen coexistir porque comparten nombres de
		 * paquetes. Si Display existe y org.lwjgl.opengl.GL no existe, es LWJGL 2.
		 */
		if (Backend.LWJGL2.estaDisponible() && !ReflexionGPU.existeClase("org.lwjgl.opengl.GL")) {
			return Backend.LWJGL2;
		}

		/*
		 * 4. Sin contexto activo se prefiere GLFW, porque es el backend histórico de
		 * Minecraft moderno. Una aplicación SDL3 puede forzarlo con la propiedad
		 * indicada arriba si llama al detector antes de crear su contexto.
		 */
		if (Backend.GLFW.estaDisponible()) {
			return Backend.GLFW;
		}
		if (Backend.SDL3.estaDisponible()) {
			return Backend.SDL3;
		}
		if (Backend.LWJGL2.estaDisponible()) {
			return Backend.LWJGL2;
		}

		return null;
	}

	private static void imprimirYRegistrar(String texto) {
		System.out.println(texto);
		CrashDetectorLogger.log(texto);
	}

	private enum Backend {
		GLFW("glfw", "LWJGL 3 + GLFW", "com.asbestosstar.crashdetector.bajo.hw.gpu.ValidadorGPU"),
		SDL3("SDL3", "LWJGL 3 + SDL3", "com.asbestosstar.crashdetector.bajo.hw.gpu.ValidadorGPUSDL3"),
		LWJGL2("lwjgl2", "LWJGL 2", "com.asbestosstar.crashdetector.bajo.hw.gpu.ValidadorGPULWJGL2"),
		DESACTIVADO("off", "Desactivado", "");

		final String nombreConfiguracion;
		final String nombreVisible;
		final String nombreClase;

		Backend(String nombreConfiguracion, String nombreVisible, String nombreClase) {
			this.nombreConfiguracion = nombreConfiguracion;
			this.nombreVisible = nombreVisible;
			this.nombreClase = nombreClase;
		}

		static Backend desdeNombre(String nombre) {
			if ("sdl".equals(nombre) || "sdl3".equals(nombre) || "gpusdl3".equals(nombre)) {
				return SDL3;
			}
			if ("glfw".equals(nombre) || "lwjgl3".equals(nombre)) {
				return GLFW;
			}
			if ("lwjgl2".equals(nombre) || "legacy".equals(nombre)) {
				return LWJGL2;
			}
			if ("off".equals(nombre) || "ninguno".equals(nombre)) {
				return DESACTIVADO;
			}
			return null;
		}

		boolean estaDisponible() {
			switch (this) {
			case GLFW:
				return ReflexionGPU.existeClase("org.lwjgl.glfw.GLFW")
						&& ReflexionGPU.existeClase("org.lwjgl.opengl.GL")
						&& ReflexionGPU.existeClase("org.lwjgl.opengl.GL11");

			case SDL3:
				return ReflexionGPU.existeClase("org.lwjgl.sdl.SDLInit")
						&& ReflexionGPU.existeClase("org.lwjgl.sdl.SDLVideo")
						&& ReflexionGPU.existeClase("org.lwjgl.opengl.GL")
						&& ReflexionGPU.existeClase("org.lwjgl.opengl.GL11");

			case LWJGL2:
				return ReflexionGPU.existeClase("org.lwjgl.opengl.Display")
						&& ReflexionGPU.existeClase("org.lwjgl.opengl.Pbuffer")
						&& ReflexionGPU.existeClase("org.lwjgl.opengl.PixelFormat")
						&& ReflexionGPU.existeClase("org.lwjgl.opengl.GL11");

			default:
				return false;
			}
		}

		boolean tieneContextoActivo() {
			if (!estaDisponible()) {
				return false;
			}

			try {
				switch (this) {
				case GLFW:
					Object contextoGLFW = ReflexionGPU.invocarEstatico("org.lwjgl.glfw.GLFW", "glfwGetCurrentContext");
					return contextoGLFW instanceof Number && ((Number) contextoGLFW).longValue() != 0L;

				case SDL3:
					Object contextoSDL = ReflexionGPU.invocarEstatico("org.lwjgl.sdl.SDLVideo",
							"SDL_GL_GetCurrentContext");
					return contextoSDL instanceof Number && ((Number) contextoSDL).longValue() != 0L;

				case LWJGL2:
					// GL11 funciona también con un Pbuffer actual, aunque Display no exista.
					String renderer = ValidadorGPULWJGL2.intentarLeerRendererActual();
					return renderer != null;

				default:
					return false;
				}
			} catch (Throwable ignorado) {
				return false;
			}
		}

		boolean tieneSistemaVideoInicializado() {
			if (this != SDL3 || !estaDisponible()) {
				return false;
			}

			try {
				int video = ReflexionGPU.campoInt("org.lwjgl.sdl.SDLInit", "SDL_INIT_VIDEO");
				Object resultado = ReflexionGPU.invocarEstatico("org.lwjgl.sdl.SDLInit", "SDL_WasInit",
						Integer.valueOf(video));

				return resultado instanceof Number && ((((Number) resultado).intValue() & video) != 0);
			} catch (Throwable ignorado) {
				return false;
			}
		}
	}
}
