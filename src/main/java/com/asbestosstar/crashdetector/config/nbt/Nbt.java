package com.asbestosstar.crashdetector.config.nbt;

import java.io.File;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Punto de entrada para NBT binario.
 *
 * Esta clase NO toca directamente Querz ni ninguna libreria especifica. Solo
 * selecciona un motor y delega.
 */
public class Nbt {

	private static final Motor motor = seleccionarMotor();

	public Nbt() {
	}

	public static Nodo leer(byte[] datos) {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		return motor.leer(datos);
	}

	public static Nodo leer(byte[] datos, boolean comprimidoGzip) {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		return motor.leer(datos, comprimidoGzip);
	}

	public static Nodo leerAuto(byte[] datos) {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		return motor.leerAuto(datos);
	}

	public static Nodo leerArchivo(File archivo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		return motor.leerArchivo(archivo);
	}

	public static byte[] escribirBytes(Nodo nodo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		return motor.escribirBytes(nodo);
	}

	public static byte[] escribirBytes(Nodo nodo, boolean comprimirGzip) {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		return motor.escribirBytes(nodo, comprimirGzip);
	}

	public static void escribirArchivo(Nodo nodo, File archivo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		motor.escribirArchivo(nodo, archivo);
	}

	public static void escribirArchivo(Nodo nodo, File archivo, boolean comprimirGzip) {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		motor.escribirArchivo(nodo, archivo, comprimirGzip);
	}

	public static Nodo leerSnbt(String snbt) {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		return motor.leerSnbt(snbt);
	}

	public static String escribirSnbt(Nodo nodo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		return motor.escribirSnbt(nodo);
	}

	public static Nodo crearObjeto() {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		return motor.crearObjeto();
	}

	public static Nodo crearLista() {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		return motor.crearLista();
	}

	public static Nodo desdeInterno(Object interno) {
		if (motor == null)
			throw new IllegalStateException("No se encontro motor NBT");
		return new Nodo(interno, motor);
	}

	public static String nombreMotor() {
		if (motor == null)
			return "ninguno";
		return motor.nombre();
	}

	private static Motor seleccionarMotor() {
		if (claseExiste("net.querz.nbt.io.NBTDeserializer"))
			return new NbtQuerz();

		return null;
	}

