package com.asbestosstar.crashdetector.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.firmas.CargadorDeCodice;
import com.asbestosstar.crashdetector.analizador.firmas.FiltrodeCodice;
import com.asbestosstar.crashdetector.analizador.firmas.v0.FirmasV0;
import com.asbestosstar.crashdetector.analizador.firmas.v0.VerificacionFirmasV0;

/**
 * Editor de codice.json (schema 0) SIN pestañas. El usuario define TODOS los
 * idiomas de cada verificación en una sola vista. - Izquierda: lista de
 * verificaciones. - Centro: formulario completo (campos generales + tabla de
 * idiomas). - Derecha: vista previa JSON. - Usa MonitorDePID.idioma PARA TODAS
 * LAS ETIQUETAS (debes implementar esos métodos en Idioma). - Crea el archivo
 * si no existe. - Fuerza a completar todos los campos antes de
 * guardar/actualizar.
 *
 * Compatibilidad: Java 8.
 */
public class EditorCodiceGUIIronMouse extends JFrame implements BotonDeBarraLateralDerecha {

	// Paleta inspirada en la estética de ironmouse (rosas/morados)
	private final Color rosaFondo = new Color(250, 230, 245);
	private final Color rosaSuave = new Color(255, 204, 235);
	private final Color moradoAcento = new Color(186, 85, 211);
	private final Color textoOscuro = new Color(40, 35, 45);
	private final Color bordeSuave = new Color(220, 180, 210);

	// Modelo / lista
	private final DefaultListModel<VerificacionFirmasV0> modeloLista = new DefaultListModel<VerificacionFirmasV0>();
	private JList<VerificacionFirmasV0> lista;

	// Vista previa
	private JTextArea vistaJson;

	// Campos generales
	private JTextField fId, fParaBuscar;
	private JComboBox<String> cbFiltro, cbCriticalidad; // Cambiado a JComboBox para dropdowns
	private JSpinner spPrioridad;
	private JButton btnElegirFiltro; // No necesario ahora, pero lo dejamos por compatibilidad

	// Campos por idioma (nombre, resultado) → clave -> {nombre, resultado}
	private final Map<String, JTextField[]> camposIdiomas = new LinkedHashMap<String, JTextField[]>();

	// Iconos (banderas + ironmouse)
	private final Map<String, ImageIcon> iconos = new LinkedHashMap<String, ImageIcon>();
	private JLabel etiquetaIronmouse;

	// Botones
	private JButton btnNuevo, btnActualizar, btnEliminar, btnExportar, btnGuardar;

	// Archivo
	private final Path rutaCodice = MonitorDePID.carpeta.resolve("firmas.json");

	private VerificacionFirmasV0 verificacionCargadaEnFormulario = null;

