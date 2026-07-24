package com.asbestosstar.crashdetector.gui.tipos.compartir;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.GeneradorDeInformacion;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.api_sito_registro.APIdeSitioDeRegistro;
import com.asbestosstar.crashdetector.api_sito_registro.DemasiadoGrande;
import com.asbestosstar.crashdetector.api_sito_registro.ErrorConPublicar;
import com.asbestosstar.crashdetector.api_sito_registro.LimteDeTasa;
import com.asbestosstar.crashdetector.api_sito_registro.NoAPIdeRegistro;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.compartir_instancia.CompartirInstanciaIkeEveland;

/**
 * Clase abstracta que define la funcionalidad base para el diálogo de
 * compartir. La apariencia y el layout específicos se manejarán en
 * implementaciones concretas.
 */
public abstract class DialogoCompartir extends JFrame implements CrashDetectorGUI {

	// Componentes de la interfaz
	public DefaultTableModel modeloTabla;
	public JTextField campoEndpoint;
	public JComboBox<String> comboAPI;
	public JComboBox<String> comboSitioRegistro;
	public JCheckBox checkAnonimizar;
	public Instant instant;
	public JTextField campoEnlaceReporte;
	public JButton botonCompartirTodos;
	public JButton botonCompartirMarkdown;
	public JButton botonCompartirInstanciaOModpack;
	public JButton botonEliminarRegistros;

	// Variables internas para la lógica
	public JEditorPane textoExplicacion;
	public JPanel panelPrincipal;
	public JPanel panelSuperior;
	public JPanel panelControles;
	public JTable tabla;
	public JPanel panelConfig;

	/**
	 * Prepara y muestra el diálogo con la información del instante proporcionado.
	 *
	 * @param instant El instante de tiempo para el cual se prepara el informe.
	 */
	public abstract void preperar(Instant instant);

	/**
	 * Devuelve true si el sistema operativo actual es macOS.
	 */
	public boolean esMacOS() {
		String nombreSO = System.getProperty("os.name");
		return nombreSO != null && nombreSO.toLowerCase().contains("mac");
	}

