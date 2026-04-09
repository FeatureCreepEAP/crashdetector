package com.asbestosstar.crashdetector.guard.abrol;

import java.util.ArrayList;
import java.util.List;

/**
 * Resultado de analizar una clase concreta.
 */
public class AnalisisDeClase {

	public String nombreClaseInterno;

	public final List<ConstanteTextoEncontrada> constantesTexto = new ArrayList<ConstanteTextoEncontrada>();
	public final List<ReferenciaMetodoEncontrada> referenciasAMetodo = new ArrayList<ReferenciaMetodoEncontrada>();
	public final List<ReferenciaCampoEncontrada> referenciasACampo = new ArrayList<ReferenciaCampoEncontrada>();

}