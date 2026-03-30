package com.asbestosstar.crashdetector.gui.tipos.mixins;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class MixinsGUIChiarru extends MixinsGUI {

	public static final String ID = "chiarru";

	private ConfigColor colorFondoPrincipal = ConfigColor.de("tema.mixins.chiarru.color.fondo",
			new Color(243, 177, 204));

	private ConfigColor colorPanel = ConfigColor.de("tema.mixins.chiarru.color.panel", new Color(250, 221, 235));

	private ConfigColor colorTexto = ConfigColor.de("tema.mixins.chiarru.color.texto", new Color(70, 42, 66));

	private ConfigColor colorTextoSuave = ConfigColor.de("tema.mixins.chiarru.color.texto.suave",
			new Color(110, 84, 122));

	private ConfigColor colorCarga = ConfigColor.de("tema.mixins.chiarru.color.carga", new Color(255, 255, 255));

	private ConfigColor colorComboFondo = ConfigColor.de("tema.mixins.chiarru.color.combo.fondo",
			new Color(255, 247, 252));

	private ConfigColor colorAreaContenidoFondo = ConfigColor.de("tema.mixins.chiarru.color.area.contenido.fondo",
			new Color(252, 244, 250));

	private ConfigColor colorSeleccionTexto = ConfigColor.de("tema.mixins.chiarru.color.area.seleccion",
			new Color(221, 196, 235));

	private ConfigColor colorSeleccionTextoActivo = ConfigColor.de("tema.mixins.chiarru.color.area.seleccion.texto",
			new Color(55, 33, 56));

	private ConfigColor colorAyudaTexto = ConfigColor.de("tema.mixins.chiarru.color.ayuda.texto",
			new Color(92, 52, 110));

	private ConfigColor colorArbolFondo = ConfigColor.de("tema.mixins.chiarru.color.arbol.fondo",
			new Color(252, 244, 250));

	private ConfigColor colorRendererSeleccionTexto = ConfigColor
			.de("tema.mixins.chiarru.color.renderer.seleccion.texto", new Color(61, 36, 70));

	private ConfigColor colorRendererSeleccionFondo = ConfigColor
			.de("tema.mixins.chiarru.color.renderer.seleccion.fondo", new Color(230, 210, 240));

	private ConfigColor colorRendererBordeSeleccion = ConfigColor
			.de("tema.mixins.chiarru.color.renderer.seleccion.borde", new Color(196, 155, 206));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		setTitle(MonitorDePID.idioma.tituloMixins());

		Color fondo = colorFondoPrincipal.obtener();
		Color panel = colorPanel.obtener();
		Color texto = colorTexto.obtener();
		Color textoSuave = colorTextoSuave.obtener();
		Color comboFondo = colorComboFondo.obtener();
		Color areaContenidoFondo = colorAreaContenidoFondo.obtener();
		Color colorSeleccion = colorSeleccionTexto.obtener();
		Color colorSeleccionTextoReal = colorSeleccionTextoActivo.obtener();
		Color colorAyuda = colorAyudaTexto.obtener();
		Color colorArbol = colorArbolFondo.obtener();

		getContentPane().setBackground(fondo);

		if (botonRecargar != null) {
			botonRecargar.setText(MonitorDePID.idioma.mixinsRecargar());
			botonRecargar.setBackground(panel);
			botonRecargar.setForeground(texto);
			botonRecargar.setFocusPainted(false);
		}

		if (botonDescompilar != null) {
			botonDescompilar.setText(MonitorDePID.idioma.mixinsDescompilarSeleccion());
			botonDescompilar.setBackground(panel);
			botonDescompilar.setForeground(texto);
			botonDescompilar.setFocusPainted(false);
		}

		if (comboMods != null) {
			comboMods.setBackground(comboFondo);
			comboMods.setForeground(texto);
			comboMods.setToolTipText(MonitorDePID.idioma.mixinsTooltipCombo());
		}

		if (areaContenido != null) {
			areaContenido.setBackground(areaContenidoFondo);
			areaContenido.setForeground(texto);
			areaContenido.setCaretColor(texto);
			areaContenido.setSelectionColor(colorSeleccion);
			areaContenido.setSelectedTextColor(colorSeleccionTextoReal);
		}

		if (gifCarga != null) {
			gifCarga.setText(MonitorDePID.idioma.cargando());
			gifCarga.setForeground(colorCarga.obtener());
		}

		if (imagenChiarru != null) {
			ImageIcon chiarru = cargarImagenConFallback(Statics.carpeta.resolve("imagenes/chiarru.png").toString(),
					Statics.carpeta.resolve("imagenes/chiiaru.png").toString(), "/mnt/data/chiiaru.png");

			imagenChiarru.setIcon(chiarru);
			imagenChiarru.setText(chiarru.getIconWidth() > 0 ? "" : "Chiarru");
			imagenChiarru.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			imagenChiarru.setVerticalAlignment(javax.swing.SwingConstants.TOP);
			imagenChiarru.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 6));

			java.awt.Container padre = imagenChiarru.getParent();
			while (padre != null) {
				if (padre instanceof javax.swing.JComponent) {
					((javax.swing.JComponent) padre).setOpaque(true);
					padre.setBackground(fondo);
				}
				padre = padre.getParent();
			}
		}

		if (imagenGoMix != null) {
			ImageIcon goMix = cargarImagenConFallback(Statics.carpeta.resolve("imagenes/gomix.png").toString(),
					"/mnt/data/gomix.png");

			imagenGoMix.setIcon(goMix);
			imagenGoMix.setText(goMix.getIconWidth() > 0 ? "" : "go mix");
			imagenGoMix.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			imagenGoMix.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
		}

		if (arbolMixins != null) {
			arbolMixins.setBackground(colorArbol);
			arbolMixins.setForeground(texto);
			arbolMixins.setRowHeight(22);
			arbolMixins.setCellRenderer(new RenderizadorCeldasMixinsChiarru());
		}

		// 🔹 El texto descriptivo inferior ahora será morado, no blanco
		ajustarTextosAyuda(this.getContentPane(), colorAyuda);

		revalidate();
		repaint();
	}

	/**
	 * Carga una imagen probando varias rutas hasta encontrar una válida.
	 */
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

	/**
	 * Renderer temático para el árbol de mixins.
	 */
	public class RenderizadorCeldasMixinsChiarru extends RenderizadorCeldasMixins {

		@Override
		public java.awt.Component getTreeCellRendererComponent(javax.swing.JTree tree, Object value, boolean selected,
				boolean expanded, boolean leaf, int row, boolean hasFocus) {

			java.awt.Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row,
					hasFocus);

			setForeground(selected ? colorRendererSeleccionTexto.obtener() : colorTexto.obtener());
			setBackgroundNonSelectionColor(colorArbolFondo.obtener());
			setBackgroundSelectionColor(colorRendererSeleccionFondo.obtener());
			setBorderSelectionColor(colorRendererBordeSeleccion.obtener());

			return c;
		}

	}

	/**
	 * Recorre el árbol de componentes y ajusta el color de los JTextArea de ayuda
	 * no editables y transparentes.
	 */
	private void ajustarTextosAyuda(java.awt.Container contenedor, Color colorTextoAyuda) {
		for (java.awt.Component c : contenedor.getComponents()) {
			if (c instanceof javax.swing.JTextArea) {
				javax.swing.JTextArea area = (javax.swing.JTextArea) c;
				if (!area.isEditable() && !area.isOpaque()) {
					area.setForeground(colorTextoAyuda);
					area.setCaretColor(colorTextoAyuda);
				}
			}

			if (c instanceof java.awt.Container) {
				ajustarTextosAyuda((java.awt.Container) c, colorTextoAyuda);
			}
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();

		colorFondoPrincipal.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoPrincipal());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorTexto());
		colorTextoSuave.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorTextoSuave());
		colorCarga.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoOverlayCarga());

		colorComboFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorComboFondo());
		colorAreaContenidoFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorAreaContenidoFondo());
		colorSeleccionTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorSeleccionTexto());
		colorSeleccionTextoActivo
				.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorSeleccionTextoActivo());
		colorAyudaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorAyudaTexto());
		colorArbolFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorArbolFondo());
		colorRendererSeleccionTexto
				.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorRendererSeleccionTexto());
		colorRendererSeleccionFondo
				.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorRendererSeleccionFondo());
		colorRendererBordeSeleccion
				.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mixinsColorRendererBordeSeleccion());

		elementos.add(colorFondoPrincipal);
		elementos.add(colorPanel);
		elementos.add(colorTexto);
		elementos.add(colorTextoSuave);
		elementos.add(colorCarga);

		elementos.add(colorComboFondo);
		elementos.add(colorAreaContenidoFondo);
		elementos.add(colorSeleccionTexto);
		elementos.add(colorSeleccionTextoActivo);
		elementos.add(colorAyudaTexto);
		elementos.add(colorArbolFondo);
		elementos.add(colorRendererSeleccionTexto);
		elementos.add(colorRendererSeleccionFondo);
		elementos.add(colorRendererBordeSeleccion);

		return elementos;
	}

	/**
	 * Recorre el árbol de componentes y pone en blanco los JTextArea de ayuda que
	 * se hayan creado como texto explicativo de la interfaz.
	 */
	private void ajustarTextosAyudaEnBlanco(java.awt.Container contenedor) {
		for (java.awt.Component c : contenedor.getComponents()) {
			if (c instanceof javax.swing.JTextArea) {
				javax.swing.JTextArea area = (javax.swing.JTextArea) c;
				if (!area.isEditable() && area.isOpaque() == false) {
					area.setForeground(Color.WHITE);
					area.setCaretColor(Color.WHITE);
				}
			}

			if (c instanceof java.awt.Container) {
				ajustarTextosAyudaEnBlanco((java.awt.Container) c);
			}
		}
	}

}