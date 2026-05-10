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

	private String enlaceUnico = "";

	// Señales globales baratas
	private boolean hayAccessViolation = false;
	private boolean posibleSodium = false;
	private boolean posibleOpenAL = false;

	// Evita agregar el mismo mensaje varias veces si se analizan varios logs
	private boolean mensajeGraficosAgregado = false;
	private boolean mensajeSodiumAgregado = false;
	private boolean mensajeNouveauAgregado = false;
	private boolean mensajeOpenALAgregado = false;
	private boolean mensajeAMDAgregado = false;
	private boolean mensajeIntelAgregado = false;
	private boolean mensajeMatroxAgregado = false;
	private boolean mensajeGpuNoCompatibleAgregado = false;

	private static final String[] DRIVER_PATTERNS = { "Pixel format not accelerated",
			"The driver does not appear to support OpenGL", "GLFW error 65542", "GLFW error 65543", "GLFW error 1282",
			"No context is current or a function that is not available in the current context",
			"The driver does not appear to support framebuffer objects", "org.lwjgl.LWJGLException" };

	private static final String[] PATRONES_LINEA_ULTIMA = {
			"If this message is the only thing at the bottom of your log before a crash, you probably have a driver issue.",
			"You can safely ignore this message if the game starts up successfully.", "Trying GL version" };

	private static final String[] UNSUPPORTED_GPU_PATTERNS = { "old-videocard", "unsupported by videocard",
			"Your video card does not meet the requirements", "need to purchase a newer video card",
			"videocard is too old", "does not support OpenGL 3", "OpenGL unsupported by videocard",
			"The game failed to start because the currently installed" };

	// ==========================
	// NVIDIA
	// ==========================

	private static final String[] DLLS_NVIDIA_WINDOWS = { "[nvapi64.dll", "[nvapi.dll", "[nvoglv64.dll",
			"[nvoglv32.dll", "[nvd3dumx.dll", "[nvd3dum.dll", "[nvwgf2umx.dll", "[nvwgf2um.dll", "[nvlddmkm.sys" };

	private static final String[] SOS_NVIDIA_UNIX = { "[libnvidia-glcore.so", "[libnvidia-glsi.so", "[libnvidia-tls.so",
			"[libGLX_nvidia.so", "[libnvidia-eglcore.so", "[libnvidia-rtcore.so", "[libnvidia-compiler.so",
			"[libnvidia-vulkan.so", "[libnvidia-gpucomp.so" };

	private static final String[] PATRONES_NOUVEAU_LITERAL = { "libnouveau.so", "libdrm_nouveau.so", "nouveau" };

	// ==========================
	// Matrox
	// ==========================

	private static final String[] DLLS_MATROX_WINDOWS = { "[mgag200.dll", "[mgag400.dll", "[matrox.dll" };

	private static final String[] SOS_MATROX_UNIX = { "[mga_dri.so", "[mga_drv.so", "[libdrm_mga.so", "[libmga.so" };

	// ==========================
	// Intel
	// ==========================

	private static final String[] DLLS_INTEL = { "[ig7icd32.dll", "[ig7icd64.dll", "[ig75icd32.dll", "[ig75icd64.dll",
			"[ig8icd64.dll", "[ig9icd32.dll", "[ig9icd64.dll", "[igxelpicd64" };

	private static final boolean ES_WINDOWS = System.getProperty("os.name", "").toLowerCase().contains("windows");

	private static Boolean TIENE_NVIDIA_CACHE = null;

	/**
	 * Verificación global ligera.
	 *
	 * No usa regex. No convierte todo el log a minúsculas. Solo marca señales muy
	 * baratas que ayudan a la verificación por línea.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String log = consola.contenido_verificar;

		hayAccessViolation = log.contains("EXCEPTION_ACCESS_VIOLATION");
		posibleSodium = log.contains("Sodium") || log.contains("sodium") || log.contains("caffeinemc.net")
				|| log.contains("link.caffeinemc.net");
		posibleOpenAL = log.contains("[libopenal.so+");

		verificarUltimaLinea(consola, log);
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (linea == null || linea.isEmpty())
			return;

		// Sodium
		if (posibleSodium && linea.contains("The game failed to start because the currently installed")) {
			procesarProblemaSodiumDrivers(consola, numero_de_linea);
			return;
		}

		// Nouveau / OpenAL
		if (esLineaNouveau(linea)) {
			procesarProblemaNouveau(consola, numero_de_linea);
			if (posibleOpenAL)
				procesarProblemaOpenAL(consola, numero_de_linea);
			return;
		}

		if (posibleOpenAL && linea.contains("[libopenal.so+")) {
			procesarProblemaOpenAL(consola, numero_de_linea);
			return;
		}

		// AMD
		if (hayAccessViolation && esLineaAMD(linea)) {
			procesarProblemaAMD(consola, numero_de_linea);
			return;
		}

		// NVIDIA
		if (hayAccessViolation
				&& (contienePatron(linea, DLLS_NVIDIA_WINDOWS) || contienePatron(linea, SOS_NVIDIA_UNIX))) {
			procesarProblemaGraficos(consola, numero_de_linea);
			return;
		}

		// Matrox
		if (hayAccessViolation
				&& (contienePatron(linea, DLLS_MATROX_WINDOWS) || contienePatron(linea, SOS_MATROX_UNIX))) {
			procesarProblemaMatrox(consola, numero_de_linea);
			return;
		}

		// PhysX / GLFW
		if (hayAccessViolation && (linea.contains("[PhysX_64.dll") || linea.contains("[glfw.dll"))) {
			procesarProblemaGraficos(consola, numero_de_linea);
			return;
		}

		// Intel
		if (hayAccessViolation && contienePatron(linea, DLLS_INTEL)) {
			procesarProblemaIntel(consola, numero_de_linea);
			return;
		}

		// GPU no compatible
		if (contienePatron(linea, UNSUPPORTED_GPU_PATTERNS)) {
			procesarGpuNoCompatible(consola, numero_de_linea);
			return;
		}

		// Patrones genéricos
		if (contienePatron(linea, DRIVER_PATTERNS)) {
			procesarProblemaGraficos(consola, numero_de_linea);
		}
	}

	// ---------------------------
	// Agrega un solo enlace válido
	private void agregarEnlace(Consola consola, int numero_de_linea) {
		if (consola == null || numero_de_linea < 0)
			return;

		String enlace = consola.agregarErrorALectador(numero_de_linea, this);
		if (enlace != null && !enlace.isEmpty()) {
			enlaceUnico = enlace; // reemplaza el anterior
		}
	}

	@Override
	public String mensaje() {
		return mensajes.toString().replace("\n", Verificaciones.nl_html) + enlaceUnico;
	}

	private boolean esLineaAMD(String linea) {
		return linea.contains("[atio6axx.dll") || linea.contains("[atioglxx.dll") || linea.contains("C  [atio6axx.dll+")
				|| linea.contains("C  [atioglxx.dll+") || linea.contains("# C  [atio6axx.dll+")
				|| linea.contains("# C  [atioglxx.dll+");
	}

	private boolean esLineaNouveau(String linea) {
		// Primero, verificar que contenga un patrón de Nouveau
		if (!contienePatron(linea, PATRONES_NOUVEAU_LITERAL))
			return false;

		// Excluir específicamente cualquier línea que mencione "ars_nouveau"
		if (linea.contains("ars_nouveau"))
			return false;

		// Solo coincidencias con librerías reales
		return contieneBibliotecaSoConVersionOpcional(linea, "libnouveau.so")
				|| contieneBibliotecaSoConVersionOpcional(linea, "libdrm_nouveau.so");
	}

	/**
	 * Detecta nombres tipo:
	 *
	 * libnouveau.so libnouveau.so.0 libnouveau.so.0.0.0
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

			if (c == '.' && esVersionDespuesDePunto(texto, despues)) {
				return true;
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

	private void verificarUltimaLinea(Consola consola, String log) {
		String ultimaLinea = obtenerUltimaLineaSinSplit(log);

		if (ultimaLinea == null || ultimaLinea.isEmpty()) {
			return;
		}

		CrashDetectorLogger.log(ultimaLinea);

		if (contienePatron(ultimaLinea, PATRONES_LINEA_ULTIMA)) {
			procesarProblemaGraficos(consola, -1);
		} else if (contienePatron(ultimaLinea, UNSUPPORTED_GPU_PATTERNS)) {
			procesarGpuNoCompatible(consola, -1);
		}
	}

	/**
	 * Obtiene la última línea sin usar split(), porque split() crea muchas cadenas.
	 */
	private String obtenerUltimaLineaSinSplit(String log) {
		if (log == null || log.isEmpty()) {
			return null;
		}

		int fin = log.length() - 1;

		while (fin >= 0 && (log.charAt(fin) == '\n' || log.charAt(fin) == '\r')) {
			fin--;
		}

		if (fin < 0) {
			return "";
		}

		int inicio = fin;

		while (inicio >= 0) {
			char c = log.charAt(inicio);

			if (c == '\n' || c == '\r') {
				break;
			}

			inicio--;
		}

		return log.substring(inicio + 1, fin + 1);
	}

	private void procesarProblemaGraficos(Consola consola, int numero_de_linea) {
		if (mensajeGraficosAgregado) {
			agregarEnlace(consola, numero_de_linea);
			return;
		}

		boolean esWindows = esWindows();
		boolean tieneNvidia = esWindows && tieneNvidiaGPU();
		boolean esWindowsNuevo = esWindows && esWindows11OServer2025();

		if (tieneNvidia) {
			mensajes.append(esWindowsNuevo ? MonitorDePID.idioma.problema_con_graficas_nvidia_windows_nuevo()
					: MonitorDePID.idioma.problema_con_graficas_nvidia_windows_viejo());
		} else {
			mensajes.append(MonitorDePID.idioma.problema_con_graficas_general());
		}

		mensajeGraficosAgregado = true;
		activado = true;
		agregarEnlace(consola, numero_de_linea);
	}

	private void procesarProblemaSodiumDrivers(Consola consola, int numero_de_linea) {
		if (!mensajeSodiumAgregado) {
			mensajes.append(MonitorDePID.idioma.problema_con_graficas_sodium());
			mensajeSodiumAgregado = true;
			activado = true;
		}

		agregarEnlace(consola, numero_de_linea);
	}

	private void procesarGpuNoCompatible(Consola consola, int numero_de_linea) {
		if (!mensajeGpuNoCompatibleAgregado) {
			mensajes.append(MonitorDePID.idioma.gpu_no_compatible());
			mensajeGpuNoCompatibleAgregado = true;
			activado = true;
		}

		agregarEnlace(consola, numero_de_linea);
	}

	private void procesarProblemaAMD(Consola consola, int numero_de_linea) {
		if (!mensajeAMDAgregado) {
			mensajes.append(MonitorDePID.idioma.problema_con_graficas_ati());
			mensajeAMDAgregado = true;
			activado = true;
		}

		agregarEnlace(consola, numero_de_linea);
	}

	private void procesarProblemaMatrox(Consola consola, int numero_de_linea) {
		if (!mensajeMatroxAgregado) {
			mensajes.append(MonitorDePID.idioma.problema_con_graficas_matrox());
			mensajeMatroxAgregado = true;
			activado = true;
		}

		agregarEnlace(consola, numero_de_linea);
	}

	private void procesarProblemaNouveau(Consola consola, int numero_de_linea) {
		if (mensajeNouveauAgregado) {
			agregarEnlace(consola, numero_de_linea);
			return;
		}

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
			mensajeNouveauAgregado = true;
			activado = true;
			agregarEnlace(consola, numero_de_linea);
		}
	}

	private void procesarProblemaOpenAL(Consola consola, int numero_de_linea) {
		if (!mensajeOpenALAgregado) {
			mensajes.append(nl);
			mensajes.append(MonitorDePID.idioma.problema_con_openAL());
			mensajeOpenALAgregado = true;
			activado = true;
		}

		agregarEnlace(consola, numero_de_linea);
	}

	private void procesarProblemaIntel(Consola consola, int numero_de_linea) {
		if (!mensajeIntelAgregado) {
			mensajes.append(MonitorDePID.idioma.problema_con_graficas_intel());
			mensajeIntelAgregado = true;
			activado = true;
		}

		agregarEnlace(consola, numero_de_linea);
	}

	private boolean contienePatron(String texto, String[] patrones) {
		if (texto == null || texto.isEmpty()) {
			return false;
		}

		for (String p : patrones) {
			if (texto.contains(p)) {
				CrashDetectorLogger.log("Hay patron de Drivers " + p);
				return true;
			}
		}

		return false;
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
					if (l.contains("NVIDIA") || l.contains("nvidia")) {
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