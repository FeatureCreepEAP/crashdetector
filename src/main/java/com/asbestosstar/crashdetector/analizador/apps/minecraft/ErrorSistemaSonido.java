package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Analiza errores relacionados con el sistema de sonido, específicamente el
 * error "Error starting SoundSystem. Turning off sounds & music" asociado con
 * SoundPhysicsMod.
 */
public class ErrorSistemaSonido implements Verificaciones {

	private boolean activado = false;

	private String mensaje = "";
	private String enlaceHtml = "";

	private static final String TEXTO_ERROR = "Error starting SoundSystem. Turning off sounds & music";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca el mensaje: "Error starting SoundSystem. Turning off sounds & music" en
	 * la línea actual y registra el enlace correspondiente.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea.contains(TEXTO_ERROR)) {
			mensaje = MonitorDePID.idioma.errorSistemaSonido() + Verificaciones.nl_html;
			if (kb4515384PuedeSerCausaDelSonido()) {
				mensaje += MonitorDePID.idioma.errorSistemaSonidoKB4515384() + Verificaciones.nl_html;
			}
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorSistemaSonido();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 750.0f; // Prioridad media-alta - el juego funciona pero sin sonido
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_sistema_sonido();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1_sistema_sonido())
				.agregarEtiqueta(MonitorDePID.idioma.paso2_sistema_sonido())
				.agregarEtiqueta(MonitorDePID.idioma.paso3_sistema_sonido()).construir();
	}

	@Override
	public String id() {
		return "error_en_sistema_sonido";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se activó, y</li>
	 * <li>El trazo contiene exactamente el mensaje de error del sistema de
	 * sonido.</li>
	 * </ul>
	 * Es intencionadamente conservador: mejor un falso negativo que marcar un trazo
	 * que no corresponda a este problema.
	 * </p>
	 */
	@Override
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_ERROR };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	// CD_KB4515384_SONIDO_INICIO
	// La comprobación sólo se ejecuta cuando ya se detectó el fallo del sistema de
	// sonido. El resultado se guarda en caché para no repetir procesos externos.
	private static volatile int estadoKB4515384 = -1;
	private static final Object bloqueoKB4515384 = new Object();

	private static boolean kb4515384PuedeSerCausaDelSonido() {
		int estado = estadoKB4515384;
		if (estado >= 0) {
			return estado == 1;
		}

		synchronized (bloqueoKB4515384) {
			if (estadoKB4515384 < 0) {
				estadoKB4515384 = detectarKB4515384Activa() ? 1 : 0;
			}
			return estadoKB4515384 == 1;
		}
	}

	private static boolean detectarKB4515384Activa() {
		String sistema = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH);
		if (!sistema.contains("win")) {
			return false;
		}

		EstadoActualizacionWindows estado = consultarEstadoConPowerShell();
		if (estado == null) {
			estado = consultarEstadoConRegYWmic();
		}

		if (estado == null || !estado.kb4515384Instalada) {
			return false;
		}

		// KB4517211 corrigió el problema de audio. También se considera corregido si
		// Windows 10 1903 ya tiene la compilación 18362.387 o una posterior.
		if (estado.kb4517211Instalada) {
			return false;
		}

		if (estado.compilacion != null) {
			if (estado.compilacion.intValue() != 18362) {
				return false;
			}
			if (estado.revision != null && estado.revision.intValue() >= 387) {
				return false;
			}
		}

		return true;
	}

	private static EstadoActualizacionWindows consultarEstadoConPowerShell() {
		String script = "$cv=Get-ItemProperty 'HKLM:\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion';"
				+ "$k=@(Get-HotFix -Id KB4515384,KB4517211 -ErrorAction SilentlyContinue | ForEach-Object {$_.HotFixID});"
				+ "Write-Output ('CDKB|'+$cv.CurrentBuildNumber+'|'+$cv.UBR+'|'+($k -contains 'KB4515384')+'|'+($k -contains 'KB4517211'))";

		String salida = ejecutarComandoBreve(
				new String[] { "powershell.exe", "-NoLogo", "-NoProfile", "-NonInteractive", "-Command", script },
				1800L);

		if (salida == null) {
			return null;
		}

		for (String linea : salida.split("\\R")) {
			linea = linea.trim();
			if (!linea.startsWith("CDKB|")) {
				continue;
			}

			String[] partes = linea.split("\\|", -1);
			if (partes.length < 5) {
				return null;
			}

			EstadoActualizacionWindows estado = new EstadoActualizacionWindows();
			estado.compilacion = enteroODesconocido(partes[1]);
			estado.revision = enteroODesconocido(partes[2]);
			estado.kb4515384Instalada = "true".equalsIgnoreCase(partes[3].trim());
			estado.kb4517211Instalada = "true".equalsIgnoreCase(partes[4].trim());
			return estado;
		}

		return null;
	}

	private static EstadoActualizacionWindows consultarEstadoConRegYWmic() {
		String compilacion = ejecutarComandoBreve(new String[] { "reg.exe", "query",
				"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion", "/v", "CurrentBuildNumber" }, 900L);
		String revision = ejecutarComandoBreve(new String[] { "reg.exe", "query",
				"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion", "/v", "UBR" }, 900L);
		String revisiones = ejecutarComandoBreve(new String[] { "wmic.exe", "qfe", "get", "HotFixID" }, 1300L);

		if (compilacion == null && revision == null && revisiones == null) {
			return null;
		}

		EstadoActualizacionWindows estado = new EstadoActualizacionWindows();
		estado.compilacion = ultimoEnteroDecimal(compilacion);
		estado.revision = ultimoEnteroFlexible(revision);

		String lista = revisiones == null ? "" : revisiones.toUpperCase(Locale.ENGLISH);
		estado.kb4515384Instalada = lista.contains("KB4515384");
		estado.kb4517211Instalada = lista.contains("KB4517211");
		return estado;
	}

	private static String ejecutarComandoBreve(String[] comando, long esperaMilisegundos) {
		Process proceso = null;
		try {
			ProcessBuilder constructor = new ProcessBuilder(comando);
			constructor.redirectErrorStream(true);
			proceso = constructor.start();

			if (!proceso.waitFor(esperaMilisegundos, TimeUnit.MILLISECONDS)) {
				proceso.destroyForcibly();
				return null;
			}

			StringBuilder salida = new StringBuilder();
			try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
				String linea;
				while ((linea = lector.readLine()) != null) {
					if (salida.length() > 0) {
						salida.append('\n');
					}
					salida.append(linea);
				}
			}
			return salida.toString();
		} catch (Throwable ignorado) {
			return null;
		} finally {
			if (proceso != null && proceso.isAlive()) {
				proceso.destroyForcibly();
			}
		}
	}

	private static Integer enteroODesconocido(String texto) {
		try {
			return Integer.valueOf(texto.trim());
		} catch (Exception ignorado) {
			return null;
		}
	}

	private static Integer ultimoEnteroDecimal(String texto) {
		if (texto == null) {
			return null;
		}
		java.util.regex.Matcher coincidencia = java.util.regex.Pattern.compile("(\\d+)").matcher(texto);
		Integer ultimo = null;
		while (coincidencia.find()) {
			try {
				ultimo = Integer.valueOf(coincidencia.group(1));
			} catch (NumberFormatException ignorado) {
				// Continuar buscando.
			}
		}
		return ultimo;
	}

	private static Integer ultimoEnteroFlexible(String texto) {
		if (texto == null) {
			return null;
		}

		java.util.regex.Matcher hexadecimal = java.util.regex.Pattern.compile("0x([0-9a-fA-F]+)").matcher(texto);
		Integer ultimoHex = null;
		while (hexadecimal.find()) {
			try {
				ultimoHex = Integer.valueOf(hexadecimal.group(1), 16);
			} catch (NumberFormatException ignorado) {
				// Continuar buscando.
			}
		}
		return ultimoHex != null ? ultimoHex : ultimoEnteroDecimal(texto);
	}

	private static final class EstadoActualizacionWindows {
		Integer compilacion;
		Integer revision;
		boolean kb4515384Instalada;
		boolean kb4517211Instalada;
	}
	// CD_KB4515384_SONIDO_FIN

}