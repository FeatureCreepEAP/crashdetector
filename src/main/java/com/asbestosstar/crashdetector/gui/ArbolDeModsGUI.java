package com.asbestosstar.crashdetector.gui;

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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;

public class ArbolDeModsGUI extends JFrame implements BotonDeBarraLateralDerecha {

	public JTree arbolModulos;
	private DefaultTreeModel modeloArbol;
	private JTextField campoBuscar;
	private JComboBox<String> comboFiltro;
	private JTextArea areaContenido;
	private JButton botonReset;
	private JLabel imagenHamu;
	// Iconos para los diferentes tipos de nodos
	private ImageIcon iconoMod;
	private ImageIcon iconoClase;
	private ImageIcon iconoMetodo;
	private ImageIcon iconoCampo;
	private ImageIcon iconoPaquete;
	private ImageIcon iconoReferenciaMetodo;
	private ImageIcon iconoReferenciaCampo;
	private ImageIcon iconoConstante;

	private JPanel overlayCarga;
	private JLabel gifCarga;
	private volatile boolean cargando = false;

	// Trabajadores en curso (para cancelar si el usuario vuelve a buscar)
	private SwingWorker<DefaultMutableTreeNode, Void> workerConstruir;
	private SwingWorker<DefaultMutableTreeNode, Void> workerBuscar;

	public ArbolDeModsGUI() {
		// Título y marco
		setTitle(MonitorDePID.idioma.tituloArbolDeMods());
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout());

		// Fondo del frame
		getContentPane().setBackground(new Color(144, 203, 239)); // #90cbef

		iconoMod = crearIcono("crash_detector/imagenes/mod.png", "M");
		iconoClase = crearIcono("crash_detector/imagenes/clase.png", "C");
		iconoMetodo = crearIcono("crash_detector/imagenes/metodo.png", "m");
		iconoCampo = crearIcono("crash_detector/imagenes/campo.png", "f");
		iconoPaquete = crearIcono("crash_detector/imagenes/paquete.png", "P");
		iconoReferenciaMetodo = crearIcono("crash_detector/imagenes/referencia_metodo.png", "RM");
		iconoReferenciaCampo = crearIcono("crash_detector/imagenes/referencia_campo.png", "RC");
		// iconoConstante = crearIcono("crash_detector/imagenes/constante.png", "K");
		iconoConstante = crearIcono("crash_detector/imagenes/referencia_campo.png", "K");

		// === Barra superior / búsqueda ===
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

		// Imagen inferior
		imagenHamu = new JLabel(new ImageIcon("crash_detector/imagenes/hamu.png"));

		// Botón reset
		botonReset = new JButton(MonitorDePID.idioma.botonResetearArbol());
		botonReset.addActionListener(e -> {
			construirArbolInicialAsync();
			areaContenido.setText("");
			campoBuscar.setText("");
		});

		// Árbol
		arbolModulos = new JTree();
		arbolModulos.setRootVisible(false);
		arbolModulos.setShowsRootHandles(true);
		arbolModulos.setCellRenderer(new RenderizadorCeldasArbol());

		// Menú contextual del árbol
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
			if (nodo == null)
				return;

			Object user = nodo.getUserObject();
			Object objReal = (user instanceof NodoConTexto) ? ((NodoConTexto) user).objeto() : user;

			if (objReal instanceof Object[]) {
				Object[] datos = (Object[]) objReal;
				if (datos.length == 3 && datos[2] instanceof ArchivoDeMod.InfoMetodo) {
					asegurarNodoConstantesPara(nodo);
				}
			}

