package com.asbestosstar.crashdetector.bajo.hw.gpu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Parte común del análisis de GPU.
 *
 * Los backends GLFW, SDL3 y LWJGL2 solamente se ocupan de obtener GL_RENDERER.
 * Esta clase conserva una única copia de la enumeración del hardware, la
 * clasificación y la comparación de la GPU activa con las GPUs instaladas.
 */
final class AnalizadorGPUComun {

	interface ProveedorRenderer {
		String obtenerRenderer() throws Throwable;
	}

	static final String MSG_SO = "Sistema Operativo detectado: ";
	static final String MSG_GPU_ACTIVA = "GPU en uso: ";
	static final String MSG_NO_GPUS = "No se detectaron GPUs o hubo un error.";
	static final String MSG_ANALISIS_CANCELADO = "Análisis cancelado.";
	static final String MSG_EXITO = "ÉXITO: Se usa la mejor GPU.";

	private AnalizadorGPUComun() {
		// Clase de utilidades; no se crean instancias.
	}

	static void ejecutar(String nombreBackend, ProveedorRenderer proveedor) {
		String soName = System.getProperty("os.name", "Desconocido");
		System.out.println(MSG_SO + soName);
		CrashDetectorLogger.log(MSG_SO + soName);
		CrashDetectorLogger.log("Verificación OpenGL usando backend: " + nombreBackend);

		String gpuActiva = obtenerGPUActiva(proveedor, nombreBackend);
		System.out.println(MSG_GPU_ACTIVA + gpuActiva);

		if (esRendererValido(gpuActiva)) {
			CrashDetectorLogger.log("GPU ACTIVA DETECTADA: " + gpuActiva);
		} else {
			CrashDetectorLogger.log("ADVERTENCIA: No se pudo determinar la GPU activa vía OpenGL (" + gpuActiva + ")");
		}

		List<InfoGPU> todas = obtenerGPUsDelSistema();

		if (todas.isEmpty()) {
			System.out.println(MSG_NO_GPUS);
			CrashDetectorLogger.log(MSG_NO_GPUS);
		} else {
			CrashDetectorLogger.log("--- LISTA DE GPUs EN EL SISTEMA ---");
			for (InfoGPU gpu : todas) {
				if (gpu.nombre != null && !gpu.nombre.trim().isEmpty()) {
					CrashDetectorLogger.log(gpu.toString());
				}
			}
			CrashDetectorLogger.log("-----------------------------------");
		}

		if (!esRendererValido(gpuActiva) || todas.isEmpty()) {
			System.out.println(MSG_ANALISIS_CANCELADO);
			CrashDetectorLogger.log(MSG_ANALISIS_CANCELADO);
			return;
		}

		InfoGPU mejorGPU = encontrarMejorGPU(todas);
		if (mejorGPU != null) {
			CrashDetectorLogger.log("MEJOR GPU DISPONIBLE (Teórica): " + mejorGPU.nombre + " (" + mejorGPU.tipo + ")");
		}

		if (estaUsandoMejorGPU(gpuActiva, todas)) {
			System.out.println(MSG_EXITO);
			CrashDetectorLogger.log(MSG_EXITO);
		} else {
			System.out.println(HacerVerificacionGPU.MSG_ADVERTENCIA);
			CrashDetectorLogger.log(HacerVerificacionGPU.MSG_ADVERTENCIA);
			if (mejorGPU != null) {
				CrashDetectorLogger.log("ACCIÓN SUGERIDA: El sistema debería estar usando " + mejorGPU.nombre
						+ " pero está usando " + gpuActiva);
			}
		}
	}

