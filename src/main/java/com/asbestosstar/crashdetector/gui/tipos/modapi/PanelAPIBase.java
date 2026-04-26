package com.asbestosstar.crashdetector.gui.tipos.modapi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.dto.modpack.basico.ProveedorModsBasico;
import com.asbestosstar.crashdetector.dto.modpack.bbsmc.ProveedorModsBBSMC;
import com.asbestosstar.crashdetector.dto.modpack.curseforge.ProveedorModsCurseForge;
import com.asbestosstar.crashdetector.dto.modpack.minecraftstorage.ProveedorModsMinecraftStorage;
import com.asbestosstar.crashdetector.dto.modpack.modrinth.ProveedorModsModrinth;
import com.asbestosstar.crashdetector.dto.modpack.tlmods.ProveedorModsTlmods;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.compartir_instancia.CompartirInstanciaLegacy;
import com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI;

/**
 * Clase base abstracta sin inicialización en constructor. Toda la lógica debe
 * iniciarse explícitamente mediante `init()`.
 */
public abstract class PanelAPIBase extends JPanel implements CrashDetectorGUI {

	public static Map<String, Supplier<PanelAPIBase>> GUIS = new HashMap<>();

	protected JTextField searchBar;
	protected JPanel modListPanel;
	protected JPanel sidebarPanel;
	protected JLabel etiquetaCarga;
	protected List<String> archivosJAR;
	protected ProveedorMods proveedorActual;

	public static final Map<String, Supplier<ProveedorMods>> PROVEEDORES_MODS = new LinkedHashMap<>();
	protected static final ConfigString proveedorConfig = ConfigString.de("cdmods.proveedor", "tlmods");

	protected JComboBox<ProveedorRegistrado> comboProveedores;

	static {
		registrarProveedorMods("tlmods", "TLMods", ProveedorModsTlmods::new);
		registrarProveedorMods("cursedforge", "CursedForge", ProveedorModsCurseForge::new);
		registrarProveedorMods("minecraftstorage", "MinecraftStorage", ProveedorModsMinecraftStorage::new);
		registrarProveedorMods("basico", "Basico", ProveedorModsBasico::new);
		registrarProveedorMods("bbsmc", "BBSMC", ProveedorModsBBSMC::new);
		registrarProveedorMods("modrinth", "Modrinth", ProveedorModsModrinth::new);

	}

	public static void registrarProveedorMods(String id, String nombre, Supplier<ProveedorMods> proveedor) {
		PROVEEDORES_MODS.put(id, proveedor);
	}

	protected static class ProveedorRegistrado {

		private final String id;
		private final String nombre;

		public ProveedorRegistrado(String id, String nombre) {
			this.id = id;
			this.nombre = nombre;
		}

		public String obtenerId() {
			return id;
		}

		@Override
		public String toString() {
			return nombre;
		}
	}

	protected JScrollPane scrollMods;
	protected JScrollPane sidebarScroll;
	private final java.util.List<JPanel> tarjetasMods = new java.util.ArrayList<>();

	private PrincipalGUI principalGUI;

	// Métodos abstractos para estilo y proveedor
	protected abstract void inicializarColores();

	protected abstract void aplicarEstiloVolver(JButton boton);

	protected abstract void aplicarEstiloBusqueda(JTextField campo);

	protected abstract void aplicarEstiloTarjetaMod(JPanel tarjeta);

	protected abstract void aplicarEstiloSidebar(JPanel sidebar);

	protected abstract void aplicarEstiloSidebarItem(JPanel item, JCheckBox checkBox, JLabel etiqueta);

	protected abstract void aplicarEstiloPrincipal(JPanel panel);

	protected abstract ProveedorMods crearProveedorMods();

	// Colores
	protected abstract java.awt.Color obtenerColorFondo();

	protected abstract java.awt.Color obtenerColorTexto();

	protected abstract java.awt.Color obtenerColorBoton();

	protected abstract java.awt.Color obtenerColorCajaTexto();

	protected abstract void aplicarEstiloBotonAccion(JButton boton);

	// Constructor mínimo
	public PanelAPIBase() {
		setLayout(new BorderLayout());
	}

