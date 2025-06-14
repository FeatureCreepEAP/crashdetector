package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;

public class ConfigPanel extends JPanel {
    public CrashDetectorGUI cdgui;
    private JTabbedPane tabbedPane;
    
    public ConfigPanel(CrashDetectorGUI cdgui) {
        this.cdgui = cdgui;
        setLayout(new BorderLayout());
        setBackground(CrashDetectorGUI.colorFondo);
        
        // Create pestañas con etiquetas personalizadas
        tabbedPane = new JTabbedPane();
        
        Color tabLabelColor = CrashDetectorGUI.esMac() ? null : CrashDetectorGUI.colorTexto;

        
        
        // Pestaña "Inicio de Juego/App"
        JLabel incicio_del_juego = new JLabel(MonitorDePID.idioma.inicioApp());
        if (tabLabelColor != null) {
        	incicio_del_juego.setForeground(tabLabelColor);
        }
        tabbedPane.addTab("", null, tabDelJuego(), MonitorDePID.idioma.tooltip());
        tabbedPane.setTabComponentAt(0, incicio_del_juego);
        
        // Pestaña "Ajustes CrashDetector"
        JLabel cdajustes = new JLabel(MonitorDePID.idioma.adjustesCrashDetector());
        if (tabLabelColor != null) {
        	cdajustes.setForeground(tabLabelColor);
        }
        tabbedPane.addTab("", null, tabCrashDetector(), MonitorDePID.idioma.tooltip());
        tabbedPane.setTabComponentAt(1, cdajustes);
        
        // Pestaña "Confidencialidad"
        JLabel confidencialidad = new JLabel(MonitorDePID.idioma.confidencialidad());
        if (tabLabelColor != null) {
        	confidencialidad.setForeground(tabLabelColor);
        }
        tabbedPane.addTab("", null, tabConfidentialidad(), MonitorDePID.idioma.tooltip());
        tabbedPane.setTabComponentAt(2, confidencialidad);
        
        add(tabbedPane, BorderLayout.CENTER);

        // --- Nuevo: Botón "Guardar" ---
        JButton guardarButon = new JButton(MonitorDePID.idioma.guardarYCerrar());
        
        if(CrashDetectorGUI.esMac()) {
        	guardarButon.setContentAreaFilled(false);
        }else {
        	guardarButon.setForeground(CrashDetectorGUI.colorTexto);
            guardarButon.setBackground(CrashDetectorGUI.colorBoton);
            guardarButon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        guardarButon.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }
        guardarButon.setFocusPainted(false);
        
        guardarButon.addActionListener(e -> {
        	Config.obtenerInstancia().guardar();
        	cdgui.volver();
        });

        // Panel para el botón
        JPanel butonPanel = new JPanel(new BorderLayout());
        if (!CrashDetectorGUI.esMac()) {
            butonPanel.setBackground(CrashDetectorGUI.colorFondo);
        } else {
            butonPanel.setOpaque(false); // Let macOS decide
        }

        butonPanel.add(guardarButon, BorderLayout.CENTER);

        // Añadir el botón al sur del panel principal
        add(butonPanel, BorderLayout.SOUTH);
    }

