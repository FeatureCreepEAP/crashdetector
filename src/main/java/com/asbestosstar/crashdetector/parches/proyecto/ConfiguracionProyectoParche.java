package com.asbestosstar.crashdetector.parches.proyecto;

/**
 * Datos de una plantilla de extensión de parches.
 */
public final class ConfiguracionProyectoParche {

	public final String nombreProyecto;
	public final String paqueteBase;
	public final String claseExtension;
	public final String claseParche;
	public final String claseObjetivo;

	public ConfiguracionProyectoParche(String nombreProyecto, String paqueteBase, String claseExtension,
			String claseParche, String claseObjetivo) {
		this.nombreProyecto = nombreProyecto;
		this.paqueteBase = paqueteBase;
		this.claseExtension = claseExtension;
		this.claseParche = claseParche;
		this.claseObjetivo = claseObjetivo;
	}
}
