package com.asbestosstar.crashdetector.gui.tipos.configmods;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public class ConfigsModsGUIYunenoms extends ConfigsModsGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "yunenoms_config_editor";
	public static File imagen = Statics.carpeta.resolve("imagenes/yunenoms.png").toFile();

	private JTable tablaArchivos;
	private JTable tablaValores;

	private DefaultTableModel modeloArchivos;
	private DefaultTableModel modeloValores;

	private JTextField campoBuscar;
	private JComboBox<String> comboTipo;

	private JButton botonAbrir;
	private JButton botonGuardar;
	private JButton botonRecargar;
	private JButton botonCerrar;

	private final List<EntradaConfig> entradasFiltradas = new ArrayList<EntradaConfig>();

	private ConfigColor colorFondo = ConfigColor.de("tema.yunenoms_config.fondo", new Color(205, 238, 246));
	private ConfigColor colorPanel = ConfigColor.de("tema.yunenoms_config.panel", new Color(232, 248, 252));
	private ConfigColor colorTexto = ConfigColor.de("tema.yunenoms_config.texto", new Color(26, 41, 62));
	private ConfigColor colorBoton = ConfigColor.de("tema.yunenoms_config.boton", new Color(70, 180, 205));
	private ConfigColor colorTabla = ConfigColor.de("tema.yunenoms_config.tabla", new Color(244, 253, 255));
	private ConfigColor colorBorde = ConfigColor.de("tema.yunenoms_config.borde", new Color(116, 200, 219));
	private ConfigColor colorAcento = ConfigColor.de("tema.yunenoms_config.acento", new Color(235, 87, 125));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {
		setTitle(MonitorDePID.idioma.editorConfigsMods());
		setSize(1120, 720);
		setModal(true);
		setLayout(new BorderLayout(8, 8));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		buscarConfigsEditables();

		add(crearPanelIzquierdo(), BorderLayout.WEST);
		add(crearPanelDerecho(), BorderLayout.CENTER);
		add(crearPanelBotones(), BorderLayout.SOUTH);

		recargarTablaArchivos();
		recargarApariencia();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel crearPanelIzquierdo() {
		JPanel panel = new JPanel(new BorderLayout(6, 6));
		panel.setPreferredSize(new Dimension(310, 0));
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JLabel imagenLabel = new JLabel();
		imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imagenLabel.setVerticalAlignment(SwingConstants.TOP);
		cargarImagen(imagenLabel);

		JLabel titulo = new JLabel("<html><div style='width:260px; text-align:center;'>"
				+ MonitorDePID.idioma.editorConfigsMods() + "</div></html>");
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));

		modeloArchivos = new DefaultTableModel(
				new Object[] { MonitorDePID.idioma.rutaConfig(), MonitorDePID.idioma.tipoConfig() }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		tablaArchivos = new JTable(modeloArchivos);
		tablaArchivos.setRowHeight(24);
		tablaArchivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaArchivos.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				seleccionarArchivoDesdeTabla();
			}
		});

		JPanel superior = new JPanel(new BorderLayout(4, 4));
		superior.add(imagenLabel, BorderLayout.NORTH);
		superior.add(titulo, BorderLayout.SOUTH);

		panel.add(superior, BorderLayout.NORTH);
		panel.add(new JScrollPane(tablaArchivos), BorderLayout.CENTER);
		return panel;
	}

	private JPanel crearPanelDerecho() {
		JPanel panel = new JPanel(new BorderLayout(6, 6));
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));

		campoBuscar = new JTextField(28);
		campoBuscar.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				recargarTablaValores();
			}

			public void removeUpdate(DocumentEvent e) {
				recargarTablaValores();
			}

			public void changedUpdate(DocumentEvent e) {
				recargarTablaValores();
			}
		});

		comboTipo = new JComboBox<String>(new String[] { "Todos", "BOOLEANO", "ENTERO", "LARGO", "DECIMAL", "TEXTO" });
		comboTipo.addActionListener(e -> recargarTablaValores());

		filtros.add(new JLabel(MonitorDePID.idioma.buscarConfig()));
		filtros.add(campoBuscar);
		filtros.add(new JLabel(MonitorDePID.idioma.tipoConfig()));
		filtros.add(comboTipo);

		modeloValores = new DefaultTableModel(
				new Object[] { MonitorDePID.idioma.claveConfig(), MonitorDePID.idioma.tipoConfig(),
						MonitorDePID.idioma.valorConfig() },
				0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int r, int c) {
				return c == 2;
			}
		};

		tablaValores = new JTable(modeloValores);
		tablaValores.setRowHeight(32);
		tablaValores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaValores.getColumnModel().getColumn(2).setCellRenderer(new RenderValorConfig());
		tablaValores.getColumnModel().getColumn(2).setCellEditor(new EditorValorConfig());

		panel.add(filtros, BorderLayout.NORTH);
		panel.add(new JScrollPane(tablaValores), BorderLayout.CENTER);

		return panel;
	}

	private JPanel crearPanelBotones() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));

		botonAbrir = new JButton(MonitorDePID.idioma.abrirConfig());
		botonGuardar = new JButton(MonitorDePID.idioma.guardarConfig());
		botonRecargar = new JButton(MonitorDePID.idioma.recargarConfig());
		botonCerrar = new JButton(MonitorDePID.idioma.cancelar());

		botonAbrir.addActionListener(e -> seleccionarArchivoDesdeTabla());
		botonGuardar.addActionListener(e -> guardar());
		botonRecargar.addActionListener(e -> recargarTodo());
		botonCerrar.addActionListener(e -> dispose());

		panel.add(botonAbrir);
		panel.add(botonGuardar);
		panel.add(botonRecargar);
		panel.add(botonCerrar);

		return panel;
	}

	private void recargarTodo() {
		try {
			File seleccionado = archivoActual;
			buscarConfigsEditables();
			recargarTablaArchivos();

			if (seleccionado != null && seleccionado.exists()) {
				cargarArchivo(seleccionado);
				recargarTablaValores();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.errorCargandoConfig() + ": " + ex.getMessage(),
					MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
		}
	}

	private void seleccionarArchivoDesdeTabla() {
		int fila = tablaArchivos.getSelectedRow();
		if (fila < 0 || fila >= archivosConfigs.size()) {
			return;
		}

		try {
			File archivo = archivosConfigs.get(fila);
			cargarArchivo(archivo);
			recargarTablaValores();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.errorCargandoConfig() + ": " + ex.getMessage(),
					MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
		}
	}

	private void guardar() {
		try {
			if (tablaValores != null && tablaValores.isEditing()) {
				tablaValores.getCellEditor().stopCellEditing();
			}

			guardarArchivoActual();

			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.configGuardada(), MonitorDePID.idioma.informacion(),
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.errorGuardandoConfig() + ": " + ex.getMessage(),
					MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
		}
	}

	private void recargarTablaArchivos() {
		modeloArchivos.setRowCount(0);

		for (File f : archivosConfigs) {
			modeloArchivos.addRow(new Object[] { f.getPath(), extension(f) });
		}

		if (!archivosConfigs.isEmpty() && tablaArchivos.getSelectedRow() < 0) {
			tablaArchivos.setRowSelectionInterval(0, 0);
		}
	}

	private void recargarTablaValores() {
		if (modeloValores == null) {
			return;
		}

		modeloValores.setRowCount(0);
		entradasFiltradas.clear();

		String buscar = campoBuscar == null || campoBuscar.getText() == null ? "" : campoBuscar.getText().trim().toLowerCase();
		String tipo = comboTipo == null || comboTipo.getSelectedItem() == null ? "Todos"
				: String.valueOf(comboTipo.getSelectedItem());

		for (EntradaConfig e : entradas) {
			if (!buscar.isEmpty() && !e.ruta.toLowerCase().contains(buscar)) {
				continue;
			}

			if (!"Todos".equals(tipo) && !e.tipo.name().equals(tipo)) {
				continue;
			}

			entradasFiltradas.add(e);
			modeloValores.addRow(new Object[] { e.ruta, e.tipo.name(), e });
		}
	}

	private void cargarImagen(JLabel label) {
		try {
			if (imagen != null && imagen.exists()) {
				ImageIcon icon = new ImageIcon(imagen.getAbsolutePath());
				Image img = icon.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
				label.setIcon(new ImageIcon(img));
			} else {
				label.setText(MonitorDePID.idioma.imagenNoEncontrada());
			}
		} catch (Exception e) {
			label.setText(MonitorDePID.idioma.errorCargandoImagen());
		}
	}

	@Override
	public void recargarApariencia() {
		Color fondo = colorFondo.obtener();
		Color panel = colorPanel.obtener();
		Color texto = colorTexto.obtener();
		Color boton = colorBoton.obtener();
		Color tabla = colorTabla.obtener();
		Color borde = colorBorde.obtener();
		Color acento = colorAcento.obtener();

		getContentPane().setBackground(fondo);
		pintar(getContentPane(), fondo, texto);

		estilizarTabla(tablaArchivos, tabla, texto, borde, acento);
		estilizarTabla(tablaValores, tabla, texto, borde, acento);

		estilizarBoton(botonAbrir, boton, Color.WHITE, borde);
		estilizarBoton(botonGuardar, boton, Color.WHITE, borde);
		estilizarBoton(botonRecargar, boton, Color.WHITE, borde);
		estilizarBoton(botonCerrar, boton, Color.WHITE, borde);

		if (campoBuscar != null) {
			campoBuscar.setBackground(tabla);
			campoBuscar.setForeground(texto);
			campoBuscar.setCaretColor(texto);
			campoBuscar.setBorder(BorderFactory.createLineBorder(borde, 1));
		}

		if (comboTipo != null) {
			comboTipo.setBackground(tabla);
			comboTipo.setForeground(texto);
		}

		revalidate();
		repaint();
	}

	private void pintar(java.awt.Container c, Color fondo, Color texto) {
		c.setBackground(fondo);

		for (java.awt.Component comp : c.getComponents()) {
			comp.setForeground(texto);

			if (comp instanceof JPanel) {
				comp.setBackground(fondo);
			}

			if (comp instanceof JLabel) {
				comp.setForeground(texto);
			}

			if (comp instanceof java.awt.Container) {
				pintar((java.awt.Container) comp, fondo, texto);
			}
		}
	}

	private void estilizarTabla(JTable t, Color fondo, Color texto, Color borde, Color seleccion) {
		if (t == null) {
			return;
		}

		t.setBackground(fondo);
		t.setForeground(texto);
		t.setGridColor(borde);
		t.setSelectionBackground(seleccion);
		t.setSelectionForeground(Color.WHITE);
		t.setFont(new Font(CrashDetectorGUI.esMac() ? "SansSerif" : "Segoe UI", Font.PLAIN, 12));
		t.getTableHeader().setBackground(colorPanel.obtener());
		t.getTableHeader().setForeground(texto);
		t.getTableHeader().setFont(new Font(CrashDetectorGUI.esMac() ? "SansSerif" : "Segoe UI", Font.BOLD, 12));
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<ElementoConfig>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorTabla.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBordeListas());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		//colorAcento.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorAcento());

		ret.add(colorFondo);
		ret.add(colorPanel);
		ret.add(colorTexto);
		ret.add(colorBoton);
		ret.add(colorTabla);
		ret.add(colorBorde);
		ret.add(colorAcento);

		return ret;
	}
}