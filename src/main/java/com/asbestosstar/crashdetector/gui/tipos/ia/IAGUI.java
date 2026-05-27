package com.asbestosstar.crashdetector.gui.tipos.ia;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * GUI base para integrar una IA externa orientada al análisis de crashes.
 *
 * Esta clase contiene la funcionalidad y la estructura general. La
 * implementación concreta define colores, imágenes y apariencia.
 */
public abstract class IAGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static final Map<String, Supplier<IAGUI>> GUIS = new HashMap<String, Supplier<IAGUI>>();

	/**
	 * Enlace del agente de IA.
	 */
	public static final String ENLACE_BAIDU_AGENT = "https://mbd.baidu.com/ma/s/fAnanotA";

	/**
	 * Paneles estructurales.
	 */
	public JPanel panelRaiz;
	public JPanel panelCentro;
	public JPanel panelSuperior;
	public JPanel panelInferior;
	public JPanel panelBotones;
	public JPanel panelIlustracion;

	/**
	 * Componentes principales.
	 */
	public JLabel lblTitulo;
	public JEditorPane txtDescripcion;
	public JTextField txtEnlace;
	public JButton btnCopiar;
	public JButton btnAbrir;
	public JLabel lblAviso;

	@Override
	public TipoGUI tipo() {
		return TipoGUI.IA;
	}

	@Override
	public void init() {
		configurarVentana();
		construirInterfaz();
		aplicarApariencia();
		setVisible(true);
	}

	/**
	 * Configuración base de la ventana.
	 */
	public void configurarVentana() {
		setTitle(MonitorDePID.idioma.iaTituloVentana());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(820, 560);
		setMinimumSize(new Dimension(700, 460));
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
	}

	/**
	 * Construcción técnica de la interfaz.
	 */
	public void construirInterfaz() {
		panelRaiz = new JPanel(new BorderLayout(10, 10));
		panelRaiz.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));

		panelSuperior = new JPanel(new BorderLayout(8, 8));
		panelCentro = new JPanel(new BorderLayout(12, 12));
		panelInferior = new JPanel(new BorderLayout(8, 8));
		panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));

		lblTitulo = new JLabel(MonitorDePID.idioma.iaTituloPrincipal());
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 22f));

		txtDescripcion = new JEditorPane();
		txtDescripcion.setEditable(false);
		txtDescripcion.setContentType("text/html");
		txtDescripcion.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		txtDescripcion.setText(envolverHtml(descripcionHtml()));

		txtEnlace = new JTextField(ENLACE_BAIDU_AGENT);
		txtEnlace.setEditable(false);
		txtEnlace.setCaretPosition(0);

		btnCopiar = new JButton(MonitorDePID.idioma.iaCopiarEnlace());
		btnAbrir = new JButton(MonitorDePID.idioma.iaAbrirEnlace());

		btnCopiar.addActionListener(e -> copiarEnlace());
		btnAbrir.addActionListener(e -> abrirEnlace());

		lblAviso = new JLabel(MonitorDePID.idioma.iaAvisoCuentaBaidu());

		panelBotones.add(btnCopiar);
		panelBotones.add(btnAbrir);

		panelSuperior.add(lblTitulo, BorderLayout.NORTH);
		panelSuperior.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);

		panelInferior.add(txtEnlace, BorderLayout.NORTH);
		panelInferior.add(panelBotones, BorderLayout.CENTER);
		panelInferior.add(lblAviso, BorderLayout.SOUTH);

		panelIlustracion = crearPanelIlustracion();

		panelCentro.add(panelIlustracion, BorderLayout.WEST);
		panelCentro.add(panelInferior, BorderLayout.CENTER);

		panelRaiz.add(panelSuperior, BorderLayout.CENTER);
		panelRaiz.add(panelCentro, BorderLayout.SOUTH);

		add(panelRaiz, BorderLayout.CENTER);
	}

	/**
	 * HTML descriptivo del panel.
	 */
	public String descripcionHtml() {
		StringBuilder sb = new StringBuilder();

		sb.append("<b>").append(MonitorDePID.idioma.iaDescripcionTitulo()).append("</b>");
		sb.append("<br><br>");
		sb.append(MonitorDePID.idioma.iaDescripcionCuerpo());
		sb.append("<br><br>");
		sb.append(MonitorDePID.idioma.iaDescripcionUso());

		return sb.toString();
	}

	/**
	 * Copia el enlace al portapapeles.
	 */
	public void copiarEnlace() {
		try {
			StringSelection selection = new StringSelection(txtEnlace.getText());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
		}
	}

	/**
	 * Abre el enlace en el navegador.
	 */
	public void abrirEnlace() {
		try {
			Desktop.getDesktop().browse(new URI(txtEnlace.getText()));
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
		}
	}

	/**
	 * Envuelve texto HTML básico.
	 */
	public String envolverHtml(String cuerpo) {
		return "<html><body style='font-family:sans-serif;'>" + cuerpo + "</body></html>";
	}

	/**
	 * La implementación concreta aporta la ilustración o panel visual.
	 */
	public abstract JPanel crearPanelIlustracion();

	/**
	 * La implementación concreta aplica colores, bordes y apariencia.
	 */
	public abstract void aplicarApariencia();

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
		repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		return Collections.emptyList();
	}
}