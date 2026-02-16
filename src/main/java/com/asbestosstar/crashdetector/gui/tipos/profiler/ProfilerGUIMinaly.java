package com.asbestosstar.crashdetector.gui.tipos.profiler;

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
 * Implementación visual del Profiler con estilo Minaly XO.
 *
 * Esta clase SOLO define la apariencia y cómo se muestran los datos. No
 * contiene lógica de profiler.
 */
public class ProfilerGUIMinaly extends ProfilerGUI {

	public static final String ID = "minaly_xo";

	/*
	 * ======================= Colores configurables =======================
	 */

	public ConfigColor colorFondo = ConfigColor.de("gui.profiler.minaly.color.fondo",
			new java.awt.Color(245, 242, 250));

	public ConfigColor colorTexto = ConfigColor.de("gui.profiler.minaly.color.texto", new java.awt.Color(55, 50, 65));

	public ConfigColor colorBoton = ConfigColor.de("gui.profiler.minaly.color.boton",
			new java.awt.Color(185, 145, 205));

	/*
	 * ======================= Componentes visuales =======================
	 */

	private JButton botonIniciar;
	private JButton botonDetener;
	private JButton botonLimpiar;

	private JTextArea areaDatos;

	@Override
	public String id() {
		return ID;
	}

	/**
	 * Construye la ventana del profiler.
	 */
	@Override
	public void init() {
		inicializarVentana();

		setSize(720, 460);
		setLayout(new BorderLayout());

		JPanel raiz = new JPanel(new BorderLayout(10, 10));
		raiz.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		raiz.setBackground(colorFondo.obtener());

		/*
		 * ======================= Imagen decorativa =======================
		 */
		JLabel imagen = new JLabel();
		ImageIcon icon = new ImageIcon(Statics.carpeta.resolve("imagenes/minaly_xo.png").toString());
		Image esc = icon.getImage().getScaledInstance(150, 245, Image.SCALE_SMOOTH);
		imagen.setIcon(new ImageIcon(esc));
		raiz.add(imagen, BorderLayout.EAST);

		/*
		 * ======================= Panel central =======================
		 */
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

		/* Área de datos del profiler */
		areaDatos = new JTextArea();
		areaDatos.setEditable(false);
		areaDatos.setBackground(colorFondo.obtener());
		areaDatos.setForeground(colorTexto.obtener());
		areaDatos.setBorder(BorderFactory.createLineBorder(colorTexto.obtener()));

		JScrollPane scrollDatos = new JScrollPane(areaDatos);
		scrollDatos.setPreferredSize(new Dimension(420, 200));

		centro.add(scrollDatos);
		centro.add(Box.createVerticalStrut(8));

		/*
		 * ======================= Botones =======================
		 */
		JPanel botones = new JPanel();
		botones.setOpaque(false);

		botonIniciar = new JButton(MonitorDePID.idioma.profilerIniciar());
		botonIniciar.setBackground(colorBoton.obtener());
		botonIniciar.setForeground(colorTexto.obtener());
		botonIniciar.addActionListener(e -> iniciarProfiler());

		botonDetener = new JButton(MonitorDePID.idioma.profilerDetener());
		botonDetener.setBackground(colorBoton.obtener());
		botonDetener.setForeground(colorTexto.obtener());
		botonDetener.setEnabled(false);
		botonDetener.addActionListener(e -> detenerProfiler());

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
		String texto = MonitorDePID.idioma.profilerDescripcion();

		return "<html><body>" + "<div style='font-size:11px; line-height:1.4;'>" + texto + "</div></body></html>";
	}

	/*
	 * ========================================================== Hooks visuales del
	 * ProfilerGUI ==========================================================
	 */

	@Override
	protected void onProfilerIniciado() {
		areaDatos.append(MonitorDePID.idioma.profilerEstadoIniciado() + "\n");
		botonIniciar.setEnabled(false);
		botonDetener.setEnabled(true);
	}

	@Override
	protected void onProfilerDetenido() {
		areaDatos.append(MonitorDePID.idioma.profilerEstadoDetenido() + "\n");
		botonIniciar.setEnabled(true);
		botonDetener.setEnabled(false);
	}

	@Override
	protected void onMuestraRecibida(String hilo, StackTraceElement[] stack, long nanos) {
		areaDatos.append(MonitorDePID.idioma.profilerMuestraHilo(hilo) + "\n");
	}

	@Override
	protected void onLlamadaMetodo(String clase, String metodo, String descriptor, long duracionNs) {
		areaDatos.append(clase + "." + metodo + " " + descriptor + " (" + duracionNs + " ns)\n");
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

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());

		lista.add(colorFondo);
		lista.add(colorTexto);
		lista.add(colorBoton);

		return lista;
	}
}
