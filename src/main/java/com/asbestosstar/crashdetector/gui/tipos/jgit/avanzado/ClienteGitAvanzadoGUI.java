package com.asbestosstar.crashdetector.gui.tipos.jgit.avanzado;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.jgit.JGitReflexivo;

/**
 * Cliente Git avanzado limitado al repositorio de la instancia actual.
 *
 * La estructura se inspira en clientes de escritorio como Gittyup: historial a
 * la izquierda, cambios preparados y no preparados en el centro, commit y
 * detalles de diff/blame en la zona inferior. No contiene selector de
 * repositorios ni lista de repositorios recientes.
 */
public abstract class ClienteGitAvanzadoGUI extends JFrame implements CrashDetectorGUI {

	private static final long serialVersionUID = 1L;
	private static final int MAXIMO_HISTORIAL = 250;

	public static final Map<String, Supplier<ClienteGitAvanzadoGUI>> GUIS = new HashMap<String, Supplier<ClienteGitAvanzadoGUI>>();

	protected File repositorioActual;
	protected EstadoGitAvanzado estadoActual;
	protected volatile boolean inicializada;
	protected volatile boolean trabajando;
	protected volatile boolean actualizandoRamas;

	protected JPanel panelRaiz;
	protected JPanel panelCabecera;
	protected JPanel panelBarra;
	protected JPanel panelHistorial;
	protected JPanel panelNoPreparados;
	protected JPanel panelPreparados;
	protected JPanel panelCommit;
	protected JPanel panelEstado;

	protected JLabel imagenTema;
	protected JLabel etiquetaSerie;
	protected JLabel etiquetaRepositorio;
	protected JLabel etiquetaRama;
	protected JLabel etiquetaEstado;

	protected JComboBox<String> comboRamas;
	protected JButton botonActualizar;
	protected JButton botonFetch;
	protected JButton botonPull;
	protected JButton botonPush;
	protected JButton botonNuevaRama;

	protected JTable tablaHistorial;
	protected JTable tablaNoPreparados;
	protected JTable tablaPreparados;
	protected ModeloHistorial modeloHistorial;
	protected ModeloCambios modeloNoPreparados;
	protected ModeloCambios modeloPreparados;

	protected JButton botonPreparar;
	protected JButton botonPrepararTodo;
	protected JButton botonQuitarPreparacion;
	protected JButton botonQuitarTodaPreparacion;
	protected JButton botonVerDiff;
	protected JButton botonVerBlame;
	protected JButton botonCommit;

	protected JTextArea areaMensajeCommit;
	protected JTextArea areaDiff;
	protected JTextArea areaBlame;
	protected JTextArea areaActividad;
	protected JTabbedPane pestanasDetalle;

	@Override
	public TipoGUI<ClienteGitAvanzadoGUI> tipo() {
		return TipoGUI.CLIENTE_GIT_AVANZADO;
	}

	@Override
	public void init() {
		constructir(Statics.CARPETA_DE_APP);
	}

