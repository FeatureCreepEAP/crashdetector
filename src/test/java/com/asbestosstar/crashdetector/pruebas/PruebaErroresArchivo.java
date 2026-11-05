package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.ErrorArchivoBloqueadoPorOtroProceso;
import com.asbestosstar.crashdetector.analizador.general.ErrorArchivoUsadoPorOtroProceso;
import com.asbestosstar.crashdetector.analizador.general.ErrorCaracteresInvalidosEnNombre;
import com.asbestosstar.crashdetector.analizador.general.RutaCaracteresInvalidos;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de errores de archivo: bloqueado, usado por otro proceso,
 * caracteres invalidos y ruta invalida.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.ErrorArchivoBloqueadoPorOtroProceso",
		"com.asbestosstar.crashdetector.analizador.general.ErrorArchivoUsadoPorOtroProceso",
		"com.asbestosstar.crashdetector.analizador.general.ErrorCaracteresInvalidosEnNombre",
		"com.asbestosstar.crashdetector.analizador.general.RutaCaracteresInvalidos" },
		nota = "Cubre errores de acceso a archivos bloqueados, usados por otro proceso y caracteres invalidos")
class PruebaErroresArchivo {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ErrorArchivoBloqueadoPorOtroProceso ====================

	@Test
	void archivoBloqueadoDetectaError() throws Exception {
		ErrorArchivoBloqueadoPorOtroProceso v = new ErrorArchivoBloqueadoPorOtroProceso();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.io.IOException: The process cannot access the file because another process has locked a portion of the file", 10);
		assertTrue(v.activado());
	}

	@Test
	void archivoBloqueadoNoSeActivaConLineaNormal() throws Exception {
		ErrorArchivoBloqueadoPorOtroProceso v = new ErrorArchivoBloqueadoPorOtroProceso();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: File opened", 1);
		assertFalse(v.activado());
	}

	@Test
	void archivoBloqueadoIdentidad() {
		ErrorArchivoBloqueadoPorOtroProceso v = new ErrorArchivoBloqueadoPorOtroProceso();
		assertEquals("archivo_bloqueado_por_otro_proceso", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorArchivoBloqueadoPorOtroProceso);
	}

	// ==================== ErrorArchivoUsadoPorOtroProceso ====================

	@Test
	void archivoUsadoDetectaError() throws Exception {
		ErrorArchivoUsadoPorOtroProceso v = new ErrorArchivoUsadoPorOtroProceso();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.nio.file.FileSystemException: C:\\path\\to\\file.dat: The process cannot access the file because it is being used by another process", 10);
		assertTrue(v.activado());
	}

	@Test
	void archivoUsadoNoSeActivaConLineaNormal() throws Exception {
		ErrorArchivoUsadoPorOtroProceso v = new ErrorArchivoUsadoPorOtroProceso();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: File opened", 1);
		assertFalse(v.activado());
	}

	@Test
	void archivoUsadoIdentidad() {
		ErrorArchivoUsadoPorOtroProceso v = new ErrorArchivoUsadoPorOtroProceso();
		assertEquals("archivo_usado_por_otro_proceso", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorArchivoUsadoPorOtroProceso);
	}

	// ==================== ErrorCaracteresInvalidosEnNombre ====================

	@Test
	void caracteresInvalidosDetectaError() throws Exception {
		ErrorCaracteresInvalidosEnNombre v = new ErrorCaracteresInvalidosEnNombre();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"IllegalArgumentException: my-mod-1.0: Invalid module name: 'my-mod' is not a Java identifier", 10);
		assertTrue(v.activado());
	}

	@Test
	void caracteresInvalidosNoSeActivaConLineaNormal() throws Exception {
		ErrorCaracteresInvalidosEnNombre v = new ErrorCaracteresInvalidosEnNombre();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: File name valid", 1);
		assertFalse(v.activado());
	}

	@Test
	void caracteresInvalidosIdentidad() {
		ErrorCaracteresInvalidosEnNombre v = new ErrorCaracteresInvalidosEnNombre();
		assertEquals("caracters_invalidos_en_nombre", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorCaracteresInvalidosEnNombre);
	}

	// ==================== RutaCaracteresInvalidos ====================

	@Test
	void rutaCaracteresInvalidosDetectaError() throws Exception {
		RutaCaracteresInvalidos v = new RutaCaracteresInvalidos();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"java.nio.file.InvalidPathException: Illegal char <:> at index 10: C:\\path\\to\\file", 10);
		assertTrue(v.activado());
	}

	@Test
	void rutaCaracteresInvalidosNoSeActivaConLineaNormal() throws Exception {
		RutaCaracteresInvalidos v = new RutaCaracteresInvalidos();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Path valid", 1);
		assertFalse(v.activado());
	}

	@Test
	void rutaCaracteresInvalidosIdentidad() {
		RutaCaracteresInvalidos v = new RutaCaracteresInvalidos();
		assertEquals("ruta_caracteres_invalidos", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof RutaCaracteresInvalidos);
	}
}
