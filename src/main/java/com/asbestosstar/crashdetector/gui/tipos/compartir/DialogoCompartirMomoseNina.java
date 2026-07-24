package com.asbestosstar.crashdetector.gui.tipos.compartir;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro;
import com.asbestosstar.crashdetector.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetector.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sito_registro.LimteDeTasa;
import com.asbestosstar.crashdetector.api_sito_registro.NoAPIdeRegistro;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.canario.CanarioDeOrdenJudicialGUI1984;
import com.asbestosstar.crashdetector.gui.tipos.lfpdppp.LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI;
import com.asbestosstar.crashdetector.gui.tipos.lfpdppp.LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos;

public class DialogoCompartirMomoseNina extends DialogoCompartir {

	public static final String ID = "dialogo_compartir_momose_nina";

	static {
		GUIS.put(ID, DialogoCompartirMomoseNina::new);
	}

	private final ConfigColor colorFondo = ConfigColor.de("dialogo.compartir.momose_nina.fondo",
			new Color(255, 246, 250));
	private final ConfigColor colorPanel = ConfigColor.de("dialogo.compartir.momose_nina.panel",
			new Color(255, 255, 255));
	private final ConfigColor colorPanelSecundario = ConfigColor.de("dialogo.compartir.momose_nina.panel_secundario",
			new Color(255, 239, 246));
	private final ConfigColor colorTexto = ConfigColor.de("dialogo.compartir.momose_nina.texto",
			new Color(105, 65, 86));
	private final ConfigColor colorSubtexto = ConfigColor.de("dialogo.compartir.momose_nina.subtexto",
			new Color(142, 98, 122));
	private final ConfigColor colorAcento = ConfigColor.de("dialogo.compartir.momose_nina.acento",
			new Color(243, 123, 176));
	private final ConfigColor colorAcentoSuave = ConfigColor.de("dialogo.compartir.momose_nina.acento_suave",
			new Color(255, 214, 232));
	private final ConfigColor colorBorde = ConfigColor.de("dialogo.compartir.momose_nina.borde",
			new Color(240, 188, 214));
	private final ConfigColor colorBotonSecundario = ConfigColor.de("dialogo.compartir.momose_nina.boton_secundario",
			new Color(255, 230, 241));
	private final ConfigColor colorCampo = ConfigColor.de("dialogo.compartir.momose_nina.campo",
			new Color(255, 255, 255));

	private JPanel panelCabecera;
	private JPanel panelAcciones;
	private JPanel panelContenido;
	private JPanel panelResultado;
	private JPanel panelSeccionLogs;
	private JPanel panelSeccionPrivacidad;
	private JPanel panelFilasLogs;
	private JLabel etiquetaTitulo;
	private JLabel etiquetaSubtitulo;
	private JLabel etiquetaImagen;
	private JButton botonToggleLogs;
	private JButton botonTogglePrivacidad;
	private JTextArea textoResumen;

	private JPanel contenidoLogs;
	private JPanel contenidoPrivacidad;
	private boolean logsExpandidos = false;
	private boolean privacidadExpandida = false;
	private boolean consentimientoTemporal = false;

