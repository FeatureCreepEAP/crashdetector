package com.asbestosstar.crashdetector.gui.tipos.importador;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public class ImportadorModpackMausleepsVT extends ImportadorModpackGUI {

	public static final String ID = "mausleepsvt";

	private ConfigColor colorFondo = ConfigColor.de("tema.importador.mausleepsvt.fondo",
			Config.convertirAColor("#f7f1fb"));
	private ConfigColor colorPanel = ConfigColor.de("tema.importador.mausleepsvt.panel",
			Config.convertirAColor("#eadff2"));
	private ConfigColor colorTexto = ConfigColor.de("tema.importador.mausleepsvt.texto",
			Config.convertirAColor("#5d4c6f"));
	private ConfigColor colorTextoSuave = ConfigColor.de("tema.importador.mausleepsvt.texto_suave",
			Config.convertirAColor("#8c779e"));
	private ConfigColor colorBoton = ConfigColor.de("tema.importador.mausleepsvt.boton",
			Config.convertirAColor("#b996d7"));
	private ConfigColor colorBotonTexto = ConfigColor.de("tema.importador.mausleepsvt.boton_texto",
			Config.convertirAColor("#ffffff"));
	private ConfigColor colorDrop = ConfigColor.de("tema.importador.mausleepsvt.drop",
			Config.convertirAColor("#fffaff"));
	private ConfigColor colorBorde = ConfigColor.de("tema.importador.mausleepsvt.borde",
			Config.convertirAColor("#d2b9e5"));

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

		setTitle(MonitorDePID.idioma.importadorModpackTitulo());
		getContentPane().setBackground(fondo);

		if (imagenTema != null) {
			ImageIcon icon = cargarImagen();
			imagenTema.setIcon(icon);
			imagenTema.setText(icon.getIconWidth() > 0 ? "" : "MausleepsVT");
		}

		if (areaDescripcion != null) {
			areaDescripcion.setText(MonitorDePID.idioma.importadorModpackDescripcion());
			areaDescripcion.setBackground(panel);
			areaDescripcion.setForeground(suave);
			areaDescripcion.setCaretColor(texto);
			areaDescripcion.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		}

		if (panelDrop != null) {
			panelDrop.setBackground(colorDrop.obtener());
			panelDrop.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createDashedBorder(colorBorde.obtener(), 7, 5),
							BorderFactory.createEmptyBorder(12, 12, 12, 12)));
		}

		if (etiquetaArchivo != null) {
			etiquetaArchivo.setForeground(texto);
		}

		if (comboFormato != null) {
			comboFormato.setBackground(panel);
			if (!CrashDetectorGUI.esMac()) {
				comboFormato.setForeground(texto);
			}
		}

		estilizarBoton(botonSeleccionar, boton, botonTexto);
		estilizarBoton(botonImportar, boton, botonTexto);

		if (etiquetaEstado != null) {
			etiquetaEstado.setForeground(texto);
		}

		ajustarRecursivo(getContentPane(), fondo, panel, texto);

		revalidate();
		repaint();
	}

	private void estilizarBoton(javax.swing.JButton b, Color fondo, Color texto) {
		if (b == null) {
			return;
		}

		if (!CrashDetectorGUI.esMac()) {
			b.setBackground(fondo);
			b.setForeground(texto);
		}

		b.setFocusPainted(false);
		b.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
	}

	private void ajustarRecursivo(java.awt.Container cont, Color fondo, Color panel, Color texto) {
		for (java.awt.Component c : cont.getComponents()) {
			if (c instanceof javax.swing.JPanel && c != panelDrop) {
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

			if (c instanceof java.awt.Container) {
				ajustarRecursivo((java.awt.Container) c, fondo, panel, texto);
			}
		}
	}

	private ImageIcon cargarImagen() {
		String ruta1 = Statics.carpeta.resolve("imagenes/mausleepsvt.png").toString();
		String ruta2 = "/mnt/data/mausleepsvt.png";
		String ruta3 = "/mnt/data/mausleepsvt(1).png";

		ImageIcon icon = new ImageIcon(ruta1);
		if (icon.getIconWidth() > 0) {
			return icon;
		}

		icon = new ImageIcon(ruta2);
		if (icon.getIconWidth() > 0) {
			return icon;
		}

		icon = new ImageIcon(ruta3);
		if (icon.getIconWidth() > 0) {
			return icon;
		}

		return new ImageIcon();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorModpackColorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorModpackColorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorModpackColorTexto());
		colorTextoSuave.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorModpackColorTextoSuave());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorModpackColorBoton());
		colorBotonTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorModpackColorBotonTexto());
		colorDrop.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorModpackColorDrop());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorModpackColorBorde());

		ret.add(colorFondo);
		ret.add(colorPanel);
		ret.add(colorTexto);
		ret.add(colorTextoSuave);
		ret.add(colorBoton);
		ret.add(colorBotonTexto);
		ret.add(colorDrop);
		ret.add(colorBorde);

		return ret;
	}
}