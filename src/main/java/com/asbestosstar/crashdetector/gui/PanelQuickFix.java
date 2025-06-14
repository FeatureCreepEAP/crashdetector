package com.asbestosstar.crashdetector.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.asbestosstar.crashdetector.analyzador.QuickFix;

/**
 * Panel principal que contiene múltiples QuickFix con scroll
 */
public class PanelQuickFix extends JScrollPane {
    private final JPanel panelContenedor;

    public PanelQuickFix() {
        panelContenedor = new JPanel();
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
        panelContenedor.setOpaque(false);
        
        getViewport().setOpaque(false);
        setOpaque(false);
        setViewportView(panelContenedor);
        getVerticalScrollBar().setUnitIncrement(16);
        
        // Añadir margen superior e inferior
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Añade un nuevo QuickFix al panel
     */
    public void agregarQuickFix(QuickFix quickFix) {
        if (panelContenedor.getComponentCount() > 0) {
            // Separador entre elementos
            JSeparator separador = new JSeparator();
            separador.setForeground(Color.LIGHT_GRAY);
            panelContenedor.add(separador);
        }
        
        ElementoQuickFix elemento = new ElementoQuickFix(quickFix);
        panelContenedor.add(elemento);
    }

    /**
     * Limpia todos los QuickFix del panel
     */
    public void limpiar() {
        panelContenedor.removeAll();
    }
}