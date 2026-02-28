package com.asbestosstar.crashdetector.gui.tipos.historia;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Clase base abstracta para la interfaz gráfica del historial de mods. Contiene
 * la lógica de carga, comparación y visualización de diferencias. La apariencia
 * y el layout se definen en implementaciones concretas.
 */
public abstract class HistoriaDeModsGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static Map<String, Supplier<HistoriaDeModsGUI>> GUIS = new HashMap<>();

	// Colores configurables (ahora públicos)
	public ConfigColor colorEstadoExito;
	public ConfigColor colorEstadoFallo;
	public ConfigColor colorEstadoInstantanea; // Nuevo color para instantáneas
	public ConfigColor colorResultadoAnadido;
	public ConfigColor colorResultadoEliminado;
	public ConfigColor colorBordeScroll;
	public ConfigColor colorFondoPanel;

	// Contenedores principales (ahora públicos)
	public JPanel panelPrincipal;
	public JPanel panelSuperior;
	public JPanel panelIzquierdo;
	public JPanel panelDerecho;

	// Grupos para selección (izq/der) (ahora públicos)
	public ButtonGroup grupoIzquierdo;
	public ButtonGroup grupoDerecho;

	// Presentación del resultado (HTML) (ahora público)
	public JTextPane resultadoPanel;

	// Botón de comparar y de instantánea (ahora públicos)
	public JButton botonComparar;
	public JButton botonInstantanea; // Nuevo botón para crear instantánea

	// Scrolls (para permitir que la impl ajuste apariencia fácilmente) (ahora
	// públicos)
	public JScrollPane scrollIzquierdo;
	public JScrollPane scrollDerecho;
	public JScrollPane scrollResultado;

	// Panel inferior con información adicional (ahora público)
	public JPanel panelInferior;
	public JTextPane descripcionHTML;

	// ====== Constructor ======
	public HistoriaDeModsGUI() {
		super();
		// Inicializar colores con valores por defecto para evitar NPE
		// Estos serán sobrescritos por la implementación concreta en init()
		colorEstadoExito = ConfigColor.de("tema.base.historia_mods.color.estado.exito", java.awt.Color.GREEN);
		colorEstadoFallo = ConfigColor.de("tema.base.historia_mods.color.estado.fallo", java.awt.Color.RED);
		colorEstadoInstantanea = ConfigColor.de("tema.base.historia_mods.color.estado.instantanea",
				java.awt.Color.BLUE); // Nuevo color
		colorResultadoAnadido = ConfigColor.de("tema.base.historia_mods.color.resultado.anadido", java.awt.Color.GREEN);
		colorResultadoEliminado = ConfigColor.de("tema.base.historia_mods.color.resultado.eliminado",
				java.awt.Color.RED);
		colorBordeScroll = ConfigColor.de("tema.base.historia_mods.color.borde.scroll", java.awt.Color.LIGHT_GRAY);
		colorFondoPanel = ConfigColor.de("tema.base.historia_mods.color.fondo.panel", java.awt.Color.WHITE);
	}

	// ====== Estructura base (ahora abstracta o vacía) ======
	protected abstract void construirEstructuraBase();

	// ====== Lógica técnica: carga/parseo/normalización/comparación ======
	protected void cargarArchivosHistoricos() {
		try {
			Path directorioHistorial = Statics.carpeta.resolve("historia_mods");
			if (Files.exists(directorioHistorial)) {
				File[] archivos = directorioHistorial.toFile().listFiles((dir, name) -> name.matches("\\d{6}\\.falla")
						|| name.matches("\\d{6}\\.exito") || name.matches("\\d{6}\\.instantanea")); // Incluye
																									// instantáneas

				if (archivos != null && archivos.length > 0) {
					// Orden descendente por número de archivo
					java.util.Arrays.sort(archivos, (f1, f2) -> {
						int num1 = Integer.parseInt(f1.getName().substring(0, 6));
						int num2 = Integer.parseInt(f2.getName().substring(0, 6));
						return Integer.compare(num2, num1);
					});

					// Reset panels y grupos
					if (panelIzquierdo != null) {
						panelIzquierdo.removeAll();
					}
					if (panelDerecho != null) {
						panelDerecho.removeAll();
					}
					grupoIzquierdo = new ButtonGroup();
					grupoDerecho = new ButtonGroup();

					for (File f : archivos) {
						if (panelIzquierdo != null) {
							panelIzquierdo.add(crearLineaArchivo(f, grupoIzquierdo));
						}
					}
					for (File f : archivos) {
						if (panelDerecho != null) {
							panelDerecho.add(crearLineaArchivo(f, grupoDerecho));
						}
					}

					// Refrescar y scrollear al final
					if (panelIzquierdo != null) {
						panelIzquierdo.revalidate();
					}
					if (panelDerecho != null) {
						panelDerecho.revalidate();
					}
					scrollHastaFinal(scrollIzquierdo);
					scrollHastaFinal(scrollDerecho);
				}
			}
		} catch (Exception e) {
			CrashDetectorLogger.log("Error cargando archivos históricos: " + e.getMessage());
		}
	}

	protected JPanel crearLineaArchivo(File archivo, ButtonGroup grupo) {
		JPanel linea = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		linea.setOpaque(false);

		// Radio con el nombre del archivo
		JRadioButton radio = new JRadioButton(archivo.getName());
		radio.setOpaque(false);
		radio.setActionCommand(archivo.getName());
		grupo.add(radio);

		// Etiqueta de estado (texto localizado; color lo decide la impl)
		JLabel estado = new JLabel();
		if (archivo.getName().endsWith(".exito")) {
			estado.setText(" (" + MonitorDePID.idioma.exito() + ")");
			if (colorEstadoExito != null) {
				estado.setForeground(colorEstadoExito.obtener());
			}
		} else if (archivo.getName().endsWith(".falla")) {
			estado.setText(" (" + MonitorDePID.idioma.fallo() + ")");
			if (colorEstadoFallo != null) {
				estado.setForeground(colorEstadoFallo.obtener());
			}
		} else if (archivo.getName().endsWith(".instantanea")) { // Nuevo estado para instantánea
			estado.setText(" (" + MonitorDePID.idioma.instantanea() + ")");
			if (colorEstadoInstantanea != null) {
				estado.setForeground(colorEstadoInstantanea.obtener());
			}
		}

		// Hooks de apariencia (la impl puede ajustar fuente, margen, etc.)
		estilizarRadioArchivo(radio);
		estilizarEstadoArchivo(estado);

		linea.add(radio);
		linea.add(estado);
		return linea;
	}

