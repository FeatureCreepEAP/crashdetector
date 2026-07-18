package com.asbestosstar.crashdetector.analizador.rapido.motor;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.bajo.vectorapi.VectorAPIInit;
import com.asbestosstar.crashdetector.stream.intstream.CDIntStream;

/**
 * Fabrica para crear el mejor motor de busqueda de bytes disponible.
 *
 * Prioridad:
 *
 * 1. Oracle DAX sobre Solaris SPARC V9. 2. Vector API. 3. Motor escalar.
 */
public final class MotoresBusqueda {

	private static final String CLASE_MOTOR_DAX = "com.asbestosstar.crashdetector.analizador.rapido.motor."
			+ "MotorBusquedaDaxSparc";

	private MotoresBusqueda() {
	}

	public static MotorBusquedaBytes crear() {
		MotorBusquedaBytes motor = crearMotorDax();

		if (motor != null) {
			return registrar(motor);
		}

		motor = crearMotorVector();

		if (motor != null) {
			return registrar(motor);
		}

		return registrar(new MotorBusquedaEscalar());
	}

	private static MotorBusquedaBytes crearMotorDax() {
		/*
		 * No intentar cargar ninguna clase DAX fuera de Solaris SPARC V9.
		 */
		if (!CDIntStream.esSolarisSparcV9()) {
			return null;
		}

		if (!CDIntStream.oracleDisponible()) {
			return null;
		}

		try {
			Class<?> clase = Class.forName(CLASE_MOTOR_DAX, true, MotoresBusqueda.class.getClassLoader());

			Object instancia = clase.getDeclaredConstructor().newInstance();

			return (MotorBusquedaBytes) instancia;

		} catch (ReflectiveOperationException e) {
			CrashDetectorLogger.log("No se pudo crear el motor Oracle DAX: " + describir(e));

		} catch (LinkageError e) {
			CrashDetectorLogger.log("No se pudo enlazar el motor Oracle DAX: " + describir(e));

		} catch (ClassCastException e) {
			CrashDetectorLogger.log("La clase Oracle DAX no implementa " + MotorBusquedaBytes.class.getName());
		}

		return null;
	}

	private static MotorBusquedaBytes crearMotorVector() {
		if (!VectorAPIInit.vectorAPIDisponible()) {
			return null;
		}

		try {
			return new MotorBusquedaVectorApi();

		} catch (LinkageError e) {
			CrashDetectorLogger.log("No se pudo enlazar el motor Vector API: " + describir(e));

		} catch (RuntimeException e) {
			CrashDetectorLogger.log("No se pudo crear el motor Vector API: " + describir(e));
		}

		return null;
	}

	private static MotorBusquedaBytes registrar(MotorBusquedaBytes motor) {

		CrashDetectorLogger.log("Motor de busqueda de bytes seleccionado: " + motor.nombre());

		return motor;
	}

	private static String describir(Throwable error) {
		return error.getClass().getName() + ": " + String.valueOf(error.getMessage());
	}
}