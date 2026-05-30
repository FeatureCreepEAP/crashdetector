package com.asbestosstar.crashdetector.gui.tipos.compartir;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetector.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sito_registro.LimteDeTasa;
import com.asbestosstar.crashdetector.api_sito_registro.NoAPIdeRegistro;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.ComboIdiomasConIcono;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.lfpdppp.LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI;
import com.asbestosstar.crashdetector.gui.tipos.lfpdppp.LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos;
import com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUIEstiloLanzer;

public class DialogoCompartirPrimitiva extends DialogoCompartir {

	public static String ID = "dialogo_compartir_primitiva";

	private static final int ANCHO_CONTROLES = 380;
	private static final int ALTO_CONTROLES = 24;
	private static final int ALTO_MENSAJE = 58;

	private JPanel panelEtiquetaMensaje;
	private JTextArea etiquetaMensaje;
	private JComboBox<String> comboIdioma;
	private JButton botonCentroSoporte;
	private JButton botonModoNormal;

	private boolean consentimientoTemporal = false;

	private ConfigColor colorFondo = ConfigColor.de("dialogo.compartir.primitiva.fondo",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));

	private ConfigColor colorTexto = ConfigColor.de("dialogo.compartir.primitiva.texto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));

	private ConfigColor colorBoton = ConfigColor.de("dialogo.compartir.primitiva.boton",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));

	private ConfigColor colorCaja = ConfigColor.de("dialogo.compartir.primitiva.caja",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));

	private ConfigString urlCentroSoporte = ConfigString.de("dialogo.compartir.primitiva.centro_soporte.url",
			"https://example.com/");

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void preperar(Instant instant) {
		this.instant = instant;

		setTitle(Statics.nombre_cd.obtener());
		setResizable(false);

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				cerrarTodoElPrograma();
			}
		});

		setLayout(new BorderLayout(0, 0));

		panelPrincipal = new JPanel(new BorderLayout(0, 0));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		panelControles = new JPanel();
		panelControles.setLayout(new BoxLayout(panelControles, BoxLayout.Y_AXIS));
		panelControles.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		panelEtiquetaMensaje = new JPanel(new BorderLayout(0, 0));
		panelEtiquetaMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelEtiquetaMensaje.setMaximumSize(new Dimension(ANCHO_CONTROLES, ALTO_MENSAJE));
		panelEtiquetaMensaje.setPreferredSize(new Dimension(ANCHO_CONTROLES, ALTO_MENSAJE));
		panelEtiquetaMensaje.setMinimumSize(new Dimension(ANCHO_CONTROLES, ALTO_MENSAJE));
		panelEtiquetaMensaje.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		etiquetaMensaje = new JTextArea();
		etiquetaMensaje.setEditable(false);
		etiquetaMensaje.setFocusable(false);
		etiquetaMensaje.setOpaque(false);
		etiquetaMensaje.setLineWrap(true);
		etiquetaMensaje.setWrapStyleWord(true);
		etiquetaMensaje.setRows(3);
		etiquetaMensaje.setColumns(40);
		etiquetaMensaje.setFont(new Font("Segoe UI", Font.BOLD, 13));
		etiquetaMensaje.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));

		panelEtiquetaMensaje.add(etiquetaMensaje, BorderLayout.CENTER);

		botonCompartirMarkdown = new JButton();
		botonCompartirMarkdown.addActionListener(e -> compartirConPrivacidad());

		botonCentroSoporte = new JButton();
		botonCentroSoporte.addActionListener(e -> abrirCentroSoporte());

		comboIdioma = new ComboIdiomasConIcono(Idioma.mapaParaComboBoxIdiomas());
		comboIdioma.setAlignmentX(Component.CENTER_ALIGNMENT);
		fijarTamanoControl(comboIdioma);

		String nombreActual = Idioma.nombreDeIdiomaDesdeCodigo(MonitorDePID.idioma.codigo());
		comboIdioma.setSelectedItem(nombreActual);
		comboIdioma.addActionListener(e -> cambiarIdioma());

		botonModoNormal = new JButton();
		botonModoNormal.addActionListener(e -> abrirModoNormalEstiloTL());

		estilizarBoton(botonCompartirMarkdown);
		estilizarBoton(botonCentroSoporte);
		estilizarBoton(botonModoNormal);

		panelControles.add(panelEtiquetaMensaje);
		panelControles.add(Box.createVerticalStrut(2));
		panelControles.add(botonCompartirMarkdown);
		panelControles.add(Box.createVerticalStrut(2));
		panelControles.add(botonCentroSoporte);
		panelControles.add(Box.createVerticalStrut(2));
		panelControles.add(comboIdioma);
		panelControles.add(Box.createVerticalStrut(2));
		panelControles.add(botonModoNormal);

		panelPrincipal.add(panelControles, BorderLayout.CENTER);
		add(panelPrincipal, BorderLayout.CENTER);

		recargarTextos();
		recargarApariencia();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void cerrarTodoElPrograma() {
		try {
			CrashDetectorLogger.log("Cerrando DialogoCompartirPrimitiva. Terminando programa completo.");
		} catch (Throwable ignored) {
		}
		System.exit(0);
	}

	private void cambiarIdioma() {
		String seleccion = (String) comboIdioma.getSelectedItem();
		String codigo = Idioma.codigoDesdeNombreVisible(seleccion);

		if (codigo == null)
			return;

		try {
			ConfigMundial.obtenerInstancia().guardarIdioma(codigo);
			MonitorDePID.idioma = Idioma.detectar();
			recargarTextos();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private void compartirConPrivacidad() {
		try {
			ConfigMundial cfg = ConfigMundial.obtenerInstancia();

			if (!cfg.obtenerConsentimientoLFPDPPP() && !consentimientoTemporal) {
				LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI gui = TipoGUI.LFPDPPP
						.obtenerGUIPredeterminado(
								LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos.ID,
								() -> new LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos());

				gui.setDespuesDeAceptar(() -> {
					consentimientoTemporal = true;
					SwingUtilities.invokeLater(() -> compartirConPrivacidad());
				});

				gui.init();
				return;
			}

			setEnviando(true);
			copiarTodosLosEnlacesMarkdown();

		} catch (Throwable t) {
			mostrarError(MonitorDePID.idioma.error_inesperado_al_generar_enlaces(), t);
		} finally {
			setEnviando(false);
		}
	}

	private void copiarTodosLosEnlacesMarkdown()
			throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro, LimteDeTasa {

		List<String> lineas = new ArrayList<String>();

		for (Consola consola : MonitorDePID.consolas) {
			if (consola == null)
				continue;

			String nombre = "log";

			if (consola.archivo != null && consola.archivo.getFileName() != null) {
				nombre = consola.archivo.getFileName().toString();
			}

			List<String> urls = consola.obtainerEnlaces();

			if (urls == null || urls.isEmpty())
				continue;

			for (int i = 0; i < urls.size(); i++) {
				String etiqueta = urls.size() == 1 ? nombre : nombre + " parte " + (i + 1);
				lineas.add("- [" + escaparMarkdown(etiqueta) + "](" + urls.get(i) + ")");
			}
		}

		if (lineas.isEmpty()) {
			mostrarInfo(MonitorDePID.idioma.noHayEnlacesParaCopiar());
			return;
		}

		String markdown = unirLineas(lineas);
		copiarAlPortapapeles(markdown);
		mostrarInfo(MonitorDePID.idioma.copiadoAlPortapapeles());
	}

	private void abrirCentroSoporte() {
		try {
			String url = urlCentroSoporte.obtener();

			if (url == null || url.trim().isEmpty()) {
				mostrarInfo(MonitorDePID.idioma.noHayCentroSoporteConfigurado());
				return;
			}

			abrirURL(url.trim());
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			mostrarError(MonitorDePID.idioma.error_inesperado(), t);
		}
	}

	private void abrirModoNormalEstiloTL() {
		try {
			PrincipalGUIEstiloLanzer gui = new PrincipalGUIEstiloLanzer();
			gui.constructir(instant, null);
		} catch (Throwable t) {
			mostrarError(MonitorDePID.idioma.error_inesperado(), t);
		}
	}

	private void recargarTextos() {
		setTitle(Statics.nombre_cd.obtener());

		if (etiquetaMensaje != null) {
			etiquetaMensaje.setText(MonitorDePID.idioma.mensajeDialogoCompartirPrimitiva());
			etiquetaMensaje.setCaretPosition(0);
		}

		if (botonCompartirMarkdown != null) {
			botonCompartirMarkdown.setText(MonitorDePID.idioma.texto_de_boton_compartir_markdown());
		}

		if (botonCentroSoporte != null) {
			botonCentroSoporte.setText(MonitorDePID.idioma.centroDeSoporte());
		}

		if (botonModoNormal != null) {
			botonModoNormal.setText(MonitorDePID.idioma.irAModoNormalEstiloTL());
		}
	}

	@Override
	public void recargarApariencia() {
		if (panelPrincipal != null)
			panelPrincipal.setBackground(colorFondo.obtener());

		if (panelControles != null)
			panelControles.setBackground(colorFondo.obtener());

		if (panelEtiquetaMensaje != null)
			panelEtiquetaMensaje.setBackground(colorFondo.obtener());

		if (etiquetaMensaje != null) {
			etiquetaMensaje.setForeground(colorTexto.obtener());
			etiquetaMensaje.setBackground(colorFondo.obtener());
		}

		if (comboIdioma != null && !CrashDetectorGUI.esMac()) {
			comboIdioma.setBackground(colorCaja.obtener());
			comboIdioma.setForeground(colorTexto.obtener());
		}

		estilizarBoton(botonCompartirMarkdown);
		estilizarBoton(botonCentroSoporte);
		estilizarBoton(botonModoNormal);
	}

	private void estilizarBoton(JButton boton) {
		if (boton == null)
			return;

		boton.setAlignmentX(Component.CENTER_ALIGNMENT);
		fijarTamanoControl(boton);
		boton.setFocusPainted(false);
		boton.setMargin(new java.awt.Insets(0, 2, 0, 2));
		boton.setBorder(BorderFactory.createLineBorder(colorTexto.obtener(), 1));
		boton.setFont(new Font("Segoe UI", Font.BOLD, 12));

		if (!CrashDetectorGUI.esMac()) {
			boton.setBackground(colorBoton.obtener());
			boton.setForeground(colorTexto.obtener());
			boton.setContentAreaFilled(true);
			boton.setOpaque(true);
		}
	}

	private void fijarTamanoControl(Component componente) {
		if (componente == null)
			return;

		Dimension d = new Dimension(ANCHO_CONTROLES, ALTO_CONTROLES);
		componente.setMinimumSize(d);
		componente.setPreferredSize(d);
		componente.setMaximumSize(d);
	}

	private String unirLineas(List<String> lineas) {
		StringBuilder sb = new StringBuilder();

		for (String linea : lineas) {
			if (sb.length() > 0)
				sb.append("\n");

			sb.append(linea);
		}

		return sb.toString();
	}

	private String escaparMarkdown(String texto) {
		if (texto == null)
			return "";

		return texto.replace("[", "\\[").replace("]", "\\]").replace("(", "\\(").replace(")", "\\)");
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorEnlace());

		elementos.add(colorFondo);
		elementos.add(colorTexto);
		elementos.add(colorBoton);
		elementos.add(colorCaja);
		elementos.add(urlCentroSoporte);

		return elementos;
	}
}