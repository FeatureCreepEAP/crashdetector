package com.asbestosstar.crashdetector.gui;

import com.asbestosstar.crashdetector.Statics;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.event.HyperlinkEvent;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrBase;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrSakuraRiddle;
import com.asbestosstar.crashdetector.gui.tipos.docs.LectadorDeDocumentosGUI;
import com.asbestosstar.crashdetector.gui.tipos.docs.LectadorDeDocumentosStudyJuche;
import com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasGUI;
import com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasHoloTalk;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.ElementoQuickFixDemonSlayers;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.QuickFixGUI;

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
		return Idioma.mapaParaComboBoxIdiomas();
	}

	public static String obtenerCodigoIdioma(String nombreIdioma) {
		return Idioma.codigoDesdeNombreVisible(nombreIdioma);
	}

	public default String nombreDeIdiomaDesdeCodigo(String codigoActual) {
		return Idioma.nombreDeIdiomaDesdeCodigo(codigoActual);
	}

	public static void abrirDirectorioEnExplorador() {
		File directorio = Statics.CARPETA_DE_APP;

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
			abrirURL(e.getDescription());
		}
	}

	public default void abrirURL(String url) {
		try {
			if (url != null && url.startsWith("lectador://")) {
				CrashDetectorLogger.log(url + " (lectador url)");
				LectadorDeConsolasGUI lectador = TipoGUI.LECTADOR_DE_CONSOLAS.obtenerGUIPredeterminado(
						LectadorDeConsolasHoloTalk.ID, () -> new LectadorDeConsolasHoloTalk());
				lectador.procesarHipervinculo(url);
			} else if (url.startsWith("cfr://")) {
				CrashDetectorLogger.log(url + " (cfr url)");
				CfrBase gui = TipoGUI.CFR.obtenerGUIPredeterminado(CfrSakuraRiddle.ID, CfrSakuraRiddle::new);
				gui.procesarHipervinculo(url);

			}

			else if (url.startsWith("quickfix://")) {
				CrashDetectorLogger.log(url + " (quickfix url)");
				QuickFixGUI gui = TipoGUI.QUICKFIX.obtenerGUIPredeterminado(ElementoQuickFixDemonSlayers.ID,
						ElementoQuickFixDemonSlayers::new);
				gui.constructir(MonitorDePID.analizador.obtenerQuickFixConEnlace(url));

			} else if (url.startsWith("docs://")) {
				CrashDetectorLogger.log(url + " (docs url)");
				LectadorDeDocumentosGUI gui = TipoGUI.DOCS.obtenerGUIPredeterminado(LectadorDeDocumentosStudyJuche.ID,
						() -> new LectadorDeDocumentosStudyJuche());
				gui.init();
				gui.setVisible(true);
				gui.procesarHipervinculo(url);
			}

			else if (url != null) {
				CrashDetectorLogger.log(url);
				Desktop.getDesktop().browse(new java.net.URI(url));
			}
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
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