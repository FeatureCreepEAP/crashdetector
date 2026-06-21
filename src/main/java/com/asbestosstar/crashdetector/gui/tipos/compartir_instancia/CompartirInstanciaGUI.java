package com.asbestosstar.crashdetector.gui.tipos.compartir_instancia;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import com.asbestosstar.crashdetector.api_sitio_archivo.SitioDeArchivoAPI;
import com.asbestosstar.crashdetector.api_sitio_archivo.SitioDeArchivoAPI.ObservadorDeTransferencia;
import com.asbestosstar.crashdetector.api_sitio_archivo.WormholeApp;
import com.asbestosstar.crashdetector.dto.modpack.CopiaDeSeguridadDeArchivos;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.ElementoOverlayCarga;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.modapi.PanelAPIBase;

/**
 * GUI abstracta para compartir la instancia completa o una selección de
 * carpetas.
 *
 * Reglas: - Solo se listan rutas dentro de la carpeta actual y sus subrutas. -
 * Se permite además Statics.CARPETA como raíz explícita si existe. - El árbol
 * usa selección triestado: - check = todo seleccionado - cuadrado = selección
 * parcial - vacío = no seleccionado
 *
 * Nota: - wormhole.app queda registrado aquí como servicio integrado por
 * defecto. - otros servicios pueden registrarse desde extensiones.
 */
public abstract class CompartirInstanciaGUI extends JFrame implements CrashDetectorGUI {

	public static final Map<String, Supplier<CompartirInstanciaGUI>> GUIS = new HashMap<>();

	static {
		// Ojo: la implementación existente en tu código se llama WormHoleApp.
		SitioDeArchivoAPI.SERVICIOS_REGISTRADOS.put("wormhole.app", new WormholeApp());
	}

	public JTextArea areaPolitica;
	public JTree arbolRutas;
	public DefaultTreeModel modeloArbol;
	public JComboBox<String> comboFormato;
	public JComboBox<String> comboServicio;
	public JButton botonCompartir;
	public JButton botonRefrescar;
	public JLabel etiquetaEstado;
	public JButton botonExportar;

	public SwingWorker<Void, Void> workerCompartir;

	public Path carpetaBase;
	public Set<Path> seleccionadas = new LinkedHashSet<>();

	public ElementoOverlayCarga overlayCarga;
	public String textoCargaActual = "";
	public volatile boolean cargando = false;

	@Override
	public TipoGUI<CompartirInstanciaGUI> tipo() {
		return TipoGUI.COMPARTIR_INSTANCIA;
	}

	@Override
	public void init() {
		setTitle(MonitorDePID.idioma.compartirInstanciaTitulo());
		setSize(980, 760);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout());

		carpetaBase = Paths.get(".").toAbsolutePath().normalize();

		JPanel panelSuperior = new JPanel(new BorderLayout(8, 8));
		panelSuperior.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		areaPolitica = new JTextArea();
		areaPolitica.setEditable(false);
		areaPolitica.setLineWrap(true);
		areaPolitica.setWrapStyleWord(true);
		areaPolitica.setFont(new Font("SansSerif", Font.PLAIN, 12));
		areaPolitica.setText(construirTextoPolitica());

		panelSuperior.add(new JScrollPane(areaPolitica), BorderLayout.CENTER);

		DefaultMutableTreeNode raiz = construirArbolSeleccion();
		modeloArbol = new DefaultTreeModel(raiz);
		arbolRutas = new JTree(modeloArbol);
		arbolRutas.setRootVisible(false);
		arbolRutas.setShowsRootHandles(true);
		arbolRutas.setCellRenderer(new RenderizadorTriestado());

