package com.asbestosstar.crashdetectormc;

import java.util.Locale;

import com.asbestosstar.crashdetectormc.idioma.Arabe;
import com.asbestosstar.crashdetectormc.idioma.Chino;
import com.asbestosstar.crashdetectormc.idioma.Coreano;
import com.asbestosstar.crashdetectormc.idioma.Espanol;
import com.asbestosstar.crashdetectormc.idioma.Esperanto;
import com.asbestosstar.crashdetectormc.idioma.Ingles;
import com.asbestosstar.crashdetectormc.idioma.Japones;
import com.asbestosstar.crashdetectormc.idioma.Persa;
import com.asbestosstar.crashdetectormc.idioma.Portuges;
import com.asbestosstar.crashdetectormc.idioma.Ruso;

public interface Idioma {

	public static Idioma espanol = new Espanol();
	public static Idioma ingles = new Ingles();
	public static Idioma arabe = new Arabe();
	public static Idioma portuges = new Portuges();
	public static Idioma persa = new Persa();
	public static Idioma ruso = new Ruso();
	public static Idioma chino = new Chino();
	public static Idioma esperanto = new Esperanto();
	public static Idioma japones = new Japones();
	public static Idioma coreano = new Coreano();


	    public static Idioma detectar() {
	        String id = Locale.getDefault().getLanguage();
	        switch (id.toLowerCase()) {
	            case "es":
	                return espanol;
	            case "en":
	                return ingles;
	            case "ar":
	                return arabe;
	            case "pt": 
	                return portuges;
	            case "fa":
	                return persa;
	            case "ru":
	                return ruso;
	            case "zh":
	                return chino;
	            case "eo":
	                return esperanto;
	            case "ja":
	                return japones;
	            case "ko":
	                return coreano;
	            default:
	                return espanol;
	        }
	    }	    
	    
	    /**
	     * no es una carpeta de mods valida
	     * @return
	     */
	public String carpeta_de_mods_no_valido();
	
	/**
	 * No se donde esta el archivo JAR de CrashDetector
	 * @return
	 */
	public String no_se_donde_esta_jar();
	
	/**
	 * "Buscando para PID: " + pid
	 * @param pid
	 * @return
	 */
	public String buscando_para_pid(long pid);
	
	/**
	 * 	"(PID: " + pid + ") esta muerto!"
	 * @param pid
	 * @return
	 */
	public String pid_esta_muerto(long pid);

	/**
	 * No tenemos JVM
	 * @return
	 */
	public String no_tenemos_jvm();
	
	
	
    
    public String probelma_con_graficas_ati();
    
    public String probelma_con_graficas_nouveau();
    
    
    public String probelma_con_graficas_general();
    
    
    public String fmlEarlyWindow();
    
    
    public String no_tienes_las_dependencias_necesitas();
    
    
    public String linea_de_dependencia(String linea);
	
}
