package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detecta errores AbstractMethodError específicos donde una clase no implementa
 * un método de una interfaz. Extrae los nombres concretos y el origen desde el trace.
 */
public class ErrorMetodoAbstractoNoImplementado implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";
    private static final Pattern PATRON_ABSTRACT_METHOD = Pattern.compile(
        "java\\.lang\\.AbstractMethodError: Receiver class ([^ ]+) does not define or inherit an implementation of the resolved method '([^']+)' of interface ([^\\.]+\\.[^\\s]+)"
    );

    @Override
    public void verificar(Consola consola) {
        String contenido = consola.contenido_verificar;
        String[] lineas = contenido.split(Verificaciones.nl);

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();
            if (linea.contains("java.lang.AbstractMethodError") &&
                linea.contains("does not define or inherit an implementation") &&
                linea.contains("of interface")) {

                Matcher m = PATRON_ABSTRACT_METHOD.matcher(linea);
                if (m.find()) {
                    String claseConcreta = m.group(1);      // Ej: Aru.Aru.ashvehicle.entity.vehicle.F117Entity
                    String firmaMetodo = m.group(2);        // Ej: boolean canShoot(net.minecraft.world.entity.LivingEntity)
                    String interfaz = m.group(3);           // Ej: com.atsuishio.superbwarfare.entity.vehicle.base.WeaponVehicleEntity

                    // Buscar origen en las líneas siguientes del stack trace (máx. 10 líneas)
                    String origen = "";
                    for (int j = i + 1; j < Math.min(i + 11, lineas.length); j++) {
                        String l = lineas[j].trim();
                        if (l.startsWith("at ")) {
                            // Extraer modid, jar o paquete de la línea de stack
                            String posibleOrigen = VerificacionDeStackTrace.extraerModidDeLinea(l);
                            if (posibleOrigen == null || VerificacionDeStackTrace.esModNoPermite(posibleOrigen)) {
                                // Intentar con JAR
                                java.util.List<String> jars = VerificacionDeStackTrace.extraerJarsDeLinea(l);
                                if (!jars.isEmpty()) {
                                    posibleOrigen = jars.get(0);
                                } else {
                                    // Último recurso: paquete
                                    posibleOrigen = VerificacionDeStackTrace.extraerPaqueteDeLinea(l);
                                }
                            }
                            if (posibleOrigen != null && !posibleOrigen.isEmpty() &&
                                !VerificacionDeStackTrace.esModNoPermite(posibleOrigen)) {
                                origen = posibleOrigen;
                                break;
                            }
                        } else if (!l.isEmpty() && !l.startsWith("Caused by") && !l.startsWith("...")) {
                            // Ya no es parte del stack trace inmediato
                            break;
                        }
                    }

                    this.mensaje = MonitorDePID.idioma.errorMetodoAbstractoNoImplementadoDetallado(
                        claseConcreta, firmaMetodo, interfaz, origen
                    );
                    this.activado = true;
                    return;
                }
            }
        }
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return trazo.trace.contains("java.lang.AbstractMethodError") &&
               trazo.trace.contains("does not define or inherit an implementation") &&
               trazo.trace.contains("of interface");
    }

    @Override
    public Verificaciones nueva() {
        return new ErrorMetodoAbstractoNoImplementado();
    }

    @Override
    public boolean activado() {
        return this.activado;
    }

    @Override
    public String mensaje() {
        return this.mensaje;
    }

    @Override
    public float prioridad() {
        return 1200.0f;
    }

    @Override
    public QuickFix solucion() {
        return new QuickFix.Builder(nombre())
            .agregarEtiqueta(MonitorDePID.idioma.solucionMetodoAbstractoNoImplementado())
            .construir();
    }

    @Override
    public String id() {
        return "metodo_abstracto_no_implementado";
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreErrorMetodoAbstractoNoImplementado();
    }
}