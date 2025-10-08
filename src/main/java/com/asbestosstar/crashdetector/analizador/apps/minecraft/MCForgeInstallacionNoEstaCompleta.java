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
 * https://github.com/HMCL-dev/HMCL/blob/77dc5dbe06ef1ca1cc08cd6c47525999d92a992a/HMCLCore/src/main/java/org/jackhuang/hmcl/game/CrashReportAnalyzer.java#L129C1-L129C479
 */
public class MCForgeInstallacionNoEstaCompleta implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Patrón para detectar instalación incompleta de Forge
		Pattern pattern = Pattern.compile(
				"(java\\.io\\.UncheckedIOException: java\\.io\\.IOException: Invalid paths argument, contained no existing paths: \\[(.*?)(forge-(.*?)-client\\.jar|fmlcore-(.*?)\\.jar)\\])|"
						+ "(Failed to find Minecraft resource version (.*?) at (.*?)forge-(.*?)-client\\.jar)|"
						+ "(Cannot find launch target fmlclient, unable to launch)|"
						+ "(java\\.lang\\.IllegalStateException: Could not find net/minecraft/client/Minecraft\\.class in classloader SecureModuleClassLoader)");

		Matcher matcher = pattern.matcher(contenidoConsola);
		if (matcher.find()) {
			activado = true;

			// Identificar el tipo específico de error para mostrar un mensaje preciso
			if (matcher.group(1) != null) {
				// Caso 1: Archivos JAR faltantes
				String archivo = matcher.group(3) != null ? matcher.group(3)
						: (matcher.group(4) != null ? matcher.group(4) : "forge-xxx-client.jar");
				mensaje = MonitorDePID.idioma.forgeArchivosFaltantes(archivo);
			} else if (matcher.group(5) != null) {
				// Caso 2: Versión de Minecraft no encontrada
				String version = matcher.group(6);
				String archivo = matcher.group(7);
				mensaje = MonitorDePID.idioma.forgeVersionNoEncontrada(version, archivo);
			} else if (matcher.group(8) != null) {
				// Caso 3: Target fmlclient no encontrado
				mensaje = MonitorDePID.idioma.forgeTargetFmlclientNoEncontrado();
			} else if (matcher.group(9) != null) {
				// Caso 4: Clase Minecraft faltante
				mensaje = MonitorDePID.idioma.forgeClaseMinecraftFaltante();
			} else {
				// Caso general
				mensaje = MonitorDePID.idioma.forgeInstallacionNoCompleta();
			}
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
		return mensaje;
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
					// Aquí se podría mostrar un diálogo con instrucciones detalladas
					JOptionPane.showMessageDialog(null, MonitorDePID.idioma.instrucciones_reinstalar_forge(),
							MonitorDePID.idioma.titulo_instrucciones_reinstaler_mcforge(),
							JOptionPane.INFORMATION_MESSAGE);
				}).construir();
	}

	/**
	 * Abre una URL en el navegador predeterminado del sistema
	 * 
	 * @param url La dirección web a abrir
	 */
	private void abrirEnNavegador(String url) {
		try {
			if (java.awt.Desktop.isDesktopSupported()) {
				java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
			}
		} catch (Exception e) {
			// Manejar la excepción adecuadamente en la implementación real
			e.printStackTrace();
		}
	}


	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "mcforge_installacion_no_esta_completa";
	}
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;//TODO
	}
	
	
}