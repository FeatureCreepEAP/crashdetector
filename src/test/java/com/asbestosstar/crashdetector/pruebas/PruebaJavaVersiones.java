package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.JavaVersiones;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de la verificacion JavaVersiones, cubriendo errores de
 * version de clase, problematic frame con libjvm, y falsos positivos.
 */
@PruebaDe(valor = { "com.asbestosstar.crashdetector.analizador.general.JavaVersiones" },
		nota = "Cubre UnsupportedClassVersionError, problematic frame con libjvm, y descartes de menciones a libjvm sin problematic frame")
class PruebaJavaVersiones {

	private JavaVersiones verificacion;

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
		verificacion = new JavaVersiones();
	}

	// ==================== UnsupportedClassVersionError ====================

	@Test
	void detectaUnsupportedClassVersionError() throws Exception {
		String linea = "java.lang.UnsupportedClassVersionError: com/example/MyClass has been compiled by a more recent version of the Java Runtime (class file version 61.0, this version of the Java Runtime only recognizes class file versions up to 52.0)";
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), linea, 10);
		assertTrue(verificacion.activado());
		assertTrue(verificacion.mensaje().contains("com.example.MyClass"));
	}

	@Test
	void detectaUnsupportedClassVersionErrorConBarras() throws Exception {
		String linea = "java.lang.UnsupportedClassVersionError: com/example/MyClass has been compiled by a more recent version of the Java Runtime (class file version 60.0)";
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), linea, 5);
		assertTrue(verificacion.activado());
	}

	@Test
	void detectaUnsupportedClassFileMajorVersion() throws Exception {
		String linea = "org.gradle.internal.classpath.InterceptingURLClassLoader$1 Unsupported class file major version 65";
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertTrue(verificacion.activado());
	}

	@Test
	void detectaUnsupportedMajorMinorVersion52() throws Exception {
		String linea = "java.lang.UnsupportedClassVersionError: Bad version expected: Unsupported major.minor version 52.0";
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertTrue(verificacion.activado());
	}

	// ==================== Problematic frame ====================

	@Test
	void detectaProblematicFrameConLibJvmSo() throws Exception {
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), "Problematic frame:", 100);
		assertFalse(verificacion.activado(), "Solo el encabezado no debe activar la verificacion.");
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), "# C  [libjvm.so+0x00007f1234567890]", 101);
		// No se activa porque la consola es diferente (estado por consola)
	}

	@Test
	void detectaProblematicFrameConJvmDll() throws Exception {
		com.asbestosstar.crashdetector.Consola consola = ConsolaPrueba.crear();
		verificacion.verificarPorLinea(consola, "Problematic frame:", 50);
		assertFalse(verificacion.activado());
		verificacion.verificarPorLinea(consola, "# V  [jvm.dll+0x0000000140000000]", 51);
		assertTrue(verificacion.activado());
	}

	@Test
	void detectaProblematicFrameConLibJvmDylib() throws Exception {
		com.asbestosstar.crashdetector.Consola consola = ConsolaPrueba.crear();
		verificacion.verificarPorLinea(consola, "Problematic frame:", 10);
		assertFalse(verificacion.activado());
		verificacion.verificarPorLinea(consola, "# C  [libjvm.dylib+0x0000000123456789]", 11);
		assertTrue(verificacion.activado());
	}

	@Test
	void noSeActivaConProblematicFrameSolo() throws Exception {
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), "Problematic frame:", 1);
		assertFalse(verificacion.activado());
	}

	// ==================== Falsos positivos ====================

	@Test
	void noSeActivaConLibJvmEnListaDeLibrerias() throws Exception {
		// libjvm.so en una lista de librerias sin "Problematic frame:"
		String linea = "# C  [libjvm.so+0x00007f1234567890]";
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), linea, 200);
		assertFalse(verificacion.activado(), "libjvm.so sin Problematic frame no debe activar la verificacion.");
	}

	@Test
	void noSeActivaConJvmDllEnListaDeLibrerias() throws Exception {
		String linea = "  300  0x00007f1234567890  jvm.dll  (some info)";
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), linea, 200);
		assertFalse(verificacion.activado());
	}

	@Test
	void noSeActivaConLineaNormal() throws Exception {
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Starting Minecraft server", 1);
		assertFalse(verificacion.activado());
	}

	@Test
	void noSeActivaConLineaVacia() throws Exception {
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), "", 1);
		assertFalse(verificacion.activado());
	}

	@Test
	void noSeActivaConConsolaNula() throws Exception {
		verificacion.verificarPorLinea(null, "java.lang.UnsupportedClassVersionError: com/example/MyClass has been compiled", 1);
		assertFalse(verificacion.activado(), "Consola nula no debe activar la verificacion.");
	}

	@Test
	void problematicFrameDemasiadoLejosNoActiva() throws Exception {
		com.asbestosstar.crashdetector.Consola consola = ConsolaPrueba.crear();
		verificacion.verificarPorLinea(consola, "Problematic frame:", 10);
		// La linea del frame esta muy lejos (maximo 4 lineas despues)
		verificacion.verificarPorLinea(consola, "# C  [libjvm.so+0x00007f1234567890]", 20);
		assertFalse(verificacion.activado());
	}

	// ==================== Identidad ====================

	@Test
	void identidadDeLaVerificacion() {
		assertEquals("java_versiones", verificacion.id());
		assertEquals(Criticalidad.ERROR, verificacion.nivel_de_criticalidad());
		assertTrue(verificacion.nueva() instanceof JavaVersiones);
		assertEquals(925.0f, verificacion.prioridad());
	}

	@Test
	void mensajeVacioSinActivacion() {
		assertEquals("", verificacion.mensaje());
	}

	@Test
	void mensajeNoVacioDespuesDeActivacion() throws Exception {
		String linea = "java.lang.UnsupportedClassVersionError: com/example/Test has been compiled by a more recent version of the Java Runtime (class file version 61.0)";
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertTrue(verificacion.activado());
		assertNotNull(verificacion.mensaje());
		assertTrue(verificacion.mensaje().length() > 0);
	}
}
