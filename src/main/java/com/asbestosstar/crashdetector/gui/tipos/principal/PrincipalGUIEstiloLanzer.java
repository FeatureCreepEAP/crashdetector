package com.asbestosstar.crashdetector.gui.tipos.principal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.ComboIdiomasConIcono;
import com.asbestosstar.crashdetector.gui.DialogoCompartir;
import com.asbestosstar.crashdetector.gui.PanelQuickFix;

public class PrincipalGUIEstiloLanzer extends PrincipalGUI {

	public static String ID = "estilo_lanzer";
	// Colores del tema (se setean en applyAppearance)
	private Color colorFondo, colorTexto, colorBoton, colorCajaTexto, colorEnlace;

	// Contenedores y controles que estilizamos
	private JPanel root;
	private JPanel contenido;
	private JPanel barraLateral;
	private JPanel panelInferior;

	private JButton botonVolver;
	private JButton botonConfiguracion;
	private JButton botonQuickFix;
	private JButton btnCompartir, btnActualizar, btnArchivos;

	private JComboBox<String> comboIdioma;
	private JCheckBox chkIdiomaSistema;

	/** Visor técnico del informe (compartido) */
	JScrollPane scrollPane;

	
	/**
	 * MUY IMPORTANTE. Necesitas tener una instancia. Necesitas un super a aqui en tu versiones
	 * @param tiempoFallo
	 * @param cerrojo
	 */
	public void constructir(Instant tiempoFallo, CountDownLatch cerrojo) {
		this.tiempoFallo = tiempoFallo;
		this.cerrojo = cerrojo;

		// Delegar TODO el layout/estética a la subclase:
		constuctirFormato(BOTONES_REGISTRADOS, botonesSidebarInicializados);
		scrollPane = new JScrollPane(pantalla());

		// Aplicar apariencia inicial (colores/labels) totalmente en la subclase
		recargarApariencia();
		setVisible(true);
	}


	@Override
	protected void constuctirFormato(List<Supplier<BotonDeBarraLateralDerecha>> registrados,
			Map<BotonDeBarraLateralDerecha, JButton> salidaBotonesSidebar) {
		// Raíz
		root = new JPanel(new BorderLayout(5, 5));
		setContentPane(root);

		// Centro con el visor
		contenido = new JPanel(new BorderLayout());
		contenido.add(pantalla(), BorderLayout.CENTER);
		root.add(contenido, BorderLayout.CENTER);

		// Panel inferior (idioma + acciones)
		panelInferior = crearPanelInferior();
		root.add(panelInferior, BorderLayout.SOUTH);

		// Barra lateral (logo + volver + botones registrados)
		barraLateral = crearBarraLateralDerecha();
		// Logo
		JLabel logo = new JLabel(new ImageIcon(MonitorDePID.carpeta.resolve("imagenes/cd_logo.png").toString()));
		logo.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		logo.setBorder(new EmptyBorder(10, 10, 10, 10));
		barraLateral.add(logo);

		// Volver
		botonVolver = crearBotonTexto(MonitorDePID.idioma.volver());
		botonVolver.addActionListener(e -> {
			contenido.removeAll();
			contenido.add(scrollPane, BorderLayout.CENTER);
			contenido.revalidate();
			contenido.repaint();
			botonVolver.setEnabled(false);
			recargarApariencia(); // actualiza labels/colores
		});
		barraLateral.add(botonVolver);
		barraLateral.add(Box.createVerticalStrut(10));

		// Botones dinámicos
		for (Supplier<BotonDeBarraLateralDerecha> sup : registrados) {
			BotonDeBarraLateralDerecha b = sup.get();
			JButton btn = crearBotonTexto(b.tipo().etiquetaDelBoton());
			btn.addActionListener(e -> b.init());
			barraLateral.add(btn);
			salidaBotonesSidebar.put(b, btn);
		}
		root.add(barraLateral, BorderLayout.EAST);

		// Ventana
		setSize(1050, 650);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	@Override
	protected void aplicarApariancia(Map<BotonDeBarraLateralDerecha, JButton> botonesSidebar) {
		Config cfg = Config.obtenerInstancia();
		colorFondo = Config.convertirAColor(cfg.obtenerColorFondo());
		colorTexto = Config.convertirAColor(cfg.obtenerColorTexto());
		colorBoton = Config.convertirAColor(cfg.obtenerColorBoton());
		colorCajaTexto = Config.convertirAColor(cfg.obtenerColorCajaTexto());
		colorEnlace = Config.convertirAColor(cfg.obtenerColorEnlace());

		setTitle(cfg.obtenerNombreCD());

		// Visor
		pantalla().setBackground(colorCajaTexto);
		pantalla().setForeground(colorEnlace);
		scrollPane.getViewport().setBackground(colorCajaTexto);

		// Contenedores
		if (root != null)
			root.setBackground(colorFondo);
		if (contenido != null)
			contenido.setBackground(colorFondo);
		if (panelInferior != null)
			panelInferior.setBackground(colorFondo);
		if (barraLateral != null)
			barraLateral.setBackground(colorBoton.darker());

		// Volver
		if (botonVolver != null) {
			estilizarBoton(botonVolver);
			botonVolver.setText(MonitorDePID.idioma.volver());
		}

		// Idioma
		if (comboIdioma != null) {
			if (!esMac()) {
				comboIdioma.setBackground(colorBoton);
				comboIdioma.setForeground(colorTexto);
			} else {
				comboIdioma.setBackground(null);
				comboIdioma.setForeground(null);
			}
		}
		if (chkIdiomaSistema != null) {
			chkIdiomaSistema.setForeground(colorTexto);
			chkIdiomaSistema.setOpaque(false);
		}

		// Acciones
		if (botonQuickFix != null) {
			botonQuickFix.setText("QuickFix");
			botonQuickFix.setEnabled(MonitorDePID.analizador.obtenerSoluciones().size() > 0);
			estilizarBoton(botonQuickFix);
		}
		if (botonConfiguracion != null) {
			botonConfiguracion.setText(MonitorDePID.idioma.config());
			estilizarBoton(botonConfiguracion);
		}
		if (btnCompartir != null)
			btnCompartir.setToolTipText(MonitorDePID.idioma.botonDeCompartirInforme());
		if (btnActualizar != null)
			btnActualizar.setToolTipText(MonitorDePID.idioma.actualizar());
		if (btnArchivos != null)
			btnArchivos.setToolTipText(MonitorDePID.idioma.abrirCarpeta());

		// Sidebar dinámicos
		for (Entry<BotonDeBarraLateralDerecha, JButton> e : botonesSidebar.entrySet()) {
			JButton btn = e.getValue();
			btn.setText(e.getKey().tipo().etiquetaDelBoton());
			estilizarBoton(btn);
		}
	}

	/* =================== Layout: panel inferior =================== */
	private JPanel crearPanelInferior() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new EmptyBorder(10, 20, 10, 20));