	public EditorCodiceGUIIronMouse() {
		setTitle(MonitorDePID.idioma.tituloEditorCodice());

		// In your EditorCodiceGUI() constructor, after setDefaultCloseOperation:
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (hayCambiosNoGuardados()) {
					int resp = JOptionPane.showConfirmDialog(EditorCodiceGUIIronMouse.this,
							MonitorDePID.idioma.guardarAntesDeSalir(), MonitorDePID.idioma.salirSinGuardar(),
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (resp == JOptionPane.YES_OPTION) {
						guardarTodo();
						dispose();
					} else if (resp == JOptionPane.NO_OPTION) {
						dispose();
					}
				} else {
					dispose();
				}
			}
		});

		setSize(1120, 740);
		setLocationRelativeTo(null);
		getContentPane().setBackground(rosaFondo);
		setLayout(new BorderLayout(10, 10));

		cargarIconos();

		add(crearEncabezado(), BorderLayout.NORTH);
		add(crearSplit(), BorderLayout.CENTER);

		asegurarArchivo();
		recargarDesdeDisco();
		actualizarVistaJson();
	}

	private JPanel crearEncabezado() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS)); // Vertical stack
		p.setBackground(rosaFondo);
		p.setBorder(BorderFactory.createEmptyBorder(8, 12, 0, 12));

		// --- Título ---
		JLabel titulo = new JLabel(MonitorDePID.idioma.tituloEditorCodice());
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
		titulo.setForeground(moradoAcento);
		p.add(titulo);

		// --- Contenedor de descripción + imagen (horizontal) ---
		JPanel descRow = new JPanel();
		descRow.setLayout(new BoxLayout(descRow, BoxLayout.X_AXIS));
		descRow.setOpaque(false);
		descRow.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Descripción (left side)
		String descText = "<html><body style='width: 600px;'>" + MonitorDePID.idioma.descripcionEditorCodice()
				+ "</body></html>";
		JLabel descripcion = new JLabel(descText);
		descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		descripcion.setForeground(textoOscuro);
		descripcion.setAlignmentY(Component.CENTER_ALIGNMENT);
		descRow.add(descripcion);
		descRow.add(Box.createHorizontalStrut(20)); // Space between text and image

		// Imagen (right side, vertically centered)
		etiquetaIronmouse = cargarImagen("imagenes/ironmouse.png", 240, 130);
		if (etiquetaIronmouse != null) {
			JPanel imgPanel = new JPanel(new BorderLayout());
			imgPanel.setOpaque(false);
			imgPanel.add(etiquetaIronmouse, BorderLayout.CENTER);
			imgPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
			descRow.add(imgPanel);
		}

		p.add(descRow);

		return p;
	}

	private JSplitPane crearSplit() {
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, crearPanelIzquierdoCentro(), // lista +
																									// formulario
				crearPanelDerecho()); // vista previa JSON
		split.setDividerLocation(720);
		split.setResizeWeight(0.72);
		split.setBorder(BorderFactory.createEmptyBorder());
		return split;
	}

	private Component crearPanelIzquierdoCentro() {
		JPanel cont = new JPanel(new BorderLayout(10, 10));
		cont.setBackground(rosaFondo);
		cont.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 6));

		// Lista de verificaciones
		lista = new JList<VerificacionFirmasV0>(modeloLista);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel l = new JLabel(value.id + " · " + nz(value.nombre_es));
			l.setOpaque(true);
			l.setForeground(isSelected ? Color.WHITE : textoOscuro);
			l.setBackground(isSelected ? moradoAcento : Color.WHITE);
			l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			l.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
			return l;
		});
		lista.addListSelectionListener(e -> {
			if (e.getValueIsAdjusting())
				return;

			VerificacionFirmasV0 nuevaSeleccion = lista.getSelectedValue();

			// Si estamos dejando una verificación modificada
			if (verificacionCargadaEnFormulario != null && esFormularioModificado()) {
				int respuesta = JOptionPane.showConfirmDialog(EditorCodiceGUIIronMouse.this,
						MonitorDePID.idioma.descartarCambios(), MonitorDePID.idioma.confirmacion(),
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (respuesta == JOptionPane.YES_OPTION) {
					// Aceptar: cargar la nueva selección (o limpiar si es null)
					if (nuevaSeleccion != null) {
						cargarEnFormulario(nuevaSeleccion);
					} else {
						verificacionCargadaEnFormulario = null;
					}
				} else {
					// Cancelar: restaurar la selección anterior
					lista.setSelectedValue(verificacionCargadaEnFormulario, true);
				}
			} else {
				// No hay cambios pendientes: cargar directamente
				if (nuevaSeleccion != null) {
					cargarEnFormulario(nuevaSeleccion);
				} else {
					verificacionCargadaEnFormulario = null;
				}
			}
		});
		JScrollPane spLista = new JScrollPane(lista);
		spLista.setPreferredSize(new Dimension(260, 520));
		spLista.setBorder(titulo("lista", MonitorDePID.idioma.lista()));
		cont.add(spLista, BorderLayout.WEST);

		// Formulario completo (general + idiomas)
		JPanel formulario = crearFormularioSinPestanas();
		cont.add(formulario, BorderLayout.CENTER);

		// Barra inferior
		JPanel barra = new JPanel();
		barra.setBackground(rosaFondo);
		barra.setLayout(new BoxLayout(barra, BoxLayout.X_AXIS));

		btnNuevo = boton(MonitorDePID.idioma.nuevo(), this::limpiarFormulario);
		btnActualizar = boton(MonitorDePID.idioma.actualizarSeleccionado(), this::actualizarSeleccionado);
		btnEliminar = boton(MonitorDePID.idioma.eliminarSeleccionado(), this::eliminarSeleccionado);
		btnExportar = boton(MonitorDePID.idioma.exportarJSON(), this::exportarArchivo);
		btnGuardar = boton(MonitorDePID.idioma.guardarTodo(), this::guardarTodo);

		barra.add(btnNuevo);
		barra.add(Box.createHorizontalStrut(8));
		barra.add(btnActualizar);
		barra.add(Box.createHorizontalStrut(8));
		barra.add(btnEliminar);
		barra.add(Box.createHorizontalGlue());
		barra.add(btnExportar);
		barra.add(Box.createHorizontalStrut(8));
		barra.add(btnGuardar);

		cont.add(barra, BorderLayout.SOUTH);

		return cont;
	}

	private JPanel crearFormularioSinPestanas() {
		JPanel form = new JPanel(new BorderLayout(8, 8));
		form.setBackground(rosaFondo);

		// ---------- Sección General ----------
		JPanel general = new JPanel(new GridBagLayout());
		general.setOpaque(true);
		general.setBackground(Color.WHITE);
		general.setBorder(titulo("general", MonitorDePID.idioma.general()));

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(6, 8, 6, 8);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		c.gridx = 0;

		fId = campo(general, c, MonitorDePID.idioma.id());
		fParaBuscar = campo(general, c, MonitorDePID.idioma.paraBuscar());

		// Filtro como dropdown
		etiqueta(general, c, MonitorDePID.idioma.filtro());
		GridBagConstraints c2 = (GridBagConstraints) c.clone();
		c2.gridx = 1;
		c2.weightx = 1;
		cbFiltro = new JComboBox<>();
		cbFiltro.addItem(""); // Opción vacía
		List<String> ids = new ArrayList<>(FiltrodeCodice.filtros.keySet());
		ids.sort(String::compareToIgnoreCase);
		for (String id : ids) {
			cbFiltro.addItem(id);
		}
		estilizarCampo(cbFiltro);
		general.add(cbFiltro, c2);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 0;

		// Criticalidad como dropdown con valores localizados
		etiqueta(general, c, MonitorDePID.idioma.criticalidad());
		c2 = (GridBagConstraints) c.clone();
		c2.gridx = 1;
		c2.weightx = 1;
		cbCriticalidad = new JComboBox<>();
		cbCriticalidad.addItem(Criticalidad.ADVERTENCIA.nombre); // Localizado
		cbCriticalidad.addItem(Criticalidad.ERROR.nombre); // Localizado
		cbCriticalidad.addItem(Criticalidad.FATAL.nombre); // Localizado
		estilizarCampo(cbCriticalidad);
		general.add(cbCriticalidad, c2);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 0;

		// prioridad
		etiqueta(general, c, MonitorDePID.idioma.prioridad());
		c2 = (GridBagConstraints) c.clone();
		c2.gridx = 1;
		c2.weightx = 1;
		spPrioridad = new JSpinner(new SpinnerNumberModel(0, -1000, 1000, 1));
		estilizarCampo(spPrioridad);
		general.add(spPrioridad, c2);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 0;

		form.add(general, BorderLayout.NORTH);

		// ---------- Sección Idiomas en REJILLA (sin tabs) ----------
		JPanel idiomas = new JPanel(new BorderLayout());
		idiomas.setOpaque(false);

		// Cabecera de columnas
		JPanel cab = new JPanel(new GridLayout(1, 3, 8, 8));
		cab.setBackground(rosaFondo);
		cab.add(celdaCabecera(MonitorDePID.idioma.colIdioma()));
		cab.add(celdaCabecera(MonitorDePID.idioma.colNombre()));
		cab.add(celdaCabecera(MonitorDePID.idioma.colResultado()));
		idiomas.add(cab, BorderLayout.NORTH);

		// Rejilla de filas (10 idiomas)
		JPanel grid = new JPanel(new GridLayout(10, 3, 8, 8));
		grid.setBackground(Color.WHITE);
		grid.setBorder(bordeSuave());

		// orden definido + archivo de bandera
		String[][] langs = { { "es", "bandera_mexico.png", "Español" }, { "en", "bandera_inglaterra.png", "English" },
				{ "ar", "bandera_arabia.png", "العربية" }, { "pt", "bandera_brasil.png", "Português" },
				{ "fa", "bandera_iran.png", "فارسی" }, { "ru", "bandera_rusia.png", "Русский" },
				{ "zh", "bandera_china.png", "中文" }, { "eo", "bandera_esperanto.png", "Esperanto" },
				{ "jp", "bandera_japon.png", "日本語" }, { "kp", "bandera_corea.png", "한국어" } };

		camposIdiomas.clear();
		for (String[] info : langs) {
			// Columna 1: idioma + bandera
			grid.add(celdaIdioma(info[2], info[1]));

			// Columna 2: nombre
			JTextField tNombre = new JTextField();
			estilizarCampo(tNombre);
			grid.add(tNombre);

			// Columna 3: resultado
			JTextField tResultado = new JTextField();
			estilizarCampo(tResultado);
			grid.add(tResultado);

			camposIdiomas.put(info[0], new JTextField[] { tNombre, tResultado });
		}

		idiomas.add(grid, BorderLayout.CENTER);

		JScrollPane spIdiomas = new JScrollPane(idiomas);
		spIdiomas.setBorder(titulo("idiomas", MonitorDePID.idioma.idiomas()));
		form.add(spIdiomas, BorderLayout.CENTER);

		return form;
	}

	private JPanel crearPanelDerecho() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(rosaFondo);
		p.setBorder(BorderFactory.createEmptyBorder(8, 6, 12, 12));

		vistaJson = new JTextArea();
		vistaJson.setEditable(false);
		vistaJson.setFont(new Font("Consolas", Font.PLAIN, 12));
		vistaJson.setForeground(textoOscuro);
		vistaJson.setBackground(Color.WHITE);

		JScrollPane sp = new JScrollPane(vistaJson);
		sp.setBorder(titulo("vistaJson", MonitorDePID.idioma.vistaJson()));

		p.add(sp, BorderLayout.CENTER);
		return p;
	}

	// -------------------- Utilidades UI --------------------

	private JLabel cargarImagen(String relativa, int w, int h) {
		File f = MonitorDePID.carpeta.resolve(relativa).toFile();
		if (!f.exists())
			return null;
		ImageIcon ic = new ImageIcon(f.getAbsolutePath());
		if (ic.getIconWidth() <= 0)
			return null;
		Image esc = ic.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return new JLabel(new ImageIcon(esc));
	}

	private void cargarIconos() {
		String[] archivos = { "bandera_mexico.png", "bandera_inglaterra.png", "bandera_arabia.png",
				"bandera_brasil.png", "bandera_iran.png", "bandera_rusia.png", "bandera_china.png",
				"bandera_esperanto.png", "bandera_japon.png", "bandera_corea.png", "ironmouse.png" };
		for (String a : archivos) {
			File f = MonitorDePID.carpeta.resolve("imagenes").resolve(a).toFile();
			if (f.exists()) {
				iconos.put(a, new ImageIcon(f.getAbsolutePath()));
			}
		}
	}

	private JPanel celdaIdioma(String etiqueta, String archivoBandera) {
		JPanel p = new JPanel(new BorderLayout());
		p.setOpaque(false);
		JLabel l = new JLabel(etiqueta);
		l.setFont(new Font("Segoe UI", Font.BOLD, 12));
		l.setForeground(moradoAcento);

		ImageIcon ic = iconos.get(archivoBandera);
		if (ic != null && ic.getIconWidth() > 0) {
			Image mini = ic.getImage().getScaledInstance(28, 18, Image.SCALE_SMOOTH);
			l.setIcon(new ImageIcon(mini));
			l.setIconTextGap(8);
		}
		p.add(l, BorderLayout.WEST);
		return p;
	}

	private Component celdaCabecera(String titulo) {
		JLabel l = new JLabel(titulo);
		l.setFont(new Font("Segoe UI", Font.BOLD, 12));
		l.setForeground(textoOscuro);
		l.setOpaque(false);
		return l;
	}

	private JTextField campo(JPanel p, GridBagConstraints base, String etiqueta) {
		etiqueta(p, base, etiqueta);
		GridBagConstraints c2 = (GridBagConstraints) base.clone();
		c2.gridx = 1;
		c2.weightx = 1;
		JTextField t = new JTextField();
		estilizarCampo(t);
		p.add(t, c2);
		base.gridy++;
		base.gridx = 0;
		base.weightx = 0;
		return t;
	}

	private void etiqueta(JPanel p, GridBagConstraints c, String txt) {
		JLabel l = new JLabel(txt);
		l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		l.setForeground(textoOscuro);
		p.add(l, c);
	}

	private void estilizarCampo(Component comp) {
		comp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		if (comp instanceof JTextField)
			((JTextField) comp).setBackground(Color.WHITE);
		if (comp instanceof JComboBox)
			((JComboBox<?>) comp).setBackground(Color.WHITE);
	}

	private JButton boton(String texto, Runnable r) {
		JButton b = new JButton(texto);
		b.setBackground(rosaSuave);
		b.setForeground(textoOscuro);
		b.setFocusPainted(false);
		b.setFont(new Font("Segoe UI", Font.BOLD, 12));
		b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(bordeSuave),
				BorderFactory.createEmptyBorder(6, 12, 6, 12)));
		b.addActionListener(e -> r.run());
		return b;
	}

	private javax.swing.border.TitledBorder titulo(String key, String defecto) {
		javax.swing.border.TitledBorder t = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(bordeSuave),
				defecto);
		t.setTitleColor(moradoAcento);
		t.setTitleFont(new Font("Segoe UI", Font.BOLD, 12));
		return t;
	}

	private javax.swing.border.Border bordeSuave() {
		return BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(bordeSuave),
				BorderFactory.createEmptyBorder(8, 8, 8, 8));
	}

	// -------------------- Lógica --------------------

	private void asegurarArchivo() {
		try {
			if (!Files.exists(MonitorDePID.carpeta)) {
				Files.createDirectories(MonitorDePID.carpeta);
			}
			if (!Files.exists(rutaCodice)) {
				String base = "{ \"schema\": 0, \"verificaciones\": [] }";
				Files.write(rutaCodice, base.getBytes(StandardCharsets.UTF_8));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error creando codice.json: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void recargarDesdeDisco() {
		modeloLista.clear();
		List<VerificacionFirmasV0> datos = CargadorDeCodice.cargarVerificaciones();
		datos.sort(new Comparator<VerificacionFirmasV0>() {
			@Override
			public int compare(VerificacionFirmasV0 a, VerificacionFirmasV0 b) {
				int cmp = Integer.compare(b.prioridad, a.prioridad);
				return (cmp != 0) ? cmp : nz(a.id).compareToIgnoreCase(nz(b.id));
			}
		});
		for (VerificacionFirmasV0 v : datos)
			modeloLista.addElement(v);
		if (!datos.isEmpty())
			lista.setSelectedIndex(0);
	}

	/**
	 * Carga una verificación en el formulario y actualiza el seguimiento.
	 */
	private void cargarEnFormulario(VerificacionFirmasV0 v) {
		fId.setText(nz(v.id));
		fParaBuscar.setText(nz(v.para_buscar));

		if (v.filtro != null) {
			cbFiltro.setSelectedItem(v.filtro.id);
		} else {
			cbFiltro.setSelectedIndex(0);
		}

		if (v.criticalidad != null) {
			cbCriticalidad.setSelectedItem(v.criticalidad.nombre);
		} else {
			cbCriticalidad.setSelectedItem(Criticalidad.ADVERTENCIA.nombre);
		}

		spPrioridad.setValue(v.prioridad);

		setLang("es", v.nombre_es, v.resultado_es);
		setLang("en", v.nombre_en, v.resultado_en);
		setLang("ar", v.nombre_ar, v.resultado_ar);
		setLang("pt", v.nombre_pt, v.resultado_pt);
		setLang("fa", v.nombre_fa, v.resultado_fa);
		setLang("ru", v.nombre_ru, v.resultado_ru);
		setLang("zh", v.nombre_zh, v.resultado_zh);
		setLang("eo", v.nombre_eo, v.resultado_eo);
		setLang("jp", v.nombre_jp, v.resultado_jp);
		setLang("kp", v.nombre_kp, v.resultado_kp);

		actualizarVistaJson();
		this.verificacionCargadaEnFormulario = v; // ¡Importante!
	}

	/**
	 * Determina si el formulario actual tiene cambios no guardados en comparación
	 * con la verificación cargada (o si hay datos nuevos no guardados).
	 */
	private boolean esFormularioModificado() {
		if (verificacionCargadaEnFormulario == null) {
			// Formulario vacío o nuevo: ¿tiene algún dato?
			return !isEmpty(fId.getText()) || !isEmpty(fParaBuscar.getText()) || cbFiltro.getSelectedIndex() != 0
					|| !Objects.equals(cbCriticalidad.getSelectedItem(), Criticalidad.ADVERTENCIA.nombre)
					|| (Integer) spPrioridad.getValue() != 0 || camposIdiomas.values().stream()
							.anyMatch(arr -> !isEmpty(arr[0].getText()) || !isEmpty(arr[1].getText()));
		}

		VerificacionFirmasV0 actual = verificacionCargadaEnFormulario;
		return !fId.getText().trim().equals(actual.id) || !fParaBuscar.getText().trim().equals(actual.para_buscar)
				|| !Objects.equals(cbFiltro.getSelectedItem(), actual.filtro != null ? actual.filtro.id : "")
				|| !Objects.equals(cbCriticalidad.getSelectedItem(),
						actual.criticalidad != null ? actual.criticalidad.nombre : Criticalidad.ADVERTENCIA.nombre)
				|| (Integer) spPrioridad.getValue() != actual.prioridad || !val("es", 0).equals(actual.nombre_es)
				|| !val("es", 1).equals(actual.resultado_es) || !val("en", 0).equals(actual.nombre_en)
				|| !val("en", 1).equals(actual.resultado_en) || !val("ar", 0).equals(actual.nombre_ar)
				|| !val("ar", 1).equals(actual.resultado_ar) || !val("pt", 0).equals(actual.nombre_pt)
				|| !val("pt", 1).equals(actual.resultado_pt) || !val("fa", 0).equals(actual.nombre_fa)
				|| !val("fa", 1).equals(actual.resultado_fa) || !val("ru", 0).equals(actual.nombre_ru)
				|| !val("ru", 1).equals(actual.resultado_ru) || !val("zh", 0).equals(actual.nombre_zh)
				|| !val("zh", 1).equals(actual.resultado_zh) || !val("eo", 0).equals(actual.nombre_eo)
				|| !val("eo", 1).equals(actual.resultado_eo) || !val("jp", 0).equals(actual.nombre_jp)
				|| !val("jp", 1).equals(actual.resultado_jp) || !val("kp", 0).equals(actual.nombre_kp)
				|| !val("kp", 1).equals(actual.resultado_kp);
	}

	private void setLang(String code, String nombre, String resultado) {
		JTextField[] arr = camposIdiomas.get(code);
		if (arr != null) {
			arr[0].setText(nz(nombre));
			arr[1].setText(nz(resultado));
		}
	}

	/**
	 * Limpia el formulario para crear una nueva verificación. Pregunta antes si hay
	 * cambios no guardados.
	 */
	private void limpiarFormulario() {
		if (!confirmarDescartarCambios()) {
			// Si había una verificación cargada, volver a seleccionarla
			if (verificacionCargadaEnFormulario != null) {
				lista.setSelectedValue(verificacionCargadaEnFormulario, true);
			}
			return;
		}

		// Limpiar selección y campos
		lista.clearSelection();
		fId.setText("");
		fParaBuscar.setText("");
		cbFiltro.setSelectedIndex(0);
		cbCriticalidad.setSelectedItem(Criticalidad.ADVERTENCIA.nombre);
		spPrioridad.setValue(0);
		for (JTextField[] arr : camposIdiomas.values()) {
			arr[0].setText("");
			arr[1].setText("");
		}
		verificacionCargadaEnFormulario = null;
		actualizarVistaJson();
	}

	// El botón de elegir filtro ya no es necesario, pero lo dejamos sin
	// funcionalidad
	private void elegirFiltro() {
		// No hace nada, porque ahora usamos un JComboBox
	}

	private boolean validarFormulario() {
		if (vacio(fId) || vacio(fParaBuscar)) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.faltanCampos(), "Validación",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		// Validar que se haya seleccionado un filtro (no vacío)
		if (cbFiltro.getSelectedItem() == null || "".equals(cbFiltro.getSelectedItem())) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.filtroNoExiste(), "Validación",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		// Validar que se haya seleccionado una criticalidad
		if (cbCriticalidad.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.critInvalida(), "Validación",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		// Validar que el filtro exista realmente
		String idFiltro = (String) cbFiltro.getSelectedItem();
		if (FiltrodeCodice.filtros.get(idFiltro) == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.filtroNoExiste(), "Validación",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		for (Map.Entry<String, JTextField[]> e : camposIdiomas.entrySet()) {
			if (vacio(e.getValue()[0]) || vacio(e.getValue()[1])) {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.faltanIdiomas() + " " + e.getKey(),
						"Validación", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private VerificacionFirmasV0 construirDesdeFormulario() {
		String id = fId.getText().trim();
		String paraBuscar = fParaBuscar.getText().trim();
		String idFiltro = (String) cbFiltro.getSelectedItem();
		FiltrodeCodice filtro = FiltrodeCodice.filtros.get(idFiltro);

		// Convertir el valor localizado del combo a la criticalidad correspondiente
		String critLocalizado = (String) cbCriticalidad.getSelectedItem();
		Criticalidad criticalidad;
		if (critLocalizado.equals(Criticalidad.ERROR.nombre)) {
			criticalidad = Criticalidad.ERROR;
		} else if (critLocalizado.equals(Criticalidad.FATAL.nombre)) {
			criticalidad = Criticalidad.FATAL;
		} else {
			criticalidad = Criticalidad.ADVERTENCIA;
		}

		int prio = ((Number) spPrioridad.getValue()).intValue();

		return new VerificacionFirmasV0(id, val("ar", 0), val("ar", 1), val("zh", 0), val("zh", 1), val("kp", 0),
				val("kp", 1), val("es", 0), val("es", 1), val("eo", 0), val("eo", 1), val("en", 0), val("en", 1),
				val("jp", 0), val("jp", 1), val("fa", 0), val("fa", 1), val("pt", 0), val("pt", 1), val("ru", 0),
				val("ru", 1), criticalidad, prio, paraBuscar, filtro);
	}

	private String val(String lang, int idx) {
		JTextField[] arr = camposIdiomas.get(lang);
		return arr == null ? "" : nz(arr[idx].getText());
	}

	private void actualizarSeleccionado() {
		if (!validarFormulario())
			return;
		VerificacionFirmasV0 nuevo = construirDesdeFormulario();
		int idx = lista.getSelectedIndex();
		if (idx >= 0) {
			modeloLista.set(idx, nuevo);
			lista.setSelectedIndex(idx);
		} else {
			modeloLista.addElement(nuevo);
			lista.setSelectedIndex(modeloLista.size() - 1);
		}
		actualizarVistaJson();
	}

	/**
	 * Elimina la verificación seleccionada. Pregunta antes si hay cambios no
	 * guardados en el formulario actual.
	 */
	private void eliminarSeleccionado() {
		if (lista.getSelectedValue() == null)
			return;

		if (esFormularioModificado()) {
			int respuesta = JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.descartarCambios(),
					MonitorDePID.idioma.confirmacion(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (respuesta != JOptionPane.YES_OPTION) {
				return;
			}
		}

		int idx = lista.getSelectedIndex();
		modeloLista.remove(idx);
		verificacionCargadaEnFormulario = null;

		if (modeloLista.isEmpty()) {
			limpiarFormulario();
		} else {
			// Seleccionar el mismo índice o el último si era el final
			int nuevoIdx = Math.min(idx, modeloLista.size() - 1);
			lista.setSelectedIndex(nuevoIdx);
		}
		actualizarVistaJson();
	}

	private void guardarTodo() {
		try {
			// Validar todos
			for (int i = 0; i < modeloLista.size(); i++) {
				if (!validarVerificacion(modeloLista.get(i))) {
					JOptionPane.showMessageDialog(this, MonitorDePID.idioma.verificacionInvalida(), "Validación",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			List<VerificacionFirmasV0> arr = new ArrayList<VerificacionFirmasV0>();
			for (int i = 0; i < modeloLista.size(); i++)
				arr.add(modeloLista.get(i));
			FirmasV0.guardar(arr);
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.guardadoOk(), "OK",
					JOptionPane.INFORMATION_MESSAGE);
			actualizarVistaJson();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void exportarArchivo() {
		try {
			JFileChooser ch = new JFileChooser();
			ch.setSelectedFile(new File("codice.json"));
			if (ch.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				Files.write(ch.getSelectedFile().toPath(), vistaJson.getText().getBytes(StandardCharsets.UTF_8));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error exportando: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean validarVerificacion(VerificacionFirmasV0 v) {
		if (isEmpty(v.id) || isEmpty(v.para_buscar) || v.filtro == null || isEmpty(v.filtro.id))
			return false;
		if (v.criticalidad == null)
			return false;
		return !(isEmpty(v.nombre_es) || isEmpty(v.resultado_es) || isEmpty(v.nombre_en) || isEmpty(v.resultado_en)
				|| isEmpty(v.nombre_ar) || isEmpty(v.resultado_ar) || isEmpty(v.nombre_pt) || isEmpty(v.resultado_pt)
				|| isEmpty(v.nombre_fa) || isEmpty(v.resultado_fa) || isEmpty(v.nombre_ru) || isEmpty(v.resultado_ru)
				|| isEmpty(v.nombre_zh) || isEmpty(v.resultado_zh) || isEmpty(v.nombre_eo) || isEmpty(v.resultado_eo)
				|| isEmpty(v.nombre_jp) || isEmpty(v.resultado_jp) || isEmpty(v.nombre_kp) || isEmpty(v.resultado_kp));
	}

	private void actualizarVistaJson() {
		try {
			List<VerificacionFirmasV0> arr = new ArrayList<VerificacionFirmasV0>();
			for (int i = 0; i < modeloLista.size(); i++)
				arr.add(modeloLista.get(i));
			vistaJson.setText(construirJsonPreview(arr));
			vistaJson.setCaretPosition(0);
		} catch (Exception ex) {
			vistaJson.setText("{ \"error\": \"" + ex.getMessage() + "\" }");
		}
	}

	private boolean hayCambiosNoGuardados() {
		try {
			List<VerificacionFirmasV0> enDisco = CargadorDeCodice.cargarVerificaciones();
			if (enDisco.size() != modeloLista.size())
				return true;

			for (int i = 0; i < modeloLista.size(); i++) {
				VerificacionFirmasV0 enMem = modeloLista.get(i);
				VerificacionFirmasV0 enArc = enDisco.get(i);
				if (!enMem.equals(enArc))
					return true;
			}
			return false;
		} catch (Exception ex) {
			return true;
		}
	}

	/**
	 * Intenta limpiar el formulario (para nuevo o cambio de selección). Si hay
	 * cambios no guardados, pregunta al usuario.
	 * 
	 * @return true si se puede proceder (usuario confirmó descartar o no hay
	 *         cambios)
	 */
	private boolean confirmarDescartarCambios() {
		if (esFormularioModificado()) {
			int respuesta = JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.descartarCambios(),
					MonitorDePID.idioma.confirmacion(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			return respuesta == JOptionPane.YES_OPTION;
		}
		return true;
	}

	// Vista previa manual (solo para mostrar; el guardado real lo hace CodiceV0)
	private String construirJsonPreview(List<VerificacionFirmasV0> arr) {
		String nl = "\n";
		StringBuilder sb = new StringBuilder();
		sb.append("{").append(nl);
		sb.append("  \"schema\": 0,").append(nl);
		sb.append("  \"verificaciones\": [").append(nl);
		for (int i = 0; i < arr.size(); i++) {
			VerificacionFirmasV0 v = arr.get(i);
			sb.append("    {").append(nl);
			w(sb, "id", v.id, 6).append(",").append(nl);
			w(sb, "para_buscar", v.para_buscar, 6).append(",").append(nl);
			w(sb, "filtro", (v.filtro != null ? v.filtro.id : ""), 6).append(",").append(nl);
			w(sb, "criticalidad", v.criticalidad != null ? v.criticalidad.nombre : "ADVERTENCIA", 6).append(",")
					.append(nl);
			wnum(sb, "prioridad", v.prioridad, 6).append(",").append(nl);

			sb.append(sp(6)).append("\"nombres\": {").append(nl);
			nombres(sb, "ar", v.nombre_ar, 8).append(",").append(nl);
			nombres(sb, "zh", v.nombre_zh, 8).append(",").append(nl);
			nombres(sb, "kp", v.nombre_kp, 8).append(",").append(nl);
			nombres(sb, "es", v.nombre_es, 8).append(",").append(nl);
			nombres(sb, "eo", v.nombre_eo, 8).append(",").append(nl);
			nombres(sb, "en", v.nombre_en, 8).append(",").append(nl);
			nombres(sb, "jp", v.nombre_jp, 8).append(",").append(nl);
			nombres(sb, "fa", v.nombre_fa, 8).append(",").append(nl);
			nombres(sb, "pt", v.nombre_pt, 8).append(",").append(nl);
			nombres(sb, "ru", v.nombre_ru, 8).append(nl);
			sb.append(sp(6)).append("},").append(nl);

			sb.append(sp(6)).append("\"resultados\": {").append(nl);
			nombres(sb, "ar", v.resultado_ar, 8).append(",").append(nl);
			nombres(sb, "zh", v.resultado_zh, 8).append(",").append(nl);
			nombres(sb, "kp", v.resultado_kp, 8).append(",").append(nl);
			nombres(sb, "es", v.resultado_es, 8).append(",").append(nl);
			nombres(sb, "eo", v.resultado_eo, 8).append(",").append(nl);
			nombres(sb, "en", v.resultado_en, 8).append(",").append(nl);
			nombres(sb, "jp", v.resultado_jp, 8).append(",").append(nl);
			nombres(sb, "fa", v.resultado_fa, 8).append(",").append(nl);
			nombres(sb, "pt", v.resultado_pt, 8).append(",").append(nl);
			nombres(sb, "ru", v.resultado_ru, 8).append(nl);
			sb.append(sp(6)).append("}").append(nl);

			sb.append("    }");
			if (i < arr.size() - 1)
				sb.append(",");
			sb.append(nl);
		}
		sb.append("  ]").append(nl);
		sb.append("}").append(nl);
		return sb.toString();
	}

	private StringBuilder w(StringBuilder sb, String k, String v, int sp) {
		return sb.append(sp(sp)).append("\"").append(k).append("\": ").append("\"").append(escape(nz(v))).append("\"");
	}

	private StringBuilder wnum(StringBuilder sb, String k, int v, int sp) {
		return sb.append(sp(sp)).append("\"").append(k).append("\": ").append(v);
	}

	private StringBuilder nombres(StringBuilder sb, String k, String v, int sp) {
		return w(sb, k, v, sp);
	}

	private String sp(int n) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < n; i++)
			s.append(' ');
		return s.toString();
	}

	private String escape(String s) {
		return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
	}

	// -------------------- Helpers --------------------
	private boolean vacio(JTextField t) {
		return t.getText() == null || t.getText().trim().isEmpty();
	}

	private boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	private String nz(String s) {
		return s == null ? "" : s;
	}

	// -------------------- Interfaz barra lateral --------------------
	@Override
	public void init() {
		setVisible(true);
	}

	@Override
	public String etiquetaDelBoton() {
		return MonitorDePID.idioma.editorCodiceBoton();
	}
}