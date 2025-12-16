package com.asbestosstar.crashdetector.gui.tipos.quickfix;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación "DemonSlayers" centrada en APARIENCIA y LAYOUT.
 *
 * - Define ruta/tamaño de la imagen del pie. - Ajusta el color del separador. -
 * Ajusta opacidades para mantener look transparente. - `id()` propio (codename
 * en español).
 */
public class PanelQuickFixDemonSlayers extends TodosQuickFixesGUI {

	private static final long serialVersionUID = 1L;
	public static String ID = "demonslayers";

	ConfigColor colorSeparador = ConfigColor.de("quickfix_demonslayers_separador", Color.LIGHT_GRAY);

	public PanelQuickFixDemonSlayers() {
		super();
		// Inicializar campos de color específicos para esta implementación
		// Crear la interfaz de usuario
		inicializarUI();
	}

	private void inicializarUI() {
		// Contenedor principal
		panelContenedor = new javax.swing.JPanel();
		panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
		panelContenedor.setOpaque(false);
		panelContenedor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Scroll (configuración básica, apariencia se aplica en aplicarApariencia)
		getViewport().setOpaque(false);
		setOpaque(false);
		setViewportView(panelContenedor);
		getVerticalScrollBar().setUnitIncrement(16);

		// Pie con UNA imagen (apariencia definida por esta clase)
		piePanel = new javax.swing.JPanel();
		piePanel.setOpaque(false);
		piePanel.setLayout(new BoxLayout(piePanel, BoxLayout.Y_AXIS));
		piePanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

		javax.swing.JLabel imagen = crearEtiquetaImagenEscalada(rutaImagenPie(), anchoImagenPie(), altoImagenPie());
		if (imagen != null) {
			imagen.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
			piePanel.add(imagen);
		} else {
			javax.swing.JLabel placeholder = new javax.swing.JLabel(textoFallbackImagen());
			placeholder.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
			piePanel.add(placeholder);
		}

		// Añadir el pie UNA sola vez
		panelContenedor.add(piePanel);

		// Aplicar apariencia inicial
		aplicarApariencia();
	}

	@Override
	protected String rutaImagenPie() {
		return Statics.carpeta.resolve("imagenes/demonslayers.png").toString();
	}

	// Si quieres que sea 1024x1024 como decía el comentario original, cambia a
	// 1024.
	@Override
	protected int anchoImagenPie() {
		return 128;
	}

	@Override
	protected int altoImagenPie() {
		return 128;
	}

	@Override
	protected String textoFallbackImagen() {
		return "(No se pudo cargar demonslayers.png)";
	}

	@Override
	protected void aplicarApariencia() {
		// Mantener transparencia (como tu versión inicial):
		getViewport().setOpaque(false);
		setOpaque(false);
		if (panelContenedor != null)
			panelContenedor.setOpaque(false);
		if (piePanel != null)
			piePanel.setOpaque(false);

		// Si quisieras agregar colores o bordes, hazlo aquí (NO localizado)
		// panelContenedor.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); //
		// ya se define en base
		revalidate();
		repaint();
	}

	// ====== CrashDetectorGUI ======

	@Override
	public String id() {
		// Codename simple en español
		return ID;
	}

	@Override
	public void init() {
		this.setVisible(true);
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
	    List<ElementoConfig> elementos = new ArrayList<>();

	    colorSeparador.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorSeparador());
	    elementos.add(colorSeparador);

	    return elementos;
	}
}