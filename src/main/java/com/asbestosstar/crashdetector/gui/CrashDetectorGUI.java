package com.asbestosstar.crashdetector.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.MultiMap;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public interface CrashDetectorGUI {

	/**
	 * Multimap con ID de TIPO, no ID de GUI individual pero el tipo con un etrar de Mapa con la ID de la GUI y un Supplier con la GUI
	 */
	//public static MultiMap<String,Map.Entry<String,Supplier<CrashDetectorGUI>>> GUIS = new MultiMap<>();
	
	/**
	 * Una ID para la GUI
	 * @return
	 */
	public String id();
	
	/**
	 * Tipo de GUI
	 * @return
	 */
	public TipoGUI tipo();
	
	/**
	 * Solo para actualizando aparencia, no para contenido
	 */
	public void recargarApariencia();

	/**
	 * Completa recargar
	 */
	public default void recargar() {
		MonitorDePID.recargar(false, null);
		recargarApariencia();
	}

	
	
	



	public static String obtenerCodigoIdioma(String nombreIdioma) {
		switch (nombreIdioma) {
		case "Español":
			return "es";
		case "English":
			return "en";
		case "العربية":
			return "ar";
		case "Português":
			return "pt";
		case "فارسی":
			return "fa";
		case "Русский":
			return "ru";
		case "简体中文":
			return "zh";
		case "Esperanto":
			return "eo";
		case "日本語":
			return "ja";
		case "한국어":
			return "ko";
		default:
			return "es";
		}
	}









	public static void abrirDirectorioEnExplorador() {
		File directorio = new File(System.getProperty("user.dir"));

		if (directorio.exists() && directorio.isDirectory()) {
			try {
				Desktop.getDesktop().open(directorio);
			} catch (IOException e) {
				CrashDetectorLogger.logException(e);
			}
		}
	}






	public static boolean esMac() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
}