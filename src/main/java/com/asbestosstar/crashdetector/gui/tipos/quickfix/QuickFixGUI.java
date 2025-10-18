package com.asbestosstar.crashdetector.gui.tipos.quickfix;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class QuickFixGUI implements CrashDetectorGUI {

	public static Map<String, Supplier<QuickFixGUI>> GUIS = new HashMap<String, Supplier<QuickFixGUI>>();

	public TipoGUI<QuickFixGUI> tipo() {
		return TipoGUI.QUICKFIX;
	}
}
