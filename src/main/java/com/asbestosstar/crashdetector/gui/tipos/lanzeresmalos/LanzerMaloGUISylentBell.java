package com.asbestosstar.crashdetector.gui.tipos.lanzeresmalos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.detectorlanzer.DetectorLanzer;

public class LanzerMaloGUISylentBell extends LanzerMaloGUI {
    private static final long serialVersionUID = 1L;
    public static String ID = "sylent_bell";
    public static File imagen = Statics.carpeta.resolve("imagenes/sylent_bell.png").toFile(); // 137 × 207 píxeles
    
    // Componentes de la interfaz
    private JTable tablaRecomendados;
    private JTable tablaNoRecomendados;
    private DefaultTableModel modeloRecomendados;
    private DefaultTableModel modeloNoRecomendados;
    private JButton botonMoverDerecha;
    private JButton botonMoverIzquierda;
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JLabel imagenLabel;
    private JPanel panelPrincipal;
    private JLabel etiquetaRecomendados;
    private JLabel etiquetaNoRecomendados;
    
    // Configuraciones de color
    private ConfigColor colorFondoVentana;
    private ConfigColor colorTexto;
    private ConfigColor colorBoton;
    private ConfigColor colorTabla;
    private ConfigColor colorResaltoDesaconsejado;
    private ConfigColor colorBordePanel;
    
