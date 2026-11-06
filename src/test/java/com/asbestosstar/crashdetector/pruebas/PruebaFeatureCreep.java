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
import com.asbestosstar.crashdetector.analizador.general.AdvertenciaHotSpotAttachFeatureCreep;
import com.asbestosstar.crashdetector.analizador.general.ErrorDependenciaModuloFeatureCreep;
import com.asbestosstar.crashdetector.analizador.general.ErrorVersionModuloFeatureCreep;
import com.asbestosstar.crashdetector.analizador.general.ErrorMixinBootstrapFeatureCreep;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de las verificaciones de FeatureCreep.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.AdvertenciaHotSpotAttachFeatureCreep",
		"com.asbestosstar.crashdetector.analizador.general.ErrorDependenciaModuloFeatureCreep",
		"com.asbestosstar.crashdetector.analizador.general.ErrorVersionModuloFeatureCreep",
		"com.asbestosstar.crashdetector.analizador.general.ErrorMixinBootstrapFeatureCreep" },
		nota = "Cubre deteccion de errores de FeatureCreep y falsos positivos")
class PruebaFeatureCreep {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== AdvertenciaHotSpotAttachFeatureCreep ====================

	@Test
	void hotspotAttachDetectaUnsupportedOS() throws Exception {
		AdvertenciaHotSpotAttachFeatureCreep v = new AdvertenciaHotSpotAttachFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Loading featurecreep-loader",
			"java.io.IOException: Direct HotSpot attach is unsupported on Solaris 5.11"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void hotspotAttachDetectaDirectAttachFailed() throws Exception {
		AdvertenciaHotSpotAttachFeatureCreep v = new AdvertenciaHotSpotAttachFeatureCreep();
		String[] lineas = {
			"[BootstrapCommon] FeatureCreep direct attach failed: java.io.IOException",
			"[FeatureCreep] Continuing without instrumentation"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void hotspotAttachFalsoPositivoSinFeatureCreep() throws Exception {
		// El mensaje de error sin contexto de FeatureCreep no debe activar
		AdvertenciaHotSpotAttachFeatureCreep v = new AdvertenciaHotSpotAttachFeatureCreep();
		String linea = "java.io.IOException: Direct HotSpot attach is unsupported on Unknown";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void hotspotAttachFalsoPositivoAttachExito() throws Exception {
		// "supports" no es lo mismo que "is unsupported"
		AdvertenciaHotSpotAttachFeatureCreep v = new AdvertenciaHotSpotAttachFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] HotSpot attach supported on this platform",
			"[FeatureCreep] Agent loaded successfully"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void hotspotAttachEsAdvertencia() {
		AdvertenciaHotSpotAttachFeatureCreep v = new AdvertenciaHotSpotAttachFeatureCreep();
		assertEquals(Criticalidad.ADVERTENCIA, v.nivel_de_criticalidad());
	}

	@Test
	void hotspotAttachPrioridadBaja() {
		AdvertenciaHotSpotAttachFeatureCreep v = new AdvertenciaHotSpotAttachFeatureCreep();
		assertTrue(v.prioridad() < 1000f, "La prioridad debe ser baja para una advertencia");
	}

	@Test
	void hotspotAttachNuevaInstancia() {
		AdvertenciaHotSpotAttachFeatureCreep v = new AdvertenciaHotSpotAttachFeatureCreep();
		assertNotNull(v.nueva());
	}

	@Test
	void hotspotAttachIdUnico() {
		AdvertenciaHotSpotAttachFeatureCreep v = new AdvertenciaHotSpotAttachFeatureCreep();
		assertEquals("advertencia_hotspot_attach_featurecreep", v.id());
	}

	// ==================== ErrorDependenciaModuloFeatureCreep ====================

	@Test
	void dependenciaModuloDetectaIncompatibleEvent() throws Exception {
		ErrorDependenciaModuloFeatureCreep v = new ErrorDependenciaModuloFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Loading modules...",
			"featurecreep.loader.eventviewer.listeners.IncompatibleEventListenerMethodReference: method mismatch"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void dependenciaModuloDetectaUnmet() throws Exception {
		ErrorDependenciaModuloFeatureCreep v = new ErrorDependenciaModuloFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Resolving module graph...",
			"[FeatureCreep] Unmet dependency: module A requires module B"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void dependenciaModuloDetectaCyclic() throws Exception {
		ErrorDependenciaModuloFeatureCreep v = new ErrorDependenciaModuloFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Checking dependencies...",
			"[FeatureCreep] Cyclic dependency detected between modules"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void dependenciaModuloFalsoPositivoSinFeatureCreep() throws Exception {
		ErrorDependenciaModuloFeatureCreep v = new ErrorDependenciaModuloFeatureCreep();
		String linea = "Some unmet dependency in another system";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void dependenciaModuloFalsoPositivoLineaNormal() throws Exception {
		ErrorDependenciaModuloFeatureCreep v = new ErrorDependenciaModuloFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] All modules loaded successfully",
			"[FeatureCreep] Game starting..."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void dependenciaModuloEsError() {
		ErrorDependenciaModuloFeatureCreep v = new ErrorDependenciaModuloFeatureCreep();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void dependenciaModuloIdUnico() {
		ErrorDependenciaModuloFeatureCreep v = new ErrorDependenciaModuloFeatureCreep();
		assertEquals("error_dependencia_modulo_featurecreep", v.id());
	}

	// ==================== ErrorVersionModuloFeatureCreep ====================

	@Test
	void versionModuloDetectaFCModuleCompatibility() throws Exception {
		ErrorVersionModuloFeatureCreep v = new ErrorVersionModuloFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Checking module versions...",
			"featurecreep.loader.FCModuleCompatibility: version mismatch for module X"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void versionModuloDetectaRangeMismatch() throws Exception {
		ErrorVersionModuloFeatureCreep v = new ErrorVersionModuloFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Validating versions...",
			"featurecreep.loader.FCModuleCompatibility.rangeMismatch: expected 1.21, found 1.20"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void versionModuloFalsoPositivoSinFeatureCreep() throws Exception {
		ErrorVersionModuloFeatureCreep v = new ErrorVersionModuloFeatureCreep();
		String linea = "FCModuleCompatibility check passed";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void versionModuloFalsoPositivoLineaNormal() throws Exception {
		ErrorVersionModuloFeatureCreep v = new ErrorVersionModuloFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] All module versions compatible",
			"[FeatureCreep] Starting game..."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void versionModuloEsError() {
		ErrorVersionModuloFeatureCreep v = new ErrorVersionModuloFeatureCreep();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void versionModuloIdUnico() {
		ErrorVersionModuloFeatureCreep v = new ErrorVersionModuloFeatureCreep();
		assertEquals("error_version_modulo_featurecreep", v.id());
	}

	// ==================== ErrorMixinBootstrapFeatureCreep ====================

	@Test
	void mixinBootstrapDetectaStandaloneFailed() throws Exception {
		ErrorMixinBootstrapFeatureCreep v = new ErrorMixinBootstrapFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Initializing Mixin...",
			"[FeatureCreep] Standalone Mixin bootstrap failed: java.lang.NoClassDefFoundError"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void mixinBootstrapDetectaTransformFailed() throws Exception {
		ErrorMixinBootstrapFeatureCreep v = new ErrorMixinBootstrapFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Loading mixins...",
			"[FeatureCreep] Mixin transform failed for net/minecraft/Block"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void mixinBootstrapDetectaRequireInstrumentation() throws Exception {
		ErrorMixinBootstrapFeatureCreep v = new ErrorMixinBootstrapFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Checking Mixin requirements...",
			"[FeatureCreep] Vanilla Mixin configs require startup Instrumentation"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void mixinBootstrapDetectaRuntimeNotInstalled() throws Exception {
		ErrorMixinBootstrapFeatureCreep v = new ErrorMixinBootstrapFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Looking for Mixin runtime...",
			"[FeatureCreep] Standalone Mixin runtime is not installed under /path/to/libs"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void mixinBootstrapFalsoPositivoSinFeatureCreep() throws Exception {
		ErrorMixinBootstrapFeatureCreep v = new ErrorMixinBootstrapFeatureCreep();
		String linea = "Standalone Mixin bootstrap failed in another context";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void mixinBootstrapFalsoPositivoExito() throws Exception {
		ErrorMixinBootstrapFeatureCreep v = new ErrorMixinBootstrapFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Mixin initialized successfully",
			"[FeatureCreep] All transforms applied"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void mixinBootstrapFalsoPositivoRiftWarning() throws Exception {
		// El warning de Rift Mixin bootstrap failed es solo un warning, no un error fatal
		// pero si aparece con FeatureCreep contexto, se detecta
		ErrorMixinBootstrapFeatureCreep v = new ErrorMixinBootstrapFeatureCreep();
		String[] lineas = {
			"[FeatureCreep] Rift Mixin bootstrap failed: connection refused",
			"[FeatureCreep] Continuing without Rift Mixin"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		// No se activa porque "Rift Mixin bootstrap failed" no coincide con los patrones exactos
		assertFalse(v.activado());
	}

	@Test
	void mixinBootstrapEsError() {
		ErrorMixinBootstrapFeatureCreep v = new ErrorMixinBootstrapFeatureCreep();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void mixinBootstrapIdUnico() {
		ErrorMixinBootstrapFeatureCreep v = new ErrorMixinBootstrapFeatureCreep();
		assertEquals("error_mixin_bootstrap_featurecreep", v.id());
	}

	// ==================== Pruebas de identidad y mensajes ====================

	@Test
	void hotspotAttachMensajeNoVacio() {
		AdvertenciaHotSpotAttachFeatureCreep v = new AdvertenciaHotSpotAttachFeatureCreep();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
	}

	@Test
	void hotspotAttachNombreNoVacio() {
		AdvertenciaHotSpotAttachFeatureCreep v = new AdvertenciaHotSpotAttachFeatureCreep();
		assertNotNull(v.nombre());
		assertFalse(v.nombre().isEmpty());
	}

	@Test
	void dependenciaModuloMensajeNoVacio() {
		ErrorDependenciaModuloFeatureCreep v = new ErrorDependenciaModuloFeatureCreep();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
	}

	@Test
	void versionModuloMensajeNoVacio() {
		ErrorVersionModuloFeatureCreep v = new ErrorVersionModuloFeatureCreep();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
	}

	@Test
	void mixinBootstrapMensajeNoVacio() {
		ErrorMixinBootstrapFeatureCreep v = new ErrorMixinBootstrapFeatureCreep();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
	}

	@Test
	void hotspotAttachPatronesRapidos() {
		AdvertenciaHotSpotAttachFeatureCreep v = new AdvertenciaHotSpotAttachFeatureCreep();
		String[] patrones = v.patronesRapidos();
		assertTrue(patrones.length > 0);
	}

	@Test
	void dependenciaModuloPatronesRapidos() {
		ErrorDependenciaModuloFeatureCreep v = new ErrorDependenciaModuloFeatureCreep();
		String[] patrones = v.patronesRapidos();
		assertTrue(patrones.length > 0);
	}

	@Test
	void versionModuloPatronesRapidos() {
		ErrorVersionModuloFeatureCreep v = new ErrorVersionModuloFeatureCreep();
		String[] patrones = v.patronesRapidos();
		assertTrue(patrones.length > 0);
	}

	@Test
	void mixinBootstrapPatronesRapidos() {
		ErrorMixinBootstrapFeatureCreep v = new ErrorMixinBootstrapFeatureCreep();
		String[] patrones = v.patronesRapidos();
		assertTrue(patrones.length > 0);
	}
}
