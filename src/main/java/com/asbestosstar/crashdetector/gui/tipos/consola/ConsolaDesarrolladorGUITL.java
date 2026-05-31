package com.asbestosstar.crashdetector.gui.tipos.consola;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro;
import com.asbestosstar.crashdetector.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetector.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sito_registro.LimteDeTasa;
import com.asbestosstar.crashdetector.api_sito_registro.NoAPIdeRegistro;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.lfpdppp.LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI;
import com.asbestosstar.crashdetector.gui.tipos.lfpdppp.LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos;
import com.asbestosstar.crashdetector.lanzer.CDLauncher;

/**
 * Consola del desarrollador estilo TL.
 */
public class ConsolaDesarrolladorGUITL extends ConsolaDesarrolladorGUI {

	public static String ID = "consola_dev_tl";

	private JTextArea area;
	private JScrollPane scroll;
	private JPanel barra;

	private JButton bajar;
	private JButton logs;
	private JButton stop;

	private boolean autoScroll = true;
	private boolean consentimientoTemporal = false;

	private ConfigColor fondo = ConfigColor.de("consola.dev.fondo", java.awt.Color.BLACK);
	private ConfigColor texto = ConfigColor.de("consola.dev.texto", java.awt.Color.WHITE);
	private ConfigColor barraInferior = ConfigColor.de("consola.dev.barra", java.awt.Color.decode("#404040"));

	private static final int TAMANO_ICONO_BOTON = 32;

	private static final String ICONO_BAJAR = "imagenes/consola_bajar.png";
	private static final String ICONO_LOGS = "imagenes/consola_logs.png";
	private static final String ICONO_STOP = "imagenes/consola_stop.png";

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {

		setTitle(MonitorDePID.idioma.consolaDesarrollo());
		setSize(900, 600);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		// Si el usuario cierra la consola, deshabilitarla automáticamente
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {

				// apagar consola munidial al cerrar
				ConfigMundial.obtenerInstancia().guardarConsolaDesarrollo(false);

				// limpiar referencia runtime si existe
				// if (MonitorDePID.consola_des == ConsolaDesarrolladorGUITL.this) {
				MonitorDePID.consola_des = null;
				// }
			}
		});

		// Área principal (más grande como TL)
		area = new JTextArea();
		area.setEditable(false);
		area.setBackground(fondo.obtener());
		area.setForeground(texto.obtener());
		area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

		scroll = new JScrollPane(area);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);

		// Detectar si el usuario se aleja del fondo
		scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				JScrollBar sb = scroll.getVerticalScrollBar();
				int max = sb.getMaximum();
				int extent = sb.getModel().getExtent();
				int value = sb.getValue();

				// margen pequeño
				autoScroll = value + extent + 4 >= max;
			}
		});

		// Barra inferior TL
		barra = new JPanel();
		barra.setLayout(new BoxLayout(barra, BoxLayout.X_AXIS));
		barra.setBackground(barraInferior.obtener());
		barra.setPreferredSize(new Dimension(10, 46));

		bajar = new JButton("⬇");
		logs = new JButton("§");
		stop = new JButton("■");

		bajar = crearBotonIcono(ICONO_BAJAR, "⬇");
		logs = crearBotonIcono(ICONO_LOGS, "§");
		stop = crearBotonIcono(ICONO_STOP, "■");

		for (JButton b : new JButton[] { bajar, logs, stop }) {
			estilizarBotonInferior(b);
		}

		barra.add(Box.createHorizontalGlue());
		barra.add(bajar);
		barra.add(Box.createHorizontalStrut(8));
		barra.add(logs);
		barra.add(Box.createHorizontalStrut(8));
		barra.add(stop);
		barra.add(Box.createHorizontalStrut(8));

		add(barra, BorderLayout.SOUTH);

		// Scroll manual al fondo
		bajar.addActionListener(e -> {
			autoScroll = true;
			area.setCaretPosition(area.getDocument().getLength());
		});

		// Compartir logs
		logs.addActionListener(e -> compartirLogs());

		// Matar PID (Java 8 compatible)
		stop.addActionListener(e -> {

			// 1) Prefer CDLauncher process if present
			Process p = CDLauncher.proceso_cdlauncher;
			if (p != null) {
				try {
					if (p.isAlive()) {
						p.destroy();
						CrashDetectorLogger.enviarALaConsola("[ConsolaDev] Proceso CDLauncher terminado");
						return;
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			// 2) Fallback: matar por PID (si existe)
			long pid = MonitorDePID.pid;
			if (pid <= 0) {
				CrashDetectorLogger.enviarALaConsola("[ConsolaDev] No hay proceso activo para detener");
				return;
			}

			try {
				String os = System.getProperty("os.name").toLowerCase();
				if (os.contains("win")) {
					Runtime.getRuntime().exec("taskkill /PID " + pid + " /F");
				} else {
					Runtime.getRuntime().exec("kill -9 " + pid);
				}
				CrashDetectorLogger.enviarALaConsola("[ConsolaDev] Proceso PID " + pid + " terminado");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		// Menú contextual
		JPopupMenu menu = new JPopupMenu();

		menu.add(crearItem(MonitorDePID.idioma.copiarSeleccion(), () -> area.copy()));
		menu.add(crearItem(MonitorDePID.idioma.seleccionarTodo(), () -> area.selectAll()));
		menu.add(crearItem(MonitorDePID.idioma.copiarTodo(), () -> {
			area.selectAll();
			area.copy();
		}));
		menu.add(crearItem(MonitorDePID.idioma.guardarTodoComoArchivo(), this::guardarComoArchivo));
		menu.add(crearItem(MonitorDePID.idioma.obtenerEnlaceSoporte(), this::compartirLogs));
		menu.add(crearItem(MonitorDePID.idioma.borrarTodo(), () -> area.setText("")));

		area.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					menu.show(area, e.getX(), e.getY());
				}
			}
		});

		setVisible(true);
	}

	private JButton crearBotonIcono(String rutaRelativa, String textoAccesible) {
		JButton boton = new JButton();

		ImageIcon icono = cargarIcono32(rutaRelativa);

		if (icono.getIconWidth() > 0) {
			boton.setIcon(icono);
			boton.setText("");
		} else {
			boton.setText(textoAccesible);
		}

		boton.setToolTipText(textoAccesible);
		boton.getAccessibleContext().setAccessibleName(textoAccesible);
		boton.setPreferredSize(new Dimension(46, 46));
		boton.setMinimumSize(new Dimension(46, 46));
		boton.setMaximumSize(new Dimension(46, 46));

		return boton;
	}

	private ImageIcon cargarIcono32(String rutaRelativa) {
		ImageIcon icono = cargarIconoConFallback(Statics.carpeta.resolve(rutaRelativa).toString(),
				"/mnt/data/" + nombreArchivo(rutaRelativa));

		if (icono.getIconWidth() <= 0) {
			return icono;
		}

		Image imagen = icono.getImage().getScaledInstance(TAMANO_ICONO_BOTON, TAMANO_ICONO_BOTON, Image.SCALE_SMOOTH);
		return new ImageIcon(imagen);
	}

	private ImageIcon cargarIconoConFallback(String... rutas) {
		for (String ruta : rutas) {
			if (ruta == null || ruta.trim().isEmpty()) {
				continue;
			}

			ImageIcon icono = new ImageIcon(ruta);

			if (icono.getIconWidth() > 0) {
				return icono;
			}
		}

		return new ImageIcon();
	}

	private String nombreArchivo(String ruta) {
		int slash = ruta.lastIndexOf('/');
		int backslash = ruta.lastIndexOf('\\');
		int indice = Math.max(slash, backslash);

		if (indice >= 0 && indice + 1 < ruta.length()) {
			return ruta.substring(indice + 1);
		}

		return ruta;
	}

	private void estilizarBotonInferior(JButton boton) {
		if (boton == null) {
			return;
		}

		boton.setFocusPainted(false);
		boton.setBackground(barraInferior.obtener());
		boton.setForeground(texto.obtener());
		boton.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		boton.setOpaque(true);
		boton.setContentAreaFilled(true);
		boton.setBorderPainted(false);
	}

	private JMenuItem crearItem(String nombre, Runnable accion) {
		JMenuItem it = new JMenuItem(nombre);
		it.addActionListener(e -> accion.run());
		return it;
	}

	@Override
	public void agregarLinea(String linea) {
		if (area != null) {
			area.append(linea + "\n");
			if (autoScroll) {
				area.setCaretPosition(area.getDocument().getLength());
			}
		}
	}

	private void compartirLogs() {

		try {

			ConfigMundial cfg = ConfigMundial.obtenerInstancia();

			if (!cfg.obtenerConsentimientoLFPDPPP() && !consentimientoTemporal) {

				LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI gui = TipoGUI.LFPDPPP
						.obtenerGUIPredeterminado(
								LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos.ID,
								() -> new LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos());

				gui.setDespuesDeAceptar(() -> {

					// permanente ya fue guardado por el dialogo si marcaron checkbox
					consentimientoTemporal = true;

					SwingUtilities.invokeLater(() -> compartirLogs());
				});

				gui.init();
				return;
			}

			if (area == null)
				return;

			String contenido = area.getText();
			if (contenido == null || contenido.trim().isEmpty())
				return;

			// Consola virtual temporal
			java.nio.file.Path tmp = Files.createTempFile("devconsole-", ".log");

			Consola consola = new Consola(tmp);
			consola.finalizarContenidoInyectado(contenido);

			APIdeSitioDeRegistro api = APIdeSitioDeRegistro.obtenerAPIdeConfig();

			List<String> urls = api.publicarRegistroEnPartes(consola);

			if (urls == null || urls.isEmpty())
				return;

			String enlace = urls.get(0);
			MonitorDePID.enlace = enlace;

			// abrir o copiar
			try {
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().browse(new URL(enlace).toURI());
				} else {
					copiar(enlace);
					JOptionPane.showMessageDialog(this, MonitorDePID.idioma.copiadoAlPortapapeles());
				}
			} catch (Exception ex) {
				copiar(enlace);
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.copiadoAlPortapapeles());
			}

		} catch (DemasiadoGrande e) {

			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.registroDemasiadoGrande());

		} catch (ErrorConPublicar e) {

			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.errorConPublicarRegistro(e.problema));

		} catch (NoAPIdeRegistro e) {

			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.apiDeRegistroNoExiste());

		} catch (LimteDeTasa e) {

			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.limite_de_solicitudes());

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private void guardarComoArchivo() {
		try {
			JFileChooser fc = new JFileChooser();
			if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				Files.write(f.toPath(), area.getText().getBytes("UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void recargarApariencia() {

		if (area != null) {
			area.setBackground(fondo.obtener());
			area.setForeground(texto.obtener());
		}

		if (scroll != null) {
			scroll.getViewport().setBackground(fondo.obtener());
		}

		if (barra != null) {
			barra.setBackground(barraInferior.obtener());
		}

		for (JButton b : new JButton[] { bajar, logs, stop }) {
			estilizarBotonInferior(b);
		}

		revalidate();
		repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {

		ArrayList<ElementoConfig> ret = new ArrayList<>();

		fondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		texto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		barraInferior.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPanel());

		ret.add(fondo);
		ret.add(texto);
		ret.add(barraInferior);

		return ret;
	}
}
