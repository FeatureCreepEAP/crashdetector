package com.asbestosstar.crashdetector.gui.tipos.jgit;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.dto.modpack.tlmods.ActualizadorTLauncherAdditional;

/**
 * Ejecuta commits automáticos después del backup si JGit está disponible.
 */
public class JGitAutoCommit {

	public static ConfigBoolean AUTO_COMMIT = ConfigBoolean.de("jgit.auto_commit.despues_backup", false);
	public static ConfigBoolean AUTO_PUSH = ConfigBoolean.de("jgit.auto_push.despues_commit", false);

	public static void hacerAutoCommitSiCorresponde() {
		try {
			if (!BuscarParaJGit.estaJGitBasicoEnClasspath()) {
				CrashDetectorLogger.log("JGit no está en classpath. No se hace commit automático.");
				return;
			}

			if (!AUTO_COMMIT.obtener()) {
				return;
			}

			ActualizadorTLauncherAdditional.actualizarCarpetaActual();
			File carpeta = new File(System.getProperty("user.dir")).getAbsoluteFile();

			if (!JGitReflexivo.repositorioExiste(carpeta)) {
				CrashDetectorLogger.log("JGit: no hay .git en la carpeta actual.");
				return;
			}

			String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			boolean hizoCommit = JGitReflexivo.commitAutomatico(carpeta,
					"Commit automático de " + Statics.nombre_cd.obtener() + " - " + fecha);

			if (hizoCommit && AUTO_PUSH.obtener() && JGitReflexivo.tieneRemote(carpeta)) {
				JGitReflexivo.push(carpeta);
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}
}