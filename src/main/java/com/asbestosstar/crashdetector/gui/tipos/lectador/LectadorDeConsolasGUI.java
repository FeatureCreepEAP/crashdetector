package com.asbestosstar.crashdetector.gui.tipos.lectador;

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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.NumeradorDeLineas;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Clase abstracta para el Lectador de Consolas.
 * 
 * OBJETIVO: Centralizar la lógica técnica (carga/caché de líneas, pool de
 * hilos, búsqueda, renderizado según colores semánticos), así como la
 * estructura base y el wiring de eventos. La implementación concreta debe
 * encargarse de la APARIENCIA (colores, fondos, textos NO localizados).
 *
 * NOTA: Comentarios y codenames en español.
 */
public abstract class LectadorDeConsolasGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	// ====== Registro de GUIs por tipo (mantener API) ======
	public static Map<String, java.util.function.Supplier<LectadorDeConsolasGUI>> GUIS = new java.util.HashMap<String, java.util.function.Supplier<LectadorDeConsolasGUI>>();

	// ====== Datos base ======
	/** Lista de consolas disponible desde MonitorDePID */
	protected final List<Consola> consolas = MonitorDePID.consolas;

	/** Caché concurrente de líneas por archivo de consola */
	protected final Map<String, List<String>> cacheLineasPorConsola = new ConcurrentHashMap<String, List<String>>();

	/** Pool de hilos prudente */
	protected final int CORES = Runtime.getRuntime().availableProcessors();
	protected final int N_HILOS = Math.min(4, Math.max(2, CORES - 1));
	protected final int CAPACIDAD_COLA = N_HILOS * 4;

	protected final ThreadPoolExecutor pool=new ThreadPoolExecutor(N_HILOS,N_HILOS,30L,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(CAPACIDAD_COLA),new ThreadFactory(){@Override public Thread newThread(Runnable r){Thread t=new Thread(r,"LectadorPool-"+System.nanoTime());t.setDaemon(true);return t;}},new ThreadPoolExecutor.CallerRunsPolicy());

	{pool.allowCoreThreadTimeOut(true);}

	// ====== Estado/UI técnico ======
	protected List<String> lineasActuales = java.util.Collections.emptyList();
	protected final JList<String> listaRegistros = new JList<String>();

	protected final JComboBox<String> cmbConsolas = new JComboBox<String>();
	protected final JTextArea txtNombreError = new JTextArea();

	// Colores semánticos (la impl devuelve la paleta)
	protected abstract Color colorFondo();

	protected abstract Color colorTexto();

	protected abstract Color colorError();

	protected abstract Color colorPila();

	/** Texto NO localizado (la impl lo define) */
	protected abstract String textoNormalLeyenda();

	// Contenedores
	protected JScrollPane scrollLogs;
	protected JPanel pnlInferior;
	protected JPanel pnlLeyenda;
	protected JComponent pnlSelector;

	// Buscador
	protected final JTextField txtBuscar = new JTextField();
	protected final java.util.List<Integer> posicionesCoincidencias = new java.util.ArrayList<Integer>();
	protected int indiceBusquedaActual = -1;
	protected final JComboBox<String> cmbModo = new JComboBox<String>(
			new String[] { MonitorDePID.idioma.limpiado(), MonitorDePID.idioma.original() });

	// Descripción a la derecha (HTML)
	protected final JEditorPane txtDescripcionError = new JEditorPane(); // HTML
	protected JScrollPane scrollDescripcion;

	// ====== Construcción ======
	public LectadorDeConsolasGUI() {
		super(MonitorDePID.idioma.tituloLectador());

	}

	/**
	 * Configura ventana base y listeners de ciclo de vida. La impl puede añadir
	 * fondos/imagen sobrescribiendo instalarFondoApariencia().
	 */
	protected void configurarVentanaBase() {
		setSize(1280, 720);
		setLocationRelativeTo(null);

		final JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayout(null);
		setContentPane(layeredPane);

		instalarFondoApariencia(layeredPane); // Solo apariencia; impl decide

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

	/**
	 * Hook de apariencia: la implementación puede instalar un fondo (imagen/color)
	 * en el layeredPane. Por defecto, no hace nada.
	 */
	protected void instalarFondoApariencia(JLayeredPane capa) {
		// Apariencia opcional en la implementación
	}

	/**
	 * Construye y cablea los componentes técnicos. La impl se encarga de estilos
	 * via recargarApariencia().
	 */
	protected void inicializarComponentesBase() {
		final JLayeredPane capa = (JLayeredPane) getContentPane();

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
	}

	// ====== Colocación y redimensionado (técnico, relativa a 1280x720) ======
	protected void recolocarComponentes() {
		int anchoBase = 1280, altoBase = 720;
		int anchoActual = getWidth(), altoActual = getHeight();
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

	protected void recolocarBuscador() {
		if (pnlSelector != null) {
			int margen = 8;
			int x = pnlSelector.getX();
			int y = pnlSelector.getY() + pnlSelector.getHeight() + margen;
			int w = pnlSelector.getWidth();
			int h = 40;
			txtBuscar.setBounds(x, y, w, h);
		} else {
			int ancho = 200, alto = 40;
			txtBuscar.setBounds(getWidth() - ancho - 30, 30, ancho, alto);
		}
		txtBuscar.revalidate();
		txtBuscar.repaint();
	}

	// ====== Construcción técnica de subpaneles ======
	protected JPanel crearPanelLeyenda() {
		JPanel pnl = new JPanel();
		pnl.setLayout(new javax.swing.BoxLayout(pnl, javax.swing.BoxLayout.Y_AXIS));
		pnl.setBackground(colorFondo());
		pnl.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.obtenerTituloLeyenda()));

		java.util.Set<Color> coloresMostrados = new java.util.HashSet<Color>();

		for (Consola consola : consolas) {
			for (ErrorDeLectador err : consola.errores_de_lectadores) {
				Color c = err.obtenerColor();
				if (!coloresMostrados.contains(c)) {
					javax.swing.JLabel lbl = new javax.swing.JLabel(err.verificacion.nivel_de_criticalidad().nombre);
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

		javax.swing.JLabel lblPila = new javax.swing.JLabel(MonitorDePID.idioma.obtenerStacktraceEnLeyenda());
		lblPila.setOpaque(true);
		lblPila.setBackground(colorPila());
		lblPila.setForeground(Color.BLACK);
		lblPila.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl.add(lblPila);

		javax.swing.JLabel lblNormal = new javax.swing.JLabel(textoNormalLeyenda()); // NO localizado (hook)
		lblNormal.setOpaque(true);
		lblNormal.setBackground(colorTexto());
		lblNormal.setForeground(Color.BLACK);
		lblNormal.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnl.add(lblNormal);

		return pnl;
	}

	protected void configurarAreaRegistros() {
		listaRegistros.setBackground(colorFondo());
		listaRegistros.setForeground(colorTexto());
		listaRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaRegistros.setFixedCellHeight(16);

		listaRegistros.setCellRenderer(new javax.swing.ListCellRenderer<String>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
					boolean isSelected, boolean cellHasFocus) {
				javax.swing.JLabel lbl = new javax.swing.JLabel(value == null ? "" : value);
				lbl.setOpaque(true);
				lbl.setForeground(colorTexto());
				lbl.setBackground(colorFondo());

				Consola consola = obtenerConsolaSeleccionada();
				if (consola != null) {
					List<ErrorDeLectador> errores = consola.errores_de_lectadores.stream()
							.filter(err -> err.obtenerLinea() == index).collect(Collectors.<ErrorDeLectador>toList());

					if (!errores.isEmpty()) {
						lbl.setBackground(errores.get(0).obtenerColor());
						lbl.setForeground(Color.BLACK);
					} else if (value != null && (value.contains("ERROR") || value.contains("EXCEPTION"))) {
						lbl.setBackground(colorError());
						lbl.setForeground(Color.BLACK);
					} else if (value != null && (value.contains("STACKTRACE") || value.contains("at "))) {
						lbl.setBackground(colorPila());
						lbl.setForeground(Color.BLACK);
					}
				}
				if (isSelected) {
					lbl.setBackground(lbl.getBackground().darker());
				}
				return lbl;
			}
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

	protected JPanel crearPanelInformacionErrores() {
		JPanel pnl = new JPanel(new GridLayout(1, 2, 42, 10));

		// Izquierda: nombres de error en texto plano
		txtNombreError.setEditable(false);
		txtNombreError.setBackground(colorFondo());
		txtNombreError.setForeground(colorTexto());
		pnl.add(txtNombreError);

		// Derecha: visor HTML
		txtDescripcionError.setEditable(false);
		txtDescripcionError.setContentType("text/html");
		txtDescripcionError.setOpaque(true);
		txtDescripcionError.setBackground(colorFondo());
		txtDescripcionError.setForeground(colorTexto());
		txtDescripcionError.setText("<html><body bgcolor='#111111' text='#FFFFFF'></body></html>");

		scrollDescripcion = new JScrollPane(txtDescripcionError, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pnl.add(scrollDescripcion);
		return pnl;
	}

	protected JComponent crearPanelSelector() {
		JPanel pnl = new JPanel(new GridLayout(2, 1, 0, 5));
		pnl.setBackground(colorFondo());
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

	protected void inicializarBuscador(JLayeredPane capa) {
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

	// ====== Técnicas: carga/actualización ======
	protected void cargarConsolas() {
		CrashDetectorLogger.log("consolas carg " + String.valueOf(consolas.size()));
		for (Consola consola : consolas) {
			String nombreArchivo = new File(consola.archivo.toString()).getName();
			cmbConsolas.addItem(nombreArchivo);
			// Mantener log para diagnósticos
			CrashDetectorLogger.log(consola.contenido_verificar);
		}
		if (cmbConsolas.getItemCount() > 0) {
			cmbConsolas.setSelectedIndex(0);
			actualizarConsola();
		}
	}

	protected void precargarLineasEnSegundoPlano() {
		for (final Consola consola : consolas) {
			final String nombreArchivo = new File(consola.archivo.toString()).getName();
			if (cacheLineasPorConsola.containsKey(nombreArchivo))
				continue;

			pool.submit(new Runnable() {
				@Override
				public void run() {
					try {
						List<String> lineas = Arrays.asList(consola.contenido_verificar.split(Verificaciones.nl));
						CrashDetectorLogger
								.log(consola.archivo.toString() + " linea size " + String.valueOf(lineas.size()));
						if (lineas.size() == 1) {
							CrashDetectorLogger.log(String.valueOf(consola.contenido_verificar.length()));
							CrashDetectorLogger.log(consola.archivo.toString() + " linea 1 " + lineas.get(0));
						}
						cacheLineasPorConsola.put(nombreArchivo, lineas);
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								if (nombreArchivo.equals(cmbConsolas.getSelectedItem())) {
									refrescarModeloCon(cacheLineasPorConsola.get(nombreArchivo));
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

	protected void actualizarConsola() {
        final Consola consola = obtenerConsolaSeleccionada();
        if (consola == null) return;

        final String nombreArchivo = new File(consola.archivo.toString()).getName();

        List<String> cache = cacheLineasPorConsola.get(nombreArchivo);
        if (cache != null) {
            refrescarModeloCon(cache);
            return;
        }

        // Mostrar mensaje de carga sin bloquear EDT
        setLoadingModel("Cargando " + nombreArchivo + " ...");
        setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

        pool.submit(new Runnable() {
            @Override public void run() {
                try {
                    List<String> lineas = Arrays.asList(consola.contenido_verificar.split(Verificaciones.nl));
                    cacheLineasPorConsola.put(nombreArchivo, lineas);
                    final List<String> lineasFinal = lineas;

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override public void run() {
                            if (nombreArchivo.equals(cmbConsolas.getSelectedItem())) {
                                refrescarModeloCon(lineasFinal);
                            }
                            setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
                        }
                    });
                } catch (Throwable t) {
                    CrashDetectorLogger.logException(t);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override public void run() {
                            setLoadingModel("Error al cargar " + nombreArchivo + ": " + t.getMessage());
                            setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
                        }
                    });
                }

	}});}

	protected Consola obtenerConsolaSeleccionada() {
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

	protected void setLoadingModel(final String mensaje) {
		final List<String> soloMensaje = java.util.Collections.singletonList(mensaje);
		lineasActuales = soloMensaje;
		listaRegistros.setModel(new AbstractListModel<String>() {
			@Override
			public int getSize() {
				return soloMensaje.size();
			}

			@Override
			public String getElementAt(int index) {
				return soloMensaje.get(index);
			}
		});
	}

	protected void refrescarModeloCon(final List<String> lineas) {
		lineasActuales = (lineas == null) ? java.util.Collections.<String>emptyList() : lineas;
		listaRegistros.setModel(new AbstractListModel<String>() {
			@Override
			public int getSize() {
				return lineasActuales.size();
			}

			@Override
			public String getElementAt(int index) {
				return lineasActuales.get(index);
			}
		});
		// Reiniciar estado de búsqueda al cambiar de consola
		posicionesCoincidencias.clear();
		indiceBusquedaActual = -1;
	}

	// ====== Búsqueda ======
	protected void saltarSiguienteCoincidencia() {
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

	protected void buscarTexto(String texto) {
		posicionesCoincidencias.clear();
		indiceBusquedaActual = -1;

		if (texto == null || texto.isEmpty())
			return;

		String t = texto.toLowerCase();
		for (int i = 0; i < lineasActuales.size(); i++) {
			String linea = lineasActuales.get(i);
			if (linea != null && linea.toLowerCase().contains(t)) {
				posicionesCoincidencias.add(i);
			}
		}
		if (!posicionesCoincidencias.isEmpty()) {
			indiceBusquedaActual = 0;
			resaltarCoincidenciaActual();
		}
		listaRegistros.repaint();
	}

	protected void resaltarCoincidenciaActual() {
		if (indiceBusquedaActual < 0 || indiceBusquedaActual >= posicionesCoincidencias.size())
			return;
		int pos = posicionesCoincidencias.get(indiceBusquedaActual);
		listaRegistros.setSelectedIndex(pos);
		listaRegistros.ensureIndexIsVisible(pos);
	}

	// ====== Render/Descripción de error ======
	protected static String htmlAPlano(String s) {
		if (s == null)
			return "";
		String r = s;
		r = r.replaceAll("(?is)<br\\s*/?>", "\n");
		r = r.replaceAll("(?is)</p\\s*>", "\n");
		r = r.replaceAll("(?is)</div\\s*>", "\n");
		r = r.replaceAll("(?is)</li\\s*>", "\n");
		r = r.replaceAll("(?is)</tr\\s*>", "\n");
		r = r.replaceAll("(?is)<[^>]+>", "");
		r = r.replace("&nbsp;", " ");
		r = r.replace("&lt;", "<").replace("&gt;", ">");
		r = r.replace("&quot;", "\"").replace("&#39;", "'");
		r = r.replace("&amp;", "&");
		r = r.replaceAll("\\n{3,}", "\n\n");
		return r.trim();
	}

	protected static String escHtml(String s) {
		if (s == null)
			return "";
		String r = s;
		r = r.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'",
				"&#39;");
		return r;
	}

	protected void descripcionHtml(String textoPlano) {
		String safe = (textoPlano == null ? "" : escHtml(textoPlano));
		String html = "<html><body bgcolor='#111111' text='#FFFFFF'><pre>" + safe + "</pre></body></html>";
		txtDescripcionError.setText(html);
		txtDescripcionError.setCaretPosition(0);
	}

	protected void procesarSeleccionError(int numeroLinea, Consola consola) {
		txtNombreError.setText("");

		StringBuilder detallePlano = new StringBuilder();

		List<ErrorDeLectador> erroresEnLinea = consola.errores_de_lectadores.stream()
				.filter(err -> err.obtenerLinea() == numeroLinea).collect(Collectors.<ErrorDeLectador>toList());

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

				detallePlano.append(tituloPlano).append("\n");
				detallePlano.append(mensajePlano).append("\n");
				detallePlano.append("--------------------------------------------------").append("\n");
			}
			txtNombreError.setText(nombres.toString());
			descripcionHtml(detallePlano.toString());
		} else {
			if (numeroLinea >= 0 && numeroLinea < lineasActuales.size()) {
				String textoLinea = lineasActuales.get(numeroLinea);
				if (textoLinea != null && (textoLinea.contains("ERROR") || textoLinea.contains("EXCEPTION"))) {
					txtNombreError.setText(MonitorDePID.idioma.obtenerNombreErrorPorDefecto());
					String porDefectoPlano = htmlAPlano(MonitorDePID.idioma.obtenerDescripcionErrorPorDefecto());
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

	// ====== CrashDetectorGUI ======

	@Override
	public TipoGUI tipo() {
		return TipoGUI.LECTADOR_DE_CONSOLAS;
	}

	@Override
	public void recargarApariencia() {
		// Solo apariencia (colores/texto no localizado). La impl debe restablecer todo.
		aplicarApariencia();
	}

	/**
	 * Hook principal de apariencia. La implementación **debe** restablecer colores
	 * y textos NO localizados.
	 */
	protected abstract void aplicarApariencia();

	@Override
	public void init() {
		configurarVentanaBase();
		inicializarComponentesBase();
		cargarConsolas();
		precargarLineasEnSegundoPlano();
		setVisible(true);
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

