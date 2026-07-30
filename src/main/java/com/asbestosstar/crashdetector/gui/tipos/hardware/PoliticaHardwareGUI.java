package com.asbestosstar.crashdetector.gui.tipos.hardware;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.bajo.hw.politica.CatalogoPlataformas;
import com.asbestosstar.crashdetector.bajo.hw.politica.DetectorHardwareLigero;
import com.asbestosstar.crashdetector.bajo.hw.politica.DetectorHardwareLigero.InfoLocal;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.Entrada;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.Estado;
import com.asbestosstar.crashdetector.bajo.hw.politica.PoliticaHardwareConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Funcionalidad común para las interfaces de política corporativa de hardware.
 *
 * Las clases concretas solamente definen la apariencia y los componentes Swing.
 * La lectura, edición temporal y persistencia de la política permanecen aquí
 * para que puedan existir otras apariencias además de VAllure.
 */
public abstract class PoliticaHardwareGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	/**
	 * Apariencias registradas para este tipo de GUI.
	 */
	public static final Map<String, Supplier<PoliticaHardwareGUI>> GUIS = new HashMap<String, Supplier<PoliticaHardwareGUI>>();

	/**
	 * Copia editable de la política. No se persiste hasta que la GUI llama a
	 * guardarModelo(...).
	 */
	protected final Map<String, Estado> estadosTemporales = new LinkedHashMap<String, Estado>();

	@Override
	public TipoGUI tipo() {
		return TipoGUI.POLITICA_HARDWARE;
	}

	/**
	 * Carga la política persistida en el modelo temporal de la ventana.
	 */
	protected final void cargarEstadosTemporales() {
		estadosTemporales.clear();
		for (Entrada entrada : CatalogoPlataformas.todas()) {
			estadosTemporales.put(clave(entrada), PoliticaHardwareConfig.obtenerEstado(entrada));
		}
	}

	/**
	 * Copia las sugerencias incorporadas al modelo temporal. No guarda todavía.
	 */
	protected final void aplicarSugerenciasAlModelo() {
		for (Entrada entrada : CatalogoPlataformas.todas()) {
			estadosTemporales.put(clave(entrada), entrada.sugerencia());
		}
	}

	/**
	 * Elimina todas las reglas del modelo temporal. No guarda todavía.
	 */
	protected final void limpiarModelo() {
		for (Entrada entrada : CatalogoPlataformas.todas()) {
			estadosTemporales.put(clave(entrada), Estado.SIN_REGLA);
		}
	}

	/**
	 * Persiste la política y los requisitos mínimos opcionales.
	 */
	protected final void guardarModelo(double ramGB, double ghz, int hilos) {
		for (Entrada entrada : CatalogoPlataformas.todas()) {
			PoliticaHardwareConfig.escribirEstado(entrada, estadoTemporal(entrada));
		}
		PoliticaHardwareConfig.escribirRamMinimaGB(ramGB);
		PoliticaHardwareConfig.escribirGhzMinimos(ghz);
		PoliticaHardwareConfig.escribirHilosMinimos(hilos);
	}

	protected final double ramMinimaConfigurada() {
		return PoliticaHardwareConfig.ramMinimaGB();
	}

	protected final double ghzMinimosConfigurados() {
		return PoliticaHardwareConfig.ghzMinimos();
	}

	protected final int hilosMinimosConfigurados() {
		return PoliticaHardwareConfig.hilosMinimos();
	}

	protected final Estado estadoTemporal(Entrada entrada) {
		Estado estado = estadosTemporales.get(clave(entrada));
		return estado == null ? Estado.SIN_REGLA : estado;
	}

	protected final String clave(Entrada entrada) {
		return entrada.tipo().clave() + ":" + entrada.id();
	}

	/**
	 * Construye la descripción del equipo local sin usar bibliotecas nativas.
	 */
	protected final String construirTextoDetectado() {
		InfoLocal info = DetectorHardwareLigero.detectar();
		String no = MonitorDePID.idioma.politicaHardwareNoDetectado();
		String cpu = info.cpu().isEmpty() ? no : info.cpu();
		String sistema = info.detalleSistemaOperativo().isEmpty() ? no : info.detalleSistemaOperativo();
		String ram = info.ramGB() > 0 ? formatearNumero(info.ramGB()) + " GB" : no;
		String ghz = info.ghz() > 0 ? formatearNumero(info.ghz()) + " GHz" : no;
		return MonitorDePID.idioma.politicaHardwareDetectorActual(sistema, cpu, info.arquitectura(), ram, ghz,
				Integer.toString(info.hilos()));
	}

	protected static String formatearNumero(double valor) {
		String texto = String.format(java.util.Locale.ROOT, "%.2f", valor);
		while (texto.endsWith("0")) {
			texto = texto.substring(0, texto.length() - 1);
		}
		if (texto.endsWith(".")) {
			texto = texto.substring(0, texto.length() - 1);
		}
		return texto;
	}
}
