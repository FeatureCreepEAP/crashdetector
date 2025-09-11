package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EnlanceMD;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Diálogo para que el usuario pegue o seleccione registros según su launcher.
 */
public class NoRegistroDeLauncher extends JDialog {
	private static final String GEN = "GENERIC";
	private static final String CURSE = "CURSEDFORGE";
	private static final String PRISM = "PRISM/MultiMC+++"; // incluye MultiMC & derivados
	private static final String HMCL = "HMCL";
	private static final String FENIX = "FENIX";
	private static final String ATL = "ATLAUNCHER";
	private static final String GD = "GDLAUNCHER";
	private static final String ENLANCE_MD = "ENLANCE_MD";

	private final TextArea areaTexto; // solo visible para algunos launchers
	private final JLabel imagenLbl = new JLabel("", SwingConstants.CENTER);
	private final JButton seleccionarCarpetaBtn = new JButton(MonitorDePID.idioma.seleccionarCarpeta());
	private JTextArea descripcionTextArea; // Variable de instancia para descripción

	private final JComboBox<String> selector;
	public static File cd_launcherlog = new File("cd_launcherlog");
	public Instant instant;

	// Componente del selector de idioma
	private JComboBox<String> comboBoxIdioma;



	
	
	public NoRegistroDeLauncher(JFrame blanco, Instant instant) {
	    super(blanco, true);
	    this.instant = instant;

	    setTitle("CrashDetector – " + MonitorDePID.idioma.noRegistroLauncherTitulo());
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    pack();
	    setMinimumSize(new Dimension(900, 700)); // para que no quede demasiado pequeña
	    setLocationRelativeTo(blanco);
	    setLayout(new BorderLayout(10, 10));

	    JPanel principal = new JPanel();
	    principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));
	    add(principal, BorderLayout.CENTER);

	    // Selector de launcher
	    selector = new JComboBox<>(new String[] { GEN, CURSE, PRISM, HMCL, FENIX, ATL, GD, ENLANCE_MD });
	    selector.addActionListener(e -> refrescarInterfaz());
	    principal.add(selector);

	    // Descripción
	    descripcionTextArea = new JTextArea();
	    descripcionTextArea.setEditable(false);
	    descripcionTextArea.setLineWrap(true);
	    descripcionTextArea.setWrapStyleWord(true);
	    descripcionTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    principal.add(descripcionTextArea);

	    // Panel de imagen
	    imagenLbl.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY));
	    JPanel panelImagen = new JPanel(new BorderLayout());
	    panelImagen.add(imagenLbl, BorderLayout.CENTER);
	    panelImagen.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
	    principal.add(panelImagen);

	    // ============================
	    // NUEVO: área de texto nativa con java.awt.TextArea
	    // ============================
	    areaTexto = new java.awt.TextArea("", 15, 50, java.awt.TextArea.SCROLLBARS_BOTH);
	    areaTexto.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
	    areaTexto.setEditable(true);

	    JPanel panelWrapper = new JPanel(new BorderLayout());
	    TitledBorder border = BorderFactory.createTitledBorder(
	            BorderFactory.createEtchedBorder(),
	            MonitorDePID.idioma.pegaLosRegistrosAqui()
	    );
	    border.setTitleJustification(TitledBorder.LEFT);
	    panelWrapper.setBorder(border);
	    panelWrapper.add(areaTexto, BorderLayout.CENTER);
	    principal.add(panelWrapper);
	    // ============================

	    // Botón de carpeta (solo HMCL)
	    seleccionarCarpetaBtn.addActionListener(ev -> abrirSelectorCarpeta());
	    seleccionarCarpetaBtn.setAlignmentX(LEFT_ALIGNMENT);
	    principal.add(seleccionarCarpetaBtn);

	    // ========== SELECTOR DE IDIOMA ==========
	    JPanel panelIdioma = new JPanel(new BorderLayout(5, 5));
	    panelIdioma.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

	    JLabel iconoIdioma = new JLabel("🌐");
	    iconoIdioma.setFont(new java.awt.Font("Segoe UI Emoji", java.awt.Font.PLAIN, 20));

	    String[] idiomas = { "Español", "English", "العربية", "Português", "فارسی", "Русский", "简体中文", "Esperanto", "日本語", "한국어" };

	    comboBoxIdioma = new JComboBox<>(idiomas);

	    String codigoIdiomaActual = MonitorDePID.idioma.codigo();
	    switch (codigoIdiomaActual) {
	        case "es": comboBoxIdioma.setSelectedItem("Español"); break;
	        case "en": comboBoxIdioma.setSelectedItem("English"); break;
	        case "ar": comboBoxIdioma.setSelectedItem("العربية"); break;
	        case "pt": comboBoxIdioma.setSelectedItem("Português"); break;
	        case "fa": comboBoxIdioma.setSelectedItem("فارسی"); break;
	        case "ru": comboBoxIdioma.setSelectedItem("Русский"); break;
	        case "zh": comboBoxIdioma.setSelectedItem("简体中文"); break;
	        case "eo": comboBoxIdioma.setSelectedItem("Esperanto"); break;
	        case "ja": comboBoxIdioma.setSelectedItem("日本語"); break;
	        case "ko": comboBoxIdioma.setSelectedItem("한국어"); break;
	        default: comboBoxIdioma.setSelectedItem("Español");
	    }

	    comboBoxIdioma.addActionListener(e -> {
	        String seleccion = (String) comboBoxIdioma.getSelectedItem();
	        String codigoIdioma = obtenerCodigoIdioma(seleccion);

	        if (codigoIdioma != null) {
	            Idioma.archivo.getParentFile().mkdirs();
	            try (java.io.BufferedWriter escritor = java.nio.file.Files.newBufferedWriter(
	                    Idioma.archivo.toPath(),
	                    java.nio.file.StandardOpenOption.CREATE,
	                    java.nio.file.StandardOpenOption.TRUNCATE_EXISTING)) {
	                escritor.write(codigoIdioma);
	            } catch (IOException ex) {
	                CrashDetectorLogger.logException(ex);
	            }
	        }
	        MonitorDePID.idioma = Idioma.detectar();
	        actualizarTextos();
	    });

	    panelIdioma.add(iconoIdioma, BorderLayout.WEST);
	    panelIdioma.add(comboBoxIdioma, BorderLayout.CENTER);
	    principal.add(panelIdioma, BorderLayout.SOUTH);
	    // ========== FIN SELECTOR DE IDIOMA ==========

	    // Botones de guardar/omitir
	    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	    JButton proxyBtn = new JButton("ProxySysOutSysErr");
	    proxyBtn.addActionListener(ev -> {
	        String mensajeConfirmacion = MonitorDePID.idioma.habilitarProxySysOutSysErrMensaje();
	        int respuesta = JOptionPane.showConfirmDialog(NoRegistroDeLauncher.this, mensajeConfirmacion,
	                MonitorDePID.idioma.confirmacionTitulo(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

	        if (respuesta == JOptionPane.YES_OPTION) {
	            Config.obtenerInstancia().guardarProxySysOutSysErr(true);
	            JOptionPane.showMessageDialog(NoRegistroDeLauncher.this, MonitorDePID.idioma.proxyHabilitadoMensaje(),
	                    MonitorDePID.idioma.informacionTitulo(), JOptionPane.INFORMATION_MESSAGE);
	        }
	    });

	    JButton botonGuardar = new JButton(MonitorDePID.idioma.guardarYCerrar());
	    JButton botonOmitir = new JButton(MonitorDePID.idioma.omitirYCerrar());
	    botonGuardar.addActionListener(e -> guardarRegistros());
	    botonOmitir.addActionListener(e -> dispose());

	    panelBotones.add(botonGuardar);
	    panelBotones.add(botonOmitir);
	    panelBotones.add(proxyBtn);
	    principal.add(panelBotones);

	    // Estado inicial
	    refrescarInterfaz();

	    pack();
	    setVisible(true);
	}



	/**
	 * Actualiza todos los elementos de texto en el diálogo para reflejar el idioma
	 * actual
	 */
	private void actualizarTextos() {
		// Actualizar título del diálogo
		setTitle("CrashDetector – " + MonitorDePID.idioma.noRegistroLauncherTitulo());

		// Actualizar texto de descripción
		descripcionTextArea.setText(MonitorDePID.idioma.noRegistroDeLauncher());

		// Actualizar textos de botones
		((JButton) ((JPanel) getContentPane().getComponent(0)).getComponent(2))
				.setText(MonitorDePID.idioma.seleccionarCarpeta());
		((JButton) ((JPanel) getContentPane().getComponent(0)).getComponent(4))
				.setText(MonitorDePID.idioma.guardarYCerrar());
		((JButton) ((JPanel) getContentPane().getComponent(0)).getComponent(5))
				.setText(MonitorDePID.idioma.omitirYCerrar());

		// Actualizar borde del panel de desplazamiento
		JScrollPane scrollPane = (JScrollPane) ((JPanel) getContentPane().getComponent(0)).getComponent(3);
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				MonitorDePID.idioma.pegaLosRegistrosAqui());
		border.setTitleJustification(TitledBorder.LEFT);
		scrollPane.setBorder(border);

		// Refrescar interfaz basado en la selección actual del launcher
		refrescarInterfaz();
	}

	private void refrescarInterfaz() {
		String tipo = (String) selector.getSelectedItem();
		String desc = MonitorDePID.idioma.noRegistroDeLauncher(); // Default
		ImageIcon icono = null;
		areaTexto.setVisible(true);
		seleccionarCarpetaBtn.setVisible(false);

		switch (tipo) {
		case CURSE:
			desc = MonitorDePID.idioma.descripcionCurseforge();
			icono = cargarIcono("/imagenes/cfskiplauncher.png");
			areaTexto.setVisible(false);
			break;
		case PRISM:
			desc = MonitorDePID.idioma.descripcionPrism();
			icono = cargarIcono("/imagenes/registros_de_launcher_prism.png");
			break;
		case HMCL:
			desc = MonitorDePID.idioma.descripcionHMCL();
			icono = cargarIcono("/imagenes/hcml.png");
			areaTexto.setVisible(false);
			seleccionarCarpetaBtn.setVisible(true);
			break;
		case FENIX:
			desc = MonitorDePID.idioma.descripcionFenix();
			icono = cargarIcono("/imagenes/fenix.png");
			break;
		case ATL:
			desc = MonitorDePID.idioma.descripcionATLauncher();
			icono = cargarIcono("/imagenes/atlogs.png");
			break;
		case GD:
			desc = MonitorDePID.idioma.descripcionGDLauncher();
			icono = cargarIcono("/imagenes/gdconsola.png");
			break;
		case ENLANCE_MD:
			desc = MonitorDePID.idioma.descripcionLinksMarkdown();
			icono = null;
			break;
		default:
			icono = null;
		}

		// Establece el texto en el JTextArea directamente
		descripcionTextArea.setText(desc);

		// Actualiza la imagen y otros componentes
		imagenLbl.setIcon(icono);

		imagenLbl.setText(icono == null ? MonitorDePID.idioma.imagenNoEncontrada() : "");

		revalidate();

		repaint();
	}

	/** Carga y escala icono, devuelve null si no existe */
	private ImageIcon cargarIcono(String ruta) {
		URL url = getClass().getResource(ruta);
		if (url == null) {
			CrashDetectorLogger.log("No se encontró la imagen " + ruta);
			return null;
		}
		ImageIcon imagenOriginal = new ImageIcon(url);
		Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH);
		return new ImageIcon(imagenEscalada);
	}

	/** Abre diálogo de carpeta para HMCL y guarda en Config */
	private void abrirSelectorCarpeta() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File dir = fc.getSelectedFile();
			Config.obtenerInstancia().guardarCarpetaHMCL(dir.getAbsolutePath());
		}
	}

