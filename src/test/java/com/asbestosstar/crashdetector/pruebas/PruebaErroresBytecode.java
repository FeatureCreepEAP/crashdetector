package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.ErrorCampoInexistente;
import com.asbestosstar.crashdetector.analizador.general.ErrorClaseFinalExtendida;
import com.asbestosstar.crashdetector.analizador.general.ErrorMetodoAbstractoNoImplementado;
import com.asbestosstar.crashdetector.analizador.general.ErrorMetodoFinalSobrescrito;
import com.asbestosstar.crashdetector.analizador.general.ErrorMetodoInexistente;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de errores de bytecode: clase final extendida, metodo final
 * sobrescrito, metodo abstracto no implementado, metodo inexistente y campo
 * inexistente.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.ErrorClaseFinalExtendida",
		"com.asbestosstar.crashdetector.analizador.general.ErrorMetodoFinalSobrescrito",
		"com.asbestosstar.crashdetector.analizador.general.ErrorMetodoAbstractoNoImplementado",
		"com.asbestosstar.crashdetector.analizador.general.ErrorMetodoInexistente",
		"com.asbestosstar.crashdetector.analizador.general.ErrorCampoInexistente" },
		nota = "Cubre IncompatibleClassChangeError, AbstractMethodError, NoSuchMethodError y NoSuchFieldError")
class PruebaErroresBytecode {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	// ==================== ErrorClaseFinalExtendida ====================

