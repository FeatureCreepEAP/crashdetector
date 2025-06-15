package com.asbestosstar.crashdetector.analizador;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;

public class CursedConsola implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private static final String CURSEFORGE_MARKER = "curseforge";
    private static final String INSTANCE_MARKER = "instances";
    private static final String LAUNCHER_LOG = "launcher_log";

    @Override
    public void verificar(Consola consolaog) {
        try {
            // Validar entorno básico
            if (!esEntornoCurseForge()) return;

            // Verificar existencia de logs del launcher
            if (!tieneLogLauncher()) {
                mensaje = MonitorDePID.idioma.noTieneConsolaDeLauncherCursedForge();
                activado = true;
            }
        } catch (Exception e) {
            // En caso de error inesperado, desactivar notificación
            CrashDetectorLogger.logException(e);
        	activado = false;
        }
    }

    private boolean esEntornoCurseForge() {
        try {
            Path currentPath = Paths.get("").toAbsolutePath();
            String ruta = currentPath.toString().toLowerCase();
            return ruta.contains(CURSEFORGE_MARKER) 
                   && ruta.contains(INSTANCE_MARKER);
        } catch (Exception e) {
            CrashDetectorLogger.logException(e);
            return false;
        }
    }

    private boolean tieneLogLauncher() {
        // Verificación mejorada con múltiples puntos de control
        return MonitorDePID.consolas.stream()
            .filter(consola -> consola.archivo != null)
            .anyMatch(consola -> 
                consola.archivo.toString().toLowerCase().contains(LAUNCHER_LOG)
            );
    }

    @Override
    public Verificaciones nueva() {
        return new CursedConsola(); 
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1000f; // Prioridad baja para advertencias de logs faltantes
    }

    @Override
    public String mensaje() {
        return mensaje;
    }
    
    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_de_cursed_consola();
    }
}