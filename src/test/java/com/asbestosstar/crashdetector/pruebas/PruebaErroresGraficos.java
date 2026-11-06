package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.general.ErrorBackendGraficoNoDisponible;
import com.asbestosstar.crashdetector.analizador.general.ErrorCargadorVulkan;
import com.asbestosstar.crashdetector.analizador.general.ErrorDriverDRI;
import com.asbestosstar.crashdetector.analizador.general.ErrorGLXBadFBConfig;
import com.asbestosstar.crashdetector.analizador.general.ErrorLWJGLPlataformaNoSoportada;
import com.asbestosstar.crashdetector.analizador.general.ErrorVulkanExtension;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas para los analizadores de errores gráficos: DRI driver crashes,
 * GLXBadFBConfig, Vulkan extension errors, no graphics backend, LWJGL platform
 * mismatch, and Vulkan loader failures.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.ErrorDriverDRI",
		"com.asbestosstar.crashdetector.analizador.general.ErrorGLXBadFBConfig",
		"com.asbestosstar.crashdetector.analizador.general.ErrorVulkanExtension",
		"com.asbestosstar.crashdetector.analizador.general.ErrorBackendGraficoNoDisponible",
		"com.asbestosstar.crashdetector.analizador.general.ErrorLWJGLPlataformaNoSoportada",
		"com.asbestosstar.crashdetector.analizador.general.ErrorCargadorVulkan" },
		nota = "Cubre deteccion de errores graficos: DRI, GLXBadFBConfig, Vulkan, backend, LWJGL y cargador Vulkan")
class PruebaErroresGraficos {

	@BeforeEach
	void reiniciar() {
		MonitorDePID.idioma = new Espanol();
		ErrorDriverDRI.reiniciarGlobal();
		ErrorGLXBadFBConfig.reiniciarGlobal();
		ErrorVulkanExtension.reiniciarGlobal();
		ErrorBackendGraficoNoDisponible.reiniciarGlobal();
		ErrorLWJGLPlataformaNoSoportada.reiniciarGlobal();
		ErrorCargadorVulkan.reiniciarGlobal();
	}

	// ==================== ErrorDriverDRI ====================

	@Test
	void driDetectaSwrastConProblematicFrame() {
		ErrorDriverDRI v = new ErrorDriverDRI();
		v.verificarPorLinea(null, "# Problematic frame:", 1);
		v.verificarPorLinea(null, "# C  [swrast_dri.so+0xd04cb8]  try_update_scene_state+0x2f4", 2);
		assertTrue(v.activado());
	}

	@Test
	void driDetectaSwrastConSIGBUS() {
		ErrorDriverDRI v = new ErrorDriverDRI();
		v.verificarPorLinea(null, "# SIGBUS (0xa) at pc=0xffffffff05104cb8", 1);
		v.verificarPorLinea(null, "C  [swrast_dri.so+0xd04cb8]  try_update_scene_state+0x2f4", 2);
		assertTrue(v.activado());
	}

	@Test
	void driDetectaSwrastConSIGSEGV() {
		ErrorDriverDRI v = new ErrorDriverDRI();
		v.verificarPorLinea(null, "# SIGSEGV (0xb) at pc=0xffffffff05104cb8", 1);
		v.verificarPorLinea(null, "C  [swrast_dri.so+0xd04cb8]  try_update_scene_state+0x2f4", 2);
		assertTrue(v.activado());
	}

	@Test
	void driNoActivaSoloMencionDeDri() {
		ErrorDriverDRI v = new ErrorDriverDRI();
		v.verificarPorLinea(null, "Loaded library: swrast_dri.so", 1);
		assertFalse(v.activado());
	}

	@Test
	void driNoActivaConLineaNormal() {
		ErrorDriverDRI v = new ErrorDriverDRI();
		v.verificarPorLinea(null, "[Render thread/INFO]: Backend library: LWJGL version 3.4.3", 1);
		assertFalse(v.activado());
	}

