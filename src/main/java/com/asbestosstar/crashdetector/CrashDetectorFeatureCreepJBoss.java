package com.asbestosstar.crashdetector;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.jboss.modules.ModuleClassLoader;
import org.jboss.modules.ModuleLoader;

import featurecreep.loader.FCLoaderBasic;

/**
 * CrashDetectorFeatureCreepJBoss
 *
 * Esta clase SOLO se carga si: - featurecreep.loader.FCLoaderBasic existe -
 * org.jboss.modules.ModuleClassLoader existe
 */
public final class CrashDetectorFeatureCreepJBoss {

	private CrashDetectorFeatureCreepJBoss() {
	}

	public static List<Path> obtenerPathsDeMods() {

		ArrayList<Path> paths = new ArrayList<Path>();

		ClassLoader cl = CrashDetectorFeatureCreepJBoss.class.getClassLoader();

		if (cl instanceof ModuleClassLoader) {

			ModuleClassLoader mcl = (ModuleClassLoader) cl;
			ModuleLoader ml = mcl.getModule().getModuleLoader();

			if (ml instanceof FCLoaderBasic) {

				FCLoaderBasic fc = (FCLoaderBasic) ml;

				for (Path p : fc.getCombindedModulePKZipLocations()) {
					paths.add(p);
				}
			}
		}

		if (paths.isEmpty()) {
			paths.add(new File("mods/").toPath());
		}

		return paths;
	}
}
