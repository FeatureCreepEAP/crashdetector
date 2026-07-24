package com.asbestosstar.crashdetector.gui.tipos.compartir_instancia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

/**
 * Apariencia de CompartirInstanciaGUI basada en la paleta de Ike Eveland. La
 * lógica continúa en CompartirInstanciaGUI.
 */
@SuppressWarnings("serial")
public class CompartirInstanciaIkeEveland extends CompartirInstanciaGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "compartir_instancia_ike_eveland";

	private final ConfigColor colorFondoPrincipal = ConfigColor.de("compartir_instancia.ike_eveland.fondo",
			new Color(21, 24, 30));
	private final ConfigColor colorPanel = ConfigColor.de("compartir_instancia.ike_eveland.panel",
			new Color(42, 52, 72));
	private final ConfigColor colorTexto = ConfigColor.de("compartir_instancia.ike_eveland.texto",
			new Color(237, 239, 244));
	private final ConfigColor colorAcento = ConfigColor.de("compartir_instancia.ike_eveland.acento",
			new Color(48, 87, 158));
	private final ConfigColor colorOro = ConfigColor.de("compartir_instancia.ike_eveland.oro",
			new Color(160, 143, 104));
	private final ConfigColor colorCaja = ConfigColor.de("compartir_instancia.ike_eveland.caja",
			new Color(247, 246, 244));
	private final ConfigColor colorTextoCaja = ConfigColor.de("compartir_instancia.ike_eveland.texto_caja",
			new Color(20, 22, 27));

	private JPanel panelRetrato;
	private JLabel imagenIke;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {
		super.init();
		instalarPanelRetrato();
		recargarApariencia();
	}

	private void instalarPanelRetrato() {
		if (panelRetrato != null) {
			return;
		}

		panelRetrato = new JPanel(new BorderLayout());
		panelRetrato.setPreferredSize(new Dimension(205, 10));
		panelRetrato.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, colorOro.obtener()),
						BorderFactory.createEmptyBorder(12, 12, 12, 12)));

		imagenIke = new JLabel();
		imagenIke.setHorizontalAlignment(SwingConstants.CENTER);
		imagenIke.setVerticalAlignment(SwingConstants.TOP);
		imagenIke.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				BorderFactory.createEmptyBorder(6, 6, 6, 6)));
		cargarImagenIke();

		panelRetrato.add(imagenIke, BorderLayout.NORTH);
		getContentPane().add(panelRetrato, BorderLayout.WEST);
		revalidate();
		repaint();
	}

	private void cargarImagenIke() {
		if (imagenIke == null) {
			return;
		}

		ImageIcon original = new ImageIcon(Statics.carpeta.resolve("imagenes/ike_eveland.png").toString());
		if (original.getIconWidth() <= 0 || original.getIconHeight() <= 0) {
			imagenIke.setIcon(null);
			return;
		}

		Image escalada = original.getImage().getScaledInstance(165, 233, Image.SCALE_SMOOTH);
		imagenIke.setIcon(new ImageIcon(escalada));
	}

	@Override
	public void recargarApariencia() {
		Color fondo = colorFondoPrincipal.obtener();
		Color panel = colorPanel.obtener();
		Color texto = colorTexto.obtener();
		Color caja = colorCaja.obtener();
		Color textoCaja = colorTextoCaja.obtener();

		getContentPane().setBackground(fondo);
		estilizarContenedor(getContentPane(), fondo, panel, texto);

		if (panelRetrato != null) {
			panelRetrato.setBackground(panel);
			panelRetrato.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, colorOro.obtener()),
							BorderFactory.createEmptyBorder(12, 12, 12, 12)));
		}
		if (imagenIke != null) {
			imagenIke.setOpaque(true);
			imagenIke.setBackground(panel);
			cargarImagenIke();
		}

		if (areaPolitica != null) {
			areaPolitica.setBackground(panel);
			areaPolitica.setForeground(texto);
			areaPolitica.setCaretColor(texto);
		}
		if (arbolRutas != null) {
			arbolRutas.setBackground(caja);
			arbolRutas.setForeground(textoCaja);
		}
		for (JComboBox<?> combo : Arrays.asList(comboFormato, comboServicio)) {
			if (combo != null) {
				combo.setBackground(caja);
				if (!CrashDetectorGUI.esMac()) {
					combo.setForeground(textoCaja);
				}
			}
		}
		for (JButton boton : Arrays.asList(botonCompartir, botonExportar, botonRefrescar)) {
			if (boton != null) {
				boton.setBackground(colorAcento.obtener());
				if (!CrashDetectorGUI.esMac()) {
					boton.setForeground(Color.WHITE);
				}
				boton.setFocusPainted(false);
				boton.setBorder(
						BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorOro.obtener(), 1),
								BorderFactory.createEmptyBorder(4, 10, 4, 10)));
			}
		}
		if (etiquetaEstado != null) {
			etiquetaEstado.setForeground(texto);
		}

		revalidate();
		repaint();
	}

	private void estilizarContenedor(Component componente, Color fondo, Color panel, Color texto) {
		if (componente instanceof JPanel) {
			componente.setBackground(fondo);
			componente.setForeground(texto);
		} else if (componente instanceof JScrollPane) {
			componente.setBackground(panel);
			componente.setForeground(texto);
			((JScrollPane) componente).getViewport().setBackground(panel);
		} else if (componente instanceof JLabel) {
			componente.setForeground(texto);
		} else if (componente instanceof JTree) {
			componente.setBackground(colorCaja.obtener());
			componente.setForeground(colorTextoCaja.obtener());
		}

		if (componente instanceof Container) {
			for (Component hijo : ((Container) componente).getComponents()) {
				estilizarContenedor(hijo, fondo, panel, texto);
			}
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<ElementoConfig>();

		colorFondoPrincipal.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoPrincipal());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.compartirInstanciaColorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.compartirInstanciaColorTexto());
		colorAcento.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorAcento());
		colorOro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		colorCaja.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		colorTextoCaja.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());

		ret.add(colorFondoPrincipal);
		ret.add(colorPanel);
		ret.add(colorTexto);
		ret.add(colorAcento);
		ret.add(colorOro);
		ret.add(colorCaja);
		ret.add(colorTextoCaja);
		return ret;
	}
}
