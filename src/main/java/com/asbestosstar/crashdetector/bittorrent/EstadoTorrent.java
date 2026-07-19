package com.asbestosstar.crashdetector.bittorrent;

/**
 * Instantánea inmutable de una transferencia BitTorrent.
 */
public final class EstadoTorrent {

	private final String id;
	private final String nombre;
	private final String tipo;
	private final String estado;
	private final double porcentaje;
	private final int pares;
	private final long descargado;
	private final long subido;

	public EstadoTorrent(String id, String nombre, String tipo, String estado, double porcentaje, int pares,
			long descargado, long subido) {
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.estado = estado;
		this.porcentaje = porcentaje;
		this.pares = pares;
		this.descargado = descargado;
		this.subido = subido;
	}

	public String id() {
		return id;
	}

	public String nombre() {
		return nombre;
	}

	public String tipo() {
		return tipo;
	}

	public String estado() {
		return estado;
	}

	public double porcentaje() {
		return porcentaje;
	}

	public int pares() {
		return pares;
	}

	public long descargado() {
		return descargado;
	}

	public long subido() {
		return subido;
	}
}
