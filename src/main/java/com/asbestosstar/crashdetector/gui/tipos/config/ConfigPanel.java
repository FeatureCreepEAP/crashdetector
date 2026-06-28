package com.asbestosstar.crashdetector.gui.tipos.config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.dto.modpack.CopiaDeSeguridadDeArchivos;
import com.asbestosstar.crashdetector.dto.modpack.curseforge.ProveedorModsCurseForge;
import com.asbestosstar.crashdetector.dto.modpack.minecraftstorage.ProveedorModsMinecraftStorage;
import com.asbestosstar.crashdetector.dto.modpack.tlmods.ProveedorModsTlmods;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.editor_plantilla.EditorPlantilla;
import com.asbestosstar.crashdetector.gui.tipos.editor_plantilla.EditorPlantillaPredeterminado;
import com.asbestosstar.crashdetector.gui.tipos.editorgui.CDSkinCape;
import com.asbestosstar.crashdetector.gui.tipos.editorgui.EditorGUI;
import com.asbestosstar.crashdetector.parches.ConfigDeParches;
import com.asbestosstar.crashdetector.parches.Parche;

public abstract class ConfigPanel<PrincipalGUI> extends JPanel implements CrashDetectorGUI {
	public PrincipalGUI cdgui;
	public JTabbedPane tabbedPane;

	public static Map<String, Supplier<ConfigPanel>> GUIS = new HashMap<>();

	// Colores configurables usando ConfigColor
	public ConfigColor colorFondo;
	public ConfigColor colorTexto;
	public ConfigColor colorBoton;
	public ConfigColor colorCajaTexto;

	// Color de fondo calculado para las pestañas (no Mac)
	public Color colorFondoPestanias;

	public void constructir(PrincipalGUI principal_gui) {
		this.cdgui = principal_gui;
		CrashDetectorLogger.log("configpanel const");
		setLayout(new BorderLayout());

		// Inicializar colores de configuración
		inicializarColores();

		// Crear el contenedor de pestañas
		tabbedPane = new JTabbedPane();

		// Si NO es macOS, oscurecer el fondo de las pestañas para mejorar el contraste
		if (!CrashDetectorGUI.esMac()) {
			colorFondoPestanias = colorFondo.obtener().darker().darker();
			tabbedPane.setBackground(colorFondoPestanias);
			tabbedPane.setOpaque(true);
		}

		// Configurar las pestañas
		configurarPestanas();

		add(tabbedPane, BorderLayout.CENTER);
		configurarBotonGuardar();
	}

	public void inicializarColores() {
		colorFondo = ConfigColor.de("config.color.fondo",
				Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
		colorTexto = ConfigColor.de("config.color.texto",
				Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		colorBoton = ConfigColor.de("config.color.boton",
				Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
		colorCajaTexto = ConfigColor.de("config.color.cajaTexto",
				Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
	}

	public void configurarPestanas() {
		Color colorTextoPestanias = CrashDetectorGUI.esMac() ? null : colorTexto.obtener();

		// Pestaña "Inicio de Juego/App"
		JLabel incicio_del_juego = new JLabel(MonitorDePID.idioma.inicioApp());
		if (colorTextoPestanias != null)
			incicio_del_juego.setForeground(colorTextoPestanias);
		tabbedPane.addTab("", null, tabDelJuego(), MonitorDePID.idioma.tooltip());
		tabbedPane.setTabComponentAt(0, crearComponentePestania(incicio_del_juego));

		// Pestaña "Ajustes CrashDetector"
		JLabel cdajustes = new JLabel(MonitorDePID.idioma.ajustesCrashDetector());
		if (colorTextoPestanias != null)
			cdajustes.setForeground(colorTextoPestanias);
		tabbedPane.addTab("", null, tabCrashDetector(), MonitorDePID.idioma.tooltip());
		tabbedPane.setTabComponentAt(1, crearComponentePestania(cdajustes));

		// Pestaña "Confidencialidad"
		JLabel confidencialidad = new JLabel(MonitorDePID.idioma.confidencialidad());
		if (colorTextoPestanias != null)
			confidencialidad.setForeground(colorTextoPestanias);
		tabbedPane.addTab("", null, tabConfidentialidad(), MonitorDePID.idioma.tooltip());
		tabbedPane.setTabComponentAt(2, crearComponentePestania(confidencialidad));
	}

	public void configurarBotonGuardar() {
		JButton guardarButon = new JButton(MonitorDePID.idioma.guardarYCerrar());

		if (CrashDetectorGUI.esMac()) {
			guardarButon.setContentAreaFilled(false);
		} else {
			guardarButon.setForeground(colorTexto.obtener());
			guardarButon.setBackground(colorBoton.obtener());
			guardarButon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			guardarButon.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		}
		guardarButon.setFocusPainted(false);

		guardarButon.addActionListener(e -> {
			((com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI) cdgui).volver();
		});

		// Panel para el botón
		JPanel butonPanel = new JPanel(new BorderLayout());
		if (!CrashDetectorGUI.esMac()) {
			butonPanel.setBackground(colorFondo.obtener());
		} else {
			butonPanel.setOpaque(false);
		}

		butonPanel.add(guardarButon, BorderLayout.CENTER);
		add(butonPanel, BorderLayout.SOUTH);
	}

	public Component crearComponentePestania(JLabel etiqueta) {
		if (CrashDetectorGUI.esMac()) {
			return etiqueta;
		}

		JPanel envoltura = new JPanel(new BorderLayout());
		envoltura.setOpaque(true);
		envoltura.setBackground(colorFondoPestanias != null ? colorFondoPestanias : colorFondo.obtener().darker());

		etiqueta.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
		etiqueta.setOpaque(false);

		envoltura.add(etiqueta, BorderLayout.CENTER);
		return envoltura;
	}

	public JPanel tabDelJuego() {
		JPanel panel = new JPanel();
		panel.setBackground(colorFondo.obtener());
		panel.setLayout(new GridLayout(0, 1, 5, 5));

		CrashDetectorLogger.log("Tab Del Juego ");

		for (Parche<?> parche : Parche.parches) {
			CrashDetectorLogger.log("Parche " + parche.nombre_de_gui());
			JPanel parchePanel = new JPanel(new BorderLayout());
			parchePanel.setBackground(colorFondo.obtener());

			JCheckBox checkBox = new JCheckBox();
			checkBox.setBackground(colorFondo.obtener());
			checkBox.setForeground(colorTexto.obtener());
			checkBox.setSelected(ConfigDeParches.obtenerInstancia().estaActivo(parche.id()));

			checkBox.addActionListener(e -> {
				ConfigDeParches.obtenerInstancia().establecerActivo(parche.id(), checkBox.isSelected());
			});

			JLabel label = new JLabel(parche.nombre_de_gui());
			label.setForeground(colorTexto.obtener());
			label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

			parchePanel.add(checkBox, BorderLayout.WEST);
			parchePanel.add(label, BorderLayout.CENTER);

			panel.add(parchePanel);
		}

		return panel;
	}

	public static JTextField crearCampoTextoConfig(String valorInicial, java.util.function.Consumer<String> onChange) {
		JTextField field = new JTextField(valorInicial);
		field.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				onChange.accept(field.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				onChange.accept(field.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				onChange.accept(field.getText());
			}
		});
		return field;
	}

	public JPanel tabCrashDetector() {
		JPanel panel = new JPanel();
		panel.setBackground(colorFondo.obtener());
		panel.setLayout(new GridLayout(0, 2, 5, 5));

		Config config = Config.obtenerInstancia();
		ConfigMundial munidial = ConfigMundial.obtenerInstancia();

		boolean esMac = CrashDetectorGUI.esMac();
		Color colorDeTextoDeGui = colorTexto.obtener();

		// ===== Consola de desarrollo (Munidial / Ningun) =====
		JLabel labelDev = new JLabel(MonitorDePID.idioma.consolaDesarrollo());
		labelDev.setForeground(colorDeTextoDeGui);
		panel.add(labelDev);

		JComboBox<String> comboDev = new JComboBox<>(
				new String[] { MonitorDePID.idioma.mundial(), MonitorDePID.idioma.ningun() });

		comboDev.setSelectedItem(
				munidial.obtenerConsolaDesarrollo() ? MonitorDePID.idioma.mundial() : MonitorDePID.idioma.ningun());

		comboDev.addActionListener(e -> {
			boolean valor = comboDev.getSelectedItem().equals(MonitorDePID.idioma.mundial());
			munidial.guardarConsolaDesarrollo(valor);

			if (valor) {
				// abrir consola inmediatamente
				MonitorDePID.abrirConsola();
			}
		});

		if (!esMac) {
			comboDev.setBackground(colorCajaTexto.obtener());
			comboDev.setForeground(colorDeTextoDeGui);
		}

		panel.add(comboDev);

		// Campo: Look&Feel
		JLabel labelLookAndFeel = new JLabel("Look&Feel");
		labelLookAndFeel.setForeground(colorDeTextoDeGui);
		panel.add(labelLookAndFeel);

		ConfigString lookAndFeel = ConfigString.de("lf", "javax.swing.plaf.metal.MetalLookAndFeel");

		JTextField lookAndFeelField = crearCampoTextoConfig(lookAndFeel.obtener(), lookAndFeel::escribir);

		if (!esMac) {
			lookAndFeelField.setBackground(colorCajaTexto.obtener());
			lookAndFeelField.setForeground(colorDeTextoDeGui);
		}

		panel.add(lookAndFeelField);

		// Campo: Sitio de informes
		JLabel labelSitio = new JLabel(MonitorDePID.idioma.endpointDeInforme());
		labelSitio.setForeground(colorDeTextoDeGui);
		panel.add(labelSitio);

		JTextField sitioDeInformesField = crearCampoTextoConfig(config.obtenerSitoDeInformes(),
				config::guardarSitioDeInformes);
		if (!esMac) {
			sitioDeInformesField.setBackground(colorCajaTexto.obtener());
			sitioDeInformesField.setForeground(colorDeTextoDeGui);
		}
		panel.add(sitioDeInformesField);

		// Campo: Clave API mundial de CurseForge
		JLabel labelCurseForgeClaveApiMundial = new JLabel(MonitorDePID.idioma.curseForgeClaveApiMundial());
		labelCurseForgeClaveApiMundial.setForeground(colorDeTextoDeGui);
		panel.add(labelCurseForgeClaveApiMundial);

		JTextField curseForgeClaveApiMundialField = crearCampoTextoConfig(munidial.obtenerCurseForgeClaveApi(),
				munidial::guardarCurseForgeClaveApi);

		if (!esMac) {
			curseForgeClaveApiMundialField.setBackground(colorCajaTexto.obtener());
			curseForgeClaveApiMundialField.setForeground(colorDeTextoDeGui);
		}

		panel.add(curseForgeClaveApiMundialField);

		// Campo: Endpoint de CurseForge
		JLabel labelCurseForgeEndpoint = new JLabel(MonitorDePID.idioma.curseForgeEndpoint());
		labelCurseForgeEndpoint.setForeground(colorDeTextoDeGui);
		panel.add(labelCurseForgeEndpoint);

		ConfigString curseForgeEndpoint = ConfigString.de("curseforge.endpoint",
				ProveedorModsCurseForge.obtenerEndpointPredeterminada());

		JTextField curseForgeEndpointField = crearCampoTextoConfig(curseForgeEndpoint.obtener(),
				curseForgeEndpoint::escribir);

		if (!esMac) {
			curseForgeEndpointField.setBackground(colorCajaTexto.obtener());
			curseForgeEndpointField.setForeground(colorDeTextoDeGui);
		}

		panel.add(curseForgeEndpointField);

		// Campo: Endpoint de TLMods
		JLabel labelTlmodsEndpoint = new JLabel(MonitorDePID.idioma.tlmodsEndpoint());
		labelTlmodsEndpoint.setForeground(colorDeTextoDeGui);
		panel.add(labelTlmodsEndpoint);

		JTextField tlmodsEndpointField = crearCampoTextoConfig(ProveedorModsTlmods.ENDPOINT.obtener(),
				ProveedorModsTlmods.ENDPOINT::escribir);

		if (!esMac) {
			tlmodsEndpointField.setBackground(colorCajaTexto.obtener());
			tlmodsEndpointField.setForeground(colorDeTextoDeGui);
		}

		panel.add(tlmodsEndpointField);

		// Campo: Endpoint de MinecraftStorage
		JLabel labelMinecraftStorageEndpoint = new JLabel(MonitorDePID.idioma.minecraftStorageEndpoint());
		labelMinecraftStorageEndpoint.setForeground(colorDeTextoDeGui);
		panel.add(labelMinecraftStorageEndpoint);

		JTextField minecraftStorageEndpointField = crearCampoTextoConfig(
				ProveedorModsMinecraftStorage.ENDPOINT.obtener(), ProveedorModsMinecraftStorage.ENDPOINT::escribir);

		if (!esMac) {
			minecraftStorageEndpointField.setBackground(colorCajaTexto.obtener());
			minecraftStorageEndpointField.setForeground(colorDeTextoDeGui);
		}

		panel.add(minecraftStorageEndpointField);

		// Campo: Carpeta HMCL
		JLabel labelHMCL = new JLabel(MonitorDePID.idioma.carpetaHMCL());
		labelHMCL.setForeground(colorDeTextoDeGui);
		panel.add(labelHMCL);

		JTextField carpetaHMCL = crearCampoTextoConfig(config.obtenerCarpetaHMCL(), config::guardarCarpetaHMCL);
		if (!esMac) {
			carpetaHMCL.setBackground(colorCajaTexto.obtener());
			carpetaHMCL.setForeground(colorDeTextoDeGui);
		}
		panel.add(carpetaHMCL);

		// Editor HTML
		JLabel labelEditarColores = new JLabel("HTML WYSIWYG PLANTILLA:");
		labelEditarColores.setForeground(colorDeTextoDeGui);
		panel.add(labelEditarColores);

		JButton botonEditarColores = new JButton("HTML WYSIWYG PLANTILLA");
		if (!esMac) {
			botonEditarColores.setForeground(colorTexto.obtener());
			botonEditarColores.setBackground(colorBoton.obtener());
			botonEditarColores.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		}
		botonEditarColores.setFocusPainted(false);
		botonEditarColores.addActionListener(e -> {
			JDialog dialogo = new JDialog(SwingUtilities.getWindowAncestor(this), "HTML WYSIWYG PLANTILLA");
			dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			EditorPlantilla editor = TipoGUI.EDITOR_PLANTILLA.obtenerGUIPredeterminado(EditorPlantillaPredeterminado.ID,
					() -> new EditorPlantillaPredeterminado());
			editor.init();
			dialogo.setContentPane(editor);
			dialogo.pack();
			dialogo.setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
			dialogo.setVisible(true);
		});
		panel.add(botonEditarColores);

		// Editor GUIs
		JLabel labelEditarGUI = new JLabel("Editar GUIs:");
		labelEditarGUI.setForeground(colorDeTextoDeGui);
		panel.add(labelEditarGUI);

		JButton botonEditarGUIs = new JButton("Editar GUIs");
		if (!esMac) {
			botonEditarGUIs.setForeground(colorTexto.obtener());
			botonEditarGUIs.setBackground(colorBoton.obtener());
			botonEditarGUIs.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		}
		botonEditarGUIs.setFocusPainted(false);
		botonEditarGUIs.addActionListener(e -> {
			EditorGUI editor_gui = TipoGUI.EDITOR_GUI.obtenerGUIPredeterminado(CDSkinCape.ID, () -> new CDSkinCape());
			editor_gui.init();
		});
		panel.add(botonEditarGUIs);

		// Dropdown GUIs por defecto
		JLabel labelGuiDefault = new JLabel("GUIs por defecto:");
		labelGuiDefault.setForeground(colorDeTextoDeGui);
		panel.add(labelGuiDefault);
		panel.add(new JLabel());

		for (TipoGUI<?> tipo : TipoGUI.TIPOS_DE_GUI) {
			Map<String, ?> guis = tipo.obtenerGUIs();
			if (guis.size() > 1) {
				JLabel label = new JLabel(tipo.etiquetaDelBoton() + ":");
				label.setForeground(colorDeTextoDeGui);
				panel.add(label);

				JComboBox<String> dropdown = new JComboBox<>(guis.keySet().toArray(new String[0]));
				String valorActual = ConfigString.de("guitipo_" + tipo.id(), "ningun").obtener();
				dropdown.setSelectedItem(valorActual);

				dropdown.addActionListener(e -> {
					String seleccionado = (String) dropdown.getSelectedItem();
					ConfigString.de("guitipo_" + tipo.id(), "ningun").escribir(seleccionado);
				});

				if (!esMac) {
					dropdown.setBackground(colorCajaTexto.obtener());
					dropdown.setForeground(colorDeTextoDeGui);
				}

				panel.add(dropdown);
			}
		}

		// Reemplazado por Análisis en vivo (Live Streaming)
		JLabel labelLiveStream = new JLabel("Análisis en vivo (Live Streaming)");
		labelLiveStream.setForeground(colorDeTextoDeGui);
		panel.add(labelLiveStream);

		JCheckBox liveStreamCheckBox = new JCheckBox();
		liveStreamCheckBox.setBackground(colorFondo.obtener());
		liveStreamCheckBox.setSelected(MonitorDePID.ANALISIS_EN_VIVO.obtener());
		liveStreamCheckBox
				.addItemListener(e -> MonitorDePID.ANALISIS_EN_VIVO.escribir(liveStreamCheckBox.isSelected()));
		panel.add(liveStreamCheckBox);

		// Auto-backup: activado
		JLabel labelAutoBackupActivado = new JLabel(MonitorDePID.idioma.autoBackupActivado());
		labelAutoBackupActivado.setForeground(colorDeTextoDeGui);
		panel.add(labelAutoBackupActivado);

		JCheckBox checkAutoBackupActivado = new JCheckBox();
		checkAutoBackupActivado.setBackground(colorFondo.obtener());
		checkAutoBackupActivado.setSelected(CopiaDeSeguridadDeArchivos.autoBackupActivado.obtener());
		checkAutoBackupActivado.addActionListener(e -> {
			CopiaDeSeguridadDeArchivos.autoBackupActivado.escribir(checkAutoBackupActivado.isSelected());
		});
		panel.add(checkAutoBackupActivado);

		// Auto-backup: frecuencia
		JLabel labelAutoBackupFrecuencia = new JLabel(MonitorDePID.idioma.autoBackupFrecuencia());
		labelAutoBackupFrecuencia.setForeground(colorDeTextoDeGui);
		panel.add(labelAutoBackupFrecuencia);

		JComboBox<String> comboAutoBackupFrecuencia = new JComboBox<>(
				new String[] { CopiaDeSeguridadDeArchivos.FRECUENCIA_UNA_VEZ_POR_DIA,
						CopiaDeSeguridadDeArchivos.FRECUENCIA_TODAS_LAS_VECES });

		comboAutoBackupFrecuencia.setSelectedItem(CopiaDeSeguridadDeArchivos.autoBackupFrecuencia.obtener());

		comboAutoBackupFrecuencia.addActionListener(e -> {
			String seleccionado = (String) comboAutoBackupFrecuencia.getSelectedItem();
			CopiaDeSeguridadDeArchivos.autoBackupFrecuencia.escribir(seleccionado);
		});

		if (!esMac) {
			comboAutoBackupFrecuencia.setBackground(colorCajaTexto.obtener());
			comboAutoBackupFrecuencia.setForeground(colorDeTextoDeGui);
		}

		panel.add(comboAutoBackupFrecuencia);

		// Auto-backup: días conservar
		JLabel labelAutoBackupDiasConservar = new JLabel(MonitorDePID.idioma.autoBackupDiasConservar());
		labelAutoBackupDiasConservar.setForeground(colorDeTextoDeGui);
		panel.add(labelAutoBackupDiasConservar);

		JTextField autoBackupDiasConservarField = crearCampoTextoConfig(
				String.valueOf(CopiaDeSeguridadDeArchivos.autoBackupDiasConservar.obtener()), valor -> {
					try {
						CopiaDeSeguridadDeArchivos.autoBackupDiasConservar.escribir(Double.parseDouble(valor.trim()));
					} catch (Exception ex) {
						// Ignorar mientras el usuario escribe un valor incompleto o inválido.
					}
				});

		if (!esMac) {
			autoBackupDiasConservarField.setBackground(colorCajaTexto.obtener());
			autoBackupDiasConservarField.setForeground(colorDeTextoDeGui);
		}

		panel.add(autoBackupDiasConservarField);

		// Auto-backup: tamaño máximo MB
		JLabel labelAutoBackupTamanoMaximoMB = new JLabel(MonitorDePID.idioma.autoBackupTamanoMaximoMB());
		labelAutoBackupTamanoMaximoMB.setForeground(colorDeTextoDeGui);
		panel.add(labelAutoBackupTamanoMaximoMB);

		JTextField autoBackupTamanoMaximoMBField = crearCampoTextoConfig(
				String.valueOf(CopiaDeSeguridadDeArchivos.autoBackupTamanoMaximoMB.obtener()), valor -> {
					try {
						CopiaDeSeguridadDeArchivos.autoBackupTamanoMaximoMB.escribir(Double.parseDouble(valor.trim()));
					} catch (Exception ex) {
						// Ignorar mientras el usuario escribe un valor incompleto o inválido.
					}
				});

		if (!esMac) {
			autoBackupTamanoMaximoMBField.setBackground(colorCajaTexto.obtener());
			autoBackupTamanoMaximoMBField.setForeground(colorDeTextoDeGui);
		}

		panel.add(autoBackupTamanoMaximoMBField);

		panel.setPreferredSize(new Dimension(600, 400));
		return panel;
	}

	public JPanel tabConfidentialidad() {

		ConfigMundial munidial = ConfigMundial.obtenerInstancia();

		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBackground(colorFondo.obtener());

		JEditorPane areaTexto = new JEditorPane();
		areaTexto.setContentType("text/html");
		areaTexto.setText(MonitorDePID.idioma.arco());
		areaTexto.setEditable(false);
		areaTexto.setOpaque(true);
		areaTexto.setBackground(colorFondo.obtener());
		areaTexto.setForeground(colorTexto.obtener());

		// Asegurar que respete el color del texto en HTML
		areaTexto.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);

		// Fuente similar al resto del TL
		areaTexto.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

		// Padding interno
		areaTexto.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JScrollPane scroll = new JScrollPane(areaTexto);
		scroll.setPreferredSize(new Dimension(10, 180));
		panel.add(scroll, BorderLayout.NORTH);

		JPanel panelOpciones = new JPanel(new GridLayout(0, 2, 8, 8));
		panelOpciones.setBackground(colorFondo.obtener());
		panelOpciones.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.opcionesMunidiales()));

		// Anonimizar
		JLabel labelAnon = new JLabel(MonitorDePID.idioma.anonimizarRegistros());
		labelAnon.setForeground(colorTexto.obtener());
		panelOpciones.add(labelAnon);

		JCheckBox checkAnon = new JCheckBox();
		checkAnon.setBackground(colorFondo.obtener());
		checkAnon.setSelected(munidial.esAnonimizarRegistros());
		checkAnon.addActionListener(e -> munidial.guardarAnonimizarRegistros(checkAnon.isSelected()));
		panelOpciones.add(checkAnon);

		// Consentimiento LFPDPPP
		JLabel labelConsent = new JLabel(MonitorDePID.idioma.consentimientoLFPDPPP());
		labelConsent.setForeground(colorTexto.obtener());
		panelOpciones.add(labelConsent);

		JCheckBox checkConsent = new JCheckBox();
		checkConsent.setBackground(colorFondo.obtener());
		checkConsent.setSelected(munidial.obtenerConsentimientoLFPDPPP());
		checkConsent.addActionListener(e -> munidial.guardarConsentimientoLFPDPPP(checkConsent.isSelected()));
		panelOpciones.add(checkConsent);

		// Token de acceso
		JLabel labelToken = new JLabel(MonitorDePID.idioma.habilitarTokenAccesoEnEntregar());
		labelToken.setForeground(colorTexto.obtener());
		panelOpciones.add(labelToken);

		JCheckBox checkToken = new JCheckBox();
		checkToken.setBackground(colorFondo.obtener());
		checkToken.setSelected(munidial.obtenerHabilitarTokenDeAccesoEnLaEntregaDelMonitorDePID());
		checkToken.addActionListener(
				e -> munidial.guardarHabilitarTokenDeAccesoEnLaEntregaDelMonitorDePID(checkToken.isSelected()));
		panelOpciones.add(checkToken);

		panel.add(panelOpciones, BorderLayout.CENTER);

		JPanel placeholderImagen = new JPanel();
		placeholderImagen.setBackground(colorFondo.obtener());

		JLabel etiquetaImagen = new JLabel();
		etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);

		try {
			ImageIcon iconoImagen = new ImageIcon(Statics.carpeta.resolve("imagenes/profeco.jpg").toString());
			etiquetaImagen.setIcon(iconoImagen);
		} catch (Exception e) {
			etiquetaImagen.setText(MonitorDePID.idioma.errorCargandoImagen());
			etiquetaImagen.setForeground(colorTexto.obtener());
		}

		placeholderImagen.add(etiquetaImagen);
		panel.add(placeholderImagen, BorderLayout.SOUTH);

		return panel;
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.CONFIG_PANEL;
	}

	@Override
	public void init() {
		// usar constructir
	}

}