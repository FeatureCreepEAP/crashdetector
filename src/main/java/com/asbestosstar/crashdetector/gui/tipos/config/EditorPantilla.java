package com.asbestosstar.crashdetector.gui.tipos.config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public class EditorPantilla extends JPanel {
    private JTextPane editorHTML;
    private JPanel vistaPrevia;
    private JButton botonResetearPlantilla;
    private JButton botonResetearImágenes;
    private JButton botonCerrar;
    
    private boolean esMac = CrashDetectorGUI.esMac();
    private Config configuracion = Config.obtenerInstancia();
    private boolean actualizandoVista = false;
    private File archivoPlantilla;
    
    // Color configuration panels
    private Map<String, ConfigColor> colorMap = new HashMap<>();
    private JPanel panelColores;

    public EditorPantilla() {
        inicializarComponentes();
        cargarContenidoPlantilla();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        // Panel superior con botones
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSuperior.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Botón para resetear la plantilla
        botonResetearPlantilla = new JButton("Resetear Plantilla");
        configurarBoton(botonResetearPlantilla);
        botonResetearPlantilla.addActionListener(e -> resetearPlantilla());
        panelSuperior.add(botonResetearPlantilla);
        
        // Botón para resetear las imágenes
        botonResetearImágenes = new JButton("Resetear Imágenes");
        configurarBoton(botonResetearImágenes);
        botonResetearImágenes.addActionListener(e -> resetearImágenes());
        panelSuperior.add(botonResetearImágenes);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // Main split pane - horizontal (editor/preview on left, color config on right)
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(0.65);
        
        // Create the left side (editor and preview)
        JSplitPane editorSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        editorSplitPane.setDividerLocation(0.5);
        
        // Editor de HTML (izquierda)
        JPanel panelEditor = new JPanel(new BorderLayout());
        panelEditor.setBorder(BorderFactory.createTitledBorder("Editor HTML"));
        panelEditor.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        editorHTML = new JTextPane();
        editorHTML.setFont(new Font("Monospaced", Font.PLAIN, 13));
        editorHTML.setForeground(Config.convertirAColor(configuracion.obtenerColorTexto()));
        editorHTML.setBackground(Config.convertirAColor(configuracion.obtenerColorCajaTexto()));
        editorHTML.setCaretColor(Config.convertirAColor(configuracion.obtenerColorTexto()));
        
        JScrollPane scrollEditor = new JScrollPane(editorHTML);
        scrollEditor.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panelEditor.add(scrollEditor, BorderLayout.CENTER);
        
        // Vista previa (derecha)
        JPanel panelVistaPrevia = new JPanel(new BorderLayout());
        panelVistaPrevia.setBorder(BorderFactory.createTitledBorder("Vista Previa"));
        panelVistaPrevia.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        vistaPrevia = new JPanel(new BorderLayout());
        vistaPrevia.setBackground(Color.WHITE);
        vistaPrevia.setPreferredSize(new Dimension(400, 500));
        
        JScrollPane scrollVistaPrevia = new JScrollPane(vistaPrevia);
        scrollVistaPrevia.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panelVistaPrevia.add(scrollVistaPrevia, BorderLayout.CENTER);
        
        editorSplitPane.setTopComponent(panelEditor);
        editorSplitPane.setBottomComponent(panelVistaPrevia);
        
        // Create the color configuration panel (right side)
        panelColores = new JPanel(new BorderLayout());
        panelColores.setBorder(BorderFactory.createTitledBorder("Configuración de Colores"));
        panelColores.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        // Main panel for color fields
        JPanel panelCampos = new JPanel(new GridLayout(0, 1, 5, 5));
        panelCampos.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Initialize color configuration
        inicializarConfiguracionColores(panelCampos);
        
        panelColores.add(panelCampos, BorderLayout.CENTER);
        
        // Add both panels to the main split pane
        mainSplitPane.setLeftComponent(editorSplitPane);
        mainSplitPane.setRightComponent(panelColores);
        
        add(mainSplitPane, BorderLayout.CENTER);
        
        // Panel inferior con botón de cierre
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        botonCerrar = new JButton("Cerrar");
        configurarBoton(botonCerrar);
        botonCerrar.addActionListener(e -> cerrarEditor());
        panelInferior.add(botonCerrar);
        
        add(panelInferior, BorderLayout.SOUTH);
        
        // Agregar listener para actualizar vista previa en tiempo real
        editorHTML.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarVistaPrevia();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarVistaPrevia();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarVistaPrevia();
            }
        });
        
        // Agregar KeyListener para resaltar sintaxis
        editorHTML.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // No necesario para resaltado en tiempo real
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // No necesario para resaltado en tiempo real
            }

            @Override
            public void keyReleased(KeyEvent e) {
                resaltarSintaxis();
            }
        });
        
        // Configurar el editor con el contenido inicial
        resaltarSintaxis();
    }
    
    private void inicializarConfiguracionColores(JPanel panelCampos) {
        Color colorTexto = Config.convertirAColor(configuracion.obtenerColorTexto());
        
        // Map of color names to ConfigColor objects
        // REEMPLAZADAS LAS CLAVES POR LAS REALES QUE EXISTEN EN EL SISTEMA DE CONFIGURACIÓN
    //    colorMap.put("fondo", ConfigColor.de("color_fondo", Config.convertirAColor(configuracion.obtenerColorFondo())));
        colorMap.put("texto", ConfigColor.de("color_texto", Config.convertirAColor(configuracion.obtenerColorTexto())));
      //  colorMap.put("boton", ConfigColor.de("color_boton", Config.convertirAColor(configuracion.obtenerColorBoton())));
        colorMap.put("cajaTexto", ConfigColor.de("color_caja_texto", Config.convertirAColor(configuracion.obtenerColorCajaTexto())));
        colorMap.put("enlace", ConfigColor.de("color_enlace", Config.convertirAColor(configuracion.obtenerColorEnlace())));
        colorMap.put("titulosConsolas", ConfigColor.de("color_de_titulos_de_consolas", Config.convertirAColor(configuracion.obtenerColorDeTitulosDeConsolas())));
        colorMap.put("error", ConfigColor.de("color_error", Config.convertirAColor(configuracion.obtenerColorError())));
        colorMap.put("advertencia", ConfigColor.de("color_advertencia", Config.convertirAColor(configuracion.obtenerColorAdvertencia())));
        colorMap.put("info", ConfigColor.de("color_info", Config.convertirAColor(configuracion.obtenerColorInfo())));
        colorMap.put("titulo", ConfigColor.de("color_titulo", Config.convertirAColor(configuracion.obtenerColorTitulo())));
        colorMap.put("enlaceTexto", ConfigColor.de("color_enlace_texto", Config.convertirAColor(configuracion.obtenerColorEnlace())));
        
        // Agregar campos de color con vista previa
        //panelCampos.add(crearCampoDeColor("Color de fondo (#RRGGBB):", colorMap.get("fondo")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorTexto(), colorMap.get("texto")));
       // panelCampos.add(crearCampoDeColor("Color de botón (#RRGGBB):", colorMap.get("boton")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorCajaTexto(), colorMap.get("cajaTexto")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorEnlace(), colorMap.get("enlace")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorTitulosConsolas(), colorMap.get("titulosConsolas")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorError(), colorMap.get("error")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorAdvertencia(), colorMap.get("advertencia")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorInfo(), colorMap.get("info")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorTitulo(), colorMap.get("titulo")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorEnlaceTexto(), colorMap.get("enlaceTexto")));
    }

    private void configurarBoton(JButton boton) {
        if (!esMac) {
            boton.setForeground(Config.convertirAColor(configuracion.obtenerColorTexto()));
            boton.setBackground(Config.convertirAColor(configuracion.obtenerColorBoton()));
            boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }
        boton.setFocusPainted(false);
    }

    private void cargarContenidoPlantilla() {
        // Primero intentamos cargar desde disco
        archivoPlantilla = MonitorDePID.carpeta.resolve("pantilla.htm").toFile();
        
        if (archivoPlantilla.exists()) {
            try {
                StringBuilder contenido = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new java.io.FileReader(archivoPlantilla))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        contenido.append(linea).append("\n");
                    }
                }
                editorHTML.setText(contenido.toString());
                resaltarSintaxis();
                actualizarVistaPrevia();
            } catch (IOException e) {
                // Si hay error al cargar desde disco, mostramos mensaje
                JOptionPane.showMessageDialog(this, 
                    "Error al cargar la plantilla desde disco: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Si no existe en disco, intentamos cargar desde el JAR
            try {
                String rutaPlantilla = "pantilla.htm";
                InputStream is = getClass().getClassLoader().getResourceAsStream(rutaPlantilla);
                if (is != null) {
                    StringBuilder contenido = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                        String linea;
                        while ((linea = reader.readLine()) != null) {
                            contenido.append(linea).append("\n");
                        }
                    }
                    editorHTML.setText(contenido.toString());
                    resaltarSintaxis();
                    actualizarVistaPrevia();
                }
            } catch (IOException e) {
                // Si no se encuentra en el JAR, mostramos mensaje
                JOptionPane.showMessageDialog(this, 
                    "No se encontró la plantilla en disco ni en el JAR. " +
                    "Por favor, restablece la plantilla desde el botón 'Resetear Plantilla'.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void resaltarSintaxis() {
        if (actualizandoVista) return;
        actualizandoVista = true;
        
        StyledDocument doc = editorHTML.getStyledDocument();
        String text = editorHTML.getText();
        
        // Limpiar estilos
        SimpleAttributeSet normal = new SimpleAttributeSet();
        StyleConstants.setForeground(normal, Config.convertirAColor(configuracion.obtenerColorTexto()));
        doc.setCharacterAttributes(0, text.length(), normal, true);
        
        // Resaltar {constructor}
        int inicio = 0;
        while ((inicio = text.indexOf("{constructor}", inicio)) != -1) {
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setForeground(style, new Color(0, 120, 212)); // Azul
            StyleConstants.setBold(style, true);
            doc.setCharacterAttributes(inicio, 13, style, false);
            inicio += 13;
        }
        
        // Resaltar {mensaje_ayudar}
        inicio = 0;
        while ((inicio = text.indexOf("{mensaje_ayudar}", inicio)) != -1) {
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setForeground(style, new Color(153, 0, 153)); // Morado
            StyleConstants.setBold(style, true);
            doc.setCharacterAttributes(inicio, 16, style, false);
            inicio += 16;
        }
        
        // Resaltar etiquetas HTML
        inicio = 0;
        while (inicio < text.length()) {
            int abertura = text.indexOf("<", inicio);
            if (abertura == -1) break;
            
            int cierre = text.indexOf(">", abertura);
            if (cierre == -1) break;
            
            // Resaltar la etiqueta completa
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setForeground(style, new Color(153, 0, 153)); // Morado para etiquetas
            doc.setCharacterAttributes(abertura, cierre - abertura + 1, style, false);
            
            inicio = cierre + 1;
        }
        
        actualizandoVista = false;
    }

    private void actualizarVistaPrevia() {
        if (actualizandoVista) return;
        actualizandoVista = true;
        
        // Limpiar vista previa
        vistaPrevia.removeAll();
        
        // Crear panel para la vista previa
        JPanel panelVista = new JPanel(new BorderLayout());
        panelVista.setBackground(Color.WHITE);
        
        // Simular contenido con los marcadores reemplazados
        String contenido = editorHTML.getText();
        
        // Crear el ejemplo de análisis con todos los colores configurables
        String colorError = configuracion.obtenerColorError();
        String colorAdvertencia = configuracion.obtenerColorAdvertencia();
        String colorInfo = configuracion.obtenerColorInfo();
        String colorTitulo = configuracion.obtenerColorTitulo();
        String colorTitulosConsolas = configuracion.obtenerColorDeTitulosDeConsolas();
        String colorEnlace = configuracion.obtenerColorEnlace();
        
        String ejemploAnalisis = 
            "<div style='color:#" + colorTitulo + "; font-weight: bold; margin-bottom: 10px;'>Ejemplo de Análisis</div>" +
            "<div style='color:#" + colorTitulosConsolas + "; font-weight: bold; margin-bottom: 5px;'>Título de Consola de Ejemplo</div>" +
            "<div style='color:#" + colorError + "'>[EJEMPLO] Error crítico: No se pudo cargar el mod 'ExampleMod'</div>" +
            "<div style='color:#" + colorAdvertencia + "'>[EJEMPLO] Advertencia: Conflictos entre mods detectados</div>" +
            "<div style='color:#" + colorInfo + "'>[EJEMPLO] Información: 5 soluciones potenciales encontradas</div>";
        
        String ejemploAyuda = 
            "<div style='color:#" + colorEnlace + "; margin-top: 20px;'>" +
            "¿Necesitas ayuda? Usa el botón Compartir para obtener enlaces a los registros y a los resultados." +
            "</div>";
        
        String contenidoVista = contenido
            .replace("{constructor}", ejemploAnalisis)
            .replace("{mensaje_ayudar}", ejemploAyuda);
        
        // Mostrar contenido HTML en vista previa
        JLabel etiquetaVista = new JLabel(contenidoVista);
        etiquetaVista.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        etiquetaVista.setVerticalAlignment(JLabel.TOP);
        
        // Configurar etiqueta para que renderice HTML
        etiquetaVista.setText(contenidoVista);
        
        panelVista.add(etiquetaVista, BorderLayout.CENTER);
        
        // Añadir una sección de ejemplo de imágenes
        JPanel panelImágenes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelImágenes.setBackground(Color.WHITE);
        
        // Crear etiquetas de imágenes con ejemplos
        try {
            JLabel imgGura = new JLabel("gura.png");
            imgGura.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            imgGura.setPreferredSize(new Dimension(200, 112));
            imgGura.setHorizontalAlignment(JLabel.CENTER);
            imgGura.setVerticalAlignment(JLabel.CENTER);
            panelImágenes.add(imgGura);
            
            JLabel imgMumei = new JLabel("nanashi_mumei.png");
            imgMumei.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            imgMumei.setPreferredSize(new Dimension(200, 112));
            imgMumei.setHorizontalAlignment(JLabel.CENTER);
            imgMumei.setVerticalAlignment(JLabel.CENTER);
            panelImágenes.add(imgMumei);
            
            JLabel imgShion = new JLabel("shion.png");
            imgShion.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            imgShion.setPreferredSize(new Dimension(200, 112));
            imgShion.setHorizontalAlignment(JLabel.CENTER);
            imgShion.setVerticalAlignment(JLabel.CENTER);
            panelImágenes.add(imgShion);
        } catch (Exception e) {
            // En caso de error al cargar imágenes, mostrar texto
            JLabel imgError = new JLabel("Imágenes no disponibles");
            imgError.setForeground(Color.RED);
            imgError.setHorizontalAlignment(JLabel.CENTER);
            panelImágenes.add(imgError);
        }
        
        panelVista.add(panelImágenes, BorderLayout.SOUTH);
        
        vistaPrevia.add(panelVista);
        vistaPrevia.revalidate();
        vistaPrevia.repaint();
        
        actualizandoVista = false;
    }

    private JPanel crearCampoDeColor(String nombre, ConfigColor configColor) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        // Etiqueta del campo
        JLabel label = new JLabel(nombre);
        label.setForeground(Config.convertirAColor(configuracion.obtenerColorTexto()));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        panel.add(label, BorderLayout.WEST);
        
        // Panel para el campo de texto y el selector
        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputPanel.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        // Campo de texto para el valor HEX
        JTextField textField = crearCampoTextoConfiguracion(Config.colorAHexHtml(configColor.obtener()), configColor);
        textField.setPreferredSize(new Dimension(80, 25));
        inputPanel.add(textField, BorderLayout.CENTER);
        
        // Cuadro de vista previa de color
        PanelPrevisualizacionColor colorPreview = new PanelPrevisualizacionColor(textField, configColor);
        inputPanel.add(colorPreview, BorderLayout.EAST);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JTextField crearCampoTextoConfiguracion(String valorInicial, ConfigColor configColor) {
        JTextField field = new JTextField(valorInicial);
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                procesarCambio();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                procesarCambio();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                procesarCambio();
            }
            
            private void procesarCambio() {
                String texto = field.getText();
                if (texto == null || texto.isEmpty()) return;
                
                try {
                    // Si comienza con #, quitarlo para nuestro sistema
                    if (texto.startsWith("#")) {
                        texto = texto.substring(1);
                    }
                    
                    // Validar que sea un hex válido (3 o 6 caracteres)
                    if (texto.length() == 3 || texto.length() == 6) {
                        // Asegurar que solo contiene caracteres hexadecimales
                        if (texto.matches("[0-9A-Fa-f]+")) {
                            // Convertir a color para validar
                            Color color = Color.decode("#" + texto);
                            configColor.escribir(color);
                        }
                    }
                } catch (Exception ex) {
                    // Ignorar, el usuario está editando
                }
            }
        });
        
        if (!esMac) {
            field.setBackground(Config.convertirAColor(configuracion.obtenerColorCajaTexto()));
            field.setForeground(Config.convertirAColor(configuracion.obtenerColorTexto()));
        }
        
        return field;
    }

    private void resetearPlantilla() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que quieres resetear la plantilla a los valores predeterminados?",
            "Confirmar Reset",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // Cargar contenido desde el JAR
                String rutaPlantilla = "pantilla.htm";
                InputStream is = getClass().getClassLoader().getResourceAsStream(rutaPlantilla);
                if (is != null) {
                    StringBuilder contenido = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                        String linea;
                        while ((linea = reader.readLine()) != null) {
                            contenido.append(linea).append("\n");
                        }
                    }
                    
                    // Escribir contenido al archivo en disco
                    try (java.io.FileWriter writer = new java.io.FileWriter(archivoPlantilla)) {
                        writer.write(contenido.toString());
                    }
                    
                    // Actualizar la interfaz
                    editorHTML.setText(contenido.toString());
                    resaltarSintaxis();
                    actualizarVistaPrevia();
                    
                    // Mostrar mensaje de éxito
                    JOptionPane.showMessageDialog(this, 
                        "La plantilla se ha restablecido correctamente.", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al resetear la plantilla: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void resetearImágenes() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Estás seguro de que quieres resetear las imágenes a los valores predeterminados?",
            "Confirmar Reset",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // Rutas de las imágenes
                String[] imagenes = {"gura.png", "nanashi_mumei.png", "shion.png"};
                int imagenesRestablecidas = 0;
                
                for (String imagen : imagenes) {
                    String rutaRecurso = "/imagenes/" + imagen;
                    InputStream is = getClass().getClassLoader().getResourceAsStream(rutaRecurso);
                    
                    if (is != null) {
                        File destino = new File(MonitorDePID.carpeta.resolve("imagenes").toString(), imagen);
                        destino.getParentFile().mkdirs();
                        
                        // Copiar desde el JAR al disco
                        try (java.io.OutputStream os = new java.io.FileOutputStream(destino)) {
                            byte[] buffer = new byte[4096];
                            int length;
                            while ((length = is.read(buffer)) > 0) {
                                os.write(buffer, 0, length);
                            }
                        }
                        
                        imagenesRestablecidas++;
                    }
                }
                
                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(this, 
                    "Se han restablecido " + imagenesRestablecidas + " imágenes.", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                actualizarVistaPrevia();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al resetear las imágenes: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cerrarEditor() {
        // Lógica para cerrar el editor (en este contexto, probablemente cerrar el diálogo)
        if (SwingUtilities.getWindowAncestor(this) instanceof java.awt.Dialog) {
            ((java.awt.Dialog) SwingUtilities.getWindowAncestor(this)).dispose();
        }
    }
    
    private class PanelPrevisualizacionColor extends JPanel {
        private JTextField textField;
        private ConfigColor configColor;
        
        public PanelPrevisualizacionColor(JTextField textField, ConfigColor configColor) {
            this.textField = textField;
            this.configColor = configColor;
            setPreferredSize(new Dimension(25, 25));
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            setBackground(configColor.obtener());
            
            // Añadir listener para abrir el selector de color
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Color nuevoColor = JColorChooser.showDialog(PanelPrevisualizacionColor.this, 
                            "Seleccionar Color", configColor.obtener());
                    if (nuevoColor != null) {
                        actualizarColor(nuevoColor);
                    }
                }
            });
        }
        
        public void actualizarColor(Color color) {
            configColor.escribir(color);
            setBackground(color);
            repaint();
            
            // Convertir a formato hex sin #
            String hexColor = String.format("%06X", (0xFFFFFF & color.getRGB()));
            textField.setText(hexColor);
            
            // Actualizar la vista previa del HTML
            actualizarVistaPrevia();
        }
    }
}