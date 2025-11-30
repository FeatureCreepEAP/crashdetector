package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;

/**
 * Detecta errores de compatibilidad con Falling Attack donde el mod está marcado
 * como compatible con 1.19.* pero en realidad es para 1.20.*, causando un
 * ClassMetadataNotFoundException para DamageSources.
 */
public class ErrorFallingAttackVersion implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String enlaceHtml = "";
    private boolean encontradoFallingAttack = false;

    /**
     * Método de compatibilidad — busca si Falling Attack está presente en el contenido completo del registro.
     */
    @Override
    public void verificar(Consola consola) {
        // Verificamos si Falling Attack está presente en el contenido del registro
        if (consola.contenido_verificar != null) {
            encontradoFallingAttack = consola.contenido_verificar.toLowerCase().contains("falling-attack");
        }
    }

    /**
     * Análisis por línea del registro.
     * <p>
     * Se busca el patrón característico del error donde Falling Attack falla
     * porque intenta usar una clase que no existe en la versión de Minecraft actual.
     * </p>
     */
    @Override
    public void verificar(Consola consola, String linea, int numero_de_linea) {
        if (activado) {
            // Si ya se activó, no seguimos verificando más líneas.
            return;
        }

        // Buscamos la línea que contiene el error de clase no encontrada de Falling Attack
        if (linea.contains("org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException")
                && linea.contains("net.minecraft.world.damagesource.DamageSources")
                && encontradoFallingAttack) {
            
            // Enlazar a la línea del error en el lector
            enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

            // Mensaje de error en HTML con referencia al problema de versión de Falling Attack
            mensaje = MonitorDePID.idioma.errorFallingAttackVersion() + Verificaciones.nl_html;
            activado = true;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorFallingAttackVersion();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1300.0f; // Alta prioridad: rompe la carga del mod
    }

    @Override
    public String mensaje() {
        return activado ? (mensaje + enlaceHtml) : "";
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreDeErrorFallingAttackVersion();
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
                .agregarEtiqueta(MonitorDePID.idioma.pasoErrorFallingAttackVersion())
                .construir();
    }

    @Override
    public String id() {
        return "error_falling_attack_version";
    }

    /**
     * Asocia esta verificación con un trazo específico del stack.
     * <p>
     * Devuelve true si el trazo contiene las cadenas clave del error de
     * compatibilidad de versión de Falling Attack.
     * </p>
     */
    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        if (!activado || trazo == null || trazo.trace == null) {
            return false;
        }

        String t = trazo.trace;

        return t.contains("org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException")
                && t.contains("net.minecraft.world.damagesource.DamageSources")
                && t.toLowerCase().contains("falling-attack");
    }
}