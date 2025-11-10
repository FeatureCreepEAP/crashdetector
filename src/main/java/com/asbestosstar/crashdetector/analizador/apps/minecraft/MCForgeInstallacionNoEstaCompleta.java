package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

/**
 * Detects incomplete Forge installations and related exceptions.
 */
public class MCForgeInstallacionNoEstaCompleta implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	// Pre-compiled regex patterns to optimize performance
	private static final Pattern PATTERN_INVALID_PATHS = Pattern.compile(
			"java\\.io\\.UncheckedIOException: java\\.io\\.IOException: Invalid paths argument, contained no existing paths: \\[(.*?)(forge-.*?client\\.jar|fmlcore-.*?\\.jar)\\]");

	private static final Pattern PATTERN_FAILED_TO_FIND_VERSION = Pattern
			.compile("Failed to find Minecraft resource version (.*?) at (.*?forge-.*?-client\\.jar)");

	private static final Pattern PATTERN_CANNOT_FIND_LAUNCH_TARGET = Pattern
			.compile("Cannot find launch target fmlclient, unable to launch");

	private static final Pattern PATTERN_MINECRAFT_CLASS_MISSING = Pattern.compile(
			"java\\.lang\\.IllegalStateException: Could not find net/minecraft/client/Minecraft\\.class in classloader SecureModuleClassLoader");

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Check for Forge installation problems using compiled regex patterns
		Matcher matcherInvalidPaths = PATTERN_INVALID_PATHS.matcher(contenidoConsola);
		Matcher matcherFailedToFindVersion = PATTERN_FAILED_TO_FIND_VERSION.matcher(contenidoConsola);
		Matcher matcherCannotFindTarget = PATTERN_CANNOT_FIND_LAUNCH_TARGET.matcher(contenidoConsola);
		Matcher matcherMinecraftClassMissing = PATTERN_MINECRAFT_CLASS_MISSING.matcher(contenidoConsola);

		// Check for each type of issue
		if (matcherInvalidPaths.find()) {
			mensaje = MonitorDePID.idioma.forgeArchivosFaltantes(matcherInvalidPaths.group(2));
			activado = true;
		} else if (matcherFailedToFindVersion.find()) {
			mensaje = MonitorDePID.idioma.forgeVersionNoEncontrada(matcherFailedToFindVersion.group(1),
					matcherFailedToFindVersion.group(2));
			activado = true;
		} else if (matcherCannotFindTarget.find()) {
			mensaje = MonitorDePID.idioma.forgeTargetFmlclientNoEncontrado();
			activado = true;
		} else if (matcherMinecraftClassMissing.find()) {
			mensaje = MonitorDePID.idioma.forgeClaseMinecraftFaltante();
			activado = true;
		} else {
			// mensaje = MonitorDePID.idioma.forgeInstallacionNoCompleta();
			// activado = true;
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
		return 750.0f; // High priority for this type of error
	}

	@Override
	public String mensaje() {
		return mensaje + " " + MonitorDePID.idioma.forgeInstallacionNoCompleta();
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
					// Display instructions for reinstalling Forge
					JOptionPane.showMessageDialog(null, MonitorDePID.idioma.instrucciones_reinstalar_forge(),
							MonitorDePID.idioma.titulo_instrucciones_reinstaler_mcforge(),
							JOptionPane.INFORMATION_MESSAGE);
				}).construir();
	}

	/**
	 * Opens a URL in the system's default browser.
	 * 
	 * @param url The URL to open
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

		// Check if the trace contains any of the patterns indicating an incomplete
		// Forge installation
		String t = trazo.trace;
		return PATTERN_INVALID_PATHS.matcher(t).find() || PATTERN_FAILED_TO_FIND_VERSION.matcher(t).find()
				|| PATTERN_CANNOT_FIND_LAUNCH_TARGET.matcher(t).find()
				|| PATTERN_MINECRAFT_CLASS_MISSING.matcher(t).find();
	}
}
