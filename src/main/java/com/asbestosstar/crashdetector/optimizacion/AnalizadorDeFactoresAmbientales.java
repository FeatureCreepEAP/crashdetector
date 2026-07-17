package com.asbestosstar.crashdetector.optimizacion;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.general.RaptorLakeInestable;
import com.asbestosstar.crashdetector.analizador.general.VerificacionGPU;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;

public class AnalizadorDeFactoresAmbientales {

	private static final long MB = 1024L * 1024L;
	private static final long GB = 1024L * MB;

	public static class MejoraAmbiental {
		public final String id;
		public final String titulo;
		public final String descripcion;
		public final String sugerencia;
		public final String impacto;
		public final String riesgo;

		public MejoraAmbiental(String id, String titulo, String descripcion, String sugerencia, String impacto,
				String riesgo) {
			this.id = id;
			this.titulo = titulo;
			this.descripcion = descripcion;
			this.sugerencia = sugerencia;
			this.impacto = impacto;
			this.riesgo = riesgo;
		}

		@Override
		public String toString() {
			return "[" + impacto + " / " + riesgo + "] " + titulo + ": " + sugerencia;
		}
	}

	public static List<MejoraAmbiental> obtenerMejorasPotenciales() {
		List<MejoraAmbiental> mejoras = new ArrayList<MejoraAmbiental>();

		List<String> jvmArgs = obtenerJvmArgsDeEntrega();
		List<String> appArgs = obtenerAppArgsDeEntrega();

		int javaMayor = detectarVersionMayorJava(jvmArgs, appArgs);
		String os = detectarSistemaOperativo(jvmArgs, appArgs);
		String vendor = detectarProveedorJava(jvmArgs, appArgs);

		long ramTotal = obtenerRamTotalBytes();
		long xmx = obtenerXmxBytes(jvmArgs);
		int cantidadMods = contarMods();

		revisarMemoria(mejoras, jvmArgs, xmx, ramTotal, cantidadMods);
		revisarRecolectorJava8(mejoras, jvmArgs, javaMayor);
		revisarZGC(mejoras, jvmArgs, javaMayor, ramTotal);
		revisarAikar(mejoras, jvmArgs, javaMayor);
		revisarDistribucionJava(mejoras, javaMayor, os, vendor, ramTotal);
		revisarEspacioDisco(mejoras);
		revisarWindowsRHEL(mejoras, os);
		revisarRaptorLake(mejoras);
		revisarNeoForge1201Viejo(mejoras, jvmArgs, appArgs);
		revisarGPU(mejoras);

		return mejoras;
	}

	private static void revisarNeoForge1201Viejo(List<MejoraAmbiental> mejoras, List<String> jvmArgs,
			List<String> appArgs) {

		String texto = unirArgs(jvmArgs) + " " + unirArgs(appArgs);
		String t = texto.toLowerCase(Locale.ROOT).replace('\\', '/');

		boolean tieneFancyModLoader47 = t.contains("net/neoforged/fancymodloader") && t.contains("/47.");

		boolean pareceNeoForge1201 = t.contains("neoforge") && (t.contains("1.20.1") || t.contains("47."));

		if (tieneFancyModLoader47 || pareceNeoForge1201) {
			agregar(mejoras, "neoforge_1201_antiguo", MonitorDePID.idioma.tituloAmbientalNeoForge1201Antiguo(),
					MonitorDePID.idioma.descripcionAmbientalNeoForge1201Antiguo(),
					MonitorDePID.idioma.sugerenciaAmbientalNeoForge1201Antiguo(), MonitorDePID.idioma.impactoMedio(),
					MonitorDePID.idioma.riesgoBajo());
		}
	}

	private static void revisarGPU(List<MejoraAmbiental> mejoras) {
		if (VerificacionGPU.hayProblema) {
			agregar(mejoras, "gpu_detectada", MonitorDePID.idioma.tituloAmbientalGPU(),
					MonitorDePID.idioma.descripcionAmbientalGPU(), MonitorDePID.idioma.sugerenciaAmbientalGPU(),
					MonitorDePID.idioma.impactoAlto(), MonitorDePID.idioma.riesgoMedio());
		}
	}

	private static String unirArgs(List<String> args) {
		StringBuilder sb = new StringBuilder();

		for (String arg : args) {
			if (arg == null) {
				continue;
			}

			if (sb.length() > 0) {
				sb.append(' ');
			}

			sb.append(arg);
		}

		return sb.toString();
	}

