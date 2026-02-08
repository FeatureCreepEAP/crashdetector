package com.asbestosstar.crashdetector.gui.tipos.profiler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * GUI base del Profiler.
 *
 * Esta clase:
 * - Contiene TODA la lógica del profiler
 * - Gestiona el estado (activo / detenido)
 * - Recibe datos del backend (instrumentación / sampling)
 *
 * La apariencia se implementa en las subclases.
 */
public abstract class ProfilerGUI extends JFrame implements CrashDetectorGUI {

	public static Map<String, Supplier<ProfilerGUI>> GUIS = new HashMap<>();

	/** Indica si el profiler está actualmente activo */
	protected boolean profilerActivo = false;

	protected ProfilerGUI() {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.PROFILER;
	}

	/**
	 * Inicializa la ventana del profiler.
	 * 
	 * No tiene ventana owner: es completamente independiente.
	 */
	protected void inicializarVentana() {
		setTitle(MonitorDePID.idioma.profilerTitulo());
		setLocationByPlatform(true);
	}

	/* ==========================================================
	 *  Control del profiler
	 * ==========================================================
	 */

	/**
	 * Inicia el profiler si no está activo.
	 */
	public final void iniciarProfiler() {
		if (profilerActivo)
			return;

		profilerActivo = true;
		onProfilerIniciado();
	}

	/**
	 * Detiene el profiler si está activo.
	 */
	public final void detenerProfiler() {
		if (!profilerActivo)
			return;

		profilerActivo = false;
		onProfilerDetenido();
	}

	/**
	 * @return true si el profiler está activo
	 */
	public final boolean estaProfilerActivo() {
		return profilerActivo;
	}

	/* ==========================================================
	 *  Alimentación de datos del profiler
	 * ==========================================================
	 */

	/**
	 * Recibe una muestra (sampling) de stack.
	 *
	 * @param hilo     Nombre o id del hilo
	 * @param stack    Stack trace capturado
	 * @param nanos    Tiempo estimado (opcional)
	 */
	public final void agregarMuestra(
			String hilo,
			StackTraceElement[] stack,
			long nanos
	) {
		if (!profilerActivo)
			return;

		SwingUtilities.invokeLater(() ->
				onMuestraRecibida(hilo, stack, nanos)
		);
	}

	/**
	 * Registra una llamada instrumentada a método.
	 *
	 * @param clase       Clase completa
	 * @param metodo      Nombre del método
	 * @param descriptor  Descriptor JVM
	 * @param duracionNs  Duración estimada
	 */
	public final void agregarLlamadaMetodo(
			String clase,
			String metodo,
			String descriptor,
			long duracionNs
	) {
		if (!profilerActivo)
			return;

		SwingUtilities.invokeLater(() ->
				onLlamadaMetodo(clase, metodo, descriptor, duracionNs)
		);
	}

	/**
	 * Limpia todos los datos del profiler.
	 */
	public final void limpiarDatos() {
		SwingUtilities.invokeLater(this::onLimpiarDatos);
	}

	/* ==========================================================
	 *  Hooks para la implementación visual
	 * ==========================================================
	 */

	/**
	 * Se llama cuando el profiler se inicia.
	 */
	protected abstract void onProfilerIniciado();

	/**
	 * Se llama cuando el profiler se detiene.
	 */
	protected abstract void onProfilerDetenido();

	/**
	 * Se llama cuando se recibe una muestra de sampling.
	 */
	protected abstract void onMuestraRecibida(
			String hilo,
			StackTraceElement[] stack,
			long nanos
	);

	/**
	 * Se llama cuando se recibe una llamada instrumentada.
	 */
	protected abstract void onLlamadaMetodo(
			String clase,
			String metodo,
			String descriptor,
			long duracionNs
	);

	/**
	 * Se llama cuando se solicita limpiar los datos.
	 */
	protected abstract void onLimpiarDatos();
}
