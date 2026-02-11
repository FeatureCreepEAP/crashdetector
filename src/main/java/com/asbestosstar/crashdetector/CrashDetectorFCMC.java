package com.asbestosstar.crashdetector;

import java.lang.instrument.Instrumentation;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.lanzer.servicio.CDProfiler;
import com.asbestosstar.crashdetector.lanzer.servicio.CDSampler;
import com.asbestosstar.crashdetector.lanzer.servicio.CDTracer;
import com.asbestosstar.crashdetector.lanzer.servicio.LibreMods;
import com.asbestosstar.crashdetector.lanzer.servicio.ServicioCDLauncher;

/**
 * CrashDetectorFCMC
 *
 * Entrada principal del javaagent. - Verifica explícitamente FeatureCreep +
 * JBoss - SOLO entonces carga e invoca CrashDetectorFeatureCreepJBoss - Nunca
 * referencia esa clase si no existen las dependencias
 */
public class CrashDetectorFCMC {

	public static List<ServicioCDLauncher> servicios_cdlauncher = new ArrayList<>();

	/* Clases que DEBEN existir */
	private static final String FC_CLASS = "featurecreep.loader.FCLoaderBasic";

	private static final String JBOSS_CLASS = "org.jboss.modules.ModuleClassLoader";

	/* Bridge (NO se toca si las anteriores no existen) */
	private static final String BRIDGE_CLASS = "com.asbestosstar.crashdetector.CrashDetectorFeatureCreepJBoss";

	public static void premain(String args, Instrumentation instrument) {

		/* ================= CDLauncher ================= */

		if (esModoCDLauncher(args)) {
			//LibreMods.librar(instrument);			
			Statics.app_en_cdlauncher = true;
			MonitorDePID.registrarGUISPredeterminado();
			CargadorExtensiones.cargarExtensionesProcesoApp(MonitorDePID.ultimo_mods.toFile());// TODO improver

			servicios_cdlauncher.add(new CDTracer());
			servicios_cdlauncher.add(new CDProfiler());
			servicios_cdlauncher.add(new CDSampler());

			for (ServicioCDLauncher serv : servicios_cdlauncher) {
				String id = serv.id();
				boolean activo = Boolean.getBoolean("crashdetector." + id);
				if (activo) {
					serv.activar(instrument);
				}

			}

			Transformaciones.init();
			instrument.addTransformer(new Transformaciones());
			return;
		}

		/* ========== FeatureCreep + JBoss (estricto) ========== */

		if (existeFeatureCreep() && existeJBossModules()) {
			inicializarFeatureCreepJBoss();
		}

		instrument.addTransformer(new Transformaciones());
	}

	/* ===================================================== */
	/* ==================== INTEGRACIÓN ==================== */
	/* ===================================================== */

	private static void inicializarFeatureCreepJBoss() {

		try {

			List<Path> paths = CrashDetectorFeatureCreepJBoss.obtenerPathsDeMods();

			Transformaciones.init();
			CargadoresComun.init(paths, CargadoresComun.CDOrigin.FEATURECREEP);

		} catch (Throwable t) {
			// Fallo silencioso: nunca romper el arranque
		}
	}

	/* ===================================================== */
	/* ===================== CHEQUEOS ====================== */
	/* ===================================================== */

	public static boolean existeFeatureCreep() {
		return claseExiste(FC_CLASS);
	}

	public static boolean existeJBossModules() {
		return claseExiste(JBOSS_CLASS);
	}

	private static boolean claseExiste(String name) {
		try {
			Class.forName(name, false, CrashDetectorFCMC.class.getClassLoader());
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public static boolean esModoCDLauncher(String args) {
		return args != null && args.toLowerCase().contains("cdlauncher");
	}
}