	private static void revisarMemoria(List<MejoraAmbiental> mejoras, List<String> jvmArgs, long xmx, long ramTotal,
			int cantidadMods) {
		if (ramTotal <= 0) {
			return;
		}

		long maximoSeguro = (long) (ramTotal * 0.80d);
		long minimoSugerido = calcularMinimoRamSugerido(cantidadMods);

		if (xmx <= 0) {
			agregar(mejoras, "memoria_sin_xmx", MonitorDePID.idioma.tituloAmbientalSinXmx(),
					MonitorDePID.idioma.descripcionAmbientalSinXmx(cantidadMods, mbAString(minimoSugerido),
							mbAString(maximoSeguro)),
					MonitorDePID.idioma.sugerenciaAmbientalSinXmx(mbAString(minimoSugerido)),
					MonitorDePID.idioma.impactoAlto(), MonitorDePID.idioma.riesgoBajo());
			return;
		}

		if (xmx > maximoSeguro) {
			agregar(mejoras, "memoria_demasiada", MonitorDePID.idioma.tituloAmbientalDemasiadaMemoria(),
					MonitorDePID.idioma.descripcionAmbientalDemasiadaMemoria(mbAString(xmx), mbAString(ramTotal),
							mbAString(maximoSeguro)),
					MonitorDePID.idioma.sugerenciaAmbientalDemasiadaMemoria(mbAString(maximoSeguro)),
					MonitorDePID.idioma.impactoAlto(), MonitorDePID.idioma.riesgoMedio());
		}

		if (xmx < minimoSugerido) {
			agregar(mejoras, "memoria_insuficiente", MonitorDePID.idioma.tituloAmbientalMemoriaInsuficiente(),
					MonitorDePID.idioma.descripcionAmbientalMemoriaInsuficiente(cantidadMods, mbAString(xmx),
							mbAString(minimoSugerido)),
					MonitorDePID.idioma.sugerenciaAmbientalMemoriaInsuficiente(mbAString(minimoSugerido)),
					MonitorDePID.idioma.impactoAlto(), MonitorDePID.idioma.riesgoBajo());
		}
	}

	private static void revisarRecolectorJava8(List<MejoraAmbiental> mejoras, List<String> jvmArgs, int javaMayor) {
		if (javaMayor != 8) {
			return;
		}

		boolean tieneG1 = contieneArg(jvmArgs, "-XX:+UseG1GC");
		boolean tieneShenandoah = contieneArg(jvmArgs, "-XX:+UseShenandoahGC");

		if (!tieneG1 && !tieneShenandoah) {
			agregar(mejoras, "java8_gc", MonitorDePID.idioma.tituloAmbientalJava8GC(),
					MonitorDePID.idioma.descripcionAmbientalJava8GC(), MonitorDePID.idioma.sugerenciaAmbientalJava8GC(),
					MonitorDePID.idioma.impactoAlto(), MonitorDePID.idioma.riesgoBajo());
		}
	}

	private static void revisarZGC(List<MejoraAmbiental> mejoras, List<String> jvmArgs, int javaMayor, long ramTotal) {
		if (javaMayor < 17 || ramTotal <= 12L * GB) {
			return;
		}

		if (!contieneArg(jvmArgs, "-XX:+UseZGC")) {
			agregar(mejoras, "zgc", MonitorDePID.idioma.tituloAmbientalZGC(),
					MonitorDePID.idioma.descripcionAmbientalZGC(mbAString(ramTotal)),
					MonitorDePID.idioma.sugerenciaAmbientalZGC(), MonitorDePID.idioma.impactoMedio(),
					MonitorDePID.idioma.riesgoMedio());
		}
	}

	private static void revisarAikar(List<MejoraAmbiental> mejoras, List<String> jvmArgs, int javaMayor) {
		if (javaMayor <= 0 || javaMayor > 17) {
			return;
		}

		boolean tieneG1 = contieneArg(jvmArgs, "-XX:+UseG1GC");
		boolean tienePausa = contieneArgQueEmpiezaCon(jvmArgs, "-XX:MaxGCPauseMillis=");
		boolean tieneParallelRef = contieneArg(jvmArgs, "-XX:+ParallelRefProcEnabled");

		if (!tieneG1 || !tienePausa || !tieneParallelRef) {
			agregar(mejoras, "aikar_flags", MonitorDePID.idioma.tituloAmbientalAikar(),
					MonitorDePID.idioma.descripcionAmbientalAikar(), MonitorDePID.idioma.sugerenciaAmbientalAikar(),
					MonitorDePID.idioma.impactoMedio(), MonitorDePID.idioma.riesgoBajo());
		}
	}

