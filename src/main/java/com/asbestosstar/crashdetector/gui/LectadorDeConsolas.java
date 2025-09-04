package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Componente para la lectura y análisis de logs de consolas con soporte de
 * localización Implementa la interfaz de botón lateral para integración con la
 * barra de herramientas
 */
public class LectadorDeConsolas extends JFrame implements BotonDeBarraLateralDerecha {
	private final static Idioma idioma = MonitorDePID.idioma;
	private final List<Consola> consolas = MonitorDePID.consolas;
	private JTextPane txtRegistros = new JTextPane();
	private JComboBox<String> cmbConsolas = new JComboBox<>();
	private JTextArea txtNombreError = new JTextArea();
	private JTextArea txtDescripcionError = new JTextArea();
	private Color colorFondo = new Color(17, 17, 17);
	private Color colorTexto = Color.WHITE;
	private Color colorError = Color.RED;
	private Color colorPila = new Color(255, 165, 0); // Naranja para stacktraces
	private JScrollPane scrollLogs;
	private JPanel pnlInferior;
	private JPanel pnlLeyenda;
	private JComponent pnlSelector;

	/**
	 * Constructor que inicializa la interfaz sin mostrarla inmediatamente
	 */
	public LectadorDeConsolas() {
		super(idioma.tituloLectador());
		configurarVentana();
		inicializarComponentes();
		cargarConsolas();
	}

