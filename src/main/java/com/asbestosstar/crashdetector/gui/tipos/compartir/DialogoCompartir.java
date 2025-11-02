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
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.asbestosstar.crashdetector.Config;
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

/**
 * Clase abstracta que define la funcionalidad base para el diálogo de
 * compartir. La apariencia y el layout específicos se manejarán en
 * implementaciones concretas.
 */
public abstract class DialogoCompartir extends JFrame implements CrashDetectorGUI {

	// Componentes de la interfaz (ahora públicos)
	public DefaultTableModel modeloTabla;
	public JTextField campoEndpoint;
	public JComboBox<String> comboAPI;
	public JComboBox<String> comboSitioRegistro;
	public JCheckBox checkAnonimizar;
	public Instant instant;
	public JTextField campoEnlaceReporte;
	public JButton botonCompartirTodos;
	public JButton botonCompartirMarkdown;

	// Variables internas para la lógica
	protected JTextArea textoExplicacion;
	protected JPanel panelPrincipal;
	protected JPanel panelSuperior;
	protected JPanel panelControles;
	protected JTable tabla;
	protected JPanel panelConfig;

	/**
	 * Prepara y muestra el diálogo con la información del instante proporcionado.
	 * Este método ahora es abstracto para forzar a las implementaciones concretas a
	 * definir el layout y la apariencia.
	 *
	 * @param instant El instante de tiempo para el cual se prepara el informe.
	 */
	public abstract void preperar(Instant instant);

