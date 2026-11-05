package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.HardwareFueraDePolitica;
import com.asbestosstar.crashdetector.analizador.general.Meltdown;
import com.asbestosstar.crashdetector.analizador.general.RaptorLakeInestable;
import com.asbestosstar.crashdetector.analizador.general.Spectre;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de Meltdown, Spectre, RaptorLakeInestable y HardwareFueraDePolitica.
 * Todos usan VerificacionVulnerabilidadCPUBase que busca "Processor Name:" en las lineas.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.Meltdown",
		"com.asbestosstar.crashdetector.analizador.general.Spectre",
		"com.asbestosstar.crashdetector.analizador.general.RaptorLakeInestable",
		"com.asbestosstar.crashdetector.analizador.general.HardwareFueraDePolitica" },
		nota = "Cubre deteccion de vulnerabilidades CPU Meltdown/Spectre, inestabilidad Raptor Lake y hardware fuera de politica")
class PruebaHardwareCPU {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== Meltdown ====================

	@Test
	void meltdownBuscaProcessorName() throws Exception {
		Meltdown v = new Meltdown();
		// Sin "Processor Name:" no hace nada
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: CPU check", 1);
		assertFalse(v.activado());
	}

	@Test
	void meltdownNoSeActivaConLineaNormal() throws Exception {
		Meltdown v = new Meltdown();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: CPU check passed", 1);
		assertFalse(v.activado());
	}

	@Test
	void meltdownIdentidad() {
		Meltdown v = new Meltdown();
		assertEquals("cpu_meltdown_mitigacion", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof Meltdown);
		assertEquals(-1490.0f, v.prioridad());
	}

	// ==================== Spectre ====================

	@Test
	void spectreBuscaProcessorName() throws Exception {
		Spectre v = new Spectre();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: CPU check", 1);
		assertFalse(v.activado());
	}

	@Test
	void spectreNoSeActivaConLineaNormal() throws Exception {
		Spectre v = new Spectre();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: CPU check passed", 1);
		assertFalse(v.activado());
	}

	@Test
	void spectreIdentidad() {
		Spectre v = new Spectre();
		assertEquals("cpu_spectre_v1_v2_mitigacion", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof Spectre);
	}

	// ==================== RaptorLakeInestable ====================

	@Test
	void raptorLakeBuscaProcessorName() throws Exception {
		RaptorLakeInestable v = new RaptorLakeInestable();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: CPU check", 1);
		assertFalse(v.activado());
	}

	@Test
	void raptorLakeNoSeActivaConLineaNormal() throws Exception {
		RaptorLakeInestable v = new RaptorLakeInestable();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: CPU stable", 1);
		assertFalse(v.activado());
	}

	@Test
	void raptorLakeIdentidad() {
		RaptorLakeInestable v = new RaptorLakeInestable();
		assertEquals("raptor_lake_microcode", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof RaptorLakeInestable);
	}

	// ==================== HardwareFueraDePolitica ====================

	@Test
	void hardwareFueraDePoliticaNoSeActivaConLineaNormal() throws Exception {
		HardwareFueraDePolitica v = new HardwareFueraDePolitica();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Hardware check passed", 1);
		assertFalse(v.activado());
	}

	@Test
	void hardwareFueraDePoliticaIdentidad() {
		HardwareFueraDePolitica v = new HardwareFueraDePolitica();
		assertEquals("hardware_fuera_de_politica", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof HardwareFueraDePolitica);
	}
}
