package com.asbestosstar.crashdetectormc.buscar;

import java.util.ArrayList;
import java.util.List;

public class Buscardor {

	
	public static List<ArchivoDeMod> mods = new ArrayList<ArchivoDeMod>();

	public static List<String> obternerModsConNombre(String nombre){
		List<String> modsConNombre = new ArrayList<String>();
		for(ArchivoDeMod mod:mods) {
			if(mod.tieneNombreRecursivo(nombre)) {
				modsConNombre.add(mod.obtenerNombreRecursivo(nombre));
			}
		}
		return modsConNombre;
	}

	
	
	
}
