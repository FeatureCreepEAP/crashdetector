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

public class Drivers implements Verificaciones {

	private boolean activado = false;
	private final CDStringBuilder mensajes = new CDStringBuilder();

	private static final String[] DRIVER_PATTERNS = { "Pixel format not accelerated",
			"The driver does not appear to support OpenGL", "GLFW error 65542", "GLFW error 65543", "GLFW error 1282",
			"No context is current or a function that is not available in the current context",
			"The driver does not appear to support framebuffer objects", "org.lwjgl.LWJGLException" };

	private static final String[] PATERNS_LINEA_ULTIMA = {
			"If this message is the only thing at the bottom of your log before a crash, you probably have a driver issue.",
			"You can safely ignore this message if the game starts up successfully.", "Trying GL version" };

	private static final String[] UNSUPPORTED_GPU_PATTERNS = { "old-videocard", "unsupported by videocard",
			"Your video card does not meet the requirements", "need to purchase a newer video card",
			"videocard is too old", "does not support OpenGL 3", "OpenGL unsupported by videocard",
			"The game failed to start because the currently installed" // Sodium a veces muestra esto
	};

	@Override
	public void verificar(Consola consola) {
		String log = consola.contenido_verificar;

		// 1) Sodium: mensaje propio de controlador incompatible -> mensaje diferenciado
		if (esProblemaSodiumDrivers(log)) {
			procesarProblemaSodiumDrivers();
			activado = true;
			return;
		}

		// 2) Nouveau
		if (contienePatronNouveau(log)) {
			procesarProblemaNouveau();
			return;
		}

		// 3) AMD: además de los casos con EXCEPTION_ACCESS_VIOLATION, detectar hs_err:
		// "# C [atio6axx.dll+...]" o "# C [atioglxx.dll+...]"
		if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("[atio6axx.dll")) {
			procesarProblemaAMD();
			return;
		}
		if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("[atioglxx.dll")) {
			procesarProblemaAMD();
			return;
		}
		if (contieneLineaNativaAmd(log)) {
			procesarProblemaAMD();
			return;
		}

		// 4) Otras DLLs
		if (contienePatron(log, new String[] { "[PhysX_64.dll", "[glfw.dll" })
				&& log.contains("EXCEPTION_ACCESS_VIOLATION")) {
			procesarProblemaGraficos();
			return;
		}

		// 5) Intel
		verificarProblemasIntel(log);
		if (activado)
			return;

		// 6) GPU no compatible
		if (contienePatron(log, UNSUPPORTED_GPU_PATTERNS)) {
			procesarGpuNoCompatible();
			return;
		}

		// 7) Patrones genéricos
		if (contienePatron(log, DRIVER_PATTERNS)) {
			procesarProblemaGraficos();
			return;
		}

		// 8) Última línea con pistas
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

	// Detecta el aviso propio de Sodium sobre controlador incompatible
	private boolean esProblemaSodiumDrivers(String log) {
		String low = log.toLowerCase();
		// Frase clave + referencia a Sodium/CaffeineMC
		boolean frase = low.contains("the game failed to start because the currently installed");
		boolean marca = low.contains("sodium") || low.contains("caffeinemc.net") || low.contains("link.caffeinemc.net");
		return frase && marca;
	}

	// Detecta líneas hs_err_pid del tipo "# C [atio6axx.dll+0x...]" o atioglxx
	private boolean contieneLineaNativaAmd(String log) {
		String low = log.toLowerCase();
		// Permite espacios variables antes del corchete
		return (low.contains("c  [atio6axx.dll+") || low.contains("c  [atioglxx.dll+")
				|| low.contains("# c  [atio6axx.dll+") || low.contains("# c  [atioglxx.dll+"));
	}

	private boolean contienePatronNouveau(String log) {
		String[] patronesNouveau = { "\\[libnouveau\\.so\\]", "\\[libnouveau\\.so\\.\\d+\\]",
				"\\[libnouveau\\.so\\.\\d+\\.\\d+\\]", "\\[libnouveau\\.so\\.\\d+\\.\\d+\\.\\d+\\]",
				"\\[libdrm_nouveau\\.so\\]", "\\[libdrm_nouveau\\.so\\.\\d+\\]",
				"\\[libdrm_nouveau\\.so\\.\\d+\\.\\d+\\]", "\\[libdrm_nouveau\\.so\\.\\d+\\.\\d+\\.\\d+\\]",
				"\\[nouveau\\]", "\\[.*nouveau.*\\.so\\]" };
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

	private void procesarProblemaSodiumDrivers() {
		// Mensaje específico para el caso de Sodium
		mensajes.append(MonitorDePID.idioma.problema_con_graficas_sodium());
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
		} catch (IOException ignored) {
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

	private void verificarProblemasIntel(String log) {
		String[] dllsIntel = { "[ig7icd32.dll]", "[ig7icd64.dll]", "[ig75icd32.dll]", "[ig75icd64.dll]",
				"[ig8icd64.dll]", "[ig9icd32.dll]", "[ig9icd64.dll]" };
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
		return 900.0f;
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
	
	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "drivers";
	}
}
