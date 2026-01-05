package com.asbestosstar.crashdetector.gui.tipos.deshablicarverificaciones;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public class DeshabilitarVerificacionesAmaneKanata extends DeshablicarVerificaciones {
    private static final long serialVersionUID = 1L;
    public static String ID = "amane_kanata";
    public static File imagen = Statics.carpeta.resolve("imagenes/amane_kanata.png").toFile(); // 124 × 268 píxeles
    
    // Configuraciones de color
    private ConfigColor colorFondoVentana;
    private ConfigColor colorTexto;
    private ConfigColor colorBoton;
    private ConfigColor colorCajaTexto;
    private ConfigColor colorBordePanel;
    private ConfigColor colorVerificacionCorporativa;
    
    // Componentes de la interfaz
    private JTable tablaHabilitadas;
    private JTable tablaDeshabilitadas;
    private DefaultTableModel modeloHabilitadas;
    private DefaultTableModel modeloDeshabilitadas;
    private JButton botonMoverDerecha;
    private JButton botonMoverIzquierda;
    private JButton botonDeshabilitarNoCorporativas;
    private JPanel panelPrincipal;
    private JLabel imagenLabel;
    private JLabel etiquetaHabilitadas;
    private JLabel etiquetaDeshabilitadas;
    private JLabel etiquetaMensajeAmaneKanata;

    @Override
    public void init() {
        // Inicializar configuraciones de color
        colorFondoVentana = ConfigColor.de("tema.amane_kanata.color.fondo.ventana", 
            new Color(30, 30, 30));
        colorTexto = ConfigColor.de("tema.amane_kanata.color.texto",
            new Color(220, 220, 220));
        colorBoton = ConfigColor.de("tema.amane_kanata.color.boton",
            new Color(50, 60, 150));
        colorCajaTexto = ConfigColor.de("tema.amane_kanata.color.caja_texto",
            new Color(40, 40, 40));
        colorBordePanel = ConfigColor.de("tema.amane_kanata.color.borde_panel",
            new Color(60, 60, 60));
        colorVerificacionCorporativa = ConfigColor.de("tema.amane_kanata.color.verificacion_corporativa",
            new Color(50, 150, 50));
        
        setTitle(MonitorDePID.idioma.gestionVerificaciones());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(true);
        setSize(850, 650);
        setAlwaysOnTop(false);
        
        // Crear modelos de tablas
        modeloHabilitadas = new DefaultTableModel(new Object[]{
            MonitorDePID.idioma.idVerificacion(),
            MonitorDePID.idioma.nombreVerificacion(),
            MonitorDePID.idioma.codigoVerificacion(),
            MonitorDePID.idioma.documentacionVerificacion()
        }, 0);
        modeloDeshabilitadas = new DefaultTableModel(new Object[]{
            MonitorDePID.idioma.idVerificacion(),
            MonitorDePID.idioma.nombreVerificacion(),
            MonitorDePID.idioma.codigoVerificacion(),
            MonitorDePID.idioma.documentacionVerificacion()
        }, 0);
        
        // Crear tablas
        tablaHabilitadas = new JTable(modeloHabilitadas);
        tablaDeshabilitadas = new JTable(modeloDeshabilitadas);
        
        // Configurar tablas
        configurarTabla(tablaHabilitadas);
        configurarTabla(tablaDeshabilitadas);
        
        // Panel principal con GridBagLayout para mejor control de disposición
        panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        
        // Panel izquierdo para la imagen y mensaje
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setPreferredSize(new Dimension(180, 0));
        
        // Cargar y mostrar imagen
        imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagenLabel.setVerticalAlignment(SwingConstants.TOP);
        cargarImagen();
        
        // Mensaje sobre Amane Kanata
        etiquetaMensajeAmaneKanata = new JLabel(MonitorDePID.idioma.mensajeAmaneKanata());
        etiquetaMensajeAmaneKanata.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaMensajeAmaneKanata.setVerticalAlignment(SwingConstants.BOTTOM);
        etiquetaMensajeAmaneKanata.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        
        panelIzquierdo.add(imagenLabel, BorderLayout.CENTER);
        panelIzquierdo.add(etiquetaMensajeAmaneKanata, BorderLayout.SOUTH);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 6;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        panelPrincipal.add(panelIzquierdo, gbc);
        
        // Etiquetas para las secciones
        etiquetaHabilitadas = new JLabel(MonitorDePID.idioma.verificacionesHabilitadas());
        etiquetaHabilitadas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        etiquetaDeshabilitadas = new JLabel(MonitorDePID.idioma.verificacionesDeshabilitadas());
        etiquetaDeshabilitadas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Panel de botones de movimiento
        JPanel panelBotonesMovimiento = new JPanel(new GridBagLayout());
        panelBotonesMovimiento.setOpaque(false);
        
        botonMoverDerecha = new JButton(">>");
        botonMoverIzquierda = new JButton("<<");
        botonDeshabilitarNoCorporativas = new JButton(MonitorDePID.idioma.deshabilitarNoCorporativas());
        
        // Ajustar tamaño de botones
        Dimension botonSize = new Dimension(60, 30);
        botonMoverDerecha.setPreferredSize(botonSize);
        botonMoverIzquierda.setPreferredSize(botonSize);
        
        GridBagConstraints gbcBotones = new GridBagConstraints();
        gbcBotones.insets = new java.awt.Insets(5, 5, 5, 5);
        gbcBotones.gridx = 0;
        gbcBotones.gridy = 0;
        panelBotonesMovimiento.add(botonMoverIzquierda, gbcBotones);
        
        gbcBotones.gridy = 1;
        panelBotonesMovimiento.add(botonMoverDerecha, gbcBotones);
        
        // Añadir componentes al panel principal
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.8;
        gbc.weighty = 0.05;
        panelPrincipal.add(etiquetaHabilitadas, gbc);
        
        gbc.gridy = 1;
        gbc.weighty = 0.35;
        panelPrincipal.add(new JScrollPane(tablaHabilitadas), gbc);
        
        gbc.gridy = 2;
        gbc.weighty = 0.1;
        panelPrincipal.add(panelBotonesMovimiento, gbc);
        
        gbc.gridy = 3;
        gbc.weighty = 0.05;
        panelPrincipal.add(etiquetaDeshabilitadas, gbc);
        
        gbc.gridy = 4;
        gbc.weighty = 0.35;
        panelPrincipal.add(new JScrollPane(tablaDeshabilitadas), gbc);
        
        gbc.gridy = 5;
        gbc.weighty = 0.1;
        panelPrincipal.add(botonDeshabilitarNoCorporativas, gbc);
        
        // Aplicar fondo al panel principal
        panelPrincipal.setBackground(colorFondoVentana.obtener());
        
        // Añadir panel principal al contenido
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
        
        // Cargar datos iniciales
        cargarDatos();
        
        // Agregar listeners
        agregarListeners();
        
        // Centrar la ventana
        setLocationRelativeTo(null);
        
        // Aplicar apariencia
        recargarApariencia();
        
        this.setVisible(true);
    }
    
    /**
     * Carga la imagen de Amane Kanata
     */
    private void cargarImagen() {
        try {
            if (imagen.exists()) {
                ImageIcon icon = new ImageIcon(imagen.getAbsolutePath());
                Image img = icon.getImage();
                
                // Calcular nueva dimensión manteniendo proporción
                int newWidth = 160;
                int newHeight = (int) ((double) img.getHeight(null) / img.getWidth(null) * newWidth);
                
                img = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imagenLabel.setIcon(new ImageIcon(img));
            } else {
                imagenLabel.setText(MonitorDePID.idioma.imagenNoEncontrada());
            }
        } catch (Exception e) {
            imagenLabel.setText(MonitorDePID.idioma.errorCargandoImagen());
        }
    }
    
    /**
     * Configura la tabla para mostrar los datos correctamente
     * @param tabla Tabla a configurar
     */
    private void configurarTabla(JTable tabla) {
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(25);
        
        // Configurar ancho de columnas
        TableColumnModel columnModel = tabla.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100); // ID
        columnModel.getColumn(1).setPreferredWidth(180); // Nombre
        columnModel.getColumn(2).setPreferredWidth(80);  // Código
        columnModel.getColumn(3).setPreferredWidth(100); // Documentación
        
        // Personalizar renderizador para resaltar verificaciones corporativas
        tabla.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value != null ? value.toString() : "");
                label.setOpaque(true);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                
                // Obtener el ID de la verificación en esta fila
                String id = table.getModel().getValueAt(row, 0).toString();
                Verificaciones verificacion = obtenerVerificacionPorId(id);
                
                // Si es para corporativo y no está seleccionado, resaltar en verde
                if (verificacion != null && recomendadoParaCorperata(verificacion) && !isSelected) {
                    label.setBackground(colorVerificacionCorporativa.obtener());
                    label.setForeground(Color.WHITE);
                } else if (isSelected) {
                    label.setBackground(colorBoton.obtener());
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(table.getBackground());
                    label.setForeground(table.getForeground());
                }
                
                return label;
            }
        });
    }
    
    /**
     * Carga los datos en las tablas según la configuración actual
     */
    private void cargarDatos() {
        // Obtener todas las verificaciones
        HashSet<Verificaciones> todasVerificaciones = verificaciones();
        List<String> verificacionesDeshabilitadas = configValor();
        
        // Limpiar modelos
        modeloHabilitadas.setRowCount(0);
        modeloDeshabilitadas.setRowCount(0);
        
        // Añadir verificaciones a los modelos correspondientes
        for (Verificaciones ver : todasVerificaciones) {
            Object[] rowData = new Object[]{
                id(ver),
                nombre(ver),
                enlaceACodigo(ver) != null ? MonitorDePID.idioma.verCodigo() : "",
                enlaceDocs(ver) != null ? MonitorDePID.idioma.verDocumentacion() : ""
            };
            
            if (verificacionesDeshabilitadas.contains(id(ver))) {
                modeloDeshabilitadas.addRow(rowData);
            } else {
                modeloHabilitadas.addRow(rowData);
            }
        }
    }
    
    /**
     * Agrega los listeners a los componentes interactivos
     */
    private void agregarListeners() {
        // Mover de habilitadas a deshabilitadas
        botonMoverDerecha.addActionListener(e -> {
            int filaSeleccionada = tablaHabilitadas.getSelectedRow();
            if (filaSeleccionada >= 0) {
                Object[] fila = new Object[4];
                for (int i = 0; i < 4; i++) {
                    fila[i] = modeloHabilitadas.getValueAt(filaSeleccionada, i);
                }
                modeloDeshabilitadas.addRow(fila);
                modeloHabilitadas.removeRow(filaSeleccionada);
                
                // Actualizar configuración
                actualizarConfiguracion();
            } else {
                JOptionPane.showMessageDialog(this, 
                    MonitorDePID.idioma.seleccionaVerificacionDeshabilitar(),
                    MonitorDePID.idioma.informacion(),
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Mover de deshabilitadas a habilitadas
        botonMoverIzquierda.addActionListener(e -> {
            int filaSeleccionada = tablaDeshabilitadas.getSelectedRow();
            if (filaSeleccionada >= 0) {
                Object[] fila = new Object[4];
                for (int i = 0; i < 4; i++) {
                    fila[i] = modeloDeshabilitadas.getValueAt(filaSeleccionada, i);
                }
                modeloHabilitadas.addRow(fila);
                modeloDeshabilitadas.removeRow(filaSeleccionada);
                
                // Actualizar configuración
                actualizarConfiguracion();
            } else {
                JOptionPane.showMessageDialog(this, 
                    MonitorDePID.idioma.seleccionaVerificacionHabilitar(),
                    MonitorDePID.idioma.informacion(),
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Deshabilitar todas no corporativas
        botonDeshabilitarNoCorporativas.addActionListener(e -> {
            int count = 0;
            
            // Mover todas las verificaciones no corporativas a deshabilitadas
            for (int i = modeloHabilitadas.getRowCount() - 1; i >= 0; i--) {
                String id = (String) modeloHabilitadas.getValueAt(i, 0);
                Verificaciones ver = obtenerVerificacionPorId(id);
                
                if (ver != null && !recomendadoParaCorperata(ver)) {
                    Object[] fila = new Object[4];
                    for (int j = 0; j < 4; j++) {
                        fila[j] = modeloHabilitadas.getValueAt(i, j);
                    }
                    modeloDeshabilitadas.addRow(fila);
                    modeloHabilitadas.removeRow(i);
                    count++;
                }
            }
            
            if (count > 0) {
                JOptionPane.showMessageDialog(this, 
                    String.format(MonitorDePID.idioma.verificacionesNoCorporativasDeshabilitadas(), count),
                    MonitorDePID.idioma.operacionCompletada(),
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Actualizar configuración
                actualizarConfiguracion();
            } else {
                JOptionPane.showMessageDialog(this, 
                    MonitorDePID.idioma.noVerificacionesNoCorporativas(),
                    MonitorDePID.idioma.informacion(),
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    /**
     * Actualiza la configuración con las verificaciones deshabilitadas
     */
    private void actualizarConfiguracion() {
        ArrayList<String> idsDeshabilitadas = new ArrayList<>();
        
        for (int i = 0; i < modeloDeshabilitadas.getRowCount(); i++) {
            idsDeshabilitadas.add((String) modeloDeshabilitadas.getValueAt(i, 0));
        }
        
        escribirConfig(idsDeshabilitadas);
    }
    
    /**
     * Obtiene una verificación por su ID
     * @param id ID de la verificación
     * @return Verificación correspondiente o null si no existe
     */
    private Verificaciones obtenerVerificacionPorId(String id) {
        for (Verificaciones ver : verificaciones()) {
            if (id(ver).equals(id)) {
                return ver;
            }
        }
        return null;
    }
    
    /**
     * Aplica la apariencia a todos los componentes
     */
    @Override
    public void recargarApariencia() {
        // Aplicar colores a todos los componentes
        getContentPane().setBackground(colorFondoVentana.obtener());
        panelPrincipal.setBackground(colorFondoVentana.obtener());
        
        etiquetaHabilitadas.setForeground(colorTexto.obtener());
        etiquetaDeshabilitadas.setForeground(colorTexto.obtener());
        etiquetaMensajeAmaneKanata.setForeground(colorTexto.obtener());
        
        // Aplicar colores a las tablas
        tablaHabilitadas.setBackground(colorCajaTexto.obtener());
        tablaHabilitadas.setForeground(colorTexto.obtener());
        tablaHabilitadas.setGridColor(colorBordePanel.obtener());
        
        tablaDeshabilitadas.setBackground(colorCajaTexto.obtener());
        tablaDeshabilitadas.setForeground(colorTexto.obtener());
        tablaDeshabilitadas.setGridColor(colorBordePanel.obtener());
        
        // Estilizar botones
        estilizarBoton(botonMoverDerecha);
        estilizarBoton(botonMoverIzquierda);
        estilizarBoton(botonDeshabilitarNoCorporativas);
        
        // Forzar repintado
        revalidate();
        repaint();
    }
    
    /**
     * Estiliza un botón con la apariencia corporativa
     * @param btn Botón a estilizar
     */
    private void estilizarBoton(JButton btn) {
        if (!CrashDetectorGUI.esMac()) {
            btn.setBackground(colorBoton.obtener());
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        } else {
            btn.setContentAreaFilled(false);
        }
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
        colorBordePanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
        colorVerificacionCorporativa.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorVerificacionCorporativa());
        
        elementos.add(colorFondoVentana);
        elementos.add(colorTexto);
        elementos.add(colorBoton);
        elementos.add(colorCajaTexto);
        elementos.add(colorBordePanel);
        elementos.add(colorVerificacionCorporativa);
        
        return elementos;
    }




}