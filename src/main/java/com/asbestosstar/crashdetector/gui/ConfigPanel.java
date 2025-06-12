package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.asbestosstar.crashdetector.MonitorDePID;

public class ConfigPanel extends JPanel {
    public CrashDetectorGUI cdgui;
    private JTabbedPane tabbedPane;
    
    public ConfigPanel(CrashDetectorGUI cdgui) {
        this.cdgui = cdgui;
        setLayout(new BorderLayout());
        setBackground(CrashDetectorGUI.colorFondo);
        
        // Crear pestañas con etiquetas personalizadas
        tabbedPane = new JTabbedPane();
        
        // Pestaña "Inicio de Juego/App"
        JLabel minecraftLabel = new JLabel("Inicio de Juego/App");
        minecraftLabel.setForeground(CrashDetectorGUI.colorTexto);
        tabbedPane.addTab("", null, tabDelJuego(), "Tooltip");
        tabbedPane.setTabComponentAt(0, minecraftLabel);
        
        // Pestaña "Ajustes CrashDetector"
        JLabel tlauncherLabel = new JLabel("Ajustes CrashDetector");
        tlauncherLabel.setForeground(CrashDetectorGUI.colorTexto);
        tabbedPane.addTab("", null, tabCrashDetector(), "Tooltip");
        tabbedPane.setTabComponentAt(1, tlauncherLabel);
        
        // Pestaña "Confidencialidad"
        JLabel privacyLabel = new JLabel("Confidencialidad");
        privacyLabel.setForeground(CrashDetectorGUI.colorTexto);
        tabbedPane.addTab("", null, tabConfidentialidad(), "Tooltip");
        tabbedPane.setTabComponentAt(2, privacyLabel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Pestaña con texto estático
    private JPanel tabDelJuego() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CrashDetectorGUI.colorFondo);
        
        JLabel directorioLabel = new JLabel("Proximente");
        directorioLabel.setForeground(CrashDetectorGUI.colorTexto);
        directorioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        panel.add(directorioLabel, BorderLayout.CENTER);
        return panel;
    }

    // Pestaña con texto estático
    private JPanel tabCrashDetector() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CrashDetectorGUI.colorFondo);
        
        JLabel resolucionLabel = new JLabel("Proximente");
        resolucionLabel.setForeground(CrashDetectorGUI.colorTexto);
        resolucionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        panel.add(resolucionLabel, BorderLayout.CENTER);
        return panel;
    }

    // Pestaña con un solo campo de texto
    private JPanel tabConfidentialidad() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CrashDetectorGUI.colorFondo);
        
        JTextField confidencialidadField = new JTextField(MonitorDePID.idioma.arco());
        confidencialidadField.setForeground(Color.BLACK);  // Texto negro
        confidencialidadField.setBackground(CrashDetectorGUI.colorCajaTexto);
        confidencialidadField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        panel.add(confidencialidadField, BorderLayout.CENTER);
        return panel;
    }
}