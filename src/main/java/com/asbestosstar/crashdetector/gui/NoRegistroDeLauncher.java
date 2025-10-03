package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EnlanceMD;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Diálogo cuando el launcher no provee registro. - Descripción HTML arriba
 * (renderiza links/br sin escapar). - AWT TextArea para logs grandes (si
 * aplica). - Botón HMCL debajo de la imagen. - Pie compacto con logo. -
 * Escalado flexible de imágenes (cuadradas/panorámicas). - Battly (sin pegado),
 * Servidor de Minecraft / Nightworld (con pegado). - Selección automática del
 * launcher (incluye "ox" en ruta -> GDLauncher). - Si no hay área de pegado, se
 * compacta el diálogo. - Enlace MD: overlay con GIF de “descargando” hasta
 * terminar.
 */
public class NoRegistroDeLauncher extends JDialog {

	// Opciones (etiquetas mostradas en el combo, en español donde aplica)
	private static final String GEN = "Genérico";
	private static final String CURSE = "CurseForge";
	private static final String PRISM = "Prism/MultiMC+++";
	private static final String HMCL = "HMCL";
	private static final String FENIX = "Fénix";
	private static final String ATL = "ATLauncher";
	private static final String GD = "GDLauncher";
	private static final String BATTLY = "Battly Launcher";
	private static final String NIGHTWORLD = "Nightworld";
	private static final String MCSERVER = "Servidor de Minecraft";
	private static final String ENLACE_MD = "Enlace MD";

	// Paleta desde CrashDetectorGUI
	private static final Color COLOR_FONDO = CrashDetectorGUI.colorFondo;
	private static final Color COLOR_TEXTO = CrashDetectorGUI.colorTexto;
	private static final Color COLOR_BOTON = CrashDetectorGUI.colorBoton;
	private static final Color COLOR_CAJA = CrashDetectorGUI.colorCajaTexto;
	private static final Color COLOR_ENLACE = CrashDetectorGUI.colorEnlace;

	// Escalado imagen de ayuda
	private static final int MAX_ANCHO_IMAGEN = 500;
	private static final int MAX_ALTO_CASI_CUADRADA = 220;
	private static final int MAX_ALTO_PANORAMICA = 120;
	private static final int ALTURA_MINIMA_SIN_IMAGEN = 120;

	private boolean building = false;

	// Controles
	private JComboBox<String> selector;
	private JComboBox<String> comboBoxIdioma;

	private final JLabel imagenLbl = new JLabel("", SwingConstants.CENTER);
	private final JLabel vshojoLbl = new JLabel("", SwingConstants.CENTER);
	private final JButton seleccionarCarpetaBtn = new JButton(MonitorDePID.idioma.seleccionarCarpeta());

	// Descripción en HTML
	private JEditorPane descripcionHtml;
	private JScrollPane descScroll;

	// Área de pegado (AWT)
	private TextArea areaTexto;

	private JButton botonGuardar;
	private JButton botonOmitir;
	private JButton botonProxy;

	private JPanel panelAreaTexto;
	private JPanel panelBajoImagen; // botón HMCL bajo la imagen

	public static File cd_launcherlog = new File("cd_launcherlog");
	public Instant instant;

	public NoRegistroDeLauncher(JFrame blanco, Instant instant) {
		super(blanco, true);
		this.instant = instant;

		setTitle("CrashDetector – " + MonitorDePID.idioma.noRegistroLauncherTitulo());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		setMinimumSize(new Dimension(700, 520));
		setLocationRelativeTo(blanco);
		getContentPane().setLayout(new BorderLayout(8, 0));
		getContentPane().setBackground(COLOR_FONDO);

		JPanel raiz = construirContenido();
		getContentPane().add(raiz, BorderLayout.CENTER);

		pack();
		setSize(880, 640);
		setVisible(true);
	}

