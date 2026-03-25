package com.asbestosstar.crashdetector.bajo.hw.cpu.intel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Para x64/Itanium, el microcódigo se expone en Windows a través del registro
 * (HKLM\HARDWARE\DESCRIPTION\System\CentralProcessor\0\Update Revision) y en
 * Linux/Unix a través de /proc/cpuinfo (campo "microcode"). En macOS, se puede
 * intentar obtener con sysctl, pero no siempre está disponible.
 */
public class ValidadorMicrocodigo {

	// ==========================================================
	// MÉTODO PRINCIPAL (IMPRIME EN CONSOLA)
	// ==========================================================
	public static void main() {
		// Ahora simplemente imprimimos el resultado del método que devuelve el String
		System.out.println(obtenerReporte());
	}

	// ==========================================================
	// NUEVO MÉTODO: RETORNA EL RESULTADO COMO STRING
	// ==========================================================
	/**
	 * Ejecuta la validación de microcódigo y arquitectura, retornando el resultado
	 * completo como un String formateado.
	 * 
	 * @return Reporte detallado del estado del microcódigo Intel.
	 */
	public static String obtenerReporte() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n--- Validador de Microcódigo Intel ---\n");
		CrashDetectorLogger.log("Iniciando validación de microcódigo Intel...");
		// 1. Verificar Arquitectura
		String arq = System.getProperty("os.arch").toLowerCase(Locale.ENGLISH);
		boolean esX64 = arq.contains("amd64") || arq.contains("x86_64");
		boolean esItanium = arq.contains("ia64");

		if (!esX64 && !esItanium) {
			sb.append("❌ Arquitectura no compatible: ").append(arq).append(". Se requiere Intel x64 o Itanium.\n");
			return sb.toString();
		}
		CrashDetectorLogger.log("Es Intel...");

		// 2. Verificar Fabricante
		String fabricante = obtenerFabricanteCPU();
		sb.append("Arquitectura: ").append(esItanium ? "Itanium (IA-64)" : "x64 (Intel/AMD)").append("\n");
		sb.append("Fabricante detectado: ").append(fabricante).append("\n");
		CrashDetectorLogger.log("tenemos ...");

		if (!fabricante.toLowerCase(Locale.ENGLISH).contains("intel")) {
			sb.append("❌ Microcódigo no aplicable: El procesador no es Intel.\n");
			return sb.toString();
		}

		// 3. Obtener Microcódigo
		String microcodigo = obtenerRevisionMicrocodigo();

		if (microcodigo.isEmpty() || microcodigo.equals("Desconocido")) {
			sb.append("⚠️ No se pudo determinar la revisión del microcódigo.\n");
		} else {
			sb.append("✅ Revisión de Microcódigo Intel: ").append(microcodigo).append("\n");
		}

