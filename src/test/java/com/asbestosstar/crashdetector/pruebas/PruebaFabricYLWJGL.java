package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.ErrorDeMonitorLWJGL;
import com.asbestosstar.crashdetector.analizador.general.ErrorEntrypointFabric;
import com.asbestosstar.crashdetector.analizador.general.ModulesDuplicadosJavaModulePlatform;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de ErrorEntrypointFabric, ErrorDeMonitorLWJGL y
 * ModulesDuplicadosJavaModulePlatform.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.ErrorEntrypointFabric",
		"com.asbestosstar.crashdetector.analizador.general.ErrorDeMonitorLWJGL",
		"com.asbestosstar.crashdetector.analizador.general.ModulesDuplicadosJavaModulePlatform" },
		nota = "Cubre errores de entrypoint Fabric, monitor LWJGL y modulos duplicados")
class PruebaFabricYLWJGL {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ErrorEntrypointFabric ====================

	@Test
	void entrypointFabricDetectaErrorMain() throws Exception {
		ErrorEntrypointFabric v = new ErrorEntrypointFabric();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"net.fabricmc.loader.api.EntrypointException: Exception while loading entries for entrypoint 'main' provided by 'modmenu'", 10);
		assertTrue(v.activado());
	}

	@Test
	void entrypointFabricDetectaErrorClient() throws Exception {
		ErrorEntrypointFabric v = new ErrorEntrypointFabric();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"net.fabricmc.loader.api.EntrypointException: Exception while loading entries for entrypoint 'client' provided by 'jei'", 10);
		assertTrue(v.activado());
	}

	@Test
	void entrypointFabricDetectaErrorSinComillas() throws Exception {
		ErrorEntrypointFabric v = new ErrorEntrypointFabric();
		// Sin comillas simples al final - mod nombre desconocido
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"net.fabricmc.loader.api.EntrypointException: Exception while loading entries for entrypoint 'main' provided by 'bad_mod'", 10);
		assertTrue(v.activado());
	}

	@Test
	void entrypointFabricNoSeActivaConSoloEntrypointException() throws Exception {
		ErrorEntrypointFabric v = new ErrorEntrypointFabric();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"net.fabricmc.loader.api.EntrypointException: something else", 10);
		assertFalse(v.activado(), "Necesita Exception while loading y provided by.");
	}

	@Test
	void entrypointFabricNoSeActivaConLineaNormal() throws Exception {
		ErrorEntrypointFabric v = new ErrorEntrypointFabric();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Fabric entrypoint loaded", 1);
		assertFalse(v.activado());
	}

	@Test
	void entrypointFabricIdentidad() {
		ErrorEntrypointFabric v = new ErrorEntrypointFabric();
		assertEquals("error_entrypoint_fabric", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorEntrypointFabric);
		assertEquals(1300.0f, v.prioridad());
	}

	// ==================== ErrorDeMonitorLWJGL ====================

	@Test
	void monitorLwjglDetectaError() throws Exception {
		ErrorDeMonitorLWJGL v = new ErrorDeMonitorLWJGL();
		v.verificarPorLinea(ConsolaPrueba.crear(), "org.lwjgl.LWJGLException: Failed to set display mode", 10);
		assertTrue(v.activado());
	}

	@Test
	void monitorLwjglNoSeActivaConLineaNormal() throws Exception {
		ErrorDeMonitorLWJGL v = new ErrorDeMonitorLWJGL();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Monitor initialized", 1);
		assertFalse(v.activado());
	}

	@Test
	void monitorLwjglIdentidad() {
		ErrorDeMonitorLWJGL v = new ErrorDeMonitorLWJGL();
		assertEquals("monitor_lwjgl", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorDeMonitorLWJGL);
	}

	// ==================== ModulesDuplicadosJavaModulePlatform ====================

	@Test
	void modulesDuplicadosDetectaExportPackage() throws Exception {
		ModulesDuplicadosJavaModulePlatform v = new ModulesDuplicadosJavaModulePlatform();
		ModulesDuplicadosJavaModulePlatform.reiniciarGlobal();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.lang.module.ResolutionException: Modules jlayer and mts export package javazoom.jl.player to module fabric_screen_handler_api_v1", 10);
		assertTrue(v.activado());
	}

	@Test
	void modulesDuplicadosDetectaContainsPackage() throws Exception {
		ModulesDuplicadosJavaModulePlatform v = new ModulesDuplicadosJavaModulePlatform();
		ModulesDuplicadosJavaModulePlatform.reiniciarGlobal();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.lang.module.ResolutionException: Module fabric_api contains package net.fabricmc.api also present in module fabric_api_v2", 10);
		assertTrue(v.activado());
	}

	@Test
	void modulesDuplicadosDetectaConExceptionInThread() throws Exception {
		ModulesDuplicadosJavaModulePlatform v = new ModulesDuplicadosJavaModulePlatform();
		ModulesDuplicadosJavaModulePlatform.reiniciarGlobal();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"Exception in thread \"main\" java.lang.module.ResolutionException: Modules mod1 and mod2 export package com.example to module minecraft", 10);
		assertTrue(v.activado());
	}

	@Test
	void modulesDuplicadosDetectaMixinExtras() throws Exception {
		ModulesDuplicadosJavaModulePlatform v = new ModulesDuplicadosJavaModulePlatform();
		ModulesDuplicadosJavaModulePlatform.reiniciarGlobal();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.lang.module.ResolutionException: Modules MixinExtras and mixinextras.neoforge export package org.spongepowered.mixinextras to module neoforge", 10);
		assertTrue(v.activado());
		assertTrue(v.mensaje().contains("MixinExtras"), "Mensaje debe mencionar MixinExtras.");
	}

	@Test
	void modulesDuplicadosDeduplicacionGlobal() throws Exception {
		ModulesDuplicadosJavaModulePlatform v1 = new ModulesDuplicadosJavaModulePlatform();
		ModulesDuplicadosJavaModulePlatform v2 = new ModulesDuplicadosJavaModulePlatform();
		ModulesDuplicadosJavaModulePlatform.reiniciarGlobal();
		String linea = "java.lang.module.ResolutionException: Modules a and b export package com.test to module c";
		v1.verificarPorLinea(ConsolaPrueba.crear(), linea, 10);
		assertTrue(v1.activado());
		v2.verificarPorLinea(ConsolaPrueba.crear(), linea, 20);
		assertFalse(v2.activado(), "Deduplicacion global debe evitar segunda activacion.");
	}

	@Test
	void modulesDuplicadosNoSeActivaConSoloResolutionException() throws Exception {
		ModulesDuplicadosJavaModulePlatform v = new ModulesDuplicadosJavaModulePlatform();
		ModulesDuplicadosJavaModulePlatform.reiniciarGlobal();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.lang.module.ResolutionException: some other error", 10);
		assertFalse(v.activado(), "Sin export/contains package no debe activar.");
	}

	@Test
	void modulesDuplicadosNoSeActivaConLineaNormal() throws Exception {
		ModulesDuplicadosJavaModulePlatform v = new ModulesDuplicadosJavaModulePlatform();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Module loaded", 1);
		assertFalse(v.activado());
	}

	@Test
	void modulesDuplicadosIdentidad() {
		ModulesDuplicadosJavaModulePlatform v = new ModulesDuplicadosJavaModulePlatform();
		assertEquals("modules_duplicados_java_module_platform", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ModulesDuplicadosJavaModulePlatform);
		assertEquals(1000.0f, v.prioridad());
	}
}
