package com.asbestosstar.crashdetectormc.buscar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.asbestosstar.crashdetectormc.CrashDetectorLogger;

public class ModPKZip implements ArchivoDeMod {

    public ArchivoDeMod desde;
    public String ubicacion;
    public List<ArchivoDeMod> mods_en_mod = new ArrayList<>();
    public List<String> nombres = new ArrayList<>();;
    public List<String> clases = new ArrayList<String>();
	public List<String> archivos = new ArrayList<String>();

    public ModPKZip(String ubicacion, ArchivoDeMod desde, ZipInputStream zip) {
        this.ubicacion = ubicacion;
        this.desde = desde;
        procesarZip(zip);
    }

    @Override
    public ArchivoDeMod obtenerDesde() {
        return desde;
    }

    @Override
    public List<ArchivoDeMod> mods_en_mods() {
        return mods_en_mod;
    }

    @Override
    public List<String> nombre() {
        return nombres;
    }

    @Override
    public String ubicacion() {
        return ubicacion;
    }

    /**
     * Processes the ZIP file to extract mod names and handle nested JARs.
     */
    private void procesarZip(ZipInputStream zip) {
        try {
            ZipEntry entrada;
            while ((entrada = zip.getNextEntry()) != null) {
                String nombreArchivo = entrada.getName();
                this.archivos.add(nombreArchivo);
//TODO mas y buscar si rar es ResourceArchive
                if (nombreArchivo.endsWith(".jar")||nombreArchivo.endsWith(".zip")||nombreArchivo.endsWith(".fpm")||nombreArchivo.endsWith(".litemod")||nombreArchivo.endsWith(".war")||nombreArchivo.endsWith(".rar")) {
                    String nuevaUbicacion = ubicacion + "!/" + nombreArchivo;
                    mods_en_mod.add(new ModPKZip(nuevaUbicacion, this, new ZipInputStream(zip)));
                } else {
                    if (nombreArchivo.equals("modules.xml")) { //TODO Otros archivos JBoss Modules y RPM Spec
                        nombres.add(parsearNombreModuloJBoss(zip));
                    } else if (nombreArchivo.endsWith(".mod")) {
                        nombres.add(parsearNombreModHOI4(zip));
                    } else if (nombreArchivo.equals("fabric.mod.json")) {
                        nombres.add(parsearIdModFabric(zip));
                    } else if (nombreArchivo.equals("mods.toml")) {//TODO ModLauncherServices
                        nombres.add(parsearIdModMCForge(zip));
                    }else if (nombreArchivo.endsWith(".class")) {
                    	clases.add(nombreArchivo.substring(0, nombreArchivo.replace("/", ".").length()-5));
                    }
                }

                zip.closeEntry();
            }
        } catch (IOException e) {
            CrashDetectorLogger.logException(e);
        }
    }


    private String parsearNombreModuloJBoss(ZipInputStream zip) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(zip))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        String contenidoXml = contenido.toString();
        int indiceInicio = contenidoXml.indexOf("name=\"") + "name=\"".length();
        int indiceFin = contenidoXml.indexOf("\"", indiceInicio);
        return (indiceInicio != -1 && indiceFin != -1)
                ? contenidoXml.substring(indiceInicio, indiceFin)
                : "Módulo JBoss desconocido";
    }

    /**
     * Parses the name of a HOI4 mod from a .mod file.
     */
    private String parsearNombreModHOI4(ZipInputStream zip) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(zip))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        String contenidoMod = contenido.toString();
        int indiceInicio = contenidoMod.indexOf("name=\"") + "name=\"".length();
        int indiceFin = contenidoMod.indexOf("\"", indiceInicio);
        return (indiceInicio != -1 && indiceFin != -1)
                ? contenidoMod.substring(indiceInicio, indiceFin)
                : "Mod HOI4 desconocido";
    }

  
    private String parsearIdModFabric(ZipInputStream zip) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(zip))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        String contenidoJson = contenido.toString();
        int indiceInicio = contenidoJson.indexOf("\"id\": \"") + "\"id\": \"".length();
        int indiceFin = contenidoJson.indexOf("\"", indiceInicio);
        return (indiceInicio != -1 && indiceFin != -1)
                ? contenidoJson.substring(indiceInicio, indiceFin)
                : "Mod Fabric desconocido";
    }

 
    private String parsearIdModMCForge(ZipInputStream zip) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(zip))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        String contenidoToml = contenido.toString();
        int indiceInicio = contenidoToml.indexOf("modId=\"") + "modId=\"".length();
        int indiceFin = contenidoToml.indexOf("\"", indiceInicio);
        return (indiceInicio != -1 && indiceFin != -1)
                ? contenidoToml.substring(indiceInicio, indiceFin)
                : "Mod Forge desconocido";
    }

	@Override
	public List<String> clases() {
		// TODO Auto-generated method stub
		return clases;
	}

	@Override
	public boolean tieneNombreRecursivo(String nombre) {
		// TODO Auto-generated method stub
		if(this.nombre().contains(nombre)) {
			return true;
		}
		for(ArchivoDeMod mod:this.mods_en_mods()) {
		if(mod.tieneNombreRecursivo(nombre))
			return true;
		}
		
		return false;
	}

	@Override
	public String obtenerNombreRecursivo(String nombre) {
		// TODO Auto-generated method stub

		if (tieneNombreRecursivo(nombre)) {
			if(this.nombre().contains(nombre)) {
				return this.ubicacion();
			}
			for(ArchivoDeMod mod:this.mods_en_mods()) {
				if(mod.tieneNombreRecursivo(nombre)) {
					return mod.obtenerNombreRecursivo(nombre);
				}
			}
			
		}
		
		return null;
	}


	@Override
	public boolean tieneArchivoRecursivo(String archivo) {
		// TODO Auto-generated method stub
		if(this.archivos().contains(archivo)) {
			return true;
		}
		for(ArchivoDeMod mod:this.mods_en_mods()) {
		if(mod.tieneArchivoRecursivo(archivo))
			return true;
		}
		
		return false;

	
	
	}

	@Override
	public String obtenerArchivoRecursivo(String archivo) {
		// TODO Auto-generated method stub

		if (tieneArchivoRecursivo(archivo)) {
			if(this.archivos().contains(archivo)) {
				return this.ubicacion()+"!/"+archivo;
			}
			for(ArchivoDeMod mod:this.mods_en_mods()) {
				if(mod.tieneArchivoRecursivo(archivo)) {
					return mod.obtenerArchivoRecursivo(archivo);
				}
			}
			
		}
	return null;
	}

	@Override
	public List<String> archivos() {
		// TODO Auto-generated method stub
		return archivos = new ArrayList<String>();
	}

	// Implementación en ModPKZip
	@Override
	public List<ArchivoDeMod> buscarModsCon(String termino) {
	    List<ArchivoDeMod> resultados = new ArrayList<>();
	    
	    // Verificar en este mod
	    boolean tieneArchivo = archivos.contains(termino);
	    boolean tieneClase = clases.contains(termino);
	    String packagePath = termino.replace('.', '/');
	    boolean tienePackage = clases.stream()
	        .anyMatch(clase -> clase.startsWith(packagePath));
	    
	    if (tieneArchivo || tieneClase || tienePackage) {
	        resultados.add(this);
	    }
	    
	    // Buscar en mods anidados
	    for (ArchivoDeMod mod : mods_en_mod) {
	        resultados.addAll(mod.buscarModsCon(termino));
	    }
	    
	    return resultados;
	}












}
