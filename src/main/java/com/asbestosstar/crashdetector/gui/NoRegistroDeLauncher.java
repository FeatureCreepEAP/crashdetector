package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;

public class NoRegistroDeLauncher extends JDialog {
    private final JTextArea area_de_texto;
    public static File cd_launcherlog = new File("cd_launcherlog");
    public Instant instant;
    
    /**
     * Muestra un diálogo modal para registrar registros del launcher
     * @param blanco - JFrame padre (ventana principal)
     * @param instant - Marca de tiempo del evento
     */
    public NoRegistroDeLauncher(JFrame blanco, Instant instant) {
        this.instant = instant;
        setTitle("Registro de Launcher No Encontrado");
        setSize(600, 650); // Aumentado de 500 a 650 para mostrar todo sin scroll
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(blanco);
        setLayout(new BorderLayout(10, 10));
        setModal(true); // Hacer el diálogo modal

        JPanel principal = new JPanel();
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));
        principal.setAlignmentX(JPanel.LEFT_ALIGNMENT);
        add(principal, BorderLayout.CENTER);

        // Texto descriptivo
        JTextArea descripcion = new JTextArea();
        descripcion.setEditable(false);
        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);
        descripcion.setText(MonitorDePID.idioma.noRegistroDeLauncher());
        descripcion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        principal.add(descripcion);

        // Imagen centrada
        JLabel imagen = new JLabel("", SwingConstants.CENTER); // Centrada
        imagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        URL imageUrl = getClass().getResource("/imagenes/registros_de_launcher_prism.png");
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaledImage = icon.getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(scaledImage));
        } else {
            imagen.setText("Imagen no encontrada");
            CrashDetectorLogger.log("ERROR: Imagen de registros no encontrada en el JAR");
        }

        // Panel para imagen con ancho completo y centrado
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imagen, BorderLayout.CENTER);
        imagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220)); // Altura suficiente para imagen + borde
        principal.add(imagePanel);

        // Área de texto para registros
        area_de_texto = new JTextArea(15, 50);
        area_de_texto.setLineWrap(true);
        area_de_texto.setWrapStyleWord(true);

        // Scroll con título alineado a la izquierda
        JScrollPane scrollPane = new JScrollPane(area_de_texto);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            MonitorDePID.idioma.pegaLosRegistrosAqui()
        );
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        scrollPane.setBorder(titledBorder);
        principal.add(scrollPane);

        // Panel de botones
        JPanel butonDePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JButton guardarButton = new JButton(MonitorDePID.idioma.guardarYCerrar());
        JButton omitirboton = new JButton(MonitorDePID.idioma.omitirYCerrar());

        // Acción: Guardar registros
        guardarButton.addActionListener(e -> {
            try (FileOutputStream fos = new FileOutputStream(cd_launcherlog);
                 FileChannel channel = fos.getChannel()) {
                
            	String contento = area_de_texto.getText();
            	
                // Escribir el contenido del JTextArea al archivo
                byte[] bytes = contento.getBytes(StandardCharsets.UTF_8);
                fos.write(bytes);
                
                // Forzar escritura física al disco
                //channel.force(true);
                
                CrashDetectorLogger.log("Archivo cd_launcherlog.txt guardado y forzado al disco");
                
                Consola cons = new Consola(cd_launcherlog.toPath());
                cons.finalizarContentoInyectado(contento);
                MonitorDePID.consolas.add(cons);
                MonitorDePID.consola_de_launcher_inyectado=true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, 
                    "Error al guardar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                dispose(); // Cerrar diálogo solo después de escritura segura
            }
        });

        
        

        // Acción: Omitir
        omitirboton.addActionListener(e -> dispose());

        butonDePanel.add(guardarButton);
        butonDePanel.add(omitirboton);
        principal.add(butonDePanel);

        // Mostrar diálogo
        setVisible(true);
    }
}