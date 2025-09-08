package com.asbestosstar.crashdetector.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * Panel lateral que dibuja números de línea junto al área de texto,
 * ajustando automáticamente el ancho según los dígitos necesarios.
 */
/**
 * Panel lateral que dibuja números de línea junto al área de texto,
 * ajustando automáticamente el ancho según los dígitos necesarios
 * (hasta 100000 → 6 dígitos).
 */
public class NumeradorDeLineas extends JPanel {
    private final JTextPane textPane;

    public NumeradorDeLineas(JTextPane textPane) {
        this.textPane = textPane;
        setBackground(new Color(25, 25, 25));
        setForeground(Color.LIGHT_GRAY);
    }

    @Override
    public Dimension getPreferredSize() {
        int lineas = textPane.getDocument().getDefaultRootElement().getElementCount();
        // calcular cuántos dígitos son necesarios (máximo 6, mínimo 3)
        int digitos = Math.min(6, Math.max(3, String.valueOf(lineas).length()));

        FontMetrics fm = getFontMetrics(textPane.getFont());
        int ancho = fm.charWidth('0') * digitos + 10; // margen de 10px
        return new Dimension(ancho, Integer.MAX_VALUE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FontMetrics fm = g.getFontMetrics(textPane.getFont());
        int alturaLinea = fm.getHeight();

        int start = 0;
        try {
            int offset = textPane.viewToModel(new java.awt.Point(0, 0));
            start = textPane.getDocument().getDefaultRootElement().getElementIndex(offset);
        } catch (Exception ignored) {}

        int lineasVisibles = textPane.getHeight() / alturaLinea;
        int y = fm.getAscent();

        for (int i = 0; i < lineasVisibles + 1; i++) {
            int numero = start + i + 1;
            String texto = String.valueOf(numero);
            g.drawString(texto, getPreferredSize().width - fm.stringWidth(texto) - 5, y + i * alturaLinea);
        }
    }
}
