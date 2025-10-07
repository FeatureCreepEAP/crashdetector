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
		CrashDetectorLogger.log("inicializar interfaz");
		pantalla.setContentType("text/html");
		pantalla.setEditable(false);
		pantalla.setBackground(colorCajaTexto);
		pantalla.setForeground(colorEnlace);
		pantalla.setFont(new Font("Consolas", Font.PLAIN, 14));
		try {
			CrashDetectorLogger.log("estabalar texto");
			pantalla.setText(new String(Files.readAllBytes(Paths.get(MonitorDePID.local))));
			pantalla.setCaretPosition(0);// Mas alto
			CrashDetectorLogger.log("estabalar texto completa");
		} catch (IOException e) {
			pantalla.setText(
					"<html><body style='color:#ff6b6b'>Problema con el Informe: " + e.getMessage() + "</body></html>");
		}

		pantalla.addHyperlinkListener(e -> {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				try {
					// Usar getDescription() porque getURL() puede ser null en protocolos custom
					String url = e.getDescription();

					if (url.startsWith("lectador://")) {
						CrashDetectorLogger.log(url + " (lectador url)");
						LectadorDeConsolas.procesarHipervinculo(url);
					} else {
						// Para URLs estándar (http/https/file/etc.)
						Desktop.getDesktop().browse(new java.net.URI(url));
					}
				} catch (Exception ex) {
					CrashDetectorLogger.logException(ex);
				}
			}
		});

		CrashDetectorLogger.log("estabalar frontera");
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setBackground(colorCajaTexto);

		// Mas alto
		if (scrollPane.getVerticalScrollBar() != null) {
			scrollPane.getVerticalScrollBar().setValue(0);
		}

		JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
		panelInferior.setBackground(colorFondo);
		panelInferior.setBorder(new EmptyBorder(10, 20, 10, 20));

		JPanel seccionIdioma = new JPanel();
		seccionIdioma.setLayout(new BoxLayout(seccionIdioma, BoxLayout.Y_AXIS));
		seccionIdioma.setBackground(colorFondo);

		JPanel panelDesplegableIdioma = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panelDesplegableIdioma.setBackground(colorFondo);

		JLabel iconoIdioma = new JLabel("🌐");
		iconoIdioma.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

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

		CrashDetectorLogger.log("combobox");
		JComboBox<String> comboBoxIdioma = new ComboIdiomasConIcono(idiomas);
		comboBoxIdioma.setMaximumSize(new Dimension(200, 30));
		comboBoxIdioma.setPreferredSize(new Dimension(200, 30));
		if (!esMac()) {
			comboBoxIdioma.setBackground(colorBoton);
			comboBoxIdioma.setForeground(colorTexto);
		}
		CrashDetectorLogger.log("idioma");
		String currentLangCode = MonitorDePID.idioma.codigo();
		switch (currentLangCode) {
		case "es":
			comboBoxIdioma.setSelectedItem("Español");
			break;
		case "en":
			comboBoxIdioma.setSelectedItem("English");
			break;
		case "ar":
			comboBoxIdioma.setSelectedItem("العربية");
			break;
		case "pt":
			comboBoxIdioma.setSelectedItem("Português");
			break;
		case "fa":
			comboBoxIdioma.setSelectedItem("فارسی");
			break;
		case "ru":
			comboBoxIdioma.setSelectedItem("Русский");
			break;
		case "zh":
			comboBoxIdioma.setSelectedItem("简体中文");
			break;
		case "eo":
			comboBoxIdioma.setSelectedItem("Esperanto");
			break;
		case "ja":
			comboBoxIdioma.setSelectedItem("日本語");
			break;
		case "ko":
			comboBoxIdioma.setSelectedItem("한국어");
			break;
		default:
			comboBoxIdioma.setSelectedItem("Español");
		}

		comboBoxIdioma.addActionListener(e -> {
			String seleccion = (String) comboBoxIdioma.getSelectedItem();
			String codigoIdioma = obtenerCodigoIdioma(seleccion);

			if (codigoIdioma != null) {
				Idioma.archivo.getParentFile().mkdirs();
				try (BufferedWriter writer = Files.newBufferedWriter(Idioma.archivo.toPath(), StandardOpenOption.CREATE,
						StandardOpenOption.TRUNCATE_EXISTING)) {
					writer.write(codigoIdioma);
				} catch (IOException ex) {
					CrashDetectorLogger.logException(ex);
				}
			}
			MonitorDePID.idioma = Idioma.detectar();
			recargar();
		});

		panelDesplegableIdioma.add(iconoIdioma);
		panelDesplegableIdioma.add(comboBoxIdioma);

		JCheckBox casillaVerificacionSistema = new JCheckBox(MonitorDePID.idioma.usarIdiomaDelSistema());
		casillaVerificacionSistema.setForeground(colorTexto);
		casillaVerificacionSistema.setBackground(colorFondo);
		casillaVerificacionSistema.setOpaque(false);
		casillaVerificacionSistema.setHorizontalAlignment(SwingConstants.LEFT);
		casillaVerificacionSistema.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		casillaVerificacionSistema.setMaximumSize(new Dimension(200, 30));

		casillaVerificacionSistema.addActionListener(e -> {
			if (casillaVerificacionSistema.isSelected()) {
				boolean usarSistema = casillaVerificacionSistema.isSelected();

				comboBoxIdioma.setEnabled(!usarSistema);

				if (usarSistema) {
					try {
						Files.deleteIfExists(Idioma.archivo.toPath());
						MonitorDePID.idioma = Idioma.detectar();
						recargar();
					} catch (IOException ex) {
						CrashDetectorLogger.logException(ex);
					}
				}
			}
		});

		CrashDetectorLogger.log("estabalar casilla");
		JPanel panelCasilla = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panelCasilla.setBackground(colorFondo);
		panelCasilla.add(casillaVerificacionSistema);

		seccionIdioma.add(panelDesplegableIdioma);
		seccionIdioma.add(Box.createVerticalStrut(5));
		seccionIdioma.add(panelCasilla);

		CrashDetectorLogger.log("estabalar quickfix");
		JButton botonQuickFix = new JButton("QuickFix");
		botonQuickFix.setEnabled(MonitorDePID.analizador.obtenerSoluciones().size() > 0);
		botonQuickFix.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		botonQuickFix.setMinimumSize(new Dimension(150, 40));
		botonQuickFix.setMaximumSize(new Dimension(300, 40));
		botonQuickFix.setPreferredSize(new Dimension(200, 40));
		botonQuickFix.setFocusPainted(false);

		if (esMac()) {
			botonQuickFix.setContentAreaFilled(false); // Estilo nativo de macOS
		} else {
			botonQuickFix.setFont(botonQuickFix.getFont().deriveFont(Font.BOLD, 16f));
			botonQuickFix.setBackground(colorBoton);
			botonQuickFix.setForeground(colorTexto); // Color original en otros sistemas
			botonQuickFix.setContentAreaFilled(true);
			botonQuickFix.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		}

		botonQuickFix.addActionListener(e -> mostrarVentanaQuickFix());