// ====== Reemplaza en HistoriaDeModsGUI estos métodos ======

	/** Crea una instantánea del archivo seleccionado en la columna izquierda. */
	protected void crearInstantanea() {
		String archivoSeleccionado = (grupoIzquierdo == null || grupoIzquierdo.getSelection() == null) ? null
				: grupoIzquierdo.getSelection().getActionCommand();

		if (archivoSeleccionado == null) {
			if (resultadoPanel != null) {
				resultadoPanel.setText("<html><body><font color='red'>" + MonitorDePID.idioma.seleccionarUnArchivo()
						+ "</font></body></html>");
			}
			return;
		}

		try {
			Path directorio = Statics.carpeta.resolve("historia_mods");
			Path rutaOriginal = directorio.resolve(archivoSeleccionado);

			if (!Files.exists(rutaOriginal)) {
				throw new IOException("Archivo original no encontrado: " + rutaOriginal);
			}

			// === Nuevo: nombre de instantánea = mismo base + ".instantanea" ===
			String base = archivoSeleccionado.replaceFirst("\\.[^.]+$", ""); // quita la extensión
			String nuevoNombre = generarNombreInstantaneaDesdeBase(base); // p.ej. 000452.instantanea
			Path rutaNueva = directorio.resolve(nuevoNombre);

			Files.copy(rutaOriginal, rutaNueva);
			CrashDetectorLogger.log("Instantánea creada: " + rutaNueva);

			// Recargar la UI y avisar
			cargarArchivosHistoricos();
			if (resultadoPanel != null) {
				resultadoPanel.setText("<html><body><font color='green'>"
						+ MonitorDePID.idioma.instantaneaCreadaCorrectamente() + "</font></body></html>");
			}

		} catch (Exception e) {
			CrashDetectorLogger.log("Error creando instantánea: " + e.getMessage());
			if (resultadoPanel != null) {
				resultadoPanel.setText("<html><body><font color='red'>" + MonitorDePID.idioma.errorCreandoInstantanea()
						+ "</font></body></html>");
			}
		}
	}

	/**
	 * Genera un nombre para la instantánea a partir del nombre base del archivo
	 * original (misma raíz, extensión .instantanea). Si ya existe, agrega _01, _02,
	 * ...
	 */
	private String generarNombreInstantaneaDesdeBase(String base) {
		Path directorio = Statics.carpeta.resolve("historia_mods");
		String nombre = base + ".instantanea";
		Path ruta = directorio.resolve(nombre);

		int i = 1;
		while (Files.exists(ruta)) {
			nombre = base + "_" + String.format("%02d", i) + ".instantanea";
			ruta = directorio.resolve(nombre);
			i++;
		}
		return nombre;
	}

	protected void compararArchivosSeleccionados() {
		String archivoIzq = (grupoIzquierdo == null || grupoIzquierdo.getSelection() == null) ? null
				: grupoIzquierdo.getSelection().getActionCommand();
		String archivoDer = (grupoDerecho == null || grupoDerecho.getSelection() == null) ? null
				: grupoDerecho.getSelection().getActionCommand();

		if (archivoIzq == null || archivoDer == null || archivoIzq.equals(archivoDer)) {
			if (resultadoPanel != null) {
				resultadoPanel.setText("<html><body><font color='red'>" + MonitorDePID.idioma.seleccionarDosArchivos()
						+ "</font></body></html>");
			}
			return;
		}

		try {
			Path directorio = Statics.carpeta.resolve("historia_mods");
			Path rutaIzquierda = directorio.resolve(archivoIzq);
			Path rutaDerecha = directorio.resolve(archivoDer);

			Map<String, String> modsIzquierda = leerModsNormalizados(rutaIzquierda);
			Map<String, String> modsDerecha = leerModsNormalizados(rutaDerecha);

			List<String> diferencias = compararModsNormalizados(modsIzquierda, modsDerecha);

			generarHTMLResultado(archivoIzq, archivoDer, diferencias);
		} catch (Exception e) {
			CrashDetectorLogger.log("Error comparando archivos: " + e.getMessage());
			if (resultadoPanel != null) {
				resultadoPanel.setText("<html><body><font color='red'>" + MonitorDePID.idioma.errorComparandoArchivos()
						+ "</font></body></html>");
			}
		}
	}

	protected Map<String, String> leerModsNormalizados(Path rutaArchivo) throws IOException {

		Map<String, String> mods = new HashMap<>();

		// Directorio actual donde se está ejecutando la aplicación
		Path directorioActual = Path.of("").toAbsolutePath().normalize();

		try (BufferedReader lector = new BufferedReader(
				new InputStreamReader(Files.newInputStream(rutaArchivo), StandardCharsets.UTF_8))) {

			String linea;

			while ((linea = lector.readLine()) != null) {

				if (!linea.trim().isEmpty()) {

					Path rutaOriginal = Path.of(linea.trim()).toAbsolutePath().normalize();
					String rutaRelativa;

					try {
						// Convertimos la ruta absoluta en relativa al directorio actual
						rutaRelativa = directorioActual.relativize(rutaOriginal).toString().replace("\\", "/");
					} catch (Exception e) {
						// Si no se puede relativizar (ej. distinto root en Windows),
						// usamos al menos el nombre del archivo
						rutaRelativa = rutaOriginal.getFileName().toString();
					}

					String nombreNormalizado = normalizarNombreMod(rutaRelativa);
					mods.put(nombreNormalizado, rutaRelativa);
				}
			}
		}

		return mods;
	}

	protected String normalizarNombreMod(String ruta) {
		String nombre = new File(ruta).getName().toLowerCase();
		int indicePunto = nombre.lastIndexOf('.');
		if (indicePunto > 0) {
			nombre = nombre.substring(0, indicePunto);
		}
		return nombre;
	}

	protected List<String> compararModsNormalizados(Map<String, String> modsAnt, Map<String, String> modsNuevos) {
		List<String> difs = new ArrayList<>();

		Set<String> eliminados = new TreeSet<>(modsAnt.keySet());
		eliminados.removeAll(modsNuevos.keySet());

		Set<String> anadidos = new TreeSet<>(modsNuevos.keySet());
		anadidos.removeAll(modsAnt.keySet());

		for (String mod : eliminados) {
			difs.add("- " + modsAnt.get(mod));
		}
		for (String mod : anadidos) {
			difs.add("+ " + modsNuevos.get(mod));
		}
		return difs;
	}

	public void generarHTMLResultado(String archivo1, String archivo2, List<String> diferencias) {
		StringBuilder html = new StringBuilder();
		html.append("<html><body>");
		html.append("<div style='margin:10px 0;padding:10px;border:1px solid #ccc'>").append("<h3>")
				.append(MonitorDePID.idioma.comparando()).append(" ").append(archivo1).append(" ")
				.append(MonitorDePID.idioma.con()).append(" ").append(archivo2).append(":</h3>");

		if (diferencias.isEmpty()) {
			html.append("<p style='color:green'>").append(MonitorDePID.idioma.noHayCambios()).append("</p>");
		} else {
			html.append("<ul>");
			for (String linea : diferencias) {
				String color = linea.startsWith("+")
						? (colorResultadoAnadido != null ? colorResultadoAnadido.obtener().toString() : "#000000")
						: (colorResultadoEliminado != null ? colorResultadoEliminado.obtener().toString() : "#000000");
				html.append("<li style='color:").append(color).append("'>").append(linea).append("</li>");
			}
			html.append("</ul>");
		}
		html.append("</div></body></html>");
		if (resultadoPanel != null) {
			resultadoPanel.setText(html.toString());
		}
	}

	/**
	 * Escapa caracteres especiales para evitar que el HTML del JTextPane se rompa
	 * si una ruta contiene símbolos especiales.
	 */
	public String escapeHtml(String texto) {
		return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	public void scrollHastaFinal(JScrollPane sp) {
		if (sp != null && sp.getVerticalScrollBar() != null) {
			sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		}
	}

	// ====== Hooks de apariencia (a implementar/ajustar en la impl) ======

	public abstract void estilizarRadioArchivo(JRadioButton radio);

	public abstract void estilizarEstadoArchivo(JLabel estado);

	public abstract void aplicarApariencia();

	// ====== CrashDetectorGUI ======

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
	}

	@Override
	public boolean puedeEditarApariencia() {
		return true;
	}

	@Override
	public TipoGUI<HistoriaDeModsGUI> tipo() {
		return TipoGUI.HISTORIA_DE_MODS;
	}

	@Override
	public abstract void init();

	@Override
	public abstract String id();

}