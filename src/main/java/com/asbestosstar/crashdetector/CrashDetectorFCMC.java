package com.asbestosstar.crashdetector;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.nio.file.Path;

import featurecreep.loader.FCInstrumentation;
import featurecreep.loader.FCLoaderBasic;

public class CrashDetectorFCMC {

	public static void premain(String args, Instrumentation instrument) {
		// Buscar para archivos de mods si es FC
		boolean es_fc = true;
		if (clase_existe("featurecreep.loader.FCLoaderBasic")) {
			if (clase_existe("dangerzone.BaseMod")) {
				Statics.APP = App.DANGERZONE;
			} // TODO para otras applicaciones

			Transformaciones.init();
			FCLoaderBasic fc = null;
			if (instrument instanceof FCInstrumentation) {
				FCInstrumentation fcinstrument = (FCInstrumentation) instrument;
				fc = fcinstrument.loader;
			} else if (clase_existe("featurecreep.api.GameInjections")) {
				try {
					Field field = Class.forName("featurecreep.api.GameInjections").getDeclaredField("loader");
					fc = (FCLoaderBasic) field.get(null);

				} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException
						| IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// TODO mas

			if (fc != null) {
				for (Path path : fc.getCombindedModulePKZipLocations()) {
					Statics.carpetas_de_mods.add(path);
				}

			}

		} else {// featurecreep no existe
			es_fc = false;
		}

		if (!Statics.cargador) {
			Statics.cargador = true;
			if (!es_fc) {
				Statics.carpetas_de_mods.add(new File("mods/").toPath());// La carpeta de mods es de la superloader
			}
			instrument.addTransformer(new Transformaciones());
			MonitorDePID.main(new String[] {});
		}
	}

	public static boolean clase_existe(String clase) {
		try {
			Class.forName(clase);
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return false;
		}
	}

}
