package com.asbestosstar.crashdetector.gui.elementos;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.asbestosstar.crashdetector.MonitorDePID;

public class RenderizadorBotonActualizar extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		JButton boton = new JButton(MonitorDePID.idioma.actualizadorModsBotonActualizarUno());
		boton.setFocusPainted(false);
		return boton;
	}
}