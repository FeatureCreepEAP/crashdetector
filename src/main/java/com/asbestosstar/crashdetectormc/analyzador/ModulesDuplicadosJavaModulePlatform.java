package com.asbestosstar.crashdetectormc.analyzador;

import java.util.List;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetectormc.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetectormc.buscar.Buscardor;

public class ModulesDuplicadosJavaModulePlatform implements Verificaciones {

    private boolean activado = false;
    private final CDStringBuilder mensajes = new CDStringBuilder();

    @Override
    public void verificar(Consola consola) {
    	String contento_de_consola=consola.contento_verificar;

        String[] lineas = contento_de_consola.split(Verificaciones.nl);
        
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();

            if (linea.startsWith("java.lang.module.ResolutionException:")) {
                try {
                    StringBuilder msgBuilder = new StringBuilder(linea);
                    
                    // Recolecta líneas múltiples del error
                    while (i+1 < lineas.length && !lineas[i+1].trim().isEmpty() 
                           && !lineas[i+1].startsWith("\tat ")) {
                        msgBuilder.append(" ").append(lineas[++i].trim());
                    }
                    
                    String mensaje = msgBuilder.toString()
                            .replace("ResolutionException:", "")
                            .trim();

                    String modulosCombinados = "";
                    String paquete = "";

                    if (mensaje.contains("contains package")) {
                        String[] partes = mensaje.split("contains package");
                        modulosCombinados = partes[0].replace("Module", "").trim() 
                                + "+" + partes[1].split("module ")[1].split(" ")[0].trim();
                        paquete = partes[1].split(",")[0].trim();
                        activado = true;

                    } else if (mensaje.contains("export package")) {
                        String[] partes = mensaje.split("export package");
                        modulosCombinados = partes[0].replace("Modules", "").replace(" and ", "+").trim();
                        paquete = partes[1].split("to module")[0].trim();
                        activado = true;

                    } else if (mensaje.contains("exports package")) {
                        String[] partes = mensaje.split("exports package");
                        modulosCombinados = partes[0].trim().replace("and", "+");
                        paquete = partes[1].split("to module")[0].trim();
                        activado = true;
                    }

                    if (activado) {
                        paquete = paquete.replaceAll("^\"|\"$", "").trim();
                        Buscardor.cargar();
                        List<ArchivoDeMod> mods = Buscardor.buscarModsConTermino(paquete);
                        String resultado = formatearResultadoBusqueda(mods);

                        mensajes.append(
                            MonitorDePID.idioma.module_resolution_exception(
                                "<b>" + modulosCombinados + "</b>",
                                "<b>" + paquete + "</b>"
                            )).append(resultado)
                            .append(Verificaciones.nl_html);
                    }

                } catch (Exception e) {
                    CrashDetectorLogger.logException(e);
                }
            }
        }
    }

    private String formatearResultadoBusqueda(List<ArchivoDeMod> mods) {
        if (mods.isEmpty()) return "()";
        
        String contenido = mods.stream()
                .map(mod -> "<b>" + Buscardor.rutaParaPublicar(mod.ubicacion()) + "</b>")
                .collect(Collectors.joining(", "));
        
        return "(" + contenido + ")";
    }

    @Override
    public Verificaciones nueva() {
        return new ModulesDuplicadosJavaModulePlatform();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1000.0f; 
    }

    @Override
    public String mensaje() {
        return mensajes.toString();
    }
    
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_modules_duplicados_jmps();
	}
    
}