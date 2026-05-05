package com.asbestosstar.crashdetector.gui.tipos.jgit;

import java.io.File;

/**
 * Resultado de una descarga de dependencia JGit.
 */
public class ResultadoDescargaJGit {

	public final boolean exito;
	public final File archivo;
	public final String mensaje;

	public ResultadoDescargaJGit(boolean exito, File archivo, String mensaje) {
		this.exito = exito;
		this.archivo = archivo;
		this.mensaje = mensaje;
	}
}