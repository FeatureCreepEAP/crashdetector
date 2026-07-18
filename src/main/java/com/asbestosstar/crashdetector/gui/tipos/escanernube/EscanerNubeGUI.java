package com.asbestosstar.crashdetector.gui.tipos.escanernube;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.escanernube.ClienteEscaneoNube;
import com.asbestosstar.crashdetector.escanernube.ClienteMetaDefender;
import com.asbestosstar.crashdetector.escanernube.ClienteVirusTotal;
import com.asbestosstar.crashdetector.escanernube.ProveedorEscaneoNube;
import com.asbestosstar.crashdetector.escanernube.ResultadoEscaneoNube;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * GUI base del escáner remoto de mods.
 *
 * La lógica de red, el paralelismo y el modelo de resultados permanecen aquí.
 * Una subclase concreta define la imagen y los colores configurables.
 */
public abstract class EscanerNubeGUI extends JFrame implements CrashDetectorGUI {

	public static final Map<String, Supplier<EscanerNubeGUI>> GUIS = new LinkedHashMap<>();

	protected JPanel panelRaiz;
	protected JPanel panelHero;
	protected JPanel panelClaves;
	protected JPanel panelControles;
	protected JPanel panelResultados;

	protected JLabel imagenProyecto;
	protected JTextArea areaRecuerdo;
	protected JTextArea areaAvisoPrivacidad;

	protected JLabel etiquetaVirusTotal;
	protected JLabel etiquetaMetaDefender;
	protected JLabel etiquetaHilos;
	protected JLabel etiquetaEstado;

	protected JPasswordField campoVirusTotal;
	protected JPasswordField campoMetaDefender;
	protected JSpinner selectorHilos;

	protected JButton botonGuardarClaves;
	protected JButton botonEscanearVirusTotal;
	protected JButton botonEscanearMetaDefender;
	protected JButton botonEscanearAmbos;
	protected JButton botonCancelar;

	protected JTable tablaResultados;
	protected ModeloResultados modeloResultados;
	protected JProgressBar barraProgreso;

	protected JScrollPane scrollRecuerdo;
	protected JScrollPane scrollAviso;
	protected JScrollPane scrollResultados;

	protected TitledBorder bordeClaves;
	protected TitledBorder bordeResultados;

	protected volatile boolean inicializada;
	protected volatile boolean cargando;

	protected SwingWorker<Void, ResultadoEscaneoNube> workerEscaneo;
	protected volatile ExecutorService ejecutorEscaneo;
	protected final AtomicBoolean cancelado = new AtomicBoolean(false);

	@Override
	public void init() {
		if (inicializada) {
			cargarClavesGuardadas();
			recargarApariencia();
			setVisible(true);
			toFront();
			requestFocus();
			return;
		}

		inicializada = true;
		getContentPane().removeAll();

		setTitle(MonitorDePID.idioma.escanerNubeTitulo());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(1320, 840);
		setMinimumSize(new Dimension(980, 650));
		setLocationRelativeTo(null);

		panelRaiz = new JPanel(new BorderLayout(10, 10));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		crearPanelHero();
		crearPanelClavesYControles();
		crearPanelResultados();

		JSplitPane divisionSuperior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelHero, panelClaves);
		divisionSuperior.setResizeWeight(0.34);
		divisionSuperior.setDividerLocation(390);

