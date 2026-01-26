package com.asbestosstar.crashdetector.gui.tipos.consola;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JFrame;

import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * GUI abstracta de consola de desarrollador.
 */
public abstract class ConsolaDesarrolladorGUI extends JFrame implements CrashDetectorGUI {

	public static Map<String, Supplier<ConsolaDesarrolladorGUI>> GUIS = new HashMap<String, Supplier<ConsolaDesarrolladorGUI>>();

	/**
	 * Devuelve true si hay consentimiento para compartir logs.
	 */
	public boolean tieneConsentimiento() {
		return com.asbestosstar.crashdetector.ConfigMundial.obtenerInstancia().obtenerConsentimientoLFPDPPP();
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.CONSOLA_DESARROLLADOR;
	}

	/**
	 * Añade una línea de log.
	 */
	public abstract void agregarLinea(String linea);

	public static void copiar(String texto) {
		try {
			java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
					.setContents(new java.awt.datatransfer.StringSelection(texto), null);
		} catch (Throwable ignored) {
		}
	}

}
