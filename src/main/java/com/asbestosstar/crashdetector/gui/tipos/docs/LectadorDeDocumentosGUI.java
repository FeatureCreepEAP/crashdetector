package com.asbestosstar.crashdetector.gui.tipos.docs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class LectadorDeDocumentosGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	/**
	 * Registro de implementaciones de GUI de documentos.
	 */
	public static final Map<String, Supplier<LectadorDeDocumentosGUI>> GUIS = new HashMap<String, Supplier<LectadorDeDocumentosGUI>>();

	/**
	 * Código de idioma solo para esta instancia del diálogo. No cambia el idioma
	 * global del programa.
	 */
	protected String codigoIdiomaActual = MonitorDePID.idioma.codigo();

	/**
	 * Conversor de markdown a HTML.
	 */
	protected final MarkdownHTML markdownHTML = new MarkdownHTML();

	/**
	 * Árbol y modelo.
	 */
	protected final JTree arbolDocs = new JTree();
	protected DefaultTreeModel modeloArbol;

	/**
	 * Visor HTML del documento.
	 */
	protected final JEditorPane visorDocumento = new JEditorPane();

	/**
	 * Selector de idioma de esta ventana.
	 */
	protected JComboBox<String> comboIdiomas;

	/**
	 * Paneles estructurales.
	 */
	protected JSplitPane splitPrincipal;
	protected JScrollPane scrollArbol;
	protected JScrollPane scrollDocumento;
	protected JPanel panelInferior;
	protected JPanel panelInferiorCentro;
	protected JPanel panelInferiorDerecha;
	protected JPanel panelRaiz;

	/**
	 * Documento actualmente abierto.
	 */
	protected Path documentoActual;

	/**
	 * Nodo actualmente abierto.
	 */
	protected NodoDocumento nodoActual;

	/**
	 * Mapa visible -> código.
	 */
	protected LinkedHashMap<String, String> mapaIdiomas;

	/**
	 * Representa un documento o carpeta dentro del árbol.
	 */
	protected static class NodoDocumento {
		public final String nombreVisible;
		public final Path rutaAbsoluta;
		public final boolean carpeta;

		public NodoDocumento(String nombreVisible, Path rutaAbsoluta, boolean carpeta) {
			this.nombreVisible = nombreVisible;
			this.rutaAbsoluta = rutaAbsoluta;
			this.carpeta = carpeta;
		}

		@Override
		public String toString() {
			return nombreVisible;
		}
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.DOCS;
	}

	@Override
	public void init() {
		configurarVentanaBase();
		construirInterfazBase();
		recargarArbolDeIdioma();
		aplicarApariencia();
	}

	/**
	 * Construye la ventana base.
	 */
	protected void configurarVentanaBase() {
		setTitle(MonitorDePID.idioma.docsTituloVentana());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1200, 760);
		setMinimumSize(new Dimension(900, 600));
		setLocationRelativeTo(null);

		JLayeredPane capa = new JLayeredPane();
		capa.setLayout(new BorderLayout());
		setContentPane(capa);

		instalarFondoApariencia(capa);

		getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"),
				"cerrar_docs");
		getRootPane().getActionMap().put("cerrar_docs", new javax.swing.AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	/**
	 * Permite que la implementación añada fondo o capas visuales.
	 */
	protected void instalarFondoApariencia(JLayeredPane capa) {
		// Opcional en implementación concreta
	}

	/**
	 * Construcción técnica de la interfaz.
	 */
	protected void construirInterfazBase() {
		panelRaiz = new JPanel(new BorderLayout(8, 8));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Árbol
		modeloArbol = new DefaultTreeModel(new DefaultMutableTreeNode(MonitorDePID.idioma.docsCargando()));
		arbolDocs.setModel(modeloArbol);
		arbolDocs.setRootVisible(true);
		arbolDocs.setShowsRootHandles(true);
		arbolDocs.addTreeSelectionListener(this::alSeleccionarNodo);
		arbolDocs.setCellRenderer(new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
					boolean leaf, int row, boolean hasFocus) {
				super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				return this;
			}
		});

		scrollArbol = new JScrollPane(arbolDocs);

		// Visor
		visorDocumento.setEditable(false);
		visorDocumento.setContentType("text/html");
		visorDocumento.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		visorDocumento.addHyperlinkListener(this::alHipervinculoDocumento);

		scrollDocumento = new JScrollPane(visorDocumento);

		// Split principal
		splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollArbol, scrollDocumento);
		splitPrincipal.setResizeWeight(0.28);

		// Pie inferior
		panelInferior = new JPanel(new BorderLayout(12, 0));

		panelInferiorCentro = new JPanel(new BorderLayout(8, 8));
		panelInferiorDerecha = crearPanelIlustracion();

		mapaIdiomas = mapaParaComboBoxIdiomas();
		comboIdiomas = crearComboIdiomas();

		JPanel panelFilaInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
		panelFilaInferior.add(comboIdiomas);

		JEditorPane ayuda = new JEditorPane();
		ayuda.setEditable(false);
		ayuda.setContentType("text/html");
		ayuda.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		ayuda.setText(envolverHtmlSimple(MonitorDePID.idioma.docsAyudaDeUso()));

		panelInferiorCentro.add(panelFilaInferior, BorderLayout.NORTH);
		panelInferiorCentro.add(new JScrollPane(ayuda), BorderLayout.CENTER);

		panelInferior.add(panelInferiorCentro, BorderLayout.CENTER);
		panelInferior.add(panelInferiorDerecha, BorderLayout.EAST);

		panelRaiz.add(splitPrincipal, BorderLayout.CENTER);
		panelRaiz.add(panelInferior, BorderLayout.SOUTH);

		JLayeredPane capa = (JLayeredPane) getContentPane();
		capa.add(panelRaiz, BorderLayout.CENTER);
		capa.setLayer(panelRaiz, JLayeredPane.PALETTE_LAYER);
		this.setVisible(true);
	}

	/**
	 * La implementación concreta crea aquí el panel con la ilustración.
	 */
	protected abstract JPanel crearPanelIlustracion();

	/**
	 * La implementación concreta aplica colores, bordes y tipografías.
	 */
	protected abstract void aplicarApariencia();

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
		repaint();
	}

	protected JComboBox<String> crearComboIdiomas() {
		JComboBox<String> combo = new JComboBox<String>(mapaIdiomas.keySet().toArray(new String[0]));

		String nombreVisibleActual = Idioma.nombreDeIdiomaDesdeCodigo(codigoIdiomaActual);
		if (nombreVisibleActual != null) {
			combo.setSelectedItem(nombreVisibleActual);
		}

		combo.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String visible = (String) combo.getSelectedItem();
				String codigo = Idioma.codigoDesdeNombreVisible(visible);

				if (codigo != null && !codigo.equals(codigoIdiomaActual)) {
					codigoIdiomaActual = codigo;
					recargarArbolDeIdioma();
				}
			}
		});

		return combo;
	}

	/**
	 * Recarga el árbol para el idioma actual de esta ventana.
	 */
	protected void recargarArbolDeIdioma() {
		Path carpetaIdioma = carpetaDocsDeIdiomaActual();

		DefaultMutableTreeNode raiz;

		if (carpetaIdioma == null || !Files.isDirectory(carpetaIdioma)) {
			raiz = new DefaultMutableTreeNode(new NodoDocumento(MonitorDePID.idioma.docsNoHayDocumentos(), null, true));
			modeloArbol.setRoot(raiz);
			expandirTodo();
			visorDocumento.setText(envolverHtmlSimple(MonitorDePID.idioma.docsNoHayDocumentos()));
			documentoActual = null;
			nodoActual = null;
			return;
		}

		String nombreRaiz = nombreVisibleDeIdiomaActual();
		raiz = new DefaultMutableTreeNode(new NodoDocumento(nombreRaiz, carpetaIdioma, true));
		construirNodosRecursivos(raiz, carpetaIdioma);

		modeloArbol.setRoot(raiz);
		expandirTodo();

		Path candidato = documentoActual;
		if (candidato != null && Files.exists(candidato) && candidato.startsWith(carpetaIdioma)) {
			seleccionarDocumentoEnArbol(candidato);
			abrirDocumento(candidato);
		} else {
			Path primero = encontrarPrimerMarkdown(carpetaIdioma);
			if (primero != null) {
				seleccionarDocumentoEnArbol(primero);
				abrirDocumento(primero);
			} else {
				visorDocumento.setText(envolverHtmlSimple(MonitorDePID.idioma.docsNoHayMarkdown()));
			}
		}
	}

	protected void construirNodosRecursivos(DefaultMutableTreeNode nodoPadre, Path carpeta) {
		try {
			List<Path> hijos = new ArrayList<Path>();
			Files.list(carpeta).forEach(hijos::add);

			Collections.sort(hijos, new Comparator<Path>() {
				@Override
				public int compare(Path a, Path b) {
					boolean ad = Files.isDirectory(a);
					boolean bd = Files.isDirectory(b);
					if (ad && !bd)
						return -1;
					if (!ad && bd)
						return 1;
					return a.getFileName().toString().compareToIgnoreCase(b.getFileName().toString());
				}
			});

			for (Path hijo : hijos) {
				boolean esDir = Files.isDirectory(hijo);

				if (!esDir && !esMarkdown(hijo)) {
					continue;
				}

				String nombre = nombreVisibleDesdeRuta(hijo);
				DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(new NodoDocumento(nombre, hijo, esDir));
				nodoPadre.add(nodo);

				if (esDir) {
					construirNodosRecursivos(nodo, hijo);
				}
			}
		} catch (IOException ex) {
			CrashDetectorLogger.logException(ex);
		}
	}

	protected boolean esMarkdown(Path ruta) {
		if (ruta == null || ruta.getFileName() == null) {
			return false;
		}
		String n = ruta.getFileName().toString().toLowerCase();
		return n.endsWith(".md") || n.endsWith(".markdown");
	}

	protected String nombreVisibleDesdeRuta(Path ruta) {
		String nombre = ruta.getFileName().toString();
		if (Files.isDirectory(ruta)) {
			return nombre;
		}
		int idx = nombre.lastIndexOf('.');
		if (idx > 0) {
			return nombre.substring(0, idx);
		}
		return nombre;
	}

	protected void alSeleccionarNodo(TreeSelectionEvent e) {
		TreePath path = e.getPath();
		if (path == null) {
			return;
		}

		DefaultMutableTreeNode nodoTree = (DefaultMutableTreeNode) path.getLastPathComponent();
		Object obj = nodoTree.getUserObject();

		if (!(obj instanceof NodoDocumento)) {
			return;
		}

		NodoDocumento nodo = (NodoDocumento) obj;
		if (nodo.carpeta || nodo.rutaAbsoluta == null) {
			return;
		}

		abrirDocumento(nodo.rutaAbsoluta);
	}

	protected void abrirDocumento(Path ruta) {
		if (ruta == null || !Files.exists(ruta)) {
			visorDocumento.setText(envolverHtmlSimple(MonitorDePID.idioma.docsDocumentoNoEncontrado()));
			documentoActual = null;
			nodoActual = null;
			return;
		}

		try {
			String md = new String(Files.readAllBytes(ruta), StandardCharsets.UTF_8);
			String html = markdownHTML.HTMLdesdeMarkdown(md);
			visorDocumento.setText(html);
			visorDocumento.setCaretPosition(0);
			documentoActual = ruta;
			nodoActual = new NodoDocumento(nombreVisibleDesdeRuta(ruta), ruta, false);
			setTitle(MonitorDePID.idioma.docsTituloVentana() + " - " + nombreVisibleDesdeRuta(ruta));
		} catch (IOException ex) {
			CrashDetectorLogger.logException(ex);
			visorDocumento.setText(
					envolverHtmlSimple(MonitorDePID.idioma.docsErrorAlAbrirDocumento() + "<br>" + ex.getMessage()));
		}
	}

	protected void alHipervinculoDocumento(HyperlinkEvent e) {
		if (e.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
			return;
		}

		String url = e.getDescription();
		if (url == null) {
			return;
		}

		try {
			if (url.startsWith("docs://")) {
				procesarHipervinculo(url);
				return;
			}

			if (url.startsWith("file:")) {
				Desktop.getDesktop().browse(new java.net.URI(url));
				return;
			}

			enlanceEvento(e);
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
		}
	}

	/**
	 * Procesa hipervínculos docs://
	 *
	 * Formatos soportados: docs://es/manual.md docs://manual.md
	 * docs://gui/instalacion.md
	 */
	public void procesarHipervinculo(String url) {
		try {
			String sinPrefijo = url.substring("docs://".length()).trim();
			if (sinPrefijo.isEmpty()) {
				return;
			}

			String codigoObjetivo = null;
			String rutaRelativa;

			int slash = sinPrefijo.indexOf('/');
			if (slash > 0) {
				String primerSegmento = sinPrefijo.substring(0, slash);
				if (Idioma.nombreDeIdiomaDesdeCodigo(primerSegmento) != null || existeCodigoIdioma(primerSegmento)) {
					codigoObjetivo = primerSegmento;
					rutaRelativa = sinPrefijo.substring(slash + 1);
				} else {
					rutaRelativa = sinPrefijo;
				}
			} else {
				rutaRelativa = sinPrefijo;
			}

			if (codigoObjetivo != null) {
				codigoIdiomaActual = codigoObjetivo;
				String visible = Idioma.nombreDeIdiomaDesdeCodigo(codigoObjetivo);
				if (visible != null) {
					comboIdiomas.setSelectedItem(visible);
				}
			}

			Path base = carpetaDocsDeIdiomaActual();
			if (base == null) {
				return;
			}

			Path destino = base.resolve(rutaRelativa).normalize();
			recargarArbolDeIdioma();

			if (Files.exists(destino) && destino.startsWith(base)) {
				seleccionarDocumentoEnArbol(destino);
				abrirDocumento(destino);
				toFront();
				requestFocus();
			} else {
				visorDocumento.setText(envolverHtmlSimple(
						MonitorDePID.idioma.docsDocumentoNoEncontrado() + "<br>" + escaparHtml(rutaRelativa)));
			}
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
		}
	}

	protected boolean existeCodigoIdioma(String codigo) {
		return codigo != null && Idioma.nombreDeIdiomaDesdeCodigo(codigo) != null;
	}

	protected void seleccionarDocumentoEnArbol(Path ruta) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) modeloArbol.getRoot();
		TreePath treePath = encontrarTreePath(root, ruta);

		if (treePath != null) {
			arbolDocs.setSelectionPath(treePath);
			arbolDocs.scrollPathToVisible(treePath);
		}
	}

	protected TreePath encontrarTreePath(DefaultMutableTreeNode actual, Path ruta) {
		Object obj = actual.getUserObject();
		if (obj instanceof NodoDocumento) {
			NodoDocumento nd = (NodoDocumento) obj;
			if (ruta != null && ruta.equals(nd.rutaAbsoluta)) {
				return new TreePath(actual.getPath());
			}
		}

		for (int i = 0; i < actual.getChildCount(); i++) {
			DefaultMutableTreeNode hijo = (DefaultMutableTreeNode) actual.getChildAt(i);
			TreePath encontrado = encontrarTreePath(hijo, ruta);
			if (encontrado != null) {
				return encontrado;
			}
		}
		return null;
	}

	protected Path encontrarPrimerMarkdown(Path carpeta) {
		try {
			List<Path> encontrados = new ArrayList<Path>();
			Files.walk(carpeta).forEach(p -> {
				if (Files.isRegularFile(p) && esMarkdown(p)) {
					encontrados.add(p);
				}
			});

			Collections.sort(encontrados, new Comparator<Path>() {
				@Override
				public int compare(Path a, Path b) {
					return a.toString().compareToIgnoreCase(b.toString());
				}
			});

			return encontrados.isEmpty() ? null : encontrados.get(0);
		} catch (IOException ex) {
			CrashDetectorLogger.logException(ex);
			return null;
		}
	}

	protected void expandirTodo() {
		for (int i = 0; i < arbolDocs.getRowCount(); i++) {
			arbolDocs.expandRow(i);
		}
	}

	protected Path carpetaDocsDeIdiomaActual() {
		String nombreIdiomaCarpeta = nombreAsciiDeIdiomaActual();
		if (nombreIdiomaCarpeta == null || nombreIdiomaCarpeta.isEmpty()) {
			return null;
		}
		return Statics.carpeta.resolve("docs").resolve(nombreIdiomaCarpeta);
	}

	protected String nombreAsciiDeIdiomaActual() {
		try {
			Idioma idiomaTemp = Idioma.desdeCodigo(codigoIdiomaActual);
			if (idiomaTemp != null) {
				return idiomaTemp.nombre_del_idioma_espanol_minusculas_ascii();
			}
		} catch (Throwable ignored) {
		}

		if ("es".equalsIgnoreCase(codigoIdiomaActual))
			return "espanol";
		if ("en".equalsIgnoreCase(codigoIdiomaActual))
			return "english";
		return MonitorDePID.idioma.nombre_del_idioma_espanol_minusculas_ascii();
	}

	protected String nombreVisibleDeIdiomaActual() {
		String visible = Idioma.nombreDeIdiomaDesdeCodigo(codigoIdiomaActual);
		return visible != null ? visible : codigoIdiomaActual;
	}

	protected String envolverHtmlSimple(String cuerpo) {
		return "<html><body style='font-family:sans-serif;'>" + cuerpo + "</body></html>";
	}

	protected String escaparHtml(String s) {
		if (s == null) {
			return "";
		}
		return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		return Collections.emptyList();
	}
}