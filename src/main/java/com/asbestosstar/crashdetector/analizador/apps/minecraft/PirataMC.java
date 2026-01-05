package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

public class PirataMC implements Verificaciones {

	public static ConfigBoolean config = ConfigBoolean.de("condenar_pirateria", false);
	public static Path archivo_derechos_piratas= Statics.carpeta.resolve("derechos_piratas.json");
	boolean activado = false;
	String mensaje ="";
	boolean completa=true;

	@Override
	public void verificar(Consola consola) {
		// TODO Auto-generated method stub
		
    	if(completa) {
    		return;
    	}
    	this.completa=true;
		
		
		if (config.obtener()) {
			if (consola.contenido_verificar
					.contains("Caused by: com.mojang.authlib.exceptions.MinecraftClientHttpException: Status: 401")) {
				activado = true;
				StringBuilder mensaj = new StringBuilder(MonitorDePID.idioma.mensagjePirataMC());
				String derechos =obtenerDerechosMiranda();
				if(derechos!=null) {
					mensaj.append(nl_html).append(MonitorDePID.idioma.infoDeDerechosMiranda()).append(nl_html).append(derechos);
				}
				mensaje=mensaj.toString();
			}
		}
	}

	public static String obtenerDerechosMiranda() {
	    if (!archivo_derechos_piratas.toFile().exists()) {
	        return null;
	    }

	    try {
	        String contenido = new String(Files.readAllBytes(archivo_derechos_piratas), java.nio.charset.StandardCharsets.UTF_8);
	        if (contenido == null || contenido.trim().isEmpty()) {
	            return null;
	        }

	        Nodo raiz = Json.leer(contenido);
	        if (!raiz.esObjeto()) {
	            return null;
	        }

	        String idioma_actual = MonitorDePID.idioma.codigo();
	        String idioma_respaldo = Idioma.idioma_respaldo.obtener();
	        String codigo_espanol = "es";

	        // Intentar en orden: actual → respaldo → español → null
	        String[] ordenDeBusqueda = { idioma_actual, idioma_respaldo, codigo_espanol };

	        for (String lang : ordenDeBusqueda) {
	            if (lang == null || lang.isEmpty()) continue;
	            Nodo nodo = raiz.obtener(lang);
	            if (nodo != null && !nodo.esObjeto() && !nodo.esArreglo()) {
	                String valor = nodo.comoCadena();
	                if (valor != null && !valor.trim().isEmpty()) {
	                    return valor;
	                }
	            }
	        }

	        return null;

	    } catch (IOException e) {
	        // Archivo no se pudo leer
	        return null;
	    } catch (Exception e) {
	        // JSON inválido o motor falló
	        return null;
	    }
	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new PirataMC();
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500;
	}

	@Override
	public String mensaje() {
		// TODO Auto-generated method stub
		return mensaje;
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombrePirataMC();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(MonitorDePID.idioma.nombrePirataMC())
				.agregarBoton(MonitorDePID.idioma.desactivarVerificacionPirata(), retener -> {
					PirataMC.config.escribir(true);
				}, true).agregarBoton(MonitorDePID.idioma.comprarMC(), retener -> {

					abrirEnNavegador("https://www.minecraft.net/es-mx/store/minecraft-java-bedrock-edition-pc?tabs=%7B%22details%22%3A0%7D");

				
				}, true).construir();
	}
	
	/**
	 * Abre una URL en el navegador predeterminado del sistema
	 * 
	 * @param url La dirección web a abrir
	 */
	private void abrirEnNavegador(String url) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				if (desktop.isSupported(Desktop.Action.BROWSE)) {
					desktop.browse(new URI(url));
				}
			}
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
		}
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return trazo.trace.contains("Caused by: com.mojang.authlib.exceptions.MinecraftClientHttpException: Status: 401");
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "pirata_minecraft";
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
	
	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"+this.getClass().getSimpleName()+".java";
	}
	
}
