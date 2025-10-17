package com.asbestosstar.crashdetector.gui.tipos.lectador;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public interface LectadorDeConsolasGUI extends CrashDetectorGUI,BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<LectadorDeConsolasGUI>> GUIS = new HashMap<String, Supplier<LectadorDeConsolasGUI>>();

	public default TipoGUI<LectadorDeConsolasGUI> tipo() {
		return TipoGUI.LECTADOR_DE_CONSOLAS;
	}
}
