package com.asbestosstar.crashdetector.gui.tipos.generador_parches;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.parches.proyecto.ConfiguracionProyectoParche;
import com.asbestosstar.crashdetector.parches.proyecto.LocalizadorJdk;
import com.asbestosstar.crashdetector.parches.proyecto.ResultadoCompilacionParche;
import com.asbestosstar.crashdetector.parches.proyecto.ServicioProyectosParche;

/**
 * GUI funcional para crear y compilar extensiones de parches.
 *
 * La implementación concreta aporta solamente imagen, colores y apariencia.
 */
public abstract class GeneradorParchesGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static final Map<String, Supplier<GeneradorParchesGUI>> GUIS = new HashMap<String, Supplier<GeneradorParchesGUI>>();

	public static final String URL_RED_HAT_JDK = "https://developers.redhat.com/products/openjdk/download";
	public static final String URL_TRIBBLE_JDK = "https://pkgs.tribblix.org/openjdk/";

	public JPanel panelRaiz;
	public JPanel panelFormulario;
	public JPanel panelBotones;
	public JPanel panelEstado;
	public JPanel panelTema;

	public JTextField txtDirectorioProyecto;
	public JTextField txtNombreProyecto;
	public JTextField txtPaqueteBase;
	public JTextField txtClaseObjetivo;
	public JTextField txtJdk;

	public JTextArea txtDescripcion;
	public JTextArea txtInstruccionMcp;
	public JLabel lblEstado;

	public JButton btnSeleccionarDirectorio;
	public JButton btnSeleccionarJdk;
	public JButton btnRedHat;
	public JButton btnTribble;
	public JButton btnCrear;
	public JButton btnCompilar;
	public JButton btnAbrirCarpeta;

	public volatile boolean inicializada;
	public volatile File ultimoJar;

	@Override
	public TipoGUI tipo() {
		return TipoGUI.GENERADOR_DE_PARCHES;
	}

	@Override
	public void init() {
		construir(null);
	}

	/**
	 * Construye o vuelve a mostrar la GUI y permite precargar una carpeta.
	 */
	public void construir(File directorioProyecto) {
		if (inicializada) {
			if (directorioProyecto != null) {
				txtDirectorioProyecto.setText(directorioProyecto.getAbsolutePath());
			}
			recargarApariencia();
			setVisible(true);
			toFront();
			requestFocus();
			return;
		}

		inicializada = true;
		setTitle(MonitorDePID.idioma.generadorParchesTitulo());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(1120, 720);
		setMinimumSize(new Dimension(900, 620));
		setLocationRelativeTo(null);

		panelRaiz = new JPanel(new BorderLayout(14, 14));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
		setContentPane(panelRaiz);

		panelTema = crearPanelTematico();
		if (panelTema != null) {
			panelTema.setPreferredSize(new Dimension(285, 500));
			panelRaiz.add(panelTema, BorderLayout.WEST);
		}

		JPanel contenido = new JPanel(new BorderLayout(10, 10));
		panelRaiz.add(contenido, BorderLayout.CENTER);

		txtDescripcion = crearAreaTexto(MonitorDePID.idioma.generadorParchesDescripcion(), 4);
		contenido.add(new JScrollPane(txtDescripcion), BorderLayout.NORTH);

		panelFormulario = new JPanel();
		panelFormulario.setLayout(new javax.swing.BoxLayout(panelFormulario, javax.swing.BoxLayout.Y_AXIS));
		panelFormulario.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		txtDirectorioProyecto = new JTextField();
		txtNombreProyecto = new JTextField("CrashDetectorPatch");
		txtPaqueteBase = new JTextField("com.example.patch");
		txtClaseObjetivo = new JTextField("com.example.TargetClass");
		txtJdk = new JTextField();

		File jdkActual = ServicioProyectosParche.obtenerJdkActivo();
		if (jdkActual != null) {
			txtJdk.setText(jdkActual.getAbsolutePath());
		}

		File proyectoInicial = directorioProyecto;
		if (proyectoInicial == null) {
			proyectoInicial = ServicioProyectosParche.obtenerProyectoActivo();
		}
		if (proyectoInicial == null) {
			proyectoInicial = new File(ServicioProyectosParche.carpetaProyectosPredeterminada(), "mi-parche");
		}
		txtDirectorioProyecto.setText(proyectoInicial.getAbsolutePath());

		btnSeleccionarDirectorio = new JButton(MonitorDePID.idioma.generadorParchesSeleccionar());
		btnSeleccionarJdk = new JButton(MonitorDePID.idioma.generadorParchesSeleccionar());
		btnRedHat = new JButton(MonitorDePID.idioma.generadorParchesDescargarRedHat());
		btnTribble = new JButton(MonitorDePID.idioma.generadorParchesDescargarTribble());

		panelFormulario.add(crearFila(MonitorDePID.idioma.generadorParchesDirectorio(), txtDirectorioProyecto,
				btnSeleccionarDirectorio));
		panelFormulario.add(crearFila(MonitorDePID.idioma.generadorParchesNombre(), txtNombreProyecto, null));
		panelFormulario.add(crearFila(MonitorDePID.idioma.generadorParchesPaquete(), txtPaqueteBase, null));
		panelFormulario.add(crearFila(MonitorDePID.idioma.generadorParchesClaseObjetivo(), txtClaseObjetivo, null));
		panelFormulario.add(crearFila(MonitorDePID.idioma.generadorParchesJdk(), txtJdk, btnSeleccionarJdk));

		JPanel descargas = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
		descargas.add(btnRedHat);
		descargas.add(btnTribble);
		panelFormulario.add(descargas);

		contenido.add(panelFormulario, BorderLayout.CENTER);

		JPanel sur = new JPanel(new BorderLayout(8, 8));
		panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
		btnCrear = new JButton(MonitorDePID.idioma.generadorParchesCrear());
		btnCompilar = new JButton(MonitorDePID.idioma.generadorParchesCompilar());
		btnAbrirCarpeta = new JButton(MonitorDePID.idioma.generadorParchesAbrirCarpeta());
		panelBotones.add(btnCrear);
		panelBotones.add(btnCompilar);
		panelBotones.add(btnAbrirCarpeta);
		sur.add(panelBotones, BorderLayout.NORTH);

		txtInstruccionMcp = crearAreaTexto(MonitorDePID.idioma.generadorParchesMcpInstruccion(), 3);
		sur.add(new JScrollPane(txtInstruccionMcp), BorderLayout.CENTER);

		panelEstado = new JPanel(new BorderLayout());
		lblEstado = new JLabel(MonitorDePID.idioma.generadorParchesListo(), SwingConstants.LEFT);
		lblEstado.setBorder(BorderFactory.createEmptyBorder(5, 7, 5, 7));
		panelEstado.add(lblEstado, BorderLayout.CENTER);
		sur.add(panelEstado, BorderLayout.SOUTH);
		contenido.add(sur, BorderLayout.SOUTH);

		btnSeleccionarDirectorio.addActionListener(e -> seleccionarDirectorioProyecto());
		btnSeleccionarJdk.addActionListener(e -> seleccionarJdk());
		btnRedHat.addActionListener(e -> abrirDescarga(URL_RED_HAT_JDK));
		btnTribble.addActionListener(e -> abrirDescarga(URL_TRIBBLE_JDK));
		btnCrear.addActionListener(e -> crearProyectoAsync());
		btnCompilar.addActionListener(e -> compilarProyectoAsync());
		btnAbrirCarpeta.addActionListener(e -> abrirCarpetaProyecto());

		recargarApariencia();
		setVisible(true);
	}

	private JTextArea crearAreaTexto(String texto, int filas) {
		JTextArea area = new JTextArea(texto, filas, 50);
		area.setEditable(false);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
		area.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		return area;
	}

	private JPanel crearFila(String etiqueta, JTextField campo, JButton boton) {
		JPanel fila = new JPanel(new BorderLayout(8, 4));
		JLabel label = new JLabel(etiqueta);
		label.setPreferredSize(new Dimension(175, 28));
		fila.add(label, BorderLayout.WEST);
		fila.add(campo, BorderLayout.CENTER);
		if (boton != null) {
			fila.add(boton, BorderLayout.EAST);
		}
		fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
		fila.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
		return fila;
	}

	public void seleccionarDirectorioProyecto() {
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle(MonitorDePID.idioma.generadorParchesSeleccionarDirectorioTitulo());
		selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		File actual = new File(txtDirectorioProyecto.getText().trim());
		selector.setCurrentDirectory(actual.isDirectory() ? actual : actual.getParentFile());
		if (selector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			txtDirectorioProyecto.setText(selector.getSelectedFile().getAbsolutePath());
		}
	}

	public void seleccionarJdk() {
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle(MonitorDePID.idioma.generadorParchesSeleccionarJdkTitulo());
		selector.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		File actual = new File(txtJdk.getText().trim());
		selector.setCurrentDirectory(actual.isDirectory() ? actual : actual.getParentFile());
		if (selector.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File jdk = LocalizadorJdk.normalizarJdk(selector.getSelectedFile());
		if (jdk == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.generadorParchesJdkInvalido(),
					MonitorDePID.idioma.generadorParchesTitulo(), JOptionPane.ERROR_MESSAGE);
			return;
		}
		txtJdk.setText(jdk.getAbsolutePath());
	}

	public void abrirDescarga(String url) {
		try {
			if (!Desktop.isDesktopSupported()) {
				throw new IllegalStateException("Desktop no disponible");
			}
			Desktop.getDesktop().browse(new URI(url));
			lblEstado.setText(MonitorDePID.idioma.generadorParchesDescargaAbierta());
		} catch (Throwable t) {
			mostrarError(t);
		}
	}

	public void crearProyectoAsync() {
		final File proyecto = new File(txtDirectorioProyecto.getText().trim());
		final ConfiguracionProyectoParche config = new ConfiguracionProyectoParche(txtNombreProyecto.getText(),
				txtPaqueteBase.getText(), "ExtensionCrashDetector", "ParcheGenerado", txtClaseObjetivo.getText());

		boolean permitir = false;
		if (new File(proyecto, ServicioProyectosParche.ARCHIVO_MARCADOR).isFile()) {
			int respuesta = JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.generadorParchesProyectoExiste(),
					MonitorDePID.idioma.generadorParchesTitulo(), JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (respuesta != JOptionPane.YES_OPTION) {
				return;
			}
			permitir = true;
		}

		final boolean permitirExistente = permitir;
		ejecutarTrabajo(MonitorDePID.idioma.generadorParchesCreando(), new TrabajoFondo<File>() {
			@Override
			public File ejecutar() throws Exception {
				return ServicioProyectosParche.crearProyecto(proyecto, config, permitirExistente);
			}

			@Override
			public void exito(File resultado) {
				txtDirectorioProyecto.setText(resultado.getAbsolutePath());
				lblEstado.setText(MonitorDePID.idioma.generadorParchesCreado(resultado.getAbsolutePath()));
				JOptionPane.showMessageDialog(GeneradorParchesGUI.this,
						MonitorDePID.idioma.generadorParchesCreado(resultado.getAbsolutePath()) + "\n\n"
								+ MonitorDePID.idioma.generadorParchesMcpInstruccion(),
						MonitorDePID.idioma.generadorParchesTitulo(), JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	public void compilarProyectoAsync() {
		final File proyecto = new File(txtDirectorioProyecto.getText().trim());
		final File jdk = LocalizadorJdk.normalizarJdk(new File(txtJdk.getText().trim()));
		if (jdk == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.generadorParchesJdkInvalido(),
					MonitorDePID.idioma.generadorParchesTitulo(), JOptionPane.ERROR_MESSAGE);
			return;
		}

		ejecutarTrabajo(MonitorDePID.idioma.generadorParchesCompilando(),
				new TrabajoFondo<ResultadoCompilacionParche>() {
					@Override
					public ResultadoCompilacionParche ejecutar() throws Exception {
						return ServicioProyectosParche.compilarProyecto(proyecto, jdk);
					}

					@Override
					public void exito(ResultadoCompilacionParche resultado) {
						if (!resultado.correcta) {
							JOptionPane.showMessageDialog(GeneradorParchesGUI.this,
									MonitorDePID.idioma.generadorParchesError(resultado.salidaCompilador),
									MonitorDePID.idioma.generadorParchesTitulo(), JOptionPane.ERROR_MESSAGE);
							lblEstado.setText(MonitorDePID.idioma.generadorParchesError(resultado.salidaCompilador));
							return;
						}

						ultimoJar = resultado.archivoJar;
						String ruta = resultado.archivoJar.getAbsolutePath();
						lblEstado.setText(MonitorDePID.idioma.generadorParchesCompilado(ruta));
						JOptionPane.showMessageDialog(GeneradorParchesGUI.this,
								MonitorDePID.idioma.generadorParchesCompilado(ruta) + "\n\n"
										+ MonitorDePID.idioma.generadorParchesMcpInstruccion(),
								MonitorDePID.idioma.generadorParchesTitulo(), JOptionPane.INFORMATION_MESSAGE);
					}
				});
	}

	private <T> void ejecutarTrabajo(String estado, final TrabajoFondo<T> trabajo) {
		setControlesHabilitados(false);
		lblEstado.setText(estado);

		SwingWorker<T, Void> worker = new SwingWorker<T, Void>() {
			@Override
			protected T doInBackground() throws Exception {
				return trabajo.ejecutar();
			}

			@Override
			protected void done() {
				try {
					trabajo.exito(get());
				} catch (Throwable t) {
					Throwable causa = t.getCause() == null ? t : t.getCause();
					mostrarError(causa);
				} finally {
					setControlesHabilitados(true);
				}
			}
		};
		worker.execute();
	}

	private void setControlesHabilitados(boolean valor) {
		btnCrear.setEnabled(valor);
		btnCompilar.setEnabled(valor);
		btnSeleccionarDirectorio.setEnabled(valor);
		btnSeleccionarJdk.setEnabled(valor);
		btnRedHat.setEnabled(valor);
		btnTribble.setEnabled(valor);
	}

	public void abrirCarpetaProyecto() {
		try {
			File proyecto = new File(txtDirectorioProyecto.getText().trim()).getCanonicalFile();
			if (!proyecto.isDirectory()) {
				throw new IllegalStateException(proyecto.getAbsolutePath());
			}
			Desktop.getDesktop().open(proyecto);
		} catch (Throwable t) {
			mostrarError(t);
		}
	}

	private void mostrarError(Throwable t) {
		CrashDetectorLogger.logException(t);
		String detalle = t == null || t.getMessage() == null ? "" : t.getMessage();
		lblEstado.setText(MonitorDePID.idioma.generadorParchesError(detalle));
		JOptionPane.showMessageDialog(this, MonitorDePID.idioma.generadorParchesError(detalle),
				MonitorDePID.idioma.generadorParchesTitulo(), JOptionPane.ERROR_MESSAGE);
	}

	public abstract JPanel crearPanelTematico();

	public abstract void aplicarApariencia();

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
		revalidate();
		repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		return new ArrayList<ElementoConfig>();
	}

	private static abstract class TrabajoFondo<T> {
		public abstract T ejecutar() throws Exception;

		public abstract void exito(T resultado);
	}
}
