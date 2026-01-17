package com.asbestosstar.crashdetector.gui.tipos.lanzeresbuenos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * GUI para "lanzadores buenos" (alentados). - JSON: arreglo de strings (IDs). -
 * En el combo, los IDs que CrashDetector considera "buenos" aparecen en verde.
 * - Paleta por defecto inspirada en la imagen MaidMint.
 */
public class LanzerBuenoGUIMaidMint extends LanzerBuenoGUI {
	private static final long serialVersionUID = 1L;

	public static String ID = "maid_mint";
	public static File imagen = Statics.carpeta.resolve("imagenes/maid_mint.png").toFile(); // 204 × 266 píxeles

	// UI
	private JTable tablaRecomendados;
	private DefaultTableModel modeloRecomendados;
	private JButton botonAgregar;
	private JButton botonQuitar;
	private JButton botonGuardar;
	private JButton botonCancelar;
	private JLabel imagenLabel;
	private JLabel etiquetaAviso;
	private JPanel panelPrincipal;
	private JLabel etiquetaTitulo;
	private JComboBox<String> comboLanzadores;

	// Colores (defaults similares a MaidMint)
	private ConfigColor colorFondoVentana; // menta muy clara
	private ConfigColor colorTexto; // gris oscuro
	private ConfigColor colorBoton; // menta/teal
	private ConfigColor colorTabla; // casi blanco cálido
	private ConfigColor colorResaltoRecomendado; // verde (recomendado por CD)
	private ConfigColor colorBordePanel; // gris/teal suave

