package com.asbestosstar.crashdetector.gui.tipos.universalator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Tema JellyStara para Universalator.
 */
public class UniversalatorGUIJellyStara extends UniversalatorGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "jelly_stara";

	private final ConfigColor colorFondo = ConfigColor.de("gui.universalator.jelly.fondo", new Color(243, 248, 255));
	private final ConfigColor colorPanel = ConfigColor.de("gui.universalator.jelly.panel", new Color(229, 238, 255));
	private final ConfigColor colorTexto = ConfigColor.de("gui.universalator.jelly.texto", new Color(43, 52, 76));
	private final ConfigColor colorAcento = ConfigColor.de("gui.universalator.jelly.acento", new Color(104, 129, 255));
	private final ConfigColor colorBorde = ConfigColor.de("gui.universalator.jelly.borde", new Color(175, 193, 244));
	private final ConfigColor colorAdvertencia = ConfigColor.de("gui.universalator.jelly.advertencia",
			new Color(160, 74, 74));
	private final ConfigColor colorExito = ConfigColor.de("gui.universalator.jelly.exito", new Color(61, 132, 102));
	private final ConfigColor colorSeleccion = ConfigColor.de("gui.universalator.jelly.seleccion",
			new Color(214, 222, 255));

	private ImageIcon imagenEscalada;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		actualizarTextos();

		Color fondo = colorFondo.obtener();
		Color panel = colorPanel.obtener();
		Color texto = colorTexto.obtener();
		Color acento = colorAcento.obtener();
		Color borde = colorBorde.obtener();
		Color advertencia = colorAdvertencia.obtener();
		Color exito = colorExito.obtener();
		Color seleccion = colorSeleccion.obtener();

		getContentPane().setBackground(fondo);
		if (panelRaiz != null)
			panelRaiz.setBackground(fondo);
		if (panelCabecera != null)
			panelCabecera.setBackground(fondo);
		if (panelOpciones != null)
			panelOpciones.setBackground(panel);
		if (panelBotones != null)
			panelBotones.setBackground(fondo);
		if (panelResultados != null)
			panelResultados.setBackground(fondo);

		if (etiquetaTitulo != null) {
			etiquetaTitulo.setForeground(acento.darker());
		}
		if (areaCita != null) {
			areaCita.setForeground(acento.darker());
			areaCita.setFont(areaCita.getFont().deriveFont(Font.BOLD));
		}
		if (areaDescripcion != null) {
			areaDescripcion.setForeground(texto);
		}
		if (etiquetaEstado != null) {
			etiquetaEstado.setForeground(exito.darker());
		}
		if (areaReporte != null) {
			areaReporte.setBackground(panel);
			areaReporte.setForeground(texto);
			areaReporte.setBorder(BorderFactory.createLineBorder(borde));
		}
		if (listaResultados != null) {
			listaResultados.setBackground(panel);
			listaResultados.setForeground(texto);
			listaResultados.setSelectionBackground(seleccion);
			listaResultados.setSelectionForeground(texto);
			listaResultados.setBorder(BorderFactory.createLineBorder(borde));
		}

		estilizarCheckbox(checkUniversalator, panel, texto);
		estilizarCheckbox(checkMetadataLocales, panel, advertencia);
		estilizarCheckbox(checkMetadataRemotos, panel, advertencia);
		estilizarBoton(botonEscanear, acento, Color.WHITE, borde);
		estilizarBoton(botonRefrescar, panel, texto, borde);
		estilizarBoton(botonEliminar, acento.darker(), Color.WHITE, borde);

		if (panelOpciones != null) {
			panelOpciones.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borde),
					MonitorDePID.idioma.universalatorMetodosTitulo()));
		}

		aplicarTemaRecursivo(this.getContentPane(), fondo, panel, texto, borde);
		cargarImagen();
	}

	private void cargarImagen() {
		try {
			java.io.File archivo = Statics.carpeta.resolve("imagenes/jelly_stara.png").toFile();
			if (!archivo.exists())
				return;
			ImageIcon original = new ImageIcon(archivo.getAbsolutePath());
			Image img = original.getImage().getScaledInstance(220, 250, Image.SCALE_SMOOTH);
			imagenEscalada = new ImageIcon(img);
			if (imagenJelly != null)
				imagenJelly.setIcon(imagenEscalada);
		} catch (Exception ignored) {
		}
	}

	private void estilizarCheckbox(JCheckBox box, Color fondo, Color texto) {
		if (box == null)
			return;
		box.setOpaque(true);
		box.setBackground(fondo);
		box.setForeground(texto);
	}

	private void estilizarBoton(JButton boton, Color fondo, Color texto, Color borde) {
		if (boton == null)
			return;
		boton.setOpaque(true);
		boton.setBackground(fondo);
		boton.setForeground(texto);
		boton.setBorder(BorderFactory.createLineBorder(borde));
		boton.setFocusPainted(false);
	}

	private void aplicarTemaRecursivo(Container contenedor, Color fondo, Color panel, Color texto, Color borde) {
		for (Component c : contenedor.getComponents()) {
			if (c instanceof JPanel) {
				c.setBackground(fondo);
			}
			if (c instanceof JLabel) {
				c.setForeground(texto);
			}
			if (c instanceof JScrollPane) {
				((JScrollPane) c).getViewport().setBackground(panel);
				((JScrollPane) c).setBorder(BorderFactory.createLineBorder(borde));
			}
			if (c instanceof JViewport) {
				c.setBackground(panel);
			}
			if (c instanceof JTextArea) {
				if (c != areaCita && c != areaDescripcion) {
					c.setBackground(panel);
				}
				c.setForeground(texto);
			}
			if (c instanceof JList) {
				c.setBackground(panel);
				c.setForeground(texto);
			}
			if (c instanceof JSplitPane) {
				((JSplitPane) c).setBackground(fondo);
				((JSplitPane) c).setDividerSize(6);
			}
			if (c instanceof JComponent && !(c instanceof JButton) && !(c instanceof JCheckBox)) {
				((JComponent) c).setBorder(((JComponent) c).getBorder());
			}
			if (c instanceof Container) {
				aplicarTemaRecursivo((Container) c, fondo, panel, texto, borde);
			}
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();
		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.universalatorColor("fondo"));
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.universalatorColor("panel"));
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.universalatorColor("texto"));
		colorAcento.establecerNombreParaMostrar(() -> MonitorDePID.idioma.universalatorColor("acento"));
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.universalatorColor("borde"));
		colorAdvertencia.establecerNombreParaMostrar(() -> MonitorDePID.idioma.universalatorColor("advertencia"));
		colorExito.establecerNombreParaMostrar(() -> MonitorDePID.idioma.universalatorColor("exito"));
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.universalatorColor("seleccion"));
		elementos.add(colorFondo);
		elementos.add(colorPanel);
		elementos.add(colorTexto);
		elementos.add(colorAcento);
		elementos.add(colorBorde);
		elementos.add(colorAdvertencia);
		elementos.add(colorExito);
		elementos.add(colorSeleccion);
		return elementos;
	}
}
