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

	@Override
	public void init() {

		// Construir la interfaz
		setTitle(MonitorDePID.idioma.configuracionCorporativa());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(850, 600));

		// Asegurar que no está siempre en primer plano
		setAlwaysOnTop(false);
		setModal(false);

		// Crear el contenido principal
		raizPanel = new JPanel(new BorderLayout(10, 10));
		raizPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Panel izquierdo para la imagen de SAO
		panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setPreferredSize(new Dimension(244, 338));
		panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

		imagenSAO = new JLabel();
		imagenSAO.setPreferredSize(new Dimension(244, 338));
		imagenSAO.setHorizontalAlignment(JLabel.CENTER);
		imagenSAO.setVerticalAlignment(JLabel.CENTER);
		panelIzquierdo.add(imagenSAO, BorderLayout.CENTER);

		// Panel derecho para la configuración
		panelDerecho = new JPanel(new BorderLayout(10, 10));
		panelDerecho.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		// Panel de configuración
		panelConfiguracion = new JPanel(new GridBagLayout());
		panelConfiguracion.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorBordePanel.obtener(), 1),
						MonitorDePID.idioma.configuracionBasica()));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new java.awt.Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridwidth = 1;

		// Idioma de respaldo
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.3;
		JLabel labelIdioma = new JLabel(MonitorDePID.idioma.idiomaRespaldo());
		labelIdioma.setForeground(colorTexto.obtener());
		panelConfiguracion.add(labelIdioma, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.7;
		// Using ComboIdiomasConIcono to show flags
		comboIdiomaRespaldo = new ComboIdiomasConIcono(getMapaIdiomas());
		comboIdiomaRespaldo.setPreferredSize(new Dimension(150, 30));
		panelConfiguracion.add(comboIdiomaRespaldo, gbc);

		// Buscardor habilitado
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

		// Nombre de la herramienta
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

		// Condenar piratería
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

		// Panel para botón de derechos Maranda (inicialmente invisible)
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.weightx = 1.0;
		panelDerechos = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelDerechos.setOpaque(false);
		botonDerechosMaranda = new JButton(MonitorDePID.idioma.derechosMiranda());
		botonDerechosMaranda.setVisible(false);
		panelDerechos.add(botonDerechosMaranda);
		panelConfiguracion.add(panelDerechos, gbc);

		panelDerecho.add(panelConfiguracion, BorderLayout.NORTH);

		// Panel de botones
		panelBotones = new JPanel(new GridBagLayout());
		panelBotones.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(colorBordePanel.obtener(), 1), MonitorDePID.idioma.funcionalidades()));

		gbc = new GridBagConstraints();
		gbc.insets = new java.awt.Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;

		// Lanzadores recomendados
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		botonLanzadoresRecomendados = new JButton(MonitorDePID.idioma.lanzadoresRecomendados());
		panelBotones.add(botonLanzadoresRecomendados, gbc);

		// Lanzadores desaconsejados
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.5;
		botonLanzadoresDesaconsejados = new JButton(MonitorDePID.idioma.lanzadoresDesaconsejados());
		panelBotones.add(botonLanzadoresDesaconsejados, gbc);

		// Mods recomendados
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.5;
		botonModsRecomendados = new JButton(MonitorDePID.idioma.modsRecomendados());
		panelBotones.add(botonModsRecomendados, gbc);

		// Mods desaconsejados
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0.5;
		botonModsDesaconsejados = new JButton(MonitorDePID.idioma.modsDesaconsejados());
		panelBotones.add(botonModsDesaconsejados, gbc);

		// AntiTamper
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 0.5;
		botonAntiTamper = new JButton(MonitorDePID.idioma.antiTamper());
		panelBotones.add(botonAntiTamper, gbc);

		// Firmas
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 0.5;
		botonFirmas = new JButton(TipoGUI.EDITOR_FIRMAS.etiquetaDelBoton());
		panelBotones.add(botonFirmas, gbc);

		// Verificaciones (new button)
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.weightx = 1.0;
		botonVerificaciones = new JButton(TipoGUI.DESHABLICAR_VERIFICACIONES.etiquetaDelBoton());
		panelBotones.add(botonVerificaciones, gbc);

		panelDerecho.add(panelBotones, BorderLayout.CENTER);

		// Agregar paneles a raíz
		raizPanel.add(panelIzquierdo, BorderLayout.WEST);
		raizPanel.add(panelDerecho, BorderLayout.CENTER);

		getContentPane().add(raizPanel);

		// Cargar imagen de SAO
		cargarImagenSAO();

		// Aplicar apariencia
		aplicarApariencia();

		// Establecer valores iniciales
		actualizarValores();

		// Agregar listeners de acción
		agregarListeners();

		pack();
		setSize(850, 600);
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

		// Estilizar botones
		estilizarBoton(botonLanzadoresRecomendados);
		estilizarBoton(botonLanzadoresDesaconsejados);
		estilizarBoton(botonModsRecomendados);
		estilizarBoton(botonModsDesaconsejados);
		estilizarBoton(botonAntiTamper);
		estilizarBoton(botonFirmas);
		estilizarBoton(botonDerechosMaranda);
		estilizarBoton(botonVerificaciones); // Estilizar el nuevo botón

		// Estilizar campo de texto
		estilizarCampo(campoNombreHerramienta);

		// Actualizar checkboxes
		checkBuscardor.setForeground(colorTexto.obtener());
		checkCondenarPirata.setForeground(colorTexto.obtener());

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

		// Los botones solo muestran mensaje "Próximamente" por ahora
		java.awt.event.ActionListener proximamenteListener = e -> {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.proximamente(), MonitorDePID.idioma.informacion(),
					JOptionPane.INFORMATION_MESSAGE);
		};

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

		// Botón de verificaciones
		botonVerificaciones.addActionListener(e -> {
			abrirVerificaciones();
		});

		// Botón de derechos Maranda
		botonDerechosMaranda.addActionListener(e -> {
			abrirMiranda();
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