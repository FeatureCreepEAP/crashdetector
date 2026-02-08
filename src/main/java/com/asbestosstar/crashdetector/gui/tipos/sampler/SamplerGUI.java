package com.asbestosstar.crashdetector.gui.tipos.sampler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * GUI base del Sampler.
 *
 * Contiene TODA la lógica:
 * - control de estado
 * - recepción de muestras
 *
 * La apariencia se implementa en subclases.
 */
public abstract class SamplerGUI extends JFrame implements CrashDetectorGUI {

	public static Map<String, Supplier<SamplerGUI>> GUIS = new HashMap<>();

	/** Indica si el sampler está activo */
	protected boolean samplerActivo = false;

	protected SamplerGUI() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.SAMPLER;
	}

	/**
	 * Inicializa la ventana del sampler.
	 * Ventana independiente (sin owner).
	 */
	protected void inicializarVentana() {
//		setTitle(MonitorDePID.idioma.samplerTitulo());
		setTitle("Sampler");

		setLocationByPlatform(true);
	}

	/* ==========================================================
	 * Control del sampler
	 * ========================================================== */

	public final void iniciarSampler() {
		if (samplerActivo)
			return;

		samplerActivo = true;
		onSamplerIniciado();
	}

	public final void detenerSampler() {
		if (!samplerActivo)
			return;

		samplerActivo = false;
		onSamplerDetenido();
	}

	public final boolean estaSamplerActivo() {
		return samplerActivo;
	}

	/* ==========================================================
	 * Alimentación de datos
	 * ========================================================== */

	/**
	 * Agrega una muestra de stack al sampler.
	 *
	 * @param hilo   nombre del hilo
	 * @param stack  stack trace capturado
	 */
	public final void agregarMuestra(String hilo, StackTraceElement[] stack) {
		if (!samplerActivo)
			return;

		SwingUtilities.invokeLater(() ->
				onMuestraRecibida(hilo, stack)
		);
	}

	/**
	 * Limpia los datos del sampler.
	 */
	public final void limpiarDatos() {
		SwingUtilities.invokeLater(this::onLimpiarDatos);
	}

	/* ==========================================================
	 * Hooks para la implementación visual
	 * ========================================================== */

	protected abstract void onSamplerIniciado();

	protected abstract void onSamplerDetenido();

	protected abstract void onMuestraRecibida(String hilo, StackTraceElement[] stack);

	protected abstract void onLimpiarDatos();
}
