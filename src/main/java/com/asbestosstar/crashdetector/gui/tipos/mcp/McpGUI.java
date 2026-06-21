package com.asbestosstar.crashdetector.gui.tipos.mcp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.deps.DescargadorDependenciasMaven;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * GUI base para controlar un servidor MCP local.
 *
 * Esta clase contiene la estructura y las funciones. La implementación concreta
 * solo debe aportar colores, imagen y apariencia.
 */
public abstract class McpGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static final Map<String, Supplier<McpGUI>> GUIS = new HashMap<String, Supplier<McpGUI>>();

	public JPanel panelRaiz;
	public JPanel panelDescripcion;
	public JPanel panelControles;
	public JPanel panelPuerto;
	public JPanel panelBotones;
	public JPanel panelImagen;

	public JLabel lblTitulo;
	public JLabel lblPuerto;
	public JLabel lblEstado;

	public JEditorPane txtDescripcion;
	public JTextField txtPuerto;

	public JButton btnDescargarDeps;
	public JButton btnIniciarServidor;

	public volatile Object servidorActual;

	@Override
	public TipoGUI tipo() {
		return TipoGUI.MCP;
	}

	@Override
	public void init() {
		configurarVentana();
		construirInterfaz();
		actualizarEstadoDependencias();
		aplicarApariencia();
		setVisible(true);
	}

	public void configurarVentana() {
		setTitle(MonitorDePID.idioma.mcpTituloVentana());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(860, 560);
		setMinimumSize(new Dimension(720, 460));
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
	}

	public void construirInterfaz() {
		panelRaiz = new JPanel(new BorderLayout(12, 12));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		panelDescripcion = new JPanel(new BorderLayout(8, 8));
		panelControles = new JPanel(new BorderLayout(8, 8));
		panelPuerto = new JPanel(new BorderLayout(6, 6));
		panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));

		lblTitulo = new JLabel(MonitorDePID.idioma.mcpTituloPrincipal());
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 22f));

		txtDescripcion = new JEditorPane();
		txtDescripcion.setEditable(false);
		txtDescripcion.setContentType("text/html");
		txtDescripcion.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		txtDescripcion.setText(envolverHtml(MonitorDePID.idioma.mcpDescripcionHtml()));

		lblPuerto = new JLabel(MonitorDePID.idioma.mcpPuerto());
		txtPuerto = new JTextField("8765");

		btnDescargarDeps = new JButton(MonitorDePID.idioma.mcpDescargarDependencias());
		btnIniciarServidor = new JButton(MonitorDePID.idioma.mcpIniciarServidor());

		lblEstado = new JLabel(MonitorDePID.idioma.mcpEstadoDependenciasNoCargadas());

		btnDescargarDeps.addActionListener(e -> descargarDependencias());
		btnIniciarServidor.addActionListener(e -> iniciarServidor());

		panelDescripcion.add(lblTitulo, BorderLayout.NORTH);
		panelDescripcion.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);

		panelPuerto.add(lblPuerto, BorderLayout.WEST);
		panelPuerto.add(txtPuerto, BorderLayout.CENTER);

		panelBotones.add(btnDescargarDeps);
		panelBotones.add(btnIniciarServidor);

		panelControles.add(panelPuerto, BorderLayout.NORTH);
		panelControles.add(panelBotones, BorderLayout.CENTER);
		panelControles.add(lblEstado, BorderLayout.SOUTH);

		panelImagen = crearPanelImagen();

		JPanel panelInferior = new JPanel(new BorderLayout(12, 12));
		panelInferior.add(panelImagen, BorderLayout.WEST);
		panelInferior.add(panelControles, BorderLayout.CENTER);

		panelRaiz.add(panelDescripcion, BorderLayout.CENTER);
		panelRaiz.add(panelInferior, BorderLayout.SOUTH);

		add(panelRaiz, BorderLayout.CENTER);
	}

	public String envolverHtml(String cuerpo) {
		return "<html><body style='font-family:sans-serif; font-size:12px;'>" + cuerpo + "</body></html>";
	}

	public void actualizarEstadoDependencias() {
		boolean disponibles = McpServidorBasicoOpcional.dependenciasDisponibles();

		if (btnIniciarServidor != null) {
			btnIniciarServidor.setEnabled(disponibles);
		}

		if (lblEstado != null) {
			if (disponibles) {
				lblEstado.setText(MonitorDePID.idioma.mcpEstadoDependenciasCargadas() + " ["
						+ McpServidorBasicoOpcional.diagnosticoDependencias() + "]");
			} else {
				lblEstado.setText(MonitorDePID.idioma.mcpEstadoDependenciasNoCargadas() + " ["
						+ McpServidorBasicoOpcional.diagnosticoDependencias() + "]");
			}
		}
	}

	public void descargarDependencias() {
		if (btnDescargarDeps != null) {
			btnDescargarDeps.setEnabled(false);
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					List<DescargadorDependenciasMaven.CoordenadaMaven> coordenadas = new ArrayList<DescargadorDependenciasMaven.CoordenadaMaven>();

					coordenadas.add(new DescargadorDependenciasMaven.CoordenadaMaven("io.modelcontextprotocol.sdk",
							"mcp", "1.1.3"));

					coordenadas.add(new DescargadorDependenciasMaven.CoordenadaMaven("org.benf", "cfr", "0.152"));

					DescargadorDependenciasMaven.descargarDependencias(coordenadas);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(McpGUI.this,
									MonitorDePID.idioma.mcpDependenciasDescargadasReiniciar(),
									MonitorDePID.idioma.mcpTituloVentana(), JOptionPane.INFORMATION_MESSAGE);
							actualizarEstadoDependencias();
						}
					});
				} catch (final Throwable t) {
					CrashDetectorLogger.logException(t);
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(McpGUI.this,
									MonitorDePID.idioma.mcpErrorDescargandoDependencias(t.getMessage()),
									MonitorDePID.idioma.mcpTituloVentana(), JOptionPane.ERROR_MESSAGE);
						}
					});
				} finally {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							if (btnDescargarDeps != null) {
								btnDescargarDeps.setEnabled(true);
							}
							actualizarEstadoDependencias();
						}
					});
				}
			}
		}, "MCP-Descargar-Dependencias").start();
	}

	public void iniciarServidor() {
		try {
			int puerto = Integer.parseInt(txtPuerto.getText().trim());
			if (puerto <= 0 || puerto > 65535) {
				throw new NumberFormatException("puerto fuera de rango");
			}

			servidorActual = McpServidorBasicoOpcional.iniciar(puerto);

			lblEstado.setText(MonitorDePID.idioma.mcpServidorIniciado(puerto));
			btnIniciarServidor.setEnabled(false);
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.mcpErrorIniciandoServidor(t.getMessage()),
					MonitorDePID.idioma.mcpTituloVentana(), JOptionPane.ERROR_MESSAGE);
		}
	}

	public abstract JPanel crearPanelImagen();

	public abstract void aplicarApariencia();

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
		repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		return new ArrayList<ElementoConfig>();
	}
}