package com.asbestosstar.crashdetector.json;

import java.nio.charset.StandardCharsets;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Punto de entrada para leer y escribir datos tipo JSON Detecta Gson o JBoss
 * DMR por clases disponibles expone una API estilo ModelNode con nombres en
 * español
 */
public class Json {

	private static final Motor motor = seleccionarMotor();

	public Json() {
	}

	public static Nodo leer(String json) {
		if (motor == null)
			throw new IllegalStateException("No se encontro Gson ni JBoss DMR");
		return motor.leer(json);
	}

	public static Nodo crearObjeto() {
		if (motor == null)
			throw new IllegalStateException("No se encontro Gson ni JBoss DMR");
		return motor.crearObjeto();
	}

	public static String escribir(Nodo nodo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro Gson ni JBoss DMR");
		return motor.escribir(nodo);
	}

	public static String nombreMotor() {
		if (motor == null)
			return "ninguno";
		return motor.nombre();
	}

	private static Motor seleccionarMotor() {
		if (claseExiste("com.google.gson.JsonElement"))
			return new JsonGson();
		if (claseExiste("org.jboss.dmr.ModelNode"))
			return new JsonDMR();
		return null;
	}

	private static boolean claseExiste(String cn) {
		try {
			Class.forName(cn, false, Json.class.getClassLoader());
			CrashDetectorLogger.log("JSON clase " + cn);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	/**
	 * Nodo inspirado en ModelNode con nombres en español Puede representar objeto,
	 * arreglo o valor
	 */
	public static class Nodo {
		public Object interno;
		final Motor motorRef;

		// contexto para operaciones del tipo obtener y agregar
		Object padre; // objeto padre o arreglo padre
		String claveEnPadre; // clave si el padre es objeto
		Integer indiceEnPadre; // indice si el padre es arreglo

		public Nodo(Object interno, Motor motorRef) {
			this.interno = interno;
			this.motorRef = motorRef;
		}

		// constructor con contexto
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

		// objeto
		public Nodo obtener(String nombre) {
			return motorRef.obtener(this, nombre);
		}

		// poner sobre el nodo actual
		public Nodo poner(String valor) {
			return motorRef.poner(this, valor);
		}

		public Nodo poner(int valor) {
			return motorRef.poner(this, Integer.valueOf(valor));
		}

		public Nodo poner(long valor) {
			return motorRef.poner(this, Long.valueOf(valor));
		}

		public Nodo poner(boolean valor) {
			return motorRef.poner(this, Boolean.valueOf(valor));
		}

		public Nodo poner(double valor) {
			return motorRef.poner(this, Double.valueOf(valor));
		}

		public Nodo poner(Nodo valorNodo) {
			return motorRef.poner(this, valorNodo);
		}

		// arreglo sobre el nodo actual
		public Nodo agregar(String valor) {
			return motorRef.agregar(this, valor);
		}

		public Nodo agregar(int valor) {
			return motorRef.agregar(this, Integer.valueOf(valor));
		}

		public Nodo agregar(long valor) {
			return motorRef.agregar(this, Long.valueOf(valor));
		}

		public Nodo agregar(boolean valor) {
			return motorRef.agregar(this, Boolean.valueOf(valor));
		}

		public Nodo agregar(double valor) {
			return motorRef.agregar(this, Double.valueOf(valor));
		}

		public Nodo agregar(Nodo nodo) {
			return motorRef.agregarNodo(this, nodo);
		}

		public int tamano() {
			return motorRef.tamano(this);
		}

		public Nodo en(int indice) {
			return motorRef.en(this, indice);
		}

		// conversiones
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

		// serializacion del nodo actual
		public String escribir() {
			return motorRef.escribir(this);
		}

		public byte[] aBytesUtf8() {
			return escribir().getBytes(StandardCharsets.UTF_8);
		}
	}

	/**
	 * Contrato de motores
	 */
	public interface Motor {
		String nombre();

		Nodo leer(String json);

		Nodo crearObjeto();

		String escribir(Nodo nodo);

		boolean esObjeto(Nodo nodo);

		boolean esArreglo(Nodo nodo);

		Nodo obtener(Nodo actual, String nombre);

		// poner actua sobre el nodo actual
		Nodo poner(Nodo actual, Object valor);

		// agregar actua sobre el nodo actual
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
