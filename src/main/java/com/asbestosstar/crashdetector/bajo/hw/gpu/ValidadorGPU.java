package com.asbestosstar.crashdetector.bajo.hw.gpu;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Backend para LWJGL 3 usando GLFW.
 *
 * El nombre ValidadorGPU se conserva para mantener compatibilidad con el código
 * original. HacerVerificacionGPU es quien decide si debe ejecutar esta clase.
 */
public final class ValidadorGPU {

	private static final String CLASE_GLFW = "org.lwjgl.glfw.GLFW";
	private static final String CLASE_GL = "org.lwjgl.opengl.GL";
	private static final String CLASE_GL11 = "org.lwjgl.opengl.GL11";

	private ValidadorGPU() {
		// Backend estático; no se crean instancias.
	}

	/**
	 * Método llamado por HacerVerificacionGPU mediante reflexión.
	 */
	public static void ejecutar() {
		AnalizadorGPUComun.ejecutar("LWJGL 3 + GLFW", new AnalizadorGPUComun.ProveedorRenderer() {
			@Override
			public String obtenerRenderer() throws Throwable {
				return obtenerGPUActivaOpenGL();
			}
		});
	}

	/**
	 * Conserva una entrada sin argumentos para llamadas antiguas, pero no emite los
	 * marcadores generales; esos pertenecen exclusivamente al router.
	 */
	public static void main() {
		ejecutar();
	}

	private static String obtenerGPUActivaOpenGL() throws Throwable {
		long ventanaTemporal = 0L;
		boolean capacidadesTemporales = false;

		try {
			long contextoActual = numeroLong(ReflexionGPU.invocarEstatico(CLASE_GLFW, "glfwGetCurrentContext"));

			if (contextoActual != 0L) {
				System.out.println("[GPU Check] Usando contexto OpenGL existente de GLFW.");
				asegurarCapacidadesLWJGL3();
				return leerRenderer();
			}

			/*
			 * No hay contexto actual en este hilo. Se crea una ventana oculta y se consulta
			 * GL_RENDERER. No se llama glfwTerminate(), porque GLFW podría haber sido
			 * inicializado previamente por el juego en otro hilo.
			 */
			System.out.println("[GPU Check] Creando contexto OpenGL temporal con GLFW.");

			Object inicializado = ReflexionGPU.invocarEstatico(CLASE_GLFW, "glfwInit");
			if (!ReflexionGPU.resultadoExitoso(inicializado)) {
				return "Error GLFW";
			}

			configurarHintsCore();
			ventanaTemporal = crearVentanaOculta();

			if (ventanaTemporal == 0L) {
				// Algunos controladores antiguos no aceptan OpenGL 3.2 Core.
				CrashDetectorLogger.log("GLFW no pudo crear OpenGL 3.2 Core; intentando un contexto predeterminado.");
				configurarHintsPredeterminados();
				ventanaTemporal = crearVentanaOculta();
			}

			if (ventanaTemporal == 0L) {
				return "Error ventana GLFW";
			}

			ReflexionGPU.invocarEstatico(CLASE_GLFW, "glfwMakeContextCurrent", Long.valueOf(ventanaTemporal));

			asegurarCapacidadesLWJGL3();
			capacidadesTemporales = true;
			return leerRenderer();

		} finally {
			if (ventanaTemporal != 0L) {
				if (capacidadesTemporales) {
					limpiarCapacidadesLWJGL3();
				}

				try {
					ReflexionGPU.invocarEstatico(CLASE_GLFW, "glfwMakeContextCurrent", Long.valueOf(0L));
				} catch (Throwable ignorado) {
					// La limpieza nunca debe cerrar el juego.
				}

				try {
					ReflexionGPU.invocarEstatico(CLASE_GLFW, "glfwDestroyWindow", Long.valueOf(ventanaTemporal));
				} catch (Throwable ignorado) {
					// La limpieza nunca debe cerrar el juego.
				}
			}
		}
	}

	private static void configurarHintsCore() throws Throwable {
		ReflexionGPU.invocarEstatico(CLASE_GLFW, "glfwDefaultWindowHints");

		int visible = ReflexionGPU.campoInt(CLASE_GLFW, "GLFW_VISIBLE");
		int falso = ReflexionGPU.campoInt(CLASE_GLFW, "GLFW_FALSE");
		int versionMayor = ReflexionGPU.campoInt(CLASE_GLFW, "GLFW_CONTEXT_VERSION_MAJOR");
		int versionMenor = ReflexionGPU.campoInt(CLASE_GLFW, "GLFW_CONTEXT_VERSION_MINOR");
		int perfil = ReflexionGPU.campoInt(CLASE_GLFW, "GLFW_OPENGL_PROFILE");
		int perfilCore = ReflexionGPU.campoInt(CLASE_GLFW, "GLFW_OPENGL_CORE_PROFILE");
		int forwardCompatible = ReflexionGPU.campoInt(CLASE_GLFW, "GLFW_OPENGL_FORWARD_COMPAT");
		int verdadero = ReflexionGPU.campoInt(CLASE_GLFW, "GLFW_TRUE");

		hint(visible, falso);
		hint(versionMayor, 3);
		hint(versionMenor, 2);
		hint(perfil, perfilCore);
		hint(forwardCompatible, verdadero);
	}

	private static void configurarHintsPredeterminados() throws Throwable {
		ReflexionGPU.invocarEstatico(CLASE_GLFW, "glfwDefaultWindowHints");
		int visible = ReflexionGPU.campoInt(CLASE_GLFW, "GLFW_VISIBLE");
		int falso = ReflexionGPU.campoInt(CLASE_GLFW, "GLFW_FALSE");
		hint(visible, falso);
	}

	private static void hint(int nombre, int valor) throws Throwable {
		ReflexionGPU.invocarEstatico(CLASE_GLFW, "glfwWindowHint", Integer.valueOf(nombre), Integer.valueOf(valor));
	}

	private static long crearVentanaOculta() throws Throwable {
		Object resultado = ReflexionGPU.invocarEstatico(CLASE_GLFW, "glfwCreateWindow", Integer.valueOf(100),
				Integer.valueOf(100), "Detector", Long.valueOf(0L), Long.valueOf(0L));
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
			// El contexto temporal ya va a destruirse; no propagar un fallo de limpieza.
		}
	}

	private static String leerRenderer() throws Throwable {
		int glRenderer = ReflexionGPU.campoInt(CLASE_GL11, "GL_RENDERER");
		Object renderer = ReflexionGPU.invocarEstatico(CLASE_GL11, "glGetString", Integer.valueOf(glRenderer));
		return renderer == null ? "Desconocido" : renderer.toString();
	}

	private static long numeroLong(Object valor) {
		return valor instanceof Number ? ((Number) valor).longValue() : 0L;
	}
}
