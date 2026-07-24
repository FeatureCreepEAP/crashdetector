package com.asbestosstar.crashdetector.gui.tipos.correo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación visual inspirada en Netscape Communicator/Messenger.
 */
@SuppressWarnings("serial")
public class LectorDeCorreoNetscape extends LectorDeCorreoGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "lector_correo_netscape";

	public final ConfigColor colorFondo = ConfigColor.de("correo.netscape.fondo", new Color(192, 192, 192));
	public final ConfigColor colorTexto = ConfigColor.de("correo.netscape.texto", new Color(0, 0, 0));
	public final ConfigColor colorCabecera = ConfigColor.de("correo.netscape.cabecera", new Color(0, 0, 128));
	public final ConfigColor colorTextoCabecera = ConfigColor.de("correo.netscape.texto_cabecera",
			new Color(255, 255, 255));
	public final ConfigColor colorBarra = ConfigColor.de("correo.netscape.barra", new Color(212, 208, 200));
	public final ConfigColor colorBoton = ConfigColor.de("correo.netscape.boton", new Color(212, 208, 200));
	public final ConfigColor colorCaja = ConfigColor.de("correo.netscape.caja", new Color(255, 255, 255));
	public final ConfigColor colorSeleccion = ConfigColor.de("correo.netscape.seleccion", new Color(0, 0, 128));
	public final ConfigColor colorTextoSeleccion = ConfigColor.de("correo.netscape.texto_seleccion",
			new Color(255, 255, 255));
	public final ConfigColor colorAviso = ConfigColor.de("correo.netscape.aviso", new Color(255, 255, 204));
	public final ConfigColor colorTextoAviso = ConfigColor.de("correo.netscape.texto_aviso", new Color(96, 0, 0));
	public final ConfigColor colorBordeClaro = ConfigColor.de("correo.netscape.borde_claro",
			new Color(255, 255, 255));
	public final ConfigColor colorBordeOscuro = ConfigColor.de("correo.netscape.borde_oscuro",
			new Color(64, 64, 64));
	public final ConfigColor colorCitas = ConfigColor.de("correo.netscape.citas", new Color(224, 224, 224));

	private JPanel raiz;
	private JPanel cabecera;
	private JPanel barraHerramientas;
	private JPanel panelAviso;
	private JPanel panelCuentas;
	private JPanel panelMensajes;
	private JPanel panelContenido;
	private JPanel panelCitas;
	private JPanel barraEstado;

	private JLabel titulo;
	private JLabel aviso;
	private JLabel estado;
	private JLabel estadoDependencias;
	private JLabel etiquetaBuscar;
	private JLabel etiquetaDe;
	private JLabel etiquetaPara;
	private JLabel etiquetaAsunto;
	private JLabel etiquetaFecha;

	private JButton botonAgregar;
	private JButton botonEditar;
	private JButton botonEliminar;
	private JButton botonActualizar;
	private JButton botonActualizarTodas;
	private JButton botonDescargarDependencias;
	private JButton botonCerrar;

	private JTextField campoBuscar;
	private JList<CuentaCorreo> listaCuentas;
	private DefaultTableModel modeloMensajes;
	private JTable tablaMensajes;
	private JTextArea cuerpoMensaje;
	private JTextArea citaUno;
	private JTextArea citaDos;

	private final List<CuentaCorreo> cuentasVisuales = new ArrayList<CuentaCorreo>();
	private final List<MensajeCorreo> mensajesVisuales = new ArrayList<MensajeCorreo>();
	private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
			.withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());

	@Override
	public String id() {
		return ID;
	}

	@Override
	protected void construirApariencia() {
		setTitle(MonitorDePID.idioma.correoTitulo());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(900, 620));
		setSize(new Dimension(1120, 760));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		raiz = new JPanel(new BorderLayout(4, 4));
		raiz.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		cabecera = crearCabecera();
		barraHerramientas = crearBarraHerramientas();
		panelAviso = crearPanelAviso();

		JPanel norte = new JPanel(new BorderLayout(0, 3));
		norte.add(cabecera, BorderLayout.NORTH);
		norte.add(barraHerramientas, BorderLayout.CENTER);
		norte.add(panelAviso, BorderLayout.SOUTH);

		panelCuentas = crearPanelCuentas();
		panelMensajes = crearPanelMensajes();
		panelContenido = crearPanelContenido();

		JSplitPane divisorVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelMensajes, panelContenido);
		divisorVertical.setResizeWeight(0.43);
		divisorVertical.setDividerSize(7);

		JSplitPane divisorPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelCuentas, divisorVertical);
		divisorPrincipal.setResizeWeight(0.19);
		divisorPrincipal.setDividerSize(7);

		panelCitas = crearPanelCitas();
		barraEstado = crearBarraEstado();

		JPanel sur = new JPanel(new BorderLayout(0, 3));
		sur.add(panelCitas, BorderLayout.CENTER);
		sur.add(barraEstado, BorderLayout.SOUTH);

		raiz.add(norte, BorderLayout.NORTH);
		raiz.add(divisorPrincipal, BorderLayout.CENTER);
		raiz.add(sur, BorderLayout.SOUTH);
		setContentPane(raiz);

		recargarApariencia();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel crearCabecera() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createBevelBorder(BevelBorder.RAISED, colorBordeClaro.obtener(), colorBordeClaro.obtener(),
						colorBordeOscuro.obtener(), colorBordeOscuro.obtener()),
				BorderFactory.createEmptyBorder(7, 10, 7, 10)));
		titulo = new JLabel(MonitorDePID.idioma.correoTitulo());
		titulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		panel.add(titulo, BorderLayout.WEST);
		return panel;
	}

	private JPanel crearBarraHerramientas() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, colorBordeClaro.obtener(),
				colorBordeClaro.obtener(), colorBordeOscuro.obtener(), colorBordeOscuro.obtener()));

		botonAgregar = crearBoton(MonitorDePID.idioma.correoAgregarCuenta());
		botonEditar = crearBoton(MonitorDePID.idioma.correoEditarCuenta());
		botonEliminar = crearBoton(MonitorDePID.idioma.correoEliminarCuenta());
		botonActualizar = crearBoton(MonitorDePID.idioma.correoActualizar());
		botonActualizarTodas = crearBoton(MonitorDePID.idioma.correoActualizarTodas());
		botonDescargarDependencias = crearBoton(MonitorDePID.idioma.correoDescargarDependencias());
		botonCerrar = crearBoton(MonitorDePID.idioma.correoCerrar());

		botonAgregar.addActionListener(e -> agregarCuentaInteractiva());
		botonEditar.addActionListener(e -> {
			CuentaCorreo cuenta = listaCuentas.getSelectedValue();
			if (cuenta != null) {
				editarCuentaInteractiva(cuenta.id());
			}
		});
		botonEliminar.addActionListener(e -> {
			CuentaCorreo cuenta = listaCuentas.getSelectedValue();
			if (cuenta != null) {
				eliminarCuentaInteractiva(cuenta.id());
			}
		});
		botonActualizar.addActionListener(e -> {
			CuentaCorreo cuenta = listaCuentas.getSelectedValue();
			if (cuenta != null) {
				actualizarCuentaAsync(cuenta.id());
			}
		});
		botonActualizarTodas.addActionListener(e -> actualizarTodasAsync());
		botonDescargarDependencias.addActionListener(e -> descargarDependenciasCorreoAsync());
		botonCerrar.addActionListener(e -> dispose());

		panel.add(botonAgregar);
		panel.add(botonEditar);
		panel.add(botonEliminar);
		panel.add(crearSeparadorVertical());
		panel.add(botonActualizar);
		panel.add(botonActualizarTodas);
		panel.add(crearSeparadorVertical());
		panel.add(botonDescargarDependencias);
		panel.add(crearSeparadorVertical());
		panel.add(botonCerrar);
		return panel;
	}

	private Component crearSeparadorVertical() {
		JPanel separador = new JPanel();
		separador.setPreferredSize(new Dimension(2, 24));
		separador.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, colorBordeClaro.obtener(),
				colorBordeClaro.obtener(), colorBordeOscuro.obtener(), colorBordeOscuro.obtener()));
		return separador;
	}

	private JPanel crearPanelAviso() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(colorBordeOscuro.obtener(), 1),
				BorderFactory.createEmptyBorder(5, 8, 5, 8)));
		aviso = new JLabel("<html>" + escaparHtml(MonitorDePID.idioma.correoAdvertenciaPrivacidad()) + "</html>");
		aviso.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		panel.add(aviso, BorderLayout.CENTER);
		return panel;
	}

	private JPanel crearPanelCuentas() {
		JPanel panel = new JPanel(new BorderLayout(3, 3));
		panel.setBorder(crearBordeTitulo(MonitorDePID.idioma.correoCuentas()));
		listaCuentas = new JList<CuentaCorreo>();
		listaCuentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaCuentas.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component componente = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (componente instanceof JLabel && value instanceof CuentaCorreo) {
					CuentaCorreo cuenta = (CuentaCorreo) value;
					((JLabel) componente).setText(cuenta.nombre());
					((JLabel) componente).setToolTipText(
						MonitorDePID.idioma.correoCuentaServidor(cuenta.usuario(), cuenta.servidorImap()));
				}
				return componente;
			}
		});
		listaCuentas.addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				aplicarFiltro();
			}
		});
		panel.add(new JScrollPane(listaCuentas), BorderLayout.CENTER);
		return panel;
	}

	private JPanel crearPanelMensajes() {
		JPanel panel = new JPanel(new BorderLayout(3, 3));
		panel.setBorder(crearBordeTitulo(MonitorDePID.idioma.correoMensajes()));

		JPanel busqueda = new JPanel(new BorderLayout(5, 0));
		etiquetaBuscar = new JLabel(MonitorDePID.idioma.correoBuscar());
		campoBuscar = new JTextField();
		campoBuscar.setToolTipText(MonitorDePID.idioma.correoBuscarAyuda());
		campoBuscar.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				aplicarFiltro();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				aplicarFiltro();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				aplicarFiltro();
			}
		});
		busqueda.add(etiquetaBuscar, BorderLayout.WEST);
		busqueda.add(campoBuscar, BorderLayout.CENTER);

		modeloMensajes = new DefaultTableModel(new Object[] { MonitorDePID.idioma.correoColumnaEstado(),
				MonitorDePID.idioma.correoColumnaDe(), MonitorDePID.idioma.correoColumnaAsunto(),
				MonitorDePID.idioma.correoColumnaFecha() }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablaMensajes = new JTable(modeloMensajes);
		tablaMensajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaMensajes.setAutoCreateRowSorter(true);
		tablaMensajes.setRowHeight(22);
		tablaMensajes.getColumnModel().getColumn(0).setPreferredWidth(55);
		tablaMensajes.getColumnModel().getColumn(1).setPreferredWidth(220);
		tablaMensajes.getColumnModel().getColumn(2).setPreferredWidth(460);
		tablaMensajes.getColumnModel().getColumn(3).setPreferredWidth(145);
		DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
		centro.setHorizontalAlignment(SwingConstants.CENTER);
		tablaMensajes.getColumnModel().getColumn(0).setCellRenderer(centro);
		tablaMensajes.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				mostrarMensajeSeleccionado();
			}
		});
		tablaMensajes.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				mostrarMensajeSeleccionado();
			}
		});

		panel.add(busqueda, BorderLayout.NORTH);
		panel.add(new JScrollPane(tablaMensajes), BorderLayout.CENTER);
		return panel;
	}

	private JPanel crearPanelContenido() {
		JPanel panel = new JPanel(new BorderLayout(3, 3));
		panel.setBorder(crearBordeTitulo(MonitorDePID.idioma.correoContenido()));

		JPanel metadatos = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 4, 2, 4);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		etiquetaDe = new JLabel();
		etiquetaPara = new JLabel();
		etiquetaAsunto = new JLabel();
		etiquetaFecha = new JLabel();

		agregarFilaMetadato(metadatos, gbc, 0, MonitorDePID.idioma.correoDe(), etiquetaDe);
		agregarFilaMetadato(metadatos, gbc, 1, MonitorDePID.idioma.correoPara(), etiquetaPara);
		agregarFilaMetadato(metadatos, gbc, 2, MonitorDePID.idioma.correoAsunto(), etiquetaAsunto);
		agregarFilaMetadato(metadatos, gbc, 3, MonitorDePID.idioma.correoFecha(), etiquetaFecha);

		cuerpoMensaje = new JTextArea();
		cuerpoMensaje.setEditable(false);
		cuerpoMensaje.setLineWrap(true);
		cuerpoMensaje.setWrapStyleWord(true);
		cuerpoMensaje.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
		cuerpoMensaje.setText(MonitorDePID.idioma.correoSeleccioneMensaje());

		panel.add(metadatos, BorderLayout.NORTH);
		panel.add(new JScrollPane(cuerpoMensaje), BorderLayout.CENTER);
		return panel;
	}

	private void agregarFilaMetadato(JPanel panel, GridBagConstraints gbc, int fila, String nombre, JLabel valor) {
		gbc.gridx = 0;
		gbc.gridy = fila;
		gbc.weightx = 0;
		JLabel etiqueta = new JLabel(nombre);
		etiqueta.setFont(etiqueta.getFont().deriveFont(Font.BOLD));
		panel.add(etiqueta, gbc);

		gbc.gridx = 1;
		gbc.weightx = 1;
		panel.add(valor, gbc);
	}

	private JPanel crearPanelCitas() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(crearBordeTitulo(MonitorDePID.idioma.correoCitas()));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.insets = new Insets(2, 4, 2, 4);

		citaUno = crearAreaCita(MonitorDePID.idioma.correoCitaUno());
		citaDos = crearAreaCita(MonitorDePID.idioma.correoCitaDos());
		gbc.gridy = 0;
		panel.add(citaUno, gbc);
		gbc.gridy = 1;
		panel.add(citaDos, gbc);
		return panel;
	}

	private JTextArea crearAreaCita(String texto) {
		JTextArea area = new JTextArea(texto);
		area.setEditable(false);
		area.setFocusable(false);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setRows(2);
		area.setFont(new Font(Font.SERIF, Font.ITALIC, 11));
		area.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		return area;
	}

	private JPanel crearBarraEstado() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, colorBordeClaro.obtener(),
				colorBordeClaro.obtener(), colorBordeOscuro.obtener(), colorBordeOscuro.obtener()));
		estado = new JLabel(MonitorDePID.idioma.correoEstadoListo());
		estado.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		estadoDependencias = new JLabel();
		estadoDependencias.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 5));
		panel.add(estado, BorderLayout.CENTER);
		panel.add(estadoDependencias, BorderLayout.EAST);
		return panel;
	}

	private JButton crearBoton(String texto) {
		JButton boton = new JButton(texto);
		boton.setFocusPainted(false);
		boton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
		return boton;
	}

	private javax.swing.border.Border crearBordeTitulo(String titulo) {
		return BorderFactory.createTitledBorder(
				BorderFactory.createBevelBorder(BevelBorder.LOWERED, colorBordeClaro.obtener(),
						colorBordeClaro.obtener(), colorBordeOscuro.obtener(), colorBordeOscuro.obtener()),
				titulo);
	}

	@Override
	protected char[] pedirClaveBoveda(boolean nueva) {
		JPasswordField clave = new JPasswordField(24);
		JPasswordField confirmar = nueva ? new JPasswordField(24) : null;
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 4, 4, 4);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel(MonitorDePID.idioma.correoClaveBoveda()), gbc);
		gbc.gridx = 1;
		gbc.weightx = 1;
		panel.add(clave, gbc);

		if (nueva) {
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 0;
			panel.add(new JLabel(MonitorDePID.idioma.correoConfirmarClave()), gbc);
			gbc.gridx = 1;
			gbc.weightx = 1;
			panel.add(confirmar, gbc);
		}

		String tituloDialogo = nueva ? MonitorDePID.idioma.correoCrearBovedaTitulo()
				: MonitorDePID.idioma.correoDesbloquearBovedaTitulo();
		int resultado = JOptionPane.showConfirmDialog(this, panel, tituloDialogo, JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (resultado != JOptionPane.OK_OPTION) {
			return null;
		}

		char[] valor = clave.getPassword();
		clave.setText("");
		if (valor.length < 12) {
			Arrays.fill(valor, '\0');
			mostrarErrorCorreo(MonitorDePID.idioma.correoClaveCorta(), null);
			return null;
		}
		if (nueva) {
			char[] repetida = confirmar.getPassword();
			confirmar.setText("");
			boolean iguales = Arrays.equals(valor, repetida);
			Arrays.fill(repetida, '\0');
			if (!iguales) {
				Arrays.fill(valor, '\0');
				mostrarErrorCorreo(MonitorDePID.idioma.correoClavesNoCoinciden(), null);
				return null;
			}
		}
		return valor;
	}

	@Override
	protected CuentaCorreo pedirCuenta(CuentaCorreo existente) {
		JTextField nombre = new JTextField(existente == null ? "" : existente.nombre(), 28);
		JTextField servidor = new JTextField(existente == null ? "" : existente.servidorImap(), 28);
		JTextField puerto = new JTextField(
				existente == null ? Integer.toString(PUERTO_IMAPS_PREDETERMINADO) : Integer.toString(existente.puerto()), 8);
		JTextField usuario = new JTextField(existente == null ? "" : existente.usuario(), 28);
		JPasswordField secreto = new JPasswordField(28);
		JTextField carpeta = new JTextField(
				existente == null ? MonitorDePID.idioma.correoCarpetaPredeterminada() : existente.carpeta(), 20);
		secreto.setToolTipText(MonitorDePID.idioma.correoSecretoAyuda());

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(3, 4, 3, 4);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

		agregarCampo(panel, gbc, 0, MonitorDePID.idioma.correoNombreCuenta(), nombre);
		agregarCampo(panel, gbc, 1, MonitorDePID.idioma.correoServidorImap(), servidor);
		agregarCampo(panel, gbc, 2, MonitorDePID.idioma.correoPuerto(), puerto);
		agregarCampo(panel, gbc, 3, MonitorDePID.idioma.correoUsuario(), usuario);
		agregarCampo(panel, gbc, 4, MonitorDePID.idioma.correoSecreto(), secreto);
		agregarCampo(panel, gbc, 5, MonitorDePID.idioma.correoCarpeta(), carpeta);

		int respuesta = JOptionPane.showConfirmDialog(this, panel,
				existente == null ? MonitorDePID.idioma.correoAgregarCuenta() : MonitorDePID.idioma.correoEditarCuenta(),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (respuesta != JOptionPane.OK_OPTION) {
			if (existente != null) {
				existente.borrarSecreto();
			}
			return null;
		}

		int puertoNumero;
		try {
			puertoNumero = Integer.parseInt(puerto.getText().trim());
		} catch (NumberFormatException e) {
			if (existente != null) {
				existente.borrarSecreto();
			}
			mostrarErrorCorreo(MonitorDePID.idioma.correoPuertoInvalido(), e);
			return null;
		}

		char[] secretoNuevo = secreto.getPassword();
		secreto.setText("");
		if (existente != null && secretoNuevo.length == 0) {
			secretoNuevo = existente.copiarSecreto();
		}
		try {
			String id = existente == null ? null : existente.id();
			return new CuentaCorreo(id, nombre.getText(), servidor.getText(), puertoNumero, usuario.getText(),
					secretoNuevo, carpeta.getText());
		} finally {
			Arrays.fill(secretoNuevo, '\0');
			if (existente != null) {
				existente.borrarSecreto();
			}
		}
	}

	private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String nombre, Component campo) {
		gbc.gridx = 0;
		gbc.gridy = fila;
		gbc.weightx = 0;
		panel.add(new JLabel(nombre), gbc);
		gbc.gridx = 1;
		gbc.weightx = 1;
		panel.add(campo, gbc);
	}

	@Override
	protected boolean confirmarEliminarCuenta(CuentaCorreo cuenta) {
		int respuesta = JOptionPane.showConfirmDialog(this,
				MonitorDePID.idioma.correoConfirmarEliminar(cuenta.nombre()), MonitorDePID.idioma.correoEliminarCuenta(),
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		cuenta.borrarSecreto();
		return respuesta == JOptionPane.YES_OPTION;
	}

	@Override
	protected void mostrarCuentas(List<CuentaCorreo> cuentas) {
		String idSeleccionado = null;
		CuentaCorreo seleccionada = listaCuentas == null ? null : listaCuentas.getSelectedValue();
		if (seleccionada != null) {
			idSeleccionado = seleccionada.id();
		}

		cuentasVisuales.clear();
		cuentasVisuales.addAll(cuentas);
		listaCuentas.setListData(cuentasVisuales.toArray(new CuentaCorreo[0]));

		int indice = 0;
		if (idSeleccionado != null) {
			for (int i = 0; i < cuentasVisuales.size(); i++) {
				if (idSeleccionado.equals(cuentasVisuales.get(i).id())) {
					indice = i;
					break;
				}
			}
		}
		if (!cuentasVisuales.isEmpty()) {
			listaCuentas.setSelectedIndex(indice);
		}
		actualizarBotones();
	}

	@Override
	protected void mostrarMensajes(List<MensajeCorreo> mensajes) {
		mensajesVisuales.clear();
		mensajesVisuales.addAll(mensajes == null ? Collections.emptyList() : mensajes);
		modeloMensajes.setRowCount(0);
		for (MensajeCorreo mensaje : mensajesVisuales) {
			modeloMensajes.addRow(new Object[] {
					mensaje.leido() ? MonitorDePID.idioma.correoLeido() : MonitorDePID.idioma.correoNoLeido(),
					mensaje.remitente(), mensaje.asunto(), formatoFecha.format(mensaje.fecha()) });
		}
		limpiarDetalle();
	}

	private void aplicarFiltro() {
		if (listaCuentas == null || campoBuscar == null || modeloMensajes == null) {
			return;
		}
		CuentaCorreo cuenta = listaCuentas.getSelectedValue();
		if (cuenta == null) {
			mostrarMensajes(Collections.emptyList());
			actualizarBotones();
			return;
		}
		mostrarMensajes(filtrarMensajes(cuenta.id(), campoBuscar.getText()));
		actualizarBotones();
	}

	private void mostrarMensajeSeleccionado() {
		int filaVista = tablaMensajes.getSelectedRow();
		if (filaVista < 0) {
			limpiarDetalle();
			return;
		}
		int filaModelo = tablaMensajes.convertRowIndexToModel(filaVista);
		if (filaModelo < 0 || filaModelo >= mensajesVisuales.size()) {
			limpiarDetalle();
			return;
		}
		MensajeCorreo mensaje = mensajesVisuales.get(filaModelo);
		etiquetaDe.setText(mensaje.remitente());
		etiquetaPara.setText(mensaje.destinatarios());
		etiquetaAsunto.setText(mensaje.asunto());
		etiquetaFecha.setText(formatoFecha.format(mensaje.fecha()));
		cuerpoMensaje.setText(mensaje.cuerpo());
		cuerpoMensaje.setCaretPosition(0);
	}

	private void limpiarDetalle() {
		if (etiquetaDe == null) {
			return;
		}
		etiquetaDe.setText("");
		etiquetaPara.setText("");
		etiquetaAsunto.setText("");
		etiquetaFecha.setText("");
		cuerpoMensaje.setText(MonitorDePID.idioma.correoSeleccioneMensaje());
		cuerpoMensaje.setCaretPosition(0);
	}

	private void actualizarBotones() {
		boolean hayCuenta = listaCuentas != null && listaCuentas.getSelectedValue() != null;
		boolean dependencias = dependenciasCorreoDisponibles();
		if (botonEditar != null) {
			botonEditar.setEnabled(hayCuenta);
			botonEliminar.setEnabled(hayCuenta);
			botonActualizar.setEnabled(hayCuenta && dependencias);
			botonActualizarTodas.setEnabled(!cuentasVisuales.isEmpty() && dependencias);
		}
	}

	@Override
	protected void mostrarEstadoCorreo(String texto) {
		if (estado != null) {
			estado.setText(texto == null ? "" : texto);
		}
	}

	@Override
	protected void mostrarErrorCorreo(String mensaje, Throwable error) {
		JOptionPane.showMessageDialog(this, mensaje, MonitorDePID.idioma.correoErrorTitulo(),
				JOptionPane.ERROR_MESSAGE);
		mostrarEstadoCorreo(mensaje);
	}

	@Override
	protected void mostrarInfoCorreo(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, MonitorDePID.idioma.correoTitulo(),
				JOptionPane.INFORMATION_MESSAGE);
		mostrarEstadoCorreo(mensaje);
	}

	@Override
	protected void mostrarEstadoDependenciasCorreo(boolean disponibles, String diagnostico) {
		if (estadoDependencias != null) {
			String texto = disponibles ? MonitorDePID.idioma.correoEstadoDependenciasCargadas(diagnostico)
					: MonitorDePID.idioma.correoEstadoDependenciasNoCargadas(diagnostico);
			estadoDependencias.setText(texto);
		}
		actualizarBotones();
	}

	@Override
	protected void establecerDescargaDependenciasCorreo(boolean descargando) {
		if (botonDescargarDependencias != null) {
			botonDescargarDependencias.setEnabled(!descargando);
		}
		setCursor(descargando ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
	}

	@Override
	public void recargarApariencia() {
		if (raiz == null) {
			return;
		}
		aplicarFondoRecursivo(raiz, colorFondo.obtener(), colorTexto.obtener());
		cabecera.setBackground(colorCabecera.obtener());
		titulo.setForeground(colorTextoCabecera.obtener());
		barraHerramientas.setBackground(colorBarra.obtener());
		panelAviso.setBackground(colorAviso.obtener());
		aviso.setForeground(colorTextoAviso.obtener());
		panelCitas.setBackground(colorCitas.obtener());
		citaUno.setBackground(colorCitas.obtener());
		citaDos.setBackground(colorCitas.obtener());
		citaUno.setForeground(colorTexto.obtener());
		citaDos.setForeground(colorTexto.obtener());
		barraEstado.setBackground(colorBarra.obtener());
		estado.setForeground(colorTexto.obtener());
		estadoDependencias.setForeground(colorTexto.obtener());
		campoBuscar.setBackground(colorCaja.obtener());
		campoBuscar.setForeground(colorTexto.obtener());
		listaCuentas.setBackground(colorCaja.obtener());
		listaCuentas.setForeground(colorTexto.obtener());
		listaCuentas.setSelectionBackground(colorSeleccion.obtener());
		listaCuentas.setSelectionForeground(colorTextoSeleccion.obtener());
		tablaMensajes.setBackground(colorCaja.obtener());
		tablaMensajes.setForeground(colorTexto.obtener());
		tablaMensajes.setSelectionBackground(colorSeleccion.obtener());
		tablaMensajes.setSelectionForeground(colorTextoSeleccion.obtener());
		tablaMensajes.getTableHeader().setBackground(colorBarra.obtener());
		tablaMensajes.getTableHeader().setForeground(colorTexto.obtener());
		cuerpoMensaje.setBackground(colorCaja.obtener());
		cuerpoMensaje.setForeground(colorTexto.obtener());

		for (JButton boton : Arrays.asList(botonAgregar, botonEditar, botonEliminar, botonActualizar,
				botonActualizarTodas, botonDescargarDependencias, botonCerrar)) {
			if (boton != null) {
				boton.setBackground(colorBoton.obtener());
				boton.setForeground(colorTexto.obtener());
				boton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, colorBordeClaro.obtener(),
						colorBordeClaro.obtener(), colorBordeOscuro.obtener(), colorBordeOscuro.obtener()));
			}
		}
	}

	private void aplicarFondoRecursivo(Component componente, Color fondo, Color texto) {
		if (componente instanceof JPanel) {
			componente.setBackground(fondo);
			componente.setForeground(texto);
		}
		if (componente instanceof java.awt.Container) {
			for (Component hijo : ((java.awt.Container) componente).getComponents()) {
				aplicarFondoRecursivo(hijo, fondo, texto);
			}
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();
		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorFondo());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorTexto());
		colorCabecera.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorCabecera());
		colorTextoCabecera.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorTextoCabecera());
		colorBarra.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorBarra());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorBoton());
		colorCaja.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorCaja());
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorSeleccion());
		colorTextoSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorTextoSeleccion());
		colorAviso.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorAviso());
		colorTextoAviso.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorTextoAviso());
		colorBordeClaro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorBordeClaro());
		colorBordeOscuro.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorBordeOscuro());
		colorCitas.establecerNombreParaMostrar(() -> MonitorDePID.idioma.correoColorCitas());
		elementos.add(colorFondo);
		elementos.add(colorTexto);
		elementos.add(colorCabecera);
		elementos.add(colorTextoCabecera);
		elementos.add(colorBarra);
		elementos.add(colorBoton);
		elementos.add(colorCaja);
		elementos.add(colorSeleccion);
		elementos.add(colorTextoSeleccion);
		elementos.add(colorAviso);
		elementos.add(colorTextoAviso);
		elementos.add(colorBordeClaro);
		elementos.add(colorBordeOscuro);
		elementos.add(colorCitas);
		return elementos;
	}

	private String escaparHtml(String texto) {
		if (texto == null) {
			return "";
		}
		return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
				.replace("'", "&#39;");
	}
}
