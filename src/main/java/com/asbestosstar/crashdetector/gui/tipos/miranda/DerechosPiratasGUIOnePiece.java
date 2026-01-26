// ===== DerechosPiratasGUIOnePiece =====
// Cambios:
// 1) El area de descripcion ahora ocupa el CENTER (quita el hueco gigante).
// 2) Los botones pasan al lado IZQUIERDO, debajo de la imagen.
// 3) recargarApariencia aplica estilo tambien a botones/descripcion.

package com.asbestosstar.crashdetector.gui.tipos.miranda;

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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class DerechosPiratasGUIOnePiece extends DerechosPiratasGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "one_piece";

	public static File imagen = Statics.carpeta.resolve("imagenes/onepeice.png").toFile(); // downscaled

	private ConfigColor fondo = ConfigColor.de("tema.onepiece.fondo", new Color(20, 20, 20));
	private ConfigColor texto = ConfigColor.de("tema.onepiece.texto", Color.WHITE);
	private ConfigColor caja = ConfigColor.de("tema.onepiece.caja", new Color(35, 35, 35));
	private ConfigColor borde = ConfigColor.de("tema.onepiece.borde", new Color(200, 180, 40));

	// Referencias UI (para recargarApariencia)
	private JTextArea areaDescripcion;
	private JButton botonEditar;
	private JButton botonGuardar;
	private JLabel labelImagen;

	@Override
	public void init() {

		setTitle(MonitorDePID.idioma.infoDeDerechosMiranda());
		setSize(920, 680);
		setModal(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		cargarDatos();

		/* ===== IZQUIERDA (IMAGEN + BOTONES) ===== */
		JPanel izq = new JPanel(new BorderLayout(0, 10));
		izq.setPreferredSize(new Dimension(260, 0));
		izq.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		forzarFondo(izq, fondo.obtener());

		labelImagen = new JLabel();
		labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
		forzarFondo(labelImagen, fondo.obtener());

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

		izq.add(labelImagen, BorderLayout.NORTH);

		JPanel panelBotonesLateral = new JPanel();
		panelBotonesLateral.setLayout(new BoxLayout(panelBotonesLateral, BoxLayout.Y_AXIS));
		forzarFondo(panelBotonesLateral, fondo.obtener());

		botonEditar = new JButton(MonitorDePID.idioma.editar());
		botonEditar.setAlignmentX(JButton.CENTER_ALIGNMENT);
		botonEditar.addActionListener(e -> editarDerechosMultilenguaje(MonitorDePID.idioma.infoDeDerechosMiranda(),
				fondo.obtener(), texto.obtener(), caja.obtener(), borde.obtener()));

		botonGuardar = new JButton(MonitorDePID.idioma.guardarCambios());
		botonGuardar.setAlignmentX(JButton.CENTER_ALIGNMENT);
		botonGuardar.addActionListener(e -> guardarDatos());

		panelBotonesLateral.add(botonEditar);
		panelBotonesLateral.add(Box.createVerticalStrut(8));
		panelBotonesLateral.add(botonGuardar);
		panelBotonesLateral.add(Box.createVerticalGlue());

		izq.add(panelBotonesLateral, BorderLayout.CENTER);

		add(izq, BorderLayout.WEST);

		/* ===== CENTRO (DESCRIPCION GRANDE) ===== */
		JPanel centro = new JPanel(new BorderLayout(8, 8));
		forzarFondo(centro, fondo.obtener());
		centro.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JLabel tituloDesc = new JLabel(MonitorDePID.idioma.descripcionDerechosPirateria());
		tituloDesc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		tituloDesc.setForeground(texto.obtener());

		JPanel panelTituloDesc = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		forzarFondo(panelTituloDesc, fondo.obtener());
		panelTituloDesc.add(tituloDesc);

		centro.add(panelTituloDesc, BorderLayout.NORTH);

		areaDescripcion = new JTextArea(MonitorDePID.idioma.descripcionDerechosPirateria());
		areaDescripcion.setWrapStyleWord(true);
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setEditable(false);
		areaDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		areaDescripcion.setBackground(caja.obtener());
		areaDescripcion.setForeground(texto.obtener());
		areaDescripcion.setBorder(BorderFactory.createLineBorder(borde.obtener(), 1));

		JScrollPane sp = new JScrollPane(areaDescripcion);
		sp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		centro.add(sp, BorderLayout.CENTER);

		add(centro, BorderLayout.CENTER);

		setLocationRelativeTo(null);
		recargarApariencia();
		setVisible(true);
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
		}

		if (areaDescripcion != null) {
			areaDescripcion.setBackground(caja.obtener());
			areaDescripcion.setForeground(texto.obtener());
			areaDescripcion.setCaretColor(texto.obtener());
			areaDescripcion.setBorder(BorderFactory.createLineBorder(borde.obtener(), 1));
		}

		// Botones: dorado/amarillo (borde) con texto negro, como One Piece
		JButton[] bs = { botonEditar, botonGuardar };
		for (JButton b : bs) {
			if (b == null)
				continue;
			b.setBackground(borde.obtener());
			b.setForeground(Color.BLACK);
			b.setFocusPainted(false);
			b.setFont(new Font("Segoe UI", Font.BOLD, 12));
			b.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
		}

		// Evitar “blancos” por Look&Feel
		for (java.awt.Component c : getContentPane().getComponents()) {
			aplicarFondoRecursivo(c);
		}

		revalidate();
		repaint();
	}

	/** Fuerza fondo recursivo a paneles para evitar blancos por Look&Feel. */
	private void aplicarFondoRecursivo(java.awt.Component c) {
		if (c instanceof JPanel) {
			c.setBackground(fondo.obtener());
			for (java.awt.Component h : ((JPanel) c).getComponents()) {
				aplicarFondoRecursivo(h);
			}
		}
	}
}
