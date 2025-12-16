package com.asbestosstar.crashdetector.gui.tipos.editorgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ConfigDouble;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Implementación concreta del editor de GUIs con apariencia CDSkinCape. Define
 * completamente el layout, la apariencia y la gestión de `ConfigColor`.
 */
public class CDSkinCape extends EditorGUI {

	public static String ID = "cdskincape";

	public ConfigColor colorFondo = ConfigColor.de("tema.cdskincape.editor.color.fondo", new Color(240, 245, 250));
	public ConfigColor colorTexto = ConfigColor.de("tema.cdskincape.editor.color.texto", new Color(50, 50, 50));
	public ConfigColor colorBoton = ConfigColor.de("tema.cdskincape.editor.color.boton", new Color(66, 133, 244));
	public ConfigColor colorCajaTexto = ConfigColor.de("tema.cdskincape.editor.color.caja_texto",
			new Color(255, 255, 255));
	public ConfigColor colorBorde = ConfigColor.de("tema.cdskincape.editor.color.borde", new Color(200, 200, 200));

	// Panel para mostrar las opciones de configuración de la GUI seleccionada
	private JPanel panelConfiguracionGUI;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {
		// Inicializar colores PRIMERO

		// AHORA llamamos al init del padre
		inicializarInterfaz();
		this.setVisible(true);
	}

	private void inicializarInterfaz() {
		setLayout(new BorderLayout(10, 10));
		// Establecer tamaño preferido y mínimo
		setPreferredSize(new Dimension(1000, 700));
		setMinimumSize(new Dimension(800, 500));

		// Panel superior con título y botones
		JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
		panelSuperior.setBackground(colorFondo.obtener());
		JLabel titulo = new JLabel("Editor de GUIs - CDSkinCape");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
		titulo.setForeground(colorTexto.obtener());
		panelSuperior.add(titulo, BorderLayout.WEST);

		JButton botonMostrar = new JButton("Mostrar GUI");
		botonMostrar.setForeground(Color.WHITE);
		botonMostrar.setBackground(colorBoton.obtener());
		botonMostrar.setFocusPainted(false);
		botonMostrar.addActionListener(e -> mostrarGUI());
		panelSuperior.add(botonMostrar, BorderLayout.EAST);

		add(panelSuperior, BorderLayout.NORTH);

		// Panel central con combos y panel de edición
		JPanel panelCentral = new JPanel(new GridBagLayout());
		panelCentral.setBackground(colorFondo.obtener());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Combo para seleccionar el tipo de GUI
		gbc.gridx = 0;
		gbc.gridy = 0;
		panelCentral.add(new JLabel("Tipo de GUI:"), gbc);
		gbc.gridx = 1;
		comboTipoGUI = new JComboBox<>();
		comboTipoGUI.setPreferredSize(new Dimension(200, 30));
		comboTipoGUI.setBackground(colorCajaTexto.obtener());
		comboTipoGUI.setForeground(colorTexto.obtener());
		comboTipoGUI.addActionListener(e -> actualizarComboGUI());
		panelCentral.add(comboTipoGUI, gbc);

		// Combo para seleccionar la GUI específica
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelCentral.add(new JLabel("GUI:"), gbc);
		gbc.gridx = 1;
		comboGUI = new JComboBox<>();
		comboGUI.setPreferredSize(new Dimension(200, 30));
		comboGUI.setBackground(colorCajaTexto.obtener());
		comboGUI.setForeground(colorTexto.obtener());
		comboGUI.addActionListener(e -> cargarGUISeleccionada());
		panelCentral.add(comboGUI, gbc);

		// Panel de edición principal
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		panelEdicion = new JPanel(new BorderLayout());
		panelEdicion.setBorder(BorderFactory.createLineBorder(colorBorde.obtener()));
		panelEdicion.setBackground(colorCajaTexto.obtener());
		JScrollPane scrollPanelEdicion = new JScrollPane(panelEdicion);
		panelCentral.add(scrollPanelEdicion, gbc);

		// Panel para mostrar la configuración de la GUI seleccionada
		panelConfiguracionGUI = new JPanel(new BorderLayout());
		panelConfiguracionGUI.setBorder(BorderFactory.createTitledBorder("Configuración de la GUI Seleccionada"));
		panelConfiguracionGUI.setBackground(colorCajaTexto.obtener());

		// JSplitPane para dividir panelEdicion y panelConfiguracionGUI
		JSplitPane splitPanePrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPanelEdicion,
				panelConfiguracionGUI);
		splitPanePrincipal.setDividerLocation(0.7); // Ajusta el tamaño inicial
		splitPanePrincipal.setResizeWeight(0.7); // Ajusta cómo se reparte el espacio al redimensionar
		splitPanePrincipal.setDividerSize(5); // Ajusta el tamaño del divisor
		splitPanePrincipal.setBackground(colorFondo.obtener());

