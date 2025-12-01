package com.asbestosstar.crashdetector.gui.tipos.principal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.asbestosstar.crashdetector.BiMap;
import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.discord.ManagerDiscord;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.modapi.CDModsEstiloTL;
import com.asbestosstar.crashdetector.gui.modapi.PanelAPIBase;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.arbol.ArbolDeModsGUIHamu;
import com.asbestosstar.crashdetector.gui.tipos.config.ConfigPanel;
import com.asbestosstar.crashdetector.gui.tipos.config.ConfigPanelEstiloTL;
import com.asbestosstar.crashdetector.gui.tipos.editor.EditorCodiceGUIIronMouse;
import com.asbestosstar.crashdetector.gui.tipos.grepr.BusquedaGUISaliorMoon;
import com.asbestosstar.crashdetector.gui.tipos.historia.ClioOfficeGUI;
import com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasHoloTalk;
import com.asbestosstar.crashdetector.gui.tipos.mcreator.EscanerMCreatorGUIRosemiLoveLock;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.PanelQuickFixDemonSlayers;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.TodosQuickFixesGUI;

/**
 * Base técnica: - Crea y configura el visor (JEditorPane/JScrollPane) y
 * listeners. - NO asume layout; delega toda la construcción visual a
 * buildLayout(...). - NO contiene helpers/estilo; la subclase controla
 * apariencia en applyAppearance(). - NO reimplementa métodos del interface (usa
 * los defaults de CrashDetectorGUI).
 */
public abstract class PrincipalGUI extends JFrame implements CrashDetectorGUI {

	public static Map<String, Supplier<PrincipalGUI>> GUIS = new HashMap<>();
	public Instant tiempoFallo;

