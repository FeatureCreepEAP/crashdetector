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

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.parches.ConfigDeParches;
import com.asbestosstar.crashdetector.parches.Parche;

public class ConfigPanel extends JPanel {
	public CrashDetectorGUI cdgui;
	private JTabbedPane tabbedPane;
	private Color colorFondoPestanias;

	public ConfigPanel(CrashDetectorGUI cdgui) {
		this.cdgui = cdgui;
		setLayout(new BorderLayout());
		setBackground(CrashDetectorGUI.colorFondo);

		// Crear el contenedor de pestañas
		tabbedPane = new JTabbedPane();

		// Si NO es macOS, oscurecer el fondo de las pestañas para mejorar el contraste
		if (!CrashDetectorGUI.esMac()) {
			// Dos niveles más oscuro suele dar buen contraste con texto claro
			colorFondoPestanias = CrashDetectorGUI.colorFondo.darker().darker();
			tabbedPane.setBackground(colorFondoPestanias);
			tabbedPane.setOpaque(true);
		}

		// Color del texto de las etiquetas de las pestañas
		Color colorTextoPestanias = CrashDetectorGUI.esMac() ? null : CrashDetectorGUI.colorTexto;

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
			guardarButon.setForeground(CrashDetectorGUI.colorTexto);
			guardarButon.setBackground(CrashDetectorGUI.colorBoton);
			guardarButon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			guardarButon.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		}
		guardarButon.setFocusPainted(false);

		guardarButon.addActionListener(e -> {
			Config config = Config.obtenerInstancia();

			// Actualizar los valores desde los campos de texto
			config.guardarSitioDeInformes(sitioDeInformesField.getText());
			config.guardarCarpetaHMCL(carpetaHMCL.getText());
			config.guardarColorFondo(colorFondoField.getText());
			config.guardarColorTexto(colorTextoField.getText());
			config.guardarColorBoton(colorBotonField.getText());
			config.guardarColorCajaTexto(colorCajaTextoField.getText());
			config.guardarColorEnlace(colorEnlaceField.getText());
			config.guardarColorDeTitulosDeConsolas(colorTitulosConsolasField.getText());
			config.guardarColorError(colorErrorField.getText());
			config.guardarColorAdvertencia(colorAdvertenciaField.getText());
			config.guardarColorInfo(colorInfoField.getText());
			config.guardarColorTitulo(colorTituloField.getText());
			config.guardarColorEnlaceTexto(colorEnlaceTextoField.getText());
			config.guardarProxySysOutSysErr(proxySysOutSysErrCheckBox.isSelected());
			// Guardar la nueva configuración
			config.guardar();

			// Volver a la ventana principal
			cdgui.volver();
		});

		// Panel para el botón
		JPanel butonPanel = new JPanel(new BorderLayout());
		if (!CrashDetectorGUI.esMac()) {
			butonPanel.setBackground(CrashDetectorGUI.colorFondo);
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
				colorFondoPestanias != null ? colorFondoPestanias : CrashDetectorGUI.colorFondo.darker());

		// Un poco de padding para mejorar legibilidad
		etiqueta.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
		etiqueta.setOpaque(false); // el fondo lo pinta la envoltura

