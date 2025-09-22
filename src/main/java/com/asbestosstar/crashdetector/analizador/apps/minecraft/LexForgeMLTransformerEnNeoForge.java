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
 * Detección simple del caso en el que un transformador de LexForge/Forge
 * se ejecuta bajo NeoForge usando CPW ModLauncher y falta el método
 * getTargetType() de ITransformer. No usa expresiones regulares.
 *
 * Condición: en una misma línea del log deben aparecer las cadenas clave:
 *  - "Receiver class "
 *  - "does not define or inherit an implementation"
 *  - "cpw.mods.modlauncher.api.TargetType getTargetType()"
 *  - "cpw.mods.modlauncher.api.ITransformer"
 *
 * Luego se extrae el nombre de la clase receptora, se buscan los JARs que
 * contienen esa clase y se construye el mensaje y el enlace.
 */
public class LexForgeMLTransformerEnNeoForge implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private String enlaceHtml = "";

    private String claseReceptora = "";
    private final String interfazObjetivo = "cpw.mods.modlauncher.api.ITransformer";
    private final String firmaMetodoFaltante = "TargetType getTargetType()";
    private final List<String> modsUbicacion = new ArrayList<>();

    @Override
    public void verificar(Consola consola) {
        String[] lineas = consola.contenido_verificar.split(Verificaciones.nl);

        for (int i = 0; i < lineas.length; i++) {
            String l = lineas[i];
            // Comprobación mínima: todo en la misma línea
            if (l.contains("Receiver class ")
                    && l.contains("does not define or inherit an implementation")
                    && l.contains("cpw.mods.modlauncher.api.TargetType getTargetType()")
                    && l.contains("cpw.mods.modlauncher.api.ITransformer")) {
                CrashDetectorLogger.log("LexForgeMLTransformerEnNeoForge: coincidencia -> " + l);

                // Extraer la clase entre "Receiver class " y " does not define"
                int s = l.indexOf("Receiver class ");
                if (s >= 0) {
                    s += "Receiver class ".length();
                    int e = l.indexOf(" does not define", s);
                    if (e > s) {
                        claseReceptora = l.substring(s, e).trim();
                    }
                }

                if (!claseReceptora.isEmpty()) {
                    // Buscar únicamente mods que contengan exactamente esa clase
                    String classPath = claseReceptora.replace('.', '/') + ".class";
                    List<ArchivoDeMod> mods = Buscardor.buscarModsConTermino(classPath);
                    for (ArchivoDeMod m : mods) {
                        modsUbicacion.add(m.ubicacion_para_publicar());
                    }
                } else {
                    CrashDetectorLogger.log("LexForgeMLTransformerEnNeoForge: no se pudo extraer clase receptora");
                }

                // Activar SIEMPRE tras detectar la línea, aunque no se encuentren mods
                mensaje = MonitorDePID.idioma.errorLexForgeMLTransformerEnNeoForge(
                        claseReceptora.isEmpty() ? "(desconocida)" : claseReceptora,
                        interfazObjetivo,
                        firmaMetodoFaltante,
                        modsUbicacion
                ) + Verificaciones.nl_html;

                enlaceHtml = consola.agregarErrorALectador(i, this);
                activado = true;
                break; // Una coincidencia es suficiente
            }
        }
    }

    @Override
    public Verificaciones nueva() {
        return new LexForgeMLTransformerEnNeoForge();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 950.0f; // Bloquea el arranque
    }

    @Override
    public String mensaje() {
        if (!activado) return "";
        return mensaje + enlaceHtml;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_de_LexForgeMLTransformerEnNeoForge();
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
                .agregarEtiqueta(MonitorDePID.idioma.paso1_LexForgeMLTransformerEnNeoForge(
                        claseReceptora.isEmpty() ? "(desconocida)" : claseReceptora,
                        interfazObjetivo,
                        firmaMetodoFaltante))
                .agregarEtiqueta(MonitorDePID.idioma.paso2_LexForgeMLTransformerEnNeoForge(modsUbicacion))
                .agregarEtiqueta(MonitorDePID.idioma.paso3_LexForgeMLTransformerEnNeoForge())
                .agregarEtiqueta(MonitorDePID.idioma.paso4_LexForgeMLTransformerEnNeoForge())
                .construir();
    }
}
