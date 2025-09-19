package com.asbestosstar.crashdetector.analizador.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Analizador dedicado a detectar problemas relacionados con controladores
 * (drivers) de vídeo/OpenGL. La clase reconoce firmas de «Bad video drivers» y,
 * ahora, mantiene un conjunto separado de patrones que indican explícitamente
 * una GPU no compatible (unsupported/old videocard).
 */
public class Drivers implements Verificaciones {

	/** Indica si este verificador identificó un problema. */
	private boolean activado = false;
	private final CDStringBuilder mensajes = new CDStringBuilder();

	/**
	 * Patrones genéricos de fallos de driver/ OpenGL. Desde signatures.json de
	 * TLauncher
	 */
	private static final String[] DRIVER_PATTERNS = {
			// Mensajes LWJGL / GLFW
			"Pixel format not accelerated", "The driver does not appear to support OpenGL", "GLFW error 65542", // WGL:
																												// driver
																												// sin
																												// soporte
			"GLFW error 65543", // "OpenGL profile requested but …"
			"GLFW error 1282", // ResourcePack problema, TODO mas mejor
			"No context is current or a function that is not available in the current context", // TODO hacer un
																								// verificacion dedicado
																								// para situaciones
																								// similar a
																								// https://discord.com/channels/1129059589325852724/1129069799545241703/1418053692384346183
			"The driver does not appear to support framebuffer objects",
			// Excepciones típicas
			"org.lwjgl.LWJGLException"
			// DLLs Intel / AMD / Mesa que suelen fallar
			// Otros
	};

	private static final String[] PATERNS_LINEA_ULTIMA = {
			// Mensajes LWJGL / GLFW
			"If this message is the only thing at the bottom of your log before a crash, you probably have a driver issue.",
			"You can safely ignore this message if the game starts up successfully.", "Trying GL version"

			// Otros
	};

	/**
	 * Patrones que indican explícitamente que la GPU es demasiado antigua o no
	 * soporta las características requeridas. Se maneja por separado para ofrecer
	 * un mensaje más claro al usuario.
	 */
	private static final String[] UNSUPPORTED_GPU_PATTERNS = { "old-videocard", "unsupported by videocard",
			"Your video card does not meet the requirements", "need to purchase a newer video card",
			"videocard is too old", "does not support OpenGL 3", "OpenGL unsupported by videocard",
			"The game failed to start because the currently installed"// para sodium
	};