	@Override
	public void init() {
		// Defaults aproximados a la imagen (paleta suave menta)
		colorFondoVentana = ConfigColor.de("tema.maid_mint.color.fondo.ventana", new Color(218, 253, 249));
		colorTexto = ConfigColor.de("tema.maid_mint.color.texto", new Color(98, 101, 105));
		colorBoton = ConfigColor.de("tema.maid_mint.color.boton", new Color(161, 220, 215));
		colorTabla = ConfigColor.de("tema.maid_mint.color.tabla", new Color(252, 245, 238));
		colorResaltoRecomendado = ConfigColor.de("tema.maid_mint.color.resalto.recomendado", new Color(110, 210, 160));
		colorBordePanel = ConfigColor.de("tema.maid_mint.color.borde.panel", new Color(153, 183, 184));

		setTitle(MonitorDePID.idioma.lanzadoresRecomendados()); // requiere método en Idioma
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(true);
		setSize(900, 650);
		setAlwaysOnTop(false);

		// Cargar datos
		cargarDatos();

		// Modelo/tabla
		modeloRecomendados = new DefaultTableModel(new Object[] { MonitorDePID.idioma.nombreLanzador() }, 0);

		tablaRecomendados = new JTable(modeloRecomendados);
		configurarTabla(tablaRecomendados);

		// Panel principal
		panelPrincipal = new JPanel(new GridBagLayout());
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelPrincipal.setOpaque(true);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new java.awt.Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.BOTH;

		// Panel izquierdo: imagen + mensaje debajo
		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setPreferredSize(new Dimension(220, 0));
		panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		panelIzquierdo.setOpaque(true);

		imagenLabel = new JLabel();
		imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imagenLabel.setVerticalAlignment(SwingConstants.TOP);
		cargarImagen();

		etiquetaAviso = crearEtiquetaAvisoBajoImagen();

		panelIzquierdo.add(imagenLabel, BorderLayout.NORTH);
		panelIzquierdo.add(etiquetaAviso, BorderLayout.SOUTH);

		aplicarFondoPanelIzquierdo(panelIzquierdo);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 6;
		gbc.weightx = 0.25;
		gbc.weighty = 1.0;
		panelPrincipal.add(panelIzquierdo, gbc);

		// Título
		etiquetaTitulo = new JLabel(MonitorDePID.idioma.lanzadoresRecomendados());
		etiquetaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));

		// Combo
		comboLanzadores = new JComboBox<>();
		comboLanzadores.setPreferredSize(new Dimension(280, 30));
		configurarComboLanzadores();

		// Botones
		JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		panelAcciones.setOpaque(true);

		botonAgregar = new JButton(MonitorDePID.idioma.agregarLanzador());
		botonQuitar = new JButton(MonitorDePID.idioma.quitarLanzador());
		panelAcciones.add(botonAgregar);
		panelAcciones.add(botonQuitar);

		JScrollPane scroll = new JScrollPane(tablaRecomendados);

		JPanel panelGuardado = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelGuardado.setOpaque(true);

		botonGuardar = new JButton(MonitorDePID.idioma.guardarCambios());
		botonCancelar = new JButton(MonitorDePID.idioma.cancelar());
		panelGuardado.add(botonGuardar);
		panelGuardado.add(botonCancelar);

		// Layout derecha
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		gbc.weightx = 0.75;
		gbc.weighty = 0.06;
		panelPrincipal.add(etiquetaTitulo, gbc);

		gbc.gridy = 1;
		gbc.weighty = 0.08;
		panelPrincipal.add(comboLanzadores, gbc);

		gbc.gridy = 2;
		gbc.weighty = 0.08;
		panelPrincipal.add(panelAcciones, gbc);

		gbc.gridy = 3;
		gbc.weighty = 0.68;
		panelPrincipal.add(scroll, gbc);

		gbc.gridy = 4;
		gbc.weighty = 0.10;
		panelPrincipal.add(panelGuardado, gbc);

		getContentPane().add(panelPrincipal, BorderLayout.CENTER);

		// Datos
		cargarComboLanzadores();
		cargarDatosEnTabla();

		// Listeners
		agregarListeners();

		setLocationRelativeTo(null);
		recargarApariencia();
		setVisible(true);
	}

	/**
	 * Mensaje breve bajo la imagen.
	 */
	private JLabel crearEtiquetaAvisoBajoImagen() {
		JLabel lbl = new JLabel("<html><div style='width:200px; text-align:center;'>"
				+ MonitorDePID.idioma.lanzadoresRecomendadosAviso() + "</div></html>");
		lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setForeground(colorTexto.obtener());
		lbl.setOpaque(false);
		return lbl;
	}

	/**
	 * Evita paneles “blancos” imponiendo el fondo del tema.
	 */
	private void aplicarFondoPanelIzquierdo(JPanel panelIzquierdo) {
		if (panelIzquierdo != null) {
			panelIzquierdo.setBackground(colorFondoVentana.obtener());
		}
		if (imagenLabel != null) {
			imagenLabel.setOpaque(true);
			imagenLabel.setBackground(colorFondoVentana.obtener());
		}
	}

	/**
	 * Carga imagen del personaje.
	 */
	private void cargarImagen() {
		try {
			if (imagen.exists()) {
				ImageIcon icon = new ImageIcon(imagen.getAbsolutePath());
				Image img = icon.getImage();

				int newWidth = 200;
				int newHeight = (int) ((double) img.getHeight(null) / img.getWidth(null) * newWidth);

				img = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				imagenLabel.setIcon(new ImageIcon(img));
			} else {
				imagenLabel.setText(MonitorDePID.idioma.imagenNoEncontrada());
				imagenLabel.setForeground(colorTexto.obtener());
			}
		} catch (Exception e) {
			imagenLabel.setText(MonitorDePID.idioma.errorCargandoImagen());
			imagenLabel.setForeground(colorTexto.obtener());
		}
	}

	/**
	 * Tabla simple: solo muestra IDs recomendados.
	 */
	private void configurarTabla(JTable tabla) {
		tabla.setFillsViewportHeight(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowHeight(26);
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));

		tabla.getColumnModel().getColumn(0).setPreferredWidth(380);

		tabla.setDefaultRenderer(Object.class, new TableCellRenderer() {
			@Override
			public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				String id = value == null ? "" : value.toString();
				JLabel label = new JLabel(id);
				label.setOpaque(true);
				label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
				label.setFont(new Font("Segoe UI", Font.PLAIN, 13));

				Color fondo = isSelected ? colorBoton.obtener() : colorTabla.obtener();
				label.setBackground(fondo);

				// Si CrashDetector lo marca como bueno → verde por defecto
				boolean bueno = esRecomendadoPorCrashDetector(id);
				label.setForeground(bueno ? colorResaltoRecomendado.obtener() : colorTexto.obtener());
				return label;
			}
		});
	}

	/**
	 * Renderer del combo: verde por defecto para los que CD sugiere como buenos.
	 */
	private void configurarComboLanzadores() {
		comboLanzadores.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			String id = value == null ? "" : value.toString();
			JLabel label = new JLabel(id);
			label.setOpaque(true);
			label.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
			label.setFont(new Font("Segoe UI", Font.PLAIN, 13));

			Color fondo = isSelected ? colorBoton.obtener() : colorTabla.obtener();
			label.setBackground(fondo);

			boolean bueno = esRecomendadoPorCrashDetector(id);
			label.setForeground(bueno ? colorResaltoRecomendado.obtener() : colorTexto.obtener());
			return label;
		});
	}

	/**
	 * Carga los lanzadores disponibles en el combo. - Los lanzadores que
	 * CrashDetector considera "buenos" aparecen PRIMERO (verdes). - Los lanzadores
	 * ya presentes en "recomendados" NO se muestran.
	 */
	private void cargarComboLanzadores() {
		comboLanzadores.removeAllItems();

		// Listas separadas para controlar el orden
		List<String> recomendadosPorCD = new ArrayList<>();
		List<String> normales = new ArrayList<>();

		// Obtener todos los lanzadores conocidos
		List<String> todos = obtenerNombresLanzadores();
		for (String id : todos) {

			if (id == null)
				continue;

			// Si ya está en el conjunto de recomendados, no aparece en el combo
			if (recomendados.contains(id)) {
				continue;
			}

			// Clasificar según CrashDetector
			if (esRecomendadoPorCrashDetector(id)) {
				recomendadosPorCD.add(id);
			} else {
				normales.add(id);
			}
		}

		// Añadir primero los recomendados por CrashDetector (verdes)
		for (String id : recomendadosPorCD) {
			comboLanzadores.addItem(id);
		}

		// Añadir después los normales
		for (String id : normales) {
			comboLanzadores.addItem(id);
		}

		// Habilitar / deshabilitar botón agregar
		if (botonAgregar != null) {
			botonAgregar.setEnabled(comboLanzadores.getItemCount() > 0);
		}
	}

	/**
	 * Tabla: carga IDs del conjunto.
	 */
	private void cargarDatosEnTabla() {
		modeloRecomendados.setRowCount(0);
		for (String id : recomendados) {
			modeloRecomendados.addRow(new Object[] { id });
		}

		botonQuitar.setEnabled(modeloRecomendados.getRowCount() > 0);
	}

	private void agregarListeners() {
		botonAgregar.addActionListener(e -> {
			String id = (String) comboLanzadores.getSelectedItem();
			if (id != null && !id.trim().isEmpty()) {
				agregarARecomendados(id);
				cargarComboLanzadores();
				cargarDatosEnTabla();
			}
		});

		botonQuitar.addActionListener(e -> {
			int fila = tablaRecomendados.getSelectedRow();
			if (fila >= 0) {
				String id = (String) modeloRecomendados.getValueAt(fila, 0);
				quitarDeRecomendados(id);
				cargarComboLanzadores();
				cargarDatosEnTabla();
			} else {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.seleccionaLanzadorQuitar(),
						MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
			}
		});

		botonGuardar.addActionListener(e -> {
			guardarDatos();
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.cambiosGuardadosExitosamente(),
					MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
		});

		botonCancelar.addActionListener(e -> dispose());
	}

	@Override
	public void recargarApariencia() {
		getContentPane().setBackground(colorFondoVentana.obtener());
		panelPrincipal.setBackground(colorFondoVentana.obtener());

		etiquetaTitulo.setForeground(colorTexto.obtener());

		if (etiquetaAviso != null) {
			etiquetaAviso.setForeground(colorTexto.obtener());
		}
		if (imagenLabel != null) {
			imagenLabel.setBackground(colorFondoVentana.obtener());
		}

		tablaRecomendados.setBackground(colorTabla.obtener());
		tablaRecomendados.setForeground(colorTexto.obtener());
		tablaRecomendados.setGridColor(colorBordePanel.obtener());

		estilizarBoton(botonAgregar);
		estilizarBoton(botonQuitar);
		estilizarBoton(botonGuardar);
		estilizarBoton(botonCancelar);

		estilizarCombo(comboLanzadores);

		// Evitar paneles blancos por Look&Feel
		for (java.awt.Component c : panelPrincipal.getComponents()) {
			if (c instanceof javax.swing.JComponent) {
				c.setBackground(colorFondoVentana.obtener());
			}
		}

		revalidate();
		repaint();
	}

	private void estilizarBoton(JButton btn) {
		btn.setBackground(colorBoton.obtener());
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createLineBorder(colorBordePanel.obtener(), 1));
		btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
	}

	private void estilizarCombo(JComboBox<?> combo) {
		combo.setBackground(colorTabla.obtener());
		combo.setForeground(colorTexto.obtener());
		combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<>();

		colorFondoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		ret.add(colorFondoVentana);

		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		ret.add(colorTexto);

		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		ret.add(colorBoton);

		colorTabla.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBordeListas());
		ret.add(colorTabla);

		// Necesitas un nombre en Idioma para este color (ej: colorResultadoCorrecto)
		colorResaltoRecomendado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorResultadoCorrecto());
		ret.add(colorResaltoRecomendado);

		colorBordePanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		ret.add(colorBordePanel);

		return ret;
	}
}