	private static String obtenerGPUActiva(ProveedorRenderer proveedor, String nombreBackend) {
		System.out.println(HacerVerificacionGPU.LOG_OPENGL_INICIO);
		CrashDetectorLogger.log(HacerVerificacionGPU.LOG_OPENGL_INICIO);

		try {
			String renderer = proveedor.obtenerRenderer();
			if (renderer == null || renderer.trim().isEmpty()) {
				renderer = "Desconocido";
			}

			if (renderer.startsWith("Error")) {
				System.out.println(HacerVerificacionGPU.LOG_OPENGL_ERROR);
				CrashDetectorLogger.log(HacerVerificacionGPU.LOG_OPENGL_ERROR + ": " + renderer);
			} else {
				System.out.println(HacerVerificacionGPU.LOG_OPENGL_FIN);
				CrashDetectorLogger.log(HacerVerificacionGPU.LOG_OPENGL_FIN);
			}

			return renderer;

		} catch (Throwable error) {
			String mensaje = ReflexionGPU.mensajeSeguro(error);
			System.out.println(HacerVerificacionGPU.LOG_OPENGL_ERROR);
			CrashDetectorLogger.log("Error en detección OpenGL mediante " + nombreBackend + ": " + mensaje);
			return "Error OpenGL";
		}
	}

	private static boolean esRendererValido(String renderer) {
		return renderer != null && !renderer.trim().isEmpty() && !renderer.startsWith("Error")
				&& !"Desconocido".equals(renderer);
	}

	// ==========================================================
	// MODELO DE DATOS
	// ==========================================================
	private static final class InfoGPU {
		String nombre;
		long memoriaMB;
		String relojNucleo = "N/A";
		String relojMemoria = "N/A";
		String tipo = "Desconocido";

		@Override
		public String toString() {
			StringBuilder texto = new StringBuilder();
			texto.append("GPU: ").append(nombre);
			if (memoriaMB > 0L) {
				texto.append(" | VRAM: ").append(memoriaMB).append(" MB");
			}
			if (!"N/A".equals(relojNucleo)) {
				texto.append(" | Núcleo: ").append(relojNucleo);
			}
			if (!"N/A".equals(relojMemoria)) {
				texto.append(" | Mem: ").append(relojMemoria);
			}
			texto.append(" | Tipo: ").append(tipo);
			return texto.toString();
		}
	}

	// ==========================================================
	// ENUMERACIÓN DE HARDWARE POR SISTEMA OPERATIVO
	// ==========================================================
	private static List<InfoGPU> obtenerGPUsDelSistema() {
		String so = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH);

		if (so.contains("win")) {
			return obtenerGPUsWindows();
		}
		if (so.contains("mac")) {
			return obtenerGPUsMacOS();
		}
		if (so.contains("nux")) {
			return obtenerGPUsLinux();
		}
		if (so.contains("nix") || so.contains("bsd") || so.contains("sunos") || so.contains("unix")) {
			return obtenerGPUsUnix();
		}

