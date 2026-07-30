package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad.DetectorMitigacionesCPU;
import com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad.ResultadoVulnerabilidadCPU;
import com.asbestosstar.crashdetector.config.ConfigBoolean;

/**
 * Advierte cuando el CPU puede estar afectado por Meltdown y no se puede
 * confirmar una mitigación completa del sistema operativo o del hardware.
 */
public final class Meltdown extends VerificacionVulnerabilidadCPUBase {

	public static ConfigBoolean config = ConfigBoolean.de("ignorar_meltdown", false);
	public static boolean hayProblema = false;

	@Override
	protected ConfigBoolean obtenerConfig() {
		return config;
	}

	@Override
	protected ResultadoVulnerabilidadCPU evaluar(String cpu) {
		return DetectorMitigacionesCPU.evaluarMeltdown(cpu);
	}

	@Override
	protected void marcarProblemaGlobal() {
		hayProblema = true;
	}

	@Override
	protected String nombreVisible() {
		return MonitorDePID.idioma.nombreVerificacionMeltdown();
	}

	@Override
	protected String idInterno() {
		return "cpu_meltdown_mitigacion";
	}

	@Override
	protected float prioridadInterna() {
		return -1490;
	}

	@Override
	protected String textoDesactivar() {
		return MonitorDePID.idioma.desactivarVerificacionMeltdown();
	}

	@Override
	public Verificaciones nueva() {
		return new Meltdown();
	}
}