		envoltura.add(etiqueta, BorderLayout.CENTER);
		return envoltura;
	}

	private JPanel tabDelJuego() {
		JPanel panel = new JPanel();
		panel.setBackground(CrashDetectorGUI.colorFondo);
		panel.setLayout(new GridLayout(0, 1, 5, 5));

		CrashDetectorLogger.log("Tab Del Juego ");

		// Agregar todos los parches de la aplicación
		for (Parche<?> parche : Parche.parches) {
			CrashDetectorLogger.log("Parche " + parche.nombre_de_gui());
			JPanel parchePanel = new JPanel(new BorderLayout());
			parchePanel.setBackground(CrashDetectorGUI.colorFondo);

			// Crear checkbox para activar/desactivar el parche
			JCheckBox checkBox = new JCheckBox();
			checkBox.setBackground(CrashDetectorGUI.colorFondo);
			checkBox.setForeground(CrashDetectorGUI.colorTexto);
			checkBox.setSelected(ConfigDeParches.obtenerInstancia().estaActivo(parche.id()));

			// Actualizar estado cuando se hace clic
			checkBox.addActionListener(e -> {
				ConfigDeParches.obtenerInstancia().establecerActivo(parche.id(), checkBox.isSelected());
			});

			// Etiqueta con el nombre del parche
			JLabel label = new JLabel(parche.nombre_de_gui());
			label.setForeground(CrashDetectorGUI.colorTexto);
			label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

			// Agregar componentes al panel
			parchePanel.add(checkBox, BorderLayout.WEST);
			parchePanel.add(label, BorderLayout.CENTER);

			// Añadir el panel del parche al panel principal
			panel.add(parchePanel);
		}

		return panel;
	}

	// Campos de texto para ajustes de CrashDetector
	private JTextField sitioDeInformesField;
	private JTextField carpetaHMCL;
	private JTextField colorFondoField;
	private JTextField colorTextoField;
	private JTextField colorBotonField;
	private JTextField colorCajaTextoField;
	private JTextField colorEnlaceField;
	private JTextField colorTitulosConsolasField;
	private JTextField colorErrorField;
	private JTextField colorAdvertenciaField;
	private JTextField colorInfoField;
	private JTextField colorTituloField;
	private JTextField colorEnlaceTextoField;
	private JCheckBox proxySysOutSysErrCheckBox;

	private JPanel tabCrashDetector() {
		JPanel panel = new JPanel();
		panel.setBackground(CrashDetectorGUI.colorFondo);
		panel.setLayout(new GridLayout(0, 2, 5, 5));

		Config config = Config.obtenerInstancia();

		// Helper to reduce redundancy
		boolean isMac = CrashDetectorGUI.esMac();
		Color color_de_texto_de_gui = CrashDetectorGUI.colorTexto;

		// Campo: Sitio de informes
		JLabel labelSitio = new JLabel(MonitorDePID.idioma.sitoDeLogging());
		labelSitio.setForeground(color_de_texto_de_gui);
		panel.add(labelSitio);
		sitioDeInformesField = new JTextField(config.obtenerSitoDeInformes());
		if (!isMac) {
			sitioDeInformesField.setBackground(CrashDetectorGUI.colorCajaTexto);
			sitioDeInformesField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(sitioDeInformesField);

		// Campo: Sitio de informes
		JLabel labelHMCL = new JLabel(MonitorDePID.idioma.carpetaHMCL());
		labelHMCL.setForeground(color_de_texto_de_gui);
		panel.add(labelHMCL);
		carpetaHMCL = new JTextField(config.obtenerCarpetaHMCL());
		if (!isMac) {
			carpetaHMCL.setBackground(CrashDetectorGUI.colorCajaTexto);
			carpetaHMCL.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(carpetaHMCL);

		// Campo: Color de fondo
		JLabel labelFondo = new JLabel(MonitorDePID.idioma.colorFondo());
		labelFondo.setForeground(color_de_texto_de_gui);
		panel.add(labelFondo);
		colorFondoField = new JTextField(config.obtenerColorFondo());
		if (!isMac) {
			colorFondoField.setBackground(CrashDetectorGUI.colorCajaTexto);
			colorFondoField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(colorFondoField);

		// Campo: Color de texto
		JLabel labelTexto = new JLabel(MonitorDePID.idioma.colorTexto());
		labelTexto.setForeground(color_de_texto_de_gui);
		panel.add(labelTexto);
		colorTextoField = new JTextField(config.obtenerColorTexto());
		if (!isMac) {
			colorTextoField.setBackground(CrashDetectorGUI.colorCajaTexto);
			colorTextoField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(colorTextoField);

		// Campo: Color de botón
		JLabel labelBoton = new JLabel(MonitorDePID.idioma.colorBoton());
		labelBoton.setForeground(color_de_texto_de_gui);
		panel.add(labelBoton);
		colorBotonField = new JTextField(config.obtenerColorBoton());
		if (!isMac) {
			colorBotonField.setBackground(CrashDetectorGUI.colorCajaTexto);
			colorBotonField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(colorBotonField);

		// Campo: Color de caja de texto
		JLabel labelCajaTexto = new JLabel(MonitorDePID.idioma.colorCajaTexto());
		labelCajaTexto.setForeground(color_de_texto_de_gui);
		panel.add(labelCajaTexto);
		colorCajaTextoField = new JTextField(config.obtenerColorCajaTexto());
		if (!isMac) {
			colorCajaTextoField.setBackground(CrashDetectorGUI.colorCajaTexto);
			colorCajaTextoField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(colorCajaTextoField);

		// Campo: Color de enlace
		JLabel labelEnlace = new JLabel(MonitorDePID.idioma.colorEnlace());
		labelEnlace.setForeground(color_de_texto_de_gui);
		panel.add(labelEnlace);
		colorEnlaceField = new JTextField(config.obtenerColorEnlace());
		if (!isMac) {
			colorEnlaceField.setBackground(CrashDetectorGUI.colorCajaTexto);
			colorEnlaceField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(colorEnlaceField);

		// Campo: Color de títulos de consolas
		JLabel labelTitulosConsolas = new JLabel(MonitorDePID.idioma.colorTitulosConsolas());
		labelTitulosConsolas.setForeground(color_de_texto_de_gui);
		panel.add(labelTitulosConsolas);
		colorTitulosConsolasField = new JTextField(config.obtenerColorDeTitulosDeConsolas());
		if (!isMac) {
			colorTitulosConsolasField.setBackground(CrashDetectorGUI.colorCajaTexto);
			colorTitulosConsolasField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(colorTitulosConsolasField);

		// Campo: Color de error
		JLabel labelError = new JLabel(MonitorDePID.idioma.colorError());
		labelError.setForeground(color_de_texto_de_gui);
		panel.add(labelError);
		colorErrorField = new JTextField(config.obtenerColorError());
		if (!isMac) {
			colorErrorField.setBackground(CrashDetectorGUI.colorCajaTexto);
			colorErrorField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(colorErrorField);

		// Campo: Color de advertencia
		JLabel labelAdvertencia = new JLabel(MonitorDePID.idioma.colorAdvertencia());
		labelAdvertencia.setForeground(color_de_texto_de_gui);
		panel.add(labelAdvertencia);
		colorAdvertenciaField = new JTextField(config.obtenerColorAdvertencia());
		if (!isMac) {
			colorAdvertenciaField.setBackground(CrashDetectorGUI.colorCajaTexto);
			colorAdvertenciaField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(colorAdvertenciaField);

		// Campo: Color de información
		JLabel labelInfo = new JLabel(MonitorDePID.idioma.colorInfo());
		labelInfo.setForeground(color_de_texto_de_gui);
		panel.add(labelInfo);
		colorInfoField = new JTextField(config.obtenerColorInfo());
		if (!isMac) {
			colorInfoField.setBackground(CrashDetectorGUI.colorCajaTexto);
			colorInfoField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(colorInfoField);

		// Campo: Color de título
		JLabel labelTitulo = new JLabel(MonitorDePID.idioma.colorTitulo());
		labelTitulo.setForeground(color_de_texto_de_gui);
		panel.add(labelTitulo);
		colorTituloField = new JTextField(config.obtenerColorTitulo());
		if (!isMac) {
			colorTituloField.setBackground(CrashDetectorGUI.colorCajaTexto);
			colorTituloField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(colorTituloField);

		// Campo: Color de enlace texto
		JLabel labelEnlaceTexto = new JLabel(MonitorDePID.idioma.colorEnlaceTexto());
		labelEnlaceTexto.setForeground(color_de_texto_de_gui);
		panel.add(labelEnlaceTexto);
		colorEnlaceTextoField = new JTextField(config.obtenerColorEnlace());
		if (!isMac) {
			colorEnlaceTextoField.setBackground(CrashDetectorGUI.colorCajaTexto);
			colorEnlaceTextoField.setForeground(CrashDetectorGUI.colorTexto);
		}
		panel.add(colorEnlaceTextoField);

		// Campo: ¿Usar proxy para System.out y System.err?
		JLabel labelProxy = new JLabel("proxySysOutSysErr");
		labelProxy.setForeground(color_de_texto_de_gui);
		panel.add(labelProxy);
		proxySysOutSysErrCheckBox = new JCheckBox();
		proxySysOutSysErrCheckBox.setBackground(CrashDetectorGUI.colorFondo);
		proxySysOutSysErrCheckBox.setForeground(CrashDetectorGUI.colorTexto);
		proxySysOutSysErrCheckBox.setSelected(config.obtenerProxySysOutSysErr());
		panel.add(proxySysOutSysErrCheckBox);

		return panel;
	}

	private JPanel tabConfidentialidad() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(CrashDetectorGUI.colorFondo);

		// JTextArea para texto multilinea
		JTextArea areaTexto = new JTextArea(MonitorDePID.idioma.arco());
		areaTexto.setLineWrap(true); // Activar ajuste automático de líneas
		areaTexto.setWrapStyleWord(true); // Romper líneas por palabras
		areaTexto.setEditable(false); // No permitir edición
		areaTexto.setOpaque(false); // Para que se respete el fondo del panel
		areaTexto.setBackground(CrashDetectorGUI.colorFondo);
		areaTexto.setForeground(CrashDetectorGUI.colorTexto);

		if (!CrashDetectorGUI.esMac()) {
			areaTexto.setForeground(CrashDetectorGUI.colorTexto);
		}

		JPanel panelTexto = new JPanel(new BorderLayout());
		panelTexto.setBackground(CrashDetectorGUI.colorFondo);
		panelTexto.add(areaTexto, BorderLayout.CENTER);

		panelTexto.setMaximumSize(new Dimension(600, Short.MAX_VALUE)); // Máximo 600px de ancho
		panelTexto.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente

		panel.add(panelTexto, BorderLayout.CENTER);

		JPanel placeholderImagen = new JPanel();
		placeholderImagen.setBackground(CrashDetectorGUI.colorFondo);
		placeholderImagen.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaciado superior e inferior

		JLabel etiquetaImagen = new JLabel();
		etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);

		try {
			ImageIcon iconoImagen = new ImageIcon("crash_detector/imagenes/profeco.jpg");
			etiquetaImagen.setIcon(iconoImagen);
		} catch (Exception e) {
			etiquetaImagen.setText("Error al cargar la imagen");
			if (!CrashDetectorGUI.esMac()) {
				etiquetaImagen.setForeground(CrashDetectorGUI.colorTexto);
			}
		}

		placeholderImagen.add(etiquetaImagen);
		panel.add(placeholderImagen, BorderLayout.SOUTH);
		return panel;
	}
}