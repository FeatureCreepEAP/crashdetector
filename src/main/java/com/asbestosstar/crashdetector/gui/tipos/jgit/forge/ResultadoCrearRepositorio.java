package com.asbestosstar.crashdetector.gui.tipos.jgit.forge;

public class ResultadoCrearRepositorio {

	public boolean exito;
	public String remoteUrl;
	public String mensaje;

	public ResultadoCrearRepositorio(boolean exito, String remoteUrl, String mensaje) {
		this.exito = exito;
		this.remoteUrl = remoteUrl;
		this.mensaje = mensaje;
	}
}