package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.ClassCastExceptionGeneral;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de ClassCastExceptionGeneral.
 */
@PruebaDe(valor = { "com.asbestosstar.crashdetector.analizador.general.ClassCastExceptionGeneral" },
		nota = "Cubre deteccion de ClassCastException, descartes de falsos positivos e identidad")
class PruebaClassCast {

	private ClassCastExceptionGeneral verificacion;

	@BeforeEach
	void preparar() {
		ClassCastExceptionGeneral.reiniciarGlobal();
		MonitorDePID.idioma = new Espanol();
		verificacion = new ClassCastExceptionGeneral();
	}

	@Test
	void detectaClassCastExceptionBasica() throws Exception {
		String linea = "java.lang.ClassCastException: class com.example.Source cannot be cast to class com.example.Target";
		ClassCastExceptionGeneral v = new ClassCastExceptionGeneral();
		v.verificarCoincidencia(new com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia(
			ConsolaPrueba.crearConLineas(linea), null, v, "java.lang.ClassCastException:", linea, 10, 0, linea.length(), null));
		assertTrue(v.activado());
	}

	@Test
	void detectaClassCastExceptionConIntegerToString() throws Exception {
		String linea = "java.lang.ClassCastException: class java.lang.Integer cannot be cast to class java.lang.String";
		ClassCastExceptionGeneral v = new ClassCastExceptionGeneral();
		v.verificarCoincidencia(new com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia(
			ConsolaPrueba.crearConLineas(linea), null, v, "java.lang.ClassCastException:", linea, 5, 0, linea.length(), null));
		assertTrue(v.activado());
	}

	@Test
	void noSeActivaConIntegerCastToListDescarte() throws Exception {
		// Integer -> List se descarta solo si la linea contiene oneauras-cart-optimizer
		// Sin ese contexto, se activa normalmente
		String linea = "java.lang.ClassCastException: class java.lang.Integer cannot be cast to class java.util.List";
		com.asbestosstar.crashdetector.Consola consola = ConsolaPrueba.crearConLineas(linea);
		ClassCastExceptionGeneral v = new ClassCastExceptionGeneral();
		v.verificarCoincidencia(new com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia(
			consola, null, v, "java.lang.ClassCastException:", linea, 0, 0, linea.length(), null));
		assertTrue(v.activado(), "Integer -> List sin oneauras-cart-optimizer se activa normalmente.");
	}

	@Test
	void noSeActivaConLinkedTreeMapCastToStringDescarte() throws Exception {
		String linea = "java.lang.ClassCastException: class com.google.gson.internal.LinkedTreeMap cannot be cast to class java.lang.String";
		ClassCastExceptionGeneral v = new ClassCastExceptionGeneral();
		v.verificarCoincidencia(new com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia(
			ConsolaPrueba.crearConLineas(linea), null, v, "java.lang.ClassCastException:", linea, 0, 0, linea.length(), null));
		assertFalse(v.activado(), "LinkedTreeMap -> String debe ser descartado.");
	}

	@Test
	void noSeActivaConLineaNormal() throws Exception {
		verificacion.verificarPorLinea(ConsolaPrueba.crear(), "INFO: Class loaded", 1);
		assertFalse(verificacion.activado());
	}

	@Test
	void deduplicacionGlobal() throws Exception {
		String linea = "java.lang.ClassCastException: class com.example.A cannot be cast to class com.example.B";
		com.asbestosstar.crashdetector.Consola consola1 = ConsolaPrueba.crearConLineas(linea);
		com.asbestosstar.crashdetector.Consola consola2 = ConsolaPrueba.crearConLineas(linea);

		// Primera deteccion
		ClassCastExceptionGeneral v1 = new ClassCastExceptionGeneral();
		v1.verificarCoincidencia(new com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia(
			consola1, null, v1, "java.lang.ClassCastException:", linea, 0, 0, linea.length(), null));
		assertTrue(v1.activado());

		// Segunda deteccion con la misma linea debe ser ignorada
		ClassCastExceptionGeneral v2 = new ClassCastExceptionGeneral();
		v2.verificarCoincidencia(new com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia(
			consola2, null, v2, "java.lang.ClassCastException:", linea, 0, 0, linea.length(), null));
		assertFalse(v2.activado(), "La misma linea ya fue vista globalmente.");
	}

	@Test
	void identidad() {
		assertEquals("class_cast_exception_general", verificacion.id());
		assertEquals(Criticalidad.ERROR, verificacion.nivel_de_criticalidad());
		assertTrue(verificacion.nueva() instanceof ClassCastExceptionGeneral);
		assertEquals(1380.0f, verificacion.prioridad());
	}

	@Test
	void mensajeVacioSinActivacion() {
		// mensaje() puede contener enlace vacio aunque no este activado
		// pero lineaClassCast debe estar vacio
		assertFalse(verificacion.activado());
	}
}
