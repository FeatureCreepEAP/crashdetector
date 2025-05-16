package com.asbestosstar.crashdetectormc.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.GeneradorDeInformacion;
import com.asbestosstar.crashdetector.MonitorDePID;

public class CrashDetectorGUI extends JFrame {

    private final StringBuilder contenidoInforme;
    private final Instant tiempoFallo;
    private final List<Consola> consolas;

    // Colores configurables
    public static final Color colorFondo = Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo());
    public static final Color colorTexto = Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto());
    public static final Color colorBoton = Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton());
    public static final Color colorCajaTexto = Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto());
    public static final Color colorEnlace = Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace());

    public CrashDetectorGUI(List<Consola> consolas, StringBuilder contenidoInforme, Instant tiempoFallo) {
        this.contenidoInforme = contenidoInforme;
        this.tiempoFallo = tiempoFallo;
        this.consolas = consolas;
        inicializarInterfaz();
    }

    private void inicializarInterfaz() {
        // Generar informe HTML si es necesario
        if (MonitorDePID.local == null) {
            MonitorDePID.local = GeneradorDeInformacion.generarLocal(consolas, contenidoInforme, tiempoFallo).getAbsolutePath();
        }

        setTitle("CrashDetector");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Visor de contenido HTML
        JEditorPane visorHtml = new JEditorPane();
        visorHtml.setContentType("text/html");
        visorHtml.setEditable(false);
        visorHtml.setBackground(colorCajaTexto);
        visorHtml.setForeground(colorEnlace);
        visorHtml.setFont(new Font("Consolas", Font.PLAIN, 14));

        try {
            visorHtml.setText(new String(Files.readAllBytes(Paths.get(MonitorDePID.local))));
        } catch (IOException e) {
            visorHtml.setText("<html><body style='color:#ff6b6b'>Problema con el Informe: " + e.getMessage() + "</body></html>");
        }

        visorHtml.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (Exception ex) {
                    CrashDetectorLogger.logException(ex);
                }
            }
        });


        JScrollPane panelDesplazamiento = new JScrollPane(visorHtml);
        panelDesplazamiento.setBorder(BorderFactory.createEmptyBorder());
        panelDesplazamiento.getViewport().setBackground(colorCajaTexto);
        panelDesplazamiento.getViewport().setViewPosition(new Point(0, 0));
        
        // Panel inferior para controles
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.setBackground(colorFondo);
        panelInferior.setBorder(new EmptyBorder(15, 40, 15, 40)); 

        // Panel principal para botones
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BoxLayout(panelControles, BoxLayout.Y_AXIS));
        panelControles.setBackground(colorFondo);
     
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setBackground(colorFondo);
        
        // Botones
        JButton botonAnalisis = new JButton(MonitorDePID.idioma.analisisAvanzado());
        JButton botonCompartir = new JButton(MonitorDePID.idioma.texto_de_buton_compartir_enlance());
        JButton botonQuickFix = new JButton("QuickFix");
        
        // Configuración de botones
        estilizarBoton(botonAnalisis);
        estilizarBoton(botonCompartir);
        estilizarBoton(botonQuickFix);
        botonQuickFix.setEnabled(false);

        // Agregar botones al panel
        panelBotones.add(botonAnalisis);
        panelBotones.add(botonCompartir);
        panelBotones.add(botonQuickFix);

        // Agregar paneles al contenedor principal
        panelControles.add(panelBotones);
        panelControles.add(Box.createVerticalStrut(5)); 

        // Agregar acciones
        botonAnalisis.addActionListener(e -> new AnalisisAvanzadoGUI().setVisible(true));
        botonCompartir.addActionListener(e -> new DialogoCompartir(this, contenidoInforme, tiempoFallo).setVisible(true));

        // Agregar componentes a la ventana
        add(panelDesplazamiento, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        panelInferior.add(panelControles, BorderLayout.CENTER);
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
        boton.setMaximumSize(new Dimension(200, 40));
    }
    
    
    public static boolean esMac(){
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }
    
}