//		botonQuickFix.addActionListener(e -> {
//		    contenidoPrincipal.removeAll();
//		    contenidoPrincipal.add(crearPanelQuickFix(), BorderLayout.CENTER);
//		    contenidoPrincipal.revalidate();
//		    contenidoPrincipal.repaint();
//		    botonVolver.setEnabled(true);
//		});

		CrashDetectorLogger.log("panelInferior");
		panelInferior.add(botonQuickFix, BorderLayout.CENTER);
		CrashDetectorLogger.log("estabalar derecha");
		JPanel botonesDerecha = new JPanel(new GridLayout(1, 5, 10, 10));
		botonesDerecha.setBackground(colorFondo);
		CrashDetectorLogger.log("botones principales");
		JButton botonAgregar = añadirBotonImagen(botonesDerecha,
				MonitorDePID.carpeta.resolve("imagenes/boton_agregar.png").toString(),
				MonitorDePID.idioma.anadirRegistro());

		JButton botonCompartir = añadirBotonImagen(botonesDerecha,
				MonitorDePID.carpeta.resolve("imagenes/boton_compartir.png").toString(),
				MonitorDePID.idioma.botonDeCompartirInforme());

		JButton botonActualizar = añadirBotonImagen(botonesDerecha,
				MonitorDePID.carpeta.resolve("imagenes/boton_actualizar.png").toString(),
				MonitorDePID.idioma.actualizar());

		JButton botonArchivos = añadirBotonImagen(botonesDerecha,
				MonitorDePID.carpeta.resolve("imagenes/boton_archivos.png").toString(),
				MonitorDePID.idioma.abrirCarpeta());

		botonConfiguracion = añadirBotonImagen(botonesDerecha,
				MonitorDePID.carpeta.resolve("imagenes/boton_config.png").toString(), MonitorDePID.idioma.config());

		botonCompartir.addActionListener(e -> new DialogoCompartir(this, tiempoFallo).setVisible(true));
		botonActualizar.addActionListener(e -> recargar());
		botonAgregar.addActionListener(e -> anadirRegistro());
		botonArchivos.addActionListener(e -> abrirDirectorioEnExplorador());
		;
		botonConfiguracion.addActionListener(e -> {
			contenidoPrincipal.removeAll();
			contenidoPrincipal.add(panelConfiguracion, BorderLayout.CENTER);
			contenidoPrincipal.revalidate();
			contenidoPrincipal.repaint();
			botonVolver.setEnabled(true);
		});

		panelInferior.add(seccionIdioma, BorderLayout.WEST);
		panelInferior.add(botonQuickFix, BorderLayout.CENTER);
		panelInferior.add(botonesDerecha, BorderLayout.EAST);

		JPanel barraLateralDerecha = new JPanel();
		barraLateralDerecha.setLayout(new BoxLayout(barraLateralDerecha, BoxLayout.Y_AXIS));
		barraLateralDerecha.setBackground(colorBoton.darker());
		barraLateralDerecha.setPreferredSize(new Dimension(175, 0));

		CrashDetectorLogger.log("estabalar logo");
		JLabel logoLabel = new JLabel();
		logoLabel.setBackground(colorBoton.darker());
		logoLabel.setOpaque(true);

		// 加载 logo 图片
		ImageIcon logoIcon = new ImageIcon(MonitorDePID.carpeta.resolve("imagenes/cd_logo.png").toString());
		Image logoImagen = logoIcon.getImage();
		Image escalarLogo = logoImagen.getScaledInstance(120, -1, Image.SCALE_SMOOTH); // -1 保持宽高比
		logoLabel.setIcon(new ImageIcon(escalarLogo));
		logoLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 周围留白

		// Botón Volver (top)
		estilizarBoton(botonVolver);
		botonVolver.setEnabled(false);
		botonVolver.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		botonVolver.addActionListener(e -> {
			volver();
		});
		barraLateralDerecha.add(logoLabel);
		barraLateralDerecha.add(botonVolver);
		barraLateralDerecha.add(Box.createVerticalStrut(10)); // Espaciado entre botones
		CrashDetectorLogger.log("estabalar lateral derecha");
		// Botones dinámicos (grepr/fgrepr y MCreator)

		for (Supplier<BotonDeBarraLateralDerecha> sup : botons_de_barra_lateral_derecha) {
			BotonDeBarraLateralDerecha bt = sup.get();
			JButton btn = new JButton(bt.etiquetaDelBoton());
			if (bt.icon() != null) {
				btn.setIcon(bt.icon());
			}

			estilizarBoton(btn);
			btn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

			// Asignar acciones según el botón

			btn.addActionListener(e -> {
				bt.init();
			});

			barraLateralDerecha.add(btn);
		}

		CrashDetectorLogger.log("estabalar layout");
		setLayout(new BorderLayout(5, 5));
		// add(scrollPane, BorderLayout.CENTER);
		contenidoPrincipal.add(scrollPane, BorderLayout.CENTER);
		add(contenidoPrincipal, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);
		add(barraLateralDerecha, BorderLayout.EAST);
		CrashDetectorLogger.log("estabalar titilo");
		setTitle("CrashDetector");
		setSize(1000, 650);
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

	// 替换 emoji 按钮为图像按钮
	private JButton añadirBotonImagen(JPanel panel, String imagePath, String tooltip) {
		JButton boton = new JButton();
		boton.setToolTipText(tooltip);

		// 加载图像
		ImageIcon originalIcon = new ImageIcon(imagePath);
		Image image = originalIcon.getImage();

		// 调整图像大小（64x64）
		int BUTTON_SIZE = 64; // 设置按钮大小
		Image scaledImage = image.getScaledInstance(BUTTON_SIZE - 10, BUTTON_SIZE - 10, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(scaledImage);

		// 设置按钮样式
		boton.setIcon(icon);
		boton.setText(""); // 隐藏文本
		boton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
		// boton.setBackground(colorBoton);
		boton.setBackground(colorFondo);
		boton.setBorder(BorderFactory.createLineBorder(colorFondo, 1));
		boton.setFocusPainted(false);
		boton.setMargin(new Insets(0, 0, 0, 0)); // 减少内边距

		// 悬停效果
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