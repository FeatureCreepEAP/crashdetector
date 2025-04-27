package com.asbestosstar.crashdetectormc.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;

import com.asbestosstar.crashdetectormc.Config;
import com.asbestosstar.crashdetectormc.Consola;
import com.asbestosstar.crashdetectormc.CrashDetectorLogger;
import com.asbestosstar.crashdetectormc.GeneradorDeInformacion;
import com.asbestosstar.crashdetectormc.MonitorDePID;

public class CrashDetectorGUI extends JFrame {

    private final StringBuilder contenidoInforme;
    private final Instant tiempoFallo;
    private final List<Consola> consolas;

    // Colores configurables
    private final Color colorFondo = Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo());
    private final Color colorTexto = Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto());
    private final Color colorBoton = Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton());
    private final Color colorCajaTexto = Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto());
    private final Color colorEnlace = Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace());

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

        // Panel inferior para controles
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.setBackground(colorFondo);
        panelInferior.setBorder(new EmptyBorder(0, 40, 20, 40)); // Remove gap above the image

        // Subpanel para la imagen y los botones
        JPanel subPanelControles = new JPanel();
        subPanelControles.setLayout(new BorderLayout());
        subPanelControles.setBackground(colorFondo);

        // Espacio para la imagen
        JLabel imagen = cargarImagenDesdeRecurso("/imagenes/gura.png");
        imagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        subPanelControles.add(imagen, BorderLayout.NORTH); // Imagen arriba

        // Panel para el botón de compartir y la descripción
        JPanel panelBotonYDescripcion = new JPanel();
        panelBotonYDescripcion.setLayout(new BoxLayout(panelBotonYDescripcion, BoxLayout.Y_AXIS));
        panelBotonYDescripcion.setBackground(colorFondo);

        // Botón de compartir y descripción
        JButton botonCompartir = new JButton(MonitorDePID.idioma.texto_de_buton_compartir_enlance());
        estilizarBoton(botonCompartir);
        panelBotonYDescripcion.add(botonCompartir);
        panelBotonYDescripcion.add(Box.createVerticalStrut(8));

        JLabel descripcionCompartir = new JLabel(MonitorDePID.idioma.texto_debajo_de_buton_compartir_enlance());
        estilizarEtiqueta(descripcionCompartir);
        panelBotonYDescripcion.add(descripcionCompartir);
        panelBotonYDescripcion.add(Box.createVerticalStrut(10));

        // Caja de texto para la URL
        JTextField cajaUrl = new JTextField();
        cajaUrl.setEditable(false);
        cajaUrl.setBackground(colorCajaTexto);
        cajaUrl.setForeground(colorEnlace);
        cajaUrl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cajaUrl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        cajaUrl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelBotonYDescripcion.add(cajaUrl);

        // Acción al presionar el botón de compartir
        botonCompartir.addActionListener(evento -> {
            if (MonitorDePID.enlance == null) {
                MonitorDePID.enlance = GeneradorDeInformacion.compartir(consolas, contenidoInforme, tiempoFallo);
            }
            cajaUrl.setText(MonitorDePID.enlance); // Mostrar la URL compartida en la caja de texto
            try {
                Desktop.getDesktop().browse(new URL(MonitorDePID.enlance).toURI());
            } catch (IOException | URISyntaxException e) {
                CrashDetectorLogger.logException(e);
            }
        });

        // Agregar el panel de botón y descripción al subpanel
        subPanelControles.add(panelBotonYDescripcion, BorderLayout.CENTER); // Botón y descripción abajo

        // Agregar el subpanel al panel inferior
        panelInferior.add(subPanelControles, BorderLayout.CENTER);

        // Diseño del layout
        add(panelDesplazamiento, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Cargar una imagen desde un recurso del classloader.
     *
     * @param ruta La ruta del recurso (ej.: "/imagenes/imagen.png").
     * @return Un JLabel con la imagen cargada o un texto alternativo si falla.
     */
    private JLabel cargarImagenDesdeRecurso(String ruta) {
        ImageIcon icono = null;
        try {
            InputStream flujoEntrada = getClass().getResourceAsStream(ruta);
            if (flujoEntrada != null) {
                icono = new ImageIcon(javax.imageio.ImageIO.read(flujoEntrada));
            }
        } catch (IOException e) {
            CrashDetectorLogger.logException(e);
        }

        // Si no se puede cargar la imagen, mostrar un texto alternativo
        if (icono == null) {
            return new JLabel("Imagen no encontrada");
        }

        // Escalar la imagen mientras mantiene la proporción
        int anchoOriginal = icono.getIconWidth();
        int altoOriginal = icono.getIconHeight();
        int nuevoAncho = 80; // Nuevo ancho deseado
        int nuevoAlto = (int) ((double) nuevoAncho / anchoOriginal * altoOriginal); // Mantener la proporción

        Image imagenEscalada = icono.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(imagenEscalada));
    }

    private void estilizarBoton(JButton boton) {
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setBackground(colorBoton);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setMaximumSize(new Dimension(250, 40));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void estilizarEtiqueta(JLabel etiqueta) {
        etiqueta.setAlignmentX(Component.CENTER_ALIGNMENT);
        etiqueta.setForeground(colorTexto);
        etiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }
}
