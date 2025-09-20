package com.asbestosstar.crashdetector.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class LectadorDeConsolas extends JFrame implements BotonDeBarraLateralDerecha {

	// Campos de idioma y datos
	private static final Idioma idioma = MonitorDePID.idioma;
	private final List<Consola> consolas = MonitorDePID.consolas;

	// Cache concurrente de líneas por consola
	private final Map<String, List<String>> cacheLineasPorConsola = new ConcurrentHashMap<>();

	// Ejecutor con número de hilos igual a la mitad de los núcleos (mínimo 1)
	// Número de hilos prudente para no saturar CPU
	final int CORES = Runtime.getRuntime().availableProcessors();
	final int N_HILOS = Math.min(4, Math.max(2, CORES - 1));

	// Cola acotada para evitar explosión de memoria si hay muchas consolas
	final int CAPACIDAD_COLA = N_HILOS * 4;

	private final ThreadPoolExecutor pool = new ThreadPoolExecutor(N_HILOS, N_HILOS, 30L, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(CAPACIDAD_COLA), new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r, "LectadorPool-" + System.nanoTime());
					t.setDaemon(true);
					return t;
				}
			},
			// Si la cola se llena, ejecutar en el hilo del llamador para frenar la
			// producción
			new ThreadPoolExecutor.CallerRunsPolicy());

	// Opcional: permitir que los hilos core expiren si queda inactivo
	{
		pool.allowCoreThreadTimeOut(true);
	}

	// Componentes UI
	private final DefaultListModel<String> modeloRegistros = new DefaultListModel<>();
	private final JList<String> listaRegistros = new JList<>(modeloRegistros);

	private final JComboBox<String> cmbConsolas = new JComboBox<>();
	private final JTextArea txtNombreError = new JTextArea();

	private final Color colorFondo = new Color(17, 17, 17);
	private final Color colorTexto = Color.WHITE;
	private final Color colorError = new Color(255, 165, 0);
	private final Color colorPila = Color.BLUE;

	private JScrollPane scrollLogs;
	private JPanel pnlInferior;
	private JPanel pnlLeyenda;
	private JComponent pnlSelector;
	private final JTextField txtBuscar = new JTextField();
	private final java.util.List<Integer> posicionesCoincidencias = new java.util.ArrayList<>();
	private int indiceBusquedaActual = -1;
	private final JComboBox<String> cmbModo = new JComboBox<>(
			new String[] { MonitorDePID.idioma.limpiado(), MonitorDePID.idioma.original() });

	// Fondo cargado en segundo plano
	private FondoPanel fondo;
	private final JEditorPane txtDescripcionError = new JEditorPane(); // ahora HTML
	private JScrollPane scrollDescripcion; // scroll del panel HTML

	public LectadorDeConsolas() {
		super(idioma.tituloLectador());
		configurarVentana();
		inicializarComponentes();
		cargarConsolas();
		precargarLineasEnSegundoPlano();
	}

	private void configurarVentana() {
		setSize(1280, 720);
		setLocationRelativeTo(null);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);
		setContentPane(layeredPane);

		fondo = new FondoPanel("crash_detector/imagenes/kiara_ame.png");
		fondo.setBounds(0, 0, getWidth(), getHeight());
		layeredPane.add(fondo, JLayeredPane.DEFAULT_LAYER);

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				fondo.setBounds(0, 0, getWidth(), getHeight());
				recolocarComponentes();
				repaint();
			}
		});

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {
				try {
					pool.shutdownNow();
				} catch (Exception ignored) {
				}
			}
		});
	}

	private static class FondoPanel extends JPanel {
		private volatile java.awt.Image imagen;
		private final String ruta;

		public FondoPanel(String ruta) {
			this.ruta = ruta;
		}

		@Override
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			if (imagen != null) {
				g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
			}
		}

		public void cargarAsincrono(ExecutorService pool, final Runnable whenLoadedOnEDT) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						java.awt.Image temp = ImageIO.read(new File(ruta));
						imagen = temp;
					} catch (Exception ex) {
						CrashDetectorLogger.log("No se pudo cargar fondo: " + ex.getMessage());
					}
					SwingUtilities.invokeLater(whenLoadedOnEDT);
				}
			});
		}
	}

	private void inicializarComponentes() {
		JLayeredPane capa = (JLayeredPane) getContentPane();

		fondo.cargarAsincrono(pool, new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});

		configurarAreaRegistros();
		scrollLogs = new JScrollPane(listaRegistros);
		scrollLogs.setRowHeaderView(new NumeradorDeLineas(listaRegistros));
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

	private void recolocarBuscador() {
		// Ubicar el buscador justo debajo del panel de selectores (combo + modo)
		if (pnlSelector != null) {
			int margen = 8; // separación vertical
			int x = pnlSelector.getX();
			int y = pnlSelector.getY() + pnlSelector.getHeight() + margen;
			int w = pnlSelector.getWidth();
			int h = 40; // alto razonable del buscador

			txtBuscar.setBounds(x, y, w, h);
		} else {
			// Fallback si aún no existe pnlSelector
			int ancho = 200;
			int alto = 40;
			txtBuscar.setBounds(getWidth() - ancho - 30, 30, ancho, alto);
		}
		txtBuscar.revalidate();
		txtBuscar.repaint();
	}

	private void inicializarBuscador(JLayeredPane capa) {
		txtBuscar.setVisible(false);
		txtBuscar.setBackground(new Color(40, 40, 40));
		txtBuscar.setForeground(Color.WHITE);
		txtBuscar.setBorder(BorderFactory.createTitledBorder("Buscar"));
		capa.add(txtBuscar, JLayeredPane.DRAG_LAYER);

		txtBuscar.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saltarSiguienteCoincidencia();
			}
		});

		KeyStroke keyStroke = KeyStroke.getKeyStroke("control F");
		KeyStroke keyStrokeMac = KeyStroke.getKeyStroke("meta F");
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

	private void saltarSiguienteCoincidencia() {
		String texto = txtBuscar.getText();
		if (texto == null || texto.isEmpty())
			return;

		if (posicionesCoincidencias.isEmpty()) {
			buscarTexto(texto);
			return;
		}

		indiceBusquedaActual = (indiceBusquedaActual + 1) % posicionesCoincidencias.size();
		resaltarCoincidenciaActual();
	}

	private void buscarTexto(String texto) {
		posicionesCoincidencias.clear();
		indiceBusquedaActual = -1;

		if (texto == null || texto.isEmpty())
			return;

		String t = texto.toLowerCase();
		for (int i = 0; i < modeloRegistros.size(); i++) {
			String linea = modeloRegistros.get(i).toLowerCase();
			if (linea.contains(t)) {
				posicionesCoincidencias.add(i);
			}
		}

		if (!posicionesCoincidencias.isEmpty()) {
			indiceBusquedaActual = 0;
			resaltarCoincidenciaActual();
		}

		listaRegistros.repaint();
	}

	private void resaltarCoincidenciaActual() {
		if (indiceBusquedaActual < 0 || indiceBusquedaActual >= posicionesCoincidencias.size())
			return;

		int pos = posicionesCoincidencias.get(indiceBusquedaActual);
		listaRegistros.setSelectedIndex(pos);
		listaRegistros.ensureIndexIsVisible(pos);
	}

	private JPanel crearPanelLeyenda() {
		JPanel pnl = new JPanel();
		pnl.setLayout(new javax.swing.BoxLayout(pnl, javax.swing.BoxLayout.Y_AXIS));
		pnl.setBackground(colorFondo);
		pnl.setBorder(BorderFactory.createTitledBorder(idioma.obtenerTituloLeyenda()));

		java.util.Set<Color> coloresMostrados = new java.util.HashSet<Color>();

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

	private void configurarAreaRegistros() {
		listaRegistros.setBackground(colorFondo);
		listaRegistros.setForeground(colorTexto);
		listaRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaRegistros.setFixedCellHeight(16);

		listaRegistros.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel lbl = new JLabel(value);
			lbl.setOpaque(true);
			lbl.setForeground(colorTexto);
			lbl.setBackground(colorFondo);

			Consola consola = obtenerConsolaSeleccionada();
			if (consola != null) {
				List<ErrorDeLectador> errores = consola.errores_de_lectadores.stream()
						.filter(err -> err.obtenerLinea() == index).collect(Collectors.toList());

				if (!errores.isEmpty()) {
					lbl.setBackground(errores.get(0).obtenerColor());
					lbl.setForeground(Color.BLACK);
				} else if (value.contains("ERROR") || value.contains("EXCEPTION")) {
					lbl.setBackground(colorError);
					lbl.setForeground(Color.BLACK);
				} else if (value.contains("STACKTRACE") || value.contains("at ")) {
					lbl.setBackground(colorPila);
					lbl.setForeground(Color.BLACK);
				}
			}

			if (isSelected) {
				lbl.setBackground(lbl.getBackground().darker());
			}
			return lbl;
		});

		listaRegistros.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = listaRegistros.locationToIndex(e.getPoint());
				Consola consola = obtenerConsolaSeleccionada();
				if (consola != null) {
					procesarSeleccionError(index, consola);
				}
			}
		});
	}

	private Consola obtenerConsolaSeleccionada() {
		String nombreArchivo = (String) cmbConsolas.getSelectedItem();
		if (nombreArchivo == null)
			return null;
		for (Consola c : consolas) {
			if (new File(c.archivo.toString()).getName().equals(nombreArchivo)) {
				return c;
			}
		}
		return null;
	}

	private JPanel crearPanelInformacionErrores() {
		JPanel pnl = new JPanel(new GridLayout(1, 2, 42, 10));

		// Izquierda: nombres de error en texto plano
		txtNombreError.setEditable(false);
		txtNombreError.setBackground(colorFondo);
		txtNombreError.setForeground(colorTexto);
		pnl.add(txtNombreError);

		// Derecha: visor HTML sin CSS; inicia con fondo negro usando atributos HTML
		txtDescripcionError.setEditable(false);
		txtDescripcionError.setContentType("text/html");
		txtDescripcionError.setOpaque(true);
		txtDescripcionError.setBackground(colorFondo);
		txtDescripcionError.setForeground(colorTexto);
		txtDescripcionError.setText("<html><body bgcolor='#111111' text='#FFFFFF'></body></html>");

		scrollDescripcion = new JScrollPane(txtDescripcionError, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pnl.add(scrollDescripcion);

		return pnl;
	}

	private JPanel crearPanelSelector() {
		JPanel pnl = new JPanel(new GridLayout(2, 1, 0, 5));
		pnl.setBackground(colorFondo);
		pnl.add(cmbConsolas);
		pnl.add(cmbModo);
		cmbConsolas.addActionListener(e -> actualizarConsola());
		cmbModo.addActionListener(e -> actualizarConsola());
		return pnl;
	}

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

	private void precargarLineasEnSegundoPlano() {
		for (final Consola consola : consolas) {
			final String nombreArchivo = new File(consola.archivo.toString()).getName();
			if (cacheLineasPorConsola.containsKey(nombreArchivo))
				continue;

			pool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						List<String> lineas = Arrays.asList(consola.contenido_verificar.split(Verificaciones.nl));
						cacheLineasPorConsola.put(nombreArchivo, lineas);
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								if (nombreArchivo.equals(cmbConsolas.getSelectedItem())) {
									refrescarModeloCon(lineas);
								}
							}
						});
					} catch (Throwable t) {
						CrashDetectorLogger.logException(t);
					}
				}
			});
		}
	}

	private void actualizarConsola() {
		final Consola consola = obtenerConsolaSeleccionada();
		if (consola == null)
			return;

		final String nombreArchivo = new File(consola.archivo.toString()).getName();

		List<String> cache = cacheLineasPorConsola.get(nombreArchivo);
		if (cache != null) {
			refrescarModeloCon(cache);
			return;
		}

		modeloRegistros.clear();
		modeloRegistros.addElement("Cargando " + nombreArchivo + " ...");

		// Cambiar cursor a “espera” (beach ball / reloj de arena)
		setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

		pool.submit(new Runnable() {
			@Override
			public void run() {
				try {
					List<String> lineas = Arrays.asList(consola.contenido_verificar.split(Verificaciones.nl));
					cacheLineasPorConsola.put(nombreArchivo, lineas);
					final List<String> lineasFinal = lineas;

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							if (nombreArchivo.equals(cmbConsolas.getSelectedItem())) {
								refrescarModeloCon(lineasFinal);
							}
							// Restaurar cursor normal
							setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
						}
					});
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							modeloRegistros.clear();
							modeloRegistros.addElement("Error al cargar " + nombreArchivo + ": " + t.getMessage());
							// Restaurar cursor normal incluso si falla
							setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
						}
					});
				}
			}
		});
	}

	private void refrescarModeloCon(List<String> lineas) {
		modeloRegistros.clear();
		for (String l : lineas) {
			modeloRegistros.addElement(l);
		}
	}

	public static void procesarHipervinculo(String url) {
		try {
			String sinPrefijo = url.substring("lectador://".length());
			CrashDetectorLogger.log("sin prefijo " + sinPrefijo);
			int idx = sinPrefijo.lastIndexOf(":");
			if (idx == -1) {
				CrashDetectorLogger.logException(new IllegalArgumentException("URL de lectador inválida: " + url));
				return;
			}

			String rutaArchivo = sinPrefijo.substring(0, idx);
			int numeroLinea = Integer.parseInt(sinPrefijo.substring(idx + 1));
			CrashDetectorLogger.log("ruta " + rutaArchivo);

			Consola consolaSeleccionada = null;
			for (Consola c : MonitorDePID.consolas) {
				if (c.archivo.toString().equals(rutaArchivo)) {
					consolaSeleccionada = c;
					break;
				}
			}

			if (consolaSeleccionada == null) {
				JOptionPane.showMessageDialog(null, "No se encontró la consola para el archivo: " + rutaArchivo,
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			CrashDetectorLogger.log("seleccionada " + consolaSeleccionada.archivo.toString());

			final LectadorDeConsolas lector = new LectadorDeConsolas();
			lector.setVisible(true);

			final String nombreArchivo = new File(consolaSeleccionada.archivo.toString()).getName();
			lector.cmbConsolas.setSelectedItem(nombreArchivo);

			final Consola consolaFinal = consolaSeleccionada;
			lector.pool.submit(new Runnable() {
				@Override
				public void run() {
					List<String> lineas = lector.cacheLineasPorConsola.get(nombreArchivo);
					if (lineas == null) {
						lineas = Arrays.asList(consolaFinal.contenido_verificar.split(Verificaciones.nl));
						lector.cacheLineasPorConsola.put(nombreArchivo, lineas);
					}

					final List<String> lineasFinal = lineas; // declaración final
					final int salto = Math.max(0, Math.min(numeroLinea, lineasFinal.size() - 1));

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							lector.refrescarModeloCon(lineasFinal);
							try {
								if (salto >= 0 && salto < lector.modeloRegistros.size()) {
									lector.listaRegistros.setSelectedIndex(salto);
									lector.listaRegistros.ensureIndexIsVisible(salto);
									lector.listaRegistros.requestFocus();
									CrashDetectorLogger.log("línea seleccionada en JList: " + salto);
								}
							} catch (Exception ex) {
								CrashDetectorLogger.logException(ex);
							}
						}
					});
				}
			});

		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
		}
	}

	private void procesarSeleccionError(int numeroLinea, Consola consola) {
		txtNombreError.setText("");

		StringBuilder detallePlano = new StringBuilder();

		List<ErrorDeLectador> erroresEnLinea = consola.errores_de_lectadores.stream()
				.filter(err -> err.obtenerLinea() == numeroLinea).collect(Collectors.toList());

		if (!erroresEnLinea.isEmpty()) {
			// Izquierda: nombres separados por salto de línea
			StringBuilder nombres = new StringBuilder();

			for (ErrorDeLectador err : erroresEnLinea) {
				if (nombres.length() > 0)
					nombres.append("\n");
				nombres.append(err.verificacion.nombre());

				String tituloPlano = htmlAPlano(err.verificacion.nombre());
				String mensaje = err.verificacion.mensaje();
				String mensajePlano = htmlAPlano(mensaje);

				// Si parece stacktrace, lo dejamos tal cual; si no, lo mostramos como bloques
				// con línea separadora
				detallePlano.append(tituloPlano).append("\n");
				detallePlano.append(mensajePlano).append("\n");
				detallePlano.append("--------------------------------------------------").append("\n");
			}

			txtNombreError.setText(nombres.toString());
			descripcionHtml(detallePlano.toString());
		} else {
			if (numeroLinea >= 0 && numeroLinea < modeloRegistros.size()) {
				String textoLinea = modeloRegistros.get(numeroLinea);
				if (textoLinea.contains("ERROR") || textoLinea.contains("EXCEPTION")) {
					txtNombreError.setText(idioma.obtenerNombreErrorPorDefecto());
					String porDefectoPlano = htmlAPlano(idioma.obtenerDescripcionErrorPorDefecto());
					descripcionHtml(porDefectoPlano);
				} else {
					txtNombreError.setText("");
					descripcionHtml("");
				}
			} else {
				txtNombreError.setText("");
				descripcionHtml("");
			}
		}
	}