    @Override
    public void init() {
        // Inicializar configuraciones de color
        colorFondoVentana = ConfigColor.de("tema.sylent_bell.color.fondo.ventana", new Color(30, 30, 30));
        colorTexto = ConfigColor.de("tema.sylent_bell.color.texto", new Color(220, 220, 220));
        colorBoton = ConfigColor.de("tema.sylent_bell.color.boton", new Color(50, 60, 150));
        colorTabla = ConfigColor.de("tema.sylent_bell.color.tabla", new Color(40, 40, 40));
        colorResaltoDesaconsejado = ConfigColor.de("tema.sylent_bell.color.resalto.desaconsejado", new Color(180, 50, 50));
        colorBordePanel = ConfigColor.de("tema.sylent_bell.color.borde.panel", new Color(70, 70, 70));
        
        setTitle(MonitorDePID.idioma.lanzadoresDesaconsejados());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(true);
        setSize(900, 650);
        setAlwaysOnTop(false);
        
        // Cargar datos iniciales
        cargarDatos();
        
        // Crear modelos de tablas
        modeloRecomendados = new DefaultTableModel(new Object[]{MonitorDePID.idioma.nombreLanzador()}, 0);
        modeloNoRecomendados = new DefaultTableModel(new Object[]{MonitorDePID.idioma.nombreLanzador(), MonitorDePID.idioma.motivo()}, 0);
        
        // Crear tablas
        tablaRecomendados = new JTable(modeloRecomendados);
        tablaNoRecomendados = new JTable(modeloNoRecomendados);
        
        // Configurar tablas
        configurarTabla(tablaRecomendados);
        configurarTabla(tablaNoRecomendados);
        
        // Panel principal
        panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        
        // Panel izquierdo para la imagen
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setPreferredSize(new Dimension(200, 0));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        
        // Cargar y mostrar imagen
        imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagenLabel.setVerticalAlignment(SwingConstants.TOP);
        cargarImagen();
        
        panelIzquierdo.add(imagenLabel, BorderLayout.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 6;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        panelPrincipal.add(panelIzquierdo, gbc);
        
        // Etiquetas para las secciones
        etiquetaRecomendados = new JLabel(MonitorDePID.idioma.lanzadoresRecomendados());
        etiquetaRecomendados.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        etiquetaNoRecomendados = new JLabel(MonitorDePID.idioma.lanzadoresNoRecomendados());
        etiquetaNoRecomendados.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Panel de botones de movimiento
        JPanel panelBotonesMovimiento = new JPanel(new GridBagLayout());
        panelBotonesMovimiento.setOpaque(false);
        
        botonMoverDerecha = new JButton(MonitorDePID.idioma.moverADesaconsejados());
        botonMoverIzquierda = new JButton(MonitorDePID.idioma.moverARecomendados());
        
        // Ajustar tamaño de botones
        Dimension botonSize = new Dimension(150, 30);
        botonMoverDerecha.setPreferredSize(botonSize);
        botonMoverIzquierda.setPreferredSize(botonSize);
        
        GridBagConstraints gbcBotones = new GridBagConstraints();
        gbcBotones.insets = new java.awt.Insets(5, 5, 5, 5);
        gbcBotones.gridx = 0;
        gbcBotones.gridy = 0;
        gbcBotones.fill = GridBagConstraints.HORIZONTAL;
        panelBotonesMovimiento.add(botonMoverIzquierda, gbcBotones);
        
        gbcBotones.gridy = 1;
        panelBotonesMovimiento.add(botonMoverDerecha, gbcBotones);
        
        // Scroll panes para las tablas
        JScrollPane scrollRecomendados = new JScrollPane(tablaRecomendados);
        JScrollPane scrollNoRecomendados = new JScrollPane(tablaNoRecomendados);
        
        // Panel de botones de acción
        JPanel panelBotonesAccion = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botonGuardar = new JButton(MonitorDePID.idioma.guardarCambios());
        botonCancelar = new JButton(MonitorDePID.idioma.cancelar());
        panelBotonesAccion.add(botonGuardar);
        panelBotonesAccion.add(botonCancelar);
        
        // Añadir componentes al panel principal
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.75;
        gbc.weighty = 0.05;
        panelPrincipal.add(etiquetaRecomendados, gbc);
        
        gbc.gridy = 1;
        gbc.weighty = 0.35;
        panelPrincipal.add(scrollRecomendados, gbc);
        
        gbc.gridy = 2;
        gbc.weighty = 0.1;
        panelPrincipal.add(panelBotonesMovimiento, gbc);
        
        gbc.gridy = 3;
        gbc.weighty = 0.05;
        panelPrincipal.add(etiquetaNoRecomendados, gbc);
        
        gbc.gridy = 4;
        gbc.weighty = 0.35;
        panelPrincipal.add(scrollNoRecomendados, gbc);
        
        gbc.gridy = 5;
        gbc.weighty = 0.1;
        panelPrincipal.add(panelBotonesAccion, gbc);
        
        // Añadir panel principal al contenido
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
        
        // Cargar datos en las tablas
        cargarDatosEnTablas();
        
        // Agregar listeners
        agregarListeners();
        
        // Centrar la ventana
        setLocationRelativeTo(null);
        
        // Aplicar apariencia
        recargarApariencia();
        
        this.setVisible(true);
    }
    
    /**
     * Carga la imagen de Sylent Bell
     */
    private void cargarImagen() {
        try {
            if (imagen.exists()) {
                ImageIcon icon = new ImageIcon(imagen.getAbsolutePath());
                Image img = icon.getImage();
                
                // Calcular nueva dimensión manteniendo proporción
                int newWidth = 180;
                int newHeight = (int) ((double) img.getHeight(null) / img.getWidth(null) * newWidth);
                
                img = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imagenLabel.setIcon(new ImageIcon(img));
            } else {
                imagenLabel.setText(MonitorDePID.idioma.imagenNoEncontrada());
                imagenLabel.setForeground(colorTexto.obtener());
            }
        } catch (Exception e) {
            imagenLabel.setText(MonitorDePID.idioma.errorCargandoImagen());
            imagenLabel.setForeground(colorTexto.obtener());
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
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        // Configurar ancho de columnas
        if (tabla == tablaRecomendados) {
            tabla.getColumnModel().getColumn(0).setPreferredWidth(350); // Nombre
        } else {
            tabla.getColumnModel().getColumn(0).setPreferredWidth(250); // Nombre
            tabla.getColumnModel().getColumn(1).setPreferredWidth(250); // Motivo
        }
        
        // Personalizar renderizador para resaltar lanzadores desaconsejados
        if (tabla == tablaRecomendados) {
            tabla.setDefaultRenderer(Object.class, new TableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(JTable table, Object value, 
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel label = new JLabel(value != null ? value.toString() : "");
                    label.setOpaque(true);
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                    label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                    
                    // Obtener el ID del lanzador en esta fila
                    String id = table.getModel().getValueAt(row, 0).toString();
                    
                    // Si CrashDetector lo desaconseja y no está seleccionado, resaltar en rojo
                    if (esDesaconsejadoPorCrashDetector(id) && !isSelected) {
                        label.setBackground(colorResaltoDesaconsejado.obtener());
                        label.setForeground(Color.WHITE);
                    } else if (isSelected) {
                        label.setBackground(colorBoton.obtener());
                        label.setForeground(Color.WHITE);
                    } else {
                        label.setBackground(colorTabla.obtener());
                        label.setForeground(colorTexto.obtener());
                    }
                    
                    return label;
                }
            });
        } else {
            tabla.setDefaultRenderer(Object.class, new TableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(JTable table, Object value, 
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel label = new JLabel(value != null ? value.toString() : "");
                    label.setOpaque(true);
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                    label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                    
                    if (isSelected) {
                        label.setBackground(colorBoton.obtener());
                        label.setForeground(Color.WHITE);
                    } else {
                        label.setBackground(colorTabla.obtener());
                        label.setForeground(colorTexto.obtener());
                    }
                    
                    return label;
                }
            });
        }
    }
    
