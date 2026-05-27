package com.asbestosstar.crashdetector.gui.tipos.docs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class LectadorDeDocumentosMinimalista extends LectadorDeDocumentosGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "minimalista_docs";

	public ConfigColor colorFondoVentana = ConfigColor.de("tema.docs.minimalista.color.fondo.ventana",
			new java.awt.Color(18, 18, 18));
	public ConfigColor colorPanel = ConfigColor.de("tema.docs.minimalista.color.panel", new java.awt.Color(28, 28, 28));
	public ConfigColor colorDocumento = ConfigColor.de("tema.docs.minimalista.color.documento",
			new java.awt.Color(250, 250, 250));
	public ConfigColor colorTexto = ConfigColor.de("tema.docs.minimalista.color.texto",
			new java.awt.Color(235, 235, 235));
	public ConfigColor colorTextoDocumento = ConfigColor.de("tema.docs.minimalista.color.texto.documento",
			new java.awt.Color(35, 35, 35));
	public ConfigColor colorBorde = ConfigColor.de("tema.docs.minimalista.color.borde", new java.awt.Color(80, 80, 80));

	protected JPanel panelSuperior;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void construirInterfazBase() {
		panelRaiz = new JPanel(new BorderLayout(0, 0));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		modeloArbol = new javax.swing.tree.DefaultTreeModel(
				new javax.swing.tree.DefaultMutableTreeNode(MonitorDePID.idioma.docsCargando()));

		arbolDocs.setModel(modeloArbol);
		arbolDocs.setRootVisible(true);
		arbolDocs.setShowsRootHandles(true);
		arbolDocs.addTreeSelectionListener(this::alSeleccionarNodo);

		scrollArbol = new JScrollPane(arbolDocs);

		visorDocumento.setEditable(false);
		visorDocumento.setContentType("text/html");
		visorDocumento.putClientProperty(javax.swing.JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		visorDocumento.addHyperlinkListener(this::alHipervinculoDocumento);

		scrollDocumento = new JScrollPane(visorDocumento);

		splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollArbol, scrollDocumento);
		splitPrincipal.setResizeWeight(0.22);
		splitPrincipal.setDividerSize(6);

		mapaIdiomas = mapaParaComboBoxIdiomas();
		comboIdiomas = crearComboIdiomas();

		panelSuperior = crearPanelSuperior();

		panelRaiz.add(panelSuperior, BorderLayout.NORTH);
		panelRaiz.add(splitPrincipal, BorderLayout.CENTER);

		javax.swing.JLayeredPane capa = (javax.swing.JLayeredPane) getContentPane();
		capa.add(panelRaiz, BorderLayout.CENTER);
		capa.setLayer(panelRaiz, javax.swing.JLayeredPane.PALETTE_LAYER);

		this.setVisible(true);
	}

	public JPanel crearPanelSuperior() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
		panel.setPreferredSize(new Dimension(10, 42));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

		JLabel lblIdioma = new JLabel(MonitorDePID.idioma.idiomas() + ":");
		lblIdioma.setForeground(colorTexto.obtener());

		panel.add(lblIdioma);
		panel.add(comboIdiomas);

		return panel;
	}

	@Override
	public JPanel crearPanelIlustracion() {
		// En el modo minimalista no hay ilustración ni panel inferior.
		return new JPanel(new BorderLayout());
	}

	@Override
	public void aplicarApariencia() {
		getContentPane().setBackground(colorFondoVentana.obtener());

		if (panelRaiz != null) {
			panelRaiz.setOpaque(true);
			panelRaiz.setBackground(colorFondoVentana.obtener());
		}

		if (panelSuperior != null) {
			panelSuperior.setOpaque(true);
			panelSuperior.setBackground(colorPanel.obtener());
			panelSuperior.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, colorBorde.obtener()));
		}

		if (comboIdiomas != null) {
			comboIdiomas.setBackground(colorPanel.obtener());
			comboIdiomas.setForeground(colorTexto.obtener());
			comboIdiomas.setFont(comboIdiomas.getFont().deriveFont(Font.PLAIN, 14f));
		}

		if (arbolDocs != null) {
			arbolDocs.setBackground(colorPanel.obtener());
			arbolDocs.setForeground(colorTexto.obtener());
			arbolDocs.setRowHeight(22);
			arbolDocs.setFont(arbolDocs.getFont().deriveFont(Font.PLAIN, 14f));
		}

		if (visorDocumento != null) {
			visorDocumento.setBackground(colorDocumento.obtener());
			visorDocumento.setForeground(colorTextoDocumento.obtener());
			visorDocumento.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		}

		estilizarScroll(scrollArbol, MonitorDePID.idioma.docsArbolTitulo());
		estilizarScroll(scrollDocumento, MonitorDePID.idioma.docsVisorTitulo());

		if (splitPrincipal != null) {
			splitPrincipal.setBorder(null);
			splitPrincipal.setBackground(colorBorde.obtener());
		}

		repaint();
	}

	protected void estilizarScroll(JScrollPane scroll, String titulo) {
		if (scroll == null) {
			return;
		}

		scroll.getViewport().setBackground(colorPanel.obtener());
		scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
				titulo, TitledBorder.LEFT, TitledBorder.TOP, new Font(Font.SANS_SERIF, Font.BOLD, 12),
				colorTexto.obtener()));
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();

		colorFondoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoPanel());
		colorDocumento.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoDocumento());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorTextoDocumento.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());

		elementos.add(colorFondoVentana);
		elementos.add(colorPanel);
		elementos.add(colorDocumento);
		elementos.add(colorTexto);
		elementos.add(colorTextoDocumento);
		elementos.add(colorBorde);

		return elementos;
	}
}