package com.asbestosstar.crashdetector.gui.tipos.antimanipulacion;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class AntiManipulacionGUI extends JDialog implements CrashDetectorGUI {

	public static final long serialVersionUID = 1L;

	public static final Map<String, Supplier<AntiManipulacionGUI>> GUIS = new HashMap<String, Supplier<AntiManipulacionGUI>>();

	public static final Path ARCHIVO = Statics.carpeta.resolve("antimanipulacion.json");

	public final Path baseDir = Paths.get(System.getProperty("user.dir")).toAbsolutePath();

	public final Set<String> rutasProtegidas = new LinkedHashSet<String>();

	public JTable tabla;
	public DefaultTableModel modelo;

	protected void inicializarModeloTabla() {
		modelo = new DefaultTableModel(new Object[] { MonitorDePID.idioma.rutaArchivo() }, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		tabla = new JTable(modelo);
		tabla.setRowHeight(24);
	}

	protected void seleccionar(boolean carpeta) {
		JFileChooser fc = new JFileChooser(baseDir.toFile());
		fc.setFileSelectionMode(carpeta ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY);

		if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		Path seleccionado = fc.getSelectedFile().toPath().toAbsolutePath();

		if (!seleccionado.startsWith(baseDir)) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.errorRutaFueraDirectorio(),
					MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			if (carpeta) {
				Files.walk(seleccionado).filter(Files::isRegularFile)
						.forEach(p -> rutasProtegidas.add(baseDir.relativize(p).toString()));
			} else {
				rutasProtegidas.add(baseDir.relativize(seleccionado).toString());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		recargarTabla();
	}

	protected void quitarSeleccionado() {
		int fila = tabla.getSelectedRow();
		if (fila < 0)
			return;

		Object val = modelo.getValueAt(fila, 0);
		if (val != null) {
			rutasProtegidas.remove(val.toString());
		}

		recargarTabla();
	}

	protected void recargarTabla() {
		modelo.setRowCount(0);
		for (String r : rutasProtegidas) {
			modelo.addRow(new Object[] { r });
		}
	}

	protected void cargarDatos() {
		rutasProtegidas.clear();

		if (!Files.exists(ARCHIVO))
			return;

		try {
			String json = new String(Files.readAllBytes(ARCHIVO), java.nio.charset.StandardCharsets.UTF_8);

			if (json.trim().isEmpty())
				return;

			Nodo raiz = Json.leer(json);
			if (!raiz.esArreglo())
				return;

			for (int i = 0; i < raiz.tamano(); i++) {
				Nodo n = raiz.en(i);
				if (n == null || !n.esObjeto())
					continue;

				Nodo r = n.obtener("ruta");
				if (r != null && !r.esObjeto() && !r.esArreglo()) {
					String v = r.comoCadena();
					if (v != null && !v.isEmpty()) {
						rutasProtegidas.add(v);
					}
				}
			}
		} catch (Exception ignored) {
		}
	}

	protected void guardarDatos() {
		try {
			Json.Nodo raiz = Json.leer("[]");

			for (String r : rutasProtegidas) {
				Json.Nodo obj = Json.crearObjeto();
				obj.obtener("ruta").poner(r);
				raiz.agregar(obj);
			}

			Files.write(ARCHIVO, raiz.aBytesUtf8());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.ANTI_MANIPULACION;
	}
}
