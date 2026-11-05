package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.NoTieneMemoria;
import com.asbestosstar.crashdetector.analizador.general.OpcionesJavaGCInvalidas;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de NoTieneMemoria y OpcionesJavaGCInvalidas.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.NoTieneMemoria",
		"com.asbestosstar.crashdetector.analizador.general.OpcionesJavaGCInvalidas" },
		nota = "Cubre deteccion de OutOfMemoryError, GC overhead, PermGen y opciones GC invalidas")
class PruebaMemoria {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== NoTieneMemoria ====================

	@Test
	void noTieneMemoriaDetectaOutOfMemoryHeap() throws Exception {
		NoTieneMemoria v = new NoTieneMemoria();
		v.verificarPorLinea(ConsolaPrueba.crear(), "java.lang.OutOfMemoryError: Java heap space", 10);
		assertTrue(v.activado());
	}

	@Test
	void noTieneMemoriaDetectaGcOverhead() throws Exception {
		NoTieneMemoria v = new NoTieneMemoria();
		v.verificarPorLinea(ConsolaPrueba.crear(), "java.lang.OutOfMemoryError: GC overhead limit exceeded", 20);
		assertTrue(v.activado());
	}

	@Test
	void noTieneMemoriaDetectaDirectBufferMemory() throws Exception {
		NoTieneMemoria v = new NoTieneMemoria();
		v.verificarPorLinea(ConsolaPrueba.crear(), "java.lang.OutOfMemoryError: Direct buffer memory", 30);
		assertTrue(v.activado());
	}

	@Test
	void noTieneMemoriaDetectaPermGen() throws Exception {
		NoTieneMemoria v = new NoTieneMemoria();
		v.verificarPorLinea(ConsolaPrueba.crear(), "java.lang.OutOfMemoryError: PermGen space", 40);
		assertTrue(v.activado());
	}

	@Test
	void noTieneMemoriaDetectaPermGenExitCode() throws Exception {
		NoTieneMemoria v = new NoTieneMemoria();
		v.verificarPorLinea(ConsolaPrueba.crear(), "Process finished with exit code -805306369", 50);
		assertTrue(v.activado());
	}

	@Test
	void noTieneMemoriaDetectaCouldNotReserve() throws Exception {
		NoTieneMemoria v = new NoTieneMemoria();
		v.verificarPorLinea(ConsolaPrueba.crear(), "Could not reserve enough space for 2097152KB object heap", 60);
		assertTrue(v.activado());
	}

	@Test
	void noTieneMemoriaDetectaInvalidHeap() throws Exception {
		NoTieneMemoria v = new NoTieneMemoria();
		v.verificarPorLinea(ConsolaPrueba.crear(), "Invalid maximum heap size: -Xmx20G", 70);
		assertTrue(v.activado());
	}

	@Test
	void noTieneMemoriaDetectaJvmInsufficientMemory() throws Exception {
		NoTieneMemoria v = new NoTieneMemoria();
		v.verificarPorLinea(ConsolaPrueba.crear(), "There is insufficient memory for the Java Runtime Environment to continue", 80);
		assertTrue(v.activado());
	}

	@Test
	void noTieneMemoriaDetectaNativeThread() throws Exception {
		NoTieneMemoria v = new NoTieneMemoria();
		v.verificarPorLinea(ConsolaPrueba.crear(), "java.lang.OutOfMemoryError: unable to create new native thread", 90);
		assertTrue(v.activado());
	}

	@Test
	void noTieneMemoriaNoSeActivaConLineaNormal() throws Exception {
		NoTieneMemoria v = new NoTieneMemoria();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Memory allocated successfully", 1);
		assertFalse(v.activado());
	}

	@Test
	void noTieneMemoriaNoSeActivaConLineaVacia() throws Exception {
		NoTieneMemoria v = new NoTieneMemoria();
		v.verificarPorLinea(ConsolaPrueba.crear(), "", 1);
		assertFalse(v.activado());
	}

	@Test
	void noTieneMemoriaIdentidad() {
		NoTieneMemoria v = new NoTieneMemoria();
		assertEquals("no_tiene_memoria", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof NoTieneMemoria);
		assertEquals(1400.0f, v.prioridad());
	}

	// ==================== OpcionesJavaGCInvalidas ====================

	@Test
	void opcionesGcInvalidasDetectaError() throws Exception {
		OpcionesJavaGCInvalidas v = new OpcionesJavaGCInvalidas();
		v.verificarPorLinea(ConsolaPrueba.crear(), "Conflicting collector combinations in option list", 10);
		assertTrue(v.activado());
	}

	@Test
	void opcionesGcInvalidasNoSeActivaConLineaNormal() throws Exception {
		OpcionesJavaGCInvalidas v = new OpcionesJavaGCInvalidas();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: GC configured", 1);
		assertFalse(v.activado());
	}

	@Test
	void opcionesGcInvalidasIdentidad() {
		OpcionesJavaGCInvalidas v = new OpcionesJavaGCInvalidas();
		assertEquals("opciones_java_gc_invalidas", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof OpcionesJavaGCInvalidas);
	}
}
