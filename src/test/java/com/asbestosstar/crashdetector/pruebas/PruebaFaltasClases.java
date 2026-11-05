package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.AdvertenciaFaltasClases;
import com.asbestosstar.crashdetector.analizador.general.FaltasClases;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de FaltasClases y AdvertenciaFaltasClases, cubriendo
 * ClassNotFoundException, NoClassDefFoundError, falsos positivos (WARN lines,
 * Kotlin/gg.essential) y descartes.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.FaltasClases",
		"com.asbestosstar.crashdetector.analizador.general.AdvertenciaFaltasClases" },
		nota = "Cubre ClassNotFoundException, NoClassDefFoundError, Error loading class, y descartes de WARN/Kotlin/gg.essential")
class PruebaFaltasClases {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
		FaltasClases.reiniciarGlobal();
	}

	// ==================== FaltasClases ====================

	@Test
	void faltasClasesDetectaClassNotFoundException() throws Exception {
		FaltasClases v = new FaltasClases();
		String linea = "java.lang.ClassNotFoundException: com.example.MissingClass";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 10);
		assertTrue(v.activado());
		assertTrue(v.mensaje().contains("com/example/MissingClass"));
	}

	@Test
	void faltasClasesDetectaNoClassDefFoundError() throws Exception {
		FaltasClases v = new FaltasClases();
		String linea = "java.lang.NoClassDefFoundError: com/example/SomeClass";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 20);
		assertTrue(v.activado());
	}

	@Test
	void faltasClasesDetectaNoClassDefFoundErrorConCouldNotInitialize() throws Exception {
		FaltasClases v = new FaltasClases();
		String linea = "java.lang.NoClassDefFoundError: Could not initialize class com.example.BadInit";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 30);
		assertTrue(v.activado());
	}

	@Test
	void faltasClasesDetectaErrorLoadingClass() throws Exception {
		FaltasClases v = new FaltasClases();
		String linea = "Error loading class: com.example.LoadError";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 40);
		assertTrue(v.activado());
	}

	@Test
	void faltasClasesDetectaClassMetadataNotFound() throws Exception {
		FaltasClases v = new FaltasClases();
		String linea = "org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException: org.joml.Quaternionf";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 50);
		assertTrue(v.activado());
	}

	@Test
	void faltasClasesNoSeActivaConWarnLine() throws Exception {
		FaltasClases v = new FaltasClases();
		String linea = "[12:00:00] [WARN] java.lang.ClassNotFoundException: com.example.WarnClass";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertFalse(v.activado(), "WARN lines no deben activar FaltasClases.");
	}

	@Test
	void faltasClasesFiltraKotlin() throws Exception {
		FaltasClases v = new FaltasClases();
		String linea = "java.lang.NoClassDefFoundError: kotlin/sequences/SequencesKt";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		// kotlin/ se filtra en postProcesarSiNecesario()
		assertFalse(v.activado(), "kotlin/ debe filtrarse.");
	}

	@Test
	void faltasClasesFiltraGgEssential() throws Exception {
		FaltasClases v = new FaltasClases();
		String linea = "java.lang.NoClassDefFoundError: gg/essential/xmixin/feature/Feature";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertFalse(v.activado(), "gg/essential/ debe filtrarse.");
	}

	@Test
	void faltasClasesFiltraKotlinx() throws Exception {
		FaltasClases v = new FaltasClases();
		String linea = "java.lang.NoClassDefFoundError: kotlinx/coroutines/CoroutineScope";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertFalse(v.activado(), "kotlinx/ debe filtrarse.");
	}

	@Test
	void faltasClasesNoSeActivaConLineaNormal() throws Exception {
		FaltasClases v = new FaltasClases();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: All classes loaded", 1);
		assertFalse(v.activado());
	}

	@Test
	void faltasClasesNoSeActivaConConsolaNula() throws Exception {
		FaltasClases v = new FaltasClases();
		v.verificarPorLinea(null, "java.lang.ClassNotFoundException: com.example.Test", 1);
		assertFalse(v.activado());
	}

	@Test
	void faltasClasesNoSeActivaConLineaVacia() throws Exception {
		FaltasClases v = new FaltasClases();
		v.verificarPorLinea(ConsolaPrueba.crear(), "", 1);
		assertFalse(v.activado());
	}

	@Test
	void faltasClasesIgnoraAvaritia() throws Exception {
		FaltasClases v = new FaltasClases();
		String linea = "java.lang.ClassNotFoundException: avaritia.SuperConductor";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertFalse(v.activado(), "avaritia esta en la lista de ignorar.");
	}

	@Test
	void faltasClasesIgnoraValkyrianSkies() throws Exception {
		FaltasClases v = new FaltasClases();
		String linea = "java.lang.ClassNotFoundException: Valkyrian skies compatibility disabled";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertFalse(v.activado(), "Valkyrian skies compatibility disabled debe ignorarse.");
	}

	@Test
	void faltasClasesIdentidad() {
		FaltasClases v = new FaltasClases();
		assertEquals("faltas_clases", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof FaltasClases);
		assertEquals(1025.0f, v.prioridad());
	}

	// ==================== AdvertenciaFaltasClases ====================

	@Test
	void advertenciaFaltasClasesDetectaErrorLoadingClassConWarn() throws Exception {
		AdvertenciaFaltasClases v = new AdvertenciaFaltasClases();
		String linea = "[12:00:00] [WARN] Error loading class: com.example.OptionalFeature";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 10);
		assertTrue(v.activado());
	}

	@Test
	void advertenciaFaltasClasesNoSeActivaSinWarn() throws Exception {
		AdvertenciaFaltasClases v = new AdvertenciaFaltasClases();
		String linea = "Error loading class: com.example.SomeClass";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertFalse(v.activado(), "Sin WARN no debe activar AdvertenciaFaltasClases.");
	}

	@Test
	void advertenciaFaltasClasesNoSeActivaConLineaNormal() throws Exception {
		AdvertenciaFaltasClases v = new AdvertenciaFaltasClases();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Loading complete", 1);
		assertFalse(v.activado());
	}

	@Test
	void advertenciaFaltasClasesIdentidad() {
		AdvertenciaFaltasClases v = new AdvertenciaFaltasClases();
		assertEquals("advertencia_faltas_clases", v.id());
		assertEquals(Criticalidad.ADVERTENCIA, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof AdvertenciaFaltasClases);
		assertEquals(2.0f, v.prioridad());
	}

	@Test
	void advertenciaFaltasClasesMensajeVacioSinActivacion() {
		AdvertenciaFaltasClases v = new AdvertenciaFaltasClases();
		assertEquals("", v.mensaje());
	}
}
