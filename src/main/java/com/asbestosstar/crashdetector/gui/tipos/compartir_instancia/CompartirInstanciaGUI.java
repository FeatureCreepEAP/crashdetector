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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.api_sito_archivo.SitioDeArchivoAPI;
import com.asbestosstar.crashdetector.api_sito_archivo.SitioDeArchivoAPI.ArchivoDemasiadoGrande;
import com.asbestosstar.crashdetector.api_sito_archivo.SitioDeArchivoAPI.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sito_archivo.SitioDeArchivoAPI.ErrorDeArchivo;
import com.asbestosstar.crashdetector.api_sito_archivo.SitioDeArchivoAPI.ObservadorDeTransferencia;
import com.asbestosstar.crashdetector.api_sito_archivo.SitioDeArchivoAPI.ServicioNoSoportado;
import com.asbestosstar.crashdetector.api_sito_archivo.WormHoleApp;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * GUI abstracta para compartir la instancia completa o una selección de carpetas.
 *
 * Reglas:
 * - Solo se listan rutas dentro de la carpeta actual y sus subrutas.
 * - Se permite además Statics.CARPETA como raíz explícita si existe.
 * - El árbol usa selección triestado:
 *   - check = todo seleccionado
 *   - cuadrado = selección parcial
 *   - vacío = no seleccionado
 *
 * Nota:
 * - wormhole.app queda registrado aquí como servicio integrado por defecto.
 * - otros servicios pueden registrarse desde extensiones.
 */
public abstract class CompartirInstanciaGUI extends JFrame implements CrashDetectorGUI {

	public static final Map<String, Supplier<CompartirInstanciaGUI>> GUIS = new HashMap<>();

	static {
		// Ojo: la implementación existente en tu código se llama WormHoleApp.
		SitioDeArchivoAPI.SERVICIOS_REGISTRADOS.put("wormhole.app", new WormHoleApp());
	}

	public JTextArea areaPolitica;
	public JTree arbolRutas;
	public DefaultTreeModel modeloArbol;
	public JComboBox<String> comboFormato;
	public JComboBox<String> comboServicio;
	public JButton botonCompartir;
	public JButton botonRefrescar;
	public JLabel etiquetaEstado;

	public SwingWorker<Void, Void> workerCompartir;

	public Path carpetaBase;
	public Set<Path> seleccionadas = new LinkedHashSet<>();

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

		comboFormato.setModel(new DefaultComboBoxModel<>(new String[] { "basico" }));
		recargarServicios();

		filaCombos.add(new JLabel(MonitorDePID.idioma.compartirInstanciaFormato() + ":"));
		filaCombos.add(comboFormato);
		filaCombos.add(new JLabel(MonitorDePID.idioma.compartirInstanciaServicio() + ":"));
		filaCombos.add(comboServicio);

		JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
		botonCompartir = new JButton(MonitorDePID.idioma.compartirInstanciaBotonCompartir());
		botonRefrescar = new JButton(MonitorDePID.idioma.compartirInstanciaBotonRefrescar());
		etiquetaEstado = new JLabel(MonitorDePID.idioma.compartirInstanciaEstadoListo());

