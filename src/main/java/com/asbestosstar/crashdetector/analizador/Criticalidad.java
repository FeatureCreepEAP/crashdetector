package com.asbestosstar.crashdetector.analizador;

import java.awt.Color;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;

public class Criticalidad {

	public static Criticalidad FATAL = new Criticalidad(Color.RED, MonitorDePID.idioma.fatal());
	public static Criticalidad ERROR = new Criticalidad(
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorError()), MonitorDePID.idioma.error());
	public static Criticalidad ADVERTENCIA = new Criticalidad(
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorAdvertencia()),
			MonitorDePID.idioma.advertencia());

	/**
	 * Color en lectador
	 */
	public Color color;
	public String nombre;

	/**
	 * Criticalidad en Lectador
	 * 
	 * @param color
	 */
	public Criticalidad(Color color, String nombre) {
		this.color = color;
		this.nombre = nombre;
	}

}
