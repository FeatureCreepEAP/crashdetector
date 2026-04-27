package com.asbestosstar.crashdetector.gui.tipos.actualizador;

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
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public class ActualizadorModsMiwa extends ActualizadorModsGUI {

	public static final String ID = "miwa";

	private ConfigColor colorFondo = ConfigColor.de("tema.actualizador.miwa.fondo", new Color(244, 238, 250));
	private ConfigColor colorPanel = ConfigColor.de("tema.actualizador.miwa.panel", new Color(226, 216, 242));
	private ConfigColor colorTexto = ConfigColor.de("tema.actualizador.miwa.texto", new Color(82, 65, 102));
	private ConfigColor colorTextoSuave = ConfigColor.de("tema.actualizador.miwa.texto_suave",
			new Color(126, 104, 148));
	private ConfigColor colorBoton = ConfigColor.de("tema.actualizador.miwa.boton", new Color(89, 172, 235));
	private ConfigColor colorBotonTexto = ConfigColor.de("tema.actualizador.miwa.boton_texto",
			new Color(255, 255, 255));
	private ConfigColor colorTabla = ConfigColor.de("tema.actualizador.miwa.tabla", new Color(250, 247, 253));
	private ConfigColor colorSeleccion = ConfigColor.de("tema.actualizador.miwa.seleccion", new Color(181, 214, 250));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		Color fondo = colorFondo.obtener();
		Color panel = colorPanel.obtener();
		Color texto = colorTexto.obtener();
		Color suave = colorTextoSuave.obtener();
		Color boton = colorBoton.obtener();
		Color botonTexto = colorBotonTexto.obtener();

		setTitle(MonitorDePID.idioma.actualizadorModsTitulo());
		getContentPane().setBackground(fondo);

		if (imagenTema != null) {
			ImageIcon icon = cargarImagen();
			imagenTema.setIcon(icon);
			imagenTema.setText(icon.getIconWidth() > 0 ? "" : "Miwa");
		}

		if (areaDescripcion != null) {
			areaDescripcion.setText(MonitorDePID.idioma.actualizadorModsDescripcion());
			areaDescripcion.setBackground(panel);
			areaDescripcion.setForeground(suave);
			areaDescripcion.setCaretColor(texto);
			areaDescripcion.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		}

		if (botonEscanear != null) {
			botonEscanear.setText(MonitorDePID.idioma.actualizadorModsBotonEscanear());
			botonEscanear.setBackground(boton);
			if (!CrashDetectorGUI.esMac()) {
				botonEscanear.setForeground(botonTexto);
			}
			botonEscanear.setFocusPainted(false);
		}

		if (botonActualizarTodo != null) {
			botonActualizarTodo.setText(MonitorDePID.idioma.actualizadorModsBotonActualizarTodo());
			botonActualizarTodo.setBackground(boton);
			if (!CrashDetectorGUI.esMac()) {
				botonActualizarTodo.setForeground(botonTexto);
			}
			botonActualizarTodo.setFocusPainted(false);
		}

		if (etiquetaEstado != null) {
			etiquetaEstado.setForeground(texto);
		}

		if (tablaActualizaciones != null) {
			estilizarTabla(tablaActualizaciones);
		}

		ajustarRecursivo(getContentPane(), fondo, panel, texto);

		revalidate();
		repaint();
	}

	private void estilizarTabla(JTable tabla) {
		tabla.setBackground(colorTabla.obtener());
		tabla.setForeground(colorTexto.obtener());
		tabla.setSelectionBackground(colorSeleccion.obtener());
		tabla.setSelectionForeground(colorTexto.obtener());
		tabla.setGridColor(colorPanel.obtener());
		tabla.getTableHeader().setBackground(colorPanel.obtener());
		tabla.getTableHeader().setForeground(colorTexto.obtener());
	}

	private void ajustarRecursivo(java.awt.Container cont, Color fondo, Color panel, Color texto) {
		for (java.awt.Component c : cont.getComponents()) {
			if (c instanceof javax.swing.JPanel) {
				c.setBackground(fondo);
				if (c instanceof JComponent) {
					((JComponent) c).setOpaque(true);
				}
			}

			if (c instanceof javax.swing.JLabel) {
				c.setForeground(texto);
			}

			if (c instanceof javax.swing.JScrollPane) {
				javax.swing.JScrollPane sp = (javax.swing.JScrollPane) c;
				sp.setBackground(panel);
				sp.getViewport().setBackground(panel);
			}

			if (c instanceof javax.swing.JViewport) {
				c.setBackground(panel);
			}

			if (c instanceof JTextArea) {
				c.setBackground(panel);
				c.setForeground(texto);
			}

			if (c instanceof java.awt.Container) {
				ajustarRecursivo((java.awt.Container) c, fondo, panel, texto);
			}
		}
	}

	private ImageIcon cargarImagen() {
		String ruta1 = Statics.carpeta.resolve("imagenes/miwa.png").toString();
		String ruta2 = "/mnt/data/miwa.png";

		ImageIcon icon = new ImageIcon(ruta1);
		if (icon.getIconWidth() == 194 && icon.getIconHeight() == 259) {
			return icon;
		}

		icon = new ImageIcon(ruta2);
		if (icon.getIconWidth() > 0) {
			return icon;
		}

		return new ImageIcon();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.actualizadorModsColorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.actualizadorModsColorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.actualizadorModsColorTexto());
		colorTextoSuave.establecerNombreParaMostrar(() -> MonitorDePID.idioma.actualizadorModsColorTextoSuave());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.actualizadorModsColorBoton());
		colorBotonTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.actualizadorModsColorBotonTexto());
		colorTabla.establecerNombreParaMostrar(() -> MonitorDePID.idioma.actualizadorModsColorTabla());
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.actualizadorModsColorSeleccion());

		ret.add(colorFondo);
		ret.add(colorPanel);
		ret.add(colorTexto);
		ret.add(colorTextoSuave);
		ret.add(colorBoton);
		ret.add(colorBotonTexto);
		ret.add(colorTabla);
		ret.add(colorSeleccion);

		return ret;
	}
}