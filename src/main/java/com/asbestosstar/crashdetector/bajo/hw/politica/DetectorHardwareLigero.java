package com.asbestosstar.crashdetector.bajo.hw.politica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Inventario ligero del equipo local.
 *
 * No carga JNI/JNA ni otras bibliotecas nativas. Primero usa propiedades Java y
 * archivos virtuales del sistema. Solo cuando hace falta ejecuta un comando
 * corto propio del sistema operativo, con tiempo máximo y salida limitada.
 */
public final class DetectorHardwareLigero {

	private static volatile InfoLocal cache;

	private DetectorHardwareLigero() {
	}

	public static InfoLocal detectar() {
		InfoLocal actual = cache;
		if (actual != null) {
			return actual;
		}
		synchronized (DetectorHardwareLigero.class) {
			if (cache == null) {
				cache = detectarInterno();
			}
			return cache;
		}
	}

	public static void limpiarCache() {
		cache = null;
	}

	private static InfoLocal detectarInterno() {
		String nombreSO = propiedad("os.name");
		String versionSO = propiedad("os.version");
		String arquitectura = propiedad("os.arch");
		int hilos = Math.max(1, Runtime.getRuntime().availableProcessors());

		String detalleSO = obtenerDetalleSO(nombreSO, versionSO);
		String cpu = obtenerNombreCPU(nombreSO);
		double ghz = obtenerFrecuenciaGHz(nombreSO);
		double ram = obtenerRamGB(nombreSO);

		return new InfoLocal(nombreSO, detalleSO, arquitectura, cpu, ram, ghz, hilos);
	}

	private static String obtenerDetalleSO(String nombreSO, String versionSO) {
		String base = unir(nombreSO, versionSO);
		String so = minusculas(nombreSO);

		if (esLinux(so)) {
			String pretty = leerValorArchivo(Paths.get("/etc/os-release"), "PRETTY_NAME");
			if (!pretty.isEmpty()) {
				return unir(base, pretty);
			}
		} else if (so.contains("mac")) {
			String producto = ejecutar("sw_vers", "-productName");
			String version = ejecutar("sw_vers", "-productVersion");
			return unir(base, producto, version);
		} else if (so.contains("sunos") || so.contains("solaris")) {
			return unir(base, ejecutar("uname", "-v"), ejecutar("uname", "-i"));
		} else if (so.contains("aix")) {
			return unir(base, ejecutar("oslevel", "-s"));
		} else if (so.contains("hp-ux") || so.contains("hpux")) {
			return unir(base, ejecutar("uname", "-r"), ejecutar("uname", "-m"));
		} else if (so.contains("irix")) {
			return unir(base, ejecutar("uname", "-R"));
		} else if (esUnixWareOpenServer(so)) {
			return unir(base, ejecutar("uname", "-X"));
		} else if (so.contains("haiku")) {
			return unir(base, ejecutar("uname", "-a"));
		}
		return base;
	}

	private static String obtenerNombreCPU(String nombreSO) {
		String so = minusculas(nombreSO);

		if (esLinux(so)) {
			String valor = leerPrimerCampoCpuInfo("model name", "processor", "cpu model", "machine", "cpu");
			if (!valor.isEmpty()) {
				return valor;
			}
		}

		if (so.contains("win")) {
			String salida = ejecutar("reg", "query", "HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0", "/v",
					"ProcessorNameString");
			String valor = valorFinalRegistro(salida, "ProcessorNameString");
			if (!valor.isEmpty()) {
				return valor;
			}
		} else if (so.contains("mac")) {
			String valor = ejecutar("sysctl", "-n", "machdep.cpu.brand_string");
			if (valor.isEmpty()) {
				valor = ejecutar("sysctl", "-n", "hw.model");
			}
			return valor;
		} else if (so.contains("sunos") || so.contains("solaris")) {
			String valor = ejecutar("kstat", "-p", "cpu_info:::brand");
			if (valor.isEmpty()) {
				valor = ejecutar("psrinfo", "-pv");
			}
			return limpiarSalidaHardware(valor);
		} else if (so.contains("aix")) {
			return extraerLineaHardware(ejecutar("prtconf"), "Processor Type:");
		} else if (so.contains("hp-ux") || so.contains("hpux")) {
			return limpiarSalidaHardware(ejecutar("machinfo"));
		} else if (so.contains("irix")) {
			return limpiarSalidaHardware(ejecutar("hinv", "-c", "processor"));
		} else if (esUnixWareOpenServer(so)) {
			String valor = ejecutar("uname", "-X");
			String cpu = extraerLineaHardware(valor, "Processor");
			if (cpu.isEmpty()) {
				cpu = limpiarSalidaHardware(ejecutar("psrinfo", "-v"));
			}
			return cpu;
		} else if (so.contains("freebsd") || so.contains("openbsd") || so.contains("netbsd")
				|| so.contains("dragonfly")) {
			String valor = ejecutar("sysctl", "-n", "hw.model");
			return limpiarSalidaHardware(valor);
		} else if (so.contains("haiku")) {
			return limpiarSalidaHardware(ejecutar("sysinfo", "-cpu"));
		} else if (so.contains("os/2") || so.contains("arcaos")) {
			return unir(propiedad("os.arch"), propiedad("sun.cpu.endian"));
		}

		return limpiarSalidaHardware(ejecutar("uname", "-m"));
	}

