package com.asbestosstar.crashdetector.gui.tipos.arbol;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.elementos.ElementoOverlayCarga;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrBase;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrSakuraRiddle;

public abstract class ArbolDeModsGUI extends JFrame implements BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<ArbolDeModsGUI>> GUIS = new HashMap<>();

	public JTree arbolModulos;
	public DefaultTreeModel modeloArbol;
	public JTextField campoBuscar;
	public JComboBox<String> comboFiltro;
	public JTextArea areaContenido;
	public JButton botonReset;
	public JLabel imagenHamu;

	// Iconos para los diferentes tipos de nodos
	public ImageIcon iconoMod;
	public ImageIcon iconoClase;
	public ImageIcon iconoMetodo;
	public ImageIcon iconoCampo;
	public ImageIcon iconoPaquete;
	public ImageIcon iconoReferenciaMetodo;
	public ImageIcon iconoReferenciaCampo;
	public ImageIcon iconoConstante;
	public ImageIcon iconoMixin;

	public ElementoOverlayCarga overlayCarga;
	public volatile boolean cargando = false;

	// Trabajadores en curso (para cancelar si el usuario vuelve a buscar)
	public SwingWorker<Void, Void> workerConstruir;
	public SwingWorker<DefaultMutableTreeNode, Void> workerBuscar;

	/**
	 * Índice plano que permite búsquedas instantáneas. Clave: término en
	 * minúsculas, Valor: lista de descriptores que lo contienen.
	 */
	public Map<String, List<PathDescriptor>> indiceBusqueda = new ConcurrentHashMap<>();

	public ArbolDeModsGUI() {
	}

	/**
	 * Normaliza nombres de clase para ASM/bytecode: - Acepta "com.a.B", "com/a/B",
	 * "com/a/B.class" y también "Lcom/a/B;" - Devuelve SIEMPRE formato interno
	 * "com/a/B" (sin ".class", sin "L...;")
	 */
	public static String normalizarNombreClaseInterno(String nombre) {
		if (nombre == null)
			return null;
		String s = nombre.trim();
		if (s.isEmpty())
			return s;

		// Si viene como descriptor de tipo: Lcom/x/Y;
		if (s.length() >= 2 && s.charAt(0) == 'L' && s.endsWith(";")) {
			s = s.substring(1, s.length() - 1);
		}

		// Quitar sufijo ".class" si aparece
		if (s.toLowerCase().endsWith(".class")) {
			s = s.substring(0, s.length() - ".class".length());
		}

		// Convertir puntos a barras
		if (s.indexOf('.') >= 0) {
			s = s.replace('.', '/');
		}

		return s;
	}

	/**
	 * Construye el índice de búsqueda de forma concurrente usando el doble del
	 * número de hilos lógicos disponibles. Cada mod raíz se indexa en su propio
	 * mapa local; los mapas se fusionan al final para evitar contención.
	 */
	public void construirIndice() {
		indiceBusqueda.clear();
		int totalMods = Buscador.mods.size();
		if (totalMods == 0) {
			com.asbestosstar.crashdetector.CrashDetectorLogger.log("✅ No hay mods para indexar.");
			return;
		}

		int numHilos = Math.max(1, Runtime.getRuntime().availableProcessors() * 2);
		ExecutorService executor = Executors.newFixedThreadPool(numHilos, r -> {
			Thread hilo = new Thread(r, "Indexador-" + r.hashCode());
			hilo.setDaemon(true);
			return hilo;
		});

		List<Future<Map<String, List<PathDescriptor>>>> futuros = new ArrayList<>(totalMods);
		final AtomicInteger completados = new AtomicInteger(0);

		for (ArchivoDeMod mod : Buscador.mods) {
			futuros.add(executor.submit(() -> {
				try {
					Map<String, List<PathDescriptor>> mapaLocal = new HashMap<>();
					indexarModConcurrente(mod, mapaLocal);

					int ya = completados.incrementAndGet();
					com.asbestosstar.crashdetector.CrashDetectorLogger.log(
							String.format("✅ Mod indexado: %s (%d/%d)", mod.ubicacion_para_publicar(), ya, totalMods));

					return mapaLocal;
				} catch (Throwable t) {
					com.asbestosstar.crashdetector.CrashDetectorLogger.logException(t);
					return new HashMap<>();
				}
			}));
		}

		executor.shutdown();
		try {
			if (!executor.awaitTermination(120, TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}

		int totalTerminos = 0;
		for (Future<Map<String, List<PathDescriptor>>> futuro : futuros) {
			try {
				Map<String, List<PathDescriptor>> mapa = futuro.get();
				for (Map.Entry<String, List<PathDescriptor>> entry : mapa.entrySet()) {
					String termino = entry.getKey();
					List<PathDescriptor> descriptores = entry.getValue();
					if (termino != null && descriptores != null && !descriptores.isEmpty()) {
						indiceBusqueda.computeIfAbsent(termino, k -> new ArrayList<>()).addAll(descriptores);
						totalTerminos += descriptores.size();
					}
				}
			} catch (Exception e) {
				com.asbestosstar.crashdetector.CrashDetectorLogger
						.log("Error al fusionar mapa de mod: " + e.getMessage());
			}
		}

		com.asbestosstar.crashdetector.CrashDetectorLogger.log(
				String.format("✅ Índice construido con %d términos únicos y ~%d entradas totales, usando %d hilos.",
						indiceBusqueda.size(), totalTerminos, numHilos));
	}

	/**
	 * Indexa recursivamente un mod en el mapa local dado (NO concurrente).
	 * 
	 * Esta versión: - indexa mods embebidos - indexa paquetes, clases, métodos y
	 * campos - indexa mixins, sus targets, métodos y campos shadow - indexa
	 * referencias de clase, método y campo - indexa constantes encontradas en
	 * métodos
	 */
	public void indexarModConcurrente(ArchivoDeMod mod, Map<String, List<PathDescriptor>> indiceLocal) {
		String modUbicacion = mod.ubicacion_para_publicar();
		String modUbicacionLower = modUbicacion.toLowerCase();

		// El propio mod
		PathDescriptor descMod = new PathDescriptor(modUbicacion, null, null, null, null, "MOD");
		indexarTerminoConcurrente(modUbicacionLower, descMod, indiceLocal);

		// 🔹 Recurre sobre submods (jar-en-jar)
		for (ArchivoDeMod submod : mod.mods_en_mods()) {
			indexarModConcurrente(submod, indiceLocal);
		}

		// 🔹 Recorre clases del mod
		for (String clasePuntos : mod.clases()) {
			String paquete = paqueteDe(clasePuntos);
			String claseInterna = normalizarNombreClaseInterno(clasePuntos);

			// Paquete
			if (!paquete.isEmpty()) {
				PathDescriptor descPaq = new PathDescriptor(modUbicacion, paquete, null, null, null, "PAQUETE");
				indexarTerminoConcurrente(paquete.toLowerCase(), descPaq, indiceLocal);
			}

			// Clase
			PathDescriptor descClase = new PathDescriptor(modUbicacion, paquete, clasePuntos, null, null, "CLASE");
			indexarTerminoConcurrente(clasePuntos.toLowerCase(), descClase, indiceLocal);

			// También indexar el nombre interno ASM por si se busca con barras
			if (claseInterna != null && !claseInterna.isEmpty()) {
				indexarTerminoConcurrente(claseInterna.toLowerCase(), descClase, indiceLocal);
			}

			// 🔹 Información de mixin
			if (claseInterna != null && mod.esClaseMixin(claseInterna)) {
				ArchivoDeMod.MixinInfo mixinInfo = mod.obtenerInfoMixin(claseInterna);
				if (mixinInfo != null) {
					// Indexar la propia clase mixin como categoría de mixin
					PathDescriptor descMixin = new PathDescriptor(modUbicacion, paquete, clasePuntos, null, null,
							"MIXIN");
					indexarTerminoConcurrente(clasePuntos.toLowerCase(), descMixin, indiceLocal);

					// Targets del mixin
					for (String target : mixinInfo.obtenerTargets()) {
						PathDescriptor descMixinTarget = new PathDescriptor(modUbicacion, paquete, clasePuntos,
								"@Mixin target", target, "MIXIN_TARGET");

						indexarTerminoConcurrente(target.toLowerCase(), descMixinTarget, indiceLocal);

						String targetInterno = normalizarNombreClaseInterno(target);
						if (targetInterno != null && !targetInterno.isEmpty()) {
							indexarTerminoConcurrente(targetInterno.toLowerCase(), descMixinTarget, indiceLocal);
							indexarTerminoConcurrente(convertirFormatoClasePuntos(targetInterno).toLowerCase(),
									descMixinTarget, indiceLocal);
						}
					}

					// Métodos mixin (@Inject / @Overwrite)
					for (ArchivoDeMod.MixinMetodoInfo metodoMixin : mixinInfo.obtenerMetodosMixin()) {
						String firmaMetodo = (metodoMixin.obtenerNombre() + metodoMixin.obtenerDescriptor())
								.toLowerCase();

						PathDescriptor descMixinMetodo = new PathDescriptor(modUbicacion, paquete, clasePuntos,
								metodoMixin.obtenerNombre(), metodoMixin.obtenerDescriptor(), "MIXIN_METODO");

						indexarTerminoConcurrente(firmaMetodo, descMixinMetodo, indiceLocal);
						indexarTerminoConcurrente(metodoMixin.obtenerNombre().toLowerCase(), descMixinMetodo,
								indiceLocal);

						for (String targetMetodo : metodoMixin.obtenerTargets()) {
							indexarTerminoConcurrente(targetMetodo.toLowerCase(), descMixinMetodo, indiceLocal);
						}
					}

					// Campos mixin (@Shadow)
					for (ArchivoDeMod.MixinCampoInfo campoMixin : mixinInfo.obtenerCamposMixin()) {
						String firmaCampo = (campoMixin.obtenerNombre() + " " + campoMixin.obtenerDescriptor())
								.toLowerCase();

						PathDescriptor descMixinCampo = new PathDescriptor(modUbicacion, paquete, clasePuntos,
								campoMixin.obtenerNombre(), campoMixin.obtenerDescriptor(), "MIXIN_CAMPO");

						indexarTerminoConcurrente(firmaCampo, descMixinCampo, indiceLocal);
						indexarTerminoConcurrente(campoMixin.obtenerNombre().toLowerCase(), descMixinCampo,
								indiceLocal);
					}
				}
			}

			// Si no hay análisis bytecode disponible o la clase no existe realmente, saltar
			if (!Buscador.puedeAnalizarElContentidoDeClase() || claseInterna == null
					|| !mod.existeClase(claseInterna)) {
				continue;
			}

			// 🔹 Métodos y sus referencias
			List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseInterna);
			for (ArchivoDeMod.InfoMetodo metodo : metodos) {
				PathDescriptor descMetodo = new PathDescriptor(modUbicacion, paquete, clasePuntos,
						metodo.obtenerNombre(), metodo.obtenerDescriptor(), "METODO");

				indexarTerminoConcurrente(metodo.obtenerNombre().toLowerCase(), descMetodo, indiceLocal);
				indexarTerminoConcurrente((metodo.obtenerNombre() + metodo.obtenerDescriptor()).toLowerCase(),
						descMetodo, indiceLocal);

				// Referencias a métodos
				for (ArchivoDeMod.Referencia refMetodo : metodo.obtenerReferenciasAMetodos()) {
					String claseRef = refMetodo.obtenerClase();
					String claseRefPuntos = convertirFormatoClasePuntos(claseRef);

					PathDescriptor descRefMetodo = new PathDescriptor(modUbicacion, paquete, clasePuntos,
							metodo.obtenerNombre(), metodo.obtenerDescriptor(), "REFERENCIA_METODO");

					indexarTerminoConcurrente(claseRefPuntos.toLowerCase(), descRefMetodo, indiceLocal);
					indexarTerminoConcurrente(refMetodo.obtenerNombre().toLowerCase(), descRefMetodo, indiceLocal);
					indexarTerminoConcurrente(
							(claseRefPuntos + "." + refMetodo.obtenerNombre() + refMetodo.obtenerDescriptor())
									.toLowerCase(),
							descRefMetodo, indiceLocal);

					// También indexar classref derivada de esta referencia
					PathDescriptor descRefClase = new PathDescriptor(modUbicacion, paquete, clasePuntos,
							metodo.obtenerNombre(), metodo.obtenerDescriptor(), "REFERENCIA_CLASE");
					indexarTerminoConcurrente(claseRefPuntos.toLowerCase(), descRefClase, indiceLocal);
				}

				// Referencias a campos
				for (ArchivoDeMod.Referencia refCampo : metodo.obtenerReferenciasACampos()) {
					String claseRef = refCampo.obtenerClase();
					String claseRefPuntos = convertirFormatoClasePuntos(claseRef);

					PathDescriptor descRefCampo = new PathDescriptor(modUbicacion, paquete, clasePuntos,
							metodo.obtenerNombre(), metodo.obtenerDescriptor(), "REFERENCIA_CAMPO");

					indexarTerminoConcurrente(claseRefPuntos.toLowerCase(), descRefCampo, indiceLocal);
					indexarTerminoConcurrente(refCampo.obtenerNombre().toLowerCase(), descRefCampo, indiceLocal);
					indexarTerminoConcurrente(
							(claseRefPuntos + "." + refCampo.obtenerNombre() + " " + refCampo.obtenerDescriptor())
									.toLowerCase(),
							descRefCampo, indiceLocal);

					// También indexar classref derivada de esta referencia
					PathDescriptor descRefClase = new PathDescriptor(modUbicacion, paquete, clasePuntos,
							metodo.obtenerNombre(), metodo.obtenerDescriptor(), "REFERENCIA_CLASE");
					indexarTerminoConcurrente(claseRefPuntos.toLowerCase(), descRefClase, indiceLocal);
				}

				// Constantes del método
//				try {
//					List<ArchivoDeMod.Constante> constantes = mod.buscarConstantesEnMetodo(claseInterna,
//							metodo.obtenerNombre(), metodo.obtenerDescriptor());
//
//					for (ArchivoDeMod.Constante k : constantes) {
//						String textoConstante = formatearConstante(k);
//						if (textoConstante != null && !textoConstante.isEmpty()) {
//							PathDescriptor descConst = new PathDescriptor(modUbicacion, paquete, clasePuntos,
//									metodo.obtenerNombre(), metodo.obtenerDescriptor(), "CONSTANTE");
//							indexarTerminoConcurrente(textoConstante.toLowerCase(), descConst, indiceLocal);
//						}
//					}
//				} catch (Throwable t) {
//					CrashDetectorLogger.logException(t);
//				}
			}

			// 🔹 Campos declarados
			List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(claseInterna);
			for (ArchivoDeMod.InfoCampo campo : campos) {
				PathDescriptor descCampo = new PathDescriptor(modUbicacion, paquete, clasePuntos, campo.obtenerNombre(),
						campo.obtenerDescriptor(), "CAMPO");

				indexarTerminoConcurrente(campo.obtenerNombre().toLowerCase(), descCampo, indiceLocal);
				indexarTerminoConcurrente((campo.obtenerNombre() + " " + campo.obtenerDescriptor()).toLowerCase(),
						descCampo, indiceLocal);
			}
		}
	}

	/**
	 * Indexa un término en un mapa local (NO concurrente). Usa HashMap + ArrayList.
	 */
	public void indexarTerminoConcurrente(String textoOriginal, PathDescriptor desc,
			Map<String, List<PathDescriptor>> indice) {
		if (textoOriginal == null || textoOriginal.isEmpty()) {
			return;
		}
		for (String clave : extraerClavesIndice(textoOriginal)) {
			agregarAlIndiceLocal(clave, desc, indice);
		}
	}

	public void agregarAlIndiceLocal(String termino, PathDescriptor descriptor,
			Map<String, List<PathDescriptor>> indice) {
		if (termino == null || termino.isEmpty())
			return;
		indice.computeIfAbsent(termino, k -> new ArrayList<>()).add(descriptor);
	}

	public static java.util.Set<String> extraerClavesIndice(String texto) {
		java.util.LinkedHashSet<String> claves = new java.util.LinkedHashSet<>();
		if (texto == null || texto.isEmpty())
			return claves;

		StringBuilder token = new StringBuilder(64);

		for (int i = 0; i <= texto.length(); i++) {
			char c = i < texto.length() ? texto.charAt(i) : ' ';

			boolean alnum = Character.isLetterOrDigit(c);

			if (alnum) {
				token.append(Character.toLowerCase(c));
			} else {
				if (token.length() >= 3) {
					claves.add(token.toString());
				}
				token.setLength(0);
			}
		}

		return claves;
	}

	public static java.util.Set<String> extraerClavesConsulta(String consulta) {
		java.util.LinkedHashSet<String> claves = new java.util.LinkedHashSet<>();
		if (consulta == null)
			return claves;

		String lower = consulta.toLowerCase();
		String[] tokens = lower.split("[^a-z0-9]+");
		for (String token : tokens) {
			if (token == null || token.isEmpty())
				continue;
			if (token.length() < 3) {
				claves.add(token);
				continue;
			}
			claves.add(token);
			for (int i = 0; i + 3 <= token.length(); i++) {
				claves.add(token.substring(i, i + 3));
			}
		}
		return claves;
	}

	public void indexarTermino(String textoOriginal, PathDescriptor desc) {
		if (textoOriginal == null || textoOriginal.isEmpty()) {
			return;
		}
		for (String clave : extraerClavesIndice(textoOriginal)) {
			agregarAlIndice(clave, desc);
		}
	}

	public void agregarAlIndice(String termino, PathDescriptor descriptor) {
		indiceBusqueda.computeIfAbsent(termino, k -> new ArrayList<>()).add(descriptor);
	}

	public void descompilarElementoSeleccionado() {
		TreePath rutaSeleccionada = arbolModulos.getSelectionPath();
		if (rutaSeleccionada == null) {
			return;
		}

		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) rutaSeleccionada.getLastPathComponent();
		Object objetoUsuario = nodo.getUserObject();

		if (objetoUsuario instanceof NodoConTexto) {
			Object objetoReal = ((NodoConTexto) objetoUsuario).objeto();
			if (objetoReal instanceof Object[] && ((Object[]) objetoReal).length >= 2) {
				Object[] datos = (Object[]) objetoReal;
				if (datos[0] instanceof ArchivoDeMod && datos[1] instanceof String) {
					ArchivoDeMod mod = (ArchivoDeMod) datos[0];
					String nombreClase = (String) datos[1];
					mostrarCodigoDescompilado(mod, nombreClase);
				}
			}
		}
	}

	public void exportarEstructura() {
		JFileChooser selectorArchivo = new JFileChooser();
		selectorArchivo.setSelectedFile(new File("arbol_mods.arbolmods"));
		int resultado = selectorArchivo.showSaveDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			File archivo = selectorArchivo.getSelectedFile();
			if (!archivo.getName().toLowerCase().endsWith(".arbolmods")) {
				archivo = new File(archivo.getParentFile(), archivo.getName() + ".arbolmods");
			}

			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
				NodoArbolExportable estructuraSerializable = convertirArbolASerializable();
				oos.writeObject(estructuraSerializable);

				JOptionPane.showMessageDialog(this,
						MonitorDePID.idioma.estructuraExportada() + ": " + archivo.getAbsolutePath(),
						MonitorDePID.idioma.exito(), JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.errorExportar() + ": " + ex.getMessage(),
						MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void importarEstructura() {
		JFileChooser selectorArchivo = new JFileChooser();
		selectorArchivo.setFileFilter(new FileNameExtensionFilter("Archivos de árbol de mods", "arbolmods"));
		int resultado = selectorArchivo.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			File archivo = selectorArchivo.getSelectedFile();
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
				NodoArbolExportable estructuraSerializable = (NodoArbolExportable) ois.readObject();
				DefaultMutableTreeNode nuevaRaiz = convertirSerializableAArbol(estructuraSerializable);
				modeloArbol = new ModeloArbolConExpandibleMods(nuevaRaiz);
				arbolModulos.setModel(modeloArbol);

				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.estructuraImportada(),
						MonitorDePID.idioma.exito(), JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.errorImportar() + ": " + ex.getMessage(),
						MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static class NodoArbolExportable implements Serializable {
		public static final long serialVersionUID = 1L;

		public String texto;
		public String tipo;
		public List<NodoArbolExportable> hijos;

		public NodoArbolExportable(String texto, String tipo) {
			this.texto = texto;
			this.tipo = tipo;
			this.hijos = new ArrayList<>();
		}

		public void agregarHijo(NodoArbolExportable hijo) {
			hijos.add(hijo);
		}

		public String texto() {
			return texto;
		}

		public String tipo() {
			return tipo;
		}

		public List<NodoArbolExportable> sub() {
			return hijos;
		}
	}

	public NodoArbolExportable convertirArbolASerializable() {
		DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) modeloArbol.getRoot();
		return convertirNodoASerializable(raiz);
	}

	public NodoArbolExportable convertirNodoASerializable(DefaultMutableTreeNode nodo) {
		Object userObject = nodo.getUserObject();
		String texto = "";
		String tipo = "";

		if (userObject instanceof NodoConTexto) {
			texto = ((NodoConTexto) userObject).texto();
			Object objetoReal = ((NodoConTexto) userObject).objeto();

			if (objetoReal instanceof ArchivoDeMod) {
				tipo = "MOD";
			} else if (objetoReal instanceof String) {
				tipo = "PAQUETE";
			} else if (objetoReal instanceof Object[]) {
				Object[] datos = (Object[]) objetoReal;
				if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoMetodo) {
					tipo = "METODO";
				} else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoCampo) {
					tipo = "CAMPO";
				} else if (datos.length >= 4 && datos[3] instanceof ArchivoDeMod.Referencia) {
					ArchivoDeMod.Referencia ref = (ArchivoDeMod.Referencia) datos[3];
					tipo = ref.esMetodo() ? "REFERENCIA_METODO" : "REFERENCIA_CAMPO";
				} else {
					tipo = "CLASE";
				}
			}
		} else {
			texto = nodo.toString();
			tipo = "RAIZ";
		}

		NodoArbolExportable nodoSerializable = new NodoArbolExportable(texto, tipo);

		for (int i = 0; i < nodo.getChildCount(); i++) {
			DefaultMutableTreeNode hijo = (DefaultMutableTreeNode) nodo.getChildAt(i);
			nodoSerializable.agregarHijo(convertirNodoASerializable(hijo));
		}

		return nodoSerializable;
	}

	public DefaultMutableTreeNode convertirSerializableAArbol(NodoArbolExportable nodoSerializable) {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(
				new NodoConTexto(nodoSerializable.texto(), nodoSerializable.tipo()));

		for (NodoArbolExportable serializable : nodoSerializable.sub()) {
			nodo.add(convertirSerializableAArbol(serializable));
		}

		return nodo;
	}

	@Override
	public void init() {
		setTitle(MonitorDePID.idioma.tituloArbolDeMods());
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout());

		getContentPane().setBackground(new Color(144, 203, 239)); // #90cbef

		iconoMod = crearIcono(Statics.carpeta.resolve("imagenes/mod.png").toString(), "M");
		iconoClase = crearIcono(Statics.carpeta.resolve("imagenes/clase.png").toString(), "C");
		iconoMetodo = crearIcono(Statics.carpeta.resolve("imagenes/metodo.png").toString(), "m");
		iconoCampo = crearIcono(Statics.carpeta.resolve("imagenes/campo.png").toString(), "f");
		iconoPaquete = crearIcono(Statics.carpeta.resolve("imagenes/paquete.png").toString(), "P");
		iconoReferenciaMetodo = crearIcono(Statics.carpeta.resolve("imagenes/referencia_metodo.png").toString(), "RM");
		iconoReferenciaCampo = crearIcono(Statics.carpeta.resolve("imagenes/referencia_campo.png").toString(), "RC");
		iconoConstante = crearIcono(Statics.carpeta.resolve("imagenes/referencia_campo.png").toString(), "K");
		iconoMixin = crearIcono(Statics.carpeta.resolve("imagenes/mixin.png").toString(), "MX");

		JPanel barraSuperior = new JPanel(new BorderLayout());
		barraSuperior.setOpaque(false);
		barraSuperior.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelBusqueda.setOpaque(false);
		JLabel etiquetaTipo = new JLabel(MonitorDePID.idioma.tipoBusqueda() + ":");
		String[] opcionesFiltro = { "*", MonitorDePID.idioma.filtroPaquetes(), MonitorDePID.idioma.filtroClases(),
				MonitorDePID.idioma.filtroMetodos(), MonitorDePID.idioma.filtroCampos(),
				MonitorDePID.idioma.filtroReferenciasCampo(), MonitorDePID.idioma.filtroReferenciasMetodo(),
				MonitorDePID.idioma.filtroReferenciasClase(), "Constantes" };
		comboFiltro = new JComboBox<>(opcionesFiltro);
		campoBuscar = new JTextField(30);
		campoBuscar.setToolTipText(MonitorDePID.idioma.tipBuscar());
		JButton botonBuscar = new JButton(MonitorDePID.idioma.botonBuscar());

		panelBusqueda.add(etiquetaTipo);
		panelBusqueda.add(comboFiltro);
		panelBusqueda.add(campoBuscar);
		panelBusqueda.add(botonBuscar);

		imagenHamu = new JLabel(new ImageIcon(Statics.carpeta.resolve("imagenes/hamu.png").toString()));

		botonReset = new JButton(MonitorDePID.idioma.botonResetearArbol());
		botonReset.addActionListener(e -> {
			construirArbolInicialAsync();
			areaContenido.setText("");
			campoBuscar.setText("");
		});

		// New "View Code" button
		JButton botonVerCodigo = new JButton("Ver Código");
		botonVerCodigo.addActionListener(e -> mostrarCodigoClaseSeleccionada());

		arbolModulos = new JTree();
		arbolModulos.setRootVisible(false);
		arbolModulos.setShowsRootHandles(true);
		arbolModulos.setCellRenderer(new RenderizadorCeldasArbol());

		arbolModulos.addTreeWillExpandListener(new TreeWillExpandListener() {
			@Override
			public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
				TreePath path = event.getPath();
				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) path.getLastPathComponent();
				Object userObj = nodo.getUserObject();

				com.asbestosstar.crashdetector.CrashDetectorLogger
						.log("🔍 [EXPAND] Nodo: " + nodo + ", hijos=" + nodo.getChildCount());

				if (userObj instanceof NodoConTexto) {
					Object real = ((NodoConTexto) userObj).objeto();
					com.asbestosstar.crashdetector.CrashDetectorLogger.log(
							"🔍 [EXPAND] Objeto real: " + (real != null ? real.getClass().getSimpleName() : "null"));

					if (real instanceof ArchivoDeMod && nodo.getChildCount() == 0) {
						com.asbestosstar.crashdetector.CrashDetectorLogger
								.log("✅ [EXPAND] Cargando contenido para mod: " + real);
						cargarContenidoModuloAsync(nodo, (ArchivoDeMod) real);
					} else {
						com.asbestosstar.crashdetector.CrashDetectorLogger.log("⚠️ [EXPAND] No cumple: ArchivoDeMod="
								+ (real instanceof ArchivoDeMod) + ", hijos=" + nodo.getChildCount());
					}
				} else {
					com.asbestosstar.crashdetector.CrashDetectorLogger.log("⚠️ [EXPAND] No es NodoConTexto: "
							+ (userObj != null ? userObj.getClass().getSimpleName() : "null"));
				}
			}

			@Override
			public void treeWillCollapse(TreeExpansionEvent event) {
			}
		});

		arbolModulos.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					mostrarMenuContexto(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					mostrarMenuContexto(e);
			}
		});

		arbolModulos.addTreeSelectionListener(e -> {
			TreePath ruta = arbolModulos.getSelectionPath();
			if (ruta == null)
				return;

			DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) ruta.getLastPathComponent();

			if (esNodoMetodo(nodo)) {
				asegurarNodoConstantesPara(nodo);
			}

			Object user = nodo.getUserObject();
			Object objReal = (user instanceof NodoConTexto) ? ((NodoConTexto) user).objeto() : user;
			mostrarDetallesNodo(objReal);
		});

		areaContenido = new JTextArea();
		areaContenido.setEditable(false);
		areaContenido.setLineWrap(true);
		areaContenido.setWrapStyleWord(true);
		areaContenido.setFont(new Font("Monospaced", Font.PLAIN, 12));

		JPanel panelInferior = new JPanel(new BorderLayout());
		panelInferior.setOpaque(false);
		panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelIzquierdo.setOpaque(false);
		panelIzquierdo.add(imagenHamu);

		JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelDerecho.setOpaque(false);

		JButton botonExportar = new JButton(MonitorDePID.idioma.exportar());
		JButton botonImportar = new JButton(MonitorDePID.idioma.importar());

		botonExportar.addActionListener(e -> exportarEstructura());
		botonImportar.addActionListener(e -> importarEstructura());

		panelDerecho.add(botonExportar);
		panelDerecho.add(botonImportar);
		panelDerecho.add(botonVerCodigo); // Add the new "View Code" button

		panelInferior.add(panelIzquierdo, BorderLayout.WEST);
		panelInferior.add(panelDerecho, BorderLayout.EAST);

		add(panelInferior, BorderLayout.SOUTH);
		add(botonReset, BorderLayout.NORTH);
		add(barraSuperior, BorderLayout.CENTER);

		JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(arbolModulos),
				new JScrollPane(areaContenido));
		panelDividido.setDividerLocation(500);
		barraSuperior.add(panelBusqueda, BorderLayout.NORTH);
		barraSuperior.add(panelDividido, BorderLayout.CENTER);

		campoBuscar.addActionListener(e -> buscarEnIndiceAsync());
		botonBuscar.addActionListener(e -> buscarEnIndiceAsync());
		comboFiltro.addActionListener(e -> buscarEnIndiceAsync());

		initOverlayCarga();
		setCargando(false);

		DefaultMutableTreeNode placeholder = new DefaultMutableTreeNode(MonitorDePID.idioma.cargando());
		modeloArbol = new ModeloArbolConExpandibleMods(placeholder);
		arbolModulos.setModel(modeloArbol);

		this.setVisible(true);
		iniciarCargaPesada();
	}

	public boolean esNodoMetodo(DefaultMutableTreeNode nodo) {
		if (nodo == null)
			return false;

		Object uo = nodo.getUserObject();
		if (!(uo instanceof NodoConTexto))
			return false;

		Object payload = ((NodoConTexto) uo).objeto();
		if (!(payload instanceof Object[]))
			return false;

		Object[] datos = (Object[]) payload;

		return datos.length == 3 && datos[0] instanceof ArchivoDeMod && datos[1] instanceof String
				&& datos[2] instanceof ArchivoDeMod.InfoMetodo;
	}

	public void mostrarCodigoClaseSeleccionada() {
		TreePath rutaSeleccionada = arbolModulos.getSelectionPath();

		if (rutaSeleccionada == null) {
			return;
		}

		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) rutaSeleccionada.getLastPathComponent();

		if (nodo == null) {
			return;
		}

		Object objetoUsuario = nodo.getUserObject();

		if (!(objetoUsuario instanceof NodoConTexto)) {
			return;
		}

		Object objetoReal = ((NodoConTexto) objetoUsuario).objeto();

		if (!(objetoReal instanceof Object[])) {
			return;
		}

		Object[] datos = (Object[]) objetoReal;

		if (datos.length < 2) {
			return;
		}

		if (!(datos[0] instanceof ArchivoDeMod)) {
			return;
		}

		if (!(datos[1] instanceof String)) {
			return;
		}

		String nombreClase = (String) datos[1];
		abrirClaseEnCfr(nombreClase);
	}

	public void mostrarCodigoDescompilado(ArchivoDeMod mod, String nombreClase) {
		abrirClaseEnCfr(nombreClase);
	}

	public void abrirClaseEnCfr(String nombreClase) {
		if (nombreClase == null || nombreClase.trim().isEmpty()) {
			return;
		}

		String claseNormalizada = nombreClase.trim();

		if (claseNormalizada.endsWith(".class")) {
			claseNormalizada = claseNormalizada.substring(0, claseNormalizada.length() - ".class".length());
		}

		claseNormalizada = claseNormalizada.replace('/', '.');

		String url = "cfr://" + claseNormalizada;

		CrashDetectorLogger.log(url + " (cfr url)");

		CfrBase gui = TipoGUI.CFR.obtenerGUIPredeterminado(CfrSakuraRiddle.ID, CfrSakuraRiddle::new);
		gui.procesarHipervinculo(url);
	}

	/**
	 * Muestra los detalles del nodo seleccionado en el área de contenido.
	 * 
	 * Esta versión corrige: - el detalle del nodo "Información de Mixin" - el
	 * detalle de targets específicos del mixin - el detalle de métodos/campos mixin
	 * - el detalle de classrefs
	 */
	public void mostrarDetallesNodo(Object nodo) {
		if (nodo == null) {
			areaContenido.setText("");
			return;
		}

		StringBuilder detalles = new StringBuilder();
		Object objetoReal = nodo;

		if (nodo instanceof DefaultMutableTreeNode) {
			Object objetoUsuario = ((DefaultMutableTreeNode) nodo).getUserObject();
			if (objetoUsuario == null) {
				areaContenido.setText("");
				return;
			}

			if (objetoUsuario instanceof NodoConTexto) {
				objetoReal = ((NodoConTexto) objetoUsuario).objeto();
			} else {
				objetoReal = objetoUsuario;
			}
		}

		// =========================
		// Detalle de mod
		// =========================
		if (objetoReal instanceof ArchivoDeMod) {
			ArchivoDeMod mod = (ArchivoDeMod) objetoReal;

			detalles.append(MonitorDePID.idioma.detalleMod()).append("\n");
			detalles.append(MonitorDePID.idioma.ubicacion()).append(": ").append(mod.ubicacion_para_publicar())
					.append("\n\n");

			if (!mod.nombre().isEmpty()) {
				detalles.append(MonitorDePID.idioma.nombres()).append(":\n");
				for (String nombre : mod.nombre()) {
					detalles.append("- ").append(nombre).append("\n");
				}
				detalles.append("\n");
			}

			Map<String, List<String>> clasesPorPaquete = agruparClasesPorPaquete(mod.clases());
			for (Map.Entry<String, List<String>> entrada : clasesPorPaquete.entrySet()) {
				String paquete = entrada.getKey();
				List<String> clases = entrada.getValue();

				if (paquete.isEmpty()) {
					detalles.append(MonitorDePID.idioma.clases()).append(" (").append(clases.size()).append("):\n");
					for (String nombreClase : clases) {
						detalles.append("- ").append(nombreClase).append("\n");
					}
				} else {
					detalles.append(paquete).append(" (").append(clases.size()).append("):\n");
					for (String nombreClase : clases) {
						detalles.append("  - ").append(nombreClase).append("\n");
					}
				}
				detalles.append("\n");
			}

			areaContenido.setText(detalles.toString());
			return;
		}

		// =========================
		// Detalle de paquete
		// =========================
		if (objetoReal instanceof String) {
			String paquete = (String) objetoReal;

			detalles.append(MonitorDePID.idioma.paquete()).append(" ").append(paquete).append("\n");

			List<ArchivoDeMod> modsConPaquete = new ArrayList<>();
			for (ArchivoDeMod mod : Buscador.mods) {
				for (String clase : mod.clases()) {
					String paqueteClase = "";
					int indiceUltimoPunto = clase.lastIndexOf('.');
					if (indiceUltimoPunto > 0) {
						paqueteClase = clase.substring(0, indiceUltimoPunto);
					}
					if (paqueteClase.equals(paquete)) {
						modsConPaquete.add(mod);
						break;
					}
				}
			}

			detalles.append(MonitorDePID.idioma.modulos()).append(" (").append(modsConPaquete.size()).append("):\n");
			for (ArchivoDeMod mod : modsConPaquete) {
				detalles.append("- ").append(mod.ubicacion_para_publicar()).append("\n");
			}
			detalles.append("\n");

			detalles.append(MonitorDePID.idioma.clases()).append(":\n");
			for (ArchivoDeMod mod : modsConPaquete) {
				for (String clase : mod.clases()) {
					String paqueteClase = "";
					int indiceUltimoPunto = clase.lastIndexOf('.');
					if (indiceUltimoPunto > 0) {
						paqueteClase = clase.substring(0, indiceUltimoPunto);
					}
					if (paqueteClase.equals(paquete)) {
						String nombreClase = clase.substring(indiceUltimoPunto + 1);
						detalles.append("  - ").append(nombreClase).append("\n");
					}
				}
			}

			areaContenido.setText(detalles.toString());
			return;
		}

		// =========================
		// Detalles basados en Object[]
		// =========================
		if (objetoReal instanceof Object[]) {
			Object[] datos = (Object[]) objetoReal;

			if (datos.length >= 2 && datos[0] instanceof ArchivoDeMod) {
				ArchivoDeMod mod = (ArchivoDeMod) datos[0];
				String clase = (datos[1] instanceof String) ? (String) datos[1] : null;

				if (clase == null) {
					areaContenido.setText("");
					return;
				}

				String claseInterna = convertirFormatoClase(clase);
				boolean claseExiste = mod.existeClase(claseInterna);

				// 🔹 Información general del mixin
				if (datos.length >= 3 && "mixin_info".equals(datos[2])) {
					ArchivoDeMod.MixinInfo mixinInfo = mod.obtenerInfoMixin(claseInterna);

					if (mixinInfo != null) {
						detalles.append("Mixin\n");
						detalles.append("Clase: ").append(clase).append("\n");
						detalles.append("Jar: ").append(mixinInfo.obtenerJarOrigen()).append("\n\n");

						detalles.append("Targets (").append(mixinInfo.obtenerTargets().size()).append("):\n");
						for (String target : mixinInfo.obtenerTargets()) {
							detalles.append("- ").append(target).append("\n");
						}
						detalles.append("\n");

						detalles.append("Métodos mixin (").append(mixinInfo.obtenerMetodosMixin().size())
								.append("):\n");
						for (ArchivoDeMod.MixinMetodoInfo mm : mixinInfo.obtenerMetodosMixin()) {
							detalles.append("- ").append(mm.obtenerNombre()).append(mm.obtenerDescriptor())
									.append(mm.esOverwrite() ? " [@Overwrite]" : " [@Inject]").append("\n");
							if (!mm.obtenerTargets().isEmpty()) {
								detalles.append("  Targets: ").append(String.join(", ", mm.obtenerTargets()))
										.append("\n");
							}
						}
						detalles.append("\n");

						detalles.append("Campos mixin (").append(mixinInfo.obtenerCamposMixin().size()).append("):\n");
						for (ArchivoDeMod.MixinCampoInfo cm : mixinInfo.obtenerCamposMixin()) {
							detalles.append("- ").append(cm.obtenerNombre()).append(" ").append(cm.obtenerDescriptor())
									.append(" [@Shadow]\n");
						}
					}

					areaContenido.setText(detalles.toString());
					return;
				}

				// 🔹 Target puntual del mixin
				if (datos.length >= 4 && "target".equals(datos[2]) && datos[3] instanceof String) {
					String target = (String) datos[3];

					detalles.append("Target de mixin\n");
					detalles.append("Mixin: ").append(clase).append("\n");
					detalles.append("Clase objetivo: ").append(target).append("\n");

					areaContenido.setText(detalles.toString());
					return;
				}

				// 🔹 Método de mixin
				if (datos.length >= 4 && "mixin_metodo".equals(datos[3])
						&& datos[2] instanceof ArchivoDeMod.MixinMetodoInfo) {
					ArchivoDeMod.MixinMetodoInfo mm = (ArchivoDeMod.MixinMetodoInfo) datos[2];

					detalles.append("Método de mixin\n");
					detalles.append("Mixin: ").append(clase).append("\n");
					detalles.append("Nombre: ").append(mm.obtenerNombre()).append("\n");
					detalles.append("Descriptor: ").append(mm.obtenerDescriptor()).append("\n");
					detalles.append("Tipo: ").append(mm.esOverwrite() ? "@Overwrite" : "@Inject").append("\n");

					if (!mm.obtenerTargets().isEmpty()) {
						detalles.append("Targets: ").append(String.join(", ", mm.obtenerTargets())).append("\n");
					}

					areaContenido.setText(detalles.toString());
					return;
				}

				// 🔹 Campo de mixin
				if (datos.length >= 4 && "mixin_campo".equals(datos[3])
						&& datos[2] instanceof ArchivoDeMod.MixinCampoInfo) {
					ArchivoDeMod.MixinCampoInfo cm = (ArchivoDeMod.MixinCampoInfo) datos[2];

					detalles.append("Campo de mixin\n");
					detalles.append("Mixin: ").append(clase).append("\n");
					detalles.append("Nombre: ").append(cm.obtenerNombre()).append("\n");
					detalles.append("Descriptor: ").append(cm.obtenerDescriptor()).append("\n");
					detalles.append("Tipo: @Shadow\n");

					areaContenido.setText(detalles.toString());
					return;
				}

				// 🔹 Detalle de clase
				if (datos.length == 2) {
					String nombreClase = clase;
					String paquete = "";
					int indicePunto = clase.lastIndexOf('.');
					if (indicePunto > 0) {
						paquete = clase.substring(0, indicePunto);
						nombreClase = clase.substring(indicePunto + 1);
					}

					detalles.append(MonitorDePID.idioma.detalleClase()).append(" ").append(nombreClase).append("\n");
					if (!paquete.isEmpty()) {
						detalles.append(MonitorDePID.idioma.paquete()).append(": ").append(paquete).append("\n\n");
					}
					detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar())
							.append("\n\n");

					if (Buscador.puedeAnalizarElContentidoDeClase() && claseExiste) {
						List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseInterna);
						detalles.append(MonitorDePID.idioma.metodos()).append(" (").append(metodos.size())
								.append("):\n");
						for (ArchivoDeMod.InfoMetodo metodo : metodos) {
							detalles.append("- ").append(metodo.obtenerNombre()).append(metodo.obtenerDescriptor())
									.append("\n");
						}
						detalles.append("\n");

						List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(claseInterna);
						detalles.append(MonitorDePID.idioma.campos()).append(" (").append(campos.size()).append("):\n");
						for (ArchivoDeMod.InfoCampo campo : campos) {
							detalles.append("- ").append(campo.obtenerNombre()).append(" ")
									.append(campo.obtenerDescriptor()).append("\n");
						}
					}

					areaContenido.setText(detalles.toString());
					return;
				}

				// 🔹 Detalle de método
				if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoMetodo) {
					ArchivoDeMod.InfoMetodo metodo = (ArchivoDeMod.InfoMetodo) datos[2];

					// Caso especial: classref
					if (datos.length >= 5 && "classref".equals(datos[4])
							&& datos[3] instanceof ArchivoDeMod.Referencia) {
						ArchivoDeMod.Referencia referencia = (ArchivoDeMod.Referencia) datos[3];

						detalles.append("Referencia de clase\n");
						detalles.append("Clase objetivo: ")
								.append(convertirFormatoClasePuntos(referencia.obtenerClase())).append("\n");
						detalles.append("Desde método: ").append(metodo.obtenerNombre())
								.append(metodo.obtenerDescriptor()).append("\n");
						detalles.append("Clase origen: ").append(clase).append("\n");
						detalles.append("Módulo: ").append(mod.ubicacion_para_publicar()).append("\n");

						areaContenido.setText(detalles.toString());
						return;
					}

					// Caso normal: referencia específica a método/campo
					// Caso normal: referencia específica a método/campo
					if (datos.length >= 4 && datos[3] instanceof ArchivoDeMod.Referencia) {
						ArchivoDeMod.Referencia referencia = (ArchivoDeMod.Referencia) datos[3];

						String tipoReferencia = referencia.esMetodo() ? MonitorDePID.idioma.metodo()
								: MonitorDePID.idioma.campo();

						String claseReferencia = convertirFormatoClasePuntos(referencia.obtenerClase());

						detalles.append("Referencia ").append(tipoReferencia).append("\n");
						detalles.append(MonitorDePID.idioma.nombres()).append(": ").append(referencia.obtenerNombre())
								.append("\n");
						detalles.append("Descriptor: ").append(referencia.obtenerDescriptor()).append("\n");
						detalles.append(MonitorDePID.idioma.clase()).append(": ").append(claseReferencia)
								.append("\n\n");

						detalles.append("Destino:\n");
						detalles.append("- ").append(claseReferencia).append(".").append(referencia.obtenerNombre());

						if (referencia.esMetodo()) {
							detalles.append(referencia.obtenerDescriptor());
						} else {
							detalles.append(" ").append(referencia.obtenerDescriptor());
						}
						detalles.append("\n\n");

						detalles.append("Origen:\n");

						if (referencia.tieneOrigen()) {
							detalles.append(MonitorDePID.idioma.metodo()).append(": ")
									.append(convertirFormatoClasePuntos(referencia.obtenerClaseOrigen())).append(".")
									.append(referencia.obtenerMetodoOrigen())
									.append(referencia.obtenerDescriptorOrigen()).append("\n");
						} else {
							detalles.append(MonitorDePID.idioma.metodo()).append(": ").append(clase).append(".")
									.append(metodo.obtenerNombre()).append(metodo.obtenerDescriptor()).append("\n");
						}

						detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar())
								.append("\n");

						areaContenido.setText(detalles.toString());
						return;
					}
					// Caso normal: detalle de método
					String nombreClase = clase;
					int indicePunto = nombreClase.lastIndexOf('.');
					if (indicePunto > 0) {
						nombreClase = nombreClase.substring(indicePunto + 1);
					}

					detalles.append(MonitorDePID.idioma.detalleMetodo()).append(" ").append(metodo.obtenerNombre())
							.append(metodo.obtenerDescriptor()).append("\n");
					detalles.append(MonitorDePID.idioma.clase()).append(": ").append(nombreClase).append("\n");
					detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar())
							.append("\n\n");

					detalles.append(MonitorDePID.idioma.referenciasAMetodos()).append(" (")
							.append(metodo.obtenerReferenciasAMetodos().size()).append("):\n");
					for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasAMetodos()) {
						String nombreClaseRef = convertirFormatoClasePuntos(ref.obtenerClase());
						detalles.append("- ").append(nombreClaseRef).append(".").append(ref.obtenerNombre())
								.append(ref.obtenerDescriptor()).append("\n");
					}
					detalles.append("\n");

					detalles.append(MonitorDePID.idioma.referenciasACampos()).append(" (")
							.append(metodo.obtenerReferenciasACampos().size()).append("):\n");
					for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
						String nombreClaseRef = convertirFormatoClasePuntos(ref.obtenerClase());
						detalles.append("- ").append(nombreClaseRef).append(".").append(ref.obtenerNombre()).append(" ")
								.append(ref.obtenerDescriptor()).append("\n");
					}
					detalles.append("\n");

					java.util.LinkedHashSet<String> clasesReferenciadas = new java.util.LinkedHashSet<>();
					for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasAMetodos()) {
						if (ref.obtenerClase() != null && !ref.obtenerClase().isEmpty()) {
							clasesReferenciadas.add(convertirFormatoClasePuntos(ref.obtenerClase()));
						}
					}
					for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
						if (ref.obtenerClase() != null && !ref.obtenerClase().isEmpty()) {
							clasesReferenciadas.add(convertirFormatoClasePuntos(ref.obtenerClase()));
						}
					}

					detalles.append("Referencias de clase (").append(clasesReferenciadas.size()).append("):\n");
					for (String claseRef : clasesReferenciadas) {
						detalles.append("- ").append(claseRef).append("\n");
					}
					detalles.append("\n");

