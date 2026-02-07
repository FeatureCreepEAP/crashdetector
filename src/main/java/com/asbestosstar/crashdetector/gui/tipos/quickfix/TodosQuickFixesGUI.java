package com.asbestosstar.crashdetector.gui.tipos.quickfix;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Ventana base abstracta para mostrar múltiples QuickFix.
 *
 * RESPONSABILIDADES EXCLUSIVAS: - Gestionar ventana (JFrame) - Gestionar scroll
 * - Gestionar inserción y limpieza de QuickFix
 *
 *
 * Toda la apariencia pertenece a la implementación concreta.
 */
public abstract class TodosQuickFixesGUI extends JFrame implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	/** Registro global de GUIs */
	public static Map<String, Supplier<TodosQuickFixesGUI>> GUIS = new HashMap<>();

	// ===== Estructura técnica =====
	protected JScrollPane scroll;
	protected JPanel panelContenedor;
	protected JPanel piePanel;

	// ===== API pública =====

	/**
	 * Añade un QuickFix justo antes del pie.
	 */
	public void agregarQuickFix(QuickFix quickFix) {
		if (panelContenedor == null || piePanel == null) {
			CrashDetectorLogger.log("QuickFix: contenedor no inicializado");
			return;
		}

		int indicePie = panelContenedor.getComponentCount() - 1;

		// Separador lógico (apariencia definida por la implementación)
		if (indicePie > 0) {
			panelContenedor.add(new JSeparator(), indicePie);
			indicePie++;
		}

		panelContenedor.add(new ElementoQuickFix(quickFix), indicePie);
		panelContenedor.revalidate();
		panelContenedor.repaint();
	}

	/**
	 * Elimina todos los QuickFix y deja solo el pie.
	 */
	public void limpiar() {
		if (panelContenedor == null || piePanel == null)
			return;

		panelContenedor.removeAll();
		panelContenedor.add(piePanel);
		panelContenedor.revalidate();
		panelContenedor.repaint();
	}

	// ===== Hooks de implementación =====

	protected abstract String rutaImagenPie();

	protected int anchoImagenPie() {
		return 128;
	}

	protected int altoImagenPie() {
		return 128;
	}

	protected String textoFallbackImagen() {
		return "(Imagen no disponible)";
	}

	/** Aplica TODA la apariencia (colores, opacidades, separadores, etc.) */
	protected abstract void aplicarApariencia();

	// ===== Utilidades =====

	protected JLabel crearEtiquetaImagenEscalada(String ruta, int w, int h) {
		try {
			Image img = ImageIO.read(new File(ruta));
			Image esc = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			return new JLabel(new ImageIcon(esc));
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Configuración mínima de la ventana. La implementación puede modificar lo que
	 * quiera después.
	 */
	protected void configurarVentanaBase() {
		setTitle("QuickFix");
		setSize(720, 520);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
	}

	// ===== CrashDetectorGUI =====

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
	}

	@Override
	public final TipoGUI<TodosQuickFixesGUI> tipo() {
		return TipoGUI.TODOS_QUICKFIXES;
	}

	@Override
	public abstract String id();

	@Override
	public abstract void init();
}
