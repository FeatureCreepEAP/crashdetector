package com.asbestosstar.crashdetector.gui.elementos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RenderizadorBarraSampler extends JPanel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	private final JPanel barra = new JPanel(new BorderLayout());
	private final JLabel texto = new JLabel();

	private long maximo = 1L;

	private final Color colorFondo;
	private final Color colorBarra;
	private final Color colorTexto;

	public RenderizadorBarraSampler(Color colorFondo, Color colorBarra, Color colorTexto) {
		this.colorFondo = colorFondo;
		this.colorBarra = colorBarra;
		this.colorTexto = colorTexto;

		setLayout(new BorderLayout());
		setOpaque(true);

		barra.setOpaque(true);
		barra.setBackground(colorBarra);

		texto.setForeground(colorTexto);
		texto.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));

		barra.add(texto, BorderLayout.CENTER);
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

		int anchoColumna = Math.max(1, table.getColumnModel().getColumn(column).getWidth());
		int anchoBarra = (int) Math.max(8, Math.min(anchoColumna, (valor * anchoColumna) / maximo));

		setBackground(selected ? table.getSelectionBackground() : colorFondo);

		barra.setPreferredSize(new Dimension(anchoBarra, table.getRowHeight()));
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