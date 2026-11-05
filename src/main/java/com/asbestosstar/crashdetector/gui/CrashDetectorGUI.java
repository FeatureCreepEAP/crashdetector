package com.asbestosstar.crashdetector.gui;

import com.asbestosstar.crashdetector.Statics;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.JTableHeader;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.SwingUtilities;
import java.awt.Component;

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

	/**
	 * Estila un botón con los colores personalizados.
	 * Si el Look and Feel no acepta colores personalizados (por ejemplo GTK3),
	 * utiliza los colores nativos para evitar conflictos de contraste.
	 * 
	 * @param boton       el botón a estilizar
	 * @param customBg    el color de fondo personalizado
	 * @param customFg    el color de texto personalizado
	 */
	public static void estilizarBoton(JButton boton, Color customBg, Color customFg) {
		if (esLookAndFeelSinColoresPersonalizados()) {
			estilizarBotonNativo(boton);
			return;
		}
		boton.setBackground(customBg);
		boton.setForeground(customFg);
	}

	/**
	 * Determina si el Look and Feel activo no respeta los colores personalizados
	 * establecidos mediante setBackground/setForeground, por lo que se deben usar
	 * los colores nativos para mantener el contraste correcto.
	 */
	static boolean esLookAndFeelSinColoresPersonalizados() {
		try {
			String lnf = UIManager.getLookAndFeel().getClass().getName().toLowerCase(java.util.Locale.ROOT);
			// GTK3 no respeta colores personalizados en botones
			return lnf.contains(".gtk.") || lnf.contains("gtklookandfeel");
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Estila un botón para que use los colores nativos del Look and Feel.
	 * 
	 * @param boton el botón a estilizar
	 */
	public static void estilizarBotonNativo(JButton boton) {
		Color nativeBg = UIManager.getColor("Button.background");
		Color nativeFg = UIManager.getColor("Button.foreground");
		if (nativeBg != null) {
			boton.setBackground(nativeBg);
		}
		if (nativeFg != null) {
			boton.setForeground(nativeFg);
		}
	}

	/**
	 * Estila un botón de icono sin fondo visible. El botón muestra solo el icono
	 * con el color de texto personalizado, sin área de contenido ni borde.
	 * 
	 * @param boton   el botón a estilizar
	 * @param customFg el color de texto/icono personalizado
	 */
	public static void estilizarBotonIcono(JButton boton, Color customFg) {
		boton.setContentAreaFilled(false);
		boton.setBorderPainted(false);
		boton.setFocusPainted(false);
		boton.setOpaque(false);
		boton.setForeground(customFg);
	}

	/**
	 * Estila una tabla con los colores personalizados.
	 * Si el Look and Feel no acepta colores personalizados (por ejemplo GTK3),
	 * utiliza los colores nativos para evitar conflictos de contraste.
	 * 
	 * @param tabla           la tabla a estilizar
	 * @param customBg        el color de fondo personalizado
	 * @param customFg        el color de texto personalizado
	 * @param customSelection el color de selección personalizado
	 * @param customSelectionFg el color de texto de selección personalizado
	 */
	public static void estilizarTabla(JTable tabla, Color customBg, Color customFg,
			Color customSelection, Color customSelectionFg) {
		if (tabla == null) {
			return;
		}
		if (esLookAndFeelSinColoresPersonalizados()) {
			Color nativeBg = UIManager.getColor("Table.background");
			Color nativeFg = UIManager.getColor("Table.foreground");
			Color nativeSelBg = UIManager.getColor("Table.selectionBackground");
			Color nativeSelFg = UIManager.getColor("Table.selectionForeground");
			if (nativeBg != null) {
				tabla.setBackground(nativeBg);
			}
			if (nativeFg != null) {
				tabla.setForeground(nativeFg);
			}
			if (nativeSelBg != null) {
				tabla.setSelectionBackground(nativeSelBg);
			}
			if (nativeSelFg != null) {
				tabla.setSelectionForeground(nativeSelFg);
			}
		} else {
			tabla.setBackground(customBg);
			tabla.setForeground(customFg);
			tabla.setSelectionBackground(customSelection);
			tabla.setSelectionForeground(customSelectionFg);
		}
	}

	/**
	 * Estila el encabezado de una tabla con los colores personalizados.
	 * Si el Look and Feel no acepta colores personalizados (por ejemplo GTK3),
	 * utiliza los colores nativos para evitar conflictos de contraste.
	 * 
	 * @param header el encabezado de tabla a estilizar
	 * @param customBg el color de fondo personalizado
	 * @param customFg el color de texto personalizado
	 */
	public static void estilizarEncabezadoTabla(JTableHeader header, Color customBg, Color customFg) {
		if (header == null) {
			return;
		}
		if (esLookAndFeelSinColoresPersonalizados()) {
			Color nativeBg = UIManager.getColor("TableHeader.background");
			Color nativeFg = UIManager.getColor("TableHeader.foreground");
			if (nativeBg != null) {
				header.setBackground(nativeBg);
			}
			if (nativeFg != null) {
				header.setForeground(nativeFg);
			}
		} else {
			header.setBackground(customBg);
			header.setForeground(customFg);
		}
	}

	/**
	 * Estila una JTabbedPane con los colores personalizados.
	 * Si el Look and Feel no acepta colores personalizados (por ejemplo GTK3),
	 * utiliza los colores nativos para evitar conflictos de contraste.
	 * 
	 * @param pestanas la pestaña a estilizar
	 * @param customBg el color de fondo personalizado
	 * @param customFg el color de texto personalizado
	 */
	public static void estilizarPestanas(JTabbedPane pestanas, Color customBg, Color customFg) {
		if (pestanas == null) {
			return;
		}
		Color fg;
		Color bg;
		if (esLookAndFeelSinColoresPersonalizados()) {
			Color nativeBg = UIManager.getColor("TabbedPane.background");
			Color nativeFg = UIManager.getColor("TabbedPane.foreground");
			bg = nativeBg != null ? nativeBg : customBg;
			fg = nativeFg != null ? nativeFg : customFg;
		} else {
			bg = customBg;
			fg = customFg;
		}
		pestanas.setBackground(bg);
		pestanas.setForeground(fg);
		try {
			if (pestanas.getTabCount() > 0) {
				pestanas.setBackgroundAt(-1, bg);
			}
		} catch (IndexOutOfBoundsException e) {
			// Some L&Fs (e.g., Motif on Solaris) don't support index -1 for the content area
		}
		for (int i = 0; i < pestanas.getTabCount(); i++) {
			Component comp = pestanas.getTabComponentAt(i);
			if (comp != null) {
				estilizarComponentePestania(comp, fg);
			} else {
				try {
					pestanas.setBackgroundAt(i, bg);
				} catch (IndexOutOfBoundsException e) {
					// Some L&Fs don't support per-tab background setting
				}
			}
		}
	}

	static void estilizarComponentePestania(Component comp, Color fg) {
		if (comp instanceof JLabel) {
			((JLabel) comp).setForeground(fg);
		} else if (comp instanceof JPanel) {
			JPanel panel = (JPanel) comp;
			for (Component child : panel.getComponents()) {
				if (child instanceof JLabel) {
					((JLabel) child).setForeground(fg);
				}
			}
		} else if (comp instanceof JButton) {
			((JButton) comp).setForeground(fg);
		} else {
			comp.setForeground(fg);
		}
	}

	/**
	 * Estila un JList con los colores personalizados.
	 * Si el Look and Feel no acepta colores personalizados (por ejemplo GTK3),
	 * utiliza los colores nativos para evitar conflictos de contraste.
	 * 
	 * @param lista la lista a estilizar
	 * @param customBg el color de fondo personalizado
	 * @param customFg el color de texto personalizado
	 * @param customSelection el color de selección personalizado
	 */
	public static void estilizarLista(JList<?> lista, Color customBg, Color customFg, Color customSelection) {
		if (lista == null) {
			return;
		}
		if (esLookAndFeelSinColoresPersonalizados()) {
			Color nativeBg = UIManager.getColor("List.background");
			Color nativeFg = UIManager.getColor("List.foreground");
			Color nativeSelBg = UIManager.getColor("List.selectionBackground");
			Color nativeSelFg = UIManager.getColor("List.selectionForeground");
			if (nativeBg != null) {
				lista.setBackground(nativeBg);
			}
			if (nativeFg != null) {
				lista.setForeground(nativeFg);
			}
			if (nativeSelBg != null) {
				lista.setSelectionBackground(nativeSelBg);
			}
			if (nativeSelFg != null) {
				lista.setSelectionForeground(nativeSelFg);
			}
		} else {
			lista.setBackground(customBg);
			lista.setForeground(customFg);
			lista.setSelectionBackground(customSelection);
			lista.setSelectionForeground(customFg);
		}
	}

	/**
	 * Estila un JComboBox con los colores personalizados.
	 * Si el Look and Feel no acepta colores personalizados (por ejemplo GTK3),
	 * utiliza los colores nativos para evitar conflictos de contraste.
	 * 
	 * @param combo el combo box a estilizar
	 * @param customBg el color de fondo personalizado
	 * @param customFg el color de texto personalizado
	 */
	public static void estilizarComboBox(JComboBox<?> combo, Color customBg, Color customFg) {
		if (combo == null) {
			return;
		}
		if (esLookAndFeelSinColoresPersonalizados()) {
			Color nativeBg = UIManager.getColor("ComboBox.background");
			Color nativeFg = UIManager.getColor("ComboBox.foreground");
			if (nativeBg != null) {
				combo.setBackground(nativeBg);
			}
			if (nativeFg != null) {
				combo.setForeground(nativeFg);
			}
		} else {
			combo.setBackground(customBg);
			combo.setForeground(customFg);
		}
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