package com.asbestosstar.crashdetector.gui.tipos.cfr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Implementación con apariencia temática de Sakura Riddle.
 * Muestra el código descompilado a la izquierda y un retrato de Sakura a la derecha.
 */
public class CfrSakuraRiddle extends CfrBase {

    public static final String ID = "sakura_riddle";
    private static final long serialVersionUID = 1L;

    // Colores temáticos
    public ConfigColor colorFondo;
    public ConfigColor colorTexto;
    public ConfigColor colorBorde;
    public ConfigColor colorFondoRetrato;

    // Componentes UI
    private JFrame ventana;
    private JEditorPane editorCodigo;
    private JPanel panelRetrato;
    private JLabel etiquetaRetrato;
    private JTextField campoClase;

    public CfrSakuraRiddle() {
        colorFondo = ConfigColor.de("tema.sakura.cfr.fondo", new Color(20, 20, 30));
        colorTexto = ConfigColor.de("tema.sakura.cfr.texto", Color.WHITE);
        colorBorde = ConfigColor.de("tema.sakura.cfr.borde", new Color(100, 50, 100));
        colorFondoRetrato = ConfigColor.de("tema.sakura.cfr.fondo.retrato", new Color(30, 20, 40));
    }

    @Override
    public void procesarHipervinculo(String url) {
        if (!url.startsWith("cfr://")) return;
        String nombreClase = url.substring("cfr://".length()).trim();
        if (nombreClase.isEmpty()) return;

        inicializarVentana();
        campoClase.setText(nombreClase);

        // Cargar en segundo plano
        new Thread(() -> {
            String codigo = CfrBase.descompilarClase(nombreClase);
            SwingUtilities.invokeLater(() -> {
                if (codigo != null) {
                    editorCodigo.setText(codigo);
                } else {
                    editorCodigo.setText(MonitorDePID.idioma.cfrClaseNoEncontrada(nombreClase));
                }
                editorCodigo.setCaretPosition(0);
            });
        }).start();

        ventana.setVisible(true);
    }

    private void inicializarVentana() {
        if (ventana != null) return;

        ventana = new JFrame();
        ventana.setTitle(MonitorDePID.idioma.tituloCfrSakura());
        ventana.setSize(1000, 600);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        // Panel izquierdo: código
        editorCodigo = new JEditorPane();
        editorCodigo.setEditable(false);
        editorCodigo.setContentType("text/plain");
        editorCodigo.setBackground(colorFondo.obtener());
        editorCodigo.setForeground(colorTexto.obtener());
        JScrollPane scrollCodigo = new JScrollPane(editorCodigo);

        // Panel superior: campo de entrada
        campoClase = new JTextField();
        campoClase.setEditable(false);
        campoClase.setBackground(colorFondo.obtener().darker());
        campoClase.setForeground(colorTexto.obtener());
        campoClase.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(colorBorde.obtener()),
                MonitorDePID.idioma.cfrClaseActual()));

        // Panel derecho: retrato
        panelRetrato = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(colorFondoRetrato.obtener());
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelRetrato.setLayout(new BorderLayout());
        panelRetrato.setPreferredSize(new Dimension(220, 0));
        panelRetrato.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(colorBorde.obtener()),
                MonitorDePID.idioma.cfrRetratoDeSakura(),
                TitledBorder.CENTER, TitledBorder.TOP,
                null, colorTexto.obtener()));

        etiquetaRetrato = new JLabel("", JLabel.CENTER);
        panelRetrato.add(etiquetaRetrato, BorderLayout.CENTER);

        cargarRetrato();

        // Ensamblar
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(campoClase, BorderLayout.NORTH);
        panelCentral.add(scrollCodigo, BorderLayout.CENTER);

        ventana.add(panelCentral, BorderLayout.CENTER);
        ventana.add(panelRetrato, BorderLayout.EAST);

        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ventana = null;
            }
        });

        ventana.setLocationRelativeTo(null);
    }

    private void cargarRetrato() {
        new Thread(() -> {
            try {
                File archivo = Statics.carpeta.resolve("imagenes/sakura_riddle.png").toFile();
                if (archivo.exists()) {
                    Image img = ImageIO.read(archivo);
                    SwingUtilities.invokeLater(() -> {
                        etiquetaRetrato.setIcon(new javax.swing.ImageIcon(img));
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        etiquetaRetrato.setText(MonitorDePID.idioma.cfrNoHayRetrato());
                        etiquetaRetrato.setForeground(Color.GRAY);
                    });
                }
            } catch (IOException ex) {
                CrashDetectorLogger.log("Error al cargar retrato de Sakura Riddle: " + ex.getMessage());
                SwingUtilities.invokeLater(() -> {
                    etiquetaRetrato.setText(MonitorDePID.idioma.cfrErrorCargarRetrato());
                    etiquetaRetrato.setForeground(Color.RED);
                });
            }
        }).start();
    }

    // ====== Implementación de CrashDetectorGUI ======

    @Override
    public TipoGUI tipo() {
        return TipoGUI.CFR;
    }

    @Override
    public String id() {
        return ID;
    }

    @Override
    public void init() {
        // Esta GUI se abre solo mediante URL, no directamente
    }

    @Override
    public void recargarApariencia() {
        if (ventana != null && !ventana.isDisplayable()) return;
        editorCodigo.setBackground(colorFondo.obtener());
        editorCodigo.setForeground(colorTexto.obtener());
        campoClase.setBackground(colorFondo.obtener().darker());
        campoClase.setForeground(colorTexto.obtener());
        panelRetrato.repaint();
    }

    @Override
    public List<ElementoConfig> obtenerElementosConfigs() {
        colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
        colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
        colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
        colorFondoRetrato.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoRetrato());

        return new ArrayList<>(Arrays.asList(colorFondo, colorTexto, colorBorde, colorFondoRetrato));
    }
}