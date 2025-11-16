package com.asbestosstar.crashdetector.gui.tipos.principal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.asbestosstar.crashdetector.App;
import com.asbestosstar.crashdetector.BiMap;
import com.asbestosstar.crashdetector.Config;
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

public class PrincipalGUIEstiloLanzer extends PrincipalGUI {

	public static String ID = "estilo_lanzer";

	public ConfigColor colorFondo = ConfigColor.de("gui.principal.lanzer.color.fondo",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
	public ConfigColor colorTexto = ConfigColor.de("gui.principal.lanzer.color.texto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
	public ConfigColor colorBoton = ConfigColor.de("gui.principal.lanzer.color.boton",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
	public ConfigColor colorCajaTexto = ConfigColor.de("gui.principal.lanzer.color.cajaTexto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
	public ConfigColor colorEnlace = ConfigColor.de("gui.principal.lanzer.color.enlace",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace()));

	@Override
	public void init() {
		// Inicializar colores PRIMERO

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

		// Borde y color de fondo del scroll
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setBackground(colorCajaTexto.obtener());
		if (scrollPane.getVerticalScrollBar() != null) {
			scrollPane.getVerticalScrollBar().setValue(0);
		}

		// --- Panel inferior principal (configuración + botón QuickFix + botones de
		// acción) --------
		JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
		panelInferior.setBackground(colorFondo.obtener());
		panelInferior.setBorder(new EmptyBorder(10, 20, 10, 20));

		// Sección de configuración (dos columnas: izquierda = idioma, derecha =
		// aplicación)
		JPanel seccionConfiguracion = new JPanel();
		seccionConfiguracion.setLayout(new BoxLayout(seccionConfiguracion, BoxLayout.Y_AXIS));
		seccionConfiguracion.setBackground(colorFondo.obtener());

		// Contenedor horizontal para tener ambos controles (izquierda/derecha) uno al
		// lado del otro
		JPanel panelHorizontal = new JPanel();
		panelHorizontal.setLayout(new BoxLayout(panelHorizontal, BoxLayout.X_AXIS));
		panelHorizontal.setBackground(colorFondo.obtener());

		// Columna izquierda (idioma): dropdown arriba, checkbox debajo
		JPanel columnaIzquierda = new JPanel();
		columnaIzquierda.setLayout(new BoxLayout(columnaIzquierda, BoxLayout.Y_AXIS));
		columnaIzquierda.setBackground(colorFondo.obtener());

		// Columna derecha (aplicación): dropdown arriba, checkbox debajo
		JPanel columnaDerecha = new JPanel();
		columnaDerecha.setLayout(new BoxLayout(columnaDerecha, BoxLayout.Y_AXIS));
		columnaDerecha.setBackground(colorFondo.obtener());

		// --- Datos de idiomas con iconos
		// ----------------------------------------------------------
		LinkedHashMap<String, String> idiomas = new LinkedHashMap<>();
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

		// --- Selector de idioma
		// -------------------------------------------------------------------
		JComboBox<String> comboIdioma = new ComboIdiomasConIcono(idiomas);
		comboIdioma.setMaximumSize(new Dimension(200, 30));
		comboIdioma.setPreferredSize(new Dimension(200, 30));
		if (!CrashDetectorGUI.esMac()) {
			comboIdioma.setBackground(colorBoton.obtener());
			comboIdioma.setForeground(colorTexto.obtener());
		}

		// Seleccionar por defecto según el código actual de idioma detectado
		String codigoActual = MonitorDePID.idioma.codigo();
		switch (codigoActual) {
		case "es":
			comboIdioma.setSelectedItem("Español");
			break;
		case "en":
			comboIdioma.setSelectedItem("English");
			break;
		case "ar":
			comboIdioma.setSelectedItem("العربية");
			break;
		case "pt":
			comboIdioma.setSelectedItem("Português");
			break;
		case "fa":
			comboIdioma.setSelectedItem("فارسی");
			break;
		case "ru":
			comboIdioma.setSelectedItem("Русский");
			break;
		case "zh":
			comboIdioma.setSelectedItem("简体中文");
			break;
		case "eo":
			comboIdioma.setSelectedItem("Esperanto");
			break;
		case "ja":
			comboIdioma.setSelectedItem("日本語");
			break;
		case "ko":
			comboIdioma.setSelectedItem("한국어");
			break;
		default:
			comboIdioma.setSelectedItem("Español");
			break;
		}

		// Checkbox "usar idioma del sistema"
		JCheckBox chkIdiomaSistema = new JCheckBox(MonitorDePID.idioma.usarIdiomaDelSistema());
		chkIdiomaSistema.setForeground(colorTexto.obtener());
		chkIdiomaSistema.setBackground(colorFondo.obtener());
		chkIdiomaSistema.setOpaque(false);
		chkIdiomaSistema.setHorizontalAlignment(SwingConstants.LEFT);
		chkIdiomaSistema.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		chkIdiomaSistema.setMaximumSize(new Dimension(200, 30));

		// Estado inicial del checkbox: si NO existe archivo de preferencia => usar
		// sistema
		boolean existePreferencia = Files.exists(Idioma.archivo.toPath());
		boolean usarSistemaInicial = !existePreferencia;
		chkIdiomaSistema.setSelected(usarSistemaInicial);
		comboIdioma.setEnabled(!usarSistemaInicial);

		// Acción al cambiar idioma manualmente desde el combo
		comboIdioma.addActionListener(e -> {
			// Si el usuario elige manualmente, desmarcamos "usar sistema" y persistimos
			// preferencia
			chkIdiomaSistema.setSelected(false);
			comboIdioma.setEnabled(true);
			String seleccion = (String) comboIdioma.getSelectedItem();
			String codigo = obtenerCodigoIdioma(seleccion);
			if (codigo != null) {
				try (BufferedWriter w = Files.newBufferedWriter(Idioma.archivo.toPath(), StandardOpenOption.CREATE,
						StandardOpenOption.TRUNCATE_EXISTING)) {
					w.write(codigo);
				} catch (IOException ex) {
					CrashDetectorLogger.logException(ex);
				}
				// Re-detectar idioma y recargar la UI
				MonitorDePID.idioma = Idioma.detectar();
				recargar();
			}
		});

		// Acción al marcar/desmarcar "usar idioma del sistema"
		chkIdiomaSistema.addActionListener(e -> {
			boolean usarSistema = chkIdiomaSistema.isSelected();
			comboIdioma.setEnabled(!usarSistema);
			if (usarSistema) {
				// Borrar preferencia guardada para forzar el uso del idioma del sistema
				try {
					Files.deleteIfExists(Idioma.archivo.toPath());
				} catch (IOException ex) {
					CrashDetectorLogger.logException(ex);
				}
				MonitorDePID.idioma = Idioma.detectar();
				recargar();
			} else {
				// Vuelve a habilitar selección manual y persistir lo que esté seleccionado
				String seleccion = (String) comboIdioma.getSelectedItem();
				String codigo = obtenerCodigoIdioma(seleccion);
				if (codigo != null) {
					try (BufferedWriter w = Files.newBufferedWriter(Idioma.archivo.toPath(), StandardOpenOption.CREATE,
							StandardOpenOption.TRUNCATE_EXISTING)) {
						w.write(codigo);
					} catch (IOException ex) {
						CrashDetectorLogger.logException(ex);
					}
					MonitorDePID.idioma = Idioma.detectar();
					recargar();
				}
			}
		});

		// Añadir controles de idioma a la columna izquierda (dropdown arriba, checkbox
		// debajo)
		columnaIzquierda.add(comboIdioma);
		columnaIzquierda.add(chkIdiomaSistema);

		// --- Columna derecha: selector de aplicación + detectar automáticamente
		// --------------------
		// Construir el mapa de etiquetas -> App y etiquetas -> icono (solo algunos con
		// icono)
		LinkedHashMap<String, App> etiquetasAApp = new LinkedHashMap<>();
		LinkedHashMap<String, String> etiquetasAIcono = new LinkedHashMap<>();

		// Utilidades locales
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
			// Capitalización básica para otros
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

		// Poblar desde App.APPS manteniendo orden de inserción
		for (App a : App.APPS) {
			App norm = normalizarApp.apply(a);
			String etiqueta = etiquetaParaApp.apply(norm);
			// Evitar sobrescribir si hubiera duplicados de id/etiqueta
			if (!etiquetasAApp.containsKey(etiqueta)) {
				etiquetasAApp.put(etiqueta, norm);
				// Solo algunos tienen icono CD
				if (appConIconoCD.apply(norm)) {
					etiquetasAIcono.put(etiqueta, "imagenes/cd_chars.png");
				} else {
					etiquetasAIcono.put(etiqueta, null);
				}
			}
		}

		// Fallback: asegurar que al menos Minecraft exista
		if (etiquetasAApp.isEmpty()) {
			etiquetasAApp.put("Minecraft", App.MINECRAFT);
			etiquetasAIcono.put("Minecraft", "imagenes/cd_chars.png");
		}

		// Combo con renderer de iconos (reutiliza ComboIdiomasConIcono que acepta mapa
		// etiqueta->icono)
		JComboBox<String> comboAplicacion = new ComboIdiomasConIcono(etiquetasAIcono);
		comboAplicacion.setMaximumSize(new Dimension(200, 30));
		comboAplicacion.setPreferredSize(new Dimension(200, 30));
		if (!CrashDetectorGUI.esMac()) {
			comboAplicacion.setBackground(colorBoton.obtener());
			comboAplicacion.setForeground(colorTexto.obtener());
		}

		// Checkbox detectar automáticamente (por defecto activado)
		JCheckBox chkDetectarAuto = new JCheckBox("Detectar automáticamente");
		chkDetectarAuto.setForeground(colorTexto.obtener());
		chkDetectarAuto.setBackground(colorFondo.obtener());
		chkDetectarAuto.setOpaque(false);
		chkDetectarAuto.setHorizontalAlignment(SwingConstants.LEFT);
		chkDetectarAuto.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		chkDetectarAuto.setMaximumSize(new Dimension(200, 30));
		chkDetectarAuto.setSelected(true);

		// Estado inicial: cuando está marcado, el combo debe estar deshabilitado
		comboAplicacion.setEnabled(false);

		// Determinar app detectada y reflejar en Statics/combobox
		App detectada = normalizarApp.apply(App.obtenerApp());
		// Si por alguna razón detectada fuese inválida, normalizarApp ya devuelve
		// MINECRAFT
		Statics.APP = detectada;
		String etiquetaDetectada = etiquetaParaApp.apply(detectada);
		comboAplicacion.setSelectedItem(etiquetaDetectada);

		// Listener del combo: solo aplica cuando NO está marcado "detectar
		// automáticamente"
		comboAplicacion.addActionListener(e -> {
			if (chkDetectarAuto.isSelected())
				return; // ignorar si está en modo automático
			String etiquetaSel = (String) comboAplicacion.getSelectedItem();
			App seleccionada = etiquetasAApp.get(etiquetaSel);
			seleccionada = normalizarApp.apply(seleccionada);
			// Cambiar app y refrescar
			Statics.APP = seleccionada;
			// Sincronizar selección por si normalización cambió algo
			comboAplicacion.setSelectedItem(etiquetaParaApp.apply(seleccionada));
			recargar();
		});

		// Listener del checkbox: al marcar, obtener app detectada; al desmarcar, usar
		// la del combo
		chkDetectarAuto.addActionListener(e -> {
			boolean auto = chkDetectarAuto.isSelected();
			comboAplicacion.setEnabled(!auto);
			if (auto) {
				// Obtener app detectada y fijarla
				App autodetect = normalizarApp.apply(Entregar.app_detecta);
				Statics.APP = autodetect;
				comboAplicacion.setSelectedItem(etiquetaParaApp.apply(autodetect));
				recargar();
			} else {
				// Modo manual: aplicar inmediatamente lo que esté seleccionado en el combo
				String etiquetaSel = (String) comboAplicacion.getSelectedItem();
				App seleccionada = etiquetasAApp.get(etiquetaSel);
				seleccionada = normalizarApp.apply(seleccionada);
				Statics.APP = seleccionada;
				comboAplicacion.setSelectedItem(etiquetaParaApp.apply(seleccionada));
				recargar();
			}
		});

		// Añadir controles a la columna derecha
		columnaDerecha.add(comboAplicacion);
		columnaDerecha.add(chkDetectarAuto);

		// Añadir las dos columnas al contenedor horizontal
		panelHorizontal.add(columnaIzquierda);
		panelHorizontal.add(Box.createHorizontalStrut(20)); // pequeño espacio entre columnas
		panelHorizontal.add(columnaDerecha);

		// Añadir el contenedor horizontal a la sección de configuración
		seccionConfiguracion.add(panelHorizontal);

		final int ANCHO_CONTROLES = 220; // un "poquito" más ancho (ajusta a 230 si lo ves mejor)
		final int ALTO_CONTROLES = 30;

		// columna izquierda (idioma)
		comboIdioma.setPreferredSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		comboIdioma.setMaximumSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		comboIdioma.setAlignmentX(Component.LEFT_ALIGNMENT); // alinear al borde izquierdo
		chkIdiomaSistema.setPreferredSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		chkIdiomaSistema.setMaximumSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		chkIdiomaSistema.setAlignmentX(Component.LEFT_ALIGNMENT); // alinear con el combo
		// (opcional) pequeño margen superior para separar del combo
		chkIdiomaSistema.setBorder(new EmptyBorder(6, 0, 0, 0));

		// columna derecha (aplicación)
		comboAplicacion.setPreferredSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		comboAplicacion.setMaximumSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		comboAplicacion.setAlignmentX(Component.LEFT_ALIGNMENT);
		chkDetectarAuto.setPreferredSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		chkDetectarAuto.setMaximumSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
		chkDetectarAuto.setAlignmentX(Component.LEFT_ALIGNMENT);
		chkDetectarAuto.setBorder(new EmptyBorder(6, 0, 0, 0));

		// asegurar que las columnas también alineen al borde izquierdo del BoxLayout
		columnaIzquierda.setAlignmentX(Component.LEFT_ALIGNMENT);
		columnaDerecha.setAlignmentX(Component.LEFT_ALIGNMENT);

		// chkDetectarAuto.setEnabled(false);
		chkDetectarAuto.setSelected(true);
		// comboAplicacion.setEnabled(false);

		// --- Botón QuickFix (centrado)
		// ------------------------------------------------------------
		JButton botonQuickFix = new JButton("QuickFix");
		botonQuickFix.setEnabled(MonitorDePID.analizador.obtenerSoluciones().size() > 0);
		botonQuickFix.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		botonQuickFix.setMinimumSize(new Dimension(120, 30));
		botonQuickFix.setMaximumSize(new Dimension(180, 40));
		botonQuickFix.setPreferredSize(new Dimension(150, 30));
		if (CrashDetectorGUI.esMac()) {
			botonQuickFix.setContentAreaFilled(false);
		} else {
			botonQuickFix.setFont(botonQuickFix.getFont().deriveFont(Font.BOLD, 16f));
			botonQuickFix.setBackground(colorBoton.obtener());
			botonQuickFix.setForeground(colorTexto.obtener());
			botonQuickFix.setContentAreaFilled(true);
			botonQuickFix.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		}
		botonQuickFix.addActionListener(e -> mostrarVentanaQuickFix());

		// --- Botones de acción (derecha)
		// ----------------------------------------------------------
		JPanel panelBotonesDerecha = new JPanel(new GridLayout(1, 5, 10, 10));
		panelBotonesDerecha.setBackground(colorFondo.obtener());

		// luego el botón de ícono CDMods (deshabilitado), a la derecha
		JButton boton_CDMods = añadirBotonImagen(panelBotonesDerecha,
				Statics.carpeta.resolve("imagenes/boton_cdmods.png").toString(), "CD Mods");
		boton_CDMods.setEnabled(false);
		JButton btnAgregar = añadirBotonImagen(panelBotonesDerecha,
				Statics.carpeta.resolve("imagenes/boton_agregar.png").toString(),
				MonitorDePID.idioma.anadirRegistro());
		JButton btnCompartir = añadirBotonImagen(panelBotonesDerecha,
				Statics.carpeta.resolve("imagenes/boton_compartir.png").toString(),
				MonitorDePID.idioma.botonDeCompartirInforme());
		JButton btnActualizar = añadirBotonImagen(panelBotonesDerecha,
				Statics.carpeta.resolve("imagenes/boton_actualizar.png").toString(),
				MonitorDePID.idioma.actualizar());
		JButton btnArchivos = añadirBotonImagen(panelBotonesDerecha,
				Statics.carpeta.resolve("imagenes/boton_archivos.png").toString(),
				MonitorDePID.idioma.abrirCarpeta());
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

		// Colocar secciones en el panel inferior
		panelInferior.add(seccionConfiguracion, BorderLayout.WEST);
		panelInferior.add(botonQuickFix, BorderLayout.CENTER);
		panelInferior.add(panelBotonesDerecha, BorderLayout.EAST);

		// --- Barra lateral derecha
		// ---------------------------------------------------------------
		JPanel barraLateralDerecha = new JPanel();
		barraLateralDerecha.setLayout(new BoxLayout(barraLateralDerecha, BoxLayout.Y_AXIS));
		barraLateralDerecha.setBackground(colorBoton.obtener().darker());
		barraLateralDerecha.setPreferredSize(new Dimension(230, 0)); // más ancho para evitar cortes
		JLabel logoLabel = new JLabel();
		logoLabel.setBackground(colorBoton.obtener().darker());
		logoLabel.setOpaque(true);
		ImageIcon logoIcon = new ImageIcon(Statics.carpeta.resolve("imagenes/cd_logo.png").toString());
		Image logoImg = logoIcon.getImage();
		Image logoEscalado = logoImg.getScaledInstance(120, -1, Image.SCALE_SMOOTH);
		logoLabel.setIcon(new ImageIcon(logoEscalado));
		logoLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		estilizarBoton(botonVolver);
		botonVolver.setEnabled(false);
		botonVolver.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		botonVolver.addActionListener(e -> volver());
		barraLateralDerecha.add(logoLabel);
		barraLateralDerecha.add(botonVolver);
		barraLateralDerecha.add(Box.createVerticalStrut(10));

		// Botones dinámicos registrados
		for (Entry<BiMap.DoubleKey<TipoGUI<? extends BotonDeBarraLateralDerecha>, String>, Supplier<BotonDeBarraLateralDerecha>> sup : PrincipalGUI.botons_de_barra_lateral_derecha
				.entrySet()) {
			CrashDetectorLogger.log("registrando boton");
			String id_predeterminado = sup.getKey().key1;
			CrashDetectorLogger.log(id_predeterminado + "pre");
			TipoGUI tipo = sup.getKey().key0;
			Supplier gui_predeterminado = sup.getValue();
			BotonDeBarraLateralDerecha b = (BotonDeBarraLateralDerecha) tipo.obtenerGUIPredeterminado(id_predeterminado,
					gui_predeterminado);
			CrashDetectorLogger.log(b.id() + "obtene");
			JButton btn = new JButton(tipo.etiquetaDelBoton());
			if (tipo.icon() != null) {
				btn.setIcon(tipo.icon());
			}
			CrashDetectorLogger.log(b.id() + "icon y txt");
			botons_de_barra_lateral_derecha_initalizado.put(b, btn);
			CrashDetectorLogger.log(b.id() + "init boton");
			estilizarBoton(btn);
			btn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			btn.addActionListener(e -> b.init());
			barraLateralDerecha.add(btn);
			CrashDetectorLogger.log(b.id() + "agrearboton");
		}
		CrashDetectorLogger.log("Completa con botones");

		// --- Layout principal de la ventana
		// -------------------------------------------------------
		setLayout(new BorderLayout(5, 5));
		contenidoPrincipal.add(scrollPane, BorderLayout.CENTER);
		add(contenidoPrincipal, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);
		add(barraLateralDerecha, BorderLayout.EAST);

		setTitle(Config.obtenerInstancia().obtenerNombreCD());
		setSize(1050, 650); // ancho ajustado
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
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
		// Obtener los colores actuales de la configuración
		Color fondo = colorFondo.obtener();
		Color texto = colorTexto.obtener();
		Color boton = colorBoton.obtener();
		Color cajaTexto = colorCajaTexto.obtener();
		Color enlace = colorEnlace.obtener();

		// Actualizar el color de fondo de la ventana principal
		this.getContentPane().setBackground(fondo);
		// Actualizar el color de fondo del contenido principal
		contenidoPrincipal.setBackground(fondo);
		// Actualizar el color del texto en el visor HTML
		pantalla.setForeground(texto);
		pantalla.setCaretColor(texto);
		// Actualizar los colores de los botones
		botonVolver.setForeground(texto);
		botonVolver.setBackground(boton);
		// Actualizar los colores de los botones en la barra lateral derecha
		// for (JButton btn : botons_de_barra_lateral_derecha_initalizado.values()) {
		// btn.setForeground(texto);
		// btn.setBackground(boton);
		// }TODO
		// Actualizar el color de fondo del scroll pane
		scrollPane.getViewport().setBackground(cajaTexto);
		// Actualizar el color de los enlaces en el visor HTML
		pantalla.setForeground(enlace);
		// Actualizar el color de fondo de los paneles inferiores
		Component[] components = this.getContentPane().getComponents();
		if (components.length > 1) {
			Component panelInferior = components[1];
			if (panelInferior instanceof JPanel) {
				panelInferior.setBackground(fondo);
				// Actualizar componentes dentro del panel inferior
				for (Component comp : ((JPanel) panelInferior).getComponents()) {
					if (comp instanceof JComponent) {
						((JComponent) comp).setBackground(fondo);
					}
					if (comp instanceof JPanel) {
						for (Component innerComp : ((JPanel) comp).getComponents()) {
							if (innerComp instanceof JComponent) {
								((JComponent) innerComp).setBackground(fondo);
							}
						}
					}
				}
			}
		}
		// Actualizar el color de fondo de la barra lateral derecha
		if (components.length > 2) {
			Component barraLateral = components[2];
			if (barraLateral instanceof JPanel) {
				barraLateral.setBackground(boton.darker());
			}
		}
		// Actualizar el color de los botones en el panel de botones derecho
		if (components.length > 1 && components[1] instanceof JPanel) {
			JPanel panelInferior = (JPanel) components[1];
			Component[] subComponents = panelInferior.getComponents();
			if (subComponents.length > 2 && subComponents[2] instanceof JPanel) {
				JPanel panelBotonesDerecha = (JPanel) subComponents[2];
				for (Component comp : panelBotonesDerecha.getComponents()) {
					if (comp instanceof JButton) {
						JButton btn = (JButton) comp;
						btn.setBackground(fondo);
						btn.setForeground(texto);
					}
				}
			}
		}
		// Repintar la interfaz para aplicar los cambios
		this.revalidate();
		this.repaint();
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
		// Agregar todos los elementos de configuración de color
		elementos.add(colorFondo);
		elementos.add(colorTexto);
		elementos.add(colorBoton);
		elementos.add(colorCajaTexto);
		elementos.add(colorEnlace);
		return elementos;
	}
}