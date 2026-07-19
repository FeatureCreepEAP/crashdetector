package com.asbestosstar.crashdetector.gui.tipos.heapdump;

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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.JViewport;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Tema del visor de heap dump inspirado en la selección iraní de fútbol.
 */
public final class VisorHeapDumpIranFifa extends VisorHeapDumpGUI {

	private static final long serialVersionUID = 1L;

	private final ConfigColor colorFondo = ConfigColor.de("tema.heapdump.iran.fondo", new Color(20, 73, 55));
	private final ConfigColor colorPanel = ConfigColor.de("tema.heapdump.iran.panel", new Color(245, 246, 242));
	private final ConfigColor colorTexto = ConfigColor.de("tema.heapdump.iran.texto", new Color(30, 38, 35));
	private final ConfigColor colorTextoClaro = ConfigColor.de("tema.heapdump.iran.texto.claro",
			new Color(250, 250, 248));
	private final ConfigColor colorVerde = ConfigColor.de("tema.heapdump.iran.verde", new Color(35, 130, 85));
	private final ConfigColor colorRojo = ConfigColor.de("tema.heapdump.iran.rojo", new Color(193, 39, 45));
	private final ConfigColor colorTabla = ConfigColor.de("tema.heapdump.iran.tabla", new Color(255, 255, 255));
	private final ConfigColor colorSeleccion = ConfigColor.de("tema.heapdump.iran.seleccion", new Color(206, 231, 217));
	private final ConfigColor colorBorde = ConfigColor.de("tema.heapdump.iran.borde", new Color(174, 181, 176));

	@Override
	public void recargarApariencia() {
		recargarTextos();

		Color fondo = colorFondo.obtener();
		Color panel = colorPanel.obtener();
		Color texto = colorTexto.obtener();
		Color textoClaro = colorTextoClaro.obtener();
		Color verde = colorVerde.obtener();
		Color rojo = colorRojo.obtener();
		Color tabla = colorTabla.obtener();
		Color seleccion = colorSeleccion.obtener();
		Color borde = colorBorde.obtener();

		if (panelRaiz != null) {
			panelRaiz.setBackground(fondo);
		}
		if (panelCabecera != null) {
			panelCabecera.setBackground(fondo);
			panelCabecera.setBorder(BorderFactory.createLineBorder(borde));
		}
		if (panelControles != null) {
			panelControles.setBackground(fondo);
		}
		if (panelEstado != null) {
			panelEstado.setBackground(fondo);
		}

		if (areaDescripcion != null) {
			areaDescripcion.setOpaque(true);
			areaDescripcion.setBackground(fondo);
			areaDescripcion.setForeground(textoClaro);
			areaDescripcion.setCaretColor(textoClaro);
		}

		estilizarBoton(botonImportar, verde, textoClaro);
		estilizarBoton(botonCancelar, rojo, textoClaro);
		estilizarBoton(botonExpandir, panel, texto);
		estilizarBoton(botonContraer, panel, texto);

		if (casillaIdentificarMods != null) {
			casillaIdentificarMods.setBackground(fondo);
			casillaIdentificarMods.setForeground(textoClaro);
		}

		if (tablaClases != null) {
			tablaClases.setBackground(tabla);
			tablaClases.setForeground(texto);
			tablaClases.setSelectionBackground(seleccion);
			tablaClases.setSelectionForeground(texto);
			tablaClases.setGridColor(borde);
			tablaClases.getTableHeader().setBackground(verde);
			tablaClases.getTableHeader().setForeground(textoClaro);
		}

		if (arbolMods != null) {
			arbolMods.setBackground(tabla);
			arbolMods.setForeground(texto);
		}

		if (progreso != null) {
			progreso.setBackground(panel);
			progreso.setForeground(verde);
			progreso.setBorder(BorderFactory.createLineBorder(borde));
		}

		if (etiquetaEstado != null) {
			etiquetaEstado.setForeground(textoClaro);
		}

		if (pestanas != null) {
			pestanas.setBackground(panel);
			pestanas.setForeground(texto);
		}

		cargarImagen();
		aplicarTemaRecursivo(panelRaiz, fondo, panel, texto, textoClaro);

		/*
		 * Restauramos componentes cuyo color específico no debe ser sustituido por el
		 * recorrido genérico.
		 */
		if (areaDescripcion != null) {
			areaDescripcion.setBackground(fondo);
			areaDescripcion.setForeground(textoClaro);
		}
		if (panelControles != null) {
			panelControles.setBackground(fondo);
		}
		if (panelEstado != null) {
			panelEstado.setBackground(fondo);
		}
		if (casillaIdentificarMods != null) {
			casillaIdentificarMods.setBackground(fondo);
			casillaIdentificarMods.setForeground(textoClaro);
		}

		revalidate();
		repaint();
	}

