package com.asbestosstar.crashdetector.gui.tipos.heapdump;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.heapdump.AnalizadorHprofRapido;
import com.asbestosstar.crashdetector.heapdump.EstadisticaHeap;
import com.asbestosstar.crashdetector.heapdump.ResultadoHeapDump;

/**
 * GUI base del visor rápido de heap dumps.
 *
 * La lógica de importación, análisis, tabla y árbol jerárquico permanece en
 * esta clase. Una implementación concreta define los colores y la imagen.
 */
public abstract class VisorHeapDumpGUI extends JFrame implements BotonDeBarraLateralDerecha {

	public static final Map<String, Supplier<VisorHeapDumpGUI>> GUIS = new LinkedHashMap<String, Supplier<VisorHeapDumpGUI>>();

	private static final long serialVersionUID = 1L;
	private static final int MAXIMO_SEGMENTOS_PAQUETE = 5;

	protected JPanel panelRaiz;
	protected JPanel panelCabecera;
	protected JPanel panelControles;
	protected JPanel panelEstado;
	protected JLabel imagenTema;
	protected JTextArea areaDescripcion;
	protected JButton botonImportar;
	protected JButton botonCancelar;
	protected JButton botonExpandir;
	protected JButton botonContraer;
	protected JCheckBox casillaIdentificarMods;
	protected JProgressBar progreso;
	protected JLabel etiquetaEstado;
	protected JTabbedPane pestanas;
	protected JTable tablaClases;
	protected JTree arbolMods;
	protected JScrollPane scrollTabla;
	protected JScrollPane scrollArbol;

	protected ModeloClases modeloClases;
	protected volatile ResultadoHeapDump resultadoActual;
	protected volatile SwingWorker<ResultadoHeapDump, Avance> worker;
	protected final AtomicBoolean cancelado = new AtomicBoolean(false);
	protected boolean inicializada;

	@Override
	public void init() {
		if (inicializada) {
			recargarTextos();
			recargarApariencia();
			setVisible(true);
			toFront();
			requestFocus();
			return;
		}

		inicializada = true;
		setTitle(MonitorDePID.idioma.heapVisorTitulo());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1180, 760);
		setMinimumSize(new Dimension(900, 600));
		setLocationByPlatform(true);

		panelRaiz = new JPanel(new BorderLayout(10, 10));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		crearCabecera();
		crearCentro();
		crearEstado();

