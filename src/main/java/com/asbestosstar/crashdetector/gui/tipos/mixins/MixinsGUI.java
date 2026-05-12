package com.asbestosstar.crashdetector.gui.tipos.mixins;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingWorker;
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
import com.asbestosstar.crashdetector.gui.elementos.LectadorDeCodigo;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrBase;

/**
 * GUI abstracta para explorar mixins de SpongePowered.
 * 
 * Sigue la misma idea estructural que ArbolDeModsGUI: - esta clase contiene la
 * lógica - una implementación concreta aplica colores, imágenes y apariencia
 */
public abstract class MixinsGUI extends JFrame implements BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<MixinsGUI>> GUIS = new HashMap<>();

	public JTree arbolMixins;
	public DefaultTreeModel modeloArbol;
	public LectadorDeCodigo areaContenido;

	public JComboBox<String> comboMods;
	public JButton botonRecargar;
	public JButton botonDescompilar;

	public JLabel imagenChiarru;
	public JLabel imagenGoMix;

	public ImageIcon iconoMod;
	public ImageIcon iconoMixin;
	public ImageIcon iconoTarget;
	public ImageIcon iconoMetodo;
	public ImageIcon iconoCampo;
	public ImageIcon iconoConflicto;

	public JPanel overlayCarga;
	public JLabel gifCarga;
	public volatile boolean cargando = false;

	public SwingWorker<DefaultMutableTreeNode, Void> workerCarga;

	public MixinsGUI() {
	}

	@Override
	public void init() {
		setTitle(MonitorDePID.idioma.tituloMixins());
		setSize(1400, 860);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout());

		iconoMod = crearIcono(Statics.carpeta.resolve("imagenes/mod.png").toString(), "M");
		iconoMixin = crearIcono(Statics.carpeta.resolve("imagenes/mixin.png").toString(), "MX");
		iconoTarget = crearIcono(Statics.carpeta.resolve("imagenes/clase.png").toString(), "T");
		iconoMetodo = crearIcono(Statics.carpeta.resolve("imagenes/metodo.png").toString(), "m");
		iconoCampo = crearIcono(Statics.carpeta.resolve("imagenes/campo.png").toString(), "f");
		iconoConflicto = crearIcono(Statics.carpeta.resolve("imagenes/referencia_metodo.png").toString(), "!");

		JPanel barraSuperior = new JPanel(new BorderLayout());
		barraSuperior.setOpaque(false);
		barraSuperior.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelControles.setOpaque(false);

		JLabel etiquetaMod = new JLabel(MonitorDePID.idioma.mixinsModConMixin() + ":");
		comboMods = new JComboBox<>();
		comboMods.setToolTipText(MonitorDePID.idioma.mixinsTooltipCombo());

		botonRecargar = new JButton(MonitorDePID.idioma.mixinsRecargar());
		botonDescompilar = new JButton(MonitorDePID.idioma.mixinsDescompilarSeleccion());

		panelControles.add(etiquetaMod);
		panelControles.add(comboMods);
		panelControles.add(botonRecargar);
		panelControles.add(botonDescompilar);

		arbolMixins = new JTree();
		arbolMixins.setRootVisible(false);
		arbolMixins.setShowsRootHandles(true);
		arbolMixins.setCellRenderer(new RenderizadorCeldasMixins());

		areaContenido = new LectadorDeCodigo(new Color(252, 244, 250), new Color(70, 42, 66), new Color(185, 74, 128),
				new Color(86, 137, 86), new Color(145, 118, 150), new Color(190, 125, 55), new Color(90, 118, 170),
				new Color(155, 88, 175));
		areaContenido.setFont(new Font("Monospaced", Font.PLAIN, 12));

		imagenChiarru = new JLabel();
		imagenGoMix = new JLabel();

		// 🔹 Panel lateral izquierdo completo para Chiarru.
		// Se rellena entero y usa el color del tema al aplicar la apariencia.
		JPanel panelLateralIzquierdo = new JPanel(new BorderLayout());
		panelLateralIzquierdo.setOpaque(true);
		panelLateralIzquierdo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

		// Centrar/arrancar arriba la imagen dentro de un panel que ocupe toda la altura
		JPanel panelImagenChiarru = new JPanel(new BorderLayout());
		panelImagenChiarru.setOpaque(true);
		panelImagenChiarru.add(imagenChiarru, BorderLayout.NORTH);

		panelLateralIzquierdo.add(panelImagenChiarru, BorderLayout.CENTER);

		JScrollPane scrollArbol = new JScrollPane(arbolMixins);
		JScrollPane scrollDetalles = new JScrollPane(areaContenido);

		// 🔹 Split movible entre imagen y árbol
		JSplitPane splitIzquierdo = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLateralIzquierdo, scrollArbol);
		splitIzquierdo.setDividerLocation(250);
		splitIzquierdo.setResizeWeight(0.0);
		splitIzquierdo.setContinuousLayout(true);
		splitIzquierdo.setOneTouchExpandable(true);
		splitIzquierdo.setBorder(null);

		// 🔹 Split principal entre lado izquierdo y panel de detalles
		JSplitPane splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitIzquierdo, scrollDetalles);
		splitPrincipal.setDividerLocation(650);
		splitPrincipal.setResizeWeight(0.45);
		splitPrincipal.setContinuousLayout(true);
		splitPrincipal.setOneTouchExpandable(true);
		splitPrincipal.setBorder(null);

		// 🔹 Panel inferior: ayuda a la izquierda, GoMix a la derecha
		JPanel panelInferior = new JPanel(new BorderLayout());
		panelInferior.setOpaque(false);
		panelInferior.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JTextArea areaAyuda = new JTextArea();
		areaAyuda.setEditable(false);
		areaAyuda.setLineWrap(true);
		areaAyuda.setWrapStyleWord(true);
		areaAyuda.setFont(new Font("SansSerif", Font.PLAIN, 12));
		areaAyuda.setOpaque(false);
		areaAyuda.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 12));
		areaAyuda.setForeground(Color.WHITE);
		areaAyuda.setText(MonitorDePID.idioma.mixinsAyudaUso1() + "\n\n" + MonitorDePID.idioma.mixinsAyudaUso2() + "\n"
				+ MonitorDePID.idioma.mixinsAyudaUso3() + "\n" + MonitorDePID.idioma.mixinsAyudaUso4() + "\n"
				+ MonitorDePID.idioma.mixinsAyudaUso5());

		JPanel panelAyuda = new JPanel(new BorderLayout());
		panelAyuda.setOpaque(false);
		panelAyuda.add(areaAyuda, BorderLayout.CENTER);

		JPanel panelGoMix = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		panelGoMix.setOpaque(false);
		panelGoMix.add(imagenGoMix);

		panelInferior.add(panelAyuda, BorderLayout.CENTER);
		panelInferior.add(panelGoMix, BorderLayout.EAST);

		barraSuperior.add(panelControles, BorderLayout.NORTH);
		barraSuperior.add(splitPrincipal, BorderLayout.CENTER);

		add(barraSuperior, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);

		arbolMixins.addTreeSelectionListener(e -> {
			TreePath ruta = arbolMixins.getSelectionPath();
			if (ruta == null) {
				return;
			}
			DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) ruta.getLastPathComponent();
			Object user = nodo.getUserObject();
			Object real = (user instanceof NodoConTexto) ? ((NodoConTexto) user).objeto() : user;
			mostrarDetalles(real);
		});

		arbolMixins.addMouseListener(new MouseAdapter() {
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

		comboMods.addActionListener(e -> recargarArbolMixins());
		botonRecargar.addActionListener(e -> recargarArbolMixins());
		botonDescompilar.addActionListener(e -> descompilarElementoSeleccionado());

		initOverlayCarga();
		setCargando(false);

		modeloArbol = new DefaultTreeModel(new DefaultMutableTreeNode(MonitorDePID.idioma.cargando()));
		arbolMixins.setModel(modeloArbol);

		// 🔹 Igualar visualmente la altura útil inicial del árbol al panel lateral
		scrollArbol.getViewport().setPreferredSize(new java.awt.Dimension(420, 520));

		recargarApariencia();

		setVisible(true);
		iniciarCargaPesada();
	}

	/**
	 * Carga los mods y después reconstruye la GUI de mixins.
	 * 
	 * Esto evita que el árbol salga vacío si la GUI abre antes de que Buscardor
	 * termine de leer los mods.
	 */
	public void iniciarCargaPesada() {
		setCargando(true);

		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				try {
					Buscardor.cargarYPrecargarClasesEnCache();
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
				}
				return null;
			}

			@Override
			protected void done() {
				try {
					recargarListaMods();
					recargarArbolMixins();
				} finally {
					setCargando(false);
				}
			}
		}.execute();
	}

	/**
	 * Recarga el combo con los mods que contengan al menos una clase mixin.
	 */
	public void recargarListaMods() {
		comboMods.removeAllItems();
		comboMods.addItem(MonitorDePID.idioma.mixinsTodosLosMods());

		for (ArchivoDeMod mod : obtenerModsConMixins()) {
			comboMods.addItem(mod.ubicacion_para_publicar());
		}
	}

	/**
	 * Devuelve los mods y submods que contienen al menos una clase mixin.
	 */
	public List<ArchivoDeMod> obtenerModsConMixins() {
		List<ArchivoDeMod> modsConMixins = new ArrayList<>();

		for (ArchivoDeMod mod : Buscardor.mods) {
			recolectarModsConMixinsRecursivo(mod, modsConMixins);
		}

		return modsConMixins;
	}

	/**
	 * Recolecta recursivamente mods con mixins.
	 */
	private void recolectarModsConMixinsRecursivo(ArchivoDeMod mod, List<ArchivoDeMod> salida) {
		if (mod == null) {
			return;
		}

		try {
			if (modTieneMixins(mod)) {
				salida.add(mod);
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		for (ArchivoDeMod submod : mod.mods_en_mods()) {
			recolectarModsConMixinsRecursivo(submod, salida);
		}
	}

	public boolean modTieneMixins(ArchivoDeMod mod) {
		for (String clase : mod.clases()) {
			String claseInterna = normalizarNombreClaseInterno(clase);
			try {
				if (claseInterna != null && mod.esClaseMixin(claseInterna)) {
					CrashDetectorLogger
							.log("[mixins-gui] mixin encontrado en " + mod.ubicacion() + ": " + claseInterna);
					return true;
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}
		return false;
	}

	public void recargarArbolMixins() {
		if (workerCarga != null && !workerCarga.isDone()) {
			workerCarga.cancel(true);
		}

		setCargando(true);

		SwingWorker<DefaultMutableTreeNode, Void> nuevoWorker = new SwingWorker<DefaultMutableTreeNode, Void>() {
			@Override
			protected DefaultMutableTreeNode doInBackground() {
				try {
					return construirArbolMixins();
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.mixinsRaiz());
					raiz.add(new DefaultMutableTreeNode("Error: " + t.getMessage()));
					return raiz;
				}
			}

			@Override
			protected void done() {
				try {
					if (isCancelled()) {
						return;
					}

					DefaultMutableTreeNode raiz = get();
					if (raiz == null) {
						return;
					}

					modeloArbol.setRoot(raiz);
					modeloArbol.reload();
				} catch (java.util.concurrent.CancellationException ex) {
					// Cancelación esperada al lanzar otra recarga
					return;
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
					return;
				} catch (java.util.concurrent.ExecutionException ex) {
					CrashDetectorLogger.logException(ex);
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
	 * Construye el árbol con todos los mods y mixins visibles en la GUI.
	 */
	public DefaultMutableTreeNode construirArbolMixins() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.mixinsRaiz());

		String modSeleccionado = (String) comboMods.getSelectedItem();
		boolean todos = modSeleccionado == null || modSeleccionado.equals(MonitorDePID.idioma.mixinsTodosLosMods());

		for (ArchivoDeMod mod : obtenerModsConMixins()) {
			if (!todos && !mod.ubicacion_para_publicar().equals(modSeleccionado)) {
				continue;
			}

			DefaultMutableTreeNode nodoMod = new DefaultMutableTreeNode(
					new NodoConTexto(mod.ubicacion_para_publicar(), mod));

			for (String clase : mod.clases()) {
				String claseInterna = normalizarNombreClaseInterno(clase);
				if (claseInterna == null || !mod.esClaseMixin(claseInterna)) {
					continue;
				}

				ArchivoDeMod.MixinInfo info = mod.obtenerInfoMixin(claseInterna);
				if (info == null) {
					continue;
				}

				DefaultMutableTreeNode nodoMixin = new DefaultMutableTreeNode(
						new NodoConTexto(clase, new Object[] { mod, clase, "MIXIN" }));

				DefaultMutableTreeNode nodoTargets = new DefaultMutableTreeNode(new NodoConTexto(
						MonitorDePID.idioma.mixinsTargets() + " (" + info.obtenerTargets().size() + ")",
						new Object[] { mod, clase, "TARGETS_CONTENEDOR" }));

				for (String target : info.obtenerTargets()) {
					nodoTargets.add(new DefaultMutableTreeNode(
							new NodoConTexto(target, new Object[] { mod, clase, "TARGET", target })));
				}

				DefaultMutableTreeNode nodoMetodos = new DefaultMutableTreeNode(new NodoConTexto(
						MonitorDePID.idioma.mixinsMetodos() + " (" + info.obtenerMetodosMixin().size() + ")",
						new Object[] { mod, clase, "METODOS_CONTENEDOR" }));

				for (ArchivoDeMod.MixinMetodoInfo metodo : info.obtenerMetodosMixin()) {
					DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(
							new NodoConTexto(metodo.obtenerNombre() + metodo.obtenerDescriptor(),
									new Object[] { mod, clase, "METODO", metodo }));

					for (String targetMetodo : metodo.obtenerTargets()) {
						nodoMetodo.add(new DefaultMutableTreeNode(new NodoConTexto("→ " + targetMetodo,
								new Object[] { mod, clase, "METODO_TARGET", metodo, targetMetodo })));
					}

					nodoMetodos.add(nodoMetodo);
				}

				DefaultMutableTreeNode nodoCampos = new DefaultMutableTreeNode(new NodoConTexto(
						MonitorDePID.idioma.mixinsCampos() + " (" + info.obtenerCamposMixin().size() + ")",
						new Object[] { mod, clase, "CAMPOS_CONTENEDOR" }));

				for (ArchivoDeMod.MixinCampoInfo campo : info.obtenerCamposMixin()) {
					nodoCampos.add(new DefaultMutableTreeNode(
							new NodoConTexto(campo.obtenerNombre() + " " + campo.obtenerDescriptor(),
									new Object[] { mod, clase, "CAMPO", campo })));
				}

				nodoMixin.add(nodoTargets);
				nodoMixin.add(nodoMetodos);
				nodoMixin.add(nodoCampos);

				nodoMod.add(nodoMixin);
			}

			if (nodoMod.getChildCount() > 0) {
				raiz.add(nodoMod);
			}
		}

		return raiz;
	}

	/**
	 * Muestra los detalles textuales del nodo seleccionado.
	 */
	public void mostrarDetalles(Object objetoReal) {
		if (objetoReal == null) {
			areaContenido.establecerCodigo("");
			return;
		}

		StringBuilder sb = new StringBuilder();

		if (objetoReal instanceof ArchivoDeMod) {
			ArchivoDeMod mod = (ArchivoDeMod) objetoReal;
			sb.append(MonitorDePID.idioma.detalleMod()).append("\n");
			sb.append(MonitorDePID.idioma.ubicacion()).append(": ").append(mod.ubicacion_para_publicar()).append("\n");
			sb.append(MonitorDePID.idioma.mixinsCantidad()).append(": ").append(contarMixinsEnMod(mod)).append("\n");
			areaContenido.establecerCodigo(sb.toString());
			return;
		}

		if (objetoReal instanceof Object[]) {
			Object[] datos = (Object[]) objetoReal;
			if (datos.length >= 3 && datos[0] instanceof ArchivoDeMod && datos[1] instanceof String
					&& datos[2] instanceof String) {

				ArchivoDeMod mod = (ArchivoDeMod) datos[0];
				String clase = (String) datos[1];
				String tipo = (String) datos[2];

				if ("MIXIN".equals(tipo)) {
					ArchivoDeMod.MixinInfo info = mod.obtenerInfoMixin(normalizarNombreClaseInterno(clase));
					sb.append(MonitorDePID.idioma.mixinsDetalleMixin()).append("\n");
					sb.append(MonitorDePID.idioma.clase()).append(": ").append(clase).append("\n");
					sb.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar())
							.append("\n\n");

					if (info != null) {
						sb.append(MonitorDePID.idioma.mixinsTargets()).append(": ").append(info.obtenerTargets().size())
								.append("\n");
						for (String t : info.obtenerTargets()) {
							sb.append("- ").append(t).append("\n");
						}
						sb.append("\n");

						sb.append(MonitorDePID.idioma.mixinsMetodos()).append(": ")
								.append(info.obtenerMetodosMixin().size()).append("\n");
						for (ArchivoDeMod.MixinMetodoInfo mm : info.obtenerMetodosMixin()) {
							sb.append("- ").append(mm.obtenerNombre()).append(mm.obtenerDescriptor()).append("\n");
						}
						sb.append("\n");

						sb.append(MonitorDePID.idioma.mixinsCampos()).append(": ")
								.append(info.obtenerCamposMixin().size()).append("\n");
						for (ArchivoDeMod.MixinCampoInfo cm : info.obtenerCamposMixin()) {
							sb.append("- ").append(cm.obtenerNombre()).append(" ").append(cm.obtenerDescriptor())
									.append("\n");
						}
					}

					areaContenido.establecerCodigo(sb.toString());
					return;
				}

				if ("TARGET".equals(tipo) && datos.length >= 4 && datos[3] instanceof String) {
					String target = (String) datos[3];
					sb.append(MonitorDePID.idioma.mixinsDetalleTarget()).append("\n");
					sb.append(MonitorDePID.idioma.clase()).append(": ").append(clase).append("\n");
					sb.append(MonitorDePID.idioma.mixinsTarget()).append(": ").append(target).append("\n");
					sb.append("\n");
					sb.append(MonitorDePID.idioma.mixinsConflictosPosibles()).append(": ")
							.append(buscarConflictosPorTarget(target).size()).append("\n");
					areaContenido.establecerCodigo(sb.toString());
					return;
				}

				if ("METODO".equals(tipo) && datos.length >= 4 && datos[3] instanceof ArchivoDeMod.MixinMetodoInfo) {
					ArchivoDeMod.MixinMetodoInfo metodo = (ArchivoDeMod.MixinMetodoInfo) datos[3];
					sb.append(MonitorDePID.idioma.mixinsDetalleMetodo()).append("\n");
					sb.append(MonitorDePID.idioma.clase()).append(": ").append(clase).append("\n");
					sb.append(MonitorDePID.idioma.nombres()).append(": ").append(metodo.obtenerNombre()).append("\n");
					sb.append("Descriptor: ").append(metodo.obtenerDescriptor()).append("\n");
					sb.append(MonitorDePID.idioma.mixinsTargetsMetodo()).append(": ")
							.append(metodo.obtenerTargets().size()).append("\n");
					for (String t : metodo.obtenerTargets()) {
						sb.append("- ").append(t).append("\n");
					}
					areaContenido.establecerCodigo(sb.toString());
					return;
				}

				if ("CAMPO".equals(tipo) && datos.length >= 4 && datos[3] instanceof ArchivoDeMod.MixinCampoInfo) {
					ArchivoDeMod.MixinCampoInfo campo = (ArchivoDeMod.MixinCampoInfo) datos[3];
					sb.append(MonitorDePID.idioma.mixinsDetalleCampo()).append("\n");
					sb.append(MonitorDePID.idioma.clase()).append(": ").append(clase).append("\n");
					sb.append(MonitorDePID.idioma.nombres()).append(": ").append(campo.obtenerNombre()).append("\n");
					sb.append("Descriptor: ").append(campo.obtenerDescriptor()).append("\n");
					areaContenido.establecerCodigo(sb.toString());
					return;
				}

				if ("CONFLICTO".equals(tipo) && datos.length >= 4) {
					sb.append(MonitorDePID.idioma.mixinsDetalleConflicto()).append("\n\n");
					sb.append(String.valueOf(datos[3]));
					areaContenido.establecerCodigo(sb.toString());
					return;
				}
			}
		}

		areaContenido.establecerCodigo(String.valueOf(objetoReal));
	}

	/**
	 * Busca conflictos según el nodo pulsado.
	 */
	public void mostrarMenuContextual(MouseEvent e) {
		TreePath seleccion = arbolMixins.getPathForLocation(e.getX(), e.getY());
		if (seleccion == null) {
			return;
		}

		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) seleccion.getLastPathComponent();
		Object user = nodo.getUserObject();
		if (user == null) {
			return;
		}

		final Object real = (user instanceof NodoConTexto) ? ((NodoConTexto) user).objeto() : user;

		JPopupMenu menu = new JPopupMenu();

		JMenuItem itemConflictos = new JMenuItem(MonitorDePID.idioma.mixinsBuscarConflictos());
		itemConflictos.addActionListener(ae -> buscarYMostrarConflictos(real));

		JMenuItem itemDescompilar = new JMenuItem(MonitorDePID.idioma.mixinsDescompilarSeleccion());
		itemDescompilar.addActionListener(ae -> descompilarDesdeObjeto(real));

		menu.add(itemConflictos);
		menu.add(itemDescompilar);
		menu.show(arbolMixins, e.getX(), e.getY());
	}

	/**
	 * Busca y muestra conflictos del nodo seleccionado.
	 */
	public void buscarYMostrarConflictos(Object real) {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.mixinsResultadosConflictos());

		if (real instanceof ArchivoDeMod) {
			ArchivoDeMod mod = (ArchivoDeMod) real;
			Map<String, List<String>> conflictos = buscarConflictosDeMod(mod);

			for (Map.Entry<String, List<String>> e : conflictos.entrySet()) {
				DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(
						new NodoConTexto(e.getKey(), new Object[] { mod, e.getKey(), "CONFLICTO", e.getValue() }));
				for (String s : e.getValue()) {
					nodo.add(new DefaultMutableTreeNode(s));
				}
				raiz.add(nodo);
			}
		} else if (real instanceof Object[]) {
			Object[] datos = (Object[]) real;
			if (datos.length >= 3 && "TARGET".equals(datos[2]) && datos.length >= 4 && datos[3] instanceof String) {
				String target = (String) datos[3];
				List<String> conflictos = buscarConflictosPorTarget(target);
				DefaultMutableTreeNode nodoConf = new DefaultMutableTreeNode(
						new NodoConTexto(target, new Object[] { datos[0], datos[1], "CONFLICTO", conflictos }));
				for (String s : conflictos) {
					nodoConf.add(new DefaultMutableTreeNode(s));
				}
				raiz.add(nodoConf);
			} else if (datos.length >= 4 && "METODO".equals(datos[2])
					&& datos[3] instanceof ArchivoDeMod.MixinMetodoInfo) {
				ArchivoDeMod.MixinMetodoInfo metodo = (ArchivoDeMod.MixinMetodoInfo) datos[3];
				for (String t : metodo.obtenerTargets()) {
					List<String> conflictos = buscarConflictosPorMetodoTarget(t, metodo.obtenerNombre(),
							metodo.obtenerDescriptor());
					DefaultMutableTreeNode nodoConf = new DefaultMutableTreeNode(
							new NodoConTexto(t, new Object[] { datos[0], datos[1], "CONFLICTO", conflictos }));
					for (String s : conflictos) {
						nodoConf.add(new DefaultMutableTreeNode(s));
					}
					raiz.add(nodoConf);
				}
			} else if (datos.length >= 4 && "CAMPO".equals(datos[2])
					&& datos[3] instanceof ArchivoDeMod.MixinCampoInfo) {
				ArchivoDeMod.MixinCampoInfo campo = (ArchivoDeMod.MixinCampoInfo) datos[3];
				List<String> conflictos = buscarConflictosPorCampo(campo.obtenerNombre(), campo.obtenerDescriptor());
				DefaultMutableTreeNode nodoConf = new DefaultMutableTreeNode(new NodoConTexto(campo.obtenerNombre(),
						new Object[] { datos[0], datos[1], "CONFLICTO", conflictos }));
				for (String s : conflictos) {
					nodoConf.add(new DefaultMutableTreeNode(s));
				}
				raiz.add(nodoConf);
			}
		}

		modeloArbol.setRoot(raiz);
		modeloArbol.reload();
	}

	/**
	 * Conflictos a nivel de mod: agrupa targets repetidos de todos sus mixins.
	 */
	public Map<String, List<String>> buscarConflictosDeMod(ArchivoDeMod mod) {
		Map<String, List<String>> porTarget = new LinkedHashMap<>();

		for (String clase : mod.clases()) {
			String claseInterna = normalizarNombreClaseInterno(clase);
			if (claseInterna == null || !mod.esClaseMixin(claseInterna)) {
				continue;
			}

			ArchivoDeMod.MixinInfo info = mod.obtenerInfoMixin(claseInterna);
			if (info == null) {
				continue;
			}

			for (String target : info.obtenerTargets()) {
				List<String> conflictos = buscarConflictosPorTarget(target);
				if (!conflictos.isEmpty()) {
					porTarget.put(target, conflictos);
				}
			}
		}

		return porTarget;
	}

	/**
	 * Busca otros mixins que apunten al mismo target de clase.
	 */
	public List<String> buscarConflictosPorTarget(String target) {
		List<String> out = new ArrayList<>();

		for (ArchivoDeMod mod : obtenerModsConMixins()) {
			for (String clase : mod.clases()) {
				String claseInterna = normalizarNombreClaseInterno(clase);
				if (claseInterna == null || !mod.esClaseMixin(claseInterna)) {
					continue;
				}

				ArchivoDeMod.MixinInfo info = mod.obtenerInfoMixin(claseInterna);
				if (info == null) {
					continue;
				}

				for (String t : info.obtenerTargets()) {
					if (target.equals(t)) {
						out.add(mod.ubicacion_para_publicar() + " :: " + clase);
					}
				}
			}
		}

		return deduplicarStrings(out);
	}

	/**
	 * Busca otros mixins con el mismo target de método.
	 */
	public List<String> buscarConflictosPorMetodoTarget(String targetMetodo, String nombreMetodo, String descriptor) {
		List<String> out = new ArrayList<>();

		for (ArchivoDeMod mod : obtenerModsConMixins()) {
			for (String clase : mod.clases()) {
				String claseInterna = normalizarNombreClaseInterno(clase);
				if (claseInterna == null || !mod.esClaseMixin(claseInterna)) {
					continue;
				}

				ArchivoDeMod.MixinInfo info = mod.obtenerInfoMixin(claseInterna);
				if (info == null) {
					continue;
				}

				for (ArchivoDeMod.MixinMetodoInfo mm : info.obtenerMetodosMixin()) {
					for (String t : mm.obtenerTargets()) {
						if (targetMetodo.equals(t)) {
							out.add(mod.ubicacion_para_publicar() + " :: " + clase + " :: " + mm.obtenerNombre()
									+ mm.obtenerDescriptor());
						}
					}
				}
			}
		}

		return deduplicarStrings(out);
	}

	/**
	 * Busca campos mixin potencialmente conflictivos por nombre y descriptor.
	 * 
	 * No todos los campos shadow tienen target textual, así que aquí el conflicto
	 * es heurístico.
	 */
	public List<String> buscarConflictosPorCampo(String nombreCampo, String descriptor) {
		List<String> out = new ArrayList<>();

		for (ArchivoDeMod mod : obtenerModsConMixins()) {
			for (String clase : mod.clases()) {
				String claseInterna = normalizarNombreClaseInterno(clase);
				if (claseInterna == null || !mod.esClaseMixin(claseInterna)) {
					continue;
				}

				ArchivoDeMod.MixinInfo info = mod.obtenerInfoMixin(claseInterna);
				if (info == null) {
					continue;
				}

				for (ArchivoDeMod.MixinCampoInfo campo : info.obtenerCamposMixin()) {
					if (nombreCampo.equals(campo.obtenerNombre()) && descriptor.equals(campo.obtenerDescriptor())) {
						out.add(mod.ubicacion_para_publicar() + " :: " + clase + " :: " + campo.obtenerNombre() + " "
								+ campo.obtenerDescriptor());
					}
				}
			}
		}

		return deduplicarStrings(out);
	}

	/**
	 * Descompila el elemento seleccionado actual.
	 * 
	 * Si el nodo representa un mixin o resultado conflictivo asociado a una clase,
	 * abre el código de esa clase.
	 */
	public void descompilarElementoSeleccionado() {
		TreePath ruta = arbolMixins.getSelectionPath();
		if (ruta == null) {
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
		if (datos.length >= 2 && datos[0] instanceof ArchivoDeMod && datos[1] instanceof String) {
			ArchivoDeMod mod = (ArchivoDeMod) datos[0];
			String nombreClase = (String) datos[1];
			mostrarCodigoDescompilado(mod, nombreClase);
		}
	}

	public void mostrarCodigoDescompilado(ArchivoDeMod mod, String nombreClase) {
		try {
			String codigo = CfrBase.descompilarClase(nombreClase);
			areaContenido.establecerCodigo(codigo);
		} catch (Exception e) {
			areaContenido.establecerCodigo(MonitorDePID.idioma.mixinsErrorDescompilar() + ": " + e.getMessage());
		}
	}

	public int contarMixinsEnMod(ArchivoDeMod mod) {
		int total = 0;
		for (String clase : mod.clases()) {
			String claseInterna = normalizarNombreClaseInterno(clase);
			if (claseInterna != null && mod.esClaseMixin(claseInterna)) {
				total++;
			}
		}
		return total;
	}

	public List<String> deduplicarStrings(List<String> in) {
		return new ArrayList<>(new LinkedHashSet<>(in));
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
	}

	public ImageIcon crearIcono(String ruta, String textoAlternativo) {
		ImageIcon icono = new ImageIcon(ruta);
		if (icono.getIconWidth() > 0) {
			return icono;
		}

		int tamano = 18;
		BufferedImage imagen = new BufferedImage(tamano, tamano, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = imagen.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(new Color(200, 200, 200, 180));
		g2d.fillOval(0, 0, tamano - 1, tamano - 1);

		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 10));
		FontMetrics fm = g2d.getFontMetrics();
		int x = (tamano - fm.stringWidth(textoAlternativo)) / 2;
		int y = ((tamano - fm.getHeight()) / 2) + fm.getAscent();
		g2d.drawString(textoAlternativo, x, y);

		g2d.dispose();
		return new ImageIcon(imagen);
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

	public class RenderizadorCeldasMixins extends DefaultTreeCellRenderer {
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
					if ("MIXIN".equals(tipo)) {
						setIcon(iconoMixin);
					} else if ("TARGET".equals(tipo) || "TARGETS_CONTENEDOR".equals(tipo)) {
						setIcon(iconoTarget);
					} else if ("METODO".equals(tipo) || "METODOS_CONTENEDOR".equals(tipo)
							|| "METODO_TARGET".equals(tipo)) {
						setIcon(iconoMetodo);
					} else if ("CAMPO".equals(tipo) || "CAMPOS_CONTENEDOR".equals(tipo)) {
						setIcon(iconoCampo);
					} else if ("CONFLICTO".equals(tipo)) {
						setIcon(iconoConflicto);
					}
				}
			}

			return this;
		}
	}

	@Override
	public TipoGUI tipo() {
		// TODO Auto-generated method stub
		return TipoGUI.MIXINS;
	}

}