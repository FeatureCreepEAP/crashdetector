package com.asbestosstar.crashdetector.analizador.rapido.motor;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

/**
 * Motor hibrido que usa Vector API para ventanas pequeñas y Oracle DAX para
 * ventanas suficientemente grandes.
 *
 * DAX tiene un coste de preparacion mayor, por lo que no suele ser conveniente
 * para buffers pequeños.
 */
public final class MotorBusquedaDaxVector implements MotorBusquedaBytes {

	private static final int UMBRAL_DAX_PREDETERMINADO = 262_144;

	private final MotorBusquedaBytes motorDax;
	private final MotorBusquedaBytes motorVector;
	private final int umbralDax;

	private volatile boolean daxDesactivado;

	public MotorBusquedaDaxVector(MotorBusquedaBytes motorDax, MotorBusquedaBytes motorVector) {

		if (motorDax == null) {
			throw new NullPointerException("motorDax");
		}

		if (motorVector == null) {
			throw new NullPointerException("motorVector");
		}

		this.motorDax = motorDax;
		this.motorVector = motorVector;

		this.umbralDax = Math.max(50_000,
				Integer.getInteger("crashdetector.dax.umbral", UMBRAL_DAX_PREDETERMINADO).intValue());
	}

	@Override
	public int buscar(byte[] datos, int inicio, int fin, byte valor, int[] posiciones, int maxPosiciones) {

		int longitud = fin - inicio;

		if (!daxDesactivado && longitud >= umbralDax) {
			try {
				return motorDax.buscar(datos, inicio, fin, valor, posiciones, maxPosiciones);
			} catch (LinkageError e) {
				desactivarDax(e);
			} catch (RuntimeException e) {
				desactivarDax(e);
			}
		}

		return motorVector.buscar(datos, inicio, fin, valor, posiciones, maxPosiciones);
	}

	private void desactivarDax(Throwable causa) {
		if (!daxDesactivado) {
			daxDesactivado = true;

			CrashDetectorLogger.log("Motor DAX desactivado; se usara " + motorVector.nombre() + ". Causa: "
					+ causa.getClass().getName() + ": " + String.valueOf(causa.getMessage()));
		}
	}

	@Override
	public String nombre() {
		return "dax-vector-api" + "[umbral-dax=" + umbralDax + "]";
	}
}