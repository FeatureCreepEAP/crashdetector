package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.ErrorJarCorruptoConNombre;
import com.asbestosstar.crashdetector.analizador.general.ErrorVersionInvalidaModMaven;
import com.asbestosstar.crashdetector.analizador.general.VersionInvalidaSemver;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de errores de JAR: corrupto, version Maven invalida y
 * version Semver invalida.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.ErrorJarCorruptoConNombre",
		"com.asbestosstar.crashdetector.analizador.general.ErrorVersionInvalidaModMaven",
		"com.asbestosstar.crashdetector.analizador.general.VersionInvalidaSemver" },
		nota = "Cubre JAR corruptos, versiones Maven invalidas y versiones Semver invalidas")
class PruebaErroresJar {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ErrorJarCorruptoConNombre ====================

	@Test
	void jarCorruptoDetectaError() throws Exception {
		ErrorJarCorruptoConNombre v = new ErrorJarCorruptoConNombre();
		v.verificarPorLinea(ConsolaPrueba.crear(), "Error analyzing [/path/to/mymod-1.0.jar]: java.util.zip.ZipException: zip END header not found", 10);
		assertTrue(v.activado());
	}

	@Test
	void jarCorruptoNoSeActivaConLineaNormal() throws Exception {
		ErrorJarCorruptoConNombre v = new ErrorJarCorruptoConNombre();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: JAR loaded successfully", 1);
		assertFalse(v.activado());
	}

	@Test
	void jarCorruptoIdentidad() {
		ErrorJarCorruptoConNombre v = new ErrorJarCorruptoConNombre();
		assertEquals("jar_corrupto_con_nombre", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorJarCorruptoConNombre);
	}

	// ==================== ErrorVersionInvalidaModMaven ====================

	@Test
	void versionMavenInvalidaDetectaError() throws Exception {
		ErrorVersionInvalidaModMaven v = new ErrorVersionInvalidaModMaven();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"org.apache.maven.artifact.versioning.InvalidVersionSpecificationException: Single version must be surrounded by []: 1.0.0", 10);
		assertTrue(v.activado());
	}

	@Test
	void versionMavenInvalidaNoSeActivaConLineaNormal() throws Exception {
		ErrorVersionInvalidaModMaven v = new ErrorVersionInvalidaModMaven();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Version 1.0.0 loaded", 1);
		assertFalse(v.activado());
	}

	@Test
	void versionMavenInvalidaIdentidad() {
		ErrorVersionInvalidaModMaven v = new ErrorVersionInvalidaModMaven();
		assertEquals("error_version_invalida_mod", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorVersionInvalidaModMaven);
	}

	// ==================== VersionInvalidaSemver ====================

	@Test
	void versionSemverInvalidaDetectaError() throws Exception {
		VersionInvalidaSemver v = new VersionInvalidaSemver();
		v.verificarPorLinea(ConsolaPrueba.crear(),
				"IllegalArgumentException: 1.0.0-: Empty pre-release", 10);
		assertTrue(v.activado());
	}

	@Test
	void versionSemverInvalidaNoSeActivaConLineaNormal() throws Exception {
		VersionInvalidaSemver v = new VersionInvalidaSemver();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Version 1.2.3 loaded", 1);
		assertFalse(v.activado());
	}

	@Test
	void versionSemverInvalidaIdentidad() {
		VersionInvalidaSemver v = new VersionInvalidaSemver();
		assertEquals("version_invalida_semver", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof VersionInvalidaSemver);
	}
}
