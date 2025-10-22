package com.asbestosstar.crashdetector.gui.elementos;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * ComboBox que muestra una bandera o icono junto al nombre del idioma. Las
 * rutas de los iconos se proporcionan completas en el mapa (por ejemplo
 * "imagenes/bandera_mexico.png").
 */
public class ComboIdiomasConIcono extends JComboBox<String> {

	private final Map<String, Icon> iconosPorEtiqueta;
	private int anchoIcono = 18;
	private int altoIcono = 12;
	private int separacionIconoTexto = 8;

	public ComboIdiomasConIcono(LinkedHashMap<String, String> etiquetaARuta) {
		super(etiquetaARuta.keySet().toArray(new String[0]));
		this.iconosPorEtiqueta = new LinkedHashMap<>();

		for (Map.Entry<String, String> e : etiquetaARuta.entrySet()) {
			iconosPorEtiqueta.put(e.getKey(), cargarIconoLocal(e.getValue(), anchoIcono, altoIcono));
		}

		setRenderer(new Renderizador());
		setFocusable(true);
	}

	public void aplicarColores(Color fondo, Color texto) {
		setBackground(fondo);
		setForeground(texto);
		Component c = getEditor() != null ? getEditor().getEditorComponent() : null;
		if (c != null) {
			c.setBackground(fondo);
			c.setForeground(texto);
		}
	}

	private Icon cargarIconoLocal(String rutaRelativa, int w, int h) {
		try {
			File archivo = MonitorDePID.carpeta.resolve(rutaRelativa).toFile();
			if (!archivo.exists())
				return null;
			ImageIcon base = new ImageIcon(archivo.getAbsolutePath());
			if (base.getIconWidth() <= 0 || base.getIconHeight() <= 0)
				return base;
			Image img = base.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
			return new ImageIcon(img);
		} catch (Throwable t) {
			return null;
		}
	}

	private class Renderizador extends BasicComboBoxRenderer {
		@Override
		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList lista, Object valor, int indice, boolean seleccionado,
				boolean foco) {
			JLabel lbl = (JLabel) super.getListCellRendererComponent(lista, valor, indice, seleccionado, foco);
			String etiqueta = valor != null ? valor.toString() : "";
			Icon icono = iconosPorEtiqueta.get(etiqueta);

			lbl.setIcon(icono);
			lbl.setText(etiqueta);
			lbl.setIconTextGap(separacionIconoTexto);
			lbl.setHorizontalAlignment(LEFT);
			lbl.setOpaque(true);

			if (seleccionado) {
				lbl.setBackground(lista.getSelectionBackground());
				lbl.setForeground(lista.getSelectionForeground());
			} else {
				lbl.setBackground(lista.getBackground());
				lbl.setForeground(lista.getForeground());
			}

			int altoFila = Math.max(lista.getFixedCellHeight() > 0 ? lista.getFixedCellHeight() : 0,
					Math.max(lbl.getFontMetrics(lbl.getFont()).getHeight(), altoIcono) + 4);
			if (lista.getFixedCellHeight() <= 0) {
				lista.setFixedCellHeight(altoFila);
			}
			return lbl;
		}
	}
}