	private final List<FilaLog> filasLogs = new ArrayList<FilaLog>();

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void preperar(Instant instant) {
		this.instant = instant;
		setTitle(Statics.nombre_cd.obtener());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(980, 760);
		setMinimumSize(new Dimension(900, 700));
		setLayout(new BorderLayout());

		inicializarModeloInterno();

		panelPrincipal = new JPanel(new BorderLayout(12, 12));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		panelCabecera = construirCabecera();
		panelAcciones = construirPanelAccionesPrincipales();
		panelContenido = new JPanel();
		panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));

		panelSeccionLogs = construirSeccionLogs();
		panelSeccionPrivacidad = construirSeccionPrivacidad();
		panelResultado = construirPanelResultado();

		JPanel cuerpo = new JPanel();
		cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
		cuerpo.add(panelAcciones);
		cuerpo.add(Box.createVerticalStrut(10));
		cuerpo.add(panelSeccionLogs);
		cuerpo.add(Box.createVerticalStrut(10));
		cuerpo.add(panelSeccionPrivacidad);
		cuerpo.add(Box.createVerticalStrut(10));
		cuerpo.add(panelResultado);
		cuerpo.add(Box.createVerticalGlue());

		panelContenido.add(cuerpo);

		panelPrincipal.add(panelCabecera, BorderLayout.NORTH);
		panelPrincipal.add(new JScrollPane(panelContenido), BorderLayout.CENTER);

		add(panelPrincipal, BorderLayout.CENTER);

		cargarConsolas();
		reconstruirFilasLogs();
		recargarApariencia();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void inicializarModeloInterno() {
		modeloTabla = new javax.swing.table.DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 0 ? Boolean.class : String.class;
			}
		};
		modeloTabla.addColumn(MonitorDePID.idioma.incluir());
		modeloTabla.addColumn(MonitorDePID.idioma.archivo());
		modeloTabla.addColumn(MonitorDePID.idioma.abrir());
		modeloTabla.addColumn(MonitorDePID.idioma.texto_de_boton_compartir_enlace());
		modeloTabla.addColumn(MonitorDePID.idioma.columna_url());
	}

	private JPanel construirCabecera() {
		JPanel panel = new JPanel(new BorderLayout(12, 12));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
				BorderFactory.createEmptyBorder(12, 12, 12, 12)));

		etiquetaImagen = new JLabel();
		etiquetaImagen.setHorizontalAlignment(SwingConstants.CENTER);
		etiquetaImagen.setVerticalAlignment(SwingConstants.CENTER);
		etiquetaImagen.setPreferredSize(new Dimension(160, 180));
		cargarImagen();
		panel.add(etiquetaImagen, BorderLayout.WEST);

		JPanel textos = new JPanel();
		textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
		etiquetaTitulo = new JLabel(Statics.nombre_cd.obtener());
		etiquetaTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
		etiquetaTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		etiquetaSubtitulo = new JLabel("Momose Nina Share");
		etiquetaSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
		etiquetaSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

		textoResumen = new JTextArea();
		textoResumen.setEditable(false);
		textoResumen.setFocusable(false);
		textoResumen.setLineWrap(true);
		textoResumen.setWrapStyleWord(true);
		textoResumen.setRows(4);
		textoResumen.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
		textoResumen.setAlignmentX(Component.LEFT_ALIGNMENT);
		textoResumen.setText(
				"Share a support report, upload individual logs when needed, or jump to instance sharing from a cleaner Momose Nina themed layout.");

		textos.add(etiquetaTitulo);
		textos.add(Box.createVerticalStrut(2));
		textos.add(etiquetaSubtitulo);
		textos.add(Box.createVerticalStrut(8));
		textos.add(textoResumen);
		panel.add(textos, BorderLayout.CENTER);
		return panel;
	}

	private JPanel construirPanelAccionesPrincipales() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
				BorderFactory.createEmptyBorder(12, 12, 12, 12)));

		botonCompartirTodos = new JButton(MonitorDePID.idioma.botonDeCompartirInforme());
		botonCompartirTodos.addActionListener(e -> ejecutarConConsentimiento(new Runnable() {
			@Override
			public void run() {
				setEnviando(true);
				try {
					compartirSeleccionados(e);
				} catch (Throwable t) {
					mostrarError(MonitorDePID.idioma.error_inesperado_al_compartir(), t);
				} finally {
					setEnviando(false);
				}
			}
		}));
		ajustarBotonPrincipal(botonCompartirTodos, true);
		panel.add(botonCompartirTodos);

		panel.add(Box.createVerticalStrut(8));

		botonCompartirInstanciaOModpack = new JButton(MonitorDePID.idioma.texto_de_boton_compartir_instancia_modpack());
		botonCompartirInstanciaOModpack.addActionListener(e -> compartirInstanciaOModpack(e));
		ajustarBotonPrincipal(botonCompartirInstanciaOModpack, false);
		panel.add(botonCompartirInstanciaOModpack);

		return panel;
	}

	private JPanel construirSeccionLogs() {
		JPanel panel = new JPanel(new BorderLayout(0, 8));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));

		botonToggleLogs = crearToggleSeccion("Logs individuales", logsExpandidos, e -> {
			logsExpandidos = !logsExpandidos;
			contenidoLogs.setVisible(logsExpandidos);
			actualizarTextoToggle(botonToggleLogs, "Logs individuales", logsExpandidos);
			revalidate();
		});
		panel.add(botonToggleLogs, BorderLayout.NORTH);

		contenidoLogs = new JPanel(new BorderLayout());
		contenidoLogs.setVisible(logsExpandidos);
		panelFilasLogs = new JPanel();
		panelFilasLogs.setLayout(new BoxLayout(panelFilasLogs, BoxLayout.Y_AXIS));
		contenidoLogs.add(panelFilasLogs, BorderLayout.NORTH);
		panel.add(contenidoLogs, BorderLayout.CENTER);
		return panel;
	}

	private JPanel construirSeccionPrivacidad() {
		JPanel panel = new JPanel(new BorderLayout(0, 8));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));

		botonTogglePrivacidad = crearToggleSeccion("Privacidad y configuración", privacidadExpandida, e -> {
			privacidadExpandida = !privacidadExpandida;
			contenidoPrivacidad.setVisible(privacidadExpandida);
			actualizarTextoToggle(botonTogglePrivacidad, "Privacidad y configuración", privacidadExpandida);
			revalidate();
		});
		panel.add(botonTogglePrivacidad, BorderLayout.NORTH);

		contenidoPrivacidad = construirContenidoPrivacidad();
		contenidoPrivacidad.setVisible(privacidadExpandida);
		panel.add(contenidoPrivacidad, BorderLayout.CENTER);
		return panel;
	}

	private JPanel construirContenidoPrivacidad() {
		panelConfig = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 4, 4, 4);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		int y = 0;

		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.weightx = 0;
		panelConfig.add(new JLabel(MonitorDePID.idioma.endpointDeInforme()), gbc);
		gbc.gridx = 1;
		gbc.weightx = 1;
		campoEndpoint = new JTextField(Config.obtenerInstancia().obtenerSitoDeInformes(), 40);
		panelConfig.add(campoEndpoint, gbc);
		y++;

		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.weightx = 0;
		panelConfig.add(new JLabel(MonitorDePID.idioma.apiDeLogging()), gbc);
		gbc.gridx = 1;
		gbc.weightx = 1;

		String sitoActual = Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado();
		APIdeSitioDeRegistro apiDef;
		boolean errorApi;
		try {
			apiDef = obtenerAPI();
			errorApi = false;
		} catch (NoAPIdeRegistro e) {
			apiDef = Consola.secure_logger_api;
			sitoActual = "https://securelogger.net/save/log?";
			errorApi = true;
		}

		Map<String, Set<String>> apis = new HashMap<String, Set<String>>();
		for (APIdeSitioDeRegistro api : APIdeSitioDeRegistro.APIS) {
			if (api == null) {
				continue;
			}
			Set<String> sitios = new LinkedHashSet<String>();
			if (api.equals(apiDef) && !errorApi && sitoActual != null) {
				sitios.add(sitoActual);
			}
			sitios.addAll(api.sitiosPorDefecto());
			apis.put(api.nombre(), sitios);
		}

		comboAPI = new JComboBox<String>(apis.keySet().toArray(new String[0]));
		comboAPI.setSelectedItem(apiDef.nombre());
		panelConfig.add(comboAPI, gbc);
		y++;

		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.weightx = 0;
		panelConfig.add(new JLabel(MonitorDePID.idioma.sitoDeLogging()), gbc);
		gbc.gridx = 1;
		gbc.weightx = 1;
		comboSitioRegistro = new JComboBox<String>(new String[] {});
		panelConfig.add(comboSitioRegistro, gbc);
		actualizarComboSitios(apiDef.nombre(), apis.get(apiDef.nombre()), sitoActual);
		comboAPI.addActionListener(e -> {
			String apiSeleccionada = (String) comboAPI.getSelectedItem();
			actualizarComboSitios(apiSeleccionada, apis.get(apiSeleccionada), null);
			actualizarBotonEliminador();
		});
		y++;

		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		checkAnonimizar = new JCheckBox(MonitorDePID.idioma.anonimizarRegistros());
		checkAnonimizar.setSelected(ConfigMundial.obtenerInstancia().esAnonimizarRegistros());
		panelConfig.add(checkAnonimizar, gbc);
		y++;

		JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
		JButton botonGuardar = new JButton(MonitorDePID.idioma.guardarConfigDeCompartir());
		botonGuardar.addActionListener(e -> guardarConfig());
		filaBotones.add(botonGuardar);

		botonEliminarRegistros = new JButton(MonitorDePID.idioma.eliminarRegistros());
		botonEliminarRegistros.addActionListener(e -> abrirEliminadorDeApiSeleccionada());
		filaBotones.add(botonEliminarRegistros);

		JButton botonConsentimiento = new JButton(MonitorDePID.idioma.consentimientoLFPDPPP());
		botonConsentimiento.addActionListener(e -> mostrarPopupConsentimiento(null));
		filaBotones.add(botonConsentimiento);

		JButton botonCanario = new JButton(MonitorDePID.idioma.buscador_canario_de_orden_label());
		botonCanario.addActionListener(e -> TipoGUI.CANARIO
				.obtenerGUIPredeterminado(CanarioDeOrdenJudicialGUI1984.ID, () -> new CanarioDeOrdenJudicialGUI1984())
				.init());
		filaBotones.add(botonCanario);

		gbc.gridx = 0;
		gbc.gridy = y;
		gbc.gridwidth = 2;
		panelConfig.add(filaBotones, gbc);

		actualizarBotonEliminador();
		return panelConfig;
	}

	private JPanel construirPanelResultado() {
		JPanel panel = new JPanel(new BorderLayout(6, 6));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));

		JLabel etiqueta = new JLabel("Último resultado");
		panel.add(etiqueta, BorderLayout.NORTH);

		campoEnlaceReporte = new JTextField();
		campoEnlaceReporte.setEditable(false);
		campoEnlaceReporte.setFocusable(false);
		campoEnlaceReporte.setPreferredSize(new Dimension(100, 30));
		panel.add(campoEnlaceReporte, BorderLayout.CENTER);

		return panel;
	}

	private void reconstruirFilasLogs() {
		filasLogs.clear();
		panelFilasLogs.removeAll();
		for (int i = 0; i < MonitorDePID.consolas.size(); i++) {
			Consola consola = MonitorDePID.consolas.get(i);
			FilaLog fila = new FilaLog(i, consola);
			filasLogs.add(fila);
			panelFilasLogs.add(fila.panel);
			panelFilasLogs.add(Box.createVerticalStrut(6));
		}
		panelFilasLogs.revalidate();
		panelFilasLogs.repaint();
	}

	private void ejecutarConConsentimiento(Runnable accion) {
		try {
			ConfigMundial cfg = ConfigMundial.obtenerInstancia();
			if (!cfg.obtenerConsentimientoLFPDPPP() && !consentimientoTemporal) {
				mostrarPopupConsentimiento(accion);
				return;
			}
			accion.run();
		} catch (Throwable t) {
			mostrarError(MonitorDePID.idioma.error_inesperado_al_compartir(), t);
		}
	}

	private void mostrarPopupConsentimiento(Runnable despues) {
		try {
			LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI gui = TipoGUI.LFPDPPP
					.obtenerGUIPredeterminado(
							LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos.ID,
							() -> new LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos());
			gui.setDespuesDeAceptar(() -> {
				consentimientoTemporal = true;
				if (despues != null) {
					SwingUtilities.invokeLater(despues);
				}
			});
			gui.init();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			mostrarError(MonitorDePID.idioma.error_inesperado(), t);
		}
	}

	private void compartirLogIndividual(FilaLog fila) {
		ejecutarConConsentimiento(new Runnable() {
			@Override
			public void run() {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				try {
					List<String> urls = fila.consola.obtainerEnlaces();
					String celda = (urls == null || urls.isEmpty()) ? "" : String.join("\n", urls);
					fila.enlace.setText(celda);
					if (modeloTabla != null && fila.index < modeloTabla.getRowCount()) {
						modeloTabla.setValueAt(celda, fila.index, 4);
					}
					if (campoEnlaceReporte != null) {
						campoEnlaceReporte.setText(celda);
					}
					if (celda != null && !celda.isEmpty()) {
						copiarAlPortapapeles(celda);
						mostrarInfo(MonitorDePID.idioma.copiadoAlPortapapeles());
					}
				} catch (DemasiadoGrande ex) {
					mostrarError(MonitorDePID.idioma.registroDemasiadoGrande(), ex);
				} catch (ErrorConPublicar ex) {
					mostrarError(MonitorDePID.idioma.errorConPublicarRegistro(ex.problema), ex);
				} catch (NoAPIdeRegistro ex) {
					mostrarError(MonitorDePID.idioma.apiDeRegistroNoExiste(), ex);
				} catch (LimteDeTasa ex) {
					mostrarError(MonitorDePID.idioma.limite_de_solicitudes(), ex);
				} catch (Throwable t) {
					mostrarError(MonitorDePID.idioma.error_inesperado_al_generar_enlaces(), t);
				} finally {
					setCursor(Cursor.getDefaultCursor());
				}
			}
		});
	}

	private void abrirArchivo(Consola consola) {
		try {
			if (consola == null || consola.archivo == null) {
				return;
			}
			File f = consola.archivo.toFile();
			if (!Desktop.isDesktopSupported()) {
				copiarAlPortapapeles(f.getAbsolutePath());
				mostrarInfo(MonitorDePID.idioma.escritorio_no_soportado_se_copia_ruta());
				return;
			}
			Desktop d = Desktop.getDesktop();
			if (f.exists() && d.isSupported(Desktop.Action.OPEN)) {
				d.open(f);
			} else {
				File parent = f.getParentFile();
				if (parent != null && parent.exists() && d.isSupported(Desktop.Action.OPEN)) {
					d.open(parent);
				} else {
					copiarAlPortapapeles(f.getAbsolutePath());
					mostrarInfo(MonitorDePID.idioma.no_se_pudo_abrir_se_copia_ruta());
				}
			}
		} catch (Throwable t) {
			mostrarError(MonitorDePID.idioma.no_se_pudo_editar_se_copia_ruta(), t);
		}
	}

	private JButton crearToggleSeccion(String titulo, boolean expandido, java.awt.event.ActionListener accion) {
		JButton boton = new JButton();
		actualizarTextoToggle(boton, titulo, expandido);
		boton.setHorizontalAlignment(SwingConstants.LEFT);
		boton.addActionListener(accion);
		return boton;
	}

	private void actualizarTextoToggle(JButton boton, String titulo, boolean expandido) {
		boton.setText((expandido ? "▼ " : "▶ ") + titulo);
	}

	private void ajustarBotonPrincipal(JButton boton, boolean primario) {
		boton.setAlignmentX(Component.LEFT_ALIGNMENT);
		boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
		boton.setFont(new Font("SansSerif", Font.BOLD, 14));
		boton.setFocusPainted(false);
		if (!esMacOS()) {
			boton.setOpaque(true);
			boton.setContentAreaFilled(true);
			boton.setBackground(primario ? colorAcento.obtener() : colorBotonSecundario.obtener());
			boton.setForeground(primario ? Color.WHITE : colorTexto.obtener());
		}
	}

	private void cargarImagen() {
		try {
			String ruta = Statics.carpeta.resolve("imagenes/momose_nina.png").toString();
			ImageIcon icono = new ImageIcon(ruta);
			if (icono.getIconWidth() > 0) {
				Image imagen = icono.getImage().getScaledInstance(150, 170, Image.SCALE_SMOOTH);
				etiquetaImagen.setIcon(new ImageIcon(imagen));
				etiquetaImagen.setText("");
				return;
			}
		} catch (Throwable ignored) {
		}
		etiquetaImagen.setText("momose_nina.png");
	}

	@Override
	public void recargarApariencia() {
		aplicarRecursivo(panelPrincipal);

		if (panelPrincipal != null) {
			panelPrincipal.setBackground(colorFondo.obtener());
		}
		if (panelCabecera != null) {
			panelCabecera.setBackground(colorPanel.obtener());
		}
		if (panelAcciones != null) {
			panelAcciones.setBackground(colorPanel.obtener());
		}
		if (panelContenido != null) {
			panelContenido.setBackground(colorFondo.obtener());
		}
		if (panelResultado != null) {
			panelResultado.setBackground(colorPanel.obtener());
		}
		if (panelSeccionLogs != null) {
			panelSeccionLogs.setBackground(colorPanel.obtener());
		}
		if (panelSeccionPrivacidad != null) {
			panelSeccionPrivacidad.setBackground(colorPanel.obtener());
		}
		if (contenidoLogs != null) {
			contenidoLogs.setBackground(colorPanelSecundario.obtener());
		}
		if (contenidoPrivacidad != null) {
			contenidoPrivacidad.setBackground(colorPanelSecundario.obtener());
		}
		if (panelFilasLogs != null) {
			panelFilasLogs.setBackground(colorPanelSecundario.obtener());
		}
		if (textoResumen != null) {
			textoResumen.setBackground(colorPanel.obtener());
			textoResumen.setForeground(colorSubtexto.obtener());
		}
		if (etiquetaTitulo != null) {
			etiquetaTitulo.setForeground(colorTexto.obtener());
		}
		if (etiquetaSubtitulo != null) {
			etiquetaSubtitulo.setForeground(colorAcento.obtener());
		}
		if (campoEnlaceReporte != null) {
			campoEnlaceReporte.setBackground(colorCampo.obtener());
			campoEnlaceReporte.setForeground(colorTexto.obtener());
		}
		if (campoEndpoint != null) {
			campoEndpoint.setBackground(colorCampo.obtener());
			campoEndpoint.setForeground(colorTexto.obtener());
		}
		if (comboAPI != null && !esMacOS()) {
			comboAPI.setBackground(colorCampo.obtener());
			comboAPI.setForeground(colorTexto.obtener());
		}
		if (comboSitioRegistro != null && !esMacOS()) {
			comboSitioRegistro.setBackground(colorCampo.obtener());
			comboSitioRegistro.setForeground(colorTexto.obtener());
		}
		if (checkAnonimizar != null) {
			checkAnonimizar.setBackground(colorPanelSecundario.obtener());
			checkAnonimizar.setForeground(colorTexto.obtener());
		}
		for (FilaLog fila : filasLogs) {
			fila.aplicarApariencia();
		}
		estilizarSeccion(botonToggleLogs);
		estilizarSeccion(botonTogglePrivacidad);
		ajustarBotonPrincipal(botonCompartirTodos, true);
		ajustarBotonPrincipal(botonCompartirInstanciaOModpack, false);

		revalidate();
		repaint();
	}

	private void estilizarSeccion(JButton boton) {
		if (boton == null) {
			return;
		}
		boton.setFocusPainted(false);
		boton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		if (!esMacOS()) {
			boton.setBackground(colorPanel.obtener());
			boton.setForeground(colorTexto.obtener());
			boton.setOpaque(true);
		}
	}

	private void aplicarRecursivo(Component c) {
		if (c == null) {
			return;
		}
		c.setForeground(colorTexto.obtener());
		if (c instanceof JPanel) {
			c.setBackground(colorPanel.obtener());
		}
		if (c instanceof java.awt.Container) {
			for (Component hijo : ((java.awt.Container) c).getComponents()) {
				aplicarRecursivo(hijo);
			}
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<ElementoConfig>();
		ret.add(colorFondo);
		ret.add(colorPanel);
		ret.add(colorPanelSecundario);
		ret.add(colorTexto);
		ret.add(colorSubtexto);
		ret.add(colorAcento);
		ret.add(colorAcentoSuave);
		ret.add(colorBorde);
		ret.add(colorBotonSecundario);
		ret.add(colorCampo);
		return ret;
	}

	private class FilaLog {
		final int index;
		final Consola consola;
		final JPanel panel;
		final JCheckBox incluir;
		final JLabel nombre;
		final JButton abrir;
		final JButton compartir;
		final JTextField enlace;

		FilaLog(int index, Consola consola) {
			this.index = index;
			this.consola = consola;
			this.panel = new JPanel(new BorderLayout(8, 8));
			this.panel.setBorder(
					BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde.obtener(), 1),
							BorderFactory.createEmptyBorder(8, 8, 8, 8)));

			JPanel izquierda = new JPanel();
			izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.Y_AXIS));
			incluir = new JCheckBox(MonitorDePID.idioma.incluir(), true);
			incluir.addActionListener(e -> {
				if (modeloTabla != null && index < modeloTabla.getRowCount()) {
					modeloTabla.setValueAt(Boolean.valueOf(incluir.isSelected()), index, 0);
				}
			});
			nombre = new JLabel(consola != null && consola.archivo != null && consola.archivo.getFileName() != null
					? consola.archivo.getFileName().toString()
					: "log.txt");
			nombre.setFont(new Font("SansSerif", Font.BOLD, 13));
			izquierda.add(incluir);
			izquierda.add(Box.createVerticalStrut(2));
			izquierda.add(nombre);

			JPanel derecha = new JPanel();
			derecha.setLayout(new BoxLayout(derecha, BoxLayout.Y_AXIS));
			JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
			abrir = new JButton(MonitorDePID.idioma.abrir());
			abrir.addActionListener(e -> abrirArchivo(consola));
			compartir = new JButton(MonitorDePID.idioma.texto_de_boton_compartir_enlace());
			compartir.addActionListener(e -> compartirLogIndividual(this));
			filaBotones.add(abrir);
			filaBotones.add(compartir);
			enlace = new JTextField();
			enlace.setEditable(false);
			enlace.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
			derecha.add(filaBotones);
			derecha.add(Box.createVerticalStrut(6));
			derecha.add(enlace);

			panel.add(izquierda, BorderLayout.WEST);
			panel.add(derecha, BorderLayout.CENTER);
			aplicarApariencia();
		}

		void aplicarApariencia() {
			panel.setBackground(colorPanelSecundario.obtener());
			incluir.setBackground(colorPanelSecundario.obtener());
			incluir.setForeground(colorTexto.obtener());
			nombre.setForeground(colorTexto.obtener());
			enlace.setBackground(colorCampo.obtener());
			enlace.setForeground(colorTexto.obtener());
			for (JButton boton : new JButton[] { abrir, compartir }) {
				boton.setFocusPainted(false);
				if (!esMacOS()) {
					boton.setBackground(colorBotonSecundario.obtener());
					boton.setForeground(colorTexto.obtener());
					boton.setOpaque(true);
				}
			}
		}
	}
}