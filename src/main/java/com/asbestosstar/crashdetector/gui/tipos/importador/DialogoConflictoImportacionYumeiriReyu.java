package com.asbestosstar.crashdetector.gui.tipos.importador;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public class DialogoConflictoImportacionYumeiriReyu extends DialogoConflictoImportacionGUI {

	public static final String ID = "yumeiri_reyu";

	private ConfigColor colorFondo = ConfigColor.de("tema.importador.yumeiri.fondo", Config.convertirAColor("#f8f4ff"));
	private ConfigColor colorPanel = ConfigColor.de("tema.importador.yumeiri.panel", Config.convertirAColor("#efe5fb"));
	private ConfigColor colorTexto = ConfigColor.de("tema.importador.yumeiri.texto", Config.convertirAColor("#6b557f"));
	private ConfigColor colorTextoSuave = ConfigColor.de("tema.importador.yumeiri.texto_suave",
			Config.convertirAColor("#9b7faf"));
	private ConfigColor colorBoton = ConfigColor.de("tema.importador.yumeiri.boton", Config.convertirAColor("#d99bdc"));
	private ConfigColor colorBotonTexto = ConfigColor.de("tema.importador.yumeiri.boton_texto",
			Config.convertirAColor("#ffffff"));
	private ConfigColor colorBorde = ConfigColor.de("tema.importador.yumeiri.borde", Config.convertirAColor("#cbb4e9"));

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
		Color borde = colorBorde.obtener();

		getContentPane().setBackground(fondo);

		if (panelIzquierdo != null) {
			panelIzquierdo.setBackground(fondo);
			panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		}

		if (panelCentro != null) {
			panelCentro.setBackground(panel);
			panelCentro.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borde, 1),
					BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		}

		if (panelBotones != null) {
			panelBotones.setBackground(fondo);
		}

		if (imagenTema != null) {
			ImageIcon icon = cargarImagenYumeiri();
			imagenTema.setIcon(icon);
			imagenTema.setText(icon.getIconWidth() > 0 ? "" : "Yumeiri Reyu");
		}

		if (areaMensaje != null) {
			areaMensaje.setBackground(panel);
			areaMensaje.setForeground(texto);
			areaMensaje.setCaretColor(texto);
			areaMensaje.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		}

		estilizarBoton(botonReemplazar, boton, botonTexto);
		estilizarBoton(botonSaltar, boton, botonTexto);
		estilizarBoton(botonRenombrar, boton, botonTexto);
		estilizarBoton(botonFusionar, boton, botonTexto);
		estilizarBoton(botonDescargarDepsNbt, boton, botonTexto);
		estilizarBoton(botonCancelar, suave, botonTexto);

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

	private ImageIcon cargarImagenYumeiri() {
		String ruta1 = Statics.carpeta.resolve("imagenes/yumeiri_reyu.png").toString();
		String ruta2 = "/mnt/data/yumeiri_reyu.png";
		String ruta3 = "/mnt/data/yumeiri_reyu(1).png";

		ImageIcon icon = new ImageIcon(ruta1);
		if (icon.getIconWidth() == 273 && icon.getIconHeight() == 273) {
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

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorColorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorColorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorColorTexto());
		colorTextoSuave.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorColorTextoSuave());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorColorBoton());
		colorBotonTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorColorBotonTexto());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.importadorColorBorde());

		ret.add(colorFondo);
		ret.add(colorPanel);
		ret.add(colorTexto);
		ret.add(colorTextoSuave);
		ret.add(colorBoton);
		ret.add(colorBotonTexto);
		ret.add(colorBorde);

		return ret;
	}
}