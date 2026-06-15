package com.asbestosstar.crashdetector.analizador.rapido;

import java.nio.file.Path;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public final class EventoDeCoincidencia {

	public final Consola consola;
	public final Path archivo;
	public final Verificaciones verificacion;
	public final String patron;
	public final String linea;
	public final int numeroDeLinea;
	public final int inicioEnLinea;
	public final int finEnLinea;
	public final EstadoAnalisisArchivo estado;

	public EventoDeCoincidencia(Consola consola, Path archivo, Verificaciones verificacion, String patron, String linea,
			int numeroDeLinea, int inicioEnLinea, int finEnLinea, EstadoAnalisisArchivo estado) {

		this.consola = consola;
		this.archivo = archivo;
		this.verificacion = verificacion;
		this.patron = patron;
		this.linea = linea;
		this.numeroDeLinea = numeroDeLinea;
		this.inicioEnLinea = inicioEnLinea;
		this.finEnLinea = finEnLinea;
		this.estado = estado;
	}
}