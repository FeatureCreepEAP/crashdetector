package com.asbestosstar.crashdetector.gui.elementos;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.gui.tipos.actualizador.ActualizadorModsGUI;

public class EditorBotonActualizar extends AbstractCellEditor implements TableCellEditor {

	private final ActualizadorModsGUI gui;
	private final JButton boton = new JButton();
	private int fila = -1;

	public EditorBotonActualizar(ActualizadorModsGUI gui) {
		this.gui = gui;

		boton.setText(MonitorDePID.idioma.actualizadorModsBotonActualizarUno());
		boton.setFocusPainted(false);

		boton.addActionListener(e -> {
			int filaActual = fila;
			fireEditingStopped();

			if (filaActual >= 0) {
				gui.actualizarUnaAsync(filaActual);
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.fila = table.convertRowIndexToModel(row);
		boton.setText(MonitorDePID.idioma.actualizadorModsBotonActualizarUno());
		return boton;
	}

	@Override
	public Object getCellEditorValue() {
		return boton.getText();
	}
}