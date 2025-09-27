package com.asbestosstar.crashdetector.cargador;

import java.util.Locale;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

public class CargadorFabric implements Cargador{

	@Override
	public boolean modEsDeCargador(ArchivoDeMod mod) {
		// TODO Auto-generated method stub
        // se recorre la lista de rutas internas del mod
        for (String archivo : mod.archivos()) {
            String norm = archivo.replace('\\', '/');
            String lower = norm.toLowerCase(Locale.ROOT);

            // deteccion de mods.toml en meta inf
            if (lower.equals("fabric.mod.json")) {
                return true;
            }
        }
        return false;

	
	}

	@Override
	public boolean cargadorEsActivado() {
		// TODO Auto-generated method stub
		return Cargador.claseExiste("net.fabricmc.loader.api.FabricLoader");
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "fabric";
	}

}
