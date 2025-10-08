package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;

public class CrashDetectorGUI extends JFrame {
	private final Instant tiempoFallo;

	public static final Color colorFondo = Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo());
	public static final Color colorTexto = Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto());
	public static final Color colorBoton = Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton());
	public static final Color colorCajaTexto = Config
			.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto());
	public static final Color colorEnlace = Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace());

	private CountDownLatch cerrojo;
	JEditorPane pantalla = new JEditorPane();
	JScrollPane scrollPane = new JScrollPane(pantalla);
	ConfigPanel panelConfiguracion = new ConfigPanel(this);
	JButton botonVolver = new JButton(MonitorDePID.idioma.volver());
	JButton botonConfiguracion;
	JPanel contenidoPrincipal = new JPanel(new BorderLayout());

	/**
	 * Botons de la barra lateral
	 */
	private static List<Supplier<BotonDeBarraLateralDerecha>> botons_de_barra_lateral_derecha = new ArrayList<>();

	static {
		registrarBotonDeBarraLateralDerecha(() -> new BusquedaGUI());
		registrarBotonDeBarraLateralDerecha(() -> new EscanerMCreatorGUI());
		registrarBotonDeBarraLateralDerecha(() -> new HistoriaModsGUI());
		registrarBotonDeBarraLateralDerecha(() -> new ArbolDeModsGUI());
		registrarBotonDeBarraLateralDerecha(() -> new LectadorDeConsolas());
		registrarBotonDeBarraLateralDerecha(() -> new EditorCodiceGUI());
	}

	public CrashDetectorGUI(Instant tiempoFallo, CountDownLatch cerrojo) {
		this.tiempoFallo = tiempoFallo;
		this.cerrojo = cerrojo;

		inicializarInterfaz();

	}

	/**
	 * Registrar Botons de la barra lateral
	 */
	public static void registrarBotonDeBarraLateralDerecha(Supplier<BotonDeBarraLateralDerecha> boton) {
		botons_de_barra_lateral_derecha.add(boton);
	}