		// Idioma (izquierda)
		JPanel izq = new JPanel();
		izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));

		comboIdioma = new ComboIdiomasConIcono(mapaParaComboBoxIdiomas());
		comboIdioma.setMaximumSize(new java.awt.Dimension(200, 30));
		comboIdioma.setPreferredSize(new java.awt.Dimension(200, 30));
		comboIdioma.setSelectedItem(nombreDeIdiomaDesdeCondigo(MonitorDePID.idioma.codigo()));

		chkIdiomaSistema = new JCheckBox(MonitorDePID.idioma.usarIdiomaDelSistema());
		chkIdiomaSistema.setHorizontalAlignment(SwingConstants.LEFT);
		chkIdiomaSistema.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		chkIdiomaSistema.setMaximumSize(new java.awt.Dimension(200, 30));

		boolean existePref = java.nio.file.Files.exists(com.asbestosstar.crashdetector.Idioma.archivo.toPath());
		boolean usarSistema = !existePref;
		chkIdiomaSistema.setSelected(usarSistema);
		comboIdioma.setEnabled(!usarSistema);

		comboIdioma.addActionListener(e -> {
			chkIdiomaSistema.setSelected(false);
			comboIdioma.setEnabled(true);
			String seleccion = (String) comboIdioma.getSelectedItem();
			String codigo = com.asbestosstar.crashdetector.gui.CrashDetectorGUI.obtenerCodigoIdioma(seleccion);
			if (codigo != null) {
				try (java.io.BufferedWriter w = java.nio.file.Files.newBufferedWriter(
						com.asbestosstar.crashdetector.Idioma.archivo.toPath(), java.nio.file.StandardOpenOption.CREATE,
						java.nio.file.StandardOpenOption.TRUNCATE_EXISTING)) {
					w.write(codigo);
				} catch (java.io.IOException ex) {
					com.asbestosstar.crashdetector.CrashDetectorLogger.logException(ex);
				}
				MonitorDePID.idioma = com.asbestosstar.crashdetector.Idioma.detectar();
				recargarApariencia();
			}
		});

		chkIdiomaSistema.addActionListener(e -> {
			boolean usarSis = chkIdiomaSistema.isSelected();
			comboIdioma.setEnabled(!usarSis);
			try {
				if (usarSis) {
					java.nio.file.Files.deleteIfExists(com.asbestosstar.crashdetector.Idioma.archivo.toPath());
				} else {
					String seleccion = (String) comboIdioma.getSelectedItem();
					String codigo = com.asbestosstar.crashdetector.gui.CrashDetectorGUI.obtenerCodigoIdioma(seleccion);
					if (codigo != null) {
						try (java.io.BufferedWriter w = java.nio.file.Files.newBufferedWriter(
								com.asbestosstar.crashdetector.Idioma.archivo.toPath(),
								java.nio.file.StandardOpenOption.CREATE,
								java.nio.file.StandardOpenOption.TRUNCATE_EXISTING)) {
							w.write(codigo);
						}
					}
				}
			} catch (java.io.IOException ex) {
				com.asbestosstar.crashdetector.CrashDetectorLogger.logException(ex);
			}
			MonitorDePID.idioma = com.asbestosstar.crashdetector.Idioma.detectar();
			recargarApariencia();
		});

		izq.add(comboIdioma);
		izq.add(chkIdiomaSistema);

		// Centro: QuickFix + Config
		JPanel centro = new JPanel();
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

		botonQuickFix = crearBotonTexto("QuickFix");
		botonQuickFix.addActionListener(e -> mostrarVentanaQuickFix());
		centro.add(botonQuickFix);
		centro.add(Box.createVerticalStrut(10));

		botonConfiguracion = crearBotonTexto(MonitorDePID.idioma.config());
		botonConfiguracion.addActionListener(e -> {
			contenido.removeAll();
			contenido.add(new com.asbestosstar.crashdetector.gui.ConfigPanel(this), BorderLayout.CENTER);
			contenido.revalidate();
			contenido.repaint();
			if (botonVolver != null)
				botonVolver.setEnabled(true);
			recargarApariencia();
		});
		centro.add(botonConfiguracion);

		// Derecha: acciones con icono (tooltips se ponen en applyAppearance)
		JPanel der = new JPanel(new java.awt.GridLayout(1, 3, 10, 10));

		btnCompartir = new JButton(
				new ImageIcon(MonitorDePID.carpeta.resolve("imagenes/boton_compartir.png").toString()));
		btnCompartir.setText("");
		btnCompartir.setBorder(BorderFactory.createEmptyBorder());
		btnCompartir.setFocusPainted(false);
		btnCompartir.addActionListener(e -> new DialogoCompartir(this, tiempoFallo).setVisible(true));

		btnActualizar = new JButton(
				new ImageIcon(MonitorDePID.carpeta.resolve("imagenes/boton_actualizar.png").toString()));
		btnActualizar.setText("");
		btnActualizar.setBorder(BorderFactory.createEmptyBorder());
		btnActualizar.setFocusPainted(false);
		btnActualizar.addActionListener(e -> recargar()); // usa default del interface

		btnArchivos = new JButton(
				new ImageIcon(MonitorDePID.carpeta.resolve("imagenes/boton_archivos.png").toString()));
		btnArchivos.setText("");
		btnArchivos.setBorder(BorderFactory.createEmptyBorder());
		btnArchivos.setFocusPainted(false);
		btnArchivos.addActionListener(
				e -> com.asbestosstar.crashdetector.gui.CrashDetectorGUI.abrirDirectorioEnExplorador());

		der.add(btnCompartir);
		der.add(btnActualizar);
		der.add(btnArchivos);

		panel.add(izq, BorderLayout.WEST);
		panel.add(centro, BorderLayout.CENTER);
		panel.add(der, BorderLayout.EAST);
		return panel;
	}

	private JPanel crearBarraLateralDerecha() {
		JPanel barra = new JPanel();
		barra.setLayout(new BoxLayout(barra, BoxLayout.Y_AXIS));
		barra.setPreferredSize(new java.awt.Dimension(230, 0));
		return barra;
	}

	private void mostrarVentanaQuickFix() {
		JDialog dialogo = new JDialog(this, "QuickFix", true);
		dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialogo.setSize(600, 400);
		dialogo.setLocationRelativeTo(this);

		JPanel panelQuickFix = new JPanel(new BorderLayout());
		panelQuickFix.setBackground(colorFondo);

		PanelQuickFix contenidoQuickFix = new PanelQuickFix();
		for (QuickFix q : MonitorDePID.analizador.obtenerSoluciones()) {
			contenidoQuickFix.agregarQuickFix(q);
		}

		JPanel sur = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
		sur.setBackground(colorFondo);

		JButton cerrar = crearBotonTexto(MonitorDePID.idioma.volver());
		estilizarBoton(cerrar);
		cerrar.addActionListener(e -> dialogo.dispose());
		sur.add(cerrar);

		panelQuickFix.add(contenidoQuickFix, BorderLayout.CENTER);
		panelQuickFix.add(sur, BorderLayout.SOUTH);

		dialogo.getContentPane().add(panelQuickFix);
		dialogo.setVisible(true);
	}

	private JButton crearBotonTexto(String texto) {
		JButton boton = new JButton(texto);
		boton.setAlignmentX(Component.CENTER_ALIGNMENT);
		estilizarBoton(boton); // aplica estilo actual
		return boton;
	}

	private void estilizarBoton(JButton boton) {
		if (!esMac()) {
			boton.setBackground(colorBoton);
			boton.setForeground(colorTexto);
			boton.setContentAreaFilled(true);
			boton.setFocusPainted(false);
			boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		} else {
			boton.setContentAreaFilled(false);
		}
		boton.setMaximumSize(new java.awt.Dimension(250, 40));
		boton.setMinimumSize(new java.awt.Dimension(250, 40));
		boton.setPreferredSize(new java.awt.Dimension(250, 40));
	}
	
	@Override
	public String id() {
		return ID;
	}
	
	
	
	
	
}
