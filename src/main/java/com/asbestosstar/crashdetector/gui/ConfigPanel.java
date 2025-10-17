package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI;
import com.asbestosstar.crashdetector.parches.ConfigDeParches;
import com.asbestosstar.crashdetector.parches.Parche;

public class ConfigPanel<PrincipalGUI> extends JPanel {
	public PrincipalGUI cdgui;
	private JTabbedPane tabbedPane;

	// Color de fondo calculado para las pestañas (no Mac)
	private Color colorFondoPestanias;

	public ConfigPanel(PrincipalGUI cdgui) {
		this.cdgui = cdgui;
		setLayout(new BorderLayout());
		setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));

		// Crear el contenedor de pestañas
		tabbedPane = new JTabbedPane();

		// Si NO es macOS, oscurecer el fondo de las pestañas para mejorar el contraste
		if (!CrashDetectorGUI.esMac()) {
			// Dos niveles más oscuro suele dar buen contraste con texto claro
			colorFondoPestanias = Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()).darker().darker();
			tabbedPane.setBackground(colorFondoPestanias);
			tabbedPane.setOpaque(true);
		}

		// Color del texto de las etiquetas de las pestañas
		Color colorTextoPestanias = CrashDetectorGUI.esMac() ? null : Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto());

		// Pestaña "Inicio de Juego/App"
		JLabel incicio_del_juego = new JLabel(MonitorDePID.idioma.inicioApp());
		if (colorTextoPestanias != null)
			incicio_del_juego.setForeground(colorTextoPestanias);
		tabbedPane.addTab("", null, tabDelJuego(), MonitorDePID.idioma.tooltip());
		// Envolver la etiqueta para que pinte el fondo oscuro en el área de la pestaña
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

		add(tabbedPane, BorderLayout.CENTER);
		JButton guardarButon = new JButton(MonitorDePID.idioma.guardarYCerrar());

		if (CrashDetectorGUI.esMac()) {
			guardarButon.setContentAreaFilled(false);
		} else {
			guardarButon.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
			guardarButon.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
			guardarButon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			guardarButon.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		}
		guardarButon.setFocusPainted(false);

		// Ya no se leen fields; todo se guarda al vuelo en los listeners.
		guardarButon.addActionListener(e -> {
			// Volver a la ventana principal
			((com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI) cdgui).volver();
		});

		// Panel para el botón
		JPanel butonPanel = new JPanel(new BorderLayout());
		if (!CrashDetectorGUI.esMac()) {
			butonPanel.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
		} else {
			butonPanel.setOpaque(false); // Let macOS decide
		}

		butonPanel.add(guardarButon, BorderLayout.CENTER);

		// Añadir el botón al sur del panel principal
		add(butonPanel, BorderLayout.SOUTH);
	}

	// Helper: envuelve la etiqueta de la pestaña en un panel opaco para pintar el
	// fondo oscuro
	private Component crearComponentePestania(JLabel etiqueta) {
		// En macOS dejamos el comportamiento por defecto del LAF
		if (CrashDetectorGUI.esMac()) {
			return etiqueta;
		}

		JPanel envoltura = new JPanel(new BorderLayout());
		envoltura.setOpaque(true);
		// Usar el color calculado; si faltara por alguna razón, oscurecer el fondo base
		envoltura.setBackground(
				colorFondoPestanias != null ? colorFondoPestanias : Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()).darker());

		// Un poco de padding para mejorar legibilidad
		etiqueta.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
		etiqueta.setOpaque(false); // el fondo lo pinta la envoltura

		envoltura.add(etiqueta, BorderLayout.CENTER);
		return envoltura;
	}

	private JPanel tabDelJuego() {
		JPanel panel = new JPanel();
		panel.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
		panel.setLayout(new GridLayout(0, 1, 5, 5));

		CrashDetectorLogger.log("Tab Del Juego ");

		// Agregar todos los parches de la aplicación
		for (Parche<?> parche : Parche.parches) {
			CrashDetectorLogger.log("Parche " + parche.nombre_de_gui());
			JPanel parchePanel = new JPanel(new BorderLayout());
			parchePanel.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));

			// Crear checkbox para activar/desactivar el parche
			JCheckBox checkBox = new JCheckBox();
			checkBox.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
			checkBox.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
			checkBox.setSelected(ConfigDeParches.obtenerInstancia().estaActivo(parche.id()));

			// Actualizar estado cuando se hace clic
			checkBox.addActionListener(e -> {
				ConfigDeParches.obtenerInstancia().establecerActivo(parche.id(), checkBox.isSelected());
			});

			// Etiqueta con el nombre del parche
			JLabel label = new JLabel(parche.nombre_de_gui());
			label.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
			label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

			// Agregar componentes al panel
			parchePanel.add(checkBox, BorderLayout.WEST);
			parchePanel.add(label, BorderLayout.CENTER);

			// Añadir el panel del parche al panel principal
			panel.add(parchePanel);
		}

		return panel;
	}

	// === Helper para vincular un JTextField a un setter de Config con auto-guardado ===
	private static JTextField crearCampoTextoConfig(String valorInicial, java.util.function.Consumer<String> onChange) {
		JTextField field = new JTextField(valorInicial);
		field.getDocument().addDocumentListener(new DocumentListener() {
			@Override public void insertUpdate(DocumentEvent e) { onChange.accept(field.getText()); }
			@Override public void removeUpdate(DocumentEvent e) { onChange.accept(field.getText()); }
			@Override public void changedUpdate(DocumentEvent e) { onChange.accept(field.getText()); }
		});
		return field;
	}

	private JPanel tabCrashDetector() {
		JPanel panel = new JPanel();
		panel.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
		panel.setLayout(new GridLayout(0, 2, 5, 5));

		Config config = Config.obtenerInstancia();

		// Helper to reduce redundancy
		boolean isMac = CrashDetectorGUI.esMac();
		Color color_de_texto_de_gui = Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto());

		// Campo: Sitio de informes
		JLabel labelSitio = new JLabel(MonitorDePID.idioma.sitoDeLogging());
		labelSitio.setForeground(color_de_texto_de_gui);
		panel.add(labelSitio);
		JTextField sitioDeInformesField = crearCampoTextoConfig(
				config.obtenerSitoDeInformes(),
				config::guardarSitioDeInformes
		);
		if (!isMac) {
			sitioDeInformesField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			sitioDeInformesField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(sitioDeInformesField);

		// Campo: Sitio de informes
		JLabel labelHMCL = new JLabel(MonitorDePID.idioma.carpetaHMCL());
		labelHMCL.setForeground(color_de_texto_de_gui);
		panel.add(labelHMCL);
		JTextField carpetaHMCL = crearCampoTextoConfig(
				config.obtenerCarpetaHMCL(),
				config::guardarCarpetaHMCL
		);
		if (!isMac) {
			carpetaHMCL.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			carpetaHMCL.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(carpetaHMCL);

		// Campo: Color de fondo
		JLabel labelFondo = new JLabel(MonitorDePID.idioma.colorFondo());
		labelFondo.setForeground(color_de_texto_de_gui);
		panel.add(labelFondo);
		JTextField colorFondoField = crearCampoTextoConfig(
				config.obtenerColorFondo(),
				config::guardarColorFondo
		);
		if (!isMac) {
			colorFondoField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			colorFondoField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(colorFondoField);

		// Campo: Color de texto
		JLabel labelTexto = new JLabel(MonitorDePID.idioma.colorTexto());
		labelTexto.setForeground(color_de_texto_de_gui);
		panel.add(labelTexto);
		JTextField colorTextoField = crearCampoTextoConfig(
				config.obtenerColorTexto(),
				config::guardarColorTexto
		);
		if (!isMac) {
			colorTextoField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			colorTextoField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(colorTextoField);

		// Campo: Color de botón
		JLabel labelBoton = new JLabel(MonitorDePID.idioma.colorBoton());
		labelBoton.setForeground(color_de_texto_de_gui);
		panel.add(labelBoton);
		JTextField colorBotonField = crearCampoTextoConfig(
				config.obtenerColorBoton(),
				config::guardarColorBoton
		);
		if (!isMac) {
			colorBotonField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			colorBotonField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(colorBotonField);

		// Campo: Color de caja de texto
		JLabel labelCajaTexto = new JLabel(MonitorDePID.idioma.colorCajaTexto());
		labelCajaTexto.setForeground(color_de_texto_de_gui);
		panel.add(labelCajaTexto);
		JTextField colorCajaTextoField = crearCampoTextoConfig(
				config.obtenerColorCajaTexto(),
				config::guardarColorCajaTexto
		);
		if (!isMac) {
			colorCajaTextoField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			colorCajaTextoField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(colorCajaTextoField);

		// Campo: Color de enlace
		JLabel labelEnlace = new JLabel(MonitorDePID.idioma.colorEnlace());
		labelEnlace.setForeground(color_de_texto_de_gui);
		panel.add(labelEnlace);
		JTextField colorEnlaceField = crearCampoTextoConfig(
				config.obtenerColorEnlace(),
				config::guardarColorEnlace
		);
		if (!isMac) {
			colorEnlaceField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			colorEnlaceField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(colorEnlaceField);

		// Campo: Color de títulos de consolas
		JLabel labelTitulosConsolas = new JLabel(MonitorDePID.idioma.colorTitulosConsolas());
		labelTitulosConsolas.setForeground(color_de_texto_de_gui);
		panel.add(labelTitulosConsolas);
		JTextField colorTitulosConsolasField = crearCampoTextoConfig(
				config.obtenerColorDeTitulosDeConsolas(),
				config::guardarColorDeTitulosDeConsolas
		);
		if (!isMac) {
			colorTitulosConsolasField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			colorTitulosConsolasField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(colorTitulosConsolasField);

		// Campo: Color de error
		JLabel labelError = new JLabel(MonitorDePID.idioma.colorError());
		labelError.setForeground(color_de_texto_de_gui);
		panel.add(labelError);
		JTextField colorErrorField = crearCampoTextoConfig(
				config.obtenerColorError(),
				config::guardarColorError
		);
		if (!isMac) {
			colorErrorField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			colorErrorField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(colorErrorField);

		// Campo: Color de advertencia
		JLabel labelAdvertencia = new JLabel(MonitorDePID.idioma.colorAdvertencia());
		labelAdvertencia.setForeground(color_de_texto_de_gui);
		panel.add(labelAdvertencia);
		JTextField colorAdvertenciaField = crearCampoTextoConfig(
				config.obtenerColorAdvertencia(),
				config::guardarColorAdvertencia
		);
		if (!isMac) {
			colorAdvertenciaField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			colorAdvertenciaField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(colorAdvertenciaField);

		// Campo: Color de información
		JLabel labelInfo = new JLabel(MonitorDePID.idioma.colorInfo());
		labelInfo.setForeground(color_de_texto_de_gui);
		panel.add(labelInfo);
		JTextField colorInfoField = crearCampoTextoConfig(
				config.obtenerColorInfo(),
				config::guardarColorInfo
		);
		if (!isMac) {
			colorInfoField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			colorInfoField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(colorInfoField);

		// Campo: Color de título
		JLabel labelTitulo = new JLabel(MonitorDePID.idioma.colorTitulo());
		labelTitulo.setForeground(color_de_texto_de_gui);
		panel.add(labelTitulo);
		JTextField colorTituloField = crearCampoTextoConfig(
				config.obtenerColorTitulo(),
				config::guardarColorTitulo
		);
		if (!isMac) {
			colorTituloField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			colorTituloField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(colorTituloField);

		// Campo: Color de enlace texto
		JLabel labelEnlaceTexto = new JLabel(MonitorDePID.idioma.colorEnlaceTexto());
		labelEnlaceTexto.setForeground(color_de_texto_de_gui);
		panel.add(labelEnlaceTexto);
		JTextField colorEnlaceTextoField = crearCampoTextoConfig(
				config.obtenerColorEnlace(),  // usa el nuevo getter
				config::guardarColorEnlaceTexto
		);
		if (!isMac) {
			colorEnlaceTextoField.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
			colorEnlaceTextoField.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}
		panel.add(colorEnlaceTextoField);

		// Campo: ¿Usar proxy para System.out y System.err?
		JLabel labelProxy = new JLabel("proxySysOutSysErr");
		labelProxy.setForeground(color_de_texto_de_gui);
		panel.add(labelProxy);
		JCheckBox proxySysOutSysErrCheckBox = new JCheckBox();
		proxySysOutSysErrCheckBox.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
		proxySysOutSysErrCheckBox.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		proxySysOutSysErrCheckBox.setSelected(config.obtenerProxySysOutSysErr());
		// Guardar al vuelo cambios en el checkbox
		proxySysOutSysErrCheckBox.addItemListener(e -> config.guardarProxySysOutSysErr(proxySysOutSysErrCheckBox.isSelected()));
		panel.add(proxySysOutSysErrCheckBox);

		return panel;
	}

	private JPanel tabConfidentialidad() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));

		// JTextArea para texto multilinea
		JTextArea areaTexto = new JTextArea(MonitorDePID.idioma.arco());
		areaTexto.setLineWrap(true); // Activar ajuste automático de líneas
		areaTexto.setWrapStyleWord(true); // Romper líneas por palabras
		areaTexto.setEditable(false); // No permitir edición
		areaTexto.setOpaque(false); // Para que se respete el fondo del panel
		areaTexto.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
		areaTexto.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));

		if (!CrashDetectorGUI.esMac()) {
			areaTexto.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
		}

		JPanel panelTexto = new JPanel(new BorderLayout());
		panelTexto.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
		panelTexto.add(areaTexto, BorderLayout.CENTER);

		panelTexto.setMaximumSize(new Dimension(600, Short.MAX_VALUE)); // Máximo 600px de ancho
		panelTexto.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente

		panel.add(panelTexto, BorderLayout.CENTER);

		JPanel placeholderImagen = new JPanel();
		placeholderImagen.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
		placeholderImagen.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaciado superior e inferior

		JLabel etiquetaImagen = new JLabel();
		etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);

		try {
			ImageIcon iconoImagen = new ImageIcon(MonitorDePID.carpeta.resolve("imagenes/profeco.jpg").toString());
			etiquetaImagen.setIcon(iconoImagen);
		} catch (Exception e) {
			etiquetaImagen.setText("Error al cargar la imagen");
			if (!CrashDetectorGUI.esMac()) {
				etiquetaImagen.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
			}
		}

		placeholderImagen.add(etiquetaImagen);
		panel.add(placeholderImagen, BorderLayout.SOUTH);
		return panel;
	}
}
