package com.asbestosstar.crashdetector.gui.tipos.historia;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public interface HistoriaDeModsGUI extends CrashDetectorGUI,BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<HistoriaDeModsGUI>> GUIS = new HashMap<String, Supplier<HistoriaDeModsGUI>>();

	public default TipoGUI<HistoriaDeModsGUI> tipo() {
		return TipoGUI.HISTORIA_DE_MODS;
	}
}
