package com.asbestosstar.crashdetector.gui.tipos.compartir;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro;
import com.asbestosstar.crashdetector.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetector.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sito_registro.LimteDeTasa;
import com.asbestosstar.crashdetector.api_sito_registro.NoAPIdeRegistro;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.canario.CanarioDeOrdenJudicialGUI1984;

/**
 * Implementación concreta del diálogo de compartir con apariencia legada.
 */
public class DialogoCompartirLegacy extends DialogoCompartir {

	public static String ID = "dialogo_compartir_legacy";

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void preperar(Instant instant) {
		setSize(900, 725);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.instant = instant;

		panelPrincipal = new JPanel(new BorderLayout(10, 10));
		panelSuperior = new JPanel(new BorderLayout(0, 10));
		panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		textoExplicacion = new javax.swing.JEditorPane("text/html", MonitorDePID.idioma.arco());
		textoExplicacion.setEditable(false);
		textoExplicacion.setOpaque(false);

		textoExplicacion.addHyperlinkListener(e -> {
			if (e.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
				try {
					java.awt.Desktop.getDesktop().browse(e.getURL().toURI());
				} catch (Exception ex) {
					CrashDetectorLogger.logException(ex);
				}
			}
		});

		JScrollPane scrollTexto = new JScrollPane(textoExplicacion);
		scrollTexto.setPreferredSize(new Dimension(10, 180));
		scrollTexto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

		panelControles = new JPanel();
		panelControles.setLayout(new javax.swing.BoxLayout(panelControles, javax.swing.BoxLayout.Y_AXIS));

		// Botón: compartir informe con reporte
		botonCompartirTodos = new JButton(MonitorDePID.idioma.botonDeCompartirInforme());
		botonCompartirTodos.addActionListener(e -> {
			setEnviando(true);
			try {
				compartirSeleccionados(e);
			} catch (Throwable t) {
				mostrarError(MonitorDePID.idioma.error_inesperado_al_compartir(), t);
			} finally {
				setEnviando(false);
			}
		});

		int h1 = botonCompartirTodos.getPreferredSize().height;
		botonCompartirTodos.setMaximumSize(new Dimension(Integer.MAX_VALUE, h1));
		botonCompartirTodos.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelControles.add(botonCompartirTodos);

		panelControles.add(javax.swing.Box.createVerticalStrut(8));

		// Botón: compartir todos los enlaces sin reporte
		botonCompartirMarkdown = new JButton(MonitorDePID.idioma.texto_de_boton_compartir_markdown());
		botonCompartirMarkdown.addActionListener(e -> {
			setEnviando(true);
			try {
				compartirSoloEnlacesMarkdown(e);
			} catch (Throwable t) {
				mostrarError(MonitorDePID.idioma.error_inesperado_al_generar_enlaces(), t);
			} finally {
				setEnviando(false);
			}
		});

		int h2 = botonCompartirMarkdown.getPreferredSize().height;
		botonCompartirMarkdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, h2));
		botonCompartirMarkdown.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelControles.add(botonCompartirMarkdown);

		panelControles.add(javax.swing.Box.createVerticalStrut(8));

		// Botón: compartir instancia/modpack
		botonCompartirInstanciaOModpack = new JButton(MonitorDePID.idioma.texto_de_boton_compartir_instancia_modpack());
		botonCompartirInstanciaOModpack.addActionListener(e -> compartirInstanciaOModpack(e));
		int h3 = botonCompartirInstanciaOModpack.getPreferredSize().height;
		botonCompartirInstanciaOModpack.setMaximumSize(new Dimension(Integer.MAX_VALUE, h3));
		botonCompartirInstanciaOModpack.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelControles.add(botonCompartirInstanciaOModpack);

		panelControles.add(javax.swing.Box.createVerticalStrut(10));

		campoEnlaceReporte = new JTextField();
		campoEnlaceReporte.setEditable(false);
		int hf = 28;
		campoEnlaceReporte.setMaximumSize(new Dimension(Integer.MAX_VALUE, hf));
		campoEnlaceReporte.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelControles.add(campoEnlaceReporte);

		panelSuperior.add(scrollTexto, BorderLayout.CENTER);
		panelSuperior.add(panelControles, BorderLayout.SOUTH);

		inicializarTabla();
		inicializarPanelConfiguracion();

		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		panelPrincipal.add(new JScrollPane(tabla), BorderLayout.CENTER);
		panelPrincipal.add(panelConfig, BorderLayout.SOUTH);

		add(panelPrincipal);

		cargarConsolas();
		recargarApariencia();
		this.setVisible(true);
	}

	private void inicializarTabla() {
		modeloTabla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 0 || column == 2 || column == 3;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 0 ? Boolean.class : String.class;
			}
		};
		modeloTabla.addColumn(MonitorDePID.idioma.incluir());
		modeloTabla.addColumn(MonitorDePID.idioma.archivo());
		modeloTabla.addColumn(MonitorDePID.idioma.abrir());
		modeloTabla.addColumn(MonitorDePID.idioma.texto_de_boton_compartir_enlace());
		modeloTabla.addColumn(MonitorDePID.idioma.columna_url());

		tabla = new JTable(modeloTabla);
		tabla.setRowHeight(30);
		tabla.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());
		tabla.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
		tabla.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer(MonitorDePID.idioma.abrir()));
		tabla.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(MonitorDePID.idioma.abrir()));
		tabla.getColumnModel().getColumn(3)
				.setCellRenderer(new ButtonRenderer(MonitorDePID.idioma.texto_de_boton_compartir_enlace()));
		tabla.getColumnModel().getColumn(3)
				.setCellEditor(new ButtonEditor(MonitorDePID.idioma.texto_de_boton_compartir_enlace()));
		tabla.getColumnModel().getColumn(4).setCellRenderer(new URLEditorRenderer());
		tabla.getColumnModel().getColumn(4).setCellEditor(new URLEditor());
	}



	
	private void inicializarPanelConfiguracion() {
		panelConfig = new JPanel(new GridBagLayout());
		panelConfig.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.titulo_configuracion()));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		int y = 0;

		// Endpoint del informe
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.weightx = 0.0;
		gbc.gridwidth = 1;
		panelConfig.add(new JLabel(MonitorDePID.idioma.endpointDeInforme()), gbc);

		gbc.gridx = 1;
		gbc.gridy = y;
		gbc.weightx = 3.0;
		campoEndpoint = new JTextField(Config.obtenerInstancia().obtenerSitoDeInformes(), 50);
		campoEndpoint.setMinimumSize(new Dimension(400, 25));
		panelConfig.add(campoEndpoint, gbc);

		y++;

		// Botón opcional para eliminar registros de la API seleccionada
		gbc.gridx = 1;
		gbc.gridy = y;
		gbc.weightx = 3.0;
		gbc.gridwidth = 1;

		botonEliminarRegistros = new JButton(MonitorDePID.idioma.eliminarRegistros());
		botonEliminarRegistros.addActionListener(e -> abrirEliminadorDeApiSeleccionada());
		panelConfig.add(botonEliminarRegistros, gbc);

		y++;

		// API de logging
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.weightx = 0.0;
		gbc.gridwidth = 1;
		panelConfig.add(new JLabel(MonitorDePID.idioma.apiDeLogging()), gbc);

		gbc.gridx = 1;
		gbc.gridy = y;
		gbc.weightx = 3.0;

		String sito_actual = Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado();
		APIdeSitioDeRegistro api_def;
		boolean error_de_api;

		try {
			api_def = obtenerAPI();
			error_de_api = false;
		} catch (NoAPIdeRegistro e) {
			mostrarError(MonitorDePID.idioma.apiDeRegistroNoExiste(), e);
			api_def = Consola.secure_logger_api;
			sito_actual = "https://securelogger.net/save/log?";
			error_de_api = true;
		}

		Map<String, Set<String>> apis = new HashMap<>();
		for (APIdeSitioDeRegistro api : APIdeSitioDeRegistro.APIS) {
			Set<String> sits = new LinkedHashSet<>();

			if (api != null && api.equals(api_def) && !error_de_api && sito_actual != null) {
				sits.add(sito_actual);
			}

			if (api != null) {
				sits.addAll(api.sitiosPorDefecto());
				apis.put(api.nombre(), sits);
			}
		}

		comboAPI = new JComboBox<>(apis.keySet().toArray(new String[0]));
		comboAPI.setSelectedItem(api_def.nombre());
		comboAPI.setPreferredSize(new Dimension(300, 25));
		panelConfig.add(comboAPI, gbc);

		y++;

		// Sitio de logging
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.weightx = 0.0;
		panelConfig.add(new JLabel(MonitorDePID.idioma.sitoDeLogging()), gbc);

		gbc.gridx = 1;
		gbc.gridy = y;
		gbc.weightx = 3.0;

		comboSitioRegistro = new JComboBox<>(new String[] {});
		comboSitioRegistro.setPreferredSize(new Dimension(300, 25));
		panelConfig.add(comboSitioRegistro, gbc);

		actualizarComboSitios(api_def.nombre(), apis.get(api_def.nombre()), sito_actual);

		comboAPI.addActionListener(e -> {
			String apiSeleccionada = (String) comboAPI.getSelectedItem();
			Set<String> sitios = apis.get(apiSeleccionada);

			actualizarComboSitios(apiSeleccionada, sitios, null);
			actualizarBotonEliminador();
		});

		y++;

		// Anonimizar registros
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		gbc.weightx = 1.0;

		checkAnonimizar = new JCheckBox(MonitorDePID.idioma.anonimizarRegistros());
		checkAnonimizar.setSelected(ConfigMundial.obtenerInstancia().esAnonimizarRegistros());
		panelConfig.add(checkAnonimizar, gbc);

		y++;

		// Guardar configuración
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;

		JButton boton_guardar_de_config = new JButton(MonitorDePID.idioma.guardarConfigDeCompartir());
		boton_guardar_de_config.addActionListener(e -> guardarConfig());
		panelConfig.add(boton_guardar_de_config, gbc);

		y++;

		// Canario
		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;

		JButton botonCanario = new JButton(MonitorDePID.idioma.buscador_canario_de_orden_label());
		botonCanario.addActionListener(e -> {
			TipoGUI.CANARIO.obtenerGUIPredeterminado(CanarioDeOrdenJudicialGUI1984.ID,
					() -> new CanarioDeOrdenJudicialGUI1984()).init();
		});
		panelConfig.add(botonCanario, gbc);

		actualizarBotonEliminador();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private static class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
		public CheckBoxRenderer() {
			setHorizontalAlignment(SwingConstants.CENTER);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setSelected((value != null && (Boolean) value));
			return this;
		}
	}

	private static class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer(String texto) {
			setText(texto);
			setMargin(new java.awt.Insets(2, 5, 2, 5));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return this;
		}
	}

	private class ButtonEditor extends DefaultCellEditor {
		private final String accion;
		private final JButton button;
		private int currentRow = -1;

		public ButtonEditor(String accion) {
			super(new JCheckBox());
			this.accion = accion;
			this.button = new JButton(accion);

			button.addActionListener(e -> {
				try {
					button.setEnabled(false);
					setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
					if (currentRow < 0)
						return;

					Consola cons = MonitorDePID.consolas.get(currentRow);

					if (accion.equals(MonitorDePID.idioma.abrir())) {
						if (cons == null || cons.archivo == null) {
							mostrarError(MonitorDePID.idioma.sin_archivo_para_abrir(), null);
							return;
						}
						java.io.File f = cons.archivo.toFile();
						if (!f.exists()) {
							mostrarError(MonitorDePID.idioma.archivo_no_existe_prefijo() + f.getAbsolutePath(), null);
							return;
						}

						if (java.awt.Desktop.isDesktopSupported()) {
							java.awt.Desktop d = java.awt.Desktop.getDesktop();
							try {
								if (d.isSupported(java.awt.Desktop.Action.EDIT)) {
									d.edit(f);
								} else if (d.isSupported(java.awt.Desktop.Action.OPEN)) {
									d.open(f);
								} else {
									java.io.File parent = f.getParentFile();
									if (parent != null && parent.exists()
											&& d.isSupported(java.awt.Desktop.Action.OPEN)) {
										d.open(parent);
									} else {
										copiarAlPortapapeles(f.getAbsolutePath());
										mostrarInfo(MonitorDePID.idioma.no_se_pudo_abrir_se_copia_ruta());
									}
								}
							} catch (Exception ex) {
								CrashDetectorLogger.logException(ex);
								mostrarError(MonitorDePID.idioma.no_se_pudo_editar_se_copia_ruta(), ex);
								copiarAlPortapapeles(f.getAbsolutePath());
							}
						} else {
							copiarAlPortapapeles(f.getAbsolutePath());
							mostrarInfo(MonitorDePID.idioma.escritorio_no_soportado_se_copia_ruta());
						}
						return;
					}

					if (accion.equals(MonitorDePID.idioma.texto_de_boton_compartir_enlace())) {
						java.util.List<String> urls = cons.obtainerEnlaces();
						String celda = (urls == null || urls.isEmpty()) ? "" : String.join("\n", urls);
						modeloTabla.setValueAt(celda, currentRow, 4);

						if (campoEnlaceReporte != null) {
							campoEnlaceReporte.setText(celda);
						}
						if (celda != null && !celda.isEmpty()) {
							copiarAlPortapapeles(celda);
							mostrarInfo(MonitorDePID.idioma.copiadoAlPortapapeles());
						}
						return;
					}

				} catch (DemasiadoGrande ex) {
					mostrarError(MonitorDePID.idioma.registroDemasiadoGrande(), ex);
				} catch (ErrorConPublicar ex) {
					mostrarError(MonitorDePID.idioma.errorConPublicarRegistro(ex.problema), ex);
				} catch (NoAPIdeRegistro ex) {
					mostrarError(MonitorDePID.idioma.apiDeRegistroNoExiste(), ex);
				} catch (LimteDeTasa ex) {
					mostrarError(MonitorDePID.idioma.limite_de_solicitudes(), ex);
				} catch (Exception ex) {
					CrashDetectorLogger.logException(ex);
					mostrarError(MonitorDePID.idioma.error_inesperado_al_procesar_boton(), ex);
				} finally {
					try {
						fireEditingStopped();
					} catch (Throwable ignored) {
					}
					button.setEnabled(true);
					setCursor(java.awt.Cursor.getDefaultCursor());
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.currentRow = row;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			return accion;
		}
	}

	private static class URLEditor extends DefaultCellEditor {
		private final JTextField textField;

		public URLEditor() {
			super(new JTextField());
			textField = (JTextField) getComponent();
			textField.setEditable(false);
			textField.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						copiarAlPortapapeles(textField.getText());
					}
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			textField.setText(value == null ? "" : value.toString());
			return textField;
		}

		@Override
		public Object getCellEditorValue() {
			return textField.getText();
		}
	}

	private static class URLEditorRenderer extends JLabel implements TableCellRenderer {
		public URLEditorRenderer() {
			setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.TEXT_CURSOR));
			setForeground(ConfigColor.de("dialogo_compartir_enlace", Color.BLUE.darker()).obtener());
			addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						copiarAlPortapapeles(getText());
					}
				}
			});
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value == null ? "" : value.toString());
			return this;
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		ArrayList<ElementoConfig> ret = new ArrayList<>();

		ConfigColor colorEnlace = ConfigColor.de("dialogo_compartir_enlace", java.awt.Color.BLUE.darker());
		colorEnlace.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorEnlaceCompartir());
		ret.add(colorEnlace);

		ConfigColor colorFondoCampo = ConfigColor.de("dialogo_compartir_campo_fondo", Color.YELLOW);
		colorFondoCampo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoCampoCompartir());
		ret.add(colorFondoCampo);

		// Verde oscuro para "compartir informe" y "compartir instancia/modpack"
		ConfigColor colorBotonVerdeOscuro = ConfigColor.de("dialogo_compartir_boton_verde_oscuro",
				new Color(0, 100, 0));
		colorBotonVerdeOscuro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonCompartirVerdeOscuro());
		ret.add(colorBotonVerdeOscuro);

		// Verde más claro para "compartir todos los enlaces sin reporte"
		ConfigColor colorBotonVerdeClaro = ConfigColor.de("dialogo_compartir_boton_verde_claro",
				new Color(102, 170, 102));
		colorBotonVerdeClaro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonCompartirVerdeClaro());
		ret.add(colorBotonVerdeClaro);

		// Color de texto para los botones verdes
		ConfigColor colorTextoBotonesCompartir = ConfigColor.de("dialogo_compartir_boton_texto", Color.WHITE);
		colorTextoBotonesCompartir.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoBotonesCompartir());
		ret.add(colorTextoBotonesCompartir);

		return ret;
	}

	@Override
	public void recargarApariencia() {
		if (campoEnlaceReporte != null) {
			campoEnlaceReporte.setBackground(ConfigColor.de("dialogo_compartir_campo_fondo", Color.YELLOW).obtener());
		}

		// En macOS no se fuerzan los colores para respetar mejor el estilo nativo.
		if (!esMacOS()) {
			Color verdeOscuro = ConfigColor.de("dialogo_compartir_boton_verde_oscuro", new Color(0, 100, 0)).obtener();
			Color verdeClaro = ConfigColor.de("dialogo_compartir_boton_verde_claro", new Color(102, 170, 102))
					.obtener();
			Color colorTexto = ConfigColor.de("dialogo_compartir_boton_texto", Color.WHITE).obtener();

			if (botonCompartirTodos != null) {
				botonCompartirTodos.setOpaque(true);
				botonCompartirTodos.setContentAreaFilled(true);
				botonCompartirTodos.setBackground(verdeOscuro);
				botonCompartirTodos.setForeground(colorTexto);
			}

			if (botonCompartirMarkdown != null) {
				botonCompartirMarkdown.setOpaque(true);
				botonCompartirMarkdown.setContentAreaFilled(true);
				botonCompartirMarkdown.setBackground(verdeClaro);
				botonCompartirMarkdown.setForeground(colorTexto);
			}

			if (botonCompartirInstanciaOModpack != null) {
				botonCompartirInstanciaOModpack.setOpaque(true);
				botonCompartirInstanciaOModpack.setContentAreaFilled(true);
				botonCompartirInstanciaOModpack.setBackground(verdeOscuro);
				botonCompartirInstanciaOModpack.setForeground(colorTexto);
			}
		}
	}
}