	@Override
	public void verificar(Consola consola) {
		String log = consola.contenido_verificar;

		// Verificar si hay errores relacionados con Nouveau (controlador de NVIDIA
		// open-source)
		if (contienePatronNouveau(log)) {
			procesarProblemaNouveau();
			return;
		}

		if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("[atio6axx.dll]")) {
			procesarProblemaAMD();
			return;
		}
		if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("[atioglxx.dll]")) {
			procesarProblemaAMD();
			return;
		}

		if (contienePatron(log, new String[] { "[PhysX_64.dll]", "[glfw.dll]" })
				&& log.contains("EXCEPTION_ACCESS_VIOLATION")) {
			procesarProblemaGraficos();
			return;
		}

		verificarProblemasIntel(log);
		if (activado)
			return;

		if (contienePatron(log, UNSUPPORTED_GPU_PATTERNS)) {
			procesarGpuNoCompatible();
			return;
		}

		if (contienePatron(log, DRIVER_PATTERNS)) {
			procesarProblemaGraficos();
			return;
		}

		// todo"Cocoa: Failed to find service port for display"

		String ultimaLinea = obtenerUltimaLinea(log);
		if (ultimaLinea != null) {
			CrashDetectorLogger.log(ultimaLinea);
			if (contienePatron(ultimaLinea, PATERNS_LINEA_ULTIMA)) {
				procesarProblemaGraficos();
			} else if (contienePatron(ultimaLinea, UNSUPPORTED_GPU_PATTERNS)) {
				procesarGpuNoCompatible();
			}
		}
	}

	/**
	 * Verifica si el log contiene referencias a bibliotecas Nouveau Maneja
	 * múltiples formatos posibles: - libnouveau.so - libnouveau.so.2 -
	 * libnouveau.so.2.0.0 - libdrm_nouveau.so - libdrm_nouveau.so.2 -
	 * libdrm_nouveau.so.2.0.0 - Cualquier otro archivo .so que contenga "nouveau"
	 * en su nombre
	 */
	private boolean contienePatronNouveau(String log) {
		// Patrones de bibliotecas Nouveau comunes en Linux
		String[] patronesNouveau = { "\\[libnouveau\\.so\\]", "\\[libnouveau\\.so\\.\\d+\\]",
				"\\[libnouveau\\.so\\.\\d+\\.\\d+\\]", "\\[libnouveau\\.so\\.\\d+\\.\\d+\\.\\d+\\]",
				"\\[libdrm_nouveau\\.so\\]", "\\[libdrm_nouveau\\.so\\.\\d+\\]",
				"\\[libdrm_nouveau\\.so\\.\\d+\\.\\d+\\]", "\\[libdrm_nouveau\\.so\\.\\d+\\.\\d+\\.\\d+\\]",
				"\\[nouveau\\]", "\\[.*nouveau.*\\.so\\]" // Patrón para detectar cualquier archivo .so que contenga
															// "nouveau"
		};

		for (String patron : patronesNouveau) {
			if (Pattern.compile(patron).matcher(log).find()) {
				CrashDetectorLogger.log("Patrón Nouveau encontrado: " + patron);
				return true;
			}
		}
		return false;
	}

	private void procesarProblemaGraficos() {
		boolean esWindows = esWindows();
		boolean tieneNvidia = esWindows && tieneNvidiaGPU();
		boolean esWindowsNuevo = esWindows && esWindows11OServer2025();

		if (tieneNvidia) {
			mensajes.append(esWindowsNuevo ? MonitorDePID.idioma.problema_con_graficas_nvidia_windows_nuevo()
					: MonitorDePID.idioma.problema_con_graficas_nvidia_windows_viejo());
		} else {
			mensajes.append(MonitorDePID.idioma.problema_con_graficas_general());
		}
		activado = true;
	}

	private void procesarGpuNoCompatible() {
		mensajes.append(MonitorDePID.idioma.gpu_no_compatible());
		activado = true;
	}

	private void procesarProblemaAMD() {
		mensajes.append(MonitorDePID.idioma.problema_con_graficas_ati());
		activado = true;
	}

	private void procesarProblemaNouveau() {
		mensajes.append(MonitorDePID.idioma.problema_con_graficas_nouveau());
		activado = true;
	}

	private boolean contienePatron(String texto, String[] patrones) {
		for (String p : patrones) {
			if (texto.contains(p)) {
				CrashDetectorLogger.log("Hay patron de Drivers " + p);
				return true;
			}
		}
		return false;
	}

	private String obtenerUltimaLinea(String log) {
		String[] lineas = log.split(nl);
		return lineas.length > 0 ? lineas[lineas.length - 1] : null;
	}

	private boolean esWindows() {
		return System.getProperty("os.name").toLowerCase().contains("windows");
	}

	private boolean tieneNvidiaGPU() {
		if (!esWindows())
			return false;
		try {
			Process p = Runtime.getRuntime().exec("wmic path win32_VideoController get name");
			try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				String l;
				while ((l = br.readLine()) != null) {
					if (l.toLowerCase().contains("nvidia"))
						return true;
				}
			}
		} catch (IOException e) {
			// Silenciar: simplemente devolvemos false.
		}
		return false;
	}

	private boolean esWindows11OServer2025() {
		if (!esWindows())
			return false;
		String[] v = System.getProperty("os.version").split("\\.");
		try {
			int build = v.length > 2 ? Integer.parseInt(v[2]) : 0;
			return "10".equals(v[0]) && "0".equals(v[1]) && build >= 22000;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	// En la clase Drivers (analizador)
	private void verificarProblemasIntel(String log) {
		String[] dllsIntel = { "[ig7icd32.dll]", "[ig7icd64.dll]", "[ig75icd32.dll]", "[ig75icd64.dll]",
				"[ig8icd64.dll]", "[ig9icd32.dll]", "[ig9icd64.dll]" };// https://tlauncher.org/en/ig9icd32-dll-error.html
																		// TODO para intel

		for (String dll : dllsIntel) {
			if (log.contains(dll) && log.contains("EXCEPTION_ACCESS_VIOLATION")) {
				procesarProblemaIntel();
				return;
			}
		}
	}

	private void procesarProblemaIntel() {
		mensajes.append(MonitorDePID.idioma.problema_con_graficas_intel());
		activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new Drivers();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f; // Prioridad crítica para errores de drivers [[8]]
	}

	@Override
	public String mensaje() {
		return mensajes.toString().replaceAll("\n", Verificaciones.nl_html);
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_drivers();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}

//  private String obtenerVersionControladorIntel() {
//  if (!esWindows()) return null;
//  
//  try {
//      Process p = Runtime.getRuntime().exec(
//          "wmic path win32_VideoController where \"Name like '%Intel%'\" get DriverVersion /value"
//      );
//      
//      try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
//          String linea;
//          String ultimaVersion = null;
//          while ((linea = br.readLine()) != null) {
//              if (linea.startsWith("DriverVersion=")) {
//                  String version = linea.split("=")[1].trim();
//                  if (version.compareTo(ultimaVersion) > 0) {
//                      ultimaVersion = version;
//                  }
//              }
//          }
//        return ultimaVersion;
//  }
//} catch (IOException e) {
//  return null;
//}
//}
//
//private boolean esVersionIntelObsoleta(String version) {
//String[] partes = version.split("\\.");
//if (partes.length < 1) return true;
//
//try {
//  int major = Integer.parseInt(partes[0]);
//  return major < 15;
//} catch (NumberFormatException e) {
//  return true;
//}
//}

	// TODO
	/**
	 * 
	 * 
	 * [08:33:15] [Render thread/ERROR]: The game failed to start because the
	 * currently installed NVIDIA Graphics Driver is not compatible.
	 * 
	 * Installed version: 528.92 Required version: 536.23 (or newer)
	 * 
	 * Please click the 'Help' button to read more about how to fix this problem.
	 * 
	 * For more information, please see:
	 * https://link.caffeinemc.net/help/sodium/graphics-driver/windows/nvidia/gh-1486
	 * [08:33:46] [ForkJoinPool.commonPool-worker-1/WARN]: [Iris Update Check] This
	 * version doesn't have an update index, skipping.
	 * 
	 */

}