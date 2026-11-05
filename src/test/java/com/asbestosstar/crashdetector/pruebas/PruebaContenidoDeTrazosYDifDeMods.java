package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.general.ContenidoDeTrazos;
import com.asbestosstar.crashdetector.analizador.general.DifDeMods;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de ContenidoDeTrazos y DifDeMods.
 * ContenidoDeTrazos requiere VDST con trazos, DifDeMods requiere archivos de
 * historia_mods.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.ContenidoDeTrazos",
		"com.asbestosstar.crashdetector.analizador.general.DifDeMods" },
		nota = "Cubre identidad, deduplicacion global y casos sin datos")
class PruebaContenidoDeTrazosYDifDeMods {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
		ContenidoDeTrazos.reiniciarGlobal();
	}

	// ==================== ContenidoDeTrazos ====================

	@Test
	void contenidoDeTrazosIdentidad() {
		ContenidoDeTrazos v = new ContenidoDeTrazos();
		assertEquals("contenido_de_trazos", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ContenidoDeTrazos);
		assertEquals(10.0f, v.prioridad());
	}

	@Test
	void contenidoDeTrazosNoSeActivaSinVDST() throws Exception {
		ContenidoDeTrazos v = new ContenidoDeTrazos();
		Consola consola = ConsolaPrueba.crear();
		// Sin verificacion_de_stacktrace
		v.finalizarArchivo(consola, null);
		assertFalse(v.activado());
	}

	@Test
	void contenidoDeTrazosNoSeActivaSinTrazos() throws Exception {
		ContenidoDeTrazos v = new ContenidoDeTrazos();
		Consola consola = ConsolaPrueba.crear();
		consola.verificacion_de_stacktrace = new VerificacionDeStackTrace(consola);
		// Sin trazos_completos
		v.finalizarArchivo(consola, null);
		assertFalse(v.activado());
	}

	@Test
	void contenidoDeTrazosDetectaTrazosConOrigen() throws Exception {
		ContenidoDeTrazos v = new ContenidoDeTrazos();
		Consola consola = crearConsolaConTraza("com.example.ModClass.init(ModClass.java:10) ~[mymod-1.0.jar:1.0]");
		v.finalizarArchivo(consola, null);
		assertTrue(v.activado());
	}

	@Test
	void contenidoDeTrazosDeduplicacionGlobal() throws Exception {
		// La misma traza en dos consolas diferentes solo se cuenta una vez
		ContenidoDeTrazos v1 = new ContenidoDeTrazos();
		Consola consola1 = crearConsolaConTraza("com.example.ModClass.init(ModClass.java:10) ~[mymod-1.0.jar:1.0]");
		v1.finalizarArchivo(consola1, null);
		assertTrue(v1.activado());

		ContenidoDeTrazos v2 = new ContenidoDeTrazos();
		Consola consola2 = crearConsolaConTraza("com.example.ModClass.init(ModClass.java:10) ~[mymod-1.0.jar:1.0]");
		v2.finalizarArchivo(consola2, null);
		// La segunda instancia no debe activarse porque la linea ya fue vista
		assertFalse(v2.activado(), "La misma traza ya fue vista globalmente.");
	}

	@Test
	void contenidoDeTrazosMensajeSinActivacion() {
		ContenidoDeTrazos v = new ContenidoDeTrazos();
		// Sin activacion, mensaje() muestra solo el encabezado de trazos
		String mensaje = v.mensaje();
		// El mensaje siempre tiene algun contenido (el encabezado)
		assertTrue(mensaje.length() > 0);
	}

	// ==================== DifDeMods ====================

	@Test
	void difDeModsIdentidad() {
		DifDeMods v = new DifDeMods();
		assertEquals("dif_de_mods", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof DifDeMods);
		assertEquals(-1000.0f, v.prioridad());
	}

	@Test
	void difDeModsNoSeActivaSinHistoria() {
		DifDeMods v = new DifDeMods();
		// Sin archivos de historia_mods, no se activa
		v.verificar();
		assertFalse(v.activado());
	}

	@Test
	void difDeModsMensajeVacioSinActivacion() {
		DifDeMods v = new DifDeMods();
		assertEquals("", v.mensaje());
	}

	// ==================== Utilidades ====================

	private Consola crearConsolaConTraza(String lineaStack) throws Exception {
		Path archivo = Files.createTempFile("prueba-trazo-", ".log");
		Consola consola = new Consola(archivo);
		consola.verificacion_de_stacktrace = new VerificacionDeStackTrace(consola);

		// Crear TraceInfo con reflection
		Constructor<VerificacionDeStackTrace.TraceInfo> constructor = VerificacionDeStackTrace.TraceInfo.class
				.getDeclaredConstructor(String.class, int.class, int.class, boolean.class);
		constructor.setAccessible(true);
		VerificacionDeStackTrace.TraceInfo info = constructor.newInstance(lineaStack, 1, 1, false);

		// Crear LineaTrazo usando campos publicos (no tiene constructor)
		VerificacionDeStackTrace.LineaTrazo linea = new VerificacionDeStackTrace.LineaTrazo();
		linea.clase = "com.example.ModClass";
		linea.origen = "mymod-1.0.jar";
		linea.nivel = 1;
		linea.lineaConsola = 10;
		linea.fatal = false;
		info.lineas.add(linea);
		consola.verificacion_de_stacktrace.trazos_completos = Collections.singletonList(info);

		Files.deleteIfExists(archivo);
		return consola;
	}
}
