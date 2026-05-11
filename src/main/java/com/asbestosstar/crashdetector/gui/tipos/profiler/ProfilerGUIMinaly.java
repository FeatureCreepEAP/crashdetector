package com.asbestosstar.crashdetector.gui.tipos.profiler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.elementos.RenderizadorBarraRendimiento;

/**
 * Profiler visual rápido estilo Minaly.
 *
 * Usa acumulación en memoria y refresca la tabla por lotes para evitar saturar
 * Swing con miles de invokeLater.
 */
public class ProfilerGUIMinaly extends ProfilerGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "minaly_xo";

	public ConfigColor colorFondo = ConfigColor.de("gui.profiler.minaly.color.fondo", new Color(255, 226, 236));

	public ConfigColor colorPanel = ConfigColor.de("gui.profiler.minaly.color.panel", new Color(255, 245, 249));

	public ConfigColor colorTexto = ConfigColor.de("gui.profiler.minaly.color.texto", new Color(80, 55, 70));

	public ConfigColor colorBoton = ConfigColor.de("gui.profiler.minaly.color.boton", new Color(255, 180, 205));

	public ConfigColor colorBarra = ConfigColor.de("gui.profiler.minaly.color.barra", new Color(255, 120, 160));

	private JButton botonIniciar;
	private JButton botonDetener;
	private JButton botonLimpiar;

	private JTable tabla;
	private ModeloProfiler modelo;
	private Timer timerRefresco;
	private JLabel etiquetaEstado;

	private final Map<String, DatosMetodo> datos = new ConcurrentHashMap<>();

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {
		inicializarVentana();

		setSize(920, 560);
		setMinimumSize(new Dimension(760, 430));
		setLayout(new BorderLayout());

		JPanel raiz = new JPanel(new BorderLayout(12, 12));
		raiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		raiz.setBackground(colorFondo.obtener());

		raiz.add(crearCabecera(), BorderLayout.NORTH);
		raiz.add(crearTabla(), BorderLayout.CENTER);
		raiz.add(crearPanelDerecho(), BorderLayout.EAST);
		raiz.add(crearBotones(), BorderLayout.SOUTH);

		add(raiz);

		timerRefresco = new Timer(250, e -> refrescarTabla());
		timerRefresco.start();

		setLocationByPlatform(true);
		setVisible(true);
	}

	private JPanel crearCabecera() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);

		JLabel titulo = new JLabel("Profiler de rendimiento");
		titulo.setForeground(colorTexto.obtener());
		titulo.setFont(titulo.getFont().deriveFont(18f));

		etiquetaEstado = new JLabel("Detenido");
		etiquetaEstado.setForeground(colorTexto.obtener());

		panel.add(titulo, BorderLayout.WEST);
		panel.add(etiquetaEstado, BorderLayout.EAST);

		return panel;
	}

	private JScrollPane crearTabla() {
		modelo = new ModeloProfiler();
		tabla = new JTable(modelo);

		tabla.setRowHeight(26);
		tabla.setFillsViewportHeight(true);
		tabla.setShowGrid(false);
		tabla.setIntercellSpacing(new Dimension(0, 0));
		tabla.setBackground(colorPanel.obtener());
		tabla.setForeground(colorTexto.obtener());
		tabla.setSelectionBackground(new Color(255, 205, 220));
		tabla.setSelectionForeground(colorTexto.obtener());

		tabla.getTableHeader().setBackground(colorBoton.obtener());
		tabla.getTableHeader().setForeground(colorTexto.obtener());

		RenderizadorBarraRendimiento renderizador = new RenderizadorBarraRendimiento(colorPanel.obtener(),
				colorBarra.obtener(), Color.WHITE);

		TableColumn colTiempo = tabla.getColumnModel().getColumn(3);
		colTiempo.setCellRenderer(renderizador);

		tabla.putClientProperty("renderizadorBarra", renderizador);

		tabla.getColumnModel().getColumn(0).setPreferredWidth(360);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(180);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(180);

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBorder(BorderFactory.createLineBorder(colorBoton.obtener()));
		scroll.getViewport().setBackground(colorPanel.obtener());

		return scroll;
	}

	private JPanel crearPanelDerecho() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(170, 1));

		JLabel imagen = new JLabel();

		ImageIcon icon = new ImageIcon(Statics.carpeta.resolve("imagenes/minaly_xo.png").toString());
		Image esc = icon.getImage().getScaledInstance(155, 255, Image.SCALE_SMOOTH);
		imagen.setIcon(new ImageIcon(esc));

		JLabel ayuda = new JLabel("<html><div style='width:150px;'>"
				+ "Los métodos más lentos aparecen arriba. La barra muestra el peso relativo del tiempo acumulado."
				+ "</div></html>");
		ayuda.setForeground(colorTexto.obtener());

		panel.add(imagen);
		panel.add(Box.createVerticalStrut(8));
		panel.add(ayuda);

		return panel;
	}

	private JPanel crearBotones() {
		JPanel botones = new JPanel();
		botones.setOpaque(false);

		botonIniciar = crearBoton(MonitorDePID.idioma.profilerIniciar());
		botonIniciar.addActionListener(e -> iniciarProfiler());

		botonDetener = crearBoton(MonitorDePID.idioma.profilerDetener());
		botonDetener.setEnabled(false);
		botonDetener.addActionListener(e -> detenerProfiler());

		botonLimpiar = crearBoton(MonitorDePID.idioma.profilerLimpiar());
		botonLimpiar.addActionListener(e -> limpiarDatos());

		botones.add(botonIniciar);
		botones.add(botonDetener);
		botones.add(botonLimpiar);

		return botones;
	}

	private JButton crearBoton(String texto) {
		JButton boton = new JButton(texto);
		boton.setBackground(colorBoton.obtener());
		boton.setForeground(colorTexto.obtener());
		boton.setFocusPainted(false);
		return boton;
	}

	@Override
	protected void onProfilerIniciado() {
		etiquetaEstado.setText("Activo");
		botonIniciar.setEnabled(false);
		botonDetener.setEnabled(true);
	}

	@Override
	protected void onProfilerDetenido() {
		etiquetaEstado.setText("Detenido");
		botonIniciar.setEnabled(true);
		botonDetener.setEnabled(false);
	}

	@Override
	protected void onMuestraRecibida(String hilo, StackTraceElement[] stack, long nanos) {
		// Este GUI se enfoca en tiempos por método.
		// Se deja hook disponible para sampling real si luego quieres flame graph.
	}

	@Override
	protected void onLlamadaMetodo(String clase, String metodo, String descriptor, long duracionNs) {
		String clave = clase + "." + metodo + descriptor;

		DatosMetodo d = datos.computeIfAbsent(clave, k -> new DatosMetodo(clase, metodo, descriptor));
		d.tiempoTotal.addAndGet(duracionNs);
		d.llamadas.incrementAndGet();
	}

	@Override
	protected void onLimpiarDatos() {
		datos.clear();
		refrescarTabla();
	}

	private void refrescarTabla() {
		List<DatosMetodoVista> lista = new ArrayList<>();

		for (DatosMetodo d : datos.values()) {
			long total = d.tiempoTotal.get();
			long llamadas = d.llamadas.get();

			if (total <= 0 || llamadas <= 0)
				continue;

			lista.add(new DatosMetodoVista(d.clase, d.metodo, d.descriptor, llamadas, total));
		}

		lista.sort(Comparator.comparingLong(DatosMetodoVista::getTiempoTotal).reversed());

		if (lista.size() > 300) {
			lista = new ArrayList<>(lista.subList(0, 300));
		}

		long maximo = lista.isEmpty() ? 1L : lista.get(0).tiempoTotal;

		Object obj = tabla.getClientProperty("renderizadorBarra");
		if (obj instanceof RenderizadorBarraRendimiento) {
			((RenderizadorBarraRendimiento) obj).establecerMaximo(maximo);
		}

		modelo.setDatos(lista);

		long totalGeneral = 0L;
		for (DatosMetodoVista d : lista) {
			totalGeneral += d.tiempoTotal;
		}

		etiquetaEstado.setText((profilerActivo ? "Activo" : "Detenido") + " | métodos: " + datos.size() + " | top: "
				+ lista.size() + " | total visible: " + formatearNs(totalGeneral));
	}

	private String formatearNs(long ns) {
		if (ns >= 1_000_000_000L)
			return String.format("%.2f s", ns / 1_000_000_000.0);
		if (ns >= 1_000_000L)
			return String.format("%.2f ms", ns / 1_000_000.0);
		if (ns >= 1_000L)
			return String.format("%.2f µs", ns / 1_000.0);
		return ns + " ns";
	}

	@Override
	public void recargarApariencia() {
		repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> lista = new ArrayList<>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorPanel.establecerNombreParaMostrar(() -> "Color panel");
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorBarra.establecerNombreParaMostrar(() -> "Color barra");

		lista.add(colorFondo);
		lista.add(colorPanel);
		lista.add(colorTexto);
		lista.add(colorBoton);
		lista.add(colorBarra);

		return lista;
	}

	private static final class DatosMetodo {
		final String clase;
		final String metodo;
		final String descriptor;

		final AtomicLong llamadas = new AtomicLong();
		final AtomicLong tiempoTotal = new AtomicLong();

		DatosMetodo(String clase, String metodo, String descriptor) {
			this.clase = clase;
			this.metodo = metodo;
			this.descriptor = descriptor;
		}
	}

	private static final class DatosMetodoVista {
		final String clase;
		final String metodo;
		final String descriptor;
		final long llamadas;
		final long tiempoTotal;

		DatosMetodoVista(String clase, String metodo, String descriptor, long llamadas, long tiempoTotal) {
			this.clase = clase;
			this.metodo = metodo;
			this.descriptor = descriptor;
			this.llamadas = llamadas;
			this.tiempoTotal = tiempoTotal;
		}

		long getTiempoTotal() {
			return tiempoTotal;
		}
	}

	private final class ModeloProfiler extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private final String[] columnas = { "Clase", "Método", "Llamadas", "Tiempo total" };

		private List<DatosMetodoVista> filas = new ArrayList<>();

		void setDatos(List<DatosMetodoVista> nuevasFilas) {
			this.filas = nuevasFilas;
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return filas.size();
		}

		@Override
		public int getColumnCount() {
			return columnas.length;
		}

		@Override
		public String getColumnName(int column) {
			return columnas[column];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			DatosMetodoVista d = filas.get(rowIndex);

			switch (columnIndex) {
			case 0:
				return d.clase;
			case 1:
				return d.metodo;
			case 2:
				return d.llamadas;
			case 3:
				return d.tiempoTotal;
			default:
				return "";
			}
		}
	}
}