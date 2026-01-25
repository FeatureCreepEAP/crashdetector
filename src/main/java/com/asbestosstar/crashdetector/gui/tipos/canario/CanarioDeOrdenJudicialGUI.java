package com.asbestosstar.crashdetector.gui.tipos.canario;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.canario.CanarioDeOrdenJudicial;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.corpo.CorpoBase;

/**
 * GUI base para búsqueda de canarios de orden judicial.
 */
public abstract class CanarioDeOrdenJudicialGUI extends JDialog
		implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<CanarioDeOrdenJudicialGUI>> GUIS = new HashMap<String, Supplier<CanarioDeOrdenJudicialGUI>>();

	private static final long serialVersionUID = 1L;

	/**
	 * Devuelve mapa nombre del servicio → estado seguro.
	 */
	protected static Map<String, Boolean> obtenerEstados() {

		Map<String, Boolean> mapa = new LinkedHashMap<>();

		for (CanarioDeOrdenJudicial c : CanarioDeOrdenJudicial.canarios) {
			try {
				mapa.put(c.nombre_de_servicio(), c.seguro());
			} catch (Throwable t) {
				mapa.put(c.nombre_de_servicio(), false);
			}
		}

		return mapa;
	}

	/**
	 * Cuenta cuántos servicios están inseguros.
	 */
	protected static int contarInseguros() {
		int i = 0;
		for (Boolean b : obtenerEstados().values()) {
			if (!b) {
				i++;
			}
		}
		return i;
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.CANARIO;
	}

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
	}

	/**
	 * Aplica colores y estilos visuales.
	 */
	public abstract void aplicarApariencia();
}
