package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.detectorlanzer.DetectorCurseForgeApp;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

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
public class CursedConsola implements VerificacionesLegacy {

	private boolean activado = false;
	private String mensaje = MonitorDePID.idioma.noTieneConsolaDeLauncherCursedForge();
	private static final String CURSEFORGE_MARKER = "curseforge";
	private static final String INSTANCE_MARKER = "instances";
	private static final String LAUNCHER_LOG = "launcher_log";

	@Override
	public String[] patronesRapidos() {
		// No necesita activar por línea.
		// Usa vdst.trazos_completos en finalizarArchivo().
		verificar();
		return new String[0];
	}

	public void verificar() {
//		try {
//			// Validar entorno básico
//			if (!esEntornoCurseForge())
//				return;
//
//			// Verificar existencia de logs del launcher
//			if (!tieneLogLauncher()) {
//				mensaje = MonitorDePID.idioma.noTieneConsolaDeLauncherCursedForge();
//				activado = true;
//			}
//		} catch (Exception e) {
//			// En caso de error inesperado, desactivar notificación
//			CrashDetectorLogger.logException(e);
//			activado = false;
//		} Ahora no usemos laucnher log pero CurseForge launcher tiene otras problemas

		if (Statics.lanzer_del_app != null && Statics.lanzer_del_app.equals(DetectorCurseForgeApp.ID)) {
			activado = true;
		}

	}

	public void verificarCoincidencia(EventoDeCoincidencia evento) {
	}

	@Override
	public VerificacionesLegacy nueva() {
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
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "cursedconsola";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}