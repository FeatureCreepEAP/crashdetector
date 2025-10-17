package com.asbestosstar.crashdetector;

import java.io.*;

import com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador.NoRegistroDeLauncher;

/**
 * Si tu Launcher no tiene registros puede usar esta.
 * Soluciona el problema de escritura interrumpida al registrar System.out/System.err
 * Incluye manejo robusto de errores para evitar que fallos en consola detengan el registro
 */
public class ProxySysOutSysErr {

    public static void init() {
        if (Config.obtenerInstancia().obtenerProxySysOutSysErr()) {
            File archivoLog = NoRegistroDeLauncher.cd_launcherlog;
            try {
                // 1. Crea un ÚNICO flujo de archivo sincronizado y resistente a errores
                FileOutputStream flujoArchivo = new FileOutputStream(archivoLog, false);
                OutputStream flujoArchivoSeguro = new OutputStream() {
                    private final Object cerrojo = new Object();
                    
                    @Override
                    public void write(int b) throws IOException {
                        synchronized (cerrojo) {
                            try {
                                flujoArchivo.write(b);
                            } catch (IOException e) {
                                // Intenta registrar el error en consola (si está disponible)
                                try {
                                    System.err.println("[REGISTRO] Error al escribir en archivo: " + e.getMessage());
                                } catch (Throwable t) {
                                    // Ignora errores secundarios para no colapsar el sistema
                                }
                                throw e; // Propaga el error para que PrintStream lo maneje
                            }
                        }
                    }
                    
                    @Override
                    public void flush() throws IOException {
                        synchronized (cerrojo) {
                            flujoArchivo.flush();
                        }
                    }
                    
                    @Override
                    public void close() throws IOException {
                        flush(); // Nunca cierra el flujo de archivo
                    }
                };

                // 2. Guarda referencias a los flujos originales
                PrintStream salidaOriginal = System.out;
                PrintStream errorOriginal = System.err;

                // 3. Crea flujos combinados con manejo de errores para consola
                OutputStream flujoSalidaCombinado = new OutputStream() {
                    @Override
                    public void write(int b) {
                        try {
                            // Escribe en archivo (puede lanzar IOException)
                            flujoArchivoSeguro.write(b);
                        } catch (IOException e) {
                            // Manejo especial para errores de archivo
                            manejarErrorRegistro(e, "salida");
                        }
                        
                        // Escribe en consola con manejo de errores (NO lanza excepciones)
                        escribirEnConsolaSeguro(salidaOriginal, b);
                    }
                    
                    @Override
                    public void flush() {
                        try {
                            flujoArchivoSeguro.flush();
                        } catch (IOException e) {
                            manejarErrorRegistro(e, "salida");
                        }
                        salidaOriginal.flush();
                    }
                };

                OutputStream flujoErrorCombinado = new OutputStream() {
                    @Override
                    public void write(int b) {
                        try {
                            flujoArchivoSeguro.write(b);
                        } catch (IOException e) {
                            manejarErrorRegistro(e, "error");
                        }
                        
                        escribirEnConsolaSeguro(errorOriginal, b);
                    }
                    
                    @Override
                    public void flush() {
                        try {
                            flujoArchivoSeguro.flush();
                        } catch (IOException e) {
                            manejarErrorRegistro(e, "error");
                        }
                        errorOriginal.flush();
                    }
                };

                // 4. Configura los nuevos flujos con auto-vaciado
                System.setOut(new PrintStream(flujoSalidaCombinado, true));
                System.setErr(new PrintStream(flujoErrorCombinado, true));
                
            } catch (FileNotFoundException e) {
                System.err.println("ERROR: No se encontró el archivo de registro - " + e.getMessage());
            }
        }
    }

    /**
     * Escribe en consola de forma segura ignorando errores
     * (evita que fallos en consola detengan el registro de archivos)
     */
    private static void escribirEnConsolaSeguro(PrintStream flujo, int dato) {
        try {
            flujo.write(dato);
        } catch (Throwable e) {
            // Ignora errores de consola para no afectar el registro principal
            // (común en entornos sin consola como algunos launchers)
        }
    }

    /**
     * Maneja errores críticos durante la escritura en archivo
     * (muestra advertencia pero mantiene funcionamiento del registro)
     */
    private static void manejarErrorRegistro(IOException e, String tipoFlujo) {
        try {
            // Intenta registrar el error usando el flujo de error original
            System.err.println("[CRITICO] Fallo en registro de " + tipoFlujo + 
                              " - Archivo: " + e.getMessage());
        } catch (Throwable t) {
            // Si todo falla, usa el último recurso disponible
            try {
                new FileOutputStream(FileDescriptor.err).write(
                    ("[FALLA TOTAL] Registro " + tipoFlujo + " inaccesible\n").getBytes()
                );
            } catch (IOException ex) {
                // Sin opciones restantes - el sistema está en estado crítico
            }
        }
    }
}