	private static void revisarDistribucionJava(List<MejoraAmbiental> mejoras, int javaMayor, String os, String vendor,
			long ramTotal) {
		String osLower = os.toLowerCase(Locale.ROOT);
		String vendorLower = vendor.toLowerCase(Locale.ROOT);

		if ((javaMayor == 8 || javaMayor == 11) && (osLower.contains("windows") || osLower.contains("linux"))
				&& !vendorLower.contains("red hat") && !vendorLower.contains("redhat")) {
			agregar(mejoras, "redhat_jdk", MonitorDePID.idioma.tituloAmbientalRedHatJDK(),
					MonitorDePID.idioma.descripcionAmbientalRedHatJDK(javaMayor, os),
					MonitorDePID.idioma.sugerenciaAmbientalRedHatJDK(), MonitorDePID.idioma.impactoMedio(),
					MonitorDePID.idioma.riesgoBajo());
		}

		if (javaMayor >= 16 && ramTotal > 16L * GB) {
			if (osLower.contains("linux") && !vendorLower.contains("azul") && !vendorLower.contains("zing")
					&& !vendorLower.contains("prime")) {
				agregar(mejoras, "azul_prime", MonitorDePID.idioma.tituloAmbientalAzulPrime(),
						MonitorDePID.idioma.descripcionAmbientalAzulPrime(),
						MonitorDePID.idioma.sugerenciaAmbientalAzulPrime(), MonitorDePID.idioma.impactoMedio(),
						MonitorDePID.idioma.riesgoMedio());
			} else if (!osLower.contains("linux") && !vendorLower.contains("graal")) {
				agregar(mejoras, "graalvm", MonitorDePID.idioma.tituloAmbientalGraalVM(),
						MonitorDePID.idioma.descripcionAmbientalGraalVM(),
						MonitorDePID.idioma.sugerenciaAmbientalGraalVM(), MonitorDePID.idioma.impactoMedio(),
						MonitorDePID.idioma.riesgoMedio());
			}
		}
	}

	private static void revisarEspacioDisco(List<MejoraAmbiental> mejoras) {
		try {
			File dir = new File(Statics.CARPETA_DE_APP, "config").getAbsoluteFile();
			File raiz = dir.exists() ? dir : dir.getParentFile();

			if (raiz == null) {
				return;
			}

			long libre = raiz.getUsableSpace();

			if (libre > 0 && libre < 20L * GB) {
				agregar(mejoras, "disco_bajo", MonitorDePID.idioma.tituloAmbientalDiscoBajo(),
						MonitorDePID.idioma.descripcionAmbientalDiscoBajo(mbAString(libre)),
						MonitorDePID.idioma.sugerenciaAmbientalDiscoBajo(), MonitorDePID.idioma.impactoMedio(),
						MonitorDePID.idioma.riesgoBajo());
			}
		} catch (Throwable t) {
			// Ignorar: no queremos que un fallo de permisos rompa el análisis.
		}
	}

	private static void revisarWindowsRHEL(List<MejoraAmbiental> mejoras, String os) {
		if (os == null) {
			return;
		}

		if (os.toLowerCase(Locale.ROOT).contains("windows")) {
			agregar(mejoras, "windows_rhel9", MonitorDePID.idioma.tituloAmbientalWindowsRHEL9(),
					MonitorDePID.idioma.descripcionAmbientalWindowsRHEL9(),
					MonitorDePID.idioma.sugerenciaAmbientalWindowsRHEL9(), MonitorDePID.idioma.impactoMedio(),
					MonitorDePID.idioma.riesgoBajo());
		}
	}

	private static void revisarRaptorLake(List<MejoraAmbiental> mejoras) {
		if (RaptorLakeInestable.hayProblema) {
			agregar(mejoras, "raptor_lake", MonitorDePID.idioma.tituloAmbientalRaptorLake(),
					MonitorDePID.idioma.descripcionAmbientalRaptorLake(),
					MonitorDePID.idioma.sugerenciaAmbientalRaptorLake(), MonitorDePID.idioma.impactoAlto(),
					MonitorDePID.idioma.riesgoAlto());
		}
	}

	private static List<String> obtenerJvmArgsDeEntrega() {
		if (Statics.JVM_ARGS != null) {
			return Statics.JVM_ARGS;
		}
		return new ArrayList<String>();
	}

	private static List<String> obtenerAppArgsDeEntrega() {
		if (Statics.APP_ARGS != null) {
			return Statics.APP_ARGS;
		}
		return new ArrayList<String>();
	}

	private static int detectarVersionMayorJava(List<String> jvmArgs, List<String> appArgs) {
		String valor = buscarValorSistema(jvmArgs, "java.version");
		if (valor == null) {
			valor = buscarValorSistema(appArgs, "java.version");
		}
		if (valor == null) {
			valor = System.getProperty("java.version", "");
		}
		return parsearJavaMayor(valor);
	}

	private static String detectarSistemaOperativo(List<String> jvmArgs, List<String> appArgs) {
		String valor = buscarValorSistema(jvmArgs, "os.name");
		if (valor == null) {
			valor = buscarValorSistema(appArgs, "os.name");
		}
		if (valor == null) {
			valor = System.getProperty("os.name", "");
		}
		return valor;
	}

