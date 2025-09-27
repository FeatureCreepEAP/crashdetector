package com.asbestosstar.crashdetector.cargador;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	/**
	 * Extrae el modId de un archivo mods.toml (Forge).
	 */
	public static List<String> parsearIdModNeoForge(byte[] contenido) throws IOException {
		String toml = new String(contenido, StandardCharsets.UTF_8);
		List<String> nombres = new ArrayList<String>();

		// Patrón para encontrar el modId principal en el bloque [[mods]]
		Pattern modPattern = Pattern.compile("\\[\\[mods\\]\\][^\\[]*modId\\s*=\\s*\"([^\"]+)\"", Pattern.DOTALL);
		Matcher modMatcher = modPattern.matcher(toml);

		// Valor por defecto si no se encuentra el modId
		String modIdPrincipal = "Mod Forge desconocido";

		if (modMatcher.find()) {
			modIdPrincipal = modMatcher.group(1);
		}

		// Añadir el modId principal a la lista de nombres
		nombres.add(modIdPrincipal);

		// Patrón para encontrar nombres en bloques de dependencias:
		// [[dependencies.nombre]] por que es la causa de una problema grande
		Pattern depPattern = Pattern.compile("\\[\\[dependencies\\.([a-zA-Z0-9_]+)\\]\\]");
		Matcher depMatcher = depPattern.matcher(toml);

		// Añadir cada nombre encontrado en dependencies a la lista de nombres
		while (depMatcher.find()) {
			String nombreDependencia = depMatcher.group(1);
			nombres.add(nombreDependencia);

		}

		return nombres;
	}
	
	
	
	
	
	
	
	
	
	
}
