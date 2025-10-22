package com.asbestosstar.crashdetector.gui.tipos.editor_plantilla;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class EditorPlantillaPredeterminado extends EditorPlantilla {

	public static final String ID = "editor_plantilla_predeterminado";

	@Override
	public String id() {
		return ID;
	}

	@Override
	protected void inicializarColoresEditor() {
		super.inicializarColoresEditor();

		// Configurar valores predeterminados específicos para este editor
		coloresEditor.put(ColorGUIEditor.FONDO, ConfigColor.de(ColorGUIEditor.FONDO.clave(), new Color(240, 240, 245)));
		coloresEditor.put(ColorGUIEditor.TEXTO, ConfigColor.de(ColorGUIEditor.TEXTO.clave(), new Color(50, 50, 50)));
		coloresEditor.put(ColorGUIEditor.CAJA_TEXTO,
				ConfigColor.de(ColorGUIEditor.CAJA_TEXTO.clave(), new Color(255, 255, 255)));
		coloresEditor.put(ColorGUIEditor.BOTON, ConfigColor.de(ColorGUIEditor.BOTON.clave(), new Color(220, 220, 230)));
		coloresEditor.put(ColorGUIEditor.BORDE, ConfigColor.de(ColorGUIEditor.BORDE.clave(), new Color(180, 180, 200)));
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		ArrayList<ElementoConfig> configs = new ArrayList<>();
		configs.add(coloresEditor.get(ColorGUIEditor.FONDO));
		configs.add(coloresEditor.get(ColorGUIEditor.TEXTO));
		configs.add(coloresEditor.get(ColorGUIEditor.CAJA_TEXTO));
		configs.add(coloresEditor.get(ColorGUIEditor.BOTON));
		configs.add(coloresEditor.get(ColorGUIEditor.BORDE));
		return configs;
	}
}