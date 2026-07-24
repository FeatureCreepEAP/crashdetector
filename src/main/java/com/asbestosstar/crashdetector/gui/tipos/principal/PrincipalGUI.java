package com.asbestosstar.crashdetector.gui.tipos.principal;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.discord.ManagerDiscord;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.arbol.ArbolDeModsGUIHamu;
import com.asbestosstar.crashdetector.gui.tipos.bittorrent.BitTorrentGUIHolostarsEN;
import com.asbestosstar.crashdetector.gui.tipos.cdlauncher.CDLauncherGUI;
import com.asbestosstar.crashdetector.gui.tipos.cdlauncher.CDLauncherGUISaliorMoon;
import com.asbestosstar.crashdetector.gui.tipos.config.ConfigPanel;
import com.asbestosstar.crashdetector.gui.tipos.config.ConfigPanelEstiloTL;
import com.asbestosstar.crashdetector.gui.tipos.configmods.ConfigsModsGUIYunenoms;
import com.asbestosstar.crashdetector.gui.tipos.corpo.CorpoBase;
import com.asbestosstar.crashdetector.gui.tipos.corpo.CorpoSAO;
import com.asbestosstar.crashdetector.gui.tipos.correo.LectorDeCorreoNetscape;
import com.asbestosstar.crashdetector.gui.tipos.depmap.MapaDeDependenciasGUINimu;
import com.asbestosstar.crashdetector.gui.tipos.docs.LectadorDeDocumentosStudyJuche;
import com.asbestosstar.crashdetector.gui.tipos.generador_parches.GeneradorParchesJKT48V;
import com.asbestosstar.crashdetector.gui.tipos.grepr.BusquedaGUISaliorMoon;
import com.asbestosstar.crashdetector.gui.tipos.guard.GuardiaSketchyVT;
import com.asbestosstar.crashdetector.gui.tipos.heapdump.VisorHeapDumpIranFifa;
import com.asbestosstar.crashdetector.gui.tipos.historia.ClioOfficeGUI;
import com.asbestosstar.crashdetector.gui.tipos.ia.IAGUILuotianyi;
import com.asbestosstar.crashdetector.gui.tipos.jgit.JGitHubIzzy;
import com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasHoloTalk;
import com.asbestosstar.crashdetector.gui.tipos.mcreator.EscanerMCreatorGUIRosemiLoveLock;
import com.asbestosstar.crashdetector.gui.tipos.migrador.MigradorLegacySamekoSaba;
import com.asbestosstar.crashdetector.gui.tipos.mixins.MixinsGUIChiarru;
import com.asbestosstar.crashdetector.gui.tipos.modapi.CDModsEstiloTL;
import com.asbestosstar.crashdetector.gui.tipos.modapi.PanelAPIBase;
import com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador.NoRegistroDeLauncherVShojo;
import com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador.NoRegistroLanzadorGUI;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.PanelQuickFixDemonSlayers;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.TodosQuickFixesGUI;
import com.asbestosstar.crashdetector.gui.tipos.rendimiento.AdministradorDeRendimientoNightcore;
import com.asbestosstar.crashdetector.gui.tipos.scriptide.ScriptIDEGUINiwaJPlus;
import com.asbestosstar.crashdetector.lanzer.CDLauncher;
import com.asbestosstar.crashdetector.mapas.BiMap;

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

//	public ConfigColor colorFondo = ConfigColor.de("gui.principal.color.fondo",
//			Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
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

	public PanelAPIBase panelCDMods = TipoGUI.MOD_API_PANEL.obtenerGUIPredeterminado(CDModsEstiloTL.ID,
			() -> new CDModsEstiloTL());
	public CDLauncherGUI panelCDLauncher = TipoGUI.CDLAUNCHER.obtenerGUIPredeterminado(CDLauncherGUISaliorMoon.ID,
			() -> new CDLauncherGUISaliorMoon());

	public JButton botonVolver = new JButton(MonitorDePID.idioma.volver());
	public JButton botonConfiguracion;
	public JPanel contenidoPrincipal = new JPanel(new BorderLayout());

	public boolean modolanzer = false;

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
//		registrarBotonDeBarraLateralDerecha(TipoGUI.EDITOR_FIRMAS, EditorCodiceGUIIronMouse.ID,
//				() -> new EditorCodiceGUIIronMouse());
		registrarBotonDeBarraLateralDerecha(TipoGUI.CORPO, CorpoSAO.ID, () -> new CorpoSAO());
		registrarBotonDeBarraLateralDerecha(TipoGUI.TODOS_QUICKFIXES, PanelQuickFixDemonSlayers.ID,
				() -> new PanelQuickFixDemonSlayers());
		registrarBotonDeBarraLateralDerecha(TipoGUI.MIXINS, MixinsGUIChiarru.ID, () -> new MixinsGUIChiarru());
		registrarBotonDeBarraLateralDerecha(TipoGUI.DEPMAP, MapaDeDependenciasGUINimu.ID,
				() -> new MapaDeDependenciasGUINimu());

		registrarBotonDeBarraLateralDerecha(TipoGUI.GUARD, GuardiaSketchyVT.ID, () -> new GuardiaSketchyVT());
		registrarBotonDeBarraLateralDerecha(TipoGUI.DOCS, LectadorDeDocumentosStudyJuche.ID,
				() -> new LectadorDeDocumentosStudyJuche());
		registrarBotonDeBarraLateralDerecha(TipoGUI.IA, IAGUILuotianyi.ID, () -> new IAGUILuotianyi());
		registrarBotonDeBarraLateralDerecha(TipoGUI.JGIT_HUB, JGitHubIzzy.ID, () -> new JGitHubIzzy());
		registrarBotonDeBarraLateralDerecha(TipoGUI.ADMINISTRADOR_DE_RENDIMIENTO,
				AdministradorDeRendimientoNightcore.ID, () -> new AdministradorDeRendimientoNightcore());
		registrarBotonDeBarraLateralDerecha(TipoGUI.CONFIG_MODS, ConfigsModsGUIYunenoms.ID,
				() -> new ConfigsModsGUIYunenoms());
		registrarBotonDeBarraLateralDerecha(TipoGUI.MIGRADOR_LEGACY, MigradorLegacySamekoSaba.ID,
				() -> new MigradorLegacySamekoSaba());
		registrarBotonDeBarraLateralDerecha(TipoGUI.SCRIPT_IDE, ScriptIDEGUINiwaJPlus.ID,
				() -> new ScriptIDEGUINiwaJPlus());
		registrarBotonDeBarraLateralDerecha(TipoGUI.VISOR_HEAP_DUMP, VisorHeapDumpIranFifa.ID,
				() -> new VisorHeapDumpIranFifa());

		registrarBotonDeBarraLateralDerecha(TipoGUI.BITTORRENT, BitTorrentGUIHolostarsEN.ID,
				() -> new BitTorrentGUIHolostarsEN());
		registrarBotonDeBarraLateralDerecha(TipoGUI.GENERADOR_DE_PARCHES, GeneradorParchesJKT48V.ID,
				() -> new GeneradorParchesJKT48V());
		
		registrarBotonDeBarraLateralDerecha(TipoGUI.LECTOR_CORREO, LectorDeCorreoNetscape.ID,
				() -> new LectorDeCorreoNetscape());	
		
		
		

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
		MonitorDePID.gui_principal_activo = this;
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

		if (!Buscador.hablicar.obtener() && boton.requireBuscador()) {
			CrashDetectorLogger.log("No se puede registrar el botón " + boton.id()
					+ " porque requiere el buscador, que está deshabilitado.");
			return;
		}

		if (!CorpoBase.obtenerMostrarBotonIAPrincipal() && boton == TipoGUI.IA) {
			CrashDetectorLogger.log("No se puede registrar el botón " + boton.id()
					+ " porque IA está deshabilitada en la configuración corporativa.");
			return;
		}

		botons_de_barra_lateral_derecha.put(boton, predeterminado, gui_predeterminado);
	}

	/**
	 * Inicializa la interfaz base sin layout ni apariencia específica. La
	 * apariencia se aplica en la implementación concreta.
	 */
	public abstract void inicializarInterfaz();

	public void volver() {
		contenidoPrincipal.removeAll();
		contenidoPrincipal.add(scrollPane, BorderLayout.CENTER);
		contenidoPrincipal.revalidate();
		contenidoPrincipal.repaint();
		botonVolver.setEnabled(false);

		// Volver siempre al modo Analizador
		cambiarAModoAnalizador();
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
		NoRegistroLanzadorGUI gui = TipoGUI.NO_REGISTRO_LANZER.obtenerGUIPredeterminado(NoRegistroDeLauncherVShojo.ID,
				() -> new NoRegistroDeLauncherVShojo());

		gui.preparar(this, tiempoFallo);
		gui.init();

		recargar();
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

		// Ancho flexible para que la barra lateral pueda redimensionarse sin forzar
		// todos los botones a un ancho fijo exacto.
		boton.setMinimumSize(new Dimension(160, 40));
		boton.setPreferredSize(new Dimension(220, 40));
		boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

		// Con BoxLayout esto ayuda a que el botón pueda ocupar el ancho disponible.
		boton.setAlignmentX(Component.CENTER_ALIGNMENT);
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
		File directorio = Statics.CARPETA_DE_APP;
		if (directorio.exists() && directorio.isDirectory()) {
			try {
				Desktop.getDesktop().open(directorio);
			} catch (IOException e) {
				CrashDetectorLogger.logException(e);
			}
		}
	}

	public void botonCDLauncher(JButton botonCDLauncher) {

		if (this.modolanzer) {
			botonCDLauncher.setText(MonitorDePID.idioma.cancelar());
			CDLauncher.lanzer();
		} else {
			botonCDLauncher.setText(MonitorDePID.idioma.entrarAlJuego());
			this.panelCDLauncher.construir(this);
		}

	}

	public void mostrarVentanaQuickFix() {
		TodosQuickFixesGUI gui = TipoGUI.TODOS_QUICKFIXES.obtenerGUIPredeterminado(PanelQuickFixDemonSlayers.ID,
				() -> new PanelQuickFixDemonSlayers());

		gui.limpiar();

		for (QuickFix solucion : MonitorDePID.analizador.obtenerSoluciones()) {
			gui.agregarQuickFix(solucion);
		}

		gui.init();
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

	/**
	 * Cambia completamente al modo Analizador. La implementación concreta se
	 * encarga de refrescar la apariencia.
	 */
	public void cambiarAModoAnalizador() {
		modolanzer = false;
		aplicarColoresAnalizador();
		aplicarContenidoDeLaPantallaAnalizador();
		recargarApariencia();
		actualizarTextoBotonLauncherParaCDLauncher();
	}

	/**
	 * Cambia completamente al modo Lanzer. La implementación concreta se encarga de
	 * refrescar la apariencia.
	 */
	public void cambiarAModoLanzer() {
		modolanzer = true;
		aplicarColoresLanzer();
		aplicarContenidoDeLaPantallaLanzer();
		recargarApariencia();
	}

	public abstract void actualizarTextoBotonLauncherParaCDLauncher();

	/**
	 * Aplica la paleta de colores del Analizador (CrashDetector).
	 */
	public abstract void aplicarColoresAnalizador();

	/**
	 * Aplica la paleta de colores del modo Lanzer (CDLauncher).
	 */
	public abstract void aplicarColoresLanzer();

	/**
	 * Aplica el contenido de la pantalla del Analizador (CrashDetector).
	 */
	public abstract void aplicarContenidoDeLaPantallaAnalizador();

	/**
	 * Aplica el contenido de la pantalla del modo Lanzer (estilo TLauncher).
	 */
	public abstract void aplicarContenidoDeLaPantallaLanzer();

}