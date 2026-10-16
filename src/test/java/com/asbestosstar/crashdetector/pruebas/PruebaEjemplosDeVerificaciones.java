package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.EjemploAdvertenciaDePrueba;
import com.asbestosstar.crashdetector.analizador.general.EjemploErrorDePrueba;
import com.asbestosstar.crashdetector.idioma.Espanol;

@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.general.EjemploErrorDePrueba",
		"com.asbestosstar.crashdetector.analizador.general.EjemploAdvertenciaDePrueba" },
		nota = "Cubre activacion, descarte, mensaje HTML, criticalidad e identidad de ambos ejemplos")
class PruebaEjemplosDeVerificaciones {

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
	}

	@Test
	void errorSeActivaYConservaCriticalidadPredeterminada() {
		EjemploErrorDePrueba verificacion = new EjemploErrorDePrueba();
		verificacion.verificarPorLinea(null, "CRASHDETECTOR_EJEMPLO_ERROR", 1);

		assertTrue(verificacion.activado());
		assertEquals(Criticalidad.ERROR, verificacion.nivel_de_criticalidad());
		assertTrue(verificacion.mensaje().contains("<br>"));
		assertEquals("ejemplo_error_prueba", verificacion.id());
		assertNotSame(verificacion, verificacion.nueva());
	}

	@Test
	void advertenciaSeActivaConCriticalidadAdvertencia() {
		EjemploAdvertenciaDePrueba verificacion = new EjemploAdvertenciaDePrueba();
		verificacion.verificarPorLinea(null, "CRASHDETECTOR_EJEMPLO_ADVERTENCIA", 1);

		assertTrue(verificacion.activado());
		assertEquals(Criticalidad.ADVERTENCIA, verificacion.nivel_de_criticalidad());
		assertTrue(verificacion.mensaje().contains("<br>"));
		assertTrue(verificacion.mensaje().contains("NO es la causa"));
		assertEquals("ejemplo_advertencia_prueba", verificacion.id());
		assertNotSame(verificacion, verificacion.nueva());
	}

	@Test
	void ambosEjemplosEstanRegistradosEnElAnalizador() {
		assertTrue(com.asbestosstar.crashdetector.analizador.Analizador.verificaciones.stream()
				.anyMatch(v -> v instanceof EjemploErrorDePrueba));
		assertTrue(com.asbestosstar.crashdetector.analizador.Analizador.verificaciones.stream()
				.anyMatch(v -> v instanceof EjemploAdvertenciaDePrueba));
	}

	@Test
	void lineasSinMarcadorNoActivanLosEjemplos() {
		EjemploErrorDePrueba error = new EjemploErrorDePrueba();
		EjemploAdvertenciaDePrueba advertencia = new EjemploAdvertenciaDePrueba();

		error.verificarPorLinea(null, "registro normal", 1);
		advertencia.verificarPorLinea(null, "registro normal", 1);

		assertFalse(error.activado());
		assertFalse(advertencia.activado());
	}
}
