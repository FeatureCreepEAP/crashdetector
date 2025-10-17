package com.asbestosstar.crashdetector.gui.tipos.historia;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JFrame;

import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class HistoriaDeModsGUI extends JFrame implements CrashDetectorGUI,BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<HistoriaDeModsGUI>> GUIS = new HashMap<String, Supplier<HistoriaDeModsGUI>>();

	public TipoGUI<HistoriaDeModsGUI> tipo() {
		return TipoGUI.HISTORIA_DE_MODS;
	}
}
