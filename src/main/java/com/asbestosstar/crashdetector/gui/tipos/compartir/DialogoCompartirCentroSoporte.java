package com.asbestosstar.crashdetector.gui.tipos.compartir;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ConfigDouble;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.config.ConfigPanelEstiloTL;
import com.asbestosstar.crashdetector.gui.tipos.historia.ClioOfficeGUI;
import com.asbestosstar.crashdetector.gui.tipos.lfpdppp.LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI;
import com.asbestosstar.crashdetector.gui.tipos.lfpdppp.LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos;
import com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI;
import com.asbestosstar.crashdetector.mapas.BiMap;

public class DialogoCompartirCentroSoporte extends DialogoCompartir {

	public static String ID = "dialogo_compartir_centro_soporte";

	public ConfigDouble anchoVentana = ConfigDouble.de("dialogo.compartir.centro_soporte.ancho", 860.0);
	public ConfigDouble altoVentana = ConfigDouble.de("dialogo.compartir.centro_soporte.alto", 560.0);

	public ConfigDouble altoFilaLog = ConfigDouble.de("dialogo.compartir.centro_soporte.alto_fila_log", 36.0);
	public ConfigDouble anchoNombreArchivo = ConfigDouble.de("dialogo.compartir.centro_soporte.ancho_nombre_archivo",
			240.0);
	public ConfigDouble anchoBotonAbrir = ConfigDouble.de("dialogo.compartir.centro_soporte.ancho_boton_abrir", 95.0);
	public ConfigDouble anchoBotonExplorador = ConfigDouble
			.de("dialogo.compartir.centro_soporte.ancho_boton_explorador", 145.0);
	public ConfigDouble anchoBotonSubir = ConfigDouble.de("dialogo.compartir.centro_soporte.ancho_boton_subir", 185.0);
	public ConfigDouble tamanoFuenteBotonPrincipal = ConfigDouble
			.de("dialogo.compartir.centro_soporte.tamano_fuente_boton_principal", 16.0);
	public ConfigColor colorFondo = ConfigColor.de("dialogo.compartir.centro_soporte.fondo", new Color(238, 238, 238));
	public ConfigColor colorTexto = ConfigColor.de("dialogo.compartir.centro_soporte.texto", new Color(0, 0, 0));
	public ConfigColor colorBoton = ConfigColor.de("dialogo.compartir.centro_soporte.boton", new Color(210, 228, 242));
	public ConfigColor colorBordeAviso = ConfigColor.de("dialogo.compartir.centro_soporte.borde_aviso",
			new Color(255, 0, 0));
	public ConfigColor colorTextoAviso = ConfigColor.de("dialogo.compartir.centro_soporte.texto_aviso",
			new Color(255, 0, 0));
	public ConfigColor colorBotonPrincipal = ConfigColor.de("dialogo.compartir.centro_soporte.boton_principal",
			new Color(205, 235, 205));
	public ConfigColor colorTextoBotonPrincipal = ConfigColor
			.de("dialogo.compartir.centro_soporte.texto_boton_principal", new Color(0, 170, 0));

	public JPanel panelDiffMods;
	public JLabel etiquetaDiffMods;
	public JButton botonDiffMods;

	public ConfigColor colorModsAgregados = ConfigColor.de("dialogo.compartir.centro_soporte.mods_agregados",
			new Color(0, 170, 0));
	public ConfigColor colorModsQuitados = ConfigColor.de("dialogo.compartir.centro_soporte.mods_quitados",
			new Color(210, 0, 0));

	public ConfigString textoSuperior = ConfigString.de(
			"dialogo.compartir.centro_soporte.texto_superior." + MonitorDePID.idioma.codigo(),
			MonitorDePID.idioma.centroSoporteTextoSuperior());

	public ConfigString textoAviso = ConfigString.de(
			"dialogo.compartir.centro_soporte.texto_aviso." + MonitorDePID.idioma.codigo(),
			MonitorDePID.idioma.centroSoporteTextoAviso());

