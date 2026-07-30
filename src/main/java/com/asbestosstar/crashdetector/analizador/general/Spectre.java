package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad.DetectorMitigacionesCPU;
import com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad.ResultadoVulnerabilidadCPU;
import com.asbestosstar.crashdetector.config.ConfigBoolean;

/**
 * Advierte sobre las variantes originales Spectre V1 y Spectre V2.
 *
 * El resultado puede ser PARCIAL en sistemas que permiten confirmar V2 pero no
 * ofrecen una interfaz de ejecución equivalente para todas las rutas de V1.
 */
public final class Spectre extends VerificacionVulnerabilidadCPUBase {

	public static ConfigBoolean config = ConfigBoolean.de("ignorar_spectre", false);
	public static boolean hayProblema = false;

	@Override
	protected ConfigBoolean obtenerConfig() {
		return config;
	}

	@Override
	protected ResultadoVulnerabilidadCPU evaluar(String cpu) {
		return DetectorMitigacionesCPU.evaluarSpectre(cpu);
	}

	@Override
	protected void marcarProblemaGlobal() {
		hayProblema = true;
	}

	@Override
	protected String nombreVisible() {
		return MonitorDePID.idioma.nombreVerificacionSpectre();
	}

	@Override
	protected String idInterno() {
		return "cpu_spectre_v1_v2_mitigacion";
	}

	@Override
	protected float prioridadInterna() {
		return -1480;
	}

	@Override
	protected String textoDesactivar() {
		return MonitorDePID.idioma.desactivarVerificacionSpectre();
	}

	@Override
	public Verificaciones nueva() {
		return new Spectre();
	}
}
