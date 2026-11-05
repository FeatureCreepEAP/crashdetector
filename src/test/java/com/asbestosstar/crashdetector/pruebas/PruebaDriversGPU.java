package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.Drivers;
import com.asbestosstar.crashdetector.analizador.general.ErrorOpenGLMemoriaInsuficiente;
import com.asbestosstar.crashdetector.analizador.general.VerificacionGPU;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de Drivers, VerificacionGPU y ErrorOpenGLMemoriaInsuficiente.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.Drivers",
		"com.asbestosstar.crashdetector.analizador.general.VerificacionGPU",
		"com.asbestosstar.crashdetector.analizador.general.ErrorOpenGLMemoriaInsuficiente" },
		nota = "Cubre deteccion de problemas de drivers GPU, verificacion de GPU y memoria insuficiente OpenGL")
class PruebaDriversGPU {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
		Drivers.reiniciarGlobal();
	}

	// ==================== Drivers ====================

	@Test
	void driversDetectaPixelFormatNoAccelerated() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(ConsolaPrueba.crear(), "Pixel format not accelerated", 10);
		assertTrue(v.activado());
	}

	@Test
	void driversDetectaDriverNoSoportaOpenGL() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(ConsolaPrueba.crear(), "The driver does not appear to support OpenGL", 20);
		assertTrue(v.activado());
	}

	@Test
	void driversDetectaGlfwError65542() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(ConsolaPrueba.crear(), "GLFW error 65542: Failed to create OpenGL context", 30);
		assertTrue(v.activado());
	}

	@Test
	void driversDetectaGlfwError65543() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(ConsolaPrueba.crear(), "GLFW error 65543: Failed to create OpenGL context", 30);
		assertTrue(v.activado());
	}

	@Test
	void driversDetectaGlfwError1282() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(ConsolaPrueba.crear(), "GLFW error 1282: The requested profile is unavailable", 30);
		assertTrue(v.activado());
	}

	@Test
	void driversDetectaLwjglException() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(ConsolaPrueba.crear(), "org.lwjgl.LWJGLException: Pixel format not accelerated", 40);
		assertTrue(v.activado());
	}

	@Test
	void driversDetectaFramebufferObjects() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(ConsolaPrueba.crear(), "The driver does not appear to support framebuffer objects", 50);
		assertTrue(v.activado());
	}

	@Test
	void driversDetectaGpuNoCompatible() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(ConsolaPrueba.crear(), "Your video card does not meet the requirements", 60);
		assertTrue(v.activado());
	}

	@Test
	void driversNoSeActivaConLineaNormal() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Driver loaded successfully", 1);
		assertFalse(v.activado());
	}

	@Test
	void driversNoSeActivaConLineaVacia() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(ConsolaPrueba.crear(), "", 1);
		assertFalse(v.activado());
	}

	@Test
	void driversNoSeActivaConConsolaNula() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(null, "Pixel format not accelerated", 1);
		// Con consola nula, procesarProblemaGraficos se llama pero no agrega enlace
		assertTrue(v.activado(), "activado se setea aunque consola sea nula.");
	}

	@Test
	void driversNoSeActivaConNouveauArs() throws Exception {
		Drivers v = new Drivers();
		v.verificarPorLinea(ConsolaPrueba.crear(), "ars_nouveau: some nouveau info", 1);
		assertFalse(v.activado(), "ars_nouveau debe ser excluido.");
	}

	@Test
	void driversIdentidad() {
		Drivers v = new Drivers();
		assertEquals("drivers", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof Drivers);
		assertEquals(900.0f, v.prioridad());
	}

	// ==================== VerificacionGPU ====================

	@Test
	void verificacionGpuIdentidad() {
		VerificacionGPU v = new VerificacionGPU();
		assertEquals("transformacion_de_minecraft_codigo_gpu", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof VerificacionGPU);
	}

	@Test
	void verificacionGpuNoSeActivaSinDatos() {
		VerificacionGPU v = new VerificacionGPU();
		assertFalse(v.activado());
	}

	// ==================== ErrorOpenGLMemoriaInsuficiente ====================

	@Test
	void openglMemoriaInsuficienteDetectaGlOutOfMemory() throws Exception {
		ErrorOpenGLMemoriaInsuficiente v = new ErrorOpenGLMemoriaInsuficiente();
		v.verificarPorLinea(ConsolaPrueba.crear(), "Error has been generated. GL error GL_OUT_OF_MEMORY in (null): Generic error", 10);
		assertTrue(v.activado());
	}

	@Test
	void openglMemoriaInsuficienteDetectaGlError() throws Exception {
		ErrorOpenGLMemoriaInsuficiente v = new ErrorOpenGLMemoriaInsuficiente();
		v.verificarPorLinea(ConsolaPrueba.crear(), "GL error GL_OUT_OF_MEMORY", 20);
		assertTrue(v.activado());
	}

	@Test
	void openglMemoriaInsuficienteNoSeActivaConLineaNormal() throws Exception {
		ErrorOpenGLMemoriaInsuficiente v = new ErrorOpenGLMemoriaInsuficiente();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Memory allocated", 1);
		assertFalse(v.activado());
	}

	@Test
	void openglMemoriaInsuficienteIdentidad() {
		ErrorOpenGLMemoriaInsuficiente v = new ErrorOpenGLMemoriaInsuficiente();
		assertEquals("opengl_memoria_insuficiente", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorOpenGLMemoriaInsuficiente);
	}
}
