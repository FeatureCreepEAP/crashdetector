package com.asbestosstar.crashdetector.cargador;

import java.util.Locale;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;

/**
 * Es para MinecraftForge, no para JBoss Forge
 */
public class CargadorMCForge implements Cargador {

    @Override
    public boolean modEsDeCargador(ArchivoDeMod mod) {
        // se recorre la lista de rutas internas del mod
        for (String archivo : mod.archivos()) {
            String norm = archivo.replace('\\', '/');
            String lower = norm.toLowerCase(Locale.ROOT);

            // deteccion de mods.toml en meta inf
            if (lower.equals("meta-inf/mods.toml")) {
                return true;
            }

            // deteccion de servicios bajo meta inf
            if (lower.startsWith("meta-inf/services/")) {
                String servicio = lower.substring("meta-inf/services/".length());
                if (servicio.startsWith("cpw") || servicio.startsWith("net.minecraftforge")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean cargadorEsActivado() {
        // es comun en versiones actuales y 1.13
        return Cargador.claseExiste("net.minecraftforge.api.Side");
    }

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "minecraftforge";
	}
}
