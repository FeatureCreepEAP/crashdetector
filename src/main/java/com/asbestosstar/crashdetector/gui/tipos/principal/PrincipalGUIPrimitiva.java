package com.asbestosstar.crashdetector.gui.tipos.principal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.compartir.DialogoCompartirPrimitiva;

/**
 * GUI principal primitiva.
 *
 * Esta clase no construye una ventana principal real. Solo abre el diálogo
 * primitivo para compartir logs y muestra popups separados para las
 * verificaciones activadas.
 */
public class PrincipalGUIPrimitiva extends PrincipalGUI {

	public static String ID = "primitiva";

	private boolean yaEjecutado = false;

	private ConfigColor colorFondoPopup = ConfigColor.de("gui.primitiva.popup.fondo",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorFondo()));

	private ConfigColor colorTextoPopup = ConfigColor.de("gui.primitiva.popup.texto",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));

	private ConfigColor colorBotonPopup = ConfigColor.de("gui.primitiva.popup.boton",
			Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void constructir(Instant utc, CountDownLatch latch) {
		this.tiempoFallo = utc;
		this.cerrojo = latch;

		MonitorDePID.gui_principal_activo = this;

		ejecutarFlujoPrimitivo();
	}

	@Override
	public void init() {
		this.tiempoFallo = Instant.now();
		ejecutarFlujoPrimitivo();
	}

	private void ejecutarFlujoPrimitivo() {
		if (yaEjecutado)
			return;

		yaEjecutado = true;

		SwingUtilities.invokeLater(() -> {
			try {
				abrirDialogoCompartirPrimitiva();
				crearPopups();
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		});
	}

	/**
	 * Muestra un popup separado por cada verificación activada. Los enlaces HTML
	 * dentro del contenido se abren usando abrirURL(String url).
	 */
	public void crearPopups() {
		for (Verificaciones ver : obtenerPopups()) {
			if (ver == null)
				continue;

			String contenido = ver.comoString();

			if (contenido == null || contenido.trim().isEmpty())
				continue;

			SwingUtilities.invokeLater(() -> crearPopupVerificacion(contenido));
		}
	}

	public void crearPopupVerificacion(String contenido) {
		final JDialog dialogo = new JDialog();
		dialogo.setTitle("");
		dialogo.setModal(false);
		dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		Rectangle pantalla = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

		int anchoMaximo = Math.max(420, Math.min(820, pantalla.width - 160));
		int altoMaximo = Math.max(220, Math.min(520, pantalla.height - 160));
		int anchoContenido = anchoMaximo - 90;

		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		panel.setBackground(colorFondoPopup.obtener());

		JEditorPane editor = new JEditorPane();
		editor.setContentType("text/html");
		editor.setEditable(false);
		editor.setOpaque(true);
		editor.setBackground(colorFondoPopup.obtener());
		editor.setForeground(colorTextoPopup.obtener());
		editor.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

		String html = "<html><body style='" + "width:" + anchoContenido + "px;" + "color:"
				+ colorHex(colorTextoPopup.obtener()) + ";" + "background:" + colorHex(colorFondoPopup.obtener()) + ";"
				+ "font-family:Segoe UI, Arial, sans-serif;" + "font-size:12px;" + "margin:0;" + "padding:0;" + "'>"
				+ contenido + "</body></html>";

		editor.setText(html);

		editor.addHyperlinkListener(e -> {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED && e.getURL() != null) {
				try {
					PrincipalGUIPrimitiva.this.abrirURL(e.getURL().toString());
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
				}
			}
		});

		// Forzar ancho antes de medir altura real.
		editor.setSize(new Dimension(anchoContenido, Short.MAX_VALUE));
		Dimension tamanoPreferido = editor.getPreferredSize();

		int anchoScroll = Math.min(anchoMaximo, Math.max(420, tamanoPreferido.width + 70));
		int altoScroll = Math.min(altoMaximo, Math.max(120, tamanoPreferido.height + 12));

		JScrollPane scroll = new JScrollPane(editor);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		scroll.setPreferredSize(new Dimension(anchoScroll, altoScroll));
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(
				tamanoPreferido.height + 12 > altoMaximo ? ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
						: ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		JButton cerrar = new JButton(MonitorDePID.idioma.cerrar());
		cerrar.addActionListener(e -> dialogo.dispose());
		estilizarBotonPopup(cerrar);

		JPanel panelBoton = new JPanel(new BorderLayout(0, 0));
		panelBoton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		panelBoton.setBackground(colorFondoPopup.obtener());
		panelBoton.add(cerrar, BorderLayout.CENTER);

		panel.add(scroll, BorderLayout.CENTER);
		panel.add(panelBoton, BorderLayout.SOUTH);

		dialogo.add(panel);
		dialogo.pack();
		dialogo.setLocationRelativeTo(null);
		dialogo.setVisible(true);
	}

	private void estilizarBotonPopup(JButton boton) {
		boton.setFocusPainted(false);
		boton.setBackground(colorBotonPopup.obtener());
		boton.setForeground(colorTextoPopup.obtener());
		boton.setContentAreaFilled(true);
		boton.setOpaque(true);
		boton.setBorder(BorderFactory.createLineBorder(colorTextoPopup.obtener(), 1));
	}

	private String colorHex(Color color) {
		if (color == null)
			return "#000000";

		return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
	}

	public void crerarPopups() {
		crearPopups();
	}

	public Set<Verificaciones> obtenerPopups() {
		return MonitorDePID.analizador.obtenerActivados();
	}

	private void abrirDialogoCompartirPrimitiva() {
		DialogoCompartirPrimitiva dialogo = new DialogoCompartirPrimitiva();
		dialogo.preperar(tiempoFallo != null ? tiempoFallo : Instant.now());
	}

	@Override
	public void inicializarInterfaz() {
		ejecutarFlujoPrimitivo();
	}

	@Override
	public void actualizarTextoBotonLauncherParaCDLauncher() {
		// No hay botón launcher en la GUI primitiva.
	}

	@Override
	public void aplicarColoresAnalizador() {
		// No hay interfaz visual principal.
	}

	@Override
	public void aplicarColoresLanzer() {
		// No hay interfaz visual principal.
	}

	@Override
	public void aplicarContenidoDeLaPantallaAnalizador() {
		// No hay pantalla principal.
	}

	@Override
	public void aplicarContenidoDeLaPantallaLanzer() {
		// No hay pantalla principal.
	}

	@Override
	public void recargarApariencia() {
		// Los popups leen colores desde ConfigColor al crearse.
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();

		colorFondoPopup.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorTextoPopup.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBotonPopup.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());

		elementos.add(colorFondoPopup);
		elementos.add(colorTextoPopup);
		elementos.add(colorBotonPopup);

		return elementos;
	}

	@Override
	public TipoGUI<PrincipalGUI> tipo() {
		return TipoGUI.PRINCIPAL;
	}
}