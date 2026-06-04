package com.asbestosstar.crashdetector.gui.tipos.mcp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación visual predeterminada para MCP basada en motherv3.
 */
public class McpGUIMotherV3 extends McpGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "motherv3_mcp";

	public ConfigColor colorFondoVentana = ConfigColor.de("tema.mcp.motherv3.color.fondo.ventana",
			new java.awt.Color(235, 247, 252));
	public ConfigColor colorPanel = ConfigColor.de("tema.mcp.motherv3.color.panel", new java.awt.Color(248, 253, 255));
	public ConfigColor colorPanelSecundario = ConfigColor.de("tema.mcp.motherv3.color.panel.secundario",
			new java.awt.Color(222, 239, 248));
	public ConfigColor colorTexto = ConfigColor.de("tema.mcp.motherv3.color.texto", new java.awt.Color(28, 54, 70));
	public ConfigColor colorTextoSuave = ConfigColor.de("tema.mcp.motherv3.color.texto.suave",
			new java.awt.Color(82, 105, 120));
	public ConfigColor colorBorde = ConfigColor.de("tema.mcp.motherv3.color.borde", new java.awt.Color(86, 162, 206));
	public ConfigColor colorCajaTexto = ConfigColor.de("tema.mcp.motherv3.color.caja.texto",
			new java.awt.Color(255, 255, 255));
	public ConfigColor colorBoton = ConfigColor.de("tema.mcp.motherv3.color.boton", new java.awt.Color(91, 190, 225));
	public ConfigColor colorBotonTexto = ConfigColor.de("tema.mcp.motherv3.color.boton.texto",
			new java.awt.Color(10, 36, 52));
	public ConfigColor colorAcento = ConfigColor.de("tema.mcp.motherv3.color.acento", new java.awt.Color(130, 95, 205));

	public JLabel lblImagen;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public JPanel crearPanelImagen() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(210, 230));

		lblImagen = new JLabel("", SwingConstants.CENTER);
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagen.setVerticalAlignment(SwingConstants.CENTER);

		cargarImagenMotherV3();

		panel.add(lblImagen, BorderLayout.CENTER);
		return panel;
	}

	public void cargarImagenMotherV3() {
		try {
			Path rutaImagen = Statics.carpeta.resolve("imagenes").resolve("motherv3.png");

			if (!Files.exists(rutaImagen)) {
				MonitorDePID.copiarACarpetaDesdeJar("/imagenes/motherv3.png", rutaImagen.toFile());
			}

			if (Files.exists(rutaImagen)) {
				ImageIcon icono = new ImageIcon(rutaImagen.toAbsolutePath().toString());
				java.awt.Image escalado = icono.getImage().getScaledInstance(190, 220, java.awt.Image.SCALE_SMOOTH);
				lblImagen.setIcon(new ImageIcon(escalado));
			} else {
				lblImagen.setText(MonitorDePID.idioma.mcpImagenNoDisponible());
			}
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
			lblImagen.setText(MonitorDePID.idioma.mcpImagenNoDisponible());
		}
	}

	@Override
	public void aplicarApariencia() {
		getContentPane().setBackground(colorFondoVentana.obtener());

		if (panelRaiz != null) {
			panelRaiz.setBackground(colorFondoVentana.obtener());
		}

		if (panelDescripcion != null) {
			panelDescripcion.setBackground(colorPanel.obtener());
			panelDescripcion.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
							BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		}

		if (panelControles != null) {
			panelControles.setBackground(colorPanelSecundario.obtener());
			panelControles.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
							BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		}

		if (panelPuerto != null) {
			panelPuerto.setBackground(colorPanelSecundario.obtener());
		}

		if (panelBotones != null) {
			panelBotones.setBackground(colorPanelSecundario.obtener());
		}

		if (panelImagen != null) {
			panelImagen.setBackground(colorFondoVentana.obtener());
		}

		if (lblTitulo != null) {
			lblTitulo.setForeground(colorTexto.obtener());
			lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 22f));
		}

		if (lblPuerto != null) {
			lblPuerto.setForeground(colorTexto.obtener());
			lblPuerto.setFont(lblPuerto.getFont().deriveFont(Font.BOLD, 13f));
		}

		if (lblEstado != null) {
			lblEstado.setForeground(colorTextoSuave.obtener());
			lblEstado.setFont(lblEstado.getFont().deriveFont(Font.PLAIN, 13f));
		}

		if (txtDescripcion != null) {
			txtDescripcion.setBackground(colorCajaTexto.obtener());
			txtDescripcion.setForeground(colorTexto.obtener());
			txtDescripcion.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			if (txtDescripcion.getParent() != null && txtDescripcion.getParent().getParent() instanceof JScrollPane) {
				((JScrollPane) txtDescripcion.getParent().getParent()).getViewport()
						.setBackground(colorCajaTexto.obtener());
			}
		}

		if (txtPuerto != null) {
			txtPuerto.setBackground(colorCajaTexto.obtener());
			txtPuerto.setForeground(colorTexto.obtener());
			txtPuerto.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
							BorderFactory.createEmptyBorder(6, 6, 6, 6)));
		}

		estilizarBoton(btnDescargarDeps);
		estilizarBoton(btnIniciarServidor);

		if (lblImagen != null) {
			lblImagen.setOpaque(true);
			lblImagen.setBackground(colorPanel.obtener());
			lblImagen.setForeground(colorTextoSuave.obtener());
			lblImagen.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorAcento.obtener(), 1),
							BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		}

		repaint();
	}

	public void estilizarBoton(javax.swing.JButton boton) {
		if (boton == null) {
			return;
		}

		boton.setBackground(colorBoton.obtener());
		boton.setForeground(colorBotonTexto.obtener());
		boton.setFocusPainted(false);
		boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
				BorderFactory.createEmptyBorder(6, 12, 6, 12)));
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
		colorAcento.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorAcento());

		elementos.add(colorFondoVentana);
		elementos.add(colorPanel);
		elementos.add(colorPanelSecundario);
		elementos.add(colorTexto);
		elementos.add(colorTextoSuave);
		elementos.add(colorBorde);
		elementos.add(colorCajaTexto);
		elementos.add(colorBoton);
		elementos.add(colorBotonTexto);
		elementos.add(colorAcento);

		return elementos;
	}
}