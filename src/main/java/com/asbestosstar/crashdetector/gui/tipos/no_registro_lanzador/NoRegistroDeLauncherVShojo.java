package com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.ComboIdiomasConIcono;

@Deprecated
public class NoRegistroDeLauncherVShojo extends NoRegistroLanzadorGUI {

	private static final long serialVersionUID = 1L;

	public static String ID = "vshojo";

	private ConfigColor colorFondoVentana = ConfigColor.de("tema.vshojo.no_registro.color.fondo.ventana",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));
	private ConfigColor colorTexto = ConfigColor.de("tema.vshojo.no_registro.color.texto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
	private ConfigColor colorBoton = ConfigColor.de("tema.vshojo.no_registro.color.boton",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
	private ConfigColor colorCajaTexto = ConfigColor.de("tema.vshojo.no_registro.color.caja_texto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorCajaTexto()));
	private ConfigColor colorEnlace = ConfigColor.de("tema.vshojo.no_registro.color.enlace",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorEnlace()));

	@Override
	public void init() {
		// AHORA llamamos al init del padre
		setVisible(true);
	}

	@Override
	public void preparar(JFrame blanco, Instant instant) {
		this.instant = instant;
		// Inicializar colores PRIMERO

		// Configuración básica de diálogo
		setModal(true);
		setAlwaysOnTop(true);
		setTitle(Config.obtenerInstancia().obtenerNombreCD() + " – " + MonitorDePID.idioma.noRegistroLauncherTitulo());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		setMinimumSize(new Dimension(700, 520));
		setLocationRelativeTo(blanco);
		getContentPane().setLayout(new BorderLayout(8, 0));

		// Construir contenido técnico y aplicar apariencia
		JPanel raiz = construirContenido();
		getContentPane().add(raiz, BorderLayout.CENTER);

		pack();
		setSize(880, 640);

		// Apariencia inicial (colores, bordes y textos NO localizados)
		aplicarApariencia();

		// *** Hacer que cerrar la ventana ("X") sea EXACTAMENTE como Omitir ***
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (botonOmitir != null && botonOmitir.isEnabled()) {
					botonOmitir.doClick(); // mismo handler que el botón
				} else {
					dispose();
				}
			}
		});
	}

	// ====== Construcción técnica de la UI (ahora en la implementación concreta)
	// ======
	public JPanel construirContenido() {
		building = true;

		raizPanel = new JPanel(new BorderLayout(8, 0));
		raizPanel.setBorder(new EmptyBorder(8, 10, 0, 10));
		raizPanel.setBackground(colorFondoVentana.obtener()); // Aplicar color

		// Encabezado
		encabezadoPanel = new JPanel(new BorderLayout(6, 6));
		encabezadoPanel.setBackground(colorFondoVentana.obtener()); // Aplicar color

		JLabel titulo = new JLabel("CrashDetector – " + MonitorDePID.idioma.noRegistroLauncherTitulo());
		titulo.setFont(negrita(titulo.getFont(), 18f));
		titulo.setForeground(colorTexto.obtener()); // Aplicar color
		encabezadoPanel.add(titulo, BorderLayout.NORTH);

		// Descripción HTML
		descripcionHtml = new javax.swing.JEditorPane();
		descripcionHtml.setContentType("text/html");
		descripcionHtml.setEditable(false);
		descripcionHtml.putClientProperty(javax.swing.JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		descripcionHtml.setText(htmlWrap(MonitorDePID.idioma.noRegistroDeLauncher()));
		descripcionHtml.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent e) {
				if (e.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
					try {
						if (java.awt.Desktop.isDesktopSupported()) {
							java.awt.Desktop.getDesktop().browse(e.getURL().toURI());
						}
					} catch (Exception ex) {
						CrashDetectorLogger.logException(ex);
					}
				}
			}
		});

		descScroll = new JScrollPane(descripcionHtml);
		descScroll.setPreferredSize(new Dimension(10, 84));
		descScroll.getViewport().setBackground(colorCajaTexto.obtener()); // Aplicar color
		descScroll.setBorder(javax.swing.BorderFactory.createLineBorder(colorBoton.obtener().darker(), 1)); // Aplicar
																											// color
		encabezadoPanel.add(descScroll, BorderLayout.CENTER);

		// Fila de controles (selector + idiomas)
		JPanel filaControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
		filaControles.setBackground(colorFondoVentana.obtener()); // Aplicar color

		selector = new JComboBox<>(
				new String[] { GEN, CURSE, PRISM, HMCL, FENIX, ATL, GD, BATTLY, NIGHTWORLD, MCSERVER, ENLACE_MD });
		estilizarCombo(selector); // Aplicar estilo
		selector.setPreferredSize(new Dimension(360, 34));
		selector.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (!building)
					refrescarInterfaz();
			}
		});

		LinkedHashMap<String, String> banderas = new LinkedHashMap<>();
		banderas.put("Español", "imagenes/bandera_mexico.png");
		banderas.put("English", "imagenes/bandera_inglaterra.png");
		banderas.put("العربية", "imagenes/bandera_arabia.png");
		banderas.put("Português", "imagenes/bandera_brasil.png");
		banderas.put("فارسی", "imagenes/bandera_iran.png");
		banderas.put("Русский", "imagenes/bandera_rusia.png");
		banderas.put("简体中文", "imagenes/bandera_china.png");
		banderas.put("Esperanto", "imagenes/bandera_esperanto.png");
		banderas.put("日本語", "imagenes/bandera_japon.png");
		banderas.put("한국어", "imagenes/bandera_corea.png");
		comboBoxIdioma = new ComboIdiomasConIcono(banderas);
		estilizarCombo(comboBoxIdioma); // Aplicar estilo

		// Selección por idioma actual
		String code = MonitorDePID.idioma.codigo();
		if ("es".equals(code))
			comboBoxIdioma.setSelectedItem("Español");
		else if ("en".equals(code))
			comboBoxIdioma.setSelectedItem("English");
		else if ("ar".equals(code))
			comboBoxIdioma.setSelectedItem("العربية");
		else if ("pt".equals(code))
			comboBoxIdioma.setSelectedItem("Português");
		else if ("fa".equals(code))
			comboBoxIdioma.setSelectedItem("فارسی");
		else if ("ru".equals(code))
			comboBoxIdioma.setSelectedItem("Русский");
		else if ("zh".equals(code))
			comboBoxIdioma.setSelectedItem("简体中文");
		else if ("eo".equals(code))
			comboBoxIdioma.setSelectedItem("Esperanto");
		else if ("ja".equals(code))
			comboBoxIdioma.setSelectedItem("日本語");
		else if ("ko".equals(code))
			comboBoxIdioma.setSelectedItem("한국어");
		else
			comboBoxIdioma.setSelectedItem("Español");

		comboBoxIdioma.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (building)
					return;
				String sel = (String) comboBoxIdioma.getSelectedItem();
				String codigo = CrashDetectorGUI.obtenerCodigoIdioma(sel);
				if (codigo != null) {
					try {
						java.io.File parent = Idioma.archivo.getParentFile();
						if (parent != null)
							parent.mkdirs();
						java.nio.file.Files.write(Idioma.archivo.toPath(),
								codigo.getBytes(java.nio.charset.StandardCharsets.UTF_8));
					} catch (java.io.IOException ex) {
						CrashDetectorLogger.logException(ex);
					}
					MonitorDePID.idioma = Idioma.detectar();
					actualizarTextos();
				}
			}
		});

		filaControles.add(selector);
		filaControles.add(Box.createHorizontalStrut(10));
		filaControles.add(comboBoxIdioma);
		encabezadoPanel.add(filaControles, BorderLayout.SOUTH);

		raizPanel.add(encabezadoPanel, BorderLayout.NORTH);

		// Centro: imagen + botón HMCL debajo + (opcional) área de pegado
		centroPanel = new JPanel(new BorderLayout(6, 6));
		centroPanel.setBackground(colorFondoVentana.obtener()); // Aplicar color

		panelImagenYBoton = new JPanel(new BorderLayout());
		panelImagenYBoton.setBackground(colorFondoVentana.obtener()); // Aplicar color

		imagenLbl.setOpaque(true);
		imagenLbl.setBackground(colorFondoVentana.obtener()); // Aplicar color
		imagenLbl.setBorder(javax.swing.BorderFactory.createLineBorder(colorBoton.obtener().darker(), 1)); // Aplicar
																											// color
		panelImagenYBoton.add(imagenLbl, BorderLayout.CENTER);

		panelBajoImagen = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 6));
		panelBajoImagen.setBackground(colorFondoVentana.obtener()); // Aplicar color

		estilizarBoton(seleccionarCarpetaBtn); // Aplicar estilo
		seleccionarCarpetaBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				abrirSelectorCarpeta();
			}
		});
		panelBajoImagen.add(seleccionarCarpetaBtn);

		panelImagenYBoton.add(panelBajoImagen, BorderLayout.SOUTH);
		centroPanel.add(panelImagenYBoton, BorderLayout.NORTH);

		// Área de pegado (AWT)
		areaTexto = new java.awt.TextArea("", 24, 100, java.awt.TextArea.SCROLLBARS_BOTH);
		areaTexto.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 13));
		areaTexto.setBackground(colorCajaTexto.obtener()); // Aplicar color
		areaTexto.setForeground(colorEnlace.obtener()); // Aplicar color

		panelAreaTexto = new JPanel(new BorderLayout());
		panelAreaTexto.setBackground(colorFondoVentana.obtener()); // Aplicar color
		panelAreaTexto.add(areaTexto, BorderLayout.CENTER);
		panelAreaTexto.setBorder(bordeTitulado(MonitorDePID.idioma.pegaLosRegistrosAqui())); // Aplicar borde
		centroPanel.add(panelAreaTexto, BorderLayout.CENTER);

		raizPanel.add(centroPanel, BorderLayout.CENTER);

		// Pie: logo + botones
		piePanel = new JPanel(new GridBagLayout());
		piePanel.setBackground(colorFondoVentana.obtener()); // Aplicar color

		javax.swing.ImageIcon vshojoIcon = cargarIconoEncajado("imagenes/vshojo.png", 140, 90, true);
		int filaAltura = (vshojoIcon != null ? vshojoIcon.getIconHeight() : 90);

		if (vshojoIcon != null) {
			vshojoLbl.setIcon(vshojoIcon);
			vshojoLbl.setBorder(null);
			vshojoLbl.setPreferredSize(new Dimension(vshojoIcon.getIconWidth(), vshojoIcon.getIconHeight()));
		} else {
			vshojoLbl.setText("VShojo");
		}
		vshojoLbl.setForeground(colorTexto.obtener()); // Aplicar color

		botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		botonesPanel.setBackground(colorFondoVentana.obtener()); // Aplicar color
		botonesPanel.setPreferredSize(new Dimension(10, filaAltura));
		botonesPanel.setMinimumSize(new Dimension(10, filaAltura));
		botonesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, filaAltura));

		botonProxy = new JButton("ProxySysOutSysErr");
		botonGuardar = new JButton(MonitorDePID.idioma.guardarYCerrar());
		botonOmitir = new JButton(MonitorDePID.idioma.omitirYCerrar());
		estilizarBoton(botonProxy, 4); // Aplicar estilo
		estilizarBoton(botonGuardar, 4); // Aplicar estilo
		estilizarBoton(botonOmitir, 4); // Aplicar estilo

		botonProxy.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent ev) {
				String msg = MonitorDePID.idioma.habilitarProxySysOutSysErrMensaje();
				int r = JOptionPane.showConfirmDialog(NoRegistroDeLauncherVShojo.this, msg,
						MonitorDePID.idioma.confirmacionTitulo(), JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (r == JOptionPane.YES_OPTION) {
					Config.obtenerInstancia().guardarProxySysOutSysErr(true);
					JOptionPane.showMessageDialog(NoRegistroDeLauncherVShojo.this,
							MonitorDePID.idioma.proxyHabilitadoMensaje(), MonitorDePID.idioma.informacionTitulo(),
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		botonGuardar.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				guardarRegistros();
			}
		});
		botonOmitir.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});

		// Orden: Guardar, Omitir, Proxy
		botonesPanel.add(botonGuardar);
		botonesPanel.add(botonOmitir);
		botonesPanel.add(botonProxy);

		GridBagConstraints g0 = new GridBagConstraints();
		g0.gridx = 0;
		g0.gridy = 0;
		g0.anchor = GridBagConstraints.WEST;
		g0.insets = new java.awt.Insets(0, 0, 0, 8);
		piePanel.add(vshojoLbl, g0);

		GridBagConstraints g1 = new GridBagConstraints();
		g1.gridx = 1;
		g1.gridy = 0;
		g1.weightx = 1.0;
		g1.anchor = GridBagConstraints.EAST;
		g1.fill = GridBagConstraints.HORIZONTAL;
		piePanel.add(botonesPanel, g1);

		raizPanel.add(piePanel, BorderLayout.SOUTH);

		// Selección automática por ruta
		selector.setSelectedItem(detectarPorDirectorio());
		building = false;
		refrescarInterfaz();
		return raizPanel;
	}

	@Override
	public void aplicarApariencia() {
		// Contenedor raíz y todos los paneles estructurales
		getContentPane().setBackground(colorFondoVentana.obtener());
		if (raizPanel != null)
			raizPanel.setBackground(colorFondoVentana.obtener());
		if (encabezadoPanel != null)
			encabezadoPanel.setBackground(colorFondoVentana.obtener());
		if (centroPanel != null)
			centroPanel.setBackground(colorFondoVentana.obtener());
		if (panelImagenYBoton != null)
			panelImagenYBoton.setBackground(colorFondoVentana.obtener());
		if (piePanel != null)
			piePanel.setBackground(colorFondoVentana.obtener());
		if (botonesPanel != null)
			botonesPanel.setBackground(colorFondoVentana.obtener());

		// Descripción (scroll + editor)
		if (descScroll != null) {
			descScroll.getViewport().setBackground(colorCajaTexto.obtener());
			descScroll.setBorder(javax.swing.BorderFactory.createLineBorder(colorBoton.obtener().darker(), 1));
		}
		if (descripcionHtml != null) {
			descripcionHtml.setOpaque(true);
			descripcionHtml.setBackground(colorCajaTexto.obtener());
		}

		// Imagen / contenedores
		if (imagenLbl != null) {
			imagenLbl.setBackground(colorFondoVentana.obtener());
			imagenLbl.setBorder(javax.swing.BorderFactory.createLineBorder(colorBoton.obtener().darker(), 1));
		}
		if (panelBajoImagen != null)
			panelBajoImagen.setBackground(colorFondoVentana.obtener());

		// Área de pegado
		if (panelAreaTexto != null) {
			panelAreaTexto.setBackground(colorFondoVentana.obtener());
			panelAreaTexto.setBorder(bordeTitulado(MonitorDePID.idioma.pegaLosRegistrosAqui()));
		}
		if (areaTexto != null) {
			areaTexto.setBackground(colorCajaTexto.obtener());
			areaTexto.setForeground(colorEnlace.obtener());
		}

		// Pie
		if (vshojoLbl != null)
			vshojoLbl.setForeground(colorTexto.obtener());

		// Botones explícitamente opacos (macOS / L&F)
		if (seleccionarCarpetaBtn != null)
			estilizarBoton(seleccionarCarpetaBtn);
		if (botonGuardar != null)
			estilizarBoton(botonGuardar, 4);
		if (botonOmitir != null)
			estilizarBoton(botonOmitir, 4);
		if (botonProxy != null)
			estilizarBoton(botonProxy, 4);

		// Combos
		if (comboBoxIdioma != null)
			estilizarCombo(comboBoxIdioma);
		if (selector != null)
			estilizarCombo(selector);

		// Forzar repaint
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
		elementos.add(colorFondoVentana);
		elementos.add(colorTexto);
		elementos.add(colorBoton);
		elementos.add(colorCajaTexto);
		elementos.add(colorEnlace);
		return elementos;
	}

	// ====== Utilidades de estilo ======

	public TitledBorder bordeTitulado(String titulo) {
		// El color del borde se aplicará en la implementación concreta
		TitledBorder b = BorderFactory
				.createTitledBorder(BorderFactory.createLineBorder(colorBoton.obtener().darker(), 1), titulo);
		// El color del título se aplicará en la implementación concreta
		b.setTitleColor(colorTexto.obtener());
		return b;
	}

	public void estilizarBoton(JButton btn) {
		estilizarBoton(btn, 10);
	}

	public void estilizarBoton(JButton btn, int paddingV) {
		if (!CrashDetectorGUI.esMac()) {
			// Los colores se aplicarán en la implementación concreta
			btn.setBackground(colorBoton.obtener());
			btn.setForeground(colorTexto.obtener());
			btn.setFocusPainted(false);
			btn.setBorder(BorderFactory.createEmptyBorder(paddingV, 18, paddingV, 18));
		} else {
			// por defecto L&F; la subclase puede forzar opaco/colores
			btn.setContentAreaFilled(false);
		}
		btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	}

	public void estilizarCombo(JComboBox<?> combo) {
		if (!CrashDetectorGUI.esMac()) {
			// Los colores se aplicarán en la implementación concreta
			combo.setBackground(colorBoton.obtener());
			combo.setForeground(colorTexto.obtener());
		}
		combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		combo.setPreferredSize(new Dimension(220, 32));
	}

	/** Envuelve HTML sin escapar, aplicando colores/estilo del proyecto. */
	public String htmlWrap(String innerHtml) {
		// Los colores RGB se aplicarán en la implementación concreta
		String fg = rgb(colorTexto.obtener());
		String bg = rgb(colorCajaTexto.obtener());
		return "<html><body style='margin:6px; font-family: Segoe UI, sans-serif; font-size:13px; color:" + fg
				+ "; background:" + bg + ";'>" + innerHtml + "</body></html>";
	}

}