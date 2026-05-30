package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.detectorlanzer.DetectorSKLauncher;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Verificador que muestra una advertencia cuando el juego fue iniciado desde
 * SKLauncher.
 * <p>
 * En los últimos meses se han observado varios casos de corrupción e inicios
 * problemáticos relacionados con SKLauncher. Esta advertencia no significa que
 * SKLauncher sea siempre la causa directa del fallo, pero sí informa al usuario
 * de que el launcher podría estar implicado.
 * </p>
 * <p>
 * Si el juego se cierra muy temprano, o incluso falla sin mods instalados,
 * podría tratarse de un problema de corrupción del entorno o de la instalación.
 * En esos casos, se recomienda probar otro launcher.
 * </p>
 * 
 * @author asbestosstar
 */
public class SKLauncherAdvertencia implements Verificaciones {

	private boolean activado = false;
	private String mensaje = MonitorDePID.idioma.mensajeAdvertenciaSKLauncher();

	@Override
	public void verificar(Consola consolaog) {
		// Activar únicamente cuando el launcher detectado sea SKLauncher
		if (Statics.lanzer_del_app != null && Statics.lanzer_del_app.equals(DetectorSKLauncher.ID)) {
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new SKLauncherAdvertencia();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000f; // Prioridad baja: es una advertencia general del launcher
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAdvertenciaSKLauncher();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public Criticalidad nivel_de_criticalidad() {
		return Criticalidad.ADVERTENCIA;
	}

	@Override
	public String id() {
		return "advertencia_sklauncher";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.builder().doc("en", "minecraft/Launchers.md").build();
	}

	@Override
	public String enlaceACodigo() {
		return com.asbestosstar.crashdetector.Statics.GIT
				+ "src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}