	private static double obtenerFrecuenciaGHz(String nombreSO) {
		String so = minusculas(nombreSO);

		if (esLinux(so)) {
			String khz = leerArchivoCorto(Paths.get("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"));
			double valorKhz = parsearNumero(khz);
			if (valorKhz > 0) {
				return valorKhz / 1000000.0;
			}
			String mhz = leerPrimerCampoCpuInfo("cpu MHz", "clock");
			double valorMhz = parsearNumero(mhz);
			if (valorMhz > 0) {
				if (minusculas(mhz).contains("ghz")) {
					return valorMhz;
				}
				return valorMhz / 1000.0;
			}
		}

		if (so.contains("win")) {
			String salida = ejecutar("reg", "query", "HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0", "/v",
					"~MHz");
			double mhz = parsearNumero(valorFinalRegistro(salida, "~MHz"));
			if (mhz > 0) {
				return mhz / 1000.0;
			}
		} else if (so.contains("mac")) {
			double hz = parsearNumero(ejecutar("sysctl", "-n", "hw.cpufrequency_max"));
			if (hz <= 0) {
				hz = parsearNumero(ejecutar("sysctl", "-n", "hw.cpufrequency"));
			}
			if (hz > 0) {
				return hz / 1000000000.0;
			}
		} else if (so.contains("sunos") || so.contains("solaris")) {
			double mhz = parsearNumero(ejecutar("kstat", "-p", "cpu_info:::clock_MHz"));
			if (mhz > 0) {
				return mhz / 1000.0;
			}
		} else if (so.contains("aix")) {
			String salida = ejecutar("prtconf");
			double mhz = parsearNumero(extraerLineaHardware(salida, "Processor Clock Speed:"));
			if (mhz > 0) {
				return minusculas(salida).contains("ghz") ? mhz : mhz / 1000.0;
			}
		}

		return parsearGHzDesdeTexto(obtenerNombreCPU(nombreSO));
	}

	private static double obtenerRamGB(String nombreSO) {
		try {
			Class<?> interfaz = Class.forName("com.sun.management.OperatingSystemMXBean");
			Object bean = ManagementFactory.getOperatingSystemMXBean();
			if (interfaz.isInstance(bean)) {
				Method metodo;
				try {
					metodo = interfaz.getMethod("getTotalMemorySize");
				} catch (NoSuchMethodException e) {
					metodo = interfaz.getMethod("getTotalPhysicalMemorySize");
				}
				Object valor = metodo.invoke(bean);
				if (valor instanceof Number) {
					long bytes = ((Number) valor).longValue();
					if (bytes > 0) {
						return bytes / 1073741824.0;
					}
				}
			}
		} catch (Throwable ignorado) {
			// Se usan fallbacks portables por sistema.
		}

		String memTotal = leerValorArchivo(Paths.get("/proc/meminfo"), "MemTotal");
		double kb = parsearNumero(memTotal);
		if (kb > 0) {
			return kb / 1048576.0;
		}

		String so = minusculas(nombreSO);
		if (so.contains("win")) {
			return parsearMemoriaGB(ejecutar("wmic", "ComputerSystem", "get", "TotalPhysicalMemory", "/value"));
		}
		if (so.contains("mac")) {
			double bytes = parsearNumero(ejecutar("sysctl", "-n", "hw.memsize"));
			return bytes > 0 ? bytes / 1073741824.0 : 0.0;
		}
		if (so.contains("freebsd") || so.contains("openbsd") || so.contains("netbsd") || so.contains("dragonfly")) {
			String valor = ejecutar("sysctl", "-n", "hw.physmem64");
			if (valor.isEmpty()) {
				valor = ejecutar("sysctl", "-n", "hw.physmem");
			}
			double bytes = parsearNumero(valor);
			return bytes > 0 ? bytes / 1073741824.0 : 0.0;
		}
		if (so.contains("sunos") || so.contains("solaris")) {
			return parsearMemoriaGB(ejecutar("prtconf"));
		}
		if (so.contains("aix")) {
			String valor = ejecutar("lsattr", "-El", "sys0", "-a", "realmem");
			double memoriaKb = parsearNumero(valor);
			return memoriaKb > 0 ? memoriaKb / 1048576.0 : 0.0;
		}
		if (so.contains("hp-ux") || so.contains("hpux")) {
			return parsearMemoriaGB(ejecutar("machinfo"));
		}
		if (so.contains("irix")) {
			return parsearMemoriaGB(ejecutar("hinv", "-t", "memory"));
		}
		if (esUnixWareOpenServer(so)) {
			return parsearMemoriaGB(unir(ejecutar("memsize"), ejecutar("uname", "-X")));
		}
		return 0.0;
	}