    /**
     * Carga los datos en las tablas
     */
    private void cargarDatosEnTablas() {
        // Limpiar modelos
        modeloRecomendados.setRowCount(0);
        modeloNoRecomendados.setRowCount(0);
        
        // Cargar lanzadores recomendados
        for (String id : recomendados) {
            modeloRecomendados.addRow(new Object[]{id});
        }
        
        // Cargar lanzadores no recomendados
        for (Map.Entry<String, Map<String, String>> entry : noRecomendados.entrySet()) {
            String id = entry.getKey();
            Map<String, String> motivos = entry.getValue();
            
            String motivoMostrar = "";
            if (!motivos.isEmpty()) {
                // Obtener motivo en el idioma actual o por defecto
                String langActual = MonitorDePID.idioma.codigo();
                if (motivos.containsKey(langActual)) {
                    motivoMostrar = motivos.get(langActual);
                } else if (motivos.containsKey("es")) {
                    motivoMostrar = motivos.get("es");
                } else {
                    motivoMostrar = motivos.values().iterator().next();
                }
            }
            
            modeloNoRecomendados.addRow(new Object[]{id, motivoMostrar});
        }
    }
    
    /**
     * Agrega los listeners a los componentes interactivos
     */
    private void agregarListeners() {
        // Mover de recomendados a no recomendados
        botonMoverDerecha.addActionListener(e -> {
            int filaSeleccionada = tablaRecomendados.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String id = (String) modeloRecomendados.getValueAt(filaSeleccionada, 0);
                Map<String, String> motivos = obtenerMotivosPredeterminados(id);
                moverANoRecomendados(id, motivos);
                cargarDatosEnTablas();
            } else {
                JOptionPane.showMessageDialog(this, 
                    MonitorDePID.idioma.seleccionaLanzadorMover(),
                    MonitorDePID.idioma.informacion(),
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Mover de no recomendados a recomendados
        botonMoverIzquierda.addActionListener(e -> {
            int filaSeleccionada = tablaNoRecomendados.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String id = (String) modeloNoRecomendados.getValueAt(filaSeleccionada, 0);
                moverARecomendados(id);
                cargarDatosEnTablas();
            } else {
                JOptionPane.showMessageDialog(this, 
                    MonitorDePID.idioma.seleccionaLanzadorMover(),
                    MonitorDePID.idioma.informacion(),
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Guardar cambios
        botonGuardar.addActionListener(e -> {
            guardarDatos();
            JOptionPane.showMessageDialog(this, 
                MonitorDePID.idioma.cambiosGuardadosExitosamente(),
                MonitorDePID.idioma.informacion(),
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        // Cancelar cambios
        botonCancelar.addActionListener(e -> {
            dispose();
        });
    }
    
    /**
     * Obtiene motivos predeterminados para un lanzador basado en la información de CrashDetector
     */
    private Map<String, String> obtenerMotivosPredeterminados(String id) {
        Map<String, String> motivos = new HashMap<>();
        
        for (DetectorLanzer lanzador : lanzadores) {
            if (lanzador.id().equals(id)) {
                // Obtener motivos en diferentes idiomas
                String motivoEs = MonitorDePID.idioma.motivoDesaconsejoPredeterminadoEs(lanzador.id());
                String motivoEn = MonitorDePID.idioma.motivoDesaconsejoPredeterminadoEn(lanzador.id());
                String motivoPt = MonitorDePID.idioma.motivoDesaconsejoPredeterminadoPt(lanzador.id());
                
                motivos.put("es", motivoEs);
                motivos.put("en", motivoEn);
                motivos.put("pt", motivoPt);
                
                break;
            }
        }
        
        return motivos;
    }
    
    @Override
    public void recargarApariencia() {
        // Aplicar colores a todos los componentes
        getContentPane().setBackground(colorFondoVentana.obtener());
        panelPrincipal.setBackground(colorFondoVentana.obtener());
        
        etiquetaRecomendados.setForeground(colorTexto.obtener());
        etiquetaNoRecomendados.setForeground(colorTexto.obtener());
        
        // Aplicar colores a las tablas
        tablaRecomendados.setBackground(colorTabla.obtener());
        tablaRecomendados.setForeground(colorTexto.obtener());
        tablaRecomendados.setGridColor(colorBordePanel.obtener());
        
        tablaNoRecomendados.setBackground(colorTabla.obtener());
        tablaNoRecomendados.setForeground(colorTexto.obtener());
        tablaNoRecomendados.setGridColor(colorBordePanel.obtener());
        
        // Estilizar botones
        estilizarBoton(botonMoverDerecha);
        estilizarBoton(botonMoverIzquierda);
        estilizarBoton(botonGuardar);
        estilizarBoton(botonCancelar);
        
        // Forzar repintado
        revalidate();
        repaint();
    }
    
    /**
     * Estiliza un botón con la apariencia corporativa
     * @param btn Botón a estilizar
     */
    private void estilizarBoton(JButton btn) {
        btn.setBackground(colorBoton.obtener());
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(colorBordePanel.obtener(), 1));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }
    
    @Override
    public String id() {
        return ID;
    }



	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		// TODO Auto-generated method stub
		ArrayList ret= new ArrayList();
	    ret.add(colorFondoVentana);
	    ret.add(colorTexto);
	    ret.add(colorBoton);
	    ret.add(colorTabla);
	    ret.add(colorResaltoDesaconsejado);
	    ret.add(colorBordePanel);
	    
		
		return ret;
	}
}