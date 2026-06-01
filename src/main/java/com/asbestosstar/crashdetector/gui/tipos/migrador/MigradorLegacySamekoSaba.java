package com.asbestosstar.crashdetector.gui.tipos.migrador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class MigradorLegacySamekoSaba extends MigradorLegacyGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "sameko_saba_migrador_legacy";

	public File imagenSaba = Statics.carpeta.resolve("imagenes/saba.png").toFile();
	public File logoCrashAssistant = Statics.carpeta.resolve("imagenes/logo_crashassistant.png").toFile();

	public ConfigColor colorFondo = ConfigColor.de("tema.sameko_saba.migrador.fondo", new Color(224, 248, 255));
	public ConfigColor colorTexto = ConfigColor.de("tema.sameko_saba.migrador.texto", new Color(20, 70, 95));
	public ConfigColor colorBoton = ConfigColor.de("tema.sameko_saba.migrador.boton", new Color(95, 205, 230));
	public ConfigColor colorPanel = ConfigColor.de("tema.sameko_saba.migrador.panel", new Color(245, 253, 255));
	public ConfigColor colorBorde = ConfigColor.de("tema.sameko_saba.migrador.borde", new Color(135, 220, 240));

	public JPanel panelPrincipal;
	public JPanel panelIzquierdo;
	public JPanel panelDerecho;
	public JLabel etiquetaImagen;
	public JLabel titulo;
	public JTextArea descripcion;
	public JButton botonCrashAssistant;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {
		registrarAsistente(AsistenteCrashAssistant.ID, AsistenteCrashAssistant::new);

		setTitle(MonitorDePID.idioma.migradorLegacyTitulo());
		setSize(780, 520);
		setModal(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		panelPrincipal = new JPanel(new BorderLayout(10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelPrincipal.setBackground(colorFondo.obtener());

		panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setPreferredSize(new Dimension(240, 0));
		panelIzquierdo.setBackground(colorPanel.obtener());
		panelIzquierdo.setBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 2));

		etiquetaImagen = new JLabel();
		etiquetaImagen.setHorizontalAlignment(SwingConstants.CENTER);
		etiquetaImagen.setVerticalAlignment(SwingConstants.TOP);
		cargarImagen(etiquetaImagen, imagenSaba, 220, 390);
		panelIzquierdo.add(etiquetaImagen, BorderLayout.CENTER);

		panelDerecho = new JPanel(new BorderLayout(8, 8));
		panelDerecho.setBackground(colorFondo.obtener());

		titulo = new JLabel(MonitorDePID.idioma.migradorLegacyTitulo());
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		titulo.setForeground(colorTexto.obtener());

		descripcion = new JTextArea(MonitorDePID.idioma.migradorLegacyDescripcion());
		descripcion.setEditable(false);
		descripcion.setLineWrap(true);
		descripcion.setWrapStyleWord(true);
		descripcion.setOpaque(false);
		descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		descripcion.setForeground(colorTexto.obtener());
		descripcion.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

		JPanel panelTexto = new JPanel(new BorderLayout());
		panelTexto.setBackground(colorFondo.obtener());
		panelTexto.add(titulo, BorderLayout.NORTH);
		panelTexto.add(descripcion, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelBotones.setBackground(colorFondo.obtener());

		botonCrashAssistant = new JButton(MonitorDePID.idioma.migradorLegacyCrashAssistant());
		botonCrashAssistant.setIcon(cargarIcono(logoCrashAssistant, 32, 32));
		botonCrashAssistant.setHorizontalAlignment(SwingConstants.LEFT);
		botonCrashAssistant.setPreferredSize(new Dimension(320, 52));
		botonCrashAssistant.setBackground(colorBoton.obtener());
		botonCrashAssistant.setForeground(colorTexto.obtener());
		botonCrashAssistant.setFocusPainted(false);
		botonCrashAssistant.addActionListener(e -> abrirAsistente(AsistenteCrashAssistant.ID));

		panelBotones.add(botonCrashAssistant);

		panelDerecho.add(panelTexto, BorderLayout.CENTER);
		panelDerecho.add(panelBotones, BorderLayout.SOUTH);

		panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
		panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

		add(panelPrincipal, BorderLayout.CENTER);

		setVisible(true);
	}

	public void cargarImagen(JLabel label, File archivo, int ancho, int alto) {
		if (archivo != null && archivo.exists()) {
			ImageIcon icon = new ImageIcon(archivo.getAbsolutePath());
			Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
			label.setIcon(new ImageIcon(img));
		} else {
			label.setText(MonitorDePID.idioma.imagenNoEncontrada());
			label.setForeground(colorTexto.obtener());
		}
	}

	public ImageIcon cargarIcono(File archivo, int ancho, int alto) {
		if (archivo != null && archivo.exists()) {
			ImageIcon icon = new ImageIcon(archivo.getAbsolutePath());
			Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
			return new ImageIcon(img);
		}
		return null;
	}

	@Override
	public void recargarApariencia() {
		if (panelPrincipal != null) {
			panelPrincipal.setBackground(colorFondo.obtener());
		}
		if (panelIzquierdo != null) {
			panelIzquierdo.setBackground(colorPanel.obtener());
			panelIzquierdo.setBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 2));
		}
		if (panelDerecho != null) {
			panelDerecho.setBackground(colorFondo.obtener());
		}
		if (titulo != null) {
			titulo.setForeground(colorTexto.obtener());
		}
		if (descripcion != null) {
			descripcion.setForeground(colorTexto.obtener());
		}
		if (botonCrashAssistant != null) {
			botonCrashAssistant.setBackground(colorBoton.obtener());
			botonCrashAssistant.setForeground(colorTexto.obtener());
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<ElementoConfig>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		ret.add(colorFondo);

		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		ret.add(colorTexto);

		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		ret.add(colorBoton);

		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPanel());
		ret.add(colorPanel);

		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		ret.add(colorBorde);

		return ret;
	}
}