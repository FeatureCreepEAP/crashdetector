package com.asbestosstar.crashdetector.gui.tipos.quickfix;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Panel base abstracto para mostrar múltiples QuickFix con scroll.
 *
 * RESPONSABILIDADES (técnicas): - Gestiona el contenedor con BoxLayout vertical
 * y margen. - Mantiene un «pie» con UNA sola imagen decorativa al final. -
 * Proporciona métodos para agregar QuickFix antes del pie y limpiar todo. -
 * Expone hooks de apariencia: ruta/tamaño de imagen, color de separador,
 * opacidad, etc.
 *
 * La implementación concreta (p. ej. DemonSlayers) se centra en APARIENCIA y
 * LAYOUT.
 */
public abstract class TodosQuickFixesGUI extends JScrollPane implements CrashDetectorGUI {

	private static final long serialVersionUID = 1L;

	/** Mantener este registro de GUIs (no eliminar). */
	public static Map<String, Supplier<TodosQuickFixesGUI>> GUIS = new HashMap<String, Supplier<TodosQuickFixesGUI>>();

	// ====== Estructura técnica (ahora vacía, se inicializa en la implementación
	// concreta) ======
	protected JPanel panelContenedor; // donde van los ElementoQuickFix
	protected JPanel piePanel; // «pie» fijo al final con UNA imagen

	// ====== CAMPOS DE COLOR CONFIGURABLES (ahora públicos) ======
	public ConfigColor colorSeparador = ConfigColor.de("quickfix_separador", java.awt.Color.LIGHT_GRAY);

	// ====== API pública ======

	/** Añade un QuickFix siempre antes del pie. */
	public void agregarQuickFix(QuickFix quickFix) {
		if (panelContenedor == null || piePanel == null) {
			CrashDetectorLogger.log("Error: panelContenedor o piePanel no inicializados.");
			return;
		}
		// Asegurar que el pie está presente una sola vez
		if (piePanel.getParent() != panelContenedor) {
			panelContenedor.add(piePanel);
		}

		int idxPie = panelContenedor.getComponentCount() - 1; // pie al final

		// Separador si ya hay otros QuickFix antes del pie
		if (idxPie > 0) {
			JSeparator separador = new JSeparator();
			// El color del separador se aplica en la implementación concreta
			separador.setForeground(colorSeparador.obtener());
			panelContenedor.add(separador, idxPie);
			idxPie++; // ajustar índice porque insertamos el separador
		}

		// Insertar QuickFix justo antes del pie
		ElementoQuickFix elemento = new ElementoQuickFix(quickFix);
		panelContenedor.add(elemento, idxPie);

		panelContenedor.revalidate();
		panelContenedor.repaint();
	}

	/** Limpia todos los QuickFix y deja solo el pie con UNA imagen. */
	public void limpiar() {
		if (panelContenedor != null && piePanel != null) {
			panelContenedor.removeAll();
			panelContenedor.add(piePanel); // volver a poner el pie una sola vez
			panelContenedor.revalidate();
			panelContenedor.repaint();
		}
	}

	// ====== Hooks de apariencia (ahora abstractos o con valor por defecto) ======

	/** Ruta absoluta del archivo de imagen del pie. */
	protected abstract String rutaImagenPie();

	/**
	 * Ancho de la imagen del pie. (La subclase puede devolver 1024 si lo desea).
	 */
	protected int anchoImagenPie() {
		return 128;
	}

	/** Alto de la imagen del pie. */
	protected int altoImagenPie() {
		return 128;
	}

	/** Texto placeholder si no se encuentra la imagen. */
	protected String textoFallbackImagen() {
		return "(No se pudo cargar la imagen)";
	}

	/** Restablece colores/opacidades/paddings y cualquier texto NO localizado. */
	protected abstract void aplicarApariencia();

	// ====== Utilidades internas ======

	/** Crea una etiqueta con la imagen escalada a w×h; devuelve null si falla. */
	protected JLabel crearEtiquetaImagenEscalada(String ruta, int w, int h) {
		if (ruta == null)
			return null;
		try {
			Image img = ImageIO.read(new File(ruta));
			if (img == null)
				return null;
			Image esc = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
			return new JLabel(new ImageIcon(esc));
		} catch (IOException ex) {
			return null;
		}
	}

	// ====== CrashDetectorGUI ======

	@Override
	public void recargarApariencia() {
		aplicarApariencia(); // restablece colores/opacidades/NO localizados
	}

	@Override
	public TipoGUI<TodosQuickFixesGUI> tipo() {
		return TipoGUI.TODOS_QUICKFIXES;
	}

	// `id()` lo define cada implementación concreta.
	@Override
	public abstract String id();

	@Override
	public abstract void init();

	@Override
	public abstract java.util.List<com.asbestosstar.crashdetector.config.ElementoConfig> obtenerElementosConfigs();
}