		JSplitPane divisionPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, divisionSuperior, panelResultados);
		divisionPrincipal.setResizeWeight(0.43);
		divisionPrincipal.setDividerLocation(350);

		panelRaiz.add(divisionPrincipal, BorderLayout.CENTER);
		setContentPane(panelRaiz);

		conectarEventos();
		cargarClavesGuardadas();
		recargarTextos();
		recargarApariencia();
		setCargando(false);
		setVisible(true);
	}

	private void crearPanelHero() {
		panelHero = new JPanel(new BorderLayout(8, 8));
		panelHero.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		imagenProyecto = new JLabel();
		imagenProyecto.setHorizontalAlignment(SwingConstants.CENTER);
		imagenProyecto.setVerticalAlignment(SwingConstants.CENTER);

		areaRecuerdo = new JTextArea();
		areaRecuerdo.setEditable(false);
		areaRecuerdo.setLineWrap(true);
		areaRecuerdo.setWrapStyleWord(true);
		areaRecuerdo.setRows(5);
		areaRecuerdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		scrollRecuerdo = new JScrollPane(areaRecuerdo);
		scrollRecuerdo.setBorder(BorderFactory.createEmptyBorder());

		panelHero.add(imagenProyecto, BorderLayout.NORTH);
		panelHero.add(scrollRecuerdo, BorderLayout.CENTER);
	}

	private void crearPanelClavesYControles() {
		panelClaves = new JPanel(new BorderLayout(8, 8));
		bordeClaves = BorderFactory.createTitledBorder("");
		panelClaves.setBorder(bordeClaves);

		JPanel formulario = new JPanel(new GridBagLayout());
		GridBagConstraints restricciones = new GridBagConstraints();
		restricciones.insets = new Insets(5, 6, 5, 6);
		restricciones.fill = GridBagConstraints.HORIZONTAL;
		restricciones.weightx = 0.0;
		restricciones.gridx = 0;
		restricciones.gridy = 0;

		etiquetaVirusTotal = new JLabel();
		formulario.add(etiquetaVirusTotal, restricciones);

		campoVirusTotal = new JPasswordField();
		restricciones.gridx = 1;
		restricciones.weightx = 1.0;
		formulario.add(campoVirusTotal, restricciones);

		etiquetaMetaDefender = new JLabel();
		restricciones.gridx = 0;
		restricciones.gridy++;
		restricciones.weightx = 0.0;
		formulario.add(etiquetaMetaDefender, restricciones);

		campoMetaDefender = new JPasswordField();
		restricciones.gridx = 1;
		restricciones.weightx = 1.0;
		formulario.add(campoMetaDefender, restricciones);

		etiquetaHilos = new JLabel();
		restricciones.gridx = 0;
		restricciones.gridy++;
		restricciones.weightx = 0.0;
		formulario.add(etiquetaHilos, restricciones);

		int hilosPorDefecto = Math.max(1, Math.min(2, Runtime.getRuntime().availableProcessors()));
		selectorHilos = new JSpinner(new SpinnerNumberModel(hilosPorDefecto, 1, 16, 1));
		restricciones.gridx = 1;
		restricciones.weightx = 1.0;
		formulario.add(selectorHilos, restricciones);

		botonGuardarClaves = new JButton();
		restricciones.gridx = 1;
		restricciones.gridy++;
		restricciones.weightx = 1.0;
		restricciones.anchor = GridBagConstraints.EAST;
		formulario.add(botonGuardarClaves, restricciones);

		areaAvisoPrivacidad = new JTextArea();
		areaAvisoPrivacidad.setEditable(false);
		areaAvisoPrivacidad.setLineWrap(true);
		areaAvisoPrivacidad.setWrapStyleWord(true);
		areaAvisoPrivacidad.setRows(4);
		areaAvisoPrivacidad.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		scrollAviso = new JScrollPane(areaAvisoPrivacidad);
		scrollAviso.setBorder(BorderFactory.createEmptyBorder());

		panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
		botonEscanearVirusTotal = new JButton();
		botonEscanearMetaDefender = new JButton();
		botonEscanearAmbos = new JButton();
		botonCancelar = new JButton();
		etiquetaEstado = new JLabel();
		barraProgreso = new JProgressBar(0, 100);
		barraProgreso.setStringPainted(true);
		barraProgreso.setPreferredSize(new Dimension(190, 24));

		panelControles.add(botonEscanearVirusTotal);
		panelControles.add(botonEscanearMetaDefender);
		panelControles.add(botonEscanearAmbos);
		panelControles.add(botonCancelar);
		panelControles.add(barraProgreso);
		panelControles.add(etiquetaEstado);

		JPanel centro = new JPanel(new BorderLayout(8, 8));
		centro.add(formulario, BorderLayout.NORTH);
		centro.add(scrollAviso, BorderLayout.CENTER);

		panelClaves.add(centro, BorderLayout.CENTER);
		panelClaves.add(panelControles, BorderLayout.SOUTH);
	}

	private void crearPanelResultados() {
		panelResultados = new JPanel(new BorderLayout());
		bordeResultados = BorderFactory.createTitledBorder("");
		panelResultados.setBorder(bordeResultados);

		modeloResultados = new ModeloResultados();
		tablaResultados = new JTable(modeloResultados);
		tablaResultados.setAutoCreateRowSorter(true);
		tablaResultados.setRowHeight(24);

		scrollResultados = new JScrollPane(tablaResultados);
		panelResultados.add(scrollResultados, BorderLayout.CENTER);
	}

	private void conectarEventos() {
		botonGuardarClaves.addActionListener(e -> guardarClaves(true));
		botonEscanearVirusTotal
				.addActionListener(e -> iniciarEscaneo(Collections.singletonList(ProveedorEscaneoNube.VIRUSTOTAL)));
		botonEscanearMetaDefender
				.addActionListener(e -> iniciarEscaneo(Collections.singletonList(ProveedorEscaneoNube.METADEFENDER)));
		botonEscanearAmbos.addActionListener(
				e -> iniciarEscaneo(Arrays.asList(ProveedorEscaneoNube.VIRUSTOTAL, ProveedorEscaneoNube.METADEFENDER)));
		botonCancelar.addActionListener(e -> cancelarEscaneo());
	}

	protected void recargarTextos() {
		setTitle(MonitorDePID.idioma.escanerNubeTitulo());

		if (areaRecuerdo != null) {
			areaRecuerdo.setText(MonitorDePID.idioma.escanerNubeMensajeRecuerdoParallelArtistProject());
			areaRecuerdo.setCaretPosition(0);
		}
		if (areaAvisoPrivacidad != null) {
			areaAvisoPrivacidad.setText(MonitorDePID.idioma.escanerNubeAvisoPrivacidad());
			areaAvisoPrivacidad.setCaretPosition(0);
		}
		if (etiquetaVirusTotal != null) {
			etiquetaVirusTotal.setText(MonitorDePID.idioma.escanerNubeClaveVirusTotal());
		}
		if (etiquetaMetaDefender != null) {
			etiquetaMetaDefender.setText(MonitorDePID.idioma.escanerNubeClaveMetaDefender());
		}
		if (etiquetaHilos != null) {
			etiquetaHilos.setText(MonitorDePID.idioma.escanerNubeNumeroHilos());
		}
		if (botonGuardarClaves != null) {
			botonGuardarClaves.setText(MonitorDePID.idioma.escanerNubeGuardarClaves());
		}
		if (botonEscanearVirusTotal != null) {
			botonEscanearVirusTotal.setText(MonitorDePID.idioma.escanerNubeEscanearVirusTotal());
		}
		if (botonEscanearMetaDefender != null) {
			botonEscanearMetaDefender.setText(MonitorDePID.idioma.escanerNubeEscanearMetaDefender());
		}
		if (botonEscanearAmbos != null) {
			botonEscanearAmbos.setText(MonitorDePID.idioma.escanerNubeEscanearAmbos());
		}
		if (botonCancelar != null) {
			botonCancelar.setText(MonitorDePID.idioma.escanerNubeCancelar());
		}
		if (bordeClaves != null) {
			bordeClaves.setTitle(MonitorDePID.idioma.escanerNubeSeccionClaves());
		}
		if (bordeResultados != null) {
			bordeResultados.setTitle(MonitorDePID.idioma.escanerNubeSeccionResultados());
		}
		if (!cargando && etiquetaEstado != null) {
			etiquetaEstado.setText(MonitorDePID.idioma.escanerNubeEstadoListo());
		}
		if (modeloResultados != null) {
			modeloResultados.fireTableStructureChanged();
		}
	}

	private void cargarClavesGuardadas() {
		ConfigMundial config = ConfigMundial.obtenerInstancia();
		if (campoVirusTotal != null) {
			campoVirusTotal.setText(config.obtenerVirusTotalClaveApi());
		}
		if (campoMetaDefender != null) {
			campoMetaDefender.setText(config.obtenerMetaDefenderClaveApi());
		}
	}

	private void guardarClaves(boolean mostrarConfirmacion) {
		String virusTotal = leerCampoSecreto(campoVirusTotal);
		String metaDefender = leerCampoSecreto(campoMetaDefender);

		ConfigMundial config = ConfigMundial.obtenerInstancia();
		config.guardarVirusTotalClaveApi(virusTotal);
		config.guardarMetaDefenderClaveApi(metaDefender);

		if (mostrarConfirmacion) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.escanerNubeClavesGuardadas(),
					MonitorDePID.idioma.escanerNubeTitulo(), JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private String leerCampoSecreto(JPasswordField campo) {
		if (campo == null) {
			return "";
		}
		char[] caracteres = campo.getPassword();
		try {
			return new String(caracteres).trim();
		} finally {
			Arrays.fill(caracteres, '\0');
		}
	}

	private void iniciarEscaneo(final List<ProveedorEscaneoNube> proveedores) {
		if (cargando || proveedores == null || proveedores.isEmpty()) {
			return;
		}

		guardarClaves(false);
		final ConfigMundial config = ConfigMundial.obtenerInstancia();

		if (proveedores.contains(ProveedorEscaneoNube.VIRUSTOTAL)
				&& config.obtenerVirusTotalClaveApi().trim().isEmpty()) {
			mostrarError(MonitorDePID.idioma.escanerNubeFaltaClaveVirusTotal());
			return;
		}
		if (proveedores.contains(ProveedorEscaneoNube.METADEFENDER)
				&& config.obtenerMetaDefenderClaveApi().trim().isEmpty()) {
			mostrarError(MonitorDePID.idioma.escanerNubeFaltaClaveMetaDefender());
			return;
		}

		final List<File> archivos = obtenerArchivosJar();
		if (archivos.isEmpty()) {
			mostrarError(MonitorDePID.idioma.escanerNubeSinArchivosJar());
			return;
		}

		int confirmacion = JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.escanerNubeConfirmarSubidaMensaje(),
				MonitorDePID.idioma.escanerNubeConfirmarSubidaTitulo(), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		if (confirmacion != JOptionPane.YES_OPTION) {
			return;
		}

		final int hilos = ((Number) selectorHilos.getValue()).intValue();
		final int totalTareas = archivos.size() * proveedores.size();
		final Map<ProveedorEscaneoNube, ClienteEscaneoNube> clientes = crearClientes(proveedores, config);

		modeloResultados.limpiar();
		barraProgreso.setValue(0);
		cancelado.set(false);
		setCargando(true);
		etiquetaEstado.setText(MonitorDePID.idioma.escanerNubeEstadoEscaneando(0, totalTareas));

		workerEscaneo = new SwingWorker<Void, ResultadoEscaneoNube>() {
			private int completadas;

			@Override
			protected Void doInBackground() {
				ejecutorEscaneo = Executors.newFixedThreadPool(hilos, new FabricaHilosEscaner());
				CompletionService<ResultadoEscaneoNube> servicio = new ExecutorCompletionService<>(ejecutorEscaneo);

				try {
					for (File archivo : archivos) {
						for (ProveedorEscaneoNube proveedor : proveedores) {
							servicio.submit(() -> ejecutarTarea(clientes.get(proveedor), proveedor, archivo));
						}
					}

					for (int indice = 0; indice < totalTareas; indice++) {
						if (isCancelled() || cancelado.get()) {
							break;
						}

						Future<ResultadoEscaneoNube> futuro = servicio.take();
						ResultadoEscaneoNube resultado = futuro.get();
						completadas++;
						publish(resultado);
						setProgress((int) Math.round(completadas * 100.0 / totalTareas));
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					cancelado.set(true);
				} catch (CancellationException e) {
					cancelado.set(true);
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
				} finally {
					if (ejecutorEscaneo != null) {
						ejecutorEscaneo.shutdownNow();
					}
				}
				return null;
			}

			@Override
			protected void process(List<ResultadoEscaneoNube> resultados) {
				for (ResultadoEscaneoNube resultado : resultados) {
					modeloResultados.agregar(resultado);
				}
				etiquetaEstado.setText(MonitorDePID.idioma.escanerNubeEstadoEscaneando(completadas, totalTareas));
			}

			@Override
			protected void done() {
				setCargando(false);
				if (isCancelled() || cancelado.get()) {
					etiquetaEstado.setText(MonitorDePID.idioma.escanerNubeEstadoCancelado());
				} else {
					barraProgreso.setValue(100);
					etiquetaEstado.setText(MonitorDePID.idioma.escanerNubeEstadoCompletado(completadas));
				}
			}
		};

		workerEscaneo.addPropertyChangeListener(evento -> {
			if ("progress".equals(evento.getPropertyName())) {
				barraProgreso.setValue((Integer) evento.getNewValue());
			}
		});
		workerEscaneo.execute();
	}

	private ResultadoEscaneoNube ejecutarTarea(ClienteEscaneoNube cliente, ProveedorEscaneoNube proveedor,
			File archivo) {
		try {
			if (cancelado.get()) {
				return new ResultadoEscaneoNube(proveedor, archivo, MonitorDePID.idioma.escanerNubeEstadoCancelado(), 0,
						0, "", MonitorDePID.idioma.escanerNubeEstadoCancelado());
			}
			return cliente.escanear(archivo, cancelado);
		} catch (CancellationException e) {
			return new ResultadoEscaneoNube(proveedor, archivo, MonitorDePID.idioma.escanerNubeEstadoCancelado(), 0, 0,
					"", MonitorDePID.idioma.escanerNubeEstadoCancelado());
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			String detalle = t.getMessage();
			if (detalle == null || detalle.trim().isEmpty()) {
				detalle = MonitorDePID.idioma.escanerNubeErrorDesconocido();
			}
			return new ResultadoEscaneoNube(proveedor, archivo, MonitorDePID.idioma.escanerNubeResultadoFallido(), 0, 0,
					"", detalle);
		}
	}

	private Map<ProveedorEscaneoNube, ClienteEscaneoNube> crearClientes(List<ProveedorEscaneoNube> proveedores,
			ConfigMundial config) {
		Map<ProveedorEscaneoNube, ClienteEscaneoNube> clientes = new EnumMap<>(ProveedorEscaneoNube.class);

		if (proveedores.contains(ProveedorEscaneoNube.VIRUSTOTAL)) {
			clientes.put(ProveedorEscaneoNube.VIRUSTOTAL, new ClienteVirusTotal(config.obtenerVirusTotalClaveApi()));
		}
		if (proveedores.contains(ProveedorEscaneoNube.METADEFENDER)) {
			clientes.put(ProveedorEscaneoNube.METADEFENDER,
					new ClienteMetaDefender(config.obtenerMetaDefenderClaveApi()));
		}
		return clientes;
	}

	private List<File> obtenerArchivosJar() {
		Map<String, File> sinDuplicados = new LinkedHashMap<>();

		for (java.nio.file.Path carpeta : Statics.carpetas_de_mods) {
			if (carpeta == null) {
				continue;
			}

			File directorio = carpeta.toFile();
			if (!directorio.isDirectory()) {
				continue;
			}

			File[] archivos = directorio.listFiles();
			if (archivos == null) {
				continue;
			}

			for (File archivo : archivos) {
				if (archivo == null || !archivo.isFile() || !archivo.canRead()
						|| !archivo.getName().toLowerCase(java.util.Locale.ROOT).endsWith(".jar")) {
					continue;
				}

				try {
					File canonico = archivo.getCanonicalFile();
					sinDuplicados.put(canonico.getAbsolutePath(), canonico);
				} catch (IOException e) {
					CrashDetectorLogger.logException(e);
				}
			}
		}

		List<File> resultado = new ArrayList<>(sinDuplicados.values());
		resultado.sort(Comparator.comparing(File::getAbsolutePath, String.CASE_INSENSITIVE_ORDER));
		return resultado;
	}

	protected void cancelarEscaneo() {
		cancelado.set(true);

		if (workerEscaneo != null && !workerEscaneo.isDone()) {
			workerEscaneo.cancel(true);
		}
		if (ejecutorEscaneo != null) {
			ejecutorEscaneo.shutdownNow();
		}
		etiquetaEstado.setText(MonitorDePID.idioma.escanerNubeEstadoCancelado());
	}

	protected void setCargando(boolean valor) {
		cargando = valor;

		botonGuardarClaves.setEnabled(!valor);
		botonEscanearVirusTotal.setEnabled(!valor);
		botonEscanearMetaDefender.setEnabled(!valor);
		botonEscanearAmbos.setEnabled(!valor);
		botonCancelar.setEnabled(valor);
		campoVirusTotal.setEnabled(!valor);
		campoMetaDefender.setEnabled(!valor);
		selectorHilos.setEnabled(!valor);
	}

	private void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.ESCANER_NUBE;
	}

	/**
	 * Modelo de la tabla de resultados.
	 */
	public class ModeloResultados extends AbstractTableModel {

		private final List<ResultadoEscaneoNube> filas = new ArrayList<>();

		public void limpiar() {
			filas.clear();
			fireTableDataChanged();
		}

		public void agregar(ResultadoEscaneoNube resultado) {
			int indice = filas.size();
			filas.add(resultado);
			fireTableRowsInserted(indice, indice);
		}

		@Override
		public int getRowCount() {
			return filas.size();
		}

		@Override
		public int getColumnCount() {
			return 7;
		}

		@Override
		public String getColumnName(int columna) {
			switch (columna) {
			case 0:
				return MonitorDePID.idioma.escanerNubeColProveedor();
			case 1:
				return MonitorDePID.idioma.escanerNubeColArchivo();
			case 2:
				return MonitorDePID.idioma.escanerNubeColEstado();
			case 3:
				return MonitorDePID.idioma.escanerNubeColDetecciones();
			case 4:
				return MonitorDePID.idioma.escanerNubeColMotores();
			case 5:
				return MonitorDePID.idioma.escanerNubeColSha256();
			default:
				return MonitorDePID.idioma.escanerNubeColDetalle();
			}
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			ResultadoEscaneoNube resultado = filas.get(fila);
			switch (columna) {
			case 0:
				return resultado.proveedor.nombreVisible();
			case 1:
				return resultado.archivo == null ? "" : resultado.archivo.getAbsolutePath();
			case 2:
				return resultado.estado;
			case 3:
				return resultado.detecciones;
			case 4:
				return resultado.motores;
			case 5:
				return resultado.sha256;
			default:
				return resultado.detalle;
			}
		}
	}

	/**
	 * Nombra los hilos sin incluir claves ni rutas en el nombre.
	 */
	private static final class FabricaHilosEscaner implements ThreadFactory {

		private final AtomicInteger contador = new AtomicInteger();

		@Override
		public Thread newThread(Runnable tarea) {
			Thread hilo = new Thread(tarea);
			hilo.setName("cd-escaner-nube-" + contador.incrementAndGet());
			hilo.setDaemon(true);
			return hilo;
		}
	}
}