	@Test
	void driDeduplicacionGlobal() {
		ErrorDriverDRI v1 = new ErrorDriverDRI();
		v1.verificarPorLinea(null, "# SIGBUS (0xa)", 1);
		v1.verificarPorLinea(null, "C  [swrast_dri.so+0xd04cb8]", 2);
		assertTrue(v1.activado());

		ErrorDriverDRI v2 = new ErrorDriverDRI();
		v2.verificarPorLinea(null, "# SIGBUS (0xa)", 1);
		v2.verificarPorLinea(null, "C  [swrast_dri.so+0xd04cb8]", 2);
		assertFalse(v2.activado());
	}

	@Test
	void driIdentidad() {
		ErrorDriverDRI v = new ErrorDriverDRI();
		assertEquals("error_driver_dri", v.id());
		assertTrue(v.nueva() instanceof ErrorDriverDRI);
		assertEquals(850.0f, v.prioridad());
	}

	// ==================== ErrorGLXBadFBConfig ====================

	@Test
	void glxBadFBConfigDetectaGLX() {
		ErrorGLXBadFBConfig v = new ErrorGLXBadFBConfig();
		v.verificarPorLinea(null,
				"Failed to create OpenGL context: Could not create GL context: GLXBadFBConfig", 1);
		assertTrue(v.activado());
	}

	@Test
	void glxBadFBConfigDetectaWGL() {
		ErrorGLXBadFBConfig v = new ErrorGLXBadFBConfig();
		v.verificarPorLinea(null, "Could not create GL context: WGLBadFBConfig", 1);
		assertTrue(v.activado());
	}

	@Test
	void glxBadFBConfigDetectaCGL() {
		ErrorGLXBadFBConfig v = new ErrorGLXBadFBConfig();
		v.verificarPorLinea(null, "OpenGL context creation failed: CGLBadFBConfig", 1);
		assertTrue(v.activado());
	}

	@Test
	void glxBadFBConfigNoActivaConLineaNormal() {
		ErrorGLXBadFBConfig v = new ErrorGLXBadFBConfig();
		v.verificarPorLinea(null, "[Render thread/INFO]: Backend library: LWJGL version 3.4.3", 1);
		assertFalse(v.activado());
	}

	@Test
	void glxBadFBConfigDeduplicacionGlobal() {
		ErrorGLXBadFBConfig v1 = new ErrorGLXBadFBConfig();
		v1.verificarPorLinea(null, "GLXBadFBConfig", 1);
		assertTrue(v1.activado());

		ErrorGLXBadFBConfig v2 = new ErrorGLXBadFBConfig();
		v2.verificarPorLinea(null, "GLXBadFBConfig", 2);
		assertFalse(v2.activado());
	}

	@Test
	void glxBadFBConfigIdentidad() {
		ErrorGLXBadFBConfig v = new ErrorGLXBadFBConfig();
		assertEquals("error_glx_bad_fbconfig", v.id());
		assertTrue(v.nueva() instanceof ErrorGLXBadFBConfig);
		assertEquals(820.0f, v.prioridad());
	}

	// ==================== ErrorVulkanExtension ====================

	@Test
	void vulkanExtensionDetectaExtensionNotPresent() {
		ErrorVulkanExtension v = new ErrorVulkanExtension();
		v.verificarPorLinea(null, "VK_ERROR_EXTENSION_NOT_PRESENT: Error creating instance", 1);
		assertTrue(v.activado());
	}

	@Test
	void vulkanExtensionDetectaIncompatibleDriver() {
		ErrorVulkanExtension v = new ErrorVulkanExtension();
		v.verificarPorLinea(null, "VK_ERROR_INCOMPATIBLE_DRIVER: Failed to create device", 1);
		assertTrue(v.activado());
	}

	@Test
	void vulkanExtensionNoActivaConLineaNormal() {
		ErrorVulkanExtension v = new ErrorVulkanExtension();
		v.verificarPorLinea(null, "[Render thread/INFO]: Backend library: LWJGL version 3.4.3", 1);
		assertFalse(v.activado());
	}

