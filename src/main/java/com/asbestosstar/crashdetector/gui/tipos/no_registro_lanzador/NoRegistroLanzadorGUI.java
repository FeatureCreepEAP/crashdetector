package com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextArea;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.EnlanceMD;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

@Deprecated
/**
 * Clase abstracta que define la funcionalidad base para la GUI de no registro
 * de lanzador. La apariencia, el layout y la creación de componentes se manejan
 * exclusivamente en implementaciones concretas.
 */
public abstract class NoRegistroLanzadorGUI extends JDialog implements CrashDetectorGUI {

	private static final long serialVersionUID = 1L;

	// Mantener registro de GUIs
	public static Map<String, Supplier<NoRegistroLanzadorGUI>> GUIS = new HashMap<>();

	// Opciones de selector (etiquetas mostradas)
	public static final String GEN = "Genérico";
	public static final String CURSE = "CurseForge";
	public static final String PRISM = "Prism/MultiMC+++";
	public static final String HMCL = "HMCL";
	public static final String FENIX = "Fénix";
	public static final String ATL = "ATLauncher";
	public static final String GD = "GDLauncher";
	public static final String BATTLY = "Battly Launcher";
	public static final String NIGHTWORLD = "Nightworld";
	public static final String MCSERVER = "Servidor de Minecraft";
	public static final String ENLACE_MD = "Enlace MD";

	// Escalado imagen de ayuda (técnico)
	public static final int MAX_ANCHO_IMAGEN = 500;
	public static final int MAX_ALTO_CASI_CUADRADA = 220;
	public static final int MAX_ALTO_PANORAMICA = 120;
	public static final int ALTURA_MINIMA_SIN_IMAGEN = 120;

	public boolean building = false;

	// Controles (ahora públicos)
	public JComboBox<String> selector;
	public JComboBox<String> comboBoxIdioma;

	public final JLabel imagenLbl = new JLabel("", SwingConstants.CENTER);
	public final JLabel vshojoLbl = new JLabel("", SwingConstants.CENTER);
	public final JButton seleccionarCarpetaBtn = new JButton(MonitorDePID.idioma.seleccionarCarpeta());

	public JEditorPane descripcionHtml;
	public JScrollPane descScroll;

	public TextArea areaTexto;

	public JButton botonGuardar;
	public JButton botonOmitir;
	public JButton botonProxy;

	public JPanel panelAreaTexto;
	public JPanel panelBajoImagen;

	// **** Paneles estructurales (ahora públicos) ****
	public JPanel raizPanel;
	public JPanel encabezadoPanel;
	public JPanel centroPanel;
	public JPanel panelImagenYBoton;
	public JPanel piePanel;
	public JPanel botonesPanel;

	public static File cd_launcherlog = new File("cd_launcherlog");
	public Instant instant;

	/**
	 * Prepara y construye la UI. Este método es abstracto para forzar a las
	 * implementaciones concretas a definir el layout y la apariencia.
	 * 
	 * @param blanco  frame propietario (puede ser null)
	 * @param instant instante de creación/uso
	 */
	public abstract void preparar(JFrame blanco, Instant instant);

	/**
	 * Cambia imagen/controles según launcher y compacta si no hay área de pegado.
	 */
	public void refrescarInterfaz() {
		String tipo = (String) selector.getSelectedItem();

		String desc = MonitorDePID.idioma.noRegistroDeLauncher(); // por defecto
		ImageIcon icono = null;
		boolean mostrarArea = true;
		boolean mostrarBotonHMCL = false;

		if (HMCL.equals(tipo)) {
			desc = MonitorDePID.idioma.descripcionHMCL();
		} else if (GEN.equals(tipo)) {
			desc = MonitorDePID.idioma.noRegistroDeLauncher();
		}

		if (CURSE.equals(tipo)) {
			desc = MonitorDePID.idioma.descripcionCurseforge();
			icono = cargarIconoAyudaFlexible("/imagenes/cfskiplauncher.png");
			mostrarArea = false;
		} else if (PRISM.equals(tipo)) {
			desc = MonitorDePID.idioma.descripcionPrism();
			icono = cargarIconoAyudaFlexible("/imagenes/registros_de_launcher_prism.png");
		} else if (HMCL.equals(tipo)) {
			icono = cargarIconoAyudaFlexible("/imagenes/hcml.png");
			mostrarArea = false;
			mostrarBotonHMCL = true;
		} else if (FENIX.equals(tipo)) {
			desc = MonitorDePID.idioma.descripcionFenix();
			icono = cargarIconoAyudaFlexible("/imagenes/fenix.png");
		} else if (ATL.equals(tipo)) {
			desc = MonitorDePID.idioma.descripcionATLauncher();
			icono = cargarIconoAyudaFlexible("/imagenes/atlogs.png");
		} else if (GD.equals(tipo)) {
			desc = MonitorDePID.idioma.descripcionGDLauncher();
			icono = cargarIconoAyudaFlexible("/imagenes/gdconsola.png");
		} else if (ENLACE_MD.equals(tipo)) {
			desc = MonitorDePID.idioma.descripcionLinksMarkdown();
			icono = null;
		} else if (BATTLY.equals(tipo)) {
			desc = MonitorDePID.idioma.noRegistroDeBattly();
			icono = cargarIconoAyudaFlexible("/imagenes/battly.png");
			mostrarArea = false;
		} else if (NIGHTWORLD.equals(tipo)) {
			desc = MonitorDePID.idioma.noRegistroDeNightWorld();
			icono = cargarIconoAyudaFlexible("/imagenes/nightworld.png");
			mostrarArea = true;
		} else if (MCSERVER.equals(tipo)) {
			desc = MonitorDePID.idioma.noRegistroDeMCServidor();
			icono = cargarIconoAyudaFlexible("/imagenes/minecraftserver.png");
			mostrarArea = true;
		} else {
			icono = cargarIconoAyudaFlexible("/imagenes/registros_de_lanzar.png");
		}

		// Render HTML crudo
		if (descripcionHtml != null) {
			descripcionHtml.setText(htmlWrap(desc));
		}

		if (imagenLbl != null) {
			if (icono != null) {
				imagenLbl.setIcon(icono);
				imagenLbl.setText("");
				imagenLbl.setPreferredSize(new Dimension(icono.getIconWidth(), icono.getIconHeight()));
			} else {
				imagenLbl.setIcon(null);
				imagenLbl.setText(MonitorDePID.idioma.imagenNoEncontrada());
				imagenLbl.setPreferredSize(new Dimension(10, ALTURA_MINIMA_SIN_IMAGEN));
			}
		}

		if (panelBajoImagen != null) {
			panelBajoImagen.setVisible(mostrarBotonHMCL);
		}

		if (panelAreaTexto != null) {
			boolean estabaVisible = panelAreaTexto.isVisible();
			panelAreaTexto.setVisible(mostrarArea);

			if (estabaVisible && !mostrarArea) {
				pack(); // compacta al ocultar el área
			}
		}

		revalidate();
		repaint();
	}

	protected abstract String htmlWrap(String desc);

	/** Detecta el launcher a partir de la ruta actual (ruta y padres). */
	public String detectarPorDirectorio() {
		String rutaAbs = new File(System.getProperty("user.dir")).getAbsolutePath().toLowerCase();

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
		return GEN;
	}

	public boolean contiene(String s, String... tokens) {
		for (int i = 0; i < tokens.length; i++)
			if (s.contains(tokens[i]))
				return true;
		return false;
	}

	// Acciones

	public void abrirSelectorCarpeta() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File dir = fc.getSelectedFile();
			Config.obtenerInstancia().guardarCarpetaHMCL(dir.getAbsolutePath());
		}
	}

	public void guardarRegistros() {
		String tipo = (String) selector.getSelectedItem();

		if (HMCL.equals(tipo) || CURSE.equals(tipo) || BATTLY.equals(tipo)) {
			dispose();
			return;
		} else if (ENLACE_MD.equals(tipo)) {
			String contenido = (areaTexto != null) ? areaTexto.getText() : "";
			ejecutarConOverlayDescarga(new Runnable() {
				@Override
				public void run() {
					CrashDetectorLogger.log("EnlaceMD: iniciando guardado");
					EnlanceMD.guardar(contenido);
					CrashDetectorLogger.log("EnlaceMD: guardado completo");
				}
			}, overlayMensaje());
			return;
		}

		if (areaTexto == null) {
			dispose();
			return;
		}

		final byte[] data = areaTexto.getText().getBytes(StandardCharsets.UTF_8);
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

		Thread writerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try (FileOutputStream fos = new FileOutputStream(cd_launcherlog); FileChannel ch = fos.getChannel()) {
					fos.write(data);
					CrashDetectorLogger.log("Archivo cd_launcherlog guardado");

					Consola cons = new Consola(cd_launcherlog.toPath());
					cons.finalizarContenidoInyectado(new String(data, StandardCharsets.UTF_8));
					MonitorDePID.consolas.add(cons);
					MonitorDePID.consola_de_launcher_inyectado = true;

				} catch (IOException ex) {
					JOptionPane.showMessageDialog(NoRegistroLanzadorGUI.this, "Error: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
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

	/** Overlay modal con GIF o barra indeterminada; cierra diálogo al terminar. */
	public void ejecutarConOverlayDescarga(final Runnable tarea, String mensaje) {
		final JDialog overlay = new JDialog(this, true);
		overlay.setUndecorated(true);

		JPanel box = new JPanel(new BorderLayout(10, 10));
		box.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

		ImageIcon gif = cargarGif(gifDescargaPath());
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
					JOptionPane.showMessageDialog(NoRegistroLanzadorGUI.this, "Error: " + t.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				return null;
			}

			@Override
			protected void done() {
				overlay.dispose();
				NoRegistroLanzadorGUI.this.dispose();
			}
		};

		worker.execute();
		overlay.setVisible(true);
	}

	// ====== Hooks de apariencia ======

	public abstract void aplicarApariencia();

	public String overlayMensaje() {
		return "Descargando y preparando enlaces...";
	}

	public String gifDescargaPath() {
		return "/imagenes/descargando.gif";
	}

	public Font negrita(Font base, float size) {
		return base.deriveFont(Font.BOLD, size);
	}

	public String rgb(Color c) {
		return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
	}

	// Utilidades de imágenes

	public ImageIcon cargarGif(String ruta) {
		try {
			URL url = getClass().getResource(ruta);
			return (url != null) ? new ImageIcon(url) : null;
		} catch (Throwable t) {
			return null;
		}
	}

	public ImageIcon cargarIconoAyudaFlexible(String ruta) {
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
	public ImageIcon cargarIconoEncajado(String ruta, int maxAncho, int maxAlto, boolean noAmpliar) {
		File archivo = Statics.carpeta.resolve(ruta).toFile();
		if (archivo == null) {
			CrashDetectorLogger.log("No se encontró la imagen " + ruta);
			return null;
		}
		ImageIcon original = new ImageIcon(archivo.getAbsolutePath());
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

	public void actualizarTextos() {
		setTitle(Config.obtenerInstancia().obtenerNombreCD() + " – " + MonitorDePID.idioma.noRegistroLauncherTitulo());
		if (descripcionHtml != null) {
			descripcionHtml.setText(htmlWrap(MonitorDePID.idioma.noRegistroDeLauncher()));
		}
		if (seleccionarCarpetaBtn != null) {
			seleccionarCarpetaBtn.setText(MonitorDePID.idioma.seleccionarCarpeta());
		}
		if (botonGuardar != null) {
			botonGuardar.setText(MonitorDePID.idioma.guardarYCerrar());
		}
		if (botonOmitir != null) {
			botonOmitir.setText(MonitorDePID.idioma.omitirYCerrar());
		}
		if (panelAreaTexto != null) {
			panelAreaTexto.setBorder(bordeTitulado(MonitorDePID.idioma.pegaLosRegistrosAqui()));
		}
		refrescarInterfaz();
	}

	protected abstract Border bordeTitulado(String pegaLosRegistrosAqui);

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
	}

	@Override
	public TipoGUI<NoRegistroLanzadorGUI> tipo() {
		return TipoGUI.NO_REGISTRO_LANZER;
	}

	@Override
	public abstract void init();


}