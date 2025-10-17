package com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public interface NoRegistroLanzadorGUI extends CrashDetectorGUI {

	public static Map<String, Supplier<NoRegistroLanzadorGUI>> GUIS = new HashMap<String, Supplier<NoRegistroLanzadorGUI>>();

	public default TipoGUI<NoRegistroLanzadorGUI> tipo() {
		return TipoGUI.NO_REGISTRO_LANZER;
	}
}
