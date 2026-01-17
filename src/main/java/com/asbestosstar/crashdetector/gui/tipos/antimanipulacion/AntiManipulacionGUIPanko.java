package com.asbestosstar.crashdetector.gui.tipos.antimanipulacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class AntiManipulacionGUIPanko extends AntiManipulacionGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "komachi_panko";

	public static final File IMAGEN = Statics.carpeta.resolve("imagenes/panko.png").toFile();

	private ConfigColor colorFondo;
	private ConfigColor colorTexto;
	private ConfigColor colorCaja;
	private ConfigColor colorBorde;

	@Override
	public void init() {
		crearColoresTema();
		cargarDatos();

		setTitle(MonitorDePID.idioma.nombre_antimanipulacion());
		setSize(960, 640);
		setModal(true);
		setLayout(new BorderLayout(8, 8));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		inicializarModeloTabla();
		recargarTabla();

		add(crearPanelIzquierdo(), BorderLayout.WEST);
		add(new JScrollPane(tabla), BorderLayout.CENTER);
		add(crearPanelBotones(), BorderLayout.SOUTH);

		recargarApariencia();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void crearColoresTema() {
		colorFondo = ConfigColor.de("tema.panko.fondo", new Color(252, 245, 230));
		colorTexto = ConfigColor.de("tema.panko.texto", new Color(80, 60, 40));
		colorCaja = ConfigColor.de("tema.panko.caja", new Color(255, 250, 240));
		colorBorde = ConfigColor.de("tema.panko.borde", new Color(210, 170, 120));
	}

	private JPanel crearPanelIzquierdo() {
		JPanel p = new JPanel(new BorderLayout(8, 8));
		p.setPreferredSize(new Dimension(180, 0));

		JLabel img = new JLabel();
		if (IMAGEN.exists()) {
			ImageIcon ic = new ImageIcon(IMAGEN.getAbsolutePath());
			Image im = ic.getImage().getScaledInstance(163, -1, Image.SCALE_SMOOTH);
			img.setIcon(new ImageIcon(im));
			img.setHorizontalAlignment(JLabel.CENTER);
			img.setVerticalAlignment(JLabel.TOP);
		}

		JLabel aviso = new JLabel("<html><div style='width:140px; text-align:left;'>"
				+ MonitorDePID.idioma.advertenciaHashLento() + "</div></html>");
		aviso.setVerticalAlignment(JLabel.TOP);

		p.add(img, BorderLayout.NORTH);
		p.add(aviso, BorderLayout.SOUTH);
		return p;
	}

	private JPanel crearPanelBotones() {
		JPanel p = new JPanel();

		JButton archivo = new JButton(MonitorDePID.idioma.agregarArchivo());
		JButton carpeta = new JButton(MonitorDePID.idioma.agregarCarpeta());
		JButton quitar = new JButton(MonitorDePID.idioma.quitar());
		JButton guardar = new JButton(MonitorDePID.idioma.guardarCambios());

		archivo.addActionListener(e -> seleccionar(false));
		carpeta.addActionListener(e -> seleccionar(true));
		quitar.addActionListener(e -> quitarSeleccionado());
		guardar.addActionListener(e -> guardarDatos());

		p.add(archivo);
		p.add(carpeta);
		p.add(quitar);
		p.add(guardar);
		return p;
	}

	@Override
	public void recargarApariencia() {
		getContentPane().setBackground(colorFondo.obtener());

		tabla.setBackground(colorCaja.obtener());
		tabla.setForeground(colorTexto.obtener());
		tabla.setGridColor(colorBorde.obtener());
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> l = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		l.add(colorFondo);

		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		l.add(colorTexto);

		colorCaja.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		l.add(colorCaja);

		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		l.add(colorBorde);

		return l;
	}

}
