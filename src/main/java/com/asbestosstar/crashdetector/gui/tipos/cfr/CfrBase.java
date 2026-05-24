package com.asbestosstar.crashdetector.gui.tipos.cfr;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

/**
 * Clase base abstracta para todas las interfaces gráficas de CFR. Proporciona
 * funcionalidad común, incluyendo el manejo de URLs cfr:// y métodos estáticos
 * de utilidad.
 */
public abstract class CfrBase extends JFrame implements CrashDetectorGUI {

	/**
	 * Registro global de tipos de GUI CFR por identificador.
	 */
	public static final Map<String, Supplier<CfrBase>> GUIS = new HashMap<>();

	/**
	 * Procesa una URL del esquema cfr://. El formato esperado es:
	 * cfr://<nombreCompletoDeClase>
	 *
	 * @param url la URL con esquema cfr://
	 */
	public abstract void procesarHipervinculo(String url);

	/**
	 * Método estático para verificar si CFR está disponible en el sistema.
	 *
	 * @return true si CFR está instalado; false en caso contrario.
	 */
	public static boolean estaCfrInstalado() {
		return BuscarParaCFR.estaInstalado();
	}

	/**
	 * Método estático para descompilar una clase dada su nombre. Usa
	 * Buscardor.obtenerBytesDeClase y CFR internamente.
	 *
	 * @param nombreClase nombre completo de la clase (ej. java.lang.Object)
	 * @return código fuente descompilado, o null si no se pudo encontrar la clase.
	 *         Si CFR no está instalado y el entorno es gráfico, se muestra un
	 *         diálogo y se devuelve null.
	 */

	public static String descompilarClase(String nombreClase) {
		if (!estaCfrInstalado()) {
			CrashDetectorLogger.log("No Tienese CFR");
			String mensajeHtml = MonitorDePID.idioma.necesitasInstalarCfr() + Verificaciones.nl_html
					+ MonitorDePID.idioma.noticiaLegalCFR();

			if (!java.awt.GraphicsEnvironment.isHeadless()) {
				try {
					mostrarDialogoInstalacionCFR(mensajeHtml);
				} catch (HeadlessException ignored) {
				}
			}
			return null;
		}
		CrashDetectorLogger.log("Tienese CFR");

		return DescompilarCFR.descompilar(nombreClase);
	}

	/**
	 * Muestra un diálogo personalizado con dos botones: "Descargar CFR" y "Abrir
	 * carpeta de instalación".
	 */
	private static void mostrarDialogoInstalacionCFR(String mensajeHtml) {
		// Crear panel personalizado
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JEditorPane editor = new JEditorPane("text/html", "<html>" + mensajeHtml + "</html>");
		editor.setEditable(false);
		editor.setOpaque(false);
		editor.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(editor);

		// Crear diálogo
		JDialog dialogo = new JDialog((java.awt.Frame) null, MonitorDePID.idioma.tituloCfrSakura(), true);
		dialogo.setLayout(new java.awt.BorderLayout());
		dialogo.add(panel, java.awt.BorderLayout.CENTER);

		// Botones
		JButton btnDescargar = new JButton(MonitorDePID.idioma.botonDescargarCfr());
		JButton btnAbrirCarpeta = new JButton(MonitorDePID.idioma.botonAbrirCarpetaCfr());

		btnDescargar.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URI("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar"));
			} catch (Exception ex) {
				CrashDetectorLogger.logException(ex);
			}
		});

		btnAbrirCarpeta.addActionListener(e -> {
			Path carpeta = java.nio.file.Paths.get(System.getProperty("user.home"), "crash_detector", "deps");
			try {
				if (!Files.exists(carpeta)) {
					Files.createDirectories(carpeta);
				}
				Desktop.getDesktop().open(carpeta.toFile());
			} catch (IOException | UnsupportedOperationException ex) {
				// Si open() no está soportado (ej. algunos entornos sin gestor de archivos),
				// abrir en navegador
				try {
					Desktop.getDesktop().browse(carpeta.toUri());
				} catch (Exception ex2) {
					CrashDetectorLogger.logException(ex2);
				}

			} catch (Exception ex) {
				CrashDetectorLogger.logException(ex);
			}
		});

		JPanel panelBotones = new JPanel();
		panelBotones.add(btnDescargar);
		panelBotones.add(btnAbrirCarpeta);
		dialogo.add(panelBotones, java.awt.BorderLayout.SOUTH);

		dialogo.pack();
		dialogo.setLocationRelativeTo(null);
		dialogo.setResizable(false);
		dialogo.setVisible(true);
	}

	/**
	 * Obtiene el archivo JAR de CFR si está instalado.
	 *
	 * @return File apuntando al JAR de CFR, o null si no se encuentra.
	 */
	public static File obtenerCfrJar() {
		return BuscarParaCFR.encontrarCfr();
	}
}