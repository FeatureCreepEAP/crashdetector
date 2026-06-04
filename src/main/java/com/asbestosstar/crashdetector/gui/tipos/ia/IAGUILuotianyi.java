package com.asbestosstar.crashdetector.gui.tipos.ia;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación visual estilo Luotianyi para la GUI de IA.
 */
public class IAGUILuotianyi extends IAGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "luotianyi";

	public ConfigColor colorFondoVentana = ConfigColor.de("tema.ia.luotianyi.color.fondo.ventana",
			new java.awt.Color(236, 251, 252));
	public ConfigColor colorPanel = ConfigColor.de("tema.ia.luotianyi.color.panel", new java.awt.Color(248, 255, 255));
	public ConfigColor colorPanelSecundario = ConfigColor.de("tema.ia.luotianyi.color.panel.secundario",
			new java.awt.Color(222, 245, 246));
	public ConfigColor colorTexto = ConfigColor.de("tema.ia.luotianyi.color.texto", new java.awt.Color(33, 72, 78));
	public ConfigColor colorTextoSuave = ConfigColor.de("tema.ia.luotianyi.color.texto.suave",
			new java.awt.Color(72, 114, 120));
	public ConfigColor colorBorde = ConfigColor.de("tema.ia.luotianyi.color.borde", new java.awt.Color(109, 191, 198));
	public ConfigColor colorCajaTexto = ConfigColor.de("tema.ia.luotianyi.color.caja.texto",
			new java.awt.Color(255, 255, 255));
	public ConfigColor colorBoton = ConfigColor.de("tema.ia.luotianyi.color.boton", new java.awt.Color(152, 225, 230));
	public ConfigColor colorBotonTexto = ConfigColor.de("tema.ia.luotianyi.color.boton.texto",
			new java.awt.Color(24, 58, 63));

	protected JLabel lblIlustracion;
	protected FondoPanel fondo;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public JPanel crearPanelIlustracion() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(220, 220));

		lblIlustracion = new JLabel("", SwingConstants.CENTER);
		lblIlustracion.setPreferredSize(new Dimension(200, 200));
		lblIlustracion.setHorizontalAlignment(SwingConstants.CENTER);
		lblIlustracion.setVerticalAlignment(SwingConstants.CENTER);

		java.nio.file.Path ruta = Statics.carpeta.resolve("imagenes").resolve("luotianyi.png");
		if (java.nio.file.Files.exists(ruta)) {
			lblIlustracion.setIcon(new ImageIcon(ruta.toString()));
		} else {
			lblIlustracion.setText(MonitorDePID.idioma.iaImagenNoDisponible());
		}

		panel.add(lblIlustracion, BorderLayout.CENTER);
		return panel;
	}

	@Override
	public void aplicarApariencia() {
		getContentPane().setBackground(colorFondoVentana.obtener());

		if (panelRaiz != null) {
			panelRaiz.setBackground(colorFondoVentana.obtener());
		}
		if (panelSuperior != null) {
			panelSuperior.setBackground(colorPanel.obtener());
			panelSuperior.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
							BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		}
		if (panelCentro != null) {
			panelCentro.setBackground(colorFondoVentana.obtener());
		}
		if (panelInferior != null) {
			panelInferior.setBackground(colorPanelSecundario.obtener());
			panelInferior.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
							BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		}
		if (panelBotones != null) {
			panelBotones.setBackground(colorPanelSecundario.obtener());
		}
		if (panelIlustracion != null) {
			panelIlustracion.setBackground(colorFondoVentana.obtener());
		}

		if (lblTitulo != null) {
			lblTitulo.setForeground(colorTexto.obtener());
			lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 22f));
		}

		if (btnAbrirMcp != null) {
			btnAbrirMcp.setBackground(colorBoton.obtener());
			btnAbrirMcp.setForeground(colorBotonTexto.obtener());
			btnAbrirMcp.setFocusPainted(false);
		}

		if (txtDescripcion != null) {
			txtDescripcion.setBackground(colorCajaTexto.obtener());
			txtDescripcion.setForeground(colorTexto.obtener());
			txtDescripcion.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
			if (txtDescripcion.getParent() instanceof JScrollPane) {
				((JScrollPane) txtDescripcion.getParent().getParent()).getViewport()
						.setBackground(colorCajaTexto.obtener());
			}
		}

		if (txtEnlace != null) {
			txtEnlace.setBackground(colorCajaTexto.obtener());
			txtEnlace.setForeground(colorTexto.obtener());
			txtEnlace.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
							BorderFactory.createEmptyBorder(6, 6, 6, 6)));
		}

		if (btnCopiar != null) {
			btnCopiar.setBackground(colorBoton.obtener());
			btnCopiar.setForeground(colorBotonTexto.obtener());
			btnCopiar.setFocusPainted(false);
		}

		if (btnAbrir != null) {
			btnAbrir.setBackground(colorBoton.obtener());
			btnAbrir.setForeground(colorBotonTexto.obtener());
			btnAbrir.setFocusPainted(false);
		}

		if (lblAviso != null) {
			lblAviso.setForeground(colorTextoSuave.obtener());
			lblAviso.setFont(lblAviso.getFont().deriveFont(Font.PLAIN, 13f));
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

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();

		colorFondoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoPanel());
		colorPanelSecundario.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPanelSecundario());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorTextoSuave.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoSuave());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		colorCajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorBotonTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoBotonesCompartir());

		elementos.add(colorFondoVentana);
		elementos.add(colorPanel);
		elementos.add(colorPanelSecundario);
		elementos.add(colorTexto);
		elementos.add(colorTextoSuave);
		elementos.add(colorBorde);
		elementos.add(colorCajaTexto);
		elementos.add(colorBoton);
		elementos.add(colorBotonTexto);

		return elementos;
	}

	/**
	 * Panel de fondo opcional si luego quieres usar la imagen también como fondo.
	 */
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
						CrashDetectorLogger.log("No se pudo cargar fondo IA: " + ex.getMessage());
					}
					SwingUtilities.invokeLater(whenLoadedOnEDT);
				}
			}, "FondoIA").start();
		}
	}
}