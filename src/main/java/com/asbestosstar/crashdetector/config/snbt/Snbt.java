package com.asbestosstar.crashdetector.config.snbt;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.asbestosstar.crashdetector.config.nbt.Nbt;

/**
 * Punto de entrada para SNBT.
 *
 * SNBT usa el mismo motor que NBT, pero con lectura/escritura textual. Esta
 * clase NO toca directamente Querz.
 */
public class Snbt extends Nbt {

	public Snbt() {
		super();
	}

	public static Nodo leer(String snbt) {
		return Nbt.leerSnbt(snbt);
	}

	public static Nodo leerArchivo(File archivo) {
		if (archivo == null)
			throw new IllegalArgumentException("archivo no puede ser null");

		try {
			byte[] datos = Files.readAllBytes(archivo.toPath());
			return leer(new String(datos, StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new IllegalArgumentException("No se pudo leer archivo SNBT: " + archivo, e);
		}
	}

	public static String escribir(Nodo nodo) {
		return Nbt.escribirSnbt(nodo);
	}

	public static void escribirArchivo(Nodo nodo, File archivo) {
		if (archivo == null)
			throw new IllegalArgumentException("archivo no puede ser null");

		try {
			Files.write(archivo.toPath(), escribir(nodo).getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new IllegalArgumentException("No se pudo escribir archivo SNBT: " + archivo, e);
		}
	}

	public static String nombreMotor() {
		return "snbt-sobre-" + Nbt.nombreMotor();
	}
}