package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores relacionados con el mod Mob AI Tweaks.
 *
 * Ejemplo en StackTrace: $mob-ai-tweaks$onSpawned
 *
 * La presencia de este Mixin en el crash suele indicar un conflicto durante
 * la generación o apareamiento de mobs.
 */
public class ErrorMobAITweaks implements Verificaciones {

    private boolean activado = false;
    private boolean analizarLineas = false;
    private String enlace = "";

    @Override
    public void verificar(Consola consola) {

        String log = consola.contenido_verificar;

        if (log == null)
            return;

        // Pre-check global: Buscamos la firma del Mixin de Mob AI Tweaks
        if (log.contains("$mob-ai-tweaks$onSpawned")) {
            analizarLineas = true;
        }
    }

    @Override
    public void verificar(Consola consola, String linea, int numero_de_linea) {

        if (!analizarLineas || linea == null || activado)
            return;

        // Buscamos la línea exacta donde aparece el mixin problemático
        if (linea.contains("$mob-ai-tweaks$onSpawned")) {

            this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorMobAITweaks();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1300.0f;
    }

    @Override
    public String mensaje() {
        return MonitorDePID.idioma.mensajeErrorMobAITweaks() + this.enlace;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreErrorMobAITweaks();
    }

    @Override
    public QuickFix solucion() {
        return QuickFix.NINGUN;
    }

    @Override
    public String id() {
        return "error_mob_ai_tweaks";
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return false;
    }

    @Override
    public Documento docs() {
        return Documento.NINGUN;
    }

    @Override
    public String enlaceACodigo() {
        return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
                + this.getClass().getSimpleName() + ".java";
    }
}