	// --- Funcionalidad Lógica ---
	protected void setEnviando(boolean enviando) {
		try {
			if (botonCompartirTodos != null) {
				botonCompartirTodos.setEnabled(!enviando);
			}
			setCursor(enviando ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
		} catch (Throwable ignored) {
			// Evitar fallos AWT por estados extraños
		}
	}

	protected void mostrarError(String mensaje, Throwable t) {
		CrashDetectorLogger.logException(t);
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(this, mensaje
					+ ((t != null && t.getMessage() != null && !t.getMessage().isEmpty()) ? "\n" + t.getMessage() : ""),
					"Error", JOptionPane.ERROR_MESSAGE);
		});
	}

	protected void mostrarInfo(String mensaje) {
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	protected static void copiarAlPortapapeles(String texto) {
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

	protected void cargarConsolas() {
		if (modeloTabla != null) {
			for (Consola consola : MonitorDePID.consolas) {
				modeloTabla.addRow(new Object[] { true, consola.archivo.getFileName().toString(),
						MonitorDePID.idioma.abrir(), MonitorDePID.idioma.texto_de_buton_compartir_enlace(), "" });
			}
		}
	}

public void compartirSeleccionados(ActionEvent e)
        throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro  {
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

        // Actualizar URLs individuales (todas las partes por fila, separadas por espacio)
        if (modeloTabla != null) {
            for (int j = 0; j < filasSel.size(); j++) {
                int row = filasSel.get(j);
                Consola cons = MonitorDePID.consolas.get(row);
                java.util.List<String> urls = new ArrayList<String>();
                		try {
							urls = cons.obtainerEnlaces();
						} catch (DemasiadoGrande | ErrorConPublicar | NoAPIdeRegistro | LimteDeTasa e1) {
							// TODO Auto-generated catch block
				            mostrarError(e1.getMessage(), e1);
				            return;
						}
                String concatenadas = String.join(" ", urls);
                modeloTabla.setValueAt(concatenadas, row, 4);
            }
        }

        // Intentar abrir en navegador; si falla, copiar al portapapeles
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


	// Actualiza el combo de sitios para la API seleccionada
	protected void actualizarComboSitios(String apiNombre, Set<String> sitios, String sitioSeleccionado) {
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
			Config.obtenerInstancia().guardarAnonimizarRegistros(anonimizar);
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
		// TODO ¡NO USAR, usar preperar!
	}

	public APIdeSitioDeRegistro obtenerAPI() throws NoAPIdeRegistro {
		try {
			return APIdeSitioDeRegistro.obtenerAPIdeConfig();
		} catch (NoAPIdeRegistro e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new NoAPIdeRegistro();
		}

	}

	/**
	 * Genera Markdown con enlaces directos a los logs seleccionados. AHORA soporta
	 * múltiples enlaces por cada consola (cuando la API parte el archivo).
	 *
	 * - Mantiene la tabla (columna URL) sincronizada con TODAS las partes (una por
	 * línea). - Copia el Markdown al portapapeles y lo coloca en el campo de
	 * enlace. - Orden de archivos: latest.log, debug.log, launcher.log, luego el
	 * resto alfabético. - Si un log se partió en varias URLs, se etiqueta como:
	 * nombre (parte N).
	 *
	 * @throws DemasiadoGrande  si la API reporta que una parte sigue siendo grande
	 * @throws ErrorConPublicar si ocurre un error específico de publicación
	 * @throws NoAPIdeRegistro  si no hay API seleccionada/configurada
	 */
	public void compartirSoloEnlacesMarkdown(ActionEvent e) throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro {

		// 1) Recolectar filas y consolas seleccionadas
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
			return; // nada que hacer
		}

		// Para construcción del markdown y del contenido de celda
		class Item {
			String archivoBase; // nombre base del archivo (p.ej., latest.log)
			String etiqueta; // etiqueta a mostrar en markdown (p.ej., "latest.log (parte 2)")
			String url; // enlace de la parte
			int parteIndex; // 1..N (solo informativo para orden estable dentro del mismo archivo)

			Item(String archivoBase, String etiqueta, String url, int parteIndex) {
				this.archivoBase = archivoBase;
				this.etiqueta = etiqueta;
				this.url = url;
				this.parteIndex = parteIndex;
			}
		}
		final List<Item> items = new ArrayList<>();

		// 2) Obtener/crear enlaces por consola y actualizar la tabla
		for (int idx = 0; idx < filasSel.size(); idx++) {
			final int row = filasSel.get(idx);
			final Consola cons = MonitorDePID.consolas.get(row);

			final String nombreArchivo = (cons.archivo != null) ? cons.archivo.getFileName().toString() : "log.txt";

			List<String> urls;
			try {
				// NUEVO: obtener TODAS las partes que la API haya publicado
				urls = cons.obtainerEnlaces();
			} catch (Throwable t) {
				// Propaga las excepciones esperadas; cualquier otra la registramos y seguimos
				// vacío
				if (t instanceof DemasiadoGrande)
					throw (DemasiadoGrande) t;
				if (t instanceof ErrorConPublicar)
					throw (ErrorConPublicar) t;
				if (t instanceof NoAPIdeRegistro)
					throw (NoAPIdeRegistro) t;
				CrashDetectorLogger.logException(t);
				urls = new ArrayList<>();
			}

			// Actualizar columna URL (columna 4) con TODAS las partes separadas por saltos
			// de línea
			final String celda = (urls == null || urls.isEmpty()) ? "" : String.join("\n", urls);
			if (modeloTabla != null) {
				modeloTabla.setValueAt(celda, row, 4);
			}

			// Preparar ítems para el markdown: si hay varias partes, etiquetar con " (parte
			// N)"
			if (urls != null && !urls.isEmpty()) {
				if (urls.size() == 1) {
					items.add(new Item(nombreArchivo, nombreArchivo, urls.get(0), 1));
				} else {
					for (int p = 0; p < urls.size(); p++) {
						final int parteN = p + 1;
						final String etiqueta = nombreArchivo + " (parte " + parteN + ")";
						items.add(new Item(nombreArchivo, etiqueta, urls.get(p), parteN));
					}
				}
			} else {
				// Sin URL: aún listamos el nombre para dejar constancia en el markdown si se
				// desea
				items.add(new Item(nombreArchivo, nombreArchivo, "", 1));
			}
		}

		// 3) Ordenar: latest.log, debug.log, launcher.log, luego resto por nombre
		// (estable por parteIndex)
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
			// A igualdad de archivo, mantener el orden por parte (1,2,3...)
			return Integer.compare(a.parteIndex, b.parteIndex);
		});

		// 4) Construir el Markdown (una línea larga separada por espacios)
		StringBuilder md = new StringBuilder();
		for (Item it : items) {
			if (it.url != null && !it.url.isEmpty()) {
				md.append('[').append(it.etiqueta).append(']').append('(').append(it.url).append(')').append(' ');
			} else {
				md.append(it.etiqueta).append(' ');
			}
		}
		final String markdown = md.toString().trim();

		// 5) Mostrar en campo, copiar al portapapeles e informar
		if (campoEnlaceReporte != null) {
			campoEnlaceReporte.setText(markdown);
		}
		copiarAlPortapapeles(markdown);
		mostrarInfo(MonitorDePID.idioma.copiadoAlPortapapeles());
	}

}