	private void configurarVentana() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);

		// Creamos el layeredPane para overlays
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);
		setContentPane(layeredPane);

		// Panel de fondo escalado
		FondoPanel fondo = new FondoPanel("crash_detector/imagenes/kiara_ame.png");
		fondo.setBounds(0, 0, getWidth(), getHeight());
		layeredPane.add(fondo, JLayeredPane.DEFAULT_LAYER);

		// Escuchar cambios de tamaño y redibujar fondo
		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				fondo.setBounds(0, 0, getWidth(), getHeight());
				repaint();
			}
		});
	}

	/**
	 * Panel que dibuja la imagen de fondo escalada al tamaño actual de la ventana
	 */
	private class FondoPanel extends JPanel {
		private Image imagen;

		public FondoPanel(String ruta) {
			try {
				imagen = ImageIO.read(new File(ruta));
			} catch (IOException ex) {
				System.err.println("No se pudo cargar la imagen: " + ex.getMessage());
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (imagen != null) {
				g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
			}
		}
	}

	/**
	 * Inicializa los componentes y los coloca alineados con el fondo. Los tamaños
	 * se calculan en proporción al panel de fondo.
	 */
	private void inicializarComponentes() {
		JLayeredPane capa = (JLayeredPane) getContentPane();

		configurarAreaRegistros();
		scrollLogs = new JScrollPane(txtRegistros);
		capa.add(scrollLogs, JLayeredPane.PALETTE_LAYER);

		pnlInferior = crearPanelInformacionErrores();
		pnlInferior.setOpaque(false);
		capa.add(pnlInferior, JLayeredPane.PALETTE_LAYER);

		pnlLeyenda = crearPanelLeyenda();
		pnlLeyenda.setOpaque(false);
		capa.add(pnlLeyenda, JLayeredPane.PALETTE_LAYER);

		pnlSelector = crearPanelSelector();
		pnlSelector.setOpaque(false);
		capa.add(pnlSelector, JLayeredPane.PALETTE_LAYER);

		recolocarComponentes();
		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				recolocarComponentes();
			}
		});
	}

	private void recolocarComponentes() {
		int anchoBase = 1280;
		int altoBase = 720;

		int anchoActual = getWidth();
		int altoActual = getHeight();

		double escX = (double) anchoActual / anchoBase;
		double escY = (double) altoActual / altoBase;

		if (scrollLogs != null) {
			scrollLogs.setBounds((int) (232 * escX), (int) (15 * escY), (int) (820 * escX), (int) (475 * escY));
		}

		if (pnlInferior != null) {
			pnlInferior.setBounds((int) (375 * escX), (int) (533 * escY), (int) (535 * escX), (int) (140 * escY));
		}

		if (pnlLeyenda != null) {
			pnlLeyenda.setBounds((int) (30 * escX), (int) (30 * escY), (int) (180 * escX), (int) (200 * escY));
		}

		if (pnlSelector != null) {
			pnlSelector.setBounds((int) ((anchoBase - 225) * escX), (int) (30 * escY), (int) (220 * escX),
					(int) (40 * escY));
		}

		if (scrollLogs != null) {
			scrollLogs.revalidate();
			scrollLogs.repaint();
		}

		revalidate();
		repaint();
	}

	/**
	 * Crea el panel de leyenda con los colores de identificación, ajustando la
	 * altura de cada fila al texto (no estirado).
	 */
	private JPanel crearPanelLeyenda() {
		JPanel pnl = new JPanel();
		pnl.setLayout(new javax.swing.BoxLayout(pnl, javax.swing.BoxLayout.Y_AXIS));
		pnl.setBackground(colorFondo);
		pnl.setBorder(BorderFactory.createTitledBorder(idioma.obtenerTituloLeyenda()));

		// Elemento para errores
		JLabel lblError = new JLabel(idioma.obtenerErrorEnLeyenda());
		lblError.setOpaque(true);
		lblError.setBackground(colorError);
		lblError.setForeground(Color.BLACK); // para que resalte
		lblError.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl.add(lblError);

		// Espaciado pequeño
		pnl.add(javax.swing.Box.createVerticalStrut(8));

		// Elemento para stacktraces
		JLabel lblPila = new JLabel(idioma.obtenerStacktraceEnLeyenda());
		lblPila.setOpaque(true);
		lblPila.setBackground(colorPila);
		lblPila.setForeground(Color.BLACK);
		lblPila.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl.add(lblPila);

		return pnl;
	}

	/**
	 * Crea el panel central con el visor de logs y áreas de información
	 */
	private JSplitPane crearPanelCentral() {
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setDividerLocation(400);
		split.setOneTouchExpandable(true);

		// Área de registros (logs)
		configurarAreaRegistros();
		JScrollPane scrollLogs = new JScrollPane(txtRegistros);

		// Áreas inferiores para información de errores
		JPanel pnlInferior = crearPanelInformacionErrores();

		split.setTopComponent(scrollLogs);
		split.setBottomComponent(pnlInferior);
		return split;
	}

	/**
	 * Configura las propiedades del área de registros
	 */
	private void configurarAreaRegistros() {
		txtRegistros.setEditable(false);
		txtRegistros.setBackground(colorFondo);
		txtRegistros.setForeground(colorTexto);
		txtRegistros.setOpaque(true);

		// Manejador de clics para selección de errores
		txtRegistros.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int pos = txtRegistros.viewToModel(e.getPoint());
				try {
					int linea = txtRegistros.getDocument().getDefaultRootElement().getElementIndex(pos);
					String textoLinea = texto_de_linea(linea);
					procesarSeleccionError(textoLinea);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(LectadorDeConsolas.this, idioma.obtenerErrorAlProcesarLinea(),
							idioma.obtenerTituloError(), JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Crea el panel de información de errores (nombre y descripción)
	 */
	private JPanel crearPanelInformacionErrores() {
		JPanel pnl = new JPanel(new GridLayout(1, 2, 42, 10));

		// Área de nombre de error
		txtNombreError.setEditable(false);
		txtNombreError.setBackground(colorFondo);
		txtNombreError.setForeground(colorTexto);
		txtNombreError.setLineWrap(true);
		txtNombreError.setWrapStyleWord(true);
		txtNombreError.setBorder(BorderFactory.createTitledBorder(idioma.obtenerNombreError()));
		pnl.add(txtNombreError);

		// Área de descripción de error
		txtDescripcionError.setEditable(false);
		txtDescripcionError.setBackground(colorFondo);
		txtDescripcionError.setForeground(colorTexto);
		txtDescripcionError.setLineWrap(true);
		txtDescripcionError.setWrapStyleWord(true);
		txtDescripcionError.setBorder(BorderFactory.createTitledBorder(idioma.obtenerDescripcionError()));
		pnl.add(txtDescripcionError);

		return pnl;
	}

	/**
	 * Crea el panel de selección de consolas
	 */
	private JPanel crearPanelSelector() {
		JPanel pnl = new JPanel(new BorderLayout());
		pnl.setBackground(colorFondo);
		pnl.setBorder(BorderFactory.createTitledBorder(idioma.obtenerSeleccionarConsola()));

		// Configuración del combobox
		cmbConsolas.setBackground(colorFondo);
		cmbConsolas.setForeground(colorTexto);
		cmbConsolas.setFont(cmbConsolas.getFont().deriveFont(12f));
		cmbConsolas.setRenderer(new ListCellRendererPersonalizado());

		cmbConsolas.addActionListener(e -> {
			String archivo = (String) cmbConsolas.getSelectedItem();
			if (archivo != null) {
				mostrarConsola(archivo);
			}
		});

		pnl.add(cmbConsolas, BorderLayout.CENTER);
		return pnl;
	}

	/**
	 * Cargador de consolas disponibles en el sistema
	 */
	private void cargarConsolas() {
		for (Consola consola : consolas) {
			cmbConsolas.addItem(consola.archivo.toString());
		}

		if (cmbConsolas.getItemCount() > 0) {
			cmbConsolas.setSelectedIndex(0);
			mostrarConsola((String) cmbConsolas.getSelectedItem());
		}
	}

	/**
	 * Muestra el contenido de una consola específica
	 */
	private void mostrarConsola(String rutaArchivo) {
		txtRegistros.setText("");
		StyledDocument doc = txtRegistros.getStyledDocument();

		// Creación de estilos
		Style estiloNormal = crearEstilo("normal", colorTexto);
		Style estiloError = crearEstilo("error", colorError);
		Style estiloPila = crearEstilo("pila", colorPila);

		try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				Style estiloAplicar = estiloNormal;
				if (linea.contains("ERROR") || linea.contains("EXCEPTION")) {
					estiloAplicar = estiloError;
				} else if (linea.contains("STACKTRACE") || linea.contains("at ")) {
					estiloAplicar = estiloPila;
				}
				doc.insertString(doc.getLength(), linea + "\n", estiloAplicar);
			}
		} catch (IOException | BadLocationException ex) {
			JOptionPane.showMessageDialog(this, idioma.obtenerErrorLecturaArchivo(), idioma.obtenerTituloError(),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Crea un estilo de texto con el color especificado
	 */
	private Style crearEstilo(String nombre, Color color) {
		Style estilo = txtRegistros.addStyle(nombre, null);
		StyleConstants.setForeground(estilo, color);
		return estilo;
	}

	/**
	 * Obtiene el texto de una línea específica
	 */
	private String texto_de_linea(int lineNumber) {
		try {
			Document doc = txtRegistros.getDocument();
			Element root = doc.getDefaultRootElement();
			Element elem = root.getElement(lineNumber);
			int inicio = elem.getStartOffset();
			int fin = elem.getEndOffset() - 1;
			return doc.getText(inicio, fin - inicio);
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * Procesa la selección de un error en los registros
	 */
	private void procesarSeleccionError(String textoLinea) {
		if (textoLinea.contains("ERROR") || textoLinea.contains("EXCEPTION")) {
			txtNombreError.setText(idioma.obtenerNombreErrorPorDefecto());
			txtDescripcionError.setText(idioma.obtenerDescripcionErrorPorDefecto());
		} else {
			txtNombreError.setText("");
			txtDescripcionError.setText("");
		}
	}

	/**
	 * Implementación de la interfaz BotonDeBarraLateralDerecha Acción ejecutada al
	 * presionar el botón
	 */
	@Override
	public void init() {
		setVisible(true);
	}

	/**
	 * Implementación de la interfaz BotonDeBarraLateralDerecha Devuelve la etiqueta
	 * del botón en la barra lateral
	 */
	@Override
	public String etiquetaDelBoton() {
		return idioma.obtenerEtiquetaBotonLectador();
	}

	/**
	 * Clase para renderizar elementos del combobox con tema oscuro
	 */
	private class ListCellRendererPersonalizado extends DefaultListCellRenderer {
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			c.setForeground(colorTexto);
			if (isSelected) {
				c.setBackground(new Color(50, 50, 50));
			}
			return c;
		}
	}

	/**
	 * Panel personalizado para mostrar imagen de fondo
	 */
	private class ImagePanel extends JPanel {
		private Image imagen;

		public ImagePanel(String ruta) {
			try {
				imagen = ImageIO.read(new File(ruta));
			} catch (IOException ex) {
				CrashDetectorLogger.log("Error al cargar imagen: " + ex.getMessage());
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (imagen != null) {
				// Escala la imagen manteniendo proporciones
				double ratio = Math.min((double) getWidth() / imagen.getWidth(this),
						(double) getHeight() / imagen.getHeight(this));
				int ancho = (int) (imagen.getWidth(this) * ratio);
				int alto = (int) (imagen.getHeight(this) * ratio);
				int x = (getWidth() - ancho) / 2;
				int y = (getHeight() - alto) / 2;

				g.drawImage(imagen, x, y, ancho, alto, this);
			}
		}
	}
}