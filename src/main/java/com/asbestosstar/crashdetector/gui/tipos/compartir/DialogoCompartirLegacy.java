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

/**
 * Implementación concreta del diálogo de compartir con apariencia legada. Esta
 * clase maneja específicamente el layout y la apariencia del diálogo.
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

		// Panel principal y superior
		panelPrincipal = new JPanel(new BorderLayout(10, 10));
		panelSuperior = new JPanel(new BorderLayout(0, 10));
		panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Texto de explicación
		textoExplicacion = new JTextArea(MonitorDePID.idioma.arco());
		textoExplicacion.setEditable(false);
		textoExplicacion.setLineWrap(true);
		textoExplicacion.setWrapStyleWord(true);
		textoExplicacion.setBackground(panelSuperior.getBackground());

		// Panel de controles (botones + campo de enlace)
		panelControles = new JPanel();
		panelControles.setLayout(new javax.swing.BoxLayout(panelControles, javax.swing.BoxLayout.Y_AXIS));

		// ---------- Botón: Compartir informe ----------
		botonCompartirTodos = new JButton(MonitorDePID.idioma.botonDeCompartirInforme());
		botonCompartirTodos.addActionListener(e -> {
			setEnviando(true);
			try {
				compartirSeleccionados(e);
			} catch (DemasiadoGrande e0) {
				mostrarError(MonitorDePID.idioma.registroDemasiadoGrande(), e0);
			} catch (ErrorConPublicar e1) {
				mostrarError(MonitorDePID.idioma.errorConPublicarRegistro(e1.problema), e1);
			} catch (NoAPIdeRegistro e2) {
				mostrarError(MonitorDePID.idioma.apiDeRegistroNoExiste(), e2);
			} catch (Throwable t) {
				// Mantener el comentario existente, solo se reemplaza el literal
				mostrarError(MonitorDePID.idioma.error_inesperado_al_compartir(), t);
			} finally {
				setEnviando(false);
			}
		});

		// Ajustar tamaño para ocupar todo el ancho
		int h1 = botonCompartirTodos.getPreferredSize().height;
		botonCompartirTodos.setMaximumSize(new Dimension(Integer.MAX_VALUE, h1));
		botonCompartirTodos.setMinimumSize(new Dimension(0, h1));
		botonCompartirTodos.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelControles.add(botonCompartirTodos);

		panelControles.add(javax.swing.Box.createVerticalStrut(8));

		// ---------- Botón: Obtener enlaces Markdown ----------
		botonCompartirMarkdown = new JButton(MonitorDePID.idioma.texto_de_boton_compartir_markdown());
		botonCompartirMarkdown.addActionListener(e -> {
			setEnviando(true);
			try {
				compartirSoloEnlacesMarkdown(e);
			} catch (DemasiadoGrande e0) {
				mostrarError(MonitorDePID.idioma.registroDemasiadoGrande(), e0);
			} catch (ErrorConPublicar e1) {
				mostrarError(MonitorDePID.idioma.errorConPublicarRegistro(e1.problema), e1);
			} catch (NoAPIdeRegistro e2) {
				mostrarError(MonitorDePID.idioma.apiDeRegistroNoExiste(), e2);
			} catch (Throwable t) {
				// Mantener el comentario existente, solo se reemplaza el literal
				mostrarError(MonitorDePID.idioma.error_inesperado_al_generar_enlaces(), t);
			} finally {
				setEnviando(false);
			}
		});

		// Ajustar tamaño para ocupar todo el ancho
		int h2 = botonCompartirMarkdown.getPreferredSize().height;
		botonCompartirMarkdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, h2));
		botonCompartirMarkdown.setMinimumSize(new Dimension(0, h2));
		botonCompartirMarkdown.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelControles.add(botonCompartirMarkdown);

		panelControles.add(javax.swing.Box.createVerticalStrut(10));

		// ---------- Campo de enlace ----------
		campoEnlaceReporte = new JTextField();
		campoEnlaceReporte.setEditable(false);
		campoEnlaceReporte.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					copiarAlPortapapeles(campoEnlaceReporte.getText());
				}
			}
		});
		int hf = 28;
		campoEnlaceReporte.setMaximumSize(new Dimension(Integer.MAX_VALUE, hf));
		campoEnlaceReporte.setMinimumSize(new Dimension(0, hf));
		campoEnlaceReporte.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelControles.add(campoEnlaceReporte);

		// Agregar texto y controles al panel superior
		panelSuperior.add(new JScrollPane(textoExplicacion), BorderLayout.CENTER);
		panelSuperior.add(panelControles, BorderLayout.SOUTH);

		// ---------- Resto de la estructura ----------
		inicializarTabla();
		inicializarPanelConfiguracion();
		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		panelPrincipal.add(new JScrollPane(tabla), BorderLayout.CENTER);
		panelPrincipal.add(panelConfig, BorderLayout.SOUTH);
		add(panelPrincipal);

		cargarConsolas();
		this.setVisible(true);
	}

	/**
	 * Inicializa la tabla de consolas.
	 */
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
		modeloTabla.addColumn(MonitorDePID.idioma.columna_url()); // <- antes: "URL"

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

	/**
	 * Inicializa el panel de configuración con campos para endpoint, API, sitio y
	 * anonimización.
	 */
	private void inicializarPanelConfiguracion() {
		panelConfig = new JPanel(new GridBagLayout());
		panelConfig.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.titulo_configuracion())); // <-
																												// antes:
																												// "Configuración"
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;

		// Endpoint
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		panelConfig.add(new JLabel(MonitorDePID.idioma.endpointDeInforme()), gbc);
		gbc.gridx++;
		gbc.weightx = 3.0;
		campoEndpoint = new JTextField(Config.obtenerInstancia().obtenerSitoDeInformes(), 50);
		campoEndpoint.setMinimumSize(new Dimension(400, 25));
		panelConfig.add(campoEndpoint, gbc);

		// API
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0;
		panelConfig.add(new JLabel(MonitorDePID.idioma.apiDeLogging()), gbc);
		gbc.gridx++;

		CrashDetectorLogger.log("Obtainer sito actual");
		String sito_actual = Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado();
		APIdeSitioDeRegistro api_def;
		CrashDetectorLogger.log("Obtainer API");
		boolean error_de_api;
		try {
			api_def = obtenerAPI();
			error_de_api = false;
		} catch (NoAPIdeRegistro e) {
			// TODO Auto-generated catch block
			CrashDetectorLogger.log("En Catch");
			mostrarError(MonitorDePID.idioma.apiDeRegistroNoExiste(), e);
			CrashDetectorLogger.log("Popup");
			api_def = Consola.secure_logger_api;
			sito_actual = "https://securelogger.net/save/log?";
			error_de_api = true;
		}

		CrashDetectorLogger.log("Obtainer Mapa de API");
		Map<String, Set<String>> apis = new HashMap<>();
		for (APIdeSitioDeRegistro api : APIdeSitioDeRegistro.APIS) {
			Set<String> sits = new LinkedHashSet<>();
			if (api != null && api.equals(api_def) && !error_de_api && sito_actual != null) {
				CrashDetectorLogger.log("Anadir sito");
				sits.add(sito_actual);
			}
			sits.addAll(api.sitiosPorDefecto());
			apis.put(api.nombre(), sits);
		}

		CrashDetectorLogger.log("comboAPI");
		comboAPI = new JComboBox<>(apis.keySet().toArray(new String[0]));
		comboAPI.setSelectedItem(api_def.nombre());
		comboAPI.setPreferredSize(new Dimension(300, 25));
		panelConfig.add(comboAPI, gbc);

		// Sitio
		gbc.gridx = 0;
		gbc.gridy++;
		panelConfig.add(new JLabel(MonitorDePID.idioma.sitoDeLogging()), gbc);
		gbc.gridx++;
		comboSitioRegistro = new JComboBox<>(new String[] {});
		comboSitioRegistro.setPreferredSize(new Dimension(300, 25));
		panelConfig.add(comboSitioRegistro, gbc);

		CrashDetectorLogger.log(" actualizar comboAPI");
		actualizarComboSitios(api_def.nombre(), apis.get(api_def.nombre()), sito_actual);

		comboAPI.addActionListener(e -> {
			String apiSeleccionada = (String) comboAPI.getSelectedItem();
			Set<String> sitios = apis.get(apiSeleccionada);
			actualizarComboSitios(apiSeleccionada, sitios, null);
		});

		// Anonimizar
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		checkAnonimizar = new JCheckBox(MonitorDePID.idioma.anonimizarRegistros());
		checkAnonimizar.setSelected(Config.obtenerInstancia().esAnonimizarRegistros());
		panelConfig.add(checkAnonimizar, gbc);

		// Guardar config
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		JButton boton_guardar_de_config = new JButton(MonitorDePID.idioma.guardarConfigDeCompartir());
		boton_guardar_de_config.addActionListener(e -> guardarConfig());
		panelConfig.add(boton_guardar_de_config, gbc);
	}

	// --- Renderers y Editors para la tabla ---
	// Renderer para checkboxes
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

	// Renderer para botones
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

	// Editor para botones (abrir archivo local / compartir enlace por fila)
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
						// === ABRIR: abrir el archivo local en un editor (NO publicar) ===
						if (cons == null || cons.archivo == null) {
							mostrarError(MonitorDePID.idioma.sin_archivo_para_abrir(), null); // <- antes literal
							return;
						}
						java.io.File f = cons.archivo.toFile();
						if (!f.exists()) {
							mostrarError(MonitorDePID.idioma.archivo_no_existe_prefijo() + f.getAbsolutePath(), null); // <-
																														// antes
																														// literal
							return;
						}

						if (java.awt.Desktop.isDesktopSupported()) {
							java.awt.Desktop d = java.awt.Desktop.getDesktop();
							try {
								if (d.isSupported(java.awt.Desktop.Action.EDIT)) {
									d.edit(f); // preferir editor
								} else if (d.isSupported(java.awt.Desktop.Action.OPEN)) {
									d.open(f); // fallback: abrir con app por defecto
								} else {
									// Último recurso: abrir carpeta que lo contiene
									java.io.File parent = f.getParentFile();
									if (parent != null && parent.exists()
											&& d.isSupported(java.awt.Desktop.Action.OPEN)) {
										d.open(parent);
									} else {
										copiarAlPortapapeles(f.getAbsolutePath());
										mostrarInfo(MonitorDePID.idioma.no_se_pudo_abrir_se_copia_ruta()); // <- antes
																											// literal
									}
								}
							} catch (Exception ex) {
								CrashDetectorLogger.logException(ex);
								mostrarError(MonitorDePID.idioma.no_se_pudo_editar_se_copia_ruta(), ex); // <- antes
																											// literal
								copiarAlPortapapeles(f.getAbsolutePath());
							}
						} else {
							copiarAlPortapapeles(f.getAbsolutePath());
							mostrarInfo(MonitorDePID.idioma.escritorio_no_soportado_se_copia_ruta()); // <- antes
																										// literal
						}
						return;
					}

					if (accion.equals(MonitorDePID.idioma.texto_de_boton_compartir_enlace())) {
						// === COMPARTIR ENLACE: publicar y mostrar/guardar enlaces ===
						java.util.List<String> urls = cons.obtainerEnlaces(); // <- publica si hace falta
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
					mostrarError(MonitorDePID.idioma.error_inesperado_al_procesar_boton(), ex); // <- antes literal
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

	// Editor para URLs con copiado
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

	// Renderer para URLs con copiado
	private static class URLEditorRenderer extends JLabel implements TableCellRenderer {
		public URLEditorRenderer() {
			setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.TEXT_CURSOR));
			// ConfigColor con Color como parámetro predeterminado
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
		// ConfigColor ahora acepta Color como parámetro predeterminado
		ret.add(ConfigColor.de("dialogo_compartir_enlace", java.awt.Color.BLUE.darker()));
		ret.add(ConfigColor.de("dialogo_compartir_campo_fondo", Color.YELLOW));
		return ret;
	}

	@Override
	public void recargarApariencia() {
		// Cambia el color de fondo del campo de enlace del informe usando ConfigColor
		if (campoEnlaceReporte != null) {
			// ConfigColor con Color como parámetro predeterminado
			campoEnlaceReporte.setBackground(ConfigColor.de("dialogo_compartir_campo_fondo", Color.YELLOW).obtener());
		}
		// Aquí se pueden agregar más cambios de apariencia específicos para esta
		// implementación
	}

}
