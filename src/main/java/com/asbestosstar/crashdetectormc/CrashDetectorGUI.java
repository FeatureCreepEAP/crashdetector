package com.asbestosstar.crashdetectormc;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class CrashDetectorGUI extends JFrame {

    private final StringBuilder reportContent;
    private final Instant crashTime;

    public CrashDetectorGUI(StringBuilder reportContent, Instant crashTime) {
        this.reportContent = reportContent;
        this.crashTime = crashTime;
        initializeUI();
    }

    private void initializeUI() {
        // Generate HTML report if needed
        if (MonitorDePID.local == null) {
            MonitorDePID.local = GeneradorDeInformacion.generarLocal(reportContent, crashTime).getAbsolutePath();
        }

        setTitle("Crash Detector");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel for controls
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(40, 40, 40));
        topPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Main title
        JLabel titleLabel = new JLabel(MonitorDePID.idioma.texto_de_gui());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(20));

        // Share button and description
        JButton shareButton = new JButton(MonitorDePID.idioma.texto_de_buton_compartir_enlance());
        styleButton(shareButton);
        topPanel.add(shareButton);
        topPanel.add(Box.createVerticalStrut(8));

        JLabel shareDesc = new JLabel(MonitorDePID.idioma.texto_debajo_de_buton_compartir_enlance());
        styleLabel(shareDesc);
        topPanel.add(shareDesc);

        // HTML content viewer
        JEditorPane htmlViewer = new JEditorPane();
        htmlViewer.setContentType("text/html");
        htmlViewer.setEditable(false);
        htmlViewer.setBackground(new Color(30, 30, 30));
        htmlViewer.setForeground(Color.LIGHT_GRAY);
        htmlViewer.setFont(new Font("Consolas", Font.PLAIN, 14));

        try {
            htmlViewer.setText(new String(Files.readAllBytes(Paths.get(MonitorDePID.local))));
        } catch (IOException e) {
            htmlViewer.setText("<html><body style='color:#ff6b6b'>" 
                + "Error loading report: " + e.getMessage() 
                + "</body></html>");
        }

        JScrollPane scrollPane = new JScrollPane(htmlViewer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(30, 30, 30));

        // Layout setup
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Action listener
        shareButton.addActionListener(this::compartirInforme);
    }

    private void compartirInforme(ActionEvent evento) {
//        try {
//            if (MonitorDePID.local != null) {
//                boolean success = SubidorDeInformes.subirInforme(MonitorDePID.local);
//                if (success) {
//                    JOptionPane.showMessageDialog(
//                        this,
//                        MonitorDePID.idioma.texto_compartir_exitoso(),
//                        MonitorDePID.idioma.titulo_exito(),
//                        JOptionPane.INFORMATION_MESSAGE
//                    );
//                }
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(
//                this,
//                e.getMessage(),
//                MonitorDePID.idioma.titulo_error(),
//                JOptionPane.ERROR_MESSAGE
//            );
//        }
    }

    
    
    
    private void styleButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(70, 70, 140));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setMaximumSize(new Dimension(250, 40));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void styleLabel(JLabel label) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(new Color(200, 200, 200));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }
}