	/**
	 * Abre el cliente para un único repositorio. El primer argumento puede ser un
	 * File. Cualquier otra entrada se ignora y se usa la carpeta actual.
	 */
	public void constructir(Object... argumentos) {
		File solicitado = obtenerRepositorioDeArgumentos(argumentos);
		File actual = Statics.CARPETA_DE_APP.getAbsoluteFile();
		if (solicitado != null && !mismaRuta(actual, solicitado)) {
			CrashDetectorLogger.log("El cliente Git avanzado ignoró un repositorio distinto de la instancia actual.");
		}
		repositorioActual = actual;

		if (!JGitAvanzadoReflexivo.disponible()) {
			JOptionPane.showMessageDialog(null, MonitorDePID.idioma.gitAvanzadoJGitNoDisponible(),
					MonitorDePID.idioma.gitAvanzadoTitulo(), JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (!JGitReflexivo.repositorioExiste(repositorioActual)) {
			JOptionPane.showMessageDialog(null, MonitorDePID.idioma.gitAvanzadoRepositorioRequerido(),
					MonitorDePID.idioma.gitAvanzadoTitulo(), JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (!inicializada) {
			inicializarVentana();
		}

		recargarTextos();
		recargarApariencia();
		setVisible(true);
		toFront();
		requestFocus();
		actualizarRepositorio();
	}

	private boolean mismaRuta(File a, File b) {
		try {
			return a.getCanonicalFile().equals(b.getCanonicalFile());
		} catch (Exception e) {
			return a.getAbsoluteFile().equals(b.getAbsoluteFile());
		}
	}

	private File obtenerRepositorioDeArgumentos(Object... argumentos) {
		if (argumentos != null) {
			for (Object argumento : argumentos) {
				if (argumento instanceof File) {
					return (File) argumento;
				}
			}
		}
		return null;
	}

	private void inicializarVentana() {
		inicializada = true;
		setTitle(MonitorDePID.idioma.gitAvanzadoTitulo());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(1320, 820);
		setMinimumSize(new Dimension(980, 650));
		setLocationRelativeTo(null);

		panelRaiz = new JPanel(new BorderLayout(8, 8));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		setContentPane(panelRaiz);

		crearCabecera();
		crearCentro();
		crearEstado();
		conectarEventos();
	}

	private void crearCabecera() {
		panelCabecera = new JPanel(new BorderLayout(10, 6));
		imagenTema = new JLabel();
		imagenTema.setHorizontalAlignment(JLabel.CENTER);
		imagenTema.setPreferredSize(new Dimension(160, 170));

		JPanel textos = new JPanel(new GridLayout(3, 1, 2, 2));
		etiquetaSerie = new JLabel();
		etiquetaRepositorio = new JLabel();
		etiquetaRama = new JLabel();
		textos.add(etiquetaSerie);
		textos.add(etiquetaRepositorio);
		textos.add(etiquetaRama);

		panelBarra = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
		comboRamas = new JComboBox<String>();
		comboRamas.setPreferredSize(new Dimension(190, 28));
		botonNuevaRama = new JButton();
		botonActualizar = new JButton();
		botonFetch = new JButton();
		botonPull = new JButton();
		botonPush = new JButton();
		panelBarra.add(comboRamas);
		panelBarra.add(botonNuevaRama);
		panelBarra.add(botonActualizar);
		panelBarra.add(botonFetch);
		panelBarra.add(botonPull);
		panelBarra.add(botonPush);

		JPanel centro = new JPanel(new BorderLayout(4, 4));
		centro.add(textos, BorderLayout.CENTER);
		centro.add(panelBarra, BorderLayout.SOUTH);

		panelCabecera.add(imagenTema, BorderLayout.WEST);
		panelCabecera.add(centro, BorderLayout.CENTER);
		panelRaiz.add(panelCabecera, BorderLayout.NORTH);
	}

	private void crearCentro() {
		modeloHistorial = new ModeloHistorial();
		modeloNoPreparados = new ModeloCambios(false);
		modeloPreparados = new ModeloCambios(true);

		tablaHistorial = nuevaTabla(modeloHistorial);
		tablaNoPreparados = nuevaTabla(modeloNoPreparados);
		tablaPreparados = nuevaTabla(modeloPreparados);

		panelHistorial = panelConTitulo(new JScrollPane(tablaHistorial));
		panelNoPreparados = panelConTitulo(new JScrollPane(tablaNoPreparados));
		panelPreparados = panelConTitulo(new JScrollPane(tablaPreparados));

		botonPreparar = new JButton();
		botonPrepararTodo = new JButton();
		botonQuitarPreparacion = new JButton();
		botonQuitarTodaPreparacion = new JButton();
		botonVerDiff = new JButton();
		botonVerBlame = new JButton();

		JPanel accionesNoPreparados = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3));
		accionesNoPreparados.add(botonPreparar);
		accionesNoPreparados.add(botonPrepararTodo);
		accionesNoPreparados.add(botonVerDiff);
		accionesNoPreparados.add(botonVerBlame);
		panelNoPreparados.add(accionesNoPreparados, BorderLayout.SOUTH);

		JPanel accionesPreparados = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3));
		accionesPreparados.add(botonQuitarPreparacion);
		accionesPreparados.add(botonQuitarTodaPreparacion);
		panelPreparados.add(accionesPreparados, BorderLayout.SOUTH);

		JSplitPane cambios = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelNoPreparados, panelPreparados);
		cambios.setResizeWeight(0.5);
		cambios.setDividerLocation(420);

