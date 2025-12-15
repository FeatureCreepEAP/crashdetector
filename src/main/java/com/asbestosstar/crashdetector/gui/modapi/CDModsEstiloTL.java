package com.asbestosstar.crashdetector.gui.modapi;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.asbestosstar.crashdetector.Config;
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

	private ConfigColor colorFondo;
	private ConfigColor colorTexto;
	private ConfigColor colorBoton;
	private ConfigColor colorCajaTexto;
	public static String ID = "cdmodsestilotl";

	// Constructor sin lógica
	public CDModsEstiloTL() {
	}

	@Override
	protected void inicializarColores() {
		colorFondo = ConfigColor.de("modapi.color.fondo", Config.convertirAColor("#1e1e1e"));
		colorTexto = ConfigColor.de("modapi.color.texto", Config.convertirAColor("#ffffff"));
		colorBoton = ConfigColor.de("modapi.color.boton", Config.convertirAColor("#007acc"));
		colorCajaTexto = ConfigColor.de("modapi.color.cajaTexto", Config.convertirAColor("#333333"));
	}

	@Override
	protected ProveedorMods crearProveedorMods() {
		return new ProveedorModsTlmods();
	}

	// Getters de color
	@Override
	protected Color obtenerColorFondo() {
		return colorFondo.obtener();
	}

	@Override
	protected Color obtenerColorTexto() {
		return colorTexto.obtener();
	}

	@Override
	protected Color obtenerColorBoton() {
		return colorBoton.obtener();
	}

	@Override
	protected Color obtenerColorCajaTexto() {
		return colorCajaTexto.obtener();
	}

	// Estilos visuales
	@Override
	protected void aplicarEstiloVolver(JButton boton) {
		if (!CrashDetectorGUI.esMac()) {
			boton.setForeground(colorTexto.obtener());
			boton.setBackground(colorBoton.obtener());
			boton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
		}
	}

	@Override
	protected void aplicarEstiloBusqueda(JTextField campo) {
		if (!CrashDetectorGUI.esMac()) {
			campo.setBackground(colorCajaTexto.obtener());
			campo.setForeground(colorTexto.obtener());
		}
	}

	@Override
	protected void aplicarEstiloTarjetaMod(JPanel tarjeta) {
		tarjeta.setBackground(Color.decode("#2d2d2d"));
	}

	@Override
	protected void aplicarEstiloSidebar(JPanel sidebar) {
		sidebar.setBackground(colorFondo.obtener().darker());
	}

	@Override
	protected void aplicarEstiloSidebarItem(JPanel item, JCheckBox checkBox, JLabel etiqueta) {
		item.setBackground(colorFondo.obtener().darker());
		checkBox.setBackground(colorFondo.obtener().darker());
		checkBox.setForeground(colorTexto.obtener());
		etiqueta.setForeground(colorTexto.obtener());
	}

	@Override
	protected void aplicarEstiloPrincipal(JPanel panel) {
		panel.setBackground(colorFondo.obtener());
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return ID = "cdmodsestilotl";
	}

	@Override
	public void recargarApariencia() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		// TODO Auto-generated method stub

		List<ElementoConfig> artics = new ArrayList<>();
		artics.add(colorBoton);
		artics.add(colorFondo);
		artics.add(colorTexto);
		artics.add(colorCajaTexto);

		return artics;

	}
}