	private JPanel construirContenido() {
		building = true;

		JPanel raiz = new JPanel(new BorderLayout(8, 0));
		raiz.setBorder(new EmptyBorder(8, 10, 0, 10));
		raiz.setBackground(COLOR_FONDO);

		// Encabezado
		JPanel encabezado = new JPanel(new BorderLayout(6, 6));
		encabezado.setBackground(COLOR_FONDO);

		JLabel titulo = new JLabel("CrashDetector – " + MonitorDePID.idioma.noRegistroLauncherTitulo());
		titulo.setForeground(COLOR_TEXTO);
		titulo.setFont(negrita(titulo.getFont(), 18f));
		encabezado.add(titulo, BorderLayout.NORTH);

		// Descripción HTML
		descripcionHtml = new JEditorPane();
		descripcionHtml.setContentType("text/html");
		descripcionHtml.setEditable(false);
		descripcionHtml.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		descripcionHtml.setBackground(COLOR_CAJA);
		descripcionHtml.setText(htmlWrap(MonitorDePID.idioma.noRegistroDeLauncher())); // raw HTML allowed
		descripcionHtml.addHyperlinkListener(e -> {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				try {
					if (Desktop.isDesktopSupported()) {
						Desktop.getDesktop().browse(new URI(e.getURL().toString()));
					}
				} catch (Exception ex) {
					CrashDetectorLogger.logException(ex);
				}
			}
		});

		descScroll = new JScrollPane(descripcionHtml);
		descScroll.getViewport().setBackground(COLOR_CAJA);
		descScroll.setBorder(BorderFactory.createLineBorder(COLOR_BOTON.darker(), 1));
		descScroll.setPreferredSize(new Dimension(10, 84));
		encabezado.add(descScroll, BorderLayout.CENTER);

		// Fila de controles
		JPanel filaControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
		filaControles.setBackground(COLOR_FONDO);

		selector = new JComboBox<>(
				new String[] { GEN, CURSE, PRISM, HMCL, FENIX, ATL, GD, BATTLY, NIGHTWORLD, MCSERVER, ENLACE_MD });
		estilizarCombo(selector);
		selector.setPreferredSize(new Dimension(360, 34));
		selector.addActionListener(e -> {
			if (!building)
				refrescarInterfaz();
		});

		comboBoxIdioma = new JComboBox<>(new String[] { "Español", "English", "العربية", "Português", "فارسی",
				"Русский", "简体中文", "Esperanto", "日本語", "한국어" });
		estilizarCombo(comboBoxIdioma);
		switch (MonitorDePID.idioma.codigo()) {
		case "es":
			comboBoxIdioma.setSelectedItem("Español");
			break;
		case "en":
			comboBoxIdioma.setSelectedItem("English");
			break;
		case "ar":
			comboBoxIdioma.setSelectedItem("العربية");
			break;
		case "pt":
			comboBoxIdioma.setSelectedItem("Português");
			break;
		case "fa":
			comboBoxIdioma.setSelectedItem("فارسی");
			break;
		case "ru":
			comboBoxIdioma.setSelectedItem("Русский");
			break;
		case "zh":
			comboBoxIdioma.setSelectedItem("简体中文");
			break;
		case "eo":
			comboBoxIdioma.setSelectedItem("Esperanto");
			break;
		case "ja":
			comboBoxIdioma.setSelectedItem("日本語");
			break;
		case "ko":
			comboBoxIdioma.setSelectedItem("한국어");
			break;
		default:
			comboBoxIdioma.setSelectedItem("Español");
		}
		comboBoxIdioma.addActionListener(e -> {
			if (building)
				return;
			String sel = (String) comboBoxIdioma.getSelectedItem();
			String codigo = obtenerCodigoIdioma(sel);
			if (codigo != null) {
				try {
					File parent = Idioma.archivo.getParentFile();
					if (parent != null)
						parent.mkdirs();
					try (java.io.BufferedWriter w = java.nio.file.Files.newBufferedWriter(Idioma.archivo.toPath(),
							java.nio.file.StandardOpenOption.CREATE,
							java.nio.file.StandardOpenOption.TRUNCATE_EXISTING)) {
						w.write(codigo);
					}
				} catch (IOException ex) {
					CrashDetectorLogger.logException(ex);
				}
				MonitorDePID.idioma = Idioma.detectar();
				actualizarTextos();
			}
		});

		filaControles.add(selector);
		filaControles.add(Box.createHorizontalStrut(10));
		filaControles.add(comboBoxIdioma);
		encabezado.add(filaControles, BorderLayout.SOUTH);

