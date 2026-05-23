package com.asbestosstar.crashdetector.config.toml;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

public class Toml {

	private static final Motor motor = seleccionarMotor();

	public static Nodo leer(String toml) {
		if (motor == null)
			throw new IllegalStateException("No se encontro Config-TOML");
		return motor.leer(toml);
	}

	public static Nodo crearObjeto() {
		if (motor == null)
			throw new IllegalStateException("No se encontro Config-TOML");
		return motor.crearObjeto();
	}

	public static Nodo crearArreglo() {
		if (motor == null)
			throw new IllegalStateException("No se encontro Config-TOML");
		return motor.crearArreglo();
	}

	public static String escribir(Nodo nodo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro Config-TOML");
		return motor.escribir(nodo);
	}

	public static String nombreMotor() {
		if (motor == null)
			return "ninguno";
		return motor.nombre();
	}

	private static Motor seleccionarMotor() {
		if (claseExiste("com.electronwill.nightconfig.toml.TomlParser"))
			return new TomlElectronWill();
		return null;
	}

	private static boolean claseExiste(String cn) {
		try {
			Class.forName(cn, false, Toml.class.getClassLoader());
			CrashDetectorLogger.log("TOML clase " + cn);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public static class Nodo {
		public Object interno;
		final Motor motorRef;

		Object padre;
		String claveEnPadre;
		Integer indiceEnPadre;

		public Nodo(Object interno, Motor motorRef) {
			this.interno = interno;
			this.motorRef = motorRef;
		}

		public Nodo(Object interno, Motor motorRef, Object padre, String clave, Integer indice) {
			this.interno = interno;
			this.motorRef = motorRef;
			this.padre = padre;
			this.claveEnPadre = clave;
			this.indiceEnPadre = indice;
		}

		public boolean esObjeto() {
			return motorRef.esObjeto(this);
		}

		public boolean esArreglo() {
			return motorRef.esArreglo(this);
		}

		public List<String> claves() {
			return motorRef.claves(this);
		}

		public Nodo obtener(String nombre) {
			return motorRef.obtener(this, nombre);
		}

		public boolean eliminar(String nombre) {
			return motorRef.eliminar(this, nombre);
		}

		public Nodo poner(Object valor) {
			return motorRef.poner(this, valor);
		}

		public Nodo agregar(Object valor) {
			return motorRef.agregar(this, valor);
		}

		public Nodo agregarNodo(Nodo nodo) {
			return motorRef.agregarNodo(this, nodo);
		}

		public int tamano() {
			return motorRef.tamano(this);
		}

		public Nodo en(int indice) {
			return motorRef.en(this, indice);
		}

		public String comoCadena() {
			return motorRef.comoCadena(this);
		}

		public int comoEntero() {
			return motorRef.comoEntero(this);
		}

		public long comoLargo() {
			return motorRef.comoLargo(this);
		}

		public boolean comoBooleano() {
			return motorRef.comoBooleano(this);
		}

		public double comoDouble() {
			return motorRef.comoDouble(this);
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

		Nodo leer(String toml);

		Nodo crearObjeto();

		Nodo crearArreglo();

		String escribir(Nodo nodo);

		boolean esObjeto(Nodo nodo);

		boolean esArreglo(Nodo nodo);

		List<String> claves(Nodo objeto);

		Nodo obtener(Nodo actual, String nombre);

		boolean eliminar(Nodo actual, String nombre);

		Nodo poner(Nodo actual, Object valor);

		Nodo agregar(Nodo actual, Object valor);

		Nodo agregarNodo(Nodo actual, Nodo valorNodo);

		int tamano(Nodo arreglo);

		Nodo en(Nodo arreglo, int indice);

		String comoCadena(Nodo nodo);

		int comoEntero(Nodo nodo);

		long comoLargo(Nodo nodo);

		boolean comoBooleano(Nodo nodo);

		double comoDouble(Nodo nodo);
	}
}