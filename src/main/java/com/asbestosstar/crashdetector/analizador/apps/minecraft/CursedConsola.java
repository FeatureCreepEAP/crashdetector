package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.Verificaciones.Criticalidad;

/**
 * Verificador que detecta la ausencia del archivo launcher_log.txt en entornos
 * de CurseForge.
 * <p>
 * Esta clase comprueba si el programa se está ejecutando dentro de una
 * instancia de CurseForge (mediante la ruta del directorio) y si existe el
 * archivo de registro del launcher. Si no se encuentra el archivo, se muestra
 * una advertencia, ya que es necesario para analizar errores durante el inicio
 * del juego.
 * </p>
 * <p>
 * La ausencia del log suele deberse a la opción \"Omitir inicio del launcher\"
 * activada en el cliente de CurseForge. Esta opción debe desactivarse para
 * permitir la creación del log.
 * </p>
 * 
 * @author asbestosstar
 */
public class CursedConsola implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private static final String CURSEFORGE_MARKER = "curseforge";
	private static final String INSTANCE_MARKER = "instances";
	private static final String LAUNCHER_LOG = "launcher_log";

	@Override
	public void verificar(Consola consolaog) {
		try {
			// Validar entorno básico
			if (!esEntornoCurseForge())
				return;

			// Verificar existencia de logs del launcher
			if (!tieneLogLauncher()) {
				mensaje = MonitorDePID.idioma.noTieneConsolaDeLauncherCursedForge();
				activado = true;
			}
		} catch (Exception e) {
			// En caso de error inesperado, desactivar notificación
			CrashDetectorLogger.logException(e);
			activado = false;
		}
	}

	private boolean esEntornoCurseForge() {
		try {
			Path currentPath = Paths.get("").toAbsolutePath();
			String ruta = currentPath.toString().toLowerCase();
			return ruta.contains(CURSEFORGE_MARKER) && ruta.contains(INSTANCE_MARKER);
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
			return false;
		}
	}

	private boolean tieneLogLauncher() {
		// Verificación mejorada con múltiples puntos de control
		return MonitorDePID.consolas.stream().filter(consola -> consola.archivo != null)
				.anyMatch(consola -> consola.archivo.toString().toLowerCase().contains(LAUNCHER_LOG));
	}

	@Override
	public Verificaciones nueva() {
		return new CursedConsola();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000f; // Prioridad baja para advertencias de logs faltantes
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_cursed_consola();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}

}