package com.asbestosstar.crashdetector.app.lanzermod.tlauncher;

/**
 * Transformador de bytes ejecutado antes de Mixin.
 *
 * Inserta una llamada al principio de TLauncher.main(String[]). El campo
 * marcador hace que la transformación sea idempotente si el cargador solicita
 * los bytes de la misma clase más de una vez.
 */
public final class CrashDetectorByteCoremod
//implements ICoremodTransformer
{

	// @Override
	public byte[] transform(String nombreClase, byte[] bytesClase) {
		return bytesClase;
	}

}
