package com.asbestosstar.crashdetectormc.analyzador;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class AuditorTransformer implements Verificaciones {

    private boolean activado = false;
    private final List<EntradaAudit> entradas = new ArrayList<>();
    private static final String[] LISTA_DE_DENEGADOS = {
        "PLUGIN: runtimedistcleaner",
        "PLUGIN: accesstransformer",
        "PLUGIN: crashdetector",
        "REASON: classloading",
        "TRANSFORMER: crashdetector"
    };

    @Override
    public void verificar(Consola consola) {
        if (consola == null || consola.contento_verificar == null) return;

        String[] lineas = consola.contento_verificar.split(Verificaciones.nl);
        if (lineas.length == 0) return;

        int auditIndex = 0;

        for (int i = lineas.length - 1; i >= 0; i--) {
            String linea = lineas[i].trim();
            if (linea.isEmpty()) continue;

            try {
                if (linea.startsWith("Transformer Audit:")) {
                    auditIndex++;
                    continue;
                }

                if (esEntradaValida(linea) && !estaEnDenylist(linea)) {
                    float score = auditIndex + (entradas.size() % 100) / 100f;
                    entradas.add(new EntradaAudit(linea, score));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        activado = !entradas.isEmpty();
    }

    private boolean esEntradaValida(String linea) {
        return linea.startsWith("REASON") 
               || linea.startsWith("TRANSFORMER") 
               || linea.startsWith("PLUGIN");
    }

    private boolean estaEnDenylist(String linea) {
        for (String bloque : LISTA_DE_DENEGADOS) {
            if (linea.startsWith(bloque)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String mensaje() {
        if (!activado || entradas.isEmpty()) return "";

        StringBuilder html = new StringBuilder();
        html.append(MonitorDePID.idioma.auditorias_transformer_detectadas());
        html.append("<ul>");

        for (EntradaAudit entrada : entradas) {
            html.append("<li>")
                .append(entrada.texto)
                .append(" (")
                .append(String.format("%.2f", entrada.score))
                .append(")</li>");
        }

        html.append("</ul>");
        return html.toString();
    }

    private static class EntradaAudit {
        String texto;
        float score;

        EntradaAudit(String texto, float score) {
            this.texto = texto;
            this.score = score;
        }
    }

    @Override
    public Verificaciones nueva() {
        return new AuditorTransformer();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 2.5f;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.auditorias_transformer();
    }
}