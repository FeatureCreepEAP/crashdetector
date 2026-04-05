package com.asbestosstar.crashdetector;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.nio.file.Path;

public class CrashDetectorNilLoader implements Runnable {

	static {

		// Inicializamos la carga común apuntando a la carpeta de mods.
		// Cambia CDOrigin.NILLOADER por el nombre real del enum si en tu proyecto
		// usa otro identificador.
		CargadoresComun.init(new Path[] { new File("mods/").toPath() }, CargadoresComun.CDOrigin.NILLOADER);

	}

	@Override
	public void run() {
		try {

			// Inicialización base de las transformaciones/parches.
			Transformaciones.init();

			// Obtenemos la Instrumentation interna de NilLoader por reflexión.
			Instrumentation inst = obtenerInstrumentationDeNilLoader();

			// Registramos el transformer para que NilLoader aplique los parches.
			if (inst != null) {
				inst.addTransformer(new Transformaciones());
			}

		} catch (Throwable t) {
			// Nunca romper el arranque del loader por un fallo aquí.
			// Si tienes logger propio, conviene loguearlo en vez de ignorarlo.
		}
	}

	/**
	 * Obtiene la instancia de Instrumentation retenida por NilLoader.
	 */
	private static Instrumentation obtenerInstrumentationDeNilLoader() {
		try {
			Class<?> nilAgentClass = Class.forName("nilloader.NilAgent");
			Field instrumentationField = nilAgentClass.getDeclaredField("instrumentation");
			instrumentationField.setAccessible(true);
			return (Instrumentation) instrumentationField.get(null);
		} catch (Throwable t) {
			return null;
		}
	}

}