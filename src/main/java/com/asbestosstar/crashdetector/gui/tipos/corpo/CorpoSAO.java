package com.asbestosstar.crashdetector.gui.tipos.corpo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public class CorpoSAO extends CorpoBase {
    private static final long serialVersionUID = 1L;
    public static String ID = "sao";
    
    // Configuraciones de color
    private ConfigColor colorFondoVentana;
    private ConfigColor colorTexto;
    private ConfigColor colorBoton;
    private ConfigColor colorCajaTexto;
    private ConfigColor colorEnlace;
    
    // Componentes de la interfaz
    private JPanel raizPanel;
    private JPanel panelIzquierdo;
    private JPanel panelDerecho;
    private JPanel panelConfiguracion;
    private JPanel panelBotones;
    private JLabel imagenSAO;
    private JComboBox<String> comboIdiomaRespaldo;
    private JCheckBox checkBuscardor;
    private JTextField campoNombreHerramienta;
    private JCheckBox checkCondenarPirata;
    private JButton botonLanzadoresRecomendados;
    private JButton botonLanzadoresDesaconsejados;
    private JButton botonModsRecomendados;
    private JButton botonModsDesaconsejados;
    private JButton botonAntiTamper;
    
    @Override
    public void init() {
        // Inicializar configuraciones de color
        colorFondoVentana = ConfigColor.de("tema.corpo.sao.color.fondo.ventana", 
            Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
        colorTexto = ConfigColor.de("tema.corpo.sao.color.texto",
            Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
        colorBoton = ConfigColor.de("tema.corpo.sao.color.boton",
            Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
        colorCajaTexto = ConfigColor.de("tema.corpo.sao.color.caja_texto",
            Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
        colorEnlace = ConfigColor.de("tema.corpo.sao.color.enlace",
            Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace()));
        
        // Construir la interfaz
        setModal(true);
        setAlwaysOnTop(true);
        setTitle(MonitorDePID.idioma.configuracionCorporativa());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));
        
        // Crear el contenido principal
        raizPanel = new JPanel(new BorderLayout(10, 10));
        raizPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel izquierdo para la imagen de SAO
        panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setPreferredSize(new Dimension(244, 338));
        
        imagenSAO = new JLabel();
        imagenSAO.setPreferredSize(new Dimension(244, 338));
        imagenSAO.setHorizontalAlignment(JLabel.CENTER);
        imagenSAO.setVerticalAlignment(JLabel.CENTER);
        panelIzquierdo.add(imagenSAO, BorderLayout.CENTER);
        
        // Panel derecho para la configuración
        panelDerecho = new JPanel(new BorderLayout(10, 10));
        
        // Panel de configuración
        panelConfiguracion = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Idioma de respaldo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panelConfiguracion.add(new JLabel(MonitorDePID.idioma.idiomaRespaldo()), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        comboIdiomaRespaldo = new JComboBox<>(new String[] {
            "Español", "English", "العربية", "Português", "فارسی", 
            "Русский", "简体中文", "Esperanto", "日本語", "한국어"
        });
        comboIdiomaRespaldo.setPreferredSize(new Dimension(150, 30));
        panelConfiguracion.add(comboIdiomaRespaldo, gbc);
        
        // Buscardor habilitado
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        panelConfiguracion.add(new JLabel(MonitorDePID.idioma.buscardorHabilitado()), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        checkBuscardor = new JCheckBox();
        panelConfiguracion.add(checkBuscardor, gbc);
        
        // Nombre de la herramienta
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        panelConfiguracion.add(new JLabel(MonitorDePID.idioma.nombreHerramienta()), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        campoNombreHerramienta = new JTextField();
        panelConfiguracion.add(campoNombreHerramienta, gbc);
        
        // Condenar piratería
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        panelConfiguracion.add(new JLabel(MonitorDePID.idioma.condenarPirateria()), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.7;
        checkCondenarPirata = new JCheckBox();
        panelConfiguracion.add(checkCondenarPirata, gbc);
        
        panelDerecho.add(panelConfiguracion, BorderLayout.NORTH);
        
        // Panel de botones
        panelBotones = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Lanzadores recomendados
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        botonLanzadoresRecomendados = new JButton(MonitorDePID.idioma.lanzadoresRecomendados());
        panelBotones.add(botonLanzadoresRecomendados, gbc);
        
        // Lanzadores desaconsejados
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        botonLanzadoresDesaconsejados = new JButton(MonitorDePID.idioma.lanzadoresDesaconsejados());
        panelBotones.add(botonLanzadoresDesaconsejados, gbc);
        
        // Mods recomendados
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        botonModsRecomendados = new JButton(MonitorDePID.idioma.modsRecomendados());
        panelBotones.add(botonModsRecomendados, gbc);
        
        // Mods desaconsejados
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        botonModsDesaconsejados = new JButton(MonitorDePID.idioma.modsDesaconsejados());
        panelBotones.add(botonModsDesaconsejados, gbc);
        
        // AntiTamper
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        botonAntiTamper = new JButton(MonitorDePID.idioma.antiTamper());
        panelBotones.add(botonAntiTamper, gbc);
        
        panelDerecho.add(panelBotones, BorderLayout.CENTER);
        
        // Agregar paneles a raíz
        raizPanel.add(panelIzquierdo, BorderLayout.WEST);
        raizPanel.add(panelDerecho, BorderLayout.CENTER);
        
        getContentPane().add(raizPanel);
        
        // Cargar imagen de SAO
        cargarImagenSAO();
        
        // Aplicar apariencia
        aplicarApariencia();
        
        // Establecer valores iniciales
        actualizarValores();
        
        // Agregar listeners de acción
        agregarListeners();
        
        pack();
        setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    private void cargarImagenSAO() {
        try {
            // Cargar imagen de SAO desde recursos
            java.net.URL url = Statics.carpeta.resolve("imagenes/sao.png").toFile().toURL();
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(244, 338, Image.SCALE_SMOOTH);
                imagenSAO.setIcon(new ImageIcon(img));
            } else {
                // Alternativa si no se encuentra la imagen
                imagenSAO.setText(MonitorDePID.idioma.imagenNoEncontrada());
                imagenSAO.setHorizontalAlignment(JLabel.CENTER);
                imagenSAO.setVerticalAlignment(JLabel.CENTER);
            }
        } catch (Exception e) {
            CrashDetectorLogger.logException(e);
            imagenSAO.setText(MonitorDePID.idioma.errorCargandoImagen());
            imagenSAO.setHorizontalAlignment(JLabel.CENTER);
            imagenSAO.setVerticalAlignment(JLabel.CENTER);
        }
    }
    
    @Override
    public void aplicarApariencia() {
        // Aplicar colores a todos los componentes
        getContentPane().setBackground(colorFondoVentana.obtener());
        raizPanel.setBackground(colorFondoVentana.obtener());
        
        panelIzquierdo.setBackground(colorFondoVentana.obtener());
        panelDerecho.setBackground(colorFondoVentana.obtener());
        panelConfiguracion.setBackground(colorFondoVentana.obtener());
        panelBotones.setBackground(colorFondoVentana.obtener());
        
        // Estilizar botones
        estilizarBoton(botonLanzadoresRecomendados);
        estilizarBoton(botonLanzadoresDesaconsejados);
        estilizarBoton(botonModsRecomendados);
        estilizarBoton(botonModsDesaconsejados);
        estilizarBoton(botonAntiTamper);
        
        // Estilizar campo de texto
        estilizarCampo(campoNombreHerramienta);
        
        // Actualizar apariencia del combo box
        comboIdiomaRespaldo.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
                java.awt.Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    label.setForeground(colorTexto.obtener());
                    label.setBackground(colorFondoVentana.obtener());
                    if (isSelected) {
                        label.setBackground(colorBoton.obtener());
                    }
                }
                return c;
            }
        });
        
        // Actualizar checkboxes
        checkBuscardor.setForeground(colorTexto.obtener());
        checkCondenarPirata.setForeground(colorTexto.obtener());
        
        // Forzar repintado
        revalidate();
        repaint();
    }
    
    @Override
    public String id() {
        return ID;
    }
    
    @Override
    public List<ElementoConfig> obtenerElementosConfigs() {
        List<ElementoConfig> elementos = new ArrayList<>();
        colorFondoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoVentana());
        colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
        colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
        colorCajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
        colorEnlace.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorEnlace());
        
        elementos.add(colorFondoVentana);
        elementos.add(colorTexto);
        elementos.add(colorBoton);
        elementos.add(colorCajaTexto);
        elementos.add(colorEnlace);
        
        return elementos;
    }
    
    private void actualizarValores() {
        // Establecer valores iniciales desde la configuración
        comboIdiomaRespaldo.setSelectedItem(Idioma.nombreDeIdiomaDesdeCondigo(
            obtenerIdiomaRespaldo()));
        checkBuscardor.setSelected(obtenerBuscardorHablicar());
        campoNombreHerramienta.setText(obtenerNombreCD());
        checkCondenarPirata.setSelected(obtenerCondenarPirata());
    }
    
    private void agregarListeners() {
        // Guardar idioma de respaldo al cambiar
        comboIdiomaRespaldo.addActionListener(e -> {
            String selected = (String) comboIdiomaRespaldo.getSelectedItem();
            String codigo = CrashDetectorGUI.obtenerCodigoIdioma(selected);
            escribirIdiomaRespaldo(codigo);
        });
        
        // Guardar configuración de Buscardor
        checkBuscardor.addActionListener(e -> {
            escribirBuscardorHablicar(checkBuscardor.isSelected());
        });
        
        // Guardar nombre de la herramienta
        campoNombreHerramienta.addActionListener(e -> {
            escribirNombreCD(campoNombreHerramienta.getText());
        });
        
        // Guardar configuración de condenar piratería
        checkCondenarPirata.addActionListener(e -> {
            escribirCondenarPirata(checkCondenarPirata.isSelected());
        });
        
        // Los botones solo muestran mensaje "Próximamente" por ahora
        java.awt.event.ActionListener proximamenteListener = e -> {
            JOptionPane.showMessageDialog(this, MonitorDePID.idioma.proximamente(), 
                MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
        };
        
        botonLanzadoresRecomendados.addActionListener(proximamenteListener);
        botonLanzadoresDesaconsejados.addActionListener(proximamenteListener);
        botonModsRecomendados.addActionListener(proximamenteListener);
        botonModsDesaconsejados.addActionListener(proximamenteListener);
        botonAntiTamper.addActionListener(proximamenteListener);
    }
    
    // Métodos auxiliares para estilización
    private void estilizarBoton(JButton btn) {
        estilizarBoton(btn, 10);
    }
    
    private void estilizarBoton(JButton btn, int paddingV) {
        if (!CrashDetectorGUI.esMac()) {
            btn.setBackground(colorBoton.obtener());
            btn.setForeground(colorTexto.obtener());
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(paddingV, 18, paddingV, 18));
        } else {
            btn.setContentAreaFilled(false);
        }
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }
    
    private void estilizarCampo(JTextField campo) {
        campo.setBackground(colorCajaTexto.obtener());
        campo.setForeground(colorTexto.obtener());
        campo.setBorder(BorderFactory.createLineBorder(colorBoton.obtener().darker(), 1));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }
}