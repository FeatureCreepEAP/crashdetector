package com.asbestosstar.crashdetector.gui.tipos.historia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Implementación "Legacy" centrada en apariencia.
 * 
 * - Define paleta de colores, fuentes, paddings, tamaños preferidos. - Inserta
 * imagen "clio.png" y un panel descriptivo (texto NO localizado permitido
 * aquí). - Restablece todo en recargarApariencia().
 */
public class HistoriaModsGUILegacy extends HistoriaDeModsGUI {

	public static String ID = "historia_mods_legacy";
	private static final long serialVersionUID = 1L;

	// ====== Paleta (apariencia) ======
	// Colores semánticos (éxito/fallo)
	private static final Color COLOR_EXITO = new Color(0x4C, 0xAF, 0x50); // verde
	private static final Color COLOR_FALLO = new Color(0xF4, 0x43, 0x36); // rojo

	// Colores para diffs (+/-) en HTML (hex)
	private static final String HEX_PLUS = "#2E7D32"; // verde oscuro
	private static final String HEX_MINUS = "#C62828"; // rojo oscuro

	// Tipografías
	private static final Font FUENTE_ETIQUETA = new Font(Font.SANS_SERIF, Font.BOLD, 14);
	private static final Font FUENTE_RADIO = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	private static final Font FUENTE_ESTADO = new Font(Font.SANS_SERIF, Font.PLAIN, 10);

	// Panel inferior de ayuda/imagen (solo apariencia)
	private JPanel panelInferior;
	private JTextPane descripcionHTML;

	public HistoriaModsGUILegacy() {
		// super();
		// Mostrar ventana (construcción ya hecha en super)
	}

	// ====== Apariencia (override hooks) ======

	@Override
	protected Color colorEstadoExito() {
		return COLOR_EXITO;
	}

	@Override
	protected Color colorEstadoFallo() {
		return COLOR_FALLO;
	}

	@Override
	protected String colorHexParaResultadoAnadido() {
		return HEX_PLUS;
	}

	@Override
	protected String colorHexParaResultadoEliminado() {
		return HEX_MINUS;
	}

	@Override
	protected void estilizarRadioArchivo(javax.swing.JRadioButton radio) {
		radio.setFont(FUENTE_RADIO);
	}

	@Override
	protected void estilizarEstadoArchivo(javax.swing.JLabel estado) {
		estado.setFont(FUENTE_ESTADO);
	}

	/**
	 * Restablece por completo colores, fuentes, bordes, paddings y textos NO
	 * localizados (p.ej. explicación del panel inferior). No toca datos ni
	 * selección.
	 */
	@Override
	protected void aplicarApariencia() {
		// Bordes/paddings base
		if (panelPrincipal != null) {
			panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		}

		// Scrolls (marcos suaves)
		if (scrollIzquierdo != null)
			scrollIzquierdo.setBorder(BorderFactory.createLineBorder(new Color(0xDDDDDD)));
		if (scrollDerecho != null)
			scrollDerecho.setBorder(BorderFactory.createLineBorder(new Color(0xDDDDDD)));
		if (scrollResultado != null)
			scrollResultado.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		// Títulos de columnas (buscar los dos primeros labels dentro de panelSuperior)
		if (panelSuperior != null && panelSuperior.getComponentCount() >= 2) {
			if (panelSuperior.getComponent(0) instanceof javax.swing.JLabel) {
				javax.swing.JLabel lbl0 = (javax.swing.JLabel) panelSuperior.getComponent(0);
				lbl0.setFont(FUENTE_ETIQUETA);
			}
			if (panelSuperior.getComponent(1) instanceof javax.swing.JLabel) {
				javax.swing.JLabel lbl1 = (javax.swing.JLabel) panelSuperior.getComponent(1);
				lbl1.setFont(FUENTE_ETIQUETA);
			}
		}

		// Botón comparar (solo estilo visual, el texto es localizado)
		if (botonComparar != null) {
			botonComparar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		}

		// Resultado HTML (fuentes por defecto, margen interno)
		if (resultadoPanel != null) {
			resultadoPanel.setMargin(new java.awt.Insets(6, 6, 6, 6));
		}

		// Panel inferior con texto NO localizado + imagen (clio.png)
		instalarPanelInferiorConImagenYTexto();
		revalidate();
		repaint();
	}

	/**
	 * Crea/reemplaza el panel inferior puramente de apariencia (no datos), con un
	 * bloque HTML de ayuda NO localizado y la imagen clio.png.
	 */
	private void instalarPanelInferiorConImagenYTexto() {
		// Eliminar si ya existe para evitar duplicados al recargar apariencia
		if (panelInferior != null) {
			panelPrincipal.remove(panelInferior);
			panelInferior = null;
		}

		panelInferior = new JPanel(new BorderLayout());
		panelInferior.setOpaque(false);

		// Descripción (NO localizada; se puede cambiar libremente) — Java 8 friendly
		String ayudaNoLocalizada = String.format("<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>Consejo:</b> Selecciona dos archivos de historial para comparar la lista de mods. "
				+ "  El resultado muestra <span style='color:%s;'>añadidos (+)</span> y "
				+ "  <span style='color:%s;'>eliminados (&#8722;)</span> basados en nombres normalizados." + // &#8722;
																												// =
																												// signo
																												// menos
																												// (compat)
				"</body>" + "</html>", HEX_PLUS, HEX_MINUS);

		descripcionHTML = new JTextPane();
		descripcionHTML.setContentType("text/html");
		descripcionHTML.setEditable(false);
		descripcionHTML.setText(ayudaNoLocalizada);

		JScrollPane scrollDescripcion = new JScrollPane(descripcionHTML);
		scrollDescripcion.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollDescripcion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollDescripcion.setPreferredSize(new Dimension(600, 100));
		scrollDescripcion.setBorder(BorderFactory.createEmptyBorder());

		JPanel textoPanel = new JPanel(new BorderLayout());
		textoPanel.setOpaque(false);
		textoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		textoPanel.add(scrollDescripcion, BorderLayout.CENTER);

		// Imagen decorativa (clio.png). Si no existe, se copia desde el JAR.
		JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		contenedor.setOpaque(false);
		contenedor.add(textoPanel);

		try {
			Path rutaImagen = MonitorDePID.carpeta.resolve("imagenes/clio.png");
			if (!Files.exists(rutaImagen)) {
				MonitorDePID.copiarACarpetaDesdeJar("/imagenes/clio.png", rutaImagen.toFile());
			}
			if (Files.exists(rutaImagen)) {
				ImageIcon icono = new ImageIcon(rutaImagen.toAbsolutePath().toString());
				// Escala suave a 100x100
				java.awt.Image escalado = icono.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
				JLabel etiquetaImagen = new JLabel(new ImageIcon(escalado));
				contenedor.add(etiquetaImagen);
			}
		} catch (Exception e) {
			CrashDetectorLogger.log("Error cargando clio.png: " + e.getMessage());
		}

		panelInferior.add(contenedor, BorderLayout.CENTER);
		panelInferior.setMinimumSize(new Dimension(100, 100));

		panelPrincipal.add(panelInferior, BorderLayout.PAGE_END);
	}

	// ====== CrashDetectorGUI (datos de barra lateral / init) ======

	@Override
	public String id() {
		// Codename simple en español
		return ID;
	}

	// Si tu interfaz BotonDeBarraLateralDerecha exige etiquetaDelBoton(), puedes
	// descomentar:
	// @Override
	// public String etiquetaDelBoton() {
	// return MonitorDePID.idioma.historialDeMods();
	// }
}
