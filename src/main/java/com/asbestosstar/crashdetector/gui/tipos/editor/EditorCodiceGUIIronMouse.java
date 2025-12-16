package com.asbestosstar.crashdetector.gui.tipos.editor;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class EditorCodiceGUIIronMouse extends EditorFirmasGUI {

	public static String ID = "ironmouse";

	public EditorCodiceGUIIronMouse() {
		rosaFondo = ConfigColor.de("tema.ironmouse.editor.color.fondo", new java.awt.Color(250, 230, 245));
		rosaSuave = ConfigColor.de("tema.ironmouse.editor.color.rosa.suave", new java.awt.Color(255, 204, 235));
		moradoAcento = ConfigColor.de("tema.ironmouse.editor.color.morado.acento", new java.awt.Color(186, 85, 211));
		textoOscuro = ConfigColor.de("tema.ironmouse.editor.color.texto.oscuro", new java.awt.Color(40, 35, 45));
		bordeSuave = ConfigColor.de("tema.ironmouse.editor.color.borde.suave", new java.awt.Color(220, 180, 210));
		fondoCampo = ConfigColor.de("tema.ironmouse.editor.color.fondo.campo", new java.awt.Color(255, 255, 255));

	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
	    List<ElementoConfig> elementos = new ArrayList<>();

	    rosaFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.rosaFondo());
	    rosaSuave.establecerNombreParaMostrar(() -> MonitorDePID.idioma.rosaSuave());
	    moradoAcento.establecerNombreParaMostrar(() -> MonitorDePID.idioma.moradoAcento());
	    textoOscuro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.textoOscuro());
	    bordeSuave.establecerNombreParaMostrar(() -> MonitorDePID.idioma.bordeSuave());
	    fondoCampo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.fondoCampo());

	    elementos.add(rosaFondo);
	    elementos.add(rosaSuave);
	    elementos.add(moradoAcento);
	    elementos.add(textoOscuro);
	    elementos.add(bordeSuave);
	    elementos.add(fondoCampo);

	    return elementos;
	}

	@Override
	public void recargarApariencia() {
		// Inicializar colores

		// Aplicar estilos
		aplicarEstilos();
	}

	@Override
	public void init() {
		// Configuración básica de la ventana
		super.init();

		// Recarga la apariencia (inicializa colores y construye UI)
		recargarApariencia();

		setVisible(true);
	}
}