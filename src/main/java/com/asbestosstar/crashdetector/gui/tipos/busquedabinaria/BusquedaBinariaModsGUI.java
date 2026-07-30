package com.asbestosstar.crashdetector.gui.tipos.busquedabinaria;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.lanzer.CDLauncher;

/**
 * GUI base para aislar un mod problemático mediante búsqueda binaria
 * reversible.
 */
public abstract class BusquedaBinariaModsGUI extends JFrame implements BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static final Map<String, Supplier<BusquedaBinariaModsGUI>> GUIS = new HashMap<String, Supplier<BusquedaBinariaModsGUI>>();

	protected final MotorBusquedaBinariaMods motor = new MotorBusquedaBinariaMods();

	protected JLabel imagenAnya;
	protected JLabel etiquetaTitulo;
	protected JTextArea areaDescripcion;
	protected JTextArea areaAdvertencia;
	protected JTextArea areaDependencias;
	protected JLabel etiquetaEstado;

	protected JLabel etiquetaCandidatos;
	protected JLabel etiquetaDesactivados;
	protected JLabel etiquetaHistorial;

	protected DefaultListModel<String> modeloCandidatos;
	protected DefaultListModel<String> modeloDesactivados;
	protected JList<String> listaCandidatos;
	protected JList<String> listaDesactivados;
	protected JTextArea areaHistorial;

	protected JButton botonIniciar;
	protected JButton botonPersiste;
	protected JButton botonDesaparecio;
	protected JButton botonRestaurar;

	protected JPanel panelRaiz;
	protected JPanel panelCabecera;
	protected JPanel panelInformacion;
	protected JPanel panelListas;
	protected JPanel panelBotones;

	private boolean inicializada;
	private SwingWorker<?, ?> workerActual;
	private Timer temporizadorProceso;
	private Process procesoObservado;
	private int rondaVisible;

	public BusquedaBinariaModsGUI() {
	}

	@Override
	public final void init() {
		if (inicializada) {
			actualizarTextos();
			recargarApariencia();
			setVisible(true);
			toFront();
			requestFocus();
			return;
		}

		inicializada = true;
		construirInterfaz();
		actualizarTextos();

		if (motor.hayRecuperacionPendiente()) {
			establecerEstado("recuperacion", 0, 0, 0, "");
			botonIniciar.setEnabled(false);
			botonRestaurar.setEnabled(true);
		} else {
			establecerEstado("sin_sesion", 0, 0, 0, "");
			establecerBotonesSinSesion();
		}

		recargarApariencia();
		setVisible(true);
	}

	private void construirInterfaz() {
		setTitle(MonitorDePID.idioma.busquedaBinariaModsTitulo());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(1100, 760);
		setMinimumSize(new Dimension(860, 620));
		setLocationRelativeTo(null);

		panelRaiz = new JPanel(new BorderLayout(10, 10));
		panelRaiz.setBorder(new EmptyBorder(12, 12, 12, 12));
		setContentPane(panelRaiz);

		panelCabecera = new JPanel(new BorderLayout(14, 8));
		imagenAnya = new JLabel();
		imagenAnya.setPreferredSize(new Dimension(190, 190));
		imagenAnya.setHorizontalAlignment(SwingConstants.CENTER);
		imagenAnya.setVerticalAlignment(SwingConstants.CENTER);

		panelInformacion = new JPanel();
		panelInformacion.setLayout(new javax.swing.BoxLayout(panelInformacion, javax.swing.BoxLayout.Y_AXIS));

		etiquetaTitulo = new JLabel();
		etiquetaTitulo.setFont(etiquetaTitulo.getFont().deriveFont(Font.BOLD, 24f));

		areaDescripcion = crearAreaInformativa();
		areaAdvertencia = crearAreaInformativa();
		areaDependencias = crearAreaInformativa();
		etiquetaEstado = new JLabel();
		etiquetaEstado.setFont(etiquetaEstado.getFont().deriveFont(Font.BOLD, 14f));
		etiquetaEstado.setBorder(new EmptyBorder(8, 2, 4, 2));

		panelInformacion.add(etiquetaTitulo);
		panelInformacion.add(javax.swing.Box.createVerticalStrut(6));
		panelInformacion.add(areaDescripcion);
		panelInformacion.add(javax.swing.Box.createVerticalStrut(4));
		panelInformacion.add(areaAdvertencia);
		panelInformacion.add(javax.swing.Box.createVerticalStrut(4));
		panelInformacion.add(areaDependencias);
		panelInformacion.add(javax.swing.Box.createVerticalStrut(4));
		panelInformacion.add(etiquetaEstado);

		panelCabecera.add(imagenAnya, BorderLayout.WEST);
		panelCabecera.add(panelInformacion, BorderLayout.CENTER);
		panelRaiz.add(panelCabecera, BorderLayout.NORTH);

		modeloCandidatos = new DefaultListModel<String>();
		modeloDesactivados = new DefaultListModel<String>();
		listaCandidatos = new JList<String>(modeloCandidatos);
		listaDesactivados = new JList<String>(modeloDesactivados);
		areaHistorial = new JTextArea();
		areaHistorial.setEditable(false);
		areaHistorial.setLineWrap(true);
		areaHistorial.setWrapStyleWord(true);

		etiquetaCandidatos = new JLabel();
		etiquetaDesactivados = new JLabel();
		etiquetaHistorial = new JLabel();

		JPanel candidatosPanel = crearPanelLista(etiquetaCandidatos, new JScrollPane(listaCandidatos));
		JPanel desactivadosPanel = crearPanelLista(etiquetaDesactivados, new JScrollPane(listaDesactivados));
		JPanel historialPanel = crearPanelLista(etiquetaHistorial, new JScrollPane(areaHistorial));

		JSplitPane izquierda = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, candidatosPanel, desactivadosPanel);
		izquierda.setResizeWeight(0.5);
		izquierda.setContinuousLayout(true);
		izquierda.setBorder(BorderFactory.createEmptyBorder());

		JSplitPane principal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, izquierda, historialPanel);
		principal.setResizeWeight(0.68);
		principal.setContinuousLayout(true);
		principal.setBorder(BorderFactory.createEmptyBorder());

		panelListas = new JPanel(new BorderLayout());
		panelListas.add(principal, BorderLayout.CENTER);
		panelRaiz.add(panelListas, BorderLayout.CENTER);

		panelBotones = new JPanel(new GridLayout(1, 4, 8, 0));
		botonIniciar = new JButton();
		botonPersiste = new JButton();
		botonDesaparecio = new JButton();
		botonRestaurar = new JButton();

		panelBotones.add(botonIniciar);
		panelBotones.add(botonPersiste);
		panelBotones.add(botonDesaparecio);
		panelBotones.add(botonRestaurar);
		panelRaiz.add(panelBotones, BorderLayout.SOUTH);

		botonIniciar.addActionListener(e -> iniciarBusqueda());
		botonPersiste.addActionListener(e -> registrarResultado(true));
		botonDesaparecio.addActionListener(e -> registrarResultado(false));
		botonRestaurar.addActionListener(e -> restaurarTodo());
	}

	private JTextArea crearAreaInformativa() {
		JTextArea area = new JTextArea();
		area.setEditable(false);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setOpaque(false);
		area.setBorder(BorderFactory.createEmptyBorder());
		area.setRows(2);
		return area;
	}

	private JPanel crearPanelLista(JLabel titulo, JScrollPane scroll) {
		JPanel panel = new JPanel(new BorderLayout(4, 4));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		titulo.setFont(titulo.getFont().deriveFont(Font.BOLD));
		panel.add(titulo, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);
		return panel;
	}

	protected final void actualizarTextos() {
		setTitle(MonitorDePID.idioma.busquedaBinariaModsTitulo());
		if (etiquetaTitulo == null) {
			return;
		}

		etiquetaTitulo.setText(MonitorDePID.idioma.busquedaBinariaModsTitulo());
		areaDescripcion.setText(MonitorDePID.idioma.busquedaBinariaModsDescripcion());
		areaAdvertencia.setText(MonitorDePID.idioma.busquedaBinariaModsAdvertenciaVariosMods());
		areaDependencias.setText(MonitorDePID.idioma.busquedaBinariaModsProteccionDependencias());

		etiquetaCandidatos.setText(MonitorDePID.idioma.busquedaBinariaModsCandidatos());
		etiquetaDesactivados.setText(MonitorDePID.idioma.busquedaBinariaModsDesactivados());
		etiquetaHistorial.setText(MonitorDePID.idioma.busquedaBinariaModsHistorial());

		botonIniciar.setText(MonitorDePID.idioma.busquedaBinariaModsIniciar());
		botonPersiste.setText(MonitorDePID.idioma.busquedaBinariaModsProblemaPersiste());
		botonDesaparecio.setText(MonitorDePID.idioma.busquedaBinariaModsProblemaDesaparecio());
		botonRestaurar.setText(MonitorDePID.idioma.busquedaBinariaModsRestaurar());
	}

	private void iniciarBusqueda() {
		if (ocupada() || hayJuegoActivo()) {
			establecerEstado("juego_activo", 0, 0, 0, "");
			return;
		}

		int respuesta = JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.busquedaBinariaModsConfirmacion(),
				MonitorDePID.idioma.busquedaBinariaModsTitulo(), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		if (respuesta != JOptionPane.YES_OPTION) {
			return;
		}

		establecerEstado("escaneando", 0, 0, 0, "");
		establecerBotonesOcupada();

		workerActual = new SwingWorker<MotorBusquedaBinariaMods.Prueba, Void>() {
			@Override
			protected MotorBusquedaBinariaMods.Prueba doInBackground() throws Exception {
				motor.iniciar();
				return motor.prepararPrimeraPrueba();
			}

			@Override
			protected void done() {
				try {
					MotorBusquedaBinariaMods.Prueba prueba = get();
					actualizarListas(prueba);
					lanzarPrueba(prueba);
				} catch (Exception e) {
					manejarError(e);
				} finally {
					workerActual = null;
				}
			}
		};
		workerActual.execute();
	}

	private void registrarResultado(final boolean persiste) {
		if (ocupada() || hayJuegoActivo()) {
			return;
		}

		agregarHistorial(MonitorDePID.idioma.busquedaBinariaModsEstado(persiste ? "ronda_persiste" : "ronda_desaparece",
				obtenerRondaVisible(), modeloCandidatos.getSize(), modeloDesactivados.getSize(), ""));

		establecerBotonesOcupada();
		establecerEstado("restaurando", 0, 0, 0, "");

		workerActual = new SwingWorker<MotorBusquedaBinariaMods.ResultadoRonda, Void>() {
			@Override
			protected MotorBusquedaBinariaMods.ResultadoRonda doInBackground() throws Exception {
				return motor.registrarResultado(persiste);
			}

			@Override
			protected void done() {
				try {
					MotorBusquedaBinariaMods.ResultadoRonda resultado = get();
					procesarResultadoRonda(resultado);
				} catch (Exception e) {
					manejarError(e);
				} finally {
					workerActual = null;
				}
			}
		};
		workerActual.execute();
	}

	private void procesarResultadoRonda(MotorBusquedaBinariaMods.ResultadoRonda resultado) {
		if (resultado.tipo == MotorBusquedaBinariaMods.ResultadoRonda.Tipo.CONTINUAR) {
			actualizarListas(resultado.siguiente);
			lanzarPrueba(resultado.siguiente);
			return;
		}

		vaciarDesactivados();
		actualizarCandidatos(resultado.resultado);

		if (resultado.tipo == MotorBusquedaBinariaMods.ResultadoRonda.Tipo.UNICO) {
			String mod = nombre(resultado.resultado.get(0));
			etiquetaEstado.setText(MonitorDePID.idioma.busquedaBinariaModsResultadoUnico(mod));
			agregarHistorial(etiquetaEstado.getText());
		} else if (resultado.tipo == MotorBusquedaBinariaMods.ResultadoRonda.Tipo.GRUPO) {
			String mods = unirNombres(resultado.resultado);
			etiquetaEstado.setText(MonitorDePID.idioma.busquedaBinariaModsResultadoGrupo(mods));
			agregarHistorial(MonitorDePID.idioma.busquedaBinariaModsEstado("sin_division", 0,
					resultado.resultado.size(), 0, mods));
		} else {
			establecerEstado("inconsistente", 0, 0, 0, "");
			agregarHistorial(etiquetaEstado.getText());
		}

		establecerBotonesFinal();
		recargarApariencia();
	}

	private void lanzarPrueba(MotorBusquedaBinariaMods.Prueba prueba) {
		establecerEstado("preparando", prueba.ronda, prueba.candidatos.size(), prueba.todosDesactivados.size(), "");
		establecerBotonesOcupada();

		try {
			Process anterior = CDLauncher.proceso_cdlauncher;
			establecerEstado("lanzando", prueba.ronda, prueba.candidatos.size(), prueba.todosDesactivados.size(), "");
			CDLauncher.lanzer();
			Process nuevo = CDLauncher.proceso_cdlauncher;

			if (nuevo == null || nuevo == anterior) {
				throw new IllegalStateException("CDLauncher no creó un proceso nuevo.");
			}

			procesoObservado = nuevo;
			establecerEstado("juego", prueba.ronda, prueba.candidatos.size(), prueba.todosDesactivados.size(), "");
			iniciarVigilanciaProceso();
		} catch (Throwable t) {
			try {
				motor.restaurarTodo();
			} catch (Exception restauracion) {
				CrashDetectorLogger.logException(restauracion);
			}
			manejarError(t);
		}
	}

	private void iniciarVigilanciaProceso() {
		if (temporizadorProceso != null) {
			temporizadorProceso.stop();
		}

		temporizadorProceso = new Timer(750, e -> {
			Process proceso = procesoObservado;
			if (proceso == null) {
				return;
			}
			try {
				if (!proceso.isAlive()) {
					temporizadorProceso.stop();
					procesoObservado = null;
					establecerEstado("esperando", obtenerRondaVisible(), modeloCandidatos.size(),
							modeloDesactivados.size(), "");
					botonPersiste.setEnabled(true);
					botonDesaparecio.setEnabled(true);
					botonRestaurar.setEnabled(true);
					botonIniciar.setEnabled(false);
				}
			} catch (Throwable t) {
				temporizadorProceso.stop();
				procesoObservado = null;
				manejarError(t);
			}
		});
		temporizadorProceso.start();
	}

	private void restaurarTodo() {
		if (ocupada() || hayJuegoActivo()) {
			return;
		}

		establecerBotonesOcupada();
		establecerEstado("restaurando", 0, 0, 0, "");

		workerActual = new SwingWorker<Integer, Void>() {
			@Override
			protected Integer doInBackground() throws Exception {
				if (motor.hayRecuperacionPendiente()) {
					return Integer.valueOf(motor.restaurarRecuperacionPendiente());
				}
				motor.restaurarTodo();
				return Integer.valueOf(0);
			}

			@Override
			protected void done() {
				try {
					get();
					establecerEstado("restaurado", 0, 0, 0, "");
					modeloCandidatos.clear();
					modeloDesactivados.clear();
					establecerBotonesSinSesion();
				} catch (Exception e) {
					manejarError(e);
				} finally {
					workerActual = null;
				}
			}
		};
		workerActual.execute();
	}

	private void actualizarListas(MotorBusquedaBinariaMods.Prueba prueba) {
		rondaVisible = prueba.ronda;
		actualizarCandidatos(prueba.candidatos);
		modeloDesactivados.clear();
		for (Path ruta : prueba.todosDesactivados) {
			modeloDesactivados.addElement(nombre(ruta));
		}
	}

	private void actualizarCandidatos(List<Path> rutas) {
		modeloCandidatos.clear();
		for (Path ruta : rutas) {
			modeloCandidatos.addElement(nombre(ruta));
		}
	}

	private void vaciarDesactivados() {
		modeloDesactivados.clear();
	}

	private void agregarHistorial(String texto) {
		if (texto == null || texto.trim().isEmpty()) {
			return;
		}
		if (areaHistorial.getDocument().getLength() > 0) {
			areaHistorial.append(System.lineSeparator());
		}
		areaHistorial.append(texto);
		areaHistorial.setCaretPosition(areaHistorial.getDocument().getLength());
	}

	private void manejarError(Throwable throwable) {
		Throwable causa = throwable;
		if (throwable instanceof java.util.concurrent.ExecutionException && throwable.getCause() != null) {
			causa = throwable.getCause();
		}

		String detalle = causa.getMessage() == null ? causa.getClass().getSimpleName() : causa.getMessage();
		if ("RECUPERACION_PENDIENTE".equals(detalle)) {
			establecerEstado("recuperacion", 0, 0, 0, "");
			botonIniciar.setEnabled(false);
			botonRestaurar.setEnabled(true);
		} else if ("NO_HAY_MODS".equals(detalle)) {
			establecerEstado("sin_mods", 0, 0, 0, "");
			establecerBotonesSinSesion();
		} else if ("SIN_DIVISION_SEGURA".equals(detalle)) {
			establecerEstado("sin_division", 0, modeloCandidatos.size(), 0, "");
			establecerBotonesFinal();
		} else {
			etiquetaEstado.setText(MonitorDePID.idioma.busquedaBinariaModsError(detalle));
			agregarHistorial(etiquetaEstado.getText());
			establecerBotonesSinSesion();
		}

		CrashDetectorLogger.logException(causa);
		recargarApariencia();
	}

	private void establecerEstado(String clave, int ronda, int candidatos, int desactivados, String detalle) {
		etiquetaEstado.setText(
				MonitorDePID.idioma.busquedaBinariaModsEstado(clave, ronda, candidatos, desactivados, detalle));
	}

	private void establecerBotonesOcupada() {
		botonIniciar.setEnabled(false);
		botonPersiste.setEnabled(false);
		botonDesaparecio.setEnabled(false);
		botonRestaurar.setEnabled(false);
	}

	private void establecerBotonesSinSesion() {
		botonIniciar.setEnabled(!motor.hayRecuperacionPendiente());
		botonPersiste.setEnabled(false);
		botonDesaparecio.setEnabled(false);
		botonRestaurar.setEnabled(motor.hayRecuperacionPendiente());
	}

	private void establecerBotonesFinal() {
		botonIniciar.setEnabled(true);
		botonPersiste.setEnabled(false);
		botonDesaparecio.setEnabled(false);
		botonRestaurar.setEnabled(false);
	}

	private boolean ocupada() {
		return workerActual != null;
	}

	private boolean hayJuegoActivo() {
		Process p = procesoObservado;
		if (p == null) {
			p = CDLauncher.proceso_cdlauncher;
		}
		try {
			return p != null && p.isAlive();
		} catch (Throwable t) {
			return false;
		}
	}

	private int obtenerRondaVisible() {
		return rondaVisible;
	}

	private static String nombre(Path ruta) {
		return ruta == null ? "" : ruta.getFileName().toString();
	}

	private static String unirNombres(List<Path> rutas) {
		StringBuilder sb = new StringBuilder();
		for (Path ruta : rutas) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(nombre(ruta));
		}
		return sb.toString();
	}

	@Override
	public TipoGUI<BusquedaBinariaModsGUI> tipo() {
		return TipoGUI.BUSQUEDA_BINARIA_MODS;
	}

	@Override
	public abstract void recargarApariencia();
}
