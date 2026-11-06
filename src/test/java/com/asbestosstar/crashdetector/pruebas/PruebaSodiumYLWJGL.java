package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorRenderSystemHiloIncorrectoSodium;
import com.asbestosstar.crashdetector.analizador.general.ErrorEnlazadorNoSoportado;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de ErrorRenderSystemHiloIncorrectoSodium y ErrorEnlazadorNoSoportado.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorRenderSystemHiloIncorrectoSodium",
		"com.asbestosstar.crashdetector.analizador.general.ErrorEnlazadorNoSoportado" },
		nota = "Cubre deteccion de RenderSystem hilo incorrecto con Sodium y enlazador general, y falsos positivos")
class PruebaSodiumYLWJGL {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ErrorRenderSystemHiloIncorrectoSodium ====================

	@Test
	void sodiumRenderSystemDetectaErrorDeHilo() throws Exception {
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		String[] lineas = {
			"[main/INFO]: Found mod file \"net.caffeinemc.sodium-neoforge-0.8.13+mc1.21.1-mod.jar\"",
			"java.lang.IllegalStateException: Rendersystem called from wrong thread"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola,
			"java.lang.IllegalStateException: Rendersystem called from wrong thread", 2);
		assertTrue(v.activado());
	}

	@Test
	void sodiumRenderSystemDetectaConSodiumModId() throws Exception {
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		String[] lineas = {
			"Sodium 0.8.13+mc1.21.1 (sodium)",
			"java.lang.IllegalStateException: Rendersystem called from wrong thread"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void sodiumRenderSystemDetectaConEmbeddium() throws Exception {
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		String[] lineas = {
			"[main/INFO]: Embeddium mod loaded",
			"java.lang.IllegalStateException: Rendersystem called from wrong thread"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void sodiumRenderSystemDetectaConRubidium() throws Exception {
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		String[] lineas = {
			"[main/INFO]: Rubidium mod loaded",
			"Rendersystem called from wrong thread"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void sodiumRenderSystemDetectaConNetCaffeinemc() throws Exception {
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		String[] lineas = {
			"net.caffeinemc.mods.sodium.service.SodiumServiceModLocator",
			"java.lang.IllegalStateException: Rendersystem called from wrong thread"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void sodiumRenderSystemDetectaConLogReal() throws Exception {
		// Simula el log real del usuario con SPARC/Solaris y llvmpipe
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		String[] lineas = {
			"[main/INFO]: JVM identified as OpenJDK Solaris SPARC OpenJDK 64-Bit Server VM 25.0.5+1",
			"[pool-2-thread-1/INFO]: GL info: llvmpipe (LLVM 13.0.1, 128 bits) GL version 4.6",
			"[main/INFO]: Found mod file \"net.caffeinemc.sodium-neoforge-0.8.13+mc1.21.1-mod.jar\"",
			"[main/INFO]: Sodium 0.8.13+mc1.21.1 (sodium)",
			"[main/WARN]: Failed to get system info for GL Caps",
			"java.lang.IllegalStateException: Rendersystem called from wrong thread"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		for (int i = 0; i < lineas.length; i++) {
			v.verificarPorLinea(consola, lineas[i], i + 1);
		}
		assertTrue(v.activado());
	}

	@Test
	void sodiumRenderSystemNoSeActivaSinContextoSodium() throws Exception {
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		String[] lineas = {
			"[main/INFO]: Some other mod loaded",
			"java.lang.IllegalStateException: Rendersystem called from wrong thread"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado(), "Sin contexto de Sodium no debe activar.");
	}

	@Test
	void sodiumRenderSystemFalsoPositivoLineaNormal() throws Exception {
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		v.verificarPorLinea(ConsolaPrueba.crear(),
			"INFO: Sodium configuration loaded successfully", 1);
		assertFalse(v.activado());
	}

	@Test
	void sodiumRenderSystemFalsoPositivoIllegalStateOtro() throws Exception {
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		String[] lineas = {
			"[main/INFO]: Sodium mod loaded",
			"java.lang.IllegalStateException: Block state is invalid"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado(), "IllegalStateException sin RenderSystem no debe activar.");
	}

	@Test
	void sodiumRenderSystemAceptaConsolaNula() {
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		v.verificarPorLinea(null, "Rendersystem called from wrong thread", 1);
		assertFalse(v.activado());
	}

	@Test
	void sodiumRenderSystemAceptaLineaNula() throws Exception {
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		v.verificarPorLinea(ConsolaPrueba.crear(), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void sodiumRenderSystemIdentidad() {
		ErrorRenderSystemHiloIncorrectoSodium v = new ErrorRenderSystemHiloIncorrectoSodium();
		assertEquals("error_render_system_hilo_incorrecto_sodium", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorRenderSystemHiloIncorrectoSodium);
		assertEquals(1600.0f, v.prioridad());
	}

	// ==================== ErrorEnlazadorNoSoportado ====================

	@Test
	void enlazadorNoSoportadoDetectaPlataformaNoSoportada() throws Exception {
		ErrorEnlazadorNoSoportado v = new ErrorEnlazadorNoSoportado();
		v.verificarPorLinea(ConsolaPrueba.crear(),
			"java.lang.UnsupportedOperationException: Platform does not support native linker", 42);
		assertTrue(v.activado());
	}

	@Test
	void enlazadorNoSoportadoDetectaSoloMensaje() throws Exception {
		ErrorEnlazadorNoSoportado v = new ErrorEnlazadorNoSoportado();
		v.verificarPorLinea(ConsolaPrueba.crear(),
			"Platform does not support native linker", 10);
		assertTrue(v.activado());
	}

	@Test
	void enlazadorNoSoportadoDetectaConBCCallDown() throws Exception {
		ErrorEnlazadorNoSoportado v = new ErrorEnlazadorNoSoportado();
		v.verificarPorLinea(ConsolaPrueba.crear(),
			"java.lang.UnsupportedOperationException at org.lwjgl.system.ffm.BCCallDown.<init>(BCCallDown.java:241)", 15);
		assertFalse(v.activado(), "BCCallDown solo no activa sin el mensaje principal.");
	}

	@Test
	void enlazadorNoSoportadoFalsoPositivoSoportado() throws Exception {
		ErrorEnlazadorNoSoportado v = new ErrorEnlazadorNoSoportado();
		v.verificarPorLinea(ConsolaPrueba.crear(),
			"INFO: Platform supports native linker", 1);
		assertFalse(v.activado(), "\"supports\" no es \"does not support\".");
	}

	@Test
	void enlazadorNoSoportadoFalsoPositivoLineaNormal() throws Exception {
		ErrorEnlazadorNoSoportado v = new ErrorEnlazadorNoSoportado();
		v.verificarPorLinea(ConsolaPrueba.crear(),
			"INFO: Loading native libraries", 1);
		assertFalse(v.activado());
	}

	@Test
	void enlazadorNoSoportadoFalsoPositivoLinkerOtro() throws Exception {
		ErrorEnlazadorNoSoportado v = new ErrorEnlazadorNoSoportado();
		v.verificarPorLinea(ConsolaPrueba.crear(),
			"INFO: Linker initialized successfully", 2);
		assertFalse(v.activado());
	}

	@Test
	void enlazadorNoSoportadoFalsoPositivoUnsupportedOtro() throws Exception {
		ErrorEnlazadorNoSoportado v = new ErrorEnlazadorNoSoportado();
		v.verificarPorLinea(ConsolaPrueba.crear(),
			"java.lang.UnsupportedOperationException: Method not implemented", 5);
		assertFalse(v.activado(), "UnsupportedOperationException sin linker no debe activar.");
	}

	@Test
	void enlazadorNoSoportadoAceptaConsolaNula() {
		ErrorEnlazadorNoSoportado v = new ErrorEnlazadorNoSoportado();
		v.verificarPorLinea(null, "Platform does not support native linker", 1);
		assertFalse(v.activado());
	}

	@Test
	void enlazadorNoSoportadoAceptaLineaNula() throws Exception {
		ErrorEnlazadorNoSoportado v = new ErrorEnlazadorNoSoportado();
		v.verificarPorLinea(ConsolaPrueba.crear(), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void enlazadorNoSoportadoIdentidad() {
		ErrorEnlazadorNoSoportado v = new ErrorEnlazadorNoSoportado();
		assertEquals("error_enlazador_no_soportado", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorEnlazadorNoSoportado);
		assertEquals(1550.0f, v.prioridad());
	}
}
