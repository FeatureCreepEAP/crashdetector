package com.asbestosstar.crashdetector.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

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
	private Color colorFatal = Color.RED;
	private Color colorError = new Color(255, 165, 0);// naranja
	private Color colorAdvatencia = Color.YELLOW;
	private Color colorPila = Color.BLUE; // azul para stacktraces
	private JScrollPane scrollLogs;
	private JPanel pnlInferior;
	private JPanel pnlLeyenda;
	private JComponent pnlSelector;
	private JTextField txtBuscar = new JTextField();
	private java.util.List<Integer> posicionesCoincidencias = new java.util.ArrayList<>();
	private int indiceBusquedaActual = -1;
	private JComboBox<String> cmbModo = new JComboBox<>(
			new String[] { MonitorDePID.idioma.limpiado(), MonitorDePID.idioma.original() });

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
		scrollLogs.setRowHeaderView(new NumeradorDeLineas(txtRegistros));
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

		inicializarBuscador(capa);

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
					(int) (80 * escY));
		}

		if (scrollLogs != null) {
			scrollLogs.revalidate();
			scrollLogs.repaint();
		}

		recolocarBuscador();
		revalidate();
		repaint();
	}

	/**
	 * Crea el panel de leyenda con los colores de identificación. Ahora se generan
	 * dinámicamente según los errores detectados.
	 */
	private JPanel crearPanelLeyenda() {
		JPanel pnl = new JPanel();
		pnl.setLayout(new javax.swing.BoxLayout(pnl, javax.swing.BoxLayout.Y_AXIS));
		pnl.setBackground(colorFondo);
		pnl.setBorder(BorderFactory.createTitledBorder(idioma.obtenerTituloLeyenda()));

		// 🔹 Conjunto para evitar duplicados
		java.util.Set<Color> coloresMostrados = new java.util.HashSet<>();

		// Recorrer todas las consolas y sus errores
		for (Consola consola : consolas) {
			for (ErrorDeLectador err : consola.errores_de_lectadores) {
				Color c = err.obtenerColor();
				if (!coloresMostrados.contains(c)) {
					JLabel lbl = new JLabel(err.verificacion.nivel_de_criticalidad().nombre);
					lbl.setOpaque(true);
					lbl.setBackground(c);
					lbl.setForeground(Color.BLACK);
					lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
					pnl.add(lbl);
					pnl.add(javax.swing.Box.createVerticalStrut(8));

					coloresMostrados.add(c);
				}
			}
		}

		// 🔹 Extra: pila y texto normal
		JLabel lblPila = new JLabel(idioma.obtenerStacktraceEnLeyenda());
		lblPila.setOpaque(true);
		lblPila.setBackground(colorPila);
		lblPila.setForeground(Color.BLACK);
		lblPila.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl.add(lblPila);

		JLabel lblNormal = new JLabel("Texto normal");
		lblNormal.setOpaque(true);
		lblNormal.setBackground(colorTexto);
		lblNormal.setForeground(Color.BLACK);
		lblNormal.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl.add(lblNormal);

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
		JPanel pnl = new JPanel();
		pnl.setLayout(new GridLayout(2, 1, 0, 5)); // 2 filas, con espacio vertical
		pnl.setBackground(colorFondo);
		pnl.setBorder(BorderFactory.createTitledBorder(idioma.obtenerSeleccionarConsola()));

		// Configuración del combobox de consolas (solo nombre de archivo)
		cmbConsolas.setBackground(colorFondo);
		cmbConsolas.setForeground(colorTexto);
		cmbConsolas.setFont(cmbConsolas.getFont().deriveFont(12f));
		cmbConsolas.setRenderer(new ListCellRendererPersonalizado());

		cmbConsolas.addActionListener(e -> actualizarConsola());

		// Configuración del combobox de modo (Original / limpiado)
		cmbModo.setBackground(colorFondo);
		cmbModo.setForeground(colorTexto);
		cmbModo.setFont(cmbModo.getFont().deriveFont(12f));
		cmbModo.addActionListener(e -> actualizarConsola());

		pnl.add(cmbConsolas);
		pnl.add(cmbModo);

		return pnl;
	}

	/**
	 * Cargador de consolas disponibles en el sistema
	 */
	private void cargarConsolas() {
		for (Consola consola : consolas) {
			String nombreArchivo = new File(consola.archivo.toString()).getName();
			cmbConsolas.addItem(nombreArchivo);
		}

		if (cmbConsolas.getItemCount() > 0) {
			cmbConsolas.setSelectedIndex(0);
			actualizarConsola();
		}
	}

	// 🔹 Mostrar consola según el modo seleccionado
	private void actualizarConsola() {
		String nombreArchivo = (String) cmbConsolas.getSelectedItem();
		if (nombreArchivo == null)
			return;

		Consola consolaSeleccionada = consolas.stream()
				.filter(c -> new File(c.archivo.toString()).getName().equals(nombreArchivo)).findFirst().orElse(null);

		if (consolaSeleccionada == null)
			return;

		String modo = (String) cmbModo.getSelectedItem();
		if (MonitorDePID.idioma.limpiado().equals(modo)) {
			mostrarConsolaLimpiado(consolaSeleccionada);
		} else {
			mostrarConsolaOriginal(consolaSeleccionada.archivo.toString());
		}
	}

	/**
	 * Muestra la consola con el texto limpiado y aplica colores según criticalidad.
	 * Concadena todos los errores encontrados en los paneles de info.
	 */
	private void mostrarConsolaLimpiado(Consola consola) {
		txtRegistros.setText("");
		StyledDocument doc = txtRegistros.getStyledDocument();

		// Estilos base
		Style estiloNormal = crearEstilo("normal", colorTexto);
		Style estiloError = crearEstilo("error", colorError);
		Style estiloPila = crearEstilo("pila", colorPila);

		txtNombreError.setText("");
		txtDescripcionError.setText("");

		try {
			int lineaActual = 0;
			for (String linea : consola.contenido_verificar.split(Verificaciones.nl)) {
				Style estiloAplicar = estiloNormal;
				final int lineaIndice = lineaActual;

				List<ErrorDeLectador> erroresEnLinea = consola.errores_de_lectadores.stream()
						.filter(err -> err.obtenerLinea() == lineaIndice).collect(Collectors.toList());

				if (!erroresEnLinea.isEmpty()) {
					// Usar el color del primer error como estilo de la línea
					ErrorDeLectador errPrincipal = erroresEnLinea.get(0);
					estiloAplicar = crearEstilo("error_" + lineaActual, errPrincipal.obtenerColor());

					// Concatenar info de todos los errores en paneles
					for (ErrorDeLectador err : erroresEnLinea) {
						txtNombreError.append(err.verificacion.nombre() + "\n");
						txtDescripcionError.append(err.verificacion.mensaje() + "\n\n");
						CrashDetectorLogger
								.log("Error detectado: " + err.verificacion.nombre() + " en línea " + lineaActual);
					}

				} else if (linea.contains("ERROR") || linea.contains("EXCEPTION")) {
					estiloAplicar = estiloError;
				} else if (linea.contains("STACKTRACE") || linea.contains("at ")) {
					estiloAplicar = estiloPila;
				}

				doc.insertString(doc.getLength(), linea + "\n", estiloAplicar);
				lineaActual++;
			}
		} catch (BadLocationException ex) {
			JOptionPane.showMessageDialog(this, idioma.obtenerErrorLecturaArchivo(), idioma.obtenerTituloError(),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void mostrarConsolaOriginal(String rutaArchivo) {
		txtRegistros.setText("");
		StyledDocument doc = txtRegistros.getStyledDocument();

		// Estilos base
		Style estiloNormal = crearEstilo("normal", colorTexto);
		Style estiloError = crearEstilo("error", colorError);
		Style estiloPila = crearEstilo("pila", colorPila);

		try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
			String linea;
			int lineaActual = 0;

			while ((linea = reader.readLine()) != null) {
				Style estiloAplicar = estiloNormal;
				ErrorDeLectador errorDetectado = null;

				// 🔎 Buscar si hay un error en esta línea original
				for (Consola c : consolas) {
					for (ErrorDeLectador err : c.errores_de_lectadores) {
						if (err.obtenerLineaOriginal() == lineaActual) {
							errorDetectado = err;
							break;
						}
					}
					if (errorDetectado != null)
						break;
				}

				if (errorDetectado != null) {
					estiloAplicar = crearEstilo("error_original_" + lineaActual, errorDetectado.obtenerColor());
					txtNombreError.setText(errorDetectado.verificacion.nombre());
					txtDescripcionError.setText(errorDetectado.verificacion.mensaje());

				} else if (linea.contains("ERROR") || linea.contains("EXCEPTION")) {
					estiloAplicar = estiloError;
				} else if (linea.contains("STACKTRACE") || linea.contains("at ")) {
					estiloAplicar = estiloPila;
				}

				doc.insertString(doc.getLength(), linea + "\n", estiloAplicar);
				lineaActual++;
			}
		} catch (IOException | BadLocationException ex) {
			JOptionPane.showMessageDialog(this, idioma.obtenerErrorLecturaArchivo(), idioma.obtenerTituloError(),
					JOptionPane.ERROR_MESSAGE);
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

	/**
	 * Inicializa el buscador (Ctrl+F / Command+F)
	 */
	private void inicializarBuscador(JLayeredPane capa) {
		txtBuscar.setVisible(false);
		txtBuscar.setBackground(new Color(40, 40, 40));
		txtBuscar.setForeground(Color.WHITE);
		txtBuscar.setBorder(BorderFactory.createTitledBorder("Buscar"));
		capa.add(txtBuscar, JLayeredPane.DRAG_LAYER);

		// Acción al presionar Enter → saltar a la siguiente coincidencia
		txtBuscar.addActionListener(e -> saltarSiguienteCoincidencia());

		// Atajos Ctrl+F y Command+F
		KeyStroke keyStroke = KeyStroke.getKeyStroke("control F");
		KeyStroke keyStrokeMac = KeyStroke.getKeyStroke("meta F"); // ⌘ en Mac
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "abrirBuscador");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeMac, "abrirBuscador");

		getRootPane().getActionMap().put("abrirBuscador", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtBuscar.setVisible(true);
				txtBuscar.requestFocus();
				recolocarBuscador();
			}
		});
	}

	/**
	 * Coloca el buscador en la parte superior derecha, anclado
	 */
	private void recolocarBuscador() {
		int ancho = 200;
		int alto = 50;
		txtBuscar.setBounds(getWidth() - ancho - 30, 30, ancho, alto);
		txtBuscar.revalidate();
		txtBuscar.repaint();
	}

	/**
	 * Busca todas las coincidencias del texto y resalta
	 */
	private void buscarTexto(String texto) {
		Highlighter highlighter = txtRegistros.getHighlighter();
		highlighter.removeAllHighlights();
		posicionesCoincidencias.clear();
		indiceBusquedaActual = -1;

		if (texto == null || texto.isEmpty())
			return;

		String contenido = txtRegistros.getText().toLowerCase();
		texto = texto.toLowerCase();

		int index = contenido.indexOf(texto);
		while (index >= 0) {
			try {
				highlighter.addHighlight(index, index + texto.length(),
						new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
				posicionesCoincidencias.add(index);
				index = contenido.indexOf(texto, index + texto.length());
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		// posicionarse en la primera coincidencia
		if (!posicionesCoincidencias.isEmpty()) {
			indiceBusquedaActual = 0;
			resaltarCoincidenciaActual(texto.length());
		}
	}

	/**
	 * Salta a la siguiente coincidencia con wrap-around
	 */
	private void saltarSiguienteCoincidencia() {
		String texto = txtBuscar.getText();
		if (texto == null || texto.isEmpty())
			return;

		if (posicionesCoincidencias.isEmpty()) {
			buscarTexto(texto);
			return;
		}

		indiceBusquedaActual = (indiceBusquedaActual + 1) % posicionesCoincidencias.size();
		resaltarCoincidenciaActual(texto.length());
	}

	/**
	 * Mueve el caret a la coincidencia actual y hace scroll
	 */
	private void resaltarCoincidenciaActual(int longitud) {
		if (indiceBusquedaActual < 0 || indiceBusquedaActual >= posicionesCoincidencias.size())
			return;

		int pos = posicionesCoincidencias.get(indiceBusquedaActual);
		txtRegistros.setCaretPosition(pos);
		txtRegistros.moveCaretPosition(pos + longitud);
		txtRegistros.requestFocus();
	}

	public static void procesarHipervinculo(String url) {
		try {
			String sinPrefijo = url.substring("lectador://".length());
			CrashDetectorLogger.log("sin prefijo " + sinPrefijo);
			int idx = sinPrefijo.lastIndexOf(":");
			if (idx == -1) {
				CrashDetectorLogger.logException(new IllegalArgumentException("URL de lectador inválida: " + url));
			}

			String rutaArchivo = sinPrefijo.substring(0, idx);
			int numeroLinea = Integer.parseInt(sinPrefijo.substring(idx + 1));
			CrashDetectorLogger.log("ruta " + rutaArchivo);
			Consola consolaSeleccionada = MonitorDePID.consolas.stream()
					.filter(c -> c.archivo.toString().equals(rutaArchivo)).findFirst().orElse(null);

			if (consolaSeleccionada == null) {
				JOptionPane.showMessageDialog(null, "No se encontró la consola para el archivo: " + rutaArchivo,
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			CrashDetectorLogger.log("seleccionada " + consolaSeleccionada.archivo.toString());

			LectadorDeConsolas lector = new LectadorDeConsolas();
			lector.setVisible(true);

			String nombreArchivo = new File(consolaSeleccionada.archivo.toString()).getName();

			CrashDetectorLogger.log("nombre archivo " + nombreArchivo);

			lector.cmbConsolas.setSelectedItem(nombreArchivo);

			CrashDetectorLogger.log("seleccion");

			javax.swing.SwingUtilities.invokeLater(() -> {
				try {
					CrashDetectorLogger.log("en try ");
					Document doc = lector.txtRegistros.getDocument();
					Element root = doc.getDefaultRootElement();

					if (numeroLinea >= 0 && numeroLinea < root.getElementCount()) {
						Element elem = root.getElement(numeroLinea);
						int inicio = elem.getStartOffset();
						lector.txtRegistros.setCaretPosition(inicio);
						lector.txtRegistros.requestFocus();
					}
				} catch (Exception ex) {
					CrashDetectorLogger.logException(ex);
				}
			});

		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
		}
	}

	public static class ErrorDeLectador {

		public Consola consola;
		public int numero_de_linea;
		public Verificaciones verificacion;

		/**
		 * Agregar un error a Lectador De Consolas
		 * 
		 * @param numero_de_linea el numero de linea del error. puedes usar esta metedo
		 *                        mas de una vez si el error es de mas 1 linea
		 * @param verificacion    la verificaion
		 * @param color           Color en la clase LectadorDeConsolas
		 * @return
		 */
		public ErrorDeLectador(Consola consola, int numero_de_linea, Verificaciones verificacion) {
			this.consola = consola;
			this.numero_de_linea = numero_de_linea;
			this.verificacion = verificacion;
		}

		@Override
		public String toString() {
			return "lectador://" + consola.archivo.toString() + ":" + String.valueOf(numero_de_linea);
		}

		public Color obtenerColor() {
			return verificacion.nivel_de_criticalidad().color;
		}

		public int obtenerLinea() {
			return numero_de_linea;
		}

		/**
		 * La linea del registro original.
		 * 
		 * @return
		 */
		public int obtenerLineaOriginal() {
			return consola.obtenerLimpiador().obtenerLineaOriginalDesdeLineaLimpiada(consola.linea_original,
					numero_de_linea);
		}

	}

}
