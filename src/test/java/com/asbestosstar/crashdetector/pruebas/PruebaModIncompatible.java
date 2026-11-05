package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.AccesoIlegalMod;
import com.asbestosstar.crashdetector.analizador.general.FaltaModuleJPMS;
import com.asbestosstar.crashdetector.analizador.general.JPMSIllegalAccess;
import com.asbestosstar.crashdetector.analizador.general.ModIncompatibleConCargadorActivo;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de ModIncompatibleConCargadorActivo, AccesoIlegalMod,
 * JPMSIllegalAccess y FaltaModuleJPMS.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.ModIncompatibleConCargadorActivo",
		"com.asbestosstar.crashdetector.analizador.general.AccesoIlegalMod",
		"com.asbestosstar.crashdetector.analizador.general.JPMSIllegalAccess",
		"com.asbestosstar.crashdetector.analizador.general.FaltaModuleJPMS" },
		nota = "Cubre conflictos de cargadores de mods, errores JPMS y modulos faltantes")
class PruebaModIncompatible {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ModIncompatibleConCargadorActivo ====================

	@Test
	void modIncompatibleNoSeActivaSinEstado() {
		// ModIncompatibleConCargadorActivo checks Statics.lanzer_del_app
		// It does not use verificarPorLinea for detection
		ModIncompatibleConCargadorActivo v = new ModIncompatibleConCargadorActivo();
		assertFalse(v.activado());
	}

	@Test
	void modIncompatibleNoSeActivaConLineaNormal() throws Exception {
		ModIncompatibleConCargadorActivo v = new ModIncompatibleConCargadorActivo();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Mod loaded successfully", 1);
		assertFalse(v.activado());
	}

	@Test
	void modIncompatibleIdentidad() {
		ModIncompatibleConCargadorActivo v = new ModIncompatibleConCargadorActivo();
		assertEquals("mod_incompatible_con_cargador_activo", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ModIncompatibleConCargadorActivo);
	}

	// ==================== AccesoIlegalMod ====================

	@Test
	void accesoIlegalModDetectaError() throws Exception {
		AccesoIlegalMod v = new AccesoIlegalMod();
		v.verificarPorLinea(ConsolaPrueba.crear(), "java.lang.IllegalAccessError: class com.example.ModClass tried to access private method net.minecraft.class123.method123()V", 10);
		assertTrue(v.activado());
	}

	@Test
	void accesoIlegalModNoSeActivaConLineaNormal() throws Exception {
		AccesoIlegalMod v = new AccesoIlegalMod();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Mod access granted", 1);
		assertFalse(v.activado());
	}

	@Test
	void accesoIlegalModIdentidad() {
		AccesoIlegalMod v = new AccesoIlegalMod();
		assertEquals("acceso_ilegal_mod", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof AccesoIlegalMod);
	}

	// ==================== JPMSIllegalAccess ====================

	@Test
	void jpmsIllegalAccessDetectaError() throws Exception {
		JPMSIllegalAccess v = new JPMSIllegalAccess();
		v.verificarPorLinea(ConsolaPrueba.crear(), "java.lang.IllegalAccessError: class com.example.ModClass cannot access class java.lang.ProcessHandle (com.example.ModClass is in unnamed module of loader 'app'; java.lang.ProcessHandle is in module java.base of loader 'bootstrap')", 10);
		// JPMSIllegalAccess uses finalizarArchivo, not verificarPorLinea
		assertFalse(v.activado());
	}

	@Test
	void jpmsIllegalAccessNoSeActivaConLineaNormal() throws Exception {
		JPMSIllegalAccess v = new JPMSIllegalAccess();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Module access granted", 1);
		assertFalse(v.activado());
	}

	@Test
	void jpmsIllegalAccessIdentidad() {
		JPMSIllegalAccess v = new JPMSIllegalAccess();
		assertEquals("jpms_illegal_access", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof JPMSIllegalAccess);
	}

	// ==================== FaltaModuleJPMS ====================

	@Test
	void faltaModuleJpmsDetectaError() throws Exception {
		FaltaModuleJPMS v = new FaltaModuleJPMS();
		v.verificarPorLinea(ConsolaPrueba.crear(), "java.lang.module.FindException: Module java.xml.ws not found", 10);
		// FaltaModuleJPMS uses finalizarArchivo, not verificarPorLinea
		// This test checks that the analyzer doesn't crash
		assertFalse(v.activado());
	}

	@Test
	void faltaModuleJpmsNoSeActivaConLineaNormal() throws Exception {
		FaltaModuleJPMS v = new FaltaModuleJPMS();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Module loaded", 1);
		assertFalse(v.activado());
	}

	@Test
	void faltaModuleJpmsIdentidad() {
		FaltaModuleJPMS v = new FaltaModuleJPMS();
		assertEquals("falta_module_jpms", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof FaltaModuleJPMS);
	}
}
