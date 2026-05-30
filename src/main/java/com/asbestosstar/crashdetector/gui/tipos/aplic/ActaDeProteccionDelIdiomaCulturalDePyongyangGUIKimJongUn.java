package com.asbestosstar.crashdetector.gui.tipos.aplic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class ActaDeProteccionDelIdiomaCulturalDePyongyangGUIKimJongUn
		extends ActaDeProteccionDelIdiomaCulturalDePyongyangGUI {
	private static final long serialVersionUID = 1L;
	public static String ID = "kimjongun";

	// Configuraciones de color
	// Inicializar configuraciones de color
	protected ConfigColor colorFondoVentana = ConfigColor.de("tema.pyongyang.color.fondo.ventana",
			new Color(25, 25, 25));
	protected ConfigColor colorTexto = ConfigColor.de("tema.pyongyang.color.texto", new Color(220, 220, 220));
	protected ConfigColor colorBoton = ConfigColor.de("tema.pyongyang.color.boton", new Color(50, 60, 150));
	protected ConfigColor colorCajaTexto = ConfigColor.de("tema.pyongyang.color.caja_texto", new Color(40, 40, 40));
	protected ConfigColor colorEnlace = ConfigColor.de("tema.pyongyang.color.enlace", new Color(100, 150, 255));
	protected ConfigColor colorBordePanel = ConfigColor.de("tema.pyongyang.color.borde_panel", new Color(60, 60, 60));

	@Override
	public void init() {

		// Construir la interfaz
		setTitle(MonitorDePID.idioma.actaProteccionIdiomaCultural());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(true);
		setMinimumSize(new Dimension(800, 600));

		// Crear el contenido principal
		raizPanel = new JPanel(new BorderLayout(10, 10));
		raizPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Panel izquierdo para las imágenes
		panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setPreferredSize(new Dimension(250, 0));

		// Cargar imágenes
		imagenDPRK = new JLabel();
		imagenDPRK.setPreferredSize(new Dimension(250, 300));
		imagenDPRK.setHorizontalAlignment(JLabel.CENTER);
		imagenDPRK.setVerticalAlignment(JLabel.CENTER);

		imagenKimJongUn = new JLabel();
		imagenKimJongUn.setPreferredSize(new Dimension(192, 192));
		imagenKimJongUn.setHorizontalAlignment(JLabel.CENTER);
		imagenKimJongUn.setVerticalAlignment(JLabel.CENTER);

		// Panel derecho para el contenido
		panelDerecho = new JPanel(new BorderLayout(10, 10));

		// Panel de contenido
		panelContenido = new JPanel(new GridBagLayout());
		panelContenido.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(colorBordePanel.obtener(), 1), MonitorDePID.idioma.informacion()));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new java.awt.Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		// Texto de contenido
		textoContenido = new JTextPane();
		textoContenido.setContentType("text/html");
		textoContenido.setEditable(false);
		textoContenido.putClientProperty(javax.swing.JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);

		// Botón para leer la ley
		botonLeerLey = new JButton(MonitorDePID.idioma.leerLeyCompleta());

		// Agregar componentes a los paneles
		panelIzquierdo.add(imagenDPRK, BorderLayout.NORTH);
		panelIzquierdo.add(imagenKimJongUn, BorderLayout.CENTER);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1.0;
		panelContenido.add(new JScrollPane(textoContenido), gbc);

		gbc.gridy = 1;
		gbc.weighty = 0.0;
		panelContenido.add(botonLeerLey, gbc);

		panelDerecho.add(panelContenido, BorderLayout.CENTER);

		// Agregar paneles a raíz
		raizPanel.add(panelIzquierdo, BorderLayout.WEST);
		raizPanel.add(panelDerecho, BorderLayout.CENTER);

		getContentPane().add(raizPanel);

		// Cargar datos
		cargarDatos();

		// Aplicar apariencia
		aplicarApariencia();

		// Centrar la ventana
		setLocationRelativeTo(null);

		// Cargar imágenes
		cargarImagenDPRK();
		// cargarImagenKimJongUn();activar luego

		// Agregar listeners
		agregarListeners();

		// Mostrar ventana
		setVisible(true);
	}

	private void cargarImagenDPRK() {
		try {
			// Cargar imagen de DPRK
			URL url = Statics.carpeta.resolve("imagenes/dprk.png").toFile().toURI().toURL();
			if (url != null) {
				ImageIcon icon = new ImageIcon(url);
				Image img = icon.getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH);
				imagenDPRK.setIcon(new ImageIcon(img));
			} else {
				imagenDPRK.setText(MonitorDePID.idioma.imagenNoEncontrada());
				imagenDPRK.setHorizontalAlignment(JLabel.CENTER);
				imagenDPRK.setVerticalAlignment(JLabel.CENTER);
				imagenDPRK.setForeground(colorTexto.obtener());
			}
		} catch (Exception e) {
			imagenDPRK.setText(MonitorDePID.idioma.errorCargandoImagen());
			imagenDPRK.setHorizontalAlignment(JLabel.CENTER);
			imagenDPRK.setVerticalAlignment(JLabel.CENTER);
			imagenDPRK.setForeground(colorTexto.obtener());
		}
	}

	private void cargarImagenKimJongUn() {
		try {
			// Cargar imagen de Kim Jong Un
			URL url = Statics.carpeta.resolve("imagenes/kimjongun.png").toFile().toURI().toURL();
			if (url != null) {
				ImageIcon icon = new ImageIcon(url);
				Image img = icon.getImage().getScaledInstance(192, 192, Image.SCALE_SMOOTH);
				imagenKimJongUn.setIcon(new ImageIcon(img));
			} else {
				imagenKimJongUn.setText(MonitorDePID.idioma.imagenNoEncontrada());
				imagenKimJongUn.setHorizontalAlignment(JLabel.CENTER);
				imagenKimJongUn.setVerticalAlignment(JLabel.CENTER);
				imagenKimJongUn.setForeground(colorTexto.obtener());
			}
		} catch (Exception e) {
			imagenKimJongUn.setText(MonitorDePID.idioma.errorCargandoImagen());
			imagenKimJongUn.setHorizontalAlignment(JLabel.CENTER);
			imagenKimJongUn.setVerticalAlignment(JLabel.CENTER);
			imagenKimJongUn.setForeground(colorTexto.obtener());
		}
	}

	private void agregarListeners() {
		botonLeerLey.addActionListener(e -> {
			try {
				java.awt.Desktop.getDesktop().browse(new java.net.URI(
						"https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf"));
			} catch (Exception ex) {
				javax.swing.JOptionPane.showMessageDialog(this, MonitorDePID.idioma.errorAbriendoEnlace(),
						MonitorDePID.idioma.error(), javax.swing.JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	@Override
	public String id() {
		return ID;
	}

	// Componentes de la interfaz
	protected JPanel raizPanel;
	protected JPanel panelIzquierdo;
	protected JPanel panelDerecho;
	protected JPanel panelContenido;
	protected JLabel imagenDPRK;
	protected JLabel imagenKimJongUn;
	protected JTextPane textoContenido;
	protected JButton botonLeerLey;

	protected void cargarDatos() {
		// Cargar el contenido del texto
		String contenido = "<html><body style='color: #"
				+ Integer.toHexString(colorTexto.obtener().getRGB()).substring(2) + "; background-color: #"
				+ Integer.toHexString(colorCajaTexto.obtener().getRGB()).substring(2)
				+ "; font-family: Arial, sans-serif; margin: 10px;'>";
		contenido += "<h2>" + MonitorDePID.idioma.advertencia() + "</h2>";
		contenido += "<p>" + MonitorDePID.idioma.mensajeAdvertenciaIdiomaCoreano() + "</p>";
		contenido += "<p>" + MonitorDePID.idioma.enlaceDocumentacionIdiomaCoreano() + "</p>";
		contenido += "</body></html>";

		textoContenido.setText(contenido);
	}

	public void aplicarApariencia() {
		// Aplicar colores a todos los componentes
		getContentPane().setBackground(colorFondoVentana.obtener());
		raizPanel.setBackground(colorFondoVentana.obtener());

		panelIzquierdo.setBackground(colorFondoVentana.obtener());
		panelDerecho.setBackground(colorFondoVentana.obtener());
		panelContenido.setBackground(colorFondoVentana.obtener());

		// Estilizar botones
		estilizarBoton(botonLeerLey);

		// Actualizar bordes de los paneles
		TitledBorder contentBorder = (TitledBorder) panelContenido.getBorder();
		contentBorder.setTitleColor(colorTexto.obtener());

		// Forzar repintado
		revalidate();
		repaint();
	}

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
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

	protected void estilizarBoton(JButton btn) {
		btn.setBackground(colorBoton.obtener());
		btn.setForeground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
	}

}