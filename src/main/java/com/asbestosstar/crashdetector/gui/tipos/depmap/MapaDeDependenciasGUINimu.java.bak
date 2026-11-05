package com.asbestosstar.crashdetector.gui.tipos.depmap;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextArea;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

/**
 * Implementación temática de MapaDeDependenciasGUI basada en Nimu.
 */
public class MapaDeDependenciasGUINimu extends MapaDeDependenciasGUI {

	public static final String ID = "nimu";

	private ConfigColor colorFondoPrincipal = ConfigColor.de("tema.depmap.nimu.color.fondo", new Color(26, 24, 36));

	private ConfigColor colorPanel = ConfigColor.de("tema.depmap.nimu.color.panel", new Color(41, 38, 56));

	ConfigColor colorTexto = ConfigColor.de("tema.depmap.nimu.color.texto", new Color(221, 213, 238));

	private ConfigColor colorTextoSecundario = ConfigColor.de("tema.depmap.nimu.color.texto.secundario",
			new Color(168, 154, 196));

	private ConfigColor colorAyudaTexto = ConfigColor.de("tema.depmap.nimu.color.ayuda.texto",
			new Color(171, 137, 211));

	private ConfigColor colorGrafoFondo = ConfigColor.de("tema.depmap.nimu.color.grafo.fondo", new Color(31, 28, 44));

	private ConfigColor colorListaFondo = ConfigColor.de("tema.depmap.nimu.color.lista.fondo", new Color(35, 32, 49));

	private ConfigColor colorArbolFondo = ConfigColor.de("tema.depmap.nimu.color.arbol.fondo", new Color(35, 32, 49));

	ConfigColor colorNodo = ConfigColor.de("tema.depmap.nimu.color.nodo", new Color(157, 121, 173));

	ConfigColor colorEnlace = ConfigColor.de("tema.depmap.nimu.color.enlace", new Color(124, 99, 156));

	ConfigColor colorSeleccion = ConfigColor.de("tema.depmap.nimu.color.seleccion", new Color(92, 72, 125));

	private ConfigColor colorSeleccionTexto = ConfigColor.de("tema.depmap.nimu.color.seleccion.texto",
			new Color(244, 238, 255));

	private ConfigColor colorCarga = ConfigColor.de("tema.depmap.nimu.color.carga", new Color(255, 255, 255));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		Color fondo = colorFondoPrincipal.obtener();
		Color panel = colorPanel.obtener();
		Color texto = colorTexto.obtener();
		Color textoSec = colorTextoSecundario.obtener();
		Color ayuda = colorAyudaTexto.obtener();
		Color lista = colorListaFondo.obtener();
		Color grafo = colorGrafoFondo.obtener();
		Color arbol = colorArbolFondo.obtener();
		Color seleccion = colorSeleccion.obtener();
		Color textoSeleccionado = colorSeleccionTexto.obtener();

		setTitle(MonitorDePID.idioma.depmapTitulo());
		getContentPane().setBackground(fondo);

		if (botonRecargar != null) {
			botonRecargar.setText(MonitorDePID.idioma.depmapRecargar());
			estilizarBotonSeguroMac(botonRecargar, panel, texto);
		}

		if (botonDescompilar != null) {
			botonDescompilar.setText(MonitorDePID.idioma.depmapDescompilarSeleccion());
			estilizarBotonSeguroMac(botonDescompilar, panel, texto);
		}

		if (botonVerReferencias != null) {
			botonVerReferencias.setText(MonitorDePID.idioma.depmapVerReferencias());
			estilizarBotonSeguroMac(botonVerReferencias, panel, texto);
		}

		if (botonComprobarNoAlineadas != null) {
			botonComprobarNoAlineadas.setText(MonitorDePID.idioma.depmapComprobarNoAlineadas());
			estilizarBotonSeguroMac(botonComprobarNoAlineadas, panel, texto);
		}

		if (comboModsMapa != null) {
			comboModsMapa.setBackground(panel);
			comboModsMapa.setForeground(texto);
		}
		if (comboModDependientes != null) {
			comboModDependientes.setBackground(panel);
			comboModDependientes.setForeground(texto);
		}
		if (comboModBaseNoAlineadas != null) {
			comboModBaseNoAlineadas.setBackground(panel);
			comboModBaseNoAlineadas.setForeground(texto);
		}
		if (comboModDependienteNoAlineadas != null) {
			comboModDependienteNoAlineadas.setBackground(panel);
			comboModDependienteNoAlineadas.setForeground(texto);
		}
		if (comboPaqueteNoAlineadas != null) {
			comboPaqueteNoAlineadas.setBackground(panel);
			comboPaqueteNoAlineadas.setForeground(texto);
		}

		if (areaDetalles != null) {
			areaDetalles.setBackground(lista);
			areaDetalles.setForeground(texto);
			areaDetalles.setCaretColor(texto);
			areaDetalles.setSelectionColor(seleccion);
			areaDetalles.setSelectedTextColor(textoSeleccionado);
		}

		if (areaListaDependientes != null) {
			areaListaDependientes.setBackground(lista);
			areaListaDependientes.setForeground(texto);
			areaListaDependientes.setCaretColor(texto);
			areaListaDependientes.setSelectionColor(seleccion);
			areaListaDependientes.setSelectedTextColor(textoSeleccionado);
		}

		if (areaAyuda != null) {
			areaAyuda.setForeground(ayuda);
			areaAyuda.setCaretColor(ayuda);
			areaAyuda.setSelectionColor(seleccion);
			areaAyuda.setSelectedTextColor(textoSeleccionado);
		}

		if (overlayCarga != null) {
			overlayCarga.recargarContenido();
		}

		if (panelGrafo != null) {
			panelGrafo.setBackground(grafo);
			panelGrafo.setForeground(textoSec);
		}

		if (arbolDependencias != null) {
			arbolDependencias.setBackground(arbol);
			arbolDependencias.setForeground(texto);
			arbolDependencias.setRowHeight(22);
			arbolDependencias.setCellRenderer(new RenderizadorCeldasDependenciasNimu());
		}

		if (imagenNimu != null) {
			ImageIcon nimu = cargarImagenConFallback(Statics.carpeta.resolve("imagenes/nimuspacecat.png").toString(),
					"/mnt/data/nimuspacecat.png");
			imagenNimu.setIcon(nimu);
			imagenNimu.setText(nimu.getIconWidth() > 0 ? "" : "Nimu");
			imagenNimu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			imagenNimu.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
			imagenNimu.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
		}

		// Pintar de forma consistente labels, tabs, paneles y scroll panes
		ajustarTemaRecursivo(getContentPane(), fondo, panel, texto);

		revalidate();
		repaint();
	}

	/**
	 * Aplica colores del tema a componentes comunes que el Look & Feel deja con
	 * colores oscuros por defecto.
	 */
	private void ajustarTemaRecursivo(java.awt.Container contenedor, Color fondo, Color panel, Color texto) {
		for (java.awt.Component c : contenedor.getComponents()) {

			if (c instanceof javax.swing.JLabel) {
				c.setForeground(texto);
				c.setBackground(fondo);
			}

			if (c instanceof javax.swing.JPanel) {
				c.setBackground(fondo);
				if (c instanceof JComponent) {
					((JComponent) c).setOpaque(true);
				}
			}

			if (c instanceof javax.swing.JScrollPane) {
				javax.swing.JScrollPane sp = (javax.swing.JScrollPane) c;
				sp.getViewport().setBackground(panel);
				sp.setBackground(panel);
				sp.setForeground(texto);
			}

			if (c instanceof javax.swing.JViewport) {
				c.setBackground(panel);
				c.setForeground(texto);
			}

			if (c instanceof javax.swing.JTabbedPane) {
				javax.swing.JTabbedPane tabs = (javax.swing.JTabbedPane) c;
				tabs.setBackground(panel);
				tabs.setForeground(texto);
			}

			if (c instanceof javax.swing.JSplitPane) {
				c.setBackground(fondo);
				c.setForeground(texto);
			}

			if (c instanceof JTextArea) {
				JTextArea area = (JTextArea) c;
				if (!area.isOpaque()) {
					area.setForeground(texto);
					area.setCaretColor(texto);
				}
			}

			if (c instanceof java.awt.Container) {
				ajustarTemaRecursivo((java.awt.Container) c, fondo, panel, texto);
			}
		}
	}

	private ImageIcon cargarImagenConFallback(String... rutas) {
		for (String ruta : rutas) {
			if (ruta == null || ruta.trim().isEmpty()) {
				continue;
			}
			ImageIcon icon = new ImageIcon(ruta);
			if (icon.getIconWidth() > 0) {
				return icon;
			}
		}
		return new ImageIcon();
	}

	private void estilizarBotonSeguroMac(JButton boton, Color panel, Color texto) {
		if (boton == null) {
			return;
		}

		boton.setFocusPainted(false);

		if (CrashDetectorGUI.esMac()) {
			boton.setOpaque(false);
			boton.setContentAreaFilled(true);
			boton.setBorderPainted(true);
			return;
		}

		boton.setOpaque(true);
		boton.setContentAreaFilled(true);
		boton.setBorderPainted(true);
		boton.setBackground(panel);
		boton.setForeground(texto);
	}

	public class RenderizadorCeldasDependenciasNimu extends RenderizadorCeldasDependencias {
		@Override
		public java.awt.Component getTreeCellRendererComponent(javax.swing.JTree tree, Object value, boolean selected,
				boolean expanded, boolean leaf, int row, boolean hasFocus) {

			java.awt.Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row,
					hasFocus);

			setForeground(selected ? colorSeleccionTexto.obtener() : colorTexto.obtener());
			setBackgroundNonSelectionColor(colorArbolFondo.obtener());
			setBackgroundSelectionColor(colorSeleccion.obtener());
			setBorderSelectionColor(colorEnlace.obtener());

			return c;
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();

		colorFondoPrincipal.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoPrincipal());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.depmapColorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.depmapColorTexto());
		colorTextoSecundario.establecerNombreParaMostrar(() -> MonitorDePID.idioma.depmapColorTextoSecundario());
		colorAyudaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.depmapColorAyudaTexto());
		colorGrafoFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.depmapColorGrafoFondo());
		colorListaFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.depmapColorListaFondo());
		colorArbolFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.depmapColorArbolFondo());
		colorNodo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.depmapColorNodo());
		colorEnlace.establecerNombreParaMostrar(() -> MonitorDePID.idioma.depmapColorEnlace());
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.depmapColorSeleccion());
		colorSeleccionTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.depmapColorSeleccionTexto());
		colorCarga.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoOverlayCarga());

		elementos.add(colorFondoPrincipal);
		elementos.add(colorPanel);
		elementos.add(colorTexto);
		elementos.add(colorTextoSecundario);
		elementos.add(colorAyudaTexto);
		elementos.add(colorGrafoFondo);
		elementos.add(colorListaFondo);
		elementos.add(colorArbolFondo);
		elementos.add(colorNodo);
		elementos.add(colorEnlace);
		elementos.add(colorSeleccion);
		elementos.add(colorSeleccionTexto);
		elementos.add(colorCarga);

		return elementos;
	}
}