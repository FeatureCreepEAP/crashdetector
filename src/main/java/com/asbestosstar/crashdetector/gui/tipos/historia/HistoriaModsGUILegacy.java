package com.asbestosstar.crashdetector.gui.tipos.historia;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class HistoriaModsGUILegacy extends HistoriaDeModsGUI {

    public static String ID = "historia_mods_legacy";
    private static final long serialVersionUID = 1L;

    // ====== Tipografías ======
    public Font fuenteEtiqueta = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    public Font fuenteRadio = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    public Font fuenteEstado = new Font(Font.SANS_SERIF, Font.PLAIN, 10);

    
   public ConfigColor colorEstadoExito = ConfigColor.de("tema.historia_mods.color.estado.exito", new java.awt.Color(0x4C, 0xAF, 0x50));
   public ConfigColor colorEstadoFallo = ConfigColor.de("tema.historia_mods.color.estado.fallo", new java.awt.Color(0xF4, 0x43, 0x36));
   public ConfigColor colorResultadoAnadido = ConfigColor.de("tema.historia_mods.color.resultado.anadido", new java.awt.Color(0x2E, 0x7D, 0x32));
   public ConfigColor colorResultadoEliminado = ConfigColor.de("tema.historia_mods.color.resultado.eliminado", new java.awt.Color(0xC6, 0x28, 0x28));
   public ConfigColor colorBordeScroll = ConfigColor.de("tema.historia_mods.color.borde.scroll", new java.awt.Color(0xDD, 0xDD, 0xDD));
   public ConfigColor colorFondoPanel = ConfigColor.de("tema.historia_mods.color.fondo.panel", new java.awt.Color(0xFF, 0xFF, 0xFF));

    
    
    @Override
    public String id() {
        return ID;
    }

    @Override
    public void init() {
        // Inicializar colores PRIMERO

        // Configuración básica de la ventana
        setTitle(MonitorDePID.idioma.historialDeMods());
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Construir esqueleto técnico/UI base
        construirEstructuraBase();

        // Cargar archivos históricos (técnico)
        cargarArchivosHistoricos();

        // Aplicar apariencia inicial
        aplicarApariencia();
        setVisible(true);
    }

    @Override
    public List<ElementoConfig> obtenerElementosConfigs() {
        List<ElementoConfig> elementos = new ArrayList<>();
        elementos.add(colorEstadoExito);
        elementos.add(colorEstadoFallo);
        elementos.add(colorResultadoAnadido);
        elementos.add(colorResultadoEliminado);
        elementos.add(colorBordeScroll);
        elementos.add(colorFondoPanel);
        return elementos;
    }

    @Override
    protected void construirEstructuraBase() {
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panelPrincipal, BorderLayout.CENTER);

        // Panel superior con GridBag para dos columnas (izq/der) y el botón
        panelSuperior = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Etiquetas de columnas (localizadas)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel etiquetaIzquierda = new JLabel(MonitorDePID.idioma.archivo0());
        panelSuperior.add(etiquetaIzquierda, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel etiquetaDerecha = new JLabel(MonitorDePID.idioma.archivo1());
        panelSuperior.add(etiquetaDerecha, gbc);

        // Paneles de listas
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        scrollIzquierdo = new JScrollPane(panelIzquierdo);
        scrollIzquierdo.setPreferredSize(new Dimension(350, 300));
        panelSuperior.add(scrollIzquierdo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        scrollDerecho = new JScrollPane(panelDerecho);
        scrollDerecho.setPreferredSize(new Dimension(350, 300));
        panelSuperior.add(scrollDerecho, gbc);

        // Botón de comparar (texto localizado; estilos van en la impl)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        botonComparar = new JButton(MonitorDePID.idioma.comparar());
        panelSuperior.add(botonComparar, gbc);

        // Panel de resultados
        resultadoPanel = new JTextPane();
        resultadoPanel.setContentType("text/html");
        resultadoPanel.setEditable(false);
        scrollResultado = new JScrollPane(resultadoPanel);

        // Wiring de eventos (técnico)
        botonComparar.addActionListener(e -> compararArchivosSeleccionados());

        // Ensamblar panel principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollResultado, BorderLayout.CENTER);

        // Aplicar apariencia inicial
        aplicarApariencia();
    }

    @Override
    protected void estilizarRadioArchivo(javax.swing.JRadioButton radio) {
        radio.setFont(fuenteRadio);
    }

    @Override
    protected void estilizarEstadoArchivo(javax.swing.JLabel estado) {
        estado.setFont(fuenteEstado);
    }

    @Override
    protected void aplicarApariencia() {
        // Bordes/paddings base
        if (panelPrincipal != null) {
            panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }

        // Scrolls (marcos suaves)
        if (scrollIzquierdo != null)
            scrollIzquierdo.setBorder(BorderFactory.createLineBorder(colorBordeScroll.obtener()));
        if (scrollDerecho != null)
            scrollDerecho.setBorder(BorderFactory.createLineBorder(colorBordeScroll.obtener()));
        if (scrollResultado != null)
            scrollResultado.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // Títulos de columnas
        if (panelSuperior != null && panelSuperior.getComponentCount() >= 2) {
            if (panelSuperior.getComponent(0) instanceof javax.swing.JLabel) {
                javax.swing.JLabel lbl0 = (javax.swing.JLabel) panelSuperior.getComponent(0);
                lbl0.setFont(fuenteEtiqueta);
            }
            if (panelSuperior.getComponent(1) instanceof javax.swing.JLabel) {
                javax.swing.JLabel lbl1 = (javax.swing.JLabel) panelSuperior.getComponent(1);
                lbl1.setFont(fuenteEtiqueta);
            }
        }

        // Botón comparar
        if (botonComparar != null) {
            botonComparar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        }

        // Resultado HTML
        if (resultadoPanel != null) {
            resultadoPanel.setMargin(new java.awt.Insets(6, 6, 6, 6));
        }

        // Panel inferior con texto NO localizado + imagen
        instalarPanelInferiorConImagenYTexto();
        revalidate();
        repaint();
    }

    private void instalarPanelInferiorConImagenYTexto() {
        // Eliminar si ya existe
        if (panelInferior != null) {
            panelPrincipal.remove(panelInferior);
            panelInferior = null;
        }

        panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);

        // Descripción
        String ayudaNoLocalizada = String.format("<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
                + "  <b>Consejo:</b> Selecciona dos archivos de historial para comparar la lista de mods. "
                + "  El resultado muestra <span style='color:%s;'>añadidos (+)</span> y "
                + "  <span style='color:%s;'>eliminados (&#8722;)</span> basados en nombres normalizados." + 
                "</body>" + "</html>", 
                colorResultadoAnadido.obtener().toString(),
                colorResultadoEliminado.obtener().toString());

        descripcionHTML = new JTextPane();
        descripcionHTML.setContentType("text/html");
        descripcionHTML.setEditable(false);
        descripcionHTML.setText(ayudaNoLocalizada);

        JScrollPane scrollDescripcion = new JScrollPane(descripcionHTML);
        scrollDescripcion.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDescripcion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDescripcion.setPreferredSize(new Dimension(600, 100));
        scrollDescripcion.setBorder(BorderFactory.createEmptyBorder());

        JPanel textoPanel = new JPanel(new BorderLayout());
        textoPanel.setOpaque(false);
        textoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textoPanel.add(scrollDescripcion, BorderLayout.CENTER);

        JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        contenedor.setOpaque(false);
        contenedor.add(textoPanel);

        try {
            Path rutaImagen = MonitorDePID.carpeta.resolve("imagenes/clio.png");
            if (!Files.exists(rutaImagen)) {
                MonitorDePID.copiarACarpetaDesdeJar("/imagenes/clio.png", rutaImagen.toFile());
            }
            if (Files.exists(rutaImagen)) {
                ImageIcon icono = new ImageIcon(rutaImagen.toAbsolutePath().toString());
                java.awt.Image escalado = icono.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                JLabel etiquetaImagen = new JLabel(new ImageIcon(escalado));
                contenedor.add(etiquetaImagen);
            }
        } catch (Exception e) {
            CrashDetectorLogger.log("Error cargando clio.png: " + e.getMessage());
        }

        panelInferior.add(contenedor, BorderLayout.CENTER);
        panelInferior.setMinimumSize(new Dimension(100, 100));

        panelPrincipal.add(panelInferior, BorderLayout.PAGE_END);
    }
}