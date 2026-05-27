package com.asbestosstar.crashdetector.gui.tipos.sampler;

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

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.elementos.RenderizadorBarraSampler;

public class SamplerGUIEineLotta extends SamplerGUI {

	private static final long serialVersionUID = 1L;

	public static final String ID = "eine_lotta";

	public ConfigColor colorFondo = ConfigColor.de("gui.sampler.einelotta.color.fondo", new Color(235, 239, 248));

	public ConfigColor colorPanel = ConfigColor.de("gui.sampler.einelotta.color.panel", new Color(247, 249, 255));

	public ConfigColor colorTexto = ConfigColor.de("gui.sampler.einelotta.color.texto", new Color(45, 52, 70));

	public ConfigColor colorBoton = ConfigColor.de("gui.sampler.einelotta.color.boton", new Color(170, 190, 225));

	public ConfigColor colorBarra = ConfigColor.de("gui.sampler.einelotta.color.barra", new Color(95, 135, 195));

	public ConfigBoolean usarModeloOriginal = ConfigBoolean.de("gui.sampler.einelotta.usar.modelo.original", false);

	private JButton botonIniciar;
	private JButton botonDetener;
	private JButton botonLimpiar;

	private JTable tabla;
	private ModeloSampler modelo;
	private JLabel etiquetaEstado;
	private Timer timerRefresco;

	private final Map<String, DatosMuestra> datos = new ConcurrentHashMap<>();

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

		JLabel titulo = new JLabel("Sampler de rendimiento");
		titulo.setForeground(colorTexto.obtener());
		titulo.setFont(titulo.getFont().deriveFont(18f));

		etiquetaEstado = new JLabel("Detenido");
		etiquetaEstado.setForeground(colorTexto.obtener());

		panel.add(titulo, BorderLayout.WEST);
		panel.add(etiquetaEstado, BorderLayout.EAST);

