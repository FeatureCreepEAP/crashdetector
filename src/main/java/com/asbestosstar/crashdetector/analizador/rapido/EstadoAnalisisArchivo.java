package com.asbestosstar.crashdetector.analizador.rapido;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;

/**
 * Mantiene el estado del análisis para un archivo específico.
 */
public final class EstadoAnalisisArchivo {

	public final Consola consola;
	public long bytesLeidos;
	public int lineasLeidas;

	private final List<EventoDeCoincidencia> coincidencias = new ArrayList<>();

	public EstadoAnalisisArchivo(Consola consola) {
		this.consola = consola;
	}

	public void agregarCoincidencia(EventoDeCoincidencia evento) {
		coincidencias.add(evento);
	}

	public List<EventoDeCoincidencia> obtenerCoincidencias() {
		return coincidencias;
	}
}