		return new ArrayList<InfoGPU>();
	}

	/**
	 * Windows moderno: primero usa PowerShell/CIM porque WMIC ya no está instalado
	 * por defecto en muchas versiones. Si falla, conserva el fallback WMIC de la
	 * implementación original.
	 */
	private static List<InfoGPU> obtenerGPUsWindows() {
		List<InfoGPU> gpus = ejecutarYConvertirNombres(new String[] { "powershell.exe", "-NoProfile", "-NonInteractive",
				"-Command", "Get-CimInstance Win32_VideoController | ForEach-Object { $_.Name }" }, 8L);

		if (!gpus.isEmpty()) {
			return gpus;
		}

		return ejecutarYConvertirNombres(new String[] { "wmic", "path", "win32_VideoController", "get", "name" }, 8L);
	}

	private static List<InfoGPU> obtenerGPUsMacOS() {
		List<InfoGPU> gpus = new ArrayList<InfoGPU>();
		Process proceso = null;

		try {
			proceso = new ProcessBuilder("system_profiler", "SPDisplaysDataType").redirectErrorStream(true).start();

			try (BufferedReader lector = lector(proceso)) {
				String linea;
				InfoGPU actual = null;

				while ((linea = lector.readLine()) != null) {
					linea = linea.trim();
					if (linea.startsWith("Chipset Model:")) {
						if (actual != null) {
							agregarSinDuplicar(gpus, actual);
						}
						actual = nuevaGPU(linea.substring(linea.indexOf(':') + 1).trim());
					} else if (actual != null && linea.startsWith("VRAM (Total):")) {
						actual.memoriaMB = convertirMemoriaMB(linea.substring(linea.indexOf(':') + 1).trim());
					}
				}

				if (actual != null) {
					agregarSinDuplicar(gpus, actual);
				}
			}

			esperarProceso(proceso, 10L);
		} catch (Throwable error) {
			CrashDetectorLogger.log("No se pudo ejecutar system_profiler: " + ReflexionGPU.mensajeSeguro(error));
			destruirProceso(proceso);
		}

		return gpus;
	}

	private static List<InfoGPU> obtenerGPUsLinux() {
		return parsearLspci();
	}

	private static List<InfoGPU> obtenerGPUsUnix() {
		List<InfoGPU> gpus = parsearLspci();
		if (!gpus.isEmpty()) {
			return gpus;
		}

		gpus = parsearComandoPorPalabras(new String[] { "pciconf", "-lv" },
				new String[] { "vendor", "device", "display", "vga" });
		if (!gpus.isEmpty()) {
			return gpus;
		}

		gpus = parsearComandoPorPalabras(new String[] { "prtconf", "-v" }, new String[] { "display", "model" });
		if (!gpus.isEmpty()) {
			return gpus;
		}

		return parsearComandoPorPalabras(new String[] { "hwconfig" }, new String[] { "video", "vga", "display" });
	}

	private static List<InfoGPU> parsearLspci() {
		List<InfoGPU> gpus = parsearComandoPorPalabras(new String[] { "lspci", "-v" },
				new String[] { "vga compatible controller", "3d controller", "display controller" });

		if (!gpus.isEmpty()) {
			return gpus;
		}

		return parsearComandoPorPalabras(new String[] { "lspci" }, new String[] { "vga", "3d", "display" });
	}

	private static List<InfoGPU> parsearComandoPorPalabras(String[] comando, String[] palabras) {
		List<InfoGPU> gpus = new ArrayList<InfoGPU>();
		Process proceso = null;

		try {
			proceso = new ProcessBuilder(comando).redirectErrorStream(true).start();
			try (BufferedReader lector = lector(proceso)) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					String lower = linea.toLowerCase(Locale.ENGLISH);
					if (contieneAlguna(lower, palabras) && pareceLineaGPU(lower)) {
						agregarSinDuplicar(gpus, nuevaGPU(extraerNombrePCI(linea.trim())));
					}
				}
			}
			esperarProceso(proceso, 8L);
		} catch (Throwable ignorado) {
			destruirProceso(proceso);
		}

		return gpus;
	}

	private static List<InfoGPU> ejecutarYConvertirNombres(String[] comando, long segundos) {
		List<InfoGPU> gpus = new ArrayList<InfoGPU>();
		Process proceso = null;

		try {
			proceso = new ProcessBuilder(comando).redirectErrorStream(true).start();
			try (BufferedReader lector = lector(proceso)) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					String nombre = linea.trim();
					if (nombre.isEmpty() || "name".equalsIgnoreCase(nombre)
							|| nombre.toLowerCase(Locale.ENGLISH).startsWith("get-ciminstance")) {
						continue;
					}
					if (pareceNombreGPU(nombre.toLowerCase(Locale.ENGLISH))) {
						agregarSinDuplicar(gpus, nuevaGPU(nombre));
					}
				}
			}
			esperarProceso(proceso, segundos);
		} catch (Throwable ignorado) {
			destruirProceso(proceso);
		}

		return gpus;
	}

	private static BufferedReader lector(Process proceso) {
		return new BufferedReader(new InputStreamReader(proceso.getInputStream(), Charset.defaultCharset()));
	}

	private static void esperarProceso(Process proceso, long segundos) throws InterruptedException {
		if (proceso != null && !proceso.waitFor(segundos, TimeUnit.SECONDS)) {
			proceso.destroy();
			if (!proceso.waitFor(500L, TimeUnit.MILLISECONDS)) {
				proceso.destroyForcibly();
			}
		}
	}

	private static void destruirProceso(Process proceso) {
		if (proceso != null) {
			try {
				proceso.destroy();
			} catch (Throwable ignorado) {
				// No convertir una limpieza de proceso en un fallo del juego.
			}
		}
	}

	private static boolean contieneAlguna(String texto, String[] palabras) {
		for (String palabra : palabras) {
			if (texto.contains(palabra)) {
				return true;
			}
		}
		return false;
	}

	private static boolean pareceLineaGPU(String lower) {
		return lower.contains("nvidia") || lower.contains("geforce") || lower.contains("quadro")
				|| lower.contains("tesla") || lower.contains("amd") || lower.contains("ati") || lower.contains("radeon")
				|| lower.contains("intel") || lower.contains("apple") || lower.contains("matrox")
				|| lower.contains("moore threads") || lower.contains("mthreads") || lower.contains("jingjia")
				|| lower.contains("zhaoxin") || lower.contains("glenfly") || lower.contains("vga")
				|| lower.contains("3d controller") || lower.contains("display controller");
	}

	private static boolean pareceNombreGPU(String lower) {
		return pareceLineaGPU(lower) || lower.contains("graphics") || lower.contains("video controller");
	}

	private static String extraerNombrePCI(String linea) {
		int primerDosPuntos = linea.indexOf(':');
		if (primerDosPuntos >= 0 && primerDosPuntos + 1 < linea.length()) {
			int segundoDosPuntos = linea.indexOf(':', primerDosPuntos + 1);
			if (segundoDosPuntos >= 0 && segundoDosPuntos + 1 < linea.length()) {
				return linea.substring(segundoDosPuntos + 1).trim();
			}
			return linea.substring(primerDosPuntos + 1).trim();
		}
		return linea;
	}

	private static long convertirMemoriaMB(String texto) {
		try {
			String normalizado = texto.toUpperCase(Locale.ENGLISH).replace(',', '.');
			String[] partes = normalizado.split("\\s+");
			double valor = Double.parseDouble(partes[0]);
			if (normalizado.contains("GB")) {
				valor *= 1024.0;
			}
			return Math.round(valor);
		} catch (Throwable ignorado) {
			return 0L;
		}
	}

	private static InfoGPU nuevaGPU(String nombre) {
		InfoGPU gpu = new InfoGPU();
		gpu.nombre = nombre == null ? "Desconocido" : nombre.trim();
		clasificarGPU(gpu);
		return gpu;
	}

	private static void agregarSinDuplicar(List<InfoGPU> gpus, InfoGPU candidata) {
		if (candidata == null || candidata.nombre == null || candidata.nombre.trim().isEmpty()) {
			return;
		}

		String nombre = normalizarNombre(candidata.nombre);
		for (InfoGPU existente : gpus) {
			String otro = normalizarNombre(existente.nombre);
			if (nombre.equals(otro) || nombre.contains(otro) || otro.contains(nombre)) {
				if (existente.memoriaMB == 0L && candidata.memoriaMB > 0L) {
					existente.memoriaMB = candidata.memoriaMB;
				}
				return;
			}
		}

		gpus.add(candidata);
	}

	// ==========================================================
	// CLASIFICACIÓN
	// ==========================================================
	private static void clasificarGPU(InfoGPU info) {
		if (info == null || info.nombre == null) {
			return;
		}

		String lower = info.nombre.toLowerCase(Locale.ENGLISH);

		if (lower.contains("llvmpipe") || lower.contains("softpipe") || lower.contains("software rasterizer")) {
			info.tipo = "Renderizado por software";
			return;
		}

		if (lower.contains("tesla") || lower.contains("quadro") || lower.contains("rtx a")) {
			info.tipo = "Acelerador Gráfico";
			info.nombre = info.nombre.replace("NVIDIA", "Nvidia");
			return;
		}

		if (lower.contains("nvidia") || lower.contains("geforce") || lower.contains("rtx") || lower.contains("gtx")) {
			info.tipo = "Discreta (Alto Rendimiento)";
			info.nombre = info.nombre.replace("NVIDIA", "Nvidia");
			return;
		}

		if (lower.contains("radeon") || lower.contains("amd") || lower.contains("firepro") || lower.contains("ati")) {
			if (lower.contains("apu") || lower.contains("integrated") || lower.contains("integrada")) {
				info.tipo = "Integrada (AMD)";
			} else {
				info.tipo = "Discreta (Alto Rendimiento AMD)";
			}
			return;
		}

		if (lower.contains("intel")) {
			info.tipo = lower.contains("arc") ? "Discreta (Alto Rendimiento)" : "Integrada";
			return;
		}

		if (lower.contains("apple m") || lower.contains("apple gpu")) {
			info.tipo = "Integrada (Alto Rendimiento)";
			return;
		}

		if (lower.contains("matrox")) {
			boolean dedicada = lower.contains("parhelia") || lower.contains("millennium") || lower.contains("m912")
					|| lower.contains("orion") || lower.contains("apvi") || lower.contains("m-series");
			info.tipo = dedicada ? "Matrox_Dedicada" : "Matrox_Embebida";
			return;
		}

		if (lower.contains("mthreads") || lower.contains("moore threads")) {
			info.tipo = "Discreta (Nueva Generación)";
			return;
		}
		if (lower.contains("jingjia") || lower.contains("jm5")) {
			info.tipo = "Integrada";
			return;
		}
		if (lower.contains("zhaoxin") || lower.contains("glenfly")) {
			info.tipo = "Discreta (Nueva Generación)";
		}
	}

	// ==========================================================
	// ANÁLISIS FINAL
	// ==========================================================
	private static InfoGPU encontrarMejorGPU(List<InfoGPU> todas) {
		InfoGPU mejor = null;
		int mejorPuntuacion = Integer.MIN_VALUE;

		for (InfoGPU gpu : todas) {
			int puntuacion = puntuar(gpu);
			if (puntuacion > mejorPuntuacion) {
				mejor = gpu;
				mejorPuntuacion = puntuacion;
			}
		}

		return mejor;
	}

	private static boolean estaUsandoMejorGPU(String gpuActiva, List<InfoGPU> todas) {
		InfoGPU activa = nuevaGPU(gpuActiva);
		InfoGPU mejor = encontrarMejorGPU(todas);

		if (mejor == null) {
			return true;
		}

		String nombreActiva = normalizarNombre(gpuActiva);
		String nombreMejor = normalizarNombre(mejor.nombre);

		if (nombreActiva.contains(nombreMejor) || nombreMejor.contains(nombreActiva)) {
			return true;
		}

		int puntuacionActiva = puntuar(activa);
		int puntuacionMejor = puntuar(mejor);

		if (puntuacionActiva < puntuacionMejor) {
			CrashDetectorLogger
					.log("La GPU activa parece de menor categoría que el mejor hardware detectado: " + mejor.nombre);
			return false;
		}

		return true;
	}

	private static int puntuar(InfoGPU gpu) {
		if (gpu == null) {
			return Integer.MIN_VALUE;
		}

		String tipo = gpu.tipo == null ? "" : gpu.tipo;
		int puntos;

		if ("Acelerador Gráfico".equals(tipo)) {
			puntos = 100;
		} else if ("Discreta (Alto Rendimiento)".equals(tipo)) {
			puntos = 95;
		} else if ("Discreta (Alto Rendimiento AMD)".equals(tipo)) {
			puntos = 93;
		} else if ("Discreta (Nueva Generación)".equals(tipo)) {
			puntos = 90;
		} else if ("Integrada (Alto Rendimiento)".equals(tipo)) {
			puntos = 75;
		} else if ("Matrox_Dedicada".equals(tipo)) {
			puntos = 60;
		} else if ("Integrada (AMD)".equals(tipo)) {
			puntos = 45;
		} else if ("Integrada".equals(tipo)) {
			puntos = 40;
		} else if ("Matrox_Embebida".equals(tipo)) {
			puntos = 20;
		} else if ("Renderizado por software".equals(tipo)) {
			puntos = 0;
		} else {
			puntos = 30;
		}

		// La memoria sólo desempata; no pretende ser un benchmark real.
		if (gpu.memoriaMB > 0L) {
			puntos += (int) Math.min(10L, gpu.memoriaMB / 2048L);
		}
		return puntos;
	}

	private static String normalizarNombre(String nombre) {
		if (nombre == null) {
			return "";
		}
		return nombre.toLowerCase(Locale.ENGLISH).replace("nvidia", "").replace("amd", "")
				.replace("advanced micro devices", "").replace("corporation", "").replace("corp.", "")
				.replace("inc.", "").replaceAll("[^a-z0-9]+", " ").trim();
	}
}
