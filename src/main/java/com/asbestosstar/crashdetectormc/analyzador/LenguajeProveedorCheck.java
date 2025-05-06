package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class LenguajeProveedorCheck implements Verificaciones {

    public boolean activado = false;

    @Override
    public void verificar(String contenido_de_consola, CDStringBuilder constructor) {
        // Patrón genérico para errores de proveedores de lenguaje
        if (contenido_de_consola.contains("needs language provider ")) {
            try {
                // Extraer datos del mensaje
                String proveedor = contenido_de_consola.split("language provider ")[1].split(" ")[0].trim();
                String req = proveedor.split(":")[1];
                String encontrado = contenido_de_consola.split("We have found ")[1].split("§")[0].trim();
                
                // Convertir versiones a números
                float requerido = Float.parseFloat(req);
                float encontradoNum = Float.parseFloat(encontrado);
                
                if (encontradoNum < requerido) {
                    // Mensaje genérico para cualquier proveedor
                    constructor.append(MonitorDePID.idioma.errorProveedorVersion(
                        proveedor.split(":")[0], req, encontrado)).append(nl_html);
                    
                    // Caso especial para JavaFML/MCForge
                    if (proveedor.toLowerCase().contains("javafml")) {
                        constructor.append(MonitorDePID.idioma.errorJavaFML_MCForge()).append(nl_html);
                    }
                    
                    activado = true;
                }
            } catch (Exception e) {
                // Ignorar formatos inesperados
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