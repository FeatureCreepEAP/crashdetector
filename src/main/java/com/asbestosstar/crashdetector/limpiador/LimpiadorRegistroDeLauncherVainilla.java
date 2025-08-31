package com.asbestosstar.crashdetector.limpiador;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.asbestosstar.crashdetector.CrashDetectorLogger;

public class LimpiadorRegistroDeLauncherVainilla implements LimipiadorDeRegistro{

    /**
     * Limpia el contenido completo de la consola.
     * 
     * @param contento_de_consola Contenido completo de la consola
     * @return Contenido procesado sin corchetes ni etiquetas innecesarias
     */
	@Override
    public String limpiarConsola(String contento_de_consola) {
    	CrashDetectorLogger.log("limpiar vainilla");
        // Dividir el contenido en líneas usando el separador del sistema
        String[] lineas_viejas = contento_de_consola.split(System.lineSeparator());
        
        // Limpiar cada línea individualmente
        List<String> lineas_limpias = limpiarLineas(Arrays.asList(lineas_viejas));
        
        // Unir las líneas limpias con el separador del sistema
        return String.join(System.lineSeparator(), lineas_limpias);
    }

    /**
     * Limpia una sola línea de registro.
     * 
     * @param linea Línea original del registro
     * @return Línea procesada sin elementos innecesarios
     */
    public static String limpiarLinea(String linea) {
        // Eliminar corchetes iniciales y su contenido
        if(linea.contains("LauncherAppRenderer.cpp")) {
        	linea="";
        }
        if(linea.contains("ControllerInterface.cpp")) {
        	linea="";
        }
        if(linea.contains("LauncherController.cpp")) {
        	linea="";
        } 
        if(linea.contains("NetQueue.cpp")) {
        	linea="";
        } 
        if(linea.contains("KeyMaker.cpp")) {
        	linea="";
        } 
        if(linea.contains("GamecoreClientApi_win32.cpp")) {
        	linea="";
        } 
        if(linea.contains("Sha1Cacher.cpp")) {
        	linea="";
        } 
        
        if(linea.contains("Updating.cpp")) {
        	linea="";
        } 
        
        if(linea.contains("Common.cpp")) {
        	linea="";
        } 
        if(linea.contains("mainWindows.cpp")) {
        	linea="";
        } 
        if(linea.contains("AzureUpdateTools.cpp")) {
        	linea="";
        } 
        if(linea.contains("CoreUpdateSystemCheck.cpp")) {
        	linea="";
        } 
        if(linea.contains("CurlNetQueue.cpp")) {
        	linea="";
        } 
        
        if(linea.contains("TreatmentTagFetcher.cpp")) {
        	linea="";
        } 
        
        
        if(linea.contains("main.cpp")) {
        	linea="";
        } 
        
        if(linea.contains("StartLibrary.cpp")) {
        	linea="";
        } 
        
        
        if(linea.contains("PreferencesManager.cpp")) {
        	linea="";
        } 
        
        
        if(linea.contains("SkinManager.cpp")) {
        	linea="";
        } 
        
        if(linea.contains("CefUI.cpp")) {
        	linea="";
        } 
        if(linea.contains("main_context.cpp")) {
        	linea="";
        } 
        
        
        
        if(linea.contains("root_window_manager.cpp")) {
        	linea="";
        } 
        
        if(linea.contains("client_handler.cpp")) {
        	linea="";
        } 
        if(linea.contains("JreManifestManager.cpp")) {
        	linea="";
        } 
        
        
        
        
        
        if(linea.contains("PersistentReflectedStruct.h")) {
        	linea="";
        } 
        if(linea.contains("GameVersionManager.cpp")) {
        	linea="";
        } 
        if(linea.contains("Xal.lib")) {
        	linea="";
        } 
        if(linea.contains("UserIdentity.cpp")) {
        	linea="";
        } 
        if(linea.contains("MSIPatchInstaller.cpp")) {
        	linea="";
        } 
        if(linea.contains("KeyMakerCache.cpp")) {
        	linea="";
        } 
        if(linea.contains("UpdateChecking.cpp")) {
        	linea="";
        } 
        if(linea.contains("LauncherMain.cpp")) {
        	linea="";
        } 
        if(linea.contains("SettingsManager.cpp")) {
        	linea="";
        } 
        
        
        

    	
    	
        if(linea.contains("Common_p.h")) {
        	linea="";
        } 
        
        
        if(linea.contains("XalStorage.cpp")) {
        	linea="";
        } 
        if(linea.contains("XalApi.cpp")) {
        	linea="";
        } 
        if(linea.contains("XalContext.cpp")) {
        	linea="";
        } 
        if(linea.contains("SentryAPI.cpp")) {
        	linea="";
        } 
        if(linea.contains("CoreFileUtils.cpp")) {
        	linea="";
        } 
        if(linea.contains("ProductInstance.cpp")) {
        	linea="";
        } 
        if(linea.contains("UnifiedMSAAccounts.cpp")) {
        	linea="";
        } 
        if(linea.contains("TelemetryEventSink_1DS.cpp")) {
        	linea="";
        } 
        if(linea.contains("EntitlementsManager.cpp")) {
        	linea="";
        } 
        if(linea.contains("TelemetryEventSink.cpp")) {
        	linea="";
        } 
        if(linea.contains("PistonInstaller.cpp")) {
        	linea="";
        } 
        
        if(linea.contains("CefUI.cpp")) {
        	linea="";
        } 
        if(linea.contains("LauncherAppBrowser.cpp")) {
        	linea="";
        } 
        if(linea.contains("MinecraftServicesApi.cpp")) {
        	linea="";
        } 
        if(linea.contains("Auth.cpp")) {
        	linea="";
        } 
        
        
        
        
        
        
        
        
        
        
    	
    	
    	
    	
    	
    	
    	
    	if (linea.startsWith("[")) {
            int finCorchete = linea.indexOf(']');
            if (finCorchete != -1) {
                linea = linea.substring(finCorchete + 1);
            }
        }

        // Eliminar "Game/game () Info" y espacios previos
        linea = linea.replaceFirst("^\\s*Game/game \\(\\) Info", "");

        // Limpiar espacios y ajustar formato de stacktrace
        linea = linea.trim();
        if (linea.startsWith("at ")) {  // Verificar si es línea de stacktrace
            linea = " " + linea;        // Agregar espacio solo para "at"
        }
        
        if(linea.startsWith("Authorization:Bearer")) {
        	linea="";
        } 
        if(linea.startsWith("Method:")) {
        	linea="";
        } 
        if(linea.startsWith("Headers:")) {
        	linea="";
        } 
        
        if(linea.startsWith("Content-Type:")) {
        	linea="";
        } 
        
        if(linea.startsWith("Body:")) {
        	linea="";
        } 
        if(linea.startsWith("Response")) {
        	linea="";
        } 

        
        

        return linea;
    }

    /**
     * Procesa múltiples líneas de registro.
     * 
     * @param lineas Lista de líneas originales
     * @return Lista de líneas procesadas (sin líneas vacías)
     */
    public static List<String> limpiarLineas(Iterable<String> lineas) {
        List<String> resultado = new ArrayList<>();
        for (String linea : lineas) {
            String lineaProcesada = limpiarLinea(linea);
            String trim = lineaProcesada.trim();
            if (!trim.isEmpty()) {
                resultado.add(lineaProcesada);
            }
        }
        return resultado;
    }

	@Override
	public boolean predicado(Path archivo) {
		// TODO Auto-generated method stub
		return archivo.toString().endsWith("launcher_log.txt");
	}
}