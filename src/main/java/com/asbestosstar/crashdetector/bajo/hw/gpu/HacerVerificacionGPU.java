package com.asbestosstar.crashdetector.bajo.hw.gpu;

public class HacerVerificacionGPU {

	// ==========================================================
	// CONSTANTES DE LOG (IMPORTANTE PARA ANALIZADOR)
	// ==========================================================
	public static final String LOG_INICIO = "###GPU_VERIFICACION_INICIO###";
	public static final String LOG_FIN = "###GPU_VERIFICACION_FIN###";
	public static final String LOG_ERROR = "###GPU_VERIFICACION_ERROR###";

	public static final String LOG_OPENGL_ERROR = "###GPU_OPENGL_ERROR###";
	public static final String LOG_OPENGL_INICIO = "###GPU_OPENGL_INICIO###";
	public static final String LOG_OPENGL_FIN = "###GPU_OPENGL_FIN###";

	public static final String MSG_ADVERTENCIA = "ADVERTENCIA: No se usa la mejor GPU.";

	public static final String NO_FATAL = "Si puedes leer esto, significa que el verificador de la GPU probablemente no provocó que tu juego fallara; no funcionó según lo previsto, pero no hizo que el juego se cerrara inesperadamente.";

	// Mensajes internos
	private static final String MSG_LWJGL_NO_PRESENTE = "LWJGL no detectado, omitiendo verificación de GPU.";
	private static final String MSG_INICIANDO = "Iniciando verificación de GPU...";
	private static final String MSG_ERROR_REFLEXION = "Error cargando ValidadorGPU por reflexión.";

	// ==========================================================
	// MÉTODO PRINCIPAL
	// ==========================================================
	public static void hacer() {

		System.out.println(LOG_INICIO);

		try {
			// --------------------------------------------------
			// 1. Verificar si LWJGL está presente
			// --------------------------------------------------
			if (!estaLWJGLDisponible()) {
				System.out.println(MSG_LWJGL_NO_PRESENTE);
				System.out.println(LOG_FIN);
				return;
			}

			System.out.println(MSG_INICIANDO);

			// --------------------------------------------------
			// 2. Ejecutar ValidadorGPU vía reflexión
			// (evita crash si la clase no carga correctamente)
			// --------------------------------------------------
			try {
				Class<?> clazz = Class.forName("com.asbestosstar.crashdetector.bajo.hw.gpu.ValidadorGPU");

				// Llamar al método main()
				clazz.getMethod("main").invoke(null);

			} catch (Throwable t) {
				System.out.println(LOG_ERROR);
				System.err.println(MSG_ERROR_REFLEXION + ": " + t.getMessage());
				t.printStackTrace();
			}

			// --------------------------------------------------
			// 3. Fin normal
			// --------------------------------------------------
			System.out.println(LOG_FIN);

		} catch (Throwable t) {
			// Error global (no JVM crash, pero sí fallo serio)
			System.out.println(LOG_ERROR);
			System.err.println("ERROR GENERAL: " + t.getMessage());
			t.printStackTrace();

			// Mensaje clave para tu analizador
			System.out.println(NO_FATAL);
		}
	}

	// ==========================================================
	// DETECCIÓN SEGURA DE LWJGL
	// ==========================================================
	private static boolean estaLWJGLDisponible() {
		try {
			// Intentar cargar clases clave sin inicializarlas
			Class.forName("org.lwjgl.glfw.GLFW", false, HacerVerificacionGPU.class.getClassLoader());
			Class.forName("org.lwjgl.opengl.GL", false, HacerVerificacionGPU.class.getClassLoader());
			return true;

		} catch (Throwable t) {
			return false;
		}
	}
}