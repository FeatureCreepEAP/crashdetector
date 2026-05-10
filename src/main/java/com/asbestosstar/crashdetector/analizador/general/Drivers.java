package com.asbestosstar.crashdetector.analizador.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
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
			"The game failed to start because the currently installed" };

	// ==========================
	// NVIDIA (Windows + Linux)
	// ==========================

	private static final String[] DLLS_NVIDIA_WINDOWS = { "[nvapi64.dll", "[nvapi.dll", "[nvoglv64.dll",
			"[nvoglv32.dll", "[nvd3dumx.dll", "[nvd3dum.dll", "[nvwgf2umx.dll", "[nvwgf2um.dll", "[nvlddmkm.sys" };

	private static final String[] SOS_NVIDIA_UNIX = { "[libnvidia-glcore.so", "[libnvidia-glsi.so", "[libnvidia-tls.so",
			"[libGLX_nvidia.so", "[libnvidia-eglcore.so", "[libnvidia-rtcore.so", "[libnvidia-compiler.so",
			"[libnvidia-vulkan.so", "[libnvidia-gpucomp.so" };

	private static final String[] PATRONES_NOUVEAU_LITERAL = { "libnouveau.so", "libdrm_nouveau.so", "nouveau" };

	private static final boolean ES_WINDOWS = System.getProperty("os.name", "").toLowerCase().contains("windows");
	private static Boolean TIENE_NVIDIA_CACHE = null;

	// ==========================
	// Matrox (Windows + Unix)
	// ==========================

	private static final String[] DLLS_MATROX_WINDOWS = { "[mgag200.dll", "[mgag400.dll", "[matrox.dll" };

	private static final String[] SOS_MATROX_UNIX = { "[mga_dri.so", "[mga_drv.so", "[libdrm_mga.so", "[libmga.so" };

	@Override
	public void verificar(Consola consola) {
		String log = consola.contenido_verificar;

		if (log == null || log.isEmpty()) {
			return;
		}

		// 1) Sodium: mensaje propio de controlador incompatible
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

		// 3) AMD
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

		// 4) NVIDIA
		if (log.contains("EXCEPTION_ACCESS_VIOLATION") && contienePatronNvidia(log)) {
			procesarProblemaGraficos();
			CrashDetectorLogger.log("NVIDIA Driver Error");
			return;
		}

		// 5) Matrox
		if (log.contains("EXCEPTION_ACCESS_VIOLATION") && contienePatronMatrox(log)) {
			mensajes.append(MonitorDePID.idioma.problema_con_graficas_matrox());
			activado = true;
			CrashDetectorLogger.log("Matrox Driver Error");
			return;
		}

		// 6) Otras DLLs
		if (contienePatron(log, new String[] { "[PhysX_64.dll", "[glfw.dll" })
				&& log.contains("EXCEPTION_ACCESS_VIOLATION")) {
			procesarProblemaGraficos();
			CrashDetectorLogger.log("PhysX o glfw error Drivers");
			return;
		}

		// 7) Intel
		verificarProblemasIntel(log);
		if (activado) {
			return;
		}

		// 8) GPU no compatible
		if (contienePatron(log, UNSUPPORTED_GPU_PATTERNS)) {
			procesarGpuNoCompatible();
			CrashDetectorLogger.log("Driver GPU No Compat");
			return;
		}

		// 9) Patrones genéricos
		if (contienePatron(log, DRIVER_PATTERNS)) {
			CrashDetectorLogger.log("DRIVER PATTERNS Driver Error");
			procesarProblemaGraficos();
			return;
		}

		// 10) Última línea con pistas
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

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// No se usa en este verificador: requiere el log completo para decidir.
	}

	private boolean esProblemaSodiumDrivers(String log) {
		String low = log.toLowerCase();

		boolean frase = low.contains("the game failed to start because the currently installed");
		boolean marca = low.contains("sodium") || low.contains("caffeinemc.net") || low.contains("link.caffeinemc.net");

		return frase && marca;
	}

	private boolean contieneLineaNativaAmd(String log) {
		String low = log.toLowerCase();

		return low.contains("c  [atio6axx.dll+") || low.contains("c  [atioglxx.dll+")
				|| low.contains("# c  [atio6axx.dll+") || low.contains("# c  [atioglxx.dll+");
	}

	private boolean contienePatronNouveau(String log) {
		String low = log.toLowerCase();

		boolean encontradoLiteral = false;

		for (String patronLiteral : PATRONES_NOUVEAU_LITERAL) {
			if (low.contains(patronLiteral)) {
				encontradoLiteral = true;
				break;
			}
		}

		if (!encontradoLiteral) {
			return contienePatronOpenAL(log);
		}

		// Confirmar libnouveau.so o libdrm_nouveau.so sin regex.
		if (contieneBibliotecaSoConVersionOpcional(low, "libnouveau.so")
				|| contieneBibliotecaSoConVersionOpcional(low, "libdrm_nouveau.so")) {
			return true;
		}

		return contienePatronOpenAL(log);
	}

	/**
	 * Detecta nombres tipo: libnouveau.so libnouveau.so.0 libnouveau.so.0.0.0
	 *
	 * Sin usar regex.
	 */
	private boolean contieneBibliotecaSoConVersionOpcional(String texto, String nombreBase) {
		int indice = texto.indexOf(nombreBase);

		while (indice >= 0) {
			int despues = indice + nombreBase.length();

			if (despues >= texto.length()) {
				return true;
			}

			char c = texto.charAt(despues);

			if (Character.isWhitespace(c) || c == ']' || c == ')' || c == ',' || c == ';' || c == ':') {
				return true;
			}

			if (c == '.') {
				if (esVersionDespuesDePunto(texto, despues)) {
					return true;
				}
			}

			indice = texto.indexOf(nombreBase, indice + nombreBase.length());
		}

		return false;
	}

	/**
	 * Valida sufijo tipo ".1", ".1.2", ".1.2.3".
	 */
	private boolean esVersionDespuesDePunto(String texto, int indicePunto) {
		int i = indicePunto;

		while (i < texto.length()) {
			if (texto.charAt(i) != '.') {
				break;
			}

			i++;

			if (i >= texto.length() || !Character.isDigit(texto.charAt(i))) {
				return false;
			}

			while (i < texto.length() && Character.isDigit(texto.charAt(i))) {
				i++;
			}
		}

		if (i >= texto.length()) {
			return true;
		}

		char fin = texto.charAt(i);

		return Character.isWhitespace(fin) || fin == ']' || fin == ')' || fin == ',' || fin == ';' || fin == ':';
	}

	private boolean contienePatronNvidia(String log) {
		for (String dll : DLLS_NVIDIA_WINDOWS) {
			if (log.contains(dll)) {
				return true;
			}
		}

		for (String so : SOS_NVIDIA_UNIX) {
			if (log.contains(so)) {
				return true;
			}
		}

		return false;
	}

	private boolean contienePatronOpenAL(String log) {
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

	private boolean contienePatronMatrox(String log) {
		for (String dll : DLLS_MATROX_WINDOWS) {
			if (log.contains(dll)) {
				return true;
			}
		}

		for (String so : SOS_MATROX_UNIX) {
			if (log.contains(so)) {
				return true;
			}
		}

		return false;
	}

	private void procesarProblemaNouveau() {
		boolean tieneNvidia = false;

		try {
			Process process = Runtime.getRuntime().exec(new String[] { "sh", "-c", "lspci | grep -i vga" });

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				String linea;

				while ((linea = reader.readLine()) != null) {
					String lineaBaja = linea.toLowerCase();

					if (lineaBaja.contains("nvidia") || lineaBaja.contains("rtx") || lineaBaja.contains("gtx")
							|| lineaBaja.contains("gt") || lineaBaja.contains("titan") || lineaBaja.contains("quadro")
							|| lineaBaja.contains("tesla") || lineaBaja.contains("ampere")) {
						tieneNvidia = true;
						break;
					}
				}
			}

			int salir = process.waitFor();

			if (salir != 0) {
				tieneNvidia = true;
			}
		} catch (Exception e) {
			tieneNvidia = true;
		}

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

		int[] version = parsearVersionWindows(System.getProperty("os.version", ""));

		if (version.length < 2) {
			return false;
		}

		int mayor = version[0];
		int menor = version[1];
		int build = version.length > 2 ? version[2] : 0;

		return mayor == 10 && menor == 0 && build >= 22000;
	}

	/**
	 * Convierte una version tipo "10.0.22631" a enteros sin usar split("\\.").
	 */
	private int[] parsearVersionWindows(String texto) {
		if (texto == null || texto.isEmpty()) {
			return new int[0];
		}

		int[] partes = new int[4];
		int cantidad = 0;
		int valor = 0;
		boolean leyendoNumero = false;

		for (int i = 0; i < texto.length() && cantidad < partes.length; i++) {
			char c = texto.charAt(i);

			if (Character.isDigit(c)) {
				valor = (valor * 10) + (c - '0');
				leyendoNumero = true;
			} else if (c == '.') {
				if (leyendoNumero) {
					partes[cantidad++] = valor;
					valor = 0;
					leyendoNumero = false;
				} else {
					return new int[0];
				}
			} else {
				break;
			}
		}

		if (leyendoNumero && cantidad < partes.length) {
			partes[cantidad++] = valor;
		}

		int[] resultado = new int[cantidad];

		for (int i = 0; i < cantidad; i++) {
			resultado[i] = partes[i];
		}

		return resultado;
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
		return mensajes.toString().replace("\n", Verificaciones.nl_html);
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_drivers();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "drivers";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}