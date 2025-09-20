package com.asbestosstar.crashdetector.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Panel lateral que dibuja números de línea junto a un JList<String>. Diseñado
 * para ser rápido con ficheros grandes — dibuja solo las filas visibles y
 * recalcula su ancho cuando cambia el modelo.
 */
public class NumeradorDeLineas extends JPanel {
	private final JList<String> lista;
	private int anchoCache = -1;

	public NumeradorDeLineas(JList<String> lista) {
		this.lista = lista;
		setBackground(new Color(25, 25, 25));
		setForeground(Color.LIGHT_GRAY);
		setFont(lista.getFont());

		// Escuchar cambios en el modelo para actualizar tamaño/repintado
		lista.getModel().addListDataListener(new ListDataListener() {
			@Override
			public void contentsChanged(ListDataEvent e) {
				recalcularYActualizar();
			}

			@Override
			public void intervalAdded(ListDataEvent e) {
				recalcularYActualizar();
			}

			@Override
			public void intervalRemoved(ListDataEvent e) {
				recalcularYActualizar();
			}
		});

		// También cuando la fuente cambia en la lista (por si acaso)
		lista.addPropertyChangeListener("font", evt -> {
			setFont(lista.getFont());
			recalcularYActualizar();
		});

		// Forzar primera medida
		recalcularYActualizar();
	}

	private void recalcularYActualizar() {
		anchoCache = -1; // forzar recálculo en getPreferredSize()
		revalidate();
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		int lineas = lista.getModel().getSize();
		int digitos = Math.min(6, Math.max(3, String.valueOf(Math.max(1, lineas)).length()));
		FontMetrics fm = getFontMetrics(getFont());
		int ancho = fm.charWidth('0') * digitos + 10; // margen
		return new Dimension(ancho, Integer.MAX_VALUE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// fondo
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());

		// si no hay nada visible, no dibujamos
		int primero = lista.getFirstVisibleIndex();
		int ultimo = lista.getLastVisibleIndex();
		if (primero == -1 || ultimo == -1) {
			return;
		}

		FontMetrics fm = g.getFontMetrics(lista.getFont());
		int alturaLinea;
		// preferir fixedCellHeight si está establecido (mejor rendimiento)
		int fixedH = lista.getFixedCellHeight();
		if (fixedH > 0) {
			alturaLinea = fixedH;
		} else {
			// intentar obtener altura por bounds de la primera celda visible
			Rectangle r = lista.getCellBounds(primero, primero);
			alturaLinea = (r != null) ? r.height : fm.getHeight();
		}

		// posición X base para alinear a la derecha
		int anchoPreferido = getPreferredSize().width;
		int baseX = anchoPreferido - 5;

		// dibujar números de línea para cada celda visible
		for (int i = primero; i <= ultimo; i++) {
			Rectangle bounds = lista.getCellBounds(i, i);
			if (bounds == null)
				continue;

			// calcular baseline vertical para texto (centrado verticalmente dentro de la
			// celda)
			int y = bounds.y + (bounds.height + fm.getAscent() - fm.getDescent()) / 2;

			String texto = String.valueOf(i + 1);
			int textWidth = fm.stringWidth(texto);

			g.setColor(getForeground());
			g.drawString(texto, baseX - textWidth, y);
		}
	}
}
