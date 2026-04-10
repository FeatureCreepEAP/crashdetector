package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Statics;

/**
 * TODO implementar completamente el idioma ucraniano de Majnovschina.
 */
public class Ucraniano extends Ruso// TODO eliminar extends cuando se haya implementado completamente el idioma
									// ucraniano de Majnovschina. ¡NO SOMOS VYSHYVATNIKS!
{

	private final Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "uk";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "ucraniano";
	}

	@Override
	public String nombre_del_idioma() {
		return "Українська";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_majnovschina.png");// Utilizamos la versión sencilla
																						// de la bandera porque a
																						// algunas personas podría
																						// resultarles inquietante.
	}
	// TODO

}