/** Guarda los registros pegados en areaTexto (si aplica) y cierra. */
private void guardarRegistros() {
    String tipo = (String) selector.getSelectedItem();

    if (tipo.equals(HMCL) || tipo.equals(CURSE)) {
        dispose();
        return;
    } else if (tipo.equals(ENLANCE_MD)) {
        if (areaTexto != null) {
            CrashDetectorLogger.log("areatexto");
            EnlanceMD.guardar(areaTexto.getText());
            CrashDetectorLogger.log("completa");
        }
        dispose();
        return;
    }

    if (areaTexto == null) {
        dispose();
        return;
    }

    // Calcular tamaño de los datos
    byte[] data = areaTexto.getText().getBytes(StandardCharsets.UTF_8);
    long sizeBytes = data.length;

    long timeoutSeconds;
    if (sizeBytes < (10L * 1024 * 1024)) {
        timeoutSeconds = 5; // máx 5s para logs pequeños
    } else {
        long chunks = (sizeBytes + (50L * 1024 * 1024) - 1) / (50L * 1024 * 1024);
        timeoutSeconds = chunks * 20;
    }

    CrashDetectorLogger.log(
        "Tamaño de logs = " + (sizeBytes / (1024 * 1024)) + " MB, timeout = " + timeoutSeconds + "s"
    );

    Thread writerThread = new Thread(() -> {
        try (FileOutputStream fos = new FileOutputStream(cd_launcherlog);
             FileChannel ch = fos.getChannel()) {

            fos.write(data);

            CrashDetectorLogger.log("Archivo cd_launcherlog guardado");

            Consola cons = new Consola(cd_launcherlog.toPath());
            cons.finalizarContenidoInyectado(new String(data, StandardCharsets.UTF_8));
            MonitorDePID.consolas.add(cons);
            MonitorDePID.consola_de_launcher_inyectado = true;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    writerThread.start();

    try {
        writerThread.join(timeoutSeconds * 1000);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }

    // Cerrar aunque no haya terminado completamente
    dispose();
}





	/**
	 * Convierte nombre de idioma a código de idioma
	 */
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

	/**
	 * Verifica si se está ejecutando en macOS
	 */
	public static boolean esMacOS() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
}