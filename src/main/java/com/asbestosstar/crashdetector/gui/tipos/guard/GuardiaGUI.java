package com.asbestosstar.crashdetector.gui.tipos.guard;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.guard.ArticuloMalware;
import com.asbestosstar.crashdetector.guard.ModsMalware;
import com.asbestosstar.crashdetector.guard.ServidoresProblematicos;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrBase;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrSakuraRiddle;

/**
 * GUI abstracta del sistema Guard.
 *
 * Separa: - lógica y flujo de escaneo en esta clase - apariencia, colores e
 * imagen en una implementación concreta
 *
 * La tabla superior muestra servidores problemáticos. La tabla inferior muestra
 * malware detectado.
 *
 * Nota para el usuario: puede haber falsos positivos, especialmente si las
 * definiciones o escáneres externos son agresivos.
 */
public abstract class GuardiaGUI extends JFrame implements BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<GuardiaGUI>> GUIS = new HashMap<>();

	public JLabel imagenGuard;
	public JTextArea areaDescripcion;
	public JLabel etiquetaEstado;

	public JButton botonEscanearTodo;
	public JButton botonEscanearServidores;
	public JButton botonEscanearMalware;

	public JTable tablaServidores;
	public JTable tablaMalware;

	public ModeloTablaServidores modeloServidores;
	public ModeloTablaMalware modeloMalware;

	public JPanel overlayCarga;
	public JLabel gifCarga;
	public volatile boolean cargando = false;

	public volatile boolean inicializada = false;
	public SwingWorker<Void, Void> workerEscaneo;

	public JLabel imagenSketchyVT;
	public JLabel etiquetaSketchyVT;

	public GuardiaGUI() {
	}

	@Override
	public void init() {
		if (inicializada) {
			recargarApariencia();
			revalidate();
			repaint();
			setVisible(true);
			toFront();
			requestFocus();
			return;
		}

		inicializada = true;

		getContentPane().removeAll();
		setTitle(MonitorDePID.idioma.guardTitulo());
		setSize(1360, 880);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setBorder(new EmptyBorder(8, 8, 8, 8));
		panelIzquierdo.setPreferredSize(new Dimension(250, 400));

		imagenGuard = new JLabel("", SwingConstants.CENTER);
		imagenGuard.setBorder(new EmptyBorder(0, 0, 8, 0));

		areaDescripcion = new JTextArea();
		areaDescripcion.setEditable(false);
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setWrapStyleWord(true);
		areaDescripcion.setOpaque(false);
		areaDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 12));
		areaDescripcion.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		areaDescripcion.setText(MonitorDePID.idioma.guardDescripcion1() + "\n\n"
				+ MonitorDePID.idioma.guardDescripcion2() + "\n" + MonitorDePID.idioma.guardDescripcion3() + "\n"
				+ MonitorDePID.idioma.guardDescripcion4() + "\n" + MonitorDePID.idioma.guardDescripcion5());

		imagenSketchyVT = new JLabel("", SwingConstants.CENTER);
		imagenSketchyVT.setBorder(new EmptyBorder(8, 0, 4, 0));

		etiquetaSketchyVT = new JLabel("SketchyVT", SwingConstants.CENTER);
		etiquetaSketchyVT.setBorder(new EmptyBorder(0, 0, 4, 0));

		JPanel panelInferiorIzquierdo = new JPanel(new BorderLayout());
		panelInferiorIzquierdo.setOpaque(false);
		panelInferiorIzquierdo.add(imagenSketchyVT, BorderLayout.CENTER);
		panelInferiorIzquierdo.add(etiquetaSketchyVT, BorderLayout.SOUTH);

		JPanel bloqueDescripcion = new JPanel(new BorderLayout());
		bloqueDescripcion.setOpaque(false);
		bloqueDescripcion.add(new JScrollPane(areaDescripcion), BorderLayout.CENTER);
		bloqueDescripcion.add(panelInferiorIzquierdo, BorderLayout.SOUTH);

		panelIzquierdo.add(imagenGuard, BorderLayout.NORTH);
		panelIzquierdo.add(bloqueDescripcion, BorderLayout.CENTER);

		JPanel panelPrincipal = new JPanel(new BorderLayout());
		panelPrincipal.setBorder(new EmptyBorder(8, 8, 8, 8));

		JPanel barraBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));

		botonEscanearTodo = new JButton(MonitorDePID.idioma.guardEscanearTodo());
		botonEscanearServidores = new JButton(MonitorDePID.idioma.guardEscanearServidores());
		botonEscanearMalware = new JButton(MonitorDePID.idioma.guardEscanearMalware());

		etiquetaEstado = new JLabel(MonitorDePID.idioma.guardEstadoListo());

		barraBotones.add(botonEscanearTodo);
		barraBotones.add(botonEscanearServidores);
		barraBotones.add(botonEscanearMalware);
		barraBotones.add(etiquetaEstado);

		modeloServidores = new ModeloTablaServidores();
		modeloMalware = new ModeloTablaMalware();

		tablaServidores = new JTable(modeloServidores);
		tablaMalware = new JTable(modeloMalware);

		tablaServidores.setRowHeight(22);
		tablaMalware.setRowHeight(24);

		tablaMalware.getColumnModel().getColumn(3).setCellRenderer(new RenderizadorBotonCfr());
		tablaMalware.getColumnModel().getColumn(3).setCellEditor(new EditorBotonCfr());

		JPanel panelTablaServidores = new JPanel(new BorderLayout());
		panelTablaServidores.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.guardTablaServidores()));
		panelTablaServidores.add(new JScrollPane(tablaServidores), BorderLayout.CENTER);

		JPanel panelTablaMalware = new JPanel(new BorderLayout());
		panelTablaMalware.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.guardTablaMalware()));
		panelTablaMalware.add(new JScrollPane(tablaMalware), BorderLayout.CENTER);

		JSplitPane splitTablas = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelTablaServidores, panelTablaMalware);
		splitTablas.setDividerLocation(260);
		splitTablas.setResizeWeight(0.35);

		panelPrincipal.add(barraBotones, BorderLayout.NORTH);
		panelPrincipal.add(splitTablas, BorderLayout.CENTER);

		JSplitPane splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelPrincipal);
		splitPrincipal.setDividerLocation(260);
		splitPrincipal.setResizeWeight(0.0);

		add(splitPrincipal, BorderLayout.CENTER);

		botonEscanearTodo.addActionListener(e -> escanearTodoAsync());
		botonEscanearServidores.addActionListener(e -> escanearSoloServidoresAsync());
		botonEscanearMalware.addActionListener(e -> escanearSoloMalwareAsync());

		initOverlayCarga();
		setCargando(false);

		recargarApariencia();
		setVisible(true);
	}

	public void escanearTodoAsync() {
		cancelarWorkerAnterior();

		final Boolean politicaDefiniciones = resolverPoliticaDefinicionesServidor();
		if (politicaDefiniciones == null) {
			setEstado(MonitorDePID.idioma.guardEstadoListo());
			return;
		}

		setEstado(MonitorDePID.idioma.guardEstadoEscaneandoTodo());
		setCargando(true);

		workerEscaneo = new SwingWorker<Void, Void>() {
			private List<FilaServidor> servidores = new ArrayList<>();
			private List<FilaMalware> malware = new ArrayList<>();

			@Override
			protected Void doInBackground() {
				try {
					servidores = escanearServidoresInterno(politicaDefiniciones.booleanValue());
					malware = escanearMalwareInterno();
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
				}
				return null;
			}

			@Override
			protected void done() {
				try {
					if (isCancelled()) {
						return;
					}
					modeloServidores.establecerFilas(servidores);
					modeloMalware.establecerFilas(malware);
					setEstado(MonitorDePID.idioma.guardEstadoListo());
				} finally {
					setCargando(false);
				}
			}
		};

		workerEscaneo.execute();
	}

	public void escanearSoloServidoresAsync() {
		cancelarWorkerAnterior();

		final Boolean politicaDefiniciones = resolverPoliticaDefinicionesServidor();
		if (politicaDefiniciones == null) {
			setEstado(MonitorDePID.idioma.guardEstadoListo());
			return;
		}

		setEstado(MonitorDePID.idioma.guardEstadoEscaneandoServidores());
		setCargando(true);

		workerEscaneo = new SwingWorker<Void, Void>() {
			private List<FilaServidor> servidores = new ArrayList<>();

			@Override
			protected Void doInBackground() {
				try {
					servidores = escanearServidoresInterno(politicaDefiniciones.booleanValue());
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
				}
				return null;
			}

			@Override
			protected void done() {
				try {
					if (isCancelled()) {
						return;
					}
					modeloServidores.establecerFilas(servidores);
					setEstado(MonitorDePID.idioma.guardEstadoListo());
				} finally {
					setCargando(false);
				}
			}
		};

		workerEscaneo.execute();
	}

	protected List<FilaServidor> escanearServidoresInterno(boolean actualizarDefiniciones) {
		List<FilaServidor> filas = new ArrayList<>();

		List<String> encontrados = ServidoresProblematicos.obtenerServidoresProblematicos(actualizarDefiniciones);
		for (String servidor : encontrados) {
			filas.add(new FilaServidor(servidor, MonitorDePID.idioma.guardFuenteDefinicionesTLauncher()));
		}

		return filas;
	}

	public void escanearSoloMalwareAsync() {
		cancelarWorkerAnterior();
		setEstado(MonitorDePID.idioma.guardEstadoEscaneandoMalware());
		setCargando(true);

		workerEscaneo = new SwingWorker<Void, Void>() {
			private List<FilaMalware> malware = new ArrayList<>();

			@Override
			protected Void doInBackground() {
				try {
					malware = escanearMalwareInterno();
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
				}
				return null;
			}

			@Override
			protected void done() {
				try {
					if (isCancelled()) {
						return;
					}
					modeloMalware.establecerFilas(malware);
					setEstado(MonitorDePID.idioma.guardEstadoListo());
				} finally {
					setCargando(false);
				}
			}
		};

		workerEscaneo.execute();
	}

	protected void cancelarWorkerAnterior() {
		if (workerEscaneo != null && !workerEscaneo.isDone()) {
			workerEscaneo.cancel(true);
		}
	}

	protected List<FilaMalware> escanearMalwareInterno() {
		List<FilaMalware> filas = new ArrayList<>();
		List<ArticuloMalware> hallazgos = ModsMalware.escanear();

		if (hallazgos == null || hallazgos.isEmpty()) {
			return filas;
		}

		for (ArticuloMalware articulo : hallazgos) {
			try {
				String mensaje = articulo.mensaje() != null ? articulo.mensaje().get() : "";
				String ubicacion = articulo.ubicacion_de_archivo_mod();
				String clase = articulo.clase();

				filas.add(new FilaMalware(mensaje, ubicacion, clase, articulo));
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}

		return filas;
	}

	protected List<FilaServidor> escanearServidoresConPoliticaInteractiva() {
		List<FilaServidor> filas = new ArrayList<>();

		Boolean actualizar = resolverPoliticaDefinicionesServidor();
		if (actualizar == null) {
			return filas;
		}

		List<String> encontrados = ServidoresProblematicos.obtenerServidoresProblematicos(actualizar.booleanValue());
		for (String servidor : encontrados) {
			filas.add(new FilaServidor(servidor, MonitorDePID.idioma.guardFuenteDefinicionesTLauncher()));
		}

		return filas;
	}

	/**
	 * Política: - si no existen definiciones, preguntar si quiere descargarlas - si
	 * ya existen, preguntar si quiere actualizarlas o usar las locales - cancelar
	 * devuelve null
	 */
	protected Boolean resolverPoliticaDefinicionesServidor() {
		try {
			if (!ServidoresProblematicos.existenDefiniciones()) {
				Object[] opciones = new Object[] { MonitorDePID.idioma.guardDefsDescargar(),
						MonitorDePID.idioma.guardDefsCancelar() };

				int respuesta = JOptionPane.showOptionDialog(this, MonitorDePID.idioma.guardDefsNoEncontradasMensaje(),
						MonitorDePID.idioma.guardDefsNoEncontradasTitulo(), JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

				if (respuesta == 0) {
					return Boolean.TRUE;
				}
				return null;
			}

			Object[] opciones = new Object[] { MonitorDePID.idioma.guardDefsUsarLocales(),
					MonitorDePID.idioma.guardDefsActualizar(), MonitorDePID.idioma.guardDefsCancelar() };

			int respuesta = JOptionPane.showOptionDialog(this, MonitorDePID.idioma.guardDefsActualizarMensaje(),
					MonitorDePID.idioma.guardDefsActualizarTitulo(), JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

			if (respuesta == 0) {
				return Boolean.FALSE;
			}
			if (respuesta == 1) {
				return Boolean.TRUE;
			}
			return null;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return null;
		}
	}

	protected void descompilarClase(String claseInterna) {
		if (claseInterna == null || claseInterna.trim().isEmpty()) {
			return;
		}

		try {
			String claseNormalizada = claseInterna.trim();

			if (claseNormalizada.endsWith(".class")) {
				claseNormalizada = claseNormalizada.substring(0, claseNormalizada.length() - ".class".length());
			}

			claseNormalizada = claseNormalizada.replace('/', '.');

			String url = "cfr://" + claseNormalizada;

			CrashDetectorLogger.log(url + " (cfr url)");

			CfrBase gui = TipoGUI.CFR.obtenerGUIPredeterminado(CfrSakuraRiddle.ID, CfrSakuraRiddle::new);
			gui.procesarHipervinculo(url);

		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.guardErrorDescompilar() + ": " + t.getMessage(),
					MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void setEstado(String texto) {
		if (etiquetaEstado != null) {
			etiquetaEstado.setText(texto);
		}
	}

	public void initOverlayCarga() {
		overlayCarga = new JPanel(new BorderLayout());
		overlayCarga.setOpaque(false);

		gifCarga = new JLabel(MonitorDePID.idioma.cargando(), JLabel.CENTER);
		gifCarga.setHorizontalTextPosition(JLabel.CENTER);
		gifCarga.setVerticalTextPosition(JLabel.BOTTOM);

		overlayCarga.add(gifCarga, BorderLayout.CENTER);
		setGlassPane(overlayCarga);
	}

	public void setCargando(boolean valor) {
		cargando = valor;
		if (overlayCarga != null) {
			overlayCarga.setVisible(valor);
		}
		if (botonEscanearTodo != null) {
			botonEscanearTodo.setEnabled(!valor);
		}
		if (botonEscanearServidores != null) {
			botonEscanearServidores.setEnabled(!valor);
		}
		if (botonEscanearMalware != null) {
			botonEscanearMalware.setEnabled(!valor);
		}
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.GUARD;
	}

	public static class FilaServidor {
		public final String servidor;
		public final String fuente;

		public FilaServidor(String servidor, String fuente) {
			this.servidor = servidor;
			this.fuente = fuente;
		}
	}

	public static class FilaMalware {
		public final String mensaje;
		public final String ubicacion;
		public final String clase;
		public final ArticuloMalware articulo;

		public FilaMalware(String mensaje, String ubicacion, String clase, ArticuloMalware articulo) {
			this.mensaje = mensaje != null ? mensaje : "";
			this.ubicacion = ubicacion != null ? ubicacion : "";
			this.clase = clase;
			this.articulo = articulo;
		}
	}

	public class ModeloTablaServidores extends AbstractTableModel {
		private final List<FilaServidor> filas = new ArrayList<>();

		public void establecerFilas(List<FilaServidor> nuevas) {
			filas.clear();
			if (nuevas != null) {
				filas.addAll(nuevas);
			}
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return filas.size();
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public String getColumnName(int column) {
			if (column == 0) {
				return MonitorDePID.idioma.guardColServidor();
			}
			return MonitorDePID.idioma.guardColDefinicion();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			FilaServidor fila = filas.get(rowIndex);
			if (columnIndex == 0) {
				return fila.servidor;
			}
			return fila.fuente;
		}
	}

	public class ModeloTablaMalware extends AbstractTableModel {
		private final List<FilaMalware> filas = new ArrayList<>();

		public void establecerFilas(List<FilaMalware> nuevas) {
			filas.clear();
			if (nuevas != null) {
				filas.addAll(nuevas);
			}
			fireTableDataChanged();
		}

		public FilaMalware obtenerFila(int fila) {
			if (fila < 0 || fila >= filas.size()) {
				return null;
			}
			return filas.get(fila);
		}

		@Override
		public int getRowCount() {
			return filas.size();
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return MonitorDePID.idioma.guardColMensaje();
			case 1:
				return MonitorDePID.idioma.guardColUbicacion();
			case 2:
				return MonitorDePID.idioma.guardColClase();
			default:
				return MonitorDePID.idioma.guardColCfr();
			}
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			FilaMalware fila = filas.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return fila.mensaje;
			case 1:
				return fila.ubicacion;
			case 2:
				return fila.clase != null ? fila.clase : "";
			default:
				return fila.clase != null && !fila.clase.trim().isEmpty() ? MonitorDePID.idioma.guardCfr() : "";
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex != 3) {
				return false;
			}
			FilaMalware fila = obtenerFila(rowIndex);
			return fila != null && fila.clase != null && !fila.clase.trim().isEmpty();
		}
	}

	public class RenderizadorBotonCfr extends JButton implements TableCellRenderer {
		public RenderizadorBotonCfr() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value == null ? "" : String.valueOf(value));
			return this;
		}
	}

	public class EditorBotonCfr extends DefaultCellEditor implements TableCellEditor {
		private final JButton boton;
		private int filaActual = -1;

		public EditorBotonCfr() {
			super(new javax.swing.JTextField());
			boton = new JButton();
			boton.addActionListener(e -> {
				FilaMalware fila = modeloMalware.obtenerFila(filaActual);
				if (fila != null && fila.clase != null && !fila.clase.trim().isEmpty()) {
					descompilarClase(fila.clase);
				}
				fireEditingStopped();
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			filaActual = row;
			boton.setText(value == null ? "" : String.valueOf(value));
			return boton;
		}

		@Override
		public Object getCellEditorValue() {
			return boton.getText();
		}
	}
}