		setContentPane(panelRaiz);
		conectarEventos();
		recargarTextos();
		recargarApariencia();
		establecerCargando(false);
		setVisible(true);
	}

	private void crearCabecera() {
		panelCabecera = new JPanel(new BorderLayout(12, 8));

		imagenTema = new JLabel();
		imagenTema.setHorizontalAlignment(SwingConstants.CENTER);
		imagenTema.setVerticalAlignment(SwingConstants.CENTER);
		imagenTema.setPreferredSize(new Dimension(260, 150));

		areaDescripcion = new JTextArea();
		areaDescripcion.setEditable(false);
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setWrapStyleWord(true);
		areaDescripcion.setRows(5);
		areaDescripcion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
		botonImportar = new JButton();
		botonCancelar = new JButton();
		botonExpandir = new JButton();
		botonContraer = new JButton();
		casillaIdentificarMods = new JCheckBox();
		casillaIdentificarMods.setSelected(true);

		panelControles.add(botonImportar);
		panelControles.add(botonCancelar);
		panelControles.add(casillaIdentificarMods);
		panelControles.add(botonExpandir);
		panelControles.add(botonContraer);

		JPanel panelTexto = new JPanel(new BorderLayout(4, 4));
		panelTexto.add(areaDescripcion, BorderLayout.CENTER);
		panelTexto.add(panelControles, BorderLayout.SOUTH);

		panelCabecera.add(imagenTema, BorderLayout.WEST);
		panelCabecera.add(panelTexto, BorderLayout.CENTER);
		panelRaiz.add(panelCabecera, BorderLayout.NORTH);
	}

	private void crearCentro() {
		modeloClases = new ModeloClases();
		tablaClases = new JTable(modeloClases);
		tablaClases.setAutoCreateRowSorter(true);
		tablaClases.setFillsViewportHeight(true);
		configurarColumnasTabla();

		arbolMods = new JTree(new DefaultMutableTreeNode());
		arbolMods.setRootVisible(false);
		arbolMods.setShowsRootHandles(true);
		arbolMods.setCellRenderer(new RenderizadorArbol());

		scrollTabla = new JScrollPane(tablaClases);
		scrollArbol = new JScrollPane(arbolMods);

		pestanas = new JTabbedPane();
		pestanas.addTab("", scrollTabla);
		pestanas.addTab("", scrollArbol);

		JSplitPane division = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pestanas, crearPanelAyuda());
		division.setResizeWeight(0.82);
		division.setDividerLocation(900);
		panelRaiz.add(division, BorderLayout.CENTER);
	}

	private void configurarColumnasTabla() {
		if (tablaClases == null || tablaClases.getColumnModel().getColumnCount() < 5) {
			return;
		}

		tablaClases.getColumnModel().getColumn(3).setCellRenderer(new RenderizadorBytes());
		tablaClases.getColumnModel().getColumn(4).setCellRenderer(new RenderizadorPorcentaje());
	}

	private JPanel crearPanelAyuda() {
		JPanel panel = new JPanel(new BorderLayout());
		JTextArea ayuda = new JTextArea();
		ayuda.setName("ayudaHeapDump");
		ayuda.setEditable(false);
		ayuda.setLineWrap(true);
		ayuda.setWrapStyleWord(true);
		ayuda.setText(MonitorDePID.idioma.heapVisorAyudaArbol());
		ayuda.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(ayuda, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(230, 200));
		return panel;
	}

	private void crearEstado() {
		panelEstado = new JPanel(new BorderLayout(8, 4));
		progreso = new JProgressBar(0, 100);
		progreso.setStringPainted(true);
		etiquetaEstado = new JLabel();
		panelEstado.add(etiquetaEstado, BorderLayout.WEST);
		panelEstado.add(progreso, BorderLayout.CENTER);
		panelRaiz.add(panelEstado, BorderLayout.SOUTH);
	}

	private void conectarEventos() {
		botonImportar.addActionListener(e -> elegirHeapDump());
		botonCancelar.addActionListener(e -> cancelarAnalisis());
		botonExpandir.addActionListener(e -> expandirSeleccion());
		botonContraer.addActionListener(e -> contraerTodo());
	}

	public void abrirArchivo(File archivo) {
		init();
		if (archivo != null) {
			analizarArchivo(archivo);
		}
	}

	private void elegirHeapDump() {
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle(MonitorDePID.idioma.heapVisorSeleccionarArchivo());

		if (selector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			analizarArchivo(selector.getSelectedFile());
		}
	}

	private void analizarArchivo(final File archivo) {
		if (worker != null && !worker.isDone()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.heapVisorAnalisisEnCurso());
			return;
		}

		if (archivo == null || !archivo.isFile() || !archivo.canRead()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.heapVisorArchivoNoValido());
			return;
		}

		cancelado.set(false);
		final boolean identificarMods = casillaIdentificarMods.isSelected();
		establecerCargando(true);
		etiquetaEstado.setText(MonitorDePID.idioma.heapVisorAnalizando(archivo.getName()));
		progreso.setValue(0);

		worker = new SwingWorker<ResultadoHeapDump, Avance>() {
			@Override
			protected ResultadoHeapDump doInBackground() throws Exception {
				return AnalizadorHprofRapido.analizar(archivo, identificarMods, new AnalizadorHprofRapido.Progreso() {
					@Override
					public void actualizar(int porcentaje, String detalle) {
						publish(new Avance(porcentaje, detalle));
					}
				}, cancelado);
			}

			@Override
			protected void process(List<Avance> avances) {
				if (avances == null || avances.isEmpty()) {
					return;
				}
				Avance ultimo = avances.get(avances.size() - 1);
				progreso.setValue(ultimo.porcentaje);
				progreso.setString(ultimo.porcentaje + "%");
				etiquetaEstado.setText(MonitorDePID.idioma.heapVisorProgreso(ultimo.porcentaje, ultimo.detalle));
			}

			@Override
			protected void done() {
				try {
					resultadoActual = get();
					mostrarResultado(resultadoActual);
					etiquetaEstado.setText(MonitorDePID.idioma.heapVisorListo(
							formatearBytes(resultadoActual.bytesTotalesEstimados()), resultadoActual.objetosTotales()));
				} catch (CancellationException e) {
					etiquetaEstado.setText(MonitorDePID.idioma.heapVisorCancelado());
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					etiquetaEstado.setText(MonitorDePID.idioma.heapVisorCancelado());
				} catch (ExecutionException e) {
					Throwable causa = e.getCause() == null ? e : e.getCause();
					CrashDetectorLogger.logException(causa);
					etiquetaEstado.setText(MonitorDePID.idioma.heapVisorError());
					JOptionPane.showMessageDialog(VisorHeapDumpGUI.this,
							MonitorDePID.idioma.heapVisorErrorDetalle(mensajeSeguro(causa)));
				} finally {
					establecerCargando(false);
				}
			}
		};

		worker.execute();
	}

	private void mostrarResultado(ResultadoHeapDump resultado) {
		modeloClases.establecer(resultado);
		DefaultMutableTreeNode raiz = construirArbol(resultado);
		arbolMods.setModel(new javax.swing.tree.DefaultTreeModel(raiz));
		arbolMods.setRootVisible(false);
		arbolMods.setShowsRootHandles(true);

		for (int i = 0; i < arbolMods.getRowCount() && i < 12; i++) {
			arbolMods.expandRow(i);
		}
	}

	private DefaultMutableTreeNode construirArbol(ResultadoHeapDump resultado) {
		NodoAgrupado raiz = new NodoAgrupado(MonitorDePID.idioma.heapVisorRaiz());

		for (EstadisticaHeap estadistica : resultado.estadisticas()) {
			NodoAgrupado nodo = raiz;
			nodo.acumular(estadistica);

			nodo = nodo.hijo(estadistica.mod().isEmpty() ? MonitorDePID.idioma.heapVisorSinMod() : estadistica.mod());
			nodo.acumular(estadistica);

			String clase = estadistica.clase();
			String claseBase = clase;
			int dimensiones = 0;
			while (claseBase.endsWith("[]")) {
				claseBase = claseBase.substring(0, claseBase.length() - 2);
				dimensiones++;
			}

			String[] partes = claseBase.split("\\.");
			int limitePaquete = Math.max(0, partes.length - 1);
			limitePaquete = Math.min(limitePaquete, MAXIMO_SEGMENTOS_PAQUETE);

			for (int i = 0; i < limitePaquete; i++) {
				nodo = nodo.hijo(partes[i]);
				nodo.acumular(estadistica);
			}

			String nombreClase = partes.length == 0 ? clase : partes[partes.length - 1];
			StringBuilder nombreFinal = new StringBuilder(nombreClase);
			for (int i = 0; i < dimensiones; i++) {
				nombreFinal.append("[]");
			}

			NodoAgrupado hoja = nodo.hijo(nombreFinal.toString());
			hoja.acumular(estadistica);
			hoja.claseCompleta = clase;
		}

		return convertirNodo(raiz, Math.max(1L, resultado.bytesTotalesEstimados()));
	}

	private DefaultMutableTreeNode convertirNodo(NodoAgrupado origen, long total) {
		NodoVista vista = new NodoVista(origen.nombre, origen.claseCompleta, origen.bytes, origen.instancias, total);
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(vista);

		List<NodoAgrupado> hijos = new ArrayList<NodoAgrupado>(origen.hijos.values());
		Collections.sort(hijos, new Comparator<NodoAgrupado>() {
			@Override
			public int compare(NodoAgrupado a, NodoAgrupado b) {
				int porBytes = Long.compare(b.bytes, a.bytes);
				return porBytes != 0 ? porBytes : a.nombre.compareToIgnoreCase(b.nombre);
			}
		});

		for (NodoAgrupado hijo : hijos) {
			nodo.add(convertirNodo(hijo, total));
		}
		return nodo;
	}

	private void expandirSeleccion() {
		TreePath seleccion = arbolMods.getSelectionPath();
		if (seleccion == null) {
			for (int i = 0; i < arbolMods.getRowCount() && i < 25; i++) {
				arbolMods.expandRow(i);
			}
			return;
		}

		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) seleccion.getLastPathComponent();
		expandirRecursivo(new TreePath(nodo.getPath()), 0, 4);
	}

	private void expandirRecursivo(TreePath ruta, int nivel, int maximo) {
		if (nivel > maximo) {
			return;
		}
		arbolMods.expandPath(ruta);
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) ruta.getLastPathComponent();
		java.util.Enumeration<?> hijos = nodo.children();

		while (hijos.hasMoreElements()) {
			DefaultMutableTreeNode hijo = (DefaultMutableTreeNode) hijos.nextElement();
			expandirRecursivo(ruta.pathByAddingChild(hijo), nivel + 1, maximo);
		}
	}

	private void contraerTodo() {
		for (int i = arbolMods.getRowCount() - 1; i >= 0; i--) {
			arbolMods.collapseRow(i);
		}
	}

	private void cancelarAnalisis() {
		cancelado.set(true);
		if (worker != null) {
			worker.cancel(true);
		}
	}

	protected void establecerCargando(boolean cargando) {
		botonImportar.setEnabled(!cargando);
		botonCancelar.setEnabled(cargando);
		casillaIdentificarMods.setEnabled(!cargando);
		progreso.setIndeterminate(false);
	}

	protected void recargarTextos() {
		setTitle(MonitorDePID.idioma.heapVisorTitulo());

		if (areaDescripcion != null) {
			areaDescripcion.setText(MonitorDePID.idioma.heapVisorDescripcion());
		}
		if (botonImportar != null) {
			botonImportar.setText(MonitorDePID.idioma.heapVisorImportar());
		}
		if (botonCancelar != null) {
			botonCancelar.setText(MonitorDePID.idioma.heapVisorCancelar());
		}
		if (botonExpandir != null) {
			botonExpandir.setText(MonitorDePID.idioma.heapVisorExpandir());
		}
		if (botonContraer != null) {
			botonContraer.setText(MonitorDePID.idioma.heapVisorContraer());
		}
		if (casillaIdentificarMods != null) {
			casillaIdentificarMods.setText(MonitorDePID.idioma.heapVisorIdentificarMods());
		}
		if (pestanas != null) {
			pestanas.setTitleAt(0, MonitorDePID.idioma.heapVisorPestanaClases());
			pestanas.setTitleAt(1, MonitorDePID.idioma.heapVisorPestanaMods());
		}

		if (panelRaiz != null) {
			actualizarAyuda(panelRaiz);
		}
		if (modeloClases != null) {
			modeloClases.fireTableStructureChanged();
			configurarColumnasTabla();
		}
	}

	private void actualizarAyuda(java.awt.Container contenedor) {
		for (java.awt.Component componente : contenedor.getComponents()) {
			if (componente instanceof JTextArea && "ayudaHeapDump".equals(componente.getName())) {
				((JTextArea) componente).setText(MonitorDePID.idioma.heapVisorAyudaArbol());
			}
			if (componente instanceof java.awt.Container) {
				actualizarAyuda((java.awt.Container) componente);
			}
		}
	}

	@Override
	public TipoGUI<VisorHeapDumpGUI> tipo() {
		return TipoGUI.VISOR_HEAP_DUMP;
	}

	@Override
	public abstract String id();

	@Override
	public abstract void recargarApariencia();

	@Override
	public abstract List<ElementoConfig> obtenerElementosConfigs();

	protected static String formatearBytes(long bytes) {
		double valor = bytes;
		String[] unidades = { "B", "KiB", "MiB", "GiB", "TiB" };
		int unidad = 0;

		while (valor >= 1024.0 && unidad < unidades.length - 1) {
			valor /= 1024.0;
			unidad++;
		}

		return new DecimalFormat(valor >= 100.0 ? "0" : valor >= 10.0 ? "0.0" : "0.00").format(valor) + " "
				+ unidades[unidad];
	}

	private static String mensajeSeguro(Throwable t) {
		if (t == null) {
			return "";
		}
		String mensaje = t.getMessage();
		if (mensaje == null || mensaje.trim().isEmpty()) {
			mensaje = t.getClass().getSimpleName();
		}
		mensaje = mensaje.replace('\r', ' ').replace('\n', ' ').trim();
		return mensaje.length() > 800 ? mensaje.substring(0, 800) : mensaje;
	}

	protected final class ModeloClases extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private final List<EstadisticaHeap> filas = new ArrayList<EstadisticaHeap>();
		private long total = 1L;

		void establecer(ResultadoHeapDump resultado) {
			filas.clear();
			if (resultado != null) {
				filas.addAll(resultado.estadisticas());
				total = Math.max(1L, resultado.bytesTotalesEstimados());
			}
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return filas.size();
		}

		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public String getColumnName(int columna) {
			switch (columna) {
			case 0:
				return MonitorDePID.idioma.heapVisorColClase();
			case 1:
				return MonitorDePID.idioma.heapVisorColMod();
			case 2:
				return MonitorDePID.idioma.heapVisorColInstancias();
			case 3:
				return MonitorDePID.idioma.heapVisorColMemoria();
			default:
				return MonitorDePID.idioma.heapVisorColPorcentaje();
			}
		}

		@Override
		public Class<?> getColumnClass(int columna) {
			if (columna == 2 || columna == 3) {
				return Long.class;
			}
			if (columna == 4) {
				return Double.class;
			}
			return String.class;
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			EstadisticaHeap estadistica = filas.get(fila);
			switch (columna) {
			case 0:
				return estadistica.clase();
			case 1:
				return estadistica.mod();
			case 2:
				return Long.valueOf(estadistica.instancias());
			case 3:
				return Long.valueOf(estadistica.bytesEstimados());
			default:
				return Double.valueOf((estadistica.bytesEstimados() * 100.0) / total);
			}
		}
	}

	private static final class RenderizadorBytes extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		protected void setValue(Object valor) {
			if (valor instanceof Long) {
				setText(formatearBytes(((Long) valor).longValue()));
				setHorizontalAlignment(SwingConstants.RIGHT);
			} else {
				super.setValue(valor);
			}
		}
	}

	private static final class RenderizadorPorcentaje extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		protected void setValue(Object valor) {
			if (valor instanceof Double) {
				setText(new DecimalFormat("0.00").format(((Double) valor).doubleValue()) + "%");
				setHorizontalAlignment(SwingConstants.RIGHT);
			} else {
				super.setValue(valor);
			}
		}
	}

	protected static final class Avance {
		final int porcentaje;
		final String detalle;

		Avance(int porcentaje, String detalle) {
			this.porcentaje = porcentaje;
			this.detalle = detalle == null ? "" : detalle;
		}
	}

	private static final class NodoAgrupado {
		final String nombre;
		final Map<String, NodoAgrupado> hijos = new LinkedHashMap<String, NodoAgrupado>();
		String claseCompleta;
		long bytes;
		long instancias;

		NodoAgrupado(String nombre) {
			this.nombre = nombre;
		}

		NodoAgrupado hijo(String nombre) {
			NodoAgrupado hijo = hijos.get(nombre);
			if (hijo == null) {
				hijo = new NodoAgrupado(nombre);
				hijos.put(nombre, hijo);
			}
			return hijo;
		}

		void acumular(EstadisticaHeap estadistica) {
			bytes += estadistica.bytesEstimados();
			instancias += estadistica.instancias();
		}
	}

	protected static final class NodoVista {
		final String nombre;
		final String claseCompleta;
		final long bytes;
		final long instancias;
		final long total;

		NodoVista(String nombre, String claseCompleta, long bytes, long instancias, long total) {
			this.nombre = nombre;
			this.claseCompleta = claseCompleta;
			this.bytes = bytes;
			this.instancias = instancias;
			this.total = total;
		}

		@Override
		public String toString() {
			double porcentaje = (bytes * 100.0) / Math.max(1L, total);
			return nombre + " — " + formatearBytes(bytes) + " — " + new DecimalFormat("0.00").format(porcentaje) + "%";
		}
	}

	private final class RenderizadorArbol extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public java.awt.Component getTreeCellRendererComponent(JTree arbol, Object valor, boolean seleccionado,
				boolean expandido, boolean hoja, int fila, boolean tieneFoco) {
			super.getTreeCellRendererComponent(arbol, valor, seleccionado, expandido, hoja, fila, tieneFoco);

			DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) valor;
			Object usuario = nodo.getUserObject();
			if (usuario instanceof NodoVista) {
				setText(usuario.toString());
				NodoVista vista = (NodoVista) usuario;
				setToolTipText(vista.claseCompleta == null
						? MonitorDePID.idioma.heapVisorDetalleNodo(vista.instancias, formatearBytes(vista.bytes))
						: vista.claseCompleta + " — " + MonitorDePID.idioma.heapVisorDetalleNodo(vista.instancias,
								formatearBytes(vista.bytes)));
			}
			return this;
		}
	}
}