package com.asbestosstar.crashdetector.gui;

import java.awt.*;
import javax.swing.*;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.analizador.QuickFix;

/**
 * Representación visual de un QuickFix individual con alineación correcta
 */
public class ElementoQuickFix extends JPanel {
    private final JCheckBox checkBoxRetener = new JCheckBox();

    public ElementoQuickFix(QuickFix quickFix) {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Panel principal con diseño vertical
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setOpaque(false);

        // Etiqueta principal (título)
        JLabel etiquetaPrincipal = new JLabel(quickFix.etiqueta);
        etiquetaPrincipal.setFont(etiquetaPrincipal.getFont().deriveFont(Font.BOLD, 14));
        etiquetaPrincipal.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        if (!CrashDetectorGUI.esMac()) {
        	etiquetaPrincipal.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTitulo()));
        	etiquetaPrincipal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	etiquetaPrincipal.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }
        
        
        
        panelPrincipal.add(etiquetaPrincipal);

        // Panel para componentes verticales
        JPanel panelComponentes = new JPanel();
        panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));
        panelComponentes.setOpaque(false);

        // Checkbox de retener (si está presente)
        if (quickFix.tieneCheckbox) {
            JPanel panelRetener = new JPanel();
            panelRetener.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0)); // Alineación estricta a la izquierda
            panelRetener.setOpaque(false);
            panelRetener.add(new JLabel("Retener:"));
            panelRetener.add(checkBoxRetener);
            panelComponentes.add(panelRetener);

            // Listener para actualizar el estado
            checkBoxRetener.addItemListener(e -> 
                quickFix.tieneMantener = checkBoxRetener.isSelected()
            );
        }

        // Botones y etiquetas adicionales
        for (QuickFix.ComponenteGUI componente : quickFix.componentes) {
            JComponent comp = componente.crearComponente(() -> quickFix.tieneMantener);
            
            // Forzar alineación izquierda y control de tamaño
            Dimension prefSize = comp.getPreferredSize();
            comp.setAlignmentX(JComponent.LEFT_ALIGNMENT);
            
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setHorizontalAlignment(SwingConstants.LEFT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, prefSize.height));
            } else {
                comp.setMaximumSize(new Dimension(Integer.MAX_VALUE, prefSize.height));
            }
            
            panelComponentes.add(comp);
            panelComponentes.add(Box.createVerticalStrut(5)); // Espaciado uniforme
        }

        panelPrincipal.add(panelComponentes);
        add(panelPrincipal, BorderLayout.NORTH);
    }
}