	public void setEnviando(boolean enviando) {
		try {
			if (botonCompartirTodos != null) {
				botonCompartirTodos.setEnabled(!enviando);
			}
			if (botonCompartirMarkdown != null) {
				botonCompartirMarkdown.setEnabled(!enviando);
			}
			if (botonCompartirInstanciaOModpack != null) {
				botonCompartirInstanciaOModpack.setEnabled(!enviando);
			}
			setCursor(enviando ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
		} catch (Throwable ignored) {
			// Evitar fallos AWT por estados extraños
		}
	}

	public void mostrarError(String mensaje, Throwable t) {
		CrashDetectorLogger.logException(t);
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(this, mensaje
					+ ((t != null && t.getMessage() != null && !t.getMessage().isEmpty()) ? "\n" + t.getMessage() : ""),
					"Error", JOptionPane.ERROR_MESSAGE);
		});
	}

	public void mostrarInfo(String mensaje) {
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	public static void copiarAlPortapapeles(String texto) {
		if (texto == null)
			return;
		try {
			StringSelection selection = new StringSelection(texto);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, null);
		} catch (Throwable t) {
			// Silencioso: no bloquear la UI si no hay portapapeles
		}
	}

	public void cargarConsolas() {
		if (modeloTabla != null) {
			for (Consola consola : MonitorDePID.consolas) {
				modeloTabla.addRow(new Object[] { true, consola.archivo.getFileName().toString(),
						MonitorDePID.idioma.abrir(), MonitorDePID.idioma.texto_de_buton_compartir_enlace(), "" });
			}
		}
	}

	public void compartirSeleccionados(ActionEvent e) throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro {
		ArrayList<Consola> seleccionados = new ArrayList<>();
		ArrayList<Integer> filasSel = new ArrayList<>();
		if (modeloTabla != null) {
			for (int i = 0; i < modeloTabla.getRowCount(); i++) {
				if (Boolean.TRUE.equals(modeloTabla.getValueAt(i, 0))) {
					seleccionados.add(MonitorDePID.consolas.get(i));
					filasSel.add(i);
				}
			}
		}

		if (!seleccionados.isEmpty()) {
			String enlace;
			try {
				enlace = GeneradorDeInformacion.compartir(seleccionados, instant);
			} catch (LimteDeTasa rl) {
				mostrarError(MonitorDePID.idioma.limite_de_solicitudes(), rl);
				return;
			}

			if (campoEnlaceReporte != null) {
				campoEnlaceReporte.setText(enlace);
			}
			MonitorDePID.enlace = enlace;

			if (modeloTabla != null) {
				for (int j = 0; j < filasSel.size(); j++) {
					int row = filasSel.get(j);
					Consola cons = MonitorDePID.consolas.get(row);
					java.util.List<String> urls = new ArrayList<String>();
					try {
						urls = cons.obtainerEnlaces();
					} catch (DemasiadoGrande | ErrorConPublicar | NoAPIdeRegistro | LimteDeTasa e1) {
						mostrarError(e1.getMessage(), e1);
						return;
					}
					String concatenadas = String.join(" ", urls);
					modeloTabla.setValueAt(concatenadas, row, 4);
				}
			}

			try {
				if (enlace != null && !enlace.isEmpty() && Desktop.isDesktopSupported()) {
					Desktop.getDesktop().browse(new URL(enlace).toURI());
				} else if (enlace != null) {
					copiarAlPortapapeles(enlace);
					mostrarInfo(MonitorDePID.idioma.copiadoAlPortapapeles());
				}
			} catch (Exception ex) {
				copiarAlPortapapeles(enlace);
				mostrarInfo(MonitorDePID.idioma.copiadoAlPortapapeles());
				CrashDetectorLogger.logException(ex);
			}
		}
	}

	/**
	 * Acción provisional para compartir la instancia o modpack. Por ahora solo
	 * muestra un popup informativo.
	 */
