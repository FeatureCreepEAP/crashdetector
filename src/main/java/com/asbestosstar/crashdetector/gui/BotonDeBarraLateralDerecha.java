package com.asbestosstar.crashdetector.gui;

import javax.swing.Icon;

public interface BotonDeBarraLateralDerecha {
	
	/**
	 * acción cuando se presiona el botón
	 */
	public void init();

	/**
	 * el etiquera del boton en la barra lateral
	 * @return
	 */
	public String etiquetaDelBoton();
	
	/**
	 * Icon del boton
	 * @return
	 */
	public default Icon icon() {
		return null;
	}
}
