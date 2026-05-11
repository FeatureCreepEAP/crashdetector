package com.asbestosstar.crashdetector.gui.tipos.profiler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JFrame;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class ProfilerGUI extends JFrame implements CrashDetectorGUI {

	public static Map<String, Supplier<ProfilerGUI>> GUIS = new HashMap<>();

	protected volatile boolean profilerActivo = false;

	protected ProfilerGUI() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.PROFILER;
	}

	protected void inicializarVentana() {
		setTitle(MonitorDePID.idioma.profilerTitulo());
		setLocationByPlatform(true);
	}

	public final void iniciarProfiler() {
		if (profilerActivo)
			return;

		profilerActivo = true;
		onProfilerIniciado();
	}

	public final void detenerProfiler() {
		if (!profilerActivo)
			return;

		profilerActivo = false;
		onProfilerDetenido();
	}

	public final boolean estaProfilerActivo() {
		return profilerActivo;
	}

	public final void agregarMuestra(String hilo, StackTraceElement[] stack, long nanos) {
		if (!profilerActivo)
			return;

		onMuestraRecibida(hilo, stack, nanos);
	}

	public final void agregarLlamadaMetodo(String clase, String metodo, String descriptor, long duracionNs) {
		if (!profilerActivo)
			return;

		onLlamadaMetodo(clase, metodo, descriptor, duracionNs);
	}

	public final void limpiarDatos() {
		onLimpiarDatos();
	}

	protected abstract void onProfilerIniciado();

	protected abstract void onProfilerDetenido();

	protected abstract void onMuestraRecibida(String hilo, StackTraceElement[] stack, long nanos);

	protected abstract void onLlamadaMetodo(String clase, String metodo, String descriptor, long duracionNs);

	protected abstract void onLimpiarDatos();
}