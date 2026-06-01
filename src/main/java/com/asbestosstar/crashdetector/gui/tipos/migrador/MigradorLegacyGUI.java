package com.asbestosstar.crashdetector.gui.tipos.migrador;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class MigradorLegacyGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static Map<String, Supplier<MigradorLegacyGUI>> GUIS = new HashMap<String, Supplier<MigradorLegacyGUI>>();
	public Map<String, Supplier<AsistenteMigracionLegacy>> asistentes = new HashMap<String, Supplier<AsistenteMigracionLegacy>>();

	@Override
	public TipoGUI<MigradorLegacyGUI> tipo() {
		return TipoGUI.MIGRADOR_LEGACY;
	}

	public void registrarAsistente(String id, Supplier<AsistenteMigracionLegacy> asistente) {
		asistentes.put(id, asistente);
	}

	public void abrirAsistente(String id) {
		Supplier<AsistenteMigracionLegacy> sup = asistentes.get(id);
		if (sup == null) {
			return;
		}

		AsistenteMigracionLegacy asistente = sup.get();
		if (asistente != null) {
			asistente.init();
		}
	}
}