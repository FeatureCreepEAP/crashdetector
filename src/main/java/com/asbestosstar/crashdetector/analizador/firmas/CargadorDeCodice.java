package com.asbestosstar.crashdetector.analizador.firmas;

import java.util.List;

import com.asbestosstar.crashdetector.analizador.firmas.v0.CodiceV0;
import com.asbestosstar.crashdetector.analizador.firmas.v0.VerificacionCodexV0;

/**
 * Enrutador de esquemas. Por ahora delega directamente a V0.
 * Evitamos preparsear el archivo para no fallar antes de tiempo.
 */
public class CargadorDeCodice {

    /**
     * Carga verificaciones desde codice.json usando el parser V0,
     * que internamente valida el campo "schema".
     */
    public static List<VerificacionCodexV0> cargarVerificaciones() {
        return CodiceV0.cargar();
    }

    /** Guarda una lista de verificaciones en schema 0. */
    public static void guardarSchema0(List<VerificacionCodexV0> verificaciones) throws java.io.IOException {
        CodiceV0.guardar(verificaciones);
    }
}
