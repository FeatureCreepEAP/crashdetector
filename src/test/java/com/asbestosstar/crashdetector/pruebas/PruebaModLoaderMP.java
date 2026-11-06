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
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorRegistroPacketModLoaderMP;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorEntidadPersonalizadaModLoaderMP;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorModNuloModLoaderMP;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorDatosPacketModLoaderMP;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaRegistroDuplicadoModLoaderMP;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de las verificaciones de ModLoaderMP.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorRegistroPacketModLoaderMP",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorEntidadPersonalizadaModLoaderMP",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorModNuloModLoaderMP",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ErrorDatosPacketModLoaderMP",
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.AdvertenciaRegistroDuplicadoModLoaderMP" },
		nota = "Cubre deteccion de errores de ModLoaderMP y falsos positivos")
class PruebaModLoaderMP {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ErrorRegistroPacketModLoaderMP ====================

	@Test
	void registroPacketDetectaImpossibleError() throws Exception {
		ErrorRegistroPacketModLoaderMP v = new ErrorRegistroPacketModLoaderMP();
		String[] lineas = {
			"ModLoaderMP 1.3.2v1 Initialized",
			"An impossible error has occurred!"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void registroPacketDetectaConThrowing() throws Exception {
		ErrorRegistroPacketModLoaderMP v = new ErrorRegistroPacketModLoaderMP();
		String[] lineas = {
			"ModLoaderMP initialized",
			"FINE: ModLoaderMp.init - An impossible error has occurred!"
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void registroPacketDetectaEnMismaLinea() throws Exception {
		ErrorRegistroPacketModLoaderMP v = new ErrorRegistroPacketModLoaderMP();
		String linea = "[ModLoaderMp] An impossible error has occurred!";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertTrue(v.activado());
	}

	@Test
	void registroPacketFalsoPositivoSinContexto() throws Exception {
		ErrorRegistroPacketModLoaderMP v = new ErrorRegistroPacketModLoaderMP();
		String linea = "An impossible error has occurred!";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void registroPacketFalsoPositivoInicializacionExitosa() throws Exception {
		ErrorRegistroPacketModLoaderMP v = new ErrorRegistroPacketModLoaderMP();
		String linea = "ModLoaderMP 1.3.2v1 Initialized";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void registroPacketFalsoPositivoModLoaderSinMP() throws Exception {
		ErrorRegistroPacketModLoaderMP v = new ErrorRegistroPacketModLoaderMP();
		String linea = "ModLoader has failed to initialize.";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void registroPacketEsError() {
		ErrorRegistroPacketModLoaderMP v = new ErrorRegistroPacketModLoaderMP();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertEquals(1800f, v.prioridad());
		assertEquals("error_registro_packet_modloadermp", v.id());
	}

	// ==================== ErrorEntidadPersonalizadaModLoaderMP ====================

	@Test
	void entidadDetectaErrorInitializing() throws Exception {
		ErrorEntidadPersonalizadaModLoaderMP v = new ErrorEntidadPersonalizadaModLoaderMP();
		String[] lineas = {
			"ModLoaderMP 1.3.2v1 Initialized",
			"Error initializing entity of type 90."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void entidadDetectaConClase() throws Exception {
		ErrorEntidadPersonalizadaModLoaderMP v = new ErrorEntidadPersonalizadaModLoaderMP();
		String linea = "[ModLoaderMp] Error initializing entity of type com.example.MyEntity.";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertTrue(v.activado());
	}

	@Test
	void entidadDetectaDesdeHandleCustomSpawn() throws Exception {
		ErrorEntidadPersonalizadaModLoaderMP v = new ErrorEntidadPersonalizadaModLoaderMP();
		String[] lineas = {
			"ModLoaderMP initialized",
			"FINE: ModLoader.handleCustomSpawn - Error initializing entity of type 60."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void entidadFalsoPositivoSinContexto() throws Exception {
		ErrorEntidadPersonalizadaModLoaderMP v = new ErrorEntidadPersonalizadaModLoaderMP();
		String linea = "Error initializing entity of type zombie.";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void entidadFalsoPositivoOwnerNoEncontrado() throws Exception {
		ErrorEntidadPersonalizadaModLoaderMP v = new ErrorEntidadPersonalizadaModLoaderMP();
		String[] lineas = {
			"ModLoaderMP 1.3.2v1 Initialized",
			"Received spawn packet for entity with owner, but owner was not found."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void entidadEsError() {
		ErrorEntidadPersonalizadaModLoaderMP v = new ErrorEntidadPersonalizadaModLoaderMP();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertEquals(1800f, v.prioridad());
		assertEquals("error_entidad_personalizada_modloadermp", v.id());
	}

	// ==================== ErrorModNuloModLoaderMP ====================

	@Test
	void modNuloDetectaSendPacket() throws Exception {
		ErrorModNuloModLoaderMP v = new ErrorModNuloModLoaderMP();
		String[] lineas = {
			"ModLoaderMP 1.3.2v1 Initialized",
			"baseModMp cannot be null."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void modNuloDetectaSendKey() throws Exception {
		ErrorModNuloModLoaderMP v = new ErrorModNuloModLoaderMP();
		String linea = "[ModLoaderMp] SendKey - baseModMp cannot be null.";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertTrue(v.activado());
	}

	@Test
	void modNuloDetectaConIllegalArgumentException() throws Exception {
		ErrorModNuloModLoaderMP v = new ErrorModNuloModLoaderMP();
		String[] lineas = {
			"ModLoaderMP initialized",
			"java.lang.IllegalArgumentException: baseModMp cannot be null."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void modNuloFalsoPositivoSinContexto() throws Exception {
		ErrorModNuloModLoaderMP v = new ErrorModNuloModLoaderMP();
		String linea = "baseModMp cannot be null.";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void modNuloEsError() {
		ErrorModNuloModLoaderMP v = new ErrorModNuloModLoaderMP();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertEquals(1800f, v.prioridad());
		assertEquals("error_mod_nulo_modloadermp", v.id());
	}

	// ==================== ErrorDatosPacketModLoaderMP ====================

	@Test
	void datosPacketDetectaIntegerOverflow() throws Exception {
		ErrorDatosPacketModLoaderMP v = new ErrorDatosPacketModLoaderMP();
		String[] lineas = {
			"ModLoaderMP 1.3.2v1 Initialized",
			"java.io.IOException: Integer data size of 70000 is higher than the max (65535)."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void datosPacketDetectaFloatOverflow() throws Exception {
		ErrorDatosPacketModLoaderMP v = new ErrorDatosPacketModLoaderMP();
		String linea = "[ModLoaderMp] Float data size of 100000 is higher than the max (65535).";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertTrue(v.activado());
	}

	@Test
	void datosPacketDetectaDoubleOverflow() throws Exception {
		ErrorDatosPacketModLoaderMP v = new ErrorDatosPacketModLoaderMP();
		String[] lineas = {
			"ModLoaderMP initialized",
			"Double data size of 99999 is higher than the max (65535)."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void datosPacketDetectaStringOverflow() throws Exception {
		ErrorDatosPacketModLoaderMP v = new ErrorDatosPacketModLoaderMP();
		String[] lineas = {
			"ModLoaderMP 1.3.2v1 Initialized",
			"String data size of 70000 is higher than the max (65535)."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void datosPacketDetectaStringLenght() throws Exception {
		ErrorDatosPacketModLoaderMP v = new ErrorDatosPacketModLoaderMP();
		String linea = "[ModLoaderMp] String length of 70000 is higher than the max (65535).";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertTrue(v.activado());
	}

	@Test
	void datosPacketFalsoPositivoSinContexto() throws Exception {
		ErrorDatosPacketModLoaderMP v = new ErrorDatosPacketModLoaderMP();
		String linea = "Integer data size of 70000 is higher than the max (65535).";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void datosPacketFalsoPositivoBadTileEntityPacket() throws Exception {
		ErrorDatosPacketModLoaderMP v = new ErrorDatosPacketModLoaderMP();
		String[] lineas = {
			"ModLoaderMP 1.3.2v1 Initialized",
			"Bad TileEntityPacket received."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void datosPacketFalsoPositivoTamañoValido() throws Exception {
		ErrorDatosPacketModLoaderMP v = new ErrorDatosPacketModLoaderMP();
		String[] lineas = {
			"ModLoaderMP 1.3.2v1 Initialized",
			"Packet sent successfully with 100 data elements."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void datosPacketEsError() {
		ErrorDatosPacketModLoaderMP v = new ErrorDatosPacketModLoaderMP();
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertEquals(1800f, v.prioridad());
		assertEquals("error_datos_packet_modloadermp", v.id());
	}

	// ==================== AdvertenciaRegistroDuplicadoModLoaderMP ====================

	@Test
	void registroDuplicadoDetectaGUI() throws Exception {
		AdvertenciaRegistroDuplicadoModLoaderMP v = new AdvertenciaRegistroDuplicadoModLoaderMP();
		String[] lineas = {
			"ModLoaderMP 1.3.2v1 Initialized",
			"RegisterGUI error: inventoryType already registered."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertTrue(v.activado());
	}

	@Test
	void registroDuplicadoDetectaEntityId() throws Exception {
		AdvertenciaRegistroDuplicadoModLoaderMP v = new AdvertenciaRegistroDuplicadoModLoaderMP();
		String linea = "[ModLoaderMp] RegisterNetClientHandlerEntity error: entityId already registered.";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertTrue(v.activado());
	}

	@Test
	void registroDuplicadoDetectaEnMismaLinea() throws Exception {
		AdvertenciaRegistroDuplicadoModLoaderMP v = new AdvertenciaRegistroDuplicadoModLoaderMP();
		String linea = "ModLoaderMP: inventoryType already registered.";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertTrue(v.activado());
	}

	@Test
	void registroDuplicadoFalsoPositivoSinContexto() throws Exception {
		AdvertenciaRegistroDuplicadoModLoaderMP v = new AdvertenciaRegistroDuplicadoModLoaderMP();
		String linea = "inventoryType already registered.";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void registroDuplicadoFalsoPositivoRegistroExitoso() throws Exception {
		AdvertenciaRegistroDuplicadoModLoaderMP v = new AdvertenciaRegistroDuplicadoModLoaderMP();
		String[] lineas = {
			"ModLoaderMP 1.3.2v1 Initialized",
			"RegisterGUI: inventoryType 10 registered successfully."
		};
		Consola consola = ConsolaPrueba.crearConLineas(lineas);
		v.verificarPorLinea(consola, lineas[0], 1);
		v.verificarPorLinea(consola, lineas[1], 2);
		assertFalse(v.activado());
	}

	@Test
	void registroDuplicadoFalsoPositivoTextoSimilar() throws Exception {
		AdvertenciaRegistroDuplicadoModLoaderMP v = new AdvertenciaRegistroDuplicadoModLoaderMP();
		String linea = "This item is already registered in the system.";
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { linea });
		v.verificarPorLinea(consola, linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void registroDuplicadoEsAdvertencia() {
		AdvertenciaRegistroDuplicadoModLoaderMP v = new AdvertenciaRegistroDuplicadoModLoaderMP();
		assertEquals(Criticalidad.ADVERTENCIA, v.nivel_de_criticalidad());
		assertEquals(500f, v.prioridad());
		assertEquals("advertencia_registro_duplicado_modloadermp", v.id());
	}

	// ==================== Mensajes y nombres ====================

	@Test
	void mensajeRegistroPacketNoVacio() {
		ErrorRegistroPacketModLoaderMP v = new ErrorRegistroPacketModLoaderMP();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
		assertNotNull(v.nombre());
		assertFalse(v.nombre().isEmpty());
	}

	@Test
	void mensajeEntidadNoVacio() {
		ErrorEntidadPersonalizadaModLoaderMP v = new ErrorEntidadPersonalizadaModLoaderMP();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
		assertNotNull(v.nombre());
		assertFalse(v.nombre().isEmpty());
	}

	@Test
	void mensajeModNuloNoVacio() {
		ErrorModNuloModLoaderMP v = new ErrorModNuloModLoaderMP();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
		assertNotNull(v.nombre());
		assertFalse(v.nombre().isEmpty());
	}

	@Test
	void mensajeDatosPacketNoVacio() {
		ErrorDatosPacketModLoaderMP v = new ErrorDatosPacketModLoaderMP();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
		assertNotNull(v.nombre());
		assertFalse(v.nombre().isEmpty());
	}

	@Test
	void mensajeRegistroDuplicadoNoVacio() {
		AdvertenciaRegistroDuplicadoModLoaderMP v = new AdvertenciaRegistroDuplicadoModLoaderMP();
		assertNotNull(v.mensaje());
		assertFalse(v.mensaje().isEmpty());
		assertNotNull(v.nombre());
		assertFalse(v.nombre().isEmpty());
	}

	// ==================== Patrones rapidos ====================

	@Test
	void patronesRapidosRegistroPacket() {
		ErrorRegistroPacketModLoaderMP v = new ErrorRegistroPacketModLoaderMP();
		String[] patrones = v.patronesRapidos();
		assertNotNull(patrones);
		assertTrue(patrones.length > 0);
	}

	@Test
	void patronesRapidosEntidad() {
		ErrorEntidadPersonalizadaModLoaderMP v = new ErrorEntidadPersonalizadaModLoaderMP();
		String[] patrones = v.patronesRapidos();
		assertNotNull(patrones);
		assertTrue(patrones.length > 0);
	}

	@Test
	void patronesRapidosModNulo() {
		ErrorModNuloModLoaderMP v = new ErrorModNuloModLoaderMP();
		String[] patrones = v.patronesRapidos();
		assertNotNull(patrones);
		assertTrue(patrones.length > 0);
	}

	@Test
	void patronesRapidosDatosPacket() {
		ErrorDatosPacketModLoaderMP v = new ErrorDatosPacketModLoaderMP();
		String[] patrones = v.patronesRapidos();
		assertNotNull(patrones);
		assertTrue(patrones.length > 0);
	}

	@Test
	void patronesRapidosRegistroDuplicado() {
		AdvertenciaRegistroDuplicadoModLoaderMP v = new AdvertenciaRegistroDuplicadoModLoaderMP();
		String[] patrones = v.patronesRapidos();
		assertNotNull(patrones);
		assertTrue(patrones.length > 0);
	}

	// ==================== Null safety ====================

	@Test
	void registroPacketNullSafety() throws Exception {
		ErrorRegistroPacketModLoaderMP v = new ErrorRegistroPacketModLoaderMP();
		Consola consola = ConsolaPrueba.crearConLineas(new String[] { "test" });
		v.verificarPorLinea(consola, null, 0);
		v.verificarPorLinea(null, "An impossible error has occurred!", 0);
		v.verificarCoincidencia(null);
		assertFalse(v.activado());
	}
}
