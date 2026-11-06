package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorInicializacionModLoader;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorCargaModModLoader;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorDependenciaModuloModLoader;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaCanalInvalidoModLoader;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de las verificaciones de ModLoader (Risugami).
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorInicializacionModLoader",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorCargaModModLoader",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorDependenciaModuloModLoader",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaCanalInvalidoModLoader" },
		nota = "Cubre deteccion de errores de ModLoader y falsos positivos")
class PruebaModLoader {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ErrorInicializacionModLoader ====================

	@Test
	void inicializacionDetectaFailedToInitialize() throws Exception {
		ErrorInicializacionModLoader v = new ErrorInicializacionModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"java.lang.RuntimeException: ModLoader has failed to initialize."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void inicializacionDetectaConModLoaderContext() throws Exception {
		ErrorInicializacionModLoader v = new ErrorInicializacionModLoader();
		String[] lineas = {
			"[ModLoader] Loading mods",
			"ModLoader has failed to initialize."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void inicializacionDetectaConThrowable() throws Exception {
		ErrorInicializacionModLoader v = new ErrorInicializacionModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"Caused by: java.lang.NoSuchFieldException: field_modifiers",
			"ModLoader has failed to initialize."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		v.verificarPorLinea(consola, lineas[2], 3);
		assertTrue(v.activado());
	}

	@Test
	void inicializacionFalsoPositivoSinModLoader() throws Exception {
		ErrorInicializacionModLoader v = new ErrorInicializacionModLoader();
		String linea = "Some other loader has failed to initialize.";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void inicializacionFalsoPositivoSinError() throws Exception {
		ErrorInicializacionModLoader v = new ErrorInicializacionModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"ModLoader initialized successfully"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void inicializacionFalsoPositivoInicializando() throws Exception {
		ErrorInicializacionModLoader v = new ErrorInicializacionModLoader();
		String linea = "ModLoader 1.6.2 Initializing...";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void inicializacionEsError() {
		ErrorInicializacionModLoader v = new ErrorInicializacionModLoader();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void inicializacionPrioridad() {
		ErrorInicializacionModLoader v = new ErrorInicializacionModLoader();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void inicializacionNuevaInstancia() {
		ErrorInicializacionModLoader v = new ErrorInicializacionModLoader();
		assertNotNull(v.nueva());
	}

	@Test
	void inicializacionIdUnico() {
		ErrorInicializacionModLoader v = new ErrorInicializacionModLoader();
		assertEquals("error_inicializacion_modloader", v.id());
	}

	// ==================== ErrorCargaModModLoader ====================

	@Test
	void cargaModDetectaFailedToLoad() throws Exception {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"Failed to load mod from \"mod_BiomesOPlenty\""
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void cargaModDetectaConModLoaderContext() throws Exception {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		String[] lineas = {
			"[ModLoader] Loading mods",
			"Failed to load mod from \"mod_Example\""
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void cargaModDetectaConClassNotFoundException() throws Exception {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"java.lang.ClassNotFoundException: mod_BrokenMod",
			"Failed to load mod from \"mod_BrokenMod\""
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		v.verificarPorLinea(consola, lineas[2], 3);
		assertTrue(v.activado());
	}

	@Test
	void cargaModDetectaConNoClassDefFoundError() throws Exception {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		String[] lineas = {
			"ModLoader Initializing...",
			"java.lang.NoClassDefFoundError: net/minecraft/block/Block",
			"Failed to load mod from \"mod_OldMod\""
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		v.verificarPorLinea(consola, lineas[2], 3);
		assertTrue(v.activado());
	}

	@Test
	void cargaModFalsoPositivoSinModLoader() throws Exception {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		String linea = "Failed to load mod from some other context";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void cargaModFalsoPositivoSinError() throws Exception {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"Mod Initialized: BiomesOPlenty 4.0.0"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void cargaModFalsoPositivoModLoaded() throws Exception {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"Mod Loaded: ExampleMod 1.0"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void cargaModEsError() {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void cargaModPrioridad() {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void cargaModNuevaInstancia() {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		assertNotNull(v.nueva());
	}

	@Test
	void cargaModIdUnico() {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		assertEquals("error_carga_mod_modloader", v.id());
	}

	// ==================== ErrorDependenciaModuloModLoader ====================

	@Test
	void dependenciaDetectaMissingDependency() throws Exception {
		ErrorDependenciaModuloModLoader v = new ErrorDependenciaModuloModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"java.lang.Exception: mod_Thaumcraft is missing dependency: mod_ArchimedesShelves"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void dependenciaDetectaConModLoaderContext() throws Exception {
		ErrorDependenciaModuloModLoader v = new ErrorDependenciaModuloModLoader();
		String[] lineas = {
			"[ModLoader] Sorting mods",
			"mod_Example is missing dependency: mod_Required"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void dependenciaDetectaConRequiredAfter() throws Exception {
		ErrorDependenciaModuloModLoader v = new ErrorDependenciaModuloModLoader();
		String[] lineas = {
			"ModLoader Initializing...",
			"Exception: mod_B is missing dependency: mod_A"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void dependenciaFalsoPositivoSinModLoader() throws Exception {
		ErrorDependenciaModuloModLoader v = new ErrorDependenciaModuloModLoader();
		String linea = "Some mod is missing dependency: something";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void dependenciaFalsoPositivoSinError() throws Exception {
		ErrorDependenciaModuloModLoader v = new ErrorDependenciaModuloModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"Mod Loaded: ExampleMod 1.0"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void dependenciaFalsoPositivoDependenciaEnOtroContexto() throws Exception {
		ErrorDependenciaModuloModLoader v = new ErrorDependenciaModuloModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"Checking dependencies for mods..."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void dependenciaEsError() {
		ErrorDependenciaModuloModLoader v = new ErrorDependenciaModuloModLoader();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void dependenciaPrioridad() {
		ErrorDependenciaModuloModLoader v = new ErrorDependenciaModuloModLoader();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void dependenciaNuevaInstancia() {
		ErrorDependenciaModuloModLoader v = new ErrorDependenciaModuloModLoader();
		assertNotNull(v.nueva());
	}

	@Test
	void dependenciaIdUnico() {
		ErrorDependenciaModuloModLoader v = new ErrorDependenciaModuloModLoader();
		assertEquals("error_dependencia_modulo_modloader", v.id());
	}

	// ==================== AdvertenciaCanalInvalidoModLoader ====================

	@Test
	void canalInvalidoDetectaInvalidChannelName() throws Exception {
		AdvertenciaCanalInvalidoModLoader v = new AdvertenciaCanalInvalidoModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"java.lang.RuntimeException: Invalid channel name: this_is_a_very_long_channel. Must be less than 16 characters."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void canalInvalidoDetectaConModLoaderContext() throws Exception {
		AdvertenciaCanalInvalidoModLoader v = new AdvertenciaCanalInvalidoModLoader();
		String[] lineas = {
			"[ModLoader] Registering packet channels",
			"Invalid channel name: too_long_channel_name"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void canalInvalidoFalsoPositivoSinModLoader() throws Exception {
		AdvertenciaCanalInvalidoModLoader v = new AdvertenciaCanalInvalidoModLoader();
		String linea = "Invalid channel name in some other context";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void canalInvalidoFalsoPositivoSinError() throws Exception {
		AdvertenciaCanalInvalidoModLoader v = new AdvertenciaCanalInvalidoModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"Mod Loaded: ExampleMod 1.0"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void canalInvalidoFalsoPositivoCanalValido() throws Exception {
		AdvertenciaCanalInvalidoModLoader v = new AdvertenciaCanalInvalidoModLoader();
		String[] lineas = {
			"ModLoader 1.6.2 Initializing...",
			"Channel registered: ML_Channel"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void canalInvalidoEsAdvertencia() {
		AdvertenciaCanalInvalidoModLoader v = new AdvertenciaCanalInvalidoModLoader();
		assertEquals(Criticalidad.ADVERTENCIA, v.nivel_de_criticalidad());
	}

	@Test
	void canalInvalidoPrioridadBaja() {
		AdvertenciaCanalInvalidoModLoader v = new AdvertenciaCanalInvalidoModLoader();
		assertTrue(v.prioridad() < 1000f, "La prioridad debe ser baja para una advertencia");
	}

	@Test
	void canalInvalidoNuevaInstancia() {
		AdvertenciaCanalInvalidoModLoader v = new AdvertenciaCanalInvalidoModLoader();
		assertNotNull(v.nueva());
	}

	@Test
	void canalInvalidoIdUnico() {
		AdvertenciaCanalInvalidoModLoader v = new AdvertenciaCanalInvalidoModLoader();
		assertEquals("advertencia_canal_invalido_modloader", v.id());
	}

	// ==================== Null safety ====================

	@Test
	void inicializacionNullSafety() throws Exception {
		ErrorInicializacionModLoader v = new ErrorInicializacionModLoader();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void cargaModNullSafety() throws Exception {
		ErrorCargaModModLoader v = new ErrorCargaModModLoader();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void dependenciaNullSafety() throws Exception {
		ErrorDependenciaModuloModLoader v = new ErrorDependenciaModuloModLoader();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void canalInvalidoNullSafety() throws Exception {
		AdvertenciaCanalInvalidoModLoader v = new AdvertenciaCanalInvalidoModLoader();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}
}
