package com.asbestosstar.crashdetector.gui.tipos.historia;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Interfaz “Oficina de Clio”.
 *
 * - Coloca los controles exactamente encima de las zonas del arte. - Mantiene
 * proporciones con rectángulos normalizados (0..1). - Sin bordes en el
 * pergamino de resultados. - TODOS los colores provienen de ConfigColor (nada
 * hardcodeado).
 */
public class ClioOfficeGUI extends HistoriaDeModsGUI {

	public static String ID = "cliosoffice";
	private static final long serialVersionUID = 1L;

	// ---------- Imagen de fondo ----------
	private BufferedImage imagenFondo;
	private int anchoDiseno = 1200;
	private int altoDiseno = 800;

	// ---------- Colores configurables ----------
	public ConfigColor colorEstadoExito;
	public ConfigColor colorEstadoFallo;
	public ConfigColor colorEstadoInstantanea;
	public ConfigColor colorResultadoAnadido;
	public ConfigColor colorResultadoEliminado;
	public ConfigColor colorBordeScroll;
	public ConfigColor colorFondoPanel;

	// Tema visual (todos vía ConfigColor)
	public ConfigColor colorBeigeListas; // fondo de listas (beige)
	public ConfigColor colorTextoListas; // texto de radios
	public ConfigColor colorBordeListas; // borde de los JScrollPane de listas
	public ConfigColor colorBotonFondo; // fondo de botón
	public ConfigColor colorBotonTexto; // texto de botón
	public ConfigColor colorBordeBoton; // borde de botón
	public ConfigColor colorDoradoTexto; // texto dorado de la descripción

	// ---------- Paneles principales ----------
	private JPanel panelLista1;
	private JPanel panelLista2;
	private JPanel panelResultados;
	private JPanel panelDescripcion;
	private JPanel panelBotones;

	private JPanel panelLista2Contenido;

	// ---------- Controles ----------
	private JButton botonComparar;
	private JButton botonInstantanea;

	private JScrollPane scrollLista1;
	private JScrollPane scrollLista2;
	private JScrollPane scrollResultados;

