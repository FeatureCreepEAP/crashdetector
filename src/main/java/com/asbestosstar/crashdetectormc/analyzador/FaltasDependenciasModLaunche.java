package com.asbestosstar.crashdetectormc.analyzador;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class FaltasDependenciasModLaunche implements Verificaciones {

    private boolean activado = false;
    private final Set<String> errores = new HashSet<>();

    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contento_verificar;
        String[] lineas = contenidoConsola.split(Verificaciones.nl);
        
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();
            
            if (linea.contains("only supports") && linea.contains("or above")) {
                try {
                    String modId = extraerModId(linea);
                    String dependencia = extraerDependencia(linea);
                    String versionRequerida = extraerVersionRequerida(linea);
                    
                    if (i+1 < lineas.length) {
                        String lineaSiguiente = lineas[i+1].trim();
                        if (lineaSiguiente.startsWith("Currently,")) {
                            String versionActual = extraerVersionActual(lineaSiguiente, dependencia);
                            errores.add(
                                MonitorDePID.idioma.errorVersionDependencia(
                                    modId, dependencia, versionRequerida, versionActual
                                )
                            );
                            activado = true;
                        }
                    }
                } catch (Exception e) {
                    // Ignora errores de parseo
                }
            }
            
            // Formato antiguo de dependencias faltantes
            else if (linea.contains("Missing or unsupported mandatory dependencies:")) {
                StringBuilder mensaje = new StringBuilder(
                    MonitorDePID.idioma.no_tienes_las_dependencias_necesitas()
                ).append(Verificaciones.nl_html);
                
                for (String lineaDep : lineas) {
                    if (lineaDep.contains("Mod ID") && lineaDep.contains("Requested by")) {
                        mensaje.append(
                            MonitorDePID.idioma.linea_de_dependencia(lineaDep)
                        ).append(Verificaciones.nl_html);
                    }
                }
                errores.add(mensaje.toString());
                activado = true;
            }
        }
    }

    // Métodos auxiliares de parseo (mantenidos igual)
    private String extraerModId(String linea) {
        int inicio = linea.indexOf("Mod ") + 4;
        int fin = linea.indexOf(" only supports");
        return limpiarFormato(linea.substring(inicio, fin));
    }

    private String extraerDependencia(String linea) {
        int inicio = linea.indexOf("supports ") + 9;
        int fin = linea.indexOf(" ", inicio);
        return limpiarFormato(linea.substring(inicio, fin));
    }

    private String extraerVersionRequerida(String linea) {
        int inicio = linea.indexOf("supports ") + 9;
        int fin = linea.indexOf(" or above");
        return limpiarFormato(linea.substring(inicio, fin));
    }

    private String extraerVersionActual(String linea, String dependencia) {
        int inicio = linea.indexOf("is ") + 3;
        return limpiarFormato(linea.substring(inicio));
    }

    private String limpiarFormato(String texto) {
        return texto.replaceAll("§[a-zA-Z0-9]", "");
    }

    @Override
    public Verificaciones nueva() {
        return new FaltasDependenciasModLaunche();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 900f;
    }

    @Override
    public String mensaje() {
        if (errores.isEmpty()) return "";
        
        StringBuilder html = new StringBuilder("<ul>");
        for (String error : errores) {
            html.append("<li>").append(error).append("</li>");
        }
        html.append("</ul>");
        return html.toString();
    }
    
	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_faltas_dependencias_de_modlauncher();
	}
    
    
    
}