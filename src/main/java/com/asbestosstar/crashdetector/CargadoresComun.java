package com.asbestosstar.crashdetector;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.asbestosstar.crashdetector.divisor.HolaMundoConsolaDivisidor;

public class CargadoresComun {

	public static void init(Path[] carpetas_de_mods, CDOrigin origin) {
		Set<Path> paths = new HashSet<>();
		for (Path path : carpetas_de_mods) {
			paths.add(path);
		}
		init(paths, origin);
	}

	public static void init(Collection<Path> carpetas_de_mods, CDOrigin origin) {
		// TODO Auto-generated method stub

		// Asegurar detección correcta de CDLauncher en ESTE layer
		detectarCdLauncherPorArgs();

		Statics.carpetas_de_mods.addAll(carpetas_de_mods);
		if (!Statics.cargador) {
			Statics.cargador = true;
			if (!Statics.app_en_cdlauncher) {
				ProxySysOutSysErr.init();
				if (ProxyLog4j2.log4j2existe()) {
					ProxyLog4j2.init();
					LogManager.getLogger(HolaMundoConsolaDivisidor.class).log(Level.ERROR,
							HolaMundoConsolaDivisidor.HOLA_MUNDO);
				}
			}
			if (!origin.equals(CDOrigin.FEATURECREEP) && CrashDetectorFCMC.existeFeatureCreep()) {
				Statics.carpetas_de_mods.addAll(CrashDetectorFeatureCreepJBoss.obtenerPathsDeMods());
			}

			if (!Statics.app_en_cdlauncher) {
				// ParcheSoyPirata.SOY_PIRATA.delete();//Eliminar cuando reiniciar.
				MonitorDePID.main(new String[] {});
			}

		}
	}

	private static void detectarCdLauncherPorArgs() {

		// Si ya fue seteado en este classloader, no repetir
		if (Statics.app_en_cdlauncher)
			return;

		RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
		List<String> args = mxBean.getInputArguments();

		for (String arg : args) {
			// Ejemplo:
			// -javaagent:/ruta/crashdetector.jar=cdlauncher
			if (arg.startsWith("-javaagent:") && arg.contains("crashdetector") && arg.contains("cdlauncher")) {

				Statics.app_en_cdlauncher = true;
				return;
			}
		}
	}

	/**
	 * Donde estas ejecutar CrashDetecto desde, NO SIMEPRE ES EL MISMO DE CARGADOR,
	 * PUEDE SER DIFERENTE
	 */
	public static class CDOrigin {
		public static CDOrigin FEATURECREEP = new CDOrigin();
		public static CDOrigin FABRICMC = new CDOrigin();
		public static CDOrigin MODLAUNCHER = new CDOrigin();
		public static CDOrigin FANCYMODLOADER = new CDOrigin();
		public static CDOrigin MANGOMODLOADER = new CDOrigin();
		public static CDOrigin RIFT = new CDOrigin();
		public static CDOrigin LITELOADER = new CDOrigin();
		public static CDOrigin NILLOADER = new CDOrigin();
		public static CDOrigin MEDDLE = new CDOrigin();
		public static CDOrigin MCFORGE_PREMODLAUNCHER = new CDOrigin();
		public static CDOrigin BUKKIT = new CDOrigin();

	}

}
