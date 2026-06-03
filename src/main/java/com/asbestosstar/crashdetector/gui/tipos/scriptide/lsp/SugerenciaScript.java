package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

/**
 * Sugerencia simple para mostrar en el popup de completado.
 */
public class SugerenciaScript implements Comparable<SugerenciaScript> {

	public String etiqueta;
	public String insercion;
	public String detalle;

	public SugerenciaScript(String etiqueta, String insercion, String detalle) {
		this.etiqueta = etiqueta == null ? "" : etiqueta;
		this.insercion = insercion == null ? this.etiqueta : insercion;
		this.detalle = detalle == null ? "" : detalle;
	}

	@Override
	public String toString() {
		if (detalle == null || detalle.trim().isEmpty()) {
			return etiqueta;
		}
		return etiqueta + "    " + detalle;
	}

	@Override
	public int compareTo(SugerenciaScript o) {
		if (o == null) {
			return 1;
		}
		return etiqueta.compareToIgnoreCase(o.etiqueta);
	}
}