package com.asbestosstar.crashdetector.gui.tipos.compartir_instancia;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class CompartirInstanciaLegacy extends CompartirInstanciaGUI {

	public static final String ID = "compartir_instancia_legacy";

	private ConfigColor colorFondoPrincipal = ConfigColor.de("compartir_instancia.fondo", new Color(28, 28, 28));
	private ConfigColor colorPanel = ConfigColor.de("compartir_instancia.panel", new Color(41, 41, 41));
	private ConfigColor colorTexto = ConfigColor.de("compartir_instancia.texto", new Color(235, 235, 235));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		getContentPane().setBackground(colorFondoPrincipal.obtener());

		if (areaPolitica != null) {
			areaPolitica.setBackground(colorPanel.obtener());
			areaPolitica.setForeground(colorTexto.obtener());
			areaPolitica.setCaretColor(colorTexto.obtener());
		}

		if (arbolRutas != null) {
			arbolRutas.setBackground(colorPanel.obtener());
			arbolRutas.setForeground(colorTexto.obtener());
		}

		if (comboFormato != null) {
			comboFormato.setBackground(colorPanel.obtener());
			comboFormato.setForeground(colorTexto.obtener());
		}

		if (comboServicio != null) {
			comboServicio.setBackground(colorPanel.obtener());
			comboServicio.setForeground(colorTexto.obtener());
		}

		if (botonCompartir != null) {
			botonCompartir.setText(MonitorDePID.idioma.compartirInstanciaBotonCompartir());
			botonCompartir.setBackground(colorPanel.obtener());
			botonCompartir.setForeground(colorTexto.obtener());
		}

		if (botonRefrescar != null) {
			botonRefrescar.setText(MonitorDePID.idioma.compartirInstanciaBotonRefrescar());
			botonRefrescar.setBackground(colorPanel.obtener());
			botonRefrescar.setForeground(colorTexto.obtener());
		}

		if (etiquetaEstado != null) {
			etiquetaEstado.setForeground(colorTexto.obtener());
		}

		revalidate();
		repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<>();

		colorFondoPrincipal.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoPrincipal());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.compartirInstanciaColorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.compartirInstanciaColorTexto());

		ret.add(colorFondoPrincipal);
		ret.add(colorPanel);
		ret.add(colorTexto);

		return ret;
	}
}