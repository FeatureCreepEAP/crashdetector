package com.asbestosstar.crashdetector.gui.tipos.modapi;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.dto.modpack.tlmods.ProveedorModsTlmods;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

/**
 * Estilo concreto para TLauncher (tlmods). Toda la inicialización se hace en
 * `init()`.
 */
public class CDModsEstiloTL extends PanelAPIBase {

	private ConfigColor colorFondo = ConfigColor.de("cdmods.color.fondo", Config.convertirAColor("#232a34")); // azul
																												// gris
																												// oscuro
																												// TL

	private ConfigColor colorTexto = ConfigColor.de("cdmods.color.texto", Config.convertirAColor("#e6edf3")); // blanco
																												// suave
																												// (no
																												// puro)

	private ConfigColor colorBoton = ConfigColor.de("cdmods.color.boton", Config.convertirAColor("#2ecc71")); // verde
																												// TL
																												// (instalar)

	private ConfigColor colorCajaTexto = ConfigColor.de("cdmods.color.cajaTexto", Config.convertirAColor("#1b222b")); // input
																														// más
																														// oscuro

	public static String ID = "cdmodsestilotl";

	// Constructor sin lógica
	public CDModsEstiloTL() {
	}

	@Override
	public void inicializarColores() {

	}

	@Override
	public ProveedorMods crearProveedorMods() {
		return crearProveedorSeleccionado();
	}

	// Getters de color
	@Override
	public Color obtenerColorFondo() {
		return colorFondo.obtener();
	}

	@Override
	public Color obtenerColorTexto() {
		return colorTexto.obtener();
	}

	@Override
	public Color obtenerColorBoton() {
		return colorBoton.obtener();
	}

	@Override
	public Color obtenerColorCajaTexto() {
		return colorCajaTexto.obtener();
	}

	// Estilos visuales
	@Override
	public void aplicarEstiloVolver(JButton boton) {
		if (!CrashDetectorGUI.esMac()) {
			boton.setForeground(colorTexto.obtener());
			boton.setBackground(colorBoton.obtener());
			boton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
		}
	}

	@Override
	public void aplicarEstiloBusqueda(JTextField campo) {
		if (!CrashDetectorGUI.esMac()) {
			campo.setBackground(colorCajaTexto.obtener());
			campo.setForeground(colorTexto.obtener());
		}
	}

	@Override
	public void aplicarEstiloTarjetaMod(JPanel tarjeta) {
		tarjeta.setBackground(Color.decode("#2d2d2d"));
	}

	@Override
	public void aplicarEstiloSidebar(JPanel sidebar) {
		sidebar.setBackground(colorFondo.obtener().darker());
	}

	@Override
	public void aplicarEstiloSidebarItem(JPanel item, JCheckBox checkBox, JLabel etiqueta) {
		item.setBackground(colorFondo.obtener().darker());
		checkBox.setBackground(colorFondo.obtener().darker());
		checkBox.setForeground(colorTexto.obtener());
		etiqueta.setForeground(colorTexto.obtener());
	}

	@Override
	public void aplicarEstiloPrincipal(JPanel panel) {
		panel.setBackground(colorFondo.obtener());
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		revalidate();
		repaint();
		actualizarEscalado();
	}

	@Override
	public void aplicarEstiloBotonAccion(JButton boton) {
		if (!CrashDetectorGUI.esMac()) {
			boton.setBackground(colorBoton.obtener());
			boton.setForeground(Color.WHITE);
			boton.setFocusPainted(false);
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> artics = new ArrayList<>();

		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorCajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());

		artics.add(colorBoton);
		artics.add(colorFondo);
		artics.add(colorTexto);
		artics.add(colorCajaTexto);

		return artics;
	}
}