package com.asbestosstar.crashdetector.gui.tipos.actualizador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.dto.modpack.tlmods.ColaActualizacionesModpack;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.EditorBotonActualizar;
import com.asbestosstar.crashdetector.gui.elementos.RenderizadorBotonActualizar;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class ActualizadorModsGUI extends JFrame implements CrashDetectorGUI {

	public static final Map<String, Supplier<ActualizadorModsGUI>> GUIS = new HashMap<>();

	public JTextArea areaDescripcion;
	public JLabel imagenTema;
	public JLabel etiquetaEstado;

	public JButton botonEscanear;
	public JButton botonActualizarTodo;

	public JTable tablaActualizaciones;
	public ModeloActualizaciones modeloActualizaciones;

	public Path carpetaInstancia;
	public List<ColaActualizacionesModpack.ActualizacionPendiente> cola = new ArrayList<>();

	public SwingWorker<Void, Void> worker;

	@Override
	public TipoGUI<ActualizadorModsGUI> tipo() {
		return TipoGUI.ACTUALIZADOR_MODS;
	}

	@Override
	public void init() {
		setTitle(MonitorDePID.idioma.actualizadorModsTitulo());
		setSize(1050, 680);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout());

		carpetaInstancia = Paths.get(".").toAbsolutePath().normalize();

		JPanel panelIzquierdo = new JPanel(new BorderLayout(8, 8));
		panelIzquierdo.setPreferredSize(new Dimension(250, 500));

		imagenTema = new JLabel("", JLabel.CENTER);

		areaDescripcion = new JTextArea();
		areaDescripcion.setEditable(false);
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setWrapStyleWord(true);
		areaDescripcion.setText(MonitorDePID.idioma.actualizadorModsDescripcion());

		panelIzquierdo.add(imagenTema, BorderLayout.NORTH);
		panelIzquierdo.add(new JScrollPane(areaDescripcion), BorderLayout.CENTER);

		JPanel panelDerecho = new JPanel(new BorderLayout(8, 8));

		JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
		botonEscanear = new JButton(MonitorDePID.idioma.actualizadorModsBotonEscanear());
		botonActualizarTodo = new JButton(MonitorDePID.idioma.actualizadorModsBotonActualizarTodo());
		etiquetaEstado = new JLabel(MonitorDePID.idioma.actualizadorModsEstadoListo());

		barra.add(botonEscanear);
		barra.add(botonActualizarTodo);
		barra.add(etiquetaEstado);

		modeloActualizaciones = new ModeloActualizaciones();
		tablaActualizaciones = new JTable(modeloActualizaciones);
		tablaActualizaciones.setRowHeight(26);

		tablaActualizaciones.getColumnModel().getColumn(6).setCellRenderer(new RenderizadorBotonActualizar());
		tablaActualizaciones.getColumnModel().getColumn(6).setCellEditor(new EditorBotonActualizar(this));

		panelDerecho.add(barra, BorderLayout.NORTH);
		panelDerecho.add(new JScrollPane(tablaActualizaciones), BorderLayout.CENTER);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzquierdo, panelDerecho);
		split.setDividerLocation(250);
		split.setResizeWeight(0.0);

		add(split, BorderLayout.CENTER);

		botonEscanear.addActionListener(e -> escanearActualizacionesAsync());
		botonActualizarTodo.addActionListener(e -> actualizarTodoAsync());

		recargarApariencia();
		setVisible(true);
	}

	public void escanearActualizacionesAsync() {
		cancelarWorker();

		setEstado(MonitorDePID.idioma.actualizadorModsEstadoEscaneando());
		setBotonesActivos(false);

		worker = new SwingWorker<Void, Void>() {
			private List<ColaActualizacionesModpack.ActualizacionPendiente> encontradas = new ArrayList<>();

			@Override
			protected Void doInBackground() throws Exception {
				encontradas = ColaActualizacionesModpack.crearCola(carpetaInstancia);
				return null;
			}

			@Override
			protected void done() {
				try {
					get();
					cola = encontradas;
					modeloActualizaciones.establecerFilas(cola);

					if (cola.isEmpty()) {
						setEstado(MonitorDePID.idioma.actualizadorModsEstadoSinActualizaciones());
					} else {
						setEstado(MonitorDePID.idioma.actualizadorModsEstadoEncontradas(cola.size()));
					}
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					setEstado(MonitorDePID.idioma.actualizadorModsEstadoError());
					JOptionPane.showMessageDialog(ActualizadorModsGUI.this, t.getMessage(), MonitorDePID.idioma.error(),
							JOptionPane.ERROR_MESSAGE);
				} finally {
					setBotonesActivos(true);
				}
			}
		};

		worker.execute();
	}

	public void actualizarTodoAsync() {
		if (cola == null || cola.isEmpty()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.actualizadorModsSinSeleccion(),
					MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		activarColaAsync(new ArrayList<ColaActualizacionesModpack.ActualizacionPendiente>(cola));
	}

	public void actualizarUnaAsync(int fila) {
		if (fila < 0 || fila >= cola.size()) {
			return;
		}

		List<ColaActualizacionesModpack.ActualizacionPendiente> una = new ArrayList<>();
		una.add(cola.get(fila));
		activarColaAsync(una);
	}

	public void activarColaAsync(final List<ColaActualizacionesModpack.ActualizacionPendiente> seleccion) {
		cancelarWorker();

		setEstado(MonitorDePID.idioma.actualizadorModsEstadoActualizando());
		setBotonesActivos(false);

		worker = new SwingWorker<Void, Void>() {
			private int aplicadas;

			@Override
			protected Void doInBackground() throws Exception {
				aplicadas = ColaActualizacionesModpack.activarCola(carpetaInstancia, seleccion);
				return null;
			}

			@Override
			protected void done() {
				try {
					get();
					setEstado(MonitorDePID.idioma.actualizadorModsEstadoActualizadas(aplicadas));
					escanearActualizacionesAsync();
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					setEstado(MonitorDePID.idioma.actualizadorModsEstadoError());
					JOptionPane.showMessageDialog(ActualizadorModsGUI.this, t.getMessage(), MonitorDePID.idioma.error(),
							JOptionPane.ERROR_MESSAGE);
					setBotonesActivos(true);
				}
			}
		};

		worker.execute();
	}

	protected void cancelarWorker() {
		if (worker != null && !worker.isDone()) {
			worker.cancel(true);
		}
	}

	protected void setEstado(String texto) {
		if (etiquetaEstado != null) {
			etiquetaEstado.setText(texto);
		}
	}

	protected void setBotonesActivos(boolean activo) {
		if (botonEscanear != null) {
			botonEscanear.setEnabled(activo);
		}
		if (botonActualizarTodo != null) {
			botonActualizarTodo.setEnabled(activo && cola != null && !cola.isEmpty());
		}
		if (tablaActualizaciones != null) {
			tablaActualizaciones.setEnabled(activo);
		}
	}

	public static class ModeloActualizaciones extends AbstractTableModel {
		private final List<ColaActualizacionesModpack.ActualizacionPendiente> filas = new ArrayList<>();

		public void establecerFilas(List<ColaActualizacionesModpack.ActualizacionPendiente> nuevas) {
			filas.clear();
			if (nuevas != null) {
				filas.addAll(nuevas);
			}
			fireTableDataChanged();
		}

		public ColaActualizacionesModpack.ActualizacionPendiente obtener(int fila) {
			return filas.get(fila);
		}

		@Override
		public int getRowCount() {
			return filas.size();
		}

		@Override
		public int getColumnCount() {
			return 7;
		}

		@Override
		public String getColumnName(int col) {
			switch (col) {
			case 0:
				return MonitorDePID.idioma.actualizadorModsColumnaMod();
			case 1:
				return MonitorDePID.idioma.actualizadorModsColumnaActual();
			case 2:
				return MonitorDePID.idioma.actualizadorModsColumnaNueva();
			case 3:
				return MonitorDePID.idioma.actualizadorModsColumnaFuente();
			case 4:
				return MonitorDePID.idioma.actualizadorModsColumnaLoader();
			case 5:
				return MonitorDePID.idioma.actualizadorModsColumnaRuta();
			case 6:
				return MonitorDePID.idioma.actualizadorModsColumnaAccion();
			default:
				return "";
			}
		}

		@Override
		public Object getValueAt(int fila, int col) {
			ColaActualizacionesModpack.ActualizacionPendiente a = filas.get(fila);

			switch (col) {
			case 0:
				return a.nombreActual;
			case 1:
				return a.curseForgeFileIdActual > 0 ? String.valueOf(a.curseForgeFileIdActual)
						: valor(a.modrinthVersionIdActual);
			case 2:
				return a.nombreNuevo;
			case 3:
				return a.fuente;
			case 4:
				return a.loader;
			case 5:
				return a.rutaRelativa;
			case 6:
				return MonitorDePID.idioma.actualizadorModsBotonActualizarUno();
			default:
				return "";
			}
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return col == 6;
		}

		private String valor(String s) {
			return s == null ? "" : s;
		}
	}
}