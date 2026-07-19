package com.asbestosstar.crashdetector.gui.tipos.bittorrent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.bittorrent.ConfiguracionBitTorrent;
import com.asbestosstar.crashdetector.bittorrent.CreadorTorrentV1;
import com.asbestosstar.crashdetector.bittorrent.EstadoTorrent;
import com.asbestosstar.crashdetector.bittorrent.MotorBitTorrent;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * GUI base del cliente BitTorrent para intercambio de archivos de soporte.
 *
 * Las implementaciones concretas aportan el tema visual y los ConfigColor. La
 * lógica de torrent, magnet, trackers y sesiones vive aquí.
 */
public abstract class BitTorrentGUI extends JFrame implements BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static final Map<String, Supplier<BitTorrentGUI>> GUIS = new HashMap<String, Supplier<BitTorrentGUI>>();

	public static final int PESTANA_ABRIR = 0;
	public static final int PESTANA_CREAR = 1;
	public static final int PESTANA_TRANSFERENCIAS = 2;
	public static final int PESTANA_CONFIGURACION = 3;

	/**
	 * Argumentos tipados para abrir la GUI en una pestaña concreta y rellenar sus
	 * campos sin depender del orden de parámetros Object.
	 */
	public static final class ArgumentosConstruccion {

		private int pestana = PESTANA_ABRIR;
		private Path archivoOrigen;
		private Path archivoTorrentEntrada;
		private Path directorioDestino;
		private Path archivoTorrentSalida;
		private String magnet;
		private Boolean privacidadConfirmada;

		private ArgumentosConstruccion() {
		}

		/**
		 * Prepara la pestaña de creación para compartir un archivo o directorio.
		 */
		public static ArgumentosConstruccion paraCompartir(Path archivoOrigen, boolean privacidadConfirmada) {
			ArgumentosConstruccion argumentos = new ArgumentosConstruccion();
			argumentos.pestana = PESTANA_CREAR;
			argumentos.archivoOrigen = normalizar(archivoOrigen);
			argumentos.privacidadConfirmada = Boolean.valueOf(privacidadConfirmada);
			return argumentos;
		}

		/**
		 * Prepara la pestaña de descarga usando un archivo .torrent.
		 */
		public static ArgumentosConstruccion paraAbrirTorrent(Path archivoTorrent, Path directorioDestino,
				boolean privacidadConfirmada) {
			ArgumentosConstruccion argumentos = new ArgumentosConstruccion();
			argumentos.pestana = PESTANA_ABRIR;
			argumentos.archivoTorrentEntrada = normalizar(archivoTorrent);
			argumentos.directorioDestino = normalizar(directorioDestino);
			argumentos.privacidadConfirmada = Boolean.valueOf(privacidadConfirmada);
			return argumentos;
		}

		/**
		 * Prepara la pestaña de descarga usando un enlace magnet.
		 */
		public static ArgumentosConstruccion paraAbrirMagnet(String magnet, Path directorioDestino,
				boolean privacidadConfirmada) {
			ArgumentosConstruccion argumentos = new ArgumentosConstruccion();
			argumentos.pestana = PESTANA_ABRIR;
			argumentos.magnet = magnet == null ? "" : magnet.trim();
			argumentos.directorioDestino = normalizar(directorioDestino);
			argumentos.privacidadConfirmada = Boolean.valueOf(privacidadConfirmada);
			return argumentos;
		}

		public ArgumentosConstruccion conArchivoTorrentSalida(Path archivoTorrentSalida) {
			this.archivoTorrentSalida = normalizar(archivoTorrentSalida);
			return this;
		}

		public ArgumentosConstruccion conPestana(int pestana) {
			this.pestana = pestana;
			return this;
		}

		private static Path normalizar(Path ruta) {
			return ruta == null ? null : ruta.toAbsolutePath().normalize();
		}
	}

	protected JPanel panelRaiz;
	protected JPanel panelCabecera;
	protected JPanel panelPrivacidad;
	protected JLabel imagenTema;
	protected JTextArea areaPrivacidad;
	protected JCheckBox confirmarPrivacidad;
	protected JTabbedPane pestanas;

	protected JTextArea campoMagnet;
	protected JTextField campoTorrentEntrada;
	protected JTextField campoDestino;
	protected JCheckBox seguirCompartiendoDescarga;
	protected JButton botonElegirTorrent;
	protected JButton botonElegirDestino;
	protected JButton botonIniciarDescarga;

	protected JTextField campoOrigen;
	protected JTextField campoTorrentSalida;
	protected JButton botonElegirOrigen;
	protected JButton botonElegirSalida;
	protected JButton botonCrearCompartir;
	protected JTextArea campoMagnetCreado;
	protected JButton botonCopiarMagnet;
	protected JProgressBar progresoCreacion;

	protected JTextArea areaTrackers;
	protected JCheckBox casillaDht;
	protected JCheckBox casillaPex;
	protected JCheckBox casillaLsd;
	protected JTextField campoPuerto;
	protected JTextField campoHilosHash;
	protected JButton botonGuardarConfiguracion;

	protected JTable tablaTransferencias;
	protected ModeloTransferencias modeloTransferencias;
	protected JButton botonDetenerSeleccionada;
	protected JButton botonActualizarTransferencias;

	protected volatile boolean inicializada;
	protected volatile boolean trabajando;
	protected Timer temporizador;

	@Override
	public TipoGUI<BitTorrentGUI> tipo() {
		return TipoGUI.BITTORRENT;
	}

	@Override
	public void init() {
		constructir();
	}

	/**
	 * Construye o muestra la GUI y aplica argumentos opcionales.
	 *
	 * Los argumentos reconocidos son instancias de ArgumentosConstruccion. También
	 * se acepta un Integer para seleccionar directamente una pestaña.
	 */
	public void constructir(Object... argumentos) {
		inicializarComponentesSiHaceFalta();
		recargarTextos();
		recargarApariencia();
		aplicarArgumentosConstruccion(argumentos);

		setVisible(true);
		toFront();
		requestFocus();

		if (pestanas != null && pestanas.getSelectedIndex() == PESTANA_CREAR && campoOrigen != null) {
			campoOrigen.requestFocusInWindow();
		}
	}

	private void inicializarComponentesSiHaceFalta() {
		if (inicializada) {
			return;
		}

		inicializada = true;
		setTitle(MonitorDePID.idioma.bittorrentTitulo());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(1100, 760);
		setMinimumSize(new Dimension(850, 620));
		setLocationByPlatform(true);

		panelRaiz = new JPanel(new BorderLayout(10, 10));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		crearCabecera();
		crearPestanas();
		setContentPane(panelRaiz);
		iniciarTemporizador();
	}

	private void aplicarArgumentosConstruccion(Object... argumentos) {
		if (argumentos == null) {
			return;
		}

		for (Object argumento : argumentos) {
			if (argumento instanceof ArgumentosConstruccion) {
				aplicarArgumentosConstruccion((ArgumentosConstruccion) argumento);
			} else if (argumento instanceof Integer) {
				seleccionarPestana(((Integer) argumento).intValue());
			}
		}
	}

	private void aplicarArgumentosConstruccion(ArgumentosConstruccion argumentos) {
		if (argumentos == null) {
			return;
		}

		if (argumentos.privacidadConfirmada != null && confirmarPrivacidad != null) {
			confirmarPrivacidad.setSelected(argumentos.privacidadConfirmada.booleanValue());
		}

		if (argumentos.archivoOrigen != null && campoOrigen != null) {
			campoOrigen.setText(argumentos.archivoOrigen.toString());

			Path salidaSugerida = argumentos.archivoTorrentSalida != null ? argumentos.archivoTorrentSalida
					: sugerirArchivoTorrent(argumentos.archivoOrigen);

			if (salidaSugerida != null && campoTorrentSalida != null) {
				campoTorrentSalida.setText(salidaSugerida.toString());
			}
		} else if (argumentos.archivoTorrentSalida != null && campoTorrentSalida != null) {
			campoTorrentSalida.setText(argumentos.archivoTorrentSalida.toString());
		}

		if (argumentos.archivoTorrentEntrada != null && campoTorrentEntrada != null) {
			campoTorrentEntrada.setText(argumentos.archivoTorrentEntrada.toString());
		}

		if (argumentos.directorioDestino != null && campoDestino != null) {
			campoDestino.setText(argumentos.directorioDestino.toString());
		}

		if (argumentos.magnet != null && campoMagnet != null) {
			campoMagnet.setText(argumentos.magnet);
		}

		seleccionarPestana(argumentos.pestana);
	}

	private void seleccionarPestana(int indice) {
		if (pestanas == null || indice < 0 || indice >= pestanas.getTabCount()) {
			return;
		}
		pestanas.setSelectedIndex(indice);
	}

	private Path sugerirArchivoTorrent(Path origen) {
		if (origen == null) {
			return null;
		}

		Path normal = origen.toAbsolutePath().normalize();
		Path nombre = normal.getFileName();
		String nombreTorrent = (nombre == null ? "compartido" : nombre.toString()) + ".torrent";
		Path padre = normal.getParent();

		return padre == null ? Paths.get(nombreTorrent).toAbsolutePath().normalize()
				: padre.resolve(nombreTorrent).toAbsolutePath().normalize();
	}

	private void crearCabecera() {
		panelCabecera = new JPanel(new BorderLayout(12, 8));
		imagenTema = new JLabel();
		imagenTema.setPreferredSize(new Dimension(300, 170));
		imagenTema.setHorizontalAlignment(JLabel.CENTER);

		panelPrivacidad = new JPanel(new BorderLayout(6, 6));
		areaPrivacidad = new JTextArea();
		areaPrivacidad.setEditable(false);
		areaPrivacidad.setLineWrap(true);
		areaPrivacidad.setWrapStyleWord(true);
		areaPrivacidad.setRows(7);
		areaPrivacidad.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		confirmarPrivacidad = new JCheckBox();

		panelPrivacidad.add(new JScrollPane(areaPrivacidad), BorderLayout.CENTER);
		panelPrivacidad.add(confirmarPrivacidad, BorderLayout.SOUTH);
		panelCabecera.add(imagenTema, BorderLayout.WEST);
		panelCabecera.add(panelPrivacidad, BorderLayout.CENTER);
		panelRaiz.add(panelCabecera, BorderLayout.NORTH);
	}

	private void crearPestanas() {
		pestanas = new JTabbedPane();
		pestanas.addTab("", crearPanelAbrir());
		pestanas.addTab("", crearPanelCrear());
		pestanas.addTab("", crearPanelTransferencias());
		pestanas.addTab("", crearPanelConfiguracion());
		panelRaiz.add(pestanas, BorderLayout.CENTER);
	}

	private JPanel crearPanelAbrir() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = restricciones();

		campoMagnet = new JTextArea(4, 60);
		campoMagnet.setLineWrap(true);
		campoMagnet.setWrapStyleWord(true);
		campoTorrentEntrada = new JTextField();
		campoDestino = new JTextField();
		seguirCompartiendoDescarga = new JCheckBox();
		seguirCompartiendoDescarga.setSelected(ConfiguracionBitTorrent.SEGUIR_COMPARTIENDO.obtener());
		botonElegirTorrent = new JButton();
		botonElegirDestino = new JButton();
		botonIniciarDescarga = new JButton();

		agregarFila(panel, c, 0, new JLabel(), new JScrollPane(campoMagnet), null, "etiquetaMagnet");
		agregarFila(panel, c, 1, new JLabel(), campoTorrentEntrada, botonElegirTorrent, "etiquetaTorrentEntrada");
		agregarFila(panel, c, 2, new JLabel(), campoDestino, botonElegirDestino, "etiquetaDestino");

		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(seguirCompartiendoDescarga, c);

		c.gridy = 4;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		panel.add(botonIniciarDescarga, c);

		c.gridy = 5;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		panel.add(new JPanel(), c);

		botonElegirTorrent.addActionListener(e -> elegirTorrentEntrada());
		botonElegirDestino.addActionListener(e -> elegirDestino());
		botonIniciarDescarga.addActionListener(e -> iniciarDescarga());
		return panel;
	}

	private JPanel crearPanelCrear() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = restricciones();

		campoOrigen = new JTextField();
		campoTorrentSalida = new JTextField();
		botonElegirOrigen = new JButton();
		botonElegirSalida = new JButton();
		botonCrearCompartir = new JButton();
		campoMagnetCreado = new JTextArea(5, 60);
		campoMagnetCreado.setEditable(false);
		campoMagnetCreado.setLineWrap(true);
		campoMagnetCreado.setWrapStyleWord(true);
		botonCopiarMagnet = new JButton();
		progresoCreacion = new JProgressBar(0, 100);
		progresoCreacion.setStringPainted(true);

		agregarFila(panel, c, 0, new JLabel(), campoOrigen, botonElegirOrigen, "etiquetaOrigen");
		agregarFila(panel, c, 1, new JLabel(), campoTorrentSalida, botonElegirSalida, "etiquetaTorrentSalida");

		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(progresoCreacion, c);

		c.gridy = 3;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		panel.add(botonCrearCompartir, c);

		c.gridy = 4;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1.0;
		panel.add(new JScrollPane(campoMagnetCreado), c);

		c.gridy = 5;
		c.fill = GridBagConstraints.NONE;
		c.weighty = 0.0;
		panel.add(botonCopiarMagnet, c);

		botonElegirOrigen.addActionListener(e -> elegirOrigen());
		botonElegirSalida.addActionListener(e -> elegirTorrentSalida());
		botonCrearCompartir.addActionListener(e -> crearYCompartir());
		botonCopiarMagnet.addActionListener(e -> copiarMagnetCreado());
		return panel;
	}

	private JPanel crearPanelTransferencias() {
		JPanel panel = new JPanel(new BorderLayout(6, 6));
		modeloTransferencias = new ModeloTransferencias();
		tablaTransferencias = new JTable(modeloTransferencias);
		tablaTransferencias.setAutoCreateRowSorter(true);
		tablaTransferencias.setFillsViewportHeight(true);

		botonDetenerSeleccionada = new JButton();
		botonActualizarTransferencias = new JButton();
		JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
		botones.add(botonDetenerSeleccionada);
		botones.add(botonActualizarTransferencias);

		panel.add(new JScrollPane(tablaTransferencias), BorderLayout.CENTER);
		panel.add(botones, BorderLayout.SOUTH);

		botonDetenerSeleccionada.addActionListener(e -> detenerSeleccionada());
		botonActualizarTransferencias.addActionListener(e -> actualizarTransferencias());
		return panel;
	}

	private JPanel crearPanelConfiguracion() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = restricciones();

		areaTrackers = new JTextArea(10, 60);
		areaTrackers.setLineWrap(false);
		areaTrackers.setText(ConfiguracionBitTorrent.TRACKERS.obtener());
		casillaDht = new JCheckBox();
		casillaDht.setSelected(false);
		casillaDht.setEnabled(false);
		casillaDht.setVisible(false);
		casillaPex = new JCheckBox();
		casillaPex.setSelected(false);
		casillaPex.setEnabled(false);
		casillaPex.setVisible(false);
		casillaLsd = new JCheckBox();
		casillaLsd.setSelected(false);
		casillaLsd.setEnabled(false);
		casillaLsd.setVisible(false);
		campoPuerto = new JTextField(ConfiguracionBitTorrent.PUERTO.obtener());
		campoHilosHash = new JTextField(ConfiguracionBitTorrent.HILOS_HASH.obtener());
		botonGuardarConfiguracion = new JButton();

		agregarFila(panel, c, 0, new JLabel(), new JScrollPane(areaTrackers), null, "etiquetaTrackers");
		agregarFila(panel, c, 1, new JLabel(), campoPuerto, null, "etiquetaPuerto");
		agregarFila(panel, c, 2, new JLabel(), campoHilosHash, null, "etiquetaHilosHash");

		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.NONE;
		panel.add(botonGuardarConfiguracion, c);
		c.gridy = 4;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		panel.add(new JPanel(), c);

		botonGuardarConfiguracion.addActionListener(e -> guardarConfiguracion());
		return panel;
	}

	private void agregarFila(JPanel panel, GridBagConstraints base, int fila, JLabel etiqueta,
			java.awt.Component centro, java.awt.Component derecha, String nombreEtiqueta) {
		etiqueta.setName(nombreEtiqueta);
		GridBagConstraints c = (GridBagConstraints) base.clone();
		c.gridy = fila;
		c.gridx = 0;
		c.weightx = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;
		panel.add(etiqueta, c);

		c.gridx = 1;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(centro, c);

		if (derecha != null) {
			c.gridx = 2;
			c.weightx = 0.0;
			c.fill = GridBagConstraints.NONE;
			panel.add(derecha, c);
		}
	}

	private GridBagConstraints restricciones() {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(6, 6, 6, 6);
		c.anchor = GridBagConstraints.WEST;
		return c;
	}

	private boolean validarPrivacidad() {
		if (confirmarPrivacidad.isSelected()) {
			return true;
		}
		JOptionPane.showMessageDialog(this, MonitorDePID.idioma.bittorrentDebeAceptarPrivacidad(),
				MonitorDePID.idioma.bittorrentPrivacidadTitulo(), JOptionPane.WARNING_MESSAGE);
		return false;
	}

	private void elegirTorrentEntrada() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(MonitorDePID.idioma.bittorrentElegirTorrent());
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			campoTorrentEntrada.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}

	private void elegirDestino() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle(MonitorDePID.idioma.bittorrentElegirDestino());
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			campoDestino.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}

	private void elegirOrigen() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setDialogTitle(MonitorDePID.idioma.bittorrentElegirOrigen());
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File seleccionado = chooser.getSelectedFile();
			campoOrigen.setText(seleccionado.getAbsolutePath());
			campoTorrentSalida.setText(
					new File(seleccionado.getParentFile(), seleccionado.getName() + ".torrent").getAbsolutePath());
		}
	}

	private void elegirTorrentSalida() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(MonitorDePID.idioma.bittorrentGuardarTorrent());
		if (!campoTorrentSalida.getText().trim().isEmpty()) {
			chooser.setSelectedFile(new File(campoTorrentSalida.getText().trim()));
		}
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			campoTorrentSalida.setText(asegurarTorrent(chooser.getSelectedFile()).getAbsolutePath());
		}
	}

	private void iniciarDescarga() {
		if (!validarPrivacidad() || trabajando) {
			return;
		}
		final Path destino = ruta(campoDestino.getText());
		final String magnet = campoMagnet.getText().trim();
		final String torrentTexto = campoTorrentEntrada.getText().trim();
		final boolean seguir = seguirCompartiendoDescarga.isSelected();

		if (destino == null) {
			mostrarError(MonitorDePID.idioma.bittorrentDestinoNoValido());
			return;
		}
		if (magnet.isEmpty() && torrentTexto.isEmpty()) {
			mostrarError(MonitorDePID.idioma.bittorrentFaltaTorrentOMagnet());
			return;
		}

		ejecutarTrabajo(new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				guardarConfiguracionInterna();
				if (!torrentTexto.isEmpty()) {
					MotorBitTorrent.obtenerInstancia().abrirTorrent(Paths.get(torrentTexto), destino, seguir);
				} else {
					MotorBitTorrent.obtenerInstancia().descargarMagnet(magnet, destino, seguir);
				}
				return null;
			}

			@Override
			protected void done() {
				finalizarTrabajo(this, MonitorDePID.idioma.bittorrentTransferenciaIniciada());
			}
		});
	}

	private void crearYCompartir() {
		if (!validarPrivacidad() || trabajando) {
			return;
		}
		final Path origen = ruta(campoOrigen.getText());
		final Path salida = ruta(campoTorrentSalida.getText());

		if (origen == null || salida == null) {
			mostrarError(MonitorDePID.idioma.bittorrentOrigenNoValido());
			return;
		}
		if (Files.exists(salida)) {
			int respuesta = JOptionPane.showConfirmDialog(this,
					MonitorDePID.idioma.bittorrentSobrescribirTorrent(salida.toString()),
					MonitorDePID.idioma.bittorrentCrearCompartir(), JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (respuesta != JOptionPane.YES_OPTION) {
				return;
			}
		}

		trabajando = true;
		setControlesHabilitados(false);
		progresoCreacion.setValue(0);

		SwingWorker<CreadorTorrentV1.Resultado, Integer> worker = new SwingWorker<CreadorTorrentV1.Resultado, Integer>() {
			@Override
			protected CreadorTorrentV1.Resultado doInBackground() throws Exception {
				guardarConfiguracionInterna();
				if (ConfiguracionBitTorrent.obtenerTrackers().isEmpty()) {
					throw new IllegalStateException(MonitorDePID.idioma.bittorrentSinTrackersNiDht());
				}
				CreadorTorrentV1.Resultado resultado = CreadorTorrentV1.crear(origen,
						ConfiguracionBitTorrent.obtenerTrackers(), false, (procesados, total) -> {
							int valor = total <= 0L ? 0 : (int) Math.min(100L, (procesados * 100L) / total);
							publish(Integer.valueOf(valor));
						});
				Path padre = salida.toAbsolutePath().normalize().getParent();
				if (padre != null) {
					Files.createDirectories(padre);
				}
				Files.write(salida, resultado.bytesTorrent());
				Path raiz = origen.toAbsolutePath().normalize().getParent();
				if (raiz == null) {
					raiz = origen.toAbsolutePath().normalize();
				}
				MotorBitTorrent.obtenerInstancia().sembrarTorrent(salida, raiz, resultado.nombre());
				return resultado;
			}

			@Override
			protected void process(List<Integer> valores) {
				if (!valores.isEmpty()) {
					progresoCreacion.setValue(valores.get(valores.size() - 1).intValue());
				}
			}

			@Override
			protected void done() {
				try {
					CreadorTorrentV1.Resultado resultado = get();
					campoMagnetCreado.setText(resultado.magnet());
					progresoCreacion.setValue(100);
					JOptionPane.showMessageDialog(BitTorrentGUI.this,
							MonitorDePID.idioma.bittorrentTorrentCreado(salida.toString()),
							MonitorDePID.idioma.bittorrentCrearCompartir(), JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					Throwable causa = e.getCause() == null ? e : e.getCause();
					CrashDetectorLogger.logException(causa);
					mostrarError(MonitorDePID.idioma.bittorrentErrorDetalle(mensajeSeguro(causa)));
				} finally {
					trabajando = false;
					setControlesHabilitados(true);
					actualizarTransferencias();
				}
			}
		};
		worker.execute();
	}

	private void ejecutarTrabajo(SwingWorker<Void, Void> worker) {
		trabajando = true;
		setControlesHabilitados(false);
		worker.execute();
	}

	private void finalizarTrabajo(SwingWorker<?, ?> worker, String mensajeExito) {
		try {
			worker.get();
			JOptionPane.showMessageDialog(this, mensajeExito, MonitorDePID.idioma.bittorrentTitulo(),
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			Throwable causa = e.getCause() == null ? e : e.getCause();
			CrashDetectorLogger.logException(causa);
			mostrarError(MonitorDePID.idioma.bittorrentErrorDetalle(mensajeSeguro(causa)));
		} finally {
			trabajando = false;
			setControlesHabilitados(true);
			actualizarTransferencias();
		}
	}

	private void copiarMagnetCreado() {
		String magnet = campoMagnetCreado.getText().trim();
		if (magnet.isEmpty()) {
			return;
		}
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(magnet), null);
		JOptionPane.showMessageDialog(this, MonitorDePID.idioma.bittorrentMagnetCopiado());
	}

	private void guardarConfiguracion() {
		try {
			guardarConfiguracionInterna();
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.bittorrentConfiguracionGuardada());
		} catch (Exception e) {
			mostrarError(MonitorDePID.idioma.bittorrentErrorDetalle(mensajeSeguro(e)));
		}
	}

	private void guardarConfiguracionInterna() {
		ConfiguracionBitTorrent.guardarTrackers(areaTrackers.getText());
		ConfiguracionBitTorrent.USAR_DHT.escribir(false);
		ConfiguracionBitTorrent.USAR_PEX.escribir(false);
		ConfiguracionBitTorrent.USAR_LSD.escribir(false);
		ConfiguracionBitTorrent.PUERTO.escribir(campoPuerto.getText().trim());
		ConfiguracionBitTorrent.HILOS_HASH.escribir(campoHilosHash.getText().trim());
		ConfiguracionBitTorrent.SEGUIR_COMPARTIENDO.escribir(seguirCompartiendoDescarga.isSelected());
	}

	private void detenerSeleccionada() {
		int filaVista = tablaTransferencias.getSelectedRow();
		if (filaVista < 0) {
			return;
		}
		int fila = tablaTransferencias.convertRowIndexToModel(filaVista);
		EstadoTorrent estado = modeloTransferencias.obtener(fila);
		MotorBitTorrent.obtenerInstancia().detener(estado.id());
		actualizarTransferencias();
	}

	protected void actualizarTransferencias() {
		if (modeloTransferencias != null) {
			modeloTransferencias.establecer(MotorBitTorrent.obtenerInstancia().estados());
		}
	}

	private void iniciarTemporizador() {
		temporizador = new Timer(1000, e -> actualizarTransferencias());
		temporizador.start();
	}

	protected void setControlesHabilitados(boolean habilitados) {
		botonIniciarDescarga.setEnabled(habilitados);
		botonCrearCompartir.setEnabled(habilitados);
		botonGuardarConfiguracion.setEnabled(habilitados);
		botonElegirOrigen.setEnabled(habilitados);
		botonElegirSalida.setEnabled(habilitados);
		botonElegirTorrent.setEnabled(habilitados);
		botonElegirDestino.setEnabled(habilitados);
	}

	protected void recargarTextos() {
		setTitle(MonitorDePID.idioma.bittorrentTitulo());
		if (areaPrivacidad != null) {
			areaPrivacidad.setText(MonitorDePID.idioma.bittorrentPrivacidadAdvertencia());
		}
		if (confirmarPrivacidad != null) {
			confirmarPrivacidad.setText(MonitorDePID.idioma.bittorrentConfirmarPrivacidad());
		}
		if (pestanas != null) {
			pestanas.setTitleAt(0, MonitorDePID.idioma.bittorrentPestanaAbrir());
			pestanas.setTitleAt(1, MonitorDePID.idioma.bittorrentPestanaCrear());
			pestanas.setTitleAt(2, MonitorDePID.idioma.bittorrentPestanaTransferencias());
			pestanas.setTitleAt(3, MonitorDePID.idioma.bittorrentPestanaConfiguracion());
		}
		actualizarEtiquetas(panelRaiz);
		if (botonElegirTorrent != null)
			botonElegirTorrent.setText(MonitorDePID.idioma.bittorrentExaminar());
		if (botonElegirDestino != null)
			botonElegirDestino.setText(MonitorDePID.idioma.bittorrentExaminar());
		if (botonIniciarDescarga != null)
			botonIniciarDescarga.setText(MonitorDePID.idioma.bittorrentIniciarDescarga());
		if (seguirCompartiendoDescarga != null)
			seguirCompartiendoDescarga.setText(MonitorDePID.idioma.bittorrentSeguirCompartiendo());
		if (botonElegirOrigen != null)
			botonElegirOrigen.setText(MonitorDePID.idioma.bittorrentExaminar());
		if (botonElegirSalida != null)
			botonElegirSalida.setText(MonitorDePID.idioma.bittorrentExaminar());
		if (botonCrearCompartir != null)
			botonCrearCompartir.setText(MonitorDePID.idioma.bittorrentCrearCompartir());
		if (botonCopiarMagnet != null)
			botonCopiarMagnet.setText(MonitorDePID.idioma.bittorrentCopiarMagnet());
		if (casillaDht != null)
			casillaDht.setText(MonitorDePID.idioma.bittorrentUsarDht());
		if (casillaPex != null)
			casillaPex.setText(MonitorDePID.idioma.bittorrentUsarPex());
		if (casillaLsd != null)
			casillaLsd.setText(MonitorDePID.idioma.bittorrentUsarLsd());
		if (botonGuardarConfiguracion != null)
			botonGuardarConfiguracion.setText(MonitorDePID.idioma.bittorrentGuardarConfiguracion());
		if (botonDetenerSeleccionada != null)
			botonDetenerSeleccionada.setText(MonitorDePID.idioma.bittorrentDetenerSeleccionada());
		if (botonActualizarTransferencias != null)
			botonActualizarTransferencias.setText(MonitorDePID.idioma.bittorrentActualizar());
		if (modeloTransferencias != null)
			modeloTransferencias.fireTableStructureChanged();
	}

	private void actualizarEtiquetas(java.awt.Container contenedor) {
		if (contenedor == null)
			return;
		for (java.awt.Component componente : contenedor.getComponents()) {
			if (componente instanceof JLabel) {
				JLabel etiqueta = (JLabel) componente;
				String nombre = etiqueta.getName();
				if ("etiquetaMagnet".equals(nombre))
					etiqueta.setText(MonitorDePID.idioma.bittorrentMagnet());
				if ("etiquetaTorrentEntrada".equals(nombre))
					etiqueta.setText(MonitorDePID.idioma.bittorrentArchivoTorrent());
				if ("etiquetaDestino".equals(nombre))
					etiqueta.setText(MonitorDePID.idioma.bittorrentDestino());
				if ("etiquetaOrigen".equals(nombre))
					etiqueta.setText(MonitorDePID.idioma.bittorrentOrigen());
				if ("etiquetaTorrentSalida".equals(nombre))
					etiqueta.setText(MonitorDePID.idioma.bittorrentArchivoTorrentSalida());
				if ("etiquetaTrackers".equals(nombre))
					etiqueta.setText(MonitorDePID.idioma.bittorrentTrackers());
				if ("etiquetaPuerto".equals(nombre))
					etiqueta.setText(MonitorDePID.idioma.bittorrentPuerto());
				if ("etiquetaHilosHash".equals(nombre))
					etiqueta.setText(MonitorDePID.idioma.bittorrentHilosHash());
			}
			if (componente instanceof java.awt.Container)
				actualizarEtiquetas((java.awt.Container) componente);
		}
	}

	private Path ruta(String texto) {
		try {
			return texto == null || texto.trim().isEmpty() ? null
					: Paths.get(texto.trim()).toAbsolutePath().normalize();
		} catch (Exception e) {
			return null;
		}
	}

	private File asegurarTorrent(File archivo) {
		if (archivo.getName().toLowerCase(java.util.Locale.ROOT).endsWith(".torrent"))
			return archivo;
		return new File(archivo.getParentFile(), archivo.getName() + ".torrent");
	}

	private void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
	}

	private String mensajeSeguro(Throwable t) {
		String mensaje = t == null ? "" : t.getMessage();
		if (mensaje == null || mensaje.trim().isEmpty())
			mensaje = t == null ? "" : t.getClass().getSimpleName();
		mensaje = mensaje.replace('\r', ' ').replace('\n', ' ').trim();
		return mensaje.length() > 1000 ? mensaje.substring(0, 1000) : mensaje;
	}

	@Override
	public abstract String id();

	@Override
	public abstract void recargarApariencia();

	@Override
	public abstract List<ElementoConfig> obtenerElementosConfigs();

	protected final class ModeloTransferencias extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private final List<EstadoTorrent> filas = new ArrayList<EstadoTorrent>();

		void establecer(List<EstadoTorrent> nuevos) {
			filas.clear();
			filas.addAll(nuevos);
			fireTableDataChanged();
		}

		EstadoTorrent obtener(int fila) {
			return filas.get(fila);
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
				return MonitorDePID.idioma.bittorrentColNombre();
			case 1:
				return MonitorDePID.idioma.bittorrentColTipo();
			case 2:
				return MonitorDePID.idioma.bittorrentColEstado();
			case 3:
				return MonitorDePID.idioma.bittorrentColProgreso();
			case 4:
				return MonitorDePID.idioma.bittorrentColPares();
			case 5:
				return MonitorDePID.idioma.bittorrentColDescargado();
			default:
				return MonitorDePID.idioma.bittorrentColSubido();
			}
		}

		@Override
		public Class<?> getColumnClass(int columna) {
			if (columna == 3)
				return Double.class;
			if (columna == 4)
				return Integer.class;
			if (columna >= 5)
				return Long.class;
			return String.class;
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			EstadoTorrent e = filas.get(fila);
			switch (columna) {
			case 0:
				return e.nombre();
			case 1:
				return e.tipo();
			case 2:
				return e.estado();
			case 3:
				return Double.valueOf(e.porcentaje());
			case 4:
				return Integer.valueOf(e.pares());
			case 5:
				return Long.valueOf(e.descargado());
			default:
				return Long.valueOf(e.subido());
			}
		}
	}
}