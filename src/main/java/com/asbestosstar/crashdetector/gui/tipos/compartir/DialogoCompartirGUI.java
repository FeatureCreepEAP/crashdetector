package com.asbestosstar.crashdetector.gui.tipos.compartir;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public interface DialogoCompartirGUI extends CrashDetectorGUI {

	public static Map<String, Supplier<DialogoCompartirGUI>> GUIS = new HashMap<String, Supplier<DialogoCompartirGUI>>();

	public default TipoGUI<DialogoCompartirGUI> tipo() {
		return TipoGUI.DIALOGO_COMPARTIR;
	}
}
