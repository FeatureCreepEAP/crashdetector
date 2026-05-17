// GPUFixOptimusPrime.java
package com.asbestosstar.crashdetector.gui.tipos.gpu;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class GPUFixOptimusPrime extends GPUFixGUI {

	public static final String ID = "optimus_prime";

	private ConfigColor colorFondo = ConfigColor.de("tema.gpu.optimusprim.fondo", new Color(18, 24, 38));
	private ConfigColor colorPanel = ConfigColor.de("tema.gpu.optimusprim.panel", new Color(32, 45, 70));
	private ConfigColor colorTexto = ConfigColor.de("tema.gpu.optimusprim.texto", new Color(235, 240, 250));
	private ConfigColor colorBoton = ConfigColor.de("tema.gpu.optimusprim.boton", new Color(120, 28, 28));
	private ConfigColor colorBotonTexto = ConfigColor.de("tema.gpu.optimusprim.boton.texto", new Color(255, 255, 255));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		Color fondo = colorFondo.obtener();
		Color panel = colorPanel.obtener();
		Color texto = colorTexto.obtener();
		Color boton = colorBoton.obtener();
		Color botonTexto = colorBotonTexto.obtener();

		getContentPane().setBackground(fondo);

		if (areaInfo != null) {
			areaInfo.setBackground(panel);
			areaInfo.setForeground(texto);
			areaInfo.setCaretColor(texto);

			if (esWindows()) {
				areaInfo.setText(MonitorDePID.idioma.gpuFixTextoWindows());
			} else if (esLinux()) {
				areaInfo.setText(MonitorDePID.idioma.gpuFixTextoLinux());
			} else if (esMac()) {
				areaInfo.setText(MonitorDePID.idioma.gpuFixTextoMac());
			} else {
				areaInfo.setText(MonitorDePID.idioma.gpuFixTextoOtroSistema());
			}
		}

		if (imagenOptimus != null) {
			ImageIcon icon = new ImageIcon(Statics.carpeta.resolve("imagenes/optimus_prime.png").toString());
			imagenOptimus.setIcon(icon);
			imagenOptimus.setText(icon.getIconWidth() > 0 ? "" : "Optimus Prime");
		}

		estilizarBoton(botonAplicar, MonitorDePID.idioma.gpuFixBotonAplicar(), boton, botonTexto);
		estilizarBoton(botonAbrirFuenteTLauncher, MonitorDePID.idioma.gpuFixBotonFuenteTLauncher(), boton, botonTexto);
		estilizarBoton(botonAbrirVirusTotal, MonitorDePID.idioma.gpuFixBotonVirusTotal(), boton, botonTexto);
		estilizarBoton(botonAbrirOptimusLinux, MonitorDePID.idioma.gpuFixBotonOptimusLinux(), boton, botonTexto);

		revalidate();
		repaint();
	}

	private void estilizarBoton(javax.swing.JButton b, String texto, Color fondo, Color frente) {
		if (b == null) {
			return;
		}

		b.setText(texto);
		b.setBackground(fondo);
		b.setForeground(frente);
		b.setFocusPainted(false);
		b.setOpaque(true);
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();

		elementos.add(colorFondo);
		elementos.add(colorPanel);
		elementos.add(colorTexto);
		elementos.add(colorBoton);
		elementos.add(colorBotonTexto);

		return elementos;
	}
}