package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;

public class LenguajeProveedorCheck implements Verificaciones {

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
	public void verificar(String contenido_de_consola, CDStringBuilder constructor) {
	    // Dividir el contenido de la consola en líneas individuales para procesarlas
	    String[] lineas = contenido_de_consola.split("\n");

	    for (String linea : lineas) {
	        // Verificar si la línea contiene el patrón que indica un problema de proveedor de lenguaje
	        if (linea.contains("Mod File") && linea.contains("needs language provider")) {
	            CrashDetectorLogger.log("Se detectó un error de proveedor de lenguaje.");

	            try {
	                // Extraer el nombre del archivo JAR
	                String archivoJar = linea.split("Mod File ")[1].split(" needs")[0].trim();
	                CrashDetectorLogger.log("Archivo JAR: " + archivoJar);

	                // Extraer el proveedor de lenguaje requerido y su versión
	                String proveedor = linea.split("language provider ")[1].split(" ")[0].trim();
	                CrashDetectorLogger.log("Proveedor: " + proveedor);

	                String req = proveedor.split(":")[1];
	                CrashDetectorLogger.log("Versión Requerida: " + req);

	                // Buscar la versión encontrada en las líneas siguientes
	                String encontrado = "";
	                for (String siguienteLinea : lineas) {
	                    if (siguienteLinea.contains("We have found ")) {
	                        encontrado = siguienteLinea.split("We have found ")[1].split("§")[0].trim();
	                        CrashDetectorLogger.log("Versión Encontrada: " + encontrado);
	                        break;
	                    }
	                }

	                // Construir el mensaje de error incluyendo el nombre del archivo JAR
	                constructor.append(MonitorDePID.idioma.errorProveedorVersion(
	                        archivoJar, proveedor.split(":")[0], req, encontrado))
	                        .append(nl_html);

	                // Caso especial para JavaFML/MCForge
	                if (proveedor.toLowerCase().contains("javafml")) {
	                    constructor.append(MonitorDePID.idioma.errorJavaFML_MCForge())
	                            .append(nl_html);
	                }

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
}