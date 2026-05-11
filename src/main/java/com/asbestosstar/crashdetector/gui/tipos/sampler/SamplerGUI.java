package com.asbestosstar.crashdetector.gui.tipos.sampler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JFrame;

import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class SamplerGUI extends JFrame implements CrashDetectorGUI {

	public static Map<String, Supplier<SamplerGUI>> GUIS = new HashMap<>();

	protected volatile boolean samplerActivo = false;

	protected SamplerGUI() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.SAMPLER;
	}

	protected void inicializarVentana() {
		setTitle("Sampler");
		setLocationByPlatform(true);
	}

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

	public final void agregarMuestra(String metodo, StackTraceElement[] stack) {
		agregarMuestra(metodo, stack, 1L);
	}

	public final void agregarMuestra(String metodo, StackTraceElement[] stack, long nanos) {
		if (!samplerActivo)
			return;

		onMuestraRecibida(metodo, stack, nanos);
	}

	public final void limpiarDatos() {
		onLimpiarDatos();
	}

	protected abstract void onSamplerIniciado();

	protected abstract void onSamplerDetenido();

	protected abstract void onMuestraRecibida(String metodo, StackTraceElement[] stack, long nanos);

	protected abstract void onLimpiarDatos();
}