	@Test
	void claseFinalExtendidaDetectaError() throws Exception {
		ErrorClaseFinalExtendida v = new ErrorClaseFinalExtendida();
		String linea = "java.lang.IncompatibleClassChangeError: class com.example.ModClass cannot inherit from final class net.minecraft.world.entity.Entity";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 10);
		assertTrue(v.activado());
	}

	@Test
	void claseFinalExtendidaNoSeActivaSinSeparador() throws Exception {
		ErrorClaseFinalExtendida v = new ErrorClaseFinalExtendida();
		String linea = "java.lang.IncompatibleClassChangeError: class com.example.Test";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void claseFinalExtendidaIdentidad() {
		ErrorClaseFinalExtendida v = new ErrorClaseFinalExtendida();
		assertEquals("clase_final_extendida", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorClaseFinalExtendida);
		assertEquals(820.0f, v.prioridad());
	}

	// ==================== ErrorMetodoFinalSobrescrito ====================

	@Test
	void metodoFinalSobrescritoDetectaError() throws Exception {
		ErrorMetodoFinalSobrescrito v = new ErrorMetodoFinalSobrescrito();
		String linea = "java.lang.IncompatibleClassChangeError: class qouteall.imm_ptl.core.portal.Portal overrides final method net.minecraft.world.entity.Entity.m20191()Lnet/minecraft/world/phys/AABB;";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 20);
		assertTrue(v.activado());
		assertTrue(v.mensaje().contains("Portal"));
	}

	@Test
	void metodoFinalSobrescritoNoSeActivaSinOverrides() throws Exception {
		ErrorMetodoFinalSobrescrito v = new ErrorMetodoFinalSobrescrito();
		String linea = "java.lang.IncompatibleClassChangeError: class com.example.Test";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void metodoFinalSobrescritoIdentidad() {
		ErrorMetodoFinalSobrescrito v = new ErrorMetodoFinalSobrescrito();
		assertEquals("metodo_final_sobrescrito", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorMetodoFinalSobrescrito);
		assertEquals(1200.0f, v.prioridad());
	}

	// ==================== ErrorMetodoAbstractoNoImplementado ====================

	@Test
	void metodoAbstractoDetectaErrorConInterfaz() throws Exception {
		ErrorMetodoAbstractoNoImplementado v = new ErrorMetodoAbstractoNoImplementado();
		String linea = "java.lang.AbstractMethodError: class com.example.ModClass does not define or inherit an implementation of the resolved method 'void someMethod()' of interface com.example.SomeInterface";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 30);
		assertTrue(v.activado());
	}

	@Test
	void metodoAbstractoDetectaErrorConClaseAbstracta() throws Exception {
		ErrorMetodoAbstractoNoImplementado v = new ErrorMetodoAbstractoNoImplementado();
		String linea = "java.lang.AbstractMethodError: class com.example.ModClass does not define or inherit an implementation of the resolved method 'void someMethod()' of abstract class com.example.AbstractBase";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 30);
		assertTrue(v.activado());
	}

	@Test
	void metodoAbstractoNoSeActivaSinDetalle() throws Exception {
		ErrorMetodoAbstractoNoImplementado v = new ErrorMetodoAbstractoNoImplementado();
		String linea = "java.lang.AbstractMethodError: some other error";
		v.verificarPorLinea(ConsolaPrueba.crear(), linea, 1);
		assertFalse(v.activado());
	}

	@Test
	void metodoAbstractoIdentidad() {
		ErrorMetodoAbstractoNoImplementado v = new ErrorMetodoAbstractoNoImplementado();
		assertEquals("metodo_abstracto_no_implementado", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorMetodoAbstractoNoImplementado);
		assertEquals(1200.0f, v.prioridad());
	}

	// ==================== ErrorMetodoInexistente ====================

	@Test
	void metodoInexistenteDetectaError() throws Exception {
		ErrorMetodoInexistente v = new ErrorMetodoInexistente();
		String lineaError = "java.lang.NoSuchMethodError: 'void net.minecraft.world.entity.npc.VillagerType.registerBiomeType(java.lang.Object)'";
		String lineaStack = " at net.hibiscus.naturespirit.NatureSpirit.lambda$commonSetup$0(NatureSpirit.java:148)";
		com.asbestosstar.crashdetector.Consola consola = ConsolaPrueba.crearConLineas(lineaError, lineaStack);
		v.verificarPorLinea(consola, lineaError, 0);
		assertTrue(v.activado());
	}

	@Test
	void metodoInexistenteNoSeActivaConLineaNormal() throws Exception {
		ErrorMetodoInexistente v = new ErrorMetodoInexistente();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Method loaded successfully", 1);
		assertFalse(v.activado());
	}

	@Test
	void metodoInexistenteIdentidad() {
		ErrorMetodoInexistente v = new ErrorMetodoInexistente();
		assertEquals("error_metodo_inexistente", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorMetodoInexistente);
		assertEquals(1100.0f, v.prioridad());
	}

	// ==================== ErrorCampoInexistente ====================

	@Test
	void campoInexistenteDetectaError() throws Exception {
		ErrorCampoInexistente v = new ErrorCampoInexistente();
		String lineaError = "java.lang.NoSuchFieldError: fieldX";
		String lineaStack = " at com.example.ModClass.init(ModClass.java:50)";
		com.asbestosstar.crashdetector.Consola consola = ConsolaPrueba.crearConLineas(lineaError, lineaStack);
		v.verificarPorLinea(consola, lineaError, 0);
		assertTrue(v.activado());
	}

	@Test
	void campoInexistenteDetectaCampoConComillas() throws Exception {
		ErrorCampoInexistente v = new ErrorCampoInexistente();
		String lineaError = "java.lang.NoSuchFieldError: 'Lnet/minecraft/world/level/Level; fieldX'";
		String lineaStack = " at com.example.ModClass.init(ModClass.java:50)";
		com.asbestosstar.crashdetector.Consola consola = ConsolaPrueba.crearConLineas(lineaError, lineaStack);
		v.verificarPorLinea(consola, lineaError, 0);
		assertTrue(v.activado());
	}

	@Test
	void campoInexistenteNoSeActivaConLineaNormal() throws Exception {
		ErrorCampoInexistente v = new ErrorCampoInexistente();
		v.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Field loaded", 1);
		assertFalse(v.activado());
	}

	@Test
	void campoInexistenteIdentidad() {
		ErrorCampoInexistente v = new ErrorCampoInexistente();
		assertEquals("error_campo_inexistente", v.id());
		assertEquals(Criticalidad.ERROR, v.nivel_de_criticalidad());
		assertTrue(v.nueva() instanceof ErrorCampoInexistente);
		assertEquals(1100.0f, v.prioridad());
	}
}
