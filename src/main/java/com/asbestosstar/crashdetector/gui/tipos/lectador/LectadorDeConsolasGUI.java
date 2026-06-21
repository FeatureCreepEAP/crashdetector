package com.asbestosstar.crashdetector.gui.tipos.lectador;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.elementos.VisorLogVirtual;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class LectadorDeConsolasGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	public static final long serialVersionUID = 1L;

	// ====== Registro de GUIs por tipo ======

	public static Map<String, java.util.function.Supplier<LectadorDeConsolasGUI>> GUIS = new java.util.HashMap<String, java.util.function.Supplier<LectadorDeConsolasGUI>>();

	// ====== Caché y carga diferida ======

	/** Índice/cargador lazy por archivo de consola. */
	public final Map<String, CargadorDeLogDiferido> cacheCargadoresPorConsola = new ConcurrentHashMap<String, CargadorDeLogDiferido>();

	/** Errores indexados por línea para evitar buscar durante cada pintado. */
	public final Map<Integer, ErrorDeLectador> erroresPorLinea = new ConcurrentHashMap<Integer, ErrorDeLectador>();

	/** Última consola seleccionada. */
	public Consola consolaActual;

	// ====== Datos base ======

	/** Lista de consolas disponible desde MonitorDePID. */
	public final List<Consola> consolas = MonitorDePID.consolas;

	/**
	 * Vista lógica vieja.
	 *
	 * Se conserva por compatibilidad, pero ya no se usa para pintar el log
	 * completo.
	 */
	public List<String> lineasActuales = java.util.Collections.emptyList();

	// ====== Hilos ======

	public final int CORES = Runtime.getRuntime().availableProcessors();
	public final int N_HILOS = Math.min(4, Math.max(2, CORES - 1));
	public final int CAPACIDAD_COLA = N_HILOS * 4;

	public final ThreadPoolExecutor pool = new ThreadPoolExecutor(N_HILOS, N_HILOS, 30L, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(CAPACIDAD_COLA), new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r, "LectadorPool-" + System.nanoTime());
					t.setDaemon(true);
					return t;
				}
			}, new ThreadPoolExecutor.CallerRunsPolicy());

	{
		pool.allowCoreThreadTimeOut(true);
	}

	// ====== Estado/UI técnico ======

	public final VisorLogVirtual visorRegistros = new VisorLogVirtual();

	public final JComboBox<String> cmbConsolas = new JComboBox<String>();
	public final JTextArea txtNombreError = new JTextArea();

	// ====== Colores configurables ======

	public ConfigColor colorFondo;
	public ConfigColor colorTexto;
	public ConfigColor colorError;
	public ConfigColor colorPila;
	public ConfigColor colorFondoPanel;
	public ConfigColor colorTextoPanel;
	public ConfigColor colorTextoNegro;

	/** Texto NO localizado. La implementación concreta lo define. */
	protected abstract String textoNormalLeyenda();

	// ====== Contenedores ======

	public JScrollPane scrollLogs;
	public JPanel pnlInferior;
	public JPanel pnlLeyenda;
	public JComponent pnlSelector;

	// ====== Buscador ======

	public final JTextField txtBuscar = new JTextField();
	public final java.util.List<Integer> posicionesCoincidencias = new java.util.ArrayList<Integer>();
	public int indiceBusquedaActual = -1;

	public final JComboBox<String> cmbModo = new JComboBox<String>(
			new String[] { MonitorDePID.idioma.limpiado(), MonitorDePID.idioma.original() });

	public final javax.swing.JButton btnBuscarAnterior = new javax.swing.JButton("↑");
	public final javax.swing.JButton btnBuscarSiguiente = new javax.swing.JButton("↓");

	public JPanel pnlBuscador;

	// ====== Descripción HTML ======

	public final JEditorPane txtDescripcionError = new JEditorPane();
	public JScrollPane scrollDescripcion;

	/**
	 * Línea destino pendiente para aperturas directas desde enlaces lectador://.
	 *
	 * Si es null, la apertura normal empieza arriba. Si tiene valor, la carga
	 * inicial prioriza esa zona y evita el salto visual desde la línea 0.
	 */
	public Integer lineaDestinoPendiente = null;

	public LectadorDeConsolasGUI() {
		super();
	}

	public abstract void procesarHipervinculo(String url);

	// ====== Construcción ======

	public void configurarVentanaBase() {
		setTitle(MonitorDePID.idioma.tituloLectador());
		setSize(1280, 720);
		setLocationRelativeTo(null);

		final JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);
		setContentPane(layeredPane);

		instalarFondoApariencia(layeredPane);

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

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				recolocarComponentes();
				repaint();
			}
		});
	}

	public void instalarFondoApariencia(JLayeredPane capa) {
		// Apariencia opcional en la implementación concreta.
	}

	public void inicializarComponentesBase() {
		final JLayeredPane capa = (JLayeredPane) getContentPane();

		configurarAreaRegistros();

		scrollLogs = new JScrollPane(visorRegistros);
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
		instalarEscuchaDeScrollParaCargaDiferida();

		recolocarComponentes();
	}

	public void instalarEscuchaDeScrollParaCargaDiferida() {
		if (scrollLogs == null) {
			return;
		}

		scrollLogs.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
				priorizarZonaVisibleActual();
			}
		});
	}

	public void priorizarZonaVisibleActual() {
		if (visorRegistros == null || consolaActual == null) {
			return;
		}

		CargadorDeLogDiferido cargador = obtenerCargadorDeConsolaActual();

		if (cargador == null) {
			return;
		}

		int indiceInicio = visorRegistros.obtenerPrimeraLineaVisible();
		int indiceFin = visorRegistros.obtenerUltimaLineaVisible();

		if (indiceInicio < 0 || indiceFin < 0) {
			return;
		}

		cargador.cargarRangoVisible(indiceInicio, indiceFin);
	}

	public CargadorDeLogDiferido obtenerCargadorDeConsolaActual() {
		if (consolaActual == null || consolaActual.archivo == null) {
			return null;
		}

		String claveArchivo = consolaActual.archivo.toString();
		return cacheCargadoresPorConsola.get(claveArchivo);
	}

	// ====== Colocación ======

	public void recolocarComponentes() {
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

		recolocarBuscador();

		if (scrollLogs != null) {
			scrollLogs.revalidate();
			scrollLogs.repaint();
		}

		revalidate();
		repaint();
	}

	public void recolocarBuscador() {
		int x;
		int y;
		int w;
		int h = 40;

		if (pnlSelector != null) {
			int margen = 8;
			x = pnlSelector.getX();
			y = pnlSelector.getY() + pnlSelector.getHeight() + margen;
			w = pnlSelector.getWidth();
		} else {
			w = 260;
			x = getWidth() - w - 30;
			y = 30;
		}

		if (pnlBuscador != null) {
			pnlBuscador.setBounds(x, y, w, h);
			pnlBuscador.revalidate();
			pnlBuscador.repaint();
		}
	}

	// ====== Subpaneles ======

	public JPanel crearPanelLeyenda() {
		JPanel pnl = new JPanel();
		pnl.setLayout(new javax.swing.BoxLayout(pnl, javax.swing.BoxLayout.Y_AXIS));
		pnl.setBackground(colorFondoPanel.obtener());
		pnl.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.obtenerTituloLeyenda()));

		java.util.Set<java.awt.Color> coloresMostrados = new java.util.HashSet<java.awt.Color>();

		for (Consola consola : consolas) {
			for (ErrorDeLectador err : consola.errores_de_lectadores) {
				java.awt.Color c = err.obtenerColor();

				if (!coloresMostrados.contains(c)) {
					javax.swing.JLabel lbl = new javax.swing.JLabel(err.verificacion.nivel_de_criticalidad().nombre);
					lbl.setOpaque(true);
					lbl.setBackground(c);
					lbl.setForeground(colorTextoNegro.obtener());
					lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

					pnl.add(lbl);
					pnl.add(javax.swing.Box.createVerticalStrut(8));

					coloresMostrados.add(c);
				}
			}
		}

		javax.swing.JLabel lblPila = new javax.swing.JLabel(MonitorDePID.idioma.obtenerStacktraceEnLeyenda());
		lblPila.setOpaque(true);
		lblPila.setBackground(colorPila.obtener());
		lblPila.setForeground(colorTextoNegro.obtener());
		lblPila.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl.add(lblPila);

		javax.swing.JLabel lblNormal = new javax.swing.JLabel(textoNormalLeyenda());
		lblNormal.setOpaque(true);
		lblNormal.setBackground(colorTextoPanel.obtener());
		lblNormal.setForeground(colorTextoNegro.obtener());
		lblNormal.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl.add(lblNormal);

		return pnl;
	}

	public void configurarAreaRegistros() {
		visorRegistros.setFont(getFont());

		visorRegistros.colorFondo = colorFondo.obtener();
		visorRegistros.colorTexto = colorTexto.obtener();
		visorRegistros.colorError = colorError.obtener();
		visorRegistros.colorPila = colorPila.obtener();
		visorRegistros.colorTextoSobreColor = colorTextoNegro.obtener();
		visorRegistros.colorAdvertencia = Config.convertirAColor(Config.obtenerInstancia().obtenerColorAdvertencia());
		visorRegistros.oyenteDeLinea = new VisorLogVirtual.OyenteDeLinea() {
			@Override
			public void lineaSeleccionada(int linea) {
				Consola consola = obtenerConsolaSeleccionada();

				if (consola != null) {
					procesarSeleccionError(linea, consola);
				}
			}
		};
	}

	public void reconstruirIndiceDeErrores(Consola consola) {
		erroresPorLinea.clear();

		if (consola == null || consola.errores_de_lectadores == null) {
			return;
		}

		for (ErrorDeLectador err : consola.errores_de_lectadores) {
			if (err != null) {
				erroresPorLinea.put(Integer.valueOf(err.obtenerLinea()), err);
			}
		}

		visorRegistros.establecerErrores(erroresPorLinea);
	}

	public JPanel crearPanelInformacionErrores() {
		JPanel pnl = new JPanel(new GridLayout(1, 2, 42, 10));

		txtNombreError.setEditable(false);
		txtNombreError.setBackground(colorFondo.obtener());
		txtNombreError.setForeground(colorTexto.obtener());
		pnl.add(txtNombreError);

		txtDescripcionError.setEditable(false);
		txtDescripcionError.setContentType("text/html");
		txtDescripcionError.setOpaque(true);
		txtDescripcionError.setBackground(colorFondo.obtener());
		txtDescripcionError.setForeground(colorTexto.obtener());

		String fondoHex = String.format("#%02x%02x%02x", colorFondo.obtener().getRed(), colorFondo.obtener().getGreen(),
				colorFondo.obtener().getBlue());

		String textoHex = String.format("#%02x%02x%02x", colorTexto.obtener().getRed(), colorTexto.obtener().getGreen(),
				colorTexto.obtener().getBlue());

		txtDescripcionError.setText("<html><body bgcolor='" + fondoHex + "' text='" + textoHex + "'></body></html>");

		scrollDescripcion = new JScrollPane(txtDescripcionError, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		pnl.add(scrollDescripcion);

		return pnl;
	}

	public JComponent crearPanelSelector() {
		JPanel pnl = new JPanel(new GridLayout(2, 1, 0, 5));
		pnl.setBackground(colorFondoPanel.obtener());

		pnl.add(cmbConsolas);
		pnl.add(cmbModo);

		cmbConsolas.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarConsola();
			}
		});

		cmbModo.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarConsola();
			}
		});

		return pnl;
	}

	public void inicializarBuscador(JLayeredPane capa) {
		pnlBuscador = new JPanel(new java.awt.BorderLayout(4, 0));
		pnlBuscador.setVisible(false);
		pnlBuscador.setOpaque(true);
		pnlBuscador.setBackground(colorFondo.obtener());
		pnlBuscador.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.buscar()));

		txtBuscar.setBackground(colorFondo.obtener());
		txtBuscar.setForeground(colorTexto.obtener());
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 4, 2, 4));

		JPanel pnlBotones = new JPanel(new java.awt.GridLayout(1, 2, 2, 0));
		pnlBotones.setOpaque(false);

		btnBuscarAnterior.setFocusable(false);
		btnBuscarSiguiente.setFocusable(false);

		pnlBotones.add(btnBuscarAnterior);
		pnlBotones.add(btnBuscarSiguiente);

		pnlBuscador.add(txtBuscar, java.awt.BorderLayout.CENTER);
		pnlBuscador.add(pnlBotones, java.awt.BorderLayout.EAST);

		capa.add(pnlBuscador, JLayeredPane.DRAG_LAYER);

		txtBuscar.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saltarSiguienteCoincidencia();
			}
		});

		btnBuscarAnterior.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saltarCoincidenciaAnterior();
			}
		});

		btnBuscarSiguiente.addActionListener(new java.awt.event.ActionListener() {
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
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				pnlBuscador.setVisible(true);
				recolocarBuscador();
				txtBuscar.requestFocus();
				txtBuscar.selectAll();
			}
		});
	}

	// ====== Consolas ======

	public void cargarConsolas() {
		cmbConsolas.removeAllItems();

		for (Consola consola : consolas) {
			String nombreArchivo = new File(consola.archivo.toString()).getName();
			cmbConsolas.addItem(nombreArchivo);
		}

		if (cmbConsolas.getItemCount() > 0) {
			cmbConsolas.setSelectedIndex(0);
		}
	}

	public void actualizarConsola() {
		final Consola consola = obtenerConsolaSeleccionada();

		if (consola == null) {
			return;
		}

		consolaActual = consola;
		reconstruirIndiceDeErrores(consola);

		final String claveArchivo = consola.archivo.toString();

		CargadorDeLogDiferido cargador = cacheCargadoresPorConsola.get(claveArchivo);

		if (cargador == null) {
			cargador = new CargadorDeLogDiferido(consola.contenido_verificar, 500);
			cacheCargadoresPorConsola.put(claveArchivo, cargador);
		}

		refrescarModeloCon(cargador);

		setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

		final Integer destinoPendiente = lineaDestinoPendiente;

		if (destinoPendiente != null) {
			final int destino = Math.max(0,
					Math.min(destinoPendiente.intValue(), Math.max(0, cargador.totalLineas() - 1)));

			cargador.asegurarChunkDeLineaSincrono(destino);
			cargador.cargarZonaPrioritaria(destino);

			int tamanoChunk = cargador.obtenerTamanoChunk();
			int indiceChunk = destino / tamanoChunk;
			int inicio = indiceChunk * tamanoChunk;
			int fin = Math.min(cargador.totalLineas() - 1, inicio + tamanoChunk - 1);

			visorRegistros.repintarRangoLineas(inicio, fin);

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
					visorRegistros.seleccionarLinea(destino, true);
					visorRegistros.requestFocus();
					priorizarZonaVisibleActual();
				}
			});

			lineaDestinoPendiente = null;
			return;
		}

		cargador.cargarZonaPrioritaria(0);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
				visorRegistros.seleccionarLinea(0, false);
				priorizarZonaVisibleActual();
			}
		});
	}

	public void setLoadingModel(final String mensaje) {
		visorRegistros.establecerFuente(new VisorLogVirtual.FuenteDeLineas() {
			@Override
			public int totalLineas() {
				return 1;
			}

			@Override
			public String obtenerLinea(int indice) {
				return mensaje == null ? "" : mensaje;
			}

			@Override
			public String obtenerLineaSincrona(int indice) {
				return mensaje == null ? "" : mensaje;
			}
		});

		visorRegistros.establecerErrores(null);
		visorRegistros.repaint();
	}

	public Consola obtenerConsolaSeleccionada() {
		String nombreArchivo = (String) cmbConsolas.getSelectedItem();

		if (nombreArchivo == null) {
			return null;
		}

		for (Consola c : consolas) {
			if (new File(c.archivo.toString()).getName().equals(nombreArchivo)) {
				return c;
			}
		}

		return null;
	}

	public void refrescarModeloCon(final CargadorDeLogDiferido cargador) {
		visorRegistros.establecerFuente(new VisorLogVirtual.FuenteDeLineas() {
			@Override
			public int totalLineas() {
				return cargador == null ? 0 : cargador.totalLineas();
			}

			@Override
			public String obtenerLinea(int indice) {
				return cargador == null ? "" : cargador.obtenerLinea(indice);
			}

			@Override
			public String obtenerLineaSincrona(int indice) {
				return cargador == null ? "" : cargador.obtenerLineaSincrona(indice);
			}
		});

		visorRegistros.establecerErrores(erroresPorLinea);

		posicionesCoincidencias.clear();
		indiceBusquedaActual = -1;

		lineasActuales = java.util.Collections.emptyList();
	}

	// ====== Búsqueda ======

	public void saltarCoincidenciaAnterior() {
		String texto = txtBuscar.getText();

		if (texto == null || texto.isEmpty()) {
			return;
		}

		if (posicionesCoincidencias.isEmpty()) {
			buscarTexto(texto);
			return;
		}

		indiceBusquedaActual--;

		if (indiceBusquedaActual < 0) {
			indiceBusquedaActual = posicionesCoincidencias.size() - 1;
		}

		resaltarCoincidenciaActual();
	}

	public void saltarSiguienteCoincidencia() {
		String texto = txtBuscar.getText();

		if (texto == null || texto.isEmpty()) {
			return;
		}

		if (posicionesCoincidencias.isEmpty()) {
			buscarTexto(texto);
			return;
		}

		indiceBusquedaActual++;

		if (indiceBusquedaActual >= posicionesCoincidencias.size()) {
			indiceBusquedaActual = 0;
		}

		resaltarCoincidenciaActual();
	}

	public void buscarTexto(final String texto) {
		posicionesCoincidencias.clear();
		indiceBusquedaActual = -1;

		if (texto == null || texto.isEmpty()) {
			return;
		}

		final CargadorDeLogDiferido cargador = obtenerCargadorDeConsolaActual();

		if (cargador == null) {
			return;
		}

		final String textoBusqueda = texto.toLowerCase();

		setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

		pool.submit(new Runnable() {
			@Override
			public void run() {
				try {
					final java.util.List<Integer> coincidenciasEncontradas = new java.util.ArrayList<Integer>();

					for (int i = 0; i < cargador.totalLineas(); i++) {
						String linea = cargador.obtenerLineaSincrona(i);

						if (linea != null && linea.toLowerCase().contains(textoBusqueda)) {
							coincidenciasEncontradas.add(Integer.valueOf(i));
						}
					}

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							posicionesCoincidencias.clear();
							posicionesCoincidencias.addAll(coincidenciasEncontradas);

							if (!posicionesCoincidencias.isEmpty()) {
								indiceBusquedaActual = 0;
								resaltarCoincidenciaActual();
							} else {
								indiceBusquedaActual = -1;
							}

							visorRegistros.repaint();
							setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
						}
					});
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
						}
					});
				}
			}
		});
	}

	public void resaltarCoincidenciaActual() {
		if (indiceBusquedaActual < 0 || indiceBusquedaActual >= posicionesCoincidencias.size()) {
			if (pnlBuscador != null) {
				pnlBuscador.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.buscar()));
				pnlBuscador.repaint();
			}

			return;
		}

		int pos = posicionesCoincidencias.get(indiceBusquedaActual).intValue();

		CargadorDeLogDiferido cargador = obtenerCargadorDeConsolaActual();

		if (cargador != null) {
			cargador.asegurarChunkDeLineaSincrono(pos);
			cargador.cargarZonaPrioritaria(pos);
		}

		visorRegistros.seleccionarLinea(pos, true);

		if (pnlBuscador != null) {
			String titulo = MonitorDePID.idioma.buscar() + " (" + (indiceBusquedaActual + 1) + "/"
					+ posicionesCoincidencias.size() + ")";

			pnlBuscador.setBorder(BorderFactory.createTitledBorder(titulo));
			pnlBuscador.repaint();
		}
	}

	// ====== HTML / descripción de error ======

	public static String htmlAPlano(String s) {
		if (s == null) {
			return "";
		}

		String r = s;

		r = r.replaceAll("(?is)<br\\s*/?>", "\n");
		r = r.replaceAll("(?is)</p\\s*>", "\n");
		r = r.replaceAll("(?is)</div\\s*>", "\n");
		r = r.replaceAll("(?is)</li\\s*>", "\n");
		r = r.replaceAll("(?is)</tr\\s*>", "\n");
		r = r.replaceAll("(?is)<[^>]+>", "");

		r = r.replace("&nbsp;", " ");
		r = r.replace("&lt;", "<");
		r = r.replace("&gt;", ">");
		r = r.replace("&quot;", "\"");
		r = r.replace("&#39;", "'");
		r = r.replace("&amp;", "&");

		r = r.replaceAll("\\n{3,}", "\n\n");

		return r.trim();
	}

	public static String escHtml(String s) {
		if (s == null) {
			return "";
		}

		String r = s;

		r = r.replace("&", "&amp;");
		r = r.replace("<", "&lt;");
		r = r.replace(">", "&gt;");
		r = r.replace("\"", "&quot;");
		r = r.replace("'", "&#39;");

		return r;
	}

	public void descripcionHtml(String textoPlano) {
		String safe = textoPlano == null ? "" : escHtml(textoPlano);
		String html = "<html><body bgcolor='#111111' text='#FFFFFF'><pre>" + safe + "</pre></body></html>";

		txtDescripcionError.setText(html);
		txtDescripcionError.setCaretPosition(0);
	}

	public void procesarSeleccionError(int numeroLinea, Consola consola) {
		txtNombreError.setText("");

		StringBuilder detallePlano = new StringBuilder();

		ErrorDeLectador errorPrincipal = erroresPorLinea.get(Integer.valueOf(numeroLinea));

		if (errorPrincipal != null) {
			StringBuilder nombres = new StringBuilder();

			nombres.append(errorPrincipal.verificacion.nombre());

			String tituloPlano = htmlAPlano(errorPrincipal.verificacion.nombre());
			String mensajePlano = htmlAPlano(errorPrincipal.verificacion.mensaje());

			detallePlano.append(tituloPlano).append("\n");
			detallePlano.append(mensajePlano).append("\n");
			detallePlano.append("--------------------------------------------------").append("\n");

			if (consola != null && consola.errores_de_lectadores != null) {
				for (ErrorDeLectador err : consola.errores_de_lectadores) {
					if (err == null || err == errorPrincipal) {
						continue;
					}

					if (err.obtenerLinea() == numeroLinea) {
						nombres.append("\n").append(err.verificacion.nombre());

						String tituloPlanoExtra = htmlAPlano(err.verificacion.nombre());
						String mensajePlanoExtra = htmlAPlano(err.verificacion.mensaje());

						detallePlano.append(tituloPlanoExtra).append("\n");
						detallePlano.append(mensajePlanoExtra).append("\n");
						detallePlano.append("--------------------------------------------------").append("\n");
					}
				}
			}

			txtNombreError.setText(nombres.toString());
			descripcionHtml(detallePlano.toString());
			return;
		}

		CargadorDeLogDiferido cargador = obtenerCargadorDeConsolaActual();
		String textoLinea = "";

		if (cargador != null && numeroLinea >= 0 && numeroLinea < cargador.totalLineas()) {
			textoLinea = cargador.obtenerLineaSincrona(numeroLinea);
		}

		if (textoLinea != null && (textoLinea.contains("ERROR") || textoLinea.contains("EXCEPTION"))) {
			txtNombreError.setText(MonitorDePID.idioma.obtenerNombreErrorPorDefecto());

			String porDefectoPlano = htmlAPlano(MonitorDePID.idioma.obtenerDescripcionErrorPorDefecto());
			descripcionHtml(porDefectoPlano);
		} else {
			txtNombreError.setText("");
			descripcionHtml("");
		}
	}

	// ====== Saltos directos ======

	public void saltarDirectamenteALinea(final int numeroLinea) {
		final CargadorDeLogDiferido cargador = obtenerCargadorDeConsolaActual();

		if (cargador == null) {
			return;
		}

		final int destino = Math.max(0, Math.min(numeroLinea, Math.max(0, cargador.totalLineas() - 1)));

		cargador.asegurarChunkDeLineaSincrono(destino);
		cargador.cargarZonaPrioritaria(destino);

		int tamanoChunk = cargador.obtenerTamanoChunk();
		int indiceChunk = destino / tamanoChunk;
		int inicio = indiceChunk * tamanoChunk;
		int fin = Math.min(cargador.totalLineas() - 1, inicio + tamanoChunk - 1);

		visorRegistros.repintarRangoLineas(inicio, fin);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				visorRegistros.seleccionarLinea(destino, true);
				visorRegistros.requestFocus();
			}
		});
	}

	// ====== CrashDetectorGUI ======

	@Override
	public TipoGUI tipo() {
		return TipoGUI.LECTADOR_DE_CONSOLAS;
	}

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
	}

	public abstract void aplicarApariencia();

	@Override
	public void init() {
		configurarVentanaBase();
		inicializarComponentesBase();
		cargarConsolas();

		setVisible(true);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					actualizarConsola();
				} catch (Exception ex) {
					CrashDetectorLogger.logException(ex);
				}
			}
		});
	}

	// ====== Error de lectador ======

	public static class ErrorDeLectador {
		public Consola consola;
		public int numero_de_linea;
		public Verificaciones verificacion;

		public ErrorDeLectador(Consola consola, int numero_de_linea, Verificaciones verificacion) {
			this.consola = consola;
			this.numero_de_linea = numero_de_linea;
			this.verificacion = verificacion;
		}

		public java.awt.Color obtenerColor() {
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

	// ====== Cargador diferido ======

	public class CargadorDeLogDiferido {

		public final String contenido;
		public final int tamanoChunk;

		public final java.util.List<Integer> offsetsDeLinea = new java.util.ArrayList<Integer>();

		public final Map<Integer, java.util.List<String>> cacheChunks = new ConcurrentHashMap<Integer, java.util.List<String>>();

		public final java.util.Set<Integer> chunksEnCarga = java.util.Collections
				.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

		public volatile boolean rellenoGeneralSolicitado = false;

		public CargadorDeLogDiferido(String contenido, int tamanoChunk) {
			this.contenido = contenido == null ? "" : contenido;
			this.tamanoChunk = Math.max(100, tamanoChunk);

			construirIndiceDeLineas();
		}

		public void construirIndiceDeLineas() {
			offsetsDeLinea.clear();
			offsetsDeLinea.add(Integer.valueOf(0));

			for (int i = 0; i < contenido.length(); i++) {
				char c = contenido.charAt(i);

				if (c == '\n') {
					if (i + 1 < contenido.length()) {
						offsetsDeLinea.add(Integer.valueOf(i + 1));
					}
				}
			}
		}

		public int totalLineas() {
			return offsetsDeLinea.size();
		}

		public int totalChunks() {
			return (int) Math.ceil((double) totalLineas() / (double) tamanoChunk);
		}

		public int obtenerTamanoChunk() {
			return tamanoChunk;
		}

		public String obtenerLinea(int indiceLinea) {
			if (indiceLinea < 0 || indiceLinea >= totalLineas()) {
				return "";
			}

			int chunk = indiceLinea / tamanoChunk;

			java.util.List<String> lineasChunk = cacheChunks.get(Integer.valueOf(chunk));

			if (lineasChunk == null) {
				cargarChunkAsincrono(chunk, true);
				return MonitorDePID.idioma.cargando();
			}

			int indiceLocal = indiceLinea % tamanoChunk;

			if (indiceLocal >= 0 && indiceLocal < lineasChunk.size()) {
				return lineasChunk.get(indiceLocal);
			}

			return "";
		}

		public String obtenerLineaSincrona(int indiceLinea) {
			if (indiceLinea < 0 || indiceLinea >= totalLineas()) {
				return "";
			}

			int chunk = indiceLinea / tamanoChunk;

			java.util.List<String> lineasChunk = cacheChunks.get(Integer.valueOf(chunk));

			if (lineasChunk == null) {
				lineasChunk = construirChunk(chunk);
				cacheChunks.put(Integer.valueOf(chunk), lineasChunk);
			}

			int indiceLocal = indiceLinea % tamanoChunk;

			if (indiceLocal >= 0 && indiceLocal < lineasChunk.size()) {
				return lineasChunk.get(indiceLocal);
			}

			return "";
		}

		public void cargarZonaPrioritaria(final int lineaPrioritaria) {
			if (totalLineas() <= 0) {
				return;
			}

			final int chunkCentral = Math.max(0, Math.min(totalChunks() - 1, lineaPrioritaria / tamanoChunk));

			cargarChunkAsincrono(chunkCentral, true);
			cargarChunkAsincrono(chunkCentral - 1, false);
			cargarChunkAsincrono(chunkCentral + 1, false);

			if (rellenoGeneralSolicitado) {
				return;
			}

			rellenoGeneralSolicitado = true;

			pool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						for (int distancia = 2; distancia < totalChunks(); distancia++) {
							int izq = chunkCentral - distancia;
							int der = chunkCentral + distancia;

							if (izq >= 0) {
								cargarChunkAsincrono(izq, false);
							}

							if (der < totalChunks()) {
								cargarChunkAsincrono(der, false);
							}
						}
					} catch (Throwable t) {
						CrashDetectorLogger.logException(t);
					}
				}
			});
		}

		public void cargarRangoVisible(int lineaInicio, int lineaFin) {
			if (lineaInicio < 0 || lineaFin < 0 || lineaInicio > lineaFin) {
				return;
			}

			int chunkInicio = Math.max(0, lineaInicio / tamanoChunk);
			int chunkFin = Math.min(totalChunks() - 1, lineaFin / tamanoChunk);

			for (int chunk = chunkInicio; chunk <= chunkFin; chunk++) {
				cargarChunkAsincrono(chunk, true);
			}

			cargarChunkAsincrono(chunkInicio - 1, false);
			cargarChunkAsincrono(chunkFin + 1, false);
		}

		public java.util.List<String> construirChunk(int indiceChunk) {
			if (indiceChunk < 0 || indiceChunk >= totalChunks()) {
				return java.util.Collections.emptyList();
			}

			int lineaInicio = indiceChunk * tamanoChunk;
			int lineaFin = Math.min(totalLineas(), lineaInicio + tamanoChunk);

			java.util.List<String> resultado = new java.util.ArrayList<String>(lineaFin - lineaInicio);

			for (int i = lineaInicio; i < lineaFin; i++) {
				int inicio = offsetsDeLinea.get(i).intValue();
				int fin;

				if (i + 1 < offsetsDeLinea.size()) {
					fin = offsetsDeLinea.get(i + 1).intValue();
				} else {
					fin = contenido.length();
				}

				while (fin > inicio) {
					char c = contenido.charAt(fin - 1);

					if (c == '\n' || c == '\r') {
						fin--;
					} else {
						break;
					}
				}

				resultado.add(contenido.substring(inicio, fin));
			}

			return resultado;
		}

		public void cargarChunkAsincrono(final int indiceChunk, final boolean refrescarUIAlFinal) {
			if (indiceChunk < 0 || indiceChunk >= totalChunks()) {
				return;
			}

			if (cacheChunks.containsKey(Integer.valueOf(indiceChunk))) {
				return;
			}

			if (!chunksEnCarga.add(Integer.valueOf(indiceChunk))) {
				return;
			}

			pool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						java.util.List<String> chunk = construirChunk(indiceChunk);
						cacheChunks.put(Integer.valueOf(indiceChunk), chunk);

						if (refrescarUIAlFinal) {
							SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									int inicio = indiceChunk * tamanoChunk;
									int fin = Math.min(totalLineas() - 1, inicio + tamanoChunk - 1);

									visorRegistros.repintarRangoLineas(inicio, fin);
									visorRegistros.revalidate();
								}
							});
						}
					} catch (Throwable t) {
						CrashDetectorLogger.logException(t);
					} finally {
						chunksEnCarga.remove(Integer.valueOf(indiceChunk));
					}
				}
			});
		}

		public void asegurarChunkDeLineaSincrono(int indiceLinea) {
			if (indiceLinea < 0 || indiceLinea >= totalLineas()) {
				return;
			}

			int chunk = indiceLinea / tamanoChunk;

			if (cacheChunks.containsKey(Integer.valueOf(chunk))) {
				return;
			}

			java.util.List<String> lineasChunk = construirChunk(chunk);
			cacheChunks.put(Integer.valueOf(chunk), lineasChunk);
		}
	}
}