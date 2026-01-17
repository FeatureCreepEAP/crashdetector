package com.asbestosstar.crashdetector.gui.tipos.lanzeresmalos;

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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.detectorlanzer.DetectorLanzer;

public class LanzerMaloGUISylentBell extends LanzerMaloGUI {
	private static final long serialVersionUID = 1L;

	public static String ID = "sylent_bell";
	public static File imagen = Statics.carpeta.resolve("imagenes/sylent_bell.png").toFile(); // 137 × 207 píxeles

	// Componentes de la interfaz
	private JTable tablaNoRecomendados;
	private DefaultTableModel modeloNoRecomendados;
	private JButton botonAgregar;
	private JButton botonQuitar;
	private JButton botonEditarRazones;
	private JButton botonGuardar;
	private JButton botonCancelar;
	private JLabel imagenLabel;
	private JPanel panelPrincipal;
	private JLabel etiquetaTitulo;
	private JLabel etiquetaDisclaimer;
	private JComboBox<String> comboLanzadores;

	// Configuraciones de color
	private ConfigColor colorFondoVentana;
	private ConfigColor colorTexto;
	private ConfigColor colorBoton;
	private ConfigColor colorTabla;
	private ConfigColor colorResaltoDesaconsejado;
	private ConfigColor colorBordePanel;

	@Override
	public void init() {
		// Inicializar configuraciones de color
		// Inicializar configuraciones de color (paleta inspirada en la imagen de Sylent
		// Bell)
		colorFondoVentana = ConfigColor.de("tema.sylent_bell.color.fondo.ventana", new Color(15, 18, 28)); // azul noche
																											// casi
																											// negro
		colorTexto = ConfigColor.de("tema.sylent_bell.color.texto", new Color(236, 232, 235)); // blanco frío
																								// (cabello/luz)
		colorBoton = ConfigColor.de("tema.sylent_bell.color.boton", new Color(61, 72, 93)); // azul grafito
																							// (ropa/sombras)
		colorTabla = ConfigColor.de("tema.sylent_bell.color.tabla", new Color(23, 24, 36)); // fondo más profundo para
																							// listas
		colorResaltoDesaconsejado = ConfigColor.de("tema.sylent_bell.color.resalto.desaconsejado",
				new Color(255, 92, 120)); // rosado/rojo (acento)
		colorBordePanel = ConfigColor.de("tema.sylent_bell.color.borde.panel", new Color(108, 117, 142)); // gris
																											// azulado
																											// (contornos)

		setTitle(MonitorDePID.idioma.lanzadoresNoRecomendados());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(true);
		setSize(900, 650);
		setAlwaysOnTop(false);

		// Cargar datos iniciales
		cargarDatos();

		// Crear modelo de tabla
		modeloNoRecomendados = new DefaultTableModel(
				new Object[] { MonitorDePID.idioma.nombreLanzador(), MonitorDePID.idioma.razones() }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // ninguna celda editable
			}
		};

		// Crear tabla
		tablaNoRecomendados = new JTable(modeloNoRecomendados);

		// Configurar tabla
		configurarTabla(tablaNoRecomendados);

		// Panel principal
		panelPrincipal = new JPanel(new GridBagLayout());
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelPrincipal.setOpaque(true);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new java.awt.Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.BOTH;

		// Panel izquierdo para la imagen y el mensaje
		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setPreferredSize(new Dimension(200, 0));
		panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

		// Imagen
		imagenLabel = new JLabel();
		imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imagenLabel.setVerticalAlignment(SwingConstants.TOP);
		cargarImagen();

		// Mensaje debajo de la imagen
		JLabel avisoSylentBell = crearEtiquetaDisclaimerSylentBellLadoIzquierdo();

		// Añadir componentes (IMPORTANTE: NORTH + SOUTH, no CENTER)
		panelIzquierdo.add(imagenLabel, BorderLayout.NORTH);
		panelIzquierdo.add(avisoSylentBell, BorderLayout.SOUTH);

		// Aplicar fondo correcto (evita blanco)
		aplicarFondoPanelIzquierdo(panelIzquierdo);

