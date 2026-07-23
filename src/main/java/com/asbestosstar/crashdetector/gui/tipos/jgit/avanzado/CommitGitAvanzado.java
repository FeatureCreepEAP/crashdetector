package com.asbestosstar.crashdetector.gui.tipos.jgit.avanzado;

/**
 * Resumen inmutable de un commit.
 */
public final class CommitGitAvanzado {

	private final String hash;
	private final String autor;
	private final String correo;
	private final String mensaje;
	private final long fechaEpochSegundos;

	public CommitGitAvanzado(String hash, String autor, String correo, String mensaje, long fechaEpochSegundos) {
		this.hash = hash == null ? "" : hash;
		this.autor = autor == null ? "" : autor;
		this.correo = correo == null ? "" : correo;
		this.mensaje = mensaje == null ? "" : mensaje;
		this.fechaEpochSegundos = fechaEpochSegundos;
	}

	public String hash() {
		return hash;
	}

	public String hashCorto() {
		return hash.length() <= 8 ? hash : hash.substring(0, 8);
	}

	public String autor() {
		return autor;
	}

	public String correo() {
		return correo;
	}

	public String mensaje() {
		return mensaje;
	}

	public long fechaEpochSegundos() {
		return fechaEpochSegundos;
	}
}