    private JPanel tabDelJuego() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CrashDetectorGUI.colorFondo);

        JLabel directorioLabel = new JLabel("Proximente");
        if (!CrashDetectorGUI.esMac()) {
            directorioLabel.setForeground(CrashDetectorGUI.colorTexto);
        }
        directorioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(directorioLabel, BorderLayout.CENTER);
        return panel;
    }
    
    
    
 // Campos de texto para ajustes de CrashDetector
    private JTextField sitioDeInformesField;
    private JTextField colorFondoField;
    private JTextField colorTextoField;
    private JTextField colorBotonField;
    private JTextField colorCajaTextoField;
    private JTextField colorEnlaceField;
    private JTextField colorTitulosConsolasField;
    private JTextField colorErrorField;
    private JTextField colorAdvertenciaField;
    private JTextField colorInfoField;
    private JTextField colorTituloField;
    private JTextField colorEnlaceTextoField;

    private JPanel tabCrashDetector() {
        JPanel panel = new JPanel();
        panel.setBackground(CrashDetectorGUI.colorFondo);
        panel.setLayout(new GridLayout(0, 2, 5, 5));

        Config config = Config.obtenerInstancia();

        // Helper to reduce redundancy
        boolean isMac = CrashDetectorGUI.esMac();
        Color color_de_texto_de_gui = CrashDetectorGUI.colorTexto;

        // Campo: Sitio de informes
        JLabel labelSitio = new JLabel(MonitorDePID.idioma.sitoDeLogging());
        labelSitio.setForeground(color_de_texto_de_gui);
        panel.add(labelSitio);
        sitioDeInformesField = new JTextField(config.obtenerSitoDeInformes());
        if (!isMac) {
            sitioDeInformesField.setBackground(CrashDetectorGUI.colorCajaTexto);
            sitioDeInformesField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(sitioDeInformesField);

        // Campo: Color de fondo
        JLabel labelFondo = new JLabel(MonitorDePID.idioma.colorFondo());
        labelFondo.setForeground(color_de_texto_de_gui);
        panel.add(labelFondo);
        colorFondoField = new JTextField(config.obtenerColorFondo());
        if (!isMac) {
            colorFondoField.setBackground(CrashDetectorGUI.colorCajaTexto);
            colorFondoField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(colorFondoField);

        // Campo: Color de texto
        JLabel labelTexto = new JLabel(MonitorDePID.idioma.colorTexto());
        labelTexto.setForeground(color_de_texto_de_gui);
        panel.add(labelTexto);
        colorTextoField = new JTextField(config.obtenerColorTexto());
        if (!isMac) {
            colorTextoField.setBackground(CrashDetectorGUI.colorCajaTexto);
            colorTextoField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(colorTextoField);

        // Campo: Color de botón
        JLabel labelBoton = new JLabel(MonitorDePID.idioma.colorBoton());
        labelBoton.setForeground(color_de_texto_de_gui);
        panel.add(labelBoton);
        colorBotonField = new JTextField(config.obtenerColorBoton());
        if (!isMac) {
            colorBotonField.setBackground(CrashDetectorGUI.colorCajaTexto);
            colorBotonField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(colorBotonField);

        // Campo: Color de caja de texto
        JLabel labelCajaTexto = new JLabel(MonitorDePID.idioma.colorCajaTexto());
        labelCajaTexto.setForeground(color_de_texto_de_gui);
        panel.add(labelCajaTexto);
        colorCajaTextoField = new JTextField(config.obtenerColorCajaTexto());
        if (!isMac) {
            colorCajaTextoField.setBackground(CrashDetectorGUI.colorCajaTexto);
            colorCajaTextoField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(colorCajaTextoField);

        // Campo: Color de enlace
        JLabel labelEnlace = new JLabel(MonitorDePID.idioma.colorEnlace());
        labelEnlace.setForeground(color_de_texto_de_gui);
        panel.add(labelEnlace);
        colorEnlaceField = new JTextField(config.obtenerColorEnlace());
        if (!isMac) {
            colorEnlaceField.setBackground(CrashDetectorGUI.colorCajaTexto);
            colorEnlaceField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(colorEnlaceField);

        // Campo: Color de títulos de consolas
        JLabel labelTitulosConsolas = new JLabel(MonitorDePID.idioma.colorTitulosConsolas());
        labelTitulosConsolas.setForeground(color_de_texto_de_gui);
        panel.add(labelTitulosConsolas);
        colorTitulosConsolasField = new JTextField(config.obtenerColorDeTitulosDeConsolas());
        if (!isMac) {
            colorTitulosConsolasField.setBackground(CrashDetectorGUI.colorCajaTexto);
            colorTitulosConsolasField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(colorTitulosConsolasField);

        // Campo: Color de error
        JLabel labelError = new JLabel(MonitorDePID.idioma.colorError());
        labelError.setForeground(color_de_texto_de_gui);
        panel.add(labelError);
        colorErrorField = new JTextField(config.obtenerColorError());
        if (!isMac) {
            colorErrorField.setBackground(CrashDetectorGUI.colorCajaTexto);
            colorErrorField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(colorErrorField);

        // Campo: Color de advertencia
        JLabel labelAdvertencia = new JLabel(MonitorDePID.idioma.colorAdvertencia());
        labelAdvertencia.setForeground(color_de_texto_de_gui);
        panel.add(labelAdvertencia);
        colorAdvertenciaField = new JTextField(config.obtenerColorAdvertencia());
        if (!isMac) {
            colorAdvertenciaField.setBackground(CrashDetectorGUI.colorCajaTexto);
            colorAdvertenciaField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(colorAdvertenciaField);

        // Campo: Color de información
        JLabel labelInfo = new JLabel(MonitorDePID.idioma.colorInfo());
        labelInfo.setForeground(color_de_texto_de_gui);
        panel.add(labelInfo);
        colorInfoField = new JTextField(config.obtenerColorInfo());
        if (!isMac) {
            colorInfoField.setBackground(CrashDetectorGUI.colorCajaTexto);
            colorInfoField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(colorInfoField);

        // Campo: Color de título
        JLabel labelTitulo = new JLabel(MonitorDePID.idioma.colorTitulo());
        labelTitulo.setForeground(color_de_texto_de_gui);
        panel.add(labelTitulo);
        colorTituloField = new JTextField(config.obtenerColorTitulo());
        if (!isMac) {
            colorTituloField.setBackground(CrashDetectorGUI.colorCajaTexto);
            colorTituloField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(colorTituloField);

        // Campo: Color de enlace texto
        JLabel labelEnlaceTexto = new JLabel(MonitorDePID.idioma.colorEnlaceTexto());
        labelEnlaceTexto.setForeground(color_de_texto_de_gui);
        panel.add(labelEnlaceTexto);
        colorEnlaceTextoField = new JTextField(config.obtenerColorEnlace());
        if (!isMac) {
            colorEnlaceTextoField.setBackground(CrashDetectorGUI.colorCajaTexto);
            colorEnlaceTextoField.setForeground(CrashDetectorGUI.colorTexto);
        }
        panel.add(colorEnlaceTextoField);

        return panel;
    }

    private JPanel tabConfidentialidad() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CrashDetectorGUI.colorFondo);


        // JTextArea para texto multilinea
        JTextArea areaTexto = new JTextArea(MonitorDePID.idioma.arco());
        areaTexto.setLineWrap(true); // Activar ajuste automático de líneas
        areaTexto.setWrapStyleWord(true); // Romper líneas por palabras
        areaTexto.setEditable(false); // No permitir edición
        areaTexto.setOpaque(false); // Para que se respete el fondo del panel
        areaTexto.setBackground(CrashDetectorGUI.colorFondo);
        areaTexto.setForeground(CrashDetectorGUI.colorTexto);
        
        if (!CrashDetectorGUI.esMac()) {
            areaTexto.setForeground(CrashDetectorGUI.colorTexto);
        }

        JPanel panelTexto = new JPanel(new BorderLayout());
        panelTexto.setBackground(CrashDetectorGUI.colorFondo);
        panelTexto.add(areaTexto, BorderLayout.CENTER);

        panelTexto.setMaximumSize(new Dimension(600, Short.MAX_VALUE)); // Máximo 600px de ancho
        panelTexto.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente

        panel.add(panelTexto, BorderLayout.CENTER);

        JPanel placeholderImagen = new JPanel();
        placeholderImagen.setBackground(CrashDetectorGUI.colorFondo);
        placeholderImagen.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaciado superior e inferior

        JLabel etiquetaImagen = new JLabel();
        etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);

        try {
            ImageIcon iconoImagen = new ImageIcon("crash_detector/imagenes/profeco.jpg");
            etiquetaImagen.setIcon(iconoImagen);
        } catch (Exception e) {
            etiquetaImagen.setText("Error al cargar la imagen");
            if (!CrashDetectorGUI.esMac()) {
                etiquetaImagen.setForeground(CrashDetectorGUI.colorTexto);
            }
        }

        placeholderImagen.add(etiquetaImagen);
        panel.add(placeholderImagen, BorderLayout.SOUTH);
        return panel;
    }
}