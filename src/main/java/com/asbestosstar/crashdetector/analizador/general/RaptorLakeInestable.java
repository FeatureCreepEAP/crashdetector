package com.asbestosstar.crashdetector.analizador.general;

import java.awt.Desktop;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.bajo.hw.cpu.intel.ValidadorMicrocodigo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class RaptorLakeInestable implements Verificaciones {

	// Configuración para desactivar la verificación desde la GUI
	public static ConfigBoolean config = ConfigBoolean.de("ignorar_raptor_lake", false);

	// Versión mínima requerida de microcódigo para la corrección de voltaje
	// 0x129 en hexadecimal = 297 en decimal
	private static final int MICROCODE_FIX_VERSION = 0x129;

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		if (config.obtener()) {
			return; // El usuario desactivó esta verificación
		}

		// 1. Buscar el nombre del procesador en el log
		String cpuName = obtenerCPUDeLog(consola.contenido_verificar);

		if (cpuName != null && esRaptorLake(cpuName)) {

			// 2. Obtener microcódigo del sistema de forma segura
			String microcodeRaw = "";
			try {
				// Intentamos llamar al método obtenerRevisionMicrocodigo()
				// NOTA: Asegúrate de que ese método sea público o accesible desde este paquete.
				// Si es privado, deberás cambiarlo a public en ValidadorMicrocodigo.
				microcodeRaw = ValidadorMicrocodigo.obtenerRevisionMicrocodigo();
			} catch (Throwable t) {
				// Si hay cualquier error (seguridad, clase no encontrada, acceso restringido),
				// lo capturamos
				CrashDetectorLogger.log(
						"No se pudo validar microcódigo para Raptor Lake (excepción controlada): " + t.getMessage());
				return; // No activamos la advertencia si no podemos verificar el microcódigo
			}

			// 3. Validar y comparar versión
			if (microcodeRaw != null && !microcodeRaw.isEmpty() && !microcodeRaw.equals("Desconocido")) {
				try {
					// Limpiar el string (puede venir como "0x129", "297", etc.)
					String limpio = microcodeRaw.replace("0x", "").replace("0X", "").trim();
					int versionActual;

					// Intentar parsear como hexadecimal si parece hex, si no como decimal
					if (microcodeRaw.toLowerCase().startsWith("0x")) {
						versionActual = Integer.parseInt(limpio, 16);
					} else {
						versionActual = Integer.parseInt(limpio);
					}

					// Comparación
					if (versionActual < MICROCODE_FIX_VERSION) {
						activado = true;
						mensaje = construirMensaje(cpuName, microcodeRaw);
					}

				} catch (NumberFormatException e) {
					// Si el formato del microcódigo es inválido, no hacemos nada (seguridad)
					CrashDetectorLogger.log("Formato de microcódigo no reconocido: " + microcodeRaw);
				}
			}
		}
	}

	/**
	 * Extrae el nombre del CPU buscando la línea típica en los logs de Minecraft.
	 */
	private String obtenerCPUDeLog(String contenido) {
		// Busca líneas como: "Processor Name: Intel(R) Core(TM) i9-14900F"
		Pattern pattern = Pattern.compile("Processor Name:\\s*(.+)");
		Matcher matcher = pattern.matcher(contenido);
		if (matcher.find()) {
			return matcher.group(1).trim();
		}
		return null;
	}

	/**
	 * Determina si el CPU es de la serie 13 o 14 (Raptor Lake).
	 */
	private boolean esRaptorLake(String cpuName) {
		String lower = cpuName.toLowerCase();
		// Detectamos i5-13xxx, i7-14xxx, i9-13900KS, etc.
		// Patrón: i3/i5/i7/i9 seguido de guión y número empezando por 13 o 14.
		return lower.matches(".*i[3579]-[1][34][0-9]{3}[a-z]*.*");
	}

	private String construirMensaje(String cpu, String microcode) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b>").append(MonitorDePID.idioma.advertenciaRaptorLakeTitulo()).append("</b><br><br>");
		sb.append(MonitorDePID.idioma.advertenciaRaptorLakeDetalle(cpu, microcode, "0x129"));
		return sb.toString();
	}

	@Override
	public Verificaciones nueva() {
		return new RaptorLakeInestable();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return -1500;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreVerificacionRaptorLake();
	}

	@Override
	public QuickFix solucion() {
		QuickFix.Builder builder = new QuickFix.Builder(nombre())
				.agregarBoton(MonitorDePID.idioma.desactivarVerificacionRaptorLake(), retener -> {
					config.escribir(true);
				}, true);

		// Botones para abrir artículos
		try {
			builder.agregarBoton(MonitorDePID.idioma.verArticuloRaptorLake("CPU-Monkey"), retener -> {
				abrirEnNavegador(
						"https://www.cpu-monkey.com/en/article/how_to_check_if_your_intel_13_14_gen_cpu_is_affected_by_instability_problems");
			}, false);

			builder.agregarBoton(MonitorDePID.idioma.verArticuloRaptorLake("Machaddr"), retener -> {
				abrirEnNavegador("https://machaddr.substack.com/p/extensive-analysis-of-the-raptor");
			}, false);
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}

		return builder.construir();
	}

	private void abrirEnNavegador(String url) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				if (desktop.isSupported(Desktop.Action.BROWSE)) {
					desktop.browse(new URI(url));
				}
			}
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false; // No depende de un stack trace específico
	}

	@Override
	public String id() {
		return "raptor_lake_microcode";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}