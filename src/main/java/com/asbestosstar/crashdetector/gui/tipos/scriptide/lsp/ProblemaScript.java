package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

/**
 * Diagnostico LSP reducido para que la GUI no dependa directamente de LSP4J.
 */
public class ProblemaScript {

	public String archivo;
	public int linea;
	public int columna;
	public String mensaje;
	public int severidad;

	public ProblemaScript(String archivo, int linea, int columna, String mensaje, int severidad) {
		this.archivo = archivo;
		this.linea = linea;
		this.columna = columna;
		this.mensaje = mensaje;
		this.severidad = severidad;
	}
}