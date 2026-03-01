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
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

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

	/**
	 * Patrones de nouveau precompilados para evitar recompilar en cada
	 * verificación.
	 */
	private static final String[] PATRONES_NOUVEAU_LITERAL = { "libnouveau.so", "libdrm_nouveau.so", "nouveau" };

	/**
	 * Cache de plataforma y GPU para no repetir detecciones costosas.
	 */
	private static final boolean ES_WINDOWS = System.getProperty("os.name", "").toLowerCase().contains("windows");
	private static Boolean TIENE_NVIDIA_CACHE = null;

	@Override
	public void verificar(Consola consola) {
		String log = consola.contenido_verificar;

		// 1) Sodium: mensaje propio de controlador incompatible -> mensaje diferenciado
		if (esProblemaSodiumDrivers(log)) {
			procesarProblemaSodiumDrivers();
			activado = true;
			CrashDetectorLogger.log("Sodium Driver Error");
			return;
		}

		// 2) Nouveau
		if (contienePatronNouveau(log)) {
			procesarProblemaNouveau();
			CrashDetectorLogger.log("Nouveau Driver Error");

			if (contienePatronOpenAL(log)) {
				CrashDetectorLogger.log("OpenAL Driver Error");
				procesarProblemaOpenAL();
			}

			return;
		}

		// 3) AMD: además de los casos con EXCEPTION_ACCESS_VIOLATION, detectar hs_err:
		// "# C [atio6axx.dll+...]" o "# C [atioglxx.dll+...]"
		if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("[atio6axx.dll")) {
			procesarProblemaAMD();
			CrashDetectorLogger.log("Driver atio6axx error");
			return;
		}
		if (log.contains("EXCEPTION_ACCESS_VIOLATION") && log.contains("[atioglxx.dll")) {
			procesarProblemaAMD();
			CrashDetectorLogger.log("Sodium Driver Error");

			return;
		}
		if (contieneLineaNativaAmd(log)) {
			procesarProblemaAMD();
			CrashDetectorLogger.log("Nativa AMD Driver Error");
			return;
		}

		// 4) Otras DLLs
		if (contienePatron(log, new String[] { "[PhysX_64.dll", "[glfw.dll" })
				&& log.contains("EXCEPTION_ACCESS_VIOLATION")) {
			procesarProblemaGraficos();
			CrashDetectorLogger.log("PhysX o glfw error Drivers");
			return;
		}

		// 5) Intel
		verificarProblemasIntel(log);
		if (activado)
			return;

		// 6) GPU no compatible
		if (contienePatron(log, UNSUPPORTED_GPU_PATTERNS)) {
			procesarGpuNoCompatible();
			CrashDetectorLogger.log("Driver GPU No Compat");
			return;
		}

		// 7) Patrones genéricos
		if (contienePatron(log, DRIVER_PATTERNS)) {
			CrashDetectorLogger.log("DRIVER PATTERNS Driver Error");
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

	/**
	 * Verificación por línea.
	 * <p>
	 * En este verificador no se hace análisis por línea porque todas las
	 * heurísticas necesitan el contexto completo del log (hs_err, última línea,
	 * presencia de varias DLL, etc.). Se deja implementado vacío para cumplir con
	 * la interfaz y mantener un punto de extensión futuro si hiciera falta añadir
	 * detecciones ligeras por línea.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// No se usa en este verificador: requiere el log completo para decidir.
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
		// Búsqueda rápida con cadenas literales antes de usar expresiones regulares
		String low = log.toLowerCase();

		boolean encontradoLiteral = false;
		for (String patronLiteral : PATRONES_NOUVEAU_LITERAL) {
			if (low.contains(patronLiteral)) {
				encontradoLiteral = true;
				break;
			}
		}

		// Si no se encontró ninguno de los patrones literales, verificamos OpenAL
		if (!encontradoLiteral) {
			return contienePatronOpenAL(log); // OpenAL también puede indicar nouveau en algunos casos
		}

		// Si se encontró un patrón literal, ahora verificamos con expresiones regulares
		// más específicas
		// para confirmar que es un patrón completo (por ejemplo, con números de
		// versión)
		if (log.contains("nouveau") && (log.matches(".*libnouveau\\.so(\\.\\d+)*\\s*")
				|| log.matches(".*libdrm_nouveau\\.so(\\.\\d+)*\\s*"))) {
			return true;
		}

		// A veces nouveau puede causar problemas con OpenAL en mi experiencia
		return contienePatronOpenAL(log);
	}

	private boolean contienePatronOpenAL(String log) {
		// Verificamos si "libopenal.so" está presente dentro de los corchetes
		if (log.contains("[libopenal.so+")) {
			CrashDetectorLogger.log("Patrón Nouveau encontrado: libopenal");
			return true;
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
		boolean tieneNvidia = false;

		try {
			// Ejecutar el comando a través de un shell para manejar el pipe
			Process process = Runtime.getRuntime().exec(new String[] { "sh", "-c", "lspci | grep -i vga" });
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String linea;
				while ((linea = reader.readLine()) != null) {
					String lineaBaja = linea.toLowerCase();
					// Verificar identificadores de tarjetas NVIDIA
					if (lineaBaja.contains("nvidia") || lineaBaja.contains("rtx") || lineaBaja.contains("gtx")
							|| lineaBaja.contains("gt") || lineaBaja.contains("titan") || lineaBaja.contains("quadro")
							|| lineaBaja.contains("tesla") || lineaBaja.contains("ampere")) {
						tieneNvidia = true;
						break;
					}
				}
			}

			// Esperar a que el proceso termine
			int salir = process.waitFor();
			if (salir != 0) {
				// Si el comando falla, asumir que hay una tarjeta NVIDIA
				tieneNvidia = true;
			}
		} catch (Exception e) {
			// Si el comando falla o no se puede ejecutar, asumir que hay una tarjeta NVIDIA
			tieneNvidia = true;
		}

		// Si se detecta una GPU NVIDIA (o el comando falló), mostrar el mensaje
		if (tieneNvidia) {
			mensajes.append(MonitorDePID.idioma.problema_con_graficas_nouveau());
			activado = true;
		}
	}

	private void procesarProblemaOpenAL() {
		mensajes.append(nl);
		mensajes.append(MonitorDePID.idioma.problema_con_openAL());
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
		if (log == null || log.isEmpty()) {
			return null;
		}

		String[] lineas = log.split(Verificaciones.nl);
		return lineas.length > 0 ? lineas[lineas.length - 1] : null;
	}

	private boolean esWindows() {
		return ES_WINDOWS;
	}

	private boolean tieneNvidiaGPU() {
		if (!ES_WINDOWS) {
			return false;
		}
		// Cachear el resultado: ejecutar wmic sólo la primera vez
		if (TIENE_NVIDIA_CACHE != null) {
			return TIENE_NVIDIA_CACHE;
		}
		boolean tiene = false;
		try {
			Process p = Runtime.getRuntime().exec("wmic path win32_VideoController get name");
			try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				String l;
				while ((l = br.readLine()) != null) {
					if (l.toLowerCase().contains("nvidia")) {
						tiene = true;
						break;
					}
				}
			}
		} catch (IOException ignored) {
		}
		TIENE_NVIDIA_CACHE = tiene;
		return tiene;
	}

	private boolean esWindows11OServer2025() {
		if (!ES_WINDOWS) {
			return false;
		}
		String[] v = System.getProperty("os.version", "").split("\\.");
		try {
			int build = v.length > 2 ? Integer.parseInt(v[2]) : 0;
			return "10".equals(v[0]) && "0".equals(v[1]) && build >= 22000;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	private void verificarProblemasIntel(String log) {
		String[] dllsIntel = { "[ig7icd32.dll", "[ig7icd64.dll", "[ig75icd32.dll", "[ig75icd64.dll", "[ig8icd64.dll",
				"[ig9icd32.dll", "[ig9icd64.dll", "[igxelpicd64" };
		for (String dll : dllsIntel) {
			if (log.contains(dll) && log.contains("EXCEPTION_ACCESS_VIOLATION")) {
				procesarProblemaIntel();
				CrashDetectorLogger.log(dll + " Driver Error");
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
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "drivers";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}
