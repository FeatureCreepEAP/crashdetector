package com.asbestosstar.crashdetector.gui.tipos.guard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextArea;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación visual por defecto del Guard.
 *
 * Tema inspirado en el icono tlguard.
 */
public class GuardiaSketchyVT extends GuardiaGUI {

	public static final String ID = "sketchyvt";

	private ConfigColor colorFondoPrincipal = ConfigColor.de("tema.guard.sketchyvt.color.fondo", new Color(20, 30, 44));
	private ConfigColor colorPanel = ConfigColor.de("tema.guard.sketchyvt.color.panel", new Color(29, 44, 65));
	private ConfigColor colorTexto = ConfigColor.de("tema.guard.sketchyvt.color.texto", new Color(220, 232, 245));
	private ConfigColor colorTextoSecundario = ConfigColor.de("tema.guard.sketchyvt.color.texto.secundario",
			new Color(150, 181, 214));
	private ConfigColor colorTabla = ConfigColor.de("tema.guard.sketchyvt.color.tabla", new Color(16, 25, 37));
	private ConfigColor colorSeleccion = ConfigColor.de("tema.guard.sketchyvt.color.seleccion",
			new Color(59, 103, 168));
	private ConfigColor colorSeleccionTexto = ConfigColor.de("tema.guard.sketchyvt.color.seleccion.texto",
			new Color(255, 255, 255));
	private ConfigColor colorCarga = ConfigColor.de("tema.guard.sketchyvt.color.carga", new Color(255, 255, 255));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		Color fondo = colorFondoPrincipal.obtener();
		Color panel = colorPanel.obtener();
		Color texto = colorTexto.obtener();
		Color textoSec = colorTextoSecundario.obtener();
		Color tabla = colorTabla.obtener();
		Color seleccion = colorSeleccion.obtener();
		Color textoSeleccion = colorSeleccionTexto.obtener();

		setTitle(MonitorDePID.idioma.guardTitulo());
		getContentPane().setBackground(fondo);

		if (botonEscanearTodo != null) {
			botonEscanearTodo.setText(MonitorDePID.idioma.guardEscanearTodo());
			botonEscanearTodo.setBackground(panel);
			botonEscanearTodo.setForeground(texto);
			botonEscanearTodo.setFocusPainted(false);
		}

		if (botonEscanearServidores != null) {
			botonEscanearServidores.setText(MonitorDePID.idioma.guardEscanearServidores());
			botonEscanearServidores.setBackground(panel);
			botonEscanearServidores.setForeground(texto);
			botonEscanearServidores.setFocusPainted(false);
		}

		if (botonEscanearMalware != null) {
			botonEscanearMalware.setText(MonitorDePID.idioma.guardEscanearMalware());
			botonEscanearMalware.setBackground(panel);
			botonEscanearMalware.setForeground(texto);
			botonEscanearMalware.setFocusPainted(false);
		}

		if (etiquetaEstado != null) {
			etiquetaEstado.setForeground(textoSec);
		}

		if (areaDescripcion != null) {
			areaDescripcion.setForeground(textoSec);
			areaDescripcion.setCaretColor(textoSec);
		}

		if (etiquetaSketchyVT != null) {
			etiquetaSketchyVT.setForeground(texto);
		}

		if (tablaServidores != null) {
			estilizarTabla(tablaServidores, tabla, texto, seleccion, textoSeleccion);
		}

		if (tablaMalware != null) {
			estilizarTabla(tablaMalware, tabla, texto, seleccion, textoSeleccion);
		}

		if (gifCarga != null) {
			gifCarga.setText(MonitorDePID.idioma.cargando());
			gifCarga.setForeground(colorCarga.obtener());
		}

		if (imagenGuard != null) {
			ImageIcon icon = cargarImagenConFallback(Statics.carpeta.resolve("imagenes/tlguard.png").toString(),
					"/mnt/data/tlguard.png");
			imagenGuard.setIcon(icon);
			imagenGuard.setText(icon.getIconWidth() > 0 ? "" : "Guard");
			imagenGuard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			imagenGuard.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		}

		if (imagenSketchyVT != null) {
			ImageIcon sketchy = cargarImagenConFallback(Statics.carpeta.resolve("imagenes/sketchyvt.png").toString(),
					"/mnt/data/sketchyvt.png");
			imagenSketchyVT.setIcon(sketchy);
			imagenSketchyVT.setText(sketchy.getIconWidth() > 0 ? "" : "SketchyVT");
			imagenSketchyVT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			imagenSketchyVT.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		}

		ajustarTemaRecursivo(getContentPane(), fondo, panel, texto);

		revalidate();
		repaint();
	}

	private void estilizarTabla(JTable tabla, Color fondo, Color texto, Color seleccion, Color textoSeleccion) {
		tabla.setBackground(fondo);
		tabla.setForeground(texto);
		tabla.setSelectionBackground(seleccion);
		tabla.setSelectionForeground(textoSeleccion);
		tabla.setGridColor(seleccion.darker());
		tabla.getTableHeader().setBackground(fondo.darker());
		tabla.getTableHeader().setForeground(texto);
	}

	private void ajustarTemaRecursivo(java.awt.Container contenedor, Color fondo, Color panel, Color texto) {
		for (java.awt.Component c : contenedor.getComponents()) {

			if (c instanceof javax.swing.JLabel) {
				c.setForeground(texto);
				c.setBackground(fondo);
			}

			if (c instanceof javax.swing.JPanel) {
				c.setBackground(fondo);
				if (c instanceof JComponent) {
					((JComponent) c).setOpaque(true);
				}
			}

			if (c instanceof javax.swing.JScrollPane) {
				javax.swing.JScrollPane sp = (javax.swing.JScrollPane) c;
				sp.getViewport().setBackground(panel);
				sp.setBackground(panel);
				sp.setForeground(texto);
			}

			if (c instanceof javax.swing.JViewport) {
				c.setBackground(panel);
				c.setForeground(texto);
			}

			if (c instanceof javax.swing.JSplitPane) {
				c.setBackground(fondo);
				c.setForeground(texto);
			}

			if (c instanceof JTextArea) {
				JTextArea area = (JTextArea) c;
				if (!area.isOpaque()) {
					area.setForeground(texto);
					area.setCaretColor(texto);
				}
			}

			if (c instanceof java.awt.Container) {
				ajustarTemaRecursivo((java.awt.Container) c, fondo, panel, texto);
			}
		}
	}

	private ImageIcon cargarImagenConFallback(String... rutas) {
		for (String ruta : rutas) {
			if (ruta == null || ruta.trim().isEmpty()) {
				continue;
			}
			ImageIcon icon = new ImageIcon(ruta);
			if (icon.getIconWidth() > 0) {
				return icon;
			}
		}
		return new ImageIcon();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();

		colorFondoPrincipal.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoPrincipal());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.guardColorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.guardColorTexto());
		colorTextoSecundario.establecerNombreParaMostrar(() -> MonitorDePID.idioma.guardColorTextoSecundario());
		colorTabla.establecerNombreParaMostrar(() -> MonitorDePID.idioma.guardColorTabla());
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.guardColorSeleccion());
		colorSeleccionTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.guardColorSeleccionTexto());
		colorCarga.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoOverlayCarga());

		elementos.add(colorFondoPrincipal);
		elementos.add(colorPanel);
		elementos.add(colorTexto);
		elementos.add(colorTextoSecundario);
		elementos.add(colorTabla);
		elementos.add(colorSeleccion);
		elementos.add(colorSeleccionTexto);
		elementos.add(colorCarga);

		return elementos;
	}
}