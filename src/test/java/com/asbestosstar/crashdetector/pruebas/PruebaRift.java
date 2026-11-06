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
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorModDuplicadoRift;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorRiftModJSONRift;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorListenerRift;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorTransformadorAccesoRift;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaJarNoValidoRift;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de las verificaciones de Rift.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorModDuplicadoRift",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorRiftModJSONRift",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorListenerRift",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorTransformadorAccesoRift",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaJarNoValidoRift" },
		nota = "Cubre deteccion de errores de Rift y falsos positivos")
class PruebaRift {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ErrorModDuplicadoRift ====================

	@Test
	void modDuplicadoDetectaDuplicateMod() throws Exception {
		ErrorModDuplicadoRift v = new ErrorModDuplicadoRift();
		String[] lineas = {
			"[RiftLoader] Loading Rift mods",
			"java.lang.RuntimeException: Duplicate mod examplemod:\r\n - /mods/mod1.jar\r\n - /mods/mod2.jar"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void modDuplicadoDetectaConRiftLoader() throws Exception {
		ErrorModDuplicadoRift v = new ErrorModDuplicadoRift();
		String[] lineas = {
			"[RiftLoader] Searching for mods",
			"Duplicate mod sodium:"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void modDuplicadoFalsoPositivoSinRift() throws Exception {
		ErrorModDuplicadoRift v = new ErrorModDuplicadoRift();
		String linea = "Duplicate mod something in another loader";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void modDuplicadoFalsoPositivoSinDuplicate() throws Exception {
		ErrorModDuplicadoRift v = new ErrorModDuplicadoRift();
		String[] lineas = {
			"[RiftLoader] Loading mods",
			"[RiftLoader] Loaded 5 mods successfully"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void modDuplicadoEsError() {
		ErrorModDuplicadoRift v = new ErrorModDuplicadoRift();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void modDuplicadoPrioridad() {
		ErrorModDuplicadoRift v = new ErrorModDuplicadoRift();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void modDuplicadoNuevaInstancia() {
		ErrorModDuplicadoRift v = new ErrorModDuplicadoRift();
		assertNotNull(v.nueva());
	}

	@Test
	void modDuplicadoIdUnico() {
		ErrorModDuplicadoRift v = new ErrorModDuplicadoRift();
		assertEquals("error_mod_duplicado_rift", v.id());
	}

	// ==================== ErrorRiftModJSONRift ====================

	@Test
	void riftModJSONDetectaCouldNotRead() throws Exception {
		ErrorRiftModJSONRift v = new ErrorRiftModJSONRift();
		String[] lineas = {
			"[RiftLoader] Loading Rift mods",
			"java.lang.RuntimeException: Could not read riftmod.json in /mods/broken-mod.jar"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void riftModJSONDetectaConRift() throws Exception {
		ErrorRiftModJSONRift v = new ErrorRiftModJSONRift();
		String[] lineas = {
			"[RiftLoader] Searching for mods",
			"Could not read riftmod.json in /mods/invalid.jar"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void riftModJSONFalsoPositivoSinRift() throws Exception {
		// "riftmod.json" contiene "rift", por lo que se activa el contexto.
		// Para probar falsos positivos, usamos un mensaje que no contenga el patron de error.
		ErrorRiftModJSONRift v = new ErrorRiftModJSONRift();
		String linea = "Some other error message without the pattern";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void riftModJSONFalsoPositivoSinError() throws Exception {
		ErrorRiftModJSONRift v = new ErrorRiftModJSONRift();
		String[] lineas = {
			"[RiftLoader] Loading mods",
			"[RiftLoader] riftmod.json loaded successfully"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void riftModJSONEsError() {
		ErrorRiftModJSONRift v = new ErrorRiftModJSONRift();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void riftModJSONPrioridad() {
		ErrorRiftModJSONRift v = new ErrorRiftModJSONRift();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void riftModJSONNuevaInstancia() {
		ErrorRiftModJSONRift v = new ErrorRiftModJSONRift();
		assertNotNull(v.nueva());
	}

	@Test
	void riftModJSONIdUnico() {
		ErrorRiftModJSONRift v = new ErrorRiftModJSONRift();
		assertEquals("error_riftmod_json_rift", v.id());
	}

	// ==================== ErrorListenerRift ====================

	@Test
	void listenerDetectaFailedToFind() throws Exception {
		ErrorListenerRift v = new ErrorListenerRift();
		String[] lineas = {
			"[RiftLoader] Initializing mods",
			"java.lang.RuntimeException: Failed to find listener class org.example.ModListener"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void listenerDetectaConRift() throws Exception {
		ErrorListenerRift v = new ErrorListenerRift();
		String[] lineas = {
			"[RiftLoader] Loading mods",
			"Failed to find listener class com.test.Listener"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void listenerFalsoPositivoSinRift() throws Exception {
		ErrorListenerRift v = new ErrorListenerRift();
		String linea = "Failed to find listener class in some other context";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void listenerFalsoPositivoSinError() throws Exception {
		ErrorListenerRift v = new ErrorListenerRift();
		String[] lineas = {
			"[RiftLoader] Loading mods",
			"[RiftLoader] Listener class loaded successfully"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void listenerEsError() {
		ErrorListenerRift v = new ErrorListenerRift();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void listenerPrioridad() {
		ErrorListenerRift v = new ErrorListenerRift();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void listenerNuevaInstancia() {
		ErrorListenerRift v = new ErrorListenerRift();
		assertNotNull(v.nueva());
	}

	@Test
	void listenerIdUnico() {
		ErrorListenerRift v = new ErrorListenerRift();
		assertEquals("error_listener_rift", v.id());
	}

	// ==================== ErrorTransformadorAccesoRift ====================

	@Test
	void transformadorAccesoDetectaFailedInit() throws Exception {
		ErrorTransformadorAccesoRift v = new ErrorTransformadorAccesoRift();
		String[] lineas = {
			"[RiftLoader] Initializing access transformers",
			"java.lang.RuntimeException: Failed to initialize access transformers"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void transformadorAccesoDetectaConRift() throws Exception {
		ErrorTransformadorAccesoRift v = new ErrorTransformadorAccesoRift();
		String[] lineas = {
			"[RiftLoader] Loading mods",
			"Failed to initialize access transformers"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void transformadorAccesoFalsoPositivoSinRift() throws Exception {
		ErrorTransformadorAccesoRift v = new ErrorTransformadorAccesoRift();
		String linea = "Failed to initialize access transformers in another loader";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void transformadorAccesoFalsoPositivoSinError() throws Exception {
		ErrorTransformadorAccesoRift v = new ErrorTransformadorAccesoRift();
		String[] lineas = {
			"[RiftLoader] Loading mods",
			"[RiftLoader] Access transformers initialized successfully"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void transformadorAccesoEsError() {
		ErrorTransformadorAccesoRift v = new ErrorTransformadorAccesoRift();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void transformadorAccesoPrioridad() {
		ErrorTransformadorAccesoRift v = new ErrorTransformadorAccesoRift();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void transformadorAccesoNuevaInstancia() {
		ErrorTransformadorAccesoRift v = new ErrorTransformadorAccesoRift();
		assertNotNull(v.nueva());
	}

	@Test
	void transformadorAccesoIdUnico() {
		ErrorTransformadorAccesoRift v = new ErrorTransformadorAccesoRift();
		assertEquals("error_transformador_acceso_rift", v.id());
	}

	// ==================== AdvertenciaJarNoValidoRift ====================

	@Test
	void jarNoValidoDetectaCouldNotReadAsJar() throws Exception {
		AdvertenciaJarNoValidoRift v = new AdvertenciaJarNoValidoRift();
		String[] lineas = {
			"[RiftLoader] Searching for mods in /mods",
			"[RiftLoader] Could not read file /mods/corrupted.jar as a jar file"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void jarNoValidoDetectaConRift() throws Exception {
		AdvertenciaJarNoValidoRift v = new AdvertenciaJarNoValidoRift();
		String[] lineas = {
			"[RiftLoader] Loading mods",
			"Could not read file broken.jar as a jar file"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void jarNoValidoFalsoPositivoSinRift() throws Exception {
		AdvertenciaJarNoValidoRift v = new AdvertenciaJarNoValidoRift();
		String linea = "Could not read file something.jar as a jar file";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void jarNoValidoFalsoPositivoSinAsJar() throws Exception {
		AdvertenciaJarNoValidoRift v = new AdvertenciaJarNoValidoRift();
		String[] lineas = {
			"[RiftLoader] Loading mods",
			"Could not read file /mods/mod.jar for another reason"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void jarNoValidoFalsoPositivoExito() throws Exception {
		AdvertenciaJarNoValidoRift v = new AdvertenciaJarNoValidoRift();
		String[] lineas = {
			"[RiftLoader] Loading mods",
			"[RiftLoader] Successfully read /mods/mod.jar"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void jarNoValidoEsAdvertencia() {
		AdvertenciaJarNoValidoRift v = new AdvertenciaJarNoValidoRift();
		assertEquals(Criticalidad.ADVERTENCIA, v.nivel_de_criticalidad());
	}

	@Test
	void jarNoValidoPrioridadBaja() {
		AdvertenciaJarNoValidoRift v = new AdvertenciaJarNoValidoRift();
		assertTrue(v.prioridad() < 1000f, "La prioridad debe ser baja para una advertencia");
	}

	@Test
	void jarNoValidoNuevaInstancia() {
		AdvertenciaJarNoValidoRift v = new AdvertenciaJarNoValidoRift();
		assertNotNull(v.nueva());
	}

	@Test
	void jarNoValidoIdUnico() {
		AdvertenciaJarNoValidoRift v = new AdvertenciaJarNoValidoRift();
		assertEquals("advertencia_jar_no_valido_rift", v.id());
	}

	// ==================== Null safety ====================

	@Test
	void modDuplicadoNullSafety() throws Exception {
		ErrorModDuplicadoRift v = new ErrorModDuplicadoRift();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void riftModJSONNullSafety() throws Exception {
		ErrorRiftModJSONRift v = new ErrorRiftModJSONRift();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void listenerNullSafety() throws Exception {
		ErrorListenerRift v = new ErrorListenerRift();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void transformadorAccesoNullSafety() throws Exception {
		ErrorTransformadorAccesoRift v = new ErrorTransformadorAccesoRift();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void jarNoValidoNullSafety() throws Exception {
		AdvertenciaJarNoValidoRift v = new AdvertenciaJarNoValidoRift();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}
}
