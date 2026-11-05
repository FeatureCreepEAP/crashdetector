package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.ErrorContextoOpenGL;
import com.asbestosstar.crashdetector.analizador.general.ErrorDeEnlaceInsatisfecho;
import com.asbestosstar.crashdetector.analizador.general.ErrorStackSmashingDetected;
import com.asbestosstar.crashdetector.analizador.general.ErrorVerificacionBytecode;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de ErrorDeEnlaceInsatisfecho, ErrorStackSmashingDetected,
 * ErrorVerificacionBytecode y ErrorContextoOpenGL.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.ErrorDeEnlaceInsatisfecho",
		"com.asbestosstar.crashdetector.analizador.general.ErrorStackSmashingDetected",
		"com.asbestosstar.crashdetector.analizador.general.ErrorVerificacionBytecode",
		"com.asbestosstar.crashdetector.analizador.general.ErrorContextoOpenGL" },
		nota = "Cubre deteccion de errores JVM nativos, stack smashing, bytecode verification y OpenGL context")
class PruebaErroresJVM {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ErrorDeEnlaceInsatisfecho ====================

	@Test
	void enlaceInsatisfechoDetectaBibliotecaFaltante() throws Exception {
		ErrorDeEnlaceInsatisfecho v = new ErrorDeEnlaceInsatisfecho();
		String linea = "java.lang.UnsatisfiedLinkError: Failed to locate library: liblwjgl64.so";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 5);
		assertTrue(v.activado());
		assertTrue(v.mensaje().contains("liblwjgl64.so"));
	}

	@Test
	void enlaceInsatisfechoDetectaBibliotecaWindows() throws Exception {
		ErrorDeEnlaceInsatisfecho v = new ErrorDeEnlaceInsatisfecho();
		String linea = "java.lang.UnsatisfiedLinkError: Failed to locate library: opengl32.dll";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 10);
		assertTrue(v.activado());
	}

	@Test
	void enlaceInsatisfechoIgnoraVulkanProbe() throws Exception {
		ErrorDeEnlaceInsatisfecho v = new ErrorDeEnlaceInsatisfecho();
		String linea = "java.lang.UnsatisfiedLinkError: Failed to locate library: Vulkan Probe test";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertFalse(v.activado(), "Vulkan Probe debe ser ignorado.");
	}

	@Test
	void enlaceInsatisfechoNoSeActivaConLineaNormal() throws Exception {
		ErrorDeEnlaceInsatisfecho v = new ErrorDeEnlaceInsatisfecho();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Loading libraries", 1);
		assertFalse(v.activado());
	}

	@Test
	void enlaceInsatisfechoIdentidad() {
		ErrorDeEnlaceInsatisfecho v = new ErrorDeEnlaceInsatisfecho();
		assertEquals("enlace_insatisfecho", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorDeEnlaceInsatisfecho);
		assertEquals(700.0f, v.prioridad());
	}

	// ==================== ErrorStackSmashingDetected ====================

	@Test
	void stackSmashingDetectaError() throws Exception {
		ErrorStackSmashingDetected v = new ErrorStackSmashingDetected();
		String linea = "*** stack smashing detected ***: terminated";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 100);
		assertTrue(v.activado());
	}

	@Test
	void stackSmashingNoSeActivaConLineaNormal() throws Exception {
		ErrorStackSmashingDetected v = new ErrorStackSmashingDetected();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Stack size is normal", 1);
		assertFalse(v.activado());
	}

	@Test
	void stackSmashingIdentidad() {
		ErrorStackSmashingDetected v = new ErrorStackSmashingDetected();
		assertEquals("error_stack_smashing_detected", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorStackSmashingDetected);
		assertEquals(1000.0f, v.prioridad());
	}

	// ==================== ErrorVerificacionBytecode ====================

	@Test
	void verifyErrorDetectaError() throws Exception {
		ErrorVerificacionBytecode v = new ErrorVerificacionBytecode();
		String linea = "java.lang.VerifyError: Bad type on operand stack";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 50);
		assertTrue(v.activado());
	}

	@Test
	void verifyErrorDetectaUbicacionYRazon() throws Exception {
		ErrorVerificacionBytecode v = new ErrorVerificacionBytecode();
		v.verificarPorLinea(ConsolaPrueba.crear(), "java.lang.VerifyError", 10);
		assertTrue(v.activado());
		v.verificarPorLinea(ConsolaPrueba.crear(), "Location:", 11);
		v.verificarPorLinea(ConsolaPrueba.crear(), "com/example/ModClass.init()V @ 10", 12);
		v.verificarPorLinea(ConsolaPrueba.crear(), "Reason:", 13);
		v.verificarPorLinea(ConsolaPrueba.crear(), "Type 'com/example/BadType' is not assignable to 'java/lang/Object'", 14);
		String mensaje = v.mensaje();
		assertTrue(mensaje.contains("ModClass"));
	}

	@Test
	void verifyErrorNoSeActivaConLineaNormal() throws Exception {
		ErrorVerificacionBytecode v = new ErrorVerificacionBytecode();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Bytecode verification passed", 1);
		assertFalse(v.activado());
	}

	@Test
	void verifyErrorIdentidad() {
		ErrorVerificacionBytecode v = new ErrorVerificacionBytecode();
		assertEquals("error_verificacion_bytecode", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorVerificacionBytecode);
		assertEquals(1750.0f, v.prioridad());
	}

	// ==================== ErrorContextoOpenGL ====================

	@Test
	void openglContextDetectaEnUnaLinea() throws Exception {
		ErrorContextoOpenGL v = new ErrorContextoOpenGL();
		String linea = "FATAL ERROR in native method: No context is current or a function that is not available in the current context was called";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 200);
		assertTrue(v.activado());
	}

	@Test
	void openglContextDetectaEnDosLineas() throws Exception {
		ErrorContextoOpenGL v = new ErrorContextoOpenGL();
		v.verificarPorLinea(ConsolaPrueba.crear(), "FATAL ERROR in native method: some details", 100);
		assertFalse(v.activado(), "Solo la cabecera no debe activar.");
		v.verificarPorLinea(ConsolaPrueba.crear(), "No context is current or a function that is not available in the current context was called", 101);
		assertTrue(v.activado());
	}

	@Test
	void openglContextNoSeActivaConCabeceraAislada() throws Exception {
		ErrorContextoOpenGL v = new ErrorContextoOpenGL();
		v.verificarPorLinea(ConsolaPrueba.crear(), "FATAL ERROR in native method: something else", 10);
		v.verificarPorLinea(ConsolaPrueba.crear(), "Some other line", 11);
		assertFalse(v.activado(), "La cabecera solo vive una linea.");
	}

	@Test
	void openglContextNoSeActivaConLineaNormal() throws Exception {
		ErrorContextoOpenGL v = new ErrorContextoOpenGL();
		v.verificarPorLinea(ConsolaPrueba.crear(), "OpenGL context created successfully", 1);
		assertFalse(v.activado());
	}

	@Test
	void openglContextIdentidad() {
		ErrorContextoOpenGL v = new ErrorContextoOpenGL();
		assertEquals("contexto_opengl", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorContextoOpenGL);
		assertEquals(1050.0f, v.prioridad());
	}

	@Test
	void openglContextAceptaConsolaNula() {
		ErrorContextoOpenGL v = new ErrorContextoOpenGL();
		// No debe lanzar NullPointerException
		v.verificarPorLinea(null, "FATAL ERROR in native method: No context is current or a function that is not available in the current context was called", 1);
		// Con consola nula, activar() se llama pero no agrega enlace
		// El comportamiento depende de la implementacion
	}
}
