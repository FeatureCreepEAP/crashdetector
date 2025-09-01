package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta discrepancias de versión en mods utilizados por un mundo guardado. Gracias a Aternos por que esta es una implementacion de su codex https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaVersionModMundo implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private final List<String> nombresMods = new ArrayList<>();
    private final List<String> versionesEsperadas = new ArrayList<>();
    private final List<String> versionesActuales = new ArrayList<>();

    /**
     * Verifica si el log contiene discrepancias de versión de mods en mundos guardados.
     */
    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contenido_verificar;

        // Patrón para detectar discrepancias de versión de mod en el mundo
        Pattern patron = Pattern.compile(
            "This world was saved with mod (\\w+) version (\\S+) and it is now at version ([^,]+), things may not work well",
            Pattern.CASE_INSENSITIVE
        );
        Matcher coincidencia = patron.matcher(contenido);

        while (coincidencia.find()) {
            String nombreMod = coincidencia.group(1).trim();
            String versionEsperada = coincidencia.group(2).trim();
            String versionActual = coincidencia.group(3).trim();

            if (!nombreMod.isEmpty() && !versionEsperada.isEmpty() && !versionActual.isEmpty()) {
                nombresMods.add(nombreMod);
                versionesEsperadas.add(versionEsperada);
                versionesActuales.add(versionActual);
            }
        }

        if (!nombresMods.isEmpty()) {
            if (nombresMods.size() > 1) {
                this.mensaje = MonitorDePID.idioma.mensajeVersionModMundoPlural(nombresMods, versionesEsperadas, versionesActuales);
            } else {
                this.mensaje = MonitorDePID.idioma.mensajeVersionModMundoSingular(
                    nombresMods.get(0), versionesEsperadas.get(0), versionesActuales.get(0)
                );
            }
            activado = true;
        }
    }

    /**
     * Crea una nueva instancia del verificador.
     */
    @Override
    public Verificaciones nueva() {
        return new ProblemaVersionModMundo();
    }

    /**
     * Indica si el problema fue detectado.
     */
    @Override
    public boolean activado() {
        return activado;
    }

    /**
     * Prioridad del problema (alta).
     */
    @Override
    public float prioridad() {
        return 800.0f;
    }

    /**
     * Devuelve el mensaje de error almacenado.
     */
    @Override
    public String mensaje() {
        return mensaje;
    }

    /**
     * Devuelve el nombre del problema para mostrar en la interfaz.
     */
    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreProblemaVersionModMundo();
    }

    /**
     * Devuelve las soluciones posibles para este problema.
     */
    @Override
    public QuickFix solucion() {
        Builder builder = new Builder(nombre());

        // Agregar solución para cada mod afectado
        for (int i = 0; i < nombresMods.size(); i++) {
            String nombreMod = nombresMods.get(i);
            String versionEsperada = versionesEsperadas.get(i);
            builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarModConVersion(nombreMod, versionEsperada));
        }

        // Agregar solución de no hacer nada
        builder.agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible());

        return builder.construir();
    }
    
    
    @Override
    public boolean anularNormal() {
    	return true;
    }
    
}