		panelCommit = new JPanel(new BorderLayout(5, 5));
		areaMensajeCommit = new JTextArea(4, 50);
		areaMensajeCommit.setLineWrap(true);
		areaMensajeCommit.setWrapStyleWord(true);
		botonCommit = new JButton();
		panelCommit.add(new JScrollPane(areaMensajeCommit), BorderLayout.CENTER);
		panelCommit.add(botonCommit, BorderLayout.EAST);

		JPanel cambiosYCommit = new JPanel(new BorderLayout(5, 5));
		cambiosYCommit.add(cambios, BorderLayout.CENTER);
		cambiosYCommit.add(panelCommit, BorderLayout.SOUTH);

		areaDiff = areaSoloLectura();
		areaBlame = areaSoloLectura();
		areaActividad = areaSoloLectura();
		pestanasDetalle = new JTabbedPane();
		pestanasDetalle.addTab("", new JScrollPane(areaDiff));
		pestanasDetalle.addTab("", new JScrollPane(areaBlame));
		pestanasDetalle.addTab("", new JScrollPane(areaActividad));

		JSplitPane derecha = new JSplitPane(JSplitPane.VERTICAL_SPLIT, cambiosYCommit, pestanasDetalle);
		derecha.setResizeWeight(0.58);
		derecha.setDividerLocation(390);

