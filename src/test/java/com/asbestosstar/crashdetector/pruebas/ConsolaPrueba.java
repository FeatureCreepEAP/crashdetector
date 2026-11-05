package com.asbestosstar.crashdetector.pruebas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.asbestosstar.crashdetector.Consola;

/**
 * Utilidad para crear objetos Consola minimos para pruebas unitarias.
 * Los analizadores que requieren Consola no nula (como JavaVersiones o
 * ErrorDeEnlaceInsatisfecho) necesitan este objeto para llamar a
 * agregarErrorALectador() sin lanzar NullPointerException.
 */
public final class ConsolaPrueba {

	private ConsolaPrueba() {
	}

	/**
	 * Crea una Consola con un archivo temporal vacio.
	 */
	public static Consola crear() throws IOException {
		Path archivo = Files.createTempFile("prueba-consola-", ".log");
		return new Consola(archivo);
	}

	/**
	 * Crea una Consola con el contenido dado, listo para que los analizadores
	 * consulten lineas_verificar.
	 */
	public static Consola crearConLineas(String... lineas) throws IOException {
		Consola consola = crear();
		consola.lineas_verificar = lineas;
		consola.contenido_verificar = String.join("\n", lineas);
		return consola;
	}
}
