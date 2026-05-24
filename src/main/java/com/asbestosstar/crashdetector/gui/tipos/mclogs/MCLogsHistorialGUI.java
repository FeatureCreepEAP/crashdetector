package com.asbestosstar.crashdetector.gui.tipos.mclogs;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.api_sito_registro.HistoriaMCLogs;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Base lógica para el eliminador/historial de MCLogs.
 */
public abstract class MCLogsHistorialGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {
	private static final long serialVersionUID = 1L;

	public static Map<String, Supplier<MCLogsHistorialGUI>> GUIS = new HashMap<String, Supplier<MCLogsHistorialGUI>>();

	protected Nodo historial;

	protected void cargarDatos() {
		historial = HistoriaMCLogs.cargarRaiz();
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.MCLOGS_HISTORIAL;
	}
}