package com.asbestosstar.crashdetector.config.json5;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.config.json.Json;

/**
 * Punto de entrada para JSON5.
 */
public class Json5 {

	private static final Motor motor = seleccionarMotor();

	public static Json.Nodo leer(String json5) {
		if (motor == null)
			throw new IllegalStateException("No se encontro Json5-Java");
		return motor.leer(json5);
	}

	public static Json.Nodo crearObjeto() {
		if (motor == null)
			throw new IllegalStateException("No se encontro Json5-Java");
		return motor.crearObjeto();
	}

	public static String escribir(Json.Nodo nodo) {
		if (motor == null)
			throw new IllegalStateException("No se encontro Json5-Java");
		return motor.escribir(nodo);
	}

	public static String nombreMotor() {
		if (motor == null)
			return "ninguno";
		return motor.nombre();
	}

	private static Motor seleccionarMotor() {
		if (claseExiste("de.marhali.json5.stream.Json5Parser"))
			return new Json5Motor();
		return null;
	}

	private static boolean claseExiste(String cn) {
		try {
			Class.forName(cn, false, Json5.class.getClassLoader());
			CrashDetectorLogger.log("JSON5 clase " + cn);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public interface Motor {
		String nombre();

		Json.Nodo leer(String json5);

		Json.Nodo crearObjeto();

		String escribir(Json.Nodo nodo);
	}
}