	private static String detectarProveedorJava(List<String> jvmArgs, List<String> appArgs) {
		String valor = buscarValorSistema(jvmArgs, "java.vendor");
		if (valor == null) {
			valor = buscarValorSistema(appArgs, "java.vendor");
		}
		if (valor == null) {
			valor = System.getProperty("java.vendor", "");
		}
		return valor;
	}

	private static String buscarValorSistema(List<String> args, String clave) {
		String prefijo = "-D" + clave + "=";

		for (String arg : args) {
			if (arg != null && arg.startsWith(prefijo)) {
				return arg.substring(prefijo.length()).trim();
			}
		}

		return null;
	}

	private static int parsearJavaMayor(String version) {
		if (version == null) {
			return -1;
		}

		String v = version.trim();
		if (v.isEmpty()) {
			return -1;
		}

		if (v.startsWith("1.")) {
			return leerNumeroDesde(v, 2);
		}

		return leerNumeroDesde(v, 0);
	}

	private static int leerNumeroDesde(String texto, int inicio) {
		StringBuilder sb = new StringBuilder();

		for (int i = inicio; i < texto.length(); i++) {
			char c = texto.charAt(i);
			if (!Character.isDigit(c)) {
				break;
			}
			sb.append(c);
		}

		if (sb.length() == 0) {
			return -1;
		}

		try {
			return Integer.parseInt(sb.toString());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private static long obtenerRamTotalBytes() {
		try {
			java.lang.management.OperatingSystemMXBean bean = ManagementFactory.getOperatingSystemMXBean();

			if (bean instanceof com.sun.management.OperatingSystemMXBean) {
				return ((com.sun.management.OperatingSystemMXBean) bean).getTotalPhysicalMemorySize();
			}
		} catch (Throwable t) {
			return -1;
		}

		return -1;
	}

	private static long obtenerXmxBytes(List<String> jvmArgs) {
		for (String arg : jvmArgs) {
			if (arg == null) {
				continue;
			}

			if (arg.startsWith("-Xmx")) {
				return parsearMemoria(arg.substring(4));
			}

			if (arg.startsWith("-XX:MaxRAM=")) {
				return parsearMemoria(arg.substring("-XX:MaxRAM=".length()));
			}
		}

		return -1;
	}

	private static long parsearMemoria(String valor) {
		if (valor == null) {
			return -1;
		}

		String v = valor.trim().toLowerCase(Locale.ROOT);
		if (v.isEmpty()) {
			return -1;
		}

		long multiplicador = 1L;

		char ultimo = v.charAt(v.length() - 1);
		if (ultimo == 'g') {
			multiplicador = GB;
			v = v.substring(0, v.length() - 1);
		} else if (ultimo == 'm') {
			multiplicador = MB;
			v = v.substring(0, v.length() - 1);
		} else if (ultimo == 'k') {
			multiplicador = 1024L;
			v = v.substring(0, v.length() - 1);
		}

		try {
			double numero = Double.parseDouble(v);
			return (long) (numero * multiplicador);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private static long calcularMinimoRamSugerido(int cantidadMods) {
		long base = 4L * GB;
		long porMods = ((Math.max(0, cantidadMods) + 99) / 100) * 1536L * MB;
		return Math.max(base, porMods);
	}

	private static int contarMods() {
		try {
			Buscador.cargar();

			int total = 0;
			for (ArchivoDeMod mod : Buscador.obtenerTodosLosModsYSubmodsRecursivos()) {
				if (mod != null) {
					total++;
				}
			}
			return total;
		} catch (Throwable t) {
			return 0;
		}
	}

	private static boolean contieneArg(List<String> args, String esperado) {
		for (String arg : args) {
			if (esperado.equals(arg)) {
				return true;
			}
		}
		return false;
	}

	private static boolean contieneArgQueEmpiezaCon(List<String> args, String prefijo) {
		for (String arg : args) {
			if (arg != null && arg.startsWith(prefijo)) {
				return true;
			}
		}
		return false;
	}

	private static String mbAString(long bytes) {
		if (bytes <= 0) {
			return "desconocido";
		}

		long mb = bytes / MB;

		if (mb >= 1024) {
			double gb = mb / 1024.0d;
			return String.format(Locale.ROOT, "%.1f GB", gb);
		}

		return mb + " MB";
	}

	private static void agregar(List<MejoraAmbiental> mejoras, String id, String titulo, String descripcion,
			String sugerencia, String impacto, String riesgo) {
		mejoras.add(new MejoraAmbiental(id, titulo, descripcion, sugerencia, impacto, riesgo));
	}
}