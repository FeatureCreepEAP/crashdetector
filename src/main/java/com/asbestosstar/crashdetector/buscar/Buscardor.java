package com.asbestosstar.crashdetector.buscar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.anon.AnonimizadorDeRuta;

public class Buscardor {

	
	public static List<ArchivoDeMod> mods = new ArrayList<ArchivoDeMod>();

	public static boolean cargado = false;
	
	public static void cargar() {
		if(!cargado) {
			try {
				for (String mod:MonitorDePID.leer_archivo(MonitorDePID.ultima_mods).split(MonitorDePID.nl)) {
					File arc = new File(mod);
					if(arc.isFile()) {
					Buscardor.mods.add(new ModPKZip(mod,ArchivoDeMod.origin,new ZipInputStream(new FileInputStream(arc))));
					}
					cargado=true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String rutaParaPublicar(String ruta) {
		boolean anon = Config.obtenerInstancia().esAnonimizarRegistros();
		if (anon) {
			return AnonimizadorDeRuta.anonimizarNombreDeUsuario(ruta.toString());
		}
		return ruta.toString();
	}
	
	
	public static List<String> obternerModsConNombre(String nombre){
		List<String> modsConNombre = new ArrayList<String>();
		for(ArchivoDeMod mod:mods) {
			if(mod.tieneNombreRecursivo(nombre)) {
				modsConNombre.add(rutaParaPublicar(mod.obtenerNombreRecursivo(nombre)));
			}
		}
		return modsConNombre;
	}

	
    /**
     * Busca todos los mods que contienen un archivo, clase o paquete específico.
     * @param termino Término a buscar (archivo, clase o paquete)
     * @return Lista de ubicaciones de mods que contienen el término
     */

    public static List<ArchivoDeMod> buscarModsConTermino(String termino) {
        List<ArchivoDeMod> resultados = new ArrayList<>();
        for (ArchivoDeMod mod : mods) {
            resultados.addAll(mod.buscarModsCon(termino));
        }
        return resultados;
    }
	
    /**
     * Si tienes una lista de ArchivoDeMods pero quieres las ubicaciones
     * @param mods
     * @return
     */
	public static List<String> obternerUbicaciones(List<ArchivoDeMod> mods){
		List<String> ret = new ArrayList<String>();
		for(ArchivoDeMod mod:mods) {
			ret.add(mod.ubicacion());
		}
		return ret;
	}
	
	
}
