package com.asbestosstar.crashdetector.gui.tipos.migrador;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.deshablicarverificaciones.DeshabilitarVerificacionesAmaneKanata;
import com.asbestosstar.crashdetector.gui.tipos.migrador.crashassistant.MigracionCrashAssistant;

public class AsistenteCrashAssistant extends AsistenteMigracionLegacy {

	private static final long serialVersionUID = 1L;

	public static final String ID = "crash_assistant";

	public MigracionCrashAssistant migracion = new MigracionCrashAssistant();


	public JCheckBox checkUsarPrimitiva;

	
	
	
	public JLabel etiqueta;
	public JLabel avisoNoMigrado;
	public JLabel avisoModoPrincipal;
	public JLabel avisoSitioLogging;

	public JComboBox<String> comboSitioLogging;

	public JButton botonMigrar;
	public JButton botonDeshabilitarChecks;
	
	

	@Override
	public String id() {
		return ID;
	}

	@Override
	public TipoGUI<?> tipo() {
		return TipoGUI.MIGRADOR_LEGACY;
	}

	@Override
	public String nombreVisible() {
		return MonitorDePID.idioma.migradorLegacyCrashAssistant();
	}

	@Override
	public void init() {
		setTitle(nombreVisible());
		setSize(900, 768);
		setModal(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(8, 8));

		etiqueta = new JLabel("<html>" + MonitorDePID.idioma.migradorCrashAssistantDescripcion() + "</html>");

		avisoModoPrincipal = new JLabel("<html>" + MonitorDePID.idioma.migradorCAModoPrincipalAviso() + "</html>");

		comboModoPrincipal = new JComboBox<String>(new String[] {
				MigracionCrashAssistant.MODO_ORIGINAL,
				MigracionCrashAssistant.MODO_CENTRO_SOPORTE,
				MigracionCrashAssistant.MODO_PRIMITIVA
		});
		comboModoPrincipal.setSelectedItem(MigracionCrashAssistant.MODO_ORIGINAL);

		avisoSitioLogging = new JLabel("<html>" + MonitorDePID.idioma.migradorCASitioLoggingAviso() + "</html>");

		comboSitioLogging = new JComboBox<String>(new String[] {
				MigracionCrashAssistant.LOGGING_MANTENER,
				MigracionCrashAssistant.LOGGING_MIGRAR_CA
		});
		comboSitioLogging.setSelectedItem(MigracionCrashAssistant.LOGGING_MANTENER);

		avisoNoMigrado = new JLabel("<html>" + MonitorDePID.idioma.migradorCAAvisoNoMigrado() + "</html>");

		botonMigrar = new JButton(MonitorDePID.idioma.migradorEjecutar());
		botonDeshabilitarChecks = new JButton(MonitorDePID.idioma.migradorCADeshabilitarChecks());

		botonMigrar.addActionListener(e -> {
			boolean ok = ejecutarMigracion();
			JOptionPane.showMessageDialog(this,
					ok ? MonitorDePID.idioma.migradorCompletado()
							: MonitorDePID.idioma.migradorNadaParaMigrar());
		});

		botonDeshabilitarChecks.addActionListener(e -> {
			TipoGUI.DESHABLICAR_VERIFICACIONES
					.obtenerGUIPredeterminado(
							DeshabilitarVerificacionesAmaneKanata.ID,
							() -> new DeshabilitarVerificacionesAmaneKanata())
					.init();
		});

		JPanel panelCentro = new JPanel(new GridLayout(0, 1, 6, 6));
		panelCentro.add(etiqueta);
		panelCentro.add(avisoModoPrincipal);
		panelCentro.add(comboModoPrincipal);
		panelCentro.add(avisoSitioLogging);
		panelCentro.add(comboSitioLogging);
		panelCentro.add(avisoNoMigrado);

		JPanel panelBotones = new JPanel(new GridLayout(1, 2, 6, 6));
		panelBotones.add(botonMigrar);
		panelBotones.add(botonDeshabilitarChecks);

		JPanel panel = new JPanel(new BorderLayout(8, 8));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(panelCentro, BorderLayout.CENTER);
		panel.add(panelBotones, BorderLayout.SOUTH);

		add(panel, BorderLayout.CENTER);
		setVisible(true);
	}

	@Override
	public boolean ejecutarMigracion() {
		boolean mods = migracion.migrarProblematicMods();

		String modo = (String) comboModoPrincipal.getSelectedItem();
		if (modo == null || modo.trim().isEmpty()) {
			modo = MigracionCrashAssistant.MODO_ORIGINAL;
		}

		String sitio = (String) comboSitioLogging.getSelectedItem();
		boolean migrarSitioLogging = MigracionCrashAssistant.LOGGING_MIGRAR_CA.equals(sitio);

		boolean toml = migracion.migrarConfigToml(modo, migrarSitioLogging);

		return mods || toml;
	}

	@Override
	public void recargarApariencia() {
	}
	
	public JComboBox<String> comboModoPrincipal;
	public JCheckBox checkMigrarSitioLogging;



	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		return new ArrayList<ElementoConfig>();
	}
}