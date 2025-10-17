package com.asbestosstar.crashdetector.gui.tipos.grepr;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public interface GrepRGUI extends CrashDetectorGUI,BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<GrepRGUI>> GUIS = new HashMap<String, Supplier<GrepRGUI>>();

	public default TipoGUI<GrepRGUI> tipo() {
		return TipoGUI.GREPR;
	}

}
