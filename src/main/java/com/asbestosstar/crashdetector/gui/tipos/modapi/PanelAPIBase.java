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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Clase base abstracta sin inicialización en constructor. Toda la lógica debe
 * iniciarse explícitamente mediante `init()`.
 */
public abstract class PanelAPIBase extends JPanel implements CrashDetectorGUI {

	public static Map<String, Supplier<PanelAPIBase>> GUIS = new HashMap<>();

	protected JTextField searchBar;
	protected JPanel modListPanel;
	protected JPanel sidebarPanel;
	protected List<String> archivosJAR;
	protected ProveedorMods proveedorActual;

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

	// Constructor mínimo
	public PanelAPIBase() {
		setLayout(new BorderLayout());
	}

	// Inicialización explícita
	public void init() {

		inicializarColores();
		setBackground(obtenerColorFondo());

		JButton botonVolver = new JButton("← Volver");
		aplicarEstiloVolver(botonVolver);
		botonVolver.setFocusPainted(false);
		botonVolver.addActionListener(e -> {
			if (getParent() instanceof com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI) {
				((com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI) getParent()).volver();
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

		// ---- Sidebar (scrollable) ----
		sidebarPanel = crearSidebar();

		JScrollPane sidebarScroll = new JScrollPane(sidebarPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sidebarScroll.setBorder(null);

		int sidebarW = 140;
		sidebarScroll.setPreferredSize(new Dimension(sidebarW, 0));
		sidebarScroll.setMinimumSize(new Dimension(sidebarW, 0));
		sidebarScroll.getVerticalScrollBar().setUnitIncrement(14);

		sidebarScroll.getViewport().setOpaque(true);
		sidebarScroll.getViewport().setBackground(obtenerColorFondo().darker());

		mainPanel.add(sidebarScroll, BorderLayout.WEST);

		// ---- Mod list (scrollable) ----
		modListPanel = new JPanel();
		modListPanel.setLayout(new BoxLayout(modListPanel, BoxLayout.Y_AXIS));

		JScrollPane scrollPane = new JScrollPane(modListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		mainPanel.add(scrollPane, BorderLayout.CENTER);

		add(mainPanel, BorderLayout.CENTER);

		cargarArchivosJAR();
		proveedorActual = crearProveedorMods();
		buscarMods("");
	}

	// === Resto de métodos idénticos a la versión anterior ===
	// (crearPanelBusqueda, buscarMods, crearTarjetaMod, crearSidebar,
	// cargarArchivosJAR, etc.)
	// Pero ahora usan el método `obtenerExtensionDesactivacion()` para decidir la
	// extensión

	private JPanel crearPanelBusqueda() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		panel.setBackground(obtenerColorFondo());

		JLabel label = new JLabel("Buscar mods:");
		label.setForeground(obtenerColorTexto());
		panel.add(label);

		searchBar = new JTextField(20);
		aplicarEstiloBusqueda(searchBar);
		searchBar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				buscarMods(searchBar.getText());
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				buscarMods(searchBar.getText());
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				buscarMods(searchBar.getText());
			}
		});
		panel.add(searchBar);
		return panel;
	}

	private void buscarMods(String termino) {

		modListPanel.removeAll();

		CompletableFuture.runAsync(() -> {
			try {
				PaginaMods pagina = proveedorActual.buscarMods(MonitorDePID.idioma.codigo().toUpperCase(), 0, termino);

				// Evitar duplicados por URL de proyecto
				java.util.Set<String> urlsVistas = new java.util.HashSet<>();

				SwingUtilities.invokeLater(() -> {

					for (InternetMod mod : pagina.obtenerListaMods()) {

						String enlace = mod.obtenerEnlaceProyecto();

						// Validaciones básicas
						if (enlace == null || enlace.isEmpty())
							continue;

						if (!enlace.toLowerCase().contains("curseforge.com"))
							continue;

						// FILTRO ANTI-DUPLICADOS
						if (!urlsVistas.add(enlace))
							continue;

						modListPanel.add(crearTarjetaMod(mod));
					}

					modListPanel.revalidate();
					modListPanel.repaint();
				});

			} catch (IOException ex) {
				SwingUtilities.invokeLater(() -> {
					modListPanel.add(new JLabel("Error al cargar mods."));
					modListPanel.revalidate();
				});
			}
		});
	}

	protected JPanel crearTarjetaMod(InternetMod mod) {

		// Altura mayor, estilo TLMods
		int anchoMaximo = 650;
		int altoTarjeta = 96;

		JPanel tarjeta = new JPanel(new BorderLayout(10, 8));
		tarjeta.setMaximumSize(new Dimension(anchoMaximo, altoTarjeta));
		tarjeta.setPreferredSize(new Dimension(anchoMaximo, altoTarjeta));
		tarjeta.setMinimumSize(new Dimension(anchoMaximo, altoTarjeta));
		tarjeta.setAlignmentX(Component.CENTER_ALIGNMENT);

		tarjeta.setBorder(javax.swing.BorderFactory.createCompoundBorder(
				javax.swing.BorderFactory.createLineBorder(java.awt.Color.DARK_GRAY, 1),
				javax.swing.BorderFactory.createEmptyBorder(8, 10, 8, 10)));

		aplicarEstiloTarjetaMod(tarjeta);

		// ================= IZQUIERDA: ICONO + BOTÓN =================
		JPanel panelIzq = new JPanel();
		panelIzq.setLayout(new BoxLayout(panelIzq, BoxLayout.Y_AXIS));
		panelIzq.setOpaque(false);

		JLabel icono = crearIconoMod(mod);
		icono.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton instalar = new JButton("Instalar");
		instalar.setAlignmentX(Component.CENTER_ALIGNMENT);
		instalar.setMaximumSize(new Dimension(72, 22));
		instalar.setPreferredSize(new Dimension(72, 22));
		instalar.setBackground(obtenerColorBoton());
		instalar.setForeground(java.awt.Color.WHITE);

		instalar.addActionListener(e -> instalarMod(mod));

		panelIzq.add(icono);
		panelIzq.add(Box.createRigidArea(new Dimension(0, 6)));
		panelIzq.add(instalar);

		// ================= CENTRO: TEXTO =================
		JPanel centro = new JPanel();
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		centro.setOpaque(false);

		JLabel nombre = new JLabel(mod.obtenerNombre());
		nombre.setForeground(obtenerColorTexto());
		nombre.setFont(nombre.getFont().deriveFont(15f));

		String desc = limitarTexto(mod.obtenerDescripcionCorta(), 420);
		JLabel descripcion = new JLabel("<html><div style='width:460px;'>" + desc + "</div></html>");
		descripcion.setForeground(java.awt.Color.LIGHT_GRAY);

		centro.add(nombre);
		centro.add(Box.createRigidArea(new Dimension(0, 4)));
		centro.add(descripcion);

		// ================= ENSAMBLADO =================
		tarjeta.add(panelIzq, BorderLayout.WEST);
		tarjeta.add(centro, BorderLayout.CENTER);

		return tarjeta;
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

			nombre.setPreferredSize(new Dimension(86, 20));
			nombre.setMinimumSize(new Dimension(86, 20));

			SwitchVerde sw = new SwitchVerde();
			sw.setSelected(activo);

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

	@Override
	public TipoGUI<?> tipo() {
		return TipoGUI.MOD_API_PANEL;
	}

	public static class SwitchVerde extends JCheckBox {

		private static final int W = 34;
		private static final int H = 16;

		public SwitchVerde() {
			setOpaque(false);
			setFocusPainted(false);
			setBorderPainted(false);
			setContentAreaFilled(false);

			setPreferredSize(new Dimension(W, H));
			setMinimumSize(new Dimension(W, H));
			setMaximumSize(new Dimension(W, H));
		}

		@Override
		protected void paintComponent(java.awt.Graphics g) {

			java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
			g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

			int x = 0;
			int y = (getHeight() - H) / 2;

			java.awt.Color fondo = isSelected() ? new java.awt.Color(110, 210, 80) : new java.awt.Color(90, 90, 90);

			g2.setColor(fondo);
			g2.fillRect(x, y, W, H);

			g2.setColor(new java.awt.Color(0, 0, 0, 100));
			g2.drawRect(x, y, W - 1, H - 1);

			int knobSize = H - 4;
			int knobX = isSelected() ? x + W - knobSize - 2 : x + 2;

			g2.setColor(java.awt.Color.WHITE);
			g2.fillRect(knobX, y + 2, knobSize, knobSize);

			g2.setColor(new java.awt.Color(0, 0, 0, 80));
			g2.drawRect(knobX, y + 2, knobSize - 1, knobSize - 1);

			g2.dispose();
		}
	}

}