	private static boolean claseExiste(String cn) {
		try {
			Class.forName(cn, false, Nbt.class.getClassLoader());
			CrashDetectorLogger.log("NBT clase " + cn);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	/**
	 * Nodo independiente para NBT.
	 *
	 * No expone directamente clases de Querz. El campo interno se mantiene publico
	 * solo por compatibilidad con el estilo de Json.Nodo.
	 */
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

		public Nodo(Object interno, Motor motorRef, Object padre, String claveEnPadre, Integer indiceEnPadre) {
			this.interno = interno;
			this.motorRef = motorRef;
			this.padre = padre;
			this.claveEnPadre = claveEnPadre;
			this.indiceEnPadre = indiceEnPadre;
		}

		public boolean esObjeto() {
			return motorRef.esObjeto(this);
		}

		public boolean esLista() {
			return motorRef.esLista(this);
		}

		public boolean esNumero() {
			return motorRef.esNumero(this);
		}

		public boolean esCadena() {
			return motorRef.esCadena(this);
		}

		public boolean esByteArray() {
			return motorRef.esByteArray(this);
		}

		public boolean esIntArray() {
			return motorRef.esIntArray(this);
		}

		public boolean esLongArray() {
			return motorRef.esLongArray(this);
		}

		public List<String> claves() {
			return motorRef.claves(this);
		}

		public Nodo obtener(String clave) {
			return motorRef.obtener(this, clave);
		}

		public boolean contiene(String clave) {
			return motorRef.contiene(this, clave);
		}

		public boolean eliminar(String clave) {
			return motorRef.eliminar(this, clave);
		}

		public Nodo poner(String clave, String valor) {
			return obtener(clave).poner(valor);
		}

		public Nodo poner(String clave, byte valor) {
			return obtener(clave).poner(valor);
		}

		public Nodo poner(String clave, short valor) {
			return obtener(clave).poner(valor);
		}

		public Nodo poner(String clave, int valor) {
			return obtener(clave).poner(valor);
		}

		public Nodo poner(String clave, long valor) {
			return obtener(clave).poner(valor);
		}

		public Nodo poner(String clave, float valor) {
			return obtener(clave).poner(valor);
		}

		public Nodo poner(String clave, double valor) {
			return obtener(clave).poner(valor);
		}

		public Nodo poner(String clave, boolean valor) {
			return obtener(clave).poner(valor);
		}

		public Nodo poner(String clave, Nodo valor) {
			return obtener(clave).poner(valor);
		}

		public Nodo poner(String valor) {
			return motorRef.poner(this, valor);
		}

		public Nodo poner(byte valor) {
			return motorRef.poner(this, Byte.valueOf(valor));
		}

		public Nodo poner(short valor) {
			return motorRef.poner(this, Short.valueOf(valor));
		}

		public Nodo poner(int valor) {
			return motorRef.poner(this, Integer.valueOf(valor));
		}

		public Nodo poner(long valor) {
			return motorRef.poner(this, Long.valueOf(valor));
		}

		public Nodo poner(float valor) {
			return motorRef.poner(this, Float.valueOf(valor));
		}

		public Nodo poner(double valor) {
			return motorRef.poner(this, Double.valueOf(valor));
		}

		public Nodo poner(boolean valor) {
			return motorRef.poner(this, Boolean.valueOf(valor));
		}

		public Nodo poner(byte[] valor) {
			return motorRef.poner(this, valor);
		}

		public Nodo poner(int[] valor) {
			return motorRef.poner(this, valor);
		}

		public Nodo poner(long[] valor) {
			return motorRef.poner(this, valor);
		}

		public Nodo poner(Nodo valor) {
			return motorRef.poner(this, valor);
		}

		public Nodo agregar(String valor) {
			return motorRef.agregar(this, valor);
		}

		public Nodo agregar(byte valor) {
			return motorRef.agregar(this, Byte.valueOf(valor));
		}

		public Nodo agregar(short valor) {
			return motorRef.agregar(this, Short.valueOf(valor));
		}

		public Nodo agregar(int valor) {
			return motorRef.agregar(this, Integer.valueOf(valor));
		}

		public Nodo agregar(long valor) {
			return motorRef.agregar(this, Long.valueOf(valor));
		}

		public Nodo agregar(float valor) {
			return motorRef.agregar(this, Float.valueOf(valor));
		}

		public Nodo agregar(double valor) {
			return motorRef.agregar(this, Double.valueOf(valor));
		}

		public Nodo agregar(boolean valor) {
			return motorRef.agregar(this, Boolean.valueOf(valor));
		}

		public Nodo agregar(Nodo valor) {
			return motorRef.agregarNodo(this, valor);
		}

		public Nodo convertirEnObjeto() {
			return motorRef.convertirEnObjeto(this);
		}

		public Nodo convertirEnLista() {
			return motorRef.convertirEnLista(this);
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

		public byte comoByte() {
			return motorRef.comoByte(this);
		}

		public short comoShort() {
			return motorRef.comoShort(this);
		}

		public int comoEntero() {
			return motorRef.comoEntero(this);
		}

		public long comoLargo() {
			return motorRef.comoLargo(this);
		}

		public float comoFloat() {
			return motorRef.comoFloat(this);
		}

		public double comoDouble() {
			return motorRef.comoDouble(this);
		}

		public boolean comoBooleano() {
			return motorRef.comoBooleano(this);
		}

		public byte[] comoByteArray() {
			return motorRef.comoByteArray(this);
		}

		public int[] comoIntArray() {
			return motorRef.comoIntArray(this);
		}

		public long[] comoLongArray() {
			return motorRef.comoLongArray(this);
		}

		public String escribirSnbt() {
			return motorRef.escribirSnbt(this);
		}
	}

	public interface Motor {

		String nombre();

		Nodo leer(byte[] datos);

		Nodo leer(byte[] datos, boolean comprimidoGzip);

		Nodo leerAuto(byte[] datos);

		Nodo leerArchivo(File archivo);

		byte[] escribirBytes(Nodo nodo);

		byte[] escribirBytes(Nodo nodo, boolean comprimirGzip);

		void escribirArchivo(Nodo nodo, File archivo);

		void escribirArchivo(Nodo nodo, File archivo, boolean comprimirGzip);

		Nodo leerSnbt(String snbt);

		String escribirSnbt(Nodo nodo);

		Nodo crearObjeto();

		Nodo crearLista();

		boolean esObjeto(Nodo nodo);

		boolean esLista(Nodo nodo);

		boolean esNumero(Nodo nodo);

		boolean esCadena(Nodo nodo);

		boolean esByteArray(Nodo nodo);

		boolean esIntArray(Nodo nodo);

		boolean esLongArray(Nodo nodo);

		List<String> claves(Nodo nodo);

		Nodo obtener(Nodo actual, String clave);

		boolean contiene(Nodo actual, String clave);

		boolean eliminar(Nodo actual, String clave);

		Nodo poner(Nodo actual, Object valor);

		Nodo agregar(Nodo actual, Object valor);

		Nodo agregarNodo(Nodo actual, Nodo valor);

		Nodo convertirEnObjeto(Nodo actual);

		Nodo convertirEnLista(Nodo actual);

		int tamano(Nodo nodo);

		Nodo en(Nodo nodo, int indice);

		String comoCadena(Nodo nodo);

		byte comoByte(Nodo nodo);

		short comoShort(Nodo nodo);

		int comoEntero(Nodo nodo);

		long comoLargo(Nodo nodo);

		float comoFloat(Nodo nodo);

		double comoDouble(Nodo nodo);

		boolean comoBooleano(Nodo nodo);

		byte[] comoByteArray(Nodo nodo);

		int[] comoIntArray(Nodo nodo);

		long[] comoLongArray(Nodo nodo);
	}
}