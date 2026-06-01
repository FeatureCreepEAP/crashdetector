package com.asbestosstar.crashdetector.gui.tipos.migrador;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public abstract class AsistenteMigracionLegacy extends JDialog implements CrashDetectorGUI {

	private static final long serialVersionUID = 1L;

	public abstract String nombreVisible();

	public abstract boolean ejecutarMigracion();
}