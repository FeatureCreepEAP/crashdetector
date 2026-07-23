package com.asbestosstar.crashdetector.gui.tipos.jgit.avanzado;

/**
 * Cambio de archivo mostrado por el cliente Git avanzado.
 */
public final class CambioGitAvanzado {

	public enum Estado {
		AGREGADO, MODIFICADO, ELIMINADO, NO_RASTREADO, CONFLICTO, RENOMBRADO
	}

	private final String ruta;
	private final Estado estado;
	private final boolean preparado;

	public CambioGitAvanzado(String ruta, Estado estado, boolean preparado) {
		this.ruta = ruta == null ? "" : ruta;
		this.estado = estado == null ? Estado.MODIFICADO : estado;
		this.preparado = preparado;
	}

	public String ruta() {
		return ruta;
	}

	public Estado estado() {
		return estado;
	}

	public boolean preparado() {
		return preparado;
	}
}
