package com.asbestosstar.crashdetector.gui.tipos.editor_plantilla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ConfigString; // <-- NUEVO: soporte para enlaces configurables
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Clase abstracta para el editor de plantilla. Proporciona funcionalidad básica
 * sin manejar la apariencia.
 */
public abstract class EditorPlantilla extends JPanel implements CrashDetectorGUI {

	public static Map<String, Supplier<EditorPlantilla>> GUIS = new HashMap<>();

	// Componentes que las implementaciones pueden usar
	public JTextPane editorHTML;
	public JEditorPane vistaPrevia;
	public JButton botonGuardar;
	public JButton botonRestablecerPlantilla;
	public JButton botonCerrar;
	public JPanel panelConfiguracion;

	// Campos funcionales
	public Config configuracion = Config.obtenerInstancia();
	public boolean actualizandoVista = false;
	public File archivoPlantilla;
	public Map<String, ConfigColor> colorMap = new HashMap<>();

	// --- NUEVO: mapa de enlaces configurables para el reporte compartido ---
	public Map<String, ConfigString> enlacesMap = new HashMap<>();

	// Las implementaciones deben inicializar los componentes
	public abstract void inicializarComponentes();

	/**
	 * Carga el contenido de la plantilla en el editor.
	 */
	public void cargarContenidoPlantilla() {
		archivoPlantilla = Statics.carpeta.resolve("pantilla.htm").toFile();

		if (archivoPlantilla.exists()) {
			try {
				StringBuilder contenido = new StringBuilder();
				try (BufferedReader reader = new BufferedReader(new java.io.FileReader(archivoPlantilla))) {
					String linea;
					while ((linea = reader.readLine()) != null) {
						contenido.append(linea).append("\n");
					}
				}
				editorHTML.setText(contenido.toString());
				resaltarSintaxis();
				actualizarVistaPrevia();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error al cargar la plantilla desde disco: " + e.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			try {
				String rutaPlantilla = "pantilla.htm";
				InputStream is = getClass().getClassLoader().getResourceAsStream(rutaPlantilla);
				if (is != null) {
					StringBuilder contenido = new StringBuilder();
					try (BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, StandardCharsets.UTF_8))) {
						String linea;
						while ((linea = reader.readLine()) != null) {
							contenido.append(linea).append("\n");
						}
					}
					editorHTML.setText(contenido.toString());
					resaltarSintaxis();
					actualizarVistaPrevia();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this,
						"No se encontró la plantilla. Restablezca usando el botón 'Restablecer Plantilla'.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Resalta la sintaxis del editor HTML.
	 */
	public void resaltarSintaxis() {
		if (actualizandoVista)
			return;
		actualizandoVista = true;

		StyledDocument doc = editorHTML.getStyledDocument();
		String text = editorHTML.getText();

		SimpleAttributeSet normal = new SimpleAttributeSet();
		doc.setCharacterAttributes(0, text.length(), normal, true);

		// Resaltar {constructor}
		int inicio = 0;
		while ((inicio = text.indexOf("{constructor}", inicio)) != -1) {
			SimpleAttributeSet style = new SimpleAttributeSet();
			StyleConstants.setForeground(style, new Color(0, 120, 212));
			StyleConstants.setBold(style, true);
			doc.setCharacterAttributes(inicio, 13, style, false);
			inicio += 13;
		}

		// Resaltar {mensaje_ayudar}
		inicio = 0;
		while ((inicio = text.indexOf("{mensaje_ayudar}", inicio)) != -1) {
			SimpleAttributeSet style = new SimpleAttributeSet();
			StyleConstants.setForeground(style, new Color(153, 0, 153));
			StyleConstants.setBold(style, true);
			doc.setCharacterAttributes(inicio, 16, style, false);
			inicio += 16;
		}

		// Resaltar etiquetas HTML
		inicio = 0;
		while (inicio < text.length()) {
			int abertura = text.indexOf("<", inicio);
			if (abertura == -1)
				break;

			int cierre = text.indexOf(">", abertura);
			if (cierre == -1)
				break;

			SimpleAttributeSet style = new SimpleAttributeSet();
			StyleConstants.setForeground(style, new Color(153, 0, 153));
			doc.setCharacterAttributes(abertura, cierre - abertura + 1, style, false);

			inicio = cierre + 1;
		}

		actualizandoVista = false;
	}

	/**
	 * Actualiza la vista previa del editor.
	 */
	public void actualizarVistaPrevia() {
		if (actualizandoVista)
			return;
		actualizandoVista = true;

		vistaPrevia.removeAll();

		String contenido = editorHTML.getText();
		String colorError = configuracion.obtenerColorError();
		String colorAdvertencia = configuracion.obtenerColorAdvertencia();
		String colorInfo = configuracion.obtenerColorInfo();
		String colorTitulo = configuracion.obtenerColorTitulo();
		String colorTitulosConsolas = configuracion.obtenerColorDeTitulosDeConsolas();
		String colorEnlace = configuracion.obtenerColorEnlace();

		String ejemploAnalisis = "<div style='color:#" + colorTitulo
				+ "; font-weight: bold; margin-bottom: 10px;'>Ejemplo de Análisis</div>" + "<div style='color:#"
				+ colorTitulosConsolas + "; font-weight: bold; margin-bottom: 5px;'>Título de Consola de Ejemplo</div>"
				+ "<div style='color:#" + colorError
				+ "'>[EJEMPLO] Error crítico: No se pudo cargar el mod 'ExampleMod'</div>" + "<div style='color:#"
				+ colorAdvertencia + "'>[EJEMPLO] Advertencia: Conflictos entre mods detectados</div>"
				+ "<div style='color:#" + colorInfo
				+ "'>[EJEMPLO] Información: 5 soluciones potenciales encontradas</div>";

		String ejemploAyuda = "<div style='color:#" + colorEnlace + "; margin-top: 20px;'>"
				+ "¿Necesitas ayuda? Usa el botón Compartir para obtener enlaces a los registros y a los resultados."
				+ "</div>";

		String contenidoVista = contenido.replace("{constructor}", ejemploAnalisis).replace("{mensaje_ayudar}",
				ejemploAyuda);

		vistaPrevia.setText(contenidoVista);
		vistaPrevia.setCaretPosition(0);

		actualizandoVista = false;
	}

	/**
	 * Crea un panel para una imagen.
	 */
	public JPanel crearPanelImagen(String nombreImagen) {
		JPanel panel = new JPanel(new BorderLayout());

		JLabel label = new JLabel(nombreImagen);
		label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panel.add(label, BorderLayout.NORTH);

		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.X_AXIS));
		panelContenido.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel previewLabel = new JLabel();
		previewLabel.setPreferredSize(new Dimension(100, 56));
		previewLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		actualizarVistaPreviaImagen(nombreImagen, previewLabel);

		JButton botonRestablecer = new JButton(MonitorDePID.idioma.restablecer());
		botonRestablecer.setPreferredSize(new Dimension(80, 25));
		botonRestablecer.setAlignmentY(Component.CENTER_ALIGNMENT);
		botonRestablecer.addActionListener(e -> restablecerImagen(nombreImagen, previewLabel));

		panelContenido.add(previewLabel);
		panelContenido.add(Box.createHorizontalStrut(10));
		panelContenido.add(botonRestablecer);

		panel.add(panelContenido, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * Inicializa la configuración de colores en el panel proporcionado.
	 * 
	 * @param panelCampos El panel donde se añadirán los campos de color.
	 */
	public void inicializarConfiguracionColores(JPanel panelCampos) {
		colorMap.put("enlace",
				ConfigColor.de("color_enlace", Config.convertirAColor(configuracion.obtenerColorEnlace())));
		colorMap.put("titulosConsolas", ConfigColor.de("color_de_titulos_de_consolas",
				Config.convertirAColor(configuracion.obtenerColorDeTitulosDeConsolas())));
		colorMap.put("error", ConfigColor.de("color_error", Config.convertirAColor(configuracion.obtenerColorError())));
		colorMap.put("advertencia",
				ConfigColor.de("color_advertencia", Config.convertirAColor(configuracion.obtenerColorAdvertencia())));
		colorMap.put("info", ConfigColor.de("color_info", Config.convertirAColor(configuracion.obtenerColorInfo())));
		colorMap.put("titulo",
				ConfigColor.de("color_titulo", Config.convertirAColor(configuracion.obtenerColorTitulo())));

		panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorEnlace(), colorMap.get("enlace")));
		panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorTitulosConsolas(), colorMap.get("titulosConsolas")));
		panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorError(), colorMap.get("error")));
		panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorAdvertencia(), colorMap.get("advertencia")));
		panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorInfo(), colorMap.get("info")));
		panelCampos.add(crearCampoDeColor(MonitorDePID.idioma.colorTitulo(), colorMap.get("titulo")));
	}

	// --- NUEVO: Inicializa la configuración de enlaces (Gura, Mumei, Shion) ---
	/**
	 * Inicializa la configuración de enlaces de imágenes para el reporte
	 * compartido. Los cambios se guardan inmediatamente mediante
	 * ConfigString.escribir(...).
	 */
	public void inicializarConfiguracionEnlaces(JPanel panelCampos) {
		// Valores por defecto (como en tu snippet)
		enlacesMap.put("gura",
				ConfigString.de("enlace_imagen_gura", "http://asbestosstar.egoism.jp/crash_detector/gura.png"));
		enlacesMap.put("mumei", ConfigString.de("enlace_imagen_mumei",
				"http://asbestosstar.egoism.jp/crash_detector/nanashi_mumei.png"));
		enlacesMap.put("shion",
				ConfigString.de("enlace_imagen_shion", "http://asbestosstar.egoism.jp/crash_detector/shion.png"));

		panelCampos.add(crearCampoDeTextoEnlace("Enlace imagen Gura", enlacesMap.get("gura")));
		panelCampos.add(crearCampoDeTextoEnlace("Enlace imagen Mumei", enlacesMap.get("mumei")));
		panelCampos.add(crearCampoDeTextoEnlace("Enlace imagen Shion", enlacesMap.get("shion")));
	}

	// --- NUEVO: helper para crear campo de texto de enlace ---
	/**
	 * Crea un campo de texto para editar un enlace de imagen del reporte. Guarda al
	 * vuelo en configuración.
	 */
	public JPanel crearCampoDeTextoEnlace(String etiqueta, ConfigString configString) {
		JPanel panel = new JPanel(new BorderLayout(5, 0));
		JLabel label = new JLabel(etiqueta);
		label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panel.add(label, BorderLayout.WEST);

		JTextField field = new JTextField(configString.obtener());
		field.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				escribir();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				escribir();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				escribir();
			}

			private void escribir() {
				try {
					// Escribe inmediatamente en la config persistente
					configString.escribir(field.getText());
				} catch (Exception ignored) {
					// Ignorar errores menores durante la edición
				}
			}
		});
		panel.add(field, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * Actualiza la vista previa de una imagen.
	 */
	public void actualizarVistaPreviaImagen(String nombreImagen, JLabel previewLabel) {
		File imagenFile = Statics.carpeta.resolve("imagenes").resolve(nombreImagen).toFile();

		if (imagenFile.exists()) {
			try {
				BufferedImage img = ImageIO.read(imagenFile);
				int ancho = 100;
				int alto = (int) (ancho * 0.56);
				Image scaledImage = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
				previewLabel.setIcon(new ImageIcon(scaledImage));
			} catch (IOException e) {
				previewLabel.setText("Error");
			}
		} else {
			previewLabel.setText("No existe");
		}
	}

	/**
	 * Restablece una imagen a su versión predeterminada.
	 */
	public void restablecerImagen(String nombreImagen, JLabel previewLabel) {
		int confirmacion = JOptionPane.showConfirmDialog(this,
				MonitorDePID.idioma.restablecerImagenMensjae(nombreImagen), MonitorDePID.idioma.restablecer(),
				JOptionPane.YES_NO_OPTION);

		if (confirmacion != JOptionPane.YES_OPTION)
			return;

		String rutaRecurso = "/imagenes/" + nombreImagen;
		try (InputStream is = getClass().getClassLoader().getResourceAsStream(rutaRecurso)) {
			if (is == null) {
				JOptionPane.showMessageDialog(this, "No se encontró la imagen en el recurso: " + rutaRecurso, "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			File destino = Statics.carpeta.resolve("imagenes").resolve(nombreImagen).toFile();
			destino.getParentFile().mkdirs();

			try (OutputStream os = new java.io.FileOutputStream(destino)) {
				byte[] buffer = new byte[4096];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
			}

			JOptionPane.showMessageDialog(this, "Imagen restablecida: " + nombreImagen, "Éxito",
					JOptionPane.INFORMATION_MESSAGE);

			actualizarVistaPreviaImagen(nombreImagen, previewLabel);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error al restablecer la imagen: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Crea un campo de color para la configuración.
	 */
	public JPanel crearCampoDeColor(String nombre, ConfigColor configColor) {
		JPanel panel = new JPanel(new BorderLayout(5, 0));

		JLabel label = new JLabel(nombre);
		label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panel.add(label, BorderLayout.WEST);

		JPanel inputPanel = new JPanel(new BorderLayout(5, 0));

		JTextField textField = crearCampoTextoConfiguracion(Config.colorAHexHtml(configColor.obtener()), configColor);
		textField.setPreferredSize(new Dimension(80, 25));
		inputPanel.add(textField, BorderLayout.CENTER);

		PanelPrevisualizacionColor colorPreview = new PanelPrevisualizacionColor(textField, configColor);
		inputPanel.add(colorPreview, BorderLayout.EAST);

		panel.add(inputPanel, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * Crea un campo de texto para la configuración de colores.
	 */
	public JTextField crearCampoTextoConfiguracion(String valorInicial, ConfigColor configColor) {
		JTextField field = new JTextField(valorInicial);
		field.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				procesarCambio();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				procesarCambio();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				procesarCambio();
			}

			private void procesarCambio() {
				String texto = field.getText();
				if (texto == null || texto.isEmpty())
					return;

				try {
					if (texto.startsWith("#")) {
						texto = texto.substring(1);
					}

					if (texto.length() == 3 || texto.length() == 6) {
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
		return field;
	}

	/**
	 * Guarda la plantilla actual.
	 */
	public void guardarPlantilla() {
		if (archivoPlantilla == null || !archivoPlantilla.exists()) {
			archivoPlantilla = Statics.carpeta.resolve("pantilla.htm").toFile();
		}

		try {
			try (java.io.FileWriter writer = new java.io.FileWriter(archivoPlantilla)) {
				writer.write(editorHTML.getText());
			}

			JOptionPane.showMessageDialog(this, "Plantilla guardada en: " + archivoPlantilla.getAbsolutePath(), "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Restablece la plantilla a su versión predeterminada.
	 */
	public void restablecerPlantilla() {
		int confirmacion = JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.restablecerPlantillaMensaje(),
				MonitorDePID.idioma.confirmacion(), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirmacion == JOptionPane.YES_OPTION) {
			try {
				String rutaPlantilla = "pantilla.htm";
				InputStream is = getClass().getClassLoader().getResourceAsStream(rutaPlantilla);
				if (is != null) {
					StringBuilder contenido = new StringBuilder();
					try (BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, StandardCharsets.UTF_8))) {
						String linea;
						while ((linea = reader.readLine()) != null) {
							contenido.append(linea).append("\n");
						}
					}

					try (java.io.FileWriter writer = new java.io.FileWriter(archivoPlantilla)) {
						writer.write(contenido.toString());
					}

					editorHTML.setText(contenido.toString());
					resaltarSintaxis();
					actualizarVistaPrevia();

					JOptionPane.showMessageDialog(this, "Plantilla restablecida correctamente.", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error al restablecer: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Cierra el editor.
	 */
	public void cerrarEditor() {
		if (SwingUtilities.getWindowAncestor(this) instanceof java.awt.Dialog) {
			((java.awt.Dialog) SwingUtilities.getWindowAncestor(this)).dispose();
		}
	}

	/**
	 * Panel para previsualizar colores.
	 */
	public class PanelPrevisualizacionColor extends JPanel {
		private JTextField textField;
		private ConfigColor configColor;

		public PanelPrevisualizacionColor(JTextField textField, ConfigColor configColor) {
			this.textField = textField;
			this.configColor = configColor;
			setPreferredSize(new Dimension(25, 25));
			setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
			setBackground(configColor.obtener());
			setOpaque(true);

			// Ensure the color is displayed immediately
			SwingUtilities.invokeLater(() -> {
				setBackground(configColor.obtener());
				repaint();
			});

			addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Color nuevoColor = JColorChooser.showDialog(PanelPrevisualizacionColor.this, "Seleccionar Color",
							configColor.obtener());
					if (nuevoColor != null) {
						actualizarColor(nuevoColor);
					}
				}
			});
		}

		public void actualizarColor(Color color) {
			configColor.escribir(color);
			setBackground(color);
			repaint();

			String hexColor = String.format("%06X", (0xFFFFFF & color.getRGB()));
			textField.setText(hexColor);
			actualizarVistaPrevia();
		}

		public void actualizarPrevisualizacion() {
			setBackground(configColor.obtener());
			repaint();
		}
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.EDITOR_PLANTILLA;
	}

	@Override
	public void init() {
		inicializarComponentes();
		cargarContenidoPlantilla();
	}

	/**
	 * Obtiene los nombres de las imágenes de VTubers.
	 */
	public List<String> obtenerNombresImágenesVTuber() {
		List<String> ret = new ArrayList<>();
		ret.add("gura.png");
		ret.add("nanashi_mumei.png");
		ret.add("shion.png");

		return ret;
	}
}