	public ConfigString textoBajoTitulo = ConfigString.de(
			"dialogo.compartir.centro_soporte.texto_bajo_titulo." + MonitorDePID.idioma.codigo(),
			MonitorDePID.idioma.centroSoporteTextoBajoTitulo());

	public ConfigString urlSoporte = ConfigString.de("dialogo.compartir.centro_soporte.url_soporte",
			"https://example.com/");

	public ConfigString mensajeHeader = ConfigString.de(
			"dialogo.compartir.centro_soporte.mensaje.header." + MonitorDePID.idioma.codigo(), "Logs `$UPLOAD_TO$`:");

	public ConfigString mensajeEstructura = ConfigString.de(
			"dialogo.compartir.centro_soporte.mensaje.estructura." + MonitorDePID.idioma.codigo(),
			"$HEADER$\n$LOGS$\n\n$MODLIST_DIFF$");

	public ConfigString mensajeLineaLog = ConfigString.de(
			"dialogo.compartir.centro_soporte.mensaje.linea_log." + MonitorDePID.idioma.codigo(),
			"[$LOG_NAME$](<$LINK$>)");

	public ConfigString mensajeSeparadorLogs = ConfigString
			.de("dialogo.compartir.centro_soporte.mensaje.separador_logs", "   |   ");

	public ConfigString mensajeLogsBloque = ConfigString
			.de("dialogo.compartir.centro_soporte.mensaje.logs_bloque." + MonitorDePID.idioma.codigo(), "### $LOGS$");

	public ConfigString mensajeModlistDiff = ConfigString.de(
			"dialogo.compartir.centro_soporte.mensaje.modlist_diff." + MonitorDePID.idioma.codigo(),
			"### $MODLIST_LINK$ since last:\n```ansi\n\u001B[2;32m$ADDED$\u001B[0m added, \u001B[2;31m$REMOVED$\u001B[0m removed, \u001B[2;34m$UPDATED$\u001B[0m updated.\n```");

	public ConfigBoolean ocultarTextoAviso = ConfigBoolean.de("dialogo.compartir.centro_soporte.ocultar_texto_aviso",
			false);

	public ConfigBoolean mostrarLogoCuadrado = ConfigBoolean
			.de("dialogo.compartir.centro_soporte.logo_cuadrado.mostrar", false);

	public ConfigString rutaLogoCuadrado = ConfigString.de("dialogo.compartir.centro_soporte.logo_cuadrado.ruta", "");

	public ConfigDouble tamanoLogoCuadrado = ConfigDouble.de("dialogo.compartir.centro_soporte.logo_cuadrado.tamano",
			64.0);

	public JLabel logoCuadrado;

	public JPanel raiz;
	public JPanel panelContenido;
	public JPanel panelLogs;
	public JPanel panelBotonesGrandes;