	public ConfigColor colorFondo = ConfigColor.de("gui.principal.color.fondo",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
	public ConfigColor colorTexto = ConfigColor.de("gui.principal.color.texto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
	public ConfigColor colorBoton = ConfigColor.de("gui.principal.color.boton",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
	public ConfigColor colorCajaTexto = ConfigColor.de("gui.principal.color.cajaTexto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
	public ConfigColor colorEnlace = ConfigColor.de("gui.principal.color.enlace",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace()));

	public CountDownLatch cerrojo;

	// Componentes públicos para que la implementación concreta los estilice
	public JEditorPane pantalla = new JEditorPane();
	public JScrollPane scrollPane = new JScrollPane(pantalla);
	public ConfigPanel<PrincipalGUI> panelConfiguracion = TipoGUI.CONFIG_PANEL
			.obtenerGUIPredeterminado(ConfigPanelEstiloTL.ID, () -> new ConfigPanelEstiloTL());
	
	public PanelAPIBase panelCDMods = TipoGUI.MOD_API_PANEL
			.obtenerGUIPredeterminado(CDModsEstiloTL.ID, () -> new CDModsEstiloTL());
	
	
	
	public JButton botonVolver = new JButton(MonitorDePID.idioma.volver());
	public JButton botonConfiguracion;
	public JPanel contenidoPrincipal = new JPanel(new BorderLayout());

	/**
	 * Botons de la barra lateral
	 */
	public static BiMap<TipoGUI<? extends BotonDeBarraLateralDerecha>, String, Supplier<BotonDeBarraLateralDerecha>> botons_de_barra_lateral_derecha = new BiMap<>();

	/**
	 * para botones initalizado
	 */
	public Map<BotonDeBarraLateralDerecha, JButton> botons_de_barra_lateral_derecha_initalizado = new HashMap<>();

	static {
		CrashDetectorLogger.log("Principal Static");
		registrarBotonDeBarraLateralDerecha(TipoGUI.GREPR, BusquedaGUISaliorMoon.ID, () -> new BusquedaGUISaliorMoon());
		registrarBotonDeBarraLateralDerecha(TipoGUI.ESCANER_MCREATOR, EscanerMCreatorGUIRosemiLoveLock.ID,
				() -> new EscanerMCreatorGUIRosemiLoveLock());
		registrarBotonDeBarraLateralDerecha(TipoGUI.HISTORIA_DE_MODS, ClioOfficeGUI.ID, () -> new ClioOfficeGUI());
		registrarBotonDeBarraLateralDerecha(TipoGUI.ARBOL_DE_MODS, ArbolDeModsGUIHamu.ID,
				() -> new ArbolDeModsGUIHamu());
		registrarBotonDeBarraLateralDerecha(TipoGUI.LECTADOR_DE_CONSOLAS, LectadorDeConsolasHoloTalk.ID,
				() -> new LectadorDeConsolasHoloTalk());
		registrarBotonDeBarraLateralDerecha(TipoGUI.EDITOR_FIRMAS, EditorCodiceGUIIronMouse.ID,
				() -> new EditorCodiceGUIIronMouse());
	}

	public PrincipalGUI() {
		// Constructor vacío según los requisitos
		// No debe contener código
	}

	public void constructir(Instant utc, CountDownLatch latch) {
		this.tiempoFallo = utc;
		this.cerrojo = latch;
		// Inicializar los colores de configuración con valores por defecto
		// Serán sobrescritos por la implementación concreta en init()
		ManagerDiscord.comenzar();
		CrashDetectorLogger.log("en constructir");
		inicializarInterfaz();
		this.setVisible(true);
	}

	/**
	 * Registrar Botons de la barra lateral
	 */
	public static void registrarBotonDeBarraLateralDerecha(TipoGUI<? extends BotonDeBarraLateralDerecha> boton,
			String predeterminado, Supplier<BotonDeBarraLateralDerecha> gui_predeterminado) {
		botons_de_barra_lateral_derecha.put(boton, predeterminado, gui_predeterminado);
	}

	/**
	 * Inicializa la interfaz base sin layout ni apariencia específica. La
	 * apariencia se aplica en la implementación concreta.
	 */
	public abstract void inicializarInterfaz();

	public void volver() {
		// TODO Auto-generated method stub
		contenidoPrincipal.removeAll();
		contenidoPrincipal.add(scrollPane, BorderLayout.CENTER);
		contenidoPrincipal.revalidate();
		contenidoPrincipal.repaint();
		botonVolver.setEnabled(false);
	}

	public String obtenerCodigoIdioma(String nombreIdioma) {
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

	public void anadirRegistro() {
		// TODO Auto-generated method stub
		JFileChooser selectorArchivo = new JFileChooser();
		selectorArchivo.setDialogTitle("Seleccione un archivo");
		int resultado = selectorArchivo.showOpenDialog(null);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			File archivoSeleccionado = selectorArchivo.getSelectedFile();
			try {
				Consola cons = new Consola(archivoSeleccionado.toPath());
				cons.finalizarContenido(tiempoFallo, true);
				MonitorDePID.consolas.add(cons);
				recargar();
			} catch (IOException e) {
				// Registramos la excepción
				CrashDetectorLogger.logException(e);
				// Mostramos un mensaje de error al usuario
				JOptionPane.showMessageDialog(null, "Error al abrir el archivo: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public JButton añadirBotonEmoji(JPanel panel, String emoji, String tooltip) {
		JButton boton = new JButton(emoji);
		boton.setToolTipText(tooltip);
		boton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
		boton.setRolloverEnabled(true);
		boton.setContentAreaFilled(false);
		boton.setBorderPainted(false);
		boton.setFocusPainted(false);
		boton.setPreferredSize(new Dimension(40, 40));
		boton.setMaximumSize(new Dimension(40, 40));
		boton.setMinimumSize(new Dimension(40, 40));
		boton.setForeground(colorTexto.obtener());
		boton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				boton.setBackground(colorBoton.obtener().brighter());
				boton.setForeground(Color.BLACK);
			}

			public void mouseExited(MouseEvent evt) {
				boton.setBackground(colorFondo.obtener());
				boton.setForeground(colorTexto.obtener());
			}
		});
		panel.add(boton);
		return boton;
	}

	public void añadirBotonBarraLateral(JPanel panel, String texto) {
		JButton boton = new JButton(texto);
		boton.setBackground(colorBoton.obtener().darker());
		boton.setForeground(colorTexto.obtener());
		boton.setFont(boton.getFont().deriveFont(Font.BOLD, 14f));
		boton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		boton.setMargin(new Insets(10, 20, 10, 20));
		boton.setMaximumSize(new Dimension(130, 40));
		boton.setMinimumSize(new Dimension(130, 40));
		boton.setPreferredSize(new Dimension(130, 40));
		boton.setContentAreaFilled(true);
		boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		panel.add(boton);
	}

	public void estilizarBoton(JButton boton) {
		if (!CrashDetectorGUI.esMac()) {
			boton.setAlignmentX(Component.CENTER_ALIGNMENT);
			boton.setBackground(colorBoton.obtener());
			boton.setContentAreaFilled(true);
			boton.setForeground(colorTexto.obtener());
			boton.setFocusPainted(false);
			boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		} else {
			boton.setContentAreaFilled(false);
		}
		boton.setMaximumSize(new Dimension(250, 40));
		boton.setMinimumSize(new Dimension(250, 40));
		boton.setPreferredSize(new Dimension(250, 40));
	}

	public void recargar() {
		MonitorDePID.recargar(false, null);
		// this.inicializarInterfaz();
		try {
			pantalla.setText(new String(Files.readAllBytes(Paths.get(MonitorDePID.local))));
			pantalla.setCaretPosition(0);// Mas alto
			for (Entry<BotonDeBarraLateralDerecha, JButton> entry : botons_de_barra_lateral_derecha_initalizado
					.entrySet()) {
				entry.getValue().setText(entry.getKey().tipo().etiquetaDelBoton());
			}
			botonVolver.setText(MonitorDePID.idioma.volver());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public JButton añadirBotonImagen(JPanel panel, String imagePath, String tooltip) {
		JButton boton = new JButton();
		boton.setToolTipText(tooltip);
		ImageIcon originalIcon = new ImageIcon(imagePath);
		Image image = originalIcon.getImage();
		int BUTTON_SIZE = 40;
		Image scaledImage = image.getScaledInstance(BUTTON_SIZE - 10, BUTTON_SIZE - 10, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(scaledImage);
		boton.setIcon(icon);
		boton.setText("");
		boton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
		boton.setBackground(colorFondo.obtener());
		boton.setBorder(BorderFactory.createLineBorder(colorFondo.obtener(), 1));
		boton.setFocusPainted(false);
		boton.setMargin(new Insets(0, 0, 0, 0)); // Reduced margin
		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				boton.setBackground(colorBoton.obtener().brighter());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				boton.setBackground(colorFondo.obtener());
			}
		});
		panel.add(boton);
		return boton;
	}

	public void mostrarVentanaQuickFix() {
		// Crear el diálogo emergente
		JDialog dialogo = new JDialog(this, "QuickFix", true);
		dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialogo.setSize(600, 400);
		dialogo.setLocationRelativeTo(this);
		// Crear el panel QuickFix
		JPanel panelQuickFix = new JPanel(new BorderLayout());
		if (!CrashDetectorGUI.esMac()) {
			panelQuickFix.setBackground(colorFondo.obtener());
		}
		// Crear el scrollable QuickFix panel
		TodosQuickFixesGUI panelContenido = TipoGUI.TODOS_QUICKFIXES
				.obtenerGUIPredeterminado(PanelQuickFixDemonSlayers.ID, () -> {
					return new PanelQuickFixDemonSlayers();
				});
		for (QuickFix solucion : MonitorDePID.analizador.obtenerSoluciones()) {
			panelContenido.agregarQuickFix(solucion);
		}
		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelInferior.setBackground(colorFondo.obtener());
		JButton botonCerrar = new JButton(MonitorDePID.idioma.volver());
		estilizarBoton(botonCerrar);
		botonCerrar.addActionListener(e -> dialogo.dispose());
		panelInferior.add(botonCerrar);
		panelQuickFix.add(panelContenido, BorderLayout.CENTER);
		panelQuickFix.add(panelInferior, BorderLayout.SOUTH);
		dialogo.getContentPane().add(panelQuickFix);
		dialogo.setVisible(true);
	}

	@Override
	public void dispose() {
		super.dispose();
		MonitorDePID.fin(cerrojo);
	}

	@Override
	public TipoGUI<PrincipalGUI> tipo() {
		// TODO Auto-generated method stub
		return TipoGUI.PRINCIPAL;
	}

	@Override
	public abstract void init();
}