	@Test
	void vulkanExtensionDeduplicacionGlobal() {
		ErrorVulkanExtension v1 = new ErrorVulkanExtension();
		v1.verificarPorLinea(null, "VK_ERROR_EXTENSION_NOT_PRESENT", 1);
		assertTrue(v1.activado());

		ErrorVulkanExtension v2 = new ErrorVulkanExtension();
		v2.verificarPorLinea(null, "VK_ERROR_EXTENSION_NOT_PRESENT", 2);
		assertFalse(v2.activado());
	}

	@Test
	void vulkanExtensionIdentidad() {
		ErrorVulkanExtension v = new ErrorVulkanExtension();
		assertEquals("error_vulkan_extension", v.id());
		assertTrue(v.nueva() instanceof ErrorVulkanExtension);
		assertEquals(810.0f, v.prioridad());
	}

	// ==================== ErrorBackendGraficoNoDisponible ====================

	@Test
	void backendNoDisponibleDetectaMensaje() {
		ErrorBackendGraficoNoDisponible v = new ErrorBackendGraficoNoDisponible();
		v.verificarPorLinea(null, "No supported graphics backend was found.", 1);
		assertTrue(v.activado());
	}

	@Test
	void backendNoDisponibleDetectaConDetalles() {
		ErrorBackendGraficoNoDisponible v = new ErrorBackendGraficoNoDisponible();
		v.verificarPorLinea(null,
				"Window$WindowInitFailed: No supported graphics backend was found.", 1);
		assertTrue(v.activado());
	}

	@Test
	void backendNoDisponibleNoActivaConLineaNormal() {
		ErrorBackendGraficoNoDisponible v = new ErrorBackendGraficoNoDisponible();
		v.verificarPorLinea(null, "[Render thread/INFO]: Backend library: LWJGL version 3.4.3", 1);
		assertFalse(v.activado());
	}

	@Test
	void backendNoDisponibleDeduplicacionGlobal() {
		ErrorBackendGraficoNoDisponible v1 = new ErrorBackendGraficoNoDisponible();
		v1.verificarPorLinea(null, "No supported graphics backend was found", 1);
		assertTrue(v1.activado());

		ErrorBackendGraficoNoDisponible v2 = new ErrorBackendGraficoNoDisponible();
		v2.verificarPorLinea(null, "No supported graphics backend was found", 2);
		assertFalse(v2.activado());
	}

	@Test
	void backendNoDisponibleIdentidad() {
		ErrorBackendGraficoNoDisponible v = new ErrorBackendGraficoNoDisponible();
		assertEquals("error_backend_grafico_no_disponible", v.id());
		assertTrue(v.nueva() instanceof ErrorBackendGraficoNoDisponible);
		assertEquals(900.0f, v.prioridad());
	}

	// ==================== ErrorLWJGLPlataformaNoSoportada ====================

	@Test
	void lwjglPlataformaDetectaMismatch() {
		ErrorLWJGLPlataformaNoSoportada v = new ErrorLWJGLPlataformaNoSoportada();
		v.verificarPorLinea(null,
				"[LWJGL] Platform/architecture mismatch detected for module: org.lwjgl", 1);
		assertTrue(v.activado());
	}

	@Test
	void lwjglPlataformaDetectaMismatchConDetalles() {
		ErrorLWJGLPlataformaNoSoportada v = new ErrorLWJGLPlataformaNoSoportada();
		v.verificarPorLinea(null, "Platform/architecture mismatch detected for module: org.lwjgl.glfw", 1);
		assertTrue(v.activado());
	}

	@Test
	void lwjglPlataformaNoActivaConLineaNormal() {
		ErrorLWJGLPlataformaNoSoportada v = new ErrorLWJGLPlataformaNoSoportada();
		v.verificarPorLinea(null, "[Render thread/INFO]: Backend library: LWJGL version 3.4.3", 1);
		assertFalse(v.activado());
	}

	@Test
	void lwjglPlataformaDeduplicacionGlobal() {
		ErrorLWJGLPlataformaNoSoportada v1 = new ErrorLWJGLPlataformaNoSoportada();
		v1.verificarPorLinea(null, "Platform/architecture mismatch detected", 1);
		assertTrue(v1.activado());

		ErrorLWJGLPlataformaNoSoportada v2 = new ErrorLWJGLPlataformaNoSoportada();
		v2.verificarPorLinea(null, "Platform/architecture mismatch detected", 2);
		assertFalse(v2.activado());
	}

