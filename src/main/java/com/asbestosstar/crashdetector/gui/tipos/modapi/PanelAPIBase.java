package com.asbestosstar.crashdetector.gui.tipos.modapi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.dto.modpack.InternetMod;
import com.asbestosstar.crashdetector.dto.modpack.PaginaMods;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.editorgui.EditorGUI;

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
		add(botonVolver, BorderLayout.NORTH);

		JPanel topPanel = crearPanelBusqueda();
		add(topPanel, BorderLayout.NORTH);

		JPanel mainPanel = new JPanel(new BorderLayout());
		aplicarEstiloPrincipal(mainPanel);

		sidebarPanel = crearSidebar();
		mainPanel.add(sidebarPanel, BorderLayout.WEST);

		modListPanel = new JPanel();
		modListPanel.setLayout(new BoxLayout(modListPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(modListPanel);
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
		modListPanel.revalidate();
		modListPanel.repaint();

		CompletableFuture.runAsync(() -> {
			try {
				PaginaMods pagina = proveedorActual.buscarMods("ES", 0, termino);
				SwingUtilities.invokeLater(() -> {
					for (InternetMod mod : pagina.obtenerListaMods()) {
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
		JPanel tarjeta = new JPanel(new BorderLayout());
		tarjeta.setBorder(javax.swing.BorderFactory.createCompoundBorder(
				javax.swing.BorderFactory.createLineBorder(java.awt.Color.DARK_GRAY, 1),
				javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		aplicarEstiloTarjetaMod(tarjeta);

		JLabel nombre = new JLabel("<html><b>" + mod.obtenerNombre() + "</b></html>");
		nombre.setForeground(java.awt.Color.WHITE);
		tarjeta.add(nombre, BorderLayout.NORTH);

		JButton instalar = new JButton("Instalar");
		instalar.addActionListener(e -> {
		});
		tarjeta.add(instalar, BorderLayout.EAST);

		return tarjeta;
	}

	private JPanel crearSidebar() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(10, 5, 10, 5));
		aplicarEstiloSidebar(panel);

		JLabel titulo = new JLabel("Mods Instalados");
		titulo.setForeground(obtenerColorTexto());
		titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		panel.add(titulo);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		return panel;
	}

	private void cargarArchivosJAR() {
		Path modsDir = Statics.carpeta.resolve("mods");
		archivosJAR = new ArrayList<>();
		if (Files.exists(modsDir)) {
			try {
				archivosJAR = Files.list(modsDir).filter(Files::isRegularFile).map(p -> p.getFileName().toString())
						.filter(f -> f.endsWith(".jar") || f.endsWith(".nil.jar") || f.endsWith(".nil")
								|| f.endsWith(".disabled") || f.endsWith(".deactivation"))
						.collect(Collectors.toList());
			} catch (IOException ignored) {
			}
		}
	}

	private void actualizarSidebar() {
		sidebarPanel.removeAll();
		JLabel titulo = new JLabel("Mods Instalados");
		titulo.setForeground(obtenerColorTexto());
		titulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
		sidebarPanel.add(titulo);
		sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		String carpetaModsPath = Statics.carpeta.resolve("mods").toString();
		boolean usaCurseForge = carpetaModsPath.contains("curseforge");

		for (String archivo : archivosJAR) {
			String nombreBase = obtenerNombreBase(archivo);
			boolean activo = !archivo.endsWith(".disabled") && !archivo.endsWith(".nil")
					&& !archivo.endsWith(".nil.jar") && !archivo.endsWith(".deactivation");

			JPanel fila = new JPanel(new BorderLayout());
			JCheckBox checkBox = new JCheckBox();
			checkBox.setSelected(activo);
			JLabel nombreLabel = new JLabel(nombreBase);

			checkBox.addActionListener(evt -> {
				try {
					Path origen = Statics.carpeta.resolve("mods").resolve(archivo);
					Path destino;
					if (checkBox.isSelected()) {
						// Activar → quitar sufijo
						destino = Statics.carpeta.resolve("mods").resolve(nombreBase + ".jar");
					} else {
						// Desactivar → usar extensión según entorno
						String ext = usaCurseForge ? ".jar.disabled" : ".deactivation";
						destino = Statics.carpeta.resolve("mods").resolve(nombreBase + ".jar" + ext);
					}
					Files.move(origen, destino);
					cargarArchivosJAR();
					actualizarSidebar();
				} catch (IOException ignored) {
				}
			});

			aplicarEstiloSidebarItem(fila, checkBox, nombreLabel);
			fila.add(checkBox, BorderLayout.WEST);
			fila.add(nombreLabel, BorderLayout.CENTER);
			sidebarPanel.add(fila);
			sidebarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		}

		sidebarPanel.revalidate();
		sidebarPanel.repaint();
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

}