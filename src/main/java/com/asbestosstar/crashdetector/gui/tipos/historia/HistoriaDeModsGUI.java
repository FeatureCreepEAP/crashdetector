package com.asbestosstar.crashdetector.gui.tipos.historia;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class HistoriaDeModsGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

    private static final long serialVersionUID = 1L;

    public static Map<String, Supplier<HistoriaDeModsGUI>> GUIS = new HashMap<>();


    // Colores configurables (públicos y no finales)
    public ConfigColor colorEstadoExito;
    public ConfigColor colorEstadoFallo;
    public ConfigColor colorResultadoAnadido;
    public ConfigColor colorResultadoEliminado;
    public ConfigColor colorBordeScroll;
    public ConfigColor colorFondoPanel;

    // Contenedores principales
    public JPanel panelPrincipal;
    public JPanel panelSuperior;
    public JPanel panelIzquierdo;
    public JPanel panelDerecho;

    // Grupos para selección (izq/der)
    public ButtonGroup grupoIzquierdo;
    public ButtonGroup grupoDerecho;

    // Presentación del resultado (HTML)
    public JTextPane resultadoPanel;

    // Botón de comparar (se crea aquí; estilo lo aplica la impl)
    public JButton botonComparar;

    // Scrolls (para permitir que la impl ajuste apariencia fácilmente)
    public JScrollPane scrollIzquierdo;
    public JScrollPane scrollDerecho;
    public JScrollPane scrollResultado;

    // Panel inferior con información adicional
    public JPanel panelInferior;
    public JTextPane descripcionHTML;

    // ====== Constructor ======
    public HistoriaDeModsGUI() {
        super();
    }

    // ====== Estructura base (técnico) ======
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
    }

    // ====== Lógica técnica: carga/parseo/normalización/comparación ======
    protected void cargarArchivosHistoricos() {
        try {
            Path directorioHistorial = MonitorDePID.carpeta.resolve("historia_mods");
            if (Files.exists(directorioHistorial)) {
                File[] archivos = directorioHistorial.toFile()
                        .listFiles((dir, name) -> name.matches("\\d{6}\\.falla") || name.matches("\\d{6}\\.exito"));

                if (archivos != null && archivos.length > 0) {
                    // Orden descendente por número de archivo
                    java.util.Arrays.sort(archivos, (f1, f2) -> {
                        int num1 = Integer.parseInt(f1.getName().substring(0, 6));
                        int num2 = Integer.parseInt(f2.getName().substring(0, 6));
                        return Integer.compare(num2, num1);
                    });

                    // Reset panels y grupos
                    panelIzquierdo.removeAll();
                    panelDerecho.removeAll();
                    grupoIzquierdo = new ButtonGroup();
                    grupoDerecho = new ButtonGroup();

                    for (File f : archivos) {
                        panelIzquierdo.add(crearLineaArchivo(f, grupoIzquierdo));
                    }
                    for (File f : archivos) {
                        panelDerecho.add(crearLineaArchivo(f, grupoDerecho));
                    }

                    // Refrescar y scrollear al final
                    panelIzquierdo.revalidate();
                    panelDerecho.revalidate();
                    scrollHastaFinal(scrollIzquierdo);
                    scrollHastaFinal(scrollDerecho);
                }
            }
        } catch (Exception e) {
            CrashDetectorLogger.log("Error cargando archivos históricos: " + e.getMessage());
        }
    }

    protected JPanel crearLineaArchivo(File archivo, ButtonGroup grupo) {
        JPanel linea = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        linea.setOpaque(false);

        // Radio con el nombre del archivo
        JRadioButton radio = new JRadioButton(archivo.getName());
        radio.setOpaque(false);
        radio.setActionCommand(archivo.getName());
        grupo.add(radio);

        // Etiqueta de estado (texto localizado; color lo decide la impl)
        JLabel estado = new JLabel();
        if (archivo.getName().endsWith(".exito")) {
            estado.setText(" (" + MonitorDePID.idioma.exito() + ")");
            estado.setForeground(colorEstadoExito.obtener());
        } else {
            estado.setText(" (" + MonitorDePID.idioma.fallo() + ")");
            estado.setForeground(colorEstadoFallo.obtener());
        }

        // Hooks de apariencia (la impl puede ajustar fuente, margen, etc.)
        estilizarRadioArchivo(radio);
        estilizarEstadoArchivo(estado);

        linea.add(radio);
        linea.add(estado);
        return linea;
    }

    protected void compararArchivosSeleccionados() {
        String archivoIzq = (grupoIzquierdo == null || grupoIzquierdo.getSelection() == null) ? null
                : grupoIzquierdo.getSelection().getActionCommand();
        String archivoDer = (grupoDerecho == null || grupoDerecho.getSelection() == null) ? null
                : grupoDerecho.getSelection().getActionCommand();

        if (archivoIzq == null || archivoDer == null || archivoIzq.equals(archivoDer)) {
            resultadoPanel.setText(
                    "<html><body><font color='red'>" + MonitorDePID.idioma.seleccionarDosArchivos() + "</font></body></html>");
            return;
        }

        try {
            Path directorio = MonitorDePID.carpeta.resolve("historia_mods");
            Path rutaIzquierda = directorio.resolve(archivoIzq);
            Path rutaDerecha = directorio.resolve(archivoDer);

            Map<String, String> modsIzquierda = leerModsNormalizados(rutaIzquierda);
            Map<String, String> modsDerecha = leerModsNormalizados(rutaDerecha);

            List<String> diferencias = compararModsNormalizados(modsIzquierda, modsDerecha);

            generarHTMLResultado(archivoIzq, archivoDer, diferencias);
        } catch (Exception e) {
            CrashDetectorLogger.log("Error comparando archivos: " + e.getMessage());
            resultadoPanel.setText(
                    "<html><body><font color='red'>" + MonitorDePID.idioma.errorComparandoArchivos() + "</font></body></html>");
        }
    }

    protected Map<String, String> leerModsNormalizados(Path rutaArchivo) throws IOException {
        Map<String, String> mods = new HashMap<>();
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(Files.newInputStream(rutaArchivo), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String nombreNormalizado = normalizarNombreMod(linea.trim());
                    mods.put(nombreNormalizado, linea.trim());
                }
            }
        }
        return mods;
    }

    protected String normalizarNombreMod(String ruta) {
        String nombre = new File(ruta).getName().toLowerCase();
        int indicePunto = nombre.lastIndexOf('.');
        if (indicePunto > 0) {
            nombre = nombre.substring(0, indicePunto);
        }
        return nombre;
    }

    protected List<String> compararModsNormalizados(Map<String, String> modsAnt, Map<String, String> modsNuevos) {
        List<String> difs = new ArrayList<>();

        Set<String> eliminados = new TreeSet<>(modsAnt.keySet());
        eliminados.removeAll(modsNuevos.keySet());

        Set<String> anadidos = new TreeSet<>(modsNuevos.keySet());
        anadidos.removeAll(modsAnt.keySet());

        for (String mod : eliminados) {
            difs.add("- " + modsAnt.get(mod));
        }
        for (String mod : anadidos) {
            difs.add("+ " + modsNuevos.get(mod));
        }
        return difs;
    }

    protected void generarHTMLResultado(String archivo1, String archivo2, List<String> diferencias) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<div style='margin:10px 0;padding:10px;border:1px solid #ccc'>").append("<h3>")
                .append(MonitorDePID.idioma.comparando()).append(" ").append(archivo1).append(" ").append(MonitorDePID.idioma.con()).append(" ")
                .append(archivo2).append(":</h3>");

        if (diferencias.isEmpty()) {
            html.append("<p style='color:green'>").append(MonitorDePID.idioma.noHayCambios()).append("</p>");
        } else {
            html.append("<ul>");
            for (String linea : diferencias) {
                String color = linea.startsWith("+") ? colorResultadoAnadido.obtener().toString()
                        : colorResultadoEliminado.obtener().toString();
                html.append("<li style='color:").append(color).append("'>").append(linea).append("</li>");
            }
            html.append("</ul>");
        }
        html.append("</div></body></html>");
        resultadoPanel.setText(html.toString());
    }

    protected void scrollHastaFinal(JScrollPane sp) {
        if (sp != null && sp.getVerticalScrollBar() != null) {
            sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
        }
    }

    // ====== Hooks de apariencia (a implementar/ajustar en la impl) ======

    protected abstract void estilizarRadioArchivo(JRadioButton radio);

    protected abstract void estilizarEstadoArchivo(JLabel estado);

    protected abstract void aplicarApariencia();

    // ====== CrashDetectorGUI ======

    @Override
    public void recargarApariencia() {
        aplicarApariencia();
    }

    @Override
    public boolean puedeEditarApariencia() {
        return true;
    }

    @Override
    public TipoGUI<HistoriaDeModsGUI> tipo() {
        return TipoGUI.HISTORIA_DE_MODS;
    }

    public static void mostrarGUIHistorialMods() {
        SwingUtilities.invokeLater(() -> {
            HistoriaDeModsGUI gui = new HistoriaModsGUILegacy();
            gui.setVisible(true);
        });
    }

    @Override
    public void init() {
        // Configuración mínima y localizada
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
}