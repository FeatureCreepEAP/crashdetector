package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import com.asbestosstar.crashdetector.gui.NoRegistroDeLauncher;

/**
 * Si tu Lanzer no tiene registros puede usar esta.
 */
public class ProxySysOutSysErr {

	public static void init() {
		if (Config.obtenerInstancia().obtenerProxySysOutSysErr()) {
		    // Obtiene el archivo de registro donde se guardarán las salidas
		    File archivoLog = NoRegistroDeLauncher.cd_launcherlog;
		    try {
		        // Guarda referencias a los flujos originales de salida y error
		        PrintStream salidaOriginal = System.out;
		        PrintStream errorOriginal = System.err;

		        // Asegura que el archivo esté limpio: crea si no existe o limpia contenido existente
		        FileOutputStream flujoArchivoLog = new FileOutputStream(archivoLog, false);

		        // Crea flujos combinados que escriben tanto al archivo como a los flujos originales
		        OutputStream flujoSalidaCombinado = new OutputStream() {
		            @Override
		            public void write(int dato) throws IOException {
		                // Escribe en el archivo limpio y en el flujo original de salida
		                flujoArchivoLog.write(dato);
		                salidaOriginal.write(dato);
		            }

		            @Override
		            public void flush() throws IOException {
		                flujoArchivoLog.flush();
		                salidaOriginal.flush();
		            }

		            @Override
		            public void close() throws IOException {
		                // Solo se limpian los buffers, no se cierra el flujo de archivo (compartido)
		                flush();
		            }
		        };

		        OutputStream flujoErrorCombinado = new OutputStream() {
		            @Override
		            public void write(int dato) throws IOException {
		                // Escribe en el archivo limpio y en el flujo original de error
		                flujoArchivoLog.write(dato);
		                errorOriginal.write(dato);
		            }

		            @Override
		            public void flush() throws IOException {
		                flujoArchivoLog.flush();
		                errorOriginal.flush();
		            }

		            @Override
		            public void close() throws IOException {
		                // Solo se limpian los buffers, no se cierra el flujo de archivo (compartido)
		                flush();
		            }
		        };

		        // Crea nuevos PrintStream con los flujos combinados y activa auto-flush
		        PrintStream nuevaSalida = new PrintStream(flujoSalidaCombinado, true);
		        PrintStream nuevoError = new PrintStream(flujoErrorCombinado, true);

		        // Redirige las salidas del sistema a los nuevos flujos combinados
		        System.setOut(nuevaSalida);
		        System.setErr(nuevoError);
		    } catch (FileNotFoundException e) {
		        // Si no se puede acceder al archivo, mantiene los flujos originales
		        // y registra el error en la salida estándar (que sigue apuntando a consola)
		        System.err.println("ERROR: No se pudo crear/inicializar el archivo de registro - " + e.getMessage());
		    }
		}
	}
	
	
}
