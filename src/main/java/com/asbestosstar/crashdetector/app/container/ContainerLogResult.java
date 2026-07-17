package com.asbestosstar.crashdetector.app.container;

/**
 * Resultado inmutable de obtener los logs de una plataforma de contenedores.
 */
public final class ContainerLogResult {

	private final String contenido;
	private final String advertencias;
	private final String comandoVisible;

	public ContainerLogResult(String contenido, String advertencias, String comandoVisible) {
		this.contenido = contenido == null ? "" : contenido;
		this.advertencias = advertencias == null ? "" : advertencias;
		this.comandoVisible = comandoVisible == null ? "" : comandoVisible;
	}

	public String getContenido() {
		return contenido;
	}

	public String getAdvertencias() {
		return advertencias;
	}

	public String getComandoVisible() {
		return comandoVisible;
	}
}
