package com.asbestosstar.crashdetector.gui.tipos.quickfix;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación "DemonSlayers" como ventana independiente (JFrame).
 * 
 * Estilo visual coherente con PanelQuickFixDemonSlayers: - Fondo negro
 * (#000000) - Texto blanco - Separador rojo - Imagen centrada al final (como en
 * el "pie")
 */
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
		this.setTitle("DemonSlayers - QuickFix");
	}

	@Override
	public Component crearContenido(QuickFix fix) {
		if (fix == null) {
			return crearEtiquetaFallback("(QuickFix nulo)");
		}

		final int WRAP_WIDTH = 350;

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(true);
		panel.setBackground(colorFondoDemon.obtener());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		// === Etiqueta (título) ===
		JLabel titulo = new JLabel(wrapHtml(fix.etiqueta, WRAP_WIDTH));
		titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
		titulo.setForeground(colorTextoDemon.obtener());
		titulo.setFont(titulo.getFont().deriveFont(14f).deriveFont(java.awt.Font.BOLD));
		panel.add(titulo);

		// === Separador rojo ===
		JPanel separador = new JPanel();
		separador.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 2));
		separador.setOpaque(true);
		separador.setBackground(colorBordeDemon.obtener());
		separador.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(separador);

		// === Componentes adicionales (botones, etiquetas, etc.) ===
		for (QuickFix.ComponenteGUI comp : fix.componentes) {
			JComponent jcomp = comp.crearComponente(() -> fix.tieneMantener);
			jcomp.setAlignmentX(Component.LEFT_ALIGNMENT);

			if (jcomp instanceof JLabel) {
				JLabel lbl = (JLabel) jcomp;
				lbl.setForeground(colorTextoDemon.obtener());
				lbl.setText(wrapHtml(lbl.getText(), WRAP_WIDTH));
			}

			panel.add(jcomp);
		}

		// === Imagen decorativa ===
		int[] dims = dimensionesImagenDecorativa();
		etiquetaImagenDecorativa = crearEtiquetaImagenEscalada(rutaImagenDecorativa(), dims[0], dims[1]);
		if (etiquetaImagenDecorativa != null) {
			JPanel panelImagen = new JPanel();
			panelImagen.setLayout(new BoxLayout(panelImagen, BoxLayout.Y_AXIS));
			panelImagen.setOpaque(true);
			panelImagen.setBackground(colorImagenFondo.obtener());
			panelImagen.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

			etiquetaImagenDecorativa.setAlignmentX(Component.CENTER_ALIGNMENT);
			panelImagen.add(etiquetaImagenDecorativa);
			panel.add(panelImagen);
		}

		return panel;
	}

	private static String wrapHtml(String text, int widthPx) {
		if (text == null)
			return "";
		return "<html><body style='width:" + widthPx + "px'>" + text + "</body></html>";
	}

	private JLabel crearEtiquetaFallback(String texto) {
		JLabel lbl = new JLabel(wrapHtml(texto, 350));
		lbl.setForeground(Color.WHITE);
		lbl.setOpaque(true);
		lbl.setBackground(Color.BLACK);
		lbl.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		return lbl;
	}

	@Override
	public void aplicarApariencia() {
		// El fondo lo maneja el panel interno; no se necesita más
		this.revalidate();
		this.repaint();
	}

	@Override
	public void aplicarAparienciaBase() {
		super.aplicarAparienciaBase();
		this.panelContenido.setOpaque(true);
		this.panelContenido.setBackground(colorFondoDemon.obtener());
	}

	@Override
	public String rutaImagenDecorativa() {
		return Statics.carpeta.resolve("imagenes/demonslayers.png").toString();
	}

	@Override
	public int[] dimensionesImagenDecorativa() {
		return new int[] { 128, 128 }; // igual que en el scrollable para coherencia
	}

	// ====== CrashDetectorGUI ======

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