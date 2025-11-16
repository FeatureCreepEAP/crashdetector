package com.asbestosstar.crashdetector;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.jboss.modules.ModuleClassLoader;
import org.jboss.modules.ModuleLoader;

import com.asbestosstar.crashdetector.divisor.HolaMundoConsolaDivisidor;

import featurecreep.loader.FCLoaderBasic;

public class CrashDetectorFCMC {

	public static void premain(String args, Instrumentation instrument) {
		// Buscar para archivos de mods si es FC
		if (FCExiste()) {
			if (clase_existe("dangerzone.BaseMod")) {
				Statics.APP = App.DANGERZONE;
			} // TODO para otras applicaciones

			Transformaciones.init();

			CargadoresComun.init(obtenerPathsDeMods(), CargadoresComun.CDOrigin.FEATURECREEP);
		}

		instrument.addTransformer(new Transformaciones());

	}

	public static boolean FCExiste() {// TODO improver
		return clase_existe("featurecreep.loader.FCLoaderBasic");
	}

	/**
	 * 
	 * @return FCLoaderBasic. Si Es FeatureCreep y cargando de FeatureCreep puedemos
	 *         obener FCLoaderBasic. Null si no es FCLoaderBasic
	 */
	public static @Nullable FCLoaderBasic obtenerFCLoaderBasic() {
		if(!FCExiste()) {
			return null;
		}
		
		ClassLoader cl = CrashDetectorFCMC.class.getClassLoader();
		if (cl instanceof ModuleClassLoader) {
			ModuleClassLoader mcl = (ModuleClassLoader) cl;
			ModuleLoader ml = mcl.getModule().getModuleLoader();

			if (ml instanceof FCLoaderBasic) {
				return (FCLoaderBasic) ml;
			} else {
				// UN OTRA JBOSS MODULES O JBOSS FORGE
				return null;
			}

		} else {
			// NO EJECTIR DESDE FEATURECREEP
			return null;
		}
	}

	public static List<Path> obtenerPathsDeMods() {
		ArrayList<Path> paths = new ArrayList<Path>();// TODO obtener desde GameProviders cuando no ejecutir desde FC

		FCLoaderBasic fc = obtenerFCLoaderBasic();

		// TODO mas

		if (fc != null) {
			for (Path path : fc.getCombindedModulePKZipLocations()) {
				paths.add(path);
			}

		}

		if (paths.isEmpty()) {
			paths.add(new File("mods/").toPath());
		} // La carpeta de mods es de la superloader

		return paths;
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
