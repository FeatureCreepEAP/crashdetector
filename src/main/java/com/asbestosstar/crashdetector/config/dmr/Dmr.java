package com.asbestosstar.crashdetector.config.dmr;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Punto de entrada para leer y escribir DMR. Usa solamente JBoss DMR como
 * motor.
 */
public class Dmr {

	private static final Motor motor = seleccionarMotor();

	public Dmr() {
	}

	public static Json.Nodo leer(String textoDmr) {
		if (motor == null)
			throw new IllegalStateException("No se encontro JBoss DMR");
		return motor.leer(textoDmr);
	}

	public static Json.Nodo leerJson(String json) {
		if (motor == null)
			throw new IllegalStateException("No se encontro JBoss DMR");
		return motor.leerJson(json);
	}

	public static Json.Nodo leerBytes(byte[] bytes) {
		if (motor == null)
			throw new IllegalStateException("No se encontro JBoss DMR");
		return motor.leerBytes(bytes);
	}

	public static Json.Nodo crearObjeto() {
		if (motor == null)
			throw new IllegalStateException("No se encontro JBoss DMR");
		return motor.crearObjeto();
	}

	public static String escribir(Json.Nodo nodo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro JBoss DMR");
		return motor.escribir(nodo);
	}

	public static String escribirJson(Json.Nodo nodo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro JBoss DMR");
		return motor.escribirJson(nodo);
	}

	public static byte[] escribirBytes(Json.Nodo nodo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro JBoss DMR");
		return motor.escribirBytes(nodo);
	}

	public static String nombreMotor() {
		if (motor == null)
			return "ninguno";
		return motor.nombre();
	}

	private static Motor seleccionarMotor() {
		if (claseExiste("org.jboss.dmr.ModelNode"))
			return new DmrJBoss();
		return null;
	}

	private static boolean claseExiste(String cn) {
		try {
			Class.forName(cn, false, Dmr.class.getClassLoader());
			CrashDetectorLogger.log("DMR clase " + cn);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public interface Motor extends Json.Motor {

		Json.Nodo leerJson(String json);

		Json.Nodo leerBytes(byte[] bytes);

		String escribirJson(Json.Nodo nodo);

		byte[] escribirBytes(Json.Nodo nodo);
	}
}