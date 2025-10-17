package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.asbestosstar.crashdetector.EscanerMCreator;
import com.asbestosstar.crashdetector.MonitorDePID;

public class EscanerMCreatorGUIRosemiLoveLock extends JFrame  implements BotonDeBarraLateralDerecha{
    private JTextArea areaResultados;
    private JLabel etiquetaEstado;
    private JButton botonEscanear;

    public EscanerMCreatorGUIRosemiLoveLock() {
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
        JLabel etiquetaDescripcion = new JLabel("<html><div style='text-align: center;'>" 
            + MonitorDePID.idioma.descripcionEscanerMCreator() 
            + "</div></html>");
        etiquetaDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        etiquetaDescripcion.setForeground(CrashDetectorGUI.colorTexto);
        etiquetaDescripcion.setHorizontalAlignment(JLabel.CENTER);

        // Área de resultados
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Consolas", Font.PLAIN, 12));
        areaResultados.setForeground(new Color(50, 50, 50));
        areaResultados.setBackground(Color.WHITE);
        areaResultados.setLineWrap(true);
        areaResultados.setWrapStyleWord(true);

        JScrollPane panelDesplazamiento = new JScrollPane(areaResultados);
        panelDesplazamiento.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JLabel etiquetaImagen = null;
        File archivoImagen = MonitorDePID.carpeta.resolve("imagenes/rosemi.png").toFile();
        
        // Verificar si el archivo de imagen existe en el sistema del usuario
        if (archivoImagen.exists()) {
            ImageIcon iconoOriginal = new ImageIcon(archivoImagen.getPath());
            // Verificar si la imagen se cargó correctamente
            if (iconoOriginal.getIconWidth() > 0) {
                // Escalar imagen a 200x112
                Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                    200, 112, Image.SCALE_SMOOTH);
                ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
                
                // Configurar etiqueta de imagen
                etiquetaImagen = new JLabel(iconoEscalado);
                etiquetaImagen.setPreferredSize(new Dimension(200, 112));
                etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);
                etiquetaImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        }
        // ========================================

        // Crear contenedor para área de texto (y imagen si existe)
        JPanel panelContenidoConImagen = new JPanel();
        panelContenidoConImagen.setLayout(new BoxLayout(panelContenidoConImagen, BoxLayout.Y_AXIS));
        panelContenidoConImagen.setBackground(CrashDetectorGUI.colorFondo);
        panelContenidoConImagen.add(panelDesplazamiento);
        
        // Agregar imagen SOLO si existe (evita espacio vacío)
        if (etiquetaImagen != null) {
            panelContenidoConImagen.add(etiquetaImagen);
        }

        // Estado
        etiquetaEstado = new JLabel(" ");
        etiquetaEstado.setForeground(new Color(150, 150, 150));
        etiquetaEstado.setHorizontalAlignment(JLabel.CENTER);

        // Botón de escaneo
        botonEscanear = new JButton(MonitorDePID.idioma.escanear());
        botonEscanear.setBackground(CrashDetectorGUI.colorBoton);
        botonEscanear.setForeground(Color.GREEN); 
        botonEscanear.setFocusPainted(false);
        botonEscanear.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botonEscanear.setPreferredSize(new Dimension(200, 50));
        botonEscanear.addActionListener(e -> iniciarEscaneo());

        // Organización de componentes
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBackground(CrashDetectorGUI.colorFondo);
        panelSuperior.add(etiquetaDescripcion, BorderLayout.CENTER);
        panelSuperior.add(botonEscanear, BorderLayout.PAGE_END);

        panelContenido.add(panelSuperior, BorderLayout.NORTH);
        panelContenido.add(panelContenidoConImagen, BorderLayout.CENTER);
        panelContenido.add(etiquetaEstado, BorderLayout.SOUTH);

        add(panelContenido);
    }

    private void iniciarEscaneo() {
        areaResultados.setText("");
        etiquetaEstado.setText("cargando");
        botonEscanear.setEnabled(false);

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return EscanerMCreator.obtainerMCreatorMods();
            }

            @Override
            protected void done() {
                try {
                    String resultado = get();
                    areaResultados.setText(resultado);
                    etiquetaEstado.setText("esta completdo");
                } catch (Exception e) {
                    areaResultados.setText("Error: " + e.getMessage());
                    etiquetaEstado.setText("error");
                } finally {
                    botonEscanear.setEnabled(true);
                }
            }
        }.execute();
    }

	@Override
	public void init() {
		this.setVisible(true);
	}

	@Override
	public String etiquetaDelBoton() {
		return MonitorDePID.idioma.escanearDeMCreator();
	}
}