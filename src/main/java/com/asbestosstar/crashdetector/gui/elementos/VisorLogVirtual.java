package com.asbestosstar.crashdetector.gui.elementos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.MonitorDePID;
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

	public int lineaSeleccionInicio = -1;
	public int lineaSeleccionFin = -1;
	public boolean arrastrandoSeleccion = false;

	public Color colorFondo = Color.BLACK;
	public Color colorTexto = Color.WHITE;
	public Color colorError = new Color(255, 165, 0);
	public Color colorPila = Color.BLUE;
	public Color colorTextoSobreColor = Color.BLACK;
	public Color colorFondoNumeros = new Color(25, 25, 25);
	public Color colorTextoNumeros = Color.LIGHT_GRAY;
	public Color colorSeleccion = new Color(70, 70, 70);
	public Color colorAdvertencia = new Color(255, 220, 80);
	public boolean usarTextoAutomaticoSobreColores = true;

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
		setFocusable(true);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				requestFocusInWindow();

				int linea = lineaDesdeY(e.getY());

				if (linea < 0) {
					return;
				}

				if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0 && lineaSeleccionInicio >= 0) {
					lineaSeleccionFin = linea;
				} else {
					lineaSeleccionInicio = linea;
					lineaSeleccionFin = linea;
				}

				arrastrandoSeleccion = true;
				seleccionarLinea(linea, false);

				if (oyenteDeLinea != null) {
					oyenteDeLinea.lineaSeleccionada(linea);
				}

				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				arrastrandoSeleccion = false;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				requestFocusInWindow();

				int linea = lineaDesdeY(e.getY());

				if (linea < 0) {
					return;
				}

				seleccionarLinea(linea, false);

				if (oyenteDeLinea != null) {
					oyenteDeLinea.lineaSeleccionada(linea);
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int linea = lineaDesdeY(e.getY());

				if (linea < 0) {
					return;
				}

				lineaSeleccionFin = linea;
				lineaSeleccionada = linea;

				Rectangle r = new Rectangle(0, linea * alturaLinea, Math.max(1, getWidth()), alturaLinea);
				scrollRectToVisible(r);

				repaint();
			}
		});

		instalarAtajosDeCopia();
	}

	public void instalarAtajosDeCopia() {
		getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), "copiar");
		getInputMap(WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.META_DOWN_MASK), "copiar");

		getActionMap().put("copiar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				copiarSeleccionAlPortapapeles();
			}
		});
	}

	public void copiarSeleccionAlPortapapeles() {
		if (fuente == null) {
			return;
		}

		int total = totalLineas();

		if (total <= 0) {
			return;
		}

		int inicio;
		int fin;

		if (lineaSeleccionInicio >= 0 && lineaSeleccionFin >= 0) {
			inicio = Math.min(lineaSeleccionInicio, lineaSeleccionFin);
			fin = Math.max(lineaSeleccionInicio, lineaSeleccionFin);
		} else if (lineaSeleccionada >= 0) {
			inicio = lineaSeleccionada;
			fin = lineaSeleccionada;
		} else {
			return;
		}

		inicio = Math.max(0, Math.min(inicio, total - 1));
		fin = Math.max(0, Math.min(fin, total - 1));

		StringBuilder sb = new StringBuilder();

		for (int i = inicio; i <= fin; i++) {
			String linea = fuente.obtenerLineaSincrona(i);

			if (linea == null) {
				linea = "";
			}

			sb.append(linea);

			if (i < fin) {
				sb.append(System.lineSeparator());
			}
		}

		StringSelection seleccion = new StringSelection(sb.toString());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(seleccion, seleccion);
	}

	public boolean lineaEstaSeleccionada(int linea) {
		if (lineaSeleccionInicio < 0 || lineaSeleccionFin < 0) {
			return linea == lineaSeleccionada;
		}

		int inicio = Math.min(lineaSeleccionInicio, lineaSeleccionFin);
		int fin = Math.max(lineaSeleccionInicio, lineaSeleccionFin);

		return linea >= inicio && linea <= fin;
	}

	public void establecerFuente(FuenteDeLineas nuevaFuente) {
		fuente = nuevaFuente;
		lineaSeleccionada = -1;
		lineaSeleccionInicio = -1;
		lineaSeleccionFin = -1;
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

		if (lineaSeleccionInicio < 0 || lineaSeleccionFin < 0) {
			lineaSeleccionInicio = linea;
			lineaSeleccionFin = linea;
		}

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
				textoLinea = error.obtenerColor();
			} else if (lineaTienePilaTexto(linea)) {
				textoLinea = colorPila;
			} else if (lineaTieneErrorTexto(linea)) {
				textoLinea = colorError;
			} else if (lineaTieneAdvertenciaTexto(linea)) {
				textoLinea = colorAdvertencia;
			}

			if (lineaEstaSeleccionada(i)) {
				fondoLinea = colorSeleccionPara(colorFondo);
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

	public Color colorTextoParaFondoEspecial(Color fondo) {
		if (usarTextoAutomaticoSobreColores) {
			return colorTextoLegiblePara(fondo);
		}

		return colorTextoSobreColor;
	}

	public Color colorSeleccionPara(Color colorBase) {
		if (colorBase == null) {
			return colorSeleccion;
		}

		int brillo = (colorBase.getRed() + colorBase.getGreen() + colorBase.getBlue()) / 3;

		// Si el fondo es oscuro, aclarar la selección.
		// Si el fondo ya es claro, oscurecerla para que siga viéndose.
		if (brillo < 90) {
			return aclararColor(colorBase, 70);
		}

		return oscurecerColor(colorBase, 45);
	}

	public Color aclararColor(Color color, int cantidad) {
		int r = Math.min(255, color.getRed() + cantidad);
		int g = Math.min(255, color.getGreen() + cantidad);
		int b = Math.min(255, color.getBlue() + cantidad);

		return new Color(r, g, b);
	}

	public Color oscurecerColor(Color color, int cantidad) {
		int r = Math.max(0, color.getRed() - cantidad);
		int g = Math.max(0, color.getGreen() - cantidad);
		int b = Math.max(0, color.getBlue() - cantidad);

		return new Color(r, g, b);
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

	public boolean lineaTieneErrorTexto(String linea) {
		if (linea == null) {
			return false;
		}

		String s = linea.toUpperCase();

		return s.contains("ERROR") || s.contains("EXCEPTION") || s.contains("SEVERE") || s.contains("FATAL");
	}

	public boolean lineaTieneAdvertenciaTexto(String linea) {
		if (linea == null) {
			return false;
		}

		String s = linea.toUpperCase();

		return s.contains("WARN") || s.contains("WARNING") || s.contains("ADVERTENCIA");
	}

	public boolean lineaTienePilaTexto(String linea) {
		if (linea == null) {
			return false;
		}

		String s = linea.trim();

		return s.equals("STACKTRACE") || s.contains("STACKTRACE") || s.startsWith("at ") || s.startsWith("at\t")
				|| s.startsWith("Caused by:") || s.startsWith("Suppressed:") || s.startsWith("... ")
				|| s.contains(" at ") || s.contains("\tat ");
	}

	public Color colorTextoLegiblePara(Color fondo) {
		if (fondo == null) {
			return colorTexto;
		}

		int r = fondo.getRed();
		int g = fondo.getGreen();
		int b = fondo.getBlue();

		int luminancia = (int) ((r * 0.299) + (g * 0.587) + (b * 0.114));

		if (luminancia < 140) {
			return Color.WHITE;
		}

		return Color.BLACK;
	}
}