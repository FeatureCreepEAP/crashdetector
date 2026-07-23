package com.asbestosstar.crashdetector.parches.proyecto;

import java.io.File;

/** Resultado estable de la compilación de un proyecto de parches. */
public final class ResultadoCompilacionParche {

	public final boolean correcta;
	public final File archivoJar;
	public final String salidaCompilador;

	public ResultadoCompilacionParche(boolean correcta, File archivoJar, String salidaCompilador) {
		this.correcta = correcta;
		this.archivoJar = archivoJar;
		this.salidaCompilador = salidaCompilador == null ? "" : salidaCompilador;
	}
}
