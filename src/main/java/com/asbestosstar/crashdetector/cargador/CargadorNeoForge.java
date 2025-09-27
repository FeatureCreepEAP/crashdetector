package com.asbestosstar.crashdetector.cargador;

import java.util.Locale;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;


public class CargadorNeoForge implements Cargador {

    @Override
    public boolean modEsDeCargador(ArchivoDeMod mod) {
        // se recorre la lista de rutas internas del mod
        for (String archivo : mod.archivos()) {
            String norm = archivo.replace('\\', '/');
            String lower = norm.toLowerCase(Locale.ROOT);

            // deteccion de mods.toml en meta inf
            if (lower.equals("meta-inf/neoforge.mods.toml")) {
                return true;
            }

            // deteccion de servicios bajo meta inf
            if (lower.startsWith("meta-inf/services/")) {
                String servicio = lower.substring("meta-inf/services/".length());
                if (servicio.startsWith("cpw") || servicio.startsWith("net.neoforged")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean cargadorEsActivado() {
        // es comun en versiones actuales y 1.13
        return Cargador.claseExiste("net.neoforged.neoforgespi.locating.IModFile");
    }

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "neoforge";
	}
}
