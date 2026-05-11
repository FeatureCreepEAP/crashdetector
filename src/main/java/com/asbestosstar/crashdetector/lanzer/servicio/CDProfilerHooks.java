package com.asbestosstar.crashdetector.lanzer.servicio;

import com.asbestosstar.crashdetector.gui.tipos.profiler.ProfilerGUI;

public final class CDProfilerHooks {
	static volatile ProfilerGUI guiActiva;

	private CDProfilerHooks() {
	}

	public static void registrarLlamada(String claseInterna, String metodo, String descriptor, long duracionNs) {
		ProfilerGUI gui = guiActiva;
		if (gui == null)
			return;

		String clase = (claseInterna == null) ? "?" : claseInterna.replace('/', '.');
		gui.agregarLlamadaMetodo(clase, metodo, descriptor, duracionNs);
	}
}