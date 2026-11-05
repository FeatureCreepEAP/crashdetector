package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.FaltaModAnimado;
import com.asbestosstar.crashdetector.analizador.general.LanzerDesAnimado;
import com.asbestosstar.crashdetector.analizador.general.LanzerNoAnimado;
import com.asbestosstar.crashdetector.analizador.general.SpongeMixinClaseMalUbicada;
import com.asbestosstar.crashdetector.analizador.general.SpongeMixinConfigsProblematicos;
import com.asbestosstar.crashdetector.analizador.general.TienesModDesAnimado;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de SpongeMixin y lanzadores: clase mal ubicada, configs
 * problematicos, lanzador desanimado, mod desanimado y mod faltante.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.SpongeMixinClaseMalUbicada",
		"com.asbestosstar.crashdetector.analizador.general.SpongeMixinConfigsProblematicos",
		"com.asbestosstar.crashdetector.analizador.general.LanzerDesAnimado",
		"com.asbestosstar.crashdetector.analizador.general.LanzerNoAnimado",
		"com.asbestosstar.crashdetector.analizador.general.TienesModDesAnimado",
		"com.asbestosstar.crashdetector.analizador.general.FaltaModAnimado" },
		nota = "Cubre errores de SpongeMixin, lanzadores desanimados y mods faltantes")
class PruebaSpongeMixinYLanzadores {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== SpongeMixinClaseMalUbicada ====================

