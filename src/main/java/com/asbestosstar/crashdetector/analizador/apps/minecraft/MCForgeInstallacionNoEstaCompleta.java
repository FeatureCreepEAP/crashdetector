package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta instalaciones incompletas de Forge y excepciones relacionadas.
 */
public class MCForgeInstallacionNoEstaCompleta implements VerificacionRapida {

	private boolean posibleForgeIncompleto = false;
	private boolean activado = false;

	private String mensaje = "";
	private String enlace = "";

	private static final String ERROR_INVALID_PATHS = "java.io.UncheckedIOException: java.io.IOException: Invalid paths argument, contained no existing paths: [";

	private static final String ERROR_FAILED_TO_FIND_VERSION = "Failed to find Minecraft resource version ";

	private static final String ERROR_CANNOT_FIND_TARGET = "Cannot find launch target fmlclient, unable to launch";

	private static final String ERROR_MINECRAFT_CLASS_MISSING = "java.lang.IllegalStateException: Could not find net/minecraft/client/Minecraft.class in classloader SecureModuleClassLoader";

	private static final String FORGE_PREFIX = "forge-";
	private static final String FMLCORE_PREFIX = "fmlcore-";
	private static final String CLIENT_JAR = "client.jar";
	private static final String JAR = ".jar";
	private static final String AT = " at ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ERROR_INVALID_PATHS, ERROR_FAILED_TO_FIND_VERSION, ERROR_CANNOT_FIND_TARGET,
				ERROR_MINECRAFT_CLASS_MISSING };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (contieneProblemaForge(evento.linea)) {
			posibleForgeIncompleto = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificacion global ligera.
	 *
	 * Se ejecuta primero. No se limpian campos porque esta verificacion puede
	 * ejecutarse sobre varios archivos de log con la misma instancia.
	 */
	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(ERROR_INVALID_PATHS) || contenido.contains(ERROR_FAILED_TO_FIND_VERSION)
				|| contenido.contains(ERROR_CANNOT_FIND_TARGET) || contenido.contains(ERROR_MINECRAFT_CLASS_MISSING)) {
			posibleForgeIncompleto = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleForgeIncompleto && !activado;
	}

	/**
	 * Verificacion por linea.
	 *
	 * Aquí se detecta el problema exacto y se guarda el enlace hacia la línea del
	 * log.
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleForgeIncompleto || activado || linea == null || linea.isEmpty()) {
			return;
		}

		// 1) Detectar archivos faltantes en rutas de Forge/FML
		String archivoFaltante = extraerArchivoForgeFaltante(linea);
		if (archivoFaltante != null) {
			mensaje = MonitorDePID.idioma.forgeArchivosFaltantes(archivoFaltante);
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
			return;
		}

		// 2) Detectar version de recurso Minecraft no encontrada
		DatosVersionForge datosVersion = extraerVersionForgeNoEncontrada(linea);
		if (datosVersion != null) {
			mensaje = MonitorDePID.idioma.forgeVersionNoEncontrada(datosVersion.version, datosVersion.ruta);
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
			return;
		}

		// 3) Detectar target fmlclient faltante
		if (linea.indexOf(ERROR_CANNOT_FIND_TARGET) >= 0) {
			mensaje = MonitorDePID.idioma.forgeTargetFmlclientNoEncontrado();
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
			return;
		}

		// 4) Detectar clase principal de Minecraft faltante
		if (linea.indexOf(ERROR_MINECRAFT_CLASS_MISSING) >= 0) {
			mensaje = MonitorDePID.idioma.forgeClaseMinecraftFaltante();
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	/**
	 * Extrae el archivo faltante de un error como:
	 *
	 * java.io.UncheckedIOException: java.io.IOException: Invalid paths argument,
	 * contained no existing paths: [...forge-...client.jar]
	 *
	 * Tambien acepta fmlcore-....jar.
	 */
	private String extraerArchivoForgeFaltante(String texto) {
		int inicio = texto.indexOf(ERROR_INVALID_PATHS);

		if (inicio < 0) {
			return null;
		}

		int inicioLista = inicio + ERROR_INVALID_PATHS.length();
		int finLista = texto.indexOf(']', inicioLista);

		if (finLista < 0) {
			return null;
		}

		String dentroDeCorchetes = texto.substring(inicioLista, finLista);

		String archivo = extraerUltimoArchivoForgeO_fmlcore(dentroDeCorchetes);

		if (archivo == null || archivo.isEmpty()) {
			return null;
		}

		return archivo;
	}

	/**
	 * Extrae datos de un error como:
	 *
	 * Failed to find Minecraft resource version 1.20.1 at ...forge-...-client.jar
	 */
	private DatosVersionForge extraerVersionForgeNoEncontrada(String texto) {
		int inicio = texto.indexOf(ERROR_FAILED_TO_FIND_VERSION);

		if (inicio < 0) {
			return null;
		}

		int inicioVersion = inicio + ERROR_FAILED_TO_FIND_VERSION.length();
		int indiceAt = texto.indexOf(AT, inicioVersion);

		if (indiceAt < 0) {
			return null;
		}

		String version = texto.substring(inicioVersion, indiceAt).trim();

		int inicioRuta = indiceAt + AT.length();
		int finRuta = buscarFinDeRutaJar(texto, inicioRuta);

		if (finRuta <= inicioRuta) {
			return null;
		}

		String ruta = texto.substring(inicioRuta, finRuta).trim();

		if (version.isEmpty() || ruta.isEmpty()) {
			return null;
		}

		if (!ruta.contains(FORGE_PREFIX) || !ruta.endsWith("-client.jar")) {
			return null;
		}

		return new DatosVersionForge(version, ruta);
	}

	/**
	 * Busca el final de una ruta .jar dentro del texto.
	 */
	private int buscarFinDeRutaJar(String texto, int inicio) {
		int indiceJar = texto.indexOf(JAR, inicio);

		if (indiceJar < 0) {
			return -1;
		}

		return indiceJar + JAR.length();
	}

	/**
	 * Detecta el ultimo archivo forge-...client.jar o fmlcore-....jar dentro del
	 * texto recibido.
	 */
	private String extraerUltimoArchivoForgeO_fmlcore(String texto) {
		String mejor = null;

		int indiceForge = 0;
		while (indiceForge >= 0 && indiceForge < texto.length()) {
			indiceForge = texto.indexOf(FORGE_PREFIX, indiceForge);

			if (indiceForge < 0) {
				break;
			}

			int fin = texto.indexOf(CLIENT_JAR, indiceForge);

			if (fin >= 0) {
				mejor = texto.substring(indiceForge, fin + CLIENT_JAR.length());
				indiceForge = fin + CLIENT_JAR.length();
			} else {
				indiceForge += FORGE_PREFIX.length();
			}
		}

		int indiceFmlcore = 0;
		while (indiceFmlcore >= 0 && indiceFmlcore < texto.length()) {
			indiceFmlcore = texto.indexOf(FMLCORE_PREFIX, indiceFmlcore);

			if (indiceFmlcore < 0) {
				break;
			}

			int fin = texto.indexOf(JAR, indiceFmlcore);

			if (fin >= 0) {
				mejor = texto.substring(indiceFmlcore, fin + JAR.length());
				indiceFmlcore = fin + JAR.length();
			} else {
				indiceFmlcore += FMLCORE_PREFIX.length();
			}
		}

		return mejor;
	}

	private boolean contieneProblemaForge(String texto) {
		if (texto == null || texto.isEmpty()) {
			return false;
		}

		return extraerArchivoForgeFaltante(texto) != null || extraerVersionForgeNoEncontrada(texto) != null
				|| texto.indexOf(ERROR_CANNOT_FIND_TARGET) >= 0 || texto.indexOf(ERROR_MINECRAFT_CLASS_MISSING) >= 0;
	}

	private static class DatosVersionForge {
		private final String version;
		private final String ruta;

		private DatosVersionForge(String version, String ruta) {
			this.version = version;
			this.ruta = ruta;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new MCForgeInstallacionNoEstaCompleta();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 750.0f; // Prioridad alta para este tipo de error
	}

	@Override
	public String mensaje() {
		if (!activado) {
			return "";
		}

		return mensaje + " " + MonitorDePID.idioma.forgeInstallacionNoCompleta() + " " + enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_forge_instalacion_no_completa();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucion_para_forge_instalacion_no_completa())
				.agregarBoton(MonitorDePID.idioma.descargar_forge_oficial(), (bool) -> {
					abrirEnNavegador("https://files.minecraftforge.net/");
				}).agregarBoton(MonitorDePID.idioma.reinstalar_forge_correctamente(), (bool) -> {
					// Mostrar instrucciones para reinstalar Forge
					JOptionPane.showMessageDialog(null, MonitorDePID.idioma.instrucciones_reinstalar_forge(),
							MonitorDePID.idioma.titulo_instrucciones_reinstaler_mcforge(),
							JOptionPane.INFORMATION_MESSAGE);
				}).construir();
	}

	/**
	 * Abre una URL en el navegador predeterminado del sistema.
	 *
	 * @param url La URL para abrir
	 */
	private void abrirEnNavegador(String url) {
		try {
			if (java.awt.Desktop.isDesktopSupported()) {
				java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String id() {
		return "mcforge_installacion_no_esta_completa";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		// Verificar si el trazo contiene algun error de instalacion incompleta de Forge
		return contieneProblemaForge(trazo.trace);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}