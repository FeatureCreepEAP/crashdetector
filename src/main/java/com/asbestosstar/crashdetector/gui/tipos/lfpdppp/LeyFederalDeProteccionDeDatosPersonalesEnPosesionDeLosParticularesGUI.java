package com.asbestosstar.crashdetector.gui.tipos.lfpdppp;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.ConfigMunidial;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.corpo.CorpoBase;

/**
 * GUI base para consentimiento de la LFPDPPP. Usado como popup por otras
 * interfaces.
 */
public abstract class LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI extends JDialog
		implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI>> GUIS = new HashMap<String, Supplier<LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI>>();

	/**
	 * Devuelve true si el usuario ya dio consentimiento permanente.
	 */
	public static boolean tieneConsentimiento() {
		return ConfigMunidial.obtenerInstancia().obtenerConsentimientoLFPDPPP();
	}

	/**
	 * Guarda el consentimiento permanente.
	 */
	protected void guardarConsentimiento() {
		ConfigMunidial.obtenerInstancia().guardarConsentimientoLFPDPPP(true);
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.LFPDPPP;
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
