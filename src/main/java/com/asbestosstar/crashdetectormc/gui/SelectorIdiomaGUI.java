package com.asbestosstar.crashdetectormc.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;

public class SelectorIdiomaGUI extends JDialog {

	public SelectorIdiomaGUI(JFrame parent) {
		super(parent, "Seleccione Idioma", true);
		setSize(500, 400);
		setLocationRelativeTo(parent);
		setLayout(new GridLayout(5, 2, 10, 10));

		add(createButton("\uD83C\uDDF2\uD83C\uDDFD", "Español", "es"));
		add(createButton("\uD83C\uDFF4\uDB40\uDC67\uDB40\uDC62\uDB40\uDC65\uDB40\uDC6E\uDB40\uDC67\uDB40\uDC7F",
				"English", "en"));
		add(createButton("\uD83C\uDDF8\uD83C\uDDE6", "العربية", "ar"));
		add(createButton("\uD83C\uDDE7\uD83C\uDDF7", "Português", "pt"));
		add(createButton("\uD83C\uDDEE\uD83C\uDDF7", "فارسی", "fa"));
		add(createButton("\uD83C\uDDF7\uD83C\uDDFA", "Русский", "ru"));
		add(createButton("\uD83C\uDDE8\uD83C\uDDF3", "简体中文", "zh"));
		add(createButton("\uD83C\uDF0E", "Esperanto", "eo"));
		add(createButton("\uD83C\uDDEF\uD83C\uDDF5", "日本語", "ja"));
		add(createButton("\uD83C\uDDF0\uD83C\uDDF5", "한국어", "ko"));
	}

	private JButton createButton(String banderaUnicode, String nombreNativo, String codigo) {
	    JButton boton = new JButton(banderaUnicode + " " + nombreNativo);
	    boton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
	    boton.addActionListener(e -> {
	        try (BufferedWriter writer = Files.newBufferedWriter(Idioma.archivo.toPath(), 
	                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
	            writer.write(codigo);
	        } catch (IOException e1) {
	            CrashDetectorLogger.logException(e1);
	        } finally {
	            dispose(); // Ensure dialog closes even if error occurs
	        }
	    });
	    return boton;
	}

	public static void obtenerConsolaDeLauncher() {
		JFrame frameBlanco = new JFrame();
		frameBlanco.setUndecorated(true);
		frameBlanco.setSize(0, 0);
		frameBlanco.setLocationRelativeTo(null);
		frameBlanco.setVisible(true);

		CountDownLatch latch = new CountDownLatch(1);

		SwingUtilities.invokeLater(() -> {
			SelectorIdiomaGUI selector = new SelectorIdiomaGUI(frameBlanco);
			selector.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					latch.countDown();
				}
			});
			selector.setVisible(true);
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		frameBlanco.dispose();
	}
}