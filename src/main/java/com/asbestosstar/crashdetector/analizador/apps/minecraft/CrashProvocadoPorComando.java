package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta crashes provocados manualmente usando el comando: /crash_assistant crash
 */
public class CrashProvocadoPorComando implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";

    private static final String TEXTO_REPORTED_EXCEPTION = "net.minecraft.ReportedException";
    private static final String TEXTO_CRASH_ASSISTANT = "/crash_assistant";
    private static final String TEXTO_CRASH_OBLIGATORIO = TEXTO_CRASH_ASSISTANT + " crash";
    private static final String TEXTO_MODLIST = "Modlist";

    @Override
    public String[] patronesRapidos() {
        // Mantenemos el patrón rápido general para que el escáner sepa que debe
        // prestar atención a las líneas que contienen esto.
        return new String[] { TEXTO_REPORTED_EXCEPTION, TEXTO_CRASH_ASSISTANT };
    }

    @Override
    public void verificarCoincidencia(EventoDeCoincidencia evento) {
        if (evento == null || evento.linea == null) {
            return;
        }

        verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
    }

    @Override
    public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
        // Si ya se activó, no seguimos buscando
        if (activado) {
            return;
        }

        // Filtrar líneas de pila o rutas que no son comandos de chat
        if (linea.contains("|") || linea.contains(".jar")) {
            return;
        }

        // 1. Buscar la posición exacta del comando
        int inicio = linea.indexOf(TEXTO_CRASH_ASSISTANT);
        if (inicio < 0) {
            return; // No contiene /crash_assistant
        }

        // 2. COMPROBAR QUE SEA EXACTAMENTE "/crash_assistant crash"
        // Verificamos si a partir de esa posición, la cadena sigue con " crash"
        if (!linea.startsWith(TEXTO_CRASH_OBLIGATORIO, inicio)) {
            return; // Era otra cosa, como "/crash_assistant help" o "/crash_assistant"
        }

        // 3. Evitar falsos positivos en la lista de mods (a veces los nombres de mod se confunden)
        if (linea.contains(TEXTO_MODLIST)) {
            return;
        }

        // Si pasa todas las validaciones, extraemos el comando completo y activamos
        String comandoDetectado = extraerComando(linea, inicio);
        String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

        mensaje = MonitorDePID.idioma.errorCrashProvocadoPorComando(comandoDetectado) + Verificaciones.nl_html
                + enlaceHtml;

        activado = true;
    }

    /**
     * Extrae el comando completo empezando desde la posición ya validada.
     */
    private String extraerComando(String linea, int inicio) {
        String comando = linea.substring(inicio).trim();

        // Cortar si hay una comilla simple después
        int comillaSimple = comando.indexOf('\'', 1);
        if (comillaSimple > 0) {
            comando = comando.substring(0, comillaSimple);
        }

        // Cortar si hay una comilla doble después
        int comillaDoble = comando.indexOf('"', 1);
        if (comillaDoble > 0) {
            comando = comando.substring(0, comillaDoble);
        }

        // Cortar si hay un corchete (común en algunos formatos de log de chat)
        int corchete = comando.indexOf(']', 1);
        if (corchete > 0) {
            comando = comando.substring(0, corchete);
        }

        return comando;
    }

    @Override
    public String[] ocupaTrazo() {
        return new String[] { TEXTO_CRASH_ASSISTANT };
    }

    @Override
    public Verificaciones nueva() {
        return new CrashProvocadoPorComando();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public String mensaje() {
        return mensaje;
    }

    @Override
    public float prioridad() {
        return 1500.0f;
    }

    @Override
    public QuickFix solucion() {
        return QuickFix.NINGUN;
    }

    @Override
    public String id() {
        return "crash_provocado_por_comando";
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreCrashProvocadoPorComando();
    }

    @Override
    public Documento docs() {
        return Documento.NINGUN;
    }
}