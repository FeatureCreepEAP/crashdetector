package com.asbestosstar.crashdetectormc.analyzador;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;

public class AuditorTransformer implements Verificaciones {

    private boolean activado = false;
    private final List<Audit> audits = new ArrayList<>();

    @Override
    public void verificar(Consola consola) {
        if (consola == null || consola.contento_verificar == null) return;

        String[] lineas = consola.contento_verificar.split(Verificaciones.nl);
        if (lineas.length == 0) return;

        // Procesar secciones de auditoría
        int auditIndex = 0;
        Audit currentAudit = null;

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();
            if (linea.isEmpty()) continue;

            try {
                if (linea.startsWith("Transformer Audit:")) {
                    // Reiniciar auditoría al encontrar nueva sección
                    currentAudit = null;
                    continue;
                }

                if (currentAudit == null && esClasePrincipal(linea)) {
                    currentAudit = new Audit(auditIndex++, linea);
                } else if (currentAudit != null && esEntradaValida(linea)) {
                    currentAudit.entradas.add(new Entrada(linea));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (currentAudit != null && !currentAudit.entradas.isEmpty()) {
            audits.add(currentAudit);
        }

        // Ordenar por posición inversa para asignar puntuaciones
        audits.sort((a, b) -> Integer.compare(b.index, a.index));

        activado = !audits.isEmpty();
    }

    private boolean esClasePrincipal(String linea) {
        return !linea.startsWith("REASON") 
               && !linea.startsWith("TRANSFORMER") 
               && !linea.startsWith("PLUGIN");
    }

    private boolean esEntradaValida(String linea) {
        return linea.startsWith("REASON") 
               || linea.startsWith("TRANSFORMER") 
               || linea.startsWith("PLUGIN");
    }

    @Override
    public String mensaje() {
        if (!activado || audits.isEmpty()) return "";

        CDStringBuilder html = new CDStringBuilder();
        html.append(MonitorDePID.idioma.auditorias_transformer_detectadas());
        html.append("<ul>");

        for (Audit audit : audits) {
            html.append("<li><strong>").append(audit.clase)
                .append("</strong> (Score: ").append(String.valueOf(audit.score)).append(")<ul>");

            for (int i = 0; i < audit.entradas.size(); i++) {
                Entrada entrada = audit.entradas.get(i);
                html.append("<li>").append(entrada.texto)
                    .append(" <small>(").append(String.valueOf(audit.score)).append(", ")
                    .append(String.valueOf(i)).append(")</small></li>");
            }
            html.append("</ul></li>");
        }

        html.append("</ul>");
        return html.toString();
    }

    private static class Audit {
        int index;
        String clase;
        List<Entrada> entradas = new ArrayList<>();
        float score;

        Audit(int index, String clase) {
            this.index = index;
            this.clase = clase;
            this.score = (float) index + 0.1f * entradas.size();
        }
    }

    private static class Entrada {
        String texto;

        Entrada(String texto) {
            this.texto = texto;
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