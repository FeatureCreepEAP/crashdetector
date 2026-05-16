package com.asbestosstar.crashdetector.gui.elementos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;

public class ElementoOverlayCarga extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JLabel gifCarga;
	private final JButton botonCorredorUma;
	private final JPanel panelCentral;

	public ElementoOverlayCarga() {
		super(new BorderLayout());

		setOpaque(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panelCentral.setOpaque(true);
		panelCentral.setBackground(new Color(0, 0, 0, 140));
		panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panelCentral.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		gifCarga = new JLabel();
		gifCarga.setHorizontalAlignment(JLabel.CENTER);
		gifCarga.setHorizontalTextPosition(JLabel.CENTER);
		gifCarga.setVerticalTextPosition(JLabel.BOTTOM);
		gifCarga.setForeground(Color.WHITE);
		gifCarga.setFont(gifCarga.getFont().deriveFont(Font.BOLD, 14f));
		gifCarga.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		gifCarga.setAlignmentX(CENTER_ALIGNMENT);

		botonCorredorUma = new JButton("ウマのランナー");
		botonCorredorUma.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		botonCorredorUma.setAlignmentX(CENTER_ALIGNMENT);
		botonCorredorUma.addActionListener(e -> abrirCorredorUma());

		panelCentral.add(gifCarga);
		panelCentral.add(Box.createVerticalStrut(10));
		panelCentral.add(botonCorredorUma);

		JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.CENTER));
		contenedor.setOpaque(false);
		contenedor.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		contenedor.add(panelCentral);

		add(contenedor, BorderLayout.CENTER);

		recargarContenido();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(0, 0, 0, 70));
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

	public void recargarContenido() {
		ImageIcon icono = new ImageIcon(Statics.carpeta.resolve("imagenes/padoru.gif").toString());

		if (icono.getIconWidth() <= 0) {
			gifCarga.setIcon(null);
			gifCarga.setText("… " + MonitorDePID.idioma.cargando());
		} else {
			gifCarga.setIcon(icono);
			gifCarga.setText(MonitorDePID.idioma.cargando());
		}
	}

	private void abrirCorredorUma() {
		try {
			File archivo = Statics.carpeta.resolve("corredoruma.htm").toFile();

			if (!archivo.isFile()) {
				CrashDetectorLogger.log("No existe corredoruma.htm: " + archivo.getAbsolutePath());
				return;
			}

			URI uri = archivo.toURI();

			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(uri);
			} else if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
				Desktop.getDesktop().open(archivo);
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}
}