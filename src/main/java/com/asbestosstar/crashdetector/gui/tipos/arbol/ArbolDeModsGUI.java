package com.asbestosstar.crashdetector.gui.tipos.arbol;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public interface ArbolDeModsGUI extends CrashDetectorGUI,BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<ArbolDeModsGUI>> GUIS = new HashMap<String, Supplier<ArbolDeModsGUI>>();

	public default TipoGUI<ArbolDeModsGUI> tipo() {
		return TipoGUI.ARBOL_DE_MODS;
	}
}
