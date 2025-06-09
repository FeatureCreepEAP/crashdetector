package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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

		JComboBox<String> comboBoxIdioma = new JComboBox<>(new String[] { "Español", "English", "Français" });
		comboBoxIdioma.setMaximumSize(new Dimension(200, 30));
		if (!esMac()) {
			comboBoxIdioma.setBackground(colorBoton);
			comboBoxIdioma.setForeground(colorTexto);
		}

		panelDesplegableIdioma.add(iconoIdioma);
		panelDesplegableIdioma.add(comboBoxIdioma);

		JCheckBox casillaVerificacionSistema = new JCheckBox("Usar idioma del sistema");
		casillaVerificacionSistema.setForeground(colorTexto);
		casillaVerificacionSistema.setBackground(colorFondo);
		casillaVerificacionSistema.setOpaque(false);
		casillaVerificacionSistema.setHorizontalAlignment(SwingConstants.LEFT);
		casillaVerificacionSistema.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		casillaVerificacionSistema.setMaximumSize(new Dimension(200, 30));

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

		JButton botonConfiguracion = añadirBotonEmoji(botonesDerecha, "⚙️", "Configuración");
		JButton botonArchivos = añadirBotonEmoji(botonesDerecha, "📁", "Archivos");
		JButton botonActualizar = añadirBotonEmoji(botonesDerecha, "🔄", "Actualizar");
		JButton botonCompartir = añadirBotonEmoji(botonesDerecha, "📤", "Compartir");
		JButton botonAgregar = añadirBotonEmoji(botonesDerecha, "➕", "Agregar");

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

	@Override
	public void dispose() {
		super.dispose();
		MonitorDePID.fin(cerrojo);
	}

	private boolean esMac() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
}