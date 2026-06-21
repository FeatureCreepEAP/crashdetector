package com.asbestosstar.crashdetector.analizador.general;

import java.awt.Desktop;
import java.net.URI;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.bajo.hw.cpu.intel.ValidadorMicrocodigo;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class RaptorLakeInestable implements Verificaciones {

	// Configuración para desactivar la verificación desde la GUI
	public static ConfigBoolean config = ConfigBoolean.de("ignorar_raptor_lake", false);

	// Versión mínima requerida de microcódigo para la corrección de voltaje
	// 0x129 en hexadecimal = 297 en decimal
	private static final int MICROCODE_FIX_VERSION = 0x129;

	public static boolean hayProblema = false;

	private boolean activado = false;

	private String mensaje = "";
	private String cpuName = "";
	private String enlace = "";

	private static final String TEXTO_PROCESSOR_NAME = "Processor Name:";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_PROCESSOR_NAME };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null || activado || config.obtener()) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		String cpuDetectado = obtenerCPUDeLinea(linea);

		if (cpuDetectado == null || cpuDetectado.isEmpty()) {
			return;
		}

		if (!esRaptorLake(cpuDetectado)) {
			return;
		}

		String microcodeRaw = obtenerMicrocodigoSeguro();

		if (microcodeRaw == null || microcodeRaw.isEmpty() || microcodeRaw.equals("Desconocido")) {
			return;
		}

		int versionActual = parsearMicrocodigo(microcodeRaw);

		if (versionActual < 0) {
			CrashDetectorLogger.log("Formato de microcódigo no reconocido: " + microcodeRaw);
			return;
		}

		if (versionActual < MICROCODE_FIX_VERSION) {
			this.cpuName = cpuDetectado;

			if (consola != null) {
				this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			}

			this.mensaje = construirMensaje(cpuName, microcodeRaw) + " " + enlace;
			hayProblema = true;
			this.activado = true;
		}
	}

	/**
	 * Extrae el nombre del CPU desde una línea como: Processor Name: Intel(R)
	 * Core(TM) i9-14900F
	 *
	 * Sin Pattern/Matcher.
	 */
	private String obtenerCPUDeLinea(String linea) {
		int indice = linea.indexOf(TEXTO_PROCESSOR_NAME);

		if (indice < 0) {
			return null;
		}

		int inicio = indice + TEXTO_PROCESSOR_NAME.length();

		while (inicio < linea.length() && Character.isWhitespace(linea.charAt(inicio))) {
			inicio++;
		}

		if (inicio >= linea.length()) {
			return null;
		}

		return linea.substring(inicio).trim();
	}

	/**
	 * Determina si el CPU es Intel Core i3/i5/i7/i9 de 13ª o 14ª generación.
	 *
	 * Reemplaza: lower.matches(".*i[3579]-[1][34][0-9]{3}[a-z]*.*")
	 *
	 * Sin regex y sin crear una copia completa con toLowerCase().
	 */
	private boolean esRaptorLake(String cpuName) {
		if (cpuName == null || cpuName.isEmpty()) {
			return false;
		}

		for (int i = 0; i < cpuName.length() - 7; i++) {
			char c0 = cpuName.charAt(i);

			if (c0 != 'i' && c0 != 'I') {
				continue;
			}

			char serie = cpuName.charAt(i + 1);
			if (serie != '3' && serie != '5' && serie != '7' && serie != '9') {
				continue;
			}

			if (cpuName.charAt(i + 2) != '-') {
				continue;
			}

			if (cpuName.charAt(i + 3) != '1') {
				continue;
			}

			char gen = cpuName.charAt(i + 4);
			if (gen != '3' && gen != '4') {
				continue;
			}

			if (!hayTresDigitos(cpuName, i + 5)) {
				continue;
			}

			// Acepta sufijos como K, KF, F, KS, HX, etc.
			return true;
		}

		return false;
	}

	private boolean hayTresDigitos(String texto, int inicio) {
		return inicio + 2 < texto.length() && Character.isDigit(texto.charAt(inicio))
				&& Character.isDigit(texto.charAt(inicio + 1)) && Character.isDigit(texto.charAt(inicio + 2));
	}

	/**
	 * Obtiene el microcódigo del sistema de forma segura.
	 */
	private String obtenerMicrocodigoSeguro() {
		try {
			return ValidadorMicrocodigo.obtenerRevisionMicrocodigo();
		} catch (Throwable t) {
			CrashDetectorLogger
					.log("No se pudo validar microcódigo para Raptor Lake (excepción controlada): " + t.getMessage());
			return null;
		}
	}

	/**
	 * Parsea microcódigo en formato: 0x129 297
	 *
	 * Devuelve -1 si no se puede parsear.
	 */
	private int parsearMicrocodigo(String microcodeRaw) {
		if (microcodeRaw == null) {
			return -1;
		}

		String limpio = microcodeRaw.trim();

		if (limpio.isEmpty()) {
			return -1;
		}

		boolean esHex = empiezaConIgnoreCase(limpio, "0x");

		if (esHex) {
			limpio = limpio.substring(2).trim();
		}

		if (limpio.isEmpty()) {
			return -1;
		}

		try {
			return esHex ? Integer.parseInt(limpio, 16) : Integer.parseInt(limpio);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private boolean empiezaConIgnoreCase(String texto, String prefijo) {
		if (texto == null || prefijo == null || prefijo.length() > texto.length()) {
			return false;
		}

		return texto.regionMatches(true, 0, prefijo, 0, prefijo.length());
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

}