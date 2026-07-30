package com.asbestosstar.crashdetector.bajo.hw.politica;

import java.util.List;
import java.util.Locale;

import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.Entrada;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.Estado;

/**
 * Persistencia de la política. Se usan ConfigString para evitar introducir una
 * dependencia hacia una clase numérica que quizá no exista en instalaciones
 * antiguas del proyecto.
 */
public final class PoliticaHardwareConfig {

	private static final String PREFIJO = "corporativo.hardware.";
	private static final ConfigString RAM_MINIMA = ConfigString.de(PREFIJO + "ram_gb_minima", "0");
	private static final ConfigString GHZ_MINIMOS = ConfigString.de(PREFIJO + "ghz_minimos", "0");
	private static final ConfigString HILOS_MINIMOS = ConfigString.de(PREFIJO + "hilos_minimos", "0");

	private PoliticaHardwareConfig() {
	}

	public static Estado obtenerEstado(Entrada entrada) {
		if (entrada == null) {
			return Estado.SIN_REGLA;
		}
		String clave = claveEstado(entrada);
		return Estado.desdeTexto(ConfigString.de(clave, Estado.SIN_REGLA.name()).obtener());
	}

	public static void escribirEstado(Entrada entrada, Estado estado) {
		if (entrada == null) {
			return;
		}
		Estado seguro = estado == null ? Estado.SIN_REGLA : estado;
		ConfigString.de(claveEstado(entrada), Estado.SIN_REGLA.name()).escribir(seguro.name());
	}

	public static void aplicarSugerencias() {
		for (Entrada entrada : CatalogoPlataformas.todas()) {
			escribirEstado(entrada, entrada.sugerencia());
		}
	}

	public static void limpiarReglas() {
		for (Entrada entrada : CatalogoPlataformas.todas()) {
			escribirEstado(entrada, Estado.SIN_REGLA);
		}
	}

	public static boolean tieneAlgunaRegla() {
		List<Entrada> entradas = CatalogoPlataformas.todas();
		for (Entrada entrada : entradas) {
			if (obtenerEstado(entrada) != Estado.SIN_REGLA) {
				return true;
			}
		}
		return false;
	}

	public static boolean tieneAlgunaRestriccion() {
		for (Entrada entrada : CatalogoPlataformas.todas()) {
			if (obtenerEstado(entrada) == Estado.DESACONSEJADO) {
				return true;
			}
		}
		return ramMinimaGB() > 0.0 || ghzMinimos() > 0.0 || hilosMinimos() > 0;
	}

	public static double ramMinimaGB() {
		return parsearDouble(RAM_MINIMA.obtener());
	}

	public static void escribirRamMinimaGB(double valor) {
		RAM_MINIMA.escribir(formatear(Math.max(0.0, valor)));
	}

	public static double ghzMinimos() {
		return parsearDouble(GHZ_MINIMOS.obtener());
	}

	public static void escribirGhzMinimos(double valor) {
		GHZ_MINIMOS.escribir(formatear(Math.max(0.0, valor)));
	}

	public static int hilosMinimos() {
		return Math.max(0, parsearEntero(HILOS_MINIMOS.obtener()));
	}

	public static void escribirHilosMinimos(int valor) {
		HILOS_MINIMOS.escribir(Integer.toString(Math.max(0, valor)));
	}

	private static String claveEstado(Entrada entrada) {
		return PREFIJO + "regla." + entrada.tipo().clave() + "." + entrada.id();
	}

	private static double parsearDouble(String texto) {
		if (texto == null) {
			return 0.0;
		}
		try {
			return Math.max(0.0, Double.parseDouble(texto.trim().replace(',', '.')));
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}

	private static int parsearEntero(String texto) {
		if (texto == null) {
			return 0;
		}
		try {
			return Integer.parseInt(texto.trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private static String formatear(double valor) {
		if (valor == Math.rint(valor)) {
			return Long.toString((long) valor);
		}
		return String.format(Locale.ROOT, "%.2f", valor).replaceAll("0+$", "").replaceAll("\\.$", "");
	}
}
