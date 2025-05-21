package com.asbestosstar.crashdetectormc.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.asbestosstar.crashdetector.EscanerMCreator;
import com.asbestosstar.crashdetector.MonitorDePID;

public class EscanerMCreatorGUI extends JFrame {
    private JTextArea resultadosArea;
    private JLabel estadoLabel;
    private JButton escanearBtn;

    public EscanerMCreatorGUI() {
        setTitle("Escaner MCreator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(CrashDetectorGUI.colorFondo);
        setLayout(new BorderLayout(10, 10));

        // Panel principal
        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelContenido.setBackground(CrashDetectorGUI.colorFondo);

        // Descripción
        JLabel descripcion = new JLabel("<html><div style='text-align: center;'>" 
            + MonitorDePID.idioma.descripcionEscanerMCreator() 
            + "</div></html>");
        descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descripcion.setForeground(CrashDetectorGUI.colorTexto);
        descripcion.setHorizontalAlignment(JLabel.CENTER);

        // Área de resultados
        resultadosArea = new JTextArea();
        resultadosArea.setEditable(false);
        resultadosArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        resultadosArea.setForeground(new Color(50, 50, 50)); // Texto gris oscuro
        resultadosArea.setBackground(Color.WHITE); // Fondo blanco
        resultadosArea.setLineWrap(true);
        resultadosArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(resultadosArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Estado
        estadoLabel = new JLabel(" ");
        estadoLabel.setForeground(new Color(150, 150, 150));
        estadoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Botón de escaneo (instanciado como atributo de clase)
        escanearBtn = new JButton(MonitorDePID.idioma.escanear());
        escanearBtn.setBackground(CrashDetectorGUI.colorBoton);
        escanearBtn.setForeground(Color.GREEN); 
        escanearBtn.setFocusPainted(false);
        escanearBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        escanearBtn.setPreferredSize(new Dimension(200, 50));
        escanearBtn.addActionListener(e -> iniciarEscaneo());

        // Organización de componentes
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBackground(CrashDetectorGUI.colorFondo);
        panelSuperior.add(descripcion, BorderLayout.CENTER);
        panelSuperior.add(escanearBtn, BorderLayout.PAGE_END);

        panelContenido.add(panelSuperior, BorderLayout.NORTH);
        panelContenido.add(scroll, BorderLayout.CENTER);
        panelContenido.add(estadoLabel, BorderLayout.SOUTH);

        add(panelContenido);
    }

    private void iniciarEscaneo() {
        resultadosArea.setText("");
        estadoLabel.setText("cargando");
        escanearBtn.setEnabled(false);

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return EscanerMCreator.obtainerMCreatorMods();
            }

            @Override
            protected void done() {
                try {
                    String resultado = get();
                    resultadosArea.setText(resultado);
                    estadoLabel.setText("esta completdo");
                } catch (Exception e) {
                    resultadosArea.setText("Error: " + e.getMessage());
                    estadoLabel.setText("error");
                } finally {
                    escanearBtn.setEnabled(true);
                }
            }
        }.execute();
    }
}