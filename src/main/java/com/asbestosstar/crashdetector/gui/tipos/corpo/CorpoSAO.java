package com.asbestosstar.crashdetector.gui.tipos.corpo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.ComboIdiomasConIcono;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public class CorpoSAO extends CorpoBase {
	private static final long serialVersionUID = 1L;
	public static String ID = "sao";

	// Configuraciones de color

	// Inicializar configuraciones de color con valores por defecto que coincidan
	// con la imagen
	/*
	 * Paleta por defecto inspirada en la imagen: - Fondo: pared clara/beige
	 * (dominante). - Texto: casi negro (carpeta). - Botones: gris traje (con texto
	 * claro). - Caja de texto: claro (papel/pared) con borde gris. - Enlace/acento:
	 * tono morado del texto de la imagen. - Bordes: gris piedra del muro.
	 */

	private ConfigColor colorFondoVentana = ConfigColor.de("tema.corpo.sao.color.fondo.ventana",
			new Color(212, 204, 198));
	private ConfigColor colorTexto = ConfigColor.de("tema.corpo.sao.color.texto", new Color(29, 30, 35));
	private ConfigColor colorBoton = ConfigColor.de("tema.corpo.sao.color.boton", new Color(124, 119, 119));
	private ConfigColor colorCajaTexto = ConfigColor.de("tema.corpo.sao.color.caja_texto", new Color(236, 230, 226));
	private ConfigColor colorEnlace = ConfigColor.de("tema.corpo.sao.color.enlace", new Color(89, 72, 84));
	private ConfigColor colorBordePanel = ConfigColor.de("tema.corpo.sao.color.borde_panel", new Color(188, 179, 173));

	// Componentes de la interfaz
	private JPanel raizPanel;
	private JPanel panelIzquierdo;
	private JPanel panelDerecho;
	private JPanel panelConfiguracion;
	private JPanel panelBotones;
	private JLabel imagenSAO;
	private JComboBox<String> comboIdiomaRespaldo;
	private JCheckBox checkBuscardor;
	private JTextField campoNombreHerramienta;
	private JCheckBox checkCondenarPirata;
	private JButton botonLanzadoresRecomendados;
	private JButton botonLanzadoresDesaconsejados;
	private JButton botonModsRecomendados;
	private JButton botonModsDesaconsejados;
	private JButton botonAntiTamper;
	private JButton botonFirmas; // New button for signatures
	private JButton botonDerechosMaranda; // New button for Maranda rights
	private JButton botonVerificaciones; // New button for verifications
	private JPanel panelDerechos; // Panel to hold the Maranda rights button
	private JCheckBox checkSuprimirConsolaCD;
	private JCheckBox checkSuprimirVDST;
	private JCheckBox checkMostrarSelectorAplicacionPrincipal;
	private JCheckBox checkMostrarBotonCDLauncherPrincipal;
	private JCheckBox checkMostrarBotonCDModsPrincipal;
	private JCheckBox checkMostrarBotonIAPrincipal;

	@Override
	public void init() {

		setTitle(MonitorDePID.idioma.configuracionCorporativa());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		setMinimumSize(new Dimension(800, 700));
		setPreferredSize(new Dimension(850, 700));

		setAlwaysOnTop(false);
		setModal(false);

		raizPanel = new JPanel(new BorderLayout(10, 10));
		raizPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setPreferredSize(new Dimension(244, 338));
		panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

		imagenSAO = new JLabel();
		imagenSAO.setPreferredSize(new Dimension(244, 338));
		imagenSAO.setHorizontalAlignment(JLabel.CENTER);
		imagenSAO.setVerticalAlignment(JLabel.CENTER);
		panelIzquierdo.add(imagenSAO, BorderLayout.CENTER);

		panelDerecho = new JPanel(new BorderLayout(10, 10));
		panelDerecho.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		panelConfiguracion = new JPanel(new GridBagLayout());
		panelConfiguracion.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorBordePanel.obtener(), 1),
						MonitorDePID.idioma.configuracionBasica()));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new java.awt.Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridwidth = 1;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.3;
		JLabel labelIdioma = new JLabel(MonitorDePID.idioma.idiomaRespaldo());
		labelIdioma.setForeground(colorTexto.obtener());
		panelConfiguracion.add(labelIdioma, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.7;
		comboIdiomaRespaldo = new ComboIdiomasConIcono(getMapaIdiomas());
		comboIdiomaRespaldo.setPreferredSize(new Dimension(150, 30));
		panelConfiguracion.add(comboIdiomaRespaldo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.3;
		JLabel labelBuscardor = new JLabel(MonitorDePID.idioma.buscardorHabilitado());
		labelBuscardor.setForeground(colorTexto.obtener());
		panelConfiguracion.add(labelBuscardor, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0.7;
		checkBuscardor = new JCheckBox();
		panelConfiguracion.add(checkBuscardor, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0.3;
		JLabel labelNombre = new JLabel(MonitorDePID.idioma.nombreHerramienta());
		labelNombre.setForeground(colorTexto.obtener());
		panelConfiguracion.add(labelNombre, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0.7;
		campoNombreHerramienta = new JTextField();
		panelConfiguracion.add(campoNombreHerramienta, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0.3;
		JLabel labelPirateria = new JLabel(MonitorDePID.idioma.condenarPirateria());
		labelPirateria.setForeground(colorTexto.obtener());
		panelConfiguracion.add(labelPirateria, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.weightx = 0.7;
		checkCondenarPirata = new JCheckBox();
		panelConfiguracion.add(checkCondenarPirata, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weightx = 0.3;
		JLabel labelSuprimirConsolaCD = new JLabel(MonitorDePID.idioma.suprimirConsolaCD());
		labelSuprimirConsolaCD.setForeground(colorTexto.obtener());
		panelConfiguracion.add(labelSuprimirConsolaCD, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.weightx = 0.7;
		checkSuprimirConsolaCD = new JCheckBox();
		panelConfiguracion.add(checkSuprimirConsolaCD, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.weightx = 0.3;
		JLabel labelSuprimirVDST = new JLabel(MonitorDePID.idioma.suprimirVerificacionDeStacktrazos());
		labelSuprimirVDST.setForeground(colorTexto.obtener());
		panelConfiguracion.add(labelSuprimirVDST, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.weightx = 0.7;
		checkSuprimirVDST = new JCheckBox();
		panelConfiguracion.add(checkSuprimirVDST, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.weightx = 1.0;
		panelDerechos = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelDerechos.setOpaque(false);
		botonDerechosMaranda = new JButton(MonitorDePID.idioma.derechosMiranda());
		botonDerechosMaranda.setVisible(false);
		panelDerechos.add(botonDerechosMaranda);
		panelConfiguracion.add(panelDerechos, gbc);

		panelDerecho.add(panelConfiguracion, BorderLayout.NORTH);

		panelBotones = new JPanel(new GridBagLayout());
		panelBotones.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(colorBordePanel.obtener(), 1), MonitorDePID.idioma.funcionalidades()));

		gbc = new GridBagConstraints();
		gbc.insets = new java.awt.Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		botonLanzadoresRecomendados = new JButton(MonitorDePID.idioma.lanzadoresRecomendados());
		panelBotones.add(botonLanzadoresRecomendados, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		botonLanzadoresDesaconsejados = new JButton(MonitorDePID.idioma.lanzadoresDesaconsejados());
		panelBotones.add(botonLanzadoresDesaconsejados, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.5;
		botonModsRecomendados = new JButton(MonitorDePID.idioma.modsRecomendados());
		panelBotones.add(botonModsRecomendados, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0.5;
		botonModsDesaconsejados = new JButton(MonitorDePID.idioma.modsDesaconsejados());
		panelBotones.add(botonModsDesaconsejados, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 0.5;
		botonAntiTamper = new JButton(MonitorDePID.idioma.antiTamper());
		panelBotones.add(botonAntiTamper, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 0.5;
		botonFirmas = new JButton(TipoGUI.EDITOR_FIRMAS.etiquetaDelBoton());
		panelBotones.add(botonFirmas, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.weightx = 1.0;
		botonVerificaciones = new JButton(TipoGUI.DESHABLICAR_VERIFICACIONES.etiquetaDelBoton());
		panelBotones.add(botonVerificaciones, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.weightx = 0.3;
		JLabel labelMostrarSelectorAplicacion = new JLabel(MonitorDePID.idioma.mostrarSelectorAplicacionPrincipal());
		labelMostrarSelectorAplicacion.setForeground(colorTexto.obtener());
		panelConfiguracion.add(labelMostrarSelectorAplicacion, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.weightx = 0.7;
		checkMostrarSelectorAplicacionPrincipal = new JCheckBox();
		panelConfiguracion.add(checkMostrarSelectorAplicacionPrincipal, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.weightx = 0.3;
		JLabel labelMostrarCDLauncher = new JLabel(MonitorDePID.idioma.mostrarBotonCDLauncherPrincipal());
		labelMostrarCDLauncher.setForeground(colorTexto.obtener());
		panelConfiguracion.add(labelMostrarCDLauncher, gbc);

		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.weightx = 0.7;
		checkMostrarBotonCDLauncherPrincipal = new JCheckBox();
		panelConfiguracion.add(checkMostrarBotonCDLauncherPrincipal, gbc);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.weightx = 0.3;
		JLabel labelMostrarCDMods = new JLabel(MonitorDePID.idioma.mostrarBotonCDModsPrincipal());
		labelMostrarCDMods.setForeground(colorTexto.obtener());
		panelConfiguracion.add(labelMostrarCDMods, gbc);

		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.weightx = 0.7;
		checkMostrarBotonCDModsPrincipal = new JCheckBox();
		panelConfiguracion.add(checkMostrarBotonCDModsPrincipal, gbc);

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.weightx = 0.3;
		JLabel labelMostrarIA = new JLabel(MonitorDePID.idioma.mostrarBotonIAPrincipal());
		labelMostrarIA.setForeground(colorTexto.obtener());
		panelConfiguracion.add(labelMostrarIA, gbc);

		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.weightx = 0.7;
		checkMostrarBotonIAPrincipal = new JCheckBox();
		panelConfiguracion.add(checkMostrarBotonIAPrincipal, gbc);

		panelDerecho.add(panelBotones, BorderLayout.CENTER);

		raizPanel.add(panelIzquierdo, BorderLayout.WEST);
		raizPanel.add(panelDerecho, BorderLayout.CENTER);

		getContentPane().add(raizPanel);

		cargarImagenSAO();
		aplicarApariencia();
		actualizarValores();
		agregarListeners();

		pack();
		setSize(850, 650);
		setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void cargarImagenSAO() {
		try {
			// Cargar imagen de SAO desde recursos
			java.net.URL url = Statics.carpeta.resolve("imagenes/sao.png").toFile().toURL();
			if (url != null) {
				ImageIcon icon = new ImageIcon(url);
				Image img = icon.getImage().getScaledInstance(244, 338, Image.SCALE_SMOOTH);
				imagenSAO.setIcon(new ImageIcon(img));
			} else {
				// Alternativa si no se encuentra la imagen
				imagenSAO.setText(MonitorDePID.idioma.imagenNoEncontrada());
				imagenSAO.setHorizontalAlignment(JLabel.CENTER);
				imagenSAO.setVerticalAlignment(JLabel.CENTER);
				imagenSAO.setForeground(colorTexto.obtener());
			}
		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
			imagenSAO.setText(MonitorDePID.idioma.errorCargandoImagen());
			imagenSAO.setHorizontalAlignment(JLabel.CENTER);
			imagenSAO.setVerticalAlignment(JLabel.CENTER);
			imagenSAO.setForeground(colorTexto.obtener());
		}
	}

	@Override
	public void aplicarApariencia() {
		// Aplicar colores a todos los componentes
		getContentPane().setBackground(colorFondoVentana.obtener());
		raizPanel.setBackground(colorFondoVentana.obtener());

		panelIzquierdo.setBackground(colorFondoVentana.obtener());
		panelDerecho.setBackground(colorFondoVentana.obtener());
		panelConfiguracion.setBackground(colorFondoVentana.obtener());
		panelBotones.setBackground(colorFondoVentana.obtener());
		panelDerechos.setBackground(colorFondoVentana.obtener());

		checkMostrarSelectorAplicacionPrincipal.setForeground(colorTexto.obtener());
		checkMostrarBotonCDLauncherPrincipal.setForeground(colorTexto.obtener());
		checkMostrarBotonCDModsPrincipal.setForeground(colorTexto.obtener());
		checkMostrarBotonIAPrincipal.setForeground(colorTexto.obtener());

		checkMostrarSelectorAplicacionPrincipal.setBackground(colorFondoVentana.obtener());
		checkMostrarBotonCDLauncherPrincipal.setBackground(colorFondoVentana.obtener());
		checkMostrarBotonCDModsPrincipal.setBackground(colorFondoVentana.obtener());
		checkMostrarBotonIAPrincipal.setBackground(colorFondoVentana.obtener());

		checkMostrarSelectorAplicacionPrincipal.setOpaque(false);
		checkMostrarBotonCDLauncherPrincipal.setOpaque(false);
		checkMostrarBotonCDModsPrincipal.setOpaque(false);
		checkMostrarBotonIAPrincipal.setOpaque(false);

		// Estilizar botones
		estilizarBoton(botonLanzadoresRecomendados);
		estilizarBoton(botonLanzadoresDesaconsejados);
		estilizarBoton(botonModsRecomendados);
		estilizarBoton(botonModsDesaconsejados);
		estilizarBoton(botonAntiTamper);
		estilizarBoton(botonFirmas);
		estilizarBoton(botonDerechosMaranda);
		estilizarBoton(botonVerificaciones);

		// Estilizar campo de texto
		estilizarCampo(campoNombreHerramienta);

		// Actualizar checkboxes
		checkBuscardor.setForeground(colorTexto.obtener());
		checkCondenarPirata.setForeground(colorTexto.obtener());
		checkSuprimirConsolaCD.setForeground(colorTexto.obtener());
		checkSuprimirVDST.setForeground(colorTexto.obtener());

		// Actualizar bordes de los paneles
		TitledBorder configBorder = (TitledBorder) panelConfiguracion.getBorder();
		configBorder.setTitleColor(colorTexto.obtener());
		configBorder.setBorder(BorderFactory.createLineBorder(colorBordePanel.obtener(), 1));

		TitledBorder funcBorder = (TitledBorder) panelBotones.getBorder();
		funcBorder.setTitleColor(colorTexto.obtener());
		funcBorder.setBorder(BorderFactory.createLineBorder(colorBordePanel.obtener(), 1));

		// Forzar repintado
		revalidate();
		repaint();
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();
		colorFondoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoVentana());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorCajaTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorCajaTexto());
		colorEnlace.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorEnlace());
		colorBordePanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());

		elementos.add(colorFondoVentana);
		elementos.add(colorTexto);
		elementos.add(colorBoton);
		elementos.add(colorCajaTexto);
		elementos.add(colorEnlace);
		elementos.add(colorBordePanel);

		return elementos;
	}

	private void actualizarValores() {
		// Establecer valores iniciales desde la configuración
		comboIdiomaRespaldo.setSelectedItem(Idioma.nombreDeIdiomaDesdeCodigo(obtenerIdiomaRespaldo()));
		checkBuscardor.setSelected(obtenerBuscardorHablicar());
		campoNombreHerramienta.setText(obtenerNombreCD());
		checkCondenarPirata.setSelected(obtenerCondenarPirata());
		checkSuprimirConsolaCD.setSelected(obtenerSuprimirConsolaCD());
		checkSuprimirVDST.setSelected(obtenerSuprimirVerificacionDeStacktrazos());

		checkMostrarSelectorAplicacionPrincipal.setSelected(obtenerMostrarSelectorAplicacionPrincipal());
		checkMostrarBotonCDLauncherPrincipal.setSelected(obtenerMostrarBotonCDLauncherPrincipal());
		checkMostrarBotonCDModsPrincipal.setSelected(obtenerMostrarBotonCDModsPrincipal());
		checkMostrarBotonIAPrincipal.setSelected(obtenerMostrarBotonIAPrincipal());

		// Actualizar visibilidad del botón de derechos Maranda
		botonDerechosMaranda.setVisible(obtenerCondenarPirata());
	}

	private void agregarListeners() {
		// Guardar idioma de respaldo al cambiar
		comboIdiomaRespaldo.addActionListener(e -> {
			String seleccionado = (String) comboIdiomaRespaldo.getSelectedItem();
			String codigo = Idioma.codigoDesdeNombreVisible(seleccionado);

			if (codigo != null) {
				escribirIdiomaRespaldo(codigo);
			}
		});

		// Guardar configuración de Buscardor
		checkBuscardor.addActionListener(e -> {
			escribirBuscardorHablicar(checkBuscardor.isSelected());
		});

		// Guardar nombre de la herramienta
		campoNombreHerramienta.addActionListener(e -> {
			escribirNombreCD(campoNombreHerramienta.getText());
		});

		// Guardar configuración de condenar piratería
		checkCondenarPirata.addActionListener(e -> {
			escribirCondenarPirata(checkCondenarPirata.isSelected());
			botonDerechosMaranda.setVisible(checkCondenarPirata.isSelected());
		});

		// Guardar configuración para suprimir consola CD
		checkSuprimirConsolaCD.addActionListener(e -> {
			escribirSuprimirConsolaCD(checkSuprimirConsolaCD.isSelected());
		});

		// Guardar configuración para suprimir verificación de stacktrazos
		checkSuprimirVDST.addActionListener(e -> {
			escribirSuprimirVerificacionDeStacktrazos(checkSuprimirVDST.isSelected());
		});

		botonLanzadoresRecomendados.addActionListener(e -> {
			abrirLanzeresBuenos();
		});

		botonLanzadoresDesaconsejados.addActionListener(e -> {
			abrirLanzeresMalos();
		});

		botonModsRecomendados.addActionListener(e -> {
			abrirModsBuenas();
		});

		botonModsDesaconsejados.addActionListener(e -> {
			abrirModsMalas();
		});

		botonAntiTamper.addActionListener(e -> {
			abrirAntiManipulacion();
		});

		botonFirmas.addActionListener(e -> {
			abrirEditorCodice();
		});

		botonVerificaciones.addActionListener(e -> {
			abrirVerificaciones();
		});

		botonDerechosMaranda.addActionListener(e -> {
			abrirMiranda();
		});

		// Guardar configuración del selector de aplicación principal
		checkMostrarSelectorAplicacionPrincipal.addActionListener(e -> {
			escribirMostrarSelectorAplicacionPrincipal(checkMostrarSelectorAplicacionPrincipal.isSelected());
		});

		// Guardar configuración del botón CDLauncher principal
		checkMostrarBotonCDLauncherPrincipal.addActionListener(e -> {
			escribirMostrarBotonCDLauncherPrincipal(checkMostrarBotonCDLauncherPrincipal.isSelected());
		});

		// Guardar configuración del botón CDMods principal
		checkMostrarBotonCDModsPrincipal.addActionListener(e -> {
			escribirMostrarBotonCDModsPrincipal(checkMostrarBotonCDModsPrincipal.isSelected());
		});

		// Guardar configuración del botón IA principal
		checkMostrarBotonIAPrincipal.addActionListener(e -> {
			escribirMostrarBotonIAPrincipal(checkMostrarBotonIAPrincipal.isSelected());
		});

	}

	// Métodos auxiliares para estilización
	private void estilizarBoton(JButton btn) {
		estilizarBoton(btn, 10);
	}

	private void estilizarBoton(JButton btn, int paddingV) {
		if (!CrashDetectorGUI.esMac()) {
			btn.setBackground(colorBoton.obtener());
			btn.setForeground(Color.WHITE);
			btn.setFocusPainted(false);
			btn.setBorder(BorderFactory.createEmptyBorder(paddingV, 18, paddingV, 18));
		} else {
			btn.setContentAreaFilled(false);
		}
		btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	}

	private void estilizarCampo(JTextField campo) {
		campo.setBackground(colorCajaTexto.obtener());
		campo.setForeground(Color.WHITE);
		campo.setBorder(BorderFactory.createLineBorder(colorBoton.obtener().darker(), 1));
		campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	}

	private java.util.LinkedHashMap<String, String> getMapaIdiomas() {
		return Idioma.mapaParaComboBoxIdiomas();
	}
}