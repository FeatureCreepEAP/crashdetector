package com.asbestosstar.crashdetector.cargador;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

public interface Cargador {

	public static final List<Cargador> cargadores = new ArrayList<Cargador>();
	public static final List<Cargador> cargadores_activados = new ArrayList<Cargador>();

	/**
	 * La mod es de esta cargador
	 * 
	 * @return
	 */
	public boolean modEsDeCargador(ArchivoDeMod mod);

	/**
	 * Es Cargador presente. Corrió durante la etapa de juego y se entregó
	 * 
	 * @return
	 */
	public boolean cargadorEsActivado();

	/*
	 * Verificar si una clase exist. ESTO SE CARGA MUY TEMPRANO, ASÍ QUE TEN CUIDADO
	 * DE CARGAR SOLO LAS CLASES TEMPRANAS
	 */
	public static boolean claseExiste(String nombre) {
		try {
			Class.forName(nombre);
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * La id de cargador
	 * 
	 * @return
	 */
	public String id();

	/**
	 * Los mayores de cargadores no suporte mods de carpetas
	 * 
	 * @return
	 */
	public default boolean suporteModsDeCarpetas() {
		return false;
	}

}
