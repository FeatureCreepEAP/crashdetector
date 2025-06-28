package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class HistoriaModsGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    
    // Componentes de la interfaz
    private JPanel panelIzquierdo;
    private JPanel panelDerecho;
    private ButtonGroup grupoIzquierdo;
    private ButtonGroup grupoDerecho;
    private JTextPane resultadoPanel;
    
    // Atributos para internacionalización
    private Idioma idioma = MonitorDePID.idioma;

    public HistoriaModsGUI() {
        // Configuración básica de la ventana
        setTitle(idioma.historialDeMods());
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializar componentes
        inicializarInterfaz();
        
        // Cargar archivos históricos
        cargarArchivosHistoricos();
        
        // Añadir imagen clio.png en la esquina inferior derecha
        agregarImagenClio();
        //pack();
    }

    // Inicializa todos los componentes de la interfaz
    private void inicializarInterfaz() {
        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior para selección de archivos
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Panel izquierdo para selección
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        
        JLabel etiquetaIzquierda = new JLabel(idioma.archivo0());
        etiquetaIzquierda.setFont(etiquetaIzquierda.getFont().deriveFont(Font.BOLD, 14));
        panelSuperior.add(etiquetaIzquierda, gbc);
        
        gbc.gridy = 1;
        panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        JScrollPane scrollIzquierdo = new JScrollPane(panelIzquierdo);
        scrollIzquierdo.setPreferredSize(new Dimension(350, 300));
        panelSuperior.add(scrollIzquierdo, gbc);
        
        // Panel derecho para selección
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        
        JLabel etiquetaDerecha = new JLabel(idioma.archivo1());
        etiquetaDerecha.setFont(etiquetaDerecha.getFont().deriveFont(Font.BOLD, 14));
        panelSuperior.add(etiquetaDerecha, gbc);
        
        gbc.gridy = 1;
        panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        JScrollPane scrollDerecho = new JScrollPane(panelDerecho);
        scrollDerecho.setPreferredSize(new Dimension(350, 300));
        panelSuperior.add(scrollDerecho, gbc);
        
        // Botón de comparación
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JButton botonComparar = new JButton(idioma.comparar());
        botonComparar.setFont(botonComparar.getFont().deriveFont(Font.BOLD));
        //botonComparar.setBackground(new Color(0x4CAF50)); // Verde
        //botonComparar.setForeground(Color.WHITE);
        panelSuperior.add(botonComparar, gbc);
        
        // Panel de resultados
        resultadoPanel = new JTextPane();
        resultadoPanel.setContentType("text/html");
        resultadoPanel.setEditable(false);
        JScrollPane panelDesplazamiento = new JScrollPane(resultadoPanel);
        
        // Acción del botón comparar
        botonComparar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compararArchivosSeleccionados();
            }
        });
        
        // Añadir componentes al panel principal
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelDesplazamiento, BorderLayout.CENTER);
        
        
        
        
        

        
        
        add(panelPrincipal, BorderLayout.CENTER);
    }

    // Carga los archivos históricos desde el directorio historia_mods
    private void cargarArchivosHistoricos() {
        try {
            Path directorioHistorial = MonitorDePID.carpeta.resolve("historia_mods");
            if (Files.exists(directorioHistorial)) {
                File[] archivos = directorioHistorial.toFile().listFiles((dir, name) -> 
                    name.matches("\\d{6}\\.falla") || name.matches("\\d{6}\\.exito"));
                
                if (archivos != null && archivos.length > 0) {
                    // Ordenar por número de archivo en orden descendente
                    java.util.Arrays.sort(archivos, (f1, f2) -> {
                        int num1 = Integer.parseInt(f1.getName().substring(0, 6));
                        int num2 = Integer.parseInt(f2.getName().substring(0, 6));
                        return Integer.compare(num2, num1); // Descendente
                    });
                    
                    // Inicializar grupos de botones
                    panelIzquierdo.removeAll();
                    panelDerecho.removeAll();
                    grupoIzquierdo = new ButtonGroup();
                    panelIzquierdo.removeAll();
                    for (File f : archivos) {
                      panelIzquierdo.add(crearBotonArchivo(f, grupoIzquierdo));
                    }

                    grupoDerecho = new ButtonGroup();
                    panelDerecho.removeAll();
                    for (File f : archivos) {
                      panelDerecho.add(crearBotonArchivo(f, grupoDerecho));
                    }

                    
                    // Asegurar que el scroll empiece abajo
                    panelIzquierdo.revalidate();
                    panelDerecho.revalidate();
                    ((JScrollPane)panelIzquierdo.getParent()).getVerticalScrollBar().setValue(
                        ((JScrollPane)panelIzquierdo.getParent()).getVerticalScrollBar().getMaximum());
                    ((JScrollPane)panelDerecho.getParent()).getVerticalScrollBar().setValue(
                        ((JScrollPane)panelDerecho.getParent()).getVerticalScrollBar().getMaximum());
                }
            }
        } catch (Exception e) {
            CrashDetectorLogger.log("Error cargando archivos históricos: " + e.getMessage());
        }
    }

    /**
     * Crea un panel que contiene:
     *   • JRadioButton con el nombre del archivo
     *   • JLabel con "(éxito)" o "(fallo)" coloreado
     */
    private JPanel crearBotonArchivo(File archivo, ButtonGroup grupo) {
      // Panel contenedor en fila
      JPanel linea = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
      linea.setOpaque(false);

      // 1) El radio button muestra el nombre del archivo
      JRadioButton radio = new JRadioButton( archivo.getName() );
      radio.setOpaque(false);
      radio.setFont(radio.getFont().deriveFont(12f));
      radio.setActionCommand( archivo.getName() );
      grupo.add(radio);

      // 2) El label extra para el estado, en pequeño y colorido
      JLabel estado = new JLabel();
      estado.setFont(estado.getFont().deriveFont(9f));

      if (archivo.getName().endsWith(".exito")) {
        estado.setText(" (" + idioma.exito() + ")");
        estado.setForeground(new Color(0x4CAF50));  // verde
      }
      else {
        estado.setText(" (" + idioma.fallo() + ")");
        estado.setForeground(new Color(0xF44336));  // rojo
      }

      linea.add(radio);
      linea.add(estado);

      return linea;
    }


    
    
    
    // Realiza la comparación entre los archivos seleccionados
    private void compararArchivosSeleccionados() {
        String archivoIzquierdo = grupoIzquierdo.getSelection() == null ? null : grupoIzquierdo.getSelection().getActionCommand();
        String archivoDerecho = grupoDerecho.getSelection() == null ? null : grupoDerecho.getSelection().getActionCommand();
        
        if (archivoIzquierdo == null || archivoDerecho == null) {
            resultadoPanel.setText("<html><body><font color='red'>" + idioma.seleccionarDosArchivos() + "</font></body></html>");
            return;
        }
        
        if (archivoIzquierdo.equals(archivoDerecho)) {
            resultadoPanel.setText("<html><body><font color='red'>" + idioma.seleccionarDosArchivos() + "</font></body></html>");
            return;
        }
        
        try {
            Path directorio = MonitorDePID.carpeta.resolve("historia_mods");
            Path rutaIzquierda = directorio.resolve(archivoIzquierdo);
            Path rutaDerecha = directorio.resolve(archivoDerecho);
            
            Set<String> modsIzquierda = leerMods(rutaIzquierda);
            Set<String> modsDerecha = leerMods(rutaDerecha);
            
            List<String> diferenciasIzquierdo = compararMods(modsIzquierda, modsDerecha);
            List<String> diferenciasDerecho = compararMods(modsDerecha, modsIzquierda);
            
            generarHTMLResultado(archivoIzquierdo, archivoDerecho, diferenciasIzquierdo, diferenciasDerecho);
        } catch (Exception e) {
            CrashDetectorLogger.log("Error comparando archivos: " + e.getMessage());
            resultadoPanel.setText("<html><body><font color='red'>" + idioma.errorComparandoArchivos() + "</font></body></html>");
        }
    }

    // Lee los mods de un archivo
    private Set<String> leerMods(Path rutaArchivo) throws IOException {
        Set<String> mods = new HashSet<>();
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(Files.newInputStream(rutaArchivo), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    mods.add(linea.trim());
                }
            }
        }
        return mods;
    }

    // Compara dos conjuntos de mods y devuelve las diferencias
    private List<String> compararMods(Set<String> viejos, Set<String> nuevos) {
        List<String> diferencias = new ArrayList<>();
        
        // Mods añadidos
        nuevos.stream()
              .filter(mod -> !viejos.contains(mod))
              .forEach(mod -> diferencias.add("+ " + mod));
        
        // Mods eliminados
        viejos.stream()
              .filter(mod -> !nuevos.contains(mod))
              .forEach(mod -> diferencias.add("- " + mod));
        
        return diferencias;
    }

    // Genera el resultado en formato HTML
    private void generarHTMLResultado(String archivo1, String archivo2, List<String> diferencias1, List<String> diferencias2) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        
        // Información de comparación
        html.append("<div style='margin:10px 0;padding:10px;border:1px solid #ccc'>")
            .append("<h3>").append(idioma.comparando()).append(" ")
            .append(archivo1).append(" ").append(idioma.con()).append(" ")
            .append(archivo2).append(":</h3>")
            .append("<ul>");
        
        if (diferencias1.isEmpty() && diferencias2.isEmpty()) {
            html.append("<p style='color:green'>").append(idioma.noHayCambios()).append("</p>");
        } else {
            for (String linea : diferencias1) {
                String color = linea.startsWith("+") ? "green" : "red";
                html.append("<li style='color:").append(color).append("'>").append(linea).append("</li>");
            }
            
            for (String linea : diferencias2) {
                String color = linea.startsWith("+") ? "green" : "red";
                html.append("<li style='color:").append(color).append("'>").append(linea).append("</li>");
            }
        }
        
        html.append("</ul></div></body></html>");
        resultadoPanel.setText(html.toString());
    }

    private void agregarImagenClio() {
        // Panel principal con diseño horizontal
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);
        
        // Panel para descripción con scroll
        JPanel textoPanel = new JPanel(new BorderLayout());
        textoPanel.setOpaque(false);
        textoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // JTextPane para HTML con scroll
        JTextPane descripcionHTML = new JTextPane();
        descripcionHTML.setContentType("text/html");
        descripcionHTML.setEditable(false);
        descripcionHTML.setText(idioma.descripcionPanelHistoriaMods());
        
        JScrollPane scrollDescripcion = new JScrollPane(descripcionHTML);
        scrollDescripcion.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDescripcion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDescripcion.setPreferredSize(new Dimension(600, 100)); // Altura fija
        
        textoPanel.add(scrollDescripcion, BorderLayout.CENTER);
        
        // Panel para imagen y texto
        JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        contenedor.setOpaque(false);
        contenedor.add(textoPanel);
        
        // Cargar y mostrar imagen
        try {
            Path rutaImagen = MonitorDePID.carpeta.resolve("imagenes/clio.png");
            if (!Files.exists(rutaImagen)) {
                MonitorDePID.copiarACarpetaDesdeJar("/imagenes/clio.png", rutaImagen.toFile());
            }
            
            if (Files.exists(rutaImagen)) {
                ImageIcon icono = new ImageIcon(rutaImagen.toAbsolutePath().toString());
                Image escalado = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                JLabel etiquetaImagen = new JLabel(new ImageIcon(escalado));
                contenedor.add(etiquetaImagen);
            }
        } catch (Exception e) {
            CrashDetectorLogger.log("Error cargando clio.png: " + e.getMessage());
        }
        
        // Asegurar tamaño mínimo
        panelInferior.add(contenedor, BorderLayout.CENTER);
        panelInferior.setMinimumSize(new Dimension(100, 100));
        
        // Agregar al frame principal
        add(panelInferior, BorderLayout.PAGE_END);
    }



    // Método para mostrar la GUI
    public static void mostrarGUIHistorialMods() {
        SwingUtilities.invokeLater(() -> {
            HistoriaModsGUI gui = new HistoriaModsGUI();
            gui.setVisible(true);
        });
    }
}