	// ================== RECTÁNGULOS NORMALIZADOS (x, y, w, h) ==================
	private static final double[] R_LISTA1 = { 0.030, 0.030, 0.142, 0.690 };
	private static final double[] R_LISTA2 = { 0.274, 0.035, 0.142, 0.690 };
	private static final double[] R_RESULT = { 0.675, 0.170, 0.315, 0.580 };
	// Subimos más la franja inferior para evitar corte del texto (antes 0.905)
	private static final double[] R_BOTONES = { 0.025, 0.890, 0.300, 0.070 };
	private static final double[] R_DESC = { 0.330, 0.890, 0.645, 0.070 };
	// ===========================================================================

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {
		// Colores por defecto / tema (valores por defecto iguales a los que veníamos
		// usando)
		colorEstadoExito = ConfigColor.de("tema.cliosoffice.color.estado.exito", new java.awt.Color(0x4C, 0xAF, 0x50));
		colorEstadoFallo = ConfigColor.de("tema.cliosoffice.color.estado.fallo", new java.awt.Color(0xF4, 0x43, 0x36));
		colorEstadoInstantanea = ConfigColor.de("tema.cliosoffice.color.estado.instantanea",
				new java.awt.Color(0x21, 0x96, 0xF3));
		colorResultadoAnadido = ConfigColor.de("tema.cliosoffice.color.resultado.anadido",
				new java.awt.Color(0x2E, 0x7D, 0x32));
		colorResultadoEliminado = ConfigColor.de("tema.cliosoffice.color.resultado.eliminado",
				new java.awt.Color(0xC6, 0x28, 0x28));
		colorBordeScroll = ConfigColor.de("tema.cliosoffice.color.borde.scroll", new java.awt.Color(0xDD, 0xDD, 0xDD));
		colorFondoPanel = ConfigColor.de("tema.cliosoffice.color.fondo.panel", new java.awt.Color(0xFF, 0xFF, 0xFF));

		colorBeigeListas = ConfigColor.de("tema.cliosoffice.color.beige.listas", new java.awt.Color(0xEB, 0xD7, 0xC0)); // #EBD7C0
		colorTextoListas = ConfigColor.de("tema.cliosoffice.color.texto.listas", java.awt.Color.DARK_GRAY);
		colorBordeListas = ConfigColor.de("tema.cliosoffice.color.borde.listas", new java.awt.Color(0xCC, 0xBC, 0xA5));
		colorBotonFondo = ConfigColor.de("tema.cliosoffice.color.boton.fondo", new java.awt.Color(0xE9, 0xD8, 0xC4));
		colorBotonTexto = ConfigColor.de("tema.cliosoffice.color.boton.texto", new java.awt.Color(0x2B, 0x21, 0x1F));
		colorBordeBoton = ConfigColor.de("tema.cliosoffice.color.boton.borde", new java.awt.Color(0xB7, 0xA6, 0x93));
		colorDoradoTexto = ConfigColor.de("tema.cliosoffice.color.dorado.texto", new java.awt.Color(0xC9, 0xA0, 0x44));

		// Cargar imagen de fondo
		cargarImagenFondo();

		// Ventana
		setTitle(MonitorDePID.idioma.historialDeMods());
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		// Estructura
		construirEstructuraBase();

		// Datos
		cargarArchivosHistoricos();

		// Apariencia
		aplicarApariencia();

		// Relayout proporcional
		panelPrincipal.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				relayout();
			}
		});

		setVisible(true);
		relayout();
	}

	/** Carga la imagen de fondo y guarda su tamaño real. */
	private void cargarImagenFondo() {
		try {
			Path ruta = Statics.carpeta.resolve("imagenes/cliosoffice.png");
			if (!Files.exists(ruta)) {
				MonitorDePID.copiarACarpetaDesdeJar("/imagenes/cliosoffice.png", ruta.toFile());
			}
			if (Files.exists(ruta)) {
				imagenFondo = ImageIO.read(ruta.toFile());
				anchoDiseno = imagenFondo.getWidth();
				altoDiseno = imagenFondo.getHeight();
			} else {
				CrashDetectorLogger.log("No se encontró la imagen de fondo cliosoffice.png");
			}
		} catch (IOException e) {
			CrashDetectorLogger.log("Error cargando imagen de fondo: " + e.getMessage());
			imagenFondo = null;
		}
	}

	/** Construye paneles y scrolls. */
	@Override
	protected void construirEstructuraBase() {
		// Panel que pinta el fondo
		panelPrincipal = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagenFondo != null) {
					g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};
		panelPrincipal.setLayout(null);
		add(panelPrincipal, BorderLayout.CENTER);

		// Zonas
		panelLista1 = new JPanel(new BorderLayout());
		panelLista1.setOpaque(false);
		panelLista1.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		panelPrincipal.add(panelLista1);

		panelLista2 = new JPanel(new BorderLayout());
		panelLista2.setOpaque(false);
		panelLista2.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		panelPrincipal.add(panelLista2);

		panelResultados = new JPanel(new BorderLayout());
		panelResultados.setOpaque(false);
		panelResultados.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6)); // sin marco
		panelPrincipal.add(panelResultados);

		panelDescripcion = new JPanel(new BorderLayout());
		panelDescripcion.setOpaque(false); // transparente
		panelDescripcion.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		panelPrincipal.add(panelDescripcion);

		panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 6));
		panelBotones.setOpaque(false);
		panelPrincipal.add(panelBotones);

		// Scrolls
		scrollLista1 = crearScrollConFondoListas();
		panelLista1.add(scrollLista1, BorderLayout.CENTER);

		scrollLista2 = crearScrollConFondoListas();
		panelLista2.add(scrollLista2, BorderLayout.CENTER);

		scrollResultados = crearScrollTransparenteSinBorde();
		panelResultados.add(scrollResultados, BorderLayout.CENTER);

		// Panel HTML resultados
		resultadoPanel = new JTextPane();
		resultadoPanel.setContentType("text/html");
		resultadoPanel.setEditable(false);
		resultadoPanel.setOpaque(false);
		scrollResultados.setViewportView(resultadoPanel);

		// Botones
		botonComparar = new JButton(MonitorDePID.idioma.comparar());
		botonInstantanea = new JButton(MonitorDePID.idioma.instantanea());
		estilizarBoton(botonComparar);
		estilizarBoton(botonInstantanea);
		panelBotones.add(botonComparar);
		panelBotones.add(botonInstantanea);

		// Descripción
		descripcionHTML = new JTextPane();
		descripcionHTML.setContentType("text/html");
		descripcionHTML.setEditable(false);
		descripcionHTML.setOpaque(false);
		descripcionHTML.setBorder(null);
		actualizarDescripcion();
		panelDescripcion.add(descripcionHTML, BorderLayout.CENTER);

		// Eventos
		botonComparar.addActionListener(e -> compararArchivosSeleccionados());
		botonInstantanea.addActionListener(e -> crearInstantanea());

		// Grupos radio
		grupoIzquierdo = new ButtonGroup();
		grupoDerecho = new ButtonGroup();

		// Contenedores de listas
		panelIzquierdo = new JPanel();
		panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
		panelIzquierdo.setOpaque(true);
		panelIzquierdo.setBackground(colorBeigeListas.obtener());
		scrollLista1.setViewportView(panelIzquierdo);

		panelLista2Contenido = new JPanel();
		panelLista2Contenido.setLayout(new BoxLayout(panelLista2Contenido, BoxLayout.Y_AXIS));
		panelLista2Contenido.setOpaque(true);
		panelLista2Contenido.setBackground(colorBeigeListas.obtener());
		scrollLista2.setViewportView(panelLista2Contenido);
	}

	/** Scroll de resultados: transparente y sin borde. */
	private static JScrollPane crearScrollTransparenteSinBorde() {
		JScrollPane sp = new JScrollPane();
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setOpaque(false);
		sp.setBorder(BorderFactory.createEmptyBorder());
		JViewport vp = sp.getViewport();
		if (vp != null)
			vp.setOpaque(false);
		return sp;
	}

	/** Scroll de listas: fondo beige del tema y borde suave. */
	private JScrollPane crearScrollConFondoListas() {
		JScrollPane sp = new JScrollPane();
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setOpaque(true);
		sp.getViewport().setOpaque(true);
		sp.getViewport().setBackground(colorBeigeListas.obtener());
		sp.setBorder(BorderFactory.createLineBorder(colorBordeListas.obtener()));
		return sp;
	}

	/** Botón con estilo de tema (más grande). */
	private void estilizarBoton(JButton b) {
		b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		b.setBackground(colorBotonFondo.obtener());
		b.setForeground(colorBotonTexto.obtener());
		b.setOpaque(true);
		b.setBorder(BorderFactory.createLineBorder(colorBordeBoton.obtener(), 2));
		b.setFocusPainted(false);
		b.setPreferredSize(new Dimension(140, 38));
		b.setMargin(new Insets(6, 12, 6, 12));
	}

	// -------------------- Layout proporcional --------------------
	private void relayout() {
		int W = panelPrincipal.getWidth();
		int H = panelPrincipal.getHeight();
		if (W <= 0 || H <= 0)
			return;

		setBoundsNorm(panelLista1, R_LISTA1, W, H);
		setBoundsNorm(panelLista2, R_LISTA2, W, H);
		setBoundsNorm(panelResultados, R_RESULT, W, H);
		setBoundsNorm(panelBotones, R_BOTONES, W, H);
		setBoundsNorm(panelDescripcion, R_DESC, W, H);

		panelPrincipal.revalidate();
		panelPrincipal.repaint();
	}

	private static void setBoundsNorm(JPanel p, double[] r, int W, int H) {
		int x = (int) Math.round(r[0] * W);
		int y = (int) Math.round(r[1] * H);
		int w = (int) Math.round(r[2] * W);
		int h = (int) Math.round(r[3] * H);
		p.setBounds(x, y, w, h);
	}
	// -------------------------------------------------------------

	@Override
	public void estilizarRadioArchivo(JRadioButton radio) {
		radio.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		radio.setOpaque(true);
		radio.setBackground(colorBeigeListas.obtener());
		radio.setForeground(colorTextoListas.obtener());
	}

	@Override
	public void estilizarEstadoArchivo(JLabel estado) {
		estado.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
		estado.setOpaque(false);
	}

	@Override
	public void aplicarApariencia() {
		if (descripcionHTML != null)
			descripcionHTML.setMargin(new Insets(2, 6, 2, 6));
		revalidate();
		repaint();
	}

	@Override
	public void generarHTMLResultado(String archivo1, String archivo2, List<String> diferencias) {

		// Convertimos los colores configurables del tema a formato HTML
		String colorAnadido = Config.colorAHexHtml(colorResultadoAnadido.obtener());
		String colorEliminado = Config.colorAHexHtml(colorResultadoEliminado.obtener());
		String colorTextoNormal = Config.colorAHexHtml(colorTextoListas.obtener());

		StringBuilder html = new StringBuilder();

		html.append("<html>");
		html.append("<body style='font-family:sans-serif;font-size:12px;color:").append(colorTextoNormal).append(";'>");

		html.append("<div style='margin:4px 0;'>");

		// Cabecera de comparación
		html.append("<h3 style='margin-bottom:8px;'>").append(MonitorDePID.idioma.comparando()).append(" ")
				.append(archivo1).append(" ").append(MonitorDePID.idioma.con()).append(" ").append(archivo2)
				.append(":</h3>");

		if (diferencias.isEmpty()) {

			html.append("<p style='color:").append(colorAnadido).append(";'>")
					.append(MonitorDePID.idioma.noHayCambios()).append("</p>");

		} else {

			html.append("<ul style='list-style-type:none;padding-left:6px;'>");

			for (String linea : diferencias) {

				boolean esAnadido = linea.startsWith("+");
				String color = esAnadido ? colorAnadido : colorEliminado;

				html.append("<li style='color:").append(color).append(";margin-bottom:3px;'>").append(escapeHtml(linea))
						.append("</li>");
			}

			html.append("</ul>");
		}

		html.append("</div>");
		html.append("</body></html>");

		if (resultadoPanel != null) {
			resultadoPanel.setText(html.toString());
		}
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> l = new ArrayList<>();

		colorEstadoExito.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorEstadoExito());
		colorEstadoFallo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorEstadoFallo());
		colorEstadoInstantanea.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorEstadoInstantanea());
		colorResultadoAnadido.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorResultadoAnadido());
		colorResultadoEliminado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorResultadoEliminado());
		colorBordeScroll.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBordeScroll());
		colorFondoPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondoPanel());
		colorBeigeListas.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBeigeListas());
		colorTextoListas.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTextoListas());
		colorBordeListas.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBordeListas());
		colorBotonFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonFondo());
		colorBotonTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBotonTexto());
		colorBordeBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBordeBoton());
		colorDoradoTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorDoradoTexto());

		l.add(colorEstadoExito);
		l.add(colorEstadoFallo);
		l.add(colorEstadoInstantanea);
		l.add(colorResultadoAnadido);
		l.add(colorResultadoEliminado);
		l.add(colorBordeScroll);
		l.add(colorFondoPanel);
		l.add(colorBeigeListas);
		l.add(colorTextoListas);
		l.add(colorBordeListas);
		l.add(colorBotonFondo);
		l.add(colorBotonTexto);
		l.add(colorBordeBoton);
		l.add(colorDoradoTexto);

		return l;
	}

	/**
	 * Llena ambas listas con el historial (orden descendente) manteniendo la lógica
	 * base; sólo se formatea el texto mostrado.
	 */
	@Override
	protected void cargarArchivosHistoricos() {
		try {
			Path dir = Statics.carpeta.resolve("historia_mods");
			if (Files.exists(dir)) {
				File[] archivos = dir.toFile().listFiles((d, name) -> name.matches("\\d{6}\\.falla")
						|| name.matches("\\d{6}\\.exito") || name.matches("\\d{6}\\.instantanea"));

				if (archivos != null && archivos.length > 0) {
					java.util.Arrays.sort(archivos, (f1, f2) -> {
						int n1 = Integer.parseInt(f1.getName().substring(0, 6));
						int n2 = Integer.parseInt(f2.getName().substring(0, 6));
						return Integer.compare(n2, n1);
					});

					if (panelIzquierdo != null)
						panelIzquierdo.removeAll();
					if (panelLista2Contenido != null)
						panelLista2Contenido.removeAll();

					grupoIzquierdo = new ButtonGroup();
					grupoDerecho = new ButtonGroup();

					for (File f : archivos) {
						JPanel fila = crearLineaArchivo(f, grupoIzquierdo); // usa la lógica base
						formatearFilaNombre(fila, f); // sólo cambiamos los textos visibles
						if (panelIzquierdo != null)
							panelIzquierdo.add(fila);
					}
					for (File f : archivos) {
						JPanel fila = crearLineaArchivo(f, grupoDerecho);
						formatearFilaNombre(fila, f);
						if (panelLista2Contenido != null)
							panelLista2Contenido.add(fila);
					}

					if (panelIzquierdo != null)
						panelIzquierdo.revalidate();
					if (panelLista2Contenido != null)
						panelLista2Contenido.revalidate();
				}
			}
		} catch (Exception e) {
			CrashDetectorLogger.log("Error cargando archivos históricos: " + e.getMessage());
		}
	}

	/**
	 * Cambia el texto que se muestra en cada fila sin alterar selección/compare.
	 */
	private void formatearFilaNombre(JPanel fila, File f) {
		JRadioButton rb = null;
		JLabel lbl = null;
		for (java.awt.Component c : fila.getComponents()) {
			if (c instanceof JRadioButton)
				rb = (JRadioButton) c;
			if (c instanceof JLabel)
				lbl = (JLabel) c;
		}
		if (rb != null) {
			String nombre = f.getName(); // p.ej. 000431.falla
			int i = nombre.lastIndexOf('.');
			String base = i > 0 ? nombre.substring(0, i) : nombre;
			String numero = base.replaceFirst("^0+(?!$)", "");
			rb.setText(numero); // solo número sin ceros/extensión
		}
		if (lbl != null) {
			String ext = "";
			String n = f.getName();
			int p = n.lastIndexOf('.');
			if (p > 0)
				ext = n.substring(p + 1);
			switch (ext) {
			case "exito":
				lbl.setText(" (Éxito)");
				break;
			case "instantanea":
				lbl.setText(" (Instantánea)");
				break;
			default:
				lbl.setText(" (Falló)");
			}
		}
	}

	/** Texto de ayuda dorado en la franja inferior. */
	private void actualizarDescripcion() {
		String html = "<html><body style='font-family:sans-serif;font-size:12px;color:"
				+ aHex(colorDoradoTexto.obtener()) + ";'>" + MonitorDePID.idioma.historaDeModsDesc() + "</body></html>";
		if (descripcionHTML != null)
			descripcionHTML.setText(html);
	}

	private static String aHex(java.awt.Color c) {
		return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
	}

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
		actualizarDescripcion();
		relayout();
	}
}
