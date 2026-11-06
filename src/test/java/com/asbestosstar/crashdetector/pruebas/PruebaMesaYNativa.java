package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.BibliotecaNativaFaltante;
import com.asbestosstar.crashdetector.analizador.general.ProblemaMesaSoftwareRenderer;
import com.asbestosstar.crashdetector.idioma.Espanol;

@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.ProblemaMesaSoftwareRenderer",
		"com.asbestosstar.crashdetector.analizador.general.BibliotecaNativaFaltante" },
		nota = "Cubre deteccion de Mesa/llvmpipe software renderer y bibliotecas nativas faltantes, comunes en Solaris/Illumos y SPARC")
class PruebaMesaYNativa {

	@BeforeEach
	void reiniciar() {
		MonitorDePID.idioma = new Espanol();
		ProblemaMesaSoftwareRenderer.reiniciarGlobal();
		BibliotecaNativaFaltante.reiniciarGlobal();
	}

	// ==================== ProblemaMesaSoftwareRenderer ====================

	@Test
	void mesaDetectaLlvmpipeConCrashLwjgl() throws Exception {
		ProblemaMesaSoftwareRenderer v = new ProblemaMesaSoftwareRenderer();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"Graphics Vendor: Mesa", 1);
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"Backend API: llvmpipe (LLVM 15.0.6, 256 bits)", 2);
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"at org.lwjgl.system.Checks.check(Checks.java:60)", 3);
		assertTrue(v.activado(), "Debe activar con llvmpipe + crash de LWJGL.");
	}

	@Test
	void mesaDetectaLlvmpipeConGLFW() throws Exception {
		ProblemaMesaSoftwareRenderer v = new ProblemaMesaSoftwareRenderer();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"llvmpipe software renderer", 1);
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"at org.lwjgl.glfw.GLFW.nglfwSetPreeditCallback(GLFW.java:3000)", 2);
		assertTrue(v.activado(), "Debe activar con llvmpipe + GLFW nativo.");
	}

	@Test
	void mesaNoSeActivaConSoloLlvmpipe() throws Exception {
		ProblemaMesaSoftwareRenderer v = new ProblemaMesaSoftwareRenderer();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"Backend API: llvmpipe (LLVM 15.0.6, 256 bits)", 1);
		assertFalse(v.activado(), "Solo llvmpipe sin crash no debe activar.");
	}

	@Test
	void mesaNoSeActivaConSoloMesa() throws Exception {
		ProblemaMesaSoftwareRenderer v = new ProblemaMesaSoftwareRenderer();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"Graphics Vendor: Mesa", 1);
		assertFalse(v.activado(), "Solo Mesa sin llvmpipe ni crash no debe activar.");
	}

	@Test
	void mesaNoSeActivaConLineaNormal() throws Exception {
		ProblemaMesaSoftwareRenderer v = new ProblemaMesaSoftwareRenderer();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"INFO: OpenGL renderer initialized", 1);
		assertFalse(v.activado());
	}

	@Test
	void mesaNoSeActivaConLineaVacia() throws Exception {
		ProblemaMesaSoftwareRenderer v = new ProblemaMesaSoftwareRenderer();
		v.verificarPorLinea(ConsolaPrueba.crear(), "", 1);
		assertFalse(v.activado());
	}

	@Test
	void mesaNoSeActivaConNull() throws Exception {
		ProblemaMesaSoftwareRenderer v = new ProblemaMesaSoftwareRenderer();
		v.verificarPorLinea(ConsolaPrueba.crear(), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void mesaDeduplicacionGlobal() throws Exception {
		ProblemaMesaSoftwareRenderer v1 = new ProblemaMesaSoftwareRenderer();
		ProblemaMesaSoftwareRenderer v2 = new ProblemaMesaSoftwareRenderer();
		v1.verificarPorLinea(ConsolaPrueba.crear(), "llvmpipe", 1);
		v1.verificarPorLinea(ConsolaPrueba.crear(), "org.lwjgl.system.Checks.check", 2);
		assertTrue(v1.activado());
		v2.verificarPorLinea(ConsolaPrueba.crear(), "llvmpipe", 1);
		v2.verificarPorLinea(ConsolaPrueba.crear(), "org.lwjgl.system.Checks.check", 2);
		assertFalse(v2.activado(), "Deduplicacion global debe evitar segunda activacion.");
	}

	@Test
	void mesaIdentidad() {
		ProblemaMesaSoftwareRenderer v = new ProblemaMesaSoftwareRenderer();
		assertEquals("problema_mesa_software_renderer", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ProblemaMesaSoftwareRenderer);
		assertEquals(800.0f, v.prioridad());
	}

	// ==================== BibliotecaNativaFaltante ====================

	@Test
	void nativaDetectaNmemset() throws Exception {
		BibliotecaNativaFaltante v = new BibliotecaNativaFaltante();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.lang.UnsatisfiedLinkError: 'long org.lwjgl.system.libc.LibCString.nmemset(long, int, long)'", 1);
		assertTrue(v.activado());
		assertTrue(v.mensaje().contains("nmemset"));
	}

	@Test
	void nativaDetectaNdlopen() throws Exception {
		BibliotecaNativaFaltante v = new BibliotecaNativaFaltante();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.lang.UnsatisfiedLinkError: 'long org.lwjgl.system.linux.DynamicLinkLoader.ndlopen(java.lang.String, int)'",
				1);
		assertTrue(v.activado());
		assertTrue(v.mensaje().contains("ndlopen"));
	}

	@Test
	void nativaDetectaMemoryUtil() throws Exception {
		// Formato real de crash report en Solaris
		BibliotecaNativaFaltante v = new BibliotecaNativaFaltante();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.lang.UnsatisfiedLinkError: 'int org.lwjgl.system.MemoryUtil.ngetPageSize()'", 1);
		assertTrue(v.activado());
		assertTrue(v.mensaje().contains("ngetPageSize"));
	}

	@Test
	void nativaNoSeActivaConFailedToLocate() throws Exception {
		BibliotecaNativaFaltante v = new BibliotecaNativaFaltante();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.lang.UnsatisfiedLinkError: Failed to locate library: liblwjni.so", 1);
		assertFalse(v.activado(), "Failed to locate library ya cubre ErrorDeEnlaceInsatisfecho.");
	}

	@Test
	void nativaNoSeActivaConLineaNormal() throws Exception {
		BibliotecaNativaFaltante v = new BibliotecaNativaFaltante();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"INFO: Native library loaded successfully", 1);
		assertFalse(v.activado());
	}

	@Test
	void nativaNoSeActivaConLineaVacia() throws Exception {
		BibliotecaNativaFaltante v = new BibliotecaNativaFaltante();
		v.verificarPorLinea(ConsolaPrueba.crear(), "", 1);
		assertFalse(v.activado());
	}

	@Test
	void nativaNoSeActivaConNull() throws Exception {
		BibliotecaNativaFaltante v = new BibliotecaNativaFaltante();
		v.verificarPorLinea(ConsolaPrueba.crear(), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void nativaDeduplicacionGlobal() throws Exception {
		BibliotecaNativaFaltante v1 = new BibliotecaNativaFaltante();
		BibliotecaNativaFaltante v2 = new BibliotecaNativaFaltante();
		String linea = "java.lang.UnsatisfiedLinkError: 'long org.lwjgl.system.libc.LibCString.nmemset(long, int, long)'";
		v1.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertTrue(v1.activado());
		v2.verificarPorLinea(ConsolaPrueba.crear(), linea, 2);
		assertFalse(v2.activado(), "Deduplicacion global debe evitar segunda activacion.");
	}

	@Test
	void nativaIdentidad() {
		BibliotecaNativaFaltante v = new BibliotecaNativaFaltante();
		assertEquals("biblioteca_nativa_faltante", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof BibliotecaNativaFaltante);
		assertEquals(750.0f, v.prioridad());
	}
}
