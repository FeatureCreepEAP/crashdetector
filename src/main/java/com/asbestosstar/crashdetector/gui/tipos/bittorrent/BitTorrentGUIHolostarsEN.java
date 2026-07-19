package com.asbestosstar.crashdetector.gui.tipos.bittorrent;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.bittorrent.ConfiguracionBitTorrent;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Tema Holostars EN para el cliente BitTorrent.
 */
public final class BitTorrentGUIHolostarsEN extends BitTorrentGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "bittorrent_holostars_en";

	private final ConfigColor colorFondo = ConfigColor.de("tema.bittorrent.holostars.fondo", new Color(14, 17, 45));
	private final ConfigColor colorPanel = ConfigColor.de("tema.bittorrent.holostars.panel", new Color(31, 33, 75));
	private final ConfigColor colorPanelClaro = ConfigColor.de("tema.bittorrent.holostars.panel_claro",
			new Color(238, 239, 249));
	private final ConfigColor colorTexto = ConfigColor.de("tema.bittorrent.holostars.texto", new Color(247, 247, 255));
	private final ConfigColor colorTextoOscuro = ConfigColor.de("tema.bittorrent.holostars.texto_oscuro",
			new Color(31, 29, 54));
	private final ConfigColor colorAcento = ConfigColor.de("tema.bittorrent.holostars.acento", new Color(146, 84, 190));
	private final ConfigColor colorAcentoSecundario = ConfigColor.de("tema.bittorrent.holostars.acento_secundario",
			new Color(198, 65, 103));
	private final ConfigColor colorSeleccion = ConfigColor.de("tema.bittorrent.holostars.seleccion",
			new Color(196, 180, 229));
	private final ConfigColor colorBorde = ConfigColor.de("tema.bittorrent.holostars.borde", new Color(106, 92, 155));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		recargarTextos();

		Color fondo = colorFondo.obtener();
		Color panel = colorPanel.obtener();
		Color panelClaro = colorPanelClaro.obtener();
		Color texto = colorTexto.obtener();
		Color textoOscuro = colorTextoOscuro.obtener();
		Color acento = colorAcento.obtener();
		Color acentoSecundario = colorAcentoSecundario.obtener();
		Color seleccion = colorSeleccion.obtener();
		Color borde = colorBorde.obtener();

		if (panelRaiz != null)
			panelRaiz.setBackground(fondo);
		if (panelCabecera != null) {
			panelCabecera.setBackground(fondo);
			panelCabecera.setBorder(BorderFactory.createLineBorder(borde));
		}
		if (panelPrivacidad != null)
			panelPrivacidad.setBackground(panel);
		if (areaPrivacidad != null) {
			areaPrivacidad.setBackground(panel);
			areaPrivacidad.setForeground(texto);
			areaPrivacidad.setCaretColor(texto);
		}
		if (confirmarPrivacidad != null) {
			confirmarPrivacidad.setBackground(panel);
			confirmarPrivacidad.setForeground(texto);
		}
		if (pestanas != null) {
			pestanas.setBackground(panel);
			pestanas.setForeground(texto);
		}

		aplicarRecursivo(panelRaiz, fondo, panel, panelClaro, texto, textoOscuro, acento, acentoSecundario, seleccion,
				borde);

		if (areaPrivacidad != null) {
			areaPrivacidad.setBackground(panel);
			areaPrivacidad.setForeground(texto);
		}
		if (confirmarPrivacidad != null) {
			confirmarPrivacidad.setBackground(panel);
			confirmarPrivacidad.setForeground(texto);
		}
		cargarImagen();
		revalidate();
		repaint();
	}

	private void aplicarRecursivo(Container contenedor, Color fondo, Color panel, Color panelClaro, Color texto,
			Color textoOscuro, Color acento, Color acentoSecundario, Color seleccion, Color borde) {
		if (contenedor == null)
			return;

		for (Component componente : contenedor.getComponents()) {
			if (componente instanceof JPanel) {
				componente.setBackground(panel);
			}
			if (componente instanceof JLabel) {
				componente.setForeground(texto);
			}
			if (componente instanceof JTextField) {
				componente.setBackground(panelClaro);
				componente.setForeground(textoOscuro);
				((JTextField) componente).setCaretColor(textoOscuro);
				((JTextField) componente).setBorder(BorderFactory.createLineBorder(borde));
			}
			if (componente instanceof JTextArea && componente != areaPrivacidad) {
				componente.setBackground(panelClaro);
				componente.setForeground(textoOscuro);
				((JTextArea) componente).setCaretColor(textoOscuro);
				((JTextArea) componente).setBorder(BorderFactory.createLineBorder(borde));
			}
			if (componente instanceof JButton) {
				JButton boton = (JButton) componente;
				boton.setBackground(acento);
				boton.setForeground(texto);
				boton.setFocusPainted(false);
				boton.setOpaque(true);
				boton.setContentAreaFilled(true);
				boton.setBorder(BorderFactory.createEmptyBorder(7, 12, 7, 12));
			}
			if (componente instanceof JCheckBox && componente != confirmarPrivacidad) {
				componente.setBackground(panel);
				componente.setForeground(texto);
			}
			if (componente instanceof JTable) {
				JTable tabla = (JTable) componente;
				tabla.setBackground(panelClaro);
				tabla.setForeground(textoOscuro);
				tabla.setSelectionBackground(seleccion);
				tabla.setSelectionForeground(textoOscuro);
				tabla.setGridColor(borde);
				tabla.getTableHeader().setBackground(acentoSecundario);
				tabla.getTableHeader().setForeground(texto);
			}
			if (componente instanceof JProgressBar) {
				JProgressBar barra = (JProgressBar) componente;
				barra.setBackground(panelClaro);
				barra.setForeground(acentoSecundario);
				barra.setBorder(BorderFactory.createLineBorder(borde));
			}
			if (componente instanceof JScrollPane) {
				JScrollPane scroll = (JScrollPane) componente;
				scroll.setBackground(panel);
				scroll.getViewport().setBackground(panelClaro);
				scroll.setBorder(BorderFactory.createLineBorder(borde));
			}
			if (componente instanceof JViewport) {
				componente.setBackground(panelClaro);
			}
			if (componente instanceof JTabbedPane) {
				componente.setBackground(panel);
				componente.setForeground(texto);
			}
			if (componente instanceof Container) {
				aplicarRecursivo((Container) componente, fondo, panel, panelClaro, texto, textoOscuro, acento,
						acentoSecundario, seleccion, borde);
			}
		}
	}

	private void cargarImagen() {
		if (imagenTema == null)
			return;
		ImageIcon icono = null;
		URL recurso = getClass().getResource("/imagenes/holostars_en.png");
		if (recurso != null)
			icono = new ImageIcon(recurso);
		if (icono == null || icono.getIconWidth() <= 0) {
			icono = new ImageIcon(Statics.carpeta.resolve("imagenes/holostars_en.png").toString());
		}
		if (icono.getIconWidth() > 0) {
			Image imagen = icono.getImage().getScaledInstance(292, 170, Image.SCALE_SMOOTH);
			imagenTema.setIcon(new ImageIcon(imagen));
			imagenTema.setText("");
		} else {
			imagenTema.setIcon(null);
			imagenTema.setText(MonitorDePID.idioma.bittorrentImagenAlternativa());
			imagenTema.setForeground(colorTexto.obtener());
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentColorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentColorPanel());
		colorPanelClaro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentColorPanelClaro());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentColorTexto());
		colorTextoOscuro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentColorTextoOscuro());
		colorAcento.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentColorAcento());
		colorAcentoSecundario.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentColorAcentoSecundario());
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentColorSeleccion());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentColorBorde());

		ConfiguracionBitTorrent.TRACKERS
				.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentTrackersConfig());
		ConfiguracionBitTorrent.SEGUIR_COMPARTIENDO
				.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentSeguirCompartiendo());
		ConfiguracionBitTorrent.PUERTO.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentPuerto());
		ConfiguracionBitTorrent.HILOS_HASH.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bittorrentHilosHash());

		elementos.add(colorFondo);
		elementos.add(colorPanel);
		elementos.add(colorPanelClaro);
		elementos.add(colorTexto);
		elementos.add(colorTextoOscuro);
		elementos.add(colorAcento);
		elementos.add(colorAcentoSecundario);
		elementos.add(colorSeleccion);
		elementos.add(colorBorde);
		elementos.add(ConfiguracionBitTorrent.TRACKERS);
		elementos.add(ConfiguracionBitTorrent.SEGUIR_COMPARTIENDO);
		elementos.add(ConfiguracionBitTorrent.PUERTO);
		elementos.add(ConfiguracionBitTorrent.HILOS_HASH);
		return elementos;
	}
}