		return sb.toString();
	}

	// ==========================================================
	// OBTENER FABRICANTE (CPUID)
	// ==========================================================
	private static String obtenerFabricanteCPU() {
		String so = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

		try {
			if (so.contains("win")) {
				return ejecutarComando("wmic", "cpu", "get", "Manufacturer");
			} else if (so.contains("linux") || so.contains("nix") || so.contains("nux") || so.contains("bsd")) {
				// Linux/Unix: grep "vendor_id" /proc/cpuinfo
				return parsearProcCpuinfo("vendor_id");
			} else if (so.contains("mac")) {
				// macOS: sysctl -n machdep.cpu.vendor
				return ejecutarComando("sysctl", "-n", "machdep.cpu.vendor");
			}
		} catch (Exception e) {
			System.err.println("Error detectando fabricante: " + e.getMessage());
		}
		return "Desconocido";
	}

	// ==========================================================
	// OBTENER REVISIÓN DE MICROCÓDIGO
	// ==========================================================
	public static String obtenerRevisionMicrocodigo() {
		String so = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

		try {
			if (so.contains("win")) {

				String mc = obtenerMicrocodigoWindowsDesdeRegistro();
				if (!mc.isEmpty())
					return mc;
				return "Desconocido";

			} else if (so.contains("linux") || so.contains("nix") || so.contains("nux")) {
				// Linux: microcode en /proc/cpuinfo
				String mc = parsearProcCpuinfo("microcode");
				if (!mc.isEmpty())
					return mc;
			} else if (so.contains("bsd")) {
				// FreeBSD: sysctl dev.cpu.0.microcode_version
				String mc = ejecutarComando("sysctl", "-n", "dev.cpu.0.microcode_version");
				if (!mc.isEmpty() && !mc.contains("unknown"))
					return mc;

				return parsearProcCpuinfo("microcode");
			} else if (so.contains("mac")) {
				// macOS: sysctl -n machdep.cpu.microcode_version
				String mc = ejecutarComando("sysctl", "-n", "machdep.cpu.microcode_version");
				if (!mc.isEmpty() && !mc.contains("unknown"))
					return mc;

				return "No expuesto por el SO";
			}
		} catch (Exception e) {
			System.err.println("Error leyendo microcódigo: " + e.getMessage());
		}
		return "Desconocido";
	}

	private static String obtenerMicrocodigoWindowsDesdeRegistro() {
		try {
			ProcessBuilder pb = new ProcessBuilder("reg", "query",
					"HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0", "/v", "Update Revision");// https://www.tenforums.com/drivers-hardware/106331-powershell-script-cpu-information-incl-cpuid.html
			pb.redirectErrorStream(true);
			Process p = pb.start();

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {

				String linea;
				while ((linea = reader.readLine()) != null) {
					linea = linea.trim();
					CrashDetectorLogger.log("Registro Windows: " + linea);
					if (linea.startsWith("Update Revision")) {
						String[] partes = linea.split("\\s+");
						if (partes.length >= 3) {
							String hexLittleEndian = partes[partes.length - 1].trim();
							return convertirRegBinaryMicrocodigo(hexLittleEndian);
						}
					}
				}
			}
		} catch (Exception e) {
			CrashDetectorLogger.log("Error leyendo registro de Windows: " + e.getMessage());
		}
		CrashDetectorLogger.log("No se encontró la clave 'Update Revision' en el registro.");
		return "";
	}

	private static String convertirRegBinaryMicrocodigo(String hexLittleEndian) {
		if (hexLittleEndian == null)
			return "";

		hexLittleEndian = hexLittleEndian.trim().replaceAll("[^0-9A-Fa-f]", "");
		if (hexLittleEndian.length() == 0 || (hexLittleEndian.length() % 2) != 0) {
			return "";
		}

		StringBuilder bigEndian = new StringBuilder();
		for (int i = hexLittleEndian.length(); i > 0; i -= 2) {
			bigEndian.append(hexLittleEndian, i - 2, i);
		}

		try {
			long valor = Long.parseUnsignedLong(bigEndian.toString(), 16);
			return "0x" + Long.toHexString(valor).toUpperCase(Locale.ENGLISH);
		} catch (NumberFormatException e) {
			return "";
		}
	}

	// ==========================================================
	// HELPERS DE EJECUCIÓN
	// ==========================================================

	private static String ejecutarComando(String... comando) {
		try {
			ProcessBuilder pb = new ProcessBuilder(comando);
			pb.redirectErrorStream(true);
			Process p = pb.start();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				String linea;
				StringBuilder resultado = new StringBuilder();
				while ((linea = reader.readLine()) != null) {
					linea = linea.trim();
					if (linea.isEmpty() || linea.toLowerCase().contains("revision")
							|| linea.toLowerCase().contains("manufacturer")) {
						continue; // Saltar cabeceras
					}
					resultado.append(linea);
				}
				return resultado.toString().trim();
			}
		} catch (Exception e) {
			return "";
		}
	}

	private static String parsearProcCpuinfo(String claveBuscada) {
		try {
			Process p = Runtime.getRuntime().exec("cat /proc/cpuinfo");
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
				String linea;
				while ((linea = reader.readLine()) != null) {
					if (linea.toLowerCase().contains(claveBuscada.toLowerCase())) {
						String[] partes = linea.split(":");
						if (partes.length > 1) {
							return partes[1].trim();
						}
					}
				}
			}
		} catch (Exception e) {
			// Ignorar
		}
		return "";
	}
}