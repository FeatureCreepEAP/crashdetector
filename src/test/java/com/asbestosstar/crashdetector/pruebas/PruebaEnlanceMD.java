package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.EnlanceMD;

/** Comprueba la extracción y el resultado determinista de documentos Markdown. */
class PruebaEnlanceMD {

	private static final String PROPIEDAD_SIN_RED = "crashdetector.markdown.sin_red";

	@AfterEach
	void limpiarModoSinRed() {
		System.clearProperty(PROPIEDAD_SIN_RED);
	}

	@Test
	void procesaMuchosEnlacesYEliminaDuplicadosSinRed() {
		System.setProperty(PROPIEDAD_SIN_RED, "true");
		String markdown = "# Informe\n"
				+ "[latest.log (head)](https://mclo.gs/Q7m4ZpN2)\n"
				+ "[latest.log (tail)](https://mclo.gs/V8k2LmR5)\n"
				+ "[latest.log (head)](https://mclo.gs/Q7m4ZpN2)\n"
				+ "[crash-2026-10-16_12.00.00-client.txt](https://mclo.gs/X3p9AaT6)\n";

		EnlanceMD.ResultadoMarkdown resultado = EnlanceMD.guardarConResultado(markdown);

		assertEquals(3, resultado.obtenerEnlacesExtraidos());
		assertEquals(3, resultado.obtenerEnlacesCompatibles());
		assertEquals(2, resultado.obtenerGrupos());
		assertEquals(0, resultado.obtenerGruposExitosos());
		assertEquals(2, resultado.obtenerGruposFallidos());
	}

	@Test
	void descartaMarkdownVacioMalformadoYEsquemasNoCompatibles() {
		EnlanceMD.ResultadoMarkdown vacio = EnlanceMD.guardarConResultado("");
		assertEquals(0, vacio.obtenerEnlacesExtraidos());
		assertEquals(0, vacio.obtenerGrupos());

		String markdown = "[incompleto](https://example.invalid/log\n"
				+ "[local](file:///tmp/latest.log)\n";
		EnlanceMD.ResultadoMarkdown invalido = EnlanceMD.guardarConResultado(markdown);

		assertEquals(2, invalido.obtenerEnlacesExtraidos());
		assertEquals(0, invalido.obtenerEnlacesCompatibles());
		assertEquals(0, invalido.obtenerGrupos());
	}
}
