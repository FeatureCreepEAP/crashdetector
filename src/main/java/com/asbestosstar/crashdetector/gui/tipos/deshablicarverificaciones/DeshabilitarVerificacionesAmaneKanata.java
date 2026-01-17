package com.asbestosstar.crashdetector.gui.tipos.deshablicarverificaciones;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.table.TableColumnModel;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public class DeshabilitarVerificacionesAmaneKanata extends DeshablicarVerificaciones {
	private static final long serialVersionUID = 1L;
	public static String ID = "amane_kanata";
	public static File imagen = Statics.carpeta.resolve("imagenes/amane_kanata.png").toFile(); // 124 × 268 píxeles

	// Configuraciones de color
	private ConfigColor colorFondoVentana;
	private ConfigColor colorTexto;
	private ConfigColor colorBoton;
	private ConfigColor colorCajaTexto;
	private ConfigColor colorBordePanel;
	private ConfigColor colorVerificacionCorporativa;

	// Componentes de la interfaz
	private JTable tablaHabilitadas;
	private JTable tablaDeshabilitadas;
	private DefaultTableModel modeloHabilitadas;
	private DefaultTableModel modeloDeshabilitadas;
	private JButton botonMoverDerecha;
	private JButton botonMoverIzquierda;
	private JButton botonDeshabilitarNoCorporativas;
	private JPanel panelPrincipal;
	private JLabel imagenLabel;
	private JLabel etiquetaHabilitadas;
	private JLabel etiquetaDeshabilitadas;
	private JLabel etiquetaMensajeAmaneKanata;

	@Override
	public void init() {

		// ====== COLORES DEL TEMA ======
		colorFondoVentana = ConfigColor.de("tema.amane_kanata.color.fondo.ventana", new Color(245, 252, 255));
		colorTexto = ConfigColor.de("tema.amane_kanata.color.texto", new Color(45, 56, 72));
		colorBoton = ConfigColor.de("tema.amane_kanata.color.boton", new Color(95, 170, 230));
		colorCajaTexto = ConfigColor.de("tema.amane_kanata.color.caja_texto", new Color(255, 255, 255));
		colorBordePanel = ConfigColor.de("tema.amane_kanata.color.borde_panel", new Color(204, 220, 235));
		colorVerificacionCorporativa = ConfigColor.de("tema.amane_kanata.color.verificacion_corporativa",
				new Color(120, 210, 170));

		setTitle(MonitorDePID.idioma.gestionVerificaciones());
		setModal(true);
		setResizable(true);
		setSize(900, 680); // cabe en 1024x768
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// ====== MODELOS NO EDITABLES ======
		modeloHabilitadas = new DefaultTableModel(
				new Object[] { MonitorDePID.idioma.idVerificacion(), MonitorDePID.idioma.nombreVerificacion(),
						MonitorDePID.idioma.codigoVerificacion(), MonitorDePID.idioma.documentacionVerificacion() },
				0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modeloDeshabilitadas = new DefaultTableModel(
				new Object[] { MonitorDePID.idioma.idVerificacion(), MonitorDePID.idioma.nombreVerificacion(),
						MonitorDePID.idioma.codigoVerificacion(), MonitorDePID.idioma.documentacionVerificacion() },
				0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaHabilitadas = new JTable(modeloHabilitadas);
		tablaDeshabilitadas = new JTable(modeloDeshabilitadas);

		configurarTabla(tablaHabilitadas);
		configurarTabla(tablaDeshabilitadas);

		// ====== PANEL PRINCIPAL ======
		panelPrincipal = new JPanel(new GridBagLayout());
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(8, 4, 8, 8));
		panelPrincipal.setBackground(colorFondoVentana.obtener());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new java.awt.Insets(4, 4, 4, 4);

		// ====== PANEL IZQUIERDO (IMAGEN) ======
		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setPreferredSize(new Dimension(170, 0));
		panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		panelIzquierdo.setBackground(colorFondoVentana.obtener());

		imagenLabel = new JLabel();
		imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cargarImagen();

		etiquetaMensajeAmaneKanata = new JLabel(MonitorDePID.idioma.mensajeAmaneKanata(), SwingConstants.CENTER);
		etiquetaMensajeAmaneKanata.setFont(new Font("Segoe UI", Font.ITALIC, 12));

		panelIzquierdo.add(imagenLabel, BorderLayout.CENTER);
		panelIzquierdo.add(etiquetaMensajeAmaneKanata, BorderLayout.SOUTH);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 6;
		gbc.weightx = 0.2;
		gbc.weighty = 1.0;
		panelPrincipal.add(panelIzquierdo, gbc);

		// ====== ETIQUETAS ======
		etiquetaHabilitadas = new JLabel(MonitorDePID.idioma.verificacionesHabilitadas());
		etiquetaHabilitadas.setFont(new Font("Segoe UI", Font.BOLD, 14));

		etiquetaDeshabilitadas = new JLabel(MonitorDePID.idioma.verificacionesDeshabilitadas());
		etiquetaDeshabilitadas.setFont(new Font("Segoe UI", Font.BOLD, 14));

		// ====== SCROLLS (ALTURA CONTROLADA) ======
		JScrollPane scrollHabilitadas = new JScrollPane(tablaHabilitadas);
		scrollHabilitadas.setPreferredSize(new Dimension(520, 230));

		JScrollPane scrollDeshabilitadas = new JScrollPane(tablaDeshabilitadas);
		scrollDeshabilitadas.setPreferredSize(new Dimension(520, 170));

		// ====== BOTONES CENTRALES (LADO A LADO) ======
		JPanel panelBotonesMovimiento = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 0));
		panelBotonesMovimiento.setOpaque(false);

		botonMoverIzquierda = new JButton("<<");
		botonMoverDerecha = new JButton(">>");

		panelBotonesMovimiento.add(botonMoverIzquierda);
		panelBotonesMovimiento.add(botonMoverDerecha);

		botonDeshabilitarNoCorporativas = new JButton(MonitorDePID.idioma.deshabilitarNoCorporativas());

		// ====== LAYOUT DERECHO ======
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0.8;

		gbc.gridy = 0;
		gbc.weighty = 0;
		panelPrincipal.add(etiquetaHabilitadas, gbc);

		gbc.gridy = 1;
		gbc.weighty = 0.45;
		panelPrincipal.add(scrollHabilitadas, gbc);

		gbc.gridy = 2;
		gbc.weighty = 0;
		panelPrincipal.add(panelBotonesMovimiento, gbc);

		gbc.gridy = 3;
		gbc.weighty = 0;
		panelPrincipal.add(etiquetaDeshabilitadas, gbc);

		gbc.gridy = 4;
		gbc.weighty = 0.35;
		panelPrincipal.add(scrollDeshabilitadas, gbc);

		gbc.gridy = 5;
		gbc.weighty = 0;
		panelPrincipal.add(botonDeshabilitarNoCorporativas, gbc);

		getContentPane().add(panelPrincipal, BorderLayout.CENTER);

		cargarDatos();
		agregarListeners();
		recargarApariencia();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Carga la imagen de Amane Kanata
	 */
	private void cargarImagen() {
		try {
			if (imagen.exists()) {
				ImageIcon icon = new ImageIcon(imagen.getAbsolutePath());
				Image img = icon.getImage();

				// Calcular nueva dimensión manteniendo proporción
				int newWidth = 160;
				int newHeight = (int) ((double) img.getHeight(null) / img.getWidth(null) * newWidth);

				img = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				imagenLabel.setIcon(new ImageIcon(img));
			} else {
				imagenLabel.setText(MonitorDePID.idioma.imagenNoEncontrada());
			}
		} catch (Exception e) {
			imagenLabel.setText(MonitorDePID.idioma.errorCargandoImagen());
		}
	}

	/**
	 * Configura la tabla para mostrar los datos correctamente
	 * 
	 * @param tabla Tabla a configurar
	 */
	private void configurarTabla(JTable tabla) {

		tabla.setRowHeight(26);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setFillsViewportHeight(true);

		TableColumnModel cols = tabla.getColumnModel();
		cols.getColumn(0).setPreferredWidth(120);
		cols.getColumn(1).setPreferredWidth(260);
		cols.getColumn(2).setPreferredWidth(90);
		cols.getColumn(3).setPreferredWidth(110);

		// Renderizado visual
		tabla.setDefaultRenderer(Object.class, new TableCellRenderer() {
			@Override
			public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				JLabel lbl = new JLabel(value != null ? value.toString() : "");
				lbl.setOpaque(true);
				lbl.setHorizontalAlignment(SwingConstants.CENTER);

				String id = table.getValueAt(row, 0).toString();
				Verificaciones ver = obtenerVerificacionPorId(id);

				if (isSelected) {
					lbl.setBackground(colorBoton.obtener());
					lbl.setForeground(Color.WHITE);
				} else if (ver != null && recomendadoParaCorperata(ver)) {
					lbl.setBackground(colorVerificacionCorporativa.obtener());
					lbl.setForeground(Color.WHITE);
				} else {
					lbl.setBackground(colorCajaTexto.obtener());
					lbl.setForeground(colorTexto.obtener());
				}
				return lbl;
			}
		});

		// Soporte clic en "Ver código" / "Ver documentación"
		tabla.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int fila = tabla.rowAtPoint(e.getPoint());
				int col = tabla.columnAtPoint(e.getPoint());
				if (fila < 0 || col < 0)
					return;

				if (col != 2 && col != 3)
					return;

				String id = tabla.getValueAt(fila, 0).toString();
				Verificaciones ver = obtenerVerificacionPorId(id);
				if (ver == null)
					return;

				String url = (col == 2) ? enlaceACodigo(ver) : enlaceDocs(ver);
				if (url != null && !url.isEmpty()) {
					abrirURL(url);
				}
			}
		});
	}

	/**
	 * Carga los datos en las tablas según la configuración actual
	 */
	private void cargarDatos() {
		// Obtener todas las verificaciones
		HashSet<Verificaciones> todasVerificaciones = verificaciones();
		List<String> verificacionesDeshabilitadas = configValor();

		// Limpiar modelos
		modeloHabilitadas.setRowCount(0);
		modeloDeshabilitadas.setRowCount(0);

		// Añadir verificaciones a los modelos correspondientes
		for (Verificaciones ver : todasVerificaciones) {
			Object[] rowData = new Object[] { id(ver), nombre(ver),
					enlaceACodigo(ver) != null ? MonitorDePID.idioma.verCodigo() : "",
					enlaceDocs(ver) != null ? MonitorDePID.idioma.verDocumentacion() : "" };

			if (verificacionesDeshabilitadas.contains(id(ver))) {
				modeloDeshabilitadas.addRow(rowData);
			} else {
				modeloHabilitadas.addRow(rowData);
			}
		}
	}

	/**
	 * Agrega los listeners a los componentes interactivos
	 */
	private void agregarListeners() {
		// Mover de habilitadas a deshabilitadas
		botonMoverDerecha.addActionListener(e -> {
			int filaSeleccionada = tablaHabilitadas.getSelectedRow();
			if (filaSeleccionada >= 0) {
				Object[] fila = new Object[4];
				for (int i = 0; i < 4; i++) {
					fila[i] = modeloHabilitadas.getValueAt(filaSeleccionada, i);
				}
				modeloDeshabilitadas.addRow(fila);
				modeloHabilitadas.removeRow(filaSeleccionada);

				// Actualizar configuración
				actualizarConfiguracion();
			} else {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.seleccionaVerificacionDeshabilitar(),
						MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// Mover de deshabilitadas a habilitadas
		botonMoverIzquierda.addActionListener(e -> {
			int filaSeleccionada = tablaDeshabilitadas.getSelectedRow();
			if (filaSeleccionada >= 0) {
				Object[] fila = new Object[4];
				for (int i = 0; i < 4; i++) {
					fila[i] = modeloDeshabilitadas.getValueAt(filaSeleccionada, i);
				}
				modeloHabilitadas.addRow(fila);
				modeloDeshabilitadas.removeRow(filaSeleccionada);

				// Actualizar configuración
				actualizarConfiguracion();
			} else {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.seleccionaVerificacionHabilitar(),
						MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// Deshabilitar todas no corporativas
		botonDeshabilitarNoCorporativas.addActionListener(e -> {
			int count = 0;

			// Mover todas las verificaciones no corporativas a deshabilitadas
			for (int i = modeloHabilitadas.getRowCount() - 1; i >= 0; i--) {
				String id = (String) modeloHabilitadas.getValueAt(i, 0);
				Verificaciones ver = obtenerVerificacionPorId(id);

				if (ver != null && !recomendadoParaCorperata(ver)) {
					Object[] fila = new Object[4];
					for (int j = 0; j < 4; j++) {
						fila[j] = modeloHabilitadas.getValueAt(i, j);
					}
					modeloDeshabilitadas.addRow(fila);
					modeloHabilitadas.removeRow(i);
					count++;
				}
			}

			if (count > 0) {
				JOptionPane.showMessageDialog(this,
						String.format(MonitorDePID.idioma.verificacionesNoCorporativasDeshabilitadas(), count),
						MonitorDePID.idioma.operacionCompletada(), JOptionPane.INFORMATION_MESSAGE);

				// Actualizar configuración
				actualizarConfiguracion();
			} else {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.noVerificacionesNoCorporativas(),
						MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	/**
	 * Actualiza la configuración con las verificaciones deshabilitadas
	 */
	private void actualizarConfiguracion() {
		ArrayList<String> idsDeshabilitadas = new ArrayList<>();

		for (int i = 0; i < modeloDeshabilitadas.getRowCount(); i++) {
			idsDeshabilitadas.add((String) modeloDeshabilitadas.getValueAt(i, 0));
		}

		escribirConfig(idsDeshabilitadas);
	}

	/**
	 * Obtiene una verificación por su ID
	 * 
	 * @param id ID de la verificación
	 * @return Verificación correspondiente o null si no existe
	 */
	private Verificaciones obtenerVerificacionPorId(String id) {
		for (Verificaciones ver : verificaciones()) {
			if (id(ver).equals(id)) {
				return ver;
			}
		}
		return null;
	}

	/**
	 * Aplica la apariencia a todos los componentes
	 */
	@Override
	public void recargarApariencia() {
		// Aplicar colores a todos los componentes
		getContentPane().setBackground(colorFondoVentana.obtener());
		panelPrincipal.setBackground(colorFondoVentana.obtener());

		etiquetaHabilitadas.setForeground(colorTexto.obtener());
		etiquetaDeshabilitadas.setForeground(colorTexto.obtener());
		etiquetaMensajeAmaneKanata.setForeground(colorTexto.obtener());

		// Aplicar colores a las tablas
		tablaHabilitadas.setBackground(colorCajaTexto.obtener());
		tablaHabilitadas.setForeground(colorTexto.obtener());
		tablaHabilitadas.setGridColor(colorBordePanel.obtener());

		tablaDeshabilitadas.setBackground(colorCajaTexto.obtener());
		tablaDeshabilitadas.setForeground(colorTexto.obtener());
		tablaDeshabilitadas.setGridColor(colorBordePanel.obtener());

		// Estilizar botones
		estilizarBoton(botonMoverDerecha);
		estilizarBoton(botonMoverIzquierda);
		estilizarBoton(botonDeshabilitarNoCorporativas);

		// Forzar repintado
		revalidate();
		repaint();
	}

	/**
	 * Estiliza un botón con la apariencia corporativa
	 * 
	 * @param btn Botón a estilizar
	 */
	private void estilizarBoton(JButton btn) {
		if (!CrashDetectorGUI.esMac()) {
			btn.setBackground(colorBoton.obtener());
			btn.setForeground(Color.WHITE);
			btn.setFocusPainted(false);
			btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		} else {
			btn.setContentAreaFilled(false);
		}
		btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();
		colorFondoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoVentana());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorCajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		colorBordePanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		colorVerificacionCorporativa
				.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorVerificacionCorporativa());

		elementos.add(colorFondoVentana);
		elementos.add(colorTexto);
		elementos.add(colorBoton);
		elementos.add(colorCajaTexto);
		elementos.add(colorBordePanel);
		elementos.add(colorVerificacionCorporativa);

		return elementos;
	}

}