package com.asbestosstar.crashdetector.gui.tipos.mcreator;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.asbestosstar.crashdetector.EscanerMCreator;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class EscanerMCreatorGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	// Mantener el registro de GUIS
	public static Map<String, Supplier<EscanerMCreatorGUI>> GUIS = new HashMap<>();

	// ====== Componentes técnicos ======
	protected JTextArea areaResultados;
	protected JLabel etiquetaEstado;
	protected JButton botonEscanear;

	// Contenedores
	protected JPanel panelContenido;
	protected JPanel panelContenidoConImagen;
	protected JScrollPane panelDesplazamiento;

	// ====== Constructor ======
	public EscanerMCreatorGUI() {
		super();
	}

	// ====== Métodos auxiliares para aplicación de colores ======
	protected void aplicarColor(JLabel label, ConfigColor color) {
		label.setForeground(color.obtener());
	}

	protected void aplicarFondo(JComponent component, ConfigColor color) {
		component.setBackground(color.obtener());
		component.setOpaque(true);
	}

	// ====== Lógica técnica del escaneo ======
	protected void iniciarEscaneo() {
		areaResultados.setText("");
		etiquetaEstado.setText(textoEstadoCargando());
		botonEscanear.setEnabled(false);

		new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() {
				return EscanerMCreator.obtainerMCreatorMods();
			}

			@Override
			protected void done() {
				try {
					String resultado = get();
					areaResultados.setText(resultado);
					etiquetaEstado.setText(textoEstadoCompletado());
				} catch (Exception ex) {
					areaResultados.setText("Error: " + ex.getMessage());
					etiquetaEstado.setText(textoEstadoError());
				} finally {
					botonEscanear.setEnabled(true);
				}
			}
		}.execute();
	}

	// ====== Hooks de apariencia / textos NO localizados ======

	// === Fuentes (la impl decide si cambia) ===
	protected Font fuenteDescripcion() {
		return new Font("Segoe UI", Font.PLAIN, 14);
	}

	protected Font fuenteResultados() {
		return new Font("Consolas", Font.PLAIN, 12);
	}

	protected Font fuenteBoton() {
		return new Font("Segoe UI", Font.BOLD, 14);
	}

	// === Imagen decorativa opcional ===
	protected ImageIcon iconoDecorativo() {
		return null;
	}

	// === Textos NO localizados (la impl decide) ===
	protected String textoEstadoCargando() {
		return "cargando";
	}

	protected String textoEstadoCompletado() {
		return "completado";
	}

	protected String textoEstadoError() {
		return "error";
	}

	protected String tituloVentanaNoLocalizado() {
		return null;
	}

	// ====== CrashDetectorGUI ======
	@Override
	public void recargarApariencia() {
		aplicarApariencia();
	}

	@Override
	public void init() {
		setTitle("Escaner MCreator");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		construirEstructuraBase();
		aplicarApariencia();
		setVisible(true);
	}

	protected abstract void construirEstructuraBase();

	protected abstract void aplicarApariencia();

	@Override
	public TipoGUI<EscanerMCreatorGUI> tipo() {
		return TipoGUI.ESCANER_MCREATOR;
	}
}