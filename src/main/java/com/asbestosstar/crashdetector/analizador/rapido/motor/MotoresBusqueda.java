package com.asbestosstar.crashdetector.analizador.rapido.motor;

import com.asbestosstar.crashdetector.bajo.vectorapi.VectorAPIInit;
import com.asbestosstar.crashdetector.stream.intstream.CDIntStream;

/**
 * Fábrica para crear motores de búsqueda de bytes con la mejor aceleración
 * disponible.
 */
public final class MotoresBusqueda {

	private MotoresBusqueda() {
	}

	public static MotorBusquedaBytes crear() {
		if (CDIntStream.oracleDisponible()) {
			// MotorBusquedaDaxSparc será implementado cuando DAX sea necesario.
			// Por ahora volvemos a escalar si no existe la clase específica.
			try {
				return (MotorBusquedaBytes) Class
						.forName("com.asbestosstar.crashdetector.analizador.rapido.motor.MotorBusquedaDaxSparc")
						.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				// Ignorar y seguir al siguiente motor
			}
		}

		if (VectorAPIInit.vectorAPIDisponible()) {
			try {
				return (MotorBusquedaBytes) Class
						.forName("com.asbestosstar.crashdetector.analizador.rapido.motor.MotorBusquedaVectorApi")
						.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				// Ignorar y seguir al siguiente motor
			}
		}

		return new MotorBusquedaEscalar();
	}
}