		JSplitPane principal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelHistorial, derecha);
		principal.setResizeWeight(0.24);
		principal.setDividerLocation(310);
		panelRaiz.add(principal, BorderLayout.CENTER);
	}

	private JPanel panelConTitulo(Component contenido) {
		JPanel panel = new JPanel(new BorderLayout(4, 4));
		panel.add(contenido, BorderLayout.CENTER);
		return panel;
	}

	private JTable nuevaTabla(AbstractTableModel modelo) {
		JTable tabla = new JTable(modelo);
		tabla.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tabla.setAutoCreateRowSorter(true);
		tabla.setFillsViewportHeight(true);
		return tabla;
	}

	private JTextArea areaSoloLectura() {
		JTextArea area = new JTextArea();
		area.setEditable(false);
		area.setLineWrap(false);
		area.setTabSize(4);
		return area;
	}

	private void crearEstado() {
		panelEstado = new JPanel(new BorderLayout());
		etiquetaEstado = new JLabel();
		panelEstado.add(etiquetaEstado, BorderLayout.CENTER);
		panelRaiz.add(panelEstado, BorderLayout.SOUTH);
	}

	private void conectarEventos() {
		botonActualizar.addActionListener(e -> actualizarRepositorio());
		botonFetch.addActionListener(e -> ejecutarOperacion(MonitorDePID.idioma.gitAvanzadoActividadFetch(),
				() -> JGitAvanzadoReflexivo.fetch(repositorioActual)));
		botonPull.addActionListener(e -> ejecutarOperacion(MonitorDePID.idioma.gitAvanzadoActividadPull(),
				() -> JGitAvanzadoReflexivo.pull(repositorioActual)));
		botonPush.addActionListener(e -> ejecutarOperacion(MonitorDePID.idioma.gitAvanzadoActividadPush(),
				() -> JGitAvanzadoReflexivo.push(repositorioActual)));
		botonNuevaRama.addActionListener(e -> crearRama());
		comboRamas.addActionListener(e -> cambiarRamaSeleccionada());

		botonPreparar.addActionListener(e -> prepararSeleccionados());
		botonPrepararTodo
				.addActionListener(e -> ejecutarOperacion(MonitorDePID.idioma.gitAvanzadoActividadPrepararTodo(),
						() -> JGitAvanzadoReflexivo.prepararTodo(repositorioActual)));
		botonQuitarPreparacion.addActionListener(e -> quitarPreparacionSeleccionados());
		botonQuitarTodaPreparacion
				.addActionListener(e -> ejecutarOperacion(MonitorDePID.idioma.gitAvanzadoActividadQuitarTodo(),
						() -> JGitAvanzadoReflexivo.quitarTodaPreparacion(repositorioActual)));
		botonCommit.addActionListener(e -> hacerCommit());
		botonVerDiff.addActionListener(e -> mostrarDiffSeleccionado());
		botonVerBlame.addActionListener(e -> mostrarBlameSeleccionado());

		tablaNoPreparados.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				mostrarDiffSeleccionado();
			}
		});
		tablaPreparados.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				mostrarDiffPreparadoSeleccionado();
			}
		});
		tablaHistorial.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				mostrarCommitSeleccionado();
			}
		});
	}

	public void actualizarRepositorio() {
		if (trabajando || repositorioActual == null) {
			return;
		}
		trabajando = true;
		actualizarControles();
		etiquetaEstado.setText(MonitorDePID.idioma.gitAvanzadoActualizando());

		new SwingWorker<EstadoGitAvanzado, Void>() {
			@Override
			protected EstadoGitAvanzado doInBackground() {
				return JGitAvanzadoReflexivo.obtenerEstado(repositorioActual, MAXIMO_HISTORIAL);
			}

			@Override
			protected void done() {
				try {
					EstadoGitAvanzado estado = get();
					if (estado == null) {
						mostrarError();
						return;
					}
					estadoActual = estado;
					aplicarEstado(estado);
					registrarActividad(MonitorDePID.idioma.gitAvanzadoActividadActualizado());
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					mostrarError();
				} finally {
					trabajando = false;
					actualizarControles();
				}
			}
		}.execute();
	}

	private void aplicarEstado(EstadoGitAvanzado estado) {
		modeloHistorial.establecer(estado.historial());
		modeloNoPreparados.establecer(estado.noPreparados());
		modeloPreparados.establecer(estado.preparados());

		actualizandoRamas = true;
		try {
			DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();
			for (String rama : estado.ramas()) {
				modelo.addElement(rama);
			}
			comboRamas.setModel(modelo);
			comboRamas.setSelectedItem(estado.ramaActual());
		} finally {
			actualizandoRamas = false;
		}

		etiquetaRepositorio
				.setText(MonitorDePID.idioma.gitAvanzadoRepositorio() + ": " + repositorioActual.getAbsolutePath());
		etiquetaRama.setText(MonitorDePID.idioma.gitAvanzadoRama() + ": " + estado.ramaActual());
		etiquetaEstado.setText(MonitorDePID.idioma.gitAvanzadoEstadoResumen(estado.noPreparados().size(),
				estado.preparados().size(), estado.historial().size()));
	}

	private void prepararSeleccionados() {
		final List<String> rutas = rutasSeleccionadas(tablaNoPreparados, modeloNoPreparados);
		if (rutas.isEmpty()) {
			return;
		}
		ejecutarOperacion(MonitorDePID.idioma.gitAvanzadoActividadPreparar(),
				() -> JGitAvanzadoReflexivo.preparar(repositorioActual, rutas));
	}

	private void quitarPreparacionSeleccionados() {
		final List<String> rutas = rutasSeleccionadas(tablaPreparados, modeloPreparados);
		if (rutas.isEmpty()) {
			return;
		}
		ejecutarOperacion(MonitorDePID.idioma.gitAvanzadoActividadQuitarPreparacion(),
				() -> JGitAvanzadoReflexivo.quitarPreparacion(repositorioActual, rutas));
	}

	private List<String> rutasSeleccionadas(JTable tabla, ModeloCambios modelo) {
		List<String> rutas = new ArrayList<String>();
		for (int filaVista : tabla.getSelectedRows()) {
			int filaModelo = tabla.convertRowIndexToModel(filaVista);
			CambioGitAvanzado cambio = modelo.obtener(filaModelo);
			if (cambio != null) {
				rutas.add(cambio.ruta());
			}
		}
		return rutas;
	}

	private void hacerCommit() {
		final String mensaje = areaMensajeCommit.getText().trim();
		if (mensaje.isEmpty()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.gitAvanzadoMensajeRequerido());
			return;
		}
		ejecutarOperacion(MonitorDePID.idioma.gitAvanzadoActividadCommit(), new Callable<Boolean>() {
			@Override
			public Boolean call() {
				boolean exito = JGitAvanzadoReflexivo.commit(repositorioActual, mensaje);
				if (exito) {
					SwingUtilities.invokeLater(() -> areaMensajeCommit.setText(""));
				}
				return Boolean.valueOf(exito);
			}
		});
	}

	private void crearRama() {
		String nombre = JOptionPane.showInputDialog(this, MonitorDePID.idioma.gitAvanzadoNombreNuevaRama());
		if (nombre == null || nombre.trim().isEmpty()) {
			return;
		}
		final String rama = nombre.trim();
		ejecutarOperacion(MonitorDePID.idioma.gitAvanzadoActividadCrearRama(rama),
				() -> JGitAvanzadoReflexivo.crearRama(repositorioActual, rama));
	}

	private void cambiarRamaSeleccionada() {
		if (actualizandoRamas || trabajando || estadoActual == null) {
			return;
		}
		Object seleccionado = comboRamas.getSelectedItem();
		if (!(seleccionado instanceof String)) {
			return;
		}
		final String rama = seleccionado.toString();
		if (rama.equals(estadoActual.ramaActual())) {
			return;
		}
		int confirmar = JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.gitAvanzadoConfirmarCambioRama(rama),
				MonitorDePID.idioma.gitAvanzadoRama(), JOptionPane.YES_NO_OPTION);
		if (confirmar != JOptionPane.YES_OPTION) {
			actualizandoRamas = true;
			comboRamas.setSelectedItem(estadoActual.ramaActual());
			actualizandoRamas = false;
			return;
		}
		ejecutarOperacion(MonitorDePID.idioma.gitAvanzadoActividadCambiarRama(rama),
				() -> JGitAvanzadoReflexivo.cambiarRama(repositorioActual, rama));
	}

	private void mostrarDiffSeleccionado() {
		CambioGitAvanzado cambio = cambioSeleccionado(tablaNoPreparados, modeloNoPreparados);
		if (cambio != null) {
			areaDiff.setText(JGitAvanzadoReflexivo.diffArchivo(repositorioActual, cambio.ruta(), false));
			areaDiff.setCaretPosition(0);
			pestanasDetalle.setSelectedIndex(0);
		}
	}

	private void mostrarDiffPreparadoSeleccionado() {
		CambioGitAvanzado cambio = cambioSeleccionado(tablaPreparados, modeloPreparados);
		if (cambio != null) {
			areaDiff.setText(JGitAvanzadoReflexivo.diffArchivo(repositorioActual, cambio.ruta(), true));
			areaDiff.setCaretPosition(0);
			pestanasDetalle.setSelectedIndex(0);
		}
	}

	private void mostrarBlameSeleccionado() {
		CambioGitAvanzado cambio = cambioSeleccionado(tablaNoPreparados, modeloNoPreparados);
		if (cambio == null) {
			cambio = cambioSeleccionado(tablaPreparados, modeloPreparados);
		}
		if (cambio != null) {
			areaBlame.setText(JGitAvanzadoReflexivo.blame(repositorioActual, cambio.ruta()));
			areaBlame.setCaretPosition(0);
			pestanasDetalle.setSelectedIndex(1);
		}
	}

	private void mostrarCommitSeleccionado() {
		int filaVista = tablaHistorial.getSelectedRow();
		if (filaVista < 0) {
			return;
		}
		CommitGitAvanzado commit = modeloHistorial.obtener(tablaHistorial.convertRowIndexToModel(filaVista));
		if (commit != null) {
			areaDiff.setText(JGitAvanzadoReflexivo.diffCommit(repositorioActual, commit.hash()));
			areaDiff.setCaretPosition(0);
			pestanasDetalle.setSelectedIndex(0);
		}
	}

	private CambioGitAvanzado cambioSeleccionado(JTable tabla, ModeloCambios modelo) {
		int filaVista = tabla.getSelectedRow();
		return filaVista < 0 ? null : modelo.obtener(tabla.convertRowIndexToModel(filaVista));
	}

	private void ejecutarOperacion(final String actividad, final Callable<Boolean> operacion) {
		if (trabajando) {
			return;
		}
		trabajando = true;
		actualizarControles();
		etiquetaEstado.setText(actividad);
		registrarActividad(actividad);

		new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				return operacion.call();
			}

			@Override
			protected void done() {
				try {
					boolean exito = Boolean.TRUE.equals(get());
					registrarActividad(exito ? MonitorDePID.idioma.gitAvanzadoOperacionCompletada()
							: MonitorDePID.idioma.gitAvanzadoOperacionFallida());
					if (!exito) {
						mostrarError();
					}
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					mostrarError();
				} finally {
					trabajando = false;
					actualizarControles();
					actualizarRepositorio();
				}
			}
		}.execute();
	}

	private void registrarActividad(String mensaje) {
		String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
		areaActividad.append("[" + hora + "] " + mensaje + "\n");
		areaActividad.setCaretPosition(areaActividad.getDocument().getLength());
	}

	private void mostrarError() {
		JOptionPane.showMessageDialog(this, MonitorDePID.idioma.gitAvanzadoErrorOperacion(),
				MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
	}

	protected void actualizarControles() {
		boolean disponible = !trabajando && estadoActual != null;
		boolean remote = disponible && !estadoActual.remote().trim().isEmpty();
		botonActualizar.setEnabled(!trabajando);
		botonFetch.setEnabled(remote);
		botonPull.setEnabled(remote);
		botonPush.setEnabled(remote);
		botonNuevaRama.setEnabled(disponible);
		comboRamas.setEnabled(disponible);
		botonPreparar.setEnabled(disponible && !modeloNoPreparados.vacio());
		botonPrepararTodo.setEnabled(disponible && !modeloNoPreparados.vacio());
		botonQuitarPreparacion.setEnabled(disponible && !modeloPreparados.vacio());
		botonQuitarTodaPreparacion.setEnabled(disponible && !modeloPreparados.vacio());
		botonCommit.setEnabled(disponible && !modeloPreparados.vacio());
		botonVerDiff.setEnabled(disponible);
		botonVerBlame.setEnabled(disponible);
	}

	protected void recargarTextos() {
		setTitle(MonitorDePID.idioma.gitAvanzadoTitulo());
		if (etiquetaSerie != null) {
			etiquetaSerie.setText(MonitorDePID.idioma.gitAvanzadoSerieLostMedia());
		}
		if (etiquetaRepositorio != null && repositorioActual != null) {
			etiquetaRepositorio
					.setText(MonitorDePID.idioma.gitAvanzadoRepositorio() + ": " + repositorioActual.getAbsolutePath());
		}
		if (botonActualizar != null)
			botonActualizar.setText(MonitorDePID.idioma.gitAvanzadoActualizar());
		if (botonFetch != null)
			botonFetch.setText(MonitorDePID.idioma.gitAvanzadoFetch());
		if (botonPull != null)
			botonPull.setText(MonitorDePID.idioma.gitAvanzadoPull());
		if (botonPush != null)
			botonPush.setText(MonitorDePID.idioma.gitAvanzadoPush());
		if (botonNuevaRama != null)
			botonNuevaRama.setText(MonitorDePID.idioma.gitAvanzadoNuevaRama());
		if (botonPreparar != null)
			botonPreparar.setText(MonitorDePID.idioma.gitAvanzadoPrepararSeleccion());
		if (botonPrepararTodo != null)
			botonPrepararTodo.setText(MonitorDePID.idioma.gitAvanzadoPrepararTodo());
		if (botonQuitarPreparacion != null)
			botonQuitarPreparacion.setText(MonitorDePID.idioma.gitAvanzadoQuitarSeleccion());
		if (botonQuitarTodaPreparacion != null)
			botonQuitarTodaPreparacion.setText(MonitorDePID.idioma.gitAvanzadoQuitarTodo());
		if (botonVerDiff != null)
			botonVerDiff.setText(MonitorDePID.idioma.gitAvanzadoVerDiff());
		if (botonVerBlame != null)
			botonVerBlame.setText(MonitorDePID.idioma.gitAvanzadoVerBlame());
		if (botonCommit != null)
			botonCommit.setText(MonitorDePID.idioma.gitAvanzadoCommit());
		if (areaMensajeCommit != null)
			areaMensajeCommit.setToolTipText(MonitorDePID.idioma.gitAvanzadoMensajeCommit());
		if (pestanasDetalle != null) {
			pestanasDetalle.setTitleAt(0, MonitorDePID.idioma.gitAvanzadoDiff());
			pestanasDetalle.setTitleAt(1, MonitorDePID.idioma.gitAvanzadoBlame());
			pestanasDetalle.setTitleAt(2, MonitorDePID.idioma.gitAvanzadoActividad());
		}
		if (panelHistorial != null)
			panelHistorial.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.gitAvanzadoHistorial()));
		if (panelNoPreparados != null)
			panelNoPreparados
					.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.gitAvanzadoNoPreparados()));
		if (panelPreparados != null)
			panelPreparados.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.gitAvanzadoPreparados()));
		if (panelCommit != null)
			panelCommit.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.gitAvanzadoMensajeCommit()));
		if (modeloHistorial != null)
			modeloHistorial.fireTableStructureChanged();
		if (modeloNoPreparados != null)
			modeloNoPreparados.fireTableStructureChanged();
		if (modeloPreparados != null)
			modeloPreparados.fireTableStructureChanged();
	}

	protected String textoEstado(CambioGitAvanzado.Estado estado) {
		switch (estado) {
		case AGREGADO:
			return MonitorDePID.idioma.gitAvanzadoEstadoAgregado();
		case ELIMINADO:
			return MonitorDePID.idioma.gitAvanzadoEstadoEliminado();
		case NO_RASTREADO:
			return MonitorDePID.idioma.gitAvanzadoEstadoNoRastreado();
		case CONFLICTO:
			return MonitorDePID.idioma.gitAvanzadoEstadoConflicto();
		case RENOMBRADO:
			return MonitorDePID.idioma.gitAvanzadoEstadoRenombrado();
		default:
			return MonitorDePID.idioma.gitAvanzadoEstadoModificado();
		}
	}

	@Override
	public abstract String id();

	@Override
	public abstract void recargarApariencia();

	@Override
	public abstract List<ElementoConfig> obtenerElementosConfigs();

	protected final class ModeloCambios extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private final List<CambioGitAvanzado> filas = new ArrayList<CambioGitAvanzado>();
		@SuppressWarnings("unused")
		private final boolean preparado;

		ModeloCambios(boolean preparado) {
			this.preparado = preparado;
		}

		void establecer(List<CambioGitAvanzado> cambios) {
			filas.clear();
			if (cambios != null)
				filas.addAll(cambios);
			fireTableDataChanged();
		}

		CambioGitAvanzado obtener(int fila) {
			return fila < 0 || fila >= filas.size() ? null : filas.get(fila);
		}

		boolean vacio() {
			return filas.isEmpty();
		}

		@Override
		public int getRowCount() {
			return filas.size();
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public String getColumnName(int columna) {
			return columna == 0 ? MonitorDePID.idioma.gitAvanzadoColEstado() : MonitorDePID.idioma.gitAvanzadoColRuta();
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			CambioGitAvanzado cambio = filas.get(fila);
			return columna == 0 ? textoEstado(cambio.estado()) : cambio.ruta();
		}
	}

	protected final class ModeloHistorial extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private final List<CommitGitAvanzado> filas = new ArrayList<CommitGitAvanzado>();
		private final SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		void establecer(List<CommitGitAvanzado> commits) {
			filas.clear();
			if (commits != null)
				filas.addAll(commits);
			fireTableDataChanged();
		}

		CommitGitAvanzado obtener(int fila) {
			return fila < 0 || fila >= filas.size() ? null : filas.get(fila);
		}

		@Override
		public int getRowCount() {
			return filas.size();
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public String getColumnName(int columna) {
			switch (columna) {
			case 0:
				return MonitorDePID.idioma.gitAvanzadoColHash();
			case 1:
				return MonitorDePID.idioma.gitAvanzadoColFecha();
			case 2:
				return MonitorDePID.idioma.gitAvanzadoColAutor();
			default:
				return MonitorDePID.idioma.gitAvanzadoColMensaje();
			}
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			CommitGitAvanzado commit = filas.get(fila);
			switch (columna) {
			case 0:
				return commit.hashCorto();
			case 1:
				return formato.format(new Date(commit.fechaEpochSegundos() * 1000L));
			case 2:
				return commit.autor();
			default:
				return commit.mensaje();
			}
		}
	}
}
