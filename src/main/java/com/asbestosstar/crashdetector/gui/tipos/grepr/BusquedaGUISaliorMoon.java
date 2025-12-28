package com.asbestosstar.crashdetector.gui.tipos.grepr;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class BusquedaGUISaliorMoon extends GrepRGUI {

	public static final String ID = "saliormoon_grep";

	public BusquedaGUISaliorMoon() {
		colorFondoVentana = ConfigColor.de("tema.saliormoon.grep.color.fondo.ventana", new java.awt.Color(12, 18, 56));
		colorPanel = ConfigColor.de("tema.saliormoon.grep.color.panel", new java.awt.Color(20, 28, 78));
		colorTexto = ConfigColor.de("tema.saliormoon.grep.color.texto", java.awt.Color.WHITE);
		colorBoton = ConfigColor.de("tema.saliormoon.grep.color.boton", new java.awt.Color(220, 64, 92));
		colorBotonTexto = ConfigColor.de("tema.saliormoon.grep.color.texto.boton", java.awt.Color.WHITE);
		colorCampo = ConfigColor.de("tema.saliormoon.grep.color.campo", new java.awt.Color(16, 22, 66));
		colorBordeDestacado = ConfigColor.de("tema.saliormoon.grep.color.borde.destacado",
				new java.awt.Color(255, 215, 0)); // Dorado
		colorSeleccionTexto = ConfigColor.de("tema.saliormoon.grep.color.seleccion.texto",
				new java.awt.Color(255, 215, 0)); // Dorado
		colorTextoSeleccionado = ConfigColor.de("tema.saliormoon.grep.color.texto.seleccionado", java.awt.Color.BLACK); // Negro
																														// sobre
																														// dorado

	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		// Inicializar colores (esto asigna valores a los campos públicos del padre)

		// Ahora que los colores están inicializados, aplicar estilos
		aplicarEstilos();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();

		colorFondoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoVentana());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorBotonTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonTexto());
		colorCampo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCampo());
		colorBordeDestacado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBordeDestacado());
		colorSeleccionTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorSeleccionTexto());
		colorTextoSeleccionado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoSeleccionado());

		elementos.add(colorFondoVentana);
		elementos.add(colorPanel);
		elementos.add(colorTexto);
		elementos.add(colorBoton);
		elementos.add(colorBotonTexto);
		elementos.add(colorCampo);
		elementos.add(colorBordeDestacado);
		elementos.add(colorSeleccionTexto);
		elementos.add(colorTextoSeleccionado);

		return elementos;
	}

	@Override
	public void init() {
		// Configurar ventana básica (tamaño, cierre, ubicación)
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1000, 600);
		setLocationRelativeTo(null);

		// Recarga la apariencia (que inicializa colores y aplica estilos)
		recargarApariencia();

		// Mostrar ventana
		this.setVisible(true);
	}
}