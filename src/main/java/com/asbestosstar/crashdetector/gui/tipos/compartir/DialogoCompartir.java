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
import javax.swing.JDialog;
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
import com.asbestosstar.crashdetector.api_sito_registro.NoAPIdeRegistro;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Clase abstracta que define la funcionalidad base para el diálogo de
 * compartir. La apariencia y el layout específicos se manejarán en
 * implementaciones concretas.
 */
public abstract class DialogoCompartir extends JDialog implements CrashDetectorGUI {

	// Componentes de la interfaz (ahora públicos)
	public DefaultTableModel modeloTabla;
	public JTextField campoEndpoint;
	public JComboBox<String> comboAPI;
	public JComboBox<String> comboSitioRegistro;
	public JCheckBox checkAnonimizar;
	public Instant instant;
	public JTextField campoEnlaceReporte;
	public JButton botonCompartirTodos;

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

	protected void compartirSeleccionados(ActionEvent e) throws DemasiadoGrande, ErrorConPublicar, NoAPIdeRegistro {
		ArrayList<Consola> seleccionados = new ArrayList<>();
		if (modeloTabla != null) {
			for (int i = 0; i < modeloTabla.getRowCount(); i++) {
				if (Boolean.TRUE.equals(modeloTabla.getValueAt(i, 0))) {
					seleccionados.add(MonitorDePID.consolas.get(i));
				}
			}
		}

		if (!seleccionados.isEmpty()) {
			String enlace = GeneradorDeInformacion.compartir(seleccionados, instant);
			if (campoEnlaceReporte != null) {
				campoEnlaceReporte.setText(enlace);
			}
			MonitorDePID.enlace = enlace;

			// Actualizar URLs individuales
			if (modeloTabla != null) {
				for (int i = 0; i < modeloTabla.getRowCount(); i++) {
					if (Boolean.TRUE.equals(modeloTabla.getValueAt(i, 0))) {
						Consola cons = MonitorDePID.consolas.get(i);
						String url = cons.obtainerEnlance();
						modeloTabla.setValueAt(url, i, 4);
					}
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

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		ArrayList<ElementoConfig> ret = new ArrayList<>();
		// ConfigColor ahora acepta Color como parámetro predeterminado
		ret.add(ConfigColor.de("dialogo_compartir_enlace", java.awt.Color.BLUE.darker()));
		return ret;
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

}