	@Override
	public void init() {

		inicializarColores();
		setBackground(obtenerColorFondo());
		setLayout(new BorderLayout());

		JButton botonVolver = new JButton("←");
		aplicarEstiloVolver(botonVolver);
		botonVolver.setFocusPainted(false);
		botonVolver.addActionListener(e -> {
			if (principalGUI != null) {
				principalGUI.volver();
			}
		});

		JPanel topPanel = crearPanelBusqueda();

		JPanel norte = new JPanel(new BorderLayout());
		norte.setBackground(obtenerColorFondo());
		norte.add(botonVolver, BorderLayout.WEST);
		norte.add(topPanel, BorderLayout.CENTER);
		add(norte, BorderLayout.NORTH);

		JPanel mainPanel = new JPanel(new BorderLayout());
		aplicarEstiloPrincipal(mainPanel);

		sidebarPanel = crearSidebar();

		sidebarScroll = new JScrollPane(sidebarPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sidebarScroll.setBorder(null);
		sidebarScroll.getVerticalScrollBar().setUnitIncrement(14);
		sidebarScroll.getViewport().setOpaque(true);
		sidebarScroll.getViewport().setBackground(obtenerColorFondo().darker());

		mainPanel.add(sidebarScroll, BorderLayout.WEST);

		modListPanel = new JPanel();
		modListPanel.setLayout(new BoxLayout(modListPanel, BoxLayout.Y_AXIS));
		modListPanel.setOpaque(true);
		modListPanel.setBackground(obtenerColorFondo());

		scrollMods = new JScrollPane(modListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollMods.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollMods.getVerticalScrollBar().setUnitIncrement(14);

		mainPanel.add(scrollMods, BorderLayout.CENTER);

		add(mainPanel, BorderLayout.CENTER);

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				actualizarEscalado();
			}
		});

