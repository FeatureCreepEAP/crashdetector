package com.asbestosstar.crashdetector.gui.elementos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JComponent;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasGUI.ErrorDeLectador;

/**
 * Visor rápido para logs grandes.
 *
 * Este componente reemplaza JList + NumeradorDeLineas.
 *
 * Ventajas: - No crea JLabel por cada fila. - No usa CellRenderer. - No
 * pregunta getCellBounds() miles de veces. - Dibuja solamente las líneas
 * visibles. - Los números de línea se dibujan en el mismo componente. - El
 * scroll usa matemática simple: y / alturaLinea.
 *
 * Está diseñado como API pública, sin encapsulación fuerte, para que otras
 * partes de CrashDetector puedan tocar el estado directamente si lo necesitan.
 */
public class VisorLogVirtual extends JComponent implements Scrollable {

	private static final long serialVersionUID = 1L;

	public FuenteDeLineas fuente;

	public int alturaLinea = 16;
	public int margenIzquierdo = 8;
	public int margenDerechoNumero = 8;
	public int anchoNumeros = 56;
	public int lineaSeleccionada = -1;

	public Color colorFondo = Color.BLACK;
	public Color colorTexto = Color.WHITE;
	public Color colorError = new Color(255, 165, 0);
	public Color colorPila = Color.BLUE;
	public Color colorTextoSobreColor = Color.BLACK;
	public Color colorFondoNumeros = new Color(25, 25, 25);
	public Color colorTextoNumeros = Color.LIGHT_GRAY;
	public Color colorSeleccion = new Color(70, 70, 70);

	public Map<Integer, ErrorDeLectador> erroresPorLinea = new ConcurrentHashMap<Integer, ErrorDeLectador>();

	public OyenteDeLinea oyenteDeLinea;

	public interface FuenteDeLineas {
		int totalLineas();

		String obtenerLinea(int indice);

		String obtenerLineaSincrona(int indice);
	}

	public interface OyenteDeLinea {
		void lineaSeleccionada(int linea);
	}

