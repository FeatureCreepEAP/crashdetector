package com.asbestosstar.crashdetector.gui.tipos.scriptide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public class ScriptIDEGUINiwaJPlus extends ScriptIDEGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "niwa_jpp6_script_ide";
	public static File imagen = Statics.carpeta.resolve("imagenes/niwa.png").toFile();

	public JComboBox<TipoProyectoScript> comboProyecto;
	public JButton botonNuevo;
	public JButton botonAbrirCarpeta;
	public JButton botonAbrir;
	public JButton botonGuardar;
	public JButton botonGuardarComo;
	public JButton botonDeps;
	public JButton botonCompletar;
	public JPanel panelRaiz;
	public JPanel barraHerramientas;
	public JPanel panelIzquierdo;
	public JPanel panelEstado;

	public ConfigColor colorFondo = ConfigColor.de("tema.scriptide.niwa_jpp6.fondo", new Color(212, 208, 200));
	public ConfigColor colorPanel = ConfigColor.de("tema.scriptide.niwa_jpp6.panel", new Color(236, 233, 216));
	public ConfigColor colorPanelOscuro = ConfigColor.de("tema.scriptide.niwa_jpp6.panel_oscuro", new Color(188, 184, 176));
	public ConfigColor colorTexto = ConfigColor.de("tema.scriptide.niwa_jpp6.texto", new Color(24, 28, 36));
	public ConfigColor colorEditor = ConfigColor.de("tema.scriptide.niwa_jpp6.editor", new Color(255, 252, 242));
	public ConfigColor colorBorde = ConfigColor.de("tema.scriptide.niwa_jpp6.borde", new Color(110, 105, 96));
	public ConfigColor colorAcentoNiwa = ConfigColor.de("tema.scriptide.niwa_jpp6.acento_niwa", new Color(230, 159, 126));
	public ConfigColor colorAcentoVerde = ConfigColor.de("tema.scriptide.niwa_jpp6.acento_verde", new Color(166, 221, 198));
	public ConfigColor colorKeyword = ConfigColor.de("tema.scriptide.niwa_jpp6.keyword", new Color(0, 0, 160));
	public ConfigColor colorComentario = ConfigColor.de("tema.scriptide.niwa_jpp6.comentario", new Color(0, 128, 0));
	public ConfigColor colorCadena = ConfigColor.de("tema.scriptide.niwa_jpp6.cadena", new Color(160, 64, 0));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {
		setTitle(MonitorDePID.idioma.ideScriptTitulo());
		setSize(1160, 760);
		setModal(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		editor = new JTextPane();
		inicializarEditorBase();

		panelRaiz = new JPanel(new BorderLayout(2, 2));
		barraHerramientas = crearBarraHerramientas();
		panelIzquierdo = crearPanelIzquierdo();
		panelEstado = crearPanelEstado();

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, crearPanelEditor());
		split.setDividerLocation(285);
		split.setContinuousLayout(true);

		panelRaiz.add(barraHerramientas, BorderLayout.NORTH);
		panelRaiz.add(split, BorderLayout.CENTER);
		panelRaiz.add(panelEstado, BorderLayout.SOUTH);

		add(panelRaiz, BorderLayout.CENTER);
		recargarApariencia();
		actualizarEstado();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public JPanel crearBarraHerramientas() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 3));
		p.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		botonNuevo = new JButton(MonitorDePID.idioma.ideScriptNuevo());
		botonAbrirCarpeta = new JButton(MonitorDePID.idioma.ideScriptAbrirCarpeta());
		botonAbrir = new JButton(MonitorDePID.idioma.ideScriptAbrirArchivo());
		botonGuardar = new JButton(MonitorDePID.idioma.guardarConfig());
		botonGuardarComo = new JButton(MonitorDePID.idioma.ideScriptGuardarComo());
		botonDeps = new JButton(MonitorDePID.idioma.ideScriptDescargarDeps());
		botonCompletar = new JButton(MonitorDePID.idioma.ideScriptCompletar());

		comboProyecto = new JComboBox<TipoProyectoScript>(TipoProyectoScript.values());
		comboProyecto.setRenderer(new RenderTipoProyecto());
		comboProyecto.setSelectedItem(proyectoActual);
		comboProyecto.addActionListener(e -> {
			TipoProyectoScript seleccionado = (TipoProyectoScript) comboProyecto.getSelectedItem();
			if (seleccionado != null && seleccionado.habilitadoAhora) {
				cambiarProyecto(seleccionado);
			} else {
				comboProyecto.setSelectedItem(proyectoActual);
				cambiarProyecto(seleccionado);
			}
		});

		botonNuevo.addActionListener(e -> nuevoArchivo());
		botonAbrirCarpeta.addActionListener(e -> abrirCarpetaProyecto());
		botonAbrir.addActionListener(e -> abrirArchivoConDialogo());
		botonGuardar.addActionListener(e -> guardarArchivoActual());
		botonGuardarComo.addActionListener(e -> guardarArchivoComo());
		botonDeps.addActionListener(e -> descargarDependenciasIDE());
		botonCompletar.addActionListener(e -> mostrarCompletado());

		p.add(new JLabel(MonitorDePID.idioma.ideScriptProyecto()));
		p.add(comboProyecto);
		p.add(separador());
		p.add(botonNuevo);
		p.add(botonAbrirCarpeta);
		p.add(botonAbrir);
		p.add(botonGuardar);
		p.add(botonGuardarComo);
		p.add(separador());
		p.add(botonCompletar);
		p.add(botonDeps);
		return p;
	}

	public JLabel separador() {
		JLabel l = new JLabel(" ");
		l.setPreferredSize(new Dimension(8, 24));
		return l;
	}

	public JPanel crearPanelIzquierdo() {
		JPanel p = new JPanel(new BorderLayout(4, 4));
		p.setPreferredSize(new Dimension(285, 0));
		p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		JLabel img = new JLabel();
		img.setHorizontalAlignment(SwingConstants.CENTER);
		cargarImagenNiwa(img);

		JLabel titulo = new JLabel(MonitorDePID.idioma.ideScriptExplorador(), SwingConstants.CENTER);
		titulo.setFont(new Font(CrashDetectorGUI.esMac() ? "SansSerif" : "MS Sans Serif", Font.BOLD, 12));

		listaArchivos = new JList<File>(modeloArchivos);
		listaArchivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaArchivos.setCellRenderer(new RenderArchivoProyecto());
		listaArchivos.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() >= 2) {
					abrirArchivoDesdeLista();
				}
			}
		});

		JPanel superior = new JPanel(new BorderLayout());
		superior.add(img, BorderLayout.NORTH);
		superior.add(titulo, BorderLayout.SOUTH);

		p.add(superior, BorderLayout.NORTH);
		p.add(new JScrollPane(listaArchivos), BorderLayout.CENTER);
		return p;
	}

	public JPanel crearPanelEditor() {
		JPanel p = new JPanel(new BorderLayout(0, 0));
		p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED),
				BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		p.add(new JScrollPane(editor), BorderLayout.CENTER);
		return p;
	}

	public JPanel crearPanelEstado() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		etiquetaEstado = new JLabel(" ");
		etiquetaEstado.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
		p.add(etiquetaEstado, BorderLayout.CENTER);
		return p;
	}

	public void cargarImagenNiwa(JLabel label) {
		try {
			if (imagen != null && imagen.exists()) {
				ImageIcon icon = new ImageIcon(imagen.getAbsolutePath());
				Image scaled = icon.getImage().getScaledInstance(126, 126, Image.SCALE_SMOOTH);
				label.setIcon(new ImageIcon(scaled));
			}
		} catch (Throwable ignored) {
		}
	}

	public void recargarApariencia() {
		Color fondo = colorFondo.obtener();
		Color panel = colorPanel.obtener();
		Color panelOscuro = colorPanelOscuro.obtener();
		Color texto = colorTexto.obtener();
		Color editorFondo = colorEditor.obtener();
		Color borde = colorBorde.obtener();
		Color acento = colorAcentoNiwa.obtener();

		pintar(panelRaiz, fondo, texto);
		pintar(barraHerramientas, panel, texto);
		pintar(panelIzquierdo, panel, texto);
		pintar(panelEstado, panelOscuro, texto);

		if (editor != null) {
			editor.setBackground(editorFondo);
			editor.setForeground(texto);
			editor.setCaretColor(texto);
		}
		if (listaArchivos != null) {
			listaArchivos.setBackground(editorFondo);
			listaArchivos.setForeground(texto);
		}
		if (listaCompletado != null) {
			listaCompletado.setBackground(editorFondo);
			listaCompletado.setForeground(texto);
		}

		estilizarBoton(botones(), panel, texto, borde, acento);
		revalidate();
		repaint();
	}

	public JButton[] botones() {
		return new JButton[] { botonNuevo, botonAbrirCarpeta, botonAbrir, botonGuardar, botonGuardarComo, botonDeps,
				botonCompletar };
	}

	public void estilizarBoton(JButton[] botones, Color fondo, Color texto, Color borde, Color acento) {
		if (botones == null) {
			return;
		}
		for (JButton b : botones) {
			if (b == null) {
				continue;
			}
			b.setBackground(fondo);
			b.setForeground(texto);
			b.setFocusPainted(false);
			b.setOpaque(true);
			b.setContentAreaFilled(true);
			b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borde, 1),
					BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, acento),
							BorderFactory.createEmptyBorder(4, 9, 4, 9))));
		}
	}

	public void pintar(java.awt.Container c, Color fondo, Color texto) {
		if (c == null) {
			return;
		}
		c.setBackground(fondo);
		for (java.awt.Component comp : c.getComponents()) {
			comp.setForeground(texto);
			if (comp instanceof java.awt.Container) {
				((java.awt.Container) comp).setBackground(fondo);
				pintar((java.awt.Container) comp, fondo, texto);
			}
		}
	}

	@Override
	public Color colorTextoEditor() {
		return colorTexto.obtener();
	}

	@Override
	public Color colorKeywordEditor() {
		return colorKeyword.obtener();
	}

	@Override
	public Color colorComentarioEditor() {
		return colorComentario.obtener();
	}

	@Override
	public Color colorCadenaEditor() {
		return colorCadena.obtener();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<ElementoConfig>();
		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorPanelOscuro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBordeListas());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorEditor.establecerNombreParaMostrar(() -> MonitorDePID.idioma.ideScriptColorEditor());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		colorAcentoNiwa.establecerNombreParaMostrar(() -> "acento");
		colorAcentoVerde.establecerNombreParaMostrar(() -> "acento");
		colorKeyword.establecerNombreParaMostrar(() -> MonitorDePID.idioma.ideScriptColorKeyword());
		colorComentario.establecerNombreParaMostrar(() -> MonitorDePID.idioma.ideScriptColorComentario());
		colorCadena.establecerNombreParaMostrar(() -> MonitorDePID.idioma.ideScriptColorCadena());
		ret.add(colorFondo);
		ret.add(colorPanel);
		ret.add(colorPanelOscuro);
		ret.add(colorTexto);
		ret.add(colorEditor);
		ret.add(colorBorde);
		ret.add(colorAcentoNiwa);
		ret.add(colorAcentoVerde);
		ret.add(colorKeyword);
		ret.add(colorComentario);
		ret.add(colorCadena);
		return ret;
	}

	public class RenderArchivoProyecto extends javax.swing.DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected,
				boolean cellHasFocus) {
			JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, selected, cellHasFocus);
			if (value instanceof File) {
				File f = (File) value;
				if (carpetaProyecto != null) {
					try {
						l.setText(carpetaProyecto.toPath().relativize(f.toPath()).toString());
					} catch (Exception ex) {
						l.setText(f.getName());
					}
				} else {
					l.setText(f.getName());
				}
			}
			return l;
		}
	}
}