		// Añadir el splitPane al panel central en lugar del scrollPanelEdicion
		// directamente
		gbc.gridy = 2; // Mantener la misma fila
		panelCentral.add(splitPanePrincipal, gbc);

		add(panelCentral, BorderLayout.CENTER);

		// Cargar tipos de GUI
		for (TipoGUI<?> tipo : TipoGUI.TIPOS_DE_GUI) {
			comboTipoGUI.addItem(tipo.id());
		}

		// Aplicar apariencia inicial
		aplicarApariencia();
	}

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
	}

	private void aplicarApariencia() {
		// Actualizar colores de todos los componentes
		setBackground(colorFondo.obtener());
		if (getComponents().length > 0) {
			Component panelSuperior = getComponents()[0];
			if (panelSuperior instanceof JPanel) {
				((JPanel) panelSuperior).setBackground(colorFondo.obtener());
			}
		}
		if (getComponents().length > 1) {
			Component panelCentral = getComponents()[1];
			if (panelCentral instanceof JPanel) {
				((JPanel) panelCentral).setBackground(colorFondo.obtener());
			}
		}
		if (comboTipoGUI != null) {
			comboTipoGUI.setBackground(colorCajaTexto.obtener());
			comboTipoGUI.setForeground(colorTexto.obtener());
		}
		if (comboGUI != null) {
			comboGUI.setBackground(colorCajaTexto.obtener());
			comboGUI.setForeground(colorTexto.obtener());
		}
		if (panelEdicion != null) {
			panelEdicion.setBackground(colorCajaTexto.obtener());
		}
		if (panelConfiguracionGUI != null) {
			panelConfiguracionGUI.setBackground(colorCajaTexto.obtener());
		}
		revalidate();
		repaint();
	}

	@Override
	public void cargarGUISeleccionada() {
		super.cargarGUISeleccionada(); // Llama al método padre para cargar la GUI
		actualizarPanelConfiguracionGUI(); // Actualiza el panel de configuración
	}

	private void actualizarPanelConfiguracionGUI() {
		// Limpiar el panel de configuración
		panelConfiguracionGUI.removeAll();

		// Obtener los elementos de configuración de la GUI seleccionada
		java.util.List<ElementoConfig> elementosConfig = obtenerElementosConfigSeleccionados();

		if (elementosConfig != null && !elementosConfig.isEmpty()) {
			// Crear un panel para contener los componentes de configuración
			JPanel panelContenidoConfig = new JPanel();
			panelContenidoConfig.setLayout(new BoxLayout(panelContenidoConfig, BoxLayout.Y_AXIS));
			panelContenidoConfig.setBackground(colorCajaTexto.obtener());

			for (ElementoConfig<?> elemento : elementosConfig) {
				// Verificar si el elemento es nulo antes de usarlo
				if (elemento == null) {
					continue; // Saltar elementos nulos
				}
				// Crear un panel para cada elemento de configuración
				JPanel panelElemento = new JPanel(new BorderLayout(5, 5));
				panelElemento.setBackground(colorCajaTexto.obtener());
				panelElemento.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

				JLabel labelNombre = new JLabel(elemento.obtenerNombreParaMostrar() + " (" + elemento.clave() + "):");
				labelNombre.setForeground(colorTexto.obtener());
				panelElemento.add(labelNombre, BorderLayout.WEST);

				// Crear un componente editable según el tipo del elemento
				Component componenteEditable = crearComponenteEditable(elemento);
				if (componenteEditable != null) {
					panelElemento.add(componenteEditable, BorderLayout.CENTER);
				}

				// Solo añadir el botón "Editar" si NO es un ConfigColor
				if (!(elemento instanceof com.asbestosstar.crashdetector.config.ConfigColor)) {
					JButton botonEditar = new JButton("Editar");
					botonEditar.addActionListener(e -> editarElemento(elemento));
					panelElemento.add(botonEditar, BorderLayout.EAST);
				}

				panelContenidoConfig.add(panelElemento);
				panelContenidoConfig.add(Box.createVerticalStrut(5)); // Espacio entre elementos
			}

			// Añadir el panel de contenido al JScrollPane del panel de configuración
			JScrollPane scrollConfig = new JScrollPane(panelContenidoConfig);
			scrollConfig.setBorder(BorderFactory.createEmptyBorder());
			panelConfiguracionGUI.add(scrollConfig, BorderLayout.CENTER);
		} else {
			// Si no hay elementos de configuración, mostrar un mensaje
			JLabel labelMensaje = new JLabel("No hay opciones de configuración disponibles para esta GUI.");
			labelMensaje.setHorizontalAlignment(JLabel.CENTER);
			labelMensaje.setForeground(colorTexto.obtener());
			panelConfiguracionGUI.add(labelMensaje, BorderLayout.CENTER);
		}

		// Repintar el panel de configuración
		panelConfiguracionGUI.revalidate();
		panelConfiguracionGUI.repaint();
	}

	/**
	 * Crea un componente editable adecuado para el tipo de ElementoConfig.
	 *
	 * @param elemento El elemento de configuración.
	 * @return Un componente editable o null si no se puede crear.
	 */
	private Component crearComponenteEditable(ElementoConfig<?> elemento) {
		if (elemento instanceof com.asbestosstar.crashdetector.config.ConfigBoolean) {
			JCheckBox checkBox = new JCheckBox();
			checkBox.setSelected((Boolean) elemento.obtener());
			checkBox.addActionListener(e -> {
				boolean nuevoValor = checkBox.isSelected();
				((ConfigBoolean) elemento).escribir(nuevoValor);
			});
			return checkBox;
		} else if (elemento instanceof com.asbestosstar.crashdetector.config.ConfigColor) {
			// Para colores, usar un panel con un cuadrado de previsualización y un campo de
			// texto
			JPanel panelColor = new JPanel(new BorderLayout(5, 0));
			JTextField textField = new JTextField("#" + Integer
					.toHexString(((com.asbestosstar.crashdetector.config.ConfigColor) elemento).obtener().getRGB())
					.substring(2));
			textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
				@Override
				public void insertUpdate(javax.swing.event.DocumentEvent e) {
					actualizarColor(textField, (com.asbestosstar.crashdetector.config.ConfigColor) elemento);
				}

				@Override
				public void removeUpdate(javax.swing.event.DocumentEvent e) {
					actualizarColor(textField, (com.asbestosstar.crashdetector.config.ConfigColor) elemento);
				}

				@Override
				public void changedUpdate(javax.swing.event.DocumentEvent e) {
					actualizarColor(textField, (com.asbestosstar.crashdetector.config.ConfigColor) elemento);
				}

				private void actualizarColor(JTextField field,
						com.asbestosstar.crashdetector.config.ConfigColor configColor) {
					String texto = field.getText();
					if (texto == null || texto.isEmpty())
						return;
					try {
						if (texto.startsWith("#")) {
							texto = texto.substring(1);
						}
						if (texto.length() == 6) {
							if (texto.matches("[0-9A-Fa-f]+")) {
								Color color = Color.decode("#" + texto);
								configColor.escribir(color);
							}
						}
					} catch (Exception ex) {
						// Ignorar durante la edición
					}
				}
			});
			// Crear el cuadrado de previsualización
			JPanel cuadradoColor = new JPanel();
			cuadradoColor.setPreferredSize(new Dimension(20, 20));
			cuadradoColor.setBackground(((com.asbestosstar.crashdetector.config.ConfigColor) elemento).obtener());
			cuadradoColor.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			// Añadir listener al cuadrado para abrir el selector de color
			cuadradoColor.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Color nuevoColor = javax.swing.JColorChooser.showDialog(CDSkinCape.this, "Seleccionar Color",
							((com.asbestosstar.crashdetector.config.ConfigColor) elemento).obtener());
					if (nuevoColor != null) {
						((com.asbestosstar.crashdetector.config.ConfigColor) elemento).escribir(nuevoColor);
						cuadradoColor.setBackground(nuevoColor);
						textField.setText("#" + Integer.toHexString(nuevoColor.getRGB()).substring(2));
					}
				}
			});
			// Añadir el cuadrado y el campo de texto al panel
			JPanel panelInterno = new JPanel(new BorderLayout(5, 0));
			panelInterno.add(cuadradoColor, BorderLayout.WEST);
			panelInterno.add(textField, BorderLayout.CENTER);
			panelColor.add(panelInterno, BorderLayout.CENTER);
			return panelColor;
		} else if (elemento instanceof com.asbestosstar.crashdetector.config.ConfigDouble) {
			// Ahora usa un JTextField para ConfigDouble
			JTextField textField = new JTextField(elemento.obtener().toString());
			textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
				@Override
				public void insertUpdate(javax.swing.event.DocumentEvent e) {
					actualizarTexto(textField, (com.asbestosstar.crashdetector.config.ConfigDouble) elemento);
				}

				@Override
				public void removeUpdate(javax.swing.event.DocumentEvent e) {
					actualizarTexto(textField, (com.asbestosstar.crashdetector.config.ConfigDouble) elemento);
				}

				@Override
				public void changedUpdate(javax.swing.event.DocumentEvent e) {
					actualizarTexto(textField, (com.asbestosstar.crashdetector.config.ConfigDouble) elemento);
				}

				private void actualizarTexto(JTextField field,
						com.asbestosstar.crashdetector.config.ConfigDouble configDouble) {
					String texto = field.getText();
					if (texto == null || texto.isEmpty())
						return;
					try {
						double valor = Double.parseDouble(texto);
						configDouble.escribir(valor);
					} catch (NumberFormatException ex) {
						// Ignorar durante la edición si no es un número válido
					}
				}
			});
			return textField;
		} else if (elemento instanceof com.asbestosstar.crashdetector.config.ConfigString) {
			JTextField textField = new JTextField((String) elemento.obtener());
			textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
				@Override
				public void insertUpdate(javax.swing.event.DocumentEvent e) {
					actualizarTexto(textField, (com.asbestosstar.crashdetector.config.ConfigString) elemento);
				}

				@Override
				public void removeUpdate(javax.swing.event.DocumentEvent e) {
					actualizarTexto(textField, (com.asbestosstar.crashdetector.config.ConfigString) elemento);
				}

				@Override
				public void changedUpdate(javax.swing.event.DocumentEvent e) {
					actualizarTexto(textField, (com.asbestosstar.crashdetector.config.ConfigString) elemento);
				}

				private void actualizarTexto(JTextField field,
						com.asbestosstar.crashdetector.config.ConfigString configString) {
					String texto = field.getText();
					configString.escribir(texto);
				}
			});
			return textField;
		}
		// Para otros tipos, devolver un JLabel con el valor como texto
		JLabel labelValor = new JLabel(elemento.obtener().toString());
		labelValor.setForeground(colorTexto.obtener());
		return labelValor;
	}
	
	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
	    List<ElementoConfig> elementos = new ArrayList<>();

	    colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
	    colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
	    colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
	    colorCajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
	    colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());

	    elementos.add(colorFondo);
	    elementos.add(colorTexto);
	    elementos.add(colorBoton);
	    elementos.add(colorCajaTexto);
	    elementos.add(colorBorde);

	    return elementos;
	}

	/**
	 * Edita el elemento de configuración proporcionado.
	 * 
	 * @param elemento El elemento de configuración a editar.
	 */
	public void editarElemento(ElementoConfig<?> elemento) {
		if (elemento == null) {
			return;
		}

		String titulo = "Editar: " + elemento.obtenerNombreParaMostrar() + " (" + elemento.clave() + ")";
		String valorActual = elemento.obtener().toString();

		// Crear un JTextField para la entrada del usuario
		JTextField campoTexto = new JTextField(valorActual);
		Object[] mensaje = { "Nuevo valor:", campoTexto };

		// Mostrar el cuadro de diálogo de entrada con JTextField
		int opcion = JOptionPane.showConfirmDialog(this, mensaje, titulo, JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		// Si el usuario cancela el cuadro de entrada
		if (opcion == JOptionPane.CANCEL_OPTION) {
			return; // Salir si no se proporciona un nuevo valor
		}

		String nuevoValor = campoTexto.getText(); // Obtener el String del campo de texto

		try {
			// Manejar según tipos específicos de configuración
			if (elemento instanceof ConfigBoolean) {
				Boolean nuevoBoolean = Boolean.valueOf(nuevoValor.trim());
				((ConfigBoolean) elemento).escribir(nuevoBoolean);
			} else if (elemento instanceof ConfigColor) {
				Color nuevoColor = Color.decode(nuevoValor.trim());
				((ConfigColor) elemento).escribir(nuevoColor);
			} else if (elemento instanceof ConfigDouble) {
				double nuevoDouble = Double.parseDouble(nuevoValor.trim());
				((ConfigDouble) elemento).escribir(nuevoDouble);
			} else if (elemento instanceof ConfigString) {
				((ConfigString) elemento).escribir(nuevoValor.trim());
			} else {
				throw new IllegalArgumentException(
						"Tipo de ElementoConfig no soportado: " + elemento.getClass().getName());
			}

			// Actualizar la interfaz para reflejar los cambios
			actualizarPanelConfiguracionGUI();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Error al parsear el nuevo valor: debe ser un número válido.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al procesar el nuevo valor: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}