package com.asbestosstar.crashdetector.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;

/**
 * Panel principal que contiene múltiples QuickFix con scroll.
 * Al fondo se muestra UNA sola imagen (escalada a 1024x1024).
 */
public class PanelQuickFix extends JScrollPane {
    private final JPanel panelContenedor;

    // Panel «pie» que siempre queda al final con la imagen
    private final JPanel piePanel;

    public PanelQuickFix() {
        panelContenedor = new JPanel();
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
        panelContenedor.setOpaque(false);

        getViewport().setOpaque(false);
        setOpaque(false);
        setViewportView(panelContenedor);
        getVerticalScrollBar().setUnitIncrement(16);

        // Margen
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Pie con UNA imagen 1024x1024 (siempre al final) ---
        piePanel = new JPanel();
        piePanel.setOpaque(false);
        piePanel.setLayout(new BoxLayout(piePanel, BoxLayout.Y_AXIS));
        piePanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        JLabel img1024 = crearEtiquetaImagenEscalada(
        	    MonitorDePID.carpeta.resolve("imagenes/demonslayers.png").toString(),
        	    32,
        	    32
        	);
        if (img1024 != null) {
            img1024.setAlignmentX(Component.CENTER_ALIGNMENT);
            piePanel.add(img1024);
        } else {
            JLabel placeholder = new JLabel("(No se pudo cargar demonslayers.png)");
            placeholder.setAlignmentX(Component.CENTER_ALIGNMENT);
            piePanel.add(placeholder);
        }

        // Añadir el pie SOLO una vez
        panelContenedor.add(piePanel);
    }

    /**
     * Añade un QuickFix siempre antes del pie.
     */
    public void agregarQuickFix(QuickFix quickFix) {
        // Asegurar que el pie está presente una sola vez
        if (piePanel.getParent() != panelContenedor) {
            panelContenedor.add(piePanel);
        }

        int idxPie = panelContenedor.getComponentCount() - 1; // pie al final

        // Separador si ya hay otros QuickFix antes del pie
        if (idxPie > 0) {
            JSeparator separador = new JSeparator();
            separador.setForeground(Color.LIGHT_GRAY);
            panelContenedor.add(separador, idxPie);
            idxPie++; // ajustar índice porque insertamos el separador
        }

        // Insertar QuickFix justo antes del pie
        ElementoQuickFix elemento = new ElementoQuickFix(quickFix);
        panelContenedor.add(elemento, idxPie);

        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    /**
     * Limpia los QuickFix y deja solo el pie con UNA imagen.
     */
    public void limpiar() {
        panelContenedor.removeAll();
        panelContenedor.add(piePanel); // volver a poner el pie una sola vez
        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    // --- Utilidad de imagen ---

    /** Crea una etiqueta con la imagen escalada a w×h; devuelve null si falla. */
    private JLabel crearEtiquetaImagenEscalada(String ruta, int w, int h) {
        try {
            Image img = ImageIO.read(new File(ruta));
            if (img == null) return null;
            Image esc = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(esc));
        } catch (IOException ex) {
            return null;
        }
    }
}