	private static double parsearMemoriaGB(String texto) {
		if (texto == null || texto.trim().isEmpty()) {
			return 0.0;
		}
		String lower = texto.toLowerCase(Locale.ROOT);
		double valor = parsearNumero(lower);
		if (valor <= 0) {
			return 0.0;
		}
		if (lower.contains("terabyte") || lower.contains(" tb") || lower.endsWith("tb")) {
			return valor * 1024.0;
		}
		if (lower.contains("gigabyte") || lower.contains(" gb") || lower.endsWith("gb")) {
			return valor;
		}
		if (lower.contains("megabyte") || lower.contains(" mb") || lower.endsWith("mb")) {
			return valor / 1024.0;
		}
		if (lower.contains("kilobyte") || lower.contains(" kb") || lower.endsWith("kb")) {
			return valor / 1048576.0;
		}
		// TotalPhysicalMemory y algunos memsize devuelven bytes sin unidad.
		if (valor > 1073741824.0) {
			return valor / 1073741824.0;
		}
		return 0.0;
	}

	private static String leerPrimerCampoCpuInfo(String... claves) {
		Path ruta = Paths.get("/proc/cpuinfo");
		if (!Files.isReadable(ruta)) {
			return "";
		}
		try {
			List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
			// Respeta la prioridad de las claves solicitadas. Así "model name" gana a
			// "processor", que en x86 normalmente solo contiene el índice 0.
			for (String buscada : claves) {
				for (String linea : lineas) {
					int dosPuntos = linea.indexOf(':');
					if (dosPuntos < 0) {
						continue;
					}
					String clave = linea.substring(0, dosPuntos).trim();
					if (clave.equalsIgnoreCase(buscada)) {
						String valor = linea.substring(dosPuntos + 1).trim();
						if (!valor.isEmpty()) {
							return valor;
						}
					}
				}
			}
		} catch (Exception e) {
			CrashDetectorLogger.log("No se pudo leer /proc/cpuinfo: " + e.getMessage());
		}
		return "";
	}

