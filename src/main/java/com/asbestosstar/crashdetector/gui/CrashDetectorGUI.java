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
import java.util.concurrent.CountDownLatch;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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


	public CrashDetectorGUI(Instant tiempoFallo, CountDownLatch cerrojo) {
		this.tiempoFallo = tiempoFallo;
		this.cerrojo = cerrojo;
		inicializarInterfaz();
	}

	private void inicializarInterfaz() {
		pantalla.setContentType("text/html");
		pantalla.setEditable(false);
		pantalla.setBackground(colorCajaTexto);
		pantalla.setForeground(colorEnlace);
		pantalla.setFont(new Font("Consolas", Font.PLAIN, 14));
		try {
			pantalla.setText(new String(Files.readAllBytes(Paths.get(MonitorDePID.local))));
			pantalla.setCaretPosition(0);// Mas alto
		} catch (IOException e) {
			pantalla.setText(
					"<html><body style='color:#ff6b6b'>Problema con el Informe: " + e.getMessage() + "</body></html>");
		}

		pantalla.addHyperlinkListener(e -> {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				try {
					Desktop.getDesktop().browse(e.getURL().toURI());
				} catch (Exception ex) {
					CrashDetectorLogger.logException(ex);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(pantalla);
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

		String[] idiomas = {
		        "Español", "English", "العربية", "Português", 
		        "فارسی", "Русский", "简体中文", "Esperanto", 
		        "日本語", "한국어"
		    };
		
		
		JComboBox<String> comboBoxIdioma = new JComboBox<>(idiomas);
		comboBoxIdioma.setMaximumSize(new Dimension(200, 30));
		if (!esMac()) {
			comboBoxIdioma.setBackground(colorBoton);
			comboBoxIdioma.setForeground(colorTexto);
		}
		
	    String currentLangCode = MonitorDePID.idioma.codigo();
	    switch (currentLangCode) {
	        case "es": comboBoxIdioma.setSelectedItem("Español"); break;
	        case "en": comboBoxIdioma.setSelectedItem("English"); break;
	        case "ar": comboBoxIdioma.setSelectedItem("العربية"); break;
	        case "pt": comboBoxIdioma.setSelectedItem("Português"); break;
	        case "fa": comboBoxIdioma.setSelectedItem("فارسی"); break;
	        case "ru": comboBoxIdioma.setSelectedItem("Русский"); break;
	        case "zh": comboBoxIdioma.setSelectedItem("简体中文"); break;
	        case "eo": comboBoxIdioma.setSelectedItem("Esperanto"); break;
	        case "ja": comboBoxIdioma.setSelectedItem("日本語"); break;
	        case "ko": comboBoxIdioma.setSelectedItem("한국어"); break;
	        default: comboBoxIdioma.setSelectedItem("Español");
	    }
	    
	    comboBoxIdioma.addActionListener(e -> {
	        String seleccion = (String) comboBoxIdioma.getSelectedItem();
	        String codigoIdioma = obtenerCodigoIdioma(seleccion);
	        
	        if (codigoIdioma != null) {
	            try (BufferedWriter writer = Files.newBufferedWriter(
	                Idioma.archivo.toPath(), 
	                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
	            	Idioma.archivo.getParentFile().mkdirs();
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

		JCheckBox casillaVerificacionSistema = new JCheckBox("Usar idioma del sistema");
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
		
		
		JPanel panelCasilla = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		panelCasilla.setBackground(colorFondo);
		panelCasilla.add(casillaVerificacionSistema);

		seccionIdioma.add(panelDesplegableIdioma);
		seccionIdioma.add(Box.createVerticalStrut(5));
		seccionIdioma.add(panelCasilla);

		JButton botonQuickFix = new JButton("QuickFix");
		botonQuickFix.setEnabled(false);
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

		panelInferior.add(botonQuickFix, BorderLayout.CENTER);

		JPanel botonesDerecha = new JPanel(new GridLayout(1, 5, 10, 10));
		botonesDerecha.setBackground(colorFondo);

        JButton botonAgregar = añadirBotonImagen(botonesDerecha, "crash_detector/boton_agregar.png", "Agregar");
        JButton botonCompartir = añadirBotonImagen(botonesDerecha, "crash_detector/boton_compartir.png", "Compartir");
        JButton botonActualizar = añadirBotonImagen(botonesDerecha, "crash_detector/boton_actualizar.png", "Actualizar");
        JButton botonArchivos = añadirBotonImagen(botonesDerecha, "crash_detector/boton_archivos.png", "Archivos");
        JButton botonConfiguracion = añadirBotonImagen(botonesDerecha, "crash_detector/boton_config.png", "Configuración");

		botonCompartir.addActionListener(e -> new DialogoCompartir(this, tiempoFallo).setVisible(true));
		botonActualizar.addActionListener(e -> recargar());
		botonAgregar.addActionListener(e -> anadirRegistro());
		botonArchivos.addActionListener(e -> abrirDirectorioEnExplorador());;
		

		panelInferior.add(seccionIdioma, BorderLayout.WEST);
		panelInferior.add(botonQuickFix, BorderLayout.CENTER);
		panelInferior.add(botonesDerecha, BorderLayout.EAST);

		JPanel barraLateralDerecha = new JPanel();
		barraLateralDerecha.setLayout(new BoxLayout(barraLateralDerecha, BoxLayout.Y_AXIS));
		barraLateralDerecha.setBackground(colorBoton.darker());
		barraLateralDerecha.setPreferredSize(new Dimension(150, 0));

		// Botón Volver (top)
		JButton botonVolver = new JButton("Volver");
		estilizarBoton(botonVolver);
		botonVolver.setEnabled(false);
		botonVolver.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		botonVolver.addActionListener(e -> {
//TODO
			botonVolver.setEnabled(false);
		});
		barraLateralDerecha.add(botonVolver);
		barraLateralDerecha.add(Box.createVerticalStrut(10)); // Espaciado entre botones

		// Botones dinámicos (grepr/fgrepr y MCreator)
		String[] botones = { "grepr/fgrepr", "MCreator" };
		for (String texto : botones) {
			JButton btn = new JButton(texto);
			estilizarBoton(btn);
			btn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

			// Asignar acciones según el botón
			if ("grepr/fgrepr".equals(texto)) {
				btn.addActionListener(e -> {
					BusquedaGUI busquedaGUI = new BusquedaGUI();
					busquedaGUI.setVisible(true);
				});
			} else if ("MCreator".equals(texto)) {
				btn.addActionListener(e -> {
					EscanerMCreatorGUI escaner = new EscanerMCreatorGUI();
					escaner.setVisible(true);
				});
			}

			barraLateralDerecha.add(btn);
		}

		setLayout(new BorderLayout(5, 5));
		add(scrollPane, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);
		add(barraLateralDerecha, BorderLayout.EAST);

		setTitle("CrashDetector");
		setSize(1000, 650);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	private String obtenerCodigoIdioma(String nombreIdioma) {
	    switch (nombreIdioma) {
	        case "Español": return "es";
	        case "English": return "en";
	        case "العربية": return "ar";
	        case "Português": return "pt";
	        case "فارسی": return "fa";
	        case "Русский": return "ru";
	        case "简体中文": return "zh";
	        case "Esperanto": return "eo";
	        case "日本語": return "ja";
	        case "한국어": return "ko";
	        default: return "es";
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
	            cons.finalizarContento(tiempoFallo, true);
	            MonitorDePID.consolas.add(cons);
	            recargar();
	        } catch (IOException e) {
	            // Registramos la excepción
	            CrashDetectorLogger.logException(e);
	            
	            // Mostramos un mensaje de error al usuario
	            JOptionPane.showMessageDialog(null, "Error al abrir el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
		boton.setMaximumSize(new Dimension(130, 40));
		boton.setMinimumSize(new Dimension(130, 40));
		boton.setPreferredSize(new Dimension(130, 40));
	}

	public void recargar() {
		MonitorDePID.recargar(false, null);
		//this.inicializarInterfaz();
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
        int BUTTON_SIZE = 64;  // 设置按钮大小
        Image scaledImage = image.getScaledInstance(BUTTON_SIZE - 10, BUTTON_SIZE - 10, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        
        // 设置按钮样式
        boton.setIcon(icon);
        boton.setText("");  // 隐藏文本
        boton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        boton.setBackground(colorBoton);
        boton.setBorder(BorderFactory.createLineBorder(colorFondo, 1));
        boton.setFocusPainted(false);
        boton.setMargin(new Insets(0, 0, 0, 0));  // 减少内边距
        
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
    
    
    
	@Override
	public void dispose() {
		super.dispose();
		MonitorDePID.fin(cerrojo);
	}

	private boolean esMac() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
}