	public VisorLogVirtual() {
		setOpaque(true);
		setAutoscrolls(true);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				seleccionarLineaPorY(e.getY());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				seleccionarLineaPorY(e.getY());
			}
		});
	}

	public void establecerFuente(FuenteDeLineas nuevaFuente) {
		fuente = nuevaFuente;
		lineaSeleccionada = -1;
		recalcularMedidas();
		revalidate();
		repaint();
	}

	public void establecerErrores(Map<Integer, ErrorDeLectador> nuevosErrores) {
		erroresPorLinea.clear();

		if (nuevosErrores != null) {
			erroresPorLinea.putAll(nuevosErrores);
		}

		repaint();
	}

	public void aplicarColores(Color fondo, Color texto, Color error, Color pila, Color textoSobreColor) {
		if (fondo != null) {
			colorFondo = fondo;
		}
		if (texto != null) {
			colorTexto = texto;
		}
		if (error != null) {
			colorError = error;
		}
		if (pila != null) {
			colorPila = pila;
		}
		if (textoSobreColor != null) {
			colorTextoSobreColor = textoSobreColor;
		}

		repaint();
	}

	public void recalcularMedidas() {
		FontMetrics fm = getFontMetrics(getFont());

		if (fm != null) {
			alturaLinea = Math.max(14, fm.getHeight() + 2);

			int total = totalLineas();
			int digitos = Math.max(3, String.valueOf(Math.max(1, total)).length());
			anchoNumeros = Math.max(44, fm.charWidth('0') * digitos + 18);
		}
	}

	public int totalLineas() {
		if (fuente == null) {
			return 0;
		}

		return fuente.totalLineas();
	}

	public int lineaDesdeY(int y) {
		int linea = y / Math.max(1, alturaLinea);

		if (linea < 0) {
			return 0;
		}

		int total = totalLineas();

		if (total <= 0) {
			return -1;
		}

		if (linea >= total) {
			return total - 1;
		}

		return linea;
	}

	public void seleccionarLineaPorY(int y) {
		int linea = lineaDesdeY(y);

		if (linea < 0) {
			return;
		}

		seleccionarLinea(linea, false);

		if (oyenteDeLinea != null) {
			oyenteDeLinea.lineaSeleccionada(linea);
		}
	}

	public void seleccionarLinea(int linea, boolean centrar) {
		int total = totalLineas();

		if (total <= 0) {
			lineaSeleccionada = -1;
			repaint();
			return;
		}

		if (linea < 0) {
			linea = 0;
		}

		if (linea >= total) {
			linea = total - 1;
		}

		int anterior = lineaSeleccionada;
		lineaSeleccionada = linea;

		if (anterior >= 0) {
			repaint(0, anterior * alturaLinea, getWidth(), alturaLinea);
		}

		repaint(0, lineaSeleccionada * alturaLinea, getWidth(), alturaLinea);

		Rectangle r = new Rectangle(0, lineaSeleccionada * alturaLinea, Math.max(1, getWidth()), alturaLinea);

		if (centrar) {
			Rectangle visible = getVisibleRect();
			r.y = Math.max(0, r.y - visible.height / 2);
			r.height = visible.height;
		}

		scrollRectToVisible(r);
	}

	public int obtenerPrimeraLineaVisible() {
		Rectangle visible = getVisibleRect();
		return lineaDesdeY(visible.y);
	}

	public int obtenerUltimaLineaVisible() {
		Rectangle visible = getVisibleRect();
		return lineaDesdeY(visible.y + visible.height + alturaLinea);
	}

	public void repintarRangoLineas(int inicio, int fin) {
		if (inicio < 0) {
			inicio = 0;
		}

		if (fin < inicio) {
			fin = inicio;
		}

		int y = inicio * alturaLinea;
		int h = (fin - inicio + 1) * alturaLinea;

		repaint(0, y, getWidth(), h);
	}

	@Override
	public Dimension getPreferredSize() {
		int total = totalLineas();
		int alto = Math.max(1, total * Math.max(1, alturaLinea));

		return new Dimension(2000, alto);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		recalcularMedidas();

		int w = getWidth();
		int h = getHeight();

		g.setColor(colorFondo);
		g.fillRect(0, 0, w, h);

		g.setColor(colorFondoNumeros);
		g.fillRect(0, 0, anchoNumeros, h);

		if (fuente == null || totalLineas() <= 0) {
			return;
		}

		FontMetrics fm = g.getFontMetrics(getFont());

		Rectangle clip = g.getClipBounds();

		if (clip == null) {
			clip = new Rectangle(0, 0, w, h);
		}

		int primera = Math.max(0, clip.y / alturaLinea);
		int ultima = Math.min(totalLineas() - 1, (clip.y + clip.height) / alturaLinea + 1);

		int baseTextoExtra = (alturaLinea + fm.getAscent() - fm.getDescent()) / 2;

		for (int i = primera; i <= ultima; i++) {
			int y = i * alturaLinea;
			String linea = fuente.obtenerLinea(i);

			Color fondoLinea = colorFondo;
			Color textoLinea = colorTexto;

			ErrorDeLectador error = erroresPorLinea.get(Integer.valueOf(i));

			if (error != null) {
				fondoLinea = error.obtenerColor();
				textoLinea = colorTextoSobreColor;
			} else if (linea != null && (linea.contains("ERROR") || linea.contains("EXCEPTION"))) {
				fondoLinea = colorError;
				textoLinea = colorTextoSobreColor;
			} else if (linea != null && (linea.contains("STACKTRACE") || linea.contains("at "))) {
				fondoLinea = colorPila;
				textoLinea = colorTextoSobreColor;
			}

			if (i == lineaSeleccionada) {
				fondoLinea = fondoLinea.darker();
			}

			g.setColor(fondoLinea);
			g.fillRect(anchoNumeros, y, w - anchoNumeros, alturaLinea);

			g.setColor(colorTextoNumeros);
			String numero = String.valueOf(i + 1);
			int anchoNumeroTexto = fm.stringWidth(numero);
			g.drawString(numero, anchoNumeros - margenDerechoNumero - anchoNumeroTexto, y + baseTextoExtra);

			g.setColor(textoLinea);

			if (linea == null) {
				linea = "";
			}

			g.drawString(linea, anchoNumeros + margenIzquierdo, y + baseTextoExtra);
		}
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return new Dimension(800, 500);
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		if (orientation == SwingConstants.VERTICAL) {
			return alturaLinea;
		}

		return 40;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		if (orientation == SwingConstants.VERTICAL) {
			return Math.max(alturaLinea, visibleRect.height - alturaLinea);
		}

		return Math.max(40, visibleRect.width - 40);
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	public void ejecutarEnEDT(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}
}