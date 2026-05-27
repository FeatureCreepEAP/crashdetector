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
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class EscanerMCreatorGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	// Mantener el registro de GUIS
	public static Map<String, Supplier<EscanerMCreatorGUI>> GUIS = new HashMap<>();

	// ====== Componentes técnicos ======
	public JTextArea areaResultados;

	/**
	 * Se conserva por compatibilidad con temas viejos.
	 *
	 * Los temas nuevos pueden no usar barra de estado y escribir el estado dentro
	 * del área principal de resultados.
	 */
	public JLabel etiquetaEstado;

	public JButton botonEscanear;

	// Contenedores
	public JPanel panelContenido;
	public JPanel panelContenidoConImagen;
	public JScrollPane panelDesplazamiento;

	// ====== Constructor ======
	public EscanerMCreatorGUI() {
		super();
	}

	// ====== Métodos auxiliares para aplicación de colores ======
	public void aplicarColor(JLabel label, ConfigColor color) {
		if (label == null || color == null) {
			return;
		}

		label.setForeground(color.obtener());
	}

	public void aplicarFondo(JComponent component, ConfigColor color) {
		if (component == null || color == null) {
			return;
		}

		component.setBackground(color.obtener());
		component.setOpaque(true);
	}

	// ====== Lógica técnica del escaneo ======
	public void iniciarEscaneo() {
		areaResultados.setText(MonitorDePID.idioma.escanerMCreatorEscaneandoMods() + "\n\n"
				+ MonitorDePID.idioma.escanerMCreatorPorFavorEspera());

		areaResultados.setCaretPosition(0);

		if (etiquetaEstado != null) {
			etiquetaEstado.setText(textoEstadoCargando());
		}

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

					resultado = limpiarResultadoEscaner(resultado);

					if (resultado == null || resultado.trim().isEmpty()) {
						resultado = MonitorDePID.idioma.escanerMCreatorNoSeEncontraronMods();
					}

					areaResultados.setText(MonitorDePID.idioma.escanerMCreatorResultadosAnalisis() + "\n\n" + resultado
							+ "\n\n" + MonitorDePID.idioma.escanerMCreatorEscaneoCompletado());

					areaResultados.setCaretPosition(0);

					if (etiquetaEstado != null) {
						etiquetaEstado.setText(textoEstadoCompletado());
					}
				} catch (Exception ex) {
					areaResultados.setText(
							MonitorDePID.idioma.escanerMCreatorErrorDuranteEscaneo() + "\n\n" + ex.getMessage());

					areaResultados.setCaretPosition(0);

					if (etiquetaEstado != null) {
						etiquetaEstado.setText(textoEstadoError());
					}
				} finally {
					botonEscanear.setEnabled(true);
					botonEscanear.setText(MonitorDePID.idioma.escanear());
				}
			}
		}.execute();
	}

	public String limpiarResultadoEscaner(String resultado) {
		if (resultado == null) {
			return "";
		}

		String limpio = resultado.trim();
		String encabezado = MonitorDePID.idioma.escanerMCreatorResultadosAnalisis();

		if (limpio.startsWith(encabezado)) {
			limpio = limpio.substring(encabezado.length()).trim();
		}

		return limpio;
	}

	// ====== Hooks de apariencia / textos localizados ======

	public Font fuenteDescripcion() {
		return new Font("Segoe UI", Font.PLAIN, 14);
	}

	public Font fuenteResultados() {
		return new Font("Consolas", Font.PLAIN, 12);
	}

	public Font fuenteBoton() {
		return new Font("Segoe UI", Font.BOLD, 14);
	}

	public ImageIcon iconoDecorativo() {
		return null;
	}

	public String textoEstadoCargando() {
		return MonitorDePID.idioma.escanerMCreatorCargando();
	}

	public String textoEstadoCompletado() {
		return MonitorDePID.idioma.escanerMCreatorCompletado();
	}

	public String textoEstadoError() {
		return MonitorDePID.idioma.escanerMCreatorError();
	}

	public String tituloVentanaNoLocalizado() {
		return null;
	}

	// ====== CrashDetectorGUI ======

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
	}

	@Override
	public void init() {
		String titulo = tituloVentanaNoLocalizado();

		if (titulo == null || titulo.isEmpty()) {
			titulo = MonitorDePID.idioma.tituloEscanerMCreator();
		}

		setTitle(titulo);
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		construirEstructuraBase();
		aplicarApariencia();
		setVisible(true);
	}

	public abstract void construirEstructuraBase();

	public abstract void aplicarApariencia();

	@Override
	public TipoGUI<EscanerMCreatorGUI> tipo() {
		return TipoGUI.ESCANER_MCREATOR;
	}

}