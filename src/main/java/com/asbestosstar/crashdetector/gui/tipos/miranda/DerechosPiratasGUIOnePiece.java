package com.asbestosstar.crashdetector.gui.tipos.miranda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingConstants;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public class DerechosPiratasGUIOnePiece extends DerechosPiratasGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "one_piece";

	public static File imagen = Statics.carpeta.resolve("imagenes/onepeice.png").toFile();

	private ConfigColor fondo = ConfigColor.de("tema.onepiece.fondo", new Color(20, 20, 20));
	private ConfigColor texto = ConfigColor.de("tema.onepiece.texto", Color.WHITE);
	private ConfigColor caja = ConfigColor.de("tema.onepiece.caja", new Color(35, 35, 35));
	private ConfigColor borde = ConfigColor.de("tema.onepiece.borde", new Color(200, 180, 40));

	private JTextArea areaDescripcion;
	private JButton botonEditar;
	private JButton botonGuardar;
	private JLabel labelImagen;
	private JPanel panelIzq;
	private JPanel panelBotonesLateral;
	private JPanel panelCentro;
	private JScrollPane scrollDescripcion;

	@Override
	public void init() {
		setTitle(MonitorDePID.idioma.infoDeDerechosMiranda());
		setModal(true);
		setLayout(new BorderLayout());

		// En macOS damos un poco más de espacio.
		if (CrashDetectorGUI.esMac()) {
			setSize(980, 720);
			setMinimumSize(new Dimension(860, 620));
		} else {
			setSize(920, 680);
		}

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		cargarDatos();

		/* ===== IZQUIERDA ===== */
		panelIzq = new JPanel(new BorderLayout(0, 10));
		panelIzq.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		forzarFondo(panelIzq, fondo.obtener());

		if (CrashDetectorGUI.esMac()) {
			panelIzq.setPreferredSize(new Dimension(290, 0));
			panelIzq.setMinimumSize(new Dimension(270, 0));
		} else {
			panelIzq.setPreferredSize(new Dimension(260, 0));
		}

		labelImagen = new JLabel();
		labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
		forzarFondo(labelImagen, fondo.obtener());

		if (CrashDetectorGUI.esMac()) {
			labelImagen.setPreferredSize(new Dimension(250, 260));
			labelImagen.setMinimumSize(new Dimension(230, 220));
		}

		try {
			if (imagen.exists()) {
				ImageIcon ic = new ImageIcon(imagen.getAbsolutePath());
				Image im = ic.getImage().getScaledInstance(240, -1, Image.SCALE_SMOOTH);
				labelImagen.setIcon(new ImageIcon(im));
			} else {
				labelImagen.setText(MonitorDePID.idioma.imagenNoEncontrada());
				labelImagen.setForeground(texto.obtener());
			}
		} catch (Exception ignored) {
			labelImagen.setText(MonitorDePID.idioma.errorCargandoImagen());
			labelImagen.setForeground(texto.obtener());
		}

		panelIzq.add(labelImagen, BorderLayout.NORTH);

		panelBotonesLateral = new JPanel();
		panelBotonesLateral.setLayout(new BoxLayout(panelBotonesLateral, BoxLayout.Y_AXIS));
		forzarFondo(panelBotonesLateral, fondo.obtener());

		botonEditar = new JButton(MonitorDePID.idioma.editar());
		botonEditar.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonEditar.addActionListener(e -> editarDerechosMultilenguaje(MonitorDePID.idioma.infoDeDerechosMiranda(),
				fondo.obtener(), texto.obtener(), caja.obtener(), borde.obtener()));

		botonGuardar = new JButton(MonitorDePID.idioma.guardarCambios());
		botonGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonGuardar.addActionListener(e -> guardarDatos());

		// En macOS BoxLayout se porta mejor si fijamos tamaños máximos/preferidos.
		configurarBotonLateral(botonEditar);
		configurarBotonLateral(botonGuardar);

		panelBotonesLateral.add(botonEditar);
		panelBotonesLateral.add(Box.createVerticalStrut(8));
		panelBotonesLateral.add(botonGuardar);
		panelBotonesLateral.add(Box.createVerticalGlue());

		// NORTH en vez de CENTER para evitar expansiones raras de BoxLayout en Aqua.
		panelIzq.add(panelBotonesLateral, BorderLayout.CENTER);

		add(panelIzq, BorderLayout.WEST);

		/* ===== CENTRO ===== */
		panelCentro = new JPanel(new BorderLayout(8, 8));
		forzarFondo(panelCentro, fondo.obtener());
		panelCentro.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JLabel tituloDesc = new JLabel(MonitorDePID.idioma.descripcionDerechosPirateria());
		tituloDesc.setFont(fuenteUI(Font.BOLD, 13));
		tituloDesc.setForeground(texto.obtener());

		JPanel panelTituloDesc = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		forzarFondo(panelTituloDesc, fondo.obtener());
		panelTituloDesc.add(tituloDesc);

		panelCentro.add(panelTituloDesc, BorderLayout.NORTH);

		areaDescripcion = new JTextArea(MonitorDePID.idioma.descripcionDerechosPirateria());
		areaDescripcion.setWrapStyleWord(true);
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setEditable(false);
		areaDescripcion.setFont(fuenteUI(Font.PLAIN, 13));
		areaDescripcion.setBackground(caja.obtener());
		areaDescripcion.setForeground(texto.obtener());
		areaDescripcion.setCaretColor(texto.obtener());
		areaDescripcion.setBorder(BorderFactory.createLineBorder(borde.obtener(), 1));

		// Dar tamaño explícito para que macOS no la colapse.
		areaDescripcion.setRows(CrashDetectorGUI.esMac() ? 18 : 14);
		areaDescripcion.setColumns(40);

		if (CrashDetectorGUI.esMac()) {
			areaDescripcion.setMinimumSize(new Dimension(420, 320));
			areaDescripcion.setPreferredSize(new Dimension(560, 420));
		}

		scrollDescripcion = new JScrollPane(areaDescripcion);
		scrollDescripcion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		scrollDescripcion.setOpaque(true);
		scrollDescripcion.setBackground(caja.obtener());
		scrollDescripcion.getViewport().setOpaque(true);
		scrollDescripcion.getViewport().setBackground(caja.obtener());

		panelCentro.add(scrollDescripcion, BorderLayout.CENTER);

		add(panelCentro, BorderLayout.CENTER);

		setLocationRelativeTo(null);
		recargarApariencia();
		setVisible(true);
	}

	private void configurarBotonLateral(JButton boton) {
		if (boton == null) {
			return;
		}

		if (CrashDetectorGUI.esMac()) {
			Dimension d = new Dimension(210, 36);
			boton.setMinimumSize(d);
			boton.setPreferredSize(d);
			boton.setMaximumSize(d);
		}
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> l = new ArrayList<>();

		fondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		l.add(fondo);

		texto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		l.add(texto);

		caja.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		l.add(caja);

		borde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		l.add(borde);

		return l;
	}

	@Override
	public void recargarApariencia() {
		getContentPane().setBackground(fondo.obtener());

		if (labelImagen != null) {
			labelImagen.setBackground(fondo.obtener());
			labelImagen.setForeground(texto.obtener());
			labelImagen.setOpaque(true);
		}

		if (panelIzq != null) {
			panelIzq.setBackground(fondo.obtener());
			panelIzq.setOpaque(true);
		}

		if (panelBotonesLateral != null) {
			panelBotonesLateral.setBackground(fondo.obtener());
			panelBotonesLateral.setOpaque(true);
		}

		if (panelCentro != null) {
			panelCentro.setBackground(fondo.obtener());
			panelCentro.setOpaque(true);
		}

		if (areaDescripcion != null) {
			areaDescripcion.setBackground(caja.obtener());
			areaDescripcion.setForeground(texto.obtener());
			areaDescripcion.setCaretColor(texto.obtener());
			areaDescripcion.setBorder(BorderFactory.createLineBorder(borde.obtener(), 1));
			areaDescripcion.setOpaque(true);
		}

		if (scrollDescripcion != null) {
			scrollDescripcion.setBackground(caja.obtener());
			scrollDescripcion.setOpaque(true);
			scrollDescripcion.getViewport().setBackground(caja.obtener());
			scrollDescripcion.getViewport().setOpaque(true);
		}

		JButton[] bs = { botonEditar, botonGuardar };
		for (JButton b : bs) {
			if (b == null) {
				continue;
			}

			b.setBackground(borde.obtener());
			b.setForeground(Color.BLACK);
			b.setFocusPainted(false);
			b.setFont(fuenteUI(Font.BOLD, 12));
			b.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
			b.setOpaque(true);

			if (CrashDetectorGUI.esMac()) {
				b.setContentAreaFilled(true);
			}
		}

		aplicarTemaRecursivo(getContentPane());

		revalidate();
		repaint();
	}

	/**
	 * En macOS no basta con paneles: también viewport, scrolls, labels y botones
	 * pueden quedar visualmente mal si no se fuerzan.
	 */
	private void aplicarTemaRecursivo(Component c) {
		if (c == null) {
			return;
		}

		if (c instanceof JPanel || c instanceof JViewport) {
			c.setBackground(fondo.obtener());
			if (c instanceof JComponent) {
				((JComponent) c).setOpaque(true);
			}
		}

		if (c instanceof JScrollPane) {
			JScrollPane sp = (JScrollPane) c;
			sp.setOpaque(true);
			sp.setBackground(caja.obtener());
			sp.getViewport().setOpaque(true);
			sp.getViewport().setBackground(caja.obtener());
		}

		if (c instanceof JLabel) {
			c.setForeground(texto.obtener());
		}

		if (c instanceof JButton) {
			JButton b = (JButton) c;
			b.setOpaque(true);
			if (CrashDetectorGUI.esMac()) {
				b.setContentAreaFilled(true);
			}
		}

		if (c instanceof java.awt.Container) {
			for (Component h : ((java.awt.Container) c).getComponents()) {
				aplicarTemaRecursivo(h);
			}
		}
	}
}