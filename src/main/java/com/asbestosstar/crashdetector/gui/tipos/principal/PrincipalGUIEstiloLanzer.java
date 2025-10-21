package com.asbestosstar.crashdetector.gui.tipos.principal;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class PrincipalGUIEstiloLanzer extends PrincipalGUI {

    public static String ID = "estilo_lanzer";
    
    // Campos públicos para los colores de configuración
    public ConfigColor colorFondo;
    public ConfigColor colorTexto;
    public ConfigColor colorBoton;
    public ConfigColor colorCajaTexto;
    public ConfigColor colorEnlace;

    @Override
    public String id() {
        return ID;
    }

    /**
     * Recarga la apariencia de la interfaz de usuario aplicando los colores
     * configurados.
     * 
     * Este método actualiza todos los componentes de la interfaz con los colores
     * actuales de la configuración. Debe ser llamado después de que los valores de
     * configuración hayan cambiado para que los cambios se reflejen en la interfaz.
     */
    @Override
    public void recargarApariencia() {
        // Obtener los colores actuales de la configuración
        Color fondo = colorFondo.obtener();
        Color texto = colorTexto.obtener();
        Color boton = colorBoton.obtener();
        Color cajaTexto = colorCajaTexto.obtener();
        Color enlace = colorEnlace.obtener();
        
        // Actualizar el color de fondo de la ventana principal
        this.getContentPane().setBackground(fondo);
        
        // Actualizar el color de fondo del contenido principal
        contenidoPrincipal.setBackground(fondo);
        
        // Actualizar el color del texto en el visor HTML
        pantalla.setForeground(texto);
        pantalla.setCaretColor(texto);
        
        // Actualizar los colores de los botones
        botonVolver.setForeground(texto);
        botonVolver.setBackground(boton);
        
        // Actualizar los colores de los botones en la barra lateral derecha
 //       for (JButton btn : botons_de_barra_lateral_derecha_initalizado.values()) {
  //          btn.setForeground(texto);
   //         btn.setBackground(boton);
    //    }TODO
        
        // Actualizar el color de fondo del scroll pane
        scrollPane.getViewport().setBackground(cajaTexto);
        
        // Actualizar el color de los enlaces en el visor HTML
        pantalla.setForeground(enlace);
        
        // Actualizar el color de fondo de los paneles inferiores
        Component[] components = this.getContentPane().getComponents();
        if (components.length > 1) {
            Component panelInferior = components[1];
            if (panelInferior instanceof JPanel) {
                panelInferior.setBackground(fondo);
                
                // Actualizar componentes dentro del panel inferior
                for (Component comp : ((JPanel) panelInferior).getComponents()) {
                    if (comp instanceof JComponent) {
                        ((JComponent) comp).setBackground(fondo);
                    }
                    if (comp instanceof JPanel) {
                        for (Component innerComp : ((JPanel) comp).getComponents()) {
                            if (innerComp instanceof JComponent) {
                                ((JComponent) innerComp).setBackground(fondo);
                            }
                        }
                    }
                }
            }
        }
        
        // Actualizar el color de fondo de la barra lateral derecha
        if (components.length > 2) {
            Component barraLateral = components[2];
            if (barraLateral instanceof JPanel) {
                barraLateral.setBackground(boton.darker());
            }
        }
        
        // Actualizar el color de los botones en el panel de botones derecho
        if (components.length > 1 && components[1] instanceof JPanel) {
            JPanel panelInferior = (JPanel) components[1];
            Component[] subComponents = panelInferior.getComponents();
            if (subComponents.length > 2 && subComponents[2] instanceof JPanel) {
                JPanel panelBotonesDerecha = (JPanel) subComponents[2];
                for (Component comp : panelBotonesDerecha.getComponents()) {
                    if (comp instanceof JButton) {
                        JButton btn = (JButton) comp;
                        btn.setBackground(fondo);
                        btn.setForeground(texto);
                    }
                }
            }
        }
        
        // Repintar la interfaz para aplicar los cambios
        this.revalidate();
        this.repaint();
    }

    /**
     * Obtiene los elementos de configuración relacionados con los colores de la
     * interfaz.
     * 
     * Este método devuelve una lista de elementos que pueden ser modificados en el
     * panel de configuración. Cada elemento representa un color configurable de la
     * interfaz de usuario.
     * 
     * @return Lista de elementos de configuración de colores
     */
    @Override
    public List<ElementoConfig> obtenerElementosConfigs() {
        List<ElementoConfig> elementos = new ArrayList<>();
        
        // Agregar todos los elementos de configuración de color
        elementos.add(colorFondo);
        elementos.add(colorTexto);
        elementos.add(colorBoton);
        elementos.add(colorCajaTexto);
        elementos.add(colorEnlace);
        
        return elementos;
    }
}