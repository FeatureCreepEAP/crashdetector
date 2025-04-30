package com.asbestosstar.crashdetectormc.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetectormc.Consola;
import com.asbestosstar.crashdetectormc.CrashDetectorLogger;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class NoRegistroDeLauncher extends JDialog {
    private final JTextArea area_de_texto;
    public static File cd_launcherlog = new File("cd_launcherlog");
    public Instant instant;
    
    public NoRegistroDeLauncher(Instant instant) {
    	this.instant=instant;
        setTitle("Registro de Launcher No Encontrado");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel principal = new JPanel();
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));
        add(principal, BorderLayout.CENTER);

        JTextArea descripcion = new JTextArea();
        descripcion.setEditable(false);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setText(
                MonitorDePID.idioma.noRegistroDeLauncher()
        );
        descripcion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        principal.add(descripcion);

        // Image loading
        JLabel imagen = new JLabel("", SwingConstants.CENTER);
        imagen.setPreferredSize(new Dimension(500, 200));
        imagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        URL imageUrl = getClass().getResource("/imagenes/registros_de_launcher.png");
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaledImage = icon.getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(scaledImage));
        } else {
            imagen.setText("Imagen no encontrada");
            CrashDetectorLogger.log("ERROR: Imagen de registros no encontrada en el JAR");
        }
        principal.add(imagen);

        area_de_texto = new JTextArea(15, 50);
        area_de_texto.setLineWrap(true);
        area_de_texto.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(area_de_texto);
        scrollPane.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.pegaLosRegistrosAqui()));
        principal.add(scrollPane);

        // Buttons panel
        JPanel butonDePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton guardarButton = new JButton(MonitorDePID.idioma.guardarYCerrar());
        JButton omitirboton = new JButton(MonitorDePID.idioma.omitirYCerrar());

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (FileWriter writer = new FileWriter(cd_launcherlog)) {
                    writer.write(area_de_texto.getText());
                    CrashDetectorLogger.log("Archivo cd_launcherlog.txt guardado");
                    Consola cons = new Consola(cd_launcherlog.toPath());
                    cons.finalizarContento(instant);
                    MonitorDePID.consolas.add(cons);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, 
                        "Error al guardar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    dispose();
                }
            }
        });

        omitirboton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        butonDePanel.add(guardarButton);
        butonDePanel.add(omitirboton);
        principal.add(butonDePanel);

        setVisible(true);
    }

}