	private void cargarImagen() {
		if (imagenTema == null) {
			return;
		}

		ImageIcon icono = null;
		URL recurso = getClass().getResource("/imagenes/fifa_iran.png");
		if (recurso != null) {
			icono = new ImageIcon(recurso);
		}

		if (icono == null || icono.getIconWidth() <= 0) {
			icono = new ImageIcon(Statics.carpeta.resolve("imagenes/fifa_iran.png").toString());
		}

		if (icono.getIconWidth() > 0) {
			Image imagen = icono.getImage().getScaledInstance(240, 135, Image.SCALE_SMOOTH);
			imagenTema.setIcon(new ImageIcon(imagen));
			imagenTema.setText("");
		} else {
			imagenTema.setIcon(null);
			imagenTema.setText(MonitorDePID.idioma.heapVisorImagenAlternativa());
			imagenTema.setForeground(colorTextoClaro.obtener());
		}
	}

	private void estilizarBoton(JButton boton, Color fondo, Color texto) {
		if (boton == null) {
			return;
		}
		boton.setFocusPainted(false);
		boton.setOpaque(true);
		boton.setContentAreaFilled(true);
		boton.setBorderPainted(false);
		boton.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
		boton.setBackground(fondo);
		boton.setForeground(texto);
	}

	private void aplicarTemaRecursivo(Container contenedor, Color fondo, Color panel, Color texto, Color textoClaro) {
		if (contenedor == null) {
			return;
		}

		for (Component componente : contenedor.getComponents()) {
			if (componente instanceof JPanel) {
				componente.setBackground(panel);
			}
			if (componente instanceof JLabel) {
				componente.setForeground(texto);
			}
			if (componente instanceof JTextArea && componente != areaDescripcion) {
				componente.setBackground(panel);
				componente.setForeground(texto);
			}
			if (componente instanceof JScrollPane) {
				JScrollPane scroll = (JScrollPane) componente;
				scroll.setBackground(panel);
				scroll.getViewport().setBackground(panel);
			}
			if (componente instanceof JViewport) {
				componente.setBackground(panel);
			}
			if (componente instanceof JTabbedPane) {
				componente.setBackground(panel);
				componente.setForeground(texto);
			}
			if (componente instanceof Container) {
				aplicarTemaRecursivo((Container) componente, fondo, panel, texto, textoClaro);
			}
		}
	}

	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorTextoClaro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.heapVisorColorTextoClaro());
		colorVerde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.heapVisorColorVerde());
		colorRojo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.heapVisorColorRojo());
		colorTabla.establecerNombreParaMostrar(() -> MonitorDePID.idioma.heapVisorColorTabla());
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.heapVisorColorSeleccion());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.heapVisorColorBorde());

		elementos.add(colorFondo);
		elementos.add(colorPanel);
		elementos.add(colorTexto);
		elementos.add(colorTextoClaro);
		elementos.add(colorVerde);
		elementos.add(colorRojo);
		elementos.add(colorTabla);
		elementos.add(colorSeleccion);
		elementos.add(colorBorde);
		return elementos;
	}
}
