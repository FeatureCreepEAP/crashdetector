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
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorMySQLConexionHMod;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorCargaPluginHMod;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorGrupoPorDefectoHMod;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaPluginHookHMod;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de las verificaciones de HMod.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorMySQLConexionHMod",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorCargaPluginHMod",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorGrupoPorDefectoHMod",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaPluginHookHMod" },
		nota = "Cubre deteccion de errores de HMod y falsos positivos")
class PruebaHMod {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ErrorMySQLConexionHMod ====================

	@Test
	void mysqlConexionDetectaUnableToRetreive() throws Exception {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		String[] lineas = {
			"[HMod] Initializing MySQL data source",
			"[SEVERE] Unable to retreive connection java.sql.SQLException: Communications link failure"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void mysqlConexionDetectaConMySQLSource() throws Exception {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		String[] lineas = {
			"MySQLSource.initialize() called",
			"SEVERE: Unable to retreive connection"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void mysqlConexionDetectaConSQLConnection() throws Exception {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		String[] lineas = {
			"getSQLConnection() called",
			"SEVERE: Unable to retreive connection"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void mysqlConexionDetectaConHMod() throws Exception {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		String[] lineas = {
			"hmod server starting",
			"SEVERE: Unable to retreive connection"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void mysqlConexionFalsoPositivoSinHMod() throws Exception {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		String linea = "SEVERE: Unable to retreive connection from some other app";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void mysqlConexionFalsoPositivoConexionExitosa() throws Exception {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		String[] lineas = {
			"[HMod] Initializing MySQL data source",
			"[INFO] MySQL connection established successfully"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void mysqlConexionEsError() {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void mysqlConexionPrioridad() {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void mysqlConexionNuevaInstancia() {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		assertNotNull(v.nueva());
	}

	@Test
	void mysqlConexionIdUnico() {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		assertEquals("error_mysql_conexion_hmod", v.id());
	}

	// ==================== ErrorCargaPluginHMod ====================

	@Test
	void cargaPluginDetectaExceptionWhileLoading() throws Exception {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		String[] lineas = {
			"[HMod] Loading plugins...",
			"[SEVERE] Exception while loading plugin java.lang.ClassNotFoundException: MyPlugin"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void cargaPluginDetectaFailedPluginFile() throws Exception {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		String[] lineas = {
			"[HMod] Loading plugins...",
			"[SEVERE] Failed to find plugin file: plugins/MyPlugin.jar. Please ensure the file exists"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void cargaPluginDetectaConPluginLoader() throws Exception {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		String[] lineas = {
			"PluginLoader.loadPlugins() called",
			"SEVERE: Exception while loading plugin"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void cargaPluginDetectaConHMod() throws Exception {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		String[] lineas = {
			"hmod server starting",
			"SEVERE: Failed to find plugin file: plugins/Test.jar"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void cargaPluginFalsoPositivoSinHMod() throws Exception {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		String linea = "Exception while loading plugin from some other app";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void cargaPluginFalsoPositivoCargaExitosa() throws Exception {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		String[] lineas = {
			"[HMod] Loading plugins...",
			"[INFO] Plugin MyPlugin loaded successfully"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void cargaPluginFalsoPositivoPluginDesactivado() throws Exception {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		String[] lineas = {
			"[HMod] Loading plugins...",
			"[INFO] Plugin MyPlugin disabled by user"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void cargaPluginEsError() {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void cargaPluginPrioridad() {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void cargaPluginNuevaInstancia() {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		assertNotNull(v.nueva());
	}

	@Test
	void cargaPluginIdUnico() {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		assertEquals("error_carga_plugin_hmod", v.id());
	}

	// ==================== ErrorGrupoPorDefectoHMod ====================

	@Test
	void grupoPorDefectoDetectaNoDefaultGroup() throws Exception {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		String[] lineas = {
			"[HMod] Loading data source",
			"[SEVERE] No default group! Expect lots of errors!"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void grupoPorDefectoDetectaConDataSource() throws Exception {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		String[] lineas = {
			"DataSource.getDefaultGroup() called",
			"SEVERE: No default group! Expect lots of errors!"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void grupoPorDefectoDetectaConHMod() throws Exception {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		String[] lineas = {
			"hmod server starting",
			"SEVERE: No default group! Expect lots of errors!"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void grupoPorDefectoFalsoPositivoSinHMod() throws Exception {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		String linea = "No default group found in some other app";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void grupoPorDefectoFalsoPositivoGrupoEncontrado() throws Exception {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		String[] lineas = {
			"[HMod] Loading data source",
			"[INFO] Default group 'Default' loaded successfully"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void grupoPorDefectoFalsoPositivoGrupoCreado() throws Exception {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		String[] lineas = {
			"[HMod] Loading data source",
			"[INFO] Created default group automatically"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void grupoPorDefectoEsError() {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
	}

	@Test
	void grupoPorDefectoPrioridad() {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		assertEquals(1800f, v.prioridad(), 0.01f);
	}

	@Test
	void grupoPorDefectoNuevaInstancia() {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		assertNotNull(v.nueva());
	}

	@Test
	void grupoPorDefectoIdUnico() {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		assertEquals("error_grupo_por_defecto_hmod", v.id());
	}

	// ==================== AdvertenciaPluginHookHMod ====================

	@Test
	void pluginHookDetectaExceptionCallingPlugin() throws Exception {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		String[] lineas = {
			"[HMod] Plugin system active",
			"[SEVERE] Exception while calling plugin function in 'MyPlugin' while calling hook: 'CHAT'."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void pluginHookDetectaConPluginLoader() throws Exception {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		String[] lineas = {
			"PluginLoader.callHook() invoked",
			"SEVERE: Exception while calling plugin function in 'com.example.MyPlugin'"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void pluginHookDetectaConHMod() throws Exception {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		String[] lineas = {
			"hmod server running",
			"SEVERE: Exception while calling plugin function in 'TestPlugin' while calling hook: 'LOGIN'."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void pluginHookDetectaDiferentesHooks() throws Exception {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		String[] lineas = {
			"[HMod] Plugin system active",
			"[SEVERE] Exception while calling plugin function in 'AntiCheat' while calling hook: 'DAMAGE'."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void pluginHookFalsoPositivoSinHMod() throws Exception {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		String linea = "Exception while calling plugin function in some other app";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void pluginHookFalsoPositivoHookExitoso() throws Exception {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		String[] lineas = {
			"[HMod] Plugin system active",
			"[INFO] Plugin MyPlugin hook CHAT processed successfully"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void pluginHookFalsoPositivoPluginDesactivado() throws Exception {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		String[] lineas = {
			"[HMod] Plugin system active",
			"[INFO] Plugin MyPlugin disabled, skipping hooks"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void pluginHookEsAdvertencia() {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		assertEquals(Criticalidad.ADVERTENCIA, v.nivel_de_criticalidad());
	}

	@Test
	void pluginHookPrioridadBaja() {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		assertEquals(500f, v.prioridad(), 0.01f);
	}

	@Test
	void pluginHookNuevaInstancia() {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		assertNotNull(v.nueva());
	}

	@Test
	void pluginHookIdUnico() {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		assertEquals("advertencia_plugin_hook_hmod", v.id());
	}

	// ==================== Null safety ====================

	@Test
	void mysqlConexionNullSafety() throws Exception {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void cargaPluginNullSafety() throws Exception {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void grupoPorDefectoNullSafety() throws Exception {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	@Test
	void pluginHookNullSafety() throws Exception {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		v.verificarPorLinea(null, "test", 1);
		v.verificarPorLinea(ConsolaPrueba.crearConLineas(new String[] {}), null, 1);
		assertFalse(v.activado());
	}

	// ==================== Identity checks ====================

	@Test
	void mysqlConexionNoEsOtro() {
		ErrorMySQLConexionHMod v = new ErrorMySQLConexionHMod();
		assertEquals("error_mysql_conexion_hmod", v.id());
		assertFalse(v.id().equals("error_carga_plugin_hmod"));
	}

	@Test
	void cargaPluginNoEsOtro() {
		ErrorCargaPluginHMod v = new ErrorCargaPluginHMod();
		assertEquals("error_carga_plugin_hmod", v.id());
		assertFalse(v.id().equals("error_mysql_conexion_hmod"));
	}

	@Test
	void grupoPorDefectoNoEsOtro() {
		ErrorGrupoPorDefectoHMod v = new ErrorGrupoPorDefectoHMod();
		assertEquals("error_grupo_por_defecto_hmod", v.id());
		assertFalse(v.id().equals("error_mysql_conexion_hmod"));
	}

	@Test
	void pluginHookNoEsOtro() {
		AdvertenciaPluginHookHMod v = new AdvertenciaPluginHookHMod();
		assertEquals("advertencia_plugin_hook_hmod", v.id());
		assertFalse(v.id().equals("error_mysql_conexion_hmod"));
	}
}
