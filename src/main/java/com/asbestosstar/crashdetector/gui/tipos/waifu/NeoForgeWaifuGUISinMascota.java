package com.asbestosstar.crashdetector.gui.tipos.waifu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación visual inicial de NeoForge Waifu.
 *
 * NeoForge Waifu todavía no tiene una mascota propia disponible para esta GUI.
 * Por eso el espacio destinado a la ilustración muestra un marcador localizado
 * en vez de inventar o reutilizar una mascota que no corresponda.
 */
public class NeoForgeWaifuGUISinMascota extends NeoForgeWaifuGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "sin_mascota";

	public final ConfigColor colorFondo = ConfigColor.de("tema.neoforge_waifu.sin_mascota.fondo",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));

	public final ConfigColor colorPanel = ConfigColor.de("tema.neoforge_waifu.sin_mascota.panel",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));

	public final ConfigColor colorTexto = ConfigColor.de("tema.neoforge_waifu.sin_mascota.texto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));

	public final ConfigColor colorBoton = ConfigColor.de("tema.neoforge_waifu.sin_mascota.boton",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));

	public final ConfigColor colorBotonTexto = ConfigColor.de("tema.neoforge_waifu.sin_mascota.boton_texto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));

	public final ConfigColor colorCajaTexto = ConfigColor.de("tema.neoforge_waifu.sin_mascota.caja_texto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));

	public final ConfigColor colorBorde = ConfigColor.de("tema.neoforge_waifu.sin_mascota.borde",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace()));

	private JLabel etiquetaSinMascota;

	public NeoForgeWaifuGUISinMascota() {
		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorBotonTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonTexto());
		colorCajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	protected JPanel crearPanelMascota() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(250, 0));
		panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		etiquetaSinMascota = new JLabel("<html><div style='text-align:center;'>"
				+ escaparHtml(MonitorDePID.idioma.neoforgeWaifuSinMascota()) + "</div></html>", SwingConstants.CENTER);
		etiquetaSinMascota.setHorizontalAlignment(SwingConstants.CENTER);
		etiquetaSinMascota.setVerticalAlignment(SwingConstants.CENTER);
		etiquetaSinMascota.setFont(etiquetaSinMascota.getFont().deriveFont(Font.BOLD, 16f));

		panel.add(etiquetaSinMascota, BorderLayout.CENTER);
		return panel;
	}

	@Override
	protected void aplicarApariencia() {
		getContentPane().setBackground(colorFondo.obtener());

		if (panelRaiz != null) {
			panelRaiz.setBackground(colorFondo.obtener());
		}

		if (panelCabecera != null) {
			panelCabecera.setBackground(colorPanel.obtener());
			panelCabecera.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
							BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		}

		if (panelTrabajo != null) {
			panelTrabajo.setBackground(colorFondo.obtener());
		}

		if (panelControles != null) {
			panelControles.setBackground(colorPanel.obtener());
		}

		if (panelConsultaAvanzada != null) {
			panelConsultaAvanzada.setBackground(colorPanel.obtener());
		}

		if (panelBotones != null) {
			panelBotones.setBackground(colorFondo.obtener());
		}

		if (panelMascota != null) {
			panelMascota.setBackground(colorPanel.obtener());
			panelMascota.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
							BorderFactory.createEmptyBorder(12, 12, 12, 12)));
		}

		if (etiquetaTitulo != null) {
			etiquetaTitulo.setForeground(colorTexto.obtener());
		}

		if (areaDescripcion != null) {
			areaDescripcion.setForeground(colorTexto.obtener());
		}

		if (etiquetaTipoConsulta != null) {
			etiquetaTipoConsulta.setForeground(colorTexto.obtener());
		}
		if (etiquetaVariables != null) {
			etiquetaVariables.setForeground(colorTexto.obtener());
		}
		if (etiquetaOperacion != null) {
			etiquetaOperacion.setForeground(colorTexto.obtener());
		}
		if (etiquetaConsulta != null) {
			etiquetaConsulta.setForeground(colorTexto.obtener());
		}

		if (etiquetaSinMascota != null) {
			etiquetaSinMascota.setForeground(colorTexto.obtener());
			etiquetaSinMascota.setBackground(colorPanel.obtener());
			etiquetaSinMascota.setOpaque(true);
		}

		if (comboConsulta != null) {
			comboConsulta.setBackground(colorCajaTexto.obtener());
			comboConsulta.setForeground(colorTexto.obtener());
		}

		if (checkEditorAvanzado != null) {
			checkEditorAvanzado.setBackground(colorPanel.obtener());
			checkEditorAvanzado.setForeground(colorTexto.obtener());
		}

		aplicarCajaTexto(areaVariables);
		aplicarCajaTexto(areaConsulta);
		aplicarCajaTexto(areaResultados);

		if (campoOperacion != null) {
			campoOperacion.setBackground(colorCajaTexto.obtener());
			campoOperacion.setForeground(colorTexto.obtener());
			campoOperacion.setBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1));
		}

		aplicarBoton(botonEjecutar);
		aplicarBoton(botonCopiar);
		aplicarBoton(botonCerrar);

		if (scrollVariables != null) {
			scrollVariables.getViewport().setBackground(colorCajaTexto.obtener());
		}
		if (scrollConsulta != null) {
			scrollConsulta.getViewport().setBackground(colorCajaTexto.obtener());
		}
		if (scrollResultados != null) {
			scrollResultados.getViewport().setBackground(colorCajaTexto.obtener());
		}
	}

	private void aplicarCajaTexto(javax.swing.JTextArea area) {
		if (area == null) {
			return;
		}
		area.setBackground(colorCajaTexto.obtener());
		area.setForeground(colorTexto.obtener());
		area.setCaretColor(colorTexto.obtener());
		area.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
	}

	private void aplicarBoton(javax.swing.JButton boton) {
		if (boton == null) {
			return;
		}
		boton.setBackground(colorBoton.obtener());
		boton.setForeground(colorBotonTexto.obtener());
		boton.setFocusPainted(false);
	}

	private static String escaparHtml(String texto) {
		if (texto == null) {
			return "";
		}
		return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
				.replace("'", "&#39;");
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>(super.obtenerElementosConfigs());
		elementos.add(colorFondo);
		elementos.add(colorPanel);
		elementos.add(colorTexto);
		elementos.add(colorBoton);
		elementos.add(colorBotonTexto);
		elementos.add(colorCajaTexto);
		elementos.add(colorBorde);
		return elementos;
	}
}