		arbolRutas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				TreePath path = arbolRutas.getPathForLocation(e.getX(), e.getY());
				if (path == null) {
					return;
				}

				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) path.getLastPathComponent();
				Object user = nodo.getUserObject();
				if (!(user instanceof NodoRutaSeleccionable)) {
					return;
				}

				NodoRutaSeleccionable dato = (NodoRutaSeleccionable) user;
				boolean nuevoValor = dato.estado != EstadoSeleccion.PARCIAL
						? dato.estado != EstadoSeleccion.SELECCIONADO
						: true;

				establecerSeleccionRecursiva(nodo, nuevoValor);
				actualizarPadres((DefaultMutableTreeNode) nodo.getParent());

				modeloArbol.nodeStructureChanged((DefaultMutableTreeNode) modeloArbol.getRoot());
				arbolRutas.expandPath(path);
			}
		});

		JPanel panelCentro = new JPanel(new BorderLayout(8, 8));
		panelCentro.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
		panelCentro.add(new JScrollPane(arbolRutas), BorderLayout.CENTER);

		JPanel panelInferior = new JPanel(new BorderLayout(8, 8));
		panelInferior.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JPanel filaCombos = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));

		comboFormato = new JComboBox<>();
		comboServicio = new JComboBox<>();

		recargarFormatosExportables();
		recargarServicios();

		filaCombos.add(new JLabel(MonitorDePID.idioma.compartirInstanciaFormato() + ":"));
		filaCombos.add(comboFormato);
		filaCombos.add(new JLabel(MonitorDePID.idioma.compartirInstanciaServicio() + ":"));
		filaCombos.add(comboServicio);

		JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
		botonCompartir = new JButton(MonitorDePID.idioma.compartirInstanciaBotonCompartir());
		botonRefrescar = new JButton(MonitorDePID.idioma.compartirInstanciaBotonRefrescar());
		etiquetaEstado = new JLabel(MonitorDePID.idioma.compartirInstanciaEstadoListo());
		botonExportar = new JButton(MonitorDePID.idioma.guardar_como_archivo());
		botonExportar.addActionListener(e -> exportarSeleccionAUbicacion());

		filaBotones.add(botonCompartir);
		filaBotones.add(botonExportar);
		filaBotones.add(botonRefrescar);
		filaBotones.add(etiquetaEstado);

		panelInferior.add(filaCombos, BorderLayout.NORTH);
		panelInferior.add(filaBotones, BorderLayout.SOUTH);

		add(panelSuperior, BorderLayout.NORTH);
		add(panelCentro, BorderLayout.CENTER);
		add(panelInferior, BorderLayout.SOUTH);

		botonRefrescar.addActionListener(e -> refrescarArbol());
		botonCompartir.addActionListener(e -> compartirSeleccion());

		recargarApariencia();
		initOverlayCarga();
		setCargando(false);
		setVisible(true);
	}

	public void exportarSeleccionAUbicacion() {
		List<Path> rutas = obtenerSeleccionFinal();

		if (rutas.isEmpty()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.compartirInstanciaSinSeleccion(),
					MonitorDePID.idioma.error(), JOptionPane.WARNING_MESSAGE);
			return;
		}

		ProveedorMods proveedor = obtenerProveedorFormatoSeleccionado();

		if (proveedor == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.compartirInstanciaFormatoNoSoportado(),
					MonitorDePID.idioma.error(), JOptionPane.WARNING_MESSAGE);
			return;
		}

		String extension = CopiaDeSeguridadDeArchivos.obtenerExtensionProveedor(proveedor);
		String nombreSugerido = CopiaDeSeguridadDeArchivos.crearNombreArchivoBackup(carpetaBase, extension);

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(MonitorDePID.idioma.exportar_modpack());
		chooser.setSelectedFile(new java.io.File(nombreSugerido));

		int resultado = chooser.showSaveDialog(this);

		if (resultado != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path destino = chooser.getSelectedFile().toPath();

		setTextoCarga(MonitorDePID.idioma.exportando_modpack());
		setCargando(true);

		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				proveedor.exportarModpack(destino, rutas);
				return null;
			}

			@Override
			protected void done() {
				try {
					get();

					JOptionPane.showMessageDialog(CompartirInstanciaGUI.this,
							MonitorDePID.idioma.modpack_exportado() + destino, MonitorDePID.idioma.informacion(),
							JOptionPane.INFORMATION_MESSAGE);

					etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEstadoListo());
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);

					JOptionPane.showMessageDialog(CompartirInstanciaGUI.this,
							MonitorDePID.idioma.error_exportando_modpack() + t.getMessage(),
							MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);

					etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEstadoError());
				} finally {
					setCargando(false);
				}
			}
		}.execute();
	}

	public void recargarServicios() {
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();

		for (String nombre : SitioDeArchivoAPI.SERVICIOS_REGISTRADOS.keySet()) {
			modelo.addElement(nombre);
		}

		comboServicio.setModel(modelo);
	}

	public void refrescarArbol() {
		DefaultMutableTreeNode raiz = construirArbolSeleccion();
		modeloArbol.setRoot(raiz);
		modeloArbol.reload();
	}

	public void recargarFormatosExportables() {
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();

		for (Map.Entry<String, Supplier<ProveedorMods>> entrada : PanelAPIBase.PROVEEDORES_MODS.entrySet()) {
			try {
				ProveedorMods proveedor = entrada.getValue().get();

				if (proveedor != null && proveedor.soportaExportarModpack()) {
					modelo.addElement(entrada.getKey());
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}

		comboFormato.setModel(modelo);
	}

	public ProveedorMods obtenerProveedorFormatoSeleccionado() {
		String formato = (String) comboFormato.getSelectedItem();

		if (formato == null) {
			return null;
		}

		Supplier<ProveedorMods> supplier = PanelAPIBase.PROVEEDORES_MODS.get(formato);

		if (supplier == null) {
			return null;
		}

		ProveedorMods proveedor = supplier.get();

		if (proveedor == null || !proveedor.soportaExportarModpack()) {
			return null;
		}

		return proveedor;
	}

	public String construirTextoPolitica() {
		StringBuilder sb = new StringBuilder();

		sb.append(MonitorDePID.idioma.compartirInstanciaPolitica1()).append("\n\n");
		sb.append(MonitorDePID.idioma.compartirInstanciaPolitica2()).append("\n");
		sb.append(MonitorDePID.idioma.compartirInstanciaPolitica3()).append("\n");
		sb.append(MonitorDePID.idioma.compartirInstanciaPolitica4()).append("\n\n");
		sb.append(MonitorDePID.idioma.compartirInstanciaPolitica5()).append("\n");
		sb.append(MonitorDePID.idioma.compartirInstanciaPolitica6()).append("\n");
		sb.append(MonitorDePID.idioma.compartirInstanciaPolitica7()).append("\n");

		return sb.toString();
	}

	public DefaultMutableTreeNode construirArbolSeleccion() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("ROOT");

		for (Path ruta : obtenerRutasPredeterminadas()) {
			DefaultMutableTreeNode nodo = crearNodoRecursivoSiExiste(ruta);
			if (nodo != null) {
				raiz.add(nodo);
			}
		}

		return raiz;
	}

	public List<Path> obtenerRutasPredeterminadas() {
		List<Path> ret = new ArrayList<>();

		agregarSiExiste(ret, carpetaBase.resolve("mods"));
		agregarSiExiste(ret, carpetaBase.resolve("config"));
		agregarSiExiste(ret, carpetaBase.resolve("etc"));
		agregarSiExiste(ret, carpetaBase.resolve("gfx"));
		agregarSiExiste(ret, carpetaBase.resolve("history"));
		agregarSiExiste(ret, carpetaBase.resolve("addons"));
		agregarSiExiste(ret, carpetaBase.resolve("plugins"));
		agregarSiExiste(ret, carpetaBase.resolve("datafiedcontents"));
		agregarSiExiste(ret, carpetaBase.resolve("kubejs"));
		agregarSiExiste(ret, Statics.carpeta.toAbsolutePath().normalize());
		agregarSiExiste(ret, carpetaBase.resolve("saves"));
		agregarSiExiste(ret, carpetaBase.resolve("world"));
		agregarSiExiste(ret, carpetaBase.resolve("resourcepacks"));
		agregarSiExiste(ret, carpetaBase.resolve("Texture packs"));
		agregarSiExiste(ret, carpetaBase.resolve("datapacks"));
		agregarSiExiste(ret, carpetaBase.resolve("options.txt"));
		agregarSiExiste(ret, carpetaBase.resolve("TLauncherAdditional.json"));

		return ret;
	}

	public void agregarSiExiste(List<Path> lista, Path ruta) {
		try {
			if (ruta != null && Files.exists(ruta) && !lista.contains(ruta)) {
				lista.add(ruta.normalize().toAbsolutePath());
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public DefaultMutableTreeNode crearNodoRecursivoSiExiste(Path ruta) {
		try {
			if (ruta == null || !Files.exists(ruta)) {
				return null;
			}

			NodoRutaSeleccionable dato = new NodoRutaSeleccionable(ruta, EstadoSeleccion.SELECCIONADO);
			DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(dato);

			if (Files.isDirectory(ruta)) {
				List<Path> hijos = new ArrayList<>();
				try (java.util.stream.Stream<Path> s = Files.list(ruta)) {
					s.sorted(Comparator.comparing(p -> p.getFileName().toString().toLowerCase())).forEach(hijos::add);
				}

				for (Path hijo : hijos) {
					// Solo rutas permitidas: carpeta actual y subrutas, o Statics.carpeta explícita
					if (rutaPermitida(hijo)) {
						DefaultMutableTreeNode hijoNodo = crearNodoRecursivoSiExiste(hijo);
						if (hijoNodo != null) {
							nodo.add(hijoNodo);
						}
					}
				}
			}

			return nodo;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return null;
		}
	}

	public boolean rutaPermitida(Path ruta) {
		if (ruta == null) {
			return false;
		}

		Path normal = ruta.toAbsolutePath().normalize();
		Path statics = Statics.carpeta.toAbsolutePath().normalize();

		return normal.startsWith(carpetaBase) || normal.startsWith(statics);
	}

	public void establecerSeleccionRecursiva(DefaultMutableTreeNode nodo, boolean seleccionada) {
		Object user = nodo.getUserObject();
		if (user instanceof NodoRutaSeleccionable) {
			((NodoRutaSeleccionable) user).estado = seleccionada ? EstadoSeleccion.SELECCIONADO
					: EstadoSeleccion.NO_SELECCIONADO;
		}

		Enumeration<?> en = nodo.children();
		while (en.hasMoreElements()) {
			establecerSeleccionRecursiva((DefaultMutableTreeNode) en.nextElement(), seleccionada);
		}
	}

	public void actualizarPadres(DefaultMutableTreeNode nodo) {
		if (nodo == null) {
			return;
		}

		int seleccionados = 0;
		int vacios = 0;

		Enumeration<?> en = nodo.children();
		while (en.hasMoreElements()) {
			DefaultMutableTreeNode hijo = (DefaultMutableTreeNode) en.nextElement();
			Object user = hijo.getUserObject();
			if (!(user instanceof NodoRutaSeleccionable)) {
				continue;
			}

			EstadoSeleccion estado = ((NodoRutaSeleccionable) user).estado;
			if (estado == EstadoSeleccion.SELECCIONADO) {
				seleccionados++;
			} else if (estado == EstadoSeleccion.NO_SELECCIONADO) {
				vacios++;
			}
		}

		Object userNodo = nodo.getUserObject();
		if (userNodo instanceof NodoRutaSeleccionable) {
			NodoRutaSeleccionable dato = (NodoRutaSeleccionable) userNodo;
			int total = nodo.getChildCount();

			if (total == 0) {
				// no cambiar
			} else if (seleccionados == total) {
				dato.estado = EstadoSeleccion.SELECCIONADO;
			} else if (vacios == total) {
				dato.estado = EstadoSeleccion.NO_SELECCIONADO;
			} else {
				dato.estado = EstadoSeleccion.PARCIAL;
			}
		}

		actualizarPadres((DefaultMutableTreeNode) nodo.getParent());
	}

	public List<Path> obtenerSeleccionFinal() {
		List<Path> ret = new ArrayList<>();

		DefaultMutableTreeNode raiz = (DefaultMutableTreeNode) modeloArbol.getRoot();
		Enumeration<?> en = raiz.depthFirstEnumeration();

		while (en.hasMoreElements()) {
			DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) en.nextElement();
			Object user = nodo.getUserObject();

			if (!(user instanceof NodoRutaSeleccionable)) {
				continue;
			}

			NodoRutaSeleccionable dato = (NodoRutaSeleccionable) user;
			if (dato.estado == EstadoSeleccion.SELECCIONADO) {
				// evitar añadir hijos si el padre ya va completo
				DefaultMutableTreeNode padre = (DefaultMutableTreeNode) nodo.getParent();
				boolean padreCompleto = false;

				while (padre != null) {
					Object up = padre.getUserObject();
					if (up instanceof NodoRutaSeleccionable
							&& ((NodoRutaSeleccionable) up).estado == EstadoSeleccion.SELECCIONADO) {
						padreCompleto = true;
						break;
					}
					padre = (DefaultMutableTreeNode) padre.getParent();
				}

				if (!padreCompleto) {
					ret.add(dato.ruta);
				}
			}
		}

		return ret;
	}

	public void compartirSeleccion() {
		List<Path> rutas = obtenerSeleccionFinal();
		if (rutas.isEmpty()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.compartirInstanciaSinSeleccion(),
					MonitorDePID.idioma.error(), JOptionPane.WARNING_MESSAGE);
			return;
		}

		ProveedorMods proveedor = obtenerProveedorFormatoSeleccionado();
		String servicio = (String) comboServicio.getSelectedItem();

		if (proveedor == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.compartirInstanciaFormatoNoSoportado(),
					MonitorDePID.idioma.error(), JOptionPane.WARNING_MESSAGE);
			return;
		}

		SitioDeArchivoAPI api = SitioDeArchivoAPI.SERVICIOS_REGISTRADOS.get(servicio);
		if (api == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.compartirInstanciaServicioNoDisponible(),
					MonitorDePID.idioma.error(), JOptionPane.WARNING_MESSAGE);
			return;
		}

		setTextoCarga(MonitorDePID.idioma.compartirInstanciaEstadoEmpaquetando());
		setCargando(true);

		workerCompartir = new SwingWorker<Void, Void>() {
			private Path zipCreado;
			private SitioDeArchivoAPI.SesionDeTransferencia sesion;

			@Override
			protected Void doInBackground() throws Exception {
				etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEstadoEmpaquetando());

				String extension = CopiaDeSeguridadDeArchivos.obtenerExtensionProveedor(proveedor);
				zipCreado = CopiaDeSeguridadDeArchivos.crearRutaBackup(carpetaBase, extension);
				proveedor.exportarModpack(zipCreado, rutas);

				etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEstadoSubiendo());
				setTextoCarga(MonitorDePID.idioma.compartirInstanciaEstadoSubiendo());

				sesion = api.publicarArchivoZip(zipCreado, new ObservadorDeTransferencia() {
					@Override
					public void alRecibirCodigo(String codigo) {
						etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaCodigo() + ": " + codigo);
					}

					@Override
					public void alRecibirEnlace(String enlace) {
						etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEnlace() + ": " + enlace);
					}

					@Override
					public void alCambiarEstado(SitioDeArchivoAPI.EstadoDeTransferencia estado) {
						switch (estado) {
						case CONECTANDO:
							setTextoCarga(MonitorDePID.idioma.conectando());
							break;
						case SUBIENDO:
							setTextoCarga(MonitorDePID.idioma.compartirInstanciaEstadoSubiendo());
							break;
						case ESPERANDO_DESCARGA:
							setTextoCarga(MonitorDePID.idioma.esperando_descarga());
							break;
						case FINALIZADA:
							setTextoCarga(MonitorDePID.idioma.finalizado());
							break;
						case ERROR:
							setTextoCarga(MonitorDePID.idioma.error());
							break;
						default:
							break;
						}
					}
				});

				if (sesion != null) {
					sesion.esperarFinalizacion();
				}

				return null;
			}

			@Override
			protected void done() {
				try {
					get();

					StringBuilder sb = new StringBuilder();
					sb.append(MonitorDePID.idioma.compartirInstanciaSubidaCompleta());

					if (sesion != null) {
						if (sesion.codigo() != null) {
							sb.append("\n").append(MonitorDePID.idioma.compartirInstanciaCodigo()).append(": ")
									.append(sesion.codigo());
						}
						if (sesion.enlace() != null) {
							sb.append("\n").append(MonitorDePID.idioma.compartirInstanciaEnlace()).append(": ")
									.append(sesion.enlace());
						}
						if (sesion.requiereMantenerSesionAbierta()) {
							sb.append("\n").append(MonitorDePID.idioma.compartirInstanciaMantenerAbierto());
						}
					}

					JTextArea areaResultado = new JTextArea(sb.toString());
					areaResultado.setEditable(false);
					areaResultado.setLineWrap(true);
					areaResultado.setWrapStyleWord(true);
					areaResultado.setCaretPosition(0);

					JScrollPane scroll = new JScrollPane(areaResultado);
					scroll.setBorder(null);
					scroll.setPreferredSize(new Dimension(520, 180));

					JOptionPane.showMessageDialog(CompartirInstanciaGUI.this, scroll, MonitorDePID.idioma.informacion(),
							JOptionPane.INFORMATION_MESSAGE);

					etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEstadoListo());
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					JOptionPane.showMessageDialog(CompartirInstanciaGUI.this,
							MonitorDePID.idioma.compartirInstanciaErrorSubir() + "\n" + t.getMessage(),
							MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
					etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEstadoError());
				} finally {
					setCargando(false);
				}
			}
		};

		workerCompartir.execute();
	}

	public enum EstadoSeleccion {
		NO_SELECCIONADO, PARCIAL, SELECCIONADO
	}

	public static class NodoRutaSeleccionable {
		public Path ruta;
		public EstadoSeleccion estado;

		public NodoRutaSeleccionable(Path ruta, EstadoSeleccion estado) {
			this.ruta = ruta;
			this.estado = estado;
		}

		@Override
		public String toString() {
			Path nombre = ruta.getFileName();
			return nombre != null ? nombre.toString() : ruta.toString();
		}
	}

	public class RenderizadorTriestado extends DefaultTreeCellRenderer {
		private final Icon iconoCheck = new ImageIcon(Statics.carpeta.resolve("imagenes/check.png").toString());
		private final Icon iconoParcial = new ImageIcon(Statics.carpeta.resolve("imagenes/square.png").toString());

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {

			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			Object user = ((DefaultMutableTreeNode) value).getUserObject();
			if (user instanceof NodoRutaSeleccionable) {
				NodoRutaSeleccionable dato = (NodoRutaSeleccionable) user;

				setText(dato.toString());

				if (dato.estado == EstadoSeleccion.SELECCIONADO) {
					setIcon(iconoCheck.getIconWidth() > 0 ? iconoCheck : getDefaultOpenIcon());
				} else if (dato.estado == EstadoSeleccion.PARCIAL) {
					setIcon(iconoParcial.getIconWidth() > 0 ? iconoParcial : getDefaultClosedIcon());
				} else {
					setIcon(getDefaultClosedIcon());
				}

				setToolTipText(dato.ruta.toString());
			}

			return this;
		}
	}

	public void initOverlayCarga() {
		overlayCarga = new ElementoOverlayCarga();
		overlayCarga.setVisible(false);
		setGlassPane(overlayCarga);
	}

	public void setCargando(boolean nuevoEstado) {
		cargando = nuevoEstado;

		if (overlayCarga != null) {
			overlayCarga.setVisible(nuevoEstado);
			overlayCarga.revalidate();
			overlayCarga.repaint();
		}
	}

	public void setTextoCarga(String texto) {
		textoCargaActual = texto != null ? texto : "";
	}

}