private void inicializarInterfaz() {
    // --- Inicialización del visor HTML ---------------------------------------------------------
    CrashDetectorLogger.log("inicializar interfaz");
    pantalla.setContentType("text/html");
    pantalla.setEditable(false);
    pantalla.setBackground(colorCajaTexto);
    pantalla.setForeground(colorEnlace);
    pantalla.setFont(new Font("Consolas", Font.PLAIN, 14));

    try {
        CrashDetectorLogger.log("establecer texto");
        pantalla.setText(new String(Files.readAllBytes(Paths.get(MonitorDePID.local))));
        pantalla.setCaretPosition(0); // Ir al inicio
        CrashDetectorLogger.log("establecer texto completado");
    } catch (IOException e) {
        pantalla.setText("<html><body style='color:#ff6b6b'>Problema con el Informe: "
                + e.getMessage() + "</body></html>");
    }

    // Abrir hipervínculos (incluyendo protocolo interno lectador://)
    pantalla.addHyperlinkListener(e -> {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
                String url = e.getDescription();
                if (url != null && url.startsWith("lectador://")) {
                    CrashDetectorLogger.log(url + " (lectador url)");
                    LectadorDeConsolas.procesarHipervinculo(url);
                } else if (url != null) {
                    Desktop.getDesktop().browse(new java.net.URI(url));
                }
            } catch (Exception ex) {
                CrashDetectorLogger.logException(ex);
            }
        }
    });

    // Borde y color de fondo del scroll
    CrashDetectorLogger.log("establecer borde del scroll");
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    scrollPane.getViewport().setBackground(colorCajaTexto);
    if (scrollPane.getVerticalScrollBar() != null) {
        scrollPane.getVerticalScrollBar().setValue(0);
    }

    // --- Panel inferior principal (configuración + botón QuickFix + botones de acción) --------
    JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
    panelInferior.setBackground(colorFondo);
    panelInferior.setBorder(new EmptyBorder(10, 20, 10, 20));

    // Sección de configuración (dos columnas: izquierda = idioma, derecha = aplicación)
    JPanel seccionConfiguracion = new JPanel();
    seccionConfiguracion.setLayout(new BoxLayout(seccionConfiguracion, BoxLayout.Y_AXIS));
    seccionConfiguracion.setBackground(colorFondo);

    // Contenedor horizontal para tener ambos controles (izquierda/derecha) uno al lado del otro
    JPanel panelHorizontal = new JPanel();
    panelHorizontal.setLayout(new BoxLayout(panelHorizontal, BoxLayout.X_AXIS));
    panelHorizontal.setBackground(colorFondo);

    // Columna izquierda (idioma): dropdown arriba, checkbox debajo
    JPanel columnaIzquierda = new JPanel();
    columnaIzquierda.setLayout(new BoxLayout(columnaIzquierda, BoxLayout.Y_AXIS));
    columnaIzquierda.setBackground(colorFondo);

    // Columna derecha (aplicación): dropdown arriba, checkbox debajo
    JPanel columnaDerecha = new JPanel();
    columnaDerecha.setLayout(new BoxLayout(columnaDerecha, BoxLayout.Y_AXIS));
    columnaDerecha.setBackground(colorFondo);

    // --- Datos de idiomas con iconos ----------------------------------------------------------
    LinkedHashMap<String, String> idiomas = new LinkedHashMap<>();
    idiomas.put("Español",   "imagenes/bandera_mexico.png");
    idiomas.put("English",   "imagenes/bandera_inglaterra.png");
    idiomas.put("العربية",   "imagenes/bandera_arabia.png");
    idiomas.put("Português", "imagenes/bandera_brasil.png");
    idiomas.put("فارسی",     "imagenes/bandera_iran.png");
    idiomas.put("Русский",   "imagenes/bandera_rusia.png");
    idiomas.put("简体中文",   "imagenes/bandera_china.png");
    idiomas.put("Esperanto", "imagenes/bandera_esperanto.png");
    idiomas.put("日本語",     "imagenes/bandera_japon.png");
    idiomas.put("한국어",     "imagenes/bandera_corea.png");

    // --- Selector de idioma -------------------------------------------------------------------
    JComboBox<String> comboIdioma = new ComboIdiomasConIcono(idiomas);
    comboIdioma.setMaximumSize(new Dimension(200, 30));
    comboIdioma.setPreferredSize(new Dimension(200, 30));
    if (!esMac()) {
        comboIdioma.setBackground(colorBoton);
        comboIdioma.setForeground(colorTexto);
    }

    // Seleccionar por defecto según el código actual de idioma detectado
    String codigoActual = MonitorDePID.idioma.codigo();
    switch (codigoActual) {
        case "es": comboIdioma.setSelectedItem("Español");   break;
        case "en": comboIdioma.setSelectedItem("English");   break;
        case "ar": comboIdioma.setSelectedItem("العربية");   break;
        case "pt": comboIdioma.setSelectedItem("Português"); break;
        case "fa": comboIdioma.setSelectedItem("فارسی");     break;
        case "ru": comboIdioma.setSelectedItem("Русский");   break;
        case "zh": comboIdioma.setSelectedItem("简体中文");   break;
        case "eo": comboIdioma.setSelectedItem("Esperanto"); break;
        case "ja": comboIdioma.setSelectedItem("日本語");     break;
        case "ko": comboIdioma.setSelectedItem("한국어");     break;
        default:   comboIdioma.setSelectedItem("Español");   break;
    }

    // Checkbox "usar idioma del sistema"
    JCheckBox chkIdiomaSistema = new JCheckBox(MonitorDePID.idioma.usarIdiomaDelSistema());
    chkIdiomaSistema.setForeground(colorTexto);
    chkIdiomaSistema.setBackground(colorFondo);
    chkIdiomaSistema.setOpaque(false);
    chkIdiomaSistema.setHorizontalAlignment(SwingConstants.LEFT);
    chkIdiomaSistema.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    chkIdiomaSistema.setMaximumSize(new Dimension(200, 30));

    // Estado inicial del checkbox: si NO existe archivo de preferencia => usar sistema
    boolean existePreferencia = Files.exists(Idioma.archivo.toPath());
    boolean usarSistemaInicial = !existePreferencia;
    chkIdiomaSistema.setSelected(usarSistemaInicial);
    comboIdioma.setEnabled(!usarSistemaInicial);

    // Acción al cambiar idioma manualmente desde el combo
    comboIdioma.addActionListener(e -> {
        // Si el usuario elige manualmente, desmarcamos "usar sistema" y persistimos preferencia
        chkIdiomaSistema.setSelected(false);
        comboIdioma.setEnabled(true);

        String seleccion = (String) comboIdioma.getSelectedItem();
        String codigo = obtenerCodigoIdioma(seleccion);
        if (codigo != null) {
            try (BufferedWriter w = Files.newBufferedWriter(
                    Idioma.archivo.toPath(),
                    StandardOpenOption.CREATE,
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
                try (BufferedWriter w = Files.newBufferedWriter(
                        Idioma.archivo.toPath(),
                        StandardOpenOption.CREATE,
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

    // Añadir controles de idioma a la columna izquierda (dropdown arriba, checkbox debajo)
    columnaIzquierda.add(comboIdioma);
    columnaIzquierda.add(chkIdiomaSistema);

    LinkedHashMap<String, String> apps = new LinkedHashMap<>();
    apps.put("Minecraft",   "imagenes/cd_chars.png");
    
    
    // --- Columna derecha: selector de aplicación + detectar automáticamente --------------------
    // Nota: este combo es un marcador de posición; se respeta el layout solicitado.
    JComboBox<String> comboAplicacion = new ComboIdiomasConIcono(apps);
    comboAplicacion.setMaximumSize(new Dimension(200, 30));
    comboAplicacion.setPreferredSize(new Dimension(200, 30));
    if (!esMac()) {
        comboAplicacion.setBackground(colorBoton);
        comboAplicacion.setForeground(colorTexto);
    }

    JCheckBox chkDetectarAuto = new JCheckBox("Detectar automáticamente");
    chkDetectarAuto.setForeground(colorTexto);
    chkDetectarAuto.setBackground(colorFondo);
    chkDetectarAuto.setOpaque(false);
    chkDetectarAuto.setHorizontalAlignment(SwingConstants.LEFT);
    chkDetectarAuto.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    chkDetectarAuto.setMaximumSize(new Dimension(200, 30));

    columnaDerecha.add(comboAplicacion);
    columnaDerecha.add(chkDetectarAuto);

    // Añadir las dos columnas al contenedor horizontal
    panelHorizontal.add(columnaIzquierda);
    panelHorizontal.add(Box.createHorizontalStrut(20)); // pequeño espacio entre columnas
    panelHorizontal.add(columnaDerecha);

    // Añadir el contenedor horizontal a la sección de configuración
    seccionConfiguracion.add(panelHorizontal);

    
    
    
    
    
    
    
    
    
    
    
    
    final int ANCHO_CONTROLES = 220;   // un “poquito” más ancho (ajusta a 230 si lo ves mejor)
    final int ALTO_CONTROLES  = 30;

    // columna izquierda (idioma)
    comboIdioma.setPreferredSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
    comboIdioma.setMaximumSize(new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES));
    comboIdioma.setAlignmentX(Component.LEFT_ALIGNMENT);  // alinear al borde izquierdo

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
    chkDetectarAuto.setEnabled(false);
    chkDetectarAuto.setSelected(true);
    comboAplicacion.setEnabled(false);

    
    
    
    
    
    
    
    // --- Botón QuickFix (centrado) ------------------------------------------------------------
    JButton botonQuickFix = new JButton("QuickFix");
    botonQuickFix.setEnabled(MonitorDePID.analizador.obtenerSoluciones().size() > 0);
    botonQuickFix.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    botonQuickFix.setMinimumSize(new Dimension(120, 30));
    botonQuickFix.setMaximumSize(new Dimension(180, 40));
    botonQuickFix.setPreferredSize(new Dimension(150, 30));
    if (esMac()) {
        botonQuickFix.setContentAreaFilled(false);
    } else {
        botonQuickFix.setFont(botonQuickFix.getFont().deriveFont(Font.BOLD, 16f));
        botonQuickFix.setBackground(colorBoton);
        botonQuickFix.setForeground(colorTexto);
        botonQuickFix.setContentAreaFilled(true);
        botonQuickFix.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
    botonQuickFix.addActionListener(e -> mostrarVentanaQuickFix());

    // --- Botones de acción (derecha) ----------------------------------------------------------
    JPanel panelBotonesDerecha = new JPanel(new GridLayout(1, 5, 10, 10));
    panelBotonesDerecha.setBackground(colorFondo);

 // luego el botón de ícono CDMods (deshabilitado), a la derecha
    JButton boton_CDMods = añadirBotonImagen(
    		panelBotonesDerecha,
            MonitorDePID.carpeta.resolve("imagenes/boton_cdmods.png").toString(),
            "CD Mods");
    boton_CDMods.setEnabled(false);
    
    JButton btnAgregar = añadirBotonImagen(panelBotonesDerecha,
            MonitorDePID.carpeta.resolve("imagenes/boton_agregar.png").toString(),
            MonitorDePID.idioma.anadirRegistro());

    JButton btnCompartir = añadirBotonImagen(panelBotonesDerecha,
            MonitorDePID.carpeta.resolve("imagenes/boton_compartir.png").toString(),
            MonitorDePID.idioma.botonDeCompartirInforme());

    JButton btnActualizar = añadirBotonImagen(panelBotonesDerecha,
            MonitorDePID.carpeta.resolve("imagenes/boton_actualizar.png").toString(),
            MonitorDePID.idioma.actualizar());

    JButton btnArchivos = añadirBotonImagen(panelBotonesDerecha,
            MonitorDePID.carpeta.resolve("imagenes/boton_archivos.png").toString(),
            MonitorDePID.idioma.abrirCarpeta());

    botonConfiguracion = añadirBotonImagen(panelBotonesDerecha,
            MonitorDePID.carpeta.resolve("imagenes/boton_config.png").toString(),
            MonitorDePID.idioma.config());

    btnCompartir.addActionListener(e -> new DialogoCompartir(this, tiempoFallo).setVisible(true));
    btnActualizar.addActionListener(e -> recargar());
    btnAgregar.addActionListener(e -> anadirRegistro());
    btnArchivos.addActionListener(e -> abrirDirectorioEnExplorador());
    botonConfiguracion.addActionListener(e -> {
        contenidoPrincipal.removeAll();
        contenidoPrincipal.add(panelConfiguracion, BorderLayout.CENTER);
        contenidoPrincipal.revalidate();
        contenidoPrincipal.repaint();
        botonVolver.setEnabled(true);
    });

    // Colocar secciones en el panel inferior
    panelInferior.add(seccionConfiguracion, BorderLayout.WEST);
    panelInferior.add(botonQuickFix,        BorderLayout.CENTER);
    panelInferior.add(panelBotonesDerecha,  BorderLayout.EAST);

    // --- Barra lateral derecha ---------------------------------------------------------------
    JPanel barraLateralDerecha = new JPanel();
    barraLateralDerecha.setLayout(new BoxLayout(barraLateralDerecha, BoxLayout.Y_AXIS));
    barraLateralDerecha.setBackground(colorBoton.darker());
    barraLateralDerecha.setPreferredSize(new Dimension(230, 0)); // más ancho para evitar cortes

    JLabel logoLabel = new JLabel();
    logoLabel.setBackground(colorBoton.darker());
    logoLabel.setOpaque(true);
    ImageIcon logoIcon = new ImageIcon(MonitorDePID.carpeta.resolve("imagenes/cd_logo.png").toString());
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
    for (Supplier<BotonDeBarraLateralDerecha> sup : botons_de_barra_lateral_derecha) {
        BotonDeBarraLateralDerecha b = sup.get();
        JButton btn = new JButton(b.etiquetaDelBoton());
        if (b.icon() != null) {
            btn.setIcon(b.icon());
        }
        estilizarBoton(btn);
        btn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        btn.addActionListener(e -> b.init());
        barraLateralDerecha.add(btn);
    }

    // --- Layout principal de la ventana -------------------------------------------------------
    setLayout(new BorderLayout(5, 5));
    contenidoPrincipal.add(scrollPane, BorderLayout.CENTER);
    add(contenidoPrincipal, BorderLayout.CENTER);
    add(panelInferior,      BorderLayout.SOUTH);
    add(barraLateralDerecha,BorderLayout.EAST);

    setTitle("CrashDetector");
    setSize(1050, 650); // ancho ajustado
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
}














	
	
	
	
	

	public void volver() {
		// TODO Auto-generated method stub
		contenidoPrincipal.removeAll();
		contenidoPrincipal.add(scrollPane, BorderLayout.CENTER);
		contenidoPrincipal.revalidate();
		contenidoPrincipal.repaint();
		botonVolver.setEnabled(false);
	}

	private String obtenerCodigoIdioma(String nombreIdioma) {
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

	private void anadirRegistro() {
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

	private JButton añadirBotonEmoji(JPanel panel, String emoji, String tooltip) {
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

		boton.setForeground(colorTexto);
		boton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				boton.setBackground(colorBoton.brighter());
				boton.setForeground(Color.BLACK);
			}

			public void mouseExited(MouseEvent evt) {
				boton.setBackground(colorFondo);
				boton.setForeground(colorTexto);
			}
		});

		panel.add(boton);
		return boton;
	}

	private void añadirBotonBarraLateral(JPanel panel, String texto) {
		JButton boton = new JButton(texto);
		boton.setBackground(colorBoton.darker());
		boton.setForeground(colorTexto);
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

	private void estilizarBoton(JButton boton) {
		if (!esMac()) {
			boton.setAlignmentX(Component.CENTER_ALIGNMENT);
			boton.setBackground(colorBoton);
			boton.setContentAreaFilled(true);
			boton.setForeground(colorTexto);
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

private JButton añadirBotonImagen(JPanel panel, String imagePath, String tooltip) {
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
    boton.setBackground(colorFondo);
    boton.setBorder(BorderFactory.createLineBorder(colorFondo, 1));
    boton.setFocusPainted(false);
    boton.setMargin(new Insets(0, 0, 0, 0)); // Reduced margin

    boton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            boton.setBackground(colorBoton.brighter());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            boton.setBackground(colorFondo);
        }
    });

    panel.add(boton);
    return boton;
}

//    private JPanel crearPanelQuickFix() {
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBackground(colorFondo);
//        
//        // Create the scrollable QuickFix panel
//        PanelQuickFix panelQuickFix = new PanelQuickFix();
//        
//        // Example 1: With checkbox
//        QuickFix fixConRetener = new QuickFix.Builder("Limpiar caché temporal")
//            .conRetener()
//            .agregarBoton("Limpiar", retener -> {
//                System.out.println("Limpiando caché. Retener: " + retener);
//                JOptionPane.showMessageDialog(this, 
//                    "Limpiando caché\nRetener: " + retener, 
//                    "Acción de QuickFix", 
//                    JOptionPane.INFORMATION_MESSAGE);
//            })
//            .agregarEtiqueta("Este proceso eliminará archivos temporales")
//            .construir();
//        
//        // Example 2: Without checkbox
//        QuickFix fixSinRetener = new QuickFix.Builder("Optimizar base de datos")
//            .agregarBoton("Optimizar", retener -> {
//                System.out.println("Optimizando base de datos. Retener: " + retener);
//                JOptionPane.showMessageDialog(this, 
//                    "Optimizando base de datos\nRetener: " + retener, 
//                    "Acción de QuickFix", 
//                    JOptionPane.INFORMATION_MESSAGE);
//            })
//            .agregarEtiqueta("Este proceso optimizará el rendimiento de la base de datos")
//            .construir();
//        
//        // Add both QuickFix entries to the panel
//        panelQuickFix.agregarQuickFix(fixConRetener);
//        panelQuickFix.agregarQuickFix(fixSinRetener);
//        
//        // Add to main panel with scroll support
//        panel.add(panelQuickFix, BorderLayout.CENTER);
//        
//        return panel;
//    }

	private void mostrarVentanaQuickFix() {
		// Crear el diálogo emergente
		JDialog dialogo = new JDialog(this, "QuickFix", true);
		dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialogo.setSize(600, 400);
		dialogo.setLocationRelativeTo(this);

		// Crear el panel QuickFix
		JPanel panelQuickFix = new JPanel(new BorderLayout());
		if (!esMac()) {
			panelQuickFix.setBackground(colorFondo);
		}
		// Crear el scrollable QuickFix panel
		PanelQuickFix panelContenido = new PanelQuickFix();

//        // Ejemplo 1: Con checkbox
//        QuickFix fixConRetener = new QuickFix.Builder("Limpiar caché temporal")
//            .conRetener()
//            .agregarBoton("Limpiar", retener -> {
//                System.out.println("Limpiando caché. Retener: " + retener);
//                JOptionPane.showMessageDialog(dialogo, 
//                    "Limpiando caché\nRetener: " + retener, 
//                    "Acción de QuickFix", 
//                    JOptionPane.INFORMATION_MESSAGE);
//            })
//            .agregarEtiqueta("Este proceso eliminará archivos temporales")
//            .construir();
//        
//        // Ejemplo 2: Sin checkbox
//        QuickFix fixSinRetener = new QuickFix.Builder("Optimizar base de datos")
//            .agregarBoton("Optimizar", retener -> {
//                System.out.println("Optimizando base de datos. Retener: " + retener);
//                JOptionPane.showMessageDialog(dialogo, 
//                    "Optimizando base de datos\nRetener: " + retener, 
//                    "Acción de QuickFix", 
//                    JOptionPane.INFORMATION_MESSAGE);
//            })
//            .agregarEtiqueta("Este proceso optimizará el rendimiento de la base de datos")
//            .construir();
//        
//        panelContenido.agregarQuickFix(fixConRetener);
//        panelContenido.agregarQuickFix(fixSinRetener);
		for (QuickFix solucion : MonitorDePID.analizador.obtenerSoluciones()) {
			panelContenido.agregarQuickFix(solucion);
		}

		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelInferior.setBackground(colorFondo);

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

	public static boolean esMac() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
}