		scrollMods.getViewport().addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				actualizarEscalado();
			}
		});

		cargarArchivosJAR();
		proveedorActual = crearProveedorSeleccionado();
		buscarMods("");

		SwingUtilities.invokeLater(this::actualizarEscalado);
	}

	protected void actualizarEscalado() {

		int anchoTotal = getWidth();
		if (anchoTotal <= 0) {
			return;
		}

		int sidebarW = Math.max(150, Math.min(280, (int) (anchoTotal * 0.22)));

		if (sidebarScroll != null) {
			sidebarScroll.setPreferredSize(new Dimension(sidebarW, 0));
			sidebarScroll.setMinimumSize(new Dimension(Math.max(130, sidebarW - 20), 0));
		}

		if (modListPanel != null) {
			modListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			modListPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		}

		for (JPanel tarjeta : tarjetasMods) {
			tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);
			reconfigurarTarjeta(tarjeta);
		}

		if (modListPanel != null) {
			modListPanel.revalidate();
			modListPanel.repaint();
		}

		if (sidebarPanel != null) {
			sidebarPanel.revalidate();
			sidebarPanel.repaint();
		}

		revalidate();
		repaint();
	}

	// === Resto de métodos idénticos a la versión anterior ===
	// (crearPanelBusqueda, buscarMods, crearTarjetaMod, crearSidebar,
	// cargarArchivosJAR, etc.)
	// Pero ahora usan el método `obtenerExtensionDesactivacion()` para decidir la
	// extensión
	private JPanel crearPanelBusqueda() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		panel.setBackground(obtenerColorFondo());

		Dimension tamBotonChico = new Dimension(38, 24);

		JButton botonImportar = new JButton("↑");
		botonImportar.setToolTipText("Importar instancia");
		botonImportar.setPreferredSize(tamBotonChico);
		botonImportar.setMinimumSize(tamBotonChico);
		botonImportar.setMaximumSize(tamBotonChico);
		aplicarEstiloBotonAccion(botonImportar);
		botonImportar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Próximamente"));
		panel.add(botonImportar);

		JButton botonExportar = new JButton("↓");
		botonExportar.setToolTipText("Compartir instancia");
		botonExportar.setPreferredSize(tamBotonChico);
		botonExportar.setMinimumSize(tamBotonChico);
		botonExportar.setMaximumSize(tamBotonChico);
		aplicarEstiloBotonAccion(botonExportar);
		botonExportar.addActionListener(e -> {
			TipoGUI.COMPARTIR_INSTANCIA
					.obtenerGUIPredeterminado(CompartirInstanciaLegacy.ID, CompartirInstanciaLegacy::new).init();
		});
		panel.add(botonExportar);

		comboProveedores = new JComboBox<>();
		comboProveedores.setPrototypeDisplayValue(new ProveedorRegistrado("tlmods", "tlmods"));
		comboProveedores.setPreferredSize(new Dimension(155, 24));
		comboProveedores.setMinimumSize(new Dimension(125, 24));
		comboProveedores.setMaximumSize(new Dimension(180, 24));

		for (Map.Entry<String, Supplier<ProveedorMods>> entrada : PROVEEDORES_MODS.entrySet()) {
			ProveedorMods proveedorTemporal = entrada.getValue().get();

			// Solo los proveedores que soportan búsqueda aparecen en el combo.
			if (!proveedorTemporal.soportaBusqueda()) {
				continue;
			}

			comboProveedores.addItem(new ProveedorRegistrado(entrada.getKey(), entrada.getKey()));
		}

		seleccionarProveedorEnCombo(proveedorConfig.obtener());

		comboProveedores.addActionListener(e -> {
			ProveedorRegistrado seleccionado = (ProveedorRegistrado) comboProveedores.getSelectedItem();

			if (seleccionado == null) {
				return;
			}

			proveedorConfig.escribir(seleccionado.obtenerId());
			proveedorActual = crearProveedorSeleccionado();
			buscarMods(searchBar != null ? searchBar.getText() : "");
		});

		panel.add(comboProveedores);

		JLabel label = new JLabel(MonitorDePID.idioma.buscar());
		label.setForeground(obtenerColorTexto());
		panel.add(label);

		searchBar = new JTextField(20);
		aplicarEstiloBusqueda(searchBar);
		panel.add(searchBar);

		JButton botonBuscar = new JButton("🔍");
		botonBuscar.setToolTipText(MonitorDePID.idioma.buscar());
		botonBuscar.setPreferredSize(tamBotonChico);
		botonBuscar.setMinimumSize(tamBotonChico);
		botonBuscar.setMaximumSize(tamBotonChico);
		aplicarEstiloBotonAccion(botonBuscar);
		botonBuscar.addActionListener(e -> buscarMods(searchBar.getText()));
		panel.add(botonBuscar);

		return panel;
	}

	private void buscarMods(String termino) {

		if (proveedorActual == null || !proveedorActual.soportaBusqueda()) {
			return;
		}

		mostrarCarga(true);

		CompletableFuture.runAsync(() -> {
			try {
				PaginaMods pagina = proveedorActual.buscarMods(MonitorDePID.idioma.codigo().toUpperCase(), 0, termino);

				java.util.Set<Long> idsVistos = new java.util.HashSet<>();

				SwingUtilities.invokeLater(() -> {

					// Quitar el gif antes de agregar las tarjetas.
					modListPanel.removeAll();
					tarjetasMods.clear();

					for (InternetMod mod : pagina.obtenerListaMods()) {

						if (mod == null) {
							continue;
						}

						if (!idsVistos.add(mod.obtenerIdentificador())) {
							continue;
						}

						JPanel tarjeta = crearTarjetaMod(mod);
						tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);
						modListPanel.add(tarjeta);
						modListPanel.add(Box.createRigidArea(new Dimension(0, 4)));
					}

					actualizarEscalado();
					modListPanel.revalidate();
					modListPanel.repaint();
				});

			} catch (IOException ex) {
				SwingUtilities.invokeLater(() -> {
					modListPanel.removeAll();
					tarjetasMods.clear();

					JLabel error = new JLabel("Error al cargar mods.");
					error.setForeground(obtenerColorTexto());
					modListPanel.add(error);

					modListPanel.revalidate();
					modListPanel.repaint();
				});
			}
		});
	}

	private void mostrarCarga(boolean cargando) {

		if (modListPanel == null) {
			return;
		}

		if (!cargando) {
			return;
		}

		modListPanel.removeAll();
		tarjetasMods.clear();

		etiquetaCarga = new JLabel(new ImageIcon(Statics.carpeta.resolve("imagenes/padoru.gif").toString()));
		etiquetaCarga.setAlignmentX(Component.CENTER_ALIGNMENT);
		etiquetaCarga.setHorizontalAlignment(JLabel.CENTER);

		JPanel panelCarga = new JPanel(new BorderLayout());
		panelCarga.setOpaque(true);
		panelCarga.setBackground(obtenerColorFondo());
		panelCarga.add(etiquetaCarga, BorderLayout.CENTER);
		panelCarga.setPreferredSize(new Dimension(300, 220));
		panelCarga.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

		modListPanel.add(Box.createVerticalStrut(40));
		modListPanel.add(panelCarga);

		modListPanel.revalidate();
		modListPanel.repaint();
	}

	protected JPanel crearTarjetaMod(InternetMod mod) {

		JPanel tarjeta = new JPanel(new BorderLayout(10, 6));
		tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);
		tarjeta.setOpaque(true);

		tarjeta.setBorder(javax.swing.BorderFactory.createCompoundBorder(
				javax.swing.BorderFactory.createLineBorder(java.awt.Color.DARK_GRAY, 1),
				javax.swing.BorderFactory.createEmptyBorder(6, 8, 6, 8)));

		aplicarEstiloTarjetaMod(tarjeta);

		tarjeta.putClientProperty("mod", mod);

		JPanel panelIzq = new JPanel();
		panelIzq.setLayout(new BoxLayout(panelIzq, BoxLayout.Y_AXIS));
		panelIzq.setOpaque(false);
		panelIzq.setAlignmentY(Component.TOP_ALIGNMENT);

		JLabel icono = crearIconoMod(mod, 48);
		icono.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton instalar = new JButton("Instalar");
		instalar.setAlignmentX(Component.CENTER_ALIGNMENT);
		aplicarEstiloBotonAccion(instalar);
		instalar.addActionListener(e -> instalarMod(mod));

		panelIzq.add(icono);
		panelIzq.add(Box.createRigidArea(new Dimension(0, 6)));
		panelIzq.add(instalar);

		JPanel centro = new JPanel();
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		centro.setOpaque(false);
		centro.setAlignmentY(Component.TOP_ALIGNMENT);
		centro.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel nombre = new JLabel(mod.obtenerNombre());
		nombre.setForeground(obtenerColorTexto());
		nombre.setAlignmentX(Component.LEFT_ALIGNMENT);

		JTextArea descripcion = new JTextArea();
		descripcion.setEditable(false);
		descripcion.setLineWrap(true);
		descripcion.setWrapStyleWord(true);
		descripcion.setOpaque(false);
		descripcion.setFocusable(false);
		descripcion.setBorder(null);
		descripcion.setForeground(java.awt.Color.LIGHT_GRAY);
		descripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

		centro.add(nombre);
		centro.add(Box.createRigidArea(new Dimension(0, 2)));
		centro.add(descripcion);

		tarjeta.putClientProperty("panelIzq", panelIzq);
		tarjeta.putClientProperty("icono", icono);
		tarjeta.putClientProperty("botonInstalar", instalar);
		tarjeta.putClientProperty("centro", centro);
		tarjeta.putClientProperty("labelNombre", nombre);
		tarjeta.putClientProperty("labelDescripcion", descripcion);

		tarjeta.add(panelIzq, BorderLayout.WEST);
		tarjeta.add(centro, BorderLayout.CENTER);

		tarjetasMods.add(tarjeta);

		reconfigurarTarjeta(tarjeta);

		return tarjeta;
	}

	private void reconfigurarTarjeta(JPanel tarjeta) {

		InternetMod mod = (InternetMod) tarjeta.getClientProperty("mod");
		if (mod == null) {
			return;
		}

		JPanel panelIzq = (JPanel) tarjeta.getClientProperty("panelIzq");
		JPanel centro = (JPanel) tarjeta.getClientProperty("centro");
		JLabel icono = (JLabel) tarjeta.getClientProperty("icono");
		JButton instalar = (JButton) tarjeta.getClientProperty("botonInstalar");
		JLabel nombre = (JLabel) tarjeta.getClientProperty("labelNombre");
		JTextArea descripcion = (JTextArea) tarjeta.getClientProperty("labelDescripcion");

		int viewportW = (scrollMods != null && scrollMods.getViewport() != null) ? scrollMods.getViewport().getWidth()
				: getWidth();

		if (viewportW <= 0) {
			viewportW = 700;
		}

		float escala = Math.max(0.85f, Math.min(1.05f, viewportW / 650f));

		int iconSize = Math.max(40, Math.min(52, Math.round(48 * escala)));
		int botonW = Math.max(64, Math.min(88, Math.round(72 * escala)));
		int botonH = Math.max(22, Math.min(26, Math.round(24 * escala)));
		int altoTarjeta = 92;

		int anchoTarjeta = Math.max(320, viewportW - 2);
		int anchoPanelIzq = Math.max(iconSize, botonW);
		int anchoCentro = anchoTarjeta - tarjeta.getInsets().left - tarjeta.getInsets().right - anchoPanelIzq - 10; // hgap
																													// de
																													// BorderLayout

		anchoCentro = Math.max(140, anchoCentro);
		int anchoDescripcion = Math.max(120, anchoCentro);

		if (icono != null) {
			icono.setPreferredSize(new Dimension(iconSize, iconSize));
			icono.setMinimumSize(new Dimension(iconSize, iconSize));
			icono.setMaximumSize(new Dimension(iconSize, iconSize));
			icono.setIcon(crearIconoModEscalado(mod, iconSize));
		}

		if (instalar != null) {
			instalar.setPreferredSize(new Dimension(botonW, botonH));
			instalar.setMinimumSize(new Dimension(botonW, botonH));
			instalar.setMaximumSize(new Dimension(botonW, botonH));
			instalar.setFont(instalar.getFont().deriveFont(Math.max(10.5f, Math.min(12.5f, 11.2f * escala))));
		}

		if (panelIzq != null) {
			panelIzq.setPreferredSize(new Dimension(anchoPanelIzq, altoTarjeta - 12));
			panelIzq.setMinimumSize(new Dimension(anchoPanelIzq, altoTarjeta - 12));
			panelIzq.setMaximumSize(new Dimension(anchoPanelIzq, Integer.MAX_VALUE));
		}

		if (centro != null) {
			centro.setPreferredSize(new Dimension(anchoCentro, altoTarjeta - 12));
			centro.setMinimumSize(new Dimension(anchoCentro, altoTarjeta - 12));
			centro.setMaximumSize(new Dimension(anchoCentro, Integer.MAX_VALUE));
		}

		if (nombre != null) {
			nombre.setFont(nombre.getFont().deriveFont(Math.max(13f, Math.min(17f, 15f * escala))));
			nombre.setPreferredSize(new Dimension(anchoCentro, 22));
			nombre.setMinimumSize(new Dimension(anchoCentro, 22));
			nombre.setMaximumSize(new Dimension(anchoCentro, 22));
		}

		if (descripcion != null) {
			String desc = mod.obtenerDescripcionCorta();
			if (desc == null) {
				desc = "";
			}

			desc = desc.replace("\n", " ").replaceAll("\\s+", " ").trim();

			int maxCaracteres = Math.max(120, Math.min(260, (int) (anchoDescripcion * 0.42)));
			if (desc.length() > maxCaracteres) {
				desc = desc.substring(0, maxCaracteres).trim() + "...";
			}

			descripcion.setText(desc);
			descripcion.setFont(descripcion.getFont().deriveFont(Math.max(10.5f, Math.min(12.5f, 11.2f * escala))));
			descripcion.setPreferredSize(new Dimension(anchoDescripcion, altoTarjeta - 34));
			descripcion.setMinimumSize(new Dimension(anchoDescripcion, altoTarjeta - 34));
			descripcion.setMaximumSize(new Dimension(anchoDescripcion, altoTarjeta - 34));
			descripcion.setSize(new Dimension(anchoDescripcion, altoTarjeta - 34));
		}

		tarjeta.setPreferredSize(new Dimension(anchoTarjeta, altoTarjeta));
		tarjeta.setMinimumSize(new Dimension(280, altoTarjeta));
		tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, altoTarjeta));

		tarjeta.revalidate();
		tarjeta.repaint();
	}

	private JLabel crearIconoMod(InternetMod mod) {

		int size = 48;
		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(size, size));
		label.setMinimumSize(new Dimension(size, size));
		label.setMaximumSize(new Dimension(size, size));
		label.setOpaque(true);
		label.setBackground(new java.awt.Color(60, 60, 60));

		String url = mod.urlIcon();

		if (url == null || url.isEmpty()) {
			label.setIcon(iconoPlaceholder(size));
			return label;
		}

		CompletableFuture.runAsync(() -> {
			try {
				java.net.URL u = new java.net.URL(url);
				java.awt.Image img = javax.imageio.ImageIO.read(u);

				if (img != null) {
					java.awt.Image escalada = img.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
					SwingUtilities.invokeLater(() -> label.setIcon(new javax.swing.ImageIcon(escalada)));
				} else {
					SwingUtilities.invokeLater(() -> label.setIcon(iconoPlaceholder(size)));
				}

			} catch (Exception e) {
				SwingUtilities.invokeLater(() -> label.setIcon(iconoPlaceholder(size)));
			}
		});

		return label;
	}

	private javax.swing.Icon iconoPlaceholder(int size) {

		java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size,
				java.awt.image.BufferedImage.TYPE_INT_ARGB);

		java.awt.Graphics2D g = img.createGraphics();
		g.setColor(new java.awt.Color(80, 80, 80));
		g.fillRect(0, 0, size, size);

		g.setColor(new java.awt.Color(120, 120, 120));
		g.drawRect(0, 0, size - 1, size - 1);

		g.dispose();

		return new javax.swing.ImageIcon(img);
	}

	private String limitarTexto(String texto, int maxCaracteres) {

		if (texto == null)
			return "";

		texto = texto.replace("\n", " ").trim();

		if (texto.length() <= maxCaracteres)
			return texto;

		return texto.substring(0, maxCaracteres).trim() + "...";
	}

	private void instalarMod(InternetMod mod) {

		try {

			if (!java.awt.Desktop.isDesktopSupported())
				return;

			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

			String enlace = mod.obtenerEnlaceProyecto();

			if (enlace == null || enlace.isEmpty())
				return;

			desktop.browse(new java.net.URI(enlace));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ===== crearSidebar() =====
	private JPanel crearSidebar() {

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(10, 6, 10, 6));

		// LEFT align for BoxLayout
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setOpaque(true);

		aplicarEstiloSidebar(panel);

		sidebarPanel = panel;
		actualizarSidebar();

		return panel;
	}

	private void cargarArchivosJAR() {

		Path modsDir = Paths.get(System.getProperty("user.dir")).resolve("mods");
		archivosJAR = new ArrayList<>();

		if (!Files.exists(modsDir))
			return;

		try {

			archivosJAR = Files.list(modsDir).filter(Files::isRegularFile).map(p -> p.getFileName().toString())
					.filter(f -> f.endsWith(".jar") || f.endsWith(".jar.disabled") || f.endsWith(".nil")
							|| f.endsWith(".nil.jar") || f.endsWith(".disabled") || f.endsWith(".deactivation"))
					.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

// ===== actualizarSidebar() =====
	private void actualizarSidebar() {

		if (sidebarPanel == null)
			return;

		sidebarPanel.removeAll();

		JLabel titulo = new JLabel("Mods Instalados");
		titulo.setForeground(obtenerColorTexto());
		titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
		titulo.setHorizontalAlignment(JLabel.LEFT);

		sidebarPanel.add(titulo);
		sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		cargarArchivosJAR();

		Path modsDir = Paths.get(System.getProperty("user.dir")).resolve("mods");
		boolean usaCurseForge = modsDir.toString().contains("curseforge");

		for (String archivo : archivosJAR) {

			String nombreBase = obtenerNombreBase(archivo);

			boolean activo = !archivo.endsWith(".disabled") && !archivo.endsWith(".nil")
					&& !archivo.endsWith(".nil.jar") && !archivo.endsWith(".deactivation");

			// Row
			JPanel fila = new JPanel(new BorderLayout(6, 0));
			fila.setOpaque(false);
			fila.setAlignmentX(Component.LEFT_ALIGNMENT);

			fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
			fila.setPreferredSize(new Dimension(10, 26));

			JLabel nombre = crearEtiquetaDeslizante(nombreBase);
			nombre.setHorizontalAlignment(JLabel.LEFT);
			nombre.setAlignmentX(Component.LEFT_ALIGNMENT);

			int anchoSidebar = (sidebarScroll != null && sidebarScroll.getViewport() != null)
					? sidebarScroll.getViewport().getWidth()
					: 160;

			int anchoNombre = Math.max(70, anchoSidebar - 70);
			nombre.setPreferredSize(new Dimension(anchoNombre, 20));
			nombre.setMinimumSize(new Dimension(50, 20));

			SwitchVerde sw = new SwitchVerde();
			sw.setSelected(activo);

			float escalaSwitch = Math.max(0.90f, Math.min(1.20f, getWidth() / 900f));
			sw.actualizarEscala(escalaSwitch);

			sw.addActionListener(e -> {
				try {
					Path origen = modsDir.resolve(archivo);
					Path destino;

					if (sw.isSelected()) {
						// REACTIVAR: quitar extensiones de desactivación, no duplicar .jar
						String nombreReactivado = archivo.replace(".jar.disabled", ".jar")
								.replace(".jar.deactivation", ".jar").replace(".disabled", "")
								.replace(".deactivation", "").replace(".nil.jar", ".jar").replace(".nil", "");

						destino = modsDir.resolve(nombreReactivado);

					} else {
						// DESACTIVAR
						String ext = usaCurseForge ? ".jar.disabled" : ".deactivation";

						if (archivo.endsWith(".jar")) {
							destino = modsDir.resolve(archivo + ext);
						} else {
							destino = modsDir.resolve(archivo + ".jar" + ext);
						}
					}

					Files.move(origen, destino, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
					actualizarSidebar();

				} catch (Exception ex) {
					ex.printStackTrace();
					sw.setSelected(!sw.isSelected());
				}
			});

			aplicarEstiloSidebarItem(fila, sw, nombre);

			fila.add(nombre, BorderLayout.CENTER);
			fila.add(sw, BorderLayout.EAST);

			sidebarPanel.add(fila);
			sidebarPanel.add(Box.createRigidArea(new Dimension(0, 6)));
		}

		sidebarPanel.revalidate();
		sidebarPanel.repaint();
	}

	private JLabel crearEtiquetaDeslizante(String texto) {

		final int maxChars = 16;
		final String base = acortarTexto(texto, maxChars);

		JLabel label = new JLabel(base);
		label.setToolTipText(texto);

		final javax.swing.Timer[] timer = new javax.swing.Timer[1];
		final int[] offset = { 0 };

		label.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {

				if (texto.length() <= maxChars)
					return;

				offset[0] = 0;

				timer[0] = new javax.swing.Timer(110, ev -> {

					offset[0]++;
					if (offset[0] >= texto.length())
						offset[0] = 0;

					String desplazado = texto.substring(offset[0]) + "   " + texto.substring(0, offset[0]);

					label.setText(acortarTexto(desplazado, maxChars));
				});

				timer[0].start();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				if (timer[0] != null)
					timer[0].stop();
				label.setText(base);
			}
		});

		return label;
	}

	private String acortarTexto(String texto, int max) {
		if (texto == null)
			return "";
		if (texto.length() <= max)
			return texto;
		return texto.substring(0, Math.max(0, max - 3)) + "...";
	}

	public void recargarSidebar() {
		actualizarSidebar();
	}

	private String obtenerNombreBase(String archivo) {
		if (archivo.endsWith(".jar.disabled")) {
			return archivo.substring(0, archivo.length() - ".jar.disabled".length());
		} else if (archivo.endsWith(".nil.jar")) {
			return archivo.substring(0, archivo.length() - ".nil.jar".length());
		} else if (archivo.endsWith(".nil")) {
			return archivo.substring(0, archivo.length() - ".nil".length());
		} else if (archivo.endsWith(".disabled")) {
			return archivo.substring(0, archivo.length() - ".disabled".length());
		} else if (archivo.endsWith(".deactivation")) {
			return archivo.substring(0, archivo.length() - ".deactivation".length());
		} else if (archivo.endsWith(".jar")) {
			return archivo.substring(0, archivo.length() - ".jar".length());
		}
		return archivo;
	}

	private JLabel crearIconoMod(InternetMod mod, int size) {

		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(size, size));
		label.setMinimumSize(new Dimension(size, size));
		label.setMaximumSize(new Dimension(size, size));
		label.setOpaque(true);
		label.setBackground(new java.awt.Color(60, 60, 60));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);

		label.setIcon(crearIconoModEscalado(mod, size));

		return label;
	}

	private javax.swing.Icon crearIconoModEscalado(InternetMod mod, int size) {

		String url = mod.urlIcon();

		if (url == null || url.isEmpty()) {
			return iconoPlaceholder(size);
		}

		try {
			java.net.URL u = new java.net.URL(url);
			java.awt.Image img = javax.imageio.ImageIO.read(u);

			if (img != null) {
				java.awt.Image escalada = img.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
				return new javax.swing.ImageIcon(escalada);
			}

		} catch (Exception e) {
			// Ignorar y usar placeholder
		}

		return iconoPlaceholder(size);
	}

	public void establecerPrincipalGUI(com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI principalGUI) {
		this.principalGUI = principalGUI;
	}

	@Override
	public TipoGUI<?> tipo() {
		return TipoGUI.MOD_API_PANEL;
	}

	protected ProveedorMods crearProveedorSeleccionado() {
		String id = proveedorConfig.obtener();

		Supplier<ProveedorMods> proveedor = PROVEEDORES_MODS.get(id);

		if (proveedor == null || !proveedor.get().soportaBusqueda()) {
			for (Map.Entry<String, Supplier<ProveedorMods>> entrada : PROVEEDORES_MODS.entrySet()) {
				ProveedorMods candidato = entrada.getValue().get();

				if (candidato.soportaBusqueda()) {
					proveedorConfig.escribir(entrada.getKey());
					return candidato;
				}
			}

			throw new IllegalStateException("No hay proveedores de mods que soporten búsqueda.");
		}

		return proveedor.get();
	}

	private void seleccionarProveedorEnCombo(String idProveedor) {
		if (comboProveedores == null) {
			return;
		}

		for (int i = 0; i < comboProveedores.getItemCount(); i++) {
			ProveedorRegistrado item = comboProveedores.getItemAt(i);

			if (item.obtenerId().equals(idProveedor)) {
				comboProveedores.setSelectedIndex(i);
				return;
			}
		}
	}

	public static class SwitchVerde extends JCheckBox {

		private int w = 34;
		private int h = 16;

		public SwitchVerde() {
			setOpaque(false);
			setFocusPainted(false);
			setBorderPainted(false);
			setContentAreaFilled(false);
			actualizarEscala(1.0f);
		}

		public void actualizarEscala(float escala) {
			w = Math.max(28, Math.min(52, Math.round(34 * escala)));
			h = Math.max(14, Math.min(24, Math.round(16 * escala)));

			setPreferredSize(new Dimension(w, h));
			setMinimumSize(new Dimension(w, h));
			setMaximumSize(new Dimension(w, h));
			revalidate();
			repaint();
		}

		@Override
		protected void paintComponent(java.awt.Graphics g) {

			java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
			g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

			int x = 0;
			int y = (getHeight() - h) / 2;

			java.awt.Color fondo = isSelected() ? new java.awt.Color(110, 210, 80) : new java.awt.Color(90, 90, 90);

			g2.setColor(fondo);
			g2.fillRoundRect(x, y, w, h, h, h);

			g2.setColor(new java.awt.Color(0, 0, 0, 100));
			g2.drawRoundRect(x, y, w - 1, h - 1, h, h);

			int knobSize = h - 4;
			int knobX = isSelected() ? x + w - knobSize - 2 : x + 2;

			g2.setColor(java.awt.Color.WHITE);
			g2.fillOval(knobX, y + 2, knobSize, knobSize);

			g2.setColor(new java.awt.Color(0, 0, 0, 80));
			g2.drawOval(knobX, y + 2, knobSize - 1, knobSize - 1);

			g2.dispose();
		}
	}

}