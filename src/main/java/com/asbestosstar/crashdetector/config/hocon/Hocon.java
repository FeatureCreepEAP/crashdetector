package com.asbestosstar.crashdetector.config.hocon;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Punto de entrada para leer y escribir archivos HOCON (Typesafe Config).
 * Motor: configurate-hocon
 */
public class Hocon {

	private static final com.asbestosstar.crashdetector.config.hocon.Hocon.Motor motor = seleccionarMotor();

	public static com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo leer(String texto) {
		if (motor == null)
			throw new IllegalStateException("No se encontro configurate-hocon");
		return motor.leer(texto);
	}

	public static com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo crear() {
		if (motor == null)
			throw new IllegalStateException("No se encontro configurate-hocon");
		return motor.crear();
	}

	public static String escribir(com.asbestosstar.crashdetector.config.hocon.Hocon.Nodo nodo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro configurate-hocon");
		return motor.escribir(nodo);
	}

	public static String nombreMotor() {
		return motor == null ? "ninguno" : motor.nombre();
	}

	private static com.asbestosstar.crashdetector.config.hocon.Hocon.Motor seleccionarMotor() {
		try {
			Class.forName("org.spongepowered.configurate.hocon.HoconConfigurationLoader");
			return new com.asbestosstar.crashdetector.config.hocon.HoconConfigurateHocon();
		} catch (Throwable t) {
			return null;
		}
	}

	public static class Nodo {
		public Object interno;
		final Motor motorRef;
		Object padre;
		String clave;

		public Nodo(Object interno, Motor motorRef) {
			this.interno = interno;
			this.motorRef = motorRef;
		}

		public Nodo(Object interno, Motor motorRef, Object padre, String clave) {
			this.interno = interno;
			this.motorRef = motorRef;
			this.padre = padre;
			this.clave = clave;
		}

		public List<String> claves() {
			return motorRef.claves(this);
		}

		public Nodo obtener(String clave) {
			return motorRef.obtener(this, clave);
		}

		public Nodo poner(Object valor) {
			return motorRef.poner(this, valor);
		}

		public String comoCadena() {
			return motorRef.comoCadena(this);
		}

		public int comoEntero() {
			return Integer.parseInt(comoCadena());
		}

		public long comoLargo() {
			return Long.parseLong(comoCadena());
		}

		public boolean comoBooleano() {
			return Boolean.parseBoolean(comoCadena());
		}

		public double comoDouble() {
			return Double.parseDouble(comoCadena());
		}

		public String escribir() {
			return motorRef.escribir(this);
		}

		public byte[] aBytesUtf8() {
			return escribir().getBytes(StandardCharsets.UTF_8);
		}
	}

	public interface Motor {
		String nombre();

		Nodo leer(String texto);

		Nodo crear();

		String escribir(Nodo nodo);

		List<String> claves(Nodo nodo);

		Nodo obtener(Nodo nodo, String clave);

		Nodo poner(Nodo nodo, Object valor);

		String comoCadena(Nodo nodo);
	}
}