package com.asbestosstar.crashdetector.gui.tipos.cfr;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.ClassFileSource;
import org.benf.cfr.reader.api.OutputSinkFactory;
import org.benf.cfr.reader.api.OutputSinkFactory.Sink;
import org.benf.cfr.reader.api.OutputSinkFactory.SinkClass;
import org.benf.cfr.reader.api.OutputSinkFactory.SinkType;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.Pair;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

/**
 * Clase base abstracta para todas las interfaces gráficas de CFR. Proporciona
 * funcionalidad común, incluyendo el manejo de URLs cfr:// y métodos estáticos
 * de utilidad.
 */
public abstract class CfrBase implements CrashDetectorGUI {

	/**
	 * Registro global de tipos de GUI CFR por identificador.
	 */
	public static final Map<String, Supplier<CfrBase>> GUIS = new HashMap<>();

	/**
	 * Procesa una URL del esquema cfr://. El formato esperado es:
	 * cfr://<nombreCompletoDeClase>
	 *
	 * @param url la URL con esquema cfr://
	 */
	public abstract void procesarHipervinculo(String url);

	/**
	 * Método estático para verificar si CFR está disponible en el sistema.
	 *
	 * @return true si CFR está instalado; false en caso contrario.
	 */
	public static boolean estaCfrInstalado() {
		return BuscarParaCFR.estaInstalado();
	}

	/**
	 * Método estático para descompilar una clase dada su nombre. Usa
	 * Buscardor.obtenerBytesDeClase y CFR internamente.
	 *
	 * @param nombreClase nombre completo de la clase (ej. java.lang.Object)
	 * @return código fuente descompilado, o null si no se pudo encontrar la clase.
	 *         Si CFR no está instalado y el entorno es gráfico, se muestra un
	 *         diálogo y se devuelve null.
	 */

