package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;

public class MCForgeModsSuspechoso implements Verificaciones {

    private boolean activado = false;
    private final Set<String> errores = new HashSet<>();

    @Override
    public void verificar(Consola consola) {
        String contenidoConsola = consola.contenido_verificar;
        String[] lineas = contenidoConsola.split(Verificaciones.nl);

        // Patrón para errores durante la fase de registro
        Pattern patronFaseRegistro = Pattern.compile(
            "(?i)encountered an error during the .*? dispatch for modid ([a-z0-9_\\-.]+)"
        );

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i];

            // 1. Detectar mods sospechosos
            if (linea.contains("Suspected Mod:")) {
                if (i + 1 < lineas.length) {
                    String modLinea = lineas[i + 1].trim();

                    int inicio = modLinea.indexOf('(');
                    int fin = modLinea.indexOf(')');
                    String modID = modLinea;

                    if (inicio != -1 && fin != -1 && inicio < fin) {
                        modID = modLinea.substring(inicio + 1, fin).trim();
                    }

                    // CORRECCIÓN: No agregar dos puntos adicionales
                    errores.add(MonitorDePID.idioma.mcforge_mod_sospechoso() + modID.trim());
                    activado = true;
                }
            }

            // 2. ModID fallido al crear instancia
            else if (linea.contains("Failed to create mod instance. ModID:")) {
                try {
                    String prefijo = "Failed to create mod instance. ModID: ";
                    int indiceInicio = linea.indexOf(prefijo);
                    if (indiceInicio != -1) {
                        indiceInicio += prefijo.length();
                        String parteDespuesDelPrefijo = linea.substring(indiceInicio).trim();

                        // Lógica mejorada de extracción del ModID
                        String modID = "";
                        int j = 0;
                        while (j < parteDespuesDelPrefijo.length()) {
                            char c = parteDespuesDelPrefijo.charAt(j);
                            if (Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == '+') {
                                modID += c;
                                j++;
                            } else {
                                // Detenerse en el primer carácter que no es de ModID
                                break;
                            }
                        }
                        modID = modID.trim();

                        // Solo agregar el error si se extrajo correctamente un ModID
                        if (!modID.isEmpty()) {
                            // CORRECCIÓN: No agregar dos puntos adicionales ni detalles de la clase
                            errores.add(MonitorDePID.idioma.mcforge_mod_sospechoso() + modID);
                            activado = true;
                        }
                    }
                } catch (Exception e) {
                    // Ignorar errores de formato
                }
            }
                

            // 3. Mensaje de fallo con ModID
            else if (linea.contains("Failure message:")) {
                int inicio = linea.indexOf('(');
                if (inicio != -1) {
                    int fin = linea.indexOf(')', inicio + 1);
                    if (fin != -1) {
                        String modID = linea.substring(inicio + 1, fin).trim();
                        // CORRECCIÓN: No agregar dos puntos adicionales
                        errores.add(MonitorDePID.idioma.mcforge_mod_sospechoso() + modID);
                        activado = true;
                    }
                }
            }

            // 4. Nueva detección: errores durante la fase de registro
            Matcher matcherFaseRegistro = patronFaseRegistro.matcher(linea);
            while (matcherFaseRegistro.find()) {
                String modID = matcherFaseRegistro.group(1).trim();
                // CORRECCIÓN: No agregar dos puntos adicionales
                errores.add(MonitorDePID.idioma.mcforge_mod_sospechoso() + modID);
                activado = true;
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new MCForgeModsSuspechoso();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 900.0f; // Prioridad alta para errores durante la carga
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
        return MonitorDePID.idioma.nombre_de_mcforge_mod_sespechoso();
    }

    @Override
    public QuickFix solucion() {
        return new Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
            .construir();
    }
}