	@Test
	void spongeMixinClaseMalUbicadaDetectaError() throws Exception {
		SpongeMixinClaseMalUbicada v = new SpongeMixinClaseMalUbicada();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"org.spongepowered.asm.mixin.throwables.IllegalClassLoadError: class com.example.MixinClass is in a defined mixin package owned by mixin.mod.mixins.json and cannot be referenced directly", 10);
		assertTrue(v.activado());
	}

	@Test
	void spongeMixinClaseMalUbicadaDetectaInterface() throws Exception {
		SpongeMixinClaseMalUbicada v = new SpongeMixinClaseMalUbicada();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"org.spongepowered.asm.mixin.throwables.IllegalClassLoadError: interface com.example.MixinInterface is in a defined mixin package owned by mixin.mod.mixins.json and cannot be referenced directly", 10);
		assertTrue(v.activado());
	}

	@Test
	void spongeMixinClaseMalUbicadaNoSeActivaConSoloIllegalClassLoad() throws Exception {
		SpongeMixinClaseMalUbicada v = new SpongeMixinClaseMalUbicada();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"org.spongepowered.asm.mixin.throwables.IllegalClassLoadError: something else", 10);
		assertFalse(v.activado(), "Necesita todos los tres patrones.");
	}

	@Test
	void spongeMixinClaseMalUbicadaNoSeActivaConLineaNormal() throws Exception {
		SpongeMixinClaseMalUbicada v = new SpongeMixinClaseMalUbicada();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Mixin applied", 1);
		assertFalse(v.activado());
	}

	@Test
	void spongeMixinClaseMalUbicadaIdentidad() {
		SpongeMixinClaseMalUbicada v = new SpongeMixinClaseMalUbicada();
		assertEquals("mixin_clase_mal_ubicada", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof SpongeMixinClaseMalUbicada);
		assertEquals(1650.0f, v.prioridad());
	}

	// ==================== SpongeMixinConfigsProblematicos ====================

	@Test
	void spongeMixinConfigsProblematicosDetectaResourceInvalido() throws Exception {
		SpongeMixinConfigsProblematicos v = new SpongeMixinConfigsProblematicos();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"The specified resource 'mymod.mixins.json' was invalid or could not be read", 10);
		assertTrue(v.activado());
	}

	@Test
	void spongeMixinConfigsProblematicosDetectaMixinApplyError() throws Exception {
		SpongeMixinConfigsProblematicos v = new SpongeMixinConfigsProblematicos();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"org.spongepowered.asm.mixin.throwables.MixinApplyError: Mixin [mymod.mixins.json:MixinExample] from phase [DEFAULT] in config [mymod.mixins.json] failed", 10);
		assertTrue(v.activado());
	}

	@Test
	void spongeMixinConfigsProblematicosDetectaInvalidMixinException() throws Exception {
		SpongeMixinConfigsProblematicos v = new SpongeMixinConfigsProblematicos();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException: @Mixin target net.minecraft.class_1234 was not found mymod.mixins.json", 10);
		assertTrue(v.activado());
	}

	@Test
	void spongeMixinConfigsProblematicosDetectaInvalidInjectionException() throws Exception {
		SpongeMixinConfigsProblematicos v = new SpongeMixinConfigsProblematicos();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException: @Inject on mymod.mixins.json:MixinTarget found no valid canonical targets", 10);
		assertTrue(v.activado());
	}

	@Test
	void spongeMixinConfigsProblematicosDetectaMixinTransformerError() throws Exception {
		SpongeMixinConfigsProblematicos v = new SpongeMixinConfigsProblematicos();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError: Mixin [mymod.mixins.json] failed to apply", 10);
		assertTrue(v.activado());
	}

	@Test
	void spongeMixinConfigsProblematicosDetectaFailedDuringApply() throws Exception {
		SpongeMixinConfigsProblematicos v = new SpongeMixinConfigsProblematicos();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"Mixin [mymod.mixins.json:MixinTarget] FAILED during APPLY", 10);
		assertTrue(v.activado());
	}

	@Test
	void spongeMixinConfigsProblematicosNoSeActivaConMixinJsonNormal() throws Exception {
		// .mixins.json en metadatos de frame NO es un error
		SpongeMixinConfigsProblematicos v = new SpongeMixinConfigsProblematicos();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"at net.minecraft.class_123.method_1234(class_123.java:56) [mymod.mixins.json]", 10);
		assertFalse(v.activado(), "Mixin JSON en metadatos de frame no debe activar.");
	}

	@Test
	void spongeMixinConfigsProblematicosNoSeActivaConLineaNormal() throws Exception {
		SpongeMixinConfigsProblematicos v = new SpongeMixinConfigsProblematicos();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Mixin config loaded", 1);
		assertFalse(v.activado());
	}

	@Test
	void spongeMixinConfigsProblematicosNoSeActivaConLineaVacia() throws Exception {
		SpongeMixinConfigsProblematicos v = new SpongeMixinConfigsProblematicos();
		v.verificarPorLinea(ConsolaPrueba.crear(), "", 1);
		assertFalse(v.activado());
	}

	@Test
	void spongeMixinConfigsProblematicosIdentidad() {
		SpongeMixinConfigsProblematicos v = new SpongeMixinConfigsProblematicos();
		assertEquals("spongemixin_configs_problematicos", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof SpongeMixinConfigsProblematicos);
	}

	// ==================== LanzerDesAnimado ====================

	@Test
	void lanzerDesAnimadoNoSeActivaSinEstado() {
		// LanzerDesAnimado checks Statics.lanzer_del_app against a JSON file
		// It does not use verificarPorLinea for detection
		LanzerDesAnimado v = new LanzerDesAnimado();
		assertFalse(v.activado());
	}

	@Test
	void lanzerDesAnimadoNoSeActivaConLineaNormal() throws Exception {
		LanzerDesAnimado v = new LanzerDesAnimado();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Launcher started", 1);
		assertFalse(v.activado());
	}

	@Test
	void lanzerDesAnimadoIdentidad() {
		LanzerDesAnimado v = new LanzerDesAnimado();
		assertEquals("lanzer_desanimado", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof LanzerDesAnimado);
	}

	// ==================== LanzerNoAnimado ====================

	@Test
	void lanzerNoAnimadoNoSeActivaSinEstado() {
		// LanzerNoAnimado checks Statics.lanzer_del_app against a JSON file
		// It does not use verificarPorLinea for detection
		LanzerNoAnimado v = new LanzerNoAnimado();
		assertFalse(v.activado());
	}

	@Test
	void lanzerNoAnimadoNoSeActivaConLineaNormal() throws Exception {
		LanzerNoAnimado v = new LanzerNoAnimado();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Launcher running", 1);
		assertFalse(v.activado());
	}

	@Test
	void lanzerNoAnimadoIdentidad() {
		LanzerNoAnimado v = new LanzerNoAnimado();
		assertEquals("lanzer_no_animado", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof LanzerNoAnimado);
	}

	// ==================== TienesModDesAnimado ====================

	@Test
	void modDesAnimadoNoSeActivaSinEstado() {
		// TienesModDesAnimado checks Statics.lanzer_del_app against a JSON file
		// It does not use verificarPorLinea for detection
		TienesModDesAnimado v = new TienesModDesAnimado();
		assertFalse(v.activado());
	}

	@Test
	void modDesAnimadoNoSeActivaConLineaNormal() throws Exception {
		TienesModDesAnimado v = new TienesModDesAnimado();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Mod loaded", 1);
		assertFalse(v.activado());
	}

	@Test
	void modDesAnimadoIdentidad() {
		TienesModDesAnimado v = new TienesModDesAnimado();
		assertEquals("tienes_mod_desanimado", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof TienesModDesAnimado);
	}

	// ==================== FaltaModAnimado ====================

	@Test
	void faltaModAnimadoNoSeActivaSinEstado() {
		// FaltaModAnimado checks Statics.lanzer_del_app against a JSON file
		// It does not use verificarPorLinea for detection
		FaltaModAnimado v = new FaltaModAnimado();
		assertFalse(v.activado());
	}

	@Test
	void faltaModAnimadoNoSeActivaConLineaNormal() throws Exception {
		FaltaModAnimado v = new FaltaModAnimado();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: All mods loaded", 1);
		assertFalse(v.activado());
	}

	@Test
	void faltaModAnimadoIdentidad() {
		FaltaModAnimado v = new FaltaModAnimado();
		assertEquals("falta_mod_animado", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof FaltaModAnimado);
	}
}
