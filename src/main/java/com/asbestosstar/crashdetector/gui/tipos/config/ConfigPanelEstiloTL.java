package com.asbestosstar.crashdetector.gui.tipos.config;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class ConfigPanelEstiloTL<PrincipalGUI> extends ConfigPanel<PrincipalGUI> {
    
    public static String ID = "tl_estilo_config";
    
    
    public ConfigPanelEstiloTL() {
   colorFondo = ConfigColor.de("config.color.fondo", Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
    colorTexto = ConfigColor.de("config.color.texto", Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
   colorBoton = ConfigColor.de("config.color.boton", Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
    colorCajaTexto = ConfigColor.de("config.color.cajaTexto", Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
    }
    
    
    @Override
    public void recargarApariencia() {
        // Estilo TL: fondo oscuro con detalles brillantes
      
        
        // Actualizar el color de fondo del panel
        setBackground(colorFondo.obtener());
        
        // Actualizar los colores de los componentes
        if (tabbedPane != null) {
            tabbedPane.setBackground(colorFondo.obtener().darker());
            tabbedPane.setForeground(colorTexto.obtener());
            
            // Actualizar cada pestaña
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                Component comp = tabbedPane.getTabComponentAt(i);
                if (comp instanceof JPanel) {
                    ((JPanel) comp).setBackground(colorFondo.obtener().darker());
                }
            }
        }
        
        // Actualizar el botón guardar y otros componentes
        Component[] components = getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                for (Component child : panel.getComponents()) {
                    if (child instanceof JButton) {
                        JButton btn = (JButton) child;
                        btn.setForeground(colorTexto.obtener());
                        btn.setBackground(colorBoton.obtener());
                    }
                    if (child instanceof JTextField) {
                        JTextField field = (JTextField) child;
                        field.setBackground(colorCajaTexto.obtener());
                        field.setForeground(colorTexto.obtener());
                    }
                }
            }
        }
        
        // Repintar la interfaz para aplicar los cambios
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
        
        // Agregar todos los elementos de configuración de color
        elementos.add(colorFondo);
        elementos.add(colorTexto);
        elementos.add(colorBoton);
        elementos.add(colorCajaTexto);
        
        return elementos;
    }
}