	public JLabel titulo;
	public JTextArea textoSuperiorArea;
	public JLabel aviso;
	public JButton botonUploadTodos;
	public JButton botonAyuda;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void preperar(Instant instant) {
		this.instant = instant;

		setTitle(Statics.nombre_cd.obtener());
		setSize(new Dimension(anchoVentana.obtener().intValue(), altoVentana.obtener().intValue()));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			}
		});

		crearMenuSuperior();

		raiz = new JPanel(new BorderLayout(0, 0));
		panelContenido = new JPanel(new BorderLayout(0, 0));

		titulo = new JLabel(MonitorDePID.idioma.centroSoporteTituloCrash());
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));

		textoSuperiorArea = new JTextArea(textoSuperior.obtener());
		textoSuperiorArea.setEditable(false);
		textoSuperiorArea.setFocusable(false);
		textoSuperiorArea.setLineWrap(true);
		textoSuperiorArea.setWrapStyleWord(true);
		textoSuperiorArea.setOpaque(false);

		aviso = new JLabel(textoAviso.obtener());
		aviso.setOpaque(true);
		aviso.setBorder(BorderFactory.createLineBorder(colorBordeAviso.obtener(), 1));

		JPanel panelArriba = new JPanel(new BorderLayout(0, 0));
		JPanel panelTitulo = new JPanel(new BorderLayout(8, 0));
		panelTitulo.setBackground(colorFondo.obtener());

		logoCuadrado = new JLabel();
		cargarLogoCuadrado();
		panelTitulo.add(logoCuadrado, BorderLayout.WEST);
		panelTitulo.add(titulo, BorderLayout.CENTER);

		panelArriba.add(panelTitulo, BorderLayout.NORTH);
		panelArriba.add(textoSuperiorArea, BorderLayout.CENTER);

		if (!ocultarTextoAviso.obtener()) {
			panelArriba.add(aviso, BorderLayout.SOUTH);
		}

		inicializarListaLogsCentroSoporte();

		botonUploadTodos = new JButton(MonitorDePID.idioma.centroSoporteSubirTodoYCopiar());
		botonUploadTodos.addActionListener(e -> {
			setEnviando(true);
			try {
				subirTodoCentroSoporte();
			} catch (Throwable t) {
				mostrarError(MonitorDePID.idioma.error_inesperado_al_generar_enlaces(), t);
			} finally {
				setEnviando(false);
			}
		});

		botonAyuda = new JButton(MonitorDePID.idioma.centroSoportePedirAyuda());
		botonAyuda.addActionListener(e -> abrirURLSoporte());

		botonUploadTodos.setFont(new Font("Segoe UI", Font.BOLD, tamanoFuenteBotonPrincipal.obtener().intValue()));
		botonAyuda.setFont(new Font("Segoe UI", Font.BOLD, 14));

		panelBotonesGrandes = new JPanel(new BorderLayout(0, 0));
		panelBotonesGrandes.add(botonUploadTodos, BorderLayout.NORTH);
		panelBotonesGrandes.add(botonAyuda, BorderLayout.SOUTH);

		panelContenido.add(panelArriba, BorderLayout.NORTH);

		JPanel panelCentro = new JPanel(new BorderLayout(0, 0));
		panelCentro.setBackground(colorFondo.obtener());
		panelCentro.add(panelLogs, BorderLayout.CENTER);
		panelCentro.add(crearPanelDiffMods(), BorderLayout.SOUTH);

		panelContenido.add(panelCentro, BorderLayout.CENTER);
		panelContenido.add(panelBotonesGrandes, BorderLayout.SOUTH);

		raiz.add(panelContenido, BorderLayout.CENTER);
		add(raiz, BorderLayout.CENTER);

		cargarConsolasCentroSoporte();
		recargarApariencia();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void subirTodoCentroSoporte() {
		try {
			List<String> lineasLogs = new ArrayList<String>();
			String modlistLink = "";

			for (Consola consola : MonitorDePID.consolas) {
				if (consola == null || consola.archivo == null) {
					continue;
				}

				List<String> urls = consola.obtainerEnlaces();
				if (urls == null || urls.isEmpty()) {
					continue;
				}

				String nombreArchivo = consola.archivo.getFileName().toString();

				for (String url : urls) {
					if (url == null || url.trim().isEmpty()) {
						continue;
					}

					String nombreLog = nombreArchivo;

					String linea = mensajeLineaLog.obtener().replace("$FILE_NAME$", nombreArchivo)
							.replace("$LOG_NAME$", nombreLog).replace("$LINK$", url.trim());

					if ("modlist.txt".equalsIgnoreCase(nombreArchivo)) {
						modlistLink = linea;
					}

					lineasLogs.add(linea);
				}
			}

			int[] diff = contarDiffMods();

			String uploadTo = "logs";
			try {
				uploadTo = com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro.obtenerAPIdeConfig()
						.nombre();
			} catch (Throwable ignored) {
			}

			String logs = mensajeLogsBloque.obtener().replace("$LOGS$",
					String.join(mensajeSeparadorLogs.obtener(), lineasLogs));

			String modlistDiff = mensajeModlistDiff.obtener()
					.replace("$MODLIST_LINK$",
							modlistLink.isEmpty() ? MonitorDePID.idioma.diferentesDeLasMods() : modlistLink)
					.replace("$ADDED$", Integer.toString(diff[0])).replace("$REMOVED$", Integer.toString(diff[1]))
					.replace("$UPDATED$", "0");

			String header = mensajeHeader.obtener().replace("$UPLOAD_TO$", uploadTo)
					.replace("$TITLE$", MonitorDePID.idioma.centroSoporteTituloCrash())
					.replace("$TEXT_UNDER_CRASHED$", textoBajoTitulo.obtener());

			String mensaje = mensajeEstructura.obtener().replace("$HEADER$", header).replace("$LOGS$", logs)
					.replace("$MODLIST_DIFF$", modlistDiff);

			copiarAlPortapapeles(mensaje);
			mostrarInfo(MonitorDePID.idioma.copiadoAlPortapapeles());
		} catch (Throwable t) {
			mostrarError(MonitorDePID.idioma.error_inesperado_al_generar_enlaces(), t);
		}
	}

	public void cargarLogoCuadrado() {
		if (logoCuadrado == null) {
			return;
		}

		logoCuadrado.setIcon(null);
		logoCuadrado.setText("");

		if (!mostrarLogoCuadrado.obtener()) {
			logoCuadrado.setVisible(false);
			return;
		}

		try {
			String ruta = rutaLogoCuadrado.obtener();
			if (ruta == null || ruta.trim().isEmpty()) {
				logoCuadrado.setVisible(false);
				return;
			}

			File archivo = new File(ruta.trim());
			if (!archivo.isAbsolute()) {
				archivo = Statics.carpeta.resolve(ruta.trim()).toFile();
			}

			if (!archivo.exists()) {
				logoCuadrado.setVisible(false);
				return;
			}

			int tamano = Math.max(16, tamanoLogoCuadrado.obtener().intValue());
			ImageIcon icono = new ImageIcon(archivo.getAbsolutePath());
			Image img = icono.getImage().getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);

			logoCuadrado.setIcon(new ImageIcon(img));
			logoCuadrado.setPreferredSize(new Dimension(tamano, tamano));
			logoCuadrado.setVisible(true);
		} catch (Throwable t) {
			logoCuadrado.setVisible(false);
			CrashDetectorLogger.logException(t);
		}
	}

	public JPanel crearPanelDiffMods() {
		panelDiffMods = new JPanel(new BorderLayout(6, 0));
		panelDiffMods.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.diferentesDeLasMods()));
		panelDiffMods.setBackground(colorFondo.obtener());

		etiquetaDiffMods = new JLabel(generarTextoDiffModsHtml());
		etiquetaDiffMods.setForeground(colorTexto.obtener());

		botonDiffMods = new JButton(MonitorDePID.idioma.diferentesDeLasMods());
		botonDiffMods.addActionListener(e -> abrirHistorialMods());
		estilizarBotonNormal(botonDiffMods);

		panelDiffMods.add(etiquetaDiffMods, BorderLayout.CENTER);
		panelDiffMods.add(botonDiffMods, BorderLayout.EAST);

		return panelDiffMods;
	}

	public String generarTextoDiffModsHtml() {
		int[] diff = contarDiffMods();
		String colorMas = colorAHex(colorModsAgregados.obtener());
		String colorMenos = colorAHex(colorModsQuitados.obtener());

		return "<html><span style='color:" + colorMas + ";font-weight:bold;'>+" + diff[0] + "</span>" + " &nbsp; "
				+ "<span style='color:" + colorMenos + ";font-weight:bold;'>-" + diff[1] + "</span></html>";
	}

	public int[] contarDiffMods() {
		try {
			Path actual = MonitorDePID.ultimo_mods;
			Path anterior = obtenerUltimoArchivoHistorialMods();

			if (actual == null || anterior == null || !Files.exists(actual) || !Files.exists(anterior)) {
				return new int[] { 0, 0 };
			}

			Set<String> modsActuales = leerMods(actual);
			Set<String> modsAnteriores = leerMods(anterior);

			int agregados = 0;
			int quitados = 0;

			for (String mod : modsActuales) {
				if (!modsAnteriores.contains(mod)) {
					agregados++;
				}
			}

			for (String mod : modsAnteriores) {
				if (!modsActuales.contains(mod)) {
					quitados++;
				}
			}

			return new int[] { agregados, quitados };
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return new int[] { 0, 0 };
		}
	}

	public Set<String> leerMods(Path archivo) throws Exception {
		Set<String> mods = new HashSet<String>();

		for (String linea : Files.readAllLines(archivo)) {
			if (linea == null) {
				continue;
			}

			String limpia = linea.trim();
			if (!limpia.isEmpty()) {
				mods.add(limpia);
			}
		}

		return mods;
	}

	public Path obtenerUltimoArchivoHistorialMods() {
		try {
			Path dir = Statics.carpeta.resolve("historia_mods");
			if (!Files.exists(dir)) {
				return null;
			}

			java.io.File[] archivos = dir.toFile()
					.listFiles((d, name) -> name.matches("\\d{6}\\.(exito|falla|instantanea)"));
			if (archivos == null || archivos.length == 0) {
				return null;
			}

			java.util.Arrays.sort(archivos, (a, b) -> b.getName().compareTo(a.getName()));
			return archivos[0].toPath();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			return null;
		}
	}

	public void abrirHistorialMods() {
		try {
			TipoGUI.HISTORIA_DE_MODS.obtenerGUIPredeterminado(ClioOfficeGUI.ID, () -> new ClioOfficeGUI()).init();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public String colorAHex(Color c) {
		if (c == null) {
			return "#000000";
		}
		return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
	}

	public void crearMenuSuperior() {
		JMenuBar barra = new JMenuBar();

		JMenu menuArchivo = new JMenu(MonitorDePID.idioma.archivo());
		JMenuItem itemAbrirConfig = new JMenuItem(MonitorDePID.idioma.ajustesCrashDetector());
		JMenuItem itemCompartirInstancia = new JMenuItem(
				MonitorDePID.idioma.texto_de_boton_compartir_instancia_modpack());
		JMenuItem itemSalir = new JMenuItem(MonitorDePID.idioma.salir());

		itemAbrirConfig.addActionListener(e -> abrirConfigComoDialogo());
		itemCompartirInstancia.addActionListener(e -> compartirInstanciaOModpack(e));
		itemSalir.addActionListener(e -> System.exit(0));

		menuArchivo.add(itemAbrirConfig);
		menuArchivo.add(itemCompartirInstancia);
		menuArchivo.addSeparator();
		menuArchivo.add(itemSalir);

		JMenu menuAnalisis = new JMenu(MonitorDePID.idioma.analisisAvanzado());

		for (Entry<BiMap.DoubleKey<TipoGUI<? extends BotonDeBarraLateralDerecha>, String>, Supplier<BotonDeBarraLateralDerecha>> en : PrincipalGUI.botons_de_barra_lateral_derecha
				.entrySet()) {
			try {

				TipoGUI tipo = en.getKey().key0;
				String idPredeterminado = en.getKey().key1;
				Supplier<BotonDeBarraLateralDerecha> proveedor = en.getValue();

				Object supObj = en.getValue();
				if (!(supObj instanceof java.util.function.Supplier)) {
					continue;
				}

				@SuppressWarnings("unchecked")
				java.util.function.Supplier<BotonDeBarraLateralDerecha> sup = (java.util.function.Supplier<BotonDeBarraLateralDerecha>) supObj;

				BotonDeBarraLateralDerecha gui = sup.get();
				if (gui == null) {
					continue;
				}

				JMenuItem item = new JMenuItem(tipo.etiquetaDelBoton());
				item.addActionListener(e -> gui.init());
				menuAnalisis.add(item);
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}

		JMenu menuPrivacidad = new JMenu(MonitorDePID.idioma.confidencialidad());
		JMenuItem itemPrivacidad = new JMenuItem(MonitorDePID.idioma.consentimientoLFPDPPP());
		itemPrivacidad.addActionListener(e -> abrirPrivacidad());
		menuPrivacidad.add(itemPrivacidad);

		barra.add(menuArchivo);
		barra.add(menuAnalisis);
		barra.add(menuPrivacidad);

		setJMenuBar(barra);
	}

	public void abrirPrivacidad() {
		try {
			LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI gui = TipoGUI.LFPDPPP
					.obtenerGUIPredeterminado(
							LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos.ID,
							() -> new LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos());
			gui.init();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public JPanel panelListaLogs;
	public JScrollPane scrollLogs;

	public void inicializarListaLogsCentroSoporte() {
		panelListaLogs = new JPanel();
		panelListaLogs.setLayout(new BoxLayout(panelListaLogs, BoxLayout.Y_AXIS));
		panelListaLogs.setBackground(colorFondo.obtener());

		scrollLogs = new JScrollPane(panelListaLogs);
		scrollLogs.setBorder(BorderFactory.createLineBorder(colorBordeAviso.obtener().darker(), 1));

		panelLogs = new JPanel(new BorderLayout(0, 0));
		panelLogs.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.centroSoporteArchivosDisponibles()));
		panelLogs.setBackground(colorFondo.obtener());
		panelLogs.add(scrollLogs, BorderLayout.CENTER);
	}

	public JPanel crearFilaLog(Consola consola) {
		JPanel fila = new JPanel(new BorderLayout(6, 0));
		fila.setBackground(colorFondo.obtener());
		fila.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
		fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, altoFilaLog.obtener().intValue()));
		fila.setPreferredSize(new Dimension(10, altoFilaLog.obtener().intValue()));

		JLabel nombre = new JLabel(consola.archivo.getFileName().toString());
		nombre.setForeground(colorTexto.obtener());
		nombre.setPreferredSize(
				new Dimension(anchoNombreArchivo.obtener().intValue(), altoFilaLog.obtener().intValue()));

		JButton abrir = new JButton(MonitorDePID.idioma.abrir());
		JButton explorador = new JButton(MonitorDePID.idioma.mostrarEnExplorador());
		JButton subir = new JButton(MonitorDePID.idioma.subirYCopiarEnlace());

		abrir.setPreferredSize(new Dimension(anchoBotonAbrir.obtener().intValue(), 28));
		explorador.setPreferredSize(new Dimension(anchoBotonExplorador.obtener().intValue(), 28));
		subir.setPreferredSize(new Dimension(anchoBotonSubir.obtener().intValue(), 28));

		estilizarBotonNormal(abrir);
		estilizarBotonNormal(explorador);
		estilizarBotonNormal(subir);

		abrir.addActionListener(e -> abrirArchivo(consola));
		explorador.addActionListener(e -> mostrarEnExplorador(consola));
		subir.addActionListener(e -> subirSoloUno(consola));

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
		botones.setOpaque(false);
		botones.add(abrir);
		botones.add(explorador);
		botones.add(subir);

		fila.add(nombre, BorderLayout.WEST);
		fila.add(botones, BorderLayout.CENTER);

		return fila;
	}

	public void estilizarBotonNormal(JButton boton) {
		boton.setBackground(colorBoton.obtener());
		boton.setForeground(colorTexto.obtener());
		boton.setFocusPainted(false);
	}

	public void abrirArchivo(Consola consola) {
		try {
			Desktop.getDesktop().open(consola.archivo.toFile());
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public void mostrarEnExplorador(Consola consola) {
		try {
			Desktop.getDesktop().open(consola.archivo.getParent().toFile());
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public void subirSoloUno(Consola consola) {
		try {
			List<String> urls = consola.obtainerEnlaces();
			if (urls != null && !urls.isEmpty()) {
				copiarAlPortapapeles(String.join(" ", urls));
				mostrarInfo(MonitorDePID.idioma.copiadoAlPortapapeles());
			}
		} catch (Throwable t) {
			mostrarError(MonitorDePID.idioma.error_inesperado_al_generar_enlaces(), t);
		}
	}

	public void inicializarTablaCentroSoporte() {
		modeloTabla = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column >= 1;
			}
		};

		modeloTabla.addColumn(MonitorDePID.idioma.archivo());
		modeloTabla.addColumn(MonitorDePID.idioma.abrir());
		modeloTabla.addColumn(MonitorDePID.idioma.mostrarEnExplorador());
		modeloTabla.addColumn(MonitorDePID.idioma.texto_de_buton_compartir_enlace());

		tabla = new JTable(modeloTabla);
		tabla.setRowHeight(36);
	}

	public void cargarConsolasCentroSoporte() {
		if (panelListaLogs == null) {
			return;
		}

		panelListaLogs.removeAll();

		for (Consola consola : MonitorDePID.consolas) {
			if (consola == null || consola.archivo == null) {
				continue;
			}

			panelListaLogs.add(crearFilaLog(consola));
		}

		panelListaLogs.revalidate();
		panelListaLogs.repaint();
	}

	public void abrirURLSoporte() {
		try {
			String url = urlSoporte.obtener();
			if (url == null || url.trim().isEmpty()) {
				return;
			}
			Desktop.getDesktop().browse(new java.net.URI(url.trim()));
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public void abrirConfigComoDialogo() {
		try {
			javax.swing.JDialog dialogo = new javax.swing.JDialog(this, MonitorDePID.idioma.ajustesCrashDetector(),
					true);
			ConfigPanelEstiloTL<Object> panel = new ConfigPanelEstiloTL<Object>();
			panel.constructir(null);
			dialogo.setContentPane(panel);
			dialogo.pack();
			dialogo.setLocationRelativeTo(this);
			dialogo.setVisible(true);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	@Override
	public void recargarApariencia() {
		if (raiz != null) {
			raiz.setBackground(colorFondo.obtener());
		}
		if (panelContenido != null) {
			panelContenido.setBackground(colorFondo.obtener());
		}
		if (panelLogs != null) {
			panelLogs.setBackground(colorFondo.obtener());
		}
		if (panelBotonesGrandes != null) {
			panelBotonesGrandes.setBackground(colorFondo.obtener());
		}
		if (titulo != null) {
			titulo.setForeground(colorTexto.obtener());
		}
		if (textoSuperiorArea != null) {
			textoSuperiorArea.setForeground(colorTexto.obtener());
			textoSuperiorArea.setBackground(colorFondo.obtener());
		}
		if (aviso != null) {
			aviso.setForeground(colorTextoAviso.obtener());
			aviso.setBackground(colorFondo.obtener());
			aviso.setBorder(BorderFactory.createLineBorder(colorBordeAviso.obtener(), 1));
		}
		if (botonUploadTodos != null) {
			botonUploadTodos.setBackground(colorBotonPrincipal.obtener());
			botonUploadTodos.setForeground(colorTextoBotonPrincipal.obtener());
		}
		if (botonAyuda != null) {
			botonAyuda.setBackground(colorBoton.obtener());
			botonAyuda.setForeground(colorTexto.obtener());
		}

		if (panelDiffMods != null) {
			panelDiffMods.setBackground(colorFondo.obtener());
		}
		if (botonDiffMods != null) {
			botonDiffMods.setBackground(colorBoton.obtener());
			botonDiffMods.setForeground(colorTexto.obtener());
		}
		if (etiquetaDiffMods != null) {
			etiquetaDiffMods.setText(generarTextoDiffModsHtml());
		}

		if (logoCuadrado != null) {
			cargarLogoCuadrado();
		}
		if (aviso != null) {
			aviso.setVisible(!ocultarTextoAviso.obtener());
		}

	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> ret = new ArrayList<ElementoConfig>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		ret.add(colorFondo);

		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		ret.add(colorTexto);

		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		ret.add(colorBoton);

		colorBordeAviso.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		ret.add(colorBordeAviso);

		colorTextoAviso.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoAviso());
		ret.add(colorTextoAviso);

		colorBotonPrincipal.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonPrincipal());
		ret.add(colorBotonPrincipal);

		colorTextoBotonPrincipal.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoBotonPrincipal());
		ret.add(colorTextoBotonPrincipal);

		anchoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.anchoVentana());
		ret.add(anchoVentana);

		altoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.altoVentana());
		ret.add(altoVentana);

		textoSuperior.establecerNombreParaMostrar(() -> MonitorDePID.idioma.textoSuperiorPersonalizado());
		ret.add(textoSuperior);

		textoAviso.establecerNombreParaMostrar(() -> MonitorDePID.idioma.textoAvisoPersonalizado());
		ret.add(textoAviso);

		textoBajoTitulo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.textoBajoTituloPersonalizado());
		ret.add(textoBajoTitulo);

		urlSoporte.establecerNombreParaMostrar(() -> MonitorDePID.idioma.urlSoporte());
		ret.add(urlSoporte);

		colorModsAgregados.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorResultadoAnadido());
		ret.add(colorModsAgregados);

		colorModsQuitados.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorResultadoEliminado());
		ret.add(colorModsQuitados);

		anchoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.anchoVentana());
		ret.add(anchoVentana);

		altoVentana.establecerNombreParaMostrar(() -> MonitorDePID.idioma.altoVentana());
		ret.add(altoVentana);

		altoFilaLog.establecerNombreParaMostrar(() -> MonitorDePID.idioma.altoFilaLog());
		ret.add(altoFilaLog);

		anchoNombreArchivo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.anchoNombreArchivo());
		ret.add(anchoNombreArchivo);

		anchoBotonAbrir.establecerNombreParaMostrar(() -> MonitorDePID.idioma.anchoBotonAbrir());
		ret.add(anchoBotonAbrir);

		anchoBotonExplorador.establecerNombreParaMostrar(() -> MonitorDePID.idioma.anchoBotonExplorador());
		ret.add(anchoBotonExplorador);

		anchoBotonSubir.establecerNombreParaMostrar(() -> MonitorDePID.idioma.anchoBotonSubir());
		ret.add(anchoBotonSubir);

		tamanoFuenteBotonPrincipal.establecerNombreParaMostrar(() -> MonitorDePID.idioma.tamanoFuenteBotonPrincipal());
		ret.add(tamanoFuenteBotonPrincipal);

		mensajeLogsBloque.establecerNombreParaMostrar(() -> MonitorDePID.idioma.formatoBloqueLogs());
		ret.add(mensajeLogsBloque);

		mensajeHeader.establecerNombreParaMostrar(() -> MonitorDePID.idioma.formatoHeaderMensaje());
		ret.add(mensajeHeader);

		mensajeEstructura.establecerNombreParaMostrar(() -> MonitorDePID.idioma.formatoEstructuraMensaje());
		ret.add(mensajeEstructura);

		mensajeLineaLog.establecerNombreParaMostrar(() -> MonitorDePID.idioma.formatoLineaLog());
		ret.add(mensajeLineaLog);

		mensajeSeparadorLogs.establecerNombreParaMostrar(() -> MonitorDePID.idioma.formatoSeparadorLogs());
		ret.add(mensajeSeparadorLogs);

		mensajeModlistDiff.establecerNombreParaMostrar(() -> MonitorDePID.idioma.formatoModlistDiff());
		ret.add(mensajeModlistDiff);

		ocultarTextoAviso.establecerNombreParaMostrar(() -> MonitorDePID.idioma.ocultarTextoAviso());
		ret.add(ocultarTextoAviso);

		mostrarLogoCuadrado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.mostrarLogoCuadrado());
		ret.add(mostrarLogoCuadrado);

		rutaLogoCuadrado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.rutaLogoCuadrado());
		ret.add(rutaLogoCuadrado);

		tamanoLogoCuadrado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.tamanoLogoCuadrado());
		ret.add(tamanoLogoCuadrado);

		return ret;
	}
}