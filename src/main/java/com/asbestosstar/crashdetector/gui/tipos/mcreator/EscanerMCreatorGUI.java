package com.asbestosstar.crashdetector.gui.tipos.mcreator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public interface EscanerMCreatorGUI extends CrashDetectorGUI,BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<EscanerMCreatorGUI>> GUIS = new HashMap<String, Supplier<EscanerMCreatorGUI>>();

	public default TipoGUI<EscanerMCreatorGUI> tipo() {
		return TipoGUI.ESCANER_MCREATOR;
	}
}
