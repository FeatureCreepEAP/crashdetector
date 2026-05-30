package com.asbestosstar.crashdetector.gui.tipos.editor;

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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

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

import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.firmas.CargadorDeCodice;
import com.asbestosstar.crashdetector.analizador.firmas.FiltrodeCodice;
import com.asbestosstar.crashdetector.analizador.firmas.v0.FirmasV0;
import com.asbestosstar.crashdetector.analizador.firmas.v0.VerificacionFirmasV0;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class EditorFirmasGUI extends JFrame implements BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<EditorFirmasGUI>> GUIS = new HashMap<String, Supplier<EditorFirmasGUI>>();

	// Campos de entrada (públicos y no finales)
	public JTextField fId, fParaBuscar;
	public JComboBox<String> cbFiltro, cbCriticalidad;
	public JSpinner spPrioridad;
	public JButton btnElegirFiltro;

	// Vista previa
	public JTextArea vistaJson;

	// Paleta de colores (públicos y no finales)
	public ConfigColor rosaFondo;
	public ConfigColor rosaSuave;
	public ConfigColor moradoAcento;
	public ConfigColor textoOscuro;
	public ConfigColor bordeSuave;
	public ConfigColor fondoCampo;
	public ConfigColor textoCampo;

	// Modelo / lista
	public DefaultListModel<VerificacionFirmasV0> modeloLista = new DefaultListModel<VerificacionFirmasV0>();
	public JList<VerificacionFirmasV0> lista;

	// Campos por idioma (nombre, resultado) → clave -> {nombre, resultado}
	public final Map<String, JTextField[]> camposIdiomas = new LinkedHashMap<String, JTextField[]>();

	// Iconos (banderas + ironmouse)
	public final Map<String, ImageIcon> iconos = new LinkedHashMap<String, ImageIcon>();
	public JLabel etiquetaIronmouse;

	// Botones
	public JButton btnNuevo, btnActualizar, btnEliminar, btnExportar, btnGuardar;

	// Archivo
	public final Path rutaCodice = Statics.carpeta.resolve("firmas.json");

	public VerificacionFirmasV0 verificacionCargadaEnFormulario = null;

	// Constructor: NADA. Solo llamada a super()
	public EditorFirmasGUI() {
		super();
	}

	public TipoGUI<EditorFirmasGUI> tipo() {
		return TipoGUI.EDITOR_FIRMAS;
	}

	// -------------------- Métodos públicos --------------------

	public void aplicarEstilos() {
		getContentPane().setBackground(rosaFondo.obtener());
		setLayout(new BorderLayout(10, 10));

		cargarIconos();

		add(crearEncabezado(), BorderLayout.NORTH);
		add(crearSplit(), BorderLayout.CENTER);

		asegurarArchivo();
		recargarDesdeDisco();
		actualizarVistaJson();
	}

	public void init() {
		// Configuración básica de la ventana
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (hayCambiosNoGuardados()) {
					int resp = JOptionPane.showConfirmDialog(EditorFirmasGUI.this,
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
	}

	// -------------------- Métodos de UI --------------------

	public JPanel crearEncabezado() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBackground(rosaFondo.obtener());
		p.setBorder(BorderFactory.createEmptyBorder(8, 12, 0, 12));

		// --- Título ---
		JLabel titulo = new JLabel(MonitorDePID.idioma.tituloEditorCodice());
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
		titulo.setForeground(moradoAcento.obtener());
		p.add(titulo);

		// --- Contenedor de descripción + imagen ---
		JPanel descRow = new JPanel();
		descRow.setLayout(new BoxLayout(descRow, BoxLayout.X_AXIS));
		descRow.setOpaque(false);
		descRow.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Descripción
		String descText = "<html><body style='width: 600px;'>" + MonitorDePID.idioma.descripcionEditorCodice()
				+ "</body></html>";
		JLabel descripcion = new JLabel(descText);
		descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		descripcion.setForeground(textoOscuro.obtener());
		descripcion.setAlignmentY(Component.CENTER_ALIGNMENT);
		descRow.add(descripcion);
		descRow.add(Box.createHorizontalStrut(20));

		// Imagen
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

	public JSplitPane crearSplit() {
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, crearPanelIzquierdoCentro(),
				crearPanelDerecho());
		split.setDividerLocation(720);
		split.setResizeWeight(0.72);
		split.setBorder(BorderFactory.createEmptyBorder());
		return split;
	}

	public Component crearPanelIzquierdoCentro() {
		JPanel cont = new JPanel(new BorderLayout(10, 10));
		cont.setBackground(rosaFondo.obtener());
		cont.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 6));

		// Lista de verificaciones
		lista = new JList<VerificacionFirmasV0>(modeloLista);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lista.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
			String nombreVisible = "";
			if (value != null) {
				nombreVisible = nz(value.obtenerNombre());
				if (isEmpty(nombreVisible)) {
					nombreVisible = nz(value.id);
				}
			}

			JLabel l = new JLabel(nz(value != null ? value.id : "") + " · " + nombreVisible);
			l.setOpaque(true);
			l.setForeground(isSelected ? Color.WHITE : textoOscuro.obtener());
			l.setBackground(isSelected ? moradoAcento.obtener() : Color.WHITE);
			l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			l.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
			return l;
		});

		lista.addListSelectionListener(e -> {
			if (e.getValueIsAdjusting()) {
				return;
			}

			VerificacionFirmasV0 nuevaSeleccion = lista.getSelectedValue();

			if (verificacionCargadaEnFormulario != null && esFormularioModificado()) {
				int respuesta = JOptionPane.showConfirmDialog(EditorFirmasGUI.this,
						MonitorDePID.idioma.descartarCambios(), MonitorDePID.idioma.confirmacion(),
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (respuesta == JOptionPane.YES_OPTION) {
					if (nuevaSeleccion != null) {
						cargarEnFormulario(nuevaSeleccion);
					} else {
						verificacionCargadaEnFormulario = null;
					}
				} else {
					lista.setSelectedValue(verificacionCargadaEnFormulario, true);
				}
			} else {
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

		// Formulario completo
		JPanel formulario = crearFormularioSinPestanas();
		cont.add(formulario, BorderLayout.CENTER);

		// Barra inferior
		JPanel barra = new JPanel();
		barra.setBackground(rosaFondo.obtener());
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

	public JPanel crearFormularioSinPestanas() {
		JPanel form = new JPanel(new BorderLayout(8, 8));
		form.setBackground(rosaFondo.obtener());

		// ---------- Sección General ----------
		JPanel general = new JPanel(new GridBagLayout());
		general.setOpaque(true);
		general.setBackground(fondoCampo.obtener());
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
		cbFiltro = new JComboBox<String>();
		cbFiltro.addItem(""); // Opción vacía
		List<String> ids = new ArrayList<String>(FiltrodeCodice.filtros.keySet());
		ids.sort(String::compareToIgnoreCase);
		for (String id : ids) {
			cbFiltro.addItem(id);
		}
		estilizarCampo(cbFiltro);
		general.add(cbFiltro, c2);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 0;

		// Criticalidad como dropdown
		etiqueta(general, c, MonitorDePID.idioma.criticalidad());
		c2 = (GridBagConstraints) c.clone();
		c2.gridx = 1;
		c2.weightx = 1;
		cbCriticalidad = new JComboBox<String>();
		cbCriticalidad.addItem(Criticalidad.ADVERTENCIA.nombre);
		cbCriticalidad.addItem(Criticalidad.ERROR.nombre);
		cbCriticalidad.addItem(Criticalidad.FATAL.nombre);
		estilizarCampo(cbCriticalidad);
		general.add(cbCriticalidad, c2);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 0;

		// Prioridad
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

		// ---------- Sección Idiomas dinámica ----------
		JPanel idiomas = new JPanel(new BorderLayout());
		idiomas.setOpaque(false);

		JPanel cab = new JPanel(new GridLayout(1, 3, 8, 8));
		cab.setBackground(rosaFondo.obtener());
		cab.add(celdaCabecera(MonitorDePID.idioma.colIdioma()));
		cab.add(celdaCabecera(MonitorDePID.idioma.colNombre()));
		cab.add(celdaCabecera(MonitorDePID.idioma.colResultado()));
		idiomas.add(cab, BorderLayout.NORTH);

		reconstruirIdiomasEditor();

		int cantidadIdiomas = Math.max(1, idiomasEditor.size());
		JPanel grid = new JPanel(new GridLayout(cantidadIdiomas, 3, 8, 8));
		grid.setBackground(fondoCampo.obtener());
		grid.setBorder(bordeSuave());

		camposIdiomas.clear();
		for (InfoIdiomaEditor info : idiomasEditor) {
			grid.add(celdaIdioma(info.nombreVisible, info.archivoBandera));

			JTextField tNombre = new JTextField();
			estilizarCampo(tNombre);
			grid.add(tNombre);

			JTextField tResultado = new JTextField();
			estilizarCampo(tResultado);
			grid.add(tResultado);

			camposIdiomas.put(info.codigo, new JTextField[] { tNombre, tResultado });
		}

		idiomas.add(grid, BorderLayout.CENTER);

		JScrollPane spIdiomas = new JScrollPane(idiomas);
		spIdiomas.setBorder(titulo("idiomas", MonitorDePID.idioma.idiomas()));
		form.add(spIdiomas, BorderLayout.CENTER);

		return form;
	}

	public JPanel crearPanelDerecho() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(rosaFondo.obtener());
		p.setBorder(BorderFactory.createEmptyBorder(8, 6, 12, 12));

		vistaJson = new JTextArea();
		vistaJson.setEditable(false);
		vistaJson.setFont(new Font("Consolas", Font.PLAIN, 12));
		vistaJson.setForeground(textoOscuro.obtener());
		vistaJson.setBackground(fondoCampo.obtener());

		JScrollPane sp = new JScrollPane(vistaJson);
		sp.setBorder(titulo("vistaJson", MonitorDePID.idioma.vistaJson()));

		p.add(sp, BorderLayout.CENTER);
		return p;
	}

	// -------------------- Utilidades UI --------------------

	public JLabel cargarImagen(String relativa, int w, int h) {
		File f = Statics.carpeta.resolve(relativa).toFile();
		if (!f.exists())
			return null;
		ImageIcon ic = new ImageIcon(f.getAbsolutePath());
		if (ic.getIconWidth() <= 0)
			return null;
		Image esc = ic.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return new JLabel(new ImageIcon(esc));
	}

	public void cargarIconos() {
		iconos.clear();

		reconstruirIdiomasEditor();

		for (InfoIdiomaEditor info : idiomasEditor) {
			if (info.archivoBandera == null || info.archivoBandera.trim().isEmpty()) {
				continue;
			}

			File f = Statics.carpeta.resolve("imagenes").resolve(info.archivoBandera).toFile();
			if (f.exists()) {
				iconos.put(info.archivoBandera, new ImageIcon(f.getAbsolutePath()));
			}
		}

		File iron = Statics.carpeta.resolve("imagenes").resolve("ironmouse.png").toFile();
		if (iron.exists()) {
			iconos.put("ironmouse.png", new ImageIcon(iron.getAbsolutePath()));
		}
	}

	public JPanel celdaIdioma(String etiqueta, String archivoBandera) {
		JPanel p = new JPanel(new BorderLayout());
		p.setOpaque(false);
		JLabel l = new JLabel(etiqueta);
		l.setFont(new Font("Segoe UI", Font.BOLD, 12));
		l.setForeground(moradoAcento.obtener());

		ImageIcon ic = iconos.get(archivoBandera);
		if (ic != null && ic.getIconWidth() > 0) {
			Image mini = ic.getImage().getScaledInstance(28, 18, Image.SCALE_SMOOTH);
			l.setIcon(new ImageIcon(mini));
			l.setIconTextGap(8);
		}
		p.add(l, BorderLayout.WEST);
		return p;
	}

	public Component celdaCabecera(String titulo) {
		JLabel l = new JLabel(titulo);
		l.setFont(new Font("Segoe UI", Font.BOLD, 12));
		l.setForeground(textoOscuro.obtener());
		l.setOpaque(false);
		return l;
	}

	public JTextField campo(JPanel p, GridBagConstraints base, String etiqueta) {
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

	public void etiqueta(JPanel p, GridBagConstraints c, String txt) {
		JLabel l = new JLabel(txt);
		l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		l.setForeground(textoOscuro.obtener());
		p.add(l, c);
	}

	public void estilizarCampo(Component comp) {
		comp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		if (comp instanceof JTextField)
			((JTextField) comp).setBackground(fondoCampo.obtener());
		if (comp instanceof JComboBox)
			((JComboBox<?>) comp).setBackground(fondoCampo.obtener());
	}

	public JButton boton(String texto, Runnable r) {
		JButton b = new JButton(texto);
		b.setBackground(rosaSuave.obtener());
		b.setForeground(textoOscuro.obtener());
		b.setFocusPainted(false);
		b.setFont(new Font("Segoe UI", Font.BOLD, 12));
		b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(bordeSuave.obtener()),
				BorderFactory.createEmptyBorder(6, 12, 6, 12)));
		b.addActionListener(e -> r.run());
		return b;
	}

	public javax.swing.border.TitledBorder titulo(String key, String defecto) {
		javax.swing.border.TitledBorder t = BorderFactory
				.createTitledBorder(BorderFactory.createLineBorder(bordeSuave.obtener()), defecto);
		t.setTitleColor(moradoAcento.obtener());
		t.setTitleFont(new Font("Segoe UI", Font.BOLD, 12));
		return t;
	}

	public javax.swing.border.Border bordeSuave() {
		return BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(bordeSuave.obtener()),
				BorderFactory.createEmptyBorder(8, 8, 8, 8));
	}

	// -------------------- Lógica --------------------

	public void asegurarArchivo() {
		try {
			if (!Files.exists(Statics.carpeta)) {
				Files.createDirectories(Statics.carpeta);
			}
			if (!Files.exists(rutaCodice)) {
				String base = "{ \"schema\": 0, \"verificaciones\": [] }";
				Files.write(rutaCodice, base.getBytes(StandardCharsets.UTF_8));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.error_creando_codice_json() + ex.getMessage(),
					MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void recargarDesdeDisco() {
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

	public void cargarEnFormulario(VerificacionFirmasV0 v) {
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

		for (JTextField[] arr : camposIdiomas.values()) {
			arr[0].setText("");
			arr[1].setText("");
		}

		for (Map.Entry<String, String> e : v.nombres_por_codigo.entrySet()) {
			JTextField[] arr = camposIdiomas.get(VerificacionFirmasV0.normalizarCodigoIdioma(e.getKey()));
			if (arr != null) {
				arr[0].setText(nz(e.getValue()));
			}
		}

		for (Map.Entry<String, String> e : v.resultados_por_codigo.entrySet()) {
			JTextField[] arr = camposIdiomas.get(VerificacionFirmasV0.normalizarCodigoIdioma(e.getKey()));
			if (arr != null) {
				arr[1].setText(nz(e.getValue()));
			}
		}

		actualizarVistaJson();
		this.verificacionCargadaEnFormulario = v;
	}

	public boolean esFormularioModificado() {
		if (verificacionCargadaEnFormulario == null) {
			boolean hayTextoGeneral = !isEmpty(fId.getText()) || !isEmpty(fParaBuscar.getText())
					|| cbFiltro.getSelectedIndex() != 0
					|| !Objects.equals(cbCriticalidad.getSelectedItem(), Criticalidad.ADVERTENCIA.nombre)
					|| ((Integer) spPrioridad.getValue()).intValue() != 0;

			if (hayTextoGeneral) {
				return true;
			}

			for (JTextField[] arr : camposIdiomas.values()) {
				if (!isEmpty(arr[0].getText()) || !isEmpty(arr[1].getText())) {
					return true;
				}
			}

			return false;
		}

		VerificacionFirmasV0 actual = verificacionCargadaEnFormulario;

		Map<String, String> nombresFormulario = construirMapaDesdeFormulario(0);
		Map<String, String> resultadosFormulario = construirMapaDesdeFormulario(1);

		return !fId.getText().trim().equals(actual.id) || !fParaBuscar.getText().trim().equals(actual.para_buscar)
				|| !Objects.equals(cbFiltro.getSelectedItem(), actual.filtro != null ? actual.filtro.id : "")
				|| !Objects.equals(cbCriticalidad.getSelectedItem(),
						actual.criticalidad != null ? actual.criticalidad.nombre : Criticalidad.ADVERTENCIA.nombre)
				|| ((Integer) spPrioridad.getValue()).intValue() != actual.prioridad
				|| !Objects.equals(nombresFormulario, actual.nombres_por_codigo)
				|| !Objects.equals(resultadosFormulario, actual.resultados_por_codigo);
	}

	public void establecerIdioma(String code, String nombre, String resultado) {
		JTextField[] arr = camposIdiomas.get(code);
		if (arr != null) {
			arr[0].setText(nz(nombre));
			arr[1].setText(nz(resultado));
		}
	}

	public void limpiarFormulario() {
		if (!confirmarDescartarCambios()) {
			if (verificacionCargadaEnFormulario != null) {
				lista.setSelectedValue(verificacionCargadaEnFormulario, true);
			}
			return;
		}

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

	public void elegirFiltro() {
		// No hace nada, porque ahora usamos JComboBox
	}

	public boolean validarFormulario() {
		if (vacio(fId) || vacio(fParaBuscar)) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.faltanCampos(), MonitorDePID.idioma.validacion(),
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (cbFiltro.getSelectedItem() == null || "".equals(cbFiltro.getSelectedItem())) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.filtroNoExiste(), MonitorDePID.idioma.validacion(),
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (cbCriticalidad.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.critInvalida(), MonitorDePID.idioma.validacion(),
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		String idFiltro = (String) cbFiltro.getSelectedItem();
		if (FiltrodeCodice.filtros.get(idFiltro) == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.filtroNoExiste(), MonitorDePID.idioma.validacion(),
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		boolean hayAlMenosUnIdiomaCompleto = false;

		for (Map.Entry<String, JTextField[]> e : camposIdiomas.entrySet()) {
			boolean nombreVacio = vacio(e.getValue()[0]);
			boolean resultadoVacio = vacio(e.getValue()[1]);

			if (nombreVacio && resultadoVacio) {
				continue;
			}

			if (nombreVacio || resultadoVacio) {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.faltanIdiomas() + " " + e.getKey(),
						MonitorDePID.idioma.validacion(), JOptionPane.WARNING_MESSAGE);
				return false;
			}

			hayAlMenosUnIdiomaCompleto = true;
		}

		if (!hayAlMenosUnIdiomaCompleto) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.faltanIdiomas(), MonitorDePID.idioma.validacion(),
					JOptionPane.WARNING_MESSAGE);
			return false;
		}

		return true;
	}

	public VerificacionFirmasV0 construirDesdeFormulario() {
		String id = fId.getText().trim();
		String paraBuscar = fParaBuscar.getText().trim();
		String idFiltro = (String) cbFiltro.getSelectedItem();
		FiltrodeCodice filtro = FiltrodeCodice.filtros.get(idFiltro);

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

		Map<String, String> nombres = construirMapaDesdeFormulario(0);
		Map<String, String> resultados = construirMapaDesdeFormulario(1);

		return new VerificacionFirmasV0(id, nombres, resultados, criticalidad, prio, paraBuscar, filtro);
	}

	public String val(String lang, int idx) {
		JTextField[] arr = camposIdiomas.get(lang);
		return arr == null ? "" : nz(arr[idx].getText());
	}

	public void actualizarSeleccionado() {
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

	public void eliminarSeleccionado() {
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
			int nuevoIdx = Math.min(idx, modeloLista.size() - 1);
			lista.setSelectedIndex(nuevoIdx);
		}
		actualizarVistaJson();
	}

	public void guardarTodo() {
		try {
			for (int i = 0; i < modeloLista.size(); i++) {
				if (!validarVerificacion(modeloLista.get(i))) {
					JOptionPane.showMessageDialog(this, MonitorDePID.idioma.verificacionInvalida(),
							MonitorDePID.idioma.validacion(), JOptionPane.WARNING_MESSAGE);
					return;
				}
			}

			// Verificación pasiva del coreano ya normalizado como "ko"
			for (int i = 0; i < modeloLista.size(); i++) {
				VerificacionFirmasV0 v = modeloLista.get(i);

				String textoCoreano = v.resultados_por_codigo.get("ko");
				if (textoCoreano != null && !textoCoreano.trim().isEmpty()) {
					com.asbestosstar.crashdetector.idioma.cumplimiento.ActaDeProteccionDelIdiomaCulturalDePyongyang
							.contieneJergaSur(textoCoreano);
				}

				String nombreCoreano = v.nombres_por_codigo.get("ko");
				if (nombreCoreano != null && !nombreCoreano.trim().isEmpty()) {
					com.asbestosstar.crashdetector.idioma.cumplimiento.ActaDeProteccionDelIdiomaCulturalDePyongyang
							.contieneJergaSur(nombreCoreano);
				}
			}

			List<VerificacionFirmasV0> arr = new ArrayList<VerificacionFirmasV0>();
			for (int i = 0; i < modeloLista.size(); i++) {
				arr.add(modeloLista.get(i));
			}

			FirmasV0.guardar(arr);

			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.guardadoOk(), "OK",
					JOptionPane.INFORMATION_MESSAGE);

			actualizarVistaJson();

			// Al guardar, la verificación cargada en formulario debe reflejar el estado
			// actual
			int idx = lista.getSelectedIndex();
			if (idx >= 0 && idx < modeloLista.size()) {
				verificacionCargadaEnFormulario = modeloLista.get(idx);
			} else {
				verificacionCargadaEnFormulario = null;
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.error() + ": " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void exportarArchivo() {
		try {
			JFileChooser ch = new JFileChooser();
			ch.setSelectedFile(new File("codice.json"));
			if (ch.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				Files.write(ch.getSelectedFile().toPath(), vistaJson.getText().getBytes(StandardCharsets.UTF_8));
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.error_exportando() + ex.getMessage(),
					MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean validarVerificacion(VerificacionFirmasV0 v) {
		if (v == null) {
			return false;
		}

		if (isEmpty(v.id) || isEmpty(v.para_buscar)) {
			return false;
		}

		if (v.filtro == null) {
			return false;
		}

		if (v.criticalidad == null) {
			return false;
		}

		boolean hayAlMenosUnIdiomaCompleto = false;

		java.util.LinkedHashSet<String> codigos = new java.util.LinkedHashSet<String>();
		codigos.addAll(v.nombres_por_codigo.keySet());
		codigos.addAll(v.resultados_por_codigo.keySet());

		for (String codigo : codigos) {
			String nombre = v.nombres_por_codigo.get(codigo);
			String resultado = v.resultados_por_codigo.get(codigo);

			boolean nombreVacio = isEmpty(nombre);
			boolean resultadoVacio = isEmpty(resultado);

			if (nombreVacio && resultadoVacio) {
				continue;
			}

			if (nombreVacio || resultadoVacio) {
				return false;
			}

			hayAlMenosUnIdiomaCompleto = true;
		}

		return hayAlMenosUnIdiomaCompleto;
	}

	public void actualizarVistaJson() {
		try {
			List<VerificacionFirmasV0> arr = new ArrayList<VerificacionFirmasV0>();
			for (int i = 0; i < modeloLista.size(); i++) {
				arr.add(modeloLista.get(i));
			}
			vistaJson.setText(construirJsonPreview(arr));
			vistaJson.setCaretPosition(0);
		} catch (Exception ex) {
			vistaJson.setText("{ \"error\": \"" + ex.getMessage() + "\" }");
		}
	}

	public boolean hayCambiosNoGuardados() {
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

	public boolean confirmarDescartarCambios() {
		if (esFormularioModificado()) {
			int respuesta = JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.descartarCambios(),
					MonitorDePID.idioma.confirmacion(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			return respuesta == JOptionPane.YES_OPTION;
		}
		return true;
	}

	public String construirJsonPreview(List<VerificacionFirmasV0> arr) {
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
			List<String> clavesNombres = new ArrayList<String>(v.nombres_por_codigo.keySet());
			java.util.Collections.sort(clavesNombres);
			for (int j = 0; j < clavesNombres.size(); j++) {
				String codigo = clavesNombres.get(j);
				nombres(sb, codigo, v.nombres_por_codigo.get(codigo), 8);
				if (j < clavesNombres.size() - 1) {
					sb.append(",");
				}
				sb.append(nl);
			}
			sb.append(sp(6)).append("},").append(nl);

			sb.append(sp(6)).append("\"resultados\": {").append(nl);
			List<String> clavesResultados = new ArrayList<String>(v.resultados_por_codigo.keySet());
			java.util.Collections.sort(clavesResultados);
			for (int j = 0; j < clavesResultados.size(); j++) {
				String codigo = clavesResultados.get(j);
				nombres(sb, codigo, v.resultados_por_codigo.get(codigo), 8);
				if (j < clavesResultados.size() - 1) {
					sb.append(",");
				}
				sb.append(nl);
			}
			sb.append(sp(6)).append("}").append(nl);

			sb.append("    }");
			if (i < arr.size() - 1) {
				sb.append(",");
			}
			sb.append(nl);
		}

		sb.append("  ]").append(nl);
		sb.append("}").append(nl);

		return sb.toString();
	}

	public StringBuilder w(StringBuilder sb, String k, String v, int sp) {
		return sb.append(sp(sp)).append("\"").append(k).append("\": ").append("\"").append(escape(nz(v))).append("\"");
	}

	public StringBuilder wnum(StringBuilder sb, String k, int v, int sp) {
		return sb.append(sp(sp)).append("\"").append(k).append("\": ").append(v);
	}

	public StringBuilder nombres(StringBuilder sb, String k, String v, int sp) {
		return w(sb, k, v, sp);
	}

	public String sp(int n) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < n; i++)
			s.append(' ');
		return s.toString();
	}

	public String escape(String s) {
		return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
	}

	// -------------------- Helpers --------------------
	public boolean vacio(JTextField t) {
		return t.getText() == null || t.getText().trim().isEmpty();
	}

	public boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	public String nz(String s) {
		return s == null ? "" : s;
	}

	public void recargarTextosYEstilo() {
		int indiceSeleccion = (lista != null) ? lista.getSelectedIndex() : -1;

		String sId = fId != null ? fId.getText() : "";
		String sBuscar = fParaBuscar != null ? fParaBuscar.getText() : "";
		Object selFiltro = cbFiltro != null ? cbFiltro.getSelectedItem() : null;
		Object selCrit = cbCriticalidad != null ? cbCriticalidad.getSelectedItem() : null;
		int prio = spPrioridad != null ? ((Number) spPrioridad.getValue()).intValue() : 0;

		Map<String, String[]> snapshotIdiomas = new LinkedHashMap<String, String[]>();
		for (Map.Entry<String, JTextField[]> e : camposIdiomas.entrySet()) {
			JTextField[] arr = e.getValue();
			snapshotIdiomas.put(e.getKey(), new String[] { arr[0].getText(), arr[1].getText() });
		}

		getContentPane().removeAll();
		setTitle(MonitorDePID.idioma.tituloEditorCodice());
		add(crearEncabezado(), BorderLayout.NORTH);
		add(crearSplit(), BorderLayout.CENTER);

		if (indiceSeleccion >= 0 && indiceSeleccion < modeloLista.size()) {
			lista.setSelectedIndex(indiceSeleccion);
		}

		if (fId != null)
			fId.setText(sId);
		if (fParaBuscar != null)
			fParaBuscar.setText(sBuscar);
		if (cbFiltro != null && selFiltro != null)
			cbFiltro.setSelectedItem(selFiltro);
		if (cbCriticalidad != null && selCrit != null)
			cbCriticalidad.setSelectedItem(selCrit);
		if (spPrioridad != null)
			spPrioridad.setValue(prio);

		for (Map.Entry<String, String[]> e : snapshotIdiomas.entrySet()) {
			JTextField[] arr = camposIdiomas.get(e.getKey());
			if (arr != null) {
				arr[0].setText(e.getValue()[0]);
				arr[1].setText(e.getValue()[1]);
			}
		}

		if (btnNuevo != null)
			btnNuevo.setText(MonitorDePID.idioma.nuevo());
		if (btnActualizar != null)
			btnActualizar.setText(MonitorDePID.idioma.actualizarSeleccionado());
		if (btnEliminar != null)
			btnEliminar.setText(MonitorDePID.idioma.eliminarSeleccionado());
		if (btnExportar != null)
			btnExportar.setText(MonitorDePID.idioma.exportarJSON());
		if (btnGuardar != null)
			btnGuardar.setText(MonitorDePID.idioma.guardarTodo());

		actualizarVistaJson();

		revalidate();
		repaint();
	}

	public static class InfoIdiomaEditor {
		public final String codigo;
		public final String nombreVisible;
		public final String archivoBandera;

		public InfoIdiomaEditor(String codigo, String nombreVisible, String archivoBandera) {
			this.codigo = codigo;
			this.nombreVisible = nombreVisible;
			this.archivoBandera = archivoBandera;
		}
	}

	public final List<InfoIdiomaEditor> idiomasEditor = new ArrayList<InfoIdiomaEditor>();

	public void reconstruirIdiomasEditor() {
		idiomasEditor.clear();

		LinkedHashMap<String, String> mapa = Idioma.mapaParaComboBoxIdiomas();
		for (Map.Entry<String, String> e : mapa.entrySet()) {
			String nombreVisible = e.getKey();
			String ruta = e.getValue();

			String codigo = Idioma.codigoDesdeNombreVisible(nombreVisible);
			if (codigo == null || codigo.trim().isEmpty()) {
				continue;
			}

			String archivoBandera = null;
			if (ruta != null) {
				int idx = ruta.lastIndexOf('/');
				archivoBandera = idx >= 0 ? ruta.substring(idx + 1) : ruta;
			}

			idiomasEditor.add(new InfoIdiomaEditor(VerificacionFirmasV0.normalizarCodigoIdioma(codigo), nombreVisible,
					archivoBandera));
		}

		idiomasEditor.sort((a, b) -> a.codigo.compareToIgnoreCase(b.codigo));
	}

	public Map<String, String> construirMapaDesdeFormulario(int indiceCampo) {
		Map<String, String> mapa = new LinkedHashMap<String, String>();

		for (Map.Entry<String, JTextField[]> e : camposIdiomas.entrySet()) {
			String codigo = VerificacionFirmasV0.normalizarCodigoIdioma(e.getKey());
			JTextField[] campos = e.getValue();

			if (codigo == null || campos == null || indiceCampo < 0 || indiceCampo >= campos.length) {
				continue;
			}

			String valor = nz(campos[indiceCampo].getText());
			if (!valor.trim().isEmpty()) {
				mapa.put(codigo, valor);
			}
		}

		return mapa;
	}

}