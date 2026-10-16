package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.general.NullPointer;
import com.asbestosstar.crashdetector.idioma.Espanol;

/**
 * Pruebas unitarias de la verificacion NullPointer, usando lineas de
 * registro reales y sin consola (la verificacion admite consola nula).
 */
@PruebaDe(valor = { "com.asbestosstar.crashdetector.analizador.general.NullPointer" },
		nota = "Cubre el disparador por linea (formato Cannot invoke), los descartes y la identidad de la verificacion")
class PruebaNullPointer {

	private NullPointer verificacion;

	@BeforeEach
	void preparar() {
		// El estado global de NPE vistos debe limpiarse entre pruebas.
		NullPointer.reiniciarGlobal();
		// Los mensajes pasan por el idioma activo; fijamos espanol.
		MonitorDePID.idioma = new Espanol();
		verificacion = new NullPointer();
	}

	@Test
	void detectaFormatoCannotInvoke() {
		String linea = "java.lang.NullPointerException: Cannot invoke "
				+ "\"com.ejemplo.Servicio.ejecutar()\" because \"this.servicio\" is null";
		verificacion.verificarPorLinea(null, linea, 1);

		assertTrue(verificacion.activado(), "La verificacion debio activarse con la linea 'Cannot invoke ... because ... is null'.");
		String mensaje = verificacion.mensaje();
		assertTrue(mensaje.contains("com.ejemplo.Servicio.ejecutar()"),
				"El mensaje debe mencionar el metodo involucrado. Mensaje: " + mensaje);
		assertTrue(mensaje.contains("this.servicio"),
				"El mensaje debe mencionar el objeto nulo. Mensaje: " + mensaje);
	}

	@Test
	void noSeActivaConUnaLineaSinNPE() {
		verificacion.verificarPorLinea(null, "Hola, esta linea no contiene ningun error", 1);
		assertFalse(verificacion.activado(), "Una linea sin NullPointerException no debe activar la verificacion.");
		assertEquals("", verificacion.mensaje(), "Sin activacion el mensaje debe quedar vacio.");
	}

	@Test
	void noSeActivaConCabeceraSinFormatoExtraible() {
		// Las cabeceras sin formato Cannot/JSON extraible no disparan esta
		// verificacion (permitirCabecera es falso en el camino por linea).
		verificacion.verificarPorLinea(null, "java.lang.NullPointerException: error sin detalles", 3);
		assertFalse(verificacion.activado(), "Una cabecera sin metodo/objeto extraibles no debe activar la verificacion.");
	}

	@Test
	void identidadDeLaVerificacion() {
		assertEquals("nullpointer", verificacion.id(), "El id de la verificacion no debe cambiar.");
		assertEquals(Criticalidad.ERROR, verificacion.nivel_de_criticalidad(),
				"El NPE es la causa del crash: criticalidad ERROR por defecto.");
		assertTrue(verificacion.nueva() instanceof NullPointer,
				"nueva() debe devolver otra instancia de NullPointer.");
		assertFalse(verificacion.anularNormal(), "Esta verificacion no debe forzar la apertura del programa.");
	}
}
