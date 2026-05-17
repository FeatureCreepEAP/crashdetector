package com.asbestosstar.crashdetector.gui.tipos.depmap;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.elementos.ElementoOverlayCarga;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrBase;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrSakuraRiddle;

/**
 * GUI abstracta para visualizar el mapa de dependencias entre mods.
 *
 * Las dependencias se infieren a partir de referencias de clases entre mods.
 *
 * Soporta: - dependencias directas - dependencias múltiples - dependencias
 * recursivas - dependencias mutuas/cíclicas - inspección de referencias entre
 * mod y dependencia - descompilación de clases relacionadas - comprobación de
 * dependencias no alineadas por paquete
 *
 * Idea inspirada por lilybrows, vtuber del servidor de MCForge. Crédito:
 * https://discord.com/channels/1129059589325852724/1129069799545241703/1445644099544813729
 */
public abstract class MapaDeDependenciasGUI extends JFrame implements BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<MapaDeDependenciasGUI>> GUIS = new HashMap<>();

	public JTabbedPane pestanas;
	public JPanel panelMapa;
	public GrafoDependenciasPanel panelGrafo;
	public JTree arbolDependencias;
	public DefaultTreeModel modeloArbol;
	public JTextArea areaDetalles;

	public JComboBox<String> comboModsMapa;
	public JComboBox<String> comboModDependientes;
	public JComboBox<String> comboModBaseNoAlineadas;
	public JComboBox<String> comboModDependienteNoAlineadas;
	public JComboBox<String> comboPaqueteNoAlineadas;

	public JButton botonRecargar;
	public JButton botonDescompilar;
	public JButton botonVerReferencias;
	public JButton botonComprobarNoAlineadas;

	public JTextArea areaListaDependientes;
	public JTextArea areaAyuda;
	public JLabel imagenNimu;

	public ElementoOverlayCarga overlayCarga;
	public volatile boolean cargando = false;

	public ImageIcon iconoMod;
	public ImageIcon iconoDependencia;
	public ImageIcon iconoDependiente;
	public ImageIcon iconoClase;
	public ImageIcon iconoReferencia;

	public SwingWorker<Void, Void> workerCarga;

	public boolean inicializada = false;

	/**
	 * Mapa de dependencias directas: clave = mod origen valor = mods a los que
	 * depende
	 */
	public Map<ArchivoDeMod, Set<ArchivoDeMod>> dependenciasDirectas = new LinkedHashMap<>();

	/**
	 * Mapa de dependientes directos: clave = mod destino valor = mods que dependen
	 * de él
	 */
	public Map<ArchivoDeMod, Set<ArchivoDeMod>> dependientesDirectos = new LinkedHashMap<>();

	/**
	 * Referencias agrupadas por enlace origen->destino.
	 */
	public Map<String, List<ReferenciaEntreMods>> referenciasPorEnlace = new LinkedHashMap<>();

	public MapaDeDependenciasGUI() {
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

		setTitle(MonitorDePID.idioma.depmapTitulo());
		setSize(1380, 900);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout());

		iconoMod = crearIcono(Statics.carpeta.resolve("imagenes/mod.png").toString(), "M");
		iconoDependencia = crearIcono(Statics.carpeta.resolve("imagenes/referencia_metodo.png").toString(), "D");
		iconoDependiente = crearIcono(Statics.carpeta.resolve("imagenes/referencia_campo.png").toString(), "DP");
		iconoClase = crearIcono(Statics.carpeta.resolve("imagenes/clase.png").toString(), "C");
		iconoReferencia = crearIcono(Statics.carpeta.resolve("imagenes/metodo.png").toString(), "R");

		JPanel barraSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
		barraSuperior.setOpaque(false);
		barraSuperior.setBorder(new EmptyBorder(6, 6, 6, 6));

		comboModsMapa = new JComboBox<>();
		comboModDependientes = new JComboBox<>();
		comboModBaseNoAlineadas = new JComboBox<>();
		comboModDependienteNoAlineadas = new JComboBox<>();
		comboPaqueteNoAlineadas = new JComboBox<>();

		botonRecargar = new JButton(MonitorDePID.idioma.depmapRecargar());
		botonDescompilar = new JButton(MonitorDePID.idioma.depmapDescompilarSeleccion());
		botonVerReferencias = new JButton(MonitorDePID.idioma.depmapVerReferencias());
		botonComprobarNoAlineadas = new JButton(MonitorDePID.idioma.depmapComprobarNoAlineadas());

		// Limitar el ancho visual de los combos para que no rompan la barra superior
		java.awt.Dimension tamComboGrande = new java.awt.Dimension(280, 26);
		java.awt.Dimension tamComboMediano = new java.awt.Dimension(240, 26);

		comboModsMapa.setPreferredSize(tamComboGrande);
		comboModsMapa.setMinimumSize(tamComboGrande);
		comboModsMapa.setMaximumSize(tamComboGrande);

		comboModDependientes.setPreferredSize(tamComboGrande);
		comboModDependientes.setMinimumSize(tamComboGrande);
		comboModDependientes.setMaximumSize(tamComboGrande);

		comboModBaseNoAlineadas.setPreferredSize(tamComboGrande);
		comboModBaseNoAlineadas.setMinimumSize(tamComboGrande);
		comboModBaseNoAlineadas.setMaximumSize(tamComboGrande);

		comboPaqueteNoAlineadas.setPreferredSize(tamComboMediano);
		comboPaqueteNoAlineadas.setMinimumSize(tamComboMediano);
		comboPaqueteNoAlineadas.setMaximumSize(tamComboMediano);

		// Renderer para recortar visualmente los nombres largos sin perder tooltip
		comboModsMapa.setRenderer(new RenderizadorComboRecortado(46));
		comboModDependientes.setRenderer(new RenderizadorComboRecortado(46));
		comboModBaseNoAlineadas.setRenderer(new RenderizadorComboRecortado(46));
		comboPaqueteNoAlineadas.setRenderer(new RenderizadorComboRecortado(40));

		barraSuperior.add(new JLabel(MonitorDePID.idioma.depmapSeleccionarMod() + ":"));
		barraSuperior.add(comboModsMapa);
		barraSuperior.add(botonRecargar);
		barraSuperior.add(botonVerReferencias);
		barraSuperior.add(botonDescompilar);

		pestanas = new JTabbedPane();

		// =========================
		// Pestaña 1: mapa
		// =========================
		panelMapa = new JPanel(new BorderLayout());
		panelMapa.setOpaque(false);

		imagenNimu = new JLabel();

		panelGrafo = new GrafoDependenciasPanel();
		panelGrafo.setOpaque(true);

		JScrollPane scrollGrafo = new JScrollPane(panelGrafo);
		scrollGrafo.getHorizontalScrollBar().setUnitIncrement(24);
		scrollGrafo.getVerticalScrollBar().setUnitIncrement(24);

		areaDetalles = new JTextArea();
		areaDetalles.setEditable(false);
		areaDetalles.setLineWrap(true);
		areaDetalles.setWrapStyleWord(true);
		areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 12));

		JSplitPane splitMapa = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollGrafo, new JScrollPane(areaDetalles));
		splitMapa.setDividerLocation(930);
		splitMapa.setResizeWeight(0.72);
		splitMapa.setOneTouchExpandable(true);
		splitMapa.setContinuousLayout(true);

		panelMapa.add(splitMapa, BorderLayout.CENTER);

		// =========================
		// Pestaña 2: dependientes
		// =========================
		JPanel panelDependientes = new JPanel(new BorderLayout());
		panelDependientes.setOpaque(false);

		JPanel barraDependientes = new JPanel(new FlowLayout(FlowLayout.LEFT));
		barraDependientes.setOpaque(false);
		barraDependientes.add(new JLabel(MonitorDePID.idioma.depmapSeleccionarMod() + ":"));
		barraDependientes.add(comboModDependientes);

		JPanel barraNoAlineadas = new JPanel(new FlowLayout(FlowLayout.LEFT));
		barraNoAlineadas.setOpaque(false);
		barraNoAlineadas.add(new JLabel(MonitorDePID.idioma.depmapSeleccionarModBase() + ":"));
		barraNoAlineadas.add(comboModBaseNoAlineadas);
		barraNoAlineadas.add(new JLabel(MonitorDePID.idioma.depmapSeleccionarPaquete() + ":"));
		barraNoAlineadas.add(comboPaqueteNoAlineadas);
		barraNoAlineadas.add(botonComprobarNoAlineadas);

		areaListaDependientes = new JTextArea();
		areaListaDependientes.setEditable(false);
		areaListaDependientes.setLineWrap(true);
		areaListaDependientes.setWrapStyleWord(true);
		areaListaDependientes.setFont(new Font("Monospaced", Font.PLAIN, 12));

		arbolDependencias = new JTree();
		arbolDependencias.setRootVisible(false);
		arbolDependencias.setShowsRootHandles(true);
		arbolDependencias.setCellRenderer(new RenderizadorCeldasDependencias());

		modeloArbol = new DefaultTreeModel(new DefaultMutableTreeNode(MonitorDePID.idioma.cargando()));
		arbolDependencias.setModel(modeloArbol);

		JSplitPane splitDependientes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(areaListaDependientes), new JScrollPane(arbolDependencias));
		splitDependientes.setDividerLocation(430);
		splitDependientes.setResizeWeight(0.38);

		JPanel bloqueSuperiorDependientes = new JPanel(new BorderLayout());
		bloqueSuperiorDependientes.setOpaque(false);
		bloqueSuperiorDependientes.add(barraDependientes, BorderLayout.NORTH);
		bloqueSuperiorDependientes.add(barraNoAlineadas, BorderLayout.CENTER);

		panelDependientes.add(bloqueSuperiorDependientes, BorderLayout.NORTH);
		panelDependientes.add(splitDependientes, BorderLayout.CENTER);

		pestanas.addTab(MonitorDePID.idioma.depmapPestanaMapa(), panelMapa);
		pestanas.addTab(MonitorDePID.idioma.depmapPestanaDependientes(), panelDependientes);

		// =========================
		// Ayuda inferior + imagen a la derecha
		// =========================
		JPanel panelInferior = new JPanel(new BorderLayout());
		panelInferior.setOpaque(false);
		panelInferior.setBorder(new EmptyBorder(8, 8, 8, 8));

		areaAyuda = new JTextArea();
		areaAyuda.setEditable(false);
		areaAyuda.setLineWrap(true);
		areaAyuda.setWrapStyleWord(true);
		areaAyuda.setOpaque(false);
		areaAyuda.setFont(new Font("SansSerif", Font.PLAIN, 12));
		areaAyuda.setText(MonitorDePID.idioma.depmapAyuda1() + "\n\n" + MonitorDePID.idioma.depmapAyuda2() + "\n"
				+ MonitorDePID.idioma.depmapAyuda3() + "\n" + MonitorDePID.idioma.depmapAyuda4() + "\n"
				+ MonitorDePID.idioma.depmapAyuda5());

		JPanel panelAyuda = new JPanel(new BorderLayout());
		panelAyuda.setOpaque(false);
		panelAyuda.add(areaAyuda, BorderLayout.CENTER);

		JPanel panelImagenDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		panelImagenDerecha.setOpaque(false);
		panelImagenDerecha.add(imagenNimu);

		panelInferior.add(panelAyuda, BorderLayout.CENTER);
		panelInferior.add(panelImagenDerecha, BorderLayout.EAST);

		add(barraSuperior, BorderLayout.NORTH);
		add(pestanas, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);

		comboModsMapa.addActionListener(e -> {
			Object seleccionado = comboModsMapa.getSelectedItem();
			comboModsMapa.setToolTipText(seleccionado == null ? null : String.valueOf(seleccionado));

			if (panelGrafo != null) {
				panelGrafo.reiniciarVista();
			}
		});
		comboModDependientes.addActionListener(e -> actualizarListaDependientes());
		comboModBaseNoAlineadas.addActionListener(e -> actualizarDependientesYPaquetesNoAlineadas());
		comboPaqueteNoAlineadas.addActionListener(e -> {
			// No hace falta recalcular nada aquí; el paquete se consume al pulsar
			// comprobar.
		});
		botonRecargar.addActionListener(e -> iniciarCargaPesada());
		botonVerReferencias.addActionListener(e -> mostrarReferenciasDelModSeleccionado());
		botonDescompilar.addActionListener(e -> descompilarElementoSeleccionado());
		botonComprobarNoAlineadas.addActionListener(e -> comprobarDependenciasNoAlineadas());

		arbolDependencias.addTreeSelectionListener(e -> {
			TreePath ruta = arbolDependencias.getSelectionPath();
			if (ruta == null) {
				return;
			}
			DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) ruta.getLastPathComponent();
			Object user = nodo.getUserObject();
			Object real = (user instanceof NodoConTexto) ? ((NodoConTexto) user).objeto() : user;
			mostrarDetalles(real);
		});

		arbolDependencias.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					mostrarMenuContextual(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					mostrarMenuContextual(e);
				}
			}
		});

		initOverlayCarga();
		setCargando(false);

		recargarApariencia();
		setVisible(true);
		iniciarCargaPesada();
	}

	public void iniciarCargaPesada() {
		if (workerCarga != null && !workerCarga.isDone()) {
			workerCarga.cancel(true);
		}

		setCargando(true);

		SwingWorker<Void, Void> nuevoWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				try {
					Buscardor.cargarYPrecargarClasesEnCache();
					reconstruirMapaDependencias();
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

					recargarCombos();
					actualizarListaDependientes();
					construirArbolResumen();

					if (panelGrafo != null) {
						panelGrafo.reiniciarVista();
					}
				} catch (java.util.concurrent.CancellationException ex) {
					return;
				} finally {
					if (workerCarga == this) {
						setCargando(false);
					}
				}
			}
		};

		workerCarga = nuevoWorker;
		nuevoWorker.execute();
	}

	/**
	 * Renderer para combos que recorta textos demasiado largos sin hacer que el
	 * JComboBox crezca de ancho.
	 */
	public class RenderizadorComboRecortado extends javax.swing.plaf.basic.BasicComboBoxRenderer {

		private final int maxCaracteres;

		public RenderizadorComboRecortado(int maxCaracteres) {
			this.maxCaracteres = Math.max(8, maxCaracteres);
		}

		@Override
		public Component getListCellRendererComponent(javax.swing.JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {

			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

			String textoCompleto = value == null ? "" : String.valueOf(value);
			setText(recortarTexto(textoCompleto, maxCaracteres));
			setToolTipText(textoCompleto);

			return this;
		}

		private String recortarTexto(String texto, int maxChars) {
			if (texto == null) {
				return "";
			}
			if (texto.length() <= maxChars) {
				return texto;
			}
			return texto.substring(0, Math.max(0, maxChars - 3)) + "...";
		}
	}

	/**
	 * Reconstruye el mapa completo de dependencias usando referencias de clases.
	 *
	 * Esta versión procesa los mods en paralelo para aprovechar múltiples núcleos.
	 */
	public void reconstruirMapaDependencias() {
		dependenciasDirectas.clear();
		dependientesDirectos.clear();
		referenciasPorEnlace.clear();

		List<ArchivoDeMod> mods = obtenerTodosLosModsYSubmodsRecursivos();

		for (ArchivoDeMod mod : mods) {
			dependenciasDirectas.putIfAbsent(mod, new LinkedHashSet<>());
			dependientesDirectos.putIfAbsent(mod, new LinkedHashSet<>());
		}

		// Índice global clase -> mod dueño
		Map<String, ArchivoDeMod> indiceClaseAMod = new HashMap<>();
		for (ArchivoDeMod mod : mods) {
			for (String clase : mod.obtenerTodosLosNombresDeClases()) {
				indiceClaseAMod.put(normalizarNombreClaseInterno(clase), mod);
			}
		}

		// Número de hilos: dejar uno libre para no ahogar la UI / sistema
		int hilos = Math.max(1, Runtime.getRuntime().availableProcessors() - 1);
		java.util.concurrent.ExecutorService pool = java.util.concurrent.Executors.newFixedThreadPool(hilos);

		List<java.util.concurrent.Future<ResultadoDependenciasPorMod>> futures = new ArrayList<>();

		for (ArchivoDeMod origen : mods) {
			futures.add(pool.submit(() -> analizarDependenciasDeMod(origen, indiceClaseAMod)));
		}

		try {
			for (java.util.concurrent.Future<ResultadoDependenciasPorMod> future : futures) {
				ResultadoDependenciasPorMod parcial = future.get();

				for (Map.Entry<ArchivoDeMod, Set<ArchivoDeMod>> e : parcial.dependencias.entrySet()) {
					dependenciasDirectas.computeIfAbsent(e.getKey(), k -> new LinkedHashSet<>()).addAll(e.getValue());
				}

				for (Map.Entry<ArchivoDeMod, Set<ArchivoDeMod>> e : parcial.dependientes.entrySet()) {
					dependientesDirectos.computeIfAbsent(e.getKey(), k -> new LinkedHashSet<>()).addAll(e.getValue());
				}

				for (Map.Entry<String, List<ReferenciaEntreMods>> e : parcial.referencias.entrySet()) {
					referenciasPorEnlace.computeIfAbsent(e.getKey(), k -> new ArrayList<>()).addAll(e.getValue());
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		} catch (java.util.concurrent.ExecutionException e) {
			throw new RuntimeException(e);
		} finally {
			pool.shutdownNow();
		}
	}

	/**
	 * Analiza un único mod y devuelve sus dependencias parciales.
	 *
	 * Se ejecuta en paralelo para repartir el trabajo entre varios núcleos.
	 */
	private ResultadoDependenciasPorMod analizarDependenciasDeMod(ArchivoDeMod origen,
			Map<String, ArchivoDeMod> indiceClaseAMod) {

		ResultadoDependenciasPorMod resultado = new ResultadoDependenciasPorMod();

		for (String clase : origen.obtenerTodosLosNombresDeClases()) {
			String claseInterna = normalizarNombreClaseInterno(clase);
			if (claseInterna == null || !origen.existeClase(claseInterna)) {
				continue;
			}

			List<ArchivoDeMod.InfoMetodo> metodos = origen.obtenerMetodosConReferencias(claseInterna);
			for (ArchivoDeMod.InfoMetodo metodo : metodos) {
				for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasAMetodos()) {
					procesarReferenciaEntreModsParcial(resultado, origen, claseInterna, metodo, ref, indiceClaseAMod);
				}
				for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
					procesarReferenciaEntreModsParcial(resultado, origen, claseInterna, metodo, ref, indiceClaseAMod);
				}
			}
		}

		return resultado;
	}

	/**
	 * Procesa una referencia y la guarda en un resultado parcial local.
	 *
	 * No toca los mapas globales para evitar contención entre hilos.
	 */
	private void procesarReferenciaEntreModsParcial(ResultadoDependenciasPorMod resultado, ArchivoDeMod origen,
			String claseOrigenInterna, ArchivoDeMod.InfoMetodo metodo, ArchivoDeMod.Referencia ref,
			Map<String, ArchivoDeMod> indiceClaseAMod) {

		String claseDestinoInterna = normalizarNombreClaseInterno(ref.obtenerClase());
		if (claseDestinoInterna == null) {
			return;
		}

		ArchivoDeMod destino = indiceClaseAMod.get(claseDestinoInterna);
		if (destino == null) {
			return;
		}

		if (destino == origen) {
			return;
		}

		resultado.dependencias.computeIfAbsent(origen, k -> new LinkedHashSet<>()).add(destino);

		resultado.dependientes.computeIfAbsent(destino, k -> new LinkedHashSet<>()).add(origen);

		String claveEnlace = claveEnlace(origen, destino);
		resultado.referencias.computeIfAbsent(claveEnlace, k -> new ArrayList<>()).add(new ReferenciaEntreMods(origen,
				destino, claseOrigenInterna, metodo.obtenerNombre(), metodo.obtenerDescriptor(), ref));
	}

	/**
	 * Resultado parcial del análisis de dependencias de un mod.
	 */
	private static class ResultadoDependenciasPorMod {
		public final Map<ArchivoDeMod, Set<ArchivoDeMod>> dependencias = new LinkedHashMap<>();
		public final Map<ArchivoDeMod, Set<ArchivoDeMod>> dependientes = new LinkedHashMap<>();
		public final Map<String, List<ReferenciaEntreMods>> referencias = new LinkedHashMap<>();
	}

	/**
	 * Procesa una referencia y crea el enlace entre mods si aplica.
	 */
	public void procesarReferenciaEntreMods(ArchivoDeMod origen, String claseOrigenInterna,
			ArchivoDeMod.InfoMetodo metodo, ArchivoDeMod.Referencia ref, Map<String, ArchivoDeMod> indiceClaseAMod) {

		String claseDestinoInterna = normalizarNombreClaseInterno(ref.obtenerClase());
		if (claseDestinoInterna == null) {
			return;
		}

		ArchivoDeMod destino = indiceClaseAMod.get(claseDestinoInterna);
		if (destino == null) {
			return;
		}

		// No contar dependencia interna dentro del mismo mod
		if (destino == origen) {
			return;
		}

		dependenciasDirectas.computeIfAbsent(origen, k -> new LinkedHashSet<>()).add(destino);
		dependientesDirectos.computeIfAbsent(destino, k -> new LinkedHashSet<>()).add(origen);

		String claveEnlace = claveEnlace(origen, destino);
		referenciasPorEnlace.computeIfAbsent(claveEnlace, k -> new ArrayList<>()).add(new ReferenciaEntreMods(origen,
				destino, claseOrigenInterna, metodo.obtenerNombre(), metodo.obtenerDescriptor(), ref));
	}

	public void recargarCombos() {
		List<ArchivoDeMod> mods = obtenerTodosLosModsYSubmodsRecursivos();
		List<String> nombresMods = new ArrayList<>();

		for (ArchivoDeMod mod : mods) {
			nombresMods.add(mod.ubicacion_para_publicar());
		}

		Collections.sort(nombresMods);

		DefaultComboBoxModel<String> modeloMapa = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<String> modeloDependientes = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<String> modeloBase = new DefaultComboBoxModel<>();

		modeloMapa.addElement(MonitorDePID.idioma.depmapTodos());
		modeloDependientes.addElement(MonitorDePID.idioma.depmapTodos());
		modeloBase.addElement(MonitorDePID.idioma.depmapTodos());

		for (String nombre : nombresMods) {
			modeloMapa.addElement(nombre);
			modeloDependientes.addElement(nombre);
			modeloBase.addElement(nombre);
		}

		comboModsMapa.setModel(modeloMapa);
		comboModDependientes.setModel(modeloDependientes);
		comboModBaseNoAlineadas.setModel(modeloBase);

		// Ya no participa en la comprobación de no alineadas
		comboModDependienteNoAlineadas.setModel(new DefaultComboBoxModel<>());

		actualizarDependientesYPaquetesNoAlineadas();

		Object seleccionadoMapa = comboModsMapa.getSelectedItem();
		comboModsMapa.setToolTipText(seleccionadoMapa == null ? null : String.valueOf(seleccionadoMapa));

		Object seleccionadoDependientes = comboModDependientes.getSelectedItem();
		comboModDependientes
				.setToolTipText(seleccionadoDependientes == null ? null : String.valueOf(seleccionadoDependientes));

		Object seleccionadoBase = comboModBaseNoAlineadas.getSelectedItem();
		comboModBaseNoAlineadas.setToolTipText(seleccionadoBase == null ? null : String.valueOf(seleccionadoBase));

		Object seleccionadoPaquete = comboPaqueteNoAlineadas.getSelectedItem();
		comboPaqueteNoAlineadas
				.setToolTipText(seleccionadoPaquete == null ? null : String.valueOf(seleccionadoPaquete));

	}

	public void construirArbolResumen() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.depmapDependencias());

		List<ArchivoDeMod> mods = obtenerTodosLosModsYSubmodsRecursivos();
		mods.sort((a, b) -> {
			int dependientesA = dependientesDirectos.getOrDefault(a, Collections.emptySet()).size();
			int dependientesB = dependientesDirectos.getOrDefault(b, Collections.emptySet()).size();
			if (dependientesA != dependientesB) {
				return Integer.compare(dependientesB, dependientesA);
			}

			int dependenciasA = dependenciasDirectas.getOrDefault(a, Collections.emptySet()).size();
			int dependenciasB = dependenciasDirectas.getOrDefault(b, Collections.emptySet()).size();
			if (dependenciasA != dependenciasB) {
				return Integer.compare(dependenciasB, dependenciasA);
			}

			return a.ubicacion_para_publicar().compareToIgnoreCase(b.ubicacion_para_publicar());
		});

		for (ArchivoDeMod mod : mods) {
			int totalDependencias = dependenciasDirectas.getOrDefault(mod, Collections.emptySet()).size();
			int totalDependientes = dependientesDirectos.getOrDefault(mod, Collections.emptySet()).size();

			String textoMod = mod.ubicacion_para_publicar() + " [" + MonitorDePID.idioma.depmapDependientes() + ": "
					+ totalDependientes + ", " + MonitorDePID.idioma.depmapDependencias() + ": " + totalDependencias
					+ "]";

			DefaultMutableTreeNode nodoMod = new DefaultMutableTreeNode(new NodoConTexto(textoMod, mod));

			Set<ArchivoDeMod> deps = dependenciasDirectas.getOrDefault(mod, Collections.emptySet());
			DefaultMutableTreeNode nodoDeps = new DefaultMutableTreeNode(
					new NodoConTexto(MonitorDePID.idioma.depmapDependencias() + " (" + deps.size() + ")",
							new Object[] { mod, "DEPENDENCIAS_CONTENEDOR" }));

			List<ArchivoDeMod> depsOrdenadas = new ArrayList<>(deps);
			depsOrdenadas.sort((a, b) -> {
				int dependientesA = dependientesDirectos.getOrDefault(a, Collections.emptySet()).size();
				int dependientesB = dependientesDirectos.getOrDefault(b, Collections.emptySet()).size();
				if (dependientesA != dependientesB) {
					return Integer.compare(dependientesB, dependientesA);
				}
				return a.ubicacion_para_publicar().compareToIgnoreCase(b.ubicacion_para_publicar());
			});

			for (ArchivoDeMod dep : depsOrdenadas) {
				int totalDependientesDep = dependientesDirectos.getOrDefault(dep, Collections.emptySet()).size();

				DefaultMutableTreeNode nodoDep = new DefaultMutableTreeNode(
						new NodoConTexto(dep.ubicacion_para_publicar() + " (" + totalDependientesDep + ")",
								new Object[] { mod, dep, "DEPENDENCIA" }));
				nodoDeps.add(nodoDep);
			}

			Set<ArchivoDeMod> depsDe = dependientesDirectos.getOrDefault(mod, Collections.emptySet());
			DefaultMutableTreeNode nodoDependientes = new DefaultMutableTreeNode(
					new NodoConTexto(MonitorDePID.idioma.depmapDependientes() + " (" + depsDe.size() + ")",
							new Object[] { mod, "DEPENDIENTES_CONTENEDOR" }));

			List<ArchivoDeMod> dependientesOrdenados = new ArrayList<>(depsDe);
			dependientesOrdenados.sort((a, b) -> {
				int dependientesA = dependientesDirectos.getOrDefault(a, Collections.emptySet()).size();
				int dependientesB = dependientesDirectos.getOrDefault(b, Collections.emptySet()).size();
				if (dependientesA != dependientesB) {
					return Integer.compare(dependientesB, dependientesA);
				}
				return a.ubicacion_para_publicar().compareToIgnoreCase(b.ubicacion_para_publicar());
			});

			for (ArchivoDeMod dep : dependientesOrdenados) {
				int totalDependientesDep = dependientesDirectos.getOrDefault(dep, Collections.emptySet()).size();

				nodoDependientes.add(new DefaultMutableTreeNode(
						new NodoConTexto(dep.ubicacion_para_publicar() + " (" + totalDependientesDep + ")",
								new Object[] { dep, mod, "DEPENDENCIA" })));
			}

			nodoMod.add(nodoDeps);
			nodoMod.add(nodoDependientes);
			raiz.add(nodoMod);
		}

		modeloArbol.setRoot(raiz);
		modeloArbol.reload();
	}

	/**
	 * Actualiza el área de dependientes ordenando de mayor a menor.
	 */
	public void actualizarListaDependientes() {
		String filtro = (String) comboModDependientes.getSelectedItem();

		List<ArchivoDeMod> mods = obtenerTodosLosModsYSubmodsRecursivos();
		mods.sort((a, b) -> {
			int da = dependientesDirectos.getOrDefault(a, Collections.emptySet()).size();
			int db = dependientesDirectos.getOrDefault(b, Collections.emptySet()).size();
			if (da != db) {
				return Integer.compare(db, da);
			}
			return a.ubicacion_para_publicar().compareToIgnoreCase(b.ubicacion_para_publicar());
		});

		StringBuilder sb = new StringBuilder();

		for (ArchivoDeMod mod : mods) {
			if (!MonitorDePID.idioma.depmapTodos().equals(filtro) && !mod.ubicacion_para_publicar().equals(filtro)) {
				continue;
			}

			int total = dependientesDirectos.getOrDefault(mod, Collections.emptySet()).size();
			sb.append(mod.ubicacion_para_publicar()).append(" -> ").append(total).append(" ")
					.append(MonitorDePID.idioma.depmapDependientes()).append("\n");

			List<ArchivoDeMod> deps = new ArrayList<>(dependientesDirectos.getOrDefault(mod, Collections.emptySet()));
			deps.sort(Comparator.comparing(ArchivoDeMod::ubicacion_para_publicar));

			if (deps.isEmpty()) {
				sb.append("  - ").append(MonitorDePID.idioma.depmapSinDependencias()).append("\n");
			} else {
				for (ArchivoDeMod dep : deps) {
					sb.append("  - ").append(dep.ubicacion_para_publicar()).append("\n");
				}
			}
			sb.append("\n");
		}

		areaListaDependientes.setText(sb.toString());
	}

	public void actualizarDependientesYPaquetesNoAlineadas() {
		String nombreBase = (String) comboModBaseNoAlineadas.getSelectedItem();
		DefaultComboBoxModel<String> modeloPaquetes = new DefaultComboBoxModel<>();

		if (nombreBase == null || MonitorDePID.idioma.depmapTodos().equals(nombreBase)) {
			comboPaqueteNoAlineadas.setModel(modeloPaquetes);
			return;
		}

		ArchivoDeMod modBase = buscarModPorUbicacionPublica(nombreBase);
		if (modBase == null) {
			comboPaqueteNoAlineadas.setModel(modeloPaquetes);
			return;
		}

		Set<String> paquetes = new LinkedHashSet<>();
		for (String clase : modBase.clases()) {
			String paquete = paqueteDe(clase);
			if (!paquete.isEmpty()) {
				paquetes.add(paquete);
			}
		}

		List<String> paquetesOrdenados = new ArrayList<>(paquetes);
		Collections.sort(paquetesOrdenados);

		for (String paquete : paquetesOrdenados) {
			modeloPaquetes.addElement(paquete);
		}

		comboPaqueteNoAlineadas.setModel(modeloPaquetes);

		// Ya no se usa el combo de dependiente para esta comprobación
		comboModDependienteNoAlineadas.setModel(new DefaultComboBoxModel<>());
	}

	public void actualizarPaquetesNoAlineadas() {
		String nombreBase = (String) comboModBaseNoAlineadas.getSelectedItem();
		DefaultComboBoxModel<String> modeloPaquetes = new DefaultComboBoxModel<>();

		if (nombreBase == null || MonitorDePID.idioma.depmapTodos().equals(nombreBase)) {
			comboPaqueteNoAlineadas.setModel(modeloPaquetes);
			return;
		}

		ArchivoDeMod modBase = buscarModPorUbicacionPublica(nombreBase);
		if (modBase == null) {
			comboPaqueteNoAlineadas.setModel(modeloPaquetes);
			return;
		}

		Set<String> paquetes = new LinkedHashSet<>();
		for (String clase : modBase.clases()) {
			String paquete = paqueteDe(clase);
			if (!paquete.isEmpty()) {
				paquetes.add(paquete);
			}
		}

		List<String> ordenados = new ArrayList<>(paquetes);
		Collections.sort(ordenados);

		for (String paquete : ordenados) {
			modeloPaquetes.addElement(paquete);
		}

		comboPaqueteNoAlineadas.setModel(modeloPaquetes);
	}

	/**
	 * Muestra referencias entre el mod seleccionado y sus dependencias.
	 */
	public void mostrarReferenciasDelModSeleccionado() {
		String nombreMod = (String) comboModsMapa.getSelectedItem();
		if (nombreMod == null || MonitorDePID.idioma.depmapTodos().equals(nombreMod)) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.depmapSeleccioneUnMod(),
					MonitorDePID.idioma.error(), JOptionPane.WARNING_MESSAGE);
			return;
		}

		ArchivoDeMod mod = buscarModPorUbicacionPublica(nombreMod);
		if (mod == null) {
			return;
		}

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.depmapVerReferencias());

		List<ArchivoDeMod> deps = new ArrayList<>(dependenciasDirectas.getOrDefault(mod, Collections.emptySet()));
		deps.sort(Comparator.comparing(ArchivoDeMod::ubicacion_para_publicar));

		for (ArchivoDeMod dep : deps) {
			DefaultMutableTreeNode nodoDep = new DefaultMutableTreeNode(
					new NodoConTexto(dep.ubicacion_para_publicar(), new Object[] { mod, dep, "DEPENDENCIA" }));

			List<ReferenciaEntreMods> refs = referenciasPorEnlace.getOrDefault(claveEnlace(mod, dep),
					Collections.emptyList());
			for (ReferenciaEntreMods ref : refs) {
				String texto = convertirFormatoClasePuntos(ref.claseOrigenInterna) + "." + ref.nombreMetodoOrigen
						+ ref.descriptorMetodoOrigen + " -> "
						+ convertirFormatoClasePuntos(normalizarNombreClaseInterno(ref.referencia.obtenerClase())) + "."
						+ ref.referencia.obtenerNombre();

				nodoDep.add(new DefaultMutableTreeNode(
						new NodoConTexto(texto, new Object[] { mod, dep, ref, "REFERENCIA_ENTRE_MODS" })));
			}

			raiz.add(nodoDep);
		}

		modeloArbol.setRoot(raiz);
		modeloArbol.reload();
		pestanas.setSelectedIndex(1);
	}

	public void comprobarDependenciasNoAlineadas() {
		String nombreBase = (String) comboModBaseNoAlineadas.getSelectedItem();
		String paquete = (String) comboPaqueteNoAlineadas.getSelectedItem();

		if (nombreBase == null || paquete == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.depmapSeleccioneParametrosNoAlineadas(),
					MonitorDePID.idioma.error(), JOptionPane.WARNING_MESSAGE);
			return;
		}

		ArchivoDeMod modBase = buscarModPorUbicacionPublica(nombreBase);
		if (modBase == null) {
			return;
		}

		List<ArchivoDeMod> dependientes = new ArrayList<>(
				dependientesDirectos.getOrDefault(modBase, Collections.emptySet()));
		dependientes.sort(Comparator.comparing(ArchivoDeMod::ubicacion_para_publicar));

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.depmapResultadoNoAlineadas());

		int totalHallazgos = 0;

		for (ArchivoDeMod modDependiente : dependientes) {
			DefaultMutableTreeNode nodoDependiente = new DefaultMutableTreeNode(
					new NodoConTexto(modDependiente.ubicacion_para_publicar(),
							new Object[] { modDependiente, modBase, "NO_ALINEADA_DEPENDIENTE" }));

			int hallazgosDelDependiente = 0;

			for (String clase : modDependiente.obtenerTodosLosNombresDeClases()) {
				String claseInterna = normalizarNombreClaseInterno(clase);
				if (claseInterna == null || !modDependiente.existeClase(claseInterna)) {
					continue;
				}

				List<ArchivoDeMod.InfoMetodo> metodos = modDependiente.obtenerMetodosConReferencias(claseInterna);
				for (ArchivoDeMod.InfoMetodo metodo : metodos) {
					for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasAMetodos()) {
						if (agregarHallazgoNoAlineado(nodoDependiente, modBase, modDependiente, paquete, claseInterna,
								metodo, ref)) {
							hallazgosDelDependiente++;
							totalHallazgos++;
						}
					}
					for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
						if (agregarHallazgoNoAlineado(nodoDependiente, modBase, modDependiente, paquete, claseInterna,
								metodo, ref)) {
							hallazgosDelDependiente++;
							totalHallazgos++;
						}
					}
				}
			}

			if (hallazgosDelDependiente > 0) {
				nodoDependiente.setUserObject(new NodoConTexto(
						modDependiente.ubicacion_para_publicar() + " (" + hallazgosDelDependiente + ")",
						new Object[] { modDependiente, modBase, "NO_ALINEADA_DEPENDIENTE" }));
				raiz.add(nodoDependiente);
			}
		}

		if (totalHallazgos == 0) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
		}

		modeloArbol.setRoot(raiz);
		modeloArbol.reload();
		pestanas.setSelectedIndex(1);

		StringBuilder sb = new StringBuilder();
		sb.append(MonitorDePID.idioma.depmapResultadoNoAlineadas()).append("\n");
		sb.append(MonitorDePID.idioma.depmapModBase()).append(": ").append(modBase.ubicacion_para_publicar())
				.append("\n");
		sb.append(MonitorDePID.idioma.paquete()).append(": ").append(paquete).append("\n");
		sb.append(MonitorDePID.idioma.depmapDependientes()).append(": ").append(dependientes.size()).append("\n");
		sb.append(MonitorDePID.idioma.referencias()).append(": ").append(totalHallazgos).append("\n");

		areaDetalles.setText(sb.toString());
	}

	public boolean agregarHallazgoNoAlineado(DefaultMutableTreeNode nodoDependiente, ArchivoDeMod modBase,
			ArchivoDeMod modDependiente, String paqueteBase, String claseOrigenInterna, ArchivoDeMod.InfoMetodo metodo,
			ArchivoDeMod.Referencia ref) {

		String claseRefInterna = normalizarNombreClaseInterno(ref.obtenerClase());
		if (claseRefInterna == null) {
			return false;
		}

		String claseRefPuntos = convertirFormatoClasePuntos(claseRefInterna);

		if (!(claseRefPuntos.equals(paqueteBase) || claseRefPuntos.startsWith(paqueteBase + "."))) {
			return false;
		}

		// Solo interesa si la clase NO existe en el mod base
		if (modBase.existeClase(claseRefInterna)) {
			return false;
		}

		HallazgoDependenciaNoAlineada hallazgo = new HallazgoDependenciaNoAlineada(modDependiente, modBase,
				claseRefInterna, paqueteBase, claseOrigenInterna, metodo.obtenerNombre(), metodo.obtenerDescriptor(),
				ref);

		String texto = convertirFormatoClasePuntos(claseOrigenInterna) + "." + metodo.obtenerNombre()
				+ metodo.obtenerDescriptor() + " -> " + claseRefPuntos;

		nodoDependiente.add(new DefaultMutableTreeNode(
				new NodoConTexto(texto, new Object[] { modDependiente, modBase, hallazgo, "NO_ALINEADA" })));

		return true;
	}

	/**
	 * Muestra detalles del nodo seleccionado.
	 */
	public void mostrarDetalles(Object real) {
		if (real == null) {
			areaDetalles.setText("");
			return;
		}

		StringBuilder sb = new StringBuilder();

		if (real instanceof ArchivoDeMod) {
			ArchivoDeMod mod = (ArchivoDeMod) real;
			sb.append(MonitorDePID.idioma.detalleMod()).append("\n");
			sb.append(MonitorDePID.idioma.ubicacion()).append(": ").append(mod.ubicacion_para_publicar()).append("\n");
			sb.append(MonitorDePID.idioma.depmapDependencias()).append(": ")
					.append(dependenciasDirectas.getOrDefault(mod, Collections.emptySet()).size()).append("\n");
			sb.append(MonitorDePID.idioma.depmapDependientes()).append(": ")
					.append(dependientesDirectos.getOrDefault(mod, Collections.emptySet()).size()).append("\n");
			areaDetalles.setText(sb.toString());
			return;
		}

		if (real instanceof Object[]) {
			Object[] datos = (Object[]) real;

			if (datos.length >= 3 && "DEPENDENCIA".equals(datos[2]) && datos[0] instanceof ArchivoDeMod
					&& datos[1] instanceof ArchivoDeMod) {
				ArchivoDeMod origen = (ArchivoDeMod) datos[0];
				ArchivoDeMod destino = (ArchivoDeMod) datos[1];

				sb.append(MonitorDePID.idioma.depmapDependenciaDetalle()).append("\n");
				sb.append(MonitorDePID.idioma.depmapOrigen()).append(": ").append(origen.ubicacion_para_publicar())
						.append("\n");
				sb.append(MonitorDePID.idioma.depmapDestino()).append(": ").append(destino.ubicacion_para_publicar())
						.append("\n");
				sb.append(MonitorDePID.idioma.referencias()).append(": ").append(
						referenciasPorEnlace.getOrDefault(claveEnlace(origen, destino), Collections.emptyList()).size())
						.append("\n");

				areaDetalles.setText(sb.toString());
				return;
			}

			if (datos.length >= 4 && "REFERENCIA_ENTRE_MODS".equals(datos[3])
					&& datos[2] instanceof ReferenciaEntreMods) {
				ReferenciaEntreMods ref = (ReferenciaEntreMods) datos[2];
				sb.append(MonitorDePID.idioma.depmapReferenciaDetalle()).append("\n");
				sb.append(MonitorDePID.idioma.depmapOrigen()).append(": ").append(ref.origen.ubicacion_para_publicar())
						.append("\n");
				sb.append(MonitorDePID.idioma.depmapDestino()).append(": ")
						.append(ref.destino.ubicacion_para_publicar()).append("\n");
				sb.append(MonitorDePID.idioma.clase()).append(": ")
						.append(convertirFormatoClasePuntos(ref.claseOrigenInterna)).append("\n");
				sb.append(MonitorDePID.idioma.metodo()).append(": ").append(ref.nombreMetodoOrigen)
						.append(ref.descriptorMetodoOrigen).append("\n");
				sb.append(MonitorDePID.idioma.depmapClaseReferenciada()).append(": ").append(
						convertirFormatoClasePuntos(normalizarNombreClaseInterno(ref.referencia.obtenerClase())))
						.append("\n");
				sb.append(MonitorDePID.idioma.nombres()).append(": ").append(ref.referencia.obtenerNombre())
						.append("\n");
				sb.append("Descriptor: ").append(ref.referencia.obtenerDescriptor()).append("\n");
				areaDetalles.setText(sb.toString());
				return;
			}

			if (datos.length >= 4 && "NO_ALINEADA".equals(datos[3])
					&& datos[2] instanceof HallazgoDependenciaNoAlineada) {
				HallazgoDependenciaNoAlineada hallazgo = (HallazgoDependenciaNoAlineada) datos[2];
				sb.append(MonitorDePID.idioma.depmapResultadoNoAlineadas()).append("\n");
				sb.append(MonitorDePID.idioma.depmapDependiente()).append(": ")
						.append(hallazgo.modDependiente.ubicacion_para_publicar()).append("\n");
				sb.append(MonitorDePID.idioma.depmapModBase()).append(": ")
						.append(hallazgo.modBase.ubicacion_para_publicar()).append("\n");
				sb.append(MonitorDePID.idioma.paquete()).append(": ").append(hallazgo.paquete).append("\n");
				sb.append(MonitorDePID.idioma.depmapClaseInexistente()).append(": ")
						.append(convertirFormatoClasePuntos(hallazgo.claseReferenciadaInterna)).append("\n");
				sb.append(MonitorDePID.idioma.depmapMetodoOrigen()).append(": ")
						.append(convertirFormatoClasePuntos(hallazgo.claseOrigenInterna)).append(".")
						.append(hallazgo.nombreMetodoOrigen).append(hallazgo.descriptorMetodoOrigen).append("\n");
				areaDetalles.setText(sb.toString());
				return;
			}
		}

		areaDetalles.setText(String.valueOf(real));
	}

	/**
	 * Muestra menú contextual.
	 */
	public void mostrarMenuContextual(MouseEvent e) {
		TreePath seleccion = arbolDependencias.getPathForLocation(e.getX(), e.getY());
		if (seleccion == null) {
			return;
		}

		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) seleccion.getLastPathComponent();
		Object user = nodo.getUserObject();
		Object real = (user instanceof NodoConTexto) ? ((NodoConTexto) user).objeto() : user;

		JPopupMenu menu = new JPopupMenu();

		JMenuItem itemDescompilar = new JMenuItem(MonitorDePID.idioma.depmapDescompilarSeleccion());
		itemDescompilar.addActionListener(ae -> descompilarDesdeObjeto(real));

		JMenuItem itemReferencias = new JMenuItem(MonitorDePID.idioma.depmapVerReferencias());
		itemReferencias.addActionListener(ae -> mostrarReferenciasDesdeObjeto(real));

		menu.add(itemReferencias);
		menu.add(itemDescompilar);
		menu.show(arbolDependencias, e.getX(), e.getY());
	}

	public void mostrarReferenciasDesdeObjeto(Object real) {
		if (!(real instanceof Object[])) {
			return;
		}

		Object[] datos = (Object[]) real;
		if (datos.length >= 3 && "DEPENDENCIA".equals(datos[2]) && datos[0] instanceof ArchivoDeMod
				&& datos[1] instanceof ArchivoDeMod) {

			ArchivoDeMod origen = (ArchivoDeMod) datos[0];
			ArchivoDeMod destino = (ArchivoDeMod) datos[1];

			DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.depmapVerReferencias());
			List<ReferenciaEntreMods> refs = referenciasPorEnlace.getOrDefault(claveEnlace(origen, destino),
					Collections.emptyList());

			for (ReferenciaEntreMods ref : refs) {
				String texto = convertirFormatoClasePuntos(ref.claseOrigenInterna) + "." + ref.nombreMetodoOrigen
						+ ref.descriptorMetodoOrigen + " -> "
						+ convertirFormatoClasePuntos(normalizarNombreClaseInterno(ref.referencia.obtenerClase())) + "."
						+ ref.referencia.obtenerNombre();

				raiz.add(new DefaultMutableTreeNode(
						new NodoConTexto(texto, new Object[] { origen, destino, ref, "REFERENCIA_ENTRE_MODS" })));
			}

			modeloArbol.setRoot(raiz);
			modeloArbol.reload();
			pestanas.setSelectedIndex(1);
		}
	}

	public void descompilarElementoSeleccionado() {
		TreePath ruta = arbolDependencias.getSelectionPath();
		if (ruta == null) {
			String nombreMod = (String) comboModsMapa.getSelectedItem();
			ArchivoDeMod mod = buscarModPorUbicacionPublica(nombreMod);
			if (mod != null) {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.depmapSeleccioneClaseParaDescompilar(),
						MonitorDePID.idioma.error(), JOptionPane.WARNING_MESSAGE);
			}
			return;
		}

		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) ruta.getLastPathComponent();
		Object user = nodo.getUserObject();
		Object real = (user instanceof NodoConTexto) ? ((NodoConTexto) user).objeto() : user;
		descompilarDesdeObjeto(real);
	}

	public void descompilarDesdeObjeto(Object real) {
		if (!(real instanceof Object[])) {
			return;
		}

		Object[] datos = (Object[]) real;

		if (datos.length >= 4 && "REFERENCIA_ENTRE_MODS".equals(datos[3]) && datos[2] instanceof ReferenciaEntreMods) {
			ReferenciaEntreMods ref = (ReferenciaEntreMods) datos[2];
			mostrarCodigoDescompilado(ref.origen, ref.claseOrigenInterna);
			return;
		}

		if (datos.length >= 4 && "NO_ALINEADA".equals(datos[3]) && datos[2] instanceof HallazgoDependenciaNoAlineada) {
			HallazgoDependenciaNoAlineada hallazgo = (HallazgoDependenciaNoAlineada) datos[2];
			mostrarCodigoDescompilado(hallazgo.modDependiente, hallazgo.claseOrigenInterna);
		}
	}

	public void mostrarCodigoDescompilado(ArchivoDeMod mod, String nombreClaseInterna) {
		abrirClaseEnCfr(nombreClaseInterna);
	}

	public void abrirClaseEnCfr(String nombreClase) {
		if (nombreClase == null || nombreClase.trim().isEmpty()) {
			return;
		}

		String claseNormalizada = nombreClase.trim();

		if (claseNormalizada.startsWith("L") && claseNormalizada.endsWith(";")) {
			claseNormalizada = claseNormalizada.substring(1, claseNormalizada.length() - 1);
		}

		if (claseNormalizada.endsWith(".class")) {
			claseNormalizada = claseNormalizada.substring(0, claseNormalizada.length() - ".class".length());
		}

		claseNormalizada = claseNormalizada.replace('/', '.');

		String url = "cfr://" + claseNormalizada;

		CrashDetectorLogger.log(url + " (cfr url)");

		CfrBase gui = TipoGUI.CFR.obtenerGUIPredeterminado(CfrSakuraRiddle.ID, CfrSakuraRiddle::new);
		gui.procesarHipervinculo(url);
	}

	public void initOverlayCarga() {
		overlayCarga = new ElementoOverlayCarga();
		overlayCarga.setVisible(false);
		setGlassPane(overlayCarga);
	}

	public void setCargando(boolean valor) {
		cargando = valor;

		if (overlayCarga != null) {
			overlayCarga.setVisible(valor);
			overlayCarga.revalidate();
			overlayCarga.repaint();
		}
	}

	public List<ArchivoDeMod> obtenerTodosLosModsYSubmodsRecursivos() {
		List<ArchivoDeMod> resultado = new ArrayList<>();
		for (ArchivoDeMod mod : Buscardor.mods) {
			agregarModRecursivo(mod, resultado);
		}
		return resultado;
	}

	public void agregarModRecursivo(ArchivoDeMod mod, List<ArchivoDeMod> salida) {
		if (mod == null || salida.contains(mod)) {
			return;
		}
		salida.add(mod);
		for (ArchivoDeMod submod : mod.mods_en_mods()) {
			agregarModRecursivo(submod, salida);
		}
	}

	public ArchivoDeMod buscarModPorUbicacionPublica(String ubicacion) {
		if (ubicacion == null) {
			return null;
		}
		for (ArchivoDeMod mod : obtenerTodosLosModsYSubmodsRecursivos()) {
			if (ubicacion.equals(mod.ubicacion_para_publicar())) {
				return mod;
			}
		}
		return null;
	}

	public String claveEnlace(ArchivoDeMod origen, ArchivoDeMod destino) {
		return origen.ubicacion_para_publicar() + " -> " + destino.ubicacion_para_publicar();
	}

	public static String normalizarNombreClaseInterno(String nombre) {
		if (nombre == null) {
			return null;
		}
		String s = nombre.trim();
		if (s.isEmpty()) {
			return s;
		}
		if (s.length() >= 2 && s.charAt(0) == 'L' && s.endsWith(";")) {
			s = s.substring(1, s.length() - 1);
		}
		if (s.toLowerCase().endsWith(".class")) {
			s = s.substring(0, s.length() - ".class".length());
		}
		if (s.indexOf('.') >= 0) {
			s = s.replace('.', '/');
		}
		return s;
	}

	public String convertirFormatoClasePuntos(String nombreClase) {
		if (nombreClase == null) {
			return "";
		}
		if (nombreClase.contains(".")) {
			return nombreClase;
		}
		if (nombreClase.contains("/")) {
			return nombreClase.replace('/', '.');
		}
		return nombreClase;
	}

	public String paqueteDe(String clasePuntos) {
		if (clasePuntos == null) {
			return "";
		}
		int idx = clasePuntos.lastIndexOf('.');
		return idx > 0 ? clasePuntos.substring(0, idx) : "";
	}

	public ImageIcon crearIcono(String ruta, String textoAlternativo) {
		ImageIcon icono = new ImageIcon(ruta);
		if (icono.getIconWidth() > 0) {
			return icono;
		}

		java.awt.image.BufferedImage imagen = new java.awt.image.BufferedImage(18, 18,
				java.awt.image.BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = imagen.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(200, 200, 200, 180));
		g2d.fillOval(0, 0, 17, 17);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 10));
		FontMetrics fm = g2d.getFontMetrics();
		int x = (18 - fm.stringWidth(textoAlternativo)) / 2;
		int y = ((18 - fm.getHeight()) / 2) + fm.getAscent();
		g2d.drawString(textoAlternativo, x, y);
		g2d.dispose();
		return new ImageIcon(imagen);
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.DEPMAP;
	}

	public static class ReferenciaEntreMods {
		public final ArchivoDeMod origen;
		public final ArchivoDeMod destino;
		public final String claseOrigenInterna;
		public final String nombreMetodoOrigen;
		public final String descriptorMetodoOrigen;
		public final ArchivoDeMod.Referencia referencia;

		public ReferenciaEntreMods(ArchivoDeMod origen, ArchivoDeMod destino, String claseOrigenInterna,
				String nombreMetodoOrigen, String descriptorMetodoOrigen, ArchivoDeMod.Referencia referencia) {
			this.origen = origen;
			this.destino = destino;
			this.claseOrigenInterna = claseOrigenInterna;
			this.nombreMetodoOrigen = nombreMetodoOrigen;
			this.descriptorMetodoOrigen = descriptorMetodoOrigen;
			this.referencia = referencia;
		}
	}

	public static class HallazgoDependenciaNoAlineada {
		public final ArchivoDeMod modDependiente;
		public final ArchivoDeMod modBase;
		public final String claseReferenciadaInterna;
		public final String paquete;
		public final String claseOrigenInterna;
		public final String nombreMetodoOrigen;
		public final String descriptorMetodoOrigen;
		public final ArchivoDeMod.Referencia referencia;

		public HallazgoDependenciaNoAlineada(ArchivoDeMod modDependiente, ArchivoDeMod modBase,
				String claseReferenciadaInterna, String paquete, String claseOrigenInterna, String nombreMetodoOrigen,
				String descriptorMetodoOrigen, ArchivoDeMod.Referencia referencia) {
			this.modDependiente = modDependiente;
			this.modBase = modBase;
			this.claseReferenciadaInterna = claseReferenciadaInterna;
			this.paquete = paquete;
			this.claseOrigenInterna = claseOrigenInterna;
			this.nombreMetodoOrigen = nombreMetodoOrigen;
			this.descriptorMetodoOrigen = descriptorMetodoOrigen;
			this.referencia = referencia;
		}
	}

	public static class NodoConTexto {
		public final String texto;
		public final Object objeto;

		public NodoConTexto(String texto, Object objeto) {
			this.texto = texto;
			this.objeto = objeto;
		}

		public String texto() {
			return texto;
		}

		public Object objeto() {
			return objeto;
		}

		@Override
		public String toString() {
			return texto;
		}
	}

	public class RenderizadorCeldasDependencias extends DefaultTreeCellRenderer {
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			Object user = (value instanceof DefaultMutableTreeNode) ? ((DefaultMutableTreeNode) value).getUserObject()
					: null;
			Object real = (user instanceof NodoConTexto) ? ((NodoConTexto) user).objeto() : user;

			if (real instanceof ArchivoDeMod) {
				setIcon(iconoMod);
			} else if (real instanceof Object[]) {
				Object[] datos = (Object[]) real;
				if (datos.length >= 3 && datos[2] instanceof String) {
					String tipo = (String) datos[2];
					if ("DEPENDENCIA".equals(tipo)) {
						setIcon(iconoDependencia);
					} else if ("REFERENCIA_ENTRE_MODS".equals(tipo)) {
						setIcon(iconoReferencia);
					} else if ("NO_ALINEADA".equals(tipo)) {
						setIcon(iconoClase);
					}
				}
			}

			return this;
		}
	}

	public class GrafoDependenciasPanel extends JPanel {

		private double zoom = 0.28;
		private double desplazamientoX = 0.0;
		private double desplazamientoY = 0.0;

		private int ultimoMouseX = 0;
		private int ultimoMouseY = 0;
		private boolean arrastrando = false;

		private final Map<ArchivoDeMod, java.awt.geom.Ellipse2D.Double> hitboxes = new LinkedHashMap<>();
		private final Map<ArchivoDeMod, java.awt.Point> posicionesActuales = new LinkedHashMap<>();

		private ArchivoDeMod modSeleccionado = null;
		private boolean vistaInicialAplicada = false;

		public GrafoDependenciasPanel() {
			setPreferredSize(new Dimension(2200, 1800));

			addMouseWheelListener(e -> {
				double zoomAnterior = zoom;

				if (e.getPreciseWheelRotation() < 0) {
					zoom *= 1.10;
				} else {
					zoom /= 1.10;
				}

				zoom = Math.max(0.12, Math.min(3.5, zoom));

				double factor = zoom / zoomAnterior;
				desplazamientoX = e.getX() - factor * (e.getX() - desplazamientoX);
				desplazamientoY = e.getY() - factor * (e.getY() - desplazamientoY);

				vistaInicialAplicada = true;
				repaint();
			});

			addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					ultimoMouseX = e.getX();
					ultimoMouseY = e.getY();

					ArchivoDeMod hit = encontrarNodoEn(e.getX(), e.getY());
					if (hit != null) {
						modSeleccionado = hit;
						mostrarDetalles(hit);
						repaint();

						if (e.getClickCount() >= 2) {
							comboModsMapa.setSelectedItem(hit.ubicacion_para_publicar());
							mostrarReferenciasDelModSeleccionado();
						}
						return;
					}

					arrastrando = true;
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					arrastrando = false;
				}
			});

			addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					if (!arrastrando) {
						return;
					}

					int dx = e.getX() - ultimoMouseX;
					int dy = e.getY() - ultimoMouseY;

					desplazamientoX += dx;
					desplazamientoY += dy;

					ultimoMouseX = e.getX();
					ultimoMouseY = e.getY();

					vistaInicialAplicada = true;
					repaint();
				}
			});
		}

		public void reiniciarVista() {
			zoom = 0.28;
			desplazamientoX = 0.0;
			desplazamientoY = 0.0;
			vistaInicialAplicada = false;
			repaint();
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(2200, 1800);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			Color colorFondo = getBackground();
			Color colorNodoReal = new Color(157, 121, 173);
			Color colorEnlaceReal = new Color(124, 99, 156);
			Color colorTextoReal = getForeground() != null ? getForeground() : new Color(221, 213, 238);
			Color colorSeleccionNodo = new Color(214, 180, 255);

			if (MapaDeDependenciasGUI.this instanceof MapaDeDependenciasGUINimu) {
				MapaDeDependenciasGUINimu tema = (MapaDeDependenciasGUINimu) MapaDeDependenciasGUI.this;
				colorNodoReal = tema.colorNodo.obtener();
				colorEnlaceReal = tema.colorEnlace.obtener();
				colorTextoReal = tema.colorTexto.obtener();
				colorSeleccionNodo = tema.colorSeleccion.obtener();
			}

			g2.setColor(colorFondo);
			g2.fillRect(0, 0, getWidth(), getHeight());

			List<ArchivoDeMod> mods = obtenerTodosLosModsYSubmodsRecursivos();
			if (mods.isEmpty()) {
				g2.dispose();
				return;
			}

			String filtro = (String) comboModsMapa.getSelectedItem();
			List<ArchivoDeMod> visibles = new ArrayList<>();

			for (ArchivoDeMod mod : mods) {
				if (filtro == null || MonitorDePID.idioma.depmapTodos().equals(filtro)
						|| mod.ubicacion_para_publicar().equals(filtro)
						|| dependenciasDirectas.getOrDefault(mod, Collections.emptySet()).stream()
								.anyMatch(m -> m.ubicacion_para_publicar().equals(filtro))
						|| dependientesDirectos.getOrDefault(mod, Collections.emptySet()).stream()
								.anyMatch(m -> m.ubicacion_para_publicar().equals(filtro))) {
					visibles.add(mod);
				}
			}

			if (visibles.isEmpty()) {
				g2.dispose();
				return;
			}

			visibles.sort((a, b) -> {
				int dependientesA = dependientesDirectos.getOrDefault(a, Collections.emptySet()).size();
				int dependientesB = dependientesDirectos.getOrDefault(b, Collections.emptySet()).size();
				if (dependientesA != dependientesB) {
					return Integer.compare(dependientesB, dependientesA);
				}

				int dependenciasA = dependenciasDirectas.getOrDefault(a, Collections.emptySet()).size();
				int dependenciasB = dependenciasDirectas.getOrDefault(b, Collections.emptySet()).size();
				if (dependenciasA != dependenciasB) {
					return Integer.compare(dependenciasB, dependenciasA);
				}

				return a.ubicacion_para_publicar().compareToIgnoreCase(b.ubicacion_para_publicar());
			});

			hitboxes.clear();
			posicionesActuales.clear();

			int anchoModelo = 2200;
			int altoModelo = 1800;
			int cx = anchoModelo / 2;
			int cy = altoModelo / 2;
			int radioBase = Math.max(320, Math.min(anchoModelo, altoModelo) / 3);

			for (int i = 0; i < visibles.size(); i++) {
				double angulo = (Math.PI * 2.0 * i) / Math.max(1, visibles.size());
				int x = cx + (int) (Math.cos(angulo) * radioBase);
				int y = cy + (int) (Math.sin(angulo) * radioBase);
				posicionesActuales.put(visibles.get(i), new java.awt.Point(x, y));
			}

			if (!vistaInicialAplicada) {
				ajustarVistaInicial(visibles);
			}

			g2.translate(desplazamientoX, desplazamientoY);
			g2.scale(zoom, zoom);

			Set<String> enlacesYaDibujados = new LinkedHashSet<>();
			g2.setStroke(new BasicStroke(2f));
			g2.setColor(colorEnlaceReal);

			for (ArchivoDeMod modBase : visibles) {
				Set<ArchivoDeMod> dependientes = dependientesDirectos.getOrDefault(modBase, Collections.emptySet());

				for (ArchivoDeMod modDependiente : dependientes) {
					if (!posicionesActuales.containsKey(modDependiente)) {
						continue;
					}

					String claveDirecta = modBase.ubicacion_para_publicar() + "->"
							+ modDependiente.ubicacion_para_publicar();
					String claveInversa = modDependiente.ubicacion_para_publicar() + "->"
							+ modBase.ubicacion_para_publicar();

					boolean esMutua = dependientesDirectos.getOrDefault(modDependiente, Collections.emptySet())
							.contains(modBase);

					java.awt.Point pBase = posicionesActuales.get(modBase);
					java.awt.Point pDependiente = posicionesActuales.get(modDependiente);

					double radioBaseNodo = obtenerRadioNodo(modBase);
					double radioDependienteNodo = obtenerRadioNodo(modDependiente);

					if (esMutua) {
						String claveMutua = modBase.ubicacion_para_publicar()
								.compareTo(modDependiente.ubicacion_para_publicar()) < 0 ? claveDirecta + "|M"
										: claveInversa + "|M";

						if (enlacesYaDibujados.contains(claveMutua)) {
							continue;
						}
						enlacesYaDibujados.add(claveMutua);

						dibujarFlechaCurvaDoble(g2, pBase.x, pBase.y, radioBaseNodo, pDependiente.x, pDependiente.y,
								radioDependienteNodo, colorEnlaceReal);
					} else {
						if (enlacesYaDibujados.contains(claveDirecta)) {
							continue;
						}
						enlacesYaDibujados.add(claveDirecta);

						dibujarFlechaSimple(g2, pBase.x, pBase.y, radioBaseNodo, pDependiente.x, pDependiente.y,
								radioDependienteNodo, colorEnlaceReal);
					}
				}
			}

			for (ArchivoDeMod mod : visibles) {
				java.awt.Point p = posicionesActuales.get(mod);
				int tam = obtenerTamNodo(mod);

				java.awt.geom.Ellipse2D.Double circulo = new java.awt.geom.Ellipse2D.Double(p.x - tam / 2.0,
						p.y - tam / 2.0, tam, tam);

				hitboxes.put(mod, circulo);

				g2.setColor(mod.equals(modSeleccionado) ? colorSeleccionNodo : colorNodoReal);
				g2.fill(circulo);

				g2.setColor(colorTextoReal);
				g2.setStroke(new BasicStroke(mod.equals(modSeleccionado) ? 3f : 2f));
				g2.draw(circulo);

				int dependientes = dependientesDirectos.getOrDefault(mod, Collections.emptySet()).size();
				String texto = resumirNombreMod(mod.ubicacion_para_publicar()) + " (" + dependientes + ")";
				FontMetrics fm = g2.getFontMetrics();
				int tx = p.x - (fm.stringWidth(texto) / 2);
				int ty = p.y + fm.getAscent() / 2;
				g2.drawString(texto, tx, ty);
			}

			g2.dispose();
		}

		private int obtenerTamNodo(ArchivoDeMod mod) {
			int deps = dependenciasDirectas.getOrDefault(mod, Collections.emptySet()).size();
			int dependientes = dependientesDirectos.getOrDefault(mod, Collections.emptySet()).size();

			int tam = 48 + (deps * 5) + (dependientes * 4);
			return Math.min(120, Math.max(50, tam));
		}

		private double obtenerRadioNodo(ArchivoDeMod mod) {
			return obtenerTamNodo(mod) / 2.0;
		}

		private void ajustarVistaInicial(List<ArchivoDeMod> visibles) {
			if (visibles.isEmpty() || posicionesActuales.isEmpty()) {
				vistaInicialAplicada = true;
				return;
			}

			int anchoViewport = getWidth();
			int altoViewport = getHeight();

			java.awt.Container p = getParent();
			if (p instanceof javax.swing.JViewport) {
				java.awt.Dimension extent = ((javax.swing.JViewport) p).getExtentSize();
				if (extent != null && extent.width > 0 && extent.height > 0) {
					anchoViewport = extent.width;
					altoViewport = extent.height;
				}
			}

			if (anchoViewport <= 0 || altoViewport <= 0) {
				vistaInicialAplicada = true;
				return;
			}

			int minX = Integer.MAX_VALUE;
			int minY = Integer.MAX_VALUE;
			int maxX = Integer.MIN_VALUE;
			int maxY = Integer.MIN_VALUE;

			for (ArchivoDeMod mod : visibles) {
				java.awt.Point pto = posicionesActuales.get(mod);
				if (pto == null) {
					continue;
				}

				int tam = obtenerTamNodo(mod);

				minX = Math.min(minX, pto.x - tam / 2 - 120);
				minY = Math.min(minY, pto.y - tam / 2 - 120);
				maxX = Math.max(maxX, pto.x + tam / 2 + 120);
				maxY = Math.max(maxY, pto.y + tam / 2 + 120);
			}

			if (minX >= maxX || minY >= maxY) {
				vistaInicialAplicada = true;
				return;
			}

			double anchoContenido = maxX - minX;
			double altoContenido = maxY - minY;

			double zoomX = anchoViewport / anchoContenido;
			double zoomY = altoViewport / altoContenido;

			zoom = Math.min(zoomX, zoomY) * 0.90;
			zoom = Math.max(0.12, Math.min(zoom, 0.60));

			double centroContenidoX = (minX + maxX) / 2.0;
			double centroContenidoY = (minY + maxY) / 2.0;

			desplazamientoX = (anchoViewport / 2.0) - (centroContenidoX * zoom);
			desplazamientoY = (altoViewport / 2.0) - (centroContenidoY * zoom);

			vistaInicialAplicada = true;
		}

		private void dibujarFlechaSimple(Graphics2D g2, double cx1, double cy1, double radio1, double cx2, double cy2,
				double radio2, Color color) {

			double dx = cx2 - cx1;
			double dy = cy2 - cy1;
			double len = Math.hypot(dx, dy);
			if (len < 0.0001) {
				return;
			}

			double ux = dx / len;
			double uy = dy / len;

			double x1 = cx1 + ux * radio1;
			double y1 = cy1 + uy * radio1;
			double x2 = cx2 - ux * radio2;
			double y2 = cy2 - uy * radio2;

			g2.setColor(color);
			g2.draw(new java.awt.geom.Line2D.Double(x1, y1, x2, y2));
			dibujarPuntaFlecha(g2, x1, y1, x2, y2, color);
		}

		private void dibujarFlechaCurvaDoble(Graphics2D g2, double cx1, double cy1, double radio1, double cx2,
				double cy2, double radio2, Color color) {

			double dx = cx2 - cx1;
			double dy = cy2 - cy1;
			double len = Math.hypot(dx, dy);
			if (len < 0.0001) {
				return;
			}

			double ux = dx / len;
			double uy = dy / len;
			double nx = -uy;
			double ny = ux;
			double offset = 18.0;

			double sx1 = cx1 + ux * radio1;
			double sy1 = cy1 + uy * radio1;
			double ex1 = cx2 - ux * radio2;
			double ey1 = cy2 - uy * radio2;

			double sx2 = cx2 - ux * radio2;
			double sy2 = cy2 - uy * radio2;
			double ex2 = cx1 + ux * radio1;
			double ey2 = cy1 + uy * radio1;

			double ax1 = sx1 + nx * offset;
			double ay1 = sy1 + ny * offset;
			double ax2 = ex1 + nx * offset;
			double ay2 = ey1 + ny * offset;

			double bx1 = sx2 - nx * offset;
			double by1 = sy2 - ny * offset;
			double bx2 = ex2 - nx * offset;
			double by2 = ey2 - ny * offset;

			g2.setColor(color);
			g2.draw(new java.awt.geom.Line2D.Double(ax1, ay1, ax2, ay2));
			g2.draw(new java.awt.geom.Line2D.Double(bx1, by1, bx2, by2));

			dibujarPuntaFlecha(g2, ax1, ay1, ax2, ay2, color);
			dibujarPuntaFlecha(g2, bx1, by1, bx2, by2, color);
		}

		private void dibujarPuntaFlecha(Graphics2D g2, double x1, double y1, double x2, double y2, Color color) {
			double phi = Math.toRadians(34);
			int barb = 36;

			double dy = y2 - y1;
			double dx = x2 - x1;
			double theta = Math.atan2(dy, dx);

			g2.setColor(color);
			g2.setStroke(new BasicStroke(4f));

			for (int j = 0; j < 2; j++) {
				double rho = theta + (j == 0 ? phi : -phi);
				double x = x2 - barb * Math.cos(rho);
				double y = y2 - barb * Math.sin(rho);
				g2.draw(new java.awt.geom.Line2D.Double(x2, y2, x, y));
			}
		}

		private ArchivoDeMod encontrarNodoEn(int xPantalla, int yPantalla) {
			double xModelo = (xPantalla - desplazamientoX) / zoom;
			double yModelo = (yPantalla - desplazamientoY) / zoom;

			for (Map.Entry<ArchivoDeMod, java.awt.geom.Ellipse2D.Double> e : hitboxes.entrySet()) {
				if (e.getValue().contains(xModelo, yModelo)) {
					return e.getKey();
				}
			}
			return null;
		}

		public String resumirNombreMod(String nombre) {
			if (nombre == null) {
				return "";
			}
			if (nombre.length() <= 14) {
				return nombre;
			}
			return nombre.substring(0, 11) + "...";
		}
	}

}