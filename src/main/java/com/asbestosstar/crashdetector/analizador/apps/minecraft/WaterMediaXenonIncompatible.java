package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;

/**
 * Detecta el fallo de inicio de WaterMedia en Forge por incompatibilidad con Xenon.
 * No usa expresiones regulares; busca substrings clave en una sola línea.
 *
 * Ejemplo de línea:
 * java.lang.RuntimeException: Failed starting WATERMeDIA for Forge: Xenon(xenon) is NOT compatible with WaterMedia. Please replace it with Embeddium (embeddium) or Sodium (sodium)
 */
public class WaterMediaXenonIncompatible implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String enlaceHtml = "";

    private String modNombre = "";
    private String modId = "";
    private final List<String> modsUbicacion = new ArrayList<>();

    @Override
    public void verificar(Consola consola) {
        String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);

        for (int i = 0; i < lineas.length; i++) {
            String l = lineas[i];

            // Condiciones mínimas para considerar este error
            if (l.contains("Failed starting WATERMeDIA for Forge")
                    && l.contains("is NOT compatible with WaterMedia")) {

                // Extraer "Nombre(id)" después de "Forge: "
                int forgeIdx = l.indexOf("Forge:");
                if (forgeIdx >= 0) {
                    int start = forgeIdx + "Forge: ".length();
                    int open = l.indexOf('(', start);
                    int close = (open >= 0) ? l.indexOf(')', open + 1) : -1;
                    int endName = (open >= 0) ? open : l.indexOf(" is NOT", start);

                    if (endName > start) {
                        modNombre = l.substring(start, endName).trim();
                    }
                    if (open >= 0 && close > open) {
                        modId = l.substring(open + 1, close).trim();
                    }
                }

                // Búsqueda opcional de JARs que contengan el mod (no bloquea la activación)
                String termino = !modId.isEmpty() ? modId : modNombre;
                if (termino != null && !termino.isEmpty()) {
                    try {
                        for (ArchivoDeMod m : Buscardor.buscarModsConTermino(termino)) {
                            modsUbicacion.add(m.ubicacion_para_publicar());
                        }
                    } catch (Throwable t) {
                        CrashDetectorLogger.log("WaterMediaXenonIncompatible: fallo en búsqueda de mods: " + t.getMessage());
                    }
                }

                mensaje = MonitorDePID.idioma.errorWaterMediaXenonIncompatible(
                        modNombre.isEmpty() ? "(desconocido)" : modNombre,
                        modId.isEmpty() ? "(desconocido)" : modId,
                        modsUbicacion
                ) + Verificaciones.nl_html;

                enlaceHtml = consola.agregarErrorALectador(i, this);
                activado = true;
                break;
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new WaterMediaXenonIncompatible();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 900.0f; // Evita el inicio correcto del juego
    }

    @Override
    public String mensaje() {
        if (!activado) return "";
        return mensaje + enlaceHtml;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreDeWaterMediaXenonIncompatible();
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
                .agregarEtiqueta(MonitorDePID.idioma.paso1WaterMediaXenonIncompatible(
                        modNombre.isEmpty() ? "(desconocido)" : modNombre,
                        modId.isEmpty() ? "(desconocido)" : modId))
                .agregarEtiqueta(MonitorDePID.idioma.paso2WaterMediaXenonIncompatible(modsUbicacion))
                .agregarEtiqueta(MonitorDePID.idioma.paso3WaterMediaXenonIncompatible())
                .construir();
    }
    
    @Override
	public String id() {
		// TODO Auto-generated method stub
		return "watermedia_xenon";
	}
}
