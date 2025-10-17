package com.asbestosstar.crashdetector.gui.tipos.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public interface EditorFirmasGUI extends CrashDetectorGUI,BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<EditorFirmasGUI>> GUIS = new HashMap<String, Supplier<EditorFirmasGUI>>();

	public default TipoGUI<EditorFirmasGUI> tipo() {
		return TipoGUI.EDITOR_FIRMAS;
	}
}