		raiz.add(encabezado, BorderLayout.NORTH);

		// Centro: imagen + botón HMCL debajo + (opcional) área de pegado
		JPanel centro = new JPanel(new BorderLayout(6, 6));
		centro.setBackground(COLOR_FONDO);

		JPanel panelImagenYBoton = new JPanel(new BorderLayout());
		panelImagenYBoton.setBackground(COLOR_FONDO);

		imagenLbl.setOpaque(true);
		imagenLbl.setBackground(COLOR_FONDO);
		imagenLbl.setBorder(BorderFactory.createLineBorder(COLOR_BOTON.darker(), 1));
		panelImagenYBoton.add(imagenLbl, BorderLayout.CENTER);

		panelBajoImagen = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 6));
		panelBajoImagen.setBackground(COLOR_FONDO);

		estilizarBoton(seleccionarCarpetaBtn);
		seleccionarCarpetaBtn.addActionListener(ev -> abrirSelectorCarpeta());
		panelBajoImagen.add(seleccionarCarpetaBtn);

		panelImagenYBoton.add(panelBajoImagen, BorderLayout.SOUTH);
		centro.add(panelImagenYBoton, BorderLayout.NORTH);

		// Área de pegado (AWT)
		areaTexto = new TextArea("", 24, 100, TextArea.SCROLLBARS_BOTH);
		areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 13));
		areaTexto.setBackground(COLOR_CAJA);
		areaTexto.setForeground(COLOR_ENLACE);

		panelAreaTexto = new JPanel(new BorderLayout());
		panelAreaTexto.setBackground(COLOR_FONDO);
		panelAreaTexto.setBorder(bordeTitulado(MonitorDePID.idioma.pegaLosRegistrosAqui()));
		panelAreaTexto.add(areaTexto, BorderLayout.CENTER);
		centro.add(panelAreaTexto, BorderLayout.CENTER);

		raiz.add(centro, BorderLayout.CENTER);

		// Pie: logo VShojo + botones
		JPanel pie = new JPanel(new GridBagLayout());
		pie.setBackground(COLOR_FONDO);

		ImageIcon vshojoIcon = cargarIconoEncajado("/imagenes/vshojo.png", 140, 90, true);
		int filaAltura = (vshojoIcon != null ? vshojoIcon.getIconHeight() : 90);

		if (vshojoIcon != null) {
			vshojoLbl.setIcon(vshojoIcon);
			vshojoLbl.setBorder(null);
			vshojoLbl.setPreferredSize(new Dimension(vshojoIcon.getIconWidth(), vshojoIcon.getIconHeight()));
		} else {
			vshojoLbl.setText("VShojo");
			vshojoLbl.setForeground(COLOR_TEXTO);
		}

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		botones.setBackground(COLOR_FONDO);
		botones.setPreferredSize(new Dimension(10, filaAltura));
		botones.setMinimumSize(new Dimension(10, filaAltura));
		botones.setMaximumSize(new Dimension(Integer.MAX_VALUE, filaAltura));

		botonProxy = new JButton("ProxySysOutSysErr");
		botonGuardar = new JButton(MonitorDePID.idioma.guardarYCerrar());
		botonOmitir = new JButton(MonitorDePID.idioma.omitirYCerrar());
		estilizarBoton(botonProxy, 4);
		estilizarBoton(botonGuardar, 4);
		estilizarBoton(botonOmitir, 4);

		botonProxy.addActionListener(ev -> {
			String msg = MonitorDePID.idioma.habilitarProxySysOutSysErrMensaje();
			int r = JOptionPane.showConfirmDialog(NoRegistroDeLauncher.this, msg,
					MonitorDePID.idioma.confirmacionTitulo(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (r == JOptionPane.YES_OPTION) {
				Config.obtenerInstancia().guardarProxySysOutSysErr(true);
				JOptionPane.showMessageDialog(NoRegistroDeLauncher.this, MonitorDePID.idioma.proxyHabilitadoMensaje(),
						MonitorDePID.idioma.informacionTitulo(), JOptionPane.INFORMATION_MESSAGE);
			}
		});
		botonGuardar.addActionListener(e -> guardarRegistros());
		botonOmitir.addActionListener(e -> dispose());

		// ——— Swap: Guardar primero, luego Omitir, luego Proxy ———
		botones.add(botonGuardar);
		botones.add(botonOmitir);
		botones.add(botonProxy);

		GridBagConstraints g0 = new GridBagConstraints();
		g0.gridx = 0;
		g0.gridy = 0;
		g0.anchor = GridBagConstraints.WEST;
		g0.insets = new java.awt.Insets(0, 0, 0, 8);
		pie.add(vshojoLbl, g0);

		GridBagConstraints g1 = new GridBagConstraints();
		g1.gridx = 1;
		g1.gridy = 0;
		g1.weightx = 1.0;
		g1.anchor = GridBagConstraints.EAST;
		g1.fill = GridBagConstraints.HORIZONTAL;
		pie.add(botones, g1);

		raiz.add(pie, BorderLayout.SOUTH);

		// Selección automática cuando todo ya existe
		selector.setSelectedItem(detectarPorDirectorio());
		building = false;
		refrescarInterfaz();

		return raiz;
	}

	/**
	 * Detecta el launcher a partir de la ruta actual. - Revisa la ruta completa (no
	 * solo el último segmento). - Recorre algunos padres por si estás en
	 * subcarpetas como "assets". - Añade una heurística Linux para Prism
	 * (~/.local/share/PrismLauncher).
	 */
	private String detectarPorDirectorio() {
		String rutaAbs = new File(System.getProperty("user.dir")).getAbsolutePath().toLowerCase();

		// 1) Coincidencias por ruta completa (más fiable)
		if (contiene(rutaAbs, "prismlauncher", "multimc", "polymc", "pollymc", "freesm", "fjord", "ultim", "prism"))
			return PRISM;
		if (contiene(rutaAbs, "atlauncher"))
			return ATL;
		if (contiene(rutaAbs, "gdlauncher"))
			return GD;
		if (contiene(rutaAbs, "curseforge", "overwolf"))
			return CURSE;
		if (contiene(rutaAbs, "hmcl"))
			return HMCL;
		if (contiene(rutaAbs, "battly"))
			return BATTLY;
		if (contiene(rutaAbs, "server"))
			return MCSERVER;

		// 2) Revisión de hasta 5 directorios padre (por nombre)
		File f = new File(rutaAbs);
		for (int i = 0; i < 5 && f != null; i++, f = f.getParentFile()) {
			String nombre = f.getName().toLowerCase();
			if (contiene(nombre, "prismlauncher", "multimc", "polymc", "pollymc", "freesm", "fjord", "ultim", "prism"))
				return PRISM;
			if (nombre.contains("atlauncher"))
				return ATL;
			if (nombre.contains("gdlauncher"))
				return GD;
			if (contiene(nombre, "curseforge", "overwolf"))
				return CURSE;
			if (nombre.contains("hmcl"))
				return HMCL;
			if (nombre.contains("battly"))
				return BATTLY;
			if (nombre.contains("server"))
				return MCSERVER;
		}

		// Si nada coincide, genérico
		return GEN;
	}

	/**
	 * Devuelve true si la cadena contiene cualquiera de los tokens
	 * (case-insensitive ya aplicado).
	 */
	private boolean contiene(String s, String... tokens) {
		for (String t : tokens)
			if (s.contains(t))
				return true;
		return false;
	}

	private void actualizarTextos() {
		setTitle("CrashDetector – " + MonitorDePID.idioma.noRegistroLauncherTitulo());
		descripcionHtml.setText(htmlWrap(MonitorDePID.idioma.noRegistroDeLauncher()));
		seleccionarCarpetaBtn.setText(MonitorDePID.idioma.seleccionarCarpeta());
		if (botonGuardar != null)
			botonGuardar.setText(MonitorDePID.idioma.guardarYCerrar());
		if (botonOmitir != null)
			botonOmitir.setText(MonitorDePID.idioma.omitirYCerrar());
		if (panelAreaTexto != null)
			panelAreaTexto.setBorder(bordeTitulado(MonitorDePID.idioma.pegaLosRegistrosAqui()));
		refrescarInterfaz();
	}

	/**
	 * Cambia imagen/controles según launcher y compacta si no hay área de pegado.
	 */
	private void refrescarInterfaz() {
		String tipo = (String) selector.getSelectedItem();

		// Por defecto: usa el texto del sistema de idioma (puede contener HTML)
		String desc = MonitorDePID.idioma.noRegistroDeLauncher();

		// Textos HTML específicos
		if (HMCL.equals(tipo)) {
			desc = "HMCL (HelloMinecraftLauncher): You must select the folder where HMCL is installed and choose the \".hmcl\" folder. HMCL logs are saved here and contain important error information.<br>";
		} else if (GEN.equals(tipo)) {
			desc = "GENERIC: Select the type of launcher you're using. Launcher logs (launcher_log.txt, stdout, etc.) contain vital error details not present in latest.log. CrashDetector cannot read your launcher's logs — it might not generate one, so you'll need to paste the logs manually.<br>For more info, see <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">this issue</a>. These logs include standard output (STDOUT), which is essential for diagnosing many types of errors.";
		}

		ImageIcon icono = null;
		boolean mostrarArea = true;
		boolean mostrarBotonHMCL = false;

		switch (tipo) {
		case CURSE:
			desc = MonitorDePID.idioma.descripcionCurseforge();
			icono = cargarIconoAyudaFlexible("/imagenes/cfskiplauncher.png");
			mostrarArea = false;
			break;
		case PRISM:
			desc = MonitorDePID.idioma.descripcionPrism();
			icono = cargarIconoAyudaFlexible("/imagenes/registros_de_launcher_prism.png");
			break;
		case HMCL:
			icono = cargarIconoAyudaFlexible("/imagenes/hcml.png");
			mostrarArea = false;
			mostrarBotonHMCL = true;
			break;
		case FENIX:
			desc = MonitorDePID.idioma.descripcionFenix();
			icono = cargarIconoAyudaFlexible("/imagenes/fenix.png");
			break;
		case ATL:
			desc = MonitorDePID.idioma.descripcionATLauncher();
			icono = cargarIconoAyudaFlexible("/imagenes/atlogs.png");
			break;
		case GD:
			desc = MonitorDePID.idioma.descripcionGDLauncher();
			icono = cargarIconoAyudaFlexible("/imagenes/gdconsola.png");
			break;
		case ENLACE_MD:
			desc = MonitorDePID.idioma.descripcionLinksMarkdown();
			icono = null;
			break;
		case BATTLY:
			desc = MonitorDePID.idioma.noRegistroDeBattly();
			icono = cargarIconoAyudaFlexible("/imagenes/battly.png");
			mostrarArea = false;
			break;
		case NIGHTWORLD:
			desc = MonitorDePID.idioma.noRegistroDeNightWorld();
			icono = cargarIconoAyudaFlexible("/imagenes/nightworld.png");
			mostrarArea = true;
			break;
		case MCSERVER:
			desc = MonitorDePID.idioma.noRegistroDeMCServidor();
			icono = cargarIconoAyudaFlexible("/imagenes/minecraftserver.png");
			mostrarArea = true;
			break;
		default:
			icono = cargarIconoAyudaFlexible("/imagenes/registros_de_lanzar.png");
		}

		// Render HTML crudo
		descripcionHtml.setText(htmlWrap(desc));

		if (icono != null) {
			imagenLbl.setIcon(icono);
			imagenLbl.setText("");
			imagenLbl.setPreferredSize(new Dimension(icono.getIconWidth(), icono.getIconHeight()));
		} else {
			imagenLbl.setIcon(null);
			imagenLbl.setText(MonitorDePID.idioma.imagenNoEncontrada());
			imagenLbl.setPreferredSize(new Dimension(10, ALTURA_MINIMA_SIN_IMAGEN));
		}

		if (panelBajoImagen != null)
			panelBajoImagen.setVisible(mostrarBotonHMCL);

		// Mostrar/ocultar área de pegado y COMPACTAR si no se usa
		boolean estabaVisible = panelAreaTexto.isVisible();
		panelAreaTexto.setVisible(mostrarArea);

		revalidate();
		repaint();

		if (estabaVisible && !mostrarArea) {
			pack(); // compacta al ocultar el área de pegado
		}
	}

	// Acciones

	private void abrirSelectorCarpeta() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File dir = fc.getSelectedFile();
			Config.obtenerInstancia().guardarCarpetaHMCL(dir.getAbsolutePath());
		}
	}

	private void guardarRegistros() {
		String tipo = (String) selector.getSelectedItem();

		if (tipo.equals(HMCL) || tipo.equals(CURSE) || tipo.equals(BATTLY)) {
			dispose();
			return;
		} else if (tipo.equals(ENLACE_MD)) {
			// Mostrar overlay de descarga mientras se ejecuta EnlanceMD.guardar(...)
			String contenido = (areaTexto != null) ? areaTexto.getText() : "";
			ejecutarConOverlayDescarga(() -> {
				CrashDetectorLogger.log("EnlaceMD: iniciando guardado");
				EnlanceMD.guardar(contenido);
				CrashDetectorLogger.log("EnlaceMD: guardado completo");
			}, "Descargando y preparando enlaces...");
			return;
		}

		if (areaTexto == null) {
			dispose();
			return;
		}

		byte[] data = areaTexto.getText().getBytes(StandardCharsets.UTF_8);
		long sizeBytes = data.length;

		long timeoutSeconds;
		if (sizeBytes < (10L * 1024 * 1024)) {
			timeoutSeconds = 5;
		} else {
			long chunks = (sizeBytes + (50L * 1024 * 1024) - 1) / (50L * 1024 * 1024);
			timeoutSeconds = chunks * 20;
		}

		CrashDetectorLogger
				.log("Tamaño de logs = " + (sizeBytes / (1024 * 1024)) + " MB, timeout = " + timeoutSeconds + "s");

		Thread writerThread = new Thread(() -> {
			try (FileOutputStream fos = new FileOutputStream(cd_launcherlog); FileChannel ch = fos.getChannel()) {

				fos.write(data);

				CrashDetectorLogger.log("Archivo cd_launcherlog guardado");

				Consola cons = new Consola(cd_launcherlog.toPath());
				cons.finalizarContenidoInyectado(new String(data, StandardCharsets.UTF_8));
				MonitorDePID.consolas.add(cons);
				MonitorDePID.consola_de_launcher_inyectado = true;

			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		writerThread.start();

		try {
			writerThread.join(timeoutSeconds * 1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		dispose();
	}

	/**
	 * Muestra overlay modal con GIF/progreso mientras corre la tarea; cierra
	 * diálogo al terminar.
	 */
	private void ejecutarConOverlayDescarga(Runnable tarea, String mensaje) {
		final JDialog overlay = new JDialog(this, true);
		overlay.setUndecorated(true);

		JPanel box = new JPanel(new BorderLayout(10, 10));
		box.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
		box.setBackground(COLOR_FONDO);

		// Cargar GIF en español (asegúrate de incluirlo en resources):
		// resources/imagenes/descargando.gif
		ImageIcon gif = cargarGif("/imagenes/descargando.gif");

		if (gif != null) {
			JLabel gifLbl = new JLabel(gif);
			gifLbl.setOpaque(false);
			box.add(gifLbl, BorderLayout.WEST);
		} else {
			CrashDetectorLogger.log("GIF de descarga no encontrado, usando barra indeterminada");
			JProgressBar bar = new JProgressBar();
			bar.setIndeterminate(true);
			box.add(bar, BorderLayout.WEST);
		}

		JLabel msg = new JLabel(mensaje);
		msg.setForeground(COLOR_TEXTO);
		msg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		box.add(msg, BorderLayout.CENTER);

		overlay.getContentPane().add(box);
		overlay.pack();
		overlay.setLocationRelativeTo(this);

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				try {
					tarea.run();
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					JOptionPane.showMessageDialog(NoRegistroDeLauncher.this, "Error: " + t.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				return null;
			}

			@Override
			protected void done() {
				overlay.dispose();
				NoRegistroDeLauncher.this.dispose();
			}
		};

		worker.execute();
		overlay.setVisible(true); // modal; se cierra en done()
	}

	// Utilidades de imágenes

	private ImageIcon cargarGif(String ruta) {
		try {
			URL url = getClass().getResource(ruta);
			return (url != null) ? new ImageIcon(url) : null;
		} catch (Throwable t) {
			return null;
		}
	}

	private ImageIcon cargarIconoAyudaFlexible(String ruta) {
		URL url = getClass().getResource(ruta);
		if (url == null) {
			CrashDetectorLogger.log("No se encontró la imagen " + ruta);
			return null;
		}
		ImageIcon original = new ImageIcon(url);
		int w = original.getIconWidth();
		int h = original.getIconHeight();
		if (w <= 0 || h <= 0)
			return original;

		double ratio = (double) w / (double) h;
		boolean casiCuadrada = (ratio >= 0.75 && ratio <= 1.33);

		int maxAncho = MAX_ANCHO_IMAGEN;
		int maxAlto = casiCuadrada ? MAX_ALTO_CASI_CUADRADA : MAX_ALTO_PANORAMICA;

		double escala = Math.min((double) maxAncho / w, (double) maxAlto / h);
		int nuevoW = (int) Math.round(w * escala);
		int nuevoH = (int) Math.round(h * escala);

		Image img = original.getImage().getScaledInstance(nuevoW, nuevoH, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

	/** Escalado genérico (logo). */
	private ImageIcon cargarIconoEncajado(String ruta, int maxAncho, int maxAlto, boolean noAmpliar) {
		URL url = getClass().getResource(ruta);
		if (url == null) {
			CrashDetectorLogger.log("No se encontró la imagen " + ruta);
			return null;
		}
		ImageIcon original = new ImageIcon(url);
		int w = original.getIconWidth();
		int h = original.getIconHeight();
		if (w <= 0 || h <= 0)
			return original;

		double escalaW = (double) maxAncho / w;
		double escalaH = (double) maxAlto / h;
		double escala = Math.min(escalaW, escalaH);
		if (noAmpliar && escala > 1.0)
			escala = 1.0;

		int nuevoW = (int) Math.round(w * escala);
		int nuevoH = (int) Math.round(h * escala);
		Image escalada = original.getImage().getScaledInstance(nuevoW, nuevoH, Image.SCALE_SMOOTH);
		return new ImageIcon(escalada);
	}

	// Estilo y utilidades

	private TitledBorder bordeTitulado(String titulo) {
		TitledBorder b = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_BOTON.darker(), 1),
				titulo);
		b.setTitleColor(COLOR_TEXTO);
		return b;
	}

	private void estilizarBoton(JButton btn) {
		estilizarBoton(btn, 10);
	}

	private void estilizarBoton(JButton btn, int paddingV) {
		if (!CrashDetectorGUI.esMac()) {
			btn.setBackground(COLOR_BOTON);
			btn.setForeground(COLOR_TEXTO);
			btn.setFocusPainted(false);
			btn.setBorder(BorderFactory.createEmptyBorder(paddingV, 18, paddingV, 18));
		} else {
			btn.setContentAreaFilled(false);
		}
		btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	}

	private void estilizarCombo(JComboBox<?> combo) {
		if (!CrashDetectorGUI.esMac()) {
			combo.setBackground(COLOR_BOTON);
			combo.setForeground(COLOR_TEXTO);
		}
		combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		combo.setPreferredSize(new Dimension(220, 32));
	}

	private Font negrita(Font base, float size) {
		return base.deriveFont(Font.BOLD, size);
	}

	/** Envuelve HTML sin escapar, aplicando colores/estilo del proyecto. */
	private String htmlWrap(String innerHtml) {
		String fg = rgb(COLOR_TEXTO);
		String bg = rgb(COLOR_CAJA);
		return "<html><body style='margin:6px; font-family: Segoe UI, sans-serif; font-size:13px; color:" + fg
				+ "; background:" + bg + ";'>" + innerHtml + "</body></html>";
	}

	private String rgb(Color c) {
		return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
	}

	private String obtenerCodigoIdioma(String nombreIdioma) {
		switch (nombreIdioma) {
		case "Español":
			return "es";
		case "English":
			return "en";
		case "العربية":
			return "ar";
		case "Português":
			return "pt";
		case "فارسی":
			return "fa";
		case "Русский":
			return "ru";
		case "简体中文":
			return "zh";
		case "Esperanto":
			return "eo";
		case "日本語":
			return "ja";
		case "한국어":
			return "ko";
		default:
			return "es";
		}
	}
}