		filaBotones.add(botonCompartir);
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
		setVisible(true);
	}

	protected void recargarServicios() {
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();

		for (String nombre : SitioDeArchivoAPI.SERVICIOS_REGISTRADOS.keySet()) {
			modelo.addElement(nombre);
		}

		comboServicio.setModel(modelo);
	}

	protected void refrescarArbol() {
		DefaultMutableTreeNode raiz = construirArbolSeleccion();
		modeloArbol.setRoot(raiz);
		modeloArbol.reload();
	}

	protected String construirTextoPolitica() {
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

	protected DefaultMutableTreeNode construirArbolSeleccion() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("ROOT");

		for (Path ruta : obtenerRutasPredeterminadas()) {
			DefaultMutableTreeNode nodo = crearNodoRecursivoSiExiste(ruta);
			if (nodo != null) {
				raiz.add(nodo);
			}
		}

		return raiz;
	}

	protected List<Path> obtenerRutasPredeterminadas() {
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

	protected void agregarSiExiste(List<Path> lista, Path ruta) {
		try {
			if (ruta != null && Files.exists(ruta) && !lista.contains(ruta)) {
				lista.add(ruta.normalize().toAbsolutePath());
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	protected DefaultMutableTreeNode crearNodoRecursivoSiExiste(Path ruta) {
		try {
			if (ruta == null || !Files.exists(ruta)) {
				return null;
			}

			NodoRutaSeleccionable dato = new NodoRutaSeleccionable(ruta, EstadoSeleccion.SELECCIONADO);
			DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(dato);

			if (Files.isDirectory(ruta)) {
				List<Path> hijos = new ArrayList<>();
				try (java.util.stream.Stream<Path> s = Files.list(ruta)) {
					s.sorted(Comparator.comparing(p -> p.getFileName().toString().toLowerCase()))
							.forEach(hijos::add);
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

	protected boolean rutaPermitida(Path ruta) {
		if (ruta == null) {
			return false;
		}

		Path normal = ruta.toAbsolutePath().normalize();
		Path statics = Statics.carpeta.toAbsolutePath().normalize();

		return normal.startsWith(carpetaBase) || normal.startsWith(statics);
	}

	protected void establecerSeleccionRecursiva(DefaultMutableTreeNode nodo, boolean seleccionada) {
		Object user = nodo.getUserObject();
		if (user instanceof NodoRutaSeleccionable) {
			((NodoRutaSeleccionable) user).estado = seleccionada
					? EstadoSeleccion.SELECCIONADO
					: EstadoSeleccion.NO_SELECCIONADO;
		}

		Enumeration<?> en = nodo.children();
		while (en.hasMoreElements()) {
			establecerSeleccionRecursiva((DefaultMutableTreeNode) en.nextElement(), seleccionada);
		}
	}

	protected void actualizarPadres(DefaultMutableTreeNode nodo) {
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

	protected List<Path> obtenerSeleccionFinal() {
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

	protected void compartirSeleccion() {
		List<Path> rutas = obtenerSeleccionFinal();
		if (rutas.isEmpty()) {
			JOptionPane.showMessageDialog(this,
					MonitorDePID.idioma.compartirInstanciaSinSeleccion(),
					MonitorDePID.idioma.error(),
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String formato = (String) comboFormato.getSelectedItem();
		String servicio = (String) comboServicio.getSelectedItem();

		if (!"basico".equalsIgnoreCase(formato)) {
			JOptionPane.showMessageDialog(this,
					MonitorDePID.idioma.compartirInstanciaFormatoNoSoportado(),
					MonitorDePID.idioma.error(),
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		SitioDeArchivoAPI api = SitioDeArchivoAPI.SERVICIOS_REGISTRADOS.get(servicio);
		if (api == null) {
			JOptionPane.showMessageDialog(this,
					MonitorDePID.idioma.compartirInstanciaServicioNoDisponible(),
					MonitorDePID.idioma.error(),
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		workerCompartir = new SwingWorker<Void, Void>() {
			private Path zipCreado;
			private SitioDeArchivoAPI.SesionDeTransferencia sesion;

			@Override
			protected Void doInBackground() throws Exception {
				etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEstadoEmpaquetando());
				zipCreado = crearZipBasico(rutas);

				etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEstadoSubiendo());

				sesion = api.publicarArchivoZip(zipCreado, new ObservadorDeTransferencia() {
					@Override
					public void alRecibirCodigo(String codigo) {
						etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaCodigo() + ": " + codigo);
					}

					@Override
					public void alRecibirEnlace(String enlace) {
						etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEnlace() + ": " + enlace);
					}
				});

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

					JOptionPane.showMessageDialog(
							CompartirInstanciaGUI.this,
							sb.toString(),
							MonitorDePID.idioma.informacion(),
							JOptionPane.INFORMATION_MESSAGE);

					etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEstadoListo());
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					JOptionPane.showMessageDialog(
							CompartirInstanciaGUI.this,
							MonitorDePID.idioma.compartirInstanciaErrorSubir() + "\n" + t.getMessage(),
							MonitorDePID.idioma.error(),
							JOptionPane.ERROR_MESSAGE);
					etiquetaEstado.setText(MonitorDePID.idioma.compartirInstanciaEstadoError());
				}
			}
		};

		workerCompartir.execute();
	}

	protected Path crearZipBasico(List<Path> rutas) throws Exception {
		Path temporal = Files.createTempFile("crashdetector-instancia-", ".zip");

		try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(temporal))) {
			Set<String> yaAgregadas = new LinkedHashSet<>();

			for (Path ruta : rutas) {
				if (!Files.exists(ruta)) {
					continue;
				}

				if (Files.isRegularFile(ruta)) {
					agregarArchivoAlZip(zos, ruta, yaAgregadas);
				} else if (Files.isDirectory(ruta)) {
					try (java.util.stream.Stream<Path> stream = Files.walk(ruta)) {
						stream.filter(Files::isRegularFile).forEach(p -> {
							try {
								agregarArchivoAlZip(zos, p, yaAgregadas);
							} catch (Throwable t) {
								CrashDetectorLogger.logException(t);
							}
						});
					}
				}
			}
		}

		return temporal;
	}

	protected void agregarArchivoAlZip(ZipOutputStream zos, Path archivo, Set<String> yaAgregadas) throws Exception {
		Path normal = archivo.toAbsolutePath().normalize();
		String nombreRelativo;

		Path statics = Statics.carpeta.toAbsolutePath().normalize();
		if (normal.startsWith(carpetaBase)) {
			nombreRelativo = carpetaBase.relativize(normal).toString().replace('\\', '/');
		} else if (normal.startsWith(statics)) {
			nombreRelativo = "_crashdetector/" + statics.relativize(normal).toString().replace('\\', '/');
		} else {
			return;
		}

		if (!yaAgregadas.add(nombreRelativo)) {
			return;
		}

		zos.putNextEntry(new ZipEntry(nombreRelativo));
		Files.copy(archivo, zos);
		zos.closeEntry();
	}

	public enum EstadoSeleccion {
		NO_SELECCIONADO,
		PARCIAL,
		SELECCIONADO
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
}