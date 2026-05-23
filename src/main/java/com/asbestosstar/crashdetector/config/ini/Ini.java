package com.asbestosstar.crashdetector.config.ini;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Punto de entrada para leer y escribir archivos INI. Detecta Apache Commons
 * Configuration o ini4j.
 */
public class Ini {

	private static final Motor motor = seleccionarMotor();

	public static Nodo leer(String texto) {
		if (motor == null)
			throw new IllegalStateException("No se encontro Apache Commons Configuration ni ini4j");
		return motor.leer(texto);
	}

	public static Nodo crear() {
		if (motor == null)
			throw new IllegalStateException("No se encontro Apache Commons Configuration ni ini4j");
		return motor.crear();
	}

	public static String escribir(Nodo nodo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro Apache Commons Configuration ni ini4j");
		return motor.escribir(nodo);
	}

	public static String nombreMotor() {
		return motor == null ? "ninguno" : motor.nombre();
	}

	private static Motor seleccionarMotor() {
		if (claseExiste("org.apache.commons.configuration2.INIConfiguration"))
			return new IniApacheCommonsConfig();

		if (claseExiste("org.ini4j.Ini"))
			return new IniIni4j();

		return null;
	}

	private static boolean claseExiste(String cn) {
		try {
			Class.forName(cn, false, Ini.class.getClassLoader());
			CrashDetectorLogger.log("INI clase " + cn);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public static class Nodo {
		public Object interno;
		final Motor motorRef;

		Object padre;
		String seccion;
		String clave;

		public Nodo(Object interno, Motor motorRef) {
			this.interno = interno;
			this.motorRef = motorRef;
		}

		public Nodo(Object interno, Motor motorRef, Object padre, String seccion, String clave) {
			this.interno = interno;
			this.motorRef = motorRef;
			this.padre = padre;
			this.seccion = seccion;
			this.clave = clave;
		}

		public List<String> secciones() {
			return motorRef.secciones(this);
		}

		public Nodo obtenerSeccion(String nombre) {
			return motorRef.obtenerSeccion(this, nombre);
		}

		public List<String> claves() {
			return motorRef.claves(this);
		}

		public Nodo obtener(String clave) {
			return motorRef.obtener(this, clave);
		}

		public Nodo poner(String valor) {
			return motorRef.poner(this, valor);
		}

		public boolean eliminar(String clave) {
			return motorRef.eliminar(this, clave);
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

		List<String> secciones(Nodo raiz);

		Nodo obtenerSeccion(Nodo raiz, String nombre);

		List<String> claves(Nodo seccion);

		Nodo obtener(Nodo seccion, String clave);

		Nodo poner(Nodo actual, String valor);

		boolean eliminar(Nodo actual, String clave);

		String comoCadena(Nodo nodo);
	}
}