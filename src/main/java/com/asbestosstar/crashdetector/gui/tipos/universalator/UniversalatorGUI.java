package com.asbestosstar.crashdetector.gui.tipos.universalator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.universalator.EscanerUniversalator;
import com.asbestosstar.crashdetector.universalator.ResultadoEscaneoUniversalator;

/**
 * GUI base inspirada en Universalator para detectar mods del lado cliente.
 */
public abstract class UniversalatorGUI extends JFrame implements BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static final Map<String, Supplier<UniversalatorGUI>> GUIS = new HashMap<String, Supplier<UniversalatorGUI>>();

	protected JLabel imagenJelly;
	protected JLabel etiquetaTitulo;
	protected JTextArea areaCita;
	protected JTextArea areaDescripcion;
	protected JLabel etiquetaEstado;

	protected JCheckBox checkUniversalator;
	protected JCheckBox checkMetadataLocales;
	protected JCheckBox checkMetadataRemotos;
	protected JButton botonEscanear;
	protected JButton botonRefrescar;
	protected JButton botonEliminar;

	protected DefaultListModel<String> modeloResultados;
	protected JList<String> listaResultados;
	protected JTextArea areaReporte;

	protected JPanel panelRaiz;
	protected JPanel panelCabecera;
	protected JPanel panelOpciones;
	protected JPanel panelBotones;
	protected JPanel panelResultados;

	protected final EscanerUniversalator escaner = new EscanerUniversalator();
	protected List<ResultadoEscaneoUniversalator> resultados = new ArrayList<ResultadoEscaneoUniversalator>();

	private boolean inicializada;
	private SwingWorker<List<ResultadoEscaneoUniversalator>, Void> worker;

	@Override
	public final void init() {
		if (inicializada) {
			actualizarTextos();
			recargarApariencia();
			setVisible(true);
			toFront();
			requestFocus();
			return;
		}

		inicializada = true;
		construirInterfaz();
		actualizarTextos();
		recargarApariencia();
		setVisible(true);
	}

	private void construirInterfaz() {
		setTitle(MonitorDePID.idioma.universalatorTitulo());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setMinimumSize(new Dimension(920, 680));
		setSize(1120, 780);
		setLocationRelativeTo(null);

		panelRaiz = new JPanel(new BorderLayout(10, 10));
		panelRaiz.setBorder(new EmptyBorder(12, 12, 12, 12));
		setContentPane(panelRaiz);

		panelCabecera = new JPanel(new BorderLayout(12, 8));
		imagenJelly = new JLabel();
		imagenJelly.setPreferredSize(new Dimension(230, 260));
		imagenJelly.setHorizontalAlignment(SwingConstants.CENTER);
		imagenJelly.setVerticalAlignment(SwingConstants.CENTER);

		JPanel info = new JPanel();
		info.setLayout(new javax.swing.BoxLayout(info, javax.swing.BoxLayout.Y_AXIS));
		etiquetaTitulo = new JLabel();
		etiquetaTitulo.setFont(etiquetaTitulo.getFont().deriveFont(Font.BOLD, 24f));
		areaCita = crearAreaInfo(3);
		areaDescripcion = crearAreaInfo(4);
		etiquetaEstado = new JLabel();
		etiquetaEstado.setBorder(new EmptyBorder(6, 2, 2, 2));
		etiquetaEstado.setFont(etiquetaEstado.getFont().deriveFont(Font.BOLD, 13f));

		info.add(etiquetaTitulo);
		info.add(javax.swing.Box.createVerticalStrut(8));
		info.add(areaCita);
		info.add(javax.swing.Box.createVerticalStrut(8));
		info.add(areaDescripcion);
		info.add(javax.swing.Box.createVerticalStrut(6));
		info.add(etiquetaEstado);

		panelCabecera.add(imagenJelly, BorderLayout.WEST);
		panelCabecera.add(info, BorderLayout.CENTER);
		panelRaiz.add(panelCabecera, BorderLayout.NORTH);

		panelOpciones = new JPanel();
		panelOpciones.setLayout(new javax.swing.BoxLayout(panelOpciones, javax.swing.BoxLayout.Y_AXIS));
		panelOpciones.setBorder(BorderFactory.createTitledBorder(""));
		checkUniversalator = new JCheckBox();
		checkUniversalator.setSelected(true);
		checkMetadataLocales = new JCheckBox();
		checkMetadataLocales.setEnabled(false);
		checkMetadataRemotos = new JCheckBox();
		checkMetadataRemotos.setEnabled(false);
		panelOpciones.add(checkUniversalator);
		panelOpciones.add(javax.swing.Box.createVerticalStrut(6));
		panelOpciones.add(checkMetadataLocales);
		panelOpciones.add(javax.swing.Box.createVerticalStrut(6));
		panelOpciones.add(checkMetadataRemotos);

		panelBotones = new JPanel(new java.awt.GridLayout(1, 3, 8, 0));
		botonEscanear = new JButton();
		botonRefrescar = new JButton();
		botonEliminar = new JButton();
		botonEliminar.setEnabled(false);
		panelBotones.add(botonEscanear);
		panelBotones.add(botonRefrescar);
		panelBotones.add(botonEliminar);

		JPanel centroArriba = new JPanel(new BorderLayout(8, 8));
		centroArriba.add(panelOpciones, BorderLayout.CENTER);
		centroArriba.add(panelBotones, BorderLayout.SOUTH);

		modeloResultados = new DefaultListModel<String>();
		listaResultados = new JList<String>(modeloResultados);
		listaResultados.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		areaReporte = new JTextArea();
		areaReporte.setEditable(false);
		areaReporte.setLineWrap(true);
		areaReporte.setWrapStyleWord(true);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				crearPanelConTitulo(MonitorDePID.idioma.universalatorResultadoTitulo(),
						new JScrollPane(listaResultados)),
				crearPanelConTitulo(MonitorDePID.idioma.universalatorReporteTitulo(), new JScrollPane(areaReporte)));
		split.setResizeWeight(0.36);
		split.setContinuousLayout(true);
		split.setBorder(BorderFactory.createEmptyBorder());

		panelResultados = new JPanel(new BorderLayout(8, 8));
		panelResultados.add(centroArriba, BorderLayout.NORTH);
		panelResultados.add(split, BorderLayout.CENTER);
		panelRaiz.add(panelResultados, BorderLayout.CENTER);

		botonEscanear.addActionListener(e -> iniciarEscaneo());
		botonRefrescar.addActionListener(e -> refrescarVista());
		botonEliminar.addActionListener(e -> elegirYEliminar());
	}

	private JPanel crearPanelConTitulo(String titulo, JScrollPane scroll) {
		JPanel panel = new JPanel(new BorderLayout(4, 4));
		panel.setBorder(new EmptyBorder(6, 6, 6, 6));
		JLabel etiqueta = new JLabel(titulo);
		etiqueta.setFont(etiqueta.getFont().deriveFont(Font.BOLD));
		panel.add(etiqueta, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);
		return panel;
	}

	private JTextArea crearAreaInfo(int rows) {
		JTextArea area = new JTextArea();
		area.setEditable(false);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setOpaque(false);
		area.setBorder(BorderFactory.createEmptyBorder());
		area.setRows(rows);
		return area;
	}

	protected final void actualizarTextos() {
		setTitle(MonitorDePID.idioma.universalatorTitulo());
		if (etiquetaTitulo == null) {
			return;
		}
		etiquetaTitulo.setText(MonitorDePID.idioma.universalatorTitulo());
		areaCita.setText(MonitorDePID.idioma.universalatorCita());
		areaDescripcion.setText(MonitorDePID.idioma.universalatorDescripcion() + System.lineSeparator()
				+ System.lineSeparator() + MonitorDePID.idioma.universalatorAdvertenciaSoloCliente());

		checkUniversalator.setText(MonitorDePID.idioma.universalatorMetodoUniversalator());
		checkMetadataLocales.setText(MonitorDePID.idioma.universalatorMetodoMetadatosLocales() + " ("
				+ MonitorDePID.idioma.universalatorNoImplementado() + ")");
		checkMetadataRemotos.setText(MonitorDePID.idioma.universalatorMetodoMetadatosRemotos() + " ("
				+ MonitorDePID.idioma.universalatorNoImplementado() + ")");

		botonEscanear.setText(MonitorDePID.idioma.universalatorEscanear());
		botonRefrescar.setText(MonitorDePID.idioma.universalatorActualizar());
		botonEliminar.setText(MonitorDePID.idioma.universalatorBotonElegirEliminar());

		if (resultados == null || resultados.isEmpty()) {
			etiquetaEstado.setText(MonitorDePID.idioma.universalatorEstadoListo());
		}
	}

	private void iniciarEscaneo() {
		if (worker != null) {
			return;
		}
		if (!checkUniversalator.isSelected()) {
			checkUniversalator.setSelected(true);
		}

		etiquetaEstado.setText(MonitorDePID.idioma.universalatorEstadoEscaneando());
		botonEscanear.setEnabled(false);
		botonEliminar.setEnabled(false);
		modeloResultados.clear();
		areaReporte.setText("");

		worker = new SwingWorker<List<ResultadoEscaneoUniversalator>, Void>() {
			@Override
			protected List<ResultadoEscaneoUniversalator> doInBackground() throws Exception {
				return escaner.escanear();
			}

			@Override
			protected void done() {
				try {
					resultados = get();
					refrescarVista();
				} catch (Exception e) {
					resultados = new ArrayList<ResultadoEscaneoUniversalator>();
					areaReporte.setText(MonitorDePID.idioma.universalatorErrorEscaneo() + System.lineSeparator()
							+ e.getClass().getSimpleName() + ": " + e.getMessage());
					etiquetaEstado.setText(MonitorDePID.idioma.universalatorErrorEscaneo());
				} finally {
					botonEscanear.setEnabled(true);
					botonEliminar.setEnabled(resultados != null && !resultados.isEmpty());
					worker = null;
				}
			}
		};
		worker.execute();
	}

	protected void refrescarVista() {
		modeloResultados.clear();
		StringBuilder reporte = new StringBuilder();
		reporte.append(MonitorDePID.idioma.universalatorResultadoTitulo()).append(": ");
		reporte.append(resultados == null ? 0 : resultados.size()).append(System.lineSeparator())
				.append(System.lineSeparator());

		if (resultados == null || resultados.isEmpty()) {
			reporte.append(MonitorDePID.idioma.universalatorSinResultados());
			areaReporte.setText(reporte.toString());
			etiquetaEstado.setText(MonitorDePID.idioma.universalatorEstadoListo());
			botonEliminar.setEnabled(false);
			return;
		}

		for (ResultadoEscaneoUniversalator r : resultados) {
			modeloResultados.addElement(r.getNombre());
			reporte.append("• ").append(r.getNombre()).append(" (").append(MonitorDePID.idioma.universalatorConfianza())
					.append(": ").append(r.getConfianza()).append("%)").append(System.lineSeparator());
			reporte.append("  ").append(MonitorDePID.idioma.universalatorRazones()).append(": ")
					.append(traducirRazones(r)).append(System.lineSeparator()).append(System.lineSeparator());
		}

		areaReporte.setText(reporte.toString());
		areaReporte.setCaretPosition(0);
		etiquetaEstado.setText(MonitorDePID.idioma.universalatorEstadoListo());
		botonEliminar.setEnabled(true);
	}

	private String traducirRazones(ResultadoEscaneoUniversalator resultado) {
		List<String> motivos = new ArrayList<String>();
		for (String codigo : resultado.getRazones()) {
			if (ResultadoEscaneoUniversalator.RAZON_ENTORNO_CLIENTE.equals(codigo)) {
				motivos.add(MonitorDePID.idioma.universalatorRazonEntornoCliente());
			} else if (ResultadoEscaneoUniversalator.RAZON_METADATOS_CLIENTE.equals(codigo)) {
				motivos.add(MonitorDePID.idioma.universalatorRazonMetadatosCliente());
			} else if (ResultadoEscaneoUniversalator.RAZON_HEURISTICA_NOMBRE.equals(codigo)) {
				motivos.add(MonitorDePID.idioma.universalatorRazonHeuristicaNombre());
			} else {
				motivos.add(codigo);
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < motivos.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(motivos.get(i));
		}
		return sb.toString();
	}

	private void elegirYEliminar() {
		if (resultados == null || resultados.isEmpty()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.universalatorSinResultados(),
					MonitorDePID.idioma.universalatorTitulo(), JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		DefaultListModel<String> modelo = new DefaultListModel<String>();
		for (ResultadoEscaneoUniversalator r : resultados) {
			modelo.addElement(r.getNombre());
		}
		JList<String> lista = new JList<String>(modelo);
		lista.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JDialog dialogo = new JDialog(this, MonitorDePID.idioma.universalatorDialogoElegirMods(), true);
		dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialogo.setLayout(new BorderLayout(8, 8));
		dialogo.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
		dialogo.add(new JScrollPane(lista), BorderLayout.CENTER);
		JTextArea info = new JTextArea(MonitorDePID.idioma.universalatorConfirmarEliminar());
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setEditable(false);
		info.setOpaque(false);
		dialogo.add(info, BorderLayout.NORTH);

		JPanel abajo = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
		JButton ok = new JButton(MonitorDePID.idioma.universalatorBotonElegirEliminar());
		JButton cancelar = new JButton(MonitorDePID.idioma.universalatorActualizar());
		abajo.add(cancelar);
		abajo.add(ok);
		dialogo.add(abajo, BorderLayout.SOUTH);

		cancelar.addActionListener(e -> dialogo.dispose());
		ok.addActionListener(e -> {
			List<String> seleccion = lista.getSelectedValuesList();
			if (seleccion == null || seleccion.isEmpty()) {
				JOptionPane.showMessageDialog(dialogo, MonitorDePID.idioma.universalatorNadaSeleccionado(),
						MonitorDePID.idioma.universalatorTitulo(), JOptionPane.WARNING_MESSAGE);
				return;
			}
			try {
				int movidos = eliminarSeleccionados(seleccion);
				dialogo.dispose();
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.universalatorEliminadosExito() + ": " + movidos,
						MonitorDePID.idioma.universalatorTitulo(), JOptionPane.INFORMATION_MESSAGE);
				refrescarVista();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(dialogo,
						MonitorDePID.idioma.universalatorErrorEliminar() + System.lineSeparator() + ex.getMessage(),
						MonitorDePID.idioma.universalatorTitulo(), JOptionPane.ERROR_MESSAGE);
			}
		});

		dialogo.setSize(560, 420);
		dialogo.setLocationRelativeTo(this);
		dialogo.setVisible(true);
	}

	private int eliminarSeleccionados(List<String> nombresSeleccionados) throws Exception {
		Path carpetaBackup = Statics.carpeta.resolve("universalator_eliminados");
		Files.createDirectories(carpetaBackup);
		int movidos = 0;
		List<ResultadoEscaneoUniversalator> restantes = new ArrayList<ResultadoEscaneoUniversalator>();
		for (ResultadoEscaneoUniversalator r : resultados) {
			if (nombresSeleccionados.contains(r.getNombre())) {
				Path destino = carpetaBackup.resolve(r.getArchivo().getFileName().toString());
				int i = 1;
				while (Files.exists(destino)) {
					destino = carpetaBackup.resolve(i + "_" + r.getArchivo().getFileName().toString());
					i++;
				}
				Files.move(r.getArchivo(), destino, StandardCopyOption.REPLACE_EXISTING);
				movidos++;
			} else {
				restantes.add(r);
			}
		}
		resultados = restantes;
		return movidos;
	}

	@Override
	public TipoGUI<? extends BotonDeBarraLateralDerecha> tipo() {
		return TipoGUI.UNIVERSALATOR;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		return new ArrayList<ElementoConfig>();
	}
}
