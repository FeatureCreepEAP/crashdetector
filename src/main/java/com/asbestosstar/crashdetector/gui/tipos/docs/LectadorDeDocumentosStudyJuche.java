package com.asbestosstar.crashdetector.gui.tipos.docs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class LectadorDeDocumentosStudyJuche extends LectadorDeDocumentosGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "studyjuche";

	public ConfigColor colorFondoVentana = ConfigColor.de("tema.docs.studyjuche.color.fondo.ventana",
			new java.awt.Color(247, 238, 241));
	public ConfigColor colorPanel = ConfigColor.de("tema.docs.studyjuche.color.panel",
			new java.awt.Color(255, 250, 252));
	public ConfigColor colorPanelSecundario = ConfigColor.de("tema.docs.studyjuche.color.panel.secundario",
			new java.awt.Color(245, 234, 238));
	public ConfigColor colorTexto = ConfigColor.de("tema.docs.studyjuche.color.texto", new java.awt.Color(53, 43, 52));
	public ConfigColor colorTextoSuave = ConfigColor.de("tema.docs.studyjuche.color.texto.suave",
			new java.awt.Color(92, 74, 89));
	public ConfigColor colorBorde = ConfigColor.de("tema.docs.studyjuche.color.borde",
			new java.awt.Color(186, 142, 160));
	public ConfigColor colorArbolSeleccion = ConfigColor.de("tema.docs.studyjuche.color.arbol.seleccion",
			new java.awt.Color(233, 206, 216));
	public ConfigColor colorHTML = ConfigColor.de("tema.docs.studyjuche.color.html.fondo",
			new java.awt.Color(255, 252, 253));

	protected JLabel lblIlustracion;
	protected FondoPanel fondo;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void instalarFondoApariencia(JLayeredPane capa) {
		fondo = new FondoPanel(Statics.carpeta.resolve("imagenes").resolve("studyjuche.png").toString());

		// Como la capa usa BorderLayout, el add debe usar una constraint de
		// BorderLayout.
		capa.add(fondo, BorderLayout.CENTER);
		capa.setLayer(fondo, JLayeredPane.DEFAULT_LAYER);

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				if (fondo != null) {
					fondo.revalidate();
					fondo.repaint();
				}
			}
		});

		fondo.cargarAsincrono(new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});
	}

	@Override
	public JPanel crearPanelIlustracion() {
		JPanel panel = new JPanel(new BorderLayout(6, 6));
		panel.setPreferredSize(new Dimension(170, 170));

		lblIlustracion = new JLabel("", SwingConstants.CENTER);
		lblIlustracion.setPreferredSize(new Dimension(150, 150));
		lblIlustracion.setHorizontalAlignment(SwingConstants.CENTER);
		lblIlustracion.setVerticalAlignment(SwingConstants.CENTER);

		java.nio.file.Path ruta = Statics.carpeta.resolve("imagenes").resolve("studyjuche.png");
		if (java.nio.file.Files.exists(ruta)) {
			ImageIcon icono = new ImageIcon(ruta.toString());
			lblIlustracion.setIcon(icono);
		} else {
			lblIlustracion.setText(MonitorDePID.idioma.docsImagenNoDisponible());
		}

		panel.add(lblIlustracion, BorderLayout.CENTER);
		return panel;
	}

	@Override
	public void aplicarApariencia() {
		getContentPane().setBackground(colorFondoVentana.obtener());

		if (panelRaiz != null) {
			panelRaiz.setOpaque(false);
		}

		estilizarScroll(scrollArbol, MonitorDePID.idioma.docsArbolTitulo());
		estilizarScroll(scrollDocumento, MonitorDePID.idioma.docsVisorTitulo());

		if (panelInferior != null) {
			panelInferior.setOpaque(true);
			panelInferior.setBackground(colorPanelSecundario.obtener());
			panelInferior.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
							BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		}

		if (panelInferiorCentro != null) {
			panelInferiorCentro.setOpaque(false);
		}

		if (panelInferiorDerecha != null) {
			panelInferiorDerecha.setOpaque(false);
		}

		if (comboIdiomas != null) {
			comboIdiomas.setBackground(colorPanel.obtener());
			comboIdiomas.setForeground(colorTexto.obtener());
			comboIdiomas.setFont(comboIdiomas.getFont().deriveFont(Font.PLAIN, 14f));
		}

		if (arbolDocs != null) {
			arbolDocs.setBackground(colorPanel.obtener());
			arbolDocs.setForeground(colorTexto.obtener());
			arbolDocs.setRowHeight(22);
			arbolDocs.setFont(arbolDocs.getFont().deriveFont(Font.PLAIN, 14f));
		}

		if (visorDocumento != null) {
			visorDocumento.setBackground(colorHTML.obtener());
			visorDocumento.setForeground(colorTexto.obtener());
			visorDocumento.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		}

		if (splitPrincipal != null) {
			splitPrincipal.setBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1));
		}

		if (lblIlustracion != null) {
			lblIlustracion.setOpaque(true);
			lblIlustracion.setBackground(colorPanel.obtener());
			lblIlustracion.setForeground(colorTextoSuave.obtener());
			lblIlustracion.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
							BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		}

		repaint();
	}

	protected void estilizarScroll(JScrollPane scroll, String titulo) {
		if (scroll == null) {
			return;
		}

		scroll.getViewport().setBackground(colorPanel.obtener());
		scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
				titulo, TitledBorder.LEFT, TitledBorder.TOP, new Font(Font.SANS_SERIF, Font.BOLD, 13),
				colorTexto.obtener()));
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();

		colorFondoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoPanel());
		colorPanelSecundario.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPanelSecundario());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorTextoSuave.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoSuave());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		colorArbolSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorSeleccion());
		colorHTML.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoDocumento());

		elementos.add(colorFondoVentana);
		elementos.add(colorPanel);
		elementos.add(colorPanelSecundario);
		elementos.add(colorTexto);
		elementos.add(colorTextoSuave);
		elementos.add(colorBorde);
		elementos.add(colorArbolSeleccion);
		elementos.add(colorHTML);

		return elementos;
	}

	private static class FondoPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private volatile java.awt.Image imagen;
		private final String ruta;

		public FondoPanel(String ruta) {
			this.ruta = ruta;
			setOpaque(false);
		}

		@Override
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			if (imagen != null) {
				g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
			}
		}

		public void cargarAsincrono(final Runnable whenLoadedOnEDT) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						imagen = ImageIO.read(new File(ruta));
					} catch (Exception ex) {
						CrashDetectorLogger.log("No se pudo cargar fondo de documentos: " + ex.getMessage());
					}
					SwingUtilities.invokeLater(whenLoadedOnEDT);
				}
			}, "FondoDocs").start();
		}
	}
}