//	public void compartirInstanciaOModpack(ActionEvent e) {
//		mostrarInfo(MonitorDePID.idioma.popup_compartir_instancia_modpack());
//	}

	public void compartirInstanciaOModpack(ActionEvent e) {
		TipoGUI.COMPARTIR_INSTANCIA
				.obtenerGUIPredeterminado(CompartirInstanciaIkeEveland.ID, CompartirInstanciaIkeEveland::new).init();
	}

	public void actualizarComboSitios(String apiNombre, Set<String> sitios, String sitioSeleccionado) {
		if (comboSitioRegistro != null) {
			comboSitioRegistro.removeAllItems();
			if (sitios != null) {
				for (String sitio : sitios) {
					comboSitioRegistro.addItem(sitio);
				}
				if (sitioSeleccionado != null && sitios.contains(sitioSeleccionado)) {
					comboSitioRegistro.setSelectedItem(sitioSeleccionado);
				} else if (comboSitioRegistro.getItemCount() > 0) {
					comboSitioRegistro.setSelectedIndex(0);
				}
			}
		}
	}

	public void guardarConfig() {
		if (campoEndpoint != null) {
			Config.obtenerInstancia().guardarSitioDeInformes(campoEndpoint.getText());
		}
		if (comboAPI != null) {
			String api = (String) comboAPI.getSelectedItem();
			Config.obtenerInstancia().guardarApiSeleccionada(api);
		}
		if (comboSitioRegistro != null) {
			String sitio_registro = (String) comboSitioRegistro.getSelectedItem();
			Config.obtenerInstancia().guardarSitioRegistrosSeleccionado(sitio_registro);
		}
		if (checkAnonimizar != null) {
			boolean anonimizar = checkAnonimizar.isSelected();
			ConfigMundial.obtenerInstancia().guardarAnonimizarRegistros(anonimizar);
		}
		CrashDetectorLogger.log("Configuration saved.");
	}

	public static Map<String, Supplier<DialogoCompartir>> GUIS = new HashMap<String, Supplier<DialogoCompartir>>();

	@Override
	public TipoGUI<DialogoCompartir> tipo() {
		return TipoGUI.DIALOGO_COMPARTIR;
	}

	@Override
	public abstract void recargarApariencia();

	@Override
	public void init() {
		// NO USAR, usar preperar
	}

	public APIdeSitioDeRegistro obtenerAPI() throws NoAPIdeRegistro {
		try {
			return APIdeSitioDeRegistro.obtenerAPIdeConfig();
		} catch (NoAPIdeRegistro e) {
			throw new NoAPIdeRegistro();
		}
	}

	public void compartirSoloEnlacesMarkdown(ActionEvent e) throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro {
		final List<Integer> filasSel = new ArrayList<>();
		final ArrayList<Consola> seleccionados = new ArrayList<>();
		if (modeloTabla != null) {
			for (int i = 0; i < modeloTabla.getRowCount(); i++) {
				if (Boolean.TRUE.equals(modeloTabla.getValueAt(i, 0))) {
					filasSel.add(i);
					seleccionados.add(MonitorDePID.consolas.get(i));
				}
			}
		}
		if (seleccionados.isEmpty()) {
			return;
		}

		final class Res {
			final int rowIndex;
			final String nombreArchivo;
			final List<String> urls;

			Res(int rowIndex, String nombreArchivo, List<String> urls) {
				this.rowIndex = rowIndex;
				this.nombreArchivo = nombreArchivo;
				this.urls = urls;
			}
		}

		final int MAX_HILOS = 6;
		ExecutorService pool = Executors.newFixedThreadPool(Math.min(MAX_HILOS, Math.max(1, filasSel.size())));
		List<Future<Res>> tareas = new ArrayList<>(filasSel.size());

		for (int k = 0; k < filasSel.size(); k++) {
			final int row = filasSel.get(k);
			final Consola cons = MonitorDePID.consolas.get(row);
			tareas.add(pool.submit(new Callable<Res>() {
				@Override
				public Res call() throws Exception {
					List<String> urls;
					try {
						urls = cons.obtainerEnlaces();
					} catch (Throwable t) {
						if (t instanceof DemasiadoGrande)
							throw (DemasiadoGrande) t;
						if (t instanceof ErrorConPublicar)
							throw (ErrorConPublicar) t;
						if (t instanceof NoAPIdeRegistro)
							throw (NoAPIdeRegistro) t;
						CrashDetectorLogger.logException(t);
						urls = new ArrayList<>();
					}
					String nombreArchivo = (cons.archivo != null) ? cons.archivo.getFileName().toString() : "log.txt";
					return new Res(row, nombreArchivo, urls);
				}
			}));
		}
		pool.shutdown();

		final class Item {
			String archivoBase;
			String etiqueta;
			String url;
			int parteIndex;

			Item(String archivoBase, String etiqueta, String url, int parteIndex) {
				this.archivoBase = archivoBase;
				this.etiqueta = etiqueta;
				this.url = url;
				this.parteIndex = parteIndex;
			}
		}
		final List<Item> items = new ArrayList<>();

		try {
			for (Future<Res> f : tareas) {
				Res r = f.get();
				String celda = (r.urls == null || r.urls.isEmpty()) ? "" : String.join("\n", r.urls);
				if (modeloTabla != null) {
					modeloTabla.setValueAt(celda, r.rowIndex, 4);
				}
				if (r.urls != null && !r.urls.isEmpty()) {
					if (r.urls.size() == 1) {
						items.add(new Item(r.nombreArchivo, r.nombreArchivo, r.urls.get(0), 1));
					} else {
						for (int i = 0; i < r.urls.size(); i++) {
							int parteN = i + 1;
							String etiqueta = r.nombreArchivo + " (parte " + parteN + ")";
							items.add(new Item(r.nombreArchivo, etiqueta, r.urls.get(i), parteN));
						}
					}
				} else {
					items.add(new Item(r.nombreArchivo, r.nombreArchivo, "", 1));
				}
			}
		} catch (ExecutionException ex) {
			Throwable causa = ex.getCause();
			if (causa instanceof DemasiadoGrande)
				throw (DemasiadoGrande) causa;
			if (causa instanceof ErrorConPublicar)
				throw (ErrorConPublicar) causa;
			if (causa instanceof NoAPIdeRegistro)
				throw (NoAPIdeRegistro) causa;
			CrashDetectorLogger.logException(causa);
			mostrarError(MonitorDePID.idioma.error_inesperado_al_generar_enlaces(), causa);
			return;
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			mostrarError(MonitorDePID.idioma.error_inesperado_al_generar_enlaces(), ie);
			return;
		}

		final java.util.function.Function<String, Integer> peso = nombre -> {
			String n = (nombre == null ? "" : nombre.toLowerCase());
			if (n.equals("latest.log"))
				return 0;
			if (n.equals("debug.log"))
				return 1;
			if (n.equals("launcher.log"))
				return 2;
			return 3;
		};
		items.sort((a, b) -> {
			int pa = peso.apply(a.archivoBase);
			int pb = peso.apply(b.archivoBase);
			if (pa != pb)
				return Integer.compare(pa, pb);
			int cmp = a.archivoBase.compareToIgnoreCase(b.archivoBase);
			if (cmp != 0)
				return cmp;
			return Integer.compare(a.parteIndex, b.parteIndex);
		});

		StringBuilder md = new StringBuilder();
		for (Item it : items) {
			if (it.url != null && !it.url.isEmpty()) {
				md.append('[').append(it.etiqueta).append(']').append('(').append(it.url).append(')').append(' ');
			} else {
				md.append(it.etiqueta).append(' ');
			}
		}
		final String markdown = md.toString().trim();

		if (campoEnlaceReporte != null) {
			campoEnlaceReporte.setText(markdown);
		}
		copiarAlPortapapeles(markdown);
		mostrarInfo(MonitorDePID.idioma.copiadoAlPortapapeles());
	}

	/**
	 * Busca la API actualmente seleccionada en el combo.
	 */
	public APIdeSitioDeRegistro obtenerApiSeleccionadaEnCombo() {
		if (comboAPI == null)
			return null;

		String nombreApi = (String) comboAPI.getSelectedItem();
		if (nombreApi == null)
			return null;

		for (APIdeSitioDeRegistro api : APIdeSitioDeRegistro.APIS) {
			if (api != null && nombreApi.equals(api.nombre())) {
				return api;
			}
		}

		return null;
	}

	/**
	 * Actualiza el botón opcional para abrir el eliminador de registros.
	 */
	public void actualizarBotonEliminador() {
		if (botonEliminarRegistros == null)
			return;

		APIdeSitioDeRegistro api = obtenerApiSeleccionadaEnCombo();

		boolean visible = api != null && api.eliminador() != null;
		botonEliminarRegistros.setVisible(visible);
		botonEliminarRegistros.setEnabled(visible);

		if (panelConfig != null) {
			panelConfig.revalidate();
			panelConfig.repaint();
		}
	}

	/**
	 * Abre la GUI eliminadora de registros de la API seleccionada.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void abrirEliminadorDeApiSeleccionada() {
		try {
			APIdeSitioDeRegistro api = obtenerApiSeleccionadaEnCombo();

			if (api == null || api.eliminador() == null)
				return;

			TipoGUI tipo = api.eliminador().get();
			if (tipo == null)
				return;

			Map guis = tipo.obtenerGUIs();
			if (guis == null || guis.isEmpty())
				return;

			Object primerValor = guis.values().iterator().next();
			if (!(primerValor instanceof Supplier))
				return;

			Supplier proveedor = (Supplier) primerValor;
			Object gui = proveedor.get();

			if (gui instanceof CrashDetectorGUI) {
				((CrashDetectorGUI) gui).init();
			}
		} catch (Throwable t) {
			mostrarError(MonitorDePID.idioma.error_inesperado_al_procesar_boton(), t);
		}
	}

}