package com.asbestosstar.crashdetector.analyzador;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;

public class NoPuedeAnalizarJSONDeRegistro implements Verificaciones {

	boolean activado = false;
    private final List<String> erroresJSON = new ArrayList<>(); // Almacena múltiples errores

	/**
	 * Verifica el contenido de la consola para detectar errores de análisis en
	 * archivos JSON de mods. Este método procesa cada línea de la salida de la
	 * consola para identificar problemas relacionados con el análisis fallido de
	 * archivos JSON en mods. Extrae detalles relevantes como el nombre del archivo
	 * JAR y el recurso problemático, y añade un mensaje detallado al
	 * {@code CDStringBuilder} proporcionado.
	 *
	 * @param contenido_de_consola La salida cruda de la consola que puede contener
	 *                             mensajes de error.
	 * @param constructor          Una instancia de {@code CDStringBuilder} usada
	 *                             para construir el mensaje final de error.
	 *
	 *                             El formato esperado del mensaje de error en la
	 *                             consola es:
	 * 
	 *                             <pre>
	 * java.lang.IllegalStateException: Failed to parse [recurso_problematico] from pack [archivo_jar]
	 *                             </pre>
	 *
	 *                             Ejemplo:
	 * 
	 *                             <pre>
	 * java.lang.IllegalStateException: Failed to parse souls_like_bosses:worldgen/structure/lothric_castle.json from pack souls_like_bosses_1.1_Forge+Fabric-1.20.1.jar
	 *                             </pre>
	 */
	 @Override
	    public void verificar(Consola consola) {
	    	String contenidoConsola=consola.contento_verificar;

	        String[] lineas = contenidoConsola.split(Verificaciones.nl);
	        
	        for (String linea : lineas) {
	            if (linea.contains("Failed to parse") && linea.contains(".json from pack")) {
	                CrashDetectorLogger.log("Se detectó error de análisis JSON en registro");
	                
	                try {
	                    String archivoJar = linea.split("from pack ")[1].split("\\.jar")[0].trim() + ".jar";
	                    String recurso = linea.split("Failed to parse ")[1].split(" from pack")[0].trim();
	                    
	                    String mensaje = MonitorDePID.idioma.errorConJSONDeRegistro(archivoJar, recurso);
	                    erroresJSON.add(mensaje);
	                    activado = true;
	                    
	                } catch (Exception e) {
	                    CrashDetectorLogger.logException(e);
	                }
	            }
	        }
	    }

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new NoPuedeAnalizarJSONDeRegistro();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

	
    @Override
    public float prioridad() {
        return 500.0f; // Prioridad alta para errores de configuración crítica [[4]]
    }

    @Override
    public String mensaje() {
        if (erroresJSON.isEmpty()) return "";
        
        StringBuilder html = new StringBuilder("<ul>");
        for (String error : erroresJSON) {
            html.append("<li>").append(error).append("</li>");
        }
        html.append("</ul>");
        return html.toString();
    }

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_no_puede_analizar_json_de_registro();
	}
	
	
	
	
	
}
