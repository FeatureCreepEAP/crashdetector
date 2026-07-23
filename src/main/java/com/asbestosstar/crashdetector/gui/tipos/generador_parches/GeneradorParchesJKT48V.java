package com.asbestosstar.crashdetector.gui.tipos.generador_parches;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/** Implementación predeterminada inspirada en JKT48V. */
public final class GeneradorParchesJKT48V extends GeneradorParchesGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "generador_parches_jkt48v";

	public ConfigColor colorFondo = ConfigColor.de("tema.generador.parches.jkt48v.fondo", new Color(9, 24, 51));
	public ConfigColor colorPanel = ConfigColor.de("tema.generador.parches.jkt48v.panel", new Color(20, 45, 82));
	public ConfigColor colorPanelClaro = ConfigColor.de("tema.generador.parches.jkt48v.panel.claro",
			new Color(235, 244, 255));
	public ConfigColor colorTexto = ConfigColor.de("tema.generador.parches.jkt48v.texto", new Color(245, 249, 255));
	public ConfigColor colorTextoOscuro = ConfigColor.de("tema.generador.parches.jkt48v.texto.oscuro",
			new Color(18, 35, 64));
	public ConfigColor colorAcento = ConfigColor.de("tema.generador.parches.jkt48v.acento", new Color(236, 72, 113));
	public ConfigColor colorAcentoSecundario = ConfigColor.de("tema.generador.parches.jkt48v.acento.secundario",
			new Color(74, 157, 230));
	public ConfigColor colorBorde = ConfigColor.de("tema.generador.parches.jkt48v.borde", new Color(128, 191, 239));
	public ConfigColor colorCampo = ConfigColor.de("tema.generador.parches.jkt48v.campo", Color.WHITE);
	public ConfigColor colorEstado = ConfigColor.de("tema.generador.parches.jkt48v.estado", new Color(14, 35, 69));

	public JLabel lblImagen;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public JPanel crearPanelTematico() {
		JPanel panel = new JPanel(new BorderLayout(8, 8));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 2),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		lblImagen = new JLabel("", SwingConstants.CENTER);
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagen.setVerticalAlignment(SwingConstants.TOP);
		panel.add(lblImagen, BorderLayout.NORTH);

		JTextArea leyenda = new JTextArea(MonitorDePID.idioma.generadorParchesMcpInstruccion());
		leyenda.setEditable(false);
		leyenda.setLineWrap(true);
		leyenda.setWrapStyleWord(true);
		leyenda.setOpaque(false);
		leyenda.setBorder(BorderFactory.createEmptyBorder(12, 5, 5, 5));
		panel.add(leyenda, BorderLayout.CENTER);

		cargarImagen();
		return panel;
	}

	private void cargarImagen() {
		try {
			Path ruta = Statics.carpeta.resolve("imagenes/jkt48v.png");
			if (!Files.exists(ruta)) {
				MonitorDePID.copiarACarpetaDesdeJar("/imagenes/jkt48v.png", ruta.toFile());
			}

			ImageIcon original;
			java.net.URL recurso = getClass().getResource("/imagenes/jkt48v.png");
			if (recurso != null) {
				original = new ImageIcon(recurso);
			} else {
				original = new ImageIcon(ruta.toString());
			}

			if (original.getIconWidth() > 0) {
				int ancho = 245;
				int alto = Math.max(1, original.getIconHeight() * ancho / original.getIconWidth());
				Image imagen = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
				lblImagen.setIcon(new ImageIcon(imagen));
				lblImagen.setPreferredSize(new Dimension(ancho, alto + 8));
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	@Override
	public void aplicarApariencia() {
		if (panelRaiz == null) {
			return;
		}

		panelRaiz.setBackground(colorFondo.obtener());
		if (panelTema != null) {
			panelTema.setBackground(colorPanel.obtener());
			panelTema.setForeground(colorTexto.obtener());
			panelTema.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 2),
							BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		}
		if (panelFormulario != null) {
			panelFormulario.setBackground(colorPanelClaro.obtener());
		}
		if (panelBotones != null) {
			panelBotones.setBackground(colorPanelClaro.obtener());
		}
		if (panelEstado != null) {
			panelEstado.setBackground(colorEstado.obtener());
		}
		if (lblEstado != null) {
			lblEstado.setForeground(colorTexto.obtener());
			lblEstado.setBackground(colorEstado.obtener());
			lblEstado.setOpaque(true);
		}

		for (JTextField campo : new JTextField[] { txtDirectorioProyecto, txtNombreProyecto, txtPaqueteBase,
				txtClaseObjetivo, txtJdk }) {
			estilizarCampo(campo);
		}
		for (JTextArea area : new JTextArea[] { txtDescripcion, txtInstruccionMcp }) {
			if (area != null) {
				area.setBackground(colorPanelClaro.obtener());
				area.setForeground(colorTextoOscuro.obtener());
				area.setCaretColor(colorAcento.obtener());
			}
		}

		for (JButton boton : new JButton[] { btnSeleccionarDirectorio, btnSeleccionarJdk, btnRedHat, btnTribble,
				btnCrear, btnCompilar, btnAbrirCarpeta }) {
			estilizarBoton(boton);
		}

		aplicarColoresRecursivos(panelFormulario);
	}

	private void estilizarCampo(JTextField campo) {
		if (campo == null) {
			return;
		}
		campo.setBackground(colorCampo.obtener());
		campo.setForeground(colorTextoOscuro.obtener());
		campo.setCaretColor(colorAcento.obtener());
		campo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener()),
				BorderFactory.createEmptyBorder(5, 7, 5, 7)));
	}

	private void estilizarBoton(JButton boton) {
		if (boton == null) {
			return;
		}
		boolean primario = boton == btnCrear || boton == btnCompilar;
		boton.setBackground(primario ? colorAcento.obtener() : colorAcentoSecundario.obtener());
		boton.setForeground(colorTexto.obtener());
		boton.setFocusPainted(false);
		boton.setOpaque(true);
		boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener()),
				BorderFactory.createEmptyBorder(6, 11, 6, 11)));
	}

	private void aplicarColoresRecursivos(Component componente) {
		if (componente == null) {
			return;
		}
		if (componente instanceof JLabel && componente != lblEstado && componente != lblImagen) {
			componente.setForeground(colorTextoOscuro.obtener());
		}
		if (componente instanceof JPanel && componente != panelTema && componente != panelEstado) {
			componente.setBackground(colorPanelClaro.obtener());
		}
		if (componente instanceof java.awt.Container) {
			for (Component hijo : ((java.awt.Container) componente).getComponents()) {
				aplicarColoresRecursivos(hijo);
			}
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();
		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoPanel());
		colorPanelClaro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPanelSecundario());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorTextoOscuro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoSuave());
		colorAcento.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorAcento());
		colorAcentoSecundario.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		colorCampo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		colorEstado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPanel());

		elementos.add(colorFondo);
		elementos.add(colorPanel);
		elementos.add(colorPanelClaro);
		elementos.add(colorTexto);
		elementos.add(colorTextoOscuro);
		elementos.add(colorAcento);
		elementos.add(colorAcentoSecundario);
		elementos.add(colorBorde);
		elementos.add(colorCampo);
		elementos.add(colorEstado);
		return elementos;
	}
}
