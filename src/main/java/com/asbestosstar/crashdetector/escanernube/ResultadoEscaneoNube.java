package com.asbestosstar.crashdetector.escanernube;

import java.io.File;

/**
 * Resultado normalizado de un análisis de un archivo JAR.
 */
public final class ResultadoEscaneoNube {

	public final ProveedorEscaneoNube proveedor;
	public final File archivo;
	public final String estado;
	public final int detecciones;
	public final int motores;
	public final String sha256;
	public final String detalle;

	public ResultadoEscaneoNube(ProveedorEscaneoNube proveedor, File archivo, String estado, int detecciones,
			int motores, String sha256, String detalle) {
		this.proveedor = proveedor;
		this.archivo = archivo;
		this.estado = estado == null ? "" : estado;
		this.detecciones = Math.max(0, detecciones);
		this.motores = Math.max(0, motores);
		this.sha256 = sha256 == null ? "" : sha256;
		this.detalle = detalle == null ? "" : detalle;
	}
}