		// Añadir al panel principal
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 7;
		gbc.weightx = 0.25;
		gbc.weighty = 1.0;
		panelPrincipal.add(panelIzquierdo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 7; // hay una fila extra por el disclaimer
		gbc.weightx = 0.25;
		gbc.weighty = 1.0;
		panelPrincipal.add(panelIzquierdo, gbc);

		// Título
		etiquetaTitulo = new JLabel(MonitorDePID.idioma.lanzadoresNoRecomendados());
		etiquetaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));

		// Disclaimer (texto fijo; idealmente iría a Idioma, pero se pidió aquí)
		etiquetaDisclaimer = crearEtiquetaDisclaimerSylentBell();

		// Combo para seleccionar lanzador a agregar
		comboLanzadores = new JComboBox<>();
		comboLanzadores.setPreferredSize(new Dimension(250, 30));
		configurarComboLanzadores();

		// Panel de botones de acción
		JPanel panelBotonesAccion = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		botonAgregar = new JButton(MonitorDePID.idioma.agregarLanzador());
		botonQuitar = new JButton(MonitorDePID.idioma.quitarLanzador());
		botonEditarRazones = new JButton(MonitorDePID.idioma.editarRazones());
		panelBotonesAccion.add(botonAgregar);
		panelBotonesAccion.add(botonQuitar);
		panelBotonesAccion.add(botonEditarRazones);

		// Cargar lanzadores en el combo (ya existe botonAgregar)
		cargarComboLanzadores();

		// Scroll pane para la tabla
		JScrollPane scrollNoRecomendados = new JScrollPane(tablaNoRecomendados);

		// Panel de botones de guardado
		JPanel panelBotonesGuardado = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		botonGuardar = new JButton(MonitorDePID.idioma.guardarCambios());
		botonCancelar = new JButton(MonitorDePID.idioma.cancelar());
		panelBotonesGuardado.add(botonGuardar);
		panelBotonesGuardado.add(botonCancelar);

		// Añadir componentes al panel principal (lado derecho)
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		gbc.weightx = 0.75;
		gbc.weighty = 0.05;
		panelPrincipal.add(etiquetaTitulo, gbc);

		gbc.gridy = 1;
		gbc.weighty = 0.06;
		panelPrincipal.add(etiquetaDisclaimer, gbc);

		gbc.gridy = 2;
		gbc.weighty = 0.08;
		panelPrincipal.add(comboLanzadores, gbc);

		gbc.gridy = 3;
		gbc.weighty = 0.08;
		panelPrincipal.add(panelBotonesAccion, gbc);

		gbc.gridy = 4;
		gbc.weighty = 0.64;
		panelPrincipal.add(scrollNoRecomendados, gbc);

		gbc.gridy = 5;
		gbc.weighty = 0.1;
		panelPrincipal.add(panelBotonesGuardado, gbc);

		// Añadir panel principal al contenido
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);

		// Cargar datos en la tabla
		cargarDatosEnTabla();

		// Agregar listeners
		agregarListeners();

		// Centrar la ventana
		setLocationRelativeTo(null);

		// Aplicar apariencia
		recargarApariencia();

		setVisible(true);
	}

	/**
	 * Convierte nombres (los que se muestran en UI) a codigos ISO usados
	 * internamente. Este mapeo debe coincidir con los codigos que se guardan como
	 * claves en el JSON.
	 */
	public static String obtenerCodigoIdioma(String nombreIdioma) {
		switch (nombreIdioma) {
		case "Español":
			return "es";
		case "English":
			return "en";
		case "العربية":
			return "ar";
		case "Português":
			return "pt";
		case "فارسی":
			return "fa";
		case "Русский":
			return "ru";
		case "简体中文":
			return "zh";
		case "Esperanto":
			return "eo";
		case "日本語":
			return "ja";
		case "한국어":
			return "ko";
		default:
			return "es";
		}
	}

	/**
	 * Carga la imagen de Sylent Bell.
	 */
	private void cargarImagen() {
		try {
			if (imagen.exists()) {
				ImageIcon icon = new ImageIcon(imagen.getAbsolutePath());
				Image img = icon.getImage();

				int newWidth = 180;
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
	 * Crea la etiqueta de aviso sobre Sylent Bell.
	 */
	private JLabel crearEtiquetaDisclaimerSylentBell() {
		JLabel lbl = new JLabel("<html><div style='width:520px;'>"
				+ "Las opiniones y comentarios de Sylent Bell no necesariamente coinciden con los nuestros; "
				+ "solo pensamos que sería gracioso ponerla aquí." + "</div></html>");
		lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lbl.setForeground(colorTexto.obtener());
		lbl.setOpaque(false);
		return lbl;
	}

	/**
	 * Configura la tabla para mostrar los datos correctamente.
	 */
	private void configurarTabla(JTable tabla) {
		tabla.setFillsViewportHeight(true);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setRowHeight(25);
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));

		tabla.getColumnModel().getColumn(0).setPreferredWidth(200);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(400);

		tabla.setDefaultRenderer(Object.class, new TableCellRenderer() {
			@Override
			public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				JLabel label = new JLabel(value != null ? value.toString() : "");
				label.setOpaque(true);
				label.setHorizontalAlignment(SwingConstants.LEFT);
				label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

				if (isSelected) {
					label.setBackground(colorBoton.obtener());
					label.setForeground(Color.WHITE);
				} else {
					label.setBackground(colorTabla.obtener());
					label.setForeground(colorTexto.obtener());
				}
				return label;
			}
		});
	}

	/**
	 * Configura el combo para que los lanzadores desaconsejados por CrashDetector
	 * se muestren en rojo (usando ConfigColor, sin colores hardcodeados).
	 */
	private void configurarComboLanzadores() {
		comboLanzadores.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			String texto = (value == null) ? "" : value.toString();
			JLabel label = new JLabel(texto);
			label.setOpaque(true);
			label.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
			label.setFont(new Font("Segoe UI", Font.PLAIN, 13));

			Color fondo = isSelected ? colorBoton.obtener() : colorTabla.obtener();
			label.setBackground(fondo);

			boolean desaconsejado = false;
			if (value != null) {
				desaconsejado = esDesaconsejadoPorCrashDetectorConNosotrosDice(value.toString());
			}

			label.setForeground(desaconsejado ? colorResaltoDesaconsejado.obtener() : colorTexto.obtener());
			return label;
		});
	}

	/**
	 * Implementación explícita usando LanzerMaloGUI.nosotrosDiceEsMalo(lanzer).
	 */
	private boolean esDesaconsejadoPorCrashDetectorConNosotrosDice(String id) {
		if (id == null)
			return false;

		for (DetectorLanzer lanzer : lanzadores) {
			if (lanzer != null && id.equals(lanzer.id())) {
				return LanzerMaloGUI.nosotrosDiceEsMalo(lanzer);
			}
		}
		return false;
	}

	/**
	 * Carga los lanzadores disponibles en el combo. - Los lanzadores desaconsejados
	 * por CrashDetector aparecen PRIMERO. - Los lanzadores ya presentes en
	 * "noRecomendados" no se muestran.
	 */
	private void cargarComboLanzadores() {
		comboLanzadores.removeAllItems();

		// Listas separadas para ordenar correctamente
		List<String> desaconsejados = new ArrayList<>();
		List<String> normales = new ArrayList<>();

		// Obtener todos los lanzadores detectados
		for (String id : obtenerNombresLanzadores()) {

			// Si ya está en la lista de no recomendados, no se muestra en el combo
			if (noRecomendados.containsKey(id)) {
				continue;
			}

			// Clasificar según CrashDetector
			if (esDesaconsejadoPorCrashDetectorConNosotrosDice(id)) {
				desaconsejados.add(id);
			} else {
				normales.add(id);
			}
		}

		// Añadir primero los desaconsejados (rojos)
		for (String id : desaconsejados) {
			comboLanzadores.addItem(id);
		}

		// Añadir después los normales
		for (String id : normales) {
			comboLanzadores.addItem(id);
		}

		// Habilitar / deshabilitar botón agregar según contenido
		if (botonAgregar != null) {
			botonAgregar.setEnabled(comboLanzadores.getItemCount() > 0);
		}
	}

	/**
	 * Carga los datos en la tabla.
	 */
	private void cargarDatosEnTabla() {
		modeloNoRecomendados.setRowCount(0);

		for (Map.Entry<String, Map<String, String>> entry : noRecomendados.entrySet()) {
			String id = entry.getKey();
			Map<String, String> motivos = entry.getValue();

			StringBuilder razones = new StringBuilder();
			boolean primero = true;
			for (Map.Entry<String, String> motivoEntry : motivos.entrySet()) {
				if (!primero)
					razones.append(", ");
				razones.append(motivoEntry.getKey()).append(": ").append(motivoEntry.getValue());
				primero = false;
			}

			modeloNoRecomendados.addRow(new Object[] { id, razones.toString() });
		}

		botonQuitar.setEnabled(modeloNoRecomendados.getRowCount() > 0);
		botonEditarRazones.setEnabled(modeloNoRecomendados.getRowCount() > 0);
	}

	/**
	 * Agrega los listeners a los componentes interactivos.
	 */
	private void agregarListeners() {
		botonAgregar.addActionListener(e -> {
			String id = (String) comboLanzadores.getSelectedItem();
			if (id != null && !id.isEmpty()) {
				Map<String, String> motivos = new HashMap<>();
				agregarANoRecomendados(id, motivos);
				cargarComboLanzadores();
				cargarDatosEnTabla();
			}
		});

		botonQuitar.addActionListener(e -> {
			int filaSeleccionada = tablaNoRecomendados.getSelectedRow();
			if (filaSeleccionada >= 0) {
				String id = (String) modeloNoRecomendados.getValueAt(filaSeleccionada, 0);
				quitarDeNoRecomendados(id);
				cargarComboLanzadores();
				cargarDatosEnTabla();
			} else {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.seleccionaLanzadorQuitar(),
						MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
			}
		});

		botonEditarRazones.addActionListener(e -> {
			int filaSeleccionada = tablaNoRecomendados.getSelectedRow();
			if (filaSeleccionada >= 0) {
				String id = (String) modeloNoRecomendados.getValueAt(filaSeleccionada, 0);
				editarRazonesParaLanzador(id);
			} else {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.seleccionaLanzadorEditar(),
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

	/**
	 * Edita las razones para un lanzador específico. - Muestra TODOS los idiomas
	 * (los del mapaParaComboBoxIdiomas()). - No hay botón de "agregar idioma". - Se
	 * muestran banderas junto al CÓDIGO (es/en/ar/...) y se guardan usando esas
	 * claves.
	 */
	private void editarRazonesParaLanzador(String idLanzador) {
		JDialog dialogo = new JDialog(this, MonitorDePID.idioma.editarRazonesPara(idLanzador), true);
		dialogo.setLayout(new BorderLayout(10, 10));
		dialogo.setSize(650, 420);

		JPanel panelCampos = new JPanel(new GridBagLayout());
		panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelCampos.setOpaque(true);
		panelCampos.setBackground(colorFondoVentana.obtener());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new java.awt.Insets(6, 6, 6, 6);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		Map<String, String> motivos = noRecomendados.getOrDefault(idLanzador, new HashMap<>());

		// IMPORTANTE: este método pertenece a CrashDetectorGUI (esta clase lo hereda)
		LinkedHashMap<String, String> mapaIdiomas = mapaParaComboBoxIdiomas();

		Map<String, JTextField> camposTexto = new HashMap<>();

		int fila = 0;

		for (Map.Entry<String, String> entry : mapaIdiomas.entrySet()) {
			String nombreVisible = entry.getKey();
			String rutaBandera = entry.getValue();

			String codigo = obtenerCodigoIdioma(nombreVisible);

			JLabel etiqueta = new JLabel(codigo);
			etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 12));
			etiqueta.setForeground(colorTexto.obtener());
			etiqueta.setOpaque(false);

			try {
				File f = Statics.carpeta.resolve(rutaBandera).toFile();
				if (f.exists()) {
					ImageIcon icon = new ImageIcon(f.getAbsolutePath());
					Image img = icon.getImage().getScaledInstance(18, 12, Image.SCALE_SMOOTH);
					etiqueta.setIcon(new ImageIcon(img));
					etiqueta.setIconTextGap(8);
				}
			} catch (Exception ignored) {
				// Si falla, se muestra solo el código
			}

			gbc.gridx = 0;
			gbc.gridy = fila;
			gbc.weightx = 0.25;
			panelCampos.add(etiqueta, gbc);

			gbc.gridx = 1;
			gbc.weightx = 0.75;
			JTextField campo = new JTextField(36);
			campo.setText(motivos.getOrDefault(codigo, ""));
			campo.setBackground(colorTabla.obtener());
			campo.setForeground(colorTexto.obtener());
			campo.setCaretColor(colorTexto.obtener());
			campo.setBorder(BorderFactory.createLineBorder(colorBordePanel.obtener(), 1));

			camposTexto.put(codigo, campo);
			panelCampos.add(campo, gbc);

			fila++;
		}

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBotones.setOpaque(true);
		panelBotones.setBackground(colorFondoVentana.obtener());

		JButton botonAceptar = new JButton(MonitorDePID.idioma.aceptar());
		JButton botonCancelar = new JButton(MonitorDePID.idioma.cancelar());
		estilizarBoton(botonAceptar);
		estilizarBoton(botonCancelar);
		panelBotones.add(botonAceptar);
		panelBotones.add(botonCancelar);

		botonAceptar.addActionListener(e -> {
			Map<String, String> nuevosMotivos = new HashMap<>();
			for (Map.Entry<String, JTextField> c : camposTexto.entrySet()) {
				String valor = c.getValue().getText();
				if (valor != null)
					valor = valor.trim();
				if (valor != null && !valor.isEmpty()) {
					nuevosMotivos.put(c.getKey(), valor);
				}
			}

			noRecomendados.put(idLanzador, nuevosMotivos);
			cargarDatosEnTabla();
			dialogo.dispose();
		});

		botonCancelar.addActionListener(e -> dialogo.dispose());

		dialogo.add(new JScrollPane(panelCampos), BorderLayout.CENTER);
		dialogo.add(panelBotones, BorderLayout.SOUTH);
		dialogo.setLocationRelativeTo(this);
		dialogo.setVisible(true);
	}

	@Override
	public void recargarApariencia() {
		getContentPane().setBackground(colorFondoVentana.obtener());
		panelPrincipal.setBackground(colorFondoVentana.obtener());

		etiquetaTitulo.setForeground(colorTexto.obtener());
		if (etiquetaDisclaimer != null) {
			etiquetaDisclaimer.setForeground(colorTexto.obtener());
		}

		// Asegurar que el lado izquierdo no se vea blanco
		if (imagenLabel != null) {
			imagenLabel.setBackground(colorFondoVentana.obtener());
		}

		tablaNoRecomendados.setBackground(colorTabla.obtener());
		tablaNoRecomendados.setForeground(colorTexto.obtener());
		tablaNoRecomendados.setGridColor(colorBordePanel.obtener());

		estilizarBoton(botonAgregar);
		estilizarBoton(botonQuitar);
		estilizarBoton(botonEditarRazones);
		estilizarBoton(botonGuardar);
		estilizarBoton(botonCancelar);

		estilizarCombo(comboLanzadores);

		// Paneles internos (para evitar “blancos” por Look&Feel)
		for (java.awt.Component c : panelPrincipal.getComponents()) {
			if (c instanceof javax.swing.JComponent) {
				c.setBackground(colorFondoVentana.obtener());
			}
		}

		revalidate();
		repaint();
	}

	/**
	 * Estiliza un botón con la apariencia corporativa.
	 */
	private void estilizarBoton(JButton btn) {
		btn.setBackground(colorBoton.obtener());
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createLineBorder(colorBordePanel.obtener(), 1));
		btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
	}

	/**
	 * Estiliza un combo box.
	 */
	private void estilizarCombo(JComboBox<?> combo) {
		combo.setBackground(colorTabla.obtener());
		combo.setForeground(colorTexto.obtener());
		combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
	}

	@Override
	public String id() {
		return ID;
	}

	/**
	 * Crea la etiqueta de aviso para mostrar DEBAJO de la imagen de Sylent Bell. El
	 * ancho es reducido para ajustarse al panel izquierdo.
	 */
	private JLabel crearEtiquetaDisclaimerSylentBellLadoIzquierdo() {
		JLabel lbl = new JLabel("<html><div style='width:180px; text-align:center;'>"
				+ "Las opiniones y comentarios de Sylent Bell no necesariamente coinciden con los nuestros; "
				+ "solo pensamos que sería gracioso ponerla aquí." + "</div></html>");
		lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		lbl.setForeground(colorTexto.obtener());
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setOpaque(false);
		return lbl;
	}

	/**
	 * Aplica el color de fondo corporativo al panel izquierdo y a la imagen para
	 * evitar espacios blancos por el Look&Feel.
	 */
	private void aplicarFondoPanelIzquierdo(JPanel panelIzquierdo) {
		if (panelIzquierdo != null) {
			panelIzquierdo.setOpaque(true);
			panelIzquierdo.setBackground(colorFondoVentana.obtener());
		}
		if (imagenLabel != null) {
			imagenLabel.setOpaque(true);
			imagenLabel.setBackground(colorFondoVentana.obtener());
		}
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

		colorResaltoDesaconsejado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorResultadoEliminado());
		ret.add(colorResaltoDesaconsejado);

		colorBordePanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		ret.add(colorBordePanel);

		return ret;
	}

}
