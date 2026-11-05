package com.asbestosstar.crashdetector.gui.tipos.transferidor_clases;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.buscar.ClasesTransferidas;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.controljvm.ClienteControlJVM;
import com.asbestosstar.crashdetector.controljvm.RespuestaControlJVM;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * GUI para transferir a CrashDetector los bytes actuales de una clase cargada
 * en la JVM observada.
 */
public abstract class TransferidorClasesGUI extends JFrame implements CrashDetectorGUI {

	private static final long serialVersionUID = 1L;

	public static final Map<String, Supplier<TransferidorClasesGUI>> GUIS = new ConcurrentHashMap<String, Supplier<TransferidorClasesGUI>>();

	private JPanel panelPrincipal;
	private JPanel panelCabecera;
	private JPanel panelCentro;
	private JPanel panelFormulario;
	private JPanel panelBotones;
	private JScrollPane scrollLista;
	private JLabel imagen;
	private JTextField nombreClase;
	private JTextArea descripcion;
	private JTextArea estado;
	private JTextArea clasesTransferidas;
	private JButton transferir;
	private JButton limpiar;

	protected abstract Color colorFondo();

	protected abstract Color colorPanel();

	protected abstract Color colorCampo();

	protected abstract Color colorTexto();

	protected abstract Color colorTextoSuave();

	protected abstract Color colorAcento();

	protected abstract Color colorBorde();

	protected abstract Color colorSeleccion();

	protected abstract String rutaImagenTema();

	protected abstract List<ElementoConfig> configuracionesTema();

	@Override
	public final TipoGUI tipo() {
		return TipoGUI.TRANSFERIDOR_CLASES;
	}

	@Override
	public void init() {
		constructir();
	}

	public void constructir(Object... argumentos) {
		inicializarSiHaceFalta();

		if (argumentos != null) {
			for (Object argumento : argumentos) {
				if (argumento instanceof String && nombreClase != null) {
					nombreClase.setText((String) argumento);
					nombreClase.selectAll();
				}
			}
		}

		actualizarLista();
		setVisible(true);
		toFront();
		requestFocus();
		if (nombreClase != null) {
			nombreClase.requestFocusInWindow();
		}
	}

	private void inicializarSiHaceFalta() {
		if (isDisplayable()) {
			recargarApariencia();
			return;
		}

		setTitle(MonitorDePID.idioma.transferidorClasesTitulo());
		setSize(920, 650);
		setMinimumSize(new Dimension(720, 520));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		panelPrincipal = new JPanel(new BorderLayout(12, 12));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		imagen = new JLabel("", SwingConstants.CENTER);
		imagen.setPreferredSize(new Dimension(360, 205));
		cargarImagenTema();

		descripcion = new JTextArea(MonitorDePID.idioma.transferidorClasesDescripcion());
		descripcion.setEditable(false);
		descripcion.setLineWrap(true);
		descripcion.setWrapStyleWord(true);
		descripcion.setOpaque(false);
		descripcion.setRows(5);

		panelCabecera = new JPanel(new BorderLayout(12, 0));
		panelCabecera.add(imagen, BorderLayout.WEST);
		panelCabecera.add(descripcion, BorderLayout.CENTER);
		panelPrincipal.add(panelCabecera, BorderLayout.NORTH);

		panelFormulario = new JPanel();
		panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
		panelFormulario.setBorder(crearBorde(MonitorDePID.idioma.transferidorClasesNombreClase()));

		nombreClase = new JTextField();
		nombreClase.setToolTipText(MonitorDePID.idioma.transferidorClasesNombreClase());
		nombreClase.addActionListener(this::transferirDesdeEvento);
		panelFormulario.add(nombreClase);

		panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		transferir = new JButton(MonitorDePID.idioma.transferidorClasesTransferir());
		limpiar = new JButton(MonitorDePID.idioma.transferidorClasesLimpiar());
		panelBotones.add(limpiar);
		panelBotones.add(transferir);
		panelFormulario.add(panelBotones);

		estado = new JTextArea();
		estado.setEditable(false);
		estado.setLineWrap(true);
		estado.setWrapStyleWord(true);
		estado.setRows(3);
		estado.setBorder(crearBorde(MonitorDePID.idioma.transferidorClasesTitulo()));

		clasesTransferidas = new JTextArea();
		clasesTransferidas.setEditable(false);
		clasesTransferidas.setLineWrap(false);
		scrollLista = new JScrollPane(clasesTransferidas);
		scrollLista.setBorder(crearBorde(MonitorDePID.idioma.transferidorClasesTransferir()));

		panelCentro = new JPanel(new BorderLayout(8, 8));
		panelCentro.add(panelFormulario, BorderLayout.NORTH);
		panelCentro.add(scrollLista, BorderLayout.CENTER);
		panelCentro.add(estado, BorderLayout.SOUTH);
		panelPrincipal.add(panelCentro, BorderLayout.CENTER);

		add(panelPrincipal, BorderLayout.CENTER);

		transferir.addActionListener(this::transferirDesdeEvento);
		limpiar.addActionListener(e -> {
			ClasesTransferidas.limpiar(MonitorDePID.pid);
			estado.setText("");
			actualizarLista();
		});

		recargarApariencia();
	}