	public static String descompilarClase(String nombreClase) {
		if (!estaCfrInstalado()) {
			CrashDetectorLogger.log("No Tienese CFR");
			String mensajeHtml = MonitorDePID.idioma.necesitasInstalarCfr() + Verificaciones.nl_html
					+ MonitorDePID.idioma.noticiaLegalCFR();

			if (!java.awt.GraphicsEnvironment.isHeadless()) {
				try {
					mostrarDialogoInstalacionCFR(mensajeHtml);
				} catch (HeadlessException ignored) {
				}
			}
			return null;
		}
		CrashDetectorLogger.log("Tienese CFR");

		if (nombreClase == null || nombreClase.trim().isEmpty()) {
			CrashDetectorLogger.log("no clase");
			return null;
		}

		final String claseInterna = normalizarNombreClaseInterno(nombreClase); // a/b/C
		final String rutaClase = claseInterna + ".class";

		final byte[] bytesObjetivo = Buscardor.obtenerBytesDeClase(claseInterna);
		if (bytesObjetivo == null) {
			CrashDetectorLogger.log("No obj");
			// intentar con el original por si venía raro
			byte[] b2 = Buscardor.obtenerBytesDeClase(nombreClase);
			if (b2 == null) {
				CrashDetectorLogger.log("no b2 CFR " + nombreClase);
				return null;
		}
			}

		final StringBuilder salida = new StringBuilder(64_000);

		try {
			// Options CFR (puedes añadir más si quieres)
			Map<String, String> opciones = new HashMap<>();
			// Opcional: más legible / menos ruido
			opciones.put("hideutf", "true");
			opciones.put("comments", "false");

			// Fuente de clases en memoria (y fallback al Buscardor para dependencias)
			ClassFileSource source = new ClassFileSource() {

				private String normalizarRutaClass(String path) {
					if (path == null)
						return null;
					String p = path.trim();
					if (p.startsWith("L") && p.endsWith(";"))
						p = p.substring(1, p.length() - 1);
					if (p.toLowerCase().endsWith(".class"))
						p = p.substring(0, p.length() - 6);
					p = p.replace('.', '/');
					return p;
				}

				@Override
				public Pair<byte[], String> getClassFileContent(String path) throws IOException {
					String interna = normalizarRutaClass(path);

					// clase objetivo
					if (claseInterna.equals(interna)) {
						byte[] b = Buscardor.obtenerBytesDeClase(claseInterna);
						if (b != null)
							return Pair.make(b, "in-mem");
					}

					// dependencias: pedimos al Buscardor también
					byte[] dep = Buscardor.obtenerBytesDeClase(interna);
					if (dep != null) {
						return Pair.make(dep, "in-mem-dep");
					}

					throw new IOException("Clase no disponible: " + path);
				}

				// CFR llama esto para ajustar paths; devolvemos el mismo
				@Override
				public String getPossiblyRenamedPath(String path) {
					return path;
				}

				@Override
				public void informAnalysisRelativePathDetail(String usePath, String classFilePath) {
					// no-op
				}

				@Override
				public Collection<String> addJar(String jarPath) {
					return Collections.emptyList();
				}
			};

			OutputSinkFactory sinkFactory = new OutputSinkFactory() {
				@Override
				public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> collection) {
					if (sinkType == SinkType.JAVA) {
						return Collections.singletonList(SinkClass.STRING);
					}
					// Ignora otros sinks por defecto
					return Collections.singletonList(SinkClass.EXCEPTION_MESSAGE);
				}

				@Override
				public <T> Sink<T> getSink(final SinkType sinkType, final SinkClass sinkClass) {
					return t -> {
						if (sinkType == SinkType.JAVA && t != null) {
							salida.append(t.toString());
							if (!salida.toString().endsWith("\n"))
								salida.append('\n');
						}
					};
				}
			};

			CfrDriver driver = new CfrDriver.Builder().withOptions(opciones).withClassFileSource(source)
					.withOutputSink(sinkFactory).build();

			driver.analyse(Collections.singletonList(rutaClase));

			String res = salida.toString().trim();
			CrashDetectorLogger.log("res " + res);

			return res.isEmpty() ? null : res;

		} catch (Throwable e) {
			CrashDetectorLogger.log("Error al descompilar con CFR: " + e.getMessage());
			CrashDetectorLogger.logException(e);
			return null;
		}
	}

	/**
	 * Normaliza un nombre de clase a formato interno ASM: a/b/C Soporta: -
	 * com.ejemplo.Clase - com/ejemplo/Clase - com/ejemplo/Clase.class -
	 * Lcom/ejemplo/Clase;
	 */
	private static String normalizarNombreClaseInterno(String nombre) {
		if (nombre == null)
			return null;
		String s = nombre.trim();
		if (s.isEmpty())
			return s;

		if (s.startsWith("L") && s.endsWith(";")) {
			s = s.substring(1, s.length() - 1);
		}
		if (s.toLowerCase().endsWith(".class")) {
			s = s.substring(0, s.length() - 6);
		}
		s = s.replace('.', '/');
		return s;
	}

	/**
	 * Muestra un diálogo personalizado con dos botones: "Descargar CFR" y "Abrir
	 * carpeta de instalación".
	 */
	private static void mostrarDialogoInstalacionCFR(String mensajeHtml) {
		// Crear panel personalizado
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JEditorPane editor = new JEditorPane("text/html", "<html>" + mensajeHtml + "</html>");
		editor.setEditable(false);
		editor.setOpaque(false);
		editor.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(editor);

		// Crear diálogo
		JDialog dialogo = new JDialog((java.awt.Frame) null, MonitorDePID.idioma.tituloCfrSakura(), true);
		dialogo.setLayout(new java.awt.BorderLayout());
		dialogo.add(panel, java.awt.BorderLayout.CENTER);

		// Botones
		JButton btnDescargar = new JButton(MonitorDePID.idioma.botonDescargarCfr());
		JButton btnAbrirCarpeta = new JButton(MonitorDePID.idioma.botonAbrirCarpetaCfr());

		btnDescargar.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URI("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar"));
			} catch (Exception ex) {
				CrashDetectorLogger.logException(ex);
			}
		});

		btnAbrirCarpeta.addActionListener(e -> {
			Path carpeta = java.nio.file.Paths.get(System.getProperty("user.home"), "crash_detector", "cfr");
			try {
				if (!Files.exists(carpeta)) {
					Files.createDirectories(carpeta);
				}
				Desktop.getDesktop().open(carpeta.toFile());
			} catch (IOException | UnsupportedOperationException ex) {
				// Si open() no está soportado (ej. algunos entornos sin gestor de archivos),
				// abrir en navegador
				try {
					Desktop.getDesktop().browse(carpeta.toUri());
				} catch (Exception ex2) {
					CrashDetectorLogger.logException(ex2);
				}
			} catch (Exception ex) {
				CrashDetectorLogger.logException(ex);
			}
		});

		JPanel panelBotones = new JPanel();
		panelBotones.add(btnDescargar);
		panelBotones.add(btnAbrirCarpeta);
		dialogo.add(panelBotones, java.awt.BorderLayout.SOUTH);

		dialogo.pack();
		dialogo.setLocationRelativeTo(null);
		dialogo.setResizable(false);
		dialogo.setVisible(true);
	}

	/**
	 * Obtiene el archivo JAR de CFR si está instalado.
	 *
	 * @return File apuntando al JAR de CFR, o null si no se encuentra.
	 */
	public static File obtenerCfrJar() {
		return BuscarParaCFR.encontrarCfr();
	}
}