	private static String leerValorArchivo(Path ruta, String clave) {
		if (!Files.isReadable(ruta)) {
			return "";
		}
		try (BufferedReader reader = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				String limpia = linea.trim();
				if (limpia.regionMatches(true, 0, clave, 0, clave.length())) {
					int separador = limpia.indexOf('=');
					if (separador < 0) {
						separador = limpia.indexOf(':');
					}
					if (separador >= 0) {
						String valor = limpia.substring(separador + 1).trim();
						if (valor.length() >= 2 && valor.startsWith("\"") && valor.endsWith("\"")) {
							valor = valor.substring(1, valor.length() - 1);
						}
						return valor;
					}
				}
			}
		} catch (Exception ignorado) {
		}
		return "";
	}

	private static String leerArchivoCorto(Path ruta) {
		if (!Files.isReadable(ruta)) {
			return "";
		}
		try {
			byte[] bytes = Files.readAllBytes(ruta);
			if (bytes.length > 4096) {
				return "";
			}
			return new String(bytes, StandardCharsets.UTF_8).trim();
		} catch (Exception e) {
			return "";
		}
	}

	private static String ejecutar(String... comando) {
		if (comando == null || comando.length == 0) {
			return "";
		}
		Process proceso = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(comando);
			pb.redirectErrorStream(true);
			proceso = pb.start();
			if (!proceso.waitFor(900, TimeUnit.MILLISECONDS)) {
				proceso.destroy();
				if (!proceso.waitFor(100, TimeUnit.MILLISECONDS)) {
					proceso.destroyForcibly();
				}
				return "";
			}
			StringBuilder sb = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(proceso.getInputStream(), StandardCharsets.UTF_8))) {
				String linea;
				while ((linea = reader.readLine()) != null && sb.length() < 32768) {
					if (sb.length() > 0) {
						sb.append('\n');
					}
					sb.append(linea);
				}
			}
			return sb.toString().trim();
		} catch (Throwable e) {
			return "";
		} finally {
			if (proceso != null) {
				try {
					proceso.getInputStream().close();
				} catch (Exception ignorado) {
				}
			}
		}
	}

	private static String valorFinalRegistro(String salida, String nombre) {
		if (salida == null) {
			return "";
		}
		String[] lineas = salida.split("\\r?\\n");
		for (String linea : lineas) {
			if (linea.toLowerCase(Locale.ROOT).contains(nombre.toLowerCase(Locale.ROOT))) {
				String[] partes = linea.trim().split("\\s{2,}");
				if (partes.length >= 3) {
					return partes[partes.length - 1].trim();
				}
			}
		}
		return "";
	}

	private static String extraerLineaHardware(String salida, String prefijo) {
		if (salida == null || salida.isEmpty()) {
			return "";
		}
		for (String linea : salida.split("\\r?\\n")) {
			String limpia = linea.trim();
			if (limpia.toLowerCase(Locale.ROOT).startsWith(prefijo.toLowerCase(Locale.ROOT))) {
				int p = limpia.indexOf(':');
				return p >= 0 ? limpia.substring(p + 1).trim() : limpia;
			}
		}
		return "";
	}

	private static String limpiarSalidaHardware(String texto) {
		if (texto == null) {
			return "";
		}
		String limpia = texto.trim().replace('\t', ' ');
		if (limpia.length() > 500) {
			limpia = limpia.substring(0, 500);
		}
		return limpia.replaceAll("[\\r\\n]+", " ").replaceAll("\\s+", " ").trim();
	}

	private static double parsearGHzDesdeTexto(String texto) {
		if (texto == null) {
			return 0.0;
		}
		String lower = texto.toLowerCase(Locale.ROOT);
		int ghz = lower.indexOf("ghz");
		if (ghz > 0) {
			return parsearNumero(lower.substring(Math.max(0, ghz - 10), ghz));
		}
		int mhz = lower.indexOf("mhz");
		if (mhz > 0) {
			double valor = parsearNumero(lower.substring(Math.max(0, mhz - 10), mhz));
			return valor > 0 ? valor / 1000.0 : 0.0;
		}
		return 0.0;
	}

	private static double parsearNumero(String texto) {
		if (texto == null) {
			return 0.0;
		}
		StringBuilder sb = new StringBuilder();
		boolean iniciado = false;
		boolean decimal = false;
		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);
			if (Character.isDigit(c)) {
				sb.append(c);
				iniciado = true;
			} else if ((c == '.' || c == ',') && iniciado && !decimal) {
				sb.append('.');
				decimal = true;
			} else if (iniciado) {
				break;
			}
		}
		if (sb.length() == 0) {
			return 0.0;
		}
		try {
			return Double.parseDouble(sb.toString());
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}

	private static String unir(String... valores) {
		StringBuilder sb = new StringBuilder();
		for (String valor : valores) {
			if (valor == null || valor.trim().isEmpty()) {
				continue;
			}
			if (sb.length() > 0) {
				sb.append(' ');
			}
			sb.append(valor.trim());
		}
		return sb.toString();
	}

	private static String propiedad(String nombre) {
		return System.getProperty(nombre, "").trim();
	}

	private static String minusculas(String valor) {
		return valor == null ? "" : valor.toLowerCase(Locale.ROOT);
	}

	private static boolean esLinux(String so) {
		return so.contains("linux") || so.contains("nux");
	}

	private static boolean esUnixWareOpenServer(String so) {
		return so.contains("unixware") || so.contains("openunix") || so.contains("openserver") || so.contains("sco_sv");
	}

	public static final class InfoLocal {
		private final String sistemaOperativo;
		private final String detalleSistemaOperativo;
		private final String arquitectura;
		private final String cpu;
		private final double ramGB;
		private final double ghz;
		private final int hilos;

		InfoLocal(String sistemaOperativo, String detalleSistemaOperativo, String arquitectura, String cpu,
				double ramGB, double ghz, int hilos) {
			this.sistemaOperativo = sistemaOperativo == null ? "" : sistemaOperativo;
			this.detalleSistemaOperativo = detalleSistemaOperativo == null ? "" : detalleSistemaOperativo;
			this.arquitectura = arquitectura == null ? "" : arquitectura;
			this.cpu = cpu == null ? "" : cpu;
			this.ramGB = ramGB;
			this.ghz = ghz;
			this.hilos = hilos;
		}

		public String sistemaOperativo() {
			return sistemaOperativo;
		}

		public String detalleSistemaOperativo() {
			return detalleSistemaOperativo;
		}

		public String arquitectura() {
			return arquitectura;
		}

		public String cpu() {
			return cpu;
		}

		public double ramGB() {
			return ramGB;
		}

		public double ghz() {
			return ghz;
		}

		public int hilos() {
			return hilos;
		}
	}
}