	private void transferirDesdeEvento(ActionEvent evento) {
		final String solicitado = nombreClase.getText() == null ? "" : nombreClase.getText().trim();
		if (solicitado.isEmpty()) {
			mostrarError(MonitorDePID.idioma.transferidorClasesError("NOMBRE_CLASE_VACIO"));
			return;
		}
		if (MonitorDePID.pid <= 0L) {
			mostrarError(MonitorDePID.idioma.transferidorClasesError("PROCESO_NO_DISPONIBLE"));
			return;
		}

		setControlesHabilitados(false);
		estado.setText(MonitorDePID.idioma.transferidorClasesSolicitando(solicitado));

		new SwingWorker<RespuestaControlJVM, Void>() {
			@Override
			protected RespuestaControlJVM doInBackground() {
				return new ClienteControlJVM(MonitorDePID.pid).solicitarBytesClase(solicitado);
			}

			@Override
			protected void done() {
				try {
					RespuestaControlJVM respuesta = get();
					if (!respuesta.esCorrecta() || !respuesta.tieneDatos()) {
						String mensaje = respuesta.mensajeUsuario();
						estado.setText(mensaje);
						mostrarError(mensaje);
						return;
					}

					byte[] bytes = respuesta.datos();
					ClasesTransferidas.guardar(MonitorDePID.pid, solicitado, bytes);
					String nombreGuardado = ClasesTransferidas.normalizarNombreInterno(solicitado).replace('/', '.');
					String mensaje = MonitorDePID.idioma.transferidorClasesExito(nombreGuardado, bytes.length);
					estado.setText(mensaje);
					actualizarLista();
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					String detalle = t.getMessage() == null ? t.getClass().getSimpleName() : t.getMessage();
					String mensaje = MonitorDePID.idioma.transferidorClasesError(detalle);
					estado.setText(mensaje);
					mostrarError(mensaje);
				} finally {
					setControlesHabilitados(true);
				}
			}
		}.execute();
	}

	private void actualizarLista() {
		if (clasesTransferidas == null) {
			return;
		}
		Map<String, Integer> clases = ClasesTransferidas.nombresYLongitudes(MonitorDePID.pid);
		StringBuilder texto = new StringBuilder();
		for (Map.Entry<String, Integer> entrada : clases.entrySet()) {
			texto.append(MonitorDePID.idioma.transferidorClasesExito(entrada.getKey(), entrada.getValue().intValue()))
					.append('\n');
		}
		clasesTransferidas.setText(texto.toString());
		clasesTransferidas.setCaretPosition(0);
	}

	private void setControlesHabilitados(boolean habilitados) {
		if (transferir != null) {
			transferir.setEnabled(habilitados);
		}
		if (limpiar != null) {
			limpiar.setEnabled(habilitados);
		}
		if (nombreClase != null) {
			nombreClase.setEnabled(habilitados);
		}
	}

	private void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
	}

	private TitledBorder crearBorde(String titulo) {
		return BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorBorde()), titulo, TitledBorder.LEFT,
				TitledBorder.TOP, null, colorTexto());
	}

	private void cargarImagenTema() {
		ImageIcon icono = new ImageIcon();
		URL recurso = getClass().getResource("/" + rutaImagenTema().replace('\\', '/'));
		if (recurso != null) {
			icono = new ImageIcon(recurso);
		}
		if (icono.getIconWidth() <= 0) {
			icono = new ImageIcon(Statics.carpeta.resolve(rutaImagenTema()).toString());
		}
		if (icono.getIconWidth() <= 0) {
			imagen.setText(MonitorDePID.idioma.transferidorClasesTitulo());
			return;
		}

		int maximoAncho = 350;
		int maximoAlto = 200;
		double escala = Math.min((double) maximoAncho / icono.getIconWidth(),
				(double) maximoAlto / icono.getIconHeight());
		int ancho = Math.max(1, (int) Math.round(icono.getIconWidth() * escala));
		int alto = Math.max(1, (int) Math.round(icono.getIconHeight() * escala));
		Image escalada = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
		imagen.setIcon(new ImageIcon(escalada));
		imagen.setText("");
	}

	@Override
	public void recargarApariencia() {
		if (panelPrincipal != null) {
			panelPrincipal.setBackground(colorFondo());
		}
		if (panelCabecera != null) {
			panelCabecera.setBackground(colorFondo());
		}
		if (panelCentro != null) {
			panelCentro.setBackground(colorFondo());
		}
		if (panelFormulario != null) {
			panelFormulario.setBackground(colorPanel());
			panelFormulario.setBorder(crearBorde(MonitorDePID.idioma.transferidorClasesNombreClase()));
		}
		if (panelBotones != null) {
			panelBotones.setBackground(colorPanel());
		}
		if (descripcion != null) {
			descripcion.setForeground(colorTextoSuave());
		}
		if (imagen != null) {
			imagen.setForeground(colorTexto());
		}
		if (scrollLista != null) {
			scrollLista.setBorder(crearBorde(MonitorDePID.idioma.transferidorClasesTransferir()));
			scrollLista.setBackground(colorPanel());
			scrollLista.getViewport().setBackground(colorCampo());
		}
		if (nombreClase != null) {
			nombreClase.setBackground(colorCampo());
			nombreClase.setForeground(colorTexto());
			nombreClase.setCaretColor(colorAcento());
			nombreClase.setSelectionColor(colorSeleccion());
		}
		for (JTextArea area : new JTextArea[] { estado, clasesTransferidas }) {
			if (area != null) {
				area.setBackground(colorCampo());
				area.setForeground(colorTexto());
				area.setCaretColor(colorAcento());
				area.setSelectionColor(colorSeleccion());
			}
		}
		for (JButton boton : new JButton[] { transferir, limpiar }) {
			if (boton != null && !CrashDetectorGUI.esMac()) {
				boton.setBackground(colorAcento());
				boton.setForeground(colorTexto());
				boton.setOpaque(true);
			}
		}
		repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = configuracionesTema();
		return ret == null ? new ArrayList<ElementoConfig>() : ret;
	}
}
