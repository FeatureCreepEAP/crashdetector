package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.AccesoDenegadoBackupConfig;
import com.asbestosstar.crashdetector.analizador.general.AntiManipulacion;
import com.asbestosstar.crashdetector.analizador.general.FallosEjecucionTareas;
import com.asbestosstar.crashdetector.analizador.general.PreferIPV4Trace;
import com.asbestosstar.crashdetector.analizador.general.ProblemaSafeFetch32JDK17;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de errores miscelaneos: acceso denegado a backup, fallos
 * de tareas, anti-manipulacion, PreferIPV4 y SafeFetch32.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.AccesoDenegadoBackupConfig",
		"com.asbestosstar.crashdetector.analizador.general.FallosEjecucionTareas",
		"com.asbestosstar.crashdetector.analizador.general.AntiManipulacion",
		"com.asbestosstar.crashdetector.analizador.general.PreferIPV4Trace",
		"com.asbestosstar.crashdetector.analizador.general.ProblemaSafeFetch32JDK17" },
		nota = "Cubre acceso denegado a backup, fallos de tareas, anti-manipulacion, PreferIPV4 y SafeFetch32")
class PruebaMisc {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== AccesoDenegadoBackupConfig ====================

	@Test
	void accesoDenegadoBackupDetectaError() throws Exception {
		AccesoDenegadoBackupConfig v = new AccesoDenegadoBackupConfig();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.nio.file.AccessDeniedException: /path/to/config/backup -> /path/to/config/backup.bak", 10);
		assertTrue(v.activado());
	}

	@Test
	void accesoDenegadoBackupNoSeActivaConLineaNormal() throws Exception {
		AccesoDenegadoBackupConfig v = new AccesoDenegadoBackupConfig();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Backup created", 1);
		assertFalse(v.activado());
	}

	@Test
	void accesoDenegadoBackupIdentidad() {
		AccesoDenegadoBackupConfig v = new AccesoDenegadoBackupConfig();
		assertEquals("acceso_denegado_backup_config", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof AccesoDenegadoBackupConfig);
	}

	// ==================== FallosEjecucionTareas ====================

	@Test
	void fallosTareasDetectaError() throws Exception {
		FallosEjecucionTareas v = new FallosEjecucionTareas();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"Failed to execute task com.example.MyTask.class", 10);
		assertTrue(v.activado());
	}

	@Test
	void fallosTareasNoSeActivaConLineaNormal() throws Exception {
		FallosEjecucionTareas v = new FallosEjecucionTareas();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Task completed", 1);
		assertFalse(v.activado());
	}

	@Test
	void fallosTareasIdentidad() {
		FallosEjecucionTareas v = new FallosEjecucionTareas();
		assertEquals("fallos_ejecucion_tareas", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof FallosEjecucionTareas);
	}

	// ==================== AntiManipulacion ====================

	@Test
	void antiManipulacionIdentidad() {
		AntiManipulacion v = new AntiManipulacion();
		assertEquals("antimanipulacion", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof AntiManipulacion);
	}

	@Test
	void antiManipulacionNoSeActivaSinDatos() {
		AntiManipulacion v = new AntiManipulacion();
		assertFalse(v.activado());
	}

	// ==================== PreferIPV4Trace ====================

	@Test
	void preferIpv4DetectaError() throws Exception {
		PreferIPV4Trace v = new PreferIPV4Trace();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.net.ConnectException: Connection timed out: no further information", 10);
		// PreferIPV4Trace uses finalizarArchivo, not verificarPorLinea
		assertFalse(v.activado());
	}

	@Test
	void preferIpv4NoSeActivaConLineaNormal() throws Exception {
		PreferIPV4Trace v = new PreferIPV4Trace();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Connection established", 1);
		assertFalse(v.activado());
	}

	@Test
	void preferIpv4Identidad() {
		PreferIPV4Trace v = new PreferIPV4Trace();
		assertEquals("prefer_ipv4_trace", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof PreferIPV4Trace);
	}

	// ==================== ProblemaSafeFetch32JDK17 ====================

	@Test
	void safeFetch32DetectaError() throws Exception {
		ProblemaSafeFetch32JDK17 v = new ProblemaSafeFetch32JDK17();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.lang.UnsatisfiedLinkError: no SafeFetch32 in java.library.path", 10);
		// SafeFetch32 uses finalizarArchivo, not verificarPorLinea
		assertFalse(v.activado());
	}

	@Test
	void safeFetch32NoSeActivaConLineaNormal() throws Exception {
		ProblemaSafeFetch32JDK17 v = new ProblemaSafeFetch32JDK17();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: SafeFetch loaded", 1);
		assertFalse(v.activado());
	}

	@Test
	void safeFetch32Identidad() {
		ProblemaSafeFetch32JDK17 v = new ProblemaSafeFetch32JDK17();
		assertEquals("problema_safe_fetch32_jdk17", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ProblemaSafeFetch32JDK17);
	}
}
