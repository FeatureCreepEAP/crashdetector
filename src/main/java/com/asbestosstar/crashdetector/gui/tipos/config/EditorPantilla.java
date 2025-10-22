package com.asbestosstar.crashdetector.gui.tipos.config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
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
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public class EditorPantilla extends JPanel implements CrashDetectorGUI{

	public static Map<String, Supplier<EditorPantilla>> GUIS = new HashMap<>();

	
    public JTextPane editorHTML;
    public JEditorPane vistaPrevia;
    public JButton botonGuardar;
    public JButton botonRestablecerPlantilla;
    public JButton botonCerrar;
    
    public boolean esMac = CrashDetectorGUI.esMac();
    public Config configuracion = Config.obtenerInstancia();
    public boolean actualizandoVista = false;
    public File archivoPlantilla;
    
    public Map<String, ConfigColor> colorMap = new HashMap<>();
    public JPanel panelConfiguracion;


    public void inicializarComponentes() {
        setLayout(new BorderLayout());
        setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        // Panel superior con botones
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSuperior.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        botonGuardar = new JButton(MonitorDePID.idioma.guardarTodo());
        configurarBoton(botonGuardar);
        botonGuardar.addActionListener(e -> guardarPlantilla());
        panelSuperior.add(botonGuardar);
        
        botonRestablecerPlantilla = new JButton(MonitorDePID.idioma.restablecerPlantilla());
        configurarBoton(botonRestablecerPlantilla);
        botonRestablecerPlantilla.addActionListener(e -> restablecerPlantilla());
        panelSuperior.add(botonRestablecerPlantilla);
        
        botonCerrar = new JButton(MonitorDePID.idioma.omitirYCerrar());
        configurarBoton(botonCerrar);
        botonCerrar.addActionListener(e -> cerrarEditor());
        panelSuperior.add(botonCerrar);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel principal (división horizontal)
        JSplitPane splitPanePrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPanePrincipal.setDividerLocation(0.65);
        
        // Panel izquierdo: editor y vista previa (editor más alto)
        JSplitPane splitPaneEditor = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPaneEditor.setDividerLocation(0.75); // Editor ocupa 75% del espacio vertical
        
        // Editor HTML
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
        
        // Vista previa
        JPanel panelVistaPrevia = new JPanel(new BorderLayout());
        panelVistaPrevia.setBorder(BorderFactory.createTitledBorder("Vista Previa"));
        panelVistaPrevia.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        vistaPrevia = new JEditorPane();
        vistaPrevia.setEditable(false);
        vistaPrevia.setContentType("text/html");
        vistaPrevia.setBackground(Color.WHITE);
        
        JScrollPane scrollVistaPrevia = new JScrollPane(vistaPrevia);
        scrollVistaPrevia.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panelVistaPrevia.add(scrollVistaPrevia, BorderLayout.CENTER);
        
        splitPaneEditor.setTopComponent(panelEditor);
        splitPaneEditor.setBottomComponent(panelVistaPrevia);
        
        // Panel derecho: configuración de colores e imágenes
        panelConfiguracion = new JPanel(new BorderLayout());
        panelConfiguracion.setBorder(BorderFactory.createTitledBorder("Configuración de Colores e Imágenes"));
        panelConfiguracion.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        // Panel de colores
        JPanel panelColores = new JPanel(new GridLayout(0, 1, 5, 5));
        panelColores.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        panelColores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inicializarConfiguracionColores(panelColores);
        
        // Panel de imágenes con ruta
        JPanel panelImagenes = new JPanel(new BorderLayout());
        panelImagenes.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        Path rutaImagenes = MonitorDePID.carpeta.resolve("imagenes");
        String rutaFormateada = rutaImagenes.toString().replace("\\", "/");
        panelImagenes.setBorder(BorderFactory.createTitledBorder(
            "Imágenes (" + rutaFormateada + ")"));
        
        JPanel panelContenidoImagenes = new JPanel(new GridLayout(0, 1, 5, 5));
        panelContenidoImagenes.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        panelContenidoImagenes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] imagenes = {"gura.png", "nanashi_mumei.png", "shion.png"};
        for (String imagen : imagenes) {
            panelContenidoImagenes.add(crearPanelImagen(imagen));
        }
        
        panelImagenes.add(panelContenidoImagenes, BorderLayout.CENTER);
        
        panelConfiguracion.add(panelColores, BorderLayout.CENTER);
        panelConfiguracion.add(panelImagenes, BorderLayout.SOUTH);
        
        splitPanePrincipal.setLeftComponent(splitPaneEditor);
        splitPanePrincipal.setRightComponent(panelConfiguracion);
        
        add(splitPanePrincipal, BorderLayout.CENTER);
        
        // Listener para actualizar vista previa
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
        
        // Listener para resaltar sintaxis
        editorHTML.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                resaltarSintaxis();
            }
        });
        
        resaltarSintaxis();
    }
    
    public void inicializarConfiguracionColores(JPanel panelCampos) {
        colorMap.put("enlace", ConfigColor.de("color_enlace", Config.convertirAColor(configuracion.obtenerColorEnlace())));
        colorMap.put("titulosConsolas", ConfigColor.de("color_de_titulos_de_consolas", Config.convertirAColor(configuracion.obtenerColorDeTitulosDeConsolas())));
        colorMap.put("error", ConfigColor.de("color_error", Config.convertirAColor(configuracion.obtenerColorError())));
        colorMap.put("advertencia", ConfigColor.de("color_advertencia", Config.convertirAColor(configuracion.obtenerColorAdvertencia())));
        colorMap.put("info", ConfigColor.de("color_info", Config.convertirAColor(configuracion.obtenerColorInfo())));
        colorMap.put("titulo", ConfigColor.de("color_titulo", Config.convertirAColor(configuracion.obtenerColorTitulo())));
        
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorEnlace(), colorMap.get("enlace")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorTitulosConsolas(), colorMap.get("titulosConsolas")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorError(), colorMap.get("error")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorAdvertencia(), colorMap.get("advertencia")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorInfo(), colorMap.get("info")));
        panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorTitulo(), colorMap.get("titulo")));
    }
    
    public JPanel crearPanelImagen(String nombreImagen) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        // Nombre de la imagen
        JLabel label = new JLabel(nombreImagen);
        label.setForeground(Config.convertirAColor(configuracion.obtenerColorTexto()));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        panel.add(label, BorderLayout.NORTH);
        
        // Panel para vista previa y botón (horizontal)
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.X_AXIS));
        panelContenido.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Vista previa de la imagen con relación de aspecto 200:112 (1.78:1)
        JLabel previewLabel = new JLabel();
        // Tamaño 100x56 mantiene la relación de aspecto 200:112
        previewLabel.setPreferredSize(new Dimension(100, 56));
        previewLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        actualizarVistaPreviaImagen(nombreImagen, previewLabel);
        
        // Botón para restablecer imagen
        JButton botonRestablecer = new JButton(MonitorDePID.idioma.restablecer());
        configurarBoton(botonRestablecer);
        botonRestablecer.setPreferredSize(new Dimension(80, 25));
        botonRestablecer.setAlignmentY(Component.CENTER_ALIGNMENT);
        botonRestablecer.addActionListener(e -> restablecerImagen(nombreImagen, previewLabel));
        
        panelContenido.add(previewLabel);
        panelContenido.add(Box.createHorizontalStrut(10));
        panelContenido.add(botonRestablecer);
        
        panel.add(panelContenido, BorderLayout.CENTER);
        return panel;
    }
    
    public void actualizarVistaPreviaImagen(String nombreImagen, JLabel previewLabel) {
        File imagenFile = MonitorDePID.carpeta.resolve("imagenes").resolve(nombreImagen).toFile();
        
        if (imagenFile.exists()) {
            try {
                BufferedImage img = ImageIO.read(imagenFile);
                // Mantener relación de aspecto 200:112 (1.78:1)
                int ancho = 100;
                int alto = (int) (ancho * 0.56); // 100 * (112/200) = 56
                Image scaledImage = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                previewLabel.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                previewLabel.setText("Error");
            }
        } else {
            previewLabel.setText("No existe");
        }
    }
    
    public void restablecerImagen(String nombreImagen, JLabel previewLabel) {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            MonitorDePID.idioma.restablecerImagenMensjae(nombreImagen),
            MonitorDePID.idioma.restablecer(),
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion != JOptionPane.YES_OPTION) return;
        
        String rutaRecurso = "/imagenes/" + nombreImagen;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(rutaRecurso)) {
            if (is == null) {
                JOptionPane.showMessageDialog(this, 
                    "No se encontró la imagen en el recurso: " + rutaRecurso,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            File destino = MonitorDePID.carpeta.resolve("imagenes").resolve(nombreImagen).toFile();
            destino.getParentFile().mkdirs();
            
            try (OutputStream os = new java.io.FileOutputStream(destino)) {
                byte[] buffer = new byte[4096];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                "Imagen restablecida: " + nombreImagen,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            
            actualizarVistaPreviaImagen(nombreImagen, previewLabel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al restablecer la imagen: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void configurarBoton(JButton boton) {
        if (!esMac) {
            boton.setForeground(Config.convertirAColor(configuracion.obtenerColorTexto()));
            boton.setBackground(Config.convertirAColor(configuracion.obtenerColorBoton()));
            boton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            boton.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        }
        boton.setFocusPainted(false);
    }

    public void cargarContenidoPlantilla() {
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
                JOptionPane.showMessageDialog(this, 
                    "Error al cargar la plantilla desde disco: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
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
                JOptionPane.showMessageDialog(this, 
                    "No se encontró la plantilla. Restablezca usando el botón 'Restablecer Plantilla'.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void resaltarSintaxis() {
        if (actualizandoVista) return;
        actualizandoVista = true;
        
        StyledDocument doc = editorHTML.getStyledDocument();
        String text = editorHTML.getText();
        
        SimpleAttributeSet normal = new SimpleAttributeSet();
        StyleConstants.setForeground(normal, Config.convertirAColor(configuracion.obtenerColorTexto()));
        doc.setCharacterAttributes(0, text.length(), normal, true);
        
        // Resaltar {constructor}
        int inicio = 0;
        while ((inicio = text.indexOf("{constructor}", inicio)) != -1) {
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setForeground(style, new Color(0, 120, 212));
            StyleConstants.setBold(style, true);
            doc.setCharacterAttributes(inicio, 13, style, false);
            inicio += 13;
        }
        
        // Resaltar {mensaje_ayudar}
        inicio = 0;
        while ((inicio = text.indexOf("{mensaje_ayudar}", inicio)) != -1) {
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setForeground(style, new Color(153, 0, 153));
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
            
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setForeground(style, new Color(153, 0, 153));
            doc.setCharacterAttributes(abertura, cierre - abertura + 1, style, false);
            
            inicio = cierre + 1;
        }
        
        actualizandoVista = false;
    }

    public void actualizarVistaPrevia() {
        if (actualizandoVista) return;
        actualizandoVista = true;
        
        vistaPrevia.removeAll();
        
        String contenido = editorHTML.getText();
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
        
        vistaPrevia.setText(contenidoVista);
        vistaPrevia.setCaretPosition(0);
        
        actualizandoVista = false;
    }

    public JPanel crearCampoDeColor(String nombre, ConfigColor configColor) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        JLabel label = new JLabel(nombre);
        label.setForeground(Config.convertirAColor(configuracion.obtenerColorTexto()));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        panel.add(label, BorderLayout.WEST);
        
        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputPanel.setBackground(Config.convertirAColor(configuracion.obtenerColorFondo()));
        
        JTextField textField = crearCampoTextoConfiguracion(Config.colorAHexHtml(configColor.obtener()), configColor);
        textField.setPreferredSize(new Dimension(80, 25));
        inputPanel.add(textField, BorderLayout.CENTER);
        
        PanelPrevisualizacionColor colorPreview = new PanelPrevisualizacionColor(textField, configColor);
        inputPanel.add(colorPreview, BorderLayout.EAST);
        
        panel.add(inputPanel, BorderLayout.CENTER);
        return panel;
    }
    
    public JTextField crearCampoTextoConfiguracion(String valorInicial, ConfigColor configColor) {
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
                    if (texto.startsWith("#")) {
                        texto = texto.substring(1);
                    }
                    
                    if (texto.length() == 3 || texto.length() == 6) {
                        if (texto.matches("[0-9A-Fa-f]+")) {
                            Color color = Color.decode("#" + texto);
                            configColor.escribir(color);
                        }
                    }
                } catch (Exception ex) {
                    // Ignorar durante la edición
                }
            }
        });
        
        if (!esMac) {
            field.setBackground(Config.convertirAColor(configuracion.obtenerColorCajaTexto()));
            field.setForeground(Config.convertirAColor(configuracion.obtenerColorTexto()));
        }
        
        return field;
    }

    public void guardarPlantilla() {
        if (archivoPlantilla == null || !archivoPlantilla.exists()) {
            archivoPlantilla = MonitorDePID.carpeta.resolve("pantilla.htm").toFile();
        }
        
        try {
            try (java.io.FileWriter writer = new java.io.FileWriter(archivoPlantilla)) {
                writer.write(editorHTML.getText());
            }
            
            JOptionPane.showMessageDialog(this, 
                "Plantilla guardada en: " + archivoPlantilla.getAbsolutePath(), 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void restablecerPlantilla() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            MonitorDePID.idioma.restablecerPlantillaMensaje(),
            MonitorDePID.idioma.confirmacion(),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
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
                    
                    try (java.io.FileWriter writer = new java.io.FileWriter(archivoPlantilla)) {
                        writer.write(contenido.toString());
                    }
                    
                    editorHTML.setText(contenido.toString());
                    resaltarSintaxis();
                    actualizarVistaPrevia();
                    
                    JOptionPane.showMessageDialog(this, 
                        "Plantilla restablecida correctamente.", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al restablecer: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void cerrarEditor() {
        if (SwingUtilities.getWindowAncestor(this) instanceof java.awt.Dialog) {
            ((java.awt.Dialog) SwingUtilities.getWindowAncestor(this)).dispose();
        }
    }
    
    public class PanelPrevisualizacionColor extends JPanel {
        private JTextField textField;
        private ConfigColor configColor;
        
        public PanelPrevisualizacionColor(JTextField textField, ConfigColor configColor) {
            this.textField = textField;
            this.configColor = configColor;
            setPreferredSize(new Dimension(25, 25));
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            setBackground(configColor.obtener());
            
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
            
            String hexColor = String.format("%06X", (0xFFFFFF & color.getRGB()));
            textField.setText(hexColor);
            actualizarVistaPrevia();
        }
    }


	@Override
	public TipoGUI tipo() {
		// TODO Auto-generated method stub
		return TipoGUI.EDITOR_PLANTILLA;
	}

	@Override
	public void recargarApariencia() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
        inicializarComponentes();
        cargarContenidoPlantilla();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		// TODO Auto-generated method stub
		return null;
	}
}