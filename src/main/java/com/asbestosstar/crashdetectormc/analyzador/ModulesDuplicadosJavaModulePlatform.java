package com.asbestosstar.crashdetectormc.analyzador;

import java.util.List;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetectormc.CDStringBuilder;
import com.asbestosstar.crashdetectormc.CrashDetectorLogger;
import com.asbestosstar.crashdetectormc.MonitorDePID;
import com.asbestosstar.crashdetectormc.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetectormc.buscar.Buscardor;

public class ModulesDuplicadosJavaModulePlatform implements Verificaciones {

	public boolean activado = false;

	@Override
	public void verificar(String contento_de_consola, CDStringBuilder constructor) {
	    String[] lineas = contento_de_consola.split(nl);
	    for (int i = 0; i < lineas.length; i++) {

	        String linea = lineas[i].trim();

	        if (linea.contains("java.lang.module.ResolutionException:")) {

	            try {
	                StringBuilder mensajeCompleto = new StringBuilder(linea);
	                
	                // Collect multi-line exception messages
	                while (i+1 < lineas.length && !lineas[i+1].trim().isEmpty() 
	                       && !lineas[i+1].startsWith("\tat ")) {
	                    mensajeCompleto.append(" ").append(lineas[++i].trim());
	                }
	                
	                String mensaje = mensajeCompleto.toString()
	                    .replace("ResolutionException:", "")
	                    .trim();

	                String modulosCombinados = "";
	                String paquete = "";
	                
	                if (mensaje.contains("contains package")) {
	                	constructor.append("containing");
	                    String[] partes = mensaje.split("contains package");
	                    String moduloContenedor = partes[0].replace("Module", "").trim();
	                    String resto = partes[1].trim();
	                    
	                    paquete = resto.split(",")[0].trim();
	                    String moduloExportador = resto.split("module ")[1].split(" ")[0].trim();
	                    modulosCombinados = moduloContenedor + "+" + moduloExportador;
		                activado = true;
	                    
	                } else if (mensaje.contains("export package")) {
	                    String[] partes = mensaje.split("export package");
	                    String modulosPart = partes[0].replace("Modules", "").trim();
	                    modulosCombinados = modulosPart.replace(" and ", "+").trim();
	                    
	                    paquete = partes[1].split("to module")[0].trim();
		                activado = true;
	                    
	                } else if (mensaje.contains("exports package")) {
	                    // Handle "Module A exports package X to module B"
	                    String[] partes = mensaje.split("exports package");
	                    String modulosPart = partes[0].trim();
	                    modulosCombinados = modulosPart.replace("and", "+").trim();
	                    
	                    paquete = partes[1].split("to module")[0].trim();
		                activado = true;

	                } else {
	                }
	                
	                if(this.activado){

	                // Normalize package name
	                paquete = paquete.replaceAll("^\"|\"$", "").trim();
	                Buscardor.cargar();
	                List<ArchivoDeMod> mods = Buscardor.buscarModsConTermino(paquete);
	                String resultado = formatearResultadoBusqueda(mods);
	                
	                constructor.append(
	                    MonitorDePID.idioma.module_resolution_exception(
	                        "<b>" + modulosCombinados + "</b>",
	                        "<b>" + paquete + "</b>"
	                    ) + resultado
	                ).append(nl_html);
	                }
	                
	            
	            
	            
	            } catch (Exception e) {
	                CrashDetectorLogger.logException(e);
	                // Continue processing other lines even if one fails
	            }
	        }
	    }
	}

	private String formatearResultadoBusqueda(List<ArchivoDeMod> mods) {
		if (mods.isEmpty()) {
			return "()";
		}

		String contenido = mods.stream().map(mod -> "<b>" + mod.ubicacion() + "</b>").collect(Collectors.joining(", "));

		return "(" + contenido + ")";
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new ModulesDuplicadosJavaModulePlatform();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

}