		return panel;
	}

	private JScrollPane crearTabla() {
		modelo = new ModeloSampler();
		tabla = new JTable(modelo);

		tabla.setRowHeight(26);
		tabla.setFillsViewportHeight(true);
		tabla.setShowGrid(false);
		tabla.setIntercellSpacing(new Dimension(0, 0));
		tabla.setBackground(colorPanel.obtener());
		tabla.setForeground(colorTexto.obtener());
		tabla.setSelectionBackground(new Color(205, 218, 240));
		tabla.setSelectionForeground(colorTexto.obtener());

		tabla.getTableHeader().setBackground(colorBoton.obtener());
		tabla.getTableHeader().setForeground(colorTexto.obtener());

		RenderizadorBarraSampler renderizador = new RenderizadorBarraSampler(colorPanel.obtener(), colorBarra.obtener(),
				Color.WHITE);

		tabla.getColumnModel().getColumn(3).setCellRenderer(renderizador);
		tabla.putClientProperty("renderizadorBarraSampler", renderizador);

		tabla.getColumnModel().getColumn(0).setPreferredWidth(430);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(110);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(190);

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

		ImageIcon icon;

		if (usarModeloOriginal.obtener()) {
			icon = cargarImagenConFallback(Statics.carpeta.resolve("imagenes/einelotta_original.png").toString(),
					"/mnt/data/einelotta_original.png");
		} else {
			icon = cargarImagenConFallback(Statics.carpeta.resolve("imagenes/einelotta.png").toString(),
					"/mnt/data/einelotta.png");
		}

		Image esc = icon.getImage().getScaledInstance(155, 255, Image.SCALE_SMOOTH);
		imagen.setIcon(new ImageIcon(esc));

		JLabel ayuda = new JLabel("<html><div style='width:150px;'>"
				+ "Los métodos con más tiempo acumulado aparecen arriba. La barra muestra visualmente el peso relativo."
				+ "</div></html>");
		ayuda.setForeground(colorTexto.obtener());

		panel.add(imagen);
		panel.add(Box.createVerticalStrut(8));
		panel.add(ayuda);

		return panel;
	}

	private ImageIcon cargarImagenConFallback(String... rutas) {
		for (String ruta : rutas) {

			if (ruta == null || ruta.trim().isEmpty()) {
				continue;
			}

			ImageIcon icon = new ImageIcon(ruta);

			if (icon.getIconWidth() > 0) {
				return icon;
			}
		}

		return new ImageIcon();
	}

	private JPanel crearBotones() {
		JPanel botones = new JPanel();
		botones.setOpaque(false);

		botonIniciar = crearBoton(MonitorDePID.idioma.profilerIniciar());
		botonIniciar.addActionListener(e -> iniciarSampler());

		botonDetener = crearBoton(MonitorDePID.idioma.profilerDetener());
		botonDetener.setEnabled(false);
		botonDetener.addActionListener(e -> detenerSampler());

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
	public void onSamplerIniciado() {
		etiquetaEstado.setText("Activo");
		botonIniciar.setEnabled(false);
		botonDetener.setEnabled(true);
	}

	@Override
	public void onSamplerDetenido() {
		etiquetaEstado.setText("Detenido");
		botonIniciar.setEnabled(true);
		botonDetener.setEnabled(false);
	}

	@Override
	public void onMuestraRecibida(String metodo, StackTraceElement[] stack, long nanos) {
		DatosMuestra d = datos.computeIfAbsent(metodo, DatosMuestra::new);
		d.muestras.incrementAndGet();
		d.tiempoTotal.addAndGet(nanos);
	}

	@Override
	public void onLimpiarDatos() {
		datos.clear();
		refrescarTabla();
	}

	private void refrescarTabla() {
		List<DatosVista> filas = new ArrayList<>();

		for (DatosMuestra d : datos.values()) {
			long muestras = d.muestras.get();
			long tiempo = d.tiempoTotal.get();

			if (muestras <= 0)
				continue;

			filas.add(new DatosVista(d.metodo, muestras, tiempo));
		}

		filas.sort(Comparator.comparingLong(DatosVista::getTiempoTotal).reversed());

		if (filas.size() > 300) {
			filas = new ArrayList<>(filas.subList(0, 300));
		}

		long maximo = filas.isEmpty() ? 1L : Math.max(1L, filas.get(0).tiempoTotal);

		Object obj = tabla.getClientProperty("renderizadorBarraSampler");
		if (obj instanceof RenderizadorBarraSampler) {
			((RenderizadorBarraSampler) obj).establecerMaximo(maximo);
		}

		modelo.setDatos(filas);

		etiquetaEstado.setText(
				(samplerActivo ? "Activo" : "Detenido") + " | métodos: " + datos.size() + " | top: " + filas.size());
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
		usarModeloOriginal.establecerNombreParaMostrar(() -> "Usar modelo original");

		lista.add(colorFondo);
		lista.add(colorPanel);
		lista.add(colorTexto);
		lista.add(colorBoton);
		lista.add(colorBarra);
		lista.add(usarModeloOriginal);
		return lista;
	}

	private static final class DatosMuestra {

		final String metodo;
		final AtomicLong muestras = new AtomicLong();
		final AtomicLong tiempoTotal = new AtomicLong();

		DatosMuestra(String metodo) {
			this.metodo = metodo;
		}
	}

	private final class DatosVista {

		final String metodo;
		final long muestras;
		final long tiempoTotal;
		final long promedio;

		DatosVista(String metodo, long muestras, long tiempoTotal) {
			this.metodo = metodo;
			this.muestras = muestras;
			this.tiempoTotal = tiempoTotal;
			this.promedio = muestras <= 0 ? 0 : tiempoTotal / muestras;
		}

		long getTiempoTotal() {
			return tiempoTotal;
		}
	}

	private final class ModeloSampler extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private final String[] columnas = { "Método", "Muestras", "Promedio", "Tiempo total" };

		private List<DatosVista> filas = new ArrayList<>();

		void setDatos(List<DatosVista> nuevasFilas) {
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
			DatosVista d = filas.get(rowIndex);

			switch (columnIndex) {
			case 0:
				return d.metodo;
			case 1:
				return d.muestras;
			case 2:
				return formatearNs(d.promedio);
			case 3:
				return d.tiempoTotal;
			default:
				return "";
			}
		}
	}
}