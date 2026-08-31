package com.asbestosstar.crashdetector.gui.tipos.waifu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.waifu.ConsultasWaifu;
import com.asbestosstar.crashdetector.waifu.WaifuAPI;

/**
 * GUI funcional para explorar NeoForge Waifu.
 *
 * Esta clase contiene la lógica, los componentes y la ejecución de consultas.
 * La apariencia concreta se mantiene en una implementación separada para seguir
 * el patrón de TipoGUI usado por CrashDetector.
 */
public abstract class NeoForgeWaifuGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static final Map<String, Supplier<NeoForgeWaifuGUI>> GUIS = new java.util.HashMap<String, Supplier<NeoForgeWaifuGUI>>();

	public final ConfigBoolean mostrarEditorAvanzado = ConfigBoolean.de("gui.neoforge_waifu.mostrar_editor_avanzado",
			false);

	public JPanel panelRaiz;
	public JPanel panelCabecera;
	public JPanel panelTrabajo;
	public JPanel panelControles;
	public JPanel panelConsultaAvanzada;
	public JPanel panelBotones;
	public JPanel panelMascota;

	public JLabel etiquetaTitulo;
	public JLabel etiquetaTipoConsulta;
	public JLabel etiquetaVariables;
	public JLabel etiquetaOperacion;
	public JLabel etiquetaConsulta;

	public JTextArea areaDescripcion;
	public JComboBox<String> comboConsulta;
	public JTextArea areaVariables;
	public JTextField campoOperacion;
	public JTextArea areaConsulta;
	public JTextArea areaResultados;

	public JCheckBox checkEditorAvanzado;
	public JButton botonEjecutar;
	public JButton botonCopiar;
	public JButton botonCerrar;

	public JScrollPane scrollVariables;
	public JScrollPane scrollConsulta;
	public JScrollPane scrollResultados;

	private boolean actualizandoControles;

	protected NeoForgeWaifuGUI() {
		mostrarEditorAvanzado
				.establecerNombreParaMostrar(() -> MonitorDePID.idioma.neoforgeWaifuMostrarEditorAvanzado());
	}

	@Override
	public final TipoGUI<NeoForgeWaifuGUI> tipo() {
		return TipoGUI.NEOFORGE_WAIFU;
	}

	@Override
	public void init() {
		configurarVentana();
		construirInterfaz();
		aplicarApariencia();
		setVisible(true);
	}

	/**
	 * Configura solamente propiedades funcionales de la ventana.
	 */
	protected void configurarVentana() {
		setTitle(MonitorDePID.idioma.neoforgeWaifuTitulo());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1040, 760);
		setMinimumSize(new Dimension(820, 620));
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
	}

	/**
	 * Construye la interfaz sin fijar colores.
	 */
	protected void construirInterfaz() {
		panelRaiz = new JPanel(new BorderLayout(10, 10));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		panelCabecera = new JPanel(new BorderLayout(8, 8));
		etiquetaTitulo = new JLabel(MonitorDePID.idioma.neoforgeWaifuTitulo());
		etiquetaTitulo.setFont(etiquetaTitulo.getFont().deriveFont(Font.BOLD, 22f));

		areaDescripcion = new JTextArea(MonitorDePID.idioma.neoforgeWaifuDescripcion());
		areaDescripcion.setEditable(false);
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setWrapStyleWord(true);
		areaDescripcion.setOpaque(false);

		panelCabecera.add(etiquetaTitulo, BorderLayout.NORTH);
		panelCabecera.add(areaDescripcion, BorderLayout.CENTER);

		panelTrabajo = new JPanel(new BorderLayout(10, 10));
		panelMascota = crearPanelMascota();

		JPanel panelConsultas = new JPanel(new BorderLayout(8, 8));
		panelControles = crearPanelControles();
		panelConsultaAvanzada = crearPanelConsultaAvanzada();

		JPanel panelVariables = new JPanel(new BorderLayout(4, 4));
		etiquetaVariables = new JLabel(MonitorDePID.idioma.neoforgeWaifuVariablesJson());
		areaVariables = new JTextArea(8, 50);
		areaVariables.setLineWrap(true);
		areaVariables.setWrapStyleWord(true);
		scrollVariables = new JScrollPane(areaVariables);
		panelVariables.add(etiquetaVariables, BorderLayout.NORTH);
		panelVariables.add(scrollVariables, BorderLayout.CENTER);

		JPanel panelSuperiorConsultas = new JPanel(new BorderLayout(8, 8));
		panelSuperiorConsultas.add(panelControles, BorderLayout.NORTH);
		panelSuperiorConsultas.add(panelVariables, BorderLayout.CENTER);
		panelSuperiorConsultas.add(panelConsultaAvanzada, BorderLayout.SOUTH);

		areaResultados = new JTextArea();
		areaResultados.setEditable(false);
		areaResultados.setLineWrap(true);
		areaResultados.setWrapStyleWord(true);
		scrollResultados = new JScrollPane(areaResultados);
		scrollResultados.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.resultadosDeBusqueda()));

		JSplitPane divisor = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelSuperiorConsultas, scrollResultados);
		divisor.setResizeWeight(0.55);

		panelConsultas.add(divisor, BorderLayout.CENTER);
		panelTrabajo.add(panelMascota, BorderLayout.WEST);
		panelTrabajo.add(panelConsultas, BorderLayout.CENTER);

		panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
		botonEjecutar = new JButton(MonitorDePID.idioma.neoforgeWaifuEjecutar());
		botonCopiar = new JButton(MonitorDePID.idioma.copiarTodo());
		botonCerrar = new JButton(MonitorDePID.idioma.cerrar());

		botonEjecutar.addActionListener(e -> ejecutarConsulta());
		botonCopiar.addActionListener(e -> copiarResultados());
		botonCerrar.addActionListener(e -> dispose());

		panelBotones.add(botonEjecutar);
		panelBotones.add(botonCopiar);
		panelBotones.add(botonCerrar);

		panelRaiz.add(panelCabecera, BorderLayout.NORTH);
		panelRaiz.add(panelTrabajo, BorderLayout.CENTER);
		panelRaiz.add(panelBotones, BorderLayout.SOUTH);
		add(panelRaiz, BorderLayout.CENTER);

		cargarTiposConsulta();
	}

	private JPanel crearPanelControles() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 6);
		gbc.anchor = GridBagConstraints.WEST;

		etiquetaTipoConsulta = new JLabel(MonitorDePID.idioma.neoforgeWaifuTipoConsulta());
		comboConsulta = new JComboBox<String>();
		comboConsulta.setPreferredSize(new Dimension(260, 30));

		checkEditorAvanzado = new JCheckBox(MonitorDePID.idioma.neoforgeWaifuMostrarEditorAvanzado(),
				mostrarEditorAvanzado.obtener().booleanValue());

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(etiquetaTipoConsulta, gbc);

		gbc.gridx = 1;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(comboConsulta, gbc);

		gbc.gridx = 2;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(checkEditorAvanzado, gbc);

		comboConsulta.addActionListener(e -> {
			if (!actualizandoControles) {
				aplicarConsultaSeleccionada();
			}
		});

		checkEditorAvanzado.addActionListener(e -> {
			try {
				mostrarEditorAvanzado.escribir(Boolean.valueOf(checkEditorAvanzado.isSelected()));
			} catch (Exception ex) {
				CrashDetectorLogger.logException(ex);
			}
			actualizarVisibilidadEditor();
		});

		return panel;
	}

	private JPanel crearPanelConsultaAvanzada() {
		JPanel panel = new JPanel(new BorderLayout(4, 4));

		JPanel filaOperacion = new JPanel(new BorderLayout(6, 0));
		etiquetaOperacion = new JLabel(MonitorDePID.idioma.neoforgeWaifuNombreOperacion());
		campoOperacion = new JTextField();
		filaOperacion.add(etiquetaOperacion, BorderLayout.WEST);
		filaOperacion.add(campoOperacion, BorderLayout.CENTER);

		etiquetaConsulta = new JLabel(MonitorDePID.idioma.neoforgeWaifuConsultaGraphql());
		areaConsulta = new JTextArea(12, 50);
		areaConsulta.setLineWrap(false);
		scrollConsulta = new JScrollPane(areaConsulta);

		JPanel contenido = new JPanel(new BorderLayout(4, 4));
		contenido.add(etiquetaConsulta, BorderLayout.NORTH);
		contenido.add(scrollConsulta, BorderLayout.CENTER);

		panel.add(filaOperacion, BorderLayout.NORTH);
		panel.add(contenido, BorderLayout.CENTER);
		return panel;
	}

	private void cargarTiposConsulta() {
		actualizandoControles = true;
		try {
			comboConsulta.removeAllItems();
			for (String id : ConsultasWaifu.ids()) {
				comboConsulta.addItem(id);
			}
			if (comboConsulta.getItemCount() > 0) {
				comboConsulta.setSelectedIndex(0);
			}
		} finally {
			actualizandoControles = false;
		}

		aplicarConsultaSeleccionada();
		actualizarVisibilidadEditor();
	}

	/**
	 * Carga el documento GraphQL y las variables de ejemplo del preset.
	 */
	public void aplicarConsultaSeleccionada() {
		String id = (String) comboConsulta.getSelectedItem();
		if (id == null) {
			return;
		}

		ConsultasWaifu.ConsultaPredefinida consulta = ConsultasWaifu.obtener(id);
		if (consulta == null) {
			return;
		}

		if (!ConsultasWaifu.ID_RAW.equals(id)) {
			campoOperacion.setText(consulta.nombreOperacion);
			areaConsulta.setText(consulta.graphql);
			areaVariables.setText(consulta.variablesEjemplo);
			areaConsulta.setCaretPosition(0);
			areaVariables.setCaretPosition(0);
		} else {
			checkEditorAvanzado.setSelected(true);
		}

		actualizarVisibilidadEditor();
	}

	private void actualizarVisibilidadEditor() {
		boolean mostrar = checkEditorAvanzado != null && checkEditorAvanzado.isSelected();

		if (comboConsulta != null && ConsultasWaifu.ID_RAW.equals(comboConsulta.getSelectedItem())) {
			mostrar = true;
		}

		if (panelConsultaAvanzada != null) {
			panelConsultaAvanzada.setVisible(mostrar);
			panelConsultaAvanzada.revalidate();
		}
	}

	/**
	 * Ejecuta la consulta en segundo plano para no bloquear Swing.
	 */
	public void ejecutarConsulta() {
		final String consulta = areaConsulta.getText();
		final String operacion = campoOperacion.getText();
		final String variables = areaVariables.getText();

		if (!WaifuAPI.esConsultaGraphQLSoloLectura(consulta)) {
			areaResultados.setText(MonitorDePID.idioma.neoforgeWaifuSoloLectura());
			areaResultados.setCaretPosition(0);
			return;
		}

		botonEjecutar.setEnabled(false);

		SwingWorker<String, Void> trabajador = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() throws Exception {
				return WaifuAPI.ejecutarConsultaGraphQL(consulta, operacion, variables);
			}

			@Override
			protected void done() {
				try {
					areaResultados.setText(get());
					areaResultados.setCaretPosition(0);
				} catch (Exception ex) {
					Throwable causa = ex.getCause() == null ? ex : ex.getCause();
					areaResultados.setText(MonitorDePID.idioma.error() + ": " + String.valueOf(causa.getMessage()));
					CrashDetectorLogger.logException(causa);
				} finally {
					botonEjecutar.setEnabled(true);
				}
			}
		};

		trabajador.execute();
	}

	public void copiarResultados() {
		try {
			StringSelection seleccion = new StringSelection(areaResultados.getText());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(seleccion, null);
		} catch (Exception ex) {
			CrashDetectorLogger.logException(ex);
		}
	}

	/**
	 * La implementación decorada crea el área reservada para la mascota.
	 */
	protected abstract JPanel crearPanelMascota();

	/**
	 * La implementación decorada aplica ConfigColor y demás apariencia.
	 */
	protected abstract void aplicarApariencia();

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
		repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();
		elementos.add(mostrarEditorAvanzado);
		return elementos;
	}
}
