package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import com.asbestosstar.crashdetector.GeneradorDeInformacion;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro;
import com.asbestosstar.crashdetector.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetector.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sito_registro.NoAPIdeRegistro;

public class DialogoCompartir extends JDialog {
	private final DefaultTableModel modeloTabla;
	private final JTextField campoEndpoint;
	private final JComboBox<String> comboAPI;
	private final JComboBox<String> comboSitioRegistro;
	private final JCheckBox checkAnonimizar;
	public Instant instant;
	private final JTextField campoEnlaceReporte;
	private final JButton botonCompartirTodos;

	public DialogoCompartir(JFrame padre, Instant instant) {
		super(padre, "Compartir Registros", true);
		setSize(900, 700);
		setLocationRelativeTo(padre);
		this.instant = instant;

		JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));

		// Panel superior con explicación y controles
		JPanel panelSuperior = new JPanel(new BorderLayout(0, 10));
		panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JTextArea textoExplicacion = new JTextArea(MonitorDePID.idioma.arco());
		textoExplicacion.setEditable(false);
		textoExplicacion.setLineWrap(true);
		textoExplicacion.setWrapStyleWord(true);
		textoExplicacion.setBackground(panelSuperior.getBackground());

		// Contenedor para botón y enlace
		JPanel panelControles = new JPanel(new BorderLayout(0, 5));

		botonCompartirTodos = new JButton(MonitorDePID.idioma.botonDeCompartirInforme());
		botonCompartirTodos.addActionListener(e -> {
			try {
				compartirSeleccionados(e);
			} catch (DemasiadoGrande e0) {
				// TODO Auto-generated catch block

				JOptionPane.showMessageDialog(null, MonitorDePID.idioma.registroDemasiadoGrande(), "Error",
						JOptionPane.ERROR);

			} catch (ErrorConPublicar e1) {
				// TODO Auto-generated catch block

				JOptionPane.showMessageDialog(null, MonitorDePID.idioma.errorConPublicarRegistro(e1.problema), "Error",
						JOptionPane.ERROR);

			} catch (NoAPIdeRegistro e2) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, MonitorDePID.idioma.apiDeRegistroNoExiste(), "Error",
						JOptionPane.ERROR);

			}
		});

		campoEnlaceReporte = new JTextField();
		campoEnlaceReporte.setEditable(false);
		campoEnlaceReporte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					StringSelection selection = new StringSelection(campoEnlaceReporte.getText());
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(selection, null);
				}
			}
		});

		panelControles.add(botonCompartirTodos, BorderLayout.NORTH);
		panelControles.add(campoEnlaceReporte, BorderLayout.CENTER);

		panelSuperior.add(new JScrollPane(textoExplicacion), BorderLayout.CENTER);
		panelSuperior.add(panelControles, BorderLayout.SOUTH);

		// Tabla de consolas
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
		modeloTabla.addColumn(MonitorDePID.idioma.texto_de_buton_compartir_enlance());
		modeloTabla.addColumn("URL");

		JTable tabla = new JTable(modeloTabla);
		tabla.setRowHeight(30);
		tabla.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());
		tabla.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
		tabla.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer(MonitorDePID.idioma.abrir()));
		tabla.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(MonitorDePID.idioma.abrir()));
		tabla.getColumnModel().getColumn(3)
				.setCellRenderer(new ButtonRenderer(MonitorDePID.idioma.texto_de_buton_compartir_enlance()));
		tabla.getColumnModel().getColumn(3)
				.setCellEditor(new ButtonEditor(MonitorDePID.idioma.texto_de_buton_compartir_enlance()));
		tabla.getColumnModel().getColumn(4).setCellRenderer(new URLEditorRenderer());
		tabla.getColumnModel().getColumn(4).setCellEditor(new URLEditor());

		// Panel de configuración
		JPanel panelConfig = new JPanel(new GridBagLayout());
		panelConfig.setBorder(BorderFactory.createTitledBorder("Configuración"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;

		// Endpoint (Ahora más ancho)
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		panelConfig.add(new JLabel(MonitorDePID.idioma.endpointDeInforme()), gbc);
		gbc.gridx++;
		gbc.weightx = 3.0; // Prioridad de expansión
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
		boolean error_de_api;
		CrashDetectorLogger.log("Obtainer API");
		try {
			CrashDetectorLogger.log("En Try");
			api_def = APIdeSitioDeRegistro.obtainerAPIdeConfig();
			CrashDetectorLogger.log("no Error");
			error_de_api=false;
		} catch (NoAPIdeRegistro e1) {
			// TODO Auto-generated catch block
			CrashDetectorLogger.log("En Catch");

			JOptionPane.showMessageDialog(null, MonitorDePID.idioma.apiDeRegistroNoExiste(), "Error",
					JOptionPane.ERROR);
			CrashDetectorLogger.log("Popup");

			api_def=Consola.secure_logger_api;
			sito_actual="https://securelogger.net/save/log?";
			error_de_api=true;
		}


		CrashDetectorLogger.log("Obtainer Mapa de API");

		Map<String,Set<String>> apis = new HashMap<String,Set<String>>();
		for(APIdeSitioDeRegistro api:APIdeSitioDeRegistro.APIS) {
			
			Set<String> sits = new LinkedHashSet<String>();
			if(api!=null&&api.equals(api_def)&&!error_de_api&&sito_actual!=null) {
				CrashDetectorLogger.log("Anadir sito");
				sits.add(sito_actual);
			}
			sits.addAll(api.sitosPorDefecto());
		
			apis.put(api.nombre(), sits);
		}
		
		
		
		CrashDetectorLogger.log("comboAPI");

		
		// Configurar combo de APIs
		comboAPI = new JComboBox<>(apis.keySet().toArray(new String[0]));
		comboAPI.setSelectedItem(api_def.nombre()); // Establecer API por defecto
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

		// Configurar combo de sitios inicialmente
		actualizarComboSitios(api_def.nombre(), apis.get(api_def.nombre()), sito_actual);

		// Listener para actualizar sitios al cambiar API
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
		//checkAnonimizar.setEnabled(false);
		checkAnonimizar.setSelected(Config.obtenerInstancia().esAnonimizarRegistros());
		panelConfig.add(checkAnonimizar, gbc);

		// Save Button
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;

		JButton boton_guardar_de_config = new JButton(MonitorDePID.idioma.guardarConfigDeCompartir());
		boton_guardar_de_config.addActionListener(e -> {

			guardarConfig();

		});

		panelConfig.add(boton_guardar_de_config, gbc);

		// Estructura principal
		panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
		panelPrincipal.add(new JScrollPane(tabla), BorderLayout.CENTER);
		panelPrincipal.add(panelConfig, BorderLayout.SOUTH);

		add(panelPrincipal);
		cargarConsolas();
	}

	private void cargarConsolas() {
		for (Consola consola : MonitorDePID.consolas) {
			modeloTabla.addRow(new Object[] { true, consola.archivo.getFileName().toString(),
					MonitorDePID.idioma.abrir(), MonitorDePID.idioma.texto_de_buton_compartir_enlance(), "" });
		}
	}

	private void compartirSeleccionados(ActionEvent e) throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro {
		ArrayList<Consola> seleccionados = new ArrayList<>();
		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			if ((Boolean) modeloTabla.getValueAt(i, 0)) {
				seleccionados.add(MonitorDePID.consolas.get(i));
			}
		}

		if (!seleccionados.isEmpty()) {
			String enlace = GeneradorDeInformacion.compartir(seleccionados, instant);
			campoEnlaceReporte.setText(enlace);
			MonitorDePID.enlance = enlace;

			// Actualizar URLs individuales
			for (int i = 0; i < modeloTabla.getRowCount(); i++) {
				if ((Boolean) modeloTabla.getValueAt(i, 0)) {
					Consola cons = MonitorDePID.consolas.get(i);
					String url = cons.obtainerEnlance();
					modeloTabla.setValueAt(url, i, 4);
				}
			}

			try {
				Desktop.getDesktop().browse(new URL(enlace).toURI());
			} catch (Exception ex) {
				CrashDetectorLogger.logException(ex);
			}
		}
	}

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
			setMargin(new Insets(2, 5, 2, 5));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return this;
		}
	}

	// Editor para botones
	private class ButtonEditor extends DefaultCellEditor {
		private final String accion;
		private JButton button;
		private int currentRow;

		public ButtonEditor(String accion) {
			super(new JCheckBox());
			this.accion = accion;
			button = new JButton(accion);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (currentRow >= 0) {
						if (accion.equals(MonitorDePID.idioma.abrir())) {
							Path archivo = MonitorDePID.consolas.get(currentRow).archivo;
							File carpeta = archivo.getParent().toFile();
							try {
								Desktop.getDesktop().open(carpeta);
							} catch (IOException ex) {
								CrashDetectorLogger.logException(ex);
							}
						} else if (accion.equals(MonitorDePID.idioma.texto_de_buton_compartir_enlance())) {
							CrashDetectorLogger.log("compartir boton");
							Consola cons = MonitorDePID.consolas.get(currentRow);
							try {
								String url = cons.obtainerEnlance();
								modeloTabla.setValueAt(url, currentRow, 4);
								Desktop.getDesktop().browse(new URL(url).toURI());
							} catch (MalformedURLException e1) {
								// TODO Auto-generated catch block
								CrashDetectorLogger.logException(e1);
							} catch (DemasiadoGrande e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, MonitorDePID.idioma.registroDemasiadoGrande(),
										"Error", JOptionPane.ERROR);

							} catch (ErrorConPublicar e1) {
								// TODO Auto-generated catch block

								JOptionPane.showMessageDialog(null, MonitorDePID.idioma.errorConPublicarRegistro(e1.problema),
										"Error", JOptionPane.ERROR);

							} catch (NoAPIdeRegistro e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, MonitorDePID.idioma.apiDeRegistroNoExiste(),
										"Error", JOptionPane.ERROR);

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								CrashDetectorLogger.logException(e1);
							} catch (URISyntaxException e1) {
								// TODO Auto-generated catch block
								CrashDetectorLogger.logException(e1);
							}

						}
						fireEditingStopped();
					}
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
		private JTextField textField;

		public URLEditor() {
			super(new JTextField());
			textField = (JTextField) getComponent();
			textField.setEditable(false);
			textField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						StringSelection selection = new StringSelection(textField.getText());
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(selection, null);
					}
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			textField.setText(value.toString());
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
			setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			setForeground(Color.BLUE.darker());
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						StringSelection selection = new StringSelection(getText());
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(selection, null);
					}
				}
			});
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value.toString());
			return this;
		}
	}

	public void guardarConfig() {
		Config.obtenerInstancia().guardarSitioDeInformes(campoEndpoint.getText());

		String api = (String) comboAPI.getSelectedItem();
		Config.obtenerInstancia().guardarApiSeleccionada(api);

		String sitio_registro = (String) comboSitioRegistro.getSelectedItem();
		Config.obtenerInstancia().guardarSitioRegistrosSeleccionado(sitio_registro);

		boolean anonimizar = checkAnonimizar.isSelected();
		Config.obtenerInstancia().guardarAnonimizarRegistros(anonimizar);

		// Optional: Show confirmation or log
		CrashDetectorLogger.log("Configuration saved.");
	}
	
	
	// Método para actualizar el combo de sitios
	private void actualizarComboSitios(String apiNombre, Set<String> sitios, String sitioSeleccionado) {
	    comboSitioRegistro.removeAllItems();
	    if (sitios != null) {
	        for (String sitio : sitios) {
	            comboSitioRegistro.addItem(sitio);
	        }
	        // Seleccionar sitio específico si se proporciona
	        if (sitioSeleccionado != null && sitios.contains(sitioSeleccionado)) {
	            comboSitioRegistro.setSelectedItem(sitioSeleccionado);
	        } else {
	            comboSitioRegistro.setSelectedIndex(0); // Seleccionar primer elemento
	        }
	    }
	}
	
	
	
}