//Convierte una cadena con HTML a texto plano: quita todas las etiquetas y normaliza saltos de línea
	private static String htmlAPlano(String s) {
		if (s == null)
			return "";
		String r = s;
		// Reemplazar algunos tags de bloque por saltos de línea para mantener
		// legibilidad
		r = r.replaceAll("(?is)<br\\s*/?>", "\n");
		r = r.replaceAll("(?is)</p\\s*>", "\n");
		r = r.replaceAll("(?is)</div\\s*>", "\n");
		r = r.replaceAll("(?is)</li\\s*>", "\n");
		r = r.replaceAll("(?is)</tr\\s*>", "\n");

		// Quitar todas las etiquetas que queden
		r = r.replaceAll("(?is)<[^>]+>", "");

		// Deshacer algunas entidades comunes
		r = r.replace("&nbsp;", " ");
		r = r.replace("&lt;", "<").replace("&gt;", ">");
		r = r.replace("&quot;", "\"").replace("&#39;", "'");
		r = r.replace("&amp;", "&");

		// Normalizar múltiples saltos de línea consecutivos
		r = r.replaceAll("\\n{3,}", "\n\n");
		return r.trim();
	}

	private static String escHtml(String s) {
		if (s == null)
			return "";
		String r = s;
		r = r.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'",
				"&#39;");
		return r;
	}

	private void descripcionHtml(String textoPlano) {
		String safe = (textoPlano == null ? "" : escHtml(textoPlano));
		String html = "<html><body bgcolor='#111111' text='#FFFFFF'><pre>" + safe + "</pre></body></html>";
		txtDescripcionError.setText(html);
		txtDescripcionError.setCaretPosition(0);
	}

	@Override
	public void init() {
		setVisible(true);
	}

	@Override
	public String etiquetaDelBoton() {
		return idioma.obtenerEtiquetaBotonLectador();
	}

	public static class ErrorDeLectador {
		public Consola consola;
		public int numero_de_linea;
		public Verificaciones verificacion;

		public ErrorDeLectador(Consola consola, int numero_de_linea, Verificaciones verificacion) {
			this.consola = consola;
			this.numero_de_linea = numero_de_linea;
			this.verificacion = verificacion;
		}

		public Color obtenerColor() {
			return verificacion.nivel_de_criticalidad().color;
		}

		@Override
		public String toString() {
			return "lectador://" + consola.archivo.toString() + ":" + String.valueOf(numero_de_linea);
		}

		public int obtenerLinea() {
			return numero_de_linea;
		}
	}
}
