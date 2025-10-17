package com.asbestosstar.crashdetector.gui.tipos.quickfix;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public interface TodosQuickFixesGUI extends CrashDetectorGUI {

	public static Map<String, Supplier<TodosQuickFixesGUI>> GUIS = new HashMap<String, Supplier<TodosQuickFixesGUI>>();

	public default TipoGUI<TodosQuickFixesGUI> tipo() {
		return TipoGUI.TODOS_QUICKFIXES;
	}
}
