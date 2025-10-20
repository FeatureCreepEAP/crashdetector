package com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EnlanceMD;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.gui.ComboIdiomasConIcono;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class NoRegistroLanzadorGUI extends JDialog implements CrashDetectorGUI {

    private static final long serialVersionUID = 1L;

    // Mantener registro de GUIs
    public static Map<String, Supplier<NoRegistroLanzadorGUI>> GUIS = new HashMap<>();

    // Opciones de selector (etiquetas mostradas)
    public static final String GEN = "Genérico";
    public static final String CURSE = "CurseForge";
    public static final String PRISM = "Prism/MultiMC+++";
    public static final String HMCL = "HMCL";
    public static final String FENIX = "Fénix";
    public static final String ATL = "ATLauncher";
    public static final String GD = "GDLauncher";
    public static final String BATTLY = "Battly Launcher";
    public static final String NIGHTWORLD = "Nightworld";
    public static final String MCSERVER = "Servidor de Minecraft";
    public static final String ENLACE_MD = "Enlace MD";

    // Escalado imagen de ayuda (técnico)
    public static final int MAX_ANCHO_IMAGEN = 500;
    public static final int MAX_ALTO_CASI_CUADRADA = 220;
    public static final int MAX_ALTO_PANORAMICA = 120;
    public static final int ALTURA_MINIMA_SIN_IMAGEN = 120;

    public boolean building = false;

    // Controles
    public JComboBox<String> selector;
    public JComboBox<String> comboBoxIdioma;

    public final JLabel imagenLbl = new JLabel("", SwingConstants.CENTER);
    public final JLabel vshojoLbl = new JLabel("", SwingConstants.CENTER);
    public final JButton seleccionarCarpetaBtn = new JButton(MonitorDePID.idioma.seleccionarCarpeta());

    public JEditorPane descripcionHtml;
    public JScrollPane descScroll;

    public TextArea areaTexto;

    public JButton botonGuardar;
    public JButton botonOmitir;
    public JButton botonProxy;

    public JPanel panelAreaTexto;
    public JPanel panelBajoImagen;

    // **** Paneles estructurales ****
    public JPanel raizPanel;
    public JPanel encabezadoPanel;
    public JPanel centroPanel;
    public JPanel panelImagenYBoton;
    public JPanel piePanel;
    public JPanel botonesPanel;

    public static File cd_launcherlog = new File("cd_launcherlog");
    public Instant instant;

    // ====== CAMPOS DE COLOR CONFIGURABLES ======
    public ConfigColor colorFondoVentana;
    public ConfigColor colorTexto;
    public ConfigColor colorBoton;
    public ConfigColor colorCajaTexto;
    public ConfigColor colorEnlace;

    // ====== CONSTRUCTOR ======
    public NoRegistroLanzadorGUI() {
        super();
        // Inicializar campos de color con valores por defecto para evitar NPE
        colorFondoVentana = ConfigColor.de("tema.vshojo.no_registro.color.fondo.ventana", Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
        colorTexto = ConfigColor.de("tema.vshojo.no_registro.color.texto", Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
        colorBoton = ConfigColor.de("tema.vshojo.no_registro.color.boton", Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
        colorCajaTexto = ConfigColor.de("tema.vshojo.no_registro.color.caja_texto", Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
        colorEnlace = ConfigColor.de("tema.vshojo.no_registro.color.enlace", Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace()));
    }

    /**
     * Construye la UI y aplica apariencia. Debe llamarse ANTES de init().
     * 
     * @param blanco  frame propietario (puede ser null)
     * @param instant instante de creación/uso
     */
    public final void preparar(JFrame blanco, Instant instant) {
        this.instant = instant;

        // Configuración básica de diálogo
        setModal(true);
        setAlwaysOnTop(true);
        setTitle(Config.obtenerInstancia().obtenerNombreCD() + " – " + MonitorDePID.idioma.noRegistroLauncherTitulo());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(700, 520));
        setLocationRelativeTo(blanco);
        getContentPane().setLayout(new BorderLayout(8, 0));

        // Construir contenido técnico y aplicar apariencia
        JPanel raiz = construirContenido();
        getContentPane().add(raiz, BorderLayout.CENTER);

        pack();
        setSize(880, 640);

        // Apariencia inicial (colores, bordes y textos NO localizados)
        aplicarApariencia();

        // *** Hacer que cerrar la ventana ("X") sea EXACTAMENTE como Omitir ***
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (botonOmitir != null && botonOmitir.isEnabled()) {
                    botonOmitir.doClick(); // mismo handler que el botón
                } else {
                    dispose();
                }
            }
        });
    }

    // ====== Construcción técnica de la UI ======
    public JPanel construirContenido() {
        building = true;

        raizPanel = new JPanel(new BorderLayout(8, 0));
        raizPanel.setBorder(new EmptyBorder(8, 10, 0, 10));

        // Encabezado
        encabezadoPanel = new JPanel(new BorderLayout(6, 6));

        JLabel titulo = new JLabel("CrashDetector – " + MonitorDePID.idioma.noRegistroLauncherTitulo());
        titulo.setFont(negrita(titulo.getFont(), 18f));
        encabezadoPanel.add(titulo, BorderLayout.NORTH);

        // Descripción HTML
        descripcionHtml = new JEditorPane();
        descripcionHtml.setContentType("text/html");
        descripcionHtml.setEditable(false);
        descripcionHtml.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        descripcionHtml.setText(htmlWrap(MonitorDePID.idioma.noRegistroDeLauncher()));
        descripcionHtml.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().browse(new URI(e.getURL().toString()));
                        }
                    } catch (Exception ex) {
                        CrashDetectorLogger.logException(ex);
                    }
                }
            }
        });

        descScroll = new JScrollPane(descripcionHtml);
        descScroll.setPreferredSize(new Dimension(10, 84));
        encabezadoPanel.add(descScroll, BorderLayout.CENTER);

        // Fila de controles (selector + idiomas)
        JPanel filaControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        selector = new JComboBox<>(
                new String[] { GEN, CURSE, PRISM, HMCL, FENIX, ATL, GD, BATTLY, NIGHTWORLD, MCSERVER, ENLACE_MD });
        estilizarCombo(selector);
        selector.setPreferredSize(new Dimension(360, 34));
        selector.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (!building)
                    refrescarInterfaz();
            }
        });

        LinkedHashMap<String, String> banderas = new LinkedHashMap<>();
        banderas.put("Español", "imagenes/bandera_mexico.png");
        banderas.put("English", "imagenes/bandera_inglaterra.png");
        banderas.put("العربية", "imagenes/bandera_arabia.png");
        banderas.put("Português", "imagenes/bandera_brasil.png");
        banderas.put("فارسی", "imagenes/bandera_iran.png");
        banderas.put("Русский", "imagenes/bandera_rusia.png");
        banderas.put("简体中文", "imagenes/bandera_china.png");
        banderas.put("Esperanto", "imagenes/bandera_esperanto.png");
        banderas.put("日本語", "imagenes/bandera_japon.png");
        banderas.put("한국어", "imagenes/bandera_corea.png");
        comboBoxIdioma = new ComboIdiomasConIcono(banderas);
        estilizarCombo(comboBoxIdioma);

        // Selección por idioma actual
        String code = MonitorDePID.idioma.codigo();
        if ("es".equals(code))
            comboBoxIdioma.setSelectedItem("Español");
        else if ("en".equals(code))
            comboBoxIdioma.setSelectedItem("English");
        else if ("ar".equals(code))
            comboBoxIdioma.setSelectedItem("العربية");
        else if ("pt".equals(code))
            comboBoxIdioma.setSelectedItem("Português");
        else if ("fa".equals(code))
            comboBoxIdioma.setSelectedItem("فارسی");
        else if ("ru".equals(code))
            comboBoxIdioma.setSelectedItem("Русский");
        else if ("zh".equals(code))
            comboBoxIdioma.setSelectedItem("简体中文");
        else if ("eo".equals(code))
            comboBoxIdioma.setSelectedItem("Esperanto");
        else if ("ja".equals(code))
            comboBoxIdioma.setSelectedItem("日本語");
        else if ("ko".equals(code))
            comboBoxIdioma.setSelectedItem("한국어");
        else
            comboBoxIdioma.setSelectedItem("Español");

        comboBoxIdioma.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (building)
                    return;
                String sel = (String) comboBoxIdioma.getSelectedItem();
                String codigo = CrashDetectorGUI.obtenerCodigoIdioma(sel);
                if (codigo != null) {
                    try {
                        File parent = Idioma.archivo.getParentFile();
                        if (parent != null)
                            parent.mkdirs();
                        java.nio.file.Files.write(Idioma.archivo.toPath(), codigo.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException ex) {
                        CrashDetectorLogger.logException(ex);
                    }
                    MonitorDePID.idioma = Idioma.detectar();
                    actualizarTextos();
                }
            }
        });

        filaControles.add(selector);
        filaControles.add(Box.createHorizontalStrut(10));
        filaControles.add(comboBoxIdioma);
        filaControles.setBackground(this.colorFondoVentana.obtener());
        encabezadoPanel.add(filaControles, BorderLayout.SOUTH);

        raizPanel.add(encabezadoPanel, BorderLayout.NORTH);

        // Centro: imagen + botón HMCL debajo + (opcional) área de pegado
        centroPanel = new JPanel(new BorderLayout(6, 6));

        panelImagenYBoton = new JPanel(new BorderLayout());

        imagenLbl.setOpaque(true);
        panelImagenYBoton.add(imagenLbl, BorderLayout.CENTER);

        panelBajoImagen = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 6));

        estilizarBoton(seleccionarCarpetaBtn);
        seleccionarCarpetaBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                abrirSelectorCarpeta();
            }
        });
        panelBajoImagen.add(seleccionarCarpetaBtn);

        panelImagenYBoton.add(panelBajoImagen, BorderLayout.SOUTH);
        centroPanel.add(panelImagenYBoton, BorderLayout.NORTH);

        // Área de pegado (AWT)
        areaTexto = new TextArea("", 24, 100, TextArea.SCROLLBARS_BOTH);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 13));

        panelAreaTexto = new JPanel(new BorderLayout());
        panelAreaTexto.add(areaTexto, BorderLayout.CENTER);
        centroPanel.add(panelAreaTexto, BorderLayout.CENTER);

        raizPanel.add(centroPanel, BorderLayout.CENTER);

        // Pie: logo + botones
        piePanel = new JPanel(new GridBagLayout());

        ImageIcon vshojoIcon = cargarIconoEncajado("imagenes/vshojo.png", 140, 90, true);
        int filaAltura = (vshojoIcon != null ? vshojoIcon.getIconHeight() : 90);

        if (vshojoIcon != null) {
            vshojoLbl.setIcon(vshojoIcon);
            vshojoLbl.setBorder(null);
            vshojoLbl.setPreferredSize(new Dimension(vshojoIcon.getIconWidth(), vshojoIcon.getIconHeight()));
        } else {
            vshojoLbl.setText("VShojo");
        }

        botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botonesPanel.setPreferredSize(new Dimension(10, filaAltura));
        botonesPanel.setMinimumSize(new Dimension(10, filaAltura));
        botonesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, filaAltura));

        botonProxy = new JButton("ProxySysOutSysErr");
        botonGuardar = new JButton(MonitorDePID.idioma.guardarYCerrar());
        botonOmitir = new JButton(MonitorDePID.idioma.omitirYCerrar());
        estilizarBoton(botonProxy, 4);
        estilizarBoton(botonGuardar, 4);
        estilizarBoton(botonOmitir, 4);

        botonProxy.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ev) {
                String msg = MonitorDePID.idioma.habilitarProxySysOutSysErrMensaje();
                int r = JOptionPane.showConfirmDialog(NoRegistroLanzadorGUI.this, msg,
                        MonitorDePID.idioma.confirmacionTitulo(), JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (r == JOptionPane.YES_OPTION) {
                    Config.obtenerInstancia().guardarProxySysOutSysErr(true);
                    JOptionPane.showMessageDialog(NoRegistroLanzadorGUI.this,
                            MonitorDePID.idioma.proxyHabilitadoMensaje(), MonitorDePID.idioma.informacionTitulo(),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                guardarRegistros();
            }
        });
        botonOmitir.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });

        // Orden: Guardar, Omitir, Proxy
        botonesPanel.add(botonGuardar);
        botonesPanel.add(botonOmitir);
        botonesPanel.add(botonProxy);

        GridBagConstraints g0 = new GridBagConstraints();
        g0.gridx = 0;
        g0.gridy = 0;
        g0.anchor = GridBagConstraints.WEST;
        g0.insets = new java.awt.Insets(0, 0, 0, 8);
        piePanel.add(vshojoLbl, g0);

        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 1;
        g1.gridy = 0;
        g1.weightx = 1.0;
        g1.anchor = GridBagConstraints.EAST;
        g1.fill = GridBagConstraints.HORIZONTAL;
        piePanel.add(botonesPanel, g1);

        raizPanel.add(piePanel, BorderLayout.SOUTH);

        // Selección automática por ruta
        selector.setSelectedItem(detectarPorDirectorio());
        building = false;
        refrescarInterfaz();
        return raizPanel;
    }

    // ====== Lógica técnica ======

    public void actualizarTextos() {
        setTitle("CrashDetector – " + MonitorDePID.idioma.noRegistroLauncherTitulo());
        descripcionHtml.setText(htmlWrap(MonitorDePID.idioma.noRegistroDeLauncher()));
        seleccionarCarpetaBtn.setText(MonitorDePID.idioma.seleccionarCarpeta());
        if (botonGuardar != null)
            botonGuardar.setText(MonitorDePID.idioma.guardarYCerrar());
        if (botonOmitir != null)
            botonOmitir.setText(MonitorDePID.idioma.omitirYCerrar());
        if (panelAreaTexto != null)
            panelAreaTexto.setBorder(bordeTitulado(MonitorDePID.idioma.pegaLosRegistrosAqui()));
        refrescarInterfaz();
    }

    /**
     * Cambia imagen/controles según launcher y compacta si no hay área de pegado.
     */
    public void refrescarInterfaz() {
        String tipo = (String) selector.getSelectedItem();

        String desc = MonitorDePID.idioma.noRegistroDeLauncher(); // por defecto
        ImageIcon icono = null;
        boolean mostrarArea = true;
        boolean mostrarBotonHMCL = false;

        if (HMCL.equals(tipo)) {
            desc = MonitorDePID.idioma.descripcionHMCL();
        } else if (GEN.equals(tipo)) {
            desc = MonitorDePID.idioma.noRegistroDeLauncher();
        }

        if (CURSE.equals(tipo)) {
            desc = MonitorDePID.idioma.descripcionCurseforge();
            icono = cargarIconoAyudaFlexible("/imagenes/cfskiplauncher.png");
            mostrarArea = false;
        } else if (PRISM.equals(tipo)) {
            desc = MonitorDePID.idioma.descripcionPrism();
            icono = cargarIconoAyudaFlexible("/imagenes/registros_de_launcher_prism.png");
        } else if (HMCL.equals(tipo)) {
            icono = cargarIconoAyudaFlexible("/imagenes/hcml.png");
            mostrarArea = false;
            mostrarBotonHMCL = true;
        } else if (FENIX.equals(tipo)) {
            desc = MonitorDePID.idioma.descripcionFenix();
            icono = cargarIconoAyudaFlexible("/imagenes/fenix.png");
        } else if (ATL.equals(tipo)) {
            desc = MonitorDePID.idioma.descripcionATLauncher();
            icono = cargarIconoAyudaFlexible("/imagenes/atlogs.png");
        } else if (GD.equals(tipo)) {
            desc = MonitorDePID.idioma.descripcionGDLauncher();
            icono = cargarIconoAyudaFlexible("/imagenes/gdconsola.png");
        } else if (ENLACE_MD.equals(tipo)) {
            desc = MonitorDePID.idioma.descripcionLinksMarkdown();
            icono = null;
        } else if (BATTLY.equals(tipo)) {
            desc = MonitorDePID.idioma.noRegistroDeBattly();
            icono = cargarIconoAyudaFlexible("/imagenes/battly.png");
            mostrarArea = false;
        } else if (NIGHTWORLD.equals(tipo)) {
            desc = MonitorDePID.idioma.noRegistroDeNightWorld();
            icono = cargarIconoAyudaFlexible("/imagenes/nightworld.png");
            mostrarArea = true;
        } else if (MCSERVER.equals(tipo)) {
            desc = MonitorDePID.idioma.noRegistroDeMCServidor();
            icono = cargarIconoAyudaFlexible("/imagenes/minecraftserver.png");
            mostrarArea = true;
        } else {
            icono = cargarIconoAyudaFlexible("/imagenes/registros_de_lanzar.png");
        }

        // Render HTML crudo
        descripcionHtml.setText(htmlWrap(desc));

        if (icono != null) {
            imagenLbl.setIcon(icono);
            imagenLbl.setText("");
            imagenLbl.setPreferredSize(new Dimension(icono.getIconWidth(), icono.getIconHeight()));
        } else {
            imagenLbl.setIcon(null);
            imagenLbl.setText(MonitorDePID.idioma.imagenNoEncontrada());
            imagenLbl.setPreferredSize(new Dimension(10, ALTURA_MINIMA_SIN_IMAGEN));
        }

        if (panelBajoImagen != null)
            panelBajoImagen.setVisible(mostrarBotonHMCL);

        boolean estabaVisible = panelAreaTexto.isVisible();
        panelAreaTexto.setVisible(mostrarArea);

        revalidate();
        repaint();

        if (estabaVisible && !mostrarArea) {
            pack(); // compacta al ocultar el área
        }
    }

    /** Detecta el launcher a partir de la ruta actual (ruta y padres). */
    public String detectarPorDirectorio() {
        String rutaAbs = new File(System.getProperty("user.dir")).getAbsolutePath().toLowerCase();

        if (contiene(rutaAbs, "prismlauncher", "multimc", "polymc", "pollymc", "freesm", "fjord", "ultim", "prism"))
            return PRISM;
        if (contiene(rutaAbs, "atlauncher"))
            return ATL;
        if (contiene(rutaAbs, "gdlauncher"))
            return GD;
        if (contiene(rutaAbs, "curseforge", "overwolf"))
            return CURSE;
        if (contiene(rutaAbs, "hmcl"))
            return HMCL;
        if (contiene(rutaAbs, "battly"))
            return BATTLY;
        if (contiene(rutaAbs, "server"))
            return MCSERVER;

        File f = new File(rutaAbs);
        for (int i = 0; i < 5 && f != null; i++, f = f.getParentFile()) {
            String nombre = f.getName().toLowerCase();
            if (contiene(nombre, "prismlauncher", "multimc", "polymc", "pollymc", "freesm", "fjord", "ultim", "prism"))
                return PRISM;
            if (nombre.contains("atlauncher"))
                return ATL;
            if (nombre.contains("gdlauncher"))
                return GD;
            if (contiene(nombre, "curseforge", "overwolf"))
                return CURSE;
            if (nombre.contains("hmcl"))
                return HMCL;
            if (nombre.contains("battly"))
                return BATTLY;
            if (nombre.contains("server"))
                return MCSERVER;
        }
        return GEN;
    }

    public boolean contiene(String s, String... tokens) {
        for (int i = 0; i < tokens.length; i++)
            if (s.contains(tokens[i]))
                return true;
        return false;
    }

    // Acciones

    public void abrirSelectorCarpeta() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dir = fc.getSelectedFile();
            Config.obtenerInstancia().guardarCarpetaHMCL(dir.getAbsolutePath());
        }
    }

    public void guardarRegistros() {
        String tipo = (String) selector.getSelectedItem();

        if (HMCL.equals(tipo) || CURSE.equals(tipo) || BATTLY.equals(tipo)) {
            dispose();
            return;
        } else if (ENLACE_MD.equals(tipo)) {
            String contenido = (areaTexto != null) ? areaTexto.getText() : "";
            ejecutarConOverlayDescarga(new Runnable() {
                @Override
                public void run() {
                    CrashDetectorLogger.log("EnlaceMD: iniciando guardado");
                    EnlanceMD.guardar(contenido);
                    CrashDetectorLogger.log("EnlaceMD: guardado completo");
                }
            }, overlayMensaje());
            return;
        }

        if (areaTexto == null) {
            dispose();
            return;
        }

        final byte[] data = areaTexto.getText().getBytes(StandardCharsets.UTF_8);
        long sizeBytes = data.length;

        long timeoutSeconds;
        if (sizeBytes < (10L * 1024 * 1024)) {
            timeoutSeconds = 5;
        } else {
            long chunks = (sizeBytes + (50L * 1024 * 1024) - 1) / (50L * 1024 * 1024);
            timeoutSeconds = chunks * 20;
        }

        CrashDetectorLogger
                .log("Tamaño de logs = " + (sizeBytes / (1024 * 1024)) + " MB, timeout = " + timeoutSeconds + "s");

        Thread writerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (FileOutputStream fos = new FileOutputStream(cd_launcherlog); FileChannel ch = fos.getChannel()) {
                    fos.write(data);
                    CrashDetectorLogger.log("Archivo cd_launcherlog guardado");

                    Consola cons = new Consola(cd_launcherlog.toPath());
                    cons.finalizarContenidoInyectado(new String(data, StandardCharsets.UTF_8));
                    MonitorDePID.consolas.add(cons);
                    MonitorDePID.consola_de_launcher_inyectado = true;

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(NoRegistroLanzadorGUI.this, "Error: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        writerThread.start();
        try {
            writerThread.join(timeoutSeconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        dispose();
    }

    /** Overlay modal con GIF o barra indeterminada; cierra diálogo al terminar. */
    public void ejecutarConOverlayDescarga(final Runnable tarea, String mensaje) {
        final JDialog overlay = new JDialog(this, true);
        overlay.setUndecorated(true);

        JPanel box = new JPanel(new BorderLayout(10, 10));
        box.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        ImageIcon gif = cargarGif(gifDescargaPath());
        if (gif != null) {
            JLabel gifLbl = new JLabel(gif);
            gifLbl.setOpaque(false);
            box.add(gifLbl, BorderLayout.WEST);
        } else {
            CrashDetectorLogger.log("GIF de descarga no encontrado, usando barra indeterminada");
            JProgressBar bar = new JProgressBar();
            bar.setIndeterminate(true);
            box.add(bar, BorderLayout.WEST);
        }

        JLabel msg = new JLabel(mensaje);
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.add(msg, BorderLayout.CENTER);

        overlay.getContentPane().add(box);
        overlay.pack();
        overlay.setLocationRelativeTo(this);

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    tarea.run();
                } catch (Throwable t) {
                    CrashDetectorLogger.logException(t);
                    JOptionPane.showMessageDialog(NoRegistroLanzadorGUI.this, "Error: " + t.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                return null;
            }

            @Override
            protected void done() {
                overlay.dispose();
                NoRegistroLanzadorGUI.this.dispose();
            }
        };

        worker.execute();
        overlay.setVisible(true);
    }

    // ====== Hooks de apariencia ======

    public abstract void aplicarApariencia();

    public String overlayMensaje() {
        return "Descargando y preparando enlaces...";
    }

    public String gifDescargaPath() {
        return "/imagenes/descargando.gif";
    }

    // ====== Utilidades de estilo ======

    public TitledBorder bordeTitulado(String titulo) {
        TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorBoton.obtener().darker(), 1),
                titulo);
        b.setTitleColor(colorTexto.obtener());
        return b;
    }

    public void estilizarBoton(JButton btn) {
        estilizarBoton(btn, 10);
    }

    public void estilizarBoton(JButton btn, int paddingV) {
        if (!CrashDetectorGUI.esMac()) {
            btn.setBackground(colorBoton.obtener());
            btn.setForeground(colorTexto.obtener());
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(paddingV, 18, paddingV, 18));
        } else {
            // por defecto L&F; la subclase puede forzar opaco/colores
            btn.setContentAreaFilled(false);
        }
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    public void estilizarCombo(JComboBox<?> combo) {
        if (!CrashDetectorGUI.esMac()) {
            combo.setBackground(colorBoton.obtener());
            combo.setForeground(colorTexto.obtener());
        }
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setPreferredSize(new Dimension(220, 32));
    }

    public Font negrita(Font base, float size) {
        return base.deriveFont(Font.BOLD, size);
    }

    /** Envuelve HTML sin escapar, aplicando colores/estilo del proyecto. */
    public String htmlWrap(String innerHtml) {
        String fg = rgb(colorTexto.obtener());
        String bg = rgb(colorCajaTexto.obtener());
        return "<html><body style='margin:6px; font-family: Segoe UI, sans-serif; font-size:13px; color:" + fg
                + "; background:" + bg + ";'>" + innerHtml + "</body></html>";
    }

    public String rgb(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    // Utilidades de imágenes

    public ImageIcon cargarGif(String ruta) {
        try {
            URL url = getClass().getResource(ruta);
            return (url != null) ? new ImageIcon(url) : null;
        } catch (Throwable t) {
            return null;
        }
    }

    public ImageIcon cargarIconoAyudaFlexible(String ruta) {
        URL url = getClass().getResource(ruta);
        if (url == null) {
            CrashDetectorLogger.log("No se encontró la imagen " + ruta);
            return null;
        }
        ImageIcon original = new ImageIcon(url);
        int w = original.getIconWidth();
        int h = original.getIconHeight();
        if (w <= 0 || h <= 0)
            return original;

        double ratio = (double) w / (double) h;
        boolean casiCuadrada = (ratio >= 0.75 && ratio <= 1.33);

        int maxAncho = MAX_ANCHO_IMAGEN;
        int maxAlto = casiCuadrada ? MAX_ALTO_CASI_CUADRADA : MAX_ALTO_PANORAMICA;

        double escala = Math.min((double) maxAncho / w, (double) maxAlto / h);
        int nuevoW = (int) Math.round(w * escala);
        int nuevoH = (int) Math.round(h * escala);

        Image img = original.getImage().getScaledInstance(nuevoW, nuevoH, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    /** Escalado genérico (logo). */
    public ImageIcon cargarIconoEncajado(String ruta, int maxAncho, int maxAlto, boolean noAmpliar) {
        File archivo = MonitorDePID.carpeta.resolve(ruta).toFile();
        if (archivo == null) {
            CrashDetectorLogger.log("No se encontró la imagen " + ruta);
            return null;
        }
        ImageIcon original = new ImageIcon(archivo.getAbsolutePath());
        int w = original.getIconWidth();
        int h = original.getIconHeight();
        if (w <= 0 || h <= 0)
            return original;

        double escalaW = (double) maxAncho / w;
        double escalaH = (double) maxAlto / h;
        double escala = Math.min(escalaW, escalaH);
        if (noAmpliar && escala > 1.0)
            escala = 1.0;

        int nuevoW = (int) Math.round(w * escala);
        int nuevoH = (int) Math.round(h * escala);
        Image escalada = original.getImage().getScaledInstance(nuevoW, nuevoH, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }

    // ====== CrashDetectorGUI ======

    @Override
    public void recargarApariencia() {
        aplicarApariencia();
    }

    @Override
    public TipoGUI<NoRegistroLanzadorGUI> tipo() {
        return TipoGUI.NO_REGISTRO_LANZER;
    }

    @Override
    public void init() {
        setVisible(true);
    }
}