	@Test
	void lwjglPlataformaIdentidad() {
		ErrorLWJGLPlataformaNoSoportada v = new ErrorLWJGLPlataformaNoSoportada();
		assertEquals("error_lwjgl_plataforma_no_soportada", v.id());
		assertTrue(v.nueva() instanceof ErrorLWJGLPlataformaNoSoportada);
		assertEquals(830.0f, v.prioridad());
	}

	// ==================== ErrorCargadorVulkan ====================

	@Test
	void cargadorVulkanDetectaFailedToLoad() {
		ErrorCargadorVulkan v = new ErrorCargadorVulkan();
		v.verificarPorLinea(null, "Failed to load Vulkan loader", 1);
		assertTrue(v.activado());
	}

	@Test
	void cargadorVulkanDetectaConIllegalStateException() {
		ErrorCargadorVulkan v = new ErrorCargadorVulkan();
		v.verificarPorLinea(null, "java.lang.IllegalStateException", 1);
		v.verificarPorLinea(null, "Failed to load Vulkan loader", 2);
		assertTrue(v.activado());
	}

	@Test
	void cargadorVulkanNoActivaConLineaNormal() {
		ErrorCargadorVulkan v = new ErrorCargadorVulkan();
		v.verificarPorLinea(null, "[Render thread/INFO]: Backend library: LWJGL version 3.4.3", 1);
		assertFalse(v.activado());
	}

	@Test
	void cargadorVulkanDeduplicacionGlobal() {
		ErrorCargadorVulkan v1 = new ErrorCargadorVulkan();
		v1.verificarPorLinea(null, "Failed to load Vulkan loader", 1);
		assertTrue(v1.activado());

		ErrorCargadorVulkan v2 = new ErrorCargadorVulkan();
		v2.verificarPorLinea(null, "Failed to load Vulkan loader", 2);
		assertFalse(v2.activado());
	}

	@Test
	void cargadorVulkanIdentidad() {
		ErrorCargadorVulkan v = new ErrorCargadorVulkan();
		assertEquals("error_cargador_vulkan", v.id());
		assertTrue(v.nueva() instanceof ErrorCargadorVulkan);
		assertEquals(800.0f, v.prioridad());
	}

	// ==================== Edge cases ====================

	@Test
	void driManejaLineaNula() {
		ErrorDriverDRI v = new ErrorDriverDRI();
		v.verificarPorLinea(null, null, 1);
		assertFalse(v.activado());
	}

	@Test
	void driManejaLineaVacia() {
		ErrorDriverDRI v = new ErrorDriverDRI();
		v.verificarPorLinea(null, "", 1);
		assertFalse(v.activado());
	}

	@Test
	void glxBadFBConfigManejaLineaNula() {
		ErrorGLXBadFBConfig v = new ErrorGLXBadFBConfig();
		v.verificarPorLinea(null, null, 1);
		assertFalse(v.activado());
	}

	@Test
	void vulkanExtensionManejaLineaNula() {
		ErrorVulkanExtension v = new ErrorVulkanExtension();
		v.verificarPorLinea(null, null, 1);
		assertFalse(v.activado());
	}

	@Test
	void backendNoDisponibleManejaLineaNula() {
		ErrorBackendGraficoNoDisponible v = new ErrorBackendGraficoNoDisponible();
		v.verificarPorLinea(null, null, 1);
		assertFalse(v.activado());
	}

	@Test
	void lwjglPlataformaManejaLineaNula() {
		ErrorLWJGLPlataformaNoSoportada v = new ErrorLWJGLPlataformaNoSoportada();
		v.verificarPorLinea(null, null, 1);
		assertFalse(v.activado());
	}

	@Test
	void cargadorVulkanManejaLineaNula() {
		ErrorCargadorVulkan v = new ErrorCargadorVulkan();
		v.verificarPorLinea(null, null, 1);
		assertFalse(v.activado());
	}
}
