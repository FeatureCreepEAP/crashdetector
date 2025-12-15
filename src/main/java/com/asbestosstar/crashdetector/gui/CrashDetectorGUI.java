package com.asbestosstar.crashdetector.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.event.HyperlinkEvent;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrBase;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrSakuraRiddle;
import com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasGUI;
import com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasHoloTalk;

public interface CrashDetectorGUI {

	/**
	 * Multimap con ID de TIPO, no ID de GUI individual pero el tipo con un etrar de
	 * Mapa con la ID de la GUI y un Supplier con la GUI
	 */
	// public static MultiMap<String,Map.Entry<String,Supplier<CrashDetectorGUI>>>
	// GUIS = new MultiMap<>();

	/**
	 * Una ID para la GUI
	 * 
	 * @return
	 */
	public String id();

	/**
	 * Tipo de GUI
	 * 
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

	/**
	 * Si un usario puede editar la aparencia en la GUI
	 * 
	 * @return
	 */
	public default boolean puedeEditarApariencia() {
		return true;
	}

	public default LinkedHashMap<String, String> mapaParaComboBoxIdiomas() {
		LinkedHashMap<String, String> idiomas = new java.util.LinkedHashMap<>();
		idiomas.put("Español", "imagenes/bandera_mexico.png");
		idiomas.put("English", "imagenes/bandera_inglaterra.png");
		idiomas.put("العربية", "imagenes/bandera_arabia.png");
		idiomas.put("Português", "imagenes/bandera_brasil.png");
		idiomas.put("فارسی", "imagenes/bandera_iran.png");
		idiomas.put("Русский", "imagenes/bandera_rusia.png");
		idiomas.put("简体中文", "imagenes/bandera_china.png");
		idiomas.put("Esperanto", "imagenes/bandera_esperanto.png");
		idiomas.put("日本語", "imagenes/bandera_japon.png");
		idiomas.put("한국어", "imagenes/bandera_corea.png");
		return idiomas;
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

	public default String nombreDeIdiomaDesdeCondigo(String codigoActual) {
		switch (codigoActual) {
		case "es":
			return "Español";
		case "en":
			return "English";
		case "ar":
			return "العربية";
		case "pt":
			return "Português";
		case "fa":
			return "فارسی";
		case "ru":
			return "Русский";
		case "zh":
			return "简体中文";
		case "eo":
			return "Esperanto";
		case "ja":
			return "日本語";
		case "ko":
			return "한국어";
		default:
			return "Español";
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

	public default void enlanceEvento(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			try {
				String url = e.getDescription();
				if (url != null && url.startsWith("lectador://")) {
					CrashDetectorLogger.log(url + " (lectador url)");
					LectadorDeConsolasGUI lectador = TipoGUI.LECTADOR_DE_CONSOLAS.obtenerGUIPredeterminado(
							LectadorDeConsolasHoloTalk.ID, () -> new LectadorDeConsolasHoloTalk());
					lectador.procesarHipervinculo(url);
				}else if (url.startsWith("cfr://")) {
                    CrashDetectorLogger.log(url + " (cfr url)");
                    CfrBase gui = TipoGUI.CFR.obtenerGUIPredeterminado(CfrSakuraRiddle.ID, CfrSakuraRiddle::new);
                    gui.procesarHipervinculo(url);
                }
				else if (url != null) {
					Desktop.getDesktop().browse(new java.net.URI(url));
				}
			} catch (Exception ex) {
				CrashDetectorLogger.logException(ex);
			}
		}
	}

	/**
	 * Initialisar GUI. No esta usada en PrincipalGUI
	 */
	public void init();

	/**
	 * Elementos de Configs para elementos incluyendo colores y imagenes y
	 * superpuesta
	 * 
	 * @return
	 */
	public List<ElementoConfig> obtenerElementosConfigs();

}