package com.asbestosstar.crashdetector.pruebas;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaEjecucionHookCanary;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorBaseDeDatosCanary;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorComandoCanary;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorConsistenciaHookCanary;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorDependenciaPluginCanary;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorPluginInvalidoCanary;

/**
 * Pruebas unitarias de las verificaciones de Canary/Neptune.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorPluginInvalidoCanary",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorDependenciaPluginCanary",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorConsistenciaHookCanary",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaEjecucionHookCanary",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorBaseDeDatosCanary",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorComandoCanary" },
		nota = "Cubre deteccion de errores de plugins, hooks, base de datos y comandos en Canary/Neptune")
public class PruebaCanary {

	// ==================== ErrorPluginInvalidoCanary ====================

	@Test
	public void testDeteccion_InvalidPluginException_Canary() throws IOException {
		ErrorPluginInvalidoCanary v = new ErrorPluginInvalidoCanary();
		String[] lineas = { "Starting: CanaryMod 1.0.0",
				"net.canarymod.exceptions.InvalidPluginException: Plugin is corrupted" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testDeteccion_CouldNotFindMainclass_Canary() throws IOException {
		ErrorPluginInvalidoCanary v = new ErrorPluginInvalidoCanary();
		String[] lineas = { "Canary Path: /opt/canary",
				"ERROR: Could not find databases mainclass" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testDeteccion_InvalidPluginException_Neptune() throws IOException {
		ErrorPluginInvalidoCanary v = new ErrorPluginInvalidoCanary();
		String[] lineas = { "Starting: Neptune 1.5.0",
				"net.canarymod.exceptions.InvalidPluginException: Bad plugin" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testFalsoPositivo_InvalidPluginSinContexto() throws IOException {
		ErrorPluginInvalidoCanary v = new ErrorPluginInvalidoCanary();
		String linea = "net.canarymod.exceptions.InvalidPluginException: Something";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	public void testFalsoPositivo_CouldNotFindMainclassSinContexto() throws IOException {
		ErrorPluginInvalidoCanary v = new ErrorPluginInvalidoCanary();
		String linea = "Could not find databases mainclass in other app";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	public void testCriticalidad_ErrorPluginInvalido() {
		ErrorPluginInvalidoCanary v = new ErrorPluginInvalidoCanary();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	public void testPrioridad_ErrorPluginInvalido() {
		ErrorPluginInvalidoCanary v = new ErrorPluginInvalidoCanary();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	public void testId_ErrorPluginInvalido() {
		ErrorPluginInvalidoCanary v = new ErrorPluginInvalidoCanary();
		assertEquals("error_plugin_invalido_canary", v.id());
	}

	// ==================== ErrorDependenciaPluginCanary ====================

	@Test
	public void testDeteccion_PluginDependencyException_Canary() throws IOException {
		ErrorDependenciaPluginCanary v = new ErrorDependenciaPluginCanary();
		String[] lineas = { "CanaryMod loading plugins...",
				"net.canarymod.exceptions.PluginDependencyException: Missing dependency" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testDeteccion_PluginDependencyException_Neptune() throws IOException {
		ErrorDependenciaPluginCanary v = new ErrorDependenciaPluginCanary();
		String[] lineas = { "Neptune Path: /opt/neptune",
				"net.canarymod.exceptions.PluginDependencyException: Requires WorldEdit" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testFalsoPositivo_PluginDependencySinContexto() throws IOException {
		ErrorDependenciaPluginCanary v = new ErrorDependenciaPluginCanary();
		String linea = "net.canarymod.exceptions.PluginDependencyException: Missing";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	public void testCriticalidad_ErrorDependenciaPlugin() {
		ErrorDependenciaPluginCanary v = new ErrorDependenciaPluginCanary();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	public void testPrioridad_ErrorDependenciaPlugin() {
		ErrorDependenciaPluginCanary v = new ErrorDependenciaPluginCanary();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	public void testId_ErrorDependenciaPlugin() {
		ErrorDependenciaPluginCanary v = new ErrorDependenciaPluginCanary();
		assertEquals("error_dependencia_plugin_canary", v.id());
	}

	// ==================== ErrorConsistenciaHookCanary ====================

	@Test
	public void testDeteccion_HookConsistencyException_Parameters() throws IOException {
		ErrorConsistenciaHookCanary v = new ErrorConsistenciaHookCanary();
		String[] lineas = { "CanaryMod hook registration...",
				"net.canarymod.hook.HookConsistencyException: Amount of parameters for onJoin is invalid" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testDeteccion_HookConsistencyException_NotAssignable() throws IOException {
		ErrorConsistenciaHookCanary v = new ErrorConsistenciaHookCanary();
		String[] lineas = { "Neptune loading plugins...",
				"net.canarymod.hook.HookConsistencyException: Hook is not assignable from java.lang.String" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testFalsoPositivo_HookConsistencySinContexto() throws IOException {
		ErrorConsistenciaHookCanary v = new ErrorConsistenciaHookCanary();
		String linea = "net.canarymod.hook.HookConsistencyException: Something";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	public void testCriticalidad_ErrorConsistenciaHook() {
		ErrorConsistenciaHookCanary v = new ErrorConsistenciaHookCanary();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	public void testPrioridad_ErrorConsistenciaHook() {
		ErrorConsistenciaHookCanary v = new ErrorConsistenciaHookCanary();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	public void testId_ErrorConsistenciaHook() {
		ErrorConsistenciaHookCanary v = new ErrorConsistenciaHookCanary();
		assertEquals("error_consistencia_hook_canary", v.id());
	}

	// ==================== AdvertenciaEjecucionHookCanary ====================

	@Test
	public void testDeteccion_ExceptionWhileExecutingHook_Canary() throws IOException {
		AdvertenciaEjecucionHookCanary v = new AdvertenciaEjecucionHookCanary();
		String[] lineas = { "CanaryMod running...",
				"ERROR: Exception while executing Hook: PlayerJoinHook in PluginListener: MyListener (Plugin: MyPlugin)" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testDeteccion_ExceptionWhileExecutingHook_Neptune() throws IOException {
		AdvertenciaEjecucionHookCanary v = new AdvertenciaEjecucionHookCanary();
		String[] lineas = { "Neptune server running...",
				"ERROR: Exception while executing Hook: BlockBreakHook in PluginListener: Handler (Plugin: Test)" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testFalsoPositivo_ExceptionWhileExecutingSinContexto() throws IOException {
		AdvertenciaEjecucionHookCanary v = new AdvertenciaEjecucionHookCanary();
		String linea = "Exception while executing Hook: Something";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	public void testFalsoPositivo_ExceptionGeneral_Canary() throws IOException {
		AdvertenciaEjecucionHookCanary v = new AdvertenciaEjecucionHookCanary();
		String[] lineas = { "CanaryMod running...", "java.lang.NullPointerException: Something else" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	public void testCriticalidad_AdvertenciaEjecucionHook() {
		AdvertenciaEjecucionHookCanary v = new AdvertenciaEjecucionHookCanary();
		assertEquals(Criticalidad.ADVERTENCIA, v.nivel_de_criticalidad());
	}

	@Test
	public void testPrioridad_AdvertenciaEjecucionHook() {
		AdvertenciaEjecucionHookCanary v = new AdvertenciaEjecucionHookCanary();
		assertEquals(500f, v.prioridad(), 0.01f);
	}

	@Test
	public void testId_AdvertenciaEjecucionHook() {
		AdvertenciaEjecucionHookCanary v = new AdvertenciaEjecucionHookCanary();
		assertEquals("advertencia_ejecucion_hook_canary", v.id());
	}

	// ==================== ErrorBaseDeDatosCanary ====================

	@Test
	public void testDeteccion_DatabaseAccessException_Canary() throws IOException {
		ErrorBaseDeDatosCanary v = new ErrorBaseDeDatosCanary();
		String[] lineas = { "CanaryMod database init...",
				"net.canarymod.database.exceptions.DatabaseAccessException: Connection refused" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testDeteccion_CouldNotAddDatabase_Canary() throws IOException {
		ErrorBaseDeDatosCanary v = new ErrorBaseDeDatosCanary();
		String[] lineas = { "Canary loading db adapters...",
				"ERROR: Could not add database: already registered" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testDeteccion_DatabaseAccessException_Neptune() throws IOException {
		ErrorBaseDeDatosCanary v = new ErrorBaseDeDatosCanary();
		String[] lineas = { "Neptune database init...",
				"net.canarymod.database.exceptions.DatabaseAccessException: Table not found" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testFalsoPositivo_DatabaseAccessSinContexto() throws IOException {
		ErrorBaseDeDatosCanary v = new ErrorBaseDeDatosCanary();
		String linea = "net.canarymod.database.exceptions.DatabaseAccessException: Something";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	public void testFalsoPositivo_CouldNotAddDatabaseSinContexto() throws IOException {
		ErrorBaseDeDatosCanary v = new ErrorBaseDeDatosCanary();
		String linea = "Could not add database to the system";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	public void testCriticalidad_ErrorBaseDeDatos() {
		ErrorBaseDeDatosCanary v = new ErrorBaseDeDatosCanary();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	public void testPrioridad_ErrorBaseDeDatos() {
		ErrorBaseDeDatosCanary v = new ErrorBaseDeDatosCanary();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	public void testId_ErrorBaseDeDatos() {
		ErrorBaseDeDatosCanary v = new ErrorBaseDeDatosCanary();
		assertEquals("error_base_de_datos_canary", v.id());
	}

	// ==================== ErrorComandoCanary ====================

	@Test
	public void testDeteccion_CommandDependencyException_Canary() throws IOException {
		ErrorComandoCanary v = new ErrorComandoCanary();
		String[] lineas = { "CanaryMod registering commands...",
				"net.canarymod.commandsys.CommandDependencyException: Parent not found" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testDeteccion_DuplicateCommandException_Canary() throws IOException {
		ErrorComandoCanary v = new ErrorComandoCanary();
		String[] lineas = { "CanaryMod loading plugins...",
				"net.canarymod.commandsys.DuplicateCommandException: The command give already exists!" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testDeteccion_FailedSystemCommands_Canary() throws IOException {
		ErrorComandoCanary v = new ErrorComandoCanary();
		String[] lineas = { "CanaryMod initializing...",
				"ERROR: Failed to set up system commands! Dependency resolution failed!" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testDeteccion_DuplicateCommandException_Neptune() throws IOException {
		ErrorComandoCanary v = new ErrorComandoCanary();
		String[] lineas = { "Neptune loading commands...",
				"net.canarymod.commandsys.DuplicateCommandException: The command warp already exists!" };
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	public void testFalsoPositivo_CommandDependencySinContexto() throws IOException {
		ErrorComandoCanary v = new ErrorComandoCanary();
		String linea = "net.canarymod.commandsys.CommandDependencyException: Something";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	public void testFalsoPositivo_DuplicateCommandSinContexto() throws IOException {
		ErrorComandoCanary v = new ErrorComandoCanary();
		String linea = "net.canarymod.commandsys.DuplicateCommandException: The command test already exists!";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	public void testFalsoPositivo_FailedSystemCommandsSinContexto() throws IOException {
		ErrorComandoCanary v = new ErrorComandoCanary();
		String linea = "Failed to set up system commands!";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	public void testCriticalidad_ErrorComando() {
		ErrorComandoCanary v = new ErrorComandoCanary();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	public void testPrioridad_ErrorComando() {
		ErrorComandoCanary v = new ErrorComandoCanary();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	public void testId_ErrorComando() {
		ErrorComandoCanary v = new ErrorComandoCanary();
		assertEquals("error_comando_canary", v.id());
	}

	// ==================== Null safety ====================

	@Test
	public void testNulo_ErrorPluginInvalido_lineaNula() throws IOException {
		ErrorPluginInvalidoCanary v = new ErrorPluginInvalidoCanary();
		Consola consola = ConsolaPrueba.crearConLineas("CanaryMod running...");
		v.verificarPorLinea(consola, null, 0);
		assertFalse(v.activado());
	}

	@Test
	public void testNulo_ErrorDependenciaPlugin_lineaNula() throws IOException {
		ErrorDependenciaPluginCanary v = new ErrorDependenciaPluginCanary();
		Consola consola = ConsolaPrueba.crearConLineas("CanaryMod running...");
		v.verificarPorLinea(consola, null, 0);
		assertFalse(v.activado());
	}

	@Test
	public void testNulo_ErrorConsistenciaHook_lineaNula() throws IOException {
		ErrorConsistenciaHookCanary v = new ErrorConsistenciaHookCanary();
		Consola consola = ConsolaPrueba.crearConLineas("CanaryMod running...");
		v.verificarPorLinea(consola, null, 0);
		assertFalse(v.activado());
	}

	@Test
	public void testNulo_AdvertenciaEjecucionHook_lineaNula() throws IOException {
		AdvertenciaEjecucionHookCanary v = new AdvertenciaEjecucionHookCanary();
		Consola consola = ConsolaPrueba.crearConLineas("CanaryMod running...");
		v.verificarPorLinea(consola, null, 0);
		assertFalse(v.activado());
	}

	@Test
	public void testNulo_ErrorBaseDeDatos_lineaNula() throws IOException {
		ErrorBaseDeDatosCanary v = new ErrorBaseDeDatosCanary();
		Consola consola = ConsolaPrueba.crearConLineas("CanaryMod running...");
		v.verificarPorLinea(consola, null, 0);
		assertFalse(v.activado());
	}

	@Test
	public void testNulo_ErrorComando_lineaNula() throws IOException {
		ErrorComandoCanary v = new ErrorComandoCanary();
		Consola consola = ConsolaPrueba.crearConLineas("CanaryMod running...");
		v.verificarPorLinea(consola, null, 0);
		assertFalse(v.activado());
	}

	// ==================== Identity checks ====================

	@Test
	public void testIdentidad_ErrorPluginInvalido() {
		ErrorPluginInvalidoCanary v1 = new ErrorPluginInvalidoCanary();
		assertNotSame(v1, v1.nueva());
	}

	@Test
	public void testIdentidad_ErrorDependenciaPlugin() {
		ErrorDependenciaPluginCanary v1 = new ErrorDependenciaPluginCanary();
		assertNotSame(v1, v1.nueva());
	}

	@Test
	public void testIdentidad_ErrorConsistenciaHook() {
		ErrorConsistenciaHookCanary v1 = new ErrorConsistenciaHookCanary();
		assertNotSame(v1, v1.nueva());
	}

	@Test
	public void testIdentidad_AdvertenciaEjecucionHook() {
		AdvertenciaEjecucionHookCanary v1 = new AdvertenciaEjecucionHookCanary();
		assertNotSame(v1, v1.nueva());
	}

	@Test
	public void testIdentidad_ErrorBaseDeDatos() {
		ErrorBaseDeDatosCanary v1 = new ErrorBaseDeDatosCanary();
		assertNotSame(v1, v1.nueva());
	}

	@Test
	public void testIdentidad_ErrorComando() {
		ErrorComandoCanary v1 = new ErrorComandoCanary();
		assertNotSame(v1, v1.nueva());
	}

	// ==================== Message validation ====================

	@Test
	public void testMensajeNoVacio_ErrorPluginInvalido() {
		ErrorPluginInvalidoCanary v = new ErrorPluginInvalidoCanary();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
	}

	@Test
	public void testMensajeNoVacio_ErrorDependenciaPlugin() {
		ErrorDependenciaPluginCanary v = new ErrorDependenciaPluginCanary();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
	}

	@Test
	public void testMensajeNoVacio_ErrorConsistenciaHook() {
		ErrorConsistenciaHookCanary v = new ErrorConsistenciaHookCanary();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
	}

	@Test
	public void testMensajeNoVacio_AdvertenciaEjecucionHook() {
		AdvertenciaEjecucionHookCanary v = new AdvertenciaEjecucionHookCanary();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
	}

	@Test
	public void testMensajeNoVacio_ErrorBaseDeDatos() {
		ErrorBaseDeDatosCanary v = new ErrorBaseDeDatosCanary();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
	}

	@Test
	public void testMensajeNoVacio_ErrorComando() {
		ErrorComandoCanary v = new ErrorComandoCanary();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
	}

	// ==================== PatronesRapidos ====================

	@Test
	public void testPatronesRapidos_ErrorPluginInvalido() {
		ErrorPluginInvalidoCanary v = new ErrorPluginInvalidoCanary();
		assertTrue(v.patronesRapidos().length > 0);
	}

	@Test
	public void testPatronesRapidos_ErrorDependenciaPlugin() {
		ErrorDependenciaPluginCanary v = new ErrorDependenciaPluginCanary();
		assertTrue(v.patronesRapidos().length > 0);
	}

	@Test
	public void testPatronesRapidos_ErrorConsistenciaHook() {
		ErrorConsistenciaHookCanary v = new ErrorConsistenciaHookCanary();
		assertTrue(v.patronesRapidos().length > 0);
	}

	@Test
	public void testPatronesRapidos_AdvertenciaEjecucionHook() {
		AdvertenciaEjecucionHookCanary v = new AdvertenciaEjecucionHookCanary();
		assertTrue(v.patronesRapidos().length > 0);
	}

	@Test
	public void testPatronesRapidos_ErrorBaseDeDatos() {
		ErrorBaseDeDatosCanary v = new ErrorBaseDeDatosCanary();
		assertTrue(v.patronesRapidos().length > 0);
	}

	@Test
	public void testPatronesRapidos_ErrorComando() {
		ErrorComandoCanary v = new ErrorComandoCanary();
		assertTrue(v.patronesRapidos().length > 0);
	}
}
