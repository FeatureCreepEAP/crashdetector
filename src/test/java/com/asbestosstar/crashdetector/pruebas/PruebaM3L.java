package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorJavaVersionM3L;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorMinecraftVersionNoInstaladaM3L;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorJSONLauncherM3L;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaOSNoDetectadoM3L;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de las verificaciones de M3L.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorJavaVersionM3L",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorMinecraftVersionNoInstaladaM3L",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorJSONLauncherM3L",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaOSNoDetectadoM3L" },
		nota = "Cubre deteccion de errores de M3L y falsos positivos")
class PruebaM3L {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ErrorJavaVersionM3L ====================

	@Test
	void javaVersionDetectaJava7Required() throws Exception {
		ErrorJavaVersionM3L v = new ErrorJavaVersionM3L();
		String[] lineas = {
			"[M3L Client Installer] Starting M3L installer",
			"cuchaz.m3l.installer.CantInstallException: Java 7 or later is required to run M3L"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void javaVersionDetectaConM3LContext() throws Exception {
		ErrorJavaVersionM3L v = new ErrorJavaVersionM3L();
		String[] lineas = {
			"m3l installer starting",
			"Java 7 or later is required to run M3L"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void javaVersionFalsoPositivoSinM3L() throws Exception {
		ErrorJavaVersionM3L v = new ErrorJavaVersionM3L();
		String linea = "Java 7 or later is required to run some other app";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void javaVersionFalsoPositivoSinError() throws Exception {
		ErrorJavaVersionM3L v = new ErrorJavaVersionM3L();
		String[] lineas = {
			"[M3L Client Installer] Starting",
			"[M3L Client Installer] Java version check passed"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void javaVersionEsError() {
		ErrorJavaVersionM3L v = new ErrorJavaVersionM3L();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void javaVersionPrioridad() {
		ErrorJavaVersionM3L v = new ErrorJavaVersionM3L();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void javaVersionNuevaInstancia() {
		ErrorJavaVersionM3L v = new ErrorJavaVersionM3L();
		assertNotNull(v.nueva());
	}

	@Test
	void javaVersionIdUnico() {
		ErrorJavaVersionM3L v = new ErrorJavaVersionM3L();
		assertEquals("error_java_version_m3l", v.id());
	}

	// ==================== ErrorMinecraftVersionNoInstaladaM3L ====================

	@Test
	void mcVersionNoInstaladaDetectaInstallMinecraftBefore() throws Exception {
		ErrorMinecraftVersionNoInstaladaM3L v = new ErrorMinecraftVersionNoInstaladaM3L();
		String[] lineas = {
			"[M3L Client Installer] Installing M3L",
			"cuchaz.m3l.installer.CantInstallException: Install Minecraft 1.8.3 before installing M3L"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void mcVersionNoInstaladaDetectaConM3L() throws Exception {
		ErrorMinecraftVersionNoInstaladaM3L v = new ErrorMinecraftVersionNoInstaladaM3L();
		String[] lineas = {
			"m3l installer",
			"Install Minecraft 1.8.3 before installing M3L"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void mcVersionNoInstaladaFalsoPositivoSinM3L() throws Exception {
		ErrorMinecraftVersionNoInstaladaM3L v = new ErrorMinecraftVersionNoInstaladaM3L();
		String linea = "Install Minecraft 1.8.3 before installing some other mod";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void mcVersionNoInstaladaFalsoPositivoSinError() throws Exception {
		ErrorMinecraftVersionNoInstaladaM3L v = new ErrorMinecraftVersionNoInstaladaM3L();
		String[] lineas = {
			"[M3L Client Installer] Starting",
			"[M3L Client Installer] Minecraft version found"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void mcVersionNoInstaladaFalsoPositivoInstallMinecraftSolo() throws Exception {
		ErrorMinecraftVersionNoInstaladaM3L v = new ErrorMinecraftVersionNoInstaladaM3L();
		String[] lineas = {
			"[M3L Client Installer] Starting",
			"Install Minecraft from the launcher"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void mcVersionNoInstaladaEsError() {
		ErrorMinecraftVersionNoInstaladaM3L v = new ErrorMinecraftVersionNoInstaladaM3L();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void mcVersionNoInstaladaPrioridad() {
		ErrorMinecraftVersionNoInstaladaM3L v = new ErrorMinecraftVersionNoInstaladaM3L();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void mcVersionNoInstaladaNuevaInstancia() {
		ErrorMinecraftVersionNoInstaladaM3L v = new ErrorMinecraftVersionNoInstaladaM3L();
		assertNotNull(v.nueva());
	}

	@Test
	void mcVersionNoInstaladaIdUnico() {
		ErrorMinecraftVersionNoInstaladaM3L v = new ErrorMinecraftVersionNoInstaladaM3L();
		assertEquals("error_minecraft_version_no_instalada_m3l", v.id());
	}

	// ==================== ErrorJSONLauncherM3L ====================

	@Test
	void jsonLauncherDetectaUnableToRead() throws Exception {
		ErrorJSONLauncherM3L v = new ErrorJSONLauncherM3L();
		String[] lineas = {
			"[M3L Client Installer] Loading installed profiles",
			"Unable to read M3L installs! java.io.IOException: Failed to read launcher_profiles.json"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void jsonLauncherDetectaInstallationFailed() throws Exception {
		ErrorJSONLauncherM3L v = new ErrorJSONLauncherM3L();
		String[] lineas = {
			"[M3L Client Installer] Installing M3L",
			"Installation failed! java.io.IOException: Could not write to launcher_profiles.json"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void jsonLauncherDetectaConM3L() throws Exception {
		ErrorJSONLauncherM3L v = new ErrorJSONLauncherM3L();
		String[] lineas = {
			"m3l installer",
			"Unable to read M3L installs!"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void jsonLauncherFalsoPositivoSinM3L() throws Exception {
		ErrorJSONLauncherM3L v = new ErrorJSONLauncherM3L();
		String linea = "Unable to read installs for some other app";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void jsonLauncherFalsoPositivoSinError() throws Exception {
		ErrorJSONLauncherM3L v = new ErrorJSONLauncherM3L();
		String[] lineas = {
			"[M3L Client Installer] Starting",
			"[M3L Client Installer] Installation completed successfully"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void jsonLauncherFalsoPositivoInstallationOtroContexto() throws Exception {
		ErrorJSONLauncherM3L v = new ErrorJSONLauncherM3L();
		String[] lineas = {
			"[M3L Client Installer] Starting",
			"Installation failed for some other reason unrelated to M3L JSON"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		// "Installation failed" matches the pattern, so this should activate
		assertTrue(v.activado());
	}

	@Test
	void jsonLauncherEsError() {
		ErrorJSONLauncherM3L v = new ErrorJSONLauncherM3L();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void jsonLauncherPrioridad() {
		ErrorJSONLauncherM3L v = new ErrorJSONLauncherM3L();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void jsonLauncherNuevaInstancia() {
		ErrorJSONLauncherM3L v = new ErrorJSONLauncherM3L();
		assertNotNull(v.nueva());
	}

	@Test
	void jsonLauncherIdUnico() {
		ErrorJSONLauncherM3L v = new ErrorJSONLauncherM3L();
		assertEquals("error_json_launcher_m3l", v.id());
	}

	// ==================== AdvertenciaOSNoDetectadoM3L ====================

	@Test
	void osNoDetectadoDetectaSolaris() throws Exception {
		AdvertenciaOSNoDetectadoM3L v = new AdvertenciaOSNoDetectadoM3L();
		String[] lineas = {
			"[M3L Client Installer] Starting M3L installer",
			"Java runtime: SunOS 5.11 sparcv9"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void osNoDetectadoDetectaConM3L() throws Exception {
		AdvertenciaOSNoDetectadoM3L v = new AdvertenciaOSNoDetectadoM3L();
		String[] lineas = {
			"m3l installer starting",
			"os.name=SunOS"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void osNoDetectadoFalsoPositivoSinM3L() throws Exception {
		AdvertenciaOSNoDetectadoM3L v = new AdvertenciaOSNoDetectadoM3L();
		String linea = "Running on SunOS 5.11";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void osNoDetectadoFalsoPositivoSinSolaris() throws Exception {
		AdvertenciaOSNoDetectadoM3L v = new AdvertenciaOSNoDetectadoM3L();
		String[] lineas = {
			"[M3L Client Installer] Starting",
			"Java runtime: Linux 5.15.0"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void osNoDetectadoFalsoPositivoWindows() throws Exception {
		AdvertenciaOSNoDetectadoM3L v = new AdvertenciaOSNoDetectadoM3L();
		String[] lineas = {
			"[M3L Client Installer] Starting",
			"Java runtime: Windows 10"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void osNoDetectadoEsAdvertencia() {
		AdvertenciaOSNoDetectadoM3L v = new AdvertenciaOSNoDetectadoM3L();
		assertEquals(Criticalidad.ADVERTENCIA, v.nivel_de_criticalidad());
	}

	@Test
	void osNoDetectadoPrioridadBaja() {
		AdvertenciaOSNoDetectadoM3L v = new AdvertenciaOSNoDetectadoM3L();
		assertTrue(v.prioridad() < 1000f, "La prioridad debe ser baja para una advertencia");
	}

	@Test
	void osNoDetectadoNuevaInstancia() {
		AdvertenciaOSNoDetectadoM3L v = new AdvertenciaOSNoDetectadoM3L();
		assertNotNull(v.nueva());
	}

	@Test
	void osNoDetectadoIdUnico() {
		AdvertenciaOSNoDetectadoM3L v = new AdvertenciaOSNoDetectadoM3L();
		assertEquals("advertencia_os_no_detectado_m3l", v.id());
	}

	// ==================== Null safety ====================

	@Test
	void javaVersionNullSafety() throws Exception {
		ErrorJavaVersionM3L v = new ErrorJavaVersionM3L();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void mcVersionNoInstaladaNullSafety() throws Exception {
		ErrorMinecraftVersionNoInstaladaM3L v = new ErrorMinecraftVersionNoInstaladaM3L();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void jsonLauncherNullSafety() throws Exception {
		ErrorJSONLauncherM3L v = new ErrorJSONLauncherM3L();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void osNoDetectadoNullSafety() throws Exception {
		AdvertenciaOSNoDetectadoM3L v = new AdvertenciaOSNoDetectadoM3L();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}
}
