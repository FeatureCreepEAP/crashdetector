package com.asbestosstar.crashdetector.analizador.rapido.motor;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.bajo.vectorapi.VectorAPIInit;
import com.asbestosstar.crashdetector.stream.intstream.CDIntStream;

/**
 * Fabrica para crear motores de busqueda de bytes con la mejor aceleracion
 * disponible.
 */
public final class MotoresBusqueda {

	private static final String CLASE_MOTOR_DAX = "com.asbestosstar.crashdetector.analizador.rapido.motor."
			+ "MotorBusquedaDaxSparc";

	private MotoresBusqueda() {
	}

	public static MotorBusquedaBytes crear() {
		MotorBusquedaBytes motorDax = crearMotorDax();
		MotorBusquedaBytes motorVector = crearMotorVector();

		/*
		 * Si ambos motores existen, usar DAX para bloques grandes y Vector API para
		 * bloques pequeños.
		 */
		if (motorDax != null && motorVector != null) {
			return registrar(new MotorBusquedaDaxVector(motorDax, motorVector));
		}

		if (motorDax != null) {
			return registrar(motorDax);
		}

		if (motorVector != null) {
			return registrar(motorVector);
		}

		return registrar(new MotorBusquedaEscalar());
	}

	private static MotorBusquedaBytes crearMotorDax() {
		/*
		 * Rechazar inmediatamente cualquier sistema que no sea Solaris SPARC V9. No
		 * intentar cargar la clase DAX.
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
			CrashDetectorLogger.log("No se pudo crear el motor Oracle DAX: " + e.getClass().getName() + ": "
					+ String.valueOf(e.getMessage()));

			return null;
		} catch (LinkageError e) {
			CrashDetectorLogger.log("No se pudo enlazar el motor Oracle DAX: " + e.getClass().getName() + ": "
					+ String.valueOf(e.getMessage()));

			return null;
		} catch (ClassCastException e) {
			CrashDetectorLogger
					.log("La clase del motor Oracle DAX no implementa " + MotorBusquedaBytes.class.getName());

			return null;
		}
	}

	private static MotorBusquedaBytes crearMotorVector() {
		if (!VectorAPIInit.vectorAPIDisponible()) {
			return null;
		}

		try {
			return new MotorBusquedaVectorApi();
		} catch (LinkageError e) {
			CrashDetectorLogger.log("No se pudo enlazar el motor Vector API: " + e.getClass().getName() + ": "
					+ String.valueOf(e.getMessage()));

			return null;
		} catch (RuntimeException e) {
			CrashDetectorLogger.log("No se pudo crear el motor Vector API: " + e.getClass().getName() + ": "
					+ String.valueOf(e.getMessage()));

			return null;
		}
	}

	private static MotorBusquedaBytes registrar(MotorBusquedaBytes motor) {

		CrashDetectorLogger.log("Motor de busqueda de bytes seleccionado: " + motor.nombre());

		return motor;
	}
}