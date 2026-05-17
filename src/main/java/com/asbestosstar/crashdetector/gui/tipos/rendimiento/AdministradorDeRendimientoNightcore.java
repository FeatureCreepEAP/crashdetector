package com.asbestosstar.crashdetector.gui.tipos.rendimiento;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTextArea;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class AdministradorDeRendimientoNightcore extends AdministradorDeRendimientoGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "nightcore";

	private ConfigColor colorFondo = ConfigColor.de("tema.rendimiento.nightcore.fondo", new Color(18, 16, 36));
	private ConfigColor colorPanel = ConfigColor.de("tema.rendimiento.nightcore.panel", new Color(31, 28, 62));
	private ConfigColor colorTexto = ConfigColor.de("tema.rendimiento.nightcore.texto", new Color(238, 232, 255));
	private ConfigColor colorTextoSecundario = ConfigColor.de("tema.rendimiento.nightcore.texto.secundario",
			new Color(188, 178, 240));
	private ConfigColor colorBoton = ConfigColor.de("tema.rendimiento.nightcore.boton", new Color(122, 72, 188));
	private ConfigColor colorBotonTexto = ConfigColor.de("tema.rendimiento.nightcore.boton.texto",
			new Color(255, 255, 255));
	private ConfigColor colorSeleccion = ConfigColor.de("tema.rendimiento.nightcore.seleccion",
			new Color(214, 88, 190));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		Color fondo = colorFondo.obtener();
		Color panel = colorPanel.obtener();
		Color texto = colorTexto.obtener();
		Color textoSecundario = colorTextoSecundario.obtener();
		Color boton = colorBoton.obtener();
		Color botonTexto = colorBotonTexto.obtener();
		Color seleccion = colorSeleccion.obtener();

		setTitle(MonitorDePID.idioma.rendimientoTitulo());
		getContentPane().setBackground(fondo);

		if (imagenNightcore != null) {
			ImageIcon icono = new ImageIcon(Statics.carpeta.resolve("imagenes/nightcore.png").toString());
			imagenNightcore.setIcon(icono);
			imagenNightcore.setText(icono.getIconWidth() > 0 ? "" : "Nightcore");
		}

		estilizarArea(areaDescripcion, panel, textoSecundario, seleccion);
		estilizarArea(areaResumen, panel, texto, seleccion);
		estilizarArea(areaAmbiental, panel, texto, seleccion);
		estilizarArea(areaMods, panel, texto, seleccion);
		estilizarArea(areaConfigs, panel, texto, seleccion);

		estilizarBoton(botonAnalizar, MonitorDePID.idioma.rendimientoBotonAnalizar(), boton, botonTexto);
		estilizarBoton(botonAbrirGPU, MonitorDePID.idioma.rendimientoBotonAbrirGPU(), boton, botonTexto);

		ajustarTemaRecursivo(getContentPane(), fondo, panel, texto);

		revalidate();
		repaint();
	}

	private void estilizarArea(JTextArea area, Color fondo, Color texto, Color seleccion) {
		if (area == null) {
			return;
		}

		area.setBackground(fondo);
		area.setForeground(texto);
		area.setCaretColor(texto);
		area.setSelectionColor(seleccion);
		area.setSelectedTextColor(Color.WHITE);
	}

	private void estilizarBoton(javax.swing.JButton boton, String texto, Color fondo, Color frente) {
		if (boton == null) {
			return;
		}

		boton.setText(texto);
		boton.setBackground(fondo);
		boton.setForeground(frente);
		boton.setOpaque(true);
		boton.setFocusPainted(false);
	}

	private void ajustarTemaRecursivo(java.awt.Container contenedor, Color fondo, Color panel, Color texto) {
		for (java.awt.Component c : contenedor.getComponents()) {
			if (c instanceof javax.swing.JPanel) {
				c.setBackground(fondo);
				if (c instanceof JComponent) {
					((JComponent) c).setOpaque(true);
				}
			}

			if (c instanceof javax.swing.JScrollPane) {
				javax.swing.JScrollPane sp = (javax.swing.JScrollPane) c;
				sp.setBackground(panel);
				sp.getViewport().setBackground(panel);
			}

			if (c instanceof javax.swing.JTabbedPane) {
				c.setBackground(panel);
				c.setForeground(texto);
			}

			if (c instanceof javax.swing.JLabel) {
				c.setForeground(texto);
			}

			if (c instanceof java.awt.Container) {
				ajustarTemaRecursivo((java.awt.Container) c, fondo, panel, texto);
			}
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.rendimientoColorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.rendimientoColorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.rendimientoColorTexto());
		colorTextoSecundario.establecerNombreParaMostrar(() -> MonitorDePID.idioma.rendimientoColorTextoSecundario());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.rendimientoColorBoton());
		colorBotonTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.rendimientoColorBotonTexto());
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.rendimientoColorSeleccion());

		elementos.add(colorFondo);
		elementos.add(colorPanel);
		elementos.add(colorTexto);
		elementos.add(colorTextoSecundario);
		elementos.add(colorBoton);
		elementos.add(colorBotonTexto);
		elementos.add(colorSeleccion);

		return elementos;
	}
}