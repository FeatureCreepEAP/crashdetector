package com.asbestosstar.crashdetector.gui.elementos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Renderizador visual tipo barra para mostrar qué métodos consumen más tiempo.
 */
public class RenderizadorBarraRendimiento extends JPanel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	private final JPanel barra = new JPanel();
	private final JLabel texto = new JLabel();

	private long maximo = 1L;

	private final Color colorFondo;
	private final Color colorBarra;
	private final Color colorTexto;

	public RenderizadorBarraRendimiento(Color colorFondo, Color colorBarra, Color colorTexto) {
		this.colorFondo = colorFondo;
		this.colorBarra = colorBarra;
		this.colorTexto = colorTexto;

		setLayout(new BorderLayout());
		setOpaque(true);

		barra.setLayout(new BorderLayout());
		barra.setOpaque(true);
		barra.setBackground(colorBarra);
		barra.add(texto, BorderLayout.CENTER);

		texto.setOpaque(false);
		texto.setForeground(colorTexto);
		texto.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));

		add(barra, BorderLayout.WEST);
	}

	public void establecerMaximo(long maximo) {
		this.maximo = Math.max(1L, maximo);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focus, int row,
			int column) {

		long valor = 0L;

		if (value instanceof Number) {
			valor = ((Number) value).longValue();
		}

		int anchoTabla = Math.max(1, table.getColumnModel().getColumn(column).getWidth());
		int ancho = (int) Math.max(8, Math.min(anchoTabla, (valor * anchoTabla) / maximo));

		setBackground(selected ? table.getSelectionBackground() : colorFondo);

		barra.setPreferredSize(new java.awt.Dimension(ancho, table.getRowHeight()));
		texto.setText(formatearNs(valor));

		return this;
	}

	private String formatearNs(long ns) {
		if (ns >= 1_000_000_000L)
			return String.format("%.2f s", ns / 1_000_000_000.0);
		if (ns >= 1_000_000L)
			return String.format("%.2f ms", ns / 1_000_000.0);
		if (ns >= 1_000L)
			return String.format("%.2f µs", ns / 1_000.0);
		return ns + " ns";
	}
}