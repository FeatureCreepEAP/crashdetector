package com.asbestosstar.crashdetector.gui.tipos.editor;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

public class BotonRedondeadoTL extends JButton {

	private static final int RADIO = 18;

	private final Color colorFondo;
	private final Color colorBorde;
	private final boolean solido;

	/**
	 * Botón TL Skin/Cape
	 *
	 * @param texto      Texto del botón
	 * @param fondo      Color de fondo (blanco si es outline)
	 * @param borde      Color del borde
	 * @param textoColor Color del texto
	 * @param solido     true = fondo sólido / false = outline
	 */
	public BotonRedondeadoTL(String texto, Color fondo, Color borde, Color textoColor, boolean solido) {
		super(texto);
		this.colorFondo = fondo;
		this.colorBorde = borde;
		this.solido = solido;

		setForeground(textoColor);
		setFocusPainted(false);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setOpaque(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// 🔒 Tamaño uniforme TL
		setPreferredSize(new Dimension(200, 38));
		setMaximumSize(new Dimension(200, 38));
		setMinimumSize(new Dimension(200, 38));
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Color fondo = solido ? colorFondo : Color.WHITE;

		if (getModel().isPressed()) {
			fondo = fondo.darker();
		} else if (getModel().isRollover()) {
			fondo = solido ? fondo.brighter() : new Color(245, 248, 252);
		}

		g2.setColor(fondo);
		g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), RADIO, RADIO));

		g2.dispose();
		super.paintComponent(g);
	}

	@Override
	public void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(colorBorde);
		g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, RADIO, RADIO));
		g2.dispose();
	}
}
