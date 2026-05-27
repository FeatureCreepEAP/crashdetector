package com.asbestosstar.crashdetector.gui.tipos.rendimiento;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.general.VerificacionGPU;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.elementos.ElementoOverlayCarga;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.gpu.GPUFixGUI;
import com.asbestosstar.crashdetector.gui.tipos.gpu.GPUFixOptimusPrime;
import com.asbestosstar.crashdetector.optimizacion.AnalizadorDeConfigsOptimizacion;
import com.asbestosstar.crashdetector.optimizacion.AnalizadorDeFactoresAmbientales;
import com.asbestosstar.crashdetector.optimizacion.AnalizadorDeModsOptimizacion;

public abstract class AdministradorDeRendimientoGUI extends JFrame implements BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static Map<String, Supplier<AdministradorDeRendimientoGUI>> GUIS = new HashMap<>();

	public JLabel imagenNightcore;
	public JTextArea areaDescripcion;
	public JButton botonAnalizar;
	public JButton botonAbrirGPU;

	public JTabbedPane pestanas;
	public JTextArea areaResumen;
	public JTextArea areaAmbiental;
	public JTextArea areaMods;
	public JTextArea areaConfigs;

	public ElementoOverlayCarga overlayCarga;
	public SwingWorker<Void, Void> trabajadorAnalisis;
	public boolean inicializada = false;

	@Override
	public void init() {
		if (inicializada) {
			recargarApariencia();
			setVisible(true);
			toFront();
			requestFocus();
			return;
		}

		inicializada = true;

		setTitle(MonitorDePID.idioma.rendimientoTitulo());
		setSize(1180, 760);
		setMinimumSize(new Dimension(900, 600));
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout(8, 8));

		JPanel panelSuperior = new JPanel(new BorderLayout(8, 8));

		imagenNightcore = new JLabel("", JLabel.CENTER);
		imagenNightcore.setPreferredSize(new Dimension(260, 150));

		areaDescripcion = crearAreaTexto();
		areaDescripcion.setText(MonitorDePID.idioma.rendimientoDescripcion());

		JPanel panelBotones = new JPanel();
		botonAnalizar = new JButton(MonitorDePID.idioma.rendimientoBotonAnalizar());
		botonAbrirGPU = new JButton(MonitorDePID.idioma.rendimientoBotonAbrirGPU());

		panelBotones.add(botonAnalizar);

		if (VerificacionGPU.hayProblema) {
			panelBotones.add(botonAbrirGPU);
		}

		panelSuperior.add(imagenNightcore, BorderLayout.WEST);
		panelSuperior.add(areaDescripcion, BorderLayout.CENTER);
		panelSuperior.add(panelBotones, BorderLayout.SOUTH);

		pestanas = new JTabbedPane();

		areaResumen = crearAreaTexto();
		areaAmbiental = crearAreaTexto();
		areaMods = crearAreaTexto();
		areaConfigs = crearAreaTexto();

		pestanas.addTab(MonitorDePID.idioma.rendimientoPestanaResumen(), new JScrollPane(areaResumen));
		pestanas.addTab(MonitorDePID.idioma.rendimientoPestanaAmbiental(), new JScrollPane(areaAmbiental));
		pestanas.addTab(MonitorDePID.idioma.rendimientoPestanaMods(), new JScrollPane(areaMods));
		pestanas.addTab(MonitorDePID.idioma.rendimientoPestanaConfigs(), new JScrollPane(areaConfigs));

		add(panelSuperior, BorderLayout.NORTH);
		add(pestanas, BorderLayout.CENTER);

		botonAnalizar.addActionListener(e -> analizarAsync());
		botonAbrirGPU.addActionListener(e -> abrirGPUFix());

		initOverlayCarga();
		recargarApariencia();
		analizarAsync();

		setVisible(true);
	}

	public JTextArea crearAreaTexto() {
		JTextArea area = new JTextArea();
		area.setEditable(false);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		return area;
	}

	public void analizarAsync() {
		if (trabajadorAnalisis != null && !trabajadorAnalisis.isDone()) {
			trabajadorAnalisis.cancel(true);
		}

		setCargando(true);

		trabajadorAnalisis = new SwingWorker<Void, Void>() {
			private List<AnalizadorDeFactoresAmbientales.MejoraAmbiental> mejorasAmbientales;
			private List<AnalizadorDeModsOptimizacion.MejoraMod> mejorasMods;
			private List<AnalizadorDeConfigsOptimizacion.MejoraConfig> mejorasConfigs;

			@Override
			protected Void doInBackground() {
				try {
					mejorasAmbientales = AnalizadorDeFactoresAmbientales.obtenerMejorasPotenciales();
					mejorasMods = AnalizadorDeModsOptimizacion.obtenerMejorasPotenciales();
					mejorasConfigs = AnalizadorDeConfigsOptimizacion.obtenerMejorasPotenciales();
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
				}

				return null;
			}

			@Override
			protected void done() {
				try {
					if (isCancelled()) {
						return;
					}

					renderizar(mejorasAmbientales, mejorasMods, mejorasConfigs);
				} finally {
					setCargando(false);
				}
			}
		};

		trabajadorAnalisis.execute();
	}

	public void renderizar(List<AnalizadorDeFactoresAmbientales.MejoraAmbiental> mejorasAmbientales,
			List<AnalizadorDeModsOptimizacion.MejoraMod> mejorasMods,
			List<AnalizadorDeConfigsOptimizacion.MejoraConfig> mejorasConfigs) {

		areaResumen.setText(construirResumen(mejorasAmbientales, mejorasMods, mejorasConfigs));
		areaAmbiental.setText(construirAmbiental(mejorasAmbientales));
		areaMods.setText(construirMods(mejorasMods));
		areaConfigs.setText(construirConfigs(mejorasConfigs));
	}

	public String construirResumen(List<AnalizadorDeFactoresAmbientales.MejoraAmbiental> mejorasAmbientales,
			List<AnalizadorDeModsOptimizacion.MejoraMod> mejorasMods,
			List<AnalizadorDeConfigsOptimizacion.MejoraConfig> mejorasConfigs) {

		StringBuilder sb = new StringBuilder();

		sb.append(MonitorDePID.idioma.rendimientoResumenTitulo()).append("\n\n");
		sb.append(MonitorDePID.idioma.rendimientoResumenAmbiental(contar(mejorasAmbientales))).append("\n");
		sb.append(MonitorDePID.idioma.rendimientoResumenMods(contar(mejorasMods))).append("\n");
		sb.append(MonitorDePID.idioma.rendimientoResumenConfigs(contar(mejorasConfigs))).append("\n\n");

		if (VerificacionGPU.hayProblema) {
			sb.append(MonitorDePID.idioma.rendimientoResumenGPU()).append("\n\n");
		}

		sb.append(MonitorDePID.idioma.rendimientoNotaCompatibilidad()).append("\n");

		return sb.toString();
	}

	public String construirAmbiental(List<AnalizadorDeFactoresAmbientales.MejoraAmbiental> mejoras) {
		StringBuilder sb = new StringBuilder();

		sb.append(MonitorDePID.idioma.rendimientoPestanaAmbiental()).append("\n\n");

		if (mejoras == null || mejoras.isEmpty()) {
			sb.append(MonitorDePID.idioma.rendimientoSinHallazgos()).append("\n");
			return sb.toString();
		}

		for (AnalizadorDeFactoresAmbientales.MejoraAmbiental mejora : mejoras) {
			sb.append("[").append(mejora.impacto).append(" / ").append(mejora.riesgo).append("] ");
			sb.append(mejora.titulo).append("\n");
			sb.append(mejora.descripcion).append("\n");
			sb.append(MonitorDePID.idioma.rendimientoSugerencia()).append(": ").append(mejora.sugerencia)
					.append("\n\n");
		}

		return sb.toString();
	}

	public String construirMods(List<AnalizadorDeModsOptimizacion.MejoraMod> mejoras) {
		StringBuilder sb = new StringBuilder();

		sb.append(MonitorDePID.idioma.rendimientoPestanaMods()).append("\n\n");

		if (mejoras == null || mejoras.isEmpty()) {
			sb.append(MonitorDePID.idioma.rendimientoSinHallazgos()).append("\n");
			return sb.toString();
		}

		for (AnalizadorDeModsOptimizacion.MejoraMod mejora : mejoras) {
			sb.append("[").append(mejora.impacto).append(" / ").append(mejora.riesgo).append("] ");
			sb.append(mejora.modId).append(" - ").append(mejora.titulo).append("\n");
			sb.append(mejora.descripcion).append("\n\n");
		}

		return sb.toString();
	}

	public String construirConfigs(List<AnalizadorDeConfigsOptimizacion.MejoraConfig> mejoras) {
		StringBuilder sb = new StringBuilder();

		sb.append(MonitorDePID.idioma.rendimientoPestanaConfigs()).append("\n\n");

		if (mejoras == null || mejoras.isEmpty()) {
			sb.append(MonitorDePID.idioma.rendimientoSinHallazgos()).append("\n");
			return sb.toString();
		}

		for (AnalizadorDeConfigsOptimizacion.MejoraConfig mejora : mejoras) {
			sb.append("[").append(mejora.impacto).append(" / ").append(mejora.riesgo).append("] ");
			sb.append(mejora.archivo).append(" - ").append(mejora.titulo).append("\n");
			sb.append(mejora.descripcion).append("\n");
			sb.append(MonitorDePID.idioma.rendimientoSugerencia()).append(": ").append(mejora.sugerencia)
					.append("\n\n");
		}

		return sb.toString();
	}

	public int contar(List<?> lista) {
		return lista == null ? 0 : lista.size();
	}

	public void abrirGPUFix() {
		GPUFixGUI gui = TipoGUI.GPU_FIX.obtenerGUIPredeterminado(GPUFixOptimusPrime.ID, GPUFixOptimusPrime::new);
		gui.init();
	}

	public void initOverlayCarga() {
		overlayCarga = new ElementoOverlayCarga();
		overlayCarga.setVisible(false);
		setGlassPane(overlayCarga);
	}

	public void setCargando(boolean cargando) {
		if (overlayCarga != null) {
			overlayCarga.setVisible(cargando);
			overlayCarga.revalidate();
			overlayCarga.repaint();
		}

		if (botonAnalizar != null) {
			botonAnalizar.setEnabled(!cargando);
		}

		if (botonAbrirGPU != null) {
			botonAbrirGPU.setEnabled(!cargando);
		}
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.ADMINISTRADOR_DE_RENDIMIENTO;
	}
}