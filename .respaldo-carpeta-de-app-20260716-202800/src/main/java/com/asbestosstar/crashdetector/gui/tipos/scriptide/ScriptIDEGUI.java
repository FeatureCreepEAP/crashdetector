package com.asbestosstar.crashdetector.gui.tipos.scriptide;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.deps.DescargadorDependenciasMaven;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp.ScriptIntellisense;
import com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp.ScriptIntellisenseFactory;
import com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp.ScriptIntellisenseLocal;

public abstract class ScriptIDEGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	public static final long serialVersionUID = 1L;

	public static Map<String, Supplier<ScriptIDEGUI>> GUIS = new HashMap<String, Supplier<ScriptIDEGUI>>();

	public File carpetaProyecto;
	public File archivoActual;
	public TipoProyectoScript proyectoActual = TipoProyectoScript.ZENSCRIPT;
	public boolean textoModificado;

	public JTextPane editor;
	public DefaultListModel<File> modeloArchivos = new DefaultListModel<File>();
	public JList<File> listaArchivos;
	public JLabel etiquetaEstado;

	public JPopupMenu menuCompletado;
	public DefaultListModel<String> modeloCompletado = new DefaultListModel<String>();
	public JList<String> listaCompletado = new JList<String>(modeloCompletado);

	public final File carpetaDepsIDE = new File(Statics.carpeta_mundial_como_archivo, "deps");

	public ScriptIntellisense intellisense;
	public Timer temporizadorCambioIntellisense;
	/**
	 * Temporizador que dispara el completado automatico mientras el usuario
	 * escribe.
	 */
	public Timer temporizadorCompletadoAuto;
	public boolean intellisenseInicializado;
	public int versionDocumento = 1;
	public String ultimoTextoEnviadoAIntellisense = "";

	/**
	 * Gramatica TextMate actualmente cargada (puede ser null si no esta
	 * disponible).
	 */
	public GramaticaTextMate gramaticaActual;

	/** Configuracion de lenguaje TextMate actualmente cargada. */
	public ConfigLenguajeTextMate configLenguajeActual;

	/**
	 * Cache de gramaticas ya cargadas por tipo de proyecto para no releer el disco
	 * innecesariamente.
	 */
	public static final Map<TipoProyectoScript, GramaticaTextMate> CACHE_GRAMATICAS = new HashMap<>();

	/** Cache de configuraciones de lenguaje ya cargadas. */
	public static final Map<TipoProyectoScript, ConfigLenguajeTextMate> CACHE_CONFIGS_LENGUAJE = new HashMap<>();

	public static class DependenciaScriptIDE {
		public final String groupId;
		public final String artifactId;
		public final String version;

		public DependenciaScriptIDE(String groupId, String artifactId, String version) {
			this.groupId = groupId;
			this.artifactId = artifactId;
			this.version = version;
		}

		public String nombreVisible() {
			return groupId + ":" + artifactId + ":" + version;
		}

		public boolean coordenadaValida() {
			return groupId != null && !groupId.trim().isEmpty() && artifactId != null && !artifactId.trim().isEmpty()
					&& version != null && !version.trim().isEmpty();
		}

		public boolean coincideConJar(File archivo) {
			if (archivo == null || archivo.getName() == null) {
				return false;
			}

			String n = archivo.getName().toLowerCase();
			String exacto = (artifactId + "-" + version + ".jar").toLowerCase();
			String aproximado = (artifactId + "-").toLowerCase();

			return n.equals(exacto) || n.startsWith(aproximado);
		}
	}

	/*
	 * LSP4J es el cliente/protocolo LSP. No contiene servidores de lenguaje. Los
	 * servidores reales se configuran aparte por tipo de proyecto.
	 */
	public static final List<DependenciaScriptIDE> DEPENDENCIAS_IDE_REQUERIDAS = Arrays.asList(
			new DependenciaScriptIDE("org.eclipse.lsp4j", "org.eclipse.lsp4j", "1.0.0"),
			new DependenciaScriptIDE("org.eclipse.lsp4j", "org.eclipse.lsp4j.jsonrpc", "1.0.0"),
			new DependenciaScriptIDE("org.jboss", "jboss-dmr", "1.6.1.Final"));

	@Override
	public TipoGUI tipo() {
		return TipoGUI.SCRIPT_IDE;
	}

	public void inicializarEditorBase() {
		if (editor == null) {
			editor = new JTextPane(new DefaultStyledDocument());
		}

		editor.setFont(new Font(CrashDetectorGUI.esMac() ? "Monospaced" : "Consolas", Font.PLAIN, 13));

		editor.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				textoCambiado();
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				textoCambiado();
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
			}
		});

		editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK), "completar");
		editor.getActionMap().put("completar", new javax.swing.AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				mostrarCompletado();
			}
		});

		editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cerrar_completado");
		editor.getActionMap().put("cerrar_completado", new javax.swing.AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				cerrarCompletado();
				editor.requestFocusInWindow();
			}
		});

		editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ide_enter_intellisense");
		editor.getActionMap().put("ide_enter_intellisense", new javax.swing.AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				accionEnterIntellisense();
			}
		});

		editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "ide_completado_abajo");
		editor.getActionMap().put("ide_completado_abajo", new javax.swing.AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (menuCompletado != null && menuCompletado.isVisible()) {
					moverSeleccionCompletado(1);
					return;
				}

				moverCaretVertical(1);
			}
		});

		editor.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "ide_completado_arriba");
		editor.getActionMap().put("ide_completado_arriba", new javax.swing.AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (menuCompletado != null && menuCompletado.isVisible()) {
					moverSeleccionCompletado(-1);
					return;
				}

				moverCaretVertical(-1);
			}
		});

		temporizadorCambioIntellisense = new Timer(350, e -> enviarCambioAIntellisense());
		temporizadorCambioIntellisense.setRepeats(false);

		// Completado automatico: se dispara despues de que el usuario deja de escribir.
		temporizadorCompletadoAuto = new Timer(600, e -> mostrarCompletadoAuto());
		temporizadorCompletadoAuto.setRepeats(false);

		inicializarTeclasListaCompletado();
		inicializarIntellisenseSiExiste();
	}

	public void accionEnterIntellisense() {
		if (menuCompletado != null && menuCompletado.isVisible()) {
			insertarCompletadoSeleccionado();
			editor.requestFocusInWindow();
			return;
		}

		try {
			editor.getDocument().insertString(editor.getCaretPosition(), "\n", null);
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
		}
	}

	public void moverCaretVertical(int direccion) {
		try {
			int pos = editor.getCaretPosition();
			int nuevo;

			if (direccion > 0) {
				nuevo = javax.swing.text.Utilities.getPositionBelow(editor, pos, 0);
			} else {
				nuevo = javax.swing.text.Utilities.getPositionAbove(editor, pos, 0);
			}

			if (nuevo >= 0) {
				editor.getCaret().setDot(nuevo);
			}
		} catch (Throwable ignored) {
		}
	}

	public void inicializarTeclasListaCompletado() {
		listaCompletado.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ide_lista_enter_intellisense");
		listaCompletado.getActionMap().put("ide_lista_enter_intellisense", new javax.swing.AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				accionEnterIntellisense();
			}
		});

		listaCompletado.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"ide_lista_escape_intellisense");
		listaCompletado.getActionMap().put("ide_lista_escape_intellisense", new javax.swing.AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				cerrarCompletado();
				editor.requestFocusInWindow();
			}
		});

		listaCompletado.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "ide_lista_abajo_intellisense");
		listaCompletado.getActionMap().put("ide_lista_abajo_intellisense", new javax.swing.AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				moverSeleccionCompletado(1);
			}
		});

		listaCompletado.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "ide_lista_arriba_intellisense");
		listaCompletado.getActionMap().put("ide_lista_arriba_intellisense", new javax.swing.AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				moverSeleccionCompletado(-1);
			}
		});
	}

	public void inicializarIntellisenseSiExiste() {
		try {
			if (intellisense != null) {
				return;
			}

			intellisense = ScriptIntellisenseFactory.crear(proyectoActual, carpetaProyecto);

			if (intellisense == null) {
				intellisense = new ScriptIntellisenseLocal();
			}

			intellisenseInicializado = true;
			actualizarEstado();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			intellisense = new ScriptIntellisenseLocal();
			intellisenseInicializado = false;
			actualizarEstado();
		}
	}

	public void reiniciarIntellisense() {
		cerrarIntellisense();

		try {
			intellisense = ScriptIntellisenseFactory.crear(proyectoActual, carpetaProyecto);
			if (intellisense == null) {
				intellisense = new ScriptIntellisenseLocal();
			}
			intellisenseInicializado = true;
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			intellisense = new ScriptIntellisenseLocal(this);
			intellisenseInicializado = false;
		}

		if (archivoActual != null && editor != null) {
			abrirDocumentoEnIntellisense();
		}

		actualizarEstado();
	}

	public void cerrarIntellisense() {
		try {
			if (intellisense != null) {
				intellisense.cerrar();
			}
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}

		intellisense = null;
		intellisenseInicializado = false;
	}

	public void textoCambiado() {
		textoModificado = true;
		versionDocumento++;
		programarResaltado();
		programarCambioIntellisense();
		programarCompletadoAuto();
		actualizarEstado();
	}

	public void programarCompletadoAuto() {
		if (temporizadorCompletadoAuto == null) {
			return;
		}
		// Solo programar si hay al menos un caracter de prefijo para no abrir
		// el menu en cada tecla de puntuacion o espacio
		String prefijo = palabraActual();
		if (prefijo == null || prefijo.isEmpty()) {
			// Si el menu esta visible y el prefijo quedo vacio, cerrarlo
			cerrarCompletado();
			return;
		}
		temporizadorCompletadoAuto.restart();
	}

	/**
	 * Disparado automaticamente por el temporizador. Solo muestra el menu si el
	 * prefijo tiene al menos un caracter de letra o digito, evitando abrir el popup
	 * al escribir espacios u operadores.
	 */
	public void mostrarCompletadoAuto() {
		String prefijo = palabraActual();
		if (prefijo == null || prefijo.isEmpty()) {
			return;
		}
		mostrarCompletado();
	}

	public void programarCambioIntellisense() {
		if (temporizadorCambioIntellisense == null) {
			return;
		}

		if (archivoActual == null || editor == null) {
			return;
		}

		temporizadorCambioIntellisense.restart();
	}

	public void enviarCambioAIntellisense() {
		if (intellisense == null || archivoActual == null || editor == null) {
			return;
		}

		try {
			String texto = editor.getText();
			if (texto.equals(ultimoTextoEnviadoAIntellisense)) {
				return;
			}

			ultimoTextoEnviadoAIntellisense = texto;
			intellisense.cambiarDocumento(archivoActual, texto);
			actualizarEstado();
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public void abrirDocumentoEnIntellisense() {
		if (intellisense == null || archivoActual == null || editor == null) {
			return;
		}

		try {
			String texto = editor.getText();
			ultimoTextoEnviadoAIntellisense = texto;
			versionDocumento++;
			intellisense.abrirDocumento(archivoActual, texto);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public void guardarDocumentoEnIntellisense() {
		if (intellisense == null || archivoActual == null || editor == null) {
			return;
		}

		try {
			// guardarDocumento not defined in ScriptIntellisense interface; no-op
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public void programarResaltado() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				aplicarResaltadoBasico();
			}
		});
	}

	public void abrirCarpetaProyecto() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle(MonitorDePID.idioma.ideScriptAbrirCarpeta());

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			carpetaProyecto = chooser.getSelectedFile();
			recargarArchivosProyecto();
			reiniciarIntellisense();
			actualizarEstado();
		}
	}

	public void recargarArchivosProyecto() {
		modeloArchivos.clear();

		if (carpetaProyecto == null || !carpetaProyecto.isDirectory()) {
			return;
		}

		List<File> archivos = new ArrayList<File>();
		buscarArchivosRecursivo(carpetaProyecto, archivos);
		Collections.sort(archivos, (a, b) -> a.getPath().compareToIgnoreCase(b.getPath()));

		for (File f : archivos) {
			modeloArchivos.addElement(f);
		}
	}

	public void buscarArchivosRecursivo(File carpeta, List<File> salida) {
		File[] archivos = carpeta == null ? null : carpeta.listFiles();
		if (archivos == null) {
			return;
		}

		for (File f : archivos) {
			if (f == null) {
				continue;
			}

			if (f.isDirectory()) {
				buscarArchivosRecursivo(f, salida);
			} else if (proyectoActual == null || proyectoActual.aceptaArchivo(f.getName())) {
				salida.add(f);
			}
		}
	}

	public void abrirArchivoDesdeLista() {
		if (listaArchivos == null) {
			return;
		}

		File f = listaArchivos.getSelectedValue();
		if (f != null) {
			abrirArchivo(f);
		}
	}

	public void abrirArchivoConDialogo() {
		JFileChooser chooser = new JFileChooser(carpetaProyecto == null ? new File(".") : carpetaProyecto);
		chooser.setDialogTitle(MonitorDePID.idioma.ideScriptAbrirArchivo());

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			abrirArchivo(chooser.getSelectedFile());
		}
	}

	public void abrirArchivo(File archivo) {
		try {
			if (archivo == null || !archivo.isFile()) {
				return;
			}

			archivoActual = archivo;
			String texto = new String(Files.readAllBytes(archivo.toPath()), StandardCharsets.UTF_8);

			editor.setText(texto);
			editor.setCaretPosition(0);

			textoModificado = false;
			versionDocumento++;
			ultimoTextoEnviadoAIntellisense = texto;

			programarResaltado();
			abrirDocumentoEnIntellisense();
			actualizarEstado();
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
			JOptionPane.showMessageDialog(this,
					MonitorDePID.idioma.ideScriptErrorAbrirArchivo() + ": " + ex.getMessage(),
					MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void guardarArchivoActual() {
		try {
			if (archivoActual == null) {
				guardarArchivoComo();
				return;
			}

			Files.write(archivoActual.toPath(), editor.getText().getBytes(StandardCharsets.UTF_8));

			textoModificado = false;
			versionDocumento++;

			guardarDocumentoEnIntellisense();
			actualizarEstado();
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
			JOptionPane.showMessageDialog(this,
					MonitorDePID.idioma.ideScriptErrorGuardarArchivo() + ": " + ex.getMessage(),
					MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void guardarArchivoComo() {
		JFileChooser chooser = new JFileChooser(carpetaProyecto == null ? new File(".") : carpetaProyecto);
		chooser.setDialogTitle(MonitorDePID.idioma.ideScriptGuardarComo());

		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			archivoActual = chooser.getSelectedFile();
			guardarArchivoActual();
			recargarArchivosProyecto();
			abrirDocumentoEnIntellisense();
		}
	}

	public void nuevoArchivo() {
		archivoActual = null;
		editor.setText(textoInicialParaProyecto());
		textoModificado = true;
		versionDocumento++;
		ultimoTextoEnviadoAIntellisense = "";

		programarResaltado();
		actualizarEstado();
	}

	public String textoInicialParaProyecto() {
		if (proyectoActual == TipoProyectoScript.KUBEJS) {// necesita npm install -g typescript
															// typescript-language-server

			return "// KubeJS script\n";
		}

		if (proyectoActual == TipoProyectoScript.ZENSCRIPT) {
			return "// ZenScript\n";
		}

		return "";
	}

	public void cambiarProyecto(TipoProyectoScript nuevo) {
		if (nuevo == null) {
			return;
		}

		if (!nuevo.habilitadoAhora) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.ideScriptProyectoDeshabilitadoAviso(),
					MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		proyectoActual = nuevo;
		recargarArchivosProyecto();
		reiniciarIntellisense();
		programarResaltado();
		actualizarEstado();
	}

	public String palabraActual() {
		try {
			int pos = editor.getCaretPosition();
			String texto = editor.getDocument().getText(0, pos);
			int ini = texto.length();

			while (ini > 0) {
				char c = texto.charAt(ini - 1);
				if (!Character.isLetterOrDigit(c) && c != '_' && c != '$') {
					break;
				}
				ini--;
			}

			return texto.substring(ini);
		} catch (Exception ex) {
			return "";
		}
	}

	public void mostrarCompletado() {
		inicializarIntellisenseSiExiste();

		final String prefijo = palabraActual();
		final int posicion = editor.getCaretPosition();
		final String texto = editor.getText();

		new Thread(new Runnable() {
			@Override
			public void run() {
				List<String> completados = null;

				try {
					if (intellisense != null) {
						completados = intellisense.completar(archivoActual, texto, posicion).stream()
								.map(s -> s.etiqueta).collect(java.util.stream.Collectors.toList());
					}
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
				}

				if (completados == null || completados.isEmpty()) {
					completados = completadosLocales(prefijo);
				}

				final List<String> resultado = completados;

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						mostrarListaCompletado(resultado);
					}
				});
			}
		}).start();
	}

	public List<String> completadosLocales(String prefijo) {
		String p = prefijo == null ? "" : prefijo.toLowerCase();
		List<String> ret = new ArrayList<String>();

		for (String s : palabrasCompletado()) {
			if (p.isEmpty() || s.toLowerCase().startsWith(p)) {
				ret.add(s);
			}
		}

		Collections.sort(ret);
		return ret;
	}

	public void mostrarListaCompletado(List<String> valores) {
		modeloCompletado.clear();

		if (valores == null || valores.isEmpty()) {
			cerrarCompletado();
			return;
		}

		Set<String> vistos = new HashSet<String>();

		for (String s : valores) {
			if (s == null) {
				continue;
			}

			String limpio = s.trim();
			if (limpio.isEmpty()) {
				continue;
			}

			if (vistos.add(limpio)) {
				modeloCompletado.addElement(limpio);
			}
		}

		if (modeloCompletado.isEmpty()) {
			cerrarCompletado();
			return;
		}

		if (menuCompletado == null) {
			menuCompletado = new JPopupMenu();
			listaCompletado.setVisibleRowCount(10);
			listaCompletado.setCellRenderer(new RenderCompletado());

			listaCompletado.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() >= 2) {
						insertarCompletadoSeleccionado();
					}
				}
			});

			menuCompletado.add(new JScrollPane(listaCompletado));
		}

		listaCompletado.setSelectedIndex(0);

		try {
			Rectangle r = editor.modelToView(editor.getCaretPosition());
			Point p = r == null ? new Point(12, 12) : new Point(r.x, r.y + r.height);
			menuCompletado.show(editor, p.x, p.y);

			// Importante: el foco se queda en el editor, asi Enter acepta el completado.
			editor.requestFocusInWindow();
		} catch (Exception ex) {
			menuCompletado.show(editor, 12, 12);
			editor.requestFocusInWindow();
		}
	}

	public void moverSeleccionCompletado(int delta) {
		if (listaCompletado == null || modeloCompletado == null || modeloCompletado.isEmpty()) {
			return;
		}

		int i = listaCompletado.getSelectedIndex();
		if (i < 0) {
			i = 0;
		} else {
			i += delta;
		}

		if (i < 0) {
			i = modeloCompletado.size() - 1;
		}

		if (i >= modeloCompletado.size()) {
			i = 0;
		}

		listaCompletado.setSelectedIndex(i);
		listaCompletado.ensureIndexIsVisible(i);
	}

	public void cerrarCompletado() {
		if (menuCompletado != null) {
			menuCompletado.setVisible(false);
		}
	}

	public void insertarCompletadoSeleccionado() {
		String s = listaCompletado.getSelectedValue();
		if (s == null) {
			return;
		}

		try {
			String insertar = normalizarTextoCompletado(s);
			String prefijo = palabraActual();
			int pos = editor.getCaretPosition();
			int ini = Math.max(0, pos - prefijo.length());

			editor.getDocument().remove(ini, prefijo.length());
			editor.getDocument().insertString(ini, insertar, null);
			editor.setCaretPosition(ini + insertar.length());

			cerrarCompletado();
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
		}
	}

	public String normalizarTextoCompletado(String s) {
		if (s == null) {
			return "";
		}

		int p = s.indexOf('\t');
		if (p >= 0) {
			return s.substring(0, p).trim();
		}

		p = s.indexOf(" - ");
		if (p > 0) {
			return s.substring(0, p).trim();
		}

		return s.trim();
	}

	public List<String> palabrasCompletado() {
		List<String> ret = new ArrayList<String>();

		if (proyectoActual == TipoProyectoScript.KUBEJS) {
			ret.addAll(Arrays.asList("ServerEvents", "ClientEvents", "StartupEvents", "ItemEvents", "BlockEvents",
					"PlayerEvents", "LevelEvents", "EntityEvents", "RecipeViewerEvents", "recipes", "tags", "loaded",
					"event", "remove", "replaceInput", "replaceOutput", "shaped", "shapeless", "smelting", "blasting",
					"smoking", "campfireCooking", "stonecutting", "custom", "Item", "Ingredient", "JsonIO"));
		} else if (proyectoActual == TipoProyectoScript.ZENSCRIPT) {
			ret.addAll(Arrays.asList("import", "val", "var", "function", "as", "in", "for", "if", "else", "return",
					"recipes", "craftingTable", "furnace", "removeRecipe", "addShaped", "addShapeless", "IItemStack",
					"IIngredient", "println"));
		} else if (proyectoActual == TipoProyectoScript.MINEFLAYER) {
			ret.addAll(Arrays.asList("import", "from", "def", "class", "return", "mineflayer", "bot", "chat",
					"pathfinder", "Vec3"));
		} else if (proyectoActual == TipoProyectoScript.COMPUTERCRAFT_LUA) {
			ret.addAll(Arrays.asList("local", "function", "end", "if", "then", "else", "elseif", "for", "while", "do",
					"return", "print", "term", "redstone", "peripheral", "turtle"));
		} else if (proyectoActual == TipoProyectoScript.GROOVYSCRIPT) {
			ret.addAll(Arrays.asList("def", "class", "import", "return", "if", "else", "for", "while", "closure"));
		} else {
			ret.addAll(Arrays.asList("true", "false", "null"));
		}

		Collections.sort(ret);
		return ret;
	}

	public void aplicarResaltadoBasico() {
		if (editor == null) {
			return;
		}

		try {
			StyledDocument doc = editor.getStyledDocument();
			String texto = doc.getText(0, doc.getLength());

			Style normal = estilo("normal", colorTextoEditor(), false, false);
			Style keyword = estilo("keyword", colorKeywordEditor(), true, false);
			Style comentario = estilo("comentario", colorComentarioEditor(), false, true);
			Style cadena = estilo("cadena", colorCadenaEditor(), false, false);

			doc.setCharacterAttributes(0, texto.length(), normal, true);
			resaltarPalabras(doc, texto, new HashSet<String>(palabrasClave()), keyword);
			resaltarCadenas(doc, texto, cadena);
			resaltarComentarios(doc, texto, comentario);
		} catch (Exception ignored) {
		}
	}

	public Style estilo(String nombre, Color color, boolean negrita, boolean cursiva) {
		Style s = editor.addStyle(nombre, null);
		StyleConstants.setForeground(s, color);
		StyleConstants.setBold(s, negrita);
		StyleConstants.setItalic(s, cursiva);
		return s;
	}

	public void resaltarPalabras(StyledDocument doc, String texto, Set<String> palabras, Style estilo)
			throws BadLocationException {
		int i = 0;

		while (i < texto.length()) {
			char c = texto.charAt(i);

			if (Character.isLetter(c) || c == '_') {
				int ini = i;
				i++;

				while (i < texto.length()) {
					char x = texto.charAt(i);
					if (!Character.isLetterOrDigit(x) && x != '_' && x != '$') {
						break;
					}
					i++;
				}

				String palabra = texto.substring(ini, i);
				if (palabras.contains(palabra)) {
					doc.setCharacterAttributes(ini, palabra.length(), estilo, true);
				}
			} else {
				i++;
			}
		}
	}

	public void resaltarComentarios(StyledDocument doc, String texto, Style estilo) {
		int ini = 0;

		while (ini < texto.length()) {
			int p = texto.indexOf("//", ini);
			if (p < 0) {
				return;
			}

			int fin = texto.indexOf('\n', p);
			if (fin < 0) {
				fin = texto.length();
			}

			doc.setCharacterAttributes(p, fin - p, estilo, true);
			ini = fin + 1;
		}
	}

	public void resaltarCadenas(StyledDocument doc, String texto, Style estilo) {
		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);

			if (c == '\"' || c == '\'') {
				char quote = c;
				int ini = i;
				i++;

				while (i < texto.length()) {
					char x = texto.charAt(i);

					if (x == '\\') {
						i += 2;
						continue;
					}

					if (x == quote) {
						i++;
						break;
					}

					i++;
				}

				doc.setCharacterAttributes(ini, i - ini, estilo, true);
			}
		}
	}

	public List<String> palabrasClave() {
		if (proyectoActual == TipoProyectoScript.KUBEJS || proyectoActual == TipoProyectoScript.WORLDEDIT_CRAFTSCRIPT) {
			return Arrays.asList("const", "let", "var", "function", "return", "if", "else", "for", "while", "true",
					"false", "null", "undefined", "new", "class", "import", "from", "try", "catch", "throw");
		}

		if (proyectoActual == TipoProyectoScript.ZENSCRIPT) {
			return Arrays.asList("import", "val", "var", "function", "return", "if", "else", "for", "in", "as", "true",
					"false", "null", "static", "global");
		}

		if (proyectoActual == TipoProyectoScript.MINEFLAYER) {
			return Arrays.asList("def", "class", "import", "from", "return", "if", "else", "elif", "for", "while",
					"True", "False", "None", "try", "except");
		}

		if (proyectoActual == TipoProyectoScript.COMPUTERCRAFT_LUA) {
			return Arrays.asList("local", "function", "end", "if", "then", "else", "elseif", "for", "while", "do",
					"return", "true", "false", "nil");
		}

		if (proyectoActual == TipoProyectoScript.GROOVYSCRIPT) {
			return Arrays.asList("def", "class", "import", "return", "if", "else", "for", "while", "true", "false",
					"null", "new");
		}

		return Arrays.asList("true", "false", "null");
	}

	public List<DependenciaScriptIDE> dependenciasIDEFaltantes() {
		List<DependenciaScriptIDE> faltantes = new ArrayList<DependenciaScriptIDE>();
		List<File> jars = encontrarJarsDepsIDE();

		for (DependenciaScriptIDE dep : DEPENDENCIAS_IDE_REQUERIDAS) {
			boolean encontrada = false;

			for (File jar : jars) {
				if (dep.coincideConJar(jar) && jar.length() > 0L) {
					encontrada = true;
					break;
				}
			}

			if (!encontrada) {
				faltantes.add(dep);
			}
		}

		return faltantes;
	}

	public List<File> encontrarJarsDepsIDE() {
		List<File> ret = new ArrayList<File>();
		File[] archivos = carpetaDepsIDE.listFiles();

		if (archivos == null) {
			return ret;
		}

		for (File f : archivos) {
			if (f != null && f.isFile() && f.getName().toLowerCase().endsWith(".jar")) {
				ret.add(f);
			}
		}

		return ret;
	}

	public void descargarDependenciasIDE() {
		final List<DependenciaScriptIDE> faltantes = dependenciasIDEFaltantes();

		if (faltantes.isEmpty()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.ideScriptNoFaltanDependencias());
			reiniciarIntellisense();
			return;
		}

		StringBuilder lista = new StringBuilder();

		for (DependenciaScriptIDE dep : faltantes) {
			lista.append(" - ").append(dep.nombreVisible()).append("\n");
		}

		int confirmar = JOptionPane.showConfirmDialog(this,
				MonitorDePID.idioma.ideScriptConfirmarDescargaDeps(faltantes.size(), lista.toString()),
				MonitorDePID.idioma.ideScriptDescargarDeps(), JOptionPane.YES_NO_OPTION);

		if (confirmar != JOptionPane.YES_OPTION) {
			return;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				String error = "";
				DescargadorDependenciasMaven.ResultadoDescarga resultado = null;

				try {
					carpetaDepsIDE.mkdirs();

					List<DescargadorDependenciasMaven.CoordenadaMaven> coordenadas = new ArrayList<DescargadorDependenciasMaven.CoordenadaMaven>();

					for (DependenciaScriptIDE dep : faltantes) {
						if (dep.coordenadaValida()) {
							coordenadas.add(new DescargadorDependenciasMaven.CoordenadaMaven(dep.groupId,
									dep.artifactId, dep.version));
						}
					}

					resultado = DescargadorDependenciasMaven.descargarDependencias(coordenadas);
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					error = t.getMessage();
				}

				final String mensaje = resultado == null ? error : resultado.mensaje;

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(ScriptIDEGUI.this,
								MonitorDePID.idioma.ideScriptDepsDescargadas(mensaje));

						reiniciarIntellisense();
					}
				});
			}
		}).start();
	}

	public void actualizarEstado() {
		if (etiquetaEstado == null) {
			return;
		}

		String archivo = archivoActual == null ? MonitorDePID.idioma.ideScriptSinArchivo() : archivoActual.getName();
		String mod = textoModificado ? " *" : "";
		String proyecto = proyectoActual == null ? "" : proyectoActual.etiqueta();
		String motor = "";

		try {
			if (intellisense != null) {
				motor = intellisense.nombre();
			}
		} catch (Throwable ignored) {
		}

		if (motor == null || motor.trim().isEmpty()) {
			motor = "Local";
		}

		etiquetaEstado.setText(MonitorDePID.idioma.ideScriptEstado(proyecto, archivo + mod) + " [" + motor + "]");
	}

	public String uriDeArchivo(File archivo) {
		if (archivo == null) {
			return "";
		}

		return archivo.toURI().toString();
	}

	public String lenguajeLspActual() {
		if (proyectoActual == null) {
			return "plaintext";
		}

		if (proyectoActual == TipoProyectoScript.KUBEJS) {
			return "javascript";
		}

		if (proyectoActual == TipoProyectoScript.ZENSCRIPT) {
			return "zenscript";
		}

		if (proyectoActual == TipoProyectoScript.MINEFLAYER) {
			return "python";
		}

		if (proyectoActual == TipoProyectoScript.GROOVYSCRIPT) {
			return "groovy";
		}

		if (proyectoActual == TipoProyectoScript.COMPUTERCRAFT_LUA) {
			return "lua";
		}

		if (proyectoActual == TipoProyectoScript.WORLDEDIT_CRAFTSCRIPT) {
			return "javascript";
		}

		if (proyectoActual == TipoProyectoScript.DATAPACK_RESOURCEPACK
				|| proyectoActual == TipoProyectoScript.FEATURECREEP_DMR_JSON) {
			return "json";
		}

		return "plaintext";
	}

	public abstract Color colorTextoEditor();

	public abstract Color colorKeywordEditor();

	public abstract Color colorComentarioEditor();

	public abstract Color colorCadenaEditor();

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		return new ArrayList<ElementoConfig>();
	}

	@Override
	public void dispose() {
		cerrarIntellisense();
		super.dispose();
	}

	public static class RenderTipoProyecto extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected,
				boolean cellHasFocus) {
			JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, selected, cellHasFocus);

			if (value instanceof TipoProyectoScript) {
				TipoProyectoScript t = (TipoProyectoScript) value;
				String s = t.etiqueta();

				if (!t.habilitadoAhora) {
					s += " " + MonitorDePID.idioma.ideScriptDeshabilitadoCorto();
					l.setEnabled(false);
				} else {
					l.setEnabled(true);
				}

				l.setText(s);
			}

			return l;
		}
	}

	public static class RenderCompletado extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected,
				boolean cellHasFocus) {
			JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, selected, cellHasFocus);

			if (value != null) {
				l.setText(String.valueOf(value));
			}

			return l;
		}
	}
}