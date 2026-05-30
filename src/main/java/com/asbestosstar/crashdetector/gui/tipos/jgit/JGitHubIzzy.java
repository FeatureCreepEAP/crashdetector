package com.asbestosstar.crashdetector.gui.tipos.jgit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.jgit.forge.ConfigCrearRepositorio;
import com.asbestosstar.crashdetector.gui.tipos.jgit.forge.GitForgeAPI;
import com.asbestosstar.crashdetector.gui.tipos.jgit.forge.RegistroGitForge;
import com.asbestosstar.crashdetector.gui.tipos.jgit.forge.ResultadoCrearRepositorio;

/**
 * Hub de JGit con apariencia basada en Izzy.
 */
public class JGitHubIzzy extends JGitHubBase {

	public static final String ID = "izzy";
	private static final long serialVersionUID = 1L;

	public ConfigColor colorFondo = ConfigColor.de("tema.izzy.jgit.fondo", new Color(26, 34, 48));
	public ConfigColor colorPanel = ConfigColor.de("tema.izzy.jgit.panel", new Color(38, 50, 70));
	public ConfigColor colorTexto = ConfigColor.de("tema.izzy.jgit.texto", Color.WHITE);
	public ConfigColor colorBorde = ConfigColor.de("tema.izzy.jgit.borde", new Color(80, 120, 150));
	public ConfigColor colorBoton = ConfigColor.de("tema.izzy.jgit.boton", new Color(60, 85, 115));
	public ConfigColor colorRetrato = ConfigColor.de("tema.izzy.jgit.retrato", new Color(20, 26, 38));

	public ConfigString remoteGuardado = ConfigString.de("jgit.remote.origin.url", "");
	public ConfigString forgePredeterminado = ConfigString.de("jgit.forge.predeterminado", "pagure");

	private JPanel panelPrincipal;
	private JPanel panelRetrato;
	private JLabel etiquetaRetrato;
	private JEditorPane estado;

	private JButton botonAbrirCarpetaJGit;
	private JButton botonAbrirMaven;
	private JButton botonCrearRepo;
	private JButton botonRemoteManual;
	private JButton botonCrearRemoteAPI;
	private JButton botonCommitManual;
	private JButton botonPushManual;
	private JButton botonAbrirGuiSwing;
	private JButton botonDescargarFaltantes;

	private JCheckBox checkAutoCommit;
	private JCheckBox checkAutoPush;

	private JComboBox<ForgeRegistrada> comboForge;

	private static class ForgeRegistrada {

		private final String id;
		private final String nombre;

