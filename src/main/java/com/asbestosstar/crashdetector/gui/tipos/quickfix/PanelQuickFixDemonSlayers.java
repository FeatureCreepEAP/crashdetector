package com.asbestosstar.crashdetector.gui.tipos.quickfix;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación DemonSlayers de la ventana QuickFix.
 *
 * RESPONSABILIDADES: - Define layout completo - Define colores temáticos
 * (DemonSlayers) - Define imagen del pie - Gestiona estado "sin soluciones"
 */
public class PanelQuickFixDemonSlayers extends TodosQuickFixesGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "demonslayers";

	// =====================================================
	// COLORES TEMÁTICOS DEMONSLAYERS
	// =====================================================

	/** Fondo general */
	private ConfigColor colorFondo = ConfigColor.de("quickfix.demonslayers.color.fondo", new Color(18, 18, 18)); // negro
																													// carbón

	/** Separadores */
	private ConfigColor colorSeparador = ConfigColor.de("quickfix.demonslayers.color.separador",
			new Color(120, 30, 30)); // rojo oscuro

	/** Texto informativo */
	private ConfigColor colorTextoInfo = ConfigColor.de("quickfix.demonslayers.color.textoInfo",
			new Color(210, 210, 210)); // gris claro

	// =====================================================
	// COMPONENTES DE ESTADO
	// =====================================================

	private JLabel etiquetaSinSoluciones;

	public PanelQuickFixDemonSlayers() {
		super();
		configurarVentanaBase();
		inicializarUI();
	}

	/**
	 * Construye toda la interfaz visual.
	 */
	private void inicializarUI() {

		// Fondo de la ventana
		getContentPane().setBackground(colorFondo.obtener());

		panelContenedor = new JPanel();
		panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
		panelContenedor.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panelContenedor.setBackground(colorFondo.obtener());
		panelContenedor.setOpaque(true);

		scroll = new javax.swing.JScrollPane(panelContenedor);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setBackground(colorFondo.obtener());
		scroll.getViewport().setBackground(colorFondo.obtener());
		scroll.setOpaque(true);
		scroll.getViewport().setOpaque(true);

		add(scroll, java.awt.BorderLayout.CENTER);

		// Etiqueta "sin soluciones"
		etiquetaSinSoluciones = new JLabel(MonitorDePID.idioma.noHaySolucionDisponible());
		etiquetaSinSoluciones.setForeground(colorTextoInfo.obtener());
		etiquetaSinSoluciones.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		etiquetaSinSoluciones.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 10));

		panelContenedor.add(etiquetaSinSoluciones);

		// Pie decorativo
		piePanel = new JPanel();
		piePanel.setLayout(new BoxLayout(piePanel, BoxLayout.Y_AXIS));
		piePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		piePanel.setBackground(colorFondo.obtener());
		piePanel.setOpaque(true);

		JLabel img = crearEtiquetaImagenEscalada(rutaImagenPie(), anchoImagenPie(), altoImagenPie());

		if (img != null) {
			img.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			piePanel.add(img);
		}

		panelContenedor.add(piePanel);

		aplicarApariencia();
	}

	@Override
	protected String rutaImagenPie() {
		return Statics.carpeta.resolve("imagenes/demonslayers.png").toString();
	}

	/**
	 * Aplica la apariencia DemonSlayers.
	 */
	@Override
	protected void aplicarApariencia() {

		getContentPane().setBackground(colorFondo.obtener());
		panelContenedor.setBackground(colorFondo.obtener());
		piePanel.setBackground(colorFondo.obtener());

		for (java.awt.Component c : panelContenedor.getComponents()) {
			if (c instanceof JSeparator) {
				c.setForeground(colorSeparador.obtener());
			}
		}

		revalidate();
		repaint();
	}

	/**
	 * Se llama automáticamente al agregar QuickFix. Elimina el mensaje "sin
	 * soluciones" si existe.
	 */
	@Override
	public void agregarQuickFix(com.asbestosstar.crashdetector.analizador.QuickFix qf) {
		panelContenedor.remove(etiquetaSinSoluciones);
		super.agregarQuickFix(qf);
	}

	@Override
	public void limpiar() {
		super.limpiar();
		panelContenedor.add(etiquetaSinSoluciones, 0);
		panelContenedor.revalidate();
		panelContenedor.repaint();
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {
		setVisible(true);
	}

	/**
	 * Configuración expuesta.
	 */
	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> lista = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorSeparador.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorSeparador());
		colorTextoInfo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());

		lista.add(colorFondo);
		lista.add(colorSeparador);
		lista.add(colorTextoInfo);

		return lista;
	}
}
