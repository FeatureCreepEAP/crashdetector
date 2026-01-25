package com.asbestosstar.crashdetector.gui.tipos.aplic;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class ActaDeProteccionDelIdiomaCulturalDePyongyangGUI extends JDialog implements CrashDetectorGUI {

	public static Map<String, Supplier<ActaDeProteccionDelIdiomaCulturalDePyongyangGUI>> GUIS = new HashMap<String, Supplier<ActaDeProteccionDelIdiomaCulturalDePyongyangGUI>>();

	@Override
	public TipoGUI tipo() {
		return TipoGUI.APLIC;
	}

}