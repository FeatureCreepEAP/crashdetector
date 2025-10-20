package com.asbestosstar.crashdetector.gui.tipos.mcreator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.asbestosstar.crashdetector.EscanerMCreator;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class EscanerMCreatorGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

    private static final long serialVersionUID = 1L;

    // Mantener el registro de GUIS
    public static Map<String, Supplier<EscanerMCreatorGUI>> GUIS = new HashMap<>();

    // ====== Campos de color configurables ======
    public ConfigColor colorFondoVentana;
    public ConfigColor colorTextoPrincipal;
    public ConfigColor colorFondoResultados;
    public ConfigColor colorEstado;
    public ConfigColor colorBotonFondo;
    public ConfigColor colorBotonTexto;
    public ConfigColor colorBordeScroll;
    public ConfigColor colorTextoDescripcion;

    // ====== Componentes técnicos ======
    protected JTextArea areaResultados;
    protected JLabel etiquetaEstado;
    protected JButton botonEscanear;

    // Contenedores
    protected JPanel panelContenido;
    protected JPanel panelContenidoConImagen;
    protected JScrollPane panelDesplazamiento;

    // ====== Constructor ======
    public EscanerMCreatorGUI() {
        super();
    }

    // ====== Construcción base ======
    protected void construirEstructuraBase() {
        // Panel principal
        panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panelContenido, BorderLayout.CENTER);

        // Descripción (localizada desde MonitorDePID)
        JLabel etiquetaDescripcion = new JLabel("<html><div style='text-align: center;'>"
                + MonitorDePID.idioma.descripcionEscanerMCreator() + "</div></html>", JLabel.CENTER);
        etiquetaDescripcion.setFont(fuenteDescripcion());
        aplicarColor(etiquetaDescripcion, colorTextoDescripcion);
        etiquetaDescripcion.setOpaque(true);

        // Área de resultados
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(fuenteResultados());
        areaResultados.setLineWrap(true);
        areaResultados.setWrapStyleWord(true);
        areaResultados.setOpaque(true);

        panelDesplazamiento = new JScrollPane(areaResultados);
        panelDesplazamiento.setBorder(BorderFactory.createLineBorder(colorBordeScroll.obtener()));

        // Imagen opcional
        JLabel etiquetaImagen = null;
        ImageIcon icono = iconoDecorativo();
        if (icono != null && icono.getIconWidth() > 0) {
            // Escalar a 200x112
            Image img = icono.getImage().getScaledInstance(200, 112, Image.SCALE_SMOOTH);
            ImageIcon esc = new ImageIcon(img);
            etiquetaImagen = new JLabel(esc);
            etiquetaImagen.setPreferredSize(new Dimension(200, 112));
            etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);
            etiquetaImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        // Contenedor central (texto + imagen si existe)
        panelContenidoConImagen = new JPanel();
        panelContenidoConImagen.setLayout(new BoxLayout(panelContenidoConImagen, BoxLayout.Y_AXIS));
        panelContenidoConImagen.add(panelDesplazamiento);
        if (etiquetaImagen != null) {
            panelContenidoConImagen.add(etiquetaImagen);
        }

        // Estado (texto NO localizado — hook)
        etiquetaEstado = new JLabel(" ");
        etiquetaEstado.setHorizontalAlignment(JLabel.CENTER);
        aplicarColor(etiquetaEstado, colorEstado);
        etiquetaEstado.setOpaque(true);

        // Botón de escaneo (texto localizado)
        botonEscanear = new JButton(MonitorDePID.idioma.escanear());
        botonEscanear.setFocusPainted(false);
        botonEscanear.setFont(fuenteBoton());
        botonEscanear.setPreferredSize(new Dimension(200, 50));
        botonEscanear.addActionListener(e -> iniciarEscaneo());

        // Organización
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.add(etiquetaDescripcion, BorderLayout.CENTER);
        panelSuperior.add(botonEscanear, BorderLayout.PAGE_END);

        panelContenido.add(panelSuperior, BorderLayout.NORTH);
        panelContenido.add(panelContenidoConImagen, BorderLayout.CENTER);
        panelContenido.add(etiquetaEstado, BorderLayout.SOUTH);
    }

    // ====== Métodos auxiliares para aplicación de colores ======
    protected void aplicarColor(JLabel label, ConfigColor color) {
        label.setForeground(color.obtener());
    }

    protected void aplicarFondo(JComponent component, ConfigColor color) {
        component.setBackground(color.obtener());
        component.setOpaque(true);
    }

    // ====== Lógica técnica del escaneo ======
    protected void iniciarEscaneo() {
        areaResultados.setText("");
        etiquetaEstado.setText(textoEstadoCargando());
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
                    etiquetaEstado.setText(textoEstadoCompletado());
                } catch (Exception ex) {
                    areaResultados.setText("Error: " + ex.getMessage());
                    etiquetaEstado.setText(textoEstadoError());
                } finally {
                    botonEscanear.setEnabled(true);
                }
            }
        }.execute();
    }

    // ====== Hooks de apariencia / textos NO localizados ======

    protected void aplicarApariencia() {
        // Fondo ventana y paneles
        getContentPane().setBackground(colorFondoVentana.obtener());
        panelContenido.setBackground(colorFondoVentana.obtener());
        panelContenidoConImagen.setBackground(colorFondoVentana.obtener());
        setBackground(colorFondoVentana.obtener());

        // Texto y área resultados
        areaResultados.setForeground(colorTextoPrincipal.obtener());
        areaResultados.setBackground(colorFondoResultados.obtener());
        etiquetaEstado.setForeground(colorEstado.obtener());

        // Botón
        // En macOS, no cambiamos el fondo del botón para mantener el estilo nativo
        if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
            botonEscanear.setBackground(colorBotonFondo.obtener());
        }
        botonEscanear.setForeground(colorBotonTexto.obtener());

        // Título
        String titulo = tituloVentanaNoLocalizado();
        if (titulo != null && !titulo.isEmpty()) {
            setTitle(titulo);
        }
    }

    // === Fuentes (la impl decide si cambia) ===
    protected Font fuenteDescripcion() {
        return new Font("Segoe UI", Font.PLAIN, 14);
    }

    protected Font fuenteResultados() {
        return new Font("Consolas", Font.PLAIN, 12);
    }

    protected Font fuenteBoton() {
        return new Font("Segoe UI", Font.BOLD, 14);
    }

    // === Imagen decorativa opcional ===
    protected ImageIcon iconoDecorativo() {
        return null;
    }

    // === Textos NO localizados (la impl decide) ===
    protected String textoEstadoCargando() {
        return "cargando";
    }

    protected String textoEstadoCompletado() {
        return "completado";
    }

    protected String textoEstadoError() {
        return "error";
    }

    protected String tituloVentanaNoLocalizado() {
        return null;
    }

    // ====== CrashDetectorGUI ======
    @Override
    public void recargarApariencia() {
        aplicarApariencia();
    }

    @Override
    public void init() {
        setTitle("Escaner MCreator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        construirEstructuraBase();
        aplicarApariencia();
        setVisible(true);
    }

    @Override
    public TipoGUI<EscanerMCreatorGUI> tipo() {
        return TipoGUI.ESCANER_MCREATOR;
    }
}