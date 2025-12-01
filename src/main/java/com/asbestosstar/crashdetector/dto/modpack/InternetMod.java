package com.asbestosstar.crashdetector.dto.modpack;

public abstract class InternetMod {

	public abstract long obtenerIdentificador();

	public abstract String obtenerNombre();

	public abstract String obtenerAutor();

	public abstract String obtenerEnlaceProyecto();

	public abstract String obtenerDescripcionCorta();

	public abstract boolean esFavorito();

	public abstract String obtenerNombreLocalizado();

	public abstract VersionJuego obtenerUltimaVersionJuego();

	public abstract long obtenerActualizado();

	public abstract long obtenerDescargasTotales();

	public abstract boolean esDisponible();

	public abstract boolean esAnalizable();
}