		public ForgeRegistrada(String id, String nombre) {
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

	@Override
	public void init() {
		inicializarVentana();
		setVisible(true);
	}

	private void inicializarVentana() {
		if (isDisplayable()) {
			actualizarEstadoBotones();
			return;
		}

		RegistroGitForge.registrarPredeterminados();

		setTitle(MonitorDePID.idioma.jgitTituloIzzy());
		setSize(1000, 650);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		panelPrincipal = new JPanel(new BorderLayout());
		panelPrincipal.setBackground(colorFondo.obtener());

		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
		panelContenido.setBackground(colorFondo.obtener());

		panelContenido.add(crearSeccionInstalacion());
		panelContenido.add(crearSeccionRepositorio());
		panelContenido.add(crearSeccionRemote());
		panelContenido.add(crearSeccionAutomaticos());
		panelContenido.add(crearSeccionHerramientas());

		estado = new JEditorPane("text/html", "");
		estado.setEditable(false);
		estado.setOpaque(true);
		estado.setBackground(colorPanel.obtener());
		estado.setForeground(colorTexto.obtener());

		JScrollPane scrollEstado = new JScrollPane(estado);
		scrollEstado.setBorder(crearBorde(MonitorDePID.idioma.jgitEstado()));
		scrollEstado.setBackground(colorPanel.obtener());
		scrollEstado.getViewport().setBackground(colorPanel.obtener());
		scrollEstado.getHorizontalScrollBar().setBackground(colorPanel.obtener());
		scrollEstado.getVerticalScrollBar().setBackground(colorPanel.obtener());

		panelContenido.add(scrollEstado);

		JScrollPane scrollPrincipal = new JScrollPane(panelContenido);
		scrollPrincipal.setBorder(null);
		scrollPrincipal.setBackground(colorFondo.obtener());
		scrollPrincipal.getViewport().setBackground(colorFondo.obtener());
		scrollPrincipal.getVerticalScrollBar().setUnitIncrement(14);

		panelPrincipal.add(scrollPrincipal, BorderLayout.CENTER);
		panelPrincipal.add(crearPanelRetrato(), BorderLayout.EAST);

		add(panelPrincipal, BorderLayout.CENTER);

		cargarRetrato();
		conectarEventos();
		actualizarEstadoBotones();

		setLocationRelativeTo(null);
	}

	private JPanel crearSeccionInstalacion() {
		JPanel p = crearPanelSeccion(MonitorDePID.idioma.jgitSeccionInstalacion());

		botonAbrirCarpetaJGit = crearBoton(MonitorDePID.idioma.jgitAbrirCarpetaInstalacion());
		botonAbrirMaven = crearBoton(MonitorDePID.idioma.jgitAbrirPaginaDescarga());
		botonDescargarFaltantes = crearBoton(MonitorDePID.idioma.jgitDescargarDependenciasFaltantes());

		p.add(botonAbrirCarpetaJGit);
		p.add(botonAbrirMaven);
		p.add(botonDescargarFaltantes);

		return p;
	}

	private JPanel crearSeccionRepositorio() {
		JPanel p = crearPanelSeccion(MonitorDePID.idioma.jgitSeccionRepositorio());

		botonCrearRepo = crearBoton(MonitorDePID.idioma.jgitCrearRepositorioLocal());
		botonCommitManual = crearBoton(MonitorDePID.idioma.jgitCommitManual());

		p.add(botonCrearRepo);
		p.add(botonCommitManual);

		return p;
	}

	private JPanel crearSeccionRemote() {
		JPanel p = crearPanelSeccion(MonitorDePID.idioma.jgitSeccionRemote());

		comboForge = new JComboBox<ForgeRegistrada>();

		for (Map.Entry<String, GitForgeAPI> entrada : RegistroGitForge.obtenerApis().entrySet()) {
			GitForgeAPI api = entrada.getValue();

			if (api == null) {
				continue;
			}

			comboForge.addItem(new ForgeRegistrada(api.id(), api.nombreParaMostrar()));
		}

		seleccionarForgeEnCombo(forgePredeterminado.obtener());

		botonRemoteManual = crearBoton(MonitorDePID.idioma.jgitEstablecerRemoteManual());
		botonCrearRemoteAPI = crearBoton(MonitorDePID.idioma.jgitCrearRemoteConAPI());
		botonPushManual = crearBoton(MonitorDePID.idioma.jgitPushManual());

		p.add(comboForge);
		p.add(botonRemoteManual);
		p.add(botonCrearRemoteAPI);
		p.add(botonPushManual);

		return p;
	}

	private JPanel crearSeccionAutomaticos() {
		JPanel p = crearPanelSeccion(MonitorDePID.idioma.jgitSeccionAutomaticos());

		checkAutoCommit = new JCheckBox(MonitorDePID.idioma.jgitAutoCommitDespuesBackup());
		checkAutoPush = new JCheckBox(MonitorDePID.idioma.jgitAutoPushDespuesCommit());

		prepararCheck(checkAutoCommit);
		prepararCheck(checkAutoPush);

		checkAutoCommit.setSelected(JGitAutoCommit.AUTO_COMMIT.obtener());
		checkAutoPush.setSelected(JGitAutoCommit.AUTO_PUSH.obtener());

		p.add(checkAutoCommit);
		p.add(checkAutoPush);

		return p;
	}

	private JPanel crearSeccionHerramientas() {
		JPanel p = crearPanelSeccion(MonitorDePID.idioma.jgitSeccionHerramientas());

		botonAbrirGuiSwing = crearBoton(MonitorDePID.idioma.jgitAbrirGuiSwing());

		p.add(botonAbrirGuiSwing);

		return p;
	}

	private JPanel crearPanelRetrato() {
		panelRetrato = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(colorRetrato.obtener());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};

		panelRetrato.setLayout(new BorderLayout());
		panelRetrato.setPreferredSize(new Dimension(240, 0));
		panelRetrato.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorBorde.obtener()),
				MonitorDePID.idioma.jgitRetratoIzzy(), TitledBorder.CENTER, TitledBorder.TOP, null,
				colorTexto.obtener()));

		etiquetaRetrato = new JLabel("", JLabel.CENTER);
		panelRetrato.add(etiquetaRetrato, BorderLayout.CENTER);

		return panelRetrato;
	}

	private JPanel crearPanelSeccion(String titulo) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.setBackground(colorPanel.obtener());
		p.setBorder(crearBorde(titulo));
		p.setAlignmentX(Component.LEFT_ALIGNMENT);
		return p;
	}

	private TitledBorder crearBorde(String titulo) {
		return BorderFactory.createTitledBorder(BorderFactory.createLineBorder(colorBorde.obtener()), titulo,
				TitledBorder.LEFT, TitledBorder.TOP, null, colorTexto.obtener());
	}

	private JButton crearBoton(String texto) {
		JButton b = new JButton(texto);

		/*
		 * En macOS/Aqua, Swing no respeta bien los colores personalizados de JButton.
		 * Si forzamos background/foreground, los botones pueden verse apagados o como
		 * deshabilitados. Por eso dejamos que macOS pinte el botón nativo.
		 */
		if (!CrashDetectorGUI.esMac()) {
			b.setBackground(colorBoton.obtener());
			b.setForeground(colorTexto.obtener());
			b.setOpaque(true);
		}

		return b;
	}

	private void prepararCheck(JCheckBox check) {
		check.setBackground(colorPanel.obtener());

		/*
		 * En macOS es mejor no forzar el foreground del checkbox porque Aqua puede
		 * mezclar mal el texto con el estado visual del control.
		 */
		if (!CrashDetectorGUI.esMac()) {
			check.setForeground(colorTexto.obtener());
		}
	}

	private void conectarEventos() {
		botonAbrirCarpetaJGit.addActionListener(e -> abrirCarpetaJGit());
		botonAbrirMaven.addActionListener(e -> abrirPaginaMaven());
		botonDescargarFaltantes.addActionListener(e -> accionDescargarDependenciasFaltantes());

		botonCrearRepo.addActionListener(e -> accionCrearRepo());
		botonRemoteManual.addActionListener(e -> accionRemoteManual());
		botonCrearRemoteAPI.addActionListener(e -> accionCrearRemoteAPI());
		botonCommitManual.addActionListener(e -> accionCommitManual());
		botonPushManual.addActionListener(e -> accionPushManual());
		botonAbrirGuiSwing.addActionListener(e -> abrirGuiSwing());

		checkAutoCommit.addActionListener(e -> {
			JGitAutoCommit.AUTO_COMMIT.escribir(checkAutoCommit.isSelected());
			actualizarEstadoBotones();
		});

		checkAutoPush.addActionListener(e -> {
			JGitAutoCommit.AUTO_PUSH.escribir(checkAutoPush.isSelected());
			actualizarEstadoBotones();
		});

		comboForge.addActionListener(e -> {
			ForgeRegistrada seleccionada = (ForgeRegistrada) comboForge.getSelectedItem();

			if (seleccionada != null) {
				forgePredeterminado.escribir(seleccionada.obtenerId());
			}
		});
	}

	private void abrirCarpetaJGit() {
		try {
			if (!BuscarParaJGit.CARPETA_JGIT.exists()) {
				BuscarParaJGit.CARPETA_JGIT.mkdirs();
			}
			Desktop.getDesktop().open(BuscarParaJGit.CARPETA_JGIT);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private void abrirPaginaMaven() {
		try {
			Desktop.getDesktop().browse(new URI("https://mvnrepository.com/artifact/org.eclipse.jgit"));
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	private void accionCrearRepo() {
		boolean ok = crearRepo();

		if (!ok) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.jgitNoSePudoCrearRepo());
		}

		actualizarEstadoBotones();
	}

	private void accionRemoteManual() {
		String actual = obtenerRemote();

		String url = JOptionPane.showInputDialog(this, MonitorDePID.idioma.jgitEscribaRemote(),
				actual == null ? "" : actual);

		if (url == null || url.trim().isEmpty()) {
			return;
		}

		boolean ok = establecerRemote(url.trim());

		if (ok) {
			remoteGuardado.escribir(url.trim());
		} else {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.jgitNoSePudoGuardarRemote());
		}

		actualizarEstadoBotones();
	}

	private void accionCrearRemoteAPI() {
		ForgeRegistrada seleccionada = (ForgeRegistrada) comboForge.getSelectedItem();

		if (seleccionada == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.jgitForgeNoSeleccionada());
			return;
		}

		GitForgeAPI api = RegistroGitForge.obtener(seleccionada.obtenerId());

		if (api == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.jgitForgeNoRegistrada(seleccionada.obtenerId()));
			return;
		}

		forgePredeterminado.escribir(seleccionada.obtenerId());

		String forgeUrl = JOptionPane.showInputDialog(this, MonitorDePID.idioma.jgitEscribaUrlForge(),
				api.urlPorDefecto());

		if (forgeUrl == null || forgeUrl.trim().isEmpty()) {
			return;
		}

		String repo = JOptionPane.showInputDialog(this, MonitorDePID.idioma.jgitEscribaNombreRepositorio(),
				carpetaActual().getName());

		if (repo == null || repo.trim().isEmpty()) {
			return;
		}

		String descripcion = JOptionPane.showInputDialog(this, MonitorDePID.idioma.jgitEscribaDescripcionRepositorio(),
				"Repositorio creado desde " + Statics.nombre_cd.obtener());

		if (descripcion == null) {
			descripcion = repo;
		}

		String namespace = JOptionPane.showInputDialog(this, MonitorDePID.idioma.jgitEscribaNamespaceOpcional(), "");

		if (namespace == null) {
			namespace = "";
		}

		String token = JOptionPane.showInputDialog(this, MonitorDePID.idioma.jgitEscribaTokenForge(), "");

		if (token == null || token.trim().isEmpty()) {
			return;
		}

		final ConfigCrearRepositorio cfg = new ConfigCrearRepositorio();
		cfg.forgeUrl = forgeUrl.trim();
		cfg.token = token.trim();
		cfg.nombreRepositorio = repo.trim();
		cfg.descripcion = descripcion.trim();
		cfg.namespace = namespace.trim();
		cfg.privado = false;
		cfg.crearReadme = true;
		cfg.defaultBranch = "main";

		botonCrearRemoteAPI.setEnabled(false);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					final ResultadoCrearRepositorio resultado = api.crearRepositorio(cfg);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							botonCrearRemoteAPI.setEnabled(true);

							if (!resultado.exito) {
								JOptionPane.showMessageDialog(JGitHubIzzy.this, resultado.mensaje);
								actualizarEstadoBotones();
								return;
							}

							if (resultado.remoteUrl != null && !resultado.remoteUrl.trim().isEmpty()) {
								establecerRemote(resultado.remoteUrl.trim());
								remoteGuardado.escribir(resultado.remoteUrl.trim());
							}

							JOptionPane.showMessageDialog(JGitHubIzzy.this, resultado.mensaje);
							actualizarEstadoBotones();
						}
					});
				} catch (final Throwable t) {
					CrashDetectorLogger.logException(t);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							botonCrearRemoteAPI.setEnabled(true);
							JOptionPane.showMessageDialog(JGitHubIzzy.this,
									MonitorDePID.idioma.jgitErrorCrearRemote() + ": " + t.getMessage());
							actualizarEstadoBotones();
						}
					});
				}
			}
		}).start();
	}

	private void accionCommitManual() {
		boolean ok = hacerCommitManual();

		if (!ok) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.jgitNoHayCambiosOError());
		}

		actualizarEstadoBotones();
	}

	private void accionPushManual() {
		boolean ok = hacerPushManual();

		if (!ok) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.jgitNoSePudoPush());
		}

		actualizarEstadoBotones();
	}

	private void seleccionarForgeEnCombo(String idForge) {
		if (comboForge == null || idForge == null) {
			return;
		}

		for (int i = 0; i < comboForge.getItemCount(); i++) {
			ForgeRegistrada item = comboForge.getItemAt(i);

			if (item != null && idForge.equals(item.obtenerId())) {
				comboForge.setSelectedIndex(i);
				return;
			}
		}

		if (comboForge.getItemCount() > 0) {
			comboForge.setSelectedIndex(0);
		}
	}

	@Override
	public void actualizarEstadoBotones() {
		boolean jgit = jgitDisponible();
		boolean repo = jgit && repoExiste();
		boolean remote = repo && JGitReflexivo.tieneRemote(carpetaActual());

		List<DependenciaJGit> faltantesClasspath = dependenciasFaltantes();
		List<DependenciaJGit> faltantesCarpeta = dependenciasFaltantesEnCarpetaInstalacion();

		boolean faltanDepsParaDescargar = !faltantesCarpeta.isEmpty();

		/*
		 * El botón de descarga debe depender de la carpeta ~/crash_detector/jgit/, no
		 * del classpath actual. En el IDE las dependencias pueden estar en classpath
		 * aunque todavía no estén instaladas para el launcher real.
		 */
		botonDescargarFaltantes.setEnabled(faltanDepsParaDescargar);

		botonCrearRepo.setEnabled(jgit && !repo);
		botonRemoteManual.setEnabled(repo);
		botonCrearRemoteAPI.setEnabled(repo);
		botonCommitManual.setEnabled(repo);
		botonPushManual.setEnabled(remote);
		botonAbrirGuiSwing.setEnabled(repo);
		checkAutoCommit.setEnabled(repo);
		checkAutoPush.setEnabled(repo && remote);

		StringBuilder html = new StringBuilder();
		html.append("<html><body style='color:").append(Config.colorAHexHtml(colorTexto.obtener()))
				.append("; background-color:").append(Config.colorAHexHtml(colorPanel.obtener())).append(";'>");

		/*
		 * Primero: información práctica del repositorio actual.
		 */
		html.append("<b>").append(MonitorDePID.idioma.jgitCarpetaActual()).append(":</b> ");
		html.append(carpetaActual().getAbsolutePath()).append("<br>");

		html.append("<b>").append(MonitorDePID.idioma.jgitRepositorio()).append(":</b> ");
		html.append(repo ? MonitorDePID.idioma.si() : MonitorDePID.idioma.no()).append("<br>");

		html.append("<b>").append(MonitorDePID.idioma.jgitRemote()).append(":</b> ");
		html.append(remote ? obtenerRemote() : MonitorDePID.idioma.no()).append("<br>");

		html.append("<br>");

		/*
		 * Segundo: estado general de JGit.
		 */
		html.append("<b>").append(MonitorDePID.idioma.jgitClasspath()).append(":</b> ");
		html.append(jgit ? MonitorDePID.idioma.si() : MonitorDePID.idioma.no()).append("<br>");

		html.append("<b>").append(MonitorDePID.idioma.jgitTodosLosArtefactos()).append(":</b> ");
		html.append(faltantesClasspath.isEmpty() ? MonitorDePID.idioma.si() : MonitorDePID.idioma.no()).append("<br>");

		html.append("<b>").append(MonitorDePID.idioma.jgitDependenciasEnCarpeta()).append(":</b> ");
		html.append(faltantesCarpeta.isEmpty() ? MonitorDePID.idioma.si() : MonitorDePID.idioma.no()).append("<br>");

		html.append("<br>");

		/*
		 * Último: listas largas de artefactos faltantes. Esto queda abajo para no
		 * empujar la carpeta actual y el remote fuera de vista.
		 */
		if (!faltantesClasspath.isEmpty()) {
			html.append("<b>").append(MonitorDePID.idioma.jgitArtefactosFaltantesClasspath()).append(":</b><br>");
			html.append("<ul>");

			for (DependenciaJGit dep : faltantesClasspath) {
				html.append("<li>").append(dep.nombreVisible()).append("</li>");
			}

			html.append("</ul>");
		}

		if (!faltantesCarpeta.isEmpty()) {
			html.append("<b>").append(MonitorDePID.idioma.jgitArtefactosFaltantesCarpeta()).append(":</b><br>");
			html.append("<ul>");

			for (DependenciaJGit dep : faltantesCarpeta) {
				html.append("<li>").append(dep.nombreVisible()).append("</li>");
			}

			html.append("</ul>");
		}

		html.append("</body></html>");

		estado.setText(html.toString());
		estado.setCaretPosition(0);
	}

	private void cargarRetrato() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					File archivo = Statics.carpeta.resolve("imagenes/illythedizzy.png").toFile();

					if (archivo.exists()) {
						final Image img = ImageIO.read(archivo);

						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								etiquetaRetrato.setIcon(new javax.swing.ImageIcon(img));
							}
						});
					} else {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								etiquetaRetrato.setText(MonitorDePID.idioma.jgitNoHayRetratoIzzy());
								etiquetaRetrato.setForeground(Color.GRAY);
							}
						});
					}
				} catch (IOException ex) {
					CrashDetectorLogger.logException(ex);
				}
			}
		}).start();
	}

	public void accionDescargarDependenciasFaltantes() {
		final List<DependenciaJGit> faltantes = dependenciasFaltantesEnCarpetaInstalacion();

		if (faltantes.isEmpty()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.jgitNoFaltanDependencias());
			return;
		}

		int confirmar = JOptionPane.showConfirmDialog(this,
				MonitorDePID.idioma.jgitConfirmarDescargaDependencias(faltantes.size()),
				MonitorDePID.idioma.jgitDescargarDependenciasFaltantes(), JOptionPane.YES_NO_OPTION);

		if (confirmar != JOptionPane.YES_OPTION) {
			return;
		}

		botonDescargarFaltantes.setEnabled(false);

		new Thread(new Runnable() {
			@Override
			public void run() {
				int descargadas = 0;
				StringBuilder errores = new StringBuilder();

				try {
					descargadas = descargarTodasLasDependenciasFaltantes();
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					errores.append(t.getMessage());
				}

				final int totalDescargadas = descargadas;
				final String textoErrores = errores.toString();

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						actualizarEstadoBotones();

						String mensaje = MonitorDePID.idioma.jgitDependenciasDescargadas(totalDescargadas);

						if (!textoErrores.trim().isEmpty()) {
							mensaje += "\n\n" + MonitorDePID.idioma.jgitErroresDescarga() + ":\n" + textoErrores;
						}

						mensaje += "\n\n" + MonitorDePID.idioma.jgitReiniciarParaCargarClasspath();

						JOptionPane.showMessageDialog(JGitHubIzzy.this, mensaje);
					}
				});
			}
		}).start();
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		if (panelPrincipal != null) {
			panelPrincipal.setBackground(colorFondo.obtener());
		}
		if (panelRetrato != null) {
			panelRetrato.repaint();
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<ElementoConfig>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		colorRetrato.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoRetrato());

		JGitAutoCommit.AUTO_COMMIT.establecerNombreParaMostrar(() -> MonitorDePID.idioma.jgitAutoCommitDespuesBackup());
		JGitAutoCommit.AUTO_PUSH.establecerNombreParaMostrar(() -> MonitorDePID.idioma.jgitAutoPushDespuesCommit());

		ret.add(colorFondo);
		ret.add(colorPanel);
		ret.add(colorTexto);
		ret.add(colorBorde);
		ret.add(colorBoton);
		ret.add(colorRetrato);
		ret.add(JGitAutoCommit.AUTO_COMMIT);
		ret.add(JGitAutoCommit.AUTO_PUSH);
		ret.add(remoteGuardado);
		ret.add(forgePredeterminado);

		return ret;
	}
}