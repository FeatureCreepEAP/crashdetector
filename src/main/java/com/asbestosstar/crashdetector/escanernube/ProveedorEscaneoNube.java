package com.asbestosstar.crashdetector.escanernube;

import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Proveedores de análisis remoto compatibles con el escáner de mods.
 */
public enum ProveedorEscaneoNube {

	VIRUSTOTAL, METADEFENDER;

	/**
	 * Devuelve el nombre visible desde el sistema de idiomas.
	 */
	public String nombreVisible() {
		switch (this) {
		case VIRUSTOTAL:
			return MonitorDePID.idioma.escanerNubeProveedorVirusTotal();
		case METADEFENDER:
			return MonitorDePID.idioma.escanerNubeProveedorMetaDefender();
		default:
			return MonitorDePID.idioma.escanerNubeProveedorDesconocido();
		}
	}
}
