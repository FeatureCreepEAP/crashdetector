package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashSet;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

public class LenguajeProveedorCheck implements Verificaciones {

    private final Set<String> errores = new HashSet<>();
	public boolean activado = false;

	/**
	 * Verifica el contenido de la consola para detectar errores relacionados con proveedores de lenguaje.
	 * Este método procesa cada línea de la salida de la consola para identificar problemas relacionados
	 * con proveedores de lenguaje faltantes o incompatibles. Extrae detalles relevantes como el nombre
	 * del archivo JAR, el proveedor requerido, su versión y la versión disponible. Además, añade un mensaje
	 * detallado al {@code CDStringBuilder} proporcionado.
	 *
	 * @param contenido_de_consola La salida cruda de la consola que puede contener mensajes de error.
	 * @param constructor           Una instancia de {@code CDStringBuilder} usada para construir el mensaje final de error.
	 */
    @Override
    public void verificar(Consola consola) {
    	String contenidoConsola=consola.contenido_verificar;
        String[] lineas = contenidoConsola.split(Verificaciones.nl);
        
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i];
            
            if (linea.contains("Mod File") && linea.contains("needs language provider")) {
                try {
                    // Extraer detalles del error
                    String archivoJar = linea.split("Mod File ")[1].split(" needs")[0].trim();
                    String proveedor = linea.split("language provider ")[1].split(" ")[0].trim();
                    String req = proveedor.split(":")[1];
                    String encontrado = "";
                    
                    // Buscar versión disponible en líneas posteriores
                    for (int j = i; j < lineas.length; j++) {
                        if (lineas[j].contains("We have found ")) {
                            encontrado = lineas[j].split("We have found ")[1].split("§")[0].trim();
                            break;
                        }
                    }
                    
                    // Construir mensaje base
                    String mensaje = MonitorDePID.idioma.errorProveedorVersion(
                        archivoJar, 
                        proveedor.split(":")[0], 
                        req, 
                        encontrado
                    );
                    
                    // Agregar mensaje especial para JavaFML/MCForge
                    if (proveedor.toLowerCase().contains("javafml")) {
                        mensaje += Verificaciones.nl_html + MonitorDePID.idioma.errorJavaFML_MCForge();
                    }
                    
                    errores.add(mensaje);
                    activado = true;

                } catch (Exception e) {
                    CrashDetectorLogger.logException(e);
                }
            }
        }
    }
	
    @Override
    public Verificaciones nueva() {
        return new LenguajeProveedorCheck();
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
		return MonitorDePID.idioma.nombre_de_lenguaje_proveedor_check();
	}
	
	
    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
            .construir();
    }
    
}