//					List<ArchivoDeMod.Constante> constantes = mod.buscarConstantesEnMetodo(convertirFormatoClase(clase),
//							metodo.obtenerNombre(), metodo.obtenerDescriptor());
//					detalles.append("Constantes (").append(constantes.size()).append("):\n");
//					for (ArchivoDeMod.Constante k : constantes) {
//						detalles.append("- ").append(formatearConstante(k)).append("\n");
//					}

					detalles.append("Constantes: expandir/seleccionar el método para cargarlas.\n");

					areaContenido.setText(detalles.toString());
					return;
				}

				// 🔹 Detalle de campo
				if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoCampo) {
					ArchivoDeMod.InfoCampo campo = (ArchivoDeMod.InfoCampo) datos[2];
					String nombreClase = clase;
					int indicePunto = nombreClase.lastIndexOf('.');
					if (indicePunto > 0) {
						nombreClase = nombreClase.substring(indicePunto + 1);
					}

					detalles.append(MonitorDePID.idioma.detalleCampo()).append(" ").append(campo.obtenerNombre())
							.append("\n");
					detalles.append(MonitorDePID.idioma.clase()).append(": ").append(nombreClase).append("\n");
					detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar())
							.append("\n");
					detalles.append(MonitorDePID.idioma.tipo()).append(": ").append(campo.obtenerDescriptor())
							.append("\n");

					areaContenido.setText(detalles.toString());
					return;
				}
			}
		}

		areaContenido.setText(detalles.toString());
	}

	/**
	 * Convierte un nombre de clase del formato de puntos (java.lang.Object) al
	 * formato interno de ASM (java/lang/Object)
	 */
	public String convertirFormatoClase(String nombreClase) {
		// Si ya tiene '/', asumimos que está en formato interno
		if (nombreClase.contains("/")) {
			return nombreClase;
		}
		// Si tiene '.', convertimos los puntos a barras
		else if (nombreClase.contains(".")) {
			return nombreClase.replace('.', '/');
		}
		// Si no tiene ni '.' ni '/', es una clase en el paquete por defecto
		else {
			return nombreClase;
		}
	}

	/**
	 * Convierte un nombre de clase del formato interno de ASM (java/lang/Object) al
	 * formato de puntos (java.lang.Object) para mostrar en la interfaz
	 */
	public String convertirFormatoClasePuntos(String nombreClase) {
		// Si ya tiene '.', asumimos que está en formato de puntos
		if (nombreClase.contains(".")) {
			return nombreClase;
		}
		// Si tiene '/', convertimos las barras a puntos
		else if (nombreClase.contains("/")) {
			return nombreClase.replace('/', '.');
		}
		// Si no tiene ni '.' ni '/', es una clase en el paquete por defecto
		else {
			return nombreClase;
		}
	}

	public void buscarEnIndiceAsync() {
		if (workerBuscar != null && !workerBuscar.isDone()) {
			workerBuscar.cancel(true);
		}

		final String filtro = campoBuscar.getText().trim();
		if (filtro.isEmpty()) {
			construirArbolInicialAsync();
			return;
		}

		final String filtroLower = filtro.toLowerCase();
		final String tipoFiltroUI = (String) comboFiltro.getSelectedItem();
		final String tipoFiltro = normalizarTipoFiltroUI(tipoFiltroUI);

		setCargando(true);

		workerBuscar = new SwingWorker<DefaultMutableTreeNode, Void>() {
			@Override
			protected DefaultMutableTreeNode doInBackground() {
				try {
					List<PathDescriptor> coincidencias = new ArrayList<>();

					coincidencias.addAll(buscarEnIndiceCompacto(filtroLower, tipoFiltro));

					if ("*".equals(tipoFiltro) || "METODO".equals(tipoFiltro) || "CAMPO".equals(tipoFiltro)
							|| "CONSTANTE".equals(tipoFiltro) || tipoFiltro.startsWith("REFERENCIA")) {

						coincidencias.addAll(buscarProfundoComoLista(filtroLower, tipoFiltro));
					}

					coincidencias = deduplicar(coincidencias);

					return construirArbolResultadosJerarquico(coincidencias);

				} catch (CancellationException ce) {
					return new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronResultados());
				} catch (Throwable t) {
					com.asbestosstar.crashdetector.CrashDetectorLogger.logException(t);
					DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.resultadosBusqueda());
					raiz.add(new DefaultMutableTreeNode("Error: " + t.getMessage()));
					return raiz;
				}
			}

			public List<PathDescriptor> buscarProfundoComoLista(String filtroLower, String tipoFiltro) {
				if (!Buscador.puedeAnalizarElContentidoDeClase() || !ArchivoDeMod.esAnalisisDeBytecodeDisponible()) {
					return new ArrayList<>();
				}

				final boolean incluirTodo = "*".equals(tipoFiltro);

				int numHilos = Math.max(1, Runtime.getRuntime().availableProcessors());
				ExecutorService exec = Executors.newFixedThreadPool(numHilos, r -> {
					Thread t = new Thread(r, "BusquedaProfunda-" + r.hashCode());
					t.setDaemon(true);
					return t;
				});

				java.util.concurrent.ConcurrentLinkedQueue<PathDescriptor> resultados = new java.util.concurrent.ConcurrentLinkedQueue<>();
				List<Callable<Void>> tareas = new ArrayList<>();
				List<ArchivoDeMod> mods = obtenerTodosLosMods();
				CrashDetectorLogger.log("🔎 Busqueda profunda: mods=" + mods.size());

				for (ArchivoDeMod mod : obtenerTodosLosMods()) {
					for (String clasePuntos : mod.clases()) {
						final ArchivoDeMod modFinal = mod;
						final String claseFinalPuntos = clasePuntos;

						tareas.add(() -> {
							if (workerBuscar != null && workerBuscar.isCancelled())
								return null;

							// 🔥 CAMBIO CLAVE: normalizar a interno SIEMPRE
							String claseInterna = normalizarNombreClaseInterno(claseFinalPuntos);
							if (!modFinal.existeClase(claseInterna))
								return null;

							String paquete = paqueteDe(claseFinalPuntos);

							if (incluirTodo || "METODO".equals(tipoFiltro)) {
								for (ArchivoDeMod.InfoMetodo m : modFinal.obtenerMetodosConReferencias(claseInterna)) {
									String firma = (m.obtenerNombre() + " " + m.obtenerDescriptor()).toLowerCase();
									if (firma.contains(filtroLower)) {
										resultados.add(new PathDescriptor(modFinal.ubicacion_para_publicar(), paquete,
												claseFinalPuntos, m.obtenerNombre(), m.obtenerDescriptor(), "METODO"));
									}
								}
							}

							if (incluirTodo || "CAMPO".equals(tipoFiltro)) {
								for (ArchivoDeMod.InfoCampo f : modFinal.obtenerCampos(claseInterna)) {
									String firma = (f.obtenerNombre() + " " + f.obtenerDescriptor()).toLowerCase();
									if (firma.contains(filtroLower)) {
										resultados.add(new PathDescriptor(modFinal.ubicacion_para_publicar(), paquete,
												claseFinalPuntos, f.obtenerNombre(), f.obtenerDescriptor(), "CAMPO"));
									}
								}
							}

							if (incluirTodo || "CONSTANTE".equals(tipoFiltro)) {
								for (ArchivoDeMod.InfoMetodo m : modFinal.obtenerMetodosConReferencias(claseInterna)) {
									for (ArchivoDeMod.Constante c : modFinal.buscarConstantesEnMetodo(claseInterna,
											m.obtenerNombre(), m.obtenerDescriptor())) {
										String val = String.valueOf(c.obtenerValor()).toLowerCase();
										if (val.contains(filtroLower)) {
											resultados.add(new PathDescriptor(modFinal.ubicacion_para_publicar(),
													paquete, claseFinalPuntos, m.obtenerNombre(),
													String.valueOf(c.obtenerValor()), "CONSTANTE"));
										}
									}
								}
							}

							if (incluirTodo || tipoFiltro.startsWith("REFERENCIA")) {
								for (ArchivoDeMod.InfoMetodo m : modFinal.obtenerMetodosConReferencias(claseInterna)) {

									if (incluirTodo || "REFERENCIA_METODO".equals(tipoFiltro)
											|| "REFERENCIA".equals(tipoFiltro)) {
										for (ArchivoDeMod.Referencia ref : m.obtenerReferenciasAMetodos()) {
											String s = (Buscador.convertirFormatoClasePuntos(ref.obtenerClase()) + "."
													+ ref.obtenerNombre() + ref.obtenerDescriptor()).toLowerCase();
											if (s.contains(filtroLower)) {
												resultados.add(new PathDescriptor(modFinal.ubicacion_para_publicar(),
														paquete, claseFinalPuntos, m.obtenerNombre(),
														m.obtenerDescriptor(), "REFERENCIA_METODO"));
												break;
											}
										}
									}

									if (incluirTodo || "REFERENCIA_CAMPO".equals(tipoFiltro)
											|| "REFERENCIA".equals(tipoFiltro)) {
										for (ArchivoDeMod.Referencia ref : m.obtenerReferenciasACampos()) {
											String s = (Buscador.convertirFormatoClasePuntos(ref.obtenerClase()) + "."
													+ ref.obtenerNombre() + " " + ref.obtenerDescriptor())
													.toLowerCase();
											if (s.contains(filtroLower)) {
												resultados.add(new PathDescriptor(modFinal.ubicacion_para_publicar(),
														paquete, claseFinalPuntos, m.obtenerNombre(),
														m.obtenerDescriptor(), "REFERENCIA_CAMPO"));
												break;
											}
										}
									}

									if (incluirTodo || "REFERENCIA_CLASE".equals(tipoFiltro)) {
										boolean hit = false;
										for (ArchivoDeMod.Referencia ref : m.obtenerReferenciasAMetodos()) {
											if (Buscador.convertirFormatoClasePuntos(ref.obtenerClase()).toLowerCase()
													.contains(filtroLower)) {
												hit = true;
												break;
											}
										}
										if (!hit)
											for (ArchivoDeMod.Referencia ref : m.obtenerReferenciasACampos()) {
												if (Buscador.convertirFormatoClasePuntos(ref.obtenerClase())
														.toLowerCase().contains(filtroLower)) {
													hit = true;
													break;
												}
											}
										if (hit) {
											resultados.add(new PathDescriptor(modFinal.ubicacion_para_publicar(),
													paquete, claseFinalPuntos, m.obtenerNombre(), m.obtenerDescriptor(),
													"REFERENCIA_CLASE"));
										}
									}
								}
							}

							return null;
						});
					}
				}

				try {
					exec.invokeAll(tareas);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} finally {
					exec.shutdownNow();
				}

				return new ArrayList<>(resultados);
			}

			public List<PathDescriptor> deduplicar(List<PathDescriptor> in) {
				if (in == null || in.isEmpty())
					return in;
				java.util.LinkedHashSet<PathDescriptor> set = new java.util.LinkedHashSet<>(in);
				return new ArrayList<>(set);
			}

			@Override
			protected void done() {
				try {
					if (isCancelled()) {
						setCargando(false);
						return;
					}
					DefaultMutableTreeNode raiz = get();
					modeloArbol = new ModeloArbolConExpandibleMods(raiz);
					arbolModulos.setModel(modeloArbol);
				} catch (Throwable t) {
					com.asbestosstar.crashdetector.CrashDetectorLogger.logException(t);
				} finally {
					setCargando(false);
				}
			}
		};

		workerBuscar.execute();
	}

	/**
	 * Verifica si una clase es mixin y obtiene su información de forma segura.
	 * 
	 * @param mod         El archivo de mod a consultar
	 * @param nombreClase Nombre de la clase en formato puntos o interno
	 * @return MixinInfo si es mixin, null en caso contrario
	 */
	public ArchivoDeMod.MixinInfo obtenerInfoMixinSeguro(ArchivoDeMod mod, String nombreClase) {
		try {
			String claseInterna = normalizarNombreClaseInterno(nombreClase);
			if (mod.esClaseMixin(claseInterna)) {
				return mod.obtenerInfoMixin(claseInterna);
			}
		} catch (Throwable t) {
			CrashDetectorLogger.log("Error al obtener info de mixin: " + t.getMessage());
		}
		return null;
	}

	/**
	 * Agrupa clases mixin por sus targets para búsqueda eficiente.
	 * 
	 * @param mod El mod a analizar
	 * @return Mapa de target -> lista de clases mixin que lo modifican
	 */
	public Map<String, List<String>> agruparMixinsPorTarget(ArchivoDeMod mod) {
		Map<String, List<String>> resultado = new HashMap<>();
		for (String clase : mod.obtenerTodosLosNombresDeClases()) {
			ArchivoDeMod.MixinInfo info = obtenerInfoMixinSeguro(mod, clase);
			if (info != null) {
				for (String target : info.obtenerTargets()) {
					resultado.computeIfAbsent(target, k -> new ArrayList<>()).add(clase);
				}
			}
		}
		return resultado;
	}

	public List<PathDescriptor> buscarEnIndiceCompacto(String filtroLower, String tipoFiltro) {
		java.util.Set<String> claves = extraerClavesConsulta(filtroLower);

		java.util.HashMap<PathDescriptor, Integer> puntuacion = new java.util.HashMap<>();
		for (String clave : claves) {
			List<PathDescriptor> lista = indiceBusqueda.get(clave);
			if (lista == null)
				continue;
			for (PathDescriptor desc : lista) {
				if (!debeIncluirsePorTipoFiltro(desc, tipoFiltro))
					continue;

				String texto = textoBuscableDe(desc);
				if (texto == null)
					continue;
				if (!texto.contains(filtroLower))
					continue;

				puntuacion.merge(desc, 1, Integer::sum);
			}
		}

		List<PathDescriptor> coincidencias = new ArrayList<>(puntuacion.keySet());
		coincidencias.sort((a, b) -> Integer.compare(puntuacion.getOrDefault(b, 0), puntuacion.getOrDefault(a, 0)));

		final int LIMITE_RESULTADOS = 5000;
		if (coincidencias.size() > LIMITE_RESULTADOS) {
			coincidencias = coincidencias.subList(0, LIMITE_RESULTADOS);
		}
		return coincidencias;
	}

	public DefaultMutableTreeNode construirArbolResultadosJerarquico(List<PathDescriptor> coincidencias) {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.resultadosBusqueda());
		if (coincidencias == null || coincidencias.isEmpty()) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronResultados()));
			return raiz;
		}

		Map<String, ArchivoDeMod> modsPorUbicacion = obtenerMapaModsPorUbicacion();

		Map<String, DefaultMutableTreeNode> nodosMod = new HashMap<>();
		for (PathDescriptor d : coincidencias) {
			String ub = d.obtenerModUbicacion();
			ArchivoDeMod modReal = modsPorUbicacion.get(ub);
			DefaultMutableTreeNode nodoMod = nodosMod.computeIfAbsent(ub, k -> new DefaultMutableTreeNode(
					new NodoConTexto(k, modReal != null ? modReal : new ArchivoDeModFake(k))));

			String tipo = d.obtenerTipo();
			if ("MOD".equals(tipo)) {
				continue;
			}

			String paquete = d.obtenerPaquete() == null ? "" : d.obtenerPaquete();
			DefaultMutableTreeNode nodoPaquete = asegurarNodoPaquete(nodoMod, paquete);

			if ("PAQUETE".equals(tipo)) {
				continue;
			}

			String clase = d.obtenerClase();
			if (clase == null)
				continue;

			DefaultMutableTreeNode nodoClase = asegurarNodoClase(nodoPaquete, modReal, clase);

			if ("CLASE".equals(tipo)) {
				continue;
			}

			if ("METODO".equals(tipo)) {
				DefaultMutableTreeNode cont = asegurarNodoContenedor(nodoClase, "contenedor_metodos",
						MonitorDePID.idioma.metodos());
				String nombre = d.obtenerMetodo();
				String desc = d.obtenerDescriptor();
				String texto = nombre + (desc != null ? desc : "");
				ArchivoDeMod.InfoMetodo info = resolverInfoMetodoReal(modReal, clase, nombre, desc);
				if (info == null) {
					info = (ArchivoDeMod.InfoMetodo) crearPlaceholderInfo(nombre, desc, "METODO");
				}
				Object[] datos = new Object[] { modReal, clase, info };
				cont.add(new DefaultMutableTreeNode(new NodoConTexto(texto, datos)));
			} else if (tipo != null && tipo.startsWith("REFERENCIA")) {
				DefaultMutableTreeNode cont = asegurarNodoContenedor(nodoClase, "contenedor_referencias",
						MonitorDePID.idioma.referencias());
				String nombre = d.obtenerMetodo();
				String desc = d.obtenerDescriptor();
				String texto = nombre + (desc != null ? desc : "");
				ArchivoDeMod.InfoMetodo info = resolverInfoMetodoReal(modReal, clase, nombre, desc);
				if (info == null) {
					info = (ArchivoDeMod.InfoMetodo) crearPlaceholderInfo(nombre, desc, "METODO");
				}
				Object[] datos = new Object[] { modReal, clase, info };
				cont.add(new DefaultMutableTreeNode(new NodoConTexto(texto, datos)));
			} else if ("CAMPO".equals(tipo)) {
				DefaultMutableTreeNode cont = asegurarNodoContenedor(nodoClase, "contenedor_campos",
						MonitorDePID.idioma.campos());
				String nombre = d.obtenerMetodo();
				String desc = d.obtenerDescriptor();
				String texto = nombre + (desc != null ? desc : "");
				ArchivoDeMod.InfoCampo info = resolverInfoCampoReal(modReal, clase, nombre, desc);
				if (info == null) {
					info = (ArchivoDeMod.InfoCampo) crearPlaceholderInfo(nombre, desc, "CAMPO");
				}
				Object[] datos = new Object[] { modReal, clase, info };
				cont.add(new DefaultMutableTreeNode(new NodoConTexto(texto, datos)));
			} else if ("CONSTANTE".equals(tipo)) {
				DefaultMutableTreeNode cont = asegurarNodoContenedor(nodoClase, "contenedor_constantes", "CONSTANTE");
				String valor = d.obtenerDescriptor();
				String texto = String.valueOf(valor);
				Object[] datos = new Object[] { modReal, clase, crearPlaceholderInfo("const", valor, "CONSTANTE") };
				cont.add(new DefaultMutableTreeNode(new NodoConTexto(texto, datos)));
			}
		}

		for (DefaultMutableTreeNode nm : nodosMod.values()) {
			if (nm.getChildCount() > 0) {
				raiz.add(nm);
			}
		}

		if (raiz.getChildCount() == 0) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronResultados()));
		}
		return raiz;
	}

	public DefaultMutableTreeNode asegurarNodoPaquete(DefaultMutableTreeNode nodoMod, String paquete) {
		String etiqueta = (paquete == null || paquete.isEmpty()) ? "(paquete por defecto)" : paquete;
		for (int i = 0; i < nodoMod.getChildCount(); i++) {
			DefaultMutableTreeNode h = (DefaultMutableTreeNode) nodoMod.getChildAt(i);
			Object uo = h.getUserObject();
			if (uo instanceof NodoConTexto) {
				Object obj = ((NodoConTexto) uo).objeto();
				if (obj instanceof String && ((String) obj).equals(paquete == null ? "" : paquete)) {
					return h;
				}
			}
		}
		DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(
				new NodoConTexto(etiqueta, paquete == null ? "" : paquete));
		nodoMod.add(nuevo);
		return nuevo;
	}

	public String textoBuscableDe(PathDescriptor d) {
		if (d == null)
			return null;
		String tipo = d.obtenerTipo();
		if ("MOD".equals(tipo))
			return (d.obtenerModUbicacion() == null ? "" : d.obtenerModUbicacion()).toLowerCase();
		if ("PAQUETE".equals(tipo))
			return (d.obtenerPaquete() == null ? "" : d.obtenerPaquete()).toLowerCase();
		if ("CLASE".equals(tipo))
			return (d.obtenerClase() == null ? "" : d.obtenerClase()).toLowerCase();
		if ("METODO".equals(tipo) || "CAMPO".equals(tipo)) {
			String n = d.obtenerMetodo() == null ? "" : d.obtenerMetodo();
			String desc = d.obtenerDescriptor() == null ? "" : d.obtenerDescriptor();
			return (n + " " + desc + " " + (d.obtenerClase() == null ? "" : d.obtenerClase())).toLowerCase();
		}
		if ("CONSTANTE".equals(tipo))
			return (d.obtenerDescriptor() == null ? "" : d.obtenerDescriptor()).toLowerCase();
		return null;
	}

	public Map<String, ArchivoDeMod> obtenerMapaModsPorUbicacion() {
		Map<String, ArchivoDeMod> mapa = new HashMap<>();
		for (ArchivoDeMod m : Buscador.mods) {
			rellenarMapaModsRecursivo(m, mapa);
		}
		return mapa;
	}

	public void rellenarMapaModsRecursivo(ArchivoDeMod mod, Map<String, ArchivoDeMod> mapa) {
		if (mod == null)
			return;
		mapa.put(mod.ubicacion_para_publicar(), mod);
		for (ArchivoDeMod sub : mod.mods_en_mods()) {
			rellenarMapaModsRecursivo(sub, mapa);
		}
	}

	/**
	 * Carga de forma asíncrona el contenido de un mod dentro del árbol.
	 * 
	 * Esta versión: - sigue mostrando paquetes, clases, métodos y campos - añade un
	 * bloque completo de información de mixin - añade referencias de clase
	 * ("classrefs") además de referencias a métodos y campos
	 */
	public void cargarContenidoModuloAsync(DefaultMutableTreeNode nodoMod, ArchivoDeMod mod) {
		setCargando(true);

		new SwingWorker<List<DefaultMutableTreeNode>, Void>() {
			@Override
			protected List<DefaultMutableTreeNode> doInBackground() {
				List<DefaultMutableTreeNode> hijos = new ArrayList<>();

				// 🔹 Primero, submods
				for (ArchivoDeMod submod : mod.mods_en_mods()) {
					hijos.add(new DefaultMutableTreeNode(
							new NodoConTexto("📦 " + submod.ubicacion_para_publicar(), submod)));
				}

				// 🔹 Luego, clases agrupadas por paquete
				if (!mod.clases().isEmpty()) {
					Map<String, List<String>> clasesPorPaquete = agruparClasesPorPaquete(mod.clases());

					for (Map.Entry<String, List<String>> entrada : clasesPorPaquete.entrySet()) {
						String paquete = entrada.getKey();
						List<String> clasesEnPaquete = entrada.getValue();

						DefaultMutableTreeNode nodoPaquete;
						if (paquete.isEmpty()) {
							nodoPaquete = new DefaultMutableTreeNode(new NodoConTexto(
									"(paquete por defecto) (" + clasesEnPaquete.size() + " clases)", paquete));
						} else {
							nodoPaquete = new DefaultMutableTreeNode(
									new NodoConTexto(paquete + " (" + clasesEnPaquete.size() + " clases)", paquete));
						}

						for (String nombreClase : clasesEnPaquete) {
							String clasePuntos = paquete.isEmpty() ? nombreClase : paquete + "." + nombreClase;
							String claseInterna = normalizarNombreClaseInterno(clasePuntos);

							boolean esMixin = claseInterna != null && mod.esClaseMixin(claseInterna);
							String etiquetaClase = esMixin ? "✨ " + nombreClase + " [Mixin]" : nombreClase;

							DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
									new NodoConTexto(etiquetaClase, new Object[] { mod, clasePuntos }));

							// 🔹 Bloque de información de mixin
							if (esMixin) {
								ArchivoDeMod.MixinInfo mixinInfo = mod.obtenerInfoMixin(claseInterna);
								if (mixinInfo != null) {
									DefaultMutableTreeNode nodoInfoMixin = new DefaultMutableTreeNode(
											new NodoConTexto("🔗 Información de Mixin",
													new Object[] { mod, clasePuntos, "mixin_info" }));

									// Targets
									if (!mixinInfo.obtenerTargets().isEmpty()) {
										DefaultMutableTreeNode nodoTargets = new DefaultMutableTreeNode(
												new NodoConTexto("Targets: " + mixinInfo.obtenerTargets().size(),
														"mixin_targets"));

										for (String target : mixinInfo.obtenerTargets()) {
											nodoTargets.add(new DefaultMutableTreeNode(new NodoConTexto("→ " + target,
													new Object[] { mod, clasePuntos, "target", target })));
										}

										nodoInfoMixin.add(nodoTargets);
									}

									// Métodos mixin
									if (!mixinInfo.obtenerMetodosMixin().isEmpty()) {
										DefaultMutableTreeNode nodoMetodosMixin = new DefaultMutableTreeNode(
												new NodoConTexto(
														"Métodos Mixin: " + mixinInfo.obtenerMetodosMixin().size(),
														"mixin_metodos"));

										for (ArchivoDeMod.MixinMetodoInfo mm : mixinInfo.obtenerMetodosMixin()) {
											String tipo = mm.esOverwrite() ? "[@Overwrite]" : "[@Inject]";
											nodoMetodosMixin.add(new DefaultMutableTreeNode(new NodoConTexto(
													mm.obtenerNombre() + mm.obtenerDescriptor() + " " + tipo,
													new Object[] { mod, clasePuntos, mm, "mixin_metodo" })));
										}

										nodoInfoMixin.add(nodoMetodosMixin);
									}

									// Campos mixin
									if (!mixinInfo.obtenerCamposMixin().isEmpty()) {
										DefaultMutableTreeNode nodoCamposMixin = new DefaultMutableTreeNode(
												new NodoConTexto(
														"Campos Mixin: " + mixinInfo.obtenerCamposMixin().size(),
														"mixin_campos"));

										for (ArchivoDeMod.MixinCampoInfo cm : mixinInfo.obtenerCamposMixin()) {
											nodoCamposMixin.add(new DefaultMutableTreeNode(new NodoConTexto(
													cm.obtenerNombre() + " " + cm.obtenerDescriptor() + " [@Shadow]",
													new Object[] { mod, clasePuntos, cm, "mixin_campo" })));
										}

										nodoInfoMixin.add(nodoCamposMixin);
									}

									nodoClase.add(nodoInfoMixin);
								}
							}

							// 🔹 Si no se puede analizar el contenido, dejar solo la clase
							if (!Buscador.puedeAnalizarElContentidoDeClase() || claseInterna == null
									|| !mod.existeClase(claseInterna)) {
								nodoPaquete.add(nodoClase);
								continue;
							}

							// 🔹 Métodos
							List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseInterna);
							if (!metodos.isEmpty()) {
								DefaultMutableTreeNode nodoMetodos = new DefaultMutableTreeNode(
										new NodoConTexto(MonitorDePID.idioma.metodos() + " (" + metodos.size() + ")",
												"contenedor_metodos"));

								for (ArchivoDeMod.InfoMetodo metodo : metodos) {
									DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(
											new NodoConTexto(metodo.obtenerNombre() + metodo.obtenerDescriptor(),
													new Object[] { mod, clasePuntos, metodo }));

									// Referencias del método
									boolean tieneRefMetodos = !metodo.obtenerReferenciasAMetodos().isEmpty();
									boolean tieneRefCampos = !metodo.obtenerReferenciasACampos().isEmpty();

									if (tieneRefMetodos || tieneRefCampos) {
										DefaultMutableTreeNode nodoReferencias = new DefaultMutableTreeNode(
												new NodoConTexto(MonitorDePID.idioma.referencias() + " ("
														+ (metodo.obtenerReferenciasAMetodos().size()
																+ metodo.obtenerReferenciasACampos().size())
														+ ")", "contenedor_referencias"));

										// Referencias a métodos
										for (ArchivoDeMod.Referencia refMetodo : metodo.obtenerReferenciasAMetodos()) {
											String nombreClaseMostrar = convertirFormatoClasePuntos(
													refMetodo.obtenerClase());

											DefaultMutableTreeNode nodoRefMetodo = new DefaultMutableTreeNode(
													new NodoConTexto(
															MonitorDePID.idioma.metodo() + ": " + nombreClaseMostrar
																	+ "." + refMetodo.obtenerNombre()
																	+ refMetodo.obtenerDescriptor(),
															new Object[] { mod, clasePuntos, metodo, refMetodo }));

											nodoReferencias.add(nodoRefMetodo);
										}

										// Referencias a campos
										for (ArchivoDeMod.Referencia refCampo : metodo.obtenerReferenciasACampos()) {
											String nombreClaseMostrar = convertirFormatoClasePuntos(
													refCampo.obtenerClase());

											DefaultMutableTreeNode nodoRefCampo = new DefaultMutableTreeNode(
													new NodoConTexto(
															MonitorDePID.idioma.campo() + ": " + nombreClaseMostrar
																	+ "." + refCampo.obtenerNombre() + " "
																	+ refCampo.obtenerDescriptor(),
															new Object[] { mod, clasePuntos, metodo, refCampo }));

											nodoReferencias.add(nodoRefCampo);
										}

										// 🔹 Referencias de clase deduplicadas
										java.util.LinkedHashSet<String> clasesReferenciadas = new java.util.LinkedHashSet<>();

										for (ArchivoDeMod.Referencia refMetodo : metodo.obtenerReferenciasAMetodos()) {
											if (refMetodo.obtenerClase() != null
													&& !refMetodo.obtenerClase().isEmpty()) {
												clasesReferenciadas.add(refMetodo.obtenerClase());
											}
										}

										for (ArchivoDeMod.Referencia refCampo : metodo.obtenerReferenciasACampos()) {
											if (refCampo.obtenerClase() != null && !refCampo.obtenerClase().isEmpty()) {
												clasesReferenciadas.add(refCampo.obtenerClase());
											}
										}

										if (!clasesReferenciadas.isEmpty()) {
											DefaultMutableTreeNode nodoClassRefs = new DefaultMutableTreeNode(
													new NodoConTexto(
															"Referencias de clase (" + clasesReferenciadas.size() + ")",
															"contenedor_classrefs"));

											for (String claseRef : clasesReferenciadas) {
												String nombreClaseMostrar = convertirFormatoClasePuntos(claseRef);

												nodoClassRefs.add(new DefaultMutableTreeNode(new NodoConTexto(
														"Clase: " + nombreClaseMostrar,
														new Object[] { mod, clasePuntos, metodo,
																new ArchivoDeMod.Referencia(claseRef, "", "", true),
																"classref" })));
											}

											nodoReferencias.add(nodoClassRefs);
										}

										nodoMetodo.add(nodoReferencias);
									}

									// Constantes
									try {
										List<ArchivoDeMod.Constante> constantes = mod.buscarConstantesEnMetodo(
												claseInterna, metodo.obtenerNombre(), metodo.obtenerDescriptor());

										if (!constantes.isEmpty()) {
											DefaultMutableTreeNode nodoConstantes = new DefaultMutableTreeNode(
													new NodoConTexto("Constantes (" + constantes.size() + ")",
															"contenedor_constantes"));

											for (ArchivoDeMod.Constante k : constantes) {
												nodoConstantes.add(new DefaultMutableTreeNode(new NodoConTexto(
														formatearConstante(k),
														new Object[] { mod, clasePuntos, metodo, k, "constante" })));
											}

											nodoMetodo.add(nodoConstantes);
										}
									} catch (Throwable t) {
										CrashDetectorLogger.logException(t);
									}

									nodoMetodos.add(nodoMetodo);
								}

								nodoClase.add(nodoMetodos);
							}

							// 🔹 Campos
							List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(claseInterna);
							if (!campos.isEmpty()) {
								DefaultMutableTreeNode nodoCampos = new DefaultMutableTreeNode(
										new NodoConTexto(MonitorDePID.idioma.campos() + " (" + campos.size() + ")",
												"contenedor_campos"));

								for (ArchivoDeMod.InfoCampo campo : campos) {
									nodoCampos.add(new DefaultMutableTreeNode(
											new NodoConTexto(campo.obtenerNombre() + " " + campo.obtenerDescriptor(),
													new Object[] { mod, clasePuntos, campo })));
								}

								nodoClase.add(nodoCampos);
							}

							nodoPaquete.add(nodoClase);
						}

						hijos.add(nodoPaquete);
					}
				}

				return hijos;
			}

			@Override
			protected void done() {
				try {
					List<DefaultMutableTreeNode> hijos = get();

					nodoMod.removeAllChildren();
					for (DefaultMutableTreeNode hijo : hijos) {
						nodoMod.add(hijo);
					}

					modeloArbol.reload(nodoMod);
				} catch (Exception e) {
					CrashDetectorLogger.logException(e);
				} finally {
					setCargando(false);
				}
			}
		}.execute();
	}

	public void iniciarCargaPesada() {
		setCargando(true);
		// getRootPane().getGlassPane().setVisible(true);

		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				Buscador.cargarYPrecargarClasesEnCache();
				construirIndice();
				return null;
			}

			@Override
			protected void done() {
				try {
					get();
				} catch (Exception ignored) {
				}
				construirArbolInicialAsync();
				setCargando(false);
				com.asbestosstar.crashdetector.CrashDetectorLogger.log("iniciarCargaPesada done");
			}
		}.execute();
	}

	public String normalizarTipoFiltroUI(String seleccionado) {
		if (seleccionado == null)
			return "*";
		String s = seleccionado.trim();
		if (s.isEmpty() || "*".equals(s))
			return "*";

		String sl = s.toLowerCase();

		if (sl.contains("refer")) {
			if (sl.contains("method") || sl.contains("métod") || sl.contains("metod"))
				return "REFERENCIA_METODO";
			if (sl.contains("field") || sl.contains("campo"))
				return "REFERENCIA_CAMPO";
			if (sl.contains("class") || sl.contains("clase"))
				return "REFERENCIA_CLASE";
			return "REFERENCIA";
		}

		if (sl.contains("paquet") || sl.contains("package"))
			return "PAQUETE";
		if (sl.contains("class") || sl.contains("clase"))
			return "CLASE";
		if (sl.contains("method") || sl.contains("métod") || sl.contains("metod"))
			return "METODO";
		if (sl.contains("field") || sl.contains("campo"))
			return "CAMPO";
		if (sl.contains("const"))
			return "CONSTANTE";

		return "*";
	}

	public boolean debeIncluirsePorTipoFiltro(PathDescriptor desc, String tipoFiltro) {
		String tf = normalizarTipoFiltroUI(tipoFiltro);
		if (desc == null)
			return false;
		if ("*".equals(tf))
			return true;

		String tipoDesc = desc.obtenerTipo();
		if (tipoDesc == null)
			return false;

		switch (tf) {
		case "PAQUETE":
			return "PAQUETE".equals(tipoDesc);
		case "CLASE":
			return "CLASE".equals(tipoDesc);
		case "METODO":
			return "METODO".equals(tipoDesc);
		case "CAMPO":
			return "CAMPO".equals(tipoDesc);
		case "CONSTANTE":
			return "CONSTANTE".equals(tipoDesc);
		case "REFERENCIA_METODO":
			return "REFERENCIA_METODO".equals(tipoDesc) || "REFERENCIA".equals(tipoDesc);
		case "REFERENCIA_CAMPO":
			return "REFERENCIA_CAMPO".equals(tipoDesc) || "REFERENCIA".equals(tipoDesc);
		case "REFERENCIA_CLASE":
			return "REFERENCIA_CLASE".equals(tipoDesc) || "REFERENCIA".equals(tipoDesc);
		default:
			return true;
		}
	}

	public Object crearPlaceholderInfo(String nombre, String descriptor, String tipo) {
		if ("METODO".equals(tipo) || "CONSTANTE".equals(tipo)) {
			return new ArchivoDeMod.InfoMetodo(nombre, descriptor, new ArrayList<>(), new ArrayList<>());
		} else {
			return new ArchivoDeMod.InfoCampo(nombre, descriptor);
		}
	}

	public ArchivoDeMod.InfoMetodo resolverInfoMetodoReal(ArchivoDeMod mod, String clasePuntos, String nombreMetodo,
			String descriptor) {
		if (mod == null || clasePuntos == null || nombreMetodo == null || descriptor == null)
			return null;
		try {
			// 🔥 CAMBIO CLAVE: normalizar a interno SIEMPRE
			String claseInterna = normalizarNombreClaseInterno(clasePuntos);
			if (!mod.existeClase(claseInterna))
				return null;
			for (ArchivoDeMod.InfoMetodo m : mod.obtenerMetodosConReferencias(claseInterna)) {
				if (nombreMetodo.equals(m.obtenerNombre()) && descriptor.equals(m.obtenerDescriptor())) {
					return m;
				}
			}
		} catch (Throwable t) {
		}
		return null;
	}

	public ArchivoDeMod.InfoCampo resolverInfoCampoReal(ArchivoDeMod mod, String clasePuntos, String nombreCampo,
			String descriptor) {
		if (mod == null || clasePuntos == null || nombreCampo == null)
			return null;
		try {
			// 🔥 CAMBIO CLAVE: normalizar a interno SIEMPRE
			String claseInterna = normalizarNombreClaseInterno(clasePuntos);
			if (!mod.existeClase(claseInterna))
				return null;
			for (ArchivoDeMod.InfoCampo c : mod.obtenerCampos(claseInterna)) {
				if (nombreCampo.equals(c.obtenerNombre())) {
					if (descriptor == null || descriptor.equals(c.obtenerDescriptor())) {
						return c;
					}
				}
			}
		} catch (Throwable t) {
		}
		return null;
	}

	public void construirArbolInicial() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.modsCargados());
		for (ArchivoDeMod mod : Buscador.mods) {
			agregarModuloARaiz(raiz, mod);
		}
		modeloArbol = new ModeloArbolConExpandibleMods(raiz);
		arbolModulos.setModel(modeloArbol);
	}

	public Map<String, List<String>> agruparClasesPorPaquete(List<String> clases) {
		Map<String, List<String>> clasesPorPaquete = new TreeMap<>();

		for (String clase : clases) {
			String paquete = "";
			String nombreClase = clase;

			int indiceUltimoPunto = clase.lastIndexOf('.');
			if (indiceUltimoPunto > 0) {
				paquete = clase.substring(0, indiceUltimoPunto);
				nombreClase = clase.substring(indiceUltimoPunto + 1);
			} else {
				// 🔥 Si viene en formato interno "a/b/C", sacamos paquete con '/'
				int idxBarra = clase.lastIndexOf('/');
				if (idxBarra > 0) {
					paquete = clase.substring(0, idxBarra).replace('/', '.');
					nombreClase = clase.substring(idxBarra + 1);
				}
			}

			clasesPorPaquete.computeIfAbsent(paquete, k -> new ArrayList<>()).add(nombreClase);
		}

		return clasesPorPaquete;
	}

	public void agregarModuloARaiz(DefaultMutableTreeNode padre, ArchivoDeMod mod) {
		String ubicacionPublica = mod.ubicacion_para_publicar();
		DefaultMutableTreeNode nodoModulo = new DefaultMutableTreeNode(new NodoConTexto(ubicacionPublica, mod));
		padre.add(nodoModulo);

		for (ArchivoDeMod submod : mod.mods_en_mods()) {
			agregarModuloARaiz(nodoModulo, submod);
		}

		if (!mod.clases().isEmpty()) {
			Map<String, List<String>> clasesPorPaquete = agruparClasesPorPaquete(mod.clases());

			for (Map.Entry<String, List<String>> entrada : clasesPorPaquete.entrySet()) {
				String paquete = entrada.getKey();
				List<String> clasesEnPaquete = entrada.getValue();

				DefaultMutableTreeNode nodoPaquete;
				if (paquete.isEmpty()) {
					nodoPaquete = new DefaultMutableTreeNode(
							new NodoConTexto("(paquete por defecto) (" + clasesEnPaquete.size() + " clases)", paquete));
				} else {
					nodoPaquete = new DefaultMutableTreeNode(
							new NodoConTexto(paquete + " (" + clasesEnPaquete.size() + " clases)", paquete));
				}

				for (String nombreClase : clasesEnPaquete) {
					String clasePuntos = paquete.isEmpty() ? nombreClase : paquete + "." + nombreClase;

					// 🔥 CAMBIO CLAVE: normalizar a interno SIEMPRE
					String claseInterna = normalizarNombreClaseInterno(clasePuntos);

					DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
							new NodoConTexto(nombreClase, new Object[] { mod, clasePuntos }));

					if (Buscador.puedeAnalizarElContentidoDeClase() && mod.existeClase(claseInterna)) {
						List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseInterna);
						if (!metodos.isEmpty()) {
							DefaultMutableTreeNode nodoMetodos = new DefaultMutableTreeNode(new NodoConTexto(
									MonitorDePID.idioma.metodos() + " (" + metodos.size() + ")", "contenedor_metodos"));
							for (ArchivoDeMod.InfoMetodo metodo : metodos) {
								DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(
										new NodoConTexto(metodo.obtenerNombre() + metodo.obtenerDescriptor(),
												new Object[] { mod, clasePuntos, metodo }));

								String claseInterna2 = normalizarNombreClaseInterno(claseInterna);
								List<ArchivoDeMod.Constante> constantes = mod.buscarConstantesEnMetodo(claseInterna2,
										metodo.obtenerNombre(), metodo.obtenerDescriptor());

								if (!constantes.isEmpty()) {
									DefaultMutableTreeNode nodoConstantes = new DefaultMutableTreeNode(new NodoConTexto(
											"Constantes (" + constantes.size() + ")", "contenedor_constantes"));
									for (ArchivoDeMod.Constante k : constantes) {
										nodoConstantes
												.add(new DefaultMutableTreeNode(new NodoConTexto(formatearConstante(k),
														new Object[] { mod, clasePuntos, metodo, k })));
									}
									nodoMetodo.add(nodoConstantes);
								}

								if (!metodo.obtenerReferenciasAMetodos().isEmpty()
										|| !metodo.obtenerReferenciasACampos().isEmpty()) {
									DefaultMutableTreeNode nodoReferencias = new DefaultMutableTreeNode(
											new NodoConTexto(
													MonitorDePID.idioma.referencias() + " ("
															+ (metodo.obtenerReferenciasAMetodos().size()
																	+ metodo.obtenerReferenciasACampos().size())
															+ ")",
													"contenedor_referencias"));

									for (ArchivoDeMod.Referencia refMetodo : metodo.obtenerReferenciasAMetodos()) {
										String nombreClaseMostrar = Buscador
												.convertirFormatoClasePuntos(refMetodo.obtenerClase());
										DefaultMutableTreeNode nodoRefMetodo = new DefaultMutableTreeNode(
												new NodoConTexto(
														MonitorDePID.idioma.metodo() + ": " + nombreClaseMostrar + "."
																+ refMetodo.obtenerNombre()
																+ refMetodo.obtenerDescriptor(),
														new Object[] { mod, clasePuntos, metodo, refMetodo }));
										nodoReferencias.add(nodoRefMetodo);
									}

									for (ArchivoDeMod.Referencia refCampo : metodo.obtenerReferenciasACampos()) {
										String nombreClaseMostrar = Buscador
												.convertirFormatoClasePuntos(refCampo.obtenerClase());
										DefaultMutableTreeNode nodoRefCampo = new DefaultMutableTreeNode(
												new NodoConTexto(
														MonitorDePID.idioma.campo() + ": " + nombreClaseMostrar + "."
																+ refCampo.obtenerNombre() + " "
																+ refCampo.obtenerDescriptor(),
														new Object[] { mod, clasePuntos, metodo, refCampo }));
										nodoReferencias.add(nodoRefCampo);
									}
									nodoMetodo.add(nodoReferencias);
								}
								nodoMetodos.add(nodoMetodo);
							}
							nodoClase.add(nodoMetodos);
						}

						List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(claseInterna);
						if (!campos.isEmpty()) {
							DefaultMutableTreeNode nodoCampos = new DefaultMutableTreeNode(new NodoConTexto(
									MonitorDePID.idioma.campos() + " (" + campos.size() + ")", "contenedor_campos"));
							for (ArchivoDeMod.InfoCampo campo : campos) {
								DefaultMutableTreeNode nodoCampo = new DefaultMutableTreeNode(
										new NodoConTexto(campo.obtenerNombre() + " " + campo.obtenerDescriptor(),
												new Object[] { mod, clasePuntos, campo }));
								nodoCampos.add(nodoCampo);
							}
							nodoClase.add(nodoCampos);
						}
					}
					nodoPaquete.add(nodoClase);
				}
				nodoModulo.add(nodoPaquete);
			}
		}
	}

	public List<ArchivoDeMod> obtenerTodosLosMods() {
		List<ArchivoDeMod> out = new ArrayList<>();
		for (ArchivoDeMod m : Buscador.mods) {
			recolectarModsRec(out, m);
		}
		return out;
	}

	public void recolectarModsRec(List<ArchivoDeMod> out, ArchivoDeMod m) {
		if (m == null)
			return;
		out.add(m);
		for (ArchivoDeMod sub : m.mods_en_mods()) {
			recolectarModsRec(out, sub);
		}
	}

	// === (El resto del archivo es igual al tuyo, excepto paqueteDe/nombreSimpleDe
	// y constantes) ===

	public void initOverlayCarga() {
		overlayCarga = new ElementoOverlayCarga();
		getRootPane().setGlassPane(overlayCarga);
	}

	public void setCargando(boolean cargando) {
		this.cargando = cargando;

		if (overlayCarga != null) {
			overlayCarga.setVisible(cargando);
			overlayCarga.revalidate();
			overlayCarga.repaint();
		}
	}

	public void construirArbolInicialAsync() {
		if (workerConstruir != null && !workerConstruir.isDone()) {
			workerConstruir.cancel(true);
		}
		setCargando(true);
		workerConstruir = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				List<DefaultMutableTreeNode> mods = new ArrayList<>();
				for (ArchivoDeMod mod : Buscador.mods) {
					mods.add(new DefaultMutableTreeNode(new NodoConTexto(mod.ubicacion_para_publicar(), mod)));
				}
				return null;
			}

			@Override
			protected void done() {
				try {
					if (!isCancelled()) {
						DefaultMutableTreeNode raizActual = (DefaultMutableTreeNode) modeloArbol.getRoot();
						raizActual.removeAllChildren();
						raizActual.setUserObject(MonitorDePID.idioma.modsCargados());

						for (ArchivoDeMod mod : Buscador.mods) {
							DefaultMutableTreeNode nodoMod = new DefaultMutableTreeNode(
									new NodoConTexto(mod.ubicacion_para_publicar(), mod));
							modeloArbol.insertNodeInto(nodoMod, raizActual, raizActual.getChildCount());
						}

						modeloArbol.nodeStructureChanged(raizActual);
					}
				} catch (Exception ignored) {
				} finally {
					setCargando(false);
					com.asbestosstar.crashdetector.CrashDetectorLogger.log("no cargando completa");
				}
			}
		};
		workerConstruir.execute();
	}

	private static final int NUCLEOS = Math.max(1, Runtime.getRuntime().availableProcessors());
	private static final int TAM_POOL = Math.max(2, NUCLEOS * 4);

	private static final ExecutorService POOL_BG = Executors.newFixedThreadPool(TAM_POOL, new ThreadFactory() {
		private final AtomicInteger n = new AtomicInteger(1);

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r, "ArbolMods-BG-" + n.getAndIncrement());
			t.setDaemon(true);
			return t;
		}
	});

	// Devuelve el paquete (antes del último punto) o "" si no hay.
	// 🔥 CAMBIO CLAVE: soporta también formato interno con '/'
	public String paqueteDe(String clase) {
		if (clase == null)
			return "";
		int iPunto = clase.lastIndexOf('.');
		int iBarra = clase.lastIndexOf('/');
		int i = Math.max(iPunto, iBarra);
		if (i > 0) {
			String p = clase.substring(0, i);
			// Normalizamos a puntos para mostrar/organizar
			return p.replace('/', '.');
		}
		return "";
	}

	public String nombreSimpleDe(String clase) {
		if (clase == null)
			return "";
		int iPunto = clase.lastIndexOf('.');
		int iBarra = clase.lastIndexOf('/');
		int i = Math.max(iPunto, iBarra);
		return (i > 0) ? clase.substring(i + 1) : clase;
	}

	public DefaultMutableTreeNode asegurarNodoClase(DefaultMutableTreeNode nodoPaquete, ArchivoDeMod mod,
			String clasePuntos) {
		for (int i = 0; i < nodoPaquete.getChildCount(); i++) {
			DefaultMutableTreeNode hijo = (DefaultMutableTreeNode) nodoPaquete.getChildAt(i);
			Object uo = hijo.getUserObject();
			if (uo instanceof NodoConTexto) {
				Object obj = ((NodoConTexto) uo).objeto();
				if (obj instanceof Object[]) {
					Object[] arr = (Object[]) obj;
					if (arr.length >= 2 && arr[0] == mod && clasePuntos.equals(arr[1]))
						return hijo;
				}
			}
		}
		DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
				new NodoConTexto(nombreSimpleDe(clasePuntos), new Object[] { mod, clasePuntos }));
		nodoPaquete.add(nodoClase);
		return nodoClase;
	}

	public DefaultMutableTreeNode asegurarNodoContenedor(DefaultMutableTreeNode padre, String clave, String etiqueta) {
		for (int i = 0; i < padre.getChildCount(); i++) {
			DefaultMutableTreeNode hijo = (DefaultMutableTreeNode) padre.getChildAt(i);
			Object uo = hijo.getUserObject();
			if (uo instanceof NodoConTexto && clave.equals(((NodoConTexto) uo).objeto()))
				return hijo;
		}
		DefaultMutableTreeNode cont = new DefaultMutableTreeNode(new NodoConTexto(etiqueta, clave));
		padre.add(cont);
		return cont;
	}

	public String formatearConstante(ArchivoDeMod.Constante c) {
		Object v = c.obtenerValor();
		String val;
		if (v instanceof String) {
			String s = (String) v;
			s = s.replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t").replace("\"",
					"\\\"");
			val = "\"" + s + "\"";
		} else {
			val = String.valueOf(v);
		}
		return c.obtenerTipo() + ": " + val;
	}

	public boolean constanteCoincide(ArchivoDeMod.Constante c, String filtroLower) {
		return formatearConstante(c).toLowerCase().contains(filtroLower);
	}

	// Inserta "Constantes" bajo un nodo de método si aún no existen
	public void asegurarNodoConstantesPara(DefaultMutableTreeNode nodoMetodo) {
		Object uo = nodoMetodo.getUserObject();
		if (!(uo instanceof NodoConTexto))
			return;
		Object payload = ((NodoConTexto) uo).objeto();
		if (!(payload instanceof Object[]))
			return;

		Object[] datos = (Object[]) payload;

		if (datos.length != 3 || !(datos[0] instanceof ArchivoDeMod) || !(datos[1] instanceof String)
				|| !(datos[2] instanceof ArchivoDeMod.InfoMetodo)) {
			return;
		}

		ArchivoDeMod mod = (ArchivoDeMod) datos[0];
		String clasePuntos = (String) datos[1];
		ArchivoDeMod.InfoMetodo metodo = (ArchivoDeMod.InfoMetodo) datos[2];

		for (int i = 0; i < nodoMetodo.getChildCount(); i++) {
			Object uoH = ((DefaultMutableTreeNode) nodoMetodo.getChildAt(i)).getUserObject();
			if (uoH instanceof NodoConTexto && "contenedor_constantes".equals(((NodoConTexto) uoH).objeto())) {
				return;
			}
		}

		// 🔥 CAMBIO CLAVE: normalizar para buscar constantes
		String claseInterna = normalizarNombreClaseInterno(clasePuntos);

		List<ArchivoDeMod.Constante> constantes = mod.buscarConstantesEnMetodo(claseInterna, metodo.obtenerNombre(),
				metodo.obtenerDescriptor());
		if (constantes == null || constantes.isEmpty())
			return;

		DefaultMutableTreeNode nodoConst = new DefaultMutableTreeNode(
				new NodoConTexto("Constantes (" + constantes.size() + ")", "contenedor_constantes"));

		for (ArchivoDeMod.Constante k : constantes) {
			nodoConst.add(new DefaultMutableTreeNode(
					new NodoConTexto(formatearConstante(k), new Object[] { mod, clasePuntos, metodo, k })));
		}

		modeloArbol.insertNodeInto(nodoConst, nodoMetodo, nodoMetodo.getChildCount());
		arbolModulos.expandPath(new javax.swing.tree.TreePath(nodoMetodo.getPath()));
	}

// Renderizador personalizado para el árbol
	public class RenderizadorCeldasArbol extends DefaultTreeCellRenderer {
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			if (value instanceof DefaultMutableTreeNode) {
				Object objetoUsuario = ((DefaultMutableTreeNode) value).getUserObject();
				if (objetoUsuario instanceof NodoConTexto) {
					NodoConTexto nct = (NodoConTexto) objetoUsuario;
					setText(nct.texto());
					Object objeto = nct.objeto();

					if (objeto instanceof ArchivoDeMod) {
						setForeground(new Color(0, 0, 128));
						setIcon(iconoMod);

					} else if (objeto instanceof Object[]) {
						Object[] datos = (Object[]) objeto;

						// 🔹 VERIFICAR LONGITUD ANTES DE ACCEDER A ÍNDICES (CORRECCIÓN CRÍTICA)
						if (datos.length >= 3) {
							// 🔹 Detectar nodos de mixin por el tercer elemento (String de tipo)
							Object tercerElemento = datos[2];
							if (tercerElemento instanceof String) {
								String tipo = (String) tercerElemento;
								if ("mixin_info".equals(tipo) || "mixin_targets".equals(tipo)
										|| "mixin_metodos".equals(tipo) || "mixin_campos".equals(tipo)
										|| "target".equals(tipo) || "mixin_metodo".equals(tipo)
										|| "mixin_campo".equals(tipo)) {
									setForeground(new Color(128, 0, 128)); // Púrpura para mixins
									setIcon(iconoMixin);
								}
							}
							// InfoMetodo
							else if (tercerElemento instanceof ArchivoDeMod.InfoMetodo) {
								setForeground(new Color(0, 0, 153));
								setIcon(iconoMetodo);
							}
							// InfoCampo
							else if (tercerElemento instanceof ArchivoDeMod.InfoCampo) {
								setForeground(new Color(153, 0, 0));
								setIcon(iconoCampo);
							}
							// Referencia (requiere 4 elementos)
							else if (datos.length >= 4 && datos[3] instanceof ArchivoDeMod.Referencia) {
								ArchivoDeMod.Referencia ref = (ArchivoDeMod.Referencia) datos[3];
								setIcon(ref.esMetodo() ? iconoReferenciaMetodo : iconoReferenciaCampo);
								setForeground(Color.DARK_GRAY);
							}
							// Caso por defecto para arrays de 3+ elementos no reconocidos
							else {
								setForeground(Color.DARK_GRAY);
								setIcon(iconoClase);
							}
						} else {
							// 🔹 Array con solo 2 elementos {mod, clase} = nodo de clase normal
							setForeground(Color.DARK_GRAY);
							setIcon(iconoClase);
						}

					} else if (objeto instanceof String) {
						String str = (String) objeto;
						if (str.equals("contenedor_metodos")) {
							setText(MonitorDePID.idioma.metodos());
							setIcon(iconoMetodo);
						} else if (str.equals("contenedor_campos")) {
							setText(MonitorDePID.idioma.campos());
							setIcon(iconoCampo);
						} else if (str.equals("contenedor_referencias")) {
							setText(MonitorDePID.idioma.referencias());
							setIcon(iconoReferenciaMetodo);
						} else if (str.equals("contenedor_constantes")) {
							setText("Constantes");
							setIcon(iconoConstante);
						} else if (str.startsWith("mixin_")) {
							setForeground(new Color(128, 0, 128));
							setIcon(iconoMixin);
						} else if (!nct.texto().contains("(")) {
							setForeground(new Color(0, 102, 0));
							setIcon(iconoPaquete);
						}
					} else {
						setForeground(Color.BLACK);
					}
				} else {
					setForeground(Color.BLACK);
				}
			}
			return this;
		}
	}

	public ImageIcon crearIcono(String ruta, String textoAlternativo) {
		ImageIcon icono = new ImageIcon(ruta);
		if (icono.getIconWidth() <= 0) {
			int tamano = 16;
			BufferedImage imagen = new BufferedImage(tamano, tamano, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = imagen.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g2d.setColor(new Color(200, 200, 200, 150));
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
		return icono;
	}

	// (El resto de métodos de menú contextual / mostrarDetallesNodo / etc.
	// pueden quedarse como estaban en tu clase original.)

	public static class ModeloArbolConExpandibleMods extends DefaultTreeModel {
		public ModeloArbolConExpandibleMods(TreeNode root) {
			super(root);
		}

		@Override
		public boolean isLeaf(Object node) {
			if (node instanceof DefaultMutableTreeNode) {
				Object userObj = ((DefaultMutableTreeNode) node).getUserObject();
				if (userObj instanceof NodoConTexto) {
					Object real = ((NodoConTexto) userObj).objeto();
					if (real instanceof ArchivoDeMod) {
						return false;
					}
				}
			}
			return super.isLeaf(node);
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

	public void mostrarMenuContexto(MouseEvent e) {
		TreePath seleccion = arbolModulos.getPathForLocation(e.getX(), e.getY());
		if (seleccion == null)
			return;

		Object nodo = seleccion.getLastPathComponent();
		if (!(nodo instanceof DefaultMutableTreeNode))
			return;

		Object objetoUsuario = ((DefaultMutableTreeNode) nodo).getUserObject();
		if (objetoUsuario == null)
			return;

		// Extraer el objeto real de NodoConTexto si es necesario
		final Object objetoReal;
		if (objetoUsuario instanceof NodoConTexto) {
			objetoReal = ((NodoConTexto) objetoUsuario).objeto();
		} else {
			objetoReal = objetoUsuario;
		}

		JPopupMenu menu = new JPopupMenu();

		// Opción para buscar referencias
		JMenuItem itemBuscarRef = new JMenuItem(MonitorDePID.idioma.buscarReferencias());
		itemBuscarRef.addActionListener(ae -> {
			if (objetoReal instanceof ArchivoDeMod) {
				// Es un mod - mostrar ubicaciones de mods relacionados
				List<ArchivoDeMod> mods = new ArrayList<>();
				mods.add((ArchivoDeMod) objetoReal);
				List<String> ubicaciones = Buscador.obtenerUbicaciones(mods);
				mostrarResultadosBusqueda(ubicaciones, MonitorDePID.idioma.referenciasMod());

			} else if (objetoReal instanceof String) {
				// Es un paquete - buscar mods que contengan clases en este paquete
				String paquete = (String) objetoReal;
				if (!paquete.equals("contenedor_metodos") && !paquete.equals("contenedor_campos")
						&& !paquete.equals("contenedor_referencias")) {
					List<String> ubicaciones = Buscador.obtenerUbicaciones(Buscador.buscarModsConTermino(paquete));
					mostrarResultadosBusqueda(ubicaciones, MonitorDePID.idioma.referencias() + " " + paquete);
				}

			} else if (objetoReal instanceof Object[]) {
				Object[] datos = (Object[]) objetoReal;
				if (datos.length >= 2 && datos[0] instanceof ArchivoDeMod) {
					ArchivoDeMod mod = (ArchivoDeMod) datos[0];
					String clase = (datos[1] instanceof String) ? (String) datos[1] : null;

					if (clase != null) {
						// Es una clase
						if (datos.length == 2) {
							// Buscar referencias a esta clase específica
							buscarReferenciasAClase(clase);
						}
						// Es un método - buscar referencias HACIA este método
						else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoMetodo) {
							ArchivoDeMod.InfoMetodo metodo = (ArchivoDeMod.InfoMetodo) datos[2];
							String claseInterna = Buscador.convertirFormatoClase(clase);

							// Buscar referencias HACIA este método en todos los mods
							List<Buscador.ReferenciaMod> referenciasHacia = Buscador.buscarReferenciasHaciaMetodo(
									claseInterna, metodo.obtenerNombre(), metodo.obtenerDescriptor());

							mostrarReferenciasHaciaMetodo(referenciasHacia, MonitorDePID.idioma.referenciasMetodo());
						}
						// Es un campo - buscar referencias HACIA este campo
						else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoCampo) {
							ArchivoDeMod.InfoCampo campo = (ArchivoDeMod.InfoCampo) datos[2];
							String claseInterna = Buscador.convertirFormatoClase(clase);

							// Buscar referencias hacia este campo específico
							buscarReferenciasACampo(claseInterna, campo.obtenerNombre(), campo.obtenerDescriptor());
						}
						// Es una referencia específica - buscar referencias A esa referencia
						else if (datos.length >= 4 && datos[3] instanceof ArchivoDeMod.Referencia) {
							ArchivoDeMod.Referencia referencia = (ArchivoDeMod.Referencia) datos[3];

							if (referencia.esMetodo()) {
								// Buscar referencias hacia este método referenciado
								List<Buscador.ReferenciaMod> referenciasHacia = Buscador.buscarReferenciasHaciaMetodo(
										referencia.obtenerClase(), referencia.obtenerNombre(),
										referencia.obtenerDescriptor());

								String tituloReferencias = MonitorDePID.idioma.referencias() + " "
										+ Buscador.convertirFormatoClasePuntos(referencia.obtenerClase()) + "."
										+ referencia.obtenerNombre();

								mostrarReferenciasHaciaMetodo(referenciasHacia, tituloReferencias);
							} else {
								// Es un campo referenciado - buscar referencias a este campo
								buscarReferenciasACampo(referencia.obtenerClase(), referencia.obtenerNombre(),
										referencia.obtenerDescriptor());
							}
						}
					}
				}
			}
		});
		menu.add(itemBuscarRef);

		menu.show(arbolModulos, e.getX(), e.getY());
	}

	public void buscarReferenciasACampo(String claseObjetivo, String nombreCampo, String descriptorCampo) {
		if (!Buscador.puedeAnalizarElContentidoDeClase()) {
			DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.referenciasCampo() + " "
					+ Buscador.convertirFormatoClasePuntos(claseObjetivo) + "." + nombreCampo);
			raiz.add(new DefaultMutableTreeNode("ASM no disponible"));
			modeloArbol = new ModeloArbolConExpandibleMods(raiz);
			arbolModulos.setModel(modeloArbol);
			return;
		}

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.referenciasCampo() + " "
				+ Buscador.convertirFormatoClasePuntos(claseObjetivo) + "." + nombreCampo);

		String claseObjetivoInterna = normalizarNombreClaseInterno(claseObjetivo);

		for (ArchivoDeMod mod : Buscador.mods) {
			for (String nombreClase : mod.obtenerTodosLosNombresDeClases()) {
				String claseOrigenInterna = normalizarNombreClaseInterno(nombreClase);

				byte[] bytesClase = mod.obtenerBytesClase(claseOrigenInterna);
				if (bytesClase == null) {
					continue;
				}

				try {
					List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseOrigenInterna);

					for (ArchivoDeMod.InfoMetodo metodo : metodos) {
						for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
							String claseRefInterna = normalizarNombreClaseInterno(ref.obtenerClase());

							if (claseRefInterna.equals(claseObjetivoInterna) && ref.obtenerNombre().equals(nombreCampo)
									&& ref.obtenerDescriptor().equals(descriptorCampo)) {

								String claseOrigenMostrar;
								String metodoOrigen;
								String descriptorOrigen;

								if (ref.tieneOrigen()) {
									claseOrigenMostrar = convertirFormatoClasePuntos(ref.obtenerClaseOrigen());
									metodoOrigen = ref.obtenerMetodoOrigen();
									descriptorOrigen = ref.obtenerDescriptorOrigen();
								} else {
									claseOrigenMostrar = convertirFormatoClasePuntos(claseOrigenInterna);
									metodoOrigen = metodo.obtenerNombre();
									descriptorOrigen = metodo.obtenerDescriptor();
								}

								String textoRef = MonitorDePID.idioma.metodo() + ": " + claseOrigenMostrar + "."
										+ metodoOrigen + descriptorOrigen + " (" + mod.ubicacion_para_publicar() + ")";

								raiz.add(new DefaultMutableTreeNode(textoRef));
							}
						}
					}
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
				}
			}
		}

		if (raiz.getChildCount() == 0) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
		}

		modeloArbol = new ModeloArbolConExpandibleMods(raiz);
		arbolModulos.setModel(modeloArbol);
	}

	public void mostrarResultadosBusqueda(List<String> resultados, String titulo) {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(titulo);
		if (resultados.isEmpty()) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
		} else {
			for (String resultado : resultados) {
				raiz.add(new DefaultMutableTreeNode(resultado));
			}
		}
		modeloArbol = new ModeloArbolConExpandibleMods(raiz);
		arbolModulos.setModel(modeloArbol);
	}

	public void mostrarReferenciasHaciaMetodo(List<Buscador.ReferenciaMod> referencias, String titulo) {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(titulo);

		if (referencias == null || referencias.isEmpty()) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
		} else {
			for (Buscador.ReferenciaMod refMod : referencias) {
				ArchivoDeMod.Referencia ref = refMod.obtenerReferencia();

				if (ref == null) {
					continue;
				}

				String textoReferencia;

				if (ref.tieneOrigen()) {
					String claseOrigen = Buscador.convertirFormatoClasePuntos(ref.obtenerClaseOrigen());

					textoReferencia = MonitorDePID.idioma.metodo() + ": " + claseOrigen + "."
							+ ref.obtenerMetodoOrigen() + ref.obtenerDescriptorOrigen() + " ("
							+ refMod.obtenerMod().ubicacion_para_publicar() + ")";
				} else {
					String claseDestino = Buscador.convertirFormatoClasePuntos(ref.obtenerClase());

					textoReferencia = MonitorDePID.idioma.metodo() + ": " + claseDestino + "." + ref.obtenerNombre()
							+ ref.obtenerDescriptor() + " (" + refMod.obtenerMod().ubicacion_para_publicar() + ")";
				}

				raiz.add(new DefaultMutableTreeNode(textoReferencia));
			}
		}

		if (raiz.getChildCount() == 0) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
		}

		modeloArbol = new ModeloArbolConExpandibleMods(raiz);
		arbolModulos.setModel(modeloArbol);
	}

	public void buscarReferenciasAClase(String nombreClase) {
		if (!Buscador.puedeAnalizarElContentidoDeClase()) {
			DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(
					MonitorDePID.idioma.referenciasClase() + " " + nombreClase);
			raiz.add(new DefaultMutableTreeNode("ASM no disponible"));
			modeloArbol = new ModeloArbolConExpandibleMods(raiz);
			arbolModulos.setModel(modeloArbol);
			return;
		}

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(
				MonitorDePID.idioma.referenciasClase() + " " + nombreClase);

		String claseInterna = Buscador.convertirFormatoClase(nombreClase);
		List<Buscador.ReferenciaMod> referencias = new ArrayList<>();

		String claseObjetivoInterna = normalizarNombreClaseInterno(nombreClase);

		for (ArchivoDeMod mod : Buscador.mods) {
			for (String nombreClaseActual : mod.obtenerTodosLosNombresDeClases()) {
				String claseActualInterna = normalizarNombreClaseInterno(nombreClaseActual);
				if (claseActualInterna.equals(claseObjetivoInterna))
					continue;

				byte[] bytesClase = mod.obtenerBytesClase(claseActualInterna);
				if (bytesClase == null)
					continue;

				try {
					List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseActualInterna);
					for (ArchivoDeMod.InfoMetodo metodo : metodos) {

						for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasAMetodos()) {
							if (normalizarNombreClaseInterno(ref.obtenerClase()).equals(claseObjetivoInterna)) {
								String origen = convertirFormatoClasePuntos(claseActualInterna);
								String textoRef = MonitorDePID.idioma.metodo() + ": " + origen + "."
										+ metodo.obtenerNombre() + metodo.obtenerDescriptor() + " -> "
										+ convertirFormatoClasePuntos(claseObjetivoInterna) + "." + ref.obtenerNombre()
										+ " (" + mod.ubicacion_para_publicar() + ")";
								raiz.add(new DefaultMutableTreeNode(textoRef));
							}
						}

						for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
							if (normalizarNombreClaseInterno(ref.obtenerClase()).equals(claseObjetivoInterna)) {
								String origen = convertirFormatoClasePuntos(claseActualInterna);
								String textoRef = MonitorDePID.idioma.metodo() + ": " + origen + "."
										+ metodo.obtenerNombre() + metodo.obtenerDescriptor() + " -> "
										+ convertirFormatoClasePuntos(claseObjetivoInterna) + "." + ref.obtenerNombre()
										+ " (" + mod.ubicacion_para_publicar() + ")";
								raiz.add(new DefaultMutableTreeNode(textoRef));
							}
						}
					}
				} catch (Throwable t) {
					// seguir
				}
			}
		}

		if (raiz.getChildCount() == 0) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
		}

		modeloArbol = new ModeloArbolConExpandibleMods(raiz);
		arbolModulos.setModel(modeloArbol);
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.ARBOL_DE_MODS;
	}

}
