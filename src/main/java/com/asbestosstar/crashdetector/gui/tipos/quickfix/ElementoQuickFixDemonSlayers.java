package com.asbestosstar.crashdetector.gui.tipos.quickfix;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class ElementoQuickFixDemonSlayers extends QuickFixGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "demonslayers_elemento";

	public ConfigColor colorFondoDemon = ConfigColor.de("quickfix_demonslayers_fondo", new Color(0x000000));
	public ConfigColor colorTextoDemon = ConfigColor.de("quickfix_demonslayers_texto", new Color(0xFFFFFF));
	public ConfigColor colorBordeDemon = ConfigColor.de("quickfix_demonslayers_borde", new Color(0xFF0000));
	public ConfigColor colorResaltadoDemon = ConfigColor.de("quickfix_demonslayers_resaltado", new Color(0xFFFF00));
	public ConfigColor colorImagenFondo = ConfigColor.de("quickfix_demonslayers_imagen_fondo", new Color(0x1A1A1A));

	private JLabel etiquetaImagenDecorativa;

	public ElementoQuickFixDemonSlayers() {
		super();
		this.setTitle("DemonSlayers - QuickFix"); // opcional
	}

	@Override
	protected Component crearContenido(QuickFix fix) {
		if (fix == null) {
			return new JLabel("(QuickFix nulo)");
		}

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		JLabel titulo = new JLabel("<html><b>" + formatearTexto(fix.etiqueta) + "</b></html>");
		titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
		titulo.setForeground(colorTextoDemon.obtener());
		panel.add(titulo);

		JPanel separador = new JPanel();
		separador.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 2));
		separador.setOpaque(true);
		separador.setBackground(colorBordeDemon.obtener());
		separador.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(separador);

		String descripcionTexto = fix.etiqueta != null && !fix.etiqueta.isEmpty()
			? fix.etiqueta
			: "Sin descripción adicional";

		JLabel descripcion = new JLabel(
			"<html><span style='color:#" +
			String.format("%06x", colorTextoDemon.obtener().getRGB() & 0xFFFFFF) +
			";'>" + formatearTexto(descripcionTexto) + "</span></html>"
		);
		descripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
		descripcion.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
		panel.add(descripcion);

		int[] dims = dimensionesImagenDecorativa();
		etiquetaImagenDecorativa = crearEtiquetaImagenEscalada(rutaImagenDecorativa(), dims[0], dims[1]);
		if (etiquetaImagenDecorativa != null) {
			JPanel panelImagen = new JPanel();
			panelImagen.setLayout(new BoxLayout(panelImagen, BoxLayout.X_AXIS));
			panelImagen.setOpaque(true);
			panelImagen.setBackground(colorImagenFondo.obtener());
			panelImagen.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

			JPanel espaciador = new JPanel();
			espaciador.setLayout(new BoxLayout(espaciador, BoxLayout.X_AXIS));
			espaciador.setOpaque(false);
			espaciador.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
			panelImagen.add(espaciador);
			panelImagen.add(etiquetaImagenDecorativa);
			panel.add(panelImagen);
		}

		return panel;
	}

	@Override
	protected void aplicarApariencia() {
		// El fondo del JFrame se puede dejar por defecto; el contenido está en panelContenido
		this.panelContenido.setBackground(colorFondoDemon.obtener());
		this.getContentPane().setBackground(colorFondoDemon.obtener()); // opcional
		this.revalidate();
		this.repaint();
	}

	@Override
	protected void aplicarAparienciaBase() {
		// Ya se maneja en la clase base → no sobrescribir innecesariamente
		super.aplicarAparienciaBase();
		this.panelContenido.setBackground(colorFondoDemon.obtener());
	}

	@Override
	protected String rutaImagenDecorativa() {
		return Statics.carpeta.resolve("imagenes/demonslayers.png").toString();
	}

	@Override
	protected int[] dimensionesImagenDecorativa() {
		return new int[] { 64, 64 };
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();
		colorFondoDemon.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		elementos.add(colorFondoDemon);
		colorTextoDemon.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		elementos.add(colorTextoDemon);
		colorBordeDemon.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		elementos.add(colorBordeDemon);
		colorResaltadoDemon.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colResultado());
		elementos.add(colorResaltadoDemon);
		colorImagenFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoVentana());
		elementos.add(colorImagenFondo);
		return elementos;
	}
}