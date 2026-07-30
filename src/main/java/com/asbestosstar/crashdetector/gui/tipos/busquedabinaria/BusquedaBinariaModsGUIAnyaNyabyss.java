package com.asbestosstar.crashdetector.gui.tipos.busquedabinaria;

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
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

/**
 * Tema de la búsqueda binaria inspirado en Anya Nyabyss.
 */
public class BusquedaBinariaModsGUIAnyaNyabyss extends BusquedaBinariaModsGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "anya_nyabyss";

	private final ConfigColor colorFondo = ConfigColor.de("gui.busqueda_binaria.anya.color.fondo",
			new Color(31, 27, 43));
	private final ConfigColor colorPanel = ConfigColor.de("gui.busqueda_binaria.anya.color.panel",
			new Color(48, 40, 67));
	private final ConfigColor colorTexto = ConfigColor.de("gui.busqueda_binaria.anya.color.texto",
			new Color(245, 235, 250));
	private final ConfigColor colorAcento = ConfigColor.de("gui.busqueda_binaria.anya.color.acento",
			new Color(154, 120, 220));
	private final ConfigColor colorAdvertencia = ConfigColor.de("gui.busqueda_binaria.anya.color.advertencia",
			new Color(255, 178, 102));
	private final ConfigColor colorExito = ConfigColor.de("gui.busqueda_binaria.anya.color.exito",
			new Color(133, 214, 165));
	private final ConfigColor colorBorde = ConfigColor.de("gui.busqueda_binaria.anya.color.borde",
			new Color(112, 88, 153));
	private final ConfigColor colorSeleccion = ConfigColor.de("gui.busqueda_binaria.anya.color.seleccion",
			new Color(96, 73, 139));

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
		Color advertencia = colorAdvertencia.obtener();
		Color borde = colorBorde.obtener();
		Color seleccion = colorSeleccion.obtener();

		getContentPane().setBackground(fondo);
		if (panelRaiz != null) {
			panelRaiz.setBackground(fondo);
		}
		if (panelCabecera != null) {
			panelCabecera.setBackground(fondo);
		}
		if (panelInformacion != null) {
			panelInformacion.setBackground(fondo);
		}
		if (panelListas != null) {
			panelListas.setBackground(fondo);
		}
		if (panelBotones != null) {
			panelBotones.setBackground(fondo);
		}

		if (etiquetaTitulo != null) {
			etiquetaTitulo.setForeground(acento);
		}
		if (areaDescripcion != null) {
			areaDescripcion.setForeground(texto);
		}
		if (areaAdvertencia != null) {
			areaAdvertencia.setForeground(advertencia);
			areaAdvertencia.setFont(areaAdvertencia.getFont().deriveFont(Font.BOLD));
		}
		if (areaDependencias != null) {
			areaDependencias.setForeground(colorExito.obtener());
		}
		if (etiquetaEstado != null) {
			etiquetaEstado.setForeground(texto);
		}

		estilizarLista(listaCandidatos, panel, texto, seleccion);
		estilizarLista(listaDesactivados, panel, texto, seleccion);
		estilizarArea(areaHistorial, panel, texto, seleccion);

		estilizarBoton(botonIniciar, acento, texto);
		estilizarBoton(botonPersiste, panel, texto);
		estilizarBoton(botonDesaparecio, colorExito.obtener(), fondo);
		estilizarBoton(botonRestaurar, advertencia, fondo);

		aplicarTemaRecursivo(getContentPane(), fondo, panel, texto, borde);
		cargarImagenAnya();

		revalidate();
		repaint();
	}

	private void cargarImagenAnya() {
		if (imagenAnya == null) {
			return;
		}
		if (imagenEscalada == null || imagenEscalada.getIconWidth() <= 0) {
			ImageIcon original = new ImageIcon(Statics.carpeta.resolve("imagenes/anya_nyabyss.png").toString());
			if (original.getIconWidth() > 0 && original.getIconHeight() > 0) {
				Image escalada = original.getImage().getScaledInstance(184, 184, Image.SCALE_SMOOTH);
				imagenEscalada = new ImageIcon(escalada);
			} else {
				imagenEscalada = new ImageIcon();
			}
		}
		imagenAnya.setIcon(imagenEscalada);
		imagenAnya.setText(imagenEscalada.getIconWidth() > 0 ? "" : "Anya Nyabyss");
		imagenAnya.setForeground(colorAcento.obtener());
	}

	private void estilizarLista(JList<?> lista, Color fondo, Color texto, Color seleccion) {
		if (lista == null) {
			return;
		}
		lista.setBackground(fondo);
		lista.setForeground(texto);
		lista.setSelectionBackground(seleccion);
		lista.setSelectionForeground(texto);
		lista.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
	}

	private void estilizarArea(JTextArea area, Color fondo, Color texto, Color seleccion) {
		if (area == null) {
			return;
		}
		area.setBackground(fondo);
		area.setForeground(texto);
		area.setCaretColor(texto);
		area.setSelectionColor(seleccion);
		area.setSelectedTextColor(texto);
		area.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
	}

	private void estilizarBoton(JButton boton, Color fondo, Color texto) {
		if (boton == null) {
			return;
		}
		boton.setFocusPainted(false);
		boton.setFont(boton.getFont().deriveFont(Font.BOLD));
		if (CrashDetectorGUI.esMac()) {
			boton.setOpaque(false);
			boton.setContentAreaFilled(false);
			return;
		}
		boton.setOpaque(true);
		boton.setContentAreaFilled(true);
		boton.setBackground(fondo);
		boton.setForeground(texto);
		boton.setBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1));
	}

	private void aplicarTemaRecursivo(Container contenedor, Color fondo, Color panel, Color texto, Color borde) {
		if (contenedor == null) {
			return;
		}
		for (Component componente : contenedor.getComponents()) {
			if (componente instanceof JLabel && componente != etiquetaTitulo && componente != imagenAnya) {
				componente.setForeground(texto);
			}
			if (componente instanceof JPanel) {
				componente.setBackground(fondo);
			}
			if (componente instanceof JScrollPane) {
				JScrollPane scroll = (JScrollPane) componente;
				scroll.setBackground(panel);
				scroll.getViewport().setBackground(panel);
				scroll.setBorder(BorderFactory.createLineBorder(borde, 1));
			}
			if (componente instanceof JViewport) {
				componente.setBackground(panel);
			}
			if (componente instanceof JSplitPane) {
				JSplitPane split = (JSplitPane) componente;
				split.setBackground(fondo);
				split.setDividerSize(6);
			}
			if (componente instanceof JComponent && !(componente instanceof JTextArea) && !(componente instanceof JList)
					&& !(componente instanceof JButton)) {
				((JComponent) componente).setOpaque(true);
			}
			if (componente instanceof Container) {
				aplicarTemaRecursivo((Container) componente, fondo, panel, texto, borde);
			}
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.busquedaBinariaModsColor("fondo"));
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.busquedaBinariaModsColor("panel"));
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.busquedaBinariaModsColor("texto"));
		colorAcento.establecerNombreParaMostrar(() -> MonitorDePID.idioma.busquedaBinariaModsColor("acento"));
		colorAdvertencia.establecerNombreParaMostrar(() -> MonitorDePID.idioma.busquedaBinariaModsColor("advertencia"));
		colorExito.establecerNombreParaMostrar(() -> MonitorDePID.idioma.busquedaBinariaModsColor("exito"));
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.busquedaBinariaModsColor("borde"));
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.busquedaBinariaModsColor("seleccion"));

		elementos.add(colorFondo);
		elementos.add(colorPanel);
		elementos.add(colorTexto);
		elementos.add(colorAcento);
		elementos.add(colorAdvertencia);
		elementos.add(colorExito);
		elementos.add(colorBorde);
		elementos.add(colorSeleccion);
		return elementos;
	}
}