			// Mostrar detalles como siempre
			mostrarDetallesNodo(objReal);
		});

		// Área de contenido
		areaContenido = new JTextArea();
		areaContenido.setEditable(false);
		areaContenido.setLineWrap(true);
		areaContenido.setWrapStyleWord(true);
		areaContenido.setFont(new Font("Monospaced", Font.PLAIN, 12));

		// Panel inferior (izq: imagen / der: botones)
		JPanel panelInferior = new JPanel(new BorderLayout());
		panelInferior.setOpaque(false);
		panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel panelIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelIzquierdo.setOpaque(false);
		panelIzquierdo.add(imagenHamu);

		JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelDerecho.setOpaque(false);

		JButton botonDescompilar = new JButton(MonitorDePID.idioma.descompilar());
		JButton botonExportar = new JButton(MonitorDePID.idioma.exportar());
		JButton botonImportar = new JButton(MonitorDePID.idioma.importar());

		// Deshabilitado por ahora
		botonDescompilar.setEnabled(false);

		botonDescompilar.addActionListener(e -> descompilarElementoSeleccionado());
		botonExportar.addActionListener(e -> exportarEstructura());
		botonImportar.addActionListener(e -> importarEstructura());

		panelDerecho.add(botonDescompilar);
		panelDerecho.add(botonExportar);
		panelDerecho.add(botonImportar);

		panelInferior.add(panelIzquierdo, BorderLayout.WEST);
		panelInferior.add(panelDerecho, BorderLayout.EAST);

		// Estructura principal
		add(panelInferior, BorderLayout.SOUTH);
		add(botonReset, BorderLayout.NORTH);
		add(barraSuperior, BorderLayout.CENTER);

		JSplitPane panelDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(arbolModulos),
				new JScrollPane(areaContenido));
		panelDividido.setDividerLocation(500);
		barraSuperior.add(panelBusqueda, BorderLayout.NORTH);
		barraSuperior.add(panelDividido, BorderLayout.CENTER);

		// Listeners de búsqueda → versión asíncrona
		campoBuscar.addActionListener(e -> filtrarArbolAsync());
		botonBuscar.addActionListener(e -> filtrarArbolAsync());
		comboFiltro.addActionListener(e -> filtrarArbolAsync());

		// Overlay de carga (padoru.gif)
		initOverlayCarga();
		setCargando(false); // oculto por defecto

		// Asegura que el árbol no salga vacío y que los mods estén cargados ya:
		try {
			Buscardor.cargar();
		} catch (Throwable t) {
		}

		DefaultMutableTreeNode placeholder = new DefaultMutableTreeNode(MonitorDePID.idioma.cargando());
		modeloArbol = new DefaultTreeModel(placeholder);
		arbolModulos.setModel(modeloArbol);

		// Construcción inicial del árbol en segundo plano
		construirArbolInicialAsync();
	}

	/**
	 * Acción para descompilar el elemento seleccionado en el árbol
	 */
	private void descompilarElementoSeleccionado() {
		TreePath rutaSeleccionada = arbolModulos.getSelectionPath();
		if (rutaSeleccionada == null) {
			// No hay elemento seleccionado
//        JOptionPane.showMessageDialog(this, 
//                MonitorDePID.idioma.seleccioneElementoParaDescompilar(),
//                MonitorDePID.idioma.advertencia(),
//                JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Aquí iría la lógica para descompilar el elemento seleccionado
		// Esto dependerá de qué tipo de elemento está seleccionado (clase, método,
		// etc.)
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) rutaSeleccionada.getLastPathComponent();
		Object objetoUsuario = nodo.getUserObject();

		if (objetoUsuario instanceof NodoConTexto) {
			Object objetoReal = ((NodoConTexto) objetoUsuario).objeto();
			// Aquí procesaríamos la descompilación según el tipo de objeto
			// Por ejemplo, si es una clase, podríamos mostrar su código descompilado
			if (objetoReal instanceof Object[] && ((Object[]) objetoReal).length >= 2) {
				Object[] datos = (Object[]) objetoReal;
				if (datos[0] instanceof ArchivoDeMod && datos[1] instanceof String) {
					ArchivoDeMod mod = (ArchivoDeMod) datos[0];
					String nombreClase = (String) datos[1];
					// Lógica para descompilar la clase...
					mostrarCodigoDescompilado(mod, nombreClase);
				}
			}
		}
	}

	/**
	 * Muestra el código descompilado de una clase en el área de contenido
	 */
	private void mostrarCodigoDescompilado(ArchivoDeMod mod, String nombreClase) {
		// Aquí iría la lógica para descompilar la clase y mostrar su código
		// Esto requeriría integración con una herramienta de descompilación como
		// FernFlower
//    
//    StringBuilder codigo = new StringBuilder();
//    codigo.append(MonitorDePID.idioma.codigoDescompilado()).append(" ").append(nombreClase).append(":
//");
//    codigo.append("/* 
// * Este es un ejemplo del código descompilado.
// * En una implementación real, aquí se mostraría el código Java 
// * generado a partir del archivo .class seleccionado.
// */
//");
//    
//    // Ejemplo de código descompilado
//    codigo.append("
//public class ").append(nombreClase.substring(nombreClase.lastIndexOf('.') + 1))
//          .append(" {
//    // Campos de la clase
//    private int valor;
//    private String texto;
//    
//    // Constructor
//    public ").append(nombreClase.substring(nombreClase.lastIndexOf('.') + 1))
//          .append("() {
//        this.valor = 0;
//        this.texto = \"Ejemplo\";
//    }
//    
//    // Métodos
//    public int getValor() {
//        return valor;
//    }
//    
//    public void setValor(int valor) {
//        this.valor = valor;
//    }
//}
//");
//    
		// areaContenido.setText(codigo.toString());
	}

	/**
	 * Acción para exportar la estructura actual del árbol de mods
	 */
	private void exportarEstructura() {
		// Crear un cuadro de diálogo para seleccionar la ubicación del archivo
		JFileChooser selectorArchivo = new JFileChooser();
		selectorArchivo.setSelectedFile(new File("arbol_mods.arbolmods"));
		int resultado = selectorArchivo.showSaveDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			File archivo = selectorArchivo.getSelectedFile();
			// Asegurar que el archivo tenga extensión .arbolmods
			if (!archivo.getName().toLowerCase().endsWith(".arbolmods")) {
				archivo = new File(archivo.getParentFile(), archivo.getName() + ".arbolmods");
			}

//        JOptionPane.showMessageDialog(this,
//        		MonitorDePID.idioma.exportacionTardara(),
//                MonitorDePID.idioma.porFavorEspere(),
//                JOptionPane.INFORMATION_MESSAGE);

			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
				// Convertir el árbol a una estructura serializable
				NodoArbolExportable estructuraSerializable = convertirArbolASerializable();
				// Escribir la estructura en el archivo
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

	/**
	 * Acción para importar una estructura desde un archivo
	 */
	private void importarEstructura() {
		// Crear un cuadro de diálogo para seleccionar el archivo a importar
		JFileChooser selectorArchivo = new JFileChooser();
		selectorArchivo.setFileFilter(new FileNameExtensionFilter("Archivos de árbol de mods", "arbolmods"));
		int resultado = selectorArchivo.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			File archivo = selectorArchivo.getSelectedFile();
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
				// Leer la estructura serializable
				NodoArbolExportable estructuraSerializable = (NodoArbolExportable) ois.readObject();
				// Reconstruir el árbol desde la estructura serializable
				DefaultMutableTreeNode nuevaRaiz = convertirSerializableAArbol(estructuraSerializable);
				modeloArbol = new DefaultTreeModel(nuevaRaiz);
				arbolModulos.setModel(modeloArbol);

				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.estructuraImportada(),
						MonitorDePID.idioma.exito(), JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.errorImportar() + ": " + ex.getMessage(),
						MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Clase para representar un nodo del árbol de forma serializable. Solo contiene
	 * la información necesaria para reconstruir la estructura visual del árbol.
	 */
	private static class NodoArbolExportable implements Serializable {
		private static final long serialVersionUID = 1L;

		private String texto;
		private String tipo;
		private List<NodoArbolExportable> hijos;

		public NodoArbolExportable(String texto, String tipo) {
			this.texto = texto;
			this.tipo = tipo;
			this.hijos = new ArrayList<>();
		}

		public void agregarHijo(NodoArbolExportable hijo) {
			hijos.add(hijo);
		}

		// Getters
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

	/**
	 * Convierte el modelo actual del árbol a una estructura serializable.
	 */
	private NodoArbolExportable convertirArbolASerializable() {
		DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) modeloArbol.getRoot();
		return convertirNodoASerializable(raiz);
	}

	/**
	 * Convierte un nodo del árbol a su representación serializable.
	 */
	private NodoArbolExportable convertirNodoASerializable(DefaultMutableTreeNode nodo) {
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

	/**
	 * Convierte una estructura serializable de vuelta a un nodo del árbol.
	 */
	private DefaultMutableTreeNode convertirSerializableAArbol(NodoArbolExportable nodoSerializable) {
		// Crear el nodo con NodoConTexto para mantener consistencia
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(
				new NodoConTexto(nodoSerializable.texto(), nodoSerializable.tipo()));

		for (NodoArbolExportable serializable : nodoSerializable.sub()) {
			nodo.add(convertirSerializableAArbol(serializable));
		}

		return nodo;
	}

	@Override
	public void init() {
		this.setVisible(true);
		Buscardor.cargar();
	}

	@Override
	public String etiquetaDelBoton() {
		return MonitorDePID.idioma.arbolDeMods();
	}

	public void construirArbolInicial() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.modsCargados());
		for (ArchivoDeMod mod : Buscardor.mods) {
			agregarModuloARaiz(raiz, mod);
		}
		modeloArbol = new DefaultTreeModel(raiz);
		arbolModulos.setModel(modeloArbol);
	}

	/**
	 * Agrupa las clases por paquete para una mejor organización en el árbol.
	 */
	private Map<String, List<String>> agruparClasesPorPaquete(List<String> clases) {
		Map<String, List<String>> clasesPorPaquete = new TreeMap<>(); // TreeMap para orden alfabético

		for (String clase : clases) {
			String paquete = "";
			String nombreClase = clase;

			int indiceUltimoPunto = clase.lastIndexOf('.');
			if (indiceUltimoPunto > 0) {
				paquete = clase.substring(0, indiceUltimoPunto);
				nombreClase = clase.substring(indiceUltimoPunto + 1);
			}

			clasesPorPaquete.computeIfAbsent(paquete, k -> new ArrayList<>()).add(nombreClase);
		}

		return clasesPorPaquete;
	}

	private void agregarModuloARaiz(DefaultMutableTreeNode padre, ArchivoDeMod mod) {
		String ubicacionPublica = mod.ubicacion_para_publicar();
		DefaultMutableTreeNode nodoModulo = new DefaultMutableTreeNode(new NodoConTexto(ubicacionPublica, mod));
		padre.add(nodoModulo);

		// Agregar submods recursivamente
		for (ArchivoDeMod submod : mod.mods_en_mods()) {
			agregarModuloARaiz(nodoModulo, submod);
		}

		// Agregar paquetes y clases
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
					String claseInterna = Buscardor.convertirFormatoClase(clasePuntos);

					DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
							new NodoConTexto(nombreClase, new Object[] { mod, clasePuntos }));

					if (Buscardor.puedeAnalizarElContentidoDeClase() && mod.existeClase(claseInterna)) {
						// Métodos
						List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseInterna);
						if (!metodos.isEmpty()) {
							DefaultMutableTreeNode nodoMetodos = new DefaultMutableTreeNode(new NodoConTexto(
									MonitorDePID.idioma.metodos() + " (" + metodos.size() + ")", "contenedor_metodos"));
							for (ArchivoDeMod.InfoMetodo metodo : metodos) {
								DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(
										new NodoConTexto(metodo.obtenerNombre() + metodo.obtenerDescriptor(),
												new Object[] { mod, clasePuntos, metodo }));

								// === Constantes del método ===
								List<ArchivoDeMod.Constante> constantes = mod.buscarConstantesEnMetodo(
										Buscardor.convertirFormatoClase(clasePuntos), metodo.obtenerNombre(),
										metodo.obtenerDescriptor());
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

								// Referencias
								if (!metodo.obtenerReferenciasAMetodos().isEmpty()
										|| !metodo.obtenerReferenciasACampos().isEmpty()) {
									DefaultMutableTreeNode nodoReferencias = new DefaultMutableTreeNode(
											new NodoConTexto(
													MonitorDePID.idioma.referencias() + " ("
															+ (metodo.obtenerReferenciasAMetodos().size()
																	+ metodo.obtenerReferenciasACampos().size())
															+ ")",
													"contenedor_referencias"));

									// A métodos
									for (ArchivoDeMod.Referencia refMetodo : metodo.obtenerReferenciasAMetodos()) {
										String nombreClaseMostrar = Buscardor
												.convertirFormatoClasePuntos(refMetodo.obtenerClase());
										DefaultMutableTreeNode nodoRefMetodo = new DefaultMutableTreeNode(
												new NodoConTexto(
														MonitorDePID.idioma.metodo() + ": " + nombreClaseMostrar + "."
																+ refMetodo.obtenerNombre()
																+ refMetodo.obtenerDescriptor(),
														new Object[] { mod, clasePuntos, metodo, refMetodo }));
										nodoReferencias.add(nodoRefMetodo);
									}
									// A campos
									for (ArchivoDeMod.Referencia refCampo : metodo.obtenerReferenciasACampos()) {
										String nombreClaseMostrar = Buscardor
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

						// Campos
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

	private boolean buscarEnModulo(DefaultMutableTreeNode padre, ArchivoDeMod mod, String filtro, String tipoFiltro) {
		boolean algoEnModulo = false;
		String filtroLower = (filtro == null) ? "" : filtro.toLowerCase();

		DefaultMutableTreeNode nodoModulo = new DefaultMutableTreeNode(
				new NodoConTexto(mod.ubicacion_para_publicar(), mod));

		// Si el nombre del mod coincide, marcamos que hay algo que mostrar
		if (mod.ubicacion_para_publicar().toLowerCase().contains(filtroLower)) {
			algoEnModulo = true;
		}

		// Submods
		for (ArchivoDeMod submod : mod.mods_en_mods()) {
			if (buscarEnModulo(nodoModulo, submod, filtroLower, tipoFiltro)) {
				algoEnModulo = true;
			}
		}

		// Clases del mod
		for (String clasePuntos : mod.clases()) {
			String paquete = paqueteDe(clasePuntos);
			String claseInterna = Buscardor.convertirFormatoClase(clasePuntos);

			boolean incluirPorPaquete = ("*".equals(tipoFiltro)
					|| MonitorDePID.idioma.filtroPaquetes().equals(tipoFiltro))
					&& paquete.toLowerCase().contains(filtroLower);
			boolean incluirPorClase = ("*".equals(tipoFiltro) || MonitorDePID.idioma.filtroClases().equals(tipoFiltro))
					&& clasePuntos.toLowerCase().contains(filtroLower);

			boolean puedeASM = Buscardor.puedeAnalizarElContentidoDeClase() && mod.existeClase(claseInterna);
			List<ArchivoDeMod.InfoMetodo> metodos = puedeASM ? mod.obtenerMetodosConReferencias(claseInterna)
					: new ArrayList<>();
			List<ArchivoDeMod.InfoCampo> campos = puedeASM ? mod.obtenerCampos(claseInterna) : new ArrayList<>();

			boolean hayAlgoEnEstaClase = false;

			// Caso A: match por paquete o clase → incluir clase completa (métodos + campos)
			if (incluirPorPaquete || incluirPorClase) {
				DefaultMutableTreeNode nodoPaquete = asegurarNodoPaquete(nodoModulo, paquete);
				DefaultMutableTreeNode nodoClase = asegurarNodoClase(nodoPaquete, mod, clasePuntos);

				if (puedeASM) {
					if (!metodos.isEmpty()) {
						DefaultMutableTreeNode contMetodos = asegurarNodoContenedor(nodoClase, "contenedor_metodos",
								MonitorDePID.idioma.metodos() + " (" + metodos.size() + ")");
						for (ArchivoDeMod.InfoMetodo m : metodos) {
							DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(new NodoConTexto(
									m.obtenerNombre() + m.obtenerDescriptor(), new Object[] { mod, clasePuntos, m }));
							agregarReferenciasYConstantes(nodoMetodo, mod, clasePuntos, m, null, tipoFiltro);
							contMetodos.add(nodoMetodo);
						}
					}
					if (!campos.isEmpty()) {
						DefaultMutableTreeNode contCampos = asegurarNodoContenedor(nodoClase, "contenedor_campos",
								MonitorDePID.idioma.campos() + " (" + campos.size() + ")");
						for (ArchivoDeMod.InfoCampo f : campos) {
							contCampos.add(new DefaultMutableTreeNode(
									new NodoConTexto(f.obtenerNombre() + " " + f.obtenerDescriptor(),
											new Object[] { mod, clasePuntos, f })));
						}
					}
				}
				hayAlgoEnEstaClase = true;
			} else {
				// Caso B: filtrado selectivo por métodos/campos/constantes
				if (puedeASM && ("*".equals(tipoFiltro) || MonitorDePID.idioma.filtroMetodos().equals(tipoFiltro)
						|| "Constantes".equals(tipoFiltro))) {
					DefaultMutableTreeNode nodoPaquete = null, nodoClase = null, contMetodos = null;

					for (ArchivoDeMod.InfoMetodo m : metodos) {
						String nombreMetodoLower = (m.obtenerNombre() + m.obtenerDescriptor()).toLowerCase();
						boolean coincideMetodo = ("*".equals(tipoFiltro)
								|| MonitorDePID.idioma.filtroMetodos().equals(tipoFiltro))
								&& nombreMetodoLower.contains(filtroLower);

						boolean hayConstanteCoincidente = false;
						if ("Constantes".equals(tipoFiltro) || "*".equals(tipoFiltro)) {
							List<ArchivoDeMod.Constante> consts = mod.buscarConstantesEnMetodo(claseInterna,
									m.obtenerNombre(), m.obtenerDescriptor());
							for (ArchivoDeMod.Constante k : consts) {
								if (constanteCoincide(k, filtroLower)) {
									hayConstanteCoincidente = true;
									break;
								}
							}
						}

						if (coincideMetodo || hayConstanteCoincidente) {
							if (nodoPaquete == null)
								nodoPaquete = asegurarNodoPaquete(nodoModulo, paquete);
							if (nodoClase == null)
								nodoClase = asegurarNodoClase(nodoPaquete, mod, clasePuntos);
							if (contMetodos == null)
								contMetodos = asegurarNodoContenedor(nodoClase, "contenedor_metodos",
										MonitorDePID.idioma.metodos());

							DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(new NodoConTexto(
									m.obtenerNombre() + m.obtenerDescriptor(), new Object[] { mod, clasePuntos, m }));
							agregarReferenciasYConstantes(nodoMetodo, mod, clasePuntos, m,
									"Constantes".equals(tipoFiltro) ? filtroLower : null, tipoFiltro);
							contMetodos.add(nodoMetodo);
							hayAlgoEnEstaClase = true;
						}
					}
				}

				if (puedeASM && ("*".equals(tipoFiltro) || MonitorDePID.idioma.filtroCampos().equals(tipoFiltro))) {
					DefaultMutableTreeNode nodoPaquete = null, nodoClase = null, contCampos = null;

					for (ArchivoDeMod.InfoCampo f : campos) {
						String textoCampoLower = (f.obtenerNombre() + " " + f.obtenerDescriptor()).toLowerCase();
						if (textoCampoLower.contains(filtroLower)) {
							if (nodoPaquete == null)
								nodoPaquete = asegurarNodoPaquete(nodoModulo, paquete);
							if (nodoClase == null)
								nodoClase = asegurarNodoClase(nodoPaquete, mod, clasePuntos);
							if (contCampos == null)
								contCampos = asegurarNodoContenedor(nodoClase, "contenedor_campos",
										MonitorDePID.idioma.campos());

							contCampos.add(new DefaultMutableTreeNode(
									new NodoConTexto(f.obtenerNombre() + " " + f.obtenerDescriptor(),
											new Object[] { mod, clasePuntos, f })));
							hayAlgoEnEstaClase = true;
						}
					}
				}
			}

			if (hayAlgoEnEstaClase)
				algoEnModulo = true;
		}

		if (algoEnModulo) {
			padre.add(nodoModulo);
		}
		return algoEnModulo;
	}

	private void filtrarArbol() {
		String filtro = campoBuscar.getText().trim().toLowerCase();
		String tipoFiltro = (String) comboFiltro.getSelectedItem();

		if (filtro.isEmpty()) {
			construirArbolInicial();
			return;
		}

		DefaultMutableTreeNode nuevaRaiz = new DefaultMutableTreeNode(MonitorDePID.idioma.resultadosBusqueda());
		boolean encontrado = false;

		for (ArchivoDeMod mod : Buscardor.mods) {
			encontrado |= buscarEnModulo(nuevaRaiz, mod, filtro, tipoFiltro);
		}

		if (!encontrado) {
			nuevaRaiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronResultados()));
		}

		modeloArbol = new DefaultTreeModel(nuevaRaiz);
		arbolModulos.setModel(modeloArbol);
	}

	private void resetearArbol() {
		construirArbolInicial();
		areaContenido.setText("");
		campoBuscar.setText("");
	}

	private void mostrarMenuContexto(MouseEvent e) {
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
				List<String> ubicaciones = Buscardor.obtenerUbicaciones(mods);
				mostrarResultadosBusqueda(ubicaciones, MonitorDePID.idioma.referenciasMod());

			} else if (objetoReal instanceof String) {
				// Es un paquete - buscar mods que contengan clases en este paquete
				String paquete = (String) objetoReal;
				if (!paquete.equals("contenedor_metodos") && !paquete.equals("contenedor_campos")
						&& !paquete.equals("contenedor_referencias")) {
					List<String> ubicaciones = Buscardor.obtenerUbicaciones(Buscardor.buscarModsConTermino(paquete));
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
							String claseInterna = Buscardor.convertirFormatoClase(clase);

							// Buscar referencias HACIA este método en todos los mods
							List<Buscardor.ReferenciaMod> referenciasHacia = Buscardor.buscarReferenciasHaciaMetodo(
									claseInterna, metodo.obtenerNombre(), metodo.obtenerDescriptor());

							mostrarReferenciasHaciaMetodo(referenciasHacia, MonitorDePID.idioma.referenciasMetodo());
						}
						// Es un campo - buscar referencias HACIA este campo
						else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoCampo) {
							ArchivoDeMod.InfoCampo campo = (ArchivoDeMod.InfoCampo) datos[2];
							String claseInterna = Buscardor.convertirFormatoClase(clase);

							// Buscar referencias hacia este campo específico
							buscarReferenciasACampo(claseInterna, campo.obtenerNombre(), campo.obtenerDescriptor());
						}
						// Es una referencia específica - buscar referencias A esa referencia
						else if (datos.length >= 4 && datos[3] instanceof ArchivoDeMod.Referencia) {
							ArchivoDeMod.Referencia referencia = (ArchivoDeMod.Referencia) datos[3];

							if (referencia.esMetodo()) {
								// Buscar referencias hacia este método referenciado
								List<Buscardor.ReferenciaMod> referenciasHacia = Buscardor.buscarReferenciasHaciaMetodo(
										referencia.obtenerClase(), referencia.obtenerNombre(),
										referencia.obtenerDescriptor());

								String tituloReferencias = MonitorDePID.idioma.referencias() + " "
										+ Buscardor.convertirFormatoClasePuntos(referencia.obtenerClase()) + "."
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

	/**
	 * Crea un icono a partir de una ruta de imagen, con un alternativo de texto si
	 * la imagen no se carga
	 */
	private ImageIcon crearIcono(String ruta, String textoAlternativo) {
		ImageIcon icono = new ImageIcon(ruta);
		// Si el icono no se cargó correctamente (imagen vacía), crear un alternativo
		if (icono.getIconWidth() <= 0) {
			// Crear una imagen simple con texto
			int tamano = 16;
			BufferedImage imagen = new BufferedImage(tamano, tamano, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = imagen.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Dibujar círculo de fondo
			g2d.setColor(new Color(200, 200, 200, 150));
			g2d.fillOval(0, 0, tamano - 1, tamano - 1);

			// Dibujar texto
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

	// Método auxiliar para buscar referencias a una clase específica
	private void buscarReferenciasAClase(String nombreClase) {
		if (!Buscardor.puedeAnalizarElContentidoDeClase()) {
			DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(
					MonitorDePID.idioma.referenciasClase() + " " + nombreClase);
			raiz.add(new DefaultMutableTreeNode("ASM no disponible"));
			modeloArbol = new DefaultTreeModel(raiz);
			arbolModulos.setModel(modeloArbol);
			return;
		}

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(
				MonitorDePID.idioma.referenciasClase() + " " + nombreClase);

		String claseInterna = Buscardor.convertirFormatoClase(nombreClase);
		List<Buscardor.ReferenciaMod> referencias = new ArrayList<>();

		// Buscar en todos los mods
		for (ArchivoDeMod mod : Buscardor.mods) {
			// Buscar en todas las clases del mod
			for (String nombreClaseActual : mod.obtenerTodosLosNombresDeClases()) {
				if (nombreClaseActual.equals(claseInterna))
					continue; // No buscar en la misma clase

				byte[] bytesClase = mod.obtenerBytesClase(nombreClaseActual);
				if (bytesClase != null) {
					try {
						// Buscar referencias en métodos
						List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(nombreClaseActual);
						for (ArchivoDeMod.InfoMetodo metodo : metodos) {
							// Verificar referencias en métodos
							for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasAMetodos()) {
								if (ref.obtenerClase().equals(claseInterna)) {
									String claseOrigenMostrar = Buscardor
											.convertirFormatoClasePuntos(nombreClaseActual);
									String textoRef = MonitorDePID.idioma.metodo() + ": " + claseOrigenMostrar + "."
											+ metodo.obtenerNombre() + metodo.obtenerDescriptor() + " -> "
											+ Buscardor.convertirFormatoClasePuntos(ref.obtenerClase()) + "."
											+ ref.obtenerNombre() + " (" + mod.ubicacion_para_publicar() + ")";
									raiz.add(new DefaultMutableTreeNode(textoRef));
								}
							}
							// Verificar referencias en campos
							for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
								if (ref.obtenerClase().equals(claseInterna)) {
									String claseOrigenMostrar = Buscardor
											.convertirFormatoClasePuntos(nombreClaseActual);
									String textoRef = MonitorDePID.idioma.metodo() + ": " + claseOrigenMostrar + "."
											+ metodo.obtenerNombre() + metodo.obtenerDescriptor() + " -> "
											+ Buscardor.convertirFormatoClasePuntos(ref.obtenerClase()) + "."
											+ ref.obtenerNombre() + " (" + mod.ubicacion_para_publicar() + ")";
									raiz.add(new DefaultMutableTreeNode(textoRef));
								}
							}
						}
					} catch (Throwable t) {
						// Continuar con el siguiente
					}
				}
			}
		}

		if (raiz.getChildCount() == 0) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
		}

		modeloArbol = new DefaultTreeModel(raiz);
		arbolModulos.setModel(modeloArbol);
	}

	// Método auxiliar para buscar referencias a un campo específico
	private void buscarReferenciasACampo(String claseObjetivo, String nombreCampo, String descriptorCampo) {
		if (!Buscardor.puedeAnalizarElContentidoDeClase()) {
			DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.referenciasCampo() + " "
					+ Buscardor.convertirFormatoClasePuntos(claseObjetivo) + "." + nombreCampo);
			raiz.add(new DefaultMutableTreeNode("ASM no disponible"));
			modeloArbol = new DefaultTreeModel(raiz);
			arbolModulos.setModel(modeloArbol);
			return;
		}

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.referenciasCampo() + " "
				+ Buscardor.convertirFormatoClasePuntos(claseObjetivo) + "." + nombreCampo);

		// Buscar en todos los mods
		for (ArchivoDeMod mod : Buscardor.mods) {
			// Buscar en todas las clases del mod
			for (String nombreClase : mod.obtenerTodosLosNombresDeClases()) {
				byte[] bytesClase = mod.obtenerBytesClase(nombreClase);
				if (bytesClase != null) {
					try {
						List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(nombreClase);
						for (ArchivoDeMod.InfoMetodo metodo : metodos) {
							// Buscar referencias a campos en este método
							for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
								if (ref.obtenerClase().equals(claseObjetivo) && ref.obtenerNombre().equals(nombreCampo)
										&& ref.obtenerDescriptor().equals(descriptorCampo)) {

									String claseOrigenMostrar = Buscardor.convertirFormatoClasePuntos(nombreClase);
									String textoRef = MonitorDePID.idioma.metodo() + ": " + claseOrigenMostrar + "."
											+ metodo.obtenerNombre() + metodo.obtenerDescriptor() + " ("
											+ mod.ubicacion_para_publicar() + ")";
									raiz.add(new DefaultMutableTreeNode(textoRef));
								}
							}
						}
					} catch (Throwable t) {
						// Continuar con el siguiente
					}
				}
			}
		}

		if (raiz.getChildCount() == 0) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
		}

		modeloArbol = new DefaultTreeModel(raiz);
		arbolModulos.setModel(modeloArbol);
	}

	private void mostrarReferenciasHaciaMetodo(List<Buscardor.ReferenciaMod> referencias, String titulo) {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(titulo);
		if (referencias.isEmpty()) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
		} else {
			for (Buscardor.ReferenciaMod refMod : referencias) {
				String textoReferencia = "";

				if (refMod.obtenerReferencia() != null) {
					// Referencia antigua (para compatibilidad)
					ArchivoDeMod.Referencia ref = refMod.obtenerReferencia();
					String nombreClaseMostrar = Buscardor.convertirFormatoClasePuntos(ref.obtenerClase());
					String tipo = ref.esMetodo() ? MonitorDePID.idioma.metodo() : MonitorDePID.idioma.campo();
					textoReferencia = tipo + ": " + nombreClaseMostrar + "." + ref.obtenerNombre() + " ("
							+ refMod.obtenerMod().ubicacion_para_publicar() + ")";
				}

				raiz.add(new DefaultMutableTreeNode(textoReferencia));
			}
		}
		modeloArbol = new DefaultTreeModel(raiz);
		arbolModulos.setModel(modeloArbol);
	}

	private void mostrarResultadosBusqueda(List<String> resultados, String titulo) {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(titulo);
		if (resultados.isEmpty()) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
		} else {
			for (String resultado : resultados) {
				raiz.add(new DefaultMutableTreeNode(resultado));
			}
		}
		modeloArbol = new DefaultTreeModel(raiz);
		arbolModulos.setModel(modeloArbol);
	}

	private void mostrarReferenciasMetodo(List<ArchivoDeMod.Referencia> referencias, String titulo) {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(titulo);
		if (referencias.isEmpty()) {
			raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronReferencias()));
		} else {
			for (ArchivoDeMod.Referencia ref : referencias) {
				String tipo = ref.esMetodo() ? MonitorDePID.idioma.metodo() : MonitorDePID.idioma.campo();
				String nombreClaseMostrar = Buscardor.convertirFormatoClasePuntos(ref.obtenerClase());
				raiz.add(new DefaultMutableTreeNode(
						tipo + ": " + ref.obtenerNombre() + " (" + nombreClaseMostrar + ")"));
			}
		}
		modeloArbol = new DefaultTreeModel(raiz);
		arbolModulos.setModel(modeloArbol);
	}

	/**
	 * Muestra los detalles del nodo seleccionado en el área de contenido. Este
	 * método maneja correctamente el formato de nombres de clases para ASM,
	 * asegurando que los métodos, campos y referencias se muestren adecuadamente.
	 * 
	 * @param nodo El nodo del árbol seleccionado por el usuario
	 */

	// Actualizar el método mostrarDetallesNodo para manejar referencias
	private void mostrarDetallesNodo(Object nodo) {
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

		if (objetoReal instanceof ArchivoDeMod) {
			ArchivoDeMod mod = (ArchivoDeMod) objetoReal;
			detalles.append(MonitorDePID.idioma.detalleMod()).append("\n").append(MonitorDePID.idioma.ubicacion())
					.append(": ").append(mod.ubicacion_para_publicar()).append("\n\n");

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
		} else if (objetoReal instanceof String) {
			String paquete = (String) objetoReal;
			detalles.append(MonitorDePID.idioma.paquete()).append(" ").append(paquete).append("\n");

			List<ArchivoDeMod> modsConPaquete = new ArrayList<>();
			for (ArchivoDeMod mod : Buscardor.mods) {
				for (String clase : mod.clases()) {
					String paqueteClase = "";
					int indiceUltimoPunto = clase.lastIndexOf('.');
					if (indiceUltimoPunto > 0)
						paqueteClase = clase.substring(0, indiceUltimoPunto);
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
					if (indiceUltimoPunto > 0)
						paqueteClase = clase.substring(0, indiceUltimoPunto);
					if (paqueteClase.equals(paquete)) {
						String nombreClase = clase.substring(indiceUltimoPunto + 1);
						detalles.append("  - ").append(nombreClase).append("\n");
					}
				}
			}
		} else if (objetoReal instanceof Object[]) {
			Object[] datos = (Object[]) objetoReal;
			if (datos.length >= 2 && datos[0] instanceof ArchivoDeMod) {
				ArchivoDeMod mod = (ArchivoDeMod) datos[0];
				String clase = (datos[1] instanceof String) ? (String) datos[1] : null;

				if (clase != null) {
					String claseInterna = convertirFormatoClase(clase);
					boolean claseExiste = mod.existeClase(claseInterna);

					// Detalle de clase
					if (datos.length == 2) {
						String nombreClase = clase;
						String paquete = "";
						int indicePunto = clase.lastIndexOf('.');
						if (indicePunto > 0) {
							paquete = clase.substring(0, indicePunto);
							nombreClase = clase.substring(indicePunto + 1);
						}

						detalles.append(MonitorDePID.idioma.detalleClase()).append(" ").append(nombreClase)
								.append("\n");
						if (!paquete.isEmpty()) {
							detalles.append(MonitorDePID.idioma.paquete()).append(": ").append(paquete).append("\n\n");
						}
						detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar())
								.append("\n\n");

						if (Buscardor.puedeAnalizarElContentidoDeClase() && claseExiste) {
							List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseInterna);
							detalles.append(MonitorDePID.idioma.metodos()).append(" (").append(metodos.size())
									.append("):\n");
							for (ArchivoDeMod.InfoMetodo metodo : metodos) {
								detalles.append("- ").append(metodo.obtenerNombre()).append(metodo.obtenerDescriptor())
										.append("\n");
							}
							detalles.append("\n");

							List<ArchivoDeMod.InfoCampo> campos = mod.obtenerCampos(claseInterna);
							detalles.append(MonitorDePID.idioma.campos()).append(" (").append(campos.size())
									.append("):\n");
							for (ArchivoDeMod.InfoCampo campo : campos) {
								detalles.append("- ").append(campo.obtenerNombre()).append(" ")
										.append(campo.obtenerDescriptor()).append("\n");
							}
						}
					}
					// Detalle de método
					else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoMetodo) {
						ArchivoDeMod.InfoMetodo metodo = (ArchivoDeMod.InfoMetodo) datos[2];
						String nombreClase = ((String) datos[1]);
						int indicePunto = nombreClase.lastIndexOf('.');
						if (indicePunto > 0)
							nombreClase = nombreClase.substring(indicePunto + 1);

						detalles.append(MonitorDePID.idioma.detalleMetodo()).append(" ").append(metodo.obtenerNombre())
								.append(metodo.obtenerDescriptor()).append("\n");
						detalles.append(MonitorDePID.idioma.clase()).append(": ").append(nombreClase).append("\n");
						detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar())
								.append("\n\n");

						// Referencias a métodos
						detalles.append(MonitorDePID.idioma.referenciasAMetodos()).append(" (")
								.append(metodo.obtenerReferenciasAMetodos().size()).append("):\n");
						for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasAMetodos()) {
							String nombreClaseRef = convertirFormatoClasePuntos(ref.obtenerClase());
							detalles.append("- ").append(nombreClaseRef).append(".").append(ref.obtenerNombre())
									.append(ref.obtenerDescriptor()).append("\n");
						}
						detalles.append("\n");

						// Referencias a campos
						detalles.append(MonitorDePID.idioma.referenciasACampos()).append(" (")
								.append(metodo.obtenerReferenciasACampos().size()).append("):\n");
						for (ArchivoDeMod.Referencia ref : metodo.obtenerReferenciasACampos()) {
							String nombreClaseRef = convertirFormatoClasePuntos(ref.obtenerClase());
							detalles.append("- ").append(nombreClaseRef).append(".").append(ref.obtenerNombre())
									.append(" ").append(ref.obtenerDescriptor()).append("\n");
						}
						detalles.append("\n");

						// === Constantes del método ===
						List<ArchivoDeMod.Constante> constantes = mod.buscarConstantesEnMetodo(
								convertirFormatoClase((String) datos[1]), metodo.obtenerNombre(),
								metodo.obtenerDescriptor());
						detalles.append("Constantes (").append(String.valueOf(constantes.size())).append("):\n");
						for (ArchivoDeMod.Constante k : constantes) {
							detalles.append("- ").append(formatearConstante(k)).append("\n");
						}
					}
					// Detalle de campo
					else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoCampo) {
						ArchivoDeMod.InfoCampo campo = (ArchivoDeMod.InfoCampo) datos[2];
						String nombreClase = ((String) datos[1]);
						int indicePunto = nombreClase.lastIndexOf('.');
						if (indicePunto > 0)
							nombreClase = nombreClase.substring(indicePunto + 1);

						detalles.append(MonitorDePID.idioma.detalleCampo()).append(" ").append(campo.obtenerNombre())
								.append("\n");
						detalles.append(MonitorDePID.idioma.clase()).append(": ").append(nombreClase).append("\n");
						detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar())
								.append("\n");
						detalles.append(MonitorDePID.idioma.tipo()).append(": ").append(campo.obtenerDescriptor())
								.append("\n");
					}
					// Detalle de referencia específica
					else if (datos.length >= 4 && datos[3] instanceof ArchivoDeMod.Referencia) {
						ArchivoDeMod.InfoMetodo metodo = (ArchivoDeMod.InfoMetodo) datos[2];
						ArchivoDeMod.Referencia referencia = (ArchivoDeMod.Referencia) datos[3];
						String nombreClase = ((String) datos[1]);
						int indicePunto = nombreClase.lastIndexOf('.');
						if (indicePunto > 0)
							nombreClase = nombreClase.substring(indicePunto + 1);

						String tipoReferencia = referencia.esMetodo() ? MonitorDePID.idioma.metodo()
								: MonitorDePID.idioma.campo();
						String claseReferencia = convertirFormatoClasePuntos(referencia.obtenerClase());

						detalles.append(MonitorDePID.idioma.detalleMetodo()).append(" ").append(tipoReferencia)
								.append("\n");
						detalles.append(MonitorDePID.idioma.nombres()).append(": ").append(referencia.obtenerNombre())
								.append("\n");
						detalles.append("desc").append(": ").append(referencia.obtenerDescriptor()).append("\n");
						detalles.append(MonitorDePID.idioma.clase()).append(": ").append(claseReferencia)
								.append("\n\n");

						detalles.append(MonitorDePID.idioma.referencias()).append(":\n");
						detalles.append(MonitorDePID.idioma.metodo()).append(": ").append(nombreClase).append(".")
								.append(metodo.obtenerNombre()).append(metodo.obtenerDescriptor()).append("\n");
						detalles.append(MonitorDePID.idioma.modulo()).append(": ").append(mod.ubicacion_para_publicar())
								.append("\n");
					}
				}
			}
		}

		areaContenido.setText(detalles.toString());
	}

	/**
	 * Convierte un nombre de clase del formato de puntos (java.lang.Object) al
	 * formato interno de ASM (java/lang/Object)
	 */
	private String convertirFormatoClase(String nombreClase) {
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
	private String convertirFormatoClasePuntos(String nombreClase) {
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

	// Renderizador personalizado para el árbol
	// Renderizador personalizado para el árbol con iconos específicos para cada
	// tipo de nodo
	private class RenderizadorCeldasArbol extends DefaultTreeCellRenderer {

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
						if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoMetodo) {
							setForeground(new Color(0, 0, 153));
							setIcon(iconoMetodo);
						} else if (datos.length >= 3 && datos[2] instanceof ArchivoDeMod.InfoCampo) {
							setForeground(new Color(153, 0, 0));
							setIcon(iconoCampo);
						} else if (datos.length >= 4 && datos[3] instanceof ArchivoDeMod.Referencia) {
							ArchivoDeMod.Referencia ref = (ArchivoDeMod.Referencia) datos[3];
							setIcon(ref.esMetodo() ? iconoReferenciaMetodo : iconoReferenciaCampo);
							setForeground(Color.DARK_GRAY);
						} else {
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

	// Inicializa el overlay de carga (glass pane)
	private void initOverlayCarga() {
		overlayCarga = new JPanel(new FlowLayout(FlowLayout.CENTER));
		overlayCarga.setOpaque(false); // fondo transparente

		// Panel interno con fondo semitransparente
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.setBackground(new Color(0, 0, 0, 120));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Intenta cargar el GIF
		ImageIcon icon = new ImageIcon("crash_detector/imagenes/padoru.gif");
		if (icon.getIconWidth() <= 0) {
			// Fallback visual si no existe el GIF
			icon = crearIcono("", "…");
		}
		gifCarga = new JLabel(icon);
		gifCarga.setText(MonitorDePID.idioma.cargando()); // opcional: texto “Cargando…”
		gifCarga.setHorizontalTextPosition(JLabel.CENTER);
		gifCarga.setVerticalTextPosition(JLabel.BOTTOM);
		gifCarga.setForeground(Color.WHITE);
		gifCarga.setFont(gifCarga.getFont().deriveFont(Font.BOLD, 14f));

		panel.add(gifCarga);
		overlayCarga.add(panel);

		// Usa el glass pane de la ventana para superponerlo
		getRootPane().setGlassPane(overlayCarga);
	}

	// Muestra/oculta el overlay de carga
	private void setCargando(boolean on) {
		cargando = on;
		overlayCarga.setVisible(on);
		overlayCarga.setOpaque(false);
		overlayCarga.revalidate();
		overlayCarga.repaint();
	}

	// Construye el árbol en un hilo de fondo
	private void construirArbolInicialAsync() {
		// Si había un worker anterior, cancelarlo
		if (workerConstruir != null && !workerConstruir.isDone()) {
			workerConstruir.cancel(true);
		}

		setCargando(true);

		workerConstruir = new SwingWorker<DefaultMutableTreeNode, Void>() {
			@Override
			protected DefaultMutableTreeNode doInBackground() {
				// NO tocar componentes Swing aquí
				DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.modsCargados());

				// Construcción pesada fuera del EDT
				for (ArchivoDeMod mod : Buscardor.mods) {
					if (isCancelled())
						break;
					DefaultMutableTreeNode nodoModulo = construirNodoModulo(mod);
					raiz.add(nodoModulo);
				}
				return raiz;
			}

			@Override
			protected void done() {
				try {
					if (isCancelled())
						return;
					DefaultMutableTreeNode raiz = get();
					// Sí tocar Swing aquí (EDT)
					modeloArbol = new DefaultTreeModel(raiz);
					arbolModulos.setModel(modeloArbol);
				} catch (Exception ex) {
					// Opcional: log / mensaje
				} finally {
					setCargando(false);
				}
			}
		};

		workerConstruir.execute();
	}

	private DefaultMutableTreeNode construirNodoModulo(ArchivoDeMod mod) {
		String ubicacionPublica = mod.ubicacion_para_publicar();
		DefaultMutableTreeNode nodoModulo = new DefaultMutableTreeNode(new NodoConTexto(ubicacionPublica, mod));

		// Submods
		for (ArchivoDeMod submod : mod.mods_en_mods()) {
			nodoModulo.add(construirNodoModulo(submod));
		}

		// Paquetes y clases
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
					String claseInterna = Buscardor.convertirFormatoClase(clasePuntos);

					DefaultMutableTreeNode nodoClase = new DefaultMutableTreeNode(
							new NodoConTexto(nombreClase, new Object[] { mod, clasePuntos }));

					if (Buscardor.puedeAnalizarElContentidoDeClase() && mod.existeClase(claseInterna)) {
						// Métodos
						List<ArchivoDeMod.InfoMetodo> metodos = mod.obtenerMetodosConReferencias(claseInterna);
						if (!metodos.isEmpty()) {
							DefaultMutableTreeNode nodoMetodos = new DefaultMutableTreeNode(new NodoConTexto(
									MonitorDePID.idioma.metodos() + " (" + metodos.size() + ")", "contenedor_metodos"));
							for (ArchivoDeMod.InfoMetodo metodo : metodos) {
								DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(
										new NodoConTexto(metodo.obtenerNombre() + metodo.obtenerDescriptor(),
												new Object[] { mod, clasePuntos, metodo }));

								// === Constantes del método ===
								List<ArchivoDeMod.Constante> constantes = mod.buscarConstantesEnMetodo(
										Buscardor.convertirFormatoClase(clasePuntos), metodo.obtenerNombre(),
										metodo.obtenerDescriptor());
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

								// Referencias
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
										String nombreClaseMostrar = Buscardor
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
										String nombreClaseMostrar = Buscardor
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

						// Campos
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
		return nodoModulo;
	}

	private void filtrarArbolAsync() {
		final String filtro = campoBuscar.getText().trim().toLowerCase();
		final String tipoFiltro = (String) comboFiltro.getSelectedItem();

		// Si cadena vacía, reconstruir árbol completo
		if (filtro.isEmpty()) {
			construirArbolInicialAsync();
			return;
		}

		// Cancela búsqueda anterior si existe
		if (workerBuscar != null && !workerBuscar.isDone()) {
			workerBuscar.cancel(true);
		}

		setCargando(true);

		workerBuscar = new SwingWorker<DefaultMutableTreeNode, Void>() {
			@Override
			protected DefaultMutableTreeNode doInBackground() {
				DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(MonitorDePID.idioma.resultadosBusqueda());
				boolean encontrado = false;

				for (ArchivoDeMod mod : Buscardor.mods) {
					if (isCancelled())
						break;
					DefaultMutableTreeNode nodoModulo = new DefaultMutableTreeNode(
							new NodoConTexto(mod.ubicacion_para_publicar(), mod));
					boolean agregar = buscarEnModuloBG(nodoModulo, mod, filtro, tipoFiltro, this);
					if (agregar) {
						raiz.add(nodoModulo);
						encontrado = true;
					}
				}

				if (!encontrado) {
					raiz.add(new DefaultMutableTreeNode(MonitorDePID.idioma.noSeEncontraronResultados()));
				}
				return raiz;
			}

			@Override
			protected void done() {
				try {
					if (isCancelled())
						return;
					DefaultMutableTreeNode raiz = get();
					modeloArbol = new DefaultTreeModel(raiz);
					arbolModulos.setModel(modeloArbol);
				} catch (Exception ex) {
					// Opcional: log / UI
				} finally {
					setCargando(false);
				}
			}
		};

		workerBuscar.execute();
	}

	// Versión “de fondo” de buscarEnModulo: NO tocar Swing dentro
	private boolean buscarEnModuloBG(DefaultMutableTreeNode padre, ArchivoDeMod mod, String filtro, String tipoFiltro,
			SwingWorker<?, ?> self) {
		if (self.isCancelled())
			return false;

		boolean algoEnModulo = false;
		String filtroLower = (filtro == null) ? "" : filtro.toLowerCase();

		DefaultMutableTreeNode nodoModulo = new DefaultMutableTreeNode(
				new NodoConTexto(mod.ubicacion_para_publicar(), mod));

		if (mod.ubicacion_para_publicar().toLowerCase().contains(filtroLower)) {
			algoEnModulo = true;
		}

// Submods
		for (ArchivoDeMod submod : mod.mods_en_mods()) {
			if (self.isCancelled())
				break;
			DefaultMutableTreeNode subNodo = new DefaultMutableTreeNode(
					new NodoConTexto(submod.ubicacion_para_publicar(), submod));
			if (buscarEnModuloBG(subNodo, submod, filtroLower, tipoFiltro, self)) {
				nodoModulo.add(subNodo);
				algoEnModulo = true;
			}
		}

// Clases del mod
		for (String clasePuntos : mod.clases()) {
			if (self.isCancelled())
				break;

			String paquete = paqueteDe(clasePuntos);
			String claseInterna = Buscardor.convertirFormatoClase(clasePuntos);

			boolean incluirPorPaquete = ("*".equals(tipoFiltro)
					|| MonitorDePID.idioma.filtroPaquetes().equals(tipoFiltro))
					&& paquete.toLowerCase().contains(filtroLower);
			boolean incluirPorClase = ("*".equals(tipoFiltro) || MonitorDePID.idioma.filtroClases().equals(tipoFiltro))
					&& clasePuntos.toLowerCase().contains(filtroLower);

			boolean puedeASM = Buscardor.puedeAnalizarElContentidoDeClase() && mod.existeClase(claseInterna);
			List<ArchivoDeMod.InfoMetodo> metodos = puedeASM ? mod.obtenerMetodosConReferencias(claseInterna)
					: new ArrayList<>();
			List<ArchivoDeMod.InfoCampo> campos = puedeASM ? mod.obtenerCampos(claseInterna) : new ArrayList<>();

			boolean hayAlgoEnEstaClase = false;

			if (incluirPorPaquete || incluirPorClase) {
				DefaultMutableTreeNode nodoPaquete = asegurarNodoPaquete(nodoModulo, paquete);
				DefaultMutableTreeNode nodoClase = asegurarNodoClase(nodoPaquete, mod, clasePuntos);

				if (puedeASM) {
					if (!metodos.isEmpty()) {
						DefaultMutableTreeNode contMetodos = asegurarNodoContenedor(nodoClase, "contenedor_metodos",
								MonitorDePID.idioma.metodos() + " (" + metodos.size() + ")");
						for (ArchivoDeMod.InfoMetodo m : metodos) {
							if (self.isCancelled())
								break;
							DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(new NodoConTexto(
									m.obtenerNombre() + m.obtenerDescriptor(), new Object[] { mod, clasePuntos, m }));
							agregarReferenciasYConstantes(nodoMetodo, mod, clasePuntos, m, null, tipoFiltro);
							contMetodos.add(nodoMetodo);
						}
					}
					if (!campos.isEmpty()) {
						DefaultMutableTreeNode contCampos = asegurarNodoContenedor(nodoClase, "contenedor_campos",
								MonitorDePID.idioma.campos() + " (" + campos.size() + ")");
						for (ArchivoDeMod.InfoCampo f : campos) {
							if (self.isCancelled())
								break;
							contCampos.add(new DefaultMutableTreeNode(
									new NodoConTexto(f.obtenerNombre() + " " + f.obtenerDescriptor(),
											new Object[] { mod, clasePuntos, f })));
						}
					}
				}
				hayAlgoEnEstaClase = true;
			} else {
// Selectivo por métodos/constantes
				if (puedeASM && ("*".equals(tipoFiltro) || MonitorDePID.idioma.filtroMetodos().equals(tipoFiltro)
						|| "Constantes".equals(tipoFiltro))) {
					DefaultMutableTreeNode nodoPaquete = null, nodoClase = null, contMetodos = null;

					for (ArchivoDeMod.InfoMetodo m : metodos) {
						if (self.isCancelled())
							break;

						String nombreMetodoLower = (m.obtenerNombre() + m.obtenerDescriptor()).toLowerCase();
						boolean coincideMetodo = ("*".equals(tipoFiltro)
								|| MonitorDePID.idioma.filtroMetodos().equals(tipoFiltro))
								&& nombreMetodoLower.contains(filtroLower);

						boolean hayConstanteCoincidente = false;
						if ("Constantes".equals(tipoFiltro) || "*".equals(tipoFiltro)) {
							List<ArchivoDeMod.Constante> consts = mod.buscarConstantesEnMetodo(claseInterna,
									m.obtenerNombre(), m.obtenerDescriptor());
							for (ArchivoDeMod.Constante k : consts) {
								if (constanteCoincide(k, filtroLower)) {
									hayConstanteCoincidente = true;
									break;
								}
							}
						}

						if (coincideMetodo || hayConstanteCoincidente) {
							if (nodoPaquete == null)
								nodoPaquete = asegurarNodoPaquete(nodoModulo, paquete);
							if (nodoClase == null)
								nodoClase = asegurarNodoClase(nodoPaquete, mod, clasePuntos);
							if (contMetodos == null)
								contMetodos = asegurarNodoContenedor(nodoClase, "contenedor_metodos",
										MonitorDePID.idioma.metodos());

							DefaultMutableTreeNode nodoMetodo = new DefaultMutableTreeNode(new NodoConTexto(
									m.obtenerNombre() + m.obtenerDescriptor(), new Object[] { mod, clasePuntos, m }));
							agregarReferenciasYConstantes(nodoMetodo, mod, clasePuntos, m,
									"Constantes".equals(tipoFiltro) ? filtroLower : null, tipoFiltro);
							contMetodos.add(nodoMetodo);
							hayAlgoEnEstaClase = true;
						}
					}
				}

// Selectivo por campos
				if (puedeASM && ("*".equals(tipoFiltro) || MonitorDePID.idioma.filtroCampos().equals(tipoFiltro))) {
					DefaultMutableTreeNode nodoPaquete = null, nodoClase = null, contCampos = null;

					for (ArchivoDeMod.InfoCampo f : campos) {
						if (self.isCancelled())
							break;
						String textoCampoLower = (f.obtenerNombre() + " " + f.obtenerDescriptor()).toLowerCase();
						if (textoCampoLower.contains(filtroLower)) {
							if (nodoPaquete == null)
								nodoPaquete = asegurarNodoPaquete(nodoModulo, paquete);
							if (nodoClase == null)
								nodoClase = asegurarNodoClase(nodoPaquete, mod, clasePuntos);
							if (contCampos == null)
								contCampos = asegurarNodoContenedor(nodoClase, "contenedor_campos",
										MonitorDePID.idioma.campos());

							contCampos.add(new DefaultMutableTreeNode(
									new NodoConTexto(f.obtenerNombre() + " " + f.obtenerDescriptor(),
											new Object[] { mod, clasePuntos, f })));
							hayAlgoEnEstaClase = true;
						}
					}
				}
			}

			if (hayAlgoEnEstaClase)
				algoEnModulo = true;
		}

		if (algoEnModulo) {
			padre.add(nodoModulo);
		}
		return algoEnModulo;
	}

	private String formatearConstante(ArchivoDeMod.Constante c) {
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

	private boolean constanteCoincide(ArchivoDeMod.Constante c, String filtroLower) {
		return formatearConstante(c).toLowerCase().contains(filtroLower);
	}

	// Inserta "Constantes" bajo un nodo de método si aún no existen
	private void asegurarNodoConstantesPara(DefaultMutableTreeNode nodoMetodo) {
		Object uo = nodoMetodo.getUserObject();
		if (!(uo instanceof NodoConTexto))
			return;
		Object payload = ((NodoConTexto) uo).objeto();
		if (!(payload instanceof Object[]))
			return;

		Object[] datos = (Object[]) payload;

		// Solo método: exactamente 3 elementos (mod, clasePuntos, InfoMetodo)
		if (datos.length != 3 || !(datos[0] instanceof ArchivoDeMod) || !(datos[1] instanceof String)
				|| !(datos[2] instanceof ArchivoDeMod.InfoMetodo)) {
			return;
		}

		ArchivoDeMod mod = (ArchivoDeMod) datos[0];
		String clasePuntos = (String) datos[1];
		ArchivoDeMod.InfoMetodo metodo = (ArchivoDeMod.InfoMetodo) datos[2];

		// ¿Ya existe contenedor?
		for (int i = 0; i < nodoMetodo.getChildCount(); i++) {
			Object uoH = ((DefaultMutableTreeNode) nodoMetodo.getChildAt(i)).getUserObject();
			if (uoH instanceof NodoConTexto && "contenedor_constantes".equals(((NodoConTexto) uoH).objeto())) {
				return;
			}
		}

		List<ArchivoDeMod.Constante> constantes = mod.buscarConstantesEnMetodo(convertirFormatoClase(clasePuntos),
				metodo.obtenerNombre(), metodo.obtenerDescriptor());
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

	// Devuelve el paquete (parte antes del último punto) o "" si no hay.
	private String paqueteDe(String clasePuntos) {
		int i = clasePuntos.lastIndexOf('.');
		return (i > 0) ? clasePuntos.substring(0, i) : "";
	}

	// Devuelve el nombre simple de la clase (parte después del último punto).
	private String nombreSimpleDe(String clasePuntos) {
		int i = clasePuntos.lastIndexOf('.');
		return (i > 0) ? clasePuntos.substring(i + 1) : clasePuntos;
	}

	// Asegura y retorna el nodo de paquete bajo un nodo de módulo.
	private DefaultMutableTreeNode asegurarNodoPaquete(DefaultMutableTreeNode nodoModulo, String paquete) {
		for (int i = 0; i < nodoModulo.getChildCount(); i++) {
			DefaultMutableTreeNode hijo = (DefaultMutableTreeNode) nodoModulo.getChildAt(i);
			Object uo = hijo.getUserObject();
			if (uo instanceof NodoConTexto) {
				Object obj = ((NodoConTexto) uo).objeto();
				if (obj instanceof String && ((String) obj).equals(paquete))
					return hijo;
			}
		}
		String etiqueta = paquete.isEmpty() ? "(paquete por defecto)" : paquete;
		DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(new NodoConTexto(etiqueta, paquete));
		nodoModulo.add(nuevo);
		return nuevo;
	}

	// Asegura y retorna el nodo de clase bajo un nodo de paquete.
	private DefaultMutableTreeNode asegurarNodoClase(DefaultMutableTreeNode nodoPaquete, ArchivoDeMod mod,
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

	// Asegura y retorna un contenedor (métodos/campos/referencias/constantes) bajo
	// el padre.
	private DefaultMutableTreeNode asegurarNodoContenedor(DefaultMutableTreeNode padre, String clave, String etiqueta) {
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

	// Agrega referencias y constantes bajo un nodo de método.
	private void agregarReferenciasYConstantes(DefaultMutableTreeNode nodoMetodo, ArchivoDeMod mod, String clasePuntos,
			ArchivoDeMod.InfoMetodo metodo, String filtroLower, String tipoFiltro) {
		// --- Referencias ---
		if (!metodo.obtenerReferenciasAMetodos().isEmpty() || !metodo.obtenerReferenciasACampos().isEmpty()) {
			DefaultMutableTreeNode nodoReferencias = new DefaultMutableTreeNode(
					new NodoConTexto(
							MonitorDePID.idioma.referencias() + " ("
									+ (metodo.obtenerReferenciasAMetodos().size()
											+ metodo.obtenerReferenciasACampos().size())
									+ ")",
							"contenedor_referencias"));

			// A métodos
			for (ArchivoDeMod.Referencia refMetodo : metodo.obtenerReferenciasAMetodos()) {
				String claseMostrar = Buscardor.convertirFormatoClasePuntos(refMetodo.obtenerClase());
				nodoReferencias.add(new DefaultMutableTreeNode(new NodoConTexto(
						MonitorDePID.idioma.metodo() + ": " + claseMostrar + "." + refMetodo.obtenerNombre()
								+ refMetodo.obtenerDescriptor(),
						new Object[] { mod, clasePuntos, metodo, refMetodo })));
			}
			// A campos
			for (ArchivoDeMod.Referencia refCampo : metodo.obtenerReferenciasACampos()) {
				String claseMostrar = Buscardor.convertirFormatoClasePuntos(refCampo.obtenerClase());
				nodoReferencias
						.add(new DefaultMutableTreeNode(new NodoConTexto(
								MonitorDePID.idioma.campo() + ": " + claseMostrar + "." + refCampo.obtenerNombre() + " "
										+ refCampo.obtenerDescriptor(),
								new Object[] { mod, clasePuntos, metodo, refCampo })));
			}
			nodoMetodo.add(nodoReferencias);
		}

		// --- Constantes ---
		List<ArchivoDeMod.Constante> constantes = mod.buscarConstantesEnMetodo(
				Buscardor.convertirFormatoClase(clasePuntos), metodo.obtenerNombre(), metodo.obtenerDescriptor());

		if (constantes != null && !constantes.isEmpty()) {
			DefaultMutableTreeNode nodoConstantes = new DefaultMutableTreeNode(
					new NodoConTexto("Constantes (" + constantes.size() + ")", "contenedor_constantes"));

			for (ArchivoDeMod.Constante k : constantes) {
				// Si el filtro seleccionado es “Constantes”, mostramos solo las que matchean
				if ("Constantes".equals(tipoFiltro) && filtroLower != null && !constanteCoincide(k, filtroLower)) {
					continue;
				}
				nodoConstantes.add(new DefaultMutableTreeNode(
						new NodoConTexto(formatearConstante(k), new Object[] { mod, clasePuntos, metodo, k })));
			}

			if (nodoConstantes.getChildCount() > 0) {
				nodoMetodo.add(nodoConstantes);
			}
		}
	}

	// Nodo de árbol con texto fijo + objeto asociado
	private static class NodoConTexto {
		private final String texto;
		private final Object objeto;

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
			return texto; // siempre muestra el texto esperado
		}
	}
}