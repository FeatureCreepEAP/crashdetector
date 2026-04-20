package com.asbestosstar.crashdetector.gui.tipos.principal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.asbestosstar.crashdetector.App;
import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Entregar;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.elementos.ComboIdiomasConIcono;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.compartir.DialogoCompartir;
import com.asbestosstar.crashdetector.gui.tipos.compartir.DialogoCompartirLegacy;
import com.asbestosstar.crashdetector.mapas.BiMap;

public class PrincipalGUIEstiloLanzer extends PrincipalGUI {

	public static String ID = "estilo_lanzer";

	public ConfigColor analizadorColorFondo = ConfigColor.de("gui.principal.color.fondo",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));

	public ConfigColor analizadorColorTexto = ConfigColor.de("gui.principal.color.texto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));

	public ConfigColor analizadorColorBoton = ConfigColor.de("gui.principal.color.boton",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));

	public ConfigColor analizadorColorCajaTexto = ConfigColor.de("gui.principal.color.cajaTexto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));

	public ConfigColor analizadorColorEnlace = ConfigColor.de("gui.principal.color.enlace",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace()));

	public ConfigColor analizadorColorBotonBaraLateral = ConfigColor.de("gui.principal.color.boton.bara.lateral",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));

	// =====================================================
	// Colores por defecto
	// (simplemente apuntan al Analizador)
	// =====================================================

	public ConfigColor colorFondo = analizadorColorFondo;
	public ConfigColor colorTexto = analizadorColorTexto;
	public ConfigColor colorBoton = analizadorColorBoton;
	public ConfigColor colorCajaTexto = analizadorColorCajaTexto;
	public ConfigColor colorEnlace = analizadorColorEnlace;
	public ConfigColor colorBotonBaraLateral = analizadorColorBotonBaraLateral;

	// =====================================================
	// Colores del modo Launcher / Lanzer (estilo TLauncher)
	// Claves separadas
	// =====================================================

	// Fondo principal / barra lateral derecha (azul oscuro)
//	public ConfigColor lanzerColorFondo = ConfigColor.de("gui.principal.launcher.color.fondo", new Color(36, 96, 155));
	public ConfigColor lanzerColorFondo = ConfigColor.de("gui.principal.launcher.color.fondo",
			new Color(170, 210, 120));

	// Texto principal (blanco)
	public ConfigColor lanzerColorTexto = ConfigColor.de("gui.principal.launcher.color.texto",
			new Color(255, 255, 255));

	// Botón principal inferior “Entrar al juego” (amarillo)
	public ConfigColor lanzerColorBoton = ConfigColor.de("gui.principal.launcher.color.boton", new Color(246, 196, 64));

	// Barra inferior completa (verde claro)
	public ConfigColor lanzerColorCajaTexto = ConfigColor.de("gui.principal.launcher.color.cajaTexto",
			new Color(170, 210, 120));

	// Enlaces y acentos (azul claro)
	public ConfigColor lanzerColorEnlace = ConfigColor.de("gui.principal.launcher.color.enlace",
			new Color(80, 170, 230));

	// Botón de la barra lateral (estilo CDLauncher – azul claro tipo “Cómo jugar”)
	public ConfigColor lanzerColorBotonBaraLateral = ConfigColor.de("gui.principal.launcher.color.boton.bara.lateral",
			new Color(90, 170, 215));

	/**
	 * Color especial del botón QuickFix en la barra lateral. Por defecto usa el
	 * amarillo del estilo Launcher.
	 */
	public ConfigColor lanzerColorBotonQuickFix = ConfigColor.de("gui.principal.launcher.color.boton.quickfix",
			new Color(246, 196, 64));

	private JPanel panelBotonesDerechaRef;
	private JButton botonCDLauncherRef;
	private JPanel panelInferiorRef;
	private JPanel seccionConfiguracionRef;
	private JPanel barraLateralDerechaRef;
	private JLabel logoLabelRef;

	private JScrollPane scrollBarraLateralRef;
	private JSplitPane splitPrincipalRef;

	@Override
	public void init() {
		cambiarAModoAnalizador();
		// AHORA llamamos al init del padre
		this.setVisible(true);
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void inicializarInterfaz() {
		// Inicializar componentes con colores iniciales

		pantalla.setContentType("text/html");
		pantalla.setEditable(false);
		pantalla.setBackground(colorCajaTexto.obtener());
		pantalla.setForeground(colorEnlace.obtener());
		pantalla.setFont(new Font("Consolas", Font.PLAIN, 14));

		try {
			pantalla.setText(new String(Files.readAllBytes(Paths.get(MonitorDePID.local))));
			pantalla.setCaretPosition(0); // Ir al inicio
		} catch (IOException e) {
			pantalla.setText(
					"<html><body style='color:#ff6b6b'>Problema con el Informe: " + e.getMessage() + "</body></html>");
		}

		// Abrir hipervínculos (incluyendo protocolo interno lectador://)
		pantalla.addHyperlinkListener(e -> {
			this.enlanceEvento(e);
		});

		// Borde y color de fondo del scroll principal
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setBackground(colorCajaTexto.obtener());
		if (scrollPane.getVerticalScrollBar() != null) {
			scrollPane.getVerticalScrollBar().setValue(0);
		}

		// --- Panel inferior principal (configuración + botón QuickFix + botones de
		// acción)
		panelInferiorRef = new JPanel(new BorderLayout(5, 5));
		JPanel panelInferior = panelInferiorRef;
		panelInferior.setBackground(colorFondo.obtener());
		panelInferior.setBorder(new EmptyBorder(10, 20, 10, 20));

		// Sección de configuración
		seccionConfiguracionRef = new JPanel();
		JPanel seccionConfiguracion = seccionConfiguracionRef;
		seccionConfiguracion.setLayout(new BoxLayout(seccionConfiguracion, BoxLayout.Y_AXIS));
		seccionConfiguracion.setBackground(colorFondo.obtener());

		// Contenedor horizontal para ambos grupos de controles
		JPanel panelHorizontal = new JPanel();
		panelHorizontal.setLayout(new BoxLayout(panelHorizontal, BoxLayout.X_AXIS));
		panelHorizontal.setBackground(colorFondo.obtener());

		// Columna izquierda (idioma)
		JPanel columnaIzquierda = new JPanel();
		columnaIzquierda.setLayout(new BoxLayout(columnaIzquierda, BoxLayout.Y_AXIS));
		columnaIzquierda.setBackground(colorFondo.obtener());

		// Columna derecha (aplicación)
		JPanel columnaDerecha = new JPanel();
		columnaDerecha.setLayout(new BoxLayout(columnaDerecha, BoxLayout.Y_AXIS));
		columnaDerecha.setBackground(colorFondo.obtener());

		// --- Datos de idiomas con iconos
		LinkedHashMap<String, String> idiomas = Idioma.mapaParaComboBoxIdiomas();

		// --- Selector de idioma
		JComboBox<String> comboIdioma = new ComboIdiomasConIcono(idiomas);
		comboIdioma.setMaximumSize(new Dimension(200, 30));
		comboIdioma.setPreferredSize(new Dimension(200, 30));
		if (!CrashDetectorGUI.esMac()) {
			comboIdioma.setBackground(colorBoton.obtener());
			comboIdioma.setForeground(colorTexto.obtener());
		}

		String codigoActual = MonitorDePID.idioma.codigo();
		String nombreIdiomaActual = Idioma.nombreDeIdiomaDesdeCodigo(codigoActual);
		comboIdioma.setSelectedItem(nombreIdiomaActual);

		JCheckBox chkIdiomaSistema = new JCheckBox(MonitorDePID.idioma.usarIdiomaDelSistema());
		chkIdiomaSistema.setForeground(colorTexto.obtener());
		chkIdiomaSistema.setBackground(colorFondo.obtener());
		chkIdiomaSistema.setOpaque(false);
		chkIdiomaSistema.setHorizontalAlignment(SwingConstants.LEFT);
		chkIdiomaSistema.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		chkIdiomaSistema.setMaximumSize(new Dimension(200, 30));

		boolean existePreferencia = Files.exists(Idioma.archivo.toPath());
		boolean usarSistemaInicial = !existePreferencia;
		chkIdiomaSistema.setSelected(usarSistemaInicial);
		comboIdioma.setEnabled(!usarSistemaInicial);

		comboIdioma.addActionListener(e -> {
			chkIdiomaSistema.setSelected(false);
			comboIdioma.setEnabled(true);
			String seleccion = (String) comboIdioma.getSelectedItem();
			String codigo = Idioma.codigoDesdeNombreVisible(seleccion);
			if (codigo != null) {
				try {
					ConfigMundial.obtenerInstancia().guardarIdioma(codigo);
				} catch (Exception ex) {
					CrashDetectorLogger.logException(ex);
				}
				MonitorDePID.idioma = Idioma.detectar();
				recargar();
			}
		});

		chkIdiomaSistema.addActionListener(e -> {
			boolean usarSistema = chkIdiomaSistema.isSelected();
			comboIdioma.setEnabled(!usarSistema);
			if (usarSistema) {
				try {
					Files.deleteIfExists(Idioma.archivo.toPath());
					ConfigMundial.obtenerInstancia().borrarIdioma();
				} catch (IOException ex) {
					CrashDetectorLogger.logException(ex);
				}
				MonitorDePID.idioma = Idioma.detectar();
				recargar();
			} else {
				String seleccion = (String) comboIdioma.getSelectedItem();
				String codigo = Idioma.codigoDesdeNombreVisible(seleccion);
				if (codigo != null) {
					try {
						ConfigMundial.obtenerInstancia().guardarIdioma(codigo);
					} catch (Exception ex) {
						CrashDetectorLogger.logException(ex);
					}
					MonitorDePID.idioma = Idioma.detectar();
					recargar();
				}
			}
		});

		columnaIzquierda.add(comboIdioma);
		columnaIzquierda.add(chkIdiomaSistema);

		// --- Columna derecha: selector de aplicación + detectar automáticamente
		LinkedHashMap<String, App> etiquetasAApp = new LinkedHashMap<>();
		LinkedHashMap<String, String> etiquetasAIcono = new LinkedHashMap<>();

		java.util.function.Function<App, String> etiquetaParaApp = (a) -> {
			if (a == null || a.id() == null || a.id().trim().isEmpty())
				return "Minecraft";
			String id = a.id().trim();
			if ("minecraft".equalsIgnoreCase(id))
				return "Minecraft";
			if ("dangerzone".equalsIgnoreCase(id))
				return "DangerZone";
			if ("live2d_cubism".equalsIgnoreCase(id))
				return "Live2D Cubism";
			if ("unciv".equalsIgnoreCase(id))
				return "Unciv";
			if ("nxopen".equalsIgnoreCase(id))
				return "NX Open";
			if ("teamcenter".equalsIgnoreCase(id))
				return "Teamcenter";
			return Character.toUpperCase(id.charAt(0)) + id.substring(1);
		};

		java.util.function.Function<App, Boolean> appConIconoCD = (a) -> {
			if (a == null || a.id() == null)
				return false;
			String id = a.id().trim().toLowerCase();
			return id.equals("minecraft") || id.equals("dangerzone") || id.equals("live2d_cubism") || id.equals("unciv")
					|| id.equals("nxopen") || id.equals("teamcenter");
		};

		java.util.function.Function<App, App> normalizarApp = (a) -> {
			if (a == null)
				return App.MINECRAFT;
			String id = a.id();
			if (id == null || id.trim().isEmpty())
				return App.MINECRAFT;
			return a;
		};

		for (App a : App.APPS) {
			App norm = normalizarApp.apply(a);
			String etiqueta = etiquetaParaApp.apply(norm);
			if (!etiquetasAApp.containsKey(etiqueta)) {
				etiquetasAApp.put(etiqueta, norm);
				if (appConIconoCD.apply(norm)) {
					etiquetasAIcono.put(etiqueta, "imagenes/cd_chars.png");
				} else {
					etiquetasAIcono.put(etiqueta, null);
				}
			}
		}

		if (etiquetasAApp.isEmpty()) {
			etiquetasAApp.put("Minecraft", App.MINECRAFT);
			etiquetasAIcono.put("Minecraft", "imagenes/cd_chars.png");
		}

		JComboBox<String> comboAplicacion = new ComboIdiomasConIcono(etiquetasAIcono);
		comboAplicacion.setMaximumSize(new Dimension(200, 30));
		comboAplicacion.setPreferredSize(new Dimension(200, 30));
		if (!CrashDetectorGUI.esMac()) {
			comboAplicacion.setBackground(colorBoton.obtener());
			comboAplicacion.setForeground(colorTexto.obtener());
		}

		JCheckBox chkDetectarAuto = new JCheckBox("Detectar automáticamente");
		chkDetectarAuto.setForeground(colorTexto.obtener());
		chkDetectarAuto.setBackground(colorFondo.obtener());
		chkDetectarAuto.setOpaque(false);
		chkDetectarAuto.setHorizontalAlignment(SwingConstants.LEFT);
		chkDetectarAuto.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		chkDetectarAuto.setMaximumSize(new Dimension(200, 30));
		chkDetectarAuto.setSelected(true);

		comboAplicacion.setEnabled(false);

		App detectada = normalizarApp.apply(App.obtenerApp());
		Statics.APP = detectada;
		String etiquetaDetectada = etiquetaParaApp.apply(detectada);
		comboAplicacion.setSelectedItem(etiquetaDetectada);

		comboAplicacion.addActionListener(e -> {
			if (chkDetectarAuto.isSelected())
				return;
			String etiquetaSel = (String) comboAplicacion.getSelectedItem();
			App seleccionada = etiquetasAApp.get(etiquetaSel);
			seleccionada = normalizarApp.apply(seleccionada);
			Statics.APP = seleccionada;
			comboAplicacion.setSelectedItem(etiquetaParaApp.apply(seleccionada));
			recargar();
		});

		chkDetectarAuto.addActionListener(e -> {
			boolean auto = chkDetectarAuto.isSelected();
			comboAplicacion.setEnabled(!auto);
			if (auto) {
				App autodetect = normalizarApp.apply(Entregar.app_detecta);
				Statics.APP = autodetect;
				comboAplicacion.setSelectedItem(etiquetaParaApp.apply(autodetect));
				recargar();
			} else {
				String etiquetaSel = (String) comboAplicacion.getSelectedItem();
				App seleccionada = etiquetasAApp.get(etiquetaSel);
				seleccionada = normalizarApp.apply(seleccionada);
				Statics.APP = seleccionada;
				comboAplicacion.setSelectedItem(etiquetaParaApp.apply(seleccionada));
				recargar();
			}
		});

		columnaDerecha.add(comboAplicacion);
		columnaDerecha.add(chkDetectarAuto);

		panelHorizontal.add(columnaIzquierda);
		panelHorizontal.add(Box.createHorizontalStrut(20));
		panelHorizontal.add(columnaDerecha);
		seccionConfiguracion.add(panelHorizontal);

		final int ANCHO_CONTROLES = 220;
		final int ALTO_CONTROLES = 30;

		comboIdioma.setPreferredSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		comboIdioma.setMaximumSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		comboIdioma.setAlignmentX(Component.LEFT_ALIGNMENT);

		chkIdiomaSistema.setPreferredSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		chkIdiomaSistema.setMaximumSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		chkIdiomaSistema.setAlignmentX(Component.LEFT_ALIGNMENT);
		chkIdiomaSistema.setBorder(new EmptyBorder(6, 0, 0, 0));

		comboAplicacion.setPreferredSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		comboAplicacion.setMaximumSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		comboAplicacion.setAlignmentX(Component.LEFT_ALIGNMENT);

		chkDetectarAuto.setPreferredSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		chkDetectarAuto.setMaximumSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		chkDetectarAuto.setAlignmentX(Component.LEFT_ALIGNMENT);
		chkDetectarAuto.setBorder(new EmptyBorder(6, 0, 0, 0));

		columnaIzquierda.setAlignmentX(Component.LEFT_ALIGNMENT);
		columnaDerecha.setAlignmentX(Component.LEFT_ALIGNMENT);

		chkDetectarAuto.setSelected(true);

		// --- Botón central CDLauncher
		botonCDLauncherRef = new JButton("CDLauncher");
		JButton botonCDLauncher = botonCDLauncherRef;

		botonCDLauncher.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		botonCDLauncher.setMinimumSize(new Dimension(120, 30));
		botonCDLauncher.setMaximumSize(new Dimension(180, 40));
		botonCDLauncher.setPreferredSize(new Dimension(150, 30));
		if (CrashDetectorGUI.esMac()) {
			botonCDLauncher.setContentAreaFilled(false);
		} else {
			botonCDLauncher.setFont(botonCDLauncher.getFont().deriveFont(Font.BOLD, 16f));
			botonCDLauncher.setBackground(colorBoton.obtener());
			botonCDLauncher.setForeground(colorTexto.obtener());
			botonCDLauncher.setContentAreaFilled(true);
			botonCDLauncher.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		}
		botonCDLauncher.addActionListener(e -> botonCDLauncher(botonCDLauncher));

		// --- Botones de acción (derecha)
		panelBotonesDerechaRef = new JPanel(new GridLayout(1, 5, 10, 10));
		JPanel panelBotonesDerecha = panelBotonesDerechaRef;
		panelBotonesDerecha.setBackground(colorFondo.obtener());

		JButton boton_CDMods = añadirBotonImagen(panelBotonesDerecha,
				Statics.carpeta.resolve("imagenes/boton_cdmods.png").toString(), "CD Mods");
		JButton btnAgregar = añadirBotonImagen(panelBotonesDerecha,
				Statics.carpeta.resolve("imagenes/boton_agregar.png").toString(), MonitorDePID.idioma.anadirRegistro());
		JButton btnCompartir = añadirBotonImagen(panelBotonesDerecha,
				Statics.carpeta.resolve("imagenes/boton_compartir.png").toString(),
				MonitorDePID.idioma.botonDeCompartirInforme());
		JButton btnActualizar = añadirBotonImagen(panelBotonesDerecha,
				Statics.carpeta.resolve("imagenes/boton_actualizar.png").toString(), MonitorDePID.idioma.actualizar());
		JButton btnArchivos = añadirBotonImagen(panelBotonesDerecha,
				Statics.carpeta.resolve("imagenes/boton_archivos.png").toString(), MonitorDePID.idioma.abrirCarpeta());
		botonConfiguracion = añadirBotonImagen(panelBotonesDerecha,
				Statics.carpeta.resolve("imagenes/boton_config.png").toString(), MonitorDePID.idioma.config());

		DialogoCompartir comp = TipoGUI.DIALOGO_COMPARTIR.obtenerGUIPredeterminado(DialogoCompartirLegacy.ID,
				() -> new DialogoCompartirLegacy());

		btnCompartir.addActionListener(e -> comp.preperar(tiempoFallo));
		btnActualizar.addActionListener(e -> recargar());
		btnAgregar.addActionListener(e -> anadirRegistro());
		btnArchivos.addActionListener(e -> abrirDirectorioEnExplorador());

		botonConfiguracion.addActionListener(e -> {
			contenidoPrincipal.removeAll();
			panelConfiguracion.constructir(this);
			contenidoPrincipal.add(panelConfiguracion, BorderLayout.CENTER);
			contenidoPrincipal.revalidate();
			contenidoPrincipal.repaint();
			botonVolver.setEnabled(true);
		});

		boton_CDMods.addActionListener(e -> {
			contenidoPrincipal.removeAll();
			panelCDMods.establecerPrincipalGUI(this);
			panelCDMods.init();
			contenidoPrincipal.add(panelCDMods, BorderLayout.CENTER);
			contenidoPrincipal.revalidate();
			contenidoPrincipal.repaint();
			botonVolver.setEnabled(true);
		});

		panelInferior.add(seccionConfiguracion, BorderLayout.WEST);
		panelInferior.add(botonCDLauncher, BorderLayout.CENTER);
		panelInferior.add(panelBotonesDerecha, BorderLayout.EAST);

		// --- Barra lateral derecha con scroll
		barraLateralDerechaRef = new JPanel();
		JPanel barraLateralDerecha = barraLateralDerechaRef;
		barraLateralDerecha.setLayout(new BoxLayout(barraLateralDerecha, BoxLayout.Y_AXIS));
		barraLateralDerecha.setBackground(colorBoton.obtener().darker());
		barraLateralDerecha.setPreferredSize(new Dimension(250, 800));

		logoLabelRef = new JLabel();
		logoLabelRef.setOpaque(true);

		Color botonBarra = modolanzer ? lanzerColorBotonBaraLateral.obtener() : colorBotonBaraLateral.obtener();
		logoLabelRef.setBackground(botonBarra.darker());

		ImageIcon logoIcon = new ImageIcon(Statics.carpeta.resolve("imagenes/cd_logo.png").toString());
		Image logoImg = logoIcon.getImage();
		Image logoEscalado = logoImg.getScaledInstance(120, -1, Image.SCALE_SMOOTH);
		logoLabelRef.setIcon(new ImageIcon(logoEscalado));
		logoLabelRef.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		logoLabelRef.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		estilizarBoton(botonVolver);
		botonVolver.setEnabled(false);
		botonVolver.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		botonVolver.addActionListener(e -> volver());

		barraLateralDerecha.add(logoLabelRef);
		barraLateralDerecha.add(botonVolver);
		barraLateralDerecha.add(Box.createVerticalStrut(10));

		for (Entry<BiMap.DoubleKey<TipoGUI<? extends BotonDeBarraLateralDerecha>, String>, Supplier<BotonDeBarraLateralDerecha>> entrada : botons_de_barra_lateral_derecha
				.entrySet()) {

			TipoGUI tipo = entrada.getKey().key0;
			String idPredeterminado = entrada.getKey().key1;
			Supplier<BotonDeBarraLateralDerecha> proveedor = entrada.getValue();

			BotonDeBarraLateralDerecha gui = (BotonDeBarraLateralDerecha) tipo
					.obtenerGUIPredeterminado(idPredeterminado, proveedor);

			JButton boton = new JButton(tipo.etiquetaDelBoton());
			estilizarBoton(boton);
			boton.setAlignmentX(Component.CENTER_ALIGNMENT);
			boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

			if (tipo == TipoGUI.TODOS_QUICKFIXES) {
				if (!CrashDetectorGUI.esMac()) {
					boton.setBackground(lanzerColorBotonQuickFix.obtener());
					boton.setForeground(colorTexto.obtener());
				}
				boton.addActionListener(e -> mostrarVentanaQuickFix());
			} else {
				boton.addActionListener(e -> gui.init());
			}

			botons_de_barra_lateral_derecha_initalizado.put(gui, boton);
			barraLateralDerecha.add(boton);
			barraLateralDerecha.add(Box.createVerticalStrut(6));
		}

		barraLateralDerecha.add(Box.createVerticalGlue());

		scrollBarraLateralRef = new JScrollPane(barraLateralDerecha);
		scrollBarraLateralRef.setBorder(BorderFactory.createEmptyBorder());
		scrollBarraLateralRef.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBarraLateralRef.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollBarraLateralRef.getViewport().setBackground(botonBarra.darker());
		scrollBarraLateralRef.setPreferredSize(new Dimension(250, 0));
		scrollBarraLateralRef.setMinimumSize(new Dimension(180, 0));

		CrashDetectorLogger.log("Completa con botones");

		// --- Layout principal con divisor ajustable
		setLayout(new BorderLayout(5, 5));
		contenidoPrincipal.add(scrollPane, BorderLayout.CENTER);

		splitPrincipalRef = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, contenidoPrincipal, scrollBarraLateralRef);
		splitPrincipalRef.setResizeWeight(1.0);
		splitPrincipalRef.setContinuousLayout(true);
		splitPrincipalRef.setOneTouchExpandable(false);
		splitPrincipalRef.setDividerSize(8);
		splitPrincipalRef.setBorder(BorderFactory.createEmptyBorder());
		splitPrincipalRef.setDividerLocation(780);

		add(splitPrincipalRef, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);

		setTitle(Config.obtenerInstancia().obtenerNombreCD());

		// Tamaño base correcto
		int anchoBase = 1050;
		int altoBase = 650;

		// Si la pantalla útil es 1080 o más de alto, escalar a 720 de alto y
		// aumentar el ancho proporcionalmente.
		java.awt.Rectangle areaUtil = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();

		CrashDetectorLogger.log("Área útil detectada: " + areaUtil.width + "x" + areaUtil.height);

		int anchoFinal = anchoBase;
		int altoFinal = altoBase;

		if (areaUtil.height >= 1080) {
			double factorEscala = 720.0 / altoBase;
			anchoFinal = (int) Math.round(anchoBase * factorEscala);
			altoFinal = 720;

			CrashDetectorLogger.log("Pantalla >= 1080 de alto detectada. Aplicando escalado de ventana.");
			CrashDetectorLogger.log("Factor de escala aplicado: " + factorEscala);
		} else {
			CrashDetectorLogger.log("Pantalla menor de 1080 de alto. Usando tamaño base.");
		}

		CrashDetectorLogger.log("Tamaño final de ventana: " + anchoFinal + "x" + altoFinal);

		setSize(anchoFinal, altoFinal);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public void añadirBotonBarraLateral(JPanel panel, String texto) {
		JButton boton = new JButton(texto);

		if (!CrashDetectorGUI.esMac()) {
			boton.setBackground(colorBotonBaraLateral.obtener().darker());
			boton.setForeground(colorTexto.obtener());
			boton.setFont(boton.getFont().deriveFont(Font.BOLD, 14f));
			boton.setContentAreaFilled(true);
			boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		} else {
			boton.setContentAreaFilled(false);
		}

		boton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		boton.setMargin(new Insets(10, 20, 10, 20));
		boton.setMaximumSize(new Dimension(130, 40));
		boton.setMinimumSize(new Dimension(130, 40));
		boton.setPreferredSize(new Dimension(130, 40));

		panel.add(boton);
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
		boton.setFocusPainted(false);
		boton.setMargin(new Insets(0, 0, 0, 0));

		if (!CrashDetectorGUI.esMac()) {
			boton.setBackground(colorFondo.obtener());
			boton.setBorder(BorderFactory.createLineBorder(colorFondo.obtener(), 1));
			boton.setOpaque(true);
			boton.setContentAreaFilled(true);
		} else {
			boton.setBorderPainted(false);
			boton.setContentAreaFilled(false);
			boton.setOpaque(false);
			boton.setBackground(javax.swing.UIManager.getColor("Button.background"));
			boton.setForeground(javax.swing.UIManager.getColor("Button.foreground"));
		}

		boton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!CrashDetectorGUI.esMac()) {
					boton.setBackground(colorBoton.obtener().brighter());
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!CrashDetectorGUI.esMac()) {
					boton.setBackground(colorFondo.obtener());
				}
			}
		});

		panel.add(boton);
		return boton;
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

	/**
	 * Recarga la apariencia de la interfaz de usuario aplicando los colores
	 * configurados.
	 * 
	 * Este método actualiza todos los componentes de la interfaz con los colores
	 * actuales de la configuración. Debe ser llamado después de que los valores de
	 * configuración hayan cambiado para que los cambios se reflejen en la interfaz.
	 */
	@Override
	public void recargarApariencia() {

		boolean esMac = CrashDetectorGUI.esMac();

		// =========================
		// Colores activos
		// =========================
		Color fondo = colorFondo.obtener();
		Color texto = colorTexto.obtener();
		Color boton = colorBoton.obtener();
		Color cajaTexto = colorCajaTexto.obtener();

		Color botonBarra = modolanzer ? lanzerColorBotonBaraLateral.obtener() : colorBotonBaraLateral.obtener();
		Color fondoInferior = modolanzer ? lanzerColorCajaTexto.obtener() : fondo;

		// =========================
		// Ventana y contenido central
		// =========================
		getContentPane().setBackground(fondo);
		contenidoPrincipal.setBackground(fondo);

		pantalla.setForeground(texto);
		pantalla.setCaretColor(texto);
		scrollPane.getViewport().setBackground(cajaTexto);

		// =========================
		// Botón Volver
		// =========================
		if (!esMac) {
			botonVolver.setOpaque(true);
			botonVolver.setContentAreaFilled(true);
			botonVolver.setBackground(botonBarra);
			botonVolver.setForeground(texto);
		} else {
			botonVolver.setOpaque(false);
			botonVolver.setContentAreaFilled(false);
			botonVolver.setBackground(javax.swing.UIManager.getColor("Button.background"));
			botonVolver.setForeground(javax.swing.UIManager.getColor("Button.foreground"));
		}

		// =========================
		// Barra inferior
		// =========================
		if (panelInferiorRef != null) {
			panelInferiorRef.setBackground(fondoInferior);
			aplicarColorRecursivo(panelInferiorRef, fondoInferior, texto);
		}

		if (seccionConfiguracionRef != null) {
			aplicarColorRecursivo(seccionConfiguracionRef, fondoInferior, texto);
		}

		// =========================
		// Botón central CDLauncher
		// =========================
		if (botonCDLauncherRef != null) {
			if (!esMac) {
				botonCDLauncherRef.setOpaque(true);
				botonCDLauncherRef.setContentAreaFilled(true);
				botonCDLauncherRef.setBackground(boton);
				botonCDLauncherRef.setForeground(texto);
			} else {
				botonCDLauncherRef.setOpaque(false);
				botonCDLauncherRef.setContentAreaFilled(false);
				botonCDLauncherRef.setBackground(javax.swing.UIManager.getColor("Button.background"));
				botonCDLauncherRef.setForeground(javax.swing.UIManager.getColor("Button.foreground"));
			}
		}

		// =========================
		// Botones inferiores derechos
		// =========================
		if (panelBotonesDerechaRef != null) {
			panelBotonesDerechaRef.setBackground(fondoInferior);

			for (Component c : panelBotonesDerechaRef.getComponents()) {
				if (c instanceof JButton) {
					JButton b = (JButton) c;

					if (!esMac) {
						b.setOpaque(true);
						b.setContentAreaFilled(true);
						b.setBackground(fondoInferior);
						b.setForeground(modolanzer ? Color.BLACK : texto);
					} else {
						b.setOpaque(false);
						b.setContentAreaFilled(false);
						b.setBackground(javax.swing.UIManager.getColor("Button.background"));
						b.setForeground(javax.swing.UIManager.getColor("Button.foreground"));
					}
				}
			}
		}

		// =========================
		// Barra lateral derecha
		// =========================
		if (barraLateralDerechaRef != null) {
			barraLateralDerechaRef.setBackground(botonBarra.darker());
			barraLateralDerechaRef.setOpaque(true);
		}

		if (scrollBarraLateralRef != null) {
			scrollBarraLateralRef.getViewport().setBackground(botonBarra.darker());
			scrollBarraLateralRef.setBackground(botonBarra.darker());

			JScrollBar barraVertical = scrollBarraLateralRef.getVerticalScrollBar();
			if (barraVertical != null) {
				barraVertical.setBackground(botonBarra.darker());
			}
		}

		for (Entry<BotonDeBarraLateralDerecha, JButton> entry : botons_de_barra_lateral_derecha_initalizado
				.entrySet()) {

			BotonDeBarraLateralDerecha gui = entry.getKey();
			JButton btn = entry.getValue();

			if (!esMac) {
				btn.setOpaque(true);
				btn.setContentAreaFilled(true);

				if (gui.tipo() == TipoGUI.TODOS_QUICKFIXES) {
					btn.setBackground(lanzerColorBotonQuickFix.obtener());
					btn.setForeground(texto);
				} else {
					btn.setBackground(botonBarra);
					btn.setForeground(texto);
				}
			} else {
				btn.setOpaque(false);
				btn.setContentAreaFilled(false);
				btn.setBackground(javax.swing.UIManager.getColor("Button.background"));
				btn.setForeground(javax.swing.UIManager.getColor("Button.foreground"));
			}
		}

		// =========================
		// Fondo del logo
		// =========================
		if (logoLabelRef != null) {
			logoLabelRef.setBackground(botonBarra.darker());
			logoLabelRef.repaint();
		}

		// =========================
		// Paneles hijos
		// =========================
		if (panelCDMods != null) {
			panelCDMods.recargarApariencia();
		}

		if (panelConfiguracion != null) {
			panelConfiguracion.recargarApariencia();
		}

		revalidate();
		repaint();
	}

	private void aplicarColorRecursivo(Component c, Color fondo, Color texto) {

		boolean esMac = CrashDetectorGUI.esMac();

		// No recolorear manualmente el botón grande CDLauncher aquí.
		// Ese botón se maneja aparte en recargarApariencia().
		if (c == botonCDLauncherRef) {
			return;
		}

		if (c instanceof JCheckBox) {
			JCheckBox chk = (JCheckBox) c;
			chk.setOpaque(true);
			chk.setBackground(fondo);
			chk.setForeground(texto);
			return;
		}

		if (c instanceof JButton) {
			JButton btn = (JButton) c;

			if (!esMac) {
				btn.setOpaque(true);
				btn.setContentAreaFilled(true);
				btn.setBackground(fondo);
				btn.setForeground(texto);
			} else {
				btn.setOpaque(false);
				btn.setContentAreaFilled(false);
				btn.setBackground(javax.swing.UIManager.getColor("Button.background"));
				btn.setForeground(javax.swing.UIManager.getColor("Button.foreground"));
			}
		}

		if (c instanceof JComponent && !(c instanceof JButton)) {
			((JComponent) c).setBackground(fondo);
			((JComponent) c).setForeground(texto);
		}

		if (c instanceof JPanel) {
			for (Component inner : ((JPanel) c).getComponents()) {
				aplicarColorRecursivo(inner, fondo, texto);
			}
		}
	}

	@Override
	public void aplicarColoresAnalizador() {
		// Colores configurables normales del CrashDetector
		this.colorFondo = ConfigColor.de("gui.principal.color.fondo",
				Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
		this.colorTexto = ConfigColor.de("gui.principal.color.texto",
				Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		this.colorBoton = ConfigColor.de("gui.principal.color.boton",
				Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
		this.colorCajaTexto = ConfigColor.de("gui.principal.color.cajaTexto",
				Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
		this.colorEnlace = ConfigColor.de("gui.principal.color.enlace",
				Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace()));

		// Refrescar inmediatamente la interfaz
	}

	@Override
	public void aplicarColoresLanzer() {
		this.colorFondo = lanzerColorFondo;
		this.colorTexto = lanzerColorTexto;
		this.colorBoton = lanzerColorBoton;
		this.colorCajaTexto = lanzerColorCajaTexto;
		this.colorEnlace = lanzerColorEnlace;

	}

	@Override
	public void aplicarContenidoDeLaPantallaAnalizador() {
		try {
			pantalla.setContentType("text/html");
			pantalla.setText(new String(Files.readAllBytes(Paths.get(MonitorDePID.local))));
			pantalla.setCaretPosition(0);
		} catch (IOException e) {
			pantalla.setText("<html><body>Error cargando informe: " + e.getMessage() + "</body></html>");
		}

		// Asegurar refresco visual
		pantalla.revalidate();
		pantalla.repaint();
	}

	@Override
	public void aplicarContenidoDeLaPantallaLanzer() {
		pantalla.setContentType("text/html");
		pantalla.setText(com.asbestosstar.crashdetector.lanzer.PaginaDeActualizacionesMinecraft.obtenerHTML());
		pantalla.setCaretPosition(0);

		// Asegurar refresco visual
		pantalla.revalidate();
		pantalla.repaint();
	}

	/**
	 * Obtiene los elementos de configuración relacionados con los colores de la
	 * interfaz.
	 * 
	 * Este método devuelve una lista de elementos que pueden ser modificados en el
	 * panel de configuración. Cada elemento representa un color configurable de la
	 * interfaz de usuario.
	 * 
	 * @return Lista de elementos de configuración de colores
	 */
	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();

		analizadorColorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		analizadorColorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		analizadorColorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		analizadorColorCajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		analizadorColorEnlace.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorEnlace());
		analizadorColorBotonBaraLateral.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonBaraLateral());

		elementos.add(analizadorColorFondo);
		elementos.add(analizadorColorTexto);
		elementos.add(analizadorColorBoton);
		elementos.add(analizadorColorCajaTexto);
		elementos.add(analizadorColorEnlace);
		elementos.add(analizadorColorBotonBaraLateral);

		lanzerColorBotonQuickFix.establecerNombreParaMostrar(() -> "QuickFix");

		elementos.add(lanzerColorBotonQuickFix);

		lanzerColorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo() + " (CDLauncher)");
		lanzerColorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto() + " (CDLauncher)");
		lanzerColorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton() + " (CDLauncher)");
		lanzerColorCajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto() + " (CDLauncher)");
		lanzerColorEnlace.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorEnlace() + " (CDLauncher)");
		lanzerColorBotonBaraLateral.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonBaraLateral());

		elementos.add(lanzerColorFondo);
		elementos.add(lanzerColorTexto);
		elementos.add(lanzerColorBoton);
		elementos.add(lanzerColorCajaTexto);
		elementos.add(lanzerColorEnlace);
		elementos.add(lanzerColorBotonBaraLateral);

		return elementos;
	}

	@Override
	public void actualizarTextoBotonLauncherParaCDLauncher() {
		// TODO Auto-generated method stub
		botonCDLauncherRef.setText("CDLauncher");
	}

}