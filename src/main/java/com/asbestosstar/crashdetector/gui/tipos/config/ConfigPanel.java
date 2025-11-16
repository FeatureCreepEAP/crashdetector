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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ConfigString;
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
		boolean esMac = CrashDetectorGUI.esMac();
		Color colorDeTextoDeGui = colorTexto.obtener();

		// Campo: Sitio de informes
		JLabel labelSitio = new JLabel(MonitorDePID.idioma.sitoDeLogging());
		labelSitio.setForeground(colorDeTextoDeGui);
		panel.add(labelSitio);
		JTextField sitioDeInformesField = crearCampoTextoConfig(config.obtenerSitoDeInformes(),
				config::guardarSitioDeInformes);
		if (!esMac) {
			sitioDeInformesField.setBackground(colorCajaTexto.obtener());
			sitioDeInformesField.setForeground(colorDeTextoDeGui);
		}
		panel.add(sitioDeInformesField);

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

		JLabel labelEditarColores = new JLabel("HTML WYSIWYG PLANTILLA:");
		labelEditarColores.setForeground(colorDeTextoDeGui);
		panel.add(labelEditarColores);

		JButton botonEditarColores = new JButton("HTML WYSIWYG PLANTILLA");
		if (!esMac) {
			botonEditarColores.setForeground(colorTexto.obtener());
			botonEditarColores.setBackground(colorBoton.obtener());
			botonEditarColores.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			botonEditarColores.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		}
		botonEditarColores.setFocusPainted(false);
		botonEditarColores.addActionListener(e -> {
			JDialog dialogo = new JDialog(SwingUtilities.getWindowAncestor(this), "HTML WYSIWYG PLANTILLA");
			dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			EditorPlantilla editor_plantilla = TipoGUI.EDITOR_PLANTILLA.obtenerGUIPredeterminado(
					EditorPlantillaPredeterminado.ID, () -> new EditorPlantillaPredeterminado());
			editor_plantilla.init();
			dialogo.setContentPane(editor_plantilla);
			dialogo.pack();
			dialogo.setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
			dialogo.setVisible(true);
		});
		panel.add(botonEditarColores);

		JLabel labelEditarGUI = new JLabel("Editar GUIs:");
		labelEditarGUI.setForeground(colorDeTextoDeGui);
		panel.add(labelEditarGUI);

		JButton botonEditarGUIs = new JButton("Editar GUIs");
		if (!esMac) {
			botonEditarGUIs.setForeground(colorTexto.obtener());
			botonEditarGUIs.setBackground(colorBoton.obtener());
			botonEditarGUIs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			botonEditarGUIs.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		}
		botonEditarGUIs.setFocusPainted(false);
		botonEditarGUIs.addActionListener(e -> {
			EditorGUI editor_gui = TipoGUI.EDITOR_GUI.obtenerGUIPredeterminado(CDSkinCape.ID, () -> new CDSkinCape());
			editor_gui.init();
		});
		panel.add(botonEditarGUIs);

		// MENÚS DESPLEGABLES PARA SELECCIONAR GUIs POR DEFECTO
		JLabel labelGuiDefault = new JLabel("GUIs por defecto:");
		labelGuiDefault.setForeground(colorDeTextoDeGui);
		panel.add(labelGuiDefault);

		// Crear un espacio vacío para alinear los elementos
		panel.add(new JLabel());

		// Iterar sobre todos los tipos de GUI configurables
		for (TipoGUI<?> tipo : TipoGUI.TIPOS_DE_GUI) {
			Map<String, ?> guis = tipo.obtenerGUIs();
			CrashDetectorLogger.log("tipo " + tipo.id() + " tiene " + guis.size() + " implementaciones");

			// Solo agregar dropdown si hay múltiples implementaciones
			if (guis.size() > 1) {
				JLabel label = new JLabel(tipo.etiquetaDelBoton() + ":");
				label.setForeground(colorDeTextoDeGui);
				panel.add(label);

				JComboBox<String> dropdown = new JComboBox<>(guis.keySet().toArray(new String[0]));

				String valorActual = ConfigString.de("guitipo_" + tipo.id(), "ningun").obtener();
				dropdown.setSelectedItem(valorActual);
				CrashDetectorLogger.log("tipo " + tipo.id() + " valor actual: " + valorActual);

				dropdown.addActionListener(e -> {
					String seleccionado = (String) dropdown.getSelectedItem();
					ConfigString.de("guitipo_" + tipo.id(), "ningun").escribir(seleccionado);
				});

				if (!esMac) {
					dropdown.setBackground(colorCajaTexto.obtener());
					dropdown.setForeground(colorDeTextoDeGui);
					dropdown.setOpaque(true);
				}

				panel.add(dropdown);
			} else {
				CrashDetectorLogger.log("No se muestra dropdown para " + tipo.id() + " porque solo tiene " + guis.size()
						+ " implementaciones");
			}
		}

		// Campo: ¿Usar proxy para System.out y System.err?
		JLabel labelProxy = new JLabel("proxySysOutSysErr");
		labelProxy.setForeground(colorDeTextoDeGui);
		panel.add(labelProxy);
		JCheckBox proxySysOutSysErrCheckBox = new JCheckBox();
		proxySysOutSysErrCheckBox.setBackground(colorFondo.obtener());
		proxySysOutSysErrCheckBox.setForeground(colorDeTextoDeGui);
		proxySysOutSysErrCheckBox.setSelected(config.obtenerProxySysOutSysErr());
		proxySysOutSysErrCheckBox
				.addItemListener(e -> config.guardarProxySysOutSysErr(proxySysOutSysErrCheckBox.isSelected()));
		panel.add(proxySysOutSysErrCheckBox);

		// Asegurar que el panel tenga suficiente tamaño
		panel.setPreferredSize(new Dimension(600, 400));

		return panel;
	}

	public JPanel tabConfidentialidad() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(colorFondo.obtener());

		JTextArea areaTexto = new JTextArea(MonitorDePID.idioma.arco());
		areaTexto.setLineWrap(true);
		areaTexto.setWrapStyleWord(true);
		areaTexto.setEditable(false);
		areaTexto.setOpaque(false);
		areaTexto.setBackground(colorFondo.obtener());
		areaTexto.setForeground(colorTexto.obtener());

		if (!CrashDetectorGUI.esMac()) {
			areaTexto.setForeground(colorTexto.obtener());
		}

		JPanel panelTexto = new JPanel(new BorderLayout());
		panelTexto.setBackground(colorFondo.obtener());
		panelTexto.add(areaTexto, BorderLayout.CENTER);

		panelTexto.setMaximumSize(new Dimension(600, Short.MAX_VALUE));
		panelTexto.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(panelTexto, BorderLayout.CENTER);

		JPanel placeholderImagen = new JPanel();
		placeholderImagen.setBackground(colorFondo.obtener());
		placeholderImagen.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		JLabel etiquetaImagen = new JLabel();
		etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);

		try {
			ImageIcon iconoImagen = new ImageIcon(Statics.carpeta.resolve("imagenes/profeco.jpg").toString());
			etiquetaImagen.setIcon(iconoImagen);
		} catch (Exception e) {
			etiquetaImagen.setText("Error al cargar la imagen");
			if (!CrashDetectorGUI.esMac()) {
				etiquetaImagen.setForeground(colorTexto.obtener());
			}
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