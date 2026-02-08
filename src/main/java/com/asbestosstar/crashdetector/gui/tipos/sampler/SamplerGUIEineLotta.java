package com.asbestosstar.crashdetector.gui.tipos.sampler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación visual del Sampler estilo Eine Lotta.
 *
 * Esta clase SOLO maneja la apariencia.
 */
public class SamplerGUIEineLotta extends SamplerGUI {

	public static final String ID = "eine_lotta";

	/* =======================
	 * Colores configurables
	 * ======================= */

	public ConfigColor colorFondo = ConfigColor.de(
			"gui.sampler.einelotta.color.fondo",
			new java.awt.Color(248, 244, 250)
	);

	public ConfigColor colorTexto = ConfigColor.de(
			"gui.sampler.einelotta.color.texto",
			new java.awt.Color(60, 55, 70)
	);

	public ConfigColor colorBoton = ConfigColor.de(
			"gui.sampler.einelotta.color.boton",
			new java.awt.Color(190, 150, 210)
	);

	/* =======================
	 * Componentes visuales
	 * ======================= */

	private JButton botonIniciar;
	private JButton botonDetener;
	private JButton botonLimpiar;

	private JTextArea areaDatos;

	@Override
	public String id() {
		return ID;
	}


	/**
	 * Construye la ventana del sampler.
	 */
	@Override
	public void init() {
		inicializarVentana();

		setSize(700, 450);
		setLayout(new BorderLayout());

		JPanel raiz = new JPanel(new BorderLayout(10, 10));
		raiz.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		raiz.setBackground(colorFondo.obtener());

		/* Imagen decorativa */
		JLabel imagen = new JLabel();
		ImageIcon icon = new ImageIcon(
				Statics.carpeta.resolve("imagenes/einelotta.png").toString()
		);
		Image esc = icon.getImage().getScaledInstance(145, 250, Image.SCALE_SMOOTH);
		imagen.setIcon(new ImageIcon(esc));
		raiz.add(imagen, BorderLayout.EAST);

		/* Panel central */
		JPanel centro = new JPanel();
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		centro.setBackground(colorFondo.obtener());

		/* Descripción */
		JEditorPane descripcion = new JEditorPane();
		descripcion.setContentType("text/html");
		descripcion.setEditable(false);
		descripcion.setOpaque(false);
		descripcion.setForeground(colorTexto.obtener());
		descripcion.setText(construirHTMLDescripcion());

		JScrollPane scrollDesc = new JScrollPane(descripcion);
		scrollDesc.setBorder(null);
		scrollDesc.setOpaque(false);
		scrollDesc.getViewport().setOpaque(false);

		centro.add(scrollDesc);
		centro.add(Box.createVerticalStrut(8));

		/* Área de muestras */
		areaDatos = new JTextArea();
		areaDatos.setEditable(false);
		areaDatos.setBackground(colorFondo.obtener());
		areaDatos.setForeground(colorTexto.obtener());
		areaDatos.setBorder(BorderFactory.createLineBorder(colorTexto.obtener()));

		JScrollPane scrollDatos = new JScrollPane(areaDatos);
		scrollDatos.setPreferredSize(new Dimension(420, 200));

		centro.add(scrollDatos);
		centro.add(Box.createVerticalStrut(8));

		/* Botones */
		JPanel botones = new JPanel();
		botones.setOpaque(false);

		botonIniciar = new JButton(MonitorDePID.idioma.profilerIniciar());
		botonIniciar.setBackground(colorBoton.obtener());
		botonIniciar.setForeground(colorTexto.obtener());
		botonIniciar.addActionListener(e -> iniciarSampler());

		botonDetener = new JButton(MonitorDePID.idioma.profilerDetener());
		botonDetener.setBackground(colorBoton.obtener());
		botonDetener.setForeground(colorTexto.obtener());
		botonDetener.setEnabled(false);
		botonDetener.addActionListener(e -> detenerSampler());

		botonLimpiar = new JButton(MonitorDePID.idioma.profilerLimpiar());
		botonLimpiar.setBackground(colorBoton.obtener());
		botonLimpiar.setForeground(colorTexto.obtener());
		botonLimpiar.addActionListener(e -> limpiarDatos());

		botones.add(botonIniciar);
		botones.add(botonDetener);
		botones.add(botonLimpiar);

		centro.add(botones);

		raiz.add(centro, BorderLayout.CENTER);
		add(raiz);

		setLocationByPlatform(true);
		setVisible(true);
	}

	private String construirHTMLDescripcion() {
		String texto = MonitorDePID.idioma.samplerDescripcion();

		return "<html><body>"
				+ "<div style='font-size:11px; line-height:1.4;'>"
				+ texto
				+ "</div></body></html>";
	}

	/* ==========================================================
	 * Hooks visuales
	 * ========================================================== */

	@Override
	protected void onSamplerIniciado() {
		areaDatos.append(MonitorDePID.idioma.profilerEstadoIniciado() + "\n");
		botonIniciar.setEnabled(false);
		botonDetener.setEnabled(true);
	}

	@Override
	protected void onSamplerDetenido() {
		areaDatos.append(MonitorDePID.idioma.profilerEstadoDetenido() + "\n");
		botonIniciar.setEnabled(true);
		botonDetener.setEnabled(false);
	}

	@Override
	protected void onMuestraRecibida(String hilo, StackTraceElement[] stack) {
		areaDatos.append(
				MonitorDePID.idioma.profilerMuestraHilo(hilo) + "\n"
		);
	}

	@Override
	protected void onLimpiarDatos() {
		areaDatos.setText("");
	}

	@Override
	public void recargarApariencia() {
		repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> lista = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(
				() -> MonitorDePID.idioma.colorFondo()
		);
		colorTexto.establecerNombreParaMostrar(
				() -> MonitorDePID.idioma.colorTexto()
		);
		colorBoton.establecerNombreParaMostrar(
				() -> MonitorDePID.idioma.colorBoton()
		);

		lista.add(colorFondo);
		lista.add(colorTexto);
		lista.add(colorBoton);

		return lista;
	}
}
