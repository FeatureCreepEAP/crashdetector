package com.asbestosstar.crashdetector.bajo.hw.gpu;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Backend para LWJGL 3 usando SDL3.
 *
 * No contiene imports de org.lwjgl.sdl. Todas las llamadas se realizan por
 * reflexión para que la clase pueda permanecer en el mismo JAR que las
 * versiones GLFW y LWJGL2 sin obligar a que SDL3 exista en todos los juegos.
 */
public final class ValidadorGPUSDL3 {

	private static final String CLASE_SDL_INIT = "org.lwjgl.sdl.SDLInit";
	private static final String CLASE_SDL_VIDEO = "org.lwjgl.sdl.SDLVideo";
	private static final String CLASE_SDL_ERROR = "org.lwjgl.sdl.SDLError";
	private static final String CLASE_GL = "org.lwjgl.opengl.GL";
	private static final String CLASE_GL11 = "org.lwjgl.opengl.GL11";

	private ValidadorGPUSDL3() {
		// Backend estático; no se crean instancias.
	}

	public static void ejecutar() {
		AnalizadorGPUComun.ejecutar("LWJGL 3 + SDL3", new AnalizadorGPUComun.ProveedorRenderer() {
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
		boolean inicializamosVideoSDL = false;
		boolean capacidadesTemporales = false;
		long ventanaTemporal = 0L;
		long contextoTemporal = 0L;

		try {
			long contextoActual = numeroLong(ReflexionGPU.invocarEstatico(CLASE_SDL_VIDEO, "SDL_GL_GetCurrentContext"));

			if (contextoActual != 0L) {
				System.out.println("[GPU Check] Usando contexto OpenGL existente de SDL3.");
				asegurarCapacidadesLWJGL3();
				return leerRenderer();
			}

			System.out.println("[GPU Check] Creando contexto OpenGL temporal con SDL3.");

			int video = ReflexionGPU.campoInt(CLASE_SDL_INIT, "SDL_INIT_VIDEO");
			int subsistemas = numeroInt(
					ReflexionGPU.invocarEstatico(CLASE_SDL_INIT, "SDL_WasInit", Integer.valueOf(video)));

			if ((subsistemas & video) == 0) {
				Object resultadoInit = ReflexionGPU.invocarEstatico(CLASE_SDL_INIT, "SDL_InitSubSystem",
						Integer.valueOf(video));

				if (!ReflexionGPU.resultadoExitoso(resultadoInit)) {
					return "Error SDL3: " + obtenerUltimoErrorSDL();
				}
				inicializamosVideoSDL = true;
			}

			configurarAtributosOpenGLCore();
			ventanaTemporal = crearVentanaOculta();

			if (ventanaTemporal == 0L) {
				/*
				 * Igual que en GLFW, se permite un segundo intento sin exigir perfil Core para
				 * controladores antiguos o plataformas peculiares.
				 */
				CrashDetectorLogger.log("SDL3 no pudo crear OpenGL 3.2 Core; intentando atributos predeterminados.");
				ReflexionGPU.invocarEstatico(CLASE_SDL_VIDEO, "SDL_GL_ResetAttributes");
				ventanaTemporal = crearVentanaOculta();
			}

			if (ventanaTemporal == 0L) {
				return "Error ventana SDL3: " + obtenerUltimoErrorSDL();
			}

			contextoTemporal = numeroLong(ReflexionGPU.invocarEstatico(CLASE_SDL_VIDEO, "SDL_GL_CreateContext",
					Long.valueOf(ventanaTemporal)));

			if (contextoTemporal == 0L) {
				return "Error contexto SDL3: " + obtenerUltimoErrorSDL();
			}

			Object resultadoMakeCurrent = ReflexionGPU.invocarEstatico(CLASE_SDL_VIDEO, "SDL_GL_MakeCurrent",
					Long.valueOf(ventanaTemporal), Long.valueOf(contextoTemporal));

			if (!ReflexionGPU.resultadoExitoso(resultadoMakeCurrent)) {
				return "Error activando contexto SDL3: " + obtenerUltimoErrorSDL();
			}

			asegurarCapacidadesLWJGL3();
			capacidadesTemporales = true;
			return leerRenderer();

		} finally {
			if (contextoTemporal != 0L) {
				if (capacidadesTemporales) {
					limpiarCapacidadesLWJGL3();
				}

				try {
					// En SDL3, null window + null context libera el contexto actual.
					ReflexionGPU.invocarEstatico(CLASE_SDL_VIDEO, "SDL_GL_MakeCurrent", Long.valueOf(0L),
							Long.valueOf(0L));
				} catch (Throwable ignorado) {
					// No propagar fallos durante la limpieza.
				}

				try {
					ReflexionGPU.invocarEstatico(CLASE_SDL_VIDEO, "SDL_GL_DestroyContext",
							Long.valueOf(contextoTemporal));
				} catch (Throwable ignorado) {
					// No propagar fallos durante la limpieza.
				}
			}

			if (ventanaTemporal != 0L) {
				try {
					ReflexionGPU.invocarEstatico(CLASE_SDL_VIDEO, "SDL_DestroyWindow", Long.valueOf(ventanaTemporal));
				} catch (Throwable ignorado) {
					// No propagar fallos durante la limpieza.
				}
			}

			if (inicializamosVideoSDL) {
				try {
					int video = ReflexionGPU.campoInt(CLASE_SDL_INIT, "SDL_INIT_VIDEO");
					ReflexionGPU.invocarEstatico(CLASE_SDL_INIT, "SDL_QuitSubSystem", Integer.valueOf(video));
				} catch (Throwable ignorado) {
					/*
					 * Nunca se llama SDL_Quit(): sólo intentamos devolver el contador del
					 * subsistema VIDEO que esta clase incrementó.
					 */
				}
			}
		}
	}

	private static void configurarAtributosOpenGLCore() throws Throwable {
		ReflexionGPU.invocarEstatico(CLASE_SDL_VIDEO, "SDL_GL_ResetAttributes");

		int mayor = ReflexionGPU.campoInt(CLASE_SDL_VIDEO, "SDL_GL_CONTEXT_MAJOR_VERSION");
		int menor = ReflexionGPU.campoInt(CLASE_SDL_VIDEO, "SDL_GL_CONTEXT_MINOR_VERSION");
		int mascaraPerfil = ReflexionGPU.campoInt(CLASE_SDL_VIDEO, "SDL_GL_CONTEXT_PROFILE_MASK");
		int perfilCore = ReflexionGPU.campoInt(CLASE_SDL_VIDEO, "SDL_GL_CONTEXT_PROFILE_CORE");
		int flags = ReflexionGPU.campoInt(CLASE_SDL_VIDEO, "SDL_GL_CONTEXT_FLAGS");
		int forward = ReflexionGPU.campoInt(CLASE_SDL_VIDEO, "SDL_GL_CONTEXT_FORWARD_COMPATIBLE_FLAG");

		establecerAtributo(mayor, 3, "versión mayor");
		establecerAtributo(menor, 2, "versión menor");
		establecerAtributo(mascaraPerfil, perfilCore, "perfil Core");
		establecerAtributo(flags, forward, "forward-compatible");
	}

	private static void establecerAtributo(int atributo, int valor, String descripcion) throws Throwable {
		Object resultado = ReflexionGPU.invocarEstatico(CLASE_SDL_VIDEO, "SDL_GL_SetAttribute",
				Integer.valueOf(atributo), Integer.valueOf(valor));

		if (!ReflexionGPU.resultadoExitoso(resultado)) {
			CrashDetectorLogger.log("Advertencia SDL3 configurando " + descripcion + ": " + obtenerUltimoErrorSDL());
		}
	}

	private static long crearVentanaOculta() throws Throwable {
		long openGL = ReflexionGPU.campoLong(CLASE_SDL_VIDEO, "SDL_WINDOW_OPENGL");
		long oculta = ReflexionGPU.campoLong(CLASE_SDL_VIDEO, "SDL_WINDOW_HIDDEN");

		Object resultado = ReflexionGPU.invocarEstatico(CLASE_SDL_VIDEO, "SDL_CreateWindow", "Detector",
				Integer.valueOf(100), Integer.valueOf(100), Long.valueOf(openGL | oculta));

		return numeroLong(resultado);
	}

	private static void asegurarCapacidadesLWJGL3() throws Throwable {
		try {
			ReflexionGPU.invocarEstatico(CLASE_GL, "getCapabilities");
		} catch (Throwable sinCapacidades) {
			ReflexionGPU.invocarEstatico(CLASE_GL, "createCapabilities");
		}
	}

	private static void limpiarCapacidadesLWJGL3() {
		try {
			ReflexionGPU.invocarEstatico(CLASE_GL, "setCapabilities", new Object[] { null });
		} catch (Throwable ignorado) {
			// No propagar fallos durante la limpieza.
		}
	}

	private static String leerRenderer() throws Throwable {
		int glRenderer = ReflexionGPU.campoInt(CLASE_GL11, "GL_RENDERER");
		Object renderer = ReflexionGPU.invocarEstatico(CLASE_GL11, "glGetString", Integer.valueOf(glRenderer));
		return renderer == null ? "Desconocido" : renderer.toString();
	}

	private static String obtenerUltimoErrorSDL() {
		try {
			Object error = ReflexionGPU.invocarEstatico(CLASE_SDL_ERROR, "SDL_GetError");
			if (error != null && !error.toString().trim().isEmpty()) {
				return error.toString();
			}
		} catch (Throwable ignorado) {
			// Se usa el texto genérico inferior.
		}
		return "sin detalle proporcionado por SDL3";
	}

	private static long numeroLong(Object valor) {
		return valor instanceof Number ? ((Number) valor).longValue() : 0L;
	}

	private static int numeroInt(Object valor) {
		return valor instanceof Number ? ((Number) valor).intValue() : 0;
	}
}
