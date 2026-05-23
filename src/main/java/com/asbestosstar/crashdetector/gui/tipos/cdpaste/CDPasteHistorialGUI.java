package com.asbestosstar.crashdetector.gui.tipos.cdpaste;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.api_sito_registro.HistoriaCDPaste;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Base lógica para el historial y eliminador de CDPaste.
 */
public abstract class CDPasteHistorialGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {
	private static final long serialVersionUID = 1L;

	public static Map<String, Supplier<CDPasteHistorialGUI>> GUIS =
			new HashMap<String, Supplier<CDPasteHistorialGUI>>();

	protected Nodo historial;

	protected void cargarDatos() {
		historial = HistoriaCDPaste